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
 * Thrown to indicate an entity was marked as inactive.
 *
 * @author Haixing Hu
 */
public class EntityInactiveException extends BusinessLogicException {

  private static final long serialVersionUID = 170359584234170968L;

  private final String entity;
  private final String key;
  private final Object value;

  public EntityInactiveException(final Class<?> entityType,
      final String key, final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  private EntityInactiveException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.INACTIVE,
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

  public final String getKey() {
    return key;
  }

  public final Object getValue() {
    return value;
  }
}
