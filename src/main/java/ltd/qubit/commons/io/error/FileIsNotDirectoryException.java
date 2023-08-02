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
