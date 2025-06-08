////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.nio.ByteOrder;

import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 抛出此异常以指示指定的字节顺序不受支持。
 *
 * @author 胡海星
 */
public class UnsupportedByteOrderException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = - 4996775196424495417L;

  public ByteOrder byteOrder;

  /**
   * 构造一个新的不支持字节顺序异常。
   *
   * @param byteOrder
   *     不支持的字节顺序。
   */
  public UnsupportedByteOrderException(final ByteOrder byteOrder) {
    super("The byte order " + byteOrder + " is not supported.");
    this.byteOrder = byteOrder;
  }

  /**
   * 获取不支持的字节顺序。
   *
   * @return
   *     不支持的字节顺序。
   */
  public ByteOrder getByteOrder() {
    return byteOrder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "UNSUPPORTED_BYTE_ORDER",
        KeyValuePairList.of("byteOrder", byteOrder));
  }
}