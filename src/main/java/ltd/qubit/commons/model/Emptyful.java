////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.reflect.BeanInfo;
import ltd.qubit.commons.reflect.Property;

/**
 * This interface indicates that an object has a {@code isEmpty()} method to
 * test whether it is an empty object (in the sense of business logic).
 *
 * @author Haixing Hu
 */
public interface Emptyful {

  /**
   * Determine whether this object is an empty object (in the sense of business
   * logic).
   *
   * @return
   *     Whether this object is an empty object.
   */
  @Computed
  default boolean isEmpty() {
    // By default, the reflection mechanism is used to check whether each field
    // is empty. If all fields are empty, the entire object is empty.
    final BeanInfo info = BeanInfo.of(this.getClass());
    for (final Property prop : info.getProperties()) {
      if (prop.isComputed() || prop.isJdkBuiltIn()) {
        continue;
      }
      final Object value = prop.getValue(this);
      if (! isEmpty(value)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines whether the specified object is an empty object (in the sense of
   * business logic).
   *
   * @param <T>
   *     The type of the specified object.
   * @param obj
   *     The specified object, which can be {@code null}.
   * @return
   *     If the specified object is {@code null}, or an empty object in the
   *     sense of business logic, return {@code true}; otherwise return
   *     {@code false}.
   */
  static <T> boolean isEmpty(@Nullable final T obj) {
    if (obj == null) {
      return true;
    } else {
      if (obj instanceof String) {
        return ((String) obj).isEmpty();
      }
      if (obj instanceof Collection) {
        return ((Collection<?>) obj).isEmpty();
      }
      if (obj instanceof Map) {
        return ((Map<?, ?>) obj).isEmpty();
      }
      if (ArrayUtils.isArray(obj)) {
        return ArrayUtils.isEmpty(obj);
      }
      if (obj instanceof Emptyful) {
        return ((Emptyful) obj).isEmpty();
      }
      return false;
    }
  }
}
