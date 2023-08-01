////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import ltd.qubit.commons.reflect.MethodUtils;
import ltd.qubit.commons.reflect.ReflectionException;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import static ltd.qubit.commons.lang.ClassUtils.getDefaultValueObject;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class MethodCaptor {

  public static final String FIELD_NAME = "$methodCaptor";

  private final AtomicReference<Method> capturedMethod = new AtomicReference<>();

  public void capture(final Method method) {
    final Method existing = capturedMethod.getAndSet(method);
    if (existing != null) {
      throw new IllegalArgumentException("Method already captured: "
          + existing.getName() + " called twice?");
    }
  }

  public Method getCapturedMethod() {
    final Method method = capturedMethod.get();
    if (method == null) {
      throw new IllegalArgumentException("Method could not be captured. "
          + "This can happen when no method was invoked or the method is final or non-public.");
    }
    return method;
  }

  @RuntimeType
  public static Object intercept(@Origin final Method method,
      @FieldValue(FIELD_NAME) final MethodCaptor methodCaptor) {
    methodCaptor.capture(method);
    return getDefaultValueObject(method.getReturnType());
  }

  private static final ClassValue<Class<?>> PROXY_CLASS_CACHE = new ClassValue<Class<?>>() {
    @Override
    protected Class<?> computeValue(final Class<?> type) {
      return createProxyClass(type);
    }
  };

  @SuppressWarnings("unchecked")
  public static <T> Class<? extends T> getProxyClass(final Class<T> type) {
    return (Class<? extends T>) PROXY_CLASS_CACHE.get(type);
  }

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
