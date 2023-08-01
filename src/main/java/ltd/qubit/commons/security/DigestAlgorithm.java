////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of message digesting algorithms supported by the JDK.
 *
 * @author Haixing Hu
 * @see MessageDigest
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest">MessageDigest Algorithms</a>
 */
public enum DigestAlgorithm {

  MD2("MD2"),

  MD5("MD5"),

  SHA1("SHA-1"),

  SHA224("SHA-224"),

  SHA256("SHA-256"),

  SHA384("SHA-384"),

  SHA512("SHA-512"),

  SHA512_224("SHA-512/224"),

  SHA512_256("SHA-512/256");

  private static final Map<String, DigestAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final DigestAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the digest algorithm by its enumerator name or code (i.e., the
   * standard name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified signature algorithm, ignoring the case.
   * @return
   *     The corresponding {@link DigestAlgorithm}, or {@code null} if no such
   *     algorithm.
   */
  public static DigestAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  DigestAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * Gets the code of this digest algorithm, i.e., the standard name in the JDK.
   *
   * @return
   *     the code of this digest algorithm, i.e., the standard name in the JDK.
   */
  public String code() {
    return code;
  }
}
