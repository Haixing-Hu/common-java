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
 * The enumeration of symmetric crypto algorithms supported by JDK.
 *
 * @author Haixing Hu
 * @see KeyGenerator
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">
 *   The Cipher Class</a>
 */
public enum SymmetricCryptoAlgorithm {

  /**
   * Advanced Encryption Standard as specified by NIST in
   * <a href="https://csrc.nist.gov/publications/detail/fips/197/final">FIPS
   * 197</a>.
   *
   * <p>Also known as the Rijndael algorithm by Joan Daemen and Vincent Rijmen, AES
   * is a 128-bit block cipher supporting keys of 128, 192, and 256 bits.
   *
   * <p>To use the AES cipher with only one valid key size, use the format AES_n,
   * where n can be 128, 192, or 256.
   */
  AES("AES", 128, 256, 256),

  /**
   * A stream cipher believed to be fully interoperable with the RC4 cipher
   * developed by Ron Rivest.
   *
   * <p>For more information, see A Stream Cipher Encryption Algorithm "Arcfour",
   * Internet Draft (expired).
   */
  ARCFOUR("ARCFOUR", 40, 1024, 1024),

  /**
   * The <a href="https://www.schneier.com/academic/blowfish/">Blowfish block
   * cipher</a> designed by Bruce Schneier.
   */
  BLOWFISH("Blowfish", 32, 448, 256),

  /**
   * The Digital Encryption Standard as described in
   * <a href="https://csrc.nist.gov/publications/fips/fips46-3/fips46-3.pdf">FIPS
   * PUB 46-3</a>.
   */
  DES("DES", 56, 56, 56),

  /**
   * Triple DES Encryption (also known as DES-EDE, 3DES, or Triple-DES).
   *
   * <p>Data is encrypted using the DES algorithm three separate times. It is first
   * encrypted using the first subkey, then decrypted with the second subkey,
   * and encrypted with the third subkey.
   */
  DES_EDE("DESede", 112, 168, 168),

  /**
   * Variable-key-size encryption algorithms developed by Ron Rivest for RSA
   * Data Security, Inc.
   */
  RC2("RC2", 40, 1024, 256);

  private static final Map<String, SymmetricCryptoAlgorithm> NAME_MAP = new HashMap<>();

  static {
    for (final SymmetricCryptoAlgorithm algorithm : values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the algorithm by its enumerator name or code (i.e., the standard name
   * in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified algorithm, ignoring the case.
   * @return The corresponding {@link SymmetricCryptoAlgorithm}, or {@code null}
   *     if no such algorithm.
   */
  public static SymmetricCryptoAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;
  private final int minKeySize;
  private final int maxKeySize;
  private final int defaultKeySize;

  SymmetricCryptoAlgorithm(final String code, final int minKeySize,
      final int maxKeySize, final int defaultKeySize) {
    this.code = code;
    this.minKeySize = minKeySize;
    this.maxKeySize = maxKeySize;
    this.defaultKeySize = defaultKeySize;
  }

  /**
   * Gets the code of this algorithm, i.e., the standard name in the JDK.
   *
   * @return the code of this algorithm, i.e., the standard name in the JDK.
   */
  String code() {
    return code;
  }

  /**
   * Gets the minimum key size in bits allowed for this algorithm.
   *
   * @return the minimum key size in bits allowed for this algorithm.
   */
  public int minKeySize() {
    return minKeySize;
  }

  /**
   * Gets the maximum key size in bits allowed for this algorithm.
   *
   * @return the maximum key size in bits allowed for this algorithm.
   */
  public int maxKeySize() {
    return maxKeySize;
  }

  /**
   * Gets the suggested default key size in bits for this algorithm.
   *
   * @return the suggested default key size in bits for this algorithm.
   */
  public int defaultKeySize() {
    return defaultKeySize;
  }
}
