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
 * Thrown to indicate that a directory already exists.
 *
 * @author Haixing Hu
 */
public class DirectoryAlreadyExistException extends IOException {

  private static final long serialVersionUID = 5699851702224917371L;

  private final String path;

  public DirectoryAlreadyExistException(final String path) {
    super("The directory already exists: " + path);
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
