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
 * 此错误表示对消息计算数字摘要时失败。
 *
 * @author 胡海星
 */
public class DigestMessageException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -8092428489830527184L;

  private final String reason;

  public DigestMessageException() {
    reason = null;
  }

  public DigestMessageException(final String message) {
    super(message);
    reason = message;
  }

  public DigestMessageException(final Throwable e) {
    super(e.getMessage(), e);
    reason = e.getMessage();
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "DIGEST_MESSAGE_FAILED",
        new KeyValuePair("reason", (reason == null ? this.getMessage() : reason)));
  }

  public String getReason() {
    return (reason == null ? this.getMessage() : reason);
  }
}
