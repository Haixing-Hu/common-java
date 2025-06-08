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

  /**
   * 构造一个新的解密异常。
   */
  public DecryptException() {
    reason = null;
  }

  /**
   * 构造一个带有指定消息的解密异常。
   *
   * @param message
   *     异常消息。
   */
  public DecryptException(final String message) {
    super(message);
    reason = message;
  }

  /**
   * 构造一个带有指定原因的解密异常。
   *
   * @param e
   *     异常原因。
   */
  public DecryptException(final Throwable e) {
    super(e.getMessage(), e);
    reason = e.getMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "DECRYPT_MESSAGE_FAILED",
        new KeyValuePair("reason", (reason == null ? this.getMessage() : reason)));
  }

  /**
   * 获取解密失败的原因。
   *
   * @return
   *     解密失败的原因。
   */
  public String getReason() {
    return (reason == null ? this.getMessage() : reason);
  }
}