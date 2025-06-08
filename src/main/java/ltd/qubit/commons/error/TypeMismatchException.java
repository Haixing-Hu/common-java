////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 发生类型不匹配错误时抛出。
 *
 * @author 胡海星
 */
public class TypeMismatchException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = 779519202297853145L;

  private final String expectedTypeName;
  private final String actualTypeName;

  /**
   * 使用指定的期望类型名称和实际类型名称构造一个新的
   * {@link TypeMismatchException}。
   *
   * @param expectedTypeName
   *     期望类型的名称。
   * @param actualTypeName
   *     实际类型的名称。
   */
  public TypeMismatchException(final String expectedTypeName,
      final String actualTypeName) {
    super(buildMessage(expectedTypeName, actualTypeName));
    this.expectedTypeName = expectedTypeName;
    this.actualTypeName = actualTypeName;
  }

  /**
   * 使用指定的期望类型名称、实际类型名称和消息构造一个新的
   * {@link TypeMismatchException}。
   *
   * @param expectedTypeName
   *     期望类型的名称。
   * @param actualTypeName
   *     实际类型的名称。
   * @param message
   *     详细消息。
   */
  public TypeMismatchException(final String expectedTypeName,
      final String actualTypeName, final String message) {
    super(buildMessage(expectedTypeName, actualTypeName) + message);
    this.expectedTypeName = expectedTypeName;
    this.actualTypeName = actualTypeName;
  }

  /**
   * 使用指定的期望类型和实际类型构造一个新的
   * {@link TypeMismatchException}。
   *
   * @param expectedType
   *     期望类型。
   * @param actualType
   *     实际类型。
   */
  public TypeMismatchException(final Class<?> expectedType,
      final Class<?> actualType) {
    super(buildMessage(expectedType.getName(), actualType.getName()));
    this.expectedTypeName = expectedType.getName();
    this.actualTypeName = actualType.getName();
  }

  /**
   * 使用指定的期望类型、实际类型和消息构造一个新的
   * {@link TypeMismatchException}。
   *
   * @param expectedType
   *     期望类型。
   * @param actualType
   *     实际类型。
   * @param message
   *     详细消息。
   */
  public TypeMismatchException(final Class<?> expectedType,
      final Class<?> actualType, final String message) {
    super(buildMessage(expectedType.getName(), actualType.getName()) + message);
    this.expectedTypeName = expectedType.getName();
    this.actualTypeName = actualType.getName();
  }

  /**
   * 使用指定的期望类型和实际类型构造一个新的
   * {@link TypeMismatchException}。
   *
   * @param expectedType
   *     期望类型。
   * @param actualType
   *     实际类型。
   */
  public TypeMismatchException(final Type expectedType, final Type actualType) {
    super(buildMessage(expectedType.name(), actualType.name()));
    this.expectedTypeName = expectedType.name();
    this.actualTypeName = actualType.name();
  }

  /**
   * 使用指定的期望类型、实际类型和消息构造一个新的
   * {@link TypeMismatchException}。
   *
   * @param expectedType
   *     期望类型。
   * @param actualType
   *     实际类型。
   * @param message
   *     详细消息。
   */
  public TypeMismatchException(final Type expectedType,
      final Type actualType, final String message) {
    super(buildMessage(expectedType.name(), actualType.name()) + message);
    this.expectedTypeName = expectedType.name();
    this.actualTypeName = actualType.name();
  }

  /**
   * 获取期望类型的名称。
   *
   * @return 期望类型的名称。
   */
  public String getExpectedTypeName() {
    return expectedTypeName;
  }

  /**
   * 获取实际类型的名称。
   *
   * @return 实际类型的名称。
   */
  public String getActualTypeName() {
    return actualTypeName;
  }

  private static String buildMessage(final String expectedTypeName,
      final String actualTypeName) {
    return "Type mismatch: the expected type is " + expectedTypeName
        + " but the actual type is " + actualTypeName + ". ";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "TYPE_MISMATCH",
        KeyValuePairList.of("expectedType", expectedTypeName,
            "actualType", actualTypeName));
  }
}