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

/**
 * 此错误表示对消息签名验证时失败。
 *
 * @author 胡海星
 */
public class VerifySignatureException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = -1945404113824975221L;

  public VerifySignatureException() {}

  public VerifySignatureException(final String message) {
    super(message);
  }

  public VerifySignatureException(final Throwable e) {
    super(e.getMessage(), e);
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "VERIFY_SIGNATURE_FAILED", this);
  }
}
