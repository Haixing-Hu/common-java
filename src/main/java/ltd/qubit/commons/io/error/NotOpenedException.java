////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

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
