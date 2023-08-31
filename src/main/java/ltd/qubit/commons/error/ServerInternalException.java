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
 * 此错误表示服务器内部错误。
 *
 * @author Haixing Hu
 */
public class ServerInternalException extends ServerSideException {

  private static final long serialVersionUID = 4119326191506464723L;

  public ServerInternalException(final ErrorCode code, final KeyValuePair... params) {
    super(ErrorType.SERVER_ERROR, code, params);
  }

  public ServerInternalException(final ErrorCode code, final Throwable cause) {
    super(ErrorType.SERVER_ERROR, code, cause);
  }
}
