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

import java.util.HashMap;
import java.util.Map;

/**
 * The exception thrown to indicate that the format of a field is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidFieldFormatException extends BusinessLogicException {

  private static final long serialVersionUID = 2423251503350099649L;

  private static final Map<String, ErrorCode> ERROR_CODE_MAP;

  static {
    ERROR_CODE_MAP = new HashMap<>();
    // ERROR_CODE_MAP.put("password", ErrorCode.INVALID_PASSWORD_FORMAT);
    // ERROR_CODE_MAP.put("username", ErrorCode.INVALID_USERNAME_FORMAT);
    ERROR_CODE_MAP.put("email", ErrorCode.INVALID_EMAIL_FORMAT);
    ERROR_CODE_MAP.put("mobile", ErrorCode.INVALID_PHONE_FORMAT);
    ERROR_CODE_MAP.put("phone", ErrorCode.INVALID_PHONE_FORMAT);
  }

  private final String field;
  private final Object value;

  public InvalidFieldFormatException(final String property, final Object value) {
    super(getErrorCode(property),
        new KeyValuePair("field", getFieldName(property)),
        new KeyValuePair("value", value));
    this.field = getFieldName(property);
    this.value = value;
  }

  public <T, P> InvalidFieldFormatException(final Class<T> type,
      final GetterMethod<T, P> getter, final P value) {
    this(FieldUtils.getFieldName(type, getter), value);
  }

  private static ErrorCode getErrorCode(final String property) {
    final ErrorCode code = ERROR_CODE_MAP.get(property);
    return (code != null ? code : ErrorCode.INVALID_FIELD_FORMAT);
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
