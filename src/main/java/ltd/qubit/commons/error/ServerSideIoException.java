////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.IOException;

import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * Thrown to indicate a server side I/O error.
 *
 * <p>This exception wraps a {@link IOException} and it can be thrown without
 * declaring in the method signature.</p>
 *
 * @author Haixing Hu
 */
public class ServerSideIoException extends ServerSideException {

  private static final long serialVersionUID = 476295550047450167L;

  private final IOException cause;

  public ServerSideIoException(final IOException cause) {
    super(ErrorType.IO_ERROR, ErrorCode.IO_ERROR,
        new KeyValuePair("message", cause.getMessage()));
    this.cause = cause;
  }

  @Override
  public IOException getCause() {
    return cause;
  }
}
