////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link ByteBit}.
 *
 * @author Haixing Hu
 */
public class ByteBitTest {

  @Test
  public void testReverse() {
    for (int x = 0; x <= Byte.MAX_VALUE; ++x) {
      final byte value = (byte) (x & 0xFF);
      final byte expected = calByteReverse(value);
      // System.out.println("Reverse of " + value + " is expected to be " + expected);
      assertEquals(expected, ByteBit.reverse(value));
    }
    for (int x = 0; x >= Byte.MIN_VALUE; --x) {
      final byte value = (byte) (x & 0xFF);
      final byte expected = calByteReverse(value);
      // System.out.println("Reverse of " + value + " is expected to be " + expected);
      assertEquals(expected, ByteBit.reverse(value));
    }
  }

  private byte calByteReverse(final byte x) {
    int value = ((int) x) & 0xFF;
    int result = 0;
    for (int i = 0; i < Byte.SIZE; ++i) {
      result <<= 1;
      if ((value & 1) == 1) {
        result |= 1;
      }
      value >>>= 1;
    }
    return (byte) result;
  }

  @Test
  public void testCalByteReverse() {
    assertEquals((byte) 0B11111111, calByteReverse((byte) 0B11111111));
    assertEquals((byte) 0B00000000, calByteReverse((byte) 0B00000000));
    assertEquals((byte) 0B10000000, calByteReverse((byte) 0B00000001));
    assertEquals((byte) 0B01000000, calByteReverse((byte) 0B00000010));
    assertEquals((byte) 0B11000000, calByteReverse((byte) 0B00000011));
    assertEquals((byte) 0B00100000, calByteReverse((byte) 0B00000100));
    assertEquals((byte) 0B10100000, calByteReverse((byte) 0B00000101));
    assertEquals((byte) 0B01100000, calByteReverse((byte) 0B00000110));
    assertEquals((byte) 0B11100000, calByteReverse((byte) 0B00000111));
  }

  @Test
  public void testCompare() {
    assertTrue(ByteBit.compare((byte) 0, (byte) 0) == 0);
    assertTrue(ByteBit.compare(Byte.MAX_VALUE, Byte.MAX_VALUE) == 0);
    assertTrue(ByteBit.compare(Byte.MIN_VALUE, Byte.MIN_VALUE) == 0);
    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 2), (byte) (Byte.MAX_VALUE / 2)) == 0);
    assertTrue(ByteBit.compare((byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MIN_VALUE / 2)) == 0);
    assertTrue(ByteBit.compare((byte) (Byte.MIN_VALUE / 3), (byte) (Byte.MIN_VALUE / 3)) == 0);

    assertTrue(ByteBit.compare((byte) 0, (byte) 1) < 0);
    assertTrue(ByteBit.compare((byte) 0, (byte) (Byte.MAX_VALUE / 2)) < 0);
    assertTrue(ByteBit.compare((byte) 0, (byte) (Byte.MAX_VALUE / 3)) < 0);
    assertTrue(ByteBit.compare((byte) 0, Byte.MAX_VALUE) < 0);

    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 3), (byte) (Byte.MAX_VALUE / 2)) < 0);
    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 4), (byte) (Byte.MAX_VALUE / 3)) < 0);
    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE) < 0);

    assertTrue(ByteBit.compare((byte) 0, (byte) -1) < 0);
    assertTrue(ByteBit.compare((byte) 0, (byte) (Byte.MIN_VALUE / 3)) < 0);
    assertTrue(ByteBit.compare((byte) 0, (byte) (Byte.MIN_VALUE / 2)) < 0);
    assertTrue(ByteBit.compare((byte) 0, Byte.MIN_VALUE) < 0);

    assertTrue(ByteBit.compare(Byte.MAX_VALUE, (byte) -1) < 0);
    assertTrue(ByteBit.compare(Byte.MAX_VALUE, (byte) (Byte.MIN_VALUE / 3)) < 0);
    assertTrue(ByteBit.compare(Byte.MAX_VALUE, (byte) (Byte.MIN_VALUE / 2)) < 0);
    assertTrue(ByteBit.compare(Byte.MAX_VALUE, Byte.MIN_VALUE) < 0);

    assertTrue(ByteBit.compare((byte) 1, (byte) 0) > 0);
    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 3), (byte) 0) > 0);
    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 2), (byte) 0) > 0);
    assertTrue(ByteBit.compare(Byte.MAX_VALUE, (byte) 0) > 0);

    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 2), (byte) (Byte.MAX_VALUE / 3)) > 0);
    assertTrue(ByteBit.compare((byte) (Byte.MAX_VALUE / 3), (byte) (Byte.MAX_VALUE / 4)) > 0);
    assertTrue(ByteBit.compare(Byte.MAX_VALUE, (byte) (Byte.MAX_VALUE / 2)) > 0);

    assertTrue(ByteBit.compare((byte) -1, (byte) 0) > 0);
    assertTrue(ByteBit.compare((byte) (Byte.MIN_VALUE / 2), (byte) 0) > 0);
    assertTrue(ByteBit.compare(Byte.MIN_VALUE, (byte) 0) > 0);

    assertTrue(ByteBit.compare((byte) -1, Byte.MAX_VALUE) > 0);
    assertTrue(ByteBit.compare((byte) (Byte.MIN_VALUE / 2), Byte.MAX_VALUE) > 0);
    assertTrue(ByteBit.compare((byte) (Byte.MIN_VALUE / 3), Byte.MAX_VALUE) > 0);
    assertTrue(ByteBit.compare(Byte.MIN_VALUE, Byte.MAX_VALUE) > 0);
  }
}