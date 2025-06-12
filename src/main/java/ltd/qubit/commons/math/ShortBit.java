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
 * 提供对 {@code short} 类型进行位操作功能的工具类。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class ShortBit {

  /**
   * {@code short} 类型的位数。
   */
  public static final int BITS = 16;

  /**
   * {@code short} 类型的半位数。
   */
  public static final int HALF_BITS = 8;

  /**
   * {@code short} 类型的半位掩码。
   */
  public static final int HALF_BITS_MASK = 0x00FF;

  /**
   * {@code short} 类型的全位掩码。
   */
  public static final int FULL_BITS_MASK = 0xFFFF;

  /**
   * {@code short} 类型的最高位掩码。
   */
  public static final int MSB_MASK = 0x8000;

  /**
   * 范围掩码数组，用于获取指定数量的低位。
   */
  public static final int[] RANGE_MASK = {
      0x0000, 0x0001, 0x0003, 0x0007, 0x000F, 0x001F, 0x003F, 0x007F,
      0x00FF, 0x01FF, 0x03FF, 0x07FF, 0x0FFF, 0x1FFF, 0x3FFF, 0x7FFF,
      0xFFFF
  };

  /**
   * 位掩码数组，用于获取指定位置的位。
   */
  public static final int[] BIT_MASK = {
      0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0020, 0x0040, 0x0080,
      0x0100, 0x0200, 0x0400, 0x0800, 0x1000, 0x2000, 0x4000, 0x8000
  };

  /**
   * 将 x 的值作为无符号 short 返回。
   *
   * <p>注意，这个函数有时很有用，因为 Java 中没有无符号类型。
   *
   * @param x
   *     应被视为无符号 short 的 short 值。
   * @return x 作为无符号 short 的值。更确切地说，如果 x 是非负数，则返回 x 的值；
   *     否则，返回 (65536 + x) 的值。
   */
  public static int asUnsigned(final short x) {
    return (x & FULL_BITS_MASK);
  }

  /**
   * 返回 x 的最低 n 位。
   *
   * @param x
   *     值。
   * @param n
   *     要返回的最低位数量。它必须在 [0, BITS] 范围内。
   * @return {@code short} 类型的值，其最低 n 位与 x 的最低 n 位相同，
   *     其他位为 0。如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，行为是未定义的。
   */
  public static short low(final short x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (short) (x & RANGE_MASK[n]);
  }

  /**
   * 返回 x 的最高 n 位。
   *
   * @param x
   *     值。
   * @param n
   *     要返回的最高位数量。它必须在 [0, BITS] 范围内。
   * @return {@code short} 类型的值，其最高 n 位与 x 的最高 n 位相同，
   *     其他位为 0。如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，行为是未定义的。
   */
  public static short high(final short x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (short) ((x & FULL_BITS_MASK) >>> (BITS - n));
  }

  /**
   * 返回 x 的低半位。
   *
   * @param x
   *     值。
   * @return {@code short} 类型的值，其低半位与 x 的低半位相同，
   *     其他位为 0。实际上，这个函数返回 low(x, HALF_BITS)。
   */
  public static short lowHalf(final short x) {
    return (short) (x & HALF_BITS_MASK);
  }

  /**
   * 返回 x 的高半位。
   *
   * @param x
   *     值。
   * @return {@code short} 类型的值，其高半位与 x 的高半位相同，
   *     其他位为 0。实际上，这个函数返回 high(x, HALF_BITS)。
   */
  public static short highHalf(final short x) {
    return (short) ((x & FULL_BITS_MASK) >>> HALF_BITS);
  }

  /**
   * 交换 x 的最低 n 位和最高 (BITS - n) 位。即交换位范围 [0, n) 和 [n, BITS)。
   *
   * @param x
   *     要交换的值。
   * @param n
   *     与其余高位交换的最低位数量。它必须在 [0, BITS] 范围内。
   * @return 一个新值，其最高 n 位是 x 的最低 n 位，其最低 (BITS - n) 位是 x 的最高
   *     (BITS - n) 位。如果 n == 0 或 n == BITS，函数返回 x。如果 n &lt; 0 或
   *     n &gt; BITS，函数的行为是未定义的。
   */
  public static short rotate(final short x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    final int m = BITS - n;
    final int ix = (x & FULL_BITS_MASK);
    final int ixl = (ix & RANGE_MASK[n]);
    final int ixh = (ix >>> m);
    return (short) ((ixl << m) | ixh);
  }

  /**
   * 交换 x 的最低半位和最高半位。即交换位范围 [0, HALF_BITS) 和 [HALF_BITS, BITS)。
   *
   * @param x
   *     要交换的值。
   * @return 一个新值，其最高半位是 x 的最低半位，其最低半位是 x 的最高半位。
   */
  public static short rotateHalf(final short x) {
    final int ix = (x & FULL_BITS_MASK);
    final int ixl = (ix & HALF_BITS_MASK);
    final int ixh = (ix >>> HALF_BITS);
    return (short) ((ixl << HALF_BITS) | ixh);
  }

  /**
   * 交换 x 的第 i 位和第 j 位，返回新值。
   *
   * <p>例如，假设 IntType 是无符号字符，x 是二进制的 00101111，i = 1，j = 4，
   * swap(x, i, j) 将 x 的第 1 位与第 4 位交换，得到 00111101。
   *
   * @param x
   *     原始值。
   * @param i
   *     要交换的第一个位的位置。它必须在 [0, BITS) 范围内。
   * @param j
   *     要交换的第二个位的位置。它必须在 [0, BITS) 范围内。
   * @return 一个新值，其第 i 位是 x 的第 j 位，其第 j 位是 x 的第 i 位，
   *     其余位与 x 相同。如果 i == j，函数返回 x。
   */
  public static short swap(final short x, final int i, final int j) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS));
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (((ix >>> i) ^ (ix >>> j)) & 1);
    return (short) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * 交换 x 的位范围 [i, i+n) 和位范围 [j, j+n)，返回新值。
   *
   * <p>例如，假设 short 是无符号字符，x 是二进制的 00101111，i = 1，j = 5，n = 3，
   * swapRange(x, i, j, n) 将 x 的第 1,2,3 位与第 5,6,7 位交换，或者换句话说，
   * 交换以下表示中的第一个括号和第二个括号：
   * <pre>
   * (001)0(111)1
   * </pre>
   *
   * <p>得到
   * <pre>
   * (111)0(001)1
   * </pre>
   *
   * <p>即二进制 11100011。
   *
   * @param x
   *     原始值。
   * @param i
   *     要交换的第一个位的起始位置。它必须在 [0, BITS) 范围内。
   * @param j
   *     要交换的第二个位的起始位置。它必须在 [0, BITS) 范围内。
   * @param n
   *     要交换的连续位的长度。
   * @return 一个新值，其位范围 [i, i+n) 中的位是 x 的位范围 [j, j+n) 中的位，
   *     其位范围 [j, j+n) 中的位是 x 的位范围 [i, i+n) 中的位，其余位与 x 相同。
   *     如果 n == 0，函数返回 x。如果 i + n &gt; BITS 或 j + n &gt; BITS，
   *     即范围 [i, i+n) 或 [j, j+n) 超出范围 [0, BITS)，函数的行为是未定义的。
   *     如果 i == j，函数返回 x。如果 i &lt; j 且 i + n &gt; j，或者
   *     j &lt; i 且 j + n &gt; i，即两个范围 [i, i+n) 和 [j, j+n) 重叠，
   *     函数的行为是未定义的。
   */
  public static short swapRange(final short x, final int i, final int j,
      final int n) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS) && (n > 0)
        && (i + n <= BITS) && (j + n <= BITS) && ((i + n <= j) || (j + n
        <= i)));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (((ix >>> i) ^ (ix >>> j)) & RANGE_MASK[n]);
    return (short) (ix ^ ((iy << i) | (iy << j)));
  }

  /**
   * 反转 x 中的位，即结果中位置 i 的位是 x 中位置 (BITS - 1 - i) 的位。
   *
   * <p>例如，short 是二进制无符号字符 01111011（注意保留前导 0）。
   * reverse(x) 得到 11011110。
   *
   * @param x
   *     要反转的值。
   * @return 通过反转 x 的位得到的新值。
   */
  public static short reverse(final short x) {
    final int ix = (x & FULL_BITS_MASK);
    final int x0 = ByteBit.REVERSE[ix & ByteBit.FULL_BITS_MASK];
    final int x1 = ByteBit.REVERSE[ix >>> ByteBit.BITS];

    return (short) ((x0 << ByteBit.BITS) | x1);
  }

  /**
   * 根据掩码合并两个值的位。
   *
   * <p>对于结果值中的每个位位置 i，如果掩码的第 i 位是 0，则结果的第 i 位与 x 的第 i 位相同；
   * 如果掩码的第 i 位是 1，则结果的第 i 位与 y 的第 i 位相同。
   *
   * <p>例如，设 x = 10101010，y = 11001100，mask = 01110010，
   * merge(x, y, mask) 将得到结果 11001000（所有数字均为二进制形式）。
   *
   * @param x
   *     第一个操作数。
   * @param y
   *     第二个操作数。
   * @param mask
   *     用于从 x 和 y 中选择位的掩码。如果掩码中的第 i 位是 0，
   *     则从 x 中选择第 i 位；否则，从 y 中选择第 i 位。
   * @return 根据掩码合并 x 和 y 的结果。
   */
  public static short merge(final short x, final short y, final short mask) {
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    return (short) (x ^ ((x ^ y) & mask));
  }

  /**
   * 将 x 的所有位设置为 1。实际上，这个函数将 x 设置为 ~T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static short set(final short x) {
    return (short) FULL_BITS_MASK;
  }

  /**
   * 将 x 的第 i 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要设置的 x 的位。它必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为是未定义的。
   */
  public static short set(final short x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (short) (x | BIT_MASK[i]);
  }

  /**
   * 将 x 的最高有效位设置为 1。
   *
   * <p>注意，有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static short setMsb(final short x) {
    return (short) (x | MSB_MASK);
  }

  /**
   * 将 x 的最低 n 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最低位数量。它必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static short setLow(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x | RANGE_MASK[n]);
  }

  /**
   * 将 x 的最高 n 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最高位数量。它必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static short setHigh(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x | (~RANGE_MASK[BITS - n]));
  }

  /**
   * 将 x 的所有位设置为 0。实际上，这个函数将 x 设置为 T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static short unset(final short x) {
    return 0;
  }

  /**
   * 将 x 的第 i 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要重置的 x 的位。它必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为是未定义的。
   */
  public static short unset(final short x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (short) (x & (~BIT_MASK[i]));
  }

  /**
   * 将 x 的最高有效位重置为 0。
   *
   * <p>注意，有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static short unsetMsb(final short x) {
    return (short) (x & (~MSB_MASK));
  }

  /**
   * 将 x 的最低 n 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最低位数量。它必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static short unsetLow(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x & (~RANGE_MASK[n]));
  }

  /**
   * 将 x 的最高 n 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最高位数量。它必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static short unsetHigh(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (short) (x & RANGE_MASK[BITS - n]);
  }

  /**
   * 反转 x 的所有位，即将 1 变为 0，将 0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static short invert(final short x) {
    return (short) (~x);
  }

  /**
   * 反转 x 的指定位，即将 1 变为 0，将 0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要翻转的 x 的位。它必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为是未定义的。
   */
  public static short invert(final short x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (short) (x ^ BIT_MASK[i]);
  }

  /**
   * 反转 x 的最高有效位。
   *
   * <p>注意，有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static short invertMsb(final short x) {
    return (short) (x ^ MSB_MASK);
  }

  /**
   * 测试 x 的第 i 位。
   *
   * @param x
   *     值。
   * @param i
   *     要测试的 x 的位。它必须在 [0, BITS) 范围内。
   * @return 如果 x 的第 i 位是 1，则为 true，否则为 false。
   *     如果 i &lt; 0 或 i &ge; BITS，函数的行为是未定义的。
   */
  public static boolean test(final short x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return ((x & BIT_MASK[i]) != 0);
  }

  /**
   * 测试 x 的最高有效位。
   *
   * <p>注意，有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     操作数。
   * @return 如果 x 的最高有效位是 1，则为 true，否则为 false。
   */
  public static boolean testMsb(final short x) {
    return ((x & MSB_MASK) != 0);
  }

  /**
   * 计算 x 值中设置的位数。
   *
   * @param x
   *     要计算位数的值。
   * @return x 中设置的位数。
   */
  public static int count(final short x) {
    final int ix = (x & FULL_BITS_MASK);
    final int x0 = (ix & ByteBit.FULL_BITS_MASK);
    final int x1 = (ix >>> ByteBit.BITS);

    return ByteBit.COUNT[x0] + ByteBit.COUNT[x1];
  }

  /**
   * 测试 x 的所有位是否都设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 的所有位都设置为 1，则为 true，否则为 false。
   */
  public static boolean hasAll(final short x) {
    return (x == (short) FULL_BITS_MASK);
  }

  /**
   * 测试 x 是否有任何位设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 至少有一位设置为 1，则为 true，否则为 false。
   */
  public static boolean hasAny(final short x) {
    return (x != 0);
  }

  /**
   * 测试 x 的位是否都没有设置为 1，即 x 的所有位都设置为 0。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 的位都没有设置为 1，则为 true，否则为 false。
   */
  public static boolean hasNone(final short x) {
    return (x == 0);
  }

  /**
   * 查找 x 中最低的设置位（值为 1 的位）。
   *
   * <p>例如，x 是二进制值 00101010 的无符号字符，findFirstSet(x) 将返回 1。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最低位置。如果 x 中的所有位都是 0，则返回 BITS。
   */
  public static int findFirstSet(final short x) {
    return findFirstSetImpl(x & FULL_BITS_MASK);
  }

  /**
   * 从第 n 位开始查找 x 中最低的设置位（值为 1 的位），即在位范围 [n, BITS) 中。
   *
   * <p>例如，x 是二进制值 00101010 的无符号字符，findFirstSet(x, 2) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索从这一位开始，即在 x 的位范围 [n, BITS) 中搜索。
   * @return x 中设置为 1 的位的最低位置。如果没有找到这样的位置，则返回 -1。
   *     如果 n &ge; BITS，函数的行为是未定义的。
   */
  public static int findFirstSet(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));

    // clear the lowest n bits of x
    final int x0 = (x & (~RANGE_MASK[n]) & FULL_BITS_MASK);
    return findFirstSetImpl(x0);
  }

  /**
   * findFirstSet 方法的内部实现。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最低位置。
   */
  static int findFirstSetImpl(final int x) {
    final int x0 = (x & HALF_BITS_MASK);
    if (x0 == 0) {
      return ByteBit.FIRST_SET[x >>> HALF_BITS] + HALF_BITS;
    } else {
      return ByteBit.FIRST_SET[x0];
    }
  }

  /**
   * 查找 x 中最低的未设置位（值为 0 的位）。
   *
   * <p>例如，x 是二进制值 00101011 的无符号字符，findFirstUnset(x) 将返回 2。
   *
   * <p>这个函数等价于 invert(x); return findFirstSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的位的最低位置。如果 x 中的所有位都是 1，则返回 -1。
   */
  public static int findFirstUnset(final short x) {
    // inverse x.
    final int x0 = ((~x) & FULL_BITS_MASK);
    return findFirstSetImpl(x0);
  }

  /**
   * 从第 n 位开始查找 x 中最低的未设置位（值为 0 的位），即在位范围 [n, BITS) 中。
   *
   * <p>例如，x 是二进制值 00111011 的无符号字符，findFirstUnset(x, 3) 将返回 6。
   *
   * <p>这个函数等价于 invert(x); return findFirstSet( x, n );
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索从这一位开始，即在 x 的位范围 [n, BITS) 中搜索。
   * @return x 中设置为 0 的位的最低位置。如果没有找到这样的位置，则返回 -1。
   *     如果 n &ge; BITS，函数的行为是未定义的。
   */
  public static int findFirstUnset(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final int x0 = ((~(x | RANGE_MASK[n])) & FULL_BITS_MASK);
    return findFirstSetImpl(x0);
  }

  /**
   * 查找 x 中最高的设置位（值为 1 的位）。
   *
   * <p>例如，x 是二进制值 00101101 的无符号字符，findLastSet(x) 将返回 5。
   *
   * <p>实际上，结果的值等于 (int) log_2(x)。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最高位置。如果 x 中的所有位都是 0，则返回 -1。
   */
  public static int findLastSet(final short x) {
    return findLastSetImpl(x & FULL_BITS_MASK);
  }

  /**
   * 从第 0 位开始到第 n 位（不包括）查找 x 中最高的设置位（值为 1 的位），
   * 即在位范围 [0, n) 中。
   *
   * <p>例如，x 是二进制值 00101101 的无符号字符，findLastSet(x, 5) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索结束于这一位，即在 x 的位范围 [0, n) 中搜索。
   * @return x 的位范围 [0, n) 中设置为 1 的最高位置。如果没有找到这样的位置，
   *     则返回 BITS。如果 n == 0，则返回 -1。如果 n == BITS，则返回 findLastSet(x)。
   *     如果 n &gt; BITS，行为是未定义的。
   */
  public static int findLastSet(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final int x0 = (x & RANGE_MASK[n] & FULL_BITS_MASK);
    return findLastSetImpl(x0);
  }

  /**
   * findLastSet 方法的内部实现。
   *
   * @param x
   *     要查找最后设置位的值。
   * @return x 中设置为 1 的位的最高位置。
   */
  static int findLastSetImpl(final int x) {
    if (x == 0) {
      return BITS;
    } else {
      final int x0 = (x >>> HALF_BITS);
      if (x0 == 0) { // find in the lower 16 bits.
        return ByteBit.LAST_SET[x & HALF_BITS_MASK];
      } else { // find in the higher 16 bits.
        return ByteBit.LAST_SET[x0] + HALF_BITS;
      }
    }
  }

  /**
   * 查找 x 中最高的未设置位（值为 0 的位）。
   *
   * <p>例如，x 是二进制值 11101011 的无符号字符，findLastUnset(x) 将返回 4。
   *
   * <p>这个函数等价于 invert(x); return findLastSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的位的最高位置。如果 x 中的所有位都是 1，则返回 -1。
   */
  public static int findLastUnset(final short x) {
    final int x0 = ((~x) & FULL_BITS_MASK);
    return findLastSetImpl(x0);
  }

  /**
   * 从第 0 位开始到第 n 位查找 x 中最高的未设置位（值为 0 的位），
   * 即在位范围 [0, n) 中。
   *
   * <p>例如，x 是二进制值 10111011 的无符号字符，findLastUnset(x, 6) 将返回 2。
   *
   * <p>这个函数等价于 invert(x); return findLastSet(x, n);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索结束于这一位，即在 x 的位范围 [0, n) 中搜索。
   * @return x 的位范围 [0, n) 中设置为 0 的最低位置。如果没有找到这样的位置，
   *     则返回 -1。如果 n == 0，则返回 -1。如果 n &gt; BITS，函数的行为是未定义的。
   */
  public static int findLastUnset(final short x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final int x0 = ((~x) & RANGE_MASK[n] & FULL_BITS_MASK);
    return findLastSetImpl(x0);
  }

  /**
   * 测试 x 是否是 y 的子集，即对于 x 中设置的每一位，y 中相应的位也被设置。
   *
   * <p>注意，如果 x == y，则 x 是 y 的子集，但它不是 y 的"真子集"。
   *
   * @param x
   *     要测试的值。
   * @param y
   *     要测试的另一个值。
   * @return 如果 x 是 y 的子集，则为 true；否则为 false。
   */
  public static boolean isSubsetOf(final short x, final short y) {
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (y & FULL_BITS_MASK);
    return ((ix | iy) == iy);
  }

  /**
   * 测试 x 是否是 y 的真子集，即对于 x 中设置的每一位，y 中相应的位也被设置，
   * 并且 y 中至少有一位被设置但 x 中相应的位没有被设置（或者简单地说，x 不等于 y）。
   *
   * @param x
   *     要测试的值。
   * @param y
   *     要测试的另一个值。
   * @return 如果 x 是 y 的真子集，则为 true；否则为 false。
   */
  public static boolean isProperSubsetOf(final short x, final short y) {
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (y & FULL_BITS_MASK);
    return ((ix | iy) == iy) && (ix != iy);
  }

  /**
   * 按字典序比较位。
   *
   * <p>返回一个小于、等于或大于 0 的整数，如果从位置 0 到位置 BITS - 1 的 x 的位
   * 按字典序比较小于、等于或大于从位置 0 到位置 BITS - 1 的 y 的位。
   *
   * <p>例如，
   * <ul>
   * <li><code>3 = 0b00000011</code>，<code>5 = 0b00000101</code>，
   * 因此 <code>compare(3, 5) &lt; 0</code>。</li>
   * <li><code>3 = 0b00000011</code>，<code>-1 = 0b11111111</code>，
   * 因此 <code>compare(3, -1) &lt; 0</code>。</li>
   * </ul>
   *
   * @param x
   *     比较的第一个操作数。
   * @param y
   *     比较的第二个操作数。
   * @return 字典序比较的结果，如上所述。
   */
  public static int compare(final short x, final short y) {
    final int ix = (x & FULL_BITS_MASK);
    final int iy = (y & FULL_BITS_MASK);
    return ix - iy;
  }

  /**
   * 确定整数的无符号表示是否是 2 的幂。即，整数的位是否只有一个 1，其余位都是 0。
   *
   * <p>注意，0 被认为是 2 的幂，即 isPowerOfTwo(0) 将返回 true。
   *
   * @param x
   *     要测试的操作数。注意 x 被视为无符号整数。
   * @return 如果 x 是 2 的幂，则为 true。
   */
  public static boolean isPowerOfTwo(final short x) {
    return (x & (x - 1)) == 0;
  }

  /**
   * 返回 log_2(x) 的下界。换句话说，返回满足 2^n &le; x &lt; 2^{n+1} 的最大 n。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return log_2(x) 的下界。如果 x 是 0，则返回 -1。
   */
  public static int getLog2Floor(final short x) {
    assert (x != 0);
    return findLastSet(x);
  }

  /**
   * 返回 log_2(x) 的上界。换句话说，返回满足 2^{n-1} &lt; x &le; 2^{n} 的最小 n。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return log_2(x) 的上界。如果 x 是 0，则返回 -1。
   */
  public static int getLog2Ceiling(final short x) {
    assert (x != 0);
    // note that (x & (x - 1)) == 0 means x is a power of 2
    final int ix = (x & FULL_BITS_MASK);
    return findLastSetImpl(ix) + ((ix & (ix - 1)) != 0 ? 1 : 0);
  }

  /**
   * 返回指定无符号整数的二进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return x 的二进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits2(final short x) {
    return (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
  }

  /**
   * 返回指定无符号整数的八进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return x 的八进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits8(final short x) {
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
    return (digit2 + 2) / 3;
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 返回指定无符号整数的十进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return x 的十进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits10(final short x) {
    // The fraction 643/2136 approximates log10(2) to 7 significant digits.
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
    return (digit2 * 643) / 2136 + 1;
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 返回指定无符号整数的十六进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return x 的十六进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits16(final short x) {
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSetImpl(x & FULL_BITS_MASK) + 1);
    return (digit2 + 3) / 4;
    // resume checkstyle: MagicNumberCheck
  }
}