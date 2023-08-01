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
 * Thrown to indicate that the JSON format is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidJsonFormatException extends BusinessLogicException {

  private static final long serialVersionUID = -6779062496130135614L;

  private Throwable cause = null;

  public InvalidJsonFormatException() {
    super(ErrorType.IO_ERROR, ErrorCode.INVALID_JSON_FORMAT);
  }

  public InvalidJsonFormatException(final String json) {
    super(ErrorType.IO_ERROR, ErrorCode.INVALID_JSON_FORMAT,
        new KeyValuePair("json", json));
  }

  public InvalidJsonFormatException(final String json, final Throwable cause) {
    super(ErrorType.IO_ERROR, ErrorCode.INVALID_JSON_FORMAT,
        new KeyValuePair("json", json),
        new KeyValuePair("message", cause.getMessage()));
    this.cause = cause;
  }

  public Throwable cause() {
    return cause;
  }
}
