////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

/**
 * The enumeration of crypto algorithms supported in the JDK system.
 *
 * @author Haixing Hu
 * @see Cipher
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">The Cipher Class</a>
 */
public enum CryptoAlgorithm {

  AES("AES"),

  AES_WRAP("AESWrap"),

  ARCFOUR("ARCFOUR"),

  BLOWFISH("Blowfish"),

  DES("DES"),

  DES_EDE("DESede"),

  DES_EDE_WRAP("DESedeWrap"),

  ECIES("ECIES"),

  RC2("RC2"),

  RC4("RC4"),

  RC5("RC5"),

  RSA("RSA");

  private static final Map<String, CryptoAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final CryptoAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the algorithm by its enumerator name or code (i.e., the standard
   * algorithm name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified algorithm, ignoring the case.
   * @return
   *     The corresponding {@link KeyAlgorithm}, or {@code null} if no such
   *     algorithm.
   */
  public static CryptoAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  CryptoAlgorithm(final String code) {
    this.code = code;
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
}
