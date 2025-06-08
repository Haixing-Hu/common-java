////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * A CharFilter that accept only the characters in the specified code point array.
 *
 * @author Haixing Hu
 */
public final class InArrayCodePointFilter implements CodePointFilter {

  private int[] acceptCodePoints;

  public InArrayCodePointFilter() {
    acceptCodePoints = null;
  }

  public InArrayCodePointFilter(final int[] acceptCodePoints) {
    this.acceptCodePoints = acceptCodePoints;
  }

  public int[] getAcceptCodePoints() {
    return acceptCodePoints;
  }

  public void setAcceptCodePoints(final int[] acceptCodePoints) {
    this.acceptCodePoints = acceptCodePoints;
  }

  @Override
  public boolean accept(final Integer codePoint) {
    if (codePoint == null || acceptCodePoints == null) {
      return false;
    }
    for (final int ch : acceptCodePoints) {
      if (ch == codePoint) {
        return true;
      }
    }
    return false;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InArrayCodePointFilter other = (InArrayCodePointFilter) o;
    return Equality.equals(acceptCodePoints, other.acceptCodePoints);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptCodePoints);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptCodePoints", acceptCodePoints)
        .toString();
  }
}