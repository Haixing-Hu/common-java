////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ltd.qubit.commons.datastructure.list.primitive.impl.BooleanArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ByteArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.CharArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.DoubleArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.FloatArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.LongArrayList;
import ltd.qubit.commons.datastructure.list.primitive.impl.ShortArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the Equality class.
 *
 * @author Haixing Hu
 */
public class EqualityTest {

  static class TestObject {

    private int a;

    public TestObject() {
    }

    public TestObject(final int a) {
      this.a = a;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if ((o == null) || (getClass() != o.getClass())) {
        return false;
      }
      final TestObject other = (TestObject) o;
      return Equality.equals(a, other.a);
    }

    @Override
    public int hashCode() {
      final int multiplier = 7;
      int result = 3;
      result = Hash.combine(result, multiplier, a);
      return result;
    }

    public void setA(final int a) {
      this.a = a;
    }

    public int getA() {
      return a;
    }
  }

  public static class TestACanEqualB {

    private final int a;

    public TestACanEqualB(final int a) {
      this.a = a;
    }

    @Override
    public int hashCode() {
      return a;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final TestACanEqualB other = (TestACanEqualB) obj;
      return a == other.a;
    }

    public int getA() {
      return a;
    }
  }

  public static class TestBCanEqualA {

    private final int b;

    public TestBCanEqualA(final int b) {
      this.b = b;
    }

    @Override
    public int hashCode() {
      return b;
    }

    @Override
    public boolean equals(final Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof TestACanEqualB) {
        return b == ((TestACanEqualB) o).getA();
      }
      if (o instanceof TestBCanEqualA) {
        return b == ((TestBCanEqualA) o).getB();
      }
      return false;
    }

    public int getB() {
      return b;
    }
  }

  public enum TestEnum {
    a,
    b,
    c,
    d,
    e
  }

  /**
   * Test method for {@link Equality#equals(boolean[], boolean[])}.
   */
  @Test
  public void testBooleanArray() {
    boolean[] obj1 = {true, false};
    boolean[] obj2 = {true, false};
    final boolean[] obj3 = {true, false, true};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[1] = true;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(boolean[], int, boolean[], int,
   * int)} .
   */
  @Test
  public void testBooleanArrayIndexLength() {
    final boolean[] array1 = {true, true, false, false};
    final boolean[] array2 = {true, true, false, false};
    final boolean[] array3 = {true, true, true, false};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertFalse(Equality.equals(array1, 0, array2, 1, 3));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(Boolean, Boolean)}.
   */
  @Test
  public void testBooleanObject() {
    Boolean value1 = null;
    Boolean value2 = null;

    assertTrue(Equality.equals(value1, value2));
    value1 = true;
    assertFalse(Equality.equals(value1, value2));
    value2 = false;
    assertFalse(Equality.equals(value1, value2));
    value2 = true;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Boolean[], Boolean[])}.
   */
  @Test
  public void testBooleanObjectArray() {
    Boolean[] obj1 = {true, false};
    Boolean[] obj2 = {true, false};
    final Boolean[] obj3 = {true, false, true};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[1] = true;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(Boolean[], int, Boolean[], int,
   * int)} .
   */
  @Test
  public void testBooleanObjectArrayIndexLength() {
    final Boolean[] array1 = {true, true, false, false};
    final Boolean[] array2 = {true, true, false, false};
    final Boolean[] array3 = {true, true, true, false};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertFalse(Equality.equals(array1, 0, array2, 1, 3));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(char[], char[])}.
   */
  @Test
  public void testCharArray() {
    char[] obj1 = {'a', 'b'};
    char[] obj2 = {'a', 'b'};
    final char[] obj3 = {'a', 'b', 'c'};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[1] = 'a';
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(char[], int, char[], int, int)} .
   */
  @Test
  public void testCharArrayIndexLength() {
    final char[] obj1 = {'a', 'b', 'c'};
    final char[] obj2 = {'a', 'b', 'c'};

    assertTrue(Equality.equals(obj1, 0, obj1, 0, 3));
    assertTrue(Equality.equals(obj1, 1, obj2, 1, 2));
    assertFalse(Equality.equals(obj1, 1, obj2, 0, 2));
  }

  /**
   * Test method for {@link Equality#equals(Character, Character)}.
   */
  @Test
  public void testCharacter() {
    Character value1 = null;
    Character value2 = null;

    assertTrue(Equality.equals(value1, value2));
    value1 = Character.valueOf('a');
    assertFalse(Equality.equals(value1, value2));
    value2 = Character.valueOf('a');
    assertTrue(Equality.equals(value1, value2));
    value2 = Character.valueOf('b');
    assertFalse(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Character[], Character[])} .
   */
  @Test
  public void testCharacterArray() {
    Character[] obj1 = {'a', 'b'};
    Character[] obj2 = {'a', 'b'};
    final Character[] obj3 = {'a', 'b', 'c'};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[1] = 'a';
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(Character[], int, Character[], int,
   * int)} .
   */
  @Test
  public void testCharacterArrayIndexLength() {
    final Character[] obj1 = {'a', 'b', 'c'};
    final Character[] obj2 = {'a', 'b', 'c'};

    assertTrue(Equality.equals(obj1, 0, obj1, 0, 3));
    assertTrue(Equality.equals(obj1, 1, obj2, 1, 2));
    assertFalse(Equality.equals(obj1, 1, obj2, 0, 2));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(char, char)}.
   */
  @Test
  public void testIgnoreCaseChar() {
    assertTrue(Equality.equalsIgnoreCase('a', 'a'));
    assertTrue(Equality.equalsIgnoreCase('a', 'A'));
    assertTrue(Equality.equalsIgnoreCase('A', 'a'));
    assertFalse(Equality.equalsIgnoreCase('a', 'b'));
    assertFalse(Equality.equalsIgnoreCase('a', 'B'));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(char[], char[])} .
   */
  @Test
  public void testIgnoreCaseCharArray() {
    char[] array1 = {'a', 'b', 'c'};
    char[] array2 = {'a', 'B', 'c'};
    final char[] array3 = {'A', 'B', 'C'};
    final char[] array4 = {'A', 'B', 'd'};

    assertTrue(Equality.equalsIgnoreCase(array1, array2));
    assertTrue(Equality.equalsIgnoreCase(array1, array3));
    assertTrue(Equality.equalsIgnoreCase(array3, array2));
    assertFalse(Equality.equalsIgnoreCase(array1, array4));
    array1 = null;
    assertFalse(Equality.equalsIgnoreCase(array1, array2));
    array2 = null;
    assertTrue(Equality.equalsIgnoreCase(array1, array2));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(char[], int, char[], int,
   * int)} .
   */
  @Test
  public void testIgnoreCaseCharArrayIndexLength() {
    final char[] array1 = {'a', 'b', 'c', 'd'};
    final char[] array2 = {'a', 'B', 'c', 'd'};
    final char[] array3 = {'A', 'B', 'C', 'D'};

    assertTrue(Equality.equalsIgnoreCase(array1, 0, array2, 0, 4));
    assertTrue(Equality.equalsIgnoreCase(array1, 0, array3, 0, 4));
    assertTrue(Equality.equalsIgnoreCase(array1, 1, array2, 1, 3));
    assertTrue(Equality.equalsIgnoreCase(array1, 1, array3, 1, 2));
    assertFalse(Equality.equalsIgnoreCase(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(Character, Character)} .
   */
  @Test
  public void testIgnoreCaseCharacter() {
    Character value1 = null;
    Character value2 = null;
    assertTrue(Equality.equalsIgnoreCase(value1, value2));
    value1 = 'a';
    assertFalse(Equality.equalsIgnoreCase(value1, value2));
    value2 = 'a';
    assertTrue(Equality.equalsIgnoreCase(value1, value2));
    value2 = 'A';
    assertTrue(Equality.equalsIgnoreCase(value1, value2));
    value2 = 'b';
    assertFalse(Equality.equalsIgnoreCase(value1, value2));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(Character[], Character[])}
   * .
   */
  @Test
  public void testIgnoreCaseCharacterArray() {
    Character[] array1 = {'a', 'b', 'c'};
    Character[] array2 = {'a', 'B', 'c'};
    final Character[] array3 = {'A', 'B', 'C'};
    final Character[] array4 = {'A', 'B', 'd'};

    assertTrue(Equality.equalsIgnoreCase(array1, array2));
    assertTrue(Equality.equalsIgnoreCase(array1, array3));
    assertTrue(Equality.equalsIgnoreCase(array3, array2));
    assertFalse(Equality.equalsIgnoreCase(array1, array4));
    array1 = null;
    assertFalse(Equality.equalsIgnoreCase(array1, array2));
    array2 = null;
    assertTrue(Equality.equalsIgnoreCase(array1, array2));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(Character[], int,
   * Character[], int, int)} .
   */
  @Test
  public void testIgnoreCaseCharacterArrayIndexLength() {
    final Character[] array1 = {'a', 'b', 'c', 'd'};
    final Character[] array2 = {'a', 'B', 'c', 'd'};
    final Character[] array3 = {'A', 'B', 'C', 'D'};

    assertTrue(Equality.equalsIgnoreCase(array1, 0, array2, 0, 4));
    assertTrue(Equality.equalsIgnoreCase(array1, 0, array3, 0, 4));
    assertTrue(Equality.equalsIgnoreCase(array1, 1, array2, 1, 3));
    assertTrue(Equality.equalsIgnoreCase(array1, 1, array3, 1, 2));
    assertFalse(Equality.equalsIgnoreCase(array1, 1, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(byte[], byte[])}.
   */
  @Test
  public void testByteArray() {
    byte[] obj1 = {0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    byte[] obj2 = {0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    final byte[] obj3 = {0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(byte[], int, byte[], int, int)} .
   */
  @Test
  public void testByteArrayIndexLength() {
    final byte[] array1 = {0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    final byte[] array2 = {0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    final byte[] array3 = {-1, Byte.MIN_VALUE, 0, 1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertTrue(Equality.equals(array1, 1, array2, 1, 3));
    assertFalse(Equality.equals(array1, 1, array2, 0, 3));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(Byte, Byte)}.
   */
  @Test
  public void testByteObject() {
    Byte value1 = null;
    Byte value2 = null;

    assertTrue(Equality.equals(value1, value2));
    value1 = (byte) 0;
    assertFalse(Equality.equals(value1, value2));
    value2 = (byte) 1;
    assertFalse(Equality.equals(value1, value2));
    value2 = (byte) 0;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Byte[], Byte[])}.
   */
  @Test
  public void testByteObjectArray() {
    Byte[] obj1 = new Byte[]{0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    Byte[] obj2 = new Byte[]{0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    final Byte[] obj3 = new Byte[]{0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(Byte[], int, Byte[], int, int)} .
   */
  @Test
  public void testByteObjectArrayIndexLength() {
    final Byte[] array1 = new Byte[]{0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    final Byte[] array2 = new Byte[]{0, 1, -1, Byte.MIN_VALUE, Byte.MAX_VALUE};
    final Byte[] array3 = new Byte[]{-1, Byte.MIN_VALUE, 0, 1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertTrue(Equality.equals(array1, 1, array2, 1, 3));
    assertFalse(Equality.equals(array1, 1, array2, 0, 3));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(short[], short[])}.
   */
  @Test
  public void testShortArray() {
    short[] obj1 = new short[]{0, 1};
    short[] obj2 = new short[]{0, 1};
    final short[] obj3 = new short[]{0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(short[], int, short[], int, int)} .
   */
  @Test
  public void testShortArrayIndexLength() {
    final short[] array1 = new short[]{0, -1, 1, Short.MIN_VALUE,
        Short.MAX_VALUE};
    final short[] array2 = new short[]{0, -1, 1, Short.MIN_VALUE,
        Short.MAX_VALUE};
    final short[] array3 = new short[]{1, Short.MIN_VALUE, 0, -1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertTrue(Equality.equals(array1, 1, array2, 1, 3));
    assertFalse(Equality.equals(array1, 1, array2, 0, 3));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(Short, Short)}.
   */
  @Test
  public void testShortObject() {
    Short value1 = null;
    Short value2 = null;

    assertTrue(Equality.equals(value1, value2));
    value1 = 1;
    assertFalse(Equality.equals(value1, value2));
    value2 = 2;
    assertFalse(Equality.equals(value1, value2));
    value2 = 1;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Short[], Short[])}.
   */
  @Test
  public void testShortObejctArray() {
    Short[] obj1 = new Short[]{0, -1, 1, Short.MIN_VALUE, Short.MAX_VALUE};
    Short[] obj2 = new Short[]{0, -1, 1, Short.MIN_VALUE, Short.MAX_VALUE};
    final Short[] obj3 = new Short[]{0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(short[], int, short[], int, int)} .
   */
  @Test
  public void testShortObejctArrayIndexLength() {
    final Short[] array1 = {0, -1, 1, Short.MIN_VALUE, Short.MAX_VALUE};
    final Short[] array2 = {0, -1, 1, Short.MIN_VALUE, Short.MAX_VALUE};
    final Short[] array3 = {1, Short.MIN_VALUE, 0, -1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertTrue(Equality.equals(array1, 1, array2, 1, 3));
    assertFalse(Equality.equals(array1, 1, array2, 0, 3));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(int[], int[])}.
   */
  @Test
  public void testIntArray() {
    int[] obj1 = new int[]{0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
    int[] obj2 = new int[]{0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
    final int[] obj3 = new int[]{0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(int[], int, int[], int, int)} .
   */
  @Test
  public void testIntArrayIndexLength() {
    final int[] obj1 = {0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
    final int[] obj2 = {0, -1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
    final int[] obj3 = {1, Integer.MIN_VALUE, 0, -1};

    assertTrue(Equality.equals(obj1, 0, obj2, 0, 4));
    assertTrue(Equality.equals(obj1, 0, obj2, 0, 3));
    assertFalse(Equality.equals(obj1, 0, obj2, 1, 3));
    assertTrue(Equality.equals(obj1, 2, obj3, 0, 2));
  }

  /**
   * Test method for {@link Equality#equals(Integer, Integer)}.
   */
  @Test
  public void testInteger() {
    Integer value1 = null;
    Integer value2 = null;

    assertTrue(Equality.equals(value1, value2));
    value1 = 1;
    assertFalse(Equality.equals(value1, value2));
    value2 = 2;
    assertFalse(Equality.equals(value1, value2));
    value2 = 1;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Integer[], Integer[])}.
   */
  @Test
  public void testIntegerArray() {
    Integer[] obj1 = new Integer[]{0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE};
    Integer[] obj2 = new Integer[]{0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE};
    final Integer[] obj3 = new Integer[]{0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(Integer[], int, Integer[], int,
   * int)} .
   */
  @Test
  public void testIntegerArrayIndexLength() {
    final Integer[] obj1 = new Integer[]{0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE};
    final Integer[] obj2 = new Integer[]{0, -1, 1, Integer.MIN_VALUE,
        Integer.MAX_VALUE};
    final Integer[] obj3 = new Integer[]{1, Integer.MIN_VALUE, 0, 1};

    assertTrue(Equality.equals(obj1, 0, obj2, 0, 4));
    assertTrue(Equality.equals(obj1, 0, obj2, 0, 3));
    assertFalse(Equality.equals(obj1, 0, obj2, 1, 3));
    assertTrue(Equality.equals(obj1, 2, obj3, 0, 2));
  }

  /**
   * Test method for {@link Equality#equals(long[], long[])}.
   */
  @Test
  public void testLongArray() {
    long[] obj1 = {0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE};
    long[] obj2 = {0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE};
    final long[] obj3 = {0, 1, 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(long[], int, long[], int, int)} .
   */
  @Test
  public void testLongArrayIndexLength() {
    final long[] array1 = {0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE};
    final long[] array2 = {0, -1, 1, Long.MIN_VALUE, Long.MAX_VALUE};
    final long[] array3 = {1, Long.MIN_VALUE, 0, -1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 2));
    assertTrue(Equality.equals(array1, 1, array2, 1, 2));
    assertFalse(Equality.equals(array1, 0, array3, 0, 2));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(Long, Long)}.
   */
  @Test
  public void testLongObject() {
    Long value1 = (long) 1;
    Long value2 = (long) 1;

    assertTrue(Equality.equals(value1, value2));
    value2 = (long) 2;
    assertFalse(Equality.equals(value1, value2));
    value2 = null;
    assertFalse(Equality.equals(value1, value2));
    value1 = null;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Long[], Long[])}.
   */
  @Test
  public void testLongObjectArray() {
    Long[] obj1 = {(long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        Long.MAX_VALUE};
    Long[] obj2 = {(long) 0, (long) -1, (long) 1, Long.MIN_VALUE,
        Long.MAX_VALUE};
    final Long[] obj3 = {(long) 0, (long) 1, (long) 2};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = (long) 1;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality#equals(Long[], int, Long[], int, int)} .
   */
  @Test
  public void testLongObjectArrayIndexLength() {
    final Long[] array1 = {0L, -1L, 1L,
        Long.MIN_VALUE, Long.MAX_VALUE};
    final Long[] array2 = {0L, -1L, 1L,
        Long.MIN_VALUE, Long.MAX_VALUE};
    final Long[] array3 = {1L, Long.MIN_VALUE, 0L,
        -1L};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 2));
    assertTrue(Equality.equals(array1, 1, array2, 1, 2));
    assertFalse(Equality.equals(array1, 0, array3, 0, 2));
    assertFalse(Equality.equals(array1, 1, array3, 1, 3));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality#equals(float, float)}.
   */
  @Test
  public void testFloat() {
    assertTrue(Equality.equals(0.0f, 0.0f));
    assertFalse(Equality.equals(0.0f, -0.0f));
    assertTrue(Equality.equals(Float.MAX_VALUE, Float.MAX_VALUE));
    assertTrue(Equality.equals(Float.MIN_VALUE, Float.MIN_VALUE));
    // assertTrue(Equality.equals(Float.MIN_NORMAL, Float.MIN_NORMAL));
    assertTrue(Equality.equals(Float.NaN, Float.NaN));
    assertFalse(Equality.equals(Float.MAX_VALUE, Float.NaN));
    assertFalse(Equality.equals(Float.MIN_VALUE, Float.NaN));
    // assertFalse(Equality.equals(Float.MIN_NORMAL, Float.NaN));
    assertTrue(Equality.equals(-Float.NaN, Float.NaN));
  }

  /**
   * Test method for {@link Equality#equals(float[], float[])}.
   */
  @Test
  public void testFloatArray() {
    float[] obj1 = new float[]{0.0f, 3.1415926535768f};
    float[] obj2 = new float[]{0.0f, 3.1415926535768f};
    final float[] obj3 = new float[]{-0.0f, 3.1415926535768f,
        3.1415926535768f};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = -0.0f;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality #equals(float[], int, float[], int, int)}
   * .
   */
  @Test
  public void testFloatArrayIndexLength() {
    final float[] array1 = {0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array2 = {0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array3 = {1f, Float.MIN_VALUE, 0f, -1f};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 2));
    assertFalse(Equality.equals(array1, 0, array2, 1, 3));
    assertFalse(Equality.equals(array1, 0, array3, 0, 4));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality #valueEquals(float, float, float)} .
   */
  @Test
  public void testFloatWithEpsilon() {
    final float epsilon = 0.001f;

    assertTrue(Equality.valueEquals(0.0f, 0.0f, epsilon));
    assertTrue(Equality.valueEquals(0.0f, 0.0f + epsilon, epsilon));
    assertTrue(Equality.valueEquals(0.0f, 0.0f - epsilon, epsilon));
    assertTrue(Equality.valueEquals(0.0f, -0.0f, epsilon));

    assertTrue(Equality.valueEquals(Float.MAX_VALUE, Float.MAX_VALUE, epsilon));
    assertTrue(Equality.valueEquals(Float.MAX_VALUE, Float.MAX_VALUE
        + epsilon, epsilon));
    assertTrue(Equality.valueEquals(Float.MAX_VALUE, Float.MAX_VALUE
        - epsilon, epsilon));

    assertTrue(Equality.valueEquals(Float.MIN_VALUE, Float.MIN_VALUE, epsilon));
    assertTrue(Equality.valueEquals(Float.MIN_VALUE, Float.MIN_VALUE
        + epsilon, epsilon));
    assertTrue(Equality.valueEquals(Float.MIN_VALUE, Float.MIN_VALUE
        - epsilon, epsilon));

    // assertTrue(Equality.valueEquals(Float.MIN_NORMAL,
    // Float.MIN_NORMAL, epsilon));
    // assertTrue(Equality.valueEquals(Float.MIN_NORMAL,
    // Float.MIN_NORMAL + epsilon, epsilon));
    // assertTrue(Equality.valueEquals(Float.MIN_NORMAL,
    // Float.MIN_NORMAL - epsilon, epsilon));

    assertTrue(Equality.valueEquals(Float.NaN, Float.NaN, epsilon));
    assertTrue(Equality.valueEquals(Float.NaN, Float.NaN + epsilon, epsilon));
    assertTrue(Equality.valueEquals(Float.NaN, Float.NaN - epsilon, epsilon));

    assertFalse(Equality.valueEquals(Float.MAX_VALUE, Float.NaN, epsilon));
    assertFalse(
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN + epsilon, epsilon));
    assertFalse(
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN - epsilon, epsilon));

    assertFalse(Equality.valueEquals(Float.MIN_VALUE, Float.NaN, epsilon));
    assertFalse(
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN + epsilon, epsilon));
    assertFalse(
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN - epsilon, epsilon));

    // assertFalse(Equality.equals(Float.MIN_NORMAL, Float.NaN,
    // epsilon));
    assertFalse(
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN + epsilon, epsilon));
    assertFalse(
        Equality.valueEquals(Float.MAX_VALUE, Float.NaN - epsilon, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(float[], float[], float)} .
   */
  @Test
  public void testFloatArrayWithEpsilon() {
    final float epsilon = 0.0001f;
    float[] obj1 = new float[]{0.0f, 3.1415926535768f + epsilon};
    float[] obj2 = new float[]{0.0f + epsilon, 3.1415926535768f};
    final float[] obj3 = new float[]{-0.0f, 3.1415926535768f,
        3.1415926535768f};

    assertTrue(Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    assertFalse(Equality.valueEquals(obj1, obj3, epsilon));
    assertFalse(Equality.valueEquals(obj2, obj3, epsilon));
    assertTrue(Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0f;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertFalse(Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(float[], int, float[], int,
   * int, float)} .
   */
  @Test
  public void testFloatArrayIndexLengthWithEpsilon() {
    final float epsilon = 0.01f;
    final float[] array1 = {0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE};
    final float[] array2 = {0f, -1f + epsilon, 1f, Float.MIN_VALUE + epsilon,
        Float.MAX_VALUE};
    final float[] array3 = {1f + epsilon, Float.MIN_VALUE, 0f + epsilon, -1f,
        Float.MAX_VALUE};

    assertTrue(Equality.valueEquals(array1, 0, array2, 0, 4, epsilon));
    assertTrue(Equality.valueEquals(array1, 0, array2, 0, 2, epsilon));
    assertFalse(Equality.valueEquals(array1, 0, array2, 1, 3, epsilon));
    assertFalse(Equality.valueEquals(array1, 0, array3, 0, 4, epsilon));
    assertTrue(Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for {@link Equality#equals(Float, Float)} .
   */
  @Test
  public void testFloatObject() {
    assertTrue(Equality.equals(0.0F, 0.0F));
    assertFalse(Equality.equals(0.0F, -0.0F));
    assertTrue(Equality
        .equals(Float.MAX_VALUE, Float.MAX_VALUE));
    assertTrue(Equality
        .equals(Float.MIN_VALUE, Float.MIN_VALUE));
    // assertTrue(Equality.equals(Float.MIN_NORMAL, new
    // Float(Float.MIN_NORMAL)));
    assertTrue(Equality.equals(Float.NaN, Float.NaN));
    assertFalse(
        Equality.equals(Float.MAX_VALUE, Float.NaN));
    assertFalse(
        Equality.equals(Float.MIN_VALUE, Float.NaN));
    // assertFalse(Equality.equals(Float.MIN_NORMAL, new
    // Float(Float.NaN)));
    assertTrue(Equality.equals(-Float.NaN, Float.NaN));

    assertFalse(Equality.equals(null, Float.NaN));
    assertFalse(Equality.equals(-Float.NaN, null));
    assertTrue(Equality.equals((Float) null, null));
  }

  /**
   * Test method for {@link Equality #equals(java.lang.Float[],
   * java.lang.Float[])} .
   */
  @Test
  public void testFloatObjectArray() {
    Float[] obj1 = {0.0f, 3.1415926535768f};
    Float[] obj2 = {0.0f, 3.1415926535768f};
    final Float[] obj3 = {-0.0f, 3.1415926535768f, 3.1415926535768f};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));

    obj1[0] = -0.0f;
    assertFalse(Equality.equals(obj1, obj2));
    obj1[0] = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality #equals(Float[], int, Float[], int, int)}
   * .
   */
  @Test
  public void testFloatObjectArrayIndexLength() {
    final Float[] array1 = {0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array2 = {0f, -1f, 1f, Float.MIN_VALUE, Float.MAX_VALUE};
    final Float[] array3 = {1f, Float.MIN_VALUE, 0f, -1f, Float.MAX_VALUE};

    assertTrue(Equality.equals(array1, 0, array2, 0, 4));
    assertTrue(Equality.equals(array1, 0, array2, 0, 2));
    assertFalse(Equality.equals(array1, 0, array2, 1, 3));
    assertFalse(Equality.equals(array1, 0, array3, 0, 4));
    assertTrue(Equality.equals(array1, 0, array3, 2, 2));
  }

  /**
   * Test method for {@link Equality #valueEquals(java.lang.Float,
   * java.lang.Float, float)} .
   */
  @Test
  public void testFloatObjectWithEpsilon() {
    final float epsilon = 0.001f;
    Float a = 0.0f;
    Float b = 0.0f;

    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = 0.0f + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = 0.0f - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = -0.0f;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Float.MAX_VALUE;
    b = Float.MAX_VALUE;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.MAX_VALUE + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.MAX_VALUE - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Float.MIN_VALUE;
    b = Float.MIN_VALUE;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.MIN_VALUE + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.MIN_VALUE - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    // a = Float.MIN_NORMAL;
    // b = Float.MIN_NORMAL;
    // assertTrue(Equality.valueEquals(a, b, epsilon));
    // b = Float.MIN_NORMAL + epsilon;
    // assertTrue(Equality.valueEquals(a, b, epsilon));
    // b = Float.MIN_NORMAL - epsilon;
    // assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Float.NaN;
    b = Float.NaN;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.NaN + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.NaN - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Float.MAX_VALUE;
    b = Float.NaN;
    assertFalse(Equality.valueEquals(a, b, epsilon));
    b = Float.NaN + epsilon;
    assertFalse(Equality.valueEquals(a, b, epsilon));
    b = Float.NaN - epsilon;
    assertFalse(Equality.valueEquals(a, b, epsilon));

    a = Float.NaN;
    b = null;
    assertFalse(Equality.valueEquals(a, b, epsilon));
    a = null;
    assertTrue(Equality.valueEquals(a, b, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(java.lang.Float[],
   * java.lang.Float[], float)} .
   */
  @Test
  public void testFloatObjectArrayWithEpsilon() {
    final Float epsilon = 0.0001f;
    Float[] obj1 = {0.0f, 3.1415926535768f + epsilon};
    Float[] obj2 = {0.0f + epsilon, 3.1415926535768f};
    final Float[] obj3 = {-0.0f, 3.1415926535768f,
        3.1415926535768f};

    assertTrue(Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    assertFalse(Equality.valueEquals(obj1, obj3, epsilon));
    assertFalse(Equality.valueEquals(obj2, obj3, epsilon));
    assertTrue(Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0f;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    obj1[0] = null;
    assertFalse(Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertFalse(Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(Float[], int, Float[], int,
   * int, float)} .
   */
  @Test
  public void testFloatObjectArrayIndexLengthWithEpsilon() {
    final Float epsilon = 0.001f;
    final Float[] array1 = {0f, -1f, 1f, Float.MIN_VALUE,
        Float.MAX_VALUE};
    final Float[] array2 = {0f, -1f + epsilon, 1f,
        Float.MIN_VALUE + epsilon, Float.MAX_VALUE};
    final Float[] array3 = {1f + epsilon, Float.MIN_VALUE,
        0f + epsilon, -1f, Float.MAX_VALUE};

    assertTrue(Equality.valueEquals(array1, 0, array2, 0, 5, epsilon));
    assertTrue(Equality.valueEquals(array1, 0, array2, 0, 2, epsilon));
    assertFalse(Equality.valueEquals(array1, 0, array2, 1, 3, epsilon));
    assertFalse(Equality.valueEquals(array1, 0, array3, 0, 4, epsilon));
    assertTrue(Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for {@link Equality#equals(double, double)}.
   */
  @Test
  public void testDouble() {
    assertTrue(Equality.equals(0.0, 0.0));
    assertFalse(Equality.equals(0.0, -0.0));
    assertTrue(Equality.equals(Double.MAX_VALUE, Double.MAX_VALUE));
    assertTrue(Equality.equals(Double.MIN_VALUE, Double.MIN_VALUE));
    // assertTrue(Equality.equals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL));
    assertTrue(Equality.equals(Double.NaN, Double.NaN));
    assertFalse(Equality.equals(Double.MAX_VALUE, Double.NaN));
    assertFalse(Equality.equals(Double.MIN_VALUE, Double.NaN));
    // assertFalse(Equality.equals(Double.MIN_NORMAL, Double.NaN));
    assertTrue(Equality.equals(-Double.NaN, Double.NaN));
  }

  /**
   * Test method for {@link Equality#equals(double[], double[])}.
   */
  @Test
  public void testDoubleArray() {
    double[] obj1 = {0.0, 3.1415926535768};
    double[] obj2 = {0.0, 3.1415926535768};
    final double[] obj3 = {-0.0, 3.1415926535768, 3.1415926535768};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = -0.0;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality #equals(double[], int, double[], int, int)}
   * .
   */
  @Test
  public void testDoubleArrayIndexLength() {
    final double[] array1 = {0, -1, 1, Double.MIN_VALUE,
        Double.MAX_VALUE};
    final double[] array2 = {0, -1, 1, Double.MIN_VALUE,
        Double.MAX_VALUE};
    final double[] array3 = {1, Double.MIN_VALUE, 0, -1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 5));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertFalse(Equality.equals(array1, 0, array2, 2, 3));
    assertFalse(Equality.equals(array1, 0, array3, 0, 5));
    assertFalse(Equality.equals(array1, 0, array3, 1, 3));
    assertTrue(Equality.equals(array1, 2, array3, 0, 2));
  }

  /**
   * Test method for {@link Equality #valueEquals(double, double, double)} .
   */
  @Test
  public void testDoubleWithEpsilon() {
    final double epsilon = 0.00001;

    assertTrue(Equality.valueEquals(0.0, 0.0, epsilon));
    assertTrue(Equality.valueEquals(0.0, 0.0 + epsilon, epsilon));
    assertTrue(Equality.valueEquals(0.0, 0.0 - epsilon, epsilon));
    assertTrue(Equality.valueEquals(0.0, -0.0, epsilon));

    assertTrue(
        Equality.valueEquals(Double.MAX_VALUE, Double.MAX_VALUE, epsilon));
    assertTrue(Equality.valueEquals(Double.MAX_VALUE, Double.MAX_VALUE
        + epsilon, epsilon));
    assertTrue(Equality.valueEquals(Double.MAX_VALUE, Double.MAX_VALUE
        - epsilon, epsilon));

    assertTrue(
        Equality.valueEquals(Double.MIN_VALUE, Double.MIN_VALUE, epsilon));
    assertTrue(Equality.valueEquals(Double.MIN_VALUE, Double.MIN_VALUE
        + epsilon, epsilon));
    assertTrue(Equality.valueEquals(Double.MIN_VALUE, Double.MIN_VALUE
        - epsilon, epsilon));
    //
    // assertTrue(Equality.valueEquals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL, epsilon));
    // assertTrue(Equality.valueEquals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL + epsilon, epsilon));
    // assertTrue(Equality.valueEquals(Double.MIN_NORMAL,
    // Double.MIN_NORMAL - epsilon, epsilon));

    assertTrue(Equality.valueEquals(Double.NaN, Double.NaN, epsilon));
    assertTrue(Equality.valueEquals(Double.NaN, Double.NaN + epsilon, epsilon));
    assertTrue(Equality.valueEquals(Double.NaN, Double.NaN - epsilon, epsilon));

    assertFalse(Equality.valueEquals(Double.MAX_VALUE, Double.NaN, epsilon));
    assertFalse(
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN + epsilon, epsilon));
    assertFalse(
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN - epsilon, epsilon));

    assertFalse(Equality.valueEquals(Double.MIN_VALUE, Double.NaN, epsilon));
    assertFalse(
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN + epsilon, epsilon));
    assertFalse(
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN - epsilon, epsilon));

    // assertFalse(Equality.valueEquals(Double.MIN_NORMAL, Double.NaN,
    // epsilon));
    assertFalse(
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN + epsilon, epsilon));
    assertFalse(
        Equality.valueEquals(Double.MAX_VALUE, Double.NaN - epsilon, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(double[], double[], double)}
   * .
   */
  @Test
  public void testDoubleArrayWithEpsilon() {

    final double epsilon = 0.0000001;
    double[] obj1 = {0.0, 3.1415926535768 + epsilon};
    double[] obj2 = {0.0 + epsilon, 3.1415926535768};
    final double[] obj3 = {-0.0, 3.1415926535768, 3.1415926535768};

    assertTrue(Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    assertFalse(Equality.valueEquals(obj1, obj3, epsilon));
    assertFalse(Equality.valueEquals(obj2, obj3, epsilon));
    assertTrue(Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertFalse(Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(double[], int, double[], int,
   * int, double)} .
   */
  @Test
  public void testDoubleArrayIndexLengthWithEpsilon() {
    final double epsilon = 0.0000001;
    final double[] array1 = {0, -1, 1,
        Double.MIN_VALUE, Double.MAX_VALUE};
    final double[] array2 = {0, -1 + epsilon,
        1, Double.MIN_VALUE + epsilon, Double.MAX_VALUE};
    final double[] array3 = {1, Double.MIN_VALUE,
        0, -1, Double.MAX_VALUE};

    assertTrue(Equality.valueEquals(array1, 0, array2, 0, 5, epsilon));
    assertTrue(Equality.valueEquals(array1, 1, array2, 1, 3, epsilon));
    assertFalse(Equality.valueEquals(array1, 1, array2, 2, 3, epsilon));
    assertFalse(Equality.valueEquals(array1, 0, array3, 0, 5, epsilon));
    assertTrue(Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for {@link Equality #equals(java.lang.Double,
   * java.lang.Double)} .
   */
  @Test
  public void testDoubleObject() {
    assertTrue(Equality.equals(0.0D, 0.0D));
    assertFalse(Equality.equals(0.0D, -0.0D));
    assertTrue(Equality.equals(Double.MAX_VALUE, Double.MAX_VALUE));
    assertTrue(Equality.equals(Double.MIN_VALUE, Double.MIN_VALUE));
    // assertTrue(Equality.equals(Double.MIN_NORMAL, new
    // Double(Double.MIN_NORMAL)));
    assertTrue(Equality.equals(Double.NaN, Double.NaN));
    assertFalse(Equality.equals(Double.MAX_VALUE, Double.NaN));
    assertFalse(Equality.equals(Double.MIN_VALUE, Double.NaN));
    // assertFalse(Equality.equals(Double.MIN_NORMAL, new
    // Double(Double.NaN)));
    assertTrue(Equality.equals(-Double.NaN, Double.NaN));
    assertFalse(Equality.equals(null, Float.NaN));
    assertFalse(Equality.equals(-Double.NaN, null));
    assertTrue(Equality.equals((Double) null, null));
  }

  /**
   * Test method for {@link Equality #equals(java.lang.Double[],
   * java.lang.Double[])} .
   */
  @Test
  public void testDoubleObjectArray() {
    Double[] obj1 = new Double[]{0.0, 3.1415926535768};
    Double[] obj2 = new Double[]{0.0, 3.1415926535768};
    final Double[] obj3 = new Double[]{-0.0, 3.1415926535768, 3.1415926535768};

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, obj2));
    assertFalse(Equality.equals(obj1, obj3));
    assertFalse(Equality.equals(obj2, obj3));
    assertTrue(Equality.equals(obj3, obj3));
    obj1[0] = -0.0;
    assertFalse(Equality.equals(obj1, obj2));
    obj1[0] = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));
  }

  /**
   * Test method for {@link Equality #equals(double[], int, double[], int, int)}
   * .
   */
  @Test
  public void testDoubleObjectArrayIndexLength() {
    final Double[] array1 = new Double[]{(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array2 = new Double[]{(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array3 = new Double[]{(double) 1, Double.MIN_VALUE,
        (double) 0, (double) -1};

    assertTrue(Equality.equals(array1, 0, array2, 0, 5));
    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertFalse(Equality.equals(array1, 0, array2, 2, 3));
    assertFalse(Equality.equals(array1, 0, array3, 0, 5));
    assertFalse(Equality.equals(array1, 0, array3, 1, 3));
    assertTrue(Equality.equals(array1, 2, array3, 0, 2));
  }

  /**
   * Test method for {@link Equality #valueEquals(java.lang.Double,
   * java.lang.Double, double)} .
   */
  @Test
  public void testDoubleObjectWithEpsilon() {
    final double epsilon = 0.00001;
    Double a = 0.0;
    Double b = 0.0;

    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = 0.0 + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = 0.0 - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = -0.0;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Double.MAX_VALUE;
    b = Double.MAX_VALUE;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Double.MAX_VALUE + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Double.MAX_VALUE - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Double.MIN_VALUE;
    b = Double.MIN_VALUE;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Double.MIN_VALUE + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Double.MIN_VALUE - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    //
    // a = Double.MIN_NORMAL;
    // b = Double.MIN_NORMAL;
    // assertTrue(Equality.valueEquals(a, b, epsilon));
    // b = Double.MIN_NORMAL + epsilon;
    // assertTrue(Equality.valueEquals(a, b, epsilon));
    // b = Double.MIN_NORMAL - epsilon;
    // assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Double.NaN;
    b = Double.NaN;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.NaN + epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));
    b = Float.NaN - epsilon;
    assertTrue(Equality.valueEquals(a, b, epsilon));

    a = Double.MAX_VALUE;
    b = Double.NaN;
    assertFalse(Equality.valueEquals(a, b, epsilon));
    b = Double.NaN + epsilon;
    assertFalse(Equality.valueEquals(a, b, epsilon));
    b = Double.NaN - epsilon;
    assertFalse(Equality.valueEquals(a, b, epsilon));

    a = Double.NaN;
    b = null;
    assertFalse(Equality.valueEquals(a, b, epsilon));
    a = null;
    assertTrue(Equality.valueEquals(a, b, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(java.lang.Double[],
   * java.lang.Double[], double)} .
   */
  @Test
  public void testDoubleObjectArrayWithEpsilon() {
    final Double epsilon = 0.0000001;
    Double[] obj1 = new Double[]{0.0, 3.1415926535768 + epsilon};
    Double[] obj2 = new Double[]{0.0 + epsilon, 3.1415926535768};
    final Double[] obj3 = new Double[]{-0.0, 3.1415926535768, 3.1415926535768};

    assertTrue(Equality.valueEquals(obj1, obj1, epsilon));
    // NOTE: the epsilon MUST be small enough, otherwise, obj1 may not
    // equals to obj2 because of the floating-point arithmetic inaccuracy.
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    assertFalse(Equality.valueEquals(obj1, obj3, epsilon));
    assertFalse(Equality.valueEquals(obj2, obj3, epsilon));
    assertTrue(Equality.valueEquals(obj3, obj3, epsilon));
    obj1[0] = -0.0;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
    obj1[0] = null;
    assertFalse(Equality.valueEquals(obj1, obj2, epsilon));
    obj2 = null;
    assertFalse(Equality.valueEquals(obj1, obj2, epsilon));
    obj1 = null;
    assertTrue(Equality.valueEquals(obj1, obj2, epsilon));
  }

  /**
   * Test method for {@link Equality #valueEquals(Double[], int, Double[], int,
   * int, double)} .
   */
  @Test
  public void testDoubleObjectArrayIndexLengthWithEpsilon() {
    final Double epsilon = 0.0000001;
    final Double[] array1 = new Double[]{(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE, Double.MAX_VALUE};
    final Double[] array2 = new Double[]{(double) 0, -1 + epsilon,
        (double) 1, Double.MIN_VALUE + epsilon, Double.MAX_VALUE};
    final Double[] array3 = new Double[]{1 + epsilon,
        Double.MIN_VALUE, 0 + epsilon, (double) -1, Double.MAX_VALUE};

    assertTrue(Equality.valueEquals(array1, 0, array2, 0, 5, epsilon));
    assertTrue(Equality.valueEquals(array1, 1, array2, 1, 3, epsilon));
    assertFalse(Equality.valueEquals(array1, 0, array3, 0, 5, epsilon));
    assertTrue(Equality.valueEquals(array1, 0, array3, 2, 2, epsilon));
  }

  /**
   * Test method for {@link Equality#equals(Enum, Enum)}.
   */
  @Test
  public void testEnum() {
    TestEnum value1 = TestEnum.a;
    TestEnum value2 = TestEnum.a;

    assertTrue(Equality.equals(value1, value2));
    value2 = TestEnum.b;
    assertFalse(Equality.equals(value1, value2));
    value2 = null;
    assertFalse(Equality.equals(value1, value2));
    value1 = null;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(Enum[], Enum[])}.
   */
  @Test
  public void testEnumArray() {
    TestEnum[] array1 = {TestEnum.a, TestEnum.b};
    TestEnum[] array2 = {TestEnum.a, TestEnum.b};
    final TestEnum[] Array3 = {TestEnum.a, TestEnum.b, TestEnum.c};

    assertTrue(Equality.equals(array1, array2));
    array2[1] = TestEnum.c;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertFalse(Equality.equals(array1, array2));
    array1 = null;
    assertTrue(Equality.equals(array1, array2));
    assertFalse(Equality.equals(array1, Array3));
  }

  /**
   * Test method for {@link Equality #equals(E[], int, E[], int, int)}.
   */
  @Test
  public void testEnumArrayIndexLength() {
    final TestEnum[] Array1 = {TestEnum.a, TestEnum.b, TestEnum.c, TestEnum.d};
    final TestEnum[] Array2 = {TestEnum.a, TestEnum.b, TestEnum.c, TestEnum.d};
    final TestEnum[] Array3 = {TestEnum.a, TestEnum.b, TestEnum.c, TestEnum.e};

    assertTrue(Equality.equals(Array1, 0, Array2, 0, 4));
    assertFalse(Equality.equals(Array1, 0, Array3, 0, 4));
    assertTrue(Equality.equals(Array1, 1, Array2, 1, 3));
    assertTrue(Equality.equals(Array1, 1, Array3, 1, 2));
  }

  /**
   * Test method for {@link Equality#equals(String, String)}.
   */
  @Test
  public void testString() {
    String value1 = "longlongago";
    String value2 = "longlongago";

    assertTrue(Equality.equals(value1, value2));
    value2 = "longago";
    assertFalse(Equality.equals(value1, value2));
    value2 = null;
    assertFalse(Equality.equals(value1, value2));
    value1 = null;
    assertTrue(Equality.equals(value1, value2));
  }

  /**
   * Test method for {@link Equality#equals(String[], String[])}.
   */
  @Test
  public void testStringArray() {
    String[] array1 = {"long", "long", "ago"};
    String[] array2 = {"long", "long", "ago"};
    final String[] array3 = {"long", "ago"};

    assertTrue(Equality.equals(array1, array2));
    assertFalse(Equality.equals(array1, array3));
    array2 = null;
    assertFalse(Equality.equals(array1, array2));
    array1 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  /**
   * Test method for {@link Equality #equals(String[], int, String[], int, int)}
   * .
   */
  @Test
  public void testStringArrayIndexLength() {
    final String[] array1 = {"long", "long", "ago"};
    final String[] array2 = {"long", "long", "ago"};
    final String[] array3 = {"long", "ago", "long"};

    assertTrue(Equality.equals(array1, 0, array2, 0, 3));
    assertTrue(Equality.equals(array1, 1, array2, 1, 2));
    assertFalse(Equality.equals(array1, 0, array3, 0, 3));
    assertTrue(Equality.equals(array1, 1, array3, 0, 2));
  }

  /**
   * Test method for {@link Equality#equalsIgnoreCase(String, String)}.
   */
  @Test
  public void testIgnoreCaseString() {
    String value1 = "longlongago";
    String value2 = "longlongago";

    assertTrue(Equality.equalsIgnoreCase(value1, value2));
    value2 = "LongLongAGO";
    assertTrue(Equality.equalsIgnoreCase(value1, value2));
    value2 = "longagO";
    assertFalse(Equality.equalsIgnoreCase(value1, value2));
    value2 = null;
    assertFalse(Equality.equalsIgnoreCase(value1, value2));
    value1 = null;
    assertTrue(Equality.equalsIgnoreCase(value1, value2));
  }

  /**
   * Test method for {@link Equality #equalsIgnoreCase(String[], String[])} .
   */
  @Test
  public void testIgnoreCaseStringArray() {
    String[] array1 = {"long", "long", "ago"};
    String[] array2 = {"long", "long", "ago"};
    final String[] array3 = {"lOng", "long", "AGO"};
    final String[] array4 = {"long", "ago"};

    assertTrue(Equality.equalsIgnoreCase(array1, array2));
    assertTrue(Equality.equalsIgnoreCase(array1, array3));
    assertFalse(Equality.equalsIgnoreCase(array1, array4));
    array2 = null;
    assertFalse(Equality.equalsIgnoreCase(array1, array2));
    array1 = null;
    assertTrue(Equality.equalsIgnoreCase(array1, array2));
  }

  /**
   * Test method for {@link Equality #equalsIgnoreCase(String[], int, String[],
   * int, int)} .
   */
  @Test
  public void testIgnoreCaseStringArrayIndexLength() {
    final String[] array1 = {"long", "long", "ago"};
    final String[] array2 = {"loNg", "Long", "ago"};
    final String[] array3 = {"long", "ago", "long"};

    assertTrue(Equality.equalsIgnoreCase(array1, 0, array2, 0, 3));
    assertTrue(Equality.equalsIgnoreCase(array1, 1, array2, 1, 2));
    assertFalse(Equality.equalsIgnoreCase(array1, 0, array3, 0, 3));
    assertTrue(Equality.equalsIgnoreCase(array1, 1, array3, 0, 2));
  }

  /**
   * Test method for {@link Equality #equals(String, String, boolean)}.
   */
  @Test
  public void testStringCase() {
    String value1 = "longlongago";
    String value2 = "longlongago";
    boolean casein = true;

    assertTrue(Equality.equals(value1, value2, casein));
    value2 = "LongLongAGO";
    assertTrue(Equality.equals(value1, value2, casein));
    value2 = "longagO";
    assertFalse(Equality.equals(value1, value2, casein));
    value2 = null;
    assertFalse(Equality.equals(value1, value2, casein));
    value1 = null;
    assertTrue(Equality.equals(value1, value2, casein));

    value1 = "longlongago";
    value2 = "longlongago";
    casein = false;

    assertTrue(Equality.equals(value1, value2, casein));
    value2 = "LongLongAGO";
    assertFalse(Equality.equals(value1, value2, casein));
    value2 = "longagO";
    assertFalse(Equality.equals(value1, value2, casein));
    value2 = null;
    assertFalse(Equality.equals(value1, value2, casein));
    value1 = null;
    assertTrue(Equality.equals(value1, value2, casein));
  }

  /**
   * Test method for {@link Equality #equals(String[], String[], boolean)} .
   */
  @Test
  public void testStringArrayCase() {
    String[] array1 = {"long", "long", "ago"};
    String[] array2 = {"long", "long", "ago"};
    boolean casein = true;

    assertTrue(Equality.equals(array1, array2, casein));

    assertTrue(Equality.equals(array1, array2, casein));
    array2 = new String[]{"long", "ago"};
    assertFalse(Equality.equals(array1, array2, casein));
    array2 = null;
    assertFalse(Equality.equals(array1, array2, casein));
    array1 = null;
    assertTrue(Equality.equals(array1, array2, casein));

    array1 = new String[]{"long", "long", "ago"};
    array2 = new String[]{"long", "long", "ago"};
    casein = false;

    assertTrue(Equality.equals(array1, array2, casein));
    array2 = new String[]{"lOng", "long", "AGO"};
    assertFalse(Equality.equals(array1, array2, casein));
    array2 = new String[]{"long", "ago"};
    assertFalse(Equality.equals(array1, array2, casein));
    array2 = null;
    assertFalse(Equality.equals(array1, array2, casein));
    array1 = null;
    assertTrue(Equality.equals(array1, array2, casein));
  }

  /**
   * Test method for {@link Equality #equals(String[], int, String[], int, int,
   * boolean)} .
   */
  @Test
  public void testStringArrayIndexLengthCase() {
    String[] array1 = {"long", "long", "ago"};
    String[] array2 = {"loNg", "Long", "ago"};
    String[] array3 = {"long", "ago", "long"};
    boolean casein = true;

    assertTrue(Equality.equals(array1, 0, array2, 0, 3, casein));
    assertTrue(Equality.equals(array1, 1, array2, 1, 2, casein));
    assertFalse(Equality.equals(array1, 0, array3, 0, 3, casein));
    assertTrue(Equality.equals(array1, 1, array3, 0, 2, casein));

    array1 = new String[]{"long", "long", "ago"};
    array2 = new String[]{"loNg", "Long", "ago"};
    array3 = new String[]{"long", "ago", "long"};
    casein = false;

    assertFalse(Equality.equals(array1, 0, array2, 0, 3, casein));
    assertFalse(Equality.equals(array1, 1, array2, 1, 2, casein));
    assertFalse(Equality.equals(array1, 0, array3, 0, 3, casein));
    assertTrue(Equality.equals(array1, 1, array3, 0, 2, casein));
  }

  /**
   * Test method for{@link Equality #equals(Class<?>, Class<?>)}.
   */
  @Test
  public void testClass() {

    final class TestClassA {}

    final class TestClassB {}

    assertTrue(Equality.equals(TestClassA.class, TestClassA.class));
    assertFalse(Equality.equals(TestClassA.class, TestClassB.class));
    assertFalse(Equality.equals((TestClassA) null, TestClassB.class));
    assertTrue(Equality.equals((Class<?>) null, null));
  }

  /**
   * Test method for{@link Equality #equals(Class<?>[], Class<?>[])}.
   */
  @Test
  public void testClassArray() {

    final class TestClassA {}

    final class TestClassB {}

    Class<?>[] array1 = new Class<?>[]{TestClassA.class, TestClassA.class};
    Class<?>[] array2 = new Class<?>[]{TestClassB.class, TestClassB.class};
    final Class<?>[] array3 = new Class<?>[]{TestClassA.class,
        TestClassA.class};

    assertFalse(Equality.equals(array1, array2));
    assertTrue(Equality.equals(array1, array3));
    array1 = new Class<?>[]{null};
    assertFalse(Equality.equals(array1, array2));
    array2 = new Class<?>[]{null};
    assertTrue(Equality.equals(array1, array2));
  }

  /**
   * Test method for{@link Equality #equals(Class<?>[], int, Class<?>[], int,
   * int)}.
   */
  @Test
  public void testClassArrayIndexLength() {

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[] array1 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] array2 = new Class<?>[]{TestClassB.class,
        TestClassB.class};
    final Class<?>[] array3 = new Class<?>[]{TestClassA.class,
        TestClassA.class};

    assertFalse(Equality.equals(array1, 0, array2, 0, 2));
    assertTrue(Equality.equals(array1, 0, array3, 0, 2));
    assertTrue(Equality.equals(array1, 1, array3, 1, 1));
  }

  /**
   * Test method for {@link Equality #equals(java.lang.Object,
   * java.lang.Object)} .
   */
  @Test
  public void testObject() {
    TestObject o1 = new TestObject(4);
    TestObject o2 = new TestObject(5);
    assertTrue(Equality.equals(o1, o1));
    assertFalse(Equality.equals(o1, o2));
    o2.setA(4);
    assertTrue(Equality.equals(o1, o2));
    assertFalse(Equality.equals(o1, this));
    assertFalse(Equality.equals(o1, null));
    assertFalse(Equality.equals(null, o2));
    o1 = null;
    o2 = null;
    assertTrue(Equality.equals(o1, o2));

    final boolean[] booleanarray1 = {true, false, false, true};
    final boolean[] booleanarray2 = {true, false, false, true};
    final boolean[] booleanarray3 = {false, true, true};
    assertTrue(Equality.equals(booleanarray1, (Object) booleanarray2));
    assertFalse(Equality.equals(booleanarray3, (Object) booleanarray2));

    final char[] chararray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final char[] chararray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final char[] chararray3 = {(char) 0, Character.MAX_VALUE};

    assertTrue(Equality.equals(chararray1, (Object) chararray2));
    assertFalse(Equality.equals(chararray3, (Object) chararray2));

    final byte[] bytearray1 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray2 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray3 = {(byte) 0, Byte.MAX_VALUE};
    assertTrue(Equality.equals(bytearray1, (Object) bytearray2));
    assertFalse(Equality.equals(bytearray3, (Object) bytearray2));

    final short[] shortarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray3 = {(short) 0, Short.MAX_VALUE};
    assertTrue(Equality.equals(shortarray1, (Object) shortarray2));
    assertFalse(Equality.equals(shortarray3, (Object) shortarray2));

    final int[] intarray1 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final int[] intarray2 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final int[] intarray3 = {0, Integer.MAX_VALUE};
    assertTrue(Equality.equals(intarray1, (Object) intarray2));
    assertFalse(Equality.equals(intarray3, (Object) intarray2));

    final long[] longarray1 = {0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final long[] longarray2 = {0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final long[] longarray3 = {0, Long.MAX_VALUE};
    assertTrue(Equality.equals(longarray1, (Object) longarray2));
    assertFalse(Equality.equals(longarray3, (Object) longarray2));

    final float[] floatarray1 = {0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE};
    final float[] floatarray2 = {0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE};
    final float[] floatarray3 = {0, Float.MAX_VALUE};
    assertTrue(Equality.equals(floatarray1, (Object) floatarray2));
    assertFalse(Equality.equals(floatarray3, (Object) floatarray2));

    final double[] doublearray1 = {0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final double[] doublearray2 = {0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final double[] doublearray3 = {0, Double.MAX_VALUE};
    assertTrue(Equality.equals(doublearray1, (Object) doublearray2));
    assertFalse(Equality.equals(doublearray3, (Object) doublearray2));

    final String[] stringarray1 = {"long", "long", "ago"};
    final String[] stringarray2 = {"long", "long", "ago"};
    final String[] stringarray3 = {"long", "time", "no", "see"};
    assertTrue(Equality.equals(stringarray1, (Object) stringarray2));
    assertFalse(Equality.equals(stringarray3, (Object) stringarray2));

    final Boolean[] booleanobjectarray1 = {true, false, false, true};
    final Boolean[] booleanobjectarray2 = {true, false, false, true};
    final Boolean[] booleanobjectarray3 = {false, true, true};
    assertTrue(
        Equality.equals(booleanobjectarray1, (Object) booleanobjectarray2));
    assertFalse(
        Equality.equals(booleanobjectarray3, (Object) booleanobjectarray2));

    final Character[] charobjectarray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray3 = {(char) 0, Character.MAX_VALUE};
    assertTrue(Equality.equals(charobjectarray1, (Object) charobjectarray2));
    assertFalse(Equality.equals(charobjectarray3, (Object) charobjectarray2));

    final Byte[] byteobjectarray1 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray2 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray3 = {(byte) 0, Byte.MAX_VALUE};
    assertTrue(Equality.equals(byteobjectarray1, (Object) byteobjectarray2));
    assertFalse(Equality.equals(byteobjectarray3, (Object) byteobjectarray2));

    final Short[] shortobjectarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray3 = {(short) 0, Short.MAX_VALUE};
    assertTrue(Equality.equals(shortobjectarray1, (Object) shortobjectarray2));
    assertFalse(Equality.equals(shortobjectarray3, (Object) shortobjectarray2));

    final Integer[] integerarray1 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final Integer[] integerarray2 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final Integer[] integerarray3 = {0, Integer.MAX_VALUE};
    assertTrue(Equality.equals(integerarray1, (Object) integerarray2));
    assertFalse(Equality.equals(integerarray3, (Object) integerarray2));

    final Long[] longobjectarray1 = {0L, -1L, 1L,
        Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final Long[] longobjectarray2 = {0L, -1L, 1L,
        Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};

    final Long[] longobjectarray3 = {0L, Long.MAX_VALUE};
    assertTrue(Equality.equals(longobjectarray1, (Object) longobjectarray2));
    assertFalse(Equality.equals(longobjectarray3, (Object) longobjectarray2));

    final Float[] floatobjectarray1 = {(float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, Float.MIN_VALUE / 2,
        Float.MAX_VALUE / 2, Float.MAX_VALUE};
    final Float[] floatobjectarray2 = {(float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, Float.MIN_VALUE / 2,
        Float.MAX_VALUE / 2, Float.MAX_VALUE};
    final Float[] floatobjectarray3 = {(float) 0, Float.MAX_VALUE};
    assertTrue(Equality.equals(floatobjectarray1, (Object) floatobjectarray2));
    assertFalse(Equality.equals(floatobjectarray3, (Object) floatobjectarray2));

    final Double[] doubleobjectarray1 = {(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE / 2, Double.MIN_VALUE,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final Double[] doubleobjectarray2 = {(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE / 2, Double.MIN_VALUE,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final Double[] doubleobjectarray3 = {(double) 0, Double.MAX_VALUE};
    assertTrue(
        Equality.equals(doubleobjectarray1, (Object) doubleobjectarray2));
    assertFalse(
        Equality.equals(doubleobjectarray3, (Object) doubleobjectarray2));

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[] classarray1 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray2 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray3 = new Class<?>[]{TestClassB.class,
        TestClassB.class};
    assertTrue(Equality.equals(classarray1, (Object) classarray2));
    assertFalse(Equality.equals(classarray3, (Object) classarray2));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = {datea, dateb, datec};
    final Date[] datearray2 = {datea, dateb, datec};
    final Date[] datearray3 = {datec, datea, dateb};
    assertTrue(Equality.equals(datearray1, (Object) datearray2));
    assertFalse(Equality.equals(datearray3, (Object) datearray2));

    final BigInteger[] bigintegerarray1 = {new BigInteger(bytearray1),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray2 = {new BigInteger(bytearray2),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray3 = {new BigInteger(bytearray3)};
    assertTrue(Equality.equals(bigintegerarray1, (Object) bigintegerarray2));
    assertFalse(Equality.equals(bigintegerarray3, (Object) bigintegerarray2));

    final BigDecimal[] bigdecimalarray1 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray2 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray3 = {new BigDecimal(0),
        new BigDecimal(-1)};
    assertTrue(Equality.equals(bigdecimalarray1, (Object) bigdecimalarray2));
    assertFalse(Equality.equals(bigdecimalarray3, (Object) bigdecimalarray2));

    final Boolean[][] objectarray1 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray2 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray3 = {{true, false}, {true, true}};
    assertTrue(Equality.equals(objectarray1, (Object) objectarray2));
    assertFalse(Equality.equals(objectarray3, (Object) objectarray2));

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
    assertTrue(Equality.equals(col1, (Object) col2));
    assertFalse(Equality.equals(col3, (Object) col2));
  }

  /**
   * Test method for {@link Equality#equals(Object[], Object[])} .
   */
  @Test
  public void testObjectArray() {
    TestObject[] obj1 = new TestObject[3];
    obj1[0] = new TestObject(4);
    obj1[1] = new TestObject(5);
    obj1[2] = null;
    TestObject[] obj2 = new TestObject[3];
    obj2[0] = new TestObject(4);
    obj2[1] = new TestObject(5);
    obj2[2] = null;

    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj2, obj2));
    assertTrue(Equality.equals(obj1, obj2));
    obj1[1].setA(6);
    assertFalse(Equality.equals(obj1, obj2));
    obj1[1].setA(5);
    assertTrue(Equality.equals(obj1, obj2));
    obj1[2] = obj1[1];
    assertFalse(Equality.equals(obj1, obj2));
    obj1[2] = null;
    assertTrue(Equality.equals(obj1, obj2));
    obj2 = null;
    assertFalse(Equality.equals(obj1, obj2));
    obj1 = null;
    assertTrue(Equality.equals(obj1, obj2));

    final boolean[][] booleanarray1 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray2 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    assertTrue(Equality.equals(booleanarray1, (Object) booleanarray2));
    assertFalse(Equality.equals(booleanarray3, (Object) booleanarray2));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.equals(chararray1, (Object) chararray2));
    assertFalse(Equality.equals(chararray3, (Object) chararray2));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.equals(bytearray1, (Object) bytearray2));
    assertFalse(Equality.equals(bytearray3, (Object) bytearray2));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.equals(shortarray1, (Object) shortarray2));
    assertFalse(Equality.equals(shortarray3, (Object) shortarray2));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(Equality.equals(intarray1, (Object) intarray2));
    assertFalse(Equality.equals(intarray3, (Object) intarray2));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, Long.MAX_VALUE}};
    assertTrue(Equality.equals(longarray1, (Object) longarray2));
    assertFalse(Equality.equals(longarray3, (Object) longarray2));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, Float.MAX_VALUE}};
    assertTrue(Equality.equals(floatarray1, (Object) floatarray2));
    assertFalse(Equality.equals(floatarray3, (Object) floatarray2));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, Double.MAX_VALUE}};
    assertTrue(Equality.equals(doublearray1, (Object) doublearray2));
    assertFalse(Equality.equals(doublearray3, (Object) doublearray2));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    assertTrue(Equality.equals(stringarray1, (Object) stringarray2));
    assertFalse(Equality.equals(stringarray3, (Object) stringarray2));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    assertTrue(
        Equality.equals(booleanobjectarray1, (Object) booleanobjectarray2));
    assertFalse(
        Equality.equals(booleanobjectarray3, (Object) booleanobjectarray2));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.equals(charobjectarray1, (Object) charobjectarray2));
    assertFalse(Equality.equals(charobjectarray3, (Object) charobjectarray2));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.equals(byteobjectarray1, (Object) byteobjectarray2));
    assertFalse(Equality.equals(byteobjectarray3, (Object) byteobjectarray2));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.equals(shortobjectarray1, (Object) shortobjectarray2));
    assertFalse(Equality.equals(shortobjectarray3, (Object) shortobjectarray2));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(Equality.equals(integerarray1, (Object) integerarray2));
    assertFalse(Equality.equals(integerarray3, (Object) integerarray2));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, Long.MAX_VALUE}};
    assertTrue(Equality.equals(longobjectarray1, (Object) longobjectarray2));
    assertFalse(Equality.equals(longobjectarray3, (Object) longobjectarray2));

    final Float[][] floatobjectarray1 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{(float) 0, Float.MAX_VALUE}};
    assertTrue(Equality.equals(floatobjectarray1, (Object) floatobjectarray2));
    assertFalse(Equality.equals(floatobjectarray3, (Object) floatobjectarray2));

    final Double[][] doubleobjectarray1 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{(double) 0, Double.MAX_VALUE}};
    assertTrue(
        Equality.equals(doubleobjectarray1, (Object) doubleobjectarray2));
    assertFalse(
        Equality.equals(doubleobjectarray3, (Object) doubleobjectarray2));

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class, TestClassA.class}};
    assertTrue(Equality.equals(classarray1, classarray2));
    assertFalse(Equality.equals(classarray3, classarray2));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec}, {datea, dateb}};
    assertTrue(Equality.equals(datearray1, (Object) datearray2));
    assertFalse(Equality.equals(datearray3, (Object) datearray2));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    assertTrue(Equality.equals(bigintegerarray1, (Object) bigintegerarray2));
    assertFalse(Equality.equals(bigintegerarray3, (Object) bigintegerarray2));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    assertTrue(Equality.equals(bigdecimalarray1, (Object) bigdecimalarray2));
    assertFalse(Equality.equals(bigdecimalarray3, (Object) bigdecimalarray2));

    final Boolean[][][][] objectarray1 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray2 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray3 = {{{{true}, {false}}},
        {{{true, true}}}};
    assertTrue(Equality.equals(objectarray1, (Object) objectarray2));
    assertFalse(Equality.equals(objectarray3, (Object) objectarray2));

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
    assertTrue(Equality.equals(col1, col2));
    assertFalse(Equality.equals(col3, col2));
  }

  @Test
  public void testObjectArrayIndexLength() {
    final boolean[][] booleanarray1 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray2 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    assertTrue(Equality.equals(booleanarray1, 0,
        booleanarray2, 0, booleanarray2.length));
    assertFalse(Equality.equals(booleanarray3, 0,
        booleanarray2, 0, booleanarray2.length));
    assertFalse(Equality.equals(booleanarray1, 1, booleanarray2, 0, 1));
    assertTrue(Equality.equals(booleanarray1, 0, booleanarray2, 2, 1));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.equals(chararray1, 0, chararray2, 0,
        chararray2.length));
    assertFalse(Equality.equals(chararray3, 0, chararray2,
        0, chararray2.length));
    assertFalse(Equality.equals(chararray3, 0, chararray2, 2, 1));
    assertTrue(Equality.equals(chararray1, 1, chararray2, 1, 1));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.equals(bytearray1, 0, bytearray2, 0,
        bytearray2.length));
    assertFalse(Equality.equals(bytearray3, 0, bytearray2,
        0, bytearray2.length));
    assertFalse(Equality.equals(bytearray3, 0, bytearray2, 2, 1));
    assertTrue(Equality.equals(bytearray1, 1, bytearray2, 1, 1));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.equals(shortarray1, 0, shortarray2,
        0, shortarray2.length));
    assertFalse(Equality.equals(shortarray3, 0, shortarray2,
        0, shortarray2.length));
    assertFalse(Equality.equals(shortarray3, 0, shortarray2, 2, 1));
    assertTrue(Equality.equals(shortarray1, 1, shortarray2, 1, 1));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, Integer.MAX_VALUE}};
    assertEquals(
        true,
        Equality.equals(intarray1, 0, intarray2, 0, intarray2.length));
    assertEquals(
        false,
        Equality.equals(intarray3, 0, intarray2, 0, intarray2.length));
    assertFalse(Equality.equals(intarray3, 0, intarray2, 2, 1));
    assertTrue(Equality.equals(intarray1, 1, intarray2, 1, 1));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, Long.MAX_VALUE}};
    assertTrue(Equality.equals(longarray1, 0, longarray2, 0,
        longarray2.length));
    assertFalse(Equality.equals(longarray3, 0, longarray2,
        0, longarray2.length));
    assertFalse(Equality.equals(longarray3, 0, longarray2, 2, 1));
    assertTrue(Equality.equals(longarray1, 1, longarray2, 1, 1));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, Float.MAX_VALUE}};
    assertTrue(Equality.equals(floatarray1, 0, floatarray2,
        0, floatarray2.length));
    assertFalse(Equality.equals(floatarray3, 0, floatarray2,
        0, floatarray2.length));
    assertFalse(Equality.equals(floatarray3, 0, floatarray2, 2, 1));
    assertTrue(Equality.equals(floatarray1, 1, floatarray2, 1, 1));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, Double.MAX_VALUE}};
    assertTrue(Equality.equals(doublearray1, 0,
        doublearray2, 0, doublearray2.length));
    assertFalse(Equality.equals(doublearray3, 0,
        doublearray2, 0, doublearray2.length));
    assertFalse(Equality.equals(doublearray3, 0, doublearray2, 2, 1));
    assertTrue(Equality.equals(doublearray1, 1, doublearray2, 1, 1));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    assertTrue(Equality.equals(stringarray1, 0,
        stringarray2, 0, stringarray2.length));
    assertFalse(Equality.equals(stringarray3, 0,
        stringarray2, 0, stringarray2.length));
    assertFalse(Equality.equals(stringarray3, 0, stringarray2, 1, 1));
    assertTrue(Equality.equals(stringarray1, 1, stringarray2, 1, 1));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    assertTrue(Equality.equals(booleanobjectarray1, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length));
    assertFalse(Equality.equals(booleanobjectarray3, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length));
    assertFalse(Equality.equals(booleanobjectarray3, 0,
        booleanobjectarray2, 2, 1));
    assertTrue(Equality.equals(booleanobjectarray1, 1,
        booleanobjectarray2, 1, 1));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.equals(charobjectarray1, 0,
        charobjectarray2, 0, charobjectarray2.length));
    assertFalse(Equality.equals(charobjectarray3, 0,
        charobjectarray2, 0, charobjectarray2.length));
    assertFalse(Equality.equals(charobjectarray3, 0, charobjectarray2, 2, 1));
    assertTrue(Equality.equals(charobjectarray1, 1, charobjectarray2, 1, 1));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.equals(byteobjectarray1, 0,
        byteobjectarray2, 0, byteobjectarray2.length));
    assertFalse(Equality.equals(byteobjectarray3, 0,
        byteobjectarray2, 0, byteobjectarray2.length));
    assertFalse(Equality.equals(byteobjectarray3, 0, byteobjectarray2, 2, 1));
    assertTrue(Equality.equals(byteobjectarray1, 1, byteobjectarray2, 1, 1));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.equals(shortobjectarray1, 0,
        shortobjectarray2, 0, shortobjectarray2.length));
    assertFalse(Equality.equals(shortobjectarray3, 0,
        shortobjectarray2, 0, shortobjectarray2.length));
    assertFalse(Equality.equals(shortobjectarray3, 0,
        shortobjectarray2, 2, 1));
    assertTrue(Equality.equals(shortobjectarray1, 1,
        shortobjectarray2, 1, 1));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(Equality.equals(integerarray1, 0,
        integerarray2, 0, integerarray2.length));
    assertFalse(Equality.equals(integerarray3, 0,
        integerarray2, 0, integerarray2.length));
    assertFalse(Equality.equals(integerarray3, 0, integerarray2, 2, 1));
    assertTrue(Equality.equals(integerarray1, 1, integerarray2, 1, 1));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, Long.MAX_VALUE}};
    assertTrue(Equality.equals(longobjectarray1, 0,
        longobjectarray2, 0, longobjectarray2.length));
    assertFalse(Equality.equals(longobjectarray3, 0,
        longobjectarray2, 0, longobjectarray2.length));
    assertFalse(Equality.equals(longobjectarray3, 0, longobjectarray2, 2, 1));
    assertTrue(Equality.equals(longobjectarray1, 1, longobjectarray2, 1, 1));

    final Float[][] floatobjectarray1 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{(float) 0, Float.MAX_VALUE}};
    assertTrue(Equality.equals(floatobjectarray1, 0,
        floatobjectarray2, 0, floatobjectarray2.length));
    assertFalse(Equality.equals(floatobjectarray3, 0,
        floatobjectarray2, 0, floatobjectarray2.length));
    assertFalse(Equality.equals(floatobjectarray3, 0,
        floatobjectarray2, 2, 1));
    assertTrue(Equality.equals(floatobjectarray1, 1,
        floatobjectarray2, 1, 1));

    final Double[][] doubleobjectarray1 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{(double) 0, Double.MAX_VALUE}};
    assertTrue(Equality.equals(doubleobjectarray1, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length));
    assertFalse(Equality.equals(doubleobjectarray3, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length));
    assertFalse(Equality.equals(doubleobjectarray3, 0,
        doubleobjectarray2, 2, 1));
    assertTrue(Equality.equals(doubleobjectarray1, 1,
        doubleobjectarray2, 1, 1));

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class, TestClassA.class}};

    assertTrue(
        Equality.equals(classarray1, 0, classarray2, 0, classarray2.length));
    assertTrue(
        Equality.equals(classarray1, 0, classarray2, 0, classarray2.length));
    assertFalse(
        Equality.equals(classarray3, 0, classarray2, 0, classarray2.length));
    assertFalse(Equality.equals(classarray3, 0, classarray2, 1, 1));
    assertTrue(Equality.equals(classarray1, 1, classarray2, 1, 1));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec}, {datea, dateb}};
    assertTrue(Equality.equals(datearray1, 0, datearray2, 0,
        datearray2.length));
    assertFalse(Equality.equals(datearray3, 0, datearray2,
        0, datearray2.length));
    assertFalse(Equality.equals(datearray3, 0, datearray2, 0, 1));
    assertTrue(Equality.equals(datearray3, 0, datearray2, 1, 1));
    assertTrue(Equality.equals(datearray1, 1, datearray2, 1, 1));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    assertTrue(Equality.equals(bigintegerarray1, 0,
        bigintegerarray2, 0, bigintegerarray2.length));
    assertFalse(Equality.equals(bigintegerarray3, 0,
        bigintegerarray2, 0, bigintegerarray2.length));
    assertFalse(Equality.equals(bigintegerarray3, 0, bigintegerarray2, 0, 1));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    assertTrue(Equality.equals(bigdecimalarray1, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length));
    assertFalse(Equality.equals(bigdecimalarray3, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length));
    assertFalse(Equality.equals(bigdecimalarray3, 0, bigdecimalarray2, 1, 1));
    assertTrue(Equality.equals(bigdecimalarray1, 1, bigdecimalarray2, 1, 1));

    final Boolean[][][][] objectarray1 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray2 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray3 = {{{{true}, {false}}},
        {{{true, true}}}};
    assertTrue(
        Equality.equals(objectarray1, 0, objectarray2, 0, objectarray2.length));
    assertFalse(
        Equality.equals(objectarray3, 0, objectarray2, 0, objectarray2.length));
    assertFalse(Equality.equals(objectarray3, 0, objectarray2, 1, 1));
    assertTrue(Equality.equals(objectarray1, 1, objectarray2, 1, 1));

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
    assertTrue(Equality.equals(col1, 0, col2, 0, col2.length));
    assertFalse(Equality.equals(col3, 0, col2, 0, col2.length));
    assertFalse(Equality.equals(col3, 0, col2, 1, 1));
    assertTrue(Equality.equals(col1, 1, col2, 1, 1));
  }

  /**
   * Test method for {@link Equality#valueEquals(Double, Double, double)} .
   */
  @Test
  public void testObjectWithEpsilon() {
    final double epsilon = 0.01;
    final ArrayList<Double> value1 = new ArrayList<>();
    value1.add(0.0);
    value1.add(1.0);
    value1.add(-1.0);
    value1.add(Double.MAX_VALUE);
    final ArrayList<Double> value2 = new ArrayList<>();
    value2.add(0.0);
    value2.add(1.0);
    value2.add(-1.0);
    value2.add(Double.MAX_VALUE);

    assertTrue(Equality.valueEquals(value1, (Object) value2, epsilon));
    value2.add(-1.0 + epsilon);
    assertFalse(Equality.valueEquals(value1, (Object) value2, epsilon));
    assertFalse(Equality.valueEquals(value1, (Object) null, epsilon));
    assertTrue(Equality.valueEquals((Object) null, null, epsilon));

    final boolean[] booleanarray1 = {true, false, false, true};
    final boolean[] booleanarray2 = {true, false, false, true};
    final boolean[] booleanarray3 = {false, true, true};
    assertTrue(Equality.valueEquals(booleanarray1, booleanarray2, epsilon));
    assertFalse(Equality.valueEquals(booleanarray3, booleanarray2, epsilon));

    final char[] chararray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final char[] chararray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final char[] chararray3 = {(char) 0, Character.MAX_VALUE};
    assertTrue(Equality.valueEquals(chararray1, chararray2, epsilon));
    assertFalse(Equality.valueEquals(chararray3, chararray2, epsilon));

    final byte[] bytearray1 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray2 = {(byte) 0, (byte) -1, (byte) 1, Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final byte[] bytearray3 = {(byte) 0, Byte.MAX_VALUE};
    assertTrue(Equality.valueEquals(bytearray1, bytearray2, epsilon));
    assertFalse(Equality.valueEquals(bytearray3, bytearray2, epsilon));

    final short[] shortarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MAX_VALUE,
        (short) (Short.MIN_VALUE / 2), Short.MIN_VALUE,
        (short) (Short.MAX_VALUE / 2)};
    final short[] shortarray3 = {(short) 0, Short.MAX_VALUE};
    assertTrue(Equality.valueEquals(shortarray1, shortarray2, epsilon));
    assertFalse(Equality.valueEquals(shortarray3, shortarray2, epsilon));

    final int[] intarray1 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final int[] intarray2 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final int[] intarray3 = {0, Integer.MAX_VALUE};
    assertTrue(Equality.valueEquals(intarray1, intarray2, epsilon));
    assertFalse(Equality.valueEquals(intarray3, intarray2, epsilon));

    final long[] longarray1 = {0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final long[] longarray2 = {0, -1, 1, Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final long[] longarray3 = {0, Long.MAX_VALUE};
    assertTrue(Equality.valueEquals(longarray1, longarray2, epsilon));
    assertFalse(Equality.valueEquals(longarray3, longarray2, epsilon));

    final float[] floatarray1 = {0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE};
    final float[] floatarray2 = {0, -1, 1, Float.MIN_VALUE,
        Float.MIN_VALUE / 2, Float.MAX_VALUE / 2,
        Float.MAX_VALUE};
    final float[] floatarray3 = {0, Float.MAX_VALUE};
    assertTrue(Equality.valueEquals(floatarray1, floatarray2, epsilon));
    assertFalse(Equality.valueEquals(floatarray3, floatarray2, epsilon));

    final double[] doublearray1 = {0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final double[] doublearray2 = {0, -1, 1,
        Double.MIN_VALUE, Double.MIN_VALUE / 2,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final double[] doublearray3 = {0, Double.MAX_VALUE};
    assertTrue(
        Equality.valueEquals(doublearray1, (Object) doublearray2, epsilon));
    assertFalse(
        Equality.valueEquals(doublearray3, (Object) doublearray2, epsilon));

    final String[] stringarray1 = {"long", "long", "ago"};
    final String[] stringarray2 = {"long", "long", "ago"};
    final String[] stringarray3 = {"long", "time", "no", "see"};
    assertTrue(
        Equality.valueEquals(stringarray1, (Object) stringarray2, epsilon));
    assertFalse(
        Equality.valueEquals(stringarray3, (Object) stringarray2, epsilon));

    final Boolean[] booleanobjectarray1 = {true, false, false, true};
    final Boolean[] booleanobjectarray2 = {true, false, false, true};
    final Boolean[] booleanobjectarray3 = {false, true, true};
    assertTrue(Equality.valueEquals(booleanobjectarray1,
        (Object) booleanobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(booleanobjectarray3,
        (Object) booleanobjectarray2, epsilon));

    final Character[] charobjectarray1 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray2 = {(char) 0, (char) 1,
        (char) (Character.MIN_VALUE / 2), Character.MIN_VALUE,
        (char) (Character.MAX_VALUE / 2), Character.MAX_VALUE};
    final Character[] charobjectarray3 = {(char) 0, Character.MAX_VALUE};
    assertTrue(Equality.valueEquals(charobjectarray1,
        (Object) charobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(charobjectarray3,
        (Object) charobjectarray2, epsilon));

    final Byte[] byteobjectarray1 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray2 = {(byte) 0, (byte) -1, (byte) 1,
        Byte.MIN_VALUE,
        (byte) (Byte.MIN_VALUE / 2), (byte) (Byte.MAX_VALUE / 2),
        Byte.MAX_VALUE};
    final Byte[] byteobjectarray3 = {(byte) 0, Byte.MAX_VALUE};
    assertTrue(Equality.valueEquals(byteobjectarray1,
        (Object) byteobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(byteobjectarray3,
        (Object) byteobjectarray2, epsilon));

    final Short[] shortobjectarray1 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray2 = {(short) 0, (short) -1, (short) 1,
        Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2),
        (short) (Short.MAX_VALUE / 2), Short.MAX_VALUE};
    final Short[] shortobjectarray3 = {(short) 0, Short.MAX_VALUE};
    assertTrue(Equality.valueEquals(shortobjectarray1,
        (Object) shortobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(shortobjectarray3,
        (Object) shortobjectarray2, epsilon));

    final Integer[] integerarray1 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final Integer[] integerarray2 = {0, -1, 1, Integer.MIN_VALUE,
        Integer.MIN_VALUE / 2, Integer.MAX_VALUE / 2,
        Integer.MAX_VALUE};
    final Integer[] integerarray3 = {0, Integer.MAX_VALUE};
    assertTrue(
        Equality.valueEquals(integerarray1, (Object) integerarray2, epsilon));
    assertFalse(
        Equality.valueEquals(integerarray3, (Object) integerarray2, epsilon));

    final Long[] longobjectarray1 = {0L, -1L, 1L,
        Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final Long[] longobjectarray2 = {0L, -1L, 1L,
        Long.MIN_VALUE,
        Long.MIN_VALUE / 2, Long.MAX_VALUE / 2,
        Long.MAX_VALUE};
    final Long[] longobjectarray3 = {0L, Long.MAX_VALUE};
    assertTrue(Equality.valueEquals(longobjectarray1,
        (Object) longobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(longobjectarray3,
        (Object) longobjectarray2, epsilon));

    final Float[] floatobjectarray1 = {(float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, Float.MIN_VALUE / 2,
        Float.MAX_VALUE / 2, Float.MAX_VALUE};
    final Float[] floatobjectarray2 = {(float) 0, (float) -1, (float) 1,
        Float.MIN_VALUE, Float.MIN_VALUE / 2,
        Float.MAX_VALUE / 2, Float.MAX_VALUE};
    final Float[] floatobjectarray3 = {(float) 0, Float.MAX_VALUE};
    assertTrue(Equality.valueEquals(floatobjectarray1,
        (Object) floatobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(floatobjectarray3,
        (Object) floatobjectarray2, epsilon));

    final Double[] doubleobjectarray1 = {(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE / 2, Double.MIN_VALUE,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final Double[] doubleobjectarray2 = {(double) 0, (double) -1, (double) 1,
        Double.MIN_VALUE / 2, Double.MIN_VALUE,
        Double.MAX_VALUE / 2, Double.MAX_VALUE};
    final Double[] doubleobjectarray3 = {(double) 0, Double.MAX_VALUE};
    assertTrue(Equality.valueEquals(doubleobjectarray1,
        (Object) doubleobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(doubleobjectarray3,
        (Object) doubleobjectarray2, epsilon));

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[] classarray1 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray2 = new Class<?>[]{TestClassA.class,
        TestClassA.class};
    final Class<?>[] classarray3 = new Class<?>[]{TestClassB.class,
        TestClassB.class};
    assertTrue(
        Equality.valueEquals(classarray1, (Object) classarray2, epsilon));
    assertFalse(
        Equality.valueEquals(classarray3, (Object) classarray2, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[] datearray1 = {datea, dateb, datec};
    final Date[] datearray2 = {datea, dateb, datec};
    final Date[] datearray3 = {datec, datea, dateb};
    assertTrue(Equality.valueEquals(datearray1, (Object) datearray2, epsilon));
    assertFalse(Equality.valueEquals(datearray3, (Object) datearray2, epsilon));

    final BigInteger[] bigintegerarray1 = {new BigInteger(bytearray1),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray2 = {new BigInteger(bytearray2),
        new BigInteger(bytearray3)};
    final BigInteger[] bigintegerarray3 = {new BigInteger(bytearray3)};
    assertTrue(Equality.valueEquals(bigintegerarray1,
        (Object) bigintegerarray2, epsilon));
    assertFalse(Equality.valueEquals(bigintegerarray3,
        (Object) bigintegerarray2, epsilon));

    final BigDecimal[] bigdecimalarray1 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray2 = {new BigDecimal(Integer.MIN_VALUE),
        new BigDecimal(Integer.MAX_VALUE)};
    final BigDecimal[] bigdecimalarray3 = {new BigDecimal(0),
        new BigDecimal(-1)};
    assertTrue(Equality.valueEquals(bigdecimalarray1,
        (Object) bigdecimalarray2, epsilon));
    assertFalse(Equality.valueEquals(bigdecimalarray3,
        (Object) bigdecimalarray2, epsilon));

    final Boolean[][] objectarray1 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray2 = {{true, false}, {true, true},
        {false, false, true}};
    final Boolean[][] objectarray3 = {{true, false}, {true, true}};
    assertTrue(
        Equality.valueEquals(objectarray1, (Object) objectarray2, epsilon));
    assertFalse(
        Equality.valueEquals(objectarray3, (Object) objectarray2, epsilon));

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
    assertTrue(Equality.valueEquals(col1, (Object) col2, epsilon));
    assertFalse(Equality.valueEquals(col3, (Object) col2, epsilon));
  }

  /**
   * Test method for {@link Equality#valueEquals(Object[], Object[], double)} .
   */
  @Test
  public void testObjectArrayWithEpsilon() {
    final double epsilon = 0.01;

    final boolean[][] booleanarray1 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray2 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    assertTrue(
        Equality.valueEquals(booleanarray1, (Object) booleanarray2, epsilon));
    assertFalse(
        Equality.valueEquals(booleanarray3, (Object) booleanarray2, epsilon));
    assertFalse(Equality.valueEquals(booleanarray1, (Object) null, epsilon));
    assertTrue(Equality.valueEquals((Object) null, null, epsilon));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.valueEquals(chararray1, (Object) chararray2, epsilon));
    assertFalse(Equality.valueEquals(chararray3, (Object) chararray2, epsilon));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.valueEquals(bytearray1, (Object) bytearray2, epsilon));
    assertFalse(Equality.valueEquals(bytearray3, (Object) bytearray2, epsilon));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(
        Equality.valueEquals(shortarray1, (Object) shortarray2, epsilon));
    assertFalse(
        Equality.valueEquals(shortarray3, (Object) shortarray2, epsilon));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(Equality.valueEquals(intarray1, (Object) intarray2, epsilon));
    assertFalse(Equality.valueEquals(intarray3, (Object) intarray2, epsilon));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, Long.MAX_VALUE}};
    assertTrue(Equality.valueEquals(longarray1, (Object) longarray2, epsilon));
    assertFalse(Equality.valueEquals(longarray3, (Object) longarray2, epsilon));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, Float.MAX_VALUE}};
    assertTrue(
        Equality.valueEquals(floatarray1, (Object) floatarray2, epsilon));
    assertFalse(
        Equality.valueEquals(floatarray3, (Object) floatarray2, epsilon));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, Double.MAX_VALUE}};
    assertTrue(
        Equality.valueEquals(doublearray1, (Object) doublearray2, epsilon));
    assertFalse(
        Equality.valueEquals(doublearray3, (Object) doublearray2, epsilon));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    assertTrue(
        Equality.valueEquals(stringarray1, (Object) stringarray2, epsilon));
    assertFalse(
        Equality.valueEquals(stringarray3, (Object) stringarray2, epsilon));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    assertTrue(Equality.valueEquals(booleanobjectarray1,
        (Object) booleanobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(booleanobjectarray3,
        (Object) booleanobjectarray2, epsilon));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.valueEquals(charobjectarray1,
        (Object) charobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(charobjectarray3,
        (Object) charobjectarray2, epsilon));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.valueEquals(byteobjectarray1,
        (Object) byteobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(byteobjectarray3,
        (Object) byteobjectarray2, epsilon));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.valueEquals(shortobjectarray1,
        (Object) shortobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(shortobjectarray3,
        (Object) shortobjectarray2, epsilon));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(
        Equality.valueEquals(integerarray1, (Object) integerarray2, epsilon));
    assertFalse(
        Equality.valueEquals(integerarray3, (Object) integerarray2, epsilon));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, Long.MAX_VALUE}};
    assertTrue(Equality.valueEquals(longobjectarray1,
        (Object) longobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(longobjectarray3,
        (Object) longobjectarray2, epsilon));

    final Float[][] floatobjectarray1 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{(float) 0, Float.MAX_VALUE}};
    assertTrue(Equality.valueEquals(floatobjectarray1,
        (Object) floatobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(floatobjectarray3,
        (Object) floatobjectarray2, epsilon));

    final Double[][] doubleobjectarray1 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{(double) 0, Double.MAX_VALUE}};
    assertTrue(Equality.valueEquals(doubleobjectarray1,
        (Object) doubleobjectarray2, epsilon));
    assertFalse(Equality.valueEquals(doubleobjectarray3,
        (Object) doubleobjectarray2, epsilon));

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};

    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};

    final Class<?>[][] classarray3 = {{TestClassB.class, TestClassA.class}};

    assertTrue(Equality.valueEquals(classarray1, classarray2, epsilon));
    assertFalse(Equality.valueEquals(classarray2, classarray3, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec}, {datea, dateb}};
    assertTrue(Equality.valueEquals(datearray1, datearray2, epsilon));
    assertFalse(Equality.valueEquals(datearray3, datearray2, epsilon));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    assertTrue(Equality.valueEquals(bigintegerarray1,
        (Object) bigintegerarray2, epsilon));
    assertFalse(Equality.valueEquals(bigintegerarray3,
        (Object) bigintegerarray2, epsilon));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    assertTrue(Equality.valueEquals(bigdecimalarray1,
        (Object) bigdecimalarray2, epsilon));
    assertFalse(Equality.valueEquals(bigdecimalarray3,
        (Object) bigdecimalarray2, epsilon));

    final Boolean[][][][] objectarray1 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray2 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray3 = {{{{true}, {false}}},
        {{{true, true}}}};
    assertTrue(
        Equality.valueEquals(objectarray1, (Object) objectarray2, epsilon));
    assertFalse(
        Equality.valueEquals(objectarray3, (Object) objectarray2, epsilon));

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
    assertTrue(Equality.valueEquals(col1, (Object) col2, epsilon));
    assertFalse(Equality.valueEquals(col3, (Object) col2, epsilon));
  }

  /**
   * Test method for {@link Equality #equal(java.lang.Object[], int,
   * java.lang.Object[], int, int, double)} .
   */
  @Test
  public void testObjectArrayIndexLengthWithEpsilon() {
    final double epsilon = 0.0001;

    final boolean[][] booleanarray1 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray2 = {{true}, {false, false}, {true}};
    final boolean[][] booleanarray3 = {{false, true}, {true}};
    assertTrue(Equality.valueEquals(booleanarray1, 0,
        booleanarray2, 0, booleanarray2.length, epsilon));
    assertFalse(Equality.valueEquals(booleanarray3, 0,
        booleanarray2, 0, booleanarray2.length, epsilon));
    assertFalse(Equality.valueEquals(booleanarray1, 1,
        booleanarray2, 0, 1, epsilon));
    assertTrue(Equality.valueEquals(booleanarray1, 0,
        booleanarray2, 2, 1, epsilon));

    final char[][] chararray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final char[][] chararray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.valueEquals(chararray1, 0,
        chararray2, 0, chararray2.length, epsilon));
    assertFalse(Equality.valueEquals(chararray3, 0,
        chararray2, 0, chararray2.length, epsilon));
    assertFalse(Equality.valueEquals(chararray3, 0,
        chararray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(chararray1, 1,
        chararray2, 1, 1, epsilon));

    final byte[][] bytearray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final byte[][] bytearray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.valueEquals(bytearray1, 0,
        bytearray2, 0, bytearray2.length, epsilon));
    assertFalse(Equality.valueEquals(bytearray3, 0,
        bytearray2, 0, bytearray2.length, epsilon));
    assertFalse(Equality.valueEquals(bytearray3, 0,
        bytearray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(bytearray1, 1,
        bytearray2, 1, 1, epsilon));

    final short[][] shortarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MAX_VALUE, (short) (Short.MIN_VALUE / 2)},
        {Short.MIN_VALUE, (short) (Short.MAX_VALUE / 2)}};
    final short[][] shortarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.valueEquals(shortarray1, 0,
        shortarray2, 0, shortarray2.length, epsilon));
    assertFalse(Equality.valueEquals(shortarray3, 0,
        shortarray2, 0, shortarray2.length, epsilon));
    assertFalse(Equality.valueEquals(shortarray3, 0,
        shortarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(shortarray1, 1,
        shortarray2, 1, 1, epsilon));

    final int[][] intarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final int[][] intarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(Equality.valueEquals(intarray1, 0, intarray2,
        0, intarray2.length, epsilon));
    assertFalse(Equality.valueEquals(intarray3, 0,
        intarray2, 0, intarray2.length, epsilon));
    assertFalse(Equality.valueEquals(intarray3, 0, intarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(intarray1, 1, intarray2, 1, 1, epsilon));

    final long[][] longarray1 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray2 = {{0, -1, 1},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final long[][] longarray3 = {{0, Long.MAX_VALUE}};
    assertTrue(Equality.valueEquals(longarray1, 0,
        longarray2, 0, longarray2.length, epsilon));
    assertFalse(Equality.valueEquals(longarray3, 0,
        longarray2, 0, longarray2.length, epsilon));
    assertFalse(Equality.valueEquals(longarray3, 0,
        longarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(longarray1, 1,
        longarray2, 1, 1, epsilon));

    final float[][] floatarray1 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray2 = {{0, -1, 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final float[][] floatarray3 = {{0, Float.MAX_VALUE}};
    assertTrue(Equality.valueEquals(floatarray1, 0,
        floatarray2, 0, floatarray2.length, epsilon));
    assertFalse(Equality.valueEquals(floatarray3, 0,
        floatarray2, 0, floatarray2.length, epsilon));
    assertFalse(Equality.valueEquals(floatarray3, 0,
        floatarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(floatarray1, 1,
        floatarray2, 1, 1, epsilon));

    final double[][] doublearray1 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray2 = {{0, -1, 1},
        {Double.MIN_VALUE, Double.MIN_VALUE / 2},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final double[][] doublearray3 = {{0, Double.MAX_VALUE}};
    assertTrue(Equality.valueEquals(doublearray1, 0,
        doublearray2, 0, doublearray2.length, epsilon));
    assertFalse(Equality.valueEquals(doublearray3, 0,
        doublearray2, 0, doublearray2.length, epsilon));
    assertFalse(Equality.valueEquals(doublearray3, 0,
        doublearray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(doublearray1, 1,
        doublearray2, 1, 1, epsilon));

    final String[][] stringarray1 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray2 = {{"long", "long"}, {"ago"}};
    final String[][] stringarray3 = {{"long", "time"}, {"no", "see"}};
    assertTrue(Equality.valueEquals(stringarray1, 0,
        stringarray2, 0, stringarray2.length, epsilon));
    assertFalse(Equality.valueEquals(stringarray3, 0,
        stringarray2, 0, stringarray2.length, epsilon));
    assertFalse(Equality.valueEquals(stringarray3, 0,
        stringarray2, 1, 1, epsilon));
    assertTrue(Equality.valueEquals(stringarray1, 1,
        stringarray2, 1, 1, epsilon));

    final Boolean[][] booleanobjectarray1 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray2 = {{true, false}, {false}, {true}};
    final Boolean[][] booleanobjectarray3 = {{false, true}, {true}};
    assertTrue(Equality.valueEquals(booleanobjectarray1, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(booleanobjectarray3, 0,
        booleanobjectarray2, 0, booleanobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(booleanobjectarray3, 0,
        booleanobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(booleanobjectarray1, 1,
        booleanobjectarray2, 1, 1, epsilon));

    final Character[][] charobjectarray1 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray2 = {{(char) 0, (char) 1},
        {(char) (Character.MIN_VALUE / 2), Character.MIN_VALUE},
        {(char) (Character.MAX_VALUE / 2), Character.MAX_VALUE}};
    final Character[][] charobjectarray3 = {{(char) 0, Character.MAX_VALUE}};
    assertTrue(Equality.valueEquals(charobjectarray1, 0,
        charobjectarray2, 0, charobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(charobjectarray3, 0,
        charobjectarray2, 0, charobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(charobjectarray3, 0,
        charobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(charobjectarray1, 1,
        charobjectarray2, 1, 1, epsilon));

    final Byte[][] byteobjectarray1 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray2 = {{(byte) 0, (byte) -1, (byte) 1},
        {Byte.MIN_VALUE, (byte) (Byte.MIN_VALUE / 2)},
        {(byte) (Byte.MAX_VALUE / 2), Byte.MAX_VALUE}};
    final Byte[][] byteobjectarray3 = {{(byte) 0, Byte.MAX_VALUE}};
    assertTrue(Equality.valueEquals(byteobjectarray1, 0,
        byteobjectarray2, 0, byteobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(byteobjectarray3, 0,
        byteobjectarray2, 0, byteobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(byteobjectarray3, 0,
        byteobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(byteobjectarray1, 1,
        byteobjectarray2, 1, 1, epsilon));

    final Short[][] shortobjectarray1 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray2 = {{(short) 0, (short) -1, (short) 1},
        {Short.MIN_VALUE, (short) (Short.MIN_VALUE / 2)},
        {(short) (Short.MAX_VALUE / 2), Short.MAX_VALUE}};
    final Short[][] shortobjectarray3 = {{(short) 0, Short.MAX_VALUE}};
    assertTrue(Equality.valueEquals(shortobjectarray1, 0,
        shortobjectarray2, 0, shortobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(shortobjectarray3, 0,
        shortobjectarray2, 0, shortobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(shortobjectarray3, 0,
        shortobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(shortobjectarray1, 1,
        shortobjectarray2, 1, 1, epsilon));

    final Integer[][] integerarray1 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray2 = {{0, -1, 1},
        {Integer.MIN_VALUE, Integer.MIN_VALUE / 2},
        {Integer.MAX_VALUE / 2, Integer.MAX_VALUE}};
    final Integer[][] integerarray3 = {{0, Integer.MAX_VALUE}};
    assertTrue(Equality.valueEquals(integerarray1, 0,
        integerarray2, 0, integerarray2.length, epsilon));
    assertFalse(Equality.valueEquals(integerarray3, 0,
        integerarray2, 0, integerarray2.length, epsilon));
    assertFalse(Equality.valueEquals(integerarray3, 0,
        integerarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(integerarray1, 1,
        integerarray2, 1, 1, epsilon));

    final Long[][] longobjectarray1 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray2 = {{0L, -1L, 1L},
        {Long.MIN_VALUE, Long.MIN_VALUE / 2},
        {Long.MAX_VALUE / 2, Long.MAX_VALUE}};
    final Long[][] longobjectarray3 = {{0L, Long.MAX_VALUE}};
    assertTrue(Equality.valueEquals(longobjectarray1, 0,
        longobjectarray2, 0, longobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(longobjectarray3, 0,
        longobjectarray2, 0, longobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(longobjectarray3, 0,
        longobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(longobjectarray1, 1,
        longobjectarray2, 1, 1, epsilon));

    final Float[][] floatobjectarray1 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray2 = {{(float) 0, (float) -1, (float) 1},
        {Float.MIN_VALUE, Float.MIN_VALUE / 2},
        {Float.MAX_VALUE / 2, Float.MAX_VALUE}};
    final Float[][] floatobjectarray3 = {{(float) 0, Float.MAX_VALUE}};
    assertTrue(Equality.valueEquals(floatobjectarray1, 0,
        floatobjectarray2, 0, floatobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(floatobjectarray3, 0,
        floatobjectarray2, 0, floatobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(floatobjectarray3, 0,
        floatobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(floatobjectarray1, 1,
        floatobjectarray2, 1, 1, epsilon));

    final Double[][] doubleobjectarray1 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray2 = {
        {(double) 0, (double) -1, (double) 1},
        {Double.MIN_VALUE / 2, Double.MIN_VALUE},
        {Double.MAX_VALUE / 2, Double.MAX_VALUE}};
    final Double[][] doubleobjectarray3 = {{(double) 0, Double.MAX_VALUE}};
    assertTrue(Equality.valueEquals(doubleobjectarray1, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(doubleobjectarray3, 0,
        doubleobjectarray2, 0, doubleobjectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(doubleobjectarray3, 0,
        doubleobjectarray2, 2, 1, epsilon));
    assertTrue(Equality.valueEquals(doubleobjectarray1, 1,
        doubleobjectarray2, 1, 1, epsilon));

    final class TestClassA {}

    final class TestClassB {}

    final Class<?>[][] classarray1 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray2 = {{TestClassA.class}, {TestClassA.class}};
    final Class<?>[][] classarray3 = {{TestClassB.class, TestClassA.class}};

    assertTrue(Equality.valueEquals(classarray1, 0, classarray2, 0,
        classarray2.length, epsilon));
    assertTrue(Equality.valueEquals(classarray1, 0, classarray2, 0,
        classarray2.length, epsilon));
    assertFalse(Equality.valueEquals(classarray3, 0, classarray2, 0,
        classarray2.length, epsilon));
    assertFalse(
        Equality.valueEquals(classarray3, 0, classarray2, 1, 1, epsilon));
    assertTrue(
        Equality.valueEquals(classarray1, 1, classarray2, 1, 1, epsilon));

    final Date datea = new Date();
    datea.setTime(123456);
    final Date dateb = new Date();
    dateb.setTime(7891011);
    final Date datec = new Date();
    datec.setTime(654321);
    final Date[][] datearray1 = {{datea, dateb}, {datec}};
    final Date[][] datearray2 = {{datea, dateb}, {datec}};
    final Date[][] datearray3 = {{datec}, {datea, dateb}};
    assertTrue(Equality.valueEquals(datearray1, 0,
        datearray2, 0, datearray2.length, epsilon));
    assertFalse(Equality.valueEquals(datearray3, 0,
        datearray2, 0, datearray2.length, epsilon));
    assertFalse(Equality.valueEquals(datearray3, 0,
        datearray2, 0, 1, epsilon));
    assertTrue(Equality.valueEquals(datearray3, 0,
        datearray2, 1, 1, epsilon));
    assertTrue(Equality.valueEquals(datearray1, 1,
        datearray2, 1, 1, epsilon));

    final BigInteger[][] bigintegerarray1 = {{new BigInteger(bytearray1[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray2 = {{new BigInteger(bytearray2[0]),
        new BigInteger(bytearray3[0])}};
    final BigInteger[][] bigintegerarray3 = {{new BigInteger(bytearray3[0])}};
    assertTrue(Equality.valueEquals(bigintegerarray1, 0,
        bigintegerarray2, 0, bigintegerarray2.length, epsilon));
    assertFalse(Equality.valueEquals(bigintegerarray3, 0,
        bigintegerarray2, 0, bigintegerarray2.length, epsilon));
    assertFalse(Equality.valueEquals(bigintegerarray3, 0,
        bigintegerarray2, 0, 1, epsilon));

    final BigDecimal[][] bigdecimalarray1 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray2 = {
        {new BigDecimal(Integer.MIN_VALUE)},
        {new BigDecimal(Integer.MAX_VALUE)}};
    final BigDecimal[][] bigdecimalarray3 = {
        {new BigDecimal(0), new BigDecimal(-1)}};
    assertTrue(Equality.valueEquals(bigdecimalarray1, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length, epsilon));
    assertFalse(Equality.valueEquals(bigdecimalarray3, 0,
        bigdecimalarray2, 0, bigdecimalarray2.length, epsilon));
    assertFalse(Equality.valueEquals(bigdecimalarray3, 0,
        bigdecimalarray2, 1, 1, epsilon));
    assertTrue(Equality.valueEquals(bigdecimalarray1, 1,
        bigdecimalarray2, 1, 1, epsilon));

    final Boolean[][][][] objectarray1 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray2 = {{{{true, false}, {true, true}}},
        {{{false, false}, {true}}}};
    final Boolean[][][][] objectarray3 = {{{{true}, {false}}},
        {{{true, true}}}};
    assertTrue(Equality.valueEquals(objectarray1, 0,
        objectarray2, 0, objectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(objectarray3, 0,
        objectarray2, 0, objectarray2.length, epsilon));
    assertFalse(Equality.valueEquals(objectarray3, 0,
        objectarray2, 1, 1, epsilon));
    assertTrue(Equality.valueEquals(objectarray1, 1,
        objectarray2, 1, 1, epsilon));

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
    assertTrue(Equality.valueEquals(col1, 0, col2, 0, col2.length, epsilon));
    assertFalse(Equality.valueEquals(col3, 0, col2, 0, col2.length, epsilon));
    assertFalse(Equality.valueEquals(col3, 0, col2, 1, 1, epsilon));
    assertTrue(Equality.valueEquals(col1, 1, col2, 1, 1, epsilon));
  }

  public void testMultiBooleanArray() {
    boolean[][] array1 = new boolean[2][2];
    boolean[][] array2 = new boolean[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (i == 1) || (j == 1);
        array2[i][j] = (i == 1) || (j == 1);
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = false;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final boolean[] array3 = {true, true};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiCharArray() {
    char[][] array1 = new char[2][2];
    char[][] array2 = new char[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (char) i;
        array2[i][j] = (char) i;
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = 128;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final char[] array3 = {1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiByteArray() {
    byte[][] array1 = new byte[2][2];
    byte[][] array2 = new byte[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (byte) i;
        array2[i][j] = (byte) i;
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = (byte) 100;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final byte[] array3 = {1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiShortArray() {
    short[][] array1 = new short[2][2];
    short[][] array2 = new short[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (short) i;
        array2[i][j] = (short) i;
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = (short) 100;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final short[] array3 = new short[]{1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiIntArray() {
    int[][] array1 = new int[2][2];
    int[][] array2 = new int[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = i;
        array2[i][j] = i;
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = 100;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final int[] array3 = new int[]{1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiLongArray() {
    long[][] array1 = new long[2][2];
    long[][] array2 = new long[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = i;
        array2[i][j] = i;
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = 100;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final long[] array3 = new long[]{1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiFloatArray() {
    float[][] array1 = new float[2][2];
    float[][] array2 = new float[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (i + 1) * (j + 1);
        array2[i][j] = (i + 1) * (j + 1);
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = -0.0f;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final float[] array3 = new float[]{1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiDoubleArray() {
    double[][] array1 = new double[2][2];
    double[][] array2 = new double[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (i + 1) * (j + 1);
        array2[i][j] = (i + 1) * (j + 1);
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = -0.0;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final double[] array3 = {1, 2};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiFloatObjectArray() {
    Float[][] array1 = new Float[2][2];
    Float[][] array2 = new Float[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (float) ((i + 1) * (j + 1));
        array2[i][j] = (float) ((i + 1) * (j + 1));
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = -0.0f;
    assertFalse(Equality.equals(array1, array2));
    array1[1][1] = null;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final Float[] array3 = {1.0f, 2.0f};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testMultiDoubleObjectArray() {
    Double[][] array1 = new Double[2][2];
    Double[][] array2 = new Double[2][2];
    for (int i = 0; i < array1.length; ++i) {
      for (int j = 0; j < array1[0].length; j++) {
        array1[i][j] = (double) ((i + 1) * (j + 1));
        array2[i][j] = (double) ((i + 1) * (j + 1));
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = -0.0;
    assertFalse(Equality.equals(array1, array2));
    array1[1][1] = null;
    assertFalse(Equality.equals(array1, array2));

    // compare 1 dim to 2.
    final Double[] array3 = new Double[]{1.0, 2.0};
    assertFalse(Equality.equals(array1, array3));
    assertFalse(Equality.equals(array3, array1));
    assertFalse(Equality.equals(array2, array3));
    assertFalse(Equality.equals(array3, array2));

    array1 = null;
    assertFalse(Equality.equals(array1, array2));
    array2 = null;
    assertTrue(Equality.equals(array1, array2));
  }

  public void testRaggedArray() {
    final long[][] array1 = new long[2][];
    final long[][] array2 = new long[2][];
    for (int i = 0; i < array1.length; ++i) {
      array1[i] = new long[2];
      array2[i] = new long[2];
      for (int j = 0; j < array1[i].length; ++j) {
        array1[i][j] = (i + 1) * (j + 1);
        array2[i][j] = (i + 1) * (j + 1);
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));
    array1[1][1] = 0;
    assertFalse(Equality.equals(array1, array2));
  }

  public void testMixedArray() {
    final Object[] array1 = new Object[2];
    final Object[] array2 = new Object[2];
    for (int i = 0; i < array1.length; ++i) {
      array1[i] = new long[2];
      array2[i] = new long[2];
      for (int j = 0; j < 2; ++j) {
        ((long[]) array1[i])[j] = (i + 1) * (j + 1);
        ((long[]) array2[i])[j] = (i + 1) * (j + 1);
      }
    }
    assertTrue(Equality.equals(array1, array1));
    assertTrue(Equality.equals(array1, array2));

    ((long[]) array1[1])[1] = 0;
    assertFalse(Equality.equals(array1, array2));
  }

  public void testBooleanArrayHiddenByObject() {
    final boolean[] array1 = new boolean[2];
    array1[0] = true;
    array1[1] = false;
    final boolean[] array2 = new boolean[2];
    array2[0] = true;
    array2[1] = false;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = true;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testCharArrayHiddenByObject() {
    final char[] array1 = new char[2];
    array1[0] = '1';
    array1[1] = '2';
    final char[] array2 = new char[2];
    array2[0] = '1';
    array2[1] = '2';
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = '3';
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testByteArrayHiddenByObject() {
    final byte[] array1 = new byte[2];
    array1[0] = 1;
    array1[1] = 2;
    final byte[] array2 = new byte[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testShortArrayHiddenByObject() {
    final short[] array1 = new short[2];
    array1[0] = 1;
    array1[1] = 2;
    final short[] array2 = new short[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testIntArrayHiddenByObject() {
    final int[] array1 = new int[2];
    array1[0] = 1;
    array1[1] = 2;
    final int[] array2 = new int[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testLongArrayHiddenByObject() {
    final long[] array1 = new long[2];
    array1[0] = 1;
    array1[1] = 2;
    final long[] array2 = new long[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testFloatArrayHiddenByObject() {
    final float[] array1 = new float[2];
    array1[0] = 1;
    array1[1] = 2;
    final float[] array2 = new float[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testDoubleArrayHiddenByObject() {
    final double[] array1 = new double[2];
    array1[0] = 1;
    array1[1] = 2;
    final double[] array2 = new double[2];
    array2[0] = 1;
    array2[1] = 2;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testFloatObjectArrayHiddenByObject() {
    final Float[] array1 = new Float[2];
    array1[0] = 1.0f;
    array1[1] = 2.0f;
    final Float[] array2 = new Float[2];
    array2[0] = 1.0f;
    array2[1] = 2.0f;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3.0f;
    assertFalse(Equality.equals(obj1, obj2));
    array1[1] = null;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testDoubleObjectArrayHiddenByObject() {
    final Double[] array1 = new Double[2];
    array1[0] = 1.0;
    array1[1] = 2.0;
    final Double[] array2 = new Double[2];
    array2[0] = 1.0;
    array2[1] = 2.0;
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1] = 3.0;
    assertFalse(Equality.equals(obj1, obj2));
    array1[1] = null;
    assertFalse(Equality.equals(obj1, obj2));
  }

  public void testObjectArrayHiddenByObject() {
    final TestObject[] array1 = new TestObject[2];
    array1[0] = new TestObject(4);
    array1[1] = new TestObject(5);
    final TestObject[] array2 = new TestObject[2];
    array2[0] = new TestObject(4);
    array2[1] = new TestObject(5);
    final Object obj1 = array1;
    final Object obj2 = array2;
    assertTrue(Equality.equals(obj1, obj1));
    assertTrue(Equality.equals(obj1, array1));
    assertTrue(Equality.equals(obj1, obj2));
    assertTrue(Equality.equals(obj1, array2));
    array1[1].setA(6);
    assertFalse(Equality.equals(obj1, obj2));
  }

  /*
   * Tests two instances of classes that can be equal and that are not
   * "related". The two classes are not subclasses of each other and do not
   * share a parent aside from Object.
   */
  public void testUnrelatedClasses() {
    final Object[] x = new Object[]{new TestACanEqualB(1)};
    final Object[] y = new Object[]{new TestBCanEqualA(1)};

    // sanity checks:
    assertTrue(Arrays.equals(x, x));
    assertTrue(Arrays.equals(y, y));
    assertTrue(Arrays.equals(x, y));
    assertTrue(Arrays.equals(y, x));
    // real tests:
    assertTrue(x[0].equals(x[0]));
    assertTrue(y[0].equals(y[0]));
    assertTrue(x[0].equals(y[0]));
    assertTrue(y[0].equals(x[0]));
    assertTrue(Equality.equals(x, x));
    assertTrue(Equality.equals(y, y));
    assertTrue(Equality.equals(x, y));
    assertTrue(Equality.equals(y, x));
  }

  /*
   * Test for null element in the array.
   */
  public void testNpeForNullElement() {
    final Object[] x1 = new Object[]{1, null, 3};
    final Object[] x2 = new Object[]{1, 2,
        3};
    assertFalse(Equality.equals(x1, x2));
  }

  /**
   * Test method for {@link Equality #equals(BooleanCollection,
   * BooleanCollection)} .
   */
  @Test
  public void testBooleanCollection() {
    BooleanArrayList col1 = new BooleanArrayList();
    BooleanArrayList col2 = new BooleanArrayList();

    assertTrue(Equality.equals(col1, col2));
    col1.add(true);
    col2.add(true);
    assertTrue(Equality.equals(col1, col2));
    col1.add(false);
    col2.add(false);
    assertTrue(Equality.equals(col1, col2));
    col1.add(true);
    col2.add(false);
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #equals(CharCollection, CharCollection)} .
   */
  @Test
  public void testCharCollection() {
    CharArrayList col1 = new CharArrayList(new char[]{'a', 'b', 'c', 'd'});
    CharArrayList col2 = new CharArrayList(new char[]{'a', 'b', 'c', 'd'});

    assertTrue(Equality.equals(col1, col2));
    col1.add('e');
    assertFalse(Equality.equals(col1, col2));
    col2.add('e');
    assertTrue(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #equals(ByteCollection, ByteCollection)} .
   */
  @Test
  public void testByteCollection() {
    ByteArrayList col1 = new ByteArrayList(new byte[]{1, 2, 3, 4});
    ByteArrayList col2 = new ByteArrayList(new byte[]{1, 2, 3, 4});

    assertTrue(Equality.equals(col1, col2));
    col1.add((byte) 5);
    assertFalse(Equality.equals(col1, col2));
    col2.add((byte) 5);
    assertTrue(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #equals(ShortCollection, ShortCollection)}
   * .
   */
  @Test
  public void testShortCollection() {
    ShortArrayList col1 = new ShortArrayList();
    ShortArrayList col2 = new ShortArrayList();

    col1.add((short) 2);
    assertFalse(Equality.equals(col1, col2));
    col2.add((short) 2);
    assertTrue(Equality.equals(col1, col2));
    col1.add((short) 5);
    assertFalse(Equality.equals(col1, col2));
    col2.add((short) 6);
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #equals(IntCollection, IntCollection)} .
   */
  @Test
  public void testIntCollection() {
    IntArrayList col1 = new IntArrayList();
    IntArrayList col2 = new IntArrayList();

    col1.add(2);
    assertFalse(Equality.equals(col1, col2));
    col2.add(2);
    assertTrue(Equality.equals(col1, col2));
    col1.add(5);
    assertFalse(Equality.equals(col1, col2));
    col2.add(6);
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #equals(LongCollection, LongCollection)} .
   */
  @Test
  public void testLongCollection() {
    LongArrayList col1 = new LongArrayList();
    LongArrayList col2 = new LongArrayList();

    col1.add(2);
    assertFalse(Equality.equals(col1, col2));
    col2.add(2);
    assertTrue(Equality.equals(col1, col2));
    col1.add(5);
    assertFalse(Equality.equals(col1, col2));
    col2.add(6);
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #equals(FloatCollection, FloatCollection)}
   * .
   */
  @Test
  public void testFloatCollection() {
    FloatArrayList col1 = new FloatArrayList();
    FloatArrayList col2 = new FloatArrayList();

    col1.add(2);
    assertFalse(Equality.equals(col1, col2));
    col2.add(2);
    assertTrue(Equality.equals(col1, col2));
    col1.add(5);
    assertFalse(Equality.equals(col1, col2));
    col2.add(6);
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #valueEquals(FloatCollection,
   * FloatCollection, float)} .
   */
  @Test
  public void testFloatCollectionWithEpsilon() {
    FloatArrayList col1 = new FloatArrayList();
    FloatArrayList col2 = new FloatArrayList();
    final float epsilon = 0.01f;

    col1.add(2);
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col2.add(2 + epsilon);
    assertTrue(Equality.valueEquals(col1, col2, epsilon));
    col1.add(5);
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col2.add(6);
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col1 = null;
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col2 = null;
    assertTrue(Equality.valueEquals(col1, col2, epsilon));
  }

  /**
   * Test method for {@link Equality #equals(DoubleCollection,
   * DoubleCollection)} .
   */
  @Test
  public void testDoubleCollection() {
    DoubleArrayList col1 = new DoubleArrayList();
    DoubleArrayList col2 = new DoubleArrayList();

    col1.add(2);
    assertFalse(Equality.equals(col1, col2));
    col2.add(2);
    assertTrue(Equality.equals(col1, col2));
    col1.add(5);
    assertFalse(Equality.equals(col1, col2));
    col2.add(6);
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  /**
   * Test method for {@link Equality #valueEquals(DoubleCollection,
   * DoubleCollection, double)} .
   */
  @Test
  public void testDoubleCollectionWithEpsilon() {
    DoubleArrayList col1 = new DoubleArrayList();
    DoubleArrayList col2 = new DoubleArrayList();
    final double epsilon = 0.01;

    col1.add(2);
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col2.add(2 + epsilon);
    assertTrue(Equality.valueEquals(col1, col2, epsilon));
    col1.add(5);
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col2.add(6);
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col1 = null;
    assertFalse(Equality.valueEquals(col1, col2, epsilon));
    col2 = null;
    assertTrue(Equality.valueEquals(col1, col2, epsilon));
  }

  @Test
  public void testCollection() {
    ArrayList<Character> col1 = new ArrayList<>();
    col1.add('a');
    ArrayList<Character> col2 = new ArrayList<>();
    col2.add('a');

    assertTrue(Equality.equals(col1, col2));
    col2.add(0, 'b');
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  @Test
  public void testCollectionWithEpsilon() {
    ArrayList<Double> col1 = new ArrayList<>();
    col1.add(0.002);
    final double epsilon = 0.000001;
    ArrayList<Double> col2 = new ArrayList<>();
    col2.add((0.002));

    assertTrue(Equality.valueEquals(col1, col2, epsilon));
    col1.add(0, 0.3);
    assertFalse(Equality.equals(col1, col2));
    col2 = null;
    assertFalse(Equality.equals(col1, col2));
    col1 = null;
    assertTrue(Equality.equals(col1, col2));
  }

  @Test
  public void testBigDecimalEquals() {
    BigDecimal b1 = null;
    BigDecimal b2 = null;
    assertTrue(Equality.equals(b1, b2));

    b1 = BigDecimal.ONE;
    b2 = null;
    assertFalse(Equality.equals(b1, b2));

    b1 = null;
    b2 = BigDecimal.ONE;
    assertFalse(Equality.equals(b1, b2));

    b1 = new BigDecimal("3.1415926");
    b2 = new BigDecimal("3.1415926");
    assertTrue(Equality.equals(b1, b2));

    b1 = new BigDecimal("3.1415926");
    b2 = new BigDecimal("3.1415927");
    assertFalse(Equality.equals(b1, b2));

    b1 = new BigDecimal("3.1415926000");
    b2 = new BigDecimal("3.1415926");
    assertFalse(Equality.equals(b1, b2));

    b1 = new BigDecimal("3");
    b2 = new BigDecimal("3.00000");
    assertFalse(Equality.equals(b1, b2));
  }
}
