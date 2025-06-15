////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.impl;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.text.Searcher;
import ltd.qubit.commons.text.Utf16;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;

/**
 * 该类提供用于实现 {@link Searcher} 类的函数。
 * 它仅供内部使用。
 *
 * @author 胡海星
 */
@SuppressWarnings("overloads")
public class SearcherImpl {

  /**
   * 在字符序列中查找第一个匹配指定字符过滤器的字符。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @return
   *     第一个匹配字符的索引；如果未找到则返回 {@code end}。
   */
  public static int firstIndexOf(final CharSequence str, final int start,
      final int end, final CharFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    for (int i = start; i < end; ++i) {
      final char ch = str.charAt(i);
      if (filter.accept(ch)) {
        return i;
      }
    }
    return end;
  }

  /**
   * 在字符序列中查找第一个匹配指定代码点过滤器的代码点。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @return
   *     第一个匹配代码点的索引；如果未找到则返回 {@code end}。
   */
  public static int firstIndexOf(final CharSequence str, final int start,
      final int end, final CodePointFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    int k;
    for (int i = start; i < end; i += k) {
      final int cp = Character.codePointAt(str, i);
      k = Character.charCount(cp);
      if (filter.accept(cp)) {
        return i; // FIXME: corner case, i + k > start
      }
    }
    return end;
  }

  /**
   * 在字符序列中查找指定子字符串的第一个匹配位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substring
   *     要查找的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     第一个匹配的索引；如果未找到则返回 {@code end}。
   */
  public static int firstIndexOf(final CharSequence str, final int start,
      final int end, final CharSequence substring, final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null);
    // FIXME: use the KMP algorithm for large strings
    final int substringLen = substring.length();
    if (substringLen == 0) {
      return start;
    }
    final int stopIndex = end - substringLen;
    if (start > stopIndex) {
      return end; // not found
    }
    if (ignoreCase) {
      for (int i = start; i <= stopIndex; ++i) {
        if (matchIgnoreCase(str, i, substring)) {
          return i;
        }
      }
    } else {
      for (int i = start; i <= stopIndex; ++i) {
        if (matchCaseSensitive(str, i, substring)) {
          return i;
        }
      }
    }
    return end; // not found
  }

  /**
   * 在字符序列中查找任意指定子字符串的第一个匹配位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substrings
   *     要查找的子字符串数组。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     第一个匹配的索引；如果未找到则返回 {@code end}。
   */
  public static int firstIndexOfAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    // FIXME: use the KMP algorithm for large strings
    int result = end;
    for (final CharSequence substring : substrings) {
      if (substring == null) {
        continue;
      }
      final int i = firstIndexOf(str, start, end, substring, ignoreCase);
      result = Math.min(result, i);
      if (result == start) {
        return start;
      }
    }
    return result;
  }

  /**
   * 检查字符序列在指定位置是否匹配给定的子字符串（区分大小写）。
   *
   * @param str
   *     要检查的字符序列。
   * @param startIndex
   *     开始匹配的位置。
   * @param substring
   *     要匹配的子字符串。
   * @return
   *     如果匹配则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean matchCaseSensitive(final CharSequence str,
      final int startIndex, final CharSequence substring) {
    final int endIndex = startIndex + substring.length();
    for (int i = startIndex; i < endIndex; ++i) {
      final char c1 = str.charAt(i);
      final char c2 = substring.charAt(i - startIndex);
      if (c1 != c2) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查字符序列在指定位置是否匹配给定的子字符串（忽略大小写）。
   *
   * @param str
   *     要检查的字符序列。
   * @param startIndex
   *     开始匹配的位置。
   * @param substring
   *     要匹配的子字符串。
   * @return
   *     如果匹配则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean matchIgnoreCase(final CharSequence str,
      final int startIndex, final CharSequence substring) {
    final int endIndex = startIndex + substring.length();
    final String s1 = str.subSequence(startIndex, endIndex).toString();
    final String s2 = substring.toString();
    return s1.regionMatches(true, 0, s2, 0, s2.length());
  }

  /**
   * 在字符序列中查找最后一个匹配指定字符过滤器的字符。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @return
   *     最后一个匹配字符的索引；如果未找到则返回 {@code start - 1}。
   */
  public static int lastIndexOf(final CharSequence str, final int start,
      final int end, final CharFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    for (int i = end - 1; i >= start; --i) {
      final char ch = str.charAt(i);
      if (filter.accept(ch)) {
        return i;
      }
    }
    return start - 1;
  }

  /**
   * 在字符序列中查找最后一个匹配指定代码点过滤器的代码点。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @return
   *     最后一个匹配代码点的索引；如果未找到则返回 {@code start - 1}。
   */
  public static int lastIndexOf(final CharSequence str, final int start,
      final int end, final CodePointFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    int k;
    for (int i = end; i > start; i -= k) {
      final int cp = Character.codePointBefore(str, i);
      k = Character.charCount(cp);
      if (filter.accept(cp)) {
        return i - k;   // FIXME: corner case, i - k < start
      }
    }
    return start - 1;
  }

  /**
   * 在字符序列中查找指定子字符串的最后一个匹配位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substring
   *     要查找的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     最后一个匹配的索引；如果未找到则返回 {@code start - 1}。
   */
  public static int lastIndexOf(final CharSequence str, final int start,
      final int end, final CharSequence substring, final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null);
    // FIXME: use the KMP algorithm for large strings
    final int substringLen = substring.length();
    if (substringLen == 0) {
      return end - 1;
    }
    final int stopIndex = end - substringLen;
    if (start > stopIndex) {
      return start - 1; // not found
    }
    if (ignoreCase) {
      for (int i = stopIndex; i >= start; --i) {
        if (matchIgnoreCase(str, i, substring)) {
          return i;
        }
      }
    } else {
      for (int i = stopIndex; i >= start; --i) {
        if (matchCaseSensitive(str, i, substring)) {
          return i;
        }
      }
    }
    return start - 1; // not found
  }

  /**
   * 在字符序列中查找任意指定子字符串的最后一个匹配位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substrings
   *     要查找的子字符串数组。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     最后一个匹配的索引；如果未找到则返回 {@code start - 1}。
   */
  public static int lastIndexOfAnySubstring(final CharSequence str, final int start,
      final int end, final CharSequence[] substrings, final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    // FIXME: use the KMP algorithm for large strings
    int result = start - 1;
    for (final CharSequence substring : substrings) {
      if (substring == null) {
        continue;
      }
      final int i = lastIndexOf(str, start, end, substring, ignoreCase);
      result = Math.max(result, i);
    }
    return result;
  }

  /**
   * 在字符序列中查找最后一个匹配指定字符过滤器的字符之后的位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @return
   *     最后一个匹配字符之后的位置；如果未找到则返回 {@code start}。
   */
  public static int afterLastIndexOf(final CharSequence str, final int start,
      final int end, final CharFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    for (int i = end - 1; i >= start; --i) {
      final char ch = str.charAt(i);
      if (filter.accept(ch)) {
        return i + 1;
      }
    }
    return start;
  }

  /**
   * 在字符序列中查找最后一个匹配指定代码点过滤器的代码点之后的位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @return
   *     最后一个匹配代码点之后的位置；如果未找到则返回 {@code start}。
   */
  public static int afterLastIndexOf(final CharSequence str, final int start,
      final int end, final CodePointFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    int k;
    for (int i = end; i > start; i -= k) {
      final int cp = Character.codePointBefore(str, i);
      k = Character.charCount(cp);
      if (filter.accept(cp)) {
        return i;   // FIXME: corner case, i - count < start
      }
    }
    return start;
  }

  /**
   * 统计字符序列中匹配指定字符过滤器的字符数量。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @return
   *     匹配的字符数量。
   */
  public static int countMatchesOfChar(final CharSequence str, final int start,
      final int end, final CharFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    int count = 0;
    for (int i = start; i < end; ++i) {
      final char ch = str.charAt(i);
      if (filter.accept(ch)) {
        ++count;
      }
    }
    return count;
  }

  /**
   * 统计字符序列中匹配指定代码点过滤器的代码点数量。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @return
   *     匹配的代码点数量。
   */
  public static int countMatchesOfCodePoint(final CharSequence str,
      final int start, final int end, final CodePointFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    int count = 0;
    int k;
    for (int i = start; i < end; i += k) {
      final int cp = Character.codePointAt(str, i);
      k = Character.charCount(cp);
      if (filter.accept(cp)) {
        ++count; // FIXME: corner case, i + k > start
      }
    }
    return count;
  }

  /**
   * 统计字符序列中指定子字符串的出现次数。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substring
   *     要查找的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     子字符串的出现次数。
   */
  public static int countMatchesOfSubstring(final CharSequence str,
      final int start, final int end, final CharSequence substring,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null);
    // FIXME: use the KMP algorithm for large strings
    final int substringLen = substring.length();
    if (substringLen == 0) {
      return end - start; // be care!
    }
    final int stopIndex = end - substringLen;
    if (start > stopIndex) {
      return 0; // not found
    }
    int count = 0;
    if (ignoreCase) {
      for (int i = start; i <= stopIndex; ) {
        if (matchIgnoreCase(str, i, substring)) {
          ++count;
          i += substringLen;
        } else {
          ++i;
        }
      }
    } else {
      for (int i = start; i <= stopIndex; ) {
        if (matchCaseSensitive(str, i, substring)) {
          ++count;
          i += substringLen;
        } else {
          ++i;
        }
      }
    }
    return count;
  }

  /**
   * 统计字符序列中任意指定子字符串的总出现次数。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substrings
   *     要查找的子字符串数组。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     所有子字符串的总出现次数。
   */
  public static int countMatchesOfAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    int count = 0;
    for (final CharSequence substring : substrings) {
      count += countMatchesOfSubstring(str, start, end, substring, ignoreCase);
    }
    return count;
  }

  /**
   * 检查字符序列是否以匹配指定字符过滤器的字符开头。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @return
   *     如果以匹配的字符开头则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean startsWithChar(final CharSequence str, final int start,
      final int end, final CharFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    if (start >= end) {
      return false;
    }
    final char ch = str.charAt(start);
    return filter.accept(ch);
  }

  /**
   * 检查字符序列是否以匹配指定代码点过滤器的代码点开头。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @return
   *     如果以匹配的代码点开头则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean startsWithCodePoint(final CharSequence str,
      final int start, final int end, final CodePointFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    if (start >= end) {
      return false;
    }
    final char ch0 = str.charAt(start);
    final char ch1;
    final int next;
    if (Utf16.isLeading(ch0)
        && ((next = start + 1) < end)
        && (Utf16.isTrailing(ch1 = str.charAt(next)))) {
      final int cp = Utf16.compose(ch0, ch1);
      return filter.accept(cp);
    } else {
      return filter.accept((int) ch0);
    }
  }

  /**
   * 检查字符序列是否以指定的子字符串开头。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param substring
   *     要匹配的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     如果以指定子字符串开头则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean startsWithSubstring(final CharSequence str,
      final int start, final int end, final CharSequence substring,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null);
    // FIXME: use the KMP algorithm for large strings
    if (start >= end) {
      return false;
    }
    final int substringLen = substring.length();
    if (substringLen == 0) {
      return true;
    }
    final int stopIndex = end - substringLen;
    if (start > stopIndex) {
      return false;
    }
    if (ignoreCase) {
      return matchIgnoreCase(str, start, substring);
    } else {
      return matchCaseSensitive(str, start, substring);
    }
  }

  /**
   * 检查字符序列是否以任意指定的子字符串开头。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param substrings
   *     要匹配的子字符串数组。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     如果以任意指定子字符串开头则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean startsWithAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    if (start >= end) {
      return false;
    }
    for (final CharSequence substring : substrings) {
      if ((substring != null)
          && startsWithSubstring(str, start, end, substring, ignoreCase)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查字符序列是否以匹配指定字符过滤器的字符结尾。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @return
   *     如果以匹配的字符结尾则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean endsWithChar(final CharSequence str, final int start,
      final int end, final CharFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    if (start >= end) {
      return false;
    }
    final char ch = str.charAt(end - 1);
    return filter.accept(ch);
  }

  /**
   * 检查字符序列是否以匹配指定代码点过滤器的代码点结尾。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @return
   *     如果以匹配的代码点结尾则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean endsWithCodePoint(final CharSequence str,
      final int start, final int end, final CodePointFilter filter) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    if (start >= end) {
      return false;
    }
    final char ch1 = str.charAt(end - 1);
    final char ch0;
    final int prev;
    if (Utf16.isTrailing(ch1)
        && ((prev = end - 2) >= start)
        && (Utf16.isLeading(ch0 = str.charAt(prev)))) {
      final int cp = Utf16.compose(ch0, ch1);
      return filter.accept(cp);
    } else {
      return filter.accept((int) ch1);
    }
  }

  /**
   * 检查字符序列是否以指定的子字符串结尾。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param substring
   *     要匹配的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     如果以指定子字符串结尾则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean endsWithSubstring(final CharSequence str,
      final int start, final int end, final CharSequence substring,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null);
    // FIXME: use the KMP algorithm for large strings
    if (start >= end) {
      return false;
    }
    final int substringLen = substring.length();
    if (substringLen == 0) {
      return true;
    }
    final int stopIndex = end - substringLen;
    if (start > stopIndex) {
      return false;
    }
    if (ignoreCase) {
      return matchIgnoreCase(str, stopIndex, substring);
    } else {
      return matchCaseSensitive(str, stopIndex, substring);
    }
  }

  /**
   * 检查字符序列是否以任意指定的子字符串结尾。
   *
   * @param str
   *     要检查的字符序列。
   * @param start
   *     检查的起始位置（包含）。
   * @param end
   *     检查的结束位置（不包含）。
   * @param substrings
   *     要匹配的子字符串数组。
   * @param ignoreCase
   *     是否忽略大小写。
   * @return
   *     如果以任意指定子字符串结尾则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean endsWithAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    if (start >= end) {
      return false;
    }
    for (final CharSequence substring : substrings) {
      if ((substring != null)
          && endsWithSubstring(str, start, end, substring, ignoreCase)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取字符序列中所有匹配指定字符过滤器的字符位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @param result
   *     用于存储匹配位置的整数列表。
   */
  public static void getOccurrencesOfChar(final CharSequence str,
      final int start, final int end, final CharFilter filter,
      final IntList result) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    for (int i = start; i < end; ++i) {
      final char ch = str.charAt(i);
      if (filter.accept(ch)) {
        result.add(i);
      }
    }
  }

  /**
   * 获取字符序列中所有匹配指定代码点过滤器的代码点位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param filter
   *     代码点过滤器。
   * @param result
   *     用于存储匹配位置的整数列表。
   */
  public static void getOccurrencesOfCodePoint(final CharSequence str,
      final int start, final int end, final CodePointFilter filter,
      final IntList result) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null);
    int k;
    for (int i = start; i < end; i += k) {
      final int cp = Character.codePointAt(str, i);
      k = Character.charCount(cp);
      if (filter.accept(cp)) {
        result.add(i);
      }
    }
  }

  /**
   * 获取字符序列中指定子字符串的所有出现位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substring
   *     要查找的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @param result
   *     用于存储匹配位置的整数列表。
   */
  public static void getOccurrencesOfSubstring(final CharSequence str,
      final int start, final int end, final CharSequence substring,
      final boolean ignoreCase, final IntList result) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null);
    // FIXME: use the KMP algorithm for large strings
    final int substringLen;
    if ((substringLen = substring.length()) == 0) {
      for (int i = start; i < end; ++i) {
        result.add(i);
      }
      return;
    }
    final int stopIndex = end - substringLen;
    if (start > stopIndex) {
      return; // not found
    }
    if (ignoreCase) {
      for (int i = start; i <= stopIndex; ) {
        if (matchIgnoreCase(str, i, substring)) {
          result.add(i);
          i += substringLen;
        } else {
          ++i;
        }
      }
    } else {
      for (int i = start; i <= stopIndex; ) {
        if (matchCaseSensitive(str, i, substring)) {
          result.add(i);
          i += substringLen;
        } else {
          ++i;
        }
      }
    }
  }

  /**
   * 获取字符序列中任意指定子字符串的所有出现位置。
   *
   * @param str
   *     要搜索的字符序列。
   * @param start
   *     搜索的起始位置（包含）。
   * @param end
   *     搜索的结束位置（不包含）。
   * @param substrings
   *     要查找的子字符串数组。
   * @param ignoreCase
   *     是否忽略大小写。
   * @param result
   *     用于存储匹配位置的整数列表。
   */
  public static void getOccurrencesOfAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase, final IntList result) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    if (start >= end) {
      return;
    }
    for (final CharSequence substring : substrings) {
      if (substring != null) {
        getOccurrencesOfSubstring(str, start, end, substring, ignoreCase, result);
      }
    }
    result.sort();
    result.unique();
  }
}