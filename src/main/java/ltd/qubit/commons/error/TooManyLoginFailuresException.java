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
 * An exception thrown to indicates the user login failed too many times.
 *
 * @author Haixing Hu
 */
public class TooManyLoginFailuresException extends BusinessLogicException {

  private static final long serialVersionUID = -885058974515170735L;

  private final String username;
  private final long expiredSeconds;

  public TooManyLoginFailuresException(final String username, final long expiredSeconds) {
    super(ErrorType.AUTHENTICATION_ERROR, ErrorCode.TOO_MANY_LOGIN_FAILURES,
        new KeyValuePair("username", username),
        new KeyValuePair("duration", expiredSeconds));
    this.username  = username;
    this.expiredSeconds = expiredSeconds;
  }

  public final String getUsername() {
    return username;
  }

  public long getExpiredSeconds() {
    return expiredSeconds;
  }
}
