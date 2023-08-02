////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.filter.character.AcceptAllCharFilter;
import ltd.qubit.commons.util.filter.character.AcceptSpecifiedCharFilter;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.character.InArrayCharFilter;
import ltd.qubit.commons.util.filter.character.InStringCharFilter;
import ltd.qubit.commons.util.filter.character.NotInArrayCharFilter;
import ltd.qubit.commons.util.filter.character.NotInStringCharFilter;
import ltd.qubit.commons.util.filter.character.RejectAllCharFilter;
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

import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.text.impl.SearcherImpl.afterLastIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithCodePoint;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithCodePoint;

/**
 * A class used for stripping strings.
 *
 * <p>Usage examples:</p>
 * <pre>{@code
 *  result = new Stripper().ofBlank().strip(str);
 *  result = new Stripper().ofBlank().startFrom(start).endBefore(end).strip(str);
 *  result = new Stripper().ofBlank().fromStart().strip(str);
 *  result = new Stripper().ofBlank().fromBothSide().strip(str);
 *  result = new Stripper().ofChar(' ').strip(str);
 *  result = new Stripper().ofCharsNotEqual('x').strip(str);
 *  result = new Stripper().ofCharsIn([' ', '#']).strip(str);
 *  result = new Stripper().ofCharsNotIn(['0', '1']).strip(str);
 *  result = new Stripper().ofCharsSatisfy(filter).strip(str);
 *  result = new Stripper().ofCharsNotSatisfy(filter).strip(str);
 *  result = new Stripper().ofCodePoint("\uD83D\uDD6E").strip(str);
 *  result = new Stripper().ofCodePoint("\uD83D\uDD6E").fromAnySide().isStrippable(str);
 * }</pre>
 *
 * @author Haixing Hu
 */
public class Stripper {

  private static final int DIRECTION_START = 0;
  private static final int DIRECTION_END = 1;
  private static final int DIRECTION_BOTH = 2;
  private static final int DIRECTION_ANY = 3;

  private CharFilter charFilter = null;
  private CodePointFilter codePointFilter = BlankCodePointFilter.INSTANCE;
  private int direction = DIRECTION_BOTH;

  public Stripper() {}

  private void clearStrategies() {
    this.charFilter = null;
    this.codePointFilter = null;
  }

  /**
   * Strips the specified character.
   *
   * @param ch
   *     the specified character to be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofChar(final char ch) {
    this.clearStrategies();
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * Strips any character not equal to the specified character.
   *
   * @param ch
   *     the specified character. All characters except this one in the source
   *     string will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsNotEqual(final char ch) {
    this.clearStrategies();
    this.charFilter = CharFilter.not(new AcceptSpecifiedCharFilter(ch));
    return this;
  }

  /**
   * Strips any character in the specified array.
   *
   * @param chars
   *     the array of characters to be stripped. A {@code null} or empty array
   *     indicates that no character will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Strips any character in the specified sequence.
   *
   * @param chars
   *     the sequence of characters to be stripped. A {@code null} or empty
   *     sequence indicates that no character will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Strips any character not in the specified array.
   *
   * @param chars
   *     an array of characters. All characters not in this array will be
   *     stripped. A {@code null} or empty array indicates that all characters
   *     in the source string will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsNotIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Strips any character not in the specified sequence.
   *
   * @param chars
   *     a sequence of characters. All characters not in this sequence will be
   *     stripped. A {@code null} value or empty sequence indicates that all
   *     characters in the source string will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Strips any character accepted by the specified filter.
   *
   * @param filter
   *     the filter accepting characters to be stripped. A {@code null} value
   *     indicates that no character will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = filter;
    }
    return this;
  }

  /**
   * Strips any character rejected by the specified filter.
   *
   * @param filter
   *     the filter rejecting characters to be stripped. A {@code null} value
   *     indicates that all characters in the source string will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCharsNotSatisfy(final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * Strips the specified Unicode code point.
   *
   * @param codePoint
   *     the specified Unicode code point to be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePoint(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Strips the specified Unicode code point.
   *
   * @param codePoint
   *     a character sequence containing the Unicode character to be stripped.
   *     A {@code null} or empty value indicates that no Unicode code point will
   *     be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Strips any Unicode code point not equal to the specified code point.
   *
   * @param codePoint
   *     the code point of a specified Unicode character. All Unicode characters
   *     except this one in the source string will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsNotEqual(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Strips the specified Unicode code point.
   *
   * @param codePoint
   *     a character sequence containing the specified Unicode character. All
   *     Unicode code points except the one in the start of this sequence will
   *     be stripped. A {@code null} value indicates that all Unicode characters
   *     in the source string will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Strips any Unicode code point in the specified array.
   *
   * @param codePoints
   *     the array of code points of the Unicode characters to be stripped. A
   *     {@code null} or empty array indicates that no Unicode character will be
   *     stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Strips any Unicode code point in the specified sequence.
   *
   * @param codePoints
   *     the sequence of code points of Unicode characters to be stripped. A
   *     {@code null} or empty sequence indicates that no Unicode code point
   *     will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length() == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Strips any Unicode code point not in the specified array.
   *
   * @param codePoints
   *     an array of Unicode code points. All Unicode characters whose code
   *     point not in this array will be stripped. A {@code null} or empty array
   *     indicates that all Unicode code points in the source string will be
   *     stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsNotIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Strips any Unicode code point not in the specified sequence.
   *
   * @param codePoints
   *     a sequence of code points of Unicode characters. All code points not in
   *     this sequence will be stripped. A {@code null} or empty value indicates
   *     that all Unicode code points will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Strips any Unicode code point accepted by the specified filter.
   *
   * @param filter
   *     the filter accepting Unicode code points to be stripped. A {@code null}
   *     value indicates that no Unicode code point will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsSatisfy(final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = filter;
    }
    return this;
  }

  /**
   * Strips any Unicode code point rejected by the specified filter.
   *
   * @param filter
   *     the filter rejecting Unicode code points to be stripped. A {@code null}
   *     value indicates that all Unicode code points will be stripped.
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper ofCodePointsNotSatisfy(final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * Strips all blank (i.e., non-printable or white spaces) Unicode code points.
   *
   * @return
   *     the reference to this {@link Stripper} object.
   * @see ltd.qubit.commons.lang.CharUtils#isBlank(int)
   */
  public Stripper ofBlank() {
    this.clearStrategies();
    this.codePointFilter = BlankCodePointFilter.INSTANCE;
    return this;
  }

  /**
   * Strips all whitespace Unicode code points.
   *
   * @return
   *     the reference to this {@link Stripper} object.
   * @see Character#isWhitespace(int)
   */
  public Stripper ofWhitespace() {
    this.clearStrategies();
    this.codePointFilter = WhitespaceCodePointFilter.INSTANCE;
    return this;
  }

  /**
   * Strips from the start of the source string.
   *
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper fromStart() {
    this.direction = DIRECTION_START;
    return this;
  }

  /**
   * Strips from the end of the source string.
   *
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper fromEnd() {
    this.direction = DIRECTION_END;
    return this;
  }

  /**
   * Strips from both the start and the end of the source string.
   *
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper fromBothSide() {
    this.direction = DIRECTION_BOTH;
    return this;
  }

  /**
   * Strips from either the start or the end of the source string.
   *
   * @return
   *     the reference to this {@link Stripper} object.
   */
  public Stripper fromAnySide() {
    this.direction = DIRECTION_ANY;
    return this;
  }

  /**
   * Strips the target from the specified source string.
   *
   * @param str
   *     the specified source string.
   * @return
   *     the stripping result. If {@code str} is {@code null}, returns {@code null}.
   */
  @Nullable
  public String strip(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    if (str.length() == 0) {
      return EMPTY;
    }
    switch (direction) {
      case DIRECTION_START:
        return stripStart(str);
      case DIRECTION_END:
        return stripEnd(str);
      case DIRECTION_BOTH:
        return stripBothSide(str);
      default:
        throw new IllegalStateException("Cannot strip strings from ANY side. "
            + "Must strip strings either from start, or from end, or from BOTH side.");
    }
  }

  /**
   * Strips the target from the specified source string.
   *
   * @param str
   *     the specified source string. If it is {@code null}, this function has
   *     no effect and returns 0.
   * @param output
   *     the {@link StringBuilder} where to append the stripping result.
   * @return
   *     the number of targets stripped. returns {@code null}. If {@code str}
   *     is {@code null}, this function has no effect and returns 0.
   */
  public int strip(@Nullable final CharSequence str, final StringBuilder output) {
    try {
      return strip(str, (Appendable) output);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Strips the target from the specified source string.
   *
   * @param str
   *     the specified source string. If it is {@code null}, this function has
   *     no effect and returns 0.
   * @param output
   *     the {@link Appendable} where to append the stripping result.
   * @return
   *     the number of targets stripped. returns {@code null}. If {@code str}
   *     is {@code null}, this function has no effect and returns 0.
   */
  public int strip(@Nullable final CharSequence str, final Appendable output)
      throws IOException {
    if (str == null) {
      return 0;
    }
    if (str.length() == 0) {
      return 0;
    }
    switch (direction) {
      case DIRECTION_START:
        return stripStart(str, output);
      case DIRECTION_END:
        return stripEnd(str, output);
      case DIRECTION_BOTH:
        return stripBothSide(str, output);
      default:
        throw new IllegalStateException("Cannot strip strings from ANY side. "
            + "Must strip strings either from start, or from end, or from BOTH side.");
    }
  }

  /**
   * Strips the target from the specified source string. Returns {@code null} if
   * the source string is stripped to an empty string.
   *
   * @param str
   *     the specified source string.
   * @return
   *     the stripping result, or {@code null} if {@code str} is tripped to an
   *     empty string. If {@code str} is {@code null}, returns {@code null}.
   */
  public String stripToNull(@Nullable final CharSequence str) {
    final String result = strip(str);
    if (isEmpty(result)) {
      return null;
    } else {
      return result;
    }
  }

  /**
   * Strips the target from the specified source string. Returns an empty string
   * if the source string is {@code null}.
   *
   * @param str
   *     the specified source string.
   * @return
   *     the stripping result, or an empty string if {@code str} is {@code null}.
   */
  public String stripToEmpty(@Nullable final CharSequence str) {
    final String result = strip(str);
    if (isEmpty(result)) {
      return EMPTY;
    } else {
      return result;
    }
  }

  /**
   * Tests whether the source string is strippable for the specified target in
   * the specified side.
   *
   * @param str
   *     the specified source string.
   * @return
   *     whether the source string is strippable for the specified target in the
   *     specified side.
   */
  public boolean isStrippable(@Nullable final CharSequence str) {
    if (str == null || str.length() == 0) {
      return false;
    }
    switch (direction) {
      case DIRECTION_START:
        return isStartStrippable(str);
      case DIRECTION_END:
        return isEndStrippable(str);
      case DIRECTION_BOTH:
        return isBothSideStrippable(str);
      case DIRECTION_ANY:
      default:
        return isAnySideStrippable(str);
    }
  }

  private String stripStart(final CharSequence str) {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= len) {
      return EMPTY;
    } else if (start == 0) {
      return str.toString();
    } else {
      return str.subSequence(start, len).toString();
    }
  }

  private int stripStart(final CharSequence str, final Appendable output)
      throws IOException {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= len) {
      return 0;
    } else if (start == 0) {
      output.append(str);
      return len;
    } else {
      output.append(str, start, len);
      return len - start;
    }
  }

  private String stripEnd(final CharSequence str) {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (end <= 0) {
      return EMPTY;
    } else if (end == len) {
      return str.toString();
    } else {
      return str.subSequence(0, end).toString();
    }
  }

  private int stripEnd(final CharSequence str, final Appendable output)
      throws IOException {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (end <= 0) {
      return 0;
    } else if (end == len) {
      output.append(str);
      return len;
    } else {
      output.append(str, 0, end);
      return end;
    }
  }

  private String stripBothSide(final CharSequence str) {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= end) {
      return EMPTY;
    } else if ((start == 0) && (end == len)) {
      return str.toString();
    } else {
      return str.subSequence(start, end).toString();
    }
  }

  private int stripBothSide(final CharSequence str, final Appendable output)
      throws IOException {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= end) {
      return 0;
    } else if ((start == 0) && (end == len)) {
      output.append(str);
      return len;
    } else {
      output.append(str, start, end);
      return end - start;
    }
  }

  private boolean isStartStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return startsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return startsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }

  private boolean isEndStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return endsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return endsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }

  private boolean isBothSideStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return startsWithChar(str, 0, str.length(), charFilter)
          && endsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return startsWithCodePoint(str, 0, str.length(), codePointFilter)
          && endsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }

  private boolean isAnySideStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return startsWithChar(str, 0, str.length(), charFilter)
          || endsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return startsWithCodePoint(str, 0, str.length(), codePointFilter)
          || endsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }
}
