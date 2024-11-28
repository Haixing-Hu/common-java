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
 * A CharFilter that accept only the characters in the specified string.
 *
 * @author Haixing Hu
 */
public final class InStringCharFilter implements CharFilter {

  private CharSequence acceptChars;

  public InStringCharFilter() {}

  public InStringCharFilter(final CharSequence acceptChars) {
    this.acceptChars = acceptChars;
  }

  public CharSequence getAcceptChars() {
    return acceptChars;
  }

  public void setAcceptChars(final CharSequence acceptChars) {
    this.acceptChars = acceptChars;
  }

  @Override
  public boolean accept(final Character ch) {
    if ((ch == null) || (acceptChars == null)) {
      return false;
    }
    final int n = acceptChars.length();
    for (int i = 0; i < n; ++i) {
      if (acceptChars.charAt(i) == ch) {
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
    final InStringCharFilter other = (InStringCharFilter) o;
    return Equality.equals(acceptChars, other.acceptChars);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptChars);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptChars", acceptChars)
        .toString();
  }
}
