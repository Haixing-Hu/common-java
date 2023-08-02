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

public class ThirdPartServiceException extends ServerSideException {

  private static final long serialVersionUID = -7165213672754327853L;

  public ThirdPartServiceException(final String message) {
    this(ErrorCode.SERVICE_FAILURE, message);
  }

  public ThirdPartServiceException(final ErrorCode errorCode, final String message) {
    super(ErrorType.THIRD_PART_ERROR, errorCode, new KeyValuePair("message", message));
  }

  public ThirdPartServiceException(final ErrorCode errorCode, final KeyValuePair ... params) {
    super(ErrorType.THIRD_PART_ERROR, errorCode, params);
  }
}
