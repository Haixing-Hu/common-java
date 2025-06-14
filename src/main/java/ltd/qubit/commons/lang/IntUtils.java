////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import ltd.qubit.commons.error.UnsupportedByteOrderException;
import ltd.qubit.commons.text.NumberFormatSymbols;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;

/**
 * This class provides operations on {@code int} primitives and
 * {@link Integer} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@code int} values or
 * {@link Integer} objects to common types.
 *
 * @author Haixing Hu
 */
public class IntUtils {

  /**
   * The default {@code int} value used when necessary.
   */
  public static final int DEFAULT = 0;

  /**
   * The maximum value of an unsigned int.
   */
  public static final int UNSIGNED_MAX  = 0xFFFFFFFF;

  public static int toPrimitive(@Nullable final Integer value) {
    return (value == null ? DEFAULT : value);
  }

  public static int toPrimitive(@Nullable final Integer value,
      final int defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static boolean toBoolean(final int value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Integer value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.intValue()));
  }

  public static boolean toBoolean(@Nullable final Integer value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.intValue()));
  }

  public static Boolean toBooleanObject(final int value) {
    return toBoolean(value);
  }

  public static Boolean toBooleanObject(@Nullable final Integer value) {
    return (value == null ? null : toBooleanObject(value.intValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Integer value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.intValue()));
  }

  public static char toChar(final int value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Integer value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.intValue()));
  }

  public static char toChar(@Nullable final Integer value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.intValue()));
  }

  public static Character toCharObject(final int value) {
    return toChar(value);
  }

  public static Character toCharObject(@Nullable final Integer value) {
    return (value == null ? null : toCharObject(value.intValue()));
  }

  public static Character toCharObject(@Nullable final Integer value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.intValue()));
  }

  public static byte toByte(final int value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Integer value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.intValue()));
  }

  public static byte toByte(@Nullable final Integer value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.intValue()));
  }

  public static Byte toByteObject(final int value) {
    return toByte(value);
  }

  public static Byte toByteObject(@Nullable final Integer value) {
    return (value == null ? null : toByteObject(value.intValue()));
  }

  public static Byte toByteObject(@Nullable final Integer value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.intValue()));
  }

  public static short toShort(final int value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Integer value) {
    return (value == null ? DEFAULT : toShort(value.intValue()));
  }

  public static short toShort(@Nullable final Integer value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.intValue()));
  }

  public static Short toShortObject(final int value) {
    return toShort(value);
  }

  public static Short toShortObject(@Nullable final Integer value) {
    return (value == null ? null : toShortObject(value.intValue()));
  }

  public static Short toShortObject(@Nullable final Integer value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.intValue()));
  }

  public static long toLong(final int value) {
    return value;
  }

  public static long toLong(@Nullable final Integer value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.intValue()));
  }

  public static long toLong(@Nullable final Integer value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.intValue()));
  }

  public static Long toLongObject(final int value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Integer value) {
    return (value == null ? null : toLongObject(value.intValue()));
  }

  public static Long toLongObject(@Nullable final Integer value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.intValue()));
  }

  public static float toFloat(final int value) {
    return value;
  }

  public static float toFloat(@Nullable final Integer value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.intValue()));
  }

  public static float toFloat(@Nullable final Integer value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.intValue()));
  }

  public static Float toFloatObject(final int value) {
    return toFloat(value);
  }

  public static Float toFloatObject(@Nullable final Integer value) {
    return (value == null ? null : toFloatObject(value.intValue()));
  }

  public static Float toFloatObject(@Nullable final Integer value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.intValue()));
  }

  public static double toDouble(final int value) {
    return value;
  }

  public static double toDouble(@Nullable final Integer value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.intValue()));
  }

  public static double toDouble(@Nullable final Integer value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.intValue()));
  }

  public static Double toDoubleObject(final int value) {
    return toDouble(value);
  }

  public static Double toDoubleObject(@Nullable final Integer value) {
    return (value == null ? null : toDoubleObject(value.intValue()));
  }

  public static Double toDoubleObject(@Nullable final Integer value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.intValue()));
  }

  public static String toString(final int value) {
    return Integer.toString(value, DECIMAL_RADIX);
  }

  public static String toString(@Nullable final Integer value) {
    return (value == null ? null : toString(value.intValue()));
  }

  public static String toString(@Nullable final Integer value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.intValue()));
  }

  public static String toFixSizeString(final int value, final int size) {
    return StringUtils.leftPad(toString(value), size, '0');
  }

  public static String toFixSizeString(@Nullable final Integer value,
      final int size) {
    return StringUtils.leftPad(toString(value), size, '0');
  }

  /**
   * Convert a {@code int} value into hex string.
   *
   * @param value
   *     the value to be converted.
   * @param builder
   *     a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final int value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    //  stop checkstyle: MagicNumberCheck
    builder.append("0x")
           .append(digits[(value >>> 28) & 0x0F])
           .append(digits[(value >>> 24) & 0x0F])
           .append(digits[(value >>> 20) & 0x0F])
           .append(digits[(value >>> 16) & 0x0F])
           .append(digits[(value >>> 12) & 0x0F])
           .append(digits[(value >>> 8) & 0x0F])
           .append(digits[(value >>> 4) & 0x0F])
           .append(digits[value & 0x0F]);
    //  resume checkstyle: MagicNumberCheck
  }

  /**
   * Convert a {@code int} value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final int value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final int value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Integer value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final Integer value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(final int value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final int value, final ByteOrder byteOrder) {
    //  stop checkstyle: MagicNumberCheck
    final byte[] result = new byte[4];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> 24);
      result[1] = (byte) (value >>> 16);
      result[2] = (byte) (value >>> 8);
      result[3] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[3] = (byte) (value >>> 24);
      result[2] = (byte) (value >>> 16);
      result[1] = (byte) (value >>> 8);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    //  resume checkstyle: MagicNumberCheck
    return result;
  }

  public static byte[] toByteArray(@Nullable final Integer value) {
    return (value == null ? null : toByteArray(value.intValue()));
  }

  public static byte[] toByteArray(@Nullable final Integer value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.intValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Integer value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.intValue()));
  }

  public static byte[] toByteArray(@Nullable final Integer value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.intValue(),
        byteOrder));
  }

  public static Class<?> toClass(final int value) {
    return Integer.TYPE;
  }

  public static Class<?> toClass(@Nullable final Integer value) {
    return (value == null ? null : Integer.class);
  }

  public static Class<?> toClass(@Nullable final Integer value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Integer.class);
  }

  public static BigInteger toBigInteger(final int value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Integer value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  public static BigInteger toBigInteger(@Nullable final Integer value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  public static BigDecimal toBigDecimal(final int value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Integer value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final Integer value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code int}或{@code Integer}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code int}或{@code Integer}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }
}