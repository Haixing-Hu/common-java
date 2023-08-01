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
 * Thrown to indicate that a directory can not be list.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotListException extends IOException {

  private static final long serialVersionUID = 4992858511247895491L;

  public DirectoryCanNotListException() {
    super("Can't list the directory. ");
  }

  public DirectoryCanNotListException(final String path) {
    super("Can't list the directory: " + path);
  }
}
