////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;
import java.security.GeneralSecurityException;

/**
 * 此错误表示对消息签名验证时失败。
 *
 * @author 胡海星
 */
public class VerifySignatureException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -1945404113824975221L;

  /**
   * 构造一个新的签名验证异常。
   */
  public VerifySignatureException() {}

  /**
   * 构造一个带有指定消息的签名验证异常。
   *
   * @param message
   *     异常消息。
   */
  public VerifySignatureException(final String message) {
    super(message);
  }

  /**
   * 构造一个带有指定原因的签名验证异常。
   *
   * @param e
   *     异常原因。
   */
  public VerifySignatureException(final Throwable e) {
    super(e.getMessage(), e);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "VERIFY_SIGNATURE_FAILED", this);
  }
}