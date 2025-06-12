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
 * 提供对 {@code int} 类型进行位操作功能的工具类。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class IntBit {

  /**
   * {@code int} 类型的位数。
   */
  public static final int BITS = 32;

  /**
   * {@code int} 类型一半的位数。
   */
  public static final int HALF_BITS = 16;

  /**
   * 低半位的掩码。
   */
  public static final int HALF_BITS_MASK = 0x0000FFFF;

  /**
   * 全部位的掩码。
   */
  public static final long FULL_BITS_MASK = 0xFFFFFFFFL;

  /**
   * 最高位（符号位）的掩码。
   */
  public static final int MSB_MASK = 0x80000000;

  /**
   * 用于获取指定位数范围的掩码数组。索引 i 对应的值是一个掩码，其低 i 位为 1，其余位为 0。
   */
  public static final int[] RANGE_MASK = {
      0x00000000, 0x00000001, 0x00000003, 0x00000007, 0x0000000F,
      0x0000001F, 0x0000003F, 0x0000007F, 0x000000FF, 0x000001FF,
      0x000003FF, 0x000007FF, 0x00000FFF, 0x00001FFF, 0x00003FFF,
      0x00007FFF, 0x0000FFFF, 0x0001FFFF, 0x0003FFFF, 0x0007FFFF,
      0x000FFFFF, 0x001FFFFF, 0x003FFFFF, 0x007FFFFF, 0x00FFFFFF,
      0x01FFFFFF, 0x03FFFFFF, 0x07FFFFFF, 0x0FFFFFFF, 0x1FFFFFFF,
      0x3FFFFFFF, 0x7FFFFFFF, 0xFFFFFFFF
  };

  /**
   * 用于获取单个位的掩码数组。索引 i 对应的值是一个掩码，只有第 i 位为 1，其余位为 0。
   */
  public static final int[] BIT_MASK = {
      0x00000001, 0x00000002, 0x00000004, 0x00000008, 0x00000010,
      0x00000020, 0x00000040, 0x00000080, 0x00000100, 0x00000200,
      0x00000400, 0x00000800, 0x00001000, 0x00002000, 0x00004000,
      0x00008000, 0x00010000, 0x00020000, 0x00040000, 0x00080000,
      0x00100000, 0x00200000, 0x00400000, 0x00800000, 0x01000000,
      0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000,
      0x40000000, 0x80000000
  };

  /**
   * 将 x 的值当作无符号 int 返回。
   *
   * <p>注意：此函数有时候很有用，因为 Java 中没有无符号类型。
   *
   * @param x
   *     一个 int 值，应被视为无符号 int。
   * @return x 作为无符号 int 的值。更准确地说，如果 x 非负，返回 x 的值；否则，
   *     返回 (256 + x) 的值。
   */
  public static long asUnsigned(final int x) {
    return ((long) x & FULL_BITS_MASK);
  }

  /**
   * 返回 x 的最低 n 位。
   *
   * @param x
   *     值。
   * @param n
   *     要返回的最低位数。必须在 [0, BITS] 范围内。
   * @return 一个 {@code int} 类型的值，其最低 n 位与 x 的最低 n 位相同，其他位为 0。
   *     如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。如果 n &lt; 0 或
   *     n &gt; BITS，行为未定义。
   */
  public static int low(final int x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (x & RANGE_MASK[n]);
  }

  /**
   * 返回 x 的最高 n 位。
   *
   * @param x
   *     值。
   * @param n
   *     要返回的最低位数。必须在 [0, BITS] 范围内。
   * @return 一个 {@code int} 类型的值，其最高 n 位与 x 的最高 n 位相同，其他位为 0。
   *     如果 n == 0，函数返回 0。如果 n == BITS，函数返回 x。如果 n &lt; 0 或
   *     n &gt; BITS，行为未定义。
   */
  public static int high(final int x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    return (x >>> (BITS - n));
  }

  /**
   * 返回 x 的低半位。
   *
   * @param x
   *     值。
   * @return 一个 {@code int} 类型的值，其低半位与 x 的低半位相同，其他位为 0。
   *     实际上，此函数返回 low(x, HALF_BITS)。
   */
  public static int lowHalf(final int x) {
    return (x & HALF_BITS_MASK);
  }

  /**
   * 返回 x 的高半位。
   *
   * @param x
   *     值。
   * @return 一个 {@code int} 类型的值，其高半位与 x 的高半位相同，其他位为 0。
   *     实际上，此函数返回 high(x, HALF_BITS)。
   */
  public static int highHalf(final int x) {
    return (x >>> HALF_BITS);
  }

  /**
   * 交换 x 的最低 n 位和最高 (BITS - n) 位。即交换位范围 [0, n) 和 [n, BITS)。
   *
   * @param x
   *     要交换的值。
   * @param n
   *     将与其余高位交换的最低位数。必须在 [0, BITS] 范围内。
   * @return 一个新值，其最高 n 位是 x 的最低 n 位，其最低 (BITS - n) 位是 x 的
   *     最高 (BITS - n) 位。如果 n == 0 或 n == BITS，函数返回 x。如果 n &lt; 0
   *     或 n &gt; BITS，函数的行为未定义。
   */
  public static int rotate(final int x, final int n) {
    assert ((0 <= n) && (n <= BITS));
    final int m = BITS - n;
    final int xl = (x & RANGE_MASK[n]);
    final int xh = (x >>> m);
    return ((xl << m) | xh);
  }

  /**
   * 交换 x 的最低半位和最高半位。即交换位范围 [0, HALF_BITS) 和 [HALF_BITS, BITS)。
   *
   * @param x
   *     要交换的值。
   * @return 一个新值，其最高半位是 x 的最低半位，其最低半位是 x 的最高半位。
   */
  public static int rotateHalf(final int x) {
    final int xl = (x & HALF_BITS_MASK);
    final int xh = (x >>> HALF_BITS);
    return ((xl << HALF_BITS) | xh);
  }

  /**
   * 交换 x 的第 i 位和第 j 位，返回新值。
   *
   * <p>例如，假设 IntType 是 unsigned char，x 的二进制是 00101111，
   * i = 1，j = 4，swap(x, i, j) 将交换 x 的第 1 位和第 4 位，得到 00111101。
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
  public static int swap(final int x, final int i, final int j) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS));

    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final int y = (((x >>> i) ^ (x >>> j)) & 1);
    return (x ^ ((y << i) | (y << j)));
  }

  /**
   * 交换 x 的位范围 [i, i+n) 和位范围 [j, j+n)，返回新值。
   *
   * <p>例如，假设 int 是 unsigned char，x 的二进制是 00101111，i = 1，j = 5，n = 3，
   * swapRange(x, i, j, n) 将交换 x 的第 1,2,3 位和第 5,6,7 位，或者换句话说，
   * 交换以下表示中的第一个括号和第二个括号：
   *
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
   *     第一个要交换位的起始位置。必须在 [0, BITS) 范围内。
   * @param j
   *     第二个要交换位的起始位置。必须在 [0, BITS) 范围内。
   * @param n
   *     要交换的连续位数。
   * @return 一个新值，其位范围 [i, i+n) 是 x 的位范围 [j, j+n)，位范围 [j, j+n) 是
   *     x 的位范围 [i, i+n)，其余位与 x 相同。如果 n == 0，函数返回 x。如果
   *     i + n &gt; BITS 或 j + n &gt; BITS，即范围 [i, i+n) 或 [j, j+n) 超出
   *     范围 [0, BITS)，函数的行为未定义。如果 i == j，函数返回 x。如果 i &lt; j
   *     且 i + n &gt; j，或者 j &lt; i 且 j + n &gt; i，即两个范围 [i, i+n) 和
   *     [j, j+n) 重叠，函数的行为未定义。
   */
  public static int swapRange(final int x, final int i, final int j,
      final int n) {
    assert ((0 <= i) && (i < BITS) && (0 <= j) && (j < BITS) && (n > 0)
        && (i + n <= BITS) && (j + n <= BITS) && ((i + n <= j) || (j + n
        <= i)));
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    final int y = (((x >>> i) ^ (x >>> j)) & RANGE_MASK[n]);
    return (x ^ ((y << i) | (y << j)));
  }

  /**
   * 反转 x 中的位，即结果中位置 i 的位是 x 中位置 (BITS - 1 - i) 的位。
   *
   * <p>例如，int 是二进制 unsigned char 01111011（注意保留前导 0）。
   * reverse(x) 得到 11011110。
   *
   * @param x
   *     要反转的值。
   * @return 通过反转 x 的位得到的新值。
   */
  public static int reverse(final int x) {
    int d = x;
    final int x0 = ByteBit.REVERSE[d & ByteBit.FULL_BITS_MASK];
    d >>>= ByteBit.BITS;
    final int x1 = ByteBit.REVERSE[d & ByteBit.FULL_BITS_MASK];
    d >>>= ByteBit.BITS;
    final int x2 = ByteBit.REVERSE[d & ByteBit.FULL_BITS_MASK];
    d >>>= ByteBit.BITS;
    final int x3 = ByteBit.REVERSE[d & ByteBit.FULL_BITS_MASK];

    int result = x0;
    result <<= ByteBit.BITS;
    result |= x1;
    result <<= ByteBit.BITS;
    result |= x2;
    result <<= ByteBit.BITS;
    result |= x3;
    return result;
  }

  /**
   * 根据掩码合并两个值的位。
   *
   * <p>对于结果值中的每个位位置 i，如果掩码的第 i 位是 0，结果的第 i 位与 x 的第 i 位相同；
   * 如果掩码的第 i 位是 1，结果的第 i 位与 y 的第 i 位相同。
   *
   * <p>例如，设 x = 10101010，y = 11001100，mask = 01110010，merge(x, y, mask)
   * 将得到结果 11001000（所有数字都是二进制形式）。
   *
   * @param x
   *     第一个操作数
   * @param y
   *     第二个操作数。
   * @param mask
   *     用于从 x 和 y 选择位的掩码。如果掩码中的第 i 位是 0，从 x 选择第 i 位；
   *     否则，从 y 选择第 i 位。
   * @return 根据掩码合并 x 和 y 的结果。
   */
  public static int merge(final int x, final int y, final int mask) {
    // Use a trick from http://graphics.stanford.edu/~seander/bithacks.html
    return (x ^ ((x ^ y) & mask));
  }

  /**
   * 将 x 的所有位设置为 1。实际上，此函数将 x 设置为 ~T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static int set(final int x) {
    return (int) FULL_BITS_MASK;
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
  public static int set(final int x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (x | BIT_MASK[i]);
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
  public static int setMsb(final int x) {
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
  public static int setLow(final int x, final int n) {
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
  public static int setHigh(final int x, final int n) {
    assert ((0 <= n) && (n < BITS));
    return (x | (~RANGE_MASK[BITS - n]));
  }

  /**
   * 将 x 的所有位设置为 0。实际上，此函数将 x 设置为 T(0)。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static int unset(final int x) {
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
  public static int unset(final int x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (x & (~BIT_MASK[i]));
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
  public static int unsetMsb(final int x) {
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
  public static int unsetLow(final int x, final int n) {
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
  public static int unsetHigh(final int x, final int n) {
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
  public static int invert(final int x) {
    return (~x);
  }

  /**
   * 反转 x 的指定位，即将 1 变为 0，将 0 变为 1。
   *
   * @param x
   *     要修改的值。
   * @param i
   *     要翻转的 x 的位。必须在 [0, BITS) 范围内。
   * @return 操作的结果。如果 i &lt; 0 或 i &ge; BITS，函数的行为未定义。
   */
  public static int invert(final int x, final int i) {
    assert ((0 <= i) && (i < BITS));
    return (x ^ BIT_MASK[i]);
  }

  /**
   * 反转 x 的最高有效位。
   *
   * <p>注意：有符号整数类型变量的最高有效位是其符号位。
   *
   * @param x
   *     要修改的值。
   * @return 操作的结果。
   */
  public static int invertMsb(final int x) {
    return (x ^ MSB_MASK);
  }

  /**
   * 测试 x 的第 i 位。
   *
   * @param x
   *     值。
   * @param i
   *     要测试的 x 的位。必须在 [0, BITS) 范围内。
   * @return 如果 x 的第 i 位是 1，返回 true，否则返回 false。如果 i &lt; 0 或 i &ge;
   *     BITS，函数的行为未定义。
   */
  public static boolean test(final int x, final int i) {
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
   * @return 如果 x 的最高有效位是 1，返回 true，否则返回 false。
   */
  public static boolean testMsb(final int x) {
    return ((x & MSB_MASK) != 0);
  }

  /**
   * 计算 x 值中设置的位数。
   *
   * @param x
   *     要计算位数的值。
   * @return x 中设置的位数。
   */
  public static int count(final int x) {
    int d = x;
    final int x0 = (d & ByteBit.FULL_BITS_MASK);
    d >>>= ByteBit.BITS;
    final int x1 = (d & ByteBit.FULL_BITS_MASK);
    d >>>= ByteBit.BITS;
    final int x2 = (d & ByteBit.FULL_BITS_MASK);
    d >>>= ByteBit.BITS;
    final int x3 = (d & ByteBit.FULL_BITS_MASK);
    return ByteBit.COUNT[x0] + ByteBit.COUNT[x1] + ByteBit.COUNT[x2]
        + ByteBit.COUNT[x3];
  }

  /**
   * 测试 x 的所有位是否都设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 的所有位都设置为 1，返回 true，否则返回 false。
   */
  public static boolean hasAll(final int x) {
    return (x == (int) FULL_BITS_MASK);
  }

  /**
   * 测试 x 是否有任何位设置为 1。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 至少有一个位设置为 1，返回 true，否则返回 false。
   */
  public static boolean hasAny(final int x) {
    return (x != 0);
  }

  /**
   * 测试 x 的所有位是否都没有设置为 1，即 x 的所有位都设置为 0。
   *
   * @param x
   *     要测试的值。
   * @return 如果 x 的所有位都没有设置为 1，返回 true，否则返回 false。
   */
  public static boolean hasNone(final int x) {
    return (x == 0);
  }

  /**
   * 查找 x 中最低的设置位（值为 1）。
   *
   * <p>例如，x 是一个二进制值为 00101010 的无符号字符，
   * findFirstSet(x) 将返回 1。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的最低位的位置。如果 x 中的所有位都是 0，返回 BITS。
   */
  public static int findFirstSet(final int x) {
    final int x0 = (x & HALF_BITS_MASK);
    if (x0 == 0) { // find in the higher 16 bits.
      return ShortBit.findFirstSetImpl(x >>> HALF_BITS) + HALF_BITS;
    } else { // find in the lower 16 bits.
      return ShortBit.findFirstSetImpl(x0);
    }
  }

  /**
   * 从第 n 位开始查找 x 中最低的设置位（值为 1），即在位范围 [n, BITS) 中查找。
   *
   * <p>例如，x 是一个二进制值为 00101010 的无符号字符，
   * findFirstSet(x, 2) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     从此位开始搜索，即在 x 的位范围 [n, BITS) 中搜索。
   * @return x 中设置为 1 的最低位的位置。如果找不到这样的位置，返回 -1。如果 n &ge; BITS，
   *     函数的行为未定义。
   */
  public static int findFirstSet(final int x, final int n) {
    assert ((0 <= n) && (n < BITS));

    // clear the lowest n bits of x
    final int x0 = (x & (~RANGE_MASK[n]));
    return findFirstSet(x0);
  }

  /**
   * 查找 x 中最低的未设置位（值为 0）。
   *
   * <p>例如，x 是一个二进制值为 00101011 的无符号字符，
   * findFirstUnset(x) 将返回 2。
   *
   * <p>此函数等效于 invert(x); return findFirstSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的最低位的位置。如果 x 中的所有位都是 1，返回 -1。
   */
  public static int findFirstUnset(final int x) {
    // inverse x.
    return findFirstSet(~x);
  }

  /**
   * 从第 n 位开始查找 x 中最低的未设置位（值为 0），即在位范围 [n, BITS) 中查找。
   *
   * <p>例如，x 是一个二进制值为 00111011 的无符号字符，
   * findFirstUnset(x, 3) 将返回 6。
   *
   * <p>此函数等效于 invert(x); return findFirstSet( x, n );
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     从此位开始搜索，即在 x 的位范围 [n, BITS) 中搜索。
   * @return x 中设置为 0 的最低位的位置。如果找不到这样的位置，返回 -1。如果 n &ge; BITS，
   *     函数的行为未定义。
   */
  public static int findFirstUnset(final int x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final int x0 = (~(x | RANGE_MASK[n]));
    return findFirstSet(x0);
  }

  /**
   * 查找 x 中最高的设置位（值为 1）。
   *
   * <p>例如，x 是一个二进制值为 00101101 的无符号字符，
   * findLastSet(x) 将返回 5。
   *
   * <p>实际上，结果的值等于 log_2(x)。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 1 的最高位的位置。如果 x 中的所有位都是 0，返回 -1。
   */
  public static int findLastSet(final int x) {
    if (x == 0) {
      return BITS;
    } else {
      final int x0 = (x >>> HALF_BITS);
      if (x0 == 0) { // find in the lower 16 bits.
        return ShortBit.findLastSetImpl(x & HALF_BITS_MASK);
      } else { // find in the higher 16 bits.
        return ShortBit.findLastSetImpl(x0) + HALF_BITS;
      }
    }
  }

  /**
   * 从第 0 位开始到第 n 位结束（不包括）查找 x 中最高的设置位（值为 1），即在位范围 [0, n) 中查找。
   *
   * <p>例如，x 是一个二进制值为 00101101 的无符号字符，
   * findLastSet(x, 5) 将返回 3。
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     在此位结束搜索，即在 x 的位范围 [0, n) 中搜索。
   * @return x 的位范围 [0, n) 中设置为 1 的最高位置。如果找不到这样的位置，返回 BITS。
   *     如果 n == 0，返回 -1。如果 n == BITS，返回 findLastSet(x)。如果 n &gt; BITS，
   *     行为未定义。
   */
  public static int findLastSet(final int x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final int x0 = (x & RANGE_MASK[n] & (int) FULL_BITS_MASK);
    return findLastSet(x0);
  }

  /**
   * 查找 x 中最高的未设置位（值为 0）。
   *
   * <p>例如，x 是一个二进制值为 11101011 的无符号字符，
   * findLastUnset(x) 将返回 4。
   *
   * <p>此函数等效于 invert(x); return findLastSet(x);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @return x 中设置为 0 的最高位的位置。如果 x 中的所有位都是 1，返回 -1。
   */
  public static int findLastUnset(final int x) {
    final int x0 = ((~x) & (int) FULL_BITS_MASK);
    return findLastSet(x0);
  }

  /**
   * 从第 0 位开始到第 n 位结束查找 x 中最高的未设置位（值为 0），即在位范围 [0, n) 中查找。
   *
   * <p>例如，x 是一个二进制值为 10111011 的无符号字符，
   * findLastUnset(x, 6) 将返回 2。
   *
   * <p>此函数等效于 invert(x); return findLastSet(x, n);
   *
   * @param x
   *     要查找第一个设置位的值。
   * @param n
   *     在此位结束搜索，即在 x 的位范围 [0, n) 中搜索。
   * @return x 的位范围 [0, n) 中设置为 0 的最低位置。如果找不到这样的位置，返回 -1。
   *     如果 n == 0，返回 -1。如果 n &gt; BITS，函数的行为未定义。
   */
  public static int findLastUnset(final int x, final int n) {
    assert ((0 <= n) && (n < BITS));

    final int x0 = ((~x) & RANGE_MASK[n] & (int) FULL_BITS_MASK);
    return findLastSet(x0);
  }

  /**
   * 测试 x 是否是 y 的子集，即对于 x 中设置的每个位，y 中对应的位是否也被设置。
   *
   * <p>注意：如果 x == y，x 是 y 的子集，但它不是 y 的"真"子集。
   *
   * @param x
   *     要测试的值。
   * @param y
   *     要测试的另一个值。
   * @return 如果 x 是 y 的子集，返回 true；否则返回 false。
   */
  public static boolean isSubsetOf(final int x, final int y) {
    return ((x | y) == y);
  }

  /**
   * 测试 x 是否是 y 的真子集，即对于 x 中设置的每个位，y 中对应的位也被设置，并且 y
   * 中至少有一个位被设置但 x 中对应的位没有被设置（或者简单地说，x 不等于 y）。
   *
   * @param x
   *     要测试的值。
   * @param y
   *     要测试的另一个值。
   * @return 如果 x 是 y 的真子集，返回 true；否则返回 false。
   */
  public static boolean isProperSubsetOf(final int x, final int y) {
    return ((x | y) == y) && (x != y);
  }

  /**
   * 按字典序比较位。
   *
   * <p>如果 x 从位置 0 到位置 BITS - 1 的位按字典序小于、等于或大于 y 从位置 0 到位置 BITS - 1 的位，
   * 则返回小于、等于或大于 0 的整数。
   *
   * <p>例如，
   * <ul>
   * <li><code>3 = 0b00000011</code>, <code>5 = 0b00000101</code>, 因此
   * <code>compare(3, 5) &lt; 0</code>。</li>
   * <li><code>3 = 0b00000011</code>, <code>-1 = 0b11111111</code>, 因此
   * <code>compare(3, -1) &lt; 0</code>。</li>
   * </ul>
   *
   * @param x
   *     比较的第一个操作数。
   * @param y
   *     比较的第二个操作数。
   * @return 字典序比较的结果，如上所述。
   */
  public static int compare(final int x, final int y) {
    final long lx = ((long) x) & FULL_BITS_MASK;
    final long ly = ((long) y) & FULL_BITS_MASK;
    return Long.signum(lx - ly);
  }

  /**
   * 判断整数的无符号表示是否是 2 的幂。即，整数的位是否只有一个 1，其余所有位都是 0。
   *
   * <p>注意：0 被认为是 2 的幂，即 isPowerOfTwo(0) 将返回 true。
   *
   * @param x
   *     要测试的操作数。注意 x 被视为无符号整数。
   * @return 如果 x 是 2 的幂，返回 true。
   */
  public static boolean isPowerOfTwo(final int x) {
    return (x & (x - 1)) == 0;
  }

  /**
   * 返回 log_2(x) 的向下取整。换句话说，返回最大的 n，使得 2^n &le; x &lt; 2^{n+1}。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return log_2(x) 的向下取整。如果 x 是 0，返回 -1。
   */
  public static int getLog2Floor(final int x) {
    assert (x != 0);
    return findLastSet(x);
  }

  /**
   * 返回 log_2(x) 的向上取整。换句话说，返回最小的 n，使得 2^{n-1} &lt; x &le; 2^{n}。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return log_2(x) 的向上取整。如果 x 是 0，返回 -1。
   */
  public static int getLog2Ceiling(final int x) {
    assert (x != 0);
    // note that (x & (x - 1)) == 0 means x is a power of 2
    return findLastSet(x) + ((x & (x - 1)) != 0 ? 1 : 0);
  }

  /**
   * 返回指定无符号整数的二进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return x 的二进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits2(final int x) {
    return (x == 0 ? 1 : findLastSet(x) + 1);
  }

  /**
   * 返回指定无符号整数的八进制表示中的位数。
   *
   * @param x
   *     操作数。注意 x 被视为无符号整数。
   * @return x 的八进制表示中的位数。如果 x 是 0，函数返回 1。
   */
  public static int getDigits8(final int x) {
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSet(x) + 1);
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
  public static int getDigits10(final int x) {
    // The fraction 643/2136 approximates log10(2) to 7 significant digits.
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSet(x) + 1);
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
  public static int getDigits16(final int x) {
    // stop checkstyle: MagicNumberCheck
    final int digit2 = (x == 0 ? 1 : findLastSet(x) + 1);
    return (digit2 + 3) / 4;
    // resume checkstyle: MagicNumberCheck
  }
}