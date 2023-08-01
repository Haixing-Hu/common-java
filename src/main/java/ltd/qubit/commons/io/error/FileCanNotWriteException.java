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
 * Thrown to indicate that a file or directory or device can not be write.
 *
 * @author Haixing Hu
 */
public class FileCanNotWriteException extends IOException {

  private static final long serialVersionUID = - 8520556806523403160L;

  public FileCanNotWriteException() {
    super("The file can't be written to. ");
  }

  public FileCanNotWriteException(final String path) {
    super("The file can't be written to: " + path);
  }
}
