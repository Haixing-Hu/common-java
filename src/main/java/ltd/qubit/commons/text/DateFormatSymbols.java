////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

import static ltd.qubit.commons.lang.Argument.requireLengthBe;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The symbols used to format and parse dates.
 *
 * @author Haixing Hu
 */
public final class DateFormatSymbols implements CloneableEx<DateFormatSymbols> {

  /**
   * Unlocalized date-time pattern characters. For example: 'y', 'd', etc.
   * All locales use the same these unlocalized pattern characters.
   */
  public static final String  PATTERN_CHARS = "GyMdkHmsSEDFwWahKzZ";

  @GuardedBy("DateFormatSymbols.class")
  private static final Map<Locale, java.text.DateFormatSymbols> symbolsCache =
          new HashMap<>();

  private static synchronized java.text.DateFormatSymbols getJdkSymbols(
          final Locale locale) {
    requireNonNull("locale", locale);
    java.text.DateFormatSymbols symbols = symbolsCache.get(locale);
    if (symbols == null) {
      symbols = java.text.DateFormatSymbols.getInstance(locale);
      symbolsCache.put(locale, symbols);
    }
    return symbols;
  }

  /**
   * Era strings. For example: "AD" and "BC".  An array of 2 strings,
   * indexed by {@code Calendar.BC} and {@code Calendar.AD}.
   * @serial
   */
  private String[] eras = null;

  /**
   * Month strings. For example: "January", "February", etc.  An array
   * of 13 strings (some calendars have 13 months), indexed by
   * {@code Calendar.JANUARY}, {@code Calendar.FEBRUARY}, etc.
   * @serial
   */
  private String[] months = null;

  /**
   * Short month strings. For example: "Jan", "Feb", etc.  An array of
   * 13 strings (some calendars have 13 months), indexed by
   * {@code Calendar.JANUARY}, {@code Calendar.FEBRUARY}, etc.

   * @serial
   */
  private String[] shortMonths = null;

  /**
   * Weekday strings. For example: "Sunday", "Monday", etc.  An array
   * of 8 strings, indexed by {@code Calendar.SUNDAY},
   * {@code Calendar.MONDAY}, etc.
   * The element {@code weekdays[0]} is ignored.
   * @serial
   */
  private String[] weekdays = null;

  /**
   * Short weekday strings. For example: "Sun", "Mon", etc.  An array
   * of 8 strings, indexed by {@code Calendar.SUNDAY},
   * {@code Calendar.MONDAY}, etc.
   * The element {@code shortWeekdays[0]} is ignored.
   * @serial
   */
  private String[] shortWeekdays = null;

  /**
   * AM and PM strings. For example: "AM" and "PM".  An array of
   * 2 strings, indexed by {@code Calendar.AM} and
   * {@code Calendar.PM}.
   * @serial
   */
  private String[] ampms = null;

  /**
   * Localized names of time zones in this locale.  This is a
   * two-dimensional array of strings of size <em>n</em> by <em>m</em>,
   * where <em>m</em> is at least 5.  Each of the <em>n</em> rows is an
   * entry containing the localized names for a single {@code TimeZone}.
   * Each such row contains (with {@code i} ranging from
   * 0..<em>n</em>-1):
   * <ul>
   * <li>{@code zoneStrings[i][0]} - time zone ID</li>
   * <li>{@code zoneStrings[i][1]} - long name of zone in standard
   * time</li>
   * <li>{@code zoneStrings[i][2]} - short name of zone in
   * standard time</li>
   * <li>{@code zoneStrings[i][3]} - long name of zone in daylight
   * saving time</li>
   * <li>{@code zoneStrings[i][4]} - short name of zone in daylight
   * saving time</li>
   * </ul>
   * The zone ID is <em>not</em> localized; it's one of the valid IDs of
   * the {@link java.util.TimeZone TimeZone} class that are not
   * <a href="../java/util/TimeZone.html#CustomID">custom IDs</a>.
   * All other entries are localized names.
   * @see java.util.TimeZone
   * @serial
   */
  private String[][] zoneStrings = null;

  /**
   * Localized date-time pattern characters. For example, a locale may
   * wish to use 'u' rather than 'y' to represent years in its date format
   * pattern strings.
   * This string must be exactly 18 characters long, with the index of
   * the characters described by {@code DateFormat.ERA_FIELD},
   * {@code DateFormat.YEAR_FIELD}, etc.  Thus, if the string were
   * "Xz...", then localized patterns would use 'X' for era and 'z' for year.
   * @serial
   */
  private String  localizedPatternChars = null;

  private static final int ERAS_LENGTH = 2;

  private static final int MONTHS_LENGTH = 13;

  private static final int WEEKDAYS_LENGTH = 8;

  private static final int AMPS_LENGTH = 8;

  public DateFormatSymbols() {
    reset();
  }

  public DateFormatSymbols(final Locale locale) {
    reset(locale);
  }

  public void reset() {
    reset(DateFormat.DEFAULT_LOCALE);
  }

  public void reset(final Locale locale) {
    final java.text.DateFormatSymbols symbols = getJdkSymbols(locale);
    eras = symbols.getEras();
    months = symbols.getMonths();
    shortMonths = symbols.getShortMonths();
    weekdays = symbols.getWeekdays();
    shortWeekdays = symbols.getShortWeekdays();
    ampms = symbols.getAmPmStrings();
    zoneStrings = symbols.getZoneStrings();
    localizedPatternChars = symbols.getLocalPatternChars();
  }

  public String[] getEras() {
    return eras;
  }

  public void setEras(final String[] eras) {
    this.eras = requireLengthBe("eras", eras, ERAS_LENGTH);
  }

  public String[] getMonths() {
    return months;
  }

  public void setMonths(final String[] months) {
    this.months = requireLengthBe("months", months, MONTHS_LENGTH);
  }

  public String[] getShortMonths() {
    return shortMonths;
  }

  public void setShortMonths(final String[] shortMonths) {
    this.shortMonths = requireLengthBe("shortMonths", shortMonths, MONTHS_LENGTH);
  }

  public String[] getWeekdays() {
    return weekdays;
  }

  public void setWeekdays(final String[] weekdays) {
    this.weekdays = requireLengthBe("weekdays", weekdays, WEEKDAYS_LENGTH);
  }

  public String[] getShortWeekdays() {
    return shortWeekdays;
  }

  public void setShortWeekdays(final String[] shortWeekdays) {
    this.shortWeekdays = requireLengthBe("shortWeekdays", shortWeekdays, WEEKDAYS_LENGTH);
  }

  public String[] getAmpms() {
    return ampms;
  }

  public void setAmpms(final String[] ampms) {
    this.ampms = requireLengthBe("ampms", ampms, 2);
  }

  public String[][] getZoneStrings() {
    return zoneStrings;
  }

  public void setZoneStrings(final String[][] zoneStrings) {
    this.zoneStrings = requireNonNull("zoneStrings", zoneStrings);
  }

  public String getLocalizedPatternChars() {
    return localizedPatternChars;
  }

  public void setLocalizedPatternChars(final String localizedPatternChars) {
    this.localizedPatternChars = requireNonNull("localizedPatternChars", localizedPatternChars);
  }

  public void assign(final DateFormatSymbols that) {
    if (this == that) {
      return;
    }
    eras = that.eras;
    months = that.months;
    shortMonths = that.shortMonths;
    weekdays = that.weekdays;
    shortWeekdays = that.shortWeekdays;
    ampms = that.ampms;
    zoneStrings = that.zoneStrings;
    localizedPatternChars = that.localizedPatternChars;
  }

  @Override
  public DateFormatSymbols clone() {
    final DateFormatSymbols result = new DateFormatSymbols();
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 17;
    code = Hash.combine(code, multiplier, eras);
    code = Hash.combine(code, multiplier, months);
    code = Hash.combine(code, multiplier, shortMonths);
    code = Hash.combine(code, multiplier, weekdays);
    code = Hash.combine(code, multiplier, shortWeekdays);
    code = Hash.combine(code, multiplier, ampms);
    code = Hash.combine(code, multiplier, zoneStrings);
    code = Hash.combine(code, multiplier, localizedPatternChars);
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
    final DateFormatSymbols other = (DateFormatSymbols) obj;
    return Equality.equals(eras, other.eras)
        && Equality.equals(months, other.months)
        && Equality.equals(shortMonths, other.shortMonths)
        && Equality.equals(weekdays, other.weekdays)
        && Equality.equals(shortWeekdays, other.shortWeekdays)
        && Equality.equals(ampms, other.ampms)
        && Equality.equals(zoneStrings, other.zoneStrings)
        && Equality.equals(localizedPatternChars, other.localizedPatternChars);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("eras", eras)
               .append("months", months)
               .append("shortMonths", shortMonths)
               .append("weekdays", weekdays)
               .append("shortWeekdays", shortWeekdays)
               .append("ampms", ampms)
               .append("zoneStrings", zoneStrings)
               .append("localizedPatternChars", localizedPatternChars)
               .toString();
  }
}
