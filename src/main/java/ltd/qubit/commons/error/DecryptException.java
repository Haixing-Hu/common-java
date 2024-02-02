////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.security.GeneralSecurityException;

public class DecryptException extends GeneralSecurityException implements ErrorInfoConvertable {

  private static final long serialVersionUID = 3108411653208108149L;

  public DecryptException() {}

  public DecryptException(final String message) {
    super(message);
  }

  public DecryptException(final Throwable e) {
    super(e.getMessage(), e);
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "DECRYPT_MESSAGE_FAILED", this);
  }
}
