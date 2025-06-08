////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of Mac (Message Authentication Code) algorithms supported by
 * JDK.
 *
 * @author Haixing Hu
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Mac">Mac Algorithms</a>
 */
public enum MacAlgorithm {

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
  HMAC_SHA512("HmacSHA512", 32, 256, 64);

  //  PBE_WITH_HMAC_MD5("PBEWithHmacMD5"),
  //
  //  PBE_WITH_HMAC_SHA1("PBEWithHmacSHA1"),
  //
  //  PBE_WITH_HMAC_SHA224("PBEWithHmacSHA224"),
  //
  //  PBE_WITH_HMAC_SHA256("PBEWithHmacSHA256"),
  //
  //  PBE_WITH_HMAC_SHA384("PBEWithHmacSHA384"),
  //
  //  PBE_WITH_HMAC_SHA512("PBEWithHmacSHA512");

  private static final Map<String, MacAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final MacAlgorithm algorithm: values()) {
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
   * @return
   *     The corresponding {@link MacAlgorithm}, or {@code null} if no such
   *     algorithm.
   */
  public static MacAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;
  private final int minKeySize;
  private final int maxKeySize;
  private final int defaultKeySize;

  MacAlgorithm(final String code, final int minKeySize,
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