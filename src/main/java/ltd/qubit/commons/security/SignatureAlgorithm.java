////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.security.Signature;
import java.util.HashMap;
import java.util.Map;

/**
 * JDK 支持的消息签名算法的枚举。
 *
 * @author 胡海星
 * @see DigestAlgorithm
 * @see AsymmetricCryptoAlgorithm
 * @see Signature
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecureRandom">
 *   SecureRandom Number Generation Algorithms</a>
 */
public enum SignatureAlgorithm {

  /**
   * 使用 MD2 摘要算法和 RSA 加密的签名算法，按 PKCS #1 的定义
   * 创建和验证 RSA 数字签名。
   */
  MD2_WITH_RSA("MD2withRSA", DigestAlgorithm.MD2, AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 MD5 摘要算法和 RSA 加密的签名算法，按 PKCS #1 的定义
   * 创建和验证 RSA 数字签名。
   */
  MD5_WITH_RSA("MD5withRSA", DigestAlgorithm.MD5, AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 SHA-1 和 RSA 加密算法的签名算法，按 OSI 互操作性研讨会的定义，
   * 使用 PKCS #1 中描述的填充约定。
   */
  SHA1_WITH_RSA("SHA1withRSA", DigestAlgorithm.SHA1, AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 SHA-224 和 RSA 加密算法的签名算法，按 OSI 互操作性研讨会的定义，
   * 使用 PKCS #1 中描述的填充约定。
   */
  SHA224_WITH_RSA("SHA224withRSA", DigestAlgorithm.SHA224, AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 SHA-256 和 RSA 加密算法的签名算法，按 OSI 互操作性研讨会的定义，
   * 使用 PKCS #1 中描述的填充约定。
   */
  SHA256_WITH_RSA("SHA256withRSA", DigestAlgorithm.SHA256, AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 SHA-384 和 RSA 加密算法的签名算法，按 OSI 互操作性研讨会的定义，
   * 使用 PKCS #1 中描述的填充约定。
   */
  SHA384_WITH_RSA("SHA384withRSA", DigestAlgorithm.SHA384, AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 SHA-512 和 RSA 加密算法的签名算法，按 OSI 互操作性研讨会的定义，
   * 使用 PKCS #1 中描述的填充约定。
   */
  SHA512_WITH_RSA("SHA512withRSA", DigestAlgorithm.SHA512, AsymmetricCryptoAlgorithm.RSA),

  //  /**
  //   * The signature algorithm with SHA-512/224 and the RSA encryption algorithm as
  //   * defined in the OSI Interoperability Workshop, using the padding conventions
  //   * described in PKCS #1.
  //   */
  //  SHA512_224_WITH_RSA("SHA512/224withRSA", DigestAlgorithm.SHA512_224,
  //      AsymmetricCryptoAlgorithm.RSA),

  //  /**
  //   * The signature algorithm with SHA-512/256 and the RSA encryption algorithm as
  //   * defined in the OSI Interoperability Workshop, using the padding conventions
  //   * described in PKCS #1.
  //   */
  //  SHA512_256_WITH_RSA("SHA512/256withRSA", DigestAlgorithm.SHA512_256,
  //      AsymmetricCryptoAlgorithm.RSA),

  /**
   * 使用 SHA-1 摘要算法创建和验证数字签名的 DSA 签名算法，
   * 按 FIPS PUB 186-4 的定义。
   */
  SHA1_WITH_DSA("SHA1withDSA", DigestAlgorithm.SHA1, AsymmetricCryptoAlgorithm.DSA),

  /**
   * 使用 SHA-224 摘要算法创建和验证数字签名的 DSA 签名算法，
   * 按 FIPS PUB 186-4 的定义。
   */
  SHA224_WITH_DSA("SHA224withDSA", DigestAlgorithm.SHA224, AsymmetricCryptoAlgorithm.DSA),

  /**
   * 使用 SHA-256 摘要算法创建和验证数字签名的 DSA 签名算法，
   * 按 FIPS PUB 186-4 的定义。
   */
  SHA256_WITH_DSA("SHA256withDSA", DigestAlgorithm.SHA256, AsymmetricCryptoAlgorithm.DSA),

  //  /**
  //   * The DSA signature algorithms that use SHA-384 digest algorithms to create and
  //   * verify digital signatures as defined in FIPS PUB 186-4.
  //   */
  //  SHA384_WITH_DSA("SHA384withDSA", DigestAlgorithm.SHA384, AsymmetricCryptoAlgorithm.DSA),

  //  /**
  //   * The DSA signature algorithms that use SHA-512 digest algorithms to create and
  //   * verify digital signatures as defined in FIPS PUB 186-4.
  //   */
  //  SHA512_WITH_DSA("SHA512withDSA", DigestAlgorithm.SHA512, AsymmetricCryptoAlgorithm.DSA),

  /**
   * 使用 SHA-1 摘要算法创建和验证数字签名的 ECDSA 签名算法，
   * 按 ANSI X9.62 的定义。
   *
   * <p>
   * 注意："ECDSA" 是 "SHA1withECDSA" 算法的模糊名称，不应使用。
   * 应使用正式名称 "SHA1withECDSA"。
   * </p>
   */
  SHA1_WITH_ECDSA("SHA1withECDSA", DigestAlgorithm.SHA1, AsymmetricCryptoAlgorithm.EC),

  /**
   * 使用 SHA-224 摘要算法创建和验证数字签名的 ECDSA 签名算法，
   * 按 ANSI X9.62 的定义。
   *
   * <p>
   * 注意："ECDSA" 是 "SHA1withECDSA" 算法的模糊名称，不应使用。
   * 应使用正式名称 "SHA1withECDSA"。
   * </p>
   */
  SHA224_WITH_ECDSA("SHA224withECDSA", DigestAlgorithm.SHA224, AsymmetricCryptoAlgorithm.EC),

  /**
   * 使用 SHA-256 摘要算法创建和验证数字签名的 ECDSA 签名算法，
   * 按 ANSI X9.62 的定义。
   *
   * <p>
   * 注意："ECDSA" 是 "SHA1withECDSA" 算法的模糊名称，不应使用。
   * 应使用正式名称 "SHA1withECDSA"。
   * </p>
   */
  SHA256_WITH_ECDSA("SHA256withECDSA", DigestAlgorithm.SHA256, AsymmetricCryptoAlgorithm.EC),

  /**
   * 使用 SHA-384 摘要算法创建和验证数字签名的 ECDSA 签名算法，
   * 按 ANSI X9.62 的定义。
   *
   * <p>
   * 注意："ECDSA" 是 "SHA1withECDSA" 算法的模糊名称，不应使用。
   * 应使用正式名称 "SHA1withECDSA"。
   * </p>
   */
  SHA384_WITH_ECDSA("SHA384withECDSA", DigestAlgorithm.SHA384, AsymmetricCryptoAlgorithm.EC),

  /**
   * 使用 SHA-512 摘要算法创建和验证数字签名的 ECDSA 签名算法，
   * 按 ANSI X9.62 的定义。
   *
   * <p>
   * 注意："ECDSA" 是 "SHA1withECDSA" 算法的模糊名称，不应使用。
   * 应使用正式名称 "SHA1withECDSA"。
   * </p>
   */
  SHA512_WITH_ECDSA("SHA512withECDSA", DigestAlgorithm.SHA512, AsymmetricCryptoAlgorithm.EC);

  /**
   * 签名算法名称与枚举的映射。
   */
  private static final Map<String, SignatureAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final SignatureAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * 根据枚举名称或编码（即 JDK 中的标准名称）获取签名算法。
   *
   * @param nameOrCode
   *     指定签名算法的名称或编码，忽略大小写。
   * @return
   *     对应的 {@link SignatureAlgorithm}，如果没有这样的算法则返回 {@code null}。
   */
  public static SignatureAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  /**
   * 签名算法的编码，即 JDK 中的标准名称。
   */
  private final String code;

  /**
   * 签名算法的摘要算法。
   */
  private final DigestAlgorithm digestAlgorithm;

  /**
   * 签名算法的非对称加密算法。
   */
  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  /**
   * 构造一个签名算法。
   *
   * @param code
   *     签名算法的编码，即 JDK 中的标准名称。
   * @param digestAlgorithm
   *     签名算法的摘要算法。
   * @param cryptoAlgorithm
   *     签名算法的非对称加密算法。
   */
  SignatureAlgorithm(final String code,
      final DigestAlgorithm digestAlgorithm,
      final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.code = code;
    this.digestAlgorithm = digestAlgorithm;
    this.cryptoAlgorithm = cryptoAlgorithm;
  }

  /**
   * 获取此签名算法的编码，即 JDK 中的标准名称。
   *
   * @return
   *     此签名算法的编码，即 JDK 中的标准名称。
   */
  public String code() {
    return code;
  }

  /**
   * 获取此签名算法的摘要算法。
   *
   * @return
   *     此签名算法的摘要算法。
   */
  public DigestAlgorithm digestAlgorithm() {
    return digestAlgorithm;
  }

  /**
   * 获取此签名算法的非对称加密算法。
   *
   * @return
   *     此签名算法的非对称加密算法。
   */
  public AsymmetricCryptoAlgorithm cryptoAlgorithm() {
    return cryptoAlgorithm;
  }
}