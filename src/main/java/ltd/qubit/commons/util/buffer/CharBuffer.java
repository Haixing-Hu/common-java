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
import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.Size;
import ltd.qubit.commons.lang.Swappable;
import ltd.qubit.commons.text.Ascii;
import ltd.qubit.commons.util.expand.ExpansionPolicy;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import javax.annotation.Nullable;

import static ltd.qubit.commons.io.serialize.BinarySerialization.register;
import static ltd.qubit.commons.lang.Argument.checkBounds;
import static ltd.qubit.commons.lang.Argument.requireGreaterEqual;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CHAR_ARRAY;

import static java.lang.System.arraycopy;

/**
 * A simple auto-expansion buffer for {@code char} values.
 *
 * @author Haixing Hu
 */
public final class CharBuffer implements Swappable<CharBuffer>,
    Assignable<CharBuffer>, Comparable<CharBuffer>, Serializable {

  private static final long serialVersionUID = - 5150163651924701441L;

  static {
    register(CharBuffer.class, CharBufferBinarySerializer.INSTANCE);
  }

  /**
   * The buffer used to store the characters.
   */
  char[] buffer;

  /**
   * The length of the array stored in this character buffer.
   */
  int length;

  /**
   * The expansion policy used by this buffer.
   */
  transient ExpansionPolicy expansionPolicy;

  public CharBuffer() {
    buffer = EMPTY_CHAR_ARRAY;
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final ExpansionPolicy expansionPolicy) {
    buffer = EMPTY_CHAR_ARRAY;
    length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final char[] buffer) {
    this.buffer = requireNonNull("buffer", buffer);
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final char[] buffer, final ExpansionPolicy expansionPolicy) {
    this.buffer = requireNonNull("buffer", buffer);
    length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final int bufferSize) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      buffer = EMPTY_CHAR_ARRAY;
    } else {
      buffer = new char[bufferSize];
    }
    length = 0;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final int bufferSize, final ExpansionPolicy expansionPolicy) {
    requireGreaterEqual("bufferSize", bufferSize, "zero", 0);
    if (bufferSize == 0) {
      buffer = EMPTY_CHAR_ARRAY;
    } else {
      buffer = new char[bufferSize];
    }
    length = 0;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final String value) {
    buffer = value.toCharArray();
    length = buffer.length;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final String value, final ExpansionPolicy expansionPolicy) {
    buffer = value.toCharArray();
    length = buffer.length;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public CharBuffer(final char ch) {
    buffer = new char[1];
    buffer[0] = ch;
    length = 1;
    expansionPolicy = ExpansionPolicy.getDefault();
  }

  public CharBuffer(final char ch, final ExpansionPolicy expansionPolicy) {
    buffer = new char[1];
    buffer[0] = ch;
    length = 1;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public ExpansionPolicy getExpansionPolicy() {
    return expansionPolicy;
  }

  public void setExpansionPolicy(final ExpansionPolicy expansionPolicy) {
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
  }

  public long bytes() {
    return Size.REFERENCE + Size.INT + (buffer.length * Size.CHAR);
  }

  public char at(final int i) {
    return buffer[i];
  }

  public char[] buffer() {
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

  public void append(final char ch) {
    if (length >= buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, length + 1);
    }
    buffer[length++] = ch;
  }

  public void append(@Nullable final char[] array) {
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

  public void append(@Nullable final char[] array, final int off, final int n) {
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

  public void append(@Nullable final String str) {
    if (str == null) {
      return;
    }
    final int strLen = str.length();
    if (strLen == 0) {
      return;
    }
    final int newLength = length + strLen;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    str.getChars(0, strLen, buffer, length);
    length = newLength;
  }

  public void append(@Nullable final String str, final int off, final int n) {
    if ((str == null) || (n == 0)) {
      return;
    }
    checkBounds(off, n, str.length());
    final int newLength = length + n;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    str.getChars(off, off + n, buffer, length);
    length = newLength;
  }

  public void append(@Nullable final CharBuffer array) {
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

  public void append(@Nullable final CharBuffer array, final int off,
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

  public int append(@Nullable final Reader reader, final int limit)
      throws IOException {
    if ((reader == null) || (limit <= 0)) {
      return 0;
    }
    int remained = limit;
    while (remained > 0) {
      if (length == buffer.length) {
        final int newLength = length + ExpansionPolicy.getInitialCapacity();
        buffer = expansionPolicy.expand(buffer, length, newLength);
      }
      final int space = buffer.length - length;
      final int charsToRead = Math.min(space, remained);
      final int n = reader.read(buffer, length, charsToRead);
      if (n == - 1) {
        break; // EOF
      }
      length += n;
      remained -= n;
    }
    return limit - remained;
  }

  /**
   * Appends {@code n} bytes to this buffer from the given source array .
   * The capacity of the buffer is increased, if necessary, to accommodate all
   * bytes.
   *
   * <p>NOTE: The bytes are converted to chars using simple cast.
   *
   * @param array
   *          the bytes to be appended.
   */
  public void append(@Nullable final byte[] array) {
    if ((array == null) || (array.length == 0)) {
      return;
    }
    final int newLength = length + array.length;
    if (newLength > buffer.length) {
      buffer = expansionPolicy.expand(buffer, length, newLength);
    }
    for (int i1 = 0, i2 = length; i2 < newLength; ++i1, ++i2) {
      buffer[i2] = (char) (array[i1] & 0xFF);
    }
    length = newLength;
  }

  /**
   * Appends {@code n} bytes to this buffer from the given source array
   * starting at current {@code off}. The capacity of the buffer is
   * increased, if necessary, to accommodate all {@code n} bytes.
   *
   * <p>NOTE: The bytes are converted to chars using simple cast.
   *
   * @param array
   *          the bytes to be appended.
   * @param off
   *          the index of the first byte to append.
   * @param n
   *          the number of bytes to append.
   * @throws IndexOutOfBoundsException
   *           if {@code off} is out of range, {@code n} is negative,
   *           or {@code off} + {@code n} is out of range.
   */
  public void append(@Nullable final byte[] array, final int off, final int n) {
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
    for (int i1 = off, i2 = length; i2 < newLength; ++i1, ++i2) {
      buffer[i2] = (char) (array[i1] & 0xFF);
    }
    length = newLength;
  }

  /**
   * Appends {@code n} bytes to this buffer from the given source char
   * array buffer. The capacity of the buffer is increased if necessary to
   * accommodate all chars.
   *
   * <p>NOTE: The bytes are converted to chars using simple cast.
   *
   * @param array
   *          the bytes to be appended.
   */
  public void append(@Nullable final ByteBuffer array) {
    if (array == null) {
      return;
    }
    append(array.buffer(), 0, array.length());
  }

  /**
   * Appends {@code n} bytes to this buffer from the given source char
   * array buffer starting at current {@code off}. The capacity of the
   * buffer is increased if necessary to accommodate all {@code n} chars.
   *
   * <p>NOTE: The bytes are converted to chars using simple cast.
   *
   * @param array
   *          the bytes to be appended.
   * @param off
   *          the index of the first byte to append.
   * @param n
   *          the number of bytes to append.
   * @throws IndexOutOfBoundsException
   *           if {@code off} if out of range, {@code n} is negative,
   *           or {@code off} + {@code n} is out of range.
   */
  public void append(@Nullable final ByteBuffer array, final int off,
      final int n) {
    if (array == null) {
      return;
    }
    append(array.buffer(), off, n);
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified character, starting the search at the specified
   * {@code beginIndex} and finishing at {@code endIndex}. If no such
   * character occurs in this buffer within the specified bounds,
   * {@code -1} is returned.
   *
   * <p>There is no restriction on the value of {@code beginIndex} and
   * {@code endIndex}. If {@code beginIndex} is negative, it has the
   * same effect as if it were zero. If {@code endIndex} is greater than
   * {@link #length()}, it has the same effect as if it were {@link #length()}.
   * If the {@code beginIndex} is greater than the {@code endIndex},
   * {@code -1} is returned.
   *
   * @param ch
   *          the char to search for.
   * @param beginIndex
   *          the index to start the search from.
   * @param endIndex
   *          the index to finish the search at.
   * @return the current of the first occurrence of the character in the buffer
   *         within the given bounds, or {@code -1} if the character does
   *         not occur.
   */
  public int indexOf(final int ch, final int beginIndex, final int endIndex) {
    final int begin = Math.max(0, beginIndex);
    final int end = Math.min(length, endIndex);
    if (begin > end) {
      return -1;
    }
    for (int i = begin; i < end; ++i) {
      if (buffer[i] == ch) {
        return i;
      }
    }
    return - 1;
  }

  /**
   * Returns the current within this buffer of the first occurrence of the
   * specified character, starting the search at {@code 0} and finishing at
   * {@link #length()}. If no such character occurs in this buffer within those
   * bounds, {@code -1} is returned.
   *
   * @param ch
   *          the char to search for.
   * @return the current of the first occurrence of the character in the buffer,
   *         or {@code -1} if the character does not occur.
   */
  public int indexOf(final int ch) {
    return indexOf(ch, 0, length);
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified character, starting the search at the specified
   * {@code beginIndex} and finishing at {@code endIndex}. If no such
   * character occurs in this buffer within the specified bounds,
   * {@code -1} is returned.
   *
   * <p>There is no restriction on the value of {@code beginIndex} and
   * {@code endIndex}. If {@code beginIndex} is negative, it has the
   * same effect as if it were zero. If {@code endIndex} is greater than
   * {@link #length()}, it has the same effect as if it were {@link #length()}.
   * If the {@code beginIndex} is greater than the {@code endIndex},
   * {@code -1} is returned.
   *
   * @param ch
   *          the char to search for.
   * @param beginIndex
   *          the index to start the search from.
   * @param endIndex
   *          the index to finish the search at. If it is larger than the
   *          length of this buffer, it is treated as the length of this buffer.
   * @return the current of the first occurrence of the character in the buffer
   *         within the given bounds, or {@code -1} if the character does
   *         not occur.
   */
  public int lastIndexOf(final int ch, final int beginIndex, final int endIndex) {
    final int begin = Math.max(0, beginIndex);
    final int end = Math.min(length, endIndex);
    if (begin > end) {
      return -1;
    }
    for (int i = end - 1; i >= begin; --i) {
      if (buffer[i] == ch) {
        return i;
      }
    }
    return - 1;
  }

  /**
   * Returns the current within this buffer of the last occurrence of the
   * specified character, starting the search at {@code 0} and finishing at
   * {@link #length()}. If no such character occurs in this buffer within those
   * bounds, {@code -1} is returned.
   *
   * @param ch
   *          the char to search for.
   * @return the current of the first occurrence of the character in the buffer,
   *         or {@code -1} if the character does not occur.
   */
  public int lastIndexOf(final int ch) {
    return lastIndexOf(ch, 0, length);
  }

  public void compact() {
    if (length == 0) {
      buffer = EMPTY_CHAR_ARRAY;
    } else if (length < buffer.length) {
      final char[] temp = new char[length];
      arraycopy(buffer, 0, temp, 0, length);
      buffer = temp;
    }
  }

  public void reserve(final int n, final boolean keepContent) {
    if (buffer.length < n) {
      final char[] newBuffer = new char[n];
      if (keepContent && (length > 0)) {
        arraycopy(buffer, 0, newBuffer, 0, length);
      } else {
        length = 0;
      }
      buffer = newBuffer;
    }
  }

  @Override
  public void swap(final CharBuffer other) {
    final char[] tempBuffer = other.buffer;
    other.buffer = buffer;
    buffer = tempBuffer;
    final int tempLength = other.length;
    other.length = length;
    length = tempLength;
  }

  @Override
  public void assign(final CharBuffer value) {
    if (this != value) {
      length = 0;
      if (buffer.length < value.length) {
        // create a new buffer and abandon the old one
        buffer = new char[value.length];
      }
      arraycopy(value.buffer, 0, buffer, 0, value.length);
      length = value.length;
    }
  }

  public void assign(final String value) {
    length = 0;
    final int n = value.length();
    if (buffer.length < n) {
      // create a new buffer and abandon the old one
      buffer = new char[n];
    }
    value.getChars(0, n, buffer, 0);
    length = n;
  }

  @Override
  public CharBuffer clone() {
    final CharBuffer cloned = new CharBuffer(buffer.length);
    cloned.length = length;
    arraycopy(buffer, 0, cloned.buffer, 0, buffer.length);
    return cloned;
  }

  public char[] toArray() {
    if (length == 0) {
      return EMPTY_CHAR_ARRAY;
    } else {
      final char[] result = new char[length];
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
    final CharBuffer other = (CharBuffer) obj;
    return (length == other.length)
        && Equality.equals(buffer, 0, other.buffer, 0, length);
  }

  public boolean equals(final String str) {
    if (str == null) {
      return false;
    }
    if (length != str.length()) {
      return false;
    }
    for (int i = 0; i < length; ++i) {
      if (buffer[i] != str.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  public boolean equalsIgnoreCase(final CharBuffer other) {
    return (length == other.length)
        && Equality.equalsIgnoreCase(buffer, 0, other.buffer, 0, length);
  }

  public boolean equalsIgnoreCase(final String str) {
    if (str == null) {
      return false;
    }
    if (length != str.length()) {
      return false;
    }
    for (int i = 0; i < length; ++i) {
      final char ch1 = buffer[i];
      final char ch2 = str.charAt(i);
      if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int compareTo(final CharBuffer other) {
    if (other == null) {
      return + 1;
    } else if (this == other) {
      return 0;
    } else {
      return Comparison.compare(buffer, length, other.buffer, other.length);
    }
  }

  public int compareTo(final String str) {
    if (str == null) {
      return + 1;
    } else {
      final int strlen = str.length();
      final int n = (length < strlen ? length : strlen);
      for (int i = 0; i < n; ++i) {
        final char x = buffer[i];
        final char y = str.charAt(i);
        if (x != y) {
          return (x - y);
        }
      }
      return (length - strlen);
    }
  }

  public int compareToIgnoreCase(final CharBuffer other) {
    if (other == null) {
      return + 1;
    } else if (this == other) {
      return 0;
    } else {
      return Comparison.compareIgnoreCase(buffer, length, other.buffer,
          other.length);
    }
  }

  public int compareToIgnoreCase(final String str) {
    if (str == null) {
      return + 1;
    } else {
      final int strlen = str.length();
      final int n = (length < strlen ? length : strlen);
      for (int i = 0; i < n; ++i) {
        final char x = Character.toLowerCase(buffer[i]);
        final char y = Character.toLowerCase(str.charAt(i));
        if (x != y) {
          return (x - y);
        }
      }
      return (length - strlen);
    }
  }

  /**
   * Returns a substring of this buffer. The substring begins at the specified
   * {@code beginIndex} and extends to the character at current
   * {@code endIndex - 1}.
   *
   * @param beginIndex
   *          the beginning current, inclusive.
   * @param endIndex
   *          the ending current, exclusive.
   * @return the specified substring.
   * @exception StringIndexOutOfBoundsException
   *              if the {@code beginIndex} is negative, or
   *              {@code endIndex} is larger than the length of this
   *              buffer, or {@code beginIndex} is larger than
   *              {@code endIndex}.
   */
  public String substring(final int beginIndex, final int endIndex) {
    return new String(buffer, beginIndex, endIndex - beginIndex);
  }

  /**
   * Returns a substring of this buffer with leading and trailing whitespace
   * omitted. The substring begins with the first non-whitespace character from
   * {@code beginIndex} and extends to the last non-whitespace character
   * with the current lesser than {@code endIndex}.
   *
   * @param beginIndex
   *          the beginning current, inclusive.
   * @param endIndex
   *          the ending current, exclusive.
   * @return the specified substring.
   * @exception IndexOutOfBoundsException
   *              if the {@code beginIndex} is negative, or
   *              {@code endIndex} is larger than the length of this
   *              buffer, or {@code beginIndex} is larger than
   *              {@code endIndex}.
   */
  public String substringTrimmed(final int beginIndex, final int endIndex) {
    if (beginIndex < 0) {
      throw new IndexOutOfBoundsException("Negative beginIndex: " + beginIndex);
    }
    if (endIndex > length) {
      throw new IndexOutOfBoundsException("endIndex: " + endIndex
          + " > length: " + length);
    }
    if (beginIndex > endIndex) {
      throw new IndexOutOfBoundsException("beginIndex: " + beginIndex
          + " > endIndex: " + endIndex);
    }
    int begin = beginIndex;
    int end = endIndex;
    while ((begin < end) && CharUtils.isBlank(buffer[begin])) {
      begin++;
    }
    while ((end > begin) && CharUtils.isBlank(buffer[end - 1])) {
      end--;
    }
    return new String(buffer, begin, end - begin);
  }

  /**
   * Returns a substring of this buffer with leading and trailing ASCII
   * whitespace omitted. The substring begins with the first non-whitespace
   * character from {@code beginIndex} and extends to the last
   * non-whitespace character with the current lesser than {@code endIndex}
   * .
   *
   * @param beginIndex
   *          the beginning current, inclusive.
   * @param endIndex
   *          the ending current, exclusive.
   * @return the specified substring.
   * @exception IndexOutOfBoundsException
   *              if the {@code beginIndex} is negative, or
   *              {@code endIndex} is larger than the length of this
   *              buffer, or {@code beginIndex} is larger than
   *              {@code endIndex}.
   */
  public String substringTrimmedAscii(final int beginIndex, final int endIndex) {
    if (beginIndex < 0) {
      throw new IndexOutOfBoundsException("Negative beginIndex: " + beginIndex);
    }
    if (endIndex > length) {
      throw new IndexOutOfBoundsException("endIndex: " + endIndex
          + " > length: " + length);
    }
    if (beginIndex > endIndex) {
      throw new IndexOutOfBoundsException("beginIndex: " + beginIndex
          + " > endIndex: " + endIndex);
    }
    int begin = beginIndex;
    int end = endIndex;
    while ((begin < end) && Ascii.isWhitespace(buffer[begin])) {
      begin++;
    }
    while ((end > begin) && Ascii.isWhitespace(buffer[end - 1])) {
      end--;
    }
    return new String(buffer, begin, end - begin);
  }

  @Override
  public String toString() {
    return new String(buffer, 0, length);
  }
}
