////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A {@link DatePattern} defines a rule used to extract the date from a string.
 * Each {@link DatePattern} object has a regular expression pattern used to
 * capture the date string, and a date format pattern used to parse the date
 * value form the date string, and an optional locale value used to specifies
 * the locale used for parsing the date string. Note that the blank space in the
 * captured date string will be compacted, and the line break in the captured
 * date string will be folded (i.e., be replaced by a single space). So there
 * should not be duplicated spaces in the date format pattern.
 *
 * @author Haixing Hu
 */
public final class DatePattern implements CloneableEx<DatePattern>, Serializable {

  private static final long serialVersionUID = 7257400722091068140L;

  private static final Logger LOGGER = LoggerFactory.getLogger(DatePattern.class);

  private static final String INVALID_PATTERN_SYNTAX =
      "Invalid pattern syntax of the date format: '{}'";

  private static final String CAN_NOT_MATCH_PATTERN =
      "Can't match the date format pattern '{}' against the string '{}'.";

  private static final String CAN_NOT_MATCH_DATE_FORAMT =
      "The date string '{}' does not match the date format '{}'";

  private static final int FLAGS = Pattern.DOTALL;

  static {
    BinarySerialization.register(DatePattern.class, DatePatternBinarySerializer.INSTANCE);
    XmlSerialization.register(DatePattern.class, DatePatternXmlSerializer.INSTANCE);
  }

  protected String pattern;
  protected String format;
  protected Locale locale;
  protected transient Matcher matcher;
  protected transient SimpleDateFormat dateFormat;

  public DatePattern() {
    pattern = StringUtils.EMPTY;
    format = StringUtils.EMPTY;
    locale = null;
    matcher = null;
    dateFormat = null;
  }

  public DatePattern(final String pattern, final String format) {
    this.pattern = requireNonNull("pattern", pattern);
    this.format = requireNonNull("format", format);
    locale = null;
    matcher = null;
    dateFormat = null;
  }

  public DatePattern(
          final String pattern, final String format, @Nullable final Locale locale) {
    this.pattern = requireNonNull("pattern", pattern);
    this.format = requireNonNull("format", format);
    this.locale = locale;
    matcher = null;
    dateFormat = null;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(final String pattern) {
    this.pattern = requireNonNull("pattern", pattern);
    matcher = null;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(final String format) {
    this.format = requireNonNull("format", format);
    dateFormat = null;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(@Nullable final Locale locale) {
    this.locale = locale;
    dateFormat = null;
  }

  /**
   * Extracts the date from a string using this date format.
   *
   * @param str
   *     a string.
   * @return the date extracted from the string using this date format; or null
   *     if the date can not be successfully extracted.
   */
  public Date match(final String str) {
    LOGGER.debug("Matching date against '{}'", str);
    if (matcher == null) {
      try {
        matcher = Pattern.compile(pattern, FLAGS).matcher(str);
      } catch (final PatternSyntaxException e) {
        LOGGER.error(INVALID_PATTERN_SYNTAX, pattern);
        return null;
      }
    } else {
      matcher.reset(str);
    }
    if (! matcher.find()) {
      LOGGER.warn(CAN_NOT_MATCH_PATTERN, pattern, str);
      return null;
    }
    String dateStr = matcher.group();
    dateStr = StringUtils.normalizeSpace(dateStr, true);
    LOGGER.debug("Parsing date from '{}'", dateStr);
    if (dateFormat == null) {
      if (locale == null) {
        dateFormat = new SimpleDateFormat(format);
      } else {
        dateFormat = new SimpleDateFormat(format, locale);
      }
    }
    try {
      return dateFormat.parse(dateStr);
    } catch (final ParseException e) {
      LOGGER.error(CAN_NOT_MATCH_DATE_FORAMT, dateStr, format);
      return null;
    }
  }

  @Override
  public DatePattern cloneEx() {
    return new DatePattern(pattern, format, locale);
  }

  @Override
  public int hashCode() {
    final int multiplier = 171;
    int code = 31;
    code = Hash.combine(code, multiplier, pattern);
    code = Hash.combine(code, multiplier, format);
    code = Hash.combine(code, multiplier, locale);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DatePattern other = (DatePattern) obj;
    return pattern.equals(other.pattern)
          && format.equals(other.format)
          && Equality.equals(locale, other.locale);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("pattern", pattern)
               .append("format", format)
               .append("locale", locale)
               .toString();
  }
}
