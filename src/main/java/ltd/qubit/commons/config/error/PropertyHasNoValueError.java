////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

/**
 * This error indicates that the property with a specified name does not have
 * any value.
 *
 * @author Haixing Hu
 */
public class PropertyHasNoValueError extends ConfigurationError {

  private static final long serialVersionUID = - 2617774805970541714L;

  private final String propertyName;

  public PropertyHasNoValueError(final String propertyName) {
    super("The specified property has no value: " + propertyName);
    this.propertyName = propertyName;
  }

  public String getPropertyName() {
    return propertyName;
  }
}
