////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ltd.qubit.commons.lang.DateUtils;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The {@link DateFormat} is used to parse and format {@link Date} values.
 * <p>
 * This class is similar to the {@link SimpleDateFormat} class, except
 * that it provides more convenient parsing and formatting functions.
 * </p>
 * <p>
 * The implementation of this class copies from the source code of the
 * {@link SimpleDateFormat} class.
 * </p>
 *
 * @author Haixing Hu
 */
public final class DateFormat {

  /**
   * The default date format pattern.
   */
  public static final String DEFAULT_PATTERN = DateUtils.DEFAULT_DATETIME_PATTERN;

  /**
   * The default locale of dates.
   */
  public static final Locale DEFAULT_LOCALE = Locale.US;

  public static final boolean DEFAULT_SKIP_BLANKS = true;

  private final String pattern;
  private final SimpleDateFormat format;
  private final ParsingPosition position;
  private boolean skipBlanks;

  public DateFormat() {
    this(DEFAULT_PATTERN, DEFAULT_LOCALE);
  }

  public DateFormat(final Locale locale) {
    this(DEFAULT_PATTERN, locale);
  }

  public DateFormat(final String pattern) {
    this(pattern, DEFAULT_LOCALE);
  }

  public DateFormat(final String pattern, final TimeZone timeZone) {
    this.pattern = requireNonNull("pattern", pattern);
    format = new SimpleDateFormat(pattern);
    format.setTimeZone(requireNonNull("timeZone", timeZone));
    position = new ParsingPosition();
    skipBlanks = DEFAULT_SKIP_BLANKS;
  }

  public DateFormat(final String pattern, final Locale locale) {
    this.pattern = requireNonNull("pattern", pattern);
    format = new SimpleDateFormat(pattern, requireNonNull("locale", locale));
    position = new ParsingPosition();
    skipBlanks = DEFAULT_SKIP_BLANKS;
  }

  public DateFormat(final String pattern, final Locale locale, final TimeZone timeZone) {
    this.pattern = requireNonNull("pattern", pattern);
    format = new SimpleDateFormat(pattern, requireNonNull("locale", locale));
    format.setTimeZone(requireNonNull("timeZone", timeZone));
    position = new ParsingPosition();
    skipBlanks = DEFAULT_SKIP_BLANKS;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(final String pattern) {
    requireNonNull("pattern", pattern);
    if (! this.pattern.equals(pattern)) {
      format.applyPattern(pattern);
    }
  }

  public TimeZone getTimeZone() {
    return format.getTimeZone();
  }

  public void setTimeZone(final TimeZone timeZone) {
    format.setTimeZone(requireNonNull("timeZone", timeZone));
  }

  public boolean isSkipBlanks() {
    return skipBlanks;
  }

  public void setSkipBlanks(final boolean skipBlanks) {
    this.skipBlanks = skipBlanks;
  }

  /**
   * Returns the beginning date of the 100-year period 2-digit years are
   * interpreted as being within.
   *
   * @return the start of the 100-year period into which two digit years are
   *         parsed
   * @see #set2DigitYearStart
   */
  public Date get2DigitYearStart() {
    return format.get2DigitYearStart();
  }

  /**
   * Sets the 100-year period 2-digit years will be interpreted as being in to
   * begin on the date the user specifies.
   *
   * @param startDate
   *          During parsing, two digit years will be placed in the range
   *          {@code startDate} to {@code startDate + 100 years}.
   * @see #get2DigitYearStart
   */
  public void set2DigitYearStart(final Date startDate) {
    format.set2DigitYearStart(startDate);
  }

  public ParsingPosition getParsePosition() {
    return position;
  }

  public boolean success() {
    return position.success();
  }

  public boolean fail() {
    return position.fail();
  }

  public int getParseIndex() {
    return position.getIndex();
  }

  public int getErrorIndex() {
    return position.getErrorIndex();
  }

  public int getErrorCode() {
    return position.getErrorCode();
  }

  /**
   * Parses a {@link Date} value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index {@code 0} and stops at the character
   *          before the index {@code str.length()}.
   * @return the parsed value.
   */
  public Date parse(final String str) {
    return parse(str, 0, str.length());
  }

  /**
   * Parses a {@link Date} value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index {@code startIndex} and stops at the
   *          character before the index {@code str.length()}.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public Date parse(final String str, final int startIndex) {
    return parse(str, startIndex, str.length());
  }

  /**
   * Parses a {@link Date} value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index {@code startIndex} and stops at the
   *          character before the index {@code endIndex}.
   * @param startIndex
   *          the index where to start parsing.
   * @param endIndex
   *          the index where to end parsing.
   * @return the parsed value.
   */
  public Date parse(final String str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(startIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (skipBlanks) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return null;
      }
    }
    final Date value = format.parse(str, position);
    if (value == null) {
      position.setErrorCode(ErrorCode.INVALID_SYNTAX);
    }
    return value;
  }

  /**
   * Formats a {@link Date} value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String format(final Date value) {
    requireNonNull("value", value);
    return format.format(value);
  }

  /**
   * Formats a {@link Date} value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
   */
  public StringBuilder format(final Date value, final StringBuilder output) {
    requireNonNull("value", value);
    final String str = format.format(value);
    return output.append(str);
  }
}