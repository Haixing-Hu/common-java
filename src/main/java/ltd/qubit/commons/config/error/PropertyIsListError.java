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
 * This error indicates that the property with a specified name contains a list
 * of values.
 *
 * @author Haixing Hu
 */
public class PropertyIsListError extends ConfigurationError {

  private static final long serialVersionUID = 1286137143396514506L;

  private final String propertyName;

  public PropertyIsListError(final String propertyName) {
    super("The specified property contains a list of values: " + propertyName);
    this.propertyName = propertyName;
  }

  public String getPropertyName() {
    return propertyName;
  }

}
