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
 * 表示解密时发生错误。
 *
 * @author 胡海星
 */
public class DecryptException extends GeneralSecurityException implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 3108411653208108149L;

  private final String reason;

  public DecryptException() {
    reason = null;
  }

  public DecryptException(final String message) {
    super(message);
    reason = message;
  }

  public DecryptException(final Throwable e) {
    super(e.getMessage(), e);
    reason = e.getMessage();
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "DECRYPT_MESSAGE_FAILED",
        new KeyValuePair("reason", (reason == null ? this.getMessage() : reason)));
  }

  public String getReason() {
    return (reason == null ? this.getMessage() : reason);
  }
}
