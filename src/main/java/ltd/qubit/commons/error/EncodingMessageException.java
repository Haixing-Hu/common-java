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
 * 此错误表示对消息编码时失败。
 *
 * @author 胡海星
 */
public class EncodingMessageException extends ServerInternalException {

  private static final long serialVersionUID = 8426242742998906287L;

  public EncodingMessageException() {
    super(ErrorCode.ENCODING_MESSAGE_FAILED);
  }

  public EncodingMessageException(final String message) {
    super(ErrorCode.ENCODING_MESSAGE_FAILED,
        new KeyValuePair("message", message));
  }

  public EncodingMessageException(final Throwable e) {
    super(ErrorCode.ENCODING_MESSAGE_FAILED,
        new KeyValuePair("cause", e.getMessage()));
  }
}
