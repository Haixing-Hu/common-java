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
 * 用于解密消息的对象类。

 * <p>此类封装了{@link Cipher}类，以提供带有默认配置的便捷解密方法。</p>
 *
 * @author 胡海星
 * @see Cipher
 */
public class Decryptor extends CryptoConfig {

  /**
   * 加密参数。
   */
  @Nullable
  private transient byte[] parameters;

  /**
   * 构造一个{@link Decryptor}。
   *
   * @param algorithm
   *     加密算法。
   * @throws NoSuchPaddingException
   *     如果不支持指定的填充方式。
   * @throws NoSuchAlgorithmException
   *     如果不支持指定的算法。
   */
  public Decryptor(final CryptoAlgorithm algorithm)
      throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, CryptoMode.NONE, CryptoPadding.NONE);
  }

  /**
   * 构造一个{@link Decryptor}。
   *
   * @param algorithm
   *     加密算法。
   * @param mode
   *     加密模式。
   * @param padding
   *     填充方式。
   * @throws NoSuchPaddingException
   *     如果不支持指定的填充方式。
   * @throws NoSuchAlgorithmException
   *     如果不支持指定的算法。
   */
  public Decryptor(final CryptoAlgorithm algorithm, final CryptoMode mode,
      final CryptoPadding padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
    super(algorithm, mode, padding);
  }

  /**
   * 设置用于解密消息的编码加密参数。
   *
   * @param parameters
   *     用于解密消息的加密参数，必须与加密消息后从{@link Encryptor}获取的编码加密参数相同。
   *     {@code null}值表示不需要参数。参数以算法参数的主要编码格式编码。
   *     如果存在此类型参数的ASN.1规范，则参数的主要编码格式是ASN.1。
   */
  public void setParameters(@Nullable final byte[] parameters) {
    this.parameters = parameters;
  }

  /**
   * 初始化密码器。
   *
   * @param key
   *     用于解密的密钥。
   * @throws GeneralSecurityException
   *     如果发生安全错误。
   * @throws IOException
   *     如果发生I/O错误。
   */
  private void initCipher(final Key key) throws GeneralSecurityException, IOException {
    if (parameters == null) {
      cipher.init(Cipher.DECRYPT_MODE, key);
    } else {
      final AlgorithmParameters ap = getAlgorithmParameters(parameters);
      cipher.init(Cipher.DECRYPT_MODE, key, ap);
    }
  }

  /**
   * 解密消息。
   *
   * @param key
   *     用于解密的密钥。
   * @param message
   *     要解密的消息。
   * @return
   *     解密后的消息。
   * @throws DecryptException
   *     如果发生任何解密错误。
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
   * 解密消息。
   *
   * @param key
   *     用于解密的密钥。
   * @param message
   *     要解密的消息。
   * @param charset
   *     编码消息的字符集。
   * @return
   *     解密后的消息。
   * @throws DecryptException
   *     如果发生任何解密错误。
   */
  public byte[] decrypt(final Key key, final String message, final Charset charset)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    return decrypt(key, message.getBytes(charset));
  }

  /**
   * 解密消息。
   *
   * @param key
   *     用于解密的密钥。
   * @param message
   *     要解密的消息，使用UTF_8编码。
   * @return
   *     解密后的消息。
   * @throws DecryptException
   *     如果发生任何解密错误。
   */
  public byte[] decrypt(final Key key, final String message)
      throws DecryptException {
    requireNonNull("key", key);
    requireNonNull("message", message);
    return decrypt(key, message.getBytes(UTF_8));
  }

  /**
   * 解密指定的数据。
   *
   * @param <T>
   *     要解密的数据类型。
   * @param key
   *     用于解密的密钥。
   * @param data
   *     要解密的数据，将被编码为标准化JSON字符串然后解密。
   * @param mapper
   *     用于将指定数据编码为标准化JSON字符串的JSON映射器。
   * @return
   *     解密后的数据。
   * @throws DecryptException
   *     如果发生任何解密错误。
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
   * 解密数据流。
   *
   * @param key
   *     用于解密的密钥。
   * @param input
   *     指定的输入流。
   * @return
   *     一个{@link CipherInputStream}，其read()方法返回从底层{@link InputStream} {@code input}
   *     读取但已用指定密钥解密的数据。
   * @throws DecryptException
   *     如果发生任何解密错误。
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
   * 解密数据并写入输出流。
   *
   * @param key
   *     用于解密的密钥。
   * @param output
   *     指定的输出流。
   * @return
   *     一个{@link CipherOutputStream}，其write()方法在写入底层{@link OutputStream} {@code output}
   *     之前用指定密钥解密数据。
   * @throws DecryptException
   *     如果发生任何解密错误。
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

  @Override
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