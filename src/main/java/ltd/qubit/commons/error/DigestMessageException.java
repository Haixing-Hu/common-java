////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 此错误表示对消息计算数字摘要时失败。
 *
 * @author 胡海星
 */
public class DigestMessageException extends ServerInternalException {

  private static final long serialVersionUID = -8092428489830527184L;

  public DigestMessageException() {
    super(ErrorCode.DIGEST_MESSAGE_FAILED);
  }

  public DigestMessageException(final String message) {
    super(ErrorCode.DIGEST_MESSAGE_FAILED, new KeyValuePair("message", message));
  }

  public DigestMessageException(final Throwable e) {
    super(ErrorCode.DIGEST_MESSAGE_FAILED, e);
  }
}
