////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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

import ltd.qubit.commons.error.UnsupportedByteOrderException;
import ltd.qubit.commons.math.ByteBit;
import ltd.qubit.commons.text.NumberFormatSymbols;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;

/**
 * This class provides operations on {@code short} primitives and
 * {@link Short} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@code short} values or
 * {@link Short} objects to common types.
 * </p>
 *
 * @author Haixing Hu
 */
public class ShortUtils {

  /**
   * The default {@code short} value used when necessary.
   */
  public static final short DEFAULT = (short) 0;

  /**
   * The maximum value of an unsigned short.
   */
  public static final int UNSIGNED_MAX  = 0xFFFF;

  public static short toPrimitive(@Nullable final Short value) {
    return (value == null ? DEFAULT : value);
  }

  public static short toPrimitive(@Nullable final Short value,
      final short defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static boolean toBoolean(final short value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Short value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.shortValue()));
  }

  public static boolean toBoolean(@Nullable final Short value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.shortValue()));
  }

  public static Boolean toBooleanObject(final short value) {
    return toBoolean(value);
  }

  public static Boolean toBooleanObject(@Nullable final Short value) {
    return (value == null ? null : toBooleanObject(value.shortValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Short value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.shortValue()));
  }

  public static char toChar(final short value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Short value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.shortValue()));
  }

  public static char toChar(@Nullable final Short value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.shortValue()));
  }

  public static Character toCharObject(final short value) {
    return toChar(value);
  }

  public static Character toCharObject(@Nullable final Short value) {
    return (value == null ? null : toCharObject(value.shortValue()));
  }

  public static Character toCharObject(@Nullable final Short value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.shortValue()));
  }

  public static byte toByte(final short value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Short value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.shortValue()));
  }

  public static byte toByte(@Nullable final Short value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.shortValue()));
  }

  public static Byte toByteObject(final short value) {
    return toByte(value);
  }

  public static Byte toByteObject(@Nullable final Short value) {
    return (value == null ? null : toByteObject(value.shortValue()));
  }

  public static Byte toByteObject(@Nullable final Short value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.shortValue()));
  }

  public static int toInt(final short value) {
    return value;
  }

  public static int toInt(@Nullable final Short value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.shortValue()));
  }

  public static int toInt(@Nullable final Short value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.shortValue()));
  }

  public static Integer toIntObject(final short value) {
    return toInt(value);
  }

  public static Integer toIntObject(@Nullable final Short value) {
    return (value == null ? null : toIntObject(value.shortValue()));
  }

  public static Integer toIntObject(@Nullable final Short value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.shortValue()));
  }

  public static long toLong(final short value) {
    return value;
  }

  public static long toLong(@Nullable final Short value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.shortValue()));
  }

  public static long toLong(@Nullable final Short value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.shortValue()));
  }

  public static Long toLongObject(final short value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Short value) {
    return (value == null ? null : toLongObject(value.shortValue()));
  }

  public static Long toLongObject(@Nullable final Short value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.shortValue()));
  }

  public static float toFloat(final short value) {
    return value;
  }

  public static float toFloat(@Nullable final Short value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.shortValue()));
  }

  public static float toFloat(@Nullable final Short value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.shortValue()));
  }

  public static Float toFloatObject(final short value) {
    return toFloat(value);
  }

  public static Float toFloatObject(@Nullable final Short value) {
    return (value == null ? null : toFloatObject(value.shortValue()));
  }

  public static Float toFloatObject(@Nullable final Short value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.shortValue()));
  }

  public static double toDouble(final short value) {
    return value;
  }

  public static double toDouble(@Nullable final Short value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.shortValue()));
  }

  public static double toDouble(@Nullable final Short value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.shortValue()));
  }

  public static Double toDoubleObject(final short value) {
    return toDouble(value);
  }

  public static Double toDoubleObject(@Nullable final Short value) {
    return (value == null ? null : toDoubleObject(value.shortValue()));
  }

  public static Double toDoubleObject(@Nullable final Short value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.shortValue()));
  }

  public static String toString(final short value) {
    return Integer.toString(value, DECIMAL_RADIX);
  }

  public static String toString(@Nullable final Short value) {
    return (value == null ? null : toString(value.shortValue()));
  }

  public static String toString(@Nullable final Short value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.shortValue()));
  }

  /**
   * Convert a {@code short} value into hex string.
   *
   * @param value
   *     the value to be converted.
   * @param builder
   *     a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final short value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    // stop checkstyle: MagicNumberCheck
    builder.append("0x")
           .append(digits[(value >>> 12) & 0x0F])
           .append(digits[(value >>> 8) & 0x0F])
           .append(digits[(value >>> 4) & 0x0F])
           .append(digits[value & 0x0F]);
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * Convert a {@code short} value into hex string.
   *
   * @param value
   *     the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final short value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final short value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Short value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final Short value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(final short value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final short value, final ByteOrder byteOrder) {
    final byte[] result = new byte[2];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> ByteBit.BITS);
      result[1] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[1] = (byte) (value >>> ByteBit.BITS);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
  }

  public static byte[] toByteArray(@Nullable final Short value) {
    return (value == null ? null : toByteArray(value.shortValue()));
  }

  public static byte[] toByteArray(@Nullable final Short value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.shortValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Short value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.shortValue()));
  }

  public static byte[] toByteArray(@Nullable final Short value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.shortValue(), byteOrder));
  }

  public static Class<?> toClass(final short value) {
    return Short.TYPE;
  }

  public static Class<?> toClass(@Nullable final Short value) {
    return (value == null ? null : Short.class);
  }

  public static Class<?> toClass(@Nullable final Short value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Short.class);
  }

  public static BigInteger toBigInteger(final short value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Short value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  public static BigInteger toBigInteger(@Nullable final Short value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  public static BigDecimal toBigDecimal(final short value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Short value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final Short value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code short}或{@code Short}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code short}或{@code Short}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }
}
