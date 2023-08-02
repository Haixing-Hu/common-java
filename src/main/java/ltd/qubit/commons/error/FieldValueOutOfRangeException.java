////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate that a field value is out of its allowed range.
 *
 * @author Haixing Hu
 */
public class FieldValueOutOfRangeException extends BusinessLogicException {

  private static final long serialVersionUID = -4510924126536171314L;

  private final String field;

  public FieldValueOutOfRangeException(final String property) {
    super(ErrorCode.FIELD_OUT_OF_RANGE,
        new KeyValuePair("field", getFieldName(property)));
    this.field = getFieldName(property);
  }

  public <T, P> FieldValueOutOfRangeException(final Class<T> type,
      final GetterMethod<T, P> getter) {
    this(FieldUtils.getFieldName(type, getter));
  }

  public String getField() {
    return field;
  }
}
