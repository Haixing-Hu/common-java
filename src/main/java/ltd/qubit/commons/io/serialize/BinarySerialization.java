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
 * Provides functions to manager {@link BinarySerializer}s, as well as functions
 * to help serializing and deserializing objects to and from binary streams.
 *
 * @author Haixing Hu
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
   * Registers a binary serializer for a class.
   *
   * @param objClass
   *     The class object.
   * @param serializer
   *     The binary serializer for the specified class.
   */
  public static void register(final Class<?> objClass,
      final BinarySerializer serializer) {
    requireNonNull("objClass", objClass);
    requireNonNull("serializer", serializer);
    LOGGER.debug("Registering a binary serializer for class {}.", objClass);
    REGISTRY.put(new ClassKey(objClass), serializer);
  }

  /**
   * Gets the registered binary serializer for a specified class.
   *
   * @param objClass
   *     The class object.
   * @return The registered binary serializer for the specified class, or null
   *     if no binary serializer was registered for the specified class.
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
   * Serializes an object to an binary output stream.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class object of the object to be serialized.
   * @param obj
   *     The object to be serialized. It could be null.
   * @param out
   *     A binary output stream. Note that the output stream will be closed
   *     after calling this function.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Serializes an object to a local file.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class object of the object to be serialized.
   * @param obj
   *     The object to be serialized. It could be null.
   * @param file
   *     The abstract path of the local file where to store the binary
   *     serialization of the object.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Serializes an object to a binary array.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class object of the object to be serialized.
   * @param obj
   *     The object to be serialized. It could be null.
   * @return The binary array where to store the binary serialization of the
   *     object.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Deserializes an object from an binary input stream.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class of the objects to serialize.
   * @param in
   *     A binary input stream. Note that the input stream will be closed after
   *     calling this function.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Deserializes an object from a local file.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class of the objects to serialize.
   * @param file
   *     An abstract path of a local file.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Deserializes an object from a URL.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class of the objects to serialize.
   * @param url
   *     An URL.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
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
      in = UrlUtils.openStream(url);
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
   * Deserializes an object from a URI.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class of the objects to serialize.
   * @param uri
   *     An URI.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Deserializes an object from a URL.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class of the objects to serialize.
   * @param url
   *     An URL.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
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
   * Deserializes an object from a byte array.
   *
   * @param <T>
   *     The type of the class.
   * @param objClass
   *     The class of the objects to serialize.
   * @param data
   *     An byte array.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
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