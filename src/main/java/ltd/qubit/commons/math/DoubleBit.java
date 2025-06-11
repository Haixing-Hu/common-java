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
 * 提供对IEEE 754双精度浮点数（double）的底层位操作工具类。
 *
 * <p>该类提供了一系列静态方法来操作和解析64位双精度浮点数的内部
 * 二进制表示，包括符号位、指数位和尾数位的提取与构造。
 *
 * <p>IEEE 754双精度浮点数格式：
 * <ul>
 * <li>总位数：64位</li>
 * <li>符号位：1位（第63位）</li>
 * <li>指数位：11位（第62-52位）</li>
 * <li>尾数位：52位（第51-0位）</li>
 * <li>指数偏移量：1023</li>
 * </ul>
 *
 * @author 胡海星
 */
public final class DoubleBit {

    /**
     * 总位数
     */
    public static final int BITS = 64;

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
    public static final int EXPONENT_BITS = 11;

    /**
     * 尾数位数
     */
    public static final int MANTISSA_BITS = 52;

    /**
     * 最大二进制精度位数
     */
    public static final int DIGITS = 53;

    /**
     * 最大十进制精度位数
     */
    public static final int DIGITS_10 = 15;

    /**
     * 指数偏移量
     */
    public static final int EXPONENT_BIAS = 1023;

    /**
     * 标准化数的最小二进制指数
     */
    public static final int MIN_EXPONENT = -1022;

    /**
     * 最大二进制指数
     */
    public static final int MAX_EXPONENT = 1023;

    /**
     * 标准化数的最小十进制指数
     */
    public static final int MIN_EXPONENT_10 = -307;

    /**
     * 最大十进制指数
     */
    public static final int MAX_EXPONENT_10 = 308;

    /**
     * 非标准化数的最小二进制指数
     */
    public static final int DENORM_MIN_EXPONENT = -1074;

    /**
     * 非标准化数的最小十进制指数
     */
    public static final int DENORM_MIN_EXPONENT_10 = -323;

    /**
     * 符号位掩码
     */
    public static final long SIGN_BIT_MASK = 0x8000000000000000L;

    /**
     * 隐含位掩码
     */
    public static final long HIDDEN_BITS_MASK = 0x0010000000000000L;

    /**
     * 绝对值掩码（去除符号位）
     */
    public static final long ABS_MASK = 0x7FFFFFFFFFFFFFFFL;

    /**
     * 指数位掩码
     */
    public static final long EXPONENT_MASK = 0x7FF0000000000000L;

    /**
     * 尾数位掩码
     */
    public static final long MANTISSA_MASK = 0x000FFFFFFFFFFFFFL;

    /**
     * 安静NaN掩码
     */
    public static final long QUIET_NAN_MASK = 0x7FF8000000000000L;

    /**
     * 最大精确整数
     */
    public static final long MAX_EXACT_INT = 0x0020000000000000L;

    /**
     * 私有构造函数，防止实例化
     */
    private DoubleBit() {
        // 工具类，不允许实例化
    }

    /**
     * 将双精度浮点数转换为对应的位表示。
     *
     * @param value
     *     要转换的双精度浮点数
     * @return
     *     双精度浮点数的64位长整数表示
     */
    public static long toBits(final double value) {
        return Double.doubleToRawLongBits(value);
    }

    /**
     * 将位表示转换为对应的双精度浮点数。
     *
     * @param bits
     *     64位长整数表示
     * @return
     *     对应的双精度浮点数
     */
    public static double fromBits(final long bits) {
        return Double.longBitsToDouble(bits);
    }

    /**
     * 从位表示中获取符号位。
     *
     * @param bits
     *     双精度浮点数的位表示
     * @return
     *     符号位，0表示正数，非0表示负数
     */
    public static long getSignBit(final long bits) {
        return bits & SIGN_BIT_MASK;
    }

    /**
     * 从位表示中获取尾数部分。
     *
     * @param bits
     *     双精度浮点数的位表示
     * @return
     *     尾数部分（52位）
     */
    public static long getMantissa(final long bits) {
        return bits & MANTISSA_MASK;
    }

    /**
     * 从位表示中获取指数部分。
     *
     * @param bits
     *     双精度浮点数的位表示
     * @return
     *     指数部分（11位，未减去偏移量）
     */
    public static int getExponent(final long bits) {
        return (int) ((bits & EXPONENT_MASK) >>> MANTISSA_BITS);
    }

    /**
     * 从位表示中获取实际指数值（已减去偏移量）。
     *
     * @param bits
     *     双精度浮点数的位表示
     * @return
     *     实际指数值
     */
    public static int getActualExponent(final long bits) {
        return getExponent(bits) - EXPONENT_BIAS;
    }

    /**
     * 判断双精度浮点数是否为零。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是零（包括正零和负零）返回true，否则返回false
     */
    public static boolean isZero(final double value) {
        return (toBits(value) & ABS_MASK) == 0;
    }

    /**
     * 判断双精度浮点数是否为正零。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是正零返回true，否则返回false
     */
    public static boolean isPositiveZero(final double value) {
        return toBits(value) == 0;
    }

    /**
     * 判断双精度浮点数是否为负零。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是负零返回true，否则返回false
     */
    public static boolean isNegativeZero(final double value) {
        return toBits(value) == SIGN_BIT_MASK;
    }

    /**
     * 判断双精度浮点数是否为标准化数。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是标准化数返回true，否则返回false
     */
    public static boolean isNormalized(final double value) {
        final long bits = toBits(value);
        final int exponent = getExponent(bits);
        return exponent > 0 && exponent < 0x7FF;
    }

    /**
     * 判断双精度浮点数是否为非标准化数。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是非标准化数返回true，否则返回false
     */
    public static boolean isDenormalized(final double value) {
        final long bits = toBits(value);
        final int exponent = getExponent(bits);
        final long mantissa = getMantissa(bits);
        return exponent == 0 && mantissa != 0;
    }

    /**
     * 判断双精度浮点数是否为无穷大。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是无穷大（包括正无穷和负无穷）返回true，否则返回false
     */
    public static boolean isInfinite(final double value) {
        return Double.isInfinite(value);
    }

    /**
     * 判断双精度浮点数是否为正无穷大。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是正无穷大返回true，否则返回false
     */
    public static boolean isPositiveInfinite(final double value) {
        return value == Double.POSITIVE_INFINITY;
    }

    /**
     * 判断双精度浮点数是否为负无穷大。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是负无穷大返回true，否则返回false
     */
    public static boolean isNegativeInfinite(final double value) {
        return value == Double.NEGATIVE_INFINITY;
    }

    /**
     * 判断双精度浮点数是否为NaN（非数字）。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是NaN返回true，否则返回false
     */
    public static boolean isNaN(final double value) {
        return Double.isNaN(value);
    }

    /**
     * 判断双精度浮点数是否为安静NaN。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是安静NaN返回true，否则返回false
     */
    public static boolean isQuietNaN(final double value) {
        final long bits = toBits(value);
        return (bits & QUIET_NAN_MASK) == QUIET_NAN_MASK;
    }

    /**
     * 判断双精度浮点数是否有限。
     *
     * @param value
     *     要判断的双精度浮点数
     * @return
     *     如果是有限数返回true，否则返回false
     */
    public static boolean isFinite(final double value) {
        return Double.isFinite(value);
    }

    /**
     * 获取双精度浮点数的符号。
     *
     * @param value
     *     要获取符号的双精度浮点数
     * @return
     *     1表示正数，-1表示负数，0表示零
     */
    public static int sign(final double value) {
        if (isZero(value)) {
            return 0;
        }
        return (getSignBit(toBits(value)) == 0) ? 1 : -1;
    }

    /**
     * 构造双精度浮点数。
     *
     * @param sign
     *     符号位，0表示正数，非0表示负数
     * @param exponent
     *     指数（未加偏移量）
     * @param mantissa
     *     尾数
     * @return
     *     构造的双精度浮点数
     */
    public static double compose(final int sign, final int exponent, final long mantissa) {
        long bits = 0;

        // 设置符号位
        if (sign != 0) {
            bits |= SIGN_BIT_MASK;
        }

        // 设置指数位（加上偏移量）
        final long biasedExponent = exponent + EXPONENT_BIAS;
        bits |= (biasedExponent & 0x7FFL) << MANTISSA_BITS;

        // 设置尾数位
        bits |= mantissa & MANTISSA_MASK;

        return fromBits(bits);
    }

    /**
     * 复制符号位。
     *
     * @param magnitude
     *     提供数值大小的双精度浮点数
     * @param sign
     *     提供符号的双精度浮点数
     * @return
     *     具有magnitude的大小和sign符号的双精度浮点数
     */
    public static double copySign(final double magnitude, final double sign) {
        return Math.copySign(magnitude, sign);
    }

    /**
     * 获取下一个可表示的双精度浮点数。
     *
     * @param value
     *     当前双精度浮点数
     * @return
     *     下一个可表示的双精度浮点数
     */
    public static double nextUp(final double value) {
        return Math.nextUp(value);
    }

    /**
     * 获取上一个可表示的双精度浮点数。
     *
     * @param value
     *     当前双精度浮点数
     * @return
     *     上一个可表示的双精度浮点数
     */
    public static double nextDown(final double value) {
        return Math.nextDown(value);
    }
}