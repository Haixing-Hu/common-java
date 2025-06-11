////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import com.google.common.collect.ImmutableSet;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.BooleanIterator;

/**
 * 此类提供了操作 {@code boolean} 基本类型和 {@link Boolean} 对象的方法。
 *
 * <p>此类尝试优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法都在其详细文档中记录了其行为。
 *
 * <p>此类还处理从 {@code boolean} 值或 {@link Boolean} 对象到常见类型的转换。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class BooleanUtils {

  /**
   * 必要时使用的默认 {@code boolean} 值。
   */
  public static final boolean DEFAULT = false;

  private BooleanUtils() {}

  /**
   * 根据布尔条件选择值。
   *
   * @param condition
   *     条件值
   * @param trueValue
   *     条件为真时返回的值
   * @param otherValue
   *     条件为假或null时返回的值
   * @param <T>
   *     值的类型
   * @return
   *     如果条件为真，返回 {@code trueValue}；否则返回 {@code otherValue}
   */
  public static <T> T choose(final Boolean condition, final T trueValue,
      final T otherValue) {
    return isTrue(condition) ? trueValue : otherValue;
  }

  /**
   * 检查 {@code Boolean} 值是否为 {@code true}，通过返回 {@code false} 来处理 {@code null}。
   *
   * <pre>
   *    isTrue(Boolean.TRUE)  = true
   *    isTrue(Boolean.FALSE) = false
   *    isTrue(null)          = false
   * </pre>
   *
   * @param value
   *     要检查的布尔值，null 返回 {@code false}
   * @return
   *     只有当输入非null且为true时才返回 {@code true}
   */
  public static boolean isTrue(final Boolean value) {
    if (value == null) {
      return false;
    } else {
      return value;
    }
  }

  /**
   * 检查 {@code Boolean} 值是否<i>不是</i> {@code true}，通过返回 {@code true} 来处理 {@code null}。
   *
   * <pre>
   *    isNotTrue(Boolean.TRUE)  = false
   *    isNotTrue(Boolean.FALSE) = true
   *    isNotTrue(null)          = true
   * </pre>
   *
   * @param value
   *     要检查的布尔值，null 返回 {@code true}
   * @return
   *     如果输入为null或false，返回 {@code true}
   */
  public static boolean isNotTrue(final Boolean value) {
    return ((value == null) || (!value));
  }

  /**
   * 检查 {@code Boolean} 值是否为 {@code false}，通过返回 {@code false} 来处理 {@code null}。
   *
   * <pre>
   *    isFalse(Boolean.TRUE)  = false
   *    isFalse(Boolean.FALSE) = true
   *    isFalse(null)          = false
   * </pre>
   *
   * @param value
   *     要检查的布尔值，null 返回 {@code false}
   * @return
   *     只有当输入非null且为false时才返回 {@code true}
   */
  public static boolean isFalse(final Boolean value) {
    if (value == null) {
      return false;
    } else {
      return (!value);
    }
  }

  /**
   * 检查 {@code Boolean} 值是否<i>不是</i> {@code false}，通过返回 {@code true} 来处理 {@code null}。
   *
   * <pre>
   *    isNotFalse(Boolean.TRUE)  = true
   *    isNotFalse(Boolean.FALSE) = false
   *    isNotFalse(null)          = true
   * </pre>
   *
   * @param value
   *     要检查的布尔值，null 返回 {@code true}
   * @return
   *     如果输入为null或true，返回 {@code true}
   */
  public static boolean isNotFalse(final Boolean value) {
    return ((value == null) || value);
  }

  /**
   * 对指定的布尔值进行取反操作。
   *
   * <p>如果传入 {@code null}，将返回 {@code null}。
   *
   * <pre>
   *     negate(Boolean.TRUE) = Boolean.FALSE;
   *     negate(Boolean.FALSE) = Boolean.TRUE;
   *     negate(null) = null;
   * </pre>
   *
   * @param value
   *     要取反的 Boolean 值，可能为 {@code null}
   * @return
   *     取反后的 Boolean 值，如果输入为 {@code null} 则返回 {@code null}
   */
  public static Boolean negate(@Nullable final Boolean value) {
    if (value == null) {
      return null;
    }
    return (value ? Boolean.FALSE : Boolean.TRUE);
  }

  /**
   * 对 {@code boolean} 值数组执行逻辑 {@code and} 操作。
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
   *     {@code Boolean} 值的数组
   * @return
   *     对 {@code Boolean} 值数组进行逻辑 {@code and} 操作的结果。
   *     注意，如果数组为空，将返回 true
   * @throws NullPointerException
   *     如果 {@code array} 为 {@code null}
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
   * 对 {@link BooleanCollection} 执行逻辑 {@code and} 操作。
   *
   * @param col
   *     {@link BooleanCollection} 对象
   * @return
   *     对 {@link BooleanCollection} 对象进行逻辑 {@code and} 操作的结果。
   *     注意，如果 {@code col} 为空，将返回 {@code true}
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
   * 对 {@code Boolean} 对象数组执行逻辑 {@code and} 操作。
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
   *     {@code Boolean} 对象的数组
   * @return
   *     对 {@code Boolean} 对象数组进行逻辑 {@code and} 操作的结果；
   *     如果数组中有任何元素为 {@code null}，则返回 {@code null}。
   *     注意，如果数组为空，将返回 {@code Boolean.TRUE}
   * @throws NullPointerException
   *     如果 {@code array} 为 {@code null}
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
   * 对 {@code Boolean} 对象的 {@code Iterable} 执行逻辑 {@code and} 操作。
   *
   * @param iterable
   *     {@code Boolean} 对象的 {@code Iterable}
   * @return
   *     对 {@code Boolean} 对象的可迭代对象进行逻辑 {@code and} 操作的结果；
   *     如果 {@code iterable} 中有任何元素为 {@code null}，则返回 {@code null}。
   *     注意，如果 {@code iterable} 为空，将返回 {@code Boolean.TRUE}
   * @throws NullPointerException
   *     如果 {@code iterable} 为 {@code null}
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
   * 对 {@code boolean} 值数组执行逻辑 {@code or} 操作。
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
   *     {@code Boolean} 值的数组
   * @return
   *     对 {@code Boolean} 值数组进行逻辑 {@code or} 操作的结果。
   *     注意，如果数组为空，将返回 false
   * @throws NullPointerException
   *     如果 {@code array} 为 {@code null}
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
   * 对 {@link BooleanCollection} 执行逻辑 {@code or} 操作。
   *
   * @param col
   *     {@link BooleanCollection} 对象
   * @return
   *     对 {@link BooleanCollection} 对象进行逻辑 {@code or} 操作的结果。
   *     注意，如果 {@code col} 为空，将返回 {@code false}
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
   * 对 {@code Boolean} 对象数组执行逻辑 {@code or} 操作。
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
   *     {@code Boolean} 对象的数组
   * @return
   *     对 {@code Boolean} 对象数组进行逻辑 {@code or} 操作的结果；
   *     如果数组中有任何元素为 {@code null}，则返回 {@code null}。
   *     注意，如果数组为空，将返回 {@code Boolean.FALSE}
   * @throws NullPointerException
   *     如果 {@code array} 为 {@code null}
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
   * 对 {@code Boolean} 对象的 {@code Iterable} 执行逻辑 {@code or} 操作。
   *
   * @param iterable
   *     {@code Boolean} 对象的 {@code Iterable}
   * @return
   *     对 {@code Boolean} 对象的可迭代对象进行逻辑 {@code or} 操作的结果；
   *     如果 {@code iterable} 中有任何元素为 {@code null}，则返回 {@code null}。
   *     注意，如果 {@code iterable} 为空，将返回 {@code Boolean.FALSE}
   * @throws NullPointerException
   *     如果 {@code iterable} 为 {@code null}
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
   * 对 {@code boolean} 值数组执行逻辑 {@code xor} 操作。
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
   *     {@code Boolean} 值的数组
   * @return
   *     对 {@code Boolean} 值数组进行逻辑 {@code xor} 操作的结果。
   *     注意，如果数组为空，将返回 false
   * @throws NullPointerException
   *     如果 {@code array} 为 {@code null}
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
   * 对 {@link BooleanCollection} 执行逻辑 {@code xor} 操作。
   *
   * @param col
   *     {@link BooleanCollection} 对象
   * @return
   *     对 {@link BooleanCollection} 对象进行逻辑 {@code xor} 操作的结果。
   *     注意，如果 {@code col} 为空，将返回 {@code false}
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
   * 对 {@code Boolean} 对象数组执行逻辑 {@code xor} 操作。
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
   *     {@code Boolean} 对象的数组
   * @return
   *     对 {@code Boolean} 对象数组进行逻辑 {@code xor} 操作的结果；
   *     如果数组中有任何元素为 {@code null}，则返回 {@code null}。
   *     注意，如果数组为空，将返回 {@code Boolean.FALSE}
   * @throws NullPointerException
   *     如果 {@code array} 为 {@code null}
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
   * 对 {@code Boolean} 对象的 {@code Iterable} 执行逻辑 {@code xor} 操作。
   *
   * @param iterable
   *     {@code Boolean} 对象的 {@code Iterable}
   * @return
   *     对 {@code Boolean} 对象的可迭代对象进行逻辑 {@code xor} 操作的结果；
   *     如果 {@code iterable} 中有任何元素为 {@code null}，则返回 {@code null}。
   *     注意，如果 {@code iterable} 为空，将返回 {@code Boolean.FALSE}
   * @throws NullPointerException
   *     如果 {@code iterable} 为 {@code null}
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
   * 将 {@code Boolean} 对象转换为 {@code boolean} 值，通过返回 {@code false} 来处理 {@code null}。
   *
   * <pre>
   *    toPrimitive(Boolean.TRUE)  = true
   *    toPrimitive(Boolean.FALSE) = false
   *    toPrimitive(null)          = BooleanUtils.DEFAULT
   * </pre>
   *
   * @param value
   *     要转换的 {@code Boolean} 对象，可能为 null
   * @return
   *     {@code Boolean} 对象的 {@code boolean} 值；如果 {@code Boolean} 对象为 null，
   *     返回 {@link #DEFAULT}
   */
  public static boolean toPrimitive(@Nullable final Boolean value) {
    return (value == null ? DEFAULT : value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code boolean} 值，通过返回 {@code defaultValue} 来处理 {@code null}。
   *
   * <pre>
   *    toPrimitive(Boolean.TRUE, false) = true
   *    toPrimitive(Boolean.FALSE, true) = false
   *    toPrimitive(null, true)          = true
   * </pre>
   *
   * @param value
   *     要转换的 {@code Boolean} 对象，可能为 null
   * @param defaultValue
   *     默认值
   * @return
   *     {@code Boolean} 对象的 {@code boolean} 值；如果 {@code Boolean} 对象为 null，
   *     返回默认值
   */
  public static boolean toPrimitive(@Nullable final Boolean value,
      final boolean defaultValue) {
    return (value == null ? defaultValue : value);
  }

  /**
   * 将 {@code boolean} 值转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 (char) 1；否则返回 (char) 0
   */
  public static char toChar(final boolean value) {
    return (value ? (char) 1 : (char) 0);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link CharUtils#DEFAULT}
   */
  public static char toChar(@Nullable final Boolean value) {
    return (value == null ? CharUtils.DEFAULT : toChar(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code char} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code char} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static char toChar(@Nullable final Boolean value,
      final char defaultValue) {
    return (value == null ? defaultValue : toChar(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Character} 对象
   */
  public static Character toCharObject(final boolean value) {
    return toChar(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Character toCharObject(@Nullable final Boolean value) {
    return (value == null ? null : toCharObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Character} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Character} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Character toCharObject(@Nullable final Boolean value,
      @Nullable final Character defaultValue) {
    return (value == null ? defaultValue : toCharObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 (byte) 1；否则返回 (byte) 0
   */
  public static byte toByte(final boolean value) {
    return (value ? (byte) 1 : (byte) 0);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link ByteUtils#DEFAULT}
   */
  public static byte toByte(@Nullable final Boolean value) {
    return (value == null ? ByteUtils.DEFAULT : toByte(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code byte} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code byte} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static byte toByte(@Nullable final Boolean value,
      final byte defaultValue) {
    return (value == null ? defaultValue : toByte(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Byte} 对象
   */
  public static Byte toByteObject(final boolean value) {
    return toByte(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Byte} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Byte toByteObject(@Nullable final Boolean value) {
    return (value == null ? null : toByteObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Byte} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Byte} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Byte toByteObject(@Nullable final Boolean value,
      @Nullable final Byte defaultValue) {
    return (value == null ? defaultValue : toByteObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 (short) 1；否则返回 (short) 0
   */
  public static short toShort(final boolean value) {
    return (value ? (short) 1 : (short) 0);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link ShortUtils#DEFAULT}
   */
  public static short toShort(@Nullable final Boolean value) {
    return (value == null ? ShortUtils.DEFAULT : toShort(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code short} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code short} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static short toShort(@Nullable final Boolean value,
      final short defaultValue) {
    return (value == null ? defaultValue : toShort(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Short} 对象
   */
  public static Short toShortObject(final boolean value) {
    return toShort(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Short toShortObject(@Nullable final Boolean value) {
    return (value == null ? null : toShortObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Short} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Short} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Short toShortObject(@Nullable final Boolean value,
      @Nullable final Short defaultValue) {
    return (value == null ? defaultValue : toShortObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 1；否则返回 0
   */
  public static int toInt(final boolean value) {
    return (value ? 1 : 0);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link IntUtils#DEFAULT}
   */
  public static int toInt(@Nullable final Boolean value) {
    return (value == null ? IntUtils.DEFAULT : toInt(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code int} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code int} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static int toInt(@Nullable final Boolean value,
      final int defaultValue) {
    return (value == null ? defaultValue : toInt(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Integer} 对象
   */
  public static Integer toIntObject(final boolean value) {
    return toInt(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Integer toIntObject(@Nullable final Boolean value) {
    return (value == null ? null : toIntObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Integer} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Integer} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Integer toIntObject(@Nullable final Boolean value,
      @Nullable final Integer defaultValue) {
    return (value == null ? defaultValue : toIntObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 1L；否则返回 0L
   */
  public static long toLong(final boolean value) {
    return (value ? 1L : 0L);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link LongUtils#DEFAULT}
   */
  public static long toLong(@Nullable final Boolean value) {
    return (value == null ? LongUtils.DEFAULT : toLong(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code long} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code long} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static long toLong(@Nullable final Boolean value,
      final long defaultValue) {
    return (value == null ? defaultValue : toLong(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Long} 对象
   */
  public static Long toLongObject(final boolean value) {
    return toLong(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Long toLongObject(@Nullable final Boolean value) {
    return (value == null ? null : toLongObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Long} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Long} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Long toLongObject(@Nullable final Boolean value,
      @Nullable final Long defaultValue) {
    return (value == null ? defaultValue : toLongObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 1.0f；否则返回 0.0f
   */
  public static float toFloat(final boolean value) {
    return (value ? 1.0f : 0.0f);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link FloatUtils#DEFAULT}
   */
  public static float toFloat(@Nullable final Boolean value) {
    return (value == null ? FloatUtils.DEFAULT : toFloat(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code float} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code float} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static float toFloat(@Nullable final Boolean value,
      final float defaultValue) {
    return (value == null ? defaultValue : toFloat(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Float} 对象
   */
  public static Float toFloatObject(final boolean value) {
    return toFloat(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Float toFloatObject(@Nullable final Boolean value) {
    return (value == null ? null : toFloatObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Float} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Float} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Float toFloatObject(@Nullable final Boolean value,
      @Nullable final Float defaultValue) {
    return (value == null ? defaultValue : toFloatObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 1.0；否则返回 0.0
   */
  public static double toDouble(final boolean value) {
    return (value ? 1.0 : 0.0);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@link DoubleUtils#DEFAULT}
   */
  public static double toDouble(@Nullable final Boolean value) {
    return (value == null ? DoubleUtils.DEFAULT
                          : toDouble(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code double} 值。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code double} 值。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static double toDouble(@Nullable final Boolean value,
      final double defaultValue) {
    return (value == null ? defaultValue : toDouble(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Double} 对象
   */
  public static Double toDoubleObject(final boolean value) {
    return toDouble(value);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Double toDoubleObject(@Nullable final Boolean value) {
    return (value == null ? null : toDoubleObject(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Double} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Double} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Double toDoubleObject(@Nullable final Boolean value,
      @Nullable final Double defaultValue) {
    return (value == null ? defaultValue
                          : toDoubleObject(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code true}，
   *     返回时间戳为 1L 的日期；否则返回时间戳为 0L 的日期
   */
  public static Date toDate(final boolean value) {
    return new Date((value) ? 1L : 0L);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static Date toDate(@Nullable final Boolean value) {
    return (value == null ? null : toDate(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Date} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link Date} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static Date toDate(@Nullable final Boolean value,
      @Nullable final Date defaultValue) {
    return (value == null ? defaultValue : toDate(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@code byte} 数组。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     转换后的 {@code byte} 数组。如果 {@code value} 为 {@code true}，
   *     返回包含一个元素 (byte) 1 的数组；否则返回包含一个元素 (byte) 0 的数组
   */
  public static byte[] toByteArray(final boolean value) {
    return new byte[]{(value ? (byte) 1 : (byte) 0)};
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code byte} 数组。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@code byte} 数组。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static byte[] toByteArray(@Nullable final Boolean value) {
    return (value == null ? null : toByteArray(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@code byte} 数组。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@code byte} 数组。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static byte[] toByteArray(@Nullable final Boolean value,
      @Nullable final byte[] defaultValue) {
    return (value == null ? defaultValue : toByteArray(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     总是返回 {@code Boolean.TYPE}
   */
  public static Class<?> toClass(final boolean value) {
    return Boolean.TYPE;
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code null}；
   *     否则返回 {@code Boolean.class}
   */
  public static Class<?> toClass(@Nullable final Boolean value) {
    return (value == null ? null : Boolean.class);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link Class} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     如果 {@code value} 为 {@code null}，返回 {@code defaultValue}；
   *     否则返回 {@code Boolean.class}
   */
  public static Class<?> toClass(@Nullable final Boolean value,
      @Nullable final Class<?> defaultValue) {
    return (value == null ? defaultValue : Boolean.class);
  }

  /**
   * 将 {@code boolean} 值转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 {@code BigInteger.ONE}；
   *     否则返回 {@code BigInteger.ZERO}
   */
  public static BigInteger toBigInteger(final boolean value) {
    return (value ? BigInteger.ONE : BigInteger.ZERO);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link BigInteger} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static BigInteger toBigInteger(@Nullable final Boolean value) {
    return (value == null ? null : toBigInteger(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link BigInteger} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link BigInteger} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static BigInteger toBigInteger(@Nullable final Boolean value,
      @Nullable final BigInteger defaultValue) {
    return (value == null ? defaultValue : toBigInteger(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 {@code BigDecimal.ONE}；
   *     否则返回 {@code BigDecimal.ZERO}
   */
  public static BigDecimal toBigDecimal(final boolean value) {
    return (value ? BigDecimal.ONE : BigDecimal.ZERO);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static BigDecimal toBigDecimal(@Nullable final Boolean value) {
    return (value == null ? null : toBigDecimal(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link BigDecimal} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link BigDecimal} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static BigDecimal toBigDecimal(@Nullable final Boolean value,
      @Nullable final BigDecimal defaultValue) {
    return (value == null ? defaultValue : toBigDecimal(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为 {@link String} 对象。
   *
   * @param value
   *     要转换的 {@code boolean} 值
   * @return
   *     如果 {@code value} 为 {@code true}，返回 {@code "true"}；
   *     否则返回 {@code "false"}
   */
  public static String toString(final boolean value) {
    return (value ? StringUtils.TRUE : StringUtils.FALSE);
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link String} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @return
   *     转换后的 {@link String} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code null}
   */
  public static String toString(@Nullable final Boolean value) {
    return (value == null ? null : toString(value.booleanValue()));
  }

  /**
   * 将 {@code Boolean} 对象转换为 {@link String} 对象。
   *
   * @param value
   *     要转换的 {@code Boolean} 对象
   * @param defaultValue
   *     默认值
   * @return
   *     转换后的 {@link String} 对象。如果 {@code value} 为 {@code null}，
   *     返回 {@code defaultValue}
   */
  public static String toString(@Nullable final Boolean value,
      @Nullable final String defaultValue) {
    return (value == null ? defaultValue : toString(value.booleanValue()));
  }

  /**
   * 将 {@code boolean} 值转换为字符串，返回输入字符串中的一个。
   *
   * <pre>
   *    toString(true, "true", "false")   = "true"
   *    toString(false, "true", "false")  = "false"
   * </pre>
   *
   * @param value
   *     要检查的 {@code boolean} 值
   * @param trueString
   *     当为 {@code true} 时返回的字符串，可能为 {@code null}
   * @param falseString
   *     当为 {@code false} 时返回的字符串，可能为 {@code null}
   * @return
   *     两个输入字符串中的一个
   */
  public static String toString(final boolean value, final String trueString,
      final String falseString) {
    return (value ? trueString : falseString);
  }

  /**
   * 将 {@code Boolean} 对象转换为字符串，返回输入字符串中的一个。
   *
   * <pre>
   *    toString(Boolean.TRUE, "true", "false", null)   = "true"
   *    toString(Boolean.FALSE, "true", "false", null)  = "false"
   *    toString(null, "true", "false", null)           = null;
   * </pre>
   *
   * @param value
   *     要检查的 {@code Boolean} 对象
   * @param trueString
   *     当为 {@code true} 时返回的字符串，可能为 {@code null}
   * @param falseString
   *     当为 {@code false} 时返回的字符串，可能为 {@code null}
   * @param nullString
   *     当为 {@code null} 时返回的字符串，可能为 {@code null}
   * @return
   *     三个输入字符串中的一个
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
   * 将 {@code boolean} 值转换为字符串，返回 {@code 'on'} 或 {@code 'off'}。
   *
   * <pre>
   *    toStringOnOff(true)   = "on"
   *    toStringOnOff(false)  = "off"
   * </pre>
   *
   * @param value
   *     要检查的 {@code boolean} 值
   * @return
   *     {@code 'on'} 或 {@code 'off'}
   */
  public static String toStringOnOff(final boolean value) {
    return (value ? StringUtils.ON : StringUtils.OFF);
  }

  /**
   * 将 {@code Boolean} 对象转换为字符串，返回 {@code 'on'}、{@code 'off'} 或 {@code null}。
   *
   * <pre>
   *    toStringOnOff(Boolean.TRUE)  = "on"
   *    toStringOnOff(Boolean.FALSE) = "off"
   *    toStringOnOff(null)          = null;
   * </pre>
   *
   * @param value
   *     要检查的 {@code Boolean} 对象
   * @return
   *     {@code 'on'}、{@code 'off'} 或 {@code null}
   */
  public static String toStringOnOff(final Boolean value) {
    if (value == null) {
      return null;
    } else {
      return (value ? StringUtils.ON : StringUtils.OFF);
    }
  }

  /**
   * 将 {@code boolean} 值转换为字符串，返回 {@code 'yes'} 或 {@code 'no'}。
   *
   * <pre>
   *    toStringYesNo(true)   = "yes"
   *    toStringYesNo(false)  = "no"
   * </pre>
   *
   * @param value
   *     要检查的 {@code boolean} 值
   * @return
   *     {@code 'yes'} 或 {@code 'no'}
   */
  public static String toStringYesNo(final boolean value) {
    return (value ? StringUtils.YES : StringUtils.NO);
  }

  /**
   * 将 {@code Boolean} 对象转换为字符串，返回 {@code 'yes'}、{@code 'no'} 或 {@code null}。
   *
   * <pre>
   *    toStringYesNo(Boolean.TRUE)  = "yes"
   *    toStringYesNo(Boolean.FALSE) = "no"
   *    toStringYesNo(null)          = null;
   * </pre>
   *
   * @param value
   *     要检查的 {@code Boolean} 对象
   * @return
   *     {@code 'yes'}、{@code 'no'} 或 {@code null}
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