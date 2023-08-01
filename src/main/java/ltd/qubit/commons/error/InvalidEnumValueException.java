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
 * Thrown to indicate that the value of an enumeration is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidEnumValueException extends BusinessLogicException {

  private static final long serialVersionUID = 7802717543992308170L;

  public InvalidEnumValueException(final Class<?> enumClass, final String value) {
    super(ErrorCode.INVALID_ENUM_VALUE,
        new KeyValuePair("entity", getEntityName(enumClass)),
        new KeyValuePair("value", value));
  }
}
