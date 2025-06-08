////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * 抛出以指示初始化错误。
 *
 * @author 胡海星
 */
public class InitializationError extends Error {

  private static final long serialVersionUID = - 6911953992435103567L;

  /**
   * 构造一个新的 {@link InitializationError}。
   */
  public InitializationError() {
  }

  /**
   * 使用指定的详细消息构造一个新的 {@link InitializationError}。
   *
   * @param message
   *     详细消息。
   */
  public InitializationError(final String message) {
    super(message);
  }

  /**
   * 使用指定的详细消息和原因构造一个新的 {@link InitializationError}。
   *
   * @param message
   *     详细消息。
   * @param cause
   *     原因。
   */
  public InitializationError(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * 使用指定的原因构造一个新的 {@link InitializationError}。
   *
   * @param cause
   *     原因。
   */
  public InitializationError(final Throwable cause) {
    super(cause);
  }
}