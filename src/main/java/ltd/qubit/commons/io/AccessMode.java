////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

/**
 * The enumeration of the file access modes.
 *
 * @author Haixing Hu
 */
public enum AccessMode {
  /**
   *  Open the device for read access.
   */
  READ_ONLY,

  /**
   *  Open the device for write access.
   */
  WRITE_ONLY,

  /**
   *  Open the device for read and write access.
   */
  READ_WRITE;

  /**
   * Tests whether this access mode is compatible with the specified open
   * option.
   *
   * @param openOption
   *     a specified open option.
   * @return true if this access mode is compatible with the specified open
   *     option; false otherwise.
   */
  public boolean compatibleWith(final OpenOption openOption) {
    switch (this) {
      case READ_ONLY:
        return (openOption == OpenOption.OPEN_EXISTING);
      case WRITE_ONLY:
      case READ_WRITE:
        return true;
      default:
        return false;
    }
  }
}
