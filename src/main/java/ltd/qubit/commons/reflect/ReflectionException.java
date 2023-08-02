////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

/**
 * Thrown to indicate an exception occurs during a reflection operation.
 *
 * @author Haixing Hu
 */
public class ReflectionException extends RuntimeException {

  private static final long serialVersionUID = -4383352207393863063L;

  private static final String DEFAULT_MESSAGE =
      "An exception occurs during the reflection operation: ";

  public ReflectionException() {
    super(DEFAULT_MESSAGE);
  }

  public ReflectionException(final String message) {
    super(message);
  }

  public ReflectionException(final Throwable cause) {
    super(DEFAULT_MESSAGE + cause.getMessage(), cause);
  }

  public ReflectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
