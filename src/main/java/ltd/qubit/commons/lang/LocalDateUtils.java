////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.time.LocalDate;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.IsoLocalDateCodec;
import ltd.qubit.commons.util.codec.LocalDateCodec;

/**
 * 此类提供对{@link LocalDate}对象的操作。
 * <p>
 * 此类尝试优雅地处理{@code null}输入。对于{@code null}输入不会抛出异常。
 * 每个方法都会详细记录其行为。
 * </p>
 * <p>
 * 此类还处理从{@link LocalDate}对象到常见类型的转换。
 * </p>
 *
 * @author 胡海星
 */
public class LocalDateUtils {

  /**
   * 将{@link LocalDate}值转换为字符串表示。
   * <p>
   * 使用ISO日期格式进行转换。
   * </p>
   *
   * @param value
   *     要转换的{@link LocalDate}值，可以为{@code null}。
   * @return
   *     转换后的字符串表示；如果输入为{@code null}，则返回{@code null}。
   */
  public static String toString(@Nullable final LocalDate value) {
    return toString(value, null, IsoLocalDateCodec.DEFAULT_ENCODE_PATTERN);
  }

  /**
   * 使用指定的模式将{@link LocalDate}值转换为字符串表示。
   *
   * @param value
   *     要转换的{@link LocalDate}值，可以为{@code null}。
   * @param pattern
   *     日期格式模式。
   * @return
   *     转换后的字符串表示；如果输入为{@code null}，则返回{@code null}。
   */
  public static String toString(@Nullable final LocalDate value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  /**
   * 使用指定的模式将{@link LocalDate}值转换为字符串表示，当值为{@code null}时返回默认值。
   *
   * @param value
   *     要转换的{@link LocalDate}值，可以为{@code null}。
   * @param defaultValue
   *     当{@code value}为{@code null}时返回的默认值，可以为{@code null}。
   * @param pattern
   *     日期格式模式。
   * @return
   *     转换后的字符串表示；如果输入为{@code null}，则返回{@code defaultValue}。
   */
  public static String toString(@Nullable final LocalDate value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final LocalDateCodec codec = new LocalDateCodec(pattern, true);
    return codec.encode(value);
  }

  /**
   * 获取{@link LocalDate}值对应的类型。
   *
   * @param value
   *     {@link LocalDate}值，可以为{@code null}。
   * @return
   *     如果{@code value}不为{@code null}，返回{@link LocalDate}.class；
   *     否则返回{@code null}。
   */
  public static Class<?> toClass(@Nullable final LocalDate value) {
    return (value == null ? null : LocalDate.class);
  }

  /**
   * 获取{@link LocalDate}值对应的类型，当值为{@code null}时返回默认类型。
   *
   * @param value
   *     {@link LocalDate}值，可以为{@code null}。
   * @param defaultValue
   *     当{@code value}为{@code null}时返回的默认类型，可以为{@code null}。
   * @return
   *     如果{@code value}不为{@code null}，返回{@link LocalDate}.class；
   *     否则返回{@code defaultValue}。
   */
  public static Class<?> toClass(@Nullable final LocalDate value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : LocalDate.class);
  }

  /**
   * 测试指定的类型的值是否可以和{@code LocalDate}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code LocalDate}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == LocalDate.class)
        || (type == java.sql.Date.class);
  }

  /**
   * 将指定的对象转换为{@link LocalDate}值。
   * <p>
   * 支持以下类型的转换：
   * </p>
   * <ul>
   * <li>{@link LocalDate} - 直接返回</li>
   * <li>{@link java.sql.Date} - 转换为{@link LocalDate}</li>
   * </ul>
   *
   * @param value
   *     要转换的对象，可以为{@code null}。
   * @return
   *     转换后的{@link LocalDate}值；如果输入为{@code null}，则返回{@code null}。
   * @throws IllegalArgumentException
   *     如果对象不是可转换为{@link LocalDate}的类型。
   */
  public static LocalDate toLocalDate(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    final Class<?> cls = value.getClass();
    if (cls == LocalDate.class) {
      return (LocalDate) value;
    } else if (cls == java.sql.Date.class) {
      return ((java.sql.Date) value).toLocalDate();
    } else {
      throw new IllegalArgumentException("The value is not a local date "
          + "representable value: " + value.getClass().getName());
    }
  }
}