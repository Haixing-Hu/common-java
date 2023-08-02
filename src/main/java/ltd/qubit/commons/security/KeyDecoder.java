////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A class used to decode cryptographic keys for encryption or decryption.
 *
 * @author Haixing Hu
 */
public class KeyDecoder {

  private final CryptoAlgorithm algorithm;

  public KeyDecoder(final CryptoAlgorithm algorithm) {
    this.algorithm = requireNonNull("algorithm", algorithm);
  }

  public CryptoAlgorithm getAlgorithm() {
    return algorithm;
  }

}
