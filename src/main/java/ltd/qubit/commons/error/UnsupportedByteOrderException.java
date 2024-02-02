////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.nio.ByteOrder;

import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * Thrown to indicate the specified byte order is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedByteOrderException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = - 4996775196424495417L;

  public ByteOrder byteOrder;

  public UnsupportedByteOrderException(final ByteOrder byteOrder) {
    super("The byte order " + byteOrder + " is not supported.");
    this.byteOrder = byteOrder;
  }

  public ByteOrder getByteOrder() {
    return byteOrder;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "UNSUPPORTED_BYTE_ORDER",
        KeyValuePairList.of("byteOrder", byteOrder));
  }
}
