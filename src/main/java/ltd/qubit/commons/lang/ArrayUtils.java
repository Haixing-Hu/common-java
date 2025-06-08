////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.math.MathEx;
import ltd.qubit.commons.text.tostring.SimpleToStringStyle;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 这个类提供了对数组、基本类型数组（例如 {@code int[]}）和基本类型包装数组（例如 {@code Integer[]}）的操作。
 *
 * <p>这个类尝试优雅地处理 {@code null} 输入。对于 {@code null} 数组输入不会抛出异常。
 * 但是，包含 {@code null} 元素的对象数组可能会抛出异常。每个方法都会记录其行为。
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class ArrayUtils {

  /**
   * 一个空的不可变 {@code Object} 数组。
   */
  public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  /**
   * 一个空的不可变 {@code Class} 数组。
   */
  public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

  /**
   * 一个空的不可变 {@code Type} 数组。
   */
  public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

  /**
   * 一个空的不可变 {@code String} 数组。
   */
  public static final String[] EMPTY_STRING_ARRAY = new String[0];

  /**
   * 一个空的不可变 {@code String[]} 二维数组。
   */
  public static final String[][] EMPTY_STRING_ARRAY_2D = new String[0][0];

  /**
   * 一个空的不可变 {@code long} 数组。
   */
  public static final long[] EMPTY_LONG_ARRAY = new long[0];

  /**
   * 一个空的不可变 {@code Long} 数组。
   */
  public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

  /**
   * 一个空的不可变 {@code int} 数组。
   */
  public static final int[] EMPTY_INT_ARRAY = new int[0];

  /**
   * 一个空的不可变 {@code Integer} 数组。
   */
  public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

  /**
   * 一个空的不可变 {@code short} 数组。
   */
  public static final short[] EMPTY_SHORT_ARRAY = new short[0];

  /**
   * 一个空的不可变 {@code Short} 数组。
   */
  public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

  /**
   * 一个空的不可变 {@code byte} 数组。
   */
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  /**
   * 一个空的不可变 {@code Byte} 数组。
   */
  public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

  /**
   * 一个空的不可变 {@code double} 数组。
   */
  public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

  /**
   * 一个空的不可变 {@code Double} 数组。
   */
  public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

  /**
   * 一个空的不可变 {@code float} 数组。
   */
  public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

  /**
   * 一个空的不可变 {@code Float} 数组。
   */
  public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

  /**
   * 一个空的不可变 {@code boolean} 数组。
   */
  public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

  /**
   * 一个空的不可变 {@code Boolean} 数组。
   */
  public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

  /**
   * 一个空的不可变 {@code char} 数组。
   */
  public static final char[] EMPTY_CHAR_ARRAY = new char[0];

  /**
   * 一个空的不可变 {@code Character} 数组。
   */
  public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

  /**
   * 一个空的不可变 {@code byte[]} 数组。
   */
  public static final byte[][] EMPTY_BYTE_ARRAY_ARRAY = new byte[0][];

  /**
   * 一个空的不可变 {@link BigInteger} 数组。
   */
  public static final BigInteger[] EMPTY_BIG_INTEGER_ARRAY = new BigInteger[0];

  /**
   * 一个空的不可变 {@link BigDecimal} 数组。
   */
  public static final BigDecimal[] EMPTY_BIG_DECIMAL_ARRAY = new BigDecimal[0];

  /**
   * 一个空的不可变 {@link Date} 数组。
   */
  public static final Date[] EMPTY_DATE_ARRAY = new Date[0];

  /**
   * 一个空的不可变 {@link Time} 数组。
   */
  public static final Time[] EMPTY_TIME_ARRAY = new Time[0];

  /**
   * 一个空的不可变 {@link Timestamp} 数组。
   */
  public static final Timestamp[] EMPTY_TIMESTAMP_ARRAY = new Timestamp[0];

  /**
   * 一个空的不可变 {@link LocalDate} 数组。
   */
  public static final LocalDate[] EMPTY_LOCAL_DATE_ARRAY = new LocalDate[0];

  /**
   * 一个空的不可变 {@link LocalTime} 数组。
   */
  public static final LocalTime[] EMPTY_LOCAL_TIME_ARRAY = new LocalTime[0];

  /**
   * 一个空的不可变 {@link LocalDateTime} 数组。
   */
  public static final LocalDateTime[] EMPTY_LOCAL_DATETIME_ARRAY = new LocalDateTime[0];

  /**
   * 当在列表或数组中找不到元素时的当前值：{@code -1}。
   * 此类中的方法返回此值，也可用于与来自 {@link java.util.List} 的各种方法返回的值进行比较。
   */
  public static final int INDEX_NOT_FOUND = -1;

  private ArrayUtils() {
  }

  /**
   * 将数组作为字符串输出，将 {@code null} 视为空数组。
   *
   * <p>多维数组和多维基本类型数组都会被正确处理。
   *
   * <p>格式为 Java 源代码格式，例如 {@code {a,b}}。
   *
   * @param array
   *     要获取字符串表示的数组，可能为 {@code null}
   * @return 数组的字符串表示，如果为 null 数组输入则返回 '{}'
   */
  public static String toString(final Object array) {
    return toString(array, "{}");
  }

  /**
   * 将数组作为字符串输出，处理 {@code null}。
   *
   * <p>多维数组和多维基本类型数组都会被正确处理。
   *
   * <p>格式为 Java 源代码格式，例如 {@code {a,b}}。
   *
   * @param array
   *     要获取字符串表示的数组，可能为 {@code null}
   * @param stringIfNull
   *     如果数组为 {@code null} 时返回的字符串
   * @return 数组的字符串表示
   */
  public static String toString(final Object array, final String stringIfNull) {
    if (array == null) {
      return stringIfNull;
    }
    return new ToStringBuilder(SimpleToStringStyle.INSTANCE)
        .append(array)
        .toString();
  }

  /**
   * 获取数组的哈希码，正确处理多维数组。
   *
   * <p>多维基本类型数组也会被此方法正确处理。
   *
   * @param array
   *     要获取哈希码的数组，可能为 {@code null}
   * @return 数组的哈希码，如果为 null 数组输入则返回零
   */
  public static int hashCode(final Object array) {
    int code = 2;
    final int multiplier = 13333;
    code = Hash.combine(code, multiplier, array);
    return code;
  }

  /**
   * 将给定数组转换为 {@link java.util.Map}。该数组的每个元素都必须是 {@link java.util.Map.Entry}
   * 或包含至少两个元素的数组，其中第一个元素用作键，第二个元素用作值。
   *
   * <p>此方法可用于初始化：
   *
   * <pre>
   * // 创建一个映射颜色的 Map。
   * Map colorMap = ArrayUtils.toMap(new String[][] {{
   *     {"RED", "#FF0000"},
   *     {"GREEN", "#00FF00"},
   *     {"BLUE", "#0000FF"}});
   * </pre>
   *
   * <p>此方法对于 {@code null} 输入数组返回 {@code null}。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     一个数组，其元素是 {@link java.util.Map.Entry} 或包含至少两个元素的数组，可能为 {@code null}。
   * @return 从数组创建的 {@code Map}
   * @throws IllegalArgumentException
   *     如果此数组的一个元素本身是包含少于两个元素的数组
   * @throws IllegalArgumentException
   *     如果该数组包含除 {@link java.util.Map.Entry} 和 Buffer 以外的元素
   */
  public static <T> Map<Object, Object> toMap(@Nullable final T[] array) {
    if (array == null) {
      return null;
    }
    final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
    for (int i = 0; i < array.length; ++i) {
      final Object object = array[i];
      if (object instanceof final Map.Entry<?, ?> entry) {
        map.put(entry.getKey(), entry.getValue());
      } else if (object instanceof final Object[] entry) {
        if (entry.length < 2) {
          throw new IllegalArgumentException("Array element "
              + i
              + ", '"
              + object
              + "', has a length less than 2");
        }
        map.put(entry[0], entry[1]);
      } else {
        throw new IllegalArgumentException("Array element "
            + i
            + ", '"
            + object
            + "', is neither of type Map.Entry nor an object array");
      }
    }
    return map;
  }
  /**
   * 在 byte 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static byte max(@Nullable final byte[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    byte result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] > result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 byte 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static byte max(@Nullable final byte[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 byte 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static byte min(@Nullable final byte[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    byte result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] < result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 byte 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static byte min(@Nullable final byte[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在 short 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static short max(@Nullable final short[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    short result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] > result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 short 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static short max(@Nullable final short[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 short 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static short min(@Nullable final short[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    short result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] < result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 short 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static short min(@Nullable final short[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在 int 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static int max(@Nullable final int[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    int result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] > result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 int 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static int max(@Nullable final int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 int 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static int min(@Nullable final int[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    int result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] < result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 int 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static int min(@Nullable final int[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在 long 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static long max(@Nullable final long[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0L;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0L;
    }
    long result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] > result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 long 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static long max(@Nullable final long[] array) {
    if (array == null || array.length == 0) {
      return 0L;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 long 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static long min(@Nullable final long[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0L;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0L;
    }
    long result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] < result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 long 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static long min(@Nullable final long[] array) {
    if (array == null || array.length == 0) {
      return 0L;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在 float 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0.0f}。
   */
  public static float max(@Nullable final float[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0.0f;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0.0f;
    }
    float result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (Float.isNaN(array[i])) {
        return Float.NaN;
      }
      if (array[i] > result || Float.isNaN(result)) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 float 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0.0f}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static float max(@Nullable final float[] array) {
    if (array == null || array.length == 0) {
      return 0.0f;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 float 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0.0f}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static float min(@Nullable final float[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0.0f;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0.0f;
    }
    float result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (Float.isNaN(array[i])) {
        return Float.NaN;
      }
      if (array[i] < result || Float.isNaN(result)) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 float 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0.0f}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static float min(@Nullable final float[] array) {
    if (array == null || array.length == 0) {
      return 0.0f;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在 double 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0.0}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static double max(@Nullable final double[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0.0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0.0;
    }
    double result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (Double.isNaN(array[i])) {
        return Double.NaN;
      }
      if (array[i] > result || Double.isNaN(result)) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 double 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0.0}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static double max(@Nullable final double[] array) {
    if (array == null || array.length == 0) {
      return 0.0;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 double 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0.0}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static double min(@Nullable final double[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0.0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0.0;
    }
    double result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (Double.isNaN(array[i])) {
        return Double.NaN;
      }
      if (array[i] < result || Double.isNaN(result)) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 double 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0.0}。
   *     如果任何值是 NaN，则返回 NaN。
   */
  public static double min(@Nullable final double[] array) {
    if (array == null || array.length == 0) {
      return 0.0;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在 char 数组的一段中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static char max(@Nullable final char[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    char result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] > result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 char 数组中找到最大值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static char max(@Nullable final char[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在 char 数组的一段中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code 0}。
   */
  public static char min(@Nullable final char[] array, final int start, final int end) {
    if (array == null || array.length == 0) {
      return 0;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return 0;
    }
    char result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] < result) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在 char 数组中找到最小值。
   *
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null} 或为空，则返回 {@code 0}。
   */
  public static char min(@Nullable final char[] array) {
    if (array == null || array.length == 0) {
      return 0;
    }
    return min(array, 0, array.length);
  }

  /**
   * 在数组的一段中找到最大值。
   * <p>
   * <b>注意：{@code null}值被认为是最小的。</b>
   *
   * @param <T>
   *     数组中元素的类型，必须是可比较的。
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return 在指定数组段中找到的最大值；如果输入数组为 {@code null} 或段为空，则返回 {@code null}。
   */
  public static <T extends Comparable<? super T>> T max(@Nullable final T[] array,
      final int start, final int end) {
    if (array == null) {
      return null;
    }
    int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return null;
    }
    T result = array[theStart];
    while ((result == null) && (++theStart < theEnd)) {
      result = array[theStart];
    }
    if (result == null) {
      return null;
    }
    for (int i = theStart + 1; i < theEnd; ++i) {
      if ((array[i] != null) && (result.compareTo(array[i]) < 0)) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在数组中找到最大值。
   * <p>
   * <b>注意：{@code null}值被认为是最小的。</b>
   *
   * @param <T>
   *     数组中元素的类型，必须是可比较的。
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最大值，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static <T extends Comparable<? super T>> T max(@Nullable final T[] array) {
    if (array == null) {
      return null;
    }
    return max(array, 0, array.length);
  }

  /**
   * 在数组的一段中找到最小值。
   * <p>
   * <b>注意：{@code null}值被认为是最小的。</b>
   *
   * @param <T>
   *     数组中元素的类型，必须是可比较的。
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     段的包含性起始索引。如果小于 0，则视为 0。
   * @param end
   *     段的排他性结束索引。如果大于 {@code array.length}，则视为 {@code array.length}。
   * @return
   *     在指定数组段中找到的最小值；如果输入数组为 {@code null} 或段为空，则返回 {@code null}。
   */
  public static <T extends Comparable<? super T>> T min(@Nullable final T[] array,
      final int start, final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return null;
    }
    T result = array[theStart];
    if (result == null) {
      return null;
    }
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] == null) {
        return null;
      }
      if (array[i].compareTo(result) < 0) {
        result = array[i];
      }
    }
    return result;
  }

  /**
   * 在数组中找到最小值。
   * <p>
   * <b>注意：{@code null}值被认为是最小的。</b>
   *
   * @param <T>
   *     数组中元素的类型，必须是可比较的。
   * @param array
   *     数组，可能为 {@code null}。
   * @return 在指定数组中找到的最小值，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static <T extends Comparable<? super T>> T min(@Nullable final T[] array) {
    if (array == null) {
      return null;
    }
    return min(array, 0, array.length);
  }

  /**
   * 生成一个新数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * <p>子数组的组件类型始终与输入数组的组件类型相同。因此，如果输入是 {@code Date} 类型的数组，则预期用法如下：
   *
   * <pre>
   * Date[] someDates = Arrays.subarray(allDates, 2, 5);
   * </pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     数组，可能为 {@code null}。
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] subarray(@Nullable final T[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    final Class<?> type = array.getClass().getComponentType();
    if (newSize <= 0) {
      return (T[]) Array.newInstance(type, 0);
    } else {
      final T[] result = (T[]) Array.newInstance(type, newSize);
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code long} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static long[] subarray(@Nullable final long[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_LONG_ARRAY;
    } else {
      final long[] result = new long[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code int} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static int[] subarray(@Nullable final int[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_INT_ARRAY;
    } else {
      final int[] result = new int[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code short} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static short[] subarray(@Nullable final short[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_SHORT_ARRAY;
    } else {
      final short[] result = new short[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code char} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static char[] subarray(@Nullable final char[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_CHAR_ARRAY;
    } else {
      final char[] result = new char[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code byte} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static byte[] subarray(@Nullable final byte[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_BYTE_ARRAY;
    } else {
      final byte[] result = new byte[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code double} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static double[] subarray(@Nullable final double[] array,
      final int start, final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_DOUBLE_ARRAY;
    } else {
      final double[] result = new double[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code float} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static float[] subarray(@Nullable final float[] array, final int start,
      final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_FLOAT_ARRAY;
    } else {
      final float[] result = new float[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 提供一个新 {@code boolean} 数组，包含从起始到结束索引的元素。
   *
   * <p>起始索引是包含性的，结束索引是排他性的。Null 数组输入产生 null 输出。
   *
   * @param array
   *     数组
   * @param start
   *     起始索引。下溢值 (&lt;0) 提升为 0，上溢值 (&gt;array.length) 导致空数组。
   * @param end
   *     返回的子数组包含从起始到结束索引-1的元素。下溢值 (&lt; startIndex) 产生空数组，上溢值
   *     (&gt;array.length) 被降级为数组长度。
   * @return
   *     一个包含从起始到结束索引-1的元素的新数组，如果数组为 {@code null}，则返回 {@code null}。
   */
  public static boolean[] subarray(@Nullable final boolean[] array,
      final int start, final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    final int newSize = theEnd - theStart;
    if (newSize <= 0) {
      return EMPTY_BOOLEAN_ARRAY;
    } else {
      final boolean[] result = new boolean[newSize];
      System.arraycopy(array, theStart, result, 0, newSize);
      return result;
    }
  }

  /**
   * 测试一个对象是否是数组。
   *
   * @param obj
   *     要测试的对象，可能为 null。
   * @return 如果对象不为 null 且是数组，则为 true；否则为 false。
   */
  public static boolean isArray(@Nullable final Object obj) {
    return ((obj != null) && obj.getClass().isArray());
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * <p>任何多维方面的数组都会被忽略。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static <T> boolean isSameLength(@Nullable final T[] array1,
      @Nullable final T[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final long[] array1,
      @Nullable final long[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final int[] array1,
      @Nullable final int[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final short[] array1,
      @Nullable final short[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final byte[] array1,
      @Nullable final byte[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final double[] array1,
      @Nullable final double[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final float[] array1,
      @Nullable final float[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 检查两个数组是否长度相同，将 {@code null} 数组视为长度为 {@code 0}。
   *
   * @param array1
   *     第一个数组，可能为 {@code null}
   * @param array2
   *     第二个数组，可能为 {@code null}
   * @return 如果数组长度匹配，将 {@code null} 视为空数组，则为 {@code true}。
   */
  public static boolean isSameLength(@Nullable final boolean[] array1,
      @Nullable final boolean[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0))
        && ((array2 != null) || (array1 == null) || (array1.length <= 0))
        && ((array1 == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * 返回指定数组的长度。此方法可以处理 {@code Object} 数组和原始数组。
   *
   * <p>如果输入数组为 {@code null}，则返回 {@code 0}。
   *
   * <pre>
   * Arrays.getLength(null)            = 0
   * Arrays.getLength([])              = 0
   * Arrays.getLength([null])          = 1
   * Arrays.getLength([true, false])   = 2
   * Arrays.getLength([1, 2, 3])       = 3
   * Arrays.getLength(["a", "b", "c"]) = 3
   * </pre>
   *
   * @param array
   *     要从中检索长度的数组，可能为 null
   * @return 数组的长度，如果数组为 {@code null}，则返回 {@code 0}
   * @throws IllegalArgumentException
   *     如果对象参数不是数组。
   */
  public static int getLength(@Nullable final Object array) {
    if (array == null) {
      return 0;
    } else {
      return Array.getLength(array);
    }
  }

  /**
   * 检查两个数组是否是相同类型，考虑到多维数组。
   *
   * @param array1
   *     第一个数组，必须不为 {@code null}
   * @param array2
   *     第二个数组，必须不为 {@code null}
   * @return 如果数组类型匹配，则为 {@code true}
   * @throws IllegalArgumentException
   *     如果任一数组为 {@code null}
   */
  public static boolean isSameType(final Object array1, final Object array2) {
    if ((array1 == null) || (array2 == null)) {
      throw new IllegalArgumentException("The array must not be null");
      } else {
      final String array1ClassName = array1.getClass().getName();
      final String array2ClassName = array2.getClass().getName();
      return array1ClassName.equals(array2ClassName);
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>对于多维数组没有特殊处理。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static <T> void reverse(@Nullable final T[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final T tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final long[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final long tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final int[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final int tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final short[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final short tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final char[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final char tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final byte[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final byte tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final double[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final double tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final float[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final float tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   */
  public static void reverse(@Nullable final boolean[] array) {
    if (array == null) {
      return;
    }
    int i = 0;
    int j = array.length - 1;
    while (j > i) {
      final boolean tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      --j;
      ++i;
    }
  }

  /**
   * 反转给定数组的顺序。
   *
   * <p><b>对多维数组没有特殊处理。</b>
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static <T> T[] reverseClone(@Nullable final T[] array) {
    if (array == null) {
      return null;
    }
    final T[] result = createArrayOfSameElementType(array, array.length);
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static long[] reverseClone(@Nullable final long[] array) {
    if (array == null) {
      return null;
    }
    final long[] result = new long[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static int[] reverseClone(@Nullable final int[] array) {
    if (array == null) {
      return null;
    }
    final int[] result = new int[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static short[] reverseClone(@Nullable final short[] array) {
    if (array == null) {
      return null;
    }
    final short[] result = new short[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static byte[] reverseClone(@Nullable final byte[] array) {
    if (array == null) {
      return null;
    }
    final byte[] result = new byte[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static char[] reverseClone(@Nullable final char[] array) {
    if (array == null) {
      return null;
    }
    final char[] result = new char[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static float[] reverseClone(@Nullable final float[] array) {
    if (array == null) {
      return null;
    }
    final float[] result = new float[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static double[] reverseClone(@Nullable final double[] array) {
    if (array == null) {
      return null;
    }
    final double[] result = new double[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 克隆给定数组并反转元素的顺序。
   *
   * <p>如果输入数组为 {@code null}，此方法不执行任何操作。
   *
   * @param array
   *     要反转的数组，可能为 {@code null}
   * @return 反转后的数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  @Nullable
  public static boolean[] reverseClone(@Nullable final boolean[] array) {
    if (array == null) {
      return null;
    }
    final boolean[] result = new boolean[array.length];
    final int lastIndex = array.length - 1;
    for (int i = 0; i < array.length; i++) {
      result[i] = array[lastIndex - i];
    }
    return result;
  }

  /**
   * 在数组中查找给定对象的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的对象，可能为 {@code null}
   * @return 对象在数组中的当前，如果未找到或输入数组为 {@code null}，则返回 {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static <T> int indexOf(@Nullable final T[] array,
      @Nullable final T value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定对象的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的对象，可能为 {@code null}
   * @param start
   *     开始搜索的当前
   * @return 对象在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回 {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static <T> int indexOf(@Nullable final T[] array,
      @Nullable final T value, final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    if (value == null) {
      for (int i = theStart; i < array.length; ++i) {
        if (array[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = theStart; i < array.length; ++i) {
        if (value.equals(array[i])) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final long[] array, final long value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final long[] array, final long value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final int[] array, final int value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final int[] array, final int value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final short[] array, final short value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final short[] array, final short value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final char[] array, final char value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final char[] array, final char value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final byte[] array, final byte value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND}
   * ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final byte[] array, final byte value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final double[] array, final double value) {
    return indexOf(array, value, 0, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND}
   * ({@code -1})。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final double[] array, final double value, final int start) {
    return indexOf(array, value, start, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 在数组中查找给定值的当前，该值在给定的容差范围内。此方法将返回第一个落在由{@code value - tolerance}
   * 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final double[] array, final double value, final double tolerance) {
    return indexOf(array, value, 0, tolerance);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前，该值在给定的容差范围内。此方法将返回第一个落在由
   * {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND}
   *  ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final double[] array, final double value,
      final int start, final double tolerance) {
    if (isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    final double min = value - tolerance;
    final double max = value + tolerance;
    for (int i = theStart; i < array.length; ++i) {
      if ((array[i] >= min) && (array[i] <= max)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final float[] array, final float value) {
    return indexOf(array, value, 0, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND}
   * ({@code -1})。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final float[] array, final float value, final int start) {
    return indexOf(array, value, start, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 在数组中查找给定值的当前，该值在给定的容差范围内。此方法将返回第一个落在由
   * {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final float[] array, final float value, final float tolerance) {
    return indexOf(array, value, 0, tolerance);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前，该值在给定的容差范围内。此方法将返回第一个落在由
   * {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final float[] array, final float value,
      final int start, final float tolerance) {
    if (isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    final float min = value - tolerance;
    final float max = value + tolerance;
    for (int i = theStart; i < array.length; ++i) {
      if ((array[i] >= min) && (array[i] <= max)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final boolean[] array,
      final boolean value) {
    return indexOf(array, value, 0);
  }

  /**
   * 从给定当前开始在数组中查找给定值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 被视为零。startIndex 大于数组长度将返回 {@link #INDEX_NOT_FOUND}
   * ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int indexOf(@Nullable final boolean[] array,
      final boolean value, final int start) {
    if (isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定对象的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的对象，可能为 {@code null}
   * @return
   *     对象在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static <T> int lastIndexOf(@Nullable final T[] array,
      @Nullable final T value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定对象的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的对象，可能为 {@code null}
   * @param start
   *     开始向后遍历的当前
   * @return
   *     对象在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static <T> int lastIndexOf(@Nullable final T[] array,
      @Nullable final T value, final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    if (value == null) {
      for (int i = theStart; i >= 0; --i) {
        if (array[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = theStart; i >= 0; --i) {
        if (value.equals(array[i])) {
          return i;
        }
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final boolean[] array,
      final boolean value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。startIndex
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final boolean[] array,
      final boolean value, final int start) {
    if (isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    for (int i = theStart; i >= 0; --i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final char[] array,
      final char value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final char[] array, final char value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    for (int i = theStart; i >= 0; --i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final long[] array,
      final long value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final long[] array, final long value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    for (int i = theStart; i >= 0; --i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final int[] array, final int value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final int[] array, final int value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    for (int i = theStart; i >= 0; --i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final short[] array,
      final short value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final short[] array,
      final short value, final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    for (int i = theStart; i >= 0; --i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }


  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final byte[] array,
      final byte value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final byte[] array, final byte value,
      final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    for (int i = theStart; i >= 0; --i) {
      if (value == array[i]) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(final float[] array, final float value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(final float[] array, final float value, final int start) {
    return lastIndexOf(array, value, start, MathEx.DEFAULT_FLOAT_EPSILON);
  }

  /**
   * 在数组中查找给定值的最后一个当前，该值在给定的容差范围内。此方法将返回第一个落在由
   * {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final float[] array, final float value, final float tolerance) {
    return lastIndexOf(array, value, Integer.MAX_VALUE, tolerance);
  }

  /**
   * 在数组中查找给定值的最后一个当前，该值在给定的容差范围内。此方法将返回第一个落在由
   * {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final float[] array, final float value,
      final int start, final float tolerance) {
    if (isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    final float min = value - tolerance;
    final float max = value + tolerance;
    for (int i = theStart; i >= 0; --i) {
      if ((array[i] >= min) && (array[i] <= max)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final double[] array, final double value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要向后遍历以查找对象的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始向后遍历的当前
   * @return
   *     值在数组中的最后一个当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final double[] array, final double value, final int start) {
    return lastIndexOf(array, value, start, MathEx.DEFAULT_DOUBLE_EPSILON);
  }

  /**
   * 在数组中查找给定值的最后一个当前，该值在给定的容差范围内。此方法将返回第一个落在由
   * {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final double[] array, final double value, final double tolerance) {
    return lastIndexOf(array, value, Integer.MAX_VALUE, tolerance);
  }

  /**
   * 从给定当前开始在数组中查找给定值的最后一个当前，该值在给定的容差范围内。此方法将返回第一个
   * 落在由 {@code value - tolerance} 和 {@code value + tolerance} 定义的区域内的值的当前。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的 startIndex 将返回 {@link #INDEX_NOT_FOUND} ({@code -1})。{@code startIndex}
   * 大于数组长度将从数组末尾开始搜索。
   *
   * @param array
   *     要搜索的数组，可能为 {@code null}
   * @param value
   *     要查找的值
   * @param start
   *     开始搜索的当前
   * @param tolerance
   *     搜索的容差
   * @return
   *     值在数组中从给定当前开始的当前，如果未找到或输入数组为 {@code null}，则返回
   *     {@link #INDEX_NOT_FOUND} ({@code -1})
   */
  public static int lastIndexOf(@Nullable final double[] array, final double value,
      final int start, final double tolerance) {
    if (isEmpty(array)) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.min(start, array.length - 1);
    if (theStart < 0) {
      return INDEX_NOT_FOUND;
    }
    final double min = value - tolerance;
    final double max = value + tolerance;
    for (int i = theStart; i >= 0; --i) {
      if ((array[i] >= min) && (array[i] <= max)) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final long[] array,
      final long value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final int[] array, final int value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final short[] array,
      final short value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final char[] array,
      final char value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final byte[] array,
      final byte value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final double[] array, final double value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值，该值在给定的容差范围内。如果数组包含在 (value - tolerance)
   * 到 (value + tolerance) 的范围内的值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @param tolerance
   *     搜索的容差
   * @return 如果数组包含给定容差内的值，则返回 {@code true}
   */
  public static boolean contains(@Nullable final double[] array, final double value, final double tolerance) {
    return indexOf(array, value, 0, tolerance) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final float[] array, final float value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值，该值在给定的容差范围内。如果数组包含在 (value - tolerance)
   * 到 (value + tolerance) 的范围内的值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @param tolerance
   *     搜索的容差
   * @return 如果数组包含给定容差内的值，则返回 {@code true}
   */
  public static boolean contains(@Nullable final float[] array, final float value, final float tolerance) {
    return indexOf(array, value, 0, tolerance) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定值。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的值
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static boolean contains(@Nullable final boolean[] array, final boolean value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查数组是否包含给定对象。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param <T>
   *     数组元素的类型。
   * @param array
   *     要搜索的数组
   * @param value
   *     要查找的对象
   * @return 如果数组包含该对象，则返回 {@code true}
   */
  public static <T> boolean contains(@Nullable final T[] array, final T value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * 检查给定数组是否包含满足指定谓词的任何元素。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param <T>
   *     数组元素的类型。
   * @param array
   *     要搜索的数组
   * @param predicate
   *     指定的谓词
   * @return 如果数组包含满足指定谓词的元素，则返回 {@code true}。
   */
  public static <T> boolean containsIf(@Nullable final T[] array,
      final Predicate<T> predicate) {
    if (array == null) {
      return false;
    }
    for (final T value : array) {
      if (predicate.test(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final long[] array,
      final long[] values) {
    for (final long value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final int[] array,
      final int[] values) {
    for (final int value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final short[] array,
      final short[] values) {
    for (final short value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final char[] array,
      final char[] values) {
    for (final char value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final byte[] array,
      final byte[] values) {
    for (final byte value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final double[] array, final double[] values) {
    for (final double value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @param tolerance
   *     给定的浮点比较容差
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final double[] array,
      final double[] values, final double tolerance) {
    for (final double value : values) {
      if (!contains(array, value, tolerance)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final float[] array, final float[] values) {
    for (final float value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @param tolerance
   *     给定的浮点比较容差
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final float[] array,
      final float[] values, final float tolerance) {
    for (final float value : values) {
      if (!contains(array, value, tolerance)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有值是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static boolean containsAll(@Nullable final boolean[] array,
      final boolean[] values) {
    for (final boolean value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中的所有对象是否在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含所有值，则返回 {@code true}
   */
  public static <T> boolean containsAll(@Nullable final T[] array,
      final T[] values) {
    for (final T value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final long[] array,
      final long[] values) {
    for (final long value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final int[] array,
      final int[] values) {
    for (final int value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final short[] array,
      final short[] values) {
    for (final short value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final char[] array,
      final char[] values) {
    for (final char value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final byte[] array,
      final byte[] values) {
    for (final byte value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_DOUBLE_EPSILON}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final double[] array,
      final double[] values) {
    for (final double value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @param tolerance
   *     比较的容差
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final double[] array,
      final double[] values, final double tolerance) {
    for (final double value : values) {
      if (contains(array, value, tolerance)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * <p>此函数使用默认的搜索容差{@link MathEx#DEFAULT_FLOAT_EPSILON}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final float[] array,
      final float[] values) {
    for (final float value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @param tolerance
   *     给定的浮点比较容差
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final float[] array,
      final float[] values, final float tolerance) {
    for (final float value : values) {
      if (contains(array, value, tolerance)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static boolean containsAny(@Nullable final boolean[] array,
      final boolean[] values) {
    for (final boolean value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 检查指定数组中是否存在任何值在给定数组中。
   *
   * <p>如果传入 {@code null} 数组，此方法返回 {@code false}。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索的数组
   * @param values
   *     要查找的值
   * @return
   *     如果数组包含任何值，则返回 {@code true}
   */
  public static <T> boolean containsAny(@Nullable final T[] array,
      final T[] values) {
    for (final T value : values) {
      if (contains(array, value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 将对象 Character 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Character} 数组，可能为 {@code null}
   * @return 一个 {@code char} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static char[] toPrimitive(@Nullable final Character[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CHAR_ARRAY;
    }
    final char[] result = new char[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Character 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Character} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code char} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static char[] toPrimitive(@Nullable final Character[] array,
      final char valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CHAR_ARRAY;
    }
    final char[] result = new char[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Character b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Long 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Long} 数组，可能为 {@code null}
   * @return 一个 {@code long} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static long[] toPrimitive(@Nullable final Long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_LONG_ARRAY;
    }
    final long[] result = new long[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Long 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Long} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code long} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static long[] toPrimitive(@Nullable final Long[] array,
      final long valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_LONG_ARRAY;
    }
    final long[] result = new long[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Long b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Integer 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Integer} 数组，可能为 {@code null}
   * @return 一个 {@code int} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static int[] toPrimitive(@Nullable final Integer[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_INT_ARRAY;
    }
    final int[] result = new int[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Integer 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Integer} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code int} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static int[] toPrimitive(@Nullable final Integer[] array,
      final int valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_INT_ARRAY;
    }
    final int[] result = new int[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Integer b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Short 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Short} 数组，可能为 {@code null}
   * @return 一个 {@code byte} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static short[] toPrimitive(@Nullable final Short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_SHORT_ARRAY;
    }
    final short[] result = new short[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Short 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Short} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code byte} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static short[] toPrimitive(@Nullable final Short[] array,
      final short valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_SHORT_ARRAY;
    }
    final short[] result = new short[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Short b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Byte 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Byte} 数组，可能为 {@code null}
   * @return 一个 {@code byte} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static byte[] toPrimitive(@Nullable final Byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BYTE_ARRAY;
    }
    final byte[] result = new byte[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Byte 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Byte} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code byte} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static byte[] toPrimitive(@Nullable final Byte[] array,
      final byte valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BYTE_ARRAY;
    }
    final byte[] result = new byte[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Byte b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Double 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Double} 数组，可能为 {@code null}
   * @return 一个 {@code double} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static double[] toPrimitive(@Nullable final Double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_DOUBLE_ARRAY;
    }
    final double[] result = new double[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Double 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Double} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code double} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static double[] toPrimitive(@Nullable final Double[] array,
      final double valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_DOUBLE_ARRAY;
    }
    final double[] result = new double[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Double b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Float 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Float} 数组，可能为 {@code null}
   * @return 一个 {@code float} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static float[] toPrimitive(@Nullable final Float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_FLOAT_ARRAY;
    }
    final float[] result = new float[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Float 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Float} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code float} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static float[] toPrimitive(@Nullable final Float[] array,
      final float valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_FLOAT_ARRAY;
    }
    final float[] result = new float[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Float b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将对象 Boolean 数组转换为原始类型。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Boolean} 数组，可能为 {@code null}
   * @return 一个 {@code boolean} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws NullPointerException
   *     如果数组内容为 {@code null}
   */
  public static boolean[] toPrimitive(@Nullable final Boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] result = new boolean[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将对象 Boolean 数组转换为原始类型，处理 {@code null}。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code Boolean} 数组，可能为 {@code null}
   * @param valueForNull
   *     如果找到 {@code null}，则插入的值
   * @return 一个 {@code boolean} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static boolean[] toPrimitive(@Nullable final Boolean[] array,
      final boolean valueForNull) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BOOLEAN_ARRAY;
    }
    final boolean[] result = new boolean[array.length];
    for (int i = 0; i < array.length; ++i) {
      final Boolean b = array[i];
      result[i] = (b == null ? valueForNull : b);
    }
    return result;
  }

  /**
   * 将原始 long 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code long} 数组
   * @return 一个 {@code Long} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Long[] toObject(@Nullable final long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_LONG_OBJECT_ARRAY;
    }
    final Long[] result = new Long[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 char 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code char} 数组
   * @return 一个 {@code Character} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Character[] toObject(@Nullable final char[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CHARACTER_OBJECT_ARRAY;
    }
    final Character[] result = new Character[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 int 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code int} 数组
   * @return 一个 {@code Integer} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Integer[] toObject(@Nullable final int[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_INTEGER_OBJECT_ARRAY;
    }
    final Integer[] result = new Integer[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 byte 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code byte} 数组
   * @return 一个 {@code Byte} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Byte[] toObject(@Nullable final byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BYTE_OBJECT_ARRAY;
    }
    final Byte[] result = new Byte[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 short 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code short} 数组
   * @return 一个 {@code Short} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Short[] toObject(@Nullable final short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_SHORT_OBJECT_ARRAY;
    }
    final Short[] result = new Short[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 double 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code double} 数组
   * @return 一个 {@code Double} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Double[] toObject(@Nullable final double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_DOUBLE_OBJECT_ARRAY;
    }
    final Double[] result = new Double[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 float 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code float} 数组
   * @return 一个 {@code Float} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Float[] toObject(@Nullable final float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_FLOAT_OBJECT_ARRAY;
    }
    final Float[] result = new Float[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = array[i];
    }
    return result;
  }

  /**
   * 将原始 boolean 数组转换为对象。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * @param array
   *     一个 {@code boolean} 数组
   * @return 一个 {@code Boolean} 数组，如果输入数组为 {@code null}，则返回 {@code null}
   */
  public static Boolean[] toObject(@Nullable final boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_BOOLEAN_OBJECT_ARRAY;
    }
    final Boolean[] result = new Boolean[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
    }
    return result;
  }

  /**
   * 将基本类型数组转换为对象数组。
   *
   * <p>如果输入数组为 {@code null}，此方法返回 {@code null}。
   *
   * <p>此方法可接受以下类型的基本类型数组：boolean[]、char[]、byte[]、short[]、
   * int[]、long[]、float[] 和 double[]，并将其转换为对应的对象数组。
   *
   * @param array
   *     要转换的基本类型数组，可能为 {@code null}
   * @return 转换后的对象数组，如果输入数组为 {@code null}，则返回 {@code null}
   * @throws IllegalArgumentException
   *     如果输入不是基本类型数组
   */
  public static Object[] toObject(@Nullable final Object array) {
    if (array == null) {
      return null;
    } else if (array instanceof boolean[]) {
      return toObject((boolean[]) array);
    } else if (array instanceof char[]) {
      return toObject((char[]) array);
    } else if (array instanceof byte[]) {
      return toObject((byte[]) array);
    } else if (array instanceof short[]) {
      return toObject((short[]) array);
    } else if (array instanceof int[]) {
      return toObject((int[]) array);
    } else if (array instanceof long[]) {
      return toObject((long[]) array);
    } else if (array instanceof float[]) {
      return toObject((float[]) array);
    } else if (array instanceof double[]) {
      return toObject((double[]) array);
    } else {
      throw new IllegalArgumentException(
          "The argument is not a primitive array:" + array.getClass()
                                                          .getName());
    }
  }

  /**
   * 检查对象数组是否为空或 {@code null}。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static <T> boolean isEmpty(@Nullable final T[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 long 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final long[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 int 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final int[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 short 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final short[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 char 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final char[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 byte 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final byte[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 double 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final double[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 float 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final float[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查原始 boolean 数组是否为空或 {@code null}。
   *
   * @param array
   *     要测试的数组
   * @return 如果数组为空或 {@code null}，则返回 {@code true}
   */
  public static boolean isEmpty(@Nullable final boolean[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * 检查对象是否为空数组或 {@code null}。
   *
   * @param obj
   *     要测试的对象。
   * @return 如果对象是空数组或 {@code null}，则返回 {@code true}。
   */
  public static boolean isEmpty(@Nullable final Object obj) {
    if (obj == null) {
      return true;
    } else if (!obj.getClass().isArray()) {
      return false;
    } else {
      if (obj instanceof boolean[]) {
        return (((boolean[]) obj).length == 0);
      }
      if (obj instanceof char[]) {
        return (((char[]) obj).length == 0);
      }
      if (obj instanceof byte[]) {
        return (((byte[]) obj).length == 0);
      }
      if (obj instanceof short[]) {
        return (((short[]) obj).length == 0);
      }
      if (obj instanceof int[]) {
        return (((int[]) obj).length == 0);
      }
      if (obj instanceof long[]) {
        return (((long[]) obj).length == 0);
      }
      if (obj instanceof float[]) {
        return (((float[]) obj).length == 0);
      }
      if (obj instanceof double[]) {
        return (((double[]) obj).length == 0);
      }
      return (((Object[]) obj).length == 0);
    }
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(null, null)     = null
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * Arrays.addAll([null], [null]) = [null, null]
   * Arrays.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
   * </pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array1
   *     其元素添加到新数组的第一个数组，可能为 {@code null}
   * @param array2
   *     其元素添加到新数组的第二个数组，可能为 {@code null}
   * @return
   *    新数组，如果 {@code null} 数组输入，则返回 {@code null}。新数组的类型是第一个数组
   *  的类型。
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] addAll(@Nullable final T[] array1,
      @Nullable final T[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final T[] joinedArray = (T[]) Array.newInstance(
        array1.getClass().getComponentType(), array1.length + array2.length);
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 boolean[] 数组。
   */
  public static boolean[] addAll(@Nullable final boolean[] array1,
      @Nullable final boolean[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final boolean[] joinedArray = new boolean[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 char[] 数组。
   */
  public static char[] addAll(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final char[] joinedArray = new char[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 byte[] 数组。
   */
  public static byte[] addAll(@Nullable final byte[] array1,
      @Nullable final byte[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final byte[] joinedArray = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 short[] 数组。
   */
  public static short[] addAll(@Nullable final short[] array1,
      @Nullable final short[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final short[] joinedArray = new short[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 int[] 数组。
   */
  public static int[] addAll(@Nullable final int[] array1,
      @Nullable final int[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final int[] joinedArray = new int[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 long[] 数组。
   */
  public static long[] addAll(@Nullable final long[] array1,
      @Nullable final long[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final long[] joinedArray = new long[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 float[] 数组。
   */
  public static float[] addAll(@Nullable final float[] array1,
      @Nullable final float[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final float[] joinedArray = new float[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 将给定数组的所有元素添加到新数组中。新数组包含 {@code array1} 的所有元素，后跟
   * {@code array2} 的所有元素。当返回数组时，它总是一个新数组。
   *
   * <pre>
   * Arrays.addAll(array1, null)   = array1 的克隆副本
   * Arrays.addAll(null, array2)   = array2 的克隆副本
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     其元素添加到新数组的第一个数组。
   * @param array2
   *     其元素添加到新数组的第二个数组。
   * @return 新的 double[] 数组。
   */
  public static double[] addAll(@Nullable final double[] array1,
      @Nullable final double[] array2) {
    if (array1 == null) {
      return Assignment.clone(array2);
    } else if (array2 == null) {
      return Assignment.clone(array1);
    }
    final double[] joinedArray = new double[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, null)      = [null]
   * Arrays.add(null, "a")       = ["a"]
   * Arrays.add(["a"], null)     = ["a", null]
   * Arrays.add(["a"], "b")      = ["a", "b"]
   * Arrays.add(["a", "b"], "c") = ["a", "b", "c"]
   * </pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] add(@Nullable final T[] array,
      @Nullable final T element) {
    final Class<?> type;
    if (array != null) {
      type = array.getClass().getComponentType();
    } else if (element != null) {
      type = element.getClass();
    } else {
      type = Object.class;
    }
    final T[] newArray = (T[]) copyArrayGrow(array, type);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, true)          = [true]
   * Arrays.add([true], false)       = [true, false]
   * Arrays.add([true, false], true) = [true, false, true]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static boolean[] add(@Nullable final boolean[] array,
      final boolean element) {
    final boolean[] newArray = (boolean[]) copyArrayGrow(array, Boolean.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static byte[] add(@Nullable final byte[] array, final byte element) {
    final byte[] newArray = (byte[]) copyArrayGrow(array, Byte.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, '0')       = ['0']
   * Arrays.add(['1'], '0')      = ['1', '0']
   * Arrays.add(['1', '0'], '1') = ['1', '0', '1']
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static char[] add(@Nullable final char[] array, final char element) {
    final char[] newArray = (char[]) copyArrayGrow(array, Character.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static double[] add(@Nullable final double[] array,
      final double element) {
    final double[] newArray = (double[]) copyArrayGrow(array, Double.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static float[] add(@Nullable final float[] array,
      final float element) {
    final float[] newArray = (float[]) copyArrayGrow(array, Float.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static int[] add(@Nullable final int[] array, final int element) {
    final int[] newArray = (int[]) copyArrayGrow(array, Integer.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static long[] add(@Nullable final long[] array, final long element) {
    final long[] newArray = (long[]) copyArrayGrow(array, Long.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 复制给定数组并在新数组的末尾添加给定元素。
   *
   * <p>新数组包含与输入数组相同的元素，并在最后一个位置添加给定元素。新数组的组件类型与输入数组相同。
   *
   * <p>如果输入数组为 {@code null}，则返回一个新的单元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     要复制并添加元素的数组，可能为 {@code null}
   * @param element
   *     要添加到新数组最后一个位置的对象
   * @return 包含现有元素和新元素的新数组
   */
  public static short[] add(@Nullable final short[] array,
      final short element) {
    final short[] newArray = (short[]) copyArrayGrow(array, Short.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0, null)      = [null]
   * Arrays.add(null, 0, "a")       = ["a"]
   * Arrays.add(["a"], 1, null)     = ["a", null]
   * Arrays.add(["a"], 1, "b")      = ["a", "b"]
   * Arrays.add(["a", "b"], 3, "c") = ["a", "b", "c"]
   * </pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] add(@Nullable final T[] array, final int index,
      @Nullable final T element) {
    final Class<?> cls;
    if (array != null) {
      cls = array.getClass().getComponentType();
    } else if (element != null) {
      // FIXME: can we use the element.getClass() as the type of returned array?
      // For examle, assume array is of type Object[] and its value is null,
      // assume element is a string "a", then calling this function with the
      // argument of array and element, what should be the type of returned
      // array? An array of Object, or an array of String?
      //
      cls = element.getClass();
    } else {
      cls = Object.class;
    }
    return (T[]) add(array, index, element, cls);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0, true)          = [true]
   * Arrays.add([true], 0, false)       = [false, true]
   * Arrays.add([false], 1, true)       = [false, true]
   * Arrays.add([true, false], 1, true) = [true, true, false]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static boolean[] add(@Nullable final boolean[] array, final int index,
      final boolean element) {
    return (boolean[]) add(array, index,
        (element ? Boolean.TRUE : Boolean.FALSE), Boolean.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add(null, 0, 'a')            = ['a']
   * Arrays.add(['a'], 0, 'b')           = ['b', 'a']
   * Arrays.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
   * Arrays.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
   * Arrays.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static char[] add(@Nullable final char[] array, final int index,
      final char element) {
    return (char[]) add(array, index, element, Character.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 3)      = [2, 6, 3]
   * Arrays.add([2, 6], 0, 1)      = [1, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static byte[] add(@Nullable final byte[] array, final int index,
      final byte element) {
    return (byte[]) add(array, index, element, Byte.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 10)     = [2, 6, 10]
   * Arrays.add([2, 6], 0, -4)     = [-4, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static short[] add(@Nullable final short[] array, final int index,
      final short element) {
    return (short[]) add(array, index, element, Short.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 10)     = [2, 6, 10]
   * Arrays.add([2, 6], 0, -4)     = [-4, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static int[] add(@Nullable final int[] array, final int index,
      final int element) {
    return (int[]) add(array, index, element, Integer.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add([1L], 0, 2L)           = [2L, 1L]
   * Arrays.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
   * Arrays.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
   * Arrays.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static long[] add(@Nullable final long[] array, final int index,
      final long element) {
    return (long[]) add(array, index, element, Long.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
   * Arrays.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
   * Arrays.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
   * Arrays.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static float[] add(@Nullable final float[] array, final int index,
      final float element) {
    return (float[]) add(array, index, element, Float.TYPE);
  }

  /**
   * 在指定位置插入指定元素到数组中。
   * 将当前位置的元素（如果有）和任何后续元素向右移动（将它们的索引加一）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 加上指定位置的给定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会创建一个新的一元素数组，其组件类型与元素相同。
   *
   * <pre>
   * Arrays.add([1.1], 0, 2.2)              = [2.2, 1.1]
   * Arrays.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
   * Arrays.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
   * Arrays.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
   * </pre>
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @return 包含现有元素和新元素的新数组
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &gt;
   *     array.length).
   */
  public static double[] add(@Nullable final double[] array, final int index,
      final double element) {
    return (double[]) add(array, index, element, Double.TYPE);
  }

  /**
   * 添加(array, current, element) 方法的底层实现。
   * 最后一个参数是类，可能不等于 element.getClass() 用于原始类型。
   *
   * @param array
   *     要添加元素的数组，可能为 {@code null}
   * @param index
   *     新对象的位置
   * @param element
   *     要添加的对象
   * @param cls
   *     正在添加的元素的类型
   * @return 包含现有元素和新元素的新数组
   */
  private static Object add(@Nullable final Object array, final int index,
      @Nullable final Object element, final Class<?> cls) {
    if (array == null) {
      if (index != 0) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
      }
      final Object joinedArray = Array.newInstance(cls, 1);
      Array.set(joinedArray, 0, element);
      return joinedArray;
    }
    final int length = Array.getLength(array);
    if ((index > length) || (index < 0)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Length: " + length);
    }
    final Object result = Array.newInstance(cls, length + 1);
    System.arraycopy(array, 0, result, 0, index);
    Array.set(result, index, element);
    if (index < length) {
      System.arraycopy(array, index, result, index + 1, length - index);
    }
    return result;
  }

  /**
   * 返回一个比给定数组大一的副本。新数组的最后一个值保留为默认值。
   *
   * @param array
   *     要复制的数组，不能为 {@code null}。
   * @param newArrayComponentType
   *     如果 {@code array} 为 {@code null}，则创建一个大小为 1 的数组的此类型。
   * @return 一个新的副本，比输入数组大一。
   */
  private static Object copyArrayGrow(@Nullable final Object array,
      final Class<?> newArrayComponentType) {
    if (array != null) {
      final int arrayLength = Array.getLength(array);
      final Class<?> componentType = array.getClass().getComponentType();
      final Object newArray = Array.newInstance(componentType, arrayLength + 1);
      System.arraycopy(array, 0, newArray, 0, arrayLength);
      return newArray;
    }
    return Array.newInstance(newArrayComponentType, 1);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove(["a"], 0)           = []
   * Arrays.remove(["a", "b"], 0)      = ["b"]
   * Arrays.remove(["a", "b"], 1)      = ["a"]
   * Arrays.remove(["a", "b", "c"], 1) = ["a", "c"]
   * </pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] remove(@Nullable final T[] array, final int index) {
    final Object obj = array;
    return (T[]) remove(obj, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([true], 0)              = []
   * Arrays.remove([true, false], 0)       = [false]
   * Arrays.remove([true, false], 1)       = [true]
   * Arrays.remove([true, true, false], 1) = [true, false]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static boolean[] remove(@Nullable final boolean[] array,
      final int index) {
    return (boolean[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([1], 0)          = []
   * Arrays.remove([1, 0], 0)       = [0]
   * Arrays.remove([1, 0], 1)       = [1]
   * Arrays.remove([1, 0, 1], 1)    = [1, 1]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static byte[] remove(@Nullable final byte[] array, final int index) {
    return (byte[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove(['a'], 0)           = []
   * Arrays.remove(['a', 'b'], 0)      = ['b']
   * Arrays.remove(['a', 'b'], 1)      = ['a']
   * Arrays.remove(['a', 'b', 'c'], 1) = ['a', 'c']
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static char[] remove(@Nullable final char[] array, final int index) {
    return (char[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([1.1], 0)           = []
   * Arrays.remove([2.5, 6.0], 0)      = [6.0]
   * Arrays.remove([2.5, 6.0], 1)      = [2.5]
   * Arrays.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static double[] remove(@Nullable final double[] array,
      final int index) {
    return (double[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([1.1], 0)           = []
   * Arrays.remove([2.5, 6.0], 0)      = [6.0]
   * Arrays.remove([2.5, 6.0], 1)      = [2.5]
   * Arrays.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static float[] remove(@Nullable final float[] array, final int index) {
    return (float[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static int[] remove(@Nullable final int[] array, final int index) {
    return (int[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static long[] remove(@Nullable final long[] array, final int index) {
    return (long[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  private static Object remove(@Nullable final Object array, final int index) {
    final int length = getLength(array);
    if ((index < 0) || (index >= length)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Length: " + length);
    }
    final Class<?> componentType = array.getClass().getComponentType();
    final Object result = Array.newInstance(componentType, length - 1);
    System.arraycopy(array, 0, result, 0, index);
    if (index < (length - 1)) {
      System.arraycopy(array, index + 1, result, index, length - index - 1);
    }
    return result;
  }

  /**
   * 从指定数组中删除指定位置的元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了指定位置的元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <p>如果输入数组为 {@code null}，则会抛出 IndexOutOfBoundsException，
   * 因为在这种情况下无法指定有效的当前。
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，不能为 {@code null}
   * @param index
   *     要删除的元素的位置
   * @return 包含现有元素的新数组，但不包含指定位置的元素。
   * @throws IndexOutOfBoundsException
   *     如果当前超出范围（current &lt; 0 || current &ge; array.length），或者如果数组为 {@code null}。
   */
  public static short[] remove(@Nullable final short[] array, final int index) {
    return (short[]) remove((Object) array, index);
  }

  /**
   * 从指定数组中删除第一次出现的指定元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   * 如果数组不包含这样的元素，则不会从数组中删除任何元素。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了第一次出现的指定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <pre>
   * Arrays.removeElement(null, "a")            = null
   * Arrays.removeElement([], "a")              = []
   * Arrays.removeElement(["a"], "b")           = ["a"]
   * Arrays.removeElement(["a", "b"], "a")      = ["b"]
   * Arrays.removeElement(["a", "b", "a"], "a") = ["b", "a"]
   * </pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要从中删除元素的数组，可能为 {@code null}
   * @param element
   *     要删除的元素
   * @return 包含现有元素的新数组，但不包含第一次出现的指定元素。
   */
  public static <T> T[] removeElement(@Nullable final T[] array,
      final T element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * 从指定数组中删除第一次出现的指定元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   * 如果数组不包含这样的元素，则不会从数组中删除任何元素。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了第一次出现的指定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <pre>
   * Arrays.removeElement(null, true)                = null
   * Arrays.removeElement([], true)                  = []
   * Arrays.removeElement([true], false)             = [true]
   * Arrays.removeElement([true, false], false)      = [true]
   * Arrays.removeElement([true, false, true], true) = [false, true]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，可能为 {@code null}
   * @param element
   *     要删除的元素
   * @return 包含现有元素的新数组，但不包含第一次出现的指定元素。
   */
  public static boolean[] removeElement(@Nullable final boolean[] array,
      final boolean element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * 从指定数组中删除第一次出现的指定元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   * 如果数组不包含这样的元素，则不会从数组中删除任何元素。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了第一次出现的指定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <pre>
   * Arrays.removeElement(null, 1)        = null
   * Arrays.removeElement([], 1)          = []
   * Arrays.removeElement([1], 0)         = [1]
   * Arrays.removeElement([1, 0], 0)      = [1]
   * Arrays.removeElement([1, 0, 1], 1)   = [0, 1]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，可能为 {@code null}
   * @param element
   *     要删除的元素
   * @return 包含现有元素的新数组，但不包含第一次出现的指定元素。
   */
  public static byte[] removeElement(@Nullable final byte[] array,
      final byte element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * 从指定数组中删除第一次出现的指定元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   * 如果数组不包含这样的元素，则不会从数组中删除任何元素。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了第一次出现的指定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <pre>
   * Arrays.removeElement(null, 'a')            = null
   * Arrays.removeElement([], 'a')              = []
   * Arrays.removeElement(['a'], 'b')           = ['a']
   * Arrays.removeElement(['a', 'b'], 'a')      = ['b']
   * Arrays.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，可能为 {@code null}
   * @param element
   *     要删除的元素
   * @return 包含现有元素的新数组，但不包含第一次出现的指定元素。
   */
  public static char[] removeElement(@Nullable final char[] array,
      final char element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * 从指定数组中删除第一次出现的指定元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   * 如果数组不包含这样的元素，则不会从数组中删除任何元素。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了第一次出现的指定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <pre>
   * Arrays.removeElement(null, 1.1)            = null
   * Arrays.removeElement([], 1.1)              = []
   * Arrays.removeElement([1.1], 1.2)           = [1.1]
   * Arrays.removeElement([1.1, 2.3], 1.1)      = [2.3]
   * Arrays.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，可能为 {@code null}
   * @param element
   *     要删除的元素
   * @return 包含现有元素的新数组，但不包含第一次出现的指定元素。
   */
  public static double[] removeElement(@Nullable final double[] array,
      final double element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * 从指定数组中删除第一次出现的指定元素。所有后续元素都向左移动（从它们的索引中减去 1）。
   * 如果数组不包含这样的元素，则不会从数组中删除任何元素。
   *
   * <p>此方法返回一个新数组，其中包含输入数组的所有元素，
   * 除了第一次出现的指定元素。返回数组的组件类型始终与输入数组的组件类型相同。
   *
   * <pre>
   * Arrays.removeElement(null, 1.1)            = null
   * Arrays.removeElement([], 1.1)              = []
   * Arrays.removeElement([1.1], 1.2)           = [1.1]
   * Arrays.removeElement([1.1, 2.3], 1.1)      = [2.3]
   * Arrays.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
   * </pre>
   *
   * @param array
   *     要从中删除元素的数组，可能为 {@code null}
   * @param element
   *     要删除的元素
   * @return 包含现有元素的新数组，但不包含第一次出现的指定元素。
   */
  public static float[] removeElement(@Nullable final float[] array,
      final float element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)      = null
   * Arrays.removeElement([], 1)        = []
   * Arrays.removeElement([1], 2)       = [1]
   * Arrays.removeElement([1, 3], 1)    = [3]
   * Arrays.removeElement([1, 3, 1], 1) = [3, 1]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
   */
  public static int[] removeElement(@Nullable final int[] array,
      final int element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)      = null
   * Arrays.removeElement([], 1)        = []
   * Arrays.removeElement([1], 2)       = [1]
   * Arrays.removeElement([1, 3], 1)    = [3]
   * Arrays.removeElement([1, 3, 1], 1) = [3, 1]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
   */
  public static long[] removeElement(@Nullable final long[] array,
      final long element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the first occurrence of the specified element. The component type of
   * the returned array is always the same as that of the input array.
   *
   * <pre>
   * Arrays.removeElement(null, 1)      = null
   * Arrays.removeElement([], 1)        = []
   * Arrays.removeElement([1], 2)       = [1]
   * Arrays.removeElement([1, 3], 1)    = [3]
   * Arrays.removeElement([1, 3, 1], 1) = [3, 1]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
   */
  public static short[] removeElement(@Nullable final short[] array,
      final short element) {
    final int index = indexOf(array, element);
    if (index == INDEX_NOT_FOUND) {
      return Assignment.clone(array);
    }
    return remove(array, index);
  }

  /**
   * 查找有序范围 [begin, end) 中第一个不小于指定值的元素的索引。
   *
   * <p>为了使函数产生预期结果，范围内的元素应该已经按照从低到高的顺序排序。
   *
   * <p>注意，如果范围内有等于指定值的元素，此函数返回第一个这样的元素的索引；
   * 否则，此函数返回第一个大于指定值的最小元素的索引。
   *
   * <p>函数名称 {@code lowerBound} 来源于这样一个事实：如果在 [begin, end) 中存在
   * 连续范围 [iter1, iter2)，使得
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; 且</li>
   * <li>对于 [begin, iter1) 中的每个 i，array[i] &lt; value; 且</li>
   * <li>对于 [iter1, iter2) 中的每个 i，array[i] == value; 且</li>
   * <li>对于 [iter2, last) 中的每个 i，array[i] &gt; value.</li>
   * </ul>
   *
   * <p>那么函数 {@code lowerBound(array, begin, end, value)} 将返回 {@code iter1}，
   * 它是有序范围中等于 value 的最大连续范围的“下界”。如果不存在这样的连续范围，
   * 即 [begin, end) 中没有元素等于 value，则函数将返回 [begin, end) 中大于 value 的
   * 最小元素的位置。
   *
   * <p>该函数使用二分查找算法来搜索下界，其时间复杂度为 {@code O(log n)}。
   *
   * @param array
   *     一个 {@code char} 数组，按照从低到高排序。
   * @param begin
   *     开始索引。
   * @param end
   *     结束索引。数组的范围定义为 [begin, end)。
   * @param value
   *     要查找的值。
   * @return [begin, end) 中第一个不小于指定值的元素的索引。如果范围 [begin, end) 没有
   *     正确排序，则此函数的行为未定义。
   * @throws IllegalArgumentException
   *     如果 {@code begin &lt; 0} 或 {@code begin &gt; end} 或
   *     {@code end &gt; array.length}。
   */
  public static int lowerBound(final char[] array, final int begin,
      final int end, final char value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function returns the current of the first such element; otherwise,
   * this function returns the current of the first smallest element which is
   * greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code byte} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int lowerBound(final byte[] array, final int begin,
      final int end, final byte value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function returns the current of the first such element; otherwise,
   * this function returns the current of the first smallest element which is
   * greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code short} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int lowerBound(final short[] array, final int begin,
      final int end, final short value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function returns the current of the first such element; otherwise,
   * this function returns the current of the first smallest element which is
   * greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code int} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int lowerBound(final int[] array, final int begin,
      final int end, final int value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function returns the current of the first such element; otherwise,
   * this function returns the current of the first smallest element which is
   * greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code long} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int lowerBound(final long[] array, final int begin,
      final int end, final long value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range, this function returns the current of the first such element;
   * otherwise, this function returns the current of the first smallest element
   * which is greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code float} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int lowerBound(final float[] array, final int begin,
      final int end, final float value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function returns the current of the first such element; otherwise,
   * this function returns the current of the first smallest element which is
   * greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code double} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int lowerBound(final double[] array, final int begin,
      final int end, final double value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (array[middle] < value) {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which does not compare less than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function returns the current of the first such element; otherwise,
   * this function returns the current of the first smallest element which is
   * greater than the specified value.
   *
   * <p>The function name {@code lowerBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code lowerBound(array, begin, end, value)} will
   * return {@code iter1}, which is the "lower bound" of the maximum
   * continuous range equals to value in the sorted range. If no such continuous
   * range exists, that is, no elements in [begin, end) equals to value, the
   * function will returns the position of the smallest element in [begin, end)
   * which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the lower bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     a {@code Comparable} objects array, sorted from the lower to the
   *     higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which does not
   *     compare less than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static <T extends Comparable<T>> int lowerBound(final T[] array,
      final int begin, final int end, final T value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value.compareTo(array[middle]) > 0) { // array[middle] < value
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      } else {
        n = half;
      }
    }
    return index;
  }

  /**
   * 查找有序范围 [begin, end) 中第一个大于指定值的元素的索引。
   *
   * <p>为了使函数产生预期结果，范围内的元素应该已经按照从低到高的顺序排序。
   *
   * <p>注意，如果范围内有等于指定值的元素，此函数不会返回这样元素的索引；
   * 相反，它返回第一个严格大于指定值的最小元素的索引。
   *
   * <p>函数名称 {@code upperBound} 来源于这样一个事实：如果在 [begin, end) 中存在
   * 连续范围 [iter1, iter2)，使得
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; 且</li>
   * <li>对于 [begin, iter1) 中的每个 i，array[i] &lt; value; 且</li>
   * <li>对于 [iter1, iter2) 中的每个 i，array[i] == value; 且</li>
   * <li>对于 [iter2, last) 中的每个 i，array[i] &gt; value.</li>
   * </ul>
   *
   * <p>那么函数 {@code upperBound(array, begin, end, value)} 将返回 {@code iter2}，
   * 它是有序范围中等于指定值的最大连续范围的“上界”。如果不存在这样的连续范围，
   * 即 [begin, end) 中没有元素等于指定值，则函数将返回 [begin, end) 中大于 value 的
   * 最小元素的索引。
   *
   * <p>该函数使用二分查找算法来搜索上界，其时间复杂度为 {@code O(log n)}。
   *
   * @param array
   *     一个 {@code char} 数组，按照从低到高排序。
   * @param begin
   *     开始索引。
   * @param end
   *     结束索引。数组的范围定义为 [begin, end)。
   * @param value
   *     要查找的值。
   * @return [begin, end) 中第一个大于指定值的元素的索引。如果范围 [begin, end) 没有
   *     正确排序，则此函数的行为未定义。
   * @throws IllegalArgumentException
   *     如果 {@code begin &lt; 0} 或 {@code begin &gt; end} 或
   *     {@code end &gt; array.length}。
   */
  public static int upperBound(final char[] array, final int begin,
      final int end, final char value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>那么函数 {@code upperBound(array, begin, end, value)} 将返回 {@code iter2}，
   * 它是有序范围中等于指定值的最大连续范围的“上界”。如果不存在这样的连续范围，
   * 即 [begin, end) 中没有元素等于指定值，则函数将返回 [begin, end) 中大于 value 的
   * 最小元素的索引。
   *
   * <p>该函数使用二分查找算法来搜索上界，其时间复杂度为 {@code O(log n)}。
   *
   * @param array
   *     一个 {@code byte} 数组，按照从低到高排序。
   * @param begin
   *     开始索引。
   * @param end
   *     结束索引。数组的范围定义为 [begin, end)。
   * @param value
   *     要查找的值。
   * @return [begin, end) 中第一个大于指定值的元素的索引。如果范围 [begin, end) 没有
   *     正确排序，则此函数的行为未定义。
   * @throws IllegalArgumentException
   *     如果 {@code begin &lt; 0} 或 {@code begin &gt; end} 或
   *     {@code end &gt; array.length}。
   */
  public static int upperBound(final byte[] array, final int begin,
      final int end, final byte value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * 查找有序范围 [begin, end) 中第一个大于指定值的元素的索引。
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code short} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *     greater than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int upperBound(final short[] array, final int begin,
      final int end, final short value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code int} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *     greater than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int upperBound(final int[] array, final int begin,
      final int end, final int value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code long} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *     greater than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int upperBound(final long[] array, final int begin,
      final int end, final long value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code float} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *     greater than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int upperBound(final float[] array, final int begin,
      final int end, final float value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range
   * shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range,
   * this function does NOT return the current of such element; instead, it
   * returns the current of the first smallest element which is strictly greater
   * than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a
   * continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param array
   *     a {@code double} array, sorted from the lower to the higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *     greater than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static int upperBound(final double[] array, final int begin,
      final int end, final double value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value < array[middle]) {
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * Find the current of the first element in the sorted range [begin, end) of a
   * specified array which compares greater than a specified value.
   *
   * <p>For the function to yield the expected result, the elements in the
   * range shall already be ordered from the lower to the higher.
   *
   * <p>Note that if there is an element equal to the specified value in the
   * range, this function does NOT return the current of such element; instead,
   * it returns the current of the first smallest element which is strictly
   * greater than the specified value.
   *
   * <p>The function name {@code upperBound} comes from the fact that if there
   * is a continuous range [iter1, iter2) in [begin, end), such that
   *
   * <ul>
   * <li>begin &lt;= iter1 &lt; iter2 &lt;= end; and</li>
   * <li>for every i in [begin, iter1), array[i] &lt; value; and</li>
   * <li>for every i in [iter1, iter2), array[i] == value; and</li>
   * <li>for every i in [iter2, last), array[i] &gt; value.</li>
   * </ul>
   *
   * <p>Then the function {@code upperBound(array, begin, end, value)} will
   * return {@code iter2}, which is the "upper bound" of the maximum
   * continuous range equals to the specified value in the sorted range. If no
   * such continuous range exists, that is, no elements in [begin, end) equals
   * to the specified value, the function will returns the current of the smallest
   * element in [begin, end) which is greater than value.
   *
   * <p>The function use a binary search algorithm to search the upper bound, which
   * has a time complexity of {@code O(log n)}.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     a {@code Comparable} objects array, sorted from the lower to the
   *     higher.
   * @param begin
   *     the begin current.
   * @param end
   *     the end current. The range of the array is defined as [begin, end).
   * @param value
   *     the value to be found.
   * @return the current of the first element in [begin, end) which compares
   *     greater than the specified value. If the range [begin, end) is not
   *     correctly sorted, the behavior of this function is not defined.
   * @throws IllegalArgumentException
   *     if {@code begin &lt; 0} or {@code begin &gt; end} or
   *     {@code end &gt; array.length}.
   */
  public static <T extends Comparable<T>> int upperBound(final T[] array,
      final int begin, final int end, final T value) {
    if ((begin < 0) || (begin > end) || (end > array.length)) {
      throw new IllegalArgumentException();
    }
    int n = end - begin;
    int index = begin;
    while (n > 0) {
      int half = n / 2;
      final int middle = index + half;
      if (value.compareTo(array[middle]) < 0) { // value < array[middle]
        // search in the lower half part
        n = half;
      } else {
        // search in the higher half part
        index = middle;
        ++index;
        // set n = n - half - 1
        ++half;
        n -= half;
      }
    }
    return index;
  }

  /**
   * 对数组中元素的值求和。
   *
   * @param array
   *     值的数组。
   * @return 数组中所有值的总和。
   */
  public static int sum(final byte[] array) {
    int result = 0;
    for (final byte v : array) {
      result += v;
    }
    return result;
  }

  /**
   * 对数组中元素的值求和。
   *
   * @param array
   *     值的数组。
   * @param start
   *     开始求和的值的索引。
   * @param end
   *     结束求和的值的下一个索引。
   * @return 数组中所有值的总和。
   */
  public static int sum(final byte[] array, final int start, final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    int result = 0;
    for (int i = theStart; i < theEnd; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * 对数组中元素的值求和。
   *
   * @param array
   *     值的数组。
   * @return 数组中所有值的总和。
   */
  public static int sum(final short[] array) {
    int result = 0;
    for (final short v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the sum of all values in the array.
   */
  public static int sum(final short[] array, final int start, final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    int result = 0;
    for (int i = theStart; i < theEnd; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the sum of all values in the array.
   */
  public static int sum(final int[] array) {
    int result = 0;
    for (final int v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the sum of all values in the array.
   */
  public static int sum(final int[] array, final int start, final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    int result = 0;
    for (int i = theStart; i < theEnd; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the sum of all values in the array.
   */
  public static long sum(final long[] array) {
    long result = 0;
    for (final long v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the sum of all values in the array.
   */
  public static long sum(final long[] array, final int start, final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    long result = 0;
    for (int i = theStart; i < theEnd; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the sum of all values in the array.
   */
  public static float sum(final float[] array) {
    float result = 0;
    for (final float v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the sum of all values in the array.
   */
  public static float sum(final float[] array, final int start, final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    float result = 0;
    for (int i = theStart; i < theEnd; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the sum of all values in the array.
   */
  public static double sum(final double[] array) {
    double result = 0;
    for (final double v : array) {
      result += v;
    }
    return result;
  }

  /**
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the sum of all values in the array.
   */
  public static double sum(final double[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    double result = 0;
    for (int i = theStart; i < theEnd; ++i) {
      result += array[i];
    }
    return result;
  }

  /**
   * 对数组中元素的值求积。
   *
   * @param array
   *     值的数组。
   * @return 数组中所有值的乘积。
   */
  public static double product(final byte[] array) {
    double result = 1;
    for (final byte v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * 对数组中元素的值求积。
   *
   * @param array
   *     值的数组。
   * @param start
   *     开始求积的值的索引。
   * @param end
   *     结束求积的值的下一个索引。
   * @return 数组中所有值的乘积。
   */
  public static double product(final byte[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    double result = 1;
    for (int i = theStart; i < theEnd; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the production of all values in the array.
   */
  public static double product(final short[] array) {
    double result = 1;
    for (final short v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the production of all values in the array.
   */
  public static double product(final short[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    double result = 1;
    for (int i = theStart; i < theEnd; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the production of all values in the array.
   */
  public static double product(final int[] array) {
    double result = 1;
    for (final int v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the production of all values in the array.
   */
  public static double product(final int[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    double result = 1;
    for (int i = theStart; i < theEnd; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the production of all values in the array.
   */
  public static double product(final long[] array) {
    double result = 1;
    for (final long v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the production of all values in the array.
   */
  public static double product(final long[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    double result = 1;
    for (int i = theStart; i < theEnd; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the production of all values in the array.
   */
  public static float product(final float[] array) {
    float result = 1;
    for (final float v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the production of all values in the array.
   */
  public static float product(final float[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    float result = 1;
    for (int i = theStart; i < theEnd; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the production of all values in the array.
   */
  public static double product(final double[] array) {
    double result = 1;
    for (final double v : array) {
      result *= v;
    }
    return result;
  }

  /**
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @param start
   *     the index of value starting the sum.
   * @param end
   *     the index next to the value ending the sum.
   * @return the production of all values in the array.
   */
  public static double product(final double[] array, final int start,
      final int end) {
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    double result = 1;
    for (int i = theStart; i < theEnd; ++i) {
      result *= array[i];
    }
    return result;
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要检查的数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static <T> T[] nullIfEmpty(@Nullable final T[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的布尔数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static boolean[] nullIfEmpty(@Nullable final boolean[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的字符数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static char[] nullIfEmpty(@Nullable final char[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的字节数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static byte[] nullIfEmpty(@Nullable final byte[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的短整型数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static short[] nullIfEmpty(@Nullable final short[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的整型数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static int[] nullIfEmpty(@Nullable final int[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的长整型数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static long[] nullIfEmpty(@Nullable final long[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的浮点数数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static float[] nullIfEmpty(@Nullable final float[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 如果数组为空或为 null，则返回 null，否则返回原数组。
   *
   * @param array
   *     要检查的双精度浮点数数组。
   * @return 如果数组为空或为 null，则返回 null，否则返回原数组。
   */
  public static double[] nullIfEmpty(@Nullable final double[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * 从指定数组中排除给定数组的所有元素。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     指定的数组。
   * @param toExclude
   *     包含要排除的元素的数组。
   * @return 一个新数组，其中只包含指定数组中的元素，但不包含被排除元素数组中的元素。
   */
  public static <T> T[] excludeAll(@Nullable final T[] array,
      final T[] toExclude) {
    if (array == null) {
      return null;
    }
    final List<T> list = new ArrayList<>();
    for (final T val : array) {
      if (indexOf(toExclude, val) == INDEX_NOT_FOUND) {
        list.add(val);
      }
    }
    final Class<?> componentType = array.getClass().getComponentType();
    final T[] result = createArrayOfSameElementType(array, list.size());
    return list.toArray(result);
  }

  /**
   * 创建一个与现有数组具有相同元素类型的新数组。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     现有的数组。
   * @param n
   *     新数组的长度。
   * @return 一个与指定数组具有相同元素类型的新数组。
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] createArrayOfSameElementType(final T[] array,
      final int n) {
    final Class<?> componentType = array.getClass().getComponentType();
    final Object result = Array.newInstance(componentType, n);
    return (T[]) result;
  }

  /**
   * 创建一个具有指定元素类型和指定长度的新数组。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param elementType
   *     新数组的指定元素类型。
   * @param n
   *     新数组的长度。
   * @return 一个与指定数组具有相同元素类型的新数组。
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] createArray(final Class<T> elementType, final int n) {
    return (T[]) Array.newInstance(elementType, n);
  }

  /**
   * 从给定索引开始在数组中查找符合条件的对象的索引。
   *
   * <p>对于 {@code null} 输入数组，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的起始索引将被视为零。大于数组长度的起始索引将返回
   * {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索对象的数组，可能为 {@code null}
   * @param condition
   *     要查找的值应满足此条件。
   * @return 数组中指定对象的索引，如果未找到或输入数组为 {@code null}，
   *     则返回 {@link #INDEX_NOT_FOUND} ({@value #INDEX_NOT_FOUND})。
   */
  public static <T> int findIndex(@Nullable final T[] array,
      final Predicate<T> condition) {
    return findIndex(array, condition, 0);
  }

  /**
   * 从给定索引开始在数组中查找符合条件的对象的索引。
   *
   * <p>对于 {@code null} 输入数组，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的起始索引将被视为零。大于数组长度的起始索引将返回
   * {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索对象的数组，可能为 {@code null}
   * @param condition
   *     要查找的值应满足此条件。
   * @param start
   *     开始搜索的索引
   * @return 从指定位置开始的数组中指定对象的索引，如果未找到或输入数组为
   *     {@code null}，则返回 {@link #INDEX_NOT_FOUND} ({@value #INDEX_NOT_FOUND})。
   */
  public static <T> int findIndex(@Nullable final T[] array,
      final Predicate<T> condition, final int start) {
    if (array == null) {
      return INDEX_NOT_FOUND;
    }
    final int theStart = Math.max(start, 0);
    for (int i = theStart; i < array.length; ++i) {
      if (condition.test(array[i])) {
        return i;
      }
    }
    return INDEX_NOT_FOUND;
  }

  /**
   * 从给定索引开始在数组中查找符合条件的对象。
   *
   * <p>对于 {@code null} 输入数组，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的起始索引将被视为零。大于数组长度的起始索引将返回
   * {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索对象的数组，可能为 {@code null}
   * @param condition
   *     要查找的值应满足此条件。
   * @return 数组中的指定对象，如果未找到或输入数组为 {@code null}，则返回
   *     {@code null}。
   */
  public static <T> T find(@Nullable final T[] array,
      final Predicate<T> condition) {
    return find(array, condition, 0);
  }

  /**
   * 从给定索引开始在数组中查找符合条件的对象。
   *
   * <p>对于 {@code null} 输入数组，此方法返回 {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * <p>负的起始索引将被视为零。大于数组长度的起始索引将返回
   * {@link #INDEX_NOT_FOUND} ({@code -1})。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要搜索对象的数组，可能为 {@code null}
   * @param condition
   *     要查找的值应满足此条件。
   * @param start
   *     开始搜索的索引
   * @return 从指定位置开始的数组中的指定对象，如果未找到或输入数组为
   *     {@code null}，则返回 {@code null}。
   */
  public static <T> T find(@Nullable final T[] array,
      final Predicate<T> condition, final int start) {
    if (array == null || array.length == 0) {
      return null;
    }
    final int index = findIndex(array, condition, start);
    if (index >= 0 && index < array.length) {
      return array[index];
    } else {
      return null;
    }
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的布尔数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static boolean[] nullToEmpty(@Nullable final boolean[] array) {
    return (array == null ? EMPTY_BOOLEAN_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的字符数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static char[] nullToEmpty(@Nullable final char[] array) {
    return (array == null ? EMPTY_CHAR_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的字节数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static byte[] nullToEmpty(@Nullable final byte[] array) {
    return (array == null ? EMPTY_BYTE_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的短整型数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static short[] nullToEmpty(@Nullable final short[] array) {
    return (array == null ? EMPTY_SHORT_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的整型数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static int[] nullToEmpty(@Nullable final int[] array) {
    return (array == null ? EMPTY_INT_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的长整型数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static long[] nullToEmpty(@Nullable final long[] array) {
    return (array == null ? EMPTY_LONG_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的浮点数数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static float[] nullToEmpty(@Nullable final float[] array) {
    return (array == null ? EMPTY_FLOAT_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的双精度浮点数数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static double[] nullToEmpty(@Nullable final double[] array) {
    return (array == null ? EMPTY_DOUBLE_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的字符序列数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static CharSequence[] nullToEmpty(@Nullable final CharSequence[] array) {
    return (array == null ? EMPTY_STRING_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param array
   *     要检查的字符串数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  public static String[] nullToEmpty(@Nullable final String[] array) {
    return (array == null ? EMPTY_STRING_ARRAY : array);
  }

  /**
   * 如果数组为 null，则返回空数组，否则返回原数组。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要检查的数组。
   * @return 如果数组为 null，则返回空数组，否则返回原数组。
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] nullToEmpty(@Nullable final T[] array) {
    return (array == null ? (T[]) EMPTY_OBJECT_ARRAY : array);
  }

  /**
   * 返回数组的流，如果数组为 {@code null}，则返回空流。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要转换为流的数组，可能为 {@code null}
   * @return
   *     数组的流，如果数组为 {@code null}，则返回空流
   */
  public static <T> Stream<T> stream(@Nullable final T[] array) {
    return (array == null ? Stream.empty() : Arrays.stream(array));
  }

  /**
   * 对数组的每个元素执行给定的操作，直到所有元素都已处理或操作抛出
   * 异常。操作按数组的顺序执行。操作抛出的异常会传递给调用者。
   * <p>
   * 如果操作执行的副作用修改了元素的底层源，则此方法的行为是未指定的，
   * 除非覆盖类指定了并发修改策略。
   * <p>
   * 默认实现的行为如下：
   * <pre>{@code
   *    if (array != null) {
   *      for (final T value : array) {
   *        action.accept(value);
   *      }
   *    }
   * }</pre>
   *
   * @param <T>
   *     数组中元素的类型。
   * @param array
   *     要遍历的数组，可能为 {@code null}。
   * @param action
   *     要对每个元素执行的操作。
   * @throws NullPointerException
   *     如果指定的操作为 null。
   */
  public static <T> void forEach(@Nullable final T[] array, final Consumer<? super T> action) {
    if (array != null) {
      for (final T value : array) {
        action.accept(value);
      }
    }
  }

  /**
   * 返回一个新数组，其中包含指定对象的多个副本。
   * <p>
   * 此函数执行指定对象的浅复制。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param value
   *     要复制的对象。
   * @param n
   *     要创建的副本数量。
   * @return
   *     包含指定对象的 {@code n} 个副本的数组。
   */
  public static <T> T[] duplicate(final T value, final int n) {
    @SuppressWarnings("unchecked")
    final Class<T> elementType = (Class<T>) value.getClass();
    final T[] result = createArray(elementType, n);
    Arrays.fill(result, value);
    return result;
  }

  /**
   * 返回一个新数组，其中包含指定对象的深克隆副本。
   * <p>
   * 此函数执行指定对象的深克隆。
   *
   * @param <T>
   *     数组中元素的类型。
   * @param value
   *     要复制的对象。
   * @param n
   *     要创建的副本数量。
   * @return
   *     包含指定对象的 {@code n} 个深克隆副本的数组。
   */
  public static <T extends CloneableEx<T>> T[] deepDuplicate(final T value, final int n) {
    @SuppressWarnings("unchecked")
    final Class<T> elementType = (Class<T>) value.getClass();
    final T[] result = createArray(elementType, n);
    for (int i = 0; i < n; ++i) {
      result[i] = value.cloneEx();
    }
    return result;
  }
}