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
 * Thrown to indicates the session has expired.
 *
 * @author Haixing Hu
 */
public class SessionExpiredException  extends BusinessLogicException {

  private static final long serialVersionUID = 3646435083629016975L;

  public SessionExpiredException() {
    super(ErrorType.AUTHENTICATION_ERROR, ErrorCode.SESSION_EXPIRED);
  }
}
