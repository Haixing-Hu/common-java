////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.impl;

import java.io.IOException;

import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;

import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;

/**
 * The class provides functions for implementing the {@link Replacer} class.
 * It is intended to be used internally.
 *
 * @author Haixing Hu
 */
public class ReplacerImpl {

  public static int replaceChar(final CharSequence str, final int start,
      final int end, final CharFilter filter, final CharSequence replacement,
      final int limit, final Appendable output) throws IOException {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null
        && replacement != null
        && limit > 0);
    output.append(str, 0, start);
    int index = start;
    int count = 0;
    int pos;
    while ((pos = firstIndexOf(str, index, end, filter)) < end) {
      output.append(str, index, pos)
          .append(replacement);
      index = pos + 1;
      ++count;
      if (count == limit) {
        break;
      }
    }
    output.append(str, index, str.length());
    return count;
  }

  public static int replaceCodePoint(final CharSequence str, final int start,
      final int end, final CodePointFilter filter, final CharSequence replacement,
      final int limit, final Appendable output) throws IOException {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && filter != null
        && replacement != null
        && limit > 0);
    output.append(str, 0, start);
    int index = start;
    int count = 0;
    int pos;
    while ((pos = firstIndexOf(str, index, end, filter)) < end) {
      output.append(str, index, pos)
          .append(replacement);
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

  public static int replaceSubstring(final CharSequence str, final int start,
      final int end, final CharSequence substring, final boolean ignoreCase,
      final CharSequence replacement, final int limit, final Appendable output)
      throws IOException {
    assert (str != null
        && start >= 0
        && start <= end
        && end <= str.length()
        && substring != null
        && replacement != null
        && limit > 0);
    final int substringLen = substring.length();
    if (substringLen == 0) {
      output.append(str);
      return 0;
    }
    output.append(str, 0, start);
    int index = start;
    int count = 0;
    int pos;
    while ((pos = firstIndexOf(str, index, end, substring, ignoreCase)) < end) {
      output.append(str, index, pos).append(replacement);
      index = pos + substringLen;
      ++count;
      if (count == limit) {
        break;
      }
    }
    output.append(str, index, str.length());
    return count;
  }
}
