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
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.MoneyCodec;

import com.google.common.collect.ImmutableSet;

import static java.lang.System.arraycopy;
import static java.math.BigDecimal.ZERO;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BYTE_ARRAY;
import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * This class provides operations on {@link BigDecimal} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@link BigDecimal} objects to
 * common types.
 *
 * @author Haixing Hu
 */
public class BigDecimalUtils {

  public static boolean toBoolean(@Nullable final BigDecimal value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.signum() != 0));
  }

  public static boolean toBoolean(@Nullable final BigDecimal value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.signum() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.signum() != 0);
  }

  public static Boolean toBooleanObject(@Nullable final BigDecimal value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : Boolean.valueOf(value.signum() != 0));
  }

  public static char toChar(@Nullable final BigDecimal value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.shortValue());
  }

  public static char toChar(@Nullable final BigDecimal value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.shortValue());
  }

  public static Character toCharObject(@Nullable final BigDecimal value) {
    return (value == null ? null : (char) value.shortValue());
  }

  public static Character toCharObject(@Nullable final BigDecimal value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue
                          : Character.valueOf((char) value.shortValue()));
  }

  public static byte toByte(@Nullable final BigDecimal value) {
    return (value == null ? ByteUtils.DEFAULT : value.byteValue());
  }

  public static byte toByte(@Nullable final BigDecimal value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  public static Byte toByteObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.byteValue());
  }

  public static Byte toByteObject(@Nullable final BigDecimal value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(value.byteValue()));
  }

  public static short toShort(@Nullable final BigDecimal value) {
    return (value == null ? IntUtils.DEFAULT : value.shortValue());
  }

  public static short toShort(@Nullable final BigDecimal value,
      final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  public static Short toShortObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.shortValue());
  }

  public static Short toShortObject(@Nullable final BigDecimal value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(value.shortValue()));
  }

  public static int toInt(@Nullable final BigDecimal value) {
    return (value == null ? IntUtils.DEFAULT : value.intValue());
  }

  public static int toInt(@Nullable final BigDecimal value,
      final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  public static Integer toIntObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.intValue());
  }

  public static Integer toIntObject(@Nullable final BigDecimal value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(value.intValue()));
  }

  public static long toLong(@Nullable final BigDecimal value) {
    return (value == null ? LongUtils.DEFAULT : value.longValue());
  }

  public static long toLong(@Nullable final BigDecimal value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  public static Long toLongObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.longValue());
  }

  public static Long toLongObject(@Nullable final BigDecimal value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.longValue()));
  }

  public static float toFloat(@Nullable final BigDecimal value) {
    return (value == null ? FloatUtils.DEFAULT : value.floatValue());
  }

  public static float toFloat(@Nullable final BigDecimal value,
      final float defaultValue) {
    return (value == null ? defaultValue : value.floatValue());
  }

  public static Float toFloatObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.floatValue());
  }

  public static Float toFloatObject(@Nullable final BigDecimal value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.floatValue()));
  }

  public static double toDouble(@Nullable final BigDecimal value) {
    return (value == null ? DoubleUtils.DEFAULT : value.doubleValue());
  }

  public static double toDouble(@Nullable final BigDecimal value,
      final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  public static Double toDoubleObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.doubleValue());
  }

  public static Double toDoubleObject(@Nullable final BigDecimal value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.doubleValue()));
  }

  public static String toString(@Nullable final BigDecimal value) {
    return (value == null ? null : value.toString());
  }

  public static String toString(@Nullable final BigDecimal value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : value.toString());
  }

  public static Date toDate(@Nullable final BigDecimal value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  public static Date toDate(@Nullable final BigDecimal value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  public static byte[] toByteArray(@Nullable final BigDecimal value) {
    if (value == null) {
      return null;
    }
    if (value.signum() == 0) {
      return EMPTY_BYTE_ARRAY;
    }
    final int scale = value.scale();
    final byte[] scaleBytes = IntUtils.toByteArray(scale, DEFAULT_BYTE_ORDER);
    final BigInteger unscaledValue = value.unscaledValue();
    final byte[] unscaledValueBytes = unscaledValue.toByteArray();
    final byte[] result = new byte[scaleBytes.length + unscaledValueBytes.length];
    arraycopy(scaleBytes, 0, result, 0, scaleBytes.length);
    arraycopy(unscaledValueBytes, 0, result, scaleBytes.length, unscaledValueBytes.length);
    return result;
  }

  public static byte[] toByteArray(@Nullable final BigDecimal value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value));
  }

  public static Class<?> toClass(@Nullable final BigDecimal value) {
    return (value == null ? null : BigDecimal.class);
  }

  public static Class<?> toClass(@Nullable final BigDecimal value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : BigDecimal.class);
  }

  public static BigInteger toBigInteger(@Nullable final BigDecimal value) {
    return (value == null ? null : value.toBigInteger());
  }

  public static BigInteger toBigInteger(@Nullable final BigDecimal value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : value.toBigInteger());
  }

  public static boolean equals(@Nullable final BigDecimal v1, final long v2) {
    return equals(v1, BigDecimal.valueOf(v2));
  }

  public static boolean equals(final long v1, @Nullable final BigDecimal v2) {
    return equals(BigDecimal.valueOf(v1), v2);
  }

  public static boolean equals(@Nullable final BigDecimal v1,
      @Nullable final BigDecimal v2) {
    if (v1 == null) {
      return (v2 == null);
    } else if (v2 == null) {
      return false;
    } else {
      return v1.stripTrailingZeros().equals(v2.stripTrailingZeros());
    }
  }

  public static BigDecimal limitPrecision(@Nullable final BigDecimal value,
      final int precision) {
    if (value == null) {
      return null;
    } else {
      return value.setScale(precision, RoundingMode.HALF_UP);
    }
  }

  public static BigDecimal normalizeMoney(final BigDecimal money) {
    return limitPrecision(money, MoneyCodec.DEFAULT_SCALE);
  }

  public static BigDecimal formatMoney(@Nullable final BigDecimal money) {
    return limitPrecision(defaultIfNull(money, ZERO), 2);
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, BigInteger.class,
          Float.class, Double.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code BigDecimal}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code BigDecimal}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }

}
