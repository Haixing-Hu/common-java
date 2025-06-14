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
import java.security.MessageDigest;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.error.DigestMessageException;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于消息摘要的对象类。
 *
 * @author 胡海星
 * @see MessageDigest
 * @see DigestAlgorithm
 */
public class Digester {

  /**
   * 默认使用的缓冲区大小。
   */
  private static final int BUFFER_SIZE = 16384;

  /**
   * 摘要算法。
   */
  private final DigestAlgorithm algorithm;

  /**
   * 构造一个{@link Digester}。
   *
   * @param algorithm
   *     摘要算法。
   */
  public Digester(final DigestAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  /**
   * 获取摘要算法。
   *
   * @return 摘要算法。
   */
  public DigestAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param message
   *     指定的消息，以二进制流形式表示。注意此函数<b>不会</b>关闭这个输入流。
   * @return
   *     对指定的输入流计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final InputStream message)
      throws DigestMessageException {
    try {
      final MessageDigest engine = MessageDigest.getInstance(algorithm.code());
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (true) {
        final int n = message.read(buffer);
        if (n < 0) {
          break;
        } else if (n > 0) {
          engine.update(buffer, 0, n);
        }
      }
      return engine.digest();
    } catch (final GeneralSecurityException | IOException e) {
      throw new DigestMessageException(e);
    }
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param message
   *     指定的消息，以字节数组形式表示。
   * @return
   *     对指定的字节数组计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final byte[] message)
      throws DigestMessageException {
    try {
      final MessageDigest engine = MessageDigest.getInstance(algorithm.code());
      engine.update(message);
      return engine.digest();
    } catch (final GeneralSecurityException e) {
      throw new DigestMessageException(e);
    }
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param message
   *     指定的字符串消息。
   * @param charset
   *     指定的字符串消息的字符集编码。
   * @return
   *     对指定的字符串消息计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final String message, final Charset charset)
      throws DigestMessageException {
    return digest(message.getBytes(charset));
  }

  /**
   * 对指定的消息计算数字摘要。
   *
   * @param message
   *     指定的字符串消息，以UTF-8编码。
   * @return
   *     对指定的字符串消息计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   */
  public byte[] digest(final String message)
      throws DigestMessageException {
    return digest(message, UTF_8);
  }

  /**
   * 对指定的数据消息计算数字摘要。
   * <p>
   * 此函数会首先将指定的数据进行正则化的JSON序列化，然后对序列化后的字符串计算数字摘要。
   *
   * @param <T>
   *     指定的数据的类型。
   * @param data
   *     指定的数据。
   * @param mapper
   *     用于进行JSON序列化的mapper。
   * @return
   *     对指定的数据的正则化JSON表示计算出的数字摘要。
   * @throws DigestMessageException
   *     若计算摘要时发生任何错误。
   * @see JsonMapperUtils#formatNormalized(Object, JsonMapper)
   */
  public <T> byte[] digest(final T data, final JsonMapper mapper)
      throws DigestMessageException {
    requireNonNull("data", data);
    requireNonNull("mapper", mapper);
    final String json = JsonMapperUtils.formatNormalizedNoThrow(data, mapper);
    if (json == null) {
      throw new DigestMessageException(data.toString());
    }
    return digest(json);
  }
}