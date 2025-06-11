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
 * 此类提供了操作 {@link BigInteger} 对象的方法。
 *
 * <p>此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都在其详细文档中记录了其行为。
 *
 * <p>此类还处理从 {@link BigInteger} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
public class BigIntegerUtils {

  private static final int DECIMAL_RADIX = 10;

  /**
   * 将 {@link BigInteger} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link BooleanUtils#DEFAULT}；如果 {@code value} 不为零，
   *     返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final BigInteger value) {
    return (value == null ? BooleanUtils.DEFAULT : (value.signum() != 0));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code boolean} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code boolean} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；如果 {@code value} 不为零，
   *     返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean toBoolean(@Nullable final BigInteger value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : (value.signum() != 0));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Boolean} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；如果 {@code value} 不为零，
   *     返回 {@code true}；否则返回 {@code false}。
   */
  public static Boolean toBooleanObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.signum() != 0);
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Boolean} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Boolean} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；如果 {@code value} 不为零，
   *     返回 {@code true}；否则返回 {@code false}。
   */
  public static Boolean toBooleanObject(@Nullable final BigInteger value,
      @Nullable final Boolean defaultValue) {
    return (value == null ? defaultValue
                          : Boolean.valueOf(value.signum() != 0));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link CharUtils#DEFAULT}；否则返回 {@code value} 的短整型值
   *     转换为字符。
   */
  public static char toChar(@Nullable final BigInteger value) {
    return (value == null ? CharUtils.DEFAULT : (char) value.shortValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的短整型值
   *     转换为字符。
   */
  public static char toChar(@Nullable final BigInteger value,
      final char defaultValue) {
    return (value == null ? defaultValue : (char) value.shortValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的短整型值转换为字符。
   */
  public static Character toCharObject(@Nullable final BigInteger value) {
    return (value == null ? null : (char) value.shortValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的短整型值转换为字符。
   */
  public static Character toCharObject(@Nullable final BigInteger value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue
                          : Character.valueOf((char) value.shortValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link ByteUtils#DEFAULT}；否则返回 {@code value} 的字节值。
   */
  public static byte toByte(@Nullable final BigInteger value) {
    return (value == null ? ByteUtils.DEFAULT : value.byteValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的字节值。
   */
  public static byte toByte(@Nullable final BigInteger value,
      final byte defaultValue) {
    return (value == null ? defaultValue : value.byteValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Byte} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的字节值。
   */
  public static Byte toByteObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.byteValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Byte} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的字节值。
   */
  public static Byte toByteObject(@Nullable final BigInteger value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : Byte.valueOf(value.byteValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link IntUtils#DEFAULT}；否则返回 {@code value} 的短整型值。
   */
  public static short toShort(@Nullable final BigInteger value) {
    return (value == null ? IntUtils.DEFAULT : value.shortValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的短整型值。
   */
  public static short toShort(@Nullable final BigInteger value,
      final short defaultValue) {
    return (value == null ? defaultValue : value.shortValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的短整型值。
   */
  public static Short toShortObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.shortValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的短整型值。
   */
  public static Short toShortObject(@Nullable final BigInteger value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : Short.valueOf(value.shortValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link IntUtils#DEFAULT}；否则返回 {@code value} 的整型值。
   */
  public static int toInt(@Nullable final BigInteger value) {
    return (value == null ? IntUtils.DEFAULT : value.intValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的整型值。
   */
  public static int toInt(@Nullable final BigInteger value,
      final int defaultValue) {
    return (value == null ? defaultValue : value.intValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的整型值。
   */
  public static Integer toIntObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.intValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的整型值。
   */
  public static Integer toIntObject(@Nullable final BigInteger value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : Integer.valueOf(value.intValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link LongUtils#DEFAULT}；否则返回 {@code value} 的长整型值。
   */
  public static long toLong(@Nullable final BigInteger value) {
    return (value == null ? LongUtils.DEFAULT : value.longValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的长整型值。
   */
  public static long toLong(@Nullable final BigInteger value,
      final long defaultValue) {
    return (value == null ? defaultValue : value.longValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的长整型值。
   */
  public static Long toLongObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.longValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的长整型值。
   */
  public static Long toLongObject(@Nullable final BigInteger value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : Long.valueOf(value.longValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link FloatUtils#DEFAULT}；否则返回 {@code value} 的浮点型值。
   */
  public static float toFloat(@Nullable final BigInteger value) {
    return (value == null ? FloatUtils.DEFAULT : value.floatValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的浮点型值。
   */
  public static float toFloat(@Nullable final BigInteger value,
      final float defaultValue) {
    return (value == null ? defaultValue : value.floatValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的浮点型值。
   */
  public static Float toFloatObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.floatValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的浮点型值。
   */
  public static Float toFloatObject(@Nullable final BigInteger value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : Float.valueOf(value.floatValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link DoubleUtils#DEFAULT}；否则返回 {@code value} 的双精度浮点型值。
   */
  public static double toDouble(@Nullable final BigInteger value) {
    return (value == null ? DoubleUtils.DEFAULT : value.doubleValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的双精度浮点型值。
   */
  public static double toDouble(@Nullable final BigInteger value,
      final double defaultValue) {
    return (value == null ? defaultValue : value.doubleValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的双精度浮点型值。
   */
  public static Double toDoubleObject(@Nullable final BigInteger value) {
    return (value == null ? null : value.doubleValue());
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的双精度浮点型值。
   */
  public static Double toDoubleObject(@Nullable final BigInteger value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue : Double.valueOf(value.doubleValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的字符串。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@code value} 的十进制字符串表示。
   */
  public static String toString(@Nullable final BigInteger value) {
    return (value == null ? null : value.toString(DECIMAL_RADIX));
  }

  /**
   * 将 {@link BigInteger} 值转换为字符串。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的字符串。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@code value} 的十进制字符串表示。
   */
  public static String toString(@Nullable final BigInteger value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : value.toString(DECIMAL_RADIX));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回以 {@code value} 的长整型值作为毫秒数的日期。
   */
  public static Date toDate(@Nullable final BigInteger value) {
    return (value == null ? null : new Date(value.longValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回以 {@code value} 的长整型值作为毫秒数的日期。
   */
  public static Date toDate(@Nullable final BigInteger value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : new Date(value.longValue()));
  }

  /**
   * 将 {@link BigInteger} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的字节数组。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；如果 {@code value} 为零，返回空数组；
   *     否则返回 {@code value} 的字节数组表示。
   */
  public static byte[] toByteArray(@Nullable final BigInteger value) {
    if (value == null) {
      return null;
    } else if (value.signum() == 0) {
      return EMPTY_BYTE_ARRAY;
    } else {
      return value.toByteArray();
    }
  }

  /**
   * 将 {@link BigInteger} 值转换为字节数组。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的字节数组。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；如果 {@code value} 为零，返回空数组；
   *     否则返回 {@code value} 的字节数组表示。
   */
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

  /**
   * 将 {@link BigInteger} 值转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link Class} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回 {@link BigInteger}.class。
   */
  public static Class<?> toClass(@Nullable final BigInteger value) {
    return (value == null ? null : BigInteger.class);
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link Class} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回 {@link BigInteger}.class。
   */
  public static Class<?> toClass(@Nullable final BigInteger value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : BigInteger.class);
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}；否则返回基于 {@code value} 的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final BigInteger value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将 {@link BigInteger} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@link BigInteger} 值。
   * @param defaultValue
   *     默认值。
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}；否则返回基于 {@code value} 的 {@link BigDecimal} 对象。
   */
  public static BigDecimal toBigDecimal(@Nullable final BigInteger value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : new BigDecimal(value));
  }

  private static final Set<ClassKey> COMPARABLE_TYPES =
      ImmutableSet.of(new ClassKey(boolean.class),
          new ClassKey(byte.class),
          new ClassKey(short.class),
          new ClassKey(int.class),
          new ClassKey(long.class),
          new ClassKey(float.class),
          new ClassKey(double.class),
          new ClassKey(Boolean.class),
          new ClassKey(Byte.class),
          new ClassKey(Short.class),
          new ClassKey(Integer.class),
          new ClassKey(Long.class),
          new ClassKey(BigInteger.class),
          new ClassKey(Float.class),
          new ClassKey(Double.class),
          new ClassKey(BigDecimal.class));

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
    return COMPARABLE_TYPES.contains(new ClassKey(type));
  }
}
