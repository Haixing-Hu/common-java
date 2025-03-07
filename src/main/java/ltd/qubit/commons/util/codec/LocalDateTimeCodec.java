////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * The codec which encode/decode {@link LocalDateTime} objects to/from strings.
 *
 * @author Haixing Hu
 * @see <a href="https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era">
 * What is the difference between year and year-of-era?</a>
 */
public class LocalDateTimeCodec implements Codec<LocalDateTime, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimeCodec.class);

  // NOTE that 'u' represent the year, while 'y' represent the year-of-era.
  // see: https://stackoverflow.com/questions/29014225/what-is-the-difference-between-year-and-year-of-era
  public static final String DEFAULT_ENCODE_PATTERN = "uuuu-MM-dd HH:mm:ss";

  public static final String[] DEFAULT_DECODE_PATTERNS = {
      "uuuu-MM-dd HH:mm:ss.SSS",
      "uuuu-MM-dd'T'HH:mm:ss.SSS",
      "uuuu-MM-dd HH:mm:ss.SS",
      "uuuu-MM-dd'T'HH:mm:ss.SS",
      "uuuu-MM-dd HH:mm:ss.S",
      "uuuu-MM-dd'T'HH:mm:ss.S",
      "uuuu-MM-dd HH:mm:ss",
      "uuuu-MM-dd'T'HH:mm:ss",
      "uuuu-MM-dd HH:mm",
      "uuuu-MM-dd'T'HH:mm",
      "uuuu-MM-dd",
  };

  public static final boolean DEFAULT_EMPTY_FOR_NULL = false;

  public static final boolean DEFAULT_STRIP_BEFORE_PARSING = true;

  public static final LocalDateTimeCodec INSTANCE = new LocalDateTimeCodec();

  private String encodePattern;
  private String[] decodePatterns;
  private boolean emptyForNull;
  private boolean trim;
  private transient DateTimeFormatter encodeFormatter;
  private transient DateTimeFormatter[] decodeFormatters;

  public LocalDateTimeCodec() {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERNS, DEFAULT_EMPTY_FOR_NULL, DEFAULT_STRIP_BEFORE_PARSING);
  }

  public LocalDateTimeCodec(@Nonnull final String pattern) {
    this(pattern, new String[]{ pattern }, DEFAULT_EMPTY_FOR_NULL, DEFAULT_STRIP_BEFORE_PARSING);
  }

  public LocalDateTimeCodec(@Nonnull final String pattern, final boolean emptyForNull) {
    this(pattern, new String[]{ pattern }, emptyForNull, DEFAULT_STRIP_BEFORE_PARSING);
  }

  public LocalDateTimeCodec(@Nonnull final String pattern, final boolean emptyForNull,
          final boolean trim) {
    this(pattern, new String[]{ pattern }, emptyForNull, trim);
  }

  public LocalDateTimeCodec(final boolean emptyForNull) {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERNS, emptyForNull,
            DEFAULT_STRIP_BEFORE_PARSING);
  }

  public LocalDateTimeCodec(final boolean emptyForNull, final boolean trim) {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERNS, emptyForNull, trim);
  }

  public LocalDateTimeCodec(@Nonnull final String encodePattern,
      @Nonnull final String decodePattern, final boolean emptyForNull) {
    this(encodePattern, new String[]{ decodePattern }, emptyForNull, DEFAULT_STRIP_BEFORE_PARSING);
  }

  public LocalDateTimeCodec(@Nonnull final String encodePattern,
      @Nonnull final String[] decodePatterns, final boolean emptyForNull) {
    this(encodePattern, decodePatterns, emptyForNull, DEFAULT_STRIP_BEFORE_PARSING);
  }

  public LocalDateTimeCodec(@Nonnull final String encodePattern,
      @Nonnull final String decodePattern, final boolean emptyForNull,
      final boolean trim) {
    this(encodePattern, new String[]{ decodePattern }, emptyForNull, trim);
  }

  public LocalDateTimeCodec(@Nonnull final String encodePattern,
      @Nonnull final String[] decodePatterns, final boolean emptyForNull,
      final boolean trim) {
    this.encodePattern = requireNonNull("encodePattern", encodePattern);
    this.decodePatterns = requireNonNull("decodePatterns", decodePatterns);
    this.emptyForNull = emptyForNull;
    this.trim = trim;
    this.encodeFormatter = DateTimeFormatter.ofPattern(encodePattern);
    this.decodeFormatters = new DateTimeFormatter[decodePatterns.length];
    for (int i = 0; i < decodePatterns.length; ++i) {
      this.decodeFormatters[i] = DateTimeFormatter.ofPattern(decodePatterns[i]);
    }
  }

  public final String getEncodePattern() {
    return encodePattern;
  }

  public final LocalDateTimeCodec setEncodePattern(final String encodePattern) {
    this.encodePattern = requireNonNull("encodePattern", encodePattern);
    this.encodeFormatter = DateTimeFormatter.ofPattern(encodePattern);
    return this;
  }

  public final String[] getDecodePatterns() {
    return decodePatterns;
  }

  public final LocalDateTimeCodec setDecodePatterns(final String[] decodePatterns) {
    requireNonNull("decodePatterns", decodePatterns);
    final DateTimeFormatter[] formatters = new DateTimeFormatter[decodePatterns.length];
    for (int i = 0; i < decodePatterns.length; ++i) {
      formatters[i] = DateTimeFormatter.ofPattern(decodePatterns[i]);
    }
    this.decodePatterns = decodePatterns;
    this.decodeFormatters = formatters;
    return this;
  }

  public final boolean isEmptyForNull() {
    return emptyForNull;
  }

  public final LocalDateTimeCodec setEmptyForNull(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    return this;
  }

  public final boolean isTrim() {
    return trim;
  }

  public final LocalDateTimeCodec setTrim(final boolean trim) {
    this.trim = trim;
    return this;
  }

  @Override
  public String encode(@Nullable final LocalDateTime value) {
    if (value == null) {
      return (emptyForNull ? StringUtils.EMPTY : null);
    } else {
      return value.format(encodeFormatter);
    }
  }

  @Override
  public LocalDateTime decode(@Nullable final String str) throws DecodingException {
    final String text = (trim ? new Stripper().strip(str) : str);
    if (isEmpty(text)) {
      return null;
    } else {
      LOGGER.debug("Parsing date: {}", text);
      final TemporalAccessor temporal = parseText(text);
      if (temporal == null) {
        LOGGER.error("Invalid date time format: '{}', expected {}", text, decodePatterns);
        throw new DecodingException("Invalid date time format: " + text);
      }
      if (temporal instanceof final LocalDate date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0);
      } else if (temporal instanceof LocalDateTime) {
        return (LocalDateTime) temporal;
      } else if (temporal instanceof final ZonedDateTime datetime) {
        // use the system default time zone.
        return LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
      } else {
        LOGGER.error("Unsupported time type {} while parsing {}", temporal.getClass(), text);
        throw new DecodingException("Unsupported time type "
            + temporal.getClass() + " while parsing " + text);
      }
    }
  }

  private TemporalAccessor parseText(final String text) {
    for (int i = 0; i < decodeFormatters.length; ++i) {
      final DateTimeFormatter formatter = decodeFormatters[i];
      try {
        return formatter.parseBest(text, ZonedDateTime::from, LocalDateTime::from, LocalDate::from);
      } catch (final DateTimeParseException e) {
        LOGGER.trace("Failed to parse date time: pattern = {}, text = '{}'",
            decodePatterns[i], text, e);
      }
    }
    return null;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LocalDateTimeCodec other = (LocalDateTimeCodec) o;
    return Equality.equals(emptyForNull, other.emptyForNull)
            && Equality.equals(trim, other.trim)
            && Equality.equals(encodePattern, other.encodePattern)
            && Equality.equals(decodePatterns, other.decodePatterns);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, encodePattern);
    result = Hash.combine(result, multiplier, decodePatterns);
    result = Hash.combine(result, multiplier, emptyForNull);
    result = Hash.combine(result, multiplier, trim);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("encodePattern", encodePattern)
            .append("decodePatterns", decodePatterns)
            .append("emptyForNull", emptyForNull)
            .append("trim", trim)
            .toString();
  }
}