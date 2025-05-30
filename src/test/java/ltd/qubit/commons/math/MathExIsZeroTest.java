// ////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
// ////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MathEx.isZero()方法的单元测试。
 * 
 * @author Haixing Hu
 */
class MathExIsZeroTest {

    @Test
    void testIsZeroFloat() {
        // 测试零值
        assertTrue(MathEx.isZero(0.0f));
        assertTrue(MathEx.isZero(-0.0f));
        
        // 测试接近零的值
        assertTrue(MathEx.isZero(1e-10f));
        assertTrue(MathEx.isZero(-1e-10f));
        assertTrue(MathEx.isZero(1e-20f));
        assertTrue(MathEx.isZero(-1e-20f));
        
        // 测试明显非零的值
        assertFalse(MathEx.isZero(1.0f));
        assertFalse(MathEx.isZero(-1.0f));
        assertFalse(MathEx.isZero(0.1f));
        assertFalse(MathEx.isZero(-0.1f));
        
        // 测试特殊值
        assertFalse(MathEx.isZero(Float.POSITIVE_INFINITY));
        assertFalse(MathEx.isZero(Float.NEGATIVE_INFINITY));
        assertFalse(MathEx.isZero(Float.NaN));
        
        // 测试浮点精度边界
        assertFalse(MathEx.isZero(1e-3f));  // 大于默认epsilon
        assertTrue(MathEx.isZero(1e-8f));   // 小于默认epsilon
    }

    @Test
    void testIsZeroFloatWithEpsilon() {
        // 测试自定义epsilon
        float epsilon = 1e-3f;
        
        // 在epsilon范围内
        assertTrue(MathEx.isZero(0.0f, epsilon));
        assertTrue(MathEx.isZero(0.0005f, epsilon));
        assertTrue(MathEx.isZero(-0.0005f, epsilon));
        assertTrue(MathEx.isZero(0.001f, epsilon));
        assertTrue(MathEx.isZero(-0.001f, epsilon));
        
        // 超出epsilon范围
        assertFalse(MathEx.isZero(0.002f, epsilon));
        assertFalse(MathEx.isZero(-0.002f, epsilon));
        assertFalse(MathEx.isZero(1.0f, epsilon));
        assertFalse(MathEx.isZero(-1.0f, epsilon));
        
        // 边界情况
        assertFalse(MathEx.isZero(1.0001f * epsilon, epsilon));
        assertTrue(MathEx.isZero(0.9999f * epsilon, epsilon));
        
        // 测试非常小的epsilon
        epsilon = 1e-15f;
        assertTrue(MathEx.isZero(0.0f, epsilon));
        assertFalse(MathEx.isZero(1e-10f, epsilon));
        
        // 测试较大的epsilon
        epsilon = 1.0f;
        assertTrue(MathEx.isZero(0.5f, epsilon));
        assertTrue(MathEx.isZero(-0.5f, epsilon));
        assertFalse(MathEx.isZero(1.5f, epsilon));
    }

    @Test
    void testIsZeroDouble() {
        // 测试零值
        assertTrue(MathEx.isZero(0.0));
        assertTrue(MathEx.isZero(-0.0));
        
        // 测试接近零的值
        assertTrue(MathEx.isZero(1e-20));
        assertTrue(MathEx.isZero(-1e-20));
        assertTrue(MathEx.isZero(1e-30));
        assertTrue(MathEx.isZero(-1e-30));
        
        // 测试明显非零的值
        assertFalse(MathEx.isZero(1.0));
        assertFalse(MathEx.isZero(-1.0));
        assertFalse(MathEx.isZero(0.1));
        assertFalse(MathEx.isZero(-0.1));
        
        // 测试特殊值
        assertFalse(MathEx.isZero(Double.POSITIVE_INFINITY));
        assertFalse(MathEx.isZero(Double.NEGATIVE_INFINITY));
        assertFalse(MathEx.isZero(Double.NaN));
        
        // 测试双精度精度边界
        assertFalse(MathEx.isZero(1e-10));  // 大于默认epsilon
        assertTrue(MathEx.isZero(1e-16));   // 小于默认epsilon
    }

    @Test
    void testIsZeroDoubleWithEpsilon() {
        // 测试自定义epsilon
        double epsilon = 1e-10;
        
        // 在epsilon范围内
        assertTrue(MathEx.isZero(0.0, epsilon));
        assertTrue(MathEx.isZero(1e-11, epsilon));
        assertTrue(MathEx.isZero(-1e-11, epsilon));
        assertTrue(MathEx.isZero(1e-10, epsilon));
        assertTrue(MathEx.isZero(-1e-10, epsilon));
        
        // 超出epsilon范围
        assertFalse(MathEx.isZero(2e-10, epsilon));
        assertFalse(MathEx.isZero(-2e-10, epsilon));
        assertFalse(MathEx.isZero(1.0, epsilon));
        assertFalse(MathEx.isZero(-1.0, epsilon));
        
        // 边界情况
        assertFalse(MathEx.isZero(1.0001 * epsilon, epsilon));
        assertTrue(MathEx.isZero(0.9999 * epsilon, epsilon));
        
        // 测试非常小的epsilon
        epsilon = 1e-20;
        assertTrue(MathEx.isZero(0.0, epsilon));
        assertFalse(MathEx.isZero(1e-15, epsilon));
        
        // 测试较大的epsilon
        epsilon = 1.0;
        assertTrue(MathEx.isZero(0.5, epsilon));
        assertTrue(MathEx.isZero(-0.5, epsilon));
        assertFalse(MathEx.isZero(1.5, epsilon));
    }

    @Test
    void testIsZeroConsistencyWithFloatBit() {
        // 测试与FloatBit.isZero()的一致性
        float[] testValues = {
            0.0f, -0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.MIN_VALUE,
            Float.MIN_NORMAL, 1e-10f, 1e-20f, 1e-30f, Float.POSITIVE_INFINITY,
            Float.NEGATIVE_INFINITY, Float.NaN
        };
        
        for (float value : testValues) {
            // FloatBit.isZero()检查的是精确零值（包括+0.0和-0.0）
            // MathEx.isZero()检查的是接近零的值
            if (FloatBit.isZero(value)) {
                assertTrue(MathEx.isZero(value), "Value " + value + " should be considered zero by MathEx");
            }
        }
    }

    @Test
    void testIsZeroConsistencyWithDoubleBit() {
        // 测试与DoubleBit.isZero()的一致性
        double[] testValues = {
            0.0, -0.0, 1.0, -1.0, Double.MAX_VALUE, Double.MIN_VALUE,
            Double.MIN_NORMAL, 1e-20, 1e-30, 1e-100, Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY, Double.NaN
        };
        
        for (double value : testValues) {
            // DoubleBit.isZero()检查的是精确零值（包括+0.0和-0.0）
            // MathEx.isZero()检查的是接近零的值
            if (DoubleBit.isZero(value)) {
                assertTrue(MathEx.isZero(value), "Value " + value + " should be considered zero by MathEx");
            }
        }
    }

    @Test
    void testEpsilonEdgeCases() {
        // 测试epsilon的边界情况
        
        // 零epsilon
        assertTrue(MathEx.isZero(0.0f, 0.0f));
        assertFalse(MathEx.isZero(Float.MIN_VALUE, 0.0f));
        
        assertTrue(MathEx.isZero(0.0, 0.0));
        assertFalse(MathEx.isZero(Double.MIN_VALUE, 0.0));
        
        // 无穷大epsilon
        assertTrue(MathEx.isZero(1000.0f, Float.POSITIVE_INFINITY));
        assertTrue(MathEx.isZero(-1000.0f, Float.POSITIVE_INFINITY));
        assertTrue(MathEx.isZero(1000.0, Double.POSITIVE_INFINITY));
        assertTrue(MathEx.isZero(-1000.0, Double.POSITIVE_INFINITY));
        
        // NaN epsilon
        assertFalse(MathEx.isZero(0.0f, Float.NaN));
        assertFalse(MathEx.isZero(0.0, Double.NaN));
    }

    @Test
    void testNegativeEpsilon() {
        // 负epsilon的行为测试
        // MathEx.isZero()直接使用epsilon值，不会取绝对值
        // 因此负epsilon会导致比较失败（Math.abs(x) <= 负数 永远为false）
        float negEpsilonF = -1e-3f;
        assertFalse(MathEx.isZero(0.0f, negEpsilonF));  // 即使是0也会返回false
        assertFalse(MathEx.isZero(0.0005f, negEpsilonF));
        assertFalse(MathEx.isZero(-0.0005f, negEpsilonF));
        
        double negEpsilonD = -1e-10;
        assertFalse(MathEx.isZero(0.0, negEpsilonD));    // 即使是0也会返回false
        assertFalse(MathEx.isZero(1e-11, negEpsilonD));
        assertFalse(MathEx.isZero(-1e-11, negEpsilonD));
        
        // 只有当x的绝对值为0且epsilon为0时才会返回true
        assertTrue(MathEx.isZero(0.0f, 0.0f));
        assertTrue(MathEx.isZero(0.0, 0.0));
    }
} 