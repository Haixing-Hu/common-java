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
 * This class is used to represent a pair of {@code short} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class ShortPair implements Serializable {
  private static final long serialVersionUID = 8526908146264856479L;

  public short first;
  public short second;

  public ShortPair() {
    first = 0;
    second = 0;
  }

  public ShortPair(final short t1, final short t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 111113;
    int code = 12313;
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
    if (obj.getClass() != ShortPair.class) {
      return false;
    }
    final ShortPair other = (ShortPair) obj;
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
