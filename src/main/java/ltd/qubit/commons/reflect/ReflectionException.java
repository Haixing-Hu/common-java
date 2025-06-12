////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.Serial;

/**
 * 此异常表示在反射操作期间发生异常。
 *
 * @author 胡海星
 */
public class ReflectionException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -4383352207393863063L;

  private static final String DEFAULT_MESSAGE =
      "An exception occurs during the reflection operation: ";

  /**
   * 构造一个 {@link ReflectionException}。
   */
  public ReflectionException() {
    super(DEFAULT_MESSAGE);
  }

  /**
   * 构造一个 {@link ReflectionException}。
   *
   * @param message
   *     消息
   */
  public ReflectionException(final String message) {
    super(message);
  }

  /**
   * 构造一个 {@link ReflectionException}。
   *
   * @param cause
   *     原因
   */
  public ReflectionException(final Throwable cause) {
    super(DEFAULT_MESSAGE + cause.getMessage(), cause);
  }

  /**
   * 构造一个 {@link ReflectionException}。
   *
   * @param message
   *     消息
   * @param cause
   *     原因
   */
  public ReflectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

}