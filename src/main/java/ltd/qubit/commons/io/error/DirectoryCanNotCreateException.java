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
 * Thrown to indicate that a directory can not be create.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotCreateException extends IOException {

  private static final long serialVersionUID = 2148829371944261196L;

  public DirectoryCanNotCreateException() {
    super("The directory can't be created. ");
  }

  public DirectoryCanNotCreateException(final String path) {
    super("The directory can't be created: " + path);
  }

  public DirectoryCanNotCreateException(final String path, final Throwable cause) {
    super("The directory can't be created: " + path, cause);
  }

}
