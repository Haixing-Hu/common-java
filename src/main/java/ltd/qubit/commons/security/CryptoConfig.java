////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The class representing the cryptographic configuration.
 *
 * <p>This is the base class for {@link Encryptor} and {@link Decryptor}</p>.
 *
 * @author Haixing Hu
 */
public class CryptoConfig {

  private static final String WRAP_SUFFIX = "Wrap";

  protected final CryptoAlgorithm algorithm;

  protected final CryptoMode mode;

  protected final CryptoPadding padding;

  protected final transient Cipher cipher;

  protected CryptoConfig(final CryptoAlgorithm algorithm, final CryptoMode mode,
      final CryptoPadding padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
    this.algorithm = requireNonNull("algorithm", algorithm);
    this.mode = requireNonNull("mode", mode);
    this.padding = requireNonNull("padding", padding);
    final String cipherName = algorithm.code() + "/" + mode.code() + "/" + padding.code();
    this.cipher = Cipher.getInstance(cipherName);
  }

  public CryptoAlgorithm getAlgorithm() {
    return algorithm;
  }

  public CryptoMode getMode() {
    return mode;
  }

  public CryptoPadding getPadding() {
    return padding;
  }

  /**
   * Tests whether the cryptographic configuration has cryptographic parameters.
   *
   * @return
   *     {@code true} if the configuration of this decryptor has cryptographic
   *     parameters; {@code false} otherwise.
   */
  public boolean hasParameters() {
    switch (algorithm) {
      case AES:
      case DES_EDE:
      case BLOWFISH:
        switch (mode) {
          case CFB:
          case CFB8:
          case CFB16:
          case CFB32:
          case CFB64:
          case CFB128:
          case CFB256:
          case CFB512:
          case CFB1024:
          case OFB:
          case OFB8:
          case OFB16:
          case OFB32:
          case OFB64:
          case OFB128:
          case OFB256:
          case OFB512:
          case OFB1024:
          case CBC:
          case CTR:
          case PCBC:
            return true;
          default:
            return false;
        }
      default:
        return false;
    }
  }

  /**
   * Gets the key generator of this algorithm.
   *
   * @return
   *     the key generator of this algorithm.
   * @throws NoSuchAlgorithmException
   *     if this algorithm is not supported by the system.
   */
  protected KeyGenerator getKeyGenerator() throws NoSuchAlgorithmException {
    final String name = getKeyGeneratorName();
    return KeyGenerator.getInstance(name);
  }

  protected String getKeyGeneratorName() {
    final String code = algorithm.code();
    if (code.endsWith(WRAP_SUFFIX)) {
      return code.substring(0, code.length() - WRAP_SUFFIX.length());
    } else {
      return code;
    }
  }

  /**
   * Get the opaque cryptographic parameters of this algorithm.
   *
   * @param parameters
   *     the cryptographic parameters encoded in the primary encoding format
   *     for parameters of this algorithm.  The primary encoding format for
   *     parameters is ASN.1, if an ASN.1 specification for this type of
   *     parameters exists.
   * @return
   *     the corresponding opaque cryptographic parameters of this algorithm.
   * @throws NoSuchAlgorithmException
   *     if this algorithm is not supported by the system.
   * @throws IOException
   *     if any I/O error occurs while decoding the parameters.
   */
  protected AlgorithmParameters getAlgorithmParameters(final byte[] parameters)
      throws NoSuchAlgorithmException, IOException {
    requireNonNull("parameters", parameters);
    final String code = algorithm.code();
    final AlgorithmParameters ap = AlgorithmParameters.getInstance(code);
    ap.init(parameters);
    return ap;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Encryptor other = (Encryptor) o;
    return Equality.equals(algorithm, other.algorithm)
        && Equality.equals(mode, other.mode)
        && Equality.equals(padding, other.padding);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, algorithm);
    result = Hash.combine(result, multiplier, mode);
    result = Hash.combine(result, multiplier, padding);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("algorithm", algorithm)
        .append("mode", mode)
        .append("padding", padding)
        .toString();
  }
}
