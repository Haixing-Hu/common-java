////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * An exception thrown to indicate the specified class is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedClassException extends ServerInternalException {

  private static final long serialVersionUID = 3558672511597342482L;

  public UnsupportedClassException() {
    super(ErrorCode.UNSUPPORTED_CLASS);
  }

  public UnsupportedClassException(final Class<?> cls) {
    super(ErrorCode.UNSUPPORTED_CLASS, new KeyValuePair("class", cls.getName()));
  }

  public UnsupportedClassException(final String className) {
    super(ErrorCode.UNSUPPORTED_CLASS, new KeyValuePair("class", className));
  }
}
