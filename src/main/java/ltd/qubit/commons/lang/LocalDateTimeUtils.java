////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

/**
 * 此类提供对{@link LocalDateTime}对象的操作。
 * <p>
 * 此类尝试优雅地处理{@code null}输入。对于{@code null}输入不会抛出异常。
 * 每个方法都会详细记录其行为。
 * </p>
 * <p>
 * 此类还处理从{@link LocalDateTime}对象到常见类型的转换。
 * </p>
 *
 * @author 胡海星
 */
public class LocalDateTimeUtils {

  /**
   * 使用指定的模式将{@link LocalDateTime}值转换为字符串表示。
   *
   * @param value
   *     要转换的{@link LocalDateTime}值，可以为{@code null}。
   * @param pattern
   *     日期时间格式模式。
   * @return
   *     转换后的字符串表示；如果输入为{@code null}，则返回{@code null}。
   */
  public static String toString(@Nullable final LocalDateTime value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  /**
   * 使用指定的模式将{@link LocalDateTime}值转换为字符串表示，当值为{@code null}时返回默认值。
   *
   * @param value
   *     要转换的{@link LocalDateTime}值，可以为{@code null}。
   * @param defaultValue
   *     当{@code value}为{@code null}时返回的默认值，可以为{@code null}。
   * @param pattern
   *     日期时间格式模式。
   * @return
   *     转换后的字符串表示；如果输入为{@code null}，则返回{@code defaultValue}。
   */
  public static String toString(@Nullable final LocalDateTime value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final LocalDateTimeCodec codec = new LocalDateTimeCodec(pattern, true);
    return codec.encode(value);
  }

  /**
   * 获取{@link LocalDateTime}值对应的类型。
   *
   * @param value
   *     {@link LocalDateTime}值，可以为{@code null}。
   * @return
   *     如果{@code value}不为{@code null}，返回{@link LocalDateTime}.class；
   *     否则返回{@code null}。
   */
  public static Class<?> toClass(@Nullable final LocalDateTime value) {
    return (value == null ? null : LocalDateTime.class);
  }

  /**
   * 获取{@link LocalDateTime}值对应的类型，当值为{@code null}时返回默认类型。
   *
   * @param value
   *     {@link LocalDateTime}值，可以为{@code null}。
   * @param defaultValue
   *     当{@code value}为{@code null}时返回的默认类型，可以为{@code null}。
   * @return
   *     如果{@code value}不为{@code null}，返回{@link LocalDateTime}.class；
   *     否则返回{@code defaultValue}。
   */
  public static Class<?> toClass(@Nullable final LocalDateTime value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : LocalDateTime.class);
  }

  /**
   * 测试指定的类型的值是否可以和{@code LocalDateTime}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code LocalDateTime}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == LocalDateTime.class);
  }

  /**
   * 将指定的对象转换为{@link LocalDateTime}值。
   * <p>
   * 支持以下类型的转换：
   * </p>
   * <ul>
   * <li>{@link LocalDateTime} - 直接返回</li>
   * </ul>
   *
   * @param value
   *     要转换的对象，可以为{@code null}。
   * @return
   *     转换后的{@link LocalDateTime}值；如果输入为{@code null}，则返回{@code null}。
   * @throws IllegalArgumentException
   *     如果对象不是可转换为{@link LocalDateTime}的类型。
   */
  public static LocalDateTime toLocalDateTime(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    final Class<?> cls = value.getClass();
    if (cls == LocalDateTime.class) {
      return (LocalDateTime) value;
    } else {
      throw new IllegalArgumentException("The value is not a local date time "
          + "representable value: " + value.getClass().getName());
    }
  }
}