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
 * {@link MathEx}类中浮点数特殊操作和高精度计算相关方法的单元测试。
 * 包括符号函数、浮点数操作、融合乘加运算、高精度计算等。
 *
 * @author Haixing Hu
 */
public class MathExFloatingPointTest {

  private static final double DELTA = 1e-10;
  private static final float FLOAT_DELTA = 1e-6f;

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      符号和拷贝符号测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testSignumFloat() {
    // 测试正数
    assertEquals(1.0f, MathEx.signum(5.0f));
    assertEquals(1.0f, MathEx.signum(Float.MAX_VALUE));
    assertEquals(1.0f, MathEx.signum(Float.MIN_VALUE));
    assertEquals(1.0f, MathEx.signum(Float.POSITIVE_INFINITY));

    // 测试负数
    assertEquals(-1.0f, MathEx.signum(-5.0f));
    assertEquals(-1.0f, MathEx.signum(-Float.MAX_VALUE));
    assertEquals(-1.0f, MathEx.signum(-Float.MIN_VALUE));
    assertEquals(-1.0f, MathEx.signum(Float.NEGATIVE_INFINITY));

    // 测试零（注意Java中的-0.0f和0.0f在signum中的不同表现）
    assertEquals(0.0f, MathEx.signum(0.0f));
    assertEquals(-0.0f, MathEx.signum(-0.0f));

    // 测试NaN
    assertTrue(Float.isNaN(MathEx.signum(Float.NaN)));
  }

  @Test
  void testSignumDouble() {
    // 测试正数
    assertEquals(1.0, MathEx.signum(5.0));
    assertEquals(1.0, MathEx.signum(Double.MAX_VALUE));
    assertEquals(1.0, MathEx.signum(Double.MIN_VALUE));
    assertEquals(1.0, MathEx.signum(Double.POSITIVE_INFINITY));

    // 测试负数
    assertEquals(-1.0, MathEx.signum(-5.0));
    assertEquals(-1.0, MathEx.signum(-Double.MAX_VALUE));
    assertEquals(-1.0, MathEx.signum(-Double.MIN_VALUE));
    assertEquals(-1.0, MathEx.signum(Double.NEGATIVE_INFINITY));

    // 测试零（注意Java中的-0.0和0.0在signum中的不同表现）
    assertEquals(0.0, MathEx.signum(0.0));
    assertEquals(-0.0, MathEx.signum(-0.0));

    // 测试NaN
    assertTrue(Double.isNaN(MathEx.signum(Double.NaN)));
  }

  @Test
  void testCopySignFloat() {
    // 测试正数符号
    assertEquals(5.0f, MathEx.copySign(5.0f, 1.0f));
    assertEquals(5.0f, MathEx.copySign(-5.0f, 1.0f));
    assertEquals(5.0f, MathEx.copySign(5.0f, Float.POSITIVE_INFINITY));

    // 测试负数符号
    assertEquals(-5.0f, MathEx.copySign(5.0f, -1.0f));
    assertEquals(-5.0f, MathEx.copySign(-5.0f, -1.0f));
    assertEquals(-5.0f, MathEx.copySign(5.0f, Float.NEGATIVE_INFINITY));

    // 测试零符号
    assertEquals(5.0f, MathEx.copySign(5.0f, 0.0f));
    assertEquals(-5.0f, MathEx.copySign(5.0f, -0.0f));

    // 测试特殊值
    assertEquals(Float.POSITIVE_INFINITY, MathEx.copySign(Float.POSITIVE_INFINITY, 1.0f));
    assertEquals(Float.NEGATIVE_INFINITY, MathEx.copySign(Float.POSITIVE_INFINITY, -1.0f));
    assertTrue(Float.isNaN(MathEx.copySign(Float.NaN, 1.0f)));
  }

  @Test
  void testCopySignDouble() {
    // 测试正数符号
    assertEquals(5.0, MathEx.copySign(5.0, 1.0));
    assertEquals(5.0, MathEx.copySign(-5.0, 1.0));
    assertEquals(5.0, MathEx.copySign(5.0, Double.POSITIVE_INFINITY));

    // 测试负数符号
    assertEquals(-5.0, MathEx.copySign(5.0, -1.0));
    assertEquals(-5.0, MathEx.copySign(-5.0, -1.0));
    assertEquals(-5.0, MathEx.copySign(5.0, Double.NEGATIVE_INFINITY));

    // 测试零符号
    assertEquals(5.0, MathEx.copySign(5.0, 0.0));
    assertEquals(-5.0, MathEx.copySign(5.0, -0.0));

    // 测试特殊值
    assertEquals(Double.POSITIVE_INFINITY, MathEx.copySign(Double.POSITIVE_INFINITY, 1.0));
    assertEquals(Double.NEGATIVE_INFINITY, MathEx.copySign(Double.POSITIVE_INFINITY, -1.0));
    assertTrue(Double.isNaN(MathEx.copySign(Double.NaN, 1.0)));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      指数和ULP测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testGetExponentFloat() {
    // 测试正常值
    assertEquals(2, MathEx.getExponent(4.0f)); // 4 = 2^2 * 1.0
    assertEquals(3, MathEx.getExponent(8.0f)); // 8 = 2^3 * 1.0
    assertEquals(0, MathEx.getExponent(1.0f)); // 1 = 2^0 * 1.0
    assertEquals(-1, MathEx.getExponent(0.5f)); // 0.5 = 2^(-1) * 1.0

    // 测试特殊值
    assertEquals(Float.MAX_EXPONENT + 1, MathEx.getExponent(Float.POSITIVE_INFINITY));
    assertEquals(Float.MAX_EXPONENT + 1, MathEx.getExponent(Float.NEGATIVE_INFINITY));
    assertEquals(Float.MAX_EXPONENT + 1, MathEx.getExponent(Float.NaN));
    assertEquals(Float.MIN_EXPONENT - 1, MathEx.getExponent(0.0f));
  }

  @Test
  void testGetExponentDouble() {
    // 测试正常值
    assertEquals(2, MathEx.getExponent(4.0)); // 4 = 2^2 * 1.0
    assertEquals(3, MathEx.getExponent(8.0)); // 8 = 2^3 * 1.0
    assertEquals(0, MathEx.getExponent(1.0)); // 1 = 2^0 * 1.0
    assertEquals(-1, MathEx.getExponent(0.5)); // 0.5 = 2^(-1) * 1.0

    // 测试特殊值
    assertEquals(Double.MAX_EXPONENT + 1, MathEx.getExponent(Double.POSITIVE_INFINITY));
    assertEquals(Double.MAX_EXPONENT + 1, MathEx.getExponent(Double.NEGATIVE_INFINITY));
    assertEquals(Double.MAX_EXPONENT + 1, MathEx.getExponent(Double.NaN));
    assertEquals(Double.MIN_EXPONENT - 1, MathEx.getExponent(0.0));
  }

  @Test
  void testUlpFloat() {
    // 测试ULP（最小精度单位）
    assertTrue(MathEx.ulp(1.0f) > 0);
    assertTrue(MathEx.ulp(-1.0f) > 0);
    assertTrue(MathEx.ulp(Float.MAX_VALUE) > 0);
    assertEquals(Float.MIN_VALUE, MathEx.ulp(0.0f));

    // 测试特殊值
    assertEquals(Float.POSITIVE_INFINITY, MathEx.ulp(Float.POSITIVE_INFINITY));
    assertEquals(Float.POSITIVE_INFINITY, MathEx.ulp(Float.NEGATIVE_INFINITY));
    assertTrue(Float.isNaN(MathEx.ulp(Float.NaN)));
  }

  @Test
  void testUlpDouble() {
    // 测试ULP（最小精度单位）
    assertTrue(MathEx.ulp(1.0) > 0);
    assertTrue(MathEx.ulp(-1.0) > 0);
    assertTrue(MathEx.ulp(Double.MAX_VALUE) > 0);
    assertEquals(Double.MIN_VALUE, MathEx.ulp(0.0));

    // 测试特殊值
    assertEquals(Double.POSITIVE_INFINITY, MathEx.ulp(Double.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.ulp(Double.NEGATIVE_INFINITY));
    assertTrue(Double.isNaN(MathEx.ulp(Double.NaN)));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      相邻浮点值测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testNextAfterFloat() {
    // 测试向正方向移动
    float result = MathEx.nextAfter(1.0f, 2.0);
    assertTrue(result > 1.0f);
    assertTrue(result < 1.0f + MathEx.ulp(1.0f) * 2);

    // 测试向负方向移动
    result = MathEx.nextAfter(1.0f, 0.0);
    assertTrue(result < 1.0f);
    assertTrue(result > 1.0f - MathEx.ulp(1.0f) * 2);

    // 测试相等情况
    assertEquals(1.0f, MathEx.nextAfter(1.0f, 1.0));

    // 测试特殊值
    assertTrue(Float.isNaN(MathEx.nextAfter(Float.NaN, 1.0)));
    assertTrue(Float.isNaN(MathEx.nextAfter(1.0f, Double.NaN)));
  }

  @Test
  void testNextAfterDouble() {
    // 测试向正方向移动
    double result = MathEx.nextAfter(1.0, 2.0);
    assertTrue(result > 1.0);
    assertTrue(result < 1.0 + MathEx.ulp(1.0) * 2);

    // 测试向负方向移动
    result = MathEx.nextAfter(1.0, 0.0);
    assertTrue(result < 1.0);
    assertTrue(result > 1.0 - MathEx.ulp(1.0) * 2);

    // 测试相等情况
    assertEquals(1.0, MathEx.nextAfter(1.0, 1.0));

    // 测试特殊值
    assertTrue(Double.isNaN(MathEx.nextAfter(Double.NaN, 1.0)));
    assertTrue(Double.isNaN(MathEx.nextAfter(1.0, Double.NaN)));
  }

  @Test
  void testNextUpFloat() {
    // 测试向上移动
    float result = MathEx.nextUp(1.0f);
    assertTrue(result > 1.0f);
    assertEquals(1.0f + MathEx.ulp(1.0f), result);

    // 测试零
    assertEquals(Float.MIN_VALUE, MathEx.nextUp(0.0f));
    assertEquals(Float.MIN_VALUE, MathEx.nextUp(-0.0f));

    // 测试特殊值
    assertEquals(Float.POSITIVE_INFINITY, MathEx.nextUp(Float.MAX_VALUE));
    assertEquals(Float.POSITIVE_INFINITY, MathEx.nextUp(Float.POSITIVE_INFINITY));
    assertTrue(Float.isNaN(MathEx.nextUp(Float.NaN)));
  }

  @Test
  void testNextUpDouble() {
    // 测试向上移动
    double result = MathEx.nextUp(1.0);
    assertTrue(result > 1.0);
    assertEquals(1.0 + MathEx.ulp(1.0), result);

    // 测试零
    assertEquals(Double.MIN_VALUE, MathEx.nextUp(0.0));
    assertEquals(Double.MIN_VALUE, MathEx.nextUp(-0.0));

    // 测试特殊值
    assertEquals(Double.POSITIVE_INFINITY, MathEx.nextUp(Double.MAX_VALUE));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.nextUp(Double.POSITIVE_INFINITY));
    assertTrue(Double.isNaN(MathEx.nextUp(Double.NaN)));
  }

  @Test
  void testNextDownFloat() {
    // 测试向下移动
    float result = MathEx.nextDown(1.0f);
    assertTrue(result < 1.0f);

    // 测试零
    assertEquals(-Float.MIN_VALUE, MathEx.nextDown(0.0f));
    assertEquals(-Float.MIN_VALUE, MathEx.nextDown(-0.0f));

    // 测试特殊值
    assertEquals(Float.NEGATIVE_INFINITY, MathEx.nextDown(-Float.MAX_VALUE));
    assertEquals(Float.NEGATIVE_INFINITY, MathEx.nextDown(Float.NEGATIVE_INFINITY));
    assertTrue(Float.isNaN(MathEx.nextDown(Float.NaN)));
  }

  @Test
  void testNextDownDouble() {
    // 测试向下移动
    double result = MathEx.nextDown(1.0);
    assertTrue(result < 1.0);

    // 测试零
    assertEquals(-Double.MIN_VALUE, MathEx.nextDown(0.0));
    assertEquals(-Double.MIN_VALUE, MathEx.nextDown(-0.0));

    // 测试特殊值
    assertEquals(Double.NEGATIVE_INFINITY, MathEx.nextDown(-Double.MAX_VALUE));
    assertEquals(Double.NEGATIVE_INFINITY, MathEx.nextDown(Double.NEGATIVE_INFINITY));
    assertTrue(Double.isNaN(MathEx.nextDown(Double.NaN)));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      高精度计算测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testFmaFloat() {
    // 测试融合乘加运算 (a * b + c)
    assertEquals(17.0f, MathEx.fma(3.0f, 4.0f, 5.0f), FLOAT_DELTA); // 3*4+5=17
    assertEquals(1.0f, MathEx.fma(2.0f, 0.5f, 0.0f), FLOAT_DELTA); // 2*0.5+0=1
    assertEquals(0.0f, MathEx.fma(0.0f, Float.MAX_VALUE, 0.0f), FLOAT_DELTA);

    // 测试特殊值
    assertTrue(Float.isNaN(MathEx.fma(Float.NaN, 1.0f, 1.0f)));
    assertTrue(Float.isNaN(MathEx.fma(1.0f, Float.NaN, 1.0f)));
    assertTrue(Float.isNaN(MathEx.fma(1.0f, 1.0f, Float.NaN)));
    assertEquals(Float.POSITIVE_INFINITY, MathEx.fma(Float.MAX_VALUE, 2.0f, 0.0f));
  }

  @Test
  void testFmaDouble() {
    // 测试融合乘加运算 (a * b + c)
    assertEquals(17.0, MathEx.fma(3.0, 4.0, 5.0), DELTA); // 3*4+5=17
    assertEquals(1.0, MathEx.fma(2.0, 0.5, 0.0), DELTA); // 2*0.5+0=1
    assertEquals(0.0, MathEx.fma(0.0, Double.MAX_VALUE, 0.0), DELTA);

    // 测试特殊值
    assertTrue(Double.isNaN(MathEx.fma(Double.NaN, 1.0, 1.0)));
    assertTrue(Double.isNaN(MathEx.fma(1.0, Double.NaN, 1.0)));
    assertTrue(Double.isNaN(MathEx.fma(1.0, 1.0, Double.NaN)));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.fma(Double.MAX_VALUE, 2.0, 0.0));
  }

  @Test
  void testMultiplyAddFloat() {
    // 测试multiplyAdd方法（fma的别名）
    assertEquals(17.0f, MathEx.multiplyAdd(3.0f, 4.0f, 5.0f), FLOAT_DELTA);
    assertEquals(1.0f, MathEx.multiplyAdd(2.0f, 0.5f, 0.0f), FLOAT_DELTA);
    assertEquals(-1.0f, MathEx.multiplyAdd(2.0f, -0.5f, 0.0f), FLOAT_DELTA);
  }

  @Test
  void testMultiplyAddDouble() {
    // 测试multiplyAdd方法（fma的别名）
    assertEquals(17.0, MathEx.multiplyAdd(3.0, 4.0, 5.0), DELTA);
    assertEquals(1.0, MathEx.multiplyAdd(2.0, 0.5, 0.0), DELTA);
    assertEquals(-1.0, MathEx.multiplyAdd(2.0, -0.5, 0.0), DELTA);
  }

  @Test
  void testScalbFloat() {
    // 测试scalb函数 (f * 2^scaleFactor)
    assertEquals(8.0f, MathEx.scalb(2.0f, 2), FLOAT_DELTA); // 2 * 2^2 = 8
    assertEquals(1.0f, MathEx.scalb(2.0f, -1), FLOAT_DELTA); // 2 * 2^(-1) = 1
    assertEquals(0.0f, MathEx.scalb(0.0f, 100), FLOAT_DELTA);

    // 测试特殊值
    assertEquals(Float.POSITIVE_INFINITY, MathEx.scalb(Float.POSITIVE_INFINITY, 1));
    assertEquals(Float.NEGATIVE_INFINITY, MathEx.scalb(Float.NEGATIVE_INFINITY, 1));
    assertTrue(Float.isNaN(MathEx.scalb(Float.NaN, 1)));
  }

  @Test
  void testScalbDouble() {
    // 测试scalb函数 (d * 2^scaleFactor)
    assertEquals(8.0, MathEx.scalb(2.0, 2), DELTA); // 2 * 2^2 = 8
    assertEquals(1.0, MathEx.scalb(2.0, -1), DELTA); // 2 * 2^(-1) = 1
    assertEquals(0.0, MathEx.scalb(0.0, 100), DELTA);

    // 测试特殊值
    assertEquals(Double.POSITIVE_INFINITY, MathEx.scalb(Double.POSITIVE_INFINITY, 1));
    assertEquals(Double.NEGATIVE_INFINITY, MathEx.scalb(Double.NEGATIVE_INFINITY, 1));
    assertTrue(Double.isNaN(MathEx.scalb(Double.NaN, 1)));
  }

  @Test
  void testHypot() {
    // 测试欧几里得距离 sqrt(x² + y²)
    assertEquals(5.0, MathEx.hypot(3.0, 4.0), DELTA); // 3-4-5直角三角形
    assertEquals(Math.sqrt(2), MathEx.hypot(1.0, 1.0), DELTA);
    assertEquals(0.0, MathEx.hypot(0.0, 0.0), DELTA);
    assertEquals(5.0, MathEx.hypot(-3.0, 4.0), DELTA); // 负数
    assertEquals(5.0, MathEx.hypot(3.0, -4.0), DELTA); // 负数

    // 测试特殊值
    assertEquals(Double.POSITIVE_INFINITY, MathEx.hypot(Double.POSITIVE_INFINITY, 1.0));
    assertEquals(Double.POSITIVE_INFINITY, MathEx.hypot(1.0, Double.NEGATIVE_INFINITY));
    assertTrue(Double.isNaN(MathEx.hypot(Double.NaN, 1.0)));
    assertTrue(Double.isNaN(MathEx.hypot(1.0, Double.NaN)));
  }

  @Test
  void testSquaredSumFloat() {
    // 测试平方和 x² + y²
    assertEquals(25.0f, MathEx.squaredSum(3.0f, 4.0f), FLOAT_DELTA); // 3² + 4² = 9 + 16 = 25
    assertEquals(2.0f, MathEx.squaredSum(1.0f, 1.0f), FLOAT_DELTA); // 1² + 1² = 2
    assertEquals(0.0f, MathEx.squaredSum(0.0f, 0.0f), FLOAT_DELTA);
    assertEquals(25.0f, MathEx.squaredSum(-3.0f, 4.0f), FLOAT_DELTA); // (-3)² + 4² = 25
    assertEquals(25.0f, MathEx.squaredSum(3.0f, -4.0f), FLOAT_DELTA); // 3² + (-4)² = 25

    // 测试特殊值
    assertEquals(Float.POSITIVE_INFINITY, MathEx.squaredSum(Float.MAX_VALUE, Float.MAX_VALUE));
    assertTrue(Float.isNaN(MathEx.squaredSum(Float.NaN, 1.0f)));
    assertTrue(Float.isNaN(MathEx.squaredSum(1.0f, Float.NaN)));
  }

  @Test
  void testSquaredSumDouble() {
    // 测试平方和 x² + y²
    assertEquals(25.0, MathEx.squaredSum(3.0, 4.0), DELTA); // 3² + 4² = 9 + 16 = 25
    assertEquals(2.0, MathEx.squaredSum(1.0, 1.0), DELTA); // 1² + 1² = 2
    assertEquals(0.0, MathEx.squaredSum(0.0, 0.0), DELTA);
    assertEquals(25.0, MathEx.squaredSum(-3.0, 4.0), DELTA); // (-3)² + 4² = 25
    assertEquals(25.0, MathEx.squaredSum(3.0, -4.0), DELTA); // 3² + (-4)² = 25

    // 测试特殊值
    assertEquals(Double.POSITIVE_INFINITY, MathEx.squaredSum(Double.MAX_VALUE, Double.MAX_VALUE));
    assertTrue(Double.isNaN(MathEx.squaredSum(Double.NaN, 1.0)));
    assertTrue(Double.isNaN(MathEx.squaredSum(1.0, Double.NaN)));
  }

  @Test
  void testHighPrecisionAccuracy() {
    // 测试高精度计算的准确性
    // 这里测试一些容易产生舍入误差的计算
    
    // 测试FMA相比普通运算的精度提升
    double a = 1e16;
    double b = 1e-16;
    double c = 1.0;
    
    // 使用FMA应该更精确
    double fmaResult = MathEx.fma(a, b, c);
    double normalResult = a * b + c;
    
    // 在这种情况下，两种计算应该都能给出正确结果2.0
    assertEquals(2.0, fmaResult, DELTA);
    assertEquals(2.0, normalResult, DELTA);
    
    // 测试scalb的精度
    double value = 1.5;
    double scaled = MathEx.scalb(value, 10);
    double expected = value * Math.pow(2, 10);
    assertEquals(expected, scaled, DELTA);
  }
} 