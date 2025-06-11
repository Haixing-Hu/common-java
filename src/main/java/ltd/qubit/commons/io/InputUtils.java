////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

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

/**
 * 提供从 {@link InputStream} 对象读取数据的函数。
 *
 * @author 胡海星
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
   * 从输入流读取指定数量的字节。
   *
   * <p>此方法会一直阻塞，直到出现以下条件之一：</p>
   *
   * <ul>
   * <li>有 {@code len} 个字节的输入数据可用，在这种情况下正常返回。</li>
   * <li>检测到文件结束，在这种情况下抛出 {@code EOFException}。</li>
   * <li>发生 I/O 错误，在这种情况下抛出 {@code IOException}（而不是 {@code EOFException}）。</li>
   * </ul>
   *
   * <p>如果 {@code len} 为零，则不读取任何字节。否则，读取的第一个字节存储到
   * {@code buffer[off]} 元素中，下一个存储到 {@code buffer[off+1]}，以此类推。
   * 读取的字节数始终等于 {@code len}。</p>
   *
   * @param in
   *     读取数据的输入源。
   * @param buf
   *     存储从输入流读取的字节的字节数组。
   * @param off
   *     在数组 {@code buffer} 中写入数据的起始偏移量。
   * @param len
   *     需要读取的字节数。
   * @throws NullPointerException
   *     如果 {@code buffer} 为 null。
   * @throws IndexOutOfBoundsException
   *     如果 {@code off} 为负数，{@code len} 为负数，或 {@code len} 大于
   *     {@code buff.length - off}。
   * @throws EOFException
   *     如果此输入在读取 {@code len} 个字节之前到达末尾。
   * @throws IOException
   *     如果发生 I/O 错误。
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
   * 从输入流读取一个 {@code boolean} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code boolean} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code boolean} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static boolean readBoolean(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (ch != 0);
  }

  /**
   * 从输入流读取一个 {@code Boolean} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Boolean} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Boolean} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Boolean} 对象，可能为 {@code null}。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Boolean} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code char} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code char} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code char} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static char readChar(final InputStream in) throws IOException {
    return (char) readVarShort(in);
  }

  /**
   * 从输入流读取一个 {@code Character} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Character} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Character} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Character} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Character} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code byte} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code byte} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code byte} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static byte readByte(final InputStream in) throws IOException {
    final int ch = in.read();
    if (ch < 0) {
      throw new EOFException();
    }
    return (byte) (ch);
  }

  /**
   * 从输入流读取一个 {@code Byte} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Byte} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Byte} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Byte} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Byte} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code short} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code short} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code Short} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Short} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Short} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Short} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Short} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code short} 值。
   *
   * <p>定义了一种用于正整数的变长格式，其中每个字节的高位指示是否还有更多字节需要读取。
   * 低位的七个位作为结果整数值中越来越重要的位被追加。因此，0 到 127 的值可以存储在
   * 单个字节中，128 到 16,383 的值可以存储在两个字节中，依此类推。这提供了压缩，
   * 同时仍然高效解码。
   *
   * <p>此函数将读取一到三个字节。较小的值占用较少的字节。不支持负数。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的变长编码的 {@code short} 值。
   *     注意，编码的 {@code short} 值始终是正值，
   *     因此返回值始终在 0 到 32767 之间。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果 {@code short} 值编码不正确。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code Short} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Short} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Short} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Short} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Short} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code int} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code int} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code Integer} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Integer} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Integer} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Integer} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Integer} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code int} 值。
   *
   * <p>定义了一种用于正整数的变长格式，其中每个字节的高位指示是否还有更多字节需要读取。
   * 低位的七个位作为结果整数值中越来越重要的位被追加。因此，0 到 127 的值可以存储在
   * 单个字节中，128 到 16,383 的值可以存储在两个字节中，依此类推。这提供了压缩，
   * 同时仍然高效解码。</p>
   *
   * <p>此函数将读取一到五个字节。较小的值占用较少的字节。不支持负数。</p>
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的变长编码的 {@code int} 值。
   *     注意，编码的 {@code int} 值始终是正值，
   *     因此返回值始终在 0 到 2147483647 之间。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果 {@code int} 值编码不正确。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code Integer} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Integer} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Integer} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Integer} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Integer} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code long} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code long} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code Long} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Long} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Long} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Long} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Long} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code long} 值。
   *
   * <p>定义了一种用于正整数的变长格式，其中每个字节的高位指示是否还有更多字节需要读取。
   * 低位的七个位作为结果整数值中越来越重要的位被追加。因此，0 到 127 的值可以存储在
   * 单个字节中，128 到 16,383 的值可以存储在两个字节中，依此类推。这提供了压缩，
   * 同时仍然高效解码。</p>
   *
   * <p>此函数将读取一到九个字节。较小的值占用较少的字节。不支持负数。</p>
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的变长编码的 {@code long} 值。
   *     注意，编码的 {@code long} 值始终是正值，
   *     因此返回值始终在 0 到 9223372036854775807 之间。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果 {@code long} 值编码不正确。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code Long} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Long} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Long} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Long} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Long} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code float} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code float} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code float} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static float readFloat(final InputStream in) throws IOException {
    final int intBits = readInt(in);
    return Float.intBitsToFloat(intBits);
  }

  /**
   * 从输入流读取一个 {@code Float} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Float} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Float} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Float} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Float} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code double} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @return 从输入流读取的 {@code double} 值。
   * @throws EOFException
   *     如果输入在读取完整的 {@code double} 值之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static double readDouble(final InputStream in) throws IOException {
    final long longBits = readLong(in);
    return Double.longBitsToDouble(longBits);
  }

  /**
   * 从输入流读取一个 {@code Double} 对象。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Double} 对象可以是 null 值；
   *     否则，如果从输入流读取的 {@code Double} 对象为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Double} 对象。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Double} 对象之前到达末尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code String} 值。
   *
   * <p>{@code String} 以修改过的 UTF-8 格式编码，如 {@link java.io.DataInput} 
   * 接口中所述。</p>
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code String} 值可以是 null 值；
   *     否则，如果从输入流读取的 {@code String} 值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code String} 值。注意，如果从输入流读取的值为 null
   *     且参数 <code>allowNull</code> 为 true，则返回值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code String} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的值为 null 值且参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code Date} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Date} 值可以是 null 值；
   *     否则，如果从输入流读取的 {@code Date} 值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Date} 值。注意，如果从输入流读取的值为 null
   *     且参数 <code>allowNull</code> 为 true，则返回值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Date} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的值为 null 值且参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code BigInteger} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code BigInteger} 值可以是 null 值；
   *     否则，如果从输入流读取的 {@code BigInteger} 值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code BigInteger} 值。注意，如果从输入流读取的值为 null
   *     且参数 <code>allowNull</code> 为 true，则返回值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code BigInteger} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的值为 null 值且参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code BigDecimal} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code BigDecimal} 值可以是 null 值；
   *     否则，如果从输入流读取的 {@code BigDecimal} 值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code BigDecimal} 值。注意，如果从输入流读取的值为 null
   *     且参数 <code>allowNull</code> 为 true，则返回值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code BigDecimal} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的值为 null 值且参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code Class} 值。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Class} 值可以是 null 值；
   *     否则，如果从输入流读取的 {@code Class} 值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Class} 值。注意，如果从输入流读取的值为 null
   *     且参数 <code>allowNull</code> 为 true，则返回值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Class} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的值为 null 值且参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code Enum} 值。
   *
   * @param <T>
   *     枚举类型。
   * @param enumClass
   *     {@code Enum} 类型的类对象。
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code Enum} 值可以是 null 值；
   *     否则，如果从输入流读取的 {@code Enum} 值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @return 从输入流读取的 {@code Enum} 值。注意，如果从输入流读取的值为 null
   *     且参数 <code>allowNull</code> 为 true，则返回值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code Enum} 值之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的值为 null 值且参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code boolean} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code boolean} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code boolean} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code boolean} 数组。可以为 null。
   * @return 从输入流读取的 {@code boolean} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code boolean} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code boolean} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code boolean} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code boolean} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code boolean} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code boolean} 列表。可以为 null。
   * @return 从输入流读取的 {@code boolean} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code boolean} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code boolean} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code boolean} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code boolean} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code boolean} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code boolean} 集合。可以为 null。
   * @return 从输入流读取的 {@code boolean} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code boolean} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code boolean} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code char} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code char} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code char} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code char} 数组。可以为 null。
   * @return 从输入流读取的 {@code char} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code char} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code char} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code char} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code char} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code char} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code char} 列表。可以为 null。
   * @return 从输入流读取的 {@code char} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code char} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code char} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code char} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code char} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code char} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code char} 集合。可以为 null。
   * @return 从输入流读取的 {@code char} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code char} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code char} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code byte} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code byte} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code byte} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code byte} 数组。可以为 null。
   * @return 从输入流读取的 {@code byte} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code byte} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code byte} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code byte} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code byte} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code byte} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code byte} 列表。可以为 null。
   * @return 从输入流读取的 {@code byte} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code byte} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code byte} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code byte} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code byte} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code byte} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code byte} 集合。可以为 null。
   * @return 从输入流读取的 {@code byte} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code byte} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code byte} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code short} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code short} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code short} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code short} 数组。可以为 null。
   * @return 从输入流读取的 {@code short} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code short} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code short} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code short} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code short} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code short} 列表。可以为 null。
   * @return 从输入流读取的 {@code short} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code short} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code short} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code short} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code short} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code short} 集合。可以为 null。
   * @return 从输入流读取的 {@code short} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code short} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code short} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code short} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code short} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code short} 数组。可以为 null。
   * @return 从输入流读取的变长编码 {@code short} 数组。
   *     如果结果数组的长度与参数 {@code buffer} 的长度相同，
   *     则返回值存储在参数 {@code buffer} 中；否则，创建一个新的 {@code short} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code short} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code short} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code short} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code short} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code short} 列表。可以为 null。
   * @return 从输入流读取的变长编码 {@code short} 列表。
   *     如果参数 {@code buffer} 不为 null，且要读取的列表不是 null 值，
   *     则参数 {@code buffer} 被清空并将返回值存储在其中；否则，创建一个新的 {@code short} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code short} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code short} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code short} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code short} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code short} 集合。可以为 null。
   * @return 从输入流读取的变长编码 {@code short} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code short} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code short} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code short} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code int} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code int} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code int} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code int} 数组。可以为 null。
   * @return 从输入流读取的 {@code int} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code int} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code int} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code int} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code int} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code int} 列表。可以为 null。
   * @return 从输入流读取的 {@code int} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code int} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code int} 集合。
   *
   * @param in
   *     输入流。
   * @param allowNull
   *     如果为 true，要读取的 {@code int} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code int} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code int} 集合。可以为 null。
   * @return 从输入流读取的 {@code int} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code int} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code int} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code int} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code int} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code int} 数组。可以为 null。
   * @return 从输入流读取的变长编码 {@code int} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；否则，创建一个新的 {@code int} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code short} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code int} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code int} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code int} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code int} 列表。可以为 null。
   * @return 从输入流读取的变长编码 {@code int} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；否则，创建一个新的 {@code int} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code short} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code int} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code int} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code int} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code int} 集合。可以为 null。
   * @return 从输入流读取的变长编码 {@code int} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code int} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code int} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code short} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code long} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code long} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code long} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code long} 数组。可以为 null。
   * @return 从输入流读取的 {@code long} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code long} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code long} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code long} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code long} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code long} 列表。可以为 null。
   * @return 从输入流读取的 {@code long} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code long} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code long} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code long} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code long} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code long} 集合。可以为 null。
   * @return 从输入流读取的 {@code long} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code long} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code long} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code long} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code long} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code long} 数组。可以为 null。
   * @return 从输入流读取的变长编码 {@code long} 数组。
   *     如果结果数组的长度与参数 {@code buffer} 的长度相同，
   *     则返回值存储在参数 {@code buffer} 中；否则，创建一个新的 {@code long} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code long} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code long} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code long} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code long} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code long} 列表。可以为 null。
   * @return 从输入流读取的变长编码 {@code long} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；否则，创建一个新的 {@code long} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code long} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个变长编码的 {@code long} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code long} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code long} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code long} 集合。可以为 null。
   * @return 从输入流读取的变长编码 {@code long} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code long} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code long} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false；
   *     或变长编码的 {@code long} 值格式无效。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code float} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code float} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code float} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code float} 数组。可以为 null。
   * @return 从输入流读取的 {@code float} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code float} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code float} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code float} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code float} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code float} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code float} 列表。可以为 null。
   * @return 从输入流读取的 {@code float} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code float} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code float} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code float} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code float} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code float} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code float} 集合。可以为 null。
   * @return 从输入流读取的 {@code float} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code float} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code float} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code double} 数组。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code double} 数组可以是 null 值；
   *     否则，如果从输入流读取的 {@code double} 数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code double} 数组。可以为 null。
   * @return 从输入流读取的 {@code double} 数组。如果结果数组的长度与参数 {@code buffer}
   *     的长度相同，则返回值存储在参数 {@code buffer} 中；
   *     否则，创建一个新的 {@code double} 数组来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的数组为 null 值，
   *     则返回的数组可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code double} 数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code double} 列表。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code double} 列表可以是 null 值；
   *     否则，如果从输入流读取的 {@code double} 列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code double} 列表。可以为 null。
   * @return 从输入流读取的 {@code double} 列表。如果参数 {@code buffer} 不为 null，
   *     且要读取的列表不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code double} 数组列表来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的列表为 null 值，
   *     则返回的列表可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code double} 列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个 {@code double} 集合。
   *
   * @param in
   *     读取数据的输入源。
   * @param allowNull
   *     如果为 true，要读取的 {@code double} 集合可以是 null 值；
   *     否则，如果从输入流读取的 {@code double} 集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的 {@code double} 集合。可以为 null。
   * @return 从输入流读取的 {@code double} 集合。如果参数 {@code buffer} 不为 null，
   *     且要读取的集合不是 null 值，则参数 {@code buffer} 被清空并将返回值存储在其中；
   *     否则，创建一个新的 {@code double} 哈希集合来存储返回值。
   *     注意，如果 {@code allowNull} 为 true 且从输入流读取的集合为 null 值，
   *     则返回的集合可能为 null。
   * @throws EOFException
   *     如果输入在读取完整的 {@code double} 集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNull} 为 false。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个对象。
   *
   * @param <T>
   *     类的类型。类 {@code T} 的二进制序列化器必须已经被注册。
   * @param objClass
   *     要反序列化的对象的类。
   * @param in
   *     二进制输入流。注意，此函数不会关闭输入流。
   * @param allowNull
   *     指示是否允许序列化的对象为 null。
   * @return 反序列化的对象。
   * @throws IOException
   *     如果发生任何 I/O 错误。
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
   * 从输入流读取一个对象数组。
   *
   * @param <T>
   *     要读取的数组元素的类型。类 {@code T} 的二进制序列化器必须已经被注册。
   * @param valueClass
   *     要读取的数组元素的类。
   * @param in
   *     二进制输入流。
   * @param allowNullArray
   *     如果为 true，要读取的数组可以是 null；否则，如果从输入流读取的数组为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullValue
   *     如果为 true，数组中的元素可以是 null；否则，如果从输入流读取的数组中的任何元素为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的对象数组，如果未指定则为 {@code null}。
   * @return 从输入流读取的对象数组。如果 {@code allowNullArray} 为 true，返回的数组可能为 null；
   *     如果 {@code allowNullValue} 为 true，返回数组中的元素可能为 null。
   * @throws EOFException
   *     如果输入在读取完整数组之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的数组为 null，而参数 {@code allowNullArray} 为 false；
   *     或从输入流读取的数组中的任何元素为 null，而参数 {@code allowNullValue} 为 false。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
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
   * 从输入流读取一个对象列表。
   *
   * @param <T>
   *     要读取的列表元素的类型。类 {@code T} 的二进制序列化器必须已经被注册。
   * @param valueClass
   *     要读取的列表元素的类。
   * @param in
   *     二进制输入流。
   * @param allowNullList
   *     如果为 true，要读取的列表可以是 null；否则，如果从输入流读取的列表为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullValue
   *     如果为 true，列表中的元素可以是 null；否则，如果从输入流读取的列表中的任何元素为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的列表。可以为 null。
   * @return 从输入流读取的对象列表。如果 {@code allowNullList} 为 true，返回的列表可能为 null；
   *     如果 {@code allowNullValue} 为 true，返回列表中的元素可能为 null。
   * @throws EOFException
   *     如果输入在读取完整列表之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的列表为 null，而参数 {@code allowNullList} 为 false；
   *     或从输入流读取的列表中的任何元素为 null，而参数 {@code allowNullValue} 为 false。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
   */
  @SuppressWarnings("unchecked")
  @Nullable
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
   * 从输入流读取一个对象集合。
   *
   * @param <T>
   *     要读取的集合元素的类型。类 {@code T} 的二进制序列化器必须已经被注册。
   * @param valueClass
   *     要读取的集合元素的类。
   * @param in
   *     二进制输入流。
   * @param allowNullSet
   *     如果为 true，要读取的集合可以是 null；否则，如果从输入流读取的集合为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullValue
   *     如果为 true，集合中的元素可以是 null；否则，如果从输入流读取的集合中的任何元素为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的集合。可以为 null。
   * @return 从输入流读取的对象集合。如果 {@code allowNullSet} 为 true，返回的集合可能为 null；
   *     如果 {@code allowNullValue} 为 true，返回集合中的元素可能为 null。
   * @throws EOFException
   *     如果输入在读取完整集合之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的集合为 null，而参数 {@code allowNullSet} 为 false；
   *     或从输入流读取的集合中的任何元素为 null，而参数 {@code allowNullValue} 为 false。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
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
   * 从输入流读取一个对象映射。
   *
   * @param <K>
   *     要读取的映射键的类型。类 {@code K} 的二进制序列化器必须已经被注册。
   * @param <V>
   *     要读取的映射值的类型。类 {@code V} 的二进制序列化器必须已经被注册。
   * @param keyClass
   *     要读取的映射键的类。
   * @param valueClass
   *     要读取的映射值的类。
   * @param in
   *     二进制输入流。
   * @param allowNullMap
   *     如果为 true，要读取的映射可以是 null；否则，如果从输入流读取的映射为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullKey
   *     如果为 true，映射中的键可以是 null；否则，如果从输入流读取的映射中的任何键为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullValue
   *     如果为 true，映射中的值可以是 null；否则，如果从输入流读取的映射中的任何值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的映射。可以为 null。
   * @return 从输入流读取的映射。如果 {@code allowNullMap} 为 true，返回的映射可能为 null；
   *     如果 {@code allowNullKey} 为 true，返回映射中的键可能为 null；
   *     如果 {@code allowNullValue} 为 true，返回映射中的值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整映射之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的映射为 null，而参数 {@code allowNullMap} 为 false；
   *     或从输入流读取的映射中的任何键为 null，而参数 {@code allowNullKey} 为 false；
   *     或从输入流读取的映射中的任何值为 null，而参数 {@code allowNullValue} 为 false。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
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
   * 从输入流读取一个对象多重映射。
   *
   * @param <K>
   *     要读取的多重映射键的类型。类 {@code K} 的二进制序列化器必须已经被注册。
   * @param <V>
   *     要读取的多重映射值的类型。类 {@code V} 的二进制序列化器必须已经被注册。
   * @param keyClass
   *     要读取的多重映射键的类。
   * @param valueClass
   *     要读取的多重映射值的类。
   * @param in
   *     二进制输入流。
   * @param allowNullMap
   *     如果为 true，要读取的多重映射可以是 null；否则，如果从输入流读取的多重映射为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullKey
   *     如果为 true，多重映射中的键可以是 null；否则，如果从输入流读取的多重映射中的任何键为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param allowNullValue
   *     如果为 true，多重映射中的值可以是 null；否则，如果从输入流读取的多重映射中的任何值为 null，
   *     将抛出 {@code InvalidFormatException}。
   * @param buffer
   *     用于存储结果的多重映射。可以为 null。
   * @return 从输入流读取的多重映射。如果 {@code allowNullMap} 为 true，返回的多重映射可能为 null；
   *     如果 {@code allowNullKey} 为 true，返回多重映射中的键可能为 null；
   *     如果 {@code allowNullValue} 为 true，返回多重映射中的值可能为 null。
   * @throws EOFException
   *     如果输入在读取完整多重映射之前到达末尾。
   * @throws InvalidFormatException
   *     如果从输入流读取的多重映射为 null，而参数 {@code allowNullMap} 为 false；
   *     或从输入流读取的多重映射中的任何键为 null，而参数 {@code allowNullKey} 为 false；
   *     或从输入流读取的多重映射中的任何值为 null，而参数 {@code allowNullValue} 为 false。
   * @throws IOException
   *     如果发生任何其他 I/O 错误。
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
