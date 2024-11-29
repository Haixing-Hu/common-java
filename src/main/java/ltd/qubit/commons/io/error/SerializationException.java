////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * Exception thrown when the serialization process fails.
 *
 * @author Haixing Hu
 */
public class SerializationException extends IOException {

  private static final long serialVersionUID = -900238002915030176L;

  public SerializationException() {
  }

  public SerializationException(final String msg) {
    super(msg);
  }

  public SerializationException(final String msg, final Throwable cause) {
    super(msg, cause);
  }

  public SerializationException(final Throwable cause) {
    super(cause.getClass().getName() + ": " + cause.getMessage(), cause);
  }
}
