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
 * A character filter which rejects a specified character.
 *
 * @author Haixing Hu
 */
public class RejectSpecifiedCodePointFilter implements CodePointFilter {

  private final int rejectedCodePoint;

  public RejectSpecifiedCodePointFilter(final char rejectedCodePoint) {
    this.rejectedCodePoint = rejectedCodePoint;
  }

  public RejectSpecifiedCodePointFilter(final int rejectedCodePoint) {
    this.rejectedCodePoint = rejectedCodePoint;
  }

  public RejectSpecifiedCodePointFilter(final CharSequence rejectedCodePoint) {
    this.rejectedCodePoint = Character.codePointAt(rejectedCodePoint, 0);
  }

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (codePoint != rejectedCodePoint);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final RejectSpecifiedCodePointFilter other = (RejectSpecifiedCodePointFilter) o;
    return Equality.equals(rejectedCodePoint, other.rejectedCodePoint);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, rejectedCodePoint);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("rejectedCodePoint", rejectedCodePoint)
        .toString();
  }
}
