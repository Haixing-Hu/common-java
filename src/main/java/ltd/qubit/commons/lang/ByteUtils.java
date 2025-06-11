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
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;
import static ltd.qubit.commons.text.NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;

/**
 * 此类提供对 {@code byte} 基本类型和 {@link Byte} 对象的操作。
 *
 * <p>此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都在详细说明中记录了其行为。
 *
 * <p>此类还处理从 {@code byte} 值或 {@link Byte} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class ByteUtils {

  /**
   * 必要时使用的默认 {@code byte} 值。
   */
  public static final byte DEFAULT = (byte) 0;

  /**
   * 无符号字节的最大值。
   */
  public static final int UNSIGNED_MAX   = 0xFF;

  private ByteUtils() {}

  /**
   * 将 {@link Byte} 对象转换为 {@code byte} 基本类型。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code DEFAULT}
   */
  public static byte toPrimitive(@Nullable final Byte value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code byte} 基本类型。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static byte toPrimitive(@Nullable final Byte value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code byte} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 不为零返回 {@code true}，
   *     否则返回 {@code false}
   */
  public static boolean toBoolean(final byte value) {
    return (value != 0);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code BooleanUtils.DEFAULT}；否则返回转换结果
   */
  public static boolean toBoolean(@Nullable final Byte value) {
    return (value == null ? BooleanUtils.DEFAULT
                          : toBoolean(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回转换结果
   */
  public static boolean toBoolean(@Nullable final Byte value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Boolean} 对象
   */
  public static Boolean toBooleanObject(final byte value) {
    return toBoolean(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Boolean} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Boolean toBooleanObject(@Nullable final Byte value) {
    return (value == null ? null : toBooleanObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Boolean} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Boolean toBooleanObject(@Nullable final Byte value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code char} 值
   */
  public static char toChar(final byte value) {
    return (char) value;
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code CharUtils.DEFAULT}
   */
  public static char toChar(@Nullable final Byte value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static char toChar(@Nullable final Byte value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Character} 对象
   */
  public static Character toCharObject(final byte value) {
    return toChar(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Character toCharObject(@Nullable final Byte value) {
    return (value == null ? null : toCharObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Character toCharObject(@Nullable final Byte value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code short} 值
   */
  public static short toShort(final byte value) {
    return value;
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code ShortUtils.DEFAULT}
   */
  public static short toShort(@Nullable final Byte value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static short toShort(@Nullable final Byte value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Short} 对象
   */
  public static Short toShortObject(final byte value) {
    return toShort(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Short toShortObject(@Nullable final Byte value) {
    return (value == null ? null : toShortObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Short toShortObject(@Nullable final Byte value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code int} 值
   */
  public static int toInt(final byte value) {
    return value;
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code IntUtils.DEFAULT}
   */
  public static int toInt(@Nullable final Byte value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static int toInt(@Nullable final Byte value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Integer} 对象
   */
  public static Integer toIntObject(final byte value) {
    return toInt(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Integer toIntObject(@Nullable final Byte value) {
    return (value == null ? null : toIntObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Integer toIntObject(@Nullable final Byte value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code long} 值
   */
  public static long toLong(final byte value) {
    return value;
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code LongUtils.DEFAULT}
   */
  public static long toLong(@Nullable final Byte value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static long toLong(@Nullable final Byte value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Long} 对象
   */
  public static Long toLongObject(final byte value) {
    return toLong(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Long toLongObject(@Nullable final Byte value) {
    return (value == null ? null : toLongObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Long toLongObject(@Nullable final Byte value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code float} 值
   */
  public static float toFloat(final byte value) {
    return value;
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code FloatUtils.DEFAULT}
   */
  public static float toFloat(@Nullable final Byte value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static float toFloat(@Nullable final Byte value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Float} 对象
   */
  public static Float toFloatObject(final byte value) {
    return toFloat(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Float toFloatObject(@Nullable final Byte value) {
    return (value == null ? null : toFloatObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Float toFloatObject(@Nullable final Byte value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@code double} 值
   */
  public static double toDouble(final byte value) {
    return value;
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code DoubleUtils.DEFAULT}
   */
  public static double toDouble(@Nullable final Byte value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static double toDouble(@Nullable final Byte value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link Double} 对象
   */
  public static Double toDoubleObject(final byte value) {
    return toDouble(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Double toDoubleObject(@Nullable final Byte value) {
    return (value == null ? null : toDoubleObject(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Double toDoubleObject(@Nullable final Byte value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的字符串
   */
  public static String toString(final byte value) {
    return Integer.toString(value, DECIMAL_RADIX);
  }

  /**
   * 将 {@link Byte} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的字符串。如果 {@code value} 为 {@code null}，返回 {@code null}
   */
  public static String toString(@Nullable final Byte value) {
    return (value == null ? null : toString(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的字符串。如果 {@code value} 为 {@code null}，返回 {@code defaultValue}
   */
  public static String toString(@Nullable final Byte value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @param builder
   *     用于附加十六进制字符串的 {@link StringBuilder}
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
   * 将 {@code byte} 值转换为十六进制字符串。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的十六进制字符串
   */
  public static String toHexString(final byte value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  /**
   * 将 {@code byte} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值，作为时间戳的毫秒数
   * @return
   *     转换后的 {@link Date} 对象
   */
  public static Date toDate(final byte value) {
    return new Date(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象，作为时间戳的毫秒数
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code null}，返回 {@code null}
   */
  public static Date toDate(@Nullable final Byte value) {
    return (value == null ? null : new Date(value));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象，作为时间戳的毫秒数
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code null}，返回 {@code defaultValue}
   */
  public static Date toDate(@Nullable final Byte value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value));
  }

  /**
   * 将 {@code byte} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     包含该值的字节数组
   */
  public static byte[] toByteArray(final byte value) {
    return new byte[]{value};
  }

  /**
   * 将 {@link Byte} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     包含该值的字节数组。如果 {@code value} 为 {@code null}，返回 {@code null}
   */
  public static byte[] toByteArray(@Nullable final Byte value) {
    return (value == null ? null : toByteArray(value.byteValue()));
  }

  /**
   * 将 {@link Byte} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     包含该值的字节数组。如果 {@code value} 为 {@code null}，返回 {@code defaultValue}
   */
  public static byte[] toByteArray(@Nullable final Byte value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.byteValue()));
  }

  /**
   * 将 {@code byte} 值转换为对应的 {@code Class} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     {@code byte} 基本类型的 {@code Class} 对象
   */
  public static Class<?> toClass(final byte value) {
    return Byte.TYPE;
  }

  /**
   * 将 {@link Byte} 对象转换为对应的 {@code Class} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     {@link Byte} 类的 {@code Class} 对象。如果 {@code value} 为 {@code null}，返回 {@code null}
   */
  public static Class<?> toClass(@Nullable final Byte value) {
    return (value == null ? null : Byte.class);
  }

  /**
   * 将 {@link Byte} 对象转换为对应的 {@code Class} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     {@link Byte} 类的 {@code Class} 对象。如果 {@code value} 为 {@code null}，返回 {@code defaultValue}
   */
  public static Class<?> toClass(@Nullable final Byte value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Byte.class);
  }

  /**
   * 将 {@code byte} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link BigInteger} 对象
   */
  public static BigInteger toBigInteger(final byte value) {
    return BigInteger.valueOf(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link BigInteger} 对象。如果 {@code value} 为 {@code null}，返回 {@code null}
   */
  public static BigInteger toBigInteger(@Nullable final Byte value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link BigInteger} 对象。如果 {@code value} 为 {@code null}，返回 {@code defaultValue}
   */
  public static BigInteger toBigInteger(@Nullable final Byte value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  /**
   * 将 {@code byte} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code byte} 值
   * @return
   *     转换后的 {@link BigDecimal} 对象
   */
  public static BigDecimal toBigDecimal(final byte value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果 {@code value} 为 {@code null}，返回 {@code null}
   */
  public static BigDecimal toBigDecimal(@Nullable final Byte value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 {@link Byte} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Byte} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果 {@code value} 为 {@code null}，返回 {@code defaultValue}
   */
  public static BigDecimal toBigDecimal(@Nullable final Byte value,
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
   * 测试指定的类型的值是否可以和{@code byte}或{@code Byte}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code byte}或{@code Byte}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(new ClassKey(type));
  }
}
