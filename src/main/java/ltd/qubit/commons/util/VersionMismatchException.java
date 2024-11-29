////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import ltd.qubit.commons.io.error.InvalidFormatException;

/**
 * Thrown to indicate a version mismatch error.
 *
 * @author Haixing Hu
 */
public class VersionMismatchException extends InvalidFormatException {

  private static final long serialVersionUID = 4296455318275520977L;

  private final Version expected;
  private final Version actual;

  public VersionMismatchException(final Version expected, final Version actual) {
    this.expected = expected;
    this.actual = actual;
  }

  public VersionMismatchException(final Version expected,
      final Version actual, final String message) {
    super(message);
    this.expected = expected;
    this.actual = actual;
  }

  public Version getExpected() {
    return expected;
  }

  public Version getActual() {
    return actual;
  }

  @Override
  public String getMessage() {
    final String message = super.getMessage();
    if (message != null) {
      return message;
    } else {
      return "Expect version " + expected.toString()
        + " but the actual version is " + actual.toString();
    }
  }
}
