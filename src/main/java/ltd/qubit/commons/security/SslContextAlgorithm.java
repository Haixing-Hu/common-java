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
 * JDK支持的SSL上下文算法的枚举。
 *
 * @author 胡海星
 */
public enum SslContextAlgorithm {
  /**
   * 支持某些版本的SSL；可能支持其他版本。
   */
  SSL("SSL"),

  /**
   * 支持SSL版本2或更高版本；可能支持其他版本。
   */
  SSL_V2("SSLv2"),

  /**
   * 支持SSL版本3；可能支持其他版本。
   */
  SSL_V3("SSLv3"),

  /**
   * 支持某些版本的TLS；可能支持其他版本。
   */
  TLS("TLS"),

  /**
   * 支持RFC 2246: TLS版本1.0；可能支持其他版本。
   */
  TLS_V1("TLSv1"),

  /**
   * 支持RFC 4346: TLS版本1.1；可能支持其他版本。
   */
  TLS_V1_1("TLSv1.1"),

  /**
   * 支持RFC 5246: TLS版本1.2；可能支持其他版本。
   */
  TLS_V1_2("TLSv1.2");

  /**
   * 名称到SSL上下文算法的映射。
   */
  private static final Map<String, SslContextAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final SslContextAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 根据枚举名称或代码（即JDK中的标准名称）获取SSL上下文算法。
   *
   * @param nameOrCode
   *     指定SSL上下文算法的名称或代码，忽略大小写。
   * @return
   *     对应的{@link SslContextAlgorithm}，如果没有该算法则返回{@code null}。
   */
  public static SslContextAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * SSL上下文算法的代码。
   */
  private final String code;

  /**
   * 构造函数。
   *
   * @param code
   *     SSL上下文算法的代码。
   */
  SslContextAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * 获取此SSL上下文算法的代码，即JDK中的标准名称。
   *
   * @return
   *     此SSL上下文算法的代码，即JDK中的标准名称。
   */
  public String code() {
    return code;
  }
}