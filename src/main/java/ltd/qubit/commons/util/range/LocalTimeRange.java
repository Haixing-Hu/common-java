////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示时间范围（前闭后开区间）。
 *
 * @author 胡海星
 */
public class LocalTimeRange implements Serializable, Assignable<LocalTimeRange>,
    CloneableEx<LocalTimeRange> {

  @Serial
  private static final long serialVersionUID = 2344974464980585572L;

  /**
   * 开始时间（闭区间）.
   */
  @Nullable
  private LocalTime start;

  /**
   * 结束时间（开区间）.
   */
  @Nullable
  private LocalTime end;

  public LocalTimeRange() {}

  public LocalTimeRange(final LocalTime start, final LocalTime end) {
    this.start = start;
    this.end = end;
  }

  public LocalTimeRange(final LocalTimeRange other) {
    assign(other);
  }

  @Override
  public void assign(final LocalTimeRange other) {
    start = other.start;
    end = other.end;
  }

  @Override
  public LocalTimeRange cloneEx() {
    return new LocalTimeRange(this);
  }

  @Nullable
  public final LocalTime getStart() {
    return start;
  }

  public final void setStart(@Nullable final LocalTime start) {
    this.start = start;
  }

  @Nullable
  public final LocalTime getEnd() {
    return end;
  }

  public final void setEnd(@Nullable final LocalTime end) {
    this.end = end;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LocalTimeRange other = (LocalTimeRange) o;
    return Equality.equals(start, other.start)
            && Equality.equals(end, other.end);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, start);
    result = Hash.combine(result, multiplier, end);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("start", start)
            .append("end", end)
            .toString();
  }

  @Computed({"start", "end"})
  public boolean isEmpty() {
    return (start == null) && (end == null);
  }
}