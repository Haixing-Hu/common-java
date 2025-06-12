////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.CharUtils;
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

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;
import static ltd.qubit.commons.text.impl.ReplacerImpl.replaceChar;
import static ltd.qubit.commons.text.impl.ReplacerImpl.replaceCodePoint;
import static ltd.qubit.commons.text.impl.ReplacerImpl.replaceSubstring;

/**
 * 用于替换字符串中内容的类。
 *
 * <p>此类提供了灵活强大的字符串替换功能，支持替换字符、Unicode 代码点、子字符串等，
 * 并提供多种替换选项如忽略大小写、限制替换次数、指定替换范围等。</p>
 *
 * <p>使用示例：</p>
 * <pre><code>
 * // 替换单个字符
 * String result = new Replacer().searchForChar('o')
 *                              .replaceWithChar('0')
 *                              .applyTo("hello world");
 * // 结果: "hell0 w0rld"
 *
 * // 替换字符数组中的任意字符
 * String result = new Replacer().searchForCharsIn('a', 'e', 'i', 'o', 'u')
 *                              .replaceWithChar('*')
 *                              .applyTo("hello world");
 * // 结果: "h*ll* w*rld"
 *
 * // 替换满足条件的字符（如数字）
 * String result = new Replacer().searchForCharsSatisfy(Character::isDigit)
 *                              .replaceWithChar('X')
 *                              .applyTo("abc123def456");
 * // 结果: "abcXXXdefXXX"
 *
 * // 替换子字符串
 * String result = new Replacer().searchForSubstring("world")
 *                              .replaceWithString("Java")
 *                              .applyTo("Hello world!");
 * // 结果: "Hello Java!"
 *
 * // 忽略大小写替换
 * String result = new Replacer().searchForSubstring("WORLD")
 *                              .ignoreCase(true)
 *                              .replaceWithString("Java")
 *                              .applyTo("Hello world!");
 * // 结果: "Hello Java!"
 *
 * // 限制替换次数
 * String result = new Replacer().searchForChar('l')
 *                              .replaceWithChar('L')
 *                              .limit(2)
 *                              .applyTo("hello world");
 * // 结果: "heLLo world"
 *
 * // 指定替换范围
 * String result = new Replacer().searchForChar('l')
 *                              .replaceWithChar('L')
 *                              .startFrom(3)
 *                              .endBefore(8)
 *                              .applyTo("hello world");
 * // 结果: "helLo world"
 *
 * // 移除字符（替换为空字符串）
 * String result = new Replacer().searchForCharsIn(' ', '\t', '\n')
 *                              .replaceWithString("")
 *                              .applyTo("hello world");
 * // 结果: "helloworld"
 *
 * // 替换 Unicode 代码点（表情符号）
 * String result = new Replacer().searchForCodePoint(0x1F600) // 😀
 *                              .replaceWithString(":)")
 *                              .applyTo("Hello 😀 World");
 * // 结果: "Hello :) World"
 *
 * // 链式操作多次替换
 * String result = new Replacer().searchForChar('a')
 *                              .replaceWithChar('A')
 *                              .applyTo("banana");
 * result = new Replacer().searchForChar('n')
 *                       .replaceWithChar('N')
 *                       .applyTo(result);
 * // 结果: "bANANA"
 *
 * // 使用 StringBuilder 输出（避免创建中间字符串）
 * StringBuilder sb = new StringBuilder();
 * int count = new Replacer().searchForChar('o')
 *                          .replaceWithChar('0')
 *                          .applyTo("hello world", sb);
 * // sb 内容: "hell0 w0rld", count: 2
 * </code></pre>
 *
 * @author 胡海星
 */
public class Replacer {

  private enum Mode {
    CHAR,
    CODE_POINT,
    SUBSTRING,
    SUBSTRINGS,
  }

  private Mode mode = Mode.SUBSTRING;
  private CharFilter charFilter;
  private CodePointFilter codePointFilter;
  private CharSequence substring = EMPTY;
  private CharSequence[] substrings = EMPTY_STRING_ARRAY;
  private CharSequence replacement;
  private int startIndex = 0;
  private int endIndex = Integer.MAX_VALUE;
  private int limit = Integer.MAX_VALUE;
  private boolean ignoreCase = false;

  public Replacer() {}

  private void clearStrategies() {
    this.mode = Mode.SUBSTRING;
    this.charFilter = null;
    this.codePointFilter = null;
    this.substring = EMPTY;
    this.substrings = EMPTY_STRING_ARRAY;
  }

  /**
   * 替换指定的字符。
   *
   * @param ch
   *     要替换的字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForChar(final char ch) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * 替换所有不等于指定字符的字符。
   *
   * @param ch
   *     指定的字符。源字符串中除此字符以外的所有字符都将被替换。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharNotEqual(final char ch) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    this.charFilter = new RejectSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * 替换指定数组中的所有字符。
   *
   * @param chars
   *     要替换的字符数组。{@code null} 值或空数组表示不替换任何字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharsIn(@Nullable final char... chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * 替换指定序列中的所有字符。
   *
   * @param chars
   *     要替换的字符序列。{@code null} 值或空序列表示不替换任何字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * 替换所有不在指定数组中的字符。
   *
   * @param chars
   *     字符数组。不在此数组中的所有字符都将被替换。{@code null} 值或空数组表示替换源字符串中的所有字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharsNotIn(@Nullable final char... chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * 替换所有不在指定序列中的字符。
   *
   * @param chars
   *     字符序列。不在此序列中的所有字符都将被替换。{@code null} 值或空序列表示替换源字符串中的所有字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * 替换所有满足指定过滤器的字符。
   *
   * @param filter
   *     接受要替换字符的过滤器。{@code null} 值表示不替换任何字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharsSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if (filter == null) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = filter;
    }
    return this;
  }

  /**
   * 替换所有不满足指定过滤器的字符。
   *
   * @param filter
   *     拒绝要替换字符的过滤器。{@code null} 值表示替换源字符串中的所有字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCharsNotSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CHAR;
    if (filter == null) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * 替换指定的 Unicode 字符。
   *
   * @param codePoint
   *     要替换的 Unicode 字符的代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePoint(final int codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 替换指定的 Unicode 字符。
   *
   * @param codePoint
   *     包含要替换的 Unicode 字符的字符序列。{@code null} 值或空值表示不替换任何 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (codePoint == null || codePoint.length() == 0) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * 替换所有不等于指定代码点的 Unicode 字符。
   *
   * @param codePoint
   *     指定 Unicode 字符的代码点。源字符串中除此字符以外的所有 Unicode 字符都将被替换。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointNotEqual(final int codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 替换所有不等于指定代码点的 Unicode 字符。
   *
   * @param codePoint
   *     包含指定 Unicode 字符的字符序列。除此序列开头的代码点以外的所有 Unicode 代码点都将被替换。
   *     {@code null} 值表示替换源字符串中的所有 Unicode 字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (codePoint == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * 替换所有代码点在指定数组中的 Unicode 字符。
   *
   * @param codePoints
   *     要替换的 Unicode 字符的代码点数组。{@code null} 值或空数组表示不替换任何 Unicode 字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointsIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 替换所有代码点在指定序列中的 Unicode 字符。
   *
   * @param codePoints
   *     要替换的 Unicode 字符的代码点序列。{@code null} 值或空序列表示不替换任何 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if ((codePoints == null) || (codePoints.length() == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 替换所有代码点不在指定数组中的 Unicode 字符。
   *
   * @param codePoints
   *     Unicode 代码点数组。代码点不在此数组中的所有 Unicode 字符都将被替换。
   *     {@code null} 值或空数组表示替换源字符串中的所有 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointsNotIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 替换字符串中一组 Unicode 代码点的所有出现。
   *
   * @param codePoints
   *     Unicode 字符的代码点序列。不在此序列中的所有代码点都将被替换。
   *     {@code null} 值或空值表示替换所有 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (codePoints == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 替换字符串中指定 Unicode 代码点的所有出现。
   *
   * @param filter
   *     接受要替换的 Unicode 代码点的过滤器。{@code null} 值表示不替换任何 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointsSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (filter == null) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = filter;
    }
    return this;
  }

  /**
   * 替换字符串中指定 Unicode 代码点的所有出现。
   *
   * @param filter
   *     拒绝要替换的 Unicode 代码点的过滤器。{@code null} 值表示替换所有 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForCodePointsNotSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    this.mode = Mode.CODE_POINT;
    if (filter == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * 替换字符串中指定子字符串的所有出现。
   *
   * @param substring
   *     要替换的子字符串。{@code null} 值或空值表示不替换任何子字符串。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer searchForSubstring(final CharSequence substring) {
    this.clearStrategies();
    this.mode = Mode.SUBSTRING;
    this.substring = nullToEmpty(substring);
    return this;
  }

  // public Replacer forSubstringIn(final CharSequence... substrings) {
  //   this.clearStrategies();
  //   this.mode = Mode.SUBSTRINGS;
  //   this.substrings = substrings;
  //   return this;
  // }

  /**
   * 使用指定字符替换源字符串中的目标。
   *
   * @param replacement
   *     用于替换的指定字符。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer replaceWithChar(final char replacement) {
    this.replacement = CharUtils.toString(replacement);
    return this;
  }

  /**
   * 使用指定的 Unicode 代码点替换源字符串中的目标。
   *
   * @param replacement
   *     用于替换的指定 Unicode 代码点。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer replaceWithCodePoint(final int replacement) {
    this.replacement = Character.toString(replacement);
    return this;
  }

  /**
   * 使用指定的 Unicode 代码点替换源字符串中的目标。
   *
   * @param replacement
   *     包含用于替换的指定 Unicode 代码点的字符序列。{@code null} 值或空值表示使用空字符串替换目标，即移除目标。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer replaceWithCodePoint(@Nullable final CharSequence replacement) {
    if (replacement == null || replacement.length() == 0) {
      this.replacement = EMPTY;
    } else {
      final int cp = Character.codePointAt(replacement, 0);
      this.replacement = Character.toString(cp);
    }
    return this;
  }

  /**
   * 使用指定的字符序列替换源字符串中的目标。
   *
   * @param replacement
   *     用于替换的指定字符序列。{@code null} 值表示使用空字符串替换目标，即移除目标。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer replaceWithString(@Nullable final CharSequence replacement) {
    this.replacement = defaultIfNull(replacement, EMPTY);
    return this;
  }

  /**
   * 设置源字符串中开始替换的包含性索引。
   *
   * @param startIndex
   *     源字符串中开始替换的包含性索引。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer startFrom(final int startIndex) {
    this.startIndex = startIndex;
    return this;
  }

  /**
   * 设置源字符串中结束替换的排除性索引。
   *
   * @param endIndex
   *     源字符串中结束替换的排除性索引。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer endBefore(final int endIndex) {
    this.endIndex = endIndex;
    return this;
  }

  /**
   * 设置要替换的目标出现次数的最大值。
   *
   * @param limit
   *     要替换的目标出现次数的最大值。负值表示无限制。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer limit(final int limit) {
    this.limit = (limit < 0 ? Integer.MAX_VALUE : limit);
    return this;
  }

  /**
   * 设置比较是否应该忽略大小写（不区分大小写）。
   *
   * @param ignoreCase
   *     指示比较是否应该忽略大小写（不区分大小写）。
   * @return
   *     此 {@link Replacer} 对象的引用。
   */
  public Replacer ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * 对指定的源字符序列执行替换。
   *
   * @param str
   *     指定的源字符序列。如果为 {@code null} 或空，此函数不执行任何替换。
   * @return
   *     替换的结果，如果 {@code str} 为 {@code null} 则返回 {@code null}。
   */
  @Nullable
  public String applyTo(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return EMPTY;
    }
    if ((limit == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      return str.toString();
    }
    final StringBuilder builder = new StringBuilder();
    applyTo(str, builder);
    return builder.toString();
  }

  /**
   * 对指定的源字符序列执行替换。
   *
   * @param str
   *     指定的源字符序列。如果为 {@code null} 或空，此函数不执行任何替换。
   * @param output
   *     用于追加替换结果的 {@link StringBuilder}。
   * @return
   *     已被替换的出现次数，如果 {@code str} 为 {@code null} 则返回 {@code 0}。
   */
  public int applyTo(@Nullable final CharSequence str, final StringBuilder output) {
    try {
      return applyTo(str, (Appendable) output);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * 对指定的源字符序列执行替换。
   *
   * @param str
   *     指定的源字符序列。如果为 {@code null} 或空，此函数不执行任何替换。
   * @param output
   *     用于追加替换结果的 {@link Appendable}。
   * @return
   *     已被替换的出现次数，如果 {@code str} 为 {@code null} 则返回 {@code 0}。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public int applyTo(@Nullable final CharSequence str, final Appendable output)
      throws IOException {
    if (str == null) {
      return 0;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return 0;
    }
    if ((limit == 0)
        || (startIndex >= strLen)
        || (endIndex <= 0)
        || (startIndex >= endIndex)) {
      output.append(str);
      return 0;
    }
    final int start = Math.max(0, startIndex);
    final int end = Math.min(strLen, endIndex);
    switch (mode) {
      case CHAR:
        return replaceChar(str, start, end, charFilter, replacement, limit, output);
      case CODE_POINT:
        return replaceCodePoint(str, start, end, codePointFilter, replacement, limit, output);
      case SUBSTRING:
        return replaceSubstring(str, start, end, substring, ignoreCase, replacement, limit, output);
      default:
        throw new IllegalStateException("No replace target was specified.");
    }
  }
}