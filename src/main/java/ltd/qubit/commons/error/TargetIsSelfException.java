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
 * The exception thrown to indicate that the target of an operation is the
 * current user himself.
 *
 * @author Haixing Hu
 */
public class TargetIsSelfException extends BusinessLogicException {

  private static final long serialVersionUID = -5348011388689198924L;

  private final String operation;
  private final String entity;

  public TargetIsSelfException(final String operation, final Class<?> entityType) {
    this(getOperationName(operation), getEntityName(entityType));
  }

  private TargetIsSelfException(final String operation, final String entity) {
    super(ErrorCode.TARGET_IS_SELF,
        new KeyValuePair("operation", operation),
        new KeyValuePair("entity", entity));
    this.operation = operation;
    this.entity = entity;
  }

  public final String getOperation() {
    return operation;
  }

  public final String getEntity() {
    return entity;
  }
}
