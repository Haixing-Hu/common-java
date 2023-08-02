////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * Thrown to indicate a file or a device has already been opened.
 *
 * @author Haixing Hu
 */
public class AlreadyOpenedException extends IOException {

  private static final long serialVersionUID = 429834574478758972L;

  public AlreadyOpenedException() {
    super("The object has already been opened.");
  }

  public AlreadyOpenedException(final String message) {
    super(message);
  }
}
