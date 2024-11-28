////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

/**
 * Thrown to indicate an invalid property value.
 *
 * @author Haixing Hu
 */
public class InvalidPropertyValueError extends ConfigurationError {

  private static final long serialVersionUID = 7500607643203773303L;

  public InvalidPropertyValueError(final String name, final Object value) {
    super("The property value of '" + name + "' is invalid: " + value);
  }

}
