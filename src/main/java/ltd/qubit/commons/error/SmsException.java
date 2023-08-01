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
 * The exception thrown when sending SMS failed.
 *
 * @author Haixing Hu
 */
public class SmsException extends ThirdPartServiceException {

  private static final long serialVersionUID = 8488280770978042223L;

  /**
   * Constructs a {@link SmsException}.
   *
   * @param errorCode
   *     the returned code of the service.
   * @param message
   *     the detailed error message describing the error.
   */
  public SmsException(final String errorCode, final String message) {
    super(ErrorCode.SEND_SMS_FAILED, new KeyValuePair("error_code", errorCode),
        new KeyValuePair("message", message));
  }
}
