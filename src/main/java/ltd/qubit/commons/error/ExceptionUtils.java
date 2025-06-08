////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 提供处理异常的实用方法。
 *
 * <p>此类复制自 Spring Framework 的 {@code org.springframework.util.ReflectionUtils} 类。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 */
public class ExceptionUtils {

  /**
   * 处理给定的反射异常。
   *
   * <p>仅当目标方法不希望抛出检查异常，或者在访问方法或字段时发生错误时才应调用此方法。
   *
   * <p>在 InvocationTargetException 具有此类根原因的情况下，抛出底层的
   * RuntimeException 或 Error。否则，抛出带有适当消息的 IllegalStateException
   * 或 UndeclaredThrowableException。
   *
   * @param ex
   *     要处理的反射异常。
   */
  public static void handleReflectionException(final Exception ex) {
    if (ex instanceof NoSuchMethodException) {
      throw new IllegalStateException("Method not found: " + ex.getMessage());
    }
    if (ex instanceof IllegalAccessException) {
      throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
    }
    if (ex instanceof InvocationTargetException) {
      handleInvocationTargetException((InvocationTargetException) ex);
    }
    if (ex instanceof RuntimeException) {
      throw (RuntimeException) ex;
    }
    throw new UndeclaredThrowableException(ex);
  }

  /**
   * 处理给定的调用目标异常。
   *
   * <p>仅当目标方法不希望抛出检查异常时才应调用此方法。
   *
   * <p>在此类根原因的情况下，抛出底层的 RuntimeException 或 Error。否则抛出
   * UndeclaredThrowableException。
   *
   * @param ex
   *     要处理的调用目标异常。
   */
  public static void handleInvocationTargetException(final InvocationTargetException ex) {
    rethrowRuntimeException(ex.getTargetException());
  }

  /**
   * 重新抛出给定的 {@link Throwable exception}，它可能是
   * {@link InvocationTargetException} 的<i>目标异常</i>。
   *
   * <p>仅当目标方法不希望抛出检查异常时才应调用此方法。
   *
   * <p>如果适用，将底层异常转换为 {@link RuntimeException} 或 {@link Error}
   * 并重新抛出；否则，抛出 {@link UndeclaredThrowableException}。
   *
   * @param ex
   *     要重新抛出的异常。
   * @throws RuntimeException
   *     重新抛出的异常。
   */
  public static void rethrowRuntimeException(final Throwable ex) {
    if (ex instanceof RuntimeException) {
      throw (RuntimeException) ex;
    }
    if (ex instanceof Error) {
      throw (Error) ex;
    }
    throw new UndeclaredThrowableException(ex);
  }

  /**
   * 重新抛出给定的 {@link Throwable exception}，它可能是
   * {@link InvocationTargetException} 的<i>目标异常</i>。
   *
   * <p>仅当目标方法不希望抛出检查异常时才应调用此方法。
   *
   * <p>如果适用，将底层异常转换为 {@link Exception} 或 {@link Error}
   * 并重新抛出；否则，抛出 {@link UndeclaredThrowableException}。
   *
   * @param throwable
   *     要重新抛出的异常。
   * @throws Exception
   *     重新抛出的异常（在检查异常的情况下）。
   */
  public static void rethrowException(final Throwable throwable)
      throws Exception {
    if (throwable instanceof Exception) {
      throw (Exception) throwable;
    }
    if (throwable instanceof Error) {
      throw (Error) throwable;
    }
    throw new UndeclaredThrowableException(throwable);
  }
}