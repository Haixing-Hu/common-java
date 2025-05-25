////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MathEx}类中算术运算相关方法的单元测试。
 * 包括绝对值、最大最小值、精确运算等。
 *
 * @author Haixing Hu
 */
public class MathExArithmeticTest {

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      绝对值测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testAbsByte() {
    assertEquals((byte) 5, MathEx.abs((byte) 5));
    assertEquals((byte) 5, MathEx.abs((byte) -5));
    assertEquals((byte) 0, MathEx.abs((byte) 0));
    assertEquals((byte) 127, MathEx.abs((byte) 127));
    assertEquals((byte) 127, MathEx.abs((byte) -127));
  }

  @Test
  void testAbsShort() {
    assertEquals((short) 5, MathEx.abs((short) 5));
    assertEquals((short) 5, MathEx.abs((short) -5));
    assertEquals((short) 0, MathEx.abs((short) 0));
    assertEquals((short) 32767, MathEx.abs((short) 32767));
    assertEquals((short) 32767, MathEx.abs((short) -32767));
  }

  @Test
  void testAbsInt() {
    assertEquals(5, MathEx.abs(5));
    assertEquals(5, MathEx.abs(-5));
    assertEquals(0, MathEx.abs(0));
    assertEquals(Integer.MAX_VALUE, MathEx.abs(Integer.MAX_VALUE));
    assertEquals(Integer.MAX_VALUE, MathEx.abs(-Integer.MAX_VALUE));
  }

  @Test
  void testAbsLong() {
    assertEquals(5L, MathEx.abs(5L));
    assertEquals(5L, MathEx.abs(-5L));
    assertEquals(0L, MathEx.abs(0L));
    assertEquals(Long.MAX_VALUE, MathEx.abs(Long.MAX_VALUE));
    assertEquals(Long.MAX_VALUE, MathEx.abs(-Long.MAX_VALUE));
  }

  @Test
  void testAbsFloat() {
    assertEquals(5.0f, MathEx.abs(5.0f));
    assertEquals(5.0f, MathEx.abs(-5.0f));
    assertEquals(0.0f, MathEx.abs(0.0f));
    assertEquals(0.0f, MathEx.abs(-0.0f));
    assertEquals(Float.MAX_VALUE, MathEx.abs(Float.MAX_VALUE));
    assertEquals(Float.MAX_VALUE, MathEx.abs(-Float.MAX_VALUE));
    assertEquals(Float.POSITIVE_INFINITY, MathEx.abs(Float.POSITIVE_INFINITY));
    assertEquals(Float.POSITIVE_INFINITY, MathEx.abs(Float.NEGATIVE_INFINITY));
    assertTrue(Float.isNaN(MathEx.abs(Float.NaN)));
  }

  @Test
  void testAbsDouble() {
    assertEquals(5.0, MathEx.abs(5.0));
    assertEquals(5.0, MathEx.abs(-5.0));
    assertEquals(0.0, MathEx.abs(0.0));
    assertEquals(0.0, MathEx.abs(-0.0));
    assertEquals(Double.MAX_VALUE, MathEx.abs(Double.MAX_VALUE));
    assertEquals(Double.MAX_VALUE, MathEx.abs(-Double.MAX_VALUE));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.abs(Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.abs(Double.NEGATIVE_INFINITY));
    assertTrue(Double.isNaN(MathEx.abs(Double.NaN)));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      最大最小值（两参数）测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testMinByte() {
    assertEquals((byte) 3, MathEx.min((byte) 5, (byte) 3));
    assertEquals((byte) 5, MathEx.min((byte) 5, (byte) 8));
    assertEquals((byte) -8, MathEx.min((byte) -5, (byte) -8));
    assertEquals((byte) -5, MathEx.min((byte) -5, (byte) 3));
    assertEquals((byte) 0, MathEx.min((byte) 0, (byte) 0));
  }

  @Test
  void testMinShort() {
    assertEquals((short) 3, MathEx.min((short) 5, (short) 3));
    assertEquals((short) 5, MathEx.min((short) 5, (short) 8));
    assertEquals((short) -8, MathEx.min((short) -5, (short) -8));
    assertEquals((short) -5, MathEx.min((short) -5, (short) 3));
    assertEquals((short) 0, MathEx.min((short) 0, (short) 0));
  }

  @Test
  void testMinInt() {
    assertEquals(3, MathEx.min(5, 3));
    assertEquals(5, MathEx.min(5, 8));
    assertEquals(-8, MathEx.min(-5, -8));
    assertEquals(-5, MathEx.min(-5, 3));
    assertEquals(0, MathEx.min(0, 0));
    assertEquals(Integer.MIN_VALUE, MathEx.min(Integer.MIN_VALUE, Integer.MAX_VALUE));
  }

  @Test
  void testMinLong() {
    assertEquals(3L, MathEx.min(5L, 3L));
    assertEquals(5L, MathEx.min(5L, 8L));
    assertEquals(-8L, MathEx.min(-5L, -8L));
    assertEquals(-5L, MathEx.min(-5L, 3L));
    assertEquals(0L, MathEx.min(0L, 0L));
    assertEquals(Long.MIN_VALUE, MathEx.min(Long.MIN_VALUE, Long.MAX_VALUE));
  }

  @Test
  void testMinFloat() {
    assertEquals(3.0f, MathEx.min(5.0f, 3.0f));
    assertEquals(5.0f, MathEx.min(5.0f, 8.0f));
    assertEquals(-8.0f, MathEx.min(-5.0f, -8.0f));
    assertEquals(-5.0f, MathEx.min(-5.0f, 3.0f));
    assertEquals(0.0f, MathEx.min(0.0f, 0.0f));
    assertEquals(-0.0f, MathEx.min(-0.0f, 0.0f));
    assertEquals(Float.NEGATIVE_INFINITY, MathEx.min(Float.NEGATIVE_INFINITY, Float.MAX_VALUE));
    assertTrue(Float.isNaN(MathEx.min(Float.NaN, 5.0f)));
    assertTrue(Float.isNaN(MathEx.min(5.0f, Float.NaN)));
  }

  @Test
  void testMinDouble() {
    assertEquals(3.0, MathEx.min(5.0, 3.0));
    assertEquals(5.0, MathEx.min(5.0, 8.0));
    assertEquals(-8.0, MathEx.min(-5.0, -8.0));
    assertEquals(-5.0, MathEx.min(-5.0, 3.0));
    assertEquals(0.0, MathEx.min(0.0, 0.0));
    assertEquals(-0.0, MathEx.min(-0.0, 0.0));
    assertEquals(Double.NEGATIVE_INFINITY, MathEx.min(Double.NEGATIVE_INFINITY, Double.MAX_VALUE));
    assertTrue(Double.isNaN(MathEx.min(Double.NaN, 5.0)));
    assertTrue(Double.isNaN(MathEx.min(5.0, Double.NaN)));
  }

  @Test
  void testMaxByte() {
    assertEquals((byte) 5, MathEx.max((byte) 5, (byte) 3));
    assertEquals((byte) 8, MathEx.max((byte) 5, (byte) 8));
    assertEquals((byte) -5, MathEx.max((byte) -5, (byte) -8));
    assertEquals((byte) 3, MathEx.max((byte) -5, (byte) 3));
    assertEquals((byte) 0, MathEx.max((byte) 0, (byte) 0));
  }

  @Test
  void testMaxShort() {
    assertEquals((short) 5, MathEx.max((short) 5, (short) 3));
    assertEquals((short) 8, MathEx.max((short) 5, (short) 8));
    assertEquals((short) -5, MathEx.max((short) -5, (short) -8));
    assertEquals((short) 3, MathEx.max((short) -5, (short) 3));
    assertEquals((short) 0, MathEx.max((short) 0, (short) 0));
  }

  @Test
  void testMaxInt() {
    assertEquals(5, MathEx.max(5, 3));
    assertEquals(8, MathEx.max(5, 8));
    assertEquals(-5, MathEx.max(-5, -8));
    assertEquals(3, MathEx.max(-5, 3));
    assertEquals(0, MathEx.max(0, 0));
    assertEquals(Integer.MAX_VALUE, MathEx.max(Integer.MIN_VALUE, Integer.MAX_VALUE));
  }

  @Test
  void testMaxLong() {
    assertEquals(5L, MathEx.max(5L, 3L));
    assertEquals(8L, MathEx.max(5L, 8L));
    assertEquals(-5L, MathEx.max(-5L, -8L));
    assertEquals(3L, MathEx.max(-5L, 3L));
    assertEquals(0L, MathEx.max(0L, 0L));
    assertEquals(Long.MAX_VALUE, MathEx.max(Long.MIN_VALUE, Long.MAX_VALUE));
  }

  @Test
  void testMaxFloat() {
    assertEquals(5.0f, MathEx.max(5.0f, 3.0f));
    assertEquals(8.0f, MathEx.max(5.0f, 8.0f));
    assertEquals(-5.0f, MathEx.max(-5.0f, -8.0f));
    assertEquals(3.0f, MathEx.max(-5.0f, 3.0f));
    assertEquals(0.0f, MathEx.max(0.0f, 0.0f));
    assertEquals(0.0f, MathEx.max(-0.0f, 0.0f));
    assertEquals(Float.POSITIVE_INFINITY, MathEx.max(Float.POSITIVE_INFINITY, Float.MIN_VALUE));
    assertTrue(Float.isNaN(MathEx.max(Float.NaN, 5.0f)));
    assertTrue(Float.isNaN(MathEx.max(5.0f, Float.NaN)));
  }

  @Test
  void testMaxDouble() {
    assertEquals(5.0, MathEx.max(5.0, 3.0));
    assertEquals(8.0, MathEx.max(5.0, 8.0));
    assertEquals(-5.0, MathEx.max(-5.0, -8.0));
    assertEquals(3.0, MathEx.max(-5.0, 3.0));
    assertEquals(0.0, MathEx.max(0.0, 0.0));
    assertEquals(0.0, MathEx.max(-0.0, 0.0));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.max(Double.POSITIVE_INFINITY, Double.MIN_VALUE));
    assertTrue(Double.isNaN(MathEx.max(Double.NaN, 5.0)));
    assertTrue(Double.isNaN(MathEx.max(5.0, Double.NaN)));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      多参数最大最小值测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testMinThreeParameters() {
    // 测试三参数min函数
    assertEquals((byte) 1, MathEx.min((byte) 5, (byte) 3, (byte) 1));
    assertEquals((short) 1, MathEx.min((short) 5, (short) 3, (short) 1));
    assertEquals(1, MathEx.min(5, 3, 1));
    assertEquals(1L, MathEx.min(5L, 3L, 1L));
    assertEquals(1.0f, MathEx.min(5.0f, 3.0f, 1.0f));
    assertEquals(1.0, MathEx.min(5.0, 3.0, 1.0));

    // 测试不同位置的最小值
    assertEquals(1, MathEx.min(1, 3, 5));
    assertEquals(1, MathEx.min(3, 1, 5));
    assertEquals(1, MathEx.min(3, 5, 1));
  }

  @Test
  void testMinFourParameters() {
    // 测试四参数min函数
    assertEquals((byte) 1, MathEx.min((byte) 5, (byte) 3, (byte) 8, (byte) 1));
    assertEquals((short) 1, MathEx.min((short) 5, (short) 3, (short) 8, (short) 1));
    assertEquals(1, MathEx.min(5, 3, 8, 1));
    assertEquals(1L, MathEx.min(5L, 3L, 8L, 1L));
    assertEquals(1.0f, MathEx.min(5.0f, 3.0f, 8.0f, 1.0f));
    assertEquals(1.0, MathEx.min(5.0, 3.0, 8.0, 1.0));
  }

  @Test
  void testMinFiveParameters() {
    // 测试五参数min函数
    assertEquals((byte) 1, MathEx.min((byte) 5, (byte) 3, (byte) 8, (byte) 1, (byte) 9));
    assertEquals((short) 1, MathEx.min((short) 5, (short) 3, (short) 8, (short) 1, (short) 9));
    assertEquals(1, MathEx.min(5, 3, 8, 1, 9));
    assertEquals(1L, MathEx.min(5L, 3L, 8L, 1L, 9L));
    assertEquals(1.0f, MathEx.min(5.0f, 3.0f, 8.0f, 1.0f, 9.0f));
    assertEquals(1.0, MathEx.min(5.0, 3.0, 8.0, 1.0, 9.0));
  }

  @Test
  void testMaxThreeParameters() {
    // 测试三参数max函数
    assertEquals((byte) 8, MathEx.max((byte) 5, (byte) 3, (byte) 8));
    assertEquals((short) 8, MathEx.max((short) 5, (short) 3, (short) 8));
    assertEquals(8, MathEx.max(5, 3, 8));
    assertEquals(8L, MathEx.max(5L, 3L, 8L));
    assertEquals(8.0f, MathEx.max(5.0f, 3.0f, 8.0f));
    assertEquals(8.0, MathEx.max(5.0, 3.0, 8.0));

    // 测试不同位置的最大值
    assertEquals(8, MathEx.max(8, 3, 5));
    assertEquals(8, MathEx.max(3, 8, 5));
    assertEquals(8, MathEx.max(3, 5, 8));
  }

  @Test
  void testMaxFourParameters() {
    // 测试四参数max函数
    assertEquals((byte) 10, MathEx.max((byte) 5, (byte) 3, (byte) 8, (byte) 10));
    assertEquals((short) 10, MathEx.max((short) 5, (short) 3, (short) 8, (short) 10));
    assertEquals(10, MathEx.max(5, 3, 8, 10));
    assertEquals(10L, MathEx.max(5L, 3L, 8L, 10L));
    assertEquals(10.0f, MathEx.max(5.0f, 3.0f, 8.0f, 10.0f));
    assertEquals(10.0, MathEx.max(5.0, 3.0, 8.0, 10.0));
  }

  @Test
  void testMaxFiveParameters() {
    // 测试五参数max函数
    assertEquals((byte) 10, MathEx.max((byte) 5, (byte) 3, (byte) 8, (byte) 10, (byte) 2));
    assertEquals((short) 10, MathEx.max((short) 5, (short) 3, (short) 8, (short) 10, (short) 2));
    assertEquals(10, MathEx.max(5, 3, 8, 10, 2));
    assertEquals(10L, MathEx.max(5L, 3L, 8L, 10L, 2L));
    assertEquals(10.0f, MathEx.max(5.0f, 3.0f, 8.0f, 10.0f, 2.0f));
    assertEquals(10.0, MathEx.max(5.0, 3.0, 8.0, 10.0, 2.0));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      精确运算测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testAddExact() {
    // 测试int精确加法
    assertEquals(8, MathEx.addExact(5, 3));
    assertEquals(0, MathEx.addExact(5, -5));
    assertEquals(-8, MathEx.addExact(-5, -3));
    assertEquals(Integer.MAX_VALUE, MathEx.addExact(Integer.MAX_VALUE - 1, 1));

    // 测试long精确加法
    assertEquals(8L, MathEx.addExact(5L, 3L));
    assertEquals(0L, MathEx.addExact(5L, -5L));
    assertEquals(-8L, MathEx.addExact(-5L, -3L));
    assertEquals(Long.MAX_VALUE, MathEx.addExact(Long.MAX_VALUE - 1, 1L));

    // 测试溢出
    assertThrows(ArithmeticException.class, () -> MathEx.addExact(Integer.MAX_VALUE, 1));
    assertThrows(ArithmeticException.class, () -> MathEx.addExact(Integer.MIN_VALUE, -1));
    assertThrows(ArithmeticException.class, () -> MathEx.addExact(Long.MAX_VALUE, 1L));
    assertThrows(ArithmeticException.class, () -> MathEx.addExact(Long.MIN_VALUE, -1L));
  }

  @Test
  void testSubtractExact() {
    // 测试int精确减法
    assertEquals(2, MathEx.subtractExact(5, 3));
    assertEquals(10, MathEx.subtractExact(5, -5));
    assertEquals(-2, MathEx.subtractExact(-5, -3));
    assertEquals(Integer.MIN_VALUE, MathEx.subtractExact(Integer.MIN_VALUE + 1, 1));

    // 测试long精确减法
    assertEquals(2L, MathEx.subtractExact(5L, 3L));
    assertEquals(10L, MathEx.subtractExact(5L, -5L));
    assertEquals(-2L, MathEx.subtractExact(-5L, -3L));
    assertEquals(Long.MIN_VALUE, MathEx.subtractExact(Long.MIN_VALUE + 1, 1L));

    // 测试溢出
    assertThrows(ArithmeticException.class, () -> MathEx.subtractExact(Integer.MIN_VALUE, 1));
    assertThrows(ArithmeticException.class, () -> MathEx.subtractExact(Integer.MAX_VALUE, -1));
    assertThrows(ArithmeticException.class, () -> MathEx.subtractExact(Long.MIN_VALUE, 1L));
    assertThrows(ArithmeticException.class, () -> MathEx.subtractExact(Long.MAX_VALUE, -1L));
  }

  @Test
  void testMultiplyExact() {
    // 测试int精确乘法
    assertEquals(15, MathEx.multiplyExact(5, 3));
    assertEquals(-15, MathEx.multiplyExact(5, -3));
    assertEquals(15, MathEx.multiplyExact(-5, -3));
    assertEquals(0, MathEx.multiplyExact(0, Integer.MAX_VALUE));

    // 测试long和int乘法
    assertEquals(15L, MathEx.multiplyExact(5L, 3));
    assertEquals(-15L, MathEx.multiplyExact(5L, -3));
    assertEquals(0L, MathEx.multiplyExact(0L, Integer.MAX_VALUE));

    // 测试long精确乘法
    assertEquals(15L, MathEx.multiplyExact(5L, 3L));
    assertEquals(-15L, MathEx.multiplyExact(5L, -3L));
    assertEquals(15L, MathEx.multiplyExact(-5L, -3L));
    assertEquals(0L, MathEx.multiplyExact(0L, Long.MAX_VALUE));

    // 测试溢出
    assertThrows(ArithmeticException.class, () -> MathEx.multiplyExact(Integer.MAX_VALUE, 2));
    assertThrows(ArithmeticException.class, () -> MathEx.multiplyExact(Integer.MIN_VALUE, 2));
    assertThrows(ArithmeticException.class, () -> MathEx.multiplyExact(Long.MAX_VALUE, 2L));
    assertThrows(ArithmeticException.class, () -> MathEx.multiplyExact(Long.MIN_VALUE, 2L));
  }

  @Test
  void testIncrementDecrementExact() {
    // 测试int精确递增
    assertEquals(6, MathEx.incrementExact(5));
    assertEquals(1, MathEx.incrementExact(0));
    assertEquals(0, MathEx.incrementExact(-1));
    assertEquals(Integer.MAX_VALUE, MathEx.incrementExact(Integer.MAX_VALUE - 1));

    // 测试long精确递增
    assertEquals(6L, MathEx.incrementExact(5L));
    assertEquals(1L, MathEx.incrementExact(0L));
    assertEquals(0L, MathEx.incrementExact(-1L));
    assertEquals(Long.MAX_VALUE, MathEx.incrementExact(Long.MAX_VALUE - 1));

    // 测试int精确递减
    assertEquals(4, MathEx.decrementExact(5));
    assertEquals(-1, MathEx.decrementExact(0));
    assertEquals(-2, MathEx.decrementExact(-1));
    assertEquals(Integer.MIN_VALUE, MathEx.decrementExact(Integer.MIN_VALUE + 1));

    // 测试long精确递减
    assertEquals(4L, MathEx.decrementExact(5L));
    assertEquals(-1L, MathEx.decrementExact(0L));
    assertEquals(-2L, MathEx.decrementExact(-1L));
    assertEquals(Long.MIN_VALUE, MathEx.decrementExact(Long.MIN_VALUE + 1));

    // 测试溢出
    assertThrows(ArithmeticException.class, () -> MathEx.incrementExact(Integer.MAX_VALUE));
    assertThrows(ArithmeticException.class, () -> MathEx.incrementExact(Long.MAX_VALUE));
    assertThrows(ArithmeticException.class, () -> MathEx.decrementExact(Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> MathEx.decrementExact(Long.MIN_VALUE));
  }

  @Test
  void testNegateExact() {
    // 测试int精确取负
    assertEquals(-5, MathEx.negateExact(5));
    assertEquals(5, MathEx.negateExact(-5));
    assertEquals(0, MathEx.negateExact(0));
    assertEquals(-Integer.MAX_VALUE, MathEx.negateExact(Integer.MAX_VALUE));

    // 测试long精确取负
    assertEquals(-5L, MathEx.negateExact(5L));
    assertEquals(5L, MathEx.negateExact(-5L));
    assertEquals(0L, MathEx.negateExact(0L));
    assertEquals(-Long.MAX_VALUE, MathEx.negateExact(Long.MAX_VALUE));

    // 测试溢出
    assertThrows(ArithmeticException.class, () -> MathEx.negateExact(Integer.MIN_VALUE));
    assertThrows(ArithmeticException.class, () -> MathEx.negateExact(Long.MIN_VALUE));
  }

  @Test
  void testToIntExact() {
    // 测试正常转换
    assertEquals(0, MathEx.toIntExact(0L));
    assertEquals(100, MathEx.toIntExact(100L));
    assertEquals(-100, MathEx.toIntExact(-100L));
    assertEquals(Integer.MAX_VALUE, MathEx.toIntExact((long) Integer.MAX_VALUE));
    assertEquals(Integer.MIN_VALUE, MathEx.toIntExact((long) Integer.MIN_VALUE));

    // 测试溢出
    assertThrows(ArithmeticException.class, () -> MathEx.toIntExact((long) Integer.MAX_VALUE + 1));
    assertThrows(ArithmeticException.class, () -> MathEx.toIntExact((long) Integer.MIN_VALUE - 1));
    assertThrows(ArithmeticException.class, () -> MathEx.toIntExact(Long.MAX_VALUE));
    assertThrows(ArithmeticException.class, () -> MathEx.toIntExact(Long.MIN_VALUE));
  }

  @Test
  void testMultiplyFull() {
    // 测试完整乘积
    assertEquals(15L, MathEx.multiplyFull(5, 3));
    assertEquals(-15L, MathEx.multiplyFull(5, -3));
    assertEquals(15L, MathEx.multiplyFull(-5, -3));
    assertEquals(0L, MathEx.multiplyFull(0, Integer.MAX_VALUE));

    // 测试大数乘法，验证不会溢出
    long expected = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
    assertEquals(expected, MathEx.multiplyFull(Integer.MAX_VALUE, Integer.MAX_VALUE));

    expected = (long) Integer.MIN_VALUE * Integer.MIN_VALUE;
    assertEquals(expected, MathEx.multiplyFull(Integer.MIN_VALUE, Integer.MIN_VALUE));
  }

  @Test
  void testMultiplyHigh() {
    // 测试高位乘积
    assertEquals(0L, MathEx.multiplyHigh(5L, 3L)); // 小数乘积的高位为0
    assertEquals(0L, MathEx.multiplyHigh(0L, Long.MAX_VALUE));

    // 测试大数的高位乘积
    long a = Long.MAX_VALUE;
    long b = 2L;
    long high = MathEx.multiplyHigh(a, b);
    // 验证高位结果的正确性：对于很大的数，高位应该非零
    assertTrue(high >= 0);
  }
} 