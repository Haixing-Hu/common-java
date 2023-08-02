////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

import ltd.qubit.commons.config.Configurable;

/**
 * Thrown to indicate that an {@link Configurable} is not configured.
 *
 * @author Haixing Hu
 */
public class NotConfiguredError extends ConfigurationError {

  private static final long serialVersionUID = - 6212497530366587501L;

  private static final String DEFAULT_MESSAGE =
      "The configurable object is not configured. ";

  public NotConfiguredError() {
    super(DEFAULT_MESSAGE);
  }

  public NotConfiguredError(final Throwable t) {
    super(DEFAULT_MESSAGE + t.toString(), t);
  }

  public NotConfiguredError(final String msg) {
    super(msg);
  }

  public NotConfiguredError(final String msg, final Throwable t) {
    super(msg, t);
  }
}
