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
 * 此错误表示对消息计算数字摘要时失败。
 *
 * @author 胡海星
 */
public class DigestMessageException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -8092428489830527184L;

  private final String reason;

  /**
   * 构造一个新的消息摘要异常。
   */
  public DigestMessageException() {
    reason = null;
  }

  /**
   * 构造一个带有指定消息的消息摘要异常。
   *
   * @param message
   *     异常消息。
   */
  public DigestMessageException(final String message) {
    super(message);
    reason = message;
  }

  /**
   * 构造一个带有指定原因的消息摘要异常。
   *
   * @param e
   *     异常原因。
   */
  public DigestMessageException(final Throwable e) {
    super(e.getMessage(), e);
    reason = e.getMessage();
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "DIGEST_MESSAGE_FAILED",
        new KeyValuePair("reason", (reason == null ? this.getMessage() : reason)));
  }

  /**
   * 获取消息摘要失败的原因。
   *
   * @return
   *     消息摘要失败的原因。
   */
  public String getReason() {
    return (reason == null ? this.getMessage() : reason);
  }
}