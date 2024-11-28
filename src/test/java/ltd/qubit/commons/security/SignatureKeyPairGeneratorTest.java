////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.util.codec.Base64Codec;
import ltd.qubit.commons.util.codec.EncodingException;
import ltd.qubit.commons.util.codec.HexCodec;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SignatureKeyPairGeneratorTest {

  @Test
  public void testGenerateKeyPair()
      throws NoSuchAlgorithmException, EncodingException {
    for (final SignatureAlgorithm algorithm : SignatureAlgorithm.values()) {
      if (algorithm.cryptoAlgorithm() == AsymmetricCryptoAlgorithm.EC) {
        continue;
      }
      testGenerateKeyPair(algorithm);
    }
  }

  private void testGenerateKeyPair(final SignatureAlgorithm algorithm)
      throws NoSuchAlgorithmException, EncodingException {
    System.out.println("Test generate key-pair for " + algorithm.name());
    final SignatureKeyPairGenerator generator = new SignatureKeyPairGenerator(algorithm);

    final KeyPair keyPair = generator.generateKeyPair(512);
    assertNotNull(keyPair);
    final PublicKey publicKey = keyPair.getPublic();
    assertNotNull(publicKey);
    final PrivateKey privateKey = keyPair.getPrivate();
    assertNotNull(privateKey);
    final SignaturePublicKeyCodec publicKeyCodec = new SignaturePublicKeyCodec(algorithm);
    final SignaturePrivateKeyCodec privateKeyCodec = new SignaturePrivateKeyCodec(algorithm);
    final Base64Codec base64Codec = new Base64Codec();
    final HexCodec hexCodec = new HexCodec();
    final byte[] encodedPublicKey = publicKeyCodec.encode(publicKey);
    final byte[] encodedPrivateKey = privateKeyCodec.encode(privateKey);
    System.out.println("Public Key (HEX): " + hexCodec.encode(encodedPublicKey));
    System.out.println("Public Key (BASE-64): " + base64Codec.encode(encodedPublicKey));
    System.out.println("Private Key (HEX): " + hexCodec.encode(encodedPrivateKey));
    System.out.println("Private Key (BASE-64): " + base64Codec.encode(encodedPrivateKey));
  }
}
