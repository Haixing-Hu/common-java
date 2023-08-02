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
 * Thrown to indicate that the current user blocks another user.
 *
 * @author Haixing Hu
 */
public class BlockUserException extends BusinessLogicException {

  private static final long serialVersionUID = 2981344543870559115L;

  public BlockUserException() {
    super(ErrorCode.BLOCK_USER);
  }
}
