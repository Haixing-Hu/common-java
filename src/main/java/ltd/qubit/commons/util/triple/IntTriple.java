////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.triple;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a triple of {@code int} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class IntTriple implements Serializable {
  private static final long serialVersionUID = 4320326299014925006L;

  public int first;
  public int second;
  public int third;

  public IntTriple() {
    first = 0;
    second = 0;
    third = 0;
  }

  public IntTriple(final int t1, final int t2, final int t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13119;
    int code = 1339;
    code = Hash.combine(code, multiplier, first);
    code = Hash.combine(code, multiplier, second);
    code = Hash.combine(code, multiplier, third);
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != IntTriple.class) {
      return false;
    }
    final IntTriple other = (IntTriple) obj;
    return (first == other.first)
        && (second == other.second)
        && (third == other.third);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .append("third", third)
               .toString();
  }
}
