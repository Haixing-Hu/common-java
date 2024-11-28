////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.jackson.type.TypeRegister;

/**
 * 自定义的Jackson模块，支持自定义的序列化和反序列化器。
 *
 * @author 胡海星
 */
public class TypeRegistrationModule extends com.fasterxml.jackson.databind.Module {

  public static final String MODULE_NAME = TypeRegistrationModule.class.getSimpleName();

  public static final Version VERSION = new Version(1, 0, 0, null, "ltd.qubit",
      TypeRegistrationModule.class.getName());

  private static final Logger LOGGER = LoggerFactory.getLogger(TypeRegistrationModule.class);

  private static final MapSerializers SERIALIZERS = new MapSerializers();
  private static final MapDeserializers DESERIALIZERS = new MapDeserializers();
  private static final MapSerializers KEY_SERIALIZERS = new MapSerializers();
  private static final MapKeyDeserializers KEY_DESERIALIZERS = new MapKeyDeserializers();

  static {
    registerTypesByServiceLoader();
  }

  public static final TypeRegistrationModule INSTANCE = new TypeRegistrationModule();

  @SuppressWarnings("rawtypes")
  public static List<TypeRegister> getCommonTypeRegisters() {
    final String resource = "META-INF/services/" + TypeRegister.class.getName();
    LOGGER.info("Loading the common Jackson type registers from the resource: {}", resource);
    final List<TypeRegister> result;
    try {
      result = SystemUtils.loadInstance(TypeRegister.class, resource,
          TypeRegistrationModule.class.getClassLoader());
    } catch (final IOException | ClassNotFoundException e) {
      LOGGER.error("Failed to load the common Jackson type registers from the resource: {}", resource, e);
      return Collections.emptyList();
    }
    LOGGER.info("Totally {} common Jackson types registered.", result.size());
    return result;
  }

  public static void clear() {
    LOGGER.info("Clearing all Jackson type registers...");
    SERIALIZERS.clear();
    DESERIALIZERS.clear();
    KEY_SERIALIZERS.clear();
    KEY_DESERIALIZERS.clear();
  }

  /**
   * 通过 ServiceLoader 加载预定义的 Jackson 类型注册器。
   * <p>
   * 安卓系统会根据{@code AndroidManifest.xml}中是否设置了{@code android:sharedUserId}
   * 属性来决定使用哪种{@code ClassLoader}：对于设置了{@code android:sharedUserId}
   * 属性的应用，会使用一个特殊的{@code WarningClassLoader}，无法正确通过
   * {@code ServiceLoader}预注册的服务资源。
   *
   * @see #registerCommonTypes()
   * @see #registerTypes(List)
   * @see #registerType(TypeRegister)
   */
  public static void registerTypesByServiceLoader() {
    LOGGER.info("Loading predefined Jackson type registers...");
    // 注意，使用 ServiceLoader.load() 时，必须提供一个 ClassLoader，否则会使用默认的
    // 当前线程的上下文 ClassLoader。在普通的 Java 程序中，这么做没什么问题。但如果是
    // 安卓程序，安卓系统会根据 AndroidManifest.xml 中是否设置了 android:sharedUserId
    // 属性来决定使用哪种 ClassLoader：对于设置了 android:sharedUserId 属性的应用，
    // 会使用一个特殊的 WarningClassLoader，无法正确 load 预注册的服务资源。
    // 具体参见：
    // [1] https://blog.csdn.net/Eqiqi/article/details/129042141
    // [2] https://github.com/ACRA/acra/issues/656
    // [3] https://github.com/ACRA/acra/pull/657
    @SuppressWarnings("rawtypes")
    final ServiceLoader<TypeRegister> loader = ServiceLoader.load(TypeRegister.class,
        TypeRegistrationModule.class.getClassLoader());

    int count = 0;
    for (final TypeRegister<?> register : loader) {
      ++count;
      registerType(register);
    }
    LOGGER.info("Totally {} predefined Jackson types registered.", count);
  }

  /**
   * 注册常见的预定义的 Jackson 类型注册器。
   * <p>
   * 这些类型注册器是定义在此JAR的
   * {@code META-INF/services/ltd.qubit.commons.text.jackson.type.TypeRegister}
   * 中的。
   */
  @SuppressWarnings("rawtypes")
  public static void registerCommonTypes() {
    final List<TypeRegister> registerList = getCommonTypeRegisters();
    registerTypes(registerList);
  }

  /**
   * 注册一组 Jackson 类型注册器。
   *
   * @param registers
   *     要注册的 Jackson 类型注册器。
   */
  @SuppressWarnings("rawtypes")
  public static void registerTypes(final List<TypeRegister> registers) {
    LOGGER.info("Adding Jackson type registers...");
    int count = 0;
    for (final TypeRegister<?> r : registers) {
      registerType(r);
      ++count;
    }
    LOGGER.info("Totally {} Jackson types registered.", count);
  }

  /**
   * 注册一个 Jackson 类型注册器。
   *
   * @param register
   *     要注册的 Jackson 类型注册器。
   */
  public static void registerType(final TypeRegister<?> register) {
    final Class<?> type = register.getType();
    LOGGER.info("Registering the Jackson type: {}", ClassUtils.getFullCanonicalName(type));
    final JsonSerializer<?> serializer = register.getSerializer();
    if (serializer != null) {
      SERIALIZERS.put(type, serializer);
    }
    final JsonDeserializer<?> deserializer = register.getDeserializer();
    if (deserializer != null) {
      DESERIALIZERS.put(type, deserializer);
    }
    final JsonSerializer<?> keySerializer = register.getKeySerializer();
    if (keySerializer != null) {
      KEY_SERIALIZERS.put(type, keySerializer);
    }
    final KeyDeserializer keyDeserializer = register.getKeyDeserializer();
    if (keyDeserializer != null) {
      KEY_DESERIALIZERS.put(type, keyDeserializer);
    }
  }

  public static <T> void addSerializer(final Class<? extends T> type, final JsonSerializer<T> serializer) {
    SERIALIZERS.put(type, serializer);
  }

  public static <T> void addDeserializer(final Class<? extends T> type, final JsonDeserializer<T> deserializer) {
    DESERIALIZERS.put(type, deserializer);
  }

  public static <T> void addKeySerializer(final Class<? extends T> type, final JsonSerializer<T> serializer) {
    KEY_SERIALIZERS.put(type, serializer);
  }

  public static <T> void addKeyDeserializer(final Class<? extends T> type, final KeyDeserializer deserializer) {
    KEY_DESERIALIZERS.put(type, deserializer);
  }

  @Nullable
  public static JsonSerializer<?> getSerializer(final Class<?> type) {
    return SERIALIZERS.get(type);
  }

  @Nullable
  public static JsonDeserializer<?> getDeserializer(final Class<?> type) {
    return DESERIALIZERS.get(type);
  }

  @Nullable
  public static JsonSerializer<?> getKeySerializer(final Class<?> type) {
    return KEY_SERIALIZERS.get(type);
  }

  @Nullable
  public static KeyDeserializer getKeyDeserializer(final Class<?> type) {
    return KEY_DESERIALIZERS.get(type);
  }

  public static int getSerializerCount() {
    return SERIALIZERS.size();
  }

  public static int getDeserializerCount() {
    return DESERIALIZERS.size();
  }

  public static int getKeySerializerCount() {
    return KEY_SERIALIZERS.size();
  }

  private TypeRegistrationModule() {
    //  empty
  }

  public MapSerializers getSerializers() {
    return SERIALIZERS;
  }

  public MapDeserializers getDeserializers() {
    return DESERIALIZERS;
  }

  public MapSerializers getKeySerializers() {
    return KEY_SERIALIZERS;
  }

  public MapKeyDeserializers getKeyDeserializers() {
    return KEY_DESERIALIZERS;
  }

  @Override
  public void setupModule(final SetupContext context) {
    context.addSerializers(SERIALIZERS);
    context.addDeserializers(DESERIALIZERS);
    context.addKeySerializers(KEY_SERIALIZERS);
    context.addKeyDeserializers(KEY_DESERIALIZERS);
  }

  @Override
  public String getModuleName() {
    return MODULE_NAME;
  }

  @Override
  public Version version() {
    return VERSION;
  }
}
