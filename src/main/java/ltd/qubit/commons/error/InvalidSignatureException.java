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
 * 此错误表示消息的数字签名错误。
 *
 * @author 胡海星
 */
public class InvalidSignatureException extends BusinessLogicException {

  private static final long serialVersionUID = 96191141516422229L;

  private final String message;

  private final String signature;

  public InvalidSignatureException(final String message, final String signature) {
    super(ErrorCode.INVALID_SIGNATURE, new KeyValuePair("message", message),
        new KeyValuePair("signature", signature));
    this.message = message;
    this.signature = signature;
  }

  @Override
  public final String getMessage() {
    return message;
  }

  public final String getSignature() {
    return signature;
  }
}
