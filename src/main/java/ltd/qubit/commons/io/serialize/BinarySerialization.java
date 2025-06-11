////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.io.FileUtils;
import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.serialize.predefined.BigDecimalBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.BigIntegerBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.BooleanArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.BooleanBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.ByteArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.ByteBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.CharArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.CharacterBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.ClassBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.DateBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.DoubleArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.DoubleBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.FloatArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.FloatBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.IntArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.IntegerBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.LongArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.LongBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.ShortArrayBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.ShortBinarySerializer;
import ltd.qubit.commons.io.serialize.predefined.StringBinarySerializer;
import ltd.qubit.commons.lang.ClassKey;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.net.UrlUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 提供管理 {@link BinarySerializer} 的功能，以及帮助将对象序列化和反序列化到二进制流和从二进制流反序列化的功能。
 *
 * @author 胡海星
 */
public final class BinarySerialization {

  private static final Logger LOGGER = LoggerFactory.getLogger(BinarySerialization.class);

  private static final Map<ClassKey, BinarySerializer> REGISTRY = new ConcurrentHashMap<>();

  // register the binary serializer for classes of common data types
  static {
    REGISTRY.put(new ClassKey(Boolean.class), BooleanBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Character.class), CharacterBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Byte.class), ByteBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Short.class), ShortBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Integer.class), IntegerBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Long.class), LongBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Float.class), FloatBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Double.class), DoubleBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(String.class), StringBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Class.class), ClassBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(Date.class), DateBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(BigInteger.class), BigIntegerBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(BigDecimal.class), BigDecimalBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(boolean[].class), BooleanArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(char[].class), CharArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(byte[].class), ByteArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(short[].class), ShortArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(int[].class), IntArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(long[].class), LongArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(float[].class), FloatArrayBinarySerializer.INSTANCE);
    REGISTRY.put(new ClassKey(double[].class), DoubleArrayBinarySerializer.INSTANCE);
  }

  /**
   * 为一个类注册二进制序列化器。
   *
   * @param objClass
   *     类对象。
   * @param serializer
   *     指定类的二进制序列化器。
   */
  public static void register(final Class<?> objClass,
      final BinarySerializer serializer) {
    requireNonNull("objClass", objClass);
    requireNonNull("serializer", serializer);
    LOGGER.debug("Registering a binary serializer for class {}.", objClass);
    REGISTRY.put(new ClassKey(objClass), serializer);
  }

  /**
   * 获取为指定类注册的二进制序列化器。
   *
   * @param objClass
   *     类对象。
   * @return 为指定类注册的二进制序列化器，如果没有为指定类注册二进制序列化器则返回 null。
   */
  public static BinarySerializer getSerializer(final Class<?> objClass) {
    requireNonNull("objClass", objClass);
    LOGGER.debug("Getting the binary serializer for class {}.", objClass);
    // ensure the objClass has been load, such that the register code in
    // the static initialization block of the class could be run.
    final String className = objClass.getName();
    try {
      Class.forName(className);
    } catch (final ClassNotFoundException e) {
      LOGGER.error("Failed to load the class {}", className);
    }
    return REGISTRY.get(new ClassKey(objClass));
  }

  /**
   * 将对象序列化到二进制输出流中。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类对象。
   * @param obj
   *     要序列化的对象。它可以为 null。
   * @param out
   *     二进制输出流。注意，调用此函数后输出流将被关闭。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static <T> void serialize(final Class<T> objClass,
      @Nullable final T obj, final OutputStream out) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    try {
      serializer.serialize(out, obj);
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * 将对象序列化到本地文件中。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类对象。
   * @param obj
   *     要序列化的对象。它可以为 null。
   * @param file
   *     存储对象二进制序列化的本地文件的抽象路径。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static <T> void serialize(final Class<T> objClass,
      @Nullable final T obj, final File file) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    OutputStream out = null;
    try {
      FileUtils.ensureParentExist(file);
      out = new FileOutputStream(file);
      out = new BufferedOutputStream(out);
      serializer.serialize(out, obj);
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * 将对象序列化到二进制数组中。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类对象。
   * @param obj
   *     要序列化的对象。它可以为 null。
   * @return 存储对象二进制序列化的二进制数组。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static <T> byte[] serialize(final Class<T> objClass,
      @Nullable final T obj) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    ByteArrayOutputStream out = null;
    try {
      out = new ByteArrayOutputStream();
      serializer.serialize(out, obj);
      return out.toByteArray();
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * 从二进制输入流中反序列化对象。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类。
   * @param in
   *     二进制输入流。注意，调用此函数后输入流将被关闭。
   * @param allowNull
   *     指示是否允许序列化对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass,
      final InputStream in, final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    try {
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * 从本地文件中反序列化对象。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类。
   * @param file
   *     本地文件的抽象路径。
   * @param allowNull
   *     指示是否允许序列化对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final File file,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      in = new FileInputStream(file);
      in = new BufferedInputStream(in);
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * 从 URL 中反序列化对象。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类。
   * @param url
   *     一个 URL。
   * @param allowNull
   *     指示是否允许序列化对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URL url,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      in = url.openStream();
      if (!(in instanceof BufferedInputStream)) {
        in = new BufferedInputStream(in);
      }
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * 从 URI 中反序列化对象。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类。
   * @param uri
   *     一个 URI。
   * @param allowNull
   *     指示是否允许序列化对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URI uri,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      final URL url = uri.toURL();
      in = UrlUtils.openStream(url);
      if (!(in instanceof BufferedInputStream)) {
        in = new BufferedInputStream(in);
      }
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException | MalformedURLException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * 从 URL 中反序列化对象。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类。
   * @param url
   *     一个 URL。
   * @param allowNull
   *     指示是否允许序列化对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Url url,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    InputStream in = null;
    try {
      in = url.openStream();
      if (!(in instanceof BufferedInputStream)) {
        in = new BufferedInputStream(in);
      }
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  /**
   * 从字节数组中反序列化对象。
   *
   * @param <T>
   *     类的类型。
   * @param objClass
   *     要序列化对象的类。
   * @param data
   *     一个字节数组。
   * @param allowNull
   *     指示是否允许序列化对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final byte[] data,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    ByteArrayInputStream in = null;
    try {
      in = new ByteArrayInputStream(data);
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }
}