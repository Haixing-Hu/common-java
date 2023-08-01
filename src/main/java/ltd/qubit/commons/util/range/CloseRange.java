////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.range;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The class represents close ranges.
 *
 * @param <T>
 *     the type of elements in the range.
 */
public class CloseRange<T> implements CloneableEx<CloseRange<T>> {

  private T min;
  private T max;

  public CloseRange() {
    // empty
  }

  public CloseRange(final T min, final T max) {
    this.min = min;
    this.max = max;
  }

  public CloseRange(final CloseRange<T> other) {
    this.min = other.min;
    this.max = other.max;
  }

  public final T getMin() {
    return min;
  }

  public void setMin(final T min) {
    this.min = min;
  }

  public final T getMax() {
    return max;
  }

  public void setMax(final T max) {
    this.max = max;
  }

  @SuppressWarnings("unchecked")
  public void check() {
    if (min == null) {
      throw new NullPointerException("min cannot be null.");
    }
    if (max == null) {
      throw new NullPointerException("max cannot be null");
    }
    if (min instanceof Comparable) {
      if (((Comparable<T>) min).compareTo(max) > 0) {
        throw new IllegalArgumentException("min must be less than or equal to max.");
      }
    }
  }

  @Override
  public CloseRange<T> clone() {
    return new CloseRange<>(this);
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final CloseRange<T> other = (CloseRange<T>) o;
    return Equality.equals(min, other.min)
        && Equality.equals(max, other.max);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, min);
    result = Hash.combine(result, multiplier, max);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("min", min)
        .append("max", max)
        .toString();
  }
}
