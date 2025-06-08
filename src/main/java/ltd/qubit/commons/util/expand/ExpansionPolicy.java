////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.expand;

import java.lang.reflect.Array;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.CommonsConfig;
import ltd.qubit.commons.concurrent.Lazy;
import ltd.qubit.commons.config.Config;

/**
 * {@link ExpansionPolicy} 用于在扩展动态数组时计算容量。
 * <p>
 * 实现必须是线程安全的。
 * </p>
 *
 * @author 胡海星
 */
@ThreadSafe
public abstract class ExpansionPolicy {

  /**
   * The value of this property is the class name of the implementation of the
   * default {@link ExpansionPolicy}.
   *
   * <table style="border-collapse:collapse;">
   * <caption>The property of the class name of the default expansion policy</caption>
   * <tr>
   * <th style="border:1px solid;padding:0.5rem;">Type</th>
   * <th style="border:1px solid;padding:0.5rem;">Count</th>
   * <th style="border:1px solid;padding:0.5rem;">Value</th>
   * <th style="border:1px solid;padding:0.5rem;">Required</th>
   * <th style="border:1px solid;padding:0.5rem;">Default</th>
   * <th style="border:1px solid;padding:0.5rem;">Range</th>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">class</td>
   * <td style="border:1px solid;padding:0.5rem;">1</td>
   * <td style="border:1px solid;padding:0.5rem;">the class name of the
   * implementation of the default
   * {@link ExpansionPolicy}.</td>
   * <td style="border:1px solid;padding:0.5rem;">no</td>
   * <td style="border:1px solid;padding:0.5rem;">{@link MemorySavingExpansionPolicy#INSTANCE}</td>
   * <td style="border:1px solid;padding:0.5rem;"></td>
   * </tr>
   * </table>
   */
  public static final String PROPERTY_DEFAULT = "ExpansionPolicy.default";

  /**
   * The value of this property is the initial capacity used by the
   * {@link ExpansionPolicy}.
   * <table style="border-collapse:collapse;">
   * <caption>The property of the initial capacity of the expansion policy</caption>
   * <tr>
   * <th style="border:1px solid;padding:0.5rem;">Type</th>
   * <th style="border:1px solid;padding:0.5rem;">Count</th>
   * <th style="border:1px solid;padding:0.5rem;">Value</th>
   * <th style="border:1px solid;padding:0.5rem;">Required</th>
   * <th style="border:1px solid;padding:0.5rem;">Default</th>
   * <th style="border:1px solid;padding:0.5rem;">Range</th>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">int</td>
   * <td style="border:1px solid;padding:0.5rem;">1</td>
   * <td style="border:1px solid;padding:0.5rem;">the initial capacity used by
   * the {@link ExpansionPolicy}.</td>
   * <td style="border:1px solid;padding:0.5rem;">no</td>
   * <td style="border:1px solid;padding:0.5rem;">{@link #DEFAULT_INITIAL_CAPACITY}</td>
   * <td style="border:1px solid;padding:0.5rem;">[1, {@link Integer#MAX_VALUE}]</td>
   * </tr>
   * </table>
   *
   * @see #DEFAULT_INITIAL_CAPACITY
   */
  public static final String PROPERTY_INITIAL_CAPACITY = "ExpansionPolicy.initialCapacity";

  /**
   * The default value of the property {@link #PROPERTY_INITIAL_CAPACITY}.
   *
   * @see #PROPERTY_INITIAL_CAPACITY
   */
  public static final int DEFAULT_INITIAL_CAPACITY = 16;

  private static final String INVALID_IMPLEMENTATION =
          "Invalid implementation of the abstract method.";

  private static final Lazy<ExpansionPolicy> lazyDefaultPolicy = Lazy.of(() -> {
    final Config config = CommonsConfig.get();
    return config.getInstance(PROPERTY_DEFAULT, MemorySavingExpansionPolicy.INSTANCE);
  });

  /**
   * 获取默认的 {@link ExpansionPolicy}。
   *
   * @return 默认的 {@link ExpansionPolicy}。
   */
  public static ExpansionPolicy getDefault() {
    return lazyDefaultPolicy.get();
  }

  private static final Lazy<Integer> lazyInitialCapacity = Lazy.of(() -> {
    final Config config = CommonsConfig.get();
    return config.getInt(PROPERTY_INITIAL_CAPACITY, DEFAULT_INITIAL_CAPACITY);
  });

  /**
   * 获取建议的初始容量。
   *
   * @return 建议的初始容量。
   */
  public static int getInitialCapacity() {
    return lazyInitialCapacity.get();
  }

  /**
   * 获取动态数组扩展后的下一个容量。
   *
   * @param oldCapacity
   *     旧容量。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后数组的新容量。
   */
  public abstract int getNextCapacity(int oldCapacity, int newLength);

  /**
   * 判断是否需要收缩动态数组。
   *
   * @param length
   *     数组中元素的实际长度。
   * @param capacity
   *     数组的当前容量。
   * @return
   *     如果需要收缩，则返回 {@code true}；否则返回 {@code false}。
   */
  public abstract boolean needShrink(int length, int capacity);

  /**
   * 获取收缩后动态数组的容量。
   *
   * @param length
   *     数组中元素的实际长度。
   * @param capacity
   *     数组的当前容量。
   * @return
   *     收缩后数组的新容量。
   */
  public abstract int getShrinkCapacity(int length, int capacity);

  /**
   * 对 boolean 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final boolean[] expand(@Nullable final boolean[] oldBuffer,
          final int oldLength, final int newLength) {
    return (boolean[]) doExpand(oldBuffer, oldLength, newLength, Boolean.TYPE);
  }

  /**
   * 对 char 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final char[] expand(@Nullable final char[] oldBuffer,
      final int oldLength, final int newLength) {
    return (char[]) doExpand(oldBuffer, oldLength, newLength, Character.TYPE);
  }

  /**
   * 对 byte 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final byte[] expand(@Nullable final byte[] oldBuffer,
      final int oldLength, final int newLength) {
    return (byte[]) doExpand(oldBuffer, oldLength, newLength, Byte.TYPE);
  }

  /**
   * 对 short 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final short[] expand(@Nullable final short[] oldBuffer,
      final int oldLength, final int newLength) {
    return (short[]) doExpand(oldBuffer, oldLength, newLength, Short.TYPE);
  }

  /**
   * 对 int 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final int[] expand(@Nullable final int[] oldBuffer,
      final int oldLength, final int newLength) {
    return (int[]) doExpand(oldBuffer, oldLength, newLength, Integer.TYPE);
  }

  /**
   * 对 long 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final long[] expand(@Nullable final long[] oldBuffer,
      final int oldLength, final int newLength) {
    return (long[]) doExpand(oldBuffer, oldLength, newLength, Long.TYPE);
  }

  /**
   * 对 float 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final float[] expand(@Nullable final float[] oldBuffer,
      final int oldLength, final int newLength) {
    return (float[]) doExpand(oldBuffer, oldLength, newLength, Float.TYPE);
  }

  /**
   * 对 double 数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @return
   *     扩展后的新数组。
   */
  public final double[] expand(@Nullable final double[] oldBuffer,
      final int oldLength, final int newLength) {
    return (double[]) doExpand(oldBuffer, oldLength, newLength, Double.TYPE);
  }

  /**
   * 对泛型数组进行扩展。
   *
   * @param oldBuffer
   *     要扩展的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     扩展后数组的期望最小长度。
   * @param valueClass
   *     数组元素类型。
   * @param <T>
   *     数组元素的类型。
   * @return
   *     扩展后的新数组。
   */
  @SuppressWarnings("unchecked")
  public final <T> T[] expand(@Nullable final T[] oldBuffer,
      final int oldLength, final int newLength, final Class<T> valueClass) {
    return (T[]) doExpand(oldBuffer, oldLength, newLength, valueClass);
  }

  /**
   * 对 boolean 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final boolean[] shrink(@Nullable final boolean[] oldBuffer,
          final int oldLength) {
    return (boolean[]) doShrink(oldBuffer, oldLength, Boolean.TYPE);
  }

  /**
   * 对 char 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final char[] shrink(@Nullable final char[] oldBuffer,
      final int oldLength) {
    return (char[]) doShrink(oldBuffer, oldLength, Character.TYPE);
  }

  /**
   * 对 byte 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final byte[] shrink(@Nullable final byte[] oldBuffer,
      final int oldLength) {
    return (byte[]) doShrink(oldBuffer, oldLength, Byte.TYPE);
  }

  /**
   * 对 short 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final short[] shrink(@Nullable final short[] oldBuffer,
      final int oldLength) {
    return (short[]) doShrink(oldBuffer, oldLength, Short.TYPE);
  }

  /**
   * 对 int 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final int[] shrink(@Nullable final int[] oldBuffer, final int oldLength) {
    return (int[]) doShrink(oldBuffer, oldLength, Integer.TYPE);
  }

  /**
   * 对 long 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final long[] shrink(@Nullable final long[] oldBuffer,
      final int oldLength) {
    return (long[]) doShrink(oldBuffer, oldLength, Long.TYPE);
  }

  /**
   * 对 float 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final float[] shrink(@Nullable final float[] oldBuffer,
      final int oldLength) {
    return (float[]) doShrink(oldBuffer, oldLength, Float.TYPE);
  }

  /**
   * 对 double 数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @return
   *     收缩后的新数组。
   */
  public final double[] shrink(@Nullable final double[] oldBuffer,
      final int oldLength) {
    return (double[]) doShrink(oldBuffer, oldLength, Double.TYPE);
  }

  /**
   * 对泛型数组进行收缩。
   *
   * @param oldBuffer
   *     要收缩的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param valueClass
   *     数组元素类型。
   * @param <T>
   *     数组元素的类型。
   * @return
   *     收缩后的新数组。
   */
  @SuppressWarnings("unchecked")
  public final <T> T[] shrink(@Nullable final T[] oldBuffer,
      final int oldLength, final Class<T> valueClass) {
    return (T[]) doShrink(oldBuffer, oldLength, valueClass);
  }

  /**
   * 调整 boolean 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final boolean[] resize(@Nullable final boolean[] oldBuffer,
          final int oldLength, final int newLength) {
    return (boolean[]) doResize(oldBuffer, oldLength, newLength, Boolean.TYPE);
  }

  /**
   * 调整 char 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final char[] resize(@Nullable final char[] oldBuffer,
          final int oldLength, final int newLength) {
    return (char[]) doResize(oldBuffer, oldLength, newLength, Character.TYPE);
  }

  /**
   * 调整 byte 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final byte[] resize(@Nullable final byte[] oldBuffer,
          final int oldLength, final int newLength) {
    return (byte[]) doResize(oldBuffer, oldLength, newLength, Byte.TYPE);
  }

  /**
   * 调整 short 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final short[] resize(@Nullable final short[] oldBuffer,
          final int oldLength, final int newLength) {
    return (short[]) doResize(oldBuffer, oldLength, newLength, Short.TYPE);
  }

  /**
   * 调整 int 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final int[] resize(@Nullable final int[] oldBuffer,
          final int oldLength, final int newLength) {
    return (int[]) doResize(oldBuffer, oldLength, newLength, Integer.TYPE);
  }

  /**
   * 调整 long 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final long[] resize(@Nullable final long[] oldBuffer,
          final int oldLength, final int newLength) {
    return (long[]) doResize(oldBuffer, oldLength, newLength, Long.TYPE);
  }

  /**
   * 调整 float 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final float[] resize(@Nullable final float[] oldBuffer,
          final int oldLength, final int newLength) {
    return (float[]) doResize(oldBuffer, oldLength, newLength, Float.TYPE);
  }

  /**
   * 调整 double 数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @return
   *     调整大小后的新数组。
   */
  public final double[] resize(@Nullable final double[] oldBuffer,
          final int oldLength, final int newLength) {
    return (double[]) doResize(oldBuffer, oldLength, newLength, Double.TYPE);
  }

  /**
   * 调整泛型数组的大小。
   *
   * @param oldBuffer
   *     要调整大小的旧数组。
   * @param oldLength
   *     旧数组中已使用的长度。
   * @param newLength
   *     调整大小后数组的期望长度。
   * @param valueClass
   *     数组元素类型。
   * @param <T>
   *     数组元素的类型。
   * @return
   *     调整大小后的新数组。
   */
  @SuppressWarnings("unchecked")
  public final <T> T[] resize(@Nullable final T[] oldBuffer,
          final int oldLength, final int newLength, final Class<T> valueClass) {
    return (T[]) doResize(oldBuffer, oldLength, newLength, valueClass);
  }

  private Object doExpand(@Nullable final Object oldBuffer,
          final int oldLength, final int newLength, final Class<?> valueClass) {
    if ((oldLength < 0) || (newLength < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if (oldBuffer == null) {
      return Array.newInstance(valueClass, newLength);
    }
    final int oldCapacity = Array.getLength(oldBuffer);
    if (newLength <= oldCapacity) {
      return oldBuffer; // don't need to expand
    }
    final int newCapacity = getNextCapacity(oldCapacity, newLength);
    if (newCapacity < newLength) {
      throw new RuntimeException(INVALID_IMPLEMENTATION);
    }
    final Object newBuffer = Array.newInstance(valueClass, newCapacity);
    if (oldLength > 0) {
      System.arraycopy(oldBuffer, 0, newBuffer, 0, oldLength);
    }
    return newBuffer;
  }

  private Object doShrink(@Nullable final Object oldBuffer,
          final int oldLength, final Class<?> valueClass) {
    if (oldLength < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (oldBuffer == null) {
      return null;
    }
    final int oldCapacity = Array.getLength(oldBuffer);
    final int newCapacity = getShrinkCapacity(oldLength, oldCapacity);
    if (newCapacity == oldCapacity) {
      return oldBuffer;
    }
    if ((newCapacity < oldLength) || (newCapacity > oldCapacity)) {
      throw new RuntimeException(INVALID_IMPLEMENTATION);
    }
    final Object newBuffer = Array.newInstance(valueClass, newCapacity);
    if (oldLength > 0) {
      System.arraycopy(oldBuffer, 0, newBuffer, 0, oldLength);
    }
    return newBuffer;
  }

  private Object doResize(@Nullable final Object oldBuffer,
          final int oldLength, final int newLength, final Class<?> valueClass) {
    if ((oldLength < 0) || (newLength < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if (oldBuffer == null) {
      return Array.newInstance(valueClass, newLength);
    }
    final int oldCapacity = Array.getLength(oldBuffer);
    if (newLength == oldCapacity) {
      return oldBuffer;
    } else {
      final Object newBuffer = Array.newInstance(valueClass, newLength);
      final int n = Math.min(oldLength, newLength);
      if (n > 0) {
        System.arraycopy(oldBuffer, 0, newBuffer, 0, n);
      }
      return newBuffer;
    }
  }
}