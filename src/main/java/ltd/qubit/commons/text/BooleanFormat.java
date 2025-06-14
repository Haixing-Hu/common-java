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
 * The {@link NumberFormat} is used to parse and format boolean values.
 *
 * @author Haixing Hu
 */
public final class BooleanFormat implements CloneableEx<BooleanFormat> {

  private BooleanFormatSymbols symbols;
  private BooleanFormatOptions options;
  private final transient ParsingPosition position;
  private final transient StringBuilder builder;

  public BooleanFormat() {
    symbols = new BooleanFormatSymbols();
    options = new BooleanFormatOptions();
    position = new ParsingPosition();
    builder = new StringBuilder();
  }

  public void reset() {
    symbols.reset();
    options.reset();
    position.reset();
  }

  public BooleanFormatSymbols getSymbols() {
    return symbols;
  }

  public void setSymbols(final BooleanFormatSymbols symbols) {
    this.symbols = Argument.requireNonNull("symbols", symbols);
  }

  public BooleanFormatOptions getOptions() {
    return options;
  }

  public void setOptions(final BooleanFormatOptions options) {
    this.options = Argument.requireNonNull("options", options);
  }

  public ParsingPosition getParsePosition() {
    return position;
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

  public boolean success() {
    return position.success();
  }

  public boolean fail() {
    return position.fail();
  }

  /**
   * Parses a {@code boolean} value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index {@code 0} and stops at the character
   *          before the index {@code str.length()}.
   * @return the parsed value.
   */
  public boolean parse(final CharSequence str) {
    return parse(str, 0, str.length());
  }

  /**
   * Parses a {@code boolean} value.
   *
   * @param str
   *          the text segment to be parsed. The parsing starts from the
   *          character at the index {@code startIndex} and stops at the
   *          character before the index {@code str.length()}.
   * @param startIndex
   *          the index where to start parsing.
   * @return the parsed value.
   */
  public boolean parse(final CharSequence str, final int startIndex) {
    return parse(str, startIndex, str.length());
  }

  /**
   * Parses a {@code boolean} value.
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
   * Formats a {@code boolean} value.
   *
   * @param value
   *          the value to be formated.
   * @return the formatted result.
   */
  public String format(final boolean value) {
    builder.setLength(0);
    format(value, builder);
    return builder.toString();
  }

  /**
   * Formats a {@code boolean} value.
   *
   * @param value
   *          the value to be formated.
   * @param output
   *          the {@link StringBuilder} where to append the formatted result.
   * @return the output {@link StringBuilder}.
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