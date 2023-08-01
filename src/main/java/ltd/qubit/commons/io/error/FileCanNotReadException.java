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
 * Thrown to indicate that a file or directory or device can not be read.
 *
 * @author Haixing Hu
 */
public class FileCanNotReadException extends IOException {

  private static final long serialVersionUID = - 4170967684441144089L;

  public FileCanNotReadException() {
    super("The file can't be read. ");
  }

  public FileCanNotReadException(final String path) {
    super("The file can't be read: " + path);
  }
}
