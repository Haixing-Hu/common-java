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
import java.time.LocalDate;
import java.time.ZoneOffset;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示日期范围（前闭后开区间）。
 *
 * @author 胡海星
 */
public class LocalDateRange implements Serializable, CloneableEx<LocalDateRange>,
        Assignable<LocalDateRange> {

  @Serial
  private static final long serialVersionUID = -3726401531475792968L;

  /**
   * 开始日期（闭区间）.
   */
  @Nullable
  private LocalDate start;

  /**
   * 结束日期（开区间）.
   */
  @Nullable
  private LocalDate end;

  /**
   * 创建一个日期范围。
   *
   * @param start
   *     起始日期，可以为{@code null}.
   * @param end
   *     结束日期，可以为{@code null}.
   * @return 若{@code start}和{@code end}不全为{@code null}，则返回一个指定起始和结束
   *     的日期范围，否则返回{@code null}.
   */
  public static LocalDateRange create(@Nullable final LocalDate start,
      @Nullable final LocalDate end) {
    if (start == null && end == null) {
      return null;
    } else {
      return new LocalDateRange(start, end);
    }
  }

  public LocalDateRange() {}

  public LocalDateRange(@Nullable final LocalDate start) {
    this(start, null);
  }

  public LocalDateRange(@Nullable final LocalDate start,
      @Nullable final LocalDate end) {
    this.start = start;
    this.end = end;
  }

  public LocalDateRange(@Nullable final String start) {
    this(start, null);
  }

  public LocalDateRange(@Nullable final String start,
      @Nullable final String end) {
    this((start == null ? null : LocalDate.parse(start)),
         (end == null ? null : LocalDate.parse(end)));
  }

  public LocalDateRange(final LocalDateRange other) {
    assign(other);
  }

  @Override
  public void assign(final LocalDateRange other) {
    start = other.start;
    end = other.end;
  }

  @Override
  public LocalDateRange cloneEx() {
    return new LocalDateRange(this);
  }

  @Nullable
  public final LocalDate getStart() {
    return start;
  }

  public final void setStart(@Nullable final LocalDate start) {
    this.start = start;
  }

  @Nullable
  public final LocalDate getEnd() {
    return end;
  }

  public final void setEnd(@Nullable final LocalDate end) {
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
    final LocalDateRange other = (LocalDateRange) o;
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
  public InstantRange toInstantRange(final ZoneOffset zoneOffset) {
    final InstantRange result = new InstantRange();
    if (start != null) {
      result.setStart(start.atStartOfDay().toInstant(zoneOffset));
    }
    if (end != null) {
      result.setEnd(end.atStartOfDay().toInstant(zoneOffset));
    }
    return result;
  }

  @Computed({"start", "end"})
  public LocalDateTimeRange toLocalDateTimeRange() {
    final LocalDateTimeRange result = new LocalDateTimeRange();
    if (start != null) {
      result.setStart(start.atStartOfDay());
    }
    if (end != null) {
      result.setEnd(end.atStartOfDay());
    }
    return result;
  }

  @Computed({"start", "end"})
  public boolean isEmpty() {
    return (start == null) && (end == null);
  }
}