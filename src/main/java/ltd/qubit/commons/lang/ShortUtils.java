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
import ltd.qubit.commons.math.ByteBit;
import ltd.qubit.commons.text.NumberFormatSymbols;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;

/**
 * 此类提供对 {@code short} 基本类型和 {@link Short} 对象的操作。
 * <p>
 * 此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都详细记录了其行为。
 * </p>
 * <p>
 * 此类还处理从 {@code short} 值或 {@link Short} 对象到常见类型的转换。
 * </p>
 *
 * @author 胡海星
 */
public class ShortUtils {

  /**
   * 必要时使用的默认 {@code short} 值。
   */
  public static final short DEFAULT = (short) 0;

  /**
   * 无符号 short 的最大值。
   */
  public static final int UNSIGNED_MAX  = 0xFFFF;

  /**
   * 将 {@link Short} 对象转换为 {@code short} 基本类型，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link #DEFAULT}；否则返回 {@code value.shortValue()}
   */
  public static short toPrimitive(@Nullable final Short value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@code short} 基本类型，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回 {@code value.shortValue()}
   */
  public static short toPrimitive(@Nullable final Short value,
      final short defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code short} 值转换为 {@code boolean}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     如果 {@code value} 不等于 0，返回 {@code true}；否则返回 {@code false}
   */
  public static boolean toBoolean(final short value) {
    return (value != 0);
  }

  /**
   * 将 {@link Short} 对象转换为 {@code boolean}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link BooleanUtils#DEFAULT}；
   *     否则如果 {@code value.shortValue()} 不等于 0，返回 {@code true}；否则返回 {@code false}
   */
  public static boolean toBoolean(@Nullable final Short value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code boolean}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；
   *     否则如果 {@code value.shortValue()} 不等于 0，返回 {@code true}；否则返回 {@code false}
   */
  public static boolean toBoolean(@Nullable final Short value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     如果 {@code value} 不等于 0，返回 {@link Boolean#TRUE}；否则返回 {@link Boolean#FALSE}
   */
  public static Boolean toBooleanObject(final short value) {
    return toBoolean(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；
   *     否则如果 {@code value.shortValue()} 不等于 0，返回 {@link Boolean#TRUE}；否则返回 {@link Boolean#FALSE}
   */
  public static Boolean toBooleanObject(@Nullable final Short value) {
    return (value == null ? null : toBooleanObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Boolean} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；
   *     否则如果 {@code value.shortValue()} 不等于 0，返回 {@link Boolean#TRUE}；否则返回 {@link Boolean#FALSE}
   */
  public static Boolean toBooleanObject(@Nullable final Short value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@code char}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@code char} 值
   */
  public static char toChar(final short value) {
    return (char) value;
  }

  /**
   * 将 {@link Short} 对象转换为 {@code char}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link CharUtils#DEFAULT}；否则返回转换后的 {@code char} 值
   */
  public static char toChar(@Nullable final Short value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code char}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@code char} 值
   */
  public static char toChar(@Nullable final Short value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link Character} 对象
   */
  public static Character toCharObject(final short value) {
    return toChar(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Character} 对象
   */
  public static Character toCharObject(@Nullable final Short value) {
    return (value == null ? null : toCharObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Character} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Character} 对象
   */
  public static Character toCharObject(@Nullable final Short value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@code byte}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@code byte} 值
   */
  public static byte toByte(final short value) {
    return (byte) value;
  }

  /**
   * 将 {@link Short} 对象转换为 {@code byte}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link ByteUtils#DEFAULT}；否则返回转换后的 {@code byte} 值
   */
  public static byte toByte(@Nullable final Short value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code byte}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@code byte} 值
   */
  public static byte toByte(@Nullable final Short value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link Byte} 对象
   */
  public static Byte toByteObject(final short value) {
    return toByte(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Byte} 对象
   */
  public static Byte toByteObject(@Nullable final Short value) {
    return (value == null ? null : toByteObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Byte} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Byte} 对象
   */
  public static Byte toByteObject(@Nullable final Short value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@code int}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@code int} 值
   */
  public static int toInt(final short value) {
    return value;
  }

  /**
   * 将 {@link Short} 对象转换为 {@code int}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link IntUtils#DEFAULT}；否则返回转换后的 {@code int} 值
   */
  public static int toInt(@Nullable final Short value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code int}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@code int} 值
   */
  public static int toInt(@Nullable final Short value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link Integer} 对象
   */
  public static Integer toIntObject(final short value) {
    return toInt(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Integer} 对象
   */
  public static Integer toIntObject(@Nullable final Short value) {
    return (value == null ? null : toIntObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Integer} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Integer} 对象
   */
  public static Integer toIntObject(@Nullable final Short value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@code long}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@code long} 值
   */
  public static long toLong(final short value) {
    return value;
  }

  /**
   * 将 {@link Short} 对象转换为 {@code long}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link LongUtils#DEFAULT}；否则返回转换后的 {@code long} 值
   */
  public static long toLong(@Nullable final Short value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code long}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@code long} 值
   */
  public static long toLong(@Nullable final Short value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link Long} 对象
   */
  public static Long toLongObject(final short value) {
    return toLong(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Long} 对象
   */
  public static Long toLongObject(@Nullable final Short value) {
    return (value == null ? null : toLongObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Long} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Long} 对象
   */
  public static Long toLongObject(@Nullable final Short value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@code float}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@code float} 值
   */
  public static float toFloat(final short value) {
    return value;
  }

  /**
   * 将 {@link Short} 对象转换为 {@code float}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link FloatUtils#DEFAULT}；否则返回转换后的 {@code float} 值
   */
  public static float toFloat(@Nullable final Short value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code float}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@code float} 值
   */
  public static float toFloat(@Nullable final Short value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link Float} 对象
   */
  public static Float toFloatObject(final short value) {
    return toFloat(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Float} 对象
   */
  public static Float toFloatObject(@Nullable final Short value) {
    return (value == null ? null : toFloatObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Float} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Float} 对象
   */
  public static Float toFloatObject(@Nullable final Short value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@code double}。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@code double} 值
   */
  public static double toDouble(final short value) {
    return value;
  }

  /**
   * 将 {@link Short} 对象转换为 {@code double}，使用默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@link DoubleUtils#DEFAULT}；否则返回转换后的 {@code double} 值
   */
  public static double toDouble(@Nullable final Short value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@code double}，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@code double} 值
   */
  public static double toDouble(@Nullable final Short value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link Double} 对象
   */
  public static Double toDoubleObject(final short value) {
    return toDouble(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Double} 对象
   */
  public static Double toDoubleObject(@Nullable final Short value) {
    return (value == null ? null : toDoubleObject(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Double} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Double} 对象
   */
  public static Double toDoubleObject(@Nullable final Short value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的字符串
   */
  public static String toString(final short value) {
    return Integer.toString(value, DECIMAL_RADIX);
  }

  /**
   * 将 {@link Short} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的字符串
   */
  public static String toString(@Nullable final Short value) {
    return (value == null ? null : toString(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为字符串，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的字符串
   */
  public static String toString(@Nullable final Short value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.shortValue()));
  }

  /**
   * 将 {@code short} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的值
   * @param builder
   *     用于追加十六进制字符串的 {@link StringBuilder}
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
   * 将 {@code short} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的值
   * @return 值的十六进制字符串
   */
  public static String toHexString(final short value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  /**
   * 将 {@code short} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值，表示自1970年1月1日00:00:00 GMT以来的毫秒数
   * @return
   *     转换后的 {@link Date} 对象
   */
  public static Date toDate(final short value) {
    return new Date(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}，表示自1970年1月1日00:00:00 GMT以来的毫秒数
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link Date} 对象
   */
  public static Date toDate(@Nullable final Short value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link Date} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}，表示自1970年1月1日00:00:00 GMT以来的毫秒数
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link Date} 对象
   */
  public static Date toDate(@Nullable final Short value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  /**
   * 将 {@code short} 值转换为字节数组，使用默认字节序。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的字节数组
   */
  public static byte[] toByteArray(final short value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将 {@code short} 值转换为字节数组，使用指定的字节序。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @param byteOrder
   *     字节序
   * @return
   *     转换后的字节数组
   * @throws UnsupportedByteOrderException
   *     如果字节序不受支持
   */
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

  /**
   * 将 {@link Short} 对象转换为字节数组，使用默认字节序。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Short value) {
    return (value == null ? null : toByteArray(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为字节数组，使用指定的字节序。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param byteOrder
   *     字节序
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Short value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.shortValue(), byteOrder));
  }

  /**
   * 将 {@link Short} 对象转换为字节数组，使用默认字节序和指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Short value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.shortValue()));
  }

  /**
   * 将 {@link Short} 对象转换为字节数组，使用指定的字节序和默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @param byteOrder
   *     字节序
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Short value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.shortValue(), byteOrder));
  }

  /**
   * 获取 {@code short} 基本类型对应的 {@link Class} 对象。
   *
   * @param value
   *     {@code short} 值
   * @return
   *     {@code short} 基本类型的 {@link Class} 对象，即 {@code Short.TYPE}
   */
  public static Class<?> toClass(final short value) {
    return Short.TYPE;
  }

  /**
   * 获取 {@link Short} 对象对应的 {@link Class} 对象。
   *
   * @param value
   *     {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回 {@code Short.class}
   */
  public static Class<?> toClass(@Nullable final Short value) {
    return (value == null ? null : Short.class);
  }

  /**
   * 获取 {@link Short} 对象对应的 {@link Class} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回 {@code Short.class}
   */
  public static Class<?> toClass(@Nullable final Short value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Short.class);
  }

  /**
   * 将 {@code short} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link BigInteger} 对象
   */
  public static BigInteger toBigInteger(final short value) {
    return BigInteger.valueOf(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link BigInteger} 对象
   */
  public static BigInteger toBigInteger(@Nullable final Short value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link BigInteger} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link BigInteger} 对象
   */
  public static BigInteger toBigInteger(@Nullable final Short value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  /**
   * 将 {@code short} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code short} 值
   * @return
   *     转换后的 {@link BigDecimal} 对象
   */
  public static BigDecimal toBigDecimal(final short value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 {@link Short} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @return
   *     如果输入为 {@code null}，返回 {@code null}；否则返回转换后的 {@link BigDecimal} 对象
   */
  public static BigDecimal toBigDecimal(@Nullable final Short value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 {@link Short} 对象转换为 {@link BigDecimal} 对象，使用指定的默认值处理 {@code null}。
   *
   * @param value
   *     要转换的 {@link Short} 对象，可以为 {@code null}
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值
   * @return
   *     如果输入为 {@code null}，返回 {@code defaultValue}；否则返回转换后的 {@link BigDecimal} 对象
   */
  public static BigDecimal toBigDecimal(@Nullable final Short value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  /**
   * 可与 {@code short} 或 {@code Short} 类型进行比较的类型集合。
   */
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
   * 测试指定的类型的值是否可以和{@code short}或{@code Short}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code short}或{@code Short}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(new ClassKey(type));
  }
}