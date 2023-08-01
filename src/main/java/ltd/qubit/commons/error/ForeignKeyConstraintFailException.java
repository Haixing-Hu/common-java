////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.sql.DaoOperation;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate the failure of a foreign key constraint.
 *
 * @author Haixing Hu
 */
public class ForeignKeyConstraintFailException extends BusinessLogicException {

  private static final long serialVersionUID = - 4781962031198705900L;

  private final DaoOperation operation;
  private final String field;
  private final String referenceEntity;
  private final String referenceField;

  public ForeignKeyConstraintFailException(final DaoOperation operation,
      final String property, final String referenceEntity,
      final String referenceField) {
    super(getErrorCodeImpl(operation),
          getErrorParamsImpl(operation, property, referenceEntity, referenceField));
    this.operation = operation;
    this.field = getFieldName(property);
    this.referenceEntity = getEntityName(referenceEntity);
    this.referenceField = getFieldName(referenceField);
  }

  private static ErrorCode getErrorCodeImpl(final DaoOperation operation) {
    switch (operation) {
      case ADD_OR_UPDATE:
        return ErrorCode.REFER_TO_NON_EXIST_FOREIGN_KEY;
      case DELETE:
      default:
        return ErrorCode.DELETE_REFERENCED_FOREIGN_KEY;
    }
  }

  private static KeyValuePair[] getErrorParamsImpl(final DaoOperation operation,
      final String field, final String referenceEntity,
      final String referenceField) {
    switch (operation) {
      case ADD_OR_UPDATE:
        return new KeyValuePair[]{
            new KeyValuePair("field", field.toLowerCase()),
            new KeyValuePair("reference_entity", referenceEntity.toLowerCase()),
            new KeyValuePair("reference_field", referenceField.toLowerCase())
        };
      case DELETE:
      default:
        return new KeyValuePair[]{
            new KeyValuePair("reference_field", referenceField.toLowerCase())
        };
    }
  }

  //  public ForeignKeyConstraintFailException(final DaoOperation operation,
  //      final String field, final String referenceEntity,
  //      final String referenceField, final Throwable cause) {
  //    super("Cannot perform " + operation + " operation. The foreign key '"
  //        + field + "' referenced from '" + referenceEntity + "." + referenceField
  //        + "' failed.");
  //    this.operation = operation;
  //    this.field = field;
  //    this.referenceEntity = referenceEntity;
  //    this.referenceField = referenceField;
  //  }

  public DaoOperation getOperation() {
    return operation;
  }

  public String getField() {
    return field;
  }

  public String getReferenceEntity() {
    return referenceEntity;
  }

  public String getReferenceField() {
    return referenceField;
  }
}
