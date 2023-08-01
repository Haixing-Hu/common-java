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
 * A CharFilter that accept only the characters not in the specified array.
 *
 * @author Haixing Hu
 */
public final class NotInArrayCharFilter implements CharFilter {

  private char[] rejectedChars;

  public NotInArrayCharFilter() {}

  public NotInArrayCharFilter(final char[] rejectedChars) {
    this.rejectedChars = rejectedChars;
  }

  public char[] getRejectedChars() {
    return rejectedChars;
  }

  public void setRejectedChars(final char[] rejectedChars) {
    this.rejectedChars = rejectedChars;
  }

  @Override
  public boolean accept(final Character ch) {
    if (ch == null) {
      return false;
    }
    if (rejectedChars == null) {
      return true;
    }
    for (final char c : rejectedChars) {
      if (c == ch) {
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
    final NotInArrayCharFilter other = (NotInArrayCharFilter) o;
    return Equality.equals(rejectedChars, other.rejectedChars);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, rejectedChars);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("rejectedChars", rejectedChars)
        .toString();
  }
}
