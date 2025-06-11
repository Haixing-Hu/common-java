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
import ltd.qubit.commons.math.IntBit;
import ltd.qubit.commons.text.NumberFormatSymbols;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;

/**
 * 此类提供对 {@code long} 基本类型和 {@link Long} 对象的操作。
 *
 * <p>此类尽力优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都详细记录了其行为。
 *
 * <p>此类还处理从 {@code long} 值或 {@link Long} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
public class LongUtils {

  /**
   * 必要时使用的默认 {@code long} 值。
   */
  public static final long DEFAULT = 0L;

  /**
   * 无符号长整数的最大值。
   */
  public static final long UNSIGNED_MAX  = 0xFFFFFFFFFFFFFFFFL;

  /**
   * 将 {@link Long} 对象转换为 {@code long} 基本类型。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link #DEFAULT}；
   *     否则返回 {@code value} 的基本类型值。
   */
  public static long toPrimitive(@Nullable final Long value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@code long} 基本类型。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 的基本类型值。
   */
  public static long toPrimitive(@Nullable final Long value,
      final long defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code long} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     如果 {@code value} 不为 0，返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(final long value) {
    return (value != 0);
  }

  /**
   * 将 {@link Long} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link BooleanUtils#DEFAULT}；
   *     否则返回转换后的 {@code boolean} 值。
   */
  public static boolean toBoolean(@Nullable final Long value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code boolean} 值。
   */
  public static boolean toBoolean(@Nullable final Long value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(final long value) {
    return toBoolean(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(@Nullable final Long value) {
    return (value == null ? null : toBooleanObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(@Nullable final Long value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@code char} 值。
   */
  public static char toChar(final long value) {
    return (char) value;
  }

  /**
   * 将 {@link Long} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link CharUtils#DEFAULT}；
   *     否则返回转换后的 {@code char} 值。
   */
  public static char toChar(@Nullable final Long value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code char} 值。
   */
  public static char toChar(@Nullable final Long value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(final long value) {
    return toChar(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Long value) {
    return (value == null ? null : toCharObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Long value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@code byte} 值。
   */
  public static byte toByte(final long value) {
    return (byte) value;
  }

  /**
   * 将 {@link Long} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link ByteUtils#DEFAULT}；
   *     否则返回转换后的 {@code byte} 值。
   */
  public static byte toByte(@Nullable final Long value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code byte} 值。
   */
  public static byte toByte(@Nullable final Long value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(final long value) {
    return toByte(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Long value) {
    return (value == null ? null : toByteObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Long value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@code short} 值。
   */
  public static short toShort(final long value) {
    return (short) value;
  }

  /**
   * 将 {@link Long} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回转换后的 {@code short} 值。
   */
  public static short toShort(@Nullable final Long value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code short} 值。
   */
  public static short toShort(@Nullable final Long value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(final long value) {
    return toShort(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Long value) {
    return (value == null ? null : toShortObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Long value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@code int} 值。
   */
  public static int toInt(final long value) {
    return (int) value;
  }

  /**
   * 将 {@link Long} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回转换后的 {@code int} 值。
   */
  public static int toInt(@Nullable final Long value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code int} 值。
   */
  public static int toInt(@Nullable final Long value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Integer} 对象。
   */
  public static Integer toIntObject(final long value) {
    return toInt(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Integer} 对象。
   */
  public static Integer toIntObject(@Nullable final Long value) {
    return (value == null ? null : toIntObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Integer} 对象。
   */
  public static Integer toIntObject(@Nullable final Long value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@code float} 值。
   */
  public static float toFloat(final long value) {
    return value;
  }

  /**
   * 将 {@link Long} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link FloatUtils#DEFAULT}；
   *     否则返回转换后的 {@code float} 值。
   */
  public static float toFloat(@Nullable final Long value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code float} 值。
   */
  public static float toFloat(@Nullable final Long value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Float} 对象。
   */
  public static Float toFloatObject(final long value) {
    return toFloat(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Float} 对象。
   */
  public static Float toFloatObject(@Nullable final Long value) {
    return (value == null ? null : toFloatObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Float} 对象。
   */
  public static Float toFloatObject(@Nullable final Long value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@code double} 值。
   */
  public static double toDouble(final long value) {
    return value;
  }

  /**
   * 将 {@link Long} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link DoubleUtils#DEFAULT}；
   *     否则返回转换后的 {@code double} 值。
   */
  public static double toDouble(@Nullable final Long value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@code double} 值。
   */
  public static double toDouble(@Nullable final Long value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(final long value) {
    return toDouble(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(@Nullable final Long value) {
    return (value == null ? null : toDoubleObject(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(@Nullable final Long value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的字符串。
   */
  public static String toString(final long value) {
    return Long.toString(value, DECIMAL_RADIX);
  }

  /**
   * 将 {@link Long} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的字符串。
   */
  public static String toString(@Nullable final Long value) {
    return (value == null ? null : toString(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的字符串。
   */
  public static String toString(@Nullable final Long value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.longValue()));
  }

  /**
   * 将 {@code long} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @param builder
   *     用于追加十六进制字符串的 {@link StringBuilder}。
   */
  public static void toHexString(final long value, final StringBuilder builder) {
    final char[] digits = NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;
    final int high = (int) (value >>> IntBit.BITS);
    final int low  = (int) value;
    // stop checkstyle: MagicNumberCheck
    builder.append("0x")
           .append(digits[(high >>> 28) & 0x0F])
           .append(digits[(high >>> 24) & 0x0F])
           .append(digits[(high >>> 20) & 0x0F])
           .append(digits[(high >>> 16) & 0x0F])
           .append(digits[(high >>> 12) & 0x0F])
           .append(digits[(high >>> 8) & 0x0F])
           .append(digits[(high >>> 4) & 0x0F])
           .append(digits[high & 0x0F])
           .append(digits[(low >>> 28) & 0x0F])
           .append(digits[(low >>> 24) & 0x0F])
           .append(digits[(low >>> 20) & 0x0F])
           .append(digits[(low >>> 16) & 0x0F])
           .append(digits[(low >>> 12) & 0x0F])
           .append(digits[(low >>> 8) & 0x0F])
           .append(digits[(low >>> 4) & 0x0F])
           .append(digits[low & 0x0F]);
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 将 {@code long} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @return
   *     值的十六进制字符串。
   */
  public static String toHexString(final long value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  /**
   * 将 {@code long} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值，表示时间戳。
   * @return
   *     转换后的 {@link Date} 对象。
   */
  public static Date toDate(final long value) {
    return new Date(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}，表示时间戳。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Long value) {
    return (value == null ? null : new Date(value));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}，表示时间戳。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Long value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value));
  }

  /**
   * 将 {@code long} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的字节数组。
   */
  public static byte[] toByteArray(final long value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将 {@code long} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @param byteOrder
   *     字节序。
   * @return
   *     转换后的字节数组。
   */
  public static byte[] toByteArray(final long value, final ByteOrder byteOrder) {
    // stop checkstyle: MagicNumberCheck
    final byte[] result = new byte[8];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> 56);
      result[1] = (byte) (value >>> 48);
      result[2] = (byte) (value >>> 40);
      result[3] = (byte) (value >>> 32);
      result[4] = (byte) (value >>> 24);
      result[5] = (byte) (value >>> 16);
      result[6] = (byte) (value >>> 8);
      result[7] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[7] = (byte) (value >>> 56);
      result[6] = (byte) (value >>> 48);
      result[5] = (byte) (value >>> 40);
      result[4] = (byte) (value >>> 32);
      result[3] = (byte) (value >>> 24);
      result[2] = (byte) (value >>> 16);
      result[1] = (byte) (value >>> 8);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 将 {@link Long} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Long value) {
    return (value == null ? null : toByteArray(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param byteOrder
   *     字节序。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Long value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.longValue(), byteOrder));
  }

  /**
   * 将 {@link Long} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Long value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.longValue()));
  }

  /**
   * 将 {@link Long} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @param byteOrder
   *     字节序。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Long value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.longValue(),
        byteOrder));
  }

  /**
   * 将 {@code long} 值转换为相应的 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     {@code long} 基本类型对应的 {@link Class} 对象。
   */
  public static Class<?> toClass(final long value) {
    return Long.TYPE;
  }

  /**
   * 将 {@link Long} 对象转换为相应的 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@link Long} 类对应的 {@link Class} 对象。
   */
  public static Class<?> toClass(@Nullable final Long value) {
    return (value == null ? null : Long.class);
  }

  /**
   * 将 {@link Long} 对象转换为相应的 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@link Long} 类对应的 {@link Class} 对象。
   */
  public static Class<?> toClass(@Nullable final Long value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Long.class);
  }

  /**
   * 将 {@code long} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(final long value) {
    return BigInteger.valueOf(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Long value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Long value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  /**
   * 将 {@code long} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code long} 值。
   * @return
   *     转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(final long value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 {@link Long} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Long value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 {@link Long} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Long} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时使用的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Long value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  private static final Set<ClassKey> COMPARABLE_TYPES =
      ImmutableSet.of(new ClassKey(boolean.class),
          new ClassKey(byte.class),
          new ClassKey(short.class),
          new ClassKey(int.class),
          new ClassKey(long.class),
          new ClassKey(float.class),
          new ClassKey(double.class),
          new ClassKey(Boolean.class),
          new ClassKey(Byte.class),
          new ClassKey(Short.class),
          new ClassKey(Integer.class),
          new ClassKey(Long.class),
          new ClassKey(BigInteger.class),
          new ClassKey(Float.class),
          new ClassKey(Double.class),
          new ClassKey(BigDecimal.class));

  /**
   * 测试指定的类型的值是否可以和{@code long}或{@code Long}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code long}或{@code Long}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(new ClassKey(type));
  }
}
