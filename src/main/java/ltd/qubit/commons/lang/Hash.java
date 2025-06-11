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

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.math.IntBit;

/**
 * 该类提供用于计算32位哈希码的函数。
 *
 * <p>例如，假设你需要组合三个变量的哈希码：
 * 一个名称，它是一个String；一个年龄，它是一个int；以及一个地址数组，它是一个
 * Address对象数组；以及一个多维数组，它是一个多维int数组。首先，随机选择两个奇数，
 * 比如11和12345；然后编写以下代码：
 *
 * <pre>
 *    String name = ...;
 *    int age = ...;
 *    Address[] addreses = ...;
 *    int[][] multiarray = ...;
 *
 *    int code = 11;
 *    int multiplier = 12345;
 *    code = Hash.combine(code, multiplier, name);
 *    code = Hash.combine(code, multiplier, age);
 *    code = Hash.combine(code, multiplier, contact);
 *    code = Hash.combine(code, multiplier, multiarray);
 *    return code;
 * </pre>
 *
 * @author 胡海星
 * @see Hash64
 */
@ThreadSafe
public final class Hash {

  private Hash() {}

  /**
   * 将boolean值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的boolean值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final boolean value) {
    return (code * multiplier) + (value ? 1 : 0);
  }

  /**
   * 将boolean数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的boolean数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final boolean[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final boolean b : value) {
        result = (result * multiplier) + (b ? 1 : 0);
      }
      return result;
    }
  }

  /**
   * 将char值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的char值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final char value) {
    return (code * multiplier) + value;
  }

  /**
   * 将char数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的char数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final char[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final char c : value) {
        result = (result * multiplier) + c;
      }
      return result;
    }
  }

  /**
   * 将byte值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的byte值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final byte value) {
    return (code * multiplier) + value;
  }

  /**
   * 将byte数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的byte数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final byte[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final byte b : value) {
        result = (result * multiplier) + b;
      }
      return result;
    }
  }

  /**
   * 将short值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的short值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final short value) {
    return (code * multiplier) + value;
  }

  /**
   * 将short数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的short数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final short[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final short item : value) {
        result = (result * multiplier) + item;
      }
      return result;
    }
  }

  /**
   * 将int值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的int值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final int value) {
    return (code * multiplier) + value;
  }

  /**
   * 将int数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的int数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final int[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final int j : value) {
        result = (result * multiplier) + j;
      }
      return result;
    }
  }

  /**
   * 将long值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的long值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final long value) {
    return (code * multiplier) + (int) (value ^ (value >> IntBit.BITS));
  }

  /**
   * 将long数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的long数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final long[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final long v : value) {
        result = (result * multiplier) + (int) (v ^ (v >> IntBit.BITS));
      }
      return result;
    }
  }

  /**
   * 将float值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的float值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final float value) {
    return (code * multiplier) + Float.floatToIntBits(value);
  }

  /**
   * 将float数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的float数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final float[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final float v : value) {
        result = (result * multiplier) + Float.floatToIntBits(v);
      }
      return result;
    }
  }

  /**
   * 将double值与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的double值。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier, final double value) {
    final long bits = Double.doubleToLongBits(value);
    return (code * multiplier) + (int) (bits ^ (bits >> IntBit.BITS));
  }

  /**
   * 将double数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的double数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final double[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final double v : value) {
        final long bits = Double.doubleToLongBits(v);
        result = (result * multiplier) + (int) (bits ^ (bits >> IntBit.BITS));
      }
      return result;
    }
  }

  /**
   * 将Boolean对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Boolean对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Boolean value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Boolean数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Boolean数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Boolean[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Boolean x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Character对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Character对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Character value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Character数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Character数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Character[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Character x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Byte对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Byte对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Byte value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Byte数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Byte数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Byte[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Byte x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Short对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Short对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Short value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Short数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Short数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Short[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Short x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Integer对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Integer对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Integer value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Integer数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Integer数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Integer[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Integer x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Long对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Long对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Long value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Long数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Long数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Long[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Long x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Float对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Float对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Float value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Float数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Float数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Float[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Float x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将Double对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Double对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Double value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将Double数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Double数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Double[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Double x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将String对象与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的String对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final String value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  /**
   * 将String数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的String数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final String[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final String x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  /**
   * 将枚举值与现有哈希码组合。
   *
   * @param <E>
   *     枚举类型。
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的枚举值，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static <E extends Enum<E>> int combine(final int code, final int multiplier,
      @Nullable final E value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.ordinal();
    }
  }

  /**
   * 将枚举数组与现有哈希码组合。
   *
   * @param <E>
   *     枚举类型。
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的枚举数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static <E extends Enum<E>> int combine(final int code, final int multiplier,
      @Nullable final E[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final E x : value) {
        result = (result * multiplier) + (x == null ? 0 : x.ordinal());
      }
      return result;
    }
  }

  /**
   * 将Object对象与现有哈希码组合。
   *
   * <p>此方法可以正确处理各种类型的对象，包括基本类型数组和多维数组。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Object对象，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Object value) {
    if (value == null) {
      return code * multiplier;
    } else {
      final Class<?> valueClass = value.getClass();
      if (valueClass.isArray()) {
        // 'Switch' on type of array, to dispatch to the correct handler
        // This handles multi-dimensional arrays
        if (value instanceof boolean[]) {
          return combine(code, multiplier, (boolean[]) value);
        } else if (value instanceof char[]) {
          return combine(code, multiplier, (char[]) value);
        } else if (value instanceof byte[]) {
          return combine(code, multiplier, (byte[]) value);
        } else if (value instanceof short[]) {
          return combine(code, multiplier, (short[]) value);
        } else if (value instanceof int[]) {
          return combine(code, multiplier, (int[]) value);
        } else if (value instanceof long[]) {
          return combine(code, multiplier, (long[]) value);
        } else if (value instanceof float[]) {
          return combine(code, multiplier, (float[]) value);
        } else if (value instanceof double[]) {
          return combine(code, multiplier, (double[]) value);
        } else { // an array of non-primitives
          return combine(code, multiplier, (Object[]) value);
        }
      } else {
        // value is not an array
        return code * multiplier + value.hashCode();
      }
    }
  }

  /**
   * 将Object数组与现有哈希码组合。
   *
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Object数组，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static int combine(final int code, final int multiplier,
      @Nullable final Object[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final Object o : value) {
        // Note that it's important to call the Hash.combine(int, int, Object)
        // to calculate the combination of hash code of value[i], since an
        // multi-dimensional array is an Object in Java.
        result = combine(result, multiplier, o);
      }
      return result;
    }
  }

  /**
   * 将Collection集合与现有哈希码组合。
   *
   * @param <T>
   *     集合元素类型。
   * @param code
   *     现有的哈希码。
   * @param multiplier
   *     用于组合的乘数。
   * @param value
   *     要组合的Collection集合，可以为{@code null}。
   * @return
   *     组合后的哈希码。
   */
  public static <T> int combine(final int code, final int multiplier,
      @Nullable final Collection<T> value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (final T val : value) {
        // Note that it's important to call the Hash.combine(int, int, Object)
        // to calculate the combination of hash code of val, since an
        // multi-dimensional array is an Object in Java.
        result = combine(result, multiplier, val);
      }
      return result;
    }
  }
}