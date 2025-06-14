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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import ltd.qubit.commons.error.UnsupportedAlgorithmException;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.EncodingException;

/**
 * {@link SecretKeyCodec} 的实现，用于在 {@link SecretKey} 和原始格式的字节数组之间进行编解码。
 *
 * @author 胡海星
 */
public class RawSecretKeyCodec implements SecretKeyCodec {

  /**
   * 对称加密算法。
   */
  private final SymmetricCryptoAlgorithm cryptoAlgorithm;

  /**
   * 构造一个原始格式的密钥编解码器。
   *
   * @param cryptoAlgorithm
   *     对称加密算法。
   */
  public RawSecretKeyCodec(final SymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = cryptoAlgorithm;
  }

  /**
   * 获取对称加密算法。
   *
   * @return
   *     对称加密算法。
   */
  public SymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

  private SecretKeyFactory getKeyFactory() throws UnsupportedAlgorithmException {
    switch (cryptoAlgorithm) {
      case AES:       //  fall down
      case ARCFOUR:   //  fall down
      case DES:       //  fall down
      case DES_EDE:
        try {
          return SecretKeyFactory.getInstance(cryptoAlgorithm.code());
        } catch (final NoSuchAlgorithmException e) {
          throw new UnsupportedAlgorithmException(cryptoAlgorithm.code());
        }
      default:
        throw new UnsupportedAlgorithmException(cryptoAlgorithm.code());
    }
  }

  private KeySpec getKeySpec(final byte[] key)
      throws InvalidKeyException, UnsupportedAlgorithmException {
    switch (cryptoAlgorithm) {
      case DES:
        return new DESKeySpec(key);
      case DES_EDE:
        return new DESedeKeySpec(key);
      case AES:
      case ARCFOUR:
      default:
        throw new UnsupportedAlgorithmException(cryptoAlgorithm.code());
    }
  }

  private Class<? extends KeySpec> getKeySpecClass()
      throws UnsupportedAlgorithmException {
    switch (cryptoAlgorithm) {
      case DES:
        return DESKeySpec.class;
      case DES_EDE:
        return DESedeKeySpec.class;
      case AES:
      case ARCFOUR:
      default:
        throw new UnsupportedAlgorithmException(cryptoAlgorithm.code());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SecretKey decode(final byte[] key) throws DecodingException {
    try {
      final SecretKeyFactory keyFactory = getKeyFactory();
      final KeySpec keySpec = getKeySpec(key);
      return keyFactory.generateSecret(keySpec);
    } catch (final GeneralSecurityException e) {
      throw new DecodingException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] encode(final SecretKey key) throws EncodingException {
    try {
      final SecretKeyFactory keyFactory = getKeyFactory();
      switch (cryptoAlgorithm) {
        case DES: {
          final DESKeySpec keySpec = (DESKeySpec) keyFactory.getKeySpec(key,
              DESKeySpec.class);
          return keySpec.getKey();
        }
        case DES_EDE: {
          final DESedeKeySpec keySpec = (DESedeKeySpec) keyFactory.getKeySpec(key,
              DESedeKeySpec.class);
          return keySpec.getKey();
        }
        case AES:
        case ARCFOUR:
        default:
          throw new UnsupportedAlgorithmException(cryptoAlgorithm.code());
      }
    } catch (final GeneralSecurityException e) {
      throw new EncodingException(e);
    }
  }
}