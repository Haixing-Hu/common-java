////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * JDK支持的消息摘要算法枚举。
 *
 * @author 胡海星
 * @see MessageDigest
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest">MessageDigest Algorithms</a>
 */
public enum DigestAlgorithm {

  /**
   * MD2算法。
   */
  MD2("MD2"),

  /**
   * MD5算法。
   */
  MD5("MD5"),

  /**
   * SHA-1算法。
   */
  SHA1("SHA-1"),

  /**
   * SHA-224算法。
   */
  SHA224("SHA-224"),

  /**
   * SHA-256算法。
   */
  SHA256("SHA-256"),

  /**
   * SHA-384算法。
   */
  SHA384("SHA-384"),

  /**
   * SHA-512算法。
   */
  SHA512("SHA-512"),

  /**
   * SHA-512/224算法。
   */
  SHA512_224("SHA-512/224"),

  /**
   * SHA-512/256算法。
   */
  SHA512_256("SHA-512/256");

  /**
   * 摘要算法名称与枚举的映射。
   */
  private static final Map<String, DigestAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final DigestAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 通过枚举名称或代码（即JDK中的标准名称）获取摘要算法。
   *
   * @param nameOrCode
   *     指定签名算法的名称或代码，忽略大小写。
   * @return
   *     对应的{@link DigestAlgorithm}，如果没有此类算法则返回{@code null}。
   */
  public static DigestAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 摘要算法的代码，即JDK中的标准名称。
   */
  private final String code;

  /**
   * 构造一个{@link DigestAlgorithm}对象。
   *
   * @param code
   *     摘要算法的代码，即JDK中的标准名称。
   */
  DigestAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * 获取此摘要算法的代码，即JDK中的标准名称。
   *
   * @return
   *     此摘要算法的代码，即JDK中的标准名称。
   */
  public String code() {
    return code;
  }
}