////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharArrayWrapperTest {

  private char[] createCharArray(final int n) {
    final char[] array = new char[n];
    for (int i = 0; i < n; ++i) {
      array[i] = (char)('0' + i);
    }
    return array;
  }

  @Test
  public void testConstructor() {
    final char[] array = createCharArray(10);

    final CharArrayWrapper w1 = new CharArrayWrapper(array, 0, 10);
    assertEquals(10, w1.length());
    for (int i = 0; i < 10; ++i) {
      assertEquals((char)('0' + i), w1.charAt(i));
    }

    final CharArrayWrapper w2 = new CharArrayWrapper(array, 3, 8);
    assertEquals(5, w2.length());
    for (int i = 0; i < 5; ++i) {
      assertEquals((char)('0' + i + 3), w2.charAt(i));
    }

    final CharArrayWrapper w3 = new CharArrayWrapper(array, 3, 3);
    assertEquals(0, w3.length());

    final CharArrayWrapper w4 = new CharArrayWrapper(array, 3, 4);
    assertEquals(1, w4.length());
    assertEquals((char)('0' + 3), w4.charAt(0));

    assertThrows(NullPointerException.class, () -> new CharArrayWrapper(null, 0, 1));
    assertThrows(IllegalArgumentException.class, () -> new CharArrayWrapper(array, -1, 1));
    assertThrows(IllegalArgumentException.class, () -> new CharArrayWrapper(array, 0, 11));
    assertThrows(IllegalArgumentException.class, () -> new CharArrayWrapper(array, 3, 2));
  }

  @Test
  public void testCharAt() {
    final char[] array = createCharArray(10);
    final CharArrayWrapper w2 = new CharArrayWrapper(array, 3, 8);
    assertEquals(5, w2.length());
    for (int i = 0; i < 5; ++i) {
      assertEquals((char)('0' + i + 3), w2.charAt(i));
    }
  }

  @Test
  public void testSubSequence() {
    final char[] array = createCharArray(10);
    final CharArrayWrapper w2 = new CharArrayWrapper(array, 3, 8);
    assertEquals(5, w2.length());
    for (int i = 0; i < 5; ++i) {
      assertEquals((char)('0' + i + 3), w2.charAt(i));
    }
    final CharSequence sub = w2.subSequence(2, 4);
    assertInstanceOf(CharArrayWrapper.class, sub);
    assertEquals(2, sub.length());
    for (int i = 0; i < sub.length(); ++i) {
      assertEquals((char)('0' + i + 3 + 2), sub.charAt(i));
    }
  }

  @Test
  public void testToString() {
    final char[] array1 = createCharArray(10);
    final char[] array2 = createCharArray(10);
    final CharArrayWrapper w1 = new CharArrayWrapper(array1, 0, 3);
    assertEquals("012", w1.toString());
    final CharArrayWrapper w2 = new CharArrayWrapper(array2, 2, 7);
    assertEquals("23456", w2.toString());
  }

  @Test
  public void testEquals() {
    final char[] array1 = createCharArray(10);
    final char[] array2 = createCharArray(10);
    final CharArrayWrapper w1 = new CharArrayWrapper(array1, 0, 7);
    final CharArrayWrapper w2 = new CharArrayWrapper(array2, 0, 7);
    final CharArrayWrapper w3 = new CharArrayWrapper(array1, 0, 6);
    assertTrue(w1.equals(w2));
    assertFalse(w1.equals(w3));
  }


  @Test
  public void testCompare() {
    final char[] array1 = createCharArray(10);
    final char[] array2 = createCharArray(10);
    final CharArrayWrapper w1 = new CharArrayWrapper(array1, 0, 7);
    final CharArrayWrapper w2 = new CharArrayWrapper(array2, 0, 7);
    final CharArrayWrapper w3 = new CharArrayWrapper(array1, 0, 6);
    assertEquals(0, w1.compareTo(w2));
    assertEquals(1, w1.compareTo(w3));
    assertEquals(-1, w3.compareTo(w1));
  }
}
