////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * Thrown to indicate an invalid seek position.
 *
 * @author Haixing Hu
 */
public class InvalidSeekPositionException extends IOException {

  private static final long serialVersionUID = 4459304856821158328L;

  public static final String MESSAGE = "Invalid seek position: ";

  private final long pos;

  public InvalidSeekPositionException(final long pos) {
    super(MESSAGE + pos);
    this.pos = pos;
  }

  public long position() {
    return pos;
  }

}