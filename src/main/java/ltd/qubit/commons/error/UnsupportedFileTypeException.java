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
 * Thrown to indicate that the attachment type is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedFileTypeException extends BusinessLogicException {

  private static final long serialVersionUID = 44405759363592068L;

  public UnsupportedFileTypeException(final String contentType) {
    super(ErrorType.REQUEST_ERROR, ErrorCode.UNSUPPORTED_FILE_TYPE,
        new KeyValuePair("content_type", contentType));
  }
}
