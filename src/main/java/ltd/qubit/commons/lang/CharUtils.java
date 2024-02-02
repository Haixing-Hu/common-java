////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * This class provides operations on {@code char} primitives and
 * {@link Character} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception will
 * not be thrown for a {@code null} input. Each method documents its behavior in
 * more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@code char} values or
 * {@link Character} objects to common types.
 * </p>
 *
 * @author Haixing Hu
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
   * Tests whether a character is an ASCII digit.
   *
   * @param ch
   *     the character to be tested.
   * @return
   *     {@code true} if the character is an ASCII digit, i.e., between '0' and
   *     '9'; {@code false} otherwise.
   */
  public static boolean isAsciiDigit(final char ch) {
    return (ch >= '0' && ch <= '9');
  }

  /**
   * Tests whether a character is an ASCII hex digit.
   *
   * @param ch
   *     the character to be tested.
   * @return
   *     {@code true} if the character is a hex ASCII digit, i.e., between
   *     '0' and '9', or between 'a' and 'f', or between 'A' and 'F';
   *     {@code false} otherwise.
   */
  public static boolean isAsciiHexDigit(final char ch) {
    return (ch >= '0' && ch <= '9')
        || (ch >= 'a' && ch <= 'f')
        || (ch >= 'A' && ch <= 'F');
  }

  /**
   * Tests whether a character is an octal hex digit.
   *
   * @param ch
   *     the character to be tested.
   * @return
   *     {@code true} if the character is a octal ASCII digit, i.e., between
   *     '0' and '7'; {@code false} otherwise.
   */
  public static boolean isAsciiOctalDigit(final char ch) {
    return (ch >= '0' && ch <= '7');
  }

  /**
   * Determines whether the specified code point is a "graphic" character
   * (printable, excluding spaces).
   *
   * <p>True for all characters except those with general categories "Cc"
   * (control codes), "Cf" (format controls), "Cs" (surrogates), "Cn"
   * (unassigned), and "Z" (separators).
   *
   * <p>Note that a code point is either graph or blank.
   *
   * @param codePoint
   *     the code point of an Unicode.
   * @return true if the specified code point is a "graphic" character; false
   *     otherwise.
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
   * Determines whether the specified code point is a "blank" character
   * (non-printable or white spaces).
   *
   * <p>True for all characters with general categories "Cc" (control codes),
   * "Cf" (format controls), "Cs" (surrogates), "Cn" (unassigned), and "Z"
   * (separators).
   *
   * <p>Note that a code point is either graph or blank.
   *
   * @param codePoint
   *     the code point of an Unicode.
   * @return true if the specified code point is a "graphic" character; false
   *     otherwise.
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

  public static boolean toBoolean(final char value) {
    return (value != 0);
  }

  public static boolean toBoolean(@Nullable final Character value) {
    return (value == null ? BooleanUtils.DEFAULT : toBoolean(value.charValue()));
  }

  public static boolean toBoolean(@Nullable final Character value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : toBoolean(value.charValue()));
  }

  public static Boolean toBooleanObject(final char value) {
    return Boolean.valueOf(toBoolean(value));
  }

  public static Boolean toBooleanObject(@Nullable final Character value) {
    return (value == null ? null : toBooleanObject(value.charValue()));
  }

  public static Boolean toBooleanObject(@Nullable final Character value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue : toBooleanObject(value.charValue()));
  }

  public static byte toByte(final char value) {
    return (byte) value;
  }

  public static byte toByte(@Nullable final Character value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.charValue()));
  }

  public static byte toByte(@Nullable final Character value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.charValue()));
  }

  public static Byte toByteObject(final char value) {
    return Byte.valueOf(toByte(value));
  }

  public static Byte toByteObject(@Nullable final Character value) {
    return (value == null ? null : toByteObject(value.charValue()));
  }

  public static Byte toByteObject(@Nullable final Character value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.charValue()));
  }

  public static short toShort(final char value) {
    return (short) value;
  }

  public static short toShort(@Nullable final Character value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.charValue()));
  }

  public static short toShort(@Nullable final Character value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.charValue()));
  }

  public static Short toShortObject(final char value) {
    return toShort(value);
  }

  public static Short toShortObject(@Nullable final Character value) {
    return (value == null ? null : toShortObject(value.charValue()));
  }

  public static Short toShortObject(@Nullable final Character value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.charValue()));
  }

  public static int toInt(final char value) {
    return value;
  }

  public static int toInt(@Nullable final Character value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.charValue()));
  }

  public static int toInt(@Nullable final Character value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.charValue()));
  }

  public static Integer toIntObject(final char value) {
    return toInt(value);
  }

  public static Integer toIntObject(@Nullable final Character value) {
    return (value == null ? null : toIntObject(value.charValue()));
  }

  public static Integer toIntObject(@Nullable final Character value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.charValue()));
  }

  public static long toLong(final char value) {
    return value;
  }

  public static long toLong(@Nullable final Character value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.charValue()));
  }

  public static long toLong(@Nullable final Character value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.charValue()));
  }

  public static Long toLongObject(final char value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Character value) {
    return (value == null ? null : toLongObject(value.charValue()));
  }

  public static Long toLongObject(@Nullable final Character value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.charValue()));
  }

  public static float toFloat(final char value) {
    return value;
  }

  public static float toFloat(@Nullable final Character value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.charValue()));
  }

  public static float toFloat(@Nullable final Character value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.charValue()));
  }

  public static Float toFloatObject(final char value) {
    return toFloat(value);
  }

  public static Float toFloatObject(@Nullable final Character value) {
    return (value == null ? null : toFloatObject(value.charValue()));
  }

  public static Float toFloatObject(@Nullable final Character value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.charValue()));
  }

  public static double toDouble(final char value) {
    return value;
  }

  public static double toDouble(@Nullable final Character value) {
    return (value == null ? DoubleUtils.DEFAULT : toDouble(value.charValue()));
  }

  public static double toDouble(@Nullable final Character value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.charValue()));
  }

  public static Double toDoubleObject(final char value) {
    return toDouble(value);
  }

  public static Double toDoubleObject(@Nullable final Character value) {
    return (value == null ? null : toDoubleObject(value.charValue()));
  }

  public static Double toDoubleObject(@Nullable final Character value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : toDoubleObject(value.charValue()));
  }

  /**
   * Converts the character to a String that contains the one character.
   *
   * <p>For ASCII 7 bit characters, this uses a cache that will return the same
   * String object each time.
   *
   * <pre>
   *   CharUtils.toString(' ')  = " "
   *   CharUtils.toString('A')  = "A"
   * </pre>
   *
   * @param value
   *     the character to convert
   * @return a String containing the one specified character
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
   * Converts the character to a String that contains the one character.
   *
   * <p>For ASCII 7 bit characters, this uses a cache that will return the same
   * String object each time.
   *
   * <p>If {@code null} is passed in, {@code null} will be returned.
   *
   * <pre>
   *   CharUtils.toString(null) = null
   *   CharUtils.toString(' ')  = " "
   *   CharUtils.toString('A')  = "A"
   * </pre>
   *
   * @param value
   *     the character to convert, which could be null.
   * @return a String containing the one specified character, or null if the
   *     value is null.
   */
  public static String toString(@Nullable final Character value) {
    return (value == null ? null : toString(value.charValue()));
  }

  public static String toString(@Nullable final Character value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.charValue()));
  }

  /**
   * Convert a {@code char} value into hex string.
   *
   * @param value
   *     the value to be converted.
   * @param builder
   *     a {@link StringBuilder} where to append the hex string.
   */
  public static void toHexString(final char value,
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
   * Convert a {@code char} value into hex string.
   *
   * @param value
   *     the value to be converted.
   * @return the hex string of the value.
   */
  public static String toHexString(final char value) {
    final StringBuilder builder = new StringBuilder();
    toHexString(value, builder);
    return builder.toString();
  }

  public static Date toDate(final char value) {
    return new Date(value);
  }

  public static Date toDate(@Nullable final Character value) {
    return (value == null ? null : new Date(value));
  }

  public static Date toDate(@Nullable final Character value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value));
  }

  public static byte[] toByteArray(final char value) {
    return toByteArray(value, DEFAULT_BYTE_ORDER);
  }

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

  public static byte[] toByteArray(@Nullable final Character value) {
    return (value == null ? null : toByteArray(value.charValue()));
  }

  public static byte[] toByteArray(@Nullable final Character value,
      final ByteOrder byteOrder) {
    return (value == null ? null : toByteArray(value.charValue(), byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Character value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.charValue()));
  }

  public static byte[] toByteArray(@Nullable final Character value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue
                          : toByteArray(value.charValue(), byteOrder));
  }

  public static Class<?> toClass(final char value) {
    return Character.TYPE;
  }

  public static Class<?> toClass(@Nullable final Character value) {
    return (value == null ? null : Character.class);
  }

  public static Class<?> toClass(@Nullable final Character value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Character.class);
  }

  public static BigInteger toBigInteger(final char value) {
    return BigInteger.valueOf(value);
  }

  public static BigInteger toBigInteger(@Nullable final Character value) {
    return (value == null ? null : BigInteger.valueOf(value));
  }

  public static BigInteger toBigInteger(@Nullable final Character value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value));
  }

  public static BigDecimal toBigDecimal(final char value) {
    return BigDecimal.valueOf(value);
  }

  public static BigDecimal toBigDecimal(@Nullable final Character value) {
    return (value == null ? null : BigDecimal.valueOf(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final Character value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value));
  }

  /**
   * Converts the string to the unicode format '\u0020'.
   *
   * <p>This format is the Java source code format.
   * <pre>
   *   CharUtils.toUnicodeEscape(' ') = "\u0020"
   *   CharUtils.toUnicodeEscape('A') = "\u0041"
   * </pre>
   *
   * @param ch
   *     the character to convert
   * @return the escaped unicode string
   */
  public static String toUnicodeEscape(final int ch) {
    final StringBuilder builder = new StringBuilder();
    toUnicodeEscape(ch, builder);
    return builder.toString();
  }

  /**
   * Converts the string to the unicode format '\u0020'.
   *
   * <p>This format is the Java source code format.
   * <pre>
   *   CharUtils.toUnicodeEscape(' ') = "\u0020"
   *   CharUtils.toUnicodeEscape('A') = "\u0041"
   * </pre>
   *
   * @param ch
   *     the character to convert.
   * @param builder
   *     the {@link StringBuilder} where to append the result.
   * @return the {@link StringBuilder}.
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
   * Converts the string to the unicode format '\u0020'.
   *
   * <p>This format is the Java source code format.
   *
   * <p>If {@code null} is passed in, {@code null} will be returned.
   * <pre>
   *   CharUtils.toUnicodeEscape(null) = null
   *   CharUtils.toUnicodeEscape(' ')  = "\u0020"
   *   CharUtils.toUnicodeEscape('A')  = "\u0041"
   * </pre>
   *
   * @param ch
   *     the character to convert, may be null
   * @return the escaped unicode string, null if null input
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
   * Converts the string to the unicode format '\u0020'.
   *
   * <p>This format is the Java source code format.
   *
   * <p>If {@code null} is passed in, {@code null} will be returned.
   * <pre>
   *   CharUtils.toUnicodeEscape(null) = null
   *   CharUtils.toUnicodeEscape(' ')  = "\u0020"
   *   CharUtils.toUnicodeEscape('A')  = "\u0041"
   * </pre>
   *
   * @param ch
   *     the character to convert, which can't be null
   * @param builder
   *     the {@link StringBuilder} where to append the result.
   * @return the {@link StringBuilder}.
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

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(char.class, Character.class, String.class);

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
    return COMPARABLE_TYPES.contains(type) || Enum.class.isAssignableFrom(type);
  }
}
