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
 * Thrown to indicate that a file or directory or device can not be read.
 *
 * @author Haixing Hu
 */
public class FileCanNotExecuteException extends IOException {

  private static final long serialVersionUID = - 752442371625554633L;

  public FileCanNotExecuteException() {
    super("The file can't be executed. ");
  }

  public FileCanNotExecuteException(final String path) {
    super("The file can't be executed: " + path);
  }
}
