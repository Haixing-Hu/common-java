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
 * The enumeration of the file open options.
 *
 * @author Haixing Hu
 */
public enum OpenOption {

  /**
   * Creates a new file or device, always.
   *
   * <p>If the specified file or device exists and is writable, the open function
   * creates a new file or device and overwrites the old one; If the specified
   * file or device does not exist and the pathname is a valid path to a
   * writable location, the open function create a new file or device and
   * succeeds.
   */
  CREATE_ALWAYS,

  /**
   * Creates a new file or device, only if it does not already exist.
   *
   * <p>If the specified file or device exists, the open function fails and an
   * {@link IOException} is thrown; if the specified file or device does not
   * exist and the pathname a valid path to a writable location, the open
   * function create a new file or device and succeeds.
   */
  CREATE_NEW,

  /**
   * Opens a file or device, always.
   *
   * <p>If the specified file or device exists, the open function succeeds. If the
   * specified file or device does not exist and is a valid path to a writable
   * location, the open function creates a new file or device and succeeds.
   */
  OPEN_ALWAYS,

  /**
   * Opens a file or device, only if it exists.
   *
   * <p>If the specified file or device does not exist, the open function fails and
   * an {@link IOException} is thrown.
   */
  OPEN_EXISTING,

  /**
   * Opens a file or device for appending, always.
   *
   * <p>If the specified file or device exists, the open function opens it for
   * writing, sets the writing pointer to the end for appending, and succeeds.
   * If the specified file or device does not exist and is a valid path to a
   * writable location, the open function creates a new file or device, opens it
   * for writing, set the writing pointer to the beginning, and succeeds.
   */
  APPEND_ALWAYS,

  /**
   * Opens a file or device for appending, only if it exists.
   *
   * <p>If the specified file or device exists, the open function opens it for
   * writing, sets the writing pointer to the end for appending, and succeeds.
   * If the specified file or device does not exist, the open function fails and
   * an {@link IOException} is thrown.
   */
  APPEND_EXISTING,

  /**
   * Opens a file or device, and truncates it to zero byte, only if it exists.
   *
   * <p>If the specified file or device does not exist, the open function fails and
   * and an {@link IOException} is thrown; otherwise, the open function opens it
   * and truncates it to zero byte.
   */
  TRUNCATE_EXISTING;

  /**
   * Tests whether this open option is compatible with the specified access
   * mode.
   *
   * @param accessMode
   *     a specified access mode.
   * @return true if this open option is compatible with the specified access
   *     mode; false otherwise.
   */
  public boolean isCompatibleWith(final AccessMode accessMode) {
    switch (this) {
      case OPEN_EXISTING:
        return true;
      case CREATE_ALWAYS:
      case CREATE_NEW:
      case APPEND_ALWAYS:
      case APPEND_EXISTING:
      case TRUNCATE_EXISTING:
        return (accessMode != AccessMode.READ_ONLY);
      default:
        return false;
    }
  }

}
