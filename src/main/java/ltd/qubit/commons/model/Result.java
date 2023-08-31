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
 * This model encapsulates the return result of the RESTful API as an object, so
 * that it can be correctly encoded as a JSON object.
 *
 * @param <T>
 *     The type of the returned result, usually a simple type such as
 *     {@link String}, {@link Boolean}, {@link Number}, etc.
 * @author Haixing Hu
 */
public class Result<T> {

  /**
   * The value to be wrapped.
   */
  private T value;

  public Result() {}

  public Result(final T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public final Result<T> setValue(final T value) {
    this.value = value;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    @SuppressWarnings("unchecked")final
    Result<T> other = (Result<T>) o;
    return Equality.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, result);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("value", value)
            .toString();
  }
}
