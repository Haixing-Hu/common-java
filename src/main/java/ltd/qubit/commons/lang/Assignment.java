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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * 用于帮助实现 Assignable 接口的工具函数。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class Assignment {

  /**
   * 克隆一个实现了 CloneableEx 接口的对象。
   *
   * @param <T>
   *     要克隆的对象的类型。
   * @param obj
   *     要克隆的对象，可以为null。
   * @return
   *     克隆的对象副本；如果源对象为null则返回null。
   */
  @SuppressWarnings("unchecked")
  public static <T extends CloneableEx<? super T>> T clone(@Nullable final T obj) {
    if (obj == null) {
      return null;
    } else {
      return (T) obj.cloneEx();
    }
  }

  /**
   * 克隆一个 Date 对象。
   *
   * @param value
   *     要克隆的 Date 对象，可以为null。
   * @return
   *     克隆的 Date 对象副本；如果源对象为null则返回null。
   */
  public static Date clone(@Nullable final Date value) {
    return (value == null ? null : (Date) value.clone());
  }

  /**
   * 克隆一个 Time 对象。
   *
   * @param value
   *     要克隆的 Time 对象，可以为null。
   * @return
   *     克隆的 Time 对象副本；如果源对象为null则返回null。
   */
  public static Time clone(@Nullable final Time value) {
    return (value == null ? null : (Time) value.clone());
  }

  /**
   * 克隆一个 Timestamp 对象。
   *
   * @param value
   *     要克隆的 Timestamp 对象，可以为null。
   * @return
   *     克隆的 Timestamp 对象副本；如果源对象为null则返回null。
   */
  public static Timestamp clone(@Nullable final Timestamp value) {
    return (value == null ? null : (Timestamp) value.clone());
  }

  /**
   * 克隆一个 char 数组。
   * <p>
   * {@link char} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 char 数组，可以为null。
   * @return
   *     克隆的 char 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static char[] clone(@Nullable final char[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      final char[] result = new char[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Character 数组。
   * <p>
   * {@link Character} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Character 数组，可以为null。
   * @return
   *     克隆的 Character 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Character[] clone(@Nullable final Character[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
    } else {
      final Character[] result = new Character[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 boolean 数组。
   * <p>
   * {@link boolean} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 boolean 数组，可以为null。
   * @return
   *     克隆的 boolean 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static boolean[] clone(@Nullable final boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else {
      final boolean[] result = new boolean[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Boolean 数组。
   * <p>
   * {@link Boolean} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Boolean 数组，可以为null。
   * @return
   *     克隆的 Boolean 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Boolean[] clone(@Nullable final Boolean[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
    } else {
      final Boolean[] result = new Boolean[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 byte 数组。
   * <p>
   * {@link byte} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 byte 数组，可以为null。
   * @return
   *     克隆的 byte 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static byte[] clone(@Nullable final byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      final byte[] result = new byte[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Byte 数组。
   * <p>
   * {@link Byte} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Byte 数组，可以为null。
   * @return
   *     克隆的 Byte 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Byte[] clone(@Nullable final Byte[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
    } else {
      final Byte[] result = new Byte[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 short 数组。
   * <p>
   * {@link short} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 short 数组，可以为null。
   * @return
   *     克隆的 short 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static short[] clone(@Nullable final short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      final short[] result = new short[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Short 数组。
   * <p>
   * {@link Short} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Short 数组，可以为null。
   * @return
   *     克隆的 Short 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Short[] clone(@Nullable final Short[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
    } else {
      final Short[] result = new Short[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 int 数组。
   * <p>
   * {@link int} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 int 数组，可以为null。
   * @return
   *     克隆的 int 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static int[] clone(@Nullable final int[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      final int[] result = new int[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Integer 数组。
   * <p>
   * {@link Integer} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Integer 数组，可以为null。
   * @return
   *     克隆的 Integer 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Integer[] clone(@Nullable final Integer[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
    } else {
      final Integer[] result = new Integer[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 long 数组。
   * <p>
   * {@link long} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 long 数组，可以为null。
   * @return
   *     克隆的 long 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static long[] clone(@Nullable final long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      final long[] result = new long[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Long 数组。
   * <p>
   * {@link Long} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Long 数组，可以为null。
   * @return
   *     克隆的 Long 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Long[] clone(@Nullable final Long[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
    } else {
      final Long[] result = new Long[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 float 数组。
   * <p>
   * {@link float} 是基本类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 float 数组，可以为null。
   * @return
   *     克隆的 float 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static float[] clone(@Nullable final float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else {
      final float[] result = new float[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Float 数组。
   * <p>
   * {@link Float} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Float 数组，可以为null。
   * @return
   *     克隆的 Float 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Float[] clone(@Nullable final Float[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
    } else {
      final Float[] result = new Float[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 double 数组。
   * <p>
   * {@link double} 是基本类型，因此浅克隆和深度克隆的效果相同。
   * 
   * @param array
   *     要克隆的 double 数组，可以为null。
   * @return
   *     克隆的 double 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static double[] clone(@Nullable final double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else {
      final double[] result = new double[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Double 数组。
   * <p>
   * {@link Double} 是不可变包装类型，因此浅克隆和深度克隆的效果相同。
   * 
   * @param array
   *     要克隆的 Double 数组，可以为null。
   * @return
   *     克隆的 Double 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Double[] clone(@Nullable final Double[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
    } else {
      final Double[] result = new Double[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 String 数组。
   * <p>
   * {@link String} 是不可变类，因此浅克隆和深度克隆的效果相同。
   * 
   * @param array
   *     要克隆的 String 数组，可以为null。
   * @return
   *     克隆的 String 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static String[] clone(@Nullable final String[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      final String[] result = new String[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 Class 数组。
   * <p>
   * {@link Class} 是不可变类，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 Class 数组，可以为null。
   * @return
   *     克隆的 Class 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static Class<?>[] clone(@Nullable final Class<?>[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    } else {
      final Class<?>[] result = new Class<?>[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个 Date 数组。
   *
   * @param array
   *     要浅克隆的 Date 数组，可以为null。
   * @return
   *     浅克隆的 Date 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(Date[])
   */
  public static Date[] clone(@Nullable final Date[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      final Date[] result = new Date[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个 Time 数组。
   *
   * @param array
   *     要浅克隆的 Time 数组，可以为null。
   * @return
   *     浅克隆的 Time 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(Time[])
   */
  public static Time[] clone(@Nullable final Time[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      final Time[] result = new Time[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个 Timestamp 数组。
   *
   * @param array
   *     要浅克隆的 Timestamp 数组，可以为null。
   * @return
   *     浅克隆的 Timestamp 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(Timestamp[])
   */
  public static Timestamp[] clone(@Nullable final Timestamp[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      final Timestamp[] result = new Timestamp[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 LocalDate 数组。
   * <p>
   * {@link LocalDate} 是不可变类，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 LocalDate 数组，可以为null。
   * @return
   *     克隆的 LocalDate 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static LocalDate[] clone(@Nullable final LocalDate[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATE_ARRAY;
    } else {
      final LocalDate[] result = new LocalDate[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 LocalTime 数组。
   * <p>
   * {@link LocalTime} 是不可变类，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 LocalTime 数组，可以为null。
   * @return
   *     克隆的 LocalTime 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static LocalTime[] clone(@Nullable final LocalTime[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LOCAL_TIME_ARRAY;
    } else {
      final LocalTime[] result = new LocalTime[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 LocalDateTime 数组。
   * <p>
   * {@link LocalDateTime} 是不可变类，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 LocalDateTime 数组，可以为null。
   * @return
   *     克隆的 LocalDateTime 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static LocalDateTime[] clone(@Nullable final LocalDateTime[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATETIME_ARRAY;
    } else {
      final LocalDateTime[] result = new LocalDateTime[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 BigInteger 数组。
   * <p>
   * {@link BigInteger} 是不可变类，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 BigInteger 数组，可以为null。
   * @return
   *     克隆的 BigInteger 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static BigInteger[] clone(@Nullable final BigInteger[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else {
      final BigInteger[] result = new BigInteger[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 克隆一个 BigDecimal 数组。
   * <p>
   * {@link BigDecimal} 是不可变类，因此浅克隆和深度克隆的效果相同。
   *
   * @param array
   *     要克隆的 BigDecimal 数组，可以为null。
   * @return
   *     克隆的 BigDecimal 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   */
  public static BigDecimal[] clone(@Nullable final BigDecimal[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else {
      final BigDecimal[] result = new BigDecimal[array.length];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个数组。
   *
   * <p>注意：参数类型 T 不必是可克隆的，因为这是浅克隆。
   *
   * @param <T>
   *     要克隆的数组元素的类型。
   * @param array
   *     要克隆的源数组，可以为null。
   * @return
   *     源数组的克隆副本；如果源数组为null则返回null。注意源数组中的对象
   *     只是简单地复制到返回的数组中，因此这是浅克隆。
   */
  public static <T> T[] clone(@Nullable final T[] array) {
    if (array == null) {
      return null;
    } else {
      return array.clone();
    }
  }

  /**
   * 浅克隆一个列表。
   *
   * @param <T>
   *     要克隆的列表元素的类型。
   * @param list
   *     要克隆的源列表，可以为null。
   * @return
   *     源列表的克隆副本；如果源列表为null则返回null。注意源列表中的对象
   *     只是简单地复制到返回的列表中，因此这是浅克隆。
   */
  public static <T> List<T> cloneList(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    } else {
      return new ArrayList<>(list);
    }
  }

  /**
   * 浅克隆一个集合。
   *
   * @param <T>
   *     要克隆的集合元素的类型。
   * @param set
   *     要克隆的源集合，可以为null。
   * @return
   *     源集合的克隆副本；如果源集合为null则返回null。注意源集合中的对象
   *     只是简单地复制到返回的集合中，因此这是浅克隆。
   */
  public static <T> Set<T> cloneSet(@Nullable final Set<T> set) {
    if (set == null) {
      return null;
    } else {
      return new HashSet<>(set);
    }
  }

  /**
   * 浅克隆一个映射。
   *
   * @param <K>
   *     要克隆的映射键的类型。
   * @param <V>
   *     要克隆的映射值的类型。
   * @param map
   *     要克隆的源映射，可以为null。
   * @return
   *     源映射的克隆副本；如果源映射为null则返回null。注意源映射中的对象
   *     只是简单地复制到返回的映射中，因此这是浅克隆。
   */
  public static <K, V> Map<K, V> cloneMap(@Nullable final Map<K, V> map) {
    if (map == null) {
      return null;
    } else {
      return new HashMap<>(map);
    }
  }

  /**
   * 浅克隆一个多值映射。
   *
   * @param <K>
   *     要克隆的多值映射键的类型。
   * @param <V>
   *     要克隆的多值映射值的类型。
   * @param map
   *     要克隆的源多值映射，可以为null。
   * @return
   *     源多值映射的克隆副本；如果源多值映射为null则返回null。注意源多值映射中的对象
   *     只是简单地复制到返回的多值映射中，因此这是浅克隆。
   */
  public static <K, V> Multimap<K, V> cloneMultimap(
      @Nullable final Multimap<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Multimap<K, V> result = LinkedHashMultimap.create();
      result.putAll(map);
        return result;
    }
  }

  /**
   * 浅克隆一个二维 char 数组。
   *
   * @param array
   *     要浅克隆的二维 char 数组，可以为null。
   * @return
   *     浅克隆的二维 char 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(char[][])
   */
  public static char[][] clone(@Nullable final char[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY_ARRAY;
    } else {
      final char[][] result = new char[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 boolean 数组。
   *
   * @param array
   *     要浅克隆的二维 boolean 数组，可以为null。
   * @return
   *     浅克隆的二维 boolean 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(boolean[][])
   */
  public static boolean[][] clone(@Nullable final boolean[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY_ARRAY;
    } else {
      final boolean[][] result = new boolean[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 byte 数组。
   *
   * @param array
   *     要浅克隆的二维 byte 数组，可以为null。
   * @return
   *     浅克隆的二维 byte 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(byte[][])
   */
  public static byte[][] clone(@Nullable final byte[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      final byte[][] result = new byte[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 short 数组。
   *
   * @param array
   *     要浅克隆的二维 short 数组，可以为null。
   * @return
   *     浅克隆的二维 short 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(short[][])
   */
  public static short[][] clone(@Nullable final short[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY_ARRAY;
    } else {
      final short[][] result = new short[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 int 数组。
   *
   * @param array
   *     要浅克隆的二维 int 数组，可以为null。
   * @return
   *     浅克隆的二维 int 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(int[][])
   */
  public static int[][] clone(@Nullable final int[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY_ARRAY;
    } else {
      final int[][] result = new int[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 long 数组。
   *
   * @param array
   *     要浅克隆的二维 long 数组，可以为null。
   * @return
   *     浅克隆的二维 long 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(long[][])
   */
  public static long[][] clone(@Nullable final long[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY_ARRAY;
    } else {
      final long[][] result = new long[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 float 数组。
   *
   * @param array
   *     要浅克隆的二维 float 数组，可以为null。
   * @return
   *     浅克隆的二维 byte 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(float[][])
   */
  public static float[][] clone(@Nullable final float[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY_ARRAY;
    } else {
      final float[][] result = new float[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 double 数组。
   *
   * @param array
   *     要浅克隆的二维 double 数组，可以为null。
   * @return
   *     浅克隆的二维 double 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(double[][])
   */
  public static double[][] clone(@Nullable final double[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY_ARRAY;
    } else {
      final double[][] result = new double[array.length][];
      System.arraycopy(array, 0, result, 0, array.length);
      return result;
    }
  }

  /**
   * 浅克隆一个二维 String 数组。
   * 
   * @param array2d
   *     要浅克隆的二维 String 数组，可以为null。
   * @return
   *     浅克隆的二维 String 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。
   * @see #deepClone(String[][])
   */
  public static String[][] clone(@Nullable final String[][] array2d) {
    if (array2d == null) {
      return null;
    } else if (array2d.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY_ARRAY;
    } else {
      final String[][] result = new String[array2d.length][];
      for (int i = 0; i < array2d.length; ++i) {
        result[i] = clone(array2d[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个 Date 数组。
   *
   * @param array
   *     要深度克隆的 Date 数组，可以为null。
   * @return
   *     深度克隆的 Date 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的对象也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static Date[] deepClone(@Nullable final Date[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
      } else {
      final Date[] result = new Date[array.length];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个 Time 数组。
   *
   * @param array
   *     要深度克隆的 Time 数组，可以为null。
   * @return
   *     深度克隆的 Time 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的对象也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static Time[] deepClone(@Nullable final Time[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      final Time[] result = new Time[array.length];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个 Timestamp 数组。
   *
   * @param array
   *     要深度克隆的 Timestamp 数组，可以为null。
   * @return
   *     深度克隆的 Timestamp 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的对象也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static Timestamp[] deepClone(@Nullable final Timestamp[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      final Timestamp[] result = new Timestamp[array.length];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个数组。
   *
   * @param <T>
   *     要克隆的数组元素的类型。
   * @param array
   *     要克隆的源数组，可以为null。
   * @return
   *     源数组的克隆副本；如果源数组为null则返回null。注意源数组中的对象
   *     也会被克隆到返回的数组中，因此这是深度克隆。
   */
  @SuppressWarnings("unchecked")
  public static <T extends CloneableEx<? super T>>
      T[] deepClone(@Nullable final T[] array) {
    if (array == null) {
      return null;
    } else {
      final T[] result = array.clone();
      for (int i = 0; i < array.length; ++i) {
        final T obj = array[i];
        if (obj != null) {
          result[i] = (T) obj.cloneEx();
        } else {
          result[i] = null;
        }
      }
      return result;
    }
  }

  /**
   * 深度克隆一个列表。
   *
   * @param <T>
   *     要克隆的列表元素的类型。
   * @param list
   *     要克隆的源列表，可以为null。
   * @return
   *     源列表的克隆副本；如果源列表为null则返回null。注意源列表中的对象
   *     也会被克隆到返回的列表中，因此这是深度克隆。
   */
  @SuppressWarnings("unchecked")
  public static <T extends CloneableEx<? super T>>
      List<T> deepClone(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    } else {
      final List<T> result = new ArrayList<>();
      for (final T t : list) {
        if (t == null) {
          result.add(null);
      } else {
          result.add((T) t.cloneEx());
      }
      }
      return result;
    }
  }

  /**
   * 深度克隆一个集合。
   *
   * @param <T>
   *     要克隆的集合元素的类型。
   * @param set
   *     要克隆的源集合，可以为null。
   * @return
   *     源集合的克隆副本；如果源集合为null则返回null。注意源集合中的对象
   *     也会被克隆到返回的集合中，因此这是深度克隆。
   */
  @SuppressWarnings("unchecked")
  public static <T extends CloneableEx<? super T>>
      Set<T> deepClone(@Nullable final Set<T> set) {
    if (set == null) {
      return null;
    } else {
      final Set<T> result = new HashSet<>();
      for (final T t : set) {
        if (t == null) {
          result.add(null);
      } else {
          result.add((T) t.cloneEx());
      }
      }
      return result;
    }
  }

  /**
   * 深度克隆一个映射。
   *
   * @param <K>
   *     要克隆的映射键的类型。
   * @param <V>
   *     要克隆的映射值的类型。
   * @param map
   *     要克隆的源映射，可以为null。
   * @return
   *     源映射的克隆副本；如果源映射为null则返回null。注意源映射中的值
   *     也会被克隆到返回的映射中，因此这是深度克隆。
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends CloneableEx<? super V>>
      Map<K, V> deepClone(@Nullable final Map<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Map<K, V> result = new HashMap<>();
      final Set<Map.Entry<K, V>> entries = map.entrySet();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          result.put(key, null);
      } else {
          result.put(key, (V) value.cloneEx());
      }
      }
      return result;
    }
  }

  /**
   * 深度克隆一个多值映射。
   *
   * @param <K>
   *     要克隆的多值映射键的类型。
   * @param <V>
   *     要克隆的多值映射值的类型。
   * @param map
   *     要克隆的源多值映射，可以为null。
   * @return
   *     源多值映射的克隆副本；如果源多值映射为null则返回null。注意源多值映射中的值
   *     也会被克隆到返回的多值映射中，因此这是深度克隆。
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends CloneableEx<? super V>>
      Multimap<K, V> deepClone(@Nullable final Multimap<K, V> map) {
    if (map == null) {
      return null;
    } else {
      final Multimap<K, V> result = LinkedHashMultimap.create();
      final Collection<Map.Entry<K, V>> entries = map.entries();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          result.put(key, null);
        } else {
          result.put(key, (V) value.cloneEx());
        }
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 char 数组。
   *
   * @param array
   *     要深度克隆的二维 char 数组，可以为null。
   * @return
   *     深度克隆的二维 char 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static char[][] deepClone(@Nullable final char[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY_ARRAY;
    } else {
      final char[][] result = new char[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 boolean 数组。
   *
   * @param array
   *     要深度克隆的二维 boolean 数组，可以为null。
   * @return
   *     深度克隆的二维 boolean 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static boolean[][] deepClone(@Nullable final boolean[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY_ARRAY;
    } else {
      final boolean[][] result = new boolean[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 byte 数组。
   *
   * @param array
   *     要深度克隆的二维 byte 数组，可以为null。
   * @return
   *     深度克隆的二维 byte 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static byte[][] deepClone(@Nullable final byte[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      final byte[][] result = new byte[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 short 数组。
   *
   * @param array
   *     要深度克隆的二维 short 数组，可以为null。
   * @return
   *     深度克隆的二维 short 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static short[][] deepClone(@Nullable final short[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY_ARRAY;
    } else {
      final short[][] result = new short[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 int 数组。
   *
   * @param array
   *     要深度克隆的二维 int 数组，可以为null。
   * @return
   *     深度克隆的二维 int 数组副本；如果源数组为null则
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static int[][] deepClone(@Nullable final int[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY_ARRAY;
    } else {
      final int[][] result = new int[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 long 数组。
   *
   * @param array
   *     要深度克隆的二维 long 数组，可以为null。
   * @return
   *     深度克隆的二维 long 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static long[][] deepClone(@Nullable final long[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY_ARRAY;
    } else {
      final long[][] result = new long[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 float 数组。
   *
   * @param array
   *     要深度克隆的二维 float 数组，可以为null。
   * @return
   *     深度克隆的二维 float 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static float[][] deepClone(@Nullable final float[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY_ARRAY;
    } else {
      final float[][] result = new float[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 double 数组。
   *
   * @param array
   *     要深度克隆的二维 double 数组，可以为null。
   * @return
   *     深度克隆的二维 double 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static double[][] deepClone(@Nullable final double[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY_ARRAY;
    } else {
      final double[][] result = new double[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 深度克隆一个二维 String 数组。
   *
   * @param array
   *     要深度克隆的二维 String 数组，可以为null。
   * @return
   *     深度克隆的二维 String 数组副本；如果源数组为null则返回null，
   *     如果源数组为空则返回空数组。注意源数组中的一维数组也会被克隆
   *     到返回的数组中，因此这是深度克隆。
   */
  public static String[][] deepClone(@Nullable final String[][] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY_ARRAY;
    } else {
      final String[][] result = new String[array.length][];
      for (int i = 0; i < array.length; ++i) {
        result[i] = clone(array[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧对象的值赋予左侧对象。
   *
   * @param <T>
   *     要赋值的对象类型。
   * @param left
   *     接受赋值的目标对象，可以为null。
   * @param right
   *     提供值的源对象，可以为null。
   * @return
   *     接受赋值后的目标对象；如果源对象为null则返回null；
   *     如果目标对象为null则返回源对象的克隆副本。
   */
  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
      T assign(@Nullable final T left, @Nullable final T right) {
    if (right == null) {
      return null;
    } else {
      if (left == null) {
        return (T) right.cloneEx();
      } else {
        left.assign(right);
        return left;
      }
    }
  }

  /**
   * 将右侧 Date 对象的值赋予左侧 Date 对象。
   *
   * @param left
   *     接受赋值的目标 Date 对象，可以为null。
   * @param right
   *     提供值的源 Date 对象，可以为null。
   * @return
   *     接受赋值后的目标对象；如果源对象为null则返回null；
   *     如果目标对象为null则返回源对象的克隆副本。
   */
  public static Date assign(@Nullable final Date left,
      @Nullable final Date right) {
    if (right == null) {
      return null;
    } else if (left == null) {
      return (Date) right.clone();
    } else {
      left.setTime(right.getTime());
      return left;
    }
  }

  /**
   * 将右侧 Time 对象的值赋予左侧 Time 对象。
   *
   * @param left
   *     接受赋值的目标 Time 对象，可以为null。
   * @param right
   *     提供值的源 Time 对象，可以为null。
   * @return
   *     接受赋值后的目标对象；如果源对象为null则返回null；
   *     如果目标对象为null则返回源对象的克隆副本。
   */
  public static Time assign(@Nullable final Time left,
      @Nullable final Time right) {
    if (right == null) {
      return null;
    } else if (left == null) {
      return (Time) right.clone();
    } else {
      left.setTime(right.getTime());
      return left;
    }
  }

  /**
   * 将右侧 Timestamp 对象的值赋予左侧 Timestamp 对象。
   *
   * @param left
   *     接受赋值的目标 Timestamp 对象，可以为null。
   * @param right
   *     提供值的源 Timestamp 对象，可以为null。
   * @return
   *     接受赋值后的目标对象；如果源对象为null则返回null；
   *     如果目标对象为null则返回源对象的克隆副本。
   */
  public static Timestamp assign(@Nullable final Timestamp left,
      @Nullable final Timestamp right) {
    if (right == null) {
      return null;
    } else if (left == null) {
      return (Timestamp) right.clone();
    } else {
      left.setTime(right.getTime());
      left.setNanos(right.getNanos());
      return left;
    }
  }

  /**
   * 将右侧 char 数组的值赋予左侧 char 数组。
   * <p>
   * 因为 char 是基本类型，所以 char 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 char 数组，可以为null。
   * @param right
   *     提供值的源 char 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static char[] assign(@Nullable final char[] left,
      @Nullable final char[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final char[] result = new char[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Character 数组的值赋予左侧 Character 数组。
   * <p>
   * 因为 Character 是包装类型且不可变，所以 Character 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Character 数组，可以为null。
   * @param right
   *     提供值的源 Character 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Character[] assign(@Nullable final Character[] left,
      @Nullable final Character[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Character[] result = new Character[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 boolean 数组的值赋予左侧 boolean 数组。
   * <p>
   * 因为 boolean 是基本类型，所以 boolean 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 boolean 数组，可以为null。
   * @param right
   *     提供值的源 boolean 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static boolean[] assign(@Nullable final boolean[] left,
      @Nullable final boolean[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final boolean[] result = new boolean[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Boolean 数组的值赋予左侧 Boolean 数组。
   * <p>
   * 因为 Boolean 是包装类型且不可变，所以 Boolean 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Boolean 数组，可以为null。
   * @param right
   *     提供值的源 Boolean 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Boolean[] assign(@Nullable final Boolean[] left,
      @Nullable final Boolean[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Boolean[] result = new Boolean[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 byte 数组的值赋予左侧 byte 数组。
   * <p>
   * 因为 byte 是基本类型，所以 byte 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 byte 数组，可以为null。
   * @param right
   *     提供值的源 byte 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static byte[] assign(@Nullable final byte[] left,
      @Nullable final byte[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final byte[] result = new byte[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Byte 数组的值赋予左侧 Byte 数组。
   * <p>
   * 因为 Byte 是包装类型且不可变，所以 Byte 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Byte 数组，可以为null。
   * @param right
   *     提供值的源 Byte 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Byte[] assign(@Nullable final Byte[] left,
      @Nullable final Byte[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Byte[] result = new Byte[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 short 数组的值赋予左侧 short 数组。
   * <p>
   * 因为 short 是基本类型，所以 short 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 short 数组，可以为null。
   * @param right
   *     提供值的源 short 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static short[] assign(@Nullable final short[] left,
      @Nullable final short[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final short[] result = new short[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Short 数组的值赋予左侧 Short 数组。
   * <p>
   * 因为 Short 是包装类型且不可变，所以 Short 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Short 数组，可以为null。
   * @param right
   *     提供值的源 Short 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Short[] assign(@Nullable final Short[] left,
      @Nullable final Short[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Short[] result = new Short[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 int 数组的值赋予左侧 int 数组。
   * <p>
   * 因为 int 是基本类型，所以 int 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 int 数组，可以为null。
   * @param right
   *     提供值的源 int 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static int[] assign(@Nullable final int[] left,
      @Nullable final int[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final int[] result = new int[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Integer 数组的值赋予左侧 Integer 数组。
   * <p>
   * 因为 Integer 是包装类型且不可变，所以 Integer 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Integer 数组，可以为null。
   * @param right
   *     提供值的源 Integer 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Integer[] assign(@Nullable final Integer[] left,
      @Nullable final Integer[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Integer[] result = new Integer[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 long 数组的值赋予左侧 long 数组。
   * <p>
   * 因为 long 是基本类型，所以 long 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 long 数组，可以为null。
   * @param right
   *     提供值的源 long 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static long[] assign(@Nullable final long[] left,
      @Nullable final long[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final long[] result = new long[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Long 数组的值赋予左侧 Long 数组。
   * <p>
   * 因为 Long 是包装类型且不可变，所以 Long 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Long 数组，可以为null。
   * @param right
   *     提供值的源 Long 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Long[] assign(@Nullable final Long[] left,
      @Nullable final Long[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Long[] result = new Long[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 float 数组的值赋予左侧 float 数组。
   * <p>
   * 因为 float 是基本类型，所以 float 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标 float 数组，可以为null。
   * @param right
   *     提供值的源 float 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static float[] assign(@Nullable final float[] left,
      @Nullable final float[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final float[] result = new float[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Float 数组的值赋予左侧 Float 数组。
   * <p>
   * 因为 Float 是包装类型且不可变，所以 Float 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标 Float 数组，可以为null。
   * @param right
   *     提供值的源 Float 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Float[] assign(@Nullable final Float[] left,
      @Nullable final Float[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Float[] result = new Float[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 double 数组的值赋予左侧 double 数组。
   * <p>
   * 因为 double 是基本类型，所以 double 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标 double 数组，可以为null。
   * @param right
   *     提供值的源 double 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static double[] assign(@Nullable final double[] left,
      @Nullable final double[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final double[] result = new double[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Double 数组的值赋予左侧 Double 数组。
    * <p>
   * 因为 Double 是包装类型且不可变，所以 Double 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标 Double 数组，可以为null。
   * @param right
   *     提供值的源 Double 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static Double[] assign(@Nullable final Double[] left,
      @Nullable final Double[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final Double[] result = new Double[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 String 数组的值赋予左侧 String 数组。
   * <p>
   * 因为 String 是不可变类型，所以 String 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标 String 数组，可以为null。
   * @param right
   *     提供值的源 String 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static String[] assign(@Nullable final String[] left,
      @Nullable final String[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final String[] result = new String[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 Date 数组的的值浅拷贝到左侧 Date 数组。
   * <p>
   * 因为 Date 是可变类型，所以 Date 数组的浅赋值和深赋值的效果是不一样的。
   * 
   * @param left
   *     接受赋值的目标 Date 数组，可以为null。
   * @param right
   *     提供值的源 Date 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(Date[], Date[])
   */
  public static Date[] assign(@Nullable final Date[] left,
      @Nullable final Date[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      final Date[] result;
      if ((left == null) || (left.length != right.length)) {
        result = new Date[right.length];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
        return result;
    }
  }

  /**
   * 将右侧 Time 数组的的值浅拷贝到左侧 Time 数组。
   * <p>
   * 因为 Time 是可变类型，所以 Time 数组的浅赋值和深赋值的效果是不一样的。
   * 
   * @param left
   *     接受赋值的目标 Time 数组，可以为null。
   * @param right
   *     提供值的源 Time 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。 
   * @see #deepAssign(Time[], Time[])
   */
  public static Time[] assign(@Nullable final Time[] left,
      @Nullable final Time[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
      } else {
      final Time[] result;
      if ((left == null) || (left.length != right.length)) {
        result = new Time[right.length];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧 Timestamp 数组的的值浅拷贝到左侧 Timestamp 数组。
   * <p>
   * 因为 Timestamp 是可变类型，所以 Timestamp 数组的浅赋值和深赋值的效果是不一样的。
   * 
   * @param left
   *     接受赋值的目标 Timestamp 数组，可以为null。
   * @param right
   *     提供值的源 Timestamp 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(Timestamp[], Timestamp[])
   */
  public static Timestamp[] assign(@Nullable final Timestamp[] left,
      @Nullable final Timestamp[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else {
      final Timestamp[] result;
      if ((left == null) || (left.length != right.length)) {
        result = new Timestamp[right.length];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧 Class 数组的的值浅拷贝到左侧 Class 数组。
   * <p>
   * 因为 Class 是可变类型，所以 Class 数组的浅赋值和深赋值的效果是不一样的。
   * 
   * @param left
   *     接受赋值的目标 Class 数组，可以为null。
   * @param right
   *     提供值的源 Class 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(Class<?>[], Class<?>[])
   */
  public static Class<?>[] assign(@Nullable final Class<?>[] left,
      @Nullable final Class<?>[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
      } else {
      if ((left == null) || (left.length != right.length)) {
        final Class<?>[] result = new Class<?>[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 BigInteger 数组的值赋予左侧 BigInteger 数组。
   *
   * @param left
   *     接受赋值的目标 BigInteger 数组，可以为null。
   * @param right
   *     提供值的源 BigInteger 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static BigInteger[] assign(@Nullable final BigInteger[] left,
      @Nullable final BigInteger[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final BigInteger[] result = new BigInteger[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧 BigDecimal 数组的值赋予左侧 BigDecimal 数组。
   *
   * @param left
   *     接受赋值的目标 BigDecimal 数组，可以为null。
   * @param right
   *     提供值的源 BigDecimal 数组，可以为null。
   * @return
   *     接受赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   */
  public static BigDecimal[] assign(@Nullable final BigDecimal[] left,
      @Nullable final BigDecimal[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else {
      if ((left == null) || (left.length != right.length)) {
        final BigDecimal[] result = new BigDecimal[right.length];
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧泛型数组的的值浅拷贝到左侧泛型数组。
   * <p>
   * 因为泛型数组是对象数组，所以泛型数组的浅赋值和深赋值的效果是不一样的。
   *
   * @param <T>
   *     数组元素的类型。
   * @param left
   *     接受赋值的目标泛型数组，可以为null。
   * @param right
   *     提供值的源泛型数组，可以为null。
   * @param clazz
   *     数组元素的类型。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(T[], T[], Class<T>)
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] assign(@Nullable final T[] left,
      @Nullable final T[] right, final Class<T> clazz) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return (T[]) Array.newInstance(clazz, 0);
    } else {
      if ((left == null) || (left.length != right.length)) {
        final T[] result = (T[]) Array.newInstance(clazz, right.length);
        System.arraycopy(right, 0, result, 0, right.length);
        return result;
      } else {
        System.arraycopy(right, 0, left, 0, right.length);
        return left;
      }
    }
  }

  /**
   * 将右侧列表的的值浅拷贝到左侧列表。
   * <p>
   * 因为 List 是对象集合，所以 List 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <T>
   *     列表元素的类型。
   * @param left
   *     接受赋值的目标列表，可以为null。
   * @param right
   *     提供值的源列表，可以为null。
   * @return
   *     接受浅拷贝后的目标列表；如果源列表为null，则清空目标列表并返回；
   *     如果目标列表为null则创建新列表。
   * @see #deepAssign(List<T>, List<T>)
   */
  public static <T> List<T> assign(@Nullable final List<T> left,
      @Nullable final List<T> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
      return left;
    } else {
      final List<T> result;
      if (left == null) {
        result = new ArrayList<>();
      } else {
        result = left;
        result.clear();
      }
      result.addAll(right);
      return result;
    }
  }

  /**
   * 将右侧集合的的值浅拷贝到左侧集合。
   * <p>
   * 因为 Set 是对象集合，所以 Set 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <T>
   *     集合元素的类型。
   * @param left
   *     接受赋值的目标集合，可以为null。
   * @param right
   *     提供值的源集合，可以为null。
   * @return
   *     接受浅拷贝后的目标集合；如果源集合为null，则清空目标集合并返回；
   *     如果目标集合为null则创建新集合。
   * @see #deepAssign(Set<T>, Set<T>)
   */
  public static <T> Set<T> assign(@Nullable final Set<T> left,
      @Nullable final Set<T> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
      return left;
    } else {
      final Set<T> result;
      if (left == null) {
        result = new HashSet<>();
      } else {
        result = left;
        result.clear();
      }
      result.addAll(right);
        return result;
    }
  }

  /**
   * 将右侧映射的的值浅拷贝到左侧映射。
   * <p>
   * 因为 Map 是对象集合，所以 Map 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <K>
   *     映射键的类型。
   * @param <V>
   *     映射值的类型。
   * @param left
   *     接受赋值的目标映射，可以为null。
   * @param right
   *     提供值的源映射，可以为null。
   * @return
   *     接受浅拷贝后的目标映射；如果源映射为null，则清空目标映射并返回；
   *     如果目标映射为null则创建新映射。
   * @see #deepAssign(Map<K, V>, Map<K, V>)
   */
  public static <K, V> Map<K, V> assign(@Nullable final Map<K, V> left,
      @Nullable final Map<K, V> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
      return left;
      } else {
      final Map<K, V> result;
      if (left == null) {
        result = new HashMap<>();
      } else {
        result = left;
        result.clear();
      }
      result.putAll(right);
      return result;
    }
  }

  /**
   * 将右侧多值映射的的值浅拷贝到左侧多值映射。
   * <p>
   * 因为 Multimap 是对象集合，所以 Multimap 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <K>
   *     多值映射键的类型。
   * @param <V>
   *     多值映射值的类型。
   * @param left
   *     接受赋值的目标多值映射，可以为null。
   * @param right
   *     提供值的源多值映射，可以为nul  l。
   * @return
   *     接受浅拷贝后的目标多值映射；如果源多值映射为null，则清空目标多值映射并返回；
   *     如果目标多值映射为null则创建新多值映射。
   * @see #deepAssign(Multimap<K, V>, Multimap<K, V>)
   */
  public static <K, V> Multimap<K, V> assign(@Nullable final Multimap<K, V> left,
      @Nullable final Multimap<K, V> right) {
    if (right == null) {
      if (left != null) {
        left.clear();
      }
      return left;
    } else {
      final Multimap<K, V> result;
      if (left == null) {
        result = LinkedHashMultimap.create();
      } else {
        result = left;
        result.clear();
      }
      result.putAll(right);
      return result;
    }
  }

  /**
   * 将右侧二维 char 数组的的值浅拷贝到左侧二维 char 数组。
   * <p>
   * 因为 char[] 是数组，所以 char[][] 数组的浅赋值和深赋值的效果是不一样的。
   * 
   * @param left
   *     接受赋值的目标二维 char 数组，可以为null。
   * @param right
   *     提供值的源二维 char 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(char[][], char[][])
   */
  public static char[][] assign(@Nullable final char[][] left,
      @Nullable final char[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY_ARRAY;
    } else {
      final char[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new char[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 boolean 数组的的值浅拷贝到左侧二维 boolean 数组。
   * <p>
   * 因为 boolean[] 是数组，所以 boolean[][] 数组的浅赋值和深赋值的效果是不一样的。
   * 
   * @param left
   *     接受赋值的目标二维 boolean 数组，可以为null。
   * @param right
   *     提供值的源二维 boolean 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(boolean[][], boolean[][])
   */
  public static boolean[][] assign(@Nullable final boolean[][] left,
      @Nullable final boolean[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY_ARRAY;
    } else {
      final boolean[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new boolean[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 byte 数组的的值浅拷贝到左侧二维 byte 数组。
   * <p>
   * 因为 byte[] 是数组，所以 byte[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 byte 数组，可以为null。
   * @param right
   *     提供值的源二维 byte 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(byte[][], byte[][])
   */
  public static byte[][] assign(@Nullable final byte[][] left,
      @Nullable final byte[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      final byte[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new byte[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 short 数组的的值浅拷贝到左侧二维 short 数组。
   * <p>
   * 因为 short[] 是数组，所以 short[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 short 数组，可以为null。
   * @param right
   *     提供值的源二维 short 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(short[][], short[][])
   */
  public static short[][] assign(@Nullable final short[][] left,
      @Nullable final short[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY_ARRAY;
    } else {
      final short[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new short[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 int 数组的的值浅拷贝到左侧二维 int 数组。
   * <p>
   * 因为 int[] 是数组，所以 int[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 int 数组，可以为null。
   * @param right
   *     提供值的源二维 int 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(int[][], int[][])
   */
  public static int[][] assign(@Nullable final int[][] left,
      @Nullable final int[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY_ARRAY;
    } else {
      final int[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new int[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 long 数组的的值浅拷贝到左侧二维 long 数组。
   * <p>
   * 因为 long[] 是数组，所以 long[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 long 数组，可以为null。
   * @param right
   *     提供值的源二维 long 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(long[][], long[][])
   */
  public static long[][] assign(@Nullable final long[][] left,
      @Nullable final long[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY_ARRAY;
    } else {
      final long[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new long[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 float 数组的的值浅拷贝到左侧二维 float 数组。
   * <p>
   * 因为 float[] 是数组，所以 float[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 float 数组，可以为null。
   * @param right
   *     提供值的源二维 float 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(float[][], float[][])
   */
  public static float[][] assign(@Nullable final float[][] left,
      @Nullable final float[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY_ARRAY;
    } else {
      final float[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new float[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 double 数组的的值浅拷贝到左侧二维 double 数组。
   * <p>
   * 因为 double[] 是数组，所以 double[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 double 数组，可以为null。
   * @param right
   *     提供值的源二维 double 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(double[][], double[][])
   */
  public static double[][] assign(@Nullable final double[][] left,
      @Nullable final double[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY_ARRAY;
    } else {
      final double[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new double[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧二维 String 数组的的值浅拷贝到左侧二维 String 数组。
   * <p>
   * 因为 String[] 是数组，所以 String[][] 数组的浅赋值和深赋值的效果是一样的。
   * 
   * @param left
   *     接受赋值的目标二维 String 数组，可以为null。
   * @param right
   *     提供值的源二维 String 数组，可以为null。
   * @return
   *     接受浅拷贝后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组副本。
   * @see #deepAssign(String[][], String[][])
   */
  public static String[][] assign(@Nullable final String[][] left,
      @Nullable final String[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY_ARRAY;
    } else {
      final String[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new String[right.length][];
      } else {
        result = left;
      }
      System.arraycopy(right, 0, result, 0, right.length);
      return result;
    }
  }

  /**
   * 将右侧 Date 数组的的值深度赋值到左侧 Date 数组。
   * <p>
   * 因为 Date 是对象数组，所以 Date 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Date 数组，可以为null。
   * @param right
   *     提供值的源 Date 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个元素都会被深度赋值。
   * @see #assign(Date[], Date[])
   */
  public static Date[] deepAssign(@Nullable final Date[] left,
      @Nullable final Date[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DATE_ARRAY;
    } else {
      final Date[] result;
      if ((left == null) || (left.length != right.length)) {
        result = new Date[right.length];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧 Time 数组的的值深度赋值到左侧 Time 数组。
   * <p>
   * 因为 Time 是对象数组，所以 Time 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Time 数组，可以为null。
   * @param right
   *     提供值的源 Time 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个元素都会被深度赋值。
   * @see #assign(Time[], Time[])
   */
  public static Time[] deepAssign(@Nullable final Time[] left,
      @Nullable final Time[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIME_ARRAY;
    } else {
      final Time[] result;
      if ((left == null) || (left.length != right.length)) {
        result = new Time[right.length];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
        return result;
    }
  }

  /**
   * 将右侧 Timestamp 数组的的值深度赋值到左侧 Timestamp 数组。
   * <p>
   * 因为 Timestamp 是对象数组，所以 Timestamp 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标 Timestamp 数组，可以为null。
   * @param right
   *     提供值的源 Timestamp 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个元素都会被深度赋值。
   * @see #assign(Timestamp[], Timestamp[])
   */
  public static Timestamp[] deepAssign(@Nullable final Timestamp[] left,
      @Nullable final Timestamp[] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
      } else {
      final Timestamp[] result;
      if ((left == null) || (left.length != right.length)) {
        result = new Timestamp[right.length];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧泛型数组的的值深度赋值到左侧泛型数组。
   * <p>
   * 因为泛型数组是对象数组，所以泛型数组的浅赋值和深赋值的效果是不一样的。
   *
   * @param <T>
   *     数组元素的类型，必须实现 Assignable 接口。
   * @param left
   *     接受赋值的目标泛型数组，可以为null。
   * @param right
   *     提供值的源泛型数组，可以为null。
   * @param clazz
   *     数组元素的类型。 
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个元素都会被深度赋值。
   * @see #assign(T[], T[], Class<T>)
   */
  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>> 
  T[] deepAssign(@Nullable final T[] left, @Nullable final T[] right,
      final Class<T> clazz) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return (T[]) Array.newInstance(clazz, 0);
      } else {
      final T[] result;
      if ((left == null) || (left.length != right.length)) {
        result = (T[]) Array.newInstance(clazz, right.length);
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        if (right[i] == null) {
          result[i] = null;
        } else if (result[i] == null) {
          result[i] = (T) right[i].cloneEx();
        } else {
          result[i].assign(right[i]);
        }
      }
      return result;
    }
  }

  /**
   * 将右侧列表的的值深度赋值到左侧列表。
   * <p>
   * 因为 List 是对象集合，所以 List 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <T>
   *     列表元素的类型，必须实现 Assignable 接口。
   * @param left
   *     接受赋值的目标列表，可以为null。
   * @param right
   *     提供值的源列表，可以为null。
   * @return
   *     接受深度赋值后的目标列表；如果源列表为null则返回null；
   *     如果目标列表为null则创建新列表。注意列表中的每个元素都会被深度赋值。
   * @see #assign(List<T>, List<T>)
   */
  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
  List<T> deepAssign(@Nullable final List<T> left,
      @Nullable final List<T> right) {
    if (right == null) {
      return null;
    } else {
      final List<T> result;
      if (left == null) {
        result = new ArrayList<>();
      } else {
        result = left;
        result.clear();
      }
      for (final T t : right) {
        if (t == null) {
          result.add(null);
        } else {
          result.add((T) t.cloneEx());
        }
        }
        return result;
    }
  }

  /**
   * 将右侧集合的的值深度赋值到左侧集合。
   * <p>
   * 因为 Set 是对象集合，所以 Set 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <T>
   *     集合元素的类型，必须实现 Assignable 接口。
   * @param left
   *     接受赋值的目标集合，可以为null。
   * @param right
   *     提供值的源集合，可以为null。
   * @return
   *     接受深度赋值后的目标集合；如果源集合为null则返回null；
   *     如果目标集合为null则创建新集合。注意集合中的每个元素都会被深度赋值。
   * @see #assign(Set<T>, Set<T>)
   */
  @SuppressWarnings("unchecked")
  public static <T extends Assignable<? super T>>
  Set<T> deepAssign(@Nullable final Set<T> left,
      @Nullable final Set<T> right) {
    if (right == null) {
      return null;
      } else {
      final Set<T> result;
      if (left == null) {
        result = new HashSet<>();
      } else {
        result = left;
        result.clear();
      }
      for (final T t : right) {
        if (t == null) {
          result.add(null);
        } else {
          result.add((T) t.cloneEx());
        }
      }
      return result;
    }
  }

  /**
   * 将右侧映射的的值深度赋值到左侧映射。
   * <p>
   * 因为 Map 是对象集合，所以 Map 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <K>
   *     映射键的类型。
   * @param <V>
   *     映射值的类型，必须实现 Assignable 接口。
   * @param left
   *     接受赋值的目标映射，可以为null。
   * @param right
   *     提供值的源映射，可以为null。
   * @return
   *     接受深度赋值后的目标映射；如果源映射为null则返回null；
   *     如果目标映射为null则创建新映射。注意映射中的每个值都会被深度赋值。
   * @see #assign(Map<K, V>, Map<K, V>)
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends Assignable<? super V>>
  Map<K, V> deepAssign(@Nullable final Map<K, V> left,
      @Nullable final Map<K, V> right) {
    if (right == null) {
      return null;
    } else {
      final Map<K, V> result;
      if (left == null) {
        result = new HashMap<>();
      } else {
        result = left;
        result.clear();
      }
      final Set<Map.Entry<K, V>> entries = right.entrySet();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          result.put(key, null);
        } else {
          result.put(key, (V) value.cloneEx());
        }
      }
        return result;
    }
  }

  /**
   * 将右侧多值映射的的值深度赋值到左侧多值映射。
   * <p>
   * 因为 Multimap 是对象集合，所以 Multimap 的浅赋值和深赋值的效果是不一样的。
   *
   * @param <K>
   *     多值映射键的类型。
   * @param <V>
   *     多值映射值的类型，必须实现 Assignable 接口。
   * @param left
   *     接受赋值的目标多值映射，可以为null。
   * @param right
   *     提供值的源多值映射，可以为null。
   * @return
   *     接受深度赋值后的目标多值映射；如果源多值映射为null则返回null；
   *     如果目标多值映射为null则创建新多值映射。注意多值映射中的每个值都会被深度赋值。
   * @see #assign(Multimap<K, V>, Multimap<K, V>)
   */
  @SuppressWarnings("unchecked")
  public static <K, V extends Assignable<? super V>>
  Multimap<K, V> deepAssign(@Nullable final Multimap<K, V> left,
      @Nullable final Multimap<K, V> right) {
    if (right == null) {
      return null;
      } else {
      final Multimap<K, V> result;
      if (left == null) {
        result = LinkedHashMultimap.create();
      } else {
        result = left;
        result.clear();
      }
      final Collection<Map.Entry<K, V>> entries = right.entries();
      for (final Map.Entry<K, V> entry : entries) {
        final K key = entry.getKey();
        final V value = entry.getValue();
        if (value == null) {
          result.put(key, null);
        } else {
          result.put(key, (V) value.cloneEx());
        }
      }
      return result;
    }
  }

  /**
   * 将右侧二维 char 数组的的值深度赋值到左侧二维 char 数组。
   * <p>
   * 因为 char[] 是数组，所以 char[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 char 数组，可以为null。
   * @param right
   *     提供值的源二维 char 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(char[][], char[][])
   */
  public static char[][] deepAssign(@Nullable final char[][] left,
      @Nullable final char[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_CHAR_ARRAY_ARRAY;
    } else {
      final char[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new char[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 boolean 数组的的值深度赋值到左侧二维 boolean 数组。
   * <p>
   * 因为 boolean[] 是数组，所以 boolean[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 boolean 数组，可以为null。
   * @param right
   *     提供值的源二维 boolean 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(boolean[][], boolean[][])
   */
  public static boolean[][] deepAssign(@Nullable final boolean[][] left,
      @Nullable final boolean[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_ARRAY_ARRAY;
    } else {
      final boolean[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new boolean[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 byte 数组的的值深度赋值到左侧二维 byte 数组。
   * <p>
   * 因为 byte[] 是数组，所以 byte[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 byte 数组，可以为null。
   * @param right
   *     提供值的源二维 byte 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(byte[][], byte[][])
   */
  public static byte[][] deepAssign(@Nullable final byte[][] left,
      @Nullable final byte[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else {
      final byte[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new byte[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
        return result;
    }
  }

  /**
   * 将右侧二维 short 数组的的值深度赋值到左侧二维 short 数组。
   * <p>
   * 因为 short[] 是数组，所以 short[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 short 数组，可以为null。
   * @param right
   *     提供值的源二维 short 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(short[][], short[][])
   */
  public static short[][] deepAssign(@Nullable final short[][] left,
      @Nullable final short[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_SHORT_ARRAY_ARRAY;
    } else {
      final short[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new short[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 int 数组的的值深度赋值到左侧二维 int 数组。
   * <p>
   * 因为 int[] 是数组，所以 int[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 int 数组，可以为null。
   * @param right
   *     提供值的源二维 int 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(int[][], int[][])
   */
  public static int[][] deepAssign(@Nullable final int[][] left,
      @Nullable final int[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_INT_ARRAY_ARRAY;
    } else {
      final int[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new int[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 long 数组的的值深度赋值到左侧二维 long 数组。
   * <p>
   * 因为 long[] 是数组，所以 long[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 long 数组，可以为null。
   * @param right
   *     提供值的源二维 long 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(long[][], long[][])
   */
  public static long[][] deepAssign(@Nullable final long[][] left,
      @Nullable final long[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_LONG_ARRAY_ARRAY;
    } else {
      final long[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new long[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 float 数组的的值深度赋值到左侧二维 float 数组。
   * <p>
   * 因为 float[] 是数组，所以 float[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 float 数组，可以为null。
   * @param right
   *     提供值的源二维 float 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(float[][], float[][])
   */
  public static float[][] deepAssign(@Nullable final float[][] left,
      @Nullable final float[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_FLOAT_ARRAY_ARRAY;
    } else {
      final float[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new float[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 double 数组的的值深度赋值到左侧二维 double 数组。
   * <p>
   * 因为 double[] 是数组，所以 double[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 double 数组，可以为null。
   * @param right
   *     提供值的源二维 double 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(double[][], double[][])
   */
  public static double[][] deepAssign(@Nullable final double[][] left,
      @Nullable final double[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_DOUBLE_ARRAY_ARRAY;
    } else {
      final double[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new double[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }

  /**
   * 将右侧二维 String 数组的的值深度赋值到左侧二维 String 数组。
   * <p>
   * 因为 String[] 是数组，所以 String[][] 数组的浅赋值和深赋值的效果是一样的。
   *
   * @param left
   *     接受赋值的目标二维 String 数组，可以为null。
   * @param right
   *     提供值的源二维 String 数组，可以为null。
   * @return
   *     接受深度赋值后的目标数组；如果源数组为null则返回null；
   *     如果源数组为空则返回空数组；如果目标数组为null或长度不匹配
   *     则返回新创建的数组。注意数组中的每个一维数组都会被深度赋值。
   * @see #assign(String[][], String[][])
   */
  public static String[][] deepAssign(@Nullable final String[][] left,
      @Nullable final String[][] right) {
    if (right == null) {
      return null;
    } else if (right.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY_ARRAY;
    } else {
      final String[][] result;
      if ((left == null) || (left.length != right.length)) {
        result = new String[right.length][];
      } else {
        result = left;
      }
      for (int i = 0; i < right.length; ++i) {
        result[i] = assign(result[i], right[i]);
      }
      return result;
    }
  }
}