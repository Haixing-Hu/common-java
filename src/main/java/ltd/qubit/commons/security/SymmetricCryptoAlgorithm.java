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

import javax.crypto.KeyGenerator;

/**
 * JDK支持的对称加密算法的枚举。
 *
 * @author 胡海星
 * @see KeyGenerator
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">
 *   The Cipher Class</a>
 */
public enum SymmetricCryptoAlgorithm {

  /**
   * 由NIST在<a href="https://csrc.nist.gov/publications/detail/fips/197/final">FIPS 197</a>
   * 中指定的高级加密标准（Advanced Encryption Standard）。
   *
   * <p>也被称为由Joan Daemen和Vincent Rijmen开发的Rijndael算法，AES
   * 是一个128位分组密码，支持128、192和256位密钥。
   *
   * <p>要使用只有一个有效密钥大小的AES密码，请使用格式AES_n，
   * 其中n可以是128、192或256。
   */
  AES("AES", 128, 256, 256),

  /**
   * 一种被认为与Ron Rivest开发的RC4密码完全可互操作的流密码。
   *
   * <p>有关更多信息，请参见《流密码加密算法"Arcfour"》，互联网草案（已过期）。
   */
  ARCFOUR("ARCFOUR", 40, 1024, 1024),

  /**
   * 由Bruce Schneier设计的<a href="https://www.schneier.com/academic/blowfish/">
   * Blowfish分组密码</a>。
   */
  BLOWFISH("Blowfish", 32, 448, 256),

  /**
   * 如<a href="https://csrc.nist.gov/publications/fips/fips46-3/fips46-3.pdf">
   * FIPS PUB 46-3</a>中所述的数据加密标准（Digital Encryption Standard）。
   */
  DES("DES", 56, 56, 56),

  /**
   * 三重DES加密（也称为DES-EDE、3DES或Triple-DES）。
   *
   * <p>数据使用DES算法分别加密三次。首先使用第一个子密钥加密，
   * 然后使用第二个子密钥解密，最后使用第三个子密钥加密。
   */
  DES_EDE("DESede", 112, 168, 168),

  /**
   * 由Ron Rivest为RSA Data Security, Inc.开发的可变密钥大小加密算法。
   */
  RC2("RC2", 40, 1024, 256);

  /**
   * 名称到对称加密算法的映射。
   */
  private static final Map<String, SymmetricCryptoAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final SymmetricCryptoAlgorithm algorithm : values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 根据枚举名称或代码（即JDK中的标准名称）获取算法。
   *
   * @param nameOrCode
   *     指定算法的名称或代码，忽略大小写。
   * @return 对应的{@link SymmetricCryptoAlgorithm}，如果没有该算法则返回{@code null}。
   */
  public static SymmetricCryptoAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 此算法的代码，即JDK中的标准名称。
   */
  private final String code;

  /**
   * 最小密钥大小。
   */
  private final int minKeySize;

  /**
   * 最大密钥大小。
   */
  private final int maxKeySize;

  /**
   * 默认密钥大小。
   */
  private final int defaultKeySize;

  /**
   * 构造函数。
   *
   * @param code
   *     算法的代码，即JDK中的标准名称。
   * @param minKeySize
   *     最小密钥大小。
   * @param maxKeySize
   *     最大密钥大小。
   * @param defaultKeySize
   *     默认密钥大小。
   */
  SymmetricCryptoAlgorithm(final String code, final int minKeySize,
      final int maxKeySize, final int defaultKeySize) {
    this.code = code;
    this.minKeySize = minKeySize;
    this.maxKeySize = maxKeySize;
    this.defaultKeySize = defaultKeySize;
  }

  /**
   * 获取此算法的代码，即JDK中的标准名称。
   *
   * @return 此算法的代码，即JDK中的标准名称。
   */
  String code() {
    return code;
  }

  /**
   * 获取此算法允许的最小密钥大小（以位为单位）。
   *
   * @return 此算法允许的最小密钥大小（以位为单位）。
   */
  public int minKeySize() {
    return minKeySize;
  }

  /**
   * 获取此算法允许的最大密钥大小（以位为单位）。
   *
   * @return 此算法允许的最大密钥大小（以位为单位）。
   */
  public int maxKeySize() {
    return maxKeySize;
  }

  /**
   * 获取此算法建议的默认密钥大小（以位为单位）。
   *
   * @return 此算法建议的默认密钥大小（以位为单位）。
   */
  public int defaultKeySize() {
    return defaultKeySize;
  }
}