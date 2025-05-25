////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MathEx}类中精度配置相关方法的单元测试。
 *
 * @author Haixing Hu
 */
public class MathExEpsilonTest {

  private float originalFloatEpsilon;
  private double originalDoubleEpsilon;

  @BeforeEach
  void setUp() {
    // 保存原始精度值，测试结束后恢复
    originalFloatEpsilon = MathEx.getFloatEpsilon();
    originalDoubleEpsilon = MathEx.getDoubleEpsilon();
  }

  @Test
  void testDefaultEpsilonValues() {
    // 测试默认精度值
    assertEquals(MathEx.DEFAULT_FLOAT_EPSILON, MathEx.getFloatEpsilon());
    assertEquals(MathEx.DEFAULT_DOUBLE_EPSILON, MathEx.getDoubleEpsilon());
    
    // 验证默认值的正确性
    assertEquals(1e-6F, MathEx.DEFAULT_FLOAT_EPSILON);
    assertEquals(1e-12D, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  @Test
  void testSetFloatEpsilon() {
    // 测试设置float精度值
    float newEpsilon = 1e-3F;
    MathEx.setFloatEpsilon(newEpsilon);
    assertEquals(newEpsilon, MathEx.getFloatEpsilon());

    // 测试设置更小的精度值
    newEpsilon = 1e-8F;
    MathEx.setFloatEpsilon(newEpsilon);
    assertEquals(newEpsilon, MathEx.getFloatEpsilon());

    // 恢复原始值
    MathEx.setFloatEpsilon(originalFloatEpsilon);
  }

  @Test
  void testSetDoubleEpsilon() {
    // 测试设置double精度值
    double newEpsilon = 1e-6D;
    MathEx.setDoubleEpsilon(newEpsilon);
    assertEquals(newEpsilon, MathEx.getDoubleEpsilon());

    // 测试设置更小的精度值
    newEpsilon = 1e-15D;
    MathEx.setDoubleEpsilon(newEpsilon);
    assertEquals(newEpsilon, MathEx.getDoubleEpsilon());

    // 恢复原始值
    MathEx.setDoubleEpsilon(originalDoubleEpsilon);
  }

  @Test
  void testSetFloatEpsilonWithInvalidValues() {
    // 测试设置无效的float精度值
    assertThrows(IllegalArgumentException.class, () -> MathEx.setFloatEpsilon(0.0f));
    assertThrows(IllegalArgumentException.class, () -> MathEx.setFloatEpsilon(-1e-6f));
    assertThrows(IllegalArgumentException.class, () -> MathEx.setFloatEpsilon(Float.NEGATIVE_INFINITY));
    // 修复后的源码现在能正确检测并拒绝NaN
    assertThrows(IllegalArgumentException.class, () -> MathEx.setFloatEpsilon(Float.NaN));
  }

  @Test
  void testSetDoubleEpsilonWithInvalidValues() {
    // 测试设置无效的double精度值
    assertThrows(IllegalArgumentException.class, () -> MathEx.setDoubleEpsilon(0.0));
    assertThrows(IllegalArgumentException.class, () -> MathEx.setDoubleEpsilon(-1e-12));
    assertThrows(IllegalArgumentException.class, () -> MathEx.setDoubleEpsilon(Double.NEGATIVE_INFINITY));
    // 修复后的源码现在能正确检测并拒绝NaN
    assertThrows(IllegalArgumentException.class, () -> MathEx.setDoubleEpsilon(Double.NaN));
  }

  @Test
  void testEpsilonThreadSafety() throws InterruptedException {
    // 测试精度设置的线程安全性
    final float testFloatEpsilon = 1e-4F;
    final double testDoubleEpsilon = 1e-9D;
    
    Thread thread1 = new Thread(() -> {
      MathEx.setFloatEpsilon(testFloatEpsilon);
      MathEx.setDoubleEpsilon(testDoubleEpsilon);
    });
    
    Thread thread2 = new Thread(() -> {
      try {
        Thread.sleep(10); // 确保thread1先执行
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      assertEquals(testFloatEpsilon, MathEx.getFloatEpsilon());
      assertEquals(testDoubleEpsilon, MathEx.getDoubleEpsilon());
    });
    
    thread1.start();
    thread2.start();
    
    thread1.join();
    thread2.join();
    
    // 恢复原始值
    MathEx.setFloatEpsilon(originalFloatEpsilon);
    MathEx.setDoubleEpsilon(originalDoubleEpsilon);
  }

  @Test
  void testEpsilonImpactOnFloatComparison() {
    // 测试精度设置对浮点数比较的影响
    float originalEpsilon = MathEx.getFloatEpsilon();
    
    // 使用较大的精度
    MathEx.setFloatEpsilon(0.1f);
    assertTrue(MathEx.equal(1.0f, 1.05f));
    assertTrue(MathEx.isZero(0.08f));
    
    // 使用较小的精度
    MathEx.setFloatEpsilon(1e-8f);
    assertFalse(MathEx.equal(1.0f, 1.00001f));
    assertFalse(MathEx.isZero(1e-7f));
    
    // 恢复原始值
    MathEx.setFloatEpsilon(originalEpsilon);
  }

  @Test
  void testEpsilonImpactOnDoubleComparison() {
    // 测试精度设置对双精度浮点数比较的影响
    double originalEpsilon = MathEx.getDoubleEpsilon();
    
    // 使用较大的精度
    MathEx.setDoubleEpsilon(1e-6);
    assertTrue(MathEx.equal(1.0, 1.0000005));
    assertTrue(MathEx.isZero(5e-7));
    
    // 使用较小的精度
    MathEx.setDoubleEpsilon(1e-15);
    assertFalse(MathEx.equal(1.0, 1.0000000000001));
    assertFalse(MathEx.isZero(1e-14));
    
    // 恢复原始值
    MathEx.setDoubleEpsilon(originalEpsilon);
  }

  @Test
  void testEpsilonEdgeCases() {
    // 测试极值情况
    
    // 测试非常小的正值
    MathEx.setFloatEpsilon(Float.MIN_VALUE);
    assertEquals(Float.MIN_VALUE, MathEx.getFloatEpsilon());
    
    MathEx.setDoubleEpsilon(Double.MIN_VALUE);
    assertEquals(Double.MIN_VALUE, MathEx.getDoubleEpsilon());
    
    // 测试较大的精度值
    MathEx.setFloatEpsilon(1.0f);
    assertEquals(1.0f, MathEx.getFloatEpsilon());
    
    MathEx.setDoubleEpsilon(1.0);
    assertEquals(1.0, MathEx.getDoubleEpsilon());
    
    // 恢复原始值
    MathEx.setFloatEpsilon(originalFloatEpsilon);
    MathEx.setDoubleEpsilon(originalDoubleEpsilon);
  }
} 