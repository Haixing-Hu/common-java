////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/**
 * 此模型表示日期时间范围（前闭后开区间）。
 *
 * @author Haixing Hu
 */
public class LocalDateTimeRange implements Serializable {

  private static final long serialVersionUID = -2944980473126496537L;

  /**
   * 开始日期时间（闭区间）。
   */
  @Nullable
  private LocalDateTime start;

  /**
   * 结束日期时间（开区间）。
   */
  @Nullable
  private LocalDateTime end;

  /**
   * 创建一个日期时间范围。
   *
   * @param start
   *     起始日期时间，可以为{@code null}.
   * @param end
   *     结束日期时间，可以为{@code null}.
   * @return 若{@code start}和{@code end}不全为{@code null}，则返回一个指定起始和结束
   *     的日期时间范围，否则返回{@code null}.
   */
  public static LocalDateTimeRange create(@Nullable final LocalDateTime start,
      @Nullable final LocalDateTime end) {
    if (start == null && end == null) {
      return null;
    } else {
      return new LocalDateTimeRange(start, end);
    }
  }

  public LocalDateTimeRange() {
  }

  public LocalDateTimeRange(@Nullable final LocalDateTime start,
      @Nullable final LocalDateTime end) {
    this.start = start;
    this.end = end;
  }

  @Nullable
  public final LocalDateTime getStart() {
    return start;
  }

  public final void setStart(@Nullable final LocalDateTime start) {
    this.start = start;
  }

  @Nullable
  public final LocalDateTime getEnd() {
    return end;
  }

  public final void setEnd(@Nullable final LocalDateTime end) {
    this.end = end;
  }

  public final void truncatedToSeconds() {
    start = (start == null ? null : start.truncatedTo(ChronoUnit.SECONDS));
    end = (end == null ? null : end.truncatedTo(ChronoUnit.SECONDS));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final LocalDateTimeRange other = (LocalDateTimeRange) o;
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
}
