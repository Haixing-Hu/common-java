////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireLengthBe;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于格式化和解析日期的符号。
 *
 * @author 胡海星
 */
public final class DateFormatSymbols implements CloneableEx<DateFormatSymbols> {

  /**
   * 未本地化的日期时间模式字符。例如：'y'、'd'等。
   * 所有区域设置都使用相同的未本地化模式字符。
   */
  public static final String  PATTERN_CHARS = "GyMdkHmsSEDFwWahKzZ";

  /**
   * 缓存JDK的DateFormatSymbols实例。
   */
  @GuardedBy("DateFormatSymbols.class")
  private static final Map<Locale, java.text.DateFormatSymbols> symbolsCache = new HashMap<>();

  /**
   * 获取JDK的DateFormatSymbols实例。
   *
   * @param locale 区域设置
   * @return JDK的DateFormatSymbols实例
   */
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
   * 时代字符串。例如："AD"和"BC"。包含2个字符串的数组，
   * 按{@code Calendar.BC}和{@code Calendar.AD}索引。
   * @serial
   */
  private String[] eras = null;

  /**
   * 月份字符串。例如："January"、"February"等。包含13个字符串的数组
   * （某些日历有13个月），按{@code Calendar.JANUARY}、{@code Calendar.FEBRUARY}等索引。
   * @serial
   */
  private String[] months = null;

  /**
   * 短月份字符串。例如："Jan"、"Feb"等。包含13个字符串的数组
   * （某些日历有13个月），按{@code Calendar.JANUARY}、{@code Calendar.FEBRUARY}等索引。

   * @serial
   */
  private String[] shortMonths = null;

  /**
   * 工作日字符串。例如："Sunday"、"Monday"等。包含8个字符串的数组，
   * 按{@code Calendar.SUNDAY}、{@code Calendar.MONDAY}等索引。
   * 元素{@code weekdays[0]}被忽略。
   * @serial
   */
  private String[] weekdays = null;

  /**
   * 短工作日字符串。例如："Sun"、"Mon"等。包含8个字符串的数组，
   * 按{@code Calendar.SUNDAY}、{@code Calendar.MONDAY}等索引。
   * 元素{@code shortWeekdays[0]}被忽略。
   * @serial
   */
  private String[] shortWeekdays = null;

  /**
   * AM和PM字符串。例如："AM"和"PM"。包含2个字符串的数组，
   * 按{@code Calendar.AM}和{@code Calendar.PM}索引。
   * @serial
   */
  private String[] ampms = null;

  /**
   * 此区域设置中时区的本地化名称。这是一个大小为<em>n</em> × <em>m</em>的二维字符串数组，
   * 其中<em>m</em>至少为5。<em>n</em>行中的每一行都是包含单个{@code TimeZone}的本地化名称的条目。
   * 每个这样的行包含（<em>i</em>的范围为0..<em>n</em>-1）：
   * <ul>
   * <li>{@code zoneStrings[i][0]} - 时区ID</li>
   * <li>{@code zoneStrings[i][1]} - 标准时间中区域的长名称</li>
   * <li>{@code zoneStrings[i][2]} - 标准时间中区域的短名称</li>
   * <li>{@code zoneStrings[i][3]} - 夏令时中区域的长名称</li>
   * <li>{@code zoneStrings[i][4]} - 夏令时中区域的短名称</li>
   * </ul>
   * 时区ID<em>不</em>本地化；它是{@link java.util.TimeZone TimeZone}类的有效ID之一，
   * 不是<a href="../java/util/TimeZone.html#CustomID">自定义ID</a>。
   * 所有其他条目都是本地化名称。
   * @see java.util.TimeZone
   * @serial
   */
  private String[][] zoneStrings = null;

  /**
   * 本地化的日期时间模式字符。例如，区域设置可能希望使用'u'而不是'y'来表示其日期格式模式字符串中的年份。
   * 此字符串必须恰好为18个字符长，字符的索引由{@code DateFormat.ERA_FIELD}、
   * {@code DateFormat.YEAR_FIELD}等描述。因此，如果字符串是"Xz..."，
   * 则本地化模式将使用'X'表示时代，'z'表示年份。
   * @serial
   */
  private String  localizedPatternChars = null;

  /**
   * 时代字符串长度。
   */
  private static final int ERAS_LENGTH = 2;

  /**
   * 月份字符串长度。
   */
  private static final int MONTHS_LENGTH = 13;

  /**
   * 工作日字符串长度。
   */
  private static final int WEEKDAYS_LENGTH = 8;

  /**
   * AM/PM字符串长度。
   */
  private static final int AMPS_LENGTH = 8;

  /**
   * 使用默认区域设置构造DateFormatSymbols。
   */
  public DateFormatSymbols() {
    reset();
  }

  /**
   * 使用指定区域设置构造DateFormatSymbols。
   *
   * @param locale 区域设置
   */
  public DateFormatSymbols(final Locale locale) {
    reset(locale);
  }

  /**
   * 重置为默认区域设置。
   */
  public void reset() {
    reset(DateFormat.DEFAULT_LOCALE);
  }

  /**
   * 重置为指定区域设置。
   *
   * @param locale 要使用的区域设置
   */
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

  /**
   * 获取时代字符串。
   *
   * @return 时代字符串数组
   */
  public String[] getEras() {
    return eras;
  }

  /**
   * 设置时代字符串。
   *
   * @param eras 时代字符串数组，长度必须为2
   */
  public void setEras(final String[] eras) {
    this.eras = requireLengthBe("eras", eras, ERAS_LENGTH);
  }

  /**
   * 获取月份字符串。
   *
   * @return 月份字符串数组
   */
  public String[] getMonths() {
    return months;
  }

  /**
   * 设置月份字符串。
   *
   * @param months 月份字符串数组，长度必须为13
   */
  public void setMonths(final String[] months) {
    this.months = requireLengthBe("months", months, MONTHS_LENGTH);
  }

  /**
   * 获取短月份字符串。
   *
   * @return 短月份字符串数组
   */
  public String[] getShortMonths() {
    return shortMonths;
  }

  /**
   * 设置短月份字符串。
   *
   * @param shortMonths 短月份字符串数组，长度必须为13
   */
  public void setShortMonths(final String[] shortMonths) {
    this.shortMonths = requireLengthBe("shortMonths", shortMonths, MONTHS_LENGTH);
  }

  /**
   * 获取工作日字符串。
   *
   * @return 工作日字符串数组
   */
  public String[] getWeekdays() {
    return weekdays;
  }

  /**
   * 设置工作日字符串。
   *
   * @param weekdays 工作日字符串数组，长度必须为8
   */
  public void setWeekdays(final String[] weekdays) {
    this.weekdays = requireLengthBe("weekdays", weekdays, WEEKDAYS_LENGTH);
  }

  /**
   * 获取短工作日字符串。
   *
   * @return 短工作日字符串数组
   */
  public String[] getShortWeekdays() {
    return shortWeekdays;
  }

  /**
   * 设置短工作日字符串。
   *
   * @param shortWeekdays 短工作日字符串数组，长度必须为8
   */
  public void setShortWeekdays(final String[] shortWeekdays) {
    this.shortWeekdays = requireLengthBe("shortWeekdays", shortWeekdays, WEEKDAYS_LENGTH);
  }

  /**
   * 获取AM/PM字符串。
   *
   * @return AM/PM字符串数组
   */
  public String[] getAmpms() {
    return ampms;
  }

  /**
   * 设置AM/PM字符串。
   *
   * @param ampms AM/PM字符串数组，长度必须为2
   */
  public void setAmpms(final String[] ampms) {
    this.ampms = requireLengthBe("ampms", ampms, 2);
  }

  /**
   * 获取时区字符串。
   *
   * @return 时区字符串二维数组
   */
  public String[][] getZoneStrings() {
    return zoneStrings;
  }

  /**
   * 设置时区字符串。
   *
   * @param zoneStrings 时区字符串二维数组，不能为null
   */
  public void setZoneStrings(final String[][] zoneStrings) {
    this.zoneStrings = requireNonNull("zoneStrings", zoneStrings);
  }

  /**
   * 获取本地化模式字符。
   *
   * @return 本地化模式字符串
   */
  public String getLocalizedPatternChars() {
    return localizedPatternChars;
  }

  /**
   * 设置本地化模式字符。
   *
   * @param localizedPatternChars 本地化模式字符串，不能为null
   */
  public void setLocalizedPatternChars(final String localizedPatternChars) {
    this.localizedPatternChars = requireNonNull("localizedPatternChars", localizedPatternChars);
  }

  /**
   * 从另一个DateFormatSymbols对象复制所有数据。
   *
   * @param that 要复制的DateFormatSymbols对象
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public DateFormatSymbols cloneEx() {
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