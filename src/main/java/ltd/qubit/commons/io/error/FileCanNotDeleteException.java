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
 * Thrown to indicate that a file or directory or device can not be deleted.
 *
 * @author Haixing Hu
 */
public class FileCanNotDeleteException extends IOException {

  private static final long serialVersionUID = - 7495925551285474239L;

  public FileCanNotDeleteException() {
    super("The file can't be deleted. ");
  }

  public FileCanNotDeleteException(final String path) {
    super("The file can't be deleted: " + path);
  }
}
