////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * Thrown when a type mismatch error occurs.
 *
 * @author Haixing Hu
 */
public class TypeMismatchException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = 779519202297853145L;

  private final String expectedTypeName;
  private final String actualTypeName;

  public TypeMismatchException(final String expectedTypeName,
      final String actualTypeName) {
    super(buildMessage(expectedTypeName, actualTypeName));
    this.expectedTypeName = expectedTypeName;
    this.actualTypeName = actualTypeName;
  }

  public TypeMismatchException(final String expectedTypeName,
      final String actualTypeName, final String message) {
    super(buildMessage(expectedTypeName, actualTypeName) + message);
    this.expectedTypeName = expectedTypeName;
    this.actualTypeName = actualTypeName;
  }

  public TypeMismatchException(final Class<?> expectedType,
      final Class<?> actualType) {
    super(buildMessage(expectedType.getName(), actualType.getName()));
    this.expectedTypeName = expectedType.getName();
    this.actualTypeName = actualType.getName();
  }

  public TypeMismatchException(final Class<?> expectedType,
      final Class<?> actualType, final String message) {
    super(buildMessage(expectedType.getName(), actualType.getName()) + message);
    this.expectedTypeName = expectedType.getName();
    this.actualTypeName = actualType.getName();
  }

  public TypeMismatchException(final Type expectedType, final Type actualType) {
    super(buildMessage(expectedType.name(), actualType.name()));
    this.expectedTypeName = expectedType.name();
    this.actualTypeName = actualType.name();
  }

  public TypeMismatchException(final Type expectedType,
      final Type actualType, final String message) {
    super(buildMessage(expectedType.name(), actualType.name()) + message);
    this.expectedTypeName = expectedType.name();
    this.actualTypeName = actualType.name();
  }

  public String getExpectedTypeName() {
    return expectedTypeName;
  }

  public String getActualTypeName() {
    return actualTypeName;
  }

  private static String buildMessage(final String expectedTypeName,
      final String actualTypeName) {
    return "Type mismatch: the expected type is " + expectedTypeName
        + " but the actual type is " + actualTypeName + ". ";
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "TYPE_MISMATCH",
        KeyValuePairList.of("expectedType", expectedTypeName,
            "actualType", actualTypeName));
  }
}
