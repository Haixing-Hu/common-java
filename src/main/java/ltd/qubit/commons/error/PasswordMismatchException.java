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
 * The exception thrown to indicate the mismatch of password.
 *
 * @author Haixing Hu
 */
public class PasswordMismatchException extends BusinessLogicException {

  private static final long serialVersionUID = 6497054624698434844L;

  private final String username;

  public PasswordMismatchException(final String username) {
    super(ErrorType.AUTHENTICATION_ERROR, ErrorCode.PASSWORD_MISMATCH,
        new KeyValuePair("username", username));
    this.username = username;
  }

  public final String getUsername() {
    return username;
  }
}
