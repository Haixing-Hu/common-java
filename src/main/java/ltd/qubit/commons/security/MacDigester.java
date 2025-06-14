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
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.error.DigestMessageException;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The class of objects used to digest messages with Mac (Message Authentication Code).
 *
 * @author Haixing Hu
 * @see Mac
 * @see MacAlgorithm
 */
public class MacDigester {

  private static final String PBE_WITH_PREFIX = "PBEWith";

  /**
   * The default buffer size to use.
   */
  private static final int BUFFER_SIZE = 16384;

  private final MacAlgorithm algorithm;

  public MacDigester(final MacAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  public MacAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * Generates a random key used for signing the digest.
   *
   * @return
   *     a random key used for signing the digest.
   */
  public SecretKey generateKey() throws NoSuchAlgorithmException {
    final KeyGenerator generator = getKeyGenerator();
    return generator.generateKey();
  }

  private KeyGenerator getKeyGenerator() throws NoSuchAlgorithmException {
    final String name = getKeyGeneratorName();
    return KeyGenerator.getInstance(name);
  }

  private String getKeyGeneratorName() {
    final String code = algorithm.code();
    if (code.startsWith(PBE_WITH_PREFIX)) {
      // all PBEWithXXX algorithm should use the KeyGenerator for XXX algorithm
      return code.substring(PBE_WITH_PREFIX.length());
    } else {
      return code;
    }
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param key
   *     用于签名的密钥。
   * @param message
   *     指定的消息，以二进制流形式表示。注意此函数<b>不会</b>关闭这个输入流。
   * @return
   *     对指定的输入流计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final SecretKey key, final InputStream message)
      throws DigestMessageException {
    try {
      final Mac engine = Mac.getInstance(algorithm.code());
      engine.init(key);
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (true) {
        final int n = message.read(buffer);
        if (n < 0) {
          break;
        } else if (n > 0) {
          engine.update(buffer, 0, n);
        }
      }
      return engine.doFinal();
    } catch (final GeneralSecurityException | IOException e) {
      throw new DigestMessageException(e);
    }
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param key
   *     用于签名的密钥。
   * @param message
   *     指定的消息，以字节数组形式表示。
   * @return
   *     对指定的字节数组计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final SecretKey key, final byte[] message)
      throws DigestMessageException {
    try {
      final Mac engine = Mac.getInstance(algorithm.code());
      engine.init(key);
      engine.update(message);
      return engine.doFinal();
    } catch (final GeneralSecurityException e) {
      throw new DigestMessageException(e);
    }
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param key
   *     用于签名的密钥。
   * @param message
   *     指定的字符串消息。
   * @param charset
   *     指定的字符串消息的字符集编码。
   * @return
   *     对指定的字符串消息计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final SecretKey key, final String message,
      final Charset charset)
      throws DigestMessageException {
    return digest(key, message.getBytes(charset));
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param key
   *     用于签名的密钥。
   * @param message
   *     指定的字符串消息，以UTF-8编码。
   * @return
   *     对指定的字符串消息计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final SecretKey key, final String message)
      throws DigestMessageException {
    return digest(key, message, UTF_8);
  }

  /**
   * 对指定的数据消息计算数字摘要。
   * <p>
   * 此函数会首先将指定的数据进行正则化的 JSON 序列化，然后对序列化后的字符串计算数字摘
   * 要。
   *
   * @param <T>
   *     指定的数据的类型。
   * @param key
   *     用于签名的密钥。
   * @param data
   *     指定的数据。
   * @param mapper
   *     用于进行 JSON 序列化的 mapper.
   * @return
   *     对指定的数据的正则化 JSON 表示计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   * @see JsonMapperUtils#formatNormalized(Object, JsonMapper)
   */
  public <T> byte[] digest(final SecretKey key, final T data,
      final JsonMapper mapper) throws DigestMessageException {
    requireNonNull("data", data);
    requireNonNull("mapper", mapper);
    final String json = JsonMapperUtils.formatNormalizedNoThrow(data, mapper);
    if (json == null) {
      throw new DigestMessageException(data.toString());
    }
    return digest(key, json);
  }
}