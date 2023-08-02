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

import ltd.qubit.commons.io.OpenOption;

/**
 * Thrown to indicate an open option is not supported by a function.
 *
 * @author Haixing Hu
 */
public class UnsupportedOpenOptionException extends IOException {

  private static final long serialVersionUID = 2438085249149373956L;

  private final OpenOption option;

  public UnsupportedOpenOptionException(final OpenOption option) {
    super("The specified open option is not supported by this function: "
        + option.name());
    this.option = option;
  }

  public OpenOption option() {
    return option;
  }

}
