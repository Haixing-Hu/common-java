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
 * Thrown to indicate an entity was marked as locked.
 *
 * @author Haixing Hu
 */
public class EntityLockedException extends BusinessLogicException {

  private static final long serialVersionUID = -3093538447692567629L;

  private final String entity;
  private final String key;
  private final Object value;

  public EntityLockedException(final Class<?> entityType,
      final String key, final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  public EntityLockedException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.LOCKED, new KeyValuePair("entity", entity),
        new KeyValuePair("key", getFieldName(key)),
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
