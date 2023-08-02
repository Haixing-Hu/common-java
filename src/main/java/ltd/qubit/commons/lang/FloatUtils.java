////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * This class provides operations on {@code float} primitives and
 * {@link Float} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@code float} values or
 * {@link Float} objects to common types.
 *
 * @author Haixing Hu
 */
public class FloatUtils {

  /**
   * The default {@code float} value used when necessary.
   */
  public static final float DEFAULT = 0.0f;

  /**
   * The default epsilon for {@code float} values.
   */
  public static final float EPSILON = 0.0001f;

  public static float toPrimitive(@Nullable final Float value) {
    return (value == null ? DEFAULT : value);
  }

  public static float toPrimitive(@Nullable final Float value,
      final float defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static boolean toBoolean(final float value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Float value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.floatValue()));
  }

  public static boolean toBoolean(@Nullable final Float value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.floatValue()));
  }

  public static Boolean toBooleanObject(final float value) {
    return toBoolean(value);
  }

  public static Boolean toBooleanObject(@Nullable final Float value) {
    return (value == null ? null : toBooleanObject(value.floatValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Float value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.floatValue()));
  }

  public static char toChar(final float value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Float value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.floatValue()));
  }

  public static char toChar(@Nullable final Float value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.floatValue()));
  }

  public static Character toCharObject(final float value) {
    return toChar(value);
  }

  public static Character toCharObject(@Nullable final Float value) {
    return (value == null ? null : toCharObject(value.floatValue()));
  }

  public static Character toCharObject(@Nullable final Float value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.floatValue()));
  }

  public static byte toByte(final float value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Float value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.floatValue()));
  }

  public static byte toByte(@Nullable final Float value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.floatValue()));
  }

  public static Byte toByteObject(final float value) {
    return toByte(value);
  }

  public static Byte toByteObject(@Nullable final Float value) {
    return (value == null ? null : toByteObject(value.floatValue()));
  }

  public static Byte toByteObject(@Nullable final Float value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.floatValue()));
  }

  public static short toShort(final float value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Float value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.floatValue()));
  }

  public static short toShort(@Nullable final Float value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.floatValue()));
  }

  public static Short toShortObject(final float value) {
    return toShort(value);
  }

  public static Short toShortObject(@Nullable final Float value) {
    return (value == null ? null : toShortObject(value.floatValue()));
  }

  public static Short toShortObject(@Nullable final Float value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.floatValue()));
  }

  public static int toInt(final float value) {
    return (int) value;
  }

  public static int toInt(@Nullable final Float value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.floatValue()));
  }

  public static int toInt(@Nullable final Float value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.floatValue()));
  }

  public static Integer toIntObject(final float value) {
    return toInt(value);
  }

  public static Integer toIntObject(@Nullable final Float value) {
    return (value == null ? null : toIntObject(value.floatValue()));
  }

  public static Integer toIntObject(@Nullable final Float value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.floatValue()));
  }

  public static long toLong(final float value) {
    return (long) value;
  }

  public static long toLong(@Nullable final Float value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.floatValue()));
  }

  public static long toLong(@Nullable final Float value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.floatValue()));
  }

  public static Long toLongObject(final float value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Float value) {
    return (value == null ? null : toLongObject(value.floatValue()));
  }

  public static Long toLongObject(@Nullable final Float value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.floatValue()));
  }

  public static double toDouble(final float value) {
    return value;
  }

  public static double toDouble(@Nullable final Float value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.floatValue()));
  }

  public static double toDouble(@Nullable final Float value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.floatValue()));
  }

  public static Double toDoubleObject(final float value) {
    return toDouble(value);
  }

  public static Double toDoubleObject(@Nullable final Float value) {
    return (value == null ? null : toDoubleObject(value.floatValue()));
  }

  public static Double toDoubleObject(@Nullable final Float value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.floatValue()));
  }

  public static String toString(final float value) {
    return Float.toString(value);
  }

  public static String toString(@Nullable final Float value) {
    return (value == null ? null : toString(value.floatValue()));
  }

  public static String toString(@Nullable final Float value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.floatValue()));
  }

  public static Date toDate(final float value) {
    return new Date((long) value);
  }

  public static Date toDate(@Nullable final Float value) {
    return (value == null ? null : toDate(value.floatValue()));
  }

  public static Date toDate(@Nullable final Float value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.floatValue()));
  }

  public static byte[] toByteArray(final float value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final float value,
      final ByteOrder byteOrder) {
    final int bits = Float.floatToIntBits(value);
    return IntUtils.toByteArray(bits, byteOrder);
  }

  public static byte[] toByteArray(@Nullable final Float value) {
    return (value == null ? null : toByteArray(value.floatValue()));
  }

  public static byte[] toByteArray(@Nullable final Float value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.floatValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Float value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.floatValue()));
  }

  public static byte[] toByteArray(@Nullable final Float value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.floatValue(), byteOrder));
  }

  public static Class<?> toClass(final float value) {
    return Float.TYPE;
  }

  public static Class<?> toClass(@Nullable final Float value) {
    return (value == null ? null : Float.class);
  }

  public static Class<?> toClass(@Nullable final Float value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Float.class);
  }

  public static BigInteger toBigInteger(final float value) {
    return BigInteger.valueOf((long) value);
  }

  public static BigInteger toBigInteger(@Nullable final Float value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Float value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue
                          : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final float value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Float value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final Float value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code float}或{@code Float}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code float}或{@code Float}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }
}
