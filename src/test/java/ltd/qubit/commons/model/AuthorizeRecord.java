////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示授权记录。
 *
 * @author 胡海星
 */
public class AuthorizeRecord implements Assignable<AuthorizeRecord> {

  /**
   * 连续授权失败次数。
   */
  private Integer failures = 0;

  /**
   * 上次授权时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant time;

  public AuthorizeRecord() {
    // empty
  }

  public AuthorizeRecord(@Nullable final Integer failures,
      @Nullable final Instant time) {
    this.failures = failures;
    this.time = time;
  }

  public AuthorizeRecord(final AuthorizeRecord other) {
    assign(other);
  }

  @Override
  public void assign(final AuthorizeRecord other) {
    Argument.requireNonNull("other", other);
    failures = other.failures;
    time = other.time;
  }

  @Override
  public AuthorizeRecord cloneEx() {
    return new AuthorizeRecord(this);
  }

  public Integer getFailures() {
    return failures;
  }

  public void setFailures(final Integer failures) {
    this.failures = failures;
  }

  @Nullable
  public Instant getTime() {
    return time;
  }

  public void setTime(@Nullable final Instant time) {
    this.time = time;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final AuthorizeRecord other = (AuthorizeRecord) o;
    return Equality.equals(failures, other.failures) && Equality.equals(time,
        other.time);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, failures);
    result = Hash.combine(result, multiplier, time);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("failures", failures)
        .append("time", time)
        .toString();
  }

  /**
   * 刷新连续授权失败次数。
   *
   * @param expiredSeconds
   *     连续授权失败次数过期时间，单位为秒；即经过这么长时间后，连续授权失败次数应重置为0。
   * @param now
   *     当前时间戳。
   */
  public void refreshFailures(final long expiredSeconds, final Instant now) {
    if (time == null) {
      failures = 0;
    } else {
      final long expired = expiredSeconds * 1000;
      if (time.toEpochMilli() + expired <= now.toEpochMilli()) {
        failures = 0;
      }
    }
  }

  /**
   * 增加一次连续授权失败次数。
   */
  public void increaseFailures() {
    if (failures == null) {
      failures = 0;
    }
    failures = failures + 1;
  }
}
