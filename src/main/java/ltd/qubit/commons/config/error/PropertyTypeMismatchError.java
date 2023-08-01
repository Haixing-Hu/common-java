////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

import ltd.qubit.commons.lang.Type;

/**
 * This error indicates that the property with a specified name has a mismatch
 * type.
 *
 * @author Haixing Hu
 */
public class PropertyTypeMismatchError extends ConfigurationError {

  private static final long serialVersionUID = 7007685636732043825L;

  private final String propertyName;
  private final Type expectedType;
  private final Type actualType;

  public PropertyTypeMismatchError(final String propertyName,
      final Type expectedType, final Type actualType) {
    super("The type of property '" + propertyName
        + "' mismatch: expected type is " + expectedType
        + " but the actual type is " + actualType);
    this.propertyName = propertyName;
    this.expectedType = expectedType;
    this.actualType = actualType;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public Type getExpectedType() {
    return expectedType;
  }

  public Type getActualType() {
    return actualType;
  }

}
