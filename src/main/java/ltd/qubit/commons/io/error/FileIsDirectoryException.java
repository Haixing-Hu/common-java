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
 * Thrown to indicate that the file exists but is a directory instead of a file.
 *
 * @author Haixing Hu
 */
public class FileIsDirectoryException extends IOException {

  private static final long serialVersionUID = -5140786141865269895L;

  public FileIsDirectoryException() {
    super("The file exists but is a directory. ");
  }

  public FileIsDirectoryException(final String path) {
    super("The file exists but is a directory: " + path);
  }
}
