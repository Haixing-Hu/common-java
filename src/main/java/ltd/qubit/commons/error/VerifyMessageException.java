////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * 此错误表示验证消息签名时失败。
 *
 * @author 胡海星
 */
public class VerifyMessageException extends ServerInternalException {

  private static final long serialVersionUID = 7874927577934389584L;

  public VerifyMessageException() {
    super(ErrorCode.VERIFY_SIGNATURE_FAILED);
  }

  public VerifyMessageException(final String message) {
    super(ErrorCode.VERIFY_SIGNATURE_FAILED, new KeyValuePair("message", message));
  }

  public VerifyMessageException(final Throwable e) {
    super(ErrorCode.VERIFY_SIGNATURE_FAILED, e);
  }
}
