////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 用于在字符串中搜索的类。
 *
 * <p>此类提供了灵活的字符串搜索功能，支持搜索字符、Unicode 代码点、子字符串等，
 * 并提供多种搜索选项如忽略大小写、指定搜索范围等。</p>
 *
 * <p>使用示例：</p>
 * <pre><code>
 * // 搜索单个字符
 * int index = new Searcher().forChar('a').findFirstIndexIn("hello world");
 * boolean contains = new Searcher().forChar('o').isContainedIn("hello");
 * int count = new Searcher().forChar('l').countMatchesIn("hello");
 *
 * // 搜索字符数组中的任意字符
 * int index = new Searcher().forCharsIn('a', 'e', 'i', 'o', 'u')
 *                          .findFirstIndexIn("hello world");
 *
 * // 搜索子字符串
 * int index = new Searcher().forSubstring("world").findFirstIndexIn("hello world");
 * boolean startsWith = new Searcher().forSubstring("hello").isAtStartOf("hello world");
 * boolean endsWith = new Searcher().forSubstring("world").isAtEndOf("hello world");
 *
 * // 搜索多个子字符串中的任意一个
 * int index = new Searcher().forSubstringsIn("cat", "dog", "bird")
 *                          .findFirstIndexIn("I have a cat");
 *
 * // 忽略大小写搜索
 * int index = new Searcher().forSubstring("WORLD")
 *                          .ignoreCase(true)
 *                          .findFirstIndexIn("hello world");
 *
 * // 指定搜索范围
 * int index = new Searcher().forChar('l')
 *                          .startFrom(3)
 *                          .endBefore(8)
 *                          .findFirstIndexIn("hello world");
 *
 * // 获取所有匹配位置
 * int[] occurrences = new Searcher().forChar('l').getOccurrencesIn("hello world");
 *
 * // 使用字符过滤器搜索
 * int index = new Searcher().forCharsSatisfy(Character::isDigit)
 *                          .findFirstIndexIn("abc123def");
 *
 * // 搜索 Unicode 代码点
 * int index = new Searcher().forCodePoint(0x1F600) // 😀 表情符号
 *                          .findFirstIndexIn("Hello 😀 World");
 * </code></pre>
 *
 * @author 胡海星
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
   * 搜索指定的字符。
   *
   * @param ch
   *     要查找的字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher forChar(final char ch) {
    this.clearStrategies();
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    this.target = Target.CHAR;
    return this;
  }

  /**
   * 搜索任何不等于指定字符的字符。
   *
   * @param ch
   *     指定字符。源字符串中除此字符以外的所有字符都将被搜索。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher forCharsNotEqual(final char ch) {
    this.clearStrategies();
    this.charFilter = new RejectSpecifiedCharFilter(ch);
    this.target = Target.CHAR;
    return this;
  }

  /**
   * 搜索数组中的任何字符。
   *
   * @param chars
   *     要搜索的字符数组。{@code null} 值或空数组表示不搜索任何字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索序列中的任何字符。
   *
   * @param chars
   *     要搜索的字符序列。{@code null} 值或空序列表示不搜索任何字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索不在数组中的任何字符。
   *
   * @param chars
   *     字符数组。不在此数组中的所有字符都将被搜索。{@code null} 值或空数组表示搜索源字符串中的所有字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索不在序列中的任何字符。
   *
   * @param chars
   *     字符序列。不在此序列中的所有字符都将被搜索。{@code null} 值或空序列表示搜索源字符串中的所有字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索满足指定过滤器的任何字符。
   *
   * @param filter
   *     接受要搜索字符的过滤器。{@code null} 值表示不搜索任何字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索不满足指定过滤器的任何字符。
   *
   * @param filter
   *     拒绝要搜索字符的过滤器。{@code null} 值表示搜索源字符串中的所有字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索指定的 Unicode 代码点。
   *
   * @param codePoint
   *     要搜索的 Unicode 字符的代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher forCodePoint(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * 搜索指定的 Unicode 代码点。
   *
   * @param codePoint
   *     包含要搜索的 Unicode 字符的字符序列。{@code null} 值或空值表示不搜索任何 Unicode 代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索任何不等于指定代码点的 Unicode 代码点。
   *
   * @param codePoint
   *     指定 Unicode 字符的代码点。源字符串中除此字符以外的所有 Unicode 字符都将被搜索。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher forCodePointsNotEqual(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    this.target = Target.CODE_POINT;
    return this;
  }

  /**
   * 搜索任何不等于指定代码点的 Unicode 代码点。
   *
   * @param codePoint
   *     包含指定 Unicode 字符的字符序列。除此序列开头的代码点外，所有 Unicode 代码点都将被搜索。
   *     {@code null} 值表示搜索源字符串中的所有 Unicode 字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索数组中出现的任何 Unicode 代码点。
   *
   * @param codePoints
   *     要搜索的 Unicode 字符的代码点数组。{@code null} 值或空数组表示不搜索任何 Unicode 字符。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索序列中出现的任何 Unicode 代码点。
   *
   * @param codePoints
   *     要搜索的 Unicode 字符的代码点序列。{@code null} 值或空序列表示不搜索任何 Unicode 代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索不在指定数组中的任何 Unicode 代码点。
   *
   * @param codePoints
   *     Unicode 代码点数组。代码点不在此数组中的所有 Unicode 字符都将被搜索。
   *     {@code null} 值或空数组表示搜索源字符串中的所有 Unicode 代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索不在指定序列中的任何 Unicode 代码点。
   *
   * @param codePoints
   *     Unicode 字符的代码点序列。不在此序列中的所有代码点都将被搜索。
   *     {@code null} 值或空值表示搜索所有 Unicode 代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索任何被指定过滤器接受的 Unicode 代码点。
   *
   * @param filter
   *     接受要搜索的 Unicode 代码点的过滤器。{@code null} 值表示不搜索任何 Unicode 代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索任何被指定过滤器拒绝的 Unicode 代码点。
   *
   * @param filter
   *     拒绝要搜索的 Unicode 代码点的过滤器。{@code null} 值表示搜索所有 Unicode 代码点。
   * @return
   *     此 {@link Searcher} 对象的引用。
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
   * 搜索指定的子字符串。
   *
   * @param substring
   *     要搜索的子字符串。{@code null} 值或空值表示不搜索任何子字符串。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher forSubstring(@Nullable final CharSequence substring) {
    this.clearStrategies();
    this.substring = substring;
    this.target = Target.SUBSTRING;
    return this;
  }

  /**
   * 搜索一组潜在子字符串中的任何子字符串。
   *
   * @param substrings
   *     要搜索的子字符串数组。{@code null} 值或空值表示不搜索任何子字符串。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher forSubstringsIn(@Nullable final CharSequence... substrings) {
    this.clearStrategies();
    this.substrings = ArrayUtils.nullToEmpty(substrings);
    this.target = Target.SUBSTRINGS;
    return this;
  }

  /**
   * 设置源字符串开始搜索的起始索引。
   *
   * @param startIndex
   *     源字符串开始搜索的起始索引。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher startFrom(final int startIndex) {
    this.startIndex = startIndex;
    return this;
  }

  /**
   * 设置源字符串停止搜索的结束索引。
   *
   * @param endIndex
   *     源字符串停止搜索的结束索引。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher endBefore(final int endIndex) {
    this.endIndex = endIndex;
    return this;
  }

  /**
   * 设置在搜索子字符串时是否忽略大小写。
   *
   * @param ignoreCase
   *     在搜索子字符串时是否忽略大小写。
   * @return
   *     此 {@link Searcher} 对象的引用。
   */
  public Searcher ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * 在指定的源字符串中查找指定目标（可能是字符、Unicode 代码点或子字符串）的第一个索引。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 -1。
   * @return
   *     指定目标在指定源字符串中的第一个索引。如果未找到此类目标，返回 -1。
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

  /**
   * 在指定的源字符串中查找指定目标（可能是字符、Unicode 代码点或子字符串）的最后一个索引。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 -1。
   * @return
   *     指定目标在指定源字符串中的最后一个索引。如果未找到此类目标，返回 -1。
   */
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
   * 测试指定目标（可能是字符、Unicode 代码点或子字符串）是否包含在指定的源字符串中。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 false。
   * @return
   *     指定目标（可能是字符、Unicode 代码点或子字符串）是否包含在指定的源字符串中。
   */
  public boolean isContainedIn(@Nullable final CharSequence str) {
    return findFirstIndexIn(str) >= 0;
  }

  /**
   * 测试指定目标（可能是字符、Unicode 代码点或子字符串）是否**不**包含在指定的源字符串中。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 false。
   * @return
   *     指定目标（可能是字符、Unicode 代码点或子字符串）是否**不**包含在指定的源字符串中。
   */
  public boolean isNotContainedIn(@Nullable final CharSequence str) {
    return findFirstIndexIn(str) < 0;
  }

  /**
   * 计算指定目标（可能是字符、Unicode 代码点或子字符串）在指定源字符串中的匹配数量。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 0。
   * @return
   *     指定目标在指定源字符串中的匹配数量。
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
   * 获取指定目标（可能是字符、Unicode 代码点或子字符串）在指定源字符串中所有出现位置的索引。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回空数组。
   * @return
   *     指定目标在指定源字符串中所有出现位置的索引。
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
   * 获取指定目标（可能是字符、Unicode 代码点或子字符串）在指定源字符串中所有出现位置的索引。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 {@code null} 或空，不会向返回列表追加任何内容。
   * @param output
   *     用于追加结果的可选 {@link IntList}。如果为 {@code null}，将创建新的 {@link IntList} 来存储结果并返回。
   * @return
   *     指定目标在指定源字符串中所有出现位置的索引列表。如果参数 {@code output} 不为 {@code null}，
   *     函数使用 {@code output} 来追加索引列表并返回 {@code output}；否则，创建新的 {@link IntList}
   *     来存储索引列表并返回。
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
   * 测试指定字符串是否以指定目标（可能是字符、Unicode 代码点或子字符串）开头。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 false。
   * @return
   *     指定字符串是否以指定目标开头。
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
   * 测试指定字符串是否以指定目标（可能是字符、Unicode 代码点或子字符串）结尾。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 false。
   * @return
   *     指定字符串是否以指定目标结尾。
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
   * 测试指定字符串是否以指定目标（可能是字符、Unicode 代码点或子字符串）开头或结尾。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 false。
   * @return
   *     指定字符串是否以指定目标开头或结尾。
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
   * 测试指定字符串是否<b>既</b>以指定目标（可能是字符、Unicode 代码点或子字符串）开头<b>又</b>以该目标结尾。
   *
   * @param str
   *     要在其中搜索目标的指定源字符串。如果为 null 或空，返回 false。
   * @return
   *     指定字符串是否<b>既</b>以指定目标开头<b>又</b>以该目标结尾。
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