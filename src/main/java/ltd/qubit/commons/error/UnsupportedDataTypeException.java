////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * Thrown when a type is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedDataTypeException extends RuntimeException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 67589732551790772L;

  private String typeName;

  public UnsupportedDataTypeException(final String typeName) {
    this.typeName = typeName;
  }

  public UnsupportedDataTypeException(final Type type) {
    this.typeName = type.name();
  }

  public UnsupportedDataTypeException(final Class<?> type) {
    this.typeName = ClassUtils.getFullCanonicalName(type);
  }

  public UnsupportedDataTypeException(final String typeName, final String message) {
    super(message);
    this.typeName = typeName;
  }

  public UnsupportedDataTypeException(final Type type, final String message) {
    super(message);
    this.typeName = type.name();
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(final String typeName) {
    this.typeName = typeName;
  }

  @Override
  public String getMessage() {
    String message = super.getMessage();
    if ((message == null) || (message.length() == 0)) {
      message = "The type is not supported: ";
    }
    if (typeName != null) {
      message += " type = " + typeName;
    }
    return message;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "UNSUPPORTED_DATA_TYPE",
        KeyValuePairList.of("type", typeName));
  }
}
