////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * A CharFilter that accept only the characters in the specified string.
 *
 * @author Haixing Hu
 */
public final class InStringCodePointFilter implements CodePointFilter {

  private CharSequence acceptCodePoints;
  private IntList acceptCodePointList;

  public InStringCodePointFilter(final CharSequence acceptCodePoints) {
    this.acceptCodePoints = requireNonNull("acceptCodePoints", acceptCodePoints);
    this.acceptCodePointList = splitCodePoints(acceptCodePoints);
  }

  public CharSequence getAcceptCodePoints() {
    return acceptCodePoints;
  }

  public void setAcceptCodePoints(final CharSequence acceptCodePoints) {
    this.acceptCodePoints = requireNonNull("acceptCodePoints", acceptCodePoints);
    this.acceptCodePointList = splitCodePoints(acceptCodePoints);
  }

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && acceptCodePointList.contains(codePoint);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InStringCodePointFilter other = (InStringCodePointFilter) o;
    return Equality.equals(acceptCodePoints, other.acceptCodePoints)
        && Equality.equals(acceptCodePointList, other.acceptCodePointList);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptCodePoints);
    result = Hash.combine(result, multiplier, acceptCodePointList);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptCodePoints", acceptCodePoints)
        .append("acceptCodePointList", acceptCodePointList)
        .toString();
  }
}
