////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * 在序列化过程失败时抛出。
 *
 * @author 胡海星
 */
public class SerializationException extends IOException {

  private static final long serialVersionUID = -900238002915030176L;

  /**
   * 构造一个新的 {@link SerializationException}。
   */
  public SerializationException() {
  }

  /**
   * 使用指定的详细消息构造一个新的 {@link SerializationException}。
   *
   * @param msg
   *     详细消息。
   */
  public SerializationException(final String msg) {
    super(msg);
  }

  /**
   * 使用指定的详细消息和原因构造一个新的 {@link SerializationException}。
   *
   * @param msg
   *     详细消息。
   * @param cause
   *     原因。
   */
  public SerializationException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

  /**
   * 使用指定的原因构造一个新的 {@link SerializationException}。
   *
   * @param cause
   *     原因。
   */
  public SerializationException(final Throwable cause) {
    super(cause.getClass().getName() + ": " + cause.getMessage(), cause);
  }
}