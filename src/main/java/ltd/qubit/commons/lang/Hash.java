////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * This class provides functions for calculating the 32-bits hash code.
 *
 * <p>For example, suppose you need to combine the hash code of three variables:
 * a name, which is a String; a age, which is a int; and a addresses, which is an
 * array of Address objects; and a multi-array, which is a multi-dimensional int
 * array. First, randomly choose two ODD integer number, say, 11 and 12345; then
 * write the following codes:
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
 * @author Haixing Hu
 * @see Hash64
 */
@ThreadSafe
public final class Hash {

  private Hash() {}

  public static int combine(final int code, final int multiplier, final boolean value) {
    return (code * multiplier) + (value ? 1 : 0);
  }

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

  public static int combine(final int code, final int multiplier, final char value) {
    return (code * multiplier) + value;
  }

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

  public static int combine(final int code, final int multiplier, final byte value) {
    return (code * multiplier) + value;
  }

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

  public static int combine(final int code, final int multiplier, final short value) {
    return (code * multiplier) + value;
  }

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

  public static int combine(final int code, final int multiplier, final int value) {
    return (code * multiplier) + value;
  }

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

  public static int combine(final int code, final int multiplier, final long value) {
    return (code * multiplier) + (int) (value ^ (value >> IntBit.BITS));
  }

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

  public static int combine(final int code, final int multiplier, final float value) {
    return (code * multiplier) + Float.floatToIntBits(value);
  }

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

  public static int combine(final int code, final int multiplier, final double value) {
    final long bits = Double.doubleToLongBits(value);
    return (code * multiplier) + (int) (bits ^ (bits >> IntBit.BITS));
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Boolean value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Character value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Byte value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Short value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Integer value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Long value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Float value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final Double value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static int combine(final int code, final int multiplier,
      @Nullable final String value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

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

  public static <E extends Enum<E>> int combine(final int code, final int multiplier,
      @Nullable final E value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.ordinal();
    }
  }

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
