////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 当类型不受支持时抛出此异常。
 *
 * @author 胡海星
 */
public class UnsupportedDataTypeException extends RuntimeException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 67589732551790772L;

  private String typeName;

  /**
   * 构造一个新的不支持数据类型异常。
   *
   * @param typeName
   *     不支持的类型名称。
   */
  public UnsupportedDataTypeException(final String typeName) {
    this.typeName = typeName;
  }

  /**
   * 构造一个新的不支持数据类型异常。
   *
   * @param type
   *     不支持的类型。
   */
  public UnsupportedDataTypeException(final Type type) {
    this.typeName = type.name();
  }

  /**
   * 构造一个新的不支持数据类型异常。
   *
   * @param type
   *     不支持的类型。
   */
  public UnsupportedDataTypeException(final Class<?> type) {
    this.typeName = ClassUtils.getFullCanonicalName(type);
  }

  /**
   * 构造一个带有指定消息的不支持数据类型异常。
   *
   * @param typeName
   *     不支持的类型名称。
   * @param message
   *     异常消息。
   */
  public UnsupportedDataTypeException(final String typeName, final String message) {
    super(message);
    this.typeName = typeName;
  }

  /**
   * 构造一个带有指定消息的不支持数据类型异常。
   *
   * @param type
   *     不支持的类型。
   * @param message
   *     异常消息。
   */
  public UnsupportedDataTypeException(final Type type, final String message) {
    super(message);
    this.typeName = type.name();
  }

  /**
   * 获取不支持的类型名称。
   *
   * @return
   *     不支持的类型名称。
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * 设置不支持的类型名称。
   *
   * @param typeName
   *     不支持的类型名称。
   */
  public void setTypeName(final String typeName) {
    this.typeName = typeName;
  } 

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "UNSUPPORTED_DATA_TYPE",
        KeyValuePairList.of("type", typeName));
  }
}