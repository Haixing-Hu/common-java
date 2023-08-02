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
 * Thrown to indicate that a directory can not be deleted.
 *
 * @author Haixing Hu
 */
public class DirectoryCanNotDeleteException extends IOException {

  private static final long serialVersionUID = 568544508014410954L;

  public DirectoryCanNotDeleteException() {
    super("The directory can't be deleted. ");
  }

  public DirectoryCanNotDeleteException(final String path) {
    super("The directory can't be deleted: " + path);
  }
}
