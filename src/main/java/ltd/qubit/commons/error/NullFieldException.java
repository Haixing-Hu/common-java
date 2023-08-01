////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate a field is null.
 *
 * @author Haixing Hu
 */
public class NullFieldException extends BusinessLogicException {

  private static final long serialVersionUID = 7810826919743040553L;

  private final String field;

  public NullFieldException(final String property) {
    super(ErrorCode.EMPTY_FIELD, new KeyValuePair("field", getFieldName(property)));
    this.field = getFieldName(property);
  }

  public <T, P> NullFieldException(final Class<T> type, final GetterMethod<T, P> getter) {
    this(FieldUtils.getFieldName(type, getter));
  }

  public String getField() {
    return field;
  }

}
