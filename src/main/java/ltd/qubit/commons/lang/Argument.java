////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import ltd.qubit.commons.math.MathEx;
import ltd.qubit.commons.text.Joiner;
import ltd.qubit.commons.text.Unicode;

import static ltd.qubit.commons.lang.StringUtils.isBlank;

/**
 * 提供常用的参数检查函数。
 * <p>
 * 该类包含了一系列静态方法，用于验证方法参数是否符合特定条件，例如非空、非负、
 * 在特定范围内等等。如果参数不满足指定条件，这些方法会抛出相应的异常。所有方法都
 * 设计为支持链式调用，即它们会在验证通过后返回原始参数值。
 *
 * @author 胡海星
 */
public final class Argument {

  /**
   * 检查当前边界。
   *
   * <p>注意，这个检查并非简单的边界检查，因为我们必须考虑整数溢出的情况。
   *
   * @param off
   *     偏移量。
   * @param n
   *     元素数量。
   * @param length
   *     序列长度。
   * @throws IndexOutOfBoundsException
   *     如果当前边界越界。
   */
  public static void checkBounds(final int off, final int n, final int length) {
    if ((off < 0) || (n < 0) || (off > (length - n))) {
      throw new IndexOutOfBoundsException("off: " + off
          + ", n: " + n
          + ", length: " + length);
    }
  }

  /**
   * 验证参数不为null。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   */
  public static <T> T requireNonNull(final String name, final T arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    return arg;
  }

  /**
   * 验证字符串参数既不为null也不为空白字符串。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空白字符串。
   */
  public static String requireNonBlank(final String name, final String arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (isBlank(arg)) {
      throw new IllegalArgumentException("The '" + name + "' can not be blank.");
    }
    return arg;
  }

  /**
   * 验证布尔数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的布尔数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static boolean[] requireNonEmpty(final String name, final boolean[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证字符数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字符数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static char[] requireNonEmpty(final String name, final char[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证字节数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字节数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static byte[] requireNonEmpty(final String name, final byte[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证短整型数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的短整型数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static short[] requireNonEmpty(final String name, final short[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证整型数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的整型数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static int[] requireNonEmpty(final String name, final int[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证长整型数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的长整型数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static long[] requireNonEmpty(final String name, final long[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证单精度浮点数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的单精度浮点数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static float[] requireNonEmpty(final String name, final float[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证双精度浮点数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的双精度浮点数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static double[] requireNonEmpty(final String name, final double[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证泛型数组参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的泛型数组参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空数组。
   */
  public static <T> T[] requireNonEmpty(final String name, final T[] args) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return args;
  }

  /**
   * 验证字符串参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空字符串。
   */
  public static String requireNonEmpty(final String name, final String arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.length() == 0) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return arg;
  }

  /**
   * 验证列表参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的列表参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空列表。
   */
  public static <T> List<T> requireNonEmpty(final String name,
      final List<T> arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.isEmpty()) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return arg;
  }

  /**
   * 验证集合参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的集合参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空集合。
   */
  public static <T> Set<T> requireNonEmpty(final String name,
      final Set<T> arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.isEmpty()) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return arg;
  }

  /**
   * 验证映射参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的映射参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空映射。
   */
  public static <K, V> Map<K, V> requireNonEmpty(final String name,
      final Map<K, V> arg) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.isEmpty()) {
      throw new IllegalArgumentException("The '" + name + "' can not be empty.");
    }
    return arg;
  }

  /**
   * 验证集合参数既不为null也不为空。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的集合参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数为空集合。
   */
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

  /**
   * 验证布尔数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的布尔数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证字符数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字符数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证字节数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字节数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证短整型数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的短整型数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证整型数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的整型数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证长整型数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的长整型数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证单精度浮点数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的单精度浮点数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证双精度浮点数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的双精度浮点数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证泛型数组参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的泛型数组参数。
   * @param length
   *     要求的数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证字符串参数长度必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param length
   *     要求的字符串长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度不等于指定值。
   */
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

  /**
   * 验证集合参数大小必须为指定值。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的集合参数。
   * @param size
   *     要求的集合大小。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数大小不等于指定值。
   */
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

  /**
   * 验证布尔数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的布尔数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证字符数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字符数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证字节数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字节数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证短整型数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的短整型数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证整型数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的整型数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证长整型数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的长整型数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证单精度浮点数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的单精度浮点数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证双精度浮点数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的双精度浮点数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证泛型数组参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的泛型数组参数。
   * @param minLength
   *     要求的最小数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证字符串参数长度必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param minLength
   *     要求的最小字符串长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度小于指定最小值。
   */
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

  /**
   * 验证集合参数大小必须至少为指定最小值。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的集合参数。
   * @param minSize
   *     要求的最小集合大小。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数大小小于指定最小值。
   */
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

  /**
   * 验证布尔数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的布尔数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证字符数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字符数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证字节数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的字节数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
  public static byte[] requireLengthAtMost(final String name, final byte[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + name + "' must be "
          + maxLength + ", but it is " + args.length);
    }
    return args;
  }

  /**
   * 验证短整型数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的短整型数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
  public static short[] requireLengthAtMost(final String name, final short[] args,
      final int maxLength) {
    if (args == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (args.length > maxLength) {
      throw new IllegalArgumentException("The length of '" + name + "' must be "
          + maxLength + ", but it is " + args.length);
    }
    return args;
  }

  /**
   * 验证整型数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的整型数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证长整型数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的长整型数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证单精度浮点数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的单精度浮点数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证双精度浮点数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的双精度浮点数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证泛型数组参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param args
   *     被验证的泛型数组参数。
   * @param maxLength
   *     要求的最大数组长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证字符串参数长度必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param maxLength
   *     要求的最大字符串长度。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数长度大于指定最大值。
   */
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

  /**
   * 验证集合参数大小必须最多为指定最大值。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的集合参数。
   * @param maxSize
   *     要求的最大集合大小。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数大小大于指定最大值。
   */
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

  /**
   * 验证字节参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static byte requireZero(final String name, final byte arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证短整型参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static short requireZero(final String name, final short arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证整型参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static int requireZero(final String name, final int arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证长整型参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static long requireZero(final String name, final long arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static float requireZero(final String name, final float arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static double requireZero(final String name, final double arg) {
    if (arg != 0) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static float requireZero(final String name, final float arg, final float epsilon) {
    if (Math.abs(arg) > epsilon) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不为零。
   */
  public static double requireZero(final String name, final double arg, final double epsilon) {
    if (Math.abs(arg) > epsilon) {
      throw new IllegalArgumentException(name + " must be zero.");
    }
    return arg;
  }

  /**
   * 验证字节参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static byte requireNonZero(final String name, final byte arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证短整型参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static short requireNonZero(final String name, final short arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证整型参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static int requireNonZero(final String name, final int arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证长整型参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static long requireNonZero(final String name, final long arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static float requireNonZero(final String name, final float arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static double requireNonZero(final String name, final double arg) {
    if (arg == 0) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static float requireNonZero(final String name, final float arg, final double epsilon) {
    if (Math.abs(arg) <= epsilon) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须不为零。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数为零。
   */
  public static double requireNonZero(final String name, final double arg, final double epsilon) {
    if (Math.abs(arg) <= epsilon) {
      throw new IllegalArgumentException(name + " cannot be zero.");
    }
    return arg;
  }

  /**
   * 验证字节参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static byte requirePositive(final String name, final byte arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证短整型参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static short requirePositive(final String name, final short arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证整型参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static int requirePositive(final String name, final int arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证长整型参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static long requirePositive(final String name, final long arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static float requirePositive(final String name, final float arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static double requirePositive(final String name, final double arg) {
    if (arg <= 0) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static float requirePositive(final String name, final float arg, final float epsilon) {
    if (arg <= 0 || MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为正值（大于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是正值。
   */
  public static double requirePositive(final String name, final double arg, final double epsilon) {
    if (arg <= 0 || MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be positive.");
    }
    return arg;
  }

  /**
   * 验证字节参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static byte requireNonPositive(final String name, final byte arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证短整型参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static short requireNonPositive(final String name, final short arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证整型参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static int requireNonPositive(final String name, final int arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证长整型参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static long requireNonPositive(final String name, final long arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static float requireNonPositive(final String name, final float arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static double requireNonPositive(final String name, final double arg) {
    if (arg > 0) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static float requireNonPositive(final String name, final float arg, final float epsilon) {
    if (arg > 0 && !MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为非正值（小于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非正值。
   */
  public static double requireNonPositive(final String name, final double arg, final double epsilon) {
    if (arg > 0 && !MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be non-positive.");
    }
    return arg;
  }

  /**
   * 验证字节参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static byte requireNegative(final String name, final byte arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证短整型参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static short requireNegative(final String name, final short arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证整型参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static int requireNegative(final String name, final int arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证长整型参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static long requireNegative(final String name, final long arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static float requireNegative(final String name, final float arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static double requireNegative(final String name, final double arg) {
    if (arg >= 0) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static float requireNegative(final String name, final float arg, final float epsilon) {
    if (arg >= 0 || MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为负值（小于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是负值。
   */
  public static double requireNegative(final String name, final double arg, final double epsilon) {
    if (arg >= 0 || MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be negative.");
    }
    return arg;
  }

  /**
   * 验证字节参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static byte requireNonNegative(final String name, final byte arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证短整型参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static short requireNonNegative(final String name, final short arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证整型参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static int requireNonNegative(final String name, final int arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证长整型参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static long requireNonNegative(final String name, final long arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static float requireNonNegative(final String name, final float arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static double requireNonNegative(final String name, final double arg) {
    if (arg < 0) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证单精度浮点参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static float requireNonNegative(final String name, final float arg, final float epsilon) {
    if (arg < 0 && !MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证双精度浮点参数必须为非负值（大于等于零）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是非负值。
   */
  public static double requireNonNegative(final String name, final double arg, final double epsilon) {
    if (arg < 0 && !MathEx.equal(arg, 0, epsilon)) {
      throw new IllegalArgumentException(name + " must be non-negative.");
    }
    return arg;
  }

  /**
   * 验证两个参数是同一个对象。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不是同一个对象。
   */
  public static <T> void requireSame(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1
          + " and "
          + name2
          + " must be the same object.");
    }
  }

  /**
   * 验证两个参数不是同一个对象。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个参数。
   * @throws IllegalArgumentException
   *     如果两个参数是同一个对象。
   */
  public static <T> void requireNonSame(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException(name1
          + " and "
          + name2
          + " can not be the same object.");
    }
  }

  /**
   * 验证两个布尔参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个布尔参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个布尔参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static boolean requireEqual(final String name1, final boolean arg1,
      final String name2, final boolean arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个字符参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字符参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字符参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static char requireEqual(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个字节参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字节参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字节参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static byte requireEqual(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个短整型参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个短整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个短整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static short requireEqual(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个整型参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static int requireEqual(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个长整型参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个长整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个长整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static long requireEqual(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 != arg2) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个单精度浮点参数相等（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static float requireEqual(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (!MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个双精度浮点参数相等（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static double requireEqual(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (!MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个泛型参数相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个泛型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个泛型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数不相等。
   */
  public static <T> T requireEqual(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (!Equality.equals(arg1, arg2)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " must be equal.");
    }
    return arg1;
  }

  /**
   * 验证两个布尔参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个布尔参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个布尔参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static boolean requireNotEqual(final String name1, final boolean arg1,
      final String name2, final boolean arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个字符参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字符参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字符参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static char requireNotEqual(final String name1, final char arg1,
      final String name2, final char arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个字节参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字节参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字节参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static byte requireNotEqual(final String name1, final byte arg1,
      final String name2, final byte arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个短整型参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个短整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个短整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static short requireNotEqual(final String name1, final short arg1,
      final String name2, final short arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个整型参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static int requireNotEqual(final String name1, final int arg1,
      final String name2, final int arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个长整型参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个长整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个长整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static long requireNotEqual(final String name1, final long arg1,
      final String name2, final long arg2) {
    if (arg1 == arg2) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个单精度浮点参数不相等（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static float requireNotEqual(final String name1, final float arg1,
      final String name2, final float arg2, final float epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个双精度浮点参数不相等（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static double requireNotEqual(final String name1, final double arg1,
      final String name2, final double arg2, final double epsilon) {
    if (MathEx.equal(arg1, arg2, epsilon)) {
      throw new IllegalArgumentException("The '" + name1
          + "' must not equal to " + name2);
    }
    return arg1;
  }

  /**
   * 验证两个泛型参数不相等。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个泛型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个泛型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果两个参数相等。
   */
  public static <T> T requireNotEqual(final String name1, final T arg1,
      final String name2, final T arg2) {
    if (Equality.equals(arg1, arg2)) {
      throw new IllegalArgumentException(name1 + " and "
          + name2 + " can not be equal.");
    }
    return arg1;
  }

  /**
   * 验证第一个字符参数必须小于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字符参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字符参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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

  /**
   * 验证第一个字节参数必须小于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字节参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字节参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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

  /**
   * 验证第一个短整型参数必须小于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个短整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个短整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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

  /**
   * 验证第一个整型参数必须小于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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

  /**
   * 验证第一个长整型参数必须小于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个长整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个长整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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


  /**
   * 验证第一个单精度浮点参数必须小于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
  public static float requireLess(final String name1, final float arg1,
      final String name2, final float arg2) {
    return requireLess(name1, arg1, name2, arg2, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 验证第一个单精度浮点参数必须小于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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


  /**
   * 验证第一个双精度浮点参数必须小于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
  public static double requireLess(final String name1, final double arg1,
      final String name2, final double arg2) {
    return requireLess(name1, arg1, name2, arg2, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 验证第一个双精度浮点参数必须小于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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

  /**
   * 验证第一个可比较参数必须小于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个可比较参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个可比较参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不小于第二个参数。
   */
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

  /**
   * 验证第一个字符参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字符参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字符参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个字节参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字节参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字节参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个短整型参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个短整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个短整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个整型参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个长整型参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个长整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个长整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个单精度浮点参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
  public static float requireLessEqual(final String name1, final float arg1,
      final String name2, final float arg2) {
    return requireLessEqual(name1, arg1, name2, arg2, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 验证第一个单精度浮点参数必须小于等于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个双精度浮点参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
  public static double requireLessEqual(final String name1, final double arg1,
      final String name2, final double arg2) {
    return requireLessEqual(name1, arg1, name2, arg2, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 验证第一个双精度浮点参数必须小于等于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个可比较参数必须小于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个可比较参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个可比较参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数大于第二个参数。
   */
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

  /**
   * 验证第一个字符参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字符参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字符参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个字节参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字节参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字节参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个短整型参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个短整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个短整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个整型参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个长整型参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个长整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个长整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个单精度浮点参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
  public static float requireGreater(final String name1, final float arg1,
      final String name2, final float arg2) {
    return requireGreater(name1, arg1, name2, arg2, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 验证第一个单精度浮点参数必须大于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个双精度浮点参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
  public static double requireGreater(final String name1, final double arg1,
      final String name2, final double arg2) {
    return requireGreater(name1, arg1, name2, arg2, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 验证第一个双精度浮点参数必须大于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个可比较参数必须大于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个可比较参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个可比较参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数不大于第二个参数。
   */
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

  /**
   * 验证第一个字符参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字符参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字符参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个字节参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个字节参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个字节参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个短整型参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个短整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个短整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个整型参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个长整型参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个长整型参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个长整型参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个单精度浮点参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
  public static float requireGreaterEqual(final String name1, final float arg1,
      final String name2, final float arg2) {
    return requireGreaterEqual(name1, arg1, name2, arg2, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 验证第一个单精度浮点参数必须大于等于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个单精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个单精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个双精度浮点参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
  public static double requireGreaterEqual(final String name1, final double arg1,
      final String name2, final double arg2) {
    return requireGreaterEqual(name1, arg1, name2, arg2, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 验证第一个双精度浮点参数必须大于等于第二个参数（考虑误差范围）。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个双精度浮点参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个双精度浮点参数。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证第一个可比较参数必须大于等于第二个参数。
   *
   * @param name1
   *     第一个参数的名称。
   * @param arg1
   *     被验证的第一个可比较参数。
   * @param name2
   *     第二个参数的名称。
   * @param arg2
   *     被验证的第二个可比较参数。
   * @return
   *     如果验证通过，返回第一个参数。
   * @throws IllegalArgumentException
   *     如果第一个参数小于第二个参数。
   */
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

  /**
   * 验证字节参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的字节参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的字节参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证短整型参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的短整型参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的短整型参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证整型参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的整型参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的整型参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证长整型参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的长整型参数必须在闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的长整型参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证单精度浮点参数必须在闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的单精度浮点参数必须在闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的单精度浮点参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证双精度浮点参数必须在闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的双精度浮点参数必须在闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的双精度浮点参数。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证字节参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的字节参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的字节参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证短整型参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的短整型参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的短整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证整型参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的整型参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证长整型参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的长整型参数必须在开区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的长整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证单精度浮点参数必须在开区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的单精度浮点参数必须在开区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的单精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证双精度浮点参数必须在开区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的双精度浮点参数必须在开区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的双精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证字节参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的字节参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的字节参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证短整型参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的短整型参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的短整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证整型参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的整型参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证长整型参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的长整型参数必须在左开右闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的长整型参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证单精度浮点参数必须在左开右闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的单精度浮点参数必须在左开右闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的单精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证双精度浮点参数必须在左开右闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的双精度浮点参数必须在左开右闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的双精度浮点参数。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证字节参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的字节参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的字节参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证短整型参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的短整型参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的短整型参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证整型参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的整型参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的整型参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证长整型参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的长整型参数必须在右开左闭区间范围内。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的长整型参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证单精度浮点参数必须在右开左闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的单精度浮点参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的单精度浮点参数必须在右开左闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的单精度浮点参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证双精度浮点参数必须在右开左闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的双精度浮点参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证可为空的双精度浮点参数必须在右开左闭区间范围内（考虑误差范围）。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的可为空的双精度浮点参数。
   * @param left
   *     范围的左边界（右开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @param epsilon
   *     可接受的误差范围。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws NullPointerException
   *     如果参数为null。
   * @throws IllegalArgumentException
   *     如果参数不在指定范围内。
   */
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

  /**
   * 验证索引值必须在闭区间范围内（包含边界）。
   *
   * @param index
   *     被验证的索引值。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回索引值本身。
   * @throws IndexOutOfBoundsException
   *     如果索引值不在指定范围内。
   */
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

  /**
   * 验证索引值必须在开区间范围内（不包含边界）。
   *
   * @param index
   *     被验证的索引值。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回索引值本身。
   * @throws IndexOutOfBoundsException
   *     如果索引值不在指定范围内。
   */
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

  /**
   * 验证索引值必须在左开右闭区间范围内（不包含左边界，包含右边界）。
   *
   * @param index
   *     被验证的索引值。
   * @param left
   *     范围的左边界（开区间）。
   * @param right
   *     范围的右边界（闭区间）。
   * @return
   *     如果验证通过，返回索引值本身。
   * @throws IndexOutOfBoundsException
   *     如果索引值不在指定范围内。
   */
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

  /**
   * 验证索引值必须在右开左闭区间范围内（包含左边界，不包含右边界）。
   *
   * @param index
   *     被验证的索引值。
   * @param left
   *     范围的左边界（闭区间）。
   * @param right
   *     范围的右边界（开区间）。
   * @return
   *     如果验证通过，返回索引值本身。
   * @throws IndexOutOfBoundsException
   *     如果索引值不在指定范围内。
   */
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

  /**
   * 验证字节参数必须是指定枚举值之一。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字节参数。
   * @param allowedValues
   *     允许的枚举值数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是指定枚举值之一。
   */
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

  /**
   * 验证短整型参数必须是指定枚举值之一。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的短整型参数。
   * @param allowedValues
   *     允许的枚举值数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是指定枚举值之一。
   */
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

  /**
   * 验证整型参数必须是指定枚举值之一。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的整型参数。
   * @param allowedValues
   *     允许的枚举值数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是指定枚举值之一。
   */
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

  /**
   * 验证长整型参数必须是指定枚举值之一。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的长整型参数。
   * @param allowedValues
   *     允许的枚举值数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是指定枚举值之一。
   */
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

  /**
   * 验证字符串参数必须是指定枚举值之一。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param allowedValues
   *     允许的枚举值数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是指定枚举值之一。
   * @throws NullPointerException
   *     如果参数为null。
   */
  public static String requireInEnum(final String name, final String arg,
      final String[] allowedValues) {
    for (final String allowedValue : allowedValues) {
      if (Equality.equals(arg, allowedValue)) {
        return arg;
      }
    }
    throw new IllegalArgumentException(name + " must in enumeration ["
        + new Joiner(',').addAll(allowedValues).toString()
        + "], but it is " + arg);
  }

  /**
   * 验证整型参数必须是有效的Unicode代码点。
   *
   * @param name
   *     参数的名称。
   * @param codePoint
   *     被验证的Unicode代码点参数。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不是有效的Unicode代码点。
   */
  public static int requireValidUnicode(final String name, final int codePoint) {
    if (!Unicode.isValidUnicode(codePoint)) {
      throw new IllegalArgumentException(name
          + " must be a valid Unicode code point,"
          + " but it is "
          + codePoint);
    }
    return codePoint;
  }

  /**
   * 验证对象参数必须在指定的集合中。
   *
   * @param <T>
   *     参数的类型。
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的对象参数。
   * @param list
   *     允许的值的集合。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不在指定集合中。
   * @throws NullPointerException
   *     如果参数或集合为null。
   */
  public static <T> T requireInCollection(final String name, final T arg,
      final Collection<? super T> list) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (!list.contains(arg)) {
      throw new IllegalArgumentException("The '" + name + "' must in the collection ["
          + new Joiner(',').addAll(list).toString()
          + "], but it is " + arg);
    }
    return arg;
  }

  /**
   * 验证对象参数不能在指定的集合中。
   *
   * @param <T>
   *     参数的类型。
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的对象参数。
   * @param list
   *     不允许的值的集合。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数在指定集合中。
   * @throws NullPointerException
   *     如果参数或集合为null。
   */
  public static <T> T requireNotInCollection(final String name, final T arg,
      final Collection<? super T> list) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (list.contains(arg)) {
      throw new IllegalArgumentException("The '" + name + "' must not in the list ["
          + new Joiner(',').addAll(list).toString()
          + "], but it is " + arg);
    }
    return arg;
  }

  /**
   * 验证字符串参数必须符合指定正则表达式。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param re
   *     正则表达式对象。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不符合指定正则表达式。
   * @throws NullPointerException
   *     如果参数或正则表达式对象为null。
   */
  public static String requireMatch(final String name, final String arg,
      final Pattern re) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (!re.matcher(arg).matches()) {
      throw new IllegalArgumentException("The '" + name + "' must match the regex ["
          + re.pattern()
          + "], but it is "
          + arg);
    }
    return arg;
  }

  /**
   * 验证字符串参数必须符合指定正则表达式。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param regex
   *     正则表达式字符串。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不符合指定正则表达式。
   * @throws NullPointerException
   *     如果参数或正则表达式字符串为null。
   */
  public static String requireMatch(final String name, final String arg,
      final String regex) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (!arg.matches(regex)) {
      throw new IllegalArgumentException("The '" + name + "' must match the regex ["
          + regex
          + "], but it is "
          + arg);
    }
    return arg;
  }

  /**
   * 验证字符串参数不能符合指定正则表达式。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param re
   *     正则表达式对象。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数符合指定正则表达式。
   * @throws NullPointerException
   *     如果参数或正则表达式对象为null。
   */
  public static String requireNotMatch(final String name, final String arg,
      final Pattern re) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (re.matcher(arg).matches()) {
      throw new IllegalArgumentException("The '" + name + "' must not match the regex ["
          + re.pattern()
          + "], but it is "
          + arg);
    }
    return arg;
  }

  /**
   * 验证字符串参数不能符合指定正则表达式。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的字符串参数。
   * @param regex
   *     正则表达式字符串。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数符合指定正则表达式。
   * @throws NullPointerException
   *     如果参数或正则表达式字符串为null。
   */
  public static String requireNotMatch(final String name, final String arg,
      final String regex) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (arg.matches(regex)) {
      throw new IllegalArgumentException("The '" + name + "' must not match the regex ["
          + regex
          + "], but it is "
          + arg);
    }
    return arg;
  }

  /**
   * 验证参数必须存在于指定的数组中。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的参数。
   * @param array
   *     指定的数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数不存在于指定的数组中。
   * @throws NullPointerException
   *     如果参数为null或数组为null。
   */
  public static <T> T requireInArray(final String name, final T arg, final T[] array) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (array == null) {
      throw new NullPointerException("The array can not be null.");
    }
    for (final T element : array) {
      if (Equality.equals(arg, element)) {
        return arg;
      }
    }
    throw new IllegalArgumentException("The '" + name + "' must be in the array, but it is " + arg);
  }

  /**
   * 验证参数不得存在于指定的数组中。
   *
   * @param name
   *     参数的名称。
   * @param arg
   *     被验证的参数。
   * @param array
   *     指定的数组。
   * @return
   *     如果验证通过，返回参数本身。
   * @throws IllegalArgumentException
   *     如果参数存在于指定的数组中。
   * @throws NullPointerException
   *     如果参数为null或数组为null。
   */
  public static <T> T requireNotInArray(final String name, final T arg, final T[] array) {
    if (arg == null) {
      throw new NullPointerException("The '" + name + "' can not be null.");
    }
    if (array == null) {
      throw new NullPointerException("The array can not be null.");
    }
    for (final T element : array) {
      if (Equality.equals(arg, element)) {
        throw new IllegalArgumentException("The '" + name + "' must not be in the array, but it is " + arg);
      }
    }
    return arg;
  }
}