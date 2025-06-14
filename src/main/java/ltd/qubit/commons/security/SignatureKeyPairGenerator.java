////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.KeyPairGenerator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于生成签名算法使用的密钥对的对象类。
 *
 * <p>此类封装了 {@link KeyPairGenerator}，提供了便捷的方法
 * 来生成指定签名算法的密钥对。</p>
 *
 * @author 胡海星
 * @see KeyPairGenerator
 */
public class SignatureKeyPairGenerator extends AsymmetricCryptoKeyPairGenerator {

  /**
   * 签名算法。
   */
  private final SignatureAlgorithm signatureAlgorithm;

  /**
   * 构造一个 {@link SignatureKeyPairGenerator}。
   *
   * @param signatureAlgorithm
   *     签名算法。
   */
  public SignatureKeyPairGenerator(
      final SignatureAlgorithm signatureAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm());
    this.signatureAlgorithm = requireNonNull("algorithm", signatureAlgorithm);
  }

  /**
   * 构造一个 {@link SignatureKeyPairGenerator}。
   *
   * @param signatureAlgorithm
   *     签名算法。
   * @param randomAlgorithm
   *     安全随机算法。
   */
  public SignatureKeyPairGenerator(final SignatureAlgorithm signatureAlgorithm,
      final SecureRandomAlgorithm randomAlgorithm) {
    super(signatureAlgorithm.cryptoAlgorithm(), randomAlgorithm);
    this.signatureAlgorithm = requireNonNull("algorithm", signatureAlgorithm);
  }

  /**
   * 获取签名算法。
   *
   * @return 签名算法。
   */
  public SignatureAlgorithm getSignatureAlgorithm() {
    return signatureAlgorithm;
  }
}