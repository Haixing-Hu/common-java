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

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * This class is used to represent a generic pair.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class Pair<T1, T2> implements Serializable {

  private static final long serialVersionUID = -1250168249356486261L;

  public T1 first;
  public T2 second;

  public Pair() {
    first = null;
    second = null;
  }

  public Pair(final T1 t1, final T2 t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 1111;
    int code = 123;
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
    if (obj.getClass() != Pair.class) {
      return false;
    }
    final Pair<?, ?> other = (Pair<?, ?>) obj;
    return Equality.equals(first, other.first)
        && Equality.equals(second, other.second);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("first", first)
        .append("second", second)
        .toString();
  }
}
