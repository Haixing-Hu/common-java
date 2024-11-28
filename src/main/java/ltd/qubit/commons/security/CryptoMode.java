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
 * The enumeration of crypto modes supported by the JDK.
 * <p>
 * When encrypting using a simple block cipher, two identical blocks of
 * plaintext will always produce an identical block of cipher text.
 * Cryptanalysts trying to break the ciphertext will have an easier job if they
 * note blocks of repeating text. In order to add more complexity to the text,
 * feedback modes use the previous block of output to alter the input blocks
 * before applying the encryption algorithm. The first block will need an
 * initial value, and this value is called the initialization vector (IV). Since
 * the IV simply alters the data before any encryption, the IV should be random
 * but does not necessarily need to be kept secret. There are a variety of
 * modes, such as CBC (Cipher Block Chaining), CFB (Cipher Feedback Mode), and
 * OFB (Output Feedback Mode). ECB (Electronic Codebook Mode) is a mode in which
 * there is no influence from block position or other ciphertext blocks. Because
 * ECB ciphertexts are the same if they use the same plaintext/key, this mode is
 * not typically suitable for cryptographic applications and should not be
 * used.
 * <p>
 * Some algorithms such as AES and RSA allow for keys of different lengths, but
 * others are fixed, such as 3DES. Encryption using a longer key generally
 * implies a stronger resistance to message recovery. As usual, there is a trade
 * off between security and time, so choose the key length appropriately.
 * <p>
 * Most algorithms use binary keys. Most humans do not have the ability to
 * remember long sequences of binary numbers, even when represented in
 * hexadecimal. Character passwords are much easier to recall. Because character
 * passwords are generally chosen from a small number of characters (for
 * example, [a-zA-Z0-9]), protocols such as "Password-Based Encryption" (PBE)
 * have been defined which take character passwords and generate strong binary
 * keys. In order to make the task of getting from password to key very
 * time-consuming for an attacker (via so-called "dictionary attacks" where
 * common dictionary word->value mappings are precomputed), most PBE
 * implementations will mix in a random number, known as a salt, to increase the
 * key randomness.
 * <p>
 * Newer cipher modes such as Authenticated Encryption with Associated Data
 * (AEAD) (for example, Galois/Counter Mode (GCM)) encrypt data and authenticate
 * the resulting message simultaneously. Additional Associated Data (AAD) can be
 * used during the calculation of the resulting AEAD tag (Mac), but this AAD
 * data is not output as ciphertext. (For example, some data might not need to
 * be kept confidential, but should figure into the tag calculation to detect
 * modifications.) The Cipher.updateAAD() methods can be used to include AAD in
 * the tag calculations.
 *
 * @author Haixing Hu
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#Cipher">Modes Of Operation</a>
 */
public enum CryptoMode {
  /**
   * No mode.
   */
  NONE("NONE"),

  /**
   * Cipher Block Chaining Mode, as defined in FIPS PUB 81.
   */
  CBC("CBC"),

  //  /**
  //   * Counter/CBC Mode, as defined in NIST Special Publication SP 800-38C:
  //   * Recommendation for Block Cipher Modes of Operation: the CCM Mode for
  //   * Authentication and Confidentiality.
  //   */
  //  CCM("CCM"), // NOT SUPPORTED

  /**
   * Cipher Feedback Mode, as defined in FIPS PUB 81.
   * <p>
   * Using modes such as CFB and OFB, block ciphers can encrypt data in units
   * smaller than the cipher's actual block size. When requesting such a mode,
   * you may optionally specify the number of bits to be processed at a time by
   * appending this number to the mode name as shown in the "DES/CFB8/NoPadding"
   * and "DES/OFB32/PKCS5Padding" transformations. If no such number is
   * specified, a provider-specific default is used. (For example, the SunJCE
   * provider uses a default of 64 bits for DES.) Thus, block ciphers can be
   * turned into byte-oriented stream ciphers by using an 8-bit mode such as
   * CFB8 or OFB8.
   */
  CFB("CFB"),

  /**
   * Cipher Feedback Mode with block size of 8 bits.
   */
  CFB8("CFB8"),

  /**
   * Cipher Feedback Mode with block size of 16 bits.
   */
  CFB16("CFB16"),

  /**
   * Cipher Feedback Mode with block size of 32 bits.
   */
  CFB32("CFB32"),

  /**
   * Cipher Feedback Mode with block size of 64 bits.
   */
  CFB64("CFB64"),

  /**
   * Cipher Feedback Mode with block size of 128 bits.
   */
  CFB128("CFB128"),

  /**
   * Cipher Feedback Mode with block size of 256 bits.
   */
  CFB256("CFB256"),

  /**
   * Cipher Feedback Mode with block size of 512 bits.
   */
  CFB512("CFB512"),

  /**
   * Cipher Feedback Mode with block size of 1024 bits.
   */
  CFB1024("CFB1024"),

  /**
   * A simplification of OFB, Counter mode updates the input block as a counter.
   */
  CTR("CTR"),

  /**
   * Cipher Text Stealing, as described in Bruce Schneier's book Applied
   * Cryptography-Second Edition, John Wiley and Sons, 1996.
   */
  CTS("CTS"),

  /**
   * Electronic Codebook Mode, as defined in FIPS PUB 81 (generally this mode
   * should not be used for multiple blocks of data).
   */
  ECB("ECB"),

  //  /**
  //   * Galois/Counter Mode, as defined in NIST Special Publication SP 800-38D
  //   * Recommendation for Block Cipher Modes of Operation: Galois/Counter Mode
  //   * (GCM) and GMAC.
  //   */
  //  GCM("GCM"),   // NOT SUPPORTED

  /**
   * Output Feedback Mode, as defined in FIPS PUB 81.
   * <p>
   * Using modes such as CFB and OFB, block ciphers can encrypt data in units
   * smaller than the cipher's actual block size. When requesting such a mode,
   * you may optionally specify the number of bits to be processed at a time by
   * appending this number to the mode name as shown in the "DES/CFB8/NoPadding"
   * and "DES/OFB32/PKCS5Padding" transformations. If no such number is
   * specified, a provider-specific default is used. (For example, the SunJCE
   * provider uses a default of 64 bits for DES.) Thus, block ciphers can be
   * turned into byte-oriented stream ciphers by using an 8-bit mode such as
   * CFB8 or OFB8.
   */
  OFB("OFB"),

  /**
   * Output Feedback Mode with the block size of 8 bits.
   */
  OFB8("OFB8"),

  /**
   * Output Feedback Mode with the block size of 16 bits.
   */
  OFB16("OFB16"),

  /**
   * Output Feedback Mode with the block size of 32 bits.
   */
  OFB32("OFB32"),

  /**
   * Output Feedback Mode with the block size of 64 bits.
   */
  OFB64("OFB64"),

  /**
   * Output Feedback Mode with the block size of 128 bits.
   */
  OFB128("OFB128"),

  /**
   * Output Feedback Mode with the block size of 256 bits.
   */
  OFB256("OFB256"),

  /**
   * Output Feedback Mode with the block size of 512 bits.
   */
  OFB512("OFB512"),

  /**
   * Output Feedback Mode with the block size of 1024 bits.
   */
  OFB1024("OFB1024"),

  /**
   * Propagating Cipher Block Chaining, as defined by Kerberos V4.
   */
  PCBC("PCBC");

  private static final Map<String, CryptoMode> NAME_MAP = new HashMap<>();
  static {
    for (final CryptoMode mode: values()) {
      NAME_MAP.put(mode.name().toUpperCase(), mode);
      NAME_MAP.put(mode.code().toUpperCase(), mode);
    }
  }

  /**
   * Gets the crypto mode by its enumerator name or code (i.e., the standard
   * name in JDK).
   *
   * @param nameOrCode
   *     The name or code of the specified crypto mode, ignoring the case.
   * @return
   *     The corresponding {@link CryptoMode}, or {@code null} if no such mode.
   */
  public static CryptoMode forName(final String nameOrCode) {
    return NAME_MAP.get(nameOrCode.toUpperCase());
  }

  private final String code;

  CryptoMode(final String code) {
    this.code = code;
  }

  /**
   * Gets the code of this crypto mode, i.e., the standard name in the JDK.
   *
   * @return
   *     the code of this crypto mode, i.e., the standard name in the JDK.
   */
  String code() {
    return code;
  }
}
