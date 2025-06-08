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
 * {@link MathEx}类中随机数生成相关方法的单元测试。
 * 包括各种类型的随机数生成和范围控制。
 *
 * @author Haixing Hu
 */
public class MathExRandomTest {

  private static final int TEST_ITERATIONS = 1000;

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      基础随机数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testRandom() {
    // 测试基础random()方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      double value = MathEx.random();
      assertTrue(value >= 0.0, "随机数应该大于等于0.0");
      assertTrue(value < 1.0, "随机数应该小于1.0");
    }
  }

  @Test
  void testRandomWithRange() {
    double lowerBound = 5.0;
    double upperBound = 10.0;

    // 测试指定范围的random方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      double value = MathEx.random(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomWithNegativeRange() {
    double lowerBound = -10.0;
    double upperBound = -5.0;

    // 测试负数范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      double value = MathEx.random(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomCrossZeroRange() {
    double lowerBound = -5.0;
    double upperBound = 5.0;

    // 测试跨越零的范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      double value = MathEx.random(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      byte类型随机数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testRandomByte() {
    // 测试无参数的randomByte方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      byte value = MathEx.randomByte();
      // byte值的范围是-128到127，所以任何值都是有效的
      assertTrue(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE);
    }
  }

  @Test
  void testRandomByteWithUpperBound() {
    byte upperBound = 10;

    // 测试带上界的randomByte方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      byte value = MathEx.randomByte(upperBound);
      assertTrue(value >= 0, "随机数应该大于等于0: " + value);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomByteWithRange() {
    byte lowerBound = 5;
    byte upperBound = 15;

    // 测试指定范围的randomByte方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      byte value = MathEx.randomByte(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomByteNegativeRange() {
    byte lowerBound = -15;
    byte upperBound = -5;

    // 测试负数范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      byte value = MathEx.randomByte(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      short类型随机数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testRandomShort() {
    // 测试无参数的randomShort方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      short value = MathEx.randomShort();
      // short值的范围是-32768到32767，所以任何值都是有效的
      assertTrue(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE);
    }
  }

  @Test
  void testRandomShortWithUpperBound() {
    short upperBound = 100;

    // 测试带上界的randomShort方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      short value = MathEx.randomShort(upperBound);
      assertTrue(value >= 0, "随机数应该大于等于0: " + value);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomShortWithRange() {
    short lowerBound = 50;
    short upperBound = 150;

    // 测试指定范围的randomShort方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      short value = MathEx.randomShort(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomShortNegativeRange() {
    short lowerBound = -150;
    short upperBound = -50;

    // 测试负数范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      short value = MathEx.randomShort(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      int类型随机数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testRandomInt() {
    // 测试无参数的randomInt方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      int value = MathEx.randomInt();
      // int值的范围是-2^31到2^31-1，所以任何值都是有效的
      assertTrue(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE);
    }
  }

  @Test
  void testRandomIntWithUpperBound() {
    int upperBound = 1000;

    // 测试带上界的randomInt方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      int value = MathEx.randomInt(upperBound);
      assertTrue(value >= 0, "随机数应该大于等于0: " + value);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomIntWithRange() {
    int lowerBound = 500;
    int upperBound = 1500;

    // 测试指定范围的randomInt方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      int value = MathEx.randomInt(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomIntNegativeRange() {
    int lowerBound = -1500;
    int upperBound = -500;

    // 测试负数范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      int value = MathEx.randomInt(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomIntLargeRange() {
    int lowerBound = -1000000;
    int upperBound = 1000000;

    // 测试大范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      int value = MathEx.randomInt(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      long类型随机数测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testRandomLong() {
    // 测试无参数的randomLong方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      long value = MathEx.randomLong();
      // long值的范围是-2^63到2^63-1，所以任何值都是有效的
      assertTrue(value >= Long.MIN_VALUE && value <= Long.MAX_VALUE);
    }
  }

  @Test
  void testRandomLongWithUpperBound() {
    long upperBound = 10000L;

    // 测试带上界的randomLong方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      long value = MathEx.randomLong(upperBound);
      assertTrue(value >= 0L, "随机数应该大于等于0: " + value);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomLongWithRange() {
    long lowerBound = 5000L;
    long upperBound = 15000L;

    // 测试指定范围的randomLong方法
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      long value = MathEx.randomLong(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomLongNegativeRange() {
    long lowerBound = -15000L;
    long upperBound = -5000L;

    // 测试负数范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      long value = MathEx.randomLong(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  @Test
  void testRandomLongVeryLargeRange() {
    long lowerBound = -1000000000L;
    long upperBound = 1000000000L;

    // 测试非常大的范围
    for (int i = 0; i < TEST_ITERATIONS; i++) {
      long value = MathEx.randomLong(lowerBound, upperBound);
      assertTrue(value >= lowerBound,
          "随机数应该大于等于下界: " + value + " >= " + lowerBound);
      assertTrue(value < upperBound,
          "随机数应该小于上界: " + value + " < " + upperBound);
    }
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      边界情况和特殊值测试
  //
  // ///////////////////////////////////////////////////////////////////////////

  @Test
  void testRandomWithEqualBounds() {
    // 测试上下界相等的情况
    // 注意：大多数随机数实现不允许相等的边界，这可能会抛出异常
    double bound = 5.0;

    // 如果底层实现不支持相等边界，则跳过此测试或期望异常
    try {
      double value = MathEx.random(bound, bound);
      assertEquals(bound, value, 1e-10, "相等边界时应该返回边界值");
    } catch (IllegalArgumentException e) {
      // 某些实现可能不允许相等的边界，这也是合理的
      assertTrue(e.getMessage().contains("bound") || e.getMessage().contains("range"),
          "异常信息应该与边界相关: " + e.getMessage());
    }
  }

  @Test
  void testRandomSingleValueRanges() {
    // 测试单值范围（上界比下界大1）

    // byte类型
    byte byteLower = 5;
    byte byteUpper = 6;
    for (int i = 0; i < 100; i++) {
      byte value = MathEx.randomByte(byteLower, byteUpper);
      assertEquals(byteLower, value, "单值范围应该返回下界值");
    }

    // short类型
    short shortLower = 50;
    short shortUpper = 51;
    for (int i = 0; i < 100; i++) {
      short value = MathEx.randomShort(shortLower, shortUpper);
      assertEquals(shortLower, value, "单值范围应该返回下界值");
    }

    // int类型
    int intLower = 500;
    int intUpper = 501;
    for (int i = 0; i < 100; i++) {
      int value = MathEx.randomInt(intLower, intUpper);
      assertEquals(intLower, value, "单值范围应该返回下界值");
    }

    // long类型
    long longLower = 5000L;
    long longUpper = 5001L;
    for (int i = 0; i < 100; i++) {
      long value = MathEx.randomLong(longLower, longUpper);
      assertEquals(longLower, value, "单值范围应该返回下界值");
    }
  }

  @Test
  void testRandomDistribution() {
    // 简单的分布测试：确保随机数在范围内相对均匀分布
    int rangeSize = 10;
    int[] counts = new int[rangeSize];
    int iterations = 10000;

    // 收集统计数据
    for (int i = 0; i < iterations; i++) {
      int value = MathEx.randomInt(rangeSize);
      counts[value]++;
    }

    // 验证每个值都至少出现过
    for (int i = 0; i < rangeSize; i++) {
      assertTrue(counts[i] > 0, "值 " + i + " 应该至少出现一次");
    }

    // 验证分布的合理性（每个值的出现次数不应该偏离期望值太远）
    double expectedCount = (double) iterations / rangeSize;
    double tolerance = expectedCount * 0.3; // 30%的容差

    for (int i = 0; i < rangeSize; i++) {
      assertTrue(Math.abs(counts[i] - expectedCount) < tolerance,
          "值 " + i + " 的出现次数 " + counts[i] + " 应该接近期望值 " + expectedCount);
    }
  }

  @Test
  void testRandomnessQuality() {
    // 基本的随机性质量测试
    // 生成大量随机数，确保它们不是简单的模式
    int[] values = new int[1000];
    for (int i = 0; i < values.length; i++) {
      values[i] = MathEx.randomInt(Integer.MAX_VALUE);
    }

    // 检查是否存在明显的重复模式
    int consecutiveEqual = 0;
    int maxConsecutiveEqual = 0;

    for (int i = 1; i < values.length; i++) {
      if (values[i] == values[i-1]) {
        consecutiveEqual++;
        maxConsecutiveEqual = Math.max(maxConsecutiveEqual, consecutiveEqual);
      } else {
        consecutiveEqual = 0;
      }
    }

    // 连续相等的值不应该太多（考虑到随机性，偶尔会有重复）
    assertTrue(maxConsecutiveEqual < 5,
        "连续相等的随机数不应该超过4个，实际为: " + maxConsecutiveEqual);
  }

  @Test
  void testEdgeCases() {
    // 测试边界值

    // 测试byte的最大范围
    for (int i = 0; i < 100; i++) {
      byte value = MathEx.randomByte(Byte.MIN_VALUE, Byte.MAX_VALUE);
      assertTrue(value >= Byte.MIN_VALUE && value < Byte.MAX_VALUE);
    }

    // 测试short的最大范围
    for (int i = 0; i < 100; i++) {
      short value = MathEx.randomShort(Short.MIN_VALUE, Short.MAX_VALUE);
      assertTrue(value >= Short.MIN_VALUE && value < Short.MAX_VALUE);
    }

    // 测试零值范围
    assertEquals(0, MathEx.randomInt(0, 1));
    assertEquals((byte) 0, MathEx.randomByte((byte) 0, (byte) 1));
    assertEquals((short) 0, MathEx.randomShort((short) 0, (short) 1));
    assertEquals(0L, MathEx.randomLong(0L, 1L));
  }
}