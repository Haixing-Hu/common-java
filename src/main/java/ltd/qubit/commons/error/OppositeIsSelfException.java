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
 * The exception thrown to indicate that the opposite of an entity is the
 * entity itself.
 *
 * @author Haixing Hu
 */
public class OppositeIsSelfException extends BusinessLogicException {

  private static final long serialVersionUID = -3020192983459599035L;

  private final String entity;
  private final String key;
  private final Object value;

  public OppositeIsSelfException(final Class<?> entityType, final String key,
      final Object value) {
    this(getEntityName(entityType), getFieldName(key), value);
  }

  private OppositeIsSelfException(final String entity, final String key,
      final Object value) {
    super(ErrorCode.OPPOSITE_IS_SELF,
        new KeyValuePair("entity", entity),
        new KeyValuePair("key", key),
        new KeyValuePair("value", value));
    this.entity = entity;
    this.key = key;
    this.value = value;
  }

  public final String getEntity() {
    return entity;
  }

  public String getKey() {
    return key;
  }

  public Object getValue() {
    return value;
  }
}
