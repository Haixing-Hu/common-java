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
 * Thrown to indicate that the file exists but is not a directory.
 *
 * @author Haixing Hu
 */
public class FileIsNotDirectoryException extends IOException {

  private static final long serialVersionUID = 375647745159564317L;

  public FileIsNotDirectoryException() {
    super("The file exists but is not a directory. ");
  }

  public FileIsNotDirectoryException(final String path) {
    super("The file exists but is not a directory: " + path);
  }
}
