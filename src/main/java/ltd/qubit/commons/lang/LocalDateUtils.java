////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.time.LocalDate;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.LocalDateCodec;

/**
 * This class provides operations on {@link LocalDate} objects.
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 * </p>
 * <p>
 * This class also handle the conversion from {@link LocalDate} objects to common
 * types.
 * </p>
 *
 * @author Haixing Hu
 */
public class LocalDateUtils {

  public static String toString(@Nullable final LocalDate value,
      final String pattern) {
    return toString(value, null, pattern);
  }

  public static String toString(@Nullable final LocalDate value,
      @Nullable final String defaultValue, final String pattern) {
    if (value == null) {
      return defaultValue;
    }
    final LocalDateCodec codec = new LocalDateCodec(pattern, true);
    return codec.encode(value);
  }

  public static Class<?> toClass(@Nullable final LocalDate value) {
    return (value == null ? null : LocalDate.class);
  }

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
