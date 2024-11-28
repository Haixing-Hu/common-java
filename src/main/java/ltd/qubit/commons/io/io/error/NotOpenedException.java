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
 * Thrown to indicate the file or device has note been opened.
 *
 * @author Haixing Hu
 */
public class NotOpenedException extends IOException {

  private static final long serialVersionUID = - 7810592920579567878L;

  public NotOpenedException() {
    super("The object has not been opened.");
  }

  public NotOpenedException(final String message) {
    super(message);
  }
}
