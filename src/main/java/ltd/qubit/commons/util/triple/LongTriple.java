////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.triple;

import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.io.Serializable;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * This class is used to represent a triple of {@code long} values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class LongTriple implements Serializable {
  private static final long serialVersionUID = 5236016874901009872L;

  public long first;
  public long second;
  public long third;

  public LongTriple() {
    first = 0;
    second = 0;
    third = 0;
  }

  public LongTriple(final long t1, final long t2, final long t3) {
    first = t1;
    second = t2;
    third = t3;
  }

  @Override
  public int hashCode() {
    final int multiplier = 131111;
    int code = 13311;
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
    if (obj.getClass() != LongTriple.class) {
      return false;
    }
    final LongTriple other = (LongTriple) obj;
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
