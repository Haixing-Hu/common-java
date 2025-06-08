////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of asymmetric crypto algorithms supported by the JDK.
 *
 * @author Haixing Hu
 * @see KeyPairGenerator
 * @see KeyFactory
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#KeyPairGenerator">KeyPairGenerator Algorithms</a>
 */
public enum AsymmetricCryptoAlgorithm {

  /**
   * The Diffie-Hellman KeyAgreement algorithm.
   */
  DH("DiffieHellman", 512, 1024, 1024),

  /**
   * The Digital Signature Algorithm.
   */
  DSA("DSA", 512, 2048, 1024),

  /**
   * The RSA algorithm.
   */
  RSA("RSA", 512, 8192, 2048),

  /**
   * The Elliptic Curve algorithm.
   */
  EC("EC", 112, 517, 512);

  private static final Map<String, AsymmetricCryptoAlgorithm>
      NAME_MAP = new HashMap<>();
  static {
    for (final AsymmetricCryptoAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the lgorithm by its enumerator name or code (i.e., the standard name
   * in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified algorithm, ignoring the case.
   * @return
   *     The corresponding {@link AsymmetricCryptoAlgorithm}, or {@code null} if
   *     no such algorithm.
   */
  public static AsymmetricCryptoAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;
  private final int minKeySize;
  private final int maxKeySize;
  private final int defaultKeySize;

  AsymmetricCryptoAlgorithm(final String code, final int minKeySize,
      final int maxKeySize, final int defaultKeySize) {
    this.code = code;
    this.minKeySize = minKeySize;
    this.maxKeySize = maxKeySize;
    this.defaultKeySize = defaultKeySize;
  }

  /**
   * Gets the code of this algorithm, i.e., the standard name in the JDK.
   *
   * @return
   *     the code of this algorithm, i.e., the standard name in the JDK.
   */
  String code() {
    return code;
  }

  /**
   * Gets the minimum key size in bits allowed for this algorithm.
   *
   * @return
   *     the minimum key size in bits allowed for this algorithm.
   */
  public int minKeySize() {
    return minKeySize;
  }

  /**
   * Gets the maximum key size in bits allowed for this algorithm.
   *
   * @return
   *     the maximum key size in bits allowed for this algorithm.
   */
  public int maxKeySize() {
    return maxKeySize;
  }

  /**
   * Gets the suggested default key size in bits for this algorithm.
   *
   * @return
   *     the suggested default key size in bits for this algorithm.
   */
  public int defaultKeySize() {
    return defaultKeySize;
  }
}