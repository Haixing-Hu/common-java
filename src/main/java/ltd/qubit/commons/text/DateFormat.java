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
 * {@link DateFormat}用于解析和格式化{@link Date}值。
 * <p>
 * 这个类类似于{@link SimpleDateFormat}类，但它提供了更方便的解析和格式化函数。
 * </p>
 * <p>
 * 这个类的实现复制自{@link SimpleDateFormat}类的源代码。
 * </p>
 *
 * @author 胡海星
 */
public final class DateFormat {

  /**
   * 默认日期格式模式。
   */
  public static final String DEFAULT_PATTERN = DateUtils.DEFAULT_DATETIME_PATTERN;

  /**
   * 日期的默认区域设置。
   */
  public static final Locale DEFAULT_LOCALE = Locale.US;

  public static final boolean DEFAULT_SKIP_BLANKS = true;

  private final String pattern;
  private final SimpleDateFormat format;
  private final ParsingPosition position;
  private boolean skipBlanks;

  /**
   * 使用默认模式和区域设置构造DateFormat。
   */
  public DateFormat() {
    this(DEFAULT_PATTERN, DEFAULT_LOCALE);
  }

  /**
   * 使用指定区域设置构造DateFormat。
   *
   * @param locale 区域设置
   */
  public DateFormat(final Locale locale) {
    this(DEFAULT_PATTERN, locale);
  }

  /**
   * 使用指定模式构造DateFormat。
   *
   * @param pattern 日期格式模式
   */
  public DateFormat(final String pattern) {
    this(pattern, DEFAULT_LOCALE);
  }

  /**
   * 使用指定模式和时区构造DateFormat。
   *
   * @param pattern 日期格式模式
   * @param timeZone 时区
   */
  public DateFormat(final String pattern, final TimeZone timeZone) {
    this.pattern = requireNonNull("pattern", pattern);
    format = new SimpleDateFormat(pattern);
    format.setTimeZone(requireNonNull("timeZone", timeZone));
    position = new ParsingPosition();
    skipBlanks = DEFAULT_SKIP_BLANKS;
  }

  /**
   * 使用指定模式和区域设置构造DateFormat。
   *
   * @param pattern 日期格式模式
   * @param locale 区域设置
   */
  public DateFormat(final String pattern, final Locale locale) {
    this.pattern = requireNonNull("pattern", pattern);
    format = new SimpleDateFormat(pattern, requireNonNull("locale", locale));
    position = new ParsingPosition();
    skipBlanks = DEFAULT_SKIP_BLANKS;
  }

  /**
   * 使用指定模式、区域设置和时区构造DateFormat。
   *
   * @param pattern 日期格式模式
   * @param locale 区域设置
   * @param timeZone 时区
   */
  public DateFormat(final String pattern, final Locale locale, final TimeZone timeZone) {
    this.pattern = requireNonNull("pattern", pattern);
    format = new SimpleDateFormat(pattern, requireNonNull("locale", locale));
    format.setTimeZone(requireNonNull("timeZone", timeZone));
    position = new ParsingPosition();
    skipBlanks = DEFAULT_SKIP_BLANKS;
  }

  /**
   * 获取日期格式模式。
   *
   * @return 日期格式模式
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * 设置日期格式模式。
   *
   * @param pattern 新的日期格式模式
   */
  public void setPattern(final String pattern) {
    requireNonNull("pattern", pattern);
    if (! this.pattern.equals(pattern)) {
      format.applyPattern(pattern);
    }
  }

  /**
   * 获取时区。
   *
   * @return 时区
   */
  public TimeZone getTimeZone() {
    return format.getTimeZone();
  }

  /**
   * 设置时区。
   *
   * @param timeZone 新的时区
   */
  public void setTimeZone(final TimeZone timeZone) {
    format.setTimeZone(requireNonNull("timeZone", timeZone));
  }

  /**
   * 获取是否跳过空白字符。
   *
   * @return 如果跳过空白字符则返回true，否则返回false
   */
  public boolean isSkipBlanks() {
    return skipBlanks;
  }

  /**
   * 设置是否跳过空白字符。
   *
   * @param skipBlanks 是否跳过空白字符
   */
  public void setSkipBlanks(final boolean skipBlanks) {
    this.skipBlanks = skipBlanks;
  }

  /**
   * 返回解释2位数年份所在的100年期间的开始日期。
   *
   * @return 解析2位数年份的100年期间的开始时间
   * @see #set2DigitYearStart
   */
  public Date get2DigitYearStart() {
    return format.get2DigitYearStart();
  }

  /**
   * 设置2位数年份将被解释为所在的100年期间的开始日期。
   *
   * @param startDate
   *          在解析期间，2位数年份将被放置在{@code startDate}到{@code startDate + 100年}的范围内。
   * @see #get2DigitYearStart
   */
  public void set2DigitYearStart(final Date startDate) {
    format.set2DigitYearStart(startDate);
  }

  /**
   * 获取解析位置对象。
   *
   * @return 解析位置对象
   */
  public ParsingPosition getParsePosition() {
    return position;
  }

  /**
   * 检查解析是否成功。
   *
   * @return 如果解析成功则返回true，否则返回false
   */
  public boolean success() {
    return position.success();
  }

  /**
   * 检查解析是否失败。
   *
   * @return 如果解析失败则返回true，否则返回false
   */
  public boolean fail() {
    return position.fail();
  }

  /**
   * 获取解析索引。
   *
   * @return 解析索引
   */
  public int getParseIndex() {
    return position.getIndex();
  }

  /**
   * 获取错误索引。
   *
   * @return 错误索引
   */
  public int getErrorIndex() {
    return position.getErrorIndex();
  }

  /**
   * 获取错误代码。
   *
   * @return 错误代码
   */
  public int getErrorCode() {
    return position.getErrorCode();
  }

  /**
   * 解析{@link Date}值。
   *
   * @param str
   *          要解析的文本段。解析从索引{@code 0}处的字符开始，
   *          到索引{@code str.length()}之前的字符停止。
   * @return 解析的值。
   */
  public Date parse(final String str) {
    return parse(str, 0, str.length());
  }

  /**
   * 解析{@link Date}值。
   *
   * @param str
   *          要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *          到索引{@code str.length()}之前的字符停止。
   * @param startIndex
   *          开始解析的索引。
   * @return 解析的值。
   */
  public Date parse(final String str, final int startIndex) {
    return parse(str, startIndex, str.length());
  }

  /**
   * 解析{@link Date}值。
   *
   * @param str
   *          要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *          到索引{@code endIndex}之前的字符停止。
   * @param startIndex
   *          开始解析的索引。
   * @param endIndex
   *          结束解析的索引。
   * @return 解析的值。
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
   * 格式化{@link Date}值。
   *
   * @param value
   *          要格式化的值。
   * @return 格式化结果。
   */
  public String format(final Date value) {
    requireNonNull("value", value);
    return format.format(value);
  }

  /**
   * 格式化{@link Date}值。
   *
   * @param value
   *          要格式化的值。
   * @param output
   *          用于追加格式化结果的{@link StringBuilder}。
   * @return 输出的{@link StringBuilder}。
   */
  public StringBuilder format(final Date value, final StringBuilder output) {
    requireNonNull("value", value);
    final String str = format.format(value);
    return output.append(str);
  }
}