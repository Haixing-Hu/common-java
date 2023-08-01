////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Nullable;

public class NumericUtils {

  public static BigDecimal toNumeric(final boolean value) {
    return (value ? BigDecimal.ONE : BigDecimal.ZERO);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Boolean value) {
    return (value == null ? null : (value ? BigDecimal.ONE : BigDecimal.ZERO));
  }

  public static BigDecimal toNumeric(final byte value) {
    return new BigDecimal(value);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Byte value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toNumeric(final short value) {
    return new BigDecimal(value);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Short value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toNumeric(final int value) {
    return new BigDecimal(value);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Integer value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toNumeric(final long value) {
    return new BigDecimal(value);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Long value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toNumeric(final float value) {
    return new BigDecimal(value);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Float value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toNumeric(final double value) {
    return new BigDecimal(value);
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Double value) {
    return (value == null ? null : new BigDecimal(value));
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final BigInteger value) {
    return (value == null ? null : new BigDecimal(value));
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final BigDecimal value) {
    return value;
  }

  @Nullable
  public static BigDecimal toNumeric(@Nullable final Object value) {
    if (value == null) {
      return null;
    } else if (value instanceof Boolean) {
      return toNumeric((Boolean) value);
    } else if (value instanceof Byte) {
      return toNumeric((Byte) value);
    } else if (value instanceof Short) {
      return toNumeric((Short) value);
    } else if (value instanceof Integer) {
      return toNumeric((Integer) value);
    } else if (value instanceof Long) {
      return toNumeric((Long) value);
    } else if (value instanceof Float) {
      return toNumeric((Float) value);
    } else if (value instanceof Double) {
      return toNumeric((Double) value);
    } else if (value instanceof BigInteger) {
      return toNumeric((BigInteger) value);
    } else if (value instanceof BigDecimal) {
      return toNumeric((BigDecimal) value);
    } else {
      throw new IllegalArgumentException("The value is not a numeric "
          + "representable value: " + value.getClass().getName());
    }
  }

  public static boolean isComparable(final Class<?> type) {
    return (type == boolean.class)
        || (type == Boolean.class)
        || (type == byte.class)
        || (type == Byte.class)
        || (type == short.class)
        || (type == Short.class)
        || (type == int.class)
        || (type == Integer.class)
        || (type == long.class)
        || (type == Long.class)
        || (type == float.class)
        || (type == Float.class)
        || (type == double.class)
        || (type == Double.class)
        || BigInteger.class.isAssignableFrom(type)
        || BigDecimal.class.isAssignableFrom(type);
  }
}
