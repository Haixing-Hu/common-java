////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.nio.ByteOrder;

/**
 * Thrown to indicate the specified byte order is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedByteOrderException extends RuntimeException {

  private static final long serialVersionUID = - 4996775196424495417L;

  public ByteOrder byteOrder;

  public UnsupportedByteOrderException(final ByteOrder byteOrder) {
    super("The byte order " + byteOrder + " is not supported.");
    this.byteOrder = byteOrder;
  }

  public ByteOrder getByteOrder() {
    return byteOrder;
  }

}
