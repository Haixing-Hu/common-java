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
 * Thrown when an error occurs during the type conversion.
 *
 * @author Haixing Hu
 */
public class TypeConvertException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = 7806278638359973347L;

  private final String sourceTypeName;
  private final String destinationTypeName;

  public TypeConvertException(final String sourceTypeName,
      final String destinationTypeName) {
    super(buildMessage(sourceTypeName, destinationTypeName));
    this.sourceTypeName = sourceTypeName;
    this.destinationTypeName = destinationTypeName;
  }

  public TypeConvertException(final String sourceType,
      final String destinationType, final String message) {
    super(buildMessage(sourceType, destinationType) + " : " + message);
    this.sourceTypeName = sourceType;
    this.destinationTypeName = destinationType;
  }

  public TypeConvertException(final Type sourceType,
      final Type destinationType) {
    super(buildMessage(sourceType.toString(), destinationType.toString()));
    this.sourceTypeName = sourceType.toString();
    this.destinationTypeName = destinationType.toString();
  }

  public TypeConvertException(final Type sourceType,
      final Type destinationType, final String message) {
    super(buildMessage(sourceType.toString(),
        destinationType.toString()) + " : " + message);
    this.sourceTypeName = sourceType.toString();
    this.destinationTypeName = destinationType.toString();
  }

  public TypeConvertException(final Class<?> sourceType,
      final Class<?> destinationType) {
    super(buildMessage(sourceType.getName(), destinationType.getName()));
    this.sourceTypeName = sourceType.getName();
    this.destinationTypeName = destinationType.getName();
  }

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

  public String getSourceTypeName() {
    return sourceTypeName;
  }

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
