////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.impl;

import java.io.IOException;
import java.io.Serial;

/**
 * Thrown when a bean creation error occurs.
 *
 * @author Haixing Hu
 */
public class BeanCreationException extends IOException {

  @Serial
  private static final long serialVersionUID = -5948216445929482392L;

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