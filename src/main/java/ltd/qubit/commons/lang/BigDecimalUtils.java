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
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import ltd.qubit.commons.util.codec.MoneyCodec;

import static java.lang.System.arraycopy;
import static java.math.BigDecimal.ZERO;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_BYTE_ARRAY;
import static ltd.qubit.commons.lang.ByteArrayUtils.DEFAULT_BYTE_ORDER;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * 此类提供对{@link BigDecimal}对象的操作。
 *
 * <p>此类会尝试优雅地处理{@code null}输入。对于{@code null}输入不会抛出异常。
 * 每个方法都更详细地记录了其行为。
 *
 * <p>该类还处理从{@link BigDecimal}对象到常见类型的转换。
 *
 * @author 胡海星
 */
public class BigDecimalUtils {

  /**
   * 将{@link BigDecimal}对象转换为{@code boolean}值。
   *
   * <p>如果值为{@code null}，则返回{@code false}；否则，当且仅当该值不等于0时返回{@code true}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code boolean}值。
   */
  public static boolean toBoolean(@Nullable final BigDecimal value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.signum() != 0));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code boolean}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则，当且仅当该值不等于0时返回{@code true}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code boolean}值。
   */
  public static boolean toBoolean(@Nullable final BigDecimal value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.signum() != 0));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Boolean}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则，当且仅当该值不等于0时返回{@code true}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Boolean}对象，如果输入为{@code null}则为{@code null}。
   */
  public static Boolean toBooleanObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.signum() != 0);
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Boolean}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则，当且仅当该值不等于0时返回{@code true}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Boolean}对象。
   */
  public static Boolean toBooleanObject(@Nullable final BigDecimal value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : Boolean.valueOf(value.signum() != 0));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code char}值。
   *
   * <p>如果值为{@code null}，则返回0；否则返回其{@code shortValue()}的转型。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code char}值。
   */
  public static char toChar(@Nullable final BigDecimal value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.shortValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code char}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code shortValue()}的转型。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code char}值。
   */
  public static char toChar(@Nullable final BigDecimal value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.shortValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Character}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code shortValue()}的转型。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Character}对象。
   */
  public static Character toCharObject(@Nullable final BigDecimal value) {
    return (value == null ? null : (char) value.shortValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Character}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code shortValue()}的转型。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Character}对象。
   */
  public static Character toCharObject(@Nullable final BigDecimal value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue
                          : Character.valueOf((char) value.shortValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code byte}值。
   *
   * <p>如果值为{@code null}，则返回0；否则返回其{@code byteValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code byte}值。
   */
  public static byte toByte(@Nullable final BigDecimal value) {
    return (value == null ? ByteUtils.DEFAULT : value.byteValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code byte}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code byteValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code byte}值。
   */
  public static byte toByte(@Nullable final BigDecimal value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Byte}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code byteValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Byte}对象。
   */
  public static Byte toByteObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.byteValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Byte}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code byteValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Byte}对象。
   */
  public static Byte toByteObject(@Nullable final BigDecimal value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(value.byteValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code short}值。
   *
   * <p>如果值为{@code null}，则返回0；否则返回其{@code shortValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code short}值。
   */
  public static short toShort(@Nullable final BigDecimal value) {
    return (value == null ? IntUtils.DEFAULT : value.shortValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code short}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code shortValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code short}值。
   */
  public static short toShort(@Nullable final BigDecimal value,
      final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Short}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code shortValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Short}对象。
   */
  public static Short toShortObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.shortValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Short}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code shortValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Short}对象。
   */
  public static Short toShortObject(@Nullable final BigDecimal value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(value.shortValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code int}值。
   *
   * <p>如果值为{@code null}，则返回0；否则返回其{@code intValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code int}值。
   */
  public static int toInt(@Nullable final BigDecimal value) {
    return (value == null ? IntUtils.DEFAULT : value.intValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code int}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code intValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code int}值。
   */
  public static int toInt(@Nullable final BigDecimal value,
      final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Integer}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code intValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Integer}对象。
   */
  public static Integer toIntObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.intValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Integer}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code intValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Integer}对象。
   */
  public static Integer toIntObject(@Nullable final BigDecimal value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(value.intValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code long}值。
   *
   * <p>如果值为{@code null}，则返回0；否则返回其{@code longValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code long}值。
   */
  public static long toLong(@Nullable final BigDecimal value) {
    return (value == null ? LongUtils.DEFAULT : value.longValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code long}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code longValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code long}值。
   */
  public static long toLong(@Nullable final BigDecimal value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Long}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code longValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Long}对象。
   */
  public static Long toLongObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.longValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Long}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code longValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Long}对象。
   */
  public static Long toLongObject(@Nullable final BigDecimal value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.longValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code float}值。
   *
   * <p>如果值为{@code null}，则返回0.0f；否则返回其{@code floatValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code float}值。
   */
  public static float toFloat(@Nullable final BigDecimal value) {
    return (value == null ? FloatUtils.DEFAULT : value.floatValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code float}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code floatValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code float}值。
   */
  public static float toFloat(@Nullable final BigDecimal value,
      final float defaultValue) {
    return (value == null ? defaultValue : value.floatValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Float}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code floatValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Float}对象。
   */
  public static Float toFloatObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.floatValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Float}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code floatValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Float}对象。
   */
  public static Float toFloatObject(@Nullable final BigDecimal value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.floatValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code double}值。
   *
   * <p>如果值为{@code null}，则返回0.0；否则返回其{@code doubleValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code double}值。
   */
  public static double toDouble(@Nullable final BigDecimal value) {
    return (value == null ? DoubleUtils.DEFAULT : value.doubleValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code double}值。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code doubleValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code double}值。
   */
  public static double toDouble(@Nullable final BigDecimal value,
      final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Double}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code doubleValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Double}对象。
   */
  public static Double toDoubleObject(@Nullable final BigDecimal value) {
    return (value == null ? null : value.doubleValue());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Double}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code doubleValue()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Double}对象。
   */
  public static Double toDoubleObject(@Nullable final BigDecimal value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.doubleValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link String}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code toString()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link String}对象。
   */
  public static String toString(@Nullable final BigDecimal value) {
    return (value == null ? null : value.toString());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link String}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code toString()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link String}对象。
   */
  public static String toString(@Nullable final BigDecimal value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : value.toString());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Date}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则，使用其{@code longValue()}
   * 创建一个新的{@link Date}对象。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link Date}对象。
   */
  public static Date toDate(@Nullable final BigDecimal value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Date}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则，使用其{@code longValue()}
   * 创建一个新的{@link Date}对象。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link Date}对象。
   */
  public static Date toDate(@Nullable final BigDecimal value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@code byte}数组。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@code byte}数组。
   */
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

  /**
   * 将{@link BigDecimal}对象转换为{@code byte}数组。
   *
   * <p>如果值为{@code null}，则返回指定的默认值。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@code byte}数组。
   */
  public static byte[] toByteArray(@Nullable final BigDecimal value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value));
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Class}对象。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     如果值为{@code null}，则为{@code null}，否则为{@code BigDecimal.class}。
   */
  public static Class<?> toClass(@Nullable final BigDecimal value) {
    return (value == null ? null : BigDecimal.class);
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link Class}对象。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     如果值为{@code null}，则为默认值，否则为{@code BigDecimal.class}。
   */
  public static Class<?> toClass(@Nullable final BigDecimal value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : BigDecimal.class);
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link BigInteger}对象。
   *
   * <p>如果值为{@code null}，则返回{@code null}；否则返回其{@code toBigInteger()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @return
   *     转换后的{@link BigInteger}对象。
   */
  public static BigInteger toBigInteger(@Nullable final BigDecimal value) {
    return (value == null ? null : value.toBigInteger());
  }

  /**
   * 将{@link BigDecimal}对象转换为{@link BigInteger}对象。
   *
   * <p>如果值为{@code null}，则返回指定的默认值；否则返回其{@code toBigInteger()}。
   *
   * @param value
   *     要转换的{@link BigDecimal}对象。
   * @param defaultValue
   *     值为{@code null}时返回的默认值。
   * @return
   *     转换后的{@link BigInteger}对象。
   */
  public static BigInteger toBigInteger(@Nullable final BigDecimal value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : value.toBigInteger());
  }

  /**
   * 比较{@link BigDecimal}和{@code long}值是否相等。
   *
   * @param v1
   *     要比较的{@link BigDecimal}值。
   * @param v2
   *     要比较的{@code long}值。
   * @return
   *     如果它们相等，则为{@code true}；否则为{@code false}。
   */
  public static boolean equals(@Nullable final BigDecimal v1, final long v2) {
    return equals(v1, BigDecimal.valueOf(v2));
  }

  /**
   * 比较{@code long}和{@link BigDecimal}值是否相等。
   *
   * @param v1
   *     要比较的{@code long}值。
   * @param v2
   *     要比较的{@link BigDecimal}值。
   * @return
   *     如果它们相等，则为{@code true}；否则为{@code false}。
   */
  public static boolean equals(final long v1, @Nullable final BigDecimal v2) {
    return equals(BigDecimal.valueOf(v1), v2);
  }

  /**
   * 比较两个{@link BigDecimal}值是否相等。
   *
   * @param v1
   *     要比较的第一个{@link BigDecimal}值。
   * @param v2
   *     要比较的第二个{@link BigDecimal}值。
   * @return
   *     如果它们相等，则为{@code true}；否则为{@code false}。
   */
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

  /**
   * 将{@link BigDecimal}值的精度限制为指定的大小。
   *
   * @param value
   *     要限制精度的{@link BigDecimal}值。
   * @param precision
   *     精度。
   * @return
   *     限制精度后的{@link BigDecimal}值。
   */
  public static BigDecimal limitPrecision(@Nullable final BigDecimal value,
      final int precision) {
    if (value == null) {
      return null;
    } else {
      return value.setScale(precision, RoundingMode.HALF_UP);
    }
  }

  /**
   * 规范化金额。
   *
   * @param money
   *     要规范化的金额。
   * @return
   *     规范化后的金额。
   */
  public static BigDecimal normalizeMoney(final BigDecimal money) {
    return limitPrecision(money, MoneyCodec.DEFAULT_SCALE);
  }

  /**
   * 格式化金额。
   *
   * @param money
   *     要格式化的金额。
   * @return
   *     格式化后的金额。
   */
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

  /**
   * 将指定的浮点数值四舍五入为指定精度的字符串表示。
   *
   * @param value
   *     指定的浮点数值。
   * @param defaultValue
   *     如果{@code value}为{@code null}，则返回此默认值。
   * @param precision
   *     指定的精度，即小数点后保留的位数。
   * @return
   *     {@code value}四舍五入为指定精度的字符串表示，如果{@code value}为{@code null}
   *     则返回{@code defaultValue}。
   */
  @Nullable
  public static String roundToString(@Nullable final BigDecimal value,
      @Nullable final String defaultValue, final int precision) {
    if (value == null) {
      return defaultValue;
    } else {
      return value.setScale(precision, RoundingMode.HALF_UP).toPlainString();
    }
  }
}