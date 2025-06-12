////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.util.function.Function;

/**
 * 提供 {@link ClassValue} 的实用工具。
 *
 * @author 胡海星
 */
public final class ClassValues {

  private ClassValues() {
  }

  /**
   * 创建一个 {@link ClassValue} 实例。
   *
   * @param <T>
   *     值的类型。
   * @param mapper
   *     用于计算值的映射函数。
   * @return 新创建的 {@link ClassValue} 实例。
   */
  public static <T> ClassValue<T> create(final Function<Class<?>, T> mapper) {
    return new ClassValue<>() {
      @Override
      protected T computeValue(final Class<?> type) {
        return mapper.apply(type);
      }
    };
  }
}