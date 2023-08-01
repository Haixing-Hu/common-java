////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A CharFilter that accept only the characters in the specified character array.
 *
 * @author Haixing Hu
 */
public final class InArrayCharFilter implements CharFilter {

  private char[] acceptedChars;

  public InArrayCharFilter() {}

  public InArrayCharFilter(final char[] acceptedChars) {
    this.acceptedChars = acceptedChars;
  }

  public char[] getAcceptedChars() {
    return acceptedChars;
  }

  public void setAcceptedChars(final char[] acceptedChars) {
    this.acceptedChars = acceptedChars;
  }

  @Override
  public boolean accept(final Character ch) {
    if ((ch == null) || (acceptedChars == null)) {
      return false;
    }
    for (final char c : acceptedChars) {
      if (c == ch) {
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
    final InArrayCharFilter other = (InArrayCharFilter) o;
    return Equality.equals(acceptedChars, other.acceptedChars);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptedChars);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptedChars", acceptedChars)
        .toString();
  }
}
