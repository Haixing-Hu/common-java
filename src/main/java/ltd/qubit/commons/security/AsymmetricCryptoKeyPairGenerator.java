////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
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
 * 用于生成非对称加密算法密钥对的对象类。
 *
 * <p>此类封装了{@link KeyPairGenerator}，提供便捷的方法来生成指定算法的密钥对。</p>
 *
 * @author 胡海星
 * @see KeyPairGenerator
 */
public class AsymmetricCryptoKeyPairGenerator {

  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  @Nullable
  private SecureRandomAlgorithm randomAlgorithm;

  /**
   * 构造一个{@link AsymmetricCryptoKeyPairGenerator}。
   *
   * @param cryptoAlgorithm
   *     非对称加密算法。
   */
  public AsymmetricCryptoKeyPairGenerator(
      final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
    this.randomAlgorithm = null;
  }

  /**
   * 构造一个{@link AsymmetricCryptoKeyPairGenerator}。
   *
   * @param cryptoAlgorithm
   *     非对称加密算法。
   * @param randomAlgorithm
   *     安全随机算法。
   */
  public AsymmetricCryptoKeyPairGenerator(
      final AsymmetricCryptoAlgorithm cryptoAlgorithm,
      final SecureRandomAlgorithm randomAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
    this.randomAlgorithm = requireNonNull("randomAlgorithm", randomAlgorithm);
  }

  /**
   * 获取此生成器的非对称加密算法。
   *
   * @return 此生成器的非对称加密算法。
   */
  @NotNull
  public AsymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

  /**
   * 获取安全随机算法。
   *
   * <p>{@code null}值将导致密钥对生成使用最高优先级已安装提供者的{@link SecureRandom}实现作为随机性源。</p>
   *
   * @return 安全随机算法。
   */
  @Nullable
  public SecureRandomAlgorithm getRandomAlgorithm() {
    return randomAlgorithm;
  }

  /**
   * 设置安全随机算法。
   *
   * @param randomAlgorithm
   *     新的安全随机算法。{@code null}值将导致密钥对生成使用最高优先级已安装提供者的{@link SecureRandom}实现作为随机性源。
   */
  public void setRandomAlgorithm(
      @Nullable final SecureRandomAlgorithm randomAlgorithm) {
    this.randomAlgorithm = randomAlgorithm;
  }

  /**
   * 获取此生成器的非对称加密算法允许的最小密钥大小（位）。
   *
   * @return 此生成器的非对称加密算法允许的最小密钥大小（位）。
   */
  public int getMinKeySize() {
    return cryptoAlgorithm.minKeySize();
  }

  /**
   * 获取此生成器的非对称加密算法允许的最大密钥大小（位）。
   *
   * @return 此生成器的非对称加密算法允许的最大密钥大小（位）。
   */
  public int getMaxKeySize() {
    return cryptoAlgorithm.maxKeySize();
  }

  /**
   * 获取此生成器的非对称加密算法的默认建议密钥大小（位）。
   *
   * @return 此生成器的非对称加密算法的默认建议密钥大小（位）。
   */
  public int getDefaultKeySize() {
    return cryptoAlgorithm.defaultKeySize();
  }

  /**
   * 使用此签名算法的建议默认密钥大小生成密钥对。
   *
   * @return 生成的密钥对。
   * @throws NoSuchAlgorithmException
   *     如果不支持签名算法或安全随机算法。
   */
  public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
    return generateKeyPair(cryptoAlgorithm.defaultKeySize());
  }

  /**
   * 生成密钥对。
   *
   * @param keySize
   *     密钥大小（位）。
   * @return 生成的密钥对。
   * @throws NoSuchAlgorithmException
   *     如果不支持签名算法或安全随机算法。
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