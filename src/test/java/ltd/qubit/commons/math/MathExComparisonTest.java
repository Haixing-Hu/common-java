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
 * {@link MathEx}类中比较和数值限制相关方法的单元测试。
 * 包括浮点数比较和数值限制（clamp）等。
 *
 * @author Haixing Hu
 */
public class MathExComparisonTest {

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      浮点数比较测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testIsZeroWithDefaultEpsilon() {
    // 测试float类型的isZero
    assertTrue(MathEx.isZero(0.0f));
    assertTrue(MathEx.isZero(MathEx.DEFAULT_FLOAT_EPSILON / 2));
    assertTrue(MathEx.isZero(-MathEx.DEFAULT_FLOAT_EPSILON / 2));
    assertFalse(MathEx.isZero(MathEx.DEFAULT_FLOAT_EPSILON * 2));
    assertFalse(MathEx.isZero(-MathEx.DEFAULT_FLOAT_EPSILON * 2));

    // 测试double类型的isZero
    assertTrue(MathEx.isZero(0.0));
    assertTrue(MathEx.isZero(MathEx.DEFAULT_DOUBLE_EPSILON / 2));
    assertTrue(MathEx.isZero(-MathEx.DEFAULT_DOUBLE_EPSILON / 2));
    assertFalse(MathEx.isZero(MathEx.DEFAULT_DOUBLE_EPSILON * 2));
    assertFalse(MathEx.isZero(-MathEx.DEFAULT_DOUBLE_EPSILON * 2));
  }

  @Test
  void testIsZeroWithCustomEpsilon() {
    float customFloatEpsilon = 1e-3f;
    double customDoubleEpsilon = 1e-9;

    // 测试float类型的isZero，使用自定义精度
    assertTrue(MathEx.isZero(0.0f, customFloatEpsilon));
    assertTrue(MathEx.isZero(customFloatEpsilon / 2, customFloatEpsilon));
    assertTrue(MathEx.isZero(-customFloatEpsilon / 2, customFloatEpsilon));
    assertFalse(MathEx.isZero(customFloatEpsilon * 2, customFloatEpsilon));
    assertFalse(MathEx.isZero(-customFloatEpsilon * 2, customFloatEpsilon));

    // 测试double类型的isZero，使用自定义精度
    assertTrue(MathEx.isZero(0.0, customDoubleEpsilon));
    assertTrue(MathEx.isZero(customDoubleEpsilon / 2, customDoubleEpsilon));
    assertTrue(MathEx.isZero(-customDoubleEpsilon / 2, customDoubleEpsilon));
    assertFalse(MathEx.isZero(customDoubleEpsilon * 2, customDoubleEpsilon));
    assertFalse(MathEx.isZero(-customDoubleEpsilon * 2, customDoubleEpsilon));
  }

  @Test
  void testIsPositive() {
    // 测试float类型的isPositive
    assertTrue(MathEx.isPositive(MathEx.DEFAULT_FLOAT_EPSILON * 2));
    assertTrue(MathEx.isPositive(1.0f));
    assertFalse(MathEx.isPositive(0.0f));
    assertFalse(MathEx.isPositive(MathEx.DEFAULT_FLOAT_EPSILON / 2));
    assertFalse(MathEx.isPositive(-1.0f));

    // 测试double类型的isPositive
    assertTrue(MathEx.isPositive(MathEx.DEFAULT_DOUBLE_EPSILON * 2));
    assertTrue(MathEx.isPositive(1.0));
    assertFalse(MathEx.isPositive(0.0));
    assertFalse(MathEx.isPositive(MathEx.DEFAULT_DOUBLE_EPSILON / 2));
    assertFalse(MathEx.isPositive(-1.0));

    // 测试自定义精度
    float customFloatEpsilon = 1e-3f;
    assertTrue(MathEx.isPositive(customFloatEpsilon * 2, customFloatEpsilon));
    assertFalse(MathEx.isPositive(customFloatEpsilon / 2, customFloatEpsilon));

    double customDoubleEpsilon = 1e-9;
    assertTrue(MathEx.isPositive(customDoubleEpsilon * 2, customDoubleEpsilon));
    assertFalse(MathEx.isPositive(customDoubleEpsilon / 2, customDoubleEpsilon));
  }

  @Test
  void testIsNegative() {
    // 测试float类型的isNegative
    assertTrue(MathEx.isNegative(-MathEx.DEFAULT_FLOAT_EPSILON * 2));
    assertTrue(MathEx.isNegative(-1.0f));
    assertFalse(MathEx.isNegative(0.0f));
    assertFalse(MathEx.isNegative(-MathEx.DEFAULT_FLOAT_EPSILON / 2));
    assertFalse(MathEx.isNegative(1.0f));

    // 测试double类型的isNegative
    assertTrue(MathEx.isNegative(-MathEx.DEFAULT_DOUBLE_EPSILON * 2));
    assertTrue(MathEx.isNegative(-1.0));
    assertFalse(MathEx.isNegative(0.0));
    assertFalse(MathEx.isNegative(-MathEx.DEFAULT_DOUBLE_EPSILON / 2));
    assertFalse(MathEx.isNegative(1.0));

    // 测试自定义精度
    float customFloatEpsilon = 1e-3f;
    assertTrue(MathEx.isNegative(-customFloatEpsilon * 2, customFloatEpsilon));
    assertFalse(MathEx.isNegative(-customFloatEpsilon / 2, customFloatEpsilon));

    double customDoubleEpsilon = 1e-9;
    assertTrue(MathEx.isNegative(-customDoubleEpsilon * 2, customDoubleEpsilon));
    assertFalse(MathEx.isNegative(-customDoubleEpsilon / 2, customDoubleEpsilon));
  }

  @Test
  void testBetween() {
    // 测试float类型的between
    assertTrue(MathEx.between(5.0f, 1.0f, 10.0f));
    assertTrue(MathEx.between(1.0f, 1.0f, 10.0f));
    assertTrue(MathEx.between(10.0f, 1.0f, 10.0f));
    assertFalse(MathEx.between(0.5f, 1.0f, 10.0f));
    assertFalse(MathEx.between(10.5f, 1.0f, 10.0f));

    // 测试double类型的between
    assertTrue(MathEx.between(5.0, 1.0, 10.0));
    assertTrue(MathEx.between(1.0, 1.0, 10.0));
    assertTrue(MathEx.between(10.0, 1.0, 10.0));
    assertFalse(MathEx.between(0.5, 1.0, 10.0));
    assertFalse(MathEx.between(10.5, 1.0, 10.0));

    // 测试边界情况与精度
    float epsilon = 1e-3f;
    assertTrue(MathEx.between(1.0f - epsilon / 2, 1.0f, 10.0f, epsilon));
    assertTrue(MathEx.between(10.0f + epsilon / 2, 1.0f, 10.0f, epsilon));
    assertFalse(MathEx.between(1.0f - epsilon * 2, 1.0f, 10.0f, epsilon));
    assertFalse(MathEx.between(10.0f + epsilon * 2, 1.0f, 10.0f, epsilon));
  }

  @Test
  void testEqual() {
    // 测试float类型的equal
    assertTrue(MathEx.equal(1.0f, 1.0f));
    assertTrue(MathEx.equal(1.0f, 1.0f + MathEx.DEFAULT_FLOAT_EPSILON / 2));
    assertTrue(MathEx.equal(1.0f, 1.0f - MathEx.DEFAULT_FLOAT_EPSILON / 2));
    assertFalse(MathEx.equal(1.0f, 1.0f + MathEx.DEFAULT_FLOAT_EPSILON * 2));
    assertFalse(MathEx.equal(1.0f, 1.0f - MathEx.DEFAULT_FLOAT_EPSILON * 2));

    // 测试double类型的equal
    assertTrue(MathEx.equal(1.0, 1.0));
    assertTrue(MathEx.equal(1.0, 1.0 + MathEx.DEFAULT_DOUBLE_EPSILON / 2));
    assertTrue(MathEx.equal(1.0, 1.0 - MathEx.DEFAULT_DOUBLE_EPSILON / 2));
    assertFalse(MathEx.equal(1.0, 1.0 + MathEx.DEFAULT_DOUBLE_EPSILON * 2));
    assertFalse(MathEx.equal(1.0, 1.0 - MathEx.DEFAULT_DOUBLE_EPSILON * 2));

    // 测试自定义精度
    float customFloatEpsilon = 1e-2f;
    assertTrue(MathEx.equal(1.0f, 1.005f, customFloatEpsilon));
    assertFalse(MathEx.equal(1.0f, 1.02f, customFloatEpsilon));

    double customDoubleEpsilon = 1e-8;
    assertTrue(MathEx.equal(1.0, 1.000000005, customDoubleEpsilon));
    assertFalse(MathEx.equal(1.0, 1.00000002, customDoubleEpsilon));
  }

  @Test
  void testEqualSpecialValues() {
    // 测试特殊值 - 修复后的源码能正确处理特殊值
    assertTrue(MathEx.equal(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
    assertTrue(MathEx.equal(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));
    assertFalse(MathEx.equal(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY));
    assertFalse(MathEx.equal(Float.NaN, Float.NaN)); // NaN != NaN
    assertFalse(MathEx.equal(Float.NaN, 1.0f));

    assertTrue(MathEx.equal(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    assertTrue(MathEx.equal(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertFalse(MathEx.equal(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    assertFalse(MathEx.equal(Double.NaN, Double.NaN)); // NaN != NaN
    assertFalse(MathEx.equal(Double.NaN, 1.0));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      数值限制测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testClampByte() {
    assertEquals((byte) 5, MathEx.clamp((byte) 5, (byte) 1, (byte) 10));
    assertEquals((byte) 1, MathEx.clamp((byte) 0, (byte) 1, (byte) 10));
    assertEquals((byte) 10, MathEx.clamp((byte) 15, (byte) 1, (byte) 10));
    assertEquals((byte) 1, MathEx.clamp((byte) 1, (byte) 1, (byte) 10));
    assertEquals((byte) 10, MathEx.clamp((byte) 10, (byte) 1, (byte) 10));

    // 测试负数范围
    assertEquals((byte) -5, MathEx.clamp((byte) -5, (byte) -10, (byte) -1));
    assertEquals((byte) -10, MathEx.clamp((byte) -15, (byte) -10, (byte) -1));
    assertEquals((byte) -1, MathEx.clamp((byte) 5, (byte) -10, (byte) -1));
  }

  @Test
  void testClampShort() {
    assertEquals((short) 5, MathEx.clamp((short) 5, (short) 1, (short) 10));
    assertEquals((short) 1, MathEx.clamp((short) 0, (short) 1, (short) 10));
    assertEquals((short) 10, MathEx.clamp((short) 15, (short) 1, (short) 10));
    assertEquals((short) 1, MathEx.clamp((short) 1, (short) 1, (short) 10));
    assertEquals((short) 10, MathEx.clamp((short) 10, (short) 1, (short) 10));

    // 测试负数范围
    assertEquals((short) -5, MathEx.clamp((short) -5, (short) -10, (short) -1));
    assertEquals((short) -10, MathEx.clamp((short) -15, (short) -10, (short) -1));
    assertEquals((short) -1, MathEx.clamp((short) 5, (short) -10, (short) -1));
  }

  @Test
  void testClampInt() {
    assertEquals(5, MathEx.clamp(5, 1, 10));
    assertEquals(1, MathEx.clamp(0, 1, 10));
    assertEquals(10, MathEx.clamp(15, 1, 10));
    assertEquals(1, MathEx.clamp(1, 1, 10));
    assertEquals(10, MathEx.clamp(10, 1, 10));

    // 测试负数范围
    assertEquals(-5, MathEx.clamp(-5, -10, -1));
    assertEquals(-10, MathEx.clamp(-15, -10, -1));
    assertEquals(-1, MathEx.clamp(5, -10, -1));

    // 测试极值
    assertEquals(Integer.MAX_VALUE, MathEx.clamp(Integer.MAX_VALUE, 0, Integer.MAX_VALUE));
    assertEquals(Integer.MIN_VALUE, MathEx.clamp(Integer.MIN_VALUE, Integer.MIN_VALUE, 0));
  }

  @Test
  void testClampLong() {
    assertEquals(5L, MathEx.clamp(5L, 1L, 10L));
    assertEquals(1L, MathEx.clamp(0L, 1L, 10L));
    assertEquals(10L, MathEx.clamp(15L, 1L, 10L));
    assertEquals(1L, MathEx.clamp(1L, 1L, 10L));
    assertEquals(10L, MathEx.clamp(10L, 1L, 10L));

    // 测试负数范围
    assertEquals(-5L, MathEx.clamp(-5L, -10L, -1L));
    assertEquals(-10L, MathEx.clamp(-15L, -10L, -1L));
    assertEquals(-1L, MathEx.clamp(5L, -10L, -1L));

    // 测试极值
    assertEquals(Long.MAX_VALUE, MathEx.clamp(Long.MAX_VALUE, 0L, Long.MAX_VALUE));
    assertEquals(Long.MIN_VALUE, MathEx.clamp(Long.MIN_VALUE, Long.MIN_VALUE, 0L));
  }

  @Test
  void testClampFloat() {
    assertEquals(5.0f, MathEx.clamp(5.0f, 1.0f, 10.0f));
    assertEquals(1.0f, MathEx.clamp(0.0f, 1.0f, 10.0f));
    assertEquals(10.0f, MathEx.clamp(15.0f, 1.0f, 10.0f));
    assertEquals(1.0f, MathEx.clamp(1.0f, 1.0f, 10.0f));
    assertEquals(10.0f, MathEx.clamp(10.0f, 1.0f, 10.0f));

    // 测试负数范围
    assertEquals(-5.0f, MathEx.clamp(-5.0f, -10.0f, -1.0f));
    assertEquals(-10.0f, MathEx.clamp(-15.0f, -10.0f, -1.0f));
    assertEquals(-1.0f, MathEx.clamp(5.0f, -10.0f, -1.0f));

    // 测试特殊值
    assertEquals(Float.MAX_VALUE, MathEx.clamp(Float.MAX_VALUE, 0.0f, Float.MAX_VALUE));
    assertTrue(Float.isNaN(MathEx.clamp(Float.NaN, 0.0f, 10.0f)));
  }

  @Test
  void testClampDouble() {
    assertEquals(5.0, MathEx.clamp(5.0, 1.0, 10.0));
    assertEquals(1.0, MathEx.clamp(0.0, 1.0, 10.0));
    assertEquals(10.0, MathEx.clamp(15.0, 1.0, 10.0));
    assertEquals(1.0, MathEx.clamp(1.0, 1.0, 10.0));
    assertEquals(10.0, MathEx.clamp(10.0, 1.0, 10.0));

    // 测试负数范围
    assertEquals(-5.0, MathEx.clamp(-5.0, -10.0, -1.0));
    assertEquals(-10.0, MathEx.clamp(-15.0, -10.0, -1.0));
    assertEquals(-1.0, MathEx.clamp(5.0, -10.0, -1.0));

    // 测试特殊值
    assertEquals(Double.MAX_VALUE, MathEx.clamp(Double.MAX_VALUE, 0.0, Double.MAX_VALUE));
    assertTrue(Double.isNaN(MathEx.clamp(Double.NaN, 0.0, 10.0)));
  }

  @Test
  void testClampPositive() {
    // 测试float类型
    assertEquals(5.0f, MathEx.clampPositive(5.0f));
    assertEquals(0.0f, MathEx.clampPositive(0.0f));
    assertEquals(0.0f, MathEx.clampPositive(-5.0f));
    assertEquals(Float.MAX_VALUE, MathEx.clampPositive(Float.MAX_VALUE));

    // 测试double类型
    assertEquals(5.0, MathEx.clampPositive(5.0));
    assertEquals(0.0, MathEx.clampPositive(0.0));
    assertEquals(0.0, MathEx.clampPositive(-5.0));
    assertEquals(Double.MAX_VALUE, MathEx.clampPositive(Double.MAX_VALUE));
  }

  @Test
  void testClampNegative() {
    // 测试float类型
    assertEquals(-5.0f, MathEx.clampNegative(-5.0f));
    assertEquals(0.0f, MathEx.clampNegative(0.0f));
    assertEquals(0.0f, MathEx.clampNegative(5.0f));
    assertEquals(-Float.MAX_VALUE, MathEx.clampNegative(-Float.MAX_VALUE));

    // 测试double类型
    assertEquals(-5.0, MathEx.clampNegative(-5.0));
    assertEquals(0.0, MathEx.clampNegative(0.0));
    assertEquals(0.0, MathEx.clampNegative(5.0));
    assertEquals(-Double.MAX_VALUE, MathEx.clampNegative(-Double.MAX_VALUE));
  }

  @Test
  void testClampEdgeCases() {
    // 测试相等的上下界
    assertEquals(5, MathEx.clamp(3, 5, 5));
    assertEquals(5, MathEx.clamp(7, 5, 5));
    assertEquals(5, MathEx.clamp(5, 5, 5));

    assertEquals(5.0f, MathEx.clamp(3.0f, 5.0f, 5.0f));
    assertEquals(5.0f, MathEx.clamp(7.0f, 5.0f, 5.0f));
    assertEquals(5.0f, MathEx.clamp(5.0f, 5.0f, 5.0f));

    assertEquals(5.0, MathEx.clamp(3.0, 5.0, 5.0));
    assertEquals(5.0, MathEx.clamp(7.0, 5.0, 5.0));
    assertEquals(5.0, MathEx.clamp(5.0, 5.0, 5.0));
  }
} 