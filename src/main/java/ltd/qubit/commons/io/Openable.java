////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;

/**
 * The interface for openable objects.
 *
 * @author Haixing Hu
 */
public interface Openable {

  /**
   * Tests whether this object is opened.
   *
   * @return true if this object is opened, false otherwise.
   */
  boolean isOpened();

  /**
   * Opens this object. If this object has been opened, an exception may be
   * thrown.
   *
   * @throws IOException
   *           if any I/O error occurred.
   */
  void open() throws IOException;

}
