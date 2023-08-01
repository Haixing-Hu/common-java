////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

import java.time.LocalDateTime;
import javax.annotation.Nullable;

/**
 * This class provides operations on {@link LocalDateTime} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link LocalDateTime} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 */
public class LocalDateTimeUtils {

  public static String toString(@Nullable final LocalDateTime value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  public static String toString(@Nullable final LocalDateTime value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final LocalDateTimeCodec codec = new LocalDateTimeCodec(pattern, true);
    return codec.encode(value);
  }

  public static Class<?> toClass(@Nullable final LocalDateTime value) {
    return (value == null ? null : LocalDateTime.class);
  }

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
