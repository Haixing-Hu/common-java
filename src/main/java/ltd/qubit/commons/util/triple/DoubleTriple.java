////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * This class is used to represent a triple of {@code double} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class DoubleTriple implements Serializable {
  private static final long serialVersionUID = 714860231074696573L;

  public double first;
  public double second;
  public double third;

  public DoubleTriple() {
    first = 0;
    second = 0;
    third = 0;
  }

  public DoubleTriple(final double t1, final double t2, final double t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 13115;
    int code = 1335;
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
    if (obj.getClass() != DoubleTriple.class) {
      return false;
    }
    final DoubleTriple other = (DoubleTriple) obj;
    return (Double.doubleToLongBits(first) == Double.doubleToLongBits(other.first))
        && (Double.doubleToLongBits(second) == Double.doubleToLongBits(other.second))
        && (Double.doubleToLongBits(third) == Double.doubleToLongBits(other.third));
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