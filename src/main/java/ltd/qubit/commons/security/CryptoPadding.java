////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.util.HashMap;
import java.util.Map;

/**
 * The enumeration of crypto padding schema supported by the JDK.
 * <p>
 * There are two major types of ciphers: block and stream. Block ciphers process
 * entire blocks at a time, usually many bytes in length. If there is not enough
 * data to make a complete input block, the data must be padded: that is, before
 * encryption, dummy bytes must be added to make a multiple of the cipher's
 * block size. These bytes are then stripped off during the decryption phase.
 * The padding can either be done by the application, or by initializing a
 * cipher to use a padding type such as "PKCS5PADDING". In contrast, stream
 * ciphers process incoming data one small unit (typically a byte or even a bit)
 * at a time. This allows for ciphers to process an arbitrary amount of data
 * without padding.
 *
 * @author Haixing Hu
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">Stream vs. Block Ciphers</a>
 */
public enum CryptoPadding {

  /**
   * No padding.
   */
  NONE("NoPadding"),

  /**
   * This padding for block ciphers is described in 5.2 Block Encryption
   * Algorithms in the W3C's "XML Encryption Syntax and Processing" document.
   */
  ISO_10126("ISO10126Padding"),

  /**
   * The padding scheme described in PKCS #1 v2.2, used with the RSA algorithm.
   */
  PKCS1("PKCS1Padding"),

  /**
   * The padding scheme described in PKCS #5: Password-Based Cryptography
   * Specification, version 2.1.
   */
  PKCS5("PKCS5Padding"),

  /**
   * The padding scheme defined in the SSL Protocol Version 3.0, November 18,
   * 1996, section 5.2.3.2 (CBC block cipher).
   * <p>
   * The size of an instance of a GenericBlockCipher must be a multiple of the
   * block cipher's block length.
   * <p>
   * The padding length, which is always present, contributes to the padding,
   * which implies that if:
   *
   * <pre><code>
   * sizeof(content) + sizeof(MAC) % block_length = 0,
   * </code></pre>
   *
   * <p>
   * padding has to be (block_length - 1) bytes long, because of the existence
   * of padding_length.
   * <p>
   * This makes the padding scheme similar (but not quite) to PKCS5Padding,
   * where the padding length is encoded in the padding (and ranges from 1 to
   * block_length). With the SSL scheme, the sizeof(padding) is encoded in the
   * always present padding_length and therefore ranges from 0 to
   * block_length-1.
   */
  SSL3("SSL3Padding");

  private static final Map<String, CryptoPadding> NAME_MAP = new HashMap<>();
  static {
    for (final CryptoPadding padding: values()) {
      NAME_MAP.put(padding.name().toUpperCase(), padding);
      NAME_MAP.put(padding.code().toUpperCase(), padding);
    }
  }

  /**
   * Gets the crypto padding schema by its enumerator name or code (i.e., the
   * standard name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified crypto padding schema, ignoring the case.
   * @return
   *     The corresponding {@link CryptoPadding}, or {@code null} if no such
   *     padding schema.
   */
  public static CryptoPadding forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  CryptoPadding(final String code) {
    this.code = code;
  }

  /**
   * Gets the code of this crypto padding schema, i.e., the standard name in
   * the JDK.
   *
   * @return
   *     the code of this crypto padding schema, i.e., the standard name in
   *     the JDK.
   */
  String code() {
    return code;
  }
}
