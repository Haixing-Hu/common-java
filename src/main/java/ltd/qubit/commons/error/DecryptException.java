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

public class DecryptException extends ServerInternalException {

  private static final long serialVersionUID = 3108411653208108149L;

  public DecryptException() {
    super(ErrorCode.DECRYPT_MESSAGE_FAILED);
  }

  public DecryptException(final String message) {
    super(ErrorCode.DECRYPT_MESSAGE_FAILED,
        new KeyValuePair("message", message));
  }

  public DecryptException(final Throwable e) {
    super(ErrorCode.DECRYPT_MESSAGE_FAILED, e);
  }
}
