////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import ltd.qubit.commons.datastructure.list.primitive.BooleanList;
import ltd.qubit.commons.datastructure.list.primitive.ByteList;
import ltd.qubit.commons.datastructure.list.primitive.CharList;
import ltd.qubit.commons.datastructure.list.primitive.DoubleList;
import ltd.qubit.commons.datastructure.list.primitive.FloatList;
import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.LongList;
import ltd.qubit.commons.datastructure.list.primitive.ShortList;
import ltd.qubit.commons.datastructure.list.primitive.impl.BooleanArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ByteArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.CharArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.DoubleArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.FloatArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.LongArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ShortArrayList;
import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.BinarySerializer;
import ltd.qubit.commons.io.serialize.NoBinarySerializerRegisteredException;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.StringUtils;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Provides functions to read data from the {@link InputStream} object.
 *
 * @author Haixing Hu
 */
public final class InputUtils {

  private static final int BYTE_MASK = 0xFF;
  private static final int BYTE_SHIFT_1 = 8;
  private static final int BYTE_SHIFT_2 = 2 * BYTE_SHIFT_1;
  private static final int BYTE_SHIFT_3 = 3 * BYTE_SHIFT_1;
  private static final int BYTE_SHIFT_4 = 4 * BYTE_SHIFT_1;
  private static final int BYTE_SHIFT_5 = 5 * BYTE_SHIFT_1;
  private static final int BYTE_SHIFT_6 = 6 * BYTE_SHIFT_1;
  private static final int BYTE_SHIFT_7 = 7 * BYTE_SHIFT_1;
  private static final int SHORT_BYTES = 2;
  private static final int INT_BYTES = 4;
  private static final int LONG_BYTES = 8;
  private static final int SIGN_MASK = 0x80;
  private static final int VAR_SHIFT = 7;
  private static final int VAR_MASK = 0x7F;
  private static final int VAR_SHORT_LAST_BYTE_MAX = 0x01;
  private static final int VAR_INT_LAST_BYTE_MAX = 0x07;
  private static final int VAR_LONG_LAST_BYTE_MAX = 0x7F;

  /**
   * Reads specified number of bytes from the input.
   *
   * <p>This method blocks until one of the following conditions occurs:</p>
   *
   * <ul>
   * <li>{@code len} bytes of input data are available, in which case a
   * normal return is made.</li>
   * <li>End of file is detected, in which case an {@code EOFException} is
   * thrown.</li>
   * <li>An I/O error occurs, in which case an {@code IOException} other
   * than {@code EOFException} is thrown.</li>
   * </ul>
   *
   * <p>If {@code len} is zero, then no bytes are read. Otherwise, the first
   * byte read is stored into element {@code buffer[off]}, the next one
   * into {@code buffer[off+1]}, and so on. The number of bytes read is
   * always equal to {@code len}.</p>
   *
   * @param in
   *     the input source where to read the data.
   * @param buf
   *     the byte array where to store the bytes read from the input.
   * @param off
   *     the start offset in array {@code buffer} at which the data is written.
   * @param len
   *     the number of bytes need to read.
   * @throws NullPointerException
   *     If {@code buffer} is null.
   * @throws IndexOutOfBoundsException
   *     If {@code off} is negative, {@code len} is negative, or {@code len} is
   *     greater than {@code buff.length - off}.
   * @throws EOFException
   *     if this input reaches the end before reading {@code len} bytes.
   * @throws IOException
   *     if an I/O error occurs.
   */
  public static void readFully(final InputStream in, final byte[] buf,
      final int off, final int len) throws IOException {
    int n = 0;
    while (n < len) {
      final int count = in.read(buf, off + n, len - n);
      if (count < 0) {
        throw new EOFException();
      }
      n += count;
    }
  }

  public static boolean readNullMark(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (ch != 0);
  }

  /**
   * Reads a {@code boolean} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code boolean} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code boolean}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static boolean readBoolean(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (ch != 0);
  }

  /**
   * Reads a {@code Boolean} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Boolean} object to be read could be a null
   *     value; otherwise, if the {@code Boolean} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Boolean} object read from the input, which could be {@code
   *     null}.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Boolean}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Boolean readBooleanObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readBoolean(in);
  }

  /**
   * Reads a {@code char} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code char} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code char}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static char readChar(final InputStream in) throws IOException {
    return (char) readVarShort(in);
  }

  /**
   * Reads a {@code Character} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Character} object to be read could be a null
   *     value; otherwise, if the {@code Character} object read from the input
   *     is null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Character} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Character}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Character readCharObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readChar(in);
  }

  /**
   * Reads a {@code byte} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code byte} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code byte}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static byte readByte(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (byte) (ch);
  }

  /**
   * Reads a {@code Byte} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Byte} object to be read could be a null
   *     value; otherwise, if the {@code Byte} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Byte} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Byte}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Byte readByteObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readByte(in);
  }

  /**
   * Reads a {@code short} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code short} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static short readShort(final InputStream in) throws IOException {
    final int ch1 = in.read();
    final int ch2 = in.read();
    if ((ch1 | ch2) < 0) {
      throw new EOFException();
    }
    return (short) (((ch1 & BYTE_MASK) << BYTE_SHIFT_1) | (ch2 & BYTE_MASK));
  }

  /**
   * Reads a {@code Short} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Short} object to be read could be a null
   *     value; otherwise, if the {@code Short} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Short} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Short}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Short readShortObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readShort(in);
  }

  /**
   * Reads a variant length encoded {@code short} value from the input.
   *
   * <p>A variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read.
   * The low-order seven bits are appended as increasingly more significant bits
   * in the resulting integer value. Thus values from zero to 127 may be stored
   * in a single byte, values from 128 to 16,383 may be stored in two bytes, and
   * so on. This provides compression while still being efficient to decode.
   *
   * <p>This function will read between one and three bytes. Smaller values
   * take fewer bytes. Negative numbers are not supported.
   *
   * @param in
   *     the input source where to read the data.
   * @return a variant length encoded {@code short} value read from the input.
   *     Note that the {@code short} value encoded is always a positive value,
   *     therefore the returned value is always between 0 and 32767.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     value.
   * @throws InvalidFormatException
   *     if the {@code short} value is not correctly encoded.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static short readVarShort(final InputStream in) throws IOException {
    int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    int value = ch & VAR_MASK;
    for (int shift = VAR_SHIFT; (ch & SIGN_MASK) != 0; shift += VAR_SHIFT) {
      ch = in.read();
      if (ch < 0) {
        throw new EOFException();
      }
      if (shift == SHORT_BYTES * VAR_SHIFT) {
        // A variant length short is encoded in at most three bytes,
        // so shift == 14 indicate that this is the last possible byte
        // of a variant length encoded short. Since the maximum positive
        // short has 15 bits of 1s, the last byte read should be less
        // equal than 0x01 (or one 1s in binary form).
        if (ch > VAR_SHORT_LAST_BYTE_MAX) {
          throw new InvalidFormatException(
              "Malformed variant length encoded integer.");
        }
      }
      value |= (ch & VAR_MASK) << shift;
    }
    assert (value <= Short.MAX_VALUE);
    return (short) value;
  }

  /**
   * Reads a variable length {@code Short} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Short} object to be read could be a null
   *     value; otherwise, if the {@code Short} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Short} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Short}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Short readVarShortObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readVarShort(in);
  }

  /**
   * Reads a {@code int} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code int} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static int readInt(final InputStream in) throws IOException {
    final int ch1 = in.read();
    final int ch2 = in.read();
    final int ch3 = in.read();
    final int ch4 = in.read();
    if ((ch1 | ch2 | ch3 | ch4) < 0) {
      throw new EOFException();
    }
    return (((ch1 & BYTE_MASK) << BYTE_SHIFT_3)
        | ((ch2 & BYTE_MASK) << BYTE_SHIFT_2)
        | ((ch3 & BYTE_MASK) << BYTE_SHIFT_1)
        | (ch4 & BYTE_MASK));
  }

  /**
   * Reads a {@code Integer} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Integer} object to be read could be a null
   *     value; otherwise, if the {@code Integer} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Integer} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Integer}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Integer readIntObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readInt(in);
  }

  /**
   * Reads a variant length encoded {@code int} value from the input.
   *
   * <p>A variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read.
   * The low-order seven bits are appended as increasingly more significant bits
   * in the resulting integer value. Thus values from zero to 127 may be stored
   * in a single byte, values from 128 to 16,383 may be stored in two bytes, and
   * so on. This provides compression while still being efficient to
   * decode.</p>
   *
   * <p>This function will read between one and five bytes. Smaller values take
   * fewer bytes. Negative numbers are not supported.</p>
   *
   * @param in
   *     the input source where to read the data.
   * @return a variant length encoded {@code int} value read from the input.
   *     Note that the {@code int} value encoded is always a positive value,
   *     therefore the returned value is always between 0 and 2147483647.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int}
   *     value.
   * @throws InvalidFormatException
   *     if the {@code int} value is not correctly encoded.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static int readVarInt(final InputStream in) throws IOException {
    int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    int value = ch & VAR_MASK;
    for (int shift = VAR_SHIFT; (ch & SIGN_MASK) != 0; shift += VAR_SHIFT) {
      ch = in.read();
      if (ch < 0) {
        throw new EOFException();
      }
      if (shift == INT_BYTES * VAR_SHIFT) {
        // A variant length int is encoded in at most five bytes,
        // so shift == 28 indicate that this is the last possible byte
        // of a variant length encoded int. Since the maximum positive
        // int has 31 bits of 1s, the last byte read should be less
        // equal than 0x07 (or three 1s in binary form).
        if (ch > VAR_INT_LAST_BYTE_MAX) {
          throw new InvalidFormatException(
              "Malformed variant length encoded integer.");
        }
      }
      value |= (ch & VAR_MASK) << shift;
    }
    return value;
  }

  /**
   * Reads a variable length {@code Integer} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Integer} object to be read could be a null
   *     value; otherwise, if the {@code Integer} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Integer} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Integer}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Integer readVarIntObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readVarInt(in);
  }

  /**
   * Reads a {@code long} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code long} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static long readLong(final InputStream in) throws IOException {
    final byte[] buffer = new byte[LONG_BYTES];
    readFully(in, buffer, 0, LONG_BYTES);
    // stop checkstyle: MagicNumberCheck
    return (((long) buffer[0] << BYTE_SHIFT_7)
        | ((long) (buffer[1] & BYTE_MASK) << BYTE_SHIFT_6)
        | ((long) (buffer[2] & BYTE_MASK) << BYTE_SHIFT_5)
        | ((long) (buffer[3] & BYTE_MASK) << BYTE_SHIFT_4)
        | ((long) (buffer[4] & BYTE_MASK) << BYTE_SHIFT_3)
        | ((long) (buffer[5] & BYTE_MASK) << BYTE_SHIFT_2)
        | ((long) (buffer[6] & BYTE_MASK) << BYTE_SHIFT_1)
        | ((buffer[7] & BYTE_MASK)));
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * Reads a {@code Long} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Long} object to be read could be a null
   *     value; otherwise, if the {@code Long} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Long} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Long}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Long readLongObject(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readLong(in);
  }

  /**
   * Reads a variant length encoded {@code long} value from the input.
   *
   * <p>A variable-length format for positive integers is defined where the
   * high-order bit of each byte indicates whether more bytes remain to be read.
   * The low-order seven bits are appended as increasingly more significant bits
   * in the resulting integer value. Thus values from zero to 127 may be stored
   * in a single byte, values from 128 to 16,383 may be stored in two bytes, and
   * so on. This provides compression while still being efficient to
   * decode.</p>
   *
   * <p>This function will read between one and nine bytes. Smaller values take
   * fewer bytes. Negative numbers are not supported.</p>
   *
   * @param in
   *     the input source where to read the data.
   * @return a variant length encoded {@code long} value read from the input.
   *     Note that the {@code long} value encoded is always a positive value,
   *     therefore the returned value is always between 0 and
   *     9223372036854775807.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     value.
   * @throws InvalidFormatException
   *     if the {@code long} value is not correctly encoded.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static long readVarLong(final InputStream in) throws IOException {
    int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    long value = ch & VAR_MASK;
    for (int shift = VAR_SHIFT; (ch & SIGN_MASK) != 0; shift += VAR_SHIFT) {
      ch = in.read();
      if (ch < 0) {
        throw new EOFException();
      }
      if (shift == LONG_BYTES * VAR_SHIFT) {
        // A variant length long is encoded in at most nine bytes,
        // so shift == 56 indicate that this is the last possible byte
        // of a variant length encoded long. Since the maximum positive
        // long has 63 bits of 1s, the last byte read should be less
        // equal than 0x7F (or seven 1s in binary form).
        if (ch > VAR_LONG_LAST_BYTE_MAX) {
          throw new InvalidFormatException(
              "Malformed variant length encoded integer.");
        }
      }
      // NEVER forget to convert the (ch & 0x7F) to long before left shifting
      value |= (long) (ch & VAR_MASK) << shift;
    }
    return value;
  }

  /**
   * Reads a variable length{@code Long} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Long} object to be read could be a null
   *     value; otherwise, if the {@code Long} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Long} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Long}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Long readVarLongObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readVarLong(in);
  }

  /**
   * Reads a {@code float} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code float} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code float}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static float readFloat(final InputStream in) throws IOException {
    final int intBits = readInt(in);
    return Float.intBitsToFloat(intBits);
  }

  /**
   * Reads a {@code Float} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Float} object to be read could be a null
   *     value; otherwise, if the {@code Float} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Float} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Float}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Float readFloatObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readFloat(in);
  }

  /**
   * Reads a {@code double} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @return a {@code double} value read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code double}
   *     value.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static double readDouble(final InputStream in) throws IOException {
    final long longBits = readLong(in);
    return Double.longBitsToDouble(longBits);
  }

  /**
   * Reads a {@code Double} object from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Double} object to be read could be a null
   *     value; otherwise, if the {@code Double} object read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Double} object read from the input.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Double}
   *     object.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Double readDoubleObject(final InputStream in,
      final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    return readDouble(in);
  }

  /**
   * Reads a {@code String} value from the input.
   *
   * <p>The {@code String} is encoded in the modified UTF-8 format, as
   * described in the {@link java.io.DataInput} interface.</p>
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code String} value to be read could be a null
   *     value; otherwise, if the {@code String} value read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code String} value read from the input. Note that it could be
   *     null if the value read from the input is a null and the argument
   *     <code>allowNull</code> is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code String}
   *     value.
   * @throws InvalidFormatException
   *     if the value read from the input is a null value and the argument
   *     {@code allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static String readString(final InputStream in,
      final boolean allowNull) throws IOException {
    // stop checkstyle: MagicNumberCheck
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int utfLen = readVarInt(in);
    if (utfLen == 0) {
      return StringUtils.EMPTY;
    }
    final byte[] byteBuffer = new byte[utfLen];
    final char[] charBuffer = new char[utfLen];
    int ch;
    int ch2;
    int ch3;
    int count = 0;
    int charCount = 0;
    readFully(in, byteBuffer, 0, utfLen);
    // optimization for ASCII string
    while (count < utfLen) {
      ch = byteBuffer[count] & BYTE_MASK;
      if (ch > 127) {
        break;
      }
      count++;
      charBuffer[charCount++] = (char) ch;
    }
    // decode the remained bytes
    while (count < utfLen) {
      ch = byteBuffer[count] & BYTE_MASK;
      switch (ch >> 4) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
          /* 0xxxxxxx */
          count++;
          charBuffer[charCount++] = (char) ch;
          break;
        case 12:
        case 13:
          /* 110x xxxx 10xx xxxx */
          count += 2;
          if (count > utfLen) {
            throw new UTFDataFormatException(
                "Malformed UTF-8 input: partial character at end. ");
          }
          ch2 = byteBuffer[count - 1];
          if ((ch2 & 0xC0) != 0x80) {
            throw new UTFDataFormatException(
                "Malformed UTF-8 input around byte " + count);
          }
          charBuffer[charCount++] = (char) (((ch & 0x1F) << 6)
              | (ch2 & 0x3F));
          break;
        case 14:
          /* 1110 xxxx 10xx xxxx 10xx xxxx */
          count += 3;
          if (count > utfLen) {
            throw new UTFDataFormatException(
                "Malformed UTF-8 input: partial character at end. ");
          }
          ch2 = byteBuffer[count - 2];
          ch3 = byteBuffer[count - 1];
          if (((ch2 & 0xC0) != 0x80) || ((ch3 & 0xC0) != 0x80)) {
            throw new UTFDataFormatException(
                "Malformed UTF-8 input around byte " + (count - 1));
          }
          charBuffer[charCount++] = (char) (((ch & 0x0F) << 12)
              | ((ch2 & 0x3F) << 6)
              | (ch3 & 0x3F));
          break;
        default:
          /* 10xx xxxx, 1111 xxxx */
          throw new UTFDataFormatException(
              "Malformed UTF-8 input around byte " + count);
      }
    }
    // the number of chars produced may be less than utfLen
    return new String(charBuffer, 0, charCount);
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * Reads a {@code Date} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Date} value to be read could be a null value;
   *     otherwise, if the {@code Date} value read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @return a {@code Date} value read from the input. Note that it could be
   *     null if the value read from the input is a null and the argument
   *     <code>allowNull</code> is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Date }
   *     value.
   * @throws InvalidFormatException
   *     if the value read from the input is a null value and the argument
   *     {@code allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Date readDate(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final long time = readLong(in);
    return new Date(time);
  }

  /**
   * Reads a {@code BigInteger} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code BigInteger} value to be read could be a null
   *     value; otherwise, if the {@code BigInteger} value read from the input
   *     is null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code BigInteger} value read from the input. Note that it could
   *     be null if the value read from the input is a null and the argument
   *     <code>allowNull</code> is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code
   *     BigInteger} value.
   * @throws InvalidFormatException
   *     if the value read from the input is a null value and the argument
   *     {@code allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static BigInteger readBigInteger(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return BigInteger.ZERO;
    } else {
      final byte[] bits = new byte[n];
      readFully(in, bits, 0, n);
      return new BigInteger(bits);
    }
  }

  /**
   * Reads a {@code BigDecimal} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code BigDecimal} value to be read could be a null
   *     value; otherwise, if the {@code BigDecimal} value read from the input
   *     is null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code BigDecimal} value read from the input. Note that it could
   *     be null if the value read from the input is a null and the argument
   *     <code>allowNull</code> is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code
   *     BigDecimal} value.
   * @throws InvalidFormatException
   *     if the value read from the input is a null value and the argument
   *     {@code allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static BigDecimal readBigDecimal(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return BigDecimal.ZERO;
    } else {
      final byte[] bits = new byte[n];
      readFully(in, bits, 0, n);
      final BigInteger unscaledValue = new BigInteger(bits);
      final int scale = readInt(in);
      return new BigDecimal(unscaledValue, scale);
    }
  }

  /**
   * Reads a {@code Class} value from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Class} value to be read could be a null
   *     value; otherwise, if the {@code Class} value read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @return a {@code Class} value read from the input. Note that it could be
   *     null if the value read from the input is a null and the argument
   *     <code>allowNull</code> is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Class }
   *     value.
   * @throws InvalidFormatException
   *     if the value read from the input is a null value and the argument
   *     {@code allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Class<?> readClass(final InputStream in, final boolean allowNull)
      throws IOException {
    final String className = readString(in, true);
    if (className == null) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final Class<?> result;
    try {
      result = Class.forName(className);
    } catch (final ClassNotFoundException e) {
      throw new InvalidFormatException(e);
    }
    return result;
  }

  /**
   * Reads a {@code Enum} value from the input.
   *
   * @param <T>
   *     the enumeration type.
   * @param enumClass
   *     the class object of the {@code Enum} type.
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code Enum} value to be read could be a null value;
   *     otherwise, if the {@code Enum} value read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @return a {@code Enum} value read from the input. Note that it could be
   *     null if the value read from the input is a null and the argument
   *     <code>allowNull</code> is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code Enum }
   *     value.
   * @throws InvalidFormatException
   *     if the value read from the input is a null value and the argument
   *     {@code allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static <T extends Enum<T>> T readEnum(final Class<T> enumClass,
      final InputStream in, final boolean allowNull) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int ordinal = readVarInt(in);
    final T[] enumValues = enumClass.getEnumConstants();
    if ((ordinal >= 0) && (ordinal < enumValues.length)) {
      return enumValues[ordinal];
    } else {
      throw new InvalidFormatException("Invalid enum ordinal: " + ordinal);
    }
  }

  /**
   * Reads a {@code boolean} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code boolean} array to be read could be a null
   *     value; otherwise, if the {@code boolean} array read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code boolean} array used to store the result. It could be null.
   * @return a {@code boolean} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code boolean} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code boolean}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static boolean[] readBooleanArray(final InputStream in,
      final boolean allowNull, @Nullable final boolean[] buffer)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else {
      final boolean[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new boolean[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readBoolean(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code boolean} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code boolean} list to be read could be a null
   *     value; otherwise, if the {@code boolean} list read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code boolean} list used to store the result. It could be null.
   * @return a {@code boolean} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code boolean} is created to
   *     store the returned values. Note that the returned list may be null if
   *     {@code allowNull} is true and the list read from the input is a null
   *     value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code boolean}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static BooleanList readBooleanList(final InputStream in,
      final boolean allowNull, @Nullable final BooleanList buffer)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new BooleanArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final BooleanList result;
      if (buffer == null) {
        result = new BooleanArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final boolean value = readBoolean(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code boolean} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code boolean} set to be read could be a null
   *     value; otherwise, if the {@code boolean} set read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code boolean} set used to store the result. It could be null.
   * @return a {@code boolean} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code boolean} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code boolean}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Boolean> readBooleanSet(final InputStream in,
      final boolean allowNull, @Nullable final Set<Boolean> buffer)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Boolean> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final boolean value = readBoolean(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code char} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code char} array to be read could be a null value;
   *     otherwise, if the {@code char} array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code char} array used to store the result. It could be null.
   * @return a {@code char} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code char} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code char}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static char[] readCharArray(final InputStream in, final boolean allowNull,
      @Nullable final char[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      final char[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new char[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readChar(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code char} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code char} list to be read could be a null value;
   *     otherwise, if the {@code char} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code char} list used to store the result. It could be null.
   * @return a {@code char} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code char} is created to store
   *     the returned values. Note that the returned list may be null if {@code
   *     allowNull} is true and the list read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code char}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static CharList readCharList(final InputStream in,
      final boolean allowNull, @Nullable final CharList buffer)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new CharArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final CharList result;
      if (buffer == null) {
        result = new CharArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final char value = readChar(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code char} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code char} set to be read could be a null value;
   *     otherwise, if the {@code char} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code char} set used to store the result. It could be null.
   * @return a {@code char} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code char} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code char}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Character> readCharSet(final InputStream in,
      final boolean allowNull, @Nullable final Set<Character> buffer)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Character> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final char value = readChar(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code byte} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code byte} array to be read could be a null value;
   *     otherwise, if the {@code byte} array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code byte} array used to store the result. It could be null.
   * @return a {@code byte} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code byte} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code byte}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static byte[] readByteArray(final InputStream in, final boolean allowNull,
      @Nullable final byte[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      final byte[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new byte[n];
      } else {
        result = buffer;
      }
      readFully(in, result, 0, n);
      return result;
    }
  }

  /**
   * Reads a {@code byte} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code byte} list to be read could be a null value;
   *     otherwise, if the {@code byte} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code byte} list used to store the result. It could be null.
   * @return a {@code byte} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code byte} is created to store
   *     the returned values. Note that the returned list may be null if {@code
   *     allowNull} is true and the list read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code byte}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static ByteList readByteList(final InputStream in, final boolean allowNull,
      @Nullable final ByteList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new ByteArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final ByteList result;
      if (buffer == null) {
        result = new ByteArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final byte value = readByte(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code byte} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code byte} set to be read could be a null value;
   *     otherwise, if the {@code byte} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code byte} set used to store the result. It could be null.
   * @return a {@code byte} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code byte} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code byte}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Byte> readByteSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Byte> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Byte> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final byte value = readByte(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code short} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code short} array to be read could be a null
   *     value; otherwise, if the {@code short} array read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code short} array used to store the result. It could be null.
   * @return a {@code short} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code short} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static short[] readShortArray(final InputStream in, final boolean allowNull,
      @Nullable final short[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      final short[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new short[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readShort(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code short} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code short} list to be read could be a null value;
   *     otherwise, if the {@code short} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code short} list used to store the result. It could be null.
   * @return a {@code short} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code short} is created to store
   *     the returned values. Note that the returned list may be null if {@code
   *     allowNull} is true and the list read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static ShortList readShortList(final InputStream in, final boolean allowNull,
      @Nullable final ShortList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new ShortArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final ShortList result;
      if (buffer == null) {
        result = new ShortArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code short} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code short} set to be read could be a null value;
   *     otherwise, if the {@code short} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code short} set used to store the result. It could be null.
   * @return a {@code short} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code short} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Short> readShortSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Short> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Short> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code short} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code short} array to be read could be a null
   *     value; otherwise, if the {@code short} array read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code short} array used to store the result. It could be null.
   * @return a variable length encoded {@code short} array read from the input.
   *     If the length of the result array is of the same as the length of the
   *     argument {@code result}, the returned values are stored in the argument
   *     {@code result}; otherwise, a new {@code short} array is created to
   *     store the returned values. Note that the returned array may be null if
   *     {@code allowNull} is true and the array read from the input is a null
   *     value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static short[] readVarShortArray(final InputStream in, final boolean allowNull,
      @Nullable final short[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      final short[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new short[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readVarShort(in);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code short} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code short} list to be read could be a null value;
   *     otherwise, if the {@code short} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code short} list used to store the result. It could be null.
   * @return a variable length encoded {@code short} list read from the input.
   *     If the argument {@code result} is not null, and the list to be read is
   *     not a null value, the argument {@code result} is cleared and the
   *     returned values are stored in it; otherwise, a new array list of {@code
   *     short} is created to store the returned values. Note that the returned
   *     list may be null if {@code allowNull} is true and the list read from
   *     the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static ShortList readVarShortList(final InputStream in, final boolean allowNull,
      @Nullable final ShortList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new ShortArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final ShortList result;
      if (buffer == null) {
        result = new ShortArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readVarShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code short} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code short} set to be read could be a null value;
   *     otherwise, if the {@code short} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code short} set used to store the result. It could be null.
   * @return a variable length encoded {@code short} set read from the input. If
   *     the argument {@code result} is not null, and the set to be read is not
   *     a null value, the argument {@code result} is cleared and the returned
   *     values are stored in it; otherwise, a new hash set of {@code short} is
   *     created to store the returned values. Note that the returned set may be
   *     null if {@code allowNull} is true and the set read from the input is a
   *     null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code short}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Short> readVarShortSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Short> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Short> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final short value = readVarShort(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code int} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code int} array to be read could be a null value;
   *     otherwise, if the {@code int} array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code int} array used to store the result. It could be null.
   * @return a {@code int} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code int} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static int[] readIntArray(final InputStream in, final boolean allowNull,
      @Nullable final int[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      final int[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new int[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readInt(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code int} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code int} list to be read could be a null value;
   *     otherwise, if the {@code int} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code int} list used to store the result. It could be null.
   * @return a {@code int} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code int} is created to store
   *     the returned values. Note that the returned list may be null if {@code
   *     allowNull} is true and the list read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static IntList readIntList(final InputStream in, final boolean allowNull,
      @Nullable final IntList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new IntArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final IntList result;
      if (buffer == null) {
        result = new IntArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code int} set from an input stream.
   *
   * @param in
   *     the input stream.
   * @param allowNull
   *     if it is true, the {@code int} set to be read could be a null value;
   *     otherwise, if the {@code int} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code int} set used to store the result. It could be null.
   * @return a {@code int} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code int} is created to store the
   *     returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int} set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Integer> readIntSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Integer> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Integer> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code int} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code int} array to be read could be a null value;
   *     otherwise, if the {@code int} array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code int} array used to store the result. It could be null.
   * @return a variable length encoded {@code int} array read from the input. If
   *     the length of the result array is of the same as the length of the
   *     argument {@code result}, the returned values are stored in the argument
   *     {@code result}; otherwise, a new {@code int} array is created to store
   *     the returned values. Note that the returned array may be null if {@code
   *     allowNull} is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static int[] readVarIntArray(final InputStream in, final boolean allowNull,
      @Nullable final int[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      final int[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new int[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readVarInt(in);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code int} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code int} list to be read could be a null value;
   *     otherwise, if the {@code int} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code int} list used to store the result. It could be null.
   * @return a variable length encoded {@code int} list read from the input. If
   *     the argument {@code result} is not null, and the list to be read is not
   *     a null value, the argument {@code result} is cleared and the returned
   *     values are stored in it; otherwise, a new array list of {@code int} is
   *     created to store the returned values. Note that the returned list may
   *     be null if {@code allowNull} is true and the list read from the input
   *     is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static IntList readVarIntList(final InputStream in, final boolean allowNull,
      @Nullable final IntList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new IntArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final IntList result;
      if (buffer == null) {
        result = new IntArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readVarInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code int} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code int} set to be read could be a null value;
   *     otherwise, if the {@code int} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code int} set used to store the result. It could be null.
   * @return a variable length encoded {@code int} set read from the input. If
   *     the argument {@code result} is not null, and the set to be read is not
   *     a null value, the argument {@code result} is cleared and the returned
   *     values are stored in it; otherwise, a new hash set of {@code int} is
   *     created to store the returned values. Note that the returned set may be
   *     null if {@code allowNull} is true and the set read from the input is a
   *     null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code int} set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Integer> readVarIntSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Integer> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Integer> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final int value = readVarInt(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code char} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code long} array to be read could be a null value;
   *     otherwise, if the {@code long} array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code long} array used to store the result. It could be null.
   * @return a {@code long} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code long} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static long[] readLongArray(final InputStream in, final boolean allowNull,
      @Nullable final long[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      final long[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new long[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readLong(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code long} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code long} list to be read could be a null value;
   *     otherwise, if the {@code long} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code long} list used to store the result. It could be null.
   * @return a {@code long} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code long} is created to store
   *     the returned values. Note that the returned list may be null if {@code
   *     allowNull} is true and the list read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static LongList readLongList(final InputStream in, final boolean allowNull,
      @Nullable final LongList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new LongArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final LongList result;
      if (buffer == null) {
        result = new LongArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code long} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code long} set to be read could be a null value;
   *     otherwise, if the {@code long} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code long} set used to store the result. It could be null.
   * @return a {@code long} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code long} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Long> readLongSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Long> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Long> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code char} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code long} array to be read could be a null value;
   *     otherwise, if the {@code long} array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code long} array used to store the result. It could be null.
   * @return a variable length encoded {@code long} array read from the input.
   *     If the length of the result array is of the same as the length of the
   *     argument {@code result}, the returned values are stored in the argument
   *     {@code result}; otherwise, a new {@code long} array is created to store
   *     the returned values. Note that the returned array may be null if {@code
   *     allowNull} is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static long[] readVarLongArray(final InputStream in, final boolean allowNull,
      @Nullable final long[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      final long[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new long[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readVarLong(in);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code long} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code long} list to be read could be a null value;
   *     otherwise, if the {@code long} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code long} list used to store the result. It could be null.
   * @return a variable length encoded {@code long} list read from the input. If
   *     the argument {@code result} is not null, and the list to be read is not
   *     a null value, the argument {@code result} is cleared and the returned
   *     values are stored in it; otherwise, a new array list of {@code long} is
   *     created to store the returned values. Note that the returned list may
   *     be null if {@code allowNull} is true and the list read from the input
   *     is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static LongList readVarLongList(final InputStream in, final boolean allowNull,
      @Nullable final LongList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new LongArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final LongList result;
      if (buffer == null) {
        result = new LongArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readVarLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a variable length encoded {@code long} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code long} set to be read could be a null value;
   *     otherwise, if the {@code long} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code long} set used to store the result. It could be null.
   * @return a variable length encoded {@code long} set read from the input. If
   *     the argument {@code result} is not null, and the set to be read is not
   *     a null value, the argument {@code result} is cleared and the returned
   *     values are stored in it; otherwise, a new hash set of {@code long} is
   *     created to store the returned values. Note that the returned set may be
   *     null if {@code allowNull} is true and the set read from the input is a
   *     null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code long}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false; or a variable length encoded {@code short} value
   *     has invalid format.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Long> readVarLongSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Long> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Long> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final long value = readVarLong(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code float} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code float} array to be read could be a null
   *     value; otherwise, if the {@code float} array read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code float} array used to store the result. It could be null.
   * @return a {@code float} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code float} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code float}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static float[] readFloatArray(final InputStream in, final boolean allowNull,
      @Nullable final float[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else {
      final float[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new float[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readFloat(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code float} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code float} list to be read could be a null value;
   *     otherwise, if the {@code float} list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code float} list used to store the result. It could be null.
   * @return a {@code float} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code float} is created to store
   *     the returned values. Note that the returned list may be null if {@code
   *     allowNull} is true and the list read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code float}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static FloatList readFloatList(final InputStream in, final boolean allowNull,
      @Nullable final FloatList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new FloatArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final FloatList result;
      if (buffer == null) {
        result = new FloatArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final float value = readFloat(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code float} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code float} set to be read could be a null value;
   *     otherwise, if the {@code float} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code float} set used to store the result. It could be null.
   * @return a {@code float} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code float} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code float}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Float> readFloatSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Float> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Float> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final float value = readFloat(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code double} array from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code double} array to be read could be a null
   *     value; otherwise, if the {@code double} array read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code double} array used to store the result. It could be null.
   * @return a {@code double} array read from the input. If the length of the
   *     result array is of the same as the length of the argument {@code
   *     result}, the returned values are stored in the argument {@code result};
   *     otherwise, a new {@code double} array is created to store the returned
   *     values. Note that the returned array may be null if {@code allowNull}
   *     is true and the array read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code double}
   *     array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static double[] readDoubleArray(final InputStream in, final boolean allowNull,
      @Nullable final double[] buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else {
      final double[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = new double[n];
      } else {
        result = buffer;
      }
      for (int i = 0; i < n; ++i) {
        result[i] = readDouble(in);
      }
      return result;
    }
  }

  /**
   * Reads a {@code double} list from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code double} list to be read could be a null
   *     value; otherwise, if the {@code double} list read from the input is
   *     null, an {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code double} list used to store the result. It could be null.
   * @return a {@code double} list read from the input. If the argument {@code
   *     result} is not null, and the list to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new array list of {@code double} is created to
   *     store the returned values. Note that the returned list may be null if
   *     {@code allowNull} is true and the list read from the input is a null
   *     value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code double}
   *     list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static DoubleList readDoubleList(final InputStream in, final boolean allowNull,
      @Nullable final DoubleList buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new DoubleArrayList();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final DoubleList result;
      if (buffer == null) {
        result = new DoubleArrayList(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final double value = readDouble(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads a {@code double} set from the input.
   *
   * @param in
   *     the input source where to read the data.
   * @param allowNull
   *     if it is true, the {@code double} set to be read could be a null value;
   *     otherwise, if the {@code double} set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a {@code double} set used to store the result. It could be null.
   * @return a {@code double} set read from the input. If the argument {@code
   *     result} is not null, and the set to be read is not a null value, the
   *     argument {@code result} is cleared and the returned values are stored
   *     in it; otherwise, a new hash set of {@code double} is created to store
   *     the returned values. Note that the returned set may be null if {@code
   *     allowNull} is true and the set read from the input is a null value.
   * @throws EOFException
   *     if the input reaches the end before reading the whole {@code double}
   *     set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNull} is false.
   * @throws IOException
   *     if any I/O error occurs.
   */
  public static Set<Double> readDoubleSet(final InputStream in, final boolean allowNull,
      @Nullable final Set<Double> buffer) throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<Double> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      for (int i = 0; i < n; ++i) {
        final double value = readDouble(in);
        result.add(value);
      }
      return result;
    }
  }

  /**
   * Reads an object from the input.
   *
   * @param <T>
   *     The type of the class. The binary serializer of the class {@code T}
   *     must have already been registered.
   * @param objClass
   *     The class of objects to be deserialized.
   * @param in
   *     A binary input stream. Note that this function does NOT close the input
   *     stream.
   * @param allowNull
   *     Indicates whether to allowed the serialized object to be null.
   * @return The deserialized object.
   * @throws IOException
   *     If any I/O error occurred.
   */
  @SuppressWarnings("unchecked")
  public static <T> T readObject(final Class<T> objClass, final InputStream in,
      final boolean allowNull) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(objClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(objClass);
    }
    try {
      return (T) serializer.deserialize(in, allowNull);
    } catch (final ClassCastException e) {
      throw new SerializationException(e);
    }
  }

  /**
   * Reads an array of objects from the input stream.
   *
   * @param <T>
   *     the type of the elements of the array to be read. The binary serializer
   *     of the class {@code T} must have already been registered.
   * @param valueClass
   *     the class of the elements of the array to be read.
   * @param in
   *     a binary input stream.
   * @param allowNullArray
   *     if it is true, the array to be read could be null; otherwise, if the
   *     array read from the input is null, an {@code InvalidFormatException}
   *     will be thrown.
   * @param allowNullValue
   *     if it is true, the elements in the array to be read could be null;
   *     otherwise, if any element of the array read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     the object array used to store the results, or {@code null} if not
   *     specified.
   * @return an array of objects read from the input. The returned array may be
   *     null if {@code allowNullArray} is true, and the element in the returned
   *     array may be null if {@code allowNullValue} is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole array.
   * @throws InvalidFormatException
   *     if the array read from the input is null, while the argument {@code
   *     allowNullArray} is false; or any element in the array read from the
   *     input is null, while the argument {@code allowNullValue} is false.
   * @throws IOException
   *     if any I/O other error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] readArray(final Class<T> valueClass, final InputStream in,
      final boolean allowNullArray, final boolean allowNullValue,
      @Nullable final T[] buffer)
      throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullArray) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if ((buffer == null) || (buffer.length != 0)) {
        return (T[]) Array.newInstance(valueClass, 0);
      } else {
        return buffer;
      }
    } else {
      final T[] result;
      if ((buffer == null) || (buffer.length != n)) {
        result = (T[]) Array.newInstance(valueClass, n);
      } else {
        result = buffer;
      }
      try {
        for (int i = 0; i < n; ++i) {
          result[i] = (T) serializer.deserialize(in, allowNullValue);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a list of objects from the input stream.
   *
   * @param <T>
   *     the type of the elements of the list to be read. The binary serializer
   *     of the class {@code T} must have already been registered.
   * @param valueClass
   *     the class of the elements of the list to be read.
   * @param in
   *     a binary input stream.
   * @param allowNullList
   *     if it is true, the list to be read could be null; otherwise, if the
   *     list read from the input is null, an {@code InvalidFormatException}
   *     will be thrown.
   * @param allowNullValue
   *     if it is true, the elements in the list to be read could be null;
   *     otherwise, if any element of the list read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a list used to store the result. It could be null.
   * @return a list of objects read from the input. The returned list may be
   *     null if {@code allowNullList} is true, and the element in the returned
   *     list may be null if {@code allowNullValue} is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole list.
   * @throws InvalidFormatException
   *     if the list read from the input is null, while the argument {@code
   *     allowNullList} is false; or any element in the list read from the input
   *     is null, while the argument {@code allowNullValue} is false.
   * @throws IOException
   *     if any I/O other error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> readList(final Class<T> valueClass,
      final InputStream in, final boolean allowNullList,
      final boolean allowNullValue, @Nullable final List<T> buffer)
      throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullList) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new ArrayList<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final List<T> result;
      if (buffer == null) {
        result = new ArrayList<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          final T value = (T) serializer.deserialize(in, allowNullValue);
          result.add(value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a set of objects from the input stream.
   *
   * @param <T>
   *     the type of the elements of the set to be read. The binary serializer
   *     of the class {@code T} must have already been registered.
   * @param valueClass
   *     the class of the elements of the set to be read.
   * @param in
   *     a binary input stream.
   * @param allowNullSet
   *     if it is true, the set to be read could be null; otherwise, if the set
   *     read from the input is null, an {@code InvalidFormatException} will be
   *     thrown.
   * @param allowNullValue
   *     if it is true, the elements in the set to be read could be null;
   *     otherwise, if any element of the set read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a set used to store the result. It could be null.
   * @return a set of objects read from the input. The returned set may be null
   *     if {@code allowNullList} is true, and the element in the returned set
   *     may be null if {@code allowNullValue} is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole set.
   * @throws InvalidFormatException
   *     if the set read from the input is null, while the argument {@code
   *     allowNullSet} is false; or any element in the set read from the input
   *     is null, while the argument {@code allowNullValue} is false.
   * @throws IOException
   *     if any I/O other error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <T> Set<T> readSet(final Class<T> valueClass,
      final InputStream in, final boolean allowNullSet,
      final boolean allowNullValue, @Nullable final Set<T> buffer) throws IOException {
    final BinarySerializer serializer = BinarySerialization.getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullSet) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer == null) {
        return new HashSet<>();
      } else {
        buffer.clear();
        return buffer;
      }
    } else {
      final Set<T> result;
      if (buffer == null) {
        result = new HashSet<>(n);
      } else {
        result = buffer;
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          final T value = (T) serializer.deserialize(in, allowNullValue);
          result.add(value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a map of objects from the input stream.
   *
   * @param <K>
   *     the type of the keys of the map to be read. The binary serializer of
   *     the class {@code K} must have already been registered.
   * @param <V>
   *     the type of the values of the map to be read. The binary serializer of
   *     the class {@code V} must have already been registered.
   * @param keyClass
   *     the class of the keys of the map to be read.
   * @param valueClass
   *     the class of the values of the map to be read.
   * @param in
   *     a binary input stream.
   * @param allowNullMap
   *     if it is true, the map to be read could be null; otherwise, if the map
   *     read from the input is null, an {@code InvalidFormatException} will be
   *     thrown.
   * @param allowNullKey
   *     if it is true, the key in the map to be read could be null; otherwise,
   *     if any key of the map read from the input is null, an {@code
   *     InvalidFormatException} will be thrown.
   * @param allowNullValue
   *     if it is true, the value in the map to be read could be null;
   *     otherwise, if any value of the map read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a map used to store the result. It could be null.
   * @return a map read from the input. The returned map may be null if {@code
   *     allowNullMap} is true; the key in the returned map may be null if
   *     {@code allowNullKey} is true; and the value in the returned map may be
   *     null if {@code allowNullValue} is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole map.
   * @throws InvalidFormatException
   *     if the map read from the input is null, while the argument {@code
   *     allowNullMap} is false; or any key in the map read from the input is
   *     null, while the argument {@code allowNullKey} is false; or any value in
   *     the map read from the input is null, while the argument {@code
   *     allowNullValue} is false.
   * @throws IOException
   *     if any I/O other error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> readMap(final Class<K> keyClass,
      final Class<V> valueClass, final InputStream in,
      final boolean allowNullMap, final boolean allowNullKey,
      final boolean allowNullValue, @Nullable final Map<K, V> buffer)
      throws IOException {
    final BinarySerializer keySerializer = BinarySerialization
        .getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = BinarySerialization
        .getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullMap) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer != null) {
        buffer.clear();
        return buffer;
      } else {
        return new HashMap<>();
      }
    } else {
      final Map<K, V> result;
      if (buffer == null) {
        result = new HashMap<>();
      } else {
        result = buffer;
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          final K key = (K) keySerializer.deserialize(in, allowNullKey);
          final V value = (V) valueSerializer.deserialize(in, allowNullValue);
          result.put(key, value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }

  /**
   * Reads a multimap of objects from the input stream.
   *
   * @param <K>
   *     the type of the keys of the multimap to be read. The binary serializer
   *     of the class {@code K} must have already been registered.
   * @param <V>
   *     the type of the values of the multimap to be read. The binary
   *     serializer of the class {@code V} must have already been registered.
   * @param keyClass
   *     the class of the keys of the multimap to be read.
   * @param valueClass
   *     the class of the values of the multimap to be read.
   * @param in
   *     a binary input stream.
   * @param allowNullMap
   *     if it is true, the multimap to be read could be null; otherwise, if the
   *     multimap read from the input is null, an {@code InvalidFormatException}
   *     will be thrown.
   * @param allowNullKey
   *     if it is true, the key in the multimap to be read could be null;
   *     otherwise, if any key of the multimap read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param allowNullValue
   *     if it is true, the value in the multimap to be read could be null;
   *     otherwise, if any value of the multimap read from the input is null, an
   *     {@code InvalidFormatException} will be thrown.
   * @param buffer
   *     a multimap used to store the result. It could be null.
   * @return a multimap read from the input. The returned multimap may be null
   *     if {@code allowNullMap} is true; the key in the returned multimap may
   *     be null if {@code allowNullKey} is true; and the value in the returned
   *     multimap may be null if {@code allowNullValue} is true.
   * @throws EOFException
   *     if the input reaches the end before reading the whole multimap.
   * @throws InvalidFormatException
   *     if the multimap read from the input is null, while the argument {@code
   *     allowNullMap} is false; or any key in the multimap read from the input
   *     is null, while the argument {@code allowNullKey} is false; or any value
   *     in the multimap read from the input is null, while the argument {@code
   *     allowNullValue} is false.
   * @throws IOException
   *     if any I/O other error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <K, V> Multimap<K, V> readMultimap(final Class<K> keyClass,
      final Class<V> valueClass, final InputStream in,
      final boolean allowNullMap, final boolean allowNullKey,
      final boolean allowNullValue, @Nullable final Multimap<K, V> buffer)
      throws IOException {
    final BinarySerializer keySerializer = BinarySerialization
        .getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = BinarySerialization
        .getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (readNullMark(in)) {
      if (allowNullMap) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    }
    final int n = readVarInt(in);
    if (n == 0) {
      if (buffer != null) {
        buffer.clear();
        return buffer;
      } else {
        return LinkedHashMultimap.create();
      }
    } else {
      final Multimap<K, V> result;
      if (buffer == null) {
        result = LinkedHashMultimap.create();
      } else {
        result = buffer;
        result.clear();
      }
      try {
        for (int i = 0; i < n; ++i) {
          final K key = (K) keySerializer.deserialize(in, allowNullKey);
          final V value = (V) valueSerializer.deserialize(in, allowNullValue);
          result.put(key, value);
        }
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      return result;
    }
  }
}
