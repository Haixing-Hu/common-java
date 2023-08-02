////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
