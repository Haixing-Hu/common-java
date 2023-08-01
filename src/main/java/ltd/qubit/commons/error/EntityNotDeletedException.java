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
 * Thrown to indicate that the entity is not marked as deleted.
 *
 * @author Haixing Hu
 */
public class EntityNotDeletedException extends BusinessLogicException {

  private static final long serialVersionUID = -731841457686311032L;

  private final String entity;
  private final String key;
  private final Object value;

  public EntityNotDeletedException(final Class<?> entityType, final String key,
      final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  private EntityNotDeletedException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.NOT_DELETED,
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
