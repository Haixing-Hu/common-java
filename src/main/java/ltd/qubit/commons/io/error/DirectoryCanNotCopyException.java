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
 * Thrown to indicate that a directory can not be copied.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotCopyException extends IOException {

  private static final long serialVersionUID = 1869539087762563069L;

  public DirectoryCanNotCopyException() {
    super("The directory can't be copied. ");
  }

  public DirectoryCanNotCopyException(final String path) {
    super("The directory can't be copied: " + path);
  }
}
