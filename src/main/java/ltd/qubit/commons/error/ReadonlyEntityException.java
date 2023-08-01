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
 * Thrown to indicate the entity to be modified is readonly.
 */
public class ReadonlyEntityException extends BusinessLogicException {

  private static final long serialVersionUID = 5742840366996760375L;

  private final String entity;
  private final String key;
  private final Object value;

  public ReadonlyEntityException(final Class<?> entityType, final String key,
      final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  private ReadonlyEntityException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.READONLY,
        new KeyValuePair("entity", entity),
        new KeyValuePair("key", key),
        new KeyValuePair("value", value));
    this.entity = entity;
    this.key = key;
    this.value = value;
  }

  public String getEntity() {
    return entity;
  }

  public String getKey() {
    return key;
  }

  public Object getValue() {
    return value;
  }
}
