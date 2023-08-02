////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示整数范围（前闭后开区间）。
 *
 * @author 胡海星
 */
public class IntRange {

  /**
   * 开始（闭区间）。
   */
  @Nullable
  private Integer start;

  /**
   * 结束（开区间）。
   */
  @Nullable
  private Integer end;

  /**
   * 创建一个整数范围。
   *
   * @param start
   *     起始整数，可以为{@code null}.
   * @param end
   *     结束整数，可以为{@code null}.
   * @return 若{@code start}和{@code end}不全为{@code null}，则返回一个指定起始和结束
   *     的整数范围，否则返回{@code null}.
   */
  public static IntRange create(@Nullable final Integer start, @Nullable final Integer end) {
    if (start == null && end == null) {
      return null;
    } else {
      return new IntRange(start, end);
    }
  }

  public IntRange() {}

  public IntRange(@Nullable final Integer start, @Nullable final Integer end) {
    this.start = start;
    this.end = end;
  }

  @Nullable
  public final Integer getStart() {
    return start;
  }

  public final void setStart(@Nullable final Integer start) {
    this.start = start;
  }

  @Nullable
  public final Integer getEnd() {
    return end;
  }

  public final void setEnd(@Nullable final Integer end) {
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
    final IntRange other = (IntRange) o;
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
