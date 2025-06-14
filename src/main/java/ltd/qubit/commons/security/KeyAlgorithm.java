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
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 在生成 {@link KeyFactory} 实例时可以指定的算法枚举。
 *
 * @author 胡海星
 */
public enum KeyAlgorithm {
  /**
   * Diffie-Hellman 密钥协商算法的密钥。
   *
   * <p>注意：{@code key.getAlgorithm()} 将返回 "DH" 而不是 "DiffieHellman"。</p>
   */
  DIFFIE_HELLMAN("DiffieHellman"),

  /**
   * 数字签名算法的密钥。
   */
  DSA("DSA"),

  /**
   * RSA 算法（签名/加密）的密钥。
   */
  RSA("RSA"),

  /**
   * RSASSA-PSS 算法（签名）的密钥。
   */
  RSASSA_PSS("RSASSA-PSS"),

  /**
   * 椭圆曲线算法的密钥。
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
   * 根据枚举器名称或代码（即 JDK 中的标准算法名称）获取密钥算法。
   *
   * @param nameOrCode
   *     指定枚举器的名称或代码，忽略大小写。
   * @return
   *     对应的 {@link KeyAlgorithm} 枚举器，如果没有此类枚举器则返回 {@code null}。
   */
  public static KeyAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  KeyAlgorithm(final String code) {
    this.code = code;
  }

  /**
   * 获取此密钥算法的代码，即 JDK 中的标准算法名称。
   *
   * @return
   *     此密钥算法的代码，即 JDK 中的标准算法名称。
   */
  public String code() {
    return code;
  }

  /**
   * 获取生成此算法密钥的密钥工厂。
   *
   * @return
   *     生成此算法密钥的密钥工厂。
   * @throws NoSuchAlgorithmException
   *     如果系统不支持此算法。
   */
  public KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
    return KeyFactory.getInstance(code);
  }
}