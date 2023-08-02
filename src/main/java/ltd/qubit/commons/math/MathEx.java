////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.math;

import java.math.BigDecimal;

import static ltd.qubit.commons.lang.Argument.requireInCloseRange;

/**
 * The class extends {@link java.lang.Math}.
 *
 * <p>NOTE: In order to simplify the usage, we copy all the functions implemented
 * in {@link java.lang.Math} to this class, and add some extra functions.</p>
 *
 * @author Haixing Hu
 */
public final class MathEx {

  // stop checkstyle: MethodName
  // stop checkstyle: AbbreviationAsWordInName
  // stop checkstyle: MagicNumber

  public static final double E = 2.718281828459045D;

  public static final double PI = 3.141592653589793D;

  public static final double LOG_OF_2 = Math.log(2);

  public static final float DEFAULT_FLOAT_EPSILON = 1e-3F;

  public static final double DEFAULT_DOUBLE_EPSILON = 1e-6D;

  private static final class RandomHolder {
    static final RandomEx instance = new RandomEx();
  }

  private MathEx() {}

  public static double sin(final double a) {
    return Math.sin(a);
  }

  public static double cos(final double a) {
    return Math.cos(a);
  }

  public static double tan(final double a) {
    return Math.tan(a);
  }

  public static double asin(final double a) {
    return StrictMath.asin(a);
  }

  public static double acos(final double a) {
    return Math.acos(a);
  }

  public static double atan(final double a) {
    return Math.atan(a);
  }

  public static double toRadians(final double angdeg) {
    return Math.toRadians(angdeg);
  }

  public static double toDegrees(final double angrad) {
    return Math.toDegrees(angrad);
  }

  public static double exp(final double a) {
    return Math.exp(a);
  }

  public static double log(final double a) {
    return Math.log(a);
  }

  public static double log10(final double a) {
    return Math.log10(a);
  }

  public static double log2(final double x) {
    return Math.log(x) / LOG_OF_2;
  }

  public static double sqrt(final double a) {
    return Math.sqrt(a);
  }

  public static double cbrt(final double a) {
    return Math.cbrt(a);
  }

  public static double IEEEremainder(final double f1, final double f2) {
    return Math.IEEEremainder(f1, f2);
  }

  public static double ceil(final double a) {
    return Math.ceil(a);
  }

  public static double floor(final double a) {
    return StrictMath.floor(a);
  }

  public static double rint(final double a) {
    return Math.rint(a);
  }

  public static double atan2(final double y, final double x) {
    return Math.atan2(y, x);
  }

  public static double pow(final double a, final double b) {
    return Math.pow(a, b);
  }

  public static int round(final float a) {
    return Math.round(a);
  }

  public static long round(final double a) {
    return Math.round(a);
  }

  public static double random() {
    return RandomHolder.instance.nextDouble();
  }

  public static double random(final double lowerBound, final double upperBound) {
    return RandomHolder.instance.nextDouble(lowerBound, upperBound);
  }

  public static byte randomByte() {
    return RandomHolder.instance.nextByte();
  }

  public static byte randomByte(final byte upperBound) {
    return RandomHolder.instance.nextByte(upperBound);
  }

  public static byte randomByte(final byte lowerBound, final byte upperBound) {
    return RandomHolder.instance.nextByte(lowerBound, upperBound);
  }

  public static short randomShort() {
    return RandomHolder.instance.nextShort();
  }

  public static short randomShort(final short upperBound) {
    return RandomHolder.instance.nextShort(upperBound);
  }

  public static short randomShort(final short lowerBound, final short upperBound) {
    return RandomHolder.instance.nextShort(lowerBound, upperBound);
  }

  public static int randomInt() {
    return RandomHolder.instance.nextInt();
  }

  public static int randomInt(final int upperBound) {
    return RandomHolder.instance.nextInt(upperBound);
  }

  public static int randomInt(final int lowerBound, final int upperBound) {
    return RandomHolder.instance.nextInt(lowerBound, upperBound);
  }

  public static long randomLong() {
    return RandomHolder.instance.nextLong();
  }

  public static long randomLong(final long upperBound) {
    return RandomHolder.instance.nextLong(upperBound);
  }

  public static long randomLong(final long lowerBound, final long upperBound) {
    return RandomHolder.instance.nextLong(lowerBound, upperBound);
  }

  public static int addExact(final int x, final int y) {
    return Math.addExact(x, y);
  }

  public static long addExact(final long x, final long y) {
    return Math.addExact(x, y);
  }

  public static int subtractExact(final int x, final int y) {
    return Math.subtractExact(x, y);
  }

  public static long subtractExact(final long x, final long y) {
    return Math.subtractExact(x, y);
  }

  public static int multiplyExact(final int x, final int y) {
    return Math.multiplyExact(x, y);
  }

  public static long multiplyExact(final long x, final int y) {
    return Math.multiplyExact(x, (long) y);
  }

  public static long multiplyExact(final long x, final long y) {
    return Math.multiplyExact(x, y);
  }

  public static int incrementExact(final int a) {
    return Math.incrementExact(a);
  }

  public static long incrementExact(final long a) {
    return Math.incrementExact(a);
  }

  public static int decrementExact(final int a) {
    return Math.decrementExact(a);
  }

  public static long decrementExact(final long a) {
    return Math.decrementExact(a);
  }

  public static int negateExact(final int a) {
    if (a == -2147483648) {
      throw new ArithmeticException("integer overflow");
    } else {
      return -a;
    }
  }

  public static long negateExact(final long a) {
    if (a == -9223372036854775808L) {
      throw new ArithmeticException("long overflow");
    } else {
      return -a;
    }
  }

  public static int toIntExact(final long value) {
    if ((long) ((int) value) != value) {
      throw new ArithmeticException("integer overflow");
    } else {
      return (int) value;
    }
  }

  public static long multiplyFull(final int x, final int y) {
    return (long) x * (long) y;
  }

  public static long multiplyHigh(final long x, final long y) {
    long z1;
    final long z0;
    final long x1;
    final long x2;
    final long y1;
    final long y2;
    final long z2;
    final long t;
    if (x >= 0L && y >= 0L) {
      x1 = x >>> 32;
      x2 = y >>> 32;
      y1 = x & 4294967295L;
      y2 = y & 4294967295L;
      z2 = x1 * x2;
      t = y1 * y2;
      z1 = (x1 + y1) * (x2 + y2);
      z0 = z1 - z2 - t;
      return ((t >>> 32) + z0 >>> 32) + z2;
    } else {
      x1 = x >> 32;
      x2 = x & 4294967295L;
      y1 = y >> 32;
      y2 = y & 4294967295L;
      z2 = x2 * y2;
      t = x1 * y2 + (z2 >>> 32);
      z1 = t & 4294967295L;
      z0 = t >> 32;
      z1 += x2 * y1;
      return x1 * y1 + z0 + (z1 >> 32);
    }
  }

  public static int floorDiv(final int x, final int y) {
    return Math.floorDiv(x, y);
  }

  public static long floorDiv(final long x, final int y) {
    return Math.floorDiv(x, (long) y);
  }

  public static long floorDiv(final long x, final long y) {
    return Math.floorDiv(x, y);
  }

  public static int floorMod(final int x, final int y) {
    return x - Math.floorDiv(x, y) * y;
  }

  public static int floorMod(final long x, final int y) {
    return (int) (x - Math.floorDiv(x, (long) y) * (long) y);
  }

  public static long floorMod(final long x, final long y) {
    return x - Math.floorDiv(x, y) * y;
  }

  public static int abs(final int a) {
    return a < 0 ? -a : a;
  }

  public static long abs(final long a) {
    return a < 0L ? -a : a;
  }

  public static float abs(final float a) {
    return a <= 0.0F ? 0.0F - a : a;
  }

  public static double abs(final double a) {
    return a <= 0.0D ? 0.0D - a : a;
  }

  public static int max(final int a, final int b) {
    return a >= b ? a : b;
  }

  public static long max(final long a, final long b) {
    return a >= b ? a : b;
  }

  public static float max(final float a, final float b) {
    return Math.max(a, b);
  }

  public static double max(final double a, final double b) {
    return Math.max(a, b);
  }

  public static int min(final int a, final int b) {
    return a <= b ? a : b;
  }

  public static long min(final long a, final long b) {
    return a <= b ? a : b;
  }

  public static float min(final float a, final float b) {
    return Math.min(a, b);
  }

  public static double min(final double a, final double b) {
    return Math.min(a, b);
  }

  public static double fma(final double a, final double b, final double c) {
    if (!Double.isNaN(a) && !Double.isNaN(b) && !Double.isNaN(c)) {
      final boolean infiniteA = Double.isInfinite(a);
      final boolean infiniteB = Double.isInfinite(b);
      final boolean infiniteC = Double.isInfinite(c);
      if (!infiniteA && !infiniteB && !infiniteC) {
        final BigDecimal product = (new BigDecimal(a)).multiply(new BigDecimal(b));
        if (c == 0.0D) {
          return a != 0.0D && b != 0.0D ? product.doubleValue() : a * b + c;
        } else {
          return product.add(new BigDecimal(c)).doubleValue();
        }
      } else if ((!infiniteA || b != 0.0D) && (!infiniteB || a != 0.0D)) {
        final double product = a * b;
        if (Double.isInfinite(product) && !infiniteA && !infiniteB) {
          assert Double.isInfinite(c);

          return c;
        } else {
          final double result = product + c;

          assert !Double.isFinite(result);

          return result;
        }
      } else {
        return 0.0D / 0.0;
      }
    } else {
      return 0.0D / 0.0;
    }
  }

  public static float fma(final float a, final float b, final float c) {
    if (Float.isFinite(a) && Float.isFinite(b) && Float.isFinite(c)) {
      if (((double) a != 0.0D) && ((double) b != 0.0D)) {
        return (BigDecimal.valueOf((double) a * (double) b))
            .add(new BigDecimal(c))
            .floatValue();
      } else {
        return a * b + c;
      }
    } else {
      return (float) fma(a, b, (double) c);
    }
  }

  public static double ulp(final double d) {
    return Math.ulp(d);
  }

  public static float ulp(final float f) {
    return Math.ulp(f);
  }

  public static double signum(final double d) {
    return Math.signum(d);
  }

  public static float signum(final float f) {
    return Math.signum(f);
  }

  public static double sinh(final double x) {
    return Math.sinh(x);
  }

  public static double cosh(final double x) {
    return Math.cosh(x);
  }

  public static double tanh(final double x) {
    return Math.tanh(x);
  }

  public static double hypot(final double x, final double y) {
    return Math.hypot(x, y);
  }

  public static double expm1(final double x) {
    return Math.expm1(x);
  }

  public static double log1p(final double x) {
    return Math.log1p(x);
  }

  public static double copySign(final double magnitude, final double sign) {
    return Math.copySign(magnitude, sign);
  }

  public static float copySign(final float magnitude, final float sign) {
    return Math.copySign(magnitude, sign);
  }

  public static int getExponent(final float f) {
    return Math.getExponent(f);
  }

  public static int getExponent(final double d) {
    return Math.getExponent(d);
  }

  public static double nextAfter(final double start, final double direction) {
    return Math.nextAfter(start, direction);
  }

  public static float nextAfter(final float start, final double direction) {
    return Math.nextAfter(start, direction);
  }

  public static double nextUp(final double d) {
    return Math.nextUp(d);
  }

  public static float nextUp(final float f) {
    return Math.nextUp(f);
  }

  public static double nextDown(final double d) {
    return Math.nextDown(d);
  }

  public static float nextDown(final float f) {
    return Math.nextDown(f);
  }

  public static double scalb(final double d, final int scaleFactor) {
    return Math.scalb(d, scaleFactor);
  }

  public static float scalb(final float f, final int scaleFactor) {
    return Math.scalb(f, scaleFactor);
  }

  public static boolean equal(final float x, final float y, final float epsilon) {
    return Math.abs(x - y) <= epsilon;
  }

  public static boolean equal(final double x, final double y, final double epsilon) {
    return Math.abs(x - y) <= epsilon;
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
}
