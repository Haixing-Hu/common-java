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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import ltd.qubit.commons.text.DateFormat;
import ltd.qubit.commons.util.codec.IsoDateCodec;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * 该类提供对 {@link Date} 对象的操作。
 * <p>
 * 该类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都在详细文档中说明了其行为。
 * </p>
 * <p>
 * 该类还处理从 {@link Date} 对象到常见类型的转换。
 * </p>
 *
 * @author 胡海星
 */
public class DateUtils {

  /**
   * 默认的本地日期时间格式模式。
   */
  public static final String DEFAULT_LOCAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /**
   * 默认的日期时间格式模式。
   */
  public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss z";

  /**
   * 默认的日期格式模式。
   */
  public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

  /**
   * 默认的时间格式模式。
   */
  public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

  /**
   * UTC 时区。
   */
  public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

  /**
   * 中国北京时区。
   */
  public static final TimeZone BEIJING = TimeZone.getTimeZone("GMT+8");

  /**
   * UTC 时区 ID。
   */
  public static final ZoneId UTC_ZONE_ID = UTC.toZoneId();

  /**
   * 中国北京时区 ID。
   */
  public static final ZoneId BEIJING_ZONE_ID = BEIJING.toZoneId();

  /**
   * 每秒毫秒数。
   */
  public static final long MILLISECONDS_PER_SECOND = 1000L;

  /**
   * 将 {@link Date} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link BooleanUtils#DEFAULT}；
   *     否则如果日期时间不为 0（即不是 Unix 时间戳的起始时间），返回 {@code true}，
   *     否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final Date value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.getTime() != 0));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则如果日期时间不为 0（即不是 Unix 时间戳的起始时间），返回 {@code true}，
   *     否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final Date value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.getTime() != 0));
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则如果日期时间不为 0（即不是 Unix 时间戳的起始时间），返回 {@code true}，
   *     否则返回 {@code false}。
   */
  public static Boolean toBooleanObject(@Nullable final Date value) {
    return (value == null ? null : value.getTime() != 0);
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则如果日期时间不为 0（即不是 Unix 时间戳的起始时间），返回 {@code true}，
   *     否则返回 {@code false}。
   */
  public static Boolean toBooleanObject(@Nullable final Date value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue :
            Boolean.valueOf(value.getTime() != 0));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link CharUtils#DEFAULT}；
   *     否则返回日期时间戳的低 16 位作为字符值。
   */
  public static char toChar(@Nullable final Date value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 16 位作为字符值。
   */
  public static char toChar(@Nullable final Date value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳的低 16 位作为字符值。
   */
  public static Character toCharObject(@Nullable final Date value) {
    return (value == null ? null : (char) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 16 位作为字符值。
   */
  public static Character toCharObject(@Nullable final Date value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue :
            Character.valueOf((char) value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link ByteUtils#DEFAULT}；
   *     否则返回日期时间戳的低 8 位作为字节值。
   */
  public static byte toByte(@Nullable final Date value) {
    return (value == null ? ByteUtils.DEFAULT : (byte) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 8 位作为字节值。
   */
  public static byte toByte(@Nullable final Date value,
      final byte defaultValue) {
    return (value == null ? defaultValue : (byte) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳的低 8 位作为字节值。
   */
  public static Byte toByteObject(@Nullable final Date value) {
    return (value == null ? null : (byte) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 8 位作为字节值。
   */
  public static Byte toByteObject(@Nullable final Date value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue :
            Byte.valueOf((byte) value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回日期时间戳的低 16 位作为短整型值。
   */
  public static short toShort(@Nullable final Date value) {
    return (value == null ? IntUtils.DEFAULT : (short) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 16 位作为短整型值。
   */
  public static short toShort(@Nullable final Date value,
      final short defaultValue) {
    return (value == null ? defaultValue : (short) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳的低 16 位作为短整型值。
   */
  public static Short toShortObject(@Nullable final Date value) {
    return (value == null ? null : (short) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 16 位作为短整型值。
   */
  public static Short toShortObject(@Nullable final Date value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue :
            Short.valueOf((short) value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link IntUtils#DEFAULT}；
   *     否则返回日期时间戳的低 32 位作为整型值。
   */
  public static int toInt(@Nullable final Date value) {
    return (value == null ? IntUtils.DEFAULT : (int) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 32 位作为整型值。
   */
  public static int toInt(@Nullable final Date value, final int defaultValue) {
    return (value == null ? defaultValue : (int) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳的低 32 位作为整型值。
   */
  public static Integer toIntObject(@Nullable final Date value) {
    return (value == null ? null : (int) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳的低 32 位作为整型值。
   */
  public static Integer toIntObject(@Nullable final Date value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue :
            Integer.valueOf((int) value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link LongUtils#DEFAULT}；
   *     否则返回日期时间戳。
   */
  public static long toLong(@Nullable final Date value) {
    return (value == null ? LongUtils.DEFAULT : value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳。
   */
  public static long toLong(@Nullable final Date value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳。
   */
  public static Long toLongObject(@Nullable final Date value) {
    return (value == null ? null : value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳。
   */
  public static Long toLongObject(@Nullable final Date value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link FloatUtils#DEFAULT}；
   *     否则返回日期时间戳作为浮点数值。
   */
  public static float toFloat(@Nullable final Date value) {
    return (value == null ? FloatUtils.DEFAULT : (float) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳作为浮点数值。
   */
  public static float toFloat(@Nullable final Date value,
      final float defaultValue) {
    return (value == null ? defaultValue : (float) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳作为浮点数值。
   */
  public static Float toFloatObject(@Nullable final Date value) {
    return (value == null ? null : (float) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳作为浮点数值。
   */
  public static Float toFloatObject(@Nullable final Date value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@link DoubleUtils#DEFAULT}；
   *     否则返回日期时间戳作为双精度浮点数值。
   */
  public static double toDouble(@Nullable final Date value) {
    return (value == null ? DoubleUtils.DEFAULT : (double) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳作为双精度浮点数值。
   */
  public static double toDouble(@Nullable final Date value,
      final double defaultValue) {
    return (value == null ? defaultValue : (double) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回日期时间戳作为双精度浮点数值。
   */
  public static Double toDoubleObject(@Nullable final Date value) {
    return (value == null ? null : (double) value.getTime());
  }

  /**
   * 将 {@link Date} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回日期时间戳作为双精度浮点数值。
   */
  public static Double toDoubleObject(@Nullable final Date value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.getTime()));
  }

  /**
   * 将 {@link Date} 对象按指定格式转换为字符串。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param pattern
   *     日期格式模式。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回按指定格式格式化的日期字符串。
   */
  public static String toString(@Nullable final Date value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  /**
   * 将 {@link Date} 对象按指定格式转换为字符串。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @param pattern
   *     日期格式模式。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回按指定格式格式化的日期字符串。
   */
  public static String toString(@Nullable final Date value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final DateFormat df = new DateFormat(pattern);
    return df.format(value);
  }

  /**
   * 将 {@link Date} 对象转换为 ISO 格式的字符串。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回字符串 "{@code <null>}"；
   *     否则返回 ISO 格式的日期时间字符串。
   */
  public static String toISOString(@Nullable final Date value) {
    if (value == null) {
      return "<null>";
    } else {
      return IsoDateCodec.INSTANCE.encode(value);
    }
  }

  /**
   * 将 {@link Date} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回包含日期时间戳的字节数组（使用默认字节序）。
   */
  public static byte[] toByteArray(@Nullable final Date value) {
    return (value == null ? null :
            LongUtils.toByteArray(value.getTime(), DEFAULT_BYTE_ORDER));
  }

  /**
   * 将 {@link Date} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param byteOrder
   *     字节序。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回包含日期时间戳的字节数组（使用指定字节序）。
   */
  public static byte[] toByteArray(@Nullable final Date value,
      final ByteOrder byteOrder) {
    return (value == null ? null :
            LongUtils.toByteArray(value.getTime(), byteOrder));
  }

  /**
   * 将 {@link Date} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回包含日期时间戳的字节数组（使用默认字节序）。
   */
  public static byte[] toByteArray(@Nullable final Date value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue :
            LongUtils.toByteArray(value.getTime(), DEFAULT_BYTE_ORDER));
  }

  /**
   * 将 {@link Date} 对象转换为字节数组。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @param byteOrder
   *     字节序。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回包含日期时间戳的字节数组（使用指定字节序）。
   */
  public static byte[] toByteArray(@Nullable final Date value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue :
            LongUtils.toByteArray(value.getTime(), byteOrder));
  }

  /**
   * 获取 {@link Date} 对象的类型。
   *
   * @param value
   *     {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code Date.class}。
   */
  public static Class<?> toClass(@Nullable final Date value) {
    return (value == null ? null : Date.class);
  }

  /**
   * 获取 {@link Date} 对象的类型。
   *
   * @param value
   *     {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code Date.class}。
   */
  public static Class<?> toClass(@Nullable final Date value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Date.class);
  }

  /**
   * 将 {@link Date} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回包含日期时间戳的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Date value) {
    return (value == null ? null : BigInteger.valueOf(value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回包含日期时间戳的 {@link BigInteger} 对象。
   */
  public static BigInteger toBigInteger(@Nullable final Date value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回包含日期时间戳的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Date value) {
    return (value == null ? null : BigDecimal.valueOf(value.getTime()));
  }

  /**
   * 将 {@link Date} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link Date} 对象，可以为 {@code null}。
   * @param defaultValue
   *     当 {@code value} 为 {@code null} 时返回的默认值，可以为 {@code null}。
   * @return 如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回包含日期时间戳的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final Date value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.getTime()));
  }

  /**
   * 在指定时区中为指定日期创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期的 {@link Date} 对象。
   */
  public static Date getDate(final int year, final int month, final int day,
      final TimeZone timeZone) {
    final LocalDate date = LocalDate.of(year, month, day);
    final Instant instant = date.atStartOfDay(timeZone.toZoneId()).toInstant();
    return Date.from(instant);
  }

  /**
   * 在默认时区中为指定日期创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @return 对应于指定日期的 {@link Date} 对象。
   */
  public static Date getDate(final int year, final int month, final int day) {
    return getDate(year, month, day, TimeZone.getDefault());
  }

  /**
   * 在 UTC 时区中为指定日期创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @return 对应于指定日期的 {@link Date} 对象。
   */
  public static Date getUtcDate(final int year, final int month,
      final int day) {
    return getDate(year, month, day, UTC);
  }

  /**
   * 在指定时区中为指定日期时间创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期时间的 {@link Date} 对象。
   */
  public static Date getDateTime(final int year, final int month, final int day,
      final int hour, final int minute, final int second,
      final TimeZone timeZone) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, hour,
        minute, second, 0, timeZone.toZoneId());
    final Instant instant = zonedDateTime.toInstant();
    return Date.from(instant);
  }

  /**
   * 在指定时区中为指定日期时间创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param millisecond
   *     毫秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期时间的 {@link Date} 对象。
   */
  public static Date getDateTime(final int year, final int month, final int day,
      final int hour, final int minute, final int second, final int millisecond,
      final TimeZone timeZone) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, hour,
        minute, second, (int) TimeUnit.MILLISECONDS.toNanos(millisecond),
        timeZone.toZoneId());
    final Instant instant = zonedDateTime.toInstant();
    return Date.from(instant);
  }

  /**
   * 在默认时区中为指定日期时间创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link Date} 对象。
   */
  public static Date getDateTime(final int year, final int month, final int day,
      final int hour, final int minute, final int second) {
    return getDateTime(year, month, day, hour, minute, second,
        TimeZone.getDefault());
  }

  /**
   * 在 UTC 时区中为指定日期时间创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link Date} 对象。
   */
  public static Date getUtcDateTime(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getDateTime(year, month, day, hour, minute, second, UTC);
  }

  /**
   * 在 UTC 时区中为指定日期时间创建 {@link Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param millisecond
   *     毫秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link Date} 对象。
   */
  public static Date getUtcDateTime(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond) {
    return getDateTime(year, month, day, hour, minute, second, millisecond,
        UTC);
  }

  /**
   * 在指定时区中为指定时间创建 {@link Time} 对象。
   *
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定时间的 {@link Time} 对象。
   */
  public static Time getTime(final int hour, final int minute, final int second,
      final TimeZone timeZone) {
    final Calendar cal = new GregorianCalendar(timeZone);
    cal.clear();
    // stop checkstyle: MagicNumberCheck
    cal.set(1979, 0, 1, hour, minute, second);
    // resume checkstyle: MagicNumberCheck
    return new Time(cal.getTimeInMillis());
  }

  /**
   * 在默认时区中为指定时间创建 {@link Time} 对象。
   *
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定时间的 {@link Time} 对象。
   */
  public static Time getTime(final int hour, final int minute,
      final int second) {
    return getTime(hour, minute, second, TimeZone.getDefault());
  }

  /**
   * 在 UTC 时区中为指定时间创建 {@link Time} 对象。
   *
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定时间的 {@link Time} 对象。
   */
  public static Time getUtcTime(final int hour, final int minute,
      final int second) {
    return getTime(hour, minute, second, UTC);
  }

  /**
   * 在指定时区中为指定日期时间创建 {@link Timestamp} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期时间的 {@link Timestamp} 对象。
   */
  public static Timestamp getTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final TimeZone timeZone) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, hour,
        minute, second, 0, timeZone.toZoneId());
    final Instant instant = zonedDateTime.toInstant();
    return Timestamp.from(instant);
  }

  /**
   * 在指定时区中为指定日期时间创建 {@link Timestamp} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param millisecond
   *     毫秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期时间的 {@link Timestamp} 对象。
   */
  public static Timestamp getTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond, final TimeZone timeZone) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, hour,
        minute, second, (int) TimeUnit.MILLISECONDS.toNanos(millisecond),
        timeZone.toZoneId());
    final Instant instant = zonedDateTime.toInstant();
    return Timestamp.from(instant);
  }

  /**
   * 在默认时区中为指定日期时间创建 {@link Timestamp} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link Timestamp} 对象。
   */
  public static Timestamp getTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getTimestamp(year, month, day, hour, minute, second,
        TimeZone.getDefault());
  }

  /**
   * 在 UTC 时区中为指定日期时间创建 {@link Timestamp} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link Timestamp} 对象。
   */
  public static Timestamp getUtcTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getTimestamp(year, month, day, hour, minute, second, UTC);
  }

  /**
   * 在 UTC 时区中为指定日期时间创建 {@link Timestamp} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param millisecond
   *     毫秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link Timestamp} 对象。
   */
  public static Timestamp getUtcTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond) {
    return getTimestamp(year, month, day, hour, minute, second, millisecond,
        UTC);
  }

  /**
   * 在指定时区中为指定日期时间创建 {@link java.sql.Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期时间的 {@link java.sql.Date} 对象。
   */
  public static java.sql.Date getSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final TimeZone timeZone) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, hour,
        minute, second, 0, timeZone.toZoneId());
    final Instant instant = zonedDateTime.toInstant();
    return new java.sql.Date(instant.toEpochMilli());
  }

  /**
   * 在指定时区中为指定日期时间创建 {@link java.sql.Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param millisecond
   *     毫秒，从 0 开始。
   * @param timeZone
   *     指定的时区。
   * @return 对应于指定日期时间的 {@link java.sql.Date} 对象。
   */
  public static java.sql.Date getSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond, final TimeZone timeZone) {
    final ZonedDateTime zonedDateTime = ZonedDateTime.of(year, month, day, hour,
        minute, second, (int) TimeUnit.MILLISECONDS.toNanos(millisecond),
        timeZone.toZoneId());
    final Instant instant = zonedDateTime.toInstant();
    return new java.sql.Date(instant.toEpochMilli());
  }

  /**
   * 在默认时区中为指定日期时间创建 {@link java.sql.Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link java.sql.Date} 对象。
   */
  public static java.sql.Date getSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getSqlDate(year, month, day, hour, minute, second,
        TimeZone.getDefault());
  }

  /**
   * 在 UTC 时区中为指定日期时间创建 {@link java.sql.Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link java.sql.Date} 对象。
   */
  public static java.sql.Date getUtcSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getSqlDate(year, month, day, hour, minute, second, UTC);
  }

  /**
   * 在 UTC 时区中为指定日期时间创建 {@link java.sql.Date} 对象。
   *
   * <p><b>注意：</b>月份从 1 开始，而不是从 0 开始。
   *
   * @param year
   *     年份。
   * @param month
   *     月份，从 1 开始。
   * @param day
   *     月中的日期，从 1 开始。
   * @param hour
   *     小时，从 0 开始。
   * @param minute
   *     分钟，从 0 开始。
   * @param second
   *     秒，从 0 开始。
   * @param millisecond
   *     毫秒，从 0 开始。
   * @return 对应于指定日期时间的 {@link java.sql.Date} 对象。
   */
  public static java.sql.Date getUtcSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond) {
    return getSqlDate(year, month, day, hour, minute, second, millisecond, UTC);
  }

  /**
   * 去除日期时间中的毫秒部分。
   *
   * @param value
   *     指定的日期时间。
   * @return 返回一个新的 {@link Date} 对象，该对象去除了指定日期时间的毫秒部分，
   *     如果指定对象为 {@code null}，则返回 {@code null}。
   */
  public static Date dropMilliseconds(@Nullable final Date value) {
    if (value == null) {
      return null;
    } else {
      final long milliseconds = value.getTime();
      return new Date(
          MILLISECONDS_PER_SECOND * (milliseconds / MILLISECONDS_PER_SECOND));
    }
  }

  /**
   * 去除本地日期时间中的纳秒部分。
   *
   * @param value
   *     指定的日期时间。
   * @return 返回一个新的 {@link LocalDateTime} 对象，该对象去除了指定日期时间的纳秒部分，
   *     如果指定对象为 {@code null}，则返回 {@code null}。
   */
  public static LocalDateTime dropNanoseconds(
      @Nullable final LocalDateTime value) {
    if (value == null) {
      return null;
    } else {
      return LocalDateTime.of(value.getYear(), value.getMonthValue(),
          value.getDayOfMonth(), value.getHour(), value.getMinute(),
          value.getSecond());
    }
  }

  /**
   * 就地去除日期时间中的毫秒部分。
   *
   * @param value
   *     指定的日期时间，其毫秒部分将被去除。
   */
  public static void dropMillisecondsInPlace(@Nullable final Date value) {
    if (value != null) {
      final long milliseconds = value.getTime();
      value.setTime(
          MILLISECONDS_PER_SECOND * (milliseconds / MILLISECONDS_PER_SECOND));
    }
  }

  /**
   * 就地去除本地日期时间中的纳秒部分。
   *
   * @param value
   *     指定的日期时间，其纳秒部分将被去除。
   */
  public static void dropNanosecondsInPlace(
      @Nullable final LocalDateTime value) {
    if (value != null) {
      final long nanos = value.getNano();
      value.minusNanos(nanos);
    }
  }

  /**
   * 将 {@link java.util.Date} 截断到指定的时间单位。
   *
   * @param value
   *     要截断的 {@link java.util.Date} 值。
   * @param unit
   *     要截断到的指定时间单位。
   * @return 返回原始 {@code value} 的新副本 {@link java.util.Date} 对象，
   *     其值被截断到指定的时间单位。
   */
  public static Date truncate(final Date value, final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    return new Date(result);
  }

  /**
   * 将 {@link java.sql.Date} 截断到指定的时间单位。
   *
   * @param value
   *     要截断的 {@link java.sql.Date} 值。
   * @param unit
   *     要截断到的指定时间单位。
   * @return 返回原始 {@code value} 的新副本 {@link java.sql.Date} 对象，
   *     其值被截断到指定的时间单位。
   */
  public static java.sql.Date truncate(final java.sql.Date value,
      final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    return new java.sql.Date(result);
  }

  /**
   * 就地将 {@link java.util.Date} 截断到指定的时间单位。
   *
   * @param value
   *     要截断的 {@link java.util.Date} 值。
   * @param unit
   *     要截断到的指定时间单位。
   * @return 返回原始 {@link java.util.Date} 对象 {@code value} 的引用，
   *     其值被截断到指定的时间单位。
   */
  public static Date truncateInPlace(final Date value, final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    value.setTime(result);
    return value;
  }

  /**
   * 就地将 {@link java.sql.Date} 截断到指定的时间单位。
   *
   * @param value
   *     要截断的 {@link java.sql.Date} 值。
   * @param unit
   *     要截断到的指定时间单位。
   * @return 返回原始 {@link java.sql.Date} 对象 {@code value} 的引用，
   *     其值被截断到指定的时间单位。
   */
  public static java.sql.Date truncateInPlace(final java.sql.Date value,
      final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    value.setTime(result);
    return value;
  }

  /**
   * 将 {@link TimeUnit} 转换为对应的 {@link ChronoUnit}。
   *
   * <p><b>注意：</b>此函数在 JDK 1.9 以上版本中是无用的，因为从 JDK 1.9 开始，
   * {@link TimeUnit} 类有一个名为 {@code TimeUnit.toChronoUnit()} 的新方法。
   * 但此函数在 JDK 1.8 或以下版本中仍然有用。
   *
   * @param unit
   *     一个 {@link TimeUnit}。
   * @return 对应的 {@link ChronoUnit}。
   */
  public static ChronoUnit toChronoUnit(final TimeUnit unit) {
    switch (unit) {
      case DAYS:
        return ChronoUnit.DAYS;
      case HOURS:
        return ChronoUnit.HOURS;
      case MINUTES:
        return ChronoUnit.MINUTES;
      case SECONDS:
        return ChronoUnit.SECONDS;
      case MILLISECONDS:
        return ChronoUnit.MILLIS;
      case MICROSECONDS:
        return ChronoUnit.MICROS;
      case NANOSECONDS:
        return ChronoUnit.NANOS;
      default:
        throw new IllegalArgumentException("Unsupported time unit: " + unit);
    }
  }
}