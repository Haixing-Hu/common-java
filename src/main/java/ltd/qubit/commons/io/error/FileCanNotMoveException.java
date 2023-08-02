////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * Thrown to indicate that a file or directory or device can not be moved.
 *
 * @author Haixing Hu
 */
public class FileCanNotMoveException extends IOException {

  private static final long serialVersionUID = 8721109996158945749L;

  public FileCanNotMoveException() {
    super("The file can't be moved. ");
  }

  public FileCanNotMoveException(final String path) {
    super("The file can't be moved: " + path);
  }
}
