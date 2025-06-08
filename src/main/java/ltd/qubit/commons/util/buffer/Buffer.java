////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.buffer;

import java.io.Serializable;
import java.lang.reflect.Array;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.util.expand.ExpansionPolicy;

import static ltd.qubit.commons.lang.Argument.requireGreaterEqual;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_OBJECT_ARRAY;

/**
 * A simple auto-expansion buffer of {@code Object} values of specified
 * class.
 *
 * @param <T>
 *          the type values in the buffer.
 * @author Haixing Hu
 */
public class Buffer<T> implements Serializable, Cloneable {

  private static final long serialVersionUID = - 3826413585563040903L;

  /**
   * The buffer used to store the values.
   */
  private T[] buffer;

  /**
   * The length of the array stored in this buffer.
   */
  private int length;

  /**
   * The class object of the values.
   */
  private Class<T> valueClass;

  /**
   * The expansion policy used by this buffer.
   */
  protected transient ExpansionPolicy expansionPolicy;

  @SuppressWarnings("unchecked")
  public Buffer(final Class<T> valueClass) {
    buffer = (T[]) EMPTY_OBJECT_ARRAY;
    length = 0;
    this.valueClass = requireNonNull("valueClass", valueClass);
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  @SuppressWarnings("unchecked")
  public Buffer(final Class<T> valueClass, final ExpansionPolicy expansionPolicy) {
    buffer = (T[]) EMPTY_OBJECT_ARRAY;
    length = 0;
    this.valueClass = requireNonNull("valueClass", valueClass);
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public Buffer(final T[] buffer, final Class<T> valueClass) {
    this.buffer = requireNonNull("buffer", buffer);
    length = 0;
    this.valueClass = requireNonNull("valueClass", valueClass);
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public Buffer(final T[] buffer, final Class<T> valueClass,
      final ExpansionPolicy expansionPolicy) {
    this.buffer = requireNonNull("buffer", buffer);
    length = 0;
    this.valueClass = requireNonNull("valueClass", valueClass);
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  @SuppressWarnings("unchecked")
  public Buffer(final int bufferSize, final Class<T> valueClass) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      buffer = null;
    } else {
      buffer = (T[]) Array.newInstance(valueClass, bufferSize);
    }
    length = 0;
    this.valueClass = requireNonNull("valueClass", valueClass);
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  @SuppressWarnings("unchecked")
  public Buffer(final int bufferSize, final Class<T> valueClass,
      final ExpansionPolicy expansionPolicy) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      buffer = null;
    } else {
      buffer = (T[]) Array.newInstance(valueClass, bufferSize);
    }
    length = 0;
    this.valueClass = requireNonNull("valueClass", valueClass);
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public ExpansionPolicy getExpansionPolicy() {
    return expansionPolicy;
  }

  public void setExpansionPolicy(final ExpansionPolicy expansionPolicy) {
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public T at(final int i) {
    return buffer[i];
  }

  public T[] buffer() {
    return buffer;
  }

  public int length() {
    return length;
  }

  public int capacity() {
    return buffer.length;
  }

  public boolean isEmpty() {
    return length == 0;
  }

  public boolean isFull() {
    return length == buffer.length;
  }

  public void clear() {
    length = 0;
  }

  public int room() {
    return buffer.length - length;
  }

  public void setLength(final int newLength) {
    if (newLength < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength, valueClass);
    }
    length = newLength;
  }

  public void append(final T obj) {
    if (length >= buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, length + 1, valueClass);
    }
    buffer[length++] = obj;
  }

  public void append(@Nullable final T[] array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength, valueClass);
    }
    System.arraycopy(array, 0, buffer, length, array.length);
    length = newLength;
  }

  public void append(@Nullable final T[] array, final int off, final int n) {
    if (array == null) {
      return;
    }
    Argument.checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength, valueClass);
    }
    System.arraycopy(array, off, buffer, length, n);
    length = newLength;
  }

  public void append(@Nullable final Buffer<T> array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength, valueClass);
    }
    System.arraycopy(array.buffer, 0, buffer, length, array.length);
    length = newLength;
  }

  public void append(@Nullable final Buffer<T> array, final int off, final int n) {
    if (array == null) {
      return;
    }
    Argument.checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength, valueClass);
    }
    System.arraycopy(array.buffer, off, buffer, length, n);
    length = newLength;
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified value, starting the search at the specified
   * {@code beginIndex} and finishing at {@code endIndex}. If no such
   * value occurs in this buffer within the specified bounds, {@code -1} is
   * returned.
   *
   * <p>There is no restriction on the value of {@code beginIndex} and
   * {@code endIndex}. If {@code beginIndex} is negative, it has the
   * same effect as if it were zero. If {@code endIndex} is greater than
   * {@link #length()}, it has the same effect as if it were {@link #length()}.
   * If the {@code beginIndex} is greater than the {@code endIndex},
   * {@code -1} is returned.
   *
   * @param value
   *          the value to search for.
   * @param beginIndex
   *          the index to start the search from.
   * @param endIndex
   *          the index to finish the search at.
   * @return the current of the first occurrence of the value in the buffer
   *         within the given bounds, or {@code -1} if the value does not
   *         occur.
   */
  public int indexOf(@Nullable final T value, final int beginIndex,
      final int endIndex) {
    final int begin = Math.max(0, beginIndex);
    final int end = Math.min(length, endIndex);
    if (begin > end) {
      return - 1;
    }
    for (int i = begin; i < end; ++i) {
      if (Equality.equals(buffer[i], value)) {
        return i;
      }
    }
    return - 1;
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified value, starting the search at {@code 0} and finishing at
   * {@link #length()}. If no such value occurs in this buffer within those
   * bounds, {@code -1} is returned.
   *
   * @param value
   *          the value to search for.
   * @return the current of the first occurrence of the character in the buffer,
   *         or {@code -1} if the value does not occur.
   */
  public int indexOf(@Nullable final T value) {
    return indexOf(value, 0, length);
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified value, starting the search at the specified
   * {@code beginIndex} and finishing at {@code endIndex}. If no such
   * value occurs in this buffer within the specified bounds, {@code -1} is
   * returned.
   *
   * <p>There is no restriction on the value of {@code beginIndex} and
   * {@code endIndex}. If {@code beginIndex} is negative, it has the
   * same effect as if it were zero. If {@code endIndex} is greater than
   * {@link #length()}, it has the same effect as if it were {@link #length()}.
   * If the {@code beginIndex} is greater than the {@code endIndex},
   * {@code -1} is returned.
   *
   * @param value
   *          the value to search for.
   * @param beginIndex
   *          the index to start the search from.
   * @param endIndex
   *          the index to finish the search at. If it is larger than the
   *          length of this buffer, it is treated as the length of this buffer.
   * @return the current of the first occurrence of the value in the buffer
   *         within the given bounds, or {@code -1} if the value does not
   *         occur.
   */
  public int lastIndexOf(@Nullable final T value, final int beginIndex,
      final int endIndex) {
    final int begin = Math.max(0, beginIndex);
    final int end = Math.min(length, endIndex);
    if (begin > end) {
      return - 1;
    }
    for (int i = end - 1; i >= begin; --i) {
      if (Equality.equals(buffer[i], value)) {
        return i;
      }
    }
    return - 1;
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified value, starting the search at {@code 0} and finishing at
   * {@link #length()}. If no such value occurs in this buffer within those
   * bounds, {@code -1} is returned.
   *
   * @param value
   *          the value to search for.
   * @return the current of the first occurrence of the character in the buffer,
   *         or {@code -1} if the value does not occur.
   */
  public int lastIndexOf(@Nullable final T value) {
    return lastIndexOf(value, 0, length);
  }

  @SuppressWarnings("unchecked")
  public void compact() {
    if (length == 0) {
      buffer = null;
    } else if (length < buffer.length) {
      final T[] temp = (T[]) java.lang.reflect.Array.newInstance(valueClass,
          length);
      System.arraycopy(buffer, 0, temp, 0, length);
      buffer = temp;
    }
  }

  @SuppressWarnings("unchecked")
  public void reserve(final int n, final boolean keepContent) {
    if ((buffer == null) || (buffer.length < n)) {
      final T[] newBuffer = (T[]) java.lang.reflect.Array.newInstance(
          valueClass, n);
      if (keepContent && (length > 0)) {
        System.arraycopy(buffer, 0, newBuffer, 0, length);
      } else {
        length = 0;
      }
      buffer = newBuffer;
    }
  }

  public void swap(final Buffer<T> other) {
    final T[] tempBuffer = other.buffer;
    other.buffer = buffer;
    buffer = tempBuffer;
    final int tempLength = other.length;
    other.length = length;
    length = tempLength;
  }

  @SuppressWarnings("unchecked")
  public void assign(final Buffer<T> that) {
    if (this != that) {
      length = 0;
      if (that.length == 0) {
        return;
      }
      if ((buffer == null) || (buffer.length < that.length)) {
        // create a new buffer and abandon the old one
        buffer = (T[]) java.lang.reflect.Array.newInstance(valueClass,
            that.length);
      }
      System.arraycopy(that.buffer, 0, buffer, 0, that.length);
      length = that.length;
    }
  }

  @SuppressWarnings("unchecked")
  public T[] toArray() {
    if (length == 0) {
      return (T[]) EMPTY_OBJECT_ARRAY;
    } else {
      final T[] result = (T[]) java.lang.reflect.Array.newInstance(valueClass,
          length);
      System.arraycopy(buffer, 0, result, 0, length);
      return result;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Buffer<T> clone() {
    try {
      final Buffer<T> result = (Buffer<T>) super.clone();
      result.valueClass = valueClass;
      result.expansionPolicy = expansionPolicy;
      result.length = length;
      if (length == 0) {
        result.buffer = null;
      } else {
        result.buffer = (T[]) Array.newInstance(valueClass, buffer.length);
        System.arraycopy(buffer, 0, result.buffer, 0, buffer.length);
      }
      return result;
    } catch (final CloneNotSupportedException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  @Override
  public int hashCode() {
    final int multiplier = 1771;
    int code = 93;
    code = Hash.combine(code, multiplier, length);
    for (int i = 0; i < length; ++i) {
      code = Hash.combine(code, multiplier, buffer[i]);
    }
    return code;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Buffer<T> other = (Buffer<T>) obj;
    if (length != other.length) {
      return false;
    }
    for (int i = 0; i < length; ++i) {
      if (! Equality.equals(buffer[i], other.buffer[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    if (length == 0) {
      return "[]";
    } else {
      final StringBuilder builder = new StringBuilder();
      builder.append('[');
      for (int i = 0; i < length; ++i) {
        builder.append(buffer[i]).append(',');
      }
      // eat the last separator ','
      builder.setLength(builder.length() - 1);
      builder.append(']');
      return builder.toString();
    }
  }

}