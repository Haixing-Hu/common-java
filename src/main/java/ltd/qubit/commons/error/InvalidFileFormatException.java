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
 * Thrown to indicate that the file format is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidFileFormatException extends BusinessLogicException {

  private static final long serialVersionUID = -3517828093133105010L;

  public InvalidFileFormatException() {
    super(ErrorType.IO_ERROR, ErrorCode.INVALID_FILE_FORMAT);
  }

  public InvalidFileFormatException(final String message) {
    super(ErrorType.IO_ERROR, ErrorCode.INVALID_FILE_FORMAT, new KeyValuePair("message", message));
  }
}
