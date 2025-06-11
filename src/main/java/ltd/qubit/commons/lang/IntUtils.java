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
 * 此类提供对 {@code int} 原始类型和 {@link Integer} 对象的操作。
 *
 * <p>此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法会详细说明其行为。
 *
 * <p>此类还处理从 {@code int} 值或 {@link Integer} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
public class IntUtils {

  /**
   * 必要时使用的默认 {@code int} 值。
   */
  public static final int DEFAULT = 0;

  /**
   * 无符号整数的最大值。
   */
  public static final int UNSIGNED_MAX  = 0xFFFFFFFF;

  /**
   * 将 {@link Integer} 对象转换为 {@code int} 原始类型。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link #DEFAULT}；否则返回
   *     {@code value} 的 {@code int} 值。
   */
  public static int toPrimitive(@Nullable final Integer value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code int} 原始类型。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；否则返回
   *     {@code value} 的 {@code int} 值。
   */
  public static int toPrimitive(@Nullable final Integer value,
      final int defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code int} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     如果 {@code value} 不等于 0，则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(final int value) {
    return (value != 0);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link BooleanUtils#DEFAULT}；
   *     否则将 {@code value} 转换为 {@code boolean} 值。
   */
  public static boolean toBoolean(@Nullable final Integer value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 转换为 {@code boolean} 值。
   */
  public static boolean toBoolean(@Nullable final Integer value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(final int value) {
    return toBoolean(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(@Nullable final Integer value) {
    return (value == null ? null : toBooleanObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(@Nullable final Integer value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@code char} 值。
   */
  public static char toChar(final int value) {
    return (char) value;
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link CharUtils#DEFAULT}；
   *     否则返回转换后的 {@code char} 值。
   */
  public static char toChar(@Nullable final Integer value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@code char} 值。
   */
  public static char toChar(@Nullable final Integer value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(final int value) {
    return toChar(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Integer value) {
    return (value == null ? null : toCharObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Integer value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@code byte} 值。
   */
  public static byte toByte(final int value) {
    return (byte) value;
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link ByteUtils#DEFAULT}；
   *     否则返回转换后的 {@code byte} 值。
   */
  public static byte toByte(@Nullable final Integer value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@code byte} 值。
   */
  public static byte toByte(@Nullable final Integer value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(final int value) {
    return toByte(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Integer value) {
    return (value == null ? null : toByteObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Integer value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@code short} 值。
   */
  public static short toShort(final int value) {
    return (short) value;
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link #DEFAULT}；
   *     否则返回转换后的 {@code short} 值。
   */
  public static short toShort(@Nullable final Integer value) {
    return (value == null ? DEFAULT : toShort(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@code short} 值。
   */
  public static short toShort(@Nullable final Integer value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(final int value) {
    return toShort(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Integer value) {
    return (value == null ? null : toShortObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Integer value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@code long} 值。
   */
  public static long toLong(final int value) {
    return value;
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link LongUtils#DEFAULT}；
   *     否则返回转换后的 {@code long} 值。
   */
  public static long toLong(@Nullable final Integer value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@code long} 值。
   */
  public static long toLong(@Nullable final Integer value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Long} 对象。
   */
  public static Long toLongObject(final int value) {
    return toLong(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Long} 对象。
   */
  public static Long toLongObject(@Nullable final Integer value) {
    return (value == null ? null : toLongObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Long} 对象。
   */
  public static Long toLongObject(@Nullable final Integer value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@code float} 值。
   */
  public static float toFloat(final int value) {
    return value;
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link FloatUtils#DEFAULT}；
   *     否则返回转换后的 {@code float} 值。
   */
  public static float toFloat(@Nullable final Integer value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@code float} 值。
   */
  public static float toFloat(@Nullable final Integer value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Float} 对象。
   */
  public static Float toFloatObject(final int value) {
    return toFloat(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Float} 对象。
   */
  public static Float toFloatObject(@Nullable final Integer value) {
    return (value == null ? null : toFloatObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Float} 对象。
   */
  public static Float toFloatObject(@Nullable final Integer value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@code double} 值。
   */
  public static double toDouble(final int value) {
    return value;
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@link DoubleUtils#DEFAULT}；
   *     否则返回转换后的 {@code double} 值。
   */
  public static double toDouble(@Nullable final Integer value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@code double} 值。
   */
  public static double toDouble(@Nullable final Integer value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(final int value) {
    return toDouble(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(@Nullable final Integer value) {
    return (value == null ? null : toDoubleObject(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(@Nullable final Integer value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的字符串。
   */
  public static String toString(final int value) {
    return Integer.toString(value, DECIMAL_RADIX);
  }

  /**
   * 将 {@link Integer} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的字符串。
   */
  public static String toString(@Nullable final Integer value) {
    return (value == null ? null : toString(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的字符串。
   */
  public static String toString(@Nullable final Integer value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.intValue()));
  }

  /**
   * 将 {@code int} 值转换为固定长度的字符串。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @param size
   *     字符串的长度，如果转换后的字符串长度不足，则在左侧补 '0'。
   * @return
   *     转换后的固定长度字符串。
   */
  public static String toFixSizeString(final int value, final int size) {
    return StringUtils.leftPad(toString(value), size, '0');
  }

  /**
   * 将 {@link Integer} 对象转换为固定长度的字符串。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param size
   *     字符串的长度，如果转换后的字符串长度不足，则在左侧补 '0'。
   * @return
   *     转换后的固定长度字符串。
   */
  public static String toFixSizeString(@Nullable final Integer value,
      final int size) {
    return StringUtils.leftPad(toString(value), size, '0');
  }

  /**
   * 将 {@code int} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @param builder
   *     用于追加十六进制字符串的 {@link StringBuilder}。
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
   * 将 {@code int} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @return
   *     该值的十六进制字符串。
   */
  public static String toHexString(final int value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  /**
   * 将 {@code int} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值，将作为毫秒数时间戳。
   * @return
   *     转换后的 {@link Date} 对象。
   */
  public static Date toDate(final int value) {
    return new Date(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Integer value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Integer value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  /**
   * 将 {@code int} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的字节数组。
   */
  public static byte[] toByteArray(final int value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将 {@code int} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @param byteOrder
   *     字节序。
   * @return
   *     转换后的字节数组。
   */
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

  /**
   * 将 {@link Integer} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Integer value) {
    return (value == null ? null : toByteArray(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param byteOrder
   *     字节序。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Integer value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.intValue(), byteOrder));
  }

  /**
   * 将 {@link Integer} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Integer value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.intValue()));
  }

  /**
   * 将 {@link Integer} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @param byteOrder
   *     字节序。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Integer value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : toByteArray(value.intValue(),
        byteOrder));
  }

  /**
   * 获取 {@code int} 值对应的类类型。
   *
   * @param value
   *     {@code int} 值。
   * @return
   *     {@code int} 原始类型的 {@link Class} 对象。
   */
  public static Class<?> toClass(final int value) {
    return Integer.TYPE;
  }

  /**
   * 获取 {@link Integer} 对象对应的类类型。
   *
   * @param value
   *     {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回 {@link Integer} 类的 {@link Class} 对象。
   */
  public static Class<?> toClass(@Nullable final Integer value) {
    return (value == null ? null : Integer.class);
  }

  /**
   * 获取 {@link Integer} 对象对应的类类型。
   *
   * @param value
   *     {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回 {@link Integer} 类的 {@link Class} 对象。
   */
  public static Class<?> toClass(@Nullable final Integer value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Integer.class);
  }

  /**
   * 将 {@code int} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(final int value) {
    return BigInteger.valueOf(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Integer value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Integer value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  /**
   * 将 {@code int} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code int} 值。
   * @return
   *     转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(final int value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Integer value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 {@link Integer} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Integer} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Integer value,
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
   * 测试指定的类型的值是否可以和{@code int}或{@code Integer}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code int}或{@code Integer}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(new ClassKey(type));
  }
}
