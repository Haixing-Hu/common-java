////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteIterator;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharIterator;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleIterator;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatIterator;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntIterator;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongIterator;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortIterator;
import ltd.qubit.commons.io.serialize.BinarySerializer;
import ltd.qubit.commons.io.serialize.NoBinarySerializerRegisteredException;
import ltd.qubit.commons.util.buffer.ByteBuffer;

import static ltd.qubit.commons.io.serialize.BinarySerialization.getSerializer;

/**
 * 提供向 {@link OutputStream} 对象写入数据的函数。
 *
 * @author 胡海星
 */
public final class OutputUtils {

  private static final String UNSUPPORTED_VAR_NUMBER  =
      "The variable length integer format does not support negative value.";

  //  stop checkstyle: MagicNumberCheck

  /**
   * 向输出流写入空值标记。
   *
   * @param out 输出流。
   * @param object 要检查的对象。
   * @return 如果对象为空则返回 true，否则返回 false。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static boolean writeNullMark(final OutputStream out,
      final Object object) throws IOException {
    if (object == null) {
      out.write(1);
      return true;
    } else {
      out.write(0);
      return false;
    }
  }

  /**
   * 向输出流写入布尔值。
   *
   * @param out 输出流。
   * @param value 要写入的布尔值。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBoolean(final OutputStream out, final boolean value)
      throws IOException {
    if (value) {
      out.write(1);
    } else {
      out.write(0);
    }
  }

  /**
   * 向输出流写入布尔值对象。
   *
   * @param out 输出流。
   * @param value 要写入的布尔值对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBooleanObject(final OutputStream out,
      @Nullable final Boolean value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeBoolean(out, value);
    }
  }

  /**
   * 向输出流写入字符。
   *
   * @param out 输出流。
   * @param value 要写入的字符。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeChar(final OutputStream out, final char value)
      throws IOException {
    writeVarShort(out, (short) value);
  }

  /**
   * 向输出流写入字符对象。
   *
   * @param out 输出流。
   * @param value 要写入的字符对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeCharObject(final OutputStream out,
      @Nullable final Character value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeChar(out, value);
    }
  }

  /**
   * 向输出流写入字节。
   *
   * @param out 输出流。
   * @param value 要写入的字节。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeByte(final OutputStream out, final byte value)
      throws IOException {
    out.write(value);
  }

  /**
   * 向输出流写入字节对象。
   *
   * @param out 输出流。
   * @param value 要写入的字节对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeByteObject(final OutputStream out,
      @Nullable final Byte value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeByte(out, value);
    }
  }

  /**
   * 向输出流写入短整型。
   *
   * @param out 输出流。
   * @param value 要写入的短整型。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeShort(final OutputStream out, final short value)
      throws IOException {
    out.write(value >>> 8);
    out.write(value);
  }

  /**
   * 向输出流写入短整型对象。
   *
   * @param out 输出流。
   * @param value 要写入的短整型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeShortObject(final OutputStream out,
      @Nullable final Short value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeShort(out, value);
    }
  }

  /**
   * 向输出流写入可变长度的短整型。
   *
   * @param out 输出流。
   * @param value 要写入的短整型。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarShort(final OutputStream out, final short value)
      throws IOException {
    writeVarInt(out, value);
  }

  /**
   * 向输出流写入可变长度的短整型对象。
   *
   * @param out 输出流。
   * @param value 要写入的短整型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   * @throws IllegalArgumentException 如果值为负数。
   */
  public static void writeVarShortObject(final OutputStream out,
      @Nullable final Short value) throws IOException {
    if ((value != null) && (value < 0)) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    if (! writeNullMark(out, value)) {
      writeVarInt(out, value);
    }
  }

  /**
   * 向输出流写入整型。
   *
   * @param out 输出流。
   * @param value 要写入的整型。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeInt(final OutputStream out, final int value)
      throws IOException {
    out.write(value >>> 24);
    out.write(value >>> 16);
    out.write(value >>> 8);
    out.write(value);
  }

  /**
   * 向输出流写入整型对象。
   *
   * @param out 输出流。
   * @param value 要写入的整型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeIntObject(final OutputStream out,
      @Nullable final Integer value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeInt(out, value);
    }
  }

  /**
   * 向输出流写入可变长度的整型。
   *
   * @param out 输出流。
   * @param value 要写入的整型。
   * @throws IOException 如果发生 I/O 错误。
   * @throws IllegalArgumentException 如果值为负数。
   */
  public static void writeVarInt(final OutputStream out, final int value)
      throws IOException {
    if (value < 0) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    int v = value;
    while (v > 0x7F) {
      out.write((v & 0x7F) | 0x80);
      v >>>= 7;
    }
    out.write(v);
  }

  /**
   * 向输出流写入可变长度的整型对象。
   *
   * @param out 输出流。
   * @param value 要写入的整型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   * @throws IllegalArgumentException 如果值为负数。
   */
  public static void writeVarIntObject(final OutputStream out,
      @Nullable final Integer value) throws IOException {
    if ((value != null) && (value < 0)) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    if (! writeNullMark(out, value)) {
      writeVarInt(out, value);
    }
  }

  /**
   * 向输出流写入长整型。
   *
   * @param out 输出流。
   * @param value 要写入的长整型。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeLong(final OutputStream out, final long value)
      throws IOException {
    final byte[] buffer = new byte[8];
    buffer[0] = (byte) (value >>> 56);
    buffer[1] = (byte) (value >>> 48);
    buffer[2] = (byte) (value >>> 40);
    buffer[3] = (byte) (value >>> 32);
    buffer[4] = (byte) (value >>> 24);
    buffer[5] = (byte) (value >>> 16);
    buffer[6] = (byte) (value >>> 8);
    buffer[7] = (byte) value;
    out.write(buffer, 0, 8);
  }

  /**
   * 向输出流写入长整型对象。
   *
   * @param out 输出流。
   * @param value 要写入的长整型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeLongObject(final OutputStream out,
      @Nullable final Long value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeLong(out, value);
    }
  }

  /**
   * 向输出流写入可变长度的长整型。
   *
   * @param out 输出流。
   * @param value 要写入的长整型。
   * @throws IOException 如果发生 I/O 错误。
   * @throws IllegalArgumentException 如果值为负数。
   */
  public static void writeVarLong(final OutputStream out, final long value)
      throws IOException {
    if (value < 0) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    long v = value;
    while (v > 0x7FL) {
      out.write((int) (v & 0x7FL) | 0x80);
      v >>>= 7;
    }
    out.write((int) v);
  }

  /**
   * 向输出流写入可变长度的长整型对象。
   *
   * @param out 输出流。
   * @param value 要写入的长整型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   * @throws IllegalArgumentException 如果值为负数。
   */
  public static void writeVarLongObject(final OutputStream out,
      @Nullable final Long value) throws IOException {
    if ((value != null) && (value < 0)) {
      throw new IllegalArgumentException(UNSUPPORTED_VAR_NUMBER);
    }
    if (! writeNullMark(out, value)) {
      writeVarLong(out, value);
    }
  }

  /**
   * 向输出流写入浮点型。
   *
   * @param out 输出流。
   * @param value 要写入的浮点型。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeFloat(final OutputStream out, final float value)
      throws IOException {
    final int intBits = Float.floatToIntBits(value);
    writeInt(out, intBits);
  }

  /**
   * 向输出流写入浮点型对象。
   *
   * @param out 输出流。
   * @param value 要写入的浮点型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeFloatObject(
          final OutputStream out, @Nullable final Float value)
      throws IOException {
    if (! writeNullMark(out, value)) {
      writeFloat(out, value);
    }
  }

  /**
   * 向输出流写入双精度浮点型。
   *
   * @param out 输出流。
   * @param value 要写入的双精度浮点型。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeDouble(final OutputStream out, final double value)
      throws IOException {
    final long longBits = Double.doubleToLongBits(value);
    writeLong(out, longBits);
  }

  /**
   * 向输出流写入双精度浮点型对象。
   *
   * @param out 输出流。
   * @param value 要写入的双精度浮点型对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeDoubleObject(final OutputStream out,
      @Nullable final Double value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeDouble(out, value);
    }
  }

  /**
   * 向输出流写入字符串。
   *
   * @param out 输出流。
   * @param value 要写入的字符串，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeString(final OutputStream out,
      @Nullable final String value) throws IOException {
    if (! writeNullMark(out, value)) {
      final int strlen = value.length();
      if (strlen == 0) {
        writeVarInt(out, 0); // special case for empty string
        return;
      }
      final ByteBuffer bytes = new ByteBuffer();
      // encode the characters
      for (int i = 0; i < strlen; ++i) {
        final int ch = value.charAt(i);
        if ((ch >= 0x0001) && (ch <= 0x007F)) {
          bytes.append((byte) ch);
        } else if (ch > 0x07FF) {
          bytes.append((byte) (0xE0 | ((ch >> 12) & 0x0F)));
          bytes.append((byte) (0x80 | ((ch >> 6) & 0x3F)));
          bytes.append((byte) (0x80 | (ch & 0x3F)));
        } else {
          bytes.append((byte) (0xC0 | ((ch >> 6) & 0x1F)));
          bytes.append((byte) (0x80 | (ch & 0x3F)));
        }
      }
      // write the length and bytes of the buffer
      writeVarInt(out, bytes.length());
      out.write(bytes.buffer(), 0, bytes.length());
    }
  }

  /**
   * 向输出流写入日期。
   *
   * @param out 输出流。
   * @param value 要写入的日期，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeDate(final OutputStream out,
      @Nullable final Date value) throws IOException {
    if (! writeNullMark(out, value)) {
      final long time = value.getTime();
      writeLong(out, time);
    }
  }

  /**
   * 向输出流写入大整数。
   *
   * @param out 输出流。
   * @param value 要写入的大整数，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBigInteger(final OutputStream out,
      @Nullable final BigInteger value) throws IOException {
    if (! writeNullMark(out, value)) {
      if (value.signum() == 0) { // value == 0
        writeVarInt(out, 0);
      } else {
        final byte[] bits = value.toByteArray();
        writeVarInt(out, bits.length);
        out.write(bits, 0, bits.length);
      }
    }
  }

  /**
   * 向输出流写入大十进制数。
   *
   * @param out 输出流。
   * @param value 要写入的大十进制数，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBigDecimal(final OutputStream out,
      @Nullable final BigDecimal value) throws IOException {
    if (! writeNullMark(out, value)) {
      if (value.signum() == 0) { // value == 0
        writeVarInt(out, 0);
      } else {
        final BigInteger unscaledValue = value.unscaledValue();
        final byte[] bits = unscaledValue.toByteArray();
        writeVarInt(out, bits.length);
        out.write(bits, 0, bits.length);
        writeInt(out, value.scale());
      }
    }
  }

  /**
   * 向输出流写入类对象。
   *
   * @param out 输出流。
   * @param value 要写入的类对象，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeClass(final OutputStream out,
      @Nullable final Class<?> value) throws IOException {
    if (value == null) {
      writeString(out, null);
    } else {
      writeString(out, value.getName());
    }
  }

  /**
   * 向输出流写入枚举值。
   *
   * @param out 输出流。
   * @param value 要写入的枚举值，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeEnum(final OutputStream out,
      @Nullable final Enum<?> value) throws IOException {
    if (! writeNullMark(out, value)) {
      writeVarInt(out, value.ordinal());
    }
  }

  /**
   * 向输出流写入布尔值数组。
   *
   * @param out 输出流。
   * @param array 要写入的布尔值数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBooleanArray(final OutputStream out,
      @Nullable final boolean[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final boolean value : array) {
        writeBoolean(out, value);
      }
    }
  }

  /**
   * 向输出流写入布尔值数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的布尔值数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBooleanArray(final OutputStream out,
      final boolean[] array, final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeBoolean(out, array[i]);
    }
  }

  /**
   * 向输出流写入布尔值集合。
   *
   * @param out 输出流。
   * @param col 要写入的布尔值集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeBooleanCollection(final OutputStream out,
      @Nullable final BooleanCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final BooleanIterator iter = col.iterator();
      while (iter.hasNext()) {
        final boolean value = iter.next();
        writeBoolean(out, value);
      }
    }
  }

  /**
   * 向输出流写入字符数组。
   *
   * @param out 输出流。
   * @param array 要写入的字符数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeCharArray(final OutputStream out,
      @Nullable final char[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final char value : array) {
        writeChar(out, value);
      }
    }
  }

  /**
   * 向输出流写入字符数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的字符数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeCharArray(final OutputStream out, final char[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeChar(out, array[i]);
    }
  }

  /**
   * 向输出流写入字符集合。
   *
   * @param out 输出流。
   * @param col 要写入的字符集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeCharCollection(final OutputStream out,
      @Nullable final CharCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final CharIterator iter = col.iterator();
      while (iter.hasNext()) {
        final char value = iter.next();
        writeChar(out, value);
      }
    }
  }

  /**
   * 向输出流写入字节数组。
   *
   * @param out 输出流。
   * @param array 要写入的字节数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeByteArray(final OutputStream out,
      @Nullable final byte[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      // use the write() to optimize the writing of bytes
      out.write(array, 0, array.length);
    }
  }

  /**
   * 向输出流写入字节数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的字节数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeByteArray(final OutputStream out, final byte[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    out.write(array, off, len);
  }

  /**
   * 向输出流写入字节集合。
   *
   * @param out 输出流。
   * @param col 要写入的字节集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeByteCollection(final OutputStream out,
      @Nullable final ByteCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final ByteIterator iter = col.iterator();
      while (iter.hasNext()) {
        final byte value = iter.next();
        writeByte(out, value);
      }
    }
  }

  /**
   * 向输出流写入短整型数组。
   *
   * @param out 输出流。
   * @param array 要写入的短整型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeShortArray(final OutputStream out,
      @Nullable final short[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final short value : array) {
        writeShort(out, value);
      }
    }
  }

  /**
   * 向输出流写入短整型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的短整型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeShortArray(final OutputStream out, final short[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeShort(out, array[i]);
    }
  }

  /**
   * 向输出流写入短整型集合。
   *
   * @param out 输出流。
   * @param col 要写入的短整型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeShortCollection(final OutputStream out,
      @Nullable final ShortCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final ShortIterator iter = col.iterator();
      while (iter.hasNext()) {
        final short value = iter.next();
        writeShort(out, value);
      }
    }
  }

  /**
   * 向输出流写入可变长度的短整型数组。
   *
   * @param out 输出流。
   * @param array 要写入的短整型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarShortArray(final OutputStream out,
      @Nullable final short[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final short value : array) {
        writeVarShort(out, value);
      }
    }
  }

  /**
   * 向输出流写入可变长度的短整型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的短整型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarShortArray(final OutputStream out, final short[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeVarShort(out, array[i]);
    }
  }

  /**
   * 向输出流写入可变长度的短整型集合。
   *
   * @param out 输出流。
   * @param col 要写入的短整型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarShortCollection(final OutputStream out,
      @Nullable final ShortCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final ShortIterator iter = col.iterator();
      while (iter.hasNext()) {
        final short value = iter.next();
        writeVarShort(out, value);
      }
    }
  }

  /**
   * 向输出流写入整型数组。
   *
   * @param out 输出流。
   * @param array 要写入的整型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeIntArray(final OutputStream out,
      @Nullable final int[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final int value : array) {
        writeInt(out, value);
      }
    }
  }

  /**
   * 向输出流写入整型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的整型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeIntArray(final OutputStream out, final int[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeInt(out, array[i]);
    }
  }

  /**
   * 向输出流写入整型集合。
   *
   * @param out 输出流。
   * @param col 要写入的整型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeIntCollection(final OutputStream out,
      @Nullable final IntCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final IntIterator iter = col.iterator();
      while (iter.hasNext()) {
        final int value = iter.next();
        writeInt(out, value);
      }
    }
  }

  /**
   * 向输出流写入可变长度的整型数组。
   *
   * @param out 输出流。
   * @param array 要写入的整型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarIntArray(final OutputStream out,
      @Nullable final int[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final int value : array) {
        writeVarInt(out, value);
      }
    }
  }

  /**
   * 向输出流写入可变长度的整型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的整型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarIntArray(final OutputStream out, final int[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeVarInt(out, array[i]);
    }
  }

  /**
   * 向输出流写入可变长度的整型集合。
   *
   * @param out 输出流。
   * @param col 要写入的整型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarIntCollection(final OutputStream out,
      @Nullable final IntCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final IntIterator iter = col.iterator();
      while (iter.hasNext()) {
        final int value = iter.next();
        writeVarInt(out, value);
      }
    }
  }

  /**
   * 向输出流写入长整型数组。
   *
   * @param out 输出流。
   * @param array 要写入的长整型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeLongArray(final OutputStream out,
      @Nullable final long[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final long value : array) {
        writeLong(out, value);
      }
    }
  }

  /**
   * 向输出流写入长整型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的长整型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeLongArray(final OutputStream out, final long[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeLong(out, array[i]);
    }
  }

  /**
   * 向输出流写入长整型集合。
   *
   * @param out 输出流。
   * @param col 要写入的长整型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeLongCollection(final OutputStream out,
      @Nullable final LongCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final LongIterator iter = col.iterator();
      while (iter.hasNext()) {
        final long value = iter.next();
        writeLong(out, value);
      }
    }
  }

  /**
   * 向输出流写入可变长度的长整型数组。
   *
   * @param out 输出流。
   * @param array 要写入的长整型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarLongArray(final OutputStream out,
      @Nullable final long[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final long value : array) {
        writeVarLong(out, value);
      }
    }
  }

  /**
   * 向输出流写入可变长度的长整型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的长整型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarLongArray(final OutputStream out, final long[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeVarLong(out, array[i]);
    }
  }

  /**
   * 向输出流写入可变长度的长整型集合。
   *
   * @param out 输出流。
   * @param col 要写入的长整型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeVarLongCollection(final OutputStream out,
      @Nullable final LongCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final LongIterator iter = col.iterator();
      while (iter.hasNext()) {
        final long value = iter.next();
        writeVarLong(out, value);
      }
    }
  }

  /**
   * 向输出流写入浮点型数组。
   *
   * @param out 输出流。
   * @param array 要写入的浮点型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeFloatArray(final OutputStream out,
      @Nullable final float[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final float value : array) {
        writeFloat(out, value);
      }
    }
  }

  /**
   * 向输出流写入浮点型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的浮点型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeFloatArray(final OutputStream out, final float[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeFloat(out, array[i]);
    }
  }

  /**
   * 向输出流写入浮点型集合。
   *
   * @param out 输出流。
   * @param col 要写入的浮点型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeFloatCollection(final OutputStream out,
      @Nullable final FloatCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final FloatIterator iter = col.iterator();
      while (iter.hasNext()) {
        final float value = iter.next();
        writeFloat(out, value);
      }
    }
  }

  /**
   * 向输出流写入双精度浮点型数组。
   *
   * @param out 输出流。
   * @param array 要写入的双精度浮点型数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeDoubleArray(final OutputStream out,
      @Nullable final double[] array) throws IOException {
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final double value : array) {
        writeDouble(out, value);
      }
    }
  }

  /**
   * 向输出流写入双精度浮点型数组的一部分。
   *
   * @param out 输出流。
   * @param array 要写入的双精度浮点型数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeDoubleArray(final OutputStream out, final double[] array,
      final int off, final int len) throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      writeDouble(out, array[i]);
    }
  }

  /**
   * 向输出流写入双精度浮点型集合。
   *
   * @param out 输出流。
   * @param col 要写入的双精度浮点型集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static void writeDoubleCollection(final OutputStream out,
      @Nullable final DoubleCollection col) throws IOException {
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      final DoubleIterator iter = col.iterator();
      while (iter.hasNext()) {
        final double value = iter.next();
        writeDouble(out, value);
      }
    }
  }

  /**
   * 向二进制输出流写入对象。
   *
   * @param <T>
   *          类的类型。
   * @param valueClass
   *          要序列化对象的类对象。
   * @param out
   *          二进制输出流。
   * @param value
   *          要序列化的对象。可以为空。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  public static <T> void writeObject(final Class<T> valueClass,
      final OutputStream out, @Nullable final T value) throws IOException {
    final BinarySerializer serializer = getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    serializer.serialize(out, value);
  }

  /**
   * 向输出流写入对象数组。
   *
   * @param <T> 数组元素的类型。
   * @param valueClass 数组元素的类对象。
   * @param out 输出流。
   * @param array 要写入的对象数组，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static <T> void writeArray(final Class<T> valueClass,
      final OutputStream out, @Nullable final T[] array) throws IOException {
    final BinarySerializer serializer = getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, array)) {
      writeVarInt(out, array.length);
      for (final T value : array) {
        serializer.serialize(out, value);
      }
    }
  }

  /**
   * 向输出流写入对象数组的一部分。
   *
   * @param <T> 数组元素的类型。
   * @param valueClass 数组元素的类对象。
   * @param out 输出流。
   * @param array 要写入的对象数组。
   * @param off 数组的起始偏移量。
   * @param len 要写入的元素数量。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static <T> void writeArray(final Class<T> valueClass,
      final OutputStream out, final T[] array, final int off, final int len)
      throws IOException {
    if ((off < 0) || (len < 0) || (len > array.length - off)) {
      throw new IndexOutOfBoundsException();
    }
    final BinarySerializer serializer = getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    writeVarInt(out, len);
    final int end = off + len;
    for (int i = off; i < end; ++i) {
      serializer.serialize(out, array[i]);
    }
  }

  /**
   * 向输出流写入对象集合。
   *
   * @param <T> 集合元素的类型。
   * @param valueClass 集合元素的类对象。
   * @param out 输出流。
   * @param col 要写入的对象集合，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static <T> void writeCollection(final Class<T> valueClass,
      final OutputStream out, @Nullable final Collection<T> col)
      throws IOException {
    final BinarySerializer serializer = getSerializer(valueClass);
    if (serializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, col)) {
      writeVarInt(out, col.size());
      for (final T value : col) {
        serializer.serialize(out, value);
      }
    }
  }

  /**
   * 向输出流写入映射表。
   *
   * @param <K> 映射表键的类型。
   * @param <V> 映射表值的类型。
   * @param keyClass 映射表键的类对象。
   * @param valueClass 映射表值的类对象。
   * @param out 输出流。
   * @param map 要写入的映射表，可以为空。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static <K, V> void writeMap(final Class<K> keyClass,
      final Class<V> valueClass, final OutputStream out,
      @Nullable final Map<K, V> map) throws IOException {
    final BinarySerializer keySerializer = getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, map)) {
      writeVarInt(out, map.size());
      for (final Map.Entry<K, V> entry : map.entrySet()) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        keySerializer.serialize(out, key);
        valueSerializer.serialize(out, value);
      }
    }
  }

  /**
   * 向输出流写入多重映射表。
   *
   * @param <K> 映射表键的类型。
   * @param <V> 映射表值的类型。
   * @param out 输出流。
   * @param map 要写入的多重映射表，可以为空。
   * @param keyClass 映射表键的类对象。
   * @param valueClass 映射表值的类对象。
   * @throws IOException 如果发生 I/O 错误。
   */
  public static <K, V> void writeMultimap(final OutputStream out,
      @Nullable final Multimap<K, V> map, final Class<K> keyClass,
      final Class<V> valueClass) throws IOException {
    final BinarySerializer keySerializer = getSerializer(keyClass);
    if (keySerializer == null) {
      throw new NoBinarySerializerRegisteredException(keyClass);
    }
    final BinarySerializer valueSerializer = getSerializer(valueClass);
    if (valueSerializer == null) {
      throw new NoBinarySerializerRegisteredException(valueClass);
    }
    if (! writeNullMark(out, map)) {
      writeVarInt(out, map.size());
      for (final Map.Entry<K, V> entry : map.entries()) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        keySerializer.serialize(out, key);
        valueSerializer.serialize(out, value);
      }
    }
  }

  //  resume checkstyle: MagicNumberCheck
}