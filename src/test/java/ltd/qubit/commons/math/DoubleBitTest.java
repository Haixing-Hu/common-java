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
 * DoubleBit类的单元测试。
 * 
 * @author Haixing Hu
 */
class DoubleBitTest {

    @Test
    void testConstants() {
        assertEquals(64, DoubleBit.BITS);
        assertEquals(1, DoubleBit.SIGN_BITS);
        assertEquals(1, DoubleBit.HIDDEN_BITS);
        assertEquals(11, DoubleBit.EXPONENT_BITS);
        assertEquals(52, DoubleBit.MANTISSA_BITS);
        assertEquals(53, DoubleBit.DIGITS);
        assertEquals(15, DoubleBit.DIGITS_10);
        assertEquals(1023, DoubleBit.EXPONENT_BIAS);
        assertEquals(-1022, DoubleBit.MIN_EXPONENT);
        assertEquals(1023, DoubleBit.MAX_EXPONENT);
        assertEquals(-307, DoubleBit.MIN_EXPONENT_10);
        assertEquals(308, DoubleBit.MAX_EXPONENT_10);
        assertEquals(-1074, DoubleBit.DENORM_MIN_EXPONENT);
        assertEquals(-323, DoubleBit.DENORM_MIN_EXPONENT_10);
        
        // 测试掩码常量
        assertEquals(0x8000000000000000L, DoubleBit.SIGN_BIT_MASK);
        assertEquals(0x0010000000000000L, DoubleBit.HIDDEN_BITS_MASK);
        assertEquals(0x7FFFFFFFFFFFFFFFL, DoubleBit.ABS_MASK);
        assertEquals(0x7FF0000000000000L, DoubleBit.EXPONENT_MASK);
        assertEquals(0x000FFFFFFFFFFFFFL, DoubleBit.MANTISSA_MASK);
        assertEquals(0x7FF8000000000000L, DoubleBit.QUIET_NAN_MASK);
        assertEquals(0x0020000000000000L, DoubleBit.MAX_EXACT_INT);
    }

    @Test
    void testToBitsAndFromBits() {
        final double[] testValues = {
            0.0, -0.0, 1.0, -1.0, 3.141592653589793, -3.141592653589793,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.NaN, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL,
            0.5, -0.5, 2.0, -2.0, 1000000.0, -1000000.0, Math.E, Math.PI
        };

        for (final double value : testValues) {
            final long bits = DoubleBit.toBits(value);
            final double restored = DoubleBit.fromBits(bits);
            
            if (Double.isNaN(value)) {
                assertTrue(Double.isNaN(restored));
            } else {
                assertEquals(value, restored, 0.0);
            }
        }
    }

    @Test
    void testGetSignBit() {
        assertEquals(0, DoubleBit.getSignBit(DoubleBit.toBits(1.0)));
        assertEquals(0, DoubleBit.getSignBit(DoubleBit.toBits(0.0)));
        assertEquals(0, DoubleBit.getSignBit(DoubleBit.toBits(Double.POSITIVE_INFINITY)));
        assertEquals(0, DoubleBit.getSignBit(DoubleBit.toBits(Double.MAX_VALUE)));
        
        assertTrue(DoubleBit.getSignBit(DoubleBit.toBits(-1.0)) != 0);
        assertTrue(DoubleBit.getSignBit(DoubleBit.toBits(-0.0)) != 0);
        assertTrue(DoubleBit.getSignBit(DoubleBit.toBits(Double.NEGATIVE_INFINITY)) != 0);
        assertTrue(DoubleBit.getSignBit(DoubleBit.toBits(-Double.MAX_VALUE)) != 0);
    }

    @Test
    void testGetMantissa() {
        final long bits1 = DoubleBit.toBits(1.0);
        assertEquals(0, DoubleBit.getMantissa(bits1)); // 标准化数的尾数为0（隐含的1不包含在内）
        
        final long bits2 = DoubleBit.toBits(1.5); // 1.5 = 1.1 * 2^0
        assertTrue(DoubleBit.getMantissa(bits2) != 0);
        
        final long bits3 = DoubleBit.toBits(0.0);
        assertEquals(0, DoubleBit.getMantissa(bits3)); // 零的尾数为0
        
        final long bits4 = DoubleBit.toBits(Double.MIN_VALUE);
        assertEquals(1, DoubleBit.getMantissa(bits4)); // 最小非标准化数的尾数为1
    }

    @Test
    void testGetExponent() {
        final long bits1 = DoubleBit.toBits(1.0);
        assertEquals(1023, DoubleBit.getExponent(bits1)); // 1.0的指数为0+偏移量1023
        assertEquals(0, DoubleBit.getActualExponent(bits1)); // 实际指数为0
        
        final long bits2 = DoubleBit.toBits(2.0);
        assertEquals(1024, DoubleBit.getExponent(bits2)); // 2.0的指数为1+偏移量1023
        assertEquals(1, DoubleBit.getActualExponent(bits2)); // 实际指数为1
        
        final long bits3 = DoubleBit.toBits(0.5);
        assertEquals(1022, DoubleBit.getExponent(bits3)); // 0.5的指数为-1+偏移量1023
        assertEquals(-1, DoubleBit.getActualExponent(bits3)); // 实际指数为-1
        
        final long bits4 = DoubleBit.toBits(0.0);
        assertEquals(0, DoubleBit.getExponent(bits4)); // 零的指数为0
        
        final long bits5 = DoubleBit.toBits(Double.POSITIVE_INFINITY);
        assertEquals(2047, DoubleBit.getExponent(bits5)); // 无穷大的指数为最大值
    }

    @Test
    void testIsZero() {
        assertTrue(DoubleBit.isZero(0.0));
        assertTrue(DoubleBit.isZero(-0.0));
        assertFalse(DoubleBit.isZero(1.0));
        assertFalse(DoubleBit.isZero(-1.0));
        assertFalse(DoubleBit.isZero(Double.MIN_VALUE));
        assertFalse(DoubleBit.isZero(Double.MIN_NORMAL));
        assertFalse(DoubleBit.isZero(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isZero(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isZero(Double.NaN));
    }

    @Test
    void testIsPositiveZero() {
        assertTrue(DoubleBit.isPositiveZero(0.0));
        assertFalse(DoubleBit.isPositiveZero(-0.0));
        assertFalse(DoubleBit.isPositiveZero(1.0));
        assertFalse(DoubleBit.isPositiveZero(-1.0));
        assertFalse(DoubleBit.isPositiveZero(Double.MIN_VALUE));
    }

    @Test
    void testIsNegativeZero() {
        assertTrue(DoubleBit.isNegativeZero(-0.0));
        assertFalse(DoubleBit.isNegativeZero(0.0));
        assertFalse(DoubleBit.isNegativeZero(-1.0));
        assertFalse(DoubleBit.isNegativeZero(1.0));
        assertFalse(DoubleBit.isNegativeZero(-Double.MIN_VALUE));
    }

    @Test
    void testIsNormalized() {
        assertTrue(DoubleBit.isNormalized(1.0));
        assertTrue(DoubleBit.isNormalized(-1.0));
        assertTrue(DoubleBit.isNormalized(Double.MIN_NORMAL));
        assertTrue(DoubleBit.isNormalized(-Double.MIN_NORMAL));
        assertTrue(DoubleBit.isNormalized(Double.MAX_VALUE));
        assertTrue(DoubleBit.isNormalized(-Double.MAX_VALUE));
        
        assertFalse(DoubleBit.isNormalized(0.0));
        assertFalse(DoubleBit.isNormalized(-0.0));
        assertFalse(DoubleBit.isNormalized(Double.MIN_VALUE));
        assertFalse(DoubleBit.isNormalized(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isNormalized(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isNormalized(Double.NaN));
    }

    @Test
    void testIsDenormalized() {
        assertFalse(DoubleBit.isDenormalized(0.0));
        assertFalse(DoubleBit.isDenormalized(-0.0));
        assertFalse(DoubleBit.isDenormalized(1.0));
        assertFalse(DoubleBit.isDenormalized(-1.0));
        assertFalse(DoubleBit.isDenormalized(Double.MIN_NORMAL));
        assertFalse(DoubleBit.isDenormalized(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isDenormalized(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isDenormalized(Double.NaN));
        
        assertTrue(DoubleBit.isDenormalized(Double.MIN_VALUE));
        assertTrue(DoubleBit.isDenormalized(-Double.MIN_VALUE));
    }

    @Test
    void testIsInfinite() {
        assertTrue(DoubleBit.isInfinite(Double.POSITIVE_INFINITY));
        assertTrue(DoubleBit.isInfinite(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isInfinite(1.0));
        assertFalse(DoubleBit.isInfinite(-1.0));
        assertFalse(DoubleBit.isInfinite(0.0));
        assertFalse(DoubleBit.isInfinite(Double.MAX_VALUE));
        assertFalse(DoubleBit.isInfinite(Double.NaN));
    }

    @Test
    void testIsPositiveInfinite() {
        assertTrue(DoubleBit.isPositiveInfinite(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isPositiveInfinite(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isPositiveInfinite(1.0));
        assertFalse(DoubleBit.isPositiveInfinite(Double.MAX_VALUE));
        assertFalse(DoubleBit.isPositiveInfinite(Double.NaN));
    }

    @Test
    void testIsNegativeInfinite() {
        assertTrue(DoubleBit.isNegativeInfinite(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isNegativeInfinite(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isNegativeInfinite(-1.0));
        assertFalse(DoubleBit.isNegativeInfinite(-Double.MAX_VALUE));
        assertFalse(DoubleBit.isNegativeInfinite(Double.NaN));
    }

    @Test
    void testIsNaN() {
        assertTrue(DoubleBit.isNaN(Double.NaN));
        assertFalse(DoubleBit.isNaN(1.0));
        assertFalse(DoubleBit.isNaN(-1.0));
        assertFalse(DoubleBit.isNaN(0.0));
        assertFalse(DoubleBit.isNaN(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isNaN(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isNaN(Double.MAX_VALUE));
        assertFalse(DoubleBit.isNaN(Double.MIN_VALUE));
    }

    @Test
    void testIsQuietNaN() {
        // 大多数系统生成的NaN都是安静NaN
        assertTrue(DoubleBit.isQuietNaN(Double.NaN));
        assertFalse(DoubleBit.isQuietNaN(1.0));
        assertFalse(DoubleBit.isQuietNaN(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isQuietNaN(Double.NEGATIVE_INFINITY));
    }

    @Test
    void testIsFinite() {
        assertTrue(DoubleBit.isFinite(1.0));
        assertTrue(DoubleBit.isFinite(-1.0));
        assertTrue(DoubleBit.isFinite(0.0));
        assertTrue(DoubleBit.isFinite(-0.0));
        assertTrue(DoubleBit.isFinite(Double.MAX_VALUE));
        assertTrue(DoubleBit.isFinite(-Double.MAX_VALUE));
        assertTrue(DoubleBit.isFinite(Double.MIN_VALUE));
        assertTrue(DoubleBit.isFinite(Double.MIN_NORMAL));
        
        assertFalse(DoubleBit.isFinite(Double.POSITIVE_INFINITY));
        assertFalse(DoubleBit.isFinite(Double.NEGATIVE_INFINITY));
        assertFalse(DoubleBit.isFinite(Double.NaN));
    }

    @Test
    void testSign() {
        assertEquals(1, DoubleBit.sign(1.0));
        assertEquals(1, DoubleBit.sign(Double.MAX_VALUE));
        assertEquals(1, DoubleBit.sign(Double.MIN_VALUE));
        assertEquals(1, DoubleBit.sign(Double.MIN_NORMAL));
        assertEquals(1, DoubleBit.sign(0.5));
        
        assertEquals(-1, DoubleBit.sign(-1.0));
        assertEquals(-1, DoubleBit.sign(-Double.MAX_VALUE));
        assertEquals(-1, DoubleBit.sign(-Double.MIN_VALUE));
        assertEquals(-1, DoubleBit.sign(-Double.MIN_NORMAL));
        assertEquals(-1, DoubleBit.sign(-0.5));
        
        assertEquals(0, DoubleBit.sign(0.0));
        assertEquals(0, DoubleBit.sign(-0.0));
    }

    @Test
    void testCompose() {
        // 测试构造1.0
        final double result1 = DoubleBit.compose(0, 0, 0);
        assertEquals(1.0, result1, 0.0);
        
        // 测试构造-1.0
        final double result2 = DoubleBit.compose(1, 0, 0);
        assertEquals(-1.0, result2, 0.0);
        
        // 测试构造2.0 (指数为1)
        final double result3 = DoubleBit.compose(0, 1, 0);
        assertEquals(2.0, result3, 0.0);
        
        // 测试构造0.5 (指数为-1)
        final double result4 = DoubleBit.compose(0, -1, 0);
        assertEquals(0.5, result4, 0.0);
        
        // 测试构造1.5 (指数为0，尾数为0x8000000000000L)
        final double result5 = DoubleBit.compose(0, 0, 0x8000000000000L);
        assertEquals(1.5, result5, 0.0);
    }

    @Test
    void testCopySign() {
        assertEquals(1.0, DoubleBit.copySign(1.0, 2.0), 0.0);
        assertEquals(-1.0, DoubleBit.copySign(1.0, -2.0), 0.0);
        assertEquals(3.14159, DoubleBit.copySign(3.14159, 1.0), 0.0);
        assertEquals(-3.14159, DoubleBit.copySign(3.14159, -1.0), 0.0);
        
        // 测试零值
        assertEquals(0.0, DoubleBit.copySign(0.0, 1.0), 0.0);
        assertEquals(-0.0, DoubleBit.copySign(0.0, -1.0), 0.0);
        
        // 测试无穷大
        assertEquals(Double.POSITIVE_INFINITY, DoubleBit.copySign(Double.POSITIVE_INFINITY, 1.0));
        assertEquals(Double.NEGATIVE_INFINITY, DoubleBit.copySign(Double.POSITIVE_INFINITY, -1.0));
        
        // 测试NaN
        assertTrue(Double.isNaN(DoubleBit.copySign(Double.NaN, 1.0)));
        assertTrue(Double.isNaN(DoubleBit.copySign(Double.NaN, -1.0)));
    }

    @Test
    void testNextUpAndNextDown() {
        final double value = 1.0;
        final double nextUp = DoubleBit.nextUp(value);
        final double nextDown = DoubleBit.nextDown(value);
        
        assertTrue(nextUp > value);
        assertTrue(nextDown < value);
        
        // 测试边界情况
        assertEquals(Double.POSITIVE_INFINITY, DoubleBit.nextUp(Double.MAX_VALUE));
        assertEquals(Double.NEGATIVE_INFINITY, DoubleBit.nextDown(-Double.MAX_VALUE));
        
        // 测试零值
        assertTrue(DoubleBit.nextUp(0.0) > 0.0);
        assertTrue(DoubleBit.nextDown(0.0) < 0.0);
        
        // 测试无穷大
        assertEquals(Double.POSITIVE_INFINITY, DoubleBit.nextUp(Double.POSITIVE_INFINITY));
        assertEquals(Double.NEGATIVE_INFINITY, DoubleBit.nextDown(Double.NEGATIVE_INFINITY));
        
        // 测试NaN
        assertTrue(Double.isNaN(DoubleBit.nextUp(Double.NaN)));
        assertTrue(Double.isNaN(DoubleBit.nextDown(Double.NaN)));
    }

    @Test
    void testSpecialValues() {
        // 测试一些特殊的双精度浮点数值
        double[] specialValues = {
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY,
            Double.NaN,
            Double.MAX_VALUE,
            -Double.MAX_VALUE,
            Double.MIN_VALUE,
            -Double.MIN_VALUE,
            Double.MIN_NORMAL,
            -Double.MIN_NORMAL
        };

        for (double value : specialValues) {
            long bits = DoubleBit.toBits(value);
            double restored = DoubleBit.fromBits(bits);
            
            if (Double.isNaN(value)) {
                assertTrue(Double.isNaN(restored));
            } else {
                assertEquals(value, restored, 0.0);
            }
        }
    }

    @Test
    void testBitOperations() {
        // 测试位操作的正确性
        double value = 3.141592653589793;
        long bits = DoubleBit.toBits(value);
        
        long signBit = DoubleBit.getSignBit(bits);
        int exponent = DoubleBit.getExponent(bits);
        long mantissa = DoubleBit.getMantissa(bits);
        
        // 重新组合应该得到原始值
        double reconstructed = DoubleBit.compose(signBit != 0 ? 1 : 0, 
                                                 exponent - DoubleBit.EXPONENT_BIAS, 
                                                 mantissa);
        assertEquals(value, reconstructed, 1e-15);
    }

    @Test
    void testEdgeCases() {
        // 测试边界情况
        
        // 测试最小正标准化数
        assertTrue(DoubleBit.isNormalized(Double.MIN_NORMAL));
        assertFalse(DoubleBit.isDenormalized(Double.MIN_NORMAL));
        
        // 测试最大非标准化数
        double maxDenormal = Double.longBitsToDouble(0x000fffffffffffffL);
        assertTrue(DoubleBit.isDenormalized(maxDenormal));
        assertFalse(DoubleBit.isNormalized(maxDenormal));
        
        // 测试指数边界
        assertEquals(DoubleBit.MIN_EXPONENT, DoubleBit.getActualExponent(DoubleBit.toBits(Double.MIN_NORMAL)));
        // 无穷大的指数是2047，实际指数是2047-1023=1024，这超过了MAX_EXPONENT(1023)
        // 所以我们测试一个接近最大值但不是无穷大的数
        long maxNormalBits = 0x7fefffffffffffffL; // 最大有限正数
        double maxNormal = Double.longBitsToDouble(maxNormalBits);
        assertEquals(DoubleBit.MAX_EXPONENT, DoubleBit.getActualExponent(DoubleBit.toBits(maxNormal)));
    }

    @Test
    void testMathematicalConstants() {
        // 测试数学常量
        assertFalse(DoubleBit.isZero(Math.PI));
        assertFalse(DoubleBit.isZero(Math.E));
        assertTrue(DoubleBit.isNormalized(Math.PI));
        assertTrue(DoubleBit.isNormalized(Math.E));
        assertTrue(DoubleBit.isFinite(Math.PI));
        assertTrue(DoubleBit.isFinite(Math.E));
        
        assertEquals(1, DoubleBit.sign(Math.PI));
        assertEquals(1, DoubleBit.sign(Math.E));
    }
} 