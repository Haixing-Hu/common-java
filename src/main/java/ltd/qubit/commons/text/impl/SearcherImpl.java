////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * The class provides functions for implementing the {@link Searcher} class.
 * It is intended to be used internally.
 *
 * @author Haixing Hu
 */
public class SearcherImpl {

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

  public static boolean matchIgnoreCase(final CharSequence str,
      final int startIndex, final CharSequence substring) {
    final int endIndex = startIndex + substring.length();
    for (int i = startIndex; i < endIndex; ++i) {
      final char c1 = str.charAt(i);
      final char c2 = substring.charAt(i - startIndex);
      if (Character.toUpperCase(c1) != Character.toUpperCase(c2)) {
        return false;
      }
    }
    return true;
  }

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

  public static int lastIndexOfAny(final CharSequence str, final int start,
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

  public static boolean startsWithSubstring(final CharSequence str,
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

  public static boolean startsWithAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    for (final CharSequence substring : substrings) {
      if ((substring != null)
          && startsWithSubstring(str, start, end, substring, ignoreCase)) {
        return true;
      }
    }
    return false;
  }

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

  public static boolean endsWithSubstring(final CharSequence str,
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

  public static boolean endsWithAnySubstring(final CharSequence str,
      final int start, final int end, final CharSequence[] substrings,
      final boolean ignoreCase) {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substrings != null);
    for (final CharSequence substring : substrings) {
      if ((substring != null)
          && endsWithSubstring(str, start, end, substring, ignoreCase)) {
        return true;
      }
    }
    return false;
  }

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
