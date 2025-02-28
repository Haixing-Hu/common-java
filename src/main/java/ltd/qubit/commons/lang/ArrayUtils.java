////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import ltd.qubit.commons.text.tostring.SimpleToStringStyle;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * This class provides operations on arrays, primitive arrays (like
 * {@code int[]}) and primitive wrapper arrays (like {@code Integer[]}).
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} array input. However, an Object array
 * that contains a {@code null} element may throw an exception. Each method
 * documents its behavior.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class ArrayUtils {

  /**
   * An empty immutable {@code Object} array.
   */
  public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  /**
   * An empty immutable {@code Class} array.
   */
  public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

  /**
   * An empty immutable {@code Type} array.
   */
  public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

  /**
   * An empty immutable {@code String} array.
   */
  public static final String[] EMPTY_STRING_ARRAY = new String[0];

  /**
   * An empty immutable {@code String[]} array.
   */
  public static final String[][] EMPTY_STRING_ARRAY_2D = new String[0][0];

  /**
   * An empty immutable {@code long} array.
   */
  public static final long[] EMPTY_LONG_ARRAY = new long[0];

  /**
   * An empty immutable {@code Long} array.
   */
  public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

  /**
   * An empty immutable {@code int} array.
   */
  public static final int[] EMPTY_INT_ARRAY = new int[0];

  /**
   * An empty immutable {@code Integer} array.
   */
  public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

  /**
   * An empty immutable {@code short} array.
   */
  public static final short[] EMPTY_SHORT_ARRAY = new short[0];

  /**
   * An empty immutable {@code Short} array.
   */
  public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

  /**
   * An empty immutable {@code byte} array.
   */
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  /**
   * An empty immutable {@code Byte} array.
   */
  public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

  /**
   * An empty immutable {@code double} array.
   */
  public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

  /**
   * An empty immutable {@code Double} array.
   */
  public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

  /**
   * An empty immutable {@code float} array.
   */
  public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

  /**
   * An empty immutable {@code Float} array.
   */
  public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

  /**
   * An empty immutable {@code boolean} array.
   */
  public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

  /**
   * An empty immutable {@code Boolean} array.
   */
  public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

  /**
   * An empty immutable {@code char} array.
   */
  public static final char[] EMPTY_CHAR_ARRAY = new char[0];

  /**
   * An empty immutable {@code Character} array.
   */
  public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

  /**
   * An empty immutable {@code byte[]} array.
   */
  public static final byte[][] EMPTY_BYTE_ARRAY_ARRAY = new byte[0][];

  /**
   * An empty immutable {@link BigInteger} array.
   */
  public static final BigInteger[] EMPTY_BIG_INTEGER_ARRAY = new BigInteger[0];

  /**
   * An empty immutable {@link BigDecimal} array.
   */
  public static final BigDecimal[] EMPTY_BIG_DECIMAL_ARRAY = new BigDecimal[0];

  /**
   * An empty immutable {@link Date} array.
   */
  public static final Date[] EMPTY_DATE_ARRAY = new Date[0];

  /**
   * An empty immutable {@link Time} array.
   */
  public static final Time[] EMPTY_TIME_ARRAY = new Time[0];

  /**
   * An empty immutable {@link Timestamp} array.
   */
  public static final Timestamp[] EMPTY_TIMESTAMP_ARRAY = new Timestamp[0];

  /**
   * An empty immutable {@link LocalDate} array.
   */
  public static final LocalDate[] EMPTY_LOCAL_DATE_ARRAY = new LocalDate[0];

  /**
   * An empty immutable {@link LocalTime} array.
   */
  public static final LocalTime[] EMPTY_LOCAL_TIME_ARRAY = new LocalTime[0];

  /**
   * An empty immutable {@link LocalDateTime} array.
   */
  public static final LocalDateTime[] EMPTY_LOCAL_DATETIME_ARRAY = new LocalDateTime[0];

  /**
   * The current value when an element is not found in a list or array:
   * {@code -1}. This value is returned by methods in this class and can also be
   * used in comparisons with values returned by various method from
   * {@link java.util.List}.
   */
  public static final int INDEX_NOT_FOUND = -1;

  private ArrayUtils() {
  }

  /**
   * Outputs an array as a String, treating {@code null} as an empty array.
   *
   * <p>Multi-dimensional arrays are handled correctly, including
   * multi-dimensional primitive arrays.
   *
   * <p>The format is that of Java source code, for example {@code {a,b}}.
   *
   * @param array
   *     the array to get a toString for, may be {@code null}
   * @return a String representation of the array, '{}' if null array input
   */
  public static String toString(final Object array) {
    return toString(array, "{}");
  }

  /**
   * Outputs an array as a String handling {@code null}s.
   *
   * <p>Multi-dimensional arrays are handled correctly, including
   * multi-dimensional primitive arrays.
   *
   * <p>The format is that of Java source code, for example {@code {a,b}}.
   *
   * @param array
   *     the array to get a toString for, may be {@code null}
   * @param stringIfNull
   *     the String to return if the array is {@code null}
   * @return a String representation of the array
   */
  public static String toString(final Object array, final String stringIfNull) {
    if (array == null) {
      return stringIfNull;
    }
    return new ToStringBuilder(SimpleToStringStyle.INSTANCE).append(array)
                                                            .toString();
  }

  /**
   * Get a hashCode for an array handling multi-dimensional arrays correctly.
   *
   * <p>Multi-dimensional primitive arrays are also handled correctly by this
   * method.
   *
   * @param array
   *     the array to get a hashCode for, may be {@code null}
   * @return a hashCode for the array, zero if null array input.
   */
  public static int hashCode(final Object array) {
    int code = 2;
    final int multiplier = 13333;
    code = Hash.combine(code, multiplier, array);
    return code;
  }

  /**
   * Converts the given array into a {@link java.util.Map}. Each element of the
   * array must be either a {@link java.util.Map.Entry} or an array, containing
   * at least two elements, where the first element is used as key and the
   * second as value.
   *
   * <p>This method can be used to initialize:
   *
   * <pre>
   * // Create a Map mapping colors.
   * Map colorMap = ArrayUtils.toMap(new String[][] {{
   *     {"RED", "#FF0000"},
   *     {"GREEN", "#00FF00"},
   *     {"BLUE", "#0000FF"}});
   * </pre>
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     an array whose elements are either a {@link java.util.Map.Entry} or an
   *     array containing at least two elements, may be {@code null}.
   * @return a {@code Map} that was created from the array
   * @throws IllegalArgumentException
   *     if one element of this array is itself an array containing less then
   *     two elements
   * @throws IllegalArgumentException
   *     if the array contains elements other than {@link java.util.Map.Entry}
   *     and an Buffer
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
   * Finds the maximum value in a segment of an array.
   *
   * @param <T>
   *     the type of the elements in the array, which must be comparable.
   * @param array
   *     the array, which could be {@code null}.
   * @param start
   *     the inclusive start index of the segment. If it is less than 0, it is
   *     treated as 0.
   * @param end
   *     the exclusive end index of the segment. If it is greater than
   *     {@code array.length}, it is treated as {@code array.length}.
   * @return the maximum value found in the specified segment of the array;
   *     returns {@code null} if the input array is {@code null} or the segment
   *     is empty.
   */
  public static <T extends Comparable<? super T>> T max(
      @Nullable final T[] array, final int start, final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return null;
    }
    T result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] != null) {
        if (result == null) {
          result = array[i];
        } else if (result.compareTo(array[i]) < 0) {
          result = array[i];
        }
      }
    }
    return result;
  }

  /**
   * Finds the maximum value in an array.
   *
   * @param <T>
   *     the type of the elements in the array, which must be comparable.
   * @param array
   *     the array, which could be {@code null}.
   * @return the maximum value found in the specified array, or {@code null} if
   *     the array is {@code null}.
   */
  public static <T extends Comparable<? super T>> T max(
      @Nullable final T[] array) {
    if (array == null) {
      return null;
    }
    return max(array, 0, array.length);
  }

  /**
   * Finds the minimum value in a segment of an array.
   *
   * @param <T>
   *     the type of the elements in the array, which must be comparable.
   * @param array
   *     the array, which could be {@code null}.
   * @param start
   *     the inclusive start index of the segment. If it is less than 0, it is
   *     treated as 0.
   * @param end
   *     the exclusive end index of the segment. If it is greater than
   *     {@code array.length}, it is treated as {@code array.length}.
   * @return the minimum value found in the specified segment of the array;
   *     returns {@code null} if the input array is {@code null} or the segment
   *     is empty.
   */
  public static <T extends Comparable<? super T>> T min(
      @Nullable final T[] array, final int start, final int end) {
    if (array == null) {
      return null;
    }
    final int theStart = Math.max(start, 0);
    final int theEnd = Math.min(end, array.length);
    if (theStart >= theEnd) {
      return null;
    }
    T result = array[theStart];
    for (int i = theStart + 1; i < theEnd; ++i) {
      if (array[i] != null) {
        if (result != null && result.compareTo(array[i]) > 0) {
          result = array[i];
        }
      }
    }
    return result;
  }

  /**
   * Finds the minimum value in an array.
   *
   * @param <T>
   *     the type of the elements in the array, which must be comparable.
   * @param array
   *     the array, which could be {@code null}.
   * @return the minimum value found in the specified array, or {@code null} if
   *     the array is {@code null}.
   */
  public static <T extends Comparable<? super T>> T min(
      @Nullable final T[] array) {
    if (array == null) {
      return null;
    }
    return min(array, 0, array.length);
  }

  /**
   * Produces a new array containing the elements between the start and end
   * indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * <p>The component type of the subarray is always the same as that of the
   * input array. Thus, if the input is an array of type {@code Date}, the
   * following usage is envisaged:
   *
   * <pre>
   * Date[] someDates = Arrays.subarray(allDates, 2, 5);
   * </pre>
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array, which could be {@code null}.
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code long} array containing the elements between the start
   * and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code int} array containing the elements between the start
   * and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code short} array containing the elements between the
   * start and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code char} array containing the elements between the start
   * and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code byte} array containing the elements between the start
   * and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code double} array containing the elements between the
   * start and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code float} array containing the elements between the
   * start and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Produces a new {@code boolean} array containing the elements between the
   * start and end indices.
   *
   * <p>The start current is inclusive, the end current exclusive. Null array
   * input produces null output.
   *
   * @param array
   *     the array
   * @param start
   *     the starting current. Undervalue (&lt;0) is promoted to 0, overvalue
   *     (&gt;array.length) results in an empty array.
   * @param end
   *     elements up to endIndex-1 are present in the returned subarray.
   *     Undervalue (&lt; startIndex) produces empty array, overvalue
   *     (&gt;array.length) is demoted to array length.
   * @return a new array containing the elements between the start and end
   *     indices, or {@code null} if the array is {@code null}.
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
   * Tests whether an object is an array.
   *
   * @param obj
   *     the object to be test, which could be null.
   * @return true if the object is not null and is an array; false otherwise.
   */
  public static boolean isArray(@Nullable final Object obj) {
    return ((obj != null) && obj.getClass().isArray());
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * <p>Any multi-dimensional aspects of the arrays are ignored.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static <T> boolean isSameLength(@Nullable final T[] array1,
      @Nullable final T[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final long[] array1,
      @Nullable final long[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final int[] array1,
      @Nullable final int[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final short[] array1,
      @Nullable final short[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final char[] array1,
      @Nullable final char[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final byte[] array1,
      @Nullable final byte[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final double[] array1,
      @Nullable final double[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final float[] array1,
      @Nullable final float[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Checks whether two arrays are the same length, treating {@code null} arrays
   * as length {@code 0}.
   *
   * @param array1
   *     the first array, may be {@code null}
   * @param array2
   *     the second array, may be {@code null}
   * @return {@code true} if length of arrays matches, treating {@code null} as
   *     an empty array.
   */
  public static boolean isSameLength(@Nullable final boolean[] array1,
      @Nullable final boolean[] array2) {
    return ((array1 != null) || (array2 == null) || (array2.length <= 0)) && ((
        array2
            != null) || (array1 == null) || (array1.length <= 0)) && ((array1
        == null) || (array2 == null) || (array1.length == array2.length));
  }

  /**
   * Returns the length of the specified array. This method can deal with
   * {@code Object} arrays and with primitive arrays.
   *
   * <p>If the input array is {@code null}, {@code 0} is returned.
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
   *     the array to retrieve the length from, may be null
   * @return The length of the array, or {@code 0} if the array is {@code null}
   * @throws IllegalArgumentException
   *     if the object arguement is not an array.
   */
  public static int getLength(@Nullable final Object array) {
    if (array == null) {
      return 0;
    } else {
      return Array.getLength(array);
    }
  }

  /**
   * Checks whether two arrays are the same type taking into account
   * multi-dimensional arrays.
   *
   * @param array1
   *     the first array, must not be {@code null}
   * @param array2
   *     the second array, must not be {@code null}
   * @return {@code true} if type of arrays matches
   * @throws IllegalArgumentException
   *     if either array is {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>There is no special handling for multi-dimensional arrays.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Reverses the order of the given array.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Shadow clone the given array and reverse the order of the elements.
   *
   * <p><b>There is no special handling for multi-dimensional arrays.</b>
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Clone the given array and reverse the order of the elements.
   *
   * <p>This method does nothing for a {@code null} input array.
   *
   * @param array
   *     the array to reverse, may be {@code null}
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
   * Finds the current of the given object in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the object to find, may be {@code null}
   * @return the current of the object within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static <T> int indexOf(@Nullable final T[] array,
      @Nullable final T value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given object in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the object to find, may be {@code null}
   * @param start
   *     the index to start searching at
   * @return the current of the object within the array starting at the current,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final long[] array, final long value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final int[] array, final int value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final short[] array, final short value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final char[] array, final char value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final byte[] array, final byte value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(@Nullable final double[] array,
      final double value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final double[] array, final double value,
      final int start) {
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
   * Finds the current of the given value within a given tolerance in the array.
   * This method will return the current of the first value which falls between
   * the region defined by value - tolerance and value + tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param tolerance
   *     tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final double[] array, final double value,
      final double tolerance) {
    return indexOf(array, value, 0, tolerance);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current. This method will return the current of the first value which falls
   * between the region defined by value - tolerance and value + tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @param tolerance
   *     tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final float[] array, final float value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input
   */
  public static int indexOf(@Nullable final float[] array, final float value,
      final int start) {
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
   * Finds the current of the given value within a given tolerance in the array.
   * This method will return the current of the first value which falls between
   * the region defined by value - tolerance and value + tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param tolerance
   *     tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final float[] array, final float value,
      final float tolerance) {
    return indexOf(array, value, 0, tolerance);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current. This method will return the current of the first value which falls
   * between the region defined by value - tolerance and value + tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @param tolerance
   *     tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the current of the given value in the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int indexOf(@Nullable final boolean[] array,
      final boolean value) {
    return indexOf(array, value, 0);
  }

  /**
   * Finds the current of the given value in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the index to start searching at
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
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
   * Finds the last current of the given object within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find, may be {@code null}
   * @return the last current of the object within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static <T> int lastIndexOf(@Nullable final T[] array,
      @Nullable final T value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given object in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the object to find, may be {@code null}
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the object within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final long[] array,
      final long value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input.
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final int[] array, final int value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND}
   * ({@code -1}).
   * A startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final short[] array,
      final short value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final char[] array,
      final char value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final byte[] array,
      final byte value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final double[] array,
      final double value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final double[] array,
      final double value, final int start) {
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
   * Finds the last current of the given value within a given tolerance in the
   * array. This method will return the current of the last value which falls
   * between the region defined by valueToFind - tolerance and valueToFind +
   * tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param tolerance
   *     tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int lastIndexOf(@Nullable final double[] array,
      final double value, final double tolerance) {
    return lastIndexOf(array, value, Integer.MAX_VALUE, tolerance);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current. This method will return the current of the last value which
   * falls between the region defined by valueToFind - tolerance and valueToFind
   * + tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @param tolerance
   *     search for value within plus/minus this amount
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final double[] array,
      final double value, final int start, final double tolerance) {
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(final float[] array, final float value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final float[] array,
      final float value, final int start) {
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
   * Finds the last current of the given value within a given tolerance in the
   * array. This method will return the current of the last value which falls
   * between the region defined by valueToFind - tolerance and valueToFind +
   * tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param tolerance
   *     tolerance of the search
   * @return the current of the value within the array, {@link #INDEX_NOT_FOUND}
   *     ({@code -1}) if not found or {@code null} array input.
   */
  public static int lastIndexOf(@Nullable final float[] array,
      final float value, final float tolerance) {
    return lastIndexOf(array, value, Integer.MAX_VALUE, tolerance);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current. This method will return the current of the last value which
   * falls between the region defined by valueToFind - tolerance and valueToFind
   * + tolerance.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @param tolerance
   *     search for value within plus/minus this amount
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final float[] array,
      final float value, final int start, final float tolerance) {
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
   * Finds the last current of the given value within the array.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) if
   * {@code null}
   * array input.
   *
   * @param array
   *     the array to travers backwords looking for the object, may be
   *     {@code null}
   * @param value
   *     the object to find
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
   */
  public static int lastIndexOf(@Nullable final boolean[] array,
      final boolean value) {
    return lastIndexOf(array, value, Integer.MAX_VALUE);
  }

  /**
   * Finds the last current of the given value in the array starting at the
   * given current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}
   * ). A
   * startIndex larger than the array length will search from the end of the
   * array.
   *
   * @param array
   *     the array to traverse for looking for the object, may be {@code null}
   * @param value
   *     the value to find
   * @param start
   *     the start current to travers backwards from
   * @return the last current of the value within the array,
   *     {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
   *     array input
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
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final long[] array,
      final long value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final int[] array, final int value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final short[] array,
      final short value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final char[] array,
      final char value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final byte[] array,
      final byte value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final double[] array,
      final double value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if a value falling within the given tolerance is in the given array.
   * If the array contains a value within the inclusive range defined by (value
   * - tolerance) to (value + tolerance).
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search
   * @param value
   *     the value to find
   * @param tolerance
   *     the array contains the tolerance of the search
   * @return true if value falling within tolerance is in array
   */
  public static boolean contains(@Nullable final double[] array,
      final double value, final double tolerance) {
    return indexOf(array, value, 0, tolerance) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final float[] array,
      final float value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if a value falling within the given tolerance is in the given array.
   * If the array contains a value within the inclusive range defined by (value
   * - tolerance) to (value + tolerance).
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search
   * @param value
   *     the value to find
   * @param tolerance
   *     the array contains the tolerance of the search
   * @return true if value falling within tolerance is in array
   */
  public static boolean contains(@Nullable final float[] array,
      final float value, final float tolerance) {
    return indexOf(array, value, 0, tolerance) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the value is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param value
   *     the value to find
   * @return {@code true} if the array contains the object
   */
  public static boolean contains(@Nullable final boolean[] array,
      final boolean value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks if the object is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through
   * @param value
   *     the object to find
   * @return {@code true} if the array contains the object
   */
  public static <T> boolean contains(@Nullable final T[] array, final T value) {
    return indexOf(array, value) != INDEX_NOT_FOUND;
  }

  /**
   * Checks whether the given array contains any element satisfied the specified
   * predicate.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through
   * @param predicate
   *     the specified predicate
   * @return {@code true} if the array contains an element satisfied the
   *     specified predicate.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
   */
  public static boolean containsAll(@Nullable final double[] array,
      final double[] values) {
    for (final double value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through.
   * @param values
   *     the array of values to find.
   * @param tolerance
   *     the given tolerance for floating point comparision.
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through.
   * @param values
   *     the array of values to find.
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
   */
  public static boolean containsAll(@Nullable final float[] array,
      final float[] values) {
    for (final float value : values) {
      if (!contains(array, value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through.
   * @param values
   *     the array of values to find.
   * @param tolerance
   *     the given tolerance for floating point comparision.
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all values in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the all values in the
   *     {@code values} array.
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
   * Checks if all objects in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through
   * @param values
   *     the array of objects to find
   * @return {@code true} if the array contains the all objects in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @param tolerance
   *     the tolerance of the comparison.
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through.
   * @param values
   *     the array of values to find.
   * @param tolerance
   *     the given tolerance for floating point comparision.
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Checks if any value in the specified array is in the given array.
   *
   * <p>The method returns {@code false} if a {@code null} array is passed in.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through
   * @param values
   *     the array of values to find
   * @return {@code true} if the array contains the any value in the
   *     {@code values} array.
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
   * Converts an array of object Characters to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Character} array, may be {@code null}
   * @return a {@code char} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Character to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Character} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code char} array, {@code null} if null array input
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
   * Converts an array of object Longs to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Long} array, may be {@code null}
   * @return a {@code long} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Long to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Long} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code long} array, {@code null} if null array input
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
   * Converts an array of object Integers to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Integer} array, may be {@code null}
   * @return an {@code int} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Integer to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Integer} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return an {@code int} array, {@code null} if null array input
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
   * Converts an array of object Shorts to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Short} array, may be {@code null}
   * @return a {@code byte} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Short to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Short} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code byte} array, {@code null} if null array input
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
   * Converts an array of object Bytes to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Byte} array, may be {@code null}
   * @return a {@code byte} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Bytes to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Byte} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code byte} array, {@code null} if null array input
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
   * Converts an array of object Doubles to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Double} array, may be {@code null}
   * @return a {@code double} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Doubles to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Double} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code double} array, {@code null} if null array input
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
   * Converts an array of object Floats to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Float} array, may be {@code null}
   * @return a {@code float} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Floats to primitives handling {@code null} .
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Float} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code float} array, {@code null} if null array input
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
   * Converts an array of object Booleans to primitives.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Boolean} array, may be {@code null}
   * @return a {@code boolean} array, {@code null} if null array input
   * @throws NullPointerException
   *     if array content is {@code null}
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
   * Converts an array of object Booleans to primitives handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code Boolean} array, may be {@code null}
   * @param valueForNull
   *     the value to insert if {@code null} found
   * @return a {@code boolean} array, {@code null} if null array input
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
   * Converts an array of primitive longs to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code long} array
   * @return a {@code Long} array, {@code null} if null array input
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
   * Converts an array of primitive chars to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code char} array
   * @return a {@code Character} array, {@code null} if null array input
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
   * Converts an array of primitive ints to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     an {@code int} array
   * @return an {@code Integer} array, {@code null} if null array input
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
   * Converts an array of primitive bytes to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code byte} array
   * @return a {@code Byte} array, {@code null} if null array input
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
   * Converts an array of primitive shorts to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code short} array
   * @return a {@code Short} array, {@code null} if null array input
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
   * Converts an array of primitive doubles to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code double} array
   * @return a {@code Double} array, {@code null} if null array input
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
   * Converts an array of primitive floats to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code float} array
   * @return a {@code Float} array, {@code null} if null array input
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
   * Converts an array of primitive booleans to objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     a {@code boolean} array
   * @return a {@code Boolean} array, {@code null} if null array input
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
   * Checks if an array of Objects is empty or {@code null}.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static <T> boolean isEmpty(@Nullable final T[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive longs is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final long[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive ints is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final int[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive shorts is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final short[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive chars is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final char[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive bytes is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final byte[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive doubles is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final double[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive floats is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final float[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an array of primitive booleans is empty or {@code null}.
   *
   * @param array
   *     the array to test
   * @return {@code true} if the array is empty or {@code null}
   */
  public static boolean isEmpty(@Nullable final boolean[] array) {
    return ((array == null) || (array.length == 0));
  }

  /**
   * Checks if an object is empty array or {@code null}.
   *
   * @param obj
   *     the object to test.
   * @return {@code true} if the object is an empty array or {@code null}.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(null, null)     = null
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * Arrays.addAll([null], [null]) = [null, null]
   * Arrays.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
   * </pre>
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array1
   *     the first array whose elements are added to the new array, may be
   *     {@code null}
   * @param array2
   *     the second array whose elements are added to the new array, may be
   *     {@code null}
   * @return The new array, {@code null} if {@code null} array inputs. The type
   *     of the new array is the type of the first array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new boolean[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new char[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new byte[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new short[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new int[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new long[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new float[] array.
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
   * Adds all the elements of the given arrays into a new array. The new array
   * contains all of the element of {@code array1} followed by all of the
   * elements {@code array2}. When an array is returned, it is always a new
   * array.
   *
   * <pre>
   * Arrays.addAll(array1, null)   = cloned copy of array1
   * Arrays.addAll(null, array2)   = cloned copy of array2
   * Arrays.addAll([], [])         = []
   * </pre>
   *
   * @param array1
   *     the first array whose elements are added to the new array.
   * @param array2
   *     the second array whose elements are added to the new array.
   * @return The new double[] array.
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
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
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
   *     the type of the elements in the array.
   * @param array
   *     the array to "add" the element to, may be {@code null}
   * @param element
   *     the object to add
   * @return A new array containing the existing elements plus the new element
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] add(@Nullable final T[] array,
      @Nullable final T element) {
    final Class<?> type;
    if (array != null) {
      type = array.getClass().getComponentType();
    } else if (element != null) {
      // FIXME: can we use the element.getClass() as the type of returned array?
      // For examle, assume array is of type Object[] and its value is null,
      // assume element is a string "a", then calling this function with the
      // argument of array and element, what should be the type of returned
      // array? An array of Object, or an array of String?
      //
      type = element.getClass();
    } else {
      type = Object.class;
    }
    final T[] newArray = (T[]) copyArrayGrow(array, type);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, true)          = [true]
   * Arrays.add([true], false)       = [true, false]
   * Arrays.add([true, false], true) = [true, false, true]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static boolean[] add(@Nullable final boolean[] array,
      final boolean element) {
    final boolean[] newArray = (boolean[]) copyArrayGrow(array, Boolean.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static byte[] add(@Nullable final byte[] array, final byte element) {
    final byte[] newArray = (byte[]) copyArrayGrow(array, Byte.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, '0')       = ['0']
   * Arrays.add(['1'], '0')      = ['1', '0']
   * Arrays.add(['1', '0'], '1') = ['1', '0', '1']
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static char[] add(@Nullable final char[] array, final char element) {
    final char[] newArray = (char[]) copyArrayGrow(array, Character.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static double[] add(@Nullable final double[] array,
      final double element) {
    final double[] newArray = (double[]) copyArrayGrow(array, Double.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static float[] add(@Nullable final float[] array,
      final float element) {
    final float[] newArray = (float[]) copyArrayGrow(array, Float.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static int[] add(@Nullable final int[] array, final int element) {
    final int[] newArray = (int[]) copyArrayGrow(array, Integer.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static long[] add(@Nullable final long[] array, final long element) {
    final long[] newArray = (long[]) copyArrayGrow(array, Long.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Copies the given array and adds the given element at the end of the new
   * array.
   *
   * <p>The new array contains the same elements of the input array plus the
   * given
   * element in the last position. The component type of the new array is the
   * same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0)   = [0]
   * Arrays.add([1], 0)    = [1, 0]
   * Arrays.add([1, 0], 1) = [1, 0, 1]
   * </pre>
   *
   * @param array
   *     the array to copy and add the element to, may be {@code null}
   * @param element
   *     the object to add at the last current of the new array
   * @return A new array containing the existing elements plus the new element
   */
  public static short[] add(@Nullable final short[] array,
      final short element) {
    final short[] newArray = (short[]) copyArrayGrow(array, Short.TYPE);
    newArray[newArray.length - 1] = element;
    return newArray;
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
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
   *     the type of the elements in the array.
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
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
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add(null, 0, true)          = [true]
   * Arrays.add([true], 0, false)       = [false, true]
   * Arrays.add([false], 1, true)       = [false, true]
   * Arrays.add([true, false], 1, true) = [true, true, false]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static boolean[] add(@Nullable final boolean[] array, final int index,
      final boolean element) {
    return (boolean[]) add(array, index,
        (element ? Boolean.TRUE : Boolean.FALSE), Boolean.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
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
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static char[] add(@Nullable final char[] array, final int index,
      final char element) {
    return (char[]) add(array, index, element, Character.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 3)      = [2, 6, 3]
   * Arrays.add([2, 6], 0, 1)      = [1, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static byte[] add(@Nullable final byte[] array, final int index,
      final byte element) {
    return (byte[]) add(array, index, element, Byte.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 10)     = [2, 6, 10]
   * Arrays.add([2, 6], 0, -4)     = [-4, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static short[] add(@Nullable final short[] array, final int index,
      final short element) {
    return (short[]) add(array, index, element, Short.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1], 0, 2)         = [2, 1]
   * Arrays.add([2, 6], 2, 10)     = [2, 6, 10]
   * Arrays.add([2, 6], 0, -4)     = [-4, 2, 6]
   * Arrays.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static int[] add(@Nullable final int[] array, final int index,
      final int element) {
    return (int[]) add(array, index, element, Integer.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1L], 0, 2L)           = [2L, 1L]
   * Arrays.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
   * Arrays.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
   * Arrays.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static long[] add(@Nullable final long[] array, final int index,
      final long element) {
    return (long[]) add(array, index, element, Long.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
   * Arrays.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
   * Arrays.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
   * Arrays.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static float[] add(@Nullable final float[] array, final int index,
      final float element) {
    return (float[]) add(array, index, element, Float.TYPE);
  }

  /**
   * Inserts the specified element at the specified position in the array.
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * plus the given element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, a new one element array is returned
   * whose component type is the same as the element.
   *
   * <pre>
   * Arrays.add([1.1], 0, 2.2)              = [2.2, 1.1]
   * Arrays.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
   * Arrays.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
   * Arrays.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
   * </pre>
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @return A new array containing the existing elements and the new element
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &gt;
   *     array.length).
   */
  public static double[] add(@Nullable final double[] array, final int index,
      final double element) {
    return (double[]) add(array, index, element, Double.TYPE);
  }

  /**
   * Underlying implementation of add(array, current, element) methods. The last
   * parameter is the class, which may not equal element.getClass for
   * primitives.
   *
   * @param array
   *     the array to add the element to, may be {@code null}
   * @param index
   *     the position of the new object
   * @param element
   *     the object to add
   * @param cls
   *     the type of the element being added
   * @return A new array containing the existing elements and the new element
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
   * Returns a copy of the given array of size 1 greater than the argument. The
   * last value of the array is left to the default value.
   *
   * @param array
   *     The array to copy, must not be {@code null}.
   * @param newArrayComponentType
   *     If {@code array} is {@code null}, create a size 1 array of this type.
   * @return A new copy of the array of size 1 greater than the input.
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
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove(["a"], 0)           = []
   * Arrays.remove(["a", "b"], 0)      = ["b"]
   * Arrays.remove(["a", "b"], 1)      = ["a"]
   * Arrays.remove(["a", "b", "c"], 1) = ["a", "c"]
   * </pre>
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] remove(@Nullable final T[] array, final int index) {
    final Object obj = array;
    return (T[]) remove(obj, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([true], 0)              = []
   * Arrays.remove([true, false], 0)       = [false]
   * Arrays.remove([true, false], 1)       = [true]
   * Arrays.remove([true, true, false], 1) = [true, false]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static boolean[] remove(@Nullable final boolean[] array,
      final int index) {
    return (boolean[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)          = []
   * Arrays.remove([1, 0], 0)       = [0]
   * Arrays.remove([1, 0], 1)       = [1]
   * Arrays.remove([1, 0, 1], 1)    = [1, 1]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static byte[] remove(@Nullable final byte[] array, final int index) {
    return (byte[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove(['a'], 0)           = []
   * Arrays.remove(['a', 'b'], 0)      = ['b']
   * Arrays.remove(['a', 'b'], 1)      = ['a']
   * Arrays.remove(['a', 'b', 'c'], 1) = ['a', 'c']
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static char[] remove(@Nullable final char[] array, final int index) {
    return (char[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1.1], 0)           = []
   * Arrays.remove([2.5, 6.0], 0)      = [6.0]
   * Arrays.remove([2.5, 6.0], 1)      = [2.5]
   * Arrays.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static double[] remove(@Nullable final double[] array,
      final int index) {
    return (double[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1.1], 0)           = []
   * Arrays.remove([2.5, 6.0], 0)      = [6.0]
   * Arrays.remove([2.5, 6.0], 1)      = [2.5]
   * Arrays.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static float[] remove(@Nullable final float[] array, final int index) {
    return (float[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static int[] remove(@Nullable final int[] array, final int index) {
    return (int[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static long[] remove(@Nullable final long[] array, final int index) {
    return (long[]) remove((Object) array, index);
  }

  /**
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
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
   * Removes the element at the specified position from the specified array. All
   * subsequent elements are shifted to the left (substracts one from their
   * indices).
   *
   * <p>This method returns a new array with the same elements of the input
   * array
   * except the element on the specified position. The component type of the
   * returned array is always the same as that of the input array.
   *
   * <p>If the input array is {@code null}, an IndexOutOfBoundsException will
   * be
   * thrown, because in that case no valid current can be specified.
   *
   * <pre>
   * Arrays.remove([1], 0)         = []
   * Arrays.remove([2, 6], 0)      = [6]
   * Arrays.remove([2, 6], 1)      = [2]
   * Arrays.remove([2, 6, 3], 1)   = [2, 3]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may not be {@code null}
   * @param index
   *     the position of the element to be removed
   * @return A new array containing the existing elements except the element at
   *     the specified position.
   * @throws IndexOutOfBoundsException
   *     if the current is out of range (current &lt; 0 || current &ge;
   *     array.length), or if the array is {@code null}.
   */
  public static short[] remove(@Nullable final short[] array, final int index) {
    return (short[]) remove((Object) array, index);
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
   * Arrays.removeElement(null, "a")            = null
   * Arrays.removeElement([], "a")              = []
   * Arrays.removeElement(["a"], "b")           = ["a"]
   * Arrays.removeElement(["a", "b"], "a")      = ["b"]
   * Arrays.removeElement(["a", "b", "a"], "a") = ["b", "a"]
   * </pre>
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
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
   * Arrays.removeElement(null, true)                = null
   * Arrays.removeElement([], true)                  = []
   * Arrays.removeElement([true], false)             = [true]
   * Arrays.removeElement([true, false], false)      = [true]
   * Arrays.removeElement([true, false, true], true) = [false, true]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
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
   * Arrays.removeElement(null, 1)        = null
   * Arrays.removeElement([], 1)          = []
   * Arrays.removeElement([1], 0)         = [1]
   * Arrays.removeElement([1, 0], 0)      = [1]
   * Arrays.removeElement([1, 0, 1], 1)   = [0, 1]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
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
   * Arrays.removeElement(null, 'a')            = null
   * Arrays.removeElement([], 'a')              = []
   * Arrays.removeElement(['a'], 'b')           = ['a']
   * Arrays.removeElement(['a', 'b'], 'a')      = ['b']
   * Arrays.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
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
   * Removes the first occurrence of the specified element from the specified
   * array. All subsequent elements are shifted to the left (substracts one from
   * their indices). If the array doesn't contains such an element, no elements
   * are removed from the array.
   *
   * <p>This method returns a new array with the same elements of the input
   * array except the first occurrence of the specified element. The component
   * type of the returned array is always the same as that of the input array.
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
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
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
   * Arrays.removeElement(null, 1.1)            = null
   * Arrays.removeElement([], 1.1)              = []
   * Arrays.removeElement([1.1], 1.2)           = [1.1]
   * Arrays.removeElement([1.1, 2.3], 1.1)      = [2.3]
   * Arrays.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
   * </pre>
   *
   * @param array
   *     the array to remove the element from, may be {@code null}
   * @param element
   *     the element to be removed
   * @return A new array containing the existing elements except the first
   *     occurrence of the specified element.
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
   *     a {@code char} array, sorted from the lower to the higher.
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
   *     a {@code char} array, sorted from the lower to the higher.
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
   *     a {@code byte} array, sorted from the lower to the higher.
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
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the sum of all values in the array.
   */
  public static int sum(final byte[] array) {
    int result = 0;
    for (final byte v : array) {
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
   * Sums the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the sum of all values in the array.
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
   * Products the values of elements in an array.
   *
   * @param array
   *     the array of values.
   * @return the production of all values in the array.
   */
  public static double product(final byte[] array) {
    double result = 1;
    for (final byte v : array) {
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

  public static <T> T[] nullIfEmpty(@Nullable final T[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static boolean[] nullIfEmpty(@Nullable final boolean[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static char[] nullIfEmpty(@Nullable final char[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static byte[] nullIfEmpty(@Nullable final byte[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static short[] nullIfEmpty(@Nullable final short[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static int[] nullIfEmpty(@Nullable final int[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static long[] nullIfEmpty(@Nullable final long[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static float[] nullIfEmpty(@Nullable final float[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  public static double[] nullIfEmpty(@Nullable final double[] array) {
    if (array == null || array.length == 0) {
      return null;
    } else {
      return array;
    }
  }

  /**
   * Excludes all elements of a given array from the specified array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the specified array.
   * @param toExclude
   *     the array contains the elements to be excluded.
   * @return an new array which contains only the elements in the specified
   *     array but not in the excluded element array.
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
   * Creates a new array which has the same element type with an existing
   * array.
   *
   * @param <T>
   *     the type of elements in the array.
   * @param array
   *     the existing array.
   * @param n
   *     the length of the new array.
   * @return a new array with the same element type as the specified array.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] createArrayOfSameElementType(final T[] array, final int n) {
    final Class<?> componentType = array.getClass().getComponentType();
    final Object result = Array.newInstance(componentType, n);
    return (T[]) result;
  }

  /**
   * Creates a new array which has the specified element type and specified
   * length.
   *
   * @param <T>
   *     the type of elements in the array.
   * @param elementType
   *     the specified element type of the new array.
   * @param n
   *     the length of the new array.
   * @return a new array with the same element type as the specified array.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] createArray(final Class<T> elementType, final int n) {
    return (T[]) Array.newInstance(elementType, n);
  }

  /**
   * Finds the current of the given object in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param condition
   *     the value to find should satisfy this condition.
   * @return the index of the specified object within the array, or
   *     {@link #INDEX_NOT_FOUND} ({@value #INDEX_NOT_FOUND}) if not found or
   *     {@code null} array input.
   */
  public static <T> int findIndex(@Nullable final T[] array,
      final Predicate<T> condition) {
    return findIndex(array, condition, 0);
  }

  /**
   * Finds the current of the given object in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param condition
   *     the value to find should satisfy this condition.
   * @param start
   *     the index to start searching at
   * @return the index of the specified object within the array starting from
   *     the specified position, or {@link #INDEX_NOT_FOUND}
   *     ({@value #INDEX_NOT_FOUND}) if not found or {@code null} array input.
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
   * Finds the current of the given object in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param condition
   *     the value to find should satisfy this condition.
   * @return the specified object within the array, or {@code null} if not found
   *     or {@code null} array input.
   */
  public static <T> T find(@Nullable final T[] array,
      final Predicate<T> condition) {
    return find(array, condition, 0);
  }

  /**
   * Finds the current of the given object in the array starting at the given
   * current.
   *
   * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code
   * null} input array.
   *
   * <p>A negative startIndex is treated as zero. A startIndex larger than the
   * array length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array to search through for the object, may be {@code null}
   * @param condition
   *     the value to find should satisfy this condition.
   * @param start
   *     the index to start searching at
   * @return the specified object within the array starting from the specified
   *     position, or {@code null} if not found or {@code null} array input.
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

  public static boolean[] nullToEmpty(@Nullable final boolean[] array) {
    return (array == null ? EMPTY_BOOLEAN_ARRAY : array);
  }

  public static char[] nullToEmpty(@Nullable final char[] array) {
    return (array == null ? EMPTY_CHAR_ARRAY : array);
  }

  public static byte[] nullToEmpty(@Nullable final byte[] array) {
    return (array == null ? EMPTY_BYTE_ARRAY : array);
  }

  public static short[] nullToEmpty(@Nullable final short[] array) {
    return (array == null ? EMPTY_SHORT_ARRAY : array);
  }

  public static int[] nullToEmpty(@Nullable final int[] array) {
    return (array == null ? EMPTY_INT_ARRAY : array);
  }

  public static long[] nullToEmpty(@Nullable final long[] array) {
    return (array == null ? EMPTY_LONG_ARRAY : array);
  }

  public static float[] nullToEmpty(@Nullable final float[] array) {
    return (array == null ? EMPTY_FLOAT_ARRAY : array);
  }

  public static double[] nullToEmpty(@Nullable final double[] array) {
    return (array == null ? EMPTY_DOUBLE_ARRAY : array);
  }

  public static CharSequence[] nullToEmpty(@Nullable final CharSequence[] array) {
    return (array == null ? EMPTY_STRING_ARRAY : array);
  }

  public static String[] nullToEmpty(@Nullable final String[] array) {
    return (array == null ? EMPTY_STRING_ARRAY : array);
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] nullToEmpty(@Nullable final T[] array) {
    return (array == null ? (T[]) EMPTY_OBJECT_ARRAY : array);
  }

  /**
   * Gets the stream of a nullable array.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param array
   *     the array, which could be {@code null}.
   * @return
   *     the stream of the array, or an empty stream if the array is {@code null}.
   */
  public static <T> Stream<T> stream(@Nullable final T[] array) {
    return (array == null ? Stream.empty() : Arrays.stream(array));
  }

  /**
   * Performs the given action for each element of the array until all elements
   * have been processed or the action throws an exception.  Actions are
   * performed in the order of the array. Exceptions thrown by the action are
   * relayed to the caller.
   * <p>
   * The behavior of this method is unspecified if the action performs side
   * effects that modify the underlying source of elements, unless an overriding
   * class has specified a concurrent modification policy.
   * <p>
   * The default implementation behaves as if:
   * <pre>{@code
   *    if (array != null) {
   *      for (final T value : array) {
   *        action.accept(value);
   *      }
   *    }
   * }</pre>
   *
   * @param <T>
   *     The type of the element in the array.
   * @param array
   *     An {@code array} to be iterated, which may be {@code null}.
   * @param action
   *     The action to be performed for each element.
   * @throws NullPointerException
   *     if the specified action is null.
   */
  public static <T> void forEach(@Nullable final T[] array, final Consumer<? super T> action) {
    if (array != null) {
      for (final T value : array) {
        action.accept(value);
      }
    }
  }

  /**
   * Returns a new array containing specified copies of the specified object.
   * <p>
   * This function perform the shallow copy of the specified object.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param value
   *     the object to be copied.
   * @param n
   *     the number of copies to be made.
   * @return
   *     an array containing {@code n} copies of the specified object.
   */
  public static <T> T[] duplicate(final T value, final int n) {
    @SuppressWarnings("unchecked")
    final Class<T> elementType = (Class<T>) value.getClass();
    final T[] result = createArray(elementType, n);
    Arrays.fill(result, value);
    return result;
  }

  /**
   * Returns a new array containing specified copies of deep clones of the
   * specified object.
   * <p>
   * This function perform the deep clone of the specified object.
   *
   * @param <T>
   *     the type of the elements in the array.
   * @param value
   *     the object to be copied.
   * @param n
   *     the number of copies to be made.
   * @return
   *     an array containing {@code n} copies of deep clone of the specified object.
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
