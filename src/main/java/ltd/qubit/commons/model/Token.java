////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Indexed;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * This object represents a token.
 *
 * @author Haixing Hu
 */
public class Token implements Serializable, Emptyful, Normalizable, Assignable<Token> {

  @Serial
  private static final long serialVersionUID = 9085346249421191848L;

  /**
   * The current value of the token.
   */
  @Size(min = 1, max = 128)
  @Unique
  private String value;

  /**
   * Token creation time.
   */
  @Precision(TimeUnit.SECONDS)
  @Indexed
  private Instant createTime;

  /**
   * The maximum lifetime of the token, in seconds.
   *
   * <p>A {@code null} value means unlimited.</p>
   */
  @Indexed
  @Nullable
  private Long maxAge;

  /**
   * The value of the last expired token.
   */
  @Size(min = 1, max = 128)
  @Unique
  private String previousValue;

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
    previousValue = other.previousValue;
  }

  @Override
  public Token clone() {
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

  public String getPreviousValue() {
    return previousValue;
  }

  public void setPreviousValue(final String previousValue) {
    this.previousValue = previousValue;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Token other = (Token) o;
    return Equality.equals(value, other.value)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(maxAge, other.maxAge)
        && Equality.equals(previousValue, other.previousValue);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, maxAge);
    result = Hash.combine(result, multiplier, previousValue);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("value", value)
        .append("createTime", createTime)
        .append("maxAge", maxAge)
        .append("previousValue", previousValue)
        .toString();
  }

  /**
   * Tests whether this token has expired.
   *
   * <p>If the token&#39;s lifetime ({@code maxAge} attribute) is {@code null},
   * it means it will never expire.</p>
   *
   * @param now
   *     The current timestamp.
   * @return
   *     Whether this token expires relative to the current timestamp.
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
