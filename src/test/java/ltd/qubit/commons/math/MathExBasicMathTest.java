////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MathEx}类中基础数学运算方法的单元测试。
 * 包括三角函数、双曲函数、角度转换、指数对数、幂和开方、取整、除法余数等。
 *
 * @author Haixing Hu
 */
public class MathExBasicMathTest {

  private static final double DELTA = 1e-10;

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      三角函数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testTrigonometricFunctions() {
    // 测试正弦函数
    assertEquals(0.0, MathEx.sin(0), DELTA);
    assertEquals(1.0, MathEx.sin(Math.PI / 2), DELTA);
    assertEquals(0.0, MathEx.sin(Math.PI), DELTA);
    assertEquals(-1.0, MathEx.sin(3 * Math.PI / 2), DELTA);

    // 测试余弦函数
    assertEquals(1.0, MathEx.cos(0), DELTA);
    assertEquals(0.0, MathEx.cos(Math.PI / 2), DELTA);
    assertEquals(-1.0, MathEx.cos(Math.PI), DELTA);
    assertEquals(0.0, MathEx.cos(3 * Math.PI / 2), DELTA);

    // 测试正切函数
    assertEquals(0.0, MathEx.tan(0), DELTA);
    assertEquals(1.0, MathEx.tan(Math.PI / 4), DELTA);
    assertEquals(0.0, MathEx.tan(Math.PI), DELTA);

    // 测试反三角函数
    assertEquals(0.0, MathEx.asin(0), DELTA);
    assertEquals(Math.PI / 2, MathEx.asin(1), DELTA);
    assertEquals(-Math.PI / 2, MathEx.asin(-1), DELTA);

    assertEquals(Math.PI / 2, MathEx.acos(0), DELTA);
    assertEquals(0.0, MathEx.acos(1), DELTA);
    assertEquals(Math.PI, MathEx.acos(-1), DELTA);

    assertEquals(0.0, MathEx.atan(0), DELTA);
    assertEquals(Math.PI / 4, MathEx.atan(1), DELTA);
    assertEquals(-Math.PI / 4, MathEx.atan(-1), DELTA);

    // 测试atan2函数
    assertEquals(0.0, MathEx.atan2(0, 1), DELTA);
    assertEquals(Math.PI / 2, MathEx.atan2(1, 0), DELTA);
    assertEquals(Math.PI, MathEx.atan2(0, -1), DELTA);
    assertEquals(-Math.PI / 2, MathEx.atan2(-1, 0), DELTA);
  }

  @Test
  void testHyperbolicFunctions() {
    // 测试双曲正弦函数
    assertEquals(0.0, MathEx.sinh(0), DELTA);
    assertTrue(MathEx.sinh(1) > 0);
    assertTrue(MathEx.sinh(-1) < 0);

    // 测试双曲余弦函数
    assertEquals(1.0, MathEx.cosh(0), DELTA);
    assertTrue(MathEx.cosh(1) > 1);
    assertTrue(MathEx.cosh(-1) > 1);

    // 测试双曲正切函数
    assertEquals(0.0, MathEx.tanh(0), DELTA);
    assertTrue(MathEx.tanh(1) > 0 && MathEx.tanh(1) < 1);
    assertTrue(MathEx.tanh(-1) < 0 && MathEx.tanh(-1) > -1);
  }

  @Test
  void testAngleConversion() {
    // 测试角度转弧度
    assertEquals(0.0, MathEx.toRadians(0), DELTA);
    assertEquals(Math.PI / 2, MathEx.toRadians(90), DELTA);
    assertEquals(Math.PI, MathEx.toRadians(180), DELTA);
    assertEquals(2 * Math.PI, MathEx.toRadians(360), DELTA);

    // 测试弧度转角度
    assertEquals(0.0, MathEx.toDegrees(0), DELTA);
    assertEquals(90.0, MathEx.toDegrees(Math.PI / 2), DELTA);
    assertEquals(180.0, MathEx.toDegrees(Math.PI), DELTA);
    assertEquals(360.0, MathEx.toDegrees(2 * Math.PI), DELTA);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      指数和对数函数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testExponentialFunctions() {
    // 测试自然指数函数
    assertEquals(1.0, MathEx.exp(0), DELTA);
    assertEquals(MathEx.E, MathEx.exp(1), DELTA);
    assertTrue(MathEx.exp(2) > MathEx.E);

    // 测试expm1函数
    assertEquals(0.0, MathEx.expm1(0), DELTA);
    assertEquals(MathEx.E - 1, MathEx.expm1(1), DELTA);
  }

  @Test
  void testLogarithmicFunctions() {
    // 测试自然对数
    assertEquals(0.0, MathEx.log(1), DELTA);
    assertEquals(1.0, MathEx.log(MathEx.E), DELTA);
    assertEquals(2.0, MathEx.log(MathEx.E * MathEx.E), DELTA);

    // 测试以2为底的对数
    assertEquals(0.0, MathEx.log2(1), DELTA);
    assertEquals(1.0, MathEx.log2(2), DELTA);
    assertEquals(2.0, MathEx.log2(4), DELTA);
    assertEquals(3.0, MathEx.log2(8), DELTA);
    assertEquals(10.0, MathEx.log2(1024), DELTA);

    // 测试常用对数（以10为底）
    assertEquals(0.0, MathEx.log10(1), DELTA);
    assertEquals(1.0, MathEx.log10(10), DELTA);
    assertEquals(2.0, MathEx.log10(100), DELTA);
    assertEquals(3.0, MathEx.log10(1000), DELTA);

    // 测试log1p函数
    assertEquals(0.0, MathEx.log1p(0), DELTA);
    assertEquals(MathEx.log(2), MathEx.log1p(1), DELTA);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      幂和开方函数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testPowerFunctions() {
    // 测试通用幂函数
    assertEquals(1.0, MathEx.pow(2, 0), DELTA);
    assertEquals(2.0, MathEx.pow(2, 1), DELTA);
    assertEquals(4.0, MathEx.pow(2, 2), DELTA);
    assertEquals(8.0, MathEx.pow(2, 3), DELTA);
    assertEquals(0.5, MathEx.pow(2, -1), DELTA);
    assertEquals(1.0, MathEx.pow(5, 0), DELTA);

    // 测试2的幂
    assertEquals(1L, MathEx.pow2(0));
    assertEquals(2L, MathEx.pow2(1));
    assertEquals(4L, MathEx.pow2(2));
    assertEquals(8L, MathEx.pow2(3));
    assertEquals(1024L, MathEx.pow2(10));
    assertEquals(1L << 62, MathEx.pow2(62));

    // 测试10的幂
    assertEquals(1L, MathEx.pow10(0));
    assertEquals(10L, MathEx.pow10(1));
    assertEquals(100L, MathEx.pow10(2));
    assertEquals(1000L, MathEx.pow10(3));
    assertEquals(1000000L, MathEx.pow10(6));
    assertEquals(1000000000000000000L, MathEx.pow10(18));
  }

  @Test
  void testPowerEdgeCases() {
    // 测试pow2的边界情况
    assertThrows(IllegalArgumentException.class, () -> MathEx.pow2(-1));
    assertThrows(IllegalArgumentException.class, () -> MathEx.pow2(63));

    // 测试pow10的边界情况
    assertThrows(IllegalArgumentException.class, () -> MathEx.pow10(-1));
    assertThrows(IllegalArgumentException.class, () -> MathEx.pow10(19));
  }

  @Test
  void testRootFunctions() {
    // 测试平方根
    assertEquals(0.0, MathEx.sqrt(0), DELTA);
    assertEquals(1.0, MathEx.sqrt(1), DELTA);
    assertEquals(2.0, MathEx.sqrt(4), DELTA);
    assertEquals(3.0, MathEx.sqrt(9), DELTA);
    assertEquals(10.0, MathEx.sqrt(100), DELTA);

    // 测试立方根
    assertEquals(0.0, MathEx.cbrt(0), DELTA);
    assertEquals(1.0, MathEx.cbrt(1), DELTA);
    assertEquals(2.0, MathEx.cbrt(8), DELTA);
    assertEquals(3.0, MathEx.cbrt(27), DELTA);
    assertEquals(-2.0, MathEx.cbrt(-8), DELTA);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      取整函数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testCeilingFunctions() {
    // 测试float向上取整
    assertEquals(1.0f, MathEx.ceil(0.1f));
    assertEquals(1.0f, MathEx.ceil(1.0f));
    assertEquals(2.0f, MathEx.ceil(1.1f));
    assertEquals(-1.0f, MathEx.ceil(-1.9f));
    assertEquals(-0.0f, MathEx.ceil(-0.1f));

    // 测试double向上取整
    assertEquals(1.0, MathEx.ceil(0.1));
    assertEquals(1.0, MathEx.ceil(1.0));
    assertEquals(2.0, MathEx.ceil(1.1));
    assertEquals(-1.0, MathEx.ceil(-1.9));
    assertEquals(-0.0, MathEx.ceil(-0.1));
  }

  @Test
  void testFloorFunctions() {
    // 测试float向下取整
    assertEquals(0.0f, MathEx.floor(0.9f));
    assertEquals(1.0f, MathEx.floor(1.0f));
    assertEquals(1.0f, MathEx.floor(1.9f));
    assertEquals(-2.0f, MathEx.floor(-1.1f));
    assertEquals(-1.0f, MathEx.floor(-0.1f));

    // 测试double向下取整
    assertEquals(0.0, MathEx.floor(0.9));
    assertEquals(1.0, MathEx.floor(1.0));
    assertEquals(1.0, MathEx.floor(1.9));
    assertEquals(-2.0, MathEx.floor(-1.1));
    assertEquals(-1.0, MathEx.floor(-0.1));
  }

  @Test
  void testRintFunctions() {
    // 测试float最近整数
    assertEquals(0.0f, MathEx.rint(0.4f));
    assertEquals(1.0f, MathEx.rint(0.6f));
    assertEquals(2.0f, MathEx.rint(2.5f)); // 银行家舍入
    assertEquals(4.0f, MathEx.rint(3.5f)); // 银行家舍入

    // 测试double最近整数
    assertEquals(0.0, MathEx.rint(0.4));
    assertEquals(1.0, MathEx.rint(0.6));
    assertEquals(2.0, MathEx.rint(2.5)); // 银行家舍入
    assertEquals(4.0, MathEx.rint(3.5)); // 银行家舍入
  }

  @Test
  void testRoundFunctions() {
    // 测试float四舍五入
    assertEquals(0, MathEx.round(0.4f));
    assertEquals(1, MathEx.round(0.6f));
    assertEquals(3, MathEx.round(2.5f));
    assertEquals(4, MathEx.round(3.5f));
    assertEquals(-2, MathEx.round(-2.5f));

    // 测试double四舍五入
    assertEquals(0L, MathEx.round(0.4));
    assertEquals(1L, MathEx.round(0.6));
    assertEquals(3L, MathEx.round(2.5));
    assertEquals(4L, MathEx.round(3.5));
    assertEquals(-2L, MathEx.round(-2.5));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      除法余数函数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testIEEERemainder() {
    // 测试float IEEE余数
    assertEquals(0.0f, MathEx.IEEEremainder(5.0f, 2.5f), (float) DELTA);
    assertEquals(1.0f, MathEx.IEEEremainder(5.0f, 2.0f), (float) DELTA);

    // 测试double IEEE余数
    assertEquals(0.0, MathEx.IEEEremainder(5.0, 2.5), DELTA);
    assertEquals(1.0, MathEx.IEEEremainder(5.0, 2.0), DELTA);
  }

  @Test
  void testFloorDivMod() {
    // 测试向下取整除法
    assertEquals(2, MathEx.floorDiv(7, 3));
    assertEquals(-3, MathEx.floorDiv(-7, 3));
    assertEquals(-3, MathEx.floorDiv(7, -3));
    assertEquals(2, MathEx.floorDiv(-7, -3));

    assertEquals(2L, MathEx.floorDiv(7L, 3));
    assertEquals(-3L, MathEx.floorDiv(-7L, 3));
    assertEquals(2L, MathEx.floorDiv(7L, 3L));
    assertEquals(-3L, MathEx.floorDiv(-7L, 3L));

    // 测试向下取整模
    assertEquals(1, MathEx.floorMod(7, 3));
    assertEquals(2, MathEx.floorMod(-7, 3));
    assertEquals(-2, MathEx.floorMod(7, -3));
    assertEquals(-1, MathEx.floorMod(-7, -3));

    assertEquals(1, MathEx.floorMod(7L, 3));
    assertEquals(2, MathEx.floorMod(-7L, 3));
    assertEquals(1L, MathEx.floorMod(7L, 3L));
    assertEquals(2L, MathEx.floorMod(-7L, 3L));
  }

  @Test
  void testMathConstants() {
    // 测试数学常量
    assertEquals(Math.E, MathEx.E, DELTA);
    assertEquals(Math.PI, MathEx.PI, DELTA);
    assertEquals(Math.log(2), MathEx.LOG_OF_2, DELTA);
  }
}