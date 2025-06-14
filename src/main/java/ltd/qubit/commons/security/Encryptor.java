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

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.error.EncryptException;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于加密消息的对象类。
 *
 * <p>此类封装了 {@link Cipher} 类，提供具有默认配置的便捷加密方法。</p>
 *
 * @author 胡海星
 * @see Cipher
 */
public class Encryptor extends CryptoConfig {

  /**
   * 构造一个加密器。
   *
   * @param algorithm
   *     加密算法。
   * @throws NoSuchPaddingException
   *     如果填充方案不可用。
   * @throws NoSuchAlgorithmException
   *     如果加密算法不可用。
   */
  public Encryptor(final CryptoAlgorithm algorithm)
      throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, CryptoMode.NONE, CryptoPadding.NONE);
  }

  /**
   * 构造一个加密器。
   *
   * @param algorithm
   *     加密算法。
   * @param mode
   *     加密模式。
   * @param padding
   *     填充方案。
   * @throws NoSuchPaddingException
   *     如果填充方案不可用。
   * @throws NoSuchAlgorithmException
   *     如果加密算法不可用。
   */
  public Encryptor(final CryptoAlgorithm algorithm, final CryptoMode mode,
      final CryptoPadding padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, mode, padding);
  }

  /**
   * 获取此加密器用于加密消息的算法参数。
   *
   * @return
   *     此加密器用于加密消息的算法参数，以该算法参数的主要编码格式进行编码。
   *     参数的主要编码格式是 ASN.1，前提是此类型的参数存在 ASN.1 规范。
   * @throws EncryptException
   *     如果发生任何加密错误。
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
   * 加密消息。
   *
   * @param key
   *     用于加密的密钥。
   * @param message
   *     要加密的消息。
   * @return
   *     加密后的消息。
   * @throws EncryptException
   *     如果发生任何加密错误。
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
   * 加密消息。
   *
   * @param key
   *     用于加密的密钥。
   * @param message
   *     要加密的消息。
   * @param charset
   *     消息的字符集编码。
   * @return
   *     加密后的消息。
   * @throws EncryptException
   *     如果发生任何加密错误。
   */
  public byte[] encrypt(final Key key, final String message, final Charset charset)
      throws EncryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    return encrypt(key, message.getBytes(charset));
  }

  /**
   * 加密消息。
   *
   * @param key
   *     用于加密的密钥。
   * @param message
   *     要加密的消息，使用 UTF-8 编码。
   * @return
   *     加密后的消息。
   * @throws EncryptException
   *     如果发生任何加密错误。
   */
  public byte[] encrypt(final Key key, final String message)
      throws EncryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    return encrypt(key, message.getBytes(UTF_8));
  }

  /**
   * 加密指定的数据。
   *
   * @param <T>
   *     要加密的数据类型。
   * @param key
   *     用于加密的密钥。
   * @param data
   *     要加密的数据，将被编码为标准化的 JSON 字符串，然后进行加密。
   * @param mapper
   *     用于将指定数据编码为标准化 JSON 字符串的 JSON 映射器。
   * @return
   *     加密后的数据。
   * @throws EncryptException
   *     如果发生任何加密错误。
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
   * 加密数据流。
   *
   * @param key
   *     用于加密的密钥。
   * @param input
   *     指定的输入流。
   * @return
   *     一个 {@link CipherInputStream}，其 read() 方法返回从底层 {@link InputStream}
   *     {@code input} 读取的数据，但已由指定密钥加密。
   * @throws EncryptException
   *     如果发生任何加密错误。
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
   * 加密数据并写入输出流。
   *
   * @param key
   *     用于加密的密钥。
   * @param output
   *     指定的输出流。
   * @return
   *     一个 {@link CipherOutputStream}，其 write() 方法在写入底层
   *     {@link OutputStream} {@code output} 之前使用指定密钥加密数据。
   * @throws EncryptException
   *     如果发生任何加密错误。
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

  @Override
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

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .toString();
  }
}