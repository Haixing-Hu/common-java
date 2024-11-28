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
 * Thrown to indicate a file or a device has already been closed.
 *
 * @author Haixing Hu
 */
public class AlreadyClosedException extends IOException {

  private static final long serialVersionUID = - 6574812183392346958L;

  public AlreadyClosedException() {
    super("The file, directory or device has already been closed.");
  }

  public AlreadyClosedException(final String message) {
    super(message);
  }
}
