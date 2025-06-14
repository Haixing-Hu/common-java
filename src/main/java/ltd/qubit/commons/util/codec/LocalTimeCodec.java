////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
 * The codec which encode/decode {@link LocalTime} objects to/from strings.
 *
 * @author Haixing Hu
 */
public class LocalTimeCodec implements Codec<LocalTime, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalTimeCodec.class);

  public static final String DEFAULT_ENCODE_PATTERN = "HH:mm:ss";

  public static final String DEFAULT_DECODE_PATTERN = "HH:mm:ss[.SSS]";

  public static final boolean DEFAULT_TRIM = true;

  public static final boolean DEFAULT_EMPTY_FOR_NULL = false;

  private String encodePattern;
  private String decodePattern;
  private boolean emptyForNull;
  private boolean trim;
  private transient DateTimeFormatter encodeFormatter;
  private transient DateTimeFormatter decodeFormatter;

  public LocalTimeCodec() {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERN, DEFAULT_EMPTY_FOR_NULL,
        DEFAULT_TRIM);
  }

  public LocalTimeCodec(@Nonnull final String pattern, final boolean emptyForNull) {
    this(pattern, pattern, emptyForNull, DEFAULT_TRIM);
  }

  public LocalTimeCodec(@Nonnull final String pattern, final boolean emptyForNull,
      final boolean trim) {
    this(pattern, pattern, emptyForNull, trim);
  }

  public LocalTimeCodec(final boolean emptyForNull) {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERN, emptyForNull,
        DEFAULT_TRIM);
  }

  public LocalTimeCodec(final boolean emptyForNull, final boolean trim) {
    this(DEFAULT_ENCODE_PATTERN, DEFAULT_DECODE_PATTERN, emptyForNull, trim);
  }

  public LocalTimeCodec(@Nonnull final String encodePattern,
      @Nonnull final String decodePattern, final boolean emptyForNull) {
    this(encodePattern, decodePattern, emptyForNull, DEFAULT_TRIM);
  }

  public LocalTimeCodec(@Nonnull final String encodePattern,
      @Nonnull final String decodePattern, final boolean emptyForNull,
      final boolean trim) {
    this.encodePattern = requireNonNull("encodePattern", encodePattern);
    this.decodePattern = requireNonNull("decodePattern", decodePattern);
    this.emptyForNull = emptyForNull;
    this.trim = trim;
    this.encodeFormatter = DateTimeFormatter.ofPattern(encodePattern);
    this.decodeFormatter = DateTimeFormatter.ofPattern(decodePattern);
  }

  public final String getEncodePattern() {
    return encodePattern;
  }

  public final LocalTimeCodec setEncodePattern(final String encodePattern) {
    this.encodePattern = requireNonNull("encodePattern", encodePattern);
    this.encodeFormatter = DateTimeFormatter.ofPattern(encodePattern);
    return this;
  }

  public final String getDecodePattern() {
    return decodePattern;
  }

  public final LocalTimeCodec setDecodePattern(final String decodePattern) {
    this.decodePattern = requireNonNull("decodePattern", decodePattern);
    this.decodeFormatter = DateTimeFormatter.ofPattern(decodePattern);
    return this;
  }

  public final boolean isEmptyForNull() {
    return emptyForNull;
  }

  public final LocalTimeCodec setEmptyForNull(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    return this;
  }

  public final boolean isTrim() {
    return trim;
  }

  public final LocalTimeCodec setTrim(final boolean trim) {
    this.trim = trim;
    return this;
  }

  @Override
  public String encode(@Nullable final LocalTime value) {
    if (value == null) {
      return (emptyForNull ? StringUtils.EMPTY : null);
    } else {
      return value.format(encodeFormatter);
    }
  }

  @Override
  public LocalTime decode(@Nullable final String str) throws DecodingException {
    final String text = (trim ? new Stripper().strip(str) : str);
    if (isEmpty(text)) {
      return null;
    } else {
      LOGGER.debug("Parsing date: {}", text);
      try {
        final TemporalAccessor temporal = decodeFormatter.parseBest(text,
                ZonedDateTime::from,
                LocalDateTime::from,
                LocalDate::from,
                LocalTime::from);
        if (temporal instanceof LocalTime) {
          return (LocalTime) temporal;
        } else if (temporal instanceof LocalDate) {
          final LocalDate date = (LocalDate) temporal;
          return LocalTime.of(0, 0);
        } else if (temporal instanceof LocalDateTime) {
          final LocalDateTime datetime = (LocalDateTime) temporal;
          return datetime.toLocalTime();
        } else if (temporal instanceof ZonedDateTime) {
          final ZonedDateTime datetime = (ZonedDateTime) temporal;
          // use the system default time zone.
          final LocalDateTime localDateTime = LocalDateTime.ofInstant(datetime.toInstant(),
                  ZoneId.systemDefault());
          return localDateTime.toLocalTime();
        } else {
          LOGGER.error("Unsupported time type {} while parsing {}",
                  temporal.getClass(), text);
          throw new DecodingException("Unsupported time type "
            + temporal.getClass() + " while parsing " + text);
        }
      } catch (final DateTimeParseException e) {
        LOGGER.error("Invalid time format: {}, expected {}", text, decodePattern);
        throw new DecodingException(e);
      }
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LocalTimeCodec other = (LocalTimeCodec) o;
    return Equality.equals(emptyForNull, other.emptyForNull)
            && Equality.equals(trim, other.trim)
            && Equality.equals(encodePattern, other.encodePattern)
            && Equality.equals(decodePattern, other.decodePattern);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, encodePattern);
    result = Hash.combine(result, multiplier, decodePattern);
    result = Hash.combine(result, multiplier, emptyForNull);
    result = Hash.combine(result, multiplier, trim);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("encodePattern", encodePattern)
            .append("decodePattern", decodePattern)
            .append("emptyForNull", emptyForNull)
            .append("trim", trim)
            .toString();
  }
}