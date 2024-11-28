////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

import java.nio.ByteOrder;

/**
 * The enumeration of endianness.
 * <p>
 * This enumeration is used to indicate the endianness of a binary data. It is
 * similar to {@link java.nio.ByteOrder}, but it is represented as an enumeration
 * instead of a class, and it is more convenient to use.
 *
 * @see java.nio.ByteOrder
 * @see <a href="https://en.wikipedia.org/wiki/Endianness">Endianness</a>
 * @author Haixing Hu
 */
public enum Endian {

  /**
   * The big-endian.
   */
  BIG_ENDIAN(ByteOrder.BIG_ENDIAN),

  /**
   * The little-endian.
   */
  LITTLE_ENDIAN(ByteOrder.LITTLE_ENDIAN);

  /**
   * Gets the network endianess.
   *
   * @return
   *     the network endianess, which is always {@link #BIG_ENDIAN}.
   */
  static Endian networkEndian() {
    return BIG_ENDIAN;
  }

  /**
   * Gets the native endianess of the host machine.
   *
   * @return
   *     the native endianess of the host machine, which is either
   *     {@link #BIG_ENDIAN} or {@link #LITTLE_ENDIAN}.
   */
  static Endian nativeEndian() {
    return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN ? BIG_ENDIAN : LITTLE_ENDIAN;
  }

  /**
   * Gets the endianness of a byte order.
   *
   * @param byteOrder
   *     the byte order.
   * @return
   *     the endianness of the byte order.
   */
  static Endian forByteOrder(final ByteOrder byteOrder) {
    if (byteOrder == ByteOrder.BIG_ENDIAN) {
      return BIG_ENDIAN;
    } else if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
      return LITTLE_ENDIAN;
    } else {
      throw new IllegalArgumentException("Unknown byte order: " + byteOrder);
    }
  }

  private final ByteOrder byteOrder;

  Endian(final ByteOrder byteOrder) {
    this.byteOrder = byteOrder;
  }

  /**
   * Gets the byte order of this endianness.
   *
   * @return the byte order of this endianness.
   */
  public ByteOrder byteOrder() {
    return byteOrder;
  }
}
