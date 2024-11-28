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
import java.security.GeneralSecurityException;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 表示加密时发生错误。
 *
 * @author 胡海星
 */
public class EncryptException extends GeneralSecurityException implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 8624897523255705556L;

  private final String reason;

  public EncryptException() {
    reason = null;
  }

  public EncryptException(final String message) {
    super(message);
    reason = message;
  }

  public EncryptException(final Throwable e) {
    super(e.getMessage(), e);
    reason = e.getMessage();
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "ENCRYPT_MESSAGE_FAILED",
        new KeyValuePair("reason", (reason == null ? this.getMessage() : reason)));
  }

  public String getReason() {
    return (reason == null ? this.getMessage() : reason);
  }
}
