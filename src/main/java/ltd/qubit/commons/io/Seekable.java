////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;

/**
 * This interface provides random seeking functions for input streams.
 *
 * @author Haixing Hu
 */
public interface Seekable {

  /**
   * Gets the length of the stream in bytes.
   *
   * @return the length of the stream in bytes.
   * @throws IOException
   *           if any I/O error occurs.
   */
  long length() throws IOException;

  /**
   * Gets the current position in the stream.
   *
   * @return the current position in the stream.
   * @throws IOException
   *           if any I/O error occurs.
   */
  long position() throws IOException;

  /**
   * Seeks to a specified position.
   *
   * @param pos
   *          the new position, measured in bytes from the beginning of the
   *          file, at which to set the current position.
   * @throws IOException
   *           if any I/O error occurs.
   */
  void seek(long pos) throws IOException;
}
