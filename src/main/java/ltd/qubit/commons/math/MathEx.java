////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import static ltd.qubit.commons.lang.Argument.requireInCloseRange;

import javax.annotation.Nullable;

/**
 * 扩展的数学工具类，提供比{@link java.lang.Math}更丰富的数学运算功能。
 *
 * <p>本类与{@link java.lang.Math}的主要区别如下：</p>
 * <ul>
 *   <li><strong>完整性</strong>：包含{@link java.lang.Math}的所有方法，提供统一的访问接口</li>
 *   <li><strong>扩展性</strong>：新增了多种数学运算方法，包括：
 *     <ul>
 *       <li>随机数生成：支持指定范围的随机数生成（byte、short、int、long、double等类型）</li>
 *       <li>浮点数比较：提供基于精度容差的相等性判断和范围检查</li>
 *       <li>数值限制：提供clamp方法将数值限制在指定范围内</li>
 *       <li>对数运算：新增以2为底的对数计算</li>
 *       <li>幂运算：新增10的幂次方计算</li>
 *     </ul>
 *   </li>
 *   <li><strong>便利性</strong>：为常用数学常量和操作提供更方便的访问方式</li>
 *   <li><strong>精度控制</strong>：内置默认的浮点数精度常量，简化浮点数比较操作</li>
 *   <li><strong>类型支持</strong>：为byte和short类型提供了max、min、clamp等操作的支持</li>
 * </ul>
 *
 * <p>使用本类可以避免在不同工具类之间切换，提供了一站式的数学运算解决方案。
 * 所有方法都是静态的，遵循{@link java.lang.Math}的设计模式。</p>
 *
 * @author 胡海星
 */
public final class MathEx {

  // stop checkstyle: MethodName
  // stop checkstyle: AbbreviationAsWordInName
  // stop checkstyle: MagicNumber

  /**
   * 自然常数e的值。
   * <p>
   * e是自然对数的底数，约等于2.718281828459045，是数学中最重要的常数之一。
   * 它在微积分、概率论、复分析等数学领域有广泛应用。
   */
  public static final double E = 2.718281828459045D;

  /**
   * 圆周率π的值。
   * <p>
   * π是圆的周长与直径的比值，约等于3.141592653589793，是几何学中最重要的常数。
   * 它在三角函数、圆形和球体计算、波动方程等领域有广泛应用。
   */
  public static final double PI = 3.141592653589793D;

  /**
   * 2的自然对数值。
   * <p>
   * ln(2)约等于0.6931471805599453，在信息论、概率论和计算机科学中经常使用。
   * 特别是在计算以2为底的对数时，可以使用换底公式：log₂(x) = ln(x) / ln(2)。
   */
  public static final double LOG_OF_2 = Math.log(2);

  /**
   * 默认的float类型精度值。
   * <p>
   * 设置为1e-6F，这是一个相对严格的精度值，具有以下特点：
   * <ul>
   *   <li><strong>精度选择</strong>: 1e-6F约为float类型机器精度(~1.2e-7F)的8倍，
   *       在数值稳定性和实用性之间提供了良好的平衡</li>
   *   <li><strong>应用场景</strong>: 适用于大多数科学计算、几何运算、物理仿真等场景，
   *       能够有效区分真正的数值差异和浮点运算误差</li>
   *   <li><strong>兼容性</strong>: 比传统的1e-3F严格1000倍，提供更高的计算精度</li>
   *   <li><strong>性能考虑</strong>: 仍然足够宽松以避免因微小的数值噪声导致的误判</li>
   * </ul>
   *
   * @see Float#MIN_NORMAL 最小规范化float值
   * @see #DEFAULT_DOUBLE_EPSILON 对应的double精度值
   */
  public static final float DEFAULT_FLOAT_EPSILON = 1e-6F;

  /**
   * 默认的double类型精度值。
   * <p>
   * 设置为1e-12D，这是一个相对严格的精度值，具有以下特点：
   * <ul>
   *   <li><strong>精度选择</strong>: 1e-12D约为double类型机器精度(~2.2e-16D)的万倍，
   *       提供了高精度计算所需的严格容差</li>
   *   <li><strong>应用场景</strong>: 适用于高精度科学计算、金融计算、统计分析等
   *       对数值准确性要求极高的场景</li>
   *   <li><strong>兼容性</strong>: 比传统的1e-6D严格100万倍，大幅提升了比较的准确性</li>
   *   <li><strong>数值稳定性</strong>: 在保证高精度的同时，仍为累积的浮点误差预留了充足空间</li>
   * </ul>
   *
   * @see Double#MIN_NORMAL 最小规范化double值
   * @see #DEFAULT_FLOAT_EPSILON 对应的float精度值
   */
  public static final double DEFAULT_DOUBLE_EPSILON = 1e-12D;

  /**
   * 全局的float精度值，用于浮点数比较。
   * <p>
   * 使用volatile保证线程安全的可见性。
   */
  private static volatile float floatEpsilon = DEFAULT_FLOAT_EPSILON;

  /**
   * 全局的double精度值，用于浮点数比较。
   * <p>
   * 使用volatile保证线程安全的可见性。
   */
  private static volatile double doubleEpsilon = DEFAULT_DOUBLE_EPSILON;

  private MathEx() {}

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      全局精度配置
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 获取全局的float精度值。
   *
   * @return
   *     当前全局的float精度值
   */
  public static float getFloatEpsilon() {
    return floatEpsilon;
  }

  /**
   * 设置全局的float精度值。
   * <p>
   * 此方法是线程安全的，新设置的值会立即对所有线程可见。
   *
   * @param epsilon
   *     新的float精度值，必须大于0且不能为NaN
   * @throws IllegalArgumentException
   *     如果epsilon小于等于0或为NaN
   */
  public static void setFloatEpsilon(final float epsilon) {
    if (Float.isNaN(epsilon) || epsilon <= 0.0f) {
      throw new IllegalArgumentException("epsilon must be positive and not NaN: " + epsilon);
    }
    floatEpsilon = epsilon;
  }

  /**
   * 获取全局的double精度值。
   *
   * @return
   *     当前全局的double精度值
   */
  public static double getDoubleEpsilon() {
    return doubleEpsilon;
  }

  /**
   * 设置全局的double精度值。
   * <p>
   * 此方法是线程安全的，新设置的值会立即对所有线程可见。
   *
   * @param epsilon
   *     新的double精度值，必须大于0且不能为NaN
   * @throws IllegalArgumentException
   *     如果epsilon小于等于0或为NaN
   */
  public static void setDoubleEpsilon(final double epsilon) {
    if (Double.isNaN(epsilon) || epsilon <= 0.0) {
      throw new IllegalArgumentException("epsilon must be positive and not NaN: " + epsilon);
    }
    doubleEpsilon = epsilon;
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      三角函数
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 计算指定角度的正弦值。
   *
   * @param a
   *     角度值（弧度）
   * @return
   *     正弦值
   */
  public static double sin(final double a) {
    return Math.sin(a);
  }

  /**
   * 计算指定角度的余弦值。
   *
   * @param a
   *     角度值（弧度）
   * @return
   *     余弦值
   */
  public static double cos(final double a) {
    return Math.cos(a);
  }

  /**
   * 计算指定角度的正切值。
   *
   * @param a
   *     角度值（弧度）
   * @return
   *     正切值
   */
  public static double tan(final double a) {
    return Math.tan(a);
  }

  /**
   * 计算指定值的反正弦值。
   *
   * @param a
   *     值，必须在[-1, 1]范围内
   * @return
   *     反正弦值（弧度）
   */
  public static double asin(final double a) {
    return Math.asin(a);
  }

  /**
   * 计算指定值的反余弦值。
   *
   * @param a
   *     值，必须在[-1, 1]范围内
   * @return
   *     反余弦值（弧度）
   */
  public static double acos(final double a) {
    return Math.acos(a);
  }

  /**
   * 计算指定值的反正切值。
   *
   * @param a
   *     值
   * @return
   *     反正切值（弧度）
   */
  public static double atan(final double a) {
    return Math.atan(a);
  }

  /**
   * 计算两个参数的反正切值，考虑象限。
   *
   * @param y
   *     y坐标
   * @param x
   *     x坐标
   * @return
   *     从x轴正方向到点(x,y)的角度（弧度）
   */
  public static double atan2(final double y, final double x) {
    return Math.atan2(y, x);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      双曲函数
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 计算双曲正弦值。
   *
   * @param x
   *     值
   * @return
   *     sinh(x)
   */
  public static double sinh(final double x) {
    return Math.sinh(x);
  }

  /**
   * 计算双曲余弦值。
   *
   * @param x
   *     值
   * @return
   *     cosh(x)
   */
  public static double cosh(final double x) {
    return Math.cosh(x);
  }

  /**
   * 计算双曲正切值。
   *
   * @param x
   *     值
   * @return
   *     tanh(x)
   */
  public static double tanh(final double x) {
    return Math.tanh(x);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      角度转换
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 将角度从度转换为弧度。
   *
   * @param degree
   *     角度值（度）
   * @return
   *     角度值（弧度）
   */
  public static double toRadians(final double degree) {
    return Math.toRadians(degree);
  }

  /**
   * 将角度从弧度转换为度。
   *
   * @param radian
   *     角度值（弧度）
   * @return
   *     角度值（度）
   */
  public static double toDegrees(final double radian) {
    return Math.toDegrees(radian);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      自然指数/自然对数
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 计算e的指定次幂。
   *
   * @param a
   *     指数
   * @return
   *     e^a的值
   */
  public static double exp(final double a) {
    return Math.exp(a);
  }

  /**
   * 计算 e^x - 1，对接近零的x值提供更好的精度。
   *
   * @param x
   *     指数
   * @return
   *     e^x - 1
   */
  public static double expm1(final double x) {
    return Math.expm1(x);
  }

  /**
   * 计算指定值的自然对数（以e为底）。
   *
   * @param a
   *     值，必须大于0
   * @return
   *     自然对数值
   */
  public static double log(final double a) {
    return Math.log(a);
  }

  /**
   * 计算指定值的以2为底的对数。
   *
   * @param x
   *     值，必须大于0
   * @return
   *     以2为底的对数值
   */
  public static double log2(final double x) {
    return Math.log(x) / LOG_OF_2;
  }

  /**
   * 计算指定值的常用对数（以10为底）。
   *
   * @param a
   *     值，必须大于0
   * @return
   *     常用对数值
   */
  public static double log10(final double a) {
    return Math.log10(a);
  }

  /**
   * 计算 ln(1 + x)，对接近零的x值提供更好的精度。
   *
   * @param x
   *     值
   * @return
   *     ln(1 + x)
   */
  public static double log1p(final double x) {
    return Math.log1p(x);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      幂和开方
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 计算指定底数的指定次幂。
   *
   * @param a
   *     底数
   * @param b
   *     指数
   * @return
   *     a^b的值
   */
  public static double pow(final double a, final double b) {
    return Math.pow(a, b);
  }

  private static final long[] POW_2 = {
      1L << 0,                // 2^0
      1L << 1,                // 2^1
      1L << 2,                // 2^2
      1L << 3,                // 2^3
      1L << 4,                // 2^4
      1L << 5,                // 2^5
      1L << 6,                // 2^6
      1L << 7,                // 2^7
      1L << 8,                // 2^8
      1L << 9,                // 2^9
      1L << 10,               // 2^10
      1L << 11,               // 2^11
      1L << 12,               // 2^12
      1L << 13,               // 2^13
      1L << 14,               // 2^14
      1L << 15,               // 2^15
      1L << 16,               // 2^16
      1L << 17,               // 2^17
      1L << 18,               // 2^18
      1L << 19,               // 2^19
      1L << 20,               // 2^20
      1L << 21,               // 2^21
      1L << 22,               // 2^22
      1L << 23,               // 2^23
      1L << 24,               // 2^24
      1L << 25,               // 2^25
      1L << 26,               // 2^26
      1L << 27,               // 2^27
      1L << 28,               // 2^28
      1L << 29,               // 2^29
      1L << 30,               // 2^30
      1L << 31,               // 2^31
      1L << 32,               // 2^32
      1L << 33,               // 2^33
      1L << 34,               // 2^34
      1L << 35,               // 2^35
      1L << 36,               // 2^36
      1L << 37,               // 2^37
      1L << 38,               // 2^38
      1L << 39,               // 2^39
      1L << 40,               // 2^40
      1L << 41,               // 2^41
      1L << 42,               // 2^42
      1L << 43,               // 2^43
      1L << 44,               // 2^44
      1L << 45,               // 2^45
      1L << 46,               // 2^46
      1L << 47,               // 2^47
      1L << 48,               // 2^48
      1L << 49,               // 2^49
      1L << 50,               // 2^50
      1L << 51,               // 2^51
      1L << 52,               // 2^52
      1L << 53,               // 2^53
      1L << 54,               // 2^54
      1L << 55,               // 2^55
      1L << 56,               // 2^56
      1L << 57,               // 2^57
      1L << 58,               // 2^58
      1L << 59,               // 2^59
      1L << 60,               // 2^60
      1L << 61,               // 2^61
      1L << 62,               // 2^62
  };

  public static final int MAX_2_EXPONENT = 62;

  /**
   * 计算2的幂。
   *
   * @param e
   *     幂的指数，必须位于{@code 0}和{@value MAX_2_EXPONENT}之间。
   * @return
   *     2的指定的幂，即{@code 2^e}. 注意返回值不能超过{@value Long#MAX_VALUE}.
   */
  public static long pow2(final int e) {
    requireInCloseRange("e", e, 0, MAX_2_EXPONENT);
    return POW_2[e];
  }

  private static final long[] POW_10 = {
      1L,
      10L,
      100L,
      1000L,
      10000L,
      100000L,
      1000000L,
      10000000L,
      100000000L,
      1000000000L,
      10000000000L,
      100000000000L,
      1000000000000L,
      10000000000000L,
      100000000000000L,
      1000000000000000L,
      10000000000000000L,
      100000000000000000L,
      1000000000000000000L,
  };

  public static final int MAX_10_EXPONENT = 18;

  /**
   * 计算10的幂。
   *
   * @param e
   *     幂的指数，必须位于{@code 0}和{@value MAX_10_EXPONENT}之间。
   * @return
   *     10的指定的幂，即{@code 10^e}. 注意返回值不能超过{@value Long#MAX_VALUE}.
   */
  public static long pow10(final int e) {
    requireInCloseRange("e", e, 0, MAX_10_EXPONENT);
    return POW_10[e];
  }

  /**
   * 计算指定值的平方根。
   *
   * @param a
   *     值，必须大于等于0
   * @return
   *     平方根值
   */
  public static double sqrt(final double a) {
    return Math.sqrt(a);
  }

  /**
   * 计算指定值的立方根。
   *
   * @param a
   *     值
   * @return
   *     立方根值
   */
  public static double cbrt(final double a) {
    return Math.cbrt(a);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      取整
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 返回大于等于指定值的最小整数（向上取整）。
   *
   * @param a
   *     值
   * @return
   *     天花板值
   */
  public static float ceil(final float a) {
    return (float) Math.ceil(a);
  }

  /**
   * 返回大于等于指定值的最小整数（向上取整）。
   *
   * @param a
   *     值
   * @return
   *     天花板值
   */
  public static double ceil(final double a) {
    return Math.ceil(a);
  }

  /**
   * 返回小于等于指定值的最大整数（向下取整）。
   *
   * @param a
   *     值
   * @return
   *     地板值
   */
  public static float floor(final float a) {
    return (float) Math.floor(a);
  }

  /**
   * 返回小于等于指定值的最大整数（向下取整）。
   *
   * @param a
   *     值
   * @return
   *     地板值
   */
  public static double floor(final double a) {
    return Math.floor(a);
  }

  /**
   * 返回最接近指定值的整数值。
   *
   * @param a
   *     值
   * @return
   *     最接近的整数值
   */
  public static float rint(final float a) {
    return (float) Math.rint(a);
  }

  /**
   * 返回最接近指定值的整数值。
   *
   * @param a
   *     值
   * @return
   *     最接近的整数值
   */
  public static double rint(final double a) {
    return Math.rint(a);
  }

  /**
   * 将float值四舍五入为最接近的{@code int}值。
   *
   * @param a
   *     float值
   * @return
   *     四舍五入后的{@code int}值
   */
  public static int round(final float a) {
    return Math.round(a);
  }

  /**
   * 将double值四舍五入为最接近的{@code long}值。
   *
   * @param a
   *     double值
   * @return
   *     四舍五入后的{@code long}值
   */
  public static long round(final double a) {
    return Math.round(a);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      除法余数
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 计算IEEE 754标准的浮点余数。
   *
   * @param f1
   *     被除数
   * @param f2
   *     除数
   * @return
   *     IEEE 754余数
   */
  public static float IEEEremainder(final float f1, final float f2) {
    return (float) Math.IEEEremainder(f1, f2);
  }

  /**
   * 计算IEEE 754标准的浮点余数。
   *
   * @param f1
   *     被除数
   * @param f2
   *     除数
   * @return
   *     IEEE 754余数
   */
  public static double IEEEremainder(final double f1, final double f2) {
    return Math.IEEEremainder(f1, f2);
  }

  /**
   * 计算int除法的向下取整商。
   *
   * @param x
   *     被除数
   * @param y
   *     除数
   * @return
   *     x除以y的向下取整商
   */
  public static int floorDiv(final int x, final int y) {
    return Math.floorDiv(x, y);
  }

  /**
   * 计算long除以int的向下取整商。
   *
   * @param x
   *     被除数
   * @param y
   *     除数
   * @return
   *     x除以y的向下取整商
   */
  public static long floorDiv(final long x, final int y) {
    return Math.floorDiv(x, y);
  }

  /**
   * 计算long除法的向下取整商。
   *
   * @param x
   *     被除数
   * @param y
   *     除数
   * @return
   *     x除以y的向下取整商
   */
  public static long floorDiv(final long x, final long y) {
    return Math.floorDiv(x, y);
  }

  /**
   * 计算int除法的向下取整模。
   *
   * @param x
   *     被除数
   * @param y
   *     除数
   * @return
   *     x除以y的向下取整模
   */
  public static int floorMod(final int x, final int y) {
    return Math.floorMod(x, y);
  }

  /**
   * 计算long除以int的向下取整模。
   *
   * @param x
   *     被除数
   * @param y
   *     除数
   * @return
   *     x除以y的向下取整模
   */
  public static int floorMod(final long x, final int y) {
    return Math.floorMod(x, y);
  }

  /**
   * 计算long除法的向下取整模。
   *
   * @param x
   *     被除数
   * @param y
   *     除数
   * @return
   *     x除以y的向下取整模
   */
  public static long floorMod(final long x, final long y) {
    return Math.floorMod(x, y);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      绝对值
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 返回{@code byte}值的绝对值。
   *
   * @param a
   *     值
   * @return
   *     a的绝对值
   */
  public static byte abs(final byte a) {
    return (byte) (a < 0 ? -a : a);
  }

  /**
   * 返回{@code short}值的绝对值。
   *
   * @param a
   *     值
   * @return
   *     a的绝对值
   */
  public static short abs(final short a) {
    return (short) (a < 0 ? -a : a);
  }

  /**
   * 返回{@code int}值的绝对值。
   *
   * @param a
   *     值
   * @return
   *     a的绝对值
   */
  public static int abs(final int a) {
    return (a < 0 ? -a : a);
  }

  /**
   * 返回{@code long}值的绝对值。
   *
   * @param a
   *     值
   * @return
   *     a的绝对值
   */
  public static long abs(final long a) {
    return (a < 0L ? -a : a);
  }

  /**
   * 返回float值的绝对值。
   *
   * @param a
   *     值
   * @return
   *     a的绝对值
   */
  public static float abs(final float a) {
    return (a <= 0.0f ? 0.0f - a : a);
  }

  /**
   * 返回double值的绝对值。
   *
   * @param a
   *     值
   * @return
   *     a的绝对值
   */
  public static double abs(final double a) {
    return (a <= 0.0 ? 0.0 - a : a);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      最大/最小值
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 返回两个byte值中的较小者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较小者
   */
  public static byte min(final byte a, final byte b) {
    return (byte) Math.min(a, b);
  }

  /**
   * 返回两个short值中的较小者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较小者
   */
  public static short min(final short a, final short b) {
    return (short) Math.min(a, b);
  }

  /**
   * 返回两个{@code int}值中的较小者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较小者
   */
  public static int min(final int a, final int b) {
    return Math.min(a, b);
  }

  /**
   * 返回两个{@code long}值中的较小者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较小者
   */
  public static long min(final long a, final long b) {
    return Math.min(a, b);
  }

  /**
   * 返回两个float值中的较小者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较小者
   */
  public static float min(final float a, final float b) {
    return Math.min(a, b);
  }

  /**
   * 返回两个double值中的较小者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较小者
   */
  public static double min(final double a, final double b) {
    return Math.min(a, b);
  }

  /**
   * 返回两个byte值中的较大者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较大者
   */
  public static byte max(final byte a, final byte b) {
    return (byte) Math.max(a, b);
  }

  /**
   * 返回两个short值中的较大者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较大者
   */
  public static short max(final short a, final short b) {
    return (short) Math.max(a, b);
  }

  /**
   * 返回两个{@code int}值中的较大者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较大者
   */
  public static int max(final int a, final int b) {
    return Math.max(a, b);
  }

  /**
   * 返回两个{@code long}值中的较大者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较大者
   */
  public static long max(final long a, final long b) {
    return Math.max(a, b);
  }

  /**
   * 返回两个float值中的较大者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较大者
   */
  public static float max(final float a, final float b) {
    return Math.max(a, b);
  }

  /**
   * 返回两个double值中的较大者。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @return
   *     a和b中的较大者
   */
  public static double max(final double a, final double b) {
    return Math.max(a, b);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      多参数最小/最大值
  //
  // ///////////////////////////////////////////////////////////////////////////

  // ===========================================================================
  // 三参数最小值函数
  // ===========================================================================

  /**
   * 返回三个byte值中的最小者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最小者
   */
  public static byte min(final byte a, final byte b, final byte c) {
    return min(min(a, b), c);
  }

  /**
   * 返回三个short值中的最小者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最小者
   */
  public static short min(final short a, final short b, final short c) {
    return min(min(a, b), c);
  }

  /**
   * 返回三个{@code int}值中的最小者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最小者
   */
  public static int min(final int a, final int b, final int c) {
    return min(min(a, b), c);
  }

  /**
   * 返回三个{@code long}值中的最小者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最小者
   */
  public static long min(final long a, final long b, final long c) {
    return min(min(a, b), c);
  }

  /**
   * 返回三个float值中的最小者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最小者
   */
  public static float min(final float a, final float b, final float c) {
    return min(min(a, b), c);
  }

  /**
   * 返回三个double值中的最小者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最小者
   */
  public static double min(final double a, final double b, final double c) {
    return min(min(a, b), c);
  }

  // ===========================================================================
  // 四参数最小值函数
  // ===========================================================================

  /**
   * 返回四个byte值中的最小者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最小者
   */
  public static byte min(final byte a, final byte b, final byte c, final byte d) {
    return min(min(a, b, c), d);
  }

  /**
   * 返回四个short值中的最小者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最小者
   */
  public static short min(final short a, final short b, final short c, final short d) {
    return min(min(a, b, c), d);
  }

  /**
   * 返回四个{@code int}值中的最小者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最小者
   */
  public static int min(final int a, final int b, final int c, final int d) {
    return min(min(a, b, c), d);
  }

  /**
   * 返回四个{@code long}值中的最小者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最小者
   */
  public static long min(final long a, final long b, final long c, final long d) {
    return min(min(a, b, c), d);
  }

  /**
   * 返回四个float值中的最小者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最小者
   */
  public static float min(final float a, final float b, final float c, final float d) {
    return min(min(a, b, c), d);
  }

  /**
   * 返回四个double值中的最小者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最小者
   */
  public static double min(final double a, final double b, final double c, final double d) {
    return min(min(a, b, c), d);
  }

  // ===========================================================================
  // 五参数最小值函数
  // ===========================================================================

  /**
   * 返回五个byte值中的最小者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最小者
   */
  public static byte min(final byte a, final byte b, final byte c, final byte d, final byte e) {
    return min(min(a, b, c, d), e);
  }

  /**
   * 返回五个short值中的最小者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最小者
   */
  public static short min(final short a, final short b, final short c, final short d, final short e) {
    return min(min(a, b, c, d), e);
  }

  /**
   * 返回五个{@code int}值中的最小者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最小者
   */
  public static int min(final int a, final int b, final int c, final int d, final int e) {
    return min(min(a, b, c, d), e);
  }

  /**
   * 返回五个{@code long}值中的最小者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最小者
   */
  public static long min(final long a, final long b, final long c, final long d, final long e) {
    return min(min(a, b, c, d), e);
  }

  /**
   * 返回五个float值中的最小者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最小者
   */
  public static float min(final float a, final float b, final float c, final float d, final float e) {
    return min(min(a, b, c, d), e);
  }

  /**
   * 返回五个double值中的最小者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最小者
   */
  public static double min(final double a, final double b, final double c, final double d, final double e) {
    return min(min(a, b, c, d), e);
  }

  // ===========================================================================
  // 三参数最大值函数
  // ===========================================================================

  /**
   * 返回三个byte值中的最大者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最大者
   */
  public static byte max(final byte a, final byte b, final byte c) {
    return max(max(a, b), c);
  }

  /**
   * 返回三个short值中的最大者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最大者
   */
  public static short max(final short a, final short b, final short c) {
    return max(max(a, b), c);
  }

  /**
   * 返回三个{@code int}值中的最大者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最大者
   */
  public static int max(final int a, final int b, final int c) {
    return max(max(a, b), c);
  }

  /**
   * 返回三个{@code long}值中的最大者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最大者
   */
  public static long max(final long a, final long b, final long c) {
    return max(max(a, b), c);
  }

  /**
   * 返回三个float值中的最大者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最大者
   */
  public static float max(final float a, final float b, final float c) {
    return max(max(a, b), c);
  }

  /**
   * 返回三个double值中的最大者。
   * <p>
   * 使用最优算法，总共进行2次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @return
   *     a、b和c中的最大者
   */
  public static double max(final double a, final double b, final double c) {
    return max(max(a, b), c);
  }

  // ===========================================================================
  // 四参数最大值函数
  // ===========================================================================

  /**
   * 返回四个byte值中的最大者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最大者
   */
  public static byte max(final byte a, final byte b, final byte c, final byte d) {
    return max(max(a, b, c), d);
  }

  /**
   * 返回四个short值中的最大者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最大者
   */
  public static short max(final short a, final short b, final short c, final short d) {
    return max(max(a, b, c), d);
  }

  /**
   * 返回四个{@code int}值中的最大者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最大者
   */
  public static int max(final int a, final int b, final int c, final int d) {
    return max(max(a, b, c), d);
  }

  /**
   * 返回四个{@code long}值中的最大者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最大者
   */
  public static long max(final long a, final long b, final long c, final long d) {
    return max(max(a, b, c), d);
  }

  /**
   * 返回四个float值中的最大者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最大者
   */
  public static float max(final float a, final float b, final float c, final float d) {
    return max(max(a, b, c), d);
  }

  /**
   * 返回四个double值中的最大者。
   * <p>
   * 使用最优算法，总共进行3次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @return
   *     a、b、c和d中的最大者
   */
  public static double max(final double a, final double b, final double c, final double d) {
    return max(max(a, b, c), d);
  }

  // ===========================================================================
  // 五参数最大值函数
  // ===========================================================================

  /**
   * 返回五个byte值中的最大者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最大者
   */
  public static byte max(final byte a, final byte b, final byte c, final byte d, final byte e) {
    return max(max(a, b, c, d), e);
  }

  /**
   * 返回五个short值中的最大者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最大者
   */
  public static short max(final short a, final short b, final short c, final short d, final short e) {
    return max(max(a, b, c, d), e);
  }

  /**
   * 返回五个{@code int}值中的最大者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最大者
   */
  public static int max(final int a, final int b, final int c, final int d, final int e) {
    return max(max(a, b, c, d), e);
  }

  /**
   * 返回五个{@code long}值中的最大者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最大者
   */
  public static long max(final long a, final long b, final long c, final long d, final long e) {
    return max(max(a, b, c, d), e);
  }

  /**
   * 返回五个float值中的最大者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最大者
   */
  public static float max(final float a, final float b, final float c, final float d, final float e) {
    return max(max(a, b, c, d), e);
  }

  /**
   * 返回五个double值中的最大者。
   * <p>
   * 使用最优算法，总共进行4次比较。
   *
   * @param a
   *     第一个值
   * @param b
   *     第二个值
   * @param c
   *     第三个值
   * @param d
   *     第四个值
   * @param e
   *     第五个值
   * @return
   *     a、b、c、d和e中的最大者
   */
  public static double max(final double a, final double b, final double c, final double d, final double e) {
    return max(max(a, b, c, d), e);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      浮点数比较
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 判断给定的float值是否为零（在默认精度范围内）。
   *
   * @param x
   *     待检查的float值
   * @return
   *     如果给定值在默认精度范围内等于零则返回true，否则返回false
   */
  public static boolean isZero(final float x) {
    return Math.abs(x) <= floatEpsilon;
  }

  /**
   * 判断给定的float值是否为零（在指定精度范围内）。
   *
   * @param x
   *     待检查的float值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内等于零则返回true，否则返回false
   */
  public static boolean isZero(final float x, final float epsilon) {
    return Math.abs(x) <= epsilon;
  }

  /**
   * 判断给定的double值是否为零（在默认精度范围内）。
   *
   * @param x
   *     待检查的double值
   * @return
   *     如果给定值在默认精度范围内等于零则返回true，否则返回false
   */
  public static boolean isZero(final double x) {
    return Math.abs(x) <= doubleEpsilon;
  }

  /**
   * 判断给定的double值是否为零（在指定精度范围内）。
   *
   * @param x
   *     待检查的double值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内等于零则返回true，否则返回false
   */
  public static boolean isZero(final double x, final double epsilon) {
    return Math.abs(x) <= epsilon;
  }

  /**
   * 判断给定的float值是否为正数（在默认精度范围内）。
   *
   * @param x
   *     待检查的float值
   * @return
   *     如果给定值在默认精度范围内大于零则返回true，否则返回false
   */
  public static boolean isPositive(final float x) {
    return x > floatEpsilon;
  }

  /**
   * 判断给定的float值是否为正数（在指定精度范围内）。
   *
   * @param x
   *     待检查的float值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内大于零则返回true，否则返回false
   */
  public static boolean isPositive(final float x, final float epsilon) {
    return x > epsilon;
  }

  /**
   * 判断给定的double值是否为正数（在默认精度范围内）。
   *
   * @param x
   *     待检查的double值
   * @return
   *     如果给定值在默认精度范围内大于零则返回true，否则返回false
   */
  public static boolean isPositive(final double x) {
    return x > doubleEpsilon;
  }

  /**
   * 判断给定的double值是否为正数（在指定精度范围内）。
   *
   * @param x
   *     待检查的double值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内大于零则返回true，否则返回false
   */
  public static boolean isPositive(final double x, final double epsilon) {
    return x > epsilon;
  }

  /**
   * 判断给定的float值是否为负数（在默认精度范围内）。
   *
   * @param x
   *     待检查的float值
   * @return
   *     如果给定值在默认精度范围内小于零则返回true，否则返回false
   */
  public static boolean isNegative(final float x) {
    return x < -floatEpsilon;
  }

  /**
   * 判断给定的float值是否为负数（在指定精度范围内）。
   *
   * @param x
   *     待检查的float值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内小于零则返回true，否则返回false
   */
  public static boolean isNegative(final float x, final float epsilon) {
    return x < -epsilon;
  }

  /**
   * 判断给定的double值是否为负数（在默认精度范围内）。
   *
   * @param x
   *     待检查的double值
   * @return
   *     如果给定值在默认精度范围内小于零则返回true，否则返回false
   */
  public static boolean isNegative(final double x) {
    return x < -doubleEpsilon;
  }

  /**
   * 判断给定的double值是否为负数（在指定精度范围内）。
   *
   * @param x
   *     待检查的double值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内小于零则返回true，否则返回false
   */
  public static boolean isNegative(final double x, final double epsilon) {
    return x < -epsilon;
  }

  /**
   * 判断给定的float值是否为非正数（在默认精度范围内）。
   * <p>
   * 非正数是指小于等于零的值，即 x <= 0。考虑浮点精度误差，
   * 当值在默认精度范围内小于等于零时返回true。
   *
   * @param x
   *     待检查的float值
   * @return
   *     如果给定值在默认精度范围内小于等于零则返回true，否则返回false
   */
  public static boolean isNonPositive(final float x) {
    return x <= floatEpsilon;
  }

  /**
   * 判断给定的float值是否为非正数（在指定精度范围内）。
   * <p>
   * 非正数是指小于等于零的值，即 x <= 0。考虑浮点精度误差，
   * 当值在指定精度范围内小于等于零时返回true。
   *
   * @param x
   *     待检查的float值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内小于等于零则返回true，否则返回false
   */
  public static boolean isNonPositive(final float x, final float epsilon) {
    return x <= epsilon;
  }

  /**
   * 判断给定的double值是否为非正数（在默认精度范围内）。
   * <p>
   * 非正数是指小于等于零的值，即 x <= 0。考虑浮点精度误差，
   * 当值在默认精度范围内小于等于零时返回true。
   *
   * @param x
   *     待检查的double值
   * @return
   *     如果给定值在默认精度范围内小于等于零则返回true，否则返回false
   */
  public static boolean isNonPositive(final double x) {
    return x <= doubleEpsilon;
  }

  /**
   * 判断给定的double值是否为非正数（在指定精度范围内）。
   * <p>
   * 非正数是指小于等于零的值，即 x <= 0。考虑浮点精度误差，
   * 当值在指定精度范围内小于等于零时返回true。
   *
   * @param x
   *     待检查的double值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内小于等于零则返回true，否则返回false
   */
  public static boolean isNonPositive(final double x, final double epsilon) {
    return x <= epsilon;
  }

  /**
   * 判断给定的float值是否为非负数（在默认精度范围内）。
   * <p>
   * 非负数是指大于等于零的值，即 x >= 0。考虑浮点精度误差，
   * 当值在默认精度范围内大于等于零时返回true。
   *
   * @param x
   *     待检查的float值
   * @return
   *     如果给定值在默认精度范围内大于等于零则返回true，否则返回false
   */
  public static boolean isNonNegative(final float x) {
    return x >= -floatEpsilon;
  }

  /**
   * 判断给定的float值是否为非负数（在指定精度范围内）。
   * <p>
   * 非负数是指大于等于零的值，即 x >= 0。考虑浮点精度误差，
   * 当值在指定精度范围内大于等于零时返回true。
   *
   * @param x
   *     待检查的float值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内大于等于零则返回true，否则返回false
   */
  public static boolean isNonNegative(final float x, final float epsilon) {
    return x >= -epsilon;
  }

  /**
   * 判断给定的double值是否为非负数（在默认精度范围内）。
   * <p>
   * 非负数是指大于等于零的值，即 x >= 0。考虑浮点精度误差，
   * 当值在默认精度范围内大于等于零时返回true。
   *
   * @param x
   *     待检查的double值
   * @return
   *     如果给定值在默认精度范围内大于等于零则返回true，否则返回false
   */
  public static boolean isNonNegative(final double x) {
    return x >= -doubleEpsilon;
  }

  /**
   * 判断给定的double值是否为非负数（在指定精度范围内）。
   * <p>
   * 非负数是指大于等于零的值，即 x >= 0。考虑浮点精度误差，
   * 当值在指定精度范围内大于等于零时返回true。
   *
   * @param x
   *     待检查的double值
   * @param epsilon
   *     精度范围
   * @return
   *     如果给定值在指定精度范围内大于等于零则返回true，否则返回false
   */
  public static boolean isNonNegative(final double x, final double epsilon) {
    return x >= -epsilon;
  }

  /**
   * 判断给定的float值是否在指定范围内（考虑默认精度）。
   *
   * @param value
   *     待检查的值
   * @param min
   *     范围下界
   * @param max
   *     范围上界
   * @return
   *     如果值在[min, max]范围内（考虑精度容差）则返回true，否则返回false
   */
  public static boolean between(final float value, final float min, final float max) {
    return between(value, min, max, floatEpsilon);
  }

  /**
   * 判断给定的float值是否在指定范围内（考虑指定精度）。
   *
   * @param value
   *     待检查的值
   * @param min
   *     范围下界
   * @param max
   *     范围上界
   * @param epsilon
   *     精度容差
   * @return
   *     如果值在[min, max]范围内（考虑精度容差）则返回true，否则返回false
   */
  public static boolean between(final float value, final float min, final float max, final float epsilon) {
    return value >= min - epsilon && value <= max + epsilon;
  }

  /**
   * 判断给定的double值是否在指定范围内（考虑默认精度）。
   *
   * @param value
   *     待检查的值
   * @param min
   *     范围下界
   * @param max
   *     范围上界
   * @return
   *     如果值在[min, max]范围内（考虑精度容差）则返回true，否则返回false
   */
  public static boolean between(final double value, final double min, final double max) {
    return between(value, min, max, doubleEpsilon);
  }

  /**
   * 判断给定的double值是否在指定范围内（考虑指定精度）。
   *
   * @param value
   *     待检查的值
   * @param min
   *     范围下界
   * @param max
   *     范围上界
   * @param epsilon
   *     精度容差
   * @return
   *     如果值在[min, max]范围内（考虑精度容差）则返回true，否则返回false
   */
  public static boolean between(final double value, final double min, final double max, final double epsilon) {
    return (value >= min - epsilon) && (value <= max + epsilon);
  }

  /**
   * 判断两个float值是否在默认精度范围内相等。
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>相同的无穷大值被认为是相等的</li>
   *   <li>NaN与任何值（包括NaN自身）都不相等</li>
   *   <li>+0.0和-0.0被认为是相等的</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果两个值在默认精度范围内相等则返回true，否则返回false
   */
  public static boolean equal(final float x, final float y) {
    // 处理NaN：NaN与任何值都不相等
    if (Float.isNaN(x) || Float.isNaN(y)) {
      return false;
    }
    // 处理无穷大：相同的无穷大值相等
    if (Float.isInfinite(x) && Float.isInfinite(y)) {
      return x == y;
    }
    // 处理一般情况
    return Math.abs(x - y) <= floatEpsilon;
  }

  /**
   * 判断两个float值是否在指定精度范围内相等。
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>相同的无穷大值被认为是相等的</li>
   *   <li>NaN与任何值（包括NaN自身）都不相等</li>
   *   <li>+0.0和-0.0被认为是相等的</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果两个值在指定精度范围内相等则返回true，否则返回false
   */
  public static boolean equal(final float x, final float y, final float epsilon) {
    // 处理NaN：NaN与任何值都不相等
    if (Float.isNaN(x) || Float.isNaN(y)) {
      return false;
    }
    // 处理无穷大：相同的无穷大值相等
    if (Float.isInfinite(x) && Float.isInfinite(y)) {
      return x == y;
    }
    // 处理一般情况
    return Math.abs(x - y) <= epsilon;
  }

  /**
   * 判断两个double值是否在默认精度范围内相等。
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>相同的无穷大值被认为是相等的</li>
   *   <li>NaN与任何值（包括NaN自身）都不相等</li>
   *   <li>+0.0和-0.0被认为是相等的</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果两个值在默认精度范围内相等则返回true，否则返回false
   */
  public static boolean equal(final double x, final double y) {
    // 处理NaN：NaN与任何值都不相等
    if (Double.isNaN(x) || Double.isNaN(y)) {
      return false;
    }
    // 处理无穷大：相同的无穷大值相等
    if (Double.isInfinite(x) && Double.isInfinite(y)) {
      return x == y;
    }
    // 处理一般情况
    return Math.abs(x - y) <= doubleEpsilon;
  }

  /**
   * 判断两个double值是否在指定精度范围内相等。
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>相同的无穷大值被认为是相等的</li>
   *   <li>NaN与任何值（包括NaN自身）都不相等</li>
   *   <li>+0.0和-0.0被认为是相等的</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果两个值在指定精度范围内相等则返回true，否则返回false
   */
  public static boolean equal(final double x, final double y, final double epsilon) {
    // 处理NaN：NaN与任何值都不相等
    if (Double.isNaN(x) || Double.isNaN(y)) {
      return false;
    }
    // 处理无穷大：相同的无穷大值相等
    if (Double.isInfinite(x) && Double.isInfinite(y)) {
      return x == y;
    }
    // 处理一般情况
    return Math.abs(x - y) <= epsilon;
  }

  /**
   * 判断第一个float值是否明显小于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x + epsilon < y
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x明显小于y则返回true，否则返回false
   */
  public static boolean less(final float x, final float y) {
    return less(x, y, floatEpsilon);
  }

  /**
   * 判断第一个float值是否明显小于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x + epsilon < y
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x明显小于y则返回true，否则返回false
   */
  public static boolean less(final float x, final float y, final float epsilon) {
    return x + epsilon < y;
  }

  /**
   * 判断第一个double值是否明显小于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x + epsilon < y
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x明显小于y则返回true，否则返回false
   */
  public static boolean less(final double x, final double y) {
    return less(x, y, doubleEpsilon);
  }

  /**
   * 判断第一个double值是否明显小于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x + epsilon < y
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x明显小于y则返回true，否则返回false
   */
  public static boolean less(final double x, final double y, final double epsilon) {
    return x + epsilon < y;
  }

  /**
   * 判断第一个float值是否明显大于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x > y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x明显大于y则返回true，否则返回false
   */
  public static boolean greater(final float x, final float y) {
    return greater(x, y, floatEpsilon);
  }

  /**
   * 判断第一个float值是否明显大于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x > y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x明显大于y则返回true，否则返回false
   */
  public static boolean greater(final float x, final float y, final float epsilon) {
    return x > y + epsilon;
  }

  /**
   * 判断第一个double值是否明显大于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x > y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x明显大于y则返回true，否则返回false
   */
  public static boolean greater(final double x, final double y) {
    return greater(x, y, doubleEpsilon);
  }

  /**
   * 判断第一个double值是否明显大于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x > y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x明显大于y则返回true，否则返回false
   */
  public static boolean greater(final double x, final double y, final double epsilon) {
    return x > y + epsilon;
  }

  /**
   * 判断第一个float值是否小于等于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x <= y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x小于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean lessEqual(final float x, final float y) {
    return lessEqual(x, y, floatEpsilon);
  }

  /**
   * 判断第一个float值是否小于等于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x <= y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x小于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean lessEqual(final float x, final float y, final float epsilon) {
    return x <= y + epsilon;
  }

  /**
   * 判断第一个double值是否小于等于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x <= y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x小于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean lessEqual(final double x, final double y) {
    return lessEqual(x, y, doubleEpsilon);
  }

  /**
   * 判断第一个double值是否小于等于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x <= y + epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x小于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean lessEqual(final double x, final double y, final double epsilon) {
    return x <= y + epsilon;
  }

  /**
   * 判断第一个float值是否大于等于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x >= y - epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x大于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean greaterEqual(final float x, final float y) {
    return greaterEqual(x, y, floatEpsilon);
  }

  /**
   * 判断第一个float值是否大于等于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x >= y - epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x大于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean greaterEqual(final float x, final float y, final float epsilon) {
    return x >= y - epsilon;
  }

  /**
   * 判断第一个double值是否大于等于第二个值（考虑默认精度）。
   * <p>
   * 相当于：x >= y - epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     如果x大于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean greaterEqual(final double x, final double y) {
    return greaterEqual(x, y, doubleEpsilon);
  }

  /**
   * 判断第一个double值是否大于等于第二个值（考虑指定精度）。
   * <p>
   * 相当于：x >= y - epsilon
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     如果x大于等于y（考虑精度）则返回true，否则返回false
   */
  public static boolean greaterEqual(final double x, final double y, final double epsilon) {
    return x >= y - epsilon;
  }

  /**
   * 比较两个float值，使用默认精度。
   * <p>
   * 返回值的含义：
   * <ul>
   *   <li>负数：x小于y</li>
   *   <li>0：x等于y（在精度范围内）</li>
   *   <li>正数：x大于y</li>
   * </ul>
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>NaN被认为等于NaN，且大于任何非NaN值</li>
   *   <li>正无穷大于任何有限值和负无穷</li>
   *   <li>负无穷小于任何有限值和正无穷</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     比较结果：负数表示x<y，0表示x=y，正数表示x>y
   */
  public static int compare(final float x, final float y) {
    return compare(x, y, floatEpsilon);
  }

  /**
   * 比较两个float值，使用指定精度。
   * <p>
   * 返回值的含义：
   * <ul>
   *   <li>负数：x小于y</li>
   *   <li>0：x等于y（在精度范围内）</li>
   *   <li>正数：x大于y</li>
   * </ul>
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>NaN被认为等于NaN，且大于任何非NaN值</li>
   *   <li>正无穷大于任何有限值和负无穷</li>
   *   <li>负无穷小于任何有限值和正无穷</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     比较结果：负数表示x<y，0表示x=y，正数表示x>y
   */
  public static int compare(final float x, final float y, final float epsilon) {
    // 处理NaN情况
    if (Float.isNaN(x) && Float.isNaN(y)) {
      return 0;
    }
    if (Float.isNaN(x)) {
      return 1;  // NaN > 任何非NaN值
    }
    if (Float.isNaN(y)) {
      return -1; // 任何非NaN值 < NaN
    }

    // 处理无穷大情况
    if (Float.isInfinite(x) && Float.isInfinite(y)) {
      return Float.compare(x, y);
    }
    if (Float.isInfinite(x)) {
      return x > 0 ? 1 : -1;
    }
    if (Float.isInfinite(y)) {
      return y > 0 ? -1 : 1;
    }

    // 使用精度比较
    if (equal(x, y, epsilon)) {
      return 0;
    }
    return x < y ? -1 : 1;
  }

  /**
   * 比较两个double值，使用默认精度。
   * <p>
   * 返回值的含义：
   * <ul>
   *   <li>负数：x小于y</li>
   *   <li>0：x等于y（在精度范围内）</li>
   *   <li>正数：x大于y</li>
   * </ul>
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>NaN被认为等于NaN，且大于任何非NaN值</li>
   *   <li>正无穷大于任何有限值和负无穷</li>
   *   <li>负无穷小于任何有限值和正无穷</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @return
   *     比较结果：负数表示x<y，0表示x=y，正数表示x>y
   */
  public static int compare(final double x, final double y) {
    return compare(x, y, doubleEpsilon);
  }

  /**
   * 比较两个double值，使用指定精度。
   * <p>
   * 返回值的含义：
   * <ul>
   *   <li>负数：x小于y</li>
   *   <li>0：x等于y（在精度范围内）</li>
   *   <li>正数：x大于y</li>
   * </ul>
   * <p>
   * 对特殊值的处理：
   * <ul>
   *   <li>NaN被认为等于NaN，且大于任何非NaN值</li>
   *   <li>正无穷大于任何有限值和负无穷</li>
   *   <li>负无穷小于任何有限值和正无穷</li>
   * </ul>
   *
   * @param x
   *     第一个值
   * @param y
   *     第二个值
   * @param epsilon
   *     精度容差
   * @return
   *     比较结果：负数表示x<y，0表示x=y，正数表示x>y
   */
  public static int compare(final double x, final double y, final double epsilon) {
    // 处理NaN情况
    if (Double.isNaN(x) && Double.isNaN(y)) {
      return 0;
    }
    if (Double.isNaN(x)) {
      return 1;  // NaN > 任何非NaN值
    }
    if (Double.isNaN(y)) {
      return -1; // 任何非NaN值 < NaN
    }
    // 处理无穷大情况
    if (Double.isInfinite(x) && Double.isInfinite(y)) {
      return Double.compare(x, y);
    }
    if (Double.isInfinite(x)) {
      return x > 0 ? 1 : -1;
    }
    if (Double.isInfinite(y)) {
      return y > 0 ? -1 : 1;
    }
    // 使用精度比较
    if (equal(x, y, epsilon)) {
      return 0;
    }
    return x < y ? -1 : 1;
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      数值限制
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 将给定的值限制在指定的范围内。
   *
   * @param value
   *     给定的值。
   * @param lower
   *     指定的范围的(包含)下确界。
   * @param upper
   *     指定的范围的(包含)上确界。
   * @return
   *     若给定的值位于{@code [low, upper]}之间，则返回给定的值；若给定的值小于下确界
   *     {@code lower}，则返回{@code lower}；若给定的值大于上确界{@code upper}，则
   *     返回{@code upper}。
   */
  public static byte clamp(final byte value, final byte lower, final byte upper) {
    return (byte) Math.min(upper, Math.max(lower, value));
  }

  /**
   * 将给定的值限制在指定的范围内。
   *
   * @param value
   *     给定的值。
   * @param lower
   *     指定的范围的(包含)下确界。
   * @param upper
   *     指定的范围的(包含)上确界。
   * @return
   *     若给定的值位于{@code [low, upper]}之间，则返回给定的值；若给定的值小于下确界
   *     {@code lower}，则返回{@code lower}；若给定的值大于上确界{@code upper}，则
   *     返回{@code upper}。
   */
  public static short clamp(final short value, final short lower, final short upper) {
    return (short) Math.min(upper, Math.max(lower, value));
  }

  /**
   * 将给定的值限制在指定的范围内。
   *
   * @param value
   *     给定的值。
   * @param lower
   *     指定的范围的(包含)下确界。
   * @param upper
   *     指定的范围的(包含)上确界。
   * @return
   *     若给定的值位于{@code [low, upper]}之间，则返回给定的值；若给定的值小于下确界
   *     {@code lower}，则返回{@code lower}；若给定的值大于上确界{@code upper}，则
   *     返回{@code upper}。
   */
  public static int clamp(final int value, final int lower, final int upper) {
    return Math.min(upper, Math.max(lower, value));
  }

  /**
   * 将给定的值限制在指定的范围内。
   *
   * @param value
   *     给定的值。
   * @param lower
   *     指定的范围的(包含)下确界。
   * @param upper
   *     指定的范围的(包含)上确界。
   * @return
   *     若给定的值位于{@code [low, upper]}之间，则返回给定的值；若给定的值小于下确界
   *     {@code lower}，则返回{@code lower}；若给定的值大于上确界{@code upper}，则
   *     返回{@code upper}。
   */
  public static long clamp(final long value, final long lower, final long upper) {
    return Math.min(upper, Math.max(lower, value));
  }

  /**
   * 将给定的值限制在指定的范围内。
   *
   * @param value
   *     给定的值。
   * @param lower
   *     指定的范围的(包含)下确界。
   * @param upper
   *     指定的范围的(包含)上确界。
   * @return
   *     若给定的值位于{@code [low, upper]}之间，则返回给定的值；若给定的值小于下确界
   *     {@code lower}，则返回{@code lower}；若给定的值大于上确界{@code upper}，则
   *     返回{@code upper}。
   */
  public static float clamp(final float value, final float lower, final float upper) {
    return Math.min(upper, Math.max(lower, value));
  }

  /**
   * 将给定的值限制在指定的范围内。
   *
   * @param value
   *     给定的值。
   * @param lower
   *     指定的范围的(包含)下确界。
   * @param upper
   *     指定的范围的(包含)上确界。
   * @return
   *     若给定的值位于{@code [low, upper]}之间，则返回给定的值；若给定的值小于下确界
   *     {@code lower}，则返回{@code lower}；若给定的值大于上确界{@code upper}，则
   *     返回{@code upper}。
   */
  public static double clamp(final double value, final double lower, final double upper) {
    return Math.min(upper, Math.max(lower, value));
  }

  /**
   * 将给定的值限制在正数范围内。
   *
   * @param value
   *     给定的值。
   * @return
   *     若给定的值大于等于0，则返回给定的值；否则返回0.
   */
  public static float clampPositive(final float value) {
    return Math.max(0, value);
  }

  /**
   * 将给定的值限制在正数范围内。
   *
   * @param value
   *     给定的值。
   * @return
   *     若给定的值大于等于0，则返回给定的值；否则返回0.
   */
  public static double clampPositive(final double value) {
    return Math.max(0, value);
  }

  /**
   * 将给定的值限制在负数范围内。
   *
   * @param value
   *     给定的值。
   * @return
   *     若给定的值小于等于0，则返回给定的值；否则返回0.
   */
  public static float clampNegative(final float value) {
    return Math.min(0, value);
  }

  /**
   * 将给定的值限制在负数范围内。
   *
   * @param value
   *     给定的值。
   * @return
   *     若给定的值小于等于0，则返回给定的值；否则返回0.
   */
  public static double clampNegative(final double value) {
    return Math.min(0, value);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      精确运算
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 执行两个{@code int}值的精确加法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     第一个加数
   * @param y
   *     第二个加数
   * @return
   *     x + y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出int范围
   */
  public static int addExact(final int x, final int y) {
    return Math.addExact(x, y);
  }

  /**
   * 执行两个{@code long}值的精确加法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     第一个加数
   * @param y
   *     第二个加数
   * @return
   *     x + y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long addExact(final long x, final long y) {
    return Math.addExact(x, y);
  }

  /**
   * 执行两个{@code int}值的精确减法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     被减数
   * @param y
   *     减数
   * @return
   *     x - y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出int范围
   */
  public static int subtractExact(final int x, final int y) {
    return Math.subtractExact(x, y);
  }

  /**
   * 执行两个{@code long}值的精确减法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     被减数
   * @param y
   *     减数
   * @return
   *     x - y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long subtractExact(final long x, final long y) {
    return Math.subtractExact(x, y);
  }

  /**
   * 执行两个{@code int}值的精确乘法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     第一个乘数
   * @param y
   *     第二个乘数
   * @return
   *     x * y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出int范围
   */
  public static int multiplyExact(final int x, final int y) {
    return Math.multiplyExact(x, y);
  }

  /**
   * 执行long和{@code int}值的精确乘法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     long类型的乘数
   * @param y
   *     int类型的乘数
   * @return
   *     x * y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long multiplyExact(final long x, final int y) {
    return Math.multiplyExact(x, (long) y);
  }

  /**
   * 执行两个{@code long}值的精确乘法运算，如果结果溢出则抛出异常。
   *
   * @param x
   *     第一个乘数
   * @param y
   *     第二个乘数
   * @return
   *     x * y的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long multiplyExact(final long x, final long y) {
    return Math.multiplyExact(x, y);
  }

  /**
   * 对{@code int}值执行精确递增运算，如果结果溢出则抛出异常。
   *
   * @param a
   *     要递增的值
   * @return
   *     a + 1的精确结果
   * @throws ArithmeticException
   *     如果结果溢出int范围
   */
  public static int incrementExact(final int a) {
    return Math.incrementExact(a);
  }

  /**
   * 对{@code long}值执行精确递增运算，如果结果溢出则抛出异常。
   *
   * @param a
   *     要递增的值
   * @return
   *     a + 1的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long incrementExact(final long a) {
    return Math.incrementExact(a);
  }

  /**
   * 对{@code int}值执行精确递减运算，如果结果溢出则抛出异常。
   *
   * @param a
   *     要递减的值
   * @return
   *     a - 1的精确结果
   * @throws ArithmeticException
   *     如果结果溢出int范围
   */
  public static int decrementExact(final int a) {
    return Math.decrementExact(a);
  }

  /**
   * 对{@code long}值执行精确递减运算，如果结果溢出则抛出异常。
   *
   * @param a
   *     要递减的值
   * @return
   *     a - 1的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long decrementExact(final long a) {
    return Math.decrementExact(a);
  }

  /**
   * 对{@code int}值执行精确取负运算，如果结果溢出则抛出异常。
   *
   * @param a
   *     要取负的值
   * @return
   *     -a的精确结果
   * @throws ArithmeticException
   *     如果结果溢出int范围
   */
  public static int negateExact(final int a) {
    return Math.negateExact(a);
  }

  /**
   * 对{@code long}值执行精确取负运算，如果结果溢出则抛出异常。
   *
   * @param a
   *     要取负的值
   * @return
   *     -a的精确结果
   * @throws ArithmeticException
   *     如果结果溢出long范围
   */
  public static long negateExact(final long a) {
    return Math.negateExact(a);
  }

  /**
   * 将{@code long}值精确转换为{@code int}值，如果发生溢出则抛出异常。
   *
   * @param value
   *     要转换的{@code long}值
   * @return
   *     转换后的{@code int}值
   * @throws ArithmeticException
   *     如果{@code long}值超出int范围
   */
  public static int toIntExact(final long value) {
    return Math.toIntExact(value);
  }

  /**
   * 计算两个{@code int}值的完整乘积（返回long以避免溢出）。
   *
   * @param x
   *     第一个乘数
   * @param y
   *     第二个乘数
   * @return
   *     x * y的完整乘积（long类型）
   */
  public static long multiplyFull(final int x, final int y) {
    return Math.multiplyFull(x, y);
  }

  /**
   * 计算两个{@code long}值乘积的高位部分。
   * <p>
   * 此方法计算128位乘积的高64位，适用于高精度数学运算。
   *
   * @param x
   *     第一个乘数
   * @param y
   *     第二个乘数
   * @return
   *     乘积的高64位
   */
  public static long multiplyHigh(final long x, final long y) {
    return Math.multiplyHigh(x, y);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      浮点数特殊操作
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 返回指定的float值的符号。
   *
   * @param value
   *     指定的值。
   * @return
   *     如果指定的值为正返回1，为负返回-1，为零返回0，为NaN返回null。注意，+0.0和-0.0都返回0。
   * @see #signum(float)
   */
  @Nullable
  public static Integer sign(final float value) {
    if (Float.isNaN(value)) {
      return null;
    }
    if (FloatBit.isZero(value)) {
      return 0;
    }
    return value > 0 ? 1 : -1;
  }

  /**
   * 返回指定的double值的符号。
   *
   * @param value
   *     指定的值。
   * @return
   *     如果指定的值为正返回1，为负返回-1，为零返回0，为NaN返回null。注意，+0.0和-0.0都返回0。
   * @see #signum(double)
   */
  @Nullable
  public static Integer sign(final double value) {
    if (Double.isNaN(value)) {
      return null;
    }
    if (DoubleBit.isZero(value)) {
      return 0;
    }
    return value > 0 ? 1 : -1;
  }

  /**
   * 返回float值的符号函数。
   *
   * @param f
   *     值
   * @return
   *     如果值为正返回1.0f，为负返回-1.0f，为零返回0.0f，为NaN返回NaN
   */
  public static float signum(final float f) {
    return Math.signum(f);
  }

  /**
   * 返回double值的符号函数。
   *
   * @param d
   *     值
   * @return
   *     如果值为正返回1.0，为负返回-1.0，为零返回0.0，为NaN返回NaN
   */
  public static double signum(final double d) {
    return Math.signum(d);
  }

  /**
   * 返回具有指定符号的值（float版本）。
   *
   * @param magnitude
   *     提供量值的值
   * @param sign
   *     提供符号的值
   * @return
   *     具有sign符号的magnitude值
   */
  public static float copySign(final float magnitude, final float sign) {
    return Math.copySign(magnitude, sign);
  }

  /**
   * 返回具有指定符号的值。
   *
   * @param magnitude
   *     提供量值的值
   * @param sign
   *     提供符号的值
   * @return
   *     具有sign符号的magnitude值
   */
  public static double copySign(final double magnitude, final double sign) {
    return Math.copySign(magnitude, sign);
  }

  /**
   * 返回float值在IEEE 754二进制表示中的指数部分。
   *
   * @param f
   *     float值
   * @return
   *     指数部分
   */
  public static int getExponent(final float f) {
    return Math.getExponent(f);
  }

  /**
   * 返回double值在IEEE 754二进制表示中的指数部分。
   *
   * @param d
   *     double值
   * @return
   *     指数部分
   */
  public static int getExponent(final double d) {
    return Math.getExponent(d);
  }

  /**
   * 返回float值的ULP（最小精度单位）。
   *
   * @param f
   *     值
   * @return
   *     该值的ULP
   */
  public static float ulp(final float f) {
    return Math.ulp(f);
  }

  /**
   * 返回double值的ULP（最小精度单位）。
   *
   * @param d
   *     值
   * @return
   *     该值的ULP
   */
  public static double ulp(final double d) {
    return Math.ulp(d);
  }

  /**
   * 返回从start向direction方向的相邻浮点值（float版本）。
   *
   * @param start
   *     起始值
   * @param direction
   *     方向值
   * @return
   *     相邻的浮点值
   */
  public static float nextAfter(final float start, final double direction) {
    return Math.nextAfter(start, direction);
  }

  /**
   * 返回从start向direction方向的相邻浮点值。
   *
   * @param start
   *     起始值
   * @param direction
   *     方向值
   * @return
   *     相邻的浮点值
   */
  public static double nextAfter(final double start, final double direction) {
    return Math.nextAfter(start, direction);
  }

  /**
   * 返回紧邻指定值且更大的浮点值（float版本）。
   *
   * @param f
   *     起始值
   * @return
   *     下一个更大的浮点值
   */
  public static float nextUp(final float f) {
    return Math.nextUp(f);
  }

  /**
   * 返回紧邻指定值且更大的浮点值。
   *
   * @param d
   *     起始值
   * @return
   *     下一个更大的浮点值
   */
  public static double nextUp(final double d) {
    return Math.nextUp(d);
  }

  /**
   * 返回紧邻指定值且更小的浮点值（float版本）。
   *
   * @param f
   *     起始值
   * @return
   *     下一个更小的浮点值
   */
  public static float nextDown(final float f) {
    return Math.nextDown(f);
  }

  /**
   * 返回紧邻指定值且更小的浮点值。
   *
   * @param d
   *     起始值
   * @return
   *     下一个更小的浮点值
   */
  public static double nextDown(final double d) {
    return Math.nextDown(d);
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      高精度计算
  //
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * 执行融合乘加运算 (a * b + c)，提供更高的精度（float版本）。
   *
   * @param a
   *     第一个乘数
   * @param b
   *     第二个乘数
   * @param c
   *     加数
   * @return
   *     (a * b + c) 的高精度结果
   */
  public static float fma(final float a, final float b, final float c) {
    return Math.fma(a, b, c);
  }

  /**
   * 执行融合乘加运算 (a * b + c)，提供更高的精度。
   * <p>
   * 此方法使用BigDecimal计算以避免中间结果的舍入误差。
   *
   * @param a
   *     第一个乘数
   * @param b
   *     第二个乘数
   * @param c
   *     加数
   * @return
   *     (a * b + c) 的高精度结果
   */
  public static double fma(final double a, final double b, final double c) {
    return Math.fma(a, b, c);
  }

  /**
   * 执行融合乘加运算 (a * b + c)，提供更高的精度（float版本）。
   * <p>
   * 此函数重载了{@link #fma(float, float, float)}方法，提供更具可读性的函数名。
   *
   * @param a
   *     第一个乘数
   * @param b
   *     第二个乘数
   * @param c
   *     加数
   * @return
   *     (a * b + c) 的高精度结果
   * @see #fma(float, float, float)
   */
  public static float multiplyAdd(final float a, final float b, final float c) {
    return Math.fma(a, b, c);
  }

  /**
   * 执行融合乘加运算 (a * b + c)，提供更高的精度。
   * <p>
   * 此函数重载了{@link #fma(double, double, double)}方法，提供更具可读性的函数名。
   *
   * @param a
   *     第一个乘数
   * @param b
   *     第二个乘数
   * @param c
   *     加数
   * @return
   *     (a * b + c) 的高精度结果
   * @see #fma(double, double, double)
   */
  public static double multiplyAdd(final double a, final double b, final double c) {
    return Math.fma(a, b, c);
  }

  /**
   * 计算 f × 2^scaleFactor，使用单个正确舍入的浮点乘法（float版本）。
   *
   * @param f
   *     要缩放的值
   * @param scaleFactor
   *     2的幂次指数
   * @return
   *     f × 2^scaleFactor
   */
  public static float scalb(final float f, final int scaleFactor) {
    return Math.scalb(f, scaleFactor);
  }

  /**
   * 计算 d × 2^scaleFactor，使用单个正确舍入的浮点乘法。
   *
   * @param d
   *     要缩放的值
   * @param scaleFactor
   *     2的幂次指数
   * @return
   *     d × 2^scaleFactor
   */
  public static double scalb(final double d, final int scaleFactor) {
    return Math.scalb(d, scaleFactor);
  }

  /**
   * 计算欧几里得距离 sqrt(x² + y²)，避免中间溢出。
   *
   * @param x
   *     x坐标
   * @param y
   *     y坐标
   * @return
   *     sqrt(x² + y²)
   */
  public static double hypot(final double x, final double y) {
    return Math.hypot(x, y);
  }

  /**
   * 计算两个float值的平方和（x² + y²），避免中间溢出。
   *
   * @param x
   *     第一个值。
   * @param y
   *     第二个值。
   * @return
   *     x² + y² 的平方和。
   */
  public static float squaredSum(final float x, final float y) {
    return (float) Math.fma(x, x, Math.pow(y, 2));
  }

  /**
   * 计算两个double值的平方和（x² + y²），避免中间溢出。
   *
   * @param x
   *     第一个值。
   * @param y
   *     第二个值。
   * @return
   *     x² + y² 的平方和。
   */
  public static double squaredSum(final double x, final double y) {
    return Math.fma(x, x, Math.pow(y, 2));
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  //                      随机数生成
  //
  // ///////////////////////////////////////////////////////////////////////////

  private static final class RandomHolder {
    static final RandomEx instance = new RandomEx();
  }

  /**
   * 生成[0.0, 1.0)范围内的随机double值。
   *
   * @return
   *     随机double值
   */
  public static double random() {
    return RandomHolder.instance.nextDouble();
  }

  /**
   * 生成指定范围内的随机double值。
   *
   * @param lowerBound
   *     下界（包含）
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机double值
   */
  public static double random(final double lowerBound, final double upperBound) {
    return RandomHolder.instance.nextDouble(lowerBound, upperBound);
  }

  /**
   * 生成随机byte值。
   *
   * @return
   *     随机byte值
   */
  public static byte randomByte() {
    return RandomHolder.instance.nextByte();
  }

  /**
   * 生成[0, upperBound)范围内的随机byte值。
   *
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机byte值
   */
  public static byte randomByte(final byte upperBound) {
    return RandomHolder.instance.nextByte(upperBound);
  }

  /**
   * 生成指定范围内的随机byte值。
   *
   * @param lowerBound
   *     下界（包含）
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机byte值
   */
  public static byte randomByte(final byte lowerBound, final byte upperBound) {
    return RandomHolder.instance.nextByte(lowerBound, upperBound);
  }

  /**
   * 生成随机short值。
   *
   * @return
   *     随机short值
   */
  public static short randomShort() {
    return RandomHolder.instance.nextShort();
  }

  /**
   * 生成[0, upperBound)范围内的随机short值。
   *
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机short值
   */
  public static short randomShort(final short upperBound) {
    return RandomHolder.instance.nextShort(upperBound);
  }

  /**
   * 生成指定范围内的随机short值。
   *
   * @param lowerBound
   *     下界（包含）
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机short值
   */
  public static short randomShort(final short lowerBound, final short upperBound) {
    return RandomHolder.instance.nextShort(lowerBound, upperBound);
  }

  /**
   * 生成随机{@code int}值。
   *
   * @return
   *     随机{@code int}值
   */
  public static int randomInt() {
    return RandomHolder.instance.nextInt();
  }

  /**
   * 生成[0, upperBound)范围内的随机{@code int}值。
   *
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机{@code int}值
   */
  public static int randomInt(final int upperBound) {
    return RandomHolder.instance.nextInt(upperBound);
  }

  /**
   * 生成指定范围内的随机{@code int}值。
   *
   * @param lowerBound
   *     下界（包含）
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机{@code int}值
   */
  public static int randomInt(final int lowerBound, final int upperBound) {
    return RandomHolder.instance.nextInt(lowerBound, upperBound);
  }

  /**
   * 生成随机{@code long}值。
   *
   * @return
   *     随机{@code long}值
   */
  public static long randomLong() {
    return RandomHolder.instance.nextLong();
  }

  /**
   * 生成[0, upperBound)范围内的随机{@code long}值。
   *
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机{@code long}值
   */
  public static long randomLong(final long upperBound) {
    return RandomHolder.instance.nextLong(upperBound);
  }

  /**
   * 生成指定范围内的随机{@code long}值。
   *
   * @param lowerBound
   *     下界（包含）
   * @param upperBound
   *     上界（不包含）
   * @return
   *     指定范围内的随机{@code long}值
   */
  public static long randomLong(final long lowerBound, final long upperBound) {
    return RandomHolder.instance.nextLong(lowerBound, upperBound);
  }
}