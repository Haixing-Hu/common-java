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
import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link BigIntegerUtils}类。
 *
 * @author Haixing Hu
 */
public class BigIntegerUtilsTest {

  @Test
  public void testToBoolean() {
    assertTrue(BigIntegerUtils.toBoolean(new BigInteger("1")));
    assertTrue(BigIntegerUtils.toBoolean(new BigInteger("-1")));
    assertFalse(BigIntegerUtils.toBoolean(new BigInteger("0")));
    assertFalse(BigIntegerUtils.toBoolean(null));
  }

  @Test
  public void testToBooleanDefaultValue() {
    assertTrue(BigIntegerUtils.toBoolean(new BigInteger("1"), true));
    assertTrue(BigIntegerUtils.toBoolean(new BigInteger("-1"), false));
    assertFalse(BigIntegerUtils.toBoolean(new BigInteger("0"), true));
    assertTrue(BigIntegerUtils.toBoolean(null, true));
    assertFalse(BigIntegerUtils.toBoolean(null, false));
  }

  @Test
  public void testToBooleanObject() {
    assertTrue(BigIntegerUtils.toBooleanObject(new BigInteger("1")));
    assertTrue(BigIntegerUtils.toBooleanObject(new BigInteger("-1")));
    assertFalse(BigIntegerUtils.toBooleanObject(new BigInteger("0")));
    assertNull(BigIntegerUtils.toBooleanObject(null));
  }

  @Test
  public void testToBooleanObjectDefaultValue() {
    assertTrue(BigIntegerUtils.toBooleanObject(new BigInteger("1"), Boolean.TRUE));
    assertTrue(BigIntegerUtils.toBooleanObject(new BigInteger("-1"), Boolean.FALSE));
    assertFalse(BigIntegerUtils.toBooleanObject(new BigInteger("0"), Boolean.TRUE));
    assertTrue(BigIntegerUtils.toBooleanObject(null, Boolean.TRUE));
    assertFalse(BigIntegerUtils.toBooleanObject(null, Boolean.FALSE));
    assertNull(BigIntegerUtils.toBooleanObject(null, null));
  }

  @Test
  public void testToChar() {
    assertEquals('あ', BigIntegerUtils.toChar(new BigInteger("12354")));
    assertEquals('A', BigIntegerUtils.toChar(new BigInteger("65")));
    assertEquals('\0', BigIntegerUtils.toChar(null));
  }

  @Test
  public void testToCharDefaultValue() {
    assertEquals('A', BigIntegerUtils.toChar(new BigInteger("65"), 'B'));
    assertEquals('B', BigIntegerUtils.toChar(null, 'B'));
  }

  @Test
  public void testToCharObject() {
    assertEquals(Character.valueOf('A'), BigIntegerUtils.toCharObject(new BigInteger("65")));
    assertNull(BigIntegerUtils.toCharObject(null));
  }

  @Test
  public void testToCharObjectDefaultValue() {
    assertEquals(Character.valueOf('A'), BigIntegerUtils.toCharObject(new BigInteger("65"), 'B'));
    assertEquals(Character.valueOf('B'), BigIntegerUtils.toCharObject(null, 'B'));
    assertNull(BigIntegerUtils.toCharObject(null, null));
  }

  @Test
  public void testToByte() {
    assertEquals((byte)127, BigIntegerUtils.toByte(new BigInteger("127")));
    assertEquals((byte)-128, BigIntegerUtils.toByte(new BigInteger("-128")));
    // 溢出情况测试
    assertEquals((byte)-1, BigIntegerUtils.toByte(new BigInteger("255")));
    assertEquals((byte)0, BigIntegerUtils.toByte(null));
  }

  @Test
  public void testToByteDefaultValue() {
    assertEquals((byte)127, BigIntegerUtils.toByte(new BigInteger("127"), (byte)10));
    assertEquals((byte)10, BigIntegerUtils.toByte(null, (byte)10));
  }

  @Test
  public void testToByteObject() {
    assertEquals(Byte.valueOf((byte)127), BigIntegerUtils.toByteObject(new BigInteger("127")));
    assertNull(BigIntegerUtils.toByteObject(null));
  }

  @Test
  public void testToByteObjectDefaultValue() {
    assertEquals(Byte.valueOf((byte)127), BigIntegerUtils.toByteObject(new BigInteger("127"), (byte)10));
    assertEquals(Byte.valueOf((byte)10), BigIntegerUtils.toByteObject(null, (byte)10));
    assertNull(BigIntegerUtils.toByteObject(null, null));
  }

  @Test
  public void testToShort() {
    assertEquals((short)32767, BigIntegerUtils.toShort(new BigInteger("32767")));
    assertEquals((short)-32768, BigIntegerUtils.toShort(new BigInteger("-32768")));
    // 溢出情况测试
    assertEquals((short)-1, BigIntegerUtils.toShort(new BigInteger("65535")));
    assertEquals((short)0, BigIntegerUtils.toShort(null));
  }

  @Test
  public void testToShortDefaultValue() {
    assertEquals((short)32767, BigIntegerUtils.toShort(new BigInteger("32767"), (short)100));
    assertEquals((short)100, BigIntegerUtils.toShort(null, (short)100));
  }

  @Test
  public void testToShortObject() {
    assertEquals(Short.valueOf((short)32767), BigIntegerUtils.toShortObject(new BigInteger("32767")));
    assertNull(BigIntegerUtils.toShortObject(null));
  }

  @Test
  public void testToShortObjectDefaultValue() {
    assertEquals(Short.valueOf((short)32767), BigIntegerUtils.toShortObject(new BigInteger("32767"), (short)100));
    assertEquals(Short.valueOf((short)100), BigIntegerUtils.toShortObject(null, (short)100));
    assertNull(BigIntegerUtils.toShortObject(null, null));
  }

  @Test
  public void testToInt() {
    assertEquals(2147483647, BigIntegerUtils.toInt(new BigInteger("2147483647")));
    assertEquals(-2147483648, BigIntegerUtils.toInt(new BigInteger("-2147483648")));
    // 溢出情况测试
    assertEquals(-1, BigIntegerUtils.toInt(new BigInteger("4294967295")));
    assertEquals(0, BigIntegerUtils.toInt(null));
  }

  @Test
  public void testToIntDefaultValue() {
    assertEquals(2147483647, BigIntegerUtils.toInt(new BigInteger("2147483647"), 100));
    assertEquals(100, BigIntegerUtils.toInt(null, 100));
  }

  @Test
  public void testToIntObject() {
    assertEquals(Integer.valueOf(2147483647), BigIntegerUtils.toIntObject(new BigInteger("2147483647")));
    assertNull(BigIntegerUtils.toIntObject(null));
  }

  @Test
  public void testToIntObjectDefaultValue() {
    assertEquals(Integer.valueOf(2147483647), BigIntegerUtils.toIntObject(new BigInteger("2147483647"), 100));
    assertEquals(Integer.valueOf(100), BigIntegerUtils.toIntObject(null, 100));
    assertNull(BigIntegerUtils.toIntObject(null, null));
  }

  @Test
  public void testToLong() {
    assertEquals(9223372036854775807L, BigIntegerUtils.toLong(new BigInteger("9223372036854775807")));
    assertEquals(-9223372036854775808L, BigIntegerUtils.toLong(new BigInteger("-9223372036854775808")));
    // 超出long范围的大数值
    assertEquals(-1L, BigIntegerUtils.toLong(new BigInteger("18446744073709551615")));
    assertEquals(0L, BigIntegerUtils.toLong(null));
  }

  @Test
  public void testToLongDefaultValue() {
    assertEquals(9223372036854775807L, BigIntegerUtils.toLong(new BigInteger("9223372036854775807"), 100L));
    assertEquals(100L, BigIntegerUtils.toLong(null, 100L));
  }

  @Test
  public void testToLongObject() {
    assertEquals(Long.valueOf(9223372036854775807L), BigIntegerUtils.toLongObject(new BigInteger("9223372036854775807")));
    assertNull(BigIntegerUtils.toLongObject(null));
  }

  @Test
  public void testToLongObjectDefaultValue() {
    assertEquals(Long.valueOf(9223372036854775807L), BigIntegerUtils.toLongObject(new BigInteger("9223372036854775807"), 100L));
    assertEquals(Long.valueOf(100L), BigIntegerUtils.toLongObject(null, 100L));
    assertNull(BigIntegerUtils.toLongObject(null, null));
  }

  @Test
  public void testToFloat() {
    assertEquals(3.0f, BigIntegerUtils.toFloat(new BigInteger("3")), 0.0000001f);
    assertEquals(-3.0f, BigIntegerUtils.toFloat(new BigInteger("-3")), 0.0000001f);
    // 测试大数值
    assertEquals(1.23456789E10f, BigIntegerUtils.toFloat(new BigInteger("12345678900")), 1.0f);
    assertEquals(0.0f, BigIntegerUtils.toFloat(null), 0.0f);
  }

  @Test
  public void testToFloatDefaultValue() {
    assertEquals(3.0f, BigIntegerUtils.toFloat(new BigInteger("3"), 2.718f), 0.0000001f);
    assertEquals(2.718f, BigIntegerUtils.toFloat(null, 2.718f), 0.0f);
  }

  @Test
  public void testToFloatObject() {
    assertEquals(Float.valueOf(3.0f), BigIntegerUtils.toFloatObject(new BigInteger("3")), 0.0000001f);
    assertNull(BigIntegerUtils.toFloatObject(null));
  }

  @Test
  public void testToFloatObjectDefaultValue() {
    assertEquals(Float.valueOf(3.0f), BigIntegerUtils.toFloatObject(new BigInteger("3"), 2.718f), 0.0000001f);
    assertEquals(Float.valueOf(2.718f), BigIntegerUtils.toFloatObject(null, 2.718f));
    assertNull(BigIntegerUtils.toFloatObject(null, null));
  }

  @Test
  public void testToDouble() {
    assertEquals(3.0, BigIntegerUtils.toDouble(new BigInteger("3")), 0.0000000000000001);
    assertEquals(-3.0, BigIntegerUtils.toDouble(new BigInteger("-3")), 0.0000000000000001);
    // 测试大数值
    assertEquals(1.2345678900987654E20, BigIntegerUtils.toDouble(new BigInteger("123456789009876543210")), 1.0E15);
    assertEquals(0.0, BigIntegerUtils.toDouble(null), 0.0);
  }

  @Test
  public void testToDoubleDefaultValue() {
    assertEquals(3.0, BigIntegerUtils.toDouble(new BigInteger("3"), 2.718281828459045), 0.0000000000000001);
    assertEquals(2.718281828459045, BigIntegerUtils.toDouble(null, 2.718281828459045), 0.0);
  }

  @Test
  public void testToDoubleObject() {
    assertEquals(Double.valueOf(3.0), BigIntegerUtils.toDoubleObject(new BigInteger("3")), 0.0000000000000001);
    assertNull(BigIntegerUtils.toDoubleObject(null));
  }

  @Test
  public void testToDoubleObjectDefaultValue() {
    assertEquals(Double.valueOf(3.0), BigIntegerUtils.toDoubleObject(new BigInteger("3"), 2.718281828459045), 0.0000000000000001);
    assertEquals(Double.valueOf(2.718281828459045), BigIntegerUtils.toDoubleObject(null, 2.718281828459045));
    assertNull(BigIntegerUtils.toDoubleObject(null, null));
  }

  @Test
  public void testToString() {
    assertEquals("123456789", BigIntegerUtils.toString(new BigInteger("123456789")));
    assertEquals("-123456789", BigIntegerUtils.toString(new BigInteger("-123456789")));
    assertNull(BigIntegerUtils.toString(null));
  }

  @Test
  public void testToStringDefaultValue() {
    assertEquals("123456789", BigIntegerUtils.toString(new BigInteger("123456789"), "default"));
    assertEquals("default", BigIntegerUtils.toString(null, "default"));
    assertNull(BigIntegerUtils.toString(null, null));
  }

  @Test
  public void testToDate() {
    final long timestamp = 1609459200000L; // 2021-01-01 00:00:00 UTC
    final Date expected = new Date(timestamp);
    assertEquals(expected, BigIntegerUtils.toDate(new BigInteger("1609459200000")));
    assertNull(BigIntegerUtils.toDate(null));
  }

  @Test
  public void testToDateDefaultValue() {
    final long timestamp = 1609459200000L; // 2021-01-01 00:00:00 UTC
    final Date expected = new Date(timestamp);
    final Date defaultDate = new Date(1577836800000L); // 2020-01-01 00:00:00 UTC

    assertEquals(expected, BigIntegerUtils.toDate(new BigInteger("1609459200000"), defaultDate));
    assertEquals(defaultDate, BigIntegerUtils.toDate(null, defaultDate));
    assertNull(BigIntegerUtils.toDate(null, null));
  }

  @Test
  public void testToByteArray() {
    // 对于零值的测试
    assertArrayEquals(new byte[0], BigIntegerUtils.toByteArray(BigInteger.ZERO));

    // 正常值测试 - 注意BigInteger.toByteArray()返回大端序表示
    final byte[] expected1 = {1}; // 1的字节表示
    assertArrayEquals(expected1, BigIntegerUtils.toByteArray(BigInteger.ONE));

    final byte[] expected2 = {1, 35}; // 291的字节表示 (0x0123 = 291)
    assertArrayEquals(expected2, BigIntegerUtils.toByteArray(new BigInteger("291")));

    // 负值测试 - 注意BigInteger使用二进制补码表示
    final byte[] expectedNeg = {-1}; // -1的字节表示
    assertArrayEquals(expectedNeg, BigIntegerUtils.toByteArray(BigInteger.valueOf(-1)));

    // 对于null值的测试
    assertNull(BigIntegerUtils.toByteArray(null));
  }

  @Test
  public void testToByteArrayDefaultValue() {
    final byte[] defaultValue = new byte[] {1, 2, 3};

    // 正常值测试
    final byte[] expected1 = {1}; // 1的字节表示
    assertArrayEquals(expected1, BigIntegerUtils.toByteArray(BigInteger.ONE, defaultValue));

    // 零值测试
    assertArrayEquals(new byte[0], BigIntegerUtils.toByteArray(BigInteger.ZERO, defaultValue));

    // null值测试
    assertArrayEquals(defaultValue, BigIntegerUtils.toByteArray(null, defaultValue));
  }

  @Test
  public void testToClass() {
    assertEquals(BigInteger.class, BigIntegerUtils.toClass(new BigInteger("123")));
    assertNull(BigIntegerUtils.toClass(null));
  }

  @Test
  public void testToClassDefaultValue() {
    assertEquals(BigInteger.class, BigIntegerUtils.toClass(new BigInteger("123"), String.class));
    assertEquals(String.class, BigIntegerUtils.toClass(null, String.class));
    assertNull(BigIntegerUtils.toClass(null, null));
  }

  @Test
  public void testToBigDecimal() {
    assertEquals(new BigDecimal("123"), BigIntegerUtils.toBigDecimal(new BigInteger("123")));
    assertEquals(new BigDecimal("-123"), BigIntegerUtils.toBigDecimal(new BigInteger("-123")));
    assertNull(BigIntegerUtils.toBigDecimal(null));
  }

  @Test
  public void testToBigDecimalDefaultValue() {
    final BigDecimal defaultValue = BigDecimal.TEN;
    assertEquals(new BigDecimal("123"), BigIntegerUtils.toBigDecimal(new BigInteger("123"), defaultValue));
    assertEquals(defaultValue, BigIntegerUtils.toBigDecimal(null, defaultValue));
    assertNull(BigIntegerUtils.toBigDecimal(null, null));
  }

  @Test
  public void testIsComparable() {
    assertTrue(BigIntegerUtils.isComparable(boolean.class));
    assertTrue(BigIntegerUtils.isComparable(byte.class));
    assertTrue(BigIntegerUtils.isComparable(short.class));
    assertTrue(BigIntegerUtils.isComparable(int.class));
    assertTrue(BigIntegerUtils.isComparable(long.class));
    assertTrue(BigIntegerUtils.isComparable(float.class));
    assertTrue(BigIntegerUtils.isComparable(double.class));

    assertTrue(BigIntegerUtils.isComparable(Boolean.class));
    assertTrue(BigIntegerUtils.isComparable(Byte.class));
    assertTrue(BigIntegerUtils.isComparable(Short.class));
    assertTrue(BigIntegerUtils.isComparable(Integer.class));
    assertTrue(BigIntegerUtils.isComparable(Long.class));
    assertTrue(BigIntegerUtils.isComparable(Float.class));
    assertTrue(BigIntegerUtils.isComparable(Double.class));

    assertTrue(BigIntegerUtils.isComparable(BigInteger.class));
    assertTrue(BigIntegerUtils.isComparable(BigDecimal.class));

    assertFalse(BigIntegerUtils.isComparable(String.class));
    assertFalse(BigIntegerUtils.isComparable(Date.class));
    assertFalse(BigIntegerUtils.isComparable(Object.class));
  }
}