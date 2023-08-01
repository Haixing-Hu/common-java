////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.KeyPairGenerator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The class of objects used to generate key pairs used by signature
 * algorithms.
 *
 * <p>This class encapsulates the {@link KeyPairGenerator} to provide
 * convenient
 * methods generating key pairs of the specified signature algorithm.</p>
 *
 * @author Haixing Hu
 * @see KeyPairGenerator
 */
public class SignatureKeyPairGenerator extends AsymmetricCryptoKeyPairGenerator {

  private final SignatureAlgorithm signatureAlgorithm;

  /**
   * Constructs a {@link SignatureKeyPairGenerator}.
   *
   * @param signatureAlgorithm
   *     the signature algorithm.
   */
  public SignatureKeyPairGenerator(
      final SignatureAlgorithm signatureAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm());
    this.signatureAlgorithm = requireNonNull("algorithm", signatureAlgorithm);
  }

  /**
   * Constructs a {@link SignatureKeyPairGenerator}.
   *
   * @param signatureAlgorithm
   *     the signature algorithm.
   * @param randomAlgorithm
   *     the secure random algorithm.
   */
  public SignatureKeyPairGenerator(final SignatureAlgorithm signatureAlgorithm,
      final SecureRandomAlgorithm randomAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm(), randomAlgorithm);
    this.signatureAlgorithm = requireNonNull("algorithm", signatureAlgorithm);
  }

  /**
   * Gets the signature algorithm.
   *
   * @return the signature algorithm.
   */
  public SignatureAlgorithm getSignatureAlgorithm() {
    return signatureAlgorithm;
  }
}
