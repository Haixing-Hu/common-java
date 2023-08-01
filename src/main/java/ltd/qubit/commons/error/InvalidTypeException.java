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
 * 表示实体类型错误。
 *
 * @author Haixing Hu
 */
public class InvalidTypeException extends BusinessLogicException {

  private static final long serialVersionUID = -123788747327732451L;

  private final String entity;
  private final String expected;
  private final String actual;

  public InvalidTypeException(final Class<?> entityType,
      final Enum<?> expectedType, final Enum<?> actualType) {
    this(getEntityName(entityType), expectedType.name(), actualType.name());
  }

  private InvalidTypeException(final String entity, final String expected,
      final String actual) {
    super(ErrorCode.INVALID_TYPE,
        new KeyValuePair("entity", entity),
        new KeyValuePair("expected_type", expected),
        new KeyValuePair("actual_type", actual));
    this.entity = entity;
    this.expected = expected;
    this.actual = actual;
  }

  public final String getEntity() {
    return entity;
  }

  public final String getExpected() {
    return expected;
  }

  public final String getActual() {
    return actual;
  }
}
