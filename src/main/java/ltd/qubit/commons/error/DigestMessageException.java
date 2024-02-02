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
 * 此错误表示对消息计算数字摘要时失败。
 *
 * @author 胡海星
 */
public class DigestMessageException extends GeneralSecurityException implements ErrorInfoConvertable {

  private static final long serialVersionUID = -8092428489830527184L;

  public DigestMessageException() {}

  public DigestMessageException(final String message) {
    super(message);
  }

  public DigestMessageException(final Throwable e) {
    super(e.getMessage(), e);
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "DIGEST_MESSAGE_FAILED", this);
  }
}
