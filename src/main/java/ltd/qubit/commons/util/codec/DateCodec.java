////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ltd.qubit.commons.lang.DateUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Stripper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.DateUtils.getDate;

/**
 * The codec which encode/decode {@link Date} objects to/from strings.
 *
 * @author Haixing Hu
 * @see <a href="https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era">
 * What is the difference between year and year-of-era?</a>
 */
public class DateCodec implements Codec<Date, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateCodec.class);

  // NOTE that 'u' represent the year, while 'y' represent the year-of-era.
  // see: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era
  public static final String DEFAULT_ENCODE_PATTERN = "uuuu-MM-dd'T'HH:mm:ss'Z'";

  public static final String DEFAULT_DECODE_PATTERN =
      "uuuu-MM-dd[[' ']['T']HH:mm[':'ss[.SSS]]][' ']['Z'][Z][z]";

  public static final ZoneId DEFAULT_ZONE_ID = DateUtils.UTC_ZONE_ID;

  public static final boolean DEFAULT_EMPTY_FOR_NULL = false;

  private String encodePattern;
  private String decodePattern;
  private ZoneId zoneId;
  private boolean emptyForNull;
  private transient DateTimeFormatter encodeFormatter;
  private transient DateTimeFormatter decodeFormatter;

  public DateCodec() {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERN, DEFAULT_ZONE_ID,
            DEFAULT_EMPTY_FOR_NULL);
  }

  public DateCodec(@Nonnull final String pattern, final boolean emptyForNull) {
    this(pattern, pattern, DEFAULT_ZONE_ID, emptyForNull);
  }

  public DateCodec(@Nonnull final String pattern, final ZoneId zoneId, final boolean emptyForNull) {
    this(pattern, pattern, zoneId, emptyForNull);
  }

  public DateCodec(final ZoneId zoneId, final boolean emptyForNull) {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERN, zoneId, emptyForNull);
  }

  public DateCodec(@Nonnull final String encodePattern, @Nonnull final String decodePattern,
          final ZoneId zoneId, final boolean emptyForNull) {
    this.encodePattern = requireNonNull("encodePattern", encodePattern);
    this.decodePattern = requireNonNull("decodePattern", decodePattern);
    this.zoneId = requireNonNull("zoneId", zoneId);
    this.emptyForNull = emptyForNull;
    this.encodeFormatter = DateTimeFormatter.ofPattern(encodePattern);
    this.decodeFormatter = DateTimeFormatter.ofPattern(decodePattern);
  }

  public final String getEncodePattern() {
    return encodePattern;
  }

  public final DateCodec setEncodePattern(final String encodePattern) {
    this.encodePattern = requireNonNull("encodePattern", encodePattern);
    this.encodeFormatter = DateTimeFormatter.ofPattern(encodePattern);
    return this;
  }

  public final String getDecodePattern() {
    return decodePattern;
  }

  public final DateCodec setDecodePattern(final String decodePattern) {
    this.decodePattern = requireNonNull("decodePattern", decodePattern);
    this.decodeFormatter = DateTimeFormatter.ofPattern(decodePattern);
    return this;
  }

  public final ZoneId getZoneId() {
    return zoneId;
  }

  public final DateCodec setZoneId(final ZoneId zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  public final boolean isEmptyForNull() {
    return emptyForNull;
  }

  public final DateCodec setEmptyForNull(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    return this;
  }

  @Override
  public String encode(@Nullable final Date value) {
    if (value == null) {
      return (emptyForNull ? StringUtils.EMPTY : null);
    } else {
      // there is a JDK8 bug, Date.toInstant() does not work.
      final long mills = value.getTime();
      final ZonedDateTime time = ZonedDateTime.ofInstant(Instant.ofEpochMilli(mills), zoneId);
      return time.format(encodeFormatter);
    }
  }

  @Override
  public Date decode(@Nullable final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    } else {
      LOGGER.debug("Parsing date: {}", text);
      try {
        final TimeZone zone = TimeZone.getTimeZone(zoneId);
        final TemporalAccessor temporal = decodeFormatter.parseBest(text,
                ZonedDateTime::from,
                LocalDateTime::from, LocalDate::from);
        if (temporal instanceof LocalDate) {
          final LocalDate date = (LocalDate) temporal;
          return getDate(date.getYear(), date.getMonthValue(),
                  date.getDayOfMonth(), zone);
        } else if (temporal instanceof LocalDateTime) {
          final LocalDateTime time = (LocalDateTime) temporal;
          return Date.from(time.atZone(zone.toZoneId()).toInstant());
        } else if (temporal instanceof ZonedDateTime) {
          final ZonedDateTime time = (ZonedDateTime) temporal;
          return Date.from(time.toInstant());
        } else {
          LOGGER.error("Unsupported time type {} while parsing {}",
                  temporal.getClass(), text);
          throw new DecodingException("Unsupported time type "
            + temporal.getClass() + " while parsing " + text);
        }
      } catch (final DateTimeParseException e) {
        LOGGER.error("Invalid date time format: {}, expected {}", text, decodePattern);
        throw new DecodingException(e);
      }
    }
  }
}
