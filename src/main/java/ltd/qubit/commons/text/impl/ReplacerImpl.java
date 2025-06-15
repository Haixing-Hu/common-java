////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
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
 * 该类提供用于实现 {@link Replacer} 类的函数。
 * 它仅供内部使用。
 *
 * @author 胡海星
 */
public class ReplacerImpl {

  /**
   * 在字符序列中替换匹配指定字符过滤器的字符。
   *
   * @param str
   *     要处理的字符序列。
   * @param start
   *     要处理的起始位置（包含）。
   * @param end
   *     要处理的结束位置（不包含）。
   * @param filter
   *     字符过滤器。
   * @param replacement
   *     用于替换的字符序列。
   * @param limit
   *     最多替换的字符数量。
   * @param output
   *     结果输出的 {@link Appendable} 对象。
   * @return
   *     实际替换的字符数量。
   * @throws IOException
   *     如果输出时发生I/O错误。
   */
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

  /**
   * 在字符序列中替换匹配指定码点过滤器的码点。
   *
   * @param str
   *     要处理的字符序列。
   * @param start
   *     要处理的起始位置（包含）。
   * @param end
   *     要处理的结束位置（不包含）。
   * @param filter
   *     码点过滤器。
   * @param replacement
   *     用于替换的字符序列。
   * @param limit
   *     最多替换的码点数量。
   * @param output
   *     结果输出的 {@link Appendable} 对象。
   * @return
   *     实际替换的码点数量。
   * @throws IOException
   *     如果输出时发生I/O错误。
   */
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

  /**
   * 在字符序列中替换指定的子字符串。
   *
   * @param str
   *     要处理的字符序列。
   * @param start
   *     要处理的起始位置（包含）。
   * @param end
   *     要处理的结束位置（不包含）。
   * @param substring
   *     要替换的子字符串。
   * @param ignoreCase
   *     是否忽略大小写。
   * @param replacement
   *     用于替换的字符序列。
   * @param limit
   *     最多替换的子字符串数量。
   * @param output
   *     结果输出的 {@link Appendable} 对象。
   * @return
   *     实际替换的子字符串数量。
   * @throws IOException
   *     如果输出时发生I/O错误。
   */
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