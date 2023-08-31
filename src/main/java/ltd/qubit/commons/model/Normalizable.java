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

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.reflect.BeanInfo;
import ltd.qubit.commons.reflect.Property;

/**
 * This interface indicates an entity classes can be normalized.
 *
 * @author Haixing Hu
 */
public interface Normalizable {

  /**
   * Normalizes this object.
   */
  default void normalize() {
    // 默认用反射机制正则化此对象的所有属性字段
    final BeanInfo info = BeanInfo.of(this.getClass());
    for (final Property prop : info.getProperties()) {
      if (prop.isComputed() || prop.isJdkBuiltIn() || prop.isReadonly()) {
        continue;
      }
      final Object value = prop.getValue(this);
      final Object normalizedValue = normalize(value);
      prop.setValue(this, normalizedValue);
    }
  }

  /**
   * Normalizes an object.
   *
   * @param <T>
   *     the type of the object.
   * @param obj
   *     The object to be normalized, which can be {@code null}.
   * @return
   *     The result of normalization, or {@code null} if the object is empty.
   */
  @SuppressWarnings("unchecked")
  static <T> T normalize(@Nullable final T obj) {
    if (obj == null) {
      return null;
    } else {
      if (obj instanceof Normalizable) {
        ((Normalizable) obj).normalize();
      }
      if (obj instanceof String) {
        // For string, remove its leading and trailing blanks
        final String str = StringUtils.strip((String) obj);
        return (str.isEmpty() ? null : (T) str);
      }
      if ((obj instanceof Collection) && ((Collection<?>) obj).isEmpty()) {
        return null;
      }
      if ((obj instanceof Map) && ((Map<?, ?>) obj).isEmpty()) {
        return null;
      }
      if (ArrayUtils.isArray(obj) && ArrayUtils.isEmpty(obj)) {
        return null;
      }
      if ((obj instanceof Emptyful) && ((Emptyful) obj).isEmpty()) {
        return null;
      }
      return obj;
    }
  }
}
