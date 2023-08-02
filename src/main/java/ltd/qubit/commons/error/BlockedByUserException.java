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
 * 此错误表示当前用户被另一个用户所屏蔽。
 *
 * @author 胡海星
 */
public class BlockedByUserException extends BusinessLogicException {

  private static final long serialVersionUID = -6606852043180373539L;

  public BlockedByUserException() {
    super(ErrorCode.BLOCKED_BY_USER);
  }
}
