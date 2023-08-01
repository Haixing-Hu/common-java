////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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

import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;
import static ltd.qubit.commons.text.impl.RemoverImpl.removeChar;
import static ltd.qubit.commons.text.impl.RemoverImpl.removeCodePoint;
import static ltd.qubit.commons.text.impl.RemoverImpl.removePrefix;
import static ltd.qubit.commons.text.impl.RemoverImpl.removePrefixAndSuffix;
import static ltd.qubit.commons.text.impl.RemoverImpl.removeSubstring;
import static ltd.qubit.commons.text.impl.RemoverImpl.removeSuffix;

/**
 * A class used for remove contents from strings.
 *
 * @author Haixing Hu
 */
public class Remover {

  private CharFilter charFilter;
  private CodePointFilter codePointFilter;
  private CharSequence substring;
  private CharSequence prefix;
  private CharSequence suffix;
  private int startIndex = 0;
  private int endIndex = Integer.MAX_VALUE;
  private boolean ignoreCase = false;
  private int limit = Integer.MAX_VALUE;

  public Remover() {}

  private void clearStrategies() {
    this.charFilter = null;
    this.codePointFilter = null;
    this.substring = null;
    this.prefix = null;
    this.suffix = null;
  }

  /**
   * Removes a specified character.
   *
   * @param ch
   *     the specified character to be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forChar(final char ch) {
    this.clearStrategies();
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * Removes all characters not equal to the specified character.
   *
   * @param ch
   *     the specified character. All characters except this one in the source
   *     string will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsNotEqual(final char ch) {
    this.clearStrategies();
    this.charFilter = new RejectSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * Removes all characters in the specified array.
   *
   * @param chars
   *     the array of characters to be removed. A {@code null} or empty array
   *     indicates that no character will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Removes all characters in the specified sequence.
   *
   * @param chars
   *     the sequence of characters to be removed. A {@code null} or empty
   *     sequence indicates that no character will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Removes all characters not in the specified array.
   *
   * @param chars
   *     an array of characters. All characters not in this array will be
   *     removed. A {@code null} or empty array indicates that all characters
   *     in the source string will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsNotIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * Removes all characters not in the specified sequence.
   *
   * @param chars
   *     a sequence of characters. All characters not in this sequence will be
   *     removed. A {@code null} value or empty sequence indicates that all
   *     characters in the source string will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * Removes all characters satisfying the specified filter.
   *
   * @param filter
   *     the filter accepting characters to be removed. A {@code null} value
   *     indicates that no character will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = filter;
    }
    return this;
  }

  /**
   * Removes all characters not satisfying the specified filter.
   *
   * @param filter
   *     the filter rejecting characters to be removed. A {@code null} value
   *     indicates that all characters in the source string will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCharsNotSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * Removes a specified Unicode character.
   *
   * @param codePoint
   *     the code point of the Unicode character to be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePoint(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Removes a specified Unicode character.
   *
   * @param codePoint
   *     a character sequence containing the Unicode character to be removed.
   *     A {@code null} or empty value indicates that no Unicode code point will
   *     be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
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
   *     except this one in the source string will be removed.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Remover forCodePointNotEqual(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * Replaces all Unicode characters not equal to the specified one.
   *
   * @param codePoint
   *     a character sequence containing the specified Unicode character. All
   *     Unicode code points except the one in the start of this sequence will
   *     be removed. A {@code null} value indicates that all Unicode characters
   *     in the source string will be removed.
   * @return
   *     the reference to this {@link Replacer} object.
   */
  public Remover forCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * Removes all Unicode characters whose code points in the specified array.
   *
   * @param codePoints
   *     the array of code points of the Unicode characters to be removed. A
   *     {@code null} or empty array indicates that no Unicode character will be
   *     removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePointsIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Removes all Unicode characters whose code points in the specified sequence.
   *
   * @param codePoints
   *     the sequence of code points of Unicode characters to be removed. A
   *     {@code null} or empty sequence indicates that no Unicode code point
   *     will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length() == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Removes all Unicode characters whose code points not in the specified
   * array.
   *
   * @param codePoints
   *     an array of Unicode code points. All Unicode characters whose code
   *     point not in this array will be removed. A {@code null} or empty array
   *     indicates that all Unicode code points in the source string will be
   *     removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePointsNotIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Removes all Unicode code points not in a sequence from a string.
   *
   * @param codePoints
   *     a sequence of code points of Unicode characters. All code points not in
   *     this sequence will be removed. A {@code null} or empty value indicates
   *     that all Unicode code points will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * Removes all Unicode code points accepted by the specified filter from a
   * string.
   *
   * @param filter
   *     the filter accepting Unicode code points to be removed. A {@code null}
   *     value indicates that no Unicode code point will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePointsSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = filter;
    }
    return this;
  }

  /**
   * Removes all Unicode code points rejected by the specified filter from a
   * string.
   *
   * @param filter
   *     the filter rejecting Unicode code points to be removed. A {@code null}
   *     value indicates that all Unicode code points will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forCodePointsNotSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * Removes the specified substring.
   *
   * @param substring
   *     the substring to be removed. A {@code null} or empty value indicates
   *     that no substring will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forSubstring(@Nullable final CharSequence substring) {
    this.clearStrategies();
    this.substring = nullToEmpty(substring);
    return this;
  }

  /**
   * Removes the specified prefix.
   *
   * @param prefix
   *     the prefix to be removed. A {@code null} or empty value indicates that
   *     no prefix will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forPrefix(@Nullable final CharSequence prefix) {
    // keep the old suffix, since we could remove both prefix and suffix
    final CharSequence oldSuffix = this.suffix;
    this.clearStrategies();
    this.prefix = nullToEmpty(prefix);
    this.suffix = oldSuffix;
    return this;
  }

  /**
   * Removes the specified suffix.
   *
   * @param suffix
   *     the suffix to be removed. A {@code null} or empty value indicates that
   *     no suffix will be removed.
   * @return
   *     the reference to this {@link Remover} object.
   */
  public Remover forSuffix(@Nullable final CharSequence suffix) {
    // keep the old prefix, since we could remove both prefix and suffix
    final CharSequence oldPrefix = this.prefix;
    this.clearStrategies();
    this.prefix = oldPrefix;
    this.suffix = nullToEmpty(suffix);
    return this;
  }

  public Remover startFrom(final int startIndex) {
    this.startIndex = startIndex;
    return this;
  }

  public Remover endBefore(final int endIndex) {
    this.endIndex = endIndex;
    return this;
  }

  public Remover ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  public Remover limit(final int limit) {
    this.limit = (limit < 0 ? Integer.MAX_VALUE : limit);
    return this;
  }

  public String removeFrom(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int strLen;
    if ((strLen = str.length()) == 0) {
      return EMPTY;
    }
    if ((startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)
        || (limit == 0)
        || (substring != null && substring.length() == 0)) {
      return str.toString();
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    final StringBuilder builder = new StringBuilder();
    try {
      if (charFilter != null) {
        removeChar(str, start, end, charFilter, limit, builder);
      } else if (codePointFilter != null) {
        removeCodePoint(str, start, end, codePointFilter, limit, builder);
      } else if (substring != null) {
        removeSubstring(str, start, end, substring, ignoreCase, limit, builder);
      } else if (prefix != null && suffix != null) {
        removePrefixAndSuffix(str, start, end, prefix, suffix, ignoreCase, builder);
      } else if (prefix != null) {
        removePrefix(str, start, end, prefix, ignoreCase, builder);
      } else if (suffix != null) {
        removeSuffix(str, start, end, suffix, ignoreCase, builder);
      } else {
        throw new IllegalStateException("No search target was specified.");
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
    return builder.toString();
  }

  public int removeFrom(@Nullable final CharSequence str, final StringBuilder output) {
    try {
      return removeFrom(str, (Appendable) output);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public int removeFrom(@Nullable final CharSequence str, final Appendable output)
      throws IOException {
    if (str == null) {
      return 0;
    }
    final int strLen;
    if ((strLen = str.length()) == 0) {
      return 0;
    }
    if ((startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)
        || (limit == 0)
        || (substring != null && substring.length() == 0)) {
      output.append(str);
      return 0;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    final int result;
    if (charFilter != null) {
      return removeChar(str, start, end, charFilter, limit, output);
    } else if (codePointFilter != null) {
      return removeCodePoint(str, start, end, codePointFilter, limit, output);
    } else if (substring != null) {
      return removeSubstring(str, start, end, substring, ignoreCase, limit, output);
    } else if (prefix != null && suffix != null) {
      return removePrefixAndSuffix(str, start, end, prefix, suffix, ignoreCase, output);
    } else if (prefix != null) {
      return removePrefix(str, start, end, prefix, ignoreCase, output);
    } else if (suffix != null) {
      return removeSuffix(str, start, end, suffix, ignoreCase, output);
    } else {
      throw new IllegalStateException("No search target was specified.");
    }
  }
}
