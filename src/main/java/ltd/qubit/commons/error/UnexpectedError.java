////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * Thrown to indicate an unexpected error.
 *
 * @author Haixing Hu
 */
public class UnexpectedError extends Error {

  private static final long serialVersionUID = 7624336604265401254L;

  private static final String MESSAGE = "An unexpected error occurs.";

  public UnexpectedError() {
    super(MESSAGE);
  }

  public UnexpectedError(final String message) {
    super(message);
  }

  public UnexpectedError(final Throwable cause) {
    super(MESSAGE, cause);
  }

  public UnexpectedError(final String message, final Throwable cause) {
    super(message, cause);
  }

}
