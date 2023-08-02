////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * This class is used to represent a pair of {@code float} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class FloatPair implements Serializable {
  private static final long serialVersionUID = -7218161341864776382L;

  public float first;
  public float second;

  public FloatPair() {
    first = 0;
    second = 0;
  }

  public FloatPair(final float t1, final float t2) {
    first = t1;
    second = t2;
  }

  @Override
  public int hashCode() {
    final int multiplier = 11117;
    int code = 1237;
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
    if (obj.getClass() != FloatPair.class) {
      return false;
    }
    final FloatPair other = (FloatPair) obj;
    return (Float.floatToIntBits(first) == Float.floatToIntBits(other.first))
        && (Float.floatToIntBits(second) == Float.floatToIntBits(other.second));
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("first", first)
               .append("second", second)
               .toString();
  }
}
