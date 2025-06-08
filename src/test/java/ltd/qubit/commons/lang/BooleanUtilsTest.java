////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for the Booleans class.
 *
 * @author Haixing Hu
 */
public class BooleanUtilsTest {

  @Test
  public void test_choose() {
    assertEquals("A", BooleanUtils.choose(Boolean.TRUE, "A", "B"));
    assertEquals("B", BooleanUtils.choose(Boolean.FALSE, "A", "B"));
    assertEquals("B", BooleanUtils.choose(null, "A", "B"));
  }

  @Test
  public void test_negate_Boolean() {
    assertSame(null, BooleanUtils.negate(null));
    assertSame(Boolean.TRUE, BooleanUtils.negate(Boolean.FALSE));
    assertSame(Boolean.FALSE, BooleanUtils.negate(Boolean.TRUE));
  }

  @Test
  public void test_isTrue_Boolean() {
    assertEquals(true, BooleanUtils.isTrue(Boolean.TRUE));
    assertEquals(false, BooleanUtils.isTrue(Boolean.FALSE));
    assertEquals(false, BooleanUtils.isTrue(null));
  }

  @Test
  public void test_isNotTrue_Boolean() {
    assertEquals(false, BooleanUtils.isNotTrue(Boolean.TRUE));
    assertEquals(true, BooleanUtils.isNotTrue(Boolean.FALSE));
    assertEquals(true, BooleanUtils.isNotTrue(null));
  }

  @Test
  public void test_isFalse_Boolean() {
    assertEquals(false, BooleanUtils.isFalse(Boolean.TRUE));
    assertEquals(true, BooleanUtils.isFalse(Boolean.FALSE));
    assertEquals(false, BooleanUtils.isFalse(null));
  }

  @Test
  public void test_isNotFalse_Boolean() {
    assertEquals(true, BooleanUtils.isNotFalse(Boolean.TRUE));
    assertEquals(false, BooleanUtils.isNotFalse(Boolean.FALSE));
    assertEquals(true, BooleanUtils.isNotFalse(null));
  }

  @Test
  public void test_toPrimitive_Boolean() {
    assertEquals(true, BooleanUtils.toPrimitive(Boolean.TRUE));
    assertEquals(false, BooleanUtils.toPrimitive(Boolean.FALSE));
    assertEquals(false, BooleanUtils.toPrimitive(null));
  }

  @Test
  public void test_toPrimitive_Boolean_boolean() {
    assertEquals(true, BooleanUtils.toPrimitive(Boolean.TRUE, true));
    assertEquals(true, BooleanUtils.toPrimitive(Boolean.TRUE, false));
    assertEquals(false, BooleanUtils.toPrimitive(Boolean.FALSE, true));
    assertEquals(false, BooleanUtils.toPrimitive(Boolean.FALSE, false));
    assertEquals(true, BooleanUtils.toPrimitive(null, true));
    assertEquals(false, BooleanUtils.toPrimitive(null, false));
  }

  @Test
  public void test_toChar_boolean() {
    assertEquals((char) 1, BooleanUtils.toChar(true));
    assertEquals((char) 0, BooleanUtils.toChar(false));
  }

  @Test
  public void test_toChar_Boolean() {
    assertEquals((char) 1, BooleanUtils.toChar(Boolean.TRUE));
    assertEquals((char) 0, BooleanUtils.toChar(Boolean.FALSE));
    assertEquals(CharUtils.DEFAULT, BooleanUtils.toChar(null));
  }

  @Test
  public void test_toChar_Boolean_char() {
    assertEquals((char) 1, BooleanUtils.toChar(Boolean.TRUE, 'X'));
    assertEquals((char) 0, BooleanUtils.toChar(Boolean.FALSE, 'X'));
    assertEquals('X', BooleanUtils.toChar(null, 'X'));
  }

  @Test
  public void test_toCharObject_boolean() {
    assertEquals(Character.valueOf((char) 1), BooleanUtils.toCharObject(true));
    assertEquals(Character.valueOf((char) 0), BooleanUtils.toCharObject(false));
  }

  @Test
  public void test_toCharObject_Boolean() {
    assertEquals(Character.valueOf((char) 1), BooleanUtils.toCharObject(Boolean.TRUE));
    assertEquals(Character.valueOf((char) 0), BooleanUtils.toCharObject(Boolean.FALSE));
    assertNull(BooleanUtils.toCharObject(null));
  }

  @Test
  public void test_toCharObject_Boolean_Character() {
    assertEquals(Character.valueOf((char) 1), BooleanUtils.toCharObject(Boolean.TRUE, 'X'));
    assertEquals(Character.valueOf((char) 0), BooleanUtils.toCharObject(Boolean.FALSE, 'X'));
    assertEquals(Character.valueOf('X'), BooleanUtils.toCharObject(null, 'X'));
    assertNull(BooleanUtils.toCharObject(null, null));
  }

  @Test
  public void test_toByte_boolean() {
    assertEquals((byte) 1, BooleanUtils.toByte(true));
    assertEquals((byte) 0, BooleanUtils.toByte(false));
  }

  @Test
  public void test_toByte_Boolean() {
    assertEquals((byte) 1, BooleanUtils.toByte(Boolean.TRUE));
    assertEquals((byte) 0, BooleanUtils.toByte(Boolean.FALSE));
    assertEquals((byte) 0, BooleanUtils.toByte(null));
  }

  @Test
  public void test_toByte_Boolean_byte() {
    assertEquals((byte) 1, BooleanUtils.toByte(Boolean.TRUE, (byte) 5));
    assertEquals((byte) 0, BooleanUtils.toByte(Boolean.FALSE, (byte) 5));
    assertEquals((byte) 5, BooleanUtils.toByte(null, (byte) 5));
  }

  @Test
  public void test_toByteObject_boolean() {
    assertEquals(Byte.valueOf((byte) 1), BooleanUtils.toByteObject(true));
    assertEquals(Byte.valueOf((byte) 0), BooleanUtils.toByteObject(false));
  }

  @Test
  public void test_toByteObject_Boolean() {
    assertEquals(Byte.valueOf((byte) 1), BooleanUtils.toByteObject(Boolean.TRUE));
    assertEquals(Byte.valueOf((byte) 0), BooleanUtils.toByteObject(Boolean.FALSE));
    assertNull(BooleanUtils.toByteObject(null));
  }

  @Test
  public void test_toByteObject_Boolean_Byte() {
    assertEquals(Byte.valueOf((byte) 1), BooleanUtils.toByteObject(Boolean.TRUE, (byte) 5));
    assertEquals(Byte.valueOf((byte) 0), BooleanUtils.toByteObject(Boolean.FALSE, (byte) 5));
    assertEquals(Byte.valueOf((byte) 5), BooleanUtils.toByteObject(null, (byte) 5));
    assertNull(BooleanUtils.toByteObject(null, null));
  }

  @Test
  public void test_toShort_boolean() {
    assertEquals((short) 1, BooleanUtils.toShort(true));
    assertEquals((short) 0, BooleanUtils.toShort(false));
  }

  @Test
  public void test_toShort_Boolean() {
    assertEquals((short) 1, BooleanUtils.toShort(Boolean.TRUE));
    assertEquals((short) 0, BooleanUtils.toShort(Boolean.FALSE));
    assertEquals((short) 0, BooleanUtils.toShort(null));
  }

  @Test
  public void test_toShort_Boolean_short() {
    assertEquals((short) 1, BooleanUtils.toShort(Boolean.TRUE, (short) 5));
    assertEquals((short) 0, BooleanUtils.toShort(Boolean.FALSE, (short) 5));
    assertEquals((short) 5, BooleanUtils.toShort(null, (short) 5));
  }

  @Test
  public void test_toShortObject_boolean() {
    assertEquals(Short.valueOf((short) 1), BooleanUtils.toShortObject(true));
    assertEquals(Short.valueOf((short) 0), BooleanUtils.toShortObject(false));
  }

  @Test
  public void test_toShortObject_Boolean() {
    assertEquals(Short.valueOf((short) 1), BooleanUtils.toShortObject(Boolean.TRUE));
    assertEquals(Short.valueOf((short) 0), BooleanUtils.toShortObject(Boolean.FALSE));
    assertNull(BooleanUtils.toShortObject(null));
  }

  @Test
  public void test_toShortObject_Boolean_Short() {
    assertEquals(Short.valueOf((short) 1), BooleanUtils.toShortObject(Boolean.TRUE, (short) 5));
    assertEquals(Short.valueOf((short) 0), BooleanUtils.toShortObject(Boolean.FALSE, (short) 5));
    assertEquals(Short.valueOf((short) 5), BooleanUtils.toShortObject(null, (short) 5));
    assertNull(BooleanUtils.toShortObject(null, null));
  }

  @Test
  public void test_toInt_boolean() {
    assertEquals(1, BooleanUtils.toInt(true));
    assertEquals(0, BooleanUtils.toInt(false));
  }

  @Test
  public void test_toInt_Boolean() {
    assertEquals(1, BooleanUtils.toInt(Boolean.TRUE));
    assertEquals(0, BooleanUtils.toInt(Boolean.FALSE));
    assertEquals(0, BooleanUtils.toInt(null));
  }

  @Test
  public void test_toInt_Boolean_int() {
    assertEquals(1, BooleanUtils.toInt(Boolean.TRUE, 5));
    assertEquals(0, BooleanUtils.toInt(Boolean.FALSE, 5));
    assertEquals(5, BooleanUtils.toInt(null, 5));
  }

  @Test
  public void test_toIntObject_boolean() {
    assertEquals(1, BooleanUtils.toIntObject(true));
    assertEquals(0, BooleanUtils.toIntObject(false));
  }

  @Test
  public void test_toIntObject_Boolean() {
    assertEquals(1, BooleanUtils.toIntObject(Boolean.TRUE));
    assertEquals(0, BooleanUtils.toIntObject(Boolean.FALSE));
    assertEquals(null, BooleanUtils.toIntObject(null));
  }

  @Test
  public void test_toIntObject_Boolean_Integer() {
    assertEquals(1, BooleanUtils.toIntObject(Boolean.TRUE, 5));
    assertEquals(0, BooleanUtils.toIntObject(Boolean.FALSE, 5));
    assertEquals(5, BooleanUtils.toIntObject(null, 5));
    assertNull(BooleanUtils.toIntObject(null, null));
  }

  @Test
  public void test_toLong_boolean() {
    assertEquals(1L, BooleanUtils.toLong(true));
    assertEquals(0L, BooleanUtils.toLong(false));
  }

  @Test
  public void test_toLong_Boolean() {
    assertEquals(1L, BooleanUtils.toLong(Boolean.TRUE));
    assertEquals(0L, BooleanUtils.toLong(Boolean.FALSE));
    assertEquals(0L, BooleanUtils.toLong(null));
  }

  @Test
  public void test_toLong_Boolean_long() {
    assertEquals(1L, BooleanUtils.toLong(Boolean.TRUE, 5L));
    assertEquals(0L, BooleanUtils.toLong(Boolean.FALSE, 5L));
    assertEquals(5L, BooleanUtils.toLong(null, 5L));
  }

  @Test
  public void test_toLongObject_boolean() {
    assertEquals(1L, BooleanUtils.toLongObject(true));
    assertEquals(0L, BooleanUtils.toLongObject(false));
  }

  @Test
  public void test_toLongObject_Boolean() {
    assertEquals(1L, BooleanUtils.toLongObject(Boolean.TRUE));
    assertEquals(0L, BooleanUtils.toLongObject(Boolean.FALSE));
    assertNull(BooleanUtils.toLongObject(null));
  }

  @Test
  public void test_toLongObject_Boolean_Long() {
    assertEquals(1L, BooleanUtils.toLongObject(Boolean.TRUE, 5L));
    assertEquals(0L, BooleanUtils.toLongObject(Boolean.FALSE, 5L));
    assertEquals(5L, BooleanUtils.toLongObject(null, 5L));
    assertNull(BooleanUtils.toLongObject(null, null));
  }

  @Test
  public void test_toFloat_boolean() {
    assertEquals(1.0f, BooleanUtils.toFloat(true));
    assertEquals(0.0f, BooleanUtils.toFloat(false));
  }

  @Test
  public void test_toFloat_Boolean() {
    assertEquals(1.0f, BooleanUtils.toFloat(Boolean.TRUE));
    assertEquals(0.0f, BooleanUtils.toFloat(Boolean.FALSE));
    assertEquals(0.0f, BooleanUtils.toFloat(null));
  }

  @Test
  public void test_toFloat_Boolean_float() {
    assertEquals(1.0f, BooleanUtils.toFloat(Boolean.TRUE, 5.0f));
    assertEquals(0.0f, BooleanUtils.toFloat(Boolean.FALSE, 5.0f));
    assertEquals(5.0f, BooleanUtils.toFloat(null, 5.0f));
  }

  @Test
  public void test_toFloatObject_boolean() {
    assertEquals(1.0f, BooleanUtils.toFloatObject(true));
    assertEquals(0.0f, BooleanUtils.toFloatObject(false));
  }

  @Test
  public void test_toFloatObject_Boolean() {
    assertEquals(1.0f, BooleanUtils.toFloatObject(Boolean.TRUE));
    assertEquals(0.0f, BooleanUtils.toFloatObject(Boolean.FALSE));
    assertNull(BooleanUtils.toFloatObject(null));
  }

  @Test
  public void test_toFloatObject_Boolean_Float() {
    assertEquals(1.0f, BooleanUtils.toFloatObject(Boolean.TRUE, 5.0f));
    assertEquals(0.0f, BooleanUtils.toFloatObject(Boolean.FALSE, 5.0f));
    assertEquals(5.0f, BooleanUtils.toFloatObject(null, 5.0f));
    assertNull(BooleanUtils.toFloatObject(null, null));
  }

  @Test
  public void test_toDouble_boolean() {
    assertEquals(1.0, BooleanUtils.toDouble(true));
    assertEquals(0.0, BooleanUtils.toDouble(false));
  }

  @Test
  public void test_toDouble_Boolean() {
    assertEquals(1.0, BooleanUtils.toDouble(Boolean.TRUE));
    assertEquals(0.0, BooleanUtils.toDouble(Boolean.FALSE));
    assertEquals(0.0, BooleanUtils.toDouble(null));
  }

  @Test
  public void test_toDouble_Boolean_double() {
    assertEquals(1.0, BooleanUtils.toDouble(Boolean.TRUE, 5.0));
    assertEquals(0.0, BooleanUtils.toDouble(Boolean.FALSE, 5.0));
    assertEquals(5.0, BooleanUtils.toDouble(null, 5.0));
  }

  @Test
  public void test_toDoubleObject_boolean() {
    assertEquals(1.0, BooleanUtils.toDoubleObject(true));
    assertEquals(0.0, BooleanUtils.toDoubleObject(false));
  }

  @Test
  public void test_toDoubleObject_Boolean() {
    assertEquals(1.0, BooleanUtils.toDoubleObject(Boolean.TRUE));
    assertEquals(0.0, BooleanUtils.toDoubleObject(Boolean.FALSE));
    assertNull(BooleanUtils.toDoubleObject(null));
  }

  @Test
  public void test_toDoubleObject_Boolean_Double() {
    assertEquals(1.0, BooleanUtils.toDoubleObject(Boolean.TRUE, 5.0));
    assertEquals(0.0, BooleanUtils.toDoubleObject(Boolean.FALSE, 5.0));
    assertEquals(5.0, BooleanUtils.toDoubleObject(null, 5.0));
    assertNull(BooleanUtils.toDoubleObject(null, null));
  }

  @Test
  public void test_toDate_boolean() {
    assertEquals(new Date(1), BooleanUtils.toDate(true));
    assertEquals(new Date(0), BooleanUtils.toDate(false));
  }

  @Test
  public void test_toDate_Boolean() {
    assertEquals(new Date(1), BooleanUtils.toDate(Boolean.TRUE));
    assertEquals(new Date(0), BooleanUtils.toDate(Boolean.FALSE));
    assertNull(BooleanUtils.toDate(null));
  }

  @Test
  public void test_toDate_Boolean_Date() {
    final Date defaultDate = new Date(1000);
    assertEquals(new Date(1), BooleanUtils.toDate(Boolean.TRUE, defaultDate));
    assertEquals(new Date(0), BooleanUtils.toDate(Boolean.FALSE, defaultDate));
    assertEquals(defaultDate, BooleanUtils.toDate(null, defaultDate));
    assertNull(BooleanUtils.toDate(null, null));
  }

  @Test
  public void test_toByteArray_boolean() {
    final byte[] trueBytes = {(byte) 1};
    final byte[] falseBytes = {(byte) 0};
    assertArrayEquals(trueBytes, BooleanUtils.toByteArray(true));
    assertArrayEquals(falseBytes, BooleanUtils.toByteArray(false));
  }

  @Test
  public void test_toByteArray_Boolean() {
    final byte[] trueBytes = {(byte) 1};
    final byte[] falseBytes = {(byte) 0};
    assertArrayEquals(trueBytes, BooleanUtils.toByteArray(Boolean.TRUE));
    assertArrayEquals(falseBytes, BooleanUtils.toByteArray(Boolean.FALSE));
    assertNull(BooleanUtils.toByteArray(null));
  }

  @Test
  public void test_toByteArray_Boolean_byteArray() {
    final byte[] trueBytes = {(byte) 1};
    final byte[] falseBytes = {(byte) 0};
    final byte[] defaultBytes = {(byte) 5};
    assertArrayEquals(trueBytes, BooleanUtils.toByteArray(Boolean.TRUE, defaultBytes));
    assertArrayEquals(falseBytes, BooleanUtils.toByteArray(Boolean.FALSE, defaultBytes));
    assertArrayEquals(defaultBytes, BooleanUtils.toByteArray(null, defaultBytes));
    assertNull(BooleanUtils.toByteArray(null, null));
  }

  @Test
  public void test_toClass_boolean() {
    assertEquals(boolean.class, BooleanUtils.toClass(true));
    assertEquals(boolean.class, BooleanUtils.toClass(false));
  }

  @Test
  public void test_toClass_Boolean() {
    assertEquals(Boolean.class, BooleanUtils.toClass(Boolean.TRUE));
    assertEquals(Boolean.class, BooleanUtils.toClass(Boolean.FALSE));
    assertNull(BooleanUtils.toClass(null));
  }

  @Test
  public void test_toClass_Boolean_Class() {
    assertEquals(Boolean.class, BooleanUtils.toClass(Boolean.TRUE, String.class));
    assertEquals(Boolean.class, BooleanUtils.toClass(Boolean.FALSE, String.class));
    assertEquals(String.class, BooleanUtils.toClass(null, String.class));
    assertNull(BooleanUtils.toClass(null, null));
  }

  @Test
  public void test_toBigInteger_boolean() {
    assertEquals(BigInteger.ONE, BooleanUtils.toBigInteger(true));
    assertEquals(BigInteger.ZERO, BooleanUtils.toBigInteger(false));
  }

  @Test
  public void test_toBigInteger_Boolean() {
    assertEquals(BigInteger.ONE, BooleanUtils.toBigInteger(Boolean.TRUE));
    assertEquals(BigInteger.ZERO, BooleanUtils.toBigInteger(Boolean.FALSE));
    assertNull(BooleanUtils.toBigInteger(null));
  }

  @Test
  public void test_toBigInteger_Boolean_BigInteger() {
    final BigInteger defaultValue = BigInteger.valueOf(5);
    assertEquals(BigInteger.ONE, BooleanUtils.toBigInteger(Boolean.TRUE, defaultValue));
    assertEquals(BigInteger.ZERO, BooleanUtils.toBigInteger(Boolean.FALSE, defaultValue));
    assertEquals(defaultValue, BooleanUtils.toBigInteger(null, defaultValue));
    assertNull(BooleanUtils.toBigInteger(null, null));
  }

  @Test
  public void test_toBigDecimal_boolean() {
    assertEquals(BigDecimal.ONE, BooleanUtils.toBigDecimal(true));
    assertEquals(BigDecimal.ZERO, BooleanUtils.toBigDecimal(false));
  }

  @Test
  public void test_toBigDecimal_Boolean() {
    assertEquals(BigDecimal.ONE, BooleanUtils.toBigDecimal(Boolean.TRUE));
    assertEquals(BigDecimal.ZERO, BooleanUtils.toBigDecimal(Boolean.FALSE));
    assertNull(BooleanUtils.toBigDecimal(null));
  }

  @Test
  public void test_toBigDecimal_Boolean_BigDecimal() {
    final BigDecimal defaultValue = BigDecimal.valueOf(5);
    assertEquals(BigDecimal.ONE, BooleanUtils.toBigDecimal(Boolean.TRUE, defaultValue));
    assertEquals(BigDecimal.ZERO, BooleanUtils.toBigDecimal(Boolean.FALSE, defaultValue));
    assertEquals(defaultValue, BooleanUtils.toBigDecimal(null, defaultValue));
    assertNull(BooleanUtils.toBigDecimal(null, null));
  }

  @Test
  public void test_isComparable() {
    assertTrue(BooleanUtils.isComparable(Boolean.class));
    assertTrue(BooleanUtils.isComparable(boolean.class));
    assertTrue(BooleanUtils.isComparable(Boolean.TYPE));
  }

  @Test
  public void test_toString_boolean() {
    assertEquals("true", BooleanUtils.toString(true));
    assertEquals("false", BooleanUtils.toString(false));
  }

  @Test
  public void test_toString_Boolean() {
    assertEquals(null, BooleanUtils.toString(null));
    assertEquals("true", BooleanUtils.toString(Boolean.TRUE));
    assertEquals("false", BooleanUtils.toString(Boolean.FALSE));
  }

  @Test
  public void test_toString_Boolean_String() {
    assertEquals("default", BooleanUtils.toString(null, "default"));
    assertEquals("true", BooleanUtils.toString(Boolean.TRUE, "default"));
    assertEquals("false", BooleanUtils.toString(Boolean.FALSE, "default"));
  }

  @Test
  public void test_toStringOnOff_boolean() {
    assertEquals("on", BooleanUtils.toStringOnOff(true));
    assertEquals("off", BooleanUtils.toStringOnOff(false));
  }

  @Test
  public void test_toStringOnOff_Boolean() {
    assertEquals(null, BooleanUtils.toStringOnOff(null));
    assertEquals("on", BooleanUtils.toStringOnOff(Boolean.TRUE));
    assertEquals("off", BooleanUtils.toStringOnOff(Boolean.FALSE));
  }

  @Test
  public void test_toStringYesNo_boolean() {
    assertEquals("yes", BooleanUtils.toStringYesNo(true));
    assertEquals("no", BooleanUtils.toStringYesNo(false));
  }

  @Test
  public void test_toStringYesNo_Boolean() {
    assertEquals(null, BooleanUtils.toStringYesNo(null));
    assertEquals("yes", BooleanUtils.toStringYesNo(Boolean.TRUE));
    assertEquals("no", BooleanUtils.toStringYesNo(Boolean.FALSE));
  }

  @Test
  public void test_toString_boolean_String_String_String() {
    assertEquals("Y", BooleanUtils.toString(true, "Y", "N"));
    assertEquals("N", BooleanUtils.toString(false, "Y", "N"));
  }

  @Test
  public void test_toString_Boolean_String_String_String() {
    assertEquals("U", BooleanUtils.toString(null, "Y", "N", "U"));
    assertEquals("Y", BooleanUtils.toString(Boolean.TRUE, "Y", "N", "U"));
    assertEquals("N", BooleanUtils.toString(Boolean.FALSE, "Y", "N", "U"));
  }

  @Test
  public void testAnd_primitive_nullInput() {
    final boolean[] b = null;
    try {
      BooleanUtils.and(b);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testAnd_primitive_emptyInput() {
    assertEquals(true, BooleanUtils.and(new boolean[]{}));
  }

  @Test
  public void testAnd_primitive_validInput() {

    boolean[] array = null;

    array = new boolean[]{true, true};
    assertEquals(true, BooleanUtils.and(array));

    array = new boolean[]{false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, true, true};
    assertEquals(true, BooleanUtils.and(array));

    array = new boolean[]{false, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, true, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, false, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, true, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, false, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, true, true, true};
    assertEquals(true, BooleanUtils.and(array));

    array = new boolean[]{false, false, false, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, true, false, true};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{true, false, true, false};
    assertEquals(false, BooleanUtils.and(array));

    array = new boolean[]{false, true, true, false};
    assertEquals(false, BooleanUtils.and(array));
  }

  @Test
  public void testAnd_object_nullInput() {
    final Boolean[] b1 = null;
    try {
      BooleanUtils.and(b1);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }

    final List<Boolean> b2 = null;
    try {
      BooleanUtils.and(b2);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testAnd_object_emptyInput() {
    assertEquals(Boolean.TRUE, BooleanUtils.and(new Boolean[]{}));
    assertEquals(Boolean.TRUE, BooleanUtils.and(new ArrayList<>()));
  }

  @Test
  public void testAnd_object_nullElementInput() {
    Boolean[] array = null;

    array = new Boolean[]{null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[]{null, Boolean.FALSE};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, null};
    assertNull(BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, null};
    assertNull(BooleanUtils.and(array));

  }

  @Test
  public void testAnd_object_validInput() {
    Boolean[] array = null;

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE,
        Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
        Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
        Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.and(array));
  }

  @Test
  public void testOr_primitive_nullInput() {
    final boolean[] b = null;
    try {
      BooleanUtils.or(b);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testOr_primitive_emptyInput() {
    assertEquals(false, BooleanUtils.or(new boolean[]{}));
  }

  @Test
  public void testOr_primitive_validInput() {

    boolean[] array = null;

    array = new boolean[]{true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, false};
    assertEquals(false, BooleanUtils.or(array));

    array = new boolean[]{true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{true, false, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{true, true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, false, false};
    assertEquals(false, BooleanUtils.or(array));

    array = new boolean[]{true, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{true, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, false, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, true, false, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{true, false, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{true, true, true, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, false, false, false};
    assertEquals(false, BooleanUtils.or(array));

    array = new boolean[]{true, true, false, true};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{true, false, true, false};
    assertEquals(true, BooleanUtils.or(array));

    array = new boolean[]{false, true, true, false};
    assertEquals(true, BooleanUtils.or(array));
  }

  @Test
  public void testOr_object_nullInput() {
    final Boolean[] b = null;
    try {
      BooleanUtils.or(b);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }

    final List<Boolean> list = null;
    try {
      BooleanUtils.or(list);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testOr_object_emptyInput() {
    assertEquals(Boolean.FALSE, BooleanUtils.or());
    assertEquals(Boolean.FALSE, BooleanUtils.or(new ArrayList<>()));
  }

  @Test
  public void testOr_object_nullElementInput() {
    Boolean[] array = null;

    array = new Boolean[]{null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[]{null, Boolean.FALSE};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, null};
    assertNull(BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, null};
    assertNull(BooleanUtils.or(array));
  }

  @Test
  public void testOr_object_validInput() {
    Boolean[] array = null;

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE,
        Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE,
        Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
        Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
        Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.or(array));
  }

  @Test
  public void testXor_primitive_nullInput() {
    final boolean[] b = null;
    try {
      BooleanUtils.xor(b);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testXor_primitive_emptyInput() {
    assertEquals(false, BooleanUtils.xor(new boolean[]{}));
  }

  @Test
  public void testXor_primitive_validInput() {

    boolean[] array = null;

    array = new boolean[]{true, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{false, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{true, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{false, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{false, false, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{false, true, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{true, false, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{true, true, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{false, false, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{true, true, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{true, false, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{false, true, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{false, false, true, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{false, true, false, false};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{true, false, false, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{true, true, true, true};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{false, false, false, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{true, true, false, true};
    assertEquals(true, BooleanUtils.xor(array));

    array = new boolean[]{true, false, true, false};
    assertEquals(false, BooleanUtils.xor(array));

    array = new boolean[]{false, true, true, false};
    assertEquals(false, BooleanUtils.xor(array));
  }

  @Test
  public void testXor_object_nullInput() {
    final Boolean[] b = null;
    try {
      BooleanUtils.xor(b);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }

    final List<Boolean> list = null;
    try {
      BooleanUtils.xor(list);
      fail("Exception was not thrown for null input.");
    } catch (final NullPointerException ex) {
      //  pass
    }
  }

  @Test
  public void testXor_object_emptyInput() {
    assertEquals(Boolean.FALSE, BooleanUtils.xor(new Boolean[]{}));
    assertEquals(Boolean.FALSE, BooleanUtils.xor(new ArrayList<>()));
  }

  @Test
  public void testXor_object_nullElementInput() {
    Boolean[] array = null;

    array = new Boolean[]{null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[]{null, Boolean.FALSE};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, null};
    assertNull(BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, null};
    assertNull(BooleanUtils.xor(array));
  }

  @Test
  public void testXor_object_validInput() {
    Boolean[] array = null;

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE,
        Boolean.FALSE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.FALSE,
        Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
        Boolean.TRUE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.TRUE, Boolean.FALSE,
        Boolean.TRUE};
    assertEquals(Boolean.TRUE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.TRUE, Boolean.FALSE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));

    array = new Boolean[]{Boolean.FALSE, Boolean.TRUE, Boolean.TRUE,
        Boolean.FALSE};
    assertEquals(Boolean.FALSE, BooleanUtils.xor(array));
  }
}