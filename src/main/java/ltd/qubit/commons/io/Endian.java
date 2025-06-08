////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.nio.ByteOrder;

/**
 * 字节序的枚举。
 * <p>
 * 此枚举用于指示二进制数据的字节序。它类似于 {@link java.nio.ByteOrder}，
 * 但它表示为枚举而不是类，使用起来更方便。
 *
 * @see java.nio.ByteOrder
 * @see <a href="https://en.wikipedia.org/wiki/Endianness">Endianness</a>
 * @author 胡海星
 */
public enum Endian {

  /**
   * 大端字节序。
   */
  BIG_ENDIAN(ByteOrder.BIG_ENDIAN),

  /**
   * 小端字节序。
   */
  LITTLE_ENDIAN(ByteOrder.LITTLE_ENDIAN);

  /**
   * 获取网络字节序。
   *
   * @return
   *     网络字节序，始终为 {@link #BIG_ENDIAN}。
   */
  static Endian networkEndian() {
    return BIG_ENDIAN;
  }

  /**
   * 获取主机的本机字节序。
   *
   * @return
   *     主机的本机字节序，为 {@link #BIG_ENDIAN} 或 {@link #LITTLE_ENDIAN}。
   */
  static Endian nativeEndian() {
    return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN ? BIG_ENDIAN : LITTLE_ENDIAN;
  }

  /**
   * 获取字节顺序的字节序。
   *
   * @param byteOrder
   *     字节顺序。
   * @return
   *     字节顺序的字节序。
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

  /**
   * 构造一个表示字节序的枚举。
   *
   * @param byteOrder
   *     字节顺序。
   */
  Endian(final ByteOrder byteOrder) {
    this.byteOrder = byteOrder;
  }

  /**
   * 获取此字节序的字节顺序。
   *
   * @return
   *     此字节序的字节顺序。
   */
  public ByteOrder byteOrder() {
    return byteOrder;
  }
}