////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.error;

import java.io.IOException;

/**
 * Thrown to indicate that an I/O operation operates on the same
 * source and destination object.
 *
 * @author Haixing Hu
 */
public class SameSourceDestinationException extends IOException {

  private static final long serialVersionUID = - 840013128808391562L;

  public SameSourceDestinationException() {
    super("The source and destination are the same file or directory. ");
  }

  public SameSourceDestinationException(final String path) {
    super("The source and destination are the same file or directory: " + path);
  }
}
