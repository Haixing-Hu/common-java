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

import static ltd.qubit.commons.lang.Argument.requireLessEqual;

/**
 * A character filter which only accepts characters in the specified range.
 *
 * @author Haixing Hu
 */
public class InRangeCodePointFilter implements CodePointFilter {

  private final int start;
  private final int end;

  public InRangeCodePointFilter(final int start, final int end) {
    requireLessEqual("start", start, "end", end);
    this.start = start;
    this.end = end;
  }

  @Override
  public boolean accept(final Integer codePoint) {
    return (codePoint != null) && (start <= codePoint) && (codePoint <= end);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InRangeCodePointFilter other = (InRangeCodePointFilter) o;
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