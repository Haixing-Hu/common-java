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
 * This class is used to represent a pair of {@code byte} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class BytePair implements Serializable {
  private static final long serialVersionUID = -5364874823256392299L;

  public byte first;
  public byte second;

  public BytePair() {
    first = 0;
    second = 0;
  }

  public BytePair(final byte t1, final byte t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11111;
    int code = 1231;
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
    if (obj.getClass() != BytePair.class) {
      return false;
    }
    final BytePair other = (BytePair) obj;
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
