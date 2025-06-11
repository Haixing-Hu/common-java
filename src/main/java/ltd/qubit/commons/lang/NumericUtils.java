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

import javax.annotation.Nullable;

/**
 * 此类提供数值转换相关的实用方法。
 * <p>
 * 该类主要用于将各种数值类型转换为{@link BigDecimal}类型，
 * 以便进行统一的数值计算和比较操作。
 * </p>
 *
 * @author 胡海星
 */
public class NumericUtils {

  /**
   * 将{@code boolean}值转换为{@link BigDecimal}。
   * <p>
   * 转换规则：{@code true}转换为{@code 1}，{@code false}转换为{@code 0}。
   * </p>
   *
   * @param value
   *     要转换的{@code boolean}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final boolean value) {
    return (value ? BigDecimal.ONE : BigDecimal.ZERO);
  }

  /**
   * 将{@code Boolean}对象转换为{@link BigDecimal}。
   * <p>
   * 转换规则：{@code true}转换为{@code 1}，{@code false}转换为{@code 0}，
   * {@code null}转换为{@code null}。
   * </p>
   *
   * @param value
   *     要转换的{@code Boolean}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Boolean value) {
    return (value == null ? null : (value ? BigDecimal.ONE : BigDecimal.ZERO));
  }

  /**
   * 将{@code byte}值转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code byte}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final byte value) {
    return new BigDecimal(value);
  }

  /**
   * 将{@code Byte}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code Byte}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Byte value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code short}值转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code short}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final short value) {
    return new BigDecimal(value);
  }

  /**
   * 将{@code Short}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code Short}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Short value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code int}值转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code int}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final int value) {
    return new BigDecimal(value);
  }

  /**
   * 将{@code Integer}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code Integer}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Integer value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code long}值转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code long}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final long value) {
    return new BigDecimal(value);
  }

  /**
   * 将{@code Long}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code Long}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Long value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code float}值转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code float}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final float value) {
    return new BigDecimal(value);
  }

  /**
   * 将{@code Float}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code Float}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Float value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code double}值转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code double}值。
   * @return
   *     转换后的{@link BigDecimal}值。
   */
  public static BigDecimal toNumeric(final double value) {
    return new BigDecimal(value);
  }

  /**
   * 将{@code Double}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code Double}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final Double value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code BigInteger}对象转换为{@link BigDecimal}。
   *
   * @param value
   *     要转换的{@code BigInteger}对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final BigInteger value) {
    return (value == null ? null : new BigDecimal(value));
  }

  /**
   * 将{@code BigDecimal}对象转换为{@link BigDecimal}。
   * <p>
   * 此方法直接返回输入值，主要为了保持API的一致性。
   * </p>
   *
   * @param value
   *     要转换的{@code BigDecimal}对象，可以为{@code null}。
   * @return
   *     输入的{@code BigDecimal}值本身。
   */
  @Nullable
  public static BigDecimal toNumeric(@Nullable final BigDecimal value) {
    return value;
  }

  /**
   * 将任意对象转换为{@link BigDecimal}。
   * <p>
   * 支持以下类型的转换：
   * </p>
   * <ul>
   * <li>{@code Boolean} - 转换为{@code 0}或{@code 1}</li>
   * <li>{@code Byte} - 转换为对应的数值</li>
   * <li>{@code Short} - 转换为对应的数值</li>
   * <li>{@code Integer} - 转换为对应的数值</li>
   * <li>{@code Long} - 转换为对应的数值</li>
   * <li>{@code Float} - 转换为对应的数值</li>
   * <li>{@code Double} - 转换为对应的数值</li>
   * <li>{@code BigInteger} - 转换为对应的数值</li>
   * <li>{@code BigDecimal} - 直接返回</li>
   * </ul>
   *
   * @param value
   *     要转换的对象，可以为{@code null}。
   * @return
   *     转换后的{@link BigDecimal}值；如果输入为{@code null}，则返回{@code null}。
   * @throws IllegalArgumentException
   *     如果对象不是可转换为数值的类型。
   */
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

  /**
   * 测试指定的类型是否可以与数值类型进行比较。
   * <p>
   * 支持的数值类型包括：
   * </p>
   * <ul>
   * <li>基本数值类型：{@code boolean}、{@code byte}、{@code short}、{@code int}、
   * {@code long}、{@code float}、{@code double}</li>
   * <li>包装类型：{@code Boolean}、{@code Byte}、{@code Short}、{@code Integer}、
   * {@code Long}、{@code Float}、{@code Double}</li>
   * <li>大数类型：{@code BigInteger}、{@code BigDecimal}</li>
   * </ul>
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型可以与数值类型进行比较，返回{@code true}；否则返回{@code false}。
   */
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