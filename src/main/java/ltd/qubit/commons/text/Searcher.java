////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.Nullable;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.lang.ArrayUtils;
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

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_INT_ARRAY;
import static ltd.qubit.commons.text.impl.SearcherImpl.countMatchesOfAnySubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.countMatchesOfChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.countMatchesOfCodePoint;
import static ltd.qubit.commons.text.impl.SearcherImpl.countMatchesOfSubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithAnySubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithCodePoint;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithSubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOfAnySubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.getOccurrencesOfAnySubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.getOccurrencesOfChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.getOccurrencesOfCodePoint;
import static ltd.qubit.commons.text.impl.SearcherImpl.getOccurrencesOfSubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.lastIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.lastIndexOfAnySubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithAnySubstring;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithCodePoint;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithSubstring;

/**
 * A class used to search in strings.
 *
 * @author Haixing Hu
 */
public class Searcher {

  private enum Target {
    CHAR,
    CODE_POINT,
    SUBSTRING,
    SUBSTRINGS,
  }

  private CharFilter charFilter;
  private CodePointFilter codePointFilter;
  private CharSequence substring;
  private CharSequence[] substrings;
  private int startIndex = 0;
  private int endIndex = Integer.MAX_VALUE;
  private boolean ignoreCase = false;
  private Target target = null;

  public Searcher() {}

  private void clearStrategies() {
    charFilter = null;
    codePointFilter = null;
    substring = null;
    substrings = null;
    target = null;
  }

  /**
   * Searches for the specified character.
   *
   * @param ch
   *     the character to find.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forChar(final char ch) {
    this.clearStrategies();
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character not equal to the specified character.
   *
   * @param ch
   *     the specified character. All characters except this one in the source
   *     string will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsNotEqual(final char ch) {
    this.clearStrategies();
    this.charFilter = new RejectSpecifiedCharFilter(ch);
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character of an array.
   *
   * @param chars
   *     the array of characters to be searched. A {@code null} or empty array
   *     indicates that no character will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InArrayCharFilter(chars);
    }
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character of a sequence.
   *
   * @param chars
   *     the sequence of characters to be searched. A {@code null} or empty
   *     sequence indicates that no character will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InStringCharFilter(chars);
    }
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character not in an array.
   *
   * @param chars
   *     an array of characters. All characters not in this array will be
   *     searched. A {@code null} or empty array indicates that all characters
   *     in the source string will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsNotIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInArrayCharFilter(chars);
    }
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character not in a sequence.
   *
   * @param chars
   *     a sequence of characters. All characters not in this sequence will be
   *     searched. A {@code null} value or empty sequence indicates that all
   *     characters in the source string will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInStringCharFilter(chars);
    }
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character satisfying the specified filter.
   *
   * @param filter
   *     the filter accepting characters to be searched. A {@code null} value
   *     indicates that no character will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = filter;
    }
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for any character not satisfying the specified filter.
   *
   * @param filter
   *     the filter rejecting characters to be searched. A {@code null} value
   *     indicates that all characters in the source string will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCharsNotSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = CharFilter.not(filter);
    }
    this.target = Target.CHAR;
    return this;
  }

  /**
   * Searches for the specified Unicode code point.
   *
   * @param codePoint
   *     the code point of the Unicode character to search.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePoint(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for the specified Unicode code point.
   *
   * @param codePoint
   *     a character sequence containing the Unicode character to be searched.
   *     A {@code null} or empty value indicates that no Unicode code point will
   *     be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for any Unicode code point not equal to the specified code point.
   *
   * @param codePoint
   *     the code point of a specified Unicode character. All Unicode characters
   *     except this one in the source string will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsNotEqual(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for any Unicode code point not equal to the specified code point.
   *
   * @param codePoint
   *     a character sequence containing the specified Unicode character. All
   *     Unicode code points except the one in the start of this sequence will
   *     be searched. A {@code null} value indicates that all Unicode characters
   *     in the source string will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for any Unicode code point occur within an array.
   *
   * @param codePoints
   *     the array of code points of the Unicode characters to be searched. A
   *     {@code null} or empty array indicates that no Unicode character will be
   *     searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InArrayCodePointFilter(codePoints);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for any Unicode code point occur within a sequence.
   *
   * @param codePoints
   *     the sequence of code points of Unicode characters to be searched. A
   *     {@code null} or empty sequence indicates that no Unicode code point
   *     will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length() == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InStringCodePointFilter(codePoints);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for any Unicode code point not in the specified array.
   *
   * @param codePoints
   *     an array of Unicode code points. All Unicode characters whose code
   *     point not in this array will be searched. A {@code null} or empty array
   *     indicates that all Unicode code points in the source string will be
   *     searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsNotIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Searches for any Unicode code point not in the specified sequence.
   *
   * @param codePoints
   *     a sequence of code points of Unicode characters. All code points not in
   *     this sequence will be searched. A {@code null} or empty value indicates
   *     that all Unicode code points will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Search for any Unicode code point accepted by the specified filter.
   *
   * @param filter
   *     the filter accepting Unicode code points to be searched. A {@code null}
   *     value indicates that no Unicode code point will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = filter;
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Search for any Unicode code point rejected by the specified filter.
   *
   * @param filter
   *     the filter rejecting Unicode code points to be searched. A {@code null}
   *     value indicates that all Unicode code points will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forCodePointsNotSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = CodePointFilter.not(filter);
    }
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * Search for the specified substring.
   *
   * @param substring
   *     the substring to be searched. A {@code null} or empty value indicates
   *     that no substring will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forSubstring(@Nullable final CharSequence substring) {
    this.clearStrategies();
    this.substring = substring;
    this.target = Target.SUBSTRING;
    return this;
  }

  /**
   * Search for any substring in a set of potential substrings.
   *
   * @param substrings
   *     the array of substrings to search. A {@code null} or empty value indicates
   *     that no substring will be searched.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher forSubstringsIn(@Nullable final CharSequence... substrings) {
    this.clearStrategies();
    this.substrings = ArrayUtils.nullToEmpty(substrings);
    this.target = Target.SUBSTRINGS;
    return this;
  }

  /**
   * Sets the starting index of the source string where to start searching.
   *
   * @param startIndex
   *     the starting index of the source string where to start searching.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher startFrom(final int startIndex) {
    this.startIndex = startIndex;
    return this;
  }

  /**
   * Sets the ending index of the source string where to stop searching.
   *
   * @param endIndex
   *     the ending index of the source string where to stop searching.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher endBefore(final int endIndex) {
    this.endIndex = endIndex;
    return this;
  }

  /**
   * Sets whether to ignore the case while searching for substrings.
   *
   * @param ignoreCase
   *     whether to ignore the case while searching for substrings.
   * @return
   *     the reference to this {@link Searcher} object.
   */
  public Searcher ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * Finds the first index of the specified target (which may be a character,
   * a Unicode code point, or a substring) in the specified source string.
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns -1.
   * @return
   *     the first index of the specified target in the specified source string.
   *     Or -1 if no such target found.
   */
  public int findFirstIndexIn(@Nullable final CharSequence str) {
    final int strLen;
    if ((str == null)
        || ((strLen = str.length()) == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return -1;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    final int result;
    switch (target) {
      case CHAR:
        result = firstIndexOf(str, start, end, charFilter);
        break;
      case CODE_POINT:
        result = firstIndexOf(str, start, end, codePointFilter);
        break;
      case SUBSTRING:
        if (substring == null) {
          return -1;
        }
        result = firstIndexOf(str, start, end, substring, ignoreCase);
        break;
      case SUBSTRINGS:
        result = firstIndexOfAnySubstring(str, start, end, substrings, ignoreCase);
        break;
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
    return (result < end ? result : -1);
  }

  public int findLastIndexIn(@Nullable final CharSequence str) {
    final int strLen;
    if ((str == null)
        || ((strLen = str.length()) == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return -1;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    final int result;
    switch (target) {
      case CHAR:
        result = lastIndexOf(str, start, end, charFilter);
        break;
      case CODE_POINT:
        result = lastIndexOf(str, start, end, codePointFilter);
        break;
      case SUBSTRING:
        if (substring == null) {
          return -1;
        }
        result = lastIndexOf(str, start, end, substring, ignoreCase);
        break;
      case SUBSTRINGS:
        result = lastIndexOfAnySubstring(str, start, end, substrings, ignoreCase);
        break;
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
    return (result >= start ? result : -1);
  }

  /**
   * Tests whether the specified target (which may be a character, a Unicode
   * code point, or a substring) is contained in the specified source string.
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns false.
   * @return
   *     whether the specified target (which may be a character, a Unicode code
   *     point, or a substring) is contained in the specified source string.
   */
  public boolean isContainedIn(@Nullable final CharSequence str) {
    return findFirstIndexIn(str) >= 0;
  }

  /**
   * Tests whether the specified target (which may be a character, a Unicode
   * code point, or a substring) is <b>NOT</b> contained in the specified source
   * string.
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns false.
   * @return
   *     whether the specified target (which may be a character, a Unicode code
   *     point, or a substring) is <b>NOT</b> contained in the specified source
   *     string.
   */
  public boolean isNotContainedIn(@Nullable final CharSequence str) {
    return findFirstIndexIn(str) < 0;
  }

  /**
   * Counts the number of matches of the specified target (which may be a
   * character, a Unicode code point, or a substring) in the specified source
   * string.
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns 0.
   * @return
   *     the number of matches of the specified target in the specified source
   *     string.
   */
  public int countMatchesIn(@Nullable final CharSequence str) {
    final int strLen;
    if ((str == null)
        || ((strLen = str.length()) == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return 0;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (target) {
      case CHAR:
        return countMatchesOfChar(str, start, end, charFilter);
      case CODE_POINT:
        return countMatchesOfCodePoint(str, start, end, codePointFilter);
      case SUBSTRING:
        if (substring == null || substring.length() == 0) {
          return 0;
        }
        return countMatchesOfSubstring(str, start, end, substring, ignoreCase);
      case SUBSTRINGS:
        return countMatchesOfAnySubstring(str, start, end, substrings, ignoreCase);
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
  }

  /**
   * Gets the index of all occurrences of the specified target (which may be a
   * character, a Unicode code point, or a substring) in the specified source
   * string.
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns an empty array.
   * @return
   *     the index of all occurrences of the specified target in the specified
   *     source string.
   */
  public int[] getOccurrencesIn(@Nullable final CharSequence str) {
    final int strLen;
    if ((str == null)
        || ((strLen = str.length()) == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return EMPTY_INT_ARRAY;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    final IntList result = new IntArrayList();
    switch (target) {
      case CHAR:
        getOccurrencesOfChar(str, start, end, charFilter, result);
        break;
      case CODE_POINT:
        getOccurrencesOfCodePoint(str, start, end, codePointFilter, result);
        break;
      case SUBSTRING:
        if (substring == null) {
          return EMPTY_INT_ARRAY;
        }
        getOccurrencesOfSubstring(str, start, end, substring, ignoreCase, result);
        break;
      case SUBSTRINGS:
        getOccurrencesOfAnySubstring(str, start, end, substrings, ignoreCase, result);
        break;
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
    return result.toArray();
  }

  /**
   * Gets the index of all occurrences of the specified target (which may be a
   * character, a Unicode code point, or a substring) in the specified source
   * string.
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     {@code null} or empty, nothing is appended to the returned list.
   * @param output
   *     the optional {@link IntList} where to append the result. If it is
   *     {@code null}, a new {@link IntList} is created to store the result
   *     and is returned.
   * @return
   *     the list of indexes of all occurrences of the specified target in the
   *     specified source string. If the argument {@code output} is not
   *     {@code null}, the function use the {@code output} to append the list
   *     of indexes and returns the {@code output}; otherwise, a new
   *     {@link IntList} is created to store the list of indexes and is
   *     returned.
   */
  public IntList getOccurrencesIn(@Nullable final CharSequence str,
      @Nullable final IntList output) {
    final IntList result = (output == null ? new IntArrayList() : output);
    final int strLen;
    if ((str == null)
        || ((strLen = str.length()) == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return result;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (target) {
      case CHAR:
        getOccurrencesOfChar(str, start, end, charFilter, result);
        break;
      case CODE_POINT:
        getOccurrencesOfCodePoint(str, start, end, codePointFilter, result);
        break;
      case SUBSTRING:
        if (substring == null) {
          return result;
        }
        getOccurrencesOfSubstring(str, start, end, substring, ignoreCase, result);
        break;
      case SUBSTRINGS:
        getOccurrencesOfAnySubstring(str, start, end, substrings, ignoreCase, result);
        break;
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
    return result;
  }

  /**
   * Tests whether the specified string starts with the specified target (which
   * may be a character, a Unicode code point, or a substring).
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns false.
   * @return
   *     whether the specified string starts with the specified target.
   */
  public boolean isAtStartOf(@Nullable final CharSequence str) {
    if (str == null) {
      return ((target == Target.SUBSTRING) && (substring == null));
    }
    final int strLen = str.length();
    if (strLen == 0) {
      switch (target) {
        case CHAR:
        case CODE_POINT:
          return false;
        case SUBSTRING:
          return (substring != null) && (substring.length() == 0);
        case SUBSTRINGS:
          return ArrayUtils.containsIf(substrings, (s) -> (s != null && s.length() == 0));
        default:
          throw new IllegalStateException("No searching strategy was specified.");
      }
    }
    if ((startIndex >= strLen) || (endIndex <= 0) || (startIndex >= endIndex)) {
      return false;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (target) {
      case CHAR:
        return startsWithChar(str, start, end, charFilter);
      case CODE_POINT:
        return startsWithCodePoint(str, start, end, codePointFilter);
      case SUBSTRING:
        if (substring == null) {
          return false;
        }
        return startsWithSubstring(str, start, end, substring, ignoreCase);
      case SUBSTRINGS:
        return startsWithAnySubstring(str, start, end, substrings, ignoreCase);
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
  }

  /**
   * Tests whether the specified string ends with the specified target (which
   * may be a character, a Unicode code point, or a substring).
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns false.
   * @return
   *     whether the specified string ends with the specified target.
   */
  public boolean isAtEndOf(@Nullable final CharSequence str) {
    if (str == null) {
      return ((target == Target.SUBSTRING) && (substring == null));
    }
    final int strLen = str.length();
    if (strLen == 0) {
      switch (target) {
        case CHAR:
        case CODE_POINT:
          return false;
        case SUBSTRING:
          return (substring != null) && (substring.length() == 0);
        case SUBSTRINGS:
          return ArrayUtils.containsIf(substrings, (s) -> (s != null && s.length() == 0));
        default:
          throw new IllegalStateException("No searching strategy was specified.");
      }
    }
    if ((startIndex >= strLen) || (endIndex <= 0) || (startIndex >= endIndex)) {
      return false;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (target) {
      case CHAR:
        return endsWithChar(str, start, end, charFilter);
      case CODE_POINT:
        return endsWithCodePoint(str, start, end, codePointFilter);
      case SUBSTRING:
        if (substring == null) {
          return false;
        }
        return endsWithSubstring(str, start, end, substring, ignoreCase);
      case SUBSTRINGS:
        return endsWithAnySubstring(str, start, end, substrings, ignoreCase);
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
  }

  /**
   * Tests whether the specified string starts with, or ends with the specified
   * target (which may be a character, a Unicode code point, or a substring).
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns false.
   * @return
   *     whether the specified string starts with, or ends with the specified
   *     target.
   */
  public boolean isAtStartOrEndOf(@Nullable final CharSequence str) {
    if (str == null) {
      return ((target == Target.SUBSTRING) && (substring == null));
    }
    final int strLen = str.length();
    if (strLen == 0) {
      switch (target) {
        case CHAR:
        case CODE_POINT:
          return false;
        case SUBSTRING:
          return (substring != null) && (substring.length() == 0);
        case SUBSTRINGS:
          return ArrayUtils.containsIf(substrings, (s) -> (s != null && s.length() == 0));
        default:
          throw new IllegalStateException("No searching strategy was specified.");
      }
    }
    if ((startIndex >= strLen) || (endIndex <= 0) || (startIndex >= endIndex)) {
      return false;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (target) {
      case CHAR:
        return startsWithChar(str, start, end, charFilter)
            || endsWithChar(str, start, end, charFilter);
      case CODE_POINT:
        return startsWithCodePoint(str, start, end, codePointFilter)
            || endsWithCodePoint(str, start, end, codePointFilter);
      case SUBSTRING:
        if (substring == null) {
          return false;
        }
        return startsWithSubstring(str, start, end, substring, ignoreCase)
            || endsWithSubstring(str, start, end, substring, ignoreCase);
      case SUBSTRINGS:
        return startsWithAnySubstring(str, start, end, substrings, ignoreCase)
            || endsWithAnySubstring(str, start, end, substrings, ignoreCase);
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
  }

  /**
   * Tests whether the specified string starts with, <b>AND</b> ends with the
   * specified target (which may be a character, a Unicode code point, or a
   * substring).
   *
   * @param str
   *     the specified source string where to search for the target. If it is
   *     null or empty, returns false.
   * @return
   *     whether the specified target <b>AND</b> starts with, or ends with the
   *     specified target.
   */
  public boolean isAtStartAndEndOf(@Nullable final CharSequence str) {
    if (str == null) {
      return ((target == Target.SUBSTRING) && (substring == null));
    }
    final int strLen = str.length();
    if (strLen == 0) {
      switch (target) {
        case CHAR:
        case CODE_POINT:
          return false;
        case SUBSTRING:
          return (substring != null) && (substring.length() == 0);
        case SUBSTRINGS:
          return ArrayUtils.containsIf(substrings, (s) -> (s != null && s.length() == 0));
        default:
          throw new IllegalStateException("No searching strategy was specified.");
      }
    }
    if ((startIndex >= strLen) || (endIndex <= 0) || (startIndex >= endIndex)) {
      return false;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (target) {
      case CHAR:
        return startsWithChar(str, start, end, charFilter)
            && endsWithChar(str, start, end, charFilter);
      case CODE_POINT:
        return startsWithCodePoint(str, start, end, codePointFilter)
            && endsWithCodePoint(str, start, end, codePointFilter);
      case SUBSTRING:
        if (substring == null) {
          return false;
        }
        return startsWithSubstring(str, start, end, substring, ignoreCase)
            && endsWithSubstring(str, start, end, substring, ignoreCase);
      case SUBSTRINGS:
        return startsWithAnySubstring(str, start, end, substrings, ignoreCase)
            && endsWithAnySubstring(str, start, end, substrings, ignoreCase);
      default:
        throw new IllegalStateException("No searching strategy was specified.");
    }
  }
}
