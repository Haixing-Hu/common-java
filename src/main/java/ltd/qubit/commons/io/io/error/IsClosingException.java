////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.error;

import java.io.IOException;

/**
 * Thrown to indicate a file or a device is being closed.
 *
 * @author Haixing Hu
 */
public class IsClosingException extends IOException {

  private static final long serialVersionUID = - 4707122216334525975L;

  public IsClosingException() {
    super("The file, directory or device is being closed.");
  }

  public IsClosingException(final String message) {
    super(message);
  }
}
