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
 * Thrown to indicate that a file or directory or device can not be create.
 *
 * @author Haixing Hu
 */
public class FileCanNotCreateException extends IOException {

  private static final long serialVersionUID = - 83709355751843651L;

  public FileCanNotCreateException() {
    super("The file can't be created. ");
  }

  public FileCanNotCreateException(final String path) {
    super("The file can't be created. " + path);
  }
}
