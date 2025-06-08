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
 * Utility functions for helping implementation of the Assignable interface.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class Assignment {

  @SuppressWarnings("unchecked")
  public static <T extends CloneableEx<? super T>> T clone(@Nullable final T obj) {
    if (obj == null) {
      return null;
    } else {
      return (T) obj.cloneEx();
    }
  }

  public static Date clone(@Nullable final Date value) {
    return (value == null ? null : (Date) value.clone());
  }

  public static Time clone(@Nullable final Time value) {
    return (value == null ? null : (Time) value.clone());
  }

  public static Timestamp clone(@Nullable final Timestamp value) {
    return (value == null ? null : (Timestamp) value.clone());
  }

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

  public static String[][] clone(@Nullable final String[][] array2d) {
    if (array2d == null) {
      return null;
    } else if (array2d.length == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY_2D;
    } else {
      final String[][] result = new String[array2d.length][];
      for (int i = 0; i < array2d.length; ++i) {
        result[i] = clone(array2d[i]);
      }
      return result;
    }
  }

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
   * Shallowly clones an array.
   *
   * <p>NOTE: The argument type T does NOT have to be cloneable, since this is a
   * shallow clone.
   *
   * @param <T>
   *          The type of the element of the array to be cloned.
   * @param array
   *          The source array to be cloned, which could be null.
   * @return The cloned copy of the source array; or null if the source array is
   *         null. Note that the objects in the source array is simply copied
   *         into the returned array, thus this is a shallow clone.
   */
  public static <T> T[] clone(@Nullable final T[] array) {
    if (array == null) {
      return null;
    } else {
      return array.clone();
    }
  }

  /**
   * Shallowly clones a list.
   *
   * @param <T>
   *          The type of the element of the list to be cloned.
   * @param list
   *          The source list to be cloned, which could be null.
   * @return The cloned copy of the source list; or null if the source list is
   *         null. Note that the objects in the source list is simply copied
   *         into the returned list, thus this is a shallow clone.
   */
  public static <T> List<T> cloneList(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    } else {
      return new ArrayList<>(list);
    }
  }

  /**
   * Shallowly clones a set.
   *
   * @param <T>
   *          The type of the element of the set to be cloned.
   * @param set
   *          The source set to be cloned, which could be null.
   * @return The cloned copy of the source set; or null if the source set is
   *         null. Note that the objects in the source set is simply copied
   *         into the returned set, thus this is a shallow clone.
   */
  public static <T> Set<T> cloneSet(@Nullable final Set<T> set) {
    if (set == null) {
      return null;
    } else {
      return new HashSet<>(set);
    }
  }

  /**
   * Shallowly clones a map.
   *
   * @param <K>
   *          The type of the key of the map to be cloned.
   * @param <V>
   *          The type of the value of the map to be cloned.
   * @param map
   *          The source map to be cloned, which could be null.
   * @return The cloned copy of the source map; or null if the source map is
   *         null. Note that the objects in the source map is simply copied
   *         into the returned map, thus this is a shallow clone.
   */
  public static <K, V> Map<K, V> cloneMap(@Nullable final Map<K, V> map) {
    if (map == null) {
      return null;
    } else {
      return new HashMap<>(map);
    }
  }

  /**
   * Shallowly clones a multi-map.
   *
   * @param <K>
   *          The type of the key of the multi-map to be cloned.
   * @param <V>
   *          The type of the value of the multi-map to be cloned.
   * @param map
   *          The source multi-map to be cloned, which could be null.
   * @return The cloned copy of the source multi-map; or null if the source map is
   *         null. Note that the objects in the source multi-map is simply copied
   *         into the returned multi-map, thus this is a shallow clone.
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
   * Deeply clones an array.
   *
   * @param <T>
   *          The type of the element of the array to be cloned.
   * @param array
   *          The source array to be cloned, which could be null.
   * @return The cloned copy of the source array; or null if the source array is
   *         null. Note that the objects in the source array is also cloned
   *         into the returned array, thus this is a deep clone.
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
   * Deeply clones a list.
   *
   * @param <T>
   *          The type of the element of the list to be cloned.
   * @param list
   *          the list to be cloned.
   * @return The cloned copy of the source list; or null if the source list is
   *         null. Note that the objects in the source list is also cloned into
   *         the returned list, thus this is a deep clone.
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
   * Deeply clones a set.
   *
   * @param <T>
   *          The type of the element of the set to be cloned.
   * @param set
   *          The source set to be cloned, which could be null.
   * @return The cloned copy of the source set; or null if the source set is
   *         null. Note that the objects in the source set is also cloned into
   *         the returned set, thus this is a deep clone.
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
   * Deeply clones a map.
   *
   * @param <K>
   *          The type of the key of the map to be cloned.
   * @param <V>
   *          The type of the value of the map to be cloned.
   * @param map
   *          The source map to be cloned, which could be null.
   * @return The cloned copy of the source map; or null if the source map is
   *         null. Note that the values in the source map is also cloned
   *         into the returned map, thus this is a deep clone.
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
   * Deeply clones a multi-map.
   *
   * @param <K>
   *          The type of the key of the multi-map to be cloned.
   * @param <V>
   *          The type of the value of the multi-map to be cloned.
   * @param map
   *          The source multi-map to be cloned, which could be null.
   * @return The cloned copy of the source multi-map; or null if the source
   *         multi-map is null. Note that the values in the source multi-map is
   *         also cloned into the returned multi-map, thus this is a deep clone.
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

  public static <K, V>
      Map<K, V> assign(@Nullable final Map<K, V> left,
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

  public static <K, V>
      Multimap<K, V> assign(@Nullable final Multimap<K, V> left,
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

}