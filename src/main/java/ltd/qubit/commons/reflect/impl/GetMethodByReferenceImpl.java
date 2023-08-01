////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.Option;
import ltd.qubit.commons.reflect.ReflectionException;

import java.lang.reflect.Method;

public class GetMethodByReferenceImpl {

  public static final ClassValue<ReferenceToMethodCache<?>>
      GETTER_METHOD_CACHES = new ClassValue<>() {
        @Override
        protected ReferenceToMethodCache<?> computeValue(final Class<?> type) {
          return new ReferenceToMethodCache<>();
        }
      };

  public static <T> T createProxy(final Class<T> clazz,
      final MethodCaptor captor) {
    final Class<? extends T> proxyClass = MethodCaptor.getProxyClass(clazz);
    try {
      final T proxy = ConstructorUtils.newInstance(proxyClass);
      // write the captor object to the MethodCaptor.FIELD_NAME of the proxy instance
      FieldUtils.writeField(proxyClass, Option.ALL, MethodCaptor.FIELD_NAME,
          proxy, captor);
      return proxy;
    } catch (final IllegalAccessError e) {
      throw new ReflectionException("Failed to create proxy on " + clazz, e);
    }
  }

  // TODO: support Java 15+ record

  public static <T, R> Method findMethod(final Class<T> clazz,
      final NonVoidMethod0<T, R> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    ref.invoke(proxy);
    return captor.getCapturedMethod();
  }

  public static <T, R, P1> Method findMethod(final Class<T> clazz,
      final NonVoidMethod1<T, R, P1> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2> Method findMethod(final Class<T> clazz,
      final NonVoidMethod2<T, R, P1, P2> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3> Method findMethod(final Class<T> clazz,
      final NonVoidMethod3<T, R, P1, P2, P3> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3, P4> Method findMethod(final Class<T> clazz,
      final NonVoidMethod4<T, R, P1, P2, P3, P4> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3, P4, P5> Method findMethod(
      final Class<T> clazz,
      final NonVoidMethod5<T, R, P1, P2, P3, P4, P5> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3, P4, P5, P6> Method findMethod(
      final Class<T> clazz,
      final NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3, P4, P5, P6, P7> Method findMethod(
      final Class<T> clazz,
      final NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8> Method findMethod(
      final Class<T> clazz,
      final NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method findMethod(
      final Class<T> clazz,
      final NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T> Method findMethod(final Class<T> clazz,
      final VoidMethod0<T> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    ref.invoke(proxy);
    return captor.getCapturedMethod();
  }

  public static <T, P1> Method findMethod(final Class<T> clazz,
      final VoidMethod1<T, P1> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2> Method findMethod(final Class<T> clazz,
      final VoidMethod2<T, P1, P2> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3> Method findMethod(final Class<T> clazz,
      final VoidMethod3<T, P1, P2, P3> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3, P4> Method findMethod(final Class<T> clazz,
      final VoidMethod4<T, P1, P2, P3, P4> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3, P4, P5> Method findMethod(final Class<T> clazz,
      final VoidMethod5<T, P1, P2, P3, P4, P5> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3, P4, P5, P6> Method findMethod(
      final Class<T> clazz, final VoidMethod6<T, P1, P2, P3, P4, P5, P6> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3, P4, P5, P6, P7> Method findMethod(
      final Class<T> clazz,
      final VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3, P4, P5, P6, P7, P8> Method findMethod(
      final Class<T> clazz,
      final VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }

  public static <T, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method findMethod(
      final Class<T> clazz,
      final VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final MethodCaptor captor = new MethodCaptor();
    final T proxy = createProxy(clazz, captor);
    try {
      ref.invoke(proxy, null, null, null, null, null, null, null, null, null);
    } catch (final NullPointerException e) {
      throw new IllegalArgumentException(
          "The method must have non-primitive arguments.");
    }
    return captor.getCapturedMethod();
  }
}
