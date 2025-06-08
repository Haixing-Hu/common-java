////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.set;

import java.util.Set;
import java.util.function.Predicate;

/**
 * 提供关于集合的实用函数。
 *
 * @author 胡海星
 */
public class SetUtils {

  /**
   * 测试给定集合是否包含给定数组中的任何元素。
   *
   * @param <T>
   *     集合和数组中元素的类型。
   * @param set
   *     给定的集合，不能为 null。
   * @param values
   *     给定的值数组，不能为 null。
   * @return
   *     如果集合包含数组中的任何值，则返回 `true`；否则返回 `false`。
   */
  public static <T> boolean containsAny(final Set<T> set, final T[] values) {
    for (final T value : values) {
      if (set.contains(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 测试给定集合中是否有任何元素满足指定的谓词。
   *
   * @param <T>
   *     集合中元素的类型。
   * @param set
   *     给定的集合，不能为 null。
   * @param predicate
   *     指定的谓词，不能为 null。
   * @return
   *     如果集合中有任何元素满足谓词，则返回 `true`；否则返回 `false`。
   */
  public static <T> boolean containsAny(final Set<T> set, final Predicate<T> predicate) {
    for (final T value : set) {
      if (predicate.test(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 测试给定集合是否包含给定数组中的所有元素。
   *
   * @param <T>
   *     集合和数组中元素的类型。
   * @param set
   *     给定的集合，不能为 null。
   * @param values
   *     给定的值数组，不能为 null。
   * @return
   *     如果集合包含数组中的所有值，则返回 `true`；否则返回 `false`。
   */
  public static <T> boolean containsAll(final Set<T> set, final T[] values) {
    for (final T value : values) {
      if (!set.contains(value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 测试给定集合中的所有元素是否都满足指定的谓词。
   *
   * @param <T>
   *     集合中元素的类型。
   * @param set
   *     给定的集合，不能为 null。
   * @param predicate
   *     指定的谓词，不能为 null。
   * @return
   *     如果集合中的所有元素都满足谓词，则返回 `true`；否则返回 `false`。
   */
  public static <T> boolean containsAll(final Set<T> set, final Predicate<T> predicate) {
    for (final T value : set) {
      if (!predicate.test(value)) {
        return false;
      }
    }
    return true;
  }
}