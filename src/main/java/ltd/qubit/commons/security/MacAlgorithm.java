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
 * JDK 支持的 MAC（消息认证码）算法的枚举。
 *
 * @author 胡海星
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Mac">Mac Algorithms</a>
 */
public enum MacAlgorithm {

  /**
   * 用于 HmacMD5 算法的密钥生成器，使用 MD5 作为消息摘要算法。
   */
  Hmac_MD5("HmacMD5", 32, 256, 64),

  /**
   * 用于 HmacSHA 算法的密钥生成器，使用 SHA1 作为消息摘要算法。
   */
  HMAC_SHA1("HmacSHA1", 32, 256, 64),

  /**
   * 用于 HmacSHA 算法的密钥生成器，使用 SHA224 作为消息摘要算法。
   */
  HMAC_SHA224("HmacSHA224", 32, 256, 64),

  /**
   * 用于 HmacSHA 算法的密钥生成器，使用 SHA256 作为消息摘要算法。
   */
  HMAC_SHA256("HmacSHA256", 32, 256, 64),

  /**
   * 用于 HmacSHA 算法的密钥生成器，使用 SHA384 作为消息摘要算法。
   */
  HMAC_SHA384("HmacSHA384", 32, 256, 64),

  /**
   * 用于 HmacSHA 算法的密钥生成器，使用 SHA512 作为消息摘要算法。
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

  /**
   * 算法名称与枚举的映射。
   */
  private static final Map<String, MacAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final MacAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 根据枚举器名称或代码（即 JDK 中的标准名称）获取算法。
   *
   * @param nameOrCode
   *     指定算法的名称或代码，忽略大小写。
   * @return
   *     对应的 {@link MacAlgorithm}，如果没有此类算法则返回 {@code null}。
   */
  public static MacAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 算法的代码，即JDK中的标准名称。
   */
  private final String code;

  /**
   * 算法允许的最小密钥大小（以位为单位）。
   */
  private final int minKeySize;

  /**
   * 算法允许的最大密钥大小（以位为单位）。
   */
  private final int maxKeySize;

  /**
   * 算法建议的默认密钥大小（以位为单位）。
   */
  private final int defaultKeySize;

  /**
   * 构造一个{@link MacAlgorithm}对象。
   *
   * @param code
   *     算法的代码，即JDK中的标准名称。
   * @param minKeySize
   *     算法允许的最小密钥大小（以位为单位）。
   * @param maxKeySize
   *     算法允许的最大密钥大小（以位为单位）。
   * @param defaultKeySize
   *     算法建议的默认密钥大小（以位为单位）。
   */
  MacAlgorithm(final String code, final int minKeySize,
      final int maxKeySize, final int defaultKeySize) {
    this.code = code;
    this.minKeySize = minKeySize;
    this.maxKeySize = maxKeySize;
    this.defaultKeySize = defaultKeySize;
  }

  /**
   * 获取此算法的代码，即 JDK 中的标准名称。
   *
   * @return
   *     此算法的代码，即 JDK 中的标准名称。
   */
  String code() {
    return code;
  }

  /**
   * 获取此算法允许的最小密钥大小（以位为单位）。
   *
   * @return
   *     此算法允许的最小密钥大小（以位为单位）。
   */
  public int minKeySize() {
    return minKeySize;
  }

  /**
   * 获取此算法允许的最大密钥大小（以位为单位）。
   *
   * @return
   *     此算法允许的最大密钥大小（以位为单位）。
   */
  public int maxKeySize() {
    return maxKeySize;
  }

  /**
   * 获取此算法建议的默认密钥大小（以位为单位）。
   *
   * @return
   *     此算法建议的默认密钥大小（以位为单位）。
   */
  public int defaultKeySize() {
    return defaultKeySize;
  }
}