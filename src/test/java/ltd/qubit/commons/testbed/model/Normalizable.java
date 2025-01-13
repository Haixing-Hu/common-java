////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.reflect.BeanInfo;
import ltd.qubit.commons.reflect.Property;

/**
 * 此接口表示实体类可被正则化。
 *
 * @author 胡海星
 */
public interface Normalizable {

  /**
   * 正则化此实体。
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
   * 正则化一个对象。
   *
   * @param <T>
   *     对象的类型。
   * @param obj
   *     待正则化的对象，可以为{@code null}。
   * @return
   *     正则化的结果，若对象为空则返回{@code null}。
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
        // 对于string，去除其头尾空白
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
