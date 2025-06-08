////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.Equality;

/**
 * 为转换器提供实用函数。
 *
 * @author 胡海星
 */
public final class TransformUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransformUtils.class);

  private static <T> void logTransform(final T oldValue, final T newValue) {
    if (LOGGER.isDebugEnabled()) {
      if (! Equality.equals(oldValue, newValue)) {
        LOGGER.debug("Transform: {} --> {}", oldValue, newValue);
      }
    }
  }

  /**
   * 使用指定的转换器转换指定的值。
   *
   * @param <T>
   *     被转换的值的类型。
   * @param tr
   *     指定的转换器，可以为{@code null}。
   * @param value
   *     指定的值，可以为{@code null}。
   * @return
   *     转换后的值。如果指定的值为{@code null}，或者指定的转换器为{@code null}，则返回其本身。
   */
  public static <T> T transform(@Nullable final Transformer<T> tr,
      @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (tr == null) {
      return value;
    }
    final T newValue;
    synchronized (tr) {
      newValue = tr.transform(value);
    }
    logTransform(value, newValue);
    return newValue;
  }

  /**
   * 使用指定的转换器链转换指定的值。
   *
   * @param <T>
   *     被转换的值的类型。
   * @param tr1
   *     第一个转换器，可以为{@code null}。
   * @param tr2
   *     第二个转换器，可以为{@code null}。
   * @param value
   *     指定的值，可以为{@code null}。
   * @return
   *     转换后的值。如果指定的值为{@code null}，则返回其本身。
   */
  public static <T> T transform(@Nullable final Transformer<T> tr1,
        @Nullable final Transformer<T> tr2, @Nullable final T value) {
    if (value == null) {
      return null;
    }
    T result = value;
    if (tr1 != null) {
      final T newValue;
      synchronized (tr1) {
        newValue = tr1.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    if (tr2 != null) {
      final T newValue;
      synchronized (tr2) {
        newValue = tr2.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    return result;
  }

  /**
   * 使用指定的转换器链转换指定的值。
   *
   * @param <T>
   *     被转换的值的类型。
   * @param tr1
   *     第一个转换器，可以为{@code null}。
   * @param tr2
   *     第二个转换器，可以为{@code null}。
   * @param tr3
   *     第三个转换器，可以为{@code null}。
   * @param value
   *     指定的值，可以为{@code null}。
   * @return
   *     转换后的值。如果指定的值为{@code null}，则返回其本身。
   */
  public static <T> T transform(@Nullable final Transformer<T> tr1,
        @Nullable final Transformer<T> tr2, @Nullable final Transformer<T> tr3,
        @Nullable final T value) {
    if (value == null) {
      return null;
    }
    T result = value;
    if (tr1 != null) {
      final T newValue;
      synchronized (tr1) {
        newValue = tr1.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    if (tr2 != null) {
      final T newValue;
      synchronized (tr2) {
        newValue = tr2.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    if (tr3 != null) {
      final T newValue;
      synchronized (tr3) {
        newValue = tr3.transform(result);
      }
      logTransform(result, newValue);
      result = newValue;
    }
    return result;
  }

  /**
   * 使用指定的转换器列表转换指定的值。
   *
   * @param <T>
   *     被转换的值的类型。
   * @param trs
   *     指定的转换器列表，可以为{@code null}。
   * @param value
   *     指定的值，可以为{@code null}。
   * @return
   *     转换后的值。如果指定的值为{@code null}，或者指定的转换器列表为{@code null}或空，则返回其本身。
   */
  public static <T> T transform(@Nullable final List<Transformer<T>> trs,
          @Nullable final T value) {
    if (value == null) {
      return null;
    }
    if (trs == null || trs.isEmpty()) {
      return value;
    }
    T result = value;
    for (final Transformer<T> tr: trs) {
      result = transform(tr, result);
    }
    return result;
  }

  /**
   * 使用指定的转换器转换指定的列表中的所有元素。
   *
   * @param <T>
   *     列表元素的类型。
   * @param tr
   *     指定的转换器，可以为{@code null}。
   * @param list
   *     指定的列表，可以为{@code null}。
   * @param ignoreNull
   *     是否忽略转换后为{@code null}的元素。
   * @return
   *     一个新的列表，包含对指定列表的每个元素进行转换后的结果。如果指定的列表为{@code null}，
   *     则返回{@code null}；如果指定的转换器为{@code null}，则返回原来列表的浅拷贝。
   */
  public static <T> List<T> transformList(@Nullable final Transformer<T> tr,
      @Nullable final List<T> list, final boolean ignoreNull) {
    if (list == null) {
      return null;
    }
    if (tr == null) {
      return list;
    }
    final List<T> result = new ArrayList<>();
    for (final T value : list) {
      if (value == null) {
        if (! ignoreNull) {
          result.add(null);
        }
      } else {
        final T newValue;
        synchronized (tr) {
          newValue = tr.transform(value);
        }
        logTransform(value, newValue);
        if (newValue == null && ignoreNull) {
          continue;
        }
        result.add(newValue);
      }
    }
    return result;
  }

  /**
   * 使用指定的转换器列表转换指定的列表中的所有元素。
   *
   * @param <T>
   *     列表元素的类型。
   * @param trs
   *     指定的转换器列表，可以为{@code null}。
   * @param list
   *     指定的列表，可以为{@code null}。
   * @param ignoreNull
   *     是否忽略转换后为{@code null}的元素。
   * @return
   *     一个新的列表，包含对指定列表的每个元素进行转换后的结果。如果指定的列表为{@code null}，
   *     则返回{@code null}；如果指定的转换器列表为{@code null}或空，则返回原来列表的浅拷贝。
   */
  public static <T> List<T> transformList(@Nullable final List<Transformer<T>> trs,
          @Nullable final List<T> list, final boolean ignoreNull) {
    if (list == null) {
      return null;
    }
    if (trs == null || trs.isEmpty()) {
      return list;
    }
    List<T> result = list;
    for (final Transformer<T> tr : trs) {
      result = transformList(tr, result, ignoreNull);
    }
    return result;
  }

}