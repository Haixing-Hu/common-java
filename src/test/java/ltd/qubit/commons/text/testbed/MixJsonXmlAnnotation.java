////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.annotation.Indexed;
import ltd.qubit.commons.annotation.Precision;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class MixJsonXmlAnnotation {

  @Precision(TimeUnit.SECONDS)
  @Nullable
  @Indexed
  private Instant createTime;

  @Nullable
  @Indexed
  private Instant deleteTime;

  public MixJsonXmlAnnotation() {
    // empty
  }

  @Nullable
  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(@Nullable final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final MixJsonXmlAnnotation other = (MixJsonXmlAnnotation) o;
    return Equality.equals(createTime, other.createTime) && Equality.equals(
        deleteTime,
        other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this).append("createTime", createTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
