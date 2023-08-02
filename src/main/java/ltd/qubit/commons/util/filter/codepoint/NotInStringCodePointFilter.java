////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * A CharFilter that accept only the characters NOT in the specified string.
 *
 * @author Haixing Hu
 */
public final class NotInStringCodePointFilter implements CodePointFilter {

  private CharSequence rejectCodePoints;
  private IntList rejectCodePointList;

  public NotInStringCodePointFilter(final CharSequence rejectCodePoints) {
    this.rejectCodePoints = requireNonNull("acceptCodePoints", rejectCodePoints);
    this.rejectCodePointList = splitCodePoints(rejectCodePoints);
  }

  public CharSequence getRejectCodePoints() {
    return rejectCodePoints;
  }

  public void setRejectCodePoints(final CharSequence rejectCodePoints) {
    this.rejectCodePoints = requireNonNull("acceptCodePoints", rejectCodePoints);
    this.rejectCodePointList = splitCodePoints(rejectCodePoints);
  }

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
