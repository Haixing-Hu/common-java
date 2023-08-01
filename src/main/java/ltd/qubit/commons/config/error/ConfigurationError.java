////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

/**
 * Thrown when a problem occurs while loading a default configuration resource
 * to a CommonsConfig object.
 *
 * @author Haixing Hu
 */
public class ConfigurationError extends Error {

  private static final long serialVersionUID = -8146105276111522899L;

  private static final String DEFAULT_MESSAGE =
      "There is an error in the configuration. ";

  public ConfigurationError() {
    super(DEFAULT_MESSAGE);
  }

  public ConfigurationError(final Throwable t) {
    super(DEFAULT_MESSAGE + t.toString(), t);
  }

  public ConfigurationError(final String msg) {
    super(msg);
  }

  public ConfigurationError(final String msg, final Throwable t) {
    super(msg, t);
  }
}
