////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

/**
 * Thrown to indicate the version signature of a file is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidSignatureException extends InvalidFormatException {

  private static final long serialVersionUID = 3066517065925815105L;

  public InvalidSignatureException() {
    super("The version signature of the file is invalid.");
  }

  public InvalidSignatureException(final String message) {
    super(message);
  }
}
