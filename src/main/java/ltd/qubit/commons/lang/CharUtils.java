////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.ImmutableSet;

import ltd.qubit.commons.error.UnsupportedByteOrderException;
import ltd.qubit.commons.text.Ascii;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.text.NumberFormatSymbols.DEFAULT_UPPERCASE_DIGITS;

/**
 * 该类提供对 {@code char} 基本类型和 {@link Character} 对象的操作。
 * <p>
 * 该类尽量优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法在其文档中详细说明了其行为。
 * </p>
 * <p>
 * 该类还处理从 {@code char} 值或 {@link Character} 对象到常见类型的转换。
 * </p>
 *
 * @author 胡海星
 */
@ThreadSafe
public final class CharUtils {

  /**
   * The default {@code char} value used when necessary.
   */
  public static final char DEFAULT = (char) 0;

  /**
   * The array of lowercase digits and alphabets.
   */
  public static final char[] LOWERCASE_DIGITS = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
      'u', 'v', 'w', 'x', 'y', 'z'
  };

  /**
   * The array of uppercase digits and alphabets.
   */
  public static final char[] UPPERCASE_DIGITS = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
      'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
      'U', 'V', 'W', 'X', 'Y', 'Z'
  };

  /**
   * The array strings of single ASCII character.
   */
  private static final String[] CHAR_STRING_CACHE = new String[Ascii.MAX + 1];

  static {
    for (char i = 0; i <= Ascii.MAX; ++i) {
      CHAR_STRING_CACHE[i] = String.valueOf(i);
    }
  }

  /**
   * Indicates that the Unicode is a printable character except for spaces.
   */
  public static final int VISIBILITY_GRAPH = 0x01;

  /**
   * Indicates that the Unicode is a "inline blank" character, including spaces,
   * control characters (except for '\r' and '\n'), etc.
   */
  public static final int VISIBILITY_INLINE_BLANK = 0x02;

  /**
   * Indicates that the Unicode is a line break character, including '\r', '\n'
   * and all characters with general category of "Zl" (line separators) and "Zp"
   * (paragraph separators).
   */
  public static final int VISIBILITY_LINE_BREAK = 0x04;

  /**
   * Indicates that the Unicode is a "blank" character, including "inline blank"
   * characters and "line break" characters.
   *
   * <p>This value is the bitwise OR of {@link #VISIBILITY_INLINE_BLANK} and
   * {@link #VISIBILITY_LINE_BREAK}.
   */
  public static final int VISIBILITY_BLANK = VISIBILITY_INLINE_BLANK | VISIBILITY_LINE_BREAK;

  private CharUtils() {
  }

  /**
   * 测试字符是否为 ASCII 数字。
   *
   * @param ch
   *     要测试的字符。
   * @return
   *     如果字符是 ASCII 数字（即在 '0' 和 '9' 之间）则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isAsciiDigit(final char ch) {
    return (ch >= '0' && ch <= '9');
  }

  /**
   * 测试字符是否为 ASCII 十六进制数字。
   *
   * @param ch
   *     要测试的字符。
   * @return
   *     如果字符是十六进制 ASCII 数字（即在 '0' 和 '9' 之间，或在 'a' 和 'f' 之间，或在 'A' 和 'F' 之间）
   *     则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isAsciiHexDigit(final char ch) {
    return (ch >= '0' && ch <= '9')
        || (ch >= 'a' && ch <= 'f')
        || (ch >= 'A' && ch <= 'F');
  }

  /**
   * 测试字符是否为八进制数字。
   *
   * @param ch
   *     要测试的字符。
   * @return
   *     如果字符是八进制 ASCII 数字（即在 '0' 和 '7' 之间）则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isAsciiOctalDigit(final char ch) {
    return (ch >= '0' && ch <= '7');
  }

  /**
   * 判断指定的码点是否为"图形"字符（可打印，不包括空格）。
   *
   * <p>除了一般类别为 "Cc"（控制码）、"Cf"（格式控制）、"Cs"（代理）、"Cn"（未分配）
   * 和 "Z"（分隔符）的字符外，所有字符都返回 true。
   *
   * <p>注意：码点要么是图形字符，要么是空白字符。
   *
   * @param codePoint
   *     Unicode 的码点。
   * @return 如果指定的码点是"图形"字符则返回 true；否则返回 false。
   * @see #isBlank(int)
   */
  public static boolean isGraph(final int codePoint) {
    final int type = Character.getType(codePoint);
    return (type != Character.CONTROL)
        && (type != Character.FORMAT)
        && (type != Character.SURROGATE)
        && (type != Character.UNASSIGNED)
        && (type != Character.LINE_SEPARATOR)
        && (type != Character.SPACE_SEPARATOR)
        && (type != Character.PARAGRAPH_SEPARATOR);
  }

  /**
   * 判断指定的码点是否为"空白"字符（不可打印或空白字符）。
   *
   * <p>对于一般类别为 "Cc"（控制码）、"Cf"（格式控制）、"Cs"（代理）、
   * "Cn"（未分配）和 "Z"（分隔符）的所有字符都返回 true。
   *
   * <p>注意：码点要么是图形字符，要么是空白字符。
   *
   * @param codePoint
   *     Unicode 的码点。
   * @return 如果指定的码点是"空白"字符则返回 true；否则返回 false。
   * @see #isGraph(int)
   */
  public static boolean isBlank(final int codePoint) {
    final int type = Character.getType(codePoint);
    return (type == Character.CONTROL)
        || (type == Character.FORMAT)
        || (type == Character.SURROGATE)
        || (type == Character.UNASSIGNED)
        || (type == Character.LINE_SEPARATOR)
        || (type == Character.SPACE_SEPARATOR)
        || (type == Character.PARAGRAPH_SEPARATOR);
  }

  /**
   * Determines whether the specified code point is a "inline blank" character.
   *
   * <p>A code point is a "inline blank" character if and only if it is not '\r'
   * nor '\n', and it has the general category of "Cc" (control codes), "Cf"
   * (format controls), "Cs" (surrogates), "Cn" (unassigned), and "Zs" (space
   * separators).
   *
   * @param codePoint
   *     the code point.
   * @return true if the specified code point is a "inline blank" character;
   *     false otherwise.
   * @see #isBlank(int)
   */
  public static boolean isInlineBlank(final int codePoint) {
    final int dir = Character.getDirectionality(codePoint);
    if (dir == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR) {
      return false;
    } else {
      final int type = Character.getType(codePoint);
      return (type == Character.CONTROL) || (type == Character.FORMAT)
          || (type == Character.SURROGATE) || (type == Character.UNASSIGNED)
          || (type == Character.SPACE_SEPARATOR);
    }
  }

  /**
   * Determines whether the specified code point is a "line break" character.
   *
   * <p>According to the Unicode Standard Annex #9, a character with
   * bidirectional class B is a "Paragraph Separator". And because a Paragraph
   * Separator breaks lines, there will be at most one per line, at the end of
   * that line.” As a consequence, there's 3 reasons to identify a character as
   * a linebreak:
   *
   * <ul>
   * <li>General Category Zl "Line Separator"</li>
   * <li>General Category Zp "Paragraph Separator"</li>
   * <li>Bidirectional Class B "Paragraph Separator"</li>
   * </ul>
   *
   * <p>There's 8 linebreaks in the current Unicode Database (5.2):
   *
   * <table style="border-collapse:collapse;">
   *    <caption>The Unicode Linebreaks</caption>
   *    <thead>
   *      <tr>
   *        <th style="border:1px solid;padding:0.5rem;">Code Point</th>
   *        <th style="border:1px solid;padding:0.5rem;">Short Name</th>
   *        <th style="border:1px solid;padding:0.5rem;">Long Name</th>
   *        <th style="border:1px solid;padding:0.5rem;">General Category</th>
   *        <th style="border:1px solid;padding:0.5rem;">Bidi Class</th>
   *        <th style="border:1px solid;padding:0.5rem;">Notes</th>
   *      </tr>
   *    </thead>
   * <tbody>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+000A</td>
   * <td style="border:1px solid;padding:0.5rem;">LF</td>
   * <td style="border:1px solid;padding:0.5rem;">LINE FEED</td>
   * <td style="border:1px solid;padding:0.5rem;">Cc</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;"></td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+000D</td>
   * <td style="border:1px solid;padding:0.5rem;">CR</td>
   * <td style="border:1px solid;padding:0.5rem;">CARRIAGE RETURN</td>
   * <td style="border:1px solid;padding:0.5rem;">Cc</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;"></td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+001C</td>
   * <td style="border:1px solid;padding:0.5rem;">FS</td>
   * <td style="border:1px solid;padding:0.5rem;">INFORMATION SEPARATOR FOUR</td>
   * <td style="border:1px solid;padding:0.5rem;">Cc</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;">UCD 3.1: FILE SEPARATOR</td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+001D</td>
   * <td style="border:1px solid;padding:0.5rem;">GS</td>
   * <td style="border:1px solid;padding:0.5rem;">INFORMATION SEPARATOR THREE</td>
   * <td style="border:1px solid;padding:0.5rem;">Cc</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;">UCD 3.1: GROUP SEPARATOR</td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+001E</td>
   * <td style="border:1px solid;padding:0.5rem;">RS</td>
   * <td style="border:1px solid;padding:0.5rem;">INFORMATION SEPARATOR TWO</td>
   * <td style="border:1px solid;padding:0.5rem;">Cc</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;">UCD 3.1: RECORD SEPARATOR</td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+0085</td>
   * <td style="border:1px solid;padding:0.5rem;">NEL</td>
   * <td style="border:1px solid;padding:0.5rem;">NEXT LINE</td>
   * <td style="border:1px solid;padding:0.5rem;">Cc</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;">C1 Control Code</td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+2028</td>
   * <td style="border:1px solid;padding:0.5rem;">LS</td>
   * <td style="border:1px solid;padding:0.5rem;">LINE SEPARATOR</td>
   * <td style="border:1px solid;padding:0.5rem;">Zl</td>
   * <td style="border:1px solid;padding:0.5rem;">WS</td>
   * <td style="border:1px solid;padding:0.5rem;"></td>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">U+2029</td>
   * <td style="border:1px solid;padding:0.5rem;">PS</td>
   * <td style="border:1px solid;padding:0.5rem;">PARAGRAPH SEPARATOR</td>
   * <td style="border:1px solid;padding:0.5rem;">Zp</td>
   * <td style="border:1px solid;padding:0.5rem;">B</td>
   * <td style="border:1px solid;padding:0.5rem;"></td>
   * </tr>
   * </tbody>
   * </table>
   *
   * @param codePoint
   *     the code point.
   * @return true if the specified code point is a "line break" character; false
   *     otherwise.
   */
  public static boolean isLineBreak(final int codePoint) {
    final int type = Character.getType(codePoint);
    if ((type == Character.LINE_SEPARATOR)
        || (type == Character.PARAGRAPH_SEPARATOR)) {
      return true;
    }
    final int dir = Character.getDirectionality(codePoint);
    return (dir == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR);
  }

  /**
   * Get the visibility of a code point.
   *
   * <p>The visibility of a code point is one of the following values:
   * <ul>
   * <li>{@link #VISIBILITY_GRAPH}</li>
   * <li>{@link #VISIBILITY_BLANK}</li>
   * <li>{@link #VISIBILITY_LINE_BREAK}</li>
   * <li>{@link #VISIBILITY_INLINE_BLANK}</li>
   * </ul>
   *
   * @param codePoint
   *     a code point.
   * @return the visibility of the code point.
   */
  public static int getVisibility(final int codePoint) {
    final int dir = Character.getDirectionality(codePoint);
    if (dir == Character.DIRECTIONALITY_PARAGRAPH_SEPARATOR) {
      return VISIBILITY_LINE_BREAK;
    } else {
      final int type = Character.getType(codePoint);
      switch (type) {
        case Character.CONTROL:
        case Character.FORMAT:
        case Character.SURROGATE:
        case Character.UNASSIGNED:
        case Character.SPACE_SEPARATOR:
          return VISIBILITY_INLINE_BLANK;
        case Character.LINE_SEPARATOR:
        case Character.PARAGRAPH_SEPARATOR:
          return VISIBILITY_LINE_BREAK;
        default:
          return VISIBILITY_GRAPH;
      }
    }
  }

  /**
   * Tests whether a string is a quoted character.
   *
   * @param str
   *     a string to be test.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return {@code true} if the string is a quoted character, {@code false}
   *     otherwise.
   */
  public static boolean isQuoted(@Nullable final String str,
      final char escapeChar, final char leftQuote, final char rightQuote) {
    if (str == null) {
      return false;
    }
    final int n = str.length();
    return (n >= 3)
            && (n <= 4)
            && (str.charAt(0) == leftQuote)
            && (str.charAt(n - 1) == rightQuote)
            && ((n != 4) || (str.charAt(1) == escapeChar))
            && ((n != 3) || (str.charAt(1) != escapeChar));
  }

  /**
   * Quotes a character.
   *
   * @param ch
   *     the character to be quoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return the string of quoted character.
   */
  public static String quote(final char ch, final char escapeChar,
      final char leftQuote, final char rightQuote) {
    final StringBuilder builder = new StringBuilder();
    quote(ch, escapeChar, leftQuote, rightQuote, builder);
    return builder.toString();
  }

  /**
   * Quotes a character.
   *
   * @param ch
   *     the character to be quoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @param builder
   *     the string builder where to append the quoted character.
   */
  public static void quote(final char ch, final char escapeChar,
      final char leftQuote, final char rightQuote, final StringBuilder builder) {
    builder.append(leftQuote);
    if ((ch == escapeChar) || (ch == leftQuote) || (ch == rightQuote)) {
      builder.append(escapeChar);
    }
    builder.append(ch).append(rightQuote);
  }

  /**
   * Unquotes a character.
   *
   * @param str
   *     the string of a quoted character.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return the unquoted character.
   * @throws IllegalArgumentException
   *     if the string {@code str} is not a valid quoted character.
   */
  public static char unquote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote) {
    final int n = str.length();
    if (! isQuoted(str, escapeChar, leftQuote, rightQuote)) {
      throw new IllegalArgumentException("Not a quoted character: " + str);
    }
    if (n == 3) {
      return str.charAt(1);
    } else {
      return str.charAt(2);
    }
  }

  /**
   * Converts a {@link Character} object to a {@code char} value.
   *
   * <pre>
   *   CharUtils.toPrimitive(null) = CharUtils.DEFAULT
   *   CharUtils.toPrimitive(new Character(' '))  = ' '
   *   CharUtils.toPrimitive(new Character('A'))  = 'A'
   * </pre>
   *
   * @param value
   *     the {@link Character} object to convert, which could be null.
   * @return the {@code char} value of the {@link Character} object; or {@link
   *     #DEFAULT} if the object is null.
   */
  public static char toPrimitive(@Nullable final Character value) {
    return (value == null ? DEFAULT : value.charValue());
  }

  /**
   * Converts the Character to a char handling {@code null}.
   *
   * <pre>
   *   CharUtils.toPrimitive(null, 'X') = 'X'
   *   CharUtils.toPrimitive(new Character(' '), 'X')  = ' '
   *   CharUtils.toPrimitive(new Character('A'), 'X')  = 'A'
   * </pre>
   *
   * @param value
   *     the {@link Character} object to convert, which could be null.
   * @param defaultValue
   *     the value to use if the {@link Character} object is null
   * @return the {@code char} value of the {@link Character} object; or the
   *     default value if the {@link Character} object is null.
   */
  public static char toPrimitive(@Nullable final Character value,
      final char defaultValue) {
    return (value == null ? defaultValue : value.charValue());
  }

  /**
   * 将字符值转换为 boolean 值。
   *
   * @param value 要转换的字符值
   * @return 如果字符值不为 0 则返回 {@code true}，否则返回 {@code false}
   */
  public static boolean toBoolean(final char value) {
    return (value != 0);
  }

  /**
   * 将 Character 对象转换为 boolean 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 boolean 值
   */
  public static boolean toBoolean(@Nullable final Character value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 boolean 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 boolean 值
   */
  public static boolean toBoolean(@Nullable final Character value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.charValue()));
  }

  /**
   * 将字符值转换为 Boolean 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Boolean 对象
   */
  public static Boolean toBooleanObject(final char value) {
    return Boolean.valueOf(toBoolean(value));
  }

  /**
   * 将 Character 对象转换为 Boolean 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Boolean 对象
   */
  public static Boolean toBooleanObject(@Nullable final Character value) {
    return (value == null ? null : toBooleanObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Boolean 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Boolean 对象
   */
  public static Boolean toBooleanObject(@Nullable final Character value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.charValue()));
  }

  /**
   * 将字符值转换为 byte 值。
   *
   * @param value 要转换的字符值
   * @return 转换后的 byte 值
   */
  public static byte toByte(final char value) {
    return (byte) value;
  }

  /**
   * 将 Character 对象转换为 byte 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 byte 值
   */
  public static byte toByte(@Nullable final Character value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 byte 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 byte 值
   */
  public static byte toByte(@Nullable final Character value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.charValue()));
  }

  /**
   * 将字符值转换为 Byte 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Byte 对象
   */
  public static Byte toByteObject(final char value) {
    return Byte.valueOf(toByte(value));
  }

  /**
   * 将 Character 对象转换为 Byte 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Byte 对象
   */
  public static Byte toByteObject(@Nullable final Character value) {
    return (value == null ? null : toByteObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Byte 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Byte 对象
   */
  public static Byte toByteObject(@Nullable final Character value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.charValue()));
  }

  /**
   * 将字符值转换为 short 值。
   *
   * @param value 要转换的字符值
   * @return 转换后的 short 值
   */
  public static short toShort(final char value) {
    return (short) value;
  }

  /**
   * 将 Character 对象转换为 short 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 short 值
   */
  public static short toShort(@Nullable final Character value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 short 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 short 值
   */
  public static short toShort(@Nullable final Character value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.charValue()));
  }

  /**
   * 将字符值转换为 Short 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Short 对象
   */
  public static Short toShortObject(final char value) {
    return toShort(value);
  }

  /**
   * 将 Character 对象转换为 Short 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Short 对象
   */
  public static Short toShortObject(@Nullable final Character value) {
    return (value == null ? null : toShortObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Short 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Short 对象
   */
  public static Short toShortObject(@Nullable final Character value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.charValue()));
  }

  /**
   * 将字符值转换为 int 值。
   *
   * @param value 要转换的字符值
   * @return 转换后的 int 值
   */
  public static int toInt(final char value) {
    return value;
  }

  /**
   * 将 Character 对象转换为 int 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 int 值
   */
  public static int toInt(@Nullable final Character value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 int 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 int 值
   */
  public static int toInt(@Nullable final Character value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.charValue()));
  }

  /**
   * 将字符值转换为 Integer 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Integer 对象
   */
  public static Integer toIntObject(final char value) {
    return toInt(value);
  }

  /**
   * 将 Character 对象转换为 Integer 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Integer 对象
   */
  public static Integer toIntObject(@Nullable final Character value) {
    return (value == null ? null : toIntObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Integer 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Integer 对象
   */
  public static Integer toIntObject(@Nullable final Character value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.charValue()));
  }

  /**
   * 将字符值转换为 long 值。
   *
   * @param value 要转换的字符值
   * @return 转换后的 long 值
   */
  public static long toLong(final char value) {
    return value;
  }

  /**
   * 将 Character 对象转换为 long 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 long 值
   */
  public static long toLong(@Nullable final Character value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 long 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 long 值
   */
  public static long toLong(@Nullable final Character value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.charValue()));
  }

  /**
   * 将字符值转换为 Long 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Long 对象
   */
  public static Long toLongObject(final char value) {
    return toLong(value);
  }

  /**
   * 将 Character 对象转换为 Long 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Long 对象
   */
  public static Long toLongObject(@Nullable final Character value) {
    return (value == null ? null : toLongObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Long 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Long 对象
   */
  public static Long toLongObject(@Nullable final Character value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.charValue()));
  }

  /**
   * 将字符值转换为 float 值。
   *
   * @param value 要转换的字符值
   * @return 转换后的 float 值
   */
  public static float toFloat(final char value) {
    return value;
  }

  /**
   * 将 Character 对象转换为 float 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 float 值
   */
  public static float toFloat(@Nullable final Character value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 float 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 float 值
   */
  public static float toFloat(@Nullable final Character value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.charValue()));
  }

  /**
   * 将字符值转换为 Float 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Float 对象
   */
  public static Float toFloatObject(final char value) {
    return toFloat(value);
  }

  /**
   * 将 Character 对象转换为 Float 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Float 对象
   */
  public static Float toFloatObject(@Nullable final Character value) {
    return (value == null ? null : toFloatObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Float 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Float 对象
   */
  public static Float toFloatObject(@Nullable final Character value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.charValue()));
  }

  /**
   * 将字符值转换为 double 值。
   *
   * @param value 要转换的字符值
   * @return 转换后的 double 值
   */
  public static double toDouble(final char value) {
    return value;
  }

  /**
   * 将 Character 对象转换为 double 值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 double 值
   */
  public static double toDouble(@Nullable final Character value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 double 值，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 double 值
   */
  public static double toDouble(@Nullable final Character value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.charValue()));
  }

  /**
   * 将字符值转换为 Double 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Double 对象
   */
  public static Double toDoubleObject(final char value) {
    return toDouble(value);
  }

  /**
   * 将 Character 对象转换为 Double 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Double 对象
   */
  public static Double toDoubleObject(@Nullable final Character value) {
    return (value == null ? null : toDoubleObject(value.charValue()));
  }

  /**
   * 将 Character 对象转换为 Double 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Double 对象
   */
  public static Double toDoubleObject(@Nullable final Character value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.charValue()));
  }

  /**
   * 将字符转换为包含该字符的字符串。
   *
   * <p>对于 ASCII 7 位字符，使用缓存，每次返回相同的 String 对象。
   *
   * <pre>
   *   CharUtils.toString(' ')  = " "
   *   CharUtils.toString('A')  = "A"
   * </pre>
   *
   * @param value
   *     要转换的字符
   * @return 包含指定字符的字符串
   */
  public static String toString(final char value) {
    if (value <= Ascii.MAX) {
      return CHAR_STRING_CACHE[value];
    } else {
      final char[] data = { value };
      return new String(data);
    }
  }

  /**
   * 将 Character 对象转换为包含该字符的字符串。
   *
   * <p>对于 ASCII 7 位字符，使用缓存，每次返回相同的 String 对象。
   *
   * <p>如果传入 {@code null}，则返回 {@code null}。
   *
   * <pre>
   *   CharUtils.toString(null) = null
   *   CharUtils.toString(' ')  = " "
   *   CharUtils.toString('A')  = "A"
   * </pre>
   *
   * @param value
   *     要转换的字符，可以为 null。
   * @return 包含指定字符的字符串，如果值为 null 则返回 null。
   */
  public static String toString(@Nullable final Character value) {
    return (value == null ? null : toString(value.charValue()));
  }

  /**
   * 将 Character 对象转换为包含该字符的字符串，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回包含该字符的字符串
   */
  public static String toString(@Nullable final Character value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.charValue()));
  }

  /**
   * 将 {@code char} 值转换为 4 位数的 Unicode 十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @param builder
   *     要追加 4 位数 Unicode 十六进制字符串的 {@link StringBuilder}，
   *     包括前导的 "\\u"。
   * @deprecated 使用 {@link #toUnicodeHexString(char, StringBuilder)} 代替。
   */
  @Deprecated
  public static void toHexString(final char value,
      final StringBuilder builder) {
    toUnicodeHexString(value, builder);
  }

  /**
   * 将 {@code char} 值转换为 4 位数的 Unicode 十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @return
   *     值的 4 位数十六进制 Unicode 字符串，包括前导的 "\\u"。
   * @deprecated 使用 {@link #toUnicodeHexString(char)} 代替。
   */
  @Deprecated
  public static String toHexString(final char value) {
    return toUnicodeHexString(value);
  }

  /**
   * 将 {@code char} 值转换为 4 位数的 Unicode 十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @param builder
   *     要追加 4 位数 Unicode 十六进制字符串的 {@link StringBuilder}，
   *     包括前导的 "\\u"。
   */
  public static void toUnicodeHexString(final char value,
      final StringBuilder builder) {
    // stop checkstyle: MagicNumberCheck
    builder.append("\\u")
           .append(DEFAULT_UPPERCASE_DIGITS[(value >>> 12) & 0x0F])
           .append(DEFAULT_UPPERCASE_DIGITS[(value >>> 8) & 0x0F])
           .append(DEFAULT_UPPERCASE_DIGITS[(value >>> 4) & 0x0F])
           .append(DEFAULT_UPPERCASE_DIGITS[value & 0x0F]);
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 将 {@code char} 值转换为 4 位数的 Unicode 十六进制字符串。
   *
   * @param value
   *     要转换的值。
   * @return
   *     值的 4 位数十六进制 Unicode 字符串，包括前导的 "\\u"。
   */
  public static String toUnicodeHexString(final char value) {
    final StringBuilder builder = new StringBuilder();
    toUnicodeHexString(value, builder);
    return builder.toString();
  }

  /**
   * 转义控制字符。
   *
   * @param value
   *     要转义的字符。
   * @return
   *     控制字符的转义字符串，如果不是控制字符则返回原字符的字符串。
   */
  public static String escapeControlChar(final char value) {
    if (value <= 0x1F) {  // Control character (ASCII 0-31)
      switch (value) {
        case '\b':
          return "\\b";
        case '\f':
          return "\\f";
        case '\n':
          return "\\n";
        case '\r':
          return "\\r";
        case '\t':
          return "\\t";
        default:
          // For other control characters, append the Unicode Hex string
          return toUnicodeHexString(value);
      }
    } else {
      return String.valueOf(value);
    }
  }

  /**
   * 将字符值转换为 Date 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 Date 对象
   */
  public static Date toDate(final char value) {
    return new Date(value);
  }

  /**
   * 将 Character 对象转换为 Date 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 Date 对象
   */
  public static Date toDate(@Nullable final Character value) {
    return (value == null ? null : new Date(value));
  }

  /**
   * 将 Character 对象转换为 Date 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 Date 对象
   */
  public static Date toDate(@Nullable final Character value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value));
  }

  /**
   * 将字符值转换为字节数组。
   *
   * @param value 要转换的字符值
   * @return 转换后的字节数组
   */
  public static byte[] toByteArray(final char value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

  /**
   * 将字符值转换为字节数组，使用指定的字节序。
   *
   * @param value 要转换的字符值
   * @param byteOrder 要使用的字节序
   * @return 转换后的字节数组
   */
  public static byte[] toByteArray(final char value, final ByteOrder byteOrder) {
    final byte[] result = new byte[2];
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      result[0] = (byte) (value >>> Byte.SIZE);
      result[1] = (byte) value;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      result[1] = (byte) (value >>> Byte.SIZE);
      result[0] = (byte) value;
    } else {
      throw new UnsupportedByteOrderException(byteOrder);
    }
    return result;
  }

  /**
   * 将 Character 对象转换为字节数组。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Character value) {
    return (value == null ? null : toByteArray(value.charValue()));
  }

  /**
   * 将 Character 对象转换为字节数组，使用指定的字节序。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param byteOrder 要使用的字节序
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Character value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.charValue(), byteOrder));
  }

  /**
   * 将 Character 对象转换为字节数组，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Character value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.charValue()));
  }

  /**
   * 将 Character 对象转换为字节数组，使用指定的默认值和字节序。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @param byteOrder 要使用的字节序
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的字节数组
   */
  public static byte[] toByteArray(@Nullable final Character value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.charValue(), byteOrder));
  }

  /**
   * 将字符值转换为 Class 对象。
   *
   * @param value 要转换的字符值
   * @return Character.TYPE
   */
  public static Class<?> toClass(final char value) {
    return Character.TYPE;
  }

  /**
   * 将 Character 对象转换为 Class 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回 Character.class
   */
  public static Class<?> toClass(@Nullable final Character value) {
    return (value == null ? null : Character.class);
  }

  /**
   * 将 Character 对象转换为 Class 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回 Character.class
   */
  public static Class<?> toClass(@Nullable final Character value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Character.class);
  }

  /**
   * 将字符值转换为 BigInteger 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 BigInteger 对象
   */
  public static BigInteger toBigInteger(final char value) {
    return BigInteger.valueOf(value);
  }

  /**
   * 将 Character 对象转换为 BigInteger 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 BigInteger 对象
   */
  public static BigInteger toBigInteger(@Nullable final Character value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  /**
   * 将 Character 对象转换为 BigInteger 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 BigInteger 对象
   */
  public static BigInteger toBigInteger(@Nullable final Character value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  /**
   * 将字符值转换为 BigDecimal 对象。
   *
   * @param value 要转换的字符值
   * @return 转换后的 BigDecimal 对象
   */
  public static BigDecimal toBigDecimal(final char value) {
    return BigDecimal.valueOf(value);
  }

  /**
   * 将 Character 对象转换为 BigDecimal 对象。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @return 如果 Character 对象为 null 则返回 null，否则返回转换后的 BigDecimal 对象
   */
  public static BigDecimal toBigDecimal(@Nullable final Character value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  /**
   * 将 Character 对象转换为 BigDecimal 对象，使用指定的默认值。
   *
   * @param value 要转换的 Character 对象，可以为 null
   * @param defaultValue 当 Character 对象为 null 时使用的默认值
   * @return 如果 Character 对象为 null 则返回默认值，否则返回转换后的 BigDecimal 对象
   */
  public static BigDecimal toBigDecimal(@Nullable final Character value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  /**
   * 将字符串转换为 Unicode 格式 '\u0020'。
   *
   * <p>此格式是 Java 源代码格式。
   * <pre>
   *   CharUtils.toUnicodeEscape(' ') = "\u0020"
   *   CharUtils.toUnicodeEscape('A') = "\u0041"
   * </pre>
   *
   * @param ch
   *     要转换的字符
   * @return 转义的 Unicode 字符串
   */
  public static String toUnicodeEscape(final int ch) {
    final StringBuilder builder = new StringBuilder();
    toUnicodeEscape(ch, builder);
    return builder.toString();
  }

  /**
   * 将字符串转换为 Unicode 格式 '\u0020'。
   *
   * <p>此格式是 Java 源代码格式。
   * <pre>
   *   CharUtils.toUnicodeEscape(' ') = "\u0020"
   *   CharUtils.toUnicodeEscape('A') = "\u0041"
   * </pre>
   *
   * @param ch
   *     要转换的字符。
   * @param builder
   *     要追加结果的 {@link StringBuilder}。
   * @return {@link StringBuilder}。
   */
  public static StringBuilder toUnicodeEscape(final int ch,
      final StringBuilder builder) {
    //  stop checkstyle: MagicNumberCheck
    return builder.append("\\u")
        .append(UPPERCASE_DIGITS[((ch >>> 12) & 0xF)])
        .append(UPPERCASE_DIGITS[((ch >>> 8) & 0xF)])
        .append(UPPERCASE_DIGITS[((ch >>> 4) & 0xF)])
        .append(UPPERCASE_DIGITS[(ch & 0xF)]);
    //  resume checkstyle: MagicNumberCheck
  }

  /**
   * 将字符串转换为 Unicode 格式 '\u0020'。
   *
   * <p>此格式是 Java 源代码格式。
   *
   * <p>如果传入 {@code null}，则返回 {@code null}。
   * <pre>
   *   CharUtils.toUnicodeEscape(null) = null
   *   CharUtils.toUnicodeEscape(' ')  = "\u0020"
   *   CharUtils.toUnicodeEscape('A')  = "\u0041"
   * </pre>
   *
   * @param ch
   *     要转换的字符，可以为 null
   * @return 转义的 Unicode 字符串，如果输入为 null 则返回 null
   */
  public static String toUnicodeEscape(@Nullable final Character ch) {
    if (ch == null) {
      return null;
    } else {
      final StringBuilder builder = new StringBuilder();
      toUnicodeEscape(ch, builder);
      return builder.toString();
    }
  }

  /**
   * 将字符串转换为 Unicode 格式 '\u0020'。
   *
   * <p>此格式是 Java 源代码格式。
   *
   * <p>如果传入 {@code null}，则返回 {@code null}。
   * <pre>
   *   CharUtils.toUnicodeEscape(null) = null
   *   CharUtils.toUnicodeEscape(' ')  = "\u0020"
   *   CharUtils.toUnicodeEscape('A')  = "\u0041"
   * </pre>
   *
   * @param ch
   *     要转换的字符，不能为 null
   * @param builder
   *     要追加结果的 {@link StringBuilder}。
   * @return {@link StringBuilder}。
   */
  public static StringBuilder toUnicodeEscape(final Character ch,
      final StringBuilder builder) {
    final int chValue = ch;
    //  stop checkstyle: MagicNumberCheck
    return builder.append("\\u")
        .append(UPPERCASE_DIGITS[((chValue >>> 12) & 0xF)])
        .append(UPPERCASE_DIGITS[((chValue >>> 8) & 0xF)])
        .append(UPPERCASE_DIGITS[((chValue >>> 4) & 0xF)])
        .append(UPPERCASE_DIGITS[(chValue & 0xF)]);
    //  resume checkstyle: MagicNumberCheck
  }

  private static final Set<ClassKey> COMPARABLE_TYPES =
      ImmutableSet.of(new ClassKey(char.class),
          new ClassKey(Character.class),
          new ClassKey(String.class));

  /**
   * 测试指定的类型的值是否可以和{@code char}或{@code Character}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code char}或{@code Character}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(new ClassKey(type)) || Enum.class.isAssignableFrom(type);
  }
}
