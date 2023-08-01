////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePair;

public class UnsupportedAlgorithmException extends ServerInternalException {

  private static final long serialVersionUID = 2973072118457122125L;

  public UnsupportedAlgorithmException(final String algorithm) {
    super(ErrorCode.UNSUPPORTED_ALGORITHM, new KeyValuePair("algorithm", algorithm));
  }
}
