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
 * This class is used to represent a pair of {@code double} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class DoublePair implements Serializable {
  private static final long serialVersionUID = -4610564022468622127L;

  public double first;
  public double second;

  public DoublePair() {
    first = 0;
    second = 0;
  }

  public DoublePair(final double t1, final double t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11115;
    int code = 1235;
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
    if (obj.getClass() != DoublePair.class) {
      return false;
    }
    final DoublePair other = (DoublePair) obj;
    return (Double.doubleToLongBits(first) == Double.doubleToLongBits(other.first))
        && (Double.doubleToLongBits(second) == Double.doubleToLongBits(other.second));
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .toString();
  }
}
