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
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.InstantCodec;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * 此类提供对 {@link Instant} 对象的操作。
 * <p>
 * 此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都会详细记录其行为。
 * </p>
 * <p>
 * 此类还处理从 {@link Instant} 对象到常见类型的转换。
 * </p>
 *
 * @author 胡海星
 */
public class InstantUtils {

  /**
   * 将 {@link Instant} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code boolean} 值。如果参数为 {@code null}，返回 {@link BooleanUtils#DEFAULT}；
   *     如果时间戳的毫秒值不为0，返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final Instant value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.toEpochMilli() != 0));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code boolean} 值。如果参数为 {@code null}，返回默认值；
   *     如果时间戳的毫秒值不为0，返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final Instant value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.toEpochMilli() != 0));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Boolean} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     如果时间戳的毫秒值不为0，返回 {@code true}；否则返回 {@code false}。
   */
  public static Boolean toBooleanObject(@Nullable final Instant value) {
    return (value == null ? null : value.toEpochMilli() != 0);
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Boolean} 对象。如果参数为 {@code null}，返回默认值；
   *     如果时间戳的毫秒值不为0，返回 {@code true}；否则返回 {@code false}。
   */
  public static Boolean toBooleanObject(@Nullable final Instant value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : Boolean.valueOf(value.toEpochMilli() != 0));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code char} 值。如果参数为 {@code null}，返回 {@link CharUtils#DEFAULT}；
   *     否则返回时间戳毫秒值的低16位转换为char。
   */
  public static char toChar(@Nullable final Instant value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code char} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低16位转换为char。
   */
  public static char toChar(@Nullable final Instant value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Character} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值的低16位转换为char的包装对象。
   */
  public static Character toCharObject(@Nullable final Instant value) {
    return (value == null ? null : (char) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Character} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低16位转换为char的包装对象。
   */
  public static Character toCharObject(@Nullable final Instant value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue
                          : Character.valueOf((char) value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code byte} 值。如果参数为 {@code null}，返回 {@link ByteUtils#DEFAULT}；
   *     否则返回时间戳毫秒值的低8位转换为byte。
   */
  public static byte toByte(@Nullable final Instant value) {
    return (value == null ? ByteUtils.DEFAULT : (byte) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code byte} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低8位转换为byte。
   */
  public static byte toByte(@Nullable final Instant value,
      final byte defaultValue) {
    return (value == null ? defaultValue : (byte) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Byte} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值的低8位转换为byte的包装对象。
   */
  public static Byte toByteObject(@Nullable final Instant value) {
    return (value == null ? null : (byte) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Byte} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低8位转换为byte的包装对象。
   */
  public static Byte toByteObject(@Nullable final Instant value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue
                          : Byte.valueOf((byte) value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code short} 值。如果参数为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回时间戳毫秒值的低16位转换为short。
   */
  public static short toShort(@Nullable final Instant value) {
    return (value == null ? IntUtils.DEFAULT : (short) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code short} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低16位转换为short。
   */
  public static short toShort(@Nullable final Instant value,
      final short defaultValue) {
    return (value == null ? defaultValue : (short) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Short} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值的低16位转换为short的包装对象。
   */
  public static Short toShortObject(@Nullable final Instant value) {
    return (value == null ? null : (short) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Short} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低16位转换为short的包装对象。
   */
  public static Short toShortObject(@Nullable final Instant value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue
                          : Short.valueOf((short) value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code int} 值。如果参数为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回时间戳毫秒值的低32位转换为int。
   */
  public static int toInt(@Nullable final Instant value) {
    return (value == null ? IntUtils.DEFAULT : (int) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code int} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低32位转换为int。
   */
  public static int toInt(@Nullable final Instant value,
      final int defaultValue) {
    return (value == null ? defaultValue : (int) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Integer} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值的低32位转换为int的包装对象。
   */
  public static Integer toIntObject(@Nullable final Instant value) {
    return (value == null ? null : (int) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Integer} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的低32位转换为int的包装对象。
   */
  public static Integer toIntObject(@Nullable final Instant value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue
                          : Integer.valueOf((int) value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code long} 值。如果参数为 {@code null}，返回 {@link LongUtils#DEFAULT}；
   *     否则返回时间戳的毫秒值。
   */
  public static long toLong(@Nullable final Instant value) {
    return (value == null ? LongUtils.DEFAULT : value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code long} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳的毫秒值。
   */
  public static long toLong(@Nullable final Instant value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Long} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值的包装对象。
   */
  public static Long toLongObject(@Nullable final Instant value) {
    return (value == null ? null : value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Long} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值的包装对象。
   */
  public static Long toLongObject(@Nullable final Instant value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code float} 值。如果参数为 {@code null}，返回 {@link FloatUtils#DEFAULT}；
   *     否则返回时间戳毫秒值转换为float。
   */
  public static float toFloat(@Nullable final Instant value) {
    return (value == null ? FloatUtils.DEFAULT : (float) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code float} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值转换为float。
   */
  public static float toFloat(@Nullable final Instant value,
      final float defaultValue) {
    return (value == null ? defaultValue : (float) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Float} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值转换为float的包装对象。
   */
  public static Float toFloatObject(@Nullable final Instant value) {
    return (value == null ? null : (float) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Float} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值转换为float的包装对象。
   */
  public static Float toFloatObject(@Nullable final Instant value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@code double} 值。如果参数为 {@code null}，返回 {@link DoubleUtils#DEFAULT}；
   *     否则返回时间戳毫秒值转换为double。
   */
  public static double toDouble(@Nullable final Instant value) {
    return (value == null ? DoubleUtils.DEFAULT : (double) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值。
   * @return
   *     转换后的 {@code double} 值。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值转换为double。
   */
  public static double toDouble(@Nullable final Instant value,
      final double defaultValue) {
    return (value == null ? defaultValue : (double) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link Double} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值转换为double的包装对象。
   */
  public static Double toDoubleObject(@Nullable final Instant value) {
    return (value == null ? null : (double) value.toEpochMilli());
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link Double} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值转换为double的包装对象。
   */
  public static Double toDoubleObject(@Nullable final Instant value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param pattern
   *     格式化模式。
   * @return
   *     转换后的字符串。如果参数为 {@code null}，返回 {@code null}；
   *     否则按照指定模式格式化时间。
   */
  public static String toString(@Nullable final Instant value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  /**
   * 将 {@link Instant} 对象转换为字符串。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @param pattern
   *     格式化模式。
   * @return
   *     转换后的字符串。如果参数为 {@code null}，返回默认值；
   *     否则按照指定模式格式化时间。
   */
  public static String toString(@Nullable final Instant value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final InstantCodec codec = new InstantCodec(pattern, true);
    return codec.encode(value);
  }

  /**
   * 将 {@link Instant} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的字节数组。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值按默认字节序转换的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Instant value) {
    return (value == null ? null
                          : LongUtils.toByteArray(value.toEpochMilli(), DEFAULT_BYTE_ORDER));
  }

  /**
   * 将 {@link Instant} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param byteOrder
   *     字节序。
   * @return
   *     转换后的字节数组。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值按指定字节序转换的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Instant value,
      final ByteOrder byteOrder) {
    return (value == null ? null : LongUtils.toByteArray(value.toEpochMilli(),
        byteOrder));
  }

  /**
   * 将 {@link Instant} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的字节数组。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值按默认字节序转换的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Instant value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : LongUtils.toByteArray(value.toEpochMilli(),
        DEFAULT_BYTE_ORDER));
  }

  /**
   * 将 {@link Instant} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @param byteOrder
   *     字节序。
   * @return
   *     转换后的字节数组。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值按指定字节序转换的字节数组。
   */
  public static byte[] toByteArray(@Nullable final Instant value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : LongUtils.toByteArray(value.toEpochMilli(),
        byteOrder));
  }

  /**
   * 将 {@link Instant} 对象转换为类对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的类对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回 {@link Instant} 类。
   */
  public static Class<?> toClass(@Nullable final Instant value) {
    return (value == null ? null : Instant.class);
  }

  /**
   * 将 {@link Instant} 对象转换为类对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的类对象。如果参数为 {@code null}，返回默认值；
   *     否则返回 {@link Instant} 类。
   */
  public static Class<?> toClass(@Nullable final Instant value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Instant.class);
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link BigInteger} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值对应的大整数。
   */
  public static BigInteger toBigInteger(@Nullable final Instant value) {
    return (value == null ? null : BigInteger.valueOf(value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link BigInteger} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值对应的大整数。
   */
  public static BigInteger toBigInteger(@Nullable final Instant value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果参数为 {@code null}，返回 {@code null}；
   *     否则返回时间戳毫秒值对应的大十进制数。
   */
  public static BigDecimal toBigDecimal(@Nullable final Instant value) {
    return (value == null ? null : BigDecimal.valueOf(value.toEpochMilli()));
  }

  /**
   * 将 {@link Instant} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Instant} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当参数为 {@code null} 时的默认值，可以为 {@code null}。
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果参数为 {@code null}，返回默认值；
   *     否则返回时间戳毫秒值对应的大十进制数。
   */
  public static BigDecimal toBigDecimal(@Nullable final Instant value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.toEpochMilli()));
  }

  /**
   * 测试指定的类型的值是否可以和{@code Instant}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code Instant}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == Instant.class)
        || (type == ZonedDateTime.class)
        || (type == java.sql.Timestamp.class)
        || (type == java.util.Date.class);
  }

  /**
   * 将对象转换为 {@link Instant} 对象。
   *
   * @param value
   *     要转换的对象，可以为 {@code null}。支持的类型包括：
   *     {@link Instant}、{@link ZonedDateTime}、{@link java.sql.Timestamp}、
   *     {@link java.util.Date}。
   * @return
   *     转换后的 {@link Instant} 对象。如果参数为 {@code null}，返回 {@code null}。
   * @throws IllegalArgumentException
   *     如果参数不是支持的时间类型。
   */
  public static Instant toInstant(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    final Class<?> cls = value.getClass();
    if (cls == Instant.class) {
      return (Instant) value;
    } else if (cls == ZonedDateTime.class) {
      return ((ZonedDateTime) value).toInstant();
    } else if (cls == java.sql.Timestamp.class) {
      final long milliseconds = ((java.sql.Timestamp) value).getTime();
      return Instant.ofEpochMilli(milliseconds);
    } else if (cls == java.util.Date.class) {
      final long milliseconds = ((java.util.Date) value).getTime();
      return Instant.ofEpochMilli(milliseconds);
    } else {
      throw new IllegalArgumentException("The value is not an instant "
          + "representable value: " + value.getClass().getName());
    }
  }

  /**
   * 将指定的{@link Instant}截断到指定的精度。
   *
   * @param instant
   *     指定的{@link Instant}对象，可以为{@code null}。
   * @param unit
   *     指定的时间精度。
   * @return
   *     一个新的{@link Instant}对象，表示原对象被截断后的时间；或{@code null}若原对象是
   *     {@code null}。
   */
  public static Instant truncatedTo(@Nullable final Instant instant,
      final ChronoUnit unit) {
    if (instant == null) {
      return null;
    }
    return instant.truncatedTo(unit);
  }

  /**
   * 将指定的{@link Instant}截断到秒。
   *
   * @param instant
   *     指定的{@link Instant}对象，可以为{@code null}。
   * @return
   *     一个新的{@link Instant}对象，表示原对象被截断后的时间；或{@code null}若原对象是
   *     {@code null}。
   */
  public static Instant truncatedToSecond(@Nullable final Instant instant) {
    return truncatedTo(instant, ChronoUnit.SECONDS);
  }

  /**
   * 安全地为 {@link Instant} 对象添加指定数量的时间。
   * <p>
   * 此函数<b>不会</b>抛出任何异常。如果操作结果超出了 {@link Instant} 对象的
   * 范围，它将返回 {@link Instant#MAX} 或 {@link Instant#MIN}。
   *
   * @param instant
   *     要操作的 {@link Instant} 对象。
   * @param amount
   *     要添加的时间数量，以指定单位为准。
   * @param unit
   *     要添加的时间数量的单位。
   * @return
   *     操作的结果。如果结果超出了 {@link Instant} 对象的范围，
   *     将返回 {@link Instant#MAX} 或 {@link Instant#MIN}。
   */
  public static Instant safePlus(final Instant instant, final long amount, final TemporalUnit unit) {
    try {
      return instant.plus(amount, unit);
    } catch (final DateTimeException | ArithmeticException e) {
      if (amount >= 0) {
        return Instant.MAX;
      } else {
        return Instant.MIN;
      }
    }
  }

  /**
   * 安全地为 {@link Instant} 对象减去指定数量的时间。
   * <p>
   * 此函数<b>不会</b>抛出任何异常。如果操作结果超出了 {@link Instant} 对象的
   * 范围，它将返回 {@link Instant#MAX} 或 {@link Instant#MIN}。
   *
   * @param instant
   *     要操作的 {@link Instant} 对象。
   * @param amount
   *     要减去的时间数量，以指定单位为准。
   * @param unit
   *     要减去的时间数量的单位。
   * @return
   *     操作的结果。如果结果超出了 {@link Instant} 对象的范围，
   *     将返回 {@link Instant#MAX} 或 {@link Instant#MIN}。
   */
  public static Instant safeMinus(final Instant instant, final long amount, final TemporalUnit unit) {
    try {
      return instant.minus(amount, unit);
    } catch (final DateTimeException | ArithmeticException e) {
      if (amount <= 0) {
        return Instant.MAX;
      } else {
        return Instant.MIN;
      }
    }
  }
}