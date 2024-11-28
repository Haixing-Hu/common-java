////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;

/**
 * The enumeration of key generator algorithms.
 *
 * @author Haixing Hu
 * @see KeyGenerator
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#KeyGenerator">KeyGenerator Algorithms</a>
 */
public enum KeyGeneratorAlgorithm {

  /**
   * Key generator for use with the AES algorithm.
   */
  AES("AES", 128, 256, 256),

  /**
   * Key generator for use with the ARCFOUR (RC4) algorithm.
   */
  ARCFOUR("ARCFOUR", 40, 1024, 1024),

  /**
   * Key generator for use with the Blowfish algorithm.
   */
  BLOWFISH("Blowfish", 32, 448, 256),

  /**
   * Key generator for use with the DES algorithm.
   */
  DES("DES", 56, 56, 56),

  /**
   * Key generator for use with the DESede (triple-DES) algorithm.
   */
  DES_EDE("DESede", 112, 168, 168),

  /**
   * Key generator for use with the HmacMD5 algorithm with MD5 as the message
   * digest algorithm.
   */
  Hmac_MD5("HmacMD5", 32, 256, 64),

  /**
   * Keys generator for use with the HmacSHA algorithm with SHA1 as the message
   * digest algorithm.
   */
  HMAC_SHA1("HmacSHA1", 32, 256, 64),

  /**
   * Keys generator for use with the HmacSHA algorithm with SHA224 as the message
   * digest algorithm.
   */
  HMAC_SHA224("HmacSHA224", 32, 256, 64),

  /**
   * Keys generator for use with the HmacSHA algorithm with SHA256 as the message
   * digest algorithm.
   */
  HMAC_SHA256("HmacSHA256", 32, 256, 64),

  /**
   * Keys generator for use with the HmacSHA algorithm with SHA384 as the message
   * digest algorithm.
   */
  HMAC_SHA384("HmacSHA384", 32, 256, 64),

  /**
   * Keys generator for use with the HmacSHA algorithm with SHA512 as the message
   * digest algorithm.
   */
  HMAC_SHA512("HmacSHA512", 32, 256, 64),

  /**
   * Key generator for use with the RC2 algorithm.
   */
  RC2("RC2", 40, 1024, 256);

  private static final Map<String, KeyGeneratorAlgorithm>
      NAME_MAP = new HashMap<>();
  static {
    for (final KeyGeneratorAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the algorithm by its enumerator name or code (i.e., the standard name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified algorithm, ignoring the case.
   * @return
   *     The corresponding {@link KeyGeneratorAlgorithm}, or {@code null} if no
   *     such algorithm.
   */
  public static KeyGeneratorAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;
  private final int minKeySize;
  private final int maxKeySize;
  private final int defaultKeySize;

  KeyGeneratorAlgorithm(final String code, final int minKeySize,
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
