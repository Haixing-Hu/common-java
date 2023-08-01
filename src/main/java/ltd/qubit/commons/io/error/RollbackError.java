////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * Thrown to indicate that an error or exception is occurred during the roll
 * back operation.
 *
 * @author Haixing Hu
 */
public class RollbackError extends Error {

  private static final long serialVersionUID = 8638885014873271424L;

  private static final String MESSAGE = "An error occurrs during the roll "
      + "back operation. The data may be lost.";

  public RollbackError() {
    super(MESSAGE);
  }

  public RollbackError(final String message) {
    super(message);
  }

  public RollbackError(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public RollbackError(final String message, final Throwable cause) {
    super(message, cause);
  }
}
