////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * A character filter which only accepts a specified character.
 *
 * @author Haixing Hu
 */
public class AcceptSpecifiedCharFilter implements CharFilter {

  private final int acceptedChar;

  public AcceptSpecifiedCharFilter(final int acceptedChar) {
    this.acceptedChar = acceptedChar;
  }

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (ch == acceptedChar);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final AcceptSpecifiedCharFilter other = (AcceptSpecifiedCharFilter) o;
    return Equality.equals(acceptedChar, other.acceptedChar);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptedChar);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptedChar", acceptedChar)
        .toString();
  }
}
