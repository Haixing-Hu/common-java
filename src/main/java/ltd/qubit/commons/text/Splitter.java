////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

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
import ltd.qubit.commons.util.filter.codepoint.BlankCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.CodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.InStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInArrayCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.NotInStringCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectAllCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.RejectSpecifiedCodePointFilter;
import ltd.qubit.commons.util.filter.codepoint.WhitespaceCodePointFilter;

import static ltd.qubit.commons.lang.StringUtils.nullToEmpty;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitByChar;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitByCharType;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitByCodePoint;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitBySubstring;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitEachChar;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitEachCodePoint;
import static ltd.qubit.commons.text.impl.SplitterImpl.splitLines;

/**
 * 用于分割字符串的类。
 *
 * <p>此类提供了灵活的字符串分割功能，支持按字符、Unicode 代码点、子字符串、字符类型、
 * 换行符等多种方式分割字符串，并可配置是否剥离空白、忽略空字符串、忽略大小写等选项。</p>
 *
 * <p>使用示例：</p>
 * <pre><code>
 * // 按单个字符分割
 * List&lt;String&gt; result = new Splitter().byChar(',').split("a,b,c");
 * // 结果: ["a", "b", "c"]
 *
 * // 按字符数组分割，剥离空白并忽略空字符串
 * List&lt;String&gt; result = new Splitter()
 *     .byCharsIn(',', ';', ' ')
 *     .strip(true)
 *     .ignoreEmpty(true)
 *     .split("a, b ; c   ; ; d");
 * // 结果: ["a", "b", "c", "d"]
 *
 * // 按字符序列分割
 * List&lt;String&gt; result = new Splitter().byCharsIn(",.;").split("a,b.c;d");
 * // 结果: ["a", "b", "c", "d"]
 *
 * // 按条件分割（如按数字分割）
 * List&lt;String&gt; result = new Splitter()
 *     .byCharsSatisfy(Character::isDigit)
 *     .split("abc123def456ghi");
 * // 结果: ["abc", "", "", "def", "", "", "ghi"]
 *
 * // 按 Unicode 代码点分割（表情符号）
 * List&lt;String&gt; result = new Splitter()
 *     .byCodePoint(0x1F600) // 😀
 *     .split("Hello😀World😀Test");
 * // 结果: ["Hello", "World", "Test"]
 *
 * // 按子字符串分割
 * List&lt;String&gt; result = new Splitter()
 *     .bySubstring("and")
 *     .split("cats and dogs and birds");
 * // 结果: ["cats ", " dogs ", " birds"]
 *
 * // 按子字符串分割（忽略大小写）
 * List&lt;String&gt; result = new Splitter()
 *     .bySubstring("AND")
 *     .ignoreCase(true)
 *     .split("cats and dogs AND birds");
 * // 结果: ["cats ", " dogs ", " birds"]
 *
 * // 按空白字符分割
 * List&lt;String&gt; result = new Splitter().byWhitespaces().split("a  b\tc\nd");
 * // 结果: ["a", "b", "c", "d"]
 *
 * // 按空白字符分割（包括不可打印字符）
 * List&lt;String&gt; result = new Splitter().byBlanks().split("a\u007Fb\u007F c");
 * // 结果: ["a", "b", "c"]
 *
 * // 按字符类型分割
 * List&lt;String&gt; result = new Splitter().byCharTypes().split("abc123def");
 * // 结果: ["abc", "123", "def"]
 *
 * // 按字符类型分割（驼峰命名法）
 * List&lt;String&gt; result = new Splitter()
 *     .byCharTypes()
 *     .camelCase(true)
 *     .split("fooBarBaz");
 * // 结果: ["foo", "Bar", "Baz"]
 *
 * // 按换行符分割
 * List&lt;String&gt; result = new Splitter().toLines().split("line1\nline2\r\nline3");
 * // 结果: ["line1", "line2", "line3"]
 *
 * // 分割为单个字符
 * List&lt;String&gt; result = new Splitter().toChars().split("abc");
 * // 结果: ["a", "b", "c"]
 *
 * // 分割为 Unicode 代码点
 * List&lt;String&gt; result = new Splitter().toCodePoints().split("a😀b");
 * // 结果: ["a", "😀", "b"]
 *
 * // 复杂示例：CSV 解析
 * List&lt;String&gt; result = new Splitter()
 *     .byChar(',')
 *     .strip(true)           // 剥离每个字段的空白
 *     .ignoreEmpty(false)    // 保留空字段
 *     .split("name, age, , city");
 * // 结果: ["name", "age", "", "city"]
 *
 * // 复杂示例：路径分割
 * List&lt;String&gt; result = new Splitter()
 *     .byCharsIn('/', '\\')  // 支持不同操作系统的路径分隔符
 *     .ignoreEmpty(true)     // 忽略连续分隔符产生的空字符串
 *     .split("/home//user/documents/");
 * // 结果: ["home", "user", "documents"]
 *
 * // 复杂示例：标签解析
 * List&lt;String&gt; result = new Splitter()
 *     .byCharsIn(',', ';', '|')
 *     .strip(true)
 *     .ignoreEmpty(true)
 *     .split("tag1, tag2; tag3 | tag4 ;;; tag5");
 * // 结果: ["tag1", "tag2", "tag3", "tag4", "tag5"]
 *
 * // 使用现有列表追加结果
 * List&lt;String&gt; existingList = new ArrayList&lt;&gt;();
 * existingList.add("prefix");
 * List&lt;String&gt; result = new Splitter()
 *     .byChar(',')
 *     .split("a,b,c", existingList);
 * // 结果: ["prefix", "a", "b", "c"]
 *
 * // 处理 null 输入
 * List&lt;String&gt; result = new Splitter().byChar(',').split(null);
 * // 结果: []
 *
 * // 处理空字符串
 * List&lt;String&gt; result = new Splitter().byChar(',').split("");
 * // 结果: [""]
 *
 * List&lt;String&gt; result = new Splitter()
 *     .byChar(',')
 *     .ignoreEmpty(true)
 *     .split("");
 * // 结果: []
 *
 * // 链式配置示例
 * List&lt;String&gt; result = new Splitter()
 *     .byCharsIn(" \t\n")    // 按空白字符分割
 *     .strip(true)           // 剥离结果
 *     .ignoreEmpty(true)     // 忽略空字符串
 *     .split("  hello   world  \n  test  ");
 * // 结果: ["hello", "world", "test"]
 * </code></pre>
 *
 * @author 胡海星
 */
public class Splitter {

  private CharFilter byCharFilter;

  private CodePointFilter byCodePointFilter;

  private CharSequence bySubstring;

  private boolean toLines;

  private boolean byWhitespace;

  private boolean byBlanks;

  private boolean byCharType;

  private boolean toChars;

  private boolean toCodePoints;

  private boolean strip;

  private boolean ignoreEmpty;

  private boolean ignoreCase;

  private boolean camelCase;

  public Splitter() {}

  private void clearStrategies() {
    this.byCharFilter = null;
    this.byCodePointFilter = null;
    this.bySubstring = null;
    this.toLines = false;
    this.byWhitespace = false;
    this.byBlanks = false;
    this.byCharType = false;
    this.toChars = false;
    this.toCodePoints = false;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定字符作为分隔符。
   *
   * <p>示例：
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar('.').split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar('.').split("a..b.c")  = {"a", "", "b", "c"}
   * new Splitter().byChar('.').ignoreEmpty(true).split("a..b.c")  = {"a", "b", "c"}
   * new Splitter().byChar('.').split("a. .b.c")  = {"a", " ", "b", "c"}
   * new Splitter().byChar('.').strip(true).split("a. .b.c")  = {"a", "", "b", "c"}
   * new Splitter().byChar('.').strip(true).ignoreEmpty(true).split("a. .b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(' ').split("a  b c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(' ').ignoreEmpty(true).split("a  b c")  = {"a", "b", "c"}
   * </pre>
   *
   * @param separator
   *     指定的分隔符字符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byChar(final char separator) {
    this.clearStrategies();
    this.byCharFilter = new AcceptSpecifiedCharFilter(separator);
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以除指定字符外的所有字符作为分隔符。
   *
   * @param separator
   *     指定字符。除此字符外的所有字符都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharNotEqual(final char separator) {
    this.clearStrategies();
    this.byCharFilter = new RejectSpecifiedCharFilter(separator);
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定数组中的字符作为分隔符。
   *
   * <p>示例：
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     分隔符字符数组。{@code null} 值或空数组表示不使用任何字符作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharsIn(@Nullable final char[] chars) {
    this.clearStrategies();
    if (chars == null || chars.length == 0) {
      this.byCharFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new InArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定序列中的字符作为分隔符。
   *
   * <p>示例：
   * <pre>
   *
   *
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     分隔符字符序列。{@code null} 值或空序列表示不使用任何字符作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharsIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if (chars == null || chars.length() == 0) {
      this.byCharFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new InStringCharFilter(chars);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以不在指定数组中的字符作为分隔符。
   *
   * <p>示例：
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     指定的字符数组。字符串将以不在此数组中的字符进行分割。
   *     {@code null} 值或空数组表示任何字符都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharsNotIn(@Nullable final char[] chars) {
    this.clearStrategies();
    if (chars == null || chars.length == 0) {
      this.byCharFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new NotInArrayCharFilter(chars);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以不在指定序列中的字符作为分隔符。
   *
   * <p>示例：
   * <pre>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(['.']).split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(['.',':']).ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(['.',':']).strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar([' ',',']).ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </pre>
   *
   * @param chars
   *     指定的字符序列。字符串将以不在此序列中的字符进行分割。
   *     {@code null} 值或空序列表示任何字符都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharsNotIn(@Nullable final CharSequence chars) {
    this.clearStrategies();
    if (chars == null || chars.length() == 0) {
      this.byCharFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = new NotInStringCharFilter(chars);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以满足指定过滤器的字符作为分隔符。
   *
   * @param filter
   *     指定的过滤器，接受用作分隔符的字符。{@code null} 值表示不使用任何字符作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharsSatisfy(final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCharFilter = RejectAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = filter;
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以不满足指定过滤器的字符作为分隔符。
   *
   * @param filter
   *     指定的过滤器，拒绝用作分隔符的字符。{@code null} 值表示任何字符都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCharsNotSatisfy(final CharFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCharFilter = AcceptAllCharFilter.INSTANCE;
    } else {
      this.byCharFilter = CharFilter.not(filter);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定的 Unicode 代码点作为分隔符。
   *
   * <p>示例：
   * <pre><code>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(".").split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar(".,:").ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </code></pre>
   *
   * <p>注意，如果指定的字符序列包含BMP外的Unicode字符，分割器将根据Unicode代码点而不是代码单元来分割字符串。例如：</p>
   * <pre><code>
   * final String separators = ",:&#92;uD83D&#92;uDD6E.";
   * final Splitter splitter = new Splitter().onCodePointIn(separators);
   *
   * splitter.split(",a. &#92;uD83D&#92;uDD6Eb&#92;uD83D&#92;uDD6Ec&#92;uD83D&#92;uDD6E")
   *    = {"", "a", " ", "b", "c", ""}
   * splitter.split("&#92;uD83D&#92;uDD6Ea&#92;uD83D: &#92;uD83D&#92;uDD6Eb.c&#92;uDD6E, ")
   *    = {"", "a&#92;uD83D", " ", "b", "c&#92;uDD6E", " "}
   * </code></pre>
   *
   * @param codePoint
   *     用作分隔符的指定 Unicode 代码点。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePoint(final int codePoint) {
    this.clearStrategies();
    this.byCodePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定的 Unicode 代码点作为分隔符。
   *
   * <p>示例：
   * <pre><code>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(".").split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar(".,:").ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </code></pre>
   *
   * <p>注意，如果指定的字符序列包含BMP外的Unicode字符，分割器将根据Unicode代码点而不是代码单元来分割字符串。例如：</p>
   * <pre><code>
   * final String separators = ",:&#92;uD83D&#92;uDD6E.";
   * final Splitter splitter = new Splitter().onCodePointIn(separators);
   *
   * splitter.split(",a. &#92;uD83D&#92;uDD6Eb&#92;uD83D&#92;uDD6Ec&#92;uD83D&#92;uDD6E")
   *    = {"", "a", " ", "b", "c", ""}
   * splitter.split("&#92;uD83D&#92;uDD6Ea&#92;uD83D: &#92;uD83D&#92;uDD6Eb.c&#92;uDD6E, ")
   *    = {"", "a&#92;uD83D", " ", "b", "c&#92;uDD6E", " "}
   * </code></pre>
   *
   * @param codePoint
   *     包含用作分隔符的 Unicode 字符的字符序列。{@code null} 值或空值表示不使用任何 Unicode 代码点作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePoint(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new AcceptSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以除指定代码点外的所有 Unicode 代码点作为分隔符。
   *
   * @param codePoint
   *     指定的 Unicode 代码点。除此代码点外的所有 Unicode 代码点都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointNotEqual(final int codePoint) {
    this.clearStrategies();
    this.byCodePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以除指定代码点外的所有 Unicode 代码点作为分隔符。
   *
   * @param codePoint
   *     包含指定 Unicode 代码点的字符序列。除指定代码点外的所有 Unicode 代码点都将用作分隔符。
   *     {@code null} 值或空值表示所有 Unicode 代码点都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointNotEqual(@Nullable final CharSequence codePoint) {
    this.clearStrategies();
    if (codePoint == null || codePoint.length() == 0) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new RejectSpecifiedCodePointFilter(codePoint);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定数组中的 Unicode 代码点作为分隔符。
   *
   * @param codePoints
   *     包含用作分隔符的 Unicode 代码点的指定数组。{@code null} 值或空值表示不使用任何 Unicode 代码点作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointsIn(@Nullable final int[] codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length == 0) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new InArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定字符序列中的 Unicode 代码点作为分隔符。
   *
   * <p>示例：
   * <pre><code>
   * new Splitter().byChar(*).split(null)       = {}
   * new Splitter().byChar(*).split("")         = {""}
   * new Splitter().byChar(*).ignoreEmpty(true).split("") = {}
   * new Splitter().byChar(".").split("a.b.c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").split("a.:b:c")  = {"a", "", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a.:b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").strip(true).ignoreEmpty(true).split("a. :b:c")  = {"a", "b", "c"}
   * new Splitter().byChar(".,:").ignoreEmpty(true).split("a  b c,d")  = {"a", "b", "c", "d"}
   * new Splitter().byChar(".,:").ignoreEmpty(false).split("a  b c")  = {"a", "", "b", "c", "d"}
   * </code></pre>
   *
   * <p>注意，如果指定的字符序列包含BMP外的Unicode字符，分割器将根据Unicode代码点而不是代码单元来分割字符串。例如：</p>
   * <pre><code>
   * final String separators = ",:&#92;uD83D&#92;uDD6E.";
   * final Splitter splitter = new Splitter().onCodePointIn(separators);
   *
   * splitter.split(",a. &#92;uD83D&#92;uDD6Eb&#92;uD83D&#92;uDD6Ec&#92;uD83D&#92;uDD6E")
   *    = {"", "a", " ", "b", "c", ""}
   * splitter.split("&#92;uD83D&#92;uDD6Ea&#92;uD83D: &#92;uD83D&#92;uDD6Eb.c&#92;uDD6E, ")
   *    = {"", "a&#92;uD83D", " ", "b", "c&#92;uDD6E", " "}
   * </code></pre>
   *
   * @param codePoints
   *     包含用作分隔符的 Unicode 代码点的指定字符序列。{@code null} 值或空值表示不使用任何 Unicode 代码点作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointsIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length() == 0) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new InStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以不在指定数组中的 Unicode 代码点作为分隔符。
   *
   * @param codePoints
   *     指定的 Unicode 代码点数组。字符串将以不在此数组中的 Unicode 代码点进行分割。
   *     {@code null} 值或空值表示所有 Unicode 代码点都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointsNotIn(@Nullable final int[] codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length == 0) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new NotInArrayCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以不在指定序列中的 Unicode 代码点作为分隔符。
   *
   * @param codePoints
   *     指定的 Unicode 代码点序列。字符串将以不在此序列中的 Unicode 代码点进行分割。
   *     {@code null} 值或空值表示所有 Unicode 代码点都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointsNotIn(@Nullable final CharSequence codePoints) {
    this.clearStrategies();
    if (codePoints == null || codePoints.length() == 0) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = new NotInStringCodePointFilter(codePoints);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以满足指定过滤器的 Unicode 代码点作为分隔符。
   *
   * @param filter
   *     指定的过滤器，接受用作分隔符的 Unicode 代码点。{@code null} 值表示不使用任何 Unicode 代码点作为分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointsSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCodePointFilter = RejectAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = filter;
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以不满足指定过滤器的 Unicode 代码点作为分隔符。
   *
   * @param filter
   *     指定的过滤器，拒绝用作分隔符的 Unicode 代码点。{@code null} 值表示所有 Unicode 代码点都将用作分隔符。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter byCodePointsNotSatisfy(@Nullable final CodePointFilter filter) {
    this.clearStrategies();
    if (filter == null) {
      this.byCodePointFilter = AcceptAllCodePointFilter.INSTANCE;
    } else {
      this.byCodePointFilter = CodePointFilter.not(filter);
    }
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以指定的子字符串作为分隔符。
   *
   * @param separator
   *     用作分隔符的指定子字符串。{@code null} 值或空值会导致源字符串被分割，使每个 Unicode 代码点成为单个子字符串。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter bySubstring(@Nullable final CharSequence separator) {
    this.clearStrategies();
    this.bySubstring = nullToEmpty(separator);
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以空白字符作为分隔符。
   *
   * <p>示例：
   * <pre><code>
   * new Splitter().byWhitespaces().split(null)       = null
   * new Splitter().byWhitespaces().split("")         = null
   * new Splitter().byWhitespaces().split("abc def")  = {"abc", "def"}
   * new Splitter().byWhitespaces().split("abc  def") = {"abc", "def"}
   * new Splitter().byWhitespaces().split(" abc ")    = {"abc"}
   * new Splitter().byWhitespaces().split("&#92;f a&#92;tbc ")   = {"a", "bc"}
   * new Splitter().byWhitespaces().split("&#92;f abc ")     = {"abc"}
   * // 以下示例显示了 byWhitespaces() 和 byBlanks() 之间的区别
   * new Splitter().byWhitespaces().split("a&#92;u007Fb&#92;u007F .c") = {"a&#92;u007Fb&#92;u007F", ".c"}
   * new Splitter().byWhitespaces().strip(true).split("a&#92;u007Fb&#92;u007F .c") = {"a&#92;u007Fb", ".c"}
   * </code></pre>
   *
   * @return
   *     此 {@link Splitter} 对象的引用。
   * @see Character#isWhitespace(int)
   */
  public Splitter byWhitespaces() {
    this.clearStrategies();
    this.byWhitespace = true;
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以空白字符作为分隔符。
   *
   * <p>示例：
   * <pre><code>
   * new Splitter().byBlanks().split(null)       = null
   * new Splitter().byBlanks().split("")         = null
   * new Splitter().byBlanks().split("abc def")  = {"abc", "def"}
   * new Splitter().byBlanks().split("abc  def") = {"abc", "def"}
   * new Splitter().byBlanks().split(" abc ")    = {"abc"}
   * new Splitter().byBlanks().split("&#92;f a&#92;tbc ")   = {"a", "bc"}
   * new Splitter().byBlanks().split("&#92;f abc ")     = {"abc"}
   * // 以下示例显示了 byWhitespaces() 和 byBlanks() 之间的区别
   * new Splitter().byBlanks().split("a&#92;u007Fb&#92;u007F .c") = {"a", "b", ".c"}
   * new Splitter().byBlanks().strip(true).split("a&#92;u007Fb&#92;u007F .c") = {"a", "b", ".c"}
   * </code></pre>
   *
   * @return
   *     此 {@link Splitter} 对象的引用。
   * @see CharUtils#isBlank(int)
   */
  public Splitter byBlanks() {
    this.clearStrategies();
    this.byBlanks = true;
    return this;
  }

  /**
   * 根据 {@code java.lang.Character.getType(char)} 返回的字符类型分割字符串。
   *
   * <p>相同类型的连续字符组作为完整的标记返回，但有以下例外：
   *
   * <p>如果 {@code camelCase} 设置为 {@code true}，任何紧邻 {@code Character.LOWERCASE_LETTER} 类型标记前的
   * {@code Character.UPPERCASE_LETTER} 类型字符（如果有）将属于后续标记，而不是属于前面的
   * {@code Character.UPPERCASE_LETTER} 标记（如果有）。
   *
   * <p>示例：
   * <pre>
   * new Splitter().byCharTypes().split(null)                 = {}
   * new Splitter().byCharTypes().split("")                   = {""}
   * new Splitter().byCharTypes().ignoreEmpty(true).split("") = {}
   * new Splitter().byCharTypes().split("ab   de fg")         = {"ab", "   ", "de", " ", "fg"}
   * new Splitter().byCharTypes().strip(true).split("ab   de fg") = {"ab", "", "de", "", "fg"}
   * new Splitter().byCharTypes().strip(true).ignoreEmpty(true).split("ab   de fg") = {"ab", "de", "fg"}
   * new Splitter().byCharTypes().split("ab:cd:ef")       = {"ab", ":", "cd", ":", "ef"}
   * new Splitter().byCharTypes().split("number5")        = {"number", "5"}
   * new Splitter().byCharTypes().split("fooBar") = {"foo", "B", "ar"}
   * new Splitter().byCharTypes().camelCase(true).split("fooBar")  = {"foo", "Bar"}
   * new Splitter().byCharTypes().split("foo200Bar") = {"foo", "200", "B", "ar"}
   * new Splitter().byCharTypes().camelCase(true).split("foo200Bar")  = {"foo", "200", "Bar"}
   * new Splitter().byCharTypes().camelCase(false).split("ASFRules")  = {"ASFR", "ules"}
   * new Splitter().byCharTypes().camelCase(true).split("ASFRules")   = {"ASF", "Rules"}
   * </pre>
   *
   * @return
   *     此 {@link Splitter} 对象的引用。
   * @see #camelCase(boolean)
   */
  public Splitter byCharTypes() {
    this.clearStrategies();
    this.byCharType = true;
    return this;
  }

  /**
   * 将提供的字符串分割为子字符串列表，以换行符作为分隔符。
   *
   * <p>换行符可能是 "\r"、"\r\n" 或 "\n"。</p>
   *
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter toLines() {
    this.clearStrategies();
    this.toLines = true;
    return this;
  }

  /**
   * 将字符串中的每个字符分割为子字符串。
   *
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter toChars() {
    this.clearStrategies();
    this.toChars = true;
    return this;
  }

  /**
   * 将字符串中的每个 Unicode 代码点分割为子字符串。
   *
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter toCodePoints() {
    this.clearStrategies();
    this.toCodePoints = true;
    return this;
  }

  /**
   * 设置是否剥离分割后的子字符串。
   *
   * <p>此选项的默认值设置为 {@code false}。</p>
   *
   * @param strip
   *     是否剥离分割后的子字符串。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter strip(final boolean strip) {
    this.strip = strip;
    return this;
  }

  /**
   * 设置是否在分割结果列表中忽略空子字符串。
   *
   * <p>此选项的默认值设置为 {@code false}。</p>
   *
   * @param ignoreEmpty
   *     是否在分割结果列表中忽略空子字符串。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter ignoreEmpty(final boolean ignoreEmpty) {
    this.ignoreEmpty = ignoreEmpty;
    return this;
  }

  /**
   * 设置在比较子字符串时是否忽略大小写。
   *
   * <p>此选项的默认值设置为 {@code false}。</p>
   *
   * @param ignoreCase
   *     在比较子字符串时是否忽略大小写。
   * @return
   *     此 {@link Splitter} 对象的引用。
   */
  public Splitter ignoreCase(final boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    return this;
  }

  /**
   * 设置在按字符类型分割字符串时是否使用驼峰命名法策略。
   *
   * <p>此选项的默认值设置为 {@code false}。</p>
   *
   * @param camelCase
   *     在按字符类型分割字符串时是否使用驼峰命名法策略。
   * @return
   *     此 {@link Splitter} 对象的引用。
   * @see #byCharTypes()
   */
  public Splitter camelCase(final boolean camelCase) {
    this.camelCase = camelCase;
    return this;
  }

  /**
   * 根据此 {@link Splitter} 对象的先前设置，将指定的字符串分割为子字符串。
   *
   * @param str
   *     要分割的指定字符串。如果为 {@code null}，返回空列表。
   * @return
   *     分割后的子字符串列表，不能为 {@code null}，但可能是空列表。
   */
  @NotNull
  public List<String> split(@Nullable final CharSequence str) {
    return split(str, new ArrayList<>());
  }

  /**
   * 根据此 {@link Splitter} 对象的先前设置，将指定的字符串分割为子字符串。
   *
   * @param str
   *     要分割的指定字符串。如果为 {@code null}，返回空列表。
   * @param result
   *     可选的列表，用于<b>追加</b>分割结果。<b>注意，此列表的旧内容不会被清除。</b>
   *     如果为 {@code null}，将创建并返回新列表。
   * @return
   *     分割后的子字符串列表，不能为 {@code null}，但可能是空列表。
   */
  @NotNull
  public List<String> split(@Nullable final CharSequence str,
      @Nullable final List<String> result) {
    final List<String> output = (result == null ? new ArrayList<>() : result);
    if (byCharFilter != null) {
      return splitByChar(str, byCharFilter, strip, ignoreEmpty, output);
    } else if (byCodePointFilter != null) {
      return splitByCodePoint(str, byCodePointFilter, strip, ignoreEmpty, output);
    } else if (bySubstring != null) {
      return splitBySubstring(str, bySubstring, strip, ignoreEmpty, ignoreCase, output);
    } else if (toLines) {
      return splitLines(str, strip, ignoreEmpty, output);
    } else if (byWhitespace) {
      return splitByCodePoint(str, WhitespaceCodePointFilter.INSTANCE, strip, true, output);
    } else if (byBlanks) {
      return splitByCodePoint(str, BlankCodePointFilter.INSTANCE, strip, true, output);
    } else if (byCharType) {
      return splitByCharType(str, strip, ignoreEmpty, camelCase, output);
    } else if (toChars) {
      return splitEachChar(str, strip, ignoreEmpty, result);
    } else if (toCodePoints) {
      return splitEachCodePoint(str, strip, ignoreEmpty, result);
    } else {
      throw new IllegalStateException("No split strategy was specified.");
    }
  }
}