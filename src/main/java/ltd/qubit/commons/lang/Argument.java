////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Collection;

import javax.annotation.Nullable;

import ltd.qubit.commons.math.MathEx;
import ltd.qubit.commons.text.Joiner;
import ltd.qubit.commons.text.Unicode;

import static ltd.qubit.commons.lang.StringUtils.isBlank;

/**
 * Provides common arg checking functions.
 *
 * @author Haixing Hu
 */
public final class Argument {

  /**
   * Checks the current bounds.
   *
   * <p>Note that the checking is non-trivial, since we have to consider the
   * integer overflows.
   *
   * @param off
   *     the offset.
   * @param n
   *     the number of elements.
   * @param length
   *     the length of the sequence.
   * @throws IndexOutOfBoundsException
   *     If the current is out of bounds.
   */
  public static void checkBounds(final int off, final int n, final int length) {
    if ((off < 0) || (n < 0) || (off > (length - n))) {
      throw new IndexOutOfBoundsException("off: " + off
          + ", n: " + n
          + ", length: " + length);
    }
  }

  public static <T> T requireNonNull(final String name, final T arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    return arg;
  }

  public static String requireNonBlank(final String name, final String arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (isBlank(arg)) {
      throw new IllegalArgumentException("The '" + name + "' can not be blank.");
    }
    return arg;
  }

  public static boolean[] requireNonEmpty(final String name, final boolean[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static char[] requireNonEmpty(final String name, final char[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static byte[] requireNonEmpty(final String name, final byte[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static short[] requireNonEmpty(final String name, final short[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static int[] requireNonEmpty(final String name, final int[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static long[] requireNonEmpty(final String name, final long[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static float[] requireNonEmpty(final String name, final float[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static double[] requireNonEmpty(final String name, final double[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static <T> T[] requireNonEmpty(final String name, final T[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  public static String requireNonEmpty(final String name, final String arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.length() == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return arg;
  }

  public static <T> Collection<T> requireNonEmpty(final String name,
      final Collection<T> arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.isEmpty()) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return arg;
  }

  public static boolean[] requireLengthBe(final String name,
      final boolean[] args, final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static char[] requireLengthBe(final String name, final char[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static byte[] requireLengthBe(final String name, final byte[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '" + name + "' must be "
          + length + ", but it is " + args.length);
    }
    return args;
  }

  public static short[] requireLengthBe(final String name, final short[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '" + name + "' must be "
          + length + ", but it is " + args.length);
    }
    return args;
  }

  public static int[] requireLengthBe(final String name, final int[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static long[] requireLengthBe(final String name, final long[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static float[] requireLengthBe(final String name, final float[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static double[] requireLengthBe(final String name, final double[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static <T> T[] requireLengthBe(final String name, final T[] args,
      final int length) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static String requireLengthBe(final String name, final String arg,
      final int length) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.length() != length) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be "
          + length
          + ", but it is "
          + arg.length());
    }
    return arg;
  }

  public static <T> Collection<T> requireSizeBe(final String name,
      final Collection<T> arg, final int size) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.size() != size) {
      throw new IllegalArgumentException("The size of '"
          + name
          + "' must be "
          + size
          + ", but it is "
          + arg.size());
    }
    return arg;
  }

  public static boolean[] requireLengthAtLeast(final String name,
      final boolean[] args, final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static char[] requireLengthAtLeast(final String name, final char[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static byte[] requireLengthAtLeast(final String name, final byte[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static short[] requireLengthAtLeast(final String name, final short[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static int[] requireLengthAtLeast(final String name, final int[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static long[] requireLengthAtLeast(final String name, final long[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static float[] requireLengthAtLeast(final String name, final float[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static double[] requireLengthAtLeast(final String name, final double[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static <T> T[] requireLengthAtLeast(final String name, final T[] args,
      final int minLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static String requireLengthAtLeast(final String name, final String arg,
      final int minLength) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.length() < minLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at least "
          + minLength
          + ", but it is "
          + arg.length());
    }
    return arg;
  }

  public static <T> Collection<T> requireSizeAtLeast(final String name,
      final Collection<T> arg, final int minSize) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.size() < minSize) {
      throw new IllegalArgumentException("The size of '"
          + name
          + "' must be at least "
          + minSize
          + ", but it is "
          + arg.size());
    }
    return arg;
  }

  public static boolean[] requireLengthAtMost(final String name, final boolean[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static char[] requireLengthAtMost(final String name, final char[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static byte[] requireLengthAtMost(final String name, final byte[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static short[] requireLengthAtMost(final String name, final short[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static int[] requireLengthAtMost(final String name, final int[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static long[] requireLengthAtMost(final String name, final long[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static float[] requireLengthAtMost(final String name, final float[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static double[] requireLengthAtMost(final String name, final double[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static <T> T[] requireLengthAtMost(final String name, final T[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + args.length);
    }
    return args;
  }

  public static String requireLengthAtMost(final String name, final String arg,
      final int maxLength) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.length() > maxLength) {
      throw new IllegalArgumentException("The length of '"
          + name
          + "' must be at most "
          + maxLength
          + ", but it is "
          + arg.length());
    }
    return arg;
  }

  public static <T> Collection<T> requireSizeAtMost(final String name,
      final Collection<T> arg, final int maxSize) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.size() > maxSize) {
      throw new IllegalArgumentException("The size of '"
          + name
          + "' must be at most "
          + maxSize
          + ", but it is "
          + arg.size());
    }
    return arg;
  }

  public static byte requireZero(final String name, final byte arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  public static short requireZero(final String name, final short arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  public static int requireZero(final String name, final int arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  public static long requireZero(final String name, final long arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  public static float requireZero(final String name, final float arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  public static double requireZero(final String name, final double arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  public static byte requireNonZero(final String name, final byte arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  public static short requireNonZero(final String name, final short arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  public static int requireNonZero(final String name, final int arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  public static long requireNonZero(final String name, final long arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  public static float requireNonZero(final String name, final float arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  public static double requireNonZero(final String name, final double arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  public static byte requirePositive(final String name, final byte arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  public static short requirePositive(final String name, final short arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  public static int requirePositive(final String name, final int arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  public static long requirePositive(final String name, final long arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  public static float requirePositive(final String name, final float arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  public static double requirePositive(final String name, final double arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  public static byte requireNonPositive(final String name, final byte arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  public static short requireNonPositive(final String name, final short arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  public static int requireNonPositive(final String name, final int arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  public static long requireNonPositive(final String name, final long arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  public static float requireNonPositive(final String name, final float arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  public static double requireNonPositive(final String name, final double arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  public static byte requireNegative(final String name, final byte arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  public static short requireNegative(final String name, final short arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  public static int requireNegative(final String name, final int arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  public static long requireNegative(final String name, final long arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  public static float requireNegative(final String name, final float arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  public static double requireNegative(final String name, final double arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  public static byte requireNonNegative(final String name, final byte arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  public static short requireNonNegative(final String name, final short arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  public static int requireNonNegative(final String name, final int arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  public static long requireNonNegative(final String name, final long arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  public static float requireNonNegative(final String name, final float arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  public static double requireNonNegative(final String name, final double arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  public static <T> void requireSame(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1
          + " and "
          + name2
          + " must be the same object.");
    }
  }

  public static <T> void requireNonSame(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException(name1
          + " and "
          + name2
          + " can not be the same object.");
    }
  }

  public static boolean requireEqual(final String name1, final boolean arg1,
      final String name2, final boolean arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static char requireEqual(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static byte requireEqual(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static short requireEqual(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static int requireEqual(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static long requireEqual(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static float requireEqual(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (!MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static double requireEqual(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (!MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static <T> T requireEqual(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (!Equality.equals(arg1, arg2)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  public static boolean requireNotEqual(final String name1, final boolean arg1,
      final String name2, final boolean arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static char requireNotEqual(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static byte requireNotEqual(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static short requireNotEqual(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static int requireNotEqual(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static long requireNotEqual(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static float requireNotEqual(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static double requireNotEqual(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  public static <T> T requireNotEqual(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (Equality.equals(arg1, arg2)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " can not be equal.");
    }
    return arg1;
  }

  public static char requireLess(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static byte requireLess(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static short requireLess(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static int requireLess(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static long requireLess(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 >= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static float requireLess(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (arg1 > arg2 || MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static double requireLess(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (arg1 > arg2 || MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireLess(final String name1,
      final T arg1, final String name2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc >= 0) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static char requireLessEqual(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static byte requireLessEqual(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static short requireLessEqual(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static int requireLessEqual(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static long requireLessEqual(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static float requireLessEqual(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      return arg1;
    }
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static double requireLessEqual(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      return arg1;
    }
    if (arg1 > arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireLessEqual(final String name1,
      final T arg1, final String name2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc > 0) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be less equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static char requireGreater(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static byte requireGreater(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static short requireGreater(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static int requireGreater(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static long requireGreater(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 <= arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static float requireGreater(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (arg1 < arg2 || MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static double requireGreater(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (arg1 < arg2 || MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireGreater(final String name1,
      final T arg1, final String name2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc <= 0) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static char requireGreaterEqual(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static byte requireGreaterEqual(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static short requireGreaterEqual(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static int requireGreaterEqual(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static long requireGreaterEqual(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static float requireGreaterEqual(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      return arg1;
    }
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static double requireGreaterEqual(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      return arg1;
    }
    if (arg1 < arg2) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static <T extends Comparable<T>> T requireGreaterEqual(final String name1,
      final T arg1, final String name2, final T arg2) {
    final int rc = arg1.compareTo(arg2);
    if (rc < 0) {
      throw new IllegalArgumentException(name1
          + "("
          + arg1
          + ") must be greater equal than "
          + name2
          + "("
          + arg2
          + ").");
    }
    return arg1;
  }

  public static byte requireInCloseRange(final String name, final byte arg,
      final byte left, final byte right) {
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInCloseRange(final String name,
      @Nullable final Byte arg, final byte left, final byte right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInCloseRange(final String name, final short arg,
      final short left, final short right) {
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInCloseRange(final String name,
      @Nullable final Short arg, final short left, final short right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInCloseRange(final String name, final int arg,
      final int left, final int right) {
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInCloseRange(final String name,
      @Nullable final Integer arg, final int left, final int right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInCloseRange(final String name, final long arg,
      final long left, final long right) {
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInCloseRange(final String name,
      @Nullable final Long arg, final long left, final long right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInCloseRange(final String name, final float arg,
      final float left, final float right, final float epsilon) {
    if (MathEx.equal(arg, left, epsilon) || MathEx.equal(arg, right, epsilon)) {
      return arg;
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInCloseRange(final String name,
      @Nullable final Float arg, final float left, final float right,
      final float epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if (MathEx.equal(arg, left, epsilon) || MathEx.equal(arg, right, epsilon)) {
      return arg;
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInCloseRange(final String name, final double arg,
      final double left, final double right, final double epsilon) {
    if (MathEx.equal(arg, left, epsilon) || MathEx.equal(arg, right, epsilon)) {
      return arg;
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInCloseRange(final String name,
      @Nullable final Double arg, final double left, final double right,
      final double epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if (MathEx.equal(arg, left, epsilon) || MathEx.equal(arg, right, epsilon)) {
      return arg;
    }
    if ((arg < left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInOpenRange(final String name, final byte arg,
      final byte left, final byte right) {
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInOpenRange(final String name, final Byte arg,
      final byte left, final byte right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInOpenRange(final String name, final short arg,
      final short left, final short right) {
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInOpenRange(final String name, final Short arg,
      final short left, final short right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInOpenRange(final String name, final int arg,
      final int left, final int right) {
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInOpenRange(final String name, final Integer arg,
      final int left, final int right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInOpenRange(final String name, final long arg,
      final long left, final long right) {
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInOpenRange(final String name, final Long arg,
      final long left, final long right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInOpenRange(final String name, final float arg,
      final float left, final float right, final float epsilon) {
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)
        || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInOpenRange(final String name,
      @Nullable final Float arg, final float left, final float right,
      final float epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)
        || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInOpenRange(final String name, final float arg,
      final double left, final double right, final double epsilon) {
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)
        || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInOpenRange(final String name,
      @Nullable final Double arg, final double left, final double right,
      final double epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)
        || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInLeftOpenRange(final String name, final byte arg,
      final byte left, final byte right) {
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInLeftOpenRange(final String name, final Byte arg,
      final byte left, final byte right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInLeftOpenRange(final String name, final short arg,
      final short left, final short right) {
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInLeftOpenRange(final String name, final Short arg,
      final short left, final short right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInLeftOpenRange(final String name, final int arg,
      final int left, final int right) {
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInLeftOpenRange(final String name, final Integer arg,
      final int left, final int right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInLeftOpenRange(final String name, final long arg,
      final long left, final long right) {
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInLeftOpenRange(final String name, final Long arg,
      final long left, final long right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg <= left) || (arg > right)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInLeftOpenRange(final String name, final float arg,
      final float left, final float right, final float epsilon) {
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInLeftOpenRange(final String name,
      @Nullable final Float arg, final float left, final float right,
      final float epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInLeftOpenRange(final String name,
      final double arg, final double left, final double right,
      final double epsilon) {
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInLeftOpenRange(final String name,
      @Nullable final Double arg, final double left, final double right,
      final double epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left)
        || (arg > right)
        || MathEx.equal(arg, left, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInRightOpenRange(final String name, final byte arg,
      final byte left, final byte right) {
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static byte requireInRightOpenRange(final String name, final Byte arg,
      final byte left, final byte right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInRightOpenRange(final String name, final short arg,
      final short left, final short right) {
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static short requireInRightOpenRange(final String name, final Short arg,
      final short left, final short right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInRightOpenRange(final String name, final int arg,
      final int left, final int right) {
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireInRightOpenRange(final String name, final Integer arg,
      final int left, final int right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInRightOpenRange(final String name, final long arg,
      final long left, final long right) {
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static long requireInRightOpenRange(final String name, final Long arg,
      final long left, final long right) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg >= right)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInRightOpenRange(final String name, final float arg,
      final float left, final float right, final float epsilon) {
    if ((arg < left) || (arg > right) || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static float requireInRightOpenRange(final String name, final Float arg,
      final float left, final float right, final float epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg > right) || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInRightOpenRange(final String name, final double arg,
      final double left, final double right, final double epsilon) {
    if ((arg < left) || (arg > right) || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static double requireInRightOpenRange(final String name, final Double arg,
      final double left, final double right, final double epsilon) {
    if (arg == null) {
      throw new NullPointerException(name);
    }
    if ((arg < left) || (arg > right) || MathEx.equal(arg, right, epsilon)) {
      throw new IllegalArgumentException(name
          + " must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + arg);
    }
    return arg;
  }

  public static int requireIndexInCloseRange(final int index, final int left,
      final int right) {
    if ((index < left) || (index > right)) {
      throw new IndexOutOfBoundsException("The index must in the close range ["
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + index);
    }
    return index;
  }

  public static int requireIndexInOpenRange(final int index, final int left,
      final int right) {
    if ((index <= left) || (index >= right)) {
      throw new IndexOutOfBoundsException("The index must in the open range ("
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + index);
    }
    return index;
  }

  public static int requireIndexInLeftOpenRange(final int index, final int left,
      final int right) {
    if ((index <= left) || (index > right)) {
      throw new IndexOutOfBoundsException("The index must in the left open range ("
          + left
          + ", "
          + right
          + "], "
          + " but it is "
          + index);
    }
    return index;
  }

  public static int requireIndexInRightOpenRange(final int index, final int left,
      final int right) {
    if ((index < left) || (index >= right)) {
      throw new IndexOutOfBoundsException("The index must in the right open range ["
          + left
          + ", "
          + right
          + "), "
          + " but it is "
          + index);
    }
    return index;
  }

  public static byte requireInEnum(final String name, final byte arg,
      final byte[] allowedValues) {
    for (final byte allowedValue : allowedValues) {
      if (arg == allowedValue) {
        return arg;
      }
    }
    throw new IllegalArgumentException(name + " must in enumeration ["
        + new Joiner(',').addAll(allowedValues).toString()
        + "], but it is " + arg);
  }

  public static short requireInEnum(final String name, final short arg,
      final short[] allowedValues) {
    for (final short allowedValue : allowedValues) {
      if (arg == allowedValue) {
        return arg;
      }
    }
    throw new IllegalArgumentException(name + " must in enumeration ["
        + new Joiner(',').addAll(allowedValues).toString()
        + "], but it is " + arg);
  }

  public static int requireInEnum(final String name, final int arg,
      final int[] allowedValues) {
    for (final int allowedValue : allowedValues) {
      if (arg == allowedValue) {
        return arg;
      }
    }
    throw new IllegalArgumentException(name + " must in enumeration ["
        + new Joiner(',').addAll(allowedValues).toString()
        + "], but it is " + arg);
  }

  public static long requireInEnum(final String name, final long arg,
      final long[] allowedValues) {
    for (final long allowedValue : allowedValues) {
      if (arg == allowedValue) {
        return arg;
      }
    }
    throw new IllegalArgumentException(name + " must in enumeration ["
        + new Joiner(',').addAll(allowedValues).toString()
        + "], but it is " + arg);
  }

  public static int requireValidUnicode(final String name, final int codePoint) {
    if (!Unicode.isValidUnicode(codePoint)) {
      throw new IllegalArgumentException(name
          + " must be a valid Unicode code point,"
          + " but it is "
          + codePoint);
    }
    return codePoint;
  }
}
