////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.Hash;

import static ltd.qubit.commons.lang.Argument.requireInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A simple wrapper class which wraps a character array to a {@link CharSequence}.
 *
 * @author Haixing Hu
 */
public class CharArrayWrapper implements CharSequence, Comparable<CharArrayWrapper>  {

  private final char[] array;
  private final int start;
  private final int end;

  public CharArrayWrapper(final char[] array, final int start, final int end) {
    this.array = requireNonNull("array", array);
    this.start = requireNonNegative("start", start);
    this.end = requireInCloseRange("end", end, start, array.length);
  }

  @Override
  public int length() {
    return end - start;
  }

  @Override
  public char charAt(final int i) {
    final int index = i + start;
    if (index < start || index >= end) {
      throw new IndexOutOfBoundsException(i);
    }
    return array[index];
  }

  @Override
  public CharSequence subSequence(final int start, final int end) {
    if (start > end) {
      throw new IllegalArgumentException(
          "The start index must be less than or equal to the end index: start = "
        + start + ", end = " + end);
    }
    final int startIndex = start + this.start;
    final int endIndex = end + this.start;
    if (startIndex < this.start) {
      throw new IndexOutOfBoundsException(start);
    }
    if (endIndex > this.end) {
      throw new IndexOutOfBoundsException(end);
    }
    return new CharArrayWrapper(array, startIndex, endIndex);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final CharArrayWrapper other = (CharArrayWrapper) o;
    final int n = this.length();
    if (n != other.length()) {
      return false;
    }
    for (int i = 0; i < n; ++i) {
      if (this.array[i + this.start] != other.array[i + other.start]) {
        return false;
      }
    }
    return true;
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    for (int i = start; i < end; ++i) {
      result = Hash.combine(result, multiplier, array[i]);
    }
    return result;
  }

  public String toString() {
    return new String(array, start, end - start);
  }

  @Override
  public int compareTo(@NotNull final CharArrayWrapper other) {
    final int n1 = this.length();
    final int n2 = other.length();
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final char x = this.array[i + this.start];
      final char y = other.array[i + other.start];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }
}
