////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate that a field value is too long.
 *
 * @author Haixing Hu
 */
public class FieldTooLongException extends BusinessLogicException {

  private static final long serialVersionUID = - 3651542010755649520L;

  private final String field;

  public FieldTooLongException(final String property) {
    super(ErrorCode.FIELD_TOO_LONG, new KeyValuePair("field", getFieldName(property)));
    this.field = getFieldName(property);
  }

  public String getField() {
    return field;
  }
}
