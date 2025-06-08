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
 * 抛出此错误以指示发生了意外错误。
 *
 * @author 胡海星
 */
public class UnexpectedError extends Error {

  private static final long serialVersionUID = 7624336604265401254L;

  private static final String MESSAGE = "An unexpected error occurs.";

  /**
   * 构造一个新的意外错误。
   */
  public UnexpectedError() {
    super(MESSAGE);
  }

  /**
   * 构造一个带有指定消息的意外错误。
   *
   * @param message
   *     错误消息。
   */
  public UnexpectedError(final String message) {
    super(message);
  }

  /**
   * 构造一个带有指定原因的意外错误。
   *
   * @param cause
   *     错误原因。
   */
  public UnexpectedError(final Throwable cause) {
    super(MESSAGE, cause);
  }

  /**
   * 构造一个带有指定消息和原因的意外错误。
   *
   * @param message
   *     错误消息。
   * @param cause
   *     错误原因。
   */
  public UnexpectedError(final String message, final Throwable cause) {
    super(message, cause);
  }

}