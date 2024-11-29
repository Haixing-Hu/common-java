////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.impl;

import java.io.IOException;

/**
 * Thrown when a bean creation error occurs.
 *
 * @author Haixing Hu
 */
public class BeanCreationException extends IOException {

  public BeanCreationException() {
  }

  public BeanCreationException(final String message) {
    super(message);
  }

  public BeanCreationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public BeanCreationException(final Throwable cause) {
    super(cause);
  }
}