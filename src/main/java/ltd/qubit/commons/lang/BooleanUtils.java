////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;

import com.google.common.collect.ImmutableSet;

/**
 * This class provides operations on {@code boolean} primitives and {@link
 * Boolean} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@code boolean} values or
 * {@link Boolean} objects to common types.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class BooleanUtils {

  /**
   * The default {@code boolean} value used when necessary.
   */
  public static final boolean DEFAULT = false;

  private BooleanUtils() {}

  public static <T> T choose(final Boolean condition, final T trueValue,
      final T otherValue) {
    return isTrue(condition) ? trueValue : otherValue;
  }

  /**
   * Checks if a {@code Boolean} value is {@code true}, handling {@code null} by
   * returning {@code false}.
   *
   * <pre>
   *    isTrue(Boolean.TRUE)  = true
   *    isTrue(Boolean.FALSE) = false
   *    isTrue(null)          = false
   * </pre>
   *
   * @param value
   *     the boolean to check, null returns {@code false}
   * @return {@code true} only if the input is non-null and true
   */
  public static boolean isTrue(final Boolean value) {
    if (value == null) {
      return false;
    } else {
      return value;
    }
  }

  /**
   * Checks if a {@code Boolean} value is <i>not</i> {@code true}, handling
   * {@code null} by returning {@code true}.
   *
   * <pre>
   *    isNotTrue(Boolean.TRUE)  = false
   *    isNotTrue(Boolean.FALSE) = true
   *    isNotTrue(null)          = true
   * </pre>
   *
   * @param value
   *     the boolean to check, null returns {@code true}
   * @return {@code true} if the input is null or false
   */
  public static boolean isNotTrue(final Boolean value) {
    return ((value == null) || (!value));
  }

  /**
   * Checks if a {@code Boolean} value is {@code false}, handling {@code null}
   * by returning {@code false}.
   *
   * <pre>
   *    isFalse(Boolean.TRUE)  = false
   *    isFalse(Boolean.FALSE) = true
   *    isFalse(null)          = false
   * </pre>
   *
   * @param value
   *     the boolean to check, null returns {@code false}
   * @return {@code true} only if the input is non-null and false
   */
  public static boolean isFalse(final Boolean value) {
    if (value == null) {
      return false;
    } else {
      return (!value);
    }
  }

  /**
   * Checks if a {@code Boolean} value is <i>not</i> {@code false}, handling
   * {@code null} by returning {@code true}.
   *
   * <pre>
   *    isNotFalse(Boolean.TRUE)  = true
   *    isNotFalse(Boolean.FALSE) = false
   *    isNotFalse(null)          = true
   * </pre>
   *
   * @param value
   *     the boolean to check, null returns {@code true}
   * @return {@code true} if the input is null or true
   */
  public static boolean isNotFalse(final Boolean value) {
    return ((value == null) || value);
  }

  /**
   * Negates the specified boolean.
   *
   * <p>If {@code null} is passed in, {@code null} will be returned.
   *
   * <pre>
   *     negate(Boolean.TRUE) = Boolean.FALSE;
   *     negate(Boolean.FALSE) = Boolean.TRUE;
   *     negate(null) = null;
   * </pre>
   *
   * @param value
   *     the Boolean to negate, may be {@code null}.
   * @return the negated Boolean, or {@code null} if {@code null} input.
   */
  public static Boolean negate(@Nullable final Boolean value) {
    if (value == null) {
      return null;
    }
    return (value ? Boolean.FALSE : Boolean.TRUE);
  }

  /**
   * Performs a logic {@code and} on an array of {@code boolean} values.
   *
   * <pre>
   *    and(new boolean[] { true, true })                 = true
   *    and(new boolean[] { false, false })               = false
   *    and(new boolean[] { true, false })                = false
   *    and(new boolean[] { true, false, true })          = false
   *    and(new boolean[] { true, false, true, true })    = false
   *    and(new boolean[] { true, true, true, true })     = true
   *    and(new boolean[] { false, false, false, false }) = false
   * </pre>
   *
   * @param array
   *     an array of {@code Boolean} values.
   * @return the result of the logic {@code and} on the array of {@code Boolean}
   *     values. Note that it will returns true if the array is empty.
   * @throws NullPointerException
   *     if {@code array} is {@code null}.
   */
  public static boolean and(final boolean... array) {
    for (final boolean value : array) {
      if (!value) {
        return false;
      }
    }
    return true;
  }

  /**
   * Performs a logic {@code and} on an {@link BooleanCollection}.
   *
   * @param col
   *     an {@link BooleanCollection} objects.
   * @return the result of the logic {@code and} on the {@link
   *     BooleanCollection} objects. Note that it will returns {@code true} if
   *     the {@code col} is empty.
   */
  public static boolean and(final BooleanCollection col) {
    final BooleanIterator iter = col.iterator();
    while (iter.hasNext()) {
      final boolean value = iter.next();
      if (!value) {
        return false;
      }
    }
    return true;
  }

  /**
   * Performs a logic {@code and} on an array of {@code Boolean} objects.
   *
   * <pre>
   *    and(new Boolean[] { true, true })                 = Boolean.TRUE
   *    and(new Boolean[] { false, false })               = Boolean.FALSE
   *    and(new Boolean[] { true, false })                = Boolean.FALSE
   *    and(new Boolean[] { true, false, true })          = Boolean.FALSE
   *    and(new Boolean[] { true, false, true, true })    = Boolean.FALSE
   *    and(new Boolean[] { true, true, true, true })     = Boolean.TRUE
   *    and(new Boolean[] { false, false, false, false }) = Boolean.FALSE
   *    and(new Boolean[] { false, null, false, false })  = null
   * </pre>
   *
   * @param array
   *     an array of {@code Boolean} objects.
   * @return the result of the logic {@code and} on the array of {@code Boolean}
   *     objects; or {@code null} if any element of the array is {@code null}.
   *     Note that it will returns {@code Boolean.TRUE} if the array is empty.
   * @throws NullPointerException
   *     if {@code array} is {@code null}.
   */
  public static Boolean and(final Boolean... array) {
    for (int i = 0; i < array.length; ++i) {
      final Boolean value = array[i];
      if (value == null) {
        return null;
      }
      if (!value) {
        // don't forget to check the null of the rest elements.
        for (int j = i + 1; j < array.length; ++j) {
          if (array[j] == null) {
            return null;
          }
        }
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  /**
   * Performs a logic {@code and} on an {@code Iterable} of {@code Boolean}
   * objects.
   *
   * @param iterable
   *     an {@code Iterable} of {@code Boolean} objects.
   * @return the result of the logic {@code and} on the iterable of {@code
   *     Boolean} objects; or {@code null} if any element of the {@code
   *     iterable} is {@code null}. Note that it will returns {@code
   *     Boolean.TRUE} if the {@code iterable} is empty.
   * @throws NullPointerException
   *     if {@code iterable} is {@code null}.
   */
  public static Boolean and(final Iterable<Boolean> iterable) {
    final Iterator<Boolean> iter = iterable.iterator();
    while (iter.hasNext()) {
      final Boolean value = iter.next();
      if (value == null) {
        return null;
      }
      if (!value) {
        // don't forget to check the null of the rest elements.
        while (iter.hasNext()) {
          if (iter.next() == null) {
            return null;
          }
        }
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  /**
   * Performs a logic {@code or} on an array of {@code boolean} values.
   *
   * <pre>
   *    or(new boolean[] { true, true })                 = true
   *    or(new boolean[] { false, false })               = false
   *    or(new boolean[] { true, false })                = true
   *    or(new boolean[] { true, false, true })          = true
   *    or(new boolean[] { true, false, true, true })    = true
   *    or(new boolean[] { true, true, true, true })     = true
   *    or(new boolean[] { false, false, false, false }) = false
   * </pre>
   *
   * @param array
   *     an array of {@code Boolean} values.
   * @return the result of the logic {@code or} on the array of {@code Boolean}
   *     values. Note that it will returns false if the array is empty.
   * @throws NullPointerException
   *     if {@code array} is {@code null}.
   */
  public static boolean or(final boolean[] array) {
    for (final boolean value : array) {
      if (value) {
        return true;
      }
    }
    return false;
  }

  /**
   * Performs a logic {@code or} on an {@link BooleanCollection}.
   *
   * @param col
   *     an {@link BooleanCollection} objects.
   * @return the result of the logic {@code or} on the {@link BooleanCollection}
   *     objects. Note that it will returns {@code false} if the {@code col} is
   *     empty.
   */
  public static boolean or(final BooleanCollection col) {
    final BooleanIterator iter = col.iterator();
    while (iter.hasNext()) {
      final boolean value = iter.next();
      if (value) {
        return true;
      }
    }
    return false;
  }

  /**
   * Performs a logic {@code or} on an array of {@code Boolean} objects.
   *
   * <pre>
   *    or(new Boolean[] { true, true })                 = Boolean.TRUE
   *    or(new Boolean[] { false, false })               = Boolean.FALSE
   *    or(new Boolean[] { true, false })                = Boolean.TRUE
   *    or(new Boolean[] { true, false, true })          = Boolean.TRUE
   *    or(new Boolean[] { true, false, true, true })    = Boolean.TRUE
   *    or(new Boolean[] { true, true, true, true })     = Boolean.TRUE
   *    or(new Boolean[] { false, false, false, false }) = Boolean.FALSE
   *    or(new Boolean[] { false, null, false, false })  = null
   * </pre>
   *
   * @param array
   *     an array of {@code Boolean} objects.
   * @return the result of the logic {@code or} on the array of {@code Boolean}
   *     objects; or {@code null} if any element of the array is {@code null}.
   *     Note that it will returns {@code Boolean.FALSE} if the array is empty.
   * @throws NullPointerException
   *     if {@code array} is {@code null}.
   */
  public static Boolean or(final Boolean... array) {
    for (int i = 0; i < array.length; ++i) {
      final Boolean value = array[i];
      if (value == null) {
        return null;
      }
      if (value) {
        // don't forget to check the null of the rest elements.
        for (int j = i + 1; j < array.length; ++j) {
          if (array[j] == null) {
            return null;
          }
        }
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }

  /**
   * Performs a logic {@code or} on an {@code Iterable} of {@code Boolean}
   * objects.
   *
   * @param iterable
   *     an {@code Iterable} of {@code Boolean} objects.
   * @return the result of the logic {@code or} on the iterable of {@code
   *     Boolean} objects; or null if any element of the {@code iterable} is
   *     null . Note that it will returns {@code Boolean.FALSE} if the {@code
   *     iterable} is empty.
   * @throws NullPointerException
   *     if {@code iterable} is {@code null}.
   */
  public static Boolean or(final Iterable<Boolean> iterable) {
    final Iterator<Boolean> iter = iterable.iterator();
    while (iter.hasNext()) {
      final Boolean value = iter.next();
      if (value == null) {
        return null;
      }
      if (value) {
        // don't forget to check the null of the rest elements.
        while (iter.hasNext()) {
          if (iter.next() == null) {
            return null;
          }
        }
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }

  /**
   * Performs a logic {@code xor} on an array of {@code boolean} values.
   *
   * <pre>
   *    xor(new boolean[] { true, true })                 = false
   *    xor(new boolean[] { false, false })               = false
   *    xor(new boolean[] { true, false })                = true
   *    xor(new boolean[] { true, false, true })          = false
   *    xor(new boolean[] { true, false, true, true })    = true
   *    xor(new boolean[] { true, true, true, true })     = false
   *    xor(new boolean[] { false, false, false, false }) = false
   * </pre>
   *
   * @param array
   *     an array of {@code Boolean} values.
   * @return the result of the logic {@code xor} on the array of {@code Boolean}
   *     values. Note that it will returns false if the array is empty.
   * @throws NullPointerException
   *     if {@code array} is {@code null}.
   */
  public static boolean xor(final boolean... array) {
    int trueCount = 0;
    for (final boolean element : array) {
      if (element) {
        ++trueCount;
      }
    }
    // returns true if there were odd number of true items
    return ((trueCount & 1) != 0);
  }

  /**
   * Performs a logic {@code xor} on an {@link BooleanCollection}.
   *
   * @param col
   *     an {@link BooleanCollection} objects.
   * @return the result of the logic {@code xor} on the {@link
   *     BooleanCollection} objects. Note that it will returns {@code false} if
   *     the {@code col} is empty.
   */
  public static boolean xor(final BooleanCollection col) {
    final BooleanIterator iter = col.iterator();
    int trueCount = 0;
    while (iter.hasNext()) {
      final boolean value = iter.next();
      if (value) {
        ++trueCount;
      }
    }
    // returns true if there were odd number of true items
    return ((trueCount & 1) != 0);
  }

  /**
   * Performs a logic {@code xor} on an array of {@code Boolean} objects.
   *
   * <pre>
   *    xor(new Boolean[] { true, true })                 = Boolean.FALSE
   *    xor(new Boolean[] { false, false })               = Boolean.FALSE
   *    xor(new Boolean[] { true, false })                = Boolean.TRUE
   *    xor(new Boolean[] { true, false, true })          = Boolean.FALSE
   *    xor(new Boolean[] { true, false, true, true })    = Boolean.TRUE
   *    xor(new Boolean[] { true, true, true, true })     = Boolean.FALSE
   *    xor(new Boolean[] { false, false, false, false }) = Boolean.FALSE
   *    xor(new Boolean[] { false, null, false, false })  = null
   * </pre>
   *
   * @param array
   *     an array of {@code Boolean} objects.
   * @return the result of the logic {@code xor} on the array of {@code Boolean}
   *     objects; or {@code null} if any element of the array is {@code null}.
   *     Note that it will returns {@code Boolean.FALSE} if the array is empty.
   * @throws NullPointerException
   *     if {@code array} is {@code null}.
   */
  public static Boolean xor(final Boolean... array) {
    int trueCount = 0;
    for (final Boolean element : array) {
      if (element == null) {
        return null;
      } else if (element) {
        ++trueCount;
      }
    }
    // returns true if there were odd number of true items
    return ((trueCount & 1) != 0);
  }

  /**
   * Performs a logic {@code xor} on an {@code Iterable} of {@code Boolean}
   * objects.
   *
   * @param iterable
   *     an {@code Iterable} of {@code Boolean} objects.
   * @return the result of the logic {@code xor} on the iterable of {@code
   *     Boolean} objects; or {@code null} if any element of the {@code
   *     iterable} is {@code null}. Note that it will returns {@code
   *     Boolean.FALSE} if the {@code iterable} is empty.
   * @throws NullPointerException
   *     if {@code iterable} is {@code null}.
   */
  public static Boolean xor(final Iterable<Boolean> iterable) {
    final Iterator<Boolean> iter = iterable.iterator();
    int trueCount = 0;
    while (iter.hasNext()) {
      final Boolean value = iter.next();
      if (value == null) {
        return null;
      }
      if (value) {
        ++trueCount;
      }
    }
    // Returns true if there were odd number of true items
    return ((trueCount & 1) != 0);
  }

  /**
   * Converts a {@code Boolean} object to a {@code boolean} value, handling
   * {@code null} by returning {@code false}.
   *
   * <pre>
   *    toPrimitive(Boolean.TRUE)  = true
   *    toPrimitive(Boolean.FALSE) = false
   *    toPrimitive(null)          = BooleanUtils.DEFAULT
   * </pre>
   *
   * @param value
   *     the {@code Boolean} object to convert, which could be null.
   * @return the {@code boolean} value of the {@code Boolean} object; or {@link
   *     #DEFAULT} if the {@code Boolean} object is null.
   */
  public static boolean toPrimitive(@Nullable final Boolean value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * Converts a {@code Boolean} object to a {@code boolean} value, handling
   * {@code null} by returning {@code defaultValue}.
   *
   * <pre>
   *    toPrimitive(Boolean.TRUE, false) = true
   *    toPrimitive(Boolean.FALSE, true) = false
   *    toPrimitive(null, true)          = true
   * </pre>
   *
   * @param value
   *     the {@code Boolean} object to convert, which could be null.
   * @param defaultValue
   *     the default value.
   * @return the {@code boolean} value of the {@code Boolean} object; or the
   *     default value if the {@code Boolean} object is null.
   */
  public static boolean toPrimitive(@Nullable final Boolean value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : value);
  }

  public static char toChar(final boolean value) {
    return (value ? (char) 1 : (char) 0);
  }

  public static char toChar(@Nullable final Boolean value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.booleanValue()));
  }

  public static char toChar(@Nullable final Boolean value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.booleanValue()));
  }

  public static Character toCharObject(final boolean value) {
    return toChar(value);
  }

  public static Character toCharObject(@Nullable final Boolean value) {
    return (value == null ? null : toCharObject(value.booleanValue()));
  }

  public static Character toCharObject(@Nullable final Boolean value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.booleanValue()));
  }

  public static byte toByte(final boolean value) {
    return (value ? (byte) 1 : (byte) 0);
  }

  public static byte toByte(@Nullable final Boolean value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.booleanValue()));
  }

  public static byte toByte(@Nullable final Boolean value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.booleanValue()));
  }

  public static Byte toByteObject(final boolean value) {
    return toByte(value);
  }

  public static Byte toByteObject(@Nullable final Boolean value) {
    return (value == null ? null : toByteObject(value.booleanValue()));
  }

  public static Byte toByteObject(@Nullable final Boolean value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.booleanValue()));
  }

  public static short toShort(final boolean value) {
    return (value ? (short) 1 : (short) 0);
  }

  public static short toShort(@Nullable final Boolean value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.booleanValue()));
  }

  public static short toShort(@Nullable final Boolean value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.booleanValue()));
  }

  public static Short toShortObject(final boolean value) {
    return toShort(value);
  }

  public static Short toShortObject(@Nullable final Boolean value) {
    return (value == null ? null : toShortObject(value.booleanValue()));
  }

  public static Short toShortObject(@Nullable final Boolean value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.booleanValue()));
  }

  public static int toInt(final boolean value) {
    return (value ? 1 : 0);
  }

  public static int toInt(@Nullable final Boolean value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.booleanValue()));
  }

  public static int toInt(@Nullable final Boolean value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.booleanValue()));
  }

  public static Integer toIntObject(final boolean value) {
    return toInt(value);
  }

  public static Integer toIntObject(@Nullable final Boolean value) {
    return (value == null ? null : toIntObject(value.booleanValue()));
  }

  public static Integer toIntObject(@Nullable final Boolean value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.booleanValue()));
  }

  public static long toLong(final boolean value) {
    return (value ? 1L : 0L);
  }

  public static long toLong(@Nullable final Boolean value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.booleanValue()));
  }

  public static long toLong(@Nullable final Boolean value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.booleanValue()));
  }

  public static Long toLongObject(final boolean value) {
    return toLong(value);
  }

  public static Long toLongObject(@Nullable final Boolean value) {
    return (value == null ? null : toLongObject(value.booleanValue()));
  }

  public static Long toLongObject(@Nullable final Boolean value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.booleanValue()));
  }

  public static float toFloat(final boolean value) {
    return (value ? 1.0f : 0.0f);
  }

  public static float toFloat(@Nullable final Boolean value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.booleanValue()));
  }

  public static float toFloat(@Nullable final Boolean value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.booleanValue()));
  }

  public static Float toFloatObject(final boolean value) {
    return toFloat(value);
  }

  public static Float toFloatObject(@Nullable final Boolean value) {
    return (value == null ? null : toFloatObject(value.booleanValue()));
  }

  public static Float toFloatObject(@Nullable final Boolean value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.booleanValue()));
  }

  public static double toDouble(final boolean value) {
    return (value ? 1.0 : 0.0);
  }

  public static double toDouble(@Nullable final Boolean value) {
    return (value == null ? DoubleUtils.DEFAULT
                          : toDouble(value.booleanValue()));
  }

  public static double toDouble(@Nullable final Boolean value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.booleanValue()));
  }

  public static Double toDoubleObject(final boolean value) {
    return toDouble(value);
  }

  public static Double toDoubleObject(@Nullable final Boolean value) {
    return (value == null ? null : toDoubleObject(value.booleanValue()));
  }

  public static Double toDoubleObject(@Nullable final Boolean value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue
                          : toDoubleObject(value.booleanValue()));
  }

  public static Date toDate(final boolean value) {
    return new Date((value) ? 1L : 0L);
  }

  public static Date toDate(@Nullable final Boolean value) {
    return (value == null ? null : toDate(value.booleanValue()));
  }

  public static Date toDate(@Nullable final Boolean value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.booleanValue()));
  }

  public static byte[] toByteArray(final boolean value) {
    return new byte[]{(value ? (byte) 1 : (byte) 0)};
  }

  public static byte[] toByteArray(@Nullable final Boolean value) {
    return (value == null ? null : toByteArray(value.booleanValue()));
  }

  public static byte[] toByteArray(@Nullable final Boolean value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.booleanValue()));
  }

  public static Class<?> toClass(final boolean value) {
    return Boolean.TYPE;
  }

  public static Class<?> toClass(@Nullable final Boolean value) {
    return (value == null ? null : Boolean.class);
  }

  public static Class<?> toClass(@Nullable final Boolean value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Boolean.class);
  }

  public static BigInteger toBigInteger(final boolean value) {
    return (value ? BigInteger.ONE : BigInteger.ZERO);
  }

  public static BigInteger toBigInteger(@Nullable final Boolean value) {
    return (value == null ? null : toBigInteger(value.booleanValue()));
  }

  public static BigInteger toBigInteger(@Nullable final Boolean value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : toBigInteger(value.booleanValue()));
  }

  public static BigDecimal toBigDecimal(final boolean value) {
    return (value ? BigDecimal.ONE : BigDecimal.ZERO);
  }

  public static BigDecimal toBigDecimal(@Nullable final Boolean value) {
    return (value == null ? null : toBigDecimal(value.booleanValue()));
  }

  public static BigDecimal toBigDecimal(@Nullable final Boolean value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : toBigDecimal(value.booleanValue()));
  }

  public static String toString(final boolean value) {
    return (value ? StringUtils.TRUE : StringUtils.FALSE);
  }

  public static String toString(@Nullable final Boolean value) {
    return (value == null ? null : toString(value.booleanValue()));
  }

  public static String toString(@Nullable final Boolean value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.booleanValue()));
  }

  /**
   * Converts a boolean to a String returning one of the input Strings.
   *
   * <pre>
   *    toString(true, "true", "false")   = "true"
   *    toString(false, "true", "false")  = "false"
   * </pre>
   *
   * @param value
   *     the Boolean to check
   * @param trueString
   *     the String to return if {@code true}, may be {@code null}
   * @param falseString
   *     the String to return if {@code false}, may be {@code null}
   * @return one of the two input Strings
   */
  public static String toString(final boolean value, final String trueString,
      final String falseString) {
    return (value ? trueString : falseString);
  }

  /**
   * Converts a Boolean to a String returning one of the input Strings.
   *
   * <pre>
   *    toString(Boolean.TRUE, "true", "false", null)   = "true"
   *    toString(Boolean.FALSE, "true", "false", null)  = "false"
   *    toString(null, "true", "false", null)           = null;
   * </pre>
   *
   * @param value
   *     the Boolean to check
   * @param trueString
   *     the String to return if {@code true}, may be {@code null}
   * @param falseString
   *     the String to return if {@code false}, may be {@code null}
   * @param nullString
   *     the String to return if {@code null}, may be {@code null}
   * @return one of the three input Strings
   */
  public static String toString(final Boolean value, final String trueString,
      final String falseString, final String nullString) {
    if (value == null) {
      return nullString;
    } else {
      return (value ? trueString : falseString);
    }
  }

  /**
   * Converts a boolean to a String returning {@code 'on'} or {@code 'off'}.
   *
   * <pre>
   *    toStringOnOff(true)   = "on"
   *    toStringOnOff(false)  = "off"
   * </pre>
   *
   * @param value
   *     the Boolean to check
   * @return {@code 'on'}, {@code 'off'}, or {@code null}
   */
  public static String toStringOnOff(final boolean value) {
    return (value ? StringUtils.ON : StringUtils.OFF);
  }

  /**
   * Converts a Boolean to a String returning {@code 'on'}, {@code 'off'}, or
   * {@code null}.
   *
   * <pre>
   *    toStringOnOff(Boolean.TRUE)  = "on"
   *    toStringOnOff(Boolean.FALSE) = "off"
   *    toStringOnOff(null)          = null;
   * </pre>
   *
   * @param value
   *     the Boolean to check
   * @return {@code 'on'}, {@code 'off'}, or {@code null}
   */
  public static String toStringOnOff(final Boolean value) {
    if (value == null) {
      return null;
    } else {
      return (value ? StringUtils.ON : StringUtils.OFF);
    }
  }

  /**
   * Converts a boolean to a String returning {@code 'yes'} or {@code 'no'}.
   *
   * <pre>
   *    toStringYesNo(true)   = "yes"
   *    toStringYesNo(false)  = "no"
   * </pre>
   *
   * @param value
   *     the Boolean to check
   * @return {@code 'yes'}, {@code 'no'}, or {@code null}
   */
  public static String toStringYesNo(final boolean value) {
    return (value ? StringUtils.YES : StringUtils.NO);
  }

  /**
   * Converts a Boolean to a String returning {@code 'yes'}, {@code 'no'}, or
   * {@code null}.
   *
   * <pre>
   *    toStringYesNo(Boolean.TRUE)  = "yes"
   *    toStringYesNo(Boolean.FALSE) = "no"
   *    toStringYesNo(null)          = null;
   * </pre>
   *
   * @param value
   *     the Boolean to check
   * @return {@code 'yes'}, {@code 'no'}, or {@code null}
   */
  public static String toStringYesNo(final Boolean value) {
    if (value == null) {
      return null;
    } else {
      return (value ? StringUtils.YES : StringUtils.NO);
    }
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(boolean.class, byte.class, short.class, int.class,
          long.class, float.class, double.class, Boolean.class, Byte.class,
          Short.class, Integer.class, Long.class, Float.class, Double.class,
          BigInteger.class, BigDecimal.class);

  /**
   * 测试指定的类型的值是否可以和{@code boolean}或{@code Boolean}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code boolean}或{@code Boolean}类型的值进行比较，返回
   *     {@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type);
  }
}
