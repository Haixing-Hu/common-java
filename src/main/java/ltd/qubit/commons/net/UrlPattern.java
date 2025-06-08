////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.Serializable;

import javax.annotation.Nullable;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.ObjectUtils;
import ltd.qubit.commons.text.Pattern;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link UrlPattern}表示用于匹配URL的模式规则。
 *
 * @author 胡海星
 */
public class UrlPattern implements Serializable, CloneableEx<UrlPattern> {

  private static final long serialVersionUID = 9135054642012865500L;

  /**
   * 默认的URL部分，即整个URL。
   */
  public static final UrlPart DEFAULT_PART = UrlPart.URL;

  static {
    BinarySerialization.register(UrlPattern.class, UrlPatternBinarySerializer.INSTANCE);
  }

  protected UrlPart part;

  protected Pattern pattern;

  /**
   * 构造一个空的URL模式对象。
   */
  public UrlPattern() {
    part = null;
    pattern = new Pattern();
  }

  /**
   * 构造URL模式对象。
   *
   * @param pattern
   *     模式对象
   */
  public UrlPattern(final Pattern pattern) {
    part = null;
    this.pattern = Argument.requireNonNull("pattern", pattern);
  }

  /**
   * 构造URL模式对象。
   *
   * @param part
   *     URL部分
   * @param pattern
   *     模式对象
   */
  public UrlPattern(final UrlPart part, final Pattern pattern) {
    this.part = part;
    this.pattern = Argument.requireNonNull("pattern", pattern);
  }

  /**
   * 获取URL部分。
   *
   * @return URL部分
   */
  public UrlPart getPart() {
    return part;
  }

  /**
   * 设置URL部分。
   *
   * @param part
   *     URL部分
   */
  public void setPart(final UrlPart part) {
    this.part = part;
  }

  /**
   * 获取模式对象。
   *
   * @return 模式对象
   */
  public Pattern getPattern() {
    return pattern;
  }

  /**
   * 设置模式对象。
   *
   * @param pattern
   *     模式对象
   */
  public void setPattern(final Pattern pattern) {
    this.pattern = Argument.requireNonNull("pattern", pattern);
  }

  /**
   * 检查URL是否匹配当前模式。
   *
   * @param url
   *     要检查的URL
   * @return 如果URL匹配当前模式返回true
   */
  public boolean matches(@Nullable final Url url) {
    if (url == null) {
      return false;
    }
    final String str = url.get(ObjectUtils.defaultIfNull(part, DEFAULT_PART));
    return pattern.matches(str);
  }

  /**
   * 克隆当前URL模式对象。
   *
   * @return 克隆的URL模式对象
   */
  @Override
  public UrlPattern cloneEx() {
    return new UrlPattern(part, pattern.cloneEx());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final UrlPattern other = (UrlPattern) o;
    return Equality.equals(part, other.part)
            && Equality.equals(pattern, other.pattern);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, part);
    result = Hash.combine(result, multiplier, pattern);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("part", part)
            .append("pattern", pattern)
            .toString();
  }
}