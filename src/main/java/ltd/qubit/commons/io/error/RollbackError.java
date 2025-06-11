////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * 抛出此错误以指示在回滚操作期间发生错误或异常。
 *
 * @author 胡海星
 */
public class RollbackError extends Error {

  private static final long serialVersionUID = 8638885014873271424L;

  private static final String MESSAGE = "An error occurrs during the roll "
      + "back operation. The data may be lost.";

  /**
   * 构造一个 {@link RollbackError}。
   */
  public RollbackError() {
    super(MESSAGE);
  }

  /**
   * 构造一个 {@link RollbackError}。
   *
   * @param message
   *     详细信息。
   */
  public RollbackError(final String message) {
    super(message);
  }

  /**
   * 构造一个 {@link RollbackError}。
   *
   * @param cause
   *     导致此错误的根本原因。
   */
  public RollbackError(final Throwable cause) {
    super(MESSAGE, cause);
  }

  /**
   * 构造一个 {@link RollbackError}。
   *
   * @param message
   *     详细信息。
   * @param cause
   *     导致此错误的根本原因。
   */
  public RollbackError(final String message, final Throwable cause) {
    super(message, cause);
  }
}