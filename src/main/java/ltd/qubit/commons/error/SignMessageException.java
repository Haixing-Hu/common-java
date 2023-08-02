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
 * 此错误表示对消息签名时失败。
 *
 * @author 胡海星
 */
public class SignMessageException extends ServerInternalException {

  private static final long serialVersionUID = 1999689777863825980L;

  public SignMessageException() {
    super(ErrorCode.SIGN_MESSAGE_FAILED);
  }

  public SignMessageException(final String message) {
    super(ErrorCode.SIGN_MESSAGE_FAILED, new KeyValuePair("message", message));
  }

  public SignMessageException(final Throwable e) {
    super(ErrorCode.SIGN_MESSAGE_FAILED, e);
  }
}
