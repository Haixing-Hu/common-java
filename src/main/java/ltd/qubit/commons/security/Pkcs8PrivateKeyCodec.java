////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * The implementation of {@link PrivateKeyCodec} which encode/decode between
 * {@link PrivateKey}s and byte arrays in the PKCS#8 format.
 *
 * @author Haixing Hu
 */
public class Pkcs8PrivateKeyCodec implements PrivateKeyCodec {

  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  public Pkcs8PrivateKeyCodec(final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
  }

  public AsymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

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
