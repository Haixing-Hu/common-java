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

import javax.crypto.Cipher;

/**
 * JDK系统支持的加密算法枚举。
 *
 * @author 胡海星
 * @see Cipher
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">The Cipher Class</a>
 */
public enum CryptoAlgorithm {

  /**
   * AES算法。
   */
  AES("AES"),

  /**
   * AESWrap算法。
   */
  AES_WRAP("AESWrap"),

  /**
   * ARCFOUR算法。
   */
  ARCFOUR("ARCFOUR"),

  /**
   * Blowfish算法。
   */
  BLOWFISH("Blowfish"),

  /**
   * DES算法。
   */
  DES("DES"),

  /**
   * DESede算法。
   */
  DES_EDE("DESede"),

  /**
   * DESedeWrap算法。
   */
  DES_EDE_WRAP("DESedeWrap"),

  /**
   * ECIES算法。
   */
  ECIES("ECIES"),

  /**
   * RC2算法。
   */
  RC2("RC2"),

  /**
   * RC4算法。
   */
  RC4("RC4"),

  /**
   * RC5算法。
   */
  RC5("RC5"),

  /**
   * RSA算法。
   */
  RSA("RSA");

  /**
   * 算法名称与枚举的映射。
   */
  private static final Map<String, CryptoAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final CryptoAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 通过枚举名称或代码（即JDK中的标准算法名称）获取算法。
   *
   * @param nameOrCode
   *     指定算法的名称或代码，忽略大小写。
   * @return
   *     对应的{@link KeyAlgorithm}，如果没有此类算法则返回{@code null}。
   */
  public static CryptoAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 算法的代码，即JDK中的标准名称。
   */
  private final String code;

  /**
   * 构造一个{@link CryptoAlgorithm}对象。
   *
   * @param code
   *     算法的代码，即JDK中的标准名称。
   */
  CryptoAlgorithm(final String code) {
    this.code = code;
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
}