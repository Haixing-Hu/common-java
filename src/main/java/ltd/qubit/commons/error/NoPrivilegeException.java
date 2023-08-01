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
 * The exception to thrown if the user has no privilege to perform the
 * operation.
 *
 * @author Haixing Hu
 */
public class NoPrivilegeException extends BusinessLogicException {

  private static final long serialVersionUID = 2804481438240205724L;

  private final String operation;
  private final String entity;

  public NoPrivilegeException(final String operation, @Nullable final Class<?> entityType) {
    this(getOperationName(operation), getEntityName(entityType));
  }

  private NoPrivilegeException(final String operation, @Nullable final String entity) {
    super(ErrorType.AUTHORIZATION_ERROR, ErrorCode.NO_PRIVILEGE,
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
