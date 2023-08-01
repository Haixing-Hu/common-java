////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.math.IntBit;

import java.util.Collection;
import javax.annotation.concurrent.ThreadSafe;

/**
 * This class provides functions for calculating the hash code.
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
 */
@ThreadSafe
public final class Hash {

  private Hash() {}

  public static int combine(final int code, final int multiplier, final boolean value) {
    return (code * multiplier) + (value ? 1 : 0);
  }

  public static int combine(final int code, final int multiplier, final boolean[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        result = (result * multiplier) + (value[i] ? 1 : 0);
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final char value) {
    return (code * multiplier) + value;
  }

  public static int combine(final int code, final int multiplier, final char[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        result = (result * multiplier) + value[i];
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final byte value) {
    return (code * multiplier) + value;
  }

  public static int combine(final int code, final int multiplier, final byte[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        result = (result * multiplier) + value[i];
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final short value) {
    return (code * multiplier) + value;
  }

  public static int combine(final int code, final int multiplier, final short[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        result = (result * multiplier) + value[i];
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final int value) {
    return (code * multiplier) + value;
  }

  public static int combine(final int code, final int multiplier, final int[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        result = (result * multiplier) + value[i];
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final long value) {
    return (code * multiplier) + (int) (value ^ (value >> IntBit.BITS));
  }

  public static int combine(final int code, final int multiplier, final long[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final long v = value[i];
        result = (result * multiplier) + (int) (v ^ (v >> IntBit.BITS));
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final float value) {
    return (code * multiplier) + Float.floatToIntBits(value);
  }

  public static int combine(final int code, final int multiplier, final float[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        result = (result * multiplier) + Float.floatToIntBits(value[i]);
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final double value) {
    final long bits = Double.doubleToLongBits(value);
    return (code * multiplier) + (int) (bits ^ (bits >> IntBit.BITS));
  }

  public static int combine(final int code, final int multiplier, final double[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final long bits = Double.doubleToLongBits(value[i]);
        result = (result * multiplier) + (int) (bits ^ (bits >> IntBit.BITS));
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Boolean value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Boolean[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Boolean x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Character value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Character[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Character x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Byte value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Byte[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Byte x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Short value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Short[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Short x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Integer value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Integer[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Integer x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Long value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Long[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Long x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Float value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Float[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Float x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Double value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final Double[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final Double x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final String value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.hashCode();
    }
  }

  public static int combine(final int code, final int multiplier, final String[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final String x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.hashCode());
      }
      return result;
    }
  }

  public static <E extends Enum<E>> int combine(final int code,
      final int multiplier, final E value) {
    if (value == null) {
      return code * multiplier;
    } else {
      return code * multiplier + value.ordinal();
    }
  }

  public static <E extends Enum<E>> int combine(final int code,
      final int multiplier, final E[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        final E x = value[i];
        result = (result * multiplier) + (x == null ? 0 : x.ordinal());
      }
      return result;
    }
  }

  public static int combine(final int code, final int multiplier, final Object value) {
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

  public static int combine(final int code, final int multiplier, final Object[] value) {
    if (value == null) {
      return code * multiplier;
    } else {
      int result = code;
      for (int i = 0; i < value.length; ++i) {
        // Note that it's important to call the Hash.combine(int, int, Object)
        // to calculate the combination of hash code of value[i], since an
        // multi-dimensional array is an Object in Java.
        result = combine(result, multiplier, value[i]);
      }
      return result;
    }
  }

  public static <T> int combine(final int code, final int multiplier,
      final Collection<T> value) {
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
