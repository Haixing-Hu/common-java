////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.AccessibleObject;

public class AccessibleUtils {

  private AccessibleUtils() {
  }

  public static <T extends AccessibleObject> void withAccessibleObject(
      final T obj, final AccessibleObjectConsumer<T> consumer)
      throws ReflectionException {
    withAccessibleObject(obj, o -> {
      consumer.access(o);
      return null;
    }, true);
  }

  @SuppressWarnings("deprecation")
  public static <T extends AccessibleObject, R> R withAccessibleObject(
      final T obj, final AccessibleObjectFunction<T, R> func,
      final boolean force) throws ReflectionException {
    final boolean accessible = obj.isAccessible();
    try {
      if (force && !accessible) {
        obj.setAccessible(true);
      }
      return func.access(obj);
    } catch (final IllegalArgumentException | IllegalAccessException e) {
      throw new ReflectionException(e);
    } finally {
      if (force && !accessible) {
        obj.setAccessible(false);
      }
    }
  }

}
