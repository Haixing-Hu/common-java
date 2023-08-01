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
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;
import static ltd.qubit.commons.text.NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;

/**
 * This class provides operations on {@code byte} primitives and
 * {@link Byte} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@code byte} values or
 * {@link Byte} objects to common types.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ByteUtils {

  /**
   * The default {@code byte} value used when necessary.
   */
  public static final byte DEFAULT = (byte) 0;

  /**
   * The maximum value of an unsigned byte.
   */
  public static final int UNSIGNED_MAX   = 0xFF;

  private ByteUtils() {}

  public static byte toPrimitive(@Nullable final Byte value) {
    return (value == null ? DEFAULT : value);
  }

  public static byte toPrimitive(@Nullable final Byte value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static boolean toBoolean(final byte value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Byte value) {
    return (value == null ? BooleanUtils.DEFAULT
                          : toBoolean(value.byteValue()));
  }

  public static boolean toBoolean(@Nullable final Byte value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.byteValue()));
  }

  public static Boolean toBooleanObject(final byte value) {
    return toBoolean(value);
  }

  public static Boolean toBooleanObject(@Nullable final Byte value) {
    return (value == null ? null : toBooleanObject(value.byteValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Byte value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.byteValue()));
  }

  public static char toChar(final byte value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Byte value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.byteValue()));
  }

  public static char toChar(@Nullable final Byte value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.byteValue()));
  }

  public static Character toCharObject(final byte value) {
    return toChar(value);
  }

  public static Character toCharObject(@Nullable final Byte value) {
    return (value == null ? null : toCharObject(value.byteValue()));
  }

  public static Character toCharObject(@Nullable final Byte value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.byteValue()));
  }

  public static short toShort(final byte value) {
    return value;
  }

  public static short toShort(@Nullable final Byte value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.byteValue()));
  }

  public static short toShort(@Nullable final Byte value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.byteValue()));
  }

  public static Short toShortObject(final byte value) {
    return toShort(value);
  }

  public static Short toShortObject(@Nullable final Byte value) {
    return (value == null ? null : toShortObject(value.byteValue()));
  }

  public static Short toShortObject(@Nullable final Byte value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.byteValue()));
  }

  public static int toInt(final byte value) {
    return value;
  }

  public static int toInt(@Nullable final Byte value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.byteValue()));
  }

  public static int toInt(@Nullable final Byte value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.byteValue()));
  }

  public static Integer toIntObject(final byte value) {
    return toInt(value);
  }

  public static Integer toIntObject(@Nullable final Byte value) {
    return (value == null ? null : toIntObject(value.byteValue()));
  }

  public static Integer toIntObject(@Nullable final Byte value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.byteValue()));
  }

  public static long toLong(final byte value) {
    return value;
  }

  public static long toLong(@Nullable final Byte value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.byteValue()));
  }

  public static long toLong(@Nullable final Byte value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.byteValue()));
  }

  public static Long toLongObject(final byte value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Byte value) {
    return (value == null ? null : toLongObject(value.byteValue()));
  }

  public static Long toLongObject(@Nullable final Byte value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.byteValue()));
  }

  public static float toFloat(final byte value) {
    return value;
  }

  public static float toFloat(@Nullable final Byte value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.byteValue()));
  }

  public static float toFloat(@Nullable final Byte value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.byteValue()));
  }

  public static Float toFloatObject(final byte value) {
    return toFloat(value);
  }

  public static Float toFloatObject(@Nullable final Byte value) {
    return (value == null ? null : toFloatObject(value.byteValue()));
  }

  public static Float toFloatObject(@Nullable final Byte value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.byteValue()));
  }

  public static double toDouble(final byte value) {
    return value;
  }

  public static double toDouble(@Nullable final Byte value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.byteValue()));
  }

  public static double toDouble(@Nullable final Byte value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.byteValue()));
  }

  public static Double toDoubleObject(final byte value) {
    return toDouble(value);
  }

  public static Double toDoubleObject(@Nullable final Byte value) {
    return (value == null ? null : toDoubleObject(value.byteValue()));
  }

  public static Double toDoubleObject(@Nullable final Byte value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.byteValue()));
  }

  public static String toString(final byte value) {
    return Integer.toString(value, DECIMAL_RADIX);
  }

  public static String toString(@Nullable final Byte value) {
    return (value == null ? null : toString(value.byteValue()));
  }

  public static String toString(@Nullable final Byte value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.byteValue()));
  }

  /**
   * Convert a {@code byte} value into hex string.
   *
   * @param value
   *     the value to be converted.
   * @param builder
   *     a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final byte value,
      final StringBuilder builder) {
    // stop checkstyle: MagicNumberCheck
    builder.append("0x")
           .append(DEFAULT_UPPERCASE_DIGITS[(value >>> 4) & 0x0F])
           .append(DEFAULT_UPPERCASE_DIGITS[value & 0x0F]);
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * Convert a {@code byte} value into hex string.
   *
   * @param value
   *          the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final byte value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final byte value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Byte value) {
    return (value == null ? null : new Date(value));
  }

  public static Date toDate(@Nullable final Byte value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value));
  }

  public static byte[] toByteArray(final byte value) {
    return new byte[]{value};
  }

  public static byte[] toByteArray(@Nullable final Byte value) {
    return (value == null ? null : toByteArray(value.byteValue()));
  }

  public static byte[] toByteArray(@Nullable final Byte value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.byteValue()));
  }

  public static Class<?> toClass(final byte value) {
    return Byte.TYPE;
  }

  public static Class<?> toClass(@Nullable final Byte value) {
    return (value == null ? null : Byte.class);
  }

  public static Class<?> toClass(@Nullable final Byte value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Byte.class);
  }

  public static BigInteger toBigInteger(final byte value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Byte value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  public static BigInteger toBigInteger(@Nullable final Byte value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  public static BigDecimal toBigDecimal(final byte value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Byte value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final Byte value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code byte}或{@code Byte}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code byte}或{@code Byte}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }
}
