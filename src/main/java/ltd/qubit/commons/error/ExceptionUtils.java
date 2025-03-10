////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Provides utility methods for handling exceptions.
 * <p>
 * This class is copied from the {@code org.springframework.util.ReflectionUtils}
 * class of the Spring Framework. It is used to avoid the dependency of Spring
 * Framework.
 *
 * @author Haixing Hu
 */
public class ExceptionUtils {

  /**
   * Handle the given reflection exception.
   * <p>
   * Should only be called if no checked exception is expected to be thrown by a
   * target method, or if an error occurs while accessing a method or field.
   * <p>
   * Throws the underlying RuntimeException or Error in case of an
   * InvocationTargetException with such a root cause. Throws an
   * IllegalStateException with an appropriate message or
   * UndeclaredThrowableException otherwise.
   *
   * @param ex
   *     the reflection exception to handle
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
   * Handle the given invocation target exception. Should only be called if no
   * checked exception is expected to be thrown by the target method.
   * <p>
   * Throws the underlying RuntimeException or Error in case of such a root
   * cause. Throws an UndeclaredThrowableException otherwise.
   *
   * @param ex
   *     the invocation target exception to handle
   */
  public static void handleInvocationTargetException(final InvocationTargetException ex) {
    rethrowRuntimeException(ex.getTargetException());
  }

  /**
   * Rethrow the given {@link Throwable exception}, which is presumably the
   * <em>target exception</em> of an {@link InvocationTargetException}.
   * <p>
   * Should only be called if no checked exception is expected to be thrown by
   * the target method.
   * <p>
   * Rethrows the underlying exception cast to a {@link RuntimeException} or
   * {@link Error} if appropriate; otherwise, throws an
   * {@link UndeclaredThrowableException}.
   *
   * @param ex
   *     the exception to rethrow
   * @throws RuntimeException
   *     the rethrown exception
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
   * Rethrow the given {@link Throwable exception}, which is presumably the
   * <em>target exception</em> of an {@link InvocationTargetException}.
   * <p>
   * Should only be called if no checked exception is expected to be thrown by
   * the target method.
   * <p>
   * Rethrows the underlying exception cast to an {@link Exception} or
   * {@link Error} if appropriate; otherwise, throws an
   * {@link UndeclaredThrowableException}.
   *
   * @param throwable
   *     the exception to rethrow
   * @throws Exception
   *     the rethrown exception (in case of a checked exception)
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
