////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.Version;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * An exception thrown to indicate the specified version is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedVersionException extends BusinessLogicException {

  private static final long serialVersionUID = -476273164096743622L;

  public UnsupportedVersionException() {
    super(ErrorCode.UNSUPPORTED_VERSION);
  }

  public UnsupportedVersionException(final Version version) {
    super(ErrorCode.UNSUPPORTED_VERSION,
        new KeyValuePair("version", version.toString()));
  }

  public UnsupportedVersionException(final String version) {
    super(ErrorCode.UNSUPPORTED_VERSION, new KeyValuePair("version", version));
  }
}
