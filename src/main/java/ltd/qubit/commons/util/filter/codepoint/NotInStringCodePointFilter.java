////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.splitCodePoints;

/**
 * 一个代码点过滤器，仅接受不在指定字符串中的代码点。
 *
 * @author 胡海星
 */
public final class NotInStringCodePointFilter implements CodePointFilter {

  private CharSequence rejectCodePoints;
  private IntList rejectCodePointList;

  /**
   * 构造一个 {@link NotInStringCodePointFilter}。
   *
   * @param rejectCodePoints
   *     包含被拒绝的代码点的字符串。
   */
  public NotInStringCodePointFilter(final CharSequence rejectCodePoints) {
    this.rejectCodePoints = requireNonNull("rejectCodePoints", rejectCodePoints);
    this.rejectCodePointList = splitCodePoints(rejectCodePoints);
  }

  /**
   * 获取包含被拒绝的代码点的字符串。
   *
   * @return 包含被拒绝的代码点的字符串。
   */
  public CharSequence getRejectCodePoints() {
    return rejectCodePoints;
  }

  /**
   * 设置包含被拒绝的代码点的字符串。
   *
   * @param rejectCodePoints
   *     新的包含被拒绝的代码点的字符串。
   */
  public void setRejectCodePoints(final CharSequence rejectCodePoints) {
    this.rejectCodePoints = requireNonNull("rejectCodePoints", rejectCodePoints);
    this.rejectCodePointList = splitCodePoints(rejectCodePoints);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (!rejectCodePointList.contains(codePoint));
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NotInStringCodePointFilter other = (NotInStringCodePointFilter) o;
    return Equality.equals(rejectCodePoints, other.rejectCodePoints)
        && Equality.equals(rejectCodePointList, other.rejectCodePointList);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, rejectCodePoints);
    result = Hash.combine(result, multiplier, rejectCodePointList);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("rejectCodePoints", rejectCodePoints)
        .append("rejectCodePointList", rejectCodePointList)
        .toString();
  }
}