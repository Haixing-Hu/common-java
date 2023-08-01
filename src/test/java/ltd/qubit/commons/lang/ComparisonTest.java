////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.IntStream;

import ltd.qubit.commons.datastructure.list.primitive.impl.BooleanArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ByteArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.CharArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.DoubleArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.FloatArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.LongArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ShortArrayList;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.lang.Comparison.compare;
import static ltd.qubit.commons.lang.Comparison.compareIgnoreCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparisonTest {

  public enum TestEnum {
    a,
    b,
    c,
    d,
    e
  }

  class TestClassA {

  }

  class TestClassB {

  }

  class TestClassZ {

  }

  @Test
  public void testBoolean() {
    assertEquals(0, compare(true, true));
    assertEquals(0, compare(false, false));
    assertEquals(1, compare(true, false));
    assertEquals(-1, compare(false, true));
  }

  @Test
  public void testBooleanArray() {
    final boolean[] array1 = {true, true, false};
    boolean[] array2 = {true, true, false};
    boolean[] array3 = {true, true, false, true};
    final boolean[] array4 = {true, false, false, true};
    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(-1, compare(array4, array3));
    assertEquals(1, compare(array3, array4));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array3 = null;
    assertEquals(0, compare(array2, array3));
  }

  @Test
  public void testBooleanArrayInt() {
    final boolean[] array1 = {true, true, false};
    final boolean[] array2 = {true, true, false};
    assertEquals(0, compare(array1, 1, array2, 1));
    assertEquals(0, compare(array1, 3, array2, 3));
    assertEquals(-1, compare(array1, 1, array2, 3));
    assertEquals(1, compare(array1, 3, array2, 1));
    assertEquals(-1, compare(array1, 2, array2, 3));
    assertEquals(1, compare(array1, 3, array2, 2));
    final boolean[] array3 = {true, true, false, true};
    assertEquals(-1, compare(array1, array3));
  }

  @Test
  public void testBooleanObject() {
    Boolean value1 = true;
    Boolean value2 = true;

    assertEquals(0, compare(value1, value2));
    value2 = false;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = false;
    assertEquals(0, compare(value1, value2));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
  }

  @Test
  public void testBooleanObjectArray() {
    Boolean[] array1 = {true, true, false, true};
    Boolean[] array2 = {true, true, false, true};
    final Boolean[] array3 = {true, true, null};
    final Boolean[] array4 = {true, false, null};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array3, array4));
    assertEquals(-1, compare(array4, array3));
    final Boolean[] array5 = {true, true};
    assertEquals(1, compare(array1, array5));
    assertEquals(-1, compare(array5, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBooleanObjectArrayInt() {
    final Boolean[] array1 = {true, true, false, true};
    final Boolean[] array2 = {true, true, false, true};
    final Boolean[] array3 = {true, true, null};

    assertEquals(1, compare(array1, 4, array2, 3));
    assertEquals(1, compare(array1, 4, array2, 1));
    assertEquals(-1, compare(array2, 3, array1, 4));
    assertEquals(-1, compare(array2, 1, array1, 4));
    assertEquals(1, compare(array1, 3, array3, 3));
    assertEquals(-1, compare(array3, 3, array1, 3));
    final Boolean[] array4 = {true, false, null};
    assertEquals(1, compare(array3, 2, array4, 2));
    assertEquals(-1, compare(array4, 2, array3, 2));
    assertEquals(1, compare(array3, 3, array4, 3));
    assertEquals(-1, compare(array4, 3, array3, 3));
  }

  @Test
  public void testChar() {
    assertEquals(1, compare('a', 'A'));
    assertEquals(-1, compare('A', 'a'));
    assertEquals(-1, compare('a', 'f'));
    assertEquals(1, compare('a', ' '));
    assertEquals(1, compare('a', '\0'));
  }

  @Test
  public void testCharArray() {
    char[] array1 = {'a', 'b', 'c'};
    char[] array2 = {'a', 'b', 'c'};
    assertEquals(0, compare(array1, array2));
    array2[2] = 'C';
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array2[2] = 'd';
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIgnoreCaseCharArray() {
    char[] array1 = {'a', 'b', 'c'};
    char[] array2 = {'a', 'b', 'c'};
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'C';
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'd';
    assertEquals(-1, compareIgnoreCase(array1, array2));
    assertEquals(1, compareIgnoreCase(array2, array1));
    array2 = null;
    assertEquals(1, compareIgnoreCase(array1, array2));
    assertEquals(-1, compareIgnoreCase(array2, array1));
    array1 = null;
    assertEquals(0, compareIgnoreCase(array1, array2));
  }

  @Test
  public void testCharArrayInt() {
    final char[] array1 = {'a', 'b', 'c'};
    final char[] array2 = {'a', 'b', 'c'};
    final char[] array3 = {'a', 'b', 'c', 'd'};

    assertEquals(0, compare(array1, 3, array2, 3));
    assertEquals(0, compare(array1, 3, array3, 3));
    assertEquals(-1, compare(array1, 3, array3, 4));
    assertEquals(1, compare(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(1, compare(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-1, compare(array1, 3, array2, 3));
  }

  @Test
  public void testIgnoreCaseCharArrayInt() {
    final char[] array1 = {'a', 'b', 'c'};
    final char[] array2 = {'a', 'b', 'c'};
    final char[] array3 = {'a', 'b', 'c', 'd'};

    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    assertEquals(0, compareIgnoreCase(array1, 3, array3, 3));
    assertEquals(-1, compareIgnoreCase(array1, 3, array3, 4));
    assertEquals(1, compareIgnoreCase(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-1, compareIgnoreCase(array1, 3, array2, 3));
  }

  @Test
  public void testCharacter() {
    Character value1 = 'a';
    Character value2 = 'a';

    assertEquals(0, compare(value1, value2));
    value2 = 'A';
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = ' ';
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testCharacterArray() {
    Character[] array1 = {'a', 'b', 'c'};
    Character[] array2 = {'a', 'b', 'c'};
    assertEquals(0, compare(array1, array2));
    array2[2] = 'C';
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array2[2] = 'd';
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIgnoreCaseCharacterArray() {
    Character[] array1 = {'a', 'b', 'c'};
    Character[] array2 = {'a', 'b', 'c'};
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'C';
    assertEquals(0, compareIgnoreCase(array1, array2));
    array2[2] = 'd';
    assertEquals(-1, compareIgnoreCase(array1, array2));
    assertEquals(1, compareIgnoreCase(array2, array1));
    array2 = null;
    assertEquals(1, compareIgnoreCase(array1, array2));
    assertEquals(-1, compareIgnoreCase(array2, array1));
    array1 = null;
    assertEquals(0, compareIgnoreCase(array1, array2));
  }

  @Test
  public void testCharacterArrayInt() {
    final Character[] array1 = {'a', 'b', 'c'};
    final Character[] array2 = {'a', 'b', 'c'};
    final Character[] array3 = {'a', 'b', 'c', 'd'};

    assertEquals(0, compare(array1, 3, array2, 3));
    assertEquals(0, compare(array1, 3, array3, 3));
    assertEquals(-1, compare(array1, 3, array3, 4));
    assertEquals(1, compare(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(1, compare(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-1, compare(array1, 3, array2, 3));
  }

  @Test
  public void testIgnoreCaseCharacterArrayInt() {
    final Character[] array1 = {'a', 'b', 'c'};
    final Character[] array2 = {'a', 'b', 'c'};
    final Character[] array3 = {'a', 'b', 'c', 'd'};

    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    assertEquals(0, compareIgnoreCase(array1, 3, array3, 3));
    assertEquals(-1, compareIgnoreCase(array1, 3, array3, 4));
    assertEquals(1, compareIgnoreCase(array3, 4, array1, 3));
    array2[1] = 'B';
    assertEquals(0, compareIgnoreCase(array1, 3, array2, 3));
    array2[1] = 'd';
    assertEquals(-1, compareIgnoreCase(array1, 3, array2, 3));
  }

  @Test
  public void testByte() {
    final byte[] array = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(Integer.compare(array[i], array[j]),
            compare(array[i], array[j]));
      }
    }
  }

  @Test
  public void testByteArray() {
    byte[] array1 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    byte[] array2 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    final byte[] array3 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (byte) 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    final byte[] array4 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE};
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testByteArrayInt() {
    final byte[] array1 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    final byte[] array2 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    final byte[] array3 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testByteObject() {
    final Byte[] array = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(Integer.compare(array[i], array[j]),
            compare(array[i], array[j]));
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testByteObjectArray() {
    Byte[] array1 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    Byte[] array2 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    final Byte[] array3 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1};
    final Byte[] array4 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (byte) 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testByteObjectArrayInt() {
    final Byte[] array1 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    final Byte[] array2 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE};
    final Byte[] array3 = {(byte) 0, (byte) -1, (byte) 1,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MIN_VALUE, Byte.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testShort() {
    final short[] array = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(Integer.compare(array[i], array[j]), compare(array[i], array[j]));
      }
    }
  }

  @Test
  public void testShortArray() {
    short[] array1 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    short[] array2 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    final short[] array3 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1};
    final short[] array4 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (byte) 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testShortArrayInt() {
    final short[] array1 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    final short[] array2 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    final short[] array3 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testShortObject() {
    final Short[] array = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(Integer.compare(array[i], array[j]),
            compare(array[i], array[j]));
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testShortObjectArray() {
    Short[] array1 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    Short[] array2 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    final Short[] array3 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1};
    final Short[] array4 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = (short) 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testShortObjectArrayInt() {
    final Short[] array1 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    final Short[] array2 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE};
    final Short[] array3 = {(short) 0, (short) -1, (short) 1,
        (short) (Short.MIN_VALUE / 2), (short) (Short.MAX_VALUE / 2),
        Short.MIN_VALUE, Short.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testInt() {
    final int[] array = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        assertEquals(Integer.compare(array[i], array[j]), compare(array[i], array[j]));
      }
    }
  }

  @Test
  public void testIntArray() {
    int[] array1 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE};
    int[] array2 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE};
    final int[] array3 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE - 1};
    final int[] array4 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIntArrayInt() {
    final int[] array1 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE};
    final int[] array2 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE};
    final int[] array3 = {0, -1, 1, (Integer.MIN_VALUE / 2),
        (Integer.MAX_VALUE / 2), Integer.MIN_VALUE, Integer.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testInteger() {
    final Integer[] array = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE};

    for (final Integer x : array) {
      IntStream.range(0, array.length)
               .forEach(j -> assertEquals(Integer.compare(x, array[j]), compare(x, array[j])));
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testIntegerArray() {
    Integer[] array1 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE};
    Integer[] array2 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE};
    final Integer[] array3 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE - 1};
    final Integer[] array4 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testIntegerArrayInt() {
    final Integer[] array1 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE};
    final Integer[] array2 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE};
    final Integer[] array3 = {0, -1, 1,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MIN_VALUE, Integer.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testLong() {
    final long[] array = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        if (array[i] != array[j]) {
          assertEquals((array[i] > array[j] ? 1 : -1),
              compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }
  }

  @Test
  public void testLongArray() {
    long[] array1 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    long[] array2 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    final long[] array3 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1};
    final long[] array4 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = 0;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testLongArrayInt() {
    final long[] array1 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    final long[] array2 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    final long[] array3 = {0, -1, 1,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testLongObject() {
    final Long[] array = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        if (array[i] > array[j]) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j]) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }

      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testLongObjectArray() {
    Long[] array1 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    Long[] array2 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    final Long[] array3 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1};
    final Long[] array4 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    array3[array3.length - 1] = 0L;
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testLongObjectArrayInt() {
    final Long[] array1 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    final Long[] array2 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE};
    final Long[] array3 = {0L, -1L, 1L,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MIN_VALUE, Long.MAX_VALUE - 1};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 2, array3, array3.length - 2));
  }

  @Test
  public void testFloat() {
    final float[] array = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        final int inti = Float.floatToIntBits(array[i]);
        final int intj = Float.floatToIntBits(array[j]);
        if (array[i] > array[j] || inti > intj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || inti < intj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }
  }

  @Test
  public void testFloatArray() {
    float[] array1 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    float[] array2 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array3 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        0.0f};
    final float[] array4 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testFloatArrayInt() {
    final float[] array1 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array2 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array3 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        0.0f};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testFloatObject() {
    final Float[] array = {0.0f - 1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        final int inti = Float.floatToIntBits(array[i]);
        final int intj = Float.floatToIntBits(array[j]);
        if (array[i] > array[j] || inti > intj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || inti < intj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testFloatObjectArray() {
    Float[] array1 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    Float[] array2 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array3 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        0.0f};
    final Float[] array4 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testFloatObjectArrayInt() {
    final Float[] array1 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array2 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array3 = {0.0f, -1.0f, 1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        0.0f};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 3, array3, array3.length - 2));
  }

  @Test
  public void testFloatWithEpsilon() {
    final float epsilon = 0.0001f;
    float value1 = 0.0f;
    float value2 = 0.0f + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Float.MAX_VALUE;
    value2 = Float.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testFloatArrayWithEpsilon() {
    final float epsilon = 0.0001f;
    float[] array1 = {0.0f, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    float[] array2 = {0.0f + epsilon, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array3 = {0.0f + epsilon * 2, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testFloatArrayIntWithEpsilon() {
    final float epsilon = 0.001f;
    final float[] array1 = {0.0f, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array2 = {0.0f + epsilon, 1.0f + epsilon * 2,
        -1.0f, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MIN_VALUE, Float.MAX_VALUE};
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testFloatObjectWithEpsilon() {
    final float epsilon = 0.0001f;
    Float value1 = 0.0f;
    Float value2 = 0.0f + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Float.MAX_VALUE;
    value2 = Float.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
    value2 = null;
    assertEquals(1, compare(value1, value2, epsilon));
    assertEquals(-1, compare(value2, value1, epsilon));
    value1 = null;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testFloatObjectArrayWithEpsilon() {
    final float epsilon = 0.0001f;
    Float[] array1 = {0.0f, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    Float[] array2 = {0.0f + epsilon, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array3 = {0.0f + epsilon * 2, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testFloatObjectArrayIntWithEpsilon() {
    final float epsilon = 0.001f;
    final Float[] array1 = {0.0f, 1.0f, -1.0f,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array2 = {0.0f + epsilon, 1.0f + epsilon * 2,
        -1.0f, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MIN_VALUE, Float.MAX_VALUE};
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testDouble() {
    final double[] array = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        final long longi = Double.doubleToLongBits(array[i]);
        final long longj = Double.doubleToLongBits(array[j]);
        if (array[i] > array[j] || longi > longj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || longi < longj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }
  }

  @Test
  public void testDoubleArray() {
    double[] array1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    double[] array2 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final double[] array3 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        0.0};
    final double[] array4 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testDoubleArrayInt() {
    final double[] array1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final double[] array2 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final double[] array3 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        0.0};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    assertEquals(-1,
        compare(array3, array3.length, array1, array1.length));
  }

  @Test
  public void testDoubleObject() {
    final Double[] array = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};

    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length; ++j) {
        final long longi = Double.doubleToLongBits(array[i]);
        final long longj = Double.doubleToLongBits(array[j]);
        if (array[i] > array[j] || longi > longj) {
          assertEquals(1, compare(array[i], array[j]));
        } else if (array[i] < array[j] || longi < longj) {
          assertEquals(-1, compare(array[i], array[j]));
        } else {
          assertEquals(0, compare(array[i], array[j]));
        }
      }
    }

    array[0] = null;
    assertEquals(1, compare(array[1], array[0]));
    assertEquals(-1, compare(array[0], array[1]));
    array[1] = null;
    assertEquals(0, compare(array[1], array[0]));
  }

  @Test
  public void testDoubleObjectArray() {
    Double[] array1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    Double[] array2 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array3 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        0.0};
    final Double[] array4 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE};

    assertEquals(0, compare(array1, array2));
    assertEquals(1, compare(array1, array3));
    assertEquals(-1, compare(array3, array1));
    assertEquals(1, compare(array1, array4));
    assertEquals(-1, compare(array4, array1));
    array2[0] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[0] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testDoubleObjectArrayInt() {
    final Double[] array1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array2 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array3 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        0.0};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 2));
    assertEquals(-1, compare(array2, array2.length - 2, array1, array1.length));
    assertEquals(1,
        compare(array1, array1.length, array3, array3.length));
    array3[1] = null;
    assertEquals(1,
        compare(array1, array1.length - 2, array3, array3.length - 2));
    array1[1] = null;
    assertEquals(0,
        compare(array1, array1.length - 3, array3, array3.length - 2));
  }

  @Test
  public void testDoubleWithEpsilon() {
    final double epsilon = 0.0001f;
    double value1 = 0.0f;
    double value2 = 0.0f + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Double.MAX_VALUE;
    value2 = Double.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testDoubleArrayWithEpsilon() {
    final double epsilon = 0.0001f;
    double[] array1 = {0.0, 1.0, -1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    double[] array2 = {0.0 + epsilon, 1.0, -1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final double[] array3 = {0.0 + epsilon * 2, 1.0,
        -1.0, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE};

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testDoubleArrayIntWithEpsilon() {
    final double epsilon = 0.001f;
    final double[] array1 = {0.0, 1.0, -1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final double[] array2 = {0.0 + epsilon, 1.0 + epsilon * 2,
        -1.0, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE};
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testDoubleObjectWithEpsilon() {
    final double epsilon = 0.0001;
    Double value1 = 0.0;
    Double value2 = 0.0 + epsilon;

    assertEquals(0, compare(value1, value2, epsilon));
    value1 = -epsilon;
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value1 = Double.MAX_VALUE;
    value2 = Double.MAX_VALUE + epsilon;
    assertEquals(0, compare(value1, value2, epsilon));
    value2 = null;
    assertEquals(1, compare(value1, value2, epsilon));
    assertEquals(-1, compare(value2, value1, epsilon));
    value1 = null;
    assertEquals(0, compare(value1, value2, epsilon));
  }

  @Test
  public void testDoubleObjectArrayWithEpsilon() {
    final double epsilon = 0.0001f;
    Double[] array1 = {0.0, 1.0, -1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    Double[] array2 = {0.0 + epsilon, 1.0, -1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array3 = {0.0 + epsilon * 2, 1.0,
        -1.0, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE};

    assertEquals(0, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array1, array3, epsilon));
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));
  }

  @Test
  public void testDoubleObjectArrayIntWithEpsilon() {
    final double epsilon = 0.001f;
    final Double[] array1 = {0.0, 1.0, -1.0,
        (Double.MIN_VALUE / 2), (Double.MAX_VALUE / 2),
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array2 = {0.0 + epsilon, 1.0 + epsilon * 2,
        -1.0, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MIN_VALUE, Double.MAX_VALUE};
    assertEquals(0, compare(array1, 1, array2, 1, epsilon));
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length, epsilon));
  }

  @Test
  public void testEnum() {
    final TestEnum[] array1 = {null, TestEnum.a, TestEnum.b, TestEnum.c,
        TestEnum.d, TestEnum.e};
    final TestEnum[] array2 = {null, TestEnum.a, TestEnum.b, TestEnum.c,
        TestEnum.d, TestEnum.e};

    int out;

    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array2.length; ++j) {
        if (array1[i] == array2[j]) {
          out = 0;
        } else if (array1[i] == null) {
          out = -1;
        } else if (array2[j] == null) {
          out = +1;
        } else {
          out = array1[i].ordinal() - array2[j].ordinal();
        }
        assertEquals(out, compare(array1[i], array2[j]));
      }
    }
  }

  @Test
  public void testEnumArray() {
    TestEnum[] array1 = {TestEnum.a, TestEnum.b, TestEnum.c};
    TestEnum[] array2 = {TestEnum.a, TestEnum.b, TestEnum.c};

    assertEquals(0, compare(array1, array2));
    array2[2] = TestEnum.e;
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testEnumArrayInt() {
    final TestEnum[] array1 = {TestEnum.a, TestEnum.b, TestEnum.c};
    final TestEnum[] array2 = {TestEnum.a, TestEnum.b, TestEnum.c};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 1));
    assertEquals(-1, compare(array2, array2.length - 1, array1, array1.length));
    assertEquals(0,
        compare(array1, array1.length - 1, array2, array2.length - 1));
    array2[1] = TestEnum.e;
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length - 1));
    assertEquals(1,
        compare(array2, array2.length - 1, array1, array1.length));
    array2[1] = null;
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 1));
    assertEquals(-1, compare(array2, array2.length - 1, array1, array1.length));
  }

  @Test
  public void testString() {
    String value1 = "longlongago";
    String value2 = "longlongago";
    assertEquals(0, compare(value1, value2));
    value2 = "helloworld";
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = "longlongag";
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = "longlongagoooo";
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testStringArray() {
    String[] array1 = {"long", "long", "ago"};
    String[] array2 = {"long", "long", "ago"};

    assertEquals(0, compare(array1, array2));
    array2[2] = "agooo";
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));
    array2[2] = "aao";
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array2[2] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testStringArrayInt() {
    final String[] array1 = {"long", "long", "ago"};
    final String[] array2 = {"long", "long", "ago"};

    assertEquals(0,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length - 1));
    array2[2] = "agooo";
    assertEquals(-1,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(1,
        compare(array2, array2.length, array1, array1.length));
    array2[2] = "aao";
    assertEquals(0,
        compare(array1, array1.length - 1, array2, array2.length - 1));
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(-1,
        compare(array2, array2.length, array1, array1.length));
    array2[2] = null;
    assertEquals(1,
        compare(array1, array1.length, array2, array2.length));
    assertEquals(-1,
        compare(array2, array2.length, array1, array1.length));
  }

  @Test
  public void testClassOfQ() {
    final TestClassA value1 = new TestClassA();
    final TestClassB value2 = new TestClassB();
    final TestClassA value3 = new TestClassA();
    final TestClassZ value4 = new TestClassZ();

    assertEquals(0, compare(value1.getClass(), value3.getClass()));
    assertEquals(-1, compare(value1.getClass(), value2.getClass()));
    assertEquals(1, compare(value2.getClass(), value1.getClass()));
    assertEquals(-25, compare(value1.getClass(), value4.getClass()));
    assertEquals(25, compare(value4.getClass(), value1.getClass()));
    assertEquals(-1, compare(null, value3.getClass()));
    assertEquals(1, compare(value3.getClass(), null));
    assertEquals(0, compare((Class<?>) null, null));
  }

  @Test
  public void testClassOfQArray() {
    Class<?>[] array1 = {new TestClassA().getClass(),
        new TestClassA().getClass()};
    Class<?>[] array2 = {new TestClassA().getClass(),
        new TestClassA().getClass()};
    final Class<?>[] array3 = {new TestClassB().getClass(),
        new TestClassB().getClass()};
    final Class<?>[] array4 = {new TestClassZ().getClass(),
        new TestClassZ().getClass()};
    final Class<?>[] array5 = {new TestClassA().getClass(),
        new TestClassA().getClass(), new TestClassA().getClass()};
    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));
    assertEquals(-25, compare(array1, array4));
    assertEquals(25, compare(array4, array1));
    assertEquals(-1, compare(array1, array5));
    assertEquals(1, compare(array5, array1));
    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testClassOfQArrayInt() {
    final Class<?>[] array1 = {new TestClassA().getClass(),
        new TestClassA().getClass()};
    final Class<?>[] array2 = {new TestClassA().getClass(),
        new TestClassA().getClass()};
    final Class<?>[] array3 = {new TestClassB().getClass(),
        new TestClassB().getClass()};
    final Class<?>[] array4 = {new TestClassZ().getClass(),
        new TestClassZ().getClass()};
    final Class<?>[] array5 = {new TestClassA().getClass(),
        new TestClassA().getClass(), new TestClassA().getClass()};
    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array1, 2, array3, 2));
    assertEquals(1, compare(array3, 2, array1, 2));
    assertEquals(-25, compare(array1, 2, array4, 2));
    assertEquals(25, compare(array4, 2, array1, 2));
    assertEquals(-1, compare(array1, 2, array5, 3));
    assertEquals(1, compare(array5, 3, array1, 2));
    assertEquals(0, compare(array1, 2, array5, 2));
    array2[1] = new TestClassZ().getClass();
    assertEquals(0, compare(array1, 1, array2, 1));
    assertEquals(-25, compare(array1, 2, array2, 2));
    assertEquals(25, compare(array2, 2, array1, 2));
    assertEquals(-1, compare(array1, 1, array2, 2));
    assertEquals(1, compare(array2, 2, array1, 1));
    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
  }

  @Test
  public void testDate() {
    Date value1 = null;
    Date value2 = null;

    assertEquals(0, compare(value1, value2));

    final GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 0, 12, 0, 0);
    value2 = cal.getTime();
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    cal.set(2012, 0, 8, 0, 0);
    value1 = cal.getTime();
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    cal.set(2012, 0, 12, 0, 0);
    value1 = cal.getTime();
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testDateArray() {
    Date[] array1 = {new Date(), new Date()};
    Date[] array2 = {new Date(), new Date()};
    final Date[] array3 = {new Date(), new Date(), new Date()};

    final GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 2, 15, 0, 0);
    array1[0] = cal.getTime();
    array2[0] = cal.getTime();
    array3[0] = cal.getTime();
    cal.set(2012, 3, 15, 0, 0);
    array1[1] = cal.getTime();
    array2[1] = cal.getTime();
    array3[1] = cal.getTime();

    assertEquals(0, compare(array1, array2));

    array3[2] = cal.getTime();
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));

    cal.set(2012, 3, 10, 0, 0);
    array2[1] = cal.getTime();
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));

    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));

    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    assertEquals(0, compare(array2, array1));

    array1 = null;
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));

    array2 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testDateArrayInt() {
    Date[] array1 = {new Date(), new Date()};
    Date[] array2 = {new Date(), new Date()};
    final Date[] array3 = {new Date(), new Date(), new Date()};

    final GregorianCalendar cal = new GregorianCalendar();
    cal.setTimeInMillis(0);
    cal.set(2012, 2, 15, 0, 0);
    array1[0] = cal.getTime();
    array2[0] = cal.getTime();
    array3[0] = cal.getTime();
    cal.set(2012, 3, 15, 0, 0);
    array1[1] = cal.getTime();
    array2[1] = cal.getTime();
    array3[1] = cal.getTime();

    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(0, compare(array1, 2, array3, 2));

    array3[2] = cal.getTime();
    assertEquals(-1, compare(array1, 2, array3, 3));
    assertEquals(1, compare(array3, 3, array1, 2));

    cal.set(2012, 3, 10, 0, 0);
    array2[1] = cal.getTime();
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));

    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));

    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(0, compare(array2, 2, array1, 2));
    cal.set(2012, 3, 30, 0, 0);
    array2[0] = cal.getTime();
    assertEquals(-1, compare(array1, 1, array2, 1));
    assertEquals(1, compare(array2, 1, array1, 1));

    array1 = null;
    assertEquals(-1, compare(array1, array2));
    assertEquals(1, compare(array2, array1));

    array2 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBigInteger() {
    BigInteger value1 = new BigInteger("123123123");
    BigInteger value2 = new BigInteger("123123123");

    assertEquals(0, compare(value1, value2));
    value2 = new BigInteger("123123");
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testBigIntegerArray() {
    BigInteger[] array1 = {new BigInteger("123"), new BigInteger("123456")};
    BigInteger[] array2 = {new BigInteger("123"), new BigInteger("123456")};
    final BigInteger[] array3 = {new BigInteger("123"),
        new BigInteger("123456"),
        new BigInteger("123456789")};

    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));
    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBigIntegerArrayInt() {
    final BigInteger[] array1 = {new BigInteger("123"),
        new BigInteger("123456")};
    final BigInteger[] array2 = {new BigInteger("123"),
        new BigInteger("123456")};
    final BigInteger[] array3 = {new BigInteger("123"),
        new BigInteger("123456"),
        new BigInteger("123456789")};

    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array1, 2, array3, 3));
    assertEquals(1, compare(array3, 3, array1, 2));
    assertEquals(0, compare(array1, 2, array3, 2));
    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));
    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
  }

  @Test
  public void testBigDecimal() {
    BigDecimal value1 = new BigDecimal("123123.123");
    BigDecimal value2 = new BigDecimal("123123.123");

    assertEquals(0, compare(value1, value2));
    value2 = new BigDecimal("123.123");
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));
  }

  @Test
  public void testBigDecimalArray() {
    BigDecimal[] array1 = {new BigDecimal("12.3"), new BigDecimal("1234.56")};
    BigDecimal[] array2 = {new BigDecimal("12.3"), new BigDecimal("1234.56")};
    final BigDecimal[] array3 = {new BigDecimal("12.3"),
        new BigDecimal("1234.56"),
        new BigDecimal("123456.789")};

    assertEquals(0, compare(array1, array2));
    assertEquals(-1, compare(array1, array3));
    assertEquals(1, compare(array3, array1));
    array2[1] = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1[1] = null;
    assertEquals(0, compare(array1, array2));
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));
  }

  @Test
  public void testBigDecimalArrayInt() {
    final BigDecimal[] array1 = {new BigDecimal("12.3"),
        new BigDecimal("123.456")};
    final BigDecimal[] array2 = {new BigDecimal("12.3"),
        new BigDecimal("123.456")};
    final BigDecimal[] array3 = {new BigDecimal("12.3"),
        new BigDecimal("123.456"),
        new BigDecimal("123456.789")};

    assertEquals(0, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array1, 2, array3, 3));
    assertEquals(1, compare(array3, 3, array1, 2));
    assertEquals(0, compare(array1, 2, array3, 2));
    array2[1] = null;
    assertEquals(1, compare(array1, 2, array2, 2));
    assertEquals(-1, compare(array2, 2, array1, 2));
    assertEquals(0, compare(array1, 1, array2, 1));
    array1[1] = null;
    assertEquals(0, compare(array1, 2, array2, 2));
  }

  @Test
  public void testObject() {
    Object value1 = new TestClassA();
    Object value2 = new TestClassA();

    assertEquals(0, compare(value1, value1));
    assertTrue(compare(value1, value2) != 0);
    value2 = new TestClassB();
    assertEquals(-1, compare(value1, value2));
    assertEquals(1, compare(value2, value1));
    value2 = new TestClassZ();
    assertEquals(-25, compare(value1, value2));
    assertEquals(25, compare(value2, value1));
    value1 = TestClassA.class;
    value2 = TestClassB.class;
    assertTrue(compare(value1, value2) < 0);
    assertTrue(compare(value2, value1) > 0);
    value2 = null;
    assertEquals(1, compare(value1, value2));
    assertEquals(-1, compare(value2, value1));
    value1 = null;
    assertEquals(0, compare(value1, value2));

    final boolean[] booleanarray1 = {true, false, false, true};
    final boolean[] booleanarray2 = {true, false, false, true};
    final boolean[] booleanarray3 = {false, true, true};
    final boolean[] booleanarray4 = {true, false};
    assertEquals(0, compare(booleanarray1, booleanarray2));
    assertEquals(1, compare(booleanarray2, booleanarray3));
    assertEquals(-1, compare(booleanarray3, booleanarray2));
    assertEquals(1, compare(booleanarray2, booleanarray4));
    assertEquals(-1, compare(booleanarray4, booleanarray2));

    final char[] chararray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE};
    final char[] chararray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE};
    final char[] chararray3 = {(char) 0, (char) 3};
    final char[] chararray4 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE};

    assertEquals(0, compare(chararray1, chararray2));
    assertEquals(1, compare(chararray3, chararray2));
    assertEquals(-1, compare(chararray2, chararray3));
    assertEquals(-1, compare(chararray4, chararray2));
    assertEquals(1, compare(chararray2, chararray4));

    final byte[] bytearray1 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray2 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray3 = {(byte) 0, (byte) 2};
    final byte[] bytearray4 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2)};
    assertEquals(0, compare(bytearray1, bytearray2));
    assertEquals(1, compare(bytearray3, bytearray2));
    assertEquals(-1, compare(bytearray2, bytearray3));
    assertEquals(1, compare(bytearray2, bytearray4));
    assertEquals(-1, compare(bytearray4, bytearray2));

    final short[] shortarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray3 = {(short) 0, (short) 3};
    final short[] shortarray4 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2)};
    assertEquals(0, compare(shortarray1, shortarray2));
    assertEquals(1, compare(shortarray3, shortarray2));
    assertEquals(-1, compare(shortarray2, shortarray3));
    assertEquals(1, compare(shortarray2, shortarray4));
    assertEquals(-1, compare(shortarray4, shortarray2));

    final int[] intarray1 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final int[] intarray2 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final int[] intarray3 = {0, 4};
    final int[] intarray4 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2)};
    assertEquals(0, compare(intarray1, intarray2));
    assertEquals(1, compare(intarray3, intarray2));
    assertEquals(-1, compare(intarray2, intarray3));
    assertEquals(1, compare(intarray2, intarray4));
    assertEquals(-1, compare(intarray4, intarray2));

    final long[] longarray1 = {0, -1, 1, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final long[] longarray2 = {0, -1, 1, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final long[] longarray3 = {0, 2};
    final long[] longarray4 = {0, -1, 1, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2)};
    assertEquals(0, compare(longarray1, longarray2));
    assertEquals(1, compare(longarray3, longarray2));
    assertEquals(-1, compare(longarray2, longarray3));
    assertEquals(-1, compare(longarray4, longarray2));
    assertEquals(1, compare(longarray2, longarray4));

    final float[] floatarray1 = {0, -1, 1, Float.MIN_VALUE,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MAX_VALUE};
    final float[] floatarray2 = {0, -1, 1, Float.MIN_VALUE,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MAX_VALUE};
    final float[] floatarray3 = {0, 2};
    final float[] floatarray4 = {0, -1, 1, Float.MIN_VALUE,
        (Float.MIN_VALUE / 2)};
    assertEquals(0, compare(floatarray1, floatarray2));
    assertEquals(-1, compare(floatarray2, floatarray3));
    assertEquals(1, compare(floatarray3, floatarray2));
    assertEquals(1, compare(floatarray2, floatarray4));
    assertEquals(-1, compare(floatarray4, floatarray2));

    final double[] doublearray1 = {0, -1, 1,
        Double.MIN_VALUE, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final double[] doublearray2 = {0, -1, 1,
        Double.MIN_VALUE, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final double[] doublearray3 = {0, Double.MAX_VALUE};
    final double[] doublearray4 = {0, -1, 1,
        Double.MIN_VALUE, (Double.MIN_VALUE / 2)};
    assertEquals(0, compare(doublearray1, doublearray2));
    assertEquals(1, compare(doublearray3, doublearray2));
    assertEquals(-1, compare(doublearray2, doublearray3));
    assertEquals(-1, compare(doublearray4, doublearray2));
    assertEquals(1, compare(doublearray2, doublearray4));

    final String[] stringarray1 = {"long", "long", "ago"};
    final String[] stringarray2 = {"long", "long", "ago"};
    final String[] stringarray3 = {"long", "time", "no", "see"};
    final String[] stringarray4 = {"long", "long"};
    assertEquals(0, compare(stringarray1, stringarray2));
    assertEquals(-1, compare(stringarray2, stringarray3));
    assertEquals(1, compare(stringarray3, stringarray2));
    assertEquals(1, compare(stringarray2, stringarray4));
    assertEquals(-1, compare(stringarray4, stringarray2));

    final Boolean[] booleanobjectarray1 = {true, false, false, true};
    final Boolean[] booleanobjectarray2 = {true, false, false, true};
    final Boolean[] booleanobjectarray3 = {false, true, true};
    final Boolean[] booleanobjectarray4 = {true, false};
    assertEquals(0,
        compare(booleanobjectarray1, booleanobjectarray2));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray3));
    assertEquals(-1,
        compare(booleanobjectarray3, booleanobjectarray2));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray4));
    assertEquals(-1,
        compare(booleanobjectarray4, booleanobjectarray2));

    final Character[] charobjectarray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray3 = {(char) 0, (char) 3};
    final Character[] charobjectarray4 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE};
    assertEquals(0, compare(charobjectarray1, charobjectarray2));
    assertEquals(1, compare(charobjectarray3, charobjectarray2));
    assertEquals(-1, compare(charobjectarray2, charobjectarray3));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2));
    assertEquals(1, compare(charobjectarray2, charobjectarray4));

    final Byte[] byteobjectarray1 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray2 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray3 = {(byte) 0, (byte) 2};
    final Byte[] byteobjectarray4 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2)};
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2));
    assertEquals(1, compare(byteobjectarray3, byteobjectarray2));
    assertEquals(-1, compare(byteobjectarray2, byteobjectarray3));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4));

    final Short[] shortobjectarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray3 = {(short) 0, (short) 2};
    final Short[] shortobjectarray4 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)};
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2));
    assertEquals(1, compare(shortobjectarray3, shortobjectarray2));
    assertEquals(-1, compare(shortobjectarray2, shortobjectarray3));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4));

    final Integer[] integerarray1 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final Integer[] integerarray2 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final Integer[] integerarray3 = {0, 3};
    final Integer[] integerarray4 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2)};
    assertEquals(0, compare(integerarray1, integerarray2));
    assertEquals(1, compare(integerarray3, integerarray2));
    assertEquals(-1, compare(integerarray2, integerarray3));
    assertEquals(-1, compare(integerarray4, integerarray2));
    assertEquals(1, compare(integerarray2, integerarray4));

    final Long[] longobjectarray1 = {0L, -1L, 1L, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final Long[] longobjectarray2 = {0L, -1L, 1L, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final Long[] longobjectarray3 = {0L, 2L};
    final Long[] longobjectarray4 = {0L, -1L, 1L, Long.MIN_VALUE};
    assertEquals(0, compare(longobjectarray1, longobjectarray2));
    assertEquals(1, compare(longobjectarray3, longobjectarray2));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2));
    assertEquals(1, compare(longobjectarray2, longobjectarray4));

    final Float[] floatobjectarray1 = {0f, -1f, 1f,
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray2 = {0f, -1f, 1f,
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray3 = {0f, 2f};
    final Float[] floatobjectarray4 = {0f, -1f, 1f,
        Float.MIN_VALUE};
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4));

    final Double[] doubleobjectarray1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final Double[] doubleobjectarray2 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final Double[] doubleobjectarray3 = {0.0, 2.0};
    final Double[] doubleobjectarray4 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE};
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2));
    assertEquals(-1,
        compare(doubleobjectarray2, doubleobjectarray3));
    assertEquals(-1,
        compare(doubleobjectarray4, doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4));

    final Class<?>[] classarray1 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray2 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray3 = new Class<?>[]{TestClassB.class,
        TestClassB.class};
    final Class<?>[] classarray4 = new Class<?>[]{TestClassA.class,
        TestClassA.class, TestClassA.class};
    assertEquals(0, compare(classarray1, classarray2));
    assertEquals(1, compare(classarray3, classarray2));
    assertEquals(-1, compare(classarray2, classarray3));
    assertEquals(1, compare(classarray4, classarray2));
    assertEquals(-1, compare(classarray2, classarray4));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = {datea, dateb, datec};
    final Date[] datearray2 = {datea, dateb, datec};
    final Date[] datearray3 = {datec, datea, dateb};
    final Date[] datearray4 = {datea};
    assertEquals(0, compare(datearray1, datearray2));
    assertEquals(1, compare(datearray3, datearray2));
    assertEquals(-1, compare(datearray2, datearray3));
    assertEquals(-1, compare(datearray4, datearray2));
    assertEquals(1, compare(datearray2, datearray4));

    final BigInteger[] bigintegerarray1 = {new BigInteger(bytearray1),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray2 = {new BigInteger(bytearray2),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray3 = {new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray4 = {new BigInteger(bytearray2)};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4));

    final BigDecimal[] bigdecimalarray1 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray2 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray3 = {new BigDecimal(0),
        new BigDecimal(-1)};
    final BigDecimal[] bigdecimalarray4 = {new BigDecimal(Integer.MIN_VALUE)};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2));

    final Boolean[][] objectarray1 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray2 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray3 = {{true, false}, {true, true}};
    final Boolean[][] objectarray4 = {{true, false}, {false, true}};
    assertEquals(0, compare(objectarray1, objectarray2));
    assertEquals(-1, compare(objectarray3, objectarray2));
    assertEquals(1, compare(objectarray2, objectarray3));
    assertEquals(-1, compare(objectarray4, objectarray2));
    assertEquals(1, compare(objectarray2, objectarray4));

    final ArrayList<Character> col1 = new ArrayList<>();
    col1.add('a');
    col1.add('b');
    col1.add('c');
    final ArrayList<Character> col2 = new ArrayList<>();
    col2.add('a');
    col2.add('b');
    col2.add('c');
    final ArrayList<Character> col3 = new ArrayList<>();
    col3.add('b');
    col3.add('c');
    assertEquals(0, compare(col1, col2));
    assertEquals(1, compare(col3, col2));
    assertEquals(-1, compare(col2, col3));
  }

  @Test
  public void testObjectArray() {
    Object[] array1 = {new TestClassA(), new TestClassA()};
    Object[] array2 = {new TestClassA(), new TestClassA()};

    assertEquals(0, compare(array1, array1));
    assertTrue(compare(array1, array2) != 0);
    array2 = null;
    assertEquals(1, compare(array1, array2));
    assertEquals(-1, compare(array2, array1));
    array1 = null;
    assertEquals(0, compare(array1, array2));

    final boolean[][] booleanarray1 = {{true, false}, {false, true}};
    final boolean[][] booleanarray2 = {{true, false}, {false, true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    final boolean[][] booleanarray4 = {{true, false}};
    assertEquals(0, compare(booleanarray1, booleanarray2));
    assertEquals(1, compare(booleanarray1, booleanarray3));
    assertEquals(-1, compare(booleanarray3, booleanarray1));
    assertEquals(1, compare(booleanarray1, booleanarray4));
    assertEquals(-1, compare(booleanarray4, booleanarray1));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, (char) 3}};
    final char[][] chararray4 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE}};
    assertEquals(0, compare(chararray1, chararray2));
    assertEquals(-1, compare(chararray1, chararray3));
    assertEquals(1, compare(chararray3, chararray1));
    assertEquals(1, compare(chararray1, chararray4));
    assertEquals(-1, compare(chararray4, chararray1));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, (byte) 3}};
    final byte[][] bytearray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE}};
    assertEquals(0, compare(bytearray1, bytearray2));
    assertEquals(-1, compare(bytearray1, bytearray3));
    assertEquals(1, compare(bytearray3, bytearray1));
    assertEquals(1, compare(bytearray1, bytearray4));
    assertEquals(-1, compare(bytearray4, bytearray1));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray3 = {{(short) 0, (short) 3}};
    final short[][] shortarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE}};
    assertEquals(0, compare(shortarray1, shortarray2));
    assertEquals(-1, compare(shortarray1, shortarray3));
    assertEquals(1, compare(shortarray3, shortarray1));
    assertEquals(1, compare(shortarray1, shortarray4));
    assertEquals(-1, compare(shortarray4, shortarray1));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, 3}};
    final int[][] intarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE}};
    assertEquals(0, compare(intarray1, intarray2));
    assertEquals(-1, compare(intarray1, intarray3));
    assertEquals(1, compare(intarray3, intarray1));
    assertEquals(1, compare(intarray1, intarray4));
    assertEquals(-1, compare(intarray4, intarray1));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, 3}};
    final long[][] longarray4 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE}};
    assertEquals(0, compare(longarray1, longarray2));
    assertEquals(-1, compare(longarray1, longarray3));
    assertEquals(1, compare(longarray3, longarray1));
    assertEquals(1, compare(longarray1, longarray4));
    assertEquals(-1, compare(longarray4, longarray1));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, 3}};
    final float[][] floatarray4 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE}};
    assertEquals(0, compare(floatarray1, floatarray2));
    assertEquals(-1, compare(floatarray1, floatarray3));
    assertEquals(1, compare(floatarray3, floatarray1));
    assertEquals(1, compare(floatarray1, floatarray4));
    assertEquals(-1, compare(floatarray4, floatarray1));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, 3}};
    final double[][] doublearray4 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE}};
    assertEquals(0, compare(doublearray1, doublearray2));
    assertEquals(-1, compare(doublearray1, doublearray3));
    assertEquals(1, compare(doublearray3, doublearray1));
    assertEquals(1, compare(doublearray1, doublearray4));
    assertEquals(-1, compare(doublearray4, doublearray1));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    final String[][] stringarray4 = {{"long", "long"}};
    assertEquals(0, compare(stringarray1, stringarray2));
    assertEquals(-1, compare(stringarray2, stringarray3));
    assertEquals(1, compare(stringarray3, stringarray2));
    assertEquals(1, compare(stringarray2, stringarray4));
    assertEquals(-1, compare(stringarray4, stringarray2));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    final Boolean[][] booleanobjectarray4 = {{true, false}};
    assertEquals(0,
        compare(booleanobjectarray1, booleanobjectarray2));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray3));
    assertEquals(-1,
        compare(booleanobjectarray3, booleanobjectarray2));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray4));
    assertEquals(-1,
        compare(booleanobjectarray4, booleanobjectarray2));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, (char) 3}};
    final Character[][] charobjectarray4 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE}};
    assertEquals(0, compare(charobjectarray1, charobjectarray2));
    assertEquals(1, compare(charobjectarray3, charobjectarray2));
    assertEquals(-1, compare(charobjectarray2, charobjectarray3));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2));
    assertEquals(1, compare(charobjectarray2, charobjectarray4));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, (byte) 2}};
    final Byte[][] byteobjectarray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)}};
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2));
    assertEquals(1, compare(byteobjectarray3, byteobjectarray2));
    assertEquals(-1, compare(byteobjectarray2, byteobjectarray3));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, (short) 2}};
    final Short[][] shortobjectarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)}};
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2));
    assertEquals(1, compare(shortobjectarray3, shortobjectarray2));
    assertEquals(-1, compare(shortobjectarray2, shortobjectarray3));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, 3}};
    final Integer[][] integerarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)}};
    assertEquals(0, compare(integerarray1, integerarray2));
    assertEquals(1, compare(integerarray3, integerarray2));
    assertEquals(-1, compare(integerarray2, integerarray3));
    assertEquals(-1, compare(integerarray4, integerarray2));
    assertEquals(1, compare(integerarray2, integerarray4));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, 2L}};
    final Long[][] longobjectarray4 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)}};
    assertEquals(0, compare(longobjectarray1, longobjectarray2));
    assertEquals(1, compare(longobjectarray3, longobjectarray2));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2));
    assertEquals(1, compare(longobjectarray2, longobjectarray4));

    final Float[][] floatobjectarray1 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{0f, 2f}};
    final Float[][] floatobjectarray4 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)}};
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4));

    final Double[][] doubleobjectarray1 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{0.0, 2.0}};
    final Double[][] doubleobjectarray4 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE}};
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2));
    assertEquals(-1,
        compare(doubleobjectarray2, doubleobjectarray3));
    assertEquals(-1,
        compare(doubleobjectarray4, doubleobjectarray2));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4));

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class}, {TestClassB.class}};
    final Class<?>[][] classarray4 = {{TestClassA.class}, {TestClassA.class},
        {TestClassA.class}};
    assertEquals(0, compare(classarray1, classarray2));
    assertEquals(1, compare(classarray3, classarray2));
    assertEquals(-1, compare(classarray2, classarray3));
    assertEquals(1, compare(classarray4, classarray2));
    assertEquals(-1, compare(classarray2, classarray4));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec, datea}, {dateb}};
    final Date[][] datearray4 = {{datea, dateb}};
    assertEquals(0, compare(datearray1, datearray2));
    assertEquals(1, compare(datearray3, datearray2));
    assertEquals(-1, compare(datearray2, datearray3));
    assertEquals(-1, compare(datearray4, datearray2));
    assertEquals(1, compare(datearray2, datearray4));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray4 = {{new BigInteger(bytearray2[0])}};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    final BigDecimal[][] bigdecimalarray4 = {
        {new BigDecimal(Integer.MIN_VALUE)}};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2));

    final Boolean[][][] objectarray1 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray2 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray3 = {{{true}, {false}}, {{false, true}}};
    final Boolean[][][] objectarray4 = {{{true}, {false}}, {{true, true}}};
    assertEquals(0, compare(objectarray1, objectarray2));
    assertEquals(-1, compare(objectarray3, objectarray2));
    assertEquals(1, compare(objectarray2, objectarray3));
    assertEquals(-1, compare(objectarray4, objectarray2));
    assertEquals(1, compare(objectarray2, objectarray4));

    final ArrayList<Character> arraylist1 = new ArrayList<>();
    final ArrayList<Character> arraylist2 = new ArrayList<>();
    final Object[] col1 = {arraylist1, arraylist2};
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<>();
    final ArrayList<Character> arraylist4 = new ArrayList<>();
    final Object[] col2 = {arraylist3, arraylist4};
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<>();
    final Object[] col3 = {arraylist5};
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2));
    assertEquals(-1, compare(col3, col2));
    assertEquals(1, compare(col2, col3));
  }

  @Test
  public void testObjectArrayInt() {
    final Object[] array1 = {new TestClassA(), new TestClassA()};
    final Object[] array2 = {new TestClassA(), new TestClassA()};

    assertEquals(0,
        compare(array1, array1.length, array1, array1.length));
    assertTrue(compare(array1, array1.length, array2, array2.length) != 0);
    assertTrue(compare(array1, 1, array2, 1) != 0);

    final boolean[][] booleanarray1 = {{true, false}, {false, true}};
    final boolean[][] booleanarray2 = {{true, false}, {false, true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    final boolean[][] booleanarray4 = {{true, false}};
    assertEquals(0, compare(booleanarray1, booleanarray1.length,
        booleanarray2, booleanarray2.length));
    assertEquals(1, compare(booleanarray1, booleanarray1.length,
        booleanarray3, booleanarray3.length));
    assertEquals(-1, compare(booleanarray3, booleanarray3.length,
        booleanarray1, booleanarray1.length));
    assertEquals(1, compare(booleanarray1, booleanarray1.length,
        booleanarray4, booleanarray4.length));
    assertEquals(-1, compare(booleanarray4, booleanarray4.length,
        booleanarray1, booleanarray1.length));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, (char) 3}};
    final char[][] chararray4 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE}};
    assertEquals(0,
        compare(chararray1, chararray1.length, chararray2,
            chararray2.length));
    assertEquals(-1, compare(chararray1, chararray1.length, chararray3,
            chararray3.length));
    assertEquals(1,
        compare(chararray3, chararray3.length, chararray1,
            chararray1.length));
    assertEquals(1,
        compare(chararray1, chararray1.length, chararray4,
            chararray4.length));
    assertEquals(-1, compare(chararray4, chararray4.length, chararray1,
            chararray1.length));
    assertEquals(0, compare(chararray1, 1, chararray4, 1));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, (byte) 3}};
    final byte[][] bytearray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE}};
    assertEquals(0, compare(bytearray1, bytearray1.length, bytearray2, bytearray2.length));
    assertEquals(-1, compare(bytearray1, bytearray1.length, bytearray3, bytearray3.length));
    assertEquals(1, compare(bytearray3, bytearray3.length, bytearray1, bytearray1.length));
    assertEquals(1, compare(bytearray1, bytearray1.length, bytearray4, bytearray4.length));
    assertEquals(-1, compare(bytearray4, bytearray4.length, bytearray1, bytearray1.length));
    assertEquals(0, compare(bytearray1, 1, bytearray4, 1));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray3 = {{(short) 0, (short) 3}};
    final short[][] shortarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE}};
    assertEquals(0, compare(shortarray1, shortarray1.length, shortarray2,
            shortarray2.length));
    assertEquals(-1, compare(shortarray1, shortarray1.length, shortarray3,
            shortarray3.length));
    assertEquals(1, compare(shortarray3, shortarray3.length, shortarray1,
            shortarray1.length));
    assertEquals(1, compare(shortarray1, shortarray1.length, shortarray4,
            shortarray4.length));
    assertEquals(-1, compare(shortarray4, shortarray4.length, shortarray1,
            shortarray1.length));
    assertEquals(0, compare(shortarray1, 1, shortarray4, 1));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, 3}};
    final int[][] intarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE}};
    assertEquals(0, compare(intarray1, intarray1.length, intarray2, intarray2.length));
    assertEquals(-1, compare(intarray1, intarray1.length, intarray3, intarray3.length));
    assertEquals(1, compare(intarray3, intarray3.length, intarray1, intarray1.length));
    assertEquals(1, compare(intarray1, intarray1.length, intarray4, intarray4.length));
    assertEquals(-1, compare(intarray4, intarray4.length, intarray1, intarray1.length));
    assertEquals(0, compare(intarray1, 1, intarray4, 1));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, 3}};
    final long[][] longarray4 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE}};
    assertEquals(0, compare(longarray1, longarray1.length, longarray2, longarray2.length));
    assertEquals(-1, compare(longarray1, longarray1.length, longarray3, longarray3.length));
    assertEquals(1, compare(longarray3, longarray3.length, longarray1, longarray1.length));
    assertEquals(1, compare(longarray1, longarray1.length, longarray4, longarray4.length));
    assertEquals(-1, compare(longarray4, longarray4.length, longarray1, longarray1.length));
    assertEquals(0, compare(longarray1, 1, longarray4, 1));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, 3}};
    final float[][] floatarray4 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE}};
    assertEquals(0, compare(floatarray1, floatarray1.length, floatarray2,
            floatarray2.length));
    assertEquals(-1, compare(floatarray1, floatarray1.length, floatarray3,
            floatarray3.length));
    assertEquals(1, compare(floatarray3, floatarray3.length, floatarray1,
            floatarray1.length));
    assertEquals(1, compare(floatarray1, floatarray1.length, floatarray4,
            floatarray4.length));
    assertEquals(-1, compare(floatarray4, floatarray4.length, floatarray1,
            floatarray1.length));
    assertEquals(0, compare(floatarray1, 1, floatarray4, 1));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, 3}};
    final double[][] doublearray4 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE}};
    assertEquals(0, compare(doublearray1, doublearray1.length, doublearray2,
            doublearray2.length));
    assertEquals(-1, compare(doublearray1, doublearray1.length, doublearray3,
            doublearray3.length));
    assertEquals(1, compare(doublearray3, doublearray3.length, doublearray1,
            doublearray1.length));
    assertEquals(1, compare(doublearray1, doublearray1.length, doublearray4,
            doublearray4.length));
    assertEquals(-1, compare(doublearray4, doublearray4.length, doublearray1,
            doublearray1.length));
    assertEquals(0, compare(doublearray1, 1, doublearray4, 1));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    final String[][] stringarray4 = {{"long", "long"}};
    assertEquals(0, compare(stringarray1, stringarray1.length, stringarray2,
            stringarray2.length));
    assertEquals(-1, compare(stringarray2, stringarray2.length, stringarray3,
            stringarray3.length));
    assertEquals(1, compare(stringarray3, stringarray3.length, stringarray2,
            stringarray2.length));
    assertEquals(1, compare(stringarray2, stringarray2.length, stringarray4,
            stringarray4.length));
    assertEquals(-1, compare(stringarray4, stringarray4.length, stringarray2,
            stringarray2.length));
    assertEquals(0, compare(stringarray1, 1, stringarray4, 1));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    final Boolean[][] booleanobjectarray4 = {{true, false}};
    assertEquals(0,
        compare(booleanobjectarray1, booleanobjectarray1.length,
            booleanobjectarray2, booleanobjectarray2.length));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray2.length,
            booleanobjectarray3, booleanobjectarray3.length));
    assertEquals(-1,
        compare(booleanobjectarray3, booleanobjectarray3.length,
            booleanobjectarray2, booleanobjectarray2.length));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray2.length,
            booleanobjectarray4, booleanobjectarray4.length));
    assertEquals(-1,
        compare(booleanobjectarray4, booleanobjectarray4.length,
            booleanobjectarray2, booleanobjectarray2.length));
    assertEquals(0, compare(booleanobjectarray1, 1, booleanobjectarray4, 1));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, (char) 3}};
    final Character[][] charobjectarray4 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE}};
    assertEquals(0,
        compare(charobjectarray1, charobjectarray1.length,
            charobjectarray2, charobjectarray2.length));
    assertEquals(1,
        compare(charobjectarray3, charobjectarray3.length,
            charobjectarray2, charobjectarray2.length));
    assertEquals(-1,
        compare(charobjectarray2, charobjectarray2.length,
            charobjectarray3, charobjectarray3.length));
    assertEquals(-1,
        compare(charobjectarray4, charobjectarray4.length,
            charobjectarray2, charobjectarray2.length));
    assertEquals(1,
        compare(charobjectarray2, charobjectarray2.length,
            charobjectarray4, charobjectarray4.length));
    assertEquals(0,
        compare(charobjectarray1, 1, charobjectarray4, 1));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, (byte) 2}};
    final Byte[][] byteobjectarray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(byteobjectarray1, byteobjectarray1.length,
            byteobjectarray2, byteobjectarray2.length));
    assertEquals(1,
        compare(byteobjectarray3, byteobjectarray3.length,
            byteobjectarray2, byteobjectarray2.length));
    assertEquals(-1,
        compare(byteobjectarray2, byteobjectarray2.length,
            byteobjectarray3, byteobjectarray3.length));
    assertEquals(-1,
        compare(byteobjectarray4, byteobjectarray4.length,
            byteobjectarray2, byteobjectarray2.length));
    assertEquals(1,
        compare(byteobjectarray2, byteobjectarray2.length,
            byteobjectarray4, byteobjectarray4.length));
    assertEquals(0,
        compare(byteobjectarray1, 1, byteobjectarray4, 1));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, (short) 2}};
    final Short[][] shortobjectarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(shortobjectarray1, shortobjectarray1.length,
            shortobjectarray2, shortobjectarray2.length));
    assertEquals(1,
        compare(shortobjectarray3, shortobjectarray3.length,
            shortobjectarray2, shortobjectarray2.length));
    assertEquals(-1,
        compare(shortobjectarray2, shortobjectarray2.length,
            shortobjectarray3, shortobjectarray3.length));
    assertEquals(-1,
        compare(shortobjectarray4, shortobjectarray4.length,
            shortobjectarray2, shortobjectarray2.length));
    assertEquals(1,
        compare(shortobjectarray2, shortobjectarray2.length,
            shortobjectarray4, shortobjectarray4.length));
    assertEquals(0, compare(shortobjectarray1, 1, shortobjectarray4, 1));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, 3}};
    final Integer[][] integerarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)}};
    assertEquals(0, compare(integerarray1, integerarray1.length,
        integerarray2, integerarray2.length));
    assertEquals(1, compare(integerarray3, integerarray3.length,
        integerarray2, integerarray2.length));
    assertEquals(-1, compare(integerarray2, integerarray2.length,
        integerarray3, integerarray3.length));
    assertEquals(-1, compare(integerarray4, integerarray4.length,
        integerarray2, integerarray2.length));
    assertEquals(1, compare(integerarray2, integerarray2.length,
        integerarray4, integerarray4.length));
    assertEquals(0, compare(integerarray1, 1, integerarray4, 1));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, 2L}};
    final Long[][] longobjectarray4 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(longobjectarray1, longobjectarray1.length,
            longobjectarray2, longobjectarray2.length));
    assertEquals(1,
        compare(longobjectarray3, longobjectarray3.length,
            longobjectarray2, longobjectarray2.length));
    assertEquals(-1,
        compare(longobjectarray2, longobjectarray2.length,
            longobjectarray3, longobjectarray3.length));
    assertEquals(-1,
        compare(longobjectarray4, longobjectarray4.length,
            longobjectarray2, longobjectarray2.length));
    assertEquals(1,
        compare(longobjectarray2, longobjectarray2.length,
            longobjectarray4, longobjectarray4.length));
    assertEquals(0,
        compare(longobjectarray1, 1, longobjectarray4, 1));

    final Float[][] floatobjectarray1 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{0f, 2f}};
    final Float[][] floatobjectarray4 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(floatobjectarray1, floatobjectarray1.length,
            floatobjectarray2, floatobjectarray2.length));
    assertEquals(1,
        compare(floatobjectarray3, floatobjectarray3.length,
            floatobjectarray2, floatobjectarray2.length));
    assertEquals(-1,
        compare(floatobjectarray2, floatobjectarray2.length,
            floatobjectarray3, floatobjectarray3.length));
    assertEquals(-1,
        compare(floatobjectarray4, floatobjectarray4.length,
            floatobjectarray2, floatobjectarray2.length));
    assertEquals(1,
        compare(floatobjectarray2, floatobjectarray2.length,
            floatobjectarray4, floatobjectarray4.length));
    assertEquals(0, compare(floatobjectarray1, 1, floatobjectarray4, 1));

    final Double[][] doubleobjectarray1 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{0.0, 2.0}};
    final Double[][] doubleobjectarray4 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE}};
    assertEquals(0,
        compare(doubleobjectarray1, doubleobjectarray1.length,
            doubleobjectarray2, doubleobjectarray2.length));
    assertEquals(1,
        compare(doubleobjectarray3, doubleobjectarray3.length,
            doubleobjectarray2, doubleobjectarray2.length));
    assertEquals(-1,
        compare(doubleobjectarray2, doubleobjectarray2.length,
            doubleobjectarray3, doubleobjectarray3.length));
    assertEquals(-1,
        compare(doubleobjectarray4, doubleobjectarray4.length,
            doubleobjectarray2, doubleobjectarray2.length));
    assertEquals(1,
        compare(doubleobjectarray2, doubleobjectarray2.length,
            doubleobjectarray4, doubleobjectarray4.length));
    assertEquals(0, compare(doubleobjectarray1, 1, doubleobjectarray4, 1));

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class}, {TestClassB.class}};
    final Class<?>[][] classarray4 = {{TestClassA.class}, {TestClassA.class},
        {TestClassA.class}};
    assertEquals(0, compare(classarray1, classarray1.length, classarray2,
            classarray2.length));
    assertEquals(1, compare(classarray3, classarray3.length, classarray2,
            classarray2.length));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray3,
            classarray3.length));
    assertEquals(1, compare(classarray4, classarray4.length, classarray2,
            classarray2.length));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray4,
            classarray4.length));
    assertEquals(0, compare(classarray1, 1, classarray4, 1));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec, datea}, {dateb}};
    final Date[][] datearray4 = {{datea, dateb}};
    assertEquals(0,
        compare(datearray1, datearray1.length, datearray2,
            datearray2.length));
    assertEquals(1,
        compare(datearray3, datearray3.length, datearray2,
            datearray2.length));
    assertEquals(-1, compare(datearray2, datearray2.length, datearray3,
            datearray3.length));
    assertEquals(-1, compare(datearray4, datearray4.length, datearray2,
            datearray2.length));
    assertEquals(1,
        compare(datearray2, datearray2.length, datearray4,
            datearray4.length));
    assertEquals(0, compare(datearray1, 1, datearray4, 1));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray4 = {{new BigInteger(bytearray2[0])}};
    assertEquals(0,
        compare(bigintegerarray1, bigintegerarray1.length,
            bigintegerarray2, bigintegerarray2.length));
    assertEquals(-1,
        compare(bigintegerarray3, bigintegerarray3.length,
            bigintegerarray2, bigintegerarray2.length));
    assertEquals(1,
        compare(bigintegerarray2, bigintegerarray2.length,
            bigintegerarray3, bigintegerarray3.length));
    assertEquals(-1,
        compare(bigintegerarray4, bigintegerarray4.length,
            bigintegerarray2, bigintegerarray2.length));
    assertEquals(1,
        compare(bigintegerarray2, bigintegerarray2.length,
            bigintegerarray4, bigintegerarray4.length));
    assertEquals(0,
        compare(bigintegerarray1, 1, bigintegerarray4, 1));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    final BigDecimal[][] bigdecimalarray4 = {
        {new BigDecimal(Integer.MIN_VALUE)}};
    assertEquals(0,
        compare(bigdecimalarray1, bigdecimalarray1.length,
            bigdecimalarray2, bigdecimalarray2.length));
    assertEquals(-1,
        compare(bigdecimalarray2, bigdecimalarray2.length,
            bigdecimalarray3, bigdecimalarray3.length));
    assertEquals(1,
        compare(bigdecimalarray3, bigdecimalarray3.length,
            bigdecimalarray2, bigdecimalarray2.length));
    assertEquals(1,
        compare(bigdecimalarray2, bigdecimalarray2.length,
            bigdecimalarray4, bigdecimalarray4.length));
    assertEquals(-1,
        compare(bigdecimalarray4, bigdecimalarray4.length,
            bigdecimalarray2, bigdecimalarray2.length));
    assertEquals(0,
        compare(bigdecimalarray1, 1, bigdecimalarray4, 1));

    final Boolean[][][] objectarray1 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray2 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray3 = {{{true}, {false}}, {{false, true}}};
    final Boolean[][][] objectarray4 = {{{true}, {false}}, {{true, true}}};
    assertEquals(0, compare(objectarray1, objectarray1.length, objectarray2,
            objectarray2.length));
    assertEquals(-1, compare(objectarray3, objectarray3.length, objectarray2,
            objectarray2.length));
    assertEquals(1, compare(objectarray2, array2.length, objectarray3,
            objectarray3.length));
    assertEquals(-1, compare(objectarray4, objectarray4.length, objectarray2,
            objectarray2.length));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray4,
            objectarray4.length));
    assertEquals(0, compare(objectarray1, 1, objectarray4, 1));

    final ArrayList<Character> arraylist1 = new ArrayList<>();
    final ArrayList<Character> arraylist2 = new ArrayList<>();
    final Object[] col1 = {arraylist1, arraylist2};
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<>();
    final ArrayList<Character> arraylist4 = new ArrayList<>();
    final Object[] col2 = {arraylist3, arraylist4};
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<>();
    final Object[] col3 = {arraylist5};
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2));
    assertEquals(-1, compare(col3, col2));
    assertEquals(1, compare(col2, col3));
  }

  @Test
  public void testFloatWithEpsilon_1() {
    final float epsilon = 0.01f;
    final Float v1 = 1.0f;
    final Float v2 = 1.0f - epsilon;
    assertEquals(0, compare(v1, v2, epsilon));
  }

  @Test
  public void testFloatArrayWithEpsilon_1() {
    final double epsilon = 0.01;
    final Float[] floatobjectarray1 = {0f, -1f, 1f,
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray2 = {0f, -1f, (float) (1.0 - epsilon),
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2, epsilon));
  }

  @Test
  public void testObjectWithEpsilon() {
    final double epsilon = 0.01;
    Object value1 = new TestClassA();
    Object value2 = new TestClassA();

    assertEquals(0, compare(value1, value1, epsilon));
    assertTrue(compare(value1, value2, epsilon) != 0);
    value2 = new TestClassB();
    assertEquals(-1, compare(value1, value2, epsilon));
    assertEquals(1, compare(value2, value1, epsilon));
    value2 = new TestClassZ();
    assertEquals(-25, compare(value1, value2, epsilon));
    assertEquals(25, compare(value2, value1, epsilon));
    value1 = TestClassA.class;
    value2 = TestClassB.class;
    assertTrue(compare(value1, value2, epsilon) < 0);
    assertTrue(compare(value2, value1, epsilon) > 0);
    value2 = null;
    assertEquals(1, compare(value1, value2, epsilon));
    assertEquals(-1, compare(value2, value1, epsilon));
    value1 = null;
    assertEquals(0, compare(value1, value2, epsilon));

    final boolean[] booleanarray1 = {true, false, false, true};
    final boolean[] booleanarray2 = {true, false, false, true};
    final boolean[] booleanarray3 = {false, true, true};
    final boolean[] booleanarray4 = {true, false};
    assertEquals(0, compare(booleanarray1, booleanarray2, epsilon));
    assertEquals(1, compare(booleanarray2, booleanarray3, epsilon));
    assertEquals(-1, compare(booleanarray3, booleanarray2, epsilon));
    assertEquals(1, compare(booleanarray2, booleanarray4, epsilon));
    assertEquals(-1, compare(booleanarray4, booleanarray2, epsilon));

    final char[] chararray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE};
    final char[] chararray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE, (char) (Character.MAX_VALUE / 2),
        Character.MAX_VALUE};
    final char[] chararray3 = {(char) 0, (char) 3};
    final char[] chararray4 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2),
        Character.MIN_VALUE};

    assertEquals(0, compare(chararray1, chararray2, epsilon));
    assertEquals(1, compare(chararray3, chararray2, epsilon));
    assertEquals(-1, compare(chararray2, chararray3, epsilon));
    assertEquals(-1, compare(chararray4, chararray2, epsilon));
    assertEquals(1, compare(chararray2, chararray4, epsilon));

    final byte[] bytearray1 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray2 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray3 = {(byte) 0, (byte) 2};
    final byte[] bytearray4 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2)};
    assertEquals(0, compare(bytearray1, bytearray2, epsilon));
    assertEquals(1, compare(bytearray3, bytearray2, epsilon));
    assertEquals(-1, compare(bytearray2, bytearray3, epsilon));
    assertEquals(1, compare(bytearray2, bytearray4, epsilon));
    assertEquals(-1, compare(bytearray4, bytearray2, epsilon));

    final short[] shortarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray3 = {(short) 0, (short) 3};
    final short[] shortarray4 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2)};
    assertEquals(0, compare(shortarray1, shortarray2, epsilon));
    assertEquals(1, compare(shortarray3, shortarray2, epsilon));
    assertEquals(-1, compare(shortarray2, shortarray3, epsilon));
    assertEquals(1, compare(shortarray2, shortarray4, epsilon));
    assertEquals(-1, compare(shortarray4, shortarray2, epsilon));

    final int[] intarray1 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final int[] intarray2 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final int[] intarray3 = {0, 4};
    final int[] intarray4 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2)};
    assertEquals(0, compare(intarray1, intarray2, epsilon));
    assertEquals(1, compare(intarray3, intarray2, epsilon));
    assertEquals(-1, compare(intarray2, intarray3, epsilon));
    assertEquals(1, compare(intarray2, intarray4, epsilon));
    assertEquals(-1, compare(intarray4, intarray2, epsilon));

    final long[] longarray1 = {0, -1, 1, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final long[] longarray2 = {0, -1, 1, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final long[] longarray3 = {0, 2};
    final long[] longarray4 = {0, -1, 1, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2)};
    assertEquals(0, compare(longarray1, longarray2, epsilon));
    assertEquals(1, compare(longarray3, longarray2, epsilon));
    assertEquals(-1, compare(longarray2, longarray3, epsilon));
    assertEquals(-1, compare(longarray4, longarray2, epsilon));
    assertEquals(1, compare(longarray2, longarray4, epsilon));

    final float[] floatarray1 = {0f, -1f, 1f, Float.MIN_VALUE,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MAX_VALUE};
    final float[] floatarray2 = {0f, -1f, (float) (1.0 - epsilon),
        Float.MIN_VALUE,
        (Float.MIN_VALUE / 2), (Float.MAX_VALUE / 2),
        Float.MAX_VALUE};
    final float[] floatarray3 = {0f, (float) (2.0 + epsilon)};
    final float[] floatarray4 = {0f, -1f, 1f, Float.MIN_VALUE,
        (Float.MIN_VALUE / 2)};
    assertEquals(0, compare(floatarray1, floatarray2, epsilon));
    assertEquals(-1, compare(floatarray2, floatarray3, epsilon));
    assertEquals(1, compare(floatarray3, floatarray2, epsilon));
    assertEquals(1, compare(floatarray2, floatarray4, epsilon));
    assertEquals(-1, compare(floatarray4, floatarray2, epsilon));

    final Float[] floatobjectarray1 = {0f, -1f, 1f,
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray2 = {0f, -1f, (float) (1.0 - epsilon),
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray3 = {0f, (float) (2.0 + epsilon)};
    final Float[] floatobjectarray4 = {0f, -1f, 1f,
        Float.MIN_VALUE};
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2, epsilon));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3, epsilon));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4, epsilon));

    final double[] doublearray1 = {0, -1, 1,
        Double.MIN_VALUE, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final double[] doublearray2 = {0, -1, 1.0 - epsilon / 10,
        Double.MIN_VALUE, (Double.MIN_VALUE / 2),
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final double[] doublearray3 = {0, Double.MAX_VALUE};

    final double[] doublearray4 = {0, -1, 1,
        Double.MIN_VALUE, (Double.MIN_VALUE / 2)};
    assertEquals(0, compare(doublearray1, doublearray2, epsilon));
    assertEquals(1, compare(doublearray3, doublearray2, epsilon));
    assertEquals(-1, compare(doublearray2, doublearray3, epsilon));
    assertEquals(-1, compare(doublearray4, doublearray2, epsilon));
    assertEquals(1, compare(doublearray2, doublearray4, epsilon));

    final Double[] doubleobjectarray1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final Double[] doubleobjectarray2 = {0.0, -1.0, (1.0 - epsilon / 10.0),
        (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final Double[] doubleobjectarray3 = {0.0, (2 + epsilon)};
    final Double[] doubleobjectarray4 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE};
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2, epsilon));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray3, epsilon));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4, epsilon));

    final String[] stringarray1 = {"long", "long", "ago"};
    final String[] stringarray2 = {"long", "long", "ago"};
    final String[] stringarray3 = {"long", "time", "no", "see"};
    final String[] stringarray4 = {"long", "long"};
    assertEquals(0, compare(stringarray1, stringarray2, epsilon));
    assertEquals(-1, compare(stringarray2, stringarray3, epsilon));
    assertEquals(1, compare(stringarray3, stringarray2, epsilon));
    assertEquals(1, compare(stringarray2, stringarray4, epsilon));
    assertEquals(-1, compare(stringarray4, stringarray2, epsilon));

    final Boolean[] booleanobjectarray1 = {true, false, false, true};
    final Boolean[] booleanobjectarray2 = {true, false, false, true};
    final Boolean[] booleanobjectarray3 = {false, true, true};
    final Boolean[] booleanobjectarray4 = {true, false};
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray3, epsilon));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray4, epsilon));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray2, epsilon));

    final Character[] charobjectarray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray3 = {(char) 0, (char) 3};
    final Character[] charobjectarray4 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE};
    assertEquals(0, compare(charobjectarray1, charobjectarray2, epsilon));
    assertEquals(1, compare(charobjectarray3, charobjectarray2, epsilon));
    assertEquals(-1, compare(charobjectarray2, charobjectarray3, epsilon));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2, epsilon));
    assertEquals(1, compare(charobjectarray2, charobjectarray4, epsilon));

    final Byte[] byteobjectarray1 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray2 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray3 = {(byte) 0, (byte) 2};
    final Byte[] byteobjectarray4 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2)};
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2, epsilon));
    assertEquals(1, compare(byteobjectarray3, byteobjectarray2, epsilon));
    assertEquals(-1, compare(byteobjectarray2, byteobjectarray3, epsilon));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2, epsilon));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4, epsilon));

    final Short[] shortobjectarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray3 = {(short) 0, (short) 2};
    final Short[] shortobjectarray4 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)};
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2, epsilon));
    assertEquals(1, compare(shortobjectarray3, shortobjectarray2, epsilon));
    assertEquals(-1, compare(shortobjectarray2, shortobjectarray3, epsilon));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2, epsilon));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4, epsilon));

    final Integer[] integerarray1 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final Integer[] integerarray2 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final Integer[] integerarray3 = {0, 3};
    final Integer[] integerarray4 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2)};
    assertEquals(0, compare(integerarray1, integerarray2, epsilon));
    assertEquals(1, compare(integerarray3, integerarray2, epsilon));
    assertEquals(-1, compare(integerarray2, integerarray3, epsilon));
    assertEquals(-1, compare(integerarray4, integerarray2, epsilon));
    assertEquals(1, compare(integerarray2, integerarray4, epsilon));

    final Long[] longobjectarray1 = {0L, -1L, 1L, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final Long[] longobjectarray2 = {0L, -1L, 1L, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final Long[] longobjectarray3 = {0L, 2L};
    final Long[] longobjectarray4 = {0L, -1L, 1L, Long.MIN_VALUE};
    assertEquals(0, compare(longobjectarray1, longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray3, longobjectarray2, epsilon));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3, epsilon));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray2, longobjectarray4, epsilon));

    final Class<?>[] classarray1 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray2 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray3 = new Class<?>[]{TestClassB.class,
        TestClassB.class};
    final Class<?>[] classarray4 = new Class<?>[]{TestClassA.class,
        TestClassA.class, TestClassA.class};
    assertEquals(0, compare(classarray1, classarray2, epsilon));
    assertEquals(1, compare(classarray3, classarray2, epsilon));
    assertEquals(-1, compare(classarray2, classarray3, epsilon));
    assertEquals(1, compare(classarray4, classarray2, epsilon));
    assertEquals(-1, compare(classarray2, classarray4, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = {datea, dateb, datec};
    final Date[] datearray2 = {datea, dateb, datec};
    final Date[] datearray3 = {datec, datea, dateb};
    final Date[] datearray4 = {datea};
    assertEquals(0, compare(datearray1, datearray2, epsilon));
    assertEquals(1, compare(datearray3, datearray2, epsilon));
    assertEquals(-1, compare(datearray2, datearray3, epsilon));
    assertEquals(-1, compare(datearray4, datearray2, epsilon));
    assertEquals(1, compare(datearray2, datearray4, epsilon));

    final BigInteger[] bigintegerarray1 = {new BigInteger(bytearray1),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray2 = {new BigInteger(bytearray2),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray3 = {new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray4 = {new BigInteger(bytearray2)};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2, epsilon));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3, epsilon));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4, epsilon));

    final BigDecimal[] bigdecimalarray1 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray2 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray3 = {new BigDecimal(0),
        new BigDecimal(-1)};
    final BigDecimal[] bigdecimalarray4 = {new BigDecimal(Integer.MIN_VALUE)};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2, epsilon));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3, epsilon));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2, epsilon));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4, epsilon));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2, epsilon));

    final Boolean[][] objectarray1 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray2 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray3 = {{true, false}, {true, true}};
    final Boolean[][] objectarray4 = {{true, false}, {false, true}};
    assertEquals(0, compare(objectarray1, objectarray2, epsilon));
    assertEquals(-1, compare(objectarray3, objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, objectarray3, epsilon));
    assertEquals(-1, compare(objectarray4, objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, objectarray4, epsilon));

    final ArrayList<Character> col1 = new ArrayList<>();
    col1.add('a');
    col1.add('b');
    col1.add('c');
    final ArrayList<Character> col2 = new ArrayList<>();
    col2.add('a');
    col2.add('b');
    col2.add('c');
    final ArrayList<Character> col3 = new ArrayList<>();
    col3.add('b');
    col3.add('c');
    assertEquals(0, compare(col1, col2, epsilon));
    assertEquals(1, compare(col3, col2, epsilon));
    assertEquals(-1, compare(col2, col3, epsilon));
  }

  @Test
  public void testObjectArrayWithEpsilon() {
    final double epsilon = 0.01;
    Object[] array1 = {new TestClassA(), new TestClassA()};
    Object[] array2 = {new TestClassA(), new TestClassA()};

    assertEquals(0, compare(array1, array1, epsilon));
    assertTrue(compare(array1, array2, epsilon) != 0);
    array2 = null;
    assertEquals(1, compare(array1, array2, epsilon));
    assertEquals(-1, compare(array2, array1, epsilon));
    array1 = null;
    assertEquals(0, compare(array1, array2, epsilon));

    final boolean[][] booleanarray1 = {{true, false}, {false, true}};
    final boolean[][] booleanarray2 = {{true, false}, {false, true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    final boolean[][] booleanarray4 = {{true, false}};
    assertEquals(0, compare(booleanarray1, booleanarray2, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray3, epsilon));
    assertEquals(-1, compare(booleanarray3, booleanarray1, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray4, epsilon));
    assertEquals(-1, compare(booleanarray4, booleanarray1, epsilon));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, (char) 3}};
    final char[][] chararray4 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE}};
    assertEquals(0, compare(chararray1, chararray2, epsilon));
    assertEquals(-1, compare(chararray1, chararray3, epsilon));
    assertEquals(1, compare(chararray3, chararray1, epsilon));
    assertEquals(1, compare(chararray1, chararray4, epsilon));
    assertEquals(-1, compare(chararray4, chararray1, epsilon));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, (byte) 3}};
    final byte[][] bytearray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE}};
    assertEquals(0, compare(bytearray1, bytearray2, epsilon));
    assertEquals(-1, compare(bytearray1, bytearray3, epsilon));
    assertEquals(1, compare(bytearray3, bytearray1, epsilon));
    assertEquals(1, compare(bytearray1, bytearray4, epsilon));
    assertEquals(-1, compare(bytearray4, bytearray1, epsilon));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray3 = {{(short) 0, (short) 3}};
    final short[][] shortarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE}};
    assertEquals(0, compare(shortarray1, shortarray2, epsilon));
    assertEquals(-1, compare(shortarray1, shortarray3, epsilon));
    assertEquals(1, compare(shortarray3, shortarray1, epsilon));
    assertEquals(1, compare(shortarray1, shortarray4, epsilon));
    assertEquals(-1, compare(shortarray4, shortarray1, epsilon));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, 3}};
    final int[][] intarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE}};
    assertEquals(0, compare(intarray1, intarray2, epsilon));
    assertEquals(-1, compare(intarray1, intarray3, epsilon));
    assertEquals(1, compare(intarray3, intarray1, epsilon));
    assertEquals(1, compare(intarray1, intarray4, epsilon));
    assertEquals(-1, compare(intarray4, intarray1, epsilon));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, 3}};
    final long[][] longarray4 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE}};
    assertEquals(0, compare(longarray1, longarray2, epsilon));
    assertEquals(-1, compare(longarray1, longarray3, epsilon));
    assertEquals(1, compare(longarray3, longarray1, epsilon));
    assertEquals(1, compare(longarray1, longarray4, epsilon));
    assertEquals(-1, compare(longarray4, longarray1, epsilon));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, 3}};
    final float[][] floatarray4 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE}};
    assertEquals(0, compare(floatarray1, floatarray2, epsilon));
    assertEquals(-1, compare(floatarray1, floatarray3, epsilon));
    assertEquals(1, compare(floatarray3, floatarray1, epsilon));
    assertEquals(1, compare(floatarray1, floatarray4, epsilon));
    assertEquals(-1, compare(floatarray4, floatarray1, epsilon));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, 3}};
    final double[][] doublearray4 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE}};
    assertEquals(0, compare(doublearray1, doublearray2, epsilon));
    assertEquals(-1, compare(doublearray1, doublearray3, epsilon));
    assertEquals(1, compare(doublearray3, doublearray1, epsilon));
    assertEquals(1, compare(doublearray1, doublearray4, epsilon));
    assertEquals(-1, compare(doublearray4, doublearray1, epsilon));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    final String[][] stringarray4 = {{"long", "long"}};
    assertEquals(0, compare(stringarray1, stringarray2, epsilon));
    assertEquals(-1, compare(stringarray2, stringarray3, epsilon));
    assertEquals(1, compare(stringarray3, stringarray2, epsilon));
    assertEquals(1, compare(stringarray2, stringarray4, epsilon));
    assertEquals(-1, compare(stringarray4, stringarray2, epsilon));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    final Boolean[][] booleanobjectarray4 = {{true, false}};
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray3, epsilon));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray2, epsilon));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray4, epsilon));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray2, epsilon));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, (char) 3}};
    final Character[][] charobjectarray4 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE}};
    assertEquals(0, compare(charobjectarray1, charobjectarray2, epsilon));
    assertEquals(1, compare(charobjectarray3, charobjectarray2, epsilon));
    assertEquals(-1, compare(charobjectarray2, charobjectarray3, epsilon));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2, epsilon));
    assertEquals(1, compare(charobjectarray2, charobjectarray4, epsilon));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, (byte) 2}};
    final Byte[][] byteobjectarray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)}};
    assertEquals(0, compare(byteobjectarray1, byteobjectarray2, epsilon));
    assertEquals(1, compare(byteobjectarray3, byteobjectarray2, epsilon));
    assertEquals(-1, compare(byteobjectarray2, byteobjectarray3, epsilon));
    assertEquals(-1, compare(byteobjectarray4, byteobjectarray2, epsilon));
    assertEquals(1, compare(byteobjectarray2, byteobjectarray4, epsilon));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, (short) 2}};
    final Short[][] shortobjectarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)}};
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2, epsilon));
    assertEquals(1, compare(shortobjectarray3, shortobjectarray2, epsilon));
    assertEquals(-1, compare(shortobjectarray2, shortobjectarray3, epsilon));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2, epsilon));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4, epsilon));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, 3}};
    final Integer[][] integerarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)}};
    assertEquals(0, compare(integerarray1, integerarray2, epsilon));
    assertEquals(1, compare(integerarray3, integerarray2, epsilon));
    assertEquals(-1, compare(integerarray2, integerarray3, epsilon));
    assertEquals(-1, compare(integerarray4, integerarray2, epsilon));
    assertEquals(1, compare(integerarray2, integerarray4, epsilon));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, 2L}};
    final Long[][] longobjectarray4 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)}};
    assertEquals(0, compare(longobjectarray1, longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray3, longobjectarray2, epsilon));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3, epsilon));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2, epsilon));
    assertEquals(1, compare(longobjectarray2, longobjectarray4, epsilon));

    final Float[][] floatobjectarray1 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{0f, 2f}};
    final Float[][] floatobjectarray4 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)}};
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2, epsilon));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3, epsilon));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2, epsilon));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4, epsilon));

    final Double[][] doubleobjectarray1 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{0.0, 2.0}};
    final Double[][] doubleobjectarray4 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE}};
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2, epsilon));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray3, epsilon));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray2, epsilon));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4, epsilon));

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class}, {TestClassB.class}};
    final Class<?>[][] classarray4 = {{TestClassA.class}, {TestClassA.class},
        {TestClassA.class}};
    assertEquals(0, compare(classarray1, classarray2, epsilon));
    assertEquals(1, compare(classarray3, classarray2, epsilon));
    assertEquals(-1, compare(classarray2, classarray3, epsilon));
    assertEquals(1, compare(classarray4, classarray2, epsilon));
    assertEquals(-1, compare(classarray2, classarray4, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec, datea}, {dateb}};
    final Date[][] datearray4 = {{datea, dateb}};
    assertEquals(0, compare(datearray1, datearray2, epsilon));
    assertEquals(1, compare(datearray3, datearray2, epsilon));
    assertEquals(-1, compare(datearray2, datearray3, epsilon));
    assertEquals(-1, compare(datearray4, datearray2, epsilon));
    assertEquals(1, compare(datearray2, datearray4, epsilon));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray4 = {{new BigInteger(bytearray2[0])}};
    assertEquals(0, compare(bigintegerarray1, bigintegerarray2, epsilon));
    assertEquals(-1, compare(bigintegerarray3, bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray3, epsilon));
    assertEquals(-1, compare(bigintegerarray4, bigintegerarray2, epsilon));
    assertEquals(1, compare(bigintegerarray2, bigintegerarray4, epsilon));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    final BigDecimal[][] bigdecimalarray4 = {
        {new BigDecimal(Integer.MIN_VALUE)}};
    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray2, epsilon));
    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray3, epsilon));
    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray2, epsilon));
    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray4, epsilon));
    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray2, epsilon));

    final Boolean[][][] objectarray1 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray2 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray3 = {{{true}, {false}}, {{false, true}}};
    final Boolean[][][] objectarray4 = {{{true}, {false}}, {{true, true}}};
    assertEquals(0, compare(objectarray1, objectarray2, epsilon));
    assertEquals(-1, compare(objectarray3, objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, objectarray3, epsilon));
    assertEquals(-1, compare(objectarray4, objectarray2, epsilon));
    assertEquals(1, compare(objectarray2, objectarray4, epsilon));

    final ArrayList<Character> arraylist1 = new ArrayList<>();
    final ArrayList<Character> arraylist2 = new ArrayList<>();
    final Object[] col1 = {arraylist1, arraylist2};
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<>();
    final ArrayList<Character> arraylist4 = new ArrayList<>();
    final Object[] col2 = {arraylist3, arraylist4};
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<>();
    final Object[] col3 = {arraylist5};
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2, epsilon));
    assertEquals(-1, compare(col3, col2, epsilon));
    assertEquals(1, compare(col2, col3, epsilon));
  }

  @Test
  public void testObjectArrayIntWithEpsilon() {
    final double epsilon = 0.01;
    final Object[] array1 = {new TestClassA(), new TestClassA()};
    final Object[] array2 = {new TestClassA(), new TestClassA()};

    assertEquals(0, compare(array1, array1.length, array1, array1.length, epsilon));
    assertTrue(
        compare(array1, array1.length, array2, array2.length, epsilon) != 0);
    assertTrue(compare(array1, 1, array2, 1, epsilon) != 0);

    final boolean[][] booleanarray1 = {{true, false}, {false, true}};
    final boolean[][] booleanarray2 = {{true, false}, {false, true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    final boolean[][] booleanarray4 = {{true, false}};
    assertEquals(0, compare(booleanarray1, booleanarray1.length,
        booleanarray2, booleanarray2.length, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray1.length,
        booleanarray3, booleanarray3.length, epsilon));
    assertEquals(-1, compare(booleanarray3, booleanarray3.length,
        booleanarray1, booleanarray1.length, epsilon));
    assertEquals(1, compare(booleanarray1, booleanarray1.length,
        booleanarray4, booleanarray4.length, epsilon));
    assertEquals(-1, compare(booleanarray4, booleanarray4.length,
        booleanarray1, booleanarray1.length, epsilon));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE},
        {Character.MAX_VALUE, Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, (char) 3}};
    final char[][] chararray4 = {{(char) 0, (char) 1},
        {Character.MIN_VALUE / 2, Character.MIN_VALUE}};
    assertEquals(0,
        compare(chararray1, chararray1.length, chararray2,
            chararray2.length, epsilon));
    assertEquals(-1, compare(chararray1, chararray1.length, chararray3,
            chararray3.length, epsilon));
    assertEquals(1,
        compare(chararray3, chararray3.length, chararray1,
            chararray1.length, epsilon));
    assertEquals(1,
        compare(chararray1, chararray1.length, chararray4,
            chararray4.length, epsilon));
    assertEquals(-1, compare(chararray4, chararray4.length, chararray1,
            chararray1.length, epsilon));
    assertEquals(0, compare(chararray1, 1, chararray4, 1, epsilon));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE},
        {Byte.MAX_VALUE, Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, (byte) 3}};
    final byte[][] bytearray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE / 2, Byte.MIN_VALUE}};
    assertEquals(0, compare(bytearray1, bytearray1.length, bytearray2, bytearray2.length,
            epsilon));
    assertEquals(-1, compare(bytearray1, bytearray1.length, bytearray3, bytearray3.length,
            epsilon));
    assertEquals(1, compare(bytearray3, bytearray3.length, bytearray1, bytearray1.length,
            epsilon));
    assertEquals(1, compare(bytearray1, bytearray1.length, bytearray4, bytearray4.length,
            epsilon));
    assertEquals(-1, compare(bytearray4, bytearray4.length, bytearray1, bytearray1.length,
            epsilon));
    assertEquals(0, compare(bytearray1, 1, bytearray4, 1, epsilon));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE},
        {Short.MAX_VALUE, Short.MAX_VALUE}};
    final short[][] shortarray3 = {{(short) 0, (short) 3}};
    final short[][] shortarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE / 2, Short.MIN_VALUE}};
    assertEquals(0, compare(shortarray1, shortarray1.length, shortarray2,
            shortarray2.length, epsilon));
    assertEquals(-1, compare(shortarray1, shortarray1.length, shortarray3,
            shortarray3.length, epsilon));
    assertEquals(1, compare(shortarray3, shortarray3.length, shortarray1,
            shortarray1.length, epsilon));
    assertEquals(1, compare(shortarray1, shortarray1.length, shortarray4,
            shortarray4.length, epsilon));
    assertEquals(-1, compare(shortarray4, shortarray4.length, shortarray1,
            shortarray1.length, epsilon));
    assertEquals(0,
        compare(shortarray1, 1, shortarray4, 1, epsilon));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE},
        {Integer.MAX_VALUE, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, 3}};
    final int[][] intarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE / 2, Integer.MIN_VALUE}};
    assertEquals(0, compare(intarray1, intarray1.length, intarray2, intarray2.length,
            epsilon));
    assertEquals(-1, compare(intarray1, intarray1.length, intarray3, intarray3.length,
            epsilon));
    assertEquals(1, compare(intarray3, intarray3.length, intarray1, intarray1.length,
            epsilon));
    assertEquals(1, compare(intarray1, intarray1.length, intarray4, intarray4.length,
            epsilon));
    assertEquals(-1, compare(intarray4, intarray4.length, intarray1, intarray1.length,
            epsilon));
    assertEquals(0, compare(intarray1, 1, intarray4, 1, epsilon));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE},
        {Long.MAX_VALUE, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, 3}};
    final long[][] longarray4 = {{0, -1, 1},
        {Long.MIN_VALUE / 2, Long.MIN_VALUE}};
    assertEquals(0, compare(longarray1, longarray1.length, longarray2, longarray2.length,
            epsilon));
    assertEquals(-1, compare(longarray1, longarray1.length, longarray3, longarray3.length,
            epsilon));
    assertEquals(1, compare(longarray3, longarray3.length, longarray1, longarray1.length,
            epsilon));
    assertEquals(1, compare(longarray1, longarray1.length, longarray4, longarray4.length,
            epsilon));
    assertEquals(-1, compare(longarray4, longarray4.length, longarray1, longarray1.length,
            epsilon));
    assertEquals(0, compare(longarray1, 1, longarray4, 1, epsilon));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE},
        {Float.MAX_VALUE, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, 3}};
    final float[][] floatarray4 = {{0, -1, 1},
        {Float.MIN_VALUE / 2, Float.MIN_VALUE}};
    assertEquals(0, compare(floatarray1, floatarray1.length, floatarray2,
            floatarray2.length, epsilon));
    assertEquals(-1, compare(floatarray1, floatarray1.length, floatarray3,
            floatarray3.length, epsilon));
    assertEquals(1, compare(floatarray3, floatarray3.length, floatarray1,
            floatarray1.length, epsilon));
    assertEquals(1, compare(floatarray1, floatarray1.length, floatarray4,
            floatarray4.length, epsilon));
    assertEquals(-1, compare(floatarray4, floatarray4.length, floatarray1,
            floatarray1.length, epsilon));
    assertEquals(0,
        compare(floatarray1, 1, floatarray4, 1, epsilon));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, 3}};
    final double[][] doublearray4 = {{0, -1, 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE}};
    assertEquals(0, compare(doublearray1, doublearray1.length, doublearray2,
            doublearray2.length, epsilon));
    assertEquals(-1, compare(doublearray1, doublearray1.length, doublearray3,
            doublearray3.length, epsilon));
    assertEquals(1, compare(doublearray3, doublearray3.length, doublearray1,
            doublearray1.length, epsilon));
    assertEquals(1, compare(doublearray1, doublearray1.length, doublearray4,
            doublearray4.length, epsilon));
    assertEquals(-1, compare(doublearray4, doublearray4.length, doublearray1,
            doublearray1.length, epsilon));
    assertEquals(0,
        compare(doublearray1, 1, doublearray4, 1, epsilon));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    final String[][] stringarray4 = {{"long", "long"}};
    assertEquals(0, compare(stringarray1, stringarray1.length, stringarray2,
            stringarray2.length, epsilon));
    assertEquals(-1, compare(stringarray2, stringarray2.length, stringarray3,
            stringarray3.length, epsilon));
    assertEquals(1, compare(stringarray3, stringarray3.length, stringarray2,
            stringarray2.length, epsilon));
    assertEquals(1, compare(stringarray2, stringarray2.length, stringarray4,
            stringarray4.length, epsilon));
    assertEquals(-1, compare(stringarray4, stringarray4.length, stringarray2,
            stringarray2.length, epsilon));
    assertEquals(0,
        compare(stringarray1, 1, stringarray4, 1, epsilon));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false, true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    final Boolean[][] booleanobjectarray4 = {{true, false}};
    assertEquals(0,
        compare(booleanobjectarray1, booleanobjectarray1.length,
            booleanobjectarray2, booleanobjectarray2.length, epsilon));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray2.length,
            booleanobjectarray3, booleanobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(booleanobjectarray3, booleanobjectarray3.length,
            booleanobjectarray2, booleanobjectarray2.length, epsilon));
    assertEquals(1,
        compare(booleanobjectarray2, booleanobjectarray2.length,
            booleanobjectarray4, booleanobjectarray4.length, epsilon));
    assertEquals(-1,
        compare(booleanobjectarray4, booleanobjectarray4.length,
            booleanobjectarray2, booleanobjectarray2.length, epsilon));
    assertEquals(0, compare(booleanobjectarray1, 1, booleanobjectarray4, 1, epsilon));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, (char) 3}};
    final Character[][] charobjectarray4 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE}};
    assertEquals(0,
        compare(charobjectarray1, charobjectarray1.length,
            charobjectarray2, charobjectarray2.length, epsilon));
    assertEquals(1,
        compare(charobjectarray3, charobjectarray3.length,
            charobjectarray2, charobjectarray2.length, epsilon));
    assertEquals(-1,
        compare(charobjectarray2, charobjectarray2.length,
            charobjectarray3, charobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(charobjectarray4, charobjectarray4.length,
            charobjectarray2, charobjectarray2.length, epsilon));
    assertEquals(1,
        compare(charobjectarray2, charobjectarray2.length,
            charobjectarray4, charobjectarray4.length, epsilon));
    assertEquals(0, compare(charobjectarray1, 1, charobjectarray4, 1, epsilon));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, (byte) 2}};
    final Byte[][] byteobjectarray4 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(byteobjectarray1, byteobjectarray1.length,
            byteobjectarray2, byteobjectarray2.length, epsilon));
    assertEquals(1,
        compare(byteobjectarray3, byteobjectarray3.length,
            byteobjectarray2, byteobjectarray2.length, epsilon));
    assertEquals(-1,
        compare(byteobjectarray2, byteobjectarray2.length,
            byteobjectarray3, byteobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(byteobjectarray4, byteobjectarray4.length,
            byteobjectarray2, byteobjectarray2.length, epsilon));
    assertEquals(1,
        compare(byteobjectarray2, byteobjectarray2.length,
            byteobjectarray4, byteobjectarray4.length, epsilon));
    assertEquals(0, compare(byteobjectarray1, 1, byteobjectarray4, 1, epsilon));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, (short) 2}};
    final Short[][] shortobjectarray4 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(shortobjectarray1, shortobjectarray1.length,
            shortobjectarray2, shortobjectarray2.length, epsilon));
    assertEquals(1,
        compare(shortobjectarray3, shortobjectarray3.length,
            shortobjectarray2, shortobjectarray2.length, epsilon));
    assertEquals(-1,
        compare(shortobjectarray2, shortobjectarray2.length,
            shortobjectarray3, shortobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(shortobjectarray4, shortobjectarray4.length,
            shortobjectarray2, shortobjectarray2.length, epsilon));
    assertEquals(1,
        compare(shortobjectarray2, shortobjectarray2.length,
            shortobjectarray4, shortobjectarray4.length, epsilon));
    assertEquals(0, compare(shortobjectarray1, 1, shortobjectarray4, 1, epsilon));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)},
        {(Integer.MAX_VALUE / 2), Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, 3}};
    final Integer[][] integerarray4 = {{0, -1, 1},
        {Integer.MIN_VALUE, (Integer.MIN_VALUE / 2)}};
    assertEquals(0, compare(integerarray1, integerarray1.length,
        integerarray2, integerarray2.length, epsilon));
    assertEquals(1, compare(integerarray3, integerarray3.length,
        integerarray2, integerarray2.length, epsilon));
    assertEquals(-1, compare(integerarray2, integerarray2.length,
        integerarray3, integerarray3.length, epsilon));
    assertEquals(-1, compare(integerarray4, integerarray4.length,
        integerarray2, integerarray2.length, epsilon));
    assertEquals(1, compare(integerarray2, integerarray2.length,
        integerarray4, integerarray4.length, epsilon));
    assertEquals(0, compare(integerarray1, 1, integerarray4, 1, epsilon));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)},
        {(Long.MAX_VALUE / 2), Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, 2L}};
    final Long[][] longobjectarray4 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, (Long.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(longobjectarray1, longobjectarray1.length,
            longobjectarray2, longobjectarray2.length, epsilon));
    assertEquals(1,
        compare(longobjectarray3, longobjectarray3.length,
            longobjectarray2, longobjectarray2.length, epsilon));
    assertEquals(-1,
        compare(longobjectarray2, longobjectarray2.length,
            longobjectarray3, longobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(longobjectarray4, longobjectarray4.length,
            longobjectarray2, longobjectarray2.length, epsilon));
    assertEquals(1,
        compare(longobjectarray2, longobjectarray2.length,
            longobjectarray4, longobjectarray4.length, epsilon));
    assertEquals(0, compare(longobjectarray1, 1, longobjectarray4, 1, epsilon));

    final Float[][] floatobjectarray1 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)},
        {(Float.MAX_VALUE / 2), Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{0f, 2f}};
    final Float[][] floatobjectarray4 = {{0f, -1f, 1f},
        {Float.MIN_VALUE, (Float.MIN_VALUE / 2)}};
    assertEquals(0,
        compare(floatobjectarray1, floatobjectarray1.length,
            floatobjectarray2, floatobjectarray2.length, epsilon));
    assertEquals(1,
        compare(floatobjectarray3, floatobjectarray3.length,
            floatobjectarray2, floatobjectarray2.length, epsilon));
    assertEquals(-1,
        compare(floatobjectarray2, floatobjectarray2.length,
            floatobjectarray3, floatobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(floatobjectarray4, floatobjectarray4.length,
            floatobjectarray2, floatobjectarray2.length, epsilon));
    assertEquals(1,
        compare(floatobjectarray2, floatobjectarray2.length,
            floatobjectarray4, floatobjectarray4.length, epsilon));
    assertEquals(0, compare(floatobjectarray1, 1, floatobjectarray4, 1, epsilon));

    final Double[][] doubleobjectarray1 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE},
        {(Double.MAX_VALUE / 2), Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{0.0, 2.0}};
    final Double[][] doubleobjectarray4 = {{0.0, -1.0, 1.0},
        {(Double.MIN_VALUE / 2), Double.MIN_VALUE}};
    assertEquals(0,
        compare(doubleobjectarray1, doubleobjectarray1.length,
            doubleobjectarray2, doubleobjectarray2.length, epsilon));
    assertEquals(1,
        compare(doubleobjectarray3, doubleobjectarray3.length,
            doubleobjectarray2, doubleobjectarray2.length, epsilon));
    assertEquals(-1,
        compare(doubleobjectarray2, doubleobjectarray2.length,
            doubleobjectarray3, doubleobjectarray3.length, epsilon));
    assertEquals(-1,
        compare(doubleobjectarray4, doubleobjectarray4.length,
            doubleobjectarray2, doubleobjectarray2.length, epsilon));
    assertEquals(1,
        compare(doubleobjectarray2, doubleobjectarray2.length,
            doubleobjectarray4, doubleobjectarray4.length, epsilon));
    assertEquals(0, compare(doubleobjectarray1, 1, doubleobjectarray4, 1, epsilon));

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class}, {TestClassB.class}};
    final Class<?>[][] classarray4 = {{TestClassA.class}, {TestClassA.class},
        {TestClassA.class}};
    assertEquals(0, compare(classarray1, classarray1.length, classarray2,
            classarray2.length, epsilon));
    assertEquals(1, compare(classarray3, classarray3.length, classarray2,
            classarray2.length, epsilon));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray3,
            classarray3.length, epsilon));
    assertEquals(1, compare(classarray4, classarray4.length, classarray2,
            classarray2.length, epsilon));
    assertEquals(-1, compare(classarray2, classarray2.length, classarray4,
            classarray4.length, epsilon));
    assertEquals(0,
        compare(classarray1, 1, classarray4, 1, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec, datea}, {dateb}};
    final Date[][] datearray4 = {{datea, dateb}};
    assertEquals(0,
        compare(datearray1, datearray1.length, datearray2,
            datearray2.length, epsilon));
    assertEquals(1,
        compare(datearray3, datearray3.length, datearray2,
            datearray2.length, epsilon));
    assertEquals(-1, compare(datearray2, datearray2.length, datearray3,
            datearray3.length, epsilon));
    assertEquals(-1, compare(datearray4, datearray4.length, datearray2,
            datearray2.length, epsilon));
    assertEquals(1,
        compare(datearray2, datearray2.length, datearray4,
            datearray4.length, epsilon));
    assertEquals(0, compare(datearray1, 1, datearray4, 1, epsilon));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0])},
        {new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray4 = {{new BigInteger(bytearray2[0])}};
    assertEquals(0,
        compare(bigintegerarray1, bigintegerarray1.length,
            bigintegerarray2, bigintegerarray2.length, epsilon));
    assertEquals(-1,
        compare(bigintegerarray3, bigintegerarray3.length,
            bigintegerarray2, bigintegerarray2.length, epsilon));
    assertEquals(1,
        compare(bigintegerarray2, bigintegerarray2.length,
            bigintegerarray3, bigintegerarray3.length, epsilon));
    assertEquals(-1,
        compare(bigintegerarray4, bigintegerarray4.length,
            bigintegerarray2, bigintegerarray2.length, epsilon));
    assertEquals(1,
        compare(bigintegerarray2, bigintegerarray2.length,
            bigintegerarray4, bigintegerarray4.length, epsilon));
    assertEquals(0, compare(bigintegerarray1, 1, bigintegerarray4, 1, epsilon));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    final BigDecimal[][] bigdecimalarray4 = {
        {new BigDecimal(Integer.MIN_VALUE)}};
    assertEquals(0,
        compare(bigdecimalarray1, bigdecimalarray1.length,
            bigdecimalarray2, bigdecimalarray2.length, epsilon));
    assertEquals(-1,
        compare(bigdecimalarray2, bigdecimalarray2.length,
            bigdecimalarray3, bigdecimalarray3.length, epsilon));
    assertEquals(1,
        compare(bigdecimalarray3, bigdecimalarray3.length,
            bigdecimalarray2, bigdecimalarray2.length, epsilon));
    assertEquals(1,
        compare(bigdecimalarray2, bigdecimalarray2.length,
            bigdecimalarray4, bigdecimalarray4.length, epsilon));
    assertEquals(-1,
        compare(bigdecimalarray4, bigdecimalarray4.length,
            bigdecimalarray2, bigdecimalarray2.length, epsilon));
    assertEquals(0, compare(bigdecimalarray1, 1, bigdecimalarray4, 1, epsilon));

    final Boolean[][][] objectarray1 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray2 = {{{true}, {false}}, {{true, true}},
        {{false, false}, {true}}};
    final Boolean[][][] objectarray3 = {{{true}, {false}}, {{false, true}}};
    final Boolean[][][] objectarray4 = {{{true}, {false}}, {{true, true}}};
    assertEquals(0, compare(objectarray1, objectarray1.length, objectarray2,
            objectarray2.length, epsilon));
    assertEquals(-1, compare(objectarray3, objectarray3.length, objectarray2,
            objectarray2.length, epsilon));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray3,
            objectarray3.length, epsilon));
    assertEquals(-1, compare(objectarray4, objectarray4.length, objectarray2,
            objectarray2.length, epsilon));
    assertEquals(1, compare(objectarray2, objectarray2.length, objectarray4,
            objectarray4.length, epsilon));
    assertEquals(0,
        compare(objectarray1, 1, objectarray4, 1, epsilon));

    final ArrayList<Character> arraylist1 = new ArrayList<>();
    final ArrayList<Character> arraylist2 = new ArrayList<>();
    final Object[] col1 = {arraylist1, arraylist2};
    arraylist1.add('a');
    arraylist2.add('b');
    arraylist1.add('c');
    final ArrayList<Character> arraylist3 = new ArrayList<>();
    final ArrayList<Character> arraylist4 = new ArrayList<>();
    final Object[] col2 = {arraylist3, arraylist4};
    arraylist3.add('a');
    arraylist4.add('b');
    arraylist3.add('c');
    final ArrayList<Character> arraylist5 = new ArrayList<>();
    final Object[] col3 = {arraylist5};
    arraylist5.add('a');
    arraylist5.add('c');
    assertEquals(0, compare(col1, col2, epsilon));
    assertEquals(-1, compare(col3, col2, epsilon));
    assertEquals(1, compare(col2, col3, epsilon));
  }

  @Test
  public void testObjectComparator() {
    final Comparator<String> strComparator = Comparator.naturalOrder();
    final Comparator<Character> charComparator = Comparator.naturalOrder();
    final Comparator<Boolean> boolComparator = Comparator.naturalOrder();
    final Comparator<Integer> intComparator = Comparator.naturalOrder();
    final Comparator<Byte> byteComparator = Comparator.naturalOrder();
    final Comparator<Long> longComparator = Comparator.naturalOrder();
    final Comparator<Short> shortComparator = Comparator.naturalOrder();
    final Comparator<Float> floatComparator = Comparator.naturalOrder();
    final Comparator<Double> doubleComparator = Comparator.naturalOrder();
    final Comparator<Date> dateComparator = Comparator.naturalOrder();
    final Comparator<BigInteger> bigIntComparator = Comparator.naturalOrder();
    final Comparator<BigDecimal> bigDecComparator = Comparator.naturalOrder();

    final String value1 = "alongtime";
    String value2 = "alonglongago";

    assertEquals(0, compare(value1, value1, strComparator));
    assertTrue(compare(value1, value2, strComparator) != 0);
    value2 = "along";
    assertEquals(1, compare(value1, value2, strComparator));
    assertEquals(-1, compare(value2, value1, strComparator));
    value2 = "zoo";
    assertEquals(-1, compare(value1, value2, strComparator));
    assertEquals(1, compare(value2, value1, strComparator));

    final String[] stringarray1 = {"long", "long", "ago"};
    final String[] stringarray2 = {"long", "long", "ago"};
    final String[] stringarray3 = {"long", "time", "no", "see"};
    final String[] stringarray4 = {"long", "long"};
    assertEquals(0, compare(stringarray1, stringarray2, strComparator));
    assertEquals(-1, compare(stringarray2, stringarray3, strComparator));
    assertEquals(1, compare(stringarray3, stringarray2, strComparator));
    assertEquals(1, compare(stringarray2, stringarray4, strComparator));
    assertEquals(-1, compare(stringarray4, stringarray2, strComparator));

    final Boolean[] booleanobjectarray1 = {true, false, false, true};
    final Boolean[] booleanobjectarray2 = {true, false, false, true};
    final Boolean[] booleanobjectarray3 = {false, true, true};
    final Boolean[] booleanobjectarray4 = {true, false};
    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray2, boolComparator));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray3, boolComparator));
    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray2, boolComparator));
    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray4, boolComparator));
    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray2, boolComparator));

    final Character[] charobjectarray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray3 = {(char) 0, (char) 3};
    final Character[] charobjectarray4 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE};
    assertEquals(0, compare(charobjectarray1, charobjectarray2, charComparator));
    assertEquals(1, compare(charobjectarray3, charobjectarray2, charComparator));
    assertEquals(-1, compare(charobjectarray2, charobjectarray3, charComparator));
    assertEquals(-1, compare(charobjectarray4, charobjectarray2, charComparator));
    assertEquals(1, compare(charobjectarray2, charobjectarray4, charComparator));

    final Short[] shortobjectarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray3 = {(short) 0, (short) 2};
    final Short[] shortobjectarray4 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)};
    assertEquals(0, compare(shortobjectarray1, shortobjectarray2, shortComparator));
    assertEquals(1, compare(shortobjectarray3, shortobjectarray2, shortComparator));
    assertEquals(-1, compare(shortobjectarray2, shortobjectarray3, shortComparator));
    assertEquals(-1, compare(shortobjectarray4, shortobjectarray2, shortComparator));
    assertEquals(1, compare(shortobjectarray2, shortobjectarray4, shortComparator));

    final Integer[] integerarray1 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final Integer[] integerarray2 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2), (Integer.MAX_VALUE / 2),
        Integer.MAX_VALUE};
    final Integer[] integerarray3 = {0, 3};
    final Integer[] integerarray4 = {0, -1, 1, Integer.MIN_VALUE,
        (Integer.MIN_VALUE / 2)};
    assertEquals(0, compare(integerarray1, integerarray2, intComparator));
    assertEquals(1, compare(integerarray3, integerarray2, intComparator));
    assertEquals(-1, compare(integerarray2, integerarray3, intComparator));
    assertEquals(-1, compare(integerarray4, integerarray2, intComparator));
    assertEquals(1, compare(integerarray2, integerarray4, intComparator));

    final Long[] longobjectarray1 = {0L, -1L, 1L, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final Long[] longobjectarray2 = {0L, -1L, 1L, Long.MIN_VALUE,
        (Long.MIN_VALUE / 2), (Long.MAX_VALUE / 2),
        Long.MAX_VALUE};
    final Long[] longobjectarray3 = {0L, 2L};
    final Long[] longobjectarray4 = {0L, -1L, 1L, Long.MIN_VALUE};
    assertEquals(0, compare(longobjectarray1, longobjectarray2, longComparator));
    assertEquals(1, compare(longobjectarray3, longobjectarray2, longComparator));
    assertEquals(-1, compare(longobjectarray2, longobjectarray3, longComparator));
    assertEquals(-1, compare(longobjectarray4, longobjectarray2, longComparator));
    assertEquals(1, compare(longobjectarray2, longobjectarray4, longComparator));

    final Float[] floatobjectarray1 = {0f, -1f, 1f,
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray2 = {0f, -1f, 1f,
        Float.MIN_VALUE, (Float.MIN_VALUE / 2),
        (Float.MAX_VALUE / 2), Float.MAX_VALUE};
    final Float[] floatobjectarray3 = {0f, 2f};
    final Float[] floatobjectarray4 = {0f, -1f, 1f,
        Float.MIN_VALUE};
    assertEquals(0, compare(floatobjectarray1, floatobjectarray2, floatComparator));
    assertEquals(1, compare(floatobjectarray3, floatobjectarray2, floatComparator));
    assertEquals(-1, compare(floatobjectarray2, floatobjectarray3, floatComparator));
    assertEquals(-1, compare(floatobjectarray4, floatobjectarray2, floatComparator));
    assertEquals(1, compare(floatobjectarray2, floatobjectarray4, floatComparator));

    final Double[] doubleobjectarray1 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final Double[] doubleobjectarray2 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE,
        (Double.MAX_VALUE / 2), Double.MAX_VALUE};
    final Double[] doubleobjectarray3 = {0.0, 2.0};
    final Double[] doubleobjectarray4 = {0.0, -1.0, 1.0,
        (Double.MIN_VALUE / 2), Double.MIN_VALUE};
    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray2, doubleComparator));
    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray2, doubleComparator));
    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray3, doubleComparator));
    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray2, doubleComparator));
    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray4, doubleComparator));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = {datea, dateb, datec};
    final Date[] datearray2 = {datea, dateb, datec};
    final Date[] datearray3 = {datec, datea, dateb};
    final Date[] datearray4 = {datea};
    assertEquals(0, compare(datearray1, datearray2, dateComparator));
    assertEquals(1, compare(datearray3, datearray2, dateComparator));
    assertEquals(-1, compare(datearray2, datearray3, dateComparator));
    assertEquals(-1, compare(datearray4, datearray2, dateComparator));
    assertEquals(1, compare(datearray2, datearray4, dateComparator));

    //    Boolean[][] objectarray1 = { { true, false }, { true, true },
    //        { false, false, true } };
    //    Boolean[][] objectarray2 = { { true, false }, { true, true },
    //        { false, false, true } };
    //    Boolean[][] objectarray3 = { { true, false }, { true, true } };
    //    Boolean[][] objectarray4 = { { true, false }, { false, true } };
    //    assertEquals(0, Comparison
    //            .compare(objectarray1, objectarray2, boolComparator));
    //    assertEquals(-1, Comparison
    //            .compare(objectarray3, objectarray2, boolComparator));
    //    assertEquals(1, Comparison
    //            .compare(objectarray2, objectarray3, boolComparator));
    //    assertEquals(-1, Comparison
    //            .compare(objectarray4, objectarray2, boolComparator));
    //    assertEquals(1, Comparison
    //            .compare(objectarray2, objectarray4, boolComparator));
  }

  //  @Test
  //  public void testObjectArrayComparator() {
  //    StringComparator comparator = new StringComparator();
  //
  //    Object[] array1 = { new TestClassA(), new TestClassA() };
  //    Object[] array2 = { new TestClassA(), new TestClassA() };
  //
  //    assertEquals(0, compare(array1, array1, comparator));
  //    array2 = null;
  //    assertEquals(1, compare(array1, array2, comparator));
  //    assertEquals(-1, compare(array2, array1, comparator));
  //    array1 = null;
  //    assertEquals(0, compare(array1, array2, comparator));
  //
  //    boolean[][] booleanarray1 = { { true, false }, { false, true } };
  //    boolean[][] booleanarray2 = { { true, false }, { false, true } };
  //    boolean[][] booleanarray3 = { { false, true }, { true } };
  //    boolean[][] booleanarray4 = { { true, false } };
  //    assertEquals(0, compare(booleanarray1, booleanarray2, comparator));
  //    assertEquals(1, compare(booleanarray1, booleanarray3, comparator));
  //    assertEquals(-1, compare(booleanarray3, booleanarray1, comparator));
  //    assertEquals(1, compare(booleanarray1, booleanarray4, comparator));
  //    assertEquals(-1, compare(booleanarray4, booleanarray1, comparator));
  //
  //    char[][] chararray1 = { { (char) 0, (char) 1 },
  //        { Character.MIN_VALUE / 2, Character.MIN_VALUE },
  //        { Character.MAX_VALUE, Character.MAX_VALUE } };
  //    char[][] chararray2 = { { (char) 0, (char) 1 },
  //        { Character.MIN_VALUE / 2, Character.MIN_VALUE },
  //        { Character.MAX_VALUE, Character.MAX_VALUE } };
  //    char[][] chararray3 = { { (char) 0, (char) 3 } };
  //    char[][] chararray4 = { { (char) 0, (char) 1 },
  //        { Character.MIN_VALUE / 2, Character.MIN_VALUE } };
  //    assertEquals(0, compare(chararray1, chararray2, comparator));
  //    assertEquals(-1, compare(chararray1, chararray3, comparator));
  //    assertEquals(1, compare(chararray3, chararray1, comparator));
  //    assertEquals(1, compare(chararray1, chararray4, comparator));
  //    assertEquals(-1, compare(chararray4, chararray1, comparator));
  //
  //    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
  //        { Byte.MAX_VALUE, Byte.MAX_VALUE } };
  //    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
  //        { Byte.MAX_VALUE, Byte.MAX_VALUE } };
  //    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
  //    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
  //    assertEquals(0, compare(bytearray1, bytearray2, comparator));
  //    assertEquals(-1, compare(bytearray1, bytearray3, comparator));
  //    assertEquals(1, compare(bytearray3, bytearray1, comparator));
  //    assertEquals(1, compare(bytearray1, bytearray4, comparator));
  //    assertEquals(-1, compare(bytearray4, bytearray1, comparator));
  //
  //    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE / 2, Short.MIN_VALUE },
  //        { Short.MAX_VALUE, Short.MAX_VALUE } };
  //    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE / 2, Short.MIN_VALUE },
  //        { Short.MAX_VALUE, Short.MAX_VALUE } };
  //    short[][] shortarray3 = { { (short) 0, (short) 3 } };
  //    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE / 2, Short.MIN_VALUE } };
  //    assertEquals(0, compare(shortarray1, shortarray2, comparator));
  //    assertEquals(-1, compare(shortarray1, shortarray3, comparator));
  //    assertEquals(1, compare(shortarray3, shortarray1, comparator));
  //    assertEquals(1, compare(shortarray1, shortarray4, comparator));
  //    assertEquals(-1, compare(shortarray4, shortarray1, comparator));
  //
  //    int[][] intarray1 = { {  0,  -1,  1 },
  //        {  Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
  //        {  Integer.MAX_VALUE, Integer.MAX_VALUE } };
  //    int[][] intarray2 = { {  0,  -1,  1 },
  //        {  Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
  //        {  Integer.MAX_VALUE, Integer.MAX_VALUE } };
  //    int[][] intarray3 = { {  0,  3 } };
  //    int[][] intarray4 = { {  0,  -1,  1 },
  //        {  Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
  //    assertEquals(0, compare(intarray1, intarray2, comparator));
  //    assertEquals(-1, compare(intarray1, intarray3, comparator));
  //    assertEquals(1, compare(intarray3, intarray1, comparator));
  //    assertEquals(1, compare(intarray1, intarray4, comparator));
  //    assertEquals(-1, compare(intarray4, intarray1, comparator));
  //
  //    long[][] longarray1 = { {  0,  -1,  1 },
  //        {  Long.MIN_VALUE / 2, Long.MIN_VALUE },
  //        {  Long.MAX_VALUE, Long.MAX_VALUE } };
  //    long[][] longarray2 = { {  0,  -1,  1 },
  //        {  Long.MIN_VALUE / 2, Long.MIN_VALUE },
  //        {  Long.MAX_VALUE, Long.MAX_VALUE } };
  //    long[][] longarray3 = { {  0,  3 } };
  //    long[][] longarray4 = { {  0,  -1,  1 },
  //        {  Long.MIN_VALUE / 2, Long.MIN_VALUE } };
  //    assertEquals(0, compare(longarray1, longarray2, comparator));
  //    assertEquals(-1, compare(longarray1, longarray3, comparator));
  //    assertEquals(1, compare(longarray3, longarray1, comparator));
  //    assertEquals(1, compare(longarray1, longarray4, comparator));
  //    assertEquals(-1, compare(longarray4, longarray1, comparator));
  //
  //    float[][] floatarray1 = { {  0,  -1,  1 },
  //        {  Float.MIN_VALUE / 2, Float.MIN_VALUE },
  //        {  Float.MAX_VALUE, Float.MAX_VALUE } };
  //    float[][] floatarray2 = { {  0,  -1,  1 },
  //        {  Float.MIN_VALUE / 2, Float.MIN_VALUE },
  //        {  Float.MAX_VALUE, Float.MAX_VALUE } };
  //    float[][] floatarray3 = { {  0,  3 } };
  //    float[][] floatarray4 = { {  0,  -1,  1 },
  //        {  Float.MIN_VALUE / 2, Float.MIN_VALUE } };
  //    assertEquals(0, compare(floatarray1, floatarray2, comparator));
  //    assertEquals(-1, compare(floatarray1, floatarray3, comparator));
  //    assertEquals(1, compare(floatarray3, floatarray1, comparator));
  //    assertEquals(1, compare(floatarray1, floatarray4, comparator));
  //    assertEquals(-1, compare(floatarray4, floatarray1, comparator));
  //
  //    double[][] doublearray1 = { {  0,  -1,  1 },
  //        {  Double.MIN_VALUE / 2, Double.MIN_VALUE },
  //        {  Double.MAX_VALUE, Double.MAX_VALUE } };
  //    double[][] doublearray2 = { {  0,  -1,  1 },
  //        {  Double.MIN_VALUE / 2, Double.MIN_VALUE },
  //        {  Double.MAX_VALUE, Double.MAX_VALUE } };
  //    double[][] doublearray3 = { {  0,  3 } };
  //    double[][] doublearray4 = { {  0,  -1,  1 },
  //        {  Double.MIN_VALUE / 2, Double.MIN_VALUE } };
  //    assertEquals(0, compare(doublearray1, doublearray2, comparator));
  //    assertEquals(-1, compare(doublearray1, doublearray3, comparator));
  //    assertEquals(1, compare(doublearray3, doublearray1, comparator));
  //    assertEquals(1, compare(doublearray1, doublearray4, comparator));
  //    assertEquals(-1, compare(doublearray4, doublearray1, comparator));
  //
  //    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
  //    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
  //    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
  //    String[][] stringarray4 = { { "long", "long" } };
  //    assertEquals(0, compare(stringarray1, stringarray2, comparator));
  //    assertEquals(-1, compare(stringarray2, stringarray3, comparator));
  //    assertEquals(1, compare(stringarray3, stringarray2, comparator));
  //    assertEquals(1, compare(stringarray2, stringarray4, comparator));
  //    assertEquals(-1, compare(stringarray4, stringarray2, comparator));
  //
  //    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
  //    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
  //    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
  //    Boolean[][] booleanobjectarray4 = { { true, false } };
  //    assertEquals(0, Comparison
  //            .compare(booleanobjectarray1, booleanobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(booleanobjectarray2, booleanobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(booleanobjectarray3, booleanobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(booleanobjectarray2, booleanobjectarray4, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(booleanobjectarray4, booleanobjectarray2, comparator));
  //
  //    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
  //        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
  //        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
  //    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
  //        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
  //        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
  //    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
  //    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
  //        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
  //    assertEquals(0, Comparison
  //            .compare(charobjectarray1, charobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(charobjectarray3, charobjectarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(charobjectarray2, charobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(charobjectarray4, charobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(charobjectarray2, charobjectarray4, comparator));
  //
  //    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
  //        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
  //    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
  //        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
  //    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
  //    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
  //    assertEquals(0, Comparison
  //            .compare(byteobjectarray1, byteobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(byteobjectarray3, byteobjectarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(byteobjectarray2, byteobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(byteobjectarray4, byteobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(byteobjectarray2, byteobjectarray4, comparator));
  //
  //    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
  //        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
  //    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
  //        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
  //    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
  //    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
  //    assertEquals(0, Comparison
  //            .compare(shortobjectarray1, shortobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(shortobjectarray3, shortobjectarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(shortobjectarray2, shortobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(shortobjectarray4, shortobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(shortobjectarray2, shortobjectarray4, comparator));
  //
  //    Integer[][] integerarray1 = { {  0,  -1,  1 },
  //        { Integer.MIN_VALUE,  (Integer.MIN_VALUE / 2) },
  //        {  (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
  //    Integer[][] integerarray2 = { {  0,  -1,  1 },
  //        { Integer.MIN_VALUE,  (Integer.MIN_VALUE / 2) },
  //        {  (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
  //    Integer[][] integerarray3 = { {  0,  3 } };
  //    Integer[][] integerarray4 = { {  0,  -1,  1 },
  //        { Integer.MIN_VALUE,  (Integer.MIN_VALUE / 2) } };
  //    assertEquals(0, compare(integerarray1, integerarray2, comparator));
  //    assertEquals(1, compare(integerarray3, integerarray2, comparator));
  //    assertEquals(-1, compare(integerarray2, integerarray3, comparator));
  //    assertEquals(-1, compare(integerarray4, integerarray2, comparator));
  //    assertEquals(1, compare(integerarray2, integerarray4, comparator));
  //
  //    Long[][] longobjectarray1 = { {  0L,  -1L,  1L },
  //        { Long.MIN_VALUE,  (Long.MIN_VALUE / 2) },
  //        {  (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
  //    Long[][] longobjectarray2 = { {  0L,  -1L,  1L },
  //        { Long.MIN_VALUE,  (Long.MIN_VALUE / 2) },
  //        {  (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
  //    Long[][] longobjectarray3 = { {  0L,  2L } };
  //    Long[][] longobjectarray4 = { {  0L,  -1L,  1L },
  //        { Long.MIN_VALUE,  (Long.MIN_VALUE / 2) } };
  //    assertEquals(0, Comparison
  //            .compare(longobjectarray1, longobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(longobjectarray3, longobjectarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(longobjectarray2, longobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(longobjectarray4, longobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(longobjectarray2, longobjectarray4, comparator));
  //
  //    Float[][] floatobjectarray1 = { {  0f,  -1f,  1f },
  //        { Float.MIN_VALUE,  (Float.MIN_VALUE / 2) },
  //        {  (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
  //    Float[][] floatobjectarray2 = { {  0f,  -1f,  1f },
  //        { Float.MIN_VALUE,  (Float.MIN_VALUE / 2) },
  //        {  (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
  //    Float[][] floatobjectarray3 = { {  0f,  2f } };
  //    Float[][] floatobjectarray4 = { {  0f,  -1f,  1f },
  //        { Float.MIN_VALUE,  (Float.MIN_VALUE / 2) } };
  //    assertEquals(0, Comparison
  //            .compare(floatobjectarray1, floatobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(floatobjectarray3, floatobjectarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(floatobjectarray2, floatobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(floatobjectarray4, floatobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(floatobjectarray2, floatobjectarray4, comparator));
  //
  //    Double[][] doubleobjectarray1 = { {  0.0,  -1.0,  1.0 },
  //        {  (Double.MIN_VALUE / 2), Double.MIN_VALUE },
  //        {  (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
  //    Double[][] doubleobjectarray2 = { {  0.0,  -1.0,  1.0 },
  //        {  (Double.MIN_VALUE / 2), Double.MIN_VALUE },
  //        {  (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
  //    Double[][] doubleobjectarray3 = { {  0.0,  2.0 } };
  //    Double[][] doubleobjectarray4 = { {  0.0,  -1.0,  1.0 },
  //        {  (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
  //    assertEquals(0, Comparison
  //            .compare(doubleobjectarray1, doubleobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(doubleobjectarray3, doubleobjectarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(doubleobjectarray2, doubleobjectarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(doubleobjectarray4, doubleobjectarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(doubleobjectarray2, doubleobjectarray4, comparator));
  //
  //    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
  //    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
  //    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
  //    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
  //        { TestClassA.class }};
  //    assertEquals(0, compare(classarray1, classarray2, comparator));
  //    assertEquals(1, compare(classarray3, classarray2, comparator));
  //    assertEquals(-1, compare(classarray2, classarray3, comparator));
  //    assertEquals(1, compare(classarray4, classarray2, comparator));
  //    assertEquals(-1, compare(classarray2, classarray4, comparator));
  //
  //    Date datea = new Date();
  //    datea.setTime( 123456);
  //    Date dateb = new Date();
  //    dateb.setTime( 7891011);
  //    Date datec = new Date();
  //    datec.setTime( 654321);
  //    Date[][] datearray1 = { {datea, dateb}, {datec} };
  //    Date[][] datearray2 = { {datea, dateb}, {datec} };
  //    Date[][] datearray3 = { {datec, datea}, {dateb} };
  //    Date[][] datearray4 = { {datea, dateb}};
  //    assertEquals(0, compare(datearray1, datearray2, comparator));
  //    assertEquals(1, compare(datearray3, datearray2, comparator));
  //    assertEquals(-1, compare(datearray2, datearray3, comparator));
  //    assertEquals(-1, compare(datearray4, datearray2, comparator));
  //    assertEquals(1, compare(datearray2, datearray4, comparator));
  //
  //    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
  //        {new BigInteger(bytearray3[0]) }};
  //    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
  //        {new BigInteger(bytearray3[0]) }};
  //    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
  //    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
  //    assertEquals(0, Comparison
  //            .compare(bigintegerarray1, bigintegerarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(bigintegerarray3, bigintegerarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(bigintegerarray2, bigintegerarray3, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(bigintegerarray4, bigintegerarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(bigintegerarray2, bigintegerarray4, comparator));
  //
  //    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
  //        {new BigDecimal(Integer.MAX_VALUE) }};
  //    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
  //        {new BigDecimal(Integer.MAX_VALUE) }};
  //    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
  //    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
  //    assertEquals(0, Comparison
  //            .compare(bigdecimalarray1, bigdecimalarray2, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(bigdecimalarray2, bigdecimalarray3, comparator));
  //    assertEquals(1, Comparison
  //            .compare(bigdecimalarray3, bigdecimalarray2, comparator));
  //    assertEquals(1, Comparison
  //            .compare(bigdecimalarray2, bigdecimalarray4, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(bigdecimalarray4, bigdecimalarray2, comparator));
  //
  //    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
  //        { {false, false}, {true }} };
  //    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
  //        { {false, false}, {true }} };
  //    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
  //    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
  //    assertEquals(0, compare(objectarray1, array2, comparator));
  //    assertEquals(-1, compare(objectarray3, array2, comparator));
  //    assertEquals(1, compare(objectarray2, array3, comparator));
  //    assertEquals(-1, compare(objectarray4, array2, comparator));
  //    assertEquals(1, compare(objectarray2, array4, comparator));
  //  }
  //
  //  @Test
  //  public void testObjectArrayIntComparator() {
  //    StringComparator comparator = new StringComparator();
  //
  //    Object[] array1 = { new TestClassA(), new TestClassA() };
  //    Object[] array2 = { new TestClassA(), new TestClassA() };
  //
  //    assertEquals(0, Comparison
  //            .compare(array1, array1.length, array1, array1.length, comparator));
  //    assertEquals(true, compare(array1, 1, array2, 1) != 0);
  //
  //    boolean[][] booleanarray1 = { { true, false }, { false, true } };
  //    boolean[][] booleanarray2 = { { true, false }, { false, true } };
  //    boolean[][] booleanarray3 = { { false, true }, { true } };
  //    boolean[][] booleanarray4 = { { true, false } };
  //    assertEquals(0, compare(booleanarray1, booleanarray1.length,
  //        booleanarray2, booleanarray2.length, comparator));
  //    assertEquals(1, compare(booleanarray1, booleanarray1.length,
  //        booleanarray3, booleanarray3.length, comparator));
  //    assertEquals(-1, compare(booleanarray3, booleanarray3.length,
  //        booleanarray1 , booleanarray1.length, comparator));
  //    assertEquals(1, compare(booleanarray1, booleanarray1.length,
  //        booleanarray4, booleanarray4.length, comparator));
  //    assertEquals(-1, compare(booleanarray4, booleanarray4.length,
  //        booleanarray1, booleanarray1.length, comparator));
  //
  //    char[][] chararray1 = { { (char) 0, (char) 1 },
  //        { Character.MIN_VALUE / 2, Character.MIN_VALUE },
  //        { Character.MAX_VALUE, Character.MAX_VALUE } };
  //    char[][] chararray2 = { { (char) 0, (char) 1 },
  //        { Character.MIN_VALUE / 2, Character.MIN_VALUE },
  //        { Character.MAX_VALUE, Character.MAX_VALUE } };
  //    char[][] chararray3 = { { (char) 0, (char) 3 } };
  //    char[][] chararray4 = { { (char) 0, (char) 1 },
  //        { Character.MIN_VALUE / 2, Character.MIN_VALUE } };
  //    assertEquals(0, compare(chararray1, chararray1.length, chararray2,
  //        chararray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(chararray1, chararray1.length, chararray3,
  //        chararray3.length, comparator));
  //    assertEquals(1, compare(chararray3, chararray3.length, chararray1,
  //        chararray1.length, comparator));
  //    assertEquals(1, compare(chararray1, chararray1.length, chararray4,
  //        chararray4.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(chararray4, chararray4.length, chararray1,
  //        chararray1.length, comparator));
  //    assertEquals(0, compare(chararray1, 1, chararray4, 1, comparator));
  //
  //    byte[][] bytearray1 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
  //        { Byte.MAX_VALUE, Byte.MAX_VALUE } };
  //    byte[][] bytearray2 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE / 2, Byte.MIN_VALUE },
  //        { Byte.MAX_VALUE, Byte.MAX_VALUE } };
  //    byte[][] bytearray3 = { { (byte) 0, (byte) 3 } };
  //    byte[][] bytearray4 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE / 2, Byte.MIN_VALUE } };
  //    assertEquals(0, Comparison
  //            .compare(bytearray1, bytearray1.length, bytearray2, bytearray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(bytearray1, bytearray1.length, bytearray3, bytearray3.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(bytearray3, bytearray3.length, bytearray1, bytearray1.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(bytearray1, bytearray1.length, bytearray4, bytearray4.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(bytearray4, bytearray4.length, bytearray1, bytearray1.length, comparator));
  //    assertEquals(0, compare(bytearray1, 1, bytearray4, 1, comparator));
  //
  //    short[][] shortarray1 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE / 2, Short.MIN_VALUE },
  //        { Short.MAX_VALUE, Short.MAX_VALUE } };
  //    short[][] shortarray2 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE / 2, Short.MIN_VALUE },
  //        { Short.MAX_VALUE, Short.MAX_VALUE } };
  //    short[][] shortarray3 = { { (short) 0, (short) 3 } };
  //    short[][] shortarray4 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE / 2, Short.MIN_VALUE } };
  //    assertEquals(0, compare(shortarray1, shortarray1.length,
  //        shortarray2, shortarray2.length, comparator));
  //    assertEquals(-1, compare(shortarray1, shortarray1.length,
  //        shortarray3, shortarray3.length, comparator));
  //    assertEquals(1, compare(shortarray3, shortarray3.length,
  //        shortarray1, shortarray1.length, comparator));
  //    assertEquals(1, compare(shortarray1, shortarray1.length,
  //        shortarray4, shortarray4.length, comparator));
  //    assertEquals(-1, compare(shortarray4, shortarray4.length,
  //        shortarray1, shortarray1.length, comparator));
  //    assertEquals(0, compare(shortarray1, 1, shortarray4, 1, comparator));
  //
  //    int[][] intarray1 = { {  0,  -1,  1 },
  //        {  Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
  //        {  Integer.MAX_VALUE, Integer.MAX_VALUE } };
  //    int[][] intarray2 = { {  0,  -1,  1 },
  //        {  Integer.MIN_VALUE / 2, Integer.MIN_VALUE },
  //        {  Integer.MAX_VALUE, Integer.MAX_VALUE } };
  //    int[][] intarray3 = { {  0,  3 } };
  //    int[][] intarray4 = { {  0,  -1,  1 },
  //        {  Integer.MIN_VALUE / 2, Integer.MIN_VALUE } };
  //    assertEquals(0, compare(intarray1, intarray1.length,
  //        intarray2, intarray2.length, comparator));
  //    assertEquals(-1, compare(intarray1, intarray1.length,
  //        intarray3, intarray3.length, comparator));
  //    assertEquals(1, compare(intarray3, intarray3.length,
  //        intarray1, intarray1.length, comparator));
  //    assertEquals(1, compare(intarray1, intarray1.length,
  //        intarray4, intarray4.length, comparator));
  //    assertEquals(-1, compare(intarray4, intarray4.length,
  //        intarray1, intarray1.length, comparator));
  //    assertEquals(0, compare(intarray1, 1, intarray4, 1, comparator));
  //
  //    long[][] longarray1 = { {  0,  -1,  1 },
  //        {  Long.MIN_VALUE / 2, Long.MIN_VALUE },
  //        {  Long.MAX_VALUE, Long.MAX_VALUE } };
  //    long[][] longarray2 = { {  0,  -1,  1 },
  //        {  Long.MIN_VALUE / 2, Long.MIN_VALUE },
  //        {  Long.MAX_VALUE, Long.MAX_VALUE } };
  //    long[][] longarray3 = { {  0,  3 } };
  //    long[][] longarray4 = { {  0,  -1,  1 },
  //        {  Long.MIN_VALUE / 2, Long.MIN_VALUE } };
  //    assertEquals(0, compare(longarray1, longarray1.length,
  //        longarray2, longarray2.length, comparator));
  //    assertEquals(-1, compare(longarray1, longarray1.length,
  //        longarray3, longarray3.length, comparator));
  //    assertEquals(1, compare(longarray3, longarray3.length,
  //        longarray1, longarray1.length, comparator));
  //    assertEquals(1, compare(longarray1, longarray1.length,
  //        longarray4, longarray4.length, comparator));
  //    assertEquals(-1, compare(longarray4, longarray4.length,
  //        longarray1, longarray1.length, comparator));
  //    assertEquals(0, compare(longarray1, 1, longarray4, 1, comparator));
  //
  //    float[][] floatarray1 = { {  0,  -1,  1 },
  //        {  Float.MIN_VALUE / 2, Float.MIN_VALUE },
  //        {  Float.MAX_VALUE, Float.MAX_VALUE } };
  //    float[][] floatarray2 = { {  0,  -1,  1 },
  //        {  Float.MIN_VALUE / 2, Float.MIN_VALUE },
  //        {  Float.MAX_VALUE, Float.MAX_VALUE } };
  //    float[][] floatarray3 = { {  0,  3 } };
  //    float[][] floatarray4 = { {  0,  -1,  1 },
  //        {  Float.MIN_VALUE / 2, Float.MIN_VALUE } };
  //    assertEquals(0, compare(floatarray1, floatarray1.length,
  //        floatarray2, floatarray2.length, comparator));
  //    assertEquals(-1, compare(floatarray1, floatarray1.length,
  //        floatarray3, floatarray3.length, comparator));
  //    assertEquals(1, compare(floatarray3, floatarray3.length,
  //        floatarray1, floatarray1.length, comparator));
  //    assertEquals(1, compare(floatarray1, floatarray1.length,
  //        floatarray4, floatarray4.length, comparator));
  //    assertEquals(-1, compare(floatarray4, floatarray4.length,
  //        floatarray1, floatarray1.length, comparator));
  //    assertEquals(0, compare(floatarray1, 1, floatarray4, 1,
  //    comparator));
  //
  //    double[][] doublearray1 = { {  0,  -1,  1 },
  //        {  Double.MIN_VALUE / 2, Double.MIN_VALUE },
  //        {  Double.MAX_VALUE, Double.MAX_VALUE } };
  //    double[][] doublearray2 = { {  0,  -1,  1 },
  //        {  Double.MIN_VALUE / 2, Double.MIN_VALUE },
  //        {  Double.MAX_VALUE, Double.MAX_VALUE } };
  //    double[][] doublearray3 = { {  0,  3 } };
  //    double[][] doublearray4 = { {  0,  -1,  1 },
  //        {  Double.MIN_VALUE / 2, Double.MIN_VALUE } };
  //    assertEquals(0, compare(doublearray1, doublearray1.length,
  //    doublearray2, doublearray2.length, comparator));
  //    assertEquals(-1, compare(doublearray1, doublearray1.length,
  //    doublearray3, doublearray3.length, comparator));
  //    assertEquals(1, compare(doublearray3, doublearray3.length,
  //    doublearray1, doublearray1.length, comparator));
  //    assertEquals(1, compare(doublearray1, doublearray1.length,
  //    doublearray4, doublearray4.length, comparator));
  //    assertEquals(-1, compare(doublearray4, doublearray4.length,
  //    doublearray1, doublearray1.length, comparator));
  //    assertEquals(0, compare(doublearray1, 1, doublearray4, 1,
  //    comparator));
  //
  //    String[][] stringarray1 = { { "long", "long" }, { "ago" } };
  //    String[][] stringarray2 = { { "long", "long" }, { "ago" } };
  //    String[][] stringarray3 = { { "long", "time" }, { "no", "see" } };
  //    String[][] stringarray4 = { { "long", "long" } };
  //    assertEquals(0, Comparison
  //            .compare(stringarray1, stringarray1.length, stringarray2,
  //        stringarray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(stringarray2, stringarray2.length, stringarray3,
  //        stringarray3.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(stringarray3, stringarray3.length, stringarray2,
  //        stringarray2.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(stringarray2, stringarray2.length, stringarray4,
  //        stringarray4.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(stringarray4, stringarray4.length, stringarray2,
  //        stringarray2.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(stringarray1, 1, stringarray4, 1, comparator));
  //
  //    Boolean[][] booleanobjectarray1 = { { true, false }, { false, true } };
  //    Boolean[][] booleanobjectarray2 = { { true, false }, { false, true } };
  //    Boolean[][] booleanobjectarray3 = { { false, true }, { true } };
  //    Boolean[][] booleanobjectarray4 = { { true, false } };
  //    assertEquals(0, compare(booleanobjectarray1, booleanobjectarray1.length,
  //        booleanobjectarray2, booleanobjectarray2.length, comparator));
  //    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length,
  //        booleanobjectarray3, booleanobjectarray3.length, comparator));
  //    assertEquals(-1, compare(booleanobjectarray3, booleanobjectarray3.length,
  //        booleanobjectarray2, booleanobjectarray2.length, comparator));
  //    assertEquals(1, compare(booleanobjectarray2, booleanobjectarray2.length,
  //        booleanobjectarray4, booleanobjectarray4.length, comparator));
  //    assertEquals(-1, compare(booleanobjectarray4, booleanobjectarray4.length,
  //        booleanobjectarray2, booleanobjectarray2.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(booleanobjectarray1, 1, booleanobjectarray4, 1, comparator));
  //
  //    Character[][] charobjectarray1 = { { (char) 0, (char) 1 },
  //        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
  //        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
  //    Character[][] charobjectarray2 = { { (char) 0, (char) 1 },
  //        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE },
  //        { (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE } };
  //    Character[][] charobjectarray3 = { { (char) 0, (char) 3 } };
  //    Character[][] charobjectarray4 = { { (char) 0, (char) 1 },
  //        { (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE } };
  //    assertEquals(0, compare(charobjectarray1, charobjectarray1.length,
  //        charobjectarray2, charobjectarray2.length, comparator));
  //    assertEquals(1, compare(charobjectarray3, charobjectarray3.length,
  //        charobjectarray2, charobjectarray2.length, comparator));
  //    assertEquals(-1, compare(charobjectarray2, charobjectarray2.length,
  //        charobjectarray3, charobjectarray3.length, comparator));
  //    assertEquals(-1, compare(charobjectarray4, charobjectarray4.length,
  //        charobjectarray2, charobjectarray2.length, comparator));
  //    assertEquals(1, compare(charobjectarray2, charobjectarray2.length,
  //        charobjectarray4, charobjectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(charobjectarray1, 1, charobjectarray4, 1, comparator));
  //
  //    Byte[][] byteobjectarray1 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
  //        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
  //    Byte[][] byteobjectarray2 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) },
  //        { (byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE } };
  //    Byte[][] byteobjectarray3 = { { (byte) 0, (byte) 2 } };
  //    Byte[][] byteobjectarray4 = { { (byte) 0, (byte) -1, (byte) 1 },
  //        { Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2) } };
  //    assertEquals(0, compare(byteobjectarray1, byteobjectarray1.length,
  //        byteobjectarray2, byteobjectarray2.length, comparator));
  //    assertEquals(1, compare(byteobjectarray3, byteobjectarray3.length,
  //        byteobjectarray2, byteobjectarray2.length, comparator));
  //    assertEquals(-1, compare(byteobjectarray2, byteobjectarray2.length,
  //        byteobjectarray3, byteobjectarray3.length, comparator));
  //    assertEquals(-1, compare(byteobjectarray4, byteobjectarray4.length,
  //        byteobjectarray2, byteobjectarray2.length, comparator));
  //    assertEquals(1, compare(byteobjectarray2, byteobjectarray2.length,
  //        byteobjectarray4, byteobjectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(byteobjectarray1, 1, byteobjectarray4, 1, comparator));
  //
  //    Short[][] shortobjectarray1 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
  //        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
  //    Short[][] shortobjectarray2 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) },
  //        { (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE } };
  //    Short[][] shortobjectarray3 = { { (short) 0, (short) 2 } };
  //    Short[][] shortobjectarray4 = { { (short) 0, (short) -1, (short) 1 },
  //        { Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2) } };
  //    assertEquals(0, compare(shortobjectarray1, shortobjectarray1.length,
  //        shortobjectarray2, shortobjectarray2.length, comparator));
  //    assertEquals(1, compare(shortobjectarray3, shortobjectarray3.length,
  //        shortobjectarray2, shortobjectarray2.length, comparator));
  //    assertEquals(-1, compare(shortobjectarray2, shortobjectarray2.length,
  //        shortobjectarray3, shortobjectarray3.length, comparator));
  //    assertEquals(-1, compare(shortobjectarray4, shortobjectarray4.length,
  //        shortobjectarray2, shortobjectarray2.length, comparator));
  //    assertEquals(1, compare(shortobjectarray2, shortobjectarray2.length,
  //        shortobjectarray4, shortobjectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(shortobjectarray1, 1, shortobjectarray4, 1, comparator));
  //
  //    Integer[][] integerarray1 = { {  0,  -1,  1 },
  //        { Integer.MIN_VALUE,  (Integer.MIN_VALUE / 2) },
  //        {  (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
  //    Integer[][] integerarray2 = { {  0,  -1,  1 },
  //        { Integer.MIN_VALUE,  (Integer.MIN_VALUE / 2) },
  //        {  (Integer.MAX_VALUE / 2), Integer.MAX_VALUE } };
  //    Integer[][] integerarray3 = { {  0,  3 } };
  //    Integer[][] integerarray4 = { {  0,  -1,  1 },
  //        { Integer.MIN_VALUE,  (Integer.MIN_VALUE / 2) } };
  //    assertEquals(0, compare(integerarray1, integerarray1.length,
  //        integerarray2, integerarray2.length, comparator));
  //    assertEquals(1, compare(integerarray3, integerarray3.length,
  //        integerarray2, integerarray2.length, comparator));
  //    assertEquals(-1, compare(integerarray2, integerarray2.length,
  //        integerarray3, integerarray3.length, comparator));
  //    assertEquals(-1, compare(integerarray4, integerarray4.length,
  //        integerarray2, integerarray2.length, comparator));
  //    assertEquals(1, compare(integerarray2, integerarray2.length,
  //        integerarray4, integerarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(integerarray1, 1, integerarray4, 1, comparator));
  //
  //    Long[][] longobjectarray1 = { {  0L,  -1L,  1L },
  //        { Long.MIN_VALUE,  (Long.MIN_VALUE / 2) },
  //        {  (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
  //    Long[][] longobjectarray2 = { {  0L,  -1L,  1L },
  //        { Long.MIN_VALUE,  (Long.MIN_VALUE / 2) },
  //        {  (Long.MAX_VALUE / 2), Long.MAX_VALUE } };
  //    Long[][] longobjectarray3 = { {  0L,  2L } };
  //    Long[][] longobjectarray4 = { {  0L,  -1L,  1L },
  //        { Long.MIN_VALUE,  (Long.MIN_VALUE / 2) } };
  //    assertEquals(0, compare(longobjectarray1, longobjectarray1.length,
  //        longobjectarray2, longobjectarray2.length, comparator));
  //    assertEquals(1, compare(longobjectarray3, longobjectarray3.length,
  //        longobjectarray2, longobjectarray2.length, comparator));
  //    assertEquals(-1, compare(longobjectarray2, longobjectarray2.length,
  //        longobjectarray3, longobjectarray3.length, comparator));
  //    assertEquals(-1, compare(longobjectarray4, longobjectarray4.length,
  //        longobjectarray2, longobjectarray2.length, comparator));
  //    assertEquals(1, compare(longobjectarray2, longobjectarray2.length,
  //        longobjectarray4, longobjectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(longobjectarray1, 1, longobjectarray4, 1, comparator));
  //
  //    Float[][] floatobjectarray1 = { {  0f,  -1f,  1f },
  //        { Float.MIN_VALUE,  (Float.MIN_VALUE / 2) },
  //        {  (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
  //    Float[][] floatobjectarray2 = { {  0f,  -1f,  1f },
  //        { Float.MIN_VALUE,  (Float.MIN_VALUE / 2) },
  //        {  (Float.MAX_VALUE / 2), Float.MAX_VALUE } };
  //    Float[][] floatobjectarray3 = { {  0f,  2f } };
  //    Float[][] floatobjectarray4 = { {  0f,  -1f,  1f },
  //        { Float.MIN_VALUE,  (Float.MIN_VALUE / 2) } };
  //    assertEquals(0, compare(floatobjectarray1, floatobjectarray1.length,
  //        floatobjectarray2, floatobjectarray2.length, comparator));
  //    assertEquals(1, compare(floatobjectarray3, floatobjectarray3.length,
  //        floatobjectarray2, floatobjectarray2.length, comparator));
  //    assertEquals(-1, compare(floatobjectarray2, floatobjectarray2.length,
  //        floatobjectarray3, floatobjectarray3.length, comparator));
  //    assertEquals(-1, compare(floatobjectarray4, floatobjectarray4.length,
  //        floatobjectarray2, floatobjectarray2.length, comparator));
  //    assertEquals(1, compare(floatobjectarray2, floatobjectarray2.length,
  //        floatobjectarray4, floatobjectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(floatobjectarray1, 1, floatobjectarray4, 1, comparator));
  //
  //    Double[][] doubleobjectarray1 = { {  0.0,  -1.0,  1.0 },
  //        {  (Double.MIN_VALUE / 2), Double.MIN_VALUE },
  //        {  (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
  //    Double[][] doubleobjectarray2 = { {  0.0,  -1.0,  1.0 },
  //        {  (Double.MIN_VALUE / 2), Double.MIN_VALUE },
  //        {  (Double.MAX_VALUE / 2), Double.MAX_VALUE } };
  //    Double[][] doubleobjectarray3 = { {  0.0,  2.0 } };
  //    Double[][] doubleobjectarray4 = { {  0.0,  -1.0,  1.0 },
  //        {  (Double.MIN_VALUE / 2), Double.MIN_VALUE } };
  //    assertEquals(0, compare(doubleobjectarray1, doubleobjectarray1.length,
  //        doubleobjectarray2, doubleobjectarray2.length, comparator));
  //    assertEquals(1, compare(doubleobjectarray3, doubleobjectarray3.length,
  //        doubleobjectarray2, doubleobjectarray2.length, comparator));
  //    assertEquals(-1, compare(doubleobjectarray2, doubleobjectarray2.length,
  //        doubleobjectarray3, doubleobjectarray3.length, comparator));
  //    assertEquals(-1, compare(doubleobjectarray4, doubleobjectarray4.length,
  //        doubleobjectarray2, doubleobjectarray2.length, comparator));
  //    assertEquals(1, compare(doubleobjectarray2, doubleobjectarray2.length,
  //        doubleobjectarray4, doubleobjectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(doubleobjectarray1, 1, doubleobjectarray4, 1, comparator));
  //
  //    Class<?>[][] classarray1 = { { TestClassA.class }, { TestClassA.class } };
  //    Class<?>[][] classarray2 = { { TestClassA.class }, { TestClassA.class } };
  //    Class<?>[][] classarray3 = { { TestClassB.class }, { TestClassB.class } };
  //    Class<?>[][] classarray4 = { { TestClassA.class }, { TestClassA.class },
  //        { TestClassA.class }};
  //    assertEquals(0, Comparison
  //            .compare(classarray1, classarray1.length, classarray2,
  //        classarray2.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(classarray3, classarray3.length, classarray2,
  //        classarray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(classarray2, classarray2.length, classarray3,
  //        classarray3.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(classarray4, classarray4.length, classarray2,
  //        classarray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(classarray2, classarray2.length, classarray4,
  //        classarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(classarray1, 1, classarray4, 1, comparator));
  //
  //    Date datea = new Date();
  //    datea.setTime( 123456);
  //    Date dateb = new Date();
  //    dateb.setTime( 7891011);
  //    Date datec = new Date();
  //    datec.setTime( 654321);
  //    Date[][] datearray1 = { {datea, dateb}, {datec} };
  //    Date[][] datearray2 = { {datea, dateb}, {datec} };
  //    Date[][] datearray3 = { {datec, datea}, {dateb} };
  //    Date[][] datearray4 = { {datea, dateb}};
  //    assertEquals(0, compare(datearray1, datearray1.length, datearray2,
  //        datearray2.length, comparator));
  //    assertEquals(1, compare(datearray3, datearray3.length, datearray2,
  //        datearray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(datearray2, datearray2.length, datearray3,
  //        datearray3.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(datearray4, datearray4.length, datearray2,
  //        datearray2.length, comparator));
  //    assertEquals(1, compare(datearray2, datearray2.length, datearray4,
  //        datearray4.length, comparator));
  //    assertEquals(0, compare(datearray1, 1, datearray4, 1, comparator));
  //
  //    BigInteger[][] bigintegerarray1 = { {new BigInteger(bytearray1[0])},
  //        {new BigInteger(bytearray3[0]) }};
  //    BigInteger[][] bigintegerarray2 = { {new BigInteger(bytearray2[0])},
  //        {new BigInteger(bytearray3[0]) }};
  //    BigInteger[][] bigintegerarray3 = { {new BigInteger(bytearray3[0]) }};
  //    BigInteger[][] bigintegerarray4 = { {new BigInteger(bytearray2[0]) }};
  //    assertEquals(0, compare(bigintegerarray1, bigintegerarray1.length,
  //        bigintegerarray2, bigintegerarray2.length, comparator));
  //    assertEquals(-1, compare(bigintegerarray3, bigintegerarray3.length,
  //        bigintegerarray2, bigintegerarray2.length, comparator));
  //    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length,
  //        bigintegerarray3, bigintegerarray3.length, comparator));
  //    assertEquals(-1, compare(bigintegerarray4, bigintegerarray4.length,
  //        bigintegerarray2, bigintegerarray2.length, comparator));
  //    assertEquals(1, compare(bigintegerarray2, bigintegerarray2.length,
  //        bigintegerarray4, bigintegerarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(bigintegerarray1, 1, bigintegerarray4, 1, comparator));
  //
  //    BigDecimal[][] bigdecimalarray1 = { {new BigDecimal(Integer.MIN_VALUE)},
  //        {new BigDecimal(Integer.MAX_VALUE) }};
  //    BigDecimal[][] bigdecimalarray2 = { {new BigDecimal(Integer.MIN_VALUE)},
  //        {new BigDecimal(Integer.MAX_VALUE) }};
  //    BigDecimal[][] bigdecimalarray3 = { {new BigDecimal(0), new BigDecimal(-1)}};
  //    BigDecimal[][] bigdecimalarray4 = { {new BigDecimal(Integer.MIN_VALUE) }};
  //    assertEquals(0, compare(bigdecimalarray1, bigdecimalarray1.length,
  //        bigdecimalarray2, bigdecimalarray2.length, comparator));
  //    assertEquals(-1, compare(bigdecimalarray2, bigdecimalarray2.length,
  //        bigdecimalarray3, bigdecimalarray3.length, comparator));
  //    assertEquals(1, compare(bigdecimalarray3, bigdecimalarray3.length,
  //        bigdecimalarray2, bigdecimalarray2.length, comparator));
  //    assertEquals(1, compare(bigdecimalarray2, bigdecimalarray2.length,
  //        bigdecimalarray4, bigdecimalarray4.length, comparator));
  //    assertEquals(-1, compare(bigdecimalarray4, bigdecimalarray4.length,
  //        bigdecimalarray2, bigdecimalarray2.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(bigdecimalarray1, 1, bigdecimalarray4, 1, comparator));
  //
  //    Boolean[][][] objectarray1 = { { {true}, {false} }, { {true, true }},
  //        { {false, false}, {true }} };
  //    Boolean[][][] objectarray2 = { { {true}, {false} }, { {true, true }},
  //        { {false, false}, {true }} };
  //    Boolean[][][] objectarray3 = { {{ true}, {false} }, { {false, true }} };
  //    Boolean[][][] objectarray4 = { { {true}, {false} }, { {true, true }} };
  //    assertEquals(0, Comparison
  //            .compare(objectarray1, array1.length, array2,
  //        objectarray2.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(objectarray3, array3.length, array2,
  //        objectarray2.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(objectarray2, array2.length, array3,
  //        objectarray3.length, comparator));
  //    assertEquals(-1, Comparison
  //            .compare(objectarray4, array4.length, array2,
  //        objectarray2.length, comparator));
  //    assertEquals(1, Comparison
  //            .compare(objectarray2, array2.length, array4,
  //        objectarray4.length, comparator));
  //    assertEquals(0, Comparison
  //            .compare(objectarray1, 1, array4, 1, comparator));
  //  }

  @Test
  public void testBooleanCollection() {
    BooleanArrayList col1 = new BooleanArrayList();
    BooleanArrayList col2 = new BooleanArrayList();
    col1.add(true);
    col2.add(true);
    col1.add(false);
    col2.add(false);

    assertEquals(0, compare(col1, col2));
    col2.add(true);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(false);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testCharCollection() {
    CharArrayList col1 = new CharArrayList();
    CharArrayList col2 = new CharArrayList();
    col1.add('a');
    col2.add('a');
    col1.add('b');
    col2.add('b');

    assertEquals(0, compare(col1, col2));
    col2.add('c');
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add('z');
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testByteCollection() {
    ByteArrayList col1 = new ByteArrayList();
    ByteArrayList col2 = new ByteArrayList();
    col1.add((byte) 2);
    col2.add((byte) 2);
    col1.add((byte) 31);
    col2.add((byte) 31);

    assertEquals(0, compare(col1, col2));
    col2.add((byte) 60);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((byte) 100);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testShortCollection() {
    ShortArrayList col1 = new ShortArrayList();
    ShortArrayList col2 = new ShortArrayList();
    col1.add((short) 2);
    col2.add((short) 2);
    col1.add((short) 31);
    col2.add((short) 31);

    assertEquals(0, compare(col1, col2));
    col2.add((short) 30);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add((short) 70);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col2 = null;
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testIntCollection() {
    IntArrayList col1 = new IntArrayList();
    IntArrayList col2 = new IntArrayList();
    col1.add(2);
    col2.add(2);
    col1.add(31);
    col2.add(31);

    assertEquals(0, compare(col1, col2));
    col2.add(30);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(100);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testLongCollection() {
    LongArrayList col1 = new LongArrayList();
    LongArrayList col2 = new LongArrayList();
    col1.add(2);
    col2.add(2);
    col1.add(31);
    col2.add(31);

    assertEquals(0, compare(col1, col2));
    col2.add(100);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(50);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testFloatCollection() {
    FloatArrayList col1 = new FloatArrayList();
    FloatArrayList col2 = new FloatArrayList();
    col1.add(2);
    col2.add(2);
    col1.add(31);
    col2.add(31);

    assertEquals(0, compare(col1, col2));
    col2.add(100);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(50);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testDoubleCollection() {
    DoubleArrayList col1 = new DoubleArrayList();
    DoubleArrayList col2 = new DoubleArrayList();
    col1.add(2);
    col2.add(2);
    col1.add(31);
    col2.add(31);

    assertEquals(0, compare(col1, col2));
    col2.add(100);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col1.add(50);
    assertEquals(-1, compare(col1, col2));
    assertEquals(1, compare(col2, col1));
    col2 = null;
    col1.compareTo(col2);
    assertEquals(1, compare(col1, col2));
    assertEquals(-1, compare(col2, col1));
    col1 = null;
    assertEquals(0, compare(col1, col2));
  }

  @Test
  public void testCollectionOfT() {
    // fail("Not yet implemented");
  }

  @Test
  public void testCollectionOfTDouble() {
    // fail("Not yet implemented");
  }

  @Test
  public void testCollectionOfTCollectionOfTComparatorOfQ() {
    // fail
  }

}
