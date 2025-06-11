////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import javax.annotation.concurrent.ThreadSafe;

/**
 * 提供对 {@code byte} 类型进行位操作功能的工具类。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class ByteBit {

  /**
   * {@code byte} 类型的位数。
   */
  public static final int BITS = 8;

  /**
   * {@code byte} 类型的半位数。
   */
  public static final int HALF_BITS = 4;

  /**
   * {@code byte} 类型的半位掩码。
   */
  public static final int HALF_BITS_MASK = 0x0F;

  /**
   * {@code byte} 类型的全位掩码。
   */
  public static final int FULL_BITS_MASK = 0xFF;

  /**
   * {@code byte} 类型的最高有效位掩码。
   */
  public static final int MSB_MASK = 0x80;

  /**
   * 用于提取指定范围内位的掩码数组。
   */
  public static final int[] RANGE_MASK = {
      0x00, 0x01, 0x03, 0x07, 0x0F, 0x1F, 0x3F, 0x7F, 0xFF
  };

  /**
   * 用于提取单个位的掩码数组。
   */
  public static final int[] BIT_MASK = {
      0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80
  };

  /**
   * 位翻转查找表，用于快速翻转字节的位序。
   */
  public static final int[] REVERSE = {
      0x00, 0x80, 0x40, 0xC0, 0x20, 0xA0, 0x60, 0xE0, 0x10, 0x90, 0x50,
      0xD0, 0x30, 0xB0, 0x70, 0xF0, 0x08, 0x88, 0x48, 0xC8, 0x28, 0xA8,
      0x68, 0xE8, 0x18, 0x98, 0x58, 0xD8, 0x38, 0xB8, 0x78, 0xF8, 0x04,
      0x84, 0x44, 0xC4, 0x24, 0xA4, 0x64, 0xE4, 0x14, 0x94, 0x54, 0xD4,
      0x34, 0xB4, 0x74, 0xF4, 0x0C, 0x8C, 0x4C, 0xCC, 0x2C, 0xAC, 0x6C,
      0xEC, 0x1C, 0x9C, 0x5C, 0xDC, 0x3C, 0xBC, 0x7C, 0xFC, 0x02, 0x82,
      0x42, 0xC2, 0x22, 0xA2, 0x62, 0xE2, 0x12, 0x92, 0x52, 0xD2, 0x32,
      0xB2, 0x72, 0xF2, 0x0A, 0x8A, 0x4A, 0xCA, 0x2A, 0xAA, 0x6A, 0xEA,
      0x1A, 0x9A, 0x5A, 0xDA, 0x3A, 0xBA, 0x7A, 0xFA, 0x06, 0x86, 0x46,
      0xC6, 0x26, 0xA6, 0x66, 0xE6, 0x16, 0x96, 0x56, 0xD6, 0x36, 0xB6,
      0x76, 0xF6, 0x0E, 0x8E, 0x4E, 0xCE, 0x2E, 0xAE, 0x6E, 0xEE, 0x1E,
      0x9E, 0x5E, 0xDE, 0x3E, 0xBE, 0x7E, 0xFE, 0x01, 0x81, 0x41, 0xC1,
      0x21, 0xA1, 0x61, 0xE1, 0x11, 0x91, 0x51, 0xD1, 0x31, 0xB1, 0x71,
      0xF1, 0x09, 0x89, 0x49, 0xC9, 0x29, 0xA9, 0x69, 0xE9, 0x19, 0x99,
      0x59, 0xD9, 0x39, 0xB9, 0x79, 0xF9, 0x05, 0x85, 0x45, 0xC5, 0x25,
      0xA5, 0x65, 0xE5, 0x15, 0x95, 0x55, 0xD5, 0x35, 0xB5, 0x75, 0xF5,
      0x0D, 0x8D, 0x4D, 0xCD, 0x2D, 0xAD, 0x6D, 0xED, 0x1D, 0x9D, 0x5D,
      0xDD, 0x3D, 0xBD, 0x7D, 0xFD, 0x03, 0x83, 0x43, 0xC3, 0x23, 0xA3,
      0x63, 0xE3, 0x13, 0x93, 0x53, 0xD3, 0x33, 0xB3, 0x73, 0xF3, 0x0B,
      0x8B, 0x4B, 0xCB, 0x2B, 0xAB, 0x6B, 0xEB, 0x1B, 0x9B, 0x5B, 0xDB,
      0x3B, 0xBB, 0x7B, 0xFB, 0x07, 0x87, 0x47, 0xC7, 0x27, 0xA7, 0x67,
      0xE7, 0x17, 0x97, 0x57, 0xD7, 0x37, 0xB7, 0x77, 0xF7, 0x0F, 0x8F,
      0x4F, 0xCF, 0x2F, 0xAF, 0x6F, 0xEF, 0x1F, 0x9F, 0x5F, 0xDF, 0x3F,
      0xBF, 0x7F, 0xFF
  };

  /**
   * 位计数查找表，用于快速计算字节中设置为 1 的位数。
   */
  public static final int[] COUNT = {
      0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3,
      3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4,
      3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2,
      2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5,
      3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5,
      5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3,
      2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4,
      4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
      3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4,
      4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6,
      5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5,
      5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8
  };

  /**
   * 第一个设置位查找表，用于快速查找字节中第一个设置为 1 的位的位置。
   */
  public static final int[] FIRST_SET = {
      8, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0,
      1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0,
      2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 6, 0,
      1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0,
      3, 0, 1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0,
      1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 7, 0, 1, 0,
      2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0,
      1, 0, 2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
      4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 6, 0, 1, 0, 2, 0,
      1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0,
      2, 0, 1, 0, 5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0, 4, 0,
      1, 0, 2, 0, 1, 0
  };

  /**
   * 最后一个设置位查找表，用于快速查找字节中最后一个设置为 1 的位的位置。
   */
  public static final int[] LAST_SET = {
      8, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4,
      4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
      5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6,
      6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
      6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
      6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
      7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7
  };

  /**
   * 将 x 的值视为无符号字节返回。
   *
   * <p>注意：由于Java中没有无符号类型，该函数有时很有用。
   *
   * @param x
   *     应被视为无符号字节的字节值。
   * @return x 作为无符号字节的值。更准确地说，如果 x 是非负数，则返回 x 的值；
   *     否则，返回 (256 + x) 的值。
   */
  public static int asUnsigned(final byte x) {
    return (x & FULL_BITS_MASK);
  }

  /**
   * 返回 x 的最低 n 位。
   *
   * @param x
   *     值。
   * @param n
   *     要返回的最低位的数量。必须在 [0, BITS] 范围内。
   * @return 一个 {@code byte} 类型的值，其最低 n 位与 x 的最低 n 位相同，
   *     其他位为 0。如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，行为未定义。
   */
  public static byte low(final byte x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (byte) (x & RANGE_MASK[n]);
  }

  /**
   * 返回 x 的最高 n 位。
   *
   * @param x
   *     值。
   * @param n
   *     要返回的最高位的数量。必须在 [0, BITS] 范围内。
   * @return 一个 {@code byte} 类型的值，其最高 n 位与 x 的最高 n 位相同，
   *     其他位为 0。如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，行为未定义。
   */
  public static byte high(final byte x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (byte) ((x & FULL_BITS_MASK) >>> (BITS - n));
  }

  /**
   * 返回 x 的低半位。
   *
   * @param x
   *     值。
   * @return 一个 {@code byte} 类型的值，其低半位与 x 的低半位相同，
   *     其他位为 0。实际上，此函数返回 low(x, HALF_BITS)。
   */
  public static byte lowHalf(final byte x) {
    return (byte) (x & HALF_BITS_MASK);
  }

  /**
   * 返回 x 的高半位。
   *
   * @param x
   *     值。
   * @return 一个 {@code byte} 类型的值，其高半位与 x 的高半位相同，
   *     其他位为 0。实际上，此函数返回 high(x, HALF_BITS)。
   */
  public static byte highHalf(final byte x) {
    return (byte) ((x & FULL_BITS_MASK) >>> HALF_BITS);
  }

  /**
   * 交换 x 的最低 n 位和最高 (BITS - n) 位。即交换位范围 [0, n) 和 [n, BITS)。
   *
   * @param x
   *     要交换的值。
   * @param n
   *     与其余高位交换的最低位数量。必须在 [0, BITS] 范围内。
   * @return 一个新值，其最高 n 位是 x 的最低 n 位，最低 (BITS - n) 位是 x 的
   *     最高 (BITS - n) 位。如果 n == 0 或 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，函数的行为未定义。
   */
  public static byte rotate(final byte x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    final int m = BITS - n;
    final int ix = (x & FULL_BITS_MASK);
    final int ixl = (ix & RANGE_MASK[n]);
    final int ixh = (ix >>> m);
    return (byte) ((ixl << m) | ixh);
  }

  /**
   * 交换 x 的最低半位和最高半位。即交换位范围 [0, HALF_BITS) 和 [HALF_BITS, BITS)。
   *
   * @param x
   *     要交换的值。
   * @return 一个新值，其最高半位是 x 的最低半位，最低半位是 x 的最高半位。
   */
  public static byte rotateHalf(final byte x) {
    final int ix = (x & FULL_BITS_MASK);
    final int ixl = (ix & HALF_BITS_MASK);
    final int ixh = (ix >>> HALF_BITS);
    return (byte) ((ixl << HALF_BITS) | ixh);
  }

  /**
   * 交换 x 的第 i 位和第 j 位，返回新值。
   *
   * <p>例如，假设 IntType 是 unsigned char，x 是二进制的 00101111，
   * i = 1，j = 4，swap(x, i, j) 将 x 的第 1 位与第 4 位交换，
   * 得到 00111101。
   *
   * @param x
   *     原始值。
   * @param i
   *     要交换的第一个位的位置。必须在 [0, BITS) 范围内。
   * @param j
   *     要交换的第二个位的位置。必须在 [0, BITS) 范围内。
   * @return 一个新值，其第 i 位是 x 的第 j 位，第 j 位是 x 的第 i 位，
   *     其余位与 x 相同。如果 i == j，函数返回 x。
   */
  public static byte swap(final byte x, final int i, final int j) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (((ix >>> i) ^ (ix >>> j)) & 1);
    return (byte) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * 交换 x 的位范围 [i, i+n) 和位范围 [j, j+n)，返回新值。
   *
   * <p>例如，假设 byte 是 unsigned char，x 是二进制的 00101111，i = 1，
   * j = 5，n = 3，swapRange(x, i, j, n) 将 x 的第 1、2、3 位与第 5、6、7 位交换，
   * 或换句话说，交换以下表示中的第一个括号和第二个括号：
   * <pre>
   *    (001)0(111)1
   * </pre>
   * 得到：
   * <pre>
   *    (111)0(001)1
   * </pre>
   * 即二进制 11100011。
   *
   * @param x
   *     原始值。
   * @param i
   *     要交换的第一组位的起始位置。必须在 [0, BITS) 范围内。
   * @param j
   *     要交换的第二组位的起始位置。必须在 [0, BITS) 范围内。
   * @param n
   *     要交换的连续位的长度。
   * @return 一个新值，其位范围 [i, i+n) 是 x 的位范围 [j, j+n) 中的位，
   *     位范围 [j, j+n) 是 x 的位范围 [i, i+n) 中的位，其余位与 x 相同。
   *     如果 n == 0，函数返回 x。如果 i + n &gt; BITS 或 j + n &gt; BITS，
   *     即范围 [i, i+n) 或 [j, j+n) 超出范围 [0, BITS)，函数的行为未定义。
   *     如果 i == j，函数返回 x。如果 i &lt; j 且 i + n &gt; j，
   *     或如果 j &lt; i 且 j + n &gt; i，即两个范围 [i, i+n) 和 [j, j+n) 重叠，
   *     函数的行为未定义。
   */
  public static byte swapRange(final byte x, final int i, final int j, final int n) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS) && (n > 0)
        && (i + n <= BITS) && (j + n <= BITS) && ((i + n <= j) || (j + n
        <= i)));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (((ix >>> i) ^ (ix >>> j)) & RANGE_MASK[n]);
    return (byte) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * 翻转 x 中的位，即结果中位置 i 的位是 x 中位置 (BITS - 1 - i) 的位。
   *
   * <p>例如，byte 是二进制 unsigned char 01111011（注意保留前导 0）。
   * reverse(x) 得到 11011110。
   *
   * @param x
   *     要翻转的值。
   * @return 通过翻转 x 的位得到的新值。
   */
  public static byte reverse(final byte x) {
    return (byte) REVERSE[x & FULL_BITS_MASK];
  }

  /**
   * 根据掩码合并两个值的位。
   *
   * <p>对于结果值中的每个位位置 i，如果掩码的第 i 位是 0，则结果的第 i 位
   * 与 x 的第 i 位相同；如果掩码的第 i 位是 1，则结果的第 i 位与 y 的第 i 位相同。
   *
   * <p>例如，设 x = 10101010，y = 11001100，mask = 01110010，merge(x, y,
   * mask) 将得到结果 11001000（所有数字都是二进制形式）。
   *
   * @param x
   *     第一个操作数
   * @param y
   *     第二个操作数。
   * @param mask
   *     用于从 x 和 y 中选择位的掩码。如果掩码中的第 i 位是 0，
   *     则从 x 中选择第 i 位；否则，从 y 中选择第 i 位。
   * @return 根据掩码合并 x 和 y 的结果。
   */
  public static byte merge(final byte x, final byte y, final byte mask) {
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    return (byte) (x ^ ((x ^ y) & mask));
  }

  /**
   * 将 x 的所有位设置为 1。实际上，此函数将 x 设置为 ~T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static byte set(final byte x) {
    return (byte) FULL_BITS_MASK;
  }

  /**
   * 将 x 的第 i 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要设置的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为未定义。
   */
  public static byte set(final byte x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (byte) (x | BIT_MASK[i]);
  }

  /**
   * 将 x 的最高有效位设置为 1。
   *
   * <p>注意：有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static byte setMsb(final byte x) {
    return (byte) (x | MSB_MASK);
  }

  /**
   * 将 x 的最低 n 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最低位的数量。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static byte setLow(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x | RANGE_MASK[n]);
  }

  /**
   * 将 x 的最高 n 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最高位的数量。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static byte setHigh(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x | (~RANGE_MASK[BITS - n]));
  }

  /**
   * 将 x 的所有位设置为 0。实际上，此函数将 x 设置为 T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static byte unset(final byte x) {
    return 0;
  }

  /**
   * 将 x 的第 i 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要重置的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为未定义。
   */
  public static byte unset(final byte x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (byte) (x & (~BIT_MASK[i]));
  }

  /**
   * 将 x 的最高有效位重置为 0。
   *
   * <p>注意：有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static byte unsetMsb(final byte x) {
    return (byte) (x & (~MSB_MASK));
  }

  /**
   * 将 x 的最低 n 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最低位的数量。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static byte unsetLow(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x & (~RANGE_MASK[n]));
  }

  /**
   * 将 x 的最高 n 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最高位的数量。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static byte unsetHigh(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (byte) (x & RANGE_MASK[BITS - n]);
  }

  /**
   * 翻转 x 的所有位，即将 1 变为 0，0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static byte invert(final byte x) {
    return (byte) (~x);
  }

  /**
   * 翻转 x 的指定位，即将 1 变为 0，0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要翻转的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为未定义。
   */
  public static byte invert(final byte x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (byte) (x ^ BIT_MASK[i]);
  }

  /**
   * 翻转 x 的最高有效位。
   *
   * <p>注意：有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static byte invertMsb(final byte x) {
    return (byte) (x ^ MSB_MASK);
  }

  /**
   * 测试 x 的第 i 位。
   *
   * @param x
   *     值。
   * @param i
   *     要测试的 x 的位。必须在 [0, BITS) 范围内。
   * @return 如果 x 的第 i 位是 1 则返回 true，否则返回 false。如果 i &lt; 0 或 i &ge; BITS，
   *     函数的行为未定义。
   */
  public static boolean test(final byte x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return ((x & BIT_MASK[i]) != 0);
  }

  /**
   * 测试 x 的最高有效位。
   *
   * <p>注意：有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     操作数。
   * @return 如果 x 的最高有效位是 1 则返回 true，否则返回 false。
   */
  public static boolean testMsb(final byte x) {
    return ((x & MSB_MASK) != 0);
  }

  /**
   * 计算 x 值中设置的位数。
   *
   * @param x
   *     要计算位数的值。
   * @return x 中设置的位数。
   */
  public static int count(final byte x) {
    return COUNT[x & FULL_BITS_MASK];
  }

  /**
   * 测试 x 的所有位是否都设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 的所有位都设置为 1 则返回 true，否则返回 false。
   */
  public static boolean hasAll(final byte x) {
    return (x == (byte) FULL_BITS_MASK);
  }

  /**
   * 测试 x 是否有任何位设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 至少有一位设置为 1 则返回 true，否则返回 false。
   */
  public static boolean hasAny(final byte x) {
    return (x != 0);
  }

  /**
   * 测试 x 是否没有任何位设置为 1，即 x 的所有位都设置为 0。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 没有任何位设置为 1 则返回 true，否则返回 false。
   */
  public static boolean hasNone(final byte x) {
    return (x == 0);
  }

  /**
   * 查找 x 中最低的设置位（值为 1）。
   *
   * <p>例如，x 是二进制值为 00101010 的无符号字符，
   * findFirstSet(x) 将返回 1。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最低位置。如果 x 中所有位都是 0，则返回 BITS。
   */
  public static int findFirstSet(final byte x) {
    return FIRST_SET[x & FULL_BITS_MASK];
  }

  /**
   * 从位 n 开始查找 x 中最低的设置位（值为 1），即在位范围 [n, BITS) 内查找。
   *
   * <p>例如，x 是二进制值为 00101010 的无符号字符，
   * findFirstSet(x, 2) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     从该位开始搜索，即在 x 的位范围 [n, BITS) 内搜索。
   * @return x 中设置为 1 的位的最低位置。如果未找到这样的位置，则返回 BITS。
   *     如果 n &ge; BITS，函数的行为未定义。
   */
  public static int findFirstSet(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));

    return FIRST_SET[x & (~RANGE_MASK[n]) & FULL_BITS_MASK];
  }

  /**
   * 查找 x 中最低的未设置位（值为 0）。
   *
   * <p>例如，x 是二进制值为 00101011 的无符号字符，
   * findFirstUnset(x) 将返回 2。
   *
   * <p>此函数等价于 invert(x); return findFirstSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的位的最低位置。如果 x 中所有位都是 1，则返回 BITS。
   */
  public static int findFirstUnset(final byte x) {
    return FIRST_SET[(~x) & FULL_BITS_MASK];
  }

  /**
   * 从位 n 开始查找 x 中最低的未设置位（值为 0），即在位范围 [n, BITS) 内查找。
   *
   * <p>例如，x 是二进制值为 00111011 的无符号字符，
   * findFirstUnset(x, 3) 将返回 6。
   *
   * <p>此函数等价于 invert(x); return findFirstSet( x, n )。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     从该位开始搜索，即在 x 的位范围 [n, BITS) 内搜索。
   * @return x 中设置为 0 的位的最低位置。如果未找到这样的位置，则返回 BITS。
   *     如果 n &ge; BITS，函数的行为未定义。
   */
  public static int findFirstUnset(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));

    return FIRST_SET[(~(x | RANGE_MASK[n])) & FULL_BITS_MASK];
  }

  /**
   * 查找 x 中最高的设置位（值为 1）。
   *
   * <p>例如，x 是二进制值为 00101101 的无符号字符，findLastSet(x)
   * 将返回 5。
   *
   * <p>实际上，结果的值等于 (int) log_2(x)。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最高位置。如果 x 中所有位都是 0，则返回 BITS。
   */
  public static int findLastSet(final byte x) {
    return LAST_SET[x & FULL_BITS_MASK];
  }

  /**
   * 从位 0 开始到位 n（不包括）查找 x 中最高的设置位（值为 1），即在位范围 [0, n) 内查找。
   *
   * <p>例如，x 是二进制值为 00101101 的无符号字符，findLastSet(x,
   * 5) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索结束于该位，即在 x 的位范围 [0, n) 内搜索。
   * @return x 的位范围 [0, n) 中设置为 1 的最高位置。如果未找到这样的位置，则返回 BITS。
   *     如果 n == 0，返回 BITS。如果 n == BITS，返回 findLastSet(x)。
   *     如果 n &gt; BITS，行为未定义。
   */
  public static int findLastSet(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));

    return LAST_SET[x & RANGE_MASK[n] & FULL_BITS_MASK];
  }

  /**
   * 查找 x 中最高的未设置位（值为 0）。
   *
   * <p>例如，x 是二进制值为 11101011 的无符号字符，
   * findLastUnset(x) 将返回 4。
   *
   * <p>此函数等价于 invert(x); return findLastSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的位的最高位置。如果 x 中所有位都是 1，则返回 BITS。
   */
  public static int findLastUnset(final byte x) {
    return LAST_SET[(~x) & FULL_BITS_MASK];
  }

  /**
   * 从位 0 开始到位 n 查找 x 中最高的未设置位（值为 0），即在位范围 [0, n) 内查找。
   *
   * <p>例如，x 是二进制值为 10111011 的无符号字符，
   * findLastUnset(x, 6) 将返回 2。
   *
   * <p>此函数等价于 invert(x); return findLastSet(x, n);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索结束于该位，即在 x 的位范围 [0, n) 内搜索。
   * @return x 的位范围 [0, n) 中设置为 0 的最低位置。如果未找到这样的位置，
   *     则返回 BITS。如果 n == 0，返回 BITS。如果 n &gt; BITS，函数的行为未定义。
   */
  public static int findLastUnset(final byte x, final int n) {
    assert ((0 <= n) && (n < BITS));

    return LAST_SET[(~x) & RANGE_MASK[n] & FULL_BITS_MASK];
  }

  /**
   * Tests whether x is a subset of y, i.e., whether for every bit set in x, the
   * corresponding bit in y is also set.
   *
   * <p>Note that if x == y, x is a subset of y, but it is not a "proper" subset of
   * y.
   *
   * @param x
   *     The value to be test.
   * @param y
   *     The other value to be test.
   * @return true if x is a subset of y; false otherwise.
   */
  public static boolean isSubsetOf(final byte x, final byte y) {
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (y & FULL_BITS_MASK);
    return ((ix | iy) == iy);
  }

  /**
   * Tests whether x is a proper subset of y, i.e., whether for every bit set in
   * x, the corresponding bit in y is also set, and there is at least on bit set
   * in y but the corresponding bit is not set in x (or simply, x is not equal
   * to y).
   *
   * @param x
   *     The value to be test.
   * @param y
   *     The other value to be test.
   * @return true if x is a proper subset of y; false otherwise.
   */
  public static boolean isProperSubsetOf(final byte x, final byte y) {
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (y & FULL_BITS_MASK);
    return ((ix | iy) == iy) && (ix != iy);
  }

  /**
   * Lexically compares the bits.
   *
   * <p>Returns an integer less than, equal to or greater than 0, if the bits of x
   * starting from position 0 to position BITS - 1 compares lexicographically
   * less than, equal to, or greater than the bits of y starting from position 0
   * to position BITS - 1.
   *
   * <p>For example,
   *
   * <ul>
   * <li><code>3 = 0b00000011</code>, <code>5 = 0b00000101</code>, and therefore
   * <code>compare(3, 5) &lt; 0</code>. </li>
   * <li><code>3 = 0b00000011</code>, <code>-1 = 0b11111111</code>, and therefore
   * <code>compare(3, -1) &lt; 0</code>. </li>
   * </ul>
   *
   * @param x
   *     The first operand of comparision.
   * @param y
   *     The second operand of comparision.
   * @return The result of lexically comparision, as described above.
   */
  public static int compare(final byte x, final byte y) {
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (y & FULL_BITS_MASK);
    return ix - iy;
  }

  /**
   * Determinate whether the unsigned representation of an integer is a power of
   * 2. That is, whether the bits of the integer has only one 1 and all rest
   * bits are 0s.
   *
   * <p>Note that 0 is considered a power of 2, i.e., isPowerOfTwo(0) will returns
   * true.
   *
   * @param x
   *     The operand to be tested. Note that x is treated as a unsigned
   *     integer.
   * @return ture if x is a power of 2.
   */
  public static boolean isPowerOfTwo(final byte x) {
    return (x & (x - 1)) == 0;
  }

  /**
   * Returns the floor of log_2(x). In other words, return the largest n such
   * that 2^n &le; x &lt; 2^{n+1}.
   *
   * @param x
   *     The operand. Note that x is treated as a unsigned integer.
   * @return The floor of log_2(x). If x is 0, the behaviour is undefined.
   */
  public static int getLog2Floor(final byte x) {
    assert (x != 0);
    return LAST_SET[x & FULL_BITS_MASK];
  }

  /**
   * Returns the ceiling of log_2(x). In other words, return the smallest n such
   * that 2^{n-1} &lt; x &le; 2^{n}.
   *
   * @param x
   *     The operand. Note that x is treated as a unsigned integer.
   * @return The ceiling of log_2(x). If x is 0, the behaviour is undefined.
   */
  public static int getLog2Ceiling(final byte x) {
    assert (x != 0);
    // note that (x & (x - 1)) == 0 means x is a power of 2
    return LAST_SET[x & FULL_BITS_MASK] + ((x & (x - 1)) != 0 ? 1 : 0);
  }

  /**
   * Returns the number of digits in the binary representation of a specified
   * unsigned integer.
   *
   * @param x
   *     The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the binary representation of x. If x is 0,
   *     the function returns 1.
   */
  public static int getDigits2(final byte x) {
    return (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
  }

  /**
   * Returns the number of digits in the octal representation of a specified
   * unsigned integer.
   *
   * @param x
   *     The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the octal representation of x. If x is 0,
   *     the function returns 1.
   */
  public static int getDigits8(final byte x) {
    final int digit2 = (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
    return (digit2 + 2) / 3;
  }

  /**
   * Returns the number of digits in the decimal representation of a specified
   * unsigned integer.
   *
   * @param x
   *     The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the decimal representation of x. If x is 0,
   *     the function returns 1.
   */
  public static int getDigits10(final byte x) {
    // stop checkstyle: MagicNumberCheck
    // The fraction 643/2136 approximates log10(2) to 7 significant digits.
    final int digit2 = (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
    return (digit2 * 643) / 2136 + 1;
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * Returns the number of digits in the hexadecimal representation of a
   * specified unsigned integer.
   *
   * @param x
   *     The operand. Note that x is treated as a unsigned integer.
   * @return The number of digits in the hexadecimal representation of x. If x
   *     is 0, the function returns 1.
   */
  public static int getDigits16(final byte x) {
    final int digit2 = (x == 0 ? 1 : LAST_SET[x & FULL_BITS_MASK] + 1);
    return (digit2 + 3) / 4;
  }

}