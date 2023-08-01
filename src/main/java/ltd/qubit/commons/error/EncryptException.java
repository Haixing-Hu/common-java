////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

public class EncryptException extends ServerInternalException {

  private static final long serialVersionUID = 8624897523255705556L;

  public EncryptException() {
    super(ErrorCode.ENCRYPT_MESSAGE_FAILED);
  }

  public EncryptException(final String message) {
    super(ErrorCode.ENCRYPT_MESSAGE_FAILED,
        new KeyValuePair("message", message));
  }

  public EncryptException(final Throwable e) {
    super(ErrorCode.ENCRYPT_MESSAGE_FAILED, e);
  }
}
