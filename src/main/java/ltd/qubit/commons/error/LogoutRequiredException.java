////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * Indicates that the operation should be performed by anonymous users.
 *
 * @author Haixing Hu
 */
public class LogoutRequiredException extends BusinessLogicException {

  private static final long serialVersionUID = -6379576131252730952L;

  public LogoutRequiredException() {
    super(ErrorType.AUTHENTICATION_ERROR, ErrorCode.LOGOUT_REQUIRED);
  }
}
