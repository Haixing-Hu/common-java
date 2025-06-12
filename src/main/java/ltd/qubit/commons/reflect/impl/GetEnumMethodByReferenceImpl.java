////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.objenesis.ObjenesisHelper;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.ClassKey;
import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.reflect.ReflectionException;

/**
 * 通过方法引用获取枚举方法的实现类。
 *
 * @author 胡海星
 */
public class GetEnumMethodByReferenceImpl {

  private static final ClassValue<Class<?>> DUMMY_SUBCLASSES =
      ClassValues.create(GetEnumMethodByReferenceImpl::createDummyProxyClass);

  private static Class<?> createDummyProxyClass(final Class<?> type) {
    try (final DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
        .subclass(type)
        .make()) {
      return unloadedType
          .load(GetEnumMethodByReferenceImpl.class.getClassLoader())
          .getLoaded();
    }
  }

  /**
   * 查找枚举方法。
   *
   * @param <T>
   *     枚举类型
   * @param <R>
   *     返回值类型
   * @param clazz
   *     枚举类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws IllegalArgumentException
   *     如果指定的类不是枚举类，或无法找到对应的方法
   * @throws ReflectionException
   *     如果反射操作失败
   */
  public static <T, R> Method findEnumMethod(final Class<T> clazz,
      final NonVoidMethod0<T, R> ref) {
    if (!clazz.isEnum()) {
      throw new IllegalArgumentException(clazz + " is not a enum.");
    }
    final Object[] uniqueValues = buildUniqueValues(clazz);
    try {
      final Constructor<T> ctor = getEnumConstructor(clazz);
      final T record = ConstructorUtils.newInstance(ctor, uniqueValues);
      final Object value = ref.invoke(record);
      if (needsFallbackToComponentSearch(uniqueValues, value)) {
        return exhaustiveComponentSearch(value, clazz, ref, uniqueValues, ctor);
      }
      final int componentIndex = ArrayUtils.indexOf(uniqueValues, value);
      if (componentIndex < 0) {
        throw new IllegalArgumentException("Failed to find a component in "
            + clazz.getName()
            + " for the given component accessor.");
      }
      return getRecordComponentAccessor(clazz, componentIndex);
    } catch (final ReflectiveOperationException e) {
      throw new ReflectionException(e);
    }
  }

  /**
   * 获取枚举类的构造函数。
   *
   * @param <T>
   *     枚举类型
   * @param clazz
   *     枚举类
   * @return
   *     枚举类的构造函数
   * @throws NoSuchMethodException
   *     如果枚举类没有构造函数
   */
  static <T> Constructor<T> getEnumConstructor(final Class<T> clazz)
      throws NoSuchMethodException {
    final Class<?>[] ctorTypes = getEnumComponents(clazz)
        .map(RecordComponent::getType)
        .toArray(Class[]::new);
    return clazz.getDeclaredConstructor(ctorTypes);
  }

  /**
   * 获取枚举类的组件。
   *
   * @param clazz
   *     枚举类
   * @return
   *     枚举类的组件
   */
  static Stream<RecordComponent> getEnumComponents(final Class<?> clazz) {
    if (!clazz.isRecord()) {
      throw new IllegalArgumentException(clazz + " is not a record");
    }
    return Arrays.stream(clazz.getRecordComponents());
  }

  private static Object[] buildUniqueValues(final Class<?> recordClass) {
    return getEnumComponents(recordClass)
        .map(RecordComponent::getType)
        .map(uniqueValueBuilder())
        .toArray(Object[]::new);
  }

  private static Function<Class<?>, Object> uniqueValueBuilder() {
    // 使用 ClassKey 作为 Map 的键，而不是直接使用 Class 对象。
    // 这样可以避免在 Web 容器热部署环境中因为保留对类加载器的引用而导致的内存泄漏问题。
    // 详细说明请参考 ClassKey 类的 javadoc。
    final Map<ClassKey, Long> index = new IdentityHashMap<>();
    return (type) -> {
      if (type.isAssignableFrom(boolean.class)) {
        // 注意：当记录有多个原始布尔组件时，我们需要回退到通过 exhaustiveComponentSearch(...) 进行详尽的组件搜索
        return true;
      } else if (type.isPrimitive()
          || type.isAssignableFrom(String.class)
          || Number.class.isAssignableFrom(type)) {
        final long currentIndex = index.compute(new ClassKey(type),
            (k, value) -> value == null ? 1L : value + 1L);
        if (type.isAssignableFrom(String.class)) {
          return String.valueOf(currentIndex);
        } else if (type.equals(byte.class) || type.equals(Byte.class)) {
          return safeNumberCast(currentIndex, (byte) currentIndex);
        } else if (type.equals(short.class) || type.equals(Short.class)) {
          return safeNumberCast(currentIndex, (short) currentIndex);
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
          return safeNumberCast(currentIndex, (int) currentIndex);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
          return currentIndex;
        } else if (type.equals(float.class) || type.equals(Float.class)) {
          return safeNumberCast(currentIndex, (float) currentIndex);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
          return safeNumberCast(currentIndex, (double) currentIndex);
        } else if (type.equals(char.class)) {
          return safeNumberCast(currentIndex, (char) currentIndex);
        }
      }
      return getDummyObjectInstance(type);
    };
  }

  private static Object getDummyObjectInstance(final Class<?> type) {
    if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
      if (type.isSealed()) {
        for (final Class<?> permittedSubclass : type.getPermittedSubclasses()) {
          return getDummyObjectInstance(permittedSubclass);
        }
      }
      final Class<?> dummyClass = DUMMY_SUBCLASSES.get(type);
      return ObjenesisHelper.newInstance(dummyClass);
    }
    return ObjenesisHelper.newInstance(type);
  }

  private static <T extends Number> T safeNumberCast(final long currentIndex,
      final T castedValue) {
    return safeNumberCast(currentIndex, castedValue, castedValue.longValue(), castedValue.getClass());
  }

  private static char safeNumberCast(final long currentIndex, final char castedValue) {
    return safeNumberCast(currentIndex, castedValue, castedValue, char.class);
  }

  private static <T> T safeNumberCast(final long currentIndex, final T castedValue,
      final long castedValueAsLong, final Class<?> valueType) {
    // 这目前不可能测试，因为记录不能有超过 255 个组件
    if (castedValueAsLong != currentIndex) {
      throw new IllegalArgumentException("Having more than "
          + (currentIndex - 1)
          + " record components of type "
          + valueType.getName()
          + " is currently not supported");
    }
    return castedValue;
  }

  private static <T> Method exhaustiveComponentSearch(final Object currentValue,
      final Class<T> recordClass, final NonVoidMethod0<T, ?> componentAccessor,
      final Object[] uniqueValues, final Constructor<T> recordCtor)
      throws ReflectiveOperationException {
    final Object[] values = Arrays.copyOf(uniqueValues, uniqueValues.length);
    int nextIndex;
    while ((nextIndex = ArrayUtils.indexOf(values, currentValue)) >= 0) {
      values[nextIndex] = getDefaultValue(currentValue);
      final T record = ConstructorUtils.newInstance(recordCtor, values);
      final Object value = componentAccessor.invoke(record);
      if (value == values[nextIndex]) {
        return getRecordComponentAccessor(recordClass, nextIndex);
      }
    }
    throw new IllegalArgumentException("Failed to find the component of type "
        + currentValue.getClass().getName()
        + " in the record "
        + recordClass.getName()
        + " using the provided component accessor.");
  }

  private static Method getRecordComponentAccessor(final Class<?> recordClass,
      final int componentIndex) {
    return getEnumComponents(recordClass)
        .skip(componentIndex)
        .findFirst()
        .map(RecordComponent::getAccessor)
        .orElseThrow(IllegalStateException::new);
  }

  private static Object getDefaultValue(final Object value) {
    if (!(value instanceof Boolean)) {
      throw new IllegalArgumentException("This is currently only expected to happen for boolean types");
    }
    return false;
  }

  private static boolean needsFallbackToComponentSearch(final Object[] uniqueValues,
      final Object value) {
    if (!(value instanceof Boolean)) {
      return false;
    }
    final int firstIndex = ArrayUtils.indexOf(uniqueValues, value);
    final int lastIndex = ArrayUtils.lastIndexOf(uniqueValues, value);
    return firstIndex != lastIndex;
  }
}