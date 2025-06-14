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
 * JDK支持的非对称加密算法的枚举。
 *
 * @author 胡海星
 * @see KeyPairGenerator
 * @see KeyFactory
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#KeyPairGenerator">KeyPairGenerator Algorithms</a>
 */
public enum AsymmetricCryptoAlgorithm {

  /**
   * Diffie-Hellman密钥协商算法。
   */
  DH("DiffieHellman", 512, 1024, 1024),

  /**
   * 数字签名算法。
   */
  DSA("DSA", 512, 2048, 1024),

  /**
   * RSA算法。
   */
  RSA("RSA", 512, 8192, 2048),

  /**
   * 椭圆曲线算法。
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
   * 通过枚举名称或代码（即JDK中的标准名称）获取算法。
   *
   * @param nameOrCode
   *     指定算法的名称或代码，忽略大小写。
   * @return
   *     对应的{@link AsymmetricCryptoAlgorithm}，如果没有此类算法则返回{@code null}。
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
   * 获取此算法的代码，即JDK中的标准名称。
   *
   * @return
   *     此算法的代码，即JDK中的标准名称。
   */
  String code() {
    return code;
  }

  /**
   * 获取此算法允许的最小密钥大小（位）。
   *
   * @return
   *     此算法允许的最小密钥大小（位）。
   */
  public int minKeySize() {
    return minKeySize;
  }

  /**
   * 获取此算法允许的最大密钥大小（位）。
   *
   * @return
   *     此算法允许的最大密钥大小（位）。
   */
  public int maxKeySize() {
    return maxKeySize;
  }

  /**
   * 获取此算法建议的默认密钥大小（位）。
   *
   * @return
   *     此算法建议的默认密钥大小（位）。
   */
  public int defaultKeySize() {
    return defaultKeySize;
  }
}