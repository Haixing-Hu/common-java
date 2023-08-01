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
 * Thrown to indicate that a file or directory or device can not be copied.
 *
 * @author Haixing Hu
 */
public class FileCanNotCopyException extends IOException {

  private static final long serialVersionUID = 6900269932475719771L;

  public FileCanNotCopyException() {
    super("The file can't be copied. ");
  }

  public FileCanNotCopyException(final String path) {
    super("The file can't be copied: " + path);
  }
}
