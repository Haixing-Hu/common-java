////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.util.filter.character.AcceptAllCharFilter;
import ltd.qubit.commons.util.filter.character.AcceptSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.character.InArrayCharFilter;
import ltd.qubit.commons.util.filter.character.InStringCharFilter;
import ltd.qubit.commons.util.filter.character.NotInArrayCharFilter;
import ltd.qubit.commons.util.filter.character.NotInStringCharFilter;
import ltd.qubit.commons.util.filter.character.RejectAllCharFilter;
import ltd.qubit.commons.util.filter.character.RejectSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.codepoint.AcceptAllCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.AcceptSpecifiedCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.BlankCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectAllCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectSpecifiedCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.WhitespaceCodePointFilter;

import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitByChar;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitByCharType;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitByCodePoint;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitBySubstring;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitEachChar;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitEachCodePoint;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitLines;

/**
 * A class used to split strings.
 *
 * <p>Usage examples:</p>
 * <pre><code>
 *
 * final List&lt;String&gt; r1 = new Splitter()
 *    .byChar(',')
 *    .strip(true)
 *    .ignoreEmpty(false)
 *    .split(str);
 * final List&lt;String&gt; r2 = new Splitter()
 *    .byCharsIn([',', '.', ' '])
 *    .strip(false)
 *    .ignoreEmpty(true)
 *    .split(str);
 * final List&lt;String&gt; r3 = new Splitter()
 *    .byCharsIn(",.; ")
 *    .strip(true)
 *    .ignoreEmpty(true)
 *    .split(str);
 * final List&lt;String&gt; r3 = new Splitter()
 *    .onCodePointIn(",:&#92;uD83D&#92;uDD6E.") // &#92;uD83D&#92;uDD6E is 🕮
 *    .strip(true)
 *    .ignoreEmpty(true)
 *    .split(str);
 * final List&lt;String&gt; r4 = new Splitter()
 *    .onCodePointSatisfy(filter)
 *    .strip(true)
 *    .ignoreEmpty(true)
 *    .split(str);
 * final List&lt;String&gt; r5 = new Splitter()
 *    .onSubstring("xyz")
 *    .strip(true)
 *    .ignoreEmpty(true)
 *    .ignoreCase(true)
 *    .split(str);
 * final List&lt;String&gt; r6 = new Splitter()
 *    .onLineBreaks()
 *    .ignoreEmpty(true)
 *    .split(str);
 * final List&lt;String&gt; r7 = new Splitter()
 *    .byCharTypes()
 *    .camelCase(true)
 *    .split(str);
 * </code></pre>
 *
 * @author Haixing Hu
 */
public class Splitter {

  private CharFilter byCharFilter;

  private CodePointFilter byCodePointFilter;

  private CharSequence bySubstring;

  private boolean toLines;

  private boolean byWhitespace;

  private boolean byBlanks;

  private boolean byCharType;

  private boolean toChars;

  private boolean toCodePoints;

  private boolean strip;

  private boolean ignoreEmpty;

  private boolean ignoreCase;

  private boolean camelCase;

  public Splitter() {}

  private void clearStrategies() {
    this.byCharFilter = null;
    this.byCodePointFilter = null;
    this.bySubstring = null;
    this.toLines = false;
    this.byWhitespace = false;
    this.byBlanks = false;
    this.byCharType = false;
    this.toChars = false;
    this.toCodePoints = false;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified character.
   *
   * <p>Examples:
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar('.').split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar('.').split("a..b.c")  = {"a", "", "b", "c"}
   * new Splitter().byChar('.').ignoreEmpty(true).split("a..b.c")  = {"a", "b", "c"}
   * new Splitter().byChar('.').split("a. .b.c")  = {"a", " ", "b", "c"}
   * new Splitter().byChar('.').strip(true).split("a. .b.c")  = {"a", "", "b", "c"}
   * new Splitter().byChar('.').strip(true).ignoreEmpty(true).split("a. .b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(' ').split("a  b c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(' ').ignoreEmpty(true).split("a  b c")  = {"a", "b", "c"}
   * </pre>
   *
   * @param separator
   *     the specified separator character.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byChar(final char separator) {
    this.clearStrategies();
    this.byCharFilter = new AcceptSpecifiedCharFilter(separator);
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by all
   * characters except the specified one.
   *
   * @param separator
   *     the specified character. All characters except this one will be used
   *     as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharNotEqual(final char separator) {
    this.clearStrategies();
    this.byCharFilter = new RejectSpecifiedCharFilter(separator);
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * character in the specified array.
   *
   * <p>Examples:
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     the array of separator characters. A {@code null} value or empty array
   *     indicates that no character will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharsIn(@Nullable final char[] chars) {
    this.clearStrategies();
    if (chars == null || chars.length == 0) {
      this.byCharFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * characters in the specified sequence.
   *
   * <p>Examples:
   * <pre>
   *
   *
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     the sequence of separator characters. A {@code null} value or empty
   *     sequence indicates that no character will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if (chars == null || chars.length() == 0) {
      this.byCharFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * characters not in the specified array.
   *
   * <p>Examples:
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     the specified array of characters. The string will be split by
   *     characters not in this array. A {@code null} value or empty array
   *     indicates that any character will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharsNotIn(@Nullable final char[] chars) {
    this.clearStrategies();
    if (chars == null || chars.length == 0) {
      this.byCharFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * characters not in the specified sequence.
   *
   * <p>Examples:
   * <pre>
   *
   *
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     the specified sequence of characters. The string will be split by
   *     characters not in this sequence. A {@code null} value or empty sequence
   *     indicates that any character will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if (chars == null || chars.length() == 0) {
      this.byCharFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * characters satisfying the specified filter.
   *
   * @param filter
   *     the specified filter accepting the characters used as separators. A
   *     {@code null} value indicates that no character will be used as
   *     the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharsSatisfy(final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCharFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = filter;
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * characters that does not satisfy the specified filter.
   *
   * @param filter
   *     the specified filter rejecting the characters used as separators. A
   *     {@code null} value indicates that any character will be used as
   *     the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCharsNotSatisfy(final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCharFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified Unicode code point.
   *
   * <p>Examples:
   * <pre><code>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(".").split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar(".,:").ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </code></pre>
   *
   * <p>Note that if the specified character sequence contains an Unicode
   * character outside the BMP, the splitter will split the string according
   * to the Unicode code point instead of the code unit. For example:</p>
   * <pre><code>
   * final String separators = ",:&#92;uD83D&#92;uDD6E.";
   * final Splitter splitter = new Splitter().onCodePointIn(separators);
   *
   * splitter.split(",a. &#92;uD83D&#92;uDD6Eb&#92;uD83D&#92;uDD6Ec&#92;uD83D&#92;uDD6E")
   *    = {"", "a", " ", "b", "c", ""}
   * splitter.split("&#92;uD83D&#92;uDD6Ea&#92;uD83D: &#92;uD83D&#92;uDD6Eb.c&#92;uDD6E, ")
   *    = {"", "a&#92;uD83D", " ", "b", "c&#92;uDD6E", " "}
   * </code></pre>
   *
   * @param codePoint
   *     the specified Unicode code point used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePoint(final int codePoint) {
    this.clearStrategies();
    this.byCodePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified Unicode code point.
   *
   * <p>Examples:
   * <pre><code>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(".").split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar(".,:").ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </code></pre>
   *
   * <p>Note that if the specified character sequence contains an Unicode
   * character outside the BMP, the splitter will split the string according
   * to the Unicode code point instead of the code unit. For example:</p>
   * <pre><code>
   * final String separators = ",:&#92;uD83D&#92;uDD6E.";
   * final Splitter splitter = new Splitter().onCodePointIn(separators);
   *
   * splitter.split(",a. &#92;uD83D&#92;uDD6Eb&#92;uD83D&#92;uDD6Ec&#92;uD83D&#92;uDD6E")
   *    = {"", "a", " ", "b", "c", ""}
   * splitter.split("&#92;uD83D&#92;uDD6Ea&#92;uD83D: &#92;uD83D&#92;uDD6Eb.c&#92;uDD6E, ")
   *    = {"", "a&#92;uD83D", " ", "b", "c&#92;uDD6E", " "}
   * </code></pre>
   *
   * @param codePoint
   *     the character sequence containing the Unicode character used as the
   *     separator. A {@code null} or empty value indicates that no Unicode code
   *     point will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by all
   * Unicode code points except the specified one.
   *
   * @param codePoint
   *     the specified Unicode code point. All Unicode code point except this
   *     one will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointNotEqual(final int codePoint) {
    this.clearStrategies();
    this.byCodePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by all
   * Unicode code points except the specified one.
   *
   * @param codePoint
   *     the character sequence containing the specified Unicode code point.
   *     All Unicode code point except the specified one will be used as the
   *     separator. A {@code null} or empty value indicates that all Unicode
   *     code points will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * Unicode code point in the specified array.
   *
   * @param codePoints
   *     the specified array containing the Unicode code points used as
   *     separators. A {@code null} or empty value indicates that no Unicode
   *     code point will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointsIn(@Nullable final int[] codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length == 0) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * Unicode code points in the specified character sequence.
   *
   * <p>Examples:
   * <pre><code>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(".").split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar(".,:").ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </code></pre>
   *
   * <p>Note that if the specified character sequence contains an Unicode
   * character outside the BMP, the splitter will split the string according
   * to the Unicode code point instead of the code unit. For example:</p>
   * <pre><code>
   * final String separators = ",:&#92;uD83D&#92;uDD6E.";
   * final Splitter splitter = new Splitter().onCodePointIn(separators);
   *
   * splitter.split(",a. &#92;uD83D&#92;uDD6Eb&#92;uD83D&#92;uDD6Ec&#92;uD83D&#92;uDD6E")
   *    = {"", "a", " ", "b", "c", ""}
   * splitter.split("&#92;uD83D&#92;uDD6Ea&#92;uD83D: &#92;uD83D&#92;uDD6Eb.c&#92;uDD6E, ")
   *    = {"", "a&#92;uD83D", " ", "b", "c&#92;uDD6E", " "}
   * </code></pre>
   *
   * @param codePoints
   *     the specified character sequence containing the Unicode code points
   *     used as separators. A {@code null} or empty value indicates that no
   *     Unicode code point will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length() == 0) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * Unicode code points not in the specified array.
   *
   * @param codePoints
   *     the specified Unicode code points array. The string will be split by
   *     Unicode code points not in this array. A {@code null} or empty value
   *     indicates that all Unicode code points will be used as separators.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointsNotIn(@Nullable final int[] codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length == 0) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * Unicode code points not in the specified sequence.
   *
   * @param codePoints
   *     the specified Unicode code points sequence. The string will be split by
   *     Unicode code points not in this sequence. A {@code null} or empty value
   *     indicates that all Unicode code points will be used as separators.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length() == 0) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * Unicode code points satisfying the specified filter.
   *
   * @param filter
   *     the specified filter accepting the Unicode code points used as
   *     separators. A {@code null} value indicates that no Unicode code point
   *     will be used as the separator.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointsSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = filter;
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * Unicode code points that not satisfies the specified filter.
   *
   * @param filter
   *     the specified filter rejecting the Unicode code points used as
   *     separators. A {@code null} value indicates that all Unicode code
   *     points will be used as separators.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter byCodePointsNotSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by the
   * specified substring.
   *
   * @param separator
   *     the specified substring used as the separator. A {@code null} or empty
   *     value will cause the source string to be split such that each Unicode
   *     code point become a single substring.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter bySubstring(@Nullable final CharSequence separator) {
    this.clearStrategies();
    this.bySubstring = nullToEmpty(separator);
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by
   * whitespace characters.
   *
   * <p>Examples:
   * <pre><code>
   * new Splitter().byWhitespaces().split(null)       = null
   * new Splitter().byWhitespaces().split("")         = null
   * new Splitter().byWhitespaces().split("abc def")  = {"abc", "def"}
   * new Splitter().byWhitespaces().split("abc  def") = {"abc", "def"}
   * new Splitter().byWhitespaces().split(" abc ")    = {"abc"}
   * new Splitter().byWhitespaces().split("&#92;f a&#92;tbc ")   = {"a", "bc"}
   * new Splitter().byWhitespaces().split("&#92;f abc ")     = {"abc"}
   * // the following examples showed the difference between byWhitespaces() and byBlanks()
   * new Splitter().byWhitespaces().split("a&#92;u007Fb&#92;u007F .c") = {"a&#92;u007Fb&#92;u007F", ".c"}
   * new Splitter().byWhitespaces().strip(true).split("a&#92;u007Fb&#92;u007F .c") = {"a&#92;u007Fb", ".c"}
   * </code></pre>
   *
   * @return
   *     the reference to this {@link Splitter} object.
   * @see Character#isWhitespace(int)
   */
  public Splitter byWhitespaces() {
    this.clearStrategies();
    this.byWhitespace = true;
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by blank
   * characters.
   *
   * <p>Examples:
   * <pre><code>
   * new Splitter().byBlanks().split(null)       = null
   * new Splitter().byBlanks().split("")         = null
   * new Splitter().byBlanks().split("abc def")  = {"abc", "def"}
   * new Splitter().byBlanks().split("abc  def") = {"abc", "def"}
   * new Splitter().byBlanks().split(" abc ")    = {"abc"}
   * new Splitter().byBlanks().split("&#92;f a&#92;tbc ")   = {"a", "bc"}
   * new Splitter().byBlanks().split("&#92;f abc ")     = {"abc"}
   * // the following examples showed the difference between byWhitespaces() and byBlanks()
   * new Splitter().byBlanks().split("a&#92;u007Fb&#92;u007F .c") = {"a", "b", ".c"}
   * new Splitter().byBlanks().strip(true).split("a&#92;u007Fb&#92;u007F .c") = {"a", "b", ".c"}
   * </code></pre>
   *
   * @return
   *     the reference to this {@link Splitter} object.
   * @see CharUtils#isBlank(int)
   */
  public Splitter byBlanks() {
    this.clearStrategies();
    this.byBlanks = true;
    return this;
  }

  /**
   * Splits a string by Character type as returned by
   * {@code java.lang.Character.getType(char)}.
   *
   * <p>Groups of contiguous characters of the same type are returned as
   * complete tokens, with the following exception:
   *
   * <p>If {@code camelCase} is set to {@code true}, the character of type
   * {@code Character.UPPERCASE_LETTER}, if any, immediately preceding a token
   * of type {@code Character.LOWERCASE_LETTER} will belong to the following
   * token rather than to the preceding, if any, {@code Character.UPPERCASE_LETTER}
   * token.
   *
   * <p>Examples:
   * <pre>
   * new Splitter().byCharTypes().split(null)                 = {}
   * new Splitter().byCharTypes().split("")                   = {""}
   * new Splitter().byCharTypes().ignoreEmpty(true).split("") = {}
   * new Splitter().byCharTypes().split("ab   de fg")         = {"ab", "   ", "de", " ", "fg"}
   * new Splitter().byCharTypes().strip(true).split("ab   de fg") = {"ab", "", "de", "", "fg"}
   * new Splitter().byCharTypes().strip(true).ignoreEmpty(true).split("ab   de fg") = {"ab", "de", "fg"}
   * new Splitter().byCharTypes().split("ab:cd:ef")       = {"ab", ":", "cd", ":", "ef"}
   * new Splitter().byCharTypes().split("number5")        = {"number", "5"}
   * new Splitter().byCharTypes().split("fooBar") = {"foo", "B", "ar"}
   * new Splitter().byCharTypes().camelCase(true).split("fooBar")  = {"foo", "Bar"}
   * new Splitter().byCharTypes().split("foo200Bar") = {"foo", "200", "B", "ar"}
   * new Splitter().byCharTypes().camelCase(true).split("foo200Bar")  = {"foo", "200", "Bar"}
   * new Splitter().byCharTypes().camelCase(false).split("ASFRules")  = {"ASFR", "ules"}
   * new Splitter().byCharTypes().camelCase(true).split("ASFRules")   = {"ASF", "Rules"}
   * </pre>
   *
   * @return
   *     the reference to this {@link Splitter} object.
   * @see #camelCase(boolean)
   */
  public Splitter byCharTypes() {
    this.clearStrategies();
    this.byCharType = true;
    return this;
  }

  /**
   * Splits the provided string into a list of substrings, separated by line
   * breaks.
   *
   * <p>The line break may be "\r", "\r\n", or "\n".</p>
   *
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter toLines() {
    this.clearStrategies();
    this.toLines = true;
    return this;
  }

  /**
   * Splits each character in a string into substrings.
   *
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter toChars() {
    this.clearStrategies();
    this.toChars = true;
    return this;
  }

  /**
   * Splits each Unicode code points in a string into substrings.
   *
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter toCodePoints() {
    this.clearStrategies();
    this.toCodePoints = true;
    return this;
  }

  /**
   * Sets whether to strip the split substrings.
   *
   * <p>The default value of this option is set to {@code false}.</p>
   *
   * @param strip
   *     whether to strip the split substrings.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter strip(final boolean strip) {
    this.strip = strip;
    return this;
  }

  /**
   * Sets whether to ignore the empty substrings in the split result list.
   *
   * <p>The default value of this option is set to {@code false}.</p>
   *
   * @param ignoreEmpty
   *     whether to ignore the empty substrings in the split result list.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter ignoreEmpty(final boolean ignoreEmpty) {
    this.ignoreEmpty = ignoreEmpty;
    return this;
  }

  /**
   * Sets whether to ignore the case while comparing the substrings.
   *
   * <p>The default value of this option is set to {@code false}.</p>
   *
   * @param ignoreCase
   *     whether to ignore the case while comparing the substrings.
   * @return
   *     the reference to this {@link Splitter} object.
   */
  public Splitter ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * Sets whether to use the camel-case strategy while splitting the string by
   * character types.
   *
   * <p>The default value of this option is set to {@code false}.</p>
   *
   * @param camelCase
   *     whether to use the camel-case strategy while splitting the string by
   *     character types.
   * @return
   *     the reference to this {@link Splitter} object.
   * @see #byCharTypes()
   */
  public Splitter camelCase(final boolean camelCase) {
    this.camelCase = camelCase;
    return this;
  }

  /**
   * Split the specified string into substrings, according to the previous
   * settings of this {@link Splitter} object.
   *
   * @param str
   *     the specified string to be split. If it is {@code null}, returns an
   *     empty list.
   * @return
   *     the list of split substrings, which cannot be {@code null}, but it may
   *     be an empty list.
   */
  @NotNull
  public List<String> split(@Nullable final CharSequence str) {
    return split(str, new ArrayList<>());
  }

  /**
   * Split the specified string into substrings, according to the previous
   * settings of this {@link Splitter} object.
   *
   * @param str
   *     the specified string to be split. If it is {@code null}, returns an
   *     empty list.
   * @param result
   *     the optional list where to <b>append</b> the splitting result. <b>Note
   *     that the old content of this list will not be cleared.</b> If it is
   *     {@code null}, a new list is created and returned.
   * @return
   *     the list of split substrings, which cannot be {@code null}, but it may
   *     be an empty list.
   */
  @NotNull
  public List<String> split(@Nullable final CharSequence str,
      @Nullable final List<String> result) {
    final List<String> output = (result == null ? new ArrayList<>() : result);
    if (byCharFilter != null) {
      return splitByChar(str, byCharFilter, strip, ignoreEmpty, output);
    } else if (byCodePointFilter != null) {
      return splitByCodePoint(str, byCodePointFilter, strip, ignoreEmpty, output);
    } else if (bySubstring != null) {
      return splitBySubstring(str, bySubstring, strip, ignoreEmpty, ignoreCase, output);
    } else if (toLines) {
      return splitLines(str, strip, ignoreEmpty, output);
    } else if (byWhitespace) {
      return splitByCodePoint(str, WhitespaceCodePointFilter.INSTANCE, strip, true, output);
    } else if (byBlanks) {
      return splitByCodePoint(str, BlankCodePointFilter.INSTANCE, strip, true, output);
    } else if (byCharType) {
      return splitByCharType(str, strip, ignoreEmpty, camelCase, output);
    } else if (toChars) {
      return splitEachChar(str, strip, ignoreEmpty, result);
    } else if (toCodePoints) {
      return splitEachCodePoint(str, strip, ignoreEmpty, result);
    } else {
      throw new IllegalStateException("No split strategy was specified.");
    }
  }
}