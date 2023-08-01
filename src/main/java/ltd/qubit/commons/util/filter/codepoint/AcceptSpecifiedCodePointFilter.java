////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.codepoint;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A character filter which only accepts a specified character.
 *
 * @author Haixing Hu
 */
public class AcceptSpecifiedCodePointFilter implements CodePointFilter {

  private final int acceptedCodePoint;

  public AcceptSpecifiedCodePointFilter(final char acceptedCodePoint) {
    this.acceptedCodePoint = acceptedCodePoint;
  }

  public AcceptSpecifiedCodePointFilter(final int acceptedCodePoint) {
    this.acceptedCodePoint = acceptedCodePoint;
  }

  public AcceptSpecifiedCodePointFilter(final CharSequence acceptedCodePoint) {
    this.acceptedCodePoint = Character.codePointAt(acceptedCodePoint, 0);
  }

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (codePoint == acceptedCodePoint);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final AcceptSpecifiedCodePointFilter other = (AcceptSpecifiedCodePointFilter) o;
    return Equality.equals(acceptedCodePoint, other.acceptedCodePoint);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptedCodePoint);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptedCodePoint", acceptedCodePoint)
        .toString();
  }
}
