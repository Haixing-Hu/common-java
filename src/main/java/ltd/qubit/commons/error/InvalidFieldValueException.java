////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * The exception thrown to indicate that the value of a field is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidFieldValueException extends BusinessLogicException {

  private static final long serialVersionUID = -9185610086465952665L;

  private final String field;
  private final Object value;

  public InvalidFieldValueException(final String property, final Object value) {
    super(ErrorCode.INVALID_FIELD_VALUE,
        new KeyValuePair("field", getFieldName(property)),
        new KeyValuePair("value", value));
    this.field = getFieldName(property);
    this.value = value;
  }

  /**
   * Gets the field.
   *
   * @return the field.
   */
  public String getField() {
    return field;
  }

  /**
   * Gets the value.
   *
   * @return the value.
   */
  public Object getValue() {
    return value;
  }

}
