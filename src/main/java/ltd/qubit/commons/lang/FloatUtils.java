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
import java.math.RoundingMode;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * 此类提供对 {@code float} 基本类型和 {@link Float} 对象的操作。
 *
 * <p>此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都在更详细的文档中记录了其行为。
 *
 * <p>此类还处理从 {@code float} 值或 {@link Float} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
public class FloatUtils {

  /**
   * 必要时使用的默认 {@code float} 值。
   */
  public static final float DEFAULT = 0.0f;

  /**
   * {@code float} 值的默认 epsilon。
   */
  public static final float EPSILON = 0.0001f;

  /**
   * 将 {@link Float} 对象转换为 {@code float} 基本类型。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link #DEFAULT}；否则返回 {@code value} 的值。
   */
  public static float toPrimitive(@Nullable final Float value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@code float} 基本类型。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；否则返回 {@code value} 的值。
   */
  public static float toPrimitive(@Nullable final Float value,
      final float defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code float} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     如果 {@code value} 不等于 0，返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(final float value) {
    return (value != 0);
  }

  /**
   * 将 {@link Float} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link BooleanUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code boolean} 值。
   */
  public static boolean toBoolean(@Nullable final Float value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code boolean} 值。
   */
  public static boolean toBoolean(@Nullable final Float value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(final float value) {
    return toBoolean(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(@Nullable final Float value) {
    return (value == null ? null : toBooleanObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Boolean} 对象。
   */
  public static Boolean toBooleanObject(@Nullable final Float value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@code char} 值。
   */
  public static char toChar(final float value) {
    return (char) value;
  }

  /**
   * 将 {@link Float} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link CharUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code char} 值。
   */
  public static char toChar(@Nullable final Float value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code char} 值。
   */
  public static char toChar(@Nullable final Float value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(final float value) {
    return toChar(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Float value) {
    return (value == null ? null : toCharObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Float value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@code byte} 值。
   */
  public static byte toByte(final float value) {
    return (byte) value;
  }

  /**
   * 将 {@link Float} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link ByteUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code byte} 值。
   */
  public static byte toByte(@Nullable final Float value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code byte} 值。
   */
  public static byte toByte(@Nullable final Float value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(final float value) {
    return toByte(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Float value) {
    return (value == null ? null : toByteObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Float value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@code short} 值。
   */
  public static short toShort(final float value) {
    return (short) value;
  }

  /**
   * 将 {@link Float} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code short} 值。
   */
  public static short toShort(@Nullable final Float value) {
    return (value == null ? IntUtils.DEFAULT : toShort(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code short} 值。
   */
  public static short toShort(@Nullable final Float value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(final float value) {
    return toShort(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Float value) {
    return (value == null ? null : toShortObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Float value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@code int} 值。
   */
  public static int toInt(final float value) {
    return (int) value;
  }

  /**
   * 将 {@link Float} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code int} 值。
   */
  public static int toInt(@Nullable final Float value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code int} 值。
   */
  public static int toInt(@Nullable final Float value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Integer} 对象。
   */
  public static Integer toIntObject(final float value) {
    return toInt(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Integer} 对象。
   */
  public static Integer toIntObject(@Nullable final Float value) {
    return (value == null ? null : toIntObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Integer} 对象。
   */
  public static Integer toIntObject(@Nullable final Float value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@code long} 值。
   */
  public static long toLong(final float value) {
    return (long) value;
  }

  /**
   * 将 {@link Float} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link LongUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code long} 值。
   */
  public static long toLong(@Nullable final Float value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code long} 值。
   */
  public static long toLong(@Nullable final Float value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Long} 对象。
   */
  public static Long toLongObject(final float value) {
    return toLong(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Long} 对象。
   */
  public static Long toLongObject(@Nullable final Float value) {
    return (value == null ? null : toLongObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Long} 对象。
   */
  public static Long toLongObject(@Nullable final Float value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@code double} 值。
   */
  public static double toDouble(final float value) {
    return value;
  }

  /**
   * 将 {@link Float} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@link DoubleUtils#DEFAULT}；
   *     否则返回 {@code value} 转换后的 {@code double} 值。
   */
  public static double toDouble(@Nullable final Float value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@code double} 值。
   */
  public static double toDouble(@Nullable final Float value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(final float value) {
    return toDouble(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(@Nullable final Float value) {
    return (value == null ? null : toDoubleObject(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回转换后的 {@link Double} 对象。
   */
  public static Double toDoubleObject(@Nullable final Float value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为字符串表示。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的字符串表示。
   */
  public static String toString(final float value) {
    return Float.toString(value);
  }

  /**
   * 将 {@link Float} 对象转换为字符串表示。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的字符串表示。
   */
  public static String toString(@Nullable final Float value) {
    return (value == null ? null : toString(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为字符串表示。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的字符串表示。
   */
  public static String toString(@Nullable final Float value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Date} 对象。
   */
  public static Date toDate(final float value) {
    return new Date((long) value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Float value) {
    return (value == null ? null : toDate(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Float value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.floatValue()));
  }

  /**
   * 将 {@code float} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的字节数组。
   */
  public static byte[] toByteArray(final float value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将 {@code float} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @param byteOrder
   *     字节顺序。
   * @return
   *     转换后的字节数组。
   */
  public static byte[] toByteArray(final float value,
      final ByteOrder byteOrder) {
    final int bits = Float.floatToIntBits(value);
    return IntUtils.toByteArray(bits, byteOrder);
  }

  /**
   * 将 {@link Float} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Float value) {
    return (value == null ? null : toByteArray(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param byteOrder
   *     字节顺序。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Float value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.floatValue(), byteOrder));
  }

  /**
   * 将 {@link Float} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Float value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.floatValue()));
  }

  /**
   * 将 {@link Float} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @param byteOrder
   *     字节顺序。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Float value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.floatValue(), byteOrder));
  }

  /**
   * 将 {@code float} 值转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link Class} 对象。
   */
  public static Class<?> toClass(final float value) {
    return Float.TYPE;
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的 {@link Class} 对象。
   */
  public static Class<?> toClass(@Nullable final Float value) {
    return (value == null ? null : Float.class);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@link Class} 对象。
   */
  public static Class<?> toClass(@Nullable final Float value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Float.class);
  }

  /**
   * 将 {@code float} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(final float value) {
    return BigInteger.valueOf((long) value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Float value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Float value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue
                          : BigInteger.valueOf(value.longValue()));
  }

  /**
   * 将 {@code float} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code float} 值。
   * @return
   *     转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(final float value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 {@link Float} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code value} 转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Float value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 {@link Float} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Float} 对象，可以为 {@code null}。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，返回的默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code value} 转换后的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Float value,
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
   * 测试指定的类型的值是否可以和{@code float}或{@code Float}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code float}或{@code Float}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(new ClassKey(type));
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
  public static String roundToString(final float value, final int precision) {
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
  public static String roundToString(@Nullable final Float value,
      @Nullable final String defaultValue, final int precision) {
    if (value == null) {
      return defaultValue;
    } else if (Double.isNaN(value) || Double.isInfinite(value)) {
      return Double.toString(value);
    } else {
      return roundToString(value, precision);
    }
  }
}
