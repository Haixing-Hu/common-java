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
 * Thrown to indicate a key is duplicated.
 *
 * @author Haixing Hu
 */
public class DuplicateKeyException extends BusinessLogicException {

  private static final long serialVersionUID = 8047771735232234641L;

  private final String key;
  private final String value;

  public DuplicateKeyException(final String key, final String value) {
    super(ErrorCode.DUPLICATE_KEY,
        new KeyValuePair("field", getFieldName(key)),
        new KeyValuePair("value", value));
    this.key = key.toLowerCase();
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}
