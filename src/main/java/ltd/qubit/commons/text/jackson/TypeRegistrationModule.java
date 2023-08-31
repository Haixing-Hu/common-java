////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import ltd.qubit.commons.text.jackson.deserializer.*;
import ltd.qubit.commons.text.jackson.serializer.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义的Jackson模块，支持自定义的序列化和反序列化器。
 *
 * @author Haixing Hu
 */
public class TypeRegistrationModule extends com.fasterxml.jackson.databind.Module {

  private static final long serialVersionUID = -9122443223157229093L;

  private static final ConcurrentSerializers SERIALIZERS = new ConcurrentSerializers();

  private static final ConcurrentDeserializers DESERIALIZERS = new ConcurrentDeserializers();

  private static final ConcurrentSerializers KEY_SERIALIZERS = new ConcurrentSerializers();

  private static final ConcurrentKeyDeserializers KEY_DESERIALIZERS = new ConcurrentKeyDeserializers();

  static {
    SERIALIZERS.put(Enum.class, RawEnumSerializer.INSTANCE);
    DESERIALIZERS.put(Enum.class, RawEnumDeserializer.INSTANCE);

    SERIALIZERS.put(float.class, FloatSerializer.INSTANCE);
    DESERIALIZERS.put(float.class, FloatDeserializer.INSTANCE);

    SERIALIZERS.put(Float.class, FloatSerializer.INSTANCE);
    DESERIALIZERS.put(Float.class, FloatDeserializer.INSTANCE);

    SERIALIZERS.put(double.class, DoubleSerializer.INSTANCE);
    DESERIALIZERS.put(double.class, DoubleDeserializer.INSTANCE);

    SERIALIZERS.put(Double.class, DoubleSerializer.INSTANCE);
    DESERIALIZERS.put(Double.class, DoubleDeserializer.INSTANCE);

    SERIALIZERS.put(byte[].class, Base64ByteArraySerializer.INSTANCE);
    DESERIALIZERS.put(byte[].class, Base64ByteArrayDeserializer.INSTANCE);

    SERIALIZERS.put(BigInteger.class, BigIntegerJsonSerializer.INSTANCE);
    DESERIALIZERS.put(BigInteger.class, BigIntegerDeserializer.INSTANCE);

    SERIALIZERS.put(BigDecimal.class, BigDecimalSerializer.INSTANCE);
    DESERIALIZERS.put(BigDecimal.class, BigDecimalDeserializer.INSTANCE);

    SERIALIZERS.put(Date.class, IsoDateSerializer.INSTANCE);
    DESERIALIZERS.put(Date.class, IsoDateDeserializer.INSTANCE);

    SERIALIZERS.put(Instant.class, IsoInstantSerializer.INSTANCE);
    DESERIALIZERS.put(Instant.class, IsoInstantDeserializer.INSTANCE);

    SERIALIZERS.put(Duration.class, DurationSerializer.INSTANCE);
    DESERIALIZERS.put(Duration.class, DurationDeserializer.INSTANCE);

    SERIALIZERS.put(Period.class, PeriodSerializer.INSTANCE);
    DESERIALIZERS.put(Period.class, PeriodDeserializer.INSTANCE);

    SERIALIZERS.put(LocalDate.class, IsoLocalDateSerializer.INSTANCE);
    DESERIALIZERS.put(LocalDate.class, IsoLocalDateDeserializer.INSTANCE);

    SERIALIZERS.put(LocalTime.class, IsoLocalTimeSerializer.INSTANCE);
    DESERIALIZERS.put(LocalTime.class, IsoLocalTimeDeserializer.INSTANCE);

    SERIALIZERS.put(LocalDateTime.class, IsoLocalDateTimeSerializer.INSTANCE);
    DESERIALIZERS.put(LocalDateTime.class, IsoLocalDateTimeDeserializer.INSTANCE);

    SERIALIZERS.put(Year.class, YearSerializer.INSTANCE);
    DESERIALIZERS.put(Year.class, YearDeserializer.INSTANCE);

    SERIALIZERS.put(YearMonth.class, YearMonthJsonSerializer.INSTANCE);
    DESERIALIZERS.put(YearMonth.class, YearMonthDeserializer.INSTANCE);

    SERIALIZERS.put(MonthDay.class, MonthDaySerializer.INSTANCE);
    DESERIALIZERS.put(MonthDay.class, MonthDayDeserializer.INSTANCE);

    SERIALIZERS.put(OffsetTime.class, IsoOffsetTimeJsonSerializer.INSTANCE);
    DESERIALIZERS.put(OffsetTime.class, IsoOffsetTimeJsonDeserializer.INSTANCE);

    SERIALIZERS.put(ZoneId.class, ZoneIdSerializer.INSTANCE);
    DESERIALIZERS.put(ZoneId.class, ZoneIdDeserializer.INSTANCE);

    SERIALIZERS.put(ZoneOffset.class, ZoneOffsetSerializer.INSTANCE);
    DESERIALIZERS.put(ZoneOffset.class, ZoneOffsetDeserializer.INSTANCE);

    SERIALIZERS.put(Locale.class, PosixLocaleSerializer.INSTANCE);
    DESERIALIZERS.put(Locale.class, PosixLocaleDeserializer.INSTANCE);
  }

  /**
   * 注册针对指定类型的的Jackson序列器。
   *
   * <p>该序列化器可同时被Jackson的{@code JsonMapper}和{@code XmlMapper}所使用。</p>
   *
   * @param <T>
   *     指定的类型。
   * @param type
   *     指定的类型的类对象。
   * @param serializer
   *     指定的类型对应的序列化器实例，注意待注册的序列化对象必须是不可改（Immutable）对象。
   */
  public static <T> void registerSerializer(final Class<T> type,
      final JsonSerializer<T> serializer) {
    SERIALIZERS.put(type, serializer);
  }

  /**
   * 注册针对指定类型的的Jackson反序列器。
   *
   * <p>该反序列化器可同时被Jackson的{@code JsonMapper}和{@code XmlMapper}所使用。</p>
   *
   * @param <T>
   *     指定的类型。
   * @param type
   *     指定的类型的类对象。
   * @param deserializer
   *     指定的类型对应的反序列化器实例，注意待注册的反序列化对象必须是不可改（Immutable）对象。
   */
  public static <T> void registerDeserializer(final Class<T> type,
      final JsonDeserializer<T> deserializer) {
    DESERIALIZERS.put(type, deserializer);
  }

  /**
   * 注册针对指定类型的的Jackson主键序列器。
   *
   * <p>该反序列化器可同时被Jackson的{@code JsonMapper}和{@code XmlMapper}所使用。</p>
   *
   * @param <T>
   *     指定的类型。
   * @param type
   *     指定的类型的类对象。
   * @param keySerializer
   *     指定的类型对应的主键序列化器实例，注意待注册的序列化对象必须是不可改（Immutable）对象。
   */
  public static <T> void registerKeySerializer(final Class<T> type,
      final JsonSerializer<T>  keySerializer) {
    KEY_SERIALIZERS.put(type, keySerializer);
  }

  /**
   * 注册针对指定类型的的Jackson主键反序列器。
   *
   * <p>该反序列化器可同时被Jackson的{@code JsonMapper}和{@code XmlMapper}所使用。</p>
   *
   * @param <T>
   *     指定的类型。
   * @param type
   *     指定的类型的类对象。
   * @param keyDeserializer
   *     指定的类型对应的主键反序列化器实例。
   */
  public static <T> void registerKeyDeserializer(final Class<T> type,
      final KeyDeserializer keyDeserializer) {
    KEY_DESERIALIZERS.put(type, keyDeserializer);
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

  public TypeRegistrationModule() {}

  @Override
  public void setupModule(final SetupContext context) {
    context.addSerializers(SERIALIZERS);
    context.addDeserializers(DESERIALIZERS);
    context.addKeySerializers(KEY_SERIALIZERS);
    context.addKeyDeserializers(KEY_DESERIALIZERS);
  }

  @Override
  public String getModuleName() {
    return TypeRegistrationModule.class.getSimpleName();
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }
}
