////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.FloatUtils;
import ltd.qubit.commons.lang.Type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test of the {@link BasicMultiValues} class.
 *
 * @author Haixing Hu
 */
public class BasicMultiValuesTest {

  /**
   * Test method for {@link BasicMultiValues#BasicVariants()}.
   */
  @Test
  public void testBasicVariants() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues();
    assertEquals(BasicMultiValues.DEFAULT_TYPE, prop.getType());
    assertEquals(0, prop.getCount());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(Type)}.
   */
  @Test
  public void testBasicVariantsType() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues(Type.STRING);
    assertEquals(Type.STRING, prop.getType());
    assertEquals(0, prop.getCount());

    prop = new BasicMultiValues(Type.BOOL);
    assertEquals(Type.BOOL, prop.getType());
    assertEquals(0, prop.getCount());

    prop = new BasicMultiValues(Type.BIG_DECIMAL);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(0, prop.getCount());

    try {
      final Type type = null;
      prop = new BasicMultiValues(type);
      fail("Should throw a NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(boolean)}.
   */
  @Test
  public void testBasicVariantsBoolean() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues(true);
    assertEquals(Type.BOOL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(true, prop.getBooleanValue());
    assertTrue(Equality.equals(new boolean[]{true}, prop.getBooleanValues()));

    prop = new BasicMultiValues(false);
    assertEquals(Type.BOOL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(false, prop.getBooleanValue());
    assertTrue(Equality.equals(new boolean[]{false}, prop.getBooleanValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(char)}.
   */
  @Test
  public void testBasicVariantsChar() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues('x');
    assertEquals(Type.CHAR, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals('x', prop.getCharValue());
    assertTrue(Equality.equals(new char[]{'x'}, prop.getCharValues()));

    prop = new BasicMultiValues('y');
    assertEquals(Type.CHAR, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals('y', prop.getCharValue());
    assertTrue(Equality.equals(new char[]{'y'}, prop.getCharValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(byte)}.
   */
  @Test
  public void testBasicVariantsByte() {
    BasicMultiValues prop = null;
    byte value = 0;

    value = (byte) 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = (byte) 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = (byte) -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = Byte.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));

    value = Byte.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BYTE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getByteValue());
    assertTrue(Equality.equals(new byte[]{value}, prop.getByteValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(short)}.
   */
  @Test
  public void testBasicVariantsShort() {
    BasicMultiValues prop = null;
    short value = 0;

    value = (short) 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = (short) 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = (short) -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = Short.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));

    value = Short.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.SHORT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getShortValue());
    assertTrue(Equality.equals(new short[]{value}, prop.getShortValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(int)}.
   */
  @Test
  public void testBasicVariantsInt() {
    BasicMultiValues prop = null;
    int value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = Integer.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));

    value = Integer.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.INT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getIntValue());
    assertTrue(Equality.equals(new int[]{value}, prop.getIntValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(long)}.
   */
  @Test
  public void testBasicVariantsLong() {
    BasicMultiValues prop = null;
    long value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = 10;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = -100;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = Long.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));

    value = Long.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.LONG, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getLongValue());
    assertTrue(Equality.equals(new long[]{value}, prop.getLongValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(float)}.
   */
  @Test
  public void testBasicVariantsFloat() {
    BasicMultiValues prop = null;
    float value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = 10.123f;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = -3.1415f;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.MIN_NORMAL;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.NaN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.NEGATIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));

    value = Float.POSITIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.FLOAT, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getFloatValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new float[]{value}, prop.getFloatValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(double)}.
   */
  @Test
  public void testBasicVariantsDouble() {
    BasicMultiValues prop = null;
    double value = 0;

    value = 0;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = 10.123;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = -3.1415;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.MIN_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.MIN_NORMAL;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.MAX_VALUE;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.NaN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.NEGATIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));

    value = Double.POSITIVE_INFINITY;
    prop = new BasicMultiValues(value);
    assertEquals(Type.DOUBLE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getDoubleValue(), FloatUtils.EPSILON);
    assertTrue(Equality.equals(new double[]{value}, prop.getDoubleValues()));
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(String)}.
   */
  @Test
  public void testBasicVariantsString() {
    BasicMultiValues prop = null;

    prop = new BasicMultiValues("value1");
    assertEquals(Type.STRING, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals("value1", prop.getStringValue());
    assertArrayEquals(new String[]{"value1"}, prop.getStringValues());

    prop = new BasicMultiValues("");
    assertEquals(Type.STRING, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals("", prop.getStringValue());
    assertArrayEquals(new String[]{""}, prop.getStringValues());

    final String value = null;
    prop = new BasicMultiValues(value);
    assertEquals(Type.STRING, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(null, prop.getStringValue());
    assertArrayEquals(new String[]{null}, prop.getStringValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(Date)}.
   */
  @Test
  public void testBasicVariantsDate() {
    BasicMultiValues prop = null;
    Date date = new Date();

    prop = new BasicMultiValues(date);
    assertEquals(Type.DATE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(date, prop.getDateValue());
    assertNotSame(date, prop.getDateValue());
    assertArrayEquals(new Date[]{date}, prop.getDateValues());

    date.setTime(200);
    prop = new BasicMultiValues(date);
    assertEquals(Type.DATE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(date, prop.getDateValue());
    assertNotSame(date, prop.getDateValue());
    assertArrayEquals(new Date[]{date}, prop.getDateValues());

    date = null;
    prop = new BasicMultiValues(date);
    assertEquals(Type.DATE, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(date, prop.getDateValue());
    assertArrayEquals(new Date[]{date}, prop.getDateValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(byte[])}.
   */
  @Test
  public void testBasicVariantsByteArray() {
    BasicMultiValues prop = null;
    byte[] bytes = new byte[]{1, 2, 3};

    prop = new BasicMultiValues(bytes);
    assertEquals(Type.BYTE_ARRAY, prop.getType());
    assertEquals(1, prop.getCount());
    assertArrayEquals(bytes, prop.getByteArrayValue());
    assertNotSame(bytes, prop.getByteArrayValue());
    assertArrayEquals(new byte[][]{bytes}, prop.getByteArrayValues());

    bytes = new byte[0];
    prop = new BasicMultiValues(bytes);
    assertEquals(Type.BYTE_ARRAY, prop.getType());
    assertEquals(1, prop.getCount());
    assertArrayEquals(bytes, prop.getByteArrayValue());
    assertNotSame(bytes, prop.getByteArrayValue());
    assertArrayEquals(new byte[][]{bytes}, prop.getByteArrayValues());

    bytes = null;
    prop = new BasicMultiValues(bytes);
    assertEquals(Type.BYTE_ARRAY, prop.getType());
    assertEquals(1, prop.getCount());
    assertArrayEquals(bytes, prop.getByteArrayValue());
    assertArrayEquals(new byte[][]{bytes}, prop.getByteArrayValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(Class)}.
   */
  @Test
  public void testBasicVariantsClass() {
    BasicMultiValues prop = null;
    Class<?> cls = String.class;

    prop = new BasicMultiValues(cls);
    assertEquals(Type.CLASS, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(cls, prop.getClassValue());
    assertSame(cls, prop.getClassValue());
    assertArrayEquals(new Class<?>[]{cls}, prop.getClassValues());

    cls = Boolean.class;
    prop = new BasicMultiValues(cls);
    assertEquals(Type.CLASS, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(cls, prop.getClassValue());
    assertSame(cls, prop.getClassValue());
    assertArrayEquals(new Class<?>[]{cls}, prop.getClassValues());

    cls = null;
    prop = new BasicMultiValues(cls);
    assertEquals(Type.CLASS, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(cls, prop.getClassValue());
    assertSame(cls, prop.getClassValue());
    assertArrayEquals(new Class<?>[]{cls}, prop.getClassValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(BigInteger)}.
   */
  @Test
  public void testBasicVariantsBigInteger() {
    BasicMultiValues prop = null;
    BigInteger value = null;

    value = BigInteger.ZERO;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_INTEGER, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigIntegerValue());
    assertArrayEquals(new BigInteger[]{value}, prop.getBigIntegerValues());

    value = BigInteger.TEN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_INTEGER, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigIntegerValue());
    assertArrayEquals(new BigInteger[]{value}, prop.getBigIntegerValues());

    value = null;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_INTEGER, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigIntegerValue());
    assertArrayEquals(new BigInteger[]{value}, prop.getBigIntegerValues());
  }

  /**
   * Test method for {@link BasicMultiValues#BasicVariants(BigDecimal)}.
   */
  @Test
  public void testBasicVariantsBigDecimal() {
    BasicMultiValues prop = null;
    BigDecimal value = null;

    value = BigDecimal.ZERO;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigDecimalValue());
    assertArrayEquals(new BigDecimal[]{value}, prop.getBigDecimalValues());

    value = BigDecimal.TEN;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigDecimalValue());
    assertArrayEquals(new BigDecimal[]{value}, prop.getBigDecimalValues());

    value = null;
    prop = new BasicMultiValues(value);
    assertEquals(Type.BIG_DECIMAL, prop.getType());
    assertEquals(1, prop.getCount());
    assertEquals(value, prop.getBigDecimalValue());
    assertArrayEquals(new BigDecimal[]{value}, prop.getBigDecimalValues());
  }

  /**
   * Test method for {@link BasicMultiValues#getType()} and {@link
   * BasicMultiValues#setType(Type)}.
   */
  @Test
  public void testGetSetType() {
    final BasicMultiValues prop = new BasicMultiValues("prop1");
    assertEquals(BasicMultiValues.DEFAULT_TYPE, prop.getType());

    prop.setType(Type.BIG_DECIMAL);
    assertEquals(Type.BIG_DECIMAL, prop.getType());

    try {
      prop.setType(null);
      fail("Should throw NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }


  /**
   * Test method for {@link BasicMultiValues#getCount()}.
   */
  @Test
  public void testSize() {
    final BasicMultiValues prop = new BasicMultiValues();
    assertEquals(0, prop.getCount());
    prop.addStringValue("value1");
    assertEquals(1, prop.getCount());
    prop.addStringValue("value2");
    assertEquals(2, prop.getCount());
    prop.addStringValue("value3");
    assertEquals(3, prop.getCount());
  }

  /**
   * Test method for {@link BasicMultiValues#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final BasicMultiValues prop = new BasicMultiValues();
    assertEquals(true, prop.isEmpty());
    prop.addStringValue("value1");
    assertEquals(false, prop.isEmpty());
    prop.addStringValue("value2");
    assertEquals(false, prop.isEmpty());
    prop.addStringValue("value3");
    assertEquals(false, prop.isEmpty());
  }

  /**
   * Test method for {@link BasicMultiValues#clear()}.
   */
  @Test
  public void testClear() {
    final BasicMultiValues prop = new BasicMultiValues();
    assertEquals(true, prop.isEmpty());
    prop.clear();
    assertEquals(true, prop.isEmpty());
    prop.addStringValue("value1");
    assertEquals(false, prop.isEmpty());
    prop.clear();
    assertEquals(true, prop.isEmpty());
    prop.addStringValue("value2");
    assertEquals(false, prop.isEmpty());
    prop.addStringValue("value3");
    assertEquals(false, prop.isEmpty());
    prop.clear();
    assertEquals(true, prop.isEmpty());
  }
}
