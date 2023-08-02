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
 * Thrown to indicate that the format of username is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidUsernameFormatException extends BusinessLogicException {

  private static final long serialVersionUID = -1543906267261960741L;

  public InvalidUsernameFormatException(final String requirement,
      final int minLength, final int maxLength) {
    super(ErrorCode.INVALID_USERNAME_FORMAT,
        new KeyValuePair("requirement", requirement),
        new KeyValuePair("min_length", String.valueOf(minLength)),
        new KeyValuePair("max_length", String.valueOf(maxLength)));
  }
}
