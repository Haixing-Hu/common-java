////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.impl;

import java.io.IOException;
import java.io.Serial;

/**
 * 当Bean创建错误发生时抛出此异常。
 *
 * @author 胡海星
 */
public class BeanCreationException extends IOException {

  @Serial
  private static final long serialVersionUID = -5948216445929482392L;

  /**
   * 创建一个BeanCreationException异常。
   */
  public BeanCreationException() {
  }

  /**
   * 创建一个BeanCreationException异常。
   *
   * @param message
   *     异常消息。
   */
  public BeanCreationException(final String message) {
    super(message);
  }

  /**
   * 创建一个BeanCreationException异常。
   *
   * @param message
   *     异常消息。
   * @param cause
   *     导致此异常的原因。
   */
  public BeanCreationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * 创建一个BeanCreationException异常。
   *
   * @param cause
   *     导致此异常的原因。
   */
  public BeanCreationException(final Throwable cause) {
    super(cause);
  }
}