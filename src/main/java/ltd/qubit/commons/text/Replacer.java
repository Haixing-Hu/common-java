////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nullable;

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
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectAllCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectSpecifiedCodePointFilter;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;
import static ltd.qubit.commons.text.impl.ReplacerImpl.replaceChar;
import static ltd.qubit.commons.text.impl.ReplacerImpl.replaceCodePoint;
import static ltd.qubit.commons.text.impl.ReplacerImpl.replaceSubstring;

/**
 * A class used to replace contents in strings.
 *
 * @author Haixing Hu
 */
public class Replacer {

  private enum Mode {
    CHAR,
    CODE_POINT,
    SUBSTRING,
    SUBSTRINGS,
  }

  private Mode mode = Mode.SUBSTRING;
  private CharFilter charFilter;
  private CodePointFilter codePointFilter;
  private CharSequence substring = EMPTY;
  private CharSequence[] substrings = EMPTY_STRING_ARRAY;
  private CharSequence replacement;
  private int startIndex = 0;
  private int endIndex = Integer.MAX_VALUE;
  private int limit = Integer.MAX_VALUE;
  private boolean ignoreCase = false;

  public Replacer() {}

  private void clearStrategies() {
    this.mode = Mode.SUBSTRING;
    this.charFilter = null;
    this.codePointFilter = null;
    this.substring = EMPTY;
    this.substrings = EMPTY_STRING_ARRAY;
  }

  /**
   * Replaces a specified character.
   *
   * @param ch
   *     the character to be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForChar(final char ch) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * Replaces all characters not equal to the specified character.
   *
   * @param ch
   *     the specified character. All characters except this one in the source
   *     string will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharNotEqual(final char ch) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    this.charFilter = new RejectSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * Replaces all characters in the specified array.
   *
   * @param chars
   *     the array of characters to be replaced. A {@code null} or empty array
   *     indicates that no character will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharsIn(@Nullable final char... chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Replaces all characters in the specified sequence.
   *
   * @param chars
   *     the sequence of characters to be replaced. A {@code null} or empty
   *     sequence indicates that no character will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Replaces all characters not in the specified array.
   *
   * @param chars
   *     an array of characters. All characters not in this array will be
   *     replaced. A {@code null} or empty array indicates that all characters
   *     in the source string will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharsNotIn(@Nullable final char... chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Replaces all characters not in the specified sequence.
   *
   * @param chars
   *     a sequence of characters. All characters not in this sequence will be
   *     replaced. A {@code null} value or empty sequence indicates that all
   *     characters in the source string will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Replaces all characters satisfying the specified filter.
   *
   * @param filter
   *     the filter accepting characters to be replaced. A {@code null} value
   *     indicates that no character will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharsSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if (filter == null) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = filter;
    }
    return this;
  }

  /**
   * Replaces all characters not satisfying the specified filter.
   *
   * @param filter
   *     the filter rejecting characters to be replaced. A {@code null} value
   *     indicates that all characters in the source string will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCharsNotSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if (filter == null) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * Replaces a specified Unicode character.
   *
   * @param codePoint
   *     the code point of the Unicode character to be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePoint(final int codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Replaces a specified Unicode character.
   *
   * @param codePoint
   *     the character sequence containing the Unicode character to be replaced.
   *     A {@code null} or empty value indicates that no Unicode code point will
   *     be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (codePoint == null || codePoint.length() == 0) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Replaces all Unicode characters not equal to the specified one.
   *
   * @param codePoint
   *     the code point of a specified Unicode character. All Unicode characters
   *     except this one in the source string will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointNotEqual(final int codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Replaces all Unicode characters not equal to the specified one.
   *
   * @param codePoint
   *     a character sequence containing the specified Unicode character. All
   *     Unicode code points except the one in the start of this sequence will
   *     be replaced. A {@code null} value indicates that all Unicode characters
   *     in the source string will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (codePoint == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Replaces all Unicode characters whose code points in the specified array.
   *
   * @param codePoints
   *     the array of code points of the Unicode characters to be replaced. A
   *     {@code null} or empty array indicates that no Unicode character will be
   *     replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointsIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Replaces all Unicode characters whose code points in the specified sequence.
   *
   * @param codePoints
   *     the sequence of code points of Unicode characters to be replaced. A
   *     {@code null} or empty sequence indicates that no Unicode code point
   *     will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if ((codePoints == null) || (codePoints.length() == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Replaces all Unicode characters whose code points not in the specified
   * array.
   *
   * @param codePoints
   *     an array of Unicode code points. All Unicode characters whose code
   *     point not in this array will be replaced. A {@code null} or empty array
   *     indicates that all Unicode code points in the source string will be
   *     replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointsNotIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Replaces all occurrences of a set of Unicode points in a string.
   *
   * @param codePoints
   *     a sequence of code points of Unicode characters. All code points not in
   *     this sequence will be replaced. A {@code null} or empty value indicates
   *     that all Unicode code points will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (codePoints == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Replaces all occurrences of specified Unicode code point in a string.
   *
   * @param filter
   *     the filter accepting Unicode code points to be replaced. A {@code null}
   *     value indicates that no Unicode code point will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointsSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (filter == null) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = filter;
    }
    return this;
  }

  /**
   * Replaces all occurrences of specified Unicode code point in a string.
   *
   * @param filter
   *     the filter rejecting Unicode code points to be replaced. A {@code null}
   *     value indicates that all Unicode code points will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForCodePointsNotSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (filter == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * Replaces all occurrences of the specified substring in a string.
   *
   * @param substring
   *     the substring to be replaced. A {@code null} or empty value indicates
   *     that no substring will be replaced.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer searchForSubstring(final CharSequence substring) {
    this.clearStrategies();
    this.mode = Mode.SUBSTRING;
    this.substring = nullToEmpty(substring);
    return this;
  }

  // public Replacer forSubstringIn(final CharSequence... substrings) {
  //   this.clearStrategies();
  //   this.mode = Mode.SUBSTRINGS;
  //   this.substrings = substrings;
  //   return this;
  // }

  /**
   * Replaces the targets in the source string with the specified character.
   *
   * @param replacement
   *     the specified character used for replacement.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer replaceWithChar(final char replacement) {
    this.replacement = CharUtils.toString(replacement);
    return this;
  }

  /**
   * Replaces the targets in the source string with the specified Unicode code
   * point.
   *
   * @param replacement
   *     the specified Unicode code point used for replacement.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer replaceWithCodePoint(final int replacement) {
    this.replacement = Character.toString(replacement);
    return this;
  }

  /**
   * Replaces the targets in the source string with the specified Unicode code
   * point.
   *
   * @param replacement
   *     the character sequence containing the specified Unicode code point
   *     used for replacement. A {@code null} or empty value indicates that
   *     replace the targets with an empty string, i.e., removes the targets.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer replaceWithCodePoint(@Nullable final CharSequence replacement) {
    if (replacement == null || replacement.length() == 0) {
      this.replacement = EMPTY;
    } else {
      final int cp = Character.codePointAt(replacement, 0);
      this.replacement = Character.toString(cp);
    }
    return this;
  }

  /**
   * Replaces the targets in the source string with the specified character
   * sequence.
   *
   * @param replacement
   *     the specified character sequence used for replacement. A {@code null}
   *     value indicates that replace the targets with an empty string, i.e.,
   *     removes the targets.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer replaceWithString(@Nullable final CharSequence replacement) {
    this.replacement = defaultIfNull(replacement, EMPTY);
    return this;
  }

  /**
   * Sets the inclusive index in the source string where to start the replacement.
   *
   * @param startIndex
   *     the inclusive index in the source string where to start the replacement.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer startFrom(final int startIndex) {
    this.startIndex = startIndex;
    return this;
  }

  /**
   * Sets the exclusive index in the source string where to end the replacement.
   *
   * @param endIndex
   *     the exclusive index in the source string where to end the replacement.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer endBefore(final int endIndex) {
    this.endIndex = endIndex;
    return this;
  }

  /**
   * Sets the maximum number of occurrences of the target to be replaced.
   *
   * @param limit
   *     the maximum number of occurrences of the target to be replaced. A
   *     negative value indicates no limit.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer limit(final int limit) {
    this.limit = (limit < 0 ? Integer.MAX_VALUE : limit);
    return this;
  }

  /**
   * Sets whether the comparison should ignore case (case insensitive) or not.
   *
   * @param ignoreCase
   *     indicates whether the comparison should ignore case (case insensitive)
   *     or not.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Replacer ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * Performs the replacement on the specified source character sequence.
   *
   * @param str
   *     the specified source character sequence. If it is {@code null} or empty,
   *     this function does not preform any replacement.
   * @return
   *     the result of replacement, or {@code null} if {@code str} is {@code null}.
   */
  @Nullable
  public String applyTo(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return EMPTY;
    }
    if ((limit == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return str.toString();
    }
    final StringBuilder builder = new StringBuilder();
    applyTo(str, builder);
    return builder.toString();
  }

  /**
   * Performs the replacement on the specified source character sequence.
   *
   * @param str
   *     the specified source character sequence. If it is {@code null} or empty,
   *     this function does not preform any replacement.
   * @param output
   *     the {@link StringBuilder} where to append the result of replacement.
   * @return
   *     the number of occurrences have been replaced, or {@code 0} if
   *     {@code str} is {@code null}.
   */
  public int applyTo(@Nullable final CharSequence str, final StringBuilder output) {
    try {
      return applyTo(str, (Appendable) output);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Performs the replacement on the specified source character sequence.
   *
   * @param str
   *     the specified source character sequence. If it is {@code null} or empty,
   *     this function does not preform any replacement.
   * @param output
   *     the {@link Appendable} where to append the result of replacement.
   * @return
   *     the number of occurrences have been replaced, or {@code 0} if
   *     {@code str} is {@code null}.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public int applyTo(@Nullable final CharSequence str, final Appendable output)
      throws IOException {
    if (str == null) {
      return 0;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return 0;
    }
    if ((limit == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      output.append(str);
      return 0;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (mode) {
      case CHAR:
        return replaceChar(str, start, end, charFilter, replacement, limit, output);
      case CODE_POINT:
        return replaceCodePoint(str, start, end, codePointFilter, replacement, limit, output);
      case SUBSTRING:
        return replaceSubstring(str, start, end, substring, ignoreCase, replacement, limit, output);
      default:
        throw new IllegalStateException("No replace target was specified.");
    }
  }
}