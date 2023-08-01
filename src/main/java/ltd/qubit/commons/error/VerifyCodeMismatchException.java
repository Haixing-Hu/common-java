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
 * An exception thrown to indicate the mismatch of verify code.
 *
 * @author Haixing Hu
 */
public class VerifyCodeMismatchException extends BusinessLogicException {

  private static final long serialVersionUID = -1767662246498278604L;

  public VerifyCodeMismatchException() {
    super(ErrorCode.VERIFY_CODE_MISMATCH);
  }

}
