////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.pair;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * his class is used to represent a pair of {@code int} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class IntPair implements Serializable {
  private static final long serialVersionUID = -673581249379319191L;

  public int first;
  public int second;

  public IntPair() {
    first = 0;
    second = 0;
  }

  public IntPair(final int t1, final int t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11119;
    int code = 1239;
    code = Hash.combine(code, multiplier, first);
    code = Hash.combine(code, multiplier, second);
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
    if (obj.getClass() != IntPair.class) {
      return false;
    }
    final IntPair other = (IntPair) obj;
    return (first == other.first)
        && (second == other.second);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .toString();
  }
}
