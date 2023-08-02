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
 * Thrown to indicate that a field value does not exist in the database.
 *
 * @author Haixing Hu
 */
public class DataAlreadyExistException extends BusinessLogicException {

  private static final long serialVersionUID = -7946725149423667129L;

  private final String entity;
  private final String key;
  private final Object value;

  public DataAlreadyExistException(final Class<?> entityType, final String key,
      final Object value, final KeyValuePair... params) {
    this(getEntityName(entityType), getFieldName(key), value, params);
  }

  private DataAlreadyExistException(final String entity, final String key,
      final Object value, final KeyValuePair[] params) {
    super(ErrorCode.ALREADY_EXIST, buildParams(entity, key, value, params));
    this.entity = entity;
    this.key = key;
    this.value = value;
  }

  private static KeyValuePair[] buildParams(final String entity,
      final String key, final Object value, final KeyValuePair[] params) {
    if (params == null || params.length == 0) {
      return new KeyValuePair[]{
          new KeyValuePair("entity", entity),
          new KeyValuePair("key", key),
          new KeyValuePair("value", value),
      };
    } else {
      final KeyValuePair[] result = new KeyValuePair[3 + params.length];
      result[0] = new KeyValuePair("entity", entity.toLowerCase());
      result[1] = new KeyValuePair("key", key);
      result[2] = new KeyValuePair("value", value);
      System.arraycopy(params, 0, result, 2, params.length);
      return result;
    }
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
