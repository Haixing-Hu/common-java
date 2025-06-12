////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.concurrent.Lazy;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.text.Ascii;
import ltd.qubit.commons.util.range.CloseRange;
import ltd.qubit.commons.util.range.UnmodifiableCloseRange;

import static ltd.qubit.commons.lang.ArrayUtils.createArrayOfSameElementType;

/**
 * {@link java.util.Random} 的扩展版本。
 *
 * <p>{@link RandomEx} 的实例是线程安全的。</p>
 *
 * @author 胡海星
 */
@ThreadSafe
public class RandomEx extends Random {

  private static final long serialVersionUID = 4648505854602540292L;

  /**
   * 精度常量，用于浮点数比较。
   */
  public static final double EPSILON = 0.00000001;

  /**
   * 默认字符串长度范围。
   */
  public static final CloseRange<Integer> DEFAULT_STRING_LENGTH_RANGE =
      new UnmodifiableCloseRange<>(5, 100);

  /**
   * 延迟初始化的全局 {@link RandomEx} 对象。
   */
  public static final Lazy<RandomEx> LAZY = Lazy.of(RandomEx::new);

  /**
   * 创建一个新的 {@link RandomEx} 实例。
   */
  public RandomEx() {
  }

  /**
   * 使用指定种子创建一个新的 {@link RandomEx} 实例。
   *
   * @param seed
   *     随机数生成器的种子。
   */
  public RandomEx(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的 byte 值。
   *
   * @return 一个随机的 byte 值。
   */
  public final byte nextByte() {
    final byte[] bytes = new byte[1];
    nextBytes(bytes);
    return bytes[0];
  }

  /**
   * 生成一个范围在 [0, upperBound) 内的随机 byte 值。
   *
   * @param upperBound
   *     生成随机数的独占上界。
   * @return 一个范围在 [0, upperBound) 内的随机 byte 值。
   */
  public final byte nextByte(final byte upperBound) {
    return (byte) nextInt(upperBound);
  }

  /**
   * 生成一个范围在 [lowerBound, upperBound) 内的随机 byte 值。
   *
   * @param lowerBound
   *     生成随机数的包含下界。
   * @param upperBound
   *     生成随机数的独占上界。
   * @return 一个范围在 [lowerBound, upperBound) 内的随机 byte 值。
   */
  public final byte nextByte(final byte lowerBound, final byte upperBound) {
    return (byte) nextInt(lowerBound, upperBound);
  }

  /**
   * 生成一个指定闭区间范围内的随机 byte 值。
   *
   * @param range
   *     生成随机数的闭区间范围。
   * @return 一个范围在 [range.min, range.max] 内的随机 byte 值。
   */
  public final byte nextByte(final CloseRange<Byte> range) {
    final int min = range.getMin();
    final int max = range.getMax();
    return (byte) nextInt(new CloseRange<>(min, max));
  }

  /**
   * 生成一个随机的 short 值。
   *
   * @return 一个随机的 short 值。
   */
  public final short nextShort() {
    final byte[] bytes = new byte[2];
    nextBytes(bytes);
    return (short) ((bytes[0] << ByteBit.BITS) | (bytes[1]));
  }

  /**
   * 生成一个范围在 [0, upperBound) 内的随机 short 值。
   *
   * @param upperBound
   *     生成随机数的独占上界。
   * @return 一个范围在 [0, upperBound) 内的随机 short 值。
   */
  public final short nextShort(final short upperBound) {
    return (short) nextInt(upperBound);
  }

  /**
   * 生成一个范围在 [lowerBound, upperBound) 内的随机 short 值。
   *
   * @param lowerBound
   *     生成随机数的包含下界。
   * @param upperBound
   *     生成随机数的独占上界。
   * @return 一个范围在 [lowerBound, upperBound) 内的随机 short 值。
   */
  public final short nextShort(final short lowerBound, final short upperBound) {
    return (short) nextInt(lowerBound, upperBound);
  }

  /**
   * 生成一个指定闭区间范围内的随机 short 值。
   *
   * @param range
   *     生成随机数的闭区间范围。
   * @return 一个范围在 [range.min, range.max] 内的随机 short 值。
   */
  public final short nextShort(final CloseRange<Short> range) {
    final int min = range.getMin();
    final int max = range.getMax();
    return (short) nextInt(new CloseRange<>(min, max));
  }

  /**
   * 生成一个范围在 [lowerBound, upperBound) 内的随机整数。
   *
   * @param lowerBound
   *     生成随机数的包含下界。
   * @param upperBound
   *     生成随机数的独占上界。
   * @return 一个范围在 [lowerBound, upperBound) 内的随机整数。
   */
  @Override
  public final int nextInt(final int lowerBound, final int upperBound) {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException(
          "lower bound must be less than upper bound");
    }
    // note that (upperBound - lowerBound) may exceed the range of 32-bit integers.
    // for example, lowerBound = Integer.MIN_VALUE and upperBound = Integer.MAX_VALUE
    return (int) (nextLong((long) upperBound - (long) lowerBound)
        + (long) lowerBound);
  }

  /**
   * 生成一个指定闭区间范围内的随机整数。
   *
   * @param range
   *     生成随机数的闭区间范围。
   * @return 一个范围在 [range.min, range.max] 内的随机整数。
   */
  public final int nextInt(final CloseRange<Integer> range) {
    range.check();
    final long lower = range.getMin();
    final long upper = ((long) range.getMax()) + 1L;
    return (int) (nextLong(upper - lower) + lower);
  }

  /**
   * 生成一个 32 位无符号随机整数，即范围在 [0, 2^32) 内的随机整数。
   *
   * @return 一个 32 位无符号随机整数。
   */
  private final long generate32() {
    return (long) this.nextInt() - (long) Integer.MIN_VALUE;
  }

  /**
   * 生成一个范围在 [0, bound) 内的小随机数。
   *
   * @param bound
   *     生成随机数的独占上界，必须小于 2^32。
   * @return 一个范围在 [0, bound) 内的小随机数。
   */
  private final long generateSmall(final long bound) {
    //  生成一个 [0, bound) 范围内的随机数，其中
    //          0 < bound < 2^32 = b
    //  设置
    //      top = \floor(b / bound) * bound
    //  然后如果随机数生成器生成的随机数在范围
    //      [top, b)
    //  内，则拒绝该随机数并重新生成一个随机数，
    //  直到得到一个范围内的随机数 x
    //      [0, top)
    //  然后值
    //      x % bound
    //  就是一个范围内的随机数
    //      [0, bound)
    //  注意计算必须小心避免溢出。
    final long b = (1L << IntBit.BITS);
    assert (bound < b);
    final long top = (b / bound) * bound;
    long result;
    do {
      result = generate32();
    } while (result >= top);
    result %= bound;
    return result;
  }

  /**
   * 生成一个范围在 [0, bound) 内的大随机数。
   *
   * @param bound
   *     生成随机数的独占上界，必须大于 2^32。
   * @return 一个范围在 [0, bound) 内的大随机数。
   */
  private final long generateLarge(final long bound) {
    //  设 n = bound - 1, b = 2^32，并设 k 为一个整数使得
    //      b^{k} <= n < b^{k+1}
    //  那么范围 [0, n] 内的每个 x 都可以表示为 b 进制的整数，如下所示：
    //      x = x_0 + x_1 * b + x_2 * b^2 + ... + x_{k-1} * b^{k-1} + x_k * b^{k}
    //  其中
    //      0 <= x_i < b
    //  对于
    //      i = 0, 1, .., k - 1
    //  且
    //      0 <= x_k <= r
    //  其中
    //      r = \floor(n / b^{k})
    //
    //  要从均匀随机数生成器 [0, 2^32) 生成 [0, n] 上的均匀分布，可以首先生成
    //  k - 1 个 [0, 2^32) 范围内的均匀分布随机数 x_0, x_1, ..., x_{k-1}
    //  和一个 [0, r] 范围内的均匀分布随机数 x_k，然后使用上述公式组合它们
    //  以获得数字 x；如果 x 在范围 [0, n] 内，我们就得到了该范围内的均匀分布
    //  随机数；否则，重复上述过程直到找到范围 [0, n] 内的组合数字 x。
    final long b = (1L << IntBit.BITS);
    assert (bound >= b);
    final long n = bound - 1;
    final long limit = n / b;
    long result;
    do {
      result = 0;
      long exp = 1;
      while (exp <= limit) {
        final long x = generate32();
        result += x * exp;
        exp *= b;
      }
      assert ((exp <= n) && (n / b < exp));
      final long r = n / exp;
      if (r == b - 1) {
        final long x = generate32();
        result += x * exp;
      } else {  // r < b - 1
        final long x = generateSmall(r + 1);
        result += x * exp;
      }
    } while (result > n);
    return result;
  }

  /**
   * 生成一个范围在 [0, bound) 内的随机长整数。
   *
   * @param bound
   *     生成随机数的独占上界，必须为正数。
   * @return 一个范围在 [0, bound) 内的随机长整数。
   */
  @Override
  public final long nextLong(final long bound) {
    if (bound <= 0) {
      throw new IllegalArgumentException("bound must be positive");
    }
    final long range = (1L << IntBit.BITS);
    if (bound < range) {
      return generateSmall(bound);
    } else if (bound == range) {
      return generate32();
    } else {
      return generateLarge(bound);
    }
  }

  /**
   * 生成一个范围在 [lowerBound, upperBound) 内的随机长整数。
   *
   * @param lowerBound
   *     生成随机数的包含下界。
   * @param upperBound
   *     生成随机数的独占上界。
   * @return 一个范围在 [lowerBound, upperBound) 内的随机长整数。
   */
  @Override
  public final long nextLong(final long lowerBound, final long upperBound) {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException(
          "lower bound must be less than upper bound");
    }
    return nextLong(new CloseRange<>(lowerBound, upperBound - 1));
  }

  /**
   * 生成一个指定闭区间范围内的随机长整数。
   *
   * @param range
   *     生成随机数的闭区间范围。
   * @return 一个范围在 [range.min, range.max] 内的随机长整数。
   */
  public final long nextLong(final CloseRange<Long> range) {
    range.check();
    final Long min = range.getMin();
    final Long max = range.getMax();
    if (min == max) {
      return min;
    } else if (min == Long.MIN_VALUE && max == Long.MAX_VALUE) {
      return this.nextLong();
    }
    // 如果 bound = max - min + 1 <= Long.MAX_VALUE，使用简单算法
    try {
      final long bound = Math.addExact(Math.subtractExact(max, min), 1L);
      return nextLong(bound) + min;
    } catch (final ArithmeticException e) {
      // 算术溢出，尝试另一种算法
    }
    // 否则，使用近似算法
    // FIXME: 应该使用更精确的算法
    return (long) nextDouble(new CloseRange<>((double) min, (double) max));
  }

  /**
   * 返回一个范围在 {@code [lowerBound, upperBound)} 内的随机双精度浮点数。
   *
   * @param lowerBound
   *     包含下界值。
   * @param upperBound
   *     独占上界值。
   * @return 指定范围内的随机双精度浮点数。
   */
  @Override
  public final double nextDouble(final double lowerBound,
      final double upperBound) {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException(
          "lower bound must be less than upper bound");
    }
    final double value = lowerBound + (this.nextDouble() * (upperBound
        - lowerBound));
    if (value < lowerBound) {
      return lowerBound;
    } else if (value >= upperBound) {
      return upperBound - EPSILON;
    } else {
      return value;
    }
  }

  /**
   * 返回一个范围在 {@code [range.min, range.max]} 内的随机双精度浮点数。
   *
   * @param range
   *     闭区间范围。
   * @return 指定范围内的随机双精度浮点数。
   */
  public final double nextDouble(final CloseRange<Double> range) {
    range.check();
    final Double min = range.getMin();
    final Double max = range.getMax();
    final double value = min + (this.nextDouble() * (max - min));
    if (value < min) {
      return min;
    } else if (value > max) {
      return max;
    } else {
      return value;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final float nextFloat(final float lowerBound, final float upperBound) {
    return (float) nextDouble(lowerBound, upperBound);
  }

  /**
   * 返回一个指定闭区间范围内的随机单精度浮点数。
   *
   * @param range
   *     闭区间范围。
   * @return 指定范围内的随机单精度浮点数。
   */
  public final float nextFloat(final CloseRange<Float> range) {
    final double min = range.getMin();
    final double max = range.getMax();
    return (float) nextDouble(new CloseRange<>(min, max));
  }

  /**
   * 返回一个随机的 {@link BigDecimal}。
   *
   * @return 一个随机的 {@link BigDecimal}。
   */
  public final BigDecimal nextBigDecimal() {
    return BigDecimal.valueOf(nextDouble());
  }

  /**
   * 返回一个范围在 {@code [lowerBound, upperBound)} 内的随机 {@link BigDecimal}。
   *
   * @param lowerBound
   *     包含下界值。
   * @param upperBound
   *     独占上界值。
   * @return 指定范围内的随机 {@link BigDecimal}。
   */
  public final BigDecimal nextBigDecimal(final double lowerBound,
      final double upperBound) {
    return BigDecimal.valueOf(nextDouble(lowerBound, upperBound));
  }

  /**
   * 返回一个范围在 {@code [range.min, range.max]} 内的随机 {@link BigDecimal}。
   *
   * @param range
   *     闭区间范围。
   * @return 指定范围内的随机 {@link BigDecimal}。
   */
  public final BigDecimal nextBigDecimal(final CloseRange<Double> range) {
    return BigDecimal.valueOf(nextDouble(range));
  }

  /**
   * 生成一个随机的数字字符（0-9）。
   *
   * @return 一个随机的数字字符。
   */
  public final char nextDigitChar() {
    final int index = nextInt(10);
    return (char) ('0' + index);
  }

  /**
   * 生成指定长度的随机数字字符数组。
   *
   * @param size
   *     字符数组的长度。
   * @return 指定长度的随机数字字符数组。
   */
  public final char[] nextDigitChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextDigitChar();
    }
    return result;
  }

  /**
   * 生成指定长度范围内的随机数字字符数组。
   *
   * @param sizeRange
   *     字符数组长度的范围。
   * @return 随机长度的数字字符数组。
   */
  public final char[] nextDigitChars(final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextDigitChars(size);
  }

  /**
   * 生成一个随机的小写字母字符（a-z）。
   *
   * @return 一个随机的小写字母字符。
   */
  public final char nextLowercaseLetterChar() {
    final int index = nextInt(26);
    return (char) ('a' + index);
  }

  /**
   * 生成指定长度的随机小写字母字符数组。
   *
   * @param size
   *     字符数组的长度。
   * @return 指定长度的随机小写字母字符数组。
   */
  public final char[] nextLowercaseLetterChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextLowercaseLetterChar();
    }
    return result;
  }

  /**
   * 生成指定长度范围内的随机小写字母字符数组。
   *
   * @param sizeRange
   *     字符数组长度的范围。
   * @return 随机长度的小写字母字符数组。
   */
  public final char[] nextLowercaseLetterChars(
      final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextLowercaseLetterChars(size);
  }

  /**
   * 生成一个随机的大写字母字符（A-Z）。
   *
   * @return 一个随机的大写字母字符。
   */
  public final char nextUppercaseLetterChar() {
    final int index = nextInt(26);
    return (char) ('A' + index);
  }

  /**
   * 生成指定长度的随机大写字母字符数组。
   *
   * @param size
   *     字符数组的长度。
   * @return 指定长度的随机大写字母字符数组。
   */
  public final char[] nextUppercaseLetterChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextUppercaseLetterChar();
    }
    return result;
  }

  /**
   * 生成指定长度范围内的随机大写字母字符数组。
   *
   * @param sizeRange
   *     字符数组长度的范围。
   * @return 随机长度的大写字母字符数组。
   */
  public final char[] nextUppercaseLetterChars(
      final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextUppercaseLetterChars(size);
  }

  /**
   * 生成一个随机的字母字符（a-z, A-Z）。
   *
   * @return 一个随机的字母字符。
   */
  public final char nextLetterChar() {
    return choose(Ascii.LETTER_CHARS);
  }

  /**
   * 生成指定长度的随机字母字符数组。
   *
   * @param size
   *     字符数组的长度。
   * @return 指定长度的随机字母字符数组。
   */
  public final char[] nextLetterChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextLetterChar();
    }
    return result;
  }

  /**
   * 生成指定长度范围内的随机字母字符数组。
   *
   * @param sizeRange
   *     字符数组长度的范围。
   * @return 随机长度的字母字符数组。
   */
  public final char[] nextLetterChars(final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextLetterChars(size);
  }

  /**
   * 生成一个随机的字母数字字符（a-z, A-Z, 0-9）。
   *
   * @return 一个随机的字母数字字符。
   */
  public final char nextLetterDigitChar() {
    return choose(Ascii.LETTER_DIGIT_CHARS);
  }

  /**
   * 生成指定长度的随机字母数字字符数组。
   *
   * @param size
   *     字符数组的长度。
   * @return 指定长度的随机字母数字字符数组。
   */
  public final char[] nextLetterDigitChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextLetterDigitChar();
    }
    return result;
  }

  /**
   * 生成指定长度范围内的随机字母数字字符数组。
   *
   * @param sizeRange
   *     字符数组长度的范围。
   * @return 随机长度的字母数字字符数组。
   */
  public final char[] nextLetterDigitChars(
      final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextLetterDigitChars(size);
  }

  /**
   * 生成指定长度的随机数字字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机数字字符串。
   */
  public final String nextDigitString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextDigitChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机数字字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的数字字符串。
   */
  public final String nextDigitString(final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextDigitString(length);
  }

  /**
   * 生成指定长度的随机小写字母字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机小写字母字符串。
   */
  public final String nextLowercaseLetterString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextLowercaseLetterChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机小写字母字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的小写字母字符串。
   */
  public final String nextLowercaseLetterString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextLowercaseLetterString(length);
  }

  /**
   * 生成指定长度的随机大写字母字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机大写字母字符串。
   */
  public final String nextUppercaseLetterString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextUppercaseLetterChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机大写字母字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的大写字母字符串。
   */
  public final String nextUppercaseLetterString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextUppercaseLetterString(length);
  }

  /**
   * 生成指定长度的随机字母字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机字母字符串。
   */
  public final String nextLetterString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextLetterChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机字母字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的字母字符串。
   */
  public final String nextLetterString(final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextLetterString(length);
  }

  /**
   * 生成指定长度的随机字母数字字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机字母数字字符串。
   */
  public final String nextLetterDigitString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextLetterDigitChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机字母数字字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的字母数字字符串。
   */
  public final String nextLetterDigitString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextLetterDigitString(length);
  }

  /**
   * 生成一个随机的可打印ASCII字符。
   *
   * @return 一个随机的可打印ASCII字符。
   */
  public final char nextPrintableAsciiChar() {
    final int index = this.nextInt(Ascii.PRINTABLE_CHARS.length);
    return Ascii.PRINTABLE_CHARS[index];
  }

  /**
   * 生成指定长度的随机可打印ASCII字符数组。
   *
   * @param size
   *     字符数组的长度。
   * @return 指定长度的随机可打印ASCII字符数组。
   */
  public final char[] nextPrintableAsciiChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextPrintableAsciiChar();
    }
    return result;
  }

  /**
   * 生成指定长度的随机可打印ASCII字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机可打印ASCII字符串。
   */
  public final String nextPrintableAsciiString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextPrintableAsciiChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机可打印ASCII字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的可打印ASCII字符串。
   */
  public final String nextPrintableAsciiString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextPrintableAsciiString(length);
  }

  /**
   * 生成指定长度的随机字符串。
   *
   * @param length
   *     字符串的长度。
   * @return 指定长度的随机字符串。
   */
  public final String nextString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextPrintableAsciiChar());
    }
    return builder.toString();
  }

  /**
   * 生成指定长度范围内的随机字符串。
   *
   * @param minLength
   *     字符串的最小长度。
   * @param maxLength
   *     字符串的最大长度。
   * @return 指定长度范围内的随机字符串。
   */
  public final String nextString(final int minLength, final int maxLength) {
    final int length = nextInt(new CloseRange<>(minLength, maxLength));
    return nextString(length);
  }

  /**
   * 生成指定长度范围内的随机字符串。
   *
   * @param lengthRange
   *     字符串长度的范围。
   * @return 随机长度的字符串。
   */
  public final String nextString(final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextString(length);
  }

  /**
   * 生成默认长度范围内的随机字符串。
   *
   * @return 默认长度范围内的随机字符串。
   */
  public final String nextString() {
    return nextString(DEFAULT_STRING_LENGTH_RANGE);
  }

  /**
   * 生成一个随机的本地时间。
   *
   * @return 一个随机的本地时间。
   */
  public final LocalTime nextLocalTime() {
    final int hour = this.nextInt(24);
    final int minute = this.nextInt(60);
    final int second = this.nextInt(60);
    return LocalTime.of(hour, minute, second);
  }

  /**
   * 生成指定范围内的随机本地时间。
   *
   * @param range
   *     时间范围。
   * @return 指定范围内的随机本地时间。
   */
  public final LocalTime nextLocalTime(final CloseRange<LocalTime> range) {
    final long minSecondOfDay = range.getMin()
                                     .getLong(ChronoField.SECOND_OF_DAY);
    final long maxSecondOfDay = range.getMax()
                                     .getLong(ChronoField.SECOND_OF_DAY);
    final long randomSecondOfDay = nextLong(minSecondOfDay, maxSecondOfDay);
    return LocalTime.ofSecondOfDay(randomSecondOfDay);
  }

  /**
   * 获取指定年月的天数。
   *
   * @param year
   *     年份。
   * @param month
   *     月份。
   * @return 指定年月的天数。
   */
  private static int getDaysInMonth(final int year, final int month) {
    // stop checkstyle: MagicNumberCheck
    switch (month) {
      case 2:
        return (IsoChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      default:
        return 31;
    }
    // resume checkstyle: MagicNumberCheck
  }

  /**
   * 生成一个随机的本地日期。
   *
   * @return 一个随机的本地日期。
   */
  public final LocalDate nextLocalDate() {
    final int year = this.nextInt(3000);
    final int month = this.nextInt(12) + 1;
    final int day = this.nextInt(getDaysInMonth(year, month)) + 1;
    return LocalDate.of(year, month, day);
  }

  /**
   * 生成指定范围内的随机本地日期。
   *
   * @param range
   *     日期范围。
   * @return 指定范围内的随机本地日期。
   */
  public final LocalDate nextLocalDate(final CloseRange<LocalDate> range) {
    final long minEpochDay = range.getMin().getLong(ChronoField.EPOCH_DAY);
    final long maxEpochDay = range.getMax().getLong(ChronoField.EPOCH_DAY);
    final long randomEpochDay = nextLong(minEpochDay, maxEpochDay);
    return LocalDate.ofEpochDay(randomEpochDay);
  }

  /**
   * 生成一个随机的本地日期时间。
   *
   * @return 一个随机的本地日期时间。
   */
  public final LocalDateTime nextLocalDateTime() {
    final LocalDate date = nextLocalDate();
    final LocalTime time = nextLocalTime();
    return LocalDateTime.of(date, time);
  }

  /**
   * 生成指定范围内的随机本地日期时间。
   *
   * @param range
   *     日期时间范围。
   * @return 指定范围内的随机本地日期时间。
   */
  public final LocalDateTime nextLocalDateTime(
      final CloseRange<LocalDateTime> range) {
    final long minSeconds = range.getMin().toEpochSecond(ZoneOffset.UTC);
    final long maxSeconds = range.getMax().toEpochSecond(ZoneOffset.UTC);
    final long seconds = nextLong(minSeconds, maxSeconds);
    final Instant instant = Instant.ofEpochSecond(seconds);
    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
  }

  /**
   * 生成一个随机的时刻。
   *
   * @return 一个随机的时刻。
   */
  public final Instant nextInstant() {
    final LocalDateTime datetime = nextLocalDateTime();
    return datetime.toInstant(ZoneOffset.UTC);
  }

  /**
   * 生成指定范围内的随机时刻。
   *
   * @param range
   *     时刻范围。
   * @return 指定范围内的随机时刻。
   */
  public final Instant nextInstant(final CloseRange<Instant> range) {
    final long minEpochMillis = range.getMin().toEpochMilli();
    final long maxEpochMillis = range.getMax().toEpochMilli();
    final long randomEpochMillis = nextLong(
        new CloseRange<>(minEpochMillis, maxEpochMillis));
    return Instant.ofEpochMilli(randomEpochMillis);
  }

  /**
   * 生成一个随机的布尔数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的布尔数组。
   */
  public final boolean[] nextBooleanArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final boolean[] result = new boolean[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextBoolean();
    }
    return result;
  }

  /**
   * 生成一个随机的字符数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的字符数组。
   */
  public final char[] nextCharArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final char[] result = new char[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextPrintableAsciiChar();
    }
    return result;
  }

  /**
   * 生成一个随机的字节数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的字节数组。
   */
  public final byte[] nextByteArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final byte[] result = new byte[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextByte();
    }
    return result;
  }

  /**
   * 生成一个随机的短整数数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的短整数数组。
   */
  public final short[] nextShortArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final short[] result = new short[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextShort();
    }
    return result;
  }

  /**
   * 生成一个随机的整数数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的整数数组。
   */
  public final int[] nextIntArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final int[] result = new int[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextInt();
    }
    return result;
  }

  /**
   * 生成一个随机的长整数数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的长整数数组。
   */
  public final long[] nextLongArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final long[] result = new long[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextLong();
    }
    return result;
  }

  /**
   * 生成一个随机的单精度浮点数数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的单精度浮点数数组。
   */
  public final float[] nextFloatArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final float[] result = new float[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextFloat();
    }
    return result;
  }

  /**
   * 生成一个随机的双精度浮点数数组。
   *
   * @param maxSize
   *     数组的最大长度。
   * @param allowEmpty
   *     是否允许空数组。
   * @return 一个随机的双精度浮点数数组。
   */
  public final double[] nextDoubleArray(final int maxSize, final boolean allowEmpty) {
    if (maxSize <= 0) {
      throw new IllegalArgumentException("The max size must be positive.");
    }
    final int lowerBound = (allowEmpty ? 0 : 1);
    final int n = this.nextInt(lowerBound, maxSize);
    final double[] result = new double[n];
    for (int i = 0; i < n; ++i) {
      result[i] = this.nextDouble();
    }
    return result;
  }

  //  public final <T> T[] nextArray(final Class<T> elementType, final int maxSize,
  //      final boolean allowEmpty) {
  //    if (maxSize <= 0) {
  //      throw new IllegalArgumentException("The max size must be positive.");
  //    }
  //    final int lowerBound = (allowEmpty ? 0 : 1);
  //    final int n = this.nextInt(lowerBound, maxSize);
  //    final T[] result = createArray(elementType, n);
  //    for (int i = 0; i < n; ++i) {
  //      result[i] = this.nextInt();
  //    }
  //    return result;
  //  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final boolean choose(final boolean[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final char choose(final char[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final byte choose(final byte[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final short choose(final short[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final int choose(final int[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final long choose(final long[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final float choose(final float[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @return 从数组中随机选择的元素。
   */
  public final double choose(final double[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从数组中随机选择一个元素。
   *
   * @param array
   *     数组。
   * @param <T>
   *     数组中元素的类型。
   * @return 从数组中随机选择的元素。
   */
  public final <T> T choose(final T[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * 从列表中随机选择一个元素。
   *
   * @param list
   *     列表。
   * @param <T>
   *     列表中元素的类型。
   * @return 从列表中随机选择的元素。
   */
  public final <T> T choose(final List<T> list) {
    final int index = this.nextInt(list.size());
    return list.get(index);
  }

  /**
   * 从集合中随机选择一个元素。
   *
   * @param collection
   *     集合。
   * @param <T>
   *     集合中元素的类型。
   * @return 从集合中随机选择的元素。
   */
  public final <T> T choose(final Collection<T> collection) {
    final int index = this.nextInt(collection.size());
    final Iterator<T> iterator = collection.iterator();
    for (int i = 0; i < index; ++i) {
      iterator.next();
    }
    return iterator.next();
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final boolean[] choose(final boolean[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] source = new boolean[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final boolean[] result = new boolean[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final char[] choose(final char[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    }
    final char[] source = new char[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final char[] result = new char[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final byte[] choose(final byte[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final byte[] source = new byte[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final byte[] result = new byte[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final short[] choose(final short[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    }
    final short[] source = new short[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final short[] result = new short[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final int[] choose(final int[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    }
    final int[] source = new int[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final int[] result = new int[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final long[] choose(final long[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    }
    final long[] source = new long[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final long[] result = new long[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final float[] choose(final float[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    }
    final float[] source = new float[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final float[] result = new float[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  public final double[] choose(final double[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    }
    final double[] source = new double[array.length];
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final double[] result = new double[k];
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从数组中随机选择指定数量的元素。
   *
   * @param array
   *     指定的数组。
   * @param k
   *     要选择的元素数量。
   * @return 从数组中随机选择的元素。
   */
  @SuppressWarnings("unchecked")
  public final <T> T[] choose(final T[] array, final int k) {
    if (k <= 0 || array.length == 0) {
      return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    final T[] source = createArrayOfSameElementType(array, array.length);
    System.arraycopy(array, 0, source, 0, array.length);
    if (k >= array.length) {
      return source;
    }
    final T[] result = createArrayOfSameElementType(array, k);
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从集合中随机选择指定数量的元素。
   *
   * @param col
   *     指定的集合。
   * @param k
   *     要选择的元素数量。
   * @return 从集合中随机选择的元素。
   */
  @SuppressWarnings("unchecked")
  public final <T> List<T> choose(final Collection<T> col, final int k) {
    if (k >= col.size()) {
      return new ArrayList<>(col);
    }
    final Object[] source = col.toArray();
    final List<T> result = new ArrayList<>();
    int n = source.length;
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      final Object value = source[index];
      result.add((T) value);
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }

  /**
   * 从给定范围中随机选择指定数量的不同值。
   *
   * @param lowerBound
   *     指定范围的包含下界。
   * @param upperBound
   *     指定范围的独占上界。
   * @param k
   *     要选择的值的数量。
   * @return
   *     随机选择的值。
   */
  public final int[] choose(final int lowerBound, final int upperBound,
      final int k) {
    if (k <= 0 || lowerBound >= upperBound) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    }
    int n = upperBound - lowerBound;
    final int[] source = new int[n];
    for (int i = 0; i < n; ++i) {
      source[i] = i + lowerBound;
    }
    if (k >= n) {
      return source;
    }
    final int[] result = new int[k];
    for (int i = 0; i < k; ++i) {
      final int index = this.nextInt(n);
      result[i] = source[index];
      source[index] = source[n - 1];
      --n;
      if (n == 0) {
        break;
      }
    }
    return result;
  }
}