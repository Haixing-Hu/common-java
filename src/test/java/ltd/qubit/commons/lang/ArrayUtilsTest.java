////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BOOLEAN_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BYTE_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CHAR_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_DOUBLE_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_FLOAT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_INT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_LONG_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_SHORT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.add;
import static ltd.qubit.commons.lang.ArrayUtils.addAll;
import static ltd.qubit.commons.lang.ArrayUtils.contains;
import static ltd.qubit.commons.lang.ArrayUtils.getLength;
import static ltd.qubit.commons.lang.ArrayUtils.indexOf;
import static ltd.qubit.commons.lang.ArrayUtils.isArray;
import static ltd.qubit.commons.lang.ArrayUtils.isEmpty;
import static ltd.qubit.commons.lang.ArrayUtils.isSameLength;
import static ltd.qubit.commons.lang.ArrayUtils.isSameType;
import static ltd.qubit.commons.lang.ArrayUtils.lastIndexOf;
import static ltd.qubit.commons.lang.ArrayUtils.nullToEmpty;
import static ltd.qubit.commons.lang.ArrayUtils.remove;
import static ltd.qubit.commons.lang.ArrayUtils.removeElement;
import static ltd.qubit.commons.lang.ArrayUtils.reverse;
import static ltd.qubit.commons.lang.ArrayUtils.subarray;
import static ltd.qubit.commons.lang.ArrayUtils.toObject;
import static ltd.qubit.commons.lang.ArrayUtils.toPrimitive;

/**
 * The unit test for the Arrays class.
 *
 * @author Haixing Hu
 */
public class ArrayUtilsTest {

  //  stop magic number check
  @Test
  public void testToString() {
    assertEquals("{}", ArrayUtils.toString(null));
    assertEquals("{}", ArrayUtils.toString(new Object[0]));
    assertEquals("{}", ArrayUtils.toString(new String[0]));
    assertEquals("{<null>}", ArrayUtils.toString(new String[]{null}));
    assertEquals("{\"pink\",\"blue\"}",
        ArrayUtils.toString(new String[]{"pink", "blue"}));

    assertEquals("<empty>", ArrayUtils.toString(null, "<empty>"));
    assertEquals("{}", ArrayUtils.toString(new Object[0], "<empty>"));
    assertEquals("{}", ArrayUtils.toString(new String[0], "<empty>"));
    assertEquals("{<null>}",
        ArrayUtils.toString(new String[]{null}, "<empty>"));
    assertEquals("{\"pink\",\"blue\"}",
        ArrayUtils.toString(new String[]{"pink", "blue"}, "<empty>"));
  }

  @Test
  public void testHashCode() {
    final long[][] array1 = {{2, 5}, {4, 5}};
    final long[][] array2 = {{2, 5}, {4, 6}};
    assertEquals(ArrayUtils.hashCode(array1), ArrayUtils.hashCode(array1));
    assertNotEquals(ArrayUtils.hashCode(array1), ArrayUtils.hashCode(array2));

    final Object[] array3 = {new String(new char[]{'A', 'B'})};
    final Object[] array4 = {"AB"};
    assertEquals(ArrayUtils.hashCode(array3), ArrayUtils.hashCode(array3));
    assertEquals(ArrayUtils.hashCode(array3), ArrayUtils.hashCode(array4));
  }

  //  private void assertArraysEqual(Object array1, Object array2, Object array3) {
  //    assertTrue(Equality.equals(array1, array1));
  //    assertTrue(Equality.equals(array2, array2));
  //    assertTrue(Equality.equals(array3, array3));
  //    assertFalse(Equality.equals(array1, array2));
  //    assertFalse(Equality.equals(array2, array1));
  //    assertFalse(Equality.equals(array1, array3));
  //    assertFalse(Equality.equals(array3, array1));
  //    assertFalse(Equality.equals(array1, array2));
  //    assertFalse(Equality.equals(array2, array1));
  //  }

  //  @Test
  //  public void testToMap() {
  //    Map map = Arrays.toMap(new String[][] { { "foo", "bar" },{ "hello", "world" } });
  //
  //    assertEquals("bar", map.get("foo"));
  //    assertEquals("world", map.get("hello"));
  //
  //    assertNull(Arrays.toMap(null));
  //    try {
  //      Arrays.toMap(new String[][] { { "foo", "bar" }, { "short" } });
  //      fail("exception expected");
  //    } catch (IllegalArgumentException ex) {
  //    }
  //    try {
  //      Arrays
  //          .toMap(new Object[] { new Object[] { "foo", "bar" }, "illegal type" });
  //      fail("exception expected");
  //    } catch (IllegalArgumentException ex) {
  //    }
  //    try {
  //      Arrays.toMap(new Object[] { new Object[] { "foo", "bar" }, null });
  //      fail("exception expected");
  //    } catch (IllegalArgumentException ex) {
  //    }
  //
  //    map = Arrays.toMap(new Object[] { new Map.Entry() {
  //      public Object getKey() {
  //        return "foo";
  //      }
  //
  //      public Object getValue() {
  //        return "bar";
  //      }
  //
  //      public Object setValue(Object value) {
  //        throw new UnsupportedOperationException();
  //      }
  //
  //      public boolean equals(Object o) {
  //        throw new UnsupportedOperationException();
  //      }
  //
  //      public int hashCode() {
  //        throw new UnsupportedOperationException();
  //      }
  //    } });
  //    assertEquals("bar", map.get("foo"));
  //  }

  @Test
  public void testSubarrayObject() {
    final Object[] nullArray = null;
    final Object[] objectArray = {"a", "b", "c", "d", "e", "f"};
    Object[] subArray = null;

    subArray = subarray(objectArray, 0, 4);
    assertEquals("abcd", StringUtils.join(null, subArray),
        "0 start, mid end");

    subArray = subarray(objectArray, 0, objectArray.length);
    assertEquals("abcdef", StringUtils.join(null, subArray),
        "0 start, length end");

    subArray = subarray(objectArray, 1, 4);
    assertEquals("bcd", StringUtils.join(null, subArray),
        "mid start, mid end");

    subArray = subarray(objectArray, 1, objectArray.length);
    assertEquals("bcdef", StringUtils.join(null, subArray),
        "mid start, length end");

    subArray = subarray(nullArray, 0, 3);
    assertNull(subArray, "null input");

    subArray = subarray(EMPTY_OBJECT_ARRAY, 1, 2);
    assertEquals("", StringUtils.join(null, subArray),
        "empty array");

    subArray = subarray(objectArray, 4, 2);
    assertEquals("", StringUtils.join(null, subArray),
        "start > end");

    subArray = subarray(objectArray, 3, 3);
    assertEquals("", StringUtils.join(null, subArray),
        "start == end");

    subArray = subarray(objectArray, -2, 4);
    assertEquals("abcd", StringUtils.join(null, subArray),
        "start undershoot, normal end");

    subArray = subarray(objectArray, 33, 4);
    assertEquals("", StringUtils.join(null, subArray),
        "start overshoot, any end");

    subArray = subarray(objectArray, 2, 33);
    assertEquals("cdef", StringUtils.join(null, subArray),
        "normal start, end overshoot");

    subArray = subarray(objectArray, -2, 12);
    assertEquals("abcdef", StringUtils.join(null, subArray),
        "start undershoot, end overshoot");

    // array type tests
    final Date[] dateArray = {new java.sql.Date(new Date().getTime()),
        new Date(), new Date(), new Date(), new Date()};

    assertSame(Object.class,
        subarray(objectArray, 2, 4).getClass().getComponentType(),
        "Object type");
    assertSame(Date.class,
        subarray(dateArray, 1, 4).getClass().getComponentType(),
        "java.util.Date type");
    assertNotSame(java.sql.Date.class,
        subarray(dateArray, 1, 4).getClass().getComponentType(),
        "java.sql.Date type");
    //    try {
    //      @SuppressWarnings("unused")
    //      final Object dummy = subarray(dateArray, 1, 3);
    //      fail("Invalid downcast");
    //    } catch (final ClassCastException e) {
    //    }
  }

  @Test
  public void testSubarrayLong() {
    final long[] nullArray = null;
    final long[] array = {999910, 999911, 999912, 999913, 999914, 999915};
    final long[] leftSubarray = {999910, 999911, 999912, 999913};
    final long[] midSubarray = {999911, 999912, 999913, 999914};
    final long[] rightSubarray = {999912, 999913, 999914, 999915};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_LONG_ARRAY, subarray(EMPTY_LONG_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_LONG_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_LONG_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_LONG_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_LONG_ARRAY, subarray(EMPTY_LONG_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_LONG_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_LONG_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_LONG_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    // array type tests

    assertSame(long.class, subarray(array, 2, 4).getClass().getComponentType(),
        "long type");

  }

  @Test
  public void testSubarrayInt() {
    final int[] nullArray = null;
    final int[] array = {10, 11, 12, 13, 14, 15};
    final int[] leftSubarray = {10, 11, 12, 13};
    final int[] midSubarray = {11, 12, 13, 14};
    final int[] rightSubarray = {12, 13, 14, 15};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_INT_ARRAY, subarray(EMPTY_INT_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_INT_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_INT_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_INT_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    assertSame(EMPTY_INT_ARRAY, subarray(EMPTY_INT_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_INT_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_INT_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_INT_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    assertSame(int.class, subarray(array, 2, 4).getClass().getComponentType(),
        "int type");

  }

  @Test
  public void testSubarrayShort() {
    final short[] nullArray = null;
    final short[] array = {10, 11, 12, 13, 14, 15};
    final short[] leftSubarray = {10, 11, 12, 13};
    final short[] midSubarray = {11, 12, 13, 14};
    final short[] rightSubarray = {12, 13, 14, 15};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_SHORT_ARRAY, subarray(EMPTY_SHORT_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_SHORT_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_SHORT_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_SHORT_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_SHORT_ARRAY, subarray(EMPTY_SHORT_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_SHORT_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_SHORT_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_SHORT_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    // array type tests

    assertSame(short.class, subarray(array, 2, 4).getClass().getComponentType(),
        "short type");

  }

  @Test
  public void testSubarrChar() {
    final char[] nullArray = null;
    final char[] array = {'a', 'b', 'c', 'd', 'e', 'f'};
    final char[] leftSubarray = {'a', 'b', 'c', 'd'};
    final char[] midSubarray = {'b', 'c', 'd', 'e'};
    final char[] rightSubarray = {'c', 'd', 'e', 'f'};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_CHAR_ARRAY, subarray(EMPTY_CHAR_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_CHAR_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_CHAR_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_CHAR_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_CHAR_ARRAY, subarray(EMPTY_CHAR_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_CHAR_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_CHAR_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_CHAR_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    // array type tests

    assertSame(char.class, subarray(array, 2, 4).getClass().getComponentType(),
        "char type");

  }

  @Test
  public void testSubarrayByte() {
    final byte[] nullArray = null;
    final byte[] array = {10, 11, 12, 13, 14, 15};
    final byte[] leftSubarray = {10, 11, 12, 13};
    final byte[] midSubarray = {11, 12, 13, 14};
    final byte[] rightSubarray = {12, 13, 14, 15};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_BYTE_ARRAY, subarray(EMPTY_BYTE_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_BYTE_ARRAY, subarray(array, 4, 2), "start > end");

    assertArrayEquals(EMPTY_BYTE_ARRAY, subarray(array, 3, 3), "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_BYTE_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_BYTE_ARRAY, subarray(EMPTY_BYTE_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_BYTE_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_BYTE_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_BYTE_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    assertSame(byte.class, subarray(array, 2, 4).getClass().getComponentType(),
        "byte type");

  }

  @Test
  public void testSubarrayDouble() {
    final double[] nullArray = null;
    final double[] array = {10.123, 11.234, 12.345, 13.456, 14.567, 15.678};
    final double[] leftSubarray = {10.123, 11.234, 12.345, 13.456};
    final double[] midSubarray = {11.234, 12.345, 13.456, 14.567};
    final double[] rightSubarray = {12.345, 13.456, 14.567, 15.678};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_DOUBLE_ARRAY, subarray(EMPTY_DOUBLE_ARRAY, 1, 2), ""
        + "empty array");

    assertArrayEquals(EMPTY_DOUBLE_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_DOUBLE_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_DOUBLE_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_DOUBLE_ARRAY, subarray(EMPTY_DOUBLE_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_DOUBLE_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_DOUBLE_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_DOUBLE_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    // array type tests

    assertSame(double.class, subarray(array, 2, 4).getClass().getComponentType(),
        "double type");

  }

  @Test
  public void testSubarrayFloat() {
    final float[] nullArray = null;
    final float[] array = {10, 11, 12, 13, 14, 15};
    final float[] leftSubarray = {10, 11, 12, 13};
    final float[] midSubarray = {11, 12, 13, 14};
    final float[] rightSubarray = {12, 13, 14, 15};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_FLOAT_ARRAY, subarray(EMPTY_FLOAT_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_FLOAT_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_FLOAT_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertEquals(EMPTY_FLOAT_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_FLOAT_ARRAY, subarray(EMPTY_FLOAT_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_FLOAT_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_FLOAT_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_FLOAT_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    // array type tests

    assertSame(float.class, subarray(array, 2, 4).getClass().getComponentType(),
        "float type");

  }

  @Test
  public void testSubarrayBoolean() {
    final boolean[] nullArray = null;
    final boolean[] array = {true, true, false, true, false, true};
    final boolean[] leftSubarray = {true, true, false, true};
    final boolean[] midSubarray = {true, false, true, false};
    final boolean[] rightSubarray = {false, true, false, true};

    assertArrayEquals(leftSubarray, subarray(array, 0, 4),
        "0 start, mid end");

    assertArrayEquals(array, subarray(array, 0, array.length),
        "0 start, length end");

    assertArrayEquals(midSubarray, subarray(array, 1, 5),
        "mid start, mid end");

    assertArrayEquals(rightSubarray, subarray(array, 2, array.length),
        "mid start, length end");

    assertNull(subarray(nullArray, 0, 3), "null input");

    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, subarray(EMPTY_BOOLEAN_ARRAY, 1, 2),
        "empty array");

    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, subarray(array, 4, 2),
        "start > end");

    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, subarray(array, 3, 3),
        "start == end");

    assertArrayEquals(leftSubarray, subarray(array, -2, 4),
        "start undershoot, normal end");

    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, subarray(array, 33, 4),
        "start overshoot, any end");

    assertArrayEquals(rightSubarray, subarray(array, 2, 33),
        "normal start, end overshoot");

    assertArrayEquals(array, subarray(array, -2, 12),
        "start undershoot, end overshoot");

    // empty-return tests

    assertSame(EMPTY_BOOLEAN_ARRAY, subarray(EMPTY_BOOLEAN_ARRAY, 1, 2),
        "empty array, object test");

    assertSame(EMPTY_BOOLEAN_ARRAY, subarray(array, 4, 1),
        "start > end, object test");

    assertSame(EMPTY_BOOLEAN_ARRAY, subarray(array, 3, 3),
        "start == end, object test");

    assertSame(EMPTY_BOOLEAN_ARRAY, subarray(array, 8733, 4),
        "start overshoot, any end, object test");

    // array type tests

    assertSame(boolean.class, subarray(array, 2, 4).getClass().getComponentType(),
        "boolean type");

  }

  @Test
  public void testSameLength() {
    final Object[] nullArray = null;
    final Object[] emptyArray = new Object[0];
    final Object[] oneArray = {"pick"};
    final Object[] twoArray = {"pick", "stick"};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthBoolean() {
    final boolean[] nullArray = null;
    final boolean[] emptyArray = new boolean[0];
    final boolean[] oneArray = {true};
    final boolean[] twoArray = {true, false};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthLong() {
    final long[] nullArray = null;
    final long[] emptyArray = new long[0];
    final long[] oneArray = {0L};
    final long[] twoArray = {0L, 76L};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthInt() {
    final int[] nullArray = null;
    final int[] emptyArray = new int[0];
    final int[] oneArray = {4};
    final int[] twoArray = {5, 7};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthShort() {
    final short[] nullArray = null;
    final short[] emptyArray = new short[0];
    final short[] oneArray = {4};
    final short[] twoArray = {6, 8};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthChar() {
    final char[] nullArray = null;
    final char[] emptyArray = new char[0];
    final char[] oneArray = {'f'};
    final char[] twoArray = {'d', 't'};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthByte() {
    final byte[] nullArray = null;
    final byte[] emptyArray = new byte[0];
    final byte[] oneArray = {3};
    final byte[] twoArray = {4, 6};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthDouble() {
    final double[] nullArray = null;
    final double[] emptyArray = new double[0];
    final double[] oneArray = {1.3d};
    final double[] twoArray = {4.5d, 6.3d};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameLengthFloat() {
    final float[] nullArray = null;
    final float[] emptyArray = new float[0];
    final float[] oneArray = {2.5f};
    final float[] twoArray = {6.4f, 5.8f};

    assertTrue(isSameLength(nullArray, nullArray));
    assertTrue(isSameLength(nullArray, emptyArray));
    assertFalse(isSameLength(nullArray, oneArray));
    assertFalse(isSameLength(nullArray, twoArray));

    assertTrue(isSameLength(emptyArray, nullArray));
    assertTrue(isSameLength(emptyArray, emptyArray));
    assertFalse(isSameLength(emptyArray, oneArray));
    assertFalse(isSameLength(emptyArray, twoArray));

    assertFalse(isSameLength(oneArray, nullArray));
    assertFalse(isSameLength(oneArray, emptyArray));
    assertTrue(isSameLength(oneArray, oneArray));
    assertFalse(isSameLength(oneArray, twoArray));

    assertFalse(isSameLength(twoArray, nullArray));
    assertFalse(isSameLength(twoArray, emptyArray));
    assertFalse(isSameLength(twoArray, oneArray));
    assertTrue(isSameLength(twoArray, twoArray));
  }

  @Test
  public void testSameType() {
    try {
      isSameType(null, null);
      fail();
    } catch (final IllegalArgumentException ex) {
      //  pass
    }
    try {
      isSameType(null, new Object[0]);
      fail();
    } catch (final IllegalArgumentException ex) {
      //  pass
    }
    try {
      isSameType(new Object[0], null);
      fail();
    } catch (final IllegalArgumentException ex) {
      //  pass
    }

    assertTrue(isSameType(new Object[0], new Object[0]));
    assertFalse(isSameType(new String[0], new Object[0]));
    assertEquals(true, isSameType(new String[0][0], new String[0][0]));
    assertFalse(isSameType(new String[0], new String[0][0]));
    assertFalse(isSameType(new String[0][0], new String[0]));
  }

  @Test
  public void testReverse() {
    final StringBuilder str1 = new StringBuilder("pick");
    final String str2 = "a";
    final String[] str3 = {"stick"};
    final String str4 = "up";

    Object[] array = {str1, str2, str3};
    reverse(array);
    assertEquals(array[0], str3);
    assertEquals(array[1], str2);
    assertEquals(array[2], str1);

    array = new Object[]{str1, str2, str3, str4};
    reverse(array);
    assertEquals(array[0], str4);
    assertEquals(array[1], str3);
    assertEquals(array[2], str2);
    assertEquals(array[3], str1);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseLong() {
    long[] array = {1L, 2L, 3L};
    reverse(array);
    assertEquals(array[0], 3L);
    assertEquals(array[1], 2L);
    assertEquals(array[2], 1L);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseInt() {
    int[] array = {1, 2, 3};
    reverse(array);
    assertEquals(array[0], 3);
    assertEquals(array[1], 2);
    assertEquals(array[2], 1);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseShort() {
    short[] array = {1, 2, 3};
    reverse(array);
    assertEquals(array[0], 3);
    assertEquals(array[1], 2);
    assertEquals(array[2], 1);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseChar() {
    char[] array = {'a', 'f', 'C'};
    reverse(array);
    assertEquals(array[0], 'C');
    assertEquals(array[1], 'f');
    assertEquals(array[2], 'a');

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseByte() {
    byte[] array = {2, 3, 4};
    reverse(array);
    assertEquals(array[0], 4);
    assertEquals(array[1], 3);
    assertEquals(array[2], 2);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseDouble() {
    double[] array = {0.3d, 0.4d, 0.5d};
    reverse(array);
    assertEquals(array[0], 0.5d, 0.0d);
    assertEquals(array[1], 0.4d, 0.0d);
    assertEquals(array[2], 0.3d, 0.0d);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseFloat() {
    float[] array = {0.3f, 0.4f, 0.5f};
    reverse(array);
    assertEquals(array[0], 0.5f, 0.0f);
    assertEquals(array[1], 0.4f, 0.0f);
    assertEquals(array[2], 0.3f, 0.0f);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testReverseBoolean() {
    boolean[] array = {false, false, true};
    reverse(array);
    assertEquals(array[0], true);
    assertEquals(array[1], false);
    assertEquals(array[2], false);

    array = null;
    reverse(array);
    assertNull(array);
  }

  @Test
  public void testIndexOf() {
    final Object[] array = {"0", "1", "2", "3", null, "0"};
    assertEquals(-1, indexOf(null, null));
    assertEquals(-1, indexOf(null, "0"));
    assertEquals(-1, indexOf(new Object[0], "0"));
    assertEquals(0, indexOf(array, "0"));
    assertEquals(1, indexOf(array, "1"));
    assertEquals(2, indexOf(array, "2"));
    assertEquals(3, indexOf(array, "3"));
    assertEquals(4, indexOf(array, null));
    assertEquals(-1, indexOf(array, "notInArray"));
  }

  @Test
  public void testIndexOfWithStartIndex() {
    final Object[] array = {"0", "1", "2", "3", null, "0"};
    assertEquals(-1, indexOf(null, null, 2));
    assertEquals(-1, indexOf(new Object[0], "0", 0));
    assertEquals(-1, indexOf(null, "0", 2));
    assertEquals(5, indexOf(array, "0", 2));
    assertEquals(-1, indexOf(array, "1", 2));
    assertEquals(2, indexOf(array, "2", 2));
    assertEquals(3, indexOf(array, "3", 2));
    assertEquals(4, indexOf(array, null, 2));
    assertEquals(-1, indexOf(array, "notInArray", 2));

    assertEquals(4, indexOf(array, null, -1));
    assertEquals(-1, indexOf(array, null, 8));
    assertEquals(-1, indexOf(array, "0", 8));
  }

  @Test
  public void testLastIndexOf() {
    final Object[] array = {"0", "1", "2", "3", null, "0"};
    assertEquals(-1, lastIndexOf(null, null));
    assertEquals(-1, lastIndexOf(null, "0"));
    assertEquals(5, lastIndexOf(array, "0"));
    assertEquals(1, lastIndexOf(array, "1"));
    assertEquals(2, lastIndexOf(array, "2"));
    assertEquals(3, lastIndexOf(array, "3"));
    assertEquals(4, lastIndexOf(array, null));
    assertEquals(-1, lastIndexOf(array, "notInArray"));
  }

  @Test
  public void testLastIndexOfWithStartIndex() {
    final Object[] array = {"0", "1", "2", "3", null, "0"};
    assertEquals(-1, lastIndexOf(null, null, 2));
    assertEquals(-1, lastIndexOf(null, "0", 2));
    assertEquals(0, lastIndexOf(array, "0", 2));
    assertEquals(1, lastIndexOf(array, "1", 2));
    assertEquals(2, lastIndexOf(array, "2", 2));
    assertEquals(-1, lastIndexOf(array, "3", 2));
    assertEquals(-1, lastIndexOf(array, "3", -1));
    assertEquals(4, lastIndexOf(array, null, 5));
    assertEquals(-1, lastIndexOf(array, null, 2));
    assertEquals(-1, lastIndexOf(array, "notInArray", 5));

    assertEquals(-1, lastIndexOf(array, null, -1));
    assertEquals(5, lastIndexOf(array, "0", 88));
  }

  @Test
  public void testContains() {
    final Object[] array = {"0", "1", "2", "3", null, "0"};
    assertFalse(contains(null, null));
    assertFalse(contains(null, "1"));
    assertTrue(contains(array, "0"));
    assertTrue(contains(array, "1"));
    assertTrue(contains(array, "2"));
    assertTrue(contains(array, "3"));
    assertTrue(contains(array, null));
    assertFalse(contains(array, "notInArray"));
  }

  @Test
  public void testIndexOfLong() {
    long[] array = null;
    assertEquals(-1, indexOf(array, 0));
    array = new long[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, 0));
    assertEquals(1, indexOf(array, 1));
    assertEquals(2, indexOf(array, 2));
    assertEquals(3, indexOf(array, 3));
    assertEquals(-1, indexOf(array, 99));
  }

  @Test
  public void testIndexOfLongWithStartIndex() {
    long[] array = null;
    assertEquals(-1, indexOf(array, 0, 2));
    array = new long[]{0, 1, 2, 3, 0};
    assertEquals(4, indexOf(array, 0, 2));
    assertEquals(-1, indexOf(array, 1, 2));
    assertEquals(2, indexOf(array, 2, 2));
    assertEquals(3, indexOf(array, 3, 2));
    assertEquals(3, indexOf(array, 3, -1));
    assertEquals(-1, indexOf(array, 99, 0));
    assertEquals(-1, indexOf(array, 0, 6));
  }

  @Test
  public void testLastIndexOfLong() {
    long[] array = null;
    assertEquals(-1, lastIndexOf(array, 0));
    array = new long[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, 0));
    assertEquals(1, lastIndexOf(array, 1));
    assertEquals(2, lastIndexOf(array, 2));
    assertEquals(3, lastIndexOf(array, 3));
    assertEquals(-1, lastIndexOf(array, 99));
  }

  @Test
  public void testLastIndexOfLongWithStartIndex() {
    long[] array = null;
    assertEquals(-1, lastIndexOf(array, 0, 2));
    array = new long[]{0, 1, 2, 3, 0};
    assertEquals(0, lastIndexOf(array, 0, 2));
    assertEquals(1, lastIndexOf(array, 1, 2));
    assertEquals(2, lastIndexOf(array, 2, 2));
    assertEquals(-1, lastIndexOf(array, 3, 2));
    assertEquals(-1, lastIndexOf(array, 3, -1));
    assertEquals(-1, lastIndexOf(array, 99, 4));
    assertEquals(4, lastIndexOf(array, 0, 88));
  }

  @Test
  public void testContainsLong() {
    long[] array = null;
    assertFalse(contains(array, 1));
    array = new long[]{0, 1, 2, 3, 0};
    assertTrue(contains(array, 0));
    assertTrue(contains(array, 1));
    assertTrue(contains(array, 2));
    assertTrue(contains(array, 3));
    assertFalse(contains(array, 99));
  }

  @Test
  public void testIndexOfInt() {
    int[] array = null;
    assertEquals(-1, indexOf(array, 0));
    array = new int[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, 0));
    assertEquals(1, indexOf(array, 1));
    assertEquals(2, indexOf(array, 2));
    assertEquals(3, indexOf(array, 3));
    assertEquals(-1, indexOf(array, 99));
  }

  @Test
  public void testIndexOfIntWithStartIndex() {
    int[] array = null;
    assertEquals(-1, indexOf(array, 0, 2));
    array = new int[]{0, 1, 2, 3, 0};
    assertEquals(4, indexOf(array, 0, 2));
    assertEquals(-1, indexOf(array, 1, 2));
    assertEquals(2, indexOf(array, 2, 2));
    assertEquals(3, indexOf(array, 3, 2));
    assertEquals(3, indexOf(array, 3, -1));
    assertEquals(-1, indexOf(array, 99, 0));
    assertEquals(-1, indexOf(array, 0, 6));
  }

  @Test
  public void testLastIndexOfInt() {
    int[] array = null;
    assertEquals(-1, lastIndexOf(array, 0));
    array = new int[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, 0));
    assertEquals(1, lastIndexOf(array, 1));
    assertEquals(2, lastIndexOf(array, 2));
    assertEquals(3, lastIndexOf(array, 3));
    assertEquals(-1, lastIndexOf(array, 99));
  }

  @Test
  public void testLastIndexOfIntWithStartIndex() {
    int[] array = null;
    assertEquals(-1, lastIndexOf(array, 0, 2));
    array = new int[]{0, 1, 2, 3, 0};
    assertEquals(0, lastIndexOf(array, 0, 2));
    assertEquals(1, lastIndexOf(array, 1, 2));
    assertEquals(2, lastIndexOf(array, 2, 2));
    assertEquals(-1, lastIndexOf(array, 3, 2));
    assertEquals(-1, lastIndexOf(array, 3, -1));
    assertEquals(-1, lastIndexOf(array, 99));
    assertEquals(4, lastIndexOf(array, 0, 88));
  }

  @Test
  public void testContainsInt() {
    int[] array = null;
    assertFalse(contains(array, 1));
    array = new int[]{0, 1, 2, 3, 0};
    assertTrue(contains(array, 0));
    assertTrue(contains(array, 1));
    assertTrue(contains(array, 2));
    assertTrue(contains(array, 3));
    assertFalse(contains(array, 99));
  }

  @Test
  public void testIndexOfShort() {
    short[] array = null;
    assertEquals(-1, indexOf(array, (short) 0));
    array = new short[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, (short) 0));
    assertEquals(1, indexOf(array, (short) 1));
    assertEquals(2, indexOf(array, (short) 2));
    assertEquals(3, indexOf(array, (short) 3));
    assertEquals(-1, indexOf(array, (short) 99));
  }

  @Test
  public void testIndexOfShortWithStartIndex() {
    short[] array = null;
    assertEquals(-1, indexOf(array, (short) 0, 2));
    array = new short[]{0, 1, 2, 3, 0};
    assertEquals(4, indexOf(array, (short) 0, 2));
    assertEquals(-1, indexOf(array, (short) 1, 2));
    assertEquals(2, indexOf(array, (short) 2, 2));
    assertEquals(3, indexOf(array, (short) 3, 2));
    assertEquals(3, indexOf(array, (short) 3, -1));
    assertEquals(-1, indexOf(array, (short) 99, 0));
    assertEquals(-1, indexOf(array, (short) 0, 6));
  }

  @Test
  public void testLastIndexOfShort() {
    short[] array = null;
    assertEquals(-1, lastIndexOf(array, (short) 0));
    array = new short[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, (short) 0));
    assertEquals(1, lastIndexOf(array, (short) 1));
    assertEquals(2, lastIndexOf(array, (short) 2));
    assertEquals(3, lastIndexOf(array, (short) 3));
    assertEquals(-1, lastIndexOf(array, (short) 99));
  }

  @Test
  public void testLastIndexOfShortWithStartIndex() {
    short[] array = null;
    assertEquals(-1, lastIndexOf(array, (short) 0, 2));
    array = new short[]{0, 1, 2, 3, 0};
    assertEquals(0, lastIndexOf(array, (short) 0, 2));
    assertEquals(1, lastIndexOf(array, (short) 1, 2));
    assertEquals(2, lastIndexOf(array, (short) 2, 2));
    assertEquals(-1, lastIndexOf(array, (short) 3, 2));
    assertEquals(-1, lastIndexOf(array, (short) 3, -1));
    assertEquals(-1, lastIndexOf(array, (short) 99));
    assertEquals(4, lastIndexOf(array, (short) 0, 88));
  }

  @Test
  public void testContainsShort() {
    short[] array = null;
    assertFalse(contains(array, (short) 1));
    array = new short[]{0, 1, 2, 3, 0};
    assertTrue(contains(array, (short) 0));
    assertTrue(contains(array, (short) 1));
    assertTrue(contains(array, (short) 2));
    assertTrue(contains(array, (short) 3));
    assertFalse(contains(array, (short) 99));
  }

  @Test
  public void testIndexOfChar() {
    char[] array = null;
    assertEquals(-1, indexOf(array, 'a'));
    array = new char[]{'a', 'b', 'c', 'd', 'a'};
    assertEquals(0, indexOf(array, 'a'));
    assertEquals(1, indexOf(array, 'b'));
    assertEquals(2, indexOf(array, 'c'));
    assertEquals(3, indexOf(array, 'd'));
    assertEquals(-1, indexOf(array, 'e'));
  }

  @Test
  public void testIndexOfCharWithStartIndex() {
    char[] array = null;
    assertEquals(-1, indexOf(array, 'a', 2));
    array = new char[]{'a', 'b', 'c', 'd', 'a'};
    assertEquals(4, indexOf(array, 'a', 2));
    assertEquals(-1, indexOf(array, 'b', 2));
    assertEquals(2, indexOf(array, 'c', 2));
    assertEquals(3, indexOf(array, 'd', 2));
    assertEquals(3, indexOf(array, 'd', -1));
    assertEquals(-1, indexOf(array, 'e', 0));
    assertEquals(-1, indexOf(array, 'a', 6));
  }

  @Test
  public void testLastIndexOfChar() {
    char[] array = null;
    assertEquals(-1, lastIndexOf(array, 'a'));
    array = new char[]{'a', 'b', 'c', 'd', 'a'};
    assertEquals(4, lastIndexOf(array, 'a'));
    assertEquals(1, lastIndexOf(array, 'b'));
    assertEquals(2, lastIndexOf(array, 'c'));
    assertEquals(3, lastIndexOf(array, 'd'));
    assertEquals(-1, lastIndexOf(array, 'e'));
  }

  @Test
  public void testLastIndexOfCharWithStartIndex() {
    char[] array = null;
    assertEquals(-1, lastIndexOf(array, 'a', 2));
    array = new char[]{'a', 'b', 'c', 'd', 'a'};
    assertEquals(0, lastIndexOf(array, 'a', 2));
    assertEquals(1, lastIndexOf(array, 'b', 2));
    assertEquals(2, lastIndexOf(array, 'c', 2));
    assertEquals(-1, lastIndexOf(array, 'd', 2));
    assertEquals(-1, lastIndexOf(array, 'd', -1));
    assertEquals(-1, lastIndexOf(array, 'e'));
    assertEquals(4, lastIndexOf(array, 'a', 88));
  }

  @Test
  public void testContainsChar() {
    char[] array = null;
    assertFalse(contains(array, 'b'));
    array = new char[]{'a', 'b', 'c', 'd', 'a'};
    assertTrue(contains(array, 'a'));
    assertTrue(contains(array, 'b'));
    assertTrue(contains(array, 'c'));
    assertTrue(contains(array, 'd'));
    assertFalse(contains(array, 'e'));
  }

  @Test
  public void testIndexOfByte() {
    byte[] array = null;
    assertEquals(-1, indexOf(array, (byte) 0));
    array = new byte[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, (byte) 0));
    assertEquals(1, indexOf(array, (byte) 1));
    assertEquals(2, indexOf(array, (byte) 2));
    assertEquals(3, indexOf(array, (byte) 3));
    assertEquals(-1, indexOf(array, (byte) 99));
  }

  @Test
  public void testIndexOfByteWithStartIndex() {
    byte[] array = null;
    assertEquals(-1, indexOf(array, (byte) 0, 2));
    array = new byte[]{0, 1, 2, 3, 0};
    assertEquals(4, indexOf(array, (byte) 0, 2));
    assertEquals(-1, indexOf(array, (byte) 1, 2));
    assertEquals(2, indexOf(array, (byte) 2, 2));
    assertEquals(3, indexOf(array, (byte) 3, 2));
    assertEquals(3, indexOf(array, (byte) 3, -1));
    assertEquals(-1, indexOf(array, (byte) 99, 0));
    assertEquals(-1, indexOf(array, (byte) 0, 6));
  }

  @Test
  public void testLastIndexOfByte() {
    byte[] array = null;
    assertEquals(-1, lastIndexOf(array, (byte) 0));
    array = new byte[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, (byte) 0));
    assertEquals(1, lastIndexOf(array, (byte) 1));
    assertEquals(2, lastIndexOf(array, (byte) 2));
    assertEquals(3, lastIndexOf(array, (byte) 3));
    assertEquals(-1, lastIndexOf(array, (byte) 99));
  }

  @Test
  public void testLastIndexOfByteWithStartIndex() {
    byte[] array = null;
    assertEquals(-1, lastIndexOf(array, (byte) 0, 2));
    array = new byte[]{0, 1, 2, 3, 0};
    assertEquals(0, lastIndexOf(array, (byte) 0, 2));
    assertEquals(1, lastIndexOf(array, (byte) 1, 2));
    assertEquals(2, lastIndexOf(array, (byte) 2, 2));
    assertEquals(-1, lastIndexOf(array, (byte) 3, 2));
    assertEquals(-1, lastIndexOf(array, (byte) 3, -1));
    assertEquals(-1, lastIndexOf(array, (byte) 99));
    assertEquals(4, lastIndexOf(array, (byte) 0, 88));
  }

  @Test
  public void testContainsByte() {
    byte[] array = null;
    assertFalse(contains(array, (byte) 1));
    array = new byte[]{0, 1, 2, 3, 0};
    assertTrue(contains(array, (byte) 0));
    assertTrue(contains(array, (byte) 1));
    assertTrue(contains(array, (byte) 2));
    assertTrue(contains(array, (byte) 3));
    assertFalse(contains(array, (byte) 99));
  }

  @Test
  public void testIndexOfDouble() {
    double[] array = null;
    assertEquals(-1, indexOf(array, 0));
    array = new double[0];
    assertEquals(-1, indexOf(array, 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, 0));
    assertEquals(1, indexOf(array, 1));
    assertEquals(2, indexOf(array, 2));
    assertEquals(3, indexOf(array, 3));
    assertEquals(3, indexOf(array, 3, -1));
    assertEquals(-1, indexOf(array, 99));
  }

  @Test
  public void testIndexOfDoubleTolerance() {
    double[] array = null;
    assertEquals(-1, indexOf(array, 0, (double) 0));
    array = new double[0];
    assertEquals(-1, indexOf(array, 0, (double) 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, 0, 0.3));
    assertEquals(2, indexOf(array, 2.2, 0.35));
    assertEquals(3, indexOf(array, 4.15, 2.0));
    assertEquals(1, indexOf(array, 1.00001324, 0.0001));
  }

  @Test
  public void testIndexOfDoubleWithStartIndex() {
    double[] array = null;
    assertEquals(-1, indexOf(array, 0, 2));
    array = new double[0];
    assertEquals(-1, indexOf(array, 0, 2));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(4, indexOf(array, 0, 2));
    assertEquals(-1, indexOf(array, 1, 2));
    assertEquals(2, indexOf(array, 2, 2));
    assertEquals(3, indexOf(array, 3, 2));
    assertEquals(-1, indexOf(array, 99, 0));
    assertEquals(-1, indexOf(array, 0, 6));
  }

  @Test
  public void testIndexOfDoubleWithStartIndexTolerance() {
    double[] array = null;
    assertEquals(-1, indexOf(array, 0, 2, 0));
    array = new double[0];
    assertEquals(-1, indexOf(array, 0, 2, 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(-1, indexOf(array, 0, 99, 0.3));
    assertEquals(0, indexOf(array, 0, 0, 0.3));
    assertEquals(4, indexOf(array, 0, 3, 0.3));
    assertEquals(2, indexOf(array, 2.2, 0, 0.35));
    assertEquals(3, indexOf(array, 4.15, 0, 2.0));
    assertEquals(1, indexOf(array, 1.00001324, 0,
        0.0001));
    assertEquals(3, indexOf(array, 4.15, -1, 2.0));
    assertEquals(1, indexOf(array, 1.00001324, -300,
        0.0001));
  }

  @Test
  public void testLastIndexOfDouble() {
    double[] array = null;
    assertEquals(-1, lastIndexOf(array, 0));
    array = new double[0];
    assertEquals(-1, lastIndexOf(array, 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, 0));
    assertEquals(1, lastIndexOf(array, 1));
    assertEquals(2, lastIndexOf(array, 2));
    assertEquals(3, lastIndexOf(array, 3));
    assertEquals(-1, lastIndexOf(array, 99));
  }

  @Test
  public void testLastIndexOfDoubleTolerance() {
    double[] array = null;
    assertEquals(-1, lastIndexOf(array, 0, (double) 0));
    array = new double[0];
    assertEquals(-1, lastIndexOf(array, 0, (double) 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, 0, 0.3));
    assertEquals(2, lastIndexOf(array, 2.2, 0.35));
    assertEquals(3, lastIndexOf(array, 4.15, 2.0));
    assertEquals(1, lastIndexOf(array, 1.00001324,
        0.0001));
  }

  @Test
  public void testLastIndexOfDoubleWithStartIndex() {
    double[] array = null;
    assertEquals(-1, lastIndexOf(array, 0, 2));
    array = new double[0];
    assertEquals(-1, lastIndexOf(array, 0, 2));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(0, lastIndexOf(array, 0, 2));
    assertEquals(1, lastIndexOf(array, 1, 2));
    assertEquals(2, lastIndexOf(array, 2, 2));
    assertEquals(-1, lastIndexOf(array, 3, 2));
    assertEquals(-1, lastIndexOf(array, 3, -1));
    assertEquals(-1, lastIndexOf(array, 99));
    assertEquals(4, lastIndexOf(array, 0, 88));
  }

  @Test
  public void testLastIndexOfDoubleWithStartIndexTolerance() {
    double[] array = null;
    assertEquals(-1, lastIndexOf(array, 0, 2, 0));
    array = new double[0];
    assertEquals(-1, lastIndexOf(array, 0, 2, 0));
    array = new double[]{(double) 3};
    assertEquals(-1, lastIndexOf(array, 1, 0, 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, 0, 99, 0.3));
    assertEquals(0, lastIndexOf(array, 0, 3, 0.3));
    assertEquals(2, lastIndexOf(array, 2.2, 3, 0.35));
    assertEquals(3, lastIndexOf(array, 4.15, array.length,
        2.0));
    assertEquals(1, lastIndexOf(array, 1.00001324,
        array.length, 0.0001));
    assertEquals(-1, lastIndexOf(array, 4.15, -200,
        2.0));
  }

  @Test
  public void testContainsDouble() {
    double[] array = null;
    assertFalse(contains(array, 1));
    array = new double[]{0, 1, 2, 3, 0};
    assertTrue(contains(array, 0));
    assertTrue(contains(array, 1));
    assertTrue(contains(array, 2));
    assertTrue(contains(array, 3));
    assertFalse(contains(array, 99));
  }

  @Test
  public void testContainsDoubleTolerance() {
    double[] array = null;
    assertFalse(contains(array, 1, 0));
    array = new double[]{0, 1, 2, 3, 0};
    assertFalse(contains(array, 4.0, 0.33));
    assertFalse(contains(array, 2.5, 0.49));
    assertTrue(contains(array, 2.5, 0.50));
    assertTrue(contains(array, 2.5, 0.51));
  }

  @Test
  public void testIndexOFloat() {
    float[] array = null;
    assertEquals(-1, indexOf(array, (float) 0));
    array = new float[0];
    assertEquals(-1, indexOf(array, (float) 0));
    array = new float[]{0, 1, 2, 3, 0};
    assertEquals(0, indexOf(array, (float) 0));
    assertEquals(1, indexOf(array, (float) 1));
    assertEquals(2, indexOf(array, (float) 2));
    assertEquals(3, indexOf(array, (float) 3));
    assertEquals(-1, indexOf(array, (float) 99));
  }

  @Test
  public void testIndexOFloatWithStartIndex() {
    float[] array = null;
    assertEquals(-1, indexOf(array, (float) 0, 2));
    array = new float[0];
    assertEquals(-1, indexOf(array, (float) 0, 2));
    array = new float[]{0, 1, 2, 3, 0};
    assertEquals(4, indexOf(array, (float) 0, 2));
    assertEquals(-1, indexOf(array, (float) 1, 2));
    assertEquals(2, indexOf(array, (float) 2, 2));
    assertEquals(3, indexOf(array, (float) 3, 2));
    assertEquals(3, indexOf(array, (float) 3, -1));
    assertEquals(-1, indexOf(array, (float) 99, 0));
    assertEquals(-1, indexOf(array, (float) 0, 6));
  }

  @Test
  public void testLastIndexOFloat() {
    float[] array = null;
    assertEquals(-1, lastIndexOf(array, (float) 0));
    array = new float[0];
    assertEquals(-1, lastIndexOf(array, (float) 0));
    array = new float[]{0, 1, 2, 3, 0};
    assertEquals(4, lastIndexOf(array, (float) 0));
    assertEquals(1, lastIndexOf(array, (float) 1));
    assertEquals(2, lastIndexOf(array, (float) 2));
    assertEquals(3, lastIndexOf(array, (float) 3));
    assertEquals(-1, lastIndexOf(array, (float) 99));
  }

  @Test
  public void testLastIndexOFloatWithStartIndex() {
    float[] array = null;
    assertEquals(-1, lastIndexOf(array, (float) 0, 2));
    array = new float[0];
    assertEquals(-1, lastIndexOf(array, (float) 0, 2));
    array = new float[]{0, 1, 2, 3, 0};
    assertEquals(0, lastIndexOf(array, (float) 0, 2));
    assertEquals(1, lastIndexOf(array, (float) 1, 2));
    assertEquals(2, lastIndexOf(array, (float) 2, 2));
    assertEquals(-1, lastIndexOf(array, (float) 3, 2));
    assertEquals(-1, lastIndexOf(array, (float) 3, -1));
    assertEquals(-1, lastIndexOf(array, (float) 99));
    assertEquals(4, lastIndexOf(array, (float) 0, 88));
  }

  @Test
  public void testContainsFloat() {
    float[] array = null;
    assertFalse(contains(array, (float) 1));
    array = new float[]{0, 1, 2, 3, 0};
    assertTrue(contains(array, (float) 0));
    assertTrue(contains(array, (float) 1));
    assertTrue(contains(array, (float) 2));
    assertTrue(contains(array, (float) 3));
    assertFalse(contains(array, (float) 99));
  }

  @Test
  public void testIndexOfBoolean() {
    boolean[] array = null;
    assertEquals(-1, indexOf(array, true));
    array = new boolean[0];
    assertEquals(-1, indexOf(array, true));
    array = new boolean[]{true, false, true};
    assertEquals(0, indexOf(array, true));
    assertEquals(1, indexOf(array, false));
    array = new boolean[]{true, true};
    assertEquals(-1, indexOf(array, false));
  }

  @Test
  public void testIndexOfBooleanWithStartIndex() {
    boolean[] array = null;
    assertEquals(-1, indexOf(array, true, 2));
    array = new boolean[0];
    assertEquals(-1, indexOf(array, true, 2));
    array = new boolean[]{true, false, true};
    assertEquals(2, indexOf(array, true, 1));
    assertEquals(-1, indexOf(array, false, 2));
    assertEquals(1, indexOf(array, false, 0));
    assertEquals(1, indexOf(array, false, -1));
    array = new boolean[]{true, true};
    assertEquals(-1, indexOf(array, false, 0));
    assertEquals(-1, indexOf(array, false, -1));
  }

  @Test
  public void testLastIndexOfBoolean() {
    boolean[] array = null;
    assertEquals(-1, lastIndexOf(array, true));
    array = new boolean[0];
    assertEquals(-1, lastIndexOf(array, true));
    array = new boolean[]{true, false, true};
    assertEquals(2, lastIndexOf(array, true));
    assertEquals(1, lastIndexOf(array, false));
    array = new boolean[]{true, true};
    assertEquals(-1, lastIndexOf(array, false));
  }

  @Test
  public void testLastIndexOfBooleanWithStartIndex() {
    boolean[] array = null;
    assertEquals(-1, lastIndexOf(array, true, 2));
    array = new boolean[0];
    assertEquals(-1, lastIndexOf(array, true, 2));
    array = new boolean[]{true, false, true};
    assertEquals(2, lastIndexOf(array, true, 2));
    assertEquals(0, lastIndexOf(array, true, 1));
    assertEquals(1, lastIndexOf(array, false, 2));
    assertEquals(-1, lastIndexOf(array, true, -1));
    array = new boolean[]{true, true};
    assertEquals(-1, lastIndexOf(array, false, 2));
    assertEquals(-1, lastIndexOf(array, true, -1));
  }

  @Test
  public void testContainsBoolean() {
    boolean[] array = null;
    assertFalse(contains(array, true));
    array = new boolean[]{true, false, true};
    assertTrue(contains(array, true));
    assertTrue(contains(array, false));
    array = new boolean[]{true, true};
    assertTrue(contains(array, true));
    assertFalse(contains(array, false));
  }

  @Test
  public void testToPrimitive_boolean() {
    final Boolean[] b = null;
    assertNull(toPrimitive(b));
    assertSame(EMPTY_BOOLEAN_ARRAY,
        toPrimitive(new Boolean[0]));
    assertArrayEquals(new boolean[]{true, false, true},
        toPrimitive(new Boolean[]{Boolean.TRUE, Boolean.FALSE,
            Boolean.TRUE}));

    try {
      toPrimitive(new Boolean[]{Boolean.TRUE, null});
      fail();
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testToPrimitive_boolean_boolean() {
    assertNull(toPrimitive(null, false));
    assertSame(EMPTY_BOOLEAN_ARRAY,
        toPrimitive(new Boolean[0],
            false));
    assertArrayEquals(new boolean[]{true, false, true},
        toPrimitive(new Boolean[]{Boolean.TRUE, Boolean.FALSE,
            Boolean.TRUE}, false));
    assertArrayEquals(new boolean[]{true, false, false},
        toPrimitive(new Boolean[]{Boolean.TRUE, null,
            Boolean.FALSE}, false));
    assertArrayEquals(new boolean[]{true, true, false},
        toPrimitive(new Boolean[]{Boolean.TRUE, null,
            Boolean.FALSE}, true));
  }

  @Test
  public void testToObject_boolean() {
    final boolean[] b = null;
    assertNull(toObject(b));
    assertSame(EMPTY_BOOLEAN_OBJECT_ARRAY,
        toObject(new boolean[0]));
    assertTrue(Equality
        .equals(new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE},
            toObject(new boolean[]{true, false, true})));
  }

  @Test
  public void testToPrimitive_char() {
    final Character[] b = null;
    assertNull(toPrimitive(b));
    assertSame(EMPTY_CHAR_ARRAY,
        toPrimitive(new Character[0]));
    assertArrayEquals(new char[]{Character.MIN_VALUE, Character.MAX_VALUE, '0'},
        toPrimitive(new Character[]{Character.MIN_VALUE, Character.MAX_VALUE, '0'}));
    try {
      toPrimitive(new Character[]{Character.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testToPrimitive_char_char() {
    final Character[] b = null;
    assertNull(toPrimitive(b, Character.MIN_VALUE));

    assertSame(EMPTY_CHAR_ARRAY,
        toPrimitive(new Character[0],
            (char) 0));

    assertArrayEquals(new char[]{Character.MIN_VALUE, Character.MAX_VALUE, '0'},
        toPrimitive(new Character[]{Character.MIN_VALUE, Character.MAX_VALUE, '0'},
            Character.MIN_VALUE));

    assertArrayEquals(new char[]{Character.MIN_VALUE, Character.MAX_VALUE, '0'},
        toPrimitive(new Character[]{Character.MIN_VALUE, null, '0'}, Character.MAX_VALUE));
  }

  @Test
  public void testToObject_char() {
    final char[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_CHARACTER_OBJECT_ARRAY, toObject(new char[0]));

    assertArrayEquals(new Character[]{Character.MIN_VALUE,
        Character.MAX_VALUE, '0'},
        toObject(new char[]{Character.MIN_VALUE, Character.MAX_VALUE, '0'}));
  }

  @Test
  public void testToPrimitive_byte() {
    final Byte[] b = null;
    assertNull(toPrimitive(b));

    assertSame(EMPTY_BYTE_ARRAY,
        toPrimitive(new Byte[0]));

    assertArrayEquals(new byte[]{Byte.MIN_VALUE, Byte.MAX_VALUE, (byte) 9999999},
        toPrimitive(new Byte[]{Byte.MIN_VALUE,
            Byte.MAX_VALUE, (byte) 9999999}));

    try {
      toPrimitive(new Byte[]{Byte.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testToPrimitive_byte_byte() {
    final Byte[] b = null;
    assertNull(toPrimitive(b, Byte.MIN_VALUE));

    assertSame(EMPTY_BYTE_ARRAY, toPrimitive(new Byte[0],
        (byte) 1));

    assertArrayEquals(new byte[]{Byte.MIN_VALUE, Byte.MAX_VALUE, (byte) 9999999},
        toPrimitive(new Byte[]{Byte.MIN_VALUE, Byte.MAX_VALUE,
            (byte) 9999999}, Byte.MIN_VALUE));

    assertArrayEquals(new byte[]{Byte.MIN_VALUE, Byte.MAX_VALUE, (byte) 9999999},
        toPrimitive(new Byte[]{Byte.MIN_VALUE, null,
            (byte) 9999999}, Byte.MAX_VALUE));
  }

  @Test
  public void testToObject_byte() {
    final byte[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_BYTE_OBJECT_ARRAY,
        toObject(new byte[0]));

    assertArrayEquals(
        new Byte[]{Byte.MIN_VALUE, Byte.MAX_VALUE, (byte) 9999999},
        toObject(new byte[]{Byte.MIN_VALUE, Byte.MAX_VALUE, (byte) 9999999}));
  }

  @Test
  public void testToPrimitive_short() {
    final Short[] b = null;
    assertNull(toPrimitive(b));

    assertSame(EMPTY_SHORT_ARRAY,
        toPrimitive(new Short[0]));

    assertArrayEquals(new short[]{Short.MIN_VALUE, Short.MAX_VALUE, (short) 9999999},
        toPrimitive(new Short[]{Short.MIN_VALUE, Short.MAX_VALUE,
            (short) 9999999}));

    try {
      toPrimitive(new Short[]{Short.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testToPrimitive_short_short() {
    final Short[] s = null;
    assertNull(toPrimitive(s, Short.MIN_VALUE));

    assertSame(EMPTY_SHORT_ARRAY,
        toPrimitive(new Short[0],
            Short.MIN_VALUE));

    assertArrayEquals(new short[]{Short.MIN_VALUE, Short.MAX_VALUE, (short) 9999999},
        toPrimitive(new Short[]{Short.MIN_VALUE, Short.MAX_VALUE, (short) 9999999},
            Short.MIN_VALUE));

    assertArrayEquals(new short[]{Short.MIN_VALUE, Short.MAX_VALUE, (short) 9999999},
        toPrimitive(new Short[]{Short.MIN_VALUE, null, (short) 9999999}, Short.MAX_VALUE));
  }

  @Test
  public void testToObject_short() {
    final short[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_SHORT_OBJECT_ARRAY,
        toObject(new short[0]));

    assertArrayEquals(new Short[]{Short.MIN_VALUE, Short.MAX_VALUE, (short) 9999999},
        toObject(new short[]{Short.MIN_VALUE, Short.MAX_VALUE, (short) 9999999}));
  }

  @Test
  public void testToPrimitive_int() {
    final Integer[] b = null;
    assertNull(toPrimitive(b));
    assertSame(EMPTY_INT_ARRAY, toPrimitive(new Integer[0]));
    assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999},
        toPrimitive(new Integer[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999}));

    try {
      toPrimitive(new Integer[]{Integer.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testToPrimitive_int_int() {
    final Long[] theNull = null;
    assertNull(toPrimitive(theNull, Integer.MIN_VALUE));
    assertSame(EMPTY_INT_ARRAY,
        toPrimitive(new Integer[0], 1));
    assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999},
        toPrimitive(new Integer[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999}, 1));
    assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999},
        toPrimitive(new Integer[]{Integer.MIN_VALUE, null, 9999999}, Integer.MAX_VALUE));
  }

  @Test
  public void testToPrimitive_intNull() {
    final Integer[] iArray = null;
    assertNull(toPrimitive(iArray, Integer.MIN_VALUE));
  }

  @Test
  public void testToObject_int() {
    final int[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_INTEGER_OBJECT_ARRAY,
        toObject(new int[0]));

    assertArrayEquals(new Integer[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999},
        toObject(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 9999999}));
  }

  @Test
  public void testToPrimitive_long() {
    final Long[] b = null;
    assertNull(toPrimitive(b));

    assertSame(EMPTY_LONG_ARRAY,
        toPrimitive(new Long[0]));

    assertArrayEquals(new long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999L},
        toPrimitive(new Long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999L}));

    try {
      toPrimitive(new Long[]{Long.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      // pass
    }
  }

  @Test
  public void testToPrimitive_long_long() {
    final Long[] l = null;
    assertNull(toPrimitive(l, Long.MIN_VALUE));

    assertSame(EMPTY_LONG_ARRAY,
        toPrimitive(new Long[0], 1));

    assertArrayEquals(new long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999L},
        toPrimitive(new Long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999L}, 1));

    assertArrayEquals(new long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999},
        toPrimitive(new Long[]{Long.MIN_VALUE, null, 9999999L}, Long.MAX_VALUE));
  }

  @Test
  public void testToObject_long() {
    final long[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_LONG_OBJECT_ARRAY, toObject(new long[0]));

    assertArrayEquals(new Long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999L},
        toObject(new long[]{Long.MIN_VALUE, Long.MAX_VALUE, 9999999}));
  }

  @Test
  public void testToPrimitive_float() {
    final Float[] b = null;
    assertNull(toPrimitive(b));

    assertSame(EMPTY_FLOAT_ARRAY,
        toPrimitive(new Float[0]));

    assertArrayEquals(new float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999f},
        toPrimitive(new Float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999f}));

    try {
      toPrimitive(new Float[]{Float.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      // pass
    }
  }

  @Test
  public void testToPrimitive_float_float() {
    final Float[] l = null;
    assertNull(toPrimitive(l, Float.MIN_VALUE));

    assertSame(EMPTY_FLOAT_ARRAY,
        toPrimitive(new Float[0], 1));

    assertArrayEquals(new float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999.0f},
        toPrimitive(new Float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999.0f}, 1.0f));

    assertArrayEquals(new float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999.0f},
        toPrimitive(new Float[]{Float.MIN_VALUE, null, 9999999.0f}, Float.MAX_VALUE));
  }

  @Test
  public void testToObject_float() {
    final float[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_FLOAT_OBJECT_ARRAY,
        toObject(new float[0]));

    assertArrayEquals(new Float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999f},
        toObject(new float[]{Float.MIN_VALUE, Float.MAX_VALUE, 9999999f}));
  }

  @Test
  public void testToPrimitive_double() {
    final Double[] b = null;
    assertNull(toPrimitive(b));

    assertSame(EMPTY_DOUBLE_ARRAY,
        toPrimitive(new Double[0]));

    assertArrayEquals(new double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999.0},
        toPrimitive(new Double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999.0}));

    try {
      toPrimitive(new Float[]{Float.MIN_VALUE, null});
      fail();
    } catch (final NullPointerException ex) {
      // pass
    }
  }

  @Test
  public void testToPrimitive_double_double() {
    final Double[] l = null;
    assertNull(toPrimitive(l, Double.MIN_VALUE));

    assertSame(EMPTY_DOUBLE_ARRAY,
        toPrimitive(new Double[0], 1));

    assertArrayEquals(new double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999},
        toPrimitive(new Double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999.0}, 1));

    assertArrayEquals(new double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999},
        toPrimitive(new Double[]{Double.MIN_VALUE, null, 9999999.0}, Double.MAX_VALUE));
  }

  @Test
  public void testToObject_double() {
    final double[] b = null;
    assertNull(toObject(b));

    assertSame(EMPTY_DOUBLE_OBJECT_ARRAY,
        toObject(new double[0]));

    assertArrayEquals(new Double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999.0},
        toObject(new double[]{Double.MIN_VALUE, Double.MAX_VALUE, 9999999.0}));
  }

  @Test
  public void testIsEmptyObject() {
    final Object[] emptyArray = {};
    final Object[] notEmptyArray = {"Value"};
    assertTrue(isEmpty((Object[]) null));
    assertTrue(isEmpty(emptyArray));
    assertFalse(isEmpty(notEmptyArray));
  }

  @Test
  public void testIsEmptyPrimitives() {
    final long[] emptyLongArray = new long[]{};
    final long[] notEmptyLongArray = new long[]{1L};
    assertTrue(isEmpty((long[]) null));
    assertTrue(isEmpty(emptyLongArray));
    assertFalse(isEmpty(notEmptyLongArray));

    final int[] emptyIntArray = new int[]{};
    final int[] notEmptyIntArray = new int[]{1};
    assertTrue(isEmpty((int[]) null));
    assertTrue(isEmpty(emptyIntArray));
    assertFalse(isEmpty(notEmptyIntArray));

    final short[] emptyShortArray = new short[]{};
    final short[] notEmptyShortArray = new short[]{1};
    assertTrue(isEmpty((short[]) null));
    assertTrue(isEmpty(emptyShortArray));
    assertFalse(isEmpty(notEmptyShortArray));

    final char[] emptyCharArray = new char[]{};
    final char[] notEmptyCharArray = new char[]{1};
    assertTrue(isEmpty((char[]) null));
    assertTrue(isEmpty(emptyCharArray));
    assertFalse(isEmpty(notEmptyCharArray));

    final byte[] emptyByteArray = new byte[]{};
    final byte[] notEmptyByteArray = new byte[]{1};
    assertTrue(isEmpty((byte[]) null));
    assertTrue(isEmpty(emptyByteArray));
    assertFalse(isEmpty(notEmptyByteArray));

    final double[] emptyDoubleArray = new double[]{};
    final double[] notEmptyDoubleArray = new double[]{1.0};
    assertTrue(isEmpty((double[]) null));
    assertTrue(isEmpty(emptyDoubleArray));
    assertFalse(isEmpty(notEmptyDoubleArray));

    final float[] emptyFloatArray = new float[]{};
    final float[] notEmptyFloatArray = new float[]{1.0F};
    assertTrue(isEmpty((float[]) null));
    assertTrue(isEmpty(emptyFloatArray));
    assertFalse(isEmpty(notEmptyFloatArray));

    final boolean[] emptyBooleanArray = new boolean[]{};
    final boolean[] notEmptyBooleanArray = new boolean[]{true};
    assertTrue(isEmpty((boolean[]) null));
    assertTrue(isEmpty(emptyBooleanArray));
    assertFalse(isEmpty(notEmptyBooleanArray));
  }

  @Test
  public void testGetLength() {
    assertEquals(0, getLength(null));

    final Object[] emptyObjectArray = new Object[0];
    final Object[] notEmptyObjectArray = {"aValue"};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyObjectArray));
    assertEquals(1, getLength(notEmptyObjectArray));

    final int[] emptyIntArray = new int[]{};
    final int[] notEmptyIntArray = new int[]{1};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyIntArray));
    assertEquals(1, getLength(notEmptyIntArray));

    final short[] emptyShortArray = new short[]{};
    final short[] notEmptyShortArray = new short[]{1};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyShortArray));
    assertEquals(1, getLength(notEmptyShortArray));

    final char[] emptyCharArray = new char[]{};
    final char[] notEmptyCharArray = new char[]{1};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyCharArray));
    assertEquals(1, getLength(notEmptyCharArray));

    final byte[] emptyByteArray = new byte[]{};
    final byte[] notEmptyByteArray = new byte[]{1};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyByteArray));
    assertEquals(1, getLength(notEmptyByteArray));

    final double[] emptyDoubleArray = new double[]{};
    final double[] notEmptyDoubleArray = new double[]{1.0};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyDoubleArray));
    assertEquals(1, getLength(notEmptyDoubleArray));

    final float[] emptyFloatArray = new float[]{};
    final float[] notEmptyFloatArray = new float[]{1.0F};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyFloatArray));
    assertEquals(1, getLength(notEmptyFloatArray));

    final boolean[] emptyBooleanArray = new boolean[]{};
    final boolean[] notEmptyBooleanArray = new boolean[]{true};
    assertEquals(0, getLength(null));
    assertEquals(0, getLength(emptyBooleanArray));
    assertEquals(1, getLength(notEmptyBooleanArray));

    try {
      getLength("notAnArray");
      fail("IllegalArgumentException should have been thrown");
    } catch (final IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void testAddObjectArrayBoolean() {
    boolean[] newArray;
    newArray = add(null, false);
    assertArrayEquals(new boolean[]{false}, newArray);
    assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
    newArray = add(null, true);
    assertArrayEquals(new boolean[]{true}, newArray);
    assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
    final boolean[] array1 = {true, false, true};
    newArray = add(array1, false);
    assertArrayEquals(new boolean[]{true, false, true, false},
        newArray);
    assertEquals(Boolean.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayByte() {
    byte[] newArray;
    newArray = add((byte[]) null, (byte) 0);
    assertArrayEquals(new byte[]{0}, newArray);
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    newArray = add((byte[]) null, (byte) 1);
    assertArrayEquals(new byte[]{1}, newArray);
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    final byte[] array1 = new byte[]{1, 2, 3};
    newArray = add(array1, (byte) 0);
    assertArrayEquals(new byte[]{1, 2, 3, 0}, newArray);
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, (byte) 4);
    assertArrayEquals(new byte[]{1, 2, 3, 4}, newArray);
    assertEquals(Byte.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayChar() {
    char[] newArray;
    newArray = add((char[]) null, (char) 0);
    assertArrayEquals(new char[]{0}, newArray);
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    newArray = add((char[]) null, (char) 1);
    assertArrayEquals(new char[]{1}, newArray);
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    final char[] array1 = new char[]{1, 2, 3};
    newArray = add(array1, (char) 0);
    assertArrayEquals(new char[]{1, 2, 3, 0}, newArray);
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, (char) 4);
    assertArrayEquals(new char[]{1, 2, 3, 4}, newArray);
    assertEquals(Character.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayDouble() {
    double[] newArray;
    newArray = add((double[]) null, 0);
    assertArrayEquals(new double[]{0}, newArray);
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    newArray = add((double[]) null, 1);
    assertArrayEquals(new double[]{1}, newArray);
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    final double[] array1 = new double[]{1, 2, 3};
    newArray = add(array1, 0);
    assertArrayEquals(new double[]{1, 2, 3, 0}, newArray);
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, 4);
    assertArrayEquals(new double[]{1, 2, 3, 4}, newArray);
    assertEquals(Double.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayFloat() {
    float[] newArray;
    newArray = add((float[]) null, 0);
    assertArrayEquals(new float[]{0}, newArray);
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    newArray = add((float[]) null, 1);
    assertArrayEquals(new float[]{1}, newArray);
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    final float[] array1 = new float[]{1, 2, 3};
    newArray = add(array1, 0);
    assertArrayEquals(new float[]{1, 2, 3, 0}, newArray);
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, 4);
    assertArrayEquals(new float[]{1, 2, 3, 4}, newArray);
    assertEquals(Float.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayInt() {
    int[] newArray;
    newArray = add((int[]) null, 0);
    assertArrayEquals(new int[]{0}, newArray);
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
    newArray = add((int[]) null, 1);
    assertArrayEquals(new int[]{1}, newArray);
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
    final int[] array1 = new int[]{1, 2, 3};
    newArray = add(array1, 0);
    assertArrayEquals(new int[]{1, 2, 3, 0}, newArray);
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, 4);
    assertArrayEquals(new int[]{1, 2, 3, 4}, newArray);
    assertEquals(Integer.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayLong() {
    long[] newArray;
    newArray = add((long[]) null, 0);
    assertArrayEquals(new long[]{0}, newArray);
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    newArray = add((long[]) null, 1);
    assertArrayEquals(new long[]{1}, newArray);
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    final long[] array1 = new long[]{1, 2, 3};
    newArray = add(array1, 0);
    assertArrayEquals(new long[]{1, 2, 3, 0}, newArray);
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, 4);
    assertArrayEquals(new long[]{1, 2, 3, 4}, newArray);
    assertEquals(Long.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayShort() {
    short[] newArray;
    newArray = add((short[]) null, (short) 0);
    assertArrayEquals(new short[]{0}, newArray);
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    newArray = add((short[]) null, (short) 1);
    assertArrayEquals(new short[]{1}, newArray);
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    final short[] array1 = new short[]{1, 2, 3};
    newArray = add(array1, (short) 0);
    assertArrayEquals(new short[]{1, 2, 3, 0}, newArray);
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
    newArray = add(array1, (short) 4);
    assertArrayEquals(new short[]{1, 2, 3, 4}, newArray);
    assertEquals(Short.TYPE, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayObject() {
    Object[] newArray;
    newArray = add(null, null);
    assertArrayEquals((new Object[]{null}), newArray);
    assertEquals(Object.class, newArray.getClass().getComponentType());

    newArray = add((Object[]) null, "a");
    assertArrayEquals((new String[]{"a"}), newArray);

    // FIXME: see fixme at Arrays.add(T[], T)
    //assertArrayEquals((new Object[] { "a" }), newArray));

    assertEquals(String.class, newArray.getClass().getComponentType());

    final String[] stringArray1 = {"a", "b", "c"};
    newArray = add(stringArray1, null);
    assertArrayEquals((new String[]{"a", "b", "c", null}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());

    newArray = add(stringArray1, "d");
    assertArrayEquals((new String[]{"a", "b", "c", "d"}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());

    Number[] numberArray1 = new Number[]{1, 2.0D};
    newArray = add(numberArray1, 3.0F);
    assertArrayEquals((new Number[]{1, 2.0D, 3.0F}), newArray);
    assertEquals(Number.class, newArray.getClass().getComponentType());

    numberArray1 = null;
    newArray = add(numberArray1, 3.0F);
    assertArrayEquals((new Float[]{3.0F}), newArray);
    assertEquals(Float.class, newArray.getClass().getComponentType());

    numberArray1 = null;
    newArray = add(numberArray1, null);
    assertArrayEquals((new Object[]{null}), newArray);
    assertEquals(Object.class, newArray.getClass().getComponentType());
  }

  @Test
  public void testAddObjectArrayToObjectArray() {
    assertNull(addAll(null, (Object[]) null));
    Object[] newArray;
    final String[] stringArray1 = {"a", "b", "c"};
    final String[] stringArray2 = {"1", "2", "3"};
    newArray = addAll(stringArray1, null);
    assertNotSame(stringArray1, newArray);
    assertArrayEquals(stringArray1, newArray);
    assertArrayEquals((new String[]{"a", "b", "c"}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = addAll(null, stringArray2);
    assertNotSame(stringArray2, newArray);
    assertArrayEquals(stringArray2, newArray);
    assertArrayEquals((new String[]{"1", "2", "3"}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = addAll(stringArray1, stringArray2);
    assertArrayEquals((new String[]{"a", "b", "c", "1", "2", "3"}),
        newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = addAll(EMPTY_STRING_ARRAY, null);
    assertArrayEquals(EMPTY_STRING_ARRAY, newArray);
    assertArrayEquals((new String[]{}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = addAll(null, EMPTY_STRING_ARRAY);
    assertArrayEquals(EMPTY_STRING_ARRAY, newArray);
    assertArrayEquals((new String[]{}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = addAll(EMPTY_STRING_ARRAY,
        EMPTY_STRING_ARRAY);
    assertArrayEquals(EMPTY_STRING_ARRAY, newArray);
    assertArrayEquals((new String[]{}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    final String[] stringArrayNull = new String[]{null};
    newArray = addAll(stringArrayNull, stringArrayNull);
    assertArrayEquals((new String[]{null, null}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());

    // boolean
    assertArrayEquals(new boolean[]{true, false, false, true},
        addAll(new boolean[]{true, false}, new boolean[]{false, true}));

    assertArrayEquals(new boolean[]{false, true},
        addAll(null, new boolean[]{false, true}));

    assertArrayEquals(new boolean[]{true, false},
        addAll(new boolean[]{true, false}, null));

    // char
    assertArrayEquals(new char[]{'a', 'b', 'c', 'd'},
        addAll(new char[]{'a', 'b'}, new char[]{'c', 'd'}));

    assertArrayEquals(new char[]{'c', 'd'},
        addAll(null, new char[]{'c', 'd'}));

    assertArrayEquals(new char[]{'a', 'b'},
        addAll(new char[]{'a', 'b'}, null));

    // byte
    assertArrayEquals(new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3},
        addAll(new byte[]{(byte) 0, (byte) 1}, new byte[]{(byte) 2, (byte) 3}));

    assertArrayEquals(new byte[]{(byte) 2, (byte) 3},
        addAll(null, new byte[]{(byte) 2, (byte) 3}));

    assertArrayEquals(new byte[]{(byte) 0, (byte) 1},
        addAll(new byte[]{(byte) 0, (byte) 1}, null));

    // short
    assertArrayEquals(new short[]{(short) 10, (short) 20, (short) 30, (short) 40},
        addAll(new short[]{(short) 10, (short) 20}, new short[]{(short) 30, (short) 40}));

    assertArrayEquals(new short[]{(short) 30, (short) 40},
        addAll(null, new short[]{(short) 30, (short) 40}));

    assertArrayEquals(new short[]{(short) 10, (short) 20},
        addAll(new short[]{(short) 10, (short) 20}, null));

    // int
    assertArrayEquals(new int[]{1, 1000, -1000, -1},
        addAll(new int[]{1, 1000}, new int[]{-1000, -1}));

    assertArrayEquals(new int[]{-1000, -1},
        addAll(null, new int[]{-1000, -1}));

    assertArrayEquals(new int[]{1, 1000},
        addAll(new int[]{1, 1000}, null));

    // long
    assertArrayEquals(new long[]{1L, -1L, 1000L, -1000L},
        addAll(new long[]{1L, -1L}, new long[]{1000L, -1000L}));

    assertArrayEquals(new long[]{1000L, -1000L},
        addAll(null, new long[]{1000L, -1000L}));

    assertArrayEquals(new long[]{1L, -1L},
        addAll(new long[]{1L, -1L}, null));

    // float
    assertArrayEquals(new float[]{10.5f, 10.1f, 1.6f, 0.01f},
        addAll(new float[]{10.5f, 10.1f}, new float[]{1.6f, 0.01f}));

    assertArrayEquals(new float[]{1.6f, 0.01f},
        addAll(null, new float[]{1.6f, 0.01f}));

    assertArrayEquals(new float[]{10.5f, 10.1f},
        addAll(new float[]{10.5f, 10.1f}, null));

    // double
    assertArrayEquals(new double[]{Math.PI, -Math.PI, 0, 9.99},
        addAll(new double[]{Math.PI, -Math.PI}, new double[]{0, 9.99}));

    assertArrayEquals(new double[]{0, 9.99},
        addAll(null, new double[]{0, 9.99}));

    assertArrayEquals(new double[]{Math.PI, -Math.PI},
        addAll(new double[]{Math.PI, -Math.PI}, null));

  }

  @Test
  public void testAddObjectAtIndex() {
    Object[] newArray;
    newArray = add(null, 0, null);
    assertArrayEquals((new Object[]{null}), newArray);

    // FIXME: see fixme at Arrays.add(T[], int, T)
    // assertEquals(Object.class, newArray.getClass().getComponentType());

    newArray = add((Object[]) null, 0, "a");
    assertArrayEquals((new String[]{"a"}), newArray);

    // FIXME: see fixme at Arrays.add(T[], int, T)
    // assertArrayEquals((new Object[] { "a" }), newArray));

    assertEquals(String.class, newArray.getClass().getComponentType());
    final String[] stringArray1 = {"a", "b", "c"};
    newArray = add(stringArray1, 0, null);
    assertArrayEquals((new String[]{null, "a", "b", "c"}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = add(stringArray1, 1, null);
    assertArrayEquals((new String[]{"a", null, "b", "c"}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = add(stringArray1, 3, null);
    assertArrayEquals((new String[]{"a", "b", "c", null}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    newArray = add(stringArray1, 3, "d");
    assertArrayEquals((new String[]{"a", "b", "c", "d"}), newArray);
    assertEquals(String.class, newArray.getClass().getComponentType());
    assertEquals(String.class, newArray.getClass().getComponentType());

    final Object[] o = {"1", "2", "4"};
    final Object[] result = add(o, 2, "3");
    final Object[] result2 = add(o, 3, "5");

    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals("1", result[0]);
    assertEquals("2", result[1]);
    assertEquals("3", result[2]);
    assertEquals("4", result[3]);
    assertNotNull(result2);
    assertEquals(4, result2.length);
    assertEquals("1", result2[0]);
    assertEquals("2", result2[1]);
    assertEquals("4", result2[2]);
    assertEquals("5", result2[3]);

    // boolean tests
    boolean[] booleanArray = add(null, 0, true);
    assertArrayEquals(new boolean[]{true}, booleanArray);
    try {
      booleanArray = add(null, -1, true);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    booleanArray = add(new boolean[]{true}, 0, false);
    assertArrayEquals(new boolean[]{false, true}, booleanArray);
    booleanArray = add(new boolean[]{false}, 1, true);
    assertArrayEquals(new boolean[]{false, true}, booleanArray);
    booleanArray = add(new boolean[]{true, false}, 1, true);
    assertArrayEquals(new boolean[]{true, true, false}, booleanArray);
    try {
      booleanArray = add(new boolean[]{true, false}, 4, true);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      booleanArray = add(new boolean[]{true, false}, -1, true);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // char tests
    char[] charArray = add((char[]) null, 0, 'a');
    assertArrayEquals(new char[]{'a'}, charArray);
    try {
      charArray = add((char[]) null, -1, 'a');
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    charArray = add(new char[]{'a'}, 0, 'b');
    assertArrayEquals(new char[]{'b', 'a'}, charArray);
    charArray = add(new char[]{'a', 'b'}, 0, 'c');
    assertArrayEquals(new char[]{'c', 'a', 'b'}, charArray);
    charArray = add(new char[]{'a', 'b'}, 1, 'k');
    assertArrayEquals(new char[]{'a', 'k', 'b'}, charArray);
    charArray = add(new char[]{'a', 'b', 'c'}, 1, 't');
    assertArrayEquals(new char[]{'a', 't', 'b', 'c'}, charArray);
    try {
      charArray = add(new char[]{'a', 'b'}, 4, 'c');
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      charArray = add(new char[]{'a', 'b'}, -1, 'c');
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // short tests
    short[] shortArray = add(new short[]{1}, 0, (short) 2);
    assertArrayEquals(new short[]{2, 1}, shortArray);
    try {
      shortArray = add((short[]) null, -1, (short) 2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    shortArray = add(new short[]{2, 6}, 2, (short) 10);
    assertArrayEquals(new short[]{2, 6, 10}, shortArray);
    shortArray = add(new short[]{2, 6}, 0, (short) -4);
    assertArrayEquals(new short[]{-4, 2, 6}, shortArray);
    shortArray = add(new short[]{2, 6, 3}, 2, (short) 1);
    assertArrayEquals(new short[]{2, 6, 1, 3}, shortArray);
    try {
      shortArray = add(new short[]{2, 6}, 4, (short) 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      shortArray = add(new short[]{2, 6}, -1, (short) 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // byte tests
    byte[] byteArray = add(new byte[]{1}, 0, (byte) 2);
    assertArrayEquals(new byte[]{2, 1}, byteArray);
    try {
      byteArray = add((byte[]) null, -1, (byte) 2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    byteArray = add(new byte[]{2, 6}, 2, (byte) 3);
    assertArrayEquals(new byte[]{2, 6, 3}, byteArray);
    byteArray = add(new byte[]{2, 6}, 0, (byte) 1);
    assertArrayEquals(new byte[]{1, 2, 6}, byteArray);
    byteArray = add(new byte[]{2, 6, 3}, 2, (byte) 1);
    assertArrayEquals(new byte[]{2, 6, 1, 3}, byteArray);
    try {
      byteArray = add(new byte[]{2, 6}, 4, (byte) 3);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      byteArray = add(new byte[]{2, 6}, -1, (byte) 3);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // int tests
    int[] intArray = add(new int[]{1}, 0, 2);
    assertArrayEquals(new int[]{2, 1}, intArray);
    try {
      intArray = add((int[]) null, -1, 2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    intArray = add(new int[]{2, 6}, 2, 10);
    assertArrayEquals(new int[]{2, 6, 10}, intArray);
    intArray = add(new int[]{2, 6}, 0, -4);
    assertArrayEquals(new int[]{-4, 2, 6}, intArray);
    intArray = add(new int[]{2, 6, 3}, 2, 1);
    assertArrayEquals(new int[]{2, 6, 1, 3}, intArray);
    try {
      intArray = add(new int[]{2, 6}, 4, 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      intArray = add(new int[]{2, 6}, -1, 10);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // long tests
    long[] longArray = add(new long[]{1L}, 0, 2L);
    assertArrayEquals(new long[]{2L, 1L}, longArray);
    try {
      longArray = add((long[]) null, -1, 2L);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    longArray = add(new long[]{2L, 6L}, 2, 10L);
    assertArrayEquals(new long[]{2L, 6L, 10L}, longArray);
    longArray = add(new long[]{2L, 6L}, 0, -4L);
    assertArrayEquals(new long[]{-4L, 2L, 6L}, longArray);
    longArray = add(new long[]{2L, 6L, 3L}, 2, 1L);
    assertArrayEquals(new long[]{2L, 6L, 1L, 3L}, longArray);
    try {
      longArray = add(new long[]{2L, 6L}, 4, 10L);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      longArray = add(new long[]{2L, 6L}, -1, 10L);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // float tests
    float[] floatArray = add(new float[]{1.1f}, 0, 2.2f);
    assertArrayEquals(new float[]{2.2f, 1.1f}, floatArray);
    try {
      floatArray = add((float[]) null, -1, 2.2f);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    floatArray = add(new float[]{2.3f, 6.4f}, 2, 10.5f);
    assertArrayEquals(new float[]{2.3f, 6.4f, 10.5f}, floatArray);
    floatArray = add(new float[]{2.6f, 6.7f}, 0, -4.8f);
    assertArrayEquals(new float[]{-4.8f, 2.6f, 6.7f}, floatArray);
    floatArray = add(new float[]{2.9f, 6.0f, 0.3f}, 2, 1.0f);
    assertArrayEquals(new float[]{2.9f, 6.0f, 1.0f,
        0.3f}, floatArray);
    try {
      floatArray = add(new float[]{2.3f, 6.4f}, 4, 10.5f);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      floatArray = add(new float[]{2.3f, 6.4f}, -1, 10.5f);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }

    // double tests
    double[] doubleArray = add(new double[]{1.1}, 0, 2.2);
    assertArrayEquals(new double[]{2.2, 1.1}, doubleArray);
    try {
      doubleArray = add(null, -1, 2.2);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 0", e.getMessage());
    }
    doubleArray = add(new double[]{2.3, 6.4}, 2, 10.5);
    assertArrayEquals(new double[]{2.3, 6.4, 10.5}, doubleArray);
    doubleArray = add(new double[]{2.6, 6.7}, 0, -4.8);
    assertArrayEquals(new double[]{-4.8, 2.6, 6.7}, doubleArray);
    doubleArray = add(new double[]{2.9, 6.0, 0.3}, 2, 1.0);
    assertArrayEquals(new double[]{2.9, 6.0, 1.0, 0.3}, doubleArray);
    try {
      doubleArray = add(new double[]{2.3, 6.4}, 4, 10.5);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: 4, Length: 2", e.getMessage());
    }
    try {
      doubleArray = add(new double[]{2.3, 6.4}, -1, 10.5);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals("Index: -1, Length: 2", e.getMessage());
    }
  }

  @Test
  public void testRemoveObjectArray() {
    Object[] array;
    array = remove(new Object[]{"a"}, 0);
    assertArrayEquals(EMPTY_OBJECT_ARRAY, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    array = remove(new Object[]{"a", "b"}, 0);
    assertArrayEquals(new Object[]{"b"}, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    array = remove(new Object[]{"a", "b"}, 1);
    assertArrayEquals(new Object[]{"a"}, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    array = remove(new Object[]{"a", "b", "c"}, 1);
    assertArrayEquals(new Object[]{"a", "c"}, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    try {
      remove(new Object[]{"a", "b"}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new Object[]{"a", "b"}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((Object[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveBooleanArray() {
    boolean[] array;
    array = remove(new boolean[]{true}, 0);
    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = remove(new boolean[]{true, false}, 0);
    assertArrayEquals(new boolean[]{false}, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = remove(new boolean[]{true, false}, 1);
    assertArrayEquals(new boolean[]{true}, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = remove(new boolean[]{true, false, true}, 1);
    assertArrayEquals(new boolean[]{true, true}, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    try {
      remove(new boolean[]{true, false}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new boolean[]{true, false}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((boolean[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveByteArray() {
    byte[] array;
    array = remove(new byte[]{1}, 0);
    assertArrayEquals(EMPTY_BYTE_ARRAY, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = remove(new byte[]{1, 2}, 0);
    assertArrayEquals(new byte[]{2}, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = remove(new byte[]{1, 2}, 1);
    assertArrayEquals(new byte[]{1}, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = remove(new byte[]{1, 2, 1}, 1);
    assertArrayEquals(new byte[]{1, 1}, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    try {
      remove(new byte[]{1, 2}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new byte[]{1, 2}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((byte[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveCharArray() {
    char[] array;
    array = remove(new char[]{'a'}, 0);
    assertArrayEquals(EMPTY_CHAR_ARRAY, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = remove(new char[]{'a', 'b'}, 0);
    assertArrayEquals(new char[]{'b'}, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = remove(new char[]{'a', 'b'}, 1);
    assertArrayEquals(new char[]{'a'}, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = remove(new char[]{'a', 'b', 'c'}, 1);
    assertArrayEquals(new char[]{'a', 'c'}, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    try {
      remove(new char[]{'a', 'b'}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new char[]{'a', 'b'}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((char[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveDoubleArray() {
    double[] array;
    array = remove(new double[]{1}, 0);
    assertArrayEquals(EMPTY_DOUBLE_ARRAY, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = remove(new double[]{1, 2}, 0);
    assertArrayEquals(new double[]{2}, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = remove(new double[]{1, 2}, 1);
    assertArrayEquals(new double[]{1}, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = remove(new double[]{1, 2, 1}, 1);
    assertArrayEquals(new double[]{1, 1}, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    try {
      remove(new double[]{1, 2}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new double[]{1, 2}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((double[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveFloatArray() {
    float[] array;
    array = remove(new float[]{1}, 0);
    assertArrayEquals(EMPTY_FLOAT_ARRAY, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = remove(new float[]{1, 2}, 0);
    assertArrayEquals(new float[]{2}, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = remove(new float[]{1, 2}, 1);
    assertArrayEquals(new float[]{1}, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = remove(new float[]{1, 2, 1}, 1);
    assertArrayEquals(new float[]{1, 1}, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    try {
      remove(new float[]{1, 2}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new float[]{1, 2}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((float[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveIntArray() {
    int[] array;
    array = remove(new int[]{1}, 0);
    assertArrayEquals(EMPTY_INT_ARRAY, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = remove(new int[]{1, 2}, 0);
    assertArrayEquals(new int[]{2}, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = remove(new int[]{1, 2}, 1);
    assertArrayEquals(new int[]{1}, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = remove(new int[]{1, 2, 1}, 1);
    assertArrayEquals(new int[]{1, 1}, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    try {
      remove(new int[]{1, 2}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new int[]{1, 2}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((int[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveLongArray() {
    long[] array;
    array = remove(new long[]{1}, 0);
    assertArrayEquals(EMPTY_LONG_ARRAY, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = remove(new long[]{1, 2}, 0);
    assertArrayEquals(new long[]{2}, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = remove(new long[]{1, 2}, 1);
    assertArrayEquals(new long[]{1}, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = remove(new long[]{1, 2, 1}, 1);
    assertArrayEquals(new long[]{1, 1}, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    try {
      remove(new long[]{1, 2}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new long[]{1, 2}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((long[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveShortArray() {
    short[] array;
    array = remove(new short[]{1}, 0);
    assertArrayEquals(EMPTY_SHORT_ARRAY, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = remove(new short[]{1, 2}, 0);
    assertArrayEquals(new short[]{2}, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = remove(new short[]{1, 2}, 1);
    assertArrayEquals(new short[]{1}, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = remove(new short[]{1, 2, 1}, 1);
    assertArrayEquals(new short[]{1, 1}, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    try {
      remove(new short[]{1, 2}, -1);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove(new short[]{1, 2}, 2);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
    try {
      remove((short[]) null, 0);
      fail("IndexOutOfBoundsException expected");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  @Test
  public void testRemoveElementObjectArray() {
    Object[] array;
    array = removeElement((Object[]) null, "a");
    assertNull(array);
    array = removeElement(EMPTY_OBJECT_ARRAY, "a");
    assertArrayEquals(EMPTY_OBJECT_ARRAY, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    array = removeElement(new Object[]{"a"}, "a");
    assertArrayEquals(EMPTY_OBJECT_ARRAY, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    array = removeElement(new Object[]{"a", "b"}, "a");
    assertArrayEquals(new Object[]{"b"}, array);
    assertEquals(Object.class, array.getClass().getComponentType());
    array = removeElement(new Object[]{"a", "b", "a"}, "a");
    assertArrayEquals(new Object[]{"b", "a"}, array);
    assertEquals(Object.class, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementBooleanArray() {
    boolean[] array;
    array = removeElement(null, true);
    assertNull(array);
    array = removeElement(EMPTY_BOOLEAN_ARRAY, true);
    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = removeElement(new boolean[]{true}, true);
    assertArrayEquals(EMPTY_BOOLEAN_ARRAY, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = removeElement(new boolean[]{true, false}, true);
    assertArrayEquals(new boolean[]{false}, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
    array = removeElement(new boolean[]{true, false, true}, true);
    assertArrayEquals(new boolean[]{false, true}, array);
    assertEquals(Boolean.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementByteArray() {
    byte[] array;
    array = removeElement((byte[]) null, (byte) 1);
    assertNull(array);
    array = removeElement(EMPTY_BYTE_ARRAY, (byte) 1);
    assertArrayEquals(EMPTY_BYTE_ARRAY, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = removeElement(new byte[]{1}, (byte) 1);
    assertArrayEquals(EMPTY_BYTE_ARRAY, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = removeElement(new byte[]{1, 2}, (byte) 1);
    assertArrayEquals(new byte[]{2}, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
    array = removeElement(new byte[]{1, 2, 1}, (byte) 1);
    assertArrayEquals(new byte[]{2, 1}, array);
    assertEquals(Byte.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementCharArray() {
    char[] array;
    array = removeElement((char[]) null, 'a');
    assertNull(array);
    array = removeElement(EMPTY_CHAR_ARRAY, 'a');
    assertArrayEquals(EMPTY_CHAR_ARRAY, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = removeElement(new char[]{'a'}, 'a');
    assertArrayEquals(EMPTY_CHAR_ARRAY, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = removeElement(new char[]{'a', 'b'}, 'a');
    assertArrayEquals(new char[]{'b'}, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
    array = removeElement(new char[]{'a', 'b', 'a'}, 'a');
    assertArrayEquals(new char[]{'b', 'a'}, array);
    assertEquals(Character.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementDoubleArray() {
    double[] array;
    array = removeElement(null, (double) 1);
    assertNull(array);
    array = removeElement(EMPTY_DOUBLE_ARRAY, 1);
    assertArrayEquals(EMPTY_DOUBLE_ARRAY, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = removeElement(new double[]{1}, 1);
    assertArrayEquals(EMPTY_DOUBLE_ARRAY, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = removeElement(new double[]{1, 2}, 1);
    assertArrayEquals(new double[]{2}, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
    array = removeElement(new double[]{1, 2, 1}, 1);
    assertArrayEquals(new double[]{2, 1}, array);
    assertEquals(Double.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementFloatArray() {
    float[] array;
    array = removeElement((float[]) null, (float) 1);
    assertNull(array);
    array = removeElement(EMPTY_FLOAT_ARRAY, (float) 1);
    assertArrayEquals(EMPTY_FLOAT_ARRAY, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = removeElement(new float[]{1}, (float) 1);
    assertArrayEquals(EMPTY_FLOAT_ARRAY, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = removeElement(new float[]{1, 2}, (float) 1);
    assertArrayEquals(new float[]{2}, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
    array = removeElement(new float[]{1, 2, 1}, (float) 1);
    assertArrayEquals(new float[]{2, 1}, array);
    assertEquals(Float.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementIntArray() {
    int[] array;
    array = removeElement((int[]) null, 1);
    assertNull(array);
    array = removeElement(EMPTY_INT_ARRAY, 1);
    assertArrayEquals(EMPTY_INT_ARRAY, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = removeElement(new int[]{1}, 1);
    assertArrayEquals(EMPTY_INT_ARRAY, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = removeElement(new int[]{1, 2}, 1);
    assertArrayEquals(new int[]{2}, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
    array = removeElement(new int[]{1, 2, 1}, 1);
    assertArrayEquals(new int[]{2, 1}, array);
    assertEquals(Integer.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementLongArray() {
    long[] array;
    array = removeElement((long[]) null, 1);
    assertNull(array);
    array = removeElement(EMPTY_LONG_ARRAY, 1);
    assertArrayEquals(EMPTY_LONG_ARRAY, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = removeElement(new long[]{1}, 1);
    assertArrayEquals(EMPTY_LONG_ARRAY, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = removeElement(new long[]{1, 2}, 1);
    assertArrayEquals(new long[]{2}, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
    array = removeElement(new long[]{1, 2, 1}, 1);
    assertArrayEquals(new long[]{2, 1}, array);
    assertEquals(Long.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testRemoveElementShortArray() {
    short[] array;
    array = removeElement((short[]) null, (short) 1);
    assertNull(array);
    array = removeElement(EMPTY_SHORT_ARRAY, (short) 1);
    assertArrayEquals(EMPTY_SHORT_ARRAY, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = removeElement(new short[]{1}, (short) 1);
    assertArrayEquals(EMPTY_SHORT_ARRAY, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = removeElement(new short[]{1, 2}, (short) 1);
    assertArrayEquals(new short[]{2}, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
    array = removeElement(new short[]{1, 2, 1}, (short) 1);
    assertArrayEquals(new short[]{2, 1}, array);
    assertEquals(Short.TYPE, array.getClass().getComponentType());
  }

  @Test
  public void testIsArray() {
    assertFalse(isArray(null));

    final String[] a1 = {};
    assertTrue(isArray(a1));

    final String[] a2 = {"a", "b", "c"};
    assertTrue(isArray(a2));

    final int[] a3 = {1, 2, 3};
    assertTrue(isArray(a3));
  }
  //  resume magic number check

  @Test
  public void testNullToEmpty_boolean() {
    boolean[] array = null;
    assertArrayEquals(new boolean[0], nullToEmpty(array));
    array = new boolean[0];
    assertArrayEquals(new boolean[0], nullToEmpty(array));
    array = new boolean[]{ true, false };
    assertArrayEquals(new boolean[]{ true, false }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_char() {
    char[] array = null;
    assertArrayEquals(new char[0], nullToEmpty(array));
    array = new char[0];
    assertArrayEquals(new char[0], nullToEmpty(array));
    array = new char[]{ 'x', 'y' };
    assertArrayEquals(new char[]{ 'x', 'y' }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_byte() {
    byte[] array = null;
    assertArrayEquals(new byte[0], nullToEmpty(array));
    array = new byte[0];
    assertArrayEquals(new byte[0], nullToEmpty(array));
    array = new byte[]{ 12, -13 };
    assertArrayEquals(new byte[]{ 12, -13 }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_short() {
    short[] array = null;
    assertArrayEquals(new short[0], nullToEmpty(array));
    array = new short[0];
    assertArrayEquals(new short[0], nullToEmpty(array));
    array = new short[]{ 12, -13 };
    assertArrayEquals(new short[]{ 12, -13 }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_int() {
    int[] array = null;
    assertArrayEquals(new int[0], nullToEmpty(array));
    array = new int[0];
    assertArrayEquals(new int[0], nullToEmpty(array));
    array = new int[]{ 12, -13 };
    assertArrayEquals(new int[]{ 12, -13 }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_long() {
    long[] array = null;
    assertArrayEquals(new long[0], nullToEmpty(array));
    array = new long[0];
    assertArrayEquals(new long[0], nullToEmpty(array));
    array = new long[]{ 12, -13 };
    assertArrayEquals(new long[]{ 12, -13 }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_float() {
    float[] array = null;
    assertArrayEquals(new float[0], nullToEmpty(array));
    array = new float[0];
    assertArrayEquals(new float[0], nullToEmpty(array));
    array = new float[]{ 12.1f, -13.1203f };
    assertArrayEquals(new float[]{ 12.1f, -13.1203f }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_double() {
    double[] array = null;
    assertArrayEquals(new double[0], nullToEmpty(array));
    array = new double[0];
    assertArrayEquals(new double[0], nullToEmpty(array));
    array = new double[]{ 12.0001, -13.283874983 };
    assertArrayEquals(new double[]{ 12.0001, -13.283874983 }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_object() {
    String[] array = null;
    assertArrayEquals(new String[0], nullToEmpty(array));
    array = new String[0];
    assertArrayEquals(new String[0], nullToEmpty(array));
    array = new String[]{ "12.0001", "-13.283874983" };
    assertArrayEquals(new String[]{ "12.0001", "-13.283874983" }, nullToEmpty(array));
  }

  @Test
  public void testNullToEmpty_object_vararg() {
    assertArrayEquals(new String[0], testNullToEmptyVarargImpl((String[]) null));
    assertArrayEquals(new String[0], testNullToEmptyVarargImpl());
    assertArrayEquals(new String[]{ "abc", "def" }, testNullToEmptyVarargImpl("abc", "def"));
  }

  private String[] testNullToEmptyVarargImpl(final String ... array) {
    return nullToEmpty(array);
  }

  @Test
  public void testSumByteArray() {
    final byte[] array = {1, 2, 3, 4, 5};
    assertEquals(15, ArrayUtils.sum(array));

    final byte[] emptyArray = {};
    assertEquals(0, ArrayUtils.sum(emptyArray));

    try {
      ArrayUtils.sum((byte[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6, ArrayUtils.sum(array, 0, 3)); // 1+2+3=6
    assertEquals(9, ArrayUtils.sum(array, 1, 4)); // 2+3+4=9
    assertEquals(5, ArrayUtils.sum(array, 4, 5)); // 5
    assertEquals(0, ArrayUtils.sum(array, 5, 10)); // 超出边界
    assertEquals(0, ArrayUtils.sum(array, -1, 0)); // 负索引
    assertEquals(15, ArrayUtils.sum(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.sum((byte[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testSumShortArray() {
    final short[] array = {1, 2, 3, 4, 5};
    assertEquals(15, ArrayUtils.sum(array));

    final short[] emptyArray = {};
    assertEquals(0, ArrayUtils.sum(emptyArray));

    try {
      ArrayUtils.sum((short[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6, ArrayUtils.sum(array, 0, 3)); // 1+2+3=6
    assertEquals(9, ArrayUtils.sum(array, 1, 4)); // 2+3+4=9
    assertEquals(5, ArrayUtils.sum(array, 4, 5)); // 5
    assertEquals(0, ArrayUtils.sum(array, 5, 10)); // 超出边界
    assertEquals(0, ArrayUtils.sum(array, -1, 0)); // 负索引
    assertEquals(15, ArrayUtils.sum(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.sum((short[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testSumIntArray() {
    final int[] array = {1, 2, 3, 4, 5};
    assertEquals(15, ArrayUtils.sum(array));

    final int[] emptyArray = {};
    assertEquals(0, ArrayUtils.sum(emptyArray));

    try {
      ArrayUtils.sum((int[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6, ArrayUtils.sum(array, 0, 3)); // 1+2+3=6
    assertEquals(9, ArrayUtils.sum(array, 1, 4)); // 2+3+4=9
    assertEquals(5, ArrayUtils.sum(array, 4, 5)); // 5
    assertEquals(0, ArrayUtils.sum(array, 5, 10)); // 超出边界
    assertEquals(0, ArrayUtils.sum(array, -1, 0)); // 负索引
    assertEquals(15, ArrayUtils.sum(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.sum((int[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testSumLongArray() {
    final long[] array = {1L, 2L, 3L, 4L, 5L};
    assertEquals(15L, ArrayUtils.sum(array));

    final long[] emptyArray = {};
    assertEquals(0L, ArrayUtils.sum(emptyArray));

    try {
      ArrayUtils.sum((long[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6L, ArrayUtils.sum(array, 0, 3)); // 1+2+3=6
    assertEquals(9L, ArrayUtils.sum(array, 1, 4)); // 2+3+4=9
    assertEquals(5L, ArrayUtils.sum(array, 4, 5)); // 5
    assertEquals(0L, ArrayUtils.sum(array, 5, 10)); // 超出边界
    assertEquals(0L, ArrayUtils.sum(array, -1, 0)); // 负索引
    assertEquals(15L, ArrayUtils.sum(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.sum((long[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testSumFloatArray() {
    final float[] array = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
    assertEquals(15.0f, ArrayUtils.sum(array));

    final float[] emptyArray = {};
    assertEquals(0.0f, ArrayUtils.sum(emptyArray));

    try {
      ArrayUtils.sum((float[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0f, ArrayUtils.sum(array, 0, 3)); // 1+2+3=6
    assertEquals(9.0f, ArrayUtils.sum(array, 1, 4)); // 2+3+4=9
    assertEquals(5.0f, ArrayUtils.sum(array, 4, 5)); // 5
    assertEquals(0.0f, ArrayUtils.sum(array, 5, 10)); // 超出边界
    assertEquals(0.0f, ArrayUtils.sum(array, -1, 0)); // 负索引
    assertEquals(15.0f, ArrayUtils.sum(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.sum((float[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testSumDoubleArray() {
    final double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
    assertEquals(15.0, ArrayUtils.sum(array));

    final double[] emptyArray = {};
    assertEquals(0.0, ArrayUtils.sum(emptyArray));

    try {
      ArrayUtils.sum((double[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0, ArrayUtils.sum(array, 0, 3)); // 1+2+3=6
    assertEquals(9.0, ArrayUtils.sum(array, 1, 4)); // 2+3+4=9
    assertEquals(5.0, ArrayUtils.sum(array, 4, 5)); // 5
    assertEquals(0.0, ArrayUtils.sum(array, 5, 10)); // 超出边界
    assertEquals(0.0, ArrayUtils.sum(array, -1, 0)); // 负索引
    assertEquals(15.0, ArrayUtils.sum(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.sum((double[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testProductByteArray() {
    final byte[] array = {1, 2, 3, 4, 5};
    assertEquals(120.0, ArrayUtils.product(array));

    final byte[] emptyArray = {};
    assertEquals(1.0, ArrayUtils.product(emptyArray));

    try {
      ArrayUtils.product((byte[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0, ArrayUtils.product(array, 0, 3)); // 1*2*3=6
    assertEquals(24.0, ArrayUtils.product(array, 1, 4)); // 2*3*4=24
    assertEquals(5.0, ArrayUtils.product(array, 4, 5)); // 5
    assertEquals(1.0, ArrayUtils.product(array, 5, 10)); // 超出边界
    assertEquals(1.0, ArrayUtils.product(array, -1, 0)); // 负索引
    assertEquals(120.0, ArrayUtils.product(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.product((byte[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testProductShortArray() {
    final short[] array = {1, 2, 3, 4, 5};
    assertEquals(120.0, ArrayUtils.product(array));

    final short[] emptyArray = {};
    assertEquals(1.0, ArrayUtils.product(emptyArray));

    try {
      ArrayUtils.product((short[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0, ArrayUtils.product(array, 0, 3)); // 1*2*3=6
    assertEquals(24.0, ArrayUtils.product(array, 1, 4)); // 2*3*4=24
    assertEquals(5.0, ArrayUtils.product(array, 4, 5)); // 5
    assertEquals(1.0, ArrayUtils.product(array, 5, 10)); // 超出边界
    assertEquals(1.0, ArrayUtils.product(array, -1, 0)); // 负索引
    assertEquals(120.0, ArrayUtils.product(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.product((short[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testProductIntArray() {
    final int[] array = {1, 2, 3, 4, 5};
    assertEquals(120.0, ArrayUtils.product(array));

    final int[] emptyArray = {};
    assertEquals(1.0, ArrayUtils.product(emptyArray));

    try {
      ArrayUtils.product((int[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0, ArrayUtils.product(array, 0, 3)); // 1*2*3=6
    assertEquals(24.0, ArrayUtils.product(array, 1, 4)); // 2*3*4=24
    assertEquals(5.0, ArrayUtils.product(array, 4, 5)); // 5
    assertEquals(1.0, ArrayUtils.product(array, 5, 10)); // 超出边界
    assertEquals(1.0, ArrayUtils.product(array, -1, 0)); // 负索引
    assertEquals(120.0, ArrayUtils.product(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.product((int[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testProductLongArray() {
    final long[] array = {1L, 2L, 3L, 4L, 5L};
    assertEquals(120.0, ArrayUtils.product(array));

    final long[] emptyArray = {};
    assertEquals(1.0, ArrayUtils.product(emptyArray));

    try {
      ArrayUtils.product((long[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0, ArrayUtils.product(array, 0, 3)); // 1*2*3=6
    assertEquals(24.0, ArrayUtils.product(array, 1, 4)); // 2*3*4=24
    assertEquals(5.0, ArrayUtils.product(array, 4, 5)); // 5
    assertEquals(1.0, ArrayUtils.product(array, 5, 10)); // 超出边界
    assertEquals(1.0, ArrayUtils.product(array, -1, 0)); // 负索引
    assertEquals(120.0, ArrayUtils.product(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.product((long[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testProductFloatArray() {
    final float[] array = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
    assertEquals(120.0f, ArrayUtils.product(array));

    final float[] emptyArray = {};
    assertEquals(1.0f, ArrayUtils.product(emptyArray));

    try {
      ArrayUtils.product((float[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0f, ArrayUtils.product(array, 0, 3)); // 1*2*3=6
    assertEquals(24.0f, ArrayUtils.product(array, 1, 4)); // 2*3*4=24
    assertEquals(5.0f, ArrayUtils.product(array, 4, 5)); // 5
    assertEquals(1.0f, ArrayUtils.product(array, 5, 10)); // 超出边界
    assertEquals(1.0f, ArrayUtils.product(array, -1, 0)); // 负索引
    assertEquals(120.0f, ArrayUtils.product(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.product((float[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testProductDoubleArray() {
    final double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
    assertEquals(120.0, ArrayUtils.product(array));

    final double[] emptyArray = {};
    assertEquals(1.0, ArrayUtils.product(emptyArray));

    try {
      ArrayUtils.product((double[]) null);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }

    assertEquals(6.0, ArrayUtils.product(array, 0, 3)); // 1*2*3=6
    assertEquals(24.0, ArrayUtils.product(array, 1, 4)); // 2*3*4=24
    assertEquals(5.0, ArrayUtils.product(array, 4, 5)); // 5
    assertEquals(1.0, ArrayUtils.product(array, 5, 10)); // 超出边界
    assertEquals(1.0, ArrayUtils.product(array, -1, 0)); // 负索引
    assertEquals(120.0, ArrayUtils.product(array, -1, 10)); // 范围超出时正确处理

    try {
      ArrayUtils.product((double[]) null, 0, 3);
      fail("Expected NullPointerException");
    } catch (final NullPointerException e) {
      // expected exception
    }
  }

  @Test
  public void testNullIfEmptyObjectArray() {
    final Object[] array = {1, "a", 3.0};
    final Object[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final Object[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((Object[]) null));
  }

  @Test
  public void testNullIfEmptyBooleanArray() {
    final boolean[] array = {true, false, true};
    final boolean[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final boolean[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((boolean[]) null));
  }

  @Test
  public void testNullIfEmptyCharArray() {
    final char[] array = {'a', 'b', 'c'};
    final char[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final char[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((char[]) null));
  }

  @Test
  public void testNullIfEmptyByteArray() {
    final byte[] array = {1, 2, 3};
    final byte[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final byte[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((byte[]) null));
  }

  @Test
  public void testNullIfEmptyShortArray() {
    final short[] array = {1, 2, 3};
    final short[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final short[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((short[]) null));
  }

  @Test
  public void testNullIfEmptyIntArray() {
    final int[] array = {1, 2, 3};
    final int[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final int[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((int[]) null));
  }

  @Test
  public void testNullIfEmptyLongArray() {
    final long[] array = {1L, 2L, 3L};
    final long[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final long[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((long[]) null));
  }

  @Test
  public void testNullIfEmptyFloatArray() {
    final float[] array = {1.0f, 2.0f, 3.0f};
    final float[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final float[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((float[]) null));
  }

  @Test
  public void testNullIfEmptyDoubleArray() {
    final double[] array = {1.0, 2.0, 3.0};
    final double[] result = ArrayUtils.nullIfEmpty(array);
    assertSame(array, result);

    final double[] emptyArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyArray));

    assertNull(ArrayUtils.nullIfEmpty((double[]) null));
  }

  @Test
  public void testLowerBoundObjectArray() {
    final Integer[] array = {1, 3, 5, 7, 9};

    // 查找包含在数组中的元素
    assertEquals(0, ArrayUtils.lowerBound(array, 0, 5, 1)); // 数组中第一个元素
    assertEquals(2, ArrayUtils.lowerBound(array, 0, 5, 5)); // 数组中间元素
    assertEquals(4, ArrayUtils.lowerBound(array, 0, 5, 9)); // 数组最后一个元素

    // 查找不在数组中的元素
    assertEquals(1, ArrayUtils.lowerBound(array, 0, 5, 2)); // 应返回大于2的第一个元素的位置
    assertEquals(3, ArrayUtils.lowerBound(array, 0, 5, 6)); // 应返回大于6的第一个元素的位置
    assertEquals(5, ArrayUtils.lowerBound(array, 0, 5, 10)); // 应返回数组长度

    // 测试部分范围
    assertEquals(3, ArrayUtils.lowerBound(array, 2, 5, 7)); // 在部分范围内查找存在的元素
    assertEquals(2, ArrayUtils.lowerBound(array, 0, 3, 5)); // 在部分范围内查找存在的元素
    assertEquals(3, ArrayUtils.lowerBound(array, 0, 3, 6)); // 在部分范围内查找不存在的元素，应返回范围上限

    // 测试异常情况
    try {
      ArrayUtils.lowerBound(array, -1, 5, 5);
      fail("Should throw IllegalArgumentException for negative begin index");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }

    try {
      ArrayUtils.lowerBound(array, 3, 2, 5);
      fail("Should throw IllegalArgumentException when begin > end");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }

    try {
      ArrayUtils.lowerBound(array, 0, 6, 5);
      fail("Should throw IllegalArgumentException when end > array.length");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }
  }

  @Test
  public void testLowerBoundIntArray() {
    final int[] array = {1, 3, 5, 7, 9};

    // 查找包含在数组中的元素
    assertEquals(0, ArrayUtils.lowerBound(array, 0, 5, 1)); // 数组中第一个元素
    assertEquals(2, ArrayUtils.lowerBound(array, 0, 5, 5)); // 数组中间元素
    assertEquals(4, ArrayUtils.lowerBound(array, 0, 5, 9)); // 数组最后一个元素

    // 查找不在数组中的元素
    assertEquals(1, ArrayUtils.lowerBound(array, 0, 5, 2)); // 应返回大于2的第一个元素的位置
    assertEquals(3, ArrayUtils.lowerBound(array, 0, 5, 6)); // 应返回大于6的第一个元素的位置
    assertEquals(5, ArrayUtils.lowerBound(array, 0, 5, 10)); // 应返回数组长度

    // 测试部分范围
    assertEquals(3, ArrayUtils.lowerBound(array, 2, 5, 7)); // 在部分范围内查找存在的元素
    assertEquals(2, ArrayUtils.lowerBound(array, 0, 3, 5)); // 在部分范围内查找存在的元素
    assertEquals(3, ArrayUtils.lowerBound(array, 0, 3, 6)); // 在部分范围内查找不存在的元素，应返回范围上限
  }

  @Test
  public void testUpperBoundObjectArray() {
    final Integer[] array = {1, 3, 5, 7, 9};

    // 查找包含在数组中的元素
    assertEquals(1, ArrayUtils.upperBound(array, 0, 5, 1)); // 数组中第一个元素的下一个位置
    assertEquals(3, ArrayUtils.upperBound(array, 0, 5, 5)); // 数组中间元素的下一个位置
    assertEquals(5, ArrayUtils.upperBound(array, 0, 5, 9)); // 数组最后一个元素的下一个位置

    // 查找不在数组中的元素
    assertEquals(1, ArrayUtils.upperBound(array, 0, 5, 2)); // 应返回大于等于2的第一个元素的位置
    assertEquals(3, ArrayUtils.upperBound(array, 0, 5, 6)); // 应返回大于等于6的第一个元素的位置
    assertEquals(5, ArrayUtils.upperBound(array, 0, 5, 10)); // 应返回数组长度

    // 测试部分范围
    assertEquals(4, ArrayUtils.upperBound(array, 2, 5, 7)); // 在部分范围内查找存在的元素
    assertEquals(3, ArrayUtils.upperBound(array, 0, 3, 5)); // 在部分范围内查找存在的元素
    assertEquals(3, ArrayUtils.upperBound(array, 0, 3, 6)); // 在部分范围内查找不存在的元素，应返回范围上限

    // 测试异常情况
    try {
      ArrayUtils.upperBound(array, -1, 5, 5);
      fail("Should throw IllegalArgumentException for negative begin index");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }

    try {
      ArrayUtils.upperBound(array, 3, 2, 5);
      fail("Should throw IllegalArgumentException when begin > end");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }

    try {
      ArrayUtils.upperBound(array, 0, 6, 5);
      fail("Should throw IllegalArgumentException when end > array.length");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }
  }

  @Test
  public void testUpperBoundIntArray() {
    final int[] array = {1, 3, 5, 7, 9};

    // 查找包含在数组中的元素
    assertEquals(1, ArrayUtils.upperBound(array, 0, 5, 1)); // 数组中第一个元素的下一个位置
    assertEquals(3, ArrayUtils.upperBound(array, 0, 5, 5)); // 数组中间元素的下一个位置
    assertEquals(5, ArrayUtils.upperBound(array, 0, 5, 9)); // 数组最后一个元素的下一个位置

    // 查找不在数组中的元素
    assertEquals(1, ArrayUtils.upperBound(array, 0, 5, 2)); // 应返回大于等于2的第一个元素的位置
    assertEquals(3, ArrayUtils.upperBound(array, 0, 5, 6)); // 应返回大于等于6的第一个元素的位置
    assertEquals(5, ArrayUtils.upperBound(array, 0, 5, 10)); // 应返回数组长度

    // 测试部分范围
    assertEquals(4, ArrayUtils.upperBound(array, 2, 5, 7)); // 在部分范围内查找存在的元素
    assertEquals(3, ArrayUtils.upperBound(array, 0, 3, 5)); // 在部分范围内查找存在的元素
    assertEquals(3, ArrayUtils.upperBound(array, 0, 3, 6)); // 在部分范围内查找不存在的元素，应返回范围上限
  }

  @Test
  public void testNullIfEmpty() {
    final byte[] byteArray = {1, 2, 3};
    assertSame(byteArray, ArrayUtils.nullIfEmpty(byteArray));

    final byte[] emptyByteArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyByteArray));

    assertNull(ArrayUtils.nullIfEmpty((byte[]) null));

    final short[] shortArray = {1, 2, 3};
    assertSame(shortArray, ArrayUtils.nullIfEmpty(shortArray));

    final short[] emptyShortArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyShortArray));

    assertNull(ArrayUtils.nullIfEmpty((short[]) null));

    final int[] intArray = {1, 2, 3};
    assertSame(intArray, ArrayUtils.nullIfEmpty(intArray));

    final int[] emptyIntArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyIntArray));

    assertNull(ArrayUtils.nullIfEmpty((int[]) null));

    final long[] longArray = {1L, 2L, 3L};
    assertSame(longArray, ArrayUtils.nullIfEmpty(longArray));

    final long[] emptyLongArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyLongArray));

    assertNull(ArrayUtils.nullIfEmpty((long[]) null));

    final float[] floatArray = {1.0f, 2.0f, 3.0f};
    assertSame(floatArray, ArrayUtils.nullIfEmpty(floatArray));

    final float[] emptyFloatArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyFloatArray));

    assertNull(ArrayUtils.nullIfEmpty((float[]) null));

    final double[] doubleArray = {1.0, 2.0, 3.0};
    assertSame(doubleArray, ArrayUtils.nullIfEmpty(doubleArray));

    final double[] emptyDoubleArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyDoubleArray));

    assertNull(ArrayUtils.nullIfEmpty((double[]) null));

    final char[] charArray = {'a', 'b', 'c'};
    assertSame(charArray, ArrayUtils.nullIfEmpty(charArray));

    final char[] emptyCharArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyCharArray));

    assertNull(ArrayUtils.nullIfEmpty((char[]) null));

    final boolean[] booleanArray = {true, false, true};
    assertSame(booleanArray, ArrayUtils.nullIfEmpty(booleanArray));

    final boolean[] emptyBooleanArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyBooleanArray));

    assertNull(ArrayUtils.nullIfEmpty((boolean[]) null));

    final String[] stringArray = {"a", "b", "c"};
    assertSame(stringArray, ArrayUtils.nullIfEmpty(stringArray));

    final String[] emptyStringArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyStringArray));

    assertNull(ArrayUtils.nullIfEmpty((String[]) null));

    final Object[] objectArray = {new Object(), new Object()};
    assertSame(objectArray, ArrayUtils.nullIfEmpty(objectArray));

    final Object[] emptyObjectArray = {};
    assertNull(ArrayUtils.nullIfEmpty(emptyObjectArray));

    assertNull(ArrayUtils.nullIfEmpty((Object[]) null));
  }

  @Test
  public void testToMap() {
    // 测试null数组
    assertNull(ArrayUtils.toMap(null));

    // 测试空数组
    final Map<Object, Object> emptyMap = ArrayUtils.toMap(new Object[0]);
    assertNotNull(emptyMap);
    assertEquals(0, emptyMap.size());

    // 测试二维数组转Map
    final String[][] entries = {{"key1", "value1"}, {"key2", "value2"}};
    Map<Object, Object> map = ArrayUtils.toMap(entries);
    assertEquals(2, map.size());
    assertEquals("value1", map.get("key1"));
    assertEquals("value2", map.get("key2"));

    // 测试Map.Entry数组转Map
    final Map.Entry<String, Integer>[] entryArray = new Map.Entry[2];
    entryArray[0] = Map.entry("one", 1);
    entryArray[1] = Map.entry("two", 2);

    map = ArrayUtils.toMap(entryArray);
    assertEquals(2, map.size());
    assertEquals(1, map.get("one"));
    assertEquals(2, map.get("two"));

    // 测试异常情况：元素数组长度小于2
    try {
      ArrayUtils.toMap(new String[][]{{"single"}});
      fail("Should throw IllegalArgumentException for array element with length < 2");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }

    // 测试异常情况：非数组元素
    try {
      ArrayUtils.toMap(new Object[]{new Object[]{"key", "value"}, "not an array"});
      fail("Should throw IllegalArgumentException for non-array or non-Map.Entry element");
    } catch (final IllegalArgumentException e) {
      // 期望的异常
    }
  }

  @Test
  public void testMax() {
    // 测试空数组
    assertNull(ArrayUtils.max((Integer[]) null));
    assertNull(ArrayUtils.max(new Integer[0]));

    // 测试单元素数组
    assertEquals(Integer.valueOf(5), ArrayUtils.max(new Integer[]{5}));

    // 测试多元素数组
    assertEquals(Integer.valueOf(9), ArrayUtils.max(new Integer[]{5, 2, 9, 3, 8}));
    assertEquals(Integer.valueOf(9), ArrayUtils.max(new Integer[]{9, 8, 7, 6, 5}));
    assertEquals(Integer.valueOf(9), ArrayUtils.max(new Integer[]{1, 2, 3, 9, 4}));

    // 测试包含null的数组（null为最小值）
    assertEquals(Integer.valueOf(5), ArrayUtils.max(new Integer[]{null, 5, null}));

    assertEquals(Integer.valueOf(7), ArrayUtils.max(new Integer[]{null, null, 5, 7, null}));

    // 测试全部为null的数组
    assertNull(ArrayUtils.max(new Integer[]{null, null, null}));

    // 测试其他类型
    assertEquals("xyz", ArrayUtils.max(new String[]{"abc", "def", "xyz"}));
    assertEquals(Double.valueOf(9.9), ArrayUtils.max(new Double[]{1.1, 5.5, 9.9}));
  }

  @Test
  public void testMaxWithRange() {
    // 测试空数组
    assertNull(ArrayUtils.max((Integer[]) null, 0, 0));
    assertNull(ArrayUtils.max(new Integer[0], 0, 0));

    final Integer[] array = {1, 5, 9, 3, 7};

    // 测试完整范围
    assertEquals(Integer.valueOf(9), ArrayUtils.max(array, 0, array.length));

    // 测试部分范围
    assertEquals(Integer.valueOf(5), ArrayUtils.max(array, 0, 2));  // [1, 5]
    assertEquals(Integer.valueOf(9), ArrayUtils.max(array, 1, 3));  // [5, 9]
    assertEquals(Integer.valueOf(7), ArrayUtils.max(array, 3, 5));  // [3, 7]

    // 测试范围边界
    assertEquals(Integer.valueOf(1), ArrayUtils.max(array, 0, 1));  // 只有第一个元素
    assertEquals(Integer.valueOf(7), ArrayUtils.max(array, 4, 5));  // 只有最后一个元素

    // 测试无效范围
    assertNull(ArrayUtils.max(array, 2, 2));  // 空范围
    assertNull(ArrayUtils.max(array, 5, 5));  // 空范围(超出数组)

    // 测试负数开始索引(应被视为0)
    assertEquals(Integer.valueOf(9), ArrayUtils.max(array, -1, 3));  // 实际为[0,3)

    // 测试结束索引超出数组长度(应被截断)
    assertEquals(Integer.valueOf(9), ArrayUtils.max(array, 0, 10));  // 实际为[0,5)

    // 测试开始索引大于结束索引(应返回null)
    assertNull(ArrayUtils.max(array, 3, 2));

    // 测试包含null的数组
    final Integer[] arrayWithNull = {null, 5, null, 9, null};
    assertEquals(Integer.valueOf(9), ArrayUtils.max(arrayWithNull, 0, 5));
    assertEquals(Integer.valueOf(5), ArrayUtils.max(arrayWithNull, 0, 2));

    // 测试范围内全部为null
    assertNull(ArrayUtils.max(arrayWithNull, 0, 1));
    assertNull(ArrayUtils.max(new Integer[]{null, null, null}, 0, 3));
  }

  @Test
  public void testMin() {
    // 测试空数组
    assertNull(ArrayUtils.min((Integer[]) null));
    assertNull(ArrayUtils.min(new Integer[0]));

    // 测试单元素数组
    assertEquals(Integer.valueOf(5), ArrayUtils.min(new Integer[]{5}));

    // 测试多元素数组
    assertEquals(Integer.valueOf(2), ArrayUtils.min(new Integer[]{5, 2, 9, 3, 8}));
    assertEquals(Integer.valueOf(5), ArrayUtils.min(new Integer[]{9, 8, 7, 6, 5}));
    assertEquals(Integer.valueOf(1), ArrayUtils.min(new Integer[]{1, 2, 3, 9, 4}));

    // 测试包含null的数组（ null 是最小值）
    assertNull(ArrayUtils.min(new Integer[]{2, 5, null}));

    assertNull(ArrayUtils.min(new Integer[]{null, null, 5, 2}));

    // 测试全部为null的数组
    assertNull(ArrayUtils.min(new Integer[]{null, null, null}));

    // 测试其他类型
    assertEquals("abc", ArrayUtils.min(new String[]{"abc", "def", "xyz"}));
    assertEquals(Double.valueOf(1.1), ArrayUtils.min(new Double[]{1.1, 5.5, 9.9}));
  }

  @Test
  public void testMinWithRange() {
    // 测试空数组
    assertNull(ArrayUtils.min((Integer[]) null, 0, 0));
    assertNull(ArrayUtils.min(new Integer[0], 0, 0));

    final Integer[] array = {5, 3, 1, 9, 7};

    // 测试完整范围
    assertEquals(Integer.valueOf(1), ArrayUtils.min(array, 0, array.length));

    // 测试部分范围
    assertEquals(Integer.valueOf(3), ArrayUtils.min(array, 0, 2));  // [5, 3]
    assertEquals(Integer.valueOf(1), ArrayUtils.min(array, 1, 3));  // [3, 1]
    assertEquals(Integer.valueOf(7), ArrayUtils.min(array, 3, 5));  // [9, 7]

    // 测试范围边界
    assertEquals(Integer.valueOf(5), ArrayUtils.min(array, 0, 1));  // 只有第一个元素
    assertEquals(Integer.valueOf(7), ArrayUtils.min(array, 4, 5));  // 只有最后一个元素

    // 测试无效范围
    assertNull(ArrayUtils.min(array, 2, 2));  // 空范围
    assertNull(ArrayUtils.min(array, 5, 5));  // 空范围(超出数组)

    // 测试负数开始索引(应被视为0)
    assertEquals(Integer.valueOf(3), ArrayUtils.min(array, -1, 2));  // 实际为[0,2)

    // 测试结束索引超出数组长度(应被截断)
    assertEquals(Integer.valueOf(1), ArrayUtils.min(array, 0, 10));  // 实际为[0,5)

    // 测试开始索引大于结束索引(应返回null)
    assertNull(ArrayUtils.min(array, 3, 2));

    // 测试包含null的数组
    final Integer[] arrayWithNull = {null, 5, 2, 3, null};
    assertNull(ArrayUtils.min(arrayWithNull, 0, 5));
    assertEquals(Integer.valueOf(2), ArrayUtils.min(arrayWithNull, 1, 4));

    // 测试范围内全部为null
    assertNull(ArrayUtils.min(arrayWithNull, 0, 1));
    assertNull(ArrayUtils.min(new Integer[]{null, null, null}, 0, 3));
  }

  @Test
  public void testReverseCloneObject() {
    // 测试null数组
    assertNull(ArrayUtils.reverseClone((String[]) null));

    // 测试空数组
    final String[] emptyArray = new String[0];
    String[] result = ArrayUtils.reverseClone(emptyArray);
    assertNotNull(result);
    assertEquals(0, result.length);

    // 测试单元素数组
    final String[] singleArray = {"single"};
    result = ArrayUtils.reverseClone(singleArray);
    assertNotNull(result);
    assertEquals(1, result.length);
    assertEquals("single", result[0]);

    // 测试多元素数组
    final String[] array = {"one", "two", "three", "four"};
    result = ArrayUtils.reverseClone(array);
    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals("four", result[0]);
    assertEquals("three", result[1]);
    assertEquals("two", result[2]);
    assertEquals("one", result[3]);

    // 验证原数组未改变
    assertEquals("one", array[0]);
    assertEquals("two", array[1]);
    assertEquals("three", array[2]);
    assertEquals("four", array[3]);

    // 验证返回值不是原数组
    assertNotSame(array, result);
  }

  @Test
  public void testReverseCloneLong() {
    // 测试null数组
    assertNull(ArrayUtils.reverseClone((long[]) null));

    // 测试空数组
    final long[] emptyArray = new long[0];
    long[] result = ArrayUtils.reverseClone(emptyArray);
    assertNotNull(result);
    assertEquals(0, result.length);

    // 测试单元素数组
    final long[] singleArray = {5L};
    result = ArrayUtils.reverseClone(singleArray);
    assertNotNull(result);
    assertEquals(1, result.length);
    assertEquals(5L, result[0]);

    // 测试多元素数组
    final long[] array = {1L, 2L, 3L, 4L};
    result = ArrayUtils.reverseClone(array);
    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals(4L, result[0]);
    assertEquals(3L, result[1]);
    assertEquals(2L, result[2]);
    assertEquals(1L, result[3]);

    // 验证原数组未改变
    assertEquals(1L, array[0]);
    assertEquals(2L, array[1]);
    assertEquals(3L, array[2]);
    assertEquals(4L, array[3]);

    // 验证返回值不是原数组
    assertNotSame(array, result);
  }

  @Test
  public void testReverseCloneInt() {
    // 测试null数组
    assertNull(ArrayUtils.reverseClone((int[]) null));

    // 测试空数组
    final int[] emptyArray = new int[0];
    int[] result = ArrayUtils.reverseClone(emptyArray);
    assertNotNull(result);
    assertEquals(0, result.length);

    // 测试单元素数组
    final int[] singleArray = {5};
    result = ArrayUtils.reverseClone(singleArray);
    assertNotNull(result);
    assertEquals(1, result.length);
    assertEquals(5, result[0]);

    // 测试多元素数组
    final int[] array = {1, 2, 3, 4};
    result = ArrayUtils.reverseClone(array);
    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals(4, result[0]);
    assertEquals(3, result[1]);
    assertEquals(2, result[2]);
    assertEquals(1, result[3]);

    // 验证原数组未改变
    assertEquals(1, array[0]);
    assertEquals(2, array[1]);
    assertEquals(3, array[2]);
    assertEquals(4, array[3]);

    // 验证返回值不是原数组
    assertNotSame(array, result);
  }

  @Test
  public void testReverseCloneShort() {
    // 测试null数组
    assertNull(ArrayUtils.reverseClone((short[]) null));

    // 测试空数组
    final short[] emptyArray = new short[0];
    short[] result = ArrayUtils.reverseClone(emptyArray);
    assertNotNull(result);
    assertEquals(0, result.length);

    // 测试单元素数组
    final short[] singleArray = {5};
    result = ArrayUtils.reverseClone(singleArray);
    assertNotNull(result);
    assertEquals(1, result.length);
    assertEquals(5, result[0]);

    // 测试多元素数组
    final short[] array = {1, 2, 3, 4};
    result = ArrayUtils.reverseClone(array);
    assertNotNull(result);
    assertEquals(4, result.length);
    assertEquals(4, result[0]);
    assertEquals(3, result[1]);
    assertEquals(2, result[2]);
    assertEquals(1, result[3]);

    // 验证原数组未改变
    assertEquals(1, array[0]);
    assertEquals(2, array[1]);
    assertEquals(3, array[2]);
    assertEquals(4, array[3]);

    // 验证返回值不是原数组
    assertNotSame(array, result);
  }
}