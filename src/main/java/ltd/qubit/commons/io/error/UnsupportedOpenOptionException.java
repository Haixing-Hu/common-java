////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import ltd.qubit.commons.io.OpenOption;

import java.io.IOException;

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
