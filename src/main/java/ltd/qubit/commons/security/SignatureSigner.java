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
import java.security.PrivateKey;
import java.security.Signature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.error.SignMessageException;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.codec.Base64Codec;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.io.IoUtils.BUFFER_SIZE;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于对消息进行数字签名的对象类。
 *
 * @author 胡海星
 * @see Signature
 * @see SignatureAlgorithm
 */
public class SignatureSigner {

  private final SignatureAlgorithm algorithm;
  private final transient Signature engine;

  /**
   * 构造一个 {@link SignatureSigner}。
   *
   * @param algorithm
   *     签名算法。
   * @throws SignMessageException
   *     如果指定的签名算法不被支持。
   */
  public SignatureSigner(final SignatureAlgorithm algorithm)
      throws SignMessageException {
    this.algorithm = requireNonNull("algorithm", algorithm);
    try {
      this.engine = Signature.getInstance(algorithm.code());
    } catch (final NoSuchAlgorithmException e) {
      throw new SignMessageException(e);
    }
  }

  /**
   * 获取此签名器使用的签名算法。
   *
   * @return
   *     此签名器使用的签名算法。
   */
  public SignatureAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * 对消息进行签名。
   *
   * @param privateKey
   *     用于签名的私钥。
   * @param input
   *     要签名的消息的输入流。注意此函数<b>不会</b>关闭此输入流。
   * @return
   *     输入流中消息的签名。
   * @throws SignMessageException
   *     如果发生任何错误。
   */
  public byte[] sign(final PrivateKey privateKey, final InputStream input)
      throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("input", input);
    try {
      engine.initSign(privateKey);
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (true) {
        final int n = input.read(buffer);
        if (n < 0) {
          break;
        } else if (n > 0) {
          engine.update(buffer, 0, n);
        }
      }
      return engine.sign();
    } catch (final GeneralSecurityException | IOException e) {
      throw new SignMessageException(e);
    }
  }

  /**
   * 对消息进行签名。
   *
   * @param privateKey
   *     用于签名的私钥。
   * @param message
   *     要签名的消息的字节数组。
   * @return
   *     消息的签名。
   * @throws SignMessageException
   *     如果发生任何错误。
   */
  public byte[] sign(final PrivateKey privateKey, final byte[] message)
      throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("message", message);
    try {
      engine.initSign(privateKey);
      engine.update(message);
      return engine.sign();
    } catch (final GeneralSecurityException e) {
      throw new SignMessageException(e);
    }
  }

  /**
   * 对消息进行签名。
   *
   * @param privateKey
   *     用于签名的私钥。
   * @param message
   *     要签名的消息字符串，使用指定字符集编码。
   * @param charset
   *     用于编码消息的字符集。
   * @return
   *     消息的签名。
   * @throws SignMessageException
   *     如果发生任何错误。
   */
  public byte[] sign(final PrivateKey privateKey, final String message,
      final Charset charset) throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    return sign(privateKey, message.getBytes(charset));
  }

  /**
   * 对消息进行签名。
   *
   * @param privateKey
   *     用于签名的私钥。
   * @param message
   *     要签名的消息字符串，使用UTF-8字符集编码。
   * @return
   *     消息的签名。
   * @throws SignMessageException
   *     如果发生任何错误。
   */
  public byte[] sign(final PrivateKey privateKey, final String message)
      throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("message", message);
    return sign(privateKey, message.getBytes(UTF_8));
  }

  /**
   * 对数据进行签名。
   *
   * @param <T>
   *     要签名的数据类型。
   * @param privateKey
   *     用于签名的私钥。
   * @param data
   *     要签名的数据，将被编码为规范化JSON字符串并签名。
   * @param mapper
   *     用于将数据编码为规范化JSON字符串的JSON映射器。
   * @return
   *     数据的签名。
   * @throws SignMessageException
   *     如果发生任何错误。
   * @see JsonMapperUtils#formatNormalized(Object, JsonMapper)
   */
  public <T> byte[] sign(final PrivateKey privateKey, final T data,
      final JsonMapper mapper) throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("data", data);
    requireNonNull("mapper", mapper);
    return signDataImpl(privateKey, data, mapper);
  }

  /**
   * 对已签名消息进行签名。
   *
   * @param <T>
   *     消息中数据的类型。
   * @param privateKey
   *     用于签名的私钥。
   * @param message
   *     要签名的已签名消息。签名将以BASE-64格式编码，
   *     并将设置到此对象的{@code signature}字段。
   * @param mapper
   *     用于将数据编码为规范化JSON字符串的JSON映射器。
   * @return
   *     数据的BASE-64编码签名。
   * @throws SignMessageException
   *     如果发生任何错误。
   * @see JsonMapperUtils#formatNormalized(Object, JsonMapper)
   */
  public <T> String sign(final PrivateKey privateKey,
      final SignedMessage<T> message, final JsonMapper mapper)
      throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("message", message);
    requireNonNull("mapper", mapper);
    message.setSignature(null);
    final byte[] signature = signDataImpl(privateKey, message, mapper);
    final Base64Codec codec = new Base64Codec();
    final String base64Signature = codec.encode(signature);
    message.setSignature(base64Signature);
    return base64Signature;
  }

  /**
   * 对数据进行签名。
   *
   * @param <T>
   *     要签名的数据类型。
   * @param privateKey
   *     用于签名的私钥。
   * @param data
   *     要签名的数据，将被编码为规范化JSON字符串并签名。
   * @param mapper
   *     用于将数据编码为规范化JSON字符串的JSON映射器。
   * @return
   *     数据的签名。
   * @see JsonMapperUtils#formatNormalized(Object, JsonMapper)
   */
  private <T> byte[] signDataImpl(final PrivateKey privateKey, final T data,
      final JsonMapper mapper) throws SignMessageException {
    final String json;
    try {
      json = JsonMapperUtils.formatNormalized(data, mapper);
    } catch (final JsonProcessingException e) {
      throw new SignMessageException(e);
    }
    return sign(privateKey, json.getBytes(UTF_8));
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SignatureSigner other = (SignatureSigner) o;
    return Equality.equals(algorithm, other.algorithm);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, algorithm);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("algorithm", algorithm)
        .toString();
  }
}