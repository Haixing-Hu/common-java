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
 * Thrown to indicate that a directory can not be list.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotWriteException extends IOException {

  private static final long serialVersionUID = 1028954470907555494L;

  public DirectoryCanNotWriteException() {
    super("The directory can't be written to. ");
  }

  public DirectoryCanNotWriteException(final String path) {
    super("The directory can't be written to: " + path);
  }
}
