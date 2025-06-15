////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.ByteUtils;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.IntUtils;
import ltd.qubit.commons.lang.ShortUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.text.ErrorCode.INVALID_SYNTAX;
import static ltd.qubit.commons.text.ErrorCode.UNKNOWN_ERROR;
import static ltd.qubit.commons.text.FormatUtils.putFormatResult;
import static ltd.qubit.commons.text.FormatUtils.putIntBackward;
import static ltd.qubit.commons.text.FormatUtils.putLongBackward;
import static ltd.qubit.commons.text.ParseUtils.getSign;

/**
 * {@link NumberFormat} 用于解析和格式化数字。
 *
 * <p>此类提供了对各种数值类型（包括整数和浮点数）的解析和格式化功能。
 * 支持多种进制（二进制、八进制、十进制、十六进制）和自定义格式选项。</p>
 *
 * @author 胡海星
 */
public final class NumberFormat implements Assignable<NumberFormat> {

  /**
   * 最大分组数量。
   */
  public static final int MAX_GROUP_COUNT = 32;

  /**
   * 最大浮点数字位数。
   */
  public static final int MAX_FLOAT_DIGIT_COUNT = 34;

  /**
   * 最大浮点数指数。
   */
  public static final int MAX_FLOAT_EXPONENT = 340;

  /**
   * 默认格式标志。
   */
  public static final int DEFAULT_FLAGS = FormatFlag.DEFAULT;

  /**
   * 二进制基数。
   */
  public static final int BINARY_RADIX = 2;

  /**
   * 八进制基数。
   */
  public static final int OCTAL_RADIX = 8;

  /**
   * 十进制基数。
   */
  public static final int DECIMAL_RADIX = 10;

  /**
   * 十六进制基数。
   */
  public static final int HEX_RADIX = 16;

  /**
   * 默认基数。
   */
  public static final int DEFAULT_RADIX = DECIMAL_RADIX;

  /**
   * 默认最大数字位数。
   */
  public static final int DEFAULT_MAX_DIGITS = Integer.MAX_VALUE;

  /**
   * 最大实数字位数。
   */
  public static final int MAX_REAL_DIGIT_COUNT = 34;

  /**
   * 最大实数指数。
   */
  public static final int MAX_REAL_EXPONENT = 340;

  /**
   * 默认整数精度。
   */
  public static final int DEFAULT_INT_PRECISION = 1;

  /**
   * 默认实数精度。
   */
  public static final int DEFAULT_REAL_PRECISION = 6;

  /**
   * 默认宽度。
   */
  public static final int DEFAULT_WIDTH = 0;

  /**
   * 默认填充字符。
   */
  public static final int DEFAULT_FILL = ' ';

  private NumberFormatSymbols symbols;
  private NumberFormatOptions options;
  private final transient ParsingPosition position;
  private final transient StringBuilder builder;
  private transient char[] buffer;

  /**
   * 构造一个使用默认设置的NumberFormat实例。
   */
  public NumberFormat() {
    symbols = new NumberFormatSymbols();
    options = new NumberFormatOptions();
    position = new ParsingPosition();
    builder = new StringBuilder();
    buffer = ArrayUtils.EMPTY_CHAR_ARRAY;
  }

  /**
   * 构造一个使用指定区域设置的NumberFormat实例。
   *
   * @param locale
   *     区域设置。
   */
  public NumberFormat(final Locale locale) {
    symbols = new NumberFormatSymbols(locale);
    options = new NumberFormatOptions();
    position = new ParsingPosition();
    builder = new StringBuilder();
    buffer = ArrayUtils.EMPTY_CHAR_ARRAY;
  }

  /**
   * 构造一个NumberFormat实例，复制另一个实例的设置。
   *
   * @param other
   *     要复制的NumberFormat实例。
   */
  public NumberFormat(final NumberFormat other) {
    this();
    assign(other);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assign(final NumberFormat other) {
    symbols.assign(symbols);
    options.assign(options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NumberFormat cloneEx() {
    return new NumberFormat(this);
  }

  /**
   * 重置此NumberFormat为默认设置。
   */
  public void reset() {
    symbols.reset();
    options.reset();
    position.reset();
  }

  /**
   * 重置此NumberFormat为指定区域设置的默认设置。
   *
   * @param locale
   *     区域设置。
   */
  public void reset(final Locale locale) {
    symbols.reset(locale);
    options.reset();
    position.reset();
  }

  /**
   * 获取数字格式符号。
   *
   * @return
   *     数字格式符号。
   */
  public NumberFormatSymbols getSymbols() {
    return symbols;
  }

  /**
   * 设置数字格式符号。
   *
   * @param symbols
   *     数字格式符号，不能为null。
   */
  public void setSymbols(final NumberFormatSymbols symbols) {
    this.symbols = requireNonNull("symbols", symbols);
  }

  /**
   * 获取数字格式选项。
   *
   * @return
   *     数字格式选项。
   */
  public NumberFormatOptions getOptions() {
    return options;
  }

  /**
   * 设置数字格式选项。
   *
   * @param options
   *     数字格式选项，不能为null。
   */
  public void setOptions(final NumberFormatOptions options) {
    this.options = requireNonNull("options", options);
  }

  /**
   * 获取解析位置。
   *
   * @return
   *     解析位置。
   */
  public ParsingPosition getParsePosition() {
    return position;
  }

  /**
   * 获取解析索引。
   *
   * @return
   *     解析索引。
   */
  public int getParseIndex() {
    return position.getIndex();
  }

  /**
   * 获取错误索引。
   *
   * @return
   *     错误索引。
   */
  public int getErrorIndex() {
    return position.getErrorIndex();
  }

  /**
   * 获取错误代码。
   *
   * @return
   *     错误代码。
   */
  public int getErrorCode() {
    return position.getErrorCode();
  }

  /**
   * 检查解析是否成功。
   *
   * @return
   *     如果解析成功返回true，否则返回false。
   */
  public boolean success() {
    return position.success();
  }

  /**
   * 检查解析是否失败。
   *
   * @return
   *     如果解析失败返回true，否则返回false。
   */
  public boolean fail() {
    return position.fail();
  }

  /**
   * 解析一个{@code byte}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public byte parseByte(final CharSequence str) {
    return parseByte(str, 0, str.length());
  }

  /**
   * 解析一个{@code byte}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public byte parseByte(final CharSequence str, final int startIndex) {
    return parseByte(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code byte}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public byte parseByte(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex, symbols.getPositiveSign(),
        symbols.getNegativeSign());
    // get the radix
    final int radix = ParseUtils.getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the integer
    if (radix == DECIMAL_RADIX) {
      // parse the value in decimal radix
      return (byte) ParseUtils.getDecimalInt(position, str, endIndex, sign,
          Byte.MAX_VALUE, options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return (byte) ParseUtils.getSpecialRadixInt(position, str, endIndex, sign, radix,
          ByteUtils.UNSIGNED_MAX, options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    }
  }

  /**
   * 解析一个{@code short}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public short parseShort(final CharSequence str) {
    return parseShort(str, 0, str.length());
  }

  /**
   * 解析一个{@code short}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public short parseShort(final CharSequence str, final int startIndex) {
    return parseShort(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code short}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public short parseShort(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = ParseUtils.getRadix(position, str, endIndex,
        options.getFlags(), options.getDefaultRadix());
    // parse the integer
    if (radix == DECIMAL_RADIX) {
      // parse the value in decimal radix
      return (short) ParseUtils.getDecimalInt(position, str, endIndex, sign,
          Short.MAX_VALUE, options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return (short) ParseUtils.getSpecialRadixInt(position, str, endIndex,
          sign, radix, ShortUtils.UNSIGNED_MAX, options.getMaxDigits(),
          options.isGrouping(), symbols.getGroupingSeparator());
    }
  }

  /**
   * 解析一个{@code int}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public int parseInt(final CharSequence str) {
    return parseInt(str, 0, str.length());
  }

  /**
   * 解析一个{@code int}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public int parseInt(final CharSequence str, final int startIndex) {
    return parseInt(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code int}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public int parseInt(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = ParseUtils.getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the integer
    if (radix == DECIMAL_RADIX) {
      // parse the value in decimal radix
      return ParseUtils.getDecimalInt(position, str, endIndex, sign,
          Integer.MAX_VALUE, options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return ParseUtils.getSpecialRadixInt(position, str, endIndex, sign, radix,
          IntUtils.UNSIGNED_MAX, options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    }
  }

  /**
   * 解析一个{@code long}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public long parseLong(final CharSequence str) {
    return parseLong(str, 0, str.length());
  }

  /**
   * 解析一个{@code long}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public long parseLong(final CharSequence str, final int startIndex) {
    return parseLong(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code long}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public long parseLong(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // get the sign
    final int sign = getSign(position, str, endIndex,
        symbols.getPositiveSign(), symbols.getNegativeSign());
    // get the radix
    final int radix = ParseUtils.getRadix(position, str, endIndex, options.getFlags(),
        options.getDefaultRadix());
    // parse the number
    if (radix == DECIMAL_RADIX) {
      // parse the value in decimal radix
      return ParseUtils.getDecimalLong(position, str, endIndex, sign,
          options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    } else {
      // parse the value in a special radix, ignoring the sign symbol.
      return ParseUtils.getSpecialRadixLong(position, str, endIndex, sign, radix,
          options.getMaxDigits(), options.isGrouping(),
          symbols.getGroupingSeparator());
    }
  }

  /**
   * 解析一个{@code float}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public float parseFloat(final CharSequence str) {
    return parseFloat(str, 0, str.length());
  }

  /**
   * 解析一个{@code float}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public float parseFloat(final CharSequence str, final int startIndex) {
    return parseFloat(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code float}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public float parseFloat(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // TODO: implement the parsing of float numbers.
    final String num_str = str.subSequence(position.getIndex(), endIndex).toString();
    try {
      return Float.valueOf(num_str);
    } catch (final NumberFormatException e) {
      position.setErrorCode(UNKNOWN_ERROR);
      position.setErrorIndex(position.getIndex());
      return 0.0f;
    }
  }

  /**
   * 解析一个{@code double}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public double parseDouble(final CharSequence str) {
    return parseDouble(str, 0, str.length());
  }

  /**
   * 解析一个{@code double}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public double parseDouble(final CharSequence str, final int startIndex) {
    return parseDouble(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code double}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public double parseDouble(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return 0;
      }
    }
    // TODO: implement the parsing of double numbers.
    final String num_str = str.subSequence(position.getIndex(), endIndex).toString();
    try {
      return Double.valueOf(num_str);
    } catch (final NumberFormatException e) {
      position.setErrorCode(UNKNOWN_ERROR);
      position.setErrorIndex(position.getIndex());
      return 0.0f;
    }
  }

  /**
   * 解析一个{@code BigInteger}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public BigInteger parseBigInteger(final CharSequence str) {
    return parseBigInteger(str, 0, str.length());
  }

  /**
   * 解析一个{@code BigInteger}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public BigInteger parseBigInteger(final CharSequence str, final int startIndex) {
    return parseBigInteger(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code BigInteger}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public BigInteger parseBigInteger(final CharSequence str,
      final int startIndex, final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return null;
      }
    }
    try {
      // TODO: use a better parsing method
      final String text = str.subSequence(position.getIndex(), endIndex).toString();
      return new BigInteger(text);
    } catch (final NumberFormatException e) {
      position.setErrorCode(INVALID_SYNTAX);
      position.setErrorIndex(position.getIndex());
      return null;
    }
  }

  /**
   * 解析一个{@code BigDecimal}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code 0}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @return
   *     解析的值。
   */
  public BigDecimal parseBigDecimal(final CharSequence str) {
    return parseBigDecimal(str, 0, str.length());
  }

  /**
   * 解析一个{@code BigDecimal}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code str.length()}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @return
   *     解析的值。
   */
  public BigDecimal parseBigDecimal(final CharSequence str, final int startIndex) {
    return parseBigDecimal(str, startIndex, str.length());
  }

  /**
   * 解析一个{@code BigDecimal}值。
   *
   * @param str
   *     要解析的文本段。解析从索引{@code startIndex}处的字符开始，
   *     在索引{@code endIndex}之前的字符处停止。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     结束解析的索引。
   * @return
   *     解析的值。
   */
  public BigDecimal parseBigDecimal(final CharSequence str,
      final int startIndex, final int endIndex) {
    // check the index bounds
    requireIndexInCloseRange(startIndex, 0, str.length());
    requireIndexInCloseRange(endIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return null;
      }
    }
    try {
      // TODO: use a better parsing method
      final String text = str.subSequence(position.getIndex(), endIndex).toString();
      return new BigDecimal(text);
    } catch (final NumberFormatException e) {
      position.setErrorCode(INVALID_SYNTAX);
      position.setErrorIndex(position.getIndex());
      return null;
    }
  }

  /**
   * 格式化一个{@code byte}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatByte(final byte value) {
    builder.setLength(0);
    formatByte(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@code byte}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatByte(final byte value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Byte.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putIntBackward(value,
        (value & ByteUtils.UNSIGNED_MAX), options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * 格式化一个{@code short}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatShort(final short value) {
    builder.setLength(0);
    formatShort(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@code short}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatShort(final short value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Short.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putIntBackward(value,
        (value & ShortUtils.UNSIGNED_MAX), options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * 格式化一个{@code int}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatInt(final int value) {
    builder.setLength(0);
    formatInt(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@code int}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatInt(final int value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Integer.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putIntBackward(value,
        (value & IntUtils.UNSIGNED_MAX), options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * 格式化一个{@code long}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatLong(final long value) {
    builder.setLength(0);
    formatLong(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@code long}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatLong(final long value, final StringBuilder output) {
    // the buffer must be larger enough to hold the sign, radix prefix, group
    // separator, and all digits
    final int bufferSize = Math.max(options.getIntPrecision(), Long.SIZE) + 10;
    if (buffer.length < bufferSize) {
      buffer = new char[bufferSize];
    }
    final int startIndex = putLongBackward(value, options, symbols, buffer,
        buffer.length);
    putFormatResult(options.getFlags(), options.getWidth(), options.getFill(),
        buffer, startIndex, buffer.length, output);
    return output;
  }

  /**
   * 格式化一个{@code float}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatFloat(final float value) {
    builder.setLength(0);
    formatFloat(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@code float}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatFloat(final float value, final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = Float.toString(value);
    output.append(str);
    return output;
  }

  /**
   * 格式化一个{@code double}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatDouble(final double value) {
    builder.setLength(0);
    formatDouble(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@code double}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatDouble(final double value, final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = Double.toString(value);
    output.append(str);
    return output;
  }

  /**
   * 格式化一个{@code BigInteger}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatBigInteger(final BigInteger value) {
    builder.setLength(0);
    formatBigInteger(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@link BigInteger}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatBigInteger(final BigInteger value,
      final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = value.toString();
    output.append(str);
    return output;
  }

  /**
   * 格式化一个{@code BigDecimal}值。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化结果。
   */
  public String formatBigDecimal(final BigDecimal value) {
    builder.setLength(0);
    formatBigDecimal(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个{@link BigDecimal}值。
   *
   * @param value
   *     要格式化的值。
   * @param output
   *     用于追加格式化结果的{@link StringBuilder}。
   * @return
   *     输出的{@link StringBuilder}。
   */
  public StringBuilder formatBigDecimal(final BigDecimal value,
      final StringBuilder output) {
    // FIXME: apply the format options and symbols
    final String str = value.toString();
    output.append(str);
    return output;
  }

  @Override
  public int hashCode() {
    final int multiplier = 17;
    int code = 2;
    code = Hash.combine(code, multiplier, symbols);
    code = Hash.combine(code, multiplier, options);
    code = Hash.combine(code, multiplier, position);
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
    final NumberFormat other = (NumberFormat) obj;
    return symbols.equals(other.symbols)
         && options.equals(other.options)
         && position.equals(other.position);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .appendToString("symbols", symbols.toString())
               .appendToString("options", options.toString())
               .appendToString("position", position.toString())
               .toString();
  }
}