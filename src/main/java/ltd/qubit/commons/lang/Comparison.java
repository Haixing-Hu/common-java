////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;

/**
 * 该类提供比较对象的函数。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class Comparison {

  /**
   * 比较两个 {@code boolean} 值的大小顺序。
   *
   * <p>我们假设对于 {@code boolean} 值，{@code true} &gt; {@code false}。
   *
   * @param value1
   *     第一个 {@code boolean} 值。
   * @param value2
   *     第二个 {@code boolean} 值。
   * @return 如果第一个值在字典序上小于、等于或大于第二个值，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final boolean value1, final boolean value2) {
    return Boolean.compare(value1, value2);
  }

  /**
   * 按字典序比较两个 {@code boolean} 数组。
   *
   * <p>我们假设对于 {@code boolean} 值，{@code true} &gt; {@code false}。
   *
   * @param array1
   *     第一个 {@code boolean} 数组，可以为 null。
   * @param array2
   *     第二个 {@code boolean} 数组，可以为 null。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final boolean[] array1,
      @Nullable final boolean[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * 按字典序比较两个 {@code boolean} 数组。
   *
   * <p>我们假设对于 {@code boolean} 值，{@code true} &gt; {@code false}。
   *
   * @param array1
   *     第一个 {@code boolean} 数组。
   * @param n1
   *     第一个 {@code boolean} 数组的长度。
   * @param array2
   *     第二个 {@code boolean} 数组。
   * @param n2
   *     第二个 {@code boolean} 数组的长度。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final boolean[] array1, final int n1,
      final boolean[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final boolean x = array1[i];
      final boolean y = array2[i];
      if (x != y) {
        return (x ? +1 : -1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * 比较两个 {@link Boolean} 对象的大小顺序。
   *
   * <p>我们假设对于 {@link Boolean} 对象，{@link Boolean#TRUE} &gt; {@link Boolean#FALSE} &gt; {@code null}。
   *
   * @param value1
   *     第一个 {@link Boolean} 对象，可以为 null。
   * @param value2
   *     第二个 {@link Boolean} 对象，可以为 null。
   * @return 如果第一个值在字典序上小于、等于或大于第二个值，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final Boolean value1,
      @Nullable final Boolean value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return Boolean.compare(value1, value2);
    }
  }

  /**
   * 按字典序比较两个 {@link Boolean} 数组。
   *
   * <p>我们假设对于 {@link Boolean} 对象，{@link Boolean#TRUE} &gt; {@link Boolean#FALSE} &gt; {@code null}。
   *
   * @param array1
   *     第一个 {@link Boolean} 数组，可以为 null。
   * @param array2
   *     第二个 {@link Boolean} 数组，可以为 null。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final Boolean[] array1,
      @Nullable final Boolean[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * 按字典序比较两个 {@link Boolean} 数组。
   *
   * <p>我们假设对于 {@link Boolean} 对象，{@link Boolean#TRUE} &gt; {@link Boolean#FALSE} &gt; {@code null}。
   *
   * @param array1
   *     第一个 {@link Boolean} 数组。
   * @param n1
   *     第一个 {@link Boolean} 数组的长度。
   * @param array2
   *     第二个 {@link Boolean} 数组。
   * @param n2
   *     第二个 {@link Boolean} 数组的长度。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final Boolean[] array1, final int n1,
      final Boolean[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * 比较两个 {@code char} 值的大小顺序。
   *
   * <p>该函数比较两个 {@code char} 值的大小顺序。
   *
   * @param value1
   *     第一个 {@code char} 值。
   * @param value2
   *     第二个 {@code char} 值。
   * @return 如果第一个值在字典序上小于、等于或大于第二个值，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final char value1, final char value2) {
    return Integer.compare(value1, value2);
  }

  /**
   * 按字典序比较两个 {@code char} 数组。
   *
   * <p>该函数比较两个 {@code char} 值的大小顺序。
   *
   * @param array1
   *     第一个 {@code char} 数组，可以为 null。
   * @param array2
   *     第二个 {@code char} 数组，可以为 null。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * 按字典序比较两个 {@code char} 数组。
   *
   * <p>该函数比较两个 {@code char} 值的大小顺序。
   *
   * @param array1
   *     第一个 {@code char} 数组。
   * @param n1
   *     第一个 {@code char} 数组的长度。
   * @param array2
   *     第二个 {@code char} 数组。
   * @param n2
   *     第二个 {@code char} 数组的长度。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final char[] array1, final int n1,
      final char[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final char x = array1[i];
      final char y = array2[i];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * 比较两个 {@link Character} 对象的大小顺序。
   *
   * <p>我们假设对于 {@code null} 是 {@link Character} 对象的最小值。
   *
   * @param value1
   *     第一个 {@link Character} 对象，可以为 null。
   * @param value2
   *     第二个 {@link Character} 对象，可以为 null。
   * @return 如果第一个值在字典序上小于、等于或大于第二个值，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final Character value1,
      @Nullable final Character value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final char v1 = value1;
      final char v2 = value2;
      return Integer.compare(v1, v2);
    }
  }

  /**
   * 按字典序比较两个 {@link Character} 数组。
   *
   * <p>我们假设对于 {@code null} 是 {@link Character} 对象的最小值。
   *
   * @param array1
   *     第一个 {@link Character} 数组，可以为 null。
   * @param array2
   *     第二个 {@link Character} 数组，可以为 null。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final Character[] array1,
      @Nullable final Character[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * 按字典序比较两个 {@link Character} 数组。
   *
   * <p>我们假设对于 {@code null} 是 {@link Character} 对象的最小值。
   *
   * @param array1
   *     第一个 {@link Character} 数组。
   * @param n1
   *     第一个 {@link Character} 数组的长度。
   * @param array2
   *     第二个 {@link Character} 数组。
   * @param n2
   *     第二个 {@link Character} 数组的长度。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final Character[] array1, final int n1,
      final Character[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * 比较两个 {@code byte} 值的大小顺序。
   *
   * @param value1
   *     第一个 {@code byte} 值。
   * @param value2
   *     第二个 {@code byte} 值。
   * @return 如果第一个值在字典序上小于、等于或大于第二个值，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(final byte value1, final byte value2) {
    return Integer.compare(value1, value2);
  }

  /**
   * 按字典序比较两个 {@code byte} 数组。
   *
   * @param array1
   *     第一个 {@code byte} 数组，可以为 null。
   * @param array2
   *     第二个 {@code byte} 数组，可以为 null。
   * @return 如果第一个数组在字典序上小于、等于或大于第二个数组，则分别返回小于、等于或大于 0 的整数。
   */
  public static int compare(@Nullable final byte[] array1,
      @Nullable final byte[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code byte} arrays lexically.
   *
   * @param array1
   *     the first {@code byte} array.
   * @param n1
   *     the length of the first {@code byte} array.
   * @param array2
   *     the second {@code byte} array.
   * @param n2
   *     the length of the second {@code byte} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final byte[] array1, final int n1,
      final byte[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final byte x = array1[i];
      final byte y = array2[i];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Byte} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Byte}
   * objects.
   *
   * @param value1
   *     the first {@link Byte} object, which could be null.
   * @param value2
   *     the second {@link Byte} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Byte value1,
      @Nullable final Byte value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final byte x = value1;
      final byte y = value2;
      return Integer.compare(x, y);
    }
  }

  /**
   * Compares two {@link Byte} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Byte}
   * objects.
   *
   * @param array1
   *     the first {@link Byte} array, which could be null.
   * @param array2
   *     the second {@link Byte} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Byte[] array1,
      @Nullable final Byte[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Byte} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Byte}
   * objects.
   *
   * @param array1
   *     the first {@link Byte} array.
   * @param n1
   *     the length of the first {@link Byte} array.
   * @param array2
   *     the second {@link Byte} array.
   * @param n2
   *     the length of the second {@link Byte} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Byte[] array1, final int n1,
      final Byte[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code short} value lexically.
   *
   * @param value1
   *     the first {@code short} value.
   * @param value2
   *     the second {@code short} value.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(final short value1, final short value2) {
    return Integer.compare(value1, value2);
  }

  /**
   * Compares two {@code short} arrays lexically.
   *
   * @param array1
   *     the first {@code short} array, which could be null.
   * @param array2
   *     the second {@code short} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final short[] array1,
      @Nullable final short[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code short} arrays lexically.
   *
   * @param array1
   *     the first {@code short} array.
   * @param n1
   *     the length of the first {@code short} array.
   * @param array2
   *     the second {@code short} array.
   * @param n2
   *     the length of the second {@code short} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final short[] array1, final int n1,
      final short[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final short x = array1[i];
      final short y = array2[i];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Short} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Short} objects.
   *
   * @param value1
   *     the first {@link Short} object, which could be null.
   * @param value2
   *     the second {@link Short} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Short value1,
      @Nullable final Short value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final short x = value1;
      final short y = value2;
      return Integer.compare(x, y);
    }
  }

  /**
   * Compares two {@link Short} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Short} objects.
   *
   * @param array1
   *     the first {@link Short} array, which could be null.
   * @param array2
   *     the second {@link Short} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Short[] array1,
      @Nullable final Short[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Short} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Short} objects.
   *
   * @param array1
   *     the first {@link Short} array.
   * @param n1
   *     the length of the first {@link Short} array.
   * @param array2
   *     the second {@link Short} array.
   * @param n2
   *     the length of the second {@link Short} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Short[] array1, final int n1,
      final Short[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code int} value lexically.
   *
   * @param value1
   *     the first {@code int} value.
   * @param value2
   *     the second {@code int} value.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(final int value1, final int value2) {
    return Integer.compare(value1, value2);
  }

  /**
   * Compares two {@code int} arrays lexically.
   *
   * @param array1
   *     the first {@code int} array, which could be null.
   * @param array2
   *     the second {@code int} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final int[] array1,
      @Nullable final int[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code int} arrays lexically.
   *
   * @param array1
   *     the first {@code int} array.
   * @param n1
   *     the length of the first {@code int} array.
   * @param array2
   *     the second {@code int} array.
   * @param n2
   *     the length of the second {@code int} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final int[] array1, final int n1,
      final int[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int x = array1[i];
      final int y = array2[i];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Integer} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Integer} objects.
   *
   * @param value1
   *     the first {@link Integer} object, which could be null.
   * @param value2
   *     the second {@link Integer} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Integer value1,
      @Nullable final Integer value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final int v1 = value1;
      final int v2 = value2;
      return Integer.compare(v1, v2);
    }
  }

  /**
   * Compares two {@link Integer} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Integer} objects.
   *
   * @param array1
   *     the first {@link Integer} array, which could be null.
   * @param array2
   *     the second {@link Integer} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Integer[] array1,
      @Nullable final Integer[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Integer} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Integer} objects.
   *
   * @param array1
   *     the first {@link Integer} array.
   * @param n1
   *     the length of the first {@link Integer} array.
   * @param array2
   *     the second {@link Integer} array.
   * @param n2
   *     the length of the second {@link Integer} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Integer[] array1, final int n1,
      final Integer[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code long} value lexically.
   *
   * @param value1
   *     the first {@code long} value.
   * @param value2
   *     the second {@code long} value.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(final long value1, final long value2) {
    return Long.compare(value1, value2);
  }

  /**
   * Compares two {@code long} arrays lexically.
   *
   * @param array1
   *     the first {@code long} array, which could be null.
   * @param array2
   *     the second {@code long} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final long[] array1,
      @Nullable final long[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code long} arrays lexically.
   *
   * @param array1
   *     the first {@code long} array.
   * @param n1
   *     the length of the first {@code long} array.
   * @param array2
   *     the second {@code long} array.
   * @param n2
   *     the length of the second {@code long} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final long[] array1, final int n1,
      final long[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final long x = array1[i];
      final long y = array2[i];
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Long} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Long}
   * objects.
   *
   * @param value1
   *     the first {@link Long} object, which could be null.
   * @param value2
   *     the second {@link Long} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Long value1,
      @Nullable final Long value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final long v1 = value1;
      final long v2 = value2;
      return Long.compare(v1, v2);
    }
  }

  /**
   * Compares two {@link Long} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Long}
   * objects.
   *
   * @param array1
   *     the first {@link Long} array, which could be null.
   * @param array2
   *     the second {@link Long} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Long[] array1,
      @Nullable final Long[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Long} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Long}
   * objects.
   *
   * @param array1
   *     the first {@link Long} array.
   * @param n1
   *     the length of the first {@link Long} array.
   * @param array2
   *     the second {@link Long} array.
   * @param n2
   *     the length of the second {@link Long} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Long[] array1, final int n1,
      final Long[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code float} values for order.
   *
   * <p>This method is more comprehensive than the standard Java greater than,
   * less
   * than and equals operators.
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.
   * <li>It returns {@code +1} if the first value is greater than the
   * second.
   * <li>It returns {@code 0} if the values are equal.
   * </ul>
   * The ordering is as follows, largest to smallest:
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum float
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum float (-{@link Float#MAX_VALUE})
   * <li>Negative infinity
   * </ul>
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}.
   *
   * @param value1
   *     the first {@code float} value.
   * @param value2
   *     the second {@code float} value.
   * @return An integer less than, equal to or greater than 0, if the first
   *     first value compares lexicographically less than, equal to, or greater
   *     than the second value.
   */
  public static int compare(final float value1, final float value2) {
    return Float.compare(value1, value2);
  }

  /**
   * Compares two {@code float} arrays lexically.
   *
   * @param array1
   *     the first {@code float} array, which could be null.
   * @param array2
   *     the second {@code float} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final float[] array1,
      @Nullable final float[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code float} arrays lexically.
   *
   * @param array1
   *     the first {@code float} array.
   * @param n1
   *     the length of the first {@code float} array.
   * @param array2
   *     the second {@code float} array.
   * @param n2
   *     the length of the second {@code float} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final float[] array1, final int n1,
      final float[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Float} values for order.
   *
   * <p>This method is more comprehensive than the standard Java greater than,
   * less
   * than and equals operators.
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.
   * <li>It returns {@code +1} if the first value is greater than the
   * second.
   * <li>It returns {@code 0} if the values are equal.
   * </ul>
   * The ordering is as follows, largest to smallest:
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum float
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum float (-{@link Float#MAX_VALUE})
   * <li>Negative infinityfloat
   * </ul>
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}.
   *
   * @param value1
   *     the first {@link Float} value, which could be null.
   * @param value2
   *     the second {@link Float} value, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Float value1,
      @Nullable final Float value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return Float.compare(value1, value2);
    }
  }

  /**
   * Compares two {@link Float} arrays lexically.
   *
   * @param array1
   *     the first {@link Float} array, which could be null.
   * @param array2
   *     the second {@link Float} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Float[] array1,
      @Nullable final Float[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Float} arrays lexically.
   *
   * @param array1
   *     the first {@link Float} array.
   * @param n1
   *     the length of the first {@link Float} array.
   * @param array2
   *     the second {@link Float} array.
   * @param n2
   *     the length of the second {@link Float} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Float[] array1, final int n1,
      final Float[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code float} values for order, with regard to an epsilon.
   *
   * <p>If the distance between two {@code float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *     the first {@code float} value.
   * @param value2
   *     the second {@code float} value.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(final float value1, final float value2,
      final float epsilon) {
    final float diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code float} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@code float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@code float} array, which could be null.
   * @param array2
   *     the second {@code float} array, which could be null.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final float[] array1,
      @Nullable final float[] array2, final float epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code float} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@code float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@code float} array.
   * @param n1
   *     the length of the first {@code float} array.
   * @param array2
   *     the second {@code float} array.
   * @param n2
   *     the length of the second {@code float} array.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final float[] array1, final int n1,
      final float[] array2, final int n2, final float epsilon) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final float diff = array1[i] - array2[i];
      if (Math.abs(diff) > epsilon) {
        return (diff < 0 ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Float} values for order, with regard to an epsilon.
   *
   * <p>If the distance between two {@link Float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *     the first {@link Float} value, which could be null.
   * @param value2
   *     the second {@link Float} value, which could be null.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Float value1,
      @Nullable final Float value2, final float epsilon) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    final float diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@link Float} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@link Float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@link Float} array, which could be null.
   * @param array2
   *     the second {@link Float} array, which could be null.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Float[] array1,
      @Nullable final Float[] array2, final float epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@link Float} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@link Float} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@link Float} array.
   * @param n1
   *     the length of the first {@link Float} array.
   * @param array2
   *     the second {@link Float} array.
   * @param n2
   *     the length of the second {@link Float} array.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Float[] array1, final int n1,
      final Float[] array2, final int n2, final float epsilon) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i], epsilon);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code double} for order.
   *
   * <p>This method is more comprehensive than the standard Java greater than,
   * less
   * than and equals operators.
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.</li>
   * <li>It returns {@code +1} if the first value is greater than the
   * second.</li>
   * <li>It returns {@code 0} if the values are equal.</li>
   * </ul>
   * The ordering is as follows, largest to smallest:
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum double
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum double (-{@link Double#MAX_VALUE})
   * <li>Negative infinity
   * </ul>
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}.
   *
   * @param value1
   *     the first {@code double} value.
   * @param value2
   *     the second {@code double} value.
   * @return An integer less than, equal to or greater than 0, if the first
   *     first value compares lexicographically less than, equal to, or greater
   *     than the second value.
   */
  public static int compare(final double value1, final double value2) {
    return Double.compare(value1, value2);
  }

  /**
   * Compares two {@code double} arrays lexically.
   *
   * @param array1
   *     the first {@code double} array, which could be null.
   * @param array2
   *     the second {@code double} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final double[] array1,
      @Nullable final double[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code double} arrays lexically.
   *
   * @param array1
   *     the first {@code double} array.
   * @param n1
   *     the length of the first {@code double} array.
   * @param array2
   *     the second {@code double} array.
   * @param n2
   *     the length of the second {@code double} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final double[] array1, final int n1,
      final double[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Double} for order.
   *
   * <p>This method is more comprehensive than the standard Java greater than,
   * less
   * than and equals operators.
   * <ul>
   * <li>It returns {@code -1} if the first value is less than the second.</li>
   * <li>It returns {@code +1} if the first value is greater than the
   * second.</li>
   * <li>It returns {@code 0} if the values are equal.</li>
   * </ul>
   * The ordering is as follows, largest to smallest:
   * <ul>
   * <li>NaN
   * <li>Positive infinity
   * <li>Maximum double
   * <li>Normal positive numbers
   * <li>+0.0
   * <li>-0.0
   * <li>Normal negative numbers
   * <li>Minimum double (-{@link Double#MAX_VALUE})
   * <li>Negative infinity
   * </ul>
   * Comparing {@code NaN} with {@code NaN} will return {@code 0}.
   *
   * @param value1
   *     the first {@link Double} value, which could be null.
   * @param value2
   *     the second {@link Double} value, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     first value compares lexicographically less than, equal to, or greater
   *     than the second value.
   */
  public static int compare(@Nullable final Double value1,
      @Nullable final Double value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return Double.compare(value1, value2);
    }
  }

  /**
   * Compares two {@link Double} arrays lexically.
   *
   * @param array1
   *     the first {@link Double} array, which could be null.
   * @param array2
   *     the second {@link Double} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Double[] array1,
      @Nullable final Double[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Double} arrays lexically.
   *
   * @param array1
   *     the first {@link Double} array.
   * @param n1
   *     the length of the first {@link Double} array.
   * @param array2
   *     the second {@link Double} array.
   * @param n2
   *     the length of the first {@link Double} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Double[] array1, final int n1,
      final Double[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code double} values for order, with regard to an epsilon.
   *
   * <p>If the distance between two {@code double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *     the first {@code double} value.
   * @param value2
   *     the second {@code double} value.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(final double value1, final double value2,
      final double epsilon) {
    final double diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@code double} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@code double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@code double} array, which could be null.
   * @param array2
   *     the second {@code double} array, which could be null.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final double[] array1,
      @Nullable final double[] array2, final double epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@code double} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@code double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@code double} array.
   * @param n1
   *     the length of the first {@code double} array.
   * @param array2
   *     the second {@code double} array.
   * @param n2
   *     the length of the second {@code double} array.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final double[] array1, final int n1,
      final double[] array2, final int n2, final double epsilon) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final double diff = array1[i] - array2[i];
      if (Math.abs(diff) > epsilon) {
        return (diff < 0 ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Double} values for order, with regard to an epsilon.
   *
   * <p>If the distance between two {@link Double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param value1
   *     the first {@link Double} value, which could be null.
   * @param value2
   *     the second {@link Double} value, which could be null.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Double value1,
      @Nullable final Double value2, final double epsilon) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    final double diff = value1 - value2;
    if (Math.abs(diff) <= epsilon) {
      return 0;
    } else {
      return (diff > 0 ? +1 : -1);
    }
  }

  /**
   * Compares two {@link Double} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@link Double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@link Double} array, which could be null.
   * @param array2
   *     the second {@link Double} array, which could be null.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Double[] array1,
      @Nullable final Double[] array2, final double epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@link Double} arrays lexically, with regard to an epsilon.
   *
   * <p>If the distance between two {@link Double} values less than or equals
   * to the epsilon, they are equal.
   *
   * @param array1
   *     the first {@link Double} array.
   * @param n1
   *     the length of the first {@link Double} array.
   * @param array2
   *     the second {@link Double} array.
   * @param n2
   *     the length of the second {@link Double} array.
   * @param epsilon
   *     the epsilon.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Double[] array1, final int n1,
      final Double[] array2, final int n2, final double epsilon) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i], epsilon);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Enum} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Enum}
   * objects.
   *
   * @param <E>
   *     The type of a {@link Enum} class.
   * @param value1
   *     the first {@link Enum} object, which could be null.
   * @param value2
   *     the second {@link Enum} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static <E extends Enum<E>> int compare(@Nullable final E value1,
      @Nullable final E value2) {
    if (value1 == value2) {
      return 0;
    } else if (value1 == null) {
      return -1;
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.ordinal() - value2.ordinal();
    }
  }

  /**
   * Compares two {@link Enum} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Enum}
   * objects.
   *
   * @param <E>
   *     the type of an {@link Enum} class.
   * @param array1
   *     the first {@link Enum} array, which could be null.
   * @param array2
   *     the second {@link Enum} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static <E extends Enum<E>> int compare(@Nullable final E[] array1,
      @Nullable final E[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Enum} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Enum}
   * objects.
   *
   * @param <E>
   *     the type of an {@link Enum} class.
   * @param array1
   *     the first {@link Enum} array.
   * @param n1
   *     the length of the first {@link Enum} array.
   * @param array2
   *     the second {@link Enum} array.
   * @param n2
   *     the length of the second {@link Enum} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static <E extends Enum<E>> int compare(final E[] array1, final int n1,
      final E[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final E x = array1[i];
      final E y = array2[i];
      if (x != y) {
        if (x == null) {
          return -1;
        } else if (y == null) {
          return +1;
        } else if (x.ordinal() != y.ordinal()) {
          return (x.ordinal() < y.ordinal() ? -1 : +1);
        }
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link String} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link String} objects.
   *
   * @param value1
   *     the first {@link String} object, which could be null.
   * @param value2
   *     the second {@link String} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final String value1,
      @Nullable final String value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return Integer.compare(value1.compareTo(value2), 0);
    }
  }

  /**
   * Compares two {@link String} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link String} objects.
   *
   * @param array1
   *     the first {@link String} array, which could be null.
   * @param array2
   *     the second {@link String} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final String[] array1,
      @Nullable final String[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link String} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link String} objects.
   *
   * @param array1
   *     the first {@link String} array.
   * @param n1
   *     the length of the first {@link String} array.
   * @param array2
   *     the second {@link String} array.
   * @param n2
   *     the length of the second {@link String} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final String[] array1, final int n1,
      final String[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return (result < 0 ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Class} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Class} objects.
   *
   * @param value1
   *     the first {@link Class} object, which could be null.
   * @param value2
   *     the second {@link Class} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Class<?> value1,
      @Nullable final Class<?> value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.getName().compareTo(value2.getName());
    }
  }

  /**
   * Compares two {@link Class} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Class} objects.
   *
   * @param array1
   *     the first {@link Class} array, which could be null.
   * @param array2
   *     the second {@link Class} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Class<?>[] array1,
      @Nullable final Class<?>[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Class} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Class} objects.
   *
   * @param array1
   *     the first {@link Class} array.
   * @param n1
   *     the length of the first {@link Class} array.
   * @param array2
   *     the second {@link Class} array.
   * @param n2
   *     the length of the second {@link Class} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Class<?>[] array1, final int n1,
      final Class<?>[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Type} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Type}
   * objects.
   *
   * @param value1
   *     the first {@link Type} object, which could be null.
   * @param value2
   *     the second {@link Type} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Type value1,
      @Nullable final Type value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.getTypeName().compareTo(value2.getTypeName());
    }
  }

  /**
   * Compares two {@link Type} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Type}
   * objects.
   *
   * @param array1
   *     the first {@link Type} array, which could be null.
   * @param array2
   *     the second {@link Type} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Type[] array1,
      @Nullable final Type[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Type} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Type}
   * objects.
   *
   * @param array1
   *     the first {@link Type} array.
   * @param n1
   *     the length of the first {@link Type} array.
   * @param array2
   *     the second {@link Type} array.
   * @param n2
   *     the length of the second {@link Type} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Type[] array1, final int n1,
      final Type[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link BigInteger} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link BigInteger} objects.
   *
   * @param value1
   *     the first {@link BigInteger} object, which could be null.
   * @param value2
   *     the second {@link BigInteger} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final BigInteger value1,
      @Nullable final BigInteger value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@link BigInteger} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link BigInteger} objects.
   *
   * @param array1
   *     the first {@link BigInteger} array, which could be null.
   * @param array2
   *     the second {@link BigInteger} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final BigInteger[] array1,
      @Nullable final BigInteger[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link BigInteger} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link BigInteger} objects.
   *
   * @param array1
   *     the first {@link BigInteger} array.
   * @param n1
   *     the length of the first {@link BigInteger} array.
   * @param array2
   *     the second {@link BigInteger} array.
   * @param n2
   *     the length of the second {@link BigInteger} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final BigInteger[] array1, final int n1,
      final BigInteger[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link BigDecimal} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link BigDecimal} objects.
   *
   * @param value1
   *     the first {@link BigDecimal} object.
   * @param value2
   *     the second {@link BigDecimal} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final BigDecimal value1,
      @Nullable final BigDecimal value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@link BigDecimal} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link BigDecimal} objects.
   *
   * @param array1
   *     the first {@link BigDecimal} array, which could be null.
   * @param array2
   *     the second {@link BigDecimal} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final BigDecimal[] array1,
      @Nullable final BigDecimal[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link BigDecimal} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link BigDecimal} objects.
   *
   * @param array1
   *     the first {@link BigDecimal} array.
   * @param n1
   *     the length of the first {@link BigDecimal} array.
   * @param array2
   *     the second {@link BigDecimal} array.
   * @param n2
   *     the length of the second {@link BigDecimal} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final BigDecimal[] array1, final int n1,
      final BigDecimal[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Date} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Date}
   * objects.
   *
   * @param value1
   *     the first {@link Date} object.
   * @param value2
   *     the second {@link Date} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Date value1,
      @Nullable final Date value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return value1.compareTo(value2);
    }
  }

  /**
   * Compares two {@link Date} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Date}
   * objects.
   *
   * @param array1
   *     the first {@link Date} array, which could be null.
   * @param array2
   *     the second {@link Date} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Date[] array1,
      @Nullable final Date[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Date} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Date}
   * objects.
   *
   * @param array1
   *     the first {@link Date} array.
   * @param n1
   *     the length of the first {@link Date} array.
   * @param array2
   *     the second {@link Date} array.
   * @param n2
   *     the length of the second {@link Date} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Date[] array1, final int n1,
      final Date[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Instant} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Instant}
   * objects.
   *
   * @param value1
   *     the first {@link Instant} object.
   * @param value2
   *     the second {@link Instant} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final Instant value1,
      @Nullable final Instant value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link Instant} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Instant}
   * objects.
   *
   * @param array1
   *     the first {@link Instant} array, which could be null.
   * @param array2
   *     the second {@link Instant} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Instant[] array1,
      @Nullable final Instant[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Instant} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Instant}
   * objects.
   *
   * @param array1
   *     the first {@link Instant} array.
   * @param n1
   *     the length of the first {@link Instant} array.
   * @param array2
   *     the second {@link Instant} array.
   * @param n2
   *     the length of the second {@link Instant} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Instant[] array1, final int n1,
      final Instant[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link LocalTime} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link LocalTime}
   * objects.
   *
   * @param value1
   *     the first {@link LocalTime} object.
   * @param value2
   *     the second {@link LocalTime} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final LocalTime value1,
      @Nullable final LocalTime value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link LocalTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link LocalTime}
   * objects.
   *
   * @param array1
   *     the first {@link LocalTime} array, which could be null.
   * @param array2
   *     the second {@link LocalTime} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final LocalTime[] array1,
      @Nullable final LocalTime[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link LocalTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link LocalTime}
   * objects.
   *
   * @param array1
   *     the first {@link LocalTime} array.
   * @param n1
   *     the length of the first {@link LocalTime} array.
   * @param array2
   *     the second {@link LocalTime} array.
   * @param n2
   *     the length of the second {@link LocalTime} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final LocalTime[] array1, final int n1,
      final LocalTime[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link LocalDate} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link LocalDate}
   * objects.
   *
   * @param value1
   *     the first {@link LocalDate} object.
   * @param value2
   *     the second {@link LocalDate} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final LocalDate value1,
      @Nullable final LocalDate value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link LocalDate} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link LocalDate}
   * objects.
   *
   * @param array1
   *     the first {@link LocalDate} array, which could be null.
   * @param array2
   *     the second {@link LocalDate} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final LocalDate[] array1,
      @Nullable final LocalDate[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link LocalDate} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link LocalDate}
   * objects.
   *
   * @param array1
   *     the first {@link LocalDate} array.
   * @param n1
   *     the length of the first {@link LocalDate} array.
   * @param array2
   *     the second {@link LocalDate} array.
   * @param n2
   *     the length of the second {@link LocalDate} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final LocalDate[] array1, final int n1,
      final LocalDate[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link LocalDateTime} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * LocalDateTime} objects.
   *
   * @param value1
   *     the first {@link LocalDateTime} object.
   * @param value2
   *     the second {@link LocalDateTime} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final LocalDateTime value1,
      @Nullable final LocalDateTime value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link LocalDateTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * LocalDateTime}
   * objects.
   *
   * @param array1
   *     the first {@link LocalDateTime} array, which could be null.
   * @param array2
   *     the second {@link LocalDateTime} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final LocalDateTime[] array1,
      @Nullable final LocalDateTime[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link LocalDateTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * LocalDateTime}
   * objects.
   *
   * @param array1
   *     the first {@link LocalDateTime} array.
   * @param n1
   *     the length of the first {@link LocalDateTime} array.
   * @param array2
   *     the second {@link LocalDateTime} array.
   * @param n2
   *     the length of the second {@link LocalDateTime} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final LocalDateTime[] array1, final int n1,
      final LocalDateTime[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link ZonedDateTime} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * ZonedDateTime} objects.
   *
   * @param value1
   *     the first {@link ZonedDateTime} object.
   * @param value2
   *     the second {@link ZonedDateTime} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final ZonedDateTime value1,
      @Nullable final ZonedDateTime value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link ZonedDateTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * ZonedDateTime}
   * objects.
   *
   * @param array1
   *     the first {@link ZonedDateTime} array, which could be null.
   * @param array2
   *     the second {@link ZonedDateTime} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final ZonedDateTime[] array1,
      @Nullable final ZonedDateTime[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link ZonedDateTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * ZonedDateTime}
   * objects.
   *
   * @param array1
   *     the first {@link ZonedDateTime} array.
   * @param n1
   *     the length of the first {@link ZonedDateTime} array.
   * @param array2
   *     the second {@link ZonedDateTime} array.
   * @param n2
   *     the length of the second {@link ZonedDateTime} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final ZonedDateTime[] array1, final int n1,
      final ZonedDateTime[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link OffsetDateTime} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * OffsetDateTime} objects.
   *
   * @param value1
   *     the first {@link OffsetDateTime} object.
   * @param value2
   *     the second {@link OffsetDateTime} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final OffsetDateTime value1,
      @Nullable final OffsetDateTime value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link OffsetDateTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * OffsetDateTime}
   * objects.
   *
   * @param array1
   *     the first {@link OffsetDateTime} array, which could be null.
   * @param array2
   *     the second {@link OffsetDateTime} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final OffsetDateTime[] array1,
      @Nullable final OffsetDateTime[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link OffsetDateTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link
   * OffsetDateTime}
   * objects.
   *
   * @param array1
   *     the first {@link OffsetDateTime} array.
   * @param n1
   *     the length of the first {@link OffsetDateTime} array.
   * @param array2
   *     the second {@link OffsetDateTime} array.
   * @param n2
   *     the length of the second {@link OffsetDateTime} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final OffsetDateTime[] array1, final int n1,
      final OffsetDateTime[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link OffsetTime} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link OffsetTime}
   * objects.
   *
   * @param value1
   *     the first {@link OffsetTime} object.
   * @param value2
   *     the second {@link OffsetTime} object.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compare(@Nullable final OffsetTime value1,
      @Nullable final OffsetTime value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    }
    return value1.compareTo(value2);
  }

  /**
   * Compares two {@link OffsetTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link OffsetTime}
   * objects.
   *
   * @param array1
   *     the first {@link OffsetTime} array, which could be null.
   * @param array2
   *     the second {@link OffsetTime} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final OffsetTime[] array1,
      @Nullable final OffsetTime[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link OffsetTime} arrays lexically.
   *
   * <p>We assume that {@code null} is the minimum value for {@link OffsetTime}
   * objects.
   *
   * @param array1
   *     the first {@link OffsetTime} array.
   * @param n1
   *     the length of the first {@link OffsetTime} array.
   * @param array2
   *     the second {@link OffsetTime} array.
   * @param n2
   *     the length of the second {@link OffsetTime} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final OffsetTime[] array1, final int n1,
      final OffsetTime[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two collection of {@code boolean} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code boolean} values, which could be null.
   * @param col2
   *     the second collection of {@code boolean} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final BooleanCollection col1,
      @Nullable final BooleanCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code char} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code char} values, which could be null.
   * @param col2
   *     the second collection of {@code char} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final CharCollection col1,
      @Nullable final CharCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code byte} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code byte} values, which could be null.
   * @param col2
   *     the second collection of {@code byte} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final ByteCollection col1,
      @Nullable final ByteCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code short} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code short} values, which could be null.
   * @param col2
   *     the second collection of {@code short} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final ShortCollection col1,
      @Nullable final ShortCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code int} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code int} values, which could be null.
   * @param col2
   *     the second collection of {@code int} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final IntCollection col1,
      @Nullable final IntCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code long} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code long} values, which could be null.
   * @param col2
   *     the second collection of {@code long} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final LongCollection col1,
      @Nullable final LongCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code float} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code float} values, which could be null.
   * @param col2
   *     the second collection of {@code float} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final FloatCollection col1,
      @Nullable final FloatCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@code double} values for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param col1
   *     the first collection of {@code double} values, which could be null.
   * @param col2
   *     the second collection of {@code double} values, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static int compare(@Nullable final DoubleCollection col1,
      @Nullable final DoubleCollection col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    } else {
      return col1.compareTo(col2);
    }
  }

  /**
   * Compares two collection of {@link Comparable} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param <T>
   *     the type of the elements of the collection.
   * @param col1
   *     the first collection of {@link Comparable} objects, which could be
   *     null.
   * @param col2
   *     the second collection of {@link Comparable} objects, which could be
   *     null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static <T> int compare(@Nullable final Collection<T> col1,
      @Nullable final Collection<T> col2) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    }
    final Iterator<T> iter1 = col1.iterator();
    final Iterator<T> iter2 = col2.iterator();
    while (iter1.hasNext() && iter2.hasNext()) {
      final T value1Value = iter1.next();
      final T value2Value = iter2.next();
      final int result = compare(value1Value, value2Value);
      if (result != 0) {
        return result;
      }
    }
    if (iter1.hasNext()) {
      return +1;
    } else if (iter2.hasNext()) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Compares two collection of {@link Comparable} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param <T>
   *     the type of the elements of the collection.
   * @param col1
   *     the first collection of {@link Comparable} objects, which could be
   *     null.
   * @param col2
   *     the second collection of {@link Comparable} objects, which could be
   *     null.
   * @param epsilon
   *     the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static <T> int compare(@Nullable final Collection<T> col1,
      @Nullable final Collection<T> col2, final double epsilon) {
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    }
    final Iterator<T> iter1 = col1.iterator();
    final Iterator<T> iter2 = col2.iterator();
    while (iter1.hasNext() && iter2.hasNext()) {
      final T value1Value = iter1.next();
      final T value2Value = iter2.next();
      final int result = compare(value1Value, value2Value, epsilon);
      if (result != 0) {
        return result;
      }
    }
    if (iter1.hasNext()) {
      return +1;
    } else if (iter2.hasNext()) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Compares two collection of {@link Comparable} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Comparable} objects.
   *
   * <p>This function could deal with the collection of primitive arrays and
   * muti-dimension arrays.
   *
   * @param <T>
   *     the type of the elements of the collection.
   * @param col1
   *     the first collection of {@link Comparable} objects, which could be
   *     null.
   * @param col2
   *     the second collection of {@link Comparable} objects, which could be
   *     null.
   * @param comparator
   *     the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *     collection compares lexicographically less than, equal to, or greater
   *     than the second collection.
   */
  public static <T> int compare(@Nullable final Collection<T> col1,
      @Nullable final Collection<T> col2,
      @Nullable final Comparator<T> comparator) {
    if (comparator == null) {
      return compare(col1, col2);
    }
    if (col1 == null) {
      return (col2 == null ? 0 : -1);
    } else if (col2 == null) {
      return +1;
    }
    final Iterator<T> iter1 = col1.iterator();
    final Iterator<T> iter2 = col2.iterator();
    while (iter1.hasNext() && iter2.hasNext()) {
      final T value1Value = iter1.next();
      final T value2Value = iter2.next();
      final int result = compare(value1Value, value2Value, comparator);
      if (result != 0) {
        return result;
      }
    }
    if (iter1.hasNext()) {
      return +1;
    } else if (iter2.hasNext()) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Compares two {@link Comparable} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Comparable}
   * objects.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param value1
   *     the first {@link Comparable} object. Note that it could be null or a
   *     multi-dimensional array.
   * @param value2
   *     the second {@link Comparable} object. Note that it could be null or a
   *     multi-dimensional array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static int compare(@Nullable final Object value1,
      @Nullable final Object value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return (class1.getName().compareTo(class2.getName()));
      }
      if (class1.isArray()) {
        // switch on type of array, to dispatch to the correct handler
        // handles multidimensional arrays.
        if (value1 instanceof boolean[]) {
          final boolean[] array1 = (boolean[]) value1;
          final boolean[] array2 = (boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof char[]) {
          final char[] array1 = (char[]) value1;
          final char[] array2 = (char[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof byte[]) {
          final byte[] array1 = (byte[]) value1;
          final byte[] array2 = (byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof short[]) {
          final short[] array1 = (short[]) value1;
          final short[] array2 = (short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof int[]) {
          final int[] array1 = (int[]) value1;
          final int[] array2 = (int[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof long[]) {
          final long[] array1 = (long[]) value1;
          final long[] array2 = (long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof float[]) {
          final float[] array1 = (float[]) value1;
          final float[] array2 = (float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof double[]) {
          final double[] array1 = (double[]) value1;
          final double[] array2 = (double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof String[]) {
          final String[] array1 = (String[]) value1;
          final String[] array2 = (String[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Boolean[]) {
          final Boolean[] array1 = (Boolean[]) value1;
          final Boolean[] array2 = (Boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Character[]) {
          final Character[] array1 = (Character[]) value1;
          final Character[] array2 = (Character[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Byte[]) {
          final Byte[] array1 = (Byte[]) value1;
          final Byte[] array2 = (Byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Short[]) {
          final Short[] array1 = (Short[]) value1;
          final Short[] array2 = (Short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Integer[]) {
          final Integer[] array1 = (Integer[]) value1;
          final Integer[] array2 = (Integer[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Long[]) {
          final Long[] array1 = (Long[]) value1;
          final Long[] array2 = (Long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Float[]) {
          final Float[] array1 = (Float[]) value1;
          final Float[] array2 = (Float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Double[]) {
          final Double[] array1 = (Double[]) value1;
          final Double[] array2 = (Double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Class<?>[]) {
          final Class<?>[] array1 = (Class<?>[]) value1;
          final Class<?>[] array2 = (Class<?>[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Date[]) {
          final Date[] array1 = (Date[]) value1;
          final Date[] array2 = (Date[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigInteger[]) {
          final BigInteger[] array1 = (BigInteger[]) value1;
          final BigInteger[] array2 = (BigInteger[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigDecimal[]) {
          final BigDecimal[] array1 = (BigDecimal[]) value1;
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else { // array of non-primitives
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        }
      } else if (class1 == Class.class) {
        return compare((Class<?>) value1, (Class<?>) value2);
      } else if (value1 instanceof Type) {
        return compare((Type) value1, (Type) value2);
      //      } else if (value1 instanceof Instant) {
      //        return compare((Instant) value1, (Instant) value2);
      //      } else if (value1 instanceof LocalTime) {
      //        return compare((LocalTime) value1, (LocalTime) value2);
      //      } else if (value1 instanceof LocalDate) {
      //        return compare((LocalDate) value1, (LocalDate) value2);
      //      } else if (value1 instanceof LocalDateTime) {
      //        return compare((LocalDateTime) value1, (LocalDateTime) value2);
      //      } else if (value1 instanceof ZonedDateTime) {
      //        return compare((ZonedDateTime) value1, (ZonedDateTime) value2);
      //      } else if (value1 instanceof OffsetDateTime) {
      //        return compare((OffsetDateTime) value1, (OffsetDateTime) value2);
      //      } else if (value1 instanceof OffsetTime) {
      //        return compare((OffsetTime) value1, (OffsetTime) value2);
      //      } else if (value1 instanceof Date) {
      //        return compare((Date) value1, (Date) value2);
      } else if (value1 instanceof Comparable<?>) {
        // value1 and value2 are not arrays
        return ((Comparable) value1).compareTo(value2);
      } else {
        // compare the string representation of two objects
        final String str1 = value1.toString();
        final String str2 = value2.toString();
        return str1.compareTo(str2);
      }
    }
  }

  /**
   * Compares two {@link Comparable} object arrays lexically.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *     the first {@link Comparable} object array, which could be null or array
   *     of arrays.
   * @param array2
   *     the second {@link Comparable} object array, which could be null or
   *     array of arrays.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Object[] array1,
      @Nullable final Object[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Comparable} object arrays lexically.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *     the first {@link Comparable} object array, which could be array of
   *     arrays.
   * @param n1
   *     the length of the first {@link Comparable} object array.
   * @param array2
   *     the second {@link Comparable} object array, which could be array of
   *     arrays.
   * @param n2
   *     the length of the second {@link Comparable} object array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Object[] array1, final int n1,
      final Object[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      // Note that here is very important to call the
      // Comparison.compare(Object, Object) to compare
      // array1[i] and array2[i], since the multi-array is an Object in Java
      final int result = compare(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Comparable} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Comparable}
   * objects.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param value1
   *     the first {@link Comparable} object. Note that it could be null or a
   *     multi-dimensional array.
   * @param value2
   *     the second {@link Comparable} object. Note that it could be null or a
   *     multi-dimensional array.
   * @param epsilon
   *     the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  @SuppressWarnings("unchecked")
  public static int compare(@Nullable final Object value1,
      @Nullable final Object value2, final double epsilon) {
    if (value1 == value2) {
      return 0;
    } else if (value1 == null) {
      return -1; // note that value2 != null
    } else if (value2 == null) {
      return +1; // note that value1 != null
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return (class1.getName().compareTo(class2.getName()));
      }
      if (class1.isArray()) {
        // switch on type of array, to dispatch to the correct handler
        // handles multidimensional arrays.
        if (value1 instanceof float[]) {
          final float[] array1 = (float[]) value1;
          final float[] array2 = (float[]) value2;
          return compare(array1, array1.length, array2, array2.length,
              (float) epsilon);
        } else if (value1 instanceof double[]) {
          final double[] array1 = (double[]) value1;
          final double[] array2 = (double[]) value2;
          return compare(array1, array1.length, array2, array2.length, epsilon);
        } else if (value1 instanceof boolean[]) {
          final boolean[] array1 = (boolean[]) value1;
          final boolean[] array2 = (boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof char[]) {
          final char[] array1 = (char[]) value1;
          final char[] array2 = (char[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof byte[]) {
          final byte[] array1 = (byte[]) value1;
          final byte[] array2 = (byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof short[]) {
          final short[] array1 = (short[]) value1;
          final short[] array2 = (short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof int[]) {
          final int[] array1 = (int[]) value1;
          final int[] array2 = (int[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof long[]) {
          final long[] array1 = (long[]) value1;
          final long[] array2 = (long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof String[]) {
          final String[] array1 = (String[]) value1;
          final String[] array2 = (String[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Boolean[]) {
          final Boolean[] array1 = (Boolean[]) value1;
          final Boolean[] array2 = (Boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Character[]) {
          final Character[] array1 = (Character[]) value1;
          final Character[] array2 = (Character[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Byte[]) {
          final Byte[] array1 = (Byte[]) value1;
          final Byte[] array2 = (Byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Short[]) {
          final Short[] array1 = (Short[]) value1;
          final Short[] array2 = (Short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Integer[]) {
          final Integer[] array1 = (Integer[]) value1;
          final Integer[] array2 = (Integer[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Long[]) {
          final Long[] array1 = (Long[]) value1;
          final Long[] array2 = (Long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Float[]) {
          final Float[] array1 = (Float[]) value1;
          final Float[] array2 = (Float[]) value2;
          return compare(array1, array1.length, array2, array2.length,
              (float) epsilon);
        } else if (value1 instanceof Double[]) {
          final Double[] array1 = (Double[]) value1;
          final Double[] array2 = (Double[]) value2;
          return compare(array1, array1.length, array2, array2.length, epsilon);
        } else if (value1 instanceof Class<?>[]) {
          final Class<?>[] array1 = (Class<?>[]) value1;
          final Class<?>[] array2 = (Class<?>[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Date[]) {
          final Date[] array1 = (Date[]) value1;
          final Date[] array2 = (Date[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigInteger[]) {
          final BigInteger[] array1 = (BigInteger[]) value1;
          final BigInteger[] array2 = (BigInteger[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigDecimal[]) {
          final BigDecimal[] array1 = (BigDecimal[]) value1;
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else { // array of non-primitives
          final Object[] array1 = (Object[]) value1;
          final Object[] array2 = (Object[]) value2;
          return compare(array1, array1.length, array2, array2.length, epsilon);
        }
      } else if (class1 == Class.class) {
        return compare((Class<?>) value1, (Class<?>) value2);
      } else if (value1 instanceof Float) {
        return compare(((Float) value1).floatValue(),
            ((Float) value2).floatValue(), (float) epsilon);
      } else if (value1 instanceof Double) {
        return compare(((Double) value1).doubleValue(),
            ((Double) value2).doubleValue(), epsilon);
      } else if (value1 instanceof Comparable<?>) {
        // value1 and value2 are not arrays
        return Integer.compare(((Comparable) value1).compareTo(value2), 0);
      } else {
        // compare the string representation of two objects
        final String str1 = value1.toString();
        final String str2 = value2.toString();
        return Integer.compare(str1.compareTo(str2), 0);
      }
    }
  }

  /**
   * Compares two {@link Comparable} object arrays lexically.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *     the first {@link Comparable} object array, which could be null or array
   *     of arrays.
   * @param array2
   *     the second {@link Comparable} object array, which could be null or
   *     array of arrays.
   * @param epsilon
   *     the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(@Nullable final Object[] array1,
      @Nullable final Object[] array2, final double epsilon) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, epsilon);
    }
  }

  /**
   * Compares two {@link Comparable} object arrays lexically.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param array1
   *     the first {@link Comparable} object array, which could be array of
   *     arrays.
   * @param n1
   *     the length of the first {@link Comparable} object array.
   * @param array2
   *     the second {@link Comparable} object array, which could be array of
   *     arrays.
   * @param n2
   *     the length of the second {@link Comparable} object array.
   * @param epsilon
   *     the epsilon used to compare float or double values.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compare(final Object[] array1, final int n1,
      final Object[] array2, final int n2, final double epsilon) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      // Note that here is very important to call the
      // Comparison.compare(Object, Object) to compare array1[i]
      // and array2[i], since the multi-array is an Object in
      final int result = compare(array1[i], array2[i], epsilon);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Comparable} objects for order.
   *
   * <p>We assume that {@code null} is the minimum value for {@link Comparable}
   * objects.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param <T>
   *     the type of the objects.
   * @param value1
   *     the first {@link Comparable} object. Note that it could be null or a
   *     multi-dimensional array.
   * @param value2
   *     the second {@link Comparable} object. Note that it could be null or a
   *     multi-dimensional array.
   * @param comparator
   *     the comparator used to compare the underlying objects, or {@code null}
   *     if not specified.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  @SuppressWarnings("unchecked")
  public static <T> int compare(@Nullable final T value1,
      @Nullable final T value2,
      @Nullable final Comparator<T> comparator) {
    if (comparator == null) {
      return compare(value1, value2);
    }
    if (value1 == value2) {
      return 0;
    } else if (value1 == null) {
      return -1; // note that value2 != null
    } else if (value2 == null) {
      return +1; // note that value1 != null
    } else {
      final Class<?> class1 = value1.getClass();
      final Class<?> class2 = value2.getClass();
      if (class1 != class2) {
        return (class1.getName().compareTo(class2.getName()));
      }
      if (class1.isArray()) {
        // switch on type of array, to dispatch to the correct handler
        // handles multi dimensional arrays.
        if (value1 instanceof boolean[]) {
          final boolean[] array1 = (boolean[]) value1;
          final boolean[] array2 = (boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof char[]) {
          final char[] array1 = (char[]) value1;
          final char[] array2 = (char[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof byte[]) {
          final byte[] array1 = (byte[]) value1;
          final byte[] array2 = (byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof short[]) {
          final short[] array1 = (short[]) value1;
          final short[] array2 = (short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof int[]) {
          final int[] array1 = (int[]) value1;
          final int[] array2 = (int[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof long[]) {
          final long[] array1 = (long[]) value1;
          final long[] array2 = (long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof float[]) {
          final float[] array1 = (float[]) value1;
          final float[] array2 = (float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof double[]) {
          final double[] array1 = (double[]) value1;
          final double[] array2 = (double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof String[]) {
          final String[] array1 = (String[]) value1;
          final String[] array2 = (String[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Boolean[]) {
          final Boolean[] array1 = (Boolean[]) value1;
          final Boolean[] array2 = (Boolean[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Character[]) {
          final Character[] array1 = (Character[]) value1;
          final Character[] array2 = (Character[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Byte[]) {
          final Byte[] array1 = (Byte[]) value1;
          final Byte[] array2 = (Byte[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Short[]) {
          final Short[] array1 = (Short[]) value1;
          final Short[] array2 = (Short[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Integer[]) {
          final Integer[] array1 = (Integer[]) value1;
          final Integer[] array2 = (Integer[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Long[]) {
          final Long[] array1 = (Long[]) value1;
          final Long[] array2 = (Long[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Float[]) {
          final Float[] array1 = (Float[]) value1;
          final Float[] array2 = (Float[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Double[]) {
          final Double[] array1 = (Double[]) value1;
          final Double[] array2 = (Double[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Class<?>[]) {
          final Class<?>[] array1 = (Class<?>[]) value1;
          final Class<?>[] array2 = (Class<?>[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof Date[]) {
          final Date[] array1 = (Date[]) value1;
          final Date[] array2 = (Date[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigInteger[]) {
          final BigInteger[] array1 = (BigInteger[]) value1;
          final BigInteger[] array2 = (BigInteger[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else if (value1 instanceof BigDecimal[]) {
          final BigDecimal[] array1 = (BigDecimal[]) value1;
          final BigDecimal[] array2 = (BigDecimal[]) value2;
          return compare(array1, array1.length, array2, array2.length);
        } else { // array of non-primitives
          final T[] array1 = (T[]) value1;
          final T[] array2 = (T[]) value2;
          return compare(array1, array1.length, array2, array2.length, comparator);
        }
      } else if (class1 == Class.class) {
        return compare((Class<?>) value1, (Class<?>) value2);
      } else {
        // compare the two objects using the comparator
        return Integer.compare(comparator.compare(value1, value2), 0);
      }
    }
  }

  /**
   * Compares two {@link Comparable} {@link Object} arrays lexically.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param <T>
   *     the type of the objects.
   * @param array1
   *     the first {@link Comparable} {@link Object} array, which could be null
   *     or array of arrays.
   * @param array2
   *     the second {@link Comparable} {@link Object} array, which could be null
   *     or array of arrays.
   * @param comparator
   *     the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static <T> int compare(@Nullable final T[] array1,
      @Nullable final T[] array2, final Comparator<T> comparator) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compare(array1, array1.length, array2, array2.length, comparator);
    }
  }

  /**
   * Compares two object arrays lexically.
   *
   * <p>Note that the implementation of this function is non-trivial, since the
   * multi-dimensional array is also an Object in Java.
   *
   * @param <T>
   *     the type of the objects.
   * @param array1
   *     the first object array, which could be array of arrays.
   * @param n1
   *     the length of the first {@link Comparable} object array.
   * @param array2
   *     the second object array, which could be array of arrays.
   * @param n2
   *     the length of the second {@link Comparable} object array.
   * @param comparator
   *     the comparator used to compare the underlying objects.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static <T> int compare(final T[] array1, final int n1,
      final T[] array2, final int n2, final Comparator<T> comparator) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      // Note that here is very important to call the
      // Comparison.compare(Object, Object) to compare array1[i]
      // and array2[i], since the multi-array is an Object in
      final int result = compare(array1[i], array2[i], comparator);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@code char} arrays lexically ignoring the case.
   *
   * <p>The function compares two {@code char} value by their numeric values of
   * lowercase.
   *
   * @param array1
   *     the first {@code char} array, which could be null.
   * @param array2
   *     the second {@code char} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compareIgnoreCase(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compareIgnoreCase(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@code char} arrays lexically ignoring the case.
   *
   * <p>The function compares two {@code char} value by their numeric values
   * of lowercase.
   *
   * @param array1
   *     the first {@code char} array.
   * @param n1
   *     the length of the first {@code char} array.
   * @param array2
   *     the second {@code char} array.
   * @param n2
   *     the length of the second {@code char} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compareIgnoreCase(final char[] array1, final int n1,
      final char[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final char x = Character.toLowerCase(array1[i]);
      final char y = Character.toLowerCase(array2[i]);
      if (x != y) {
        return (x < y ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link Character} objects for order, ignoring the case.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link Character} objects.
   *
   * @param value1
   *     the first {@link Character} object, which could be null.
   * @param value2
   *     the second {@link Character} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares lexicographically less than, equal to, or greater than
   *     the second value.
   */
  public static int compareIgnoreCase(@Nullable final Character value1,
      @Nullable final Character value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      final char vl = Character.toLowerCase(value1);
      final char vr = Character.toLowerCase(value2);
      return Integer.compare(vl, vr);
    }
  }

  /**
   * Compares two {@link Character} arrays lexically ignoring the case.
   *
   * <p>We assume that {@code null} is the minimum value for the lowercase of
   * {@link Character} objects.
   *
   * @param array1
   *     the first {@link Character} array, which could be null.
   * @param array2
   *     the second {@link Character} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compareIgnoreCase(@Nullable final Character[] array1,
      @Nullable final Character[] array2) {
    if (array1 == null) {
      return (array2 == null ? 0 : -1);
    } else if (array2 == null) {
      return +1;
    } else {
      return compareIgnoreCase(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link Character} arrays lexically ignoring the case.
   *
   * <p>We assume that {@code null} is the minimum value for the lowercase of
   * {@link Character} objects.
   *
   * @param array1
   *     the first {@link Character} array.
   * @param n1
   *     the length of the first {@link Character} array.
   * @param array2
   *     the second {@link Character} array.
   * @param n2
   *     the length of the second {@link Character} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares lexicographically less than, equal to, or greater than
   *     the second array.
   */
  public static int compareIgnoreCase(final Character[] array1, final int n1,
      final Character[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compareIgnoreCase(array1[i], array2[i]);
      if (result != 0) {
        return result;
      }
    }
    return Integer.compare(n1, n2);
  }

  /**
   * Compares two {@link String} objects for order, ignoring the case.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link String} objects.
   *
   * @param value1
   *     the first {@link String} object, which could be null.
   * @param value2
   *     the second {@link String} object, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     value compares ignoring the case lexicographically less than, equal to,
   *     or greater than the second value.
   */
  public static int compareIgnoreCase(@Nullable final String value1,
      @Nullable final String value2) {
    if (value1 == null) {
      return (value2 == null ? 0 : -1);
    } else if (value2 == null) {
      return +1;
    } else {
      return Integer.compare(value1.compareToIgnoreCase(value2), 0);
    }
  }

  /**
   * Compares two {@link String} arrays lexically, ignoring the case.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link String} objects.
   *
   * @param array1
   *     the first {@link String} array, which could be null.
   * @param array2
   *     the second {@link String} array, which could be null.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares ignoring the case lexicographically less than, equal to,
   *     or greater than the second array.
   */
  public static int compareIgnoreCase(@Nullable final String[] array1,
      @Nullable final String[] array2) {
    if (array1 == array2) {
      return 0;
    } else if (array1 == null) {
      return -1;
    } else if (array2 == null) {
      return +1;
    } else {
      return compareIgnoreCase(array1, array1.length, array2, array2.length);
    }
  }

  /**
   * Compares two {@link String} arrays lexically, ignoring the case.
   *
   * <p>We assume that {@code null} is the minimum value for
   * {@link String} objects.
   *
   * @param array1
   *     the first {@link String} array.
   * @param n1
   *     the length of the first {@link String} array.
   * @param array2
   *     the second {@link String} array.
   * @param n2
   *     the length of the second {@link String} array.
   * @return An integer less than, equal to or greater than 0, if the first
   *     array compares ignoring the case lexicographically less than, equal to,
   *     or greater than the second array.
   */
  public static int compareIgnoreCase(final String[] array1, final int n1,
      final String[] array2, final int n2) {
    final int n = Math.min(n1, n2);
    for (int i = 0; i < n; ++i) {
      final int result = compareIgnoreCase(array1[i], array2[i]);
      if (result != 0) {
        return (result < 0 ? -1 : +1);
      }
    }
    return Integer.compare(n1, n2);
  }
}