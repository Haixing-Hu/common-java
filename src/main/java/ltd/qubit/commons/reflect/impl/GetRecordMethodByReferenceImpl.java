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

import static ltd.qubit.commons.reflect.impl.GetMethodByReferenceThroughSerialization.findMethodBySerialization;

/**
 * 通过方法引用获取记录类方法的实现类。
 *
 * <p>该类提供了专门用于处理 Java 14+ 引入的记录（Record）类型的方法查找功能。
 * 通过分析记录类的组件和构造函数，能够根据方法引用快速定位到对应的访问器方法。</p>
 *
 * @author 胡海星
 */
public class GetRecordMethodByReferenceImpl {

  private static final ClassValue<Class<?>> DUMMY_SUBCLASSES =
      ClassValues.create(GetRecordMethodByReferenceImpl::createDummyProxyClass);

  /**
   * 为指定类型创建虚拟代理类。
   *
   * @param type 要创建代理的类型
   * @return 创建的代理类
   */
  private static Class<?> createDummyProxyClass(final Class<?> type) {
    try (final DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
        .subclass(type)
        .make()) {
      return unloadedType
          .load(GetRecordMethodByReferenceImpl.class.getClassLoader())
          .getLoaded();
    }
  }

  /**
   * 在记录类中查找指定方法引用对应的方法。
   *
   * @param <T> 记录类的类型
   * @param <R> 方法返回值的类型
   * @param clazz 记录类的Class对象
   * @param ref 方法引用
   * @return 找到的方法对象
   * @throws IllegalArgumentException 如果指定的类不是记录类
   */
  public static <T, R> Method findRecordMethod(final Class<T> clazz,
      final NonVoidMethod0<T, R> ref) {
    if (!clazz.isRecord()) {
      throw new IllegalArgumentException(clazz + " is not a record");
    }
    final Method result = findRecordMethodImpl(clazz, ref);
    if (result == null) {
      return findMethodBySerialization(clazz, ref);
    } else {
      return result;
    }
  }

  /**
   * 查找记录类方法的核心实现。
   *
   * @param <T> 记录类的类型
   * @param <R> 方法返回值的类型
   * @param clazz 记录类的Class对象
   * @param ref 方法引用
   * @return 找到的方法对象，如果未找到则返回null
   */
  static <T, R> Method findRecordMethodImpl(final Class<T> clazz,
      final NonVoidMethod0<T, R> ref) {
    final Object[] uniqueValues = buildUniqueValues(clazz);
    try {
      final Constructor<T> ctor = getRecordConstructor(clazz);
      final T record = ConstructorUtils.newInstance(ctor, uniqueValues);
      final Object value = ref.invoke(record);
      if (needsFallbackToComponentSearch(uniqueValues, value)) {
        return exhaustiveComponentSearch(value, clazz, ref, uniqueValues, ctor);
      }
      final int componentIndex = ArrayUtils.indexOf(uniqueValues, value);
      if (componentIndex < 0) {
        return null;
      }
      return getRecordComponentAccessor(clazz, componentIndex);
    } catch (final ReflectiveOperationException e) {
      throw new ReflectionException(e);
    }
  }

  /**
   * 获取记录类的所有组件。
   *
   * @param clazz 记录类的Class对象
   * @return 记录组件的流
   */
  static Stream<RecordComponent> getRecordComponents(final Class<?> clazz) {
    return Arrays.stream(clazz.getRecordComponents());
  }

  /**
   * 获取记录类的构造函数。
   *
   * @param <T> 记录类的类型
   * @param clazz 记录类的Class对象
   * @return 记录类的构造函数
   * @throws NoSuchMethodException 如果找不到对应的构造函数
   */
  static <T> Constructor<T> getRecordConstructor(final Class<T> clazz)
      throws NoSuchMethodException {
    final Class<?>[] ctorTypes = getRecordComponents(clazz)
        .map(RecordComponent::getType)
        .toArray(Class[]::new);
    return clazz.getDeclaredConstructor(ctorTypes);
  }

  /**
   * 为记录类的每个组件构建唯一值。
   *
   * @param recordClass 记录类的Class对象
   * @return 包含唯一值的数组
   */
  private static Object[] buildUniqueValues(final Class<?> recordClass) {
    return getRecordComponents(recordClass)
        .map(RecordComponent::getType)
        .map(uniqueValueBuilder())
        .toArray(Object[]::new);
  }

  /**
   * 创建唯一值构建器函数。
   *
   * @return 根据类型生成唯一值的函数
   */
  private static Function<Class<?>, Object> uniqueValueBuilder() {
    // 使用 ClassKey 作为 Map 的键，而不是直接使用 Class 对象。
    // 这样可以避免在 Web 容器热部署环境中因为保留对类加载器的引用而导致的内存泄漏问题。
    // 详细说明请参考 ClassKey 类的 javadoc。
    final Map<ClassKey, Long> index = new IdentityHashMap<>();
    return (type) -> {
      if (type.isAssignableFrom(boolean.class)) {
        // Note: When the record has more than one primitive boolean component,
        //       we need to fall back to an exhaustive component search via exhaustiveComponentSearch(…)
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

  /**
   * 获取指定类型的虚拟对象实例。
   *
   * @param type 目标类型
   * @return 该类型的虚拟实例
   */
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

  /**
   * 安全地进行数字类型转换。
   *
   * @param <T> 数字类型
   * @param currentIndex 当前索引
   * @param castedValue 转换后的值
   * @return 安全转换后的值
   */
  private static <T extends Number> T safeNumberCast(final long currentIndex,
      final T castedValue) {
    return safeNumberCast(currentIndex, castedValue, castedValue.longValue(), castedValue.getClass());
  }

  /**
   * 安全地进行字符类型转换。
   *
   * @param currentIndex 当前索引
   * @param castedValue 转换后的字符值
   * @return 安全转换后的字符值
   */
  private static char safeNumberCast(final long currentIndex, final char castedValue) {
    return safeNumberCast(currentIndex, castedValue, castedValue, char.class);
  }

  /**
   * 执行安全的数字类型转换核心逻辑。
   *
   * @param <T> 返回类型
   * @param currentIndex 当前索引
   * @param castedValue 转换后的值
   * @param castedValueAsLong 转换为long类型的值
   * @param valueType 值的类型
   * @return 安全转换后的值
   * @throws IllegalArgumentException 如果转换后的值与原索引不匹配
   */
  private static <T> T safeNumberCast(final long currentIndex, final T castedValue,
      final long castedValueAsLong, final Class<?> valueType) {
    // This is currently not possible to test since a record must not have more than 255 components
    if (castedValueAsLong != currentIndex) {
      throw new IllegalArgumentException("Having more than "
          + (currentIndex - 1)
          + " record components of type "
          + valueType.getName()
          + " is currently not supported");
    }
    return castedValue;
  }

  /**
   * 执行详尽的组件搜索。
   *
   * @param <T> 记录类的类型
   * @param currentValue 当前值
   * @param recordClass 记录类
   * @param componentAccessor 组件访问器
   * @param uniqueValues 唯一值数组
   * @param recordCtor 记录构造函数
   * @return 找到的方法
   * @throws ReflectiveOperationException 如果反射操作失败
   * @throws IllegalArgumentException 如果无法找到对应的组件
   */
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

  /**
   * 获取记录组件的访问器方法。
   *
   * @param recordClass 记录类
   * @param componentIndex 组件索引
   * @return 组件的访问器方法
   * @throws IllegalStateException 如果无法找到对应索引的组件
   */
  private static Method getRecordComponentAccessor(final Class<?> recordClass,
      final int componentIndex) {
    return getRecordComponents(recordClass)
        .skip(componentIndex)
        .findFirst()
        .map(RecordComponent::getAccessor)
        .orElseThrow(IllegalStateException::new);
  }

  /**
   * 获取指定值的默认值。
   *
   * @param value 输入值
   * @return 对应的默认值
   * @throws IllegalArgumentException 如果值不是布尔类型
   */
  private static Object getDefaultValue(final Object value) {
    if (!(value instanceof Boolean)) {
      throw new IllegalArgumentException("This is currently only expected to happen for boolean types");
    }
    return false;
  }

  /**
   * 检查是否需要回退到组件搜索。
   *
   * @param uniqueValues 唯一值数组
   * @param value 要检查的值
   * @return 如果需要回退到组件搜索则返回true
   */
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