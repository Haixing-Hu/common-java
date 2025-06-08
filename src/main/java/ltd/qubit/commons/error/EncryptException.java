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
 * 表示加密时发生错误。
 *
 * @author 胡海星
 */
public class EncryptException extends GeneralSecurityException implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 8624897523255705556L;

  private final String reason;

  /**
   * 构造一个新的加密异常。
   */
  public EncryptException() {
    reason = null;
  }

  /**
   * 构造一个带有指定消息的加密异常。
   *
   * @param message
   *     异常消息。
   */
  public EncryptException(final String message) {
    super(message);
    reason = message;
  }

  /**
   * 构造一个带有指定原因的加密异常。
   *
   * @param e
   *     异常原因。
   */
  public EncryptException(final Throwable e) {
    super(e.getMessage(), e);
    reason = e.getMessage();
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "ENCRYPT_MESSAGE_FAILED",
        new KeyValuePair("reason", (reason == null ? this.getMessage() : reason)));
  }

  /**
   * 获取加密失败的原因。
   *
   * @return
   *     加密失败的原因。
   */
  public String getReason() {
    return (reason == null ? this.getMessage() : reason);
  }
}