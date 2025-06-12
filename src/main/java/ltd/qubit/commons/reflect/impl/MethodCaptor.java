////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nonnull;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import ltd.qubit.commons.reflect.MethodUtils;
import ltd.qubit.commons.reflect.ReflectionException;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.not;

import static ltd.qubit.commons.lang.ClassUtils.getDefaultValueObject;

/**
 * 方法捕获器类，用于通过方法引用捕获方法信息。
 *
 * <p>该类使用ByteBuddy动态代理技术，在方法调用时拦截并捕获方法对象，
 * 从而实现通过方法引用获取Method对象的功能。</p>
 *
 * @author 胡海星
 */
public class MethodCaptor {

  /**
   * 字段名常量，用于在代理类中存储MethodCaptor实例。
   */
  public static final String FIELD_NAME = "$methodCaptor";

  private final AtomicReference<Method> capturedMethod = new AtomicReference<>();

  /**
   * 捕获指定的方法。
   *
   * @param method 要捕获的方法
   * @throws IllegalArgumentException 如果已经捕获了方法
   */
  public void capture(final Method method) {
    final Method existing = capturedMethod.getAndSet(method);
    if (existing != null) {
      throw new IllegalArgumentException("Method already captured: "
          + existing.getName() + " called twice?");
    }
  }

  /**
   * 获取已捕获的方法。
   *
   * @return 捕获的方法对象
   * @throws IllegalArgumentException 如果没有捕获到方法
   */
  public Method getCapturedMethod() {
    final Method method = capturedMethod.get();
    if (method == null) {
      throw new IllegalArgumentException("Method could not be captured. "
          + "This can happen when no method was invoked or the method is final or non-public.");
    }
    return method;
  }

  /**
   * 方法拦截器，用于在代理类中拦截方法调用。
   *
   * @param method 被拦截的方法
   * @param methodCaptor 方法捕获器实例
   * @return 方法返回类型的默认值
   */
  @RuntimeType
  public static Object intercept(@Origin final Method method,
      @FieldValue(FIELD_NAME) final MethodCaptor methodCaptor) {
    methodCaptor.capture(method);
    return getDefaultValueObject(method.getReturnType());
  }

  private static final ClassValue<Class<?>> PROXY_CLASS_CACHE = new ClassValue<>() {
    @Override
    protected Class<?> computeValue(@Nonnull final Class<?> type) {
      return createProxyClass(type);
    }
  };

  /**
   * 获取指定类型的代理类。
   *
   * @param <T> 目标类型
   * @param type 要创建代理的类型
   * @return 代理类的Class对象
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<? extends T> getProxyClass(final Class<T> type) {
    return (Class<? extends T>) PROXY_CLASS_CACHE.get(type);
  }

  /**
   * 为指定类型创建代理类。
   *
   * @param <T> 目标类型
   * @param type 要创建代理的类型
   * @return 创建的代理类
   * @throws ReflectionException 如果创建代理失败
   */
  private static <T> Class<? extends T> createProxyClass(final Class<T> type) {
    try {
      return new ByteBuddy()
          .subclass(type, ConstructorStrategy.Default.NO_CONSTRUCTORS)
          .defineField(FIELD_NAME, MethodCaptor.class, Visibility.PRIVATE)
          .method(isMethod()
              .and(not(isDeclaredBy(Object.class))))
          .intercept(MethodDelegation.to(MethodCaptor.class))
          .make()
          .load(MethodUtils.class.getClassLoader())
          .getLoaded();
    } catch (final IllegalAccessError e) {
      throw new ReflectionException("Failed to create proxy on " + type, e);
    }
  }
}