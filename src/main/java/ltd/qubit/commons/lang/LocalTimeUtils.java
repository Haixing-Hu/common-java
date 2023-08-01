////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.time.LocalTime;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.LocalTimeCodec;

/**
 * This class provides operations on {@link LocalTime} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link LocalTime} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 */
public class LocalTimeUtils {

  public static String toString(@Nullable final LocalTime value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  public static String toString(@Nullable final LocalTime value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final LocalTimeCodec codec = new LocalTimeCodec(pattern, true);
    return codec.encode(value);
  }

  public static Class<?> toClass(@Nullable final LocalTime value) {
    return (value == null ? null : LocalTime.class);
  }

  public static Class<?> toClass(@Nullable final LocalTime value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : LocalTime.class);
  }

  /**
   * 测试指定的类型的值是否可以和{@code LocalTime}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code LocalTime}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return (type == LocalTime.class)
        || (type == java.sql.Time.class);
  }

  public static LocalTime toLocalTime(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    final Class<?> cls = value.getClass();
    if (cls == LocalTime.class) {
      return (LocalTime) value;
    } else if (cls == java.sql.Time.class) {
      return ((java.sql.Time) value).toLocalTime();
    } else {
      throw new IllegalArgumentException("The value is not a local time "
          + "representable value: " + value.getClass().getName());
    }
  }
}
