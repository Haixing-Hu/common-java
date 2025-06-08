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

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a generic triple.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class Triple<T1, T2, T3> implements Serializable {
  private static final long serialVersionUID = -6056772926114573820L;

  public T1 first;
  public T2 second;
  public T3 third;

  public Triple() {
    first = null;
    second = null;
    third = null;
  }

  public Triple(final T1 t1, final T2 t2, final T3 t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 1311;
    int code = 133;
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
    if (obj.getClass() != Triple.class) {
      return false;
    }
    final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
    return Equality.equals(first, other.first)
        && Equality.equals(second, other.second)
        && Equality.equals(third, other.third);
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