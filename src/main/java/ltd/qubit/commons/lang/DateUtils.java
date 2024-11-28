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

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * This class provides operations on {@link Date} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception will
 * not be thrown for a {@code null} input. Each method documents its behavior in
 * more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link Date} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 */
public class DateUtils {

  /**
   * The default date format pattern.
   */
  public static final String DEFAULT_LOCAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /**
   * The default date time format pattern.
   */
  public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss z";

  /**
   * The default date format pattern.
   */
  public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

  /**
   * The default time format pattern.
   */
  public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

  /**
   * The UTC time zone.
   */
  public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

  /**
   * The Chinese Beijing time zone.
   */
  public static final TimeZone BEIJING = TimeZone.getTimeZone("GMT+8");

  /**
   * The UTC time zone ID.
   */
  public static final ZoneId UTC_ZONE_ID = UTC.toZoneId();

  /**
   * The Chinese Beijing time zone ID.
   */
  public static final ZoneId BEIJING_ZONE_ID = BEIJING.toZoneId();

  /**
   * Milliseconds per second.
   */
  public static final long MILLISECONDS_PER_SECOND = 1000L;

  public static boolean toBoolean(@Nullable final Date value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.getTime() != 0));
  }

  public static boolean toBoolean(@Nullable final Date value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.getTime() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final Date value) {
    return (value == null ? null : value.getTime() != 0);
  }

  public static Boolean toBooleanObject(@Nullable final Date value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue :
            Boolean.valueOf(value.getTime() != 0));
  }

  public static char toChar(@Nullable final Date value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.getTime());
  }

  public static char toChar(@Nullable final Date value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.getTime());
  }

  public static Character toCharObject(@Nullable final Date value) {
    return (value == null ? null : (char) value.getTime());
  }

  public static Character toCharObject(@Nullable final Date value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue :
            Character.valueOf((char) value.getTime()));
  }

  public static byte toByte(@Nullable final Date value) {
    return (value == null ? ByteUtils.DEFAULT : (byte) value.getTime());
  }

  public static byte toByte(@Nullable final Date value,
      final byte defaultValue) {
    return (value == null ? defaultValue : (byte) value.getTime());
  }

  public static Byte toByteObject(@Nullable final Date value) {
    return (value == null ? null : (byte) value.getTime());
  }

  public static Byte toByteObject(@Nullable final Date value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue :
            Byte.valueOf((byte) value.getTime()));
  }

  public static short toShort(@Nullable final Date value) {
    return (value == null ? IntUtils.DEFAULT : (short) value.getTime());
  }

  public static short toShort(@Nullable final Date value,
      final short defaultValue) {
    return (value == null ? defaultValue : (short) value.getTime());
  }

  public static Short toShortObject(@Nullable final Date value) {
    return (value == null ? null : (short) value.getTime());
  }

  public static Short toShortObject(@Nullable final Date value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue :
            Short.valueOf((short) value.getTime()));
  }

  public static int toInt(@Nullable final Date value) {
    return (value == null ? IntUtils.DEFAULT : (int) value.getTime());
  }

  public static int toInt(@Nullable final Date value, final int defaultValue) {
    return (value == null ? defaultValue : (int) value.getTime());
  }

  public static Integer toIntObject(@Nullable final Date value) {
    return (value == null ? null : (int) value.getTime());
  }

  public static Integer toIntObject(@Nullable final Date value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue :
            Integer.valueOf((int) value.getTime()));
  }

  public static long toLong(@Nullable final Date value) {
    return (value == null ? LongUtils.DEFAULT : value.getTime());
  }

  public static long toLong(@Nullable final Date value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.getTime());
  }

  public static Long toLongObject(@Nullable final Date value) {
    return (value == null ? null : value.getTime());
  }

  public static Long toLongObject(@Nullable final Date value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.getTime()));
  }

  public static float toFloat(@Nullable final Date value) {
    return (value == null ? FloatUtils.DEFAULT : (float) value.getTime());
  }

  public static float toFloat(@Nullable final Date value,
      final float defaultValue) {
    return (value == null ? defaultValue : (float) value.getTime());
  }

  public static Float toFloatObject(@Nullable final Date value) {
    return (value == null ? null : (float) value.getTime());
  }

  public static Float toFloatObject(@Nullable final Date value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.getTime()));
  }

  public static double toDouble(@Nullable final Date value) {
    return (value == null ? DoubleUtils.DEFAULT : (double) value.getTime());
  }

  public static double toDouble(@Nullable final Date value,
      final double defaultValue) {
    return (value == null ? defaultValue : (double) value.getTime());
  }

  public static Double toDoubleObject(@Nullable final Date value) {
    return (value == null ? null : (double) value.getTime());
  }

  public static Double toDoubleObject(@Nullable final Date value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.getTime()));
  }

  public static String toString(@Nullable final Date value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  public static String toString(@Nullable final Date value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final DateFormat df = new DateFormat(pattern);
    return df.format(value);
  }

  public static byte[] toByteArray(@Nullable final Date value) {
    return (value == null ? null :
            LongUtils.toByteArray(value.getTime(), DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Date value,
      final ByteOrder byteOrder) {
    return (value == null ? null :
            LongUtils.toByteArray(value.getTime(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Date value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue :
            LongUtils.toByteArray(value.getTime(), DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Date value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue :
            LongUtils.toByteArray(value.getTime(), byteOrder));
  }

  public static Class<?> toClass(@Nullable final Date value) {
    return (value == null ? null : Date.class);
  }

  public static Class<?> toClass(@Nullable final Date value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Date.class);
  }

  public static BigInteger toBigInteger(@Nullable final Date value) {
    return (value == null ? null : BigInteger.valueOf(value.getTime()));
  }

  public static BigInteger toBigInteger(@Nullable final Date value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.getTime()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Date value) {
    return (value == null ? null : BigDecimal.valueOf(value.getTime()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Date value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.getTime()));
  }

  /**
   * Creates a {@link Date} for a specified date in the specified time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Date getDate(final int year, final int month, final int day,
      final TimeZone timeZone) {
    final LocalDate date = LocalDate.of(year, month, day);
    final Instant instant = date.atStartOfDay(timeZone.toZoneId()).toInstant();
    return Date.from(instant);
  }

  /**
   * Creates a {@link Date} for a specified date in the default time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Date getDate(final int year, final int month, final int day) {
    return getDate(year, month, day, TimeZone.getDefault());
  }

  /**
   * Creates a {@link Date} for a specified date in the UTC time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Date getUtcDate(final int year, final int month,
      final int day) {
    return getDate(year, month, day, UTC);
  }

  /**
   * Creates a {@link Date} for a specified date time in the specified time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Date} object corresponds to the specified date.
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
   * Creates a {@link Date} for a specified date time in the specified time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param millisecond
   *     the millisecond in the second, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Date} object corresponds to the specified date.
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
   * Creates a {@link Date} for a specified date time in the default time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Date getDateTime(final int year, final int month, final int day,
      final int hour, final int minute, final int second) {
    return getDateTime(year, month, day, hour, minute, second,
        TimeZone.getDefault());
  }

  /**
   * Creates a {@link Date} for a specified date time in the UTC time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Date getUtcDateTime(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getDateTime(year, month, day, hour, minute, second, UTC);
  }

  /**
   * Creates a {@link Date} for a specified date time in the UTC time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param millisecond
   *     the millisecond in the second, starting from 0.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Date getUtcDateTime(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond) {
    return getDateTime(year, month, day, hour, minute, second, millisecond,
        UTC);
  }

  /**
   * Creates a {@link Time} for a specified time in the specified time zone.
   *
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Date} object corresponds to the specified date.
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
   * Creates a {@link Time} for a specified time in the default time zone.
   *
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Time getTime(final int hour, final int minute,
      final int second) {
    return getTime(hour, minute, second, TimeZone.getDefault());
  }

  /**
   * Creates a {@link Time} for a specified time in the UTC time zone.
   *
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link Date} object corresponds to the specified date.
   */
  public static Time getUtcTime(final int hour, final int minute,
      final int second) {
    return getTime(hour, minute, second, UTC);
  }

  /**
   * Creates a {@link Timestamp} for a specified date time in the specified time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Timestamp} object corresponds to the specified date
   *     time.
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
   * Creates a {@link Timestamp} for a specified date time in the specified time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param millisecond
   *     the millisecond in the second, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Timestamp} object corresponds to the specified date
   *     time.
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
   * Creates a {@link Timestamp} for a specified date time in the default time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link Timestamp} object corresponds to the specified date
   *     time.
   */
  public static Timestamp getTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getTimestamp(year, month, day, hour, minute, second,
        TimeZone.getDefault());
  }

  /**
   * Creates a {@link Timestamp} for a specified date time in the UTC time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link Timestamp} object corresponds to the specified date
   *     time.
   */
  public static Timestamp getUtcTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getTimestamp(year, month, day, hour, minute, second, UTC);
  }

  /**
   * Creates a {@link Timestamp} for a specified date time in the UTC time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param millisecond
   *     the millisecond in the second, starting from 0.
   * @return the {@link Timestamp} object corresponds to the specified date
   *     time.
   */
  public static Timestamp getUtcTimestamp(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond) {
    return getTimestamp(year, month, day, hour, minute, second, millisecond,
        UTC);
  }

  /**
   * Creates a {@link java.sql.Date} for a specified date time in the specified
   * time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link Date} object corresponds to the specified date.
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
   * Creates a {@link java.sql.Date} for a specified date time in the specified
   * time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param millisecond
   *     the millisecond in the second, starting from 0.
   * @param timeZone
   *     a specified time zone.
   * @return the {@link java.sql.Date} object corresponds to the specified date.
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
   * Creates a {@link java.sql.Date} for a specified date time in the default
   * time zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link java.sql.Date} object corresponds to the specified date.
   */
  public static java.sql.Date getSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getSqlDate(year, month, day, hour, minute, second,
        TimeZone.getDefault());
  }

  /**
   * Creates a {@link java.sql.Date} for a specified date time in the UTC time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @return the {@link java.sql.Date} object corresponds to the specified date.
   */
  public static java.sql.Date getUtcSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second) {
    return getSqlDate(year, month, day, hour, minute, second, UTC);
  }

  /**
   * Creates a {@link java.sql.Date} for a specified date time in the UTC time
   * zone.
   *
   * <p><b>NOTE:</b> the month is starting from 1 instead of 0.
   *
   * @param year
   *     the year.
   * @param month
   *     the month, starting from 1.
   * @param day
   *     the day in the month, starting from 1.
   * @param hour
   *     the hour in the day, starting from 0.
   * @param minute
   *     the minute in the hour, starting from 0.
   * @param second
   *     the second in the minute, starting from 0.
   * @param millisecond
   *     the millisecond in the second, starting from 0.
   * @return the {@link java.sql.Date} object corresponds to the specified date.
   */
  public static java.sql.Date getUtcSqlDate(final int year, final int month,
      final int day, final int hour, final int minute, final int second,
      final int millisecond) {
    return getSqlDate(year, month, day, hour, minute, second, millisecond, UTC);
  }

  /**
   * Drops the milliseconds of a date time.
   *
   * @param value
   *     the specified date time.
   * @return another {@link Date} object which drops the milliseconds of the
   *     specified date time, or {@code null} if the specified object is null.
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
   * Drops the nanoseconds of a local date time.
   *
   * @param value
   *     the specified date time.
   * @return another {@link LocalDateTime} object which drops the nanoseconds of
   *     the specified date time, or {@code null} if the specified object is
   *     null.
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
   * Drops the milliseconds of a date time.
   *
   * @param value
   *     the specified date time, whose milliseconds will be dropped.
   */
  public static void dropMillisecondsInPlace(@Nullable final Date value) {
    if (value != null) {
      final long milliseconds = value.getTime();
      value.setTime(
          MILLISECONDS_PER_SECOND * (milliseconds / MILLISECONDS_PER_SECOND));
    }
  }

  /**
   * Drops the nanoseconds of a local date time.
   *
   * @param value
   *     the specified date time, whose nanoseconds will be dropped.
   */
  public static void dropNanosecondsInPlace(
      @Nullable final LocalDateTime value) {
    if (value != null) {
      final long nanos = value.getNano();
      value.minusNanos(nanos);
    }
  }

  /**
   * Truncates a {@link java.util.Date} to the specified time unit.
   *
   * @param value
   *     the {@link java.util.Date} value to be truncated.
   * @param unit
   *     the specified time unit to be truncated to.
   * @return a new copy of {@link java.util.Date} object of the original
   *     {@code value} whose value is truncated to the specified time unit.
   */
  public static Date truncate(final Date value, final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    return new Date(result);
  }

  /**
   * Truncates a {@link java.sql.Date} to the specified time unit.
   *
   * @param value
   *     the {@link Date} value to be truncated.
   * @param unit
   *     the specified time unit to be truncated to.
   * @return a new copy of {@link java.sql.Date} object of the original
   *     {@code value} whose value is truncated to the specified time unit.
   */
  public static java.sql.Date truncate(final java.sql.Date value,
      final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    return new java.sql.Date(result);
  }

  /**
   * Truncates a {@link java.util.Date} to the specified time unit.
   *
   * @param value
   *     the {@link java.util.Date} value to be truncated.
   * @param unit
   *     the specified time unit to be truncated to.
   * @return a reference to the original {@link java.util.Date} object
   *     {@code value} whose value is truncated to the specified time unit.
   */
  public static Date truncateInPlace(final Date value, final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    value.setTime(result);
    return value;
  }

  /**
   * Truncates a {@link java.sql.Date} to the specified time unit.
   *
   * @param value
   *     the {@link java.sql.Date} value to be truncated.
   * @param unit
   *     the specified time unit to be truncated to.
   * @return a reference to the original {@link java.sql.Date} object
   *     {@code value} whose value is truncated to the specified time unit.
   */
  public static java.sql.Date truncateInPlace(final java.sql.Date value,
      final TimeUnit unit) {
    final long millis = value.getTime();
    final long result = unit.convert(millis, TimeUnit.MILLISECONDS);
    value.setTime(result);
    return value;
  }

  /**
   * Converts a {@link TimeUnit} to the corresponding {@link ChronoUnit}.
   *
   * <p><b>NOTE:</b> this function is useless in the JDK above 1.9, since from
   * JDK 1.9 the {@link TimeUnit} class has a new method called
   * {@code TimeUnit.toChronoUnit()}. But this function is still useful under
   * the JDK 1.8 or below.
   *
   * @param unit
   *     a {@link TimeUnit}.
   * @return the corresponding {@link ChronoUnit}.
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
