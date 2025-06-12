////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.text.ParseException;

import javax.annotation.concurrent.ThreadSafe;

import static ltd.qubit.commons.text.NumberFormatOptions.DEFAULT_MAX_DIGITS;

/**
 * 用于编码和解码C风格字符串字面量的类。
 *
 * <p>根据ISO C++ Standard 2003的定义，C风格字符串字面量具有以下语法：
 *
 * <pre class="code">
 *  string-literal:
 *              ""
 *              L""
 *              "s-char-sequence"
 *              L"s-char-sequence"
 *
 *  s-char-sequence:
 *              s-char
 *              s-char-sequence s-char
 *
 *  s-char:
 *              any member of the source character set except
 *              the double-quote ", backslash \, or new-line character
 *              escape-sequence
 *              universal-character-name
 *
 *  escape-sequence:
 *              simple-escape-sequence
 *              octal-escape-sequence
 *              hexadecimal-escape-sequence
 *
 *  simple-escape-sequence: one of
 *              \` \" \? \\
 *              \a \b \f \n \r \t \v
 *
 *  octal-escape-sequence:
 *              \ octal-digit
 *              \ octal-digit octal-digit
 *              \ octal-digit octal-digit octal-digit
 *
 *  hexadecimal-escape-sequence:
 *              \x hexadecimal-digit
 *              hexadecimal-escape-sequence hexadecimal-digit
 *
 *  universal-character-name:
 *              \\u hex-quad
 *              \U hex-quad hex-quad
 *
 *  hex-quad:
 *              hexadecimal-digit hexadecimal-digit hexadecimal-digit hexadecimal-digit
 *
 * </pre>
 *
 * <p>注意"\\u"应该是'u'前的单个'\'，但Java将其视为Unicode前缀，所以我们使用"\\u"。
 *
 * <p>由通用字符名称\UNNNNNNNN指定的字符是ISO/IEC 10646中字符简短名称为NNNNNNNN的字符；
 * 由通用字符名称\\uNNNN指定的字符是ISO/IEC 10646中字符简短名称为0000NNNN的字符。
 * 如果通用字符名称的十六进制值小于0x20或在0x7F-0x9F范围内（包含），
 * 或者如果通用字符名称指定了基本源字符集中的字符，则程序是非法的。（标准2.2.2）
 *
 * <p>基本源字符集包含96个字符：空格字符、表示水平制表符、垂直制表符、
 * 换页符和换行符的控制字符，以及以下91个图形字符：（标准2.2.1）
 *
 * <pre class="code">
 *      a b c d e f g h i j k l m n o p q r s t u v w x y z
 *      A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 *      0 1 2 3 4 5 6 7 8 9
 *      _ { } [ ] # ( ) &lt; &gt; % : ; . ? * + - / ^ &amp; | ~ ! = , \ " '
 * </pre>
 *
 * <p>注意'$'、'@'和'`'不是C++标准要求的源字符集，但实际上它们被广泛使用。
 * 所以这个函数也会将它们视为有效字符。
 *
 * @author 胡海星
 */
@ThreadSafe
public class CStringLiteral {

  private static final byte ACTION_ERROR   = -1;
  private static final byte ACTION_UTF16   = -2;
  private static final byte ACTION_UTF32   = -3;
  private static final byte ACTION_HEX     = -4;
  private static final byte ACTION_OCTAL   = -5;

  // stop checkstyle: WhitespaceAfter

  //  the ESCAPE_ACTION[ch] stores the action need to be done if a char ch is right after the
  //  escape character \. If the ESCAPE_ACTION[ch] is none of the predefined actions, the
  //  ch after \ consists of a simple escape sequence, and it denotes the character with the
  //  code point of ESCAPE_ACTION[ch].
  private static final byte[] ESCAPE_ACTION = {
      /*       0    1    2    3    4    5    6    7   8    9    A    B    C    D    E    F  */
      /* 0 */ -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
      /* 1 */ -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
      /* 2 */ ' ',  -1, '"',  -1,  -1,  -1,  -1,'\'', -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
      /* 3 */ -5,  -5,  -5,  -5,  -5,  -5,  -5,  -5, -1,  -1,  -1,  -1,  -1,  -1,  -1,  '?',
      /* 4 */ -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
      /* 5 */ -1,  -1,  -1,  -1,  -1,  -3,  -1,  -1, -1,  -1,  -1,  -1,'\\',  -1,  -1,  -1,
      /* 6 */ -1, 0x7,'\b',  -1,  -1,  -1,'\f',  -1, -1,  -1,  -1,  -1,  -1,  -1,'\n',  -1,
      /* 7 */ -1,  -1,'\r',  -1,'\t',  -2, 0xB,  -1, -4,  -1,  -1,  -1,  -1,  -1,  -1,  -1,
  };

  //  all the characters in the basic source character set plus $, @ and ` are set to 1,
  //  totally 96 + 3 = 99 characters are set.
  private static final byte[] SOURCE_CHARACTER_SET = {
      /*      0    1    2    3    4    5    6    7   8    9    A    B    C    D    E    F  */
      /* 0 */ 0,   0,   0,   0,   0,   0,   0,   0,  0,   1,   1,   1,   1,   0,   0,   0,
      /* 1 */ 0,   0,   0,   0,   0,   0,   0,   0,  0,   0,   0,   0,   0,   0,   0,   0,
      /* 2 */ 1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
      /* 3 */ 1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
      /* 4 */ 1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
      /* 5 */ 1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
      /* 6 */ 1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   1,
      /* 7 */ 1,   1,   1,   1,   1,   1,   1,   1,  1,   1,   1,   1,   1,   1,   1,   0,
  };
  // resume checkstyle: WhitespaceAfter

  private static final String ERROR_INVALID_CHARACTER    = "invalid character.";
  private static final String ERROR_INCOMPLETE           = "incomplete escape sequence.";
  private static final String ERROR_INVALID_HEX          = "invalid hexadecimal-escape-sequence.";
  private static final String ERROR_INVALID_ESCAPED_CHAR = "invalid escaped character.";
  private static final int MAX_ASCII_CHAR_VALUE          = 0x7F;
  private static final int UTF16_DIGITS                  = 4;
  private static final int UTF32_DIGITS                  = 8;
  private static final int MAX_OCTAL_DIGITS              = 3;

  /**
   * 给定一个C风格字符串字面量（不带引号），将其解码为字节数组。
   *
   * <p>此函数读取一个C风格字符串字面量，并将其解码为字节数组。
   * 字符串字面量是不带引号的形式（即没有前导和尾随双引号），
   * 并且不包含任何代码点大于0xFF的字符。
   *
   * @param str
   *     包含C风格字符串字面量的字符序列。不能为null，且必须具有非零长度。
   *     注意此字符串不应包含任何大于0xFF的字符。
   * @return 解码结果的字节数组。
   * @throws TextParseException
   *     如果给定的C风格字符串字面量格式错误。
   */
  public static byte[] decode(final CharSequence str)
      throws TextParseException {
    return decode(str, 0, str.length());
  }

  /**
   * 给定一个C风格字符串字面量（不带引号），将其解码为字节数组。
   *
   * <p>此函数读取一个C风格字符串字面量，并将其解码为字节数组。
   * 字符串字面量是不带引号的形式（即没有前导和尾随双引号），
   * 并且不包含任何代码点大于0xFF的字符。
   *
   * @param str
   *     包含C风格字符串字面量的字符序列。不能为null，且必须具有非零长度。
   *     注意此字符串不应包含任何大于0xFF的字符。
   * @param startIndex
   *     开始解析的索引。解析将在str的末尾停止。
   * @return 解码结果的字节数组。
   * @throws TextParseException
   *     如果给定的C风格字符串字面量格式错误。
   */
  public static byte[] decode(final CharSequence str, final int startIndex)
      throws TextParseException {
    return decode(str, startIndex, str.length());
  }

  /**
   * 给定一个C风格字符串字面量（不带引号），将其解码为字节数组。
   *
   * <p>此函数读取一个C风格字符串字面量，并将其解码为字节数组。
   * 字符串字面量是不带引号的形式（即没有前导和尾随双引号），
   * 并且不包含任何代码点大于0xFF的字符。
   *
   * @param str
   *     包含C风格字符串字面量的字符序列。不能为null，且必须具有非零长度。
   *     注意此字符串不应包含任何大于0xFF的字符。
   * @param startIndex
   *     开始解析的索引。
   * @param endIndex
   *     停止解析的索引，即解析范围[startIndex, endIndex)内的文本。
   * @return 解码结果的字节数组。
   * @throws TextParseException
   *     如果给定的C风格字符串字面量格式错误。
   */
  public static byte[] decode(final CharSequence str, final int startIndex,
      final int endIndex) throws TextParseException {
    byte[] result = new byte[str.length()];
    final NumberFormat nf = new NumberFormat();
    final NumberFormatOptions options = nf.getOptions();
    int len = 0;
    int index = startIndex;
    while (index < endIndex) {
      char ch = str.charAt(index);
      if (ch != '\\') {
        // ch is not escape character '\'
        if ((ch > MAX_ASCII_CHAR_VALUE) || (SOURCE_CHARACTER_SET[ch] == 0)) {
          // ch is not a source character, which is an error
          throw new TextParseException(str, startIndex, endIndex,
              ERROR_INVALID_CHARACTER, index);
        }
        // directly convert ch into its byte value
        result[len++] = (byte) ch;
        ++index;
      } else {
        // ch is escape character '\'
        ++index;
        if (index == endIndex) {
          throw new TextParseException(str, startIndex, endIndex,
              ERROR_INCOMPLETE, index);
        }
        ch = str.charAt(index);
        if (ch > MAX_ASCII_CHAR_VALUE) { // error
          throw new TextParseException(str, startIndex, endIndex,
              ERROR_INVALID_CHARACTER, index);
        }
        final byte action = ESCAPE_ACTION[ch];
        switch (action) {
          case ACTION_ERROR:
            throw new TextParseException(str, startIndex, endIndex,
                ERROR_INVALID_ESCAPED_CHAR, index);
          case ACTION_UTF16:
            // start parsing a UTF-16 code point
            // note that by Standard 2.2.2, it must have exactly 4 hex
            // digits.
            ++index; // skip the 'u'
            options.setKeepBlank(true); // don't skip blanks
            options.setHex(true);
            options.setMaxDigits(DEFAULT_MAX_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex, nf.getParsePosition());
            }
            if ((index + UTF16_DIGITS) != nf.getParseIndex()) {
              // the UTF16 must have exactly UTF16_DIGITS hex digits.
              throw new TextParseException(str, startIndex, endIndex, ERROR_INVALID_HEX, index);
            }
            index = nf.getParseIndex();
            break;
          case ACTION_UTF32:
            // start parsing a UTF-32 code point
            // note that by Standard 2.2.2, it must have exactly 8 hex digits.
            ++index; // skip the 'U'
            options.setKeepBlank(true); // don't skip blanks
            options.setHex(true);
            options.setMaxDigits(DEFAULT_MAX_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex,
                  nf.getParsePosition());
            }
            if ((index + UTF32_DIGITS) != nf.getParseIndex()) {
              // the UTF32 must has exactly UTF32_DIGITS hex digits.
              throw new TextParseException(str, startIndex, endIndex,
                  ERROR_INVALID_HEX, index);
            }
            index = nf.getParseIndex();
            break;
          case ACTION_HEX:
            // start parsing a HEX code point.
            // note that by Standard 2.13.2.4, there may be 1 or more hex digits
            ++index; // skip the 'x'
            options.setKeepBlank(true); // don't skip blanks
            options.setHex(true);
            options.setMaxDigits(DEFAULT_MAX_DIGITS);
            // Bug fix: 2024-02-01, there should be at most 2 hex digits
            result[len++] = nf.parseByte(str, index, Math.min(index + 2, endIndex));
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex,
                  nf.getParsePosition());
            }
            index = nf.getParseIndex();
            break;
          case ACTION_OCTAL:
            // start parsing a OCATL code point.
            // note that by Standard 2.13.2.4,
            // there may be 1, 2, or 3 octal digits
            options.setKeepBlank(true); // don't skip blanks
            options.setOctal(true);
            options.setMaxDigits(MAX_OCTAL_DIGITS);
            result[len++] = nf.parseByte(str, index, endIndex);
            if (nf.fail()) {
              throw new TextParseException(str, startIndex, endIndex,
                  nf.getParsePosition());
            }
            index = nf.getParseIndex();
            break;
          default:
            // \ followed by ch is a simple escape sequence, and
            // the character it denotes is the value of action
            assert (len < result.length);
            result[len++] = action;
            ++index;
            break;
        } // end of switch
      } // end of else
    } // end of while
    if (len < result.length) {
      // Shrink the array if necessary
      final byte[] newResult = new byte[len];
      System.arraycopy(result, 0, newResult, 0, len);
      result = newResult;
    }
    return result;
  }

  /**
   * 给定ASCII字符串的原始数据字节数组，将其编码为C风格字符串字面量（不带引号）。
   *
   * <p>此函数读取ASCII字符串的原始数据字节数组，并将其编码为C风格字符串字面量。
   * 字符串字面量是不带引号的形式（即没有前导和尾随双引号），
   * 并且不包含任何代码点大于0xFF的字符。
   *
   * @param rawData
   *     ASCII字符串的原始数据字节数组，不能为{@code null}。
   * @return 从字节数组编码的C风格字符串字面量。
   */
  public static String encode(final byte[] rawData) {
    final StringBuilder builder = new StringBuilder();
    encode(rawData, builder);
    return builder.toString();
  }

  /**
   * 给定ASCII字符串的原始数据字节数组，将其编码为C风格字符串字面量（不带引号）。
   *
   * <p>此函数读取ASCII字符串的原始数据字节数组，并将其编码为C风格字符串字面量。
   * 字符串字面量是不带引号的形式（即没有前导和尾随双引号），
   * 并且不包含任何代码点大于0xFF的字符。
   *
   * @param rawData
   *     ASCII字符串的原始数据字节数组，不能为{@code null}。
   * @param builder
   *     用于追加从字节数组编码的C风格字符串字面量。
   */
  public static void encode(final byte[] rawData, final StringBuilder builder) {
    final NumberFormat nf = new NumberFormat();
    final NumberFormatOptions options = nf.getOptions();
    options.setShowRadix(false);
    options.setHex(true);
    options.setUppercase(true);
    options.setIntPrecision(2);
    // stop checkstyle: MagicNumberCheck
    for (int i = 0; i < rawData.length; ++i) {
      final char ch = (char) (rawData[i] & 0xFF);
      switch (ch) {
        case '"': // "\""
          builder.append('\\').append('"');
          break;
        case '\'': // "\'"
          builder.append('\\').append('\'');
          break;
        case '?':
          builder.append('\\').append('?');
          break;
        case '\\': // "\\"
          builder.append('\\').append('\\');
          break;
        case 0x07: // "\a"
          builder.append('\\').append('a');
          break;
        case '\b': // "\b"
          builder.append('\\').append('b');
          break;
        case '\f': // "\f"
          builder.append('\\').append('f');
          break;
        case '\n': // "\n"
          builder.append('\\').append('n');
          break;
        case '\r': // "\r"
          builder.append('\\').append('r');
          break;
        case '\t': // "\t"
          builder.append('\\').append('t');
          break;
        case 0x0B: // "\v"
          builder.append('\\').append('v');
          break;
        default:
          if ((ch <= MAX_ASCII_CHAR_VALUE) && (SOURCE_CHARACTER_SET[ch] != 0)) {
            builder.append(ch);
          } else {
            builder.append('\\').append('x');
            nf.formatByte(rawData[i], builder);
          }
          break;
      }
    }
    // resume checkstyle: MagicNumberCheck
  }
}