////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import javax.crypto.SecretKey;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A class used to decode password based cryptographic keys for encryption or
 * decryption.
 *
 * @author Haixing Hu
 */
public class PasswordBasedKeyDecoder {

  private final CryptoAlgorithm algorithm;

  public PasswordBasedKeyDecoder(final CryptoAlgorithm algorithm) {
    this.algorithm = requireNonNull("algorithm", algorithm);
  }

  public CryptoAlgorithm getAlgorithm() {
    return algorithm;
  }

  public SecretKey getKey(final String password, final String slat) {
    return null;
  }

}