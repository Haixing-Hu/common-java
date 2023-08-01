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

import static ltd.qubit.commons.lang.Argument.requireLessEqual;

/**
 * A character filter which only accepts characters in the specified range.
 *
 * @author Haixing Hu
 */
public class InRangeCharFilter implements CharFilter {

  private final char start;
  private final char end;

  public InRangeCharFilter(final char start, final char end) {
    requireLessEqual("start", start, "end", end);
    this.start = start;
    this.end = end;
  }

  @Override
  public boolean accept(final Character ch) {
    return (ch != null) && (start <= ch) && (ch <= end);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InRangeCharFilter other = (InRangeCharFilter) o;
    return Equality.equals(start, other.start)
        && Equality.equals(end, other.end);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, start);
    result = Hash.combine(result, multiplier, end);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("start", start)
        .append("end", end)
        .toString();
  }
}
