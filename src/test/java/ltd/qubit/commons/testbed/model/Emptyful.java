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

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.reflect.BeanInfo;
import ltd.qubit.commons.reflect.Property;

/**
 * 此接口表示对象具有{@code isEmpty()}方法。
 *
 * @author 胡海星
 */
public interface Emptyful {

  /**
   * 判定此对象是否为(业务逻辑意义上的)空对象。
   *
   * @return
   *     此对象是否为空对象。
   */
  @Computed
  default boolean isEmpty() {
    // 默认用反射机制检查每个字段是否为空，若所有字段都为空则整个对象为空
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
   * 判定指定的对象是否为(业务逻辑意义上的)空对象。
   *
   * @param <T>
   *     指定的对象的类型。
   * @param obj
   *     指定的对象，可以为{@code null}。
   * @return
   *     若指定的对象为{@code null}，或在业务逻辑意义上为空对象，则返回{@code true}；
   *     否则返回{@code false}。
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