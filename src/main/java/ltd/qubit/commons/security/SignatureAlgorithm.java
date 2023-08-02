////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * The enumeration of message signature algorithms supported by the JDK.
 *
 * @author Haixing Hu
 * @see DigestAlgorithm
 * @see AsymmetricCryptoAlgorithm
 * @see Signature
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecureRandom">
 *   SecureRandom Number Generation Algorithms</a>
 */
public enum SignatureAlgorithm {

  /**
   * The MD2 with RSA Encryption signature algorithm, which uses the MD2 digest
   * algorithm and RSA to create and verify RSA digital signatures as defined
   * in PKCS #1.
   */
  MD2_WITH_RSA("MD2withRSA", DigestAlgorithm.MD2, AsymmetricCryptoAlgorithm.RSA),

  /**
   * The MD5 with RSA Encryption signature algorithm, which uses the MD5 digest
   * algorithm and RSA to create and verify RSA digital signatures as defined
   * in PKCS #1.
   */
  MD5_WITH_RSA("MD5withRSA", DigestAlgorithm.MD5, AsymmetricCryptoAlgorithm.RSA),

  /**
   * The signature algorithm with SHA-1 and the RSA encryption algorithm as
   * defined in the OSI Interoperability Workshop, using the padding conventions
   * described in PKCS #1.
   */
  SHA1_WITH_RSA("SHA1withRSA", DigestAlgorithm.SHA1, AsymmetricCryptoAlgorithm.RSA),

  /**
   * The signature algorithm with SHA-224 and the RSA encryption algorithm as
   * defined in the OSI Interoperability Workshop, using the padding conventions
   * described in PKCS #1.
   */
  SHA224_WITH_RSA("SHA224withRSA", DigestAlgorithm.SHA224, AsymmetricCryptoAlgorithm.RSA),

  /**
   * The signature algorithm with SHA-256 and the RSA encryption algorithm as
   * defined in the OSI Interoperability Workshop, using the padding conventions
   * described in PKCS #1.
   */
  SHA256_WITH_RSA("SHA256withRSA", DigestAlgorithm.SHA256, AsymmetricCryptoAlgorithm.RSA),

  /**
   * The signature algorithm with SHA-384 and the RSA encryption algorithm as
   * defined in the OSI Interoperability Workshop, using the padding conventions
   * described in PKCS #1.
   */
  SHA384_WITH_RSA("SHA384withRSA", DigestAlgorithm.SHA384, AsymmetricCryptoAlgorithm.RSA),

  /**
   * The signature algorithm with SHA-512 and the RSA encryption algorithm as
   * defined in the OSI Interoperability Workshop, using the padding conventions
   * described in PKCS #1.
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
   * The DSA signature algorithms that use SHA-1 digest algorithms to create and
   * verify digital signatures as defined in FIPS PUB 186-4.
   */
  SHA1_WITH_DSA("SHA1withDSA", DigestAlgorithm.SHA1, AsymmetricCryptoAlgorithm.DSA),

  /**
   * The DSA signature algorithms that use SHA-224 digest algorithms to create and
   * verify digital signatures as defined in FIPS PUB 186-4.
   */
  SHA224_WITH_DSA("SHA224withDSA", DigestAlgorithm.SHA224, AsymmetricCryptoAlgorithm.DSA),

  /**
   * The DSA signature algorithms that use SHA-256 digest algorithms to create and
   * verify digital signatures as defined in FIPS PUB 186-4.
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
   * The ECDSA signature algorithms that use SHA-1 digest algorithms to create
   * and verify digital signatures as defined in ANSI X9.62.
   *
   * <p>
   * Note:"ECDSA" is an ambiguous name for the "SHA1withECDSA" algorithm and
   * should not be used. The formal name "SHA1withECDSA" should be used instead.
   * </p>
   */
  SHA1_WITH_ECDSA("SHA1withECDSA", DigestAlgorithm.SHA1, AsymmetricCryptoAlgorithm.EC),

  /**
   * The ECDSA signature algorithms that use SHA-224 digest algorithms to create
   * and verify digital signatures as defined in ANSI X9.62.
   *
   * <p>
   * Note:"ECDSA" is an ambiguous name for the "SHA1withECDSA" algorithm and
   * should not be used. The formal name "SHA1withECDSA" should be used instead.
   * </p>
   */
  SHA224_WITH_ECDSA("SHA224withECDSA", DigestAlgorithm.SHA224, AsymmetricCryptoAlgorithm.EC),

  /**
   * The ECDSA signature algorithms that use SHA-256 digest algorithms to create
   * and verify digital signatures as defined in ANSI X9.62.
   *
   * <p>
   * Note:"ECDSA" is an ambiguous name for the "SHA1withECDSA" algorithm and
   * should not be used. The formal name "SHA1withECDSA" should be used instead.
   * </p>
   */
  SHA256_WITH_ECDSA("SHA256withECDSA", DigestAlgorithm.SHA256, AsymmetricCryptoAlgorithm.EC),

  /**
   * The ECDSA signature algorithms that use SHA-384 digest algorithms to create
   * and verify digital signatures as defined in ANSI X9.62.
   *
   * <p>
   * Note:"ECDSA" is an ambiguous name for the "SHA1withECDSA" algorithm and
   * should not be used. The formal name "SHA1withECDSA" should be used instead.
   * </p>
   */
  SHA384_WITH_ECDSA("SHA384withECDSA", DigestAlgorithm.SHA384, AsymmetricCryptoAlgorithm.EC),

  /**
   * The ECDSA signature algorithms that use SHA-512 digest algorithms to create
   * and verify digital signatures as defined in ANSI X9.62.
   *
   * <p>
   * Note:"ECDSA" is an ambiguous name for the "SHA1withECDSA" algorithm and
   * should not be used. The formal name "SHA1withECDSA" should be used instead.
   * </p>
   */
  SHA512_WITH_ECDSA("SHA512withECDSA", DigestAlgorithm.SHA512, AsymmetricCryptoAlgorithm.EC);

  private static final Map<String, SignatureAlgorithm> NAME_MAP = new HashMap<>();
  static {
    for (final SignatureAlgorithm algorithm: values()) {
      NAME_MAP.put(algorithm.name().toUpperCase(), algorithm);
      NAME_MAP.put(algorithm.code().toUpperCase(), algorithm);
    }
  }

  /**
   * Gets the signature algorithm by its enumerator name or code (i.e., the
   * standard name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified signature algorithm, ignoring the case.
   * @return
   *     The corresponding {@link SignatureAlgorithm}, or {@code null} if no
   *     such algorithm.
   */
  public static SignatureAlgorithm forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;
  private final DigestAlgorithm digestAlgorithm;
  private final AsymmetricCryptoAlgorithm cryptoAlgorithm;

  SignatureAlgorithm(final String code,
      final DigestAlgorithm digestAlgorithm,
      final AsymmetricCryptoAlgorithm cryptoAlgorithm) {
    this.code = code;
    this.digestAlgorithm = digestAlgorithm;
    this.cryptoAlgorithm = cryptoAlgorithm;
  }

  /**
   * Gets the code of this signature algorithm, i.e., the standard name in the JDK.
   *
   * @return
   *     the code of this signature algorithm, i.e., the standard name in the JDK.
   */
  public String code() {
    return code;
  }

  /**
   * Gets the digest algorithm of this signature algorithm.
   *
   * @return
   *     the digest algorithm of this signature algorithm.
   */
  public DigestAlgorithm digestAlgorithm() {
    return digestAlgorithm;
  }

  /**
   * Gets the asymmetric crypto algorithm of this signature algorithm.
   *
   * @return
   *     the asymmetric crypto algorithm of this signature algorithm.
   */
  public AsymmetricCryptoAlgorithm cryptoAlgorithm() {
    return cryptoAlgorithm;
  }
}
