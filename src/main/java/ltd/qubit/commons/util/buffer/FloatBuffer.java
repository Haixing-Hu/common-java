////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.buffer;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.Size;
import ltd.qubit.commons.lang.Swappable;
import ltd.qubit.commons.util.expand.ExpansionPolicy;

import java.io.Serializable;
import javax.annotation.Nullable;

import static ltd.qubit.commons.io.serialize.BinarySerialization.register;
import static ltd.qubit.commons.lang.Argument.checkBounds;
import static ltd.qubit.commons.lang.Argument.requireGreaterEqual;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_FLOAT_ARRAY;

import static java.lang.System.arraycopy;

/**
 * A simple auto-expansion buffer of {@code float} values.
 *
 * @author Haixing Hu
 */
public final class FloatBuffer implements Swappable<FloatBuffer>,
    Assignable<FloatBuffer>, CloneableEx<FloatBuffer>, Comparable<FloatBuffer>,
    Serializable {

  private static final long serialVersionUID = -5910973645512964339L;

  static {
    register(FloatBuffer.class, FloatBufferBinarySerializer.INSTANCE);
  }

  /**
   * The buffer used to store the {@code float} values.
   */
  protected float[] buffer;

  /**
   * The length of the array stored in this {@code float} buffer.
   */
  protected int length;

  /**
   * The expansion policy used by this buffer.
   */
  protected transient ExpansionPolicy expansionPolicy;

  public FloatBuffer() {
    buffer = EMPTY_FLOAT_ARRAY;
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public FloatBuffer(final ExpansionPolicy expansionPolicy) {
    buffer = EMPTY_FLOAT_ARRAY;
    length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public FloatBuffer(final float[] buffer) {
    this.buffer = requireNonNull("buffer", buffer);
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public FloatBuffer(final float[] buffer,
      final ExpansionPolicy expansionPolicy) {
    this.buffer = requireNonNull("buffer", buffer);
    length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public FloatBuffer(final int bufferSize) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      buffer = EMPTY_FLOAT_ARRAY;
    } else {
      buffer = new float[bufferSize];
    }
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public FloatBuffer(final int bufferSize,
      final ExpansionPolicy expansionPolicy) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      buffer = EMPTY_FLOAT_ARRAY;
    } else {
      buffer = new float[bufferSize];
    }
    length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public ExpansionPolicy getExpansionPolicy() {
    return expansionPolicy;
  }

  public void setExpansionPolicy(final ExpansionPolicy expansionPolicy) {
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public long bytes() {
    return Size.REFERENCE + Size.INT + (buffer.length * Size.FLOAT);
  }

  public float at(final int i) {
    return buffer[i];
  }

  public float[] buffer() {
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
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    length = newLength;
  }

  public void append(final float ch) {
    if (length >= buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, length + 1);
    }
    buffer[length++] = ch;
  }

  public void append(@Nullable final float[] array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    arraycopy(array, 0, buffer, length, array.length);
    length = newLength;
  }

  public void append(@Nullable final float[] array, final int off,
      final int n) {
    if (array == null) {
      return;
    }
    checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    arraycopy(array, off, buffer, length, n);
    length = newLength;
  }

  public void append(@Nullable final FloatBuffer array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    arraycopy(array.buffer, 0, buffer, length, array.length);
    length = newLength;
  }

  public void append(@Nullable final FloatBuffer array, final int off,
      final int n) {
    if (array == null) {
      return;
    }
    checkBounds(off, n, array.length);
    if (n == 0) {
      return;
    }
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    arraycopy(array.buffer, off, buffer, length, n);
    length = newLength;
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified value, starting the search at the specified {@code beginIndex}
   * and finishing at {@code endIndex}. If no such value occurs in this buffer
   * within the specified bounds, {@code -1} is returned.
   *
   * <p>There is no restriction on the value of {@code beginIndex} and
   * {@code endIndex}. If {@code beginIndex} is negative, it has the same effect
   * as if it were zero. If {@code endIndex} is greater than {@link #length()},
   * it has the same effect as if it were {@link #length()}. If the {@code
   * beginIndex} is greater than the {@code endIndex}, {@code -1} is returned.
   *
   * @param value
   *     the value to search for.
   * @param beginIndex
   *     the index to start the search from.
   * @param endIndex
   *     the index to finish the search at.
   * @param epsilon
   *     the epsilon used to compare floating-point values.
   * @return the current of the first occurrence of the value in the buffer
   *     within the given bounds, or {@code -1} if the value does not occur.
   */
  public int indexOf(final float value, final int beginIndex, final int endIndex,
      final float epsilon) {
    final int begin = Math.max(0, beginIndex);
    final int end = Math.max(length, endIndex);
    if (begin > end) {
      return -1;
    }
    for (int i = begin; i < end; ++i) {
      if (Math.abs(buffer[i] - value) < epsilon) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified value, starting the search at {@code 0} and finishing at {@link
   * #length()}. If no such value occurs in this buffer within those bounds,
   * {@code -1} is returned.
   *
   * @param value
   *     the value to search for.
   * @param epsilon
   *     the epsilon used to compare floating-point values.
   * @return the current of the first occurrence of the character in the buffer,
   *     or {@code -1} if the value does not occur.
   */
  public int indexOf(final float value, final float epsilon) {
    return indexOf(value, 0, length, epsilon);
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified value, starting the search at the specified {@code beginIndex}
   * and finishing at {@code endIndex}. If no such value occurs in this buffer
   * within the specified bounds, {@code -1} is returned.
   *
   * <p>There is no restriction on the value of {@code beginIndex} and
   * {@code endIndex}. If {@code beginIndex} is negative, it has the same effect
   * as if it were zero. If {@code endIndex} is greater than {@link #length()},
   * it has the same effect as if it were {@link #length()}. If the {@code
   * beginIndex} is greater than the {@code endIndex}, {@code -1} is returned.
   *
   * @param value
   *     the value to search for.
   * @param beginIndex
   *     the index to start the search from.
   * @param endIndex
   *     the index to finish the search at. If it is larger than the length of
   *     this buffer, it is treated as the length of this buffer.
   * @param epsilon
   *     the epsilon used to compare floating-point values.
   * @return the current of the first occurrence of the value in the buffer
   *     within the given bounds, or {@code -1} if the value does not occur.
   */
  public int lastIndexOf(final float value, final int beginIndex, final int endIndex,
      final float epsilon) {
    final int begin = Math.max(0, beginIndex);
    final int end = Math.max(length, endIndex);
    if (begin > end) {
      return -1;
    }
    for (int i = end - 1; i >= begin; --i) {
      if (Math.abs(buffer[i] - value) < epsilon) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified value, starting the search at {@code 0} and finishing at {@link
   * #length()}. If no such value occurs in this buffer within those bounds,
   * {@code -1} is returned.
   *
   * @param value
   *     the value to search for.
   * @param epsilon
   *     the epsilon used to compare floating-point values.
   * @return the current of the first occurrence of the character in the buffer,
   *     or {@code -1} if the value does not occur.
   */
  public int lastIndexOf(final float value, final float epsilon) {
    return lastIndexOf(value, 0, length, epsilon);
  }

  public void compact() {
    if (length == 0) {
      buffer = EMPTY_FLOAT_ARRAY;
    } else if (length < buffer.length) {
      final float[] temp = new float[length];
      arraycopy(buffer, 0, temp, 0, length);
      buffer = temp;
    }
  }

  public void reserve(final int n, final boolean keepContent) {
    if (buffer.length < n) {
      final float[] newBuffer = new float[n];
      if (keepContent && (length > 0)) {
        arraycopy(buffer, 0, newBuffer, 0, length);
      } else {
        length = 0;
      }
      buffer = newBuffer;
    }
  }

  @Override
  public void swap(final FloatBuffer other) {
    final float[] tempBuffer = other.buffer;
    other.buffer = buffer;
    buffer = tempBuffer;
    final int tempLength = other.length;
    other.length = length;
    length = tempLength;
  }

  @Override
  public void assign(final FloatBuffer that) {
    if (this != that) {
      length = 0;
      if (buffer.length < that.length) {
        // create a new buffer and abandon the old one
        buffer = new float[that.length];
      }
      arraycopy(that.buffer, 0, buffer, 0, that.length);
      length = that.length;
    }
  }

  @Override
  public FloatBuffer clone() {
    final FloatBuffer cloned = new FloatBuffer(buffer.length);
    cloned.length = length;
    arraycopy(buffer, 0, cloned.buffer, 0, buffer.length);
    return cloned;
  }

  public float[] toArray() {
    if (length == 0) {
      return EMPTY_FLOAT_ARRAY;
    } else {
      final float[] result = new float[length];
      arraycopy(buffer, 0, result, 0, length);
      return result;
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
    final FloatBuffer other = (FloatBuffer) obj;
    return (length == other.length)
        && Equality.equals(buffer, 0, other.buffer, 0, length);
  }

  @Override
  public int compareTo(final FloatBuffer other) {
    if (other == null) {
      return +1;
    } else if (this == other) {
      return 0;
    } else {
      return Comparison.compare(buffer, length, other.buffer, other.length);
    }
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
