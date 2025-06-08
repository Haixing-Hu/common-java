////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BYTE_ARRAY;

/**
 * This class provides operations on {@link BigInteger} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@link BigInteger} objects to
 * common types.
 *
 * @author Haixing Hu
 */
public class BigIntegerUtils {

  private static final int DECIMAL_RADIX = 10;

  public static boolean toBoolean(@Nullable final BigInteger value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.signum() != 0));
  }

  public static boolean toBoolean(@Nullable final BigInteger value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.signum() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.signum() != 0);
  }

  public static Boolean toBooleanObject(@Nullable final BigInteger value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : Boolean.valueOf(value.signum() != 0));
  }

  public static char toChar(@Nullable final BigInteger value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.shortValue());
  }

  public static char toChar(@Nullable final BigInteger value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.shortValue());
  }

  public static Character toCharObject(@Nullable final BigInteger value) {
    return (value == null ? null : (char) value.shortValue());
  }

  public static Character toCharObject(@Nullable final BigInteger value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue
                          : Character.valueOf((char) value.shortValue()));
  }

  public static byte toByte(@Nullable final BigInteger value) {
    return (value == null ? ByteUtils.DEFAULT : value.byteValue());
  }

  public static byte toByte(@Nullable final BigInteger value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  public static Byte toByteObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.byteValue());
  }

  public static Byte toByteObject(@Nullable final BigInteger value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(value.byteValue()));
  }

  public static short toShort(@Nullable final BigInteger value) {
    return (value == null ? IntUtils.DEFAULT : value.shortValue());
  }

  public static short toShort(@Nullable final BigInteger value,
      final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  public static Short toShortObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.shortValue());
  }

  public static Short toShortObject(@Nullable final BigInteger value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(value.shortValue()));
  }

  public static int toInt(@Nullable final BigInteger value) {
    return (value == null ? IntUtils.DEFAULT : value.intValue());
  }

  public static int toInt(@Nullable final BigInteger value,
      final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  public static Integer toIntObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.intValue());
  }

  public static Integer toIntObject(@Nullable final BigInteger value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(value.intValue()));
  }

  public static long toLong(@Nullable final BigInteger value) {
    return (value == null ? LongUtils.DEFAULT : value.longValue());
  }

  public static long toLong(@Nullable final BigInteger value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  public static Long toLongObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.longValue());
  }

  public static Long toLongObject(@Nullable final BigInteger value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.longValue()));
  }

  public static float toFloat(@Nullable final BigInteger value) {
    return (value == null ? FloatUtils.DEFAULT : value.floatValue());
  }

  public static float toFloat(@Nullable final BigInteger value,
      final float defaultValue) {
    return (value == null ? defaultValue : value.floatValue());
  }

  public static Float toFloatObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.floatValue());
  }

  public static Float toFloatObject(@Nullable final BigInteger value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.floatValue()));
  }

  public static double toDouble(@Nullable final BigInteger value) {
    return (value == null ? DoubleUtils.DEFAULT : value.doubleValue());
  }

  public static double toDouble(@Nullable final BigInteger value,
      final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  public static Double toDoubleObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.doubleValue());
  }

  public static Double toDoubleObject(@Nullable final BigInteger value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.doubleValue()));
  }

  public static String toString(@Nullable final BigInteger value) {
    return (value == null ? null : value.toString(DECIMAL_RADIX));
  }

  public static String toString(@Nullable final BigInteger value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : value.toString(DECIMAL_RADIX));
  }

  public static Date toDate(@Nullable final BigInteger value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final BigInteger value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(@Nullable final BigInteger value) {
    if (value == null) {
      return null;
    } else if (value.signum() == 0) {
      return EMPTY_BYTE_ARRAY;
    } else {
      return value.toByteArray();
    }
  }

  public static byte[] toByteArray(@Nullable final BigInteger value,
      @Nullable final byte[] defaultValue) {
    if (value == null) {
      return defaultValue;
    } else if (value.signum() == 0) {
      return EMPTY_BYTE_ARRAY;
    } else {
      return value.toByteArray();
    }
  }

  public static Class<?> toClass(@Nullable final BigInteger value) {
    return (value == null ? null : BigInteger.class);
  }

  public static Class<?> toClass(@Nullable final BigInteger value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : BigInteger.class);
  }

  public static BigDecimal toBigDecimal(@Nullable final BigInteger value) {
    return (value == null ? null : new BigDecimal(value));
  }

  public static BigDecimal toBigDecimal(@Nullable final BigInteger value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : new BigDecimal(value));
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code BigInteger}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code BigInteger}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }
}