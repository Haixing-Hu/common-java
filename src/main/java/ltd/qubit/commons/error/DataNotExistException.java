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
 * Thrown to indicate that a field value does not exist in the database.
 *
 * @author Haixing Hu
 */
public class DataNotExistException extends BusinessLogicException {

  private static final long serialVersionUID = -7946725149423667129L;

  private final String entity;
  private final String key;
  private final Object value;

  public DataNotExistException(final Class<?> entityType, final String key,
      final Object value) {
    this(entityType, key, value, new KeyValuePair[0]);
  }

  public DataNotExistException(final Class<?> entityType, final String key,
      final Object value, final KeyValuePair... params) {
    super(ErrorCode.NOT_EXIST, buildParams(entityType, key, value, params));
    this.entity = getEntityName(entityType);
    this.key = getFieldName(key);
    this.value = value;
  }

  private static KeyValuePair[] buildParams(final Class<?> entityType,
      final String key, final Object value, final KeyValuePair[] params) {
    if (params == null || params.length == 0) {
      return new KeyValuePair[]{
          new KeyValuePair("entity", getEntityName(entityType)),
          new KeyValuePair("key", getFieldName(key)),
          new KeyValuePair("value", value),
      };
    } else {
      final KeyValuePair[] result = new KeyValuePair[3 + params.length];
      result[0] = new KeyValuePair("entity", getEntityName(entityType));
      result[1] = new KeyValuePair("key", getFieldName(key));
      result[2] = new KeyValuePair("value", value);
      System.arraycopy(params, 0, result, 3, params.length);
      return result;
    }
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
