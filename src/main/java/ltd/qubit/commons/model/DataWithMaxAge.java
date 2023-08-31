////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A data wrapper interface with a {@code maxAge} property.
 *
 * <p>This object can be used to set the lifetime of each object in the cache.</p>
 *
 * @param <T>
 *     The type of data being encapsulated.
 */
public class DataWithMaxAge<T> {
  /**
   * The encapsulated object.
   */
  private final T data;

  /**
   * The maximum lifetime of this object, in milliseconds.
   */
  private final long maxAge;

  public DataWithMaxAge(final T data, final long maxAge) {
    this.data = data;
    this.maxAge = maxAge;
  }

  public T getData() {
    return data;
  }

  public long getMaxAge() {
    return maxAge;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final DataWithMaxAge<?> other = (DataWithMaxAge<?>) o;
    return Equality.equals(data, other.data)
        && Equality.equals(maxAge, other.maxAge);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, data);
    result = Hash.combine(result, multiplier, maxAge);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("data", data)
        .append("maxAge", maxAge)
        .toString();
  }
}
