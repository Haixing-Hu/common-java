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

import javax.annotation.Nullable;

/**
 * An exception thrown to indicate the permission of the operation was denied.
 *
 * @author Haixing Hu
 */
public class OperationForbiddenException extends BusinessLogicException {

  private static final long serialVersionUID = -5998273591305882106L;

  private final String operation;
  private final String entity;

  public OperationForbiddenException(final String operation,
      @Nullable final Class<?> entityType) {
    this(getOperationName(operation), getEntityName(entityType));
  }

  private OperationForbiddenException(final String operation,
          @Nullable final String entity) {
    super(ErrorType.AUTHORIZATION_ERROR, ErrorCode.OPERATION_FORBIDDEN,
        new KeyValuePair("operation", operation),
        new KeyValuePair("entity", entity));
    this.operation = operation;
    this.entity = entity;
  }

  public String getOperation() {
    return operation;
  }

  public final String getEntity() {
    return entity;
  }
}
