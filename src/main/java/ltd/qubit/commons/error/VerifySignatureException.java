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
 * 此错误表示对消息签名验证时失败。
 *
 * @author 胡海星
 */
public class VerifySignatureException extends ServerInternalException {

  private static final long serialVersionUID = -1945404113824975221L;

  public VerifySignatureException() {
    super(ErrorCode.VERIFY_SIGNATURE_FAILED);
  }

  public VerifySignatureException(final String message) {
    super(ErrorCode.VERIFY_SIGNATURE_FAILED, new KeyValuePair("message", message));
  }

  public VerifySignatureException(final Throwable e) {
    super(ErrorCode.VERIFY_SIGNATURE_FAILED, e);
  }
}
