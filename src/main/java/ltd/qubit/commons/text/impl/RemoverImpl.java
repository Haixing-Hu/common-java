////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.impl;

import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;

import java.io.IOException;

import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.matchCaseSensitive;
import static ltd.qubit.commons.text.impl.SearcherImpl.matchIgnoreCase;

/**
 * The class provides functions for implementing the {@link Remover} class.
 * It is intended to be used internally.
 *
 * @author Haixing Hu
 */
public class RemoverImpl {

  public static int removeChar(final CharSequence str, final int start, final int end,
      final CharFilter filter, final int limit, final Appendable output)
      throws IOException {
    assert (str != null
        && start >= 0
        && start < end
        && end <= str.length()
        && filter != null);
    output.append(str, 0, start);
    int index = start;
    int count = 0;
    int pos;
    while ((pos = firstIndexOf(str, index, end, filter)) < end) {
      output.append(str, index, pos);
      index = pos + 1;
      ++count;
      if (count == limit) {
        break;
      }
    }
    output.append(str, index, str.length());
    return count;
  }

  public static int removeCodePoint(final CharSequence str, final int start,
      final int end, final CodePointFilter filter, final int limit,
      final Appendable output) throws IOException {
    assert (str != null
        && start >= 0
        && start < end
        && end <= str.length()
        && filter != null);
    output.append(str, 0, start);
    int index = start;
    int count = 0;
    int pos;
    while ((pos = firstIndexOf(str, index, end, filter)) < end) {
      output.append(str, index, pos);
      final int cp = Character.codePointAt(str, pos);
      index = pos + Character.charCount(cp);
      ++count;
      if (count == limit) {
        break;
      }
    }
    output.append(str, index, str.length());
    return count;
  }

  public static int removeSubstring(final CharSequence str, final int start,
      final int end, final CharSequence substring, final boolean ignoreCase,
      final int limit, final Appendable output) throws IOException {
    assert (str != null
        && start >= 0
        && start < end
        && end <= str.length()
        && substring != null
        && substring.length() != 0);
    output.append(str, 0, start);
    final int removeLen = substring.length();
    int index = 0;
    int count = 0;
    int pos;
    while ((pos = firstIndexOf(str, index, end, substring, ignoreCase)) < end) {
      output.append(str, index, pos);
      index = pos + removeLen;
      ++count;
      if (count == limit) {
        break;
      }
    }
    output.append(str, index, str.length());
    return count;
  }

  public static int removePrefix(final CharSequence str, final int start, final int end,
      final CharSequence prefix, final boolean ignoreCase, final Appendable output)
      throws IOException {
    assert (str != null
        && start >= 0
        && start < end
        && end <= str.length()
        && prefix != null);
    final int prefixLen = prefix.length();
    if ((prefixLen == 0)                  // prefix is empty
        || (prefixLen > end - start)) {   // prefix is longer than str[start, end)
      // no matching prefix to remove
      output.append(str);
      return 0;
    }
    final boolean matched;
    if (ignoreCase) {
      matched = matchIgnoreCase(str, start, prefix);
    } else {
      matched = matchCaseSensitive(str, start, prefix);
    }
    if (matched) {
      output.append(str, 0, start);
      output.append(str, start + prefixLen, str.length());
      return 1;
    } else {
      output.append(str);
      return 0;
    }
  }

  public static int removeSuffix(final CharSequence str, final int start, final int end,
      final CharSequence suffix, final boolean ignoreCase, final Appendable output)
      throws IOException {
    assert (str != null
        && start >= 0
        && start < end
        && end <= str.length()
        && suffix != null);
    final int suffixLen = suffix.length();
    final int suffixStart;
    if ((suffixLen == 0)    // prefix is empty
        || ((suffixStart = end - suffixLen) < start)) {  // prefix is longer than str[start, end)
      // no matching suffix to remove
      output.append(str);
      return 0;
    }
    final boolean matched;
    if (ignoreCase) {
      matched = matchIgnoreCase(str, suffixStart, suffix);
    } else {
      matched = matchCaseSensitive(str, suffixStart, suffix);
    }
    if (matched) {
      output.append(str, 0, suffixStart);
      output.append(str, end, str.length());
      return 1;
    } else {
      output.append(str);
      return 0;
    }
  }

  public static int removePrefixAndSuffix(final CharSequence str, final int start,
      final int end, final CharSequence prefix, final CharSequence suffix,
      final boolean ignoreCase, final Appendable output)
      throws IOException {
    assert (str != null
        && start >= 0
        && start < end
        && end <= str.length()
        && prefix != null
        && suffix != null);
    // try to search prefix in str[start, end)
    final int prefixLen = prefix.length();
    final int prefixEnd = start + prefixLen;
    final boolean prefixMatched;
    if (prefixLen == 0) {                 // prefix is empty
      prefixMatched = false;
    } else if (prefixLen > end - start) { // prefix is too long
      prefixMatched = false;
    } else if (ignoreCase) {
      prefixMatched = matchIgnoreCase(str, start, prefix);
    } else {
      prefixMatched = matchCaseSensitive(str, start, prefix);
    }
    // try to search suffix in str[prefixStopIndex, end)
    final int suffixLen = suffix.length();
    final int suffixStart = end - suffixLen;
    final boolean suffixMatched;
    if (suffixLen == 0) {   // suffix is empty
      suffixMatched = false;
    } else if (suffixStart < (prefixMatched ? prefixEnd : start)) { // suffix is too long
      suffixMatched = false;
    } else if (ignoreCase) {
      suffixMatched = matchIgnoreCase(str, suffixStart, suffix);
    } else {
      suffixMatched = matchCaseSensitive(str, suffixStart, suffix);
    }
    if (prefixMatched && suffixMatched) {
      output.append(str, 0, start);
      output.append(str, prefixEnd, suffixStart);
      output.append(str, end, str.length());
      return 2;
    } else if (prefixMatched) {
      output.append(str, 0, start);
      output.append(str, prefixEnd, str.length());
      return 1;
    } else if (suffixMatched) {
      output.append(str, 0, suffixStart);
      output.append(str, end, str.length());
      return 1;
    } else {
      output.append(str);
      return 0;
    }
  }
}
