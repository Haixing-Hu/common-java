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

/**
 * 此错误表示对消息签名时失败。
 *
 * @author 胡海星
 */
public class SignMessageException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1999689777863825980L;

  public SignMessageException() {}

  public SignMessageException(final String message) {
    super(message);
  }

  public SignMessageException(final Throwable e) {
    super(e.getMessage(), e);
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "SIGN_MESSAGE_FAILED", this);
  }
}
