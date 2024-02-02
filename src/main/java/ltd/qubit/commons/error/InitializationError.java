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
 * Thrown to indicates an initialization error.
 *
 * @author Haixing Hu
 */
public class InitializationError extends Error {

  private static final long serialVersionUID = - 6911953992435103567L;

  public InitializationError() {
  }

  public InitializationError(final String message) {
    super(message);
  }

  public InitializationError(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InitializationError(final Throwable cause) {
    super(cause);
  }
}
