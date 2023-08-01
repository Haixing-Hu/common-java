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
 * An exception thrown to indicate the expiration of verify code.
 *
 * @author Haixing Hu
 */
public class VerifyCodeExpiredException extends BusinessLogicException {

  private static final long serialVersionUID = -3445682897108257543L;

  public VerifyCodeExpiredException() {
    super(ErrorCode.VERIFY_CODE_EXPIRED);
  }

}
