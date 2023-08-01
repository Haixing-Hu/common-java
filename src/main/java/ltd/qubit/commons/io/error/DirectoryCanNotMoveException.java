////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.IOException;

/**
 * Thrown to indicate that a directory can not be moved.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotMoveException extends IOException {

  private static final long serialVersionUID = 5344978076308643042L;

  public DirectoryCanNotMoveException() {
    super("The directory can't be moved. ");
  }

  public DirectoryCanNotMoveException(final String path) {
    super("The directory can't be moved: " + path);
  }
}
