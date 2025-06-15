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
import ltd.qubit.commons.util.filter.codepoint.AcceptAllCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.AcceptSpecifiedCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.BlankCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectAllCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectSpecifiedCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.WhitespaceCodePointFilter;

import static ltd.qubit.commons.lang.StringUtils.EMPTY;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.text.impl.SearcherImpl.afterLastIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.endsWithCodePoint;
import static ltd.qubit.commons.text.impl.SearcherImpl.firstIndexOf;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithChar;
import static ltd.qubit.commons.text.impl.SearcherImpl.startsWithCodePoint;

/**
 * 用于剥离字符串中指定字符的类。
 *
 * <p>此类提供了灵活的字符串剥离功能，支持从字符串的开始、结束或两端剥离指定的字符、
 * Unicode 代码点或满足特定条件的字符。特别适用于处理字符串的空白字符清理、
 * 格式化处理和数据预处理等场景。</p>
 *
 * <h3>基本用法</h3>
 * <pre>{@code
 * // 剥离空白字符（默认从两端剥离）
 * String result = new Stripper().ofBlank().strip("  hello world  ");
 * // 结果: "hello world"
 *
 * // 剥离指定字符
 * String result = new Stripper().ofChar('*').strip("***hello***");
 * // 结果: "hello"
 * }</pre>
 *
 * <h3>剥离方向控制</h3>
 * <pre>{@code
 * // 仅从开始剥离
 * String result = new Stripper().ofBlank().fromStart().strip("  hello world  ");
 * // 结果: "hello world  "
 *
 * // 仅从结束剥离
 * String result = new Stripper().ofBlank().fromEnd().strip("  hello world  ");
 * // 结果: "  hello world"
 *
 * // 从两端剥离（默认行为）
 * String result = new Stripper().ofBlank().fromBothSide().strip("  hello world  ");
 * // 结果: "hello world"
 * }</pre>
 *
 * <h3>字符剥离策略</h3>
 * <pre>{@code
 * // 剥离字符数组中的任意字符
 * String result = new Stripper().ofCharsIn('*', '#', '@').strip("##*hello*##");
 * // 结果: "hello"
 *
 * // 剥离不在指定字符集中的字符
 * String result = new Stripper().ofCharsNotIn('a', 'b', 'c').strip("xyzabcxyz");
 * // 结果: "abc"
 *
 * // 剥离字符串中的任意字符
 * String result = new Stripper().ofCharsIn("*#@").strip("##*hello*##");
 * // 结果: "hello"
 *
 * // 剥离除指定字符外的所有字符
 * String result = new Stripper().ofCharsNotEqual('a').strip("xxxaxxx");
 * // 结果: "a"
 * }</pre>
 *
 * <h3>基于条件的剥离</h3>
 * <pre>{@code
 * // 剥离满足条件的字符（如数字）
 * String result = new Stripper().ofCharsSatisfy(Character::isDigit)
 *                              .strip("123hello456");
 * // 结果: "hello"
 *
 * // 剥离不满足条件的字符（保留字母数字）
 * String result = new Stripper().ofCharsNotSatisfy(Character::isLetterOrDigit)
 *                              .strip("!!!Hello123World!!!");
 * // 结果: "Hello123World"
 *
 * // 使用自定义过滤器
 * CharFilter customFilter = ch -> ch == '_' || ch == '-';
 * String result = new Stripper().ofCharsSatisfy(customFilter)
 *                              .strip("__hello-world__");
 * // 结果: "hello-world"
 * }</pre>
 *
 * <h3>Unicode 代码点剥离</h3>
 * <pre>{@code
 * // 剥离指定 Unicode 代码点
 * String result = new Stripper().ofCodePoint(0x1F600) // 😀 表情符号
 *                              .strip("😀😀hello😀😀");
 * // 结果: "hello"
 *
 * // 剥离代码点数组中的任意代码点
 * int[] emojiCodePoints = {0x1F600, 0x1F601, 0x1F602};
 * String result = new Stripper().ofCodePointsIn(emojiCodePoints)
 *                              .strip("😀😁😂text😂😁😀");
 * // 结果: "text"
 *
 * // 剥离字符串中的代码点
 * String result = new Stripper().ofCodePointsIn("😀😁😂")
 *                              .strip("😀😁hello😂😀");
 * // 结果: "hello"
 * }</pre>
 *
 * <h3>空白字符处理</h3>
 * <pre>{@code
 * // 剥离空白字符（包括不可打印字符）
 * String result = new Stripper().ofBlank().strip("\t  hello world  \n");
 * // 结果: "hello world"
 *
 * // 剥离 Unicode 空白字符
 * String result = new Stripper().ofWhitespace().strip("  hello world  ");
 * // 结果: "hello world"
 * }</pre>
 *
 * <h3>实用方法</h3>
 * <pre>{@code
 * // stripToNull - 如果结果为空则返回 null
 * String result = new Stripper().ofBlank().stripToNull("   ");
 * // 结果: null
 *
 * String result2 = new Stripper().ofBlank().stripToNull("  hello  ");
 * // 结果: "hello"
 *
 * // stripToEmpty - 如果输入为 null 则返回空字符串
 * String result = new Stripper().ofBlank().stripToEmpty(null);
 * // 结果: ""
 *
 * // 测试是否可剥离
 * boolean canStrip = new Stripper().ofBlank().isStrippable("  hello  ");
 * // 结果: true
 *
 * boolean canStripFromStart = new Stripper().ofChar('*')
 *                                          .fromStart()
 *                                          .isStrippable("*hello");
 * // 结果: true
 *
 * // 任意一侧可剥离测试
 * boolean hasTargetChar = new Stripper().ofChar('*')
 *                                      .fromAnySide()
 *                                      .isStrippable("hello*");
 * // 结果: true
 * }</pre>
 *
 * <h3>性能优化：直接输出到Appendable</h3>
 * <pre>{@code
 * // 使用 StringBuilder 输出（避免创建中间字符串）
 * StringBuilder sb = new StringBuilder();
 * int length = new Stripper().ofChar('*').strip("***hello***", sb);
 * // sb 内容: "hello", length: 5
 *
 * // 输出到任意 Appendable
 * StringWriter writer = new StringWriter();
 * int length = new Stripper().ofBlank().strip("  text  ", writer);
 * // writer 内容: "text", length: 4
 * }</pre>
 *
 * <h3>链式调用示例</h3>
 * <pre>{@code
 * // 复杂的链式调用
 * String result = new Stripper()
 *     .ofCharsNotSatisfy(Character::isLetterOrDigit)
 *     .fromBothSide()
 *     .strip("!!!Hello123World!!!");
 * // 结果: "Hello123World"
 *
 * // 组合多种条件
 * String cleaned = new Stripper()
 *     .ofCharsIn(" \t\n\r*#@")
 *     .fromStart()
 *     .stripToEmpty(userInput);
 * }</pre>
 *
 * <h3>支持的剥离策略</h3>
 * <ul>
 *   <li><strong>单字符</strong>：{@code ofChar(char)} - 剥离指定字符</li>
 *   <li><strong>字符集合</strong>：{@code ofCharsIn(char...)} - 剥离数组中的任意字符</li>
 *   <li><strong>字符排除</strong>：{@code ofCharsNotIn(char...)} - 剥离不在数组中的字符</li>
 *   <li><strong>条件过滤</strong>：{@code ofCharsSatisfy(CharFilter)} - 剥离满足条件的字符</li>
 *   <li><strong>Unicode代码点</strong>：{@code ofCodePoint(int)} - 剥离指定代码点</li>
 *   <li><strong>空白字符</strong>：{@code ofBlank()} - 剥离空白和不可打印字符</li>
 *   <li><strong>空格字符</strong>：{@code ofWhitespace()} - 剥离Unicode空格字符</li>
 * </ul>
 *
 * <h3>支持的剥离方向</h3>
 * <ul>
 *   <li><strong>开始</strong>：{@code fromStart()} - 仅从字符串开头剥离</li>
 *   <li><strong>结束</strong>：{@code fromEnd()} - 仅从字符串结尾剥离</li>
 *   <li><strong>两端</strong>：{@code fromBothSide()} - 从开头和结尾剥离（默认）</li>
 *   <li><strong>任意</strong>：{@code fromAnySide()} - 仅用于{@code isStrippable()}测试</li>
 * </ul>
 *
 * <h3>注意事项</h3>
 * <ul>
 *   <li>输入字符串为{@code null}时，{@code strip()}方法返回{@code null}</li>
 *   <li>输入字符串为空时，{@code strip()}方法返回空字符串</li>
 *   <li>如果完全剥离后字符串为空，{@code stripToNull()}返回{@code null}</li>
 *   <li>{@code fromAnySide()}方向仅能用于{@code isStrippable()}测试，不能用于实际剥离</li>
 *   <li>字符过滤器和代码点过滤器不能同时使用，后设置的会覆盖前者</li>
 *   <li>所有配置方法都返回当前实例，支持链式调用</li>
 * </ul>
 *
 * @author 胡海星
 * @see ltd.qubit.commons.util.filter.character.CharFilter
 * @see ltd.qubit.commons.util.filter.codepoint.CodePointFilter
 * @see ltd.qubit.commons.lang.CharUtils
 * @see Character#isWhitespace(int)
 */
public class Stripper {

  /**
   * 剥离方向常量：从字符串开始位置剥离。
   */
  private static final int DIRECTION_START = 0;

  /**
   * 剥离方向常量：从字符串结束位置剥离。
   */
  private static final int DIRECTION_END = 1;

  /**
   * 剥离方向常量：从字符串两端剥离。
   */
  private static final int DIRECTION_BOTH = 2;

  /**
   * 剥离方向常量：从字符串任意一端剥离（仅用于测试）。
   */
  private static final int DIRECTION_ANY = 3;

  /**
   * 字符过滤器，用于字符剥离操作。
   */
  private CharFilter charFilter = null;

  /**
   * Unicode 代码点过滤器，用于代码点剥离操作。
   * 默认为 {@link BlankCodePointFilter#INSTANCE}，用于剥离空白字符。
   */
  private CodePointFilter codePointFilter = BlankCodePointFilter.INSTANCE;

  /**
   * 剥离方向，指定从字符串的哪个位置进行剥离。
   * 默认值为 {@link #DIRECTION_BOTH}，表示从两端剥离。
   */
  private int direction = DIRECTION_BOTH;

  /**
   * 构造一个新的 {@link Stripper} 实例。
   *
   * <p>默认配置：</p>
   * <ul>
   *   <li>剥离策略：剥离空白字符（{@link BlankCodePointFilter}）</li>
   *   <li>剥离方向：从两端剥离（{@link #DIRECTION_BOTH}）</li>
   *   <li>字符过滤器：未设置（需要调用 {@code ofXxx} 方法设置）</li>
   * </ul>
   */
  public Stripper() {}

  /**
   * 清除所有剥离策略设置。
   *
   * <p>此方法重置字符过滤器和代码点过滤器为 {@code null}，
   * 但保持剥离方向设置不变。调用此方法后需要重新设置剥离策略。</p>
   */
  private void clearStrategies() {
    this.charFilter = null;
    this.codePointFilter = null;
  }

  /**
   * 剥离指定字符。
   *
   * @param ch
   *     要剥离的指定字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofChar(final char ch) {
    this.clearStrategies();
    this.charFilter = new AcceptSpecifiedCharFilter(ch);
    return this;
  }

  /**
   * 剥离任何不等于指定字符的字符。
   *
   * @param ch
   *     指定字符。源字符串中除此字符以外的所有字符都将被剥离。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsNotEqual(final char ch) {
    this.clearStrategies();
    this.charFilter = CharFilter.not(new AcceptSpecifiedCharFilter(ch));
    return this;
  }

  /**
   * 剥离指定数组中的任何字符。
   *
   * @param chars
   *     要剥离的字符数组。{@code null} 值或空数组表示不剥离任何字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * 剥离指定序列中的任何字符。
   *
   * @param chars
   *     要剥离的字符序列。{@code null} 值或空序列表示不剥离任何字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * 剥离任何不在指定数组中的字符。
   *
   * @param chars
   *     字符数组。不在此数组中的所有字符都将被剥离。{@code null} 值或空数组表示剥离源字符串中的所有字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsNotIn(@Nullable final char... chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * 剥离任何不在指定序列中的字符。
   *
   * @param chars
   *     字符序列。不在此序列中的所有字符都将被剥离。{@code null} 值或空序列表示剥离源字符串中的所有字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if ((chars == null) || (chars.length() == 0)) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * 剥离任何被指定过滤器接受的字符。
   *
   * @param filter
   *     接受要剥离字符的过滤器。{@code null} 值表示不剥离任何字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsSatisfy(@Nullable final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.charFilter = filter;
    }
    return this;
  }

  /**
   * 剥离任何被指定过滤器拒绝的字符。
   *
   * @param filter
   *     拒绝要剥离字符的过滤器。{@code null} 值表示剥离源字符串中的所有字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCharsNotSatisfy(final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.charFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.charFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * 剥离指定的 Unicode 代码点。
   *
   * @param codePoint
   *     要剥离的指定 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePoint(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 剥离指定的 Unicode 代码点。
   *
   * @param codePoint
   *     包含要剥离的 Unicode 字符的字符序列。{@code null} 值或空值表示不剥离任何 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * 剥离任何不等于指定代码点的 Unicode 代码点。
   *
   * @param codePoint
   *     指定 Unicode 字符的代码点。源字符串中除此字符以外的所有 Unicode 字符都将被剥离。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsNotEqual(final int codePoint) {
    this.clearStrategies();
    this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 剥离指定的 Unicode 代码点。
   *
   * @param codePoint
   *     包含指定 Unicode 字符的字符序列。除此序列开头的代码点以外的所有 Unicode 代码点都将被剥离。
   *     {@code null} 值表示剥离源字符串中的所有 Unicode 字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * 剥离指定数组中的任何 Unicode 代码点。
   *
   * @param codePoints
   *     要剥离的 Unicode 字符的代码点数组。{@code null} 值或空数组表示不剥离任何 Unicode 字符。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 剥离指定序列中的任何 Unicode 代码点。
   *
   * @param codePoints
   *     要剥离的 Unicode 字符的代码点序列。{@code null} 值或空序列表示不剥离任何 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length() == 0)) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 剥离任何不在指定数组中的 Unicode 代码点。
   *
   * @param codePoints
   *     Unicode 代码点数组。代码点不在此数组中的所有 Unicode 字符都将被剥离。
   *     {@code null} 值或空数组表示剥离源字符串中的所有 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsNotIn(@Nullable final int... codePoints) {
    this.clearStrategies();
    if ((codePoints == null) || (codePoints.length == 0)) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 剥离任何不在指定序列中的 Unicode 代码点。
   *
   * @param codePoints
   *     Unicode 字符的代码点序列。不在此序列中的所有代码点都将被剥离。
   *     {@code null} 值或空值表示剥离所有 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 剥离任何被指定过滤器接受的 Unicode 代码点。
   *
   * @param filter
   *     接受要剥离的 Unicode 代码点的过滤器。{@code null} 值表示不剥离任何 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsSatisfy(final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = filter;
    }
    return this;
  }

  /**
   * 剥离任何被指定过滤器拒绝的 Unicode 代码点。
   *
   * @param filter
   *     拒绝要剥离的 Unicode 代码点的过滤器。{@code null} 值表示剥离所有 Unicode 代码点。
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper ofCodePointsNotSatisfy(final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.codePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.codePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * 剥离所有空白（即不可打印或空格）Unicode 代码点。
   *
   * @return
   *     此 {@link Stripper} 对象的引用。
   * @see ltd.qubit.commons.lang.CharUtils#isBlank(int)
   */
  public Stripper ofBlank() {
    this.clearStrategies();
    this.codePointFilter = BlankCodePointFilter.INSTANCE;
    return this;
  }

  /**
   * 剥离所有空白字符 Unicode 代码点。
   *
   * @return
   *     此 {@link Stripper} 对象的引用。
   * @see Character#isWhitespace(int)
   */
  public Stripper ofWhitespace() {
    this.clearStrategies();
    this.codePointFilter = WhitespaceCodePointFilter.INSTANCE;
    return this;
  }

  /**
   * 从源字符串的开始位置剥离。
   *
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper fromStart() {
    this.direction = DIRECTION_START;
    return this;
  }

  /**
   * 从源字符串的结束位置剥离。
   *
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper fromEnd() {
    this.direction = DIRECTION_END;
    return this;
  }

  /**
   * 从源字符串的开始和结束位置都剥离。
   *
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper fromBothSide() {
    this.direction = DIRECTION_BOTH;
    return this;
  }

  /**
   * 从源字符串的开始或结束位置剥离。
   *
   * @return
   *     此 {@link Stripper} 对象的引用。
   */
  public Stripper fromAnySide() {
    this.direction = DIRECTION_ANY;
    return this;
  }

  /**
   * 从指定的源字符串中剥离目标。
   *
   * @param str
   *     指定的源字符串。
   * @return
   *     剥离结果。如果 {@code str} 为 {@code null}，返回 {@code null}。
   */
  @Nullable
  public String strip(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    if (str.length() == 0) {
      return EMPTY;
    }
    switch (direction) {
      case DIRECTION_START:
        return stripStart(str);
      case DIRECTION_END:
        return stripEnd(str);
      case DIRECTION_BOTH:
        return stripBothSide(str);
      default:
        throw new IllegalStateException("Cannot strip strings from ANY side. "
            + "Must strip strings either from start, or from end, or from BOTH side.");
    }
  }

  /**
   * 从指定的源字符串中剥离目标。
   *
   * @param str
   *     指定的源字符串。如果为 {@code null}，此函数不执行任何操作并返回 0。
   * @param output
   *     用于追加剥离结果的 {@link StringBuilder}。
   * @return
   *     已剥离目标的数量。如果 {@code str} 为 {@code null}，此函数不执行任何操作并返回 0。
   */
  public int strip(@Nullable final CharSequence str, final StringBuilder output) {
    try {
      return strip(str, (Appendable) output);
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * 从指定的源字符串中剥离目标。
   *
   * @param str
   *     指定的源字符串。如果为 {@code null}，此函数不执行任何操作并返回 0。
   * @param output
   *     用于追加剥离结果的 {@link Appendable}。
   * @return
   *     已剥离目标的数量。如果 {@code str} 为 {@code null}，此函数不执行任何操作并返回 0。
   */
  public int strip(@Nullable final CharSequence str, final Appendable output)
      throws IOException {
    if (str == null) {
      return 0;
    }
    if (str.length() == 0) {
      return 0;
    }
    switch (direction) {
      case DIRECTION_START:
        return stripStart(str, output);
      case DIRECTION_END:
        return stripEnd(str, output);
      case DIRECTION_BOTH:
        return stripBothSide(str, output);
      default:
        throw new IllegalStateException("Cannot strip strings from ANY side. "
            + "Must strip strings either from start, or from end, or from BOTH side.");
    }
  }

  /**
   * 从指定的源字符串中剥离目标。如果源字符串被剥离为空字符串则返回 {@code null}。
   *
   * @param str
   *     指定的源字符串。
   * @return
   *     剥离结果，如果 {@code str} 被剥离为空字符串则返回 {@code null}。
   *     如果 {@code str} 为 {@code null}，返回 {@code null}。
   */
  public String stripToNull(@Nullable final CharSequence str) {
    final String result = strip(str);
    if (isEmpty(result)) {
      return null;
    } else {
      return result;
    }
  }

  /**
   * 从指定的源字符串中剥离目标。如果源字符串为 {@code null} 则返回空字符串。
   *
   * @param str
   *     指定的源字符串。
   * @return
   *     剥离结果，如果 {@code str} 为 {@code null} 则返回空字符串。
   */
  public String stripToEmpty(@Nullable final CharSequence str) {
    final String result = strip(str);
    if (isEmpty(result)) {
      return EMPTY;
    } else {
      return result;
    }
  }

  /**
   * 测试源字符串在指定侧是否可以剥离指定目标。
   *
   * @param str
   *     指定的源字符串。
   * @return
   *     源字符串在指定侧是否可以剥离指定目标。
   */
  public boolean isStrippable(@Nullable final CharSequence str) {
    if (str == null || str.length() == 0) {
      return false;
    }
    switch (direction) {
      case DIRECTION_START:
        return isStartStrippable(str);
      case DIRECTION_END:
        return isEndStrippable(str);
      case DIRECTION_BOTH:
        return isBothSideStrippable(str);
      case DIRECTION_ANY:
      default:
        return isAnySideStrippable(str);
    }
  }

  private String stripStart(final CharSequence str) {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= len) {
      return EMPTY;
    } else if (start == 0) {
      return str.toString();
    } else {
      return str.subSequence(start, len).toString();
    }
  }

  private int stripStart(final CharSequence str, final Appendable output)
      throws IOException {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= len) {
      return 0;
    } else if (start == 0) {
      output.append(str);
      return len;
    } else {
      output.append(str, start, len);
      return len - start;
    }
  }

  private String stripEnd(final CharSequence str) {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (end <= 0) {
      return EMPTY;
    } else if (end == len) {
      return str.toString();
    } else {
      return str.subSequence(0, end).toString();
    }
  }

  private int stripEnd(final CharSequence str, final Appendable output)
      throws IOException {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      end = afterLastIndexOf(str, 0, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (end <= 0) {
      return 0;
    } else if (end == len) {
      output.append(str);
      return len;
    } else {
      output.append(str, 0, end);
      return end;
    }
  }

  private String stripBothSide(final CharSequence str) {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= end) {
      return EMPTY;
    } else if ((start == 0) && (end == len)) {
      return str.toString();
    } else {
      return str.subSequence(start, end).toString();
    }
  }

  private int stripBothSide(final CharSequence str, final Appendable output)
      throws IOException {
    assert (str != null && str.length() > 0);
    final int len = str.length();
    final int start;
    final int end;
    if (charFilter != null) {
      final CharFilter negativeFilter = CharFilter.not(charFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else if (codePointFilter != null) {
      final CodePointFilter negativeFilter = CodePointFilter.not(codePointFilter);
      start = firstIndexOf(str, 0, len, negativeFilter);
      end = afterLastIndexOf(str, start, len, negativeFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
    if (start >= end) {
      return 0;
    } else if ((start == 0) && (end == len)) {
      output.append(str);
      return len;
    } else {
      output.append(str, start, end);
      return end - start;
    }
  }

  private boolean isStartStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return startsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return startsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }

  private boolean isEndStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return endsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return endsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }

  private boolean isBothSideStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return startsWithChar(str, 0, str.length(), charFilter)
          && endsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return startsWithCodePoint(str, 0, str.length(), codePointFilter)
          && endsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }

  private boolean isAnySideStrippable(final CharSequence str) {
    assert (str != null && str.length() > 0);
    if (charFilter != null) {
      return startsWithChar(str, 0, str.length(), charFilter)
          || endsWithChar(str, 0, str.length(), charFilter);
    } else if (codePointFilter != null) {
      return startsWithCodePoint(str, 0, str.length(), codePointFilter)
          || endsWithCodePoint(str, 0, str.length(), codePointFilter);
    } else {
      throw new IllegalStateException("No strip strategy was specified.");
    }
  }
}