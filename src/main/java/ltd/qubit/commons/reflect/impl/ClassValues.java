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

public final class ClassValues {

  private ClassValues() {
  }

  public static <T> ClassValue<T> create(final Function<Class<?>, T> mapper) {
    return new ClassValue<>() {
      @Override
      protected T computeValue(final Class<?> type) {
        return mapper.apply(type);
      }
    };
  }
}