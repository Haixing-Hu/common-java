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
 * 并提供多种替换选项如忽略大小写、限制替换次数、指定替换范围等。适用于文本处理、
 * 数据清理、格式转换等多种场景。</p>
 *
 * <h3>基本字符替换</h3>
 * <pre>{@code
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
 * // 替换字符序列中的字符
 * String result = new Replacer().searchForCharsIn("aeiou")
 *                              .replaceWithChar('*')
 *                              .applyTo("hello world");
 * // 结果: "h*ll* w*rld"
 * }</pre>
 *
 * <h3>字符保留和排除策略</h3>
 * <pre>{@code
 * // 替换不等于指定字符的所有字符
 * String result = new Replacer().searchForCharNotEqual('a')
 *                              .replaceWithChar('*')
 *                              .applyTo("banana");
 * // 结果: "a*a*a*"
 *
 * // 替换不在指定字符集中的字符
 * String result = new Replacer().searchForCharsNotIn('a', 'b', 'c')
 *                              .replaceWithChar('*')
 *                              .applyTo("abc123def");
 * // 结果: "abc***abc"
 * }</pre>
 *
 * <h3>基于条件的字符替换</h3>
 * <pre>{@code
 * // 替换满足条件的字符（如数字）
 * String result = new Replacer().searchForCharsSatisfy(Character::isDigit)
 *                              .replaceWithChar('X')
 *                              .applyTo("abc123def456");
 * // 结果: "abcXXXdefXXX"
 *
 * // 替换不满足条件的字符（保留字母）
 * String result = new Replacer().searchForCharsNotSatisfy(Character::isLetter)
 *                              .replaceWithChar('_')
 *                              .applyTo("Hello, World! 123");
 * // 结果: "Hello__World____"
 *
 * // 使用自定义过滤器
 * CharFilter punctuationFilter = ch -> ".,!?;:".indexOf(ch) >= 0;
 * String result = new Replacer().searchForCharsSatisfy(punctuationFilter)
 *                              .replaceWithChar(' ')
 *                              .applyTo("Hello, World!");
 * // 结果: "Hello  World "
 * }</pre>
 *
 * <h3>Unicode 代码点替换</h3>
 * <pre>{@code
 * // 替换指定 Unicode 代码点（表情符号）
 * String result = new Replacer().searchForCodePoint(0x1F600) // 😀
 *                              .replaceWithString(":)")
 *                              .applyTo("Hello 😀 World");
 * // 结果: "Hello :) World"
 *
 * // 替换多个 Unicode 代码点
 * String result = new Replacer().searchForCodePointsIn(0x1F600, 0x1F601, 0x1F602)
 *                              .replaceWithString("[emoji]")
 *                              .applyTo("😀Hello😁World😂");
 * // 结果: "[emoji]Hello[emoji]World[emoji]"
 *
 * // 替换字符序列中的代码点
 * String result = new Replacer().searchForCodePointsIn("😀😁😂")
 *                              .replaceWithString("😊")
 *                              .applyTo("😀Hello😁World😂");
 * // 结果: "😊Hello😊World😊"
 *
 * // 替换不等于指定代码点的字符
 * String result = new Replacer().searchForCodePointNotEqual('a')
 *                              .replaceWithCodePoint('*')
 *                              .applyTo("banana");
 * // 结果: "a*a*a*"
 * }</pre>
 *
 * <h3>子字符串替换</h3>
 * <pre>{@code
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
 * }</pre>
 *
 * <h3>替换范围控制</h3>
 * <pre>{@code
 * // 指定替换范围
 * String result = new Replacer().searchForChar('l')
 *                              .replaceWithChar('L')
 *                              .startFrom(3)
 *                              .endBefore(8)
 *                              .applyTo("hello world");
 * // 结果: "helLo world"
 *
 * // 组合范围和数量限制
 * String result = new Replacer().searchForChar('a')
 *                              .replaceWithChar('A')
 *                              .startFrom(1)
 *                              .endBefore(5)
 *                              .limit(2)
 *                              .applyTo("banana");
 * // 结果: "bAnAna"
 * }</pre>
 *
 * <h3>替换数量限制</h3>
 * <pre>{@code
 * // 限制替换次数
 * String result = new Replacer().searchForChar('l')
 *                              .replaceWithChar('L')
 *                              .limit(2)
 *                              .applyTo("hello world");
 * // 结果: "heLLo world"
 *
 * // 无限制替换
 * String result = new Replacer().searchForChar('l')
 *                              .replaceWithChar('L')
 *                              .limit(-1)  // 或不调用limit()
 *                              .applyTo("hello world");
 * // 结果: "heLLo worLd"
 * }</pre>
 *
 * <h3>移除操作（替换为空）</h3>
 * <pre>{@code
 * // 移除字符（替换为空字符串）
 * String result = new Replacer().searchForCharsIn(' ', '\t', '\n')
 *                              .replaceWithString("")
 *                              .applyTo("hello world");
 * // 结果: "helloworld"
 *
 * // 移除数字
 * String result = new Replacer().searchForCharsSatisfy(Character::isDigit)
 *                              .replaceWithString("")
 *                              .applyTo("abc123def456");
 * // 结果: "abcdef"
 * }</pre>
 *
 * <h3>性能优化：直接输出到Appendable</h3>
 * <pre>{@code
 * // 使用 StringBuilder 输出（避免创建中间字符串）
 * StringBuilder sb = new StringBuilder();
 * int count = new Replacer().searchForChar('o')
 *                          .replaceWithChar('0')
 *                          .applyTo("hello world", sb);
 * // sb 内容: "hell0 w0rld", count: 2
 *
 * // 输出到任意 Appendable
 * StringWriter writer = new StringWriter();
 * int count = new Replacer().searchForSubstring("test")
 *                          .replaceWithString("demo")
 *                          .applyTo("test string", writer);
 * // writer 内容: "demo string", count: 1
 * }</pre>
 *
 * <h3>链式调用示例</h3>
 * <pre>{@code
 * // 复杂的链式调用
 * String result = new Replacer()
 *     .searchForCharsSatisfy(Character::isDigit)
 *     .replaceWithChar('X')
 *     .startFrom(0)
 *     .endBefore(50)
 *     .limit(5)
 *     .applyTo("abc123def456ghi789");
 * // 结果: "abcXXXdefXXghi789"
 *
 * // 多步替换
 * String text = "Hello, World! 123";
 * text = new Replacer().searchForCharsSatisfy(Character::isPunctuation)
 *                     .replaceWithChar(' ')
 *                     .applyTo(text);
 * text = new Replacer().searchForCharsSatisfy(Character::isDigit)
 *                     .replaceWithString("")
 *                     .applyTo(text);
 * // 结果: "Hello  World   "
 * }</pre>
 *
 * <h3>支持的搜索策略</h3>
 * <ul>
 *   <li><strong>单字符</strong>：{@code searchForChar(char)} - 搜索指定字符</li>
 *   <li><strong>字符集合</strong>：{@code searchForCharsIn(char...)} - 搜索数组中的任意字符</li>
 *   <li><strong>字符排除</strong>：{@code searchForCharsNotIn(char...)} - 搜索不在数组中的字符</li>
 *   <li><strong>条件过滤</strong>：{@code searchForCharsSatisfy(CharFilter)} - 搜索满足条件的字符</li>
 *   <li><strong>Unicode代码点</strong>：{@code searchForCodePoint(int)} - 搜索指定代码点</li>
 *   <li><strong>子字符串</strong>：{@code searchForSubstring(CharSequence)} - 搜索子字符串</li>
 * </ul>
 *
 * <h3>支持的替换类型</h3>
 * <ul>
 *   <li><strong>字符替换</strong>：{@code replaceWithChar(char)} - 用字符替换</li>
 *   <li><strong>代码点替换</strong>：{@code replaceWithCodePoint(int)} - 用Unicode代码点替换</li>
 *   <li><strong>字符串替换</strong>：{@code replaceWithString(CharSequence)} - 用字符串替换</li>
 * </ul>
 *
 * <h3>支持的配置选项</h3>
 * <ul>
 *   <li><strong>范围控制</strong>：{@code startFrom(int)}, {@code endBefore(int)} - 指定替换范围</li>
 *   <li><strong>大小写</strong>：{@code ignoreCase(boolean)} - 忽略大小写比较</li>
 *   <li><strong>数量限制</strong>：{@code limit(int)} - 限制替换的最大数量</li>
 * </ul>
 *
 * <h3>特殊情况处理</h3>
 * <pre>{@code
 * // 处理 null 输入
 * String result = new Replacer().searchForChar('a')
 *                              .replaceWithChar('A')
 *                              .applyTo(null);
 * // 结果: null
 *
 * // 处理空字符串
 * String result = new Replacer().searchForChar('a')
 *                              .replaceWithChar('A')
 *                              .applyTo("");
 * // 结果: ""
 *
 * // 无匹配内容
 * String result = new Replacer().searchForChar('z')
 *                              .replaceWithChar('Z')
 *                              .applyTo("hello");
 * // 结果: "hello"
 * }</pre>
 *
 * <h3>注意事项</h3>
 * <ul>
 *   <li>输入字符串为{@code null}时，{@code applyTo()}方法返回{@code null}</li>
 *   <li>输入字符串为空时，{@code applyTo()}方法返回空字符串</li>
 *   <li>搜索策略是互斥的，后设置的策略会覆盖前面的策略</li>
 *   <li>必须同时设置搜索策略和替换内容才能进行替换操作</li>
 *   <li>范围索引超出字符串长度时会自动调整到有效范围</li>
 *   <li>所有配置方法都返回当前实例，支持链式调用</li>
 * </ul>
 *
 * @author 胡海星
 * @see ltd.qubit.commons.util.filter.character.CharFilter
 * @see ltd.qubit.commons.util.filter.codepoint.CodePointFilter
 * @see ltd.qubit.commons.text.Searcher
 * @see ltd.qubit.commons.text.Remover
 */
public class Replacer {

  /**
   * 替换模式枚举，定义不同的搜索策略类型。
   */
  private enum Mode {
    /** 字符替换模式 */
    CHAR,
    /** Unicode 代码点替换模式 */
    CODE_POINT,
    /** 子字符串替换模式 */
    SUBSTRING,
    /** 多个子字符串替换模式（保留用于未来扩展） */
    SUBSTRINGS,
  }

  /**
   * 当前替换模式，默认为子字符串模式。
   */
  private Mode mode = Mode.SUBSTRING;

  /**
   * 字符过滤器，用于字符替换操作。
   */
  private CharFilter charFilter;

  /**
   * Unicode 代码点过滤器，用于代码点替换操作。
   */
  private CodePointFilter codePointFilter;

  /**
   * 要搜索的子字符串，默认为空字符串。
   */
  private CharSequence substring = EMPTY;

  /**
   * 要搜索的子字符串数组（保留用于未来扩展），默认为空数组。
   */
  private CharSequence[] substrings = EMPTY_STRING_ARRAY;

  /**
   * 用于替换的内容。
   */
  private CharSequence replacement;

  /**
   * 替换操作的起始索引（包含），默认为 0。
   */
  private int startIndex = 0;

  /**
   * 替换操作的结束索引（不包含），默认为 {@link Integer#MAX_VALUE}。
   */
  private int endIndex = Integer.MAX_VALUE;

  /**
   * 替换操作的最大数量限制，默认为 {@link Integer#MAX_VALUE}（无限制）。
   */
  private int limit = Integer.MAX_VALUE;

  /**
   * 是否忽略大小写进行比较，默认为 {@code false}。
   * 仅适用于子字符串替换操作。
   */
  private boolean ignoreCase = false;

  /**
   * 构造一个新的 {@link Replacer} 实例。
   *
   * <p>默认配置：</p>
   * <ul>
   *   <li>替换模式：子字符串模式（{@link Mode#SUBSTRING}）</li>
   *   <li>搜索策略：未设置（需要调用 {@code searchForXxx} 方法设置）</li>
   *   <li>替换内容：未设置（需要调用 {@code replaceWithXxx} 方法设置）</li>
   *   <li>替换范围：整个字符串（{@code startIndex=0}, {@code endIndex=MAX_VALUE}）</li>
   *   <li>大小写敏感：区分大小写（{@code ignoreCase=false}）</li>
   *   <li>数量限制：无限制（{@code limit=MAX_VALUE}）</li>
   * </ul>
   */
  public Replacer() {}

  /**
   * 清除所有搜索策略设置。
   *
   * <p>此方法重置替换模式为子字符串模式，并清除所有搜索策略相关的字段，
   * 但保持其他配置（如范围、大小写、数量限制、替换内容）不变。
   * 调用此方法后需要重新设置搜索策略。</p>
   */
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