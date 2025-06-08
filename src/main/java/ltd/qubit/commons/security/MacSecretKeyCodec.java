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
import javax.crypto.spec.SecretKeySpec;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The key codec for the Mac (Message Authentication Code) algorithms.
 *
 * @author Haixing Hu
 */
public class MacSecretKeyCodec implements SecretKeyCodec {

  private final MacAlgorithm algorithm;

  public MacSecretKeyCodec(final MacAlgorithm algorithm) {
    this.algorithm = requireNonNull("algorithm", algorithm);
  }

  public MacAlgorithm getAlgorithm() {
    return algorithm;
  }

  @Override
  public SecretKey decode(final byte[] source) {
    return new SecretKeySpec(source, algorithm.code());
  }

  @Override
  public byte[] encode(final SecretKey source) {
    return source.getEncoded();
  }
}