////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import ltd.qubit.commons.error.EncryptException;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import com.fasterxml.jackson.databind.json.JsonMapper;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A class of object used to encrypt messages.
 *
 * <p>This class encapsulates the {@link Cipher} class to provide convenient
 * encryption method with default configuration.</p>
 *
 * @author Haixing Hu
 * @see Cipher
 */
public class Encryptor extends CryptoConfig {

  public Encryptor(final CryptoAlgorithm algorithm)
      throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, CryptoMode.NONE, CryptoPadding.NONE);
  }

  public Encryptor(final CryptoAlgorithm algorithm, final CryptoMode mode,
      final CryptoPadding padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, mode, padding);
  }

  /**
   * Gets the algorithm parameters used by this encryptor for encrypting messages.
   *
   * @return
   *     the algorithm parameters used by this encryptor for encrypting messages,
   *     encoded in the primary encoding format for parameters of the algorithm.
   *     The primary encoding format for parameters is ASN.1, if an ASN.1
   *     specification for this type of parameters exists.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public byte[] getParameters() throws EncryptException {
    final AlgorithmParameters parameters = cipher.getParameters();
    try {
      return parameters.getEncoded();
    } catch (final IOException e) {
      throw new EncryptException(e);
    }
  }

  /**
   * Encrypt the message.
   *
   * @param key
   *     the key used to encrypt.
   * @param message
   *     the message to be encrypted.
   * @return
   *     the encrypted message.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public byte[] encrypt(final Key key, final byte[] message)
      throws EncryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      cipher.update(message);
      return cipher.doFinal();
    } catch (final GeneralSecurityException e) {
      throw new EncryptException(e);
    }
  }

  /**
   * Encrypt the message.
   *
   * @param key
   *     the key used to encrypt.
   * @param message
   *     the message to be encrypted.
   * @param charset
   *     the charset encoding the message.
   * @return
   *     the encrypted message.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public byte[] encrypt(final Key key, final String message, final Charset charset)
      throws EncryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    return encrypt(key, message.getBytes(charset));
  }

  /**
   * Encrypt the message.
   *
   * @param key
   *     the key used to encrypt.
   * @param message
   *     the message to be encrypted, which is encoded with UTF_8.
   * @return
   *     the encrypted message.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public byte[] encrypt(final Key key, final String message)
      throws EncryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    return encrypt(key, message.getBytes(UTF_8));
  }

  /**
   * Encrypt the specified data.
   *
   * @param <T>
   *     the type of the data to be encrypted.
   * @param key
   *     the key used to encrypt.
   * @param data
   *     the data to be encrypted, which will be encoded as normalized JSON string
   *     and then be encrypted.
   * @param mapper
   *     the JSON mapper used to encode the specified data into the normalized
   *     JSON string.
   * @return
   *     the encrypted data.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public <T> byte[] encrypt(final Key key, final T data, final JsonMapper mapper)
      throws EncryptException {
    requireNonNull("data", data);
    requireNonNull("mapper", mapper);
    final String json = JsonMapperUtils.formatNormalizedNoThrow(data, mapper);
    if (json == null) {
      throw new EncryptException(data.toString());
    }
    return encrypt(key, json.getBytes(UTF_8));
  }

  /**
   * Encrypt a stream of data.
   *
   * @param key
   *     the key used to encrypt.
   * @param input
   *     the specified input stream.
   * @return
   *     a {@link CipherInputStream}, whose read() methods return data that are
   *     read in from the underlying {@link InputStream} {@code input} but have
   *     been encrypted by the specified key.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public CipherInputStream encrypt(final Key key, final InputStream input)
      throws EncryptException {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return new CipherInputStream(input, cipher);
    } catch (final GeneralSecurityException e) {
      throw new EncryptException(e);
    }
  }

  /**
   * Encrypt data and write to an output stream.
   *
   * @param key
   *     the key used to encrypt.
   * @param output
   *     the specified output stream.
   * @return
   *     a {@link CipherOutputStream}, whose write() methods encrypt data with
   *     the specified key before writing into the underlying
   *     {@link OutputStream} {@code output}.
   * @throws EncryptException
   *     if any encryption error occurs.
   */
  public CipherOutputStream encrypt(final Key key, final OutputStream output)
      throws EncryptException {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return new CipherOutputStream(output, cipher);
    } catch (final GeneralSecurityException e) {
      throw new EncryptException(e);
    }
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Encryptor other = (Encryptor) o;
    return super.equals(other);
  }

  public int hashCode() {
    return super.hashCode();
  }

  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .toString();
  }
}
