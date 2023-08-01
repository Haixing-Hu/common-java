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
import java.nio.ByteOrder;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.InstantCodec;

import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;

/**
 * This class provides operations on {@link Instant} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link Instant} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 */
public class InstantUtils {

  public static boolean toBoolean(@Nullable final Instant value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.toEpochMilli() != 0));
  }

  public static boolean toBoolean(@Nullable final Instant value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.toEpochMilli() != 0));
  }

  public static Boolean toBooleanObject(@Nullable final Instant value) {
    return (value == null ? null : value.toEpochMilli() != 0);
  }

  public static Boolean toBooleanObject(@Nullable final Instant value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : Boolean.valueOf(value.toEpochMilli() != 0));
  }

  public static char toChar(@Nullable final Instant value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.toEpochMilli());
  }

  public static char toChar(@Nullable final Instant value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.toEpochMilli());
  }

  public static Character toCharObject(@Nullable final Instant value) {
    return (value == null ? null : (char) value.toEpochMilli());
  }

  public static Character toCharObject(@Nullable final Instant value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue
                          : Character.valueOf((char) value.toEpochMilli()));
  }

  public static byte toByte(@Nullable final Instant value) {
    return (value == null ? ByteUtils.DEFAULT : (byte) value.toEpochMilli());
  }

  public static byte toByte(@Nullable final Instant value,
      final byte defaultValue) {
    return (value == null ? defaultValue : (byte) value.toEpochMilli());
  }

  public static Byte toByteObject(@Nullable final Instant value) {
    return (value == null ? null : (byte) value.toEpochMilli());
  }

  public static Byte toByteObject(@Nullable final Instant value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue
                          : Byte.valueOf((byte) value.toEpochMilli()));
  }

  public static short toShort(@Nullable final Instant value) {
    return (value == null ? IntUtils.DEFAULT : (short) value.toEpochMilli());
  }

  public static short toShort(@Nullable final Instant value,
      final short defaultValue) {
    return (value == null ? defaultValue : (short) value.toEpochMilli());
  }

  public static Short toShortObject(@Nullable final Instant value) {
    return (value == null ? null : (short) value.toEpochMilli());
  }

  public static Short toShortObject(@Nullable final Instant value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue
                          : Short.valueOf((short) value.toEpochMilli()));
  }

  public static int toInt(@Nullable final Instant value) {
    return (value == null ? IntUtils.DEFAULT : (int) value.toEpochMilli());
  }

  public static int toInt(@Nullable final Instant value,
      final int defaultValue) {
    return (value == null ? defaultValue : (int) value.toEpochMilli());
  }

  public static Integer toIntObject(@Nullable final Instant value) {
    return (value == null ? null : (int) value.toEpochMilli());
  }

  public static Integer toIntObject(@Nullable final Instant value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue
                          : Integer.valueOf((int) value.toEpochMilli()));
  }

  public static long toLong(@Nullable final Instant value) {
    return (value == null ? LongUtils.DEFAULT : value.toEpochMilli());
  }

  public static long toLong(@Nullable final Instant value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.toEpochMilli());
  }

  public static Long toLongObject(@Nullable final Instant value) {
    return (value == null ? null : value.toEpochMilli());
  }

  public static Long toLongObject(@Nullable final Instant value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.toEpochMilli()));
  }

  public static float toFloat(@Nullable final Instant value) {
    return (value == null ? FloatUtils.DEFAULT : (float) value.toEpochMilli());
  }

  public static float toFloat(@Nullable final Instant value,
      final float defaultValue) {
    return (value == null ? defaultValue : (float) value.toEpochMilli());
  }

  public static Float toFloatObject(@Nullable final Instant value) {
    return (value == null ? null : (float) value.toEpochMilli());
  }

  public static Float toFloatObject(@Nullable final Instant value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.toEpochMilli()));
  }

  public static double toDouble(@Nullable final Instant value) {
    return (value == null ? DoubleUtils.DEFAULT : (double) value.toEpochMilli());
  }

  public static double toDouble(@Nullable final Instant value,
      final double defaultValue) {
    return (value == null ? defaultValue : (double) value.toEpochMilli());
  }

  public static Double toDoubleObject(@Nullable final Instant value) {
    return (value == null ? null : (double) value.toEpochMilli());
  }

  public static Double toDoubleObject(@Nullable final Instant value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.toEpochMilli()));
  }

  public static String toString(@Nullable final Instant value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  public static String toString(@Nullable final Instant value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final InstantCodec codec = new InstantCodec(pattern, true);
    return codec.encode(value);
  }

  public static byte[] toByteArray(@Nullable final Instant value) {
    return (value == null ? null
                          : LongUtils.toByteArray(value.toEpochMilli(), DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Instant value,
      final ByteOrder byteOrder) {
    return (value == null ? null : LongUtils.toByteArray(value.toEpochMilli(),
        byteOrder));
  }

  public static byte[] toByteArray(@Nullable final Instant value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : LongUtils.toByteArray(value.toEpochMilli(),
        DEFAULT_BYTE_ORDER));
  }

  public static byte[] toByteArray(@Nullable final Instant value,
      @Nullable final byte[] defaultValue, final ByteOrder byteOrder) {
    return (value == null ? defaultValue : LongUtils.toByteArray(value.toEpochMilli(),
        byteOrder));
  }

  public static Class<?> toClass(@Nullable final Instant value) {
    return (value == null ? null : Instant.class);
  }

  public static Class<?> toClass(@Nullable final Instant value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Instant.class);
  }

  public static BigInteger toBigInteger(@Nullable final Instant value) {
    return (value == null ? null : BigInteger.valueOf(value.toEpochMilli()));
  }

  public static BigInteger toBigInteger(@Nullable final Instant value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : BigInteger.valueOf(value.toEpochMilli()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Instant value) {
    return (value == null ? null : BigDecimal.valueOf(value.toEpochMilli()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Instant value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : BigDecimal.valueOf(value.toEpochMilli()));
  }

  /**
   * 测试指定的类型的值是否可以和{@code Instant}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code Instant}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == Instant.class)
        || (type == ZonedDateTime.class)
        || (type == java.sql.Timestamp.class)
        || (type == java.util.Date.class);
  }

  public static Instant toInstant(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    final Class<?> cls = value.getClass();
    if (cls == Instant.class) {
      return (Instant) value;
    } else if (cls == ZonedDateTime.class) {
      return ((ZonedDateTime) value).toInstant();
    } else if (cls == java.sql.Timestamp.class) {
      final long milliseconds = ((java.sql.Timestamp) value).getTime();
      return Instant.ofEpochMilli(milliseconds);
    } else if (cls == java.util.Date.class) {
      final long milliseconds = ((java.util.Date) value).getTime();
      return Instant.ofEpochMilli(milliseconds);
    } else {
      throw new IllegalArgumentException("The value is not an instant "
          + "representable value: " + value.getClass().getName());
    }
  }

  /**
   * 将指定的{@link Instant}截断到指定的精度。
   *
   * @param instant
   *     指定的{@link Instant}对象，可以为{@code null}。
   * @param unit
   *     指定的时间精度。
   * @return
   *     一个新的{@link Instant}对象，表示原对象被截断后的时间；或{@code null}若原对象是
   *     {@code null}。
   */
  public static Instant truncatedTo(@Nullable final Instant instant,
      final ChronoUnit unit) {
    if (instant == null) {
      return null;
    }
    return instant.truncatedTo(unit);
  }

  /**
   * 将指定的{@link Instant}截断到秒。
   *
   * @param instant
   *     指定的{@link Instant}对象，可以为{@code null}。
   * @return
   *     一个新的{@link Instant}对象，表示原对象被截断后的时间；或{@code null}若原对象是
   *     {@code null}。
   */
  public static Instant truncatedToSecond(@Nullable final Instant instant) {
    return truncatedTo(instant, ChronoUnit.SECONDS);
  }
}
