////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
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

import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.error.DecryptException;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A class of objects used to decrypt messages.

 * <p>This class encapsulates the {@link Cipher} class to provide convenient
 * decryption method with default configuration.</p>
 *
 * @author Haixing Hu
 * @see Cipher
 */
public class Decryptor extends CryptoConfig {

  @Nullable
  private transient byte[] parameters;

  public Decryptor(final CryptoAlgorithm algorithm)
      throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, CryptoMode.NONE, CryptoPadding.NONE);
  }

  public Decryptor(final CryptoAlgorithm algorithm, final CryptoMode mode,
      final CryptoPadding padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, mode, padding);
  }

  /**
   * Sets encoded cryptographic parameters used to decrypt the message.
   *
   * @param parameters
   *     the cryptographic parameters used to decrypt the message, which
   *     must be the same as the encoded cryptographic parameters get from the
   *     {@link Encryptor} after encrypting the message. A {@code null} value
   *     indicates no parameters required. The parameters are encoded in the
   *     primary encoding format for parameters of the algorithm. The primary
   *     encoding format for parameters is ASN.1, if an ASN.1 specification for
   *     this type of parameters exists.
   */
  public void setParameters(@Nullable final byte[] parameters) {
    this.parameters = parameters;
  }

  private void initCipher(final Key key) throws GeneralSecurityException, IOException {
    if (parameters == null) {
      cipher.init(Cipher.DECRYPT_MODE, key);
    } else {
      final AlgorithmParameters ap = getAlgorithmParameters(parameters);
      cipher.init(Cipher.DECRYPT_MODE, key, ap);
    }
  }

  /**
   * Decrypt the message.
   *
   * @param key
   *     the key used to decrypt.
   * @param message
   *     the message to be decrypted.
   * @return
   *     the decrypted message.
   * @throws DecryptException
   *     if any decryption error occurs.
   */
  public byte[] decrypt(final Key key, final byte[] message)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    try {
      initCipher(key);
      cipher.update(message);
      return cipher.doFinal();
    } catch (final GeneralSecurityException | IOException e) {
      throw new DecryptException(e);
    }
  }

  /**
   * Decrypt the message.
   *
   * @param key
   *     the key used to decrypt.
   * @param message
   *     the message to be decrypted.
   * @param charset
   *     the charset encoding the message.
   * @return
   *     the decrypted message.
   * @throws DecryptException
   *     if any decryption error occurs.
   */
  public byte[] decrypt(final Key key, final String message, final Charset charset)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    return decrypt(key, message.getBytes(charset));
  }

  /**
   * Decrypt the message.
   *
   * @param key
   *     the key used to decrypt.
   * @param message
   *     the message to be decrypted, which is encoded with UTF_8.
   * @return
   *     the decrypted message.
   * @throws DecryptException
   *     if any decryption error occurs.
   */
  public byte[] decrypt(final Key key, final String message)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    return decrypt(key, message.getBytes(UTF_8));
  }

  /**
   * Decrypt the specified data.
   *
   * @param <T>
   *     the type of the data to be decrypted.
   * @param key
   *     the key used to decrypt.
   * @param data
   *     the data to be decrypted, which will be encoded as normalized JSON string
   *     and then be decrypted.
   * @param mapper
   *     the JSON mapper used to encode the specified data into the normalized
   *     JSON string.
   * @return
   *     the decrypted data.
   * @throws DecryptException
   *     if any decryption error occurs.
   */
  public <T> byte[] decrypt(final Key key, final T data, final JsonMapper mapper)
      throws DecryptException {
    requireNonNull("data", data);
    requireNonNull("mapper", mapper);
    final String json = JsonMapperUtils.formatNormalizedNoThrow(data, mapper);
    if (json == null) {
      throw new DecryptException(data.toString());
    }
    return decrypt(key, json.getBytes(UTF_8));
  }

  /**
   * Decrypt a stream of data.
   *
   * @param key
   *     the key used to decrypt.
   * @param input
   *     the specified input stream.
   * @return
   *     a {@link CipherInputStream}, whose read() methods return data that are
   *     read in from the underlying {@link InputStream} {@code input} but have
   *     been decrypted by the specified key.
   * @throws DecryptException
   *     if any decryption error occurs.
   */
  public CipherInputStream decrypt(final Key key, final InputStream input)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("input", input);
    try {
      initCipher(key);
      return new CipherInputStream(input, cipher);
    } catch (final GeneralSecurityException | IOException e) {
      throw new DecryptException(e);
    }
  }

  /**
   * Decrypt data and write to an output stream.
   *
   * @param key
   *     the key used to decrypt.
   * @param output
   *     the specified output stream.
   * @return
   *     a {@link CipherOutputStream}, whose write() methods decrypt data with
   *     the specified key before writing into the underlying
   *     {@link OutputStream} {@code output}.
   * @throws DecryptException
   *     if any decryption error occurs.
   */
  public CipherOutputStream decrypt(final Key key, final OutputStream output)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("output", output);
    try {
      initCipher(key);
      return new CipherOutputStream(output, cipher);
    } catch (final GeneralSecurityException | IOException e) {
      throw new DecryptException(e);
    }
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Decryptor other = (Decryptor) o;
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