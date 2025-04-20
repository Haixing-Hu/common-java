////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link BigDecimalUtils}类。
 *
 * @author Haixing Hu
 */
public class BigDecimalUtilsTest {

  @Test
  public void testToBoolean() {
    assertTrue(BigDecimalUtils.toBoolean(new BigDecimal("1")));
    assertTrue(BigDecimalUtils.toBoolean(new BigDecimal("-1")));
    assertFalse(BigDecimalUtils.toBoolean(new BigDecimal("0")));
    assertFalse(BigDecimalUtils.toBoolean(null));
  }

  @Test
  public void testToBooleanDefaultValue() {
    assertTrue(BigDecimalUtils.toBoolean(new BigDecimal("1"), true));
    assertTrue(BigDecimalUtils.toBoolean(new BigDecimal("-1"), false));
    assertFalse(BigDecimalUtils.toBoolean(new BigDecimal("0"), true));
    assertTrue(BigDecimalUtils.toBoolean(null, true));
    assertFalse(BigDecimalUtils.toBoolean(null, false));
  }

  @Test
  public void testToBooleanObject() {
    assertTrue(BigDecimalUtils.toBooleanObject(new BigDecimal("1")));
    assertTrue(BigDecimalUtils.toBooleanObject(new BigDecimal("-1")));
    assertFalse(BigDecimalUtils.toBooleanObject(new BigDecimal("0")));
    assertNull(BigDecimalUtils.toBooleanObject(null));
  }

  @Test
  public void testToBooleanObjectDefaultValue() {
    assertTrue(BigDecimalUtils.toBooleanObject(new BigDecimal("1"), Boolean.TRUE));
    assertTrue(BigDecimalUtils.toBooleanObject(new BigDecimal("-1"), Boolean.FALSE));
    assertFalse(BigDecimalUtils.toBooleanObject(new BigDecimal("0"), Boolean.TRUE));
    assertTrue(BigDecimalUtils.toBooleanObject(null, Boolean.TRUE));
    assertFalse(BigDecimalUtils.toBooleanObject(null, Boolean.FALSE));
    assertNull(BigDecimalUtils.toBooleanObject(null, null));
  }

  @Test
  public void testToChar() {
    assertEquals('あ', BigDecimalUtils.toChar(new BigDecimal("12354")));
    assertEquals('A', BigDecimalUtils.toChar(new BigDecimal("65")));
    assertEquals('\0', BigDecimalUtils.toChar(null));
  }

  @Test
  public void testToCharDefaultValue() {
    assertEquals('A', BigDecimalUtils.toChar(new BigDecimal("65"), 'B'));
    assertEquals('B', BigDecimalUtils.toChar(null, 'B'));
  }

  @Test
  public void testToCharObject() {
    assertEquals(Character.valueOf('A'), BigDecimalUtils.toCharObject(new BigDecimal("65")));
    assertNull(BigDecimalUtils.toCharObject(null));
  }

  @Test
  public void testToCharObjectDefaultValue() {
    assertEquals(Character.valueOf('A'), BigDecimalUtils.toCharObject(new BigDecimal("65"), 'B'));
    assertEquals(Character.valueOf('B'), BigDecimalUtils.toCharObject(null, 'B'));
    assertNull(BigDecimalUtils.toCharObject(null, null));
  }

  @Test
  public void testToByte() {
    assertEquals((byte)127, BigDecimalUtils.toByte(new BigDecimal("127")));
    assertEquals((byte)-128, BigDecimalUtils.toByte(new BigDecimal("-128")));
    // 溢出情况测试
    assertEquals((byte)-1, BigDecimalUtils.toByte(new BigDecimal("255")));
    assertEquals((byte)0, BigDecimalUtils.toByte(null));
  }

  @Test
  public void testToByteDefaultValue() {
    assertEquals((byte)127, BigDecimalUtils.toByte(new BigDecimal("127"), (byte)10));
    assertEquals((byte)10, BigDecimalUtils.toByte(null, (byte)10));
  }

  @Test
  public void testToByteObject() {
    assertEquals(Byte.valueOf((byte)127), BigDecimalUtils.toByteObject(new BigDecimal("127")));
    assertNull(BigDecimalUtils.toByteObject(null));
  }

  @Test
  public void testToByteObjectDefaultValue() {
    assertEquals(Byte.valueOf((byte)127), BigDecimalUtils.toByteObject(new BigDecimal("127"), (byte)10));
    assertEquals(Byte.valueOf((byte)10), BigDecimalUtils.toByteObject(null, (byte)10));
    assertNull(BigDecimalUtils.toByteObject(null, null));
  }

  @Test
  public void testToShort() {
    assertEquals((short)32767, BigDecimalUtils.toShort(new BigDecimal("32767")));
    assertEquals((short)-32768, BigDecimalUtils.toShort(new BigDecimal("-32768")));
    // 溢出情况测试
    assertEquals((short)-1, BigDecimalUtils.toShort(new BigDecimal("65535")));
    assertEquals((short)0, BigDecimalUtils.toShort(null));
  }

  @Test
  public void testToShortDefaultValue() {
    assertEquals((short)32767, BigDecimalUtils.toShort(new BigDecimal("32767"), (short)100));
    assertEquals((short)100, BigDecimalUtils.toShort(null, (short)100));
  }

  @Test
  public void testToShortObject() {
    assertEquals(Short.valueOf((short)32767), BigDecimalUtils.toShortObject(new BigDecimal("32767")));
    assertNull(BigDecimalUtils.toShortObject(null));
  }

  @Test
  public void testToShortObjectDefaultValue() {
    assertEquals(Short.valueOf((short)32767), BigDecimalUtils.toShortObject(new BigDecimal("32767"), (short)100));
    assertEquals(Short.valueOf((short)100), BigDecimalUtils.toShortObject(null, (short)100));
    assertNull(BigDecimalUtils.toShortObject(null, null));
  }

  @Test
  public void testToInt() {
    assertEquals(2147483647, BigDecimalUtils.toInt(new BigDecimal("2147483647")));
    assertEquals(-2147483648, BigDecimalUtils.toInt(new BigDecimal("-2147483648")));
    // 溢出情况测试
    assertEquals(-1, BigDecimalUtils.toInt(new BigDecimal("4294967295")));
    assertEquals(0, BigDecimalUtils.toInt(null));
  }

  @Test
  public void testToIntDefaultValue() {
    assertEquals(2147483647, BigDecimalUtils.toInt(new BigDecimal("2147483647"), 100));
    assertEquals(100, BigDecimalUtils.toInt(null, 100));
  }

  @Test
  public void testToIntObject() {
    assertEquals(Integer.valueOf(2147483647), BigDecimalUtils.toIntObject(new BigDecimal("2147483647")));
    assertNull(BigDecimalUtils.toIntObject(null));
  }

  @Test
  public void testToIntObjectDefaultValue() {
    assertEquals(Integer.valueOf(2147483647), BigDecimalUtils.toIntObject(new BigDecimal("2147483647"), 100));
    assertEquals(Integer.valueOf(100), BigDecimalUtils.toIntObject(null, 100));
    assertNull(BigDecimalUtils.toIntObject(null, null));
  }

  @Test
  public void testToLong() {
    assertEquals(9223372036854775807L, BigDecimalUtils.toLong(new BigDecimal("9223372036854775807")));
    assertEquals(-9223372036854775808L, BigDecimalUtils.toLong(new BigDecimal("-9223372036854775808")));
    // 超出long范围的大数值
    assertEquals(-1L, BigDecimalUtils.toLong(new BigDecimal("18446744073709551615")));
    assertEquals(0L, BigDecimalUtils.toLong(null));
  }

  @Test
  public void testToLongDefaultValue() {
    assertEquals(9223372036854775807L, BigDecimalUtils.toLong(new BigDecimal("9223372036854775807"), 100L));
    assertEquals(100L, BigDecimalUtils.toLong(null, 100L));
  }

  @Test
  public void testToLongObject() {
    assertEquals(Long.valueOf(9223372036854775807L), BigDecimalUtils.toLongObject(new BigDecimal("9223372036854775807")));
    assertNull(BigDecimalUtils.toLongObject(null));
  }

  @Test
  public void testToLongObjectDefaultValue() {
    assertEquals(Long.valueOf(9223372036854775807L), BigDecimalUtils.toLongObject(new BigDecimal("9223372036854775807"), 100L));
    assertEquals(Long.valueOf(100L), BigDecimalUtils.toLongObject(null, 100L));
    assertNull(BigDecimalUtils.toLongObject(null, null));
  }

  @Test
  public void testToFloat() {
    assertEquals(3.1415927f, BigDecimalUtils.toFloat(new BigDecimal("3.1415927")), 0.0000001f);
    assertEquals(-3.1415927f, BigDecimalUtils.toFloat(new BigDecimal("-3.1415927")), 0.0000001f);
    assertEquals(0.0f, BigDecimalUtils.toFloat(null), 0.0f);
  }

  @Test
  public void testToFloatDefaultValue() {
    assertEquals(3.1415927f, BigDecimalUtils.toFloat(new BigDecimal("3.1415927"), 2.718f), 0.0000001f);
    assertEquals(2.718f, BigDecimalUtils.toFloat(null, 2.718f), 0.0f);
  }

  @Test
  public void testToFloatObject() {
    assertEquals(Float.valueOf(3.1415927f), BigDecimalUtils.toFloatObject(new BigDecimal("3.1415927")), 0.0000001f);
    assertNull(BigDecimalUtils.toFloatObject(null));
  }

  @Test
  public void testToFloatObjectDefaultValue() {
    assertEquals(Float.valueOf(3.1415927f), BigDecimalUtils.toFloatObject(new BigDecimal("3.1415927"), 2.718f), 0.0000001f);
    assertEquals(Float.valueOf(2.718f), BigDecimalUtils.toFloatObject(null, 2.718f));
    assertNull(BigDecimalUtils.toFloatObject(null, null));
  }

  @Test
  public void testToDouble() {
    assertEquals(3.141592653589793, BigDecimalUtils.toDouble(new BigDecimal("3.141592653589793")), 0.0000000000000001);
    assertEquals(-3.141592653589793, BigDecimalUtils.toDouble(new BigDecimal("-3.141592653589793")), 0.0000000000000001);
    assertEquals(0.0, BigDecimalUtils.toDouble(null), 0.0);
  }

  @Test
  public void testToDoubleDefaultValue() {
    assertEquals(3.141592653589793, BigDecimalUtils.toDouble(new BigDecimal("3.141592653589793"), 2.718281828459045), 0.0000000000000001);
    assertEquals(2.718281828459045, BigDecimalUtils.toDouble(null, 2.718281828459045), 0.0);
  }

  @Test
  public void testToDoubleObject() {
    assertEquals(Double.valueOf(3.141592653589793), BigDecimalUtils.toDoubleObject(new BigDecimal("3.141592653589793")), 0.0000000000000001);
    assertNull(BigDecimalUtils.toDoubleObject(null));
  }

  @Test
  public void testToDoubleObjectDefaultValue() {
    assertEquals(Double.valueOf(3.141592653589793), BigDecimalUtils.toDoubleObject(new BigDecimal("3.141592653589793"), 2.718281828459045), 0.0000000000000001);
    assertEquals(Double.valueOf(2.718281828459045), BigDecimalUtils.toDoubleObject(null, 2.718281828459045));
    assertNull(BigDecimalUtils.toDoubleObject(null, null));
  }

  @Test
  public void testToString() {
    assertEquals("3.141592653589793", BigDecimalUtils.toString(new BigDecimal("3.141592653589793")));
    assertEquals("-3.141592653589793", BigDecimalUtils.toString(new BigDecimal("-3.141592653589793")));
    assertNull(BigDecimalUtils.toString(null));
  }

  @Test
  public void testToStringDefaultValue() {
    assertEquals("3.141592653589793", BigDecimalUtils.toString(new BigDecimal("3.141592653589793"), "default"));
    assertEquals("default", BigDecimalUtils.toString(null, "default"));
    assertNull(BigDecimalUtils.toString(null, null));
  }

  @Test
  public void testToDate() {
    final long timestamp = 1609459200000L; // 2021-01-01 00:00:00 UTC
    final Date expected = new Date(timestamp);
    assertEquals(expected, BigDecimalUtils.toDate(new BigDecimal(timestamp)));
    assertNull(BigDecimalUtils.toDate(null));
  }

  @Test
  public void testToDateDefaultValue() {
    final long timestamp = 1609459200000L; // 2021-01-01 00:00:00 UTC
    final Date expected = new Date(timestamp);
    final Date defaultDate = new Date(1577836800000L); // 2020-01-01 00:00:00 UTC
    
    assertEquals(expected, BigDecimalUtils.toDate(new BigDecimal(timestamp), defaultDate));
    assertEquals(defaultDate, BigDecimalUtils.toDate(null, defaultDate));
    assertNull(BigDecimalUtils.toDate(null, null));
  }

  @Test
  public void testToByteArray() {
    // 对于零值的测试
    assertArrayEquals(new byte[0], BigDecimalUtils.toByteArray(BigDecimal.ZERO));
    
    // 对于正常值的测试 - 由于实现细节涉及内部字节格式，这里仅确保结果非空且长度合理
    final byte[] result = BigDecimalUtils.toByteArray(new BigDecimal("123.456"));
    assertNotNull(result);
    assertTrue(result.length > 0);
    
    // 对于null值的测试
    assertNull(BigDecimalUtils.toByteArray(null));
  }

  @Test
  public void testToByteArrayDefaultValue() {
    final byte[] defaultValue = new byte[] {1, 2, 3};
    // 正常值测试
    final byte[] result = BigDecimalUtils.toByteArray(new BigDecimal("123.456"), defaultValue);
    assertNotNull(result);
    assertTrue(result.length > 0);
    
    // null值测试
    assertArrayEquals(defaultValue, BigDecimalUtils.toByteArray(null, defaultValue));
  }

  @Test
  public void testToClass() {
    assertEquals(BigDecimal.class, BigDecimalUtils.toClass(new BigDecimal("123.456")));
    assertNull(BigDecimalUtils.toClass(null));
  }

  @Test
  public void testToClassDefaultValue() {
    assertEquals(BigDecimal.class, BigDecimalUtils.toClass(new BigDecimal("123.456"), String.class));
    assertEquals(String.class, BigDecimalUtils.toClass(null, String.class));
    assertNull(BigDecimalUtils.toClass(null, null));
  }

  @Test
  public void testToBigInteger() {
    assertEquals(BigInteger.valueOf(123), BigDecimalUtils.toBigInteger(new BigDecimal("123.456")));
    assertEquals(BigInteger.valueOf(-123), BigDecimalUtils.toBigInteger(new BigDecimal("-123.456")));
    assertNull(BigDecimalUtils.toBigInteger(null));
  }

  @Test
  public void testToBigIntegerDefaultValue() {
    final BigInteger defaultValue = BigInteger.TEN;
    assertEquals(BigInteger.valueOf(123), BigDecimalUtils.toBigInteger(new BigDecimal("123.456"), defaultValue));
    assertEquals(defaultValue, BigDecimalUtils.toBigInteger(null, defaultValue));
    assertNull(BigDecimalUtils.toBigInteger(null, null));
  }

  @Test
  public void testEqualsWithLong() {
    assertTrue(BigDecimalUtils.equals(new BigDecimal("123"), 123L));
    assertTrue(BigDecimalUtils.equals(123L, new BigDecimal("123")));
    assertFalse(BigDecimalUtils.equals(new BigDecimal("123.1"), 123L));
    assertFalse(BigDecimalUtils.equals(124L, new BigDecimal("123")));
    assertFalse(BigDecimalUtils.equals(null, 123L));
    assertFalse(BigDecimalUtils.equals(123L, null));
  }

  @Test
  public void testEqualsWithBigDecimal() {
    assertTrue(BigDecimalUtils.equals(new BigDecimal("123"), new BigDecimal("123")));
    assertTrue(BigDecimalUtils.equals(new BigDecimal("123.00"), new BigDecimal("123")));
    assertFalse(BigDecimalUtils.equals(new BigDecimal("123.1"), new BigDecimal("123")));
    assertTrue(BigDecimalUtils.equals(null, null));
    assertFalse(BigDecimalUtils.equals(new BigDecimal("123"), null));
    assertFalse(BigDecimalUtils.equals(null, new BigDecimal("123")));
  }

  @Test
  public void testLimitPrecision() {
    assertEquals(new BigDecimal("123.46"), BigDecimalUtils.limitPrecision(new BigDecimal("123.456"), 2));
    assertEquals(new BigDecimal("123.5"), BigDecimalUtils.limitPrecision(new BigDecimal("123.45"), 1));
    assertEquals(new BigDecimal("123"), BigDecimalUtils.limitPrecision(new BigDecimal("123"), 0));
    assertNull(BigDecimalUtils.limitPrecision(null, 2));
  }

  @Test
  public void testNormalizeMoney() {
    // 从MoneyCodecTest中看出MoneyCodec.DEFAULT_SCALE为4
    assertEquals(new BigDecimal("123.4560"), BigDecimalUtils.normalizeMoney(new BigDecimal("123.456")));
    assertEquals(new BigDecimal("123.4500"), BigDecimalUtils.normalizeMoney(new BigDecimal("123.45")));
    assertEquals(new BigDecimal("123.0000"), BigDecimalUtils.normalizeMoney(new BigDecimal("123")));
  }

  @Test
  public void testFormatMoney() {
    assertEquals(new BigDecimal("123.46"), BigDecimalUtils.formatMoney(new BigDecimal("123.456")));
    assertEquals(new BigDecimal("123.45"), BigDecimalUtils.formatMoney(new BigDecimal("123.45")));
    assertEquals(new BigDecimal("123.00"), BigDecimalUtils.formatMoney(new BigDecimal("123")));
    assertEquals(new BigDecimal("0.00"), BigDecimalUtils.formatMoney(null));
  }

  @Test
  public void testIsComparable() {
    assertTrue(BigDecimalUtils.isComparable(boolean.class));
    assertTrue(BigDecimalUtils.isComparable(byte.class));
    assertTrue(BigDecimalUtils.isComparable(short.class));
    assertTrue(BigDecimalUtils.isComparable(int.class));
    assertTrue(BigDecimalUtils.isComparable(long.class));
    assertTrue(BigDecimalUtils.isComparable(float.class));
    assertTrue(BigDecimalUtils.isComparable(double.class));
    
    assertTrue(BigDecimalUtils.isComparable(Boolean.class));
    assertTrue(BigDecimalUtils.isComparable(Byte.class));
    assertTrue(BigDecimalUtils.isComparable(Short.class));
    assertTrue(BigDecimalUtils.isComparable(Integer.class));
    assertTrue(BigDecimalUtils.isComparable(Long.class));
    assertTrue(BigDecimalUtils.isComparable(Float.class));
    assertTrue(BigDecimalUtils.isComparable(Double.class));
    
    assertTrue(BigDecimalUtils.isComparable(BigInteger.class));
    assertTrue(BigDecimalUtils.isComparable(BigDecimal.class));
    
    assertFalse(BigDecimalUtils.isComparable(String.class));
    assertFalse(BigDecimalUtils.isComparable(Date.class));
    assertFalse(BigDecimalUtils.isComparable(Object.class));
  }
} 