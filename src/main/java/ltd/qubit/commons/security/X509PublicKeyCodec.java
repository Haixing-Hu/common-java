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
 * The implementation of {@link PublicKeyCodec} which encode/decode between
 * {@link PublicKey}s and byte arrays in the X.509 format.
 *
 * @author Haixing Hu
 */
public class X509PublicKeyCodec implements PublicKeyCodec {

  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  public X509PublicKeyCodec(final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.cryptoAlgorithm = requireNonNull("cryptoAlgorithm", cryptoAlgorithm);
  }

  public AsymmetricCryptoAlgorithm getCryptoAlgorithm() {
    return cryptoAlgorithm;
  }

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