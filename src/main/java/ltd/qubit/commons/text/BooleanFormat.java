////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.CharSequenceUtils;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link BooleanFormat} 用于解析和格式化布尔值。
 *
 * @author 胡海星
 */
public final class BooleanFormat implements CloneableEx<BooleanFormat> {

  /**
   * 格式化符号。
   */
  private BooleanFormatSymbols symbols;

  /**
   * 格式化选项。
   */
  private BooleanFormatOptions options;

  /**
   * 解析位置信息。
   */
  private final transient ParsingPosition position;

  /**
   * 用于构建字符串的缓冲区。
   */
  private final transient StringBuilder builder;

  /**
   * 构造一个新的 {@link BooleanFormat} 对象。
   */
  public BooleanFormat() {
    symbols = new BooleanFormatSymbols();
    options = new BooleanFormatOptions();
    position = new ParsingPosition();
    builder = new StringBuilder();
  }

  /**
   * 重置此对象为默认值。
   */
  public void reset() {
    symbols.reset();
    options.reset();
    position.reset();
  }

  /**
   * 获取格式化符号。
   *
   * @return 格式化符号。
   */
  public BooleanFormatSymbols getSymbols() {
    return symbols;
  }

  /**
   * 设置格式化符号。
   *
   * @param symbols
   *          格式化符号，不能为 {@code null}。
   */
  public void setSymbols(final BooleanFormatSymbols symbols) {
    this.symbols = Argument.requireNonNull("symbols", symbols);
  }

  /**
   * 获取格式化选项。
   *
   * @return 格式化选项。
   */
  public BooleanFormatOptions getOptions() {
    return options;
  }

  /**
   * 设置格式化选项。
   *
   * @param options
   *          格式化选项，不能为 {@code null}。
   */
  public void setOptions(final BooleanFormatOptions options) {
    this.options = Argument.requireNonNull("options", options);
  }

  /**
   * 获取解析位置信息。
   *
   * @return 解析位置信息。
   */
  public ParsingPosition getParsePosition() {
    return position;
  }

  /**
   * 获取解析索引。
   *
   * @return 解析索引。
   */
  public int getParseIndex() {
    return position.getIndex();
  }

  /**
   * 获取错误索引。
   *
   * @return 错误索引。
   */
  public int getErrorIndex() {
    return position.getErrorIndex();
  }

  /**
   * 获取错误代码。
   *
   * @return 错误代码。
   */
  public int getErrorCode() {
    return position.getErrorCode();
  }

  /**
   * 检查解析是否成功。
   *
   * @return 如果解析成功则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean success() {
    return position.success();
  }

  /**
   * 检查解析是否失败。
   *
   * @return 如果解析失败则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean fail() {
    return position.fail();
  }

  /**
   * 解析一个 {@code boolean} 值。
   *
   * @param str
   *          要解析的文本段。解析从索引 {@code 0} 处的字符开始，
   *          到索引 {@code str.length()} 之前的字符结束。
   * @return 解析的值。
   */
  public boolean parse(final CharSequence str) {
    return parse(str, 0, str.length());
  }

  /**
   * 解析一个 {@code boolean} 值。
   *
   * @param str
   *          要解析的文本段。解析从索引 {@code startIndex} 处的字符开始，
   *          到索引 {@code str.length()} 之前的字符结束。
   * @param startIndex
   *          开始解析的索引。
   * @return 解析的值。
   */
  public boolean parse(final CharSequence str, final int startIndex) {
    return parse(str, startIndex, str.length());
  }

  /**
   * 解析一个 {@code boolean} 值。
   *
   * @param str
   *          要解析的文本段。解析从索引 {@code startIndex} 处的字符开始，
   *          到索引 {@code endIndex} 之前的字符结束。
   * @param startIndex
   *          开始解析的索引。
   * @param endIndex
   *          结束解析的索引。
   * @return 解析的值。
   */
  public boolean parse(final CharSequence str, final int startIndex,
      final int endIndex) {
    // check the index bounds
    Argument.requireIndexInCloseRange(startIndex, 0, str.length());
    Argument.requireIndexInCloseRange(startIndex, 0, str.length());
    // reset the parse position
    position.reset(startIndex);
    // skip the leading white space if necessary
    if (! options.isKeepBlank()) {
      ParseUtils.skipBlanks(position, str, endIndex);
      if (position.fail()) {
        return false;
      }
    }
    final int index = position.getIndex();
    if (index >= endIndex) {
      position.setErrorCode(ErrorCode.EMPTY_VALUE);
      position.setErrorIndex(index);
      return false;
    }
    final char[] binaryDigits = symbols.getBinaryDigits();
    final String trueName = symbols.getTrueName();
    final String falseName = symbols.getFalseName();
    final char ch = str.charAt(index);
    if (ch == binaryDigits[0]) {
      position.setIndex(index + 1);
      return false;
    } else if (ch == binaryDigits[1]) {
      position.setIndex(index + 1);
      return true;
    } else if (CharSequenceUtils.startsWithIgnoreCase(str, index, endIndex,
        trueName)) {
      position.setIndex(index + trueName.length());
      return true;
    } else if (CharSequenceUtils.startsWithIgnoreCase(str, index, endIndex,
        falseName)) {
      position.setIndex(index + falseName.length());
      return false;
    } else {
      position.setErrorCode(ErrorCode.EMPTY_VALUE);
      position.setErrorIndex(index);
      return false;
    }
  }

  /**
   * 格式化一个 {@code boolean} 值。
   *
   * @param value
   *          要格式化的值。
   * @return 格式化的结果。
   */
  public String format(final boolean value) {
    builder.setLength(0);
    format(value, builder);
    return builder.toString();
  }

  /**
   * 格式化一个 {@code boolean} 值。
   *
   * @param value
   *          要格式化的值。
   * @param output
   *          用于附加格式化结果的 {@link StringBuilder}。
   * @return 输出的 {@link StringBuilder}。
   */
  public StringBuilder format(final boolean value, final StringBuilder output) {
    if (options.isBoolAlpha()) {
      final String str = (value ? symbols.getTrueName() : symbols.getFalseName());
      output.append(str);
    } else {
      final char[] digits = symbols.getBinaryDigits();
      final char ch = (value ? digits[1] : digits[0]);
      output.append(ch);
    }
    return output;
  }

  @Override
  public BooleanFormat cloneEx() {
    final BooleanFormat result = new BooleanFormat();
    result.symbols.assign(this.symbols);
    result.options.assign(this.options);
    return result;
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
    final BooleanFormat other = (BooleanFormat) obj;
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