////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此对象表示令牌。
 *
 * @author 胡海星
 */
public class Token implements Serializable, Emptyful, Normalizable, Assignable<Token> {

  private static final long serialVersionUID = 9085346249421191848L;

  /**
   * 令牌的值。
   */
  @Unique
  @Size(min = 1, max = 128)
  private String value;

  /**
   * 令牌创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 令牌的最大生存时间，单位为秒。
   *
   * <p>{@code null}表示无限制。</p>
   */
  @Nullable
  private Long maxAge;

  public Token() {
    // empty
  }

  public Token(final String value) {
    this.value = value;
  }

  public Token(final Token other) {
    assign(other);
  }

  @Override
  public void assign(final Token other) {
    requireNonNull("other", other);
    value = other.value;
    createTime = other.createTime;
    maxAge = other.maxAge;
  }

  @Override
  public Token cloneEx() {
    return new Token(this);
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public Long getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(@Nullable final Long maxAge) {
    this.maxAge = maxAge;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Token other = (Token) o;
    return Equality.equals(value, other.value)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(maxAge, other.maxAge);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, maxAge);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("value", value)
        .append("createTime", createTime)
        .append("maxAge", maxAge)
        .toString();
  }

  /**
   * 判定此令牌是否过期。
   *
   * <p>若次令牌的生存时间({@code maxAge}属性)为{@code null}则表示永不过期。</p>
   *
   * @param now
   *     当前时间戳。
   * @return
   *     此令牌相对指定的截止时间是否过期。
   */
  @Computed("expiredTime")
  public boolean isExpired(final Instant now) {
    requireNonNull("now", now);
    if (maxAge == null) {
      return false;     // null means never expired
    } else {
      final Instant expiredTime = createTime.plusSeconds(maxAge);
      return (expiredTime.compareTo(now) <= 0);
    }
  }
}
