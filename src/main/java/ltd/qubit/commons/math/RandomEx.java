////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

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

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.text.Ascii;
import ltd.qubit.commons.util.range.CloseRange;
import ltd.qubit.commons.util.range.UnmodifiableCloseRange;

import static ltd.qubit.commons.lang.ArrayUtils.createArrayOfSameElementType;

/**
 * An extended versions of {@link java.util.Random}.
 *
 * <p>Instances of {@link RandomEx} are thread-safe.</p>
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class RandomEx extends Random {

  private static final long serialVersionUID = 4648505854602540292L;

  public static final double EPSILON = 0.00000001;

  public static final CloseRange<Integer> DEFAULT_STRING_LENGTH_RANGE =
      new UnmodifiableCloseRange<>(5, 100);

  public RandomEx() {
  }

  public RandomEx(final long seed) {
    super(seed);
  }

  public final byte nextByte() {
    final byte[] bytes = new byte[1];
    nextBytes(bytes);
    return bytes[0];
  }

  public final byte nextByte(final byte upperBound) {
    return (byte) nextInt(upperBound);
  }

  public final byte nextByte(final byte lowerBound, final byte upperBound) {
    return (byte) nextInt(lowerBound, upperBound);
  }

  public final byte nextByte(final CloseRange<Byte> range) {
    final int min = range.getMin();
    final int max = range.getMax();
    return (byte) nextInt(new CloseRange<>(min, max));
  }

  public final short nextShort() {
    final byte[] bytes = new byte[2];
    nextBytes(bytes);
    return (short) ((bytes[0] << ByteBit.BITS) | (bytes[1]));
  }

  public final short nextShort(final short upperBound) {
    return (short) nextInt(upperBound);
  }

  public final short nextShort(final short lowerBound, final short upperBound) {
    return (short) nextInt(lowerBound, upperBound);
  }

  public final short nextShort(final CloseRange<Short> range) {
    final int min = range.getMin();
    final int max = range.getMax();
    return (short) nextInt(new CloseRange<>(min, max));
  }

  /**
   * Generate a random integer between [lowerBound, upperBound).
   *
   * @param lowerBound
   *     the inclusive lower bound of the generated random number.
   * @param upperBound
   *     the exclusive upper bound of the generated random number.
   * @return a random integer between [lowerBound, upperBound).
   */
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
   * Generate a random integer between a close range.
   *
   * @param range
   *     the close range of the generated random number.
   * @return a random integer between [range.min, range.max].
   */
  public final int nextInt(final CloseRange<Integer> range) {
    range.check();
    final long lower = range.getMin();
    final long upper = ((long) range.getMax()) + 1L;
    return (int) (nextLong(upper - lower) + lower);
  }

  /**
   * Generate a 32-bits unsigned random integer, i.e., a random integer inside
   * [0, 2^32).
   *
   * @return a 32-bits unsigned random integer.
   */
  private final long generate32() {
    return (long) this.nextInt() - (long) Integer.MIN_VALUE;
  }

  /**
   * Generate a small random number between [0, bound).
   *
   * @param bound
   *     the exclusive upper bound of the generated random number, which must be
   *     smaller than 2^32.
   * @return a small random number between [0, bound).
   */
  private final long generateSmall(final long bound) {
    //  generate a random number between [0, bound), where
    //          0 < bound < 2^32 = b
    //  set
    //      top = \floor(b / bound) * bound
    //  then if the rng generate a random number within the range
    //      [top, b)
    //  the random number is REJECTED and generate a random number again,
    //  until get a random number x within the range
    //      [0, top)
    //  then the value
    //      x % bound
    //  is a random number within the range
    //      [0, bound)
    //  Note that the calculation must be careful to avoid overflows.
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
   * Generate a large random number between [0, bound).
   *
   * @param bound
   *     the exclusive upper bound of the generated random number, which must be
   *     greater than 2^32.
   * @return a large random number between [0, bound).
   */
  private final long generateLarge(final long bound) {
    //  Let n = bound - 1, b = 2^32, and let k be a integer such that
    //      b^{k} <= n < b^{k+1}
    //  Then each x in the range [0, n] can be represented as an integer in the
    //  radix of b as following:
    //      x = x_0 + x_1 * b + x_2 * b^2 + ... + x_{k-1} * b^{k-1} + x_k * b^{k}
    //  where
    //      0 <= x_i < b
    //  for
    //      i = 0, 1, .., k - 1
    //  and
    //      0 <= x_k <= r
    //  where
    //      r = \floor(n / b^{k})
    //
    //  To generate a uniform distribution over [0, n] from an uniform random
    //  number generator over [0, 2^32), one could firstly generate
    //  k - 1 uniform distributed random numbers x_0, x_1, ..., x_{k-1} over
    //  the range of [0, 2^32) and a uniform distributed random number
    //  x_k over the range of [0, r], then compose them using the above formula
    //  to get a number x; if x is inside the range [0, n], we get a uniform
    //  distributed random number inside that range; otherwise, repeat the above
    //  procedure until find a composed number x within [0, n].
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
   * Generate a random long between [0, bound).
   *
   * @param bound
   *     the exclusive upper bound of the generated random number, which must be
   *     positive.
   * @return a random long between [0, bound).
   */
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
   * Generate a random long between [lowerBound, upperBound).
   *
   * @param lowerBound
   *     the inclusive lower bound of the generated random number.
   * @param upperBound
   *     the exclusive upper bound of the generated random number.
   * @return a random long between [lowerBound, upperBound).
   */
  public final long nextLong(final long lowerBound, final long upperBound) {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException(
          "lower bound must be less than upper bound");
    }
    return nextLong(new CloseRange<>(lowerBound, upperBound - 1));
  }

  /**
   * Generate a random long between a close range.
   *
   * @param range
   *     the close range of the generated random number.
   * @return a random long between [range.min, range.max].
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
    // if bound = max - min + 1 <= Long.MAX_VALUE, use the simple algorithm
    try {
      final long bound = Math.addExact(Math.subtractExact(max, min), 1L);
      return nextLong(bound) + min;
    } catch (final ArithmeticException e) {
      // arithmetic overflow, try another algorithm
    }
    // otherwise, use a approximate algorithm
    // FIXME: should use a more precise algorithm
    return (long) nextDouble(new CloseRange<>((double) min, (double) max));
  }

  /**
   * Return a random double between {@code [lowerBound, upperBound)}.
   *
   * @param lowerBound
   *     the inclusive lower bound value
   * @param upperBound
   *     the exclusive upper bound value
   * @return a random double in the given range
   */
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
   * Return a random double between {@code [range.min, range.max]}.
   *
   * @param range
   *     the closed range.
   * @return a random double in the given range
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

  public final float nextFloat(final float lowerBound, final float upperBound) {
    return (float) nextDouble(lowerBound, upperBound);
  }

  public final float nextFloat(final CloseRange<Float> range) {
    final double min = range.getMin();
    final double max = range.getMax();
    return (float) nextDouble(new CloseRange<>(min, max));
  }

  public final char nextDigitChar() {
    final int index = nextInt(10);
    return (char) ('0' + index);
  }

  public final char[] nextDigitChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextDigitChar();
    }
    return result;
  }

  public final char[] nextDigitChars(final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextDigitChars(size);
  }

  public final char nextLowercaseLetterChar() {
    final int index = nextInt(26);
    return (char) ('a' + index);
  }

  public final char[] nextLowercaseLetterChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextLowercaseLetterChar();
    }
    return result;
  }

  public final char[] nextLowercaseLetterChars(
      final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextLowercaseLetterChars(size);
  }

  public final char nextUppercaseLetterChar() {
    final int index = nextInt(26);
    return (char) ('A' + index);
  }

  public final char[] nextUppercaseLetterChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextUppercaseLetterChar();
    }
    return result;
  }

  public final char[] nextUppercaseLetterChars(
      final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextUppercaseLetterChars(size);
  }

  public final char nextLetterChar() {
    return choose(Ascii.LETTER_CHARS);
  }

  public final char[] nextLetterChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextLetterChar();
    }
    return result;
  }

  public final char[] nextLetterChars(final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextLetterChars(size);
  }

  public final char nextLetterDigitChar() {
    return choose(Ascii.LETTER_DIGIT_CHARS);
  }

  public final char[] nextLetterDigitChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextLetterDigitChar();
    }
    return result;
  }

  public final char[] nextLetterDigitChars(
      final CloseRange<Integer> sizeRange) {
    final int size = nextInt(sizeRange);
    return nextLetterDigitChars(size);
  }

  public final String nextDigitString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextDigitChar());
    }
    return builder.toString();
  }

  public final String nextDigitString(final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextDigitString(length);
  }

  public final String nextLowercaseLetterString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextLowercaseLetterChar());
    }
    return builder.toString();
  }

  public final String nextLowercaseLetterString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextLowercaseLetterString(length);
  }

  public final String nextUppercaseLetterString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextUppercaseLetterChar());
    }
    return builder.toString();
  }

  public final String nextUppercaseLetterString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextUppercaseLetterString(length);
  }

  public final String nextLetterString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextLetterChar());
    }
    return builder.toString();
  }

  public final String nextLetterString(final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextLetterString(length);
  }

  public final String nextLetterDigitString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextLetterDigitChar());
    }
    return builder.toString();
  }

  public final String nextLetterDigitString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextLetterDigitString(length);
  }

  public final char nextPrintableAsciiChar() {
    final int index = this.nextInt(Ascii.PRINTABLE_CHARS.length);
    return Ascii.PRINTABLE_CHARS[index];
  }

  public final char[] nextPrintableAsciiChars(final int size) {
    final char[] result = new char[size];
    for (int i = 0; i < size; ++i) {
      result[i] = nextPrintableAsciiChar();
    }
    return result;
  }

  public final String nextPrintableAsciiString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextPrintableAsciiChar());
    }
    return builder.toString();
  }

  public final String nextPrintableAsciiString(
      final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextPrintableAsciiString(length);
  }

  public final String nextString(final int length) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      builder.append(nextPrintableAsciiChar());
    }
    return builder.toString();
  }

  public final String nextString(final int minLength, final int maxLength) {
    final int length = nextInt(new CloseRange<>(minLength, maxLength));
    return nextString(length);
  }

  public final String nextString(final CloseRange<Integer> lengthRange) {
    final int length = nextInt(lengthRange);
    return nextString(length);
  }

  public final String nextString() {
    return nextString(DEFAULT_STRING_LENGTH_RANGE);
  }

  public final LocalTime nextLocalTime() {
    final int hour = this.nextInt(24);
    final int minute = this.nextInt(60);
    final int second = this.nextInt(60);
    return LocalTime.of(hour, minute, second);
  }

  public final LocalTime nextLocalTime(final CloseRange<LocalTime> range) {
    final long minSecondOfDay = range.getMin()
                                     .getLong(ChronoField.SECOND_OF_DAY);
    final long maxSecondOfDay = range.getMax()
                                     .getLong(ChronoField.SECOND_OF_DAY);
    final long randomSecondOfDay = nextLong(minSecondOfDay, maxSecondOfDay);
    return LocalTime.ofSecondOfDay(randomSecondOfDay);
  }

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

  public final LocalDate nextLocalDate() {
    final int year = this.nextInt(3000);
    final int month = this.nextInt(12) + 1;
    final int day = this.nextInt(getDaysInMonth(year, month)) + 1;
    return LocalDate.of(year, month, day);
  }

  public final LocalDate nextLocalDate(final CloseRange<LocalDate> range) {
    final long minEpochDay = range.getMin().getLong(ChronoField.EPOCH_DAY);
    final long maxEpochDay = range.getMax().getLong(ChronoField.EPOCH_DAY);
    final long randomEpochDay = nextLong(minEpochDay, maxEpochDay);
    return LocalDate.ofEpochDay(randomEpochDay);
  }

  public final LocalDateTime nextLocalDateTime() {
    final LocalDate date = nextLocalDate();
    final LocalTime time = nextLocalTime();
    return LocalDateTime.of(date, time);
  }

  public final LocalDateTime nextLocalDateTime(
      final CloseRange<LocalDateTime> range) {
    final long minSeconds = range.getMin().toEpochSecond(ZoneOffset.UTC);
    final long maxSeconds = range.getMax().toEpochSecond(ZoneOffset.UTC);
    final long seconds = nextLong(minSeconds, maxSeconds);
    final Instant instant = Instant.ofEpochSecond(seconds);
    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
  }

  public final Instant nextInstant() {
    final LocalDateTime datetime = nextLocalDateTime();
    return datetime.toInstant(ZoneOffset.UTC);
  }

  public final Instant nextInstant(final CloseRange<Instant> range) {
    final long minEpochMillis = range.getMin().toEpochMilli();
    final long maxEpochMillis = range.getMax().toEpochMilli();
    final long randomEpochMillis = nextLong(
        new CloseRange<>(minEpochMillis, maxEpochMillis));
    return Instant.ofEpochMilli(randomEpochMillis);
  }

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
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final boolean choose(final boolean[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final char choose(final char[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final byte choose(final byte[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final short choose(final short[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final int choose(final int[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final long choose(final long[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final float choose(final float[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @return the randomly chosen element from the array.
   */
  public final double choose(final double[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from an array.
   *
   * @param array
   *     the array.
   * @param <T>
   *     the type of elements in the array.
   * @return the randomly chosen element from the array.
   */
  public final <T> T choose(final T[] array) {
    final int index = this.nextInt(array.length);
    return array[index];
  }

  /**
   * Randomly choose an element from a list.
   *
   * @param list
   *     the list.
   * @param <T>
   *     the type of elements in the list.
   * @return the randomly chosen element from the list.
   */
  public final <T> T choose(final List<T> list) {
    final int index = this.nextInt(list.size());
    return list.get(index);
  }

  /**
   * Randomly choose an element from a collection.
   *
   * @param collection
   *     the collection.
   * @param <T>
   *     the type of elements in the collection.
   * @return the randomly chosen element from the collection.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from an array.
   *
   * @param array
   *     the specified array.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the array.
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
   * Randomly choose the specified number of elements from a collection.
   *
   * @param col
   *     the specified collection.
   * @param k
   *     the specified number of elements to be chosen.
   * @return the randomly chosen elements from the collection.
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
   * Randomly choose specified number of different values from a given range.
   *
   * @param lowerBound
   *     the inclusive lower bound of the specified range.
   * @param upperBound
   *     the exclusive upper bound of the specified range.
   * @param k
   *     the specified number of values to be chosen.
   * @return
   *     the randomly chosen values.
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
