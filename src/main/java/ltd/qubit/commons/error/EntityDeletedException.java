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
 * Thrown to indicate an entity was marked as deleted.
 *
 * @author Haixing Hu
 */
public class EntityDeletedException extends BusinessLogicException {

  private static final long serialVersionUID = - 4862483627407054307L;

  private final String entity;
  private final String key;
  private final Object value;

  public <T, P> EntityDeletedException(final Class<T> entityType,
      final GetterMethod<T, P> getter, final P value) {
    this(entityType, FieldUtils.getFieldName(entityType, getter), value);
  }

  public EntityDeletedException(final Class<?> entityType, final String key,
      final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  private EntityDeletedException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.DELETED,
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
