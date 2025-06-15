////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.lang.LongUtils;

import static ltd.qubit.commons.text.ErrorCode.EMPTY_VALUE;
import static ltd.qubit.commons.text.ErrorCode.NUMBER_OVERFLOW;
import static ltd.qubit.commons.text.NumberFormat.BINARY_RADIX;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;
import static ltd.qubit.commons.text.NumberFormat.OCTAL_RADIX;
import static ltd.qubit.commons.text.ParseOptions.DEFAULT_KEEP_BLANKS;

/**
 * 提供解析文本的功能。
 *
 * @author 胡海星
 */
public final class ParseUtils {

  /**
   * 跳过字符串开头的空白和不可打印字符，返回第一个图形字符的位置。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     字符序列。
   * @param endIndex
   *     跳过操作在此位置结束。
   */
  public static void skipBlanks(final ParsingPosition pos,
      final CharSequence str, final int endIndex) {
    int index = pos.getIndex();
    while (index < endIndex) {
      final int codePoint = Utf16.getNext(pos, str, endIndex);
      if (codePoint < 0) { // an error occurs
        pos.setIndex(index);
        return;
      }
      if (CharUtils.isGraph(codePoint)) { // find the first graph character
        pos.setIndex(index);
        return;
      }
      index = pos.getIndex();
    }
  }

  /**
   * 跳过字符串开头的非空白字符，返回第一个空白字符的位置。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     字符序列。
   * @param endIndex
   *     跳过操作在此位置结束。
   */
  public static void skipNonBlanks(final ParsingPosition pos,
      final CharSequence str, final int endIndex) {
    int index = pos.getIndex();
    while (index < endIndex) {
      final int codePoint = Utf16.getNext(pos, str, endIndex);
      if (codePoint < 0) { // an error occurs
        pos.setIndex(index);
        return;
      }
      if (CharUtils.isBlank(codePoint)) { // find the first blank character
        pos.setIndex(index);
        return;
      }
      index = pos.getIndex();
    }
  }

  //  /**
  //   * Skips the leading whitespace, non-printable characters and a specified
  //   * separator of a string, returns the first current of printable character.
  //   *
  //   * @param pos
  //   *          a {@link ParsingPosition} object indicate the current position in
  //   *          the character sequence to be parsed; after calling this function,
  //   *          the index of the position would be changed.
  //   * @param str
  //   *          a character sequence.
  //   * @param endIndex
  //   *          the skipping end at this current.
  //   * @param separator
  //   *          the specified separator to be skipped. Note that the separator is
  //   *          treated as a substring.
  //   */
  //  public static void skipSeparators(final ParsingPosition pos,
  //      final CharSequence str, final int endIndex, final CharSequence separator) {
  //    int index = pos.getIndex();
  //    while (index < endIndex) {
  //      final int codePoint = Utf16.next(pos, str, endIndex);
  //      if (codePoint < 0) { // an error occurs
  //        pos.setIndex(index);
  //        return;
  //      }
  //      if (CharUtils.isGraph(codePoint)) { // find the first graph character
  //        if (CharSequenceUtils.startsWith(str, index, endIndex, separator)) {
  //          index += separator.length();
  //          pos.setIndex(index);
  //        } else {
  //          pos.setIndex(index);
  //          return;
  //        }
  //      } else {
  //        index = pos.getIndex();
  //      }
  //    }
  //  }

  /**
   * 跳过可选的前缀。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，如果前缀成功跳过，此位置将设置为跳过前缀后的位置；
   *     否则，此位置不会改变。
   * @param str
   *     字符序列。
   * @param endIndex
   *     跳过操作在此位置结束。
   * @param prefix
   *     要跳过的可选前缀。
   * @return
   *     如果前缀成功跳过则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean skipPrefix(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final String prefix) {
    final int prefixLen = prefix.length();
    final int startIndex = pos.getIndex();
    if ((endIndex - startIndex) < prefixLen) {
      return false;
    }
    for (int i = 0; i < prefixLen; ++i) {
      if (str.charAt(startIndex + i) != prefix.charAt(i)) {
        return false;
      }
    }
    pos.setIndex(startIndex + prefixLen);
    return true;
  }

  /**
   * 获取要解析的数字的符号。调用此函数后，当前位置可能会向前移动以跳过可能的符号字符。
   *
   * <p><b>重要提示：</b>此函数不检查参数的有效性，因此，调用者必须确保参数有效。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     字符序列。
   * @param endIndex
   *     字符序列的结束位置。必须满足 0 ≤ current.value ≤ endIndex ≤ str.length()；
   *     整个文本段在输入的 [current.value, endIndex) 范围内。
   * @param positiveSign
   *     正号符号的字符。
   * @param negativeSign
   *     负号符号的字符。
   * @return
   *     如果输入字符序列有负号则返回 -1；否则返回 +1。
   */
  public static int getSign(final ParsingPosition pos, final CharSequence str,
      final int endIndex, final char positiveSign, final char negativeSign) {
    int sign = +1;
    final int index = pos.getIndex();
    if (index < endIndex) {
      final char ch = str.charAt(index);
      if (ch == positiveSign) {
        pos.setIndex(index + 1);
        sign = +1;
      } else if (ch == negativeSign) {
        pos.setIndex(index + 1);
        sign = -1;
      }
    }
    return sign;
  }

  /**
   * 获取要解析的数字的进制。调用此函数后，位置的索引可能会向前移动以跳过输入文本段中可能的进制前缀。
   *
   * <p><b>重要提示：</b>此函数不检查参数的有效性，因此，调用者必须确保参数有效。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     字符序列。
   * @param endIndex
   *     字符序列的结束位置。必须满足 0 ≤ current.value ≤ endIndex ≤ str.length()；
   *     整个文本段在输入的 [current.value, endIndex) 范围内。
   * @param flags
   *     要解析的数字的格式标志的组合。
   * @param defaultRadix
   *     如果要解析的数字没有指定进制且没有进制前缀时使用的默认进制。
   * @return
   *     要解析的数字的进制。
   */
  public static int getRadix(final ParsingPosition pos, final CharSequence str,
      final int endIndex, final int flags, final int defaultRadix) {
    int index = pos.getIndex();
    switch (flags & FormatFlag.RADIX_MASK) {
      case FormatFlag.BINARY:
        if (index < endIndex) {
          char ch = str.charAt(index);
          if (ch == '0') {
            ++index;
            if (index < endIndex) {
              ch = str.charAt(index);
              if ((ch == 'b') || (ch == 'B')) {
                // skip the binary prefix "0b" or "0B"
                pos.setIndex(index + 1);
                return BINARY_RADIX;
              }
            }
          }
        }
        return BINARY_RADIX;
      case FormatFlag.OCTAL:
        // since the octal prefix is a single '0', it could also be
        // treated as the leading zero, so don't skip it.
        return OCTAL_RADIX;
      case FormatFlag.HEX:
        if (index < endIndex) {
          char ch = str.charAt(index);
          if (ch == '0') {
            ++index;
            if (index < endIndex) {
              ch = str.charAt(index);
              if ((ch == 'x') || (ch == 'X')) {
                // skip the hex prefix "0x" or "0X"
                pos.setIndex(index + 1);
                return HEX_RADIX;
              }
            }
          }
        }
        return HEX_RADIX;
      case FormatFlag.DECIMAL:
        return DECIMAL_RADIX;
      default:
        // detect the radix prefix
        if (index < endIndex) {
          char ch = str.charAt(index);
          if (ch == '0') {
            ++index;
            if (index < endIndex) {
              ch = str.charAt(index);
              if ((ch == 'b') || (ch == 'B')) {
                // skip the binary prefix "0b" or "0B"
                pos.setIndex(index + 1);
                return BINARY_RADIX;
              } else if ((ch == 'x') || (ch == 'X')) {
                // skip the hex prefix "0x" or "0X"
                pos.setIndex(index + 1);
                return HEX_RADIX;
              }
            }
            // since there is a leading 0, the number is treated
            // as a octal number. Note that do NOT skip the leading
            // prefix '0'.
            return OCTAL_RADIX;
          }
        }
        return defaultRadix;
    }
  }

  /**
   * 解析特殊进制（2、4、8或16）的无符号 int 值。
   *
   * <p>由于 Java 没有无符号整数类型，返回值仍然是有符号 int，但溢出条件针对无符号 int 进行了修改。
   *
   * <p>调用此函数后，{@code pos.index} 被设置为解析停止的位置。
   *
   * <p>如果解析过程中发生错误，{@code pos.errorCode} 被设置为错误代码，
   * {@code pos.errorIndex} 被设置为发生错误的位置；否则，{@code pos.errorCode}
   * 被设置为 {@link ErrorCode#NONE}，{@code pos.errorIndex} 被设置为 {@code -1}。
   *
   * <p>注意，此函数不会跳过前导空白字符，也不会获取进制前缀和符号。
   * 为了做到这些，请在调用此函数之前调用
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)}、
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} 和
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)}。
   *
   * <p><b>重要提示：</b>此函数不检查参数的有效性，因此，调用者必须确保参数有效。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     文本段。不能为 null。
   * @param endIndex
   *     文本段的结束位置。必须满足 0 ≤ this.index ≤ endIndex ≤ input.length()；
   *     整个文本段在输入的 [this.index, endIndex) 范围内。
   * @param sign
   *     要解析的整数的符号。
   * @param radix
   *     要解析的整数值的进制。必须是 2、8 或 16 之一。
   * @param maxValue
   *     要解析的数字的最大允许值。注意此值被视为无符号 int。
   * @param maxDigits
   *     可以解析的最大位数。
   * @return
   *     此函数解析的 int 值。如果 this.errorCode 被设置为 ParseError.EMPTY，
   *     返回 0；如果 this.errorCode 被设置为 ParseError.OVERFLOW，返回 maxValue。
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static int getSpecialRadixInt(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int radix, final int maxValue, final int maxDigits,
      final boolean useGrouping, final char groupChar) {
    if ((radix != BINARY_RADIX)
        && (radix != OCTAL_RADIX)
        && (radix != HEX_RADIX)) {
      throw new IllegalArgumentException("radix is non of 2, 8, 16.");
    }
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }
    // let shift = floor(log2(radix))
    final int shift = 31 - Integer.numberOfLeadingZeros(radix);
    final int limit = (maxValue >>> shift);
    // now perform the parsing
    int digitsCount = 0;
    int value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      if (useGrouping && (ch == groupChar)) {
        continue;
      }
      final int digit = Character.digit(ch, radix);
      if (digit < 0) {
        break;
      }
      ++digitsCount; // remember the number of digits have been read
      if (pos.success()) {
        // note that since in Java int is signed, the following
        // condition "value >= 0" is critical for binary radix.
        if ((value >= 0) && (value <= limit)) {
          value <<= shift;
          value |= digit;
        } else { // overflows
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        }
      }
    }
    pos.setIndex(index);
    if (pos.success()) {
      if (digitsCount == 0) { // no digit has been read
        pos.setErrorCode(EMPTY_VALUE);
        pos.setErrorIndex(index);
      }
      if (sign > 0) {
        return value;
      } else {
        return (-value);
      }
    } else {
      if (sign > 0) {
        return maxValue;
      } else {
        return (-maxValue);
      }
    }
  }

  /**
   * 解析特殊进制（2、4、8或16）的无符号 long 值。
   *
   * <p>由于 Java 没有无符号整数类型，返回值仍然是有符号 long，但溢出条件针对无符号 long 进行了修改。
   *
   * <p>调用此函数后，{@code pos.index} 被设置为解析停止的位置。
   *
   * <p>如果解析过程中发生错误，{@code pos.errorCode} 被设置为错误代码，
   * {@code pos.errorIndex} 被设置为发生错误的位置；否则，{@code pos.errorCode}
   * 被设置为 {@link ErrorCode#NONE}，{@code pos.errorIndex} 被设置为 {@code -1}。
   *
   * <p>注意，此函数不会跳过前导空白字符，也不会获取进制前缀和符号。
   * 为了做到这些，请在调用此函数之前调用
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)}、
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} 和
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)}。
   *
   * <p><b>重要提示：</b>此函数不检查参数的有效性，因此，调用者必须确保参数有效。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     文本段。不能为 null。
   * @param endIndex
   *     文本段的结束位置。必须满足 0 ≤ this.index ≤ endIndex ≤ input.length()；
   *     整个文本段在输入的 [this.index, endIndex) 范围内。
   * @param sign
   *     解析值的预解析符号。
   * @param radix
   *     要解析的整数值的进制。必须是 2、8 或 16 之一。
   * @param maxDigits
   *     可以解析的最大位数。
   * @return
   *     此函数解析的 long 值。如果 this.errorCode 被设置为 ParseError.EMPTY，
   *     返回 0；如果 this.errorCode 被设置为 ParseError.OVERFLOW，返回 UNSIGNED_LONG_MAX。
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static long getSpecialRadixLong(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int radix, final int maxDigits, final boolean useGrouping,
      final char groupChar) {
    if ((radix != BINARY_RADIX)
        && (radix != OCTAL_RADIX)
        && (radix != HEX_RADIX)) {
      throw new IllegalArgumentException("radix is non of 2, 8, 16.");
    }
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }

    // let shift = floor(log2(radix))
    final int shift = 31 - Integer.numberOfLeadingZeros(radix);
    final long limit = (LongUtils.UNSIGNED_MAX >>> shift);
    // now perform the parsing
    int digitsCount = 0;
    long value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      if (useGrouping && ch == groupChar) {
        continue;
      }
      final int digit = Character.digit(ch, radix);
      if (digit < 0) {
        break;
      }
      ++digitsCount; // remember the number of digits have been read
      if (pos.success()) {
        // note that since in Java int is signed, the following
        // condition "value >= 0" is critical for binary radix.
        if ((value >= 0) && (value <= limit)) {
          value <<= shift;
          value |= digit;
        } else { // overflows
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        }
      }
    }
    pos.setIndex(index);
    if (pos.success()) {
      if (digitsCount == 0) { // no digit has been read
        pos.setErrorCode(EMPTY_VALUE);
        pos.setErrorIndex(index);
      }
      if (sign > 0) {
        return value;
      } else {
        return (-value);
      }
    } else {
      if (sign > 0) {
        return LongUtils.UNSIGNED_MAX;
      } else {
        return (-LongUtils.UNSIGNED_MAX);
      }
    }
  }

  /**
   * 解析十进制的有符号 int 值。
   *
   * <p>调用此函数后，{@code pos.index} 被设置为解析停止的位置。
   *
   * <p>如果解析过程中发生错误，{@code pos.errorCode} 被设置为错误代码，
   * {@code pos.errorIndex} 被设置为发生错误的位置；否则，{@code pos.errorCode}
   * 被设置为 {@link ErrorCode#NONE}，{@code pos.errorIndex} 被设置为 {@code -1}。
   *
   * <p>注意，此函数不会跳过前导空白字符，也不会获取进制前缀和符号。
   * 为了做到这些，请在调用此函数之前调用
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)}、
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} 和
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)}。
   *
   * <p><b>重要提示：</b>此函数不检查参数的有效性，因此，调用者必须确保参数有效。
   *
   * <p>TODO: 添加对数字分组的支持。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     文本段。不能为 null。
   * @param endIndex
   *     文本段的结束位置。必须满足 0 ≤ this.index ≤ endIndex ≤ input.length()；
   *     整个文本段在输入的 [this.index, endIndex) 范围内。
   * @param sign
   *     要解析的整数值的符号。负值表示要解析的值是负值，否则要解析的值是正值。
   * @param maxValue
   *     要解析的数字的最大允许值。注意此值被视为有符号 int，
   *     要解析的数字的最小允许值是 (- maxValue - 1)。
   * @param maxDigits
   *     可以解析的最大位数。
   * @return
   *     此函数解析的整数值。如果 this.errorCode 是 ParseError.EMPTY，
   *     返回值是 0；如果 this.errorCode 是 ParseError.OVERFLOW，
   *     返回值是 maxValue（如果 sign ≥ 0），或 (- maxValue - 1)（如果 sign < 0）。
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static int getDecimalInt(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int maxValue, final int maxDigits, final boolean useGrouping,
      final char groupChar) {
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }
    // note that we use a trick to deal with the overflow of integers.
    // since the maximum absolute value of a negative int can not be represented
    // as a positive int, we treat all int as a negative int, and fix it at last
    // if
    // if is really a positive int.
    //
    final int minValue = (-maxValue - 1);
    final int limit = minValue / 10;
    int digitsCount = 0;
    int value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      if (useGrouping && ch == groupChar) {
        continue;
      }
      final int digit = Character.digit(ch, 10);
      if (digit < 0) {
        break;
      }
      // remember the number of digits has been read
      ++digitsCount;
      // note that if the value overflow, the remained digits should still be
      // read, but we don't need to accumulate the absolute value for the
      // overflow
      // value.
      if (pos.success()) {
        if (value < limit) {
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        } else {
          value *= DECIMAL_RADIX; // never overflow, since value >= limit
          value -= digit; // may cause overflow
          // check for overflow cased by the above,
          // note that the following checking will miss one special case:
          // the sign >= 0 and the value is minValue.
          // so it should be fixed in the following code.
          if ((value > 0) || (value < minValue)) {
            pos.setErrorCode(NUMBER_OVERFLOW);
            pos.setErrorIndex(index);
          }
        }
      }
    }
    pos.setIndex(index);
    if (digitsCount == 0) { // no digits are read
      pos.setErrorCode(EMPTY_VALUE);
      pos.setErrorIndex(index);
      return 0;
    } else if (pos.getErrorCode() == NUMBER_OVERFLOW) {
      return (sign >= 0 ? maxValue : minValue);
    } else if (sign >= 0) {
      if (value == minValue) {
        // it's also an case of overflow
        pos.setErrorCode(NUMBER_OVERFLOW);
        pos.setErrorIndex(index - 1);
        return maxValue;
      } else {
        return (-value); // return the fixed value
      }
    } else {
      return value;
    }
  }

  /**
   * 解析十进制的有符号 long 值。
   *
   * <p>调用此函数后，{@code pos.index} 被设置为解析停止的位置。
   *
   * <p>如果解析过程中发生错误，{@code pos.errorCode} 被设置为错误代码，
   * {@code pos.errorIndex} 被设置为发生错误的位置；否则，{@code pos.errorCode}
   * 被设置为 {@link ErrorCode#NONE}，{@code pos.errorIndex} 被设置为 {@code -1}。
   *
   * <p>注意，此函数不会跳过前导空白字符，也不会获取进制前缀和符号。
   * 为了做到这些，请在调用此函数之前调用
   * {@link #skipNonBlanks(ParsingPosition, CharSequence, int)}、
   * {@link #getRadix(ParsingPosition, CharSequence, int, int, int)} 和
   * {@link #getSign(ParsingPosition, CharSequence, int, char, char)}。
   *
   * <p><b>重要提示：</b>此函数不检查参数的有效性，因此，调用者必须确保参数有效。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变。
   * @param str
   *     文本段。不能为 null。
   * @param endIndex
   *     文本段的结束位置。必须满足 0 ≤ this.index ≤ endIndex ≤ input.length()；
   *     整个文本段在输入的 [this.index, endIndex) 范围内。
   * @param sign
   *     要解析的整数值的符号。负值表示要解析的值是负值，否则要解析的值是正值。
   * @param maxDigits
   *     可以解析的最大位数。
   * @return
   *     此函数解析的整数值。如果 this.errorCode 是 ParseError.EMPTY，
   *     返回值是 0；如果 this.errorCode 是 ParseError.OVERFLOW，
   *     返回值是 Long.MAX_VALUE（如果 sign ≥ 0），或 Long.MIN_VALUE（如果 sign < 0）。
   * @see #skipNonBlanks(ParsingPosition, CharSequence, int)
   * @see #getRadix(ParsingPosition, CharSequence, int, int, int)
   * @see #getSign(ParsingPosition, CharSequence, int, char, char)
   */
  public static long getDecimalLong(final ParsingPosition pos,
      final CharSequence str, final int endIndex, final int sign,
      final int maxDigits, final boolean useGrouping, final char groupChar) {
    if (sign == 0) {
      throw new IllegalArgumentException("sign can't be zero.");
    }
    if (maxDigits <= 0) {
      throw new IllegalArgumentException("Maximum digits must be positive.");
    }

    final long limit = Long.MIN_VALUE / 10;
    int digitsCount = 0;
    long value = 0;
    pos.clearError();
    int index = pos.getIndex();
    for (; index < endIndex; ++index) {
      if (digitsCount >= maxDigits) {
        break;
      }
      final char ch = str.charAt(index);
      if (useGrouping && ch == groupChar) {
        continue;
      }
      final int digit = Character.digit(ch, 10);
      if (digit < 0) {
        break;
      }
      // remember the number of digits has been read
      ++digitsCount;
      // note that if the value overflow, the remained digits should still be
      // read, but we don't need to accumulate the absolute value for the
      // overflow
      // value.
      if (pos.success()) {
        if (value < limit) {
          pos.setErrorCode(NUMBER_OVERFLOW);
          pos.setErrorIndex(index);
        } else {
          value *= DECIMAL_RADIX; // never overflow, since value >= limit
          value -= digit; // may cause overflow
          // check for overflow cased by the above,
          // note that the following checking will miss one special case:
          // the sign >= 0 and the value is minValue.
          // so it should be fixed in the following code.
          if ((value > 0) || (value < Long.MIN_VALUE)) {
            pos.setErrorCode(NUMBER_OVERFLOW);
            pos.setErrorIndex(index);
          }
        }
      }
    }
    pos.setIndex(index);
    if (digitsCount == 0) { // no digits are read
      pos.setErrorCode(EMPTY_VALUE);
      pos.setErrorIndex(index);
      return 0;
    } else if (pos.getErrorCode() == NUMBER_OVERFLOW) {
      return (sign >= 0 ? Long.MAX_VALUE : Long.MIN_VALUE);
    } else if (sign >= 0) {
      if (value == Long.MIN_VALUE) {
        // it's also an case of overflow
        pos.setErrorCode(NUMBER_OVERFLOW);
        pos.setErrorIndex(index - 1);
        return Long.MAX_VALUE;
      } else {
        return (-value); // return the fixed value
      }
    } else {
      return value;
    }
  }

  /**
   * 解析 char 值。
   *
   * @param pos
   *     {@link ParsingPosition} 对象，指示字符序列中的当前解析位置；
   *     调用此函数后，位置的索引将被改变，如果解析过程中发生任何错误，
   *     此对象的错误代码也将被设置。
   * @param str
   *     要解析的文本段。
   * @param endIndex
   *     文本段的结束位置。
   * @param options
   *     解析选项。
   * @return
   *     解析的值。
   */
  public static char parseChar(final ParsingPosition pos,
      final CharSequence str,
      final int endIndex, final ParseOptions options) {
    // skip the leading white space if necessary
    if (!options.isKeepBlank()) {
      skipBlanks(pos, str, endIndex);
      if (pos.fail()) {
        return (char) 0;
      }
    }
    final int index = pos.getIndex();
    if ((index >= endIndex) || (index >= str.length())) {
      pos.setErrorCode(EMPTY_VALUE);
      pos.setErrorIndex(index);
      return (char) 0;
    } else {
      return str.charAt(index);
    }
  }

  /**
   * 解析 char 值。
   *
   * @param str
   *     要解析的文本段。
   * @param startIndex
   *     文本段的开始位置。
   * @param endIndex
   *     文本段的结束位置。
   * @param options
   *     解析选项。
   * @return
   *     解析的值。
   * @throws TextParseException
   *     如果解析过程中发生任何错误。
   * @throws IndexOutOfBoundsException
   *     如果 startIndex < 0 或 startIndex > endIndex 或 endIndex > str.length()。
   */
  public static char parseChar(final CharSequence str, final int startIndex,
      final int endIndex, final ParseOptions options)
      throws TextParseException {
    final ParsingPosition pos = new ParsingPosition(startIndex);
    final char result = parseChar(pos, str, endIndex, options);
    if (pos.fail()) {
      throw new TextParseException(str, startIndex, endIndex, pos);
    }
    return result;
  }

  /**
   * 解析 char 值。
   *
   * @param str
   *     要解析的文本段。
   * @param startIndex
   *     文本段的开始位置。
   * @param endIndex
   *     文本段的结束位置。
   * @return
   *     解析的值。
   * @throws TextParseException
   *     如果解析过程中发生任何错误。
   * @throws IndexOutOfBoundsException
   *     如果 startIndex < 0 或 startIndex > endIndex 或 endIndex > str.length()。
   */
  public static char parseChar(final CharSequence str, final int startIndex,
      final int endIndex) throws TextParseException {
    final ParsingPosition pos = new ParsingPosition(startIndex);
    pos.reset(startIndex);
    final char result = parseChar(pos, str, endIndex, DEFAULT_KEEP_BLANKS);
    if (pos.fail()) {
      throw new TextParseException(str, startIndex, endIndex, pos);
    }
    return result;
  }

  /**
   * 解析 char 值。
   *
   * @param str
   *     要解析的文本段。
   * @return
   *     解析的值。
   * @throws TextParseException
   *     如果解析过程中发生任何错误。
   */
  public static char parseChar(final CharSequence str)
      throws TextParseException {
    final ParsingPosition pos = new ParsingPosition();
    final char result = parseChar(pos, str, str.length(), DEFAULT_KEEP_BLANKS);
    if (pos.fail()) {
      throw new TextParseException(str, pos);
    }
    return result;
  }

  //  public static Date parseDate(final ParsingPosition pos, final CharSequence str,
  //      final int endIndex, final ParseOptions options,
  //      final NumberFormatSymbols symbols, final String formatPattern) {
  //    // skip the leading white space if necessary
  //    if (! options.isKeepBlank()) {
  //      skipBlanks(pos, str, endIndex);
  //      if (pos.fail()) {
  //        return null;
  //      }
  //    }
  //    // / TODO: use a better parsing method
  //    final SimpleDateFormat df = new SimpleDateFormat(formatPattern);
  //    final Date result = df.parse(str.toString(), pos);
  //    if (result == null) {
  //      pos.setErrorCode(ErrorCode.INVALID_SYNTAX);
  //      return null;
  //    } else if (pos.getIndex() &gt; endIndex) {
  //      pos.setErrorCode(ErrorCode.INVALID_SYNTAX);
  //      return null;
  //    } else {
  //      return result;
  //    }
  //  }
  //
  //  public static Date parseDate(final CharSequence str, final int startIndex,
  //      final int endIndex, final ParseOptions options) throws TextParseException {
  //    final ParsePositionPool pool = ParsePositionPool.getInstance();
  //    final ParsingPosition pos = pool.borrow();
  //    try {
  //      pos.reset(startIndex);
  //      final Date result = parseDate(pos, str, endIndex, options,
  //          NumberFormatSymbols.DEFAULT, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
  //      if (pos.fail()) {
  //        throw new TextParseException(str, startIndex, endIndex, pos);
  //      } else {
  //        assert (result != null);
  //        return result;
  //      }
  //    } finally {
  //      pool.restore(pos);
  //    }
  //  }
  //
  //  public static Date parseDate(final CharSequence str, final int startIndex,
  //      final int endIndex) throws TextParseException {
  //    final ParsePositionPool pool = ParsePositionPool.getInstance();
  //    final ParsingPosition pos = pool.borrow();
  //    try {
  //      pos.reset(startIndex);
  //      final Date result = parseDate(pos, str, endIndex, ParseOptions.DEFAULT,
  //          NumberFormatSymbols.DEFAULT, DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
  //      if (pos.fail()) {
  //        throw new TextParseException(str, startIndex, endIndex, pos);
  //      } else {
  //        assert (result != null);
  //        return result;
  //      }
  //    } finally {
  //      pool.restore(pos);
  //    }
  //  }
  //
  //  public static Date parseDate(final CharSequence str)
  //      throws TextParseException {
  //    final ParsePositionPool pool = ParsePositionPool.getInstance();
  //    final ParsingPosition pos = pool.borrow();
  //    try {
  //      final Date result = parseDate(pos, str, str.length(),
  //          ParseOptions.DEFAULT, FormatSymbols.DEFAULT,
  //          DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
  //      if (pos.fail()) {
  //        throw new TextParseException(str, pos);
  //      } else {
  //        assert (result != null);
  //        return result;
  //      }
  //    } finally {
  //      pool.restore(pos);
  //    }
  //  }
}