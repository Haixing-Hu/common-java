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
 * 表示实体状态错误。
 *
 * @author Haixing Hu
 */
public class InvalidStatusException extends BusinessLogicException {

  private static final long serialVersionUID = 7935755877026564378L;

  private final String entity;
  private final Class<?> statusClass;
  private final String status;

  public <S extends Enum<S>> InvalidStatusException(final Class<?> entityType,
      final S status) {
    this(getEntityName(entityType), status.getClass(), status.name());
  }

  private InvalidStatusException(final String entity, final Class<?> statusClass,
      final String status) {
    super(ErrorCode.INVALID_STATUS,
        new KeyValuePair("entity", entity),
        new KeyValuePair("status_class", statusClass.getName()),
        new KeyValuePair("status", status));
    this.entity = getEntityName(entity);
    this.statusClass = statusClass;
    this.status = getFieldName(status);
  }

  public final String getEntity() {
    return entity;
  }

  public Class<?> getStatusClass() {
    return statusClass;
  }

  public final String getStatus() {
    return status;
  }
}
