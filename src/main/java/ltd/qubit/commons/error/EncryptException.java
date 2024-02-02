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

public class EncryptException extends GeneralSecurityException implements ErrorInfoConvertable {

  private static final long serialVersionUID = 8624897523255705556L;

  public EncryptException() {}

  public EncryptException(final String message) {
    super(message);
  }

  public EncryptException(final Throwable e) {
    super(e.getMessage(), e);
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "ENCRYPT_MESSAGE_FAILED", this);
  }
}
