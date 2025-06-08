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
 * FloatBit类的单元测试。
 *
 * @author Haixing Hu
 */
class FloatBitTest {

    @Test
    void testConstants() {
        assertEquals(32, FloatBit.BITS);
        assertEquals(1, FloatBit.SIGN_BITS);
        assertEquals(1, FloatBit.HIDDEN_BITS);
        assertEquals(8, FloatBit.EXPONENT_BITS);
        assertEquals(23, FloatBit.MANTISSA_BITS);
        assertEquals(24, FloatBit.DIGITS);
        assertEquals(6, FloatBit.DIGITS_10);
        assertEquals(127, FloatBit.EXPONENT_BIAS);
        assertEquals(-126, FloatBit.MIN_EXPONENT);
        assertEquals(127, FloatBit.MAX_EXPONENT);
        assertEquals(-37, FloatBit.MIN_EXPONENT_10);
        assertEquals(38, FloatBit.MAX_EXPONENT_10);
        assertEquals(-149, FloatBit.DENORM_MIN_EXPONENT);
        assertEquals(-44, FloatBit.DENORM_MIN_EXPONENT_10);

        // 测试掩码常量
        assertEquals(0x80000000, FloatBit.SIGN_BIT_MASK);
        assertEquals(0x00800000, FloatBit.HIDDEN_BITS_MASK);
        assertEquals(0x7FFFFFFF, FloatBit.ABS_MASK);
        assertEquals(0x7F800000, FloatBit.EXPONENT_MASK);
        assertEquals(0x007FFFFF, FloatBit.MANTISSA_MASK);
        assertEquals(0x7FC00000, FloatBit.QUIET_NAN_MASK);
        assertEquals(0x01000000, FloatBit.MAX_EXACT_INT);
    }

    @Test
    void testToBitsAndFromBits() {
        final float[] testValues = {
            0.0f, -0.0f, 1.0f, -1.0f, 3.14159f, -3.14159f,
            Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY,
            Float.NaN, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_NORMAL,
            0.5f, -0.5f, 2.0f, -2.0f, 1000000.0f, -1000000.0f
        };

        for (final float value : testValues) {
            final int bits = FloatBit.toBits(value);
            final float restored = FloatBit.fromBits(bits);

            if (Float.isNaN(value)) {
                assertTrue(Float.isNaN(restored));
            } else {
                assertEquals(value, restored, 0.0f);
            }
        }
    }

    @Test
    void testGetSignBit() {
        assertEquals(0, FloatBit.getSignBit(FloatBit.toBits(1.0f)));
        assertEquals(0, FloatBit.getSignBit(FloatBit.toBits(0.0f)));
        assertEquals(0, FloatBit.getSignBit(FloatBit.toBits(Float.POSITIVE_INFINITY)));
        assertEquals(0, FloatBit.getSignBit(FloatBit.toBits(Float.MAX_VALUE)));

        assertTrue(FloatBit.getSignBit(FloatBit.toBits(-1.0f)) != 0);
        assertTrue(FloatBit.getSignBit(FloatBit.toBits(-0.0f)) != 0);
        assertTrue(FloatBit.getSignBit(FloatBit.toBits(Float.NEGATIVE_INFINITY)) != 0);
        assertTrue(FloatBit.getSignBit(FloatBit.toBits(-Float.MAX_VALUE)) != 0);
    }

    @Test
    void testGetMantissa() {
        final int bits1 = FloatBit.toBits(1.0f);
        assertEquals(0, FloatBit.getMantissa(bits1)); // 标准化数的尾数为0（隐含的1不包含在内）

        final int bits2 = FloatBit.toBits(1.5f); // 1.5 = 1.1 * 2^0
        assertTrue(FloatBit.getMantissa(bits2) != 0);

        final int bits3 = FloatBit.toBits(0.0f);
        assertEquals(0, FloatBit.getMantissa(bits3)); // 零的尾数为0

        final int bits4 = FloatBit.toBits(Float.MIN_VALUE);
        assertEquals(1, FloatBit.getMantissa(bits4)); // 最小非标准化数的尾数为1
    }

    @Test
    void testGetExponent() {
        final int bits1 = FloatBit.toBits(1.0f);
        assertEquals(127, FloatBit.getExponent(bits1)); // 1.0f的指数为0+偏移量127
        assertEquals(0, FloatBit.getActualExponent(bits1)); // 实际指数为0

        final int bits2 = FloatBit.toBits(2.0f);
        assertEquals(128, FloatBit.getExponent(bits2)); // 2.0f的指数为1+偏移量127
        assertEquals(1, FloatBit.getActualExponent(bits2)); // 实际指数为1

        final int bits3 = FloatBit.toBits(0.5f);
        assertEquals(126, FloatBit.getExponent(bits3)); // 0.5f的指数为-1+偏移量127
        assertEquals(-1, FloatBit.getActualExponent(bits3)); // 实际指数为-1

        final int bits4 = FloatBit.toBits(0.0f);
        assertEquals(0, FloatBit.getExponent(bits4)); // 零的指数为0

        final int bits5 = FloatBit.toBits(Float.POSITIVE_INFINITY);
        assertEquals(255, FloatBit.getExponent(bits5)); // 无穷大的指数为最大值
    }

    @Test
    void testIsZero() {
        assertTrue(FloatBit.isZero(0.0f));
        assertTrue(FloatBit.isZero(-0.0f));
        assertFalse(FloatBit.isZero(1.0f));
        assertFalse(FloatBit.isZero(-1.0f));
        assertFalse(FloatBit.isZero(Float.MIN_VALUE));
        assertFalse(FloatBit.isZero(Float.MIN_NORMAL));
        assertFalse(FloatBit.isZero(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isZero(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isZero(Float.NaN));
    }

    @Test
    void testIsPositiveZero() {
        assertTrue(FloatBit.isPositiveZero(0.0f));
        assertFalse(FloatBit.isPositiveZero(-0.0f));
        assertFalse(FloatBit.isPositiveZero(1.0f));
        assertFalse(FloatBit.isPositiveZero(-1.0f));
        assertFalse(FloatBit.isPositiveZero(Float.MIN_VALUE));
    }

    @Test
    void testIsNegativeZero() {
        assertTrue(FloatBit.isNegativeZero(-0.0f));
        assertFalse(FloatBit.isNegativeZero(0.0f));
        assertFalse(FloatBit.isNegativeZero(-1.0f));
        assertFalse(FloatBit.isNegativeZero(1.0f));
        assertFalse(FloatBit.isNegativeZero(-Float.MIN_VALUE));
    }

    @Test
    void testIsNormalized() {
        assertTrue(FloatBit.isNormalized(1.0f));
        assertTrue(FloatBit.isNormalized(-1.0f));
        assertTrue(FloatBit.isNormalized(Float.MIN_NORMAL));
        assertTrue(FloatBit.isNormalized(-Float.MIN_NORMAL));
        assertTrue(FloatBit.isNormalized(Float.MAX_VALUE));
        assertTrue(FloatBit.isNormalized(-Float.MAX_VALUE));

        assertFalse(FloatBit.isNormalized(0.0f));
        assertFalse(FloatBit.isNormalized(-0.0f));
        assertFalse(FloatBit.isNormalized(Float.MIN_VALUE));
        assertFalse(FloatBit.isNormalized(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isNormalized(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isNormalized(Float.NaN));
    }

    @Test
    void testIsDenormalized() {
        assertFalse(FloatBit.isDenormalized(0.0f));
        assertFalse(FloatBit.isDenormalized(-0.0f));
        assertFalse(FloatBit.isDenormalized(1.0f));
        assertFalse(FloatBit.isDenormalized(-1.0f));
        assertFalse(FloatBit.isDenormalized(Float.MIN_NORMAL));
        assertFalse(FloatBit.isDenormalized(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isDenormalized(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isDenormalized(Float.NaN));

        assertTrue(FloatBit.isDenormalized(Float.MIN_VALUE));
        assertTrue(FloatBit.isDenormalized(-Float.MIN_VALUE));
    }

    @Test
    void testIsInfinite() {
        assertTrue(FloatBit.isInfinite(Float.POSITIVE_INFINITY));
        assertTrue(FloatBit.isInfinite(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isInfinite(1.0f));
        assertFalse(FloatBit.isInfinite(-1.0f));
        assertFalse(FloatBit.isInfinite(0.0f));
        assertFalse(FloatBit.isInfinite(Float.MAX_VALUE));
        assertFalse(FloatBit.isInfinite(Float.NaN));
    }

    @Test
    void testIsPositiveInfinite() {
        assertTrue(FloatBit.isPositiveInfinite(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isPositiveInfinite(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isPositiveInfinite(1.0f));
        assertFalse(FloatBit.isPositiveInfinite(Float.MAX_VALUE));
        assertFalse(FloatBit.isPositiveInfinite(Float.NaN));
    }

    @Test
    void testIsNegativeInfinite() {
        assertTrue(FloatBit.isNegativeInfinite(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isNegativeInfinite(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isNegativeInfinite(-1.0f));
        assertFalse(FloatBit.isNegativeInfinite(-Float.MAX_VALUE));
        assertFalse(FloatBit.isNegativeInfinite(Float.NaN));
    }

    @Test
    void testIsNaN() {
        assertTrue(FloatBit.isNaN(Float.NaN));
        assertFalse(FloatBit.isNaN(1.0f));
        assertFalse(FloatBit.isNaN(-1.0f));
        assertFalse(FloatBit.isNaN(0.0f));
        assertFalse(FloatBit.isNaN(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isNaN(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isNaN(Float.MAX_VALUE));
        assertFalse(FloatBit.isNaN(Float.MIN_VALUE));
    }

    @Test
    void testIsQuietNaN() {
        // 大多数系统生成的NaN都是安静NaN
        assertTrue(FloatBit.isQuietNaN(Float.NaN));
        assertFalse(FloatBit.isQuietNaN(1.0f));
        assertFalse(FloatBit.isQuietNaN(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isQuietNaN(Float.NEGATIVE_INFINITY));
    }

    @Test
    void testIsFinite() {
        assertTrue(FloatBit.isFinite(1.0f));
        assertTrue(FloatBit.isFinite(-1.0f));
        assertTrue(FloatBit.isFinite(0.0f));
        assertTrue(FloatBit.isFinite(-0.0f));
        assertTrue(FloatBit.isFinite(Float.MAX_VALUE));
        assertTrue(FloatBit.isFinite(-Float.MAX_VALUE));
        assertTrue(FloatBit.isFinite(Float.MIN_VALUE));
        assertTrue(FloatBit.isFinite(Float.MIN_NORMAL));

        assertFalse(FloatBit.isFinite(Float.POSITIVE_INFINITY));
        assertFalse(FloatBit.isFinite(Float.NEGATIVE_INFINITY));
        assertFalse(FloatBit.isFinite(Float.NaN));
    }

    @Test
    void testSign() {
        assertEquals(1, FloatBit.sign(1.0f));
        assertEquals(1, FloatBit.sign(Float.MAX_VALUE));
        assertEquals(1, FloatBit.sign(Float.MIN_VALUE));
        assertEquals(1, FloatBit.sign(Float.MIN_NORMAL));
        assertEquals(1, FloatBit.sign(0.5f));

        assertEquals(-1, FloatBit.sign(-1.0f));
        assertEquals(-1, FloatBit.sign(-Float.MAX_VALUE));
        assertEquals(-1, FloatBit.sign(-Float.MIN_VALUE));
        assertEquals(-1, FloatBit.sign(-Float.MIN_NORMAL));
        assertEquals(-1, FloatBit.sign(-0.5f));

        assertEquals(0, FloatBit.sign(0.0f));
        assertEquals(0, FloatBit.sign(-0.0f));
    }

    @Test
    void testCompose() {
        // 测试构造1.0f
        final float result1 = FloatBit.compose(0, 0, 0);
        assertEquals(1.0f, result1, 0.0f);

        // 测试构造-1.0f
        final float result2 = FloatBit.compose(1, 0, 0);
        assertEquals(-1.0f, result2, 0.0f);

        // 测试构造2.0f (指数为1)
        final float result3 = FloatBit.compose(0, 1, 0);
        assertEquals(2.0f, result3, 0.0f);

        // 测试构造0.5f (指数为-1)
        final float result4 = FloatBit.compose(0, -1, 0);
        assertEquals(0.5f, result4, 0.0f);

        // 测试构造1.5f (指数为0，尾数为0x400000)
        final float result5 = FloatBit.compose(0, 0, 0x400000);
        assertEquals(1.5f, result5, 0.0f);
    }

    @Test
    void testCopySign() {
        assertEquals(1.0f, FloatBit.copySign(1.0f, 2.0f), 0.0f);
        assertEquals(-1.0f, FloatBit.copySign(1.0f, -2.0f), 0.0f);
        assertEquals(3.14f, FloatBit.copySign(3.14f, 1.0f), 0.0f);
        assertEquals(-3.14f, FloatBit.copySign(3.14f, -1.0f), 0.0f);

        // 测试零值
        assertEquals(0.0f, FloatBit.copySign(0.0f, 1.0f), 0.0f);
        assertEquals(-0.0f, FloatBit.copySign(0.0f, -1.0f), 0.0f);

        // 测试无穷大
        assertEquals(Float.POSITIVE_INFINITY, FloatBit.copySign(Float.POSITIVE_INFINITY, 1.0f));
        assertEquals(Float.NEGATIVE_INFINITY, FloatBit.copySign(Float.POSITIVE_INFINITY, -1.0f));

        // 测试NaN
        assertTrue(Float.isNaN(FloatBit.copySign(Float.NaN, 1.0f)));
        assertTrue(Float.isNaN(FloatBit.copySign(Float.NaN, -1.0f)));
    }

    @Test
    void testNextUpAndNextDown() {
        final float value = 1.0f;
        final float nextUp = FloatBit.nextUp(value);
        final float nextDown = FloatBit.nextDown(value);

        assertTrue(nextUp > value);
        assertTrue(nextDown < value);

        // 测试边界情况
        assertEquals(Float.POSITIVE_INFINITY, FloatBit.nextUp(Float.MAX_VALUE));
        assertEquals(Float.NEGATIVE_INFINITY, FloatBit.nextDown(-Float.MAX_VALUE));

        // 测试零值
        assertTrue(FloatBit.nextUp(0.0f) > 0.0f);
        assertTrue(FloatBit.nextDown(0.0f) < 0.0f);

        // 测试无穷大
        assertEquals(Float.POSITIVE_INFINITY, FloatBit.nextUp(Float.POSITIVE_INFINITY));
        assertEquals(Float.NEGATIVE_INFINITY, FloatBit.nextDown(Float.NEGATIVE_INFINITY));

        // 测试NaN
        assertTrue(Float.isNaN(FloatBit.nextUp(Float.NaN)));
        assertTrue(Float.isNaN(FloatBit.nextDown(Float.NaN)));
    }

    @Test
    void testSpecialValues() {
        // 测试一些特殊的浮点数值
        float[] specialValues = {
            Float.POSITIVE_INFINITY,
            Float.NEGATIVE_INFINITY,
            Float.NaN,
            Float.MAX_VALUE,
            -Float.MAX_VALUE,
            Float.MIN_VALUE,
            -Float.MIN_VALUE,
            Float.MIN_NORMAL,
            -Float.MIN_NORMAL
        };

        for (float value : specialValues) {
            int bits = FloatBit.toBits(value);
            float restored = FloatBit.fromBits(bits);

            if (Float.isNaN(value)) {
                assertTrue(Float.isNaN(restored));
            } else {
                assertEquals(value, restored, 0.0f);
            }
        }
    }

    @Test
    void testBitOperations() {
        // 测试位操作的正确性
        float value = 3.14159f;
        int bits = FloatBit.toBits(value);

        int signBit = FloatBit.getSignBit(bits);
        int exponent = FloatBit.getExponent(bits);
        int mantissa = FloatBit.getMantissa(bits);

        // 重新组合应该得到原始值
        float reconstructed = FloatBit.compose(signBit != 0 ? 1 : 0,
                                               exponent - FloatBit.EXPONENT_BIAS,
                                               mantissa);
        assertEquals(value, reconstructed, 1e-6f);
    }
}