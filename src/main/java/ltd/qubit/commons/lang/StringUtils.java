////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.text.Ascii;
import ltd.qubit.commons.text.BooleanFormat;
import ltd.qubit.commons.text.CharSequenceCodePointIterator;
import ltd.qubit.commons.text.DateFormat;
import ltd.qubit.commons.text.Joiner;
import ltd.qubit.commons.text.NumberFormat;
import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.Searcher;
import ltd.qubit.commons.text.Splitter;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.Unicode;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.HexCodec;
import ltd.qubit.commons.util.codec.IsoLocalDateCodec;
import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;
import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;
import ltd.qubit.commons.util.codec.LocalDateCodec;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;
import ltd.qubit.commons.util.codec.LocalTimeCodec;
import ltd.qubit.commons.util.filter.character.AcceptSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.character.AsciiCharFilter;
import ltd.qubit.commons.util.filter.character.AsciiPrintableCharFilter;
import ltd.qubit.commons.util.filter.character.BlankCharFilter;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.codepoint.BlankCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.DigitCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.DigitSpaceCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterDigitCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterDigitSpaceCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.LetterSpaceCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.WhitespaceCodePointFilter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireEqual;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.SplitOption.CAMEL_CASE;
import static ltd.qubit.commons.lang.SplitOption.IGNORE_EMPTY;
import static ltd.qubit.commons.lang.SplitOption.TRIM;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithSubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;

/**
 * This class provides operations on {@link String} that are {@code null} safe.
 *
 * <ul>
 * <li><b>IsEmpty/IsBlank</b> - checks if a string contains text</li>
 * <li><b>Trim/Strip</b> - removes leading and trailing whitespace</li>
 * <li><b>Equals</b> - compares two strings null-safe</li>
 * <li><b>startsWith</b> - check if a string starts with a prefix null-safe</li>
 * <li><b>endsWith</b> - check if a string ends with a suffix null-safe</li>
 * <li><b>IndexOf/LastIndexOf/Contains</b> - null-safe current-of checks
 * <li><b>IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut</b> -
 * current-of any of a set of Strings</li>
 * <li><b>ContainsOnly/ContainsNone/ContainsAny</b> - does String contains
 * only/none/any of these characters</li>
 * <li><b>Substring/Left/Right/Mid</b> - null-safe substring extractions</li>
 * <li><b>SubstringBefore/SubstringAfter/SubstringBetween</b> - substring
 * extraction relative to other strings</li>
 * <li><b>Split/Join</b> - splits a string into an array of substrings and vice
 * versa</li>
 * <li><b>Remove/Delete</b> - removes part of a string</li>
 * <li><b>Replace/Overlay</b> - Searches a string and replaces one String with
 * another</li>
 * <li><b>Chomp/Chop</b> - removes the last part of a string</li>
 * <li><b>LeftPad/RightPad/Center/Repeat</b> - pads a string</li>
 * <li><b>UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize</b> - changes the
 * case of a string</li>
 * <li><b>CountMatches</b> - counts the number of occurrences of one String in
 * another</li>
 * <li><b>IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable</b> - checks the
 * characters in a string</li>
 * <li><b>DefaultString</b> - protects against a {@code null} input String</li>
 * <li><b>Reverse/ReverseDelimited</b> - reverses a string</li>
 * <li><b>Abbreviate</b> - abbreviates a string using ellipsis</li>
 * <li><b>Difference</b> - compares Strings and reports on their differences</li>
 * <li><b>LevensteinDistance</b> - the number of changes needed to change one
 * String into another</li>
 * </ul>
 *
 * <p>The {@code Strings} class defines certain words related to String
 * handling.
 * <ul>
 * <li>null - {@code null}</li>
 * <li>empty - a zero-length string ({@code ""})</li>
 * <li>space - the space character ({@code ' '}, char 32)</li>
 * <li>whitespace - the characters defined by
 * {@link Character#isWhitespace(char)}</li>
 * <li>trim - the characters &lt;= 32 as in {@link String#trim()}</li>
 * </ul>
 *
 * <p>{@code Strings} handles {@code null} input Strings quietly. That is to say
 * that a {@code null} input will return {@code null}. Where a {@code boolean}
 * or {@code int} is being returned details vary by method.
 *
 * <p>A side effect of the {@code null} handling is that a
 * {@code NullPointerException} should be considered a bug in {@code Strings}
 * (except for deprecated methods).
 *
 * <p>Methods in this class give sample code to explain their operation. The
 * symbol {@code *} is used to indicate any input including {@code null}.
 *
 * @author Haixing Hu
 */
@SuppressWarnings("overloads")
@ThreadSafe
public class StringUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

  /**
   * Represents an empty string constant.
   */
  public static final String EMPTY = "";

  /**
   * String representation of boolean true value.
   */
  public static final String TRUE = "true";

  /**
   * String representation of boolean false value.
   */
  public static final String FALSE = "false";

  /**
   * String representation of affirmative response.
   */
  public static final String YES = "yes";

  /**
   * String representation of negative response.
   */
  public static final String NO = "no";

  /**
   * String representation of enabled state.
   */
  public static final String ON = "on";

  /**
   * String representation of disabled state.
   */
  public static final String OFF = "off";

  /**
   * Represents an ellipsis string constant.
   */
  public static final String ELLIPSES = "...";

  /**
   * Represents a single space character.
   */
  public static final String SPACE = " ";

  /**
   * Represents a tab character.
   */
  public static final String TAB = "\t";

  /**
   * String representation of minimum byte value (-128).
   */
  public static final String BYTE_MIN = "-128";

  /**
   * String representation of absolute value of minimum byte (128).
   */
  public static final String BYTE_MIN_ABS = "128";

  /**
   * String representation of maximum byte value (127).
   */
  public static final String BYTE_MAX = "127";

  /**
   * String representation of minimum short value (-32768).
   */
  public static final String SHORT_MIN = "-32768";

  /**
   * String representation of absolute value of minimum short (32768).
   */
  public static final String SHORT_MIN_ABS = "32768";

  /**
   * String representation of maximum short value (32767).
   */
  public static final String SHORT_MAX = "32767";

  /**
   * String representation of minimum integer value (-2147483648).
   */
  public static final String INT_MIN = "-2147483648";

  /**
   * String representation of absolute value of minimum integer (2147483648).
   */
  public static final String INT_MIN_ABS = "2147483648";

  /**
   * String representation of maximum integer value (2147483647).
   */
  public static final String INT_MAX = "2147483647";

  /**
   * String representation of minimum long value (-9223372036854775808).
   */
  public static final String LONG_MIN = "-9223372036854775808";

  /**
   * String representation of absolute value of minimum long (9223372036854775808).
   */
  public static final String LONG_MIN_ABS = "9223372036854775808";

  /**
   * String representation of maximum long value (9223372036854775807).
   */
  public static final String LONG_MAX = "9223372036854775807";

  /**
   * String representation of minimum float value (1.4E-45).
   */
  public static final String FLOAT_MIN = "1.4E-45";

  /**
   * String representation of maximum float value (3.4028235E38).
   */
  public static final String FLOAT_MAX = "3.4028235E38";

  /**
   * String representation of minimum double value (4.9E-324).
   */
  public static final String DOUBLE_MIN = "4.9E-324";

  /**
   * String representation of maximum double value (1.7976931348623157E308).
   */
  public static final String DOUBLE_MAX = "1.7976931348623157E308";

  /**
   * The maximum size to which the padding constant(s) can expand.
   */
  public static final int PAD_LIMIT = 8192;

  /**
   * The assumption of the length of Object.toString() result.
   */
  private static final int TO_STRING_LENGTH_ASSUMPTION = 16;

  /**
   * The assumption of the number of objects in a iterable collection.
   */
  private static final int OBJECT_COUNT_ASSUMPTION = 64;

  private StringUtils() {
  }

  /**
   * Tests whether a string is not longer than a length.
   *
   * @param str
   *     The string to be tested, which may be null.
   * @param len
   *     The length to be test.
   * @return {@code true} if the string is null or its length is not longer than
   *     {@code len}; {@code false} otherwise.
   */
  public static boolean isNotLongerThan(@Nullable final CharSequence str,
      final int len) {
    return (str == null) || (str.length() <= len);
  }

  /**
   * Tests whether a string is longer than a length.
   *
   * @param str
   *     The string to be tested, which may be null.
   * @param len
   *     The length to be test.
   * @return {@code true} if the string is not {@code null} and its length is
   *     longer than {@code len}; {@code false} otherwise.
   */
  public static boolean isLongerThan(@Nullable final CharSequence str,
      final int len) {
    return (str != null) && (str.length() > len);
  }

  /**
   * Checks if a string is empty ("") or null.
   *
   * <pre>
   * StringUtils.isEmpty(null)      = true
   * StringUtils.isEmpty("")        = true
   * StringUtils.isEmpty(" ")       = false
   * StringUtils.isEmpty("bob")     = false
   * StringUtils.isEmpty("  bob  ") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if the string is empty or null; {@code false}
   *     otherwise.
   */
  public static boolean isEmpty(@Nullable final CharSequence str) {
    return (str == null) || (str.length() == 0);
  }

  /**
   * Checks if a string is not empty ("") and not {@code null}.
   * <pre>
   * StringUtils.isNotEmpty(null)      = false
   * StringUtils.isNotEmpty("")        = false
   * StringUtils.isNotEmpty(" ")       = true
   * StringUtils.isNotEmpty("bob")     = true
   * StringUtils.isNotEmpty("  bob  ") = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if the string is not empty and not {@code null};
   *     {@code false} otherwise.
   */
  public static boolean isNotEmpty(@Nullable final CharSequence str) {
    return (str != null) && (str.length() > 0);
  }

  /**
   * Checks whether a string contains only non-printable characters or
   * whitespace, or the string is empty ("") or null.
   * <pre>
   * StringUtils.isBlank(null)      = true
   * StringUtils.isBlank("")        = true
   * StringUtils.isBlank(" ")       = true
   * StringUtils.isBlank("bob")     = false
   * StringUtils.isBlank("  bob  ") = false
   * StringUtils.isBlank("\n \r")   = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if the string is null, empty or all blanks.
   */
  public static boolean isBlank(@Nullable final CharSequence str) {
    return (str == null) || containsOnly(str, BlankCodePointFilter.INSTANCE);
  }

  /**
   * Checks whether a string does NOT contain only blanks.
   * <pre>
   * StringUtils.isNotBlank(null)      = false
   * StringUtils.isNotBlank("")        = false
   * StringUtils.isNotBlank(" ")       = false
   * StringUtils.isNotBlank("bob")     = true
   * StringUtils.isNotBlank("  bob  ") = true
   * StringUtils.isNotBlank("\n \r")   = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if the string is not {@code null}, not empty and not
   *     contains only blanks; {@code false}otherwise.
   * @see #isBlank(CharSequence)
   */
  public static boolean isNotBlank(@Nullable final CharSequence str) {
    return (str != null) && (!containsOnly(str, BlankCodePointFilter.INSTANCE));
  }

  /**
   * Checks if a string is empty ("") or null or contains only blanks.
   *
   * <pre>
   * StringUtils.isEmptyOrBlank(null)      = true
   * StringUtils.isEmptyOrBlank("")        = true
   * StringUtils.isEmptyOrBlank(" ")       = true
   * StringUtils.isEmptyOrBlank("bob")     = false
   * StringUtils.isEmptyOrBlank("  bob  ") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @return {@code true} if the string is empty or null or contains only blanks;
   *     {@code false} otherwise.
   */
  public static boolean isEmptyOrBlank(@Nullable final CharSequence str) {
    return (str == null)
        || (str.length() == 0)
        || containsOnly(str, BlankCodePointFilter.INSTANCE);
  }

  /**
   * Checks if a string is not empty ("") and not {@code null}.
   * <pre>
   * StringUtils.isNotEmptyNorBlank(null)      = false
   * StringUtils.isNotEmptyNorBlank("")        = false
   * StringUtils.isNotEmptyNorBlank(" ")       = false
   * StringUtils.isNotEmptyNorBlank("bob")     = true
   * StringUtils.isNotEmptyNorBlank("  bob  ") = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if the string is not empty nor null nor contains only
   *     blanks; {@code false} otherwise.
   */
  public static boolean isNotEmptyNorBlank(@Nullable final CharSequence str) {
    return (str != null)
        && (str.length() > 0)
        && (!containsOnly(str, BlankCodePointFilter.INSTANCE));
  }

  /**
   * Checks whether a string contains only whitespace, or the string is empty
   * ("") or null.
   * <pre>
   * StringUtils.isWhitespace(null)      = true
   * StringUtils.isWhitespace("")        = true
   * StringUtils.isWhitespace(" ")       = true
   * StringUtils.isWhitespace("bob")     = false
   * StringUtils.isWhitespace("  bob  ") = false
   * StringUtils.isWhitespace("\r\n")    = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if the string is null, empty or all whitespace.
   */
  public static boolean isWhitespace(@Nullable final CharSequence str) {
    return (str == null) || containsOnly(str, WhitespaceCodePointFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only unicode letters.
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will
   * return {@code true}.
   * <pre>
   * StringUtils.isLetter(null)   = false
   * StringUtils.isLetter("")     = true
   * StringUtils.isLetter("  ")   = false
   * StringUtils.isLetter("abc")  = true
   * StringUtils.isLetter("ab2c") = false
   * StringUtils.isLetter("ab-c") = false
   * </pre>
   *
   * @param str
   *     the string to check, which may be null.
   * @return {@code true} if only contains letters, and is non-null.
   */
  public static boolean isLetter(@Nullable final CharSequence str) {
    return containsOnly(str, LetterCodePointFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only unicode letters and space (' ').
   *
   * <p>{@code null} will return {@code false} An empty String ("") will return
   * {@code true}.
   * <pre>
   * StringUtils.isLetterSpace(null)   = false
   * StringUtils.isLetterSpace("")     = true
   * StringUtils.isLetterSpace("  ")   = true
   * StringUtils.isLetterSpace("abc")  = true
   * StringUtils.isLetterSpace("ab c") = true
   * StringUtils.isLetterSpace("ab \tc") = true
   * StringUtils.isLetterSpace("ab \f\nc") = true
   * StringUtils.isLetterSpace("ab2c") = false
   * StringUtils.isLetterSpace("ab-c") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if only contains letters and space, and is non-null
   */
  public static boolean isLetterSpace(@Nullable final CharSequence str) {
    return containsOnly(str, LetterSpaceCodePointFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only unicode letters or digits.
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will
   * return {@code true}.
   * <pre>
   * StringUtils.isLetterDigit(null)   = false
   * StringUtils.isLetterDigit("")     = true
   * StringUtils.isLetterDigit("  ")   = false
   * StringUtils.isLetterDigit("abc")  = true
   * StringUtils.isLetterDigit("ab c") = false
   * StringUtils.isLetterDigit("ab2c") = true
   * StringUtils.isLetterDigit("ab-c") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if only contains letters or digits, and is non-null
   */
  public static boolean isLetterDigit(@Nullable final CharSequence str) {
    return containsOnly(str, LetterDigitCodePointFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only unicode letters, digits or space (
   * {@code ' '}).
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will
   * return {@code true}.
   * <pre>
   * StringUtils.isLetterDigitSpace(null)   = false
   * StringUtils.isLetterDigitSpace("")     = true
   * StringUtils.isLetterDigitSpace("  ")   = true
   * StringUtils.isLetterDigitSpace("abc")  = true
   * StringUtils.isLetterDigitSpace("ab c") = true
   * StringUtils.isLetterDigitSpace("ab2c") = true
   * StringUtils.isLetterDigitSpace("ab-c") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if only contains letters, digits or space, and is
   *     non-null
   */
  public static boolean isLetterDigitSpace(@Nullable final CharSequence str) {
    return containsOnly(str, LetterDigitSpaceCodePointFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only ASCII characters.
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will
   * return {@code true}.
   * <pre>
   * StringUtils.isAscii(null)   = false
   * StringUtils.isAscii("")     = true
   * StringUtils.isAscii("  ")   = true
   * StringUtils.isAscii("abc")  = true
   * StringUtils.isAscii("ab2c") = true
   * StringUtils.isAscii("ab-c") = true
   * StringUtils.isAscii("中文") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if only contains ASCII characters, and is non-null
   */
  public static boolean isAscii(@Nullable final CharSequence str) {
    return containsOnly(str, AsciiCharFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only ASCII printable characters.
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will return
   * {@code false}.
   *
   * <pre>
   * StringUtils.isAsciiPrintable(null)     = false
   * StringUtils.isAsciiPrintable("")       = true
   * StringUtils.isAsciiPrintable(" ")      = true
   * StringUtils.isAsciiPrintable("Ceki")   = true
   * StringUtils.isAsciiPrintable("ab2c")   = true
   * StringUtils.isAsciiPrintable("!ab-c~") = true
   * StringUtils.isAsciiPrintable("\u0020") = true
   * StringUtils.isAsciiPrintable("\u0021") = true
   * StringUtils.isAsciiPrintable("\u007e") = true
   * StringUtils.isAsciiPrintable("\u007f") = false
   * StringUtils.isAsciiPrintable("Ceki Gülcü") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if every character is in the range 32 through 126. If the
   *     string is null, returns false.
   */
  public static boolean isAsciiPrintable(@Nullable final CharSequence str) {
    return containsOnly(str, AsciiPrintableCharFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only unicode digits. A decimal point is not a
   * unicode digit and returns false.
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will
   * return {@code true}.
   *
   * <pre>
   * StringUtils.isDigit(null)   = false
   * StringUtils.isDigit("")     = true
   * StringUtils.isDigit("  ")   = false
   * StringUtils.isDigit("123")  = true
   * StringUtils.isDigit("12 3") = false
   * StringUtils.isDigit("ab2c") = false
   * StringUtils.isDigit("12-3") = false
   * StringUtils.isDigit("12.3") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if only contains digits, and is non-null. If the
   *     string is null, returns false.
   */
  public static boolean isDigit(@Nullable final CharSequence str) {
    return containsOnly(str, DigitCodePointFilter.INSTANCE);
  }

  /**
   * Checks if the string contains only unicode digits or space ( {@code ' '}).
   * A decimal point is not a unicode digit and returns false.
   *
   * <p>{@code null} will return {@code false}. An empty String ("") will
   * return {@code true}.
   *
   * <pre>
   * StringUtils.isDigitSpace(null)   = false
   * StringUtils.isDigitSpace("")     = true
   * StringUtils.isDigitSpace("  ")   = true
   * StringUtils.isDigitSpace("123")  = true
   * StringUtils.isDigitSpace("12 3") = true
   * StringUtils.isDigitSpace("ab2c") = false
   * StringUtils.isDigitSpace("12-3") = false
   * StringUtils.isDigitSpace("12.3") = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @return {@code true} if only contains digits or space. If the string is
   *     null, returns false.
   */
  public static boolean isDigitSpace(@Nullable final CharSequence str) {
    return containsOnly(str, DigitSpaceCodePointFilter.INSTANCE);
  }

  /**
   * Checks whether the string a valid Java number.
   *
   * <p>Valid numbers include hexadecimal marked with the {@code 0x} qualifier,
   * scientific notation and numbers marked with a type qualifier (e.g. 123L).
   *
   * <pre><code>
   * StringUtils.isNumber(null)   = false
   * StringUtils.isNumber("")     = true
   * StringUtils.isNumber("  ")   = false
   * StringUtils.isNumber("0100")  = true
   * StringUtils.isNumber("0x00") = true
   * StringUtils.isNumber("1234") = true
   * StringUtils.isNumber("1234.123") = true
   * StringUtils.isNumber("+1234.123") = true
   * StringUtils.isNumber("-1234.123") = true
   * StringUtils.isNumber("2e9") = true
   * StringUtils.isNumber("2e-9") = true
   * StringUtils.isNumber("-2e-8") = true
   * StringUtils.isNumber("1a2") = true
   * </code></pre>
   *
   * @param str
   *     the {@code String} to check, may be null.
   * @return {@code true} if the string is a correctly formatted number. If the
   *     string is null or empty, returns false.
   */
  public static boolean isNumber(@Nullable final CharSequence str) {
    if (str == null) {
      return false;
    }
    final int n = str.length();
    if (n == 0) {
      return true;
    }
    int sz = n;
    boolean hasExp = false;
    boolean hasDecPoint = false;
    boolean allowSigns = false;
    boolean foundDigit = false;
    // deal with any possible sign up front
    char ch = str.charAt(0);
    final int start = ((ch == '-') || (ch == '+')) ? 1 : 0;
    if (sz > (start + 1)) {
      ch = str.charAt(start);
      final char next = str.charAt(start + 1);
      if ((ch == '0') && (next == 'x')) {
        int i = start + 2;
        if (i == sz) {
          return false; // str == "0x"
        }
        // checking hex (it can't be anything else)
        for (; i < n; i++) {
          ch = str.charAt(i);
          if (!CharUtils.isAsciiHexDigit(ch)) {
            return false;
          }
        }
        return true;
      }
    }
    --sz; // don't want to loop to the last char, check it afterwords
    // for type qualifiers
    int i = start;
    // loop to the next to last char or to the last char if we need another
    // digit to
    // make a valid number (e.g. chars[0..5] = "1234E")
    while ((i < sz) || (allowSigns && !foundDigit)) {
      ch = str.charAt(i);
      if ((ch >= '0') && (ch <= '9')) {
        foundDigit = true;
        allowSigns = false;
      } else if (ch == '.') {
        if (hasDecPoint || hasExp) {
          // two decimal points or dec in exponent
          return false;
        }
        hasDecPoint = true;
      } else if ((ch == 'e') || (ch == 'E')) {
        // we've already taken care of hex.
        if (hasExp) {
          // two E's
          return false;
        }
        if (!foundDigit) {
          return false;
        }
        hasExp = true;
        allowSigns = true;
      } else if ((ch == '+') || (ch == '-')) {
        if (!allowSigns) {
          return false;
        }
        allowSigns = false;
        foundDigit = false; // we need a digit after the E
      } else {
        return false;
      }
      ++i;
    }
    if (i < n) {
      ch = str.charAt(i);
      if ((ch >= '0') && (ch <= '9')) {
        // no type qualifier, OK
        return true;
      }
      if ((ch == 'e') || (ch == 'E')) {
        // can't have an E at the last byte
        return false;
      }
      if (!allowSigns
          && ((ch == 'd') || (ch == 'D') || (ch == 'f') || (ch == 'F'))) {
        return foundDigit;
      }
      if ((ch == 'l') || (ch == 'L')) {
        // not allowing L with an exponent
        return foundDigit && !hasExp;
      }
      // last character is illegal
      return false;
    }
    // allowSigns is {@code true} iff the val ends in 'E'
    // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
    return !allowSigns && foundDigit;
  }

  /**
   * Tests whether the specified string is quoted by either single quotation
   * marks (') or double quotation marks (").
   *
   * @param str
   *     The string to be tested, which could be {@code null}.
   * @return {@code true} if the string is not {@code null} and is quoted by
   *     either single quotation marks (') or double quotation marks ("); {@code
   *     false} otherwise.
   */
  public static boolean isQuoted(@Nullable final CharSequence str) {
    if (str == null) {
      return false;
    }
    final int len;
    if ((len = str.length()) < 2) {
      return false;
    }
    final char ch1 = str.charAt(0);
    if ((ch1 == Ascii.SINGLE_QUOTE) || (ch1 == Ascii.DOUBLE_QUOTE)) {
      final char ch2 = str.charAt(len - 1);
      return (ch1 == ch2);
    } else {
      return false;
    }
  }

  /**
   * Tests whether the specified string is quoted by the specified left and
   * right quotation marks.
   *
   * @param str
   *     the string to be tested, which could be {@code null}.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return {@code true} if the string is not {@code null} and is quoted by the
   *     left and right quotation marks; {@code false} otherwise.
   */
  public static boolean isQuoted(@Nullable final CharSequence str,
      final char leftQuote, final char rightQuote) {
    if (str == null) {
      return false;
    }
    final int n;
    if ((n = str.length()) < 2) {
      return false;
    }
    final char ch1 = str.charAt(0);
    if (ch1 == leftQuote) {
      final char ch2 = str.charAt(n - 1);
      return (ch2 == rightQuote);
    } else {
      return false;
    }
  }

  /**
   * Compares two Strings, returning {@code true} if they are equal.
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison could be
   * case-sensitive or case-insensitive, controlled by an argument.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.equals(null, null, *)       = true
   * StringUtils.equals(null, "abc", *)      = false
   * StringUtils.equals("abc", null, *)      = false
   * StringUtils.equals("abc", "abc", *)     = true
   * StringUtils.equals("abc", "ABC", true)  = true
   * StringUtils.equals("abc", "ABC", false) = false
   * </pre>
   *
   * @param str1
   *     the first String, may be null.
   * @param str2
   *     the second String, may be null.
   * @return {@code true} if the strings are equal, case sensitive, or both
   *     {@code null}
   * @see String#equals(Object)
   * @see String#equalsIgnoreCase(String)
   */
  public static boolean equals(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2) {
    return Equality.equals(str1, str2);
  }

  /**
   * Compares two Strings, returning {@code true} if they are equal.
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison could be
   * case-sensitive or case-insensitive, controlled by an argument.
   *
   * @param str1
   *     the first String, may be null.
   * @param str2
   *     the second String, may be null.
   * @param ignoreCase
   *     indicate whether to compare two strings ignore the case.
   * @return {@code true} if the strings are equal, case sensitive, or both
   *     {@code null}
   * @see String#equals(Object)
   * @see String#equalsIgnoreCase(String)
   */
  public static boolean equals(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2, final boolean ignoreCase) {
    if (ignoreCase) {
      return Equality.equalsIgnoreCase(str1, str2);
    } else {
      return Equality.equals(str1, str2);
    }
  }

  /**
   * Compares two Strings, returning {@code true} if they are equal.
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison could be
   * case-sensitive or case-insensitive, controlled by an argument.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.equals(null, null, *)       = true
   * StringUtils.equals(null, "abc", *)      = false
   * StringUtils.equals("abc", null, *)      = false
   * StringUtils.equals("abc", "abc", *)     = true
   * StringUtils.equals("abc", "ABC", true)  = true
   * StringUtils.equals("abc", "ABC", false) = false
   * </pre>
   *
   * @param str1
   *     the first String, may be null.
   * @param str2
   *     the second String, may be null.
   * @return {@code true} if the strings are equal, case insensitive, or both
   *     {@code null}
   * @see String#equals(Object)
   * @see String#equalsIgnoreCase(String)
   */
  public static boolean equalsIgnoreCase(@Nullable final CharSequence str1, @Nullable final CharSequence str2) {
    return Equality.equalsIgnoreCase(str1, str2);
  }

  /**
   * Checks whether the character sequence starts with a specified code point.
   *
   * <p>Examples:
   * <pre>
   *   StringUtils.startsWithChar(null, 'h') = false
   *   StringUtils.startsWithChar("", 'h') = false
   *   StringUtils.startsWithChar("while", 'h') = false
   *   StringUtils.startsWithChar("hello", 'h') = true
   *   StringUtils.startsWithChar("h", 'h') = true
   *   StringUtils.startsWithChar("𠀇abc", 0x20007) = true
   * </pre>
   *
   * @param str
   *     a character sequence, which may be null.
   * @param ch
   *     a specified character (Unicode code point).
   * @return
   *     {@code true} if the character sequence starts with the specified code
   *     point; {@code false} otherwise.
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithChar(@Nullable final CharSequence str, final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtStartOf(str);
  }

  /**
   * Tests whether a character sequence is starting with a character accepted
   * by a {@link CharFilter}.
   *
   * @param str
   *     the character sequence to be tested, which may be null.
   * @param filter
   *     the {@link CharFilter} used to test the character, which may be null.
   * @return
   *     {@code true} if the character sequence is starting with a character
   *     accepted by a {@link CharFilter}; {@code false} otherwise. If the
   *     character sequence or filter is null, returns {@code false}.
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtStartOf(str);
  }

  /**
   * Tests whether a character sequence is starting with a code point accepted
   * by a {@link CharFilter}.
   *
   * @param str
   *     the character sequence to be tested, which may be null.
   * @param filter
   *     the {@link CodePointFilter} used to test the code point, which may be
   *     null.
   * @return
   *     {@code true} if the character sequence is starting with a code point
   *     accepted by a {@link CodePointFilter}; {@code false} otherwise. If the
   *     character sequence or filter is null, returns {@code false}.
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtStartOf(str);
  }

  /**
   * Check whether a string starts with a specified prefix.
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison is case-sensitive.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.startsWith(null, null)          = true
   * StringUtils.startsWith(null, "abc")         = false
   * StringUtils.startsWith("", "")              = true
   * StringUtils.startsWith("", *)               = false
   * StringUtils.startsWith("abcdef", null)      = false
   * StringUtils.startsWith("abcdef", "")        = true
   * StringUtils.startsWith("abcdef", "abc")     = true
   * StringUtils.startsWith("abcdef", "abcdef")  = true
   * StringUtils.startsWith("ABCDEF", "abc")     = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param prefix
   *     the prefix to find, may be null.
   * @return {@code true} if the {@code str} starts with the {@code prefix}, in
   *     case sensitive mode, or both {@code null}
   * @see String#startsWith(String)
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWith(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix) {
    return new Searcher()
        .forSubstring(prefix)
        .ignoreCase(false)
        .isAtStartOf(str);
  }

  /**
   * Check if a string starts with a specified prefix.
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison could be
   * case-sensitive or case-insensitive, controlled by an argument.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.startsWithIgnoreCase(null, null)          = true
   * StringUtils.startsWithIgnoreCase(null, "abc")         = false
   * StringUtils.startsWithIgnoreCase("", "")              = true
   * StringUtils.startsWithIgnoreCase("", *)               = false
   * StringUtils.startsWithIgnoreCase("abcdef", null)      = false
   * StringUtils.startsWithIgnoreCase("abcdef", "")        = true
   * StringUtils.startsWithIgnoreCase("abcdef", "abc")     = true
   * StringUtils.startsWithIgnoreCase("abcdef", "abcdef")  = true
   * StringUtils.startsWithIgnoreCase("ABCDEF", "abc")     = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param prefix
   *     the prefix to find, may be null.
   * @return {@code true} if the {@code str} starts with the {@code prefix}, in
   *     case insensitive mode, or both {@code null}
   * @see String#startsWith(String)
   * @see Searcher#isAtStartOf(CharSequence)
   */
  public static boolean startsWithIgnoreCase(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix) {
    return new Searcher()
        .forSubstring(prefix)
        .ignoreCase(true)
        .isAtStartOf(str);
  }

  /**
   * Checks whether the character sequence ends with a specified code point.
   *
   * <p>Examples:
   * <pre>
   *   StringUtils.endsWithChar(null, 'o') = false
   *   StringUtils.endsWithChar("", 'o') = false
   *   StringUtils.endsWithChar("while", 'l') = false
   *   StringUtils.endsWithChar("hello", 'o') = true
   *   StringUtils.endsWithChar("o", 'o') = true
   *   StringUtils.endsWithChar("abc𠀇", 0x20007) = true
   * </pre>
   *
   * @param str
   *     a character sequence, which may be null.
   * @param ch
   *     a specified character (Unicode code point).
   * @return
   *     {@code true} if the character sequence ends with the specified code
   *     point; false otherwise.
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithChar(@Nullable final CharSequence str, final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtEndOf(str);
  }

  /**
   * Tests whether a character sequence is ending with a character accepted
   * by a {@link CharFilter}.
   *
   * @param str
   *     the character sequence to be tested, which may be null.
   * @param filter
   *     the {@link CharFilter} used to test the character, which may be null.
   * @return
   *     {@code true} if the character sequence is ending with a character
   *     accepted by a {@link CharFilter}; {@code false} otherwise. If the
   *     character sequence or filter is null, returns {@code false}.
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtEndOf(str);
  }

  /**
   * Tests whether a character sequence is ending with a code point accepted
   * by a {@link CharFilter}.
   *
   * @param str
   *     the character sequence to be tested, which may be null.
   * @param filter
   *     the {@link CodePointFilter} used to test the code point, which may be
   *     null.
   * @return
   *     {@code true} if the character sequence is ending with a code point
   *     accepted by a {@link CodePointFilter}; {@code false} otherwise. If the
   *     character sequence or filter is null, returns {@code false}.
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtEndOf(str);
  }

  /**
   * Check if a string ends with a specified suffix (optionally case
   * insensitive).
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison could be
   * case-sensitive or case-insensitive, controlled by an argument.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.endsWith(null, null)          = true
   * StringUtils.endsWith("abcdef", null)      = false
   * StringUtils.endsWith(null, "def")         = false
   * StringUtils.endsWith("", "")              = true
   * StringUtils.endsWith(*, "")               = true
   * StringUtils.endsWith("", "abc")           = false
   * StringUtils.endsWith("abcdef", "def")     = true
   * StringUtils.endsWith("abcdef", "abcdef")  = true
   * StringUtils.endsWith("ABCDEF", "def")     = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param suffix
   *     the suffix to find, may be null
   * @return {@code true} if the string starts with the prefix, in case
   *     sensitive mode, or both {@code null}
   * @see String#endsWith(String)
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWith(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix) {
    return new Searcher()
        .forSubstring(suffix)
        .ignoreCase(false)
        .isAtEndOf(str);
  }

  /**
   * Check if a string ends with a specified suffix (optionally case
   * insensitive).
   *
   * <p>{@code null}s are handled without exceptions. Two {@code null}
   * references are considered to be equal. The comparison could be
   * case-sensitive or case-insensitive, controlled by an argument.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.endsWithIgnoreCase(null, null)          = true
   * StringUtils.endsWithIgnoreCase("abcdef", null)      = false
   * StringUtils.endsWithIgnoreCase(null, "def")         = false
   * StringUtils.endsWithIgnoreCase("", "")              = true
   * StringUtils.endsWithIgnoreCase(*, "")               = true
   * StringUtils.endsWithIgnoreCase("", "abc")           = false
   * StringUtils.endsWithIgnoreCase("abcdef", "")        = true
   * StringUtils.endsWithIgnoreCase("abcdef", "def")     = true
   * StringUtils.endsWithIgnoreCase("abcdef", "abcdef")  = true
   * StringUtils.endsWithIgnoreCase("ABCDEF", "def")     = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param suffix
   *     the suffix to find, may be null
   * @return {@code true} if the string starts with the prefix, in case
   *     insensitive mode, or both {@code null}
   * @see String#endsWith(String)
   * @see Searcher#isAtEndOf(CharSequence)
   */
  public static boolean endsWithIgnoreCase(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix) {
    return new Searcher()
        .forSubstring(suffix)
        .ignoreCase(true)
        .isAtEndOf(str);
  }

  /**
   * Tests whether a string is starting or ending with a specified code point.
   *
   * @param str
   *     the string to be tested, which may be null.
   * @param ch
   *     the specified character (Unicode code point).
   * @return {@code true} if the string is starting or ending with a code point
   *     satisfying the specified {@link CharFilter}; {@code false} otherwise.
   *     If the string is null or empty, returns false.
   * @see Searcher#isAtStartOrEndOf(CharSequence)
   */
  public static boolean startsOrEndsWithChar(@Nullable final CharSequence str,
      final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtStartOrEndOf(str);
  }

  /**
   * Tests whether a string is starting or ending with a character satisfying
   * the specified {@link CharFilter}.
   *
   * @param str
   *     the string to be tested, which may be null.
   * @param filter
   *     the specified {@link CharFilter}.
   * @return {@code true} if the string is starting or ending with a character
   *     satisfying the specified {@link CharFilter}; {@code false} otherwise.
   *     If the string is null or empty, or if the filter is null, returns
   *     false.
   * @see Searcher#isAtStartOrEndOf(CharSequence)
   */
  public static boolean startsOrEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtStartOrEndOf(str);
  }

  /**
   * Tests whether a string is starting or ending with a code point satisfying
   * the specified {@link CodePointFilter}.
   *
   * @param str
   *     the string to be tested, which may be null.
   * @param filter
   *     the specified {@link CodePointFilter}.
   * @return {@code true} if the string is starting or ending with a code point
   *     satisfying the specified {@link CodePointFilter}; {@code false} otherwise.
   *     If the string is null or empty, or if the filter is null, returns
   *     false.
   * @see Searcher#isAtStartOrEndOf(CharSequence)
   */
  public static boolean startsOrEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtStartOrEndOf(str);
  }

  /**
   * Tests whether a string is starting and ending with a specified code point.
   *
   * @param str
   *     the string to be tested, which may be null.
   * @param ch
   *     the specified character (Unicode code point).
   * @return {@code true} if the string is starting and ending with a code point
   *     satisfying the specified {@link CharFilter}; {@code false} otherwise.
   *     If the string is null or empty, returns false.
   * @see Searcher#isAtStartAndEndOf(CharSequence)
   */
  public static boolean startsAndEndsWithChar(@Nullable final CharSequence str,
      final int ch) {
    return new Searcher()
        .forCodePoint(ch)
        .isAtStartAndEndOf(str);
  }

  /**
   * Tests whether a string is starting and ending with a character satisfying
   * the specified {@link CharFilter}.
   *
   * @param str
   *     the string to be tested, which may be null.
   * @param filter
   *     the specified {@link CharFilter}.
   * @return {@code true} if the string is starting and ending with a character
   *     satisfying the specified {@link CharFilter}; {@code false} otherwise.
   *     If the string is null or empty, or if the filter is null, returns
   *     false.
   * @see Searcher#isAtStartAndEndOf(CharSequence)
   */
  public static boolean startsAndEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isAtStartAndEndOf(str);
  }

  /**
   * Tests whether a string is starting and ending with a code point satisfying
   * the specified {@link CodePointFilter}.
   *
   * @param str
   *     the string to be tested, which may be null.
   * @param filter
   *     the specified {@link CodePointFilter}.
   * @return {@code true} if the string is starting and ending with a code point
   *     satisfying the specified {@link CodePointFilter}; {@code false} otherwise.
   *     If the string is null or empty, or if the filter is null, returns
   *     false.
   * @see Searcher#isAtStartAndEndOf(CharSequence)
   */
  public static boolean startsAndEndsWithChar(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isAtStartAndEndOf(str);
  }

  /**
   * Compares two strings, and returns the index at which the strings begin to
   * differ.
   *
   * <p>For example,
   * <pre>
   * StringUtils.indexOfDifference(null, null) = -1
   * StringUtils.indexOfDifference("", "") = -1
   * StringUtils.indexOfDifference("", "abc") = 0
   * StringUtils.indexOfDifference("abc", "") = 0
   * StringUtils.indexOfDifference("abc", "abc") = -1
   * StringUtils.indexOfDifference("ab", "abxyz") = 2
   * StringUtils.indexOfDifference("abcde", "abxyz") = 2
   * StringUtils.indexOfDifference("abcde", "xyz") = 0
   * StringUtils.indexOfDifference("i am a machine", "i am a robot")  = 7
   * </pre>
   *
   * @param str1
   *     the first String, may be null
   * @param str2
   *     the second String, may be null
   * @return the index where str2 and str1 begin to differ; -1 if they are
   *     equal.
   */
  public static int indexOfDifference(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2) {
    if (str1 == null) {
      return (str2 == null ? -1 : 0);
    }
    if (str2 == null) {
      return 0;
    }
    int i = 0;
    for (; (i < str1.length()) && (i < str2.length()); ++i) {
      if (str1.charAt(i) != str2.charAt(i)) {
        break;
      }
    }
    if ((i < str2.length()) || (i < str1.length())) {
      return i;
    }
    return -1;
  }

  /**
   * Compares all strings in an array and returns the index at which the strings
   * begin to differ.
   *
   * <p>For example,
   * <pre>
   * StringUtils.indexOfDifference(null) = -1
   * StringUtils.indexOfDifference(new String[] {}) = -1
   * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
   * StringUtils.indexOfDifference(new String[] {null, null}) = -1
   * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
   * StringUtils.indexOfDifference(new String[] {"", null}) = 0
   * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
   * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
   * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
   * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
   * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
   * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
   * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
   * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
   * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
   * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
   * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
   * </pre>
   *
   * @param strs
   *     array of strings, entries may be null
   * @return the index where the strings begin to differ; -1 if they are all
   *     equal
   */
  public static int indexOfDifference(@Nullable final CharSequence... strs) {
    if ((strs == null) || (strs.length <= 1)) {
      return -1;
    }
    boolean anyStringNull = false;
    boolean allStringsNull = true;
    final int arrayLen = strs.length;
    int shortestStrLen = Integer.MAX_VALUE;
    int longestStrLen = 0;
    // find the min and max string lengths; this avoids checking to make
    // sure we are not exceeding the length of the string each time through
    // the bottom loop.
    for (final CharSequence str : strs) {
      if (str == null) {
        anyStringNull = true;
        shortestStrLen = 0;
      } else {
        allStringsNull = false;
        shortestStrLen = Math.min(str.length(), shortestStrLen);
        longestStrLen = Math.max(str.length(), longestStrLen);
      }
    }
    // handle lists containing all nulls or all empty strings
    if (allStringsNull || ((longestStrLen == 0) && !anyStringNull)) {
      return -1;
    }
    // handle lists containing some nulls or some empty strings
    if (shortestStrLen == 0) {
      return 0;
    }
    // find the position with the first difference across all strings
    int firstDiff = -1;
    for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
      final char comparisonChar = strs[0].charAt(stringPos);
      for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
        if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
          firstDiff = stringPos;
          break;
        }
      }
      if (firstDiff != -1) {
        break;
      }
    }
    if ((firstDiff == -1) && (shortestStrLen != longestStrLen)) {
      // we compared all of the characters up to the length of the
      // shortest string and didn't find a match, but the string lengths
      // vary, so return the length of the shortest string.
      return shortestStrLen;
    }
    return firstDiff;
  }

  /**
   * Compares two Strings, and returns the portion where they differ. (More
   * precisely, return the remainder of the second String, starting from where
   * it's different from the first.)
   *
   * <p>For example,
   * <pre>
   * StringUtils.difference(null, null) = null
   * StringUtils.difference("", "") = ""
   * StringUtils.difference("", "abc") = "abc"
   * StringUtils.difference("abc", "") = ""
   * StringUtils.difference("abc", "abc") = ""
   * StringUtils.difference("ab", "abxyz") = "xyz"
   * StringUtils.difference("abcde", "abxyz") = "xyz"
   * StringUtils.difference("abcde", "xyz") = "xyz"
   * StringUtils.difference("i am a machine", "i am a robot") = "robot"
   * </pre>
   *
   * @param str1
   *     the first String, may be null
   * @param str2
   *     the second String, may be null
   * @return the portion of str2 where it differs from str1; returns the empty
   *     String if they are equal
   */
  public static String getDifference(@Nullable final CharSequence str1,
      @Nullable final CharSequence str2) {
    if (str1 == null) {
      return (str2 == null ? null : str2.toString());
    }
    if (str2 == null) {
      return str1.toString();
    }
    final int at = indexOfDifference(str1, str2);
    if (at == -1) {
      return EMPTY;
    }
    return str2.subSequence(at, str2.length()).toString();
  }

  /**
   * Compares all Strings in an array and returns the initial sequence of
   * characters that is common to all of them.
   *
   * <p>For example,
   * <pre>
   * StringUtils.getCommonPrefix(null) = ""
   * StringUtils.getCommonPrefix(new String[] {}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
   * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
   * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
   * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
   * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
   * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
   * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
   * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
   * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
   * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
   * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
   * </pre>
   *
   * @param strs
   *     array of String objects, entries may be null
   * @return the initial sequence of characters that are common to all Strings
   *     in the array; empty String if the array is null, the elements are all
   *     null or if there is no common prefix.
   */
  public static String getCommonPrefix(@Nullable final CharSequence... strs) {
    if ((strs == null) || (strs.length == 0)) {
      return EMPTY;
    }
    final int smallestIndexOfDiff = indexOfDifference(strs);
    if (smallestIndexOfDiff == -1) {
      // all strings were identical
      if (strs[0] == null) {
        return EMPTY;
      }
      return strs[0].toString();
    } else if (smallestIndexOfDiff == 0) {
      // there were no common initial characters
      return EMPTY;
    } else {
      // we found a common initial character sequence
      return strs[0].subSequence(0, smallestIndexOfDiff).toString();
    }
  }

  /**
   * Find the Levenshtein distance between two Strings.
   *
   * <p>This is the number of changes needed to change one String into another,
   * where each change is a single character modification (deletion, insertion
   * or substitution).
   *
   * <p>The previous implementation of the Levenshtein distance algorithm was
   * from
   * <a href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a>
   *
   * <p>Chas Emerick has written an implementation in Java, which avoids an
   * OutOfMemoryError which can occur when my Java implementation is used with
   * very large strings.<br> This implementation of the Levenshtein distance
   * algorithm is from
   * <a href="http://www.merriampark.com/ldjava.htm">http://www.merriampark.com/ldjava.htm</a>.
   * <pre>
   * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
   * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
   * StringUtils.getLevenshteinDistance("","")               = 0
   * StringUtils.getLevenshteinDistance("","a")              = 1
   * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
   * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
   * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
   * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
   * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
   * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
   * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
   * </pre>
   *
   * @param str1
   *     the first String, must not be null
   * @param str2
   *     the second String, must not be null
   * @return result distance
   * @throws IllegalArgumentException
   *     if either String input {@code null}
   */
  public static int getLevenshteinDistance(final CharSequence str1,
      final CharSequence str2) {
    if ((str1 == null) || (str2 == null)) {
      throw new IllegalArgumentException("Strings must not be null");
    }
    /*
     * The difference between this impl. and the previous is that, rather than
     * creating and retaining a matrix of size s.length()+1 by t.length()+1, we
     * maintain two single-dimensional arrays of length s.length()+1. The first,
     * d, is the 'current working' distance array that maintains the newest
     * distance cost counts as we iterate through the characters of String s.
     * Each time we increment the index of String t we are comparing, d is
     * copied to p, the second int[]. Doing so allows us to retain the previous
     * cost counts as required by the algorithm (taking the minimum of the cost
     * count to the left, up one, and diagonally up and to the left of the
     * current cost count being calculated). (Note that the arrays aren't really
     * copied anymore, just switched...this is clearly better than cloning an array or
     * doing a System.arraycopy() each time through the outer loop.)
     * Effectively, the difference between the two implementations is this one
     * does not cause an out of memory condition when calculating the LD over
     * two very large strings.
     */
    int n = str1.length(); // length of str1
    int m = str2.length(); // length of str2
    if (n == 0) {
      return m;
    } else if (m == 0) {
      return n;
    }
    CharSequence s = str1;
    CharSequence t = str2;
    if (n > m) {
      // swap the input strings to consume less memory
      final CharSequence tmp = s;
      s = t;
      t = tmp;
      n = m;
      m = t.length();
    }
    int[] p = new int[n + 1]; // 'previous' cost array, horizontally
    int[] d = new int[n + 1]; // cost array, horizontally
    int[] tmp; // placeholder to assist in swapping p and d
    // indexes into strings s and t
    int i; // iterates through s
    int j; // iterates through t
    char jth; // jth character of t
    int cost; // cost
    for (i = 0; i <= n; i++) {
      p[i] = i;
    }
    for (j = 1; j <= m; j++) {
      jth = t.charAt(j - 1);
      d[0] = j;
      for (i = 1; i <= n; i++) {
        cost = s.charAt(i - 1) == jth ? 0 : 1;
        // minimum of cell to the left+1, to the top+1, diagonally left and up
        // +cost
        d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
      }
      // copy current distance counts to 'previous row' distance counts
      tmp = p;
      p = d;
      d = tmp;
    }
    // our last action in the above loop was to switch d and p, so p now
    // actually has the most recent cost counts
    return p[n];
  }

  /**
   * Finds the first occurrence of a character within a string.
   *
   * <p>A {@code null} or empty ("") string will return {@code -1}. A negative
   * start position is treated as zero. A start position greater than the string
   * length returns {@code -1}.
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param ch
   *     the character to find.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the first occurrence of the search character, -1 if no match or
   *     {@code null} string input
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      final char ch, final int startIndex) {
    return new Searcher()
        .forChar(ch)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Finds the first occurrence of a character within a string.
   *
   * <p>A {@code null} or empty ("") string will return {@code -1}. A negative
   * start position is treated as zero. A start position greater than the string
   * length returns {@code -1}.
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param ch
   *     the character to find.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero.
   * @param endIndex
   *     the position to end the search.
   * @return the first occurrence of the search character, -1 if no match or
   *     {@code null} string input.
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      final char ch, final int startIndex, final int endIndex) {
    return new Searcher()
        .forChar(ch)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character accepted by
   * the given filter.
   *
   * <p>A {@code null} string or a {@code null} filter will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null
   * @param filter
   *     a {@link CharFilter} specifies the condition of character to be
   *     searched.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the first occurrence of the character accepted by the {@code filter}
   *     in the string {@code str} starting from the {@code startIndex}; or -1
   *     if no such character.
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int startIndex) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character accepted by
   * the given filter.
   *
   * <p>A {@code null} string or a {@code null} filter will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null.
   * @param filter
   *     a {@link CodePointFilter} specifies the condition of characters to be
   *     searched.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero.
   * @param endIndex
   *     the position to end the search. If it is greater than the length of
   *     {@code str}, it has the same effect as the length of {@code str}.
   * @return
   *     the index of the last character accepted by the {@code filter} in the
   *     string {@code str} starting from the {@code startIndex} and ending
   *     before the {@code endIndex}; or -1 if no such character. If {@code str}
   *     is {@code null} or empty, -1 is returned. If {@code [startIndex, endIndex)}
   *     is not a valid range of the string {@code str}, -1 is returned. If
   *     {@code filter} is {@code null}, -1 is returned.
   * @see Searcher
   */
  public static int indexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int startIndex,
      final int endIndex) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Finds the first occurrence of a Unicode code point within a string.
   *
   * <p>A {@code null} or empty ("") string will return {@code -1}. A negative
   * start position is treated as zero. A start position greater than the string
   * length returns {@code -1}.
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param codePoint
   *     the Unicode code point to find.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the first occurrence of the search Unicode code point, or -1 if no
   *     match or {@code null} string input
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      final int codePoint, final int startIndex) {
    return new Searcher()
        .forCodePoint(codePoint)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Finds the first occurrence of a Unicode code point within a string.
   *
   * <p>A {@code null} or empty ("") string will return {@code -1}. A negative
   * start position is treated as zero. A start position greater than the string
   * length returns {@code -1}.
   * <pre>
   * StringUtils.indexOfChar(null, *, *)          = -1
   * StringUtils.indexOfChar("", *, *)            = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', 0)  = 2
   * StringUtils.indexOfChar("aabaabaa", 'b', 3)  = 5
   * StringUtils.indexOfChar("aabaabaa", 'b', 9)  = -1
   * StringUtils.indexOfChar("aabaabaa", 'b', -1) = 2
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param codePoint
   *     the Unicode code point to find.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero.
   * @param endIndex
   *     the position to end the search.
   * @return the first occurrence of the search Unicode code point, or -1 if no
   *     match or {@code null} string input.
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      final int codePoint, final int startIndex, final int endIndex) {
    return new Searcher()
        .forCodePoint(codePoint)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any Unicode code point
   * accepted by the given filter.
   *
   * <p>A {@code null} string or a {@code null} filter will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null.
   * @param filter
   *     a {@link CodePointFilter} specifies the condition of characters to be
   *     searched.
   * @return
   *     the index of the first Unicode code point accepted by the {@code filter}
   *     in the string {@code str}; or -1 if no such character. If {@code str}
   *     is {@code null} or empty, -1 is returned. If {@code filter} is
   *     {@code null}, -1 is returned.
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any Unicode code point
   * accepted by the given filter.
   *
   * <p>A {@code null} string or a {@code null} filter will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null.
   * @param filter
   *     a {@link CodePointFilter} specifies the condition of characters to be
   *     searched.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero.
   * @return
   *     the index of the first Unicode code point accepted by the {@code filter}
   *     in the string {@code str} starting from the {@code startIndex}; or -1
   *     if no such character. If {@code str} is {@code null} or empty, -1 is
   *     returned. If {@code [startIndex, str.length())} is not a valid range of
   *     the string {@code str}, -1 is returned. If {@code filter} is
   *     {@code null}, -1 is returned.
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final int startIndex) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any Unicode code point
   * accepted by the given filter.
   *
   * <p>A {@code null} string or a {@code null} filter will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null.
   * @param filter
   *     a {@link CodePointFilter} specifies the condition of characters to be
   *     searched.
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero.
   * @param endIndex
   *     the position to end the search. If it is greater than the length of
   *     {@code str}, it has the same effect as the length of {@code str}.
   * @return
   *     the index of the first Unicode code point accepted by the {@code filter}
   *     in the string {@code str} starting from the {@code startIndex} and ending
   *     before the {@code endIndex}; or -1 if no such character. If {@code str}
   *     is {@code null} or empty, -1 is returned. If {@code [startIndex, endIndex)}
   *     is not a valid range of the string {@code str}, -1 is returned. If
   *     {@code filter} is {@code null}, -1 is returned.
   * @see Searcher
   */
  public static int indexOfCodePoint(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final int startIndex,
      final int endIndex) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .startFrom(startIndex)
        .endBefore(endIndex)
        .findFirstIndexIn(str);
  }


  /**
   * Search a string to find the first occurrence of any character in the given
   * set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} or zero
   * length search array will return {@code -1}.
   * <pre>
   * StringUtils.indexOfAnyChar(null, *, *)                 = -1
   * StringUtils.indexOfAnyChar("", *, *)                   = -1
   * StringUtils.indexOfAnyChar(*, null, *)                 = -1
   * StringUtils.indexOfAnyChar(*, [], *)                   = -1
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 0) = 0
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 1) = 1
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 2) = 2
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['z','a'], 5) = 10
   * StringUtils.indexOfAnyChar("zzabyycdxxz",['b','y'], 1) = 3
   * StringUtils.indexOfAnyChar("aba", ['z'], *)            = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param chars
   *     the chars to search for, may be null
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int indexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final char[] chars, final int startIndex) {
    return new Searcher()
        .forCharsIn(chars)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character in the given
   * set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} search
   * string will return {@code -1}.
   * <pre>
   * StringUtils.indexOfAnyChar(null, *, *)                 = -1
   * StringUtils.indexOfAnyChar("", *, *)                   = -1
   * StringUtils.indexOfAnyChar(*, null, *)                 = -1
   * StringUtils.indexOfAnyChar(*, "", *)                   = -1
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 0)     = 0
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 1)     = 1
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 2)     = 2
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "za", 5)     = 10
   * StringUtils.indexOfAnyChar("zzabyycdxxz", "by", 1)     = 3
   * StringUtils.indexOfAnyChar("aba", "z", *)              = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param chars
   *     the chars to search for, may be null
   * @param startIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int indexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final CharSequence chars, final int startIndex) {
    return new Searcher()
        .forCharsIn(chars)
        .startFrom(startIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character not in the
   * given set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} or zero
   * length search array will return {@code -1}.
   * <pre>
   * StringUtils.indexOfAnyCharBut(null, *, *)                 = -1
   * StringUtils.indexOfAnyCharBut("", *, *)                   = -1
   * StringUtils.indexOfAnyCharBut("abc", null, 0)             = 0
   * StringUtils.indexOfAnyCharBut("abc", null, 1)             = 1
   * StringUtils.indexOfAnyCharBut("abc", null, 100)           = -1
   * StringUtils.indexOfAnyCharBut("abc", [], 0)               = 0
   * StringUtils.indexOfAnyCharBut("abc", [], 1)               = 1
   * StringUtils.indexOfAnyCharBut("abc", [], 100)             = -1
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", ['z','a'], 0) = 3
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", ['a'], 0)     = 0
   * StringUtils.indexOfAnyCharBut("aba", ['a', 'b'], *)       = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searchChars
   *     the chars to search for, may be null
   * @param fromIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int indexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final char[] searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .startFrom(fromIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character not in the
   * given set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} search
   * string will return {@code -1}.
   * <pre>
   * StringUtils.indexOfAnyCharBut(null, *, *)            = -1
   * StringUtils.indexOfAnyCharBut("", *, *)              = -1
   * StringUtils.indexOfAnyCharBut("abc", null, 0)        = 0
   * StringUtils.indexOfAnyCharBut("abc", null, 1)        = 1
   * StringUtils.indexOfAnyCharBut("abc", null, 100)      = -1
   * StringUtils.indexOfAnyCharBut("abc", "", 0)          = 0
   * StringUtils.indexOfAnyCharBut("abc", "", 1)          = 1
   * StringUtils.indexOfAnyCharBut("abc", "", 100)        = -1
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", "za", 0) = 3
   * StringUtils.indexOfAnyCharBut("zzabyycdxx", "", 0)   = 0
   * StringUtils.indexOfAnyCharBut("aba","ab", *)         = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searchChars
   *     the chars to search for, may be null
   * @param fromIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int indexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .startFrom(fromIndex)
        .findFirstIndexIn(str);
  }

  /**
   * Finds the first occurrence of a substring within a string, handling {@code
   * null}. This method uses {@link String#indexOf(String, int)}.
   *
   * <p>A {@code null} string will return {@code -1}. A negative start position
   * is treated as zero. An empty ("") search String always matches. A start
   * position greater than the string length only matches an empty search
   * String.
   * <pre>
   * StringUtils.indexOf(null, *, *, *)          = -1
   * StringUtils.indexOf(*, null, *, *)          = -1
   * StringUtils.indexOf("", "", 0, *)           = -1
   * StringUtils.indexOf("aabaabaa", "a", 0, *)  = 0
   * StringUtils.indexOf("aabaabaa", "A", 0, true)  = 0
   * StringUtils.indexOf("aabaabaa", "A", 0, false)  = -1
   * StringUtils.indexOf("aabaabaa", "b", 0, *)  = 2
   * StringUtils.indexOf("aabaabaa", "ab", 0, *) = 1
   * StringUtils.indexOf("aabaabaa", "aB", 0, true) =  1
   * StringUtils.indexOf("aabaabaa", "aB", 0, false) = -1
   * StringUtils.indexOf("aabaabaa", "b", 3, *)  = 5
   * StringUtils.indexOf("aabaabaa", "b", 9, *)  = -1
   * StringUtils.indexOf("aabaabaa", "b", -1, *) = 2
   * StringUtils.indexOf("aabaabaa", "", 2, *)   = 2
   * StringUtils.indexOf("abc", "", 9, *)        = 3
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param search
   *     the string to find, may be null
   * @param fromIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @return the first occurrence of the search String, -1 if no match or {@code
   *     null} string input
   * @see Searcher
   */
  public static int indexOf(@Nullable final CharSequence str,
      @Nullable final CharSequence search, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstring(search)
        .startFrom(fromIndex)
        .ignoreCase(ignoreCase)
        .findFirstIndexIn(str);
  }

  /**
   * Find the first occurrence of any of a set of potential substrings.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} or zero
   * length search array will return {@code -1}. A {@code null} search array
   * entry will be ignored, but a search array containing "" will return {@code
   * 0} if {@code str} is not {@code null}. This method uses {@link
   * String#indexOf(String)}.
   * <pre>
   * StringUtils.indexOfAny(null, *, *, *)                     = -1
   * StringUtils.indexOfAny(*, null, *, *)                     = -1
   * StringUtils.indexOfAny(*, [], *, *)                       = -1
   * StringUtils.indexOfAny("zzabyycdxx", ["ab","cd"], 0, *)   = 2
   * StringUtils.indexOfAny("zzabyycdxx", ["Ab","cd"], 0, true)  = 2
   * StringUtils.indexOfAny("zzabyycdxx", ["Ab","cd"], 0, false) = 6
   * StringUtils.indexOfAny("zzabyycdxx", ["cd","ab"], 0, *)   = 2
   * StringUtils.indexOfAny("zzabyycdxx", ["mn","op"], *, *)   = -1
   * StringUtils.indexOfAny("zzabyycdxx", ["zab","aby"], 0, *) = 1
   * StringUtils.indexOfAny("zzabyycdxx", [""], 0, *)          = 0
   * StringUtils.indexOfAny("", [""], 0, *)                    = -1
   * StringUtils.indexOfAny("", ["a"], *, *)                   = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searches
   *     the strings to search for, may be null
   * @param fromIndex
   *     the position to start the search from. If it is negative, it has the
   *     same effect as if it were zero: this entire string may be searched. If
   *     it is greater than the length of the string, it has the same effect as
   *     if it were equal to the length of the string: and -1 is returned.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @return the first occurrence of any of the searchStrs in str, -1 if no
   *     match
   * @see Searcher
   */
  public static int indexOfAny(@Nullable final CharSequence str,
      @Nullable final String[] searches, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstringsIn(searches)
        .startFrom(fromIndex)
        .ignoreCase(ignoreCase)
        .findFirstIndexIn(str);
  }

  /**
   * Finds the last current within a string from a start position, handling
   * {@code null}. This method uses {@link String#lastIndexOf(int, int)}.
   *
   * <p>A {@code null} or empty ("") String will return {@code -1}. A negative
   * start position returns {@code -1}. A start position greater than the string
   * length searches the whole string.
   * <pre>
   * StringUtils.lastIndexOfChar(null, *, *)          = -1
   * StringUtils.lastIndexOfChar("", *,  *)           = -1
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 8)  = 5
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 4)  = 2
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 0)  = -1
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', 9)  = 5
   * StringUtils.lastIndexOfChar("aabaabaa", 'b', -1) = -1
   * StringUtils.lastIndexOfChar("aabaabaa", 'a', 0)  = 0
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param ch
   *     the character to find
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @return the last current of the search character, -1 if no match or {@code
   *     null} string input.
   * @see Searcher
   */
  public static int lastIndexOfChar(@Nullable final CharSequence str,
      final char ch, final int fromIndex) {
    return new Searcher()
        .forChar(ch)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character accepted by
   * the given filter.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} filter
   * will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null
   * @param filter
   *     a {@link CharFilter} specifies the condition of characters to be
   *     searched.
   * @return the first occurrence of the character accepted by the {@code
   *     filter} in the string {@code str} starting from the {@code startIndex}.
   * @see Searcher
   */
  public static int lastIndexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .findLastIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character accepted by
   * the given filter.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} filter
   * will return {@code -1}.
   *
   * @param str
   *     the string to check, may be null
   * @param filter
   *     a {@link CharFilter} specifies the condition of characters to be search.
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @return the first occurrence of the character accepted by the {@code
   *     filter} in the string {@code str} starting from the {@code fromIndex}.
   * @see Searcher
   */
  public static int lastIndexOfChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int fromIndex) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character in the given
   * set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} or zero
   * length search array will return {@code -1}.
   * <pre>
   * StringUtils.lastIndexOfAnyChar(null, *, *)                 = -1
   * StringUtils.lastIndexOfAnyChar("", *, *)                   = -1
   * StringUtils.lastIndexOfAnyChar(*, null, *)                 = -1
   * StringUtils.lastIndexOfAnyChar(*, [], *)                   = -1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 0) = 0
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 1) = 1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 2) = 2
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['z','a'], 5) = 10
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz",['b','y'], 1) = 3
   * StringUtils.lastIndexOfAnyChar("aba", ['z'], *)            = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searchChars
   *     the chars to search for, may be null
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int lastIndexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final char[] searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character in the given
   * set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} search
   * string will return {@code -1}.
   * <pre>
   * StringUtils.lastIndexOfAnyChar(null, *, *)                 = -1
   * StringUtils.lastIndexOfAnyChar("", *, *)                   = -1
   * StringUtils.lastIndexOfAnyChar(*, null, *)                 = -1
   * StringUtils.lastIndexOfAnyChar(*, "", *)                   = -1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 0)     = 0
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 1)     = 1
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 2)     = 2
   * StringUtils.lastIndexOfAnyChar("zzabyycdxxz", "za", 5)     = 10
   * StringUtils.lastIndexOfAnyChar("zzabyycdxx", "by", 1)     = 3
   * StringUtils.lastIndexOfAnyChar("aba", "z", *)              = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searchChars
   *     the chars to search for, may be null
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int lastIndexOfAnyChar(@Nullable final CharSequence str,
      @Nullable final String searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character not in the
   * given set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} or zero
   * length search array will return {@code -1}.
   * <pre>
   * StringUtils.lastIndexOfAnyCharBut(null, *, *)                 = -1
   * StringUtils.lastIndexOfAnyCharBut("", *, *)                   = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 0)             = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 1)             = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 100)           = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", [], 0)               = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", [], 1)               = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", [], 100)             = -1
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", ['z','a'], 0) = 3
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", ['a'], 0)     = 0
   * StringUtils.lastIndexOfAnyCharBut("aba", ['a', 'b'], *)       = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searchChars
   *     the chars to search for, may be null
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int lastIndexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final char[] searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * Search a string to find the first occurrence of any character not in the
   * given set of characters.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} search
   * string will return {@code -1}.
   * <pre>
   * StringUtils.lastIndexOfAnyCharBut(null, *, *)            = -1
   * StringUtils.lastIndexOfAnyCharBut("", *, *)              = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 0)        = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 1)        = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", null, 100)      = -1
   * StringUtils.lastIndexOfAnyCharBut("abc", "", 0)          = 0
   * StringUtils.lastIndexOfAnyCharBut("abc", "", 1)          = 1
   * StringUtils.lastIndexOfAnyCharBut("abc", "", 100)        = -1
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "za", 0) = 3
   * StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "", 0)   = 0
   * StringUtils.lastIndexOfAnyCharBut("aba","ab", *)         = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searchChars
   *     the chars to search for, may be null
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @return the index of any of the chars, -1 if no match or null input
   * @see Searcher
   */
  public static int lastIndexOfAnyCharBut(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, final int fromIndex) {
    return new Searcher()
        .forCharsNotIn(searchChars)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .findLastIndexIn(str);
  }

  /**
   * Finds the first occurrence within a string, handling {@code null}. This
   * method uses {@link String#lastIndexOf(String, int)}.
   *
   * <p>A {@code null} string will return {@code -1}. A negative start position
   * returns {@code -1}. An empty ("") search String always matches unless the
   * start position is negative. A start position greater than the string length
   * searches the whole string.
   * <pre>
   * StringUtils.lastIndexOf(null, *, *)          = -1
   * StringUtils.lastIndexOf(*, null, *)          = -1
   * StringUtils.lastIndexOf("", *, *)            = -1
   * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
   * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
   * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
   * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
   * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
   * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
   * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param search
   *     the string to find, may be null
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @return the first occurrence of the search String, -1 if no match or {@code
   *     null} string input
   * @see Searcher
   */
  public static int lastIndexOf(@Nullable final CharSequence str,
      @Nullable final CharSequence search, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstring(search)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .ignoreCase(ignoreCase)
        .findLastIndexIn(str);
  }

  /**
   * Find the latest current of any of a set of potential substrings.
   *
   * <p>A {@code null} string will return {@code -1}. A {@code null} search
   * array will return {@code -1}. A {@code null} or zero length search array
   * entry will be ignored, but a search array containing "" will return the
   * length of {@code str} if {@code str} is not {@code null}. This method uses
   * {@link String#indexOf(String)}
   * <pre>
   * StringUtils.lastIndexOfAny(null, *, *)                     = -1
   * StringUtils.lastIndexOfAny("", *, *)                       =  -1
   * StringUtils.lastIndexOfAny(*, null, *)                     = -1
   * StringUtils.lastIndexOfAny(*, [], *)                       = -1
   * StringUtils.lastIndexOfAny(*, [null], *)                   = -1
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab","cd"], 100) = 6
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd","ab"], 100) = 6
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"], 100) = -1
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"], 100) = -1
   * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn",""], 100)   = 10
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param searches
   *     the strings to search for, may be null
   * @param fromIndex
   *     the position to start the search from (searching backwards). There is
   *     no restriction on the value of fromIndex. If it is greater than or equal
   *     to the length of this string, it has the same effect as if it were equal
   *     to one less than the length of this string: this entire string may be
   *     searched. If it is negative, it has the same effect as if it were -1:
   *     -1 is returned.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @return the last current of any of the strings, -1 if no match
   * @see Searcher
   */
  public static int lastIndexOfAny(@Nullable final String str,
      @Nullable final CharSequence[] searches, final int fromIndex,
      final boolean ignoreCase) {
    return new Searcher()
        .forSubstringsIn(searches)
        .endBefore(Math.min(Integer.MAX_VALUE - 1, fromIndex) + 1)
        .ignoreCase(ignoreCase)
        .findLastIndexIn(str);
  }

  /**
   * Tests whether a string contains a character.
   *
   * @param str
   *     a string, may be {@code null}.
   * @param ch
   *     the character.
   * @return {@code true} if the {@code str} is not {@code null} and contains
   *     {@code ch}; {@code false} otherwise.
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsChar(@Nullable final CharSequence str, final char ch) {
    return new Searcher()
        .forChar(ch)
        .isContainedIn(str);
  }

  /**
   * Tests whether a string contains a character.
   *
   * @param str
   *     a string, may be {@code null}.
   * @param ch
   *     the character.
   * @param startIndex
   *     the index where to start searching.
   * @return {@code true} if the {@code str} is not {@code null} and contains
   *     {@code ch}; {@code false} otherwise.
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsChar(@Nullable final CharSequence str,
      final char ch, final int startIndex) {
    return new Searcher()
        .forChar(ch)
        .startFrom(startIndex)
        .isContainedIn(str);
  }

  /**
   * Tests whether a string contains the code point of a character.
   *
   * @param str
   *     a string, may be {@code null}.
   * @param codePoint
   *     the code point of a character.
   * @return {@code true} if the {@code str} is not {@code null} and contains
   *     {@code codePoint}; {@code false} otherwise.
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsCodePoint(@Nullable final CharSequence str,
      final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .isContainedIn(str);
  }

  /**
   * Tests whether a string contains the code point of a character.
   *
   * @param str
   *     a string, may be {@code null}.
   * @param codePoint
   *     the code point of a character.
   * @param startIndex
   *     the index where to start searching.
   * @return {@code true} if the {@code str} is not {@code null} and contains
   *     {@code codePoint}; {@code false} otherwise.
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsCodePoint(@Nullable final CharSequence str,
      final int codePoint, final int startIndex) {
    return new Searcher()
        .forCodePoint(codePoint)
        .startFrom(startIndex)
        .isContainedIn(str);
  }

  /**
   * Checks whether the specified string contains no character other than the
   * specified character.
   *
   * @param str
   *     a string, which may be null or empty.
   * @param ch
   *     the specified character.
   * @return {@code true} if the specified string contains no character other
   *     than the specified character; {@code false} otherwise. If the specified
   *     string is null or empty, returns false.
   * @see Searcher#forCharsNotEqual(char)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str, final char ch) {
    if (str == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotEqual(ch).isNotContainedIn(str);
    }
  }

  /**
   * Checks whether the specified string contains no Unicode code point other
   * than the specified Unicode code point.
   *
   * @param str
   *     a string, which may be null or empty.
   * @param codePoint
   *     the specified Unicode code point.
   * @return {@code true} if the specified string contains no Unicode code point
   *     other than the specified Unicode code point; {@code false} otherwise.
   *     If the specified string is null or empty, returns false.
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      final int codePoint) {
    if (str == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCodePointsNotEqual(codePoint).isNotContainedIn(str);
    }
  }

  /**
   * Tests whether the specified string contains only the characters satisfying
   * the specified {@link CharFilter}.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param filter
   *     a specified {@link CharFilter}, which cannot be null.
   * @return {@code true} if the string contains only the characters satisfying
   *     the specified {@link CharFilter}; {@code false} otherwise. If the
   *     string is null, returns false. If the string is empty, returns true.
   * @see Searcher#forCharsNotSatisfy(CharFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      final CharFilter filter) {
    if (str == null) {
      return false;
    } else if (filter == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotSatisfy(filter).isNotContainedIn(str);
    }
  }

  /**
   * Tests whether the specified string contains only the code points satisfying
   * the specified {@link CodePointFilter}.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param filter
   *     a specified {@link CodePointFilter}, which cannot be null.
   * @return {@code true} if the string contains only the code points satisfying
   *     the specified {@link CodePointFilter}; {@code false} otherwise. If the
   *     string is null, returns false. If the string is empty, returns true.
   * @see Searcher#forCharsNotSatisfy(CharFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      final CodePointFilter filter) {
    if (str == null) {
      return false;
    } else if (filter == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCodePointsNotSatisfy(filter).isNotContainedIn(str);
    }
  }

  /**
   * Checks if the string contains only certain characters.
   *
   * <p>A {@code null} string will return {@code false}. A {@code null} valid
   * character array will return {@code false}. An empty String ("") always
   * returns {@code true}.
   * <pre>
   * StringUtils.containsOnly(null, *)       = false
   * StringUtils.containsOnly(*, null)       = false
   * StringUtils.containsOnly("", *)         = true
   * StringUtils.containsOnly("ab", '')      = false
   * StringUtils.containsOnly("abab", 'abc') = true
   * StringUtils.containsOnly("ab1", 'abc')  = false
   * StringUtils.containsOnly("abz", 'abc')  = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param validChars
   *     an array of valid chars, may be null
   * @return {@code true} if it only contains valid chars and is non-null
   * @see Searcher#forCharsNotIn(char[])}
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      @Nullable final char[] validChars) {
    if (str == null) {
      return false;
    } else if (validChars == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotIn(validChars).isNotContainedIn(str);
    }
  }

  /**
   * Checks if the string contains only certain characters.
   *
   * <p>A {@code null} string will return {@code false}. A {@code null} valid
   * character String will return {@code false}. An empty String ("") always
   * returns {@code true}.
   * <pre>
   * StringUtils.containsOnly(null, *)       = false
   * StringUtils.containsOnly(*, null)       = false
   * StringUtils.containsOnly("", *)         = true
   * StringUtils.containsOnly("ab", "")      = false
   * StringUtils.containsOnly("abab", "abc") = true
   * StringUtils.containsOnly("ab1", "abc")  = false
   * StringUtils.containsOnly("abz", "abc")  = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param validChars
   *     a string of valid chars, may be null
   * @return {@code true} if it only contains valid chars and is non-null
   * @see Searcher#forCharsNotIn(CharSequence)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsOnly(@Nullable final CharSequence str,
      @Nullable final CharSequence validChars) {
    if (str == null) {
      return false;
    } else if (validChars == null) {
      return false;
    } else if (str.length() == 0) {
      return true;
    } else {
      return new Searcher().forCharsNotIn(validChars).isNotContainedIn(str);
    }
  }

  /**
   * Tests whether the specified string does not contain any character satisfying
   * the specified {@link CharFilter}.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param filter
   *     a specified {@link CharFilter}, which cannot be null.
   * @return {@code true} if the string does not contain any character satisfying
   *     the specified {@link CharFilter}; {@code false} otherwise. If the
   *     string is null, returns true. If the string is empty, returns true.
   * @see Searcher#forCharsSatisfy(CharFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isNotContainedIn(str);
  }

  /**
   * Tests whether the specified string does not contain any code point satisfying
   * the specified {@link CodePointFilter}.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param filter
   *     a specified {@link CodePointFilter}, which cannot be null.
   * @return {@code true} if the string does not contain any code point satisfying
   *     the specified {@link CodePointFilter}; {@code false} otherwise. If the
   *     string is null, returns true. If the string is empty, returns true.
   * @see Searcher#forCodePointsSatisfy(CodePointFilter)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isNotContainedIn(str);
  }

  /**
   * Tests whether the specified string does not contain the specified code
   * point.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param codePoint
   *     the specified Unicode code point.
   * @return {@code true} if the string does not contain the specified code
   *     point; {@code false} otherwise. If the string is null, returns true.
   *     If the string is empty, returns true.
   * @see Searcher#forCodePoint(int)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str, final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .isNotContainedIn(str);
  }

  /**
   * Checks if the string contains none of the invalid characters.
   *
   * <p>A {@code null} string will return {@code true}. A {@code null} invalid
   * character array will return {@code true}. An empty String ("") will
   * returns {@code true}. An empty invalid character array will return {@code true}.
   * <pre>
   * StringUtils.containsNone(null, *)                = true
   * StringUtils.containsNone(*, null)                = true
   * StringUtils.containsNone("", *)                  = true
   * StringUtils.containsNone("ab", [])               = true
   * StringUtils.containsNone("abab", ['x', 'b', y']) = false
   * StringUtils.containsNone("ab1", ['x','y','z'])   = true
   * StringUtils.containsNone("abz", ['z'])            = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param invalidChars
   *     an array of invalid characters, may be null or empty.
   * @return
   *     {@code true} if the specified string contains none of the invalid
   *     characters.
   * @see Searcher#forCharsIn(char...)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      @Nullable final char[] invalidChars) {
    return new Searcher()
        .forCharsIn(invalidChars)
        .isNotContainedIn(str);
  }

  /**
   * Checks if the string contains none of the invalid characters.
   *
   * <p>A {@code null} string will return {@code true}. A {@code null} invalid
   * character array will return {@code true}. An empty String ("") will
   * returns {@code true}. An empty invalid character string will return {@code true}.
   * <pre>
   * StringUtils.containsNone(null, *)                = true
   * StringUtils.containsNone(*, null)                = true
   * StringUtils.containsNone("", *)                  = true
   * StringUtils.containsNone("ab", "")               = true
   * StringUtils.containsNone("abab", "xby")          = false
   * StringUtils.containsNone("ab1", "xyz")           = true
   * StringUtils.containsNone("abz", "z")             = false
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param invalidChars
   *     a sequence of valid chars, may be null.
   * @return
   *     {@code true} if the specified does not contain any of the invalid
   *     characters.
   * @see Searcher#forCharsIn(CharSequence)
   * @see Searcher#isNotContainedIn(CharSequence)
   */
  public static boolean containsNone(@Nullable final CharSequence str,
      @Nullable final CharSequence invalidChars) {
    return new Searcher()
        .forCharsIn(invalidChars)
        .isNotContainedIn(str);
  }

  /**
   * Tests whether the specified string contains any character satisfying
   * the specified {@link CharFilter}.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param filter
   *     a specified {@link CharFilter}, which cannot be null.
   * @return
   *    {@code true} if the string contains any character satisfying the
   *    specified {@link CharFilter}; {@code false} otherwise. If the string is
   *    null or empty, returns {@code false}.
   * @see Searcher#forCharsSatisfy(CharFilter)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      final CharFilter filter) {
    return new Searcher()
        .forCharsSatisfy(filter)
        .isContainedIn(str);
  }

  /**
   * Tests whether the specified string contains any code point satisfying
   * the specified {@link CodePointFilter}.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param filter
   *     a specified {@link CodePointFilter}, which cannot be null.
   * @return
   *    {@code true} if the string contains any code point satisfying
   *     the specified {@link CodePointFilter}; {@code false} otherwise. If the
   *     string is null or empty, returns {@code false}.
   * @see Searcher#forCodePointsSatisfy(CodePointFilter)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      final CodePointFilter filter) {
    return new Searcher()
        .forCodePointsSatisfy(filter)
        .isContainedIn(str);
  }

  /**
   * Tests whether the specified string contains the specified code point.
   *
   * @param str
   *     the string to be tested, which may be null or empty.
   * @param codePoint
   *     the specified code point.
   * @return
   *    {@code true} if the string contains the specified code point;
   *    {@code false} otherwise. If the string is null or empty, returns
   *    {@code false}.
   * @see Searcher#forCodePoint(int)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .isContainedIn(str);
  }

  /**
   * Checks if the string contains any of the valid characters.
   *
   * <p>A {@code null} string will return {@code false}. A {@code null} valid
   * character array will return {@code false}. An empty String ("") will
   * returns {@code false}. An empty invalid character array will return
   * {@code false}.
   * <pre>
   * StringUtils.containsAny(null, *)                = false
   * StringUtils.containsAny(*, null)                = false
   * StringUtils.containsAny("", *)                  = false
   * StringUtils.containsAny("ab", [])               = false
   * StringUtils.containsAny("abab", ['x', 'b', y']) = true
   * StringUtils.containsAny("ab1", ['x','y','z'])   = false
   * StringUtils.containsAny("abz", ['z'])           = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param validChars
   *     an array of valid characters, may be null or empty.
   * @return
   *     {@code true} if the specified string contains any valid characters. If
   *     the string is null or empty, returns {@code false}.
   * @see Searcher#forCharsIn(char...)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      @Nullable final char[] validChars) {
    return new Searcher()
        .forCharsIn(validChars)
        .isContainedIn(str);
  }

  /**
   * Checks if the string contains any of the valid characters.
   *
   * <p>A {@code null} string will return {@code false}. A {@code null} valid
   * character array will return {@code false}. An empty String ("") will
   * returns {@code false}. An empty invalid character string will return
   * {@code false}.
   * <pre>
   * StringUtils.containsAny(null, *)                = false
   * StringUtils.containsAny(*, null)                = false
   * StringUtils.containsAny("", *)                  = false
   * StringUtils.containsAny("ab", "")               = false
   * StringUtils.containsAny("abab", "xby")          = true
   * StringUtils.containsAny("ab1", "xyz")           = false
   * StringUtils.containsAny("abz", "z")             = true
   * </pre>
   *
   * @param str
   *     the string to check, may be null.
   * @param validChars
   *     a sequence of valid chars, may be null.
   * @return
   *     {@code true} if the specified contains any of the valid characters. If
   *     the string is null or empty, returns {@code false}.
   * @see Searcher#forCharsIn(CharSequence)
   * @see Searcher#isContainedIn(CharSequence)
   */
  public static boolean containsAny(@Nullable final CharSequence str,
      @Nullable final CharSequence validChars) {
    return new Searcher()
        .forCharsIn(validChars)
        .isContainedIn(str);
  }

  /**
   * Gets all the occurrences of a substring in a specified string.
   *
   * @param str
   *     the string where to find the substring.
   * @param substr
   *     the substring to be found.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @param list
   *     a optional {@link IntList} where to put the result. If it is null, a
   *     new {@link IntArrayList} will be created to hold the result and
   *     returned.
   * @return a list of integers, which contains all the indexes where the
   *     substring occurs in the string.
   * @see Searcher#getOccurrencesIn(CharSequence)
   */
  public static IntList getOccurrences(@Nullable final CharSequence str,
      @Nullable final CharSequence substr, final boolean ignoreCase,
      @Nullable final IntList list) {
    return new Searcher()
        .forSubstring(substr)
        .ignoreCase(ignoreCase)
        .getOccurrencesIn(str, list);
  }

  /**
   * Counts how many times a character appears in a string.
   *
   * @param str
   *     the string.
   * @param codePoint
   *     the code point of the character to be count.
   * @return the number of occurrences of ch in str, 0 if str is null.
   * @see Searcher#countMatchesIn(CharSequence)
   */
  public static int countMatches(@Nullable final CharSequence str,
      final int codePoint) {
    return new Searcher()
        .forCodePoint(codePoint)
        .countMatchesIn(str);
  }

  /**
   * Counts how many times the substring appears in the larger String.
   *
   * <p>A {@code null} or empty ("") String input returns {@code 0}.
   * <pre>
   * StringUtils.countMatches(null, *)       = 0
   * StringUtils.countMatches("", *)         = 0
   * StringUtils.countMatches("abba", null)  = 0
   * StringUtils.countMatches("abba", "")    = 0
   * StringUtils.countMatches("abba", "a")   = 2
   * StringUtils.countMatches("abba", "ab")  = 1
   * StringUtils.countMatches("abba", "xxx") = 0
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param substr
   *     the substring to count, may be null
   * @return the number of occurrences, 0 if either String is {@code null}
   * @see Searcher#countMatchesIn(CharSequence)
   */
  public static int countMatches(@Nullable final CharSequence str,
      @Nullable final CharSequence substr) {
    return new Searcher()
        .forSubstring(substr)
        .ignoreCase(false)
        .countMatchesIn(str);
  }

  /**
   * Counts how many times the substring appears in the larger String.
   *
   * <p>A {@code null} or empty ("") String input returns {@code 0}.
   * <pre>
   * StringUtils.countMatches(null, *)       = 0
   * StringUtils.countMatches("", *)         = 0
   * StringUtils.countMatches("abba", null)  = 0
   * StringUtils.countMatches("abba", "")    = 0
   * StringUtils.countMatches("abba", "a")   = 2
   * StringUtils.countMatches("abba", "ab")  = 1
   * StringUtils.countMatches("abba", "xxx") = 0
   * </pre>
   *
   * @param str
   *     the string to check, may be null
   * @param substr
   *     the substring to count, may be null
   * @param ignoreCase
   *     whether to compare strings ignoring the case.
   * @return the number of occurrences, 0 if either String is {@code null}
   * @see Searcher#countMatchesIn(CharSequence)
   */
  public static int countMatches(@Nullable final CharSequence str,
      @Nullable final CharSequence substr, final boolean ignoreCase) {
    return new Searcher()
        .forSubstring(substr)
        .ignoreCase(ignoreCase)
        .countMatchesIn(str);
  }

  /**
   * Tests whether a character sequence is strippable from the start.
   *
   * @param str
   *     the specified character sequence.
   * @return
   *     {@code true} if the character sequence is strippable from the start,
   *     i.e., the start of the character sequence is a non-graphic character;
   *     {@code false} otherwise.
   * @see CharUtils#isGraph(int)
   * @see Stripper#isStrippable(CharSequence)
   */
  public static boolean isStartStrippable(final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromStart()
        .isStrippable(str);
  }

  /**
   * Tests whether a character sequence is strippable from the end.
   *
   * @param str
   *     the specified character sequence.
   * @return
   *     {@code true} if the character sequence is strippable from the end,
   *     i.e., the end of the character sequence is a non-graphic character;
   *     {@code false} otherwise.
   * @see CharUtils#isGraph(int)
   * @see Stripper#isStrippable(CharSequence)
   */
  public static boolean isEndStrippable(final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromEnd()
        .isStrippable(str);
  }

  /**
   * Tests whether a character sequence is strippable.
   *
   * @param str
   *     the specified character sequence.
   * @return
   *     {@code true} if the character sequence is strippable, i.e., the start
   *     or end of the character sequence is a non-graphic character; {@code false}
   *     otherwise.
   * @see CharUtils#isGraph(int)
   * @see Stripper#isStrippable(CharSequence)
   */
  public static boolean isStrippable(final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromAnySide()
        .isStrippable(str);
  }

  /**
   * Strips whitespace and non-printable characters from the start of a string.
   *
   * <p>The function use {@link CharUtils#isGraph(int)} to determinate wheter a
   * code point is a printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   * <pre>
   * StringUtils.stripStart(null)       = null
   * StringUtils.stripStart("")         = ""
   * StringUtils.stripStart("abc")      = "abc"
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("  abc  ")  = "abc    "
   * StringUtils.stripStart("abc  ")    = "abc  "
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("       yxabc  ")  = "yxabc  "
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @return the stripped String, or {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromStart()
        .strip(str);
  }

  /**
   * Strips whitespace and non-printable characters from the start of a string.
   *
   * <p>The function use {@link CharUtils#isGraph(int)} to determinate wheter a
   * code point is a printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   * <pre>
   * StringUtils.stripStart(null)       = null
   * StringUtils.stripStart("")         = ""
   * StringUtils.stripStart("abc")      = "abc"
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("  abc  ")  = "abc    "
   * StringUtils.stripStart("abc  ")    = "abc  "
   * StringUtils.stripStart(" abc ")    = "abc "
   * StringUtils.stripStart("       yxabc  ")  = "yxabc  "
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param output
   *     the output where to append the result string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      final Appendable output) throws IOException {
    new Stripper()
        .ofBlank()
        .fromStart()
        .strip(str, output);
  }

  /**
   * Strips any of a set of characters from the start of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripStart(null, *)            = null
   * StringUtils.stripStart("", *)              = ""
   * StringUtils.stripStart("abc", 'a')         = "bc"
   * StringUtils.stripStart("abc", 'b')         = "abc"
   * StringUtils.stripStart("yyyxabc  ", 'y')   = "xabc  "
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChar
   *     the character to remove.
   * @return the stripped String, or {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      final char stripChar) {
    return new Stripper()
        .ofChar(stripChar)
        .fromStart()
        .strip(str);
  }

  /**
   * Strips any of a set of characters from the start of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripStart(null, *)            = null
   * StringUtils.stripStart("", *)              = ""
   * StringUtils.stripStart("abc", 'a')         = "bc"
   * StringUtils.stripStart("abc", 'b')         = "abc"
   * StringUtils.stripStart("yyyxabc  ", 'y')   = "xabc  "
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChar
   *     the character to remove.
   * @param output
   *     the output where to append the stripped String.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      final char stripChar, final Appendable output) throws IOException {
    new Stripper()
        .ofChar(stripChar)
        .fromStart()
        .strip(str, output);
  }

  /**
   * Strips any of a set of characters from the start of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripStart(null, *)          = null
   * StringUtils.stripStart("", *)            = ""
   * StringUtils.stripStart(" abc ", null)    = " abc "
   * StringUtils.stripStart(" abc ", "")      = " abc "
   * StringUtils.stripStart("abc", "a")       = "bc"
   * StringUtils.stripStart("abc", "bc")      = "abc"
   * StringUtils.stripStart("  abc", " a")    = "bc"
   * StringUtils.stripStart("abc  ", "abc")   = "  "
   * StringUtils.stripStart(" abc ", "abc ")  = ""
   * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChars
   *     the characters to remove, null treated as an empty string, and the
   *     original string is returned.
   * @return the stripped String, or {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars) {
    return new Stripper()
        .ofCharsIn(stripChars)
        .fromStart()
        .strip(str);
  }

  /**
   * Strips any of a set of characters from the start of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripStart(null, *)          = null
   * StringUtils.stripStart("", *)            = ""
   * StringUtils.stripStart(" abc ", null)    = " abc "
   * StringUtils.stripStart(" abc ", "")      = " abc "
   * StringUtils.stripStart("abc", "a")       = "bc"
   * StringUtils.stripStart("abc", "bc")      = "abc"
   * StringUtils.stripStart("  abc", " a")    = "bc"
   * StringUtils.stripStart("abc  ", "abc")   = "  "
   * StringUtils.stripStart(" abc ", "abc ")  = ""
   * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChars
   *     the characters to remove, null treated as an empty string, and the
   *     original string is returned.
   * @param output
   *     the output where to append the stripped String.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsIn(stripChars)
        .fromStart()
        .strip(str, output);
  }

  /**
   * Strips any of the characters accepted by a {@link CharFilter} from the
   * start of a string.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param filter
   *     a {@link CharFilter}. The character accepted by this filter is striped
   *     from the start of {@code str}. If it is null, the original string is
   *     returned.
   * @return
   *     the stripped string, or {@code null} if {@code str} is {@code null}.
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Stripper()
        .ofCharsSatisfy(filter)
        .fromStart()
        .strip(str);
  }

  /**
   * Strips any of the characters accepted by a {@link CharFilter} from the
   * start of a string.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param filter
   *     a {@link CharFilter}. The character accepted by this filter is striped
   *     from the start of {@code str}. If it is null, the original string is
   *     returned.
   * @param output
   *     the output where to append the stripped string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsSatisfy(filter)
        .fromStart()
        .strip(str, output);
  }

  /**
   * Strips any of the code points accepted by a {@link CodePointFilter} from
   * the start of a string.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param filter
   *     a {@link CodePointFilter}. The code point accepted by this filter is
   *     striped from the start of {@code str}. If it is null, the original
   *     string is returned.
   * @return
   *     the stripped string, or {@code null} if {@code str} is {@code null}.
   * @see Stripper#strip(CharSequence)
   */
  public static String stripStart(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromStart()
        .strip(str);
  }

  /**
   * Strips any of the code points accepted by a {@link CodePointFilter} from
   * the start of a string.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param filter
   *     a {@link CodePointFilter}. The code point accepted by this filter is
   *     striped from the start of {@code str}. If it is null, the original
   *     string is returned.
   * @param output
   *     the output where to append the stripped string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripStart(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromStart()
        .strip(str, output);
  }

  /**
   * Strips whitespace and non-printable characters from the end of a string.
   *
   * <p>The function use {@link CharUtils#isGraph(int)} to determinate wheter a
   * code point is a printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   * <pre>
   * StringUtils.stripEnd(null)           = null
   * StringUtils.stripEnd("")             = ""
   * StringUtils.stripEnd("abc")          = "abc"
   * StringUtils.stripEnd("abc  ")        = "abc"
   * StringUtils.stripEnd("  abc")        = "  abc"
   * StringUtils.stripEnd("  abc  ")      = "  abc"
   * StringUtils.stripEnd(" abc xy")      = " abc xy"
   * StringUtils.stripEnd("  abcyx xz  ") = "  abcyx xz"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @return the stripped String, or {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromEnd()
        .strip(str);
  }

  /**
   * Strips whitespace and non-printable characters from the end of a string.
   *
   * <p>The function use {@link CharUtils#isGraph(int)} to determinate wheter a
   * code point is a printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   * <pre>
   * StringUtils.stripEnd(null)           = null
   * StringUtils.stripEnd("")             = ""
   * StringUtils.stripEnd("abc")          = "abc"
   * StringUtils.stripEnd("abc  ")        = "abc"
   * StringUtils.stripEnd("  abc")        = "  abc"
   * StringUtils.stripEnd("  abc  ")      = "  abc"
   * StringUtils.stripEnd(" abc xy")      = " abc xy"
   * StringUtils.stripEnd("  abcyx xz  ") = "  abcyx xz"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param output
   *     the output where to append the result string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      final Appendable output) throws IOException {
    new Stripper()
        .ofBlank()
        .fromEnd()
        .strip(str, output);
  }

  /**
   * Strips any of a set of characters from the end of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripEnd(null, *)            = null
   * StringUtils.stripEnd("", *)              = ""
   * StringUtils.stripEnd("  abcyx", 'x')     = "  abccy"
   * StringUtils.stripEnd("  abcyxxx", 'x')   = "  abccy"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChar
   *     the character to remove.
   * @return the stripped String, or {@code null} if null String input.
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      final char stripChar) {
    return new Stripper()
        .ofChar(stripChar)
        .fromEnd()
        .strip(str);
  }

  /**
   * Strips any of a set of characters from the end of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripEnd(null, *)            = null
   * StringUtils.stripEnd("", *)              = ""
   * StringUtils.stripEnd("  abcyx", 'x')     = "  abccy"
   * StringUtils.stripEnd("  abcyxxx", 'x')   = "  abccy"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChar
   *     the character to remove.
   * @param output
   *     the output where to append the stripped string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      final char stripChar, final Appendable output) throws IOException {
    new Stripper()
        .ofChar(stripChar)
        .fromEnd()
        .strip(str, output);
  }

  /**
   * Strips any of a set of characters from the end of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripEnd(null, *)          = null
   * StringUtils.stripEnd("", *)            = ""
   * StringUtils.stripEnd("abc", "")        = "abc"
   * StringUtils.stripEnd("abc", null)      = "abc"
   * StringUtils.stripEnd("  abc", null)    = "  abc"
   * StringUtils.stripEnd("abc  ", null)    = "abc"
   * StringUtils.stripEnd(" abc ", null)    = " abc"
   * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChars
   *     the characters to remove, null treated as an empty string, and the
   *     original string is returned.
   * @return the stripped String, or {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars) {
    return new Stripper()
        .ofCharsIn(stripChars)
        .fromEnd()
        .strip(str);
  }

  /**
   * Strips any of a set of characters from the end of a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the stripChars String is {@code null}, it is treated as an empty
   * string,
   * and the original string is returned.
   * <pre>
   * StringUtils.stripEnd(null, *)          = null
   * StringUtils.stripEnd("", *)            = ""
   * StringUtils.stripEnd("abc", "")        = "abc"
   * StringUtils.stripEnd("abc", null)      = "abc"
   * StringUtils.stripEnd("  abc", null)    = "  abc"
   * StringUtils.stripEnd("abc  ", null)    = "abc"
   * StringUtils.stripEnd(" abc ", null)    = " abc"
   * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param stripChars
   *     the characters to remove, null treated as an empty string, and the
   *     original string is returned.
   * @param output
   *     the output where to append the stripped String.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars, final StringBuilder output) {
    new Stripper()
        .ofCharsIn(stripChars)
        .fromEnd()
        .strip(str, output);
  }

  /**
   * Strips any of the characters accepted by a CharFilter from the end of a
   * String.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is null, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null.
   * @param filter
   *     a CharFilter. The characters accepted by this filter is striped from
   *     the end of {@code str}. If it is null, the original string is
   *     returned.
   * @return the stripped String, or {@code null} if {@code str} is {@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Stripper()
        .ofCharsSatisfy(filter)
        .fromEnd()
        .strip(str);
  }

  /**
   * Strips any of the characters accepted by a CharFilter from the end of a
   * String.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is null, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null.
   * @param filter
   *     a CharFilter. The characters accepted by this filter is striped from
   *     the end of {@code str}. If it is null, the original string is
   *     returned.
   * @param output
   *     the output where to append the stripped String.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final StringBuilder output) {
    new Stripper()
        .ofCharsSatisfy(filter)
        .fromEnd()
        .strip(str, output);
  }

  /**
   * Strips any of the characters accepted by a CharFilter from the end of a
   * String.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is null, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null.
   * @param filter
   *     a CharFilter. The characters accepted by this filter is striped from
   *     the end of {@code str}. If it is null, the original string is
   *     returned.
   * @return the stripped String, or {@code null} if {@code str} is {@code null}
   * @see Stripper#strip(CharSequence)
   */
  public static String stripEnd(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromEnd()
        .strip(str);
  }

  /**
   * Strips any of the characters accepted by a CharFilter from the end of a
   * String.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is null, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, may be null.
   * @param filter
   *     a CharFilter. The characters accepted by this filter is striped from
   *     the end of {@code str}. If it is null, the original string is
   *     returned.
   * @param output
   *     the output where to append the stripped String.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void stripEnd(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final StringBuilder output) {
    new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromEnd()
        .strip(str, output);
  }


  /**
   * Strips whitespace and non-printable characters from the start and end of a
   * String, and returns {@code null} if the string is stripped to empty.
   *
   * <p>This is similar to {@link String#trim()} but use
   * {@link CharUtils#isGraph(int)} to determinate whether a code point is a
   * printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns {@code null} too.
   *
   * <pre>
   * StringUtils.stripToNull(null)           = null
   * StringUtils.stripToNull("")             = null
   * StringUtils.stripToNull("abc")          = "abc"
   * StringUtils.stripToNull("  abc")        = "abc"
   * StringUtils.stripToNull("abc  ")        = "abc"
   * StringUtils.stripToNull(" abc        ") = "abc"
   * StringUtils.stripToNull("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @return the stripped String, {@code null} if the input string was stripped
   *     to an empty string.
   * @see Stripper#stripToNull(CharSequence)
   */
  public static String stripToNull(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromBothSide()
        .stripToNull(str);
  }

  /**
   * Strips whitespace and non-printable characters from the start and end of a
   * String, and returns an empty string if the string is stripped to empty.
   *
   * <p>This is similar to {@link String#trim()} but use
   * {@link CharUtils#isGraph(int)} to determinate whether a code point is a
   * printable non-whitespace character.
   *
   * <p>A {@code null} input String returns "". An empty string ("") input
   * returns "" too.
   *
   * <pre>
   * StringUtils.stripToEmpty(null)           = ""
   * StringUtils.stripToEmpty("")             = ""
   * StringUtils.stripToEmpty("abc")          = "abc"
   * StringUtils.stripToEmpty("  abc")        = "abc"
   * StringUtils.stripToEmpty("abc  ")        = "abc"
   * StringUtils.stripToEmpty(" abc        ") = "abc"
   * StringUtils.stripToEmpty("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @return the stripped String, or an empty string if the input string was
   *     stripped to an empty string.
   * @see Stripper#stripToEmpty(CharSequence)
   */
  public static String stripToEmpty(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromBothSide()
        .stripToEmpty(str);
  }

  /**
   * Strips whitespace and non-printable characters from the start and end of a
   * String.
   *
   * <p>This is similar to {@link String#trim()} but use
   * {@link CharUtils#isGraph(int)} to determinate whether a code point is a
   * printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <pre>
   * StringUtils.strip(null)           = null
   * StringUtils.strip("")             = ""
   * StringUtils.strip("abc")          = "abc"
   * StringUtils.strip("  abc")        = "abc"
   * StringUtils.strip("abc  ")        = "abc"
   * StringUtils.strip(" abc        ") = "abc"
   * StringUtils.strip("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @return the stripped String, {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   * @see String#strip()
   */
  public static String strip(@Nullable final CharSequence str) {
    return new Stripper()
        .ofBlank()
        .fromBothSide()
        .strip(str);
  }

  /**
   * Strips whitespace and non-printable characters from the start and end of a
   * String.
   *
   * <p>This is similar to {@link String#trim()} but use
   * {@link CharUtils#isGraph(int)} to determinate whether a code point is a
   * printable non-whitespace character.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <pre>
   * StringUtils.strip(null)           = null
   * StringUtils.strip("")             = ""
   * StringUtils.strip("abc")          = "abc"
   * StringUtils.strip("  abc")        = "abc"
   * StringUtils.strip("abc  ")        = "abc"
   * StringUtils.strip(" abc        ") = "abc"
   * StringUtils.strip("  abc yx ")    = "abc yx"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @param output
   *     the output where to append the result string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   * @see String#strip()
   */
  public static void strip(@Nullable final CharSequence str,
      final Appendable output) throws IOException {
    new Stripper()
        .ofBlank()
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * Strips any of a set of characters from the start and end of a string. This
   * is similar to {@link String#trim()} but allows the characters to be
   * stripped to be controlled.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the strip character string is {@code null} or empty, the original
   * string
   * is returned.
   * <pre>
   * StringUtils.strip(null, *)          = null
   * StringUtils.strip("", *)            = ""
   * StringUtils.strip("abc", null)      = "abc"
   * StringUtils.strip("  abc", null)    = "  abc"
   * StringUtils.strip("abc  ", null)    = "abc  "
   * StringUtils.strip(" abc ", null)    = "  abc "
   * StringUtils.strip("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, which may be null.
   * @param stripChars
   *     the characters to remove; if it is null or empty, the original string
   *     is returned.
   * @return the stripped String, {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String strip(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars) {
    return new Stripper()
        .ofCharsIn(stripChars)
        .fromBothSide()
        .strip(str);
  }

  /**
   * Strips any of a set of characters from the start and end of a string. This
   * is similar to {@link String#trim()} but allows the characters to be
   * stripped to be controlled.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the strip character string is {@code null} or empty, the original
   * string
   * is returned.
   * <pre>
   * StringUtils.strip(null, *)          = null
   * StringUtils.strip("", *)            = ""
   * StringUtils.strip("abc", null)      = "abc"
   * StringUtils.strip("  abc", null)    = "  abc"
   * StringUtils.strip("abc  ", null)    = "abc  "
   * StringUtils.strip(" abc ", null)    = "  abc "
   * StringUtils.strip("  abcyx", "xyz") = "  abc"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, which may be null.
   * @param stripChars
   *     the characters to remove; if it is null or empty, the original string
   *     is returned.
   * @param output
   *     the output where to append the result string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void strip(@Nullable final CharSequence str,
      @Nullable final CharSequence stripChars, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsIn(stripChars)
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * Strips any of the characters accepted by a CharFilter from both end of a
   * String.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, which may be null.
   * @param filter
   *     a CharFilter. The characters accepted by this filter is striped from
   *     both end of {@code str}. If it is null, the original string is
   *     returned.
   * @return the stripped String, {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String strip(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Stripper()
        .ofCharsSatisfy(filter)
        .fromBothSide()
        .strip(str);
  }

  /**
   * Strips any of the characters accepted by a CharFilter from both end of a
   * String.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, which may be null.
   * @param filter
   *     a CharFilter. The characters accepted by this filter is striped from
   *     both end of {@code str}. If it is null, the original string is
   *     returned.
   * @param output
   *     the output where to append the result string.
   * @throws IOException
   *     if any I/O error occurs.
   * @see Stripper#strip(CharSequence, Appendable)
   */
  public static void strip(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCharsSatisfy(filter)
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * Strips any of the characters accepted by a CodePointFilter from both end of a
   * String.
   *
   * <p>A {@code null} input string returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <p>If the filter is {@code null}, the original string is returned.
   *
   * @param str
   *     the string to remove characters from, which may be null.
   * @param filter
   *     a CodePointFilter. The characters accepted by this filter is striped from
   *     both end of {@code str}. If it is null, the original string is
   *     returned.
   * @return the stripped String, {@code null} if null String input
   * @see Stripper#strip(CharSequence)
   */
  public static String strip(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter) {
    return new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromBothSide()
        .strip(str);
  }

  public static void strip(@Nullable final CharSequence str,
      @Nullable final CodePointFilter filter, final Appendable output)
      throws IOException {
    new Stripper()
        .ofCodePointsSatisfy(filter)
        .fromBothSide()
        .strip(str, output);
  }

  /**
   * Strips all whitespace and non-printable characters from a string.
   *
   * <p>A {@code null} input String returns {@code null}. An empty string ("")
   * input returns the empty string.
   *
   * <pre>
   * StringUtils.stripAll(null)           = null
   * StringUtils.stripAll("")             = ""
   * StringUtils.stripAll("abc")          = "abc"
   * StringUtils.stripAll("  abc")        = "abc"
   * StringUtils.stripAll("abc  ")        = "abc"
   * StringUtils.stripAll(" abc        ") = "abc"
   * StringUtils.stripAll("  abc yx ")    = "abcyx"
   * </pre>
   *
   * @param str
   *     the string to remove characters from, may be null
   * @return the stripped String, {@code null} if null String input
   * @see Remover#forCodePointsSatisfy(CodePointFilter)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String stripAll(@Nullable final CharSequence str) {
    return new Remover()
        .forCodePointsSatisfy(BlankCodePointFilter.INSTANCE)
        .removeFrom(str);
  }

  /**
   * Remove the last character from a string.
   *
   * <p>If the string ends in {@code \r\n}, then remove both of them.
   * <pre>
   * StringUtils.chop(null)          = null
   * StringUtils.chop("")            = ""
   * StringUtils.chop("abc \r")      = "abc "
   * StringUtils.chop("abc\n")       = "abc"
   * StringUtils.chop("abc\r\n")     = "abc"
   * StringUtils.chop("abc")         = "ab"
   * StringUtils.chop("abc\nabc")    = "abc\nab"
   * StringUtils.chop("a")           = ""
   * StringUtils.chop("\r")          = ""
   * StringUtils.chop("\n")          = ""
   * StringUtils.chop("\r\n")        = ""
   * </pre>
   *
   * @param str
   *     the string to chop last character from, may be null
   * @return String without last character, {@code null} if null String input
   */
  public static String chop(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int strLen = str.length();
    if (strLen < 2) {
      return EMPTY;
    }
    final int lastIndex = strLen - 1;
    if (str.charAt(lastIndex) == Ascii.LINE_FEED) {
      final int lastLastIndex = lastIndex - 1;
      if (str.charAt(lastLastIndex) == Ascii.CARRIAGE_RETURN) {
        return str.toString().substring(0, lastLastIndex);
      }
    }
    return str.toString().substring(0, lastIndex);
  }

  public static void chop(@Nullable final CharSequence str,
      final StringBuilder output) {
    final int len;
    if ((str == null) || ((len = str.length()) < 2)) {
      return;
    }
    final int lastIndex = len - 1;
    if (str.charAt(lastIndex) == Ascii.LINE_FEED) {
      final int lastLastIndex = lastIndex - 1;
      if (str.charAt(lastLastIndex) == Ascii.CARRIAGE_RETURN) {
        output.append(str, 0, lastLastIndex);
        return;
      }
    }
    output.append(str, 0, lastIndex);
  }

  /**
   * Removes one newline from end of a string if it's there, otherwise leave it
   * alone. A newline is &quot;{@code \n}&quot;, &quot;{@code \r} &quot;, or
   * &quot;{@code \r\n}&quot;.
   * <pre>
   * StringUtils.chomp(null)          = null
   * StringUtils.chomp("")            = ""
   * StringUtils.chomp("abc \r")      = "abc "
   * StringUtils.chomp("abc\n")       = "abc"
   * StringUtils.chomp("abc\r\n")     = "abc"
   * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
   * StringUtils.chomp("abc\n\r")     = "abc\n"
   * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
   * StringUtils.chomp("\r")          = ""
   * StringUtils.chomp("\n")          = ""
   * StringUtils.chomp("\r\n")        = ""
   * </pre>
   *
   * @param str
   *     the string to chomp a newline from, may be null
   * @return String without newline, {@code null} if null String input
   */
  public static String chomp(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int len;
    if ((len = str.length()) == 0) {
      return EMPTY;
    }
    if (len == 1) {
      final char ch = str.charAt(0);
      if ((ch == Ascii.CARRIAGE_RETURN) || (ch == Ascii.LINE_FEED)) {
        return EMPTY;
      }
      return str.toString();
    }
    int lastIndex = len - 1;
    final char last = str.charAt(lastIndex);
    if (last == Ascii.LINE_FEED) {
      if (str.charAt(lastIndex - 1) == Ascii.CARRIAGE_RETURN) {
        --lastIndex;
      }
    } else if (last != Ascii.CARRIAGE_RETURN) {
      ++lastIndex;
    }
    if (lastIndex == 0) {
      return EMPTY;
    } else if (lastIndex == len) {
      return str.toString();
    } else {
      return str.toString().substring(0, lastIndex);
    }
  }

  public static void chomp(@Nullable final CharSequence str,
      final StringBuilder output) {
    final int len;
    if ((str == null) || ((len = str.length()) == 0)) {
      return;
    }
    if (len == 1) {
      final char ch = str.charAt(0);
      if ((ch != Ascii.CARRIAGE_RETURN) && (ch != Ascii.LINE_FEED)) {
        output.append(ch);
      }
      return;
    }
    int lastIndex = len - 1;
    final char last = str.charAt(lastIndex);
    if (last == Ascii.LINE_FEED) {
      if (str.charAt(lastIndex - 1) == Ascii.CARRIAGE_RETURN) {
        --lastIndex;
      }
    } else if (last != Ascii.CARRIAGE_RETURN) {
      ++lastIndex;
    }
    output.append(str, 0, lastIndex);
  }

  /**
   * Removes {@code separator} from the end of {@code str} if it's there,
   * otherwise leave it alone.
   *
   * <p>NOTE: This method changed in version 2.0. It now more closely matches
   * Perl
   * chomp. For the previous behavior, use {@link #substringBeforeLast(String,
   * String)}. This method uses {@link String#endsWith(String)}.
   * <pre>
   * StringUtils.chomp(null, *)         = null
   * StringUtils.chomp("", *)           = ""
   * StringUtils.chomp("foobar", "bar") = "foo"
   * StringUtils.chomp("foobar", "baz") = "foobar"
   * StringUtils.chomp("foo", "foo")    = ""
   * StringUtils.chomp("foo ", "foo")   = "foo "
   * StringUtils.chomp(" foo", "foo")   = " "
   * StringUtils.chomp("foo", "foooo")  = "foo"
   * StringUtils.chomp("foo", "")       = "foo"
   * StringUtils.chomp("foo", null)     = "foo"
   * </pre>
   *
   * @param str
   *     the string to chomp from, may be null
   * @param separator
   *     separator String, may be null
   * @return String without trailing separator, {@code null} if null String
   *     input
   */
  public static String chomp(@Nullable final CharSequence str,
      @Nullable final CharSequence separator) {
    if (str == null) {
      return null;
    }
    final int strLen;
    if ((strLen = str.length()) == 0) {
      return EMPTY;
    }
    final int sepLen;
    if ((separator == null)
        || ((sepLen = separator.length()) == 0)) {
      return str.toString();
    }
    if (endsWithSubstring(str, 0, str.length(), separator, false)) {
      if (strLen == sepLen) {
        return EMPTY;
      } else {
        return str.subSequence(0, strLen - sepLen).toString();
      }
    } else {
      return str.toString();
    }
  }

  public static void chomp(@Nullable final CharSequence str,
      @Nullable final CharSequence separator, final StringBuilder output) {
    final int strLen;
    final int sepLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return;
    }
    if ((separator == null) || ((sepLen = separator.length()) == 0)) {
      output.append(str);
      return;
    }
    if (endsWithSubstring(str, 0, str.length(), separator, false)) {
      if (strLen > sepLen) {
        output.append(str, 0, strLen - sepLen);
      }
    } else {
      output.append(str);
    }
  }

  /**
   * Removes a substring only if it is at the beginning of a source string,
   * otherwise returns the source string.
   *
   * <p>The comparison is case-sensitive.</p>
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source string will return the empty string. A {@code null} search string
   * will return the source string.</p>
   *
   * <pre><code>
   * StringUtils.removePrefix(null, *)      = null
   * StringUtils.removePrefix("", *)        = ""
   * StringUtils.removePrefix(str, null)    = str
   * StringUtils.removePrefix("www.domain.com", "www.")    = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "WwW.")    = "www.domain.com"
   * StringUtils.removePrefix("domain.com", "www.")        = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "domain")  = "www.domain.com"
   * StringUtils.removePrefix("abc", "")                   = "abc"
   * </code></pre>
   *
   * @param str
   *     the source String to search, may be null
   * @param prefix
   *     the prefix string to search for and remove, may be null.
   * @return the substring with the string removed if found, {@code null} if
   *     null string input.
   * @see Remover#forPrefix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removePrefix(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix) {
    return new Remover()
        .forPrefix(prefix)
        .ignoreCase(false)
        .removeFrom(str);
  }

  /**
   * Removes a substring only if it is at the beginning of a source string,
   * otherwise returns the source string.
   *
   * <p>The comparison could be case-sensitive or case-insensitive, controlled
   * by
   * an argument.
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source
   * string will return the empty string. A {@code null} search string will
   * return the source string.
   * <pre>
   * StringUtils.removePrefix(null, *, *)      = null
   * StringUtils.removePrefix("", *, *)        = ""
   * StringUtils.removePrefix(str, null, *)    = str
   * StringUtils.removePrefix("www.domain.com", "www.", *)    = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "WwW.", true) = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "WwW.", false)= "www.domain.com"
   * StringUtils.removePrefix("domain.com", "www.", *)        = "domain.com"
   * StringUtils.removePrefix("www.domain.com", "domain", *)  = "www.domain.com"
   * StringUtils.removePrefix("abc", "", *)                   = "abc"
   * </pre>
   *
   * @param str
   *     the source String to search, may be null
   * @param prefix
   *     the prefix string to search for and remove, may be null
   * @param ignoreCase
   *     indicates whether the compare should ignore case (case insensitive) or
   *     not.
   * @return the substring with the string removed if found, {@code null} if
   *     null string input
   * @see Remover#forPrefix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removePrefix(@Nullable final CharSequence str,
      @Nullable final CharSequence prefix, final boolean ignoreCase) {
    return new Remover()
        .forPrefix(prefix)
        .ignoreCase(ignoreCase)
        .removeFrom(str);
  }


  /**
   * Removes a substring only if it is at the end of a source string, otherwise
   * returns the source string.
   *
   * <p>The comparison is case-sensitive.</p>
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source string will return the empty string. A {@code null} search string will
   * return the source string.</p>
   *
   * <pre>
   * StringUtils.removeSuffix(null, *, *)                      = null
   * StringUtils.removeSuffix("", *, *)                        = ""
   * StringUtils.removeSuffix(*, null, *)                      = *
   * StringUtils.removeSuffix("www.domain.com", ".com.", *)    = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", ".com", *)     = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", true)  = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", false) = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", "domain", *)   = "www.domain.com"
   * StringUtils.removeSuffix("abc", "", *)                    = "abc"
   * </pre>
   *
   * @param str
   *     the source String to search, may be null.
   * @param suffix
   *     the suffix string to search for and remove, may be null.
   * @return the substring with the string removed if found, {@code null} if
   *     null String input
   * @see Remover#forSuffix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeSuffix(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix) {
    return new Remover()
        .forSuffix(suffix)
        .ignoreCase(false)
        .removeFrom(str);
  }

  /**
   * Removes a substring only if it is at the end of a source string, otherwise
   * returns the source string.
   *
   * <p>The comparison could be case-sensitive or case-insensitive, controlled
   * by an argument.
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source string will return the empty string. A {@code null} search string will
   * return the source string.</p>
   *
   * <pre>
   * StringUtils.removeSuffix(null, *, *)                      = null
   * StringUtils.removeSuffix("", *, *)                        = ""
   * StringUtils.removeSuffix(*, null, *)                      = *
   * StringUtils.removeSuffix("www.domain.com", ".com.", *)    = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", ".com", *)     = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", true)  = "www.domain"
   * StringUtils.removeSuffix("www.domain.com", ".Com", false) = "www.domain.com"
   * StringUtils.removeSuffix("www.domain.com", "domain", *)   = "www.domain.com"
   * StringUtils.removeSuffix("abc", "", *)                    = "abc"
   * </pre>
   *
   * @param str
   *     the source String to search, may be null.
   * @param suffix
   *     the suffix string to search for and remove, may be null.
   * @param ignoreCase
   *     indicates whether the compare should ignore case (case insensitive) or
   *     not.
   * @return the substring with the string removed if found, {@code null} if
   *     null String input
   * @see Remover#forSuffix(CharSequence)
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeSuffix(@Nullable final CharSequence str,
      @Nullable final CharSequence suffix, final boolean ignoreCase) {
    return new Remover()
        .forSuffix(suffix)
        .ignoreCase(ignoreCase)
        .removeFrom(str);
  }

  public static String removePrefixAndSuffix(@Nullable final CharSequence str,
      final CharSequence prefix, final String suffix) {
    return new Remover()
        .forPrefix(prefix)
        .forSuffix(suffix)
        .ignoreCase(false)
        .removeFrom(str);
  }

  public static String removePrefixAndSuffix(@Nullable final CharSequence str,
      final CharSequence prefix, final CharSequence suffix, final boolean ignoreCase) {
    return new Remover()
        .forPrefix(prefix)
        .forSuffix(suffix)
        .ignoreCase(ignoreCase)
        .removeFrom(str);
  }

  /**
   * Removes the occurrences of a character from a string.
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source
   * string will return the empty string.
   * <pre>
   * StringUtils.removeChar(null, *)       = null
   * StringUtils.removeChar("", *)         = ""
   * StringUtils.removeChar("queued", 'u') = "qeed"
   * StringUtils.removeChar("queued", 'z') = "queued"
   * </pre>
   *
   * @param str
   *     the source String to search, which may be null.
   * @param ch
   *     the character to search for and remove.
   * @return the substring with the char removed if found, {@code null} if null
   *     String input
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeChar(@Nullable final CharSequence str, final char ch) {
    return new Remover()
        .forChar(ch)
        .limit(-1)
        .removeFrom(str);
  }

  /**
   * Removes the occurrences of a character from a string.
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source
   * string will return the empty string.
   * <pre>
   * StringUtils.removeChar(null, *, *)       = null
   * StringUtils.removeChar("", *, *)         = ""
   * StringUtils.removeChar("queued", 'u', -1) = "qeed"
   * StringUtils.removeChar("queued", 'u', 1) = "qeued"
   * StringUtils.removeChar("queued", 'z', -1) = "queued"
   * </pre>
   *
   * @param str
   *     the source String to search, which may be null.
   * @param ch
   *     the character to search for and remove.
   * @param max
   *     the maximum number of characters could be removed; -1 means no limit.
   * @return the substring with the char removed if found, {@code null} if null
   *     String input
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeChar(@Nullable final CharSequence str, final char ch,
      final int max) {
    return new Remover()
        .forChar(ch)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * Removes the occurrences of a Unicode code point from a string.
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source
   * string will return the empty string.
   * <pre>
   * StringUtils.removeCodePoint(null, *)       = null
   * StringUtils.removeCodePoint("", *)         = ""
   * StringUtils.removeCodePoint("queued", 'u') = "qeed"
   * StringUtils.removeCodePoint("queued", 'z') = "queued"
   * </pre>
   *
   * @param str
   *     the source String to search, which may be null.
   * @param codePoint
   *     the Unicode code point to search for and remove.
   * @return the substring with the char removed if found, {@code null} if null
   *     String input
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeCodePoint(@Nullable final CharSequence str, final int codePoint) {
    return new Remover()
        .forCodePoint(codePoint)
        .limit(-1)
        .removeFrom(str);
  }

  /**
   * Removes the occurrences of a Unicode code point from a string.
   *
   * <p>A {@code null} source string will return {@code null}. An empty ("")
   * source
   * string will return the empty string.
   * <pre>
   * StringUtils.removeCodePoint(null, *, *)       = null
   * StringUtils.removeCodePoint("", *, *)         = ""
   * StringUtils.removeCodePoint("queued", 'u', -1) = "qeed"
   * StringUtils.removeCodePoint("queued", 'u', 1) = "qeued"
   * StringUtils.removeCodePoint("queued", 'z', -1) = "queued"
   * </pre>
   *
   * @param str
   *     the source String to search, which may be null.
   * @param codePoint
   *     the Unicode code point to search for and remove.
   * @param max
   *     the maximum number of characters could be removed; -1 means no limit.
   * @return the substring with the char removed if found, {@code null} if null
   *     String input
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeCodePoint(@Nullable final CharSequence str, final int codePoint,
      final int max) {
    return new Remover()
        .forCodePoint(codePoint)
        .limit(max)
        .removeFrom(str);
  }

  public static String removeChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter) {
    return new Remover()
        .forCharsSatisfy(filter)
        .limit(-1)
        .removeFrom(str);
  }

  public static String removeChar(@Nullable final CharSequence str,
      @Nullable final CharFilter filter, final int max) {
    return new Remover()
        .forCharsSatisfy(filter)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * Removes a substring from a string.
   *
   * <p>A {@code null} string input returns {@code null}. An empty ("") string
   * input returns an empty string. A {@code null} remove string returns the
   * input string. A {@code null} output {@link StringBuilder} throws a {@link
   * NullPointerException}.
   *
   * <pre>
   * StringUtils.removeSubstring(null, *, *)        = null
   * StringUtils.removeSubstring("", *, *)          = ""
   * StringUtils.removeSubstring("queued", null, *) = "queued"
   * StringUtils.removeSubstring("queued", "", *)   = "queued"
   * StringUtils.removeSubstring("queued", "ue", 1) = "qd"
   * StringUtils.removeSubstring("queued", "ue", 2) = "q"
   * StringUtils.removeSubstring("queued", "ue", 3) = ""
   * </pre>
   *
   * @param str
   *     the string to remove from, may be null
   * @param remove
   *     the string to remove, may be null
   * @param max
   *     maximum number of occurrences to remove, or {@code -1} if no limit
   * @param ignoreCase
   *     indicates whether the compare should ignore case (case insensitive) or
   *     not.
   * @return the substring with the string removed if found, {@code null} if
   *     null String input
   * @see Remover#removeFrom(CharSequence)
   */
  public static String removeSubstring(@Nullable final CharSequence str,
      @Nullable final CharSequence remove, final int max, final boolean ignoreCase) {
    return new Remover()
        .forSubstring(remove)
        .ignoreCase(ignoreCase)
        .limit(max)
        .removeFrom(str);
  }

  /**
   * Replaces all occurrences of a character in a string with another. This is a
   * null-safe version of {@link String#replace(char, char)}.
   *
   * <p>A {@code null} string input returns {@code null}. An empty ("") string
   * input returns an empty string.
   * <pre>
   * StringUtils.replaceChars(null, *, *)        = null
   * StringUtils.replaceChars("", *, *)          = ""
   * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
   * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
   * </pre>
   *
   * @param str
   *     String to replace characters in, may be null
   * @param search
   *     the character to search for, may be null
   * @param replacement
   *     the character to replace, may be null
   * @return modified String, {@code null} if null string input
   * @see Replacer#searchForChar(char)
   * @see Replacer#replaceWithChar(char)
   * @see Replacer#applyTo(CharSequence)
   */
  public static String replaceChar(@Nullable final CharSequence str,
      final char search, final char replacement) {
    return new Replacer()
        .searchForChar(search)
        .replaceWithChar(replacement)
        .applyTo(str);
  }

  public static void replaceChar(@Nullable final CharSequence str, final char search,
      final char replacement, final StringBuilder builder) {
    new Replacer()
        .searchForChar(search)
        .replaceWithChar(replacement)
        .applyTo(str,builder);
  }

  /**
   * Replaces multiple characters in a string in one go. This method can also be
   * used to delete characters.
   *
   * <p>A {@code null} string input returns {@code null}. An empty ("") string
   * input returns an empty string. A null or empty set of search characters
   * returns the input string.
   *
   * <p>The length of the search characters should normally equal the length of
   * the
   * replace characters. If the search characters is longer, then the extra
   * search characters are deleted. If the search characters is shorter, then
   * the extra replace characters are ignored.
   * <pre>
   * StringUtils.replaceChars(null, *, *)           = null
   * StringUtils.replaceChars("", *, *)             = ""
   * StringUtils.replaceChars("abc", null, *)       = "abc"
   * StringUtils.replaceChars("abc", "", *)         = "abc"
   * StringUtils.replaceChars("abc", "b", null)     = "ac"
   * StringUtils.replaceChars("abc", "b", "")       = "ac"
   * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
   * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
   * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
   * </pre>
   *
   * @param str
   *     the string to replace characters in, may be null.
   * @param searchChars
   *     a set of characters to search for, may be null.
   * @param replaceChars
   *     a set of characters to replace, may be null.
   * @return the modified string, or {@code null} if null string input.
   * @see Replacer#searchForChar(char)
   * @see Replacer#replaceWithChar(char)
   * @see Replacer#applyTo(CharSequence)
   */
  @Nullable
  public static String replaceChars(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, @Nullable final String replaceChars) {
    if (str == null) {
      return null;
    }
    if (str.isEmpty() || (searchChars == null) || searchChars.isEmpty()) {
      return str.toString();
    }
    final StringBuilder builder = new StringBuilder();
    final int count = replaceChars(str, searchChars, replaceChars, builder);
    if (count == 0) {
      return str.toString();
    } else if (builder.isEmpty()) {
      return EMPTY;
    } else {
      return builder.toString();
    }
  }

  public static int replaceChars(@Nullable final CharSequence str,
      @Nullable final CharSequence searchChars, @Nullable final String replaceChars,
      final StringBuilder builder) {
    final int len;
    if ((str == null) || ((len = str.length()) == 0)) {
      return 0;
    }
    if ((searchChars == null) || (searchChars.isEmpty())) {
      builder.append(str);
      return 0;
    }
    final String replace = defaultIfNull(replaceChars, EMPTY);
    final int replaceLen = replace.length();
    final int searchCharsLen = searchChars.length();
    int count = 0;
    for (int i = 0; i < len; i++) {
      final char ch = str.charAt(i);
      final CharFilter filter = new AcceptSpecifiedCharFilter(ch);
      final int index = firstIndexOf(searchChars, 0, searchCharsLen, filter);
      if (index < searchCharsLen) {
        ++count;
        if (index < replaceLen) {
          builder.append(replace.charAt(index));
        }
      } else {
        builder.append(ch);
      }
    }
    return count;
  }

  /**
   * Replaces a string with another String inside a larger String, for all
   * occurrences of the search String. A {@code null} reference passed to this
   * method is a no-op.
   * <pre>
   * StringUtils.replace(null, *, *, *)         = null
   * StringUtils.replace("", *, *, *)           = ""
   * StringUtils.replace("any", null, *, *)     = "any"
   * StringUtils.replace("any", *, null, *)     = "any"
   * StringUtils.replace("any", "", *, *)       = "any"
   * StringUtils.replace("any", *, *, 0)        = "any"
   * StringUtils.replace("abaa", "a", null, -1) = "abaa"
   * StringUtils.replace("abaa", "a", "", -1)   = "b"
   * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
   * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
   * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
   * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
   * </pre>
   *
   * @param str
   *     text to search and replace in, may be null.
   * @param search
   *     the string to search for, may be null.
   * @param replacement
   *     the string to replace it with, may be null.
   * @return the text with any replacements processed, {@code null} if null
   *     String input
   * @see Replacer#searchForSubstring(CharSequence)
   * @see Replacer#replaceWithString(CharSequence)
   * @see Replacer#applyTo(CharSequence)
   */
  public static String replace(@Nullable final CharSequence str,
      @Nullable final CharSequence search, @Nullable final CharSequence replacement) {
    return new Replacer()
        .searchForSubstring(search)
        .ignoreCase(false)
        .replaceWithString(replacement)
        .applyTo(str);
  }

  /**
   * Replaces a string with another String inside a larger String, for the first
   * {@code max} occurrences of the search String. A {@code null} reference
   * passed to this method is a no-op.
   * <pre>
   * StringUtils.replace(null, *, *, *)         = null
   * StringUtils.replace("", *, *, *)           = ""
   * StringUtils.replace("any", null, *, *)     = "any"
   * StringUtils.replace("any", "", *, *)       = "any"
   * StringUtils.replace("any", *, *, 0)        = "any"
   * StringUtils.replace("abaa", "a", null, -1) = "b"
   * StringUtils.replace("abaa", "a", "", -1)   = "b"
   * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
   * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
   * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
   * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
   * </pre>
   *
   * @param str
   *     text to search and replace in, may be null
   * @param search
   *     the string to search for, may be null
   * @param replacement
   *     the string to replace it with, may be null
   * @param max
   *     maximum number of occurrences to replace, or {@code -1} if no limit.
   * @param ignoreCase
   *     indicates whether the compare should ignore case (case insensitive) or
   *     not.
   * @return the text with any replacements processed, {@code null} if null
   *     String input
   * @see Replacer#searchForSubstring(CharSequence)
   * @see Replacer#replaceWithString(CharSequence)
   * @see Replacer#applyTo(CharSequence)
   */
  public static String replace(@Nullable final CharSequence str,
      @Nullable final CharSequence search, @Nullable final CharSequence replacement,
      final int max, final boolean ignoreCase) {
    return new Replacer()
        .searchForSubstring(search)
        .replaceWithString(replacement)
        .limit(max)
        .ignoreCase(ignoreCase)
        .applyTo(str);
  }

  public static int replace(@Nullable final CharSequence str,
      @Nullable final CharSequence search, @Nullable final CharSequence replacement,
      final int max, final boolean ignoreCase, final StringBuilder output) {
    return new Replacer()
        .searchForSubstring(search)
        .replaceWithString(replacement)
        .limit(max)
        .ignoreCase(ignoreCase)
        .applyTo(str, output);
  }

  /**
   * Replaces all occurrences of strings within another string.
   *
   * <p>A {@code null} reference passed to this method is a no-op, or if any
   * "search string" or "string to replace" is null, that replace will be
   * ignored. This will not repeat. For repeating replaces, call the function
   * {@link #replaceEachRepeatedly}.
   * <pre>
   *  StringUtils.replaceEach(null, *, *)        = null
   *  StringUtils.replaceEach("", *, *)          = ""
   *  StringUtils.replaceEach("aba", null, null) = "aba"
   *  StringUtils.replaceEach("aba", new String[0], null) = "aba"
   *  StringUtils.replaceEach("aba", null, new String[0]) = "aba"
   *  StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
   *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
   *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
   *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
   *  (example of how it does not repeat)
   *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
   * </pre>
   *
   * @param str
   *     string to search and replace in, no-op if null.
   * @param searches
   *     the strings to search for, no-op if null.
   * @param replaces
   *     the strings to replace them with, no-op if null.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @return the text with any replacements processed, {@code null} if null
   *     string input.
   * @throws IndexOutOfBoundsException
   *     if the lengths of the arrays are not the same (null is ok, and/or size
   *     0)
   */
  @Nullable
  public static String replaceEach(@Nullable final CharSequence str,
      @Nullable final CharSequence[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase) {
    if (str == null) {
      return null;
    }
    if ((str.isEmpty()) || (searches == null) || (searches.length == 0)
        || (replaces == null) || (replaces.length == 0)) {
      return str.toString();
    }
    requireEqual("searches.length", searches.length,
        "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];
    final StringBuilder builder = new StringBuilder();
    final int modified = replaceEachImpl(str, searches, replaces, ignoreCase, builder, noMoreMatches);
    if (modified == 0) {
      return str.toString();
    }
    if (builder.isEmpty()) {
      return EMPTY;
    } else {
      return builder.toString();
    }
  }

  public static int replaceEach(@Nullable final String str,
      @Nullable final String[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase, final StringBuilder builder) {
    if ((str == null) || str.isEmpty()) {
      return 0;
    }
    if ((searches == null) || (searches.length == 0) || (replaces == null)
        || (replaces.length == 0)) {
      builder.append(str);
      return 0;
    }
    requireEqual("searches.length", searches.length,
        "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];
    return replaceEachImpl(str, searches, replaces, ignoreCase, builder,
        noMoreMatches);
  }

  /**
   * Replaces all occurrences of strings within another string repeatedly.
   *
   * <p>A {@code null} reference passed to this method is a no-op, or if any
   * "search string" or "string to replace" is null, that replace will be
   * ignored. This will repeat until no further replacement could be taken.
   * <pre>
   *  replaceEach(null, *, *, *) = null
   *  replaceEach("", *, *, *) = ""
   *  replaceEach("aba", null, null, *) = "aba"
   *  replaceEach("aba", new String[0], null, *) = "aba"
   *  replaceEach("aba", null, new String[0], *) = "aba"
   *  replaceEach("aba", new String[]{"a"}, null, *) = "aba"
   *  replaceEach("aba", new String[]{"a"}, new String[]{""}, *) = "b"
   *  replaceEach("aba", new String[]{null}, new String[]{"a"}, *) = "aba"
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *) = "wcte"
   *  (example of how it repeats)
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false) = "dcte"
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true) = "tcte"
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, false) = "dcabe"
   *  (example of endless loop and throw IllegalArgumentException)
   *  replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, true)
   * </pre>
   *
   * @param str
   *     string to search and replace in, no-op if null
   * @param searches
   *     the strings to search for, no-op if null
   * @param replaces
   *     the strings to replace them with, no-op if null
   * @param ignoreCase
   *     whether to ignore case while comparing strings
   * @return the text with any replacements processed, {@code null} if null
   *     String input
   * @throws IllegalStateException
   *     if the search is repeating and there is an endless loop due to outputs
   *     of one being inputs to another
   * @throws IndexOutOfBoundsException
   *     if the lengths of the arrays are not the same (null is ok, and/or size
   *     0)
   */
  public static String replaceEachRepeatedly(@Nullable final String str,
      @Nullable final String[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase) {
    if ((str == null)
        || str.isEmpty()
        || (searches == null)
        || (searches.length == 0)
        || (replaces == null)
        || (replaces.length == 0)) {
      return str;
    }
    requireEqual("searches.length", searches.length, "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];
    final StringBuilder builder = new StringBuilder();
    String result = str;
    for (int i = 0; i < searches.length; ++i) {
      final int modifies = replaceEachImpl(result, searches, replaces,
          ignoreCase, builder, noMoreMatches);
      if (modifies == 0) {
        return result;
      } else if (builder.length() == 0) {
        return EMPTY;
      }
      result = builder.toString();
      builder.setLength(0);
    }
    // check for the loop
    if (replaceEachImpl(result, searches, replaces,
        ignoreCase, builder, noMoreMatches) > 0) {
      throw new IllegalStateException("An loop of replacement was detected.");
    }
    return result;
  }

  public static int replaceEachRepeatedly(@Nullable final String str,
      @Nullable final String[] searches, @Nullable final String[] replaces,
      final boolean ignoreCase, final StringBuilder builder) {
    if ((str == null) || (str.length() == 0)) {
      return 0;
    }
    if ((searches == null)
        || (searches.length == 0)
        || (replaces == null)
        || (replaces.length == 0)) {
      builder.append(str);
      return 0;
    }
    requireEqual("searches.length", searches.length, "replaces.length", replaces.length);
    final boolean[] noMoreMatches = new boolean[searches.length];

    final StringBuilder tempBuilder = new StringBuilder();
    int totalModifies = 0;
    String text = str;
    for (int i = 0; i < searches.length; ++i) {
      final int modifies = replaceEachImpl(text, searches, replaces, ignoreCase,
          tempBuilder, noMoreMatches);
      if (modifies == 0) {
        break;
      }
      totalModifies += modifies;
      if (tempBuilder.length() == 0) {
        return totalModifies;
      }
      text = tempBuilder.toString();
      tempBuilder.setLength(0);
    }
    builder.append(text);
    // check for the loop
    if (replaceEachImpl(text, searches, replaces, ignoreCase, tempBuilder,
        noMoreMatches) > 0) {
      throw new IllegalStateException("An loop of replacement was detected.");
    }
    return totalModifies;
  }

  /**
   * Replaces all occurrences of Strings within another String.
   *
   * <p>A {@code null} reference passed to this method is a no-op, or if any
   * "search string" or "string to replace" is null, that replace will be
   * ignored.
   *
   * @param str
   *     text to search and replace in; it can't be null nor empty.
   * @param searches
   *     the strings to search for; it can't be null nor empty.
   * @param replaces
   *     the strings to replace them with; it can't be null nor empty, and it
   *     must have the same length as the {@code searches} array.
   * @param ignoreCase
   *     whether to ignore case while comparing strings.
   * @param builder
   *     the temporary {@link StringBuilder} used by this function.
   * @param noMoreMatches
   *     the temporary {@code boolean} array used by this function. It must have
   *     the same length as the {@code searches} array.
   * @return the text with any replacements processed.
   */
  private static int replaceEachImpl(final CharSequence str, final CharSequence[] searches,
      final CharSequence[] replaces, final boolean ignoreCase,
      final StringBuilder builder, final boolean[] noMoreMatches) {
    // assume searches.length == replaces.length == noMoreMathes.length
    final int searchLen = searches.length;
    // initialize the noMoreMatches array
    for (int i = 0; i < searchLen; ++i) {
      noMoreMatches[i] = false;
    }
    // start searching
    final int strLen = str.length();
    int modifies = 0;
    int start = 0;
    while (start < strLen) {
      // find the next earliest match
      int replaceIndex = -1;
      int replacePos = Integer.MAX_VALUE;
      for (int i = 0; i < searchLen; ++i) {
        if (noMoreMatches[i] || (searches[i] == null) || searches[i].isEmpty() || (replaces[i] == null)) {
          continue;
        }
        final int pos = firstIndexOf(str, start, strLen, searches[i], ignoreCase);
        // see if we need to keep searching for this
        if (pos == strLen) {
          noMoreMatches[i] = true;
        } else if (pos < replacePos) {
          replacePos = pos;
          replaceIndex = i;
        }
      }
      if (replaceIndex < 0) {
        // no more match found
        builder.append(str, start, strLen);
        break;
      }
      // otherwise, make the replacement
      if (start < replacePos) {
        builder.append(str, start, replacePos);
      }
      if (replaces[replaceIndex] != null) {
        builder.append(replaces[replaceIndex]);
      }
      start = replacePos + searches[replaceIndex].length();
      ++modifies;
    }
    return modifies;
  }

  /**
   * Gets the leftmost {@code len} characters of a string.
   *
   * <p>If {@code len} characters are not available, or the string is {@code
   * null},
   * the string will be returned without an exception. An exception is thrown if
   * len is negative.
   * <pre>
   * StringUtils.left(null, *)    = null
   * StringUtils.left(*, -ve)     = ""
   * StringUtils.left("", *)      = ""
   * StringUtils.left("abc", 0)   = ""
   * StringUtils.left("abc", 2)   = "ab"
   * StringUtils.left("abc", 4)   = "abc"
   * </pre>
   *
   * @param str
   *     the string to get the leftmost characters from, may be null
   * @param len
   *     the length of the required String, must be zero or positive
   * @return the leftmost characters, {@code null} if null String input
   */
  public static String left(@Nullable final String str, final int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(0, len);
  }

  /**
   * Gets the rightmost {@code len} characters of a string.
   *
   * <p>If {@code len} characters are not available, or the string is {@code
   * null},
   * the string will be returned without an an exception. An exception is thrown
   * if len is negative.
   * <pre>
   * StringUtils.right(null, *)    = null
   * StringUtils.right(*, -ve)     = ""
   * StringUtils.right("", *)      = ""
   * StringUtils.right("abc", 0)   = ""
   * StringUtils.right("abc", 2)   = "bc"
   * StringUtils.right("abc", 4)   = "abc"
   * </pre>
   *
   * @param str
   *     the string to get the rightmost characters from, may be null
   * @param len
   *     the length of the required String, must be zero or positive
   * @return the rightmost characters, {@code null} if null String input
   */
  public static String right(@Nullable final String str, final int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(str.length() - len);
  }

  /**
   * Gets {@code len} characters from the middle of a string.
   *
   * <p>If {@code len} characters are not available, the remainder of the
   * String
   * will be returned without an exception. If the string is {@code null},
   * {@code null} will be returned. An exception is thrown if len is negative.
   * <pre>
   * StringUtils.mid(null, *, *)    = null
   * StringUtils.mid(*, *, -ve)     = ""
   * StringUtils.mid("", 0, *)      = ""
   * StringUtils.mid("abc", 0, 2)   = "ab"
   * StringUtils.mid("abc", 0, 4)   = "abc"
   * StringUtils.mid("abc", 2, 4)   = "c"
   * StringUtils.mid("abc", 4, 2)   = ""
   * StringUtils.mid("abc", -2, 2)  = "ab"
   * </pre>
   *
   * @param str
   *     the string to get the characters from, may be null
   * @param pos
   *     the position to start from, negative treated as zero
   * @param len
   *     the length of the required String, must be zero or positive
   * @return the middle characters, {@code null} if null String input
   */
  public static String mid(@Nullable final String str, final int pos,
      final int len) {
    if (str == null) {
      return null;
    }
    if ((len < 0) || (pos > str.length())) {
      return EMPTY;
    }
    final int start = Math.max(0, pos);
    if (str.length() <= (start + len)) {
      return str.substring(start);
    }
    return str.substring(start, start + len);
  }

  /**
   * Gets a substring from the specified String avoiding exceptions.
   *
   * <p>A negative start position can be used to start {@code n} characters
   * from
   * the end of the string.
   *
   * <p>A {@code null} string will return {@code null}. An empty ("") String
   * will
   * return "".
   * <pre>
   * StringUtils.substring(null, *)   = null
   * StringUtils.substring("", *)     = ""
   * StringUtils.substring("abc", 0)  = "abc"
   * StringUtils.substring("abc", 2)  = "c"
   * StringUtils.substring("abc", 4)  = ""
   * StringUtils.substring("abc", -2) = "bc"
   * StringUtils.substring("abc", -4) = "abc"
   * </pre>
   *
   * @param str
   *     the string to get the substring from, may be null
   * @param pos
   *     the position to start from, negative means count back from the end of
   *     the string by this many characters
   * @return substring from start position, {@code null} if null String input
   */
  public static String substring(@Nullable final String str, final int pos) {
    if (str == null) {
      return null;
    }
    // handle negatives, which means last n characters
    int start = pos;
    if (start < 0) {
      start += str.length(); // remember start is negative
    }
    if (start < 0) {
      start = 0;
    }
    if (start > str.length()) {
      return EMPTY;
    }
    return str.substring(start);
  }

  /**
   * Gets a substring from the specified String avoiding exceptions.
   *
   * <p>A negative start position can be used to start/end {@code n} characters
   * from the end of the string.
   *
   * <p>The returned substring starts with the character in the {@code start}
   * position and ends before the {@code end} position. All position counting is
   * zero-based -- i.e., to start at the beginning of the string use {@code
   * start = 0}. Negative start and end positions can be used to specify offsets
   * relative to the end of the string.
   *
   * <p>If {@code start} is not strictly to the left of {@code end}, "" is
   * returned.
   * <pre>
   * StringUtils.substring(null, *, *)    = null
   * StringUtils.substring("", * ,  *)    = "";
   * StringUtils.substring("abc", 0, 2)   = "ab"
   * StringUtils.substring("abc", 2, 0)   = ""
   * StringUtils.substring("abc", 2, 4)   = "c"
   * StringUtils.substring("abc", 4, 6)   = ""
   * StringUtils.substring("abc", 2, 2)   = ""
   * StringUtils.substring("abc", -2, -1) = "b"
   * StringUtils.substring("abc", -4, 2)  = "ab"
   * </pre>
   *
   * @param str
   *     the string to get the substring from, may be null
   * @param startIndex
   *     the position to start from, negative means count back from the end of
   *     the string by this many characters
   * @param endIndex
   *     the position to end at (exclusive), negative means count back from the
   *     end of the string by this many characters
   * @return substring from start position to end positon, {@code null} if null
   *     String input
   */
  public static String substring(@Nullable final String str, final int startIndex,
      final int endIndex) {
    if (str == null) {
      return null;
    }
    // handle negatives
    int end = endIndex;
    if (end < 0) {
      end = str.length() + end; // remember end is negative
    }
    int start = startIndex;
    if (start < 0) {
      start = str.length() + start; // remember start is negative
    }
    // check length next
    if (end > str.length()) {
      end = str.length();
    }
    // if start is greater than end, return ""
    if (start > end) {
      return EMPTY;
    }
    if (start < 0) {
      start = 0;
    }
    if (end < 0) {
      end = 0;
    }
    return str.substring(start, end);
  }

  /**
   * Gets the substring before the first occurrence of a separator. The
   * separator is not returned.
   *
   * <p>A {@code null} string input will return {@code null}. An empty ("")
   * string
   * input will return the empty string. A {@code null} separator will return
   * the input string.
   * <pre>
   * StringUtils.substringBefore(null, *)      = null
   * StringUtils.substringBefore("", *)        = ""
   * StringUtils.substringBefore("abc", "a")   = ""
   * StringUtils.substringBefore("abcba", "b") = "a"
   * StringUtils.substringBefore("abc", "c")   = "ab"
   * StringUtils.substringBefore("abc", "d")   = "abc"
   * StringUtils.substringBefore("abc", "")    = ""
   * StringUtils.substringBefore("abc", null)  = "abc"
   * </pre>
   *
   * @param str
   *     the string to get a substring from, may be null
   * @param separator
   *     the string to search for, may be null
   * @return the substring before the first occurrence of the separator, {@code
   *     null} if null String input
   */
  public static String substringBefore(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0) || (separator == null)) {
      return str;
    }
    if (separator.length() == 0) {
      return EMPTY;
    }
    final int pos = str.indexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * Gets the substring after the first occurrence of a separator. The separator
   * is not returned.
   *
   * <p>A {@code null} string input will return {@code null}. An empty ("")
   * string
   * input will return the empty string. A {@code null} separator will return
   * the empty string if the input string is not {@code null}.
   * <pre>
   * StringUtils.substringAfter(null, *)      = null
   * StringUtils.substringAfter("", *)        = ""
   * StringUtils.substringAfter(*, null)      = ""
   * StringUtils.substringAfter("abc", "a")   = "bc"
   * StringUtils.substringAfter("abcba", "b") = "cba"
   * StringUtils.substringAfter("abc", "c")   = ""
   * StringUtils.substringAfter("abc", "d")   = ""
   * StringUtils.substringAfter("abc", "")    = "abc"
   * </pre>
   *
   * @param str
   *     the string to get a substring from, may be null
   * @param separator
   *     the string to search for, may be null
   * @return the substring after the first occurrence of the separator, {@code
   *     null} if null String input
   */
  public static String substringAfter(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0)) {
      return str;
    }
    if (separator == null) {
      return EMPTY;
    }
    final int pos = str.indexOf(separator);
    if (pos == -1) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  /**
   * Gets the substring before the last occurrence of a separator. The separator
   * is not returned.
   *
   * <p>A {@code null} string input will return {@code null}. An empty ("")
   * string
   * input will return the empty string. An empty or {@code null} separator will
   * return the input string.
   * <pre>
   * StringUtils.substringBeforeLast(null, *)      = null
   * StringUtils.substringBeforeLast("", *)        = ""
   * StringUtils.substringBeforeLast("abcba", "b") = "abc"
   * StringUtils.substringBeforeLast("abc", "c")   = "ab"
   * StringUtils.substringBeforeLast("a", "a")     = ""
   * StringUtils.substringBeforeLast("a", "z")     = "a"
   * StringUtils.substringBeforeLast("a", null)    = "a"
   * StringUtils.substringBeforeLast("a", "")      = "a"
   * </pre>
   *
   * @param str
   *     the string to get a substring from, may be null
   * @param separator
   *     the string to search for, may be null
   * @return the substring before the last occurrence of the separator, {@code
   *     null} if null String input
   */
  public static String substringBeforeLast(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0) || (separator == null)
        || (separator.length() == 0)) {
      return str;
    }
    final int pos = str.lastIndexOf(separator);
    if (pos == -1) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * Gets the substring after the last occurrence of a separator. The separator
   * is not returned.
   *
   * <p>A {@code null} string input will return {@code null}. An empty ("")
   * string
   * input will return the empty string. An empty or {@code null} separator will
   * return the empty string if the input string is not {@code null}.
   * <pre>
   * StringUtils.substringAfterLast(null, *)      = null
   * StringUtils.substringAfterLast("", *)        = ""
   * StringUtils.substringAfterLast("", "")        = ""
   * StringUtils.substringAfterLast(*, "")        = ""
   * StringUtils.substringAfterLast(*, null)      = ""
   * StringUtils.substringAfterLast("abc", "a")   = "bc"
   * StringUtils.substringAfterLast("abcba", "b") = "a"
   * StringUtils.substringAfterLast("abc", "c")   = ""
   * StringUtils.substringAfterLast("a", "a")     = ""
   * StringUtils.substringAfterLast("a", "z")     = ""
   * </pre>
   *
   * @param str
   *     the string to get a substring from, may be null
   * @param separator
   *     the string to search for, may be null
   * @return the substring after the last occurrence of the separator, {@code
   *     null} if null String input
   */
  public static String substringAfterLast(@Nullable final String str,
      @Nullable final String separator) {
    if ((str == null) || (str.length() == 0)) {
      return str;
    }
    if ((separator == null) || (separator.length() == 0)) {
      return EMPTY;
    }
    final int pos = str.lastIndexOf(separator);
    if ((pos == -1) || (pos == (str.length() - separator.length()))) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  /**
   * Gets the string that is nested in between two instances of the same
   * String.
   *
   * <p>A {@code null} input String returns {@code null}. A {@code null} tag
   * returns {@code null}.
   * <pre>
   * StringUtils.substringBetween(null, *)            = null
   * StringUtils.substringBetween("", "")             = ""
   * StringUtils.substringBetween("", "tag")          = null
   * StringUtils.substringBetween("tagabctag", null)  = null
   * StringUtils.substringBetween("tagabctag", "")    = ""
   * StringUtils.substringBetween("tagabctag", "tag") = "abc"
   * </pre>
   *
   * @param str
   *     the string containing the substring, may be null
   * @param tag
   *     the string before and after the substring, may be null
   * @return the substring, {@code null} if no match
   */
  public static String substringBetween(@Nullable final String str,
      @Nullable final String tag) {
    return substringBetween(str, tag, tag);
  }

  /**
   * Gets the string that is nested in between two strings. Only the first
   * match is returned.
   *
   * <p>A {@code null} input String returns {@code null}. A {@code null}
   * open/close returns {@code null} (no match). An empty ("") open and close
   * returns an empty string.
   * <pre>
   * StringUtils.substringBetween("[a][b][c]", "[", "]") = ["a","b","c"]
   * StringUtils.substringBetween(null, *, *)            = null
   * StringUtils.substringBetween(*, null, *)            = null
   * StringUtils.substringBetween(*, *, null)            = null
   * StringUtils.substringBetween("", "[", "]")          = []
   * </pre>
   *
   * @param str
   *     the string containing the substring, may be null
   * @param open
   *     the string before the substring, may be null
   * @param close
   *     the string after the substring, may be null
   * @return the substring, {@code null} if no match
   */
  public static String substringBetween(@Nullable final String str,
      @Nullable final String open, @Nullable final String close) {
    if ((str == null) || (open == null) || (close == null)) {
      return null;
    }
    final int start = str.indexOf(open);
    if (start != -1) {
      final int end = str.indexOf(close, start + open.length());
      if (end != -1) {
        return str.substring(start + open.length(), end);
      }
    }
    return null;
  }

  /**
   * Searches a string for substrings delimited by a start and end tag,
   * returning all matching substrings in an array.
   *
   * <p>A {@code null} input String returns {@code null}. A {@code null}
   * open/close
   * returns {@code null} (no match). An empty ("") open/close returns {@code
   * null} (no match).
   * <pre>
   * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
   * StringUtils.substringsBetween(null, *, *)            = null
   * StringUtils.substringsBetween(*, null, *)            = null
   * StringUtils.substringsBetween(*, *, null)            = null
   * StringUtils.substringsBetween("", "[", "]")          = []
   * </pre>
   *
   * @param str
   *     the string containing the substrings, null returns null, empty returns
   *     empty
   * @param open
   *     the string identifying the start of the substring, empty returns null
   * @param close
   *     the string identifying the end of the substring, empty returns null
   * @return a string Buffer of substrings, or {@code null} if no match
   */
  public static String[] substringsBetween(@Nullable final String str,
      @Nullable final String open, @Nullable final String close) {
    if ((str == null) || (open == null) || (open.length() == 0)
        || (close == null) || (close.length() == 0)) {
      return null;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    final int closeLen = close.length();
    final int openLen = open.length();
    final List<String> list = new ArrayList<>();
    int pos = 0;
    while (pos < (strLen - closeLen)) {
      int start = str.indexOf(open, pos);
      if (start < 0) {
        break;
      }
      start += openLen;
      final int end = str.indexOf(close, start);
      if (end < 0) {
        break;
      }
      list.add(str.substring(start, end));
      pos = end + closeLen;
    }
    if (list.isEmpty()) {
      return null;
    }
    return list.toArray(new String[0]);
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                   = null
   * StringUtils.join(*, [])                     = ""
   * StringUtils.join(';', [true, false, true])  = "true;false;true"
   * StringUtils.join(' ', [true, false, true])  = "true false true"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final boolean[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                   = null
   * StringUtils.join(*, [], *, *)                     = ""
   * StringUtils.join(';', [true, false, true], 0, 3)  = "true;false;true"
   * StringUtils.join(' ', [true, false, true], 0, 2)  = "true false"
   * </pre>
   *
   * @param separator
   *     the separator character to use.
   * @param array
   *     the array of values to join together, may be null.
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input.
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final boolean[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                   = null
   * StringUtils.join(*, [])                     = ""
   * StringUtils.join(';', ['a', 'b', 'c'])      = "a;b;c"
   * StringUtils.join(' ', ['a', 'b', 'c'])      = "a b c"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final char[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)              = null
   * StringUtils.join(*, [], *, *)                = ""
   * StringUtils.join(';', ['a', 'b', 'c'], 0, 3) = "a;b;c"
   * StringUtils.join(' ', ['a', 'b', 'c'], 0, 2) = "a b"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final char[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param array
   *     the array of values to join together, may be null
   * @param separator
   *     the separator character to use
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final byte[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1, 2, 3], 0, 3)  = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3], 0, 2)  = "1 2"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final byte[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final short[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1, 2, 3], 0, 3)  = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3], 0, 2)  = "1 2"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final short[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null *
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final int[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3], 0, 3)  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3], 0, 2)  = "1.1 2.2"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final int[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)             = null
   * StringUtils.join(*, [])               = ""
   * StringUtils.join(';', [1, 2, 3])      = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3])      = "1 2 3"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final long[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1, 2, 3], 0, 3)  = "1;2;3"
   * StringUtils.join(' ', [1, 2, 3], 0, 2)  = "1 2"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator, @Nullable final long[] array,
      final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                 = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3])  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3])  = "1.1 2.2 3.3"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final float[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3], 0, 3)  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3], 0, 2)  = "1.1 2.2"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final float[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                 = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3])  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3])  = "1.1 2.2 3.3"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final double[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)         = null
   * StringUtils.join(*, [], *, *)           = ""
   * StringUtils.join(';', [1.1, 2.2, 3.3], 0, 3)  = "1.1;2.2;3.3"
   * StringUtils.join(' ', [1.1, 2.2, 3.3], 0, 2)  = "1.1 2.2"
   * </pre>
   *
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. It is an error to pass in
   *     an end current past the end of the array.
   * @param endIndex
   *     the index to stop joining from (exclusive). It is an error to pass in
   *     an end current past the end of the array.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(final char separator,
      @Nullable final double[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)               = null
   * StringUtils.join(*, [])                 = ""
   * StringUtils.join(*, [null])             = ""
   * StringUtils.join(';', ["a", "b", "c"])  = "a;b;c"
   * StringUtils.join(' ', ["a", "b", "c"])  = "a b c"
   * StringUtils.join(';', [null, "", "a"])  = ";;a"
   * </pre>
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final T[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the array are represented by empty strings.
   *
   * <p>See the examples here: {@link #join(char, Object[])}.
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final T[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided {@code Iterator} into a single String
   * containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the iteration are represented by empty strings.
   *
   * <p>See the examples here: {@link #join(char, Object[])}.
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use
   * @param iterator
   *     the {@code Iterator} of values to join together, may be null
   * @return the joined String, {@code null} if null iterator input
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final Iterator<T> iterator) {
    return new Joiner(separator)
        .addAll(iterator)
        .toString();
  }

  /**
   * Joins the elements of the provided {@code Iterable} into a single String
   * containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. Null objects or empty
   * strings within the iteration are represented by empty strings.
   *
   * <p>See the examples here: {@link #join(char, Object[])}.
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use
   * @param iterable
   *     the {@code Iterable} of values to join together, may be null.
   * @return the joined String, {@code null} if null iterator input
   * @see Joiner
   */
  public static <T> String join(final char separator,
      @Nullable final Iterable<T> iterable) {
    return new Joiner(separator)
        .addAll(iterable)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final boolean[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final boolean[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final char[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final char[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final byte[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final byte[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final short[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final short[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final int[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final int[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final long[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final long[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final float[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final float[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final double[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first occurrence to start joining from. If it is less than zero, it
   *     is treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static String join(@Nullable final String separator,
      @Nullable final double[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null)                = null
   * StringUtils.join(*, [])                  = ""
   * StringUtils.join(*, [null])              = ""
   * StringUtils.join("--", ["a", "b", "c"])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c"])  = "abc"
   * StringUtils.join("", ["a", "b", "c"])    = "abc"
   * StringUtils.join(',', [null, "", "a"])   = ",,a"
   * </pre>
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final T[] array) {
    return new Joiner(separator)
        .addAll(array)
        .toString();
  }

  /**
   * Joins the elements of the provided array into a single String containing
   * the provided list of elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * <pre>
   * StringUtils.join(*, null, *, *)                = null
   * StringUtils.join(*, [], *, *)                  = ""
   * StringUtils.join(*, [null], *, *)              = ""
   * StringUtils.join("--", ["a", "b", "c", 0, 3])  = "a--b--c"
   * StringUtils.join(null, ["a", "b", "c", 0, 2])  = "ab"
   * StringUtils.join("", ["a", "b", "c", 1, 3])    = "bc"
   * StringUtils.join(',', [null, "", "a", 0, 3])   = ",,a"
   * </pre>
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use, null treated as an empty string.
   * @param array
   *     the array of values to join together, may be null
   * @param startIndex
   *     the first index to start joining from. If it is less than zero, it is
   *     treated as zero.
   * @param endIndex
   *     the index to stop joining from (exclusive). If it is greater than the
   *     length of the {@code array}, it is treated as the length of the {@code
   *     array}.
   * @return the joined String, {@code null} if null array input
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final T[] array, final int startIndex, final int endIndex) {
    return new Joiner(separator)
        .addAll(array, startIndex, endIndex)
        .toString();
  }

  /**
   * Joins the elements of the provided {@code Iterator} into a single String
   * containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String ("").
   *
   * <p>See the examples here: {@link #join(String, Object[])}.
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use, null treated as ""
   * @param iterator
   *     the {@code Iterator} of values to join together, may be null
   * @return the joined String, {@code null} if null iterator input
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final Iterator<T> iterator) {
    return new Joiner(separator)
        .addAll(iterator)
        .toString();
  }

  /**
   * Joins the elements of the provided {@code Iterable} into a single String
   * containing the provided elements.
   *
   * <p>No delimiter is added before or after the list. A {@code null} separator
   * is
   * the same as an empty String ("").
   *
   * <p>See the examples here: {@link #join(String, Object[])}.
   *
   * @param <T>
   *     the type of elements to be joined.
   * @param separator
   *     the separator character to use, null treated as ""
   * @param iterable
   *     the {@code Iterable} of values to join together, may be null
   * @return the joined String, {@code null} if null iterator input
   * @see Joiner
   */
  public static <T> String join(@Nullable final String separator,
      @Nullable final Iterable<T> iterable) {
    return new Joiner(separator)
        .addAll(iterable)
        .toString();
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified character. This is an alternative to using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *)         = null
   * StringUtils.split("", *)           = null
   * StringUtils.split("a.b.c", '.')    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.')   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.')    = {"a:b:c"}
   * StringUtils.split("a b c", ' ')    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separator
   *     the character used as the delimiter
   * @return a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final char separator) {
    return new Splitter()
        .byChar(separator)
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
  }

  public static @Nonnull String[] splitToArray(@Nullable final CharSequence str,
      final char separator) {
    return split(str, separator).toArray(new String[0]);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified character. This is an alternative to using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *, null)         = null
   * StringUtils.split("", *, null)           = null
   * StringUtils.split("a.b.c", '.', null)    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.', null)   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.', null)    = {"a:b:c"}
   * StringUtils.split("a b c", ' ', null)    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separator
   *     the character used as the delimiter
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @return a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final char separator, final int options) {
    return new Splitter()
        .byChar(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified character. This is an alternative to using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *, null, null)         = null
   * StringUtils.split("", *, null, null)           = null
   * StringUtils.split("a.b.c", '.', null, null)    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.', null, null)   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.', null, null)    = {"a:b:c"}
   * StringUtils.split("a b c", ' ', null, null)    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separator
   *     the character used as the delimiter
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @param list
   *     the string list where to append the split sub-strings. If it is null,
   *     the function will create a new list of strings. Not that if this
   *     argument is not {@code null}, it will <b>NOT</b> be cleared by this
   *     function. Instead, the splitting result will be append to this list.
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will append the split sub-strings in that argument
   *     and return that argument; otherwise, a new list of string is created
   *     and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final char separator, final int options,
      @Nullable final List<String> list) {
    return new Splitter()
        .byChar(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, list);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * characters of a specified string. This is an alternative to using
   * StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *)            = null
   * StringUtils.split("", *)              = null
   * StringUtils.split("a.b.c", ".")       = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ".:")   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", ",.")      = {"a:b:c"}
   * StringUtils.split("a b c.d", " .")    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separatorChars
   *     the characters used as the delimiters. If it is null or empty string,
   *     the string is not splited and the function will return a list
   *     containing the original string.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @return a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final CharSequence separatorChars, final int options) {
    return new Splitter()
        .byCodePointsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * characters of a specified string. This is an alternative to using
   * StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *, null, null)            = null
   * StringUtils.split("", *, null, null)              = null
   * StringUtils.split("a.b.c", ".", null, null)       = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ".:", null, null)   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", ",.", null, null)      = {"a:b:c"}
   * StringUtils.split("a b c.d", " .", null, null)    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separatorChars
   *     the characters used as the delimiters. If it is null or empty string,
   *     the string is not splited and the function will return a list
   *     containing the original string.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @param result
   *     the string list where to store the split sub-strings. If it is null,
   *     the function will create a new list of strings.
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new linked list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final CharSequence separatorChars, final int options,
      @Nullable final List<String> result) {
    return new Splitter()
        .byCodePointsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, result);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * characters of a specified array. This is an alternative to using
   * StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *)                  = null
   * StringUtils.split("", *)                    = null
   * StringUtils.split("a.b.c", ['.'])           = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ['.', ':'])   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", [',', '.'])      = {"a:b:c"}
   * StringUtils.split("a b c.d", [' ', '.'])    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separatorChars
   *     the characters used as the delimiters. If it is null or empty string,
   *     the string is not splited and the function will return a list
   *     containing the original string.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   *     the function will create a new list of strings.
   * @return  a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final char[] separatorChars, final int options) {
    return new Splitter()
        .byCharsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * characters of a specified array. This is an alternative to using
   * StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *, null)                  = null
   * StringUtils.split("", *, null)                    = null
   * StringUtils.split("a.b.c", ['.'], null)           = {"a", "b", "c"}
   * StringUtils.split("a..b.c:d", ['.', ':'], null)   = {"a", "b", "c", "d"}
   * StringUtils.split("a:b,c", [',', '.'], null)      = {"a:b:c"}
   * StringUtils.split("a b c.d", [' ', '.'], null)    = {"a", "b", "c", "d"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separatorChars
   *     the characters used as the delimiters. If it is null or empty string,
   *     the string is not splited and the function will return a list
   *     containing the original string.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @param result
   *     the string list where to store the split sub-strings. If it is null,
   *     the function will create a new list of strings.
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new linked list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final char[] separatorChars, final int options,
      @Nullable final List<String> result) {
    return new Splitter()
        .byCharsIn(separatorChars)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, result);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * characters accepted by a specified CharFilter. This is an alternative to
   * using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *)         = null
   * StringUtils.split("", *)           = null
   * StringUtils.split("a.b.c", '.')    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.')   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.')    = {"a:b:c"}
   * StringUtils.split("a b c", ' ')    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param charFilter
   *     a filter used to filter characters. The function will split the string
   *     by all characters accepted by this filter. This argument can't be
   *     null.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @return a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final CharFilter charFilter, final int options) {
    return new Splitter()
        .byCharsSatisfy(charFilter)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * characters accepted by a specified CharFilter. This is an alternative to
   * using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, *, null)         = null
   * StringUtils.split("", *, null)           = null
   * StringUtils.split("a.b.c", '.', null)    = {"a", "b", "c"}
   * StringUtils.split("a..b.c", '.', null)   = {"a", "b", "c"}
   * StringUtils.split("a:b:c", '.', null)    = {"a:b:c"}
   * StringUtils.split("a b c", ' ', null)    = {"a", "b", "c"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param charFilter
   *     a filter used to filter characters. The function will split the string
   *     by all characters accepted by this filter. This argument can't be
   *     null.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @param list
   *     the string list where to store the split sub-strings. If it is null,
   *     the function will create a new list of strings.
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new linked list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      final CharFilter charFilter, final int options,
      @Nullable final List<String> list) {
    return new Splitter()
        .byCharsSatisfy(charFilter)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, list);
  }

  /**
   * Splits the provided string into a list of sub-strings, separated by
   * whitespaces. This is an alternative to using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null)       = null
   * StringUtils.split("")         = null
   * StringUtils.split("abc def")  = {"abc", "def"}
   * StringUtils.split("abc  def") = {"abc", "def"}
   * StringUtils.split(" abc ")    = {"abc"}
   * </pre>
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @return a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see Splitter
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str) {
    return new Splitter()
        .byWhitespaces()
        .split(str);
  }

  public static @Nonnull String[] splitToArray(@Nullable final CharSequence str) {
    return split(str).toArray(new String[0]);
  }

  /**
   * Splits the provided string into a list of sub-strings, separated by
   * whitespaces. This is an alternative to using StringTokenizer.
   *
   * <p>The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split
   * use the StrTokenizer class.
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.split(null, null)       = null
   * StringUtils.split("", null)         = null
   * StringUtils.split("abc def", null)  = {"abc", "def"}
   * StringUtils.split("abc  def", null) = {"abc", "def"}
   * StringUtils.split(" abc ", null)    = {"abc"}
   * </pre>
   *
   * @param str
   *     the string to splited. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param list
   *     the string list where to store the split sub-strings. If it is null,
   *     the function will create a new list of strings.
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new linked list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   */
  public static @Nonnull List<String> split(@Nullable final CharSequence str,
      @Nullable final List<String> list) {
    return new Splitter()
        .byWhitespaces()
        .split(str, list);
  }

  /**
   * Splits a string by Character type as returned by {@code
   * java.lang.Character.getType(char)}. Groups of contiguous characters of the
   * same type are returned as complete tokens, with the following exception:
   *
   * <p>If {@code camelCase} is {@code true}, the character of type
   * {@code Character.UPPERCASE_LETTER}, if any, immediately preceding a token
   * of type {@code Character.LOWERCASE_LETTER} will belong to the following
   * token rather than to the preceding, if any, {@code Character.UPPERCASE_LETTER}
   * token.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.splitByCharType(null, *)             = null
   * StringUtils.splitByCharType("", *)               = null
   * StringUtils.splitByCharType("ab   de fg", *)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab   de fg", *)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab:cd:ef", *)       = {"ab", ":", "cd", ":", "ef"}
   * StringUtils.splitByCharType("number5", *)        = {"number", "5"}
   * StringUtils.splitByCharType("fooBar", false)     = {"foo", "B", "ar"}
   * StringUtils.splitByCharType("fooBar", true)      = {"foo", "Bar"}
   * StringUtils.splitByCharType("foo200Bar", false)  = {"foo", "200", "B", "ar"}
   * StringUtils.splitByCharType("foo200Bar", true)   = {"foo", "200", "Bar"}
   * StringUtils.splitByCharType("ASFRules", false)   = {"ASFR", "ules"}
   * StringUtils.splitByCharType("ASFRules", true)    = {"ASF", "Rules"}
   * </pre>
   *
   * @param str
   *     the string to split, may be {@code null}
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}。
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> splitByCharType(@Nullable final CharSequence str,
      final int options) {
    return new Splitter()
        .byCharTypes()
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .camelCase((options & CAMEL_CASE) != 0)
        .split(str);
  }

  /**
   * Splits a string by Character type as returned by {@code
   * java.lang.Character.getType(char)}. Groups of contiguous characters of the
   * same type are returned as complete tokens, with the following exception:
   *
   * <p>If {@code camelCase} is {@code true}, the character of type
   * {@code Character.UPPERCASE_LETTER}, if any, immediately preceding a token
   * of type {@code Character.LOWERCASE_LETTER} will belong to the following
   * token rather than to the preceding, if any, {@code Character.UPPERCASE_LETTER}
   * token.
   *
   * <p>Examples:
   * <pre>
   * StringUtils.splitByCharType(null, *, null)             = null
   * StringUtils.splitByCharType("", *, null)               = null
   * StringUtils.splitByCharType("ab   de fg", *, null)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab   de fg", *, null)     = {"ab", "   ", "de", " ", "fg"}
   * StringUtils.splitByCharType("ab:cd:ef", *, null)       = {"ab", ":", "cd", ":", "ef"}
   * StringUtils.splitByCharType("number5", *, null)        = {"number", "5"}
   * StringUtils.splitByCharType("fooBar", false, null)     = {"foo", "B", "ar"}
   * StringUtils.splitByCharType("fooBar", true, null)      = {"foo", "Bar"}
   * StringUtils.splitByCharType("foo200Bar", false, null)  = {"foo", "200", "B", "ar"}
   * StringUtils.splitByCharType("foo200Bar", true, null)   = {"foo", "200", "Bar"}
   * StringUtils.splitByCharType("ASFRules", false, null)   = {"ASFR", "ules"}
   * StringUtils.splitByCharType("ASFRules", true, null)    = {"ASF", "Rules"}
   * </pre>
   *
   * @param str
   *     the string to split, may be {@code null}
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}。
   * @param list
   *     the string list where to store the split sub-strings. If it is null,
   *     the function will create a new list of strings.
   * @return a list of split strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new linked list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> splitByCharType(@Nullable final CharSequence str,
      final int options, @Nullable final List<String> list) {
    return new Splitter()
        .byCharTypes()
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .camelCase((options & CAMEL_CASE) != 0)
        .split(str, list);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the a
   * specified sub-string. This is an alternative to using StringTokenizer.
   *
   * <p>The separator(s) will not be included in the returned String array.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * splitByString(null, *)                             = null
   * splitByString("", *)                               = null
   * splitByString("ab de fg", " ")                     = {"ab", "de", "fg"}
   * splitByString("ab-!-  cd-!-  -!-ef", "-!-")        = {"ab", "cd", "ef"}
   * </pre>
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separator
   *     the string to be used as the delimiter. If it is null or empty, the
   *     string is split such that each character become a substring.
   * @return a new linked list of string stores the split sub-strings. Each
   *     split substring will be stripped and the empty strings will be
   *     ignored. Note that the returned list may be empty if the splitting
   *     result has no non-blank substring. The returned list will never be
   *     {@code null}.
   * @see Splitter
   */
  public static @Nonnull List<String> splitByString(@Nullable final CharSequence str,
      @Nullable final CharSequence separator) {
    return new Splitter()
        .bySubstring(separator)
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
  }

  public static @Nonnull String[] splitByStringToArray(@Nullable final CharSequence str,
      @Nullable final CharSequence separator) {
    return splitByString(str, separator).toArray(new String[0]);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the a
   * specified sub-string. This is an alternative to using StringTokenizer.
   *
   * <p>The separator(s) will not be included in the returned String array.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * splitByString(null, *, *, *)                             = null
   * splitByString("", *, *, *)                               = null
   * splitByString("ab de fg", " ", *, *)                     = {"ab", "de", "fg"}
   * splitByString("ab   de fg", " ", *, false)               = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", true, false)         = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", false, false)        = {"ab", "", "  ", "de ", "fg"}
   * splitByString("ab-!-  cd-!-  -!-ef", "-!-", true, true)  = {"ab", "cd", "ef"}
   * </pre>
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separator
   *     the string to be used as the delimiter. If it is null or empty, the
   *     string is split such that each character become a substring.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @return a new linked list of string stores the split sub-strings. Note that
   *     the returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   * @see Splitter
   */
  public static @Nonnull List<String> splitByString(@Nullable final CharSequence str,
      @Nullable final CharSequence separator, final int options) {
    return new Splitter()
        .bySubstring(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str);
  }

  /**
   * Splits the provided string into a list of substrings, separated by the a
   * specified sub-string. This is an alternative to using StringTokenizer.
   *
   * <p>The separator(s) will not be included in the returned String array.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * splitByString(null, *, *, *, *)                  = null
   * splitByString("", *, *, *, null)                 = null
   * splitByString("ab de fg", " ", *, *, null)       = {"ab", "de", "fg"}
   * splitByString("ab   de fg", " ", *, false, null) = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", true, false, null) = {"ab", "", "", "de", "fg"}
   * splitByString("ab;;  ;de ;fg", ";", false, false, null) = {"ab", "", "  ", "de ", "fg"}
   * splitByString("ab-!-  cd-!-  -!-ef", "-!-", true, true, null) = {"ab", "cd", "ef"}
   * </pre>
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param separator
   *     the string to be used as the delimiter. If it is null or empty, the
   *     string is split such that each character become a substring.
   * @param options
   *     a bitwise mask of combination of {@link SplitOption}.
   * @param list
   *     the string list where to store the split sub-strings. If it is null,
   *     the function will create a new list of strings.
   * @return an list of splitted strings. If the argument result is not {@code
   *     null}, the function will store the split sub-strings in that argument
   *     and return that argument; otherwise, a new linked list of string is
   *     created and stores the split sub-strings and is returned. Note that the
   *     returned list may be empty if the splitting result has no substring.
   *     The returned list will never be {@code null}.
   * @see SplitOption
   */
  public static @Nonnull List<String> splitByString(@Nullable final CharSequence str,
      @Nullable final CharSequence separator, final int options,
      @Nullable final List<String> list) {
    return new Splitter()
        .bySubstring(separator)
        .strip((options & TRIM) != 0)
        .ignoreEmpty((options & IGNORE_EMPTY) != 0)
        .split(str, list);
  }

  /**
   * Splits the provided string into a list of substrings, separated by lines.
   *
   * <p>The line break may be "\r", "\r\n", or "\n".
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @return the list of split strings. Each string in the list will be stripped,
   *     and the empty strings will be ignored. The returned list may be empty
   *     if the splitting result has no non-blank substring. The returned list
   *     will never be {@code null}.
   * @see Splitter
   */
  public static @Nonnull List<String> splitLines(@Nullable final CharSequence str) {
    return new Splitter()
        .toLines()
        .strip(true)
        .ignoreEmpty(true)
        .split(str);
  }

  public static @Nonnull String[] splitLinesToArray(@Nullable final CharSequence str) {
    return splitLines(str).toArray(new String[0]);
  }

  /**
   * Splits the provided string into a list of substrings, separated by lines.
   *
   * <p>The line break may be "\r", "\r\n", or "\n".
   *
   * <p>A {@code null} input String returns {@code null}.
   *
   * @param str
   *     the string to split. If it is null, clear the result (if it is not
   *     {@code null}) and return the result.
   * @param trim
   *     whether to trim the split substrings.
   * @param ignoreEmpty
   *     whether to ignore the empty substring in the returned list.
   * @return an list of splitted strings. The returned list may be empty if the
   *     splitting result has no substring. The returned list will never be
   *     {@code null}.
   */
  public static @Nonnull List<String> splitLines(@Nullable final CharSequence str,
      final boolean trim, final boolean ignoreEmpty) {
    return new Splitter()
        .toLines()
        .strip(trim)
        .ignoreEmpty(ignoreEmpty)
        .split(str);
  }

  /**
   * Overlays part of a string with another String.
   *
   * <p>A {@code null} string input returns {@code null}. A negative current is
   * treated as zero. An current greater than the string length is treated as
   * the string length. The start current is always the smaller of the two
   * indices.
   * <pre>
   * StringUtils.overlay(null, *, *, *)            = null
   * StringUtils.overlay("", "abc", 0, 0)          = "abc"
   * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
   * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
   * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
   * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
   * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
   * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
   * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
   * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
   * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
   * </pre>
   *
   * @param str
   *     the string to do overlaying in, may be null
   * @param overlay
   *     the string to overlay, may be null
   * @param startIndex
   *     the position to start overlaying at
   * @param endIndex
   *     the position to stop overlaying before
   * @return overlayed String, {@code null} if null String input
   */
  public static String overlay(@Nullable final String str,
      @Nullable final String overlay, final int startIndex, final int endIndex) {
    if (str == null) {
      return null;
    }
    final String theOverlay = defaultIfNull(overlay, EMPTY);
    final int len = str.length();
    int start = startIndex;
    if (start < 0) {
      start = 0;
    }
    if (start > len) {
      start = len;
    }
    int end = endIndex;
    if (end < 0) {
      end = 0;
    }
    if (end > len) {
      end = len;
    }
    if (start > end) {
      final int temp = start;
      start = end;
      end = temp;
    }
    final int n = ((len + start) - end) + theOverlay.length() + 1;
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(n);
    builder.append(str, 0, start)
        .append(theOverlay)
        .append(str, end, str.length());
    return builder.toString();
  }

  /**
   * Repeat a string {@code repeat} times to form a new String.
   * <pre>
   * StringUtils.repeat(null, 2) = null
   * StringUtils.repeat("", 0)   = ""
   * StringUtils.repeat("", 2)   = ""
   * StringUtils.repeat("a", 3)  = "aaa"
   * StringUtils.repeat("ab", 2) = "abab"
   * StringUtils.repeat("a", -2) = ""
   * </pre>
   *
   * @param str
   *     the string to repeat, may be null
   * @param repeat
   *     number of times to repeat str, negative treated as zero
   * @return a new String consisting of the original String repeated, {@code
   *     null} if null String input
   */
  public static String repeat(@Nullable final String str, final int repeat) {
    if (str == null) {
      return null;
    }
    if (repeat <= 0) {
      return EMPTY;
    }
    final int inputLength = str.length();
    if ((repeat == 1) || (inputLength == 0)) {
      return str;
    }
    if ((inputLength == 1) && (repeat <= PAD_LIMIT)) {
      return padding(repeat, str.charAt(0));
    }

    final int outputLength = inputLength * repeat;
    switch (inputLength) {
      case 1:
        final char ch = str.charAt(0);
        final char[] output1 = new char[outputLength];
        for (int i = repeat - 1; i >= 0; --i) {
          output1[i] = ch;
        }
        return new String(output1);
      case 2:
        final char ch0 = str.charAt(0);
        final char ch1 = str.charAt(1);
        final char[] output2 = new char[outputLength];
        for (int i = (repeat * 2) - 2; i >= 0; i -= 2) {
          output2[i] = ch0;
          output2[i + 1] = ch1;
        }
        return new String(output2);
      default:
        final StringBuilder builder = new StringBuilder();
        builder.ensureCapacity(outputLength);
        for (int i = 0; i < repeat; i++) {
          builder.append(str);
        }
        return builder.toString();
    }
  }

  /**
   * Returns padding using the specified delimiter repeated to a given length.
   * <pre>
   * StringUtils.padding(0, 'e')  = ""
   * StringUtils.padding(3, 'e')  = "eee"
   * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
   * </pre>
   *
   * <p>Note: this method doesn't not support padding with <a
   * href="http://www.unicode.org/glossary/#supplementary_character">Unicode
   * Supplementary Characters</a> as they require a pair of {@code char}s to be
   * represented. If you are needing to support full I18N of your applications
   * consider using {@link #repeat(String, int)} instead.
   *
   * @param repeat
   *     number of times to repeat delim
   * @param padChar
   *     character to repeat
   * @return String with repeated character
   * @throws IndexOutOfBoundsException
   *     if {@code repeat &lt; 0}
   * @see #repeat(String, int)
   */
  private static String padding(final int repeat, final char padChar)
      throws IndexOutOfBoundsException {
    if (repeat < 0) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: "
          + repeat);
    }
    final char[] buffer = new char[repeat];
    Arrays.fill(buffer, padChar);
    return new String(buffer);
  }

  /**
   * Right pad a string with a specified character.
   *
   * <p>The String is padded to the size of {@code size}.
   * <pre>
   * StringUtils.rightPad(null, *, *)     = null
   * StringUtils.rightPad("", 3, 'z')     = "zzz"
   * StringUtils.rightPad("bat", 3, 'z')  = "bat"
   * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
   * StringUtils.rightPad("bat", 1, 'z')  = "bat"
   * StringUtils.rightPad("bat", -1, 'z') = "bat"
   * </pre>
   *
   * @param str
   *     the string to pad out, may be null
   * @param size
   *     the size to pad to.
   * @param padChar
   *     the character to pad with
   * @return right padded String or original String if no padding is necessary,
   *     {@code null} if null String input
   */
  public static String rightPad(@Nullable final String str, final int size,
      final char padChar) {
    if (str == null) {
      return null;
    }
    final int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (pads > PAD_LIMIT) {
      return rightPad(str, size, String.valueOf(padChar));
    }
    return str.concat(padding(pads, padChar));
  }

  /**
   * Right pad a string with a specified String.
   *
   * <p>The String is padded to the size of {@code size}.
   * <pre>
   * StringUtils.rightPad(null, *, *, *)      = null
   * StringUtils.rightPad("", 3, "z", *)      = "zzz"
   * StringUtils.rightPad("bat", 3, "yz", *)  = "bat"
   * StringUtils.rightPad("bat", 5, "yz", *)  = "batyz"
   * StringUtils.rightPad("bat", 8, "yz", *)  = "batyzyzy"
   * StringUtils.rightPad("bat", 1, "yz", *)  = "bat"
   * StringUtils.rightPad("bat", -1, "yz", *) = "bat"
   * StringUtils.rightPad("bat", 5, null, *)  = "bat  "
   * StringUtils.rightPad("bat", 5, "", *)    = "bat  "
   * </pre>
   *
   * @param str
   *     the string to pad out, may be null
   * @param size
   *     the size to pad to
   * @param padStr
   *     the string to pad with, null or empty treated as single space
   * @return right padded String or original String if no padding is necessary,
   *     {@code null} if null String input
   */
  public static String rightPad(@Nullable final String str, final int size,
      @Nullable final String padStr) {
    if (str == null) {
      return null;
    }
    final String thePadStr;
    if ((padStr == null) || (padStr.length() == 0)) {
      thePadStr = SPACE;
    } else {
      thePadStr = padStr;
    }
    final int padLen = thePadStr.length();
    final int strLen = str.length();
    final int pads = size - strLen;
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if ((padLen == 1) && (pads <= PAD_LIMIT)) {
      return rightPad(str, size, thePadStr.charAt(0));
    }

    if (pads == padLen) {
      return str.concat(thePadStr);
    } else if (pads < padLen) {
      return str.concat(thePadStr.substring(0, pads));
    } else {
      final char[] padding = new char[pads];
      final char[] padChars = thePadStr.toCharArray();
      for (int i = 0; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return str.concat(new String(padding));
    }
  }

  /**
   * Left pad a string with a specified character.
   *
   * <p>Pad to a size of {@code size}.
   * <pre>
   * StringUtils.leftPad(null, *, *)     = null
   * StringUtils.leftPad("", 3, 'z')     = "zzz"
   * StringUtils.leftPad("bat", 3, 'z')  = "bat"
   * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
   * StringUtils.leftPad("bat", 1, 'z')  = "bat"
   * StringUtils.leftPad("bat", -1, 'z') = "bat"
   * </pre>
   *
   * @param str
   *     the string to pad out, may be null
   * @param size
   *     the size to pad to
   * @param padChar
   *     the character to pad with
   * @return left padded String or original String if no padding is necessary,
   *     {@code null} if null String input
   */
  public static String leftPad(@Nullable final String str, final int size,
      final char padChar) {
    if (str == null) {
      return null;
    }
    final int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (pads > PAD_LIMIT) {
      return leftPad(str, size, String.valueOf(padChar));
    }
    return padding(pads, padChar).concat(str);
  }

  /**
   * Left pad a string with a specified String.
   *
   * <p>Pad to a size of {@code size}.
   * <pre>
   * StringUtils.leftPad(null, *, *)      = null
   * StringUtils.leftPad("", 3, "z")      = "zzz"
   * StringUtils.leftPad("bat", 3, "yz")  = "bat"
   * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
   * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
   * StringUtils.leftPad("bat", 1, "yz")  = "bat"
   * StringUtils.leftPad("bat", -1, "yz") = "bat"
   * StringUtils.leftPad("bat", 5, null)  = "  bat"
   * StringUtils.leftPad("bat", 5, "")    = "  bat"
   * </pre>
   *
   * @param str
   *     the string to pad out, may be null
   * @param size
   *     the size to pad to
   * @param padStr
   *     the string to pad with, null or empty treated as single space
   * @return left padded String or original String if no padding is necessary,
   *     {@code null} if null String input
   */
  public static String leftPad(@Nullable final String str, final int size,
      @Nullable final String padStr) {
    if (str == null) {
      return null;
    }
    final String thePadStr;
    if ((padStr == null) || (padStr.length() == 0)) {
      thePadStr = SPACE;
    } else {
      thePadStr = padStr;
    }
    final int padLen = thePadStr.length();
    final int strLen = str.length();
    final int pads = size - strLen;
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if ((padLen == 1) && (pads <= PAD_LIMIT)) {
      return leftPad(str, size, thePadStr.charAt(0));
    }
    if (pads == padLen) {
      return thePadStr.concat(str);
    } else if (pads < padLen) {
      return thePadStr.substring(0, pads).concat(str);
    } else {
      final char[] padding = new char[pads];
      final char[] padChars = thePadStr.toCharArray();
      for (int i = 0; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return new String(padding).concat(str);
    }
  }

  /**
   * Centers a string in a larger String of size {@code size}. Uses a supplied
   * character as the value to pad the string with.
   *
   * <p>If the size is less than the string length, the string is returned. A
   * {@code null} string returns {@code null}. A negative size is treated as
   * zero.
   * <pre>
   * StringUtils.center(null, *, *)     = null
   * StringUtils.center("", 4, ' ')     = "    "
   * StringUtils.center("ab", -1, ' ')  = "ab"
   * StringUtils.center("ab", 4, ' ')   = " ab"
   * StringUtils.center("abcd", 2, ' ') = "abcd"
   * StringUtils.center("a", 4, ' ')    = " a  "
   * StringUtils.center("a", 4, 'y')    = "yayy"
   * </pre>
   *
   * @param str
   *     the string to center, may be null
   * @param size
   *     the int size of new String, negative treated as zero
   * @param padChar
   *     the character to pad the new String with
   * @return centered String, {@code null} if null String input
   */
  public static String center(@Nullable final String str, final int size,
      final char padChar) {
    if ((str == null) || (size <= 0)) {
      return str;
    }
    final int len = str.length();
    final int pads = size - len;
    if (pads <= 0) {
      return str;
    }
    String result = str;
    result = leftPad(result, len + (pads / 2), padChar);
    result = rightPad(result, size, padChar);
    return result;
  }

  /**
   * Centers a string in a larger String of size {@code size}. Uses a supplied
   * String as the value to pad the string with.
   *
   * <p>If the size is less than the string length, the string is returned. A
   * {@code null} string returns {@code null}. A negative size is treated as
   * zero.
   * <pre>
   * StringUtils.center(null, *, *)     = null
   * StringUtils.center("", 4, " ")     = "    "
   * StringUtils.center("ab", -1, " ")  = "ab"
   * StringUtils.center("ab", 4, " ")   = " ab"
   * StringUtils.center("abcd", 2, " ") = "abcd"
   * StringUtils.center("a", 4, " ")    = " a  "
   * StringUtils.center("a", 4, "yz")   = "yayz"
   * StringUtils.center("abc", 7, null) = "  abc  "
   * StringUtils.center("abc", 7, "")   = "  abc  "
   * </pre>
   *
   * @param str
   *     the string to center, may be null
   * @param size
   *     the int size of new String, negative treated as zero
   * @param padding
   *     the string to pad the new String with, must not be null or empty
   * @return centered String, {@code null} if null String input
   * @throws IllegalArgumentException
   *     if padStr is {@code null} or empty
   */
  public static String center(@Nullable final String str, final int size,
      @Nullable final String padding) {
    if ((str == null) || (size <= 0)) {
      return str;
    }
    final String padStr;
    if ((padding == null) || (padding.length() == 0)) {
      padStr = " ";
    } else {
      padStr = padding;
    }
    final int strLen = str.length();
    final int pads = size - strLen;
    if (pads <= 0) {
      return str;
    }
    String result = leftPad(str, strLen + (pads / 2), padStr);
    result = rightPad(result, size, padStr);
    return result;
  }

  /**
   * Converts a string to upper case as per {@link String#toUpperCase()}.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * StringUtils.upperCase(null)  = null
   * StringUtils.upperCase("")    = ""
   * StringUtils.upperCase("aBc") = "ABC"
   * </pre>
   *
   * <p>FIXME: the implementation is not perfect for all languages.
   *
   * @param str
   *     the string to upper case, may be null
   * @return the upper cased String, {@code null} if null String input
   */
  public static String toUpperCase(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    return str.toUpperCase();
  }

  /**
   * Converts a string to lower case as per {@link String#toLowerCase()}.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * StringUtils.lowerCase(null)  = null
   * StringUtils.lowerCase("")    = ""
   * StringUtils.lowerCase("aBc") = "abc"
   * </pre>
   *
   * <p>FIXME: the implementation is not perfect for all langauges. Need a more
   * prefect implementation.
   *
   * @param str
   *     the string to lower case, may be null
   * @return the lower cased String, {@code null} if null String input
   */
  public static String toLowerCase(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    return str.toLowerCase();
  }

  /**
   * Capitalizes a string changing the first letter to title case as per {@link
   * Character#toTitleCase(char)}. No other letters are changed.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * StringUtils.capitalize(null)  = null
   * StringUtils.capitalize("")    = ""
   * StringUtils.capitalize("cat") = "Cat"
   * StringUtils.capitalize("cAt") = "CAt"
   * </pre>
   *
   * <p>FIXME: the implementation is not perfect for all langauges. Need a more
   * prefect implementation.
   *
   * @param str
   *     the string to capitalize, may be null
   * @return the capitalized String, {@code null} if null String input
   * @see #uncapitalize(String)
   */
  public static String capitalize(@Nullable final String str) {
    final int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return str;
    }
    final char ch = Character.toTitleCase(str.charAt(0));
    if (strLen == 1) {
      return CharUtils.toString(ch);
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    builder.append(ch).append(str, 1, strLen); // recall that strLen > 1
    return builder.toString();
  }

  /**
   * Uncapitalizes a string changing the first letter to title case as per
   * {@link Character#toLowerCase(char)}. No other letters are changed.
   *
   * <p>A {@code null} input String returns {@code null}.
   * <pre>
   * StringUtils.uncapitalize(null)  = null
   * StringUtils.uncapitalize("")    = ""
   * StringUtils.uncapitalize("Cat") = "cat"
   * StringUtils.uncapitalize("CAT") = "cAT"
   * </pre>
   *
   * <p>FIXME: the implementation is not perfect for all languages.
   *
   * @param str
   *     the string to uncapitalize, may be null
   * @return the uncapitalized String, {@code null} if null String input
   * @see #capitalize(String)
   */
  public static String uncapitalize(@Nullable final String str) {
    final int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return str;
    }
    final char ch = Character.toLowerCase(str.charAt(0));
    if (strLen == 1) {
      return CharUtils.toString(ch);
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    builder.append(ch).append(str, 1, strLen); // recall that strLen > 1
    return builder.toString();
  }

  /**
   * Swaps the case of a string changing upper and title case to lower case, and
   * lower case to upper case.
   * <ul>
   * <li>Upper case character converts to Lower case</li>
   * <li>Title case character converts to Lower case</li>
   * <li>Lower case character converts to Upper case</li>
   * </ul>
   * A {@code null} input String returns {@code null}.
   * <pre>
   * StringUtils.swapCase(null)                 = null
   * StringUtils.swapCase("")                   = ""
   * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
   * </pre>
   *
   * @param str
   *     the string to swap case, may be null
   * @return the changed String, {@code null} if null String input
   */
  public static String swapCase(@Nullable final String str) {
    final int strLen;
    if ((str == null) || ((strLen = str.length()) == 0)) {
      return str;
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    for (int i = 0; i < strLen; i++) {
      char ch = str.charAt(i);
      if (Character.isUpperCase(ch)) {
        ch = Character.toLowerCase(ch);
      } else if (Character.isTitleCase(ch)) {
        ch = Character.toLowerCase(ch);
      } else if (Character.isLowerCase(ch)) {
        ch = Character.toUpperCase(ch);
      }
      builder.append(ch);
    }
    return builder.toString();
  }

  /**
   * Reverses a string as per {@link StringBuilder#reverse()}.
   *
   * <p>A {@code null} string returns {@code null}.
   * <pre>
   * StringUtils.reverse(null)  = null
   * StringUtils.reverse("")    = ""
   * StringUtils.reverse("bat") = "tab"
   * </pre>
   *
   * @param str
   *     the string to reverse, may be null
   * @return the reversed String, {@code null} if null String input
   */
  public static String reverse(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    final int strLen;
    if ((strLen = str.length()) < 2) {
      return str;
    }
    final StringBuilder builder = new StringBuilder();
    builder.ensureCapacity(strLen);
    return builder.append(str).reverse().toString();
  }

  /**
   * Abbreviates a string using ellipses. This will turn "Now is the time for
   * all good men" into "...is the time for..."
   *
   * <p>This function allows you to specify a "left edge" offset. Note that
   * this left edge is not necessarily going to be the leftmost character in the
   * result, or the first character following the ellipses, but it will appear
   * somewhere in the result.
   *
   * <p>Specifically:
   * <ul>
   * <li>If {@code str} is less than {@code maxWidth} characters long, return
   * it.</li>
   * <li>Else abbreviate it to {@code (substring(str, 0, max-3) + "...")}.</li>
   * <li>If {@code maxWidth} is less than {@code 4}, throw an
   * {@code IllegalArgumentException}.</li>
   * <li>In no case will it return a string of length greater than
   * {@code maxWidth}.</li>
   * </ul>
   * In no case will it return a string of length greater than {@code maxWidth}.
   * <pre>
   * StringUtils.abbreviate(null, *, *)                = null
   * StringUtils.abbreviate("", 0, 4)                  = ""
   * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
   * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
   * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
   * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
   * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
   * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
   * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
   * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
   * </pre>
   *
   * <p>FIXME: this function is badly designed.
   *
   * @param str
   *     the string to check, may be null
   * @param offset
   *     left edge of source String
   * @param maxWidth
   *     maximum length of result String, must be at least 4
   * @return abbreviated String, {@code null} if null String input
   * @throws IllegalArgumentException
   *     if the width is too small
   */
  public static String abbreviate(@Nullable final String str, final int offset,
      final int maxWidth) {
    if (str == null) {
      return null;
    }
    if (maxWidth < MIN_ABBREV_WIDTH) {
      throw new IllegalArgumentException("Minimum abbreviation width is "
          + MIN_ABBREV_WIDTH);
    }
    if (str.length() <= maxWidth) {
      return str;
    }
    int off = offset;
    if (off > str.length()) {
      off = str.length();
    }
    if ((str.length() - off) < (maxWidth - ELLIPSES_LENGTH)) {
      off = str.length() - (maxWidth - ELLIPSES_LENGTH);
    }
    if (off <= MIN_ABBREV_WIDTH) {
      return str.substring(0, maxWidth - ELLIPSES_LENGTH) + ELLIPSES;
    }
    if (maxWidth < MIN_ABBREV_WIDTH_WITH_OFFSET) {
      throw new IllegalArgumentException("Minimum abbreviation width with "
          + "offset is " + MIN_ABBREV_WIDTH_WITH_OFFSET);
    }
    final int newMaxWidth = (maxWidth - ELLIPSES_LENGTH);
    if ((off + newMaxWidth) < str.length()) {
      return ELLIPSES + abbreviate(str.substring(off), 0, newMaxWidth);
    } else {
      return ELLIPSES + str.substring(str.length() - newMaxWidth);
    }
  }

  private static final int ELLIPSES_LENGTH = ELLIPSES.length();

  private static final int MIN_ABBREV_WIDTH = ELLIPSES_LENGTH + 1;

  private static final int MIN_ABBREV_WIDTH_WITH_OFFSET = ELLIPSES_LENGTH + MIN_ABBREV_WIDTH;

  /**
   * Truncate the string to get a prefix string of the specified length.
   *
   * <p>This function is similar to the function String.substring(0, length),
   * except that the function ensure the result string is always a valid Unicode
   * string, i.e., no incomplete code unit sequence at the end of the result
   * string.
   *
   * @param str
   *     the string to be truncated, which could be {@code null}.
   * @param length
   *     the length of the result string.
   * @return a prefix of str shorter than length, which consists a valid Unicode
   *     string. A null string will returns a {@code null}.
   * @throws IllegalArgumentException
   *     if <code>length &lt; 0</code>.
   */
  public static String truncateUtf16(@Nullable final String str,
      final int length) {
    if (str == null) {
      return null;
    }
    if (length <= 0) {
      throw new IllegalArgumentException();
    }
    if (str.length() <= length) {
      return str;
    } else {
      int len = length;
      final char ch = str.charAt(len - 1);
      if (Unicode.isHighSurrogate(ch)) {
        --len;
      }
      return str.substring(0, len);
    }
  }

  /**
   * Truncate the string to get a prefix string of the specified length in the
   * UTF-8 encoding.
   *
   * @param str
   *     the string to be truncated. If it is {@code null}, returns {@code
   *     null}.
   * @param length
   *     the length of bytes in the UTF-8 encoding of the result.
   * @return a prefix of str, which consists a valid Unicode string and is
   *     shorter than length when encoded in the UTF-8 encoding.
   */
  public static String truncateUtf8(@Nullable final String str,
      final int length) {
    if (str == null) {
      return null;
    }
    byte[] bytes = null;
    bytes = str.getBytes(UTF_8);
    if (bytes.length <= length) {
      return str;
    }
    final ByteBuffer in = ByteBuffer.wrap(bytes, 0, length);
    final CharsetDecoder decoder = UTF_8.newDecoder();
    decoder.onMalformedInput(CodingErrorAction.IGNORE);
    decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
    CharBuffer out = null;
    try {
      out = decoder.decode(in);
    } catch (final CharacterCodingException e) {
      // should not throw
      throw new RuntimeException(e);
    }
    return new String(out.array(), 0, out.limit());
  }

  public static void toCharArrayString(final String str,
      final StringBuilder builder) {
    final int n = str.length();
    builder.append('[');
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if (i > 0) {
        builder.append(',');
      }
      CharUtils.toUnicodeHexString(ch, builder);
    }
    builder.append(']');
  }

  public static String toCharArrayString(final String str) {
    final StringBuilder builder = new StringBuilder();
    toCharArrayString(str, builder);
    return builder.toString();
  }

  /**
   * Normalizes the spaces in a string.
   *
   * <p>This function will remove duplicated line breaks, and duplicated
   * spaces,
   * and spaces at beginning or ending of a line.
   *
   * @param str
   *     the string to be compact.
   * @param lineFolding
   *     if it is true, the multi-line text will be folded into one line, i.e,
   *     the line break is replaced by a space; otherwise, the line break is
   *     kept.
   * @return the compacted string.
   */
  public static String normalizeSpace(final String str,
      final boolean lineFolding) {
    final StringBuilder builder = new StringBuilder();
    normalizeSpace(str, lineFolding, false, builder);
    return builder.toString();
  }

  /**
   * Normalizes the spaces in a string.
   *
   * <p>This function will remove duplicated line breaks, and duplicated
   * spaces, and spaces at beginning or ending of a line.
   *
   * @param str
   *     the string to be compact.
   * @param lineFolding
   *     if it is true, the multi-line text will be folded into one line, i.e,
   *     the line break is replaced by a space; otherwise, the line break is
   *     kept.
   * @param keepTrailingSpace
   *     true to keep the trailing (last) space character; false to remove the
   *     trailing space.
   * @param builder
   *     a {@link StringBuilder} where to append the compacted string.
   * @return the number of characters appended to the builder.
   */
  public static int normalizeSpace(@Nullable final String str,
      final boolean lineFolding, final boolean keepTrailingSpace,
      final StringBuilder builder) {
    if (str == null) {
      return 0;
    }
    final int len = str.length();
    if (len == 0) {
      return 0;
    }
    int last = 0;
    int builderLen = builder.length();
    if (builderLen > 0) {
      final int lastCp = builder.codePointBefore(builderLen);
      last = CharUtils.getVisibility(lastCp);
    }
    int appended = 0;
    final CharSequenceCodePointIterator iter = new CharSequenceCodePointIterator(str);
    for (; !iter.atEnd(); iter.forward()) {
      final int codePoint = iter.current();
      int visibility = CharUtils.getVisibility(codePoint);
      // if line should be folded, the line break character is treated as a
      // inline blank
      // character
      if (lineFolding && (visibility == CharUtils.VISIBILITY_LINE_BREAK)) {
        visibility = CharUtils.VISIBILITY_INLINE_BLANK;
      }
      switch (visibility) {
        case CharUtils.VISIBILITY_GRAPH:
          // append the graphic code point
          builder.appendCodePoint(codePoint);
          ++appended;
          last = CharUtils.VISIBILITY_GRAPH;
          break;
        case CharUtils.VISIBILITY_INLINE_BLANK:
          // the current code point is a blank character except for line breaks.
          if (last == CharUtils.VISIBILITY_GRAPH) {
            // append a space only if the last code point appended is a graphic
            builder.append(' ');
            ++appended;
            last = CharUtils.VISIBILITY_INLINE_BLANK;
          }
          break;
        case CharUtils.VISIBILITY_LINE_BREAK:
          // the current code point is a line breaks.
          if (last == CharUtils.VISIBILITY_GRAPH) {
            // append a line break if the last code point appended is a graphic
            builder.append('\n');
            ++appended;
            last = CharUtils.VISIBILITY_LINE_BREAK;
          } else if (last == CharUtils.VISIBILITY_INLINE_BLANK) {
            if (appended > 0) {
              // if the last appended character is a space, change it to a line
              // break
              builderLen = builder.length();
              builder.setCharAt(builderLen - 1, '\n');
            } else { // otherwise, this is the first time to append, simply
              // append a line break
              builder.append('\n');
              ++appended;
            }
            last = CharUtils.VISIBILITY_LINE_BREAK;
          }
          break;
        default:
          throw new IllegalArgumentException("Unsupported visibility: " + visibility);
      }
    }
    // eat the last appended space or line break
    if ((!keepTrailingSpace) && (appended > 0)
        && ((last & CharUtils.VISIBILITY_BLANK) != 0)) {
      final int n = builder.length();
      assert (n > 0);
      builder.setLength(n - 1);
      --appended;
    }
    return appended;
  }

  /**
   * Quotes a string with the given quotation marks, escaping the characters in
   * the string if necessary.
   *
   * @param str
   *     the string to be quoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return the quoted string, with its original content escaped.
   */
  public static String quote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote) {
    final StringBuilder builder = new StringBuilder();
    quote(str, escapeChar, leftQuote, rightQuote, builder);
    return builder.toString();
  }

  /**
   * Quotes a string with the given quotation marks, escaping the characters in
   * the string if necessary.
   *
   * @param str
   *     the string to be quoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @param builder
   *     a string builder where to append the quoted string, with its original
   *     content escaped.
   */
  public static void quote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote,
      final StringBuilder builder) {
    final String result = escape(str, escapeChar, leftQuote, rightQuote);
    builder.append(leftQuote)
           .append(result)
           .append(rightQuote);
  }

  /**
   * Quotes a string with the single quotation marks, escaping the characters in
   * the string if necessary.
   *
   * @param str
   *     the string to be quoted.
   * @return the single-quoted string, with its original content escaped.
   */
  public static String singleQuote(final String str) {
    final StringBuilder builder = new StringBuilder();
    quote(str, '\\', '\'', '\'', builder);
    return builder.toString();
  }

  /**
   * Quotes a string with the double quotation marks, escaping the characters in
   * the string if necessary.
   *
   * @param str
   *     the string to be quoted.
   * @return the double-quoted string, with its original content escaped.
   */
  public static String doubleQuote(final String str) {
    final StringBuilder builder = new StringBuilder();
    quote(str, '\\', '"', '"', builder);
    return builder.toString();
  }

  /**
   * Unquotes a string with the given quotation marks, **without** unescaping the
   * characters in the string.
   *
   * @param str
   *     the string to be unquoted.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return the unquoted string, without escaping the characters in the string.
   * @throws IllegalArgumentException
   *     if the string is not correctly quoted.
   */
  public static String unquote(final String str, final char leftQuote,
      final char rightQuote) {
    final int n = str.length();
    if ((n < 2) || (str.charAt(0) != leftQuote)
        || (str.charAt(n - 1) != rightQuote)) {
      throw new IllegalArgumentException("String is not quoted: " + str);
    }
    return str.substring(1, n - 1);
  }

  /**
   * Unquotes a string with the given quotation marks, **without** unescaping the
   * characters in the string.
   *
   * @param str
   *     the string to be unquoted.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @param builder
   *     the string builder used to append the unquoted string, **without** escaping
   *     the characters in the string.
   * @throws IllegalArgumentException
   *     if the string is not correctly quoted.
   */
  public static void unquote(final String str, final char leftQuote,
      final char rightQuote, final StringBuilder builder) {
    final int n = str.length();
    if ((n < 2) || (str.charAt(0) != leftQuote) || (str.charAt(n - 1) != rightQuote)) {
      throw new IllegalArgumentException("String is not quoted: " + str);
    }
    builder.append(str, 1, n - 1);
  }

  /**
   * Unquotes a string with the given quotation marks, unescaping the characters
   * in the string if necessary.
   *
   * @param str
   *     the string to be unquoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return the unquoted string, with its original content unescaped.
   * @throws IllegalArgumentException
   *     if the string is not correctly quoted.
   */
  public static String unquote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote) {
    final StringBuilder builder = new StringBuilder(str.length());
    unquote(str, escapeChar, leftQuote, rightQuote, builder);
    return builder.toString();
  }

  /**
   * Unquotes a string with the given quotation marks, unescaping the characters
   * in the string if necessary.
   *
   * @param str
   *     the string to be unquoted.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @param builder
   *     a string builder where to append the unquoted string, with its original
   *     content unescaped.
   * @throws IllegalArgumentException
   *     if the string is not correctly quoted.
   */
  public static void unquote(final String str, final char escapeChar,
      final char leftQuote, final char rightQuote,
      final StringBuilder builder) {
    final int n = str.length();
    if ((n < 2) || (str.charAt(0) != leftQuote) || (str.charAt(n - 1) != rightQuote)) {
      throw new IllegalArgumentException("String is not quoted: " + str);
    }
    unescape(str.substring(1, n - 1), escapeChar, builder);
  }

  /**
   * Unquotes a string with the given quotation marks, without unescaping the
   * characters in the string.
   *
   * @param str
   *     the string to be unquoted.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @return the unquoted string, without escaping the characters in the string,
   *     or {@code null} if the input string is {@code null}.
   * @throws IllegalArgumentException
   *     if the string is not correctly quoted.
   */
  public static String unquoteIfNecessary(@Nullable final String str,
      final char leftQuote, final char rightQuote) {
    if (str == null) {
      return null;
    }
    final int n = str.length();
    if ((n >= 2) && (str.charAt(0) == leftQuote) && (str.charAt(n - 1) == rightQuote)) {
      return str.substring(1, n - 1);
    } else {
      return str;
    }
  }

  /**
   * Unquotes a string with the given quotation marks, without unescaping the
   * characters in the string.
   *
   * @param str
   *     the string to be unquoted.
   * @param leftQuote
   *     the left quotation mark.
   * @param rightQuote
   *     the right quotation mark.
   * @param builder
   *     the string builder used to append the unquoted string, without escaping
   *     the characters in the string.
   * @throws IllegalArgumentException
   *     if the string is not correctly quoted.
   */
  public static void unquoteIfNecessary(@Nullable final String str,
      final char leftQuote, final char rightQuote, final StringBuilder builder) {
    if (str != null) {
      final int n = str.length();
      if ((n >= 2) && (str.charAt(0) == leftQuote) && (str.charAt(n - 1) == rightQuote)) {
        builder.append(str, 1, n - 1);
      } else {
        builder.append(str);
      }
    }
  }

  /**
   * Tests whether a character in a escaped string is escaped.
   *
   * @param str
   *     an escaped string.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param index
   *     the index of a character in the escaped string.
   * @return {@code true} if the character at the specified index in the escaped
   *     string is escaped by the escaping character; {@code false} otherwise.
   * @throws IndexOutOfBoundsException
   *     if {@code index} is out of the bounds of {@code str}.
   */
  public static boolean isEscaped(final String str, final char escapeChar,
      final int index) {
    final int n = str.length();
    if ((index < 0) || (index >= n)) {
      throw new IndexOutOfBoundsException();
    }
    boolean escaped = false;
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if (index == i) {
        return escaped;
      }
      if (escaped) {
        escaped = false;
      } else if (ch == escapeChar) {
        escaped = true;
      }
    }
    return false;
  }

  /**
   * Escapes characters in a string using a specified escaped character.
   *
   * @param str
   *     the string to be escaped.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param characters
   *     the specified characters need to be escaped.
   * @return the escaped string.
   */
  public static String escape(final String str, final char escapeChar,
      final char... characters) {
    final int n = str.length();
    final StringBuilder builder = new StringBuilder(n * 2);
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if ((ch == escapeChar) || ArrayUtils.contains(characters, ch)) {
        builder.append(escapeChar);
      }
      builder.append(ch);
    }
    return builder.toString();
  }

  /**
   * Unescapes characters in a escaped string.
   *
   * <p>All characters in the escaped string escaped by the specified escape
   * character will be unescaped.
   *
   * @param str
   *     the escaped string to be unescaped.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @return the unescaped string.
   */
  public static String unescape(final String str, final char escapeChar) {
    final StringBuilder builder = new StringBuilder(str.length());
    unescape(str, escapeChar, builder);
    return builder.toString();
  }

  /**
   * Unescapes characters in a escaped string.
   *
   * <p>All characters in the escaped string escaped by the specified escape
   * character will be unescaped.
   *
   * @param str
   *     the escaped string to be unescaped.
   * @param escapeChar
   *     the character used to escape itself and other characters.
   * @param builder
   *     a string builder where to append the unescaped string.
   */
  public static void unescape(final String str, final char escapeChar,
      final StringBuilder builder) {
    final int n = str.length();
    boolean escaped = false;
    for (int i = 0; i < n; ++i) {
      final char ch = str.charAt(i);
      if (escaped) {
        builder.append(ch);
        escaped = false;
      } else if (ch == escapeChar) {
        escaped = true;
      } else {
        builder.append(ch);
      }
    }
  }

  /**
   * Converts a string to boolean.
   *
   * <pre>
   * toBoolean(null)    = false
   * toBoolean("true")  = true
   * toBoolean("false") = false
   * toBoolean("abc")   = false
   * toBoolean("  abc") = false
   * </pre>
   *
   * @param str
   *     the string to boolean, may be null.
   * @return the boolean output, {@code false} if null string input.
   */
  public static boolean toBoolean(@Nullable final String str) {
    return toBoolean(str, BooleanUtils.DEFAULT);
  }

  /**
   * Converts a string to boolean.
   *
   * <pre>
   * toBoolean(null, "true")    = true
   * toBoolean(null, "false")   = false
   * toBoolean("true", "true")  = true
   * toBoolean("false", "true") = false
   * toBoolean("abc", "false")  = false
   * toBoolean("  abc", "true") = true
   * </pre>
   *
   * @param str
   *     the string to boolean, may be null.
   * @param defaultValue
   *     defaultValue when string is null or not boolean.
   * @return the boolean output, {@code defaultValue} if input string is null or
   *     not boolean.
   */
  public static boolean toBoolean(@Nullable final String str,
      final boolean defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Converts a string to Boolean Object.
   *
   * <pre>
   * toBooleanObject(null)    = null
   * toBooleanObject("true")  = true
   * toBooleanObject("false") = false
   * toBooleanObject("abc")   = null
   * </pre>
   *
   * @param str
   *     the string to boolean, may be null.
   * @return the boolean output, {@code null} if null string input.
   */
  public static Boolean toBooleanObject(@Nullable final String str) {
    return toBooleanObject(str, null);
  }

  /**
   * Converts a string to Boolean Object.
   *
   * <pre>
   * toBooleanObject(null, "true")    = true
   * toBooleanObject(null, "false")   = false
   * toBooleanObject("true", "true")  = true
   * toBooleanObject("false", "true") = false
   * toBooleanObject("abc", "false")  = false
   * toBooleanObject("  abc", "true") = true
   * </pre>
   *
   * @param str
   *     the string to boolean, may be null.
   * @param defaultValue
   *     defaultValue when string is null or not boolean.
   * @return the Boolean output, {@code defaultValue} if null string input.
   */
  public static Boolean toBooleanObject(@Nullable final String str,
      @Nullable final Boolean defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (bf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static char toChar(@Nullable final String str) {
    return toChar(str, CharUtils.DEFAULT);
  }

  public static char toChar(@Nullable final String str,
      final char defaultValue) {
    if ((str == null) || (str.length() == 0)) {
      return defaultValue;
    } else {
      return str.charAt(0);
    }
  }

  public static Character toCharObject(@Nullable final String str) {
    return toCharObject(str, null);
  }

  public static Character toCharObject(@Nullable final String str,
      @Nullable final Character defaultValue) {
    if ((str == null) || (str.length() == 0)) {
      return defaultValue;
    } else {
      return str.charAt(0);
    }
  }

  public static byte toByte(@Nullable final String str) {
    return toByte(str, ByteUtils.DEFAULT);
  }

  public static byte toByte(@Nullable final String str,
      final byte defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Byte toByteObject(@Nullable final String str) {
    return toByteObject(str, null);
  }

  public static Byte toByteObject(@Nullable final String str,
      @Nullable final Byte defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static short toShort(@Nullable final String str) {
    return toShort(str, ShortUtils.DEFAULT);
  }

  public static short toShort(@Nullable final String str,
      final short defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Short toShortObject(@Nullable final String str) {
    return toShortObject(str, null);
  }

  public static Short toShortObject(@Nullable final String str,
      @Nullable final Short defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static int toInt(@Nullable final String str) {
    return toInt(str, IntUtils.DEFAULT);
  }

  public static int toInt(@Nullable final String str, final int defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Integer toIntObject(@Nullable final String str) {
    return toIntObject(str, null);
  }

  public static Integer toIntObject(@Nullable final String str,
      @Nullable final Integer defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static long toLong(@Nullable final String str) {
    return toLong(str, LongUtils.DEFAULT);
  }

  public static long toLong(@Nullable final String str,
      final long defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Long toLongObject(@Nullable final String str) {
    return toLongObject(str, null);
  }

  public static Long toLongObject(@Nullable final String str,
      @Nullable final Long defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static float toFloat(@Nullable final String str) {
    return toFloat(str, FloatUtils.DEFAULT);
  }

  public static float toFloat(@Nullable final String str,
      final float defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Float toFloatObject(@Nullable final String str) {
    return toFloatObject(str, null);
  }

  public static Float toFloatObject(@Nullable final String str,
      @Nullable final Float defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static double toDouble(@Nullable final String str) {
    return toDouble(str, DoubleUtils.DEFAULT);
  }

  public static double toDouble(@Nullable final String str,
      final double defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Double toDoubleObject(@Nullable final String str) {
    return toDoubleObject(str, null);
  }

  public static Double toDoubleObject(@Nullable final String str,
      @Nullable final Double defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Date toDate(@Nullable final String str) {
    return toDate(str, null);
  }

  public static Date toDate(@Nullable final String str,
      @Nullable final Date defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final DateFormat df = new DateFormat();
    Date value = df.parse(str);
    if (df.success()) {
      return value;
    } else {
      // try to parse without timezone
      df.setPattern(DateUtils.DEFAULT_LOCAL_DATETIME_PATTERN);
      value = df.parse(str);
      if (df.success()) {
        return value;
      }
      return defaultValue;
    }
  }

  public static LocalDate toLocalDate(@Nullable final String str) {
    return toLocalDate(str, null);
  }

  public static LocalDate toLocalDate(@Nullable final String str,
      @Nullable final LocalDate defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final LocalDateCodec codec = IsoLocalDateCodec.INSTANCE;
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      LOGGER.error("Failed to decode string to LocalDate: '{}', use the default value: {}", str, defaultValue, e);
      return defaultValue;
    }
  }

  public static LocalTime toLocalTime(@Nullable final String str) {
    return toLocalTime(str, null);
  }

  public static LocalTime toLocalTime(@Nullable final String str,
      @Nullable final LocalTime defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final LocalTimeCodec codec = IsoLocalTimeCodec.INSTANCE;
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      LOGGER.error("Failed to decode string to LocalTime: '{}', use the default value: {}", str, defaultValue, e);
      return defaultValue;
    }
  }

  public static LocalDateTime toLocalDateTime(@Nullable final String str) {
    return toLocalDateTime(str, null);
  }

  public static LocalDateTime toLocalDateTime(@Nullable final String str,
      @Nullable final LocalDateTime defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final LocalDateTimeCodec codec = IsoLocalDateTimeCodec.INSTANCE;
    try {
      return codec.decode(str);
    } catch (final DecodingException e) {
      LOGGER.error("Failed to decode string to LocalDateTime: '{}', use the default value: {}", str, defaultValue, e);
      return defaultValue;
    }
  }

  public static Class<?> toClass(@Nullable final String str) {
    return toClass(str, null);
  }

  public static Class<?> toClass(@Nullable final String str,
      @Nullable final Class<?> defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final String className = strip(str);
    try {
      return ClassUtils.getClass(className);
    } catch (final ClassNotFoundException e) {
      return defaultValue;
    }
  }

  public static byte[] toByteArray(@Nullable final String str) {
    return toByteArray(str, null);
  }

  public static byte[] toByteArray(@Nullable final String str,
      @Nullable final byte[] defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    if (str.length() == 0) {
      return defaultValue;
    }
    final HexCodec codec = new HexCodec();
    final byte[] value = codec.decode(str);
    if (codec.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Converts a string to a {@link BigInteger} value.
   *
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     the {@link BigInteger} value of the string, or {@code null} if the
   *     string is null or cannot be converted to a {@link BigInteger}.
   */
  public static BigInteger toBigInteger(@Nullable final String str) {
    return toBigInteger(str, null);
  }

  /**
   * Converts a string to a {@link BigInteger} value, with a default value if
   * the string is null or cannot be converted.
   *
   * @param str
   *     the string to be converted, may be null.
   * @param defaultValue
   *     the default value to be returned if the string is null or cannot be
   *     converted to a {@link BigInteger}, may be null.
   * @return
   *     the {@link BigInteger} value of the string, or the default value if the
   *     string is null or cannot be converted to a {@link BigInteger}.
   */
  public static BigInteger toBigInteger(@Nullable final String str,
      @Nullable final BigInteger defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Converts a string to a {@link BigDecimal} value.
   *
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     the {@link BigDecimal} value of the string, or {@code null} if the
   *     string is null or cannot be converted to a {@link BigDecimal}.
   */
  public static BigDecimal toBigDecimal(@Nullable final String str) {
    return toBigDecimal(str, null);
  }

  /**
   * Converts a string to a {@link BigDecimal} value, with a default value if
   * the string is null or cannot be converted.
   *
   * @param str
   *     the string to be converted, may be null.
   * @param defaultValue
   *     the default value to be returned if the string is null or cannot be
   *     converted to a {@link BigDecimal}, may be null.
   * @return
   *     the {@link BigDecimal} value of the string, or the default value if the
   *     string is null or cannot be converted to a {@link BigDecimal}.
   */
  public static BigDecimal toBigDecimal(@Nullable final String str,
      @Nullable final BigDecimal defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(str);
    if (nf.success()) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Converts a string to an enum value of the specified enum class.
   *
   * @param <E>
   *     the type of the enum class.
   * @param cls
   *     the enum class.
   * @param str
   *     the string to be converted, may be null.
   * @param defaultValue
   *     the default value to be returned if the string is null or cannot be
   *     converted to an enum value, may be null.
   * @return
   *     the enum value of the string, or the default value if the string is
   *     null or cannot be converted to an enum value.
   */
  public static <E extends Enum<E>> E toEnum(final Class<E> cls,
      @Nullable final String str,
      @Nullable final E defaultValue) {
    for (final E value : cls.getEnumConstants()) {
      if (value.name().equals(str)) {
        return value;
      }
    }
    return defaultValue;
  }

  /**
   * Converts a string to an XML node with the specified tag name and value.
   *
   * @param doc
   *     the XML document.
   * @param tagName
   *     the tag name of the XML node.
   * @param prevSpaceAttr
   *     the attribute name for the previous space, may be null.
   * @param value
   *     the value of the XML node, may be null.
   * @return
   *     the XML node.
   */
  public static Element toXmlNode(final Document doc, final String tagName,
      @Nullable final String prevSpaceAttr, @Nullable final String value) {
    final Element node = doc.createElement(tagName);
    if (value != null) {
      if ((prevSpaceAttr != null) && (prevSpaceAttr.length() > 0)
          && startsOrEndsWithChar(value, BlankCharFilter.INSTANCE)) {
        node.setAttribute(prevSpaceAttr, TRUE);
      }
      node.setTextContent(value);
    }
    return node;
  }

  /**
   * Normalizes the line breaks in a string.
   *
   * @param str
   *     the string to be normalized, may be null.
   * @return
   *     the normalized string, or the original string if it is null.
   */
  public static String normalizeLines(final String str) {
    return normalizeLines(str, true, true);
  }

  /**
   * Normalizes the line breaks in a string, with options to trim and ignore
   * empty lines.
   *
   * @param str
   *     the string to be normalized, may be null.
   * @param trim
   *     whether to trim the lines.
   * @param ignoreEmpty
   *     whether to ignore empty lines.
   * @return
   *     the normalized string, or the original string if it is null.
   */
  public static String normalizeLines(final String str, final boolean trim,
      final boolean ignoreEmpty) {
    if (isEmpty(str)) {
      return str;
    }
    final List<String> lines = new Splitter()
        .toLines()
        .strip(trim)
        .ignoreEmpty(ignoreEmpty)
        .split(str);
    return join('\n', lines);
  }

  /**
   * Concatenates the lines in a string into a single line, with options to trim
   * and ignore empty lines.
   *
   * @param str
   *     the string to be concatenated, may be null.
   * @param trim
   *     whether to trim the lines.
   * @param ignoreEmpty
   *     whether to ignore empty lines.
   * @return
   *     the concatenated string, or the original string if it is null.
   */
  public static String concatLines(final String str, final boolean trim,
      final boolean ignoreEmpty) {
    if (isEmpty(str)) {
      return str;
    }
    final List<String> lines = new Splitter()
        .toLines()
        .strip(trim)
        .ignoreEmpty(ignoreEmpty)
        .split(str);
    return join(' ', lines);
  }

  /**
   * Truncates a string before the first occurrence of a specified substring.
   *
   * @param str
   *     the string to be truncated, may be null.
   * @param substr
   *     the substring to truncate before, may be null.
   * @return
   *     the truncated string, or the original string if the substring is not
   *     found or if the input string is null.
   */
  public static String truncateBefore(final String str, final String substr) {
    final int pos = str.indexOf(substr);
    if (pos >= 0) {
      return str.substring(0, pos);
    } else {
      return str;
    }
  }

  /**
   * Truncates a string after the first occurrence of a specified substring.
   *
   * @param str
   *     the string to be truncated, may be null.
   * @param substr
   *     the substring to truncate after, may be null.
   * @return
   *     the truncated string, or the original string if the substring is not
   *     found or if the input string is null.
   */
  public static String truncateAfter(final String str, final String substr) {
    final int pos = str.indexOf(substr);
    if (pos >= 0) {
      return str.substring(0, pos + substr.length());
    } else {
      return str;
    }
  }

  /**
   * Concatenates multiple strings into a single string.
   *
   * @param strings
   *     the strings to be concatenated.
   * @return
   *     the concatenated string.
   */
  public static String concat(final String... strings) {
    final StringBuilder builder = new StringBuilder();
    for (final String str : strings) {
      builder.append(str);
    }
    return builder.toString();
  }

  /**
   * Converts an object to a string representation.
   *
   * @param obj
   *     the object to be converted, may be null.
   * @return
   *     the string representation of the object, or {@code null} if the object
   *     is null.
   */
  public static String valueOf(@Nullable final Object obj) {
    return (obj == null ? null : obj.toString());
  }

  /**
   * Converts the first character of a string to uppercase.
   *
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     the converted string, or the original string if it is null or empty.
   */
  public static String uppercaseFirstChar(final String str) {
    if (str == null || str.length() == 0) {
      return str;
    } else {
      return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
  }

  /**
   * Converts the first character of a string to lowercase.
   *
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     the converted string, or the original string if it is null or empty.
   */
  public static String lowercaseFirstChar(final String str) {
    if (str == null || str.length() == 0) {
      return str;
    } else {
      return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
  }

  /**
   * Converts a string to a string representation.
   *
   * @param value
   *     the value to be converted, may be null.
   * @return
   *     the string representation of the value, or {@code null} if the value is
   *     {@code null}.
   */
  public static String toString(@Nullable final Object value) {
    return (value == null ? null : toStringImpl(value));
  }

  /**
   * Converts a value to a string representation, with a default value if the
   * value is {@code null}.
   *
   * @param value
   *     the value to be converted, may be null.
   * @param defaultValue
   *     the default value to be returned if the value is null.
   * @return
   *     the string representation of the value, or the default value if the
   *     value is {@code null}.
   */
  public static String toString(@Nullable final Object value,
      final String defaultValue) {
    return (value == null ? defaultValue : toStringImpl(value));
  }

  /**
   * Converts a value to a string representation.
   *
   * @param value
   *     the value to be converted.
   * @return
   *     the string representation of the value.
   */
  private static String toStringImpl(final Object value) {
    if (value instanceof String) {
      return (String) value;
    } else if (value instanceof Character) {
      return String.valueOf(value);
    } else if (value instanceof Enum) {
      return ((Enum<?>) value).name();
    } else if (ArrayUtils.isArray(value)) {
      return ArrayUtils.toString(value);
    } else {
      return value.toString();
    }
  }

  /**
   * Checks whether the specified type of values can be compared with {@code
   * String} type values.
   *
   * @param type
   *     the specified type.
   * @return
   *     {@code true} if the values of the specified type can be compared with
   *     {@code String} type values; {@code false} otherwise.
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == String.class)
        || (type == char.class)
        || (type == Character.class)
        || Enum.class.isAssignableFrom(type);
  }

  /**
   * Lowercase the first character of a string.
   *
   * @param str
   *     a specified string.
   * @return
   *     another new string whose first character is lowercased and whose
   *     remained content is the same as the specified string.
   */
  public static String lowerCaseFirstChar(final String str) {
    if (str == null || str.length() == 0) {
      return str;
    } else {
      return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
  }

  /**
   * Converts an empty string to {@code null} value.
   *
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     {@code null} if the specified string is null or empty; otherwise returns
   *     the original string.
   */
  @Nullable
  public static String emptyToNull(@Nullable final String str) {
    if (str != null && str.isEmpty()) {
      return null;
    } else {
      return str;
    }
  }

  /**
   * Converts a string with the specified default value to {@code null}.
   *
   * @param defaultValue
   *     the specified default value.
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     {@code null} if the specified string is null or equals to the
   *     specified default value; otherwise returns the original string.
   */
  @Nullable
  public static String defaultToNull(final String defaultValue, @Nullable final String str) {
    if (str != null && str.equals(defaultValue)) {
      return null;
    } else {
      return str;
    }
  }

  /**
   * Converts null string to empty string.
   *
   * @param str
   *     the string to be converted, may be null.
   * @return
   *     an empty string if the specified string is null; otherwise returns the
   *     original string.
   */
  @Nonnull
  public static String nullToEmpty(@Nullable final String str) {
    return (str == null ? EMPTY : str);
  }

  /**
   * Converts null character sequence to empty string.
   *
   * @param str
   *     the character sequence to be converted, may be null.
   * @return
   *     an empty string if the specified character sequence is null; otherwise
   *     returns the original character sequence.
   */
  @Nonnull
  public static CharSequence nullToEmpty(@Nullable final CharSequence str) {
    return (str == null ? EMPTY : str);
  }

  /**
   * Splits the specified character sequence into Unicode code points.
   *
   * @param str
   *     the specified character sequence, may be null.
   * @return
   *     the list of Unicode code points in the specified character sequence.
   *     Returns an empty list if the specified character sequence is null or
   *     empty.
   */
  public static IntList splitCodePoints(@Nullable final CharSequence str) {
    final IntList result = new IntArrayList();
    if (str == null || str.length() == 0) {
      return result;
    }
    final int n = str.length();
    int count;
    for (int i = 0; i < n; i += count) {
      final int codePoint = Character.codePointAt(str, i);
      count = Character.charCount(codePoint);
      result.add(codePoint);
    }
    return result;
  }

  /**
   * Splits the specified character sequence into Unicode code points.
   *
   * @param str
   *     the specified character sequence, may be null.
   * @return
   *     the array of Unicode code points in the specified character sequence.
   *     Returns an empty array if the specified character sequence is null or
   *     empty.
   */
  public static int[] splitCodePointsToArray(@Nullable final CharSequence str) {
    return splitCodePoints(str).toArray();
  }

  /**
   * Formats a double value as a percentage string.
   *
   * @param value
   *     the double value to be formatted.
   * @return
   *     the formatted percentage string.
   */
  public static String formatPercent(final double value) {
    return formatPercent(value, 2, Locale.getDefault());
  }

  /**
   * Formats a double value as a percentage string with the specified number of
   * fraction digits.
   *
   * @param value
   *     the double value to be formatted.
   * @param fractionDigits
   *     the number of fraction digits to be displayed.
   * @return
   *     the formatted percentage string.
   */
  public static String formatPercent(final double value, final int fractionDigits) {
    return formatPercent(value, fractionDigits, Locale.getDefault());
  }

  /**
   * Formats a double value as a percentage string with the specified number of
   * fraction digits and locale.
   *
   * @param value
   *     the double value to be formatted.
   * @param fractionDigits
   *     the number of fraction digits to be displayed.
   * @param locale
   *     the locale to be used for formatting.
   * @return
   *     the formatted percentage string.
   */
  public static String formatPercent(final double value, final int fractionDigits,
      final Locale locale) {
    final java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance(locale);
    nf.setMaximumFractionDigits(fractionDigits);
    nf.setMinimumFractionDigits(fractionDigits);
    return nf.format(value);
  }

  /**
   * Parses a percentage string into a double value.
   *
   * @param str
   *     the percentage string to be parsed.
   * @return
   *     the parsed double value.
   * @throws ParseException
   *     if the string cannot be parsed as a percentage.
   */
  public static double parsePercent(@Nullable final String str) throws ParseException {
    return parsePercent(str, 2, Locale.getDefault());
  }

  /**
   * Parses a percentage string into a double value with the specified number of
   * fraction digits.
   *
   * @param str
   *     the percentage string to be parsed.
   * @param fractionDigits
   *     the number of fraction digits expected in the percentage string.
   * @return
   *     the parsed double value.
   * @throws ParseException
   *     if the string cannot be parsed as a percentage.
   */
  public static double parsePercent(@Nullable final String str, final int fractionDigits)
      throws ParseException {
    return parsePercent(str, fractionDigits, Locale.getDefault());
  }

  /**
   * Parses a percentage string into a double value with the specified number of
   * fraction digits and locale.
   *
   * @param str
   *     the percentage string to be parsed.
   * @param fractionDigits
   *     the number of fraction digits expected in the percentage string.
   * @param locale
   *     the locale to be used for parsing.
   * @return
   *     the parsed double value.
   * @throws ParseException
   *     if the string cannot be parsed as a percentage.
   */
  public static double parsePercent(@Nullable final String str, final int fractionDigits,
      final Locale locale) throws ParseException {
    if (str == null || str.isEmpty()) {
      throw new ParseException("Cannot parse null or empty string as percentage", 0);
    }
    final java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance(locale);
    nf.setMaximumFractionDigits(fractionDigits);
    return nf.parse(str).doubleValue();
  }

  /**
   * Adds a prefix to each line of the input string.
   * <p>
   * The line break characters include {@code '\n'}, {@code '\r'}, and {@code '\r\n'}.
   *
   * @param input
   *     the input string, may be null.
   * @param prefix
   *     the prefix to be added to each line, may be null or empty.
   * @return
   *     the string with the specified prefix added to each line, or the original
   *     string if the input or prefix is null or empty.
   */
  public static String addPrefixToEachLine(final String input, final String prefix) {
    if (input == null || prefix == null || prefix.isEmpty()) {
      return input; // If input or prefix is null or empty, return the original string
    }
    // Use regular expression to match the beginning of each line and add the prefix
    // Regular expression explanation:
    // ^ represents the beginning of a line
    // (?m) is the multiline mode, which makes ^ and $ match the beginning and end of each line
    return input.replaceAll("(?m)^", prefix);
  }

  /**
   * Removes a prefix from each line of the input string.
   * <p>
   * The line break characters include {@code '\n'}, {@code '\r'}, and {@code '\r\n'}.
   *
   * @param input
   *     the input string, may be null.
   * @param prefix
   *     the prefix to be removed from each line, may be null or empty.
   * @return
   *     the string with the specified prefix removed from each line, or the original
   *     string if the input or prefix is null or empty.
   */
  public static String removePrefixFromEachLine(final String input, final String prefix) {
    if (input == null || prefix == null || prefix.isEmpty()) {
      return input; // If input or prefix is null or empty, return the original string
    }
    // Use regular expression to replace the prefix at the beginning of each line, ensuring line-by-line matching
    // ^ represents the beginning of a line
    // (?m) is the multiline mode, which makes ^ and $ match the beginning and end of each line
    final String regex = "(?m)^" + Pattern.quote(prefix);
    return input.replaceAll(regex, "");
  }
}