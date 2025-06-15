////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 一个用于字符串引用和去引用操作的工具类，支持链式调用配置。
 *
 * <p>该类提供了灵活的字符串引用和去引用功能，包括：
 * <ul>
 *   <li>可配置的左右引号字符</li>
 *   <li>可配置的转义字符</li>
 *   <li>是否使用转义功能</li>
 *   <li>链式调用的配置接口</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * // 使用默认双引号和转义字符
 * String quoted = new Quoter().quote("hello");  // "hello"
 *
 * // 使用单引号
 * String quoted = new Quoter().withSingleQuotes().quote("hello");  // 'hello'
 *
 * // 使用自定义引号和转义字符
 * String quoted = new Quoter()
 *     .withQuotes('[', ']')
 *     .withEscape('\\')
 *     .quote("hello");  // [hello]
 *
 * // 去引用
 * String unquoted = new Quoter().unquote("\"hello\"");  // hello
 *
 * // 检查是否被引用
 * boolean isQuoted = new Quoter().isQuoted("\"hello\"");  // true
 * </pre>
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class Quoter {

  public static final char DEFAULT_LEFT_QUOTE = Ascii.DOUBLE_QUOTE;

  public static final char DEFAULT_RIGHT_QUOTE = Ascii.DOUBLE_QUOTE;

  public static final char DEFAULT_ESCAPE_CHAR = Ascii.BACKSLASH;

  public static final boolean DEFAULT_USE_ESCAPE = true;

  private char leftQuote;
  private char rightQuote;
  private char escapeChar;
  private boolean useEscape;

  /**
   * 创建一个新的 Quoter 实例，使用默认配置。
   * 默认配置为：
   * <ul>
   *   <li>左引号：双引号（"）</li>
   *   <li>右引号：双引号（"）</li>
   *   <li>转义字符：反斜杠（\）</li>
   *   <li>使用转义：是</li>
   * </ul>
   */
  public Quoter() {
    this.leftQuote = DEFAULT_LEFT_QUOTE;
    this.rightQuote = DEFAULT_RIGHT_QUOTE;
    this.escapeChar = DEFAULT_ESCAPE_CHAR;
    this.useEscape = DEFAULT_USE_ESCAPE;
  }

  /**
   * 设置左右引号为指定字符。
   *
   * @param leftQuote
   *     左引号字符。
   * @param rightQuote
   *     右引号字符。
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withQuotes(final char leftQuote, final char rightQuote) {
    this.leftQuote = leftQuote;
    this.rightQuote = rightQuote;
    return this;
  }

  /**
   * 设置左右引号为同一个字符。
   *
   * @param quote
   *     引号字符。
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withQuote(final char quote) {
    this.leftQuote = quote;
    this.rightQuote = quote;
    return this;
  }

  /**
   * 设置使用单引号（'）作为左右引号。
   *
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withSingleQuotes() {
    this.leftQuote = Ascii.SINGLE_QUOTE;
    this.rightQuote = Ascii.SINGLE_QUOTE;
    return this;
  }

  /**
   * 设置使用双引号（"）作为左右引号。
   *
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withDoubleQuotes() {
    this.leftQuote = Ascii.DOUBLE_QUOTE;
    this.rightQuote = Ascii.DOUBLE_QUOTE;
    return this;
  }


  /**
   * 设置使用反引号（`）作为左右引号。
   *
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withBackQuotes() {
    this.leftQuote = Ascii.BACK_QUOTE;
    this.rightQuote = Ascii.BACK_QUOTE;
    return this;
  }

  /**
   * 设置并使用转义字符。
   *
   * @param escapeChar
   *     转义字符。
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withEscape(final char escapeChar) {
    this.escapeChar = escapeChar;
    this.useEscape = true;
    return this;
  }

  /**
   * 使用转义字符。
   *
   * @return
   *    当前 Quoter 实例，用于链式调用。
   */
  public Quoter useEscape() {
    this.useEscape = true;
    return this;
  }

  /**
   * 禁用转义功能。
   *
   * @return 当前 Quoter 实例，用于链式调用。
   */
  public Quoter withoutEscape() {
    this.useEscape = false;
    return this;
  }

  /**
   * 检查字符串是否被当前配置的引号包围。
   *
   * @param str
   *     要检查的字符串，可能为 {@code null}。
   * @return 如果字符串被引号包围则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean isQuoted(@Nullable final CharSequence str) {
    if (str == null) {
      return false;
    }
    final int len = str.length();
    if (len < 2) {
      return false;
    }
    final char firstChar = str.charAt(0);
    final char lastChar = str.charAt(len - 1);
    return (firstChar == leftQuote) && (lastChar == rightQuote);
  }

  /**
   * 用当前配置的引号引用字符串。
   *
   * @param str
   *     要引用的字符串，不能为 {@code null}。
   * @return 引用后的字符串。
   * @throws NullPointerException
   *     如果 {@code str} 为 {@code null}。
   */
  public String quote(final CharSequence str) {
    if (str == null) {
      throw new NullPointerException("String cannot be null");
    }
    final StringBuilder result = new StringBuilder();
    quote(str, result);
    return result.toString();
  }

  /**
   * 用当前配置的引号引用字符串，并将结果追加到指定的 StringBuilder。
   *
   * @param str
   *     要引用的字符串，不能为 {@code null}。
   * @param builder
   *     用于追加结果的 StringBuilder，不能为 {@code null}。
   * @throws NullPointerException
   *     如果 {@code str} 或 {@code builder} 为 {@code null}。
   */
  public void quote(final CharSequence str, final StringBuilder builder) {
    if (str == null) {
      throw new NullPointerException("String cannot be null");
    }
    if (builder == null) {
      throw new NullPointerException("StringBuilder cannot be null");
    }
    builder.append(leftQuote);
    if (useEscape) {
      // 转义字符串中的特殊字符
      for (int i = 0; i < str.length(); i++) {
        final char ch = str.charAt(i);
        if (ch == escapeChar || ch == leftQuote || ch == rightQuote) {
          builder.append(escapeChar);
        }
        builder.append(ch);
      }
    } else {
      builder.append(str);
    }
    builder.append(rightQuote);
  }

  /**
   * 去除字符串的引号。
   *
   * @param str
   *     要去引用的字符串，不能为 {@code null}。
   * @return 去引用后的字符串。
   * @throws NullPointerException
   *     如果 {@code str} 为 {@code null}。
   * @throws IllegalArgumentException
   *     如果字符串没有被正确引用。
   */
  public String unquote(final CharSequence str) {
    if (str == null) {
      throw new NullPointerException("String cannot be null");
    }
    final StringBuilder result = new StringBuilder();
    unquote(str, result);
    return result.toString();
  }

  /**
   * 去除字符串的引号，并将结果追加到指定的 StringBuilder。
   *
   * @param str
   *     要去引用的字符串，不能为 {@code null}。
   * @param builder
   *     用于追加结果的 StringBuilder，不能为 {@code null}。
   * @throws NullPointerException
   *     如果 {@code str} 或 {@code builder} 为 {@code null}。
   * @throws IllegalArgumentException
   *     如果字符串没有被正确引用。
   */
  public void unquote(final CharSequence str, final StringBuilder builder) {
    if (str == null) {
      throw new NullPointerException("String cannot be null");
    }
    if (builder == null) {
      throw new NullPointerException("StringBuilder cannot be null");
    }
    final int len = str.length();
    if (len < 2 || str.charAt(0) != leftQuote || str.charAt(len - 1) != rightQuote) {
      throw new IllegalArgumentException("String is not properly quoted: " + str);
    }
    if (useEscape) {
      // 处理转义字符
      for (int i = 1; i < len - 1; i++) {
        final char ch = str.charAt(i);
        if (ch == escapeChar && i + 1 < len - 1) {
          // 跳过转义字符，直接添加下一个字符
          builder.append(str.charAt(++i));
        } else {
          builder.append(ch);
        }
      }
    } else {
      // 直接提取中间部分
      builder.append(str, 1, len - 1);
    }
  }

  /**
   * 如果字符串被引用，则去除引号；否则返回原字符串。
   *
   * @param str
   *     要处理的字符串，可能为 {@code null}。
   * @return 处理后的字符串。如果输入为 {@code null}，则返回 {@code null}。
   */
  public String unquoteIfNecessary(@Nullable final CharSequence str) {
    if (str == null) {
      return null;
    }
    if (isQuoted(str)) {
      return unquote(str);
    } else {
      return str.toString();
    }
  }

  /**
   * 如果字符串被引用，则去除引号并将结果追加到 StringBuilder；否则将原字符串追加到 StringBuilder。
   *
   * @param str
   *     要处理的字符串，可能为 {@code null}。
   * @param builder
   *     用于追加结果的 StringBuilder，不能为 {@code null}。
   * @throws NullPointerException
   *     如果 {@code builder} 为 {@code null}。
   */
  public void unquoteIfNecessary(@Nullable final CharSequence str, final StringBuilder builder) {
    if (builder == null) {
      throw new NullPointerException("StringBuilder cannot be null");
    }
    if (str != null) {
      if (isQuoted(str)) {
        unquote(str, builder);
      } else {
        builder.append(str);
      }
    }
  }

  /**
   * 获取当前配置的左引号字符。
   *
   * @return 左引号字符。
   */
  public char getLeftQuote() {
    return leftQuote;
  }

  /**
   * 获取当前配置的右引号字符。
   *
   * @return 右引号字符。
   */
  public char getRightQuote() {
    return rightQuote;
  }

  /**
   * 获取当前配置的转义字符。
   *
   * @return 转义字符。
   */
  public char getEscapeChar() {
    return escapeChar;
  }

  /**
   * 检查是否启用了转义功能。
   *
   * @return 如果启用了转义功能则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean isUseEscape() {
    return useEscape;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Quoter other = (Quoter) o;
    return Equality.equals(leftQuote, other.leftQuote)
        && Equality.equals(rightQuote, other.rightQuote)
        && Equality.equals(escapeChar, other.escapeChar)
        && Equality.equals(useEscape, other.useEscape);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, leftQuote);
    result = Hash.combine(result, multiplier, rightQuote);
    result = Hash.combine(result, multiplier, escapeChar);
    result = Hash.combine(result, multiplier, useEscape);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append(Quoter::getLeftQuote, leftQuote)
        .append(Quoter::getRightQuote, rightQuote)
        .append(Quoter::getEscapeChar, escapeChar)
        .append(Quoter::isUseEscape, useEscape)
        .toString();
  }
}
