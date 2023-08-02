////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A character filter which only rejects a specified character.
 *
 * @author Haixing Hu
 */
public class RejectSpecifiedCharFilter implements CharFilter {

  private final int rejectedChar;

  public RejectSpecifiedCharFilter(final int rejectedChar) {
    this.rejectedChar = rejectedChar;
  }

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (ch != rejectedChar);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final RejectSpecifiedCharFilter other = (RejectSpecifiedCharFilter) o;
    return Equality.equals(rejectedChar, other.rejectedChar);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, rejectedChar);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("rejectedChar", rejectedChar)
        .toString();
  }
}
