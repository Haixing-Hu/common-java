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
 * 提供用于Java Bean属性的工具函数。
 *
 * @author 胡海星
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
   * 检索指定类的属性描述符，首次遇到特定Bean类时进行内省并缓存。
   *
   * <p>注意：此函数仅获取简单属性和索引属性，应添加对映射属性的支持。
   *
   * @param cls
   *     请求属性描述符的Bean类
   * @return 属性描述符
   */
  public static PropertyDescriptor[] getDescriptors(final Class<?> cls) {
    requireNonNull("cls", cls);
    return DESCRIPTOR_CACHE.get(cls);
  }

  /**
   * 检索指定Bean的指定属性的属性描述符，如果没有此类描述符则返回{@code null}。
   *
   * <p>此方法以与此类中其他方法相同的方式解析索引和嵌套属性引用，
   * 但如果最后（或唯一）名称元素被索引，则返回最后解析属性本身的描述符。
   *
   * @param cls
   *     请求属性描述符的Bean类。
   * @param name
   *     可能被索引和/或嵌套的属性名称，用于请求属性描述符。
   * @return 属性描述符。
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
   * 返回此属性的可访问属性getter方法（如果存在）；否则返回{@code null}。
   *
   * @param clazz
   *     将调用读取方法的类
   * @param descriptor
   *     要返回getter的属性描述符
   * @return 读取方法
   */
  public static Method getReadMethod(final Class<?> clazz,
      final PropertyDescriptor descriptor) {
    final Method method = descriptor.getReadMethod();
    return getMethod(clazz, Option.BEAN_METHOD, method.getName(),
        method.getParameterTypes());
  }

  /**
   * 返回此属性的可访问属性setter方法（如果存在）；否则返回{@code null}。
   *
   * @param clazz
   *     将调用写入方法的类。
   * @param descriptor
   *     要返回setter的属性描述符
   * @return 写入方法
   */
  public static Method getWriteMethod(final Class<?> clazz,
      final PropertyDescriptor descriptor) {
    final Method method = descriptor.getWriteMethod();
    return getMethod(clazz, Option.BEAN_METHOD, method.getName(),
        method.getParameterTypes());
  }

  /**
   * 返回指定Bean的指定简单属性的值，不进行类型转换。
   *
   * @param bean
   *     要提取属性的Bean
   * @param name
   *     要提取的属性名称
   * @return 属性值
   * @throws IllegalAccessException
   *     如果调用者没有访问属性访问器方法的权限
   * @throws NullPointerException
   *     如果{@code bean}或{@code name}为null
   * @throws InvocationTargetException
   *     如果属性访问器方法抛出异常
   * @throws NoSuchMethodException
   *     如果找不到此属性的访问器方法。
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
   * 返回表示指定属性的属性类型的Java类，如果指定Bean没有此类属性则返回{@code null}。
   *
   * <p>此方法遵循与{@code getPropertyDescriptor()}使用的相同名称解析规则，
   * 因此如果名称引用的最后一个元素被索引，则将返回属性本身的类型。
   * 如果最后（或唯一）元素没有指定名称的属性，则返回{@code null}。
   *
   * @param beanClass
   *     请求属性描述符的Bean类
   * @param name
   *     可能被索引和/或嵌套的属性名称，用于请求属性描述符
   * @return 属性类型
   * @throws IllegalAccessException
   *     如果调用者没有访问属性访问器方法的权限
   * @throws NullPointerException
   *     如果{@code bean}或{@code name}为null
   * @throws InvocationTargetException
   *     如果属性访问器方法抛出异常
   * @throws NoSuchMethodException
   *     如果找不到此属性的访问器方法
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
   * 设置指定Bean的指定简单属性的值，不进行类型转换。
   *
   * @param bean
   *     要修改属性的Bean
   * @param name
   *     要修改的属性名称
   * @param value
   *     应设置属性的值
   * @throws IllegalAccessException
   *     如果调用者没有访问属性访问器方法的权限
   * @throws NullPointerException
   *     如果{@code bean}或{@code name}为null
   * @throws InvocationTargetException
   *     如果属性访问器方法抛出异常
   * @throws NoSuchMethodException
   *     如果找不到此属性的访问器方法
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