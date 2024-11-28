////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;
import java.security.GeneralSecurityException;

import ltd.qubit.commons.util.pair.KeyValuePairList;

public class UnsupportedAlgorithmException extends GeneralSecurityException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 2973072118457122125L;

  private final String algorithm;

  public UnsupportedAlgorithmException(final String algorithm) {
    super("Unsupported algorithm: " + algorithm);
    this.algorithm = algorithm;
  }

  public final String getAlgorithm() {
    return algorithm;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "UNSUPPORTED_ALGORITHM",
        KeyValuePairList.of("algorithm", algorithm));
  }
}
