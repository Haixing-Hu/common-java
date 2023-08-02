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
 * Thrown to indicate that a file already exists.
 *
 * @author Haixing Hu
 */
public class FileAlreadyExistException extends IOException {

  private static final long serialVersionUID = 8025754182997731427L;

  private final String path;

  public FileAlreadyExistException(final String path) {
    super("The file already exists: " + path);
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
