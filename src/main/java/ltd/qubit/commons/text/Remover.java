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
 * 用于从字符串中移除内容的类。
 *
 * <p>此类提供了灵活的字符串内容移除功能，支持移除指定的字符、Unicode 代码点、
 * 子字符串、前缀、后缀或满足特定条件的字符。支持配置移除范围、大小写敏感性、
 * 数量限制等高级选项，适用于各种文本处理和数据清理场景。</p>
 *
 * <h3>基本字符移除</h3>
 * <pre>{@code
 * // 移除指定字符
 * String result = new Remover().forChar('*').removeFrom("a*b*c*d");
 * // 结果: "abcd"
 *
 * // 移除字符数组中的任意字符
 * String result = new Remover().forCharsIn('*', '#', '@').removeFrom("a*b#c@d");
 * // 结果: "abcd"
 *
 * // 移除字符序列中的字符
 * String result = new Remover().forCharsIn("aeiou").removeFrom("Hello World");
 * // 结果: "Hll Wrld"
 * }</pre>
 *
 * <h3>字符保留策略</h3>
 * <pre>{@code
 * // 移除不在指定字符集中的字符（保留指定字符）
 * String result = new Remover().forCharsNotIn('a', 'b', 'c').removeFrom("a1b2c3");
 * // 结果: "abc"
 *
 * // 移除所有不等于指定字符的字符
 * String result = new Remover().forCharsNotEqual('a').removeFrom("banana");
 * // 结果: "aaa"
 * }</pre>
 *
 * <h3>基于条件的字符移除</h3>
 * <pre>{@code
 * // 移除满足条件的字符（如数字）
 * String result = new Remover().forCharsSatisfy(Character::isDigit)
 *                              .removeFrom("abc123def456");
 * // 结果: "abcdef"
 *
 * // 移除不满足条件的字符（保留字母和空格）
 * String result = new Remover().forCharsNotSatisfy(ch -> Character.isLetter(ch) || ch == ' ')
 *                              .removeFrom("Hello, World! 123");
 * // 结果: "Hello World "
 *
 * // 使用自定义过滤器
 * CharFilter punctuationFilter = ch -> ".,!?;:".indexOf(ch) >= 0;
 * String result = new Remover().forCharsSatisfy(punctuationFilter)
 *                              .removeFrom("Hello, World!");
 * // 结果: "Hello World"
 * }</pre>
 *
 * <h3>Unicode 代码点移除</h3>
 * <pre>{@code
 * // 移除指定 Unicode 代码点（表情符号）
 * String result = new Remover().forCodePoint(0x1F600) // 😀
 *                              .removeFrom("Hello😀World😀");
 * // 结果: "HelloWorld"
 *
 * // 移除多个 Unicode 代码点
 * String result = new Remover().forCodePointsIn(0x1F600, 0x1F601, 0x1F602)
 *                              .removeFrom("😀Hello😁World😂");
 * // 结果: "HelloWorld"
 *
 * // 移除字符序列中的代码点
 * String result = new Remover().forCodePointsIn("😀😁😂")
 *                              .removeFrom("😀Hello😁World😂");
 * // 结果: "HelloWorld"
 *
 * // 移除不等于指定代码点的字符
 * String result = new Remover().forCodePointNotEqual('a')
 *                              .removeFrom("banana");
 * // 结果: "aaa"
 * }</pre>
 *
 * <h3>子字符串移除</h3>
 * <pre>{@code
 * // 移除子字符串
 * String result = new Remover().forSubstring("test").removeFrom("This is a test string test");
 * // 结果: "This is a  string "
 *
 * // 忽略大小写移除
 * String result = new Remover().forSubstring("HELLO")
 *                              .ignoreCase(true)
 *                              .removeFrom("hello world Hello");
 * // 结果: " world "
 * }</pre>
 *
 * <h3>前缀和后缀移除</h3>
 * <pre>{@code
 * // 移除前缀
 * String result = new Remover().forPrefix("Hello ").removeFrom("Hello World");
 * // 结果: "World"
 *
 * // 移除后缀
 * String result = new Remover().forSuffix(".txt").removeFrom("document.txt");
 * // 结果: "document"
 *
 * // 同时移除前缀和后缀
 * String result = new Remover().forPrefix("(").forSuffix(")").removeFrom("(content)");
 * // 结果: "content"
 *
 * // 忽略大小写移除前缀后缀
 * String result = new Remover().forPrefix("HTTP://")
 *                              .ignoreCase(true)
 *                              .removeFrom("http://example.com");
 * // 结果: "example.com"
 * }</pre>
 *
 * <h3>移除范围控制</h3>
 * <pre>{@code
 * // 指定移除范围
 * String result = new Remover().forChar('a')
 *                              .startFrom(2)
 *                              .endBefore(5)
 *                              .removeFrom("banana");
 * // 结果: "banna"
 *
 * // 移除指定范围内的子字符串
 * String result = new Remover().forSubstring("test")
 *                              .startFrom(5)
 *                              .endBefore(20)
 *                              .removeFrom("This test is a test case");
 * // 结果: "This test is a  case"
 * }</pre>
 *
 * <h3>移除数量限制</h3>
 * <pre>{@code
 * // 限制移除数量
 * String result = new Remover().forChar('a')
 *                              .limit(2)
 *                              .removeFrom("banana");
 * // 结果: "bana"
 *
 * // 组合范围和数量限制
 * String result = new Remover().forChar(' ')
 *                              .startFrom(1)
 *                              .endBefore(10)
 *                              .limit(3)
 *                              .removeFrom(" a b c d e f ");
 * // 结果: " abcdef "
 * }</pre>
 *
 * <h3>性能优化：直接输出到Appendable</h3>
 * <pre>{@code
 * // 使用 StringBuilder 输出（避免创建中间字符串）
 * StringBuilder sb = new StringBuilder();
 * int removed = new Remover().forChar('*').removeFrom("a*b*c*", sb);
 * // sb 内容: "abc", removed: 3
 *
 * // 输出到任意 Appendable
 * StringWriter writer = new StringWriter();
 * int removed = new Remover().forSubstring("test").removeFrom("test string", writer);
 * // writer 内容: " string", removed: 1
 * }</pre>
 *
 * <h3>链式调用示例</h3>
 * <pre>{@code
 * // 复杂的链式调用
 * String result = new Remover()
 *     .forCharsNotSatisfy(Character::isLetterOrDigit)
 *     .startFrom(0)
 *     .endBefore(50)
 *     .limit(10)
 *     .removeFrom("Hello, World! 123 @#$%");
 * // 结果: "HelloWorld123"
 *
 * // 组合多种配置
 * String cleaned = new Remover()
 *     .forCharsIn(" \t\n\r")
 *     .limit(5)
 *     .removeFrom(userInput);
 * }</pre>
 *
 * <h3>支持的移除策略</h3>
 * <ul>
 *   <li><strong>单字符</strong>：{@code forChar(char)} - 移除指定字符</li>
 *   <li><strong>字符集合</strong>：{@code forCharsIn(char...)} - 移除数组中的任意字符</li>
 *   <li><strong>字符排除</strong>：{@code forCharsNotIn(char...)} - 移除不在数组中的字符</li>
 *   <li><strong>条件过滤</strong>：{@code forCharsSatisfy(CharFilter)} - 移除满足条件的字符</li>
 *   <li><strong>Unicode代码点</strong>：{@code forCodePoint(int)} - 移除指定代码点</li>
 *   <li><strong>子字符串</strong>：{@code forSubstring(CharSequence)} - 移除子字符串</li>
 *   <li><strong>前缀</strong>：{@code forPrefix(CharSequence)} - 移除前缀</li>
 *   <li><strong>后缀</strong>：{@code forSuffix(CharSequence)} - 移除后缀</li>
 * </ul>
 *
 * <h3>支持的配置选项</h3>
 * <ul>
 *   <li><strong>范围控制</strong>：{@code startFrom(int)}, {@code endBefore(int)} - 指定移除范围</li>
 *   <li><strong>大小写</strong>：{@code ignoreCase(boolean)} - 忽略大小写比较</li>
 *   <li><strong>数量限制</strong>：{@code limit(int)} - 限制移除的最大数量</li>
 * </ul>
 *
 * <h3>特殊情况处理</h3>
 * <pre>{@code
 * // 处理 null 输入
 * String result = new Remover().forChar('a').removeFrom(null);
 * // 结果: null
 *
 * // 处理空字符串
 * String result = new Remover().forChar('a').removeFrom("");
 * // 结果: ""
 *
 * // 无匹配内容
 * String result = new Remover().forChar('z').removeFrom("hello");
 * // 结果: "hello"
 * }</pre>
 *
 * <h3>注意事项</h3>
 * <ul>
 *   <li>输入字符串为{@code null}时，{@code removeFrom()}方法返回{@code null}</li>
 *   <li>输入字符串为空时，{@code removeFrom()}方法返回空字符串</li>
 *   <li>移除策略是互斥的，后设置的策略会覆盖前面的策略</li>
 *   <li>前缀和后缀可以同时设置，但不能与其他移除策略同时使用</li>
 *   <li>范围索引超出字符串长度时会自动调整到有效范围</li>
 *   <li>所有配置方法都返回当前实例，支持链式调用</li>
 * </ul>
 *
 * @author 胡海星
 * @see ltd.qubit.commons.util.filter.character.CharFilter
 * @see ltd.qubit.commons.util.filter.codepoint.CodePointFilter
 * @see ltd.qubit.commons.text.Searcher
 * @see ltd.qubit.commons.text.Stripper
 */
public class Remover {

  /**
   * 字符过滤器，用于字符移除操作。
   */
  private CharFilter charFilter;

  /**
   * Unicode 代码点过滤器，用于代码点移除操作。
   */
  private CodePointFilter codePointFilter;

  /**
   * 要移除的子字符串。
   */
  private CharSequence substring;

  /**
   * 要移除的前缀字符串。
   */
  private CharSequence prefix;

  /**
   * 要移除的后缀字符串。
   */
  private CharSequence suffix;

  /**
   * 移除操作的起始索引（包含）。
   * 默认值为 0，表示从字符串开头开始。
   */
  private int startIndex = 0;

  /**
   * 移除操作的结束索引（不包含）。
   * 默认值为 {@link Integer#MAX_VALUE}，表示到字符串末尾。
   */
  private int endIndex = Integer.MAX_VALUE;

  /**
   * 是否忽略大小写进行比较。
   * 默认值为 {@code false}，表示区分大小写。
   * 仅适用于子字符串、前缀和后缀的移除操作。
   */
  private boolean ignoreCase = false;

  /**
   * 移除操作的最大数量限制。
   * 默认值为 {@link Integer#MAX_VALUE}，表示无限制。
   */
  private int limit = Integer.MAX_VALUE;

  /**
   * 构造一个新的 {@link Remover} 实例。
   *
   * <p>默认配置：</p>
   * <ul>
   *   <li>移除策略：未设置（需要调用 {@code forXxx} 方法设置）</li>
   *   <li>移除范围：整个字符串（{@code startIndex=0}, {@code endIndex=MAX_VALUE}）</li>
   *   <li>大小写敏感：区分大小写（{@code ignoreCase=false}）</li>
   *   <li>数量限制：无限制（{@code limit=MAX_VALUE}）</li>
   * </ul>
   */
  public Remover() {}

  /**
   * 清除所有移除策略设置。
   *
   * <p>此方法重置所有移除策略相关的字段为 {@code null}，
   * 但保持其他配置（如范围、大小写、数量限制）不变。
   * 调用此方法后需要重新设置移除策略。</p>
   */
  private void clearStrategies() {
    this.charFilter = null;
    this.codePointFilter = null;
    this.substring = null;
    this.prefix = null;
    this.suffix = null;
  }

  /**
   * 移除指定的字符。
   *
   * @param ch
   *     要移除的指定字符。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover forChar(final char ch) {
    this.clearStrategies();
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * 移除所有不等于指定字符的字符。
   *
   * @param ch
   *     指定的字符。源字符串中除此字符以外的所有字符都将被移除。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover forCharsNotEqual(final char ch) {
    this.clearStrategies();
    this.charFilter = new RejectSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * 移除指定数组中的所有字符。
   *
   * @param chars
   *     要移除的字符数组。{@code null} 值或空数组表示不移除任何字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除指定序列中的所有字符。
   *
   * @param chars
   *     要移除的字符序列。{@code null} 值或空序列表示不移除任何字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有不在指定数组中的字符。
   *
   * @param chars
   *     字符数组。不在此数组中的所有字符都将被移除。{@code null} 值或空数组表示移除源字符串中的所有字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有不在指定序列中的字符。
   *
   * @param chars
   *     字符序列。不在此序列中的所有字符都将被移除。{@code null} 值或空序列表示移除源字符串中的所有字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有满足指定过滤器的字符。
   *
   * @param filter
   *     接受要移除字符的过滤器。{@code null} 值表示不移除任何字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有不满足指定过滤器的字符。
   *
   * @param filter
   *     拒绝要移除字符的过滤器。{@code null} 值表示移除源字符串中的所有字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除指定的 Unicode 字符。
   *
   * @param codePoint
   *     要移除的 Unicode 字符的代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover forCodePoint(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 移除指定的 Unicode 字符。
   *
   * @param codePoint
   *     包含要移除的 Unicode 字符的字符序列。{@code null} 值或空值表示不移除任何 Unicode 代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有不等于指定代码点的 Unicode 字符。
   *
   * @param codePoint
   *     指定 Unicode 字符的代码点。源字符串中除此字符以外的所有 Unicode 字符都将被移除。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover forCodePointNotEqual(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 移除所有不等于指定代码点的 Unicode 字符。
   *
   * @param codePoint
   *     包含指定 Unicode 字符的字符序列。除此序列开头的代码点以外的所有 Unicode 代码点都将被移除。
   *     {@code null} 值表示移除源字符串中的所有 Unicode 字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有代码点在指定数组中的 Unicode 字符。
   *
   * @param codePoints
   *     要移除的 Unicode 字符的代码点数组。{@code null} 值或空数组表示不移除任何 Unicode 字符。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有代码点在指定序列中的 Unicode 字符。
   *
   * @param codePoints
   *     要移除的 Unicode 字符的代码点序列。{@code null} 值或空序列表示不移除任何 Unicode 代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除所有代码点不在指定数组中的 Unicode 字符。
   *
   * @param codePoints
   *     Unicode 代码点数组。代码点不在此数组中的所有 Unicode 字符都将被移除。
   *     {@code null} 值或空数组表示移除源字符串中的所有 Unicode 代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 从字符串中移除所有不在序列中的 Unicode 代码点。
   *
   * @param codePoints
   *     Unicode 字符的代码点序列。不在此序列中的所有代码点都将被移除。
   *     {@code null} 值或空值表示移除所有 Unicode 代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 从字符串中移除所有被指定过滤器接受的 Unicode 代码点。
   *
   * @param filter
   *     接受要移除的 Unicode 代码点的过滤器。{@code null} 值表示不移除任何 Unicode 代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 从字符串中移除所有被指定过滤器拒绝的 Unicode 代码点。
   *
   * @param filter
   *     拒绝要移除的 Unicode 代码点的过滤器。{@code null} 值表示移除所有 Unicode 代码点。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除指定的子字符串。
   *
   * @param substring
   *     要移除的子字符串。{@code null} 值或空值表示不移除任何子字符串。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover forSubstring(@Nullable final CharSequence substring) {
    this.clearStrategies();
    this.substring = nullToEmpty(substring);
    return this;
  }

  /**
   * 移除指定的前缀。
   *
   * @param prefix
   *     要移除的前缀。{@code null} 值或空值表示不移除任何前缀。
   * @return
   *     此 {@link Remover} 对象的引用。
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
   * 移除指定的后缀。
   *
   * @param suffix
   *     要移除的后缀。{@code null} 值或空值表示不移除任何后缀。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover forSuffix(@Nullable final CharSequence suffix) {
    // keep the old prefix, since we could remove both prefix and suffix
    final CharSequence oldPrefix = this.prefix;
    this.clearStrategies();
    this.prefix = oldPrefix;
    this.suffix = nullToEmpty(suffix);
    return this;
  }

  /**
   * 设置开始移除的起始索引。
   *
   * @param startIndex
   *     开始移除的起始索引（包含）。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover startFrom(final int startIndex) {
    this.startIndex = startIndex;
    return this;
  }

  /**
   * 设置结束移除的终止索引。
   *
   * @param endIndex
   *     结束移除的终止索引（不包含）。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover endBefore(final int endIndex) {
    this.endIndex = endIndex;
    return this;
  }

  /**
   * 设置是否忽略大小写。
   *
   * @param ignoreCase
   *     是否在比较时忽略大小写。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * 设置移除目标的最大数量限制。
   *
   * @param limit
   *     要移除的目标的最大数量。负值表示无限制。
   * @return
   *     此 {@link Remover} 对象的引用。
   */
  public Remover limit(final int limit) {
    this.limit = (limit < 0 ? Integer.MAX_VALUE : limit);
    return this;
  }

  /**
   * 从指定的字符序列中移除目标内容。
   *
   * @param str
   *     指定的源字符序列。
   * @return
   *     移除结果。如果 {@code str} 为 {@code null}，则返回 {@code null}。
   */
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

  /**
   * 从指定的字符序列中移除目标内容。
   *
   * @param str
   *     指定的源字符序列。如果为 {@code null}，此函数无效果并返回 0。
   * @param output
   *     用于追加移除结果的 {@link StringBuilder}。
   * @return
   *     已移除的目标数量。如果 {@code str} 为 {@code null}，此函数无效果并返回 0。
   */
  public int removeFrom(@Nullable final CharSequence str, final StringBuilder output) {
    try {
      return removeFrom(str, (Appendable) output);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * 从指定的字符序列中移除目标内容。
   *
   * @param str
   *     指定的源字符序列。如果为 {@code null}，此函数无效果并返回 0。
   * @param output
   *     用于追加移除结果的 {@link Appendable}。
   * @return
   *     已移除的目标数量。如果 {@code str} 为 {@code null}，此函数无效果并返回 0。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
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