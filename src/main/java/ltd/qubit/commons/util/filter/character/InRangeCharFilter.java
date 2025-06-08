////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireLessEqual;

/**
 * 一个字符过滤器，只接受指定范围内的字符。
 *
 * @author 胡海星
 */
public class InRangeCharFilter implements CharFilter {

  /**
   * 范围起始（包含）。
   */
  private final char start;
  /**
   * 范围结束（包含）。
   */
  private final char end;

  /**
   * 构造一个 {@link InRangeCharFilter} 对象。
   *
   * @param start
   *     范围起始（包含）。
   * @param end
   *     范围结束（包含）。
   */
  public InRangeCharFilter(final char start, final char end) {
    requireLessEqual("start", start, "end", end);
    this.start = start;
    this.end = end;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (start <= ch) && (ch <= end);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InRangeCharFilter other = (InRangeCharFilter) o;
    return Equality.equals(start, other.start)
        && Equality.equals(end, other.end);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, start);
    result = Hash.combine(result, multiplier, end);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("start", start)
        .append("end", end)
        .toString();
  }
}