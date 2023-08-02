////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A CharFilter that accept only the characters NOT in the specified code point
 * array.
 *
 * @author Haixing Hu
 */
public final class NotInArrayCodePointFilter implements CodePointFilter {

  private int[] rejectCodePoints;

  public NotInArrayCodePointFilter() {
    rejectCodePoints = null;
  }

  public NotInArrayCodePointFilter(final int[] rejectCodePoints) {
    this.rejectCodePoints = rejectCodePoints;
  }

  public int[] getRejectCodePoints() {
    return rejectCodePoints;
  }

  public void setRejectCodePoints(final int[] rejectCodePoints) {
    this.rejectCodePoints = rejectCodePoints;
  }

  @Override
  public boolean accept(final Integer codePoint) {
    if (codePoint == null) {
      return false;
    }
    if (rejectCodePoints == null) {
      return true;
    }
    for (final int ch : rejectCodePoints) {
      if (ch == codePoint) {
        return false;
      }
    }
    return true;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NotInArrayCodePointFilter other = (NotInArrayCodePointFilter) o;
    return Equality.equals(rejectCodePoints, other.rejectCodePoints);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, rejectCodePoints);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("rejectCodePoints", rejectCodePoints)
        .toString();
  }
}
