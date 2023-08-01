////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import ltd.qubit.commons.io.AccessMode;
import ltd.qubit.commons.io.OpenOption;

import java.io.IOException;

/**
 * Thrown to indicate an invalid access mode.
 *
 * @author Haixing Hu
 */
public class InvalidAccessModeException extends IOException {

  private static final long serialVersionUID = 5327741114744838035L;

  private final AccessMode accessMode;

  public InvalidAccessModeException(final AccessMode accessMode) {
    super("Invalid access mode for the operation: " + accessMode);
    this.accessMode = accessMode;
  }

  public InvalidAccessModeException(final AccessMode accessMode,
      final OpenOption openOption) {
    super("Invalid access mode for the open option "
        + openOption + ": " + accessMode);
    this.accessMode = accessMode;
  }

  public AccessMode getAccessMode() {
    return accessMode;
  }
}
