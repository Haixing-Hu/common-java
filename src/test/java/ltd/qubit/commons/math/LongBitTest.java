////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link LongBit}.
 *
 * @author Haixing Hu
 */
public class LongBitTest {

  private static final int SAMPLES = 10000;

  @Test
  public void testReverse() {
    final Random random = new Random(System.currentTimeMillis());
    for (int i = 0; i < SAMPLES; ++i) {
      final long x = random.nextLong();
      final long expected = calLongReverse(x);
      // System.out.println("Reverse of " + x + " is expected to be " + expected);
      assertEquals(expected, LongBit.reverse(x));
    }
  }

  private long calLongReverse(final long x) {
    long value = x;
    long result = 0;
    for (int i = 0; i < Long.SIZE; ++i) {
      result <<= 1;
      if ((value & 1L) == 1L) {
        result |= 1;
      }
      value >>>= 1;
    }
    return result;
  }

  @Test
  public void testReverse_1() {
    final long x =
        0B0000000000000000000000000000000000000000000100101101011010000111L;
    final long expected =
        0B1110000101101011010010000000000000000000000000000000000000000000L;
    final long actual = LongBit.reverse(x);
    assertEquals(expected, actual);
  }

  @Test
  public void testReverse_2() {
    // 0B0111010100011100000110000101010000100111001111101100011101100010L
    final long x = 8438646551500212066L;
    final long expected =
        0B0100011011100011011111001110010000101010000110000011100010101110L;
    final long actual = LongBit.reverse(x);
    assertEquals(expected, actual);
  }

  @Test
  public void testReverse_3() {
    // 0B0000000000000000000000000000000001111101100001000110111011001110L
    final long x = 2105831118L;
    final long expected =
        0B0111001101110110001000011011111000000000000000000000000000000000L;
    long actual = LongBit.reverse(x);
    assertEquals(expected, actual);
    actual = calLongReverse(x);
    assertEquals(expected, actual);
  }

  @Test
  public void testCompare() {
    assertTrue(LongBit.compare(0, 0) == 0);
    assertTrue(LongBit.compare(Long.MAX_VALUE, Long.MAX_VALUE) == 0);
    assertTrue(LongBit.compare(Long.MIN_VALUE, Long.MIN_VALUE) == 0);
    assertTrue(LongBit.compare((Long.MAX_VALUE / 2), (Long.MAX_VALUE / 2)) == 0);
    assertTrue(LongBit.compare((Long.MIN_VALUE / 2), (Long.MIN_VALUE / 2)) == 0);
    assertTrue(LongBit.compare((Long.MIN_VALUE / 3), (Long.MIN_VALUE / 3)) == 0);

    assertTrue(LongBit.compare(0, 1) < 0);
    assertTrue(LongBit.compare(0, (Long.MAX_VALUE / 2)) < 0);
    assertTrue(LongBit.compare(0, (Long.MAX_VALUE / 3)) < 0);
    assertTrue(LongBit.compare(0, Long.MAX_VALUE) < 0);

    assertTrue(LongBit.compare((Long.MAX_VALUE / 3), (Long.MAX_VALUE / 2)) < 0);
    assertTrue(LongBit.compare((Long.MAX_VALUE / 4), (Long.MAX_VALUE / 3)) < 0);
    assertTrue(LongBit.compare((Long.MAX_VALUE / 2), Long.MAX_VALUE) < 0);

    assertTrue(LongBit.compare(0, -1) < 0);
    assertTrue(LongBit.compare(0, (Long.MIN_VALUE / 3)) < 0);
    assertTrue(LongBit.compare(0, (Long.MIN_VALUE / 2)) < 0);
    assertTrue(LongBit.compare(0, Long.MIN_VALUE) < 0);

    assertTrue(LongBit.compare(Long.MAX_VALUE, -1) < 0);
    assertTrue(LongBit.compare(Long.MAX_VALUE, (Long.MIN_VALUE / 3)) < 0);
    assertTrue(LongBit.compare(Long.MAX_VALUE, (Long.MIN_VALUE / 2)) < 0);
    assertTrue(LongBit.compare(Long.MAX_VALUE, Long.MIN_VALUE) < 0);

    assertTrue(LongBit.compare(1, 0) > 0);
    assertTrue(LongBit.compare((Long.MAX_VALUE / 3), 0) > 0);
    assertTrue(LongBit.compare((Long.MAX_VALUE / 2), 0) > 0);
    assertTrue(LongBit.compare(Long.MAX_VALUE, 0) > 0);

    assertTrue(LongBit.compare((Long.MAX_VALUE / 2), (Long.MAX_VALUE / 3)) > 0);
    assertTrue(LongBit.compare((Long.MAX_VALUE / 3), (Long.MAX_VALUE / 4)) > 0);
    assertTrue(LongBit.compare(Long.MAX_VALUE, (Long.MAX_VALUE / 2)) > 0);

    assertTrue(LongBit.compare(-1, 0) > 0);
    assertTrue(LongBit.compare((Long.MIN_VALUE / 2), 0) > 0);
    assertTrue(LongBit.compare(Long.MIN_VALUE, 0) > 0);

    assertTrue(LongBit.compare(-1, Long.MAX_VALUE) > 0);
    assertTrue(LongBit.compare((Long.MIN_VALUE / 2), Long.MAX_VALUE) > 0);
    assertTrue(LongBit.compare((Long.MIN_VALUE / 3), Long.MAX_VALUE) > 0);
    assertTrue(LongBit.compare(Long.MIN_VALUE, Long.MAX_VALUE) > 0);
  }
}
