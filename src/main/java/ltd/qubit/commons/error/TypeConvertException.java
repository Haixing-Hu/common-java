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
 * 在类型转换期间发生错误时抛出。
 *
 * @author 胡海星
 */
public class TypeConvertException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = 7806278638359973347L;

  private final String sourceTypeName;
  private final String destinationTypeName;

  /**
   * 使用指定的源类型名称和目标类型名称构造一个新的
   * {@link TypeConvertException}。
   *
   * @param sourceTypeName
   *     源类型的名称。
   * @param destinationTypeName
   *     目标类型的名称。
   */
  public TypeConvertException(final String sourceTypeName,
      final String destinationTypeName) {
    super(buildMessage(sourceTypeName, destinationTypeName));
    this.sourceTypeName = sourceTypeName;
    this.destinationTypeName = destinationTypeName;
  }

  /**
   * 使用指定的源类型、目标类型和消息构造一个新的
   * {@link TypeConvertException}。
   *
   * @param sourceType
   *     源类型。
   * @param destinationType
   *     目标类型。
   * @param message
   *     详细消息。
   */
  public TypeConvertException(final Type sourceType,
      final Type destinationType, final String message) {
    super(buildMessage(sourceType.toString(), destinationType.toString()) + " : " + message);
    this.sourceTypeName = sourceType.toString();
    this.destinationTypeName = destinationType.toString();
  }

  /**
   * 使用指定的源类型和目标类型构造一个新的 {@link TypeConvertException}。
   *
   * @param sourceType
   *     源类型。
   * @param destinationType
   *     目标类型。
   */
  public TypeConvertException(final Type sourceType,
      final Type destinationType) {
    super(buildMessage(sourceType.toString(), destinationType.toString()));
    this.sourceTypeName = sourceType.toString();
    this.destinationTypeName = destinationType.toString();
  }

  /**
   * 使用指定的源类和目标类构造一个新的 {@link TypeConvertException}。
   *
   * @param sourceType
   *     源类。
   * @param destinationType
   *     目标类。
   */
  public TypeConvertException(final Class<?> sourceType,
      final Class<?> destinationType) {
    super(buildMessage(sourceType.getName(), destinationType.getName()));
    this.sourceTypeName = sourceType.getName();
    this.destinationTypeName = destinationType.getName();
  }

  /**
   * 使用指定的源类、目标类和消息构造一个新的
   * {@link TypeConvertException}。
   *
   * @param sourceType
   *     源类。
   * @param destinationType
   *     目标类。
   * @param message
   *     详细消息。
   */
  public TypeConvertException(final Class<?> sourceType,
      final Class<?> destinationType, final String message) {
    super(buildMessage(sourceType.getName(),
        destinationType.getName()) + " : " + message);
    this.sourceTypeName = sourceType.getName();
    this.destinationTypeName = destinationType.getName();
  }

  private static String buildMessage(final String sourceTypeName,
      final String destinationTypeName) {
    return "Can not convert from type " + sourceTypeName
        + " to type " + destinationTypeName;
  }

  /**
   * 获取源类型的名称。
   *
   * @return 源类型的名称。
   */
  public String getSourceTypeName() {
    return sourceTypeName;
  }

  /**
   * 获取目标类型的名称。
   *
   * @return 目标类型的名称。
   */
  public String getDestinationTypeName() {
    return destinationTypeName;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "TYPE_CONVERT_FAILED",
        KeyValuePairList.of("source", sourceTypeName,
            "destination", destinationTypeName));
  }
}