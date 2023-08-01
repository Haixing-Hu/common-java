////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.impl;

import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.text.Splitter;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;

import java.util.List;
import javax.annotation.Nullable;

import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;

/**
 * The class provides functions for implementing the {@link Splitter} class.
 * It is intended to be used internally.
 *
 * @author Haixing Hu
 */
public class SplitterImpl {

  public static List<String> splitByChar(@Nullable final CharSequence str,
      final CharFilter filter, final boolean strip, final boolean ignoreEmpty,
      final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    final int len = str.length();
    int start = 0;
    while (start <= len) {
      int end;
      for (end = start; end < len; ++end) {
        final char ch = str.charAt(end);
        if (filter.accept(ch)) {
          break;
        }
      }
      tryAddSubstring(str, start, end, strip, ignoreEmpty, result);
      start = end + 1;
    }
    return result;
  }

  public static List<String> splitByCodePoint(@Nullable final CharSequence str,
      final CodePointFilter filter, final boolean strip, final boolean ignoreEmpty,
      final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    final int len = str.length();
    int start = 0;
    while (start <= len) {
      int count = 1;
      int end;
      for (end = start; end < len; end += count) {
        final int codePoint = Character.codePointAt(str, end);
        count = Character.charCount(codePoint);
        if (filter.accept(codePoint)) {
          break;
        }
      }
      tryAddSubstring(str, start, end, strip, ignoreEmpty, result);
      start = end + count;
    }
    return result;
  }

  public static List<String> splitBySubstring(@Nullable final CharSequence str,
      final CharSequence separator, final boolean strip,
      final boolean ignoreEmpty, final boolean ignoreCase,
      final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    if (isEmpty(separator)) {
      // each character of the str is split as a substring
      return splitEachCodePoint(str, strip, ignoreEmpty, result);
    } else {
      // the separator string is not null nor empty
      return splitBySubstringImpl(str, separator, strip, ignoreEmpty, ignoreCase, result);
    }
  }

  public static List<String> splitEachChar(@Nullable final CharSequence str,
      final boolean strip, final boolean ignoreEmpty, final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    final int len = str.length();
    for (int i = 0; i < len; ++i) {
      final char ch = str.charAt(i);
      if (strip && CharUtils.isBlank(ch)) {
        if (!ignoreEmpty) {
          result.add(EMPTY);
        }
      } else {
        final String substr = CharUtils.toString(ch);
        result.add(substr);
      }
    }
    return result;
  }

  public static List<String> splitEachCodePoint(@Nullable final CharSequence str,
      final boolean strip, final boolean ignoreEmpty, final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    final int len = str.length();
    int i = 0;
    while (i < len) {
      final int codePoint = Character.codePointAt(str, i);
      final int j = i + Character.charCount(codePoint);
      if (strip && CharUtils.isBlank(codePoint)) {
        if (!ignoreEmpty) {
          result.add(EMPTY);
        }
      } else {
        final CharSequence substr = str.subSequence(i, j);
        result.add(substr.toString());
      }
      i = j;
    }
    return result;
  }

  private static List<String> splitBySubstringImpl(final CharSequence str,
      final CharSequence separator, final boolean strip, final boolean ignoreEmpty,
      final boolean ignoreCase, final List<String> result) {
    final int strLen = str.length();
    final int separatorLen = separator.length();
    int start = 0;
    while (start <= strLen) {
      final int end = firstIndexOf(str, start, strLen, separator, ignoreCase);
      tryAddSubstring(str, start, end, strip, ignoreEmpty, result);
      start = end + separatorLen;
    }
    return result;
  }

  public static List<String> splitLines(final CharSequence str,
      final boolean strip, final boolean ignoreEmpty, final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    final int len = str.length();
    int start = 0;
    while (start < len) {
      int end;
      for (end = start; end < len; ++end) {
        final char ch = str.charAt(end);
        if (ch == '\r' || ch == '\n') {
          break;
        }
      }
      tryAddSubstring(str, start, end, strip, ignoreEmpty, result);
      start = end + 1;
      if ((start < len)
          && (str.charAt(end) == '\r')
          && (str.charAt(start) == '\n')) {
        // skip the "\r\n"
        ++start;
      }
    }
    return result;
  }

  public static List<String> splitByCharType(final CharSequence str,
      final boolean strip, final boolean ignoreEmpty, final boolean camelCase,
      final List<String> result) {
    if (isEmpty(str)) {
      return splitEmptyString(str, ignoreEmpty, result);
    }
    final Stripper stripper = new Stripper();
    final int len = str.length();
    int tokenStart = 0;
    int codePoint = Character.codePointAt(str, 0);
    int charCount = Character.charCount(codePoint);
    int currentType = Character.getType(codePoint);
    for (int pos = tokenStart + charCount; pos < len; pos += charCount) {
      codePoint = Character.codePointAt(str, pos);
      charCount = Character.charCount(codePoint);
      final int type = Character.getType(codePoint);
      if (type == currentType) {
        continue;
      }
      if (camelCase
          && (type == Character.LOWERCASE_LETTER)
          && (currentType == Character.UPPERCASE_LETTER)) {
        final int newTokenStart = pos - charCount;
        if (newTokenStart != tokenStart) {
          CharSequence substr = str.subSequence(tokenStart, newTokenStart);
          if (strip) {
            substr = stripper.strip(substr);
          }
          if ((!ignoreEmpty) || (substr.length() > 0)) {
            result.add(substr.toString());
          }
          tokenStart = newTokenStart;
        }
      } else {
        CharSequence substr = str.subSequence(tokenStart, pos);
        if (strip) {
          substr = stripper.strip(substr);
        }
        if ((!ignoreEmpty) || (substr.length() > 0)) {
          result.add(substr.toString());
        }
        tokenStart = pos;
      }
      currentType = type;
    }
    // add the rest substring
    CharSequence substr = str.subSequence(tokenStart, len);
    if (strip) {
      substr = stripper.strip(substr);
    }
    if ((!ignoreEmpty) || (substr.length() > 0)) {
      result.add(substr.toString());
    }
    return result;
  }

  public static List<String> splitEmptyString(final CharSequence str,
      final boolean ignoreEmpty, final List<String> result) {
    if (str == null) {
      return result;
    }
    if (! ignoreEmpty) {
      result.add(EMPTY);
    }
    return result;
  }

  private static void tryAddSubstring(final CharSequence str, final int start,
      final int end, final boolean strip, final boolean ignoreEmpty,
      final List<String> result) {
    if (start < end) {
      CharSequence substr = str.subSequence(start, end);
      if (strip) {
        substr = new Stripper().strip(substr);
      }
      if ((!ignoreEmpty) || (substr.length() > 0)) {
        result.add(substr.toString());
      }
    } else if (!ignoreEmpty) {
      result.add(EMPTY);
    }
  }
}
