////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.ByteOrder;
import java.util.Currency;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * This class provides operations on {@code double} primitives and
 * {@link Double} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@code double} values or
 * {@link Double} objects to common types.
 *
 * @author Haixing Hu
 */
public class DoubleUtils {

  /**
   * The default {@code double} value used when necessary.
   */
  public static final double DEFAULT = 0.0;

  /**
   * The default epsilon for {@code double} values.
   */
  public static final double EPSILON = 0.0000001;

  public static double toPrimitive(@Nullable final Double value) {
    return (value == null ? DEFAULT : value);
  }

  public static double toPrimitive(@Nullable final Double value,
      final double defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static boolean toBoolean(final double value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Double value) {
    return (value == null ? BooleanUtils.DEFAULT
                          : toBoolean(value.doubleValue()));
  }

  public static boolean toBoolean(@Nullable final Double value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.doubleValue()));
  }

  public static Boolean toBooleanObject(final double value) {
    return toBoolean(value);
  }

  public static Boolean toBooleanObject(@Nullable final Double value) {
    return (value == null ? null : toBooleanObject(value.doubleValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Double value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : toBooleanObject(value.doubleValue()));
  }

  public static char toChar(final double value) {
    return (char) value;
  }

  public static char toChar(@Nullable final Double value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.doubleValue()));
  }

  public static char toChar(@Nullable final Double value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.doubleValue()));
  }

  public static Character toCharObject(final double value) {
    return toChar(value);
  }

  public static Character toCharObject(@Nullable final Double value) {
    return (value == null ? null : toCharObject(value.doubleValue()));
  }

  public static Character toCharObject(@Nullable final Double value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.doubleValue()));
  }

  public static byte toByte(final double value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Double value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.doubleValue()));
  }

  public static byte toByte(@Nullable final Double value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.doubleValue()));
  }

  public static Byte toByteObject(final double value) {
    return toByte(value);
  }

  public static Byte toByteObject(@Nullable final Double value) {
    return (value == null ? null : toByteObject(value.doubleValue()));
  }

  public static Byte toByteObject(@Nullable final Double value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.doubleValue()));
  }

  public static short toShort(final double value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Double value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.doubleValue()));
  }

  public static short toShort(@Nullable final Double value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.doubleValue()));
  }

  public static Short toShortObject(final double value) {
    return toShort(value);
  }

  public static Short toShortObject(@Nullable final Double value) {
    return (value == null ? null : toShortObject(value.doubleValue()));
  }

  public static Short toShortObject(@Nullable final Double value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.doubleValue()));
  }

  public static int toInt(final double value) {
    return (int) value;
  }

  public static int toInt(@Nullable final Double value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.doubleValue()));
  }

  public static int toInt(@Nullable final Double value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.doubleValue()));
  }

  public static Integer toIntObject(final double value) {
    return toInt(value);
  }

  public static Integer toIntObject(@Nullable final Double value) {
    return (value == null ? null : toIntObject(value.doubleValue()));
  }

  public static Integer toIntObject(@Nullable final Double value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.doubleValue()));
  }

  public static long toLong(final double value) {
    return (long) value;
  }

  public static long toLong(@Nullable final Double value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.doubleValue()));
  }

  public static long toLong(@Nullable final Double value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.doubleValue()));
  }

  public static Long toLongObject(final double value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Double value) {
    return (value == null ? null : toLongObject(value.doubleValue()));
  }

  public static Long toLongObject(@Nullable final Double value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.doubleValue()));
  }

  public static float toFloat(final double value) {
    return (float) value;
  }

  public static float toFloat(@Nullable final Double value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.doubleValue()));
  }

  public static float toFloat(@Nullable final Double value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.doubleValue()));
  }

  public static Float toFloatObject(final double value) {
    return toFloat(value);
  }

  public static Float toFloatObject(@Nullable final Double value) {
    return (value == null ? null : toFloatObject(value.doubleValue()));
  }

  public static Float toFloatObject(@Nullable final Double value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.doubleValue()));
  }

  public static String toString(final double value) {
    return Double.toString(value);
  }

  public static String toString(@Nullable final Double value) {
    return (value == null ? null : toString(value.doubleValue()));
  }

  public static String toString(@Nullable final Double value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.doubleValue()));
  }

  public static Date toDate(final double value) {
    return new Date((long) value);
  }

  public static Date toDate(@Nullable final Double value) {
    return (value == null ? null : toDate(value.doubleValue()));
  }

  public static Date toDate(@Nullable final Double value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.doubleValue()));
  }

  public static byte[] toByteArray(final double value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  public static byte[] toByteArray(final double value,
      final ByteOrder byteOrder) {
    final long bits = Double.doubleToLongBits(value);
    return LongUtils.toByteArray(bits, byteOrder);
  }

  public static byte[] toByteArray(@Nullable final Double value) {
    return (value == null ? null : toByteArray(value.doubleValue()));
  }

  public static byte[] toByteArray(@Nullable final Double value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.doubleValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Double value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.doubleValue()));
  }

  public static byte[] toByteArray(@Nullable final Double value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.doubleValue(), byteOrder));
  }

  public static Class<?> toClass(final double value) {
    return Double.TYPE;
  }

  public static Class<?> toClass(@Nullable final Double value) {
    return (value == null ? null : Double.class);
  }

  public static Class<?> toClass(@Nullable final Double value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Double.class);
  }

  public static BigInteger toBigInteger(final double value) {
    return BigInteger.valueOf((long) value);
  }

  public static BigInteger toBigInteger(@Nullable final Double value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Double value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue
                          : BigInteger.valueOf(value.longValue()));
  }

  public static BigDecimal toBigDecimal(final double value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Double value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final Double value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code double}或{@code Double}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code double}或{@code Double}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }

  /**
   * 将指定的浮点数值四舍五入为指定精度的字符串表示。
   *
   * @param value
   *     指定的浮点数值。
   * @param precision
   *     指定的精度，即小数点后保留的位数。
   * @return
   *     四舍五入为指定精度的字符串表示。
   */
  public static String roundToString(final double value, final int precision) {
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      return Double.toString(value);
    } else {
      final BigDecimal bd = new BigDecimal(value);
      return bd.setScale(precision, RoundingMode.HALF_UP).toPlainString();
    }
  }

  /**
   * 将指定的浮点数值四舍五入为指定精度的字符串表示。
   *
   * @param value
   *     指定的浮点数值。
   * @param defaultValue
   *     如果{@code value}为{@code null}，则返回此默认值。
   * @param precision
   *     指定的精度，即小数点后保留的位数。
   * @return
   *     {@code value}四舍五入为指定精度的字符串表示，如果{@code value}为{@code null}
   *     则返回{@code defaultValue}。
   */
  @Nullable
  public static String roundToString(@Nullable final Double value,
      @Nullable final String defaultValue, final int precision) {
    if (value == null) {
      return defaultValue;
    } else if (Double.isNaN(value) || Double.isInfinite(value)) {
      return Double.toString(value);
    } else {
      return roundToString(value, precision);
    }
  }

  /**
   * Formats the specified amount of money.
   *
   * @param currency
   *     the currency of the money. If {@code null}, the currency symbol will
   *     not be ignored.
   * @param amount
   *     the amount of money.
   * @return
   *     the formatted money.
   */
  public static String formatMoney(@Nullable final Currency currency,
      final double amount) {
    return formatMoney(currency, amount, 2);
  }

  /**
   * Formats the specified amount of money.
   *
   * @param currency
   *     the currency of the money. If {@code null}, the currency symbol will
   *     not be ignored.
   * @param amount
   *     the amount of money.
   * @param precision
   *     the precision of the money value, i.e., the number of digits after the
   *     decimal point.
   * @return
   *     the formatted money.
   */
  public static String formatMoney(@Nullable final Currency currency,
      final double amount, final int precision) {
    return (currency == null ? "" : currency.getSymbol() + " ")
        + roundToString(amount, precision);
  }
}
