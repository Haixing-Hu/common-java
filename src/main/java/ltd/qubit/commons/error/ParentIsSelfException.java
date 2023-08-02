////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * The exception thrown to indicate that the parent of an entity is the
 * entity itself.
 *
 * @author Haixing Hu
 */
public class ParentIsSelfException extends BusinessLogicException {

  private static final long serialVersionUID = -5431291289042906347L;

  private final String entity;
  private final String key;
  private final Object value;

  public ParentIsSelfException(final Class<?> entityType, final String key,
      final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  public ParentIsSelfException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.PARENT_IS_SELF,
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
