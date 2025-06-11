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
import java.util.Currency;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * 该类提供对 {@code double} 基本类型和 {@link Double} 对象的操作。
 *
 * <p>该类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都在详细文档中说明了其行为。
 *
 * <p>该类还处理从 {@code double} 值或 {@link Double} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
public class DoubleUtils {

  /**
   * 必要时使用的默认 {@code double} 值。
   */
  public static final double DEFAULT = 0.0;

  /**
   * {@code double} 值的默认精度。
   */
  public static final double EPSILON = 0.0000001;

  /**
   * 将 {@link Double} 对象转换为 {@code double} 基本类型。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code DEFAULT}；
   *     否则返回 {@code value} 的基本类型值。
   */
  public static double toPrimitive(@Nullable final Double value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@code double} 基本类型。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回 {@code value} 的基本类型值。
   */
  public static double toPrimitive(@Nullable final Double value,
      final double defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code double} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     如果 {@code value} 不等于 0，则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(final double value) {
    return (value != 0);
  }

  /**
   * 将 {@link Double} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code BooleanUtils.DEFAULT}；
   *     否则如果 {@code value} 不等于 0，则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final Double value) {
    return (value == null ? BooleanUtils.DEFAULT
                          : toBoolean(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则如果 {@code value} 不等于 0，则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final Double value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     如果 {@code value} 不等于 0，则返回 {@code Boolean.TRUE}；否则返回 {@code Boolean.FALSE}。
   */
  public static Boolean toBooleanObject(final double value) {
    return toBoolean(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则如果 {@code value} 不等于 0，则返回 {@code Boolean.TRUE}；否则返回 {@code Boolean.FALSE}。
   */
  public static Boolean toBooleanObject(@Nullable final Double value) {
    return (value == null ? null : toBooleanObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则如果 {@code value} 不等于 0，则返回 {@code Boolean.TRUE}；否则返回 {@code Boolean.FALSE}。
   */
  public static Boolean toBooleanObject(@Nullable final Double value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : toBooleanObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code char} 的结果。
   */
  public static char toChar(final double value) {
    return (char) value;
  }

  /**
   * 将 {@link Double} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code CharUtils.DEFAULT}；
   *     否则将 {@code value} 强制转换为 {@code char} 的结果。
   */
  public static char toChar(@Nullable final Double value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code char} 的结果。
   */
  public static char toChar(@Nullable final Double value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code char} 后包装成的 {@link Character} 对象。
   */
  public static Character toCharObject(final double value) {
    return toChar(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code char} 后包装成的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Double value) {
    return (value == null ? null : toCharObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code char} 后包装成的 {@link Character} 对象。
   */
  public static Character toCharObject(@Nullable final Double value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code byte} 的结果。
   */
  public static byte toByte(final double value) {
    return (byte) value;
  }

  /**
   * 将 {@link Double} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code ByteUtils.DEFAULT}；
   *     否则将 {@code value} 强制转换为 {@code byte} 的结果。
   */
  public static byte toByte(@Nullable final Double value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code byte} 的结果。
   */
  public static byte toByte(@Nullable final Double value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code byte} 后包装成的 {@link Byte} 对象。
   */
  public static Byte toByteObject(final double value) {
    return toByte(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code byte} 后包装成的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Double value) {
    return (value == null ? null : toByteObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code byte} 后包装成的 {@link Byte} 对象。
   */
  public static Byte toByteObject(@Nullable final Double value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code short} 的结果。
   */
  public static short toShort(final double value) {
    return (short) value;
  }

  /**
   * 将 {@link Double} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code ShortUtils.DEFAULT}；
   *     否则将 {@code value} 强制转换为 {@code short} 的结果。
   */
  public static short toShort(@Nullable final Double value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code short} 的结果。
   */
  public static short toShort(@Nullable final Double value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code short} 后包装成的 {@link Short} 对象。
   */
  public static Short toShortObject(final double value) {
    return toShort(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code short} 后包装成的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Double value) {
    return (value == null ? null : toShortObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code short} 后包装成的 {@link Short} 对象。
   */
  public static Short toShortObject(@Nullable final Double value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code int} 的结果。
   */
  public static int toInt(final double value) {
    return (int) value;
  }

  /**
   * 将 {@link Double} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code IntUtils.DEFAULT}；
   *     否则将 {@code value} 强制转换为 {@code int} 的结果。
   */
  public static int toInt(@Nullable final Double value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code int} 的结果。
   */
  public static int toInt(@Nullable final Double value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code int} 后包装成的 {@link Integer} 对象。
   */
  public static Integer toIntObject(final double value) {
    return toInt(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code int} 后包装成的 {@link Integer} 对象。
   */
  public static Integer toIntObject(@Nullable final Double value) {
    return (value == null ? null : toIntObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code int} 后包装成的 {@link Integer} 对象。
   */
  public static Integer toIntObject(@Nullable final Double value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code long} 的结果。
   */
  public static long toLong(final double value) {
    return (long) value;
  }

  /**
   * 将 {@link Double} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code LongUtils.DEFAULT}；
   *     否则将 {@code value} 强制转换为 {@code long} 的结果。
   */
  public static long toLong(@Nullable final Double value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code long} 的结果。
   */
  public static long toLong(@Nullable final Double value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code long} 后包装成的 {@link Long} 对象。
   */
  public static Long toLongObject(final double value) {
    return toLong(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code long} 后包装成的 {@link Long} 对象。
   */
  public static Long toLongObject(@Nullable final Double value) {
    return (value == null ? null : toLongObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code long} 后包装成的 {@link Long} 对象。
   */
  public static Long toLongObject(@Nullable final Double value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code float} 的结果。
   */
  public static float toFloat(final double value) {
    return (float) value;
  }

  /**
   * 将 {@link Double} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code FloatUtils.DEFAULT}；
   *     否则将 {@code value} 强制转换为 {@code float} 的结果。
   */
  public static float toFloat(@Nullable final Double value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code float} 的结果。
   */
  public static float toFloat(@Nullable final Double value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code float} 后包装成的 {@link Float} 对象。
   */
  public static Float toFloatObject(final double value) {
    return toFloat(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code float} 后包装成的 {@link Float} 对象。
   */
  public static Float toFloatObject(@Nullable final Double value) {
    return (value == null ? null : toFloatObject(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code float} 后包装成的 {@link Float} 对象。
   */
  public static Float toFloatObject(@Nullable final Double value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     {@code value} 的字符串表示。
   */
  public static String toString(final double value) {
    return Double.toString(value);
  }

  /**
   * 将 {@link Double} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回 {@code value} 的字符串表示。
   */
  public static String toString(@Nullable final Double value) {
    return (value == null ? null : toString(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回 {@code value} 的字符串表示。
   */
  public static String toString(@Nullable final Double value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值，表示自1970年1月1日以来的毫秒数。
   * @return
   *     使用 {@code value} 作为时间戳创建的 {@link Date} 对象。
   */
  public static Date toDate(final double value) {
    return new Date((long) value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象，表示自1970年1月1日以来的毫秒数。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则使用 {@code value} 作为时间戳创建的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Double value) {
    return (value == null ? null : toDate(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象，表示自1970年1月1日以来的毫秒数。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则使用 {@code value} 作为时间戳创建的 {@link Date} 对象。
   */
  public static Date toDate(@Nullable final Double value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.doubleValue()));
  }

  /**
   * 将 {@code double} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     {@code value} 的字节数组表示，使用默认字节序。
   */
  public static byte[] toByteArray(final double value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将 {@code double} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @param byteOrder
   *     字节序。
   * @return
   *     {@code value} 的字节数组表示，使用指定的字节序。
   */
  public static byte[] toByteArray(final double value,
      final ByteOrder byteOrder) {
    final long bits = Double.doubleToLongBits(value);
    return LongUtils.toByteArray(bits, byteOrder);
  }

  /**
   * 将 {@link Double} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回 {@code value} 的字节数组表示，使用默认字节序。
   */
  public static byte[] toByteArray(@Nullable final Double value) {
    return (value == null ? null : toByteArray(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param byteOrder
   *     字节序。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回 {@code value} 的字节数组表示，使用指定的字节序。
   */
  public static byte[] toByteArray(@Nullable final Double value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.doubleValue(), byteOrder));
  }

  /**
   * 将 {@link Double} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回 {@code value} 的字节数组表示，使用默认字节序。
   */
  public static byte[] toByteArray(@Nullable final Double value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.doubleValue()));
  }

  /**
   * 将 {@link Double} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @param byteOrder
   *     字节序。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回 {@code value} 的字节数组表示，使用指定的字节序。
   */
  public static byte[] toByteArray(@Nullable final Double value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.doubleValue(), byteOrder));
  }

  /**
   * 获取 {@code double} 值对应的 {@link Class} 对象。
   *
   * @param value
   *     {@code double} 值。
   * @return
   *     {@code Double.TYPE}，即 {@code double.class}。
   */
  public static Class<?> toClass(final double value) {
    return Double.TYPE;
  }

  /**
   * 获取 {@link Double} 对象对应的 {@link Class} 对象。
   *
   * @param value
   *     {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则返回 {@code Double.class}。
   */
  public static Class<?> toClass(@Nullable final Double value) {
    return (value == null ? null : Double.class);
  }

  /**
   * 获取 {@link Double} 对象对应的 {@link Class} 对象。
   *
   * @param value
   *     {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则返回 {@code Double.class}。
   */
  public static Class<?> toClass(@Nullable final Double value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Double.class);
  }

  /**
   * 将 {@code double} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     将 {@code value} 强制转换为 {@code long} 后创建的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(final double value) {
    return BigInteger.valueOf((long) value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则将 {@code value} 强制转换为 {@code long} 后创建的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Double value) {
    return (value == null ? null : BigInteger.valueOf(value.longValue()));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则将 {@code value} 强制转换为 {@code long} 后创建的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Double value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue
                          : BigInteger.valueOf(value.longValue()));
  }

  /**
   * 将 {@code double} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code double} 值。
   * @return
   *     使用 {@code value} 创建的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(final double value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 {@link Double} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code null}；
   *     否则使用 {@code value} 创建的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Double value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 {@link Double} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Double} 对象。
   * @param defaultValue
   *     如果 {@code value} 为 {@code null}，则返回此默认值。
   * @return
   *     如果 {@code value} 为 {@code null}，则返回 {@code defaultValue}；
   *     否则使用 {@code value} 创建的 {@link BigDecimal} 对象。
   */
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
   * 格式化指定的货币金额。
   *
   * @param currency
   *     货币的货币对象。如果为 {@code null}，则货币符号不会被包含。
   * @param amount
   *     货币金额。
   * @return
   *     格式化后的货币字符串。
   */
  public static String formatMoney(@Nullable final Currency currency,
      final double amount) {
    return formatMoney(currency, amount, 2);
  }

  /**
   * 格式化指定的货币金额。
   *
   * @param currency
   *     货币的货币对象。如果为 {@code null}，则货币符号不会被包含。
   * @param amount
   *     货币金额。
   * @param precision
   *     货币金额的精度，即小数点后的位数。
   * @return
   *     格式化后的货币字符串。
   */
  public static String formatMoney(@Nullable final Currency currency,
      final double amount, final int precision) {
    return (currency == null ? "" : currency.getSymbol() + " ")
        + roundToString(amount, precision);
  }
}