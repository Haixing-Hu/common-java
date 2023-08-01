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
 * The exception thrown to indicate that the characters of the value of a field
 * is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidFieldValueCharacterException extends BusinessLogicException {

  private static final long serialVersionUID = 8790807160943844485L;

  private final String field;
  private final String value;

  public InvalidFieldValueCharacterException(final String property, final String value) {
    super(ErrorType.REQUEST_ERROR, ErrorCode.INVALID_FIELD_VALUE_CHARACTER,
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
  public String getValue() {
    return value;
  }

}
