////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import java.util.Random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link IntBit}.
 *
 * @author Haixing Hu
 */
public class IntBitTest {

  private static final int SAMPLES = 10000;

  @Test
  public void testReverse() {
    final Random random = new Random(System.currentTimeMillis());
    for (int i = 0; i < SAMPLES; ++i) {
      final int x = random.nextInt();
      final int expected = calIntReverse(x);
      // System.out.println("Reverse of " + x + " is expected to be " + expected);
      assertEquals(expected, IntBit.reverse(x));
    }
  }

  private int calIntReverse(final int x) {
    long value = ((long) x) & 0xFFFFFFFFL;
    long result = 0;
    for (int i = 0; i < Integer.SIZE; ++i) {
      result <<= 1;
      if ((value & 1L) == 1L) {
        result |= 1;
      }
      value >>>= 1;
    }
    return (int) result;
  }

  @Test
  public void testReverse_1() {
    final int x = -1712133458;
    final int expected = calIntReverse(x);
    final int actual = IntBit.reverse(x);
    assertEquals(expected, actual);
  }

  @Test
  public void testReverse_2() {
    final int x = 0B00000000000100101101011010000111;
    final int expected = calIntReverse(x);
    final int actual = IntBit.reverse(x);
    assertEquals(expected, actual);
    assertEquals(0B11100001011010110100100000000000, actual);
  }

  @Test
  public void testCompare() {
    assertTrue(IntBit.compare(0, 0) == 0);
    assertTrue(IntBit.compare(Integer.MAX_VALUE, Integer.MAX_VALUE) == 0);
    assertTrue(IntBit.compare(Integer.MIN_VALUE, Integer.MIN_VALUE) == 0);
    assertTrue(IntBit.compare((Integer.MAX_VALUE / 2), (Integer.MAX_VALUE / 2)) == 0);
    assertTrue(IntBit.compare((Integer.MIN_VALUE / 2), (Integer.MIN_VALUE / 2)) == 0);
    assertTrue(IntBit.compare((Integer.MIN_VALUE / 3), (Integer.MIN_VALUE / 3)) == 0);

    assertTrue(IntBit.compare(0, 1) < 0);
    assertTrue(IntBit.compare(0, (Integer.MAX_VALUE / 2)) < 0);
    assertTrue(IntBit.compare(0, (Integer.MAX_VALUE / 3)) < 0);
    assertTrue(IntBit.compare(0, Integer.MAX_VALUE) < 0);

    assertTrue(IntBit.compare((Integer.MAX_VALUE / 3), (Integer.MAX_VALUE / 2)) < 0);
    assertTrue(IntBit.compare((Integer.MAX_VALUE / 4), (Integer.MAX_VALUE / 3)) < 0);
    assertTrue(IntBit.compare((Integer.MAX_VALUE / 2), Integer.MAX_VALUE) < 0);

    assertTrue(IntBit.compare(0, -1) < 0);
    assertTrue(IntBit.compare(0, (Integer.MIN_VALUE / 3)) < 0);
    assertTrue(IntBit.compare(0, (Integer.MIN_VALUE / 2)) < 0);
    assertTrue(IntBit.compare(0, Integer.MIN_VALUE) < 0);

    assertTrue(IntBit.compare(Integer.MAX_VALUE, -1) < 0);
    assertTrue(IntBit.compare(Integer.MAX_VALUE, (Integer.MIN_VALUE / 3)) < 0);
    assertTrue(IntBit.compare(Integer.MAX_VALUE, (Integer.MIN_VALUE / 2)) < 0);
    assertTrue(IntBit.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) < 0);

    assertTrue(IntBit.compare(1, 0) > 0);
    assertTrue(IntBit.compare((Integer.MAX_VALUE / 3), 0) > 0);
    assertTrue(IntBit.compare((Integer.MAX_VALUE / 2), 0) > 0);
    assertTrue(IntBit.compare(Integer.MAX_VALUE, 0) > 0);

    assertTrue(IntBit.compare((Integer.MAX_VALUE / 2), (Integer.MAX_VALUE / 3)) > 0);
    assertTrue(IntBit.compare((Integer.MAX_VALUE / 3), (Integer.MAX_VALUE / 4)) > 0);
    assertTrue(IntBit.compare(Integer.MAX_VALUE, (Integer.MAX_VALUE / 2)) > 0);

    assertTrue(IntBit.compare(-1, 0) > 0);
    assertTrue(IntBit.compare((Integer.MIN_VALUE / 2), 0) > 0);
    assertTrue(IntBit.compare(Integer.MIN_VALUE, 0) > 0);

    assertTrue(IntBit.compare(-1, Integer.MAX_VALUE) > 0);
    assertTrue(IntBit.compare((Integer.MIN_VALUE / 2), Integer.MAX_VALUE) > 0);
    assertTrue(IntBit.compare((Integer.MIN_VALUE / 3), Integer.MAX_VALUE) > 0);
    assertTrue(IntBit.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) > 0);
  }
}
