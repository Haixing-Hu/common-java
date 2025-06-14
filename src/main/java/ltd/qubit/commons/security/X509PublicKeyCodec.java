////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link PublicKeyCodec}的实现，用于在{@link PublicKey}和X.509格式的字节数组之间进行编码/解码。
 *
 * @author 胡海星
 */
public class X509PublicKeyCodec implements PublicKeyCodec {

  /**
   * 非对称加密算法。
   */
  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  /**
   * 构造一个新的X.509公钥编解码器。
   *
   * @param cryptoAlgorithm
   *     用于生成密钥的非对称加密算法。
   */
  public X509PublicKeyCodec(final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
  }

  /**
   * 获取此编解码器使用的非对称加密算法。
   *
   * @return 非对称加密算法。
   */
  public AsymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PublicKey decode(final byte[] encodedPublicKey) throws DecodingException {
    requireNonNull("encodedPublicKey", encodedPublicKey);
    try {
      final KeyFactory factory = KeyFactory.getInstance(cryptoAlgorithm.code());
      final EncodedKeySpec spec = new X509EncodedKeySpec(encodedPublicKey);
      return factory.generatePublic(spec);
    } catch (final GeneralSecurityException e) {
      throw new DecodingException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] encode(final PublicKey publicKey) throws EncodingException {
    requireNonNull("publicKey", publicKey);
    try {
      final KeyFactory factory = KeyFactory.getInstance(cryptoAlgorithm.code());
      final X509EncodedKeySpec spec = factory.getKeySpec(publicKey, X509EncodedKeySpec.class);
      return spec.getEncoded();
    } catch (final GeneralSecurityException e) {
      throw new EncodingException(e);
    }
  }
}