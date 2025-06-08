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
 * An InvalidFormatException is thrown when the format is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidFormatException extends SerializationException {

  private static final long serialVersionUID = 5893399775330552816L;

  public InvalidFormatException() {
    super("Invalid format. ");
  }

  public InvalidFormatException(final Throwable cause) {
    super(cause);
  }

  public InvalidFormatException(final String message) {
    super(message);
  }
}