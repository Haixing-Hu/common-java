////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示金钱范围（前闭后开区间）。
 *
 * @author 胡海星
 */
public class MoneyRange {

  /**
   * 开始日期时间（闭区间）。
   */
  @Money
  @Nullable
  private BigDecimal start;

  /**
   * 结束日期时间（开区间）。
   */
  @Money
  @Nullable
  private BigDecimal end;

  /**
   * 创建一个时间戳范围。
   *
   * @param start
   *     起始时间戳，可以为{@code null}.
   * @param end
   *     结束时间戳，可以为{@code null}.
   * @return 若{@code start}和{@code end}不全为{@code null}，则返回一个指定起始和结束
   *     的时间戳范围，否则返回{@code null}.
   */
  public static MoneyRange create(@Nullable final BigDecimal start,
      @Nullable final BigDecimal end) {
    if (start == null && end == null) {
      return null;
    } else {
      return new MoneyRange(start, end);
    }
  }

  public MoneyRange() {}

  public MoneyRange(@Nullable final BigDecimal start, @Nullable final BigDecimal end) {
    this.start = start;
    this.end = end;
  }

  @Nullable
  public final BigDecimal getStart() {
    return start;
  }

  public final void setStart(@Nullable final BigDecimal start) {
    this.start = start;
  }

  @Nullable
  public final BigDecimal getEnd() {
    return end;
  }

  public final void setEnd(@Nullable final BigDecimal end) {
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
    final MoneyRange other = (MoneyRange) o;
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
