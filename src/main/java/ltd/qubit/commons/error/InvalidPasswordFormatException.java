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
 * Thrown to indicte that the format of the password is invalid.
 *
 * @author Haixing Hu
 */
public class InvalidPasswordFormatException extends BusinessLogicException {

  private static final long serialVersionUID = 5267750209553035561L;

  public InvalidPasswordFormatException(final String requirement,
      final int minLength, final int maxLength) {
    super(ErrorCode.INVALID_PASSWORD_FORMAT,
        new KeyValuePair("requirement", requirement),
        new KeyValuePair("min_length", String.valueOf(minLength)),
        new KeyValuePair("max_length", String.valueOf(maxLength)));
  }
}
