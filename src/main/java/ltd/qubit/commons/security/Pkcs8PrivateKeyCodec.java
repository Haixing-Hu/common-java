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
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link PrivateKeyCodec} 的实现，用于在 {@link PrivateKey} 和 PKCS#8 格式的字节数组之间进行编解码。
 *
 * @author 胡海星
 */
public class Pkcs8PrivateKeyCodec implements PrivateKeyCodec {

  /**
   * 非对称加密算法。
   */
  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  /**
   * 构造一个 PKCS#8 私钥编解码器。
   *
   * @param cryptoAlgorithm
   *     非对称加密算法。
   */
  public Pkcs8PrivateKeyCodec(final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
  }

  /**
   * 获取非对称加密算法。
   *
   * @return
   *     非对称加密算法。
   */
  public AsymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PrivateKey decode(final byte[] encodedPrivateKey) throws DecodingException {
    requireNonNull("encodedPrivateKey", encodedPrivateKey);
    try {
      final KeyFactory factory = KeyFactory.getInstance(cryptoAlgorithm.code());
      final EncodedKeySpec spec = new PKCS8EncodedKeySpec(encodedPrivateKey);
      return factory.generatePrivate(spec);
    } catch (final GeneralSecurityException e) {
      throw new DecodingException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] encode(final PrivateKey privateKey) throws EncodingException {
    requireNonNull("privateKey", privateKey);
    try {
      final KeyFactory factory = KeyFactory.getInstance(cryptoAlgorithm.code());
      final PKCS8EncodedKeySpec spec = factory.getKeySpec(privateKey, PKCS8EncodedKeySpec.class);
      return spec.getEncoded();
    } catch (final GeneralSecurityException e) {
      throw new EncodingException(e);
    }
  }
}