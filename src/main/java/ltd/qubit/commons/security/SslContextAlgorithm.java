////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of SSL context algorithms supported by the JDK.
 *
 * @author Haixing Hu
 */
public enum SslContextAlgorithm {
  /**
   * Supports some version of SSL; may support other versions.
   */
  SSL("SSL"),

  /**
   * Supports SSL version 2 or later; may support other versions.
   */
  SSL_V2("SSLv2"),

  /**
   * Supports SSL version 3; may support other versions.
   */
  SSL_V3("SSLv3"),

  /**
   * Supports some version of TLS; may support other versions.
   */
  TLS("TLS"),

  /**
   * Supports RFC 2246: TLS version 1.0 ; may support other versions.
   */
  TLS_V1("TLSv1"),

  /**
   * Supports RFC 4346: TLS version 1.1 ; may support other versions.
   */
  TLS_V1_1("TLSv1.1"),

  /**
   * Supports RFC 5246: TLS version 1.2 ; may support other versions.
   */
  TLS_V1_2("TLSv1.2");

  private static final Map<String, SslContextAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final SslContextAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the SSL context algorithm by its enumerator name or code (i.e., the
   * standard name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified SSL context algorithm, ignoring the case.
   * @return
   *     The corresponding {@link SignatureAlgorithm}, or {@code null} if no
   *     such algorithm.
   */
  public static SslContextAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  SslContextAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * Gets the code of this SSL context algorithm, i.e., the standard name in the JDK.
   *
   * @return
   *     the code of this SSL context algorithm, i.e., the standard name in the JDK.
   */
  public String code() {
    return code;
  }
}
