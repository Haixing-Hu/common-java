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
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.util.codec.Base64Codec;
import ltd.qubit.commons.util.codec.HexCodec;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignatureSignerVerifierTest {

  private static final String RESOURCE = "/ltd/qubit/commons/security/three-body.txt";

  @Test
  public void testSignVerify() throws Exception {
    final String message = IoUtils.toString(RESOURCE, this.getClass(), UTF_8);
    for (final SignatureAlgorithm algorithm : SignatureAlgorithm.values()) {
      if (algorithm.name().startsWith("NONE_")) {
        continue;
      }
      if (algorithm.cryptoAlgorithm() == AsymmetricCryptoAlgorithm.EC) {
        continue;
      }
      testSignVerify(algorithm, message);
    }
  }

  private void testSignVerify(final SignatureAlgorithm algorithm, final String message)
      throws Exception {
    final SignatureKeyPairGenerator generator = new SignatureKeyPairGenerator(algorithm);
    System.out.println();
    System.out.println("==========================================================");
    System.out.println("Test the sign/verify of signature for " + algorithm.name()
        + ": keySize = " + generator.getDefaultKeySize());
    System.out.println("==========================================================");
    final KeyPair keyPair = generator.generateKeyPair();
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
    final SignatureSigner signer = new SignatureSigner(algorithm);
    final SignatureVerifier verifier = new SignatureVerifier(algorithm);
    final byte[] signature = signer.sign(privateKey, message);
    System.out.println("The signature of the message is: " + base64Codec.encode(signature));
    final boolean correct = verifier.verify(publicKey, message, signature);
    assertTrue(correct);
  }
}