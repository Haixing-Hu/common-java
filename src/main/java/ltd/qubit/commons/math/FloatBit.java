////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

/**
 * 提供对IEEE 754单精度浮点数（float）的底层位操作工具类。
 *
 * <p>该类提供了一系列静态方法来操作和解析32位单精度浮点数的内部
 * 二进制表示，包括符号位、指数位和尾数位的提取与构造。
 *
 * <p>IEEE 754单精度浮点数格式：
 * <ul>
 * <li>总位数：32位</li>
 * <li>符号位：1位（第31位）</li>
 * <li>指数位：8位（第30-23位）</li>
 * <li>尾数位：23位（第22-0位）</li>
 * <li>指数偏移量：127</li>
 * </ul>
 *
 * @author 胡海星
 */
public final class FloatBit {

    /**
     * 总位数
     */
    public static final int BITS = 32;

    /**
     * 符号位数
     */
    public static final int SIGN_BITS = 1;

    /**
     * 隐含位数（IEEE 754标准中的隐含1）
     */
    public static final int HIDDEN_BITS = 1;

    /**
     * 指数位数
     */
    public static final int EXPONENT_BITS = 8;

    /**
     * 尾数位数
     */
    public static final int MANTISSA_BITS = 23;

    /**
     * 最大二进制精度位数
     */
    public static final int DIGITS = 24;

    /**
     * 最大十进制精度位数
     */
    public static final int DIGITS_10 = 6;

    /**
     * 指数偏移量
     */
    public static final int EXPONENT_BIAS = 127;

    /**
     * 标准化数的最小二进制指数
     */
    public static final int MIN_EXPONENT = -126;

    /**
     * 最大二进制指数
     */
    public static final int MAX_EXPONENT = 127;

    /**
     * 标准化数的最小十进制指数
     */
    public static final int MIN_EXPONENT_10 = -37;

    /**
     * 最大十进制指数
     */
    public static final int MAX_EXPONENT_10 = 38;

    /**
     * 非标准化数的最小二进制指数
     */
    public static final int DENORM_MIN_EXPONENT = -149;

    /**
     * 非标准化数的最小十进制指数
     */
    public static final int DENORM_MIN_EXPONENT_10 = -44;

    /**
     * 符号位掩码
     */
    public static final int SIGN_BIT_MASK = 0x80000000;

    /**
     * 隐含位掩码
     */
    public static final int HIDDEN_BITS_MASK = 0x00800000;

    /**
     * 绝对值掩码（去除符号位）
     */
    public static final int ABS_MASK = 0x7FFFFFFF;

    /**
     * 指数位掩码
     */
    public static final int EXPONENT_MASK = 0x7F800000;

    /**
     * 尾数位掩码
     */
    public static final int MANTISSA_MASK = 0x007FFFFF;

    /**
     * 安静NaN掩码
     */
    public static final int QUIET_NAN_MASK = 0x7FC00000;

    /**
     * 最大精确整数
     */
    public static final int MAX_EXACT_INT = 0x01000000;

    /**
     * 私有构造函数，防止实例化
     */
    private FloatBit() {
        // 工具类，不允许实例化
    }

    /**
     * 将浮点数转换为对应的位表示。
     *
     * @param value
     *     要转换的浮点数
     * @return
     *     浮点数的32位整数表示
     */
    public static int toBits(final float value) {
        return Float.floatToRawIntBits(value);
    }

    /**
     * 将位表示转换为对应的浮点数。
     *
     * @param bits
     *     32位整数表示
     * @return
     *     对应的浮点数
     */
    public static float fromBits(final int bits) {
        return Float.intBitsToFloat(bits);
    }

    /**
     * 从位表示中获取符号位。
     *
     * @param bits
     *     浮点数的位表示
     * @return
     *     符号位，0表示正数，非0表示负数
     */
    public static int getSignBit(final int bits) {
        return bits & SIGN_BIT_MASK;
    }

    /**
     * 从位表示中获取尾数部分。
     *
     * @param bits
     *     浮点数的位表示
     * @return
     *     尾数部分（23位）
     */
    public static int getMantissa(final int bits) {
        return bits & MANTISSA_MASK;
    }

    /**
     * 从位表示中获取指数部分。
     *
     * @param bits
     *     浮点数的位表示
     * @return
     *     指数部分（8位，未减去偏移量）
     */
    public static int getExponent(final int bits) {
        return (bits & EXPONENT_MASK) >>> MANTISSA_BITS;
    }

    /**
     * 从位表示中获取实际指数值（已减去偏移量）。
     *
     * @param bits
     *     浮点数的位表示
     * @return
     *     实际指数值
     */
    public static int getActualExponent(final int bits) {
        return getExponent(bits) - EXPONENT_BIAS;
    }

    /**
     * 判断浮点数是否为零。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是零（包括正零和负零）返回true，否则返回false
     */
    public static boolean isZero(final float value) {
        return (toBits(value) & ABS_MASK) == 0;
    }

    /**
     * 判断浮点数是否为正零。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是正零返回true，否则返回false
     */
    public static boolean isPositiveZero(final float value) {
        return toBits(value) == 0;
    }

    /**
     * 判断浮点数是否为负零。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是负零返回true，否则返回false
     */
    public static boolean isNegativeZero(final float value) {
        return toBits(value) == SIGN_BIT_MASK;
    }

    /**
     * 判断浮点数是否为标准化数。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是标准化数返回true，否则返回false
     */
    public static boolean isNormalized(final float value) {
        final int bits = toBits(value);
        final int exponent = getExponent(bits);
        return exponent > 0 && exponent < 0xFF;
    }

    /**
     * 判断浮点数是否为非标准化数。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是非标准化数返回true，否则返回false
     */
    public static boolean isDenormalized(final float value) {
        final int bits = toBits(value);
        final int exponent = getExponent(bits);
        final int mantissa = getMantissa(bits);
        return exponent == 0 && mantissa != 0;
    }

    /**
     * 判断浮点数是否为无穷大。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是无穷大（包括正无穷和负无穷）返回true，否则返回false
     */
    public static boolean isInfinite(final float value) {
        return Float.isInfinite(value);
    }

    /**
     * 判断浮点数是否为正无穷大。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是正无穷大返回true，否则返回false
     */
    public static boolean isPositiveInfinite(final float value) {
        return value == Float.POSITIVE_INFINITY;
    }

    /**
     * 判断浮点数是否为负无穷大。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是负无穷大返回true，否则返回false
     */
    public static boolean isNegativeInfinite(final float value) {
        return value == Float.NEGATIVE_INFINITY;
    }

    /**
     * 判断浮点数是否为NaN（非数字）。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是NaN返回true，否则返回false
     */
    public static boolean isNaN(final float value) {
        return Float.isNaN(value);
    }

    /**
     * 判断浮点数是否为安静NaN。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是安静NaN返回true，否则返回false
     */
    public static boolean isQuietNaN(final float value) {
        final int bits = toBits(value);
        return (bits & QUIET_NAN_MASK) == QUIET_NAN_MASK;
    }

    /**
     * 判断浮点数是否有限。
     *
     * @param value
     *     要判断的浮点数
     * @return
     *     如果是有限数返回true，否则返回false
     */
    public static boolean isFinite(final float value) {
        return Float.isFinite(value);
    }

    /**
     * 获取浮点数的符号。
     *
     * @param value
     *     要获取符号的浮点数
     * @return
     *     1表示正数，-1表示负数，0表示零
     */
    public static int sign(final float value) {
        if (isZero(value)) {
            return 0;
        }
        return (getSignBit(toBits(value)) == 0) ? 1 : -1;
    }

    /**
     * 构造浮点数。
     *
     * @param sign
     *     符号位，0表示正数，非0表示负数
     * @param exponent
     *     指数（未加偏移量）
     * @param mantissa
     *     尾数
     * @return
     *     构造的浮点数
     */
    public static float compose(final int sign, final int exponent, final int mantissa) {
        int bits = 0;

        // 设置符号位
        if (sign != 0) {
            bits |= SIGN_BIT_MASK;
        }

        // 设置指数位（加上偏移量）
        final int biasedExponent = exponent + EXPONENT_BIAS;
        bits |= (biasedExponent & 0xFF) << MANTISSA_BITS;

        // 设置尾数位
        bits |= mantissa & MANTISSA_MASK;

        return fromBits(bits);
    }

    /**
     * 复制符号位。
     *
     * @param magnitude
     *     提供数值大小的浮点数
     * @param sign
     *     提供符号的浮点数
     * @return
     *     具有magnitude的大小和sign符号的浮点数
     */
    public static float copySign(final float magnitude, final float sign) {
        return Math.copySign(magnitude, sign);
    }

    /**
     * 获取下一个可表示的浮点数。
     *
     * @param value
     *     当前浮点数
     * @return
     *     下一个可表示的浮点数
     */
    public static float nextUp(final float value) {
        return Math.nextUp(value);
    }

    /**
     * 获取上一个可表示的浮点数。
     *
     * @param value
     *     当前浮点数
     * @return
     *     上一个可表示的浮点数
     */
    public static float nextDown(final float value) {
        return Math.nextDown(value);
    }
}