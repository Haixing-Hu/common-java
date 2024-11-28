////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Unit test of the {@link ShortBit}.
 *
 * @author Haixing Hu
 */
public class ShortBitTest {

  private static final int SAMPLES = 10000;

  @Test
  public void testReverse() {
    final Random random = new Random(System.currentTimeMillis());
    for (int i = 0; i < SAMPLES; ++i) {
      final int x = random.nextInt();
      final short value = (short) (x & 0xFFFF);
      final short expected = calShortReverse(value);
      // System.out.println("Reverse of " + value + " is expected to be " + expected);
      assertEquals(expected, ShortBit.reverse(value));
    }
  }

  private short calShortReverse(final short x) {
    int value = ((int) x) & 0xFFFF;
    int result = 0;
    for (int i = 0; i < Short.SIZE; ++i) {
      result <<= 1;
      if ((value & 1) == 1) {
        result |= 1;
      }
      value >>>= 1;
    }
    return (short) result;
  }

  @Test
  public void testCalShortReverse() {
    assertEquals((short) 0B1111111111111111,
        calShortReverse((short) 0B1111111111111111));
    assertEquals((short) 0B0000000000000000,
        calShortReverse((short) 0B0000000000000000));
    assertEquals((short) 0B1000000000000000,
        calShortReverse((short) 0B0000000000000001));
    assertEquals((short) 0B0100000000000000,
        calShortReverse((short) 0B0000000000000010));
    assertEquals((short) 0B1100000000000000,
        calShortReverse((short) 0B0000000000000011));
    assertEquals((short) 0B0010000000000000,
        calShortReverse((short) 0B0000000000000100));
    assertEquals((short) 0B1010000000000000,
        calShortReverse((short) 0B0000000000000101));
    assertEquals((short) 0B0110000000000000,
        calShortReverse((short) 0B0000000000000110));
    assertEquals((short) 0B1110000000000000,
        calShortReverse((short) 0B0000000000000111));
    assertEquals((short) 0B0100000011111111,
        calShortReverse((short) 0B1111111100000010));
  }

  @Test
  public void testCompare() {
    assertTrue(ShortBit.compare((short) 0, (short) 0) == 0);
    assertTrue(ShortBit.compare(Short.MAX_VALUE, Short.MAX_VALUE) == 0);
    assertTrue(ShortBit.compare(Short.MIN_VALUE, Short.MIN_VALUE) == 0);
    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 2), (short) (Short.MAX_VALUE / 2)) == 0);
    assertTrue(ShortBit.compare((short) (Short.MIN_VALUE / 2), (short) (Short.MIN_VALUE / 2)) == 0);
    assertTrue(ShortBit.compare((short) (Short.MIN_VALUE / 3), (short) (Short.MIN_VALUE / 3)) == 0);

    assertTrue(ShortBit.compare((short) 0, (short) 1) < 0);
    assertTrue(ShortBit.compare((short) 0, (short) (Short.MAX_VALUE / 2)) < 0);
    assertTrue(ShortBit.compare((short) 0, (short) (Short.MAX_VALUE / 3)) < 0);
    assertTrue(ShortBit.compare((short) 0, Short.MAX_VALUE) < 0);

    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 3), (short) (Short.MAX_VALUE / 2)) < 0);
    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 4), (short) (Short.MAX_VALUE / 3)) < 0);
    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 2), Short.MAX_VALUE) < 0);

    assertTrue(ShortBit.compare((short) 0, (short) -1) < 0);
    assertTrue(ShortBit.compare((short) 0, (short) (Short.MIN_VALUE / 3)) < 0);
    assertTrue(ShortBit.compare((short) 0, (short) (Short.MIN_VALUE / 2)) < 0);
    assertTrue(ShortBit.compare((short) 0, Short.MIN_VALUE) < 0);

    assertTrue(ShortBit.compare(Short.MAX_VALUE, (short) -1) < 0);
    assertTrue(ShortBit.compare(Short.MAX_VALUE, (short) (Short.MIN_VALUE / 3)) < 0);
    assertTrue(ShortBit.compare(Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)) < 0);
    assertTrue(ShortBit.compare(Short.MAX_VALUE, Short.MIN_VALUE) < 0);

    assertTrue(ShortBit.compare((short) 1, (short) 0) > 0);
    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 3), (short) 0) > 0);
    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 2), (short) 0) > 0);
    assertTrue(ShortBit.compare(Short.MAX_VALUE, (short) 0) > 0);

    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 2), (short) (Short.MAX_VALUE / 3)) > 0);
    assertTrue(ShortBit.compare((short) (Short.MAX_VALUE / 3), (short) (Short.MAX_VALUE / 4)) > 0);
    assertTrue(ShortBit.compare(Short.MAX_VALUE, (short) (Short.MAX_VALUE / 2)) > 0);

    assertTrue(ShortBit.compare((short) -1, (short) 0) > 0);
    assertTrue(ShortBit.compare((short) (Short.MIN_VALUE / 2), (short) 0) > 0);
    assertTrue(ShortBit.compare(Short.MIN_VALUE, (short) 0) > 0);

    assertTrue(ShortBit.compare((short) -1, Short.MAX_VALUE) > 0);
    assertTrue(ShortBit.compare((short) (Short.MIN_VALUE / 2), Short.MAX_VALUE) > 0);
    assertTrue(ShortBit.compare((short) (Short.MIN_VALUE / 3), Short.MAX_VALUE) > 0);
    assertTrue(ShortBit.compare(Short.MIN_VALUE, Short.MAX_VALUE) > 0);
  }
}
