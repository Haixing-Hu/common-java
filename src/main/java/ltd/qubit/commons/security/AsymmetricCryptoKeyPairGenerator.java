////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The class of objects used to generate key pairs for asymmetric crypto
 * algorithms.
 *
 * <p>This class encapsulates the {@link KeyPairGenerator} to provide
 * convenient
 * methods generating key pairs of the specified algorithm.</p>
 *
 * @author Haixing Hu
 * @see KeyPairGenerator
 */
public class AsymmetricCryptoKeyPairGenerator {

  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  @Nullable
  private SecureRandomAlgorithm randomAlgorithm;

  /**
   * Constructs a {@link AsymmetricCryptoKeyPairGenerator}.
   *
   * @param cryptoAlgorithm
   *     the asymmetric crypto algorithm.
   */
  public AsymmetricCryptoKeyPairGenerator(
      final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
    this.randomAlgorithm = null;
  }

  /**
   * Constructs a {@link AsymmetricCryptoKeyPairGenerator}.
   *
   * @param cryptoAlgorithm
   *     the asymmetric crypto algorithm.
   * @param randomAlgorithm
   *     the secure random algorithm.
   */
  public AsymmetricCryptoKeyPairGenerator(
      final AsymmetricCryptoAlgorithm cryptoAlgorithm,
      final SecureRandomAlgorithm randomAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
    this.randomAlgorithm = requireNonNull("randomAlgorithm", randomAlgorithm);
  }

  /**
   * Gets the asymmetric crypto algorithm of this generator.
   *
   * @return the asymmetric crypto algorithm of this generator.
   */
  @NotNull
  public AsymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

  /**
   * Gets the secure random algorithm.
   *
   * <p>A {@code null} value will cause the generation of key pairs use the
   * {@link SecureRandom} implementation of the highest-priority installed
   * provider as the source of randomness.</p>
   *
   * @return the secure random algorithm.
   */
  @Nullable
  public SecureRandomAlgorithm getRandomAlgorithm() {
    return randomAlgorithm;
  }

  /**
   * Sets the secure random algorithm.
   *
   * @param randomAlgorithm
   *     the new secure random algorithm. A {@code null} value will cause the
   *     generation of key pairs use the {@link SecureRandom} implementation of
   *     the highest-priority installed provider as the source of randomness.
   */
  public void setRandomAlgorithm(
      @Nullable final SecureRandomAlgorithm randomAlgorithm) {
    this.randomAlgorithm = randomAlgorithm;
  }

  /**
   * Gets the minimum allowed size of keys in bits for the asymmetric crypto
   * algorithm of this generator.
   *
   * @return the minimum allowed size of keys in bits for the asymmetric crypto
   *     algorithm of this generator.
   */
  public int getMinKeySize() {
    return cryptoAlgorithm.minKeySize();
  }

  /**
   * Gets the maximum allowed size of keys in bits for the asymmetric crypto
   * algorithm of this generator.
   *
   * @return the maximum allowed size of keys in bits for the asymmetric crypto
   *     algorithm of this generator.
   */
  public int getMaxKeySize() {
    return cryptoAlgorithm.maxKeySize();
  }

  /**
   * Gets the default suggested size of keys in bits for the asymmetric crypto
   * algorithm of this generator.
   *
   * @return the default suggested size of keys in bits for the asymmetric
   *     crypto algorithm of this generator.
   */
  public int getDefaultKeySize() {
    return cryptoAlgorithm.defaultKeySize();
  }

  /**
   * Generates a key pair with the suggested default key-size for this signature
   * algorithm.
   *
   * @return the generated key pair.
   * @throws NoSuchAlgorithmException
   *     if the signature algorithm or secure random algorithm is not
   *     supported.
   */
  public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
    return generateKeyPair(cryptoAlgorithm.defaultKeySize());
  }

  /**
   * Generates a key pair.
   *
   * @param keySize
   *     the size of the keys in bits.
   * @return the generated key pair.
   * @throws NoSuchAlgorithmException
   *     if the signature algorithm or secure random algorithm is not
   *     supported.
   */
  public KeyPair generateKeyPair(final int keySize)
      throws NoSuchAlgorithmException {
    final KeyPairGenerator generator = KeyPairGenerator.getInstance(
        cryptoAlgorithm.code());
    final SecureRandom random = getSecureRandom();
    if (random == null) {
      generator.initialize(keySize);
    } else {
      generator.initialize(keySize, random);
    }
    return generator.generateKeyPair();
  }

  @Nullable
  private SecureRandom getSecureRandom() throws NoSuchAlgorithmException {
    if (randomAlgorithm != null) {
      return SecureRandom.getInstance(randomAlgorithm.code());
    } else {
      return null;
    }
  }
}
