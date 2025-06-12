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
 * 提供对 {@code long} 类型进行位操作功能的工具类。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class LongBit {

  public static final int BITS = 64;
  public static final int HALF_BITS = 32;
  public static final long HALF_BITS_MASK = 0x00000000FFFFFFFFL;
  public static final long FULL_BITS_MASK = 0xFFFFFFFFFFFFFFFFL;
  public static final long MSB_MASK = 0x8000000000000000L;

  public static final long[] RANGE_MASK = {
      0x0000000000000000L, 0x0000000000000001L, 0x0000000000000003L,
      0x0000000000000007L, 0x000000000000000FL, 0x000000000000001FL,
      0x000000000000003FL, 0x000000000000007FL, 0x00000000000000FFL,
      0x00000000000001FFL, 0x00000000000003FFL, 0x00000000000007FFL,
      0x0000000000000FFFL, 0x0000000000001FFFL, 0x0000000000003FFFL,
      0x0000000000007FFFL, 0x000000000000FFFFL, 0x000000000001FFFFL,
      0x000000000003FFFFL, 0x000000000007FFFFL, 0x00000000000FFFFFL,
      0x00000000001FFFFFL, 0x00000000003FFFFFL, 0x00000000007FFFFFL,
      0x0000000000FFFFFFL, 0x0000000001FFFFFFL, 0x0000000003FFFFFFL,
      0x0000000007FFFFFFL, 0x000000000FFFFFFFL, 0x000000001FFFFFFFL,
      0x000000003FFFFFFFL, 0x000000007FFFFFFFL, 0x00000000FFFFFFFFL,
      0x00000001FFFFFFFFL, 0x00000003FFFFFFFFL, 0x00000007FFFFFFFFL,
      0x0000000FFFFFFFFFL, 0x0000001FFFFFFFFFL, 0x0000003FFFFFFFFFL,
      0x0000007FFFFFFFFFL, 0x000000FFFFFFFFFFL, 0x000001FFFFFFFFFFL,
      0x000003FFFFFFFFFFL, 0x000007FFFFFFFFFFL, 0x00000FFFFFFFFFFFL,
      0x00001FFFFFFFFFFFL, 0x00003FFFFFFFFFFFL, 0x00007FFFFFFFFFFFL,
      0x0000FFFFFFFFFFFFL, 0x0001FFFFFFFFFFFFL, 0x0003FFFFFFFFFFFFL,
      0x0007FFFFFFFFFFFFL, 0x000FFFFFFFFFFFFFL, 0x001FFFFFFFFFFFFFL,
      0x003FFFFFFFFFFFFFL, 0x007FFFFFFFFFFFFFL, 0x00FFFFFFFFFFFFFFL,
      0x01FFFFFFFFFFFFFFL, 0x03FFFFFFFFFFFFFFL, 0x07FFFFFFFFFFFFFFL,
      0x0FFFFFFFFFFFFFFFL, 0x1FFFFFFFFFFFFFFFL, 0x3FFFFFFFFFFFFFFFL,
      0x7FFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL
  };

  public static final long[] BIT_MASK = {
      0x0000000000000001L, 0x0000000000000002L, 0x0000000000000004L,
      0x0000000000000008L, 0x0000000000000010L, 0x0000000000000020L,
      0x0000000000000040L, 0x0000000000000080L, 0x0000000000000100L,
      0x0000000000000200L, 0x0000000000000400L, 0x0000000000000800L,
      0x0000000000001000L, 0x0000000000002000L, 0x0000000000004000L,
      0x0000000000008000L, 0x0000000000010000L, 0x0000000000020000L,
      0x0000000000040000L, 0x0000000000080000L, 0x0000000000100000L,
      0x0000000000200000L, 0x0000000000400000L, 0x0000000000800000L,
      0x0000000001000000L, 0x0000000002000000L, 0x0000000004000000L,
      0x0000000008000000L, 0x0000000010000000L, 0x0000000020000000L,
      0x0000000040000000L, 0x0000000080000000L, 0x0000000100000000L,
      0x0000000200000000L, 0x0000000400000000L, 0x0000000800000000L,
      0x0000001000000000L, 0x0000002000000000L, 0x0000004000000000L,
      0x0000008000000000L, 0x0000010000000000L, 0x0000020000000000L,
      0x0000040000000000L, 0x0000080000000000L, 0x0000100000000000L,
      0x0000200000000000L, 0x0000400000000000L, 0x0000800000000000L,
      0x0001000000000000L, 0x0002000000000000L, 0x0004000000000000L,
      0x0008000000000000L, 0x0010000000000000L, 0x0020000000000000L,
      0x0040000000000000L, 0x0080000000000000L, 0x0100000000000000L,
      0x0200000000000000L, 0x0400000000000000L, 0x0800000000000000L,
      0x1000000000000000L, 0x2000000000000000L, 0x4000000000000000L,
      0x8000000000000000L
  };

  /**
   * 返回 x 的最低 n 位。
   *
   * @param x
   *     待操作的值。
   * @param n
   *     要返回的最低位数。必须在 [0, BITS] 范围内。
   * @return 一个 {@code long} 类型的值，其最低 n 位与 x 的最低 n 位相同，其他位为 0。
   *     如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，函数行为未定义。
   */
  public static long low(final long x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (x & RANGE_MASK[n]);
  }

  /**
   * 返回 x 的最高 n 位。
   *
   * @param x
   *     待操作的值。
   * @param n
   *     要返回的最高位数。必须在 [0, BITS] 范围内。
   * @return 一个 {@code long} 类型的值，其最高 n 位与 x 的最高 n 位相同，其他位为 0。
   *     如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，函数行为未定义。
   */
  public static long high(final long x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (x >>> (BITS - n));
  }

  /**
   * 返回 x 的低半位。
   *
   * @param x
   *     待操作的值。
   * @return 一个 {@code long} 类型的值，其低半位与 x 的低半位相同，其他位为 0。
   *     实际上，该函数返回 low(x, HALF_BITS)。
   */
  public static long lowHalf(final long x) {
    return (x & HALF_BITS_MASK);
  }

  /**
   * 返回 x 的高半位。
   *
   * @param x
   *     待操作的值。
   * @return 一个 {@code long} 类型的值，其高半位与 x 的高半位相同，其他位为 0。
   *     实际上，该函数返回 high(x, HALF_BITS)。
   */
  public static long highHalf(final long x) {
    return (x >>> HALF_BITS);
  }

  /**
   * 交换 x 的最低 n 位和最高 (BITS - n) 位。即交换位范围 [0, n) 和 [n, BITS)。
   *
   * @param x
   *     要交换的值。
   * @param n
   *     要与其余高位交换的最低位数。必须在 [0, BITS] 范围内。
   * @return 一个新值，其最高 n 位是 x 的最低 n 位，其最低 (BITS - n) 位是 x 的最高 (BITS - n) 位。
   *     如果 n == 0 或 n == BITS，函数返回 x。
   *     如果 n &lt; 0 或 n &gt; BITS，函数行为未定义。
   */
  public static long rotate(final long x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    final int m = BITS - n;
    final long xl = (x & RANGE_MASK[n]);
    final long xh = (x >>> m);
    return ((xl << m) | xh);
  }

  /**
   * 交换 x 的最低半位和最高半位。即交换位范围 [0, HALF_BITS) 和 [HALF_BITS, BITS)。
   *
   * @param x
   *     要交换的值。
   * @return 一个新值，其最高半位是 x 的最低半位，其最低半位是 x 的最高半位。
   */
  public static long rotateHalf(final long x) {
    final long xl = (x & HALF_BITS_MASK);
    final long xh = (x >>> HALF_BITS);
    return ((xl << HALF_BITS) | xh);
  }

  /**
   * 交换 x 的第 i 位和第 j 位，返回新值。
   *
   * <p>例如，假设 IntType 是 unsigned char，x 是二进制 00101111，i = 1，j = 4，
   * swap(x, i, j) 将交换 x 的第 1 位和第 4 位，得到 00111101。
   *
   * @param x
   *     原始值。
   * @param i
   *     要交换的第一个位的位置。必须在 [0, BITS) 范围内。
   * @param j
   *     要交换的第二个位的位置。必须在 [0, BITS) 范围内。
   * @return 一个新值，其第 i 位是 x 的第 j 位，第 j 位是 x 的第 i 位，其余位与 x 相同。
   *     如果 i == j，函数返回 x。
   */
  public static long swap(final long x, final int i, final int j) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final long y = (((x >>> i) ^ (x >>> j)) & 1);
    return (x ^ ((y << i) | (y << j)));
  }

  /**
   * 交换 x 的位范围 [i, i+n) 和位范围 [j, j+n)，返回新值。
   *
   * <p>例如，假设 int 是 unsigned char，x 是二进制 00101111，i = 1，j = 5，n = 3，
   * swapRange(x, i, j, n) 将交换 x 的第 1,2,3 位和第 5,6,7 位，换句话说，
   * 交换以下表示中的第一个括号和第二个括号：
   * <pre>
   * (001)0(111)1
   * </pre>
   * 得到
   * <pre>
   * (111)0(001)1
   * </pre>
   * 即二进制 11100011。
   *
   * @param x
   *     原始值。
   * @param i
   *     要交换的第一个位的起始位置。必须在 [0, BITS) 范围内。
   * @param j
   *     要交换的第二个位的起始位置。必须在 [0, BITS) 范围内。
   * @param n
   *     要交换的连续位的长度。
   * @return 一个新值，其范围 [i, i+n) 中的位是 x 中范围 [j, j+n) 的位，
   *     其范围 [j, j+n) 中的位是 x 中范围 [i, i+n) 的位，其余位与 x 相同。
   *     如果 n == 0，函数返回 x。如果 i + n &gt; BITS 或 j + n &gt; BITS，
   *     即范围 [i, i+n) 或 [j, j+n) 超出范围 [0, BITS)，函数行为未定义。
   *     如果 i == j，函数返回 x。如果 i &lt; j 且 i + n &gt; j，
   *     或如果 j &lt; i 且 j + n &gt; i，即两个范围 [i, i+n) 和 [j, j+n) 重叠，
   *     函数行为未定义。
   */
  public static long swapRange(final long x, final int i, final int j, final int n) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS) && (n > 0)
        && (i + n <= BITS) && (j + n <= BITS) && ((i + n <= j) || (j + n
        <= i)));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final long y = (((x >>> i) ^ (x >>> j)) & RANGE_MASK[n]);
    return (x ^ ((y << i) | (y << j)));
  }

  /**
   * 反转 x 中的位，即结果中位置 i 处的位是 x 中位置 (BITS - 1 - i) 处的位。
   *
   * <p>例如，int 是二进制 unsigned char 01111011（注意保留前导 0）。
   * reverse(x) 得到 11011110。
   *
   * @param x
   *     要反转的值。
   * @return 通过反转 x 的位得到的新值。
   */
  public static long reverse(final long x) {
    final int x0 = (int) x;
    final int x1 = (int) (x >>> HALF_BITS);
    final long rx0 = ((long) IntBit.reverse(x0)) & IntBit.FULL_BITS_MASK;
    final long rx1 = ((long) IntBit.reverse(x1)) & IntBit.FULL_BITS_MASK;
    return ((rx0 << HALF_BITS) | rx1);
  }

  /**
   * 根据掩码合并两个值的位。
   *
   * <p>对于结果值中的每个位位置 i，如果掩码的第 i 位是 0，
   * 则结果的第 i 位与 x 的第 i 位相同；如果掩码的第 i 位是 1，
   * 则结果的第 i 位与 y 的第 i 位相同。
   *
   * <p>例如，设 x = 10101010，y = 11001100，mask = 01110010，
   * merge(x, y, mask) 将得到结果 11001000（所有数字都是二进制形式）。
   *
   * @param x
   *     第一个操作数
   * @param y
   *     第二个操作数。
   * @param mask
   *     用于从 x 和 y 中选择位的掩码。如果掩码中的第 i 位是 0，
   *     从 x 中选择第 i 位；否则，从 y 中选择第 i 位。
   * @return 根据掩码合并 x 和 y 的结果。
   */
  public static long merge(final long x, final long y, final long mask) {
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    return (x ^ ((x ^ y) & mask));
  }

  /**
   * 将 x 的所有位设置为 1。实际上，该函数将 x 设置为 ~T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static long set(final long x) {
    return FULL_BITS_MASK;
  }

  /**
   * 将 x 的第 i 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要设置的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数行为未定义。
   */
  public static long set(final long x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (x | BIT_MASK[i]);
  }

  /**
   * 将 x 的最高有效位设置为 1。
   *
   * <p>注意，有符号整型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static long setMsb(final long x) {
    return (x | MSB_MASK);
  }

  /**
   * 将 x 的最低 n 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最低位数。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static long setLow(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (x | RANGE_MASK[n]);
  }

  /**
   * 将 x 的最高 n 位设置为 1。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最高位数。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static long setHigh(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (x | (~RANGE_MASK[BITS - n]));
  }

  /**
   * 将 x 的所有位设置为 0。实际上，该函数将 x 设置为 T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static long unset(final long x) {
    return 0;
  }

  /**
   * 将 x 的第 i 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要重置的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数行为未定义。
   */
  public static long unset(final long x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (x & (~BIT_MASK[i]));
  }

  /**
   * 将 x 的最高有效位重置为 0。
   *
   * <p>注意，有符号整型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static long unsetMsb(final long x) {
    return (x & (~MSB_MASK));
  }

  /**
   * 将 x 的最低 n 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最低位数。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static long unsetLow(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (x & (~RANGE_MASK[n]));
  }

  /**
   * 将 x 的最高 n 位设置为 0。
   *
   * @param x
   *     要修改的值。
   * @param n
   *     要设置的最高位数。必须在 [0, BITS] 范围内。
   * @return 操作的结果。
   */
  public static long unsetHigh(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (x & RANGE_MASK[BITS - n]);
  }

  /**
   * 反转 x 的所有位，即将 1 变为 0，将 0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static long invert(final long x) {
    return (~x);
  }

  /**
   * 反转 x 的指定位，即将 1 变为 0，将 0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要翻转的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数行为未定义。
   */
  public static long invert(final long x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (x ^ BIT_MASK[i]);
  }

  /**
   * 反转 x 的最高有效位。
   *
   * <p>注意，有符号整型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static long invertMsb(final long x) {
    return (x ^ MSB_MASK);
  }

  /**
   * 测试 x 的第 i 位。
   *
   * @param x
   *     待测试的值。
   * @param i
   *     要测试的 x 的位。必须在 [0, BITS) 范围内。
   * @return 如果 x 的第 i 位是 1，返回 true，否则返回 false。
   *     如果 i &lt; 0 或 i &ge; BITS，函数行为未定义。
   */
  public static boolean test(final long x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return ((x & BIT_MASK[i]) != 0);
  }

  /**
   * 测试 x 的最高有效位。
   *
   * <p>注意，有符号整型变量的最高有效位是其符号位。
   *
   * @param x
   *     操作数。
   * @return 如果 x 的最高有效位是 1，返回 true，否则返回 false。
   */
  public static boolean testMsb(final long x) {
    return ((x & MSB_MASK) != 0);
  }

  /**
   * 计算 x 值中设置的位数。
   *
   * @param x
   *     要计算位数的值。
   * @return x 中设置的位数。
   */
  public static long count(final long x) {
    final int x0 = (int) x;
    final int x1 = (int) (x >>> HALF_BITS);
    return IntBit.count(x0) + IntBit.count(x1);
  }

  /**
   * 测试 x 的所有位是否都设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 的所有位都设置为 1，返回 true，否则返回 false。
   */
  public static boolean hasAll(final long x) {
    return (x == FULL_BITS_MASK);
  }

  /**
   * 测试 x 是否有任何位设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 至少有一位设置为 1，返回 true，否则返回 false。
   */
  public static boolean hasAny(final long x) {
    return (x != 0);
  }

  /**
   * 测试 x 是否没有位设置为 1，即 x 的所有位都设置为 0。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 没有位设置为 1，返回 true，否则返回 false。
   */
  public static boolean hasNone(final long x) {
    return (x == 0);
  }

  /**
   * 查找 x 中最低的设置位（值为 1）。
   *
   * <p>例如，x 是二进制值为 00101010 的 unsigned char，
   * findFirstSet(x) 将返回 1。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最低位置。如果 x 中所有位都是 0，返回 BITS。
   */
  public static int findFirstSet(final long x) {
    final int x0 = (int) x;
    if (x0 == 0) { // find in the higher 32 bits.
      final int x1 = (int) (x >>> HALF_BITS);
      return IntBit.findFirstSet(x1) + HALF_BITS;
    } else { // find in the lower 32 bits.
      return IntBit.findFirstSet(x0);
    }
  }

  /**
   * 从第 n 位开始查找 x 中最低的设置位（值为 1），即在位范围 [n, BITS) 中查找。
   *
   * <p>例如，x 是二进制值为 00101010 的 unsigned char，
   * findFirstSet(x, 2) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     从该位开始搜索，即在 x 的位范围 [n, BITS) 中搜索。
   * @return x 中设置为 1 的位的最低位置。如果未找到此位置，返回 -1。
   *     如果 n &ge; BITS，函数行为未定义。
   */
  public static int findFirstSet(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));

    // clear the lowest n bits of x
    final long x0 = (x & (~RANGE_MASK[n]));
    return findFirstSet(x0);
  }

  /**
   * 查找 x 中最低的未设置位（值为 0）。
   *
   * <p>例如，x 是二进制值为 00101011 的 unsigned char，
   * findFirstUnset(x) 将返回 2。
   *
   * <p>该函数等价于 invert(x); return findFirstSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的位的最低位置。如果 x 中所有位都是 1，返回 -1。
   */
  public static int findFirstUnset(final long x) {
    // inverse x.
    return findFirstSet(~x);
  }

  /**
   * 从第 n 位开始查找 x 中最低的未设置位（值为 0），即在位范围 [n, BITS) 中查找。
   *
   * <p>例如，x 是二进制值为 00111011 的 unsigned char，
   * findFirstUnset(x, 3) 将返回 6。
   *
   * <p>该函数等价于 invert(x); return findFirstSet( x, n );
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     从该位开始搜索，即在 x 的位范围 [n, BITS) 中搜索。
   * @return x 中设置为 0 的位的最低位置。如果未找到此位置，返回 -1。
   *     如果 n &ge; BITS，函数行为未定义。
   */
  public static int findFirstUnset(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final long x0 = (~(x | RANGE_MASK[n]));
    return findFirstSet(x0);
  }

  /**
   * 查找 x 中最高的设置位（值为 1）。
   *
   * <p>例如，x 是二进制值为 00101101 的 unsigned char，
   * findLastSet(x) 将返回 5。
   *
   * <p>实际上，结果的值等于 log_2(x)。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的位的最高位置。如果 x 中所有位都是 0，返回 -1。
   */
  public static int findLastSet(final long x) {
    if (x == 0) {
      return BITS;
    } else {
      final int x0 = (int) (x >>> HALF_BITS);
      if (x0 == 0) { // find in the lower 32 bits.
        return IntBit.findLastSet((int) (x & HALF_BITS_MASK));
      } else { // find in the higher 32 bits.
        return IntBit.findLastSet(x0) + HALF_BITS;
      }
    }
  }

  /**
   * 从第 0 位开始到第 n 位（不包括）查找 x 中最高的设置位（值为 1），
   * 即在位范围 [0, n) 中查找。
   *
   * <p>例如，x 是二进制值为 00101101 的 unsigned char，
   * findLastSet(x, 5) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索结束于该位，即在 x 的位范围 [0, n) 中搜索。
   * @return x 的位范围 [0, n) 中设置为 1 的最高位置。如果未找到此位置，返回 BITS。
   *     如果 n == 0，返回 -1。如果 n == BITS，返回 findLastSet(x)。
   *     如果 n &gt; BITS，行为未定义。
   */
  public static int findLastSet(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final long x0 = (x & RANGE_MASK[n] & FULL_BITS_MASK);
    return findLastSet(x0);
  }

  /**
   * 查找 x 中最高的未设置位（值为 0）。
   *
   * <p>例如，x 是二进制值为 11101011 的 unsigned char，
   * findLastUnset(x) 将返回 4。
   *
   * <p>该函数等价于 invert(x); return findLastSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的位的最高位置。如果 x 中所有位都是 1，返回 -1。
   */
  public static int findLastUnset(final long x) {
    final long x0 = ((~x) & FULL_BITS_MASK);
    return findLastSet(x0);
  }

  /**
   * 从第 0 位开始到第 n 位查找 x 中最高的未设置位（值为 0），
   * 即在位范围 [0, n) 中查找。
   *
   * <p>例如，x 是二进制值为 10111011 的 unsigned char，
   * findLastUnset(x, 6) 将返回 2。
   *
   * <p>该函数等价于 invert(x); return findLastSet(x, n);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     搜索结束于该位，即在 x 的位范围 [0, n) 中搜索。
   * @return x 的位范围 [0, n) 中设置为 0 的最低位置。如果未找到此位置，返回 -1。
   *     如果 n == 0，返回 -1。如果 n &gt; BITS，函数行为未定义。
   */
  public static int findLastUnset(final long x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final long x0 = ((~x) & RANGE_MASK[n] & FULL_BITS_MASK);
    return findLastSet(x0);
  }

  /**
   * 测试 x 是否是 y 的子集，即对于 x 中设置的每一位，y 中相应的位也被设置。
   *
   * <p>注意，如果 x == y，x 是 y 的子集，但它不是 y 的"真"子集。
   *
   * @param x
   *     要测试的值。
   * @param y
   *     要测试的另一个值。
   * @return 如果 x 是 y 的子集，返回 true；否则返回 false。
   */
  public static boolean isSubsetOf(final long x, final long y) {
    return ((x | y) == y);
  }

  /**
   * 测试 x 是否是 y 的真子集，即对于 x 中设置的每一位，y 中相应的位也被设置，
   * 并且 y 中至少有一位被设置但 x 中相应的位未被设置（或简单地说，x 不等于 y）。
   *
   * @param x
   *     要测试的值。
   * @param y
   *     要测试的另一个值。
   * @return 如果 x 是 y 的真子集，返回 true；否则返回 false。
   */
  public static boolean isProperSubsetOf(final long x, final long y) {
    return ((x | y) == y) && (x != y);
  }

  /**
   * 按字典序比较位。
   *
   * <p>返回一个小于、等于或大于 0 的整数，如果从位置 0 到位置 BITS - 1 的 x 的位
   * 按字典序比较小于、等于或大于从位置 0 到位置 BITS - 1 的 y 的位。
   *
   * <p>例如，
   * <ul>
   * <li><code>3 = 0b00000011</code>, <code>5 = 0b00000101</code>，因此
   * <code>compare(3, 5) &lt; 0</code>。</li>
   * <li><code>3 = 0b00000011</code>, <code>-1 = 0b11111111</code>，因此
   * <code>compare(3, -1) &lt; 0</code>。</li>
   * </ul>
   *
   * @param x
   *     比较的第一个操作数。
   * @param y
   *     比较的第二个操作数。
   * @return 如上所述的字典序比较结果。
   */
  public static int compare(final long x, final long y) {
    final int sx = Long.signum(x);
    final int sy = Long.signum(y);
    if (sx == sy) {
      return Long.signum(x - y);
    } else if (x == 0) {
      return -1;
    } else if (y == 0) {
      return +1;
    } else {
      return Long.signum(sy - sx);
    }
  }

  /**
   * 确定 long 的无符号表示是否是 2 的幂。即 long 的位是否只有一个 1，其余位都是 0。
   *
   * <p>注意，0 被认为是 2 的幂，即 isPowerOfTwo(0) 将返回 true。
   *
   * @param x
   *     要测试的操作数。注意 x 被视为无符号 long。
   * @return 如果 x 是 2 的幂，返回 true。
   */
  public static boolean isPowerOfTwo(final long x) {
    return (x & (x - 1)) == 0;
  }

  /**
   * 返回 log_2(x) 的下界。换句话说，返回满足 2^n &le; x &lt; 2^{n+1} 的最大 n。
   *
   * @param x
   *     操作数。注意 x 被视为无符号 long。
   * @return log_2(x) 的下界。如果 x 是 0，返回 -1。
   */
  public static int getLog2Floor(final long x) {
    assert (x != 0);
    return findLastSet(x);
  }

  /**
   * 返回 log_2(x) 的上界。换句话说，返回满足 2^{n-1} &lt; x &le; 2^{n} 的最小 n。
   *
   * @param x
   *     操作数。注意 x 被视为无符号 long。
   * @return log_2(x) 的上界。如果 x 是 0，返回 -1。
   */
  public static int getLog2Ceiling(final long x) {
    assert (x != 0);
    // note that (x & (x - 1)) == 0 means x is a power of 2
    return findLastSet(x) + ((x & (x - 1)) != 0 ? 1 : 0);
  }

  /**
   * 返回指定无符号 long 的二进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号 long。
   * @return x 的二进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits2(final long x) {
    return (x == 0 ? 1 : findLastSet(x) + 1);
  }

  /**
   * 返回指定无符号 long 的八进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号 long。
   * @return x 的八进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits8(final long x) {
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSet(x) + 1);
    return (digit2 + 2) / 3;
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 返回指定无符号 long 的十进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号 long。
   * @return x 的十进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits10(final long x) {
    // The fraction 643/2136 approximates log10(2) to 7 significant digits.
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSet(x) + 1);
    return (digit2 * 643) / 2136 + 1;
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 返回指定无符号 long 的十六进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号 long。
   * @return x 的十六进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits16(final long x) {
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSet(x) + 1);
    return (digit2 + 3) / 4;
    // resume checkstyle: MagicNumberCheck
  }
}