////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of algorithms can be specified when generating an instance of
 * {@link KeyFactory}.
 *
 * @author Haixing Hu
 */
public enum KeyAlgorithm {
  /**
   * Keys for the Diffie-Hellman KeyAgreement algorithm.
   *
   * Note: key.getAlgorithm() will return "DH" instead of "DiffieHellman".
   */
  DIFFIE_HELLMAN("DiffieHellman"),

  /**
   * Keys for the Digital Signature Algorithm.
   */
  DSA("DSA"),

  /**
   * Keys for the RSA algorithm (Signature/Cipher).
   */
  RSA("RSA"),

  /**
   * Keys for the RSASSA-PSS algorithm (Signature).
   */
  RSASSA_PSS("RSASSA-PSS"),

  /**
   * Keys for the Elliptic Curve algorithm.
   */
  EC("EC");

  private static final Map<String, KeyAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final KeyAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the key algorithm by its enumerator name or code (i.e., the standard
   * algorithm name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified enumerator, ignoring the case.
   * @return
   *     The corresponding {@link KeyAlgorithm} enumerator, or {@code null} if
   *     no such enumerator.
   */
  public static KeyAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  KeyAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * Gets the code of this key algorithm, i.e., the standard algorithm name in
   * the JDK.
   *
   * @return
   *     the code of this key algorithm, i.e., the standard algorithm name in
   *     the JDK.
   */
  public String code() {
    return code;
  }

  /**
   * Gets the key factory generating keys of this algorithm.
   *
   * @return
   *     the key factory generating keys of this algorithm.
   * @throws NoSuchAlgorithmException
   *     if this algorithm is not supported by the system.
   */
  public KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
    return KeyFactory.getInstance(code);
  }
}
