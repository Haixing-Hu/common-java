////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.ArrayUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ClassUtils.isEnumType;
import static ltd.qubit.commons.lang.ClassUtils.isRecordType;
import static ltd.qubit.commons.reflect.MethodUtils.getMethod;
import static ltd.qubit.commons.text.CaseFormat.LOWER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_CAMEL;

/**
 * Provides utility functions for the Java bean properties.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class PropertyUtils {

  public static final String GET_PREFIX = "get";
  public static final String SET_PREFIX = "set";
  public static final String IS_PREFIX = "is";

  public static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");

  public static final Pattern SET_PATTERN = Pattern.compile("^set[A-Z].*");

  public static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");


  private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

  private static final PropertyDescriptor[] EMPTY_PROPERTY_DESCRIPTORS =
      new PropertyDescriptor[0];

  private static final ClassValue<PropertyDescriptor[]> DESCRIPTOR_CACHE =
      new ClassValue<PropertyDescriptor[]>() {
        @Override
        protected PropertyDescriptor[] computeValue(final Class<?> type) {
          // Introspect the bean and cache the generated descriptors
          BeanInfo beanInfo = null;
          try {
            beanInfo = Introspector.getBeanInfo(type);
          } catch (final IntrospectionException e) {
            LOGGER.error("Failed to get the bean info for {}", type.getName(), e);
            return EMPTY_PROPERTY_DESCRIPTORS;
          }
          final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
          if (descriptors == null) {
            return EMPTY_PROPERTY_DESCRIPTORS;
          } else {
            return descriptors;
          }
        }
      };

  /**
   * 判定指定的方法名称是否匹配 Java Bean 的 getter 方法模式，即 {@code getXxx()} 形式。
   *
   * @param methodName
   *     指定的方法名称。
   * @return
   *     如果指定的方法名称匹配 Java Bean 的 getter 方法模式返回{@code true} ；否则返回{@code false}。
   */
  public static boolean matchGetterName(final String methodName) {
    return GET_PATTERN.matcher(methodName).matches();
  }

  /**
   * 判定指定的方法名称是否匹配 Java Bean 的 setter 方法模式，即 {@code setXxx()} 形式。
   *
   * @param methodName
   *     指定的方法名称。
   * @return
   *     如果指定的方法名称匹配 Java Bean 的 setter 方法模式返回{@code true} ；否则返回{@code false}。
   */
  public static boolean matchSetterName(final String methodName) {
    return SET_PATTERN.matcher(methodName).matches();
  }

  /**
   * 判定指定的方法名称是否匹配 Java Bean 的 is 方法模式，即 {@code isXxx()} 形式。
   *
   * @param methodName
   *     指定的方法名称。
   * @return
   *     如果指定的方法名称匹配 Java Bean 的 is 方法模式返回{@code true} ；否则返回{@code false}。
   */
  public static boolean matchIsName(final String methodName) {
    return IS_PATTERN.matcher(methodName).matches();
  }

  /**
   * Retrieve the property descriptors for the specified class, introspecting
   * and caching them the first time a particular bean class is encountered.
   *
   * <p>ODO: this function only get the simple properties and indexed
   * properties, the supporting for the mapped properties should be added.
   *
   * @param cls
   *     Bean class for which property descriptors are requested
   * @return the property descriptors
   */
  public static PropertyDescriptor[] getDescriptors(final Class<?> cls) {
    requireNonNull("cls", cls);
    return DESCRIPTOR_CACHE.get(cls);
  }

  /**
   * Retrieve the property descriptor for the specified property of the
   * specified bean, or return {@code null} if there is no such descriptor.
   *
   * <p>This method resolves indexed and nested property references in the same
   * manner as other methods in this class, except that if the last (or only)
   * name element is indexed, the descriptor for the last resolved property
   * itself is returned.
   *
   * @param cls
   *     The class of a bean for which a property descriptor is requested.
   * @param name
   *     Possibly indexed and/or nested name of the property for which a
   *     property descriptor is requested.
   * @return the property descriptor.
   */
  public static PropertyDescriptor getDescriptor(final Class<?> cls,
      final String name) {
    requireNonNull("cls", cls);
    requireNonNull("name", name);
    final PropertyDescriptor[] descriptors = getDescriptors(cls);
    if (descriptors != null) {
      for (final PropertyDescriptor descriptor : descriptors) {
        if (name.equals(descriptor.getName())) {
          return descriptor;
        }
      }
    }
    return null;
  }

  /**
   * Return an accessible property getter method for this property, if there is
   * one; otherwise return {@code null}.
   *
   * @param clazz
   *     The class of the read method will be invoked on
   * @param descriptor
   *     Property descriptor to return a getter for
   * @return The read method
   */
  public static Method getReadMethod(final Class<?> clazz,
      final PropertyDescriptor descriptor) {
    final Method method = descriptor.getReadMethod();
    return getMethod(clazz, Option.BEAN_METHOD, method.getName(),
        method.getParameterTypes());
  }

  /**
   * Return an accessible property setter method for this property, if there is
   * one; otherwise return {@code null}.
   *
   * @param clazz
   *     The class of the write method will be invoked on.
   * @param descriptor
   *     Property descriptor to return a setter for
   * @return The wrute method
   */
  public static Method getWriteMethod(final Class<?> clazz,
      final PropertyDescriptor descriptor) {
    final Method method = descriptor.getWriteMethod();
    return getMethod(clazz, Option.BEAN_METHOD, method.getName(),
        method.getParameterTypes());
  }

  /**
   * Return the value of the specified simple property of the specified bean,
   * with no type conversions.
   *
   * @param bean
   *     Bean whose property is to be extracted
   * @param name
   *     Name of the property to be extracted
   * @return The property value
   * @throws IllegalAccessException
   *     if the caller does not have access to the property accessor method
   * @throws NullPointerException
   *     if {@code bean} or {@code name} is null
   * @throws InvocationTargetException
   *     if the property accessor method throws an exception
   * @throws NoSuchMethodException
   *     if an accessor method for this property cannot be found.
   */
  public static Object getSimpleProperty(final Object bean, final String name)
      throws IllegalAccessException, NullPointerException,
      InvocationTargetException, NoSuchMethodException {
    final Class<?> beanClass = bean.getClass();
    final PropertyDescriptor descriptor = getDescriptor(beanClass, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" + name
          + "' in the bean class '" + beanClass.getName() + "'");
    }
    final Method readMethod = getReadMethod(bean.getClass(), descriptor);
    if (readMethod == null) {
      throw new NoSuchMethodException("Property '" + name
          + "' has no getter method in the bean class '" + bean.getClass()
          + "'");
    }
    // Call the property getter and return the value
    final Object value = MethodUtils.invokeMethod(readMethod, bean);
    return value;
  }

  /**
   * Return the Java Class representing the property type of the specified
   * property, or {@code null} if there is no such property for the specified
   * bean.
   *
   * <p>This method follows the same name resolution rules used by {@code
   * getPropertyDescriptor()}, so if the last element of a name reference is
   * indexed, the type of the property itself will be returned. If the last (or
   * only) element has no property with the specified name, {@code null} is
   * returned.
   *
   * @param beanClass
   *     the class of the bean for which a property descriptor is requested
   * @param name
   *     Possibly indexed and/or nested name of the property for which a
   *     property descriptor is requested
   * @return The property type
   * @throws IllegalAccessException
   *     if the caller does not have access to the property accessor method
   * @throws NullPointerException
   *     if {@code bean} or {@code name} is null
   * @throws InvocationTargetException
   *     if the property accessor method throws an exception
   * @throws NoSuchMethodException
   *     if an accessor method for this propety cannot be found
   */
  public static Class<?> getPropertyType(final Class<?> beanClass,
      final String name)
      throws IllegalAccessException, NullPointerException,
      InvocationTargetException, NoSuchMethodException {
    final PropertyDescriptor descriptor = getDescriptor(beanClass, name);
    if (descriptor == null) {
      return (null);
    } else if (descriptor instanceof IndexedPropertyDescriptor) {
      return (((IndexedPropertyDescriptor) descriptor)
          .getIndexedPropertyType());
    } else {
      return (descriptor.getPropertyType());
    }
  }

  /**
   * Set the value of the specified simple property of the specified bean, with
   * no type conversions.
   *
   * @param bean
   *     Bean whose property is to be modified
   * @param name
   *     Name of the property to be modified
   * @param value
   *     Value to which the property should be set
   * @throws IllegalAccessException
   *     if the caller does not have access to the property accessor method
   * @throws NullPointerException
   *     if {@code bean} or {@code name} is null
   * @throws InvocationTargetException
   *     if the property accessor method throws an exception
   * @throws NoSuchMethodException
   *     if an accessor method for this propety cannot be found
   */
  public static void setSimpleProperty(final Object bean, final String name,
      final Object value)
      throws IllegalAccessException, NullPointerException,
      InvocationTargetException, NoSuchMethodException {
    // Retrieve the property setter method for the specified property
    final Class<?> beanClass = bean.getClass();
    final PropertyDescriptor descriptor = getDescriptor(beanClass, name);
    if (descriptor == null) {
      throw new NoSuchMethodException("Unknown property '" + name
          + "' on class '" + beanClass + "'");
    }
    final Method writeMethod = getWriteMethod(beanClass, descriptor);
    if (writeMethod == null) {
      throw new NoSuchMethodException("Property '" + name
          + "' has no setter method in class '" + beanClass + "'");
    }
    // Call the property setter method
    MethodUtils.invokeMethod(writeMethod, bean, value);
  }

  /**
   * 根据 Java Bean 的 getter 方法获取其对应的属性名称。
   *
   * @param method
   *     指定的属性的 getter 方法，其名称必须是形如 getXxx() 或 isXxx() 的形式，
   *     或者是 "xxx()" 的形式，且没有参数。
   * @return
   *     指定的 getter 方法所对应的属性的名称；若指定的方法不是标准的Java Bean的getter方法，
   *     返回{@code null}。
   */
  @Nullable
  public static String getPropertyNameFromGetter(final Method method) {
    if (!isNoArgumentNonVoidReturnMethod(method)) {
      return null;
    }
    final String methodName = method.getName();
    if (matchGetterName(methodName)) {
      // 标准 getXxxx() 形式
      return parsePropertyName(methodName, GET_PREFIX);
    } else if (matchIsName(methodName)) {
      // 标准 isXxxx() 形式
      return parsePropertyName(methodName, IS_PREFIX);
    } else if ((isEnumType(method.getDeclaringClass()) || isRecordType(method.getDeclaringClass()))
        && (!matchSetterName(methodName))) {
      // 对于枚举类和集合类，setter 可以是 xxxx() 形式
      return methodName;
    } else if (ArrayUtils.contains(SPECIAL_GETTER_NAMES, methodName)) {
      // 一些特殊的 getter 名称需要特殊处理
      return methodName;
    } else {
      return null;
    }
  }

  private static final String[] SPECIAL_GETTER_NAMES = {
    "length",
    "size",
  };

  private static boolean isNoArgumentNonVoidReturnMethod(final Method method) {
    return (method.getParameterCount() == 0)
        && (method.getReturnType() != void.class)
        && (method.getReturnType() != Void.class);
  }

  /**
   * 根据 Java Bean 的 setter 方法获取其对应的属性名称。
   *
   * @param setter
   *     指定的属性的 setter 方法，其名称必须是形如 setXxx() 的形式，且只有一个参数。
   * @return
   *     指定的 setter 方法所对应的属性的名称；若指定的方法不是标准的Java Bean的 setter 方法，
   *     返回{@code null}。
   */
  @Nullable
  public static String getPropertyNameFromSetter(final Method setter) {
    final String getterName = setter.getName();
    if (matchSetterName(getterName) && setter.getParameterCount() == 1) { // this is a setter
      return parsePropertyName(getterName, SET_PREFIX);
    } else {
      return null;
    }
  }

  private static String parsePropertyName(final String methodName,
      final String prefix) {
    final String name = methodName.substring(prefix.length());
    return UPPER_CAMEL.to(LOWER_CAMEL, name);
  }
}