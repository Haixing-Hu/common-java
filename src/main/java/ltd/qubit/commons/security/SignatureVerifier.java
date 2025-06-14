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
import java.security.PublicKey;
import java.security.Signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.error.VerifySignatureException;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.codec.Base64Codec;
import ltd.qubit.commons.util.codec.DecodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.io.IoUtils.BUFFER_SIZE;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 用于验证消息数字签名的对象类。
 *
 * @author 胡海星
 * @see Signature
 * @see SignatureAlgorithm
 */
public class SignatureVerifier {

  /**
   * 日志记录器。
   */
  private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 签名算法。
   */
  private final SignatureAlgorithm algorithm;

  /**
   * 签名引擎。
   */
  private final transient Signature engine;

  /**
   * 构造一个数字签名验证器。
   *
   * @param algorithm
   *     用于验证签名的算法。
   * @throws VerifySignatureException
   *     如果指定的算法不受支持。
   */
  public SignatureVerifier(final SignatureAlgorithm algorithm)
      throws VerifySignatureException {
    this.algorithm = requireNonNull("algorithm", algorithm);
    try {
      this.engine = Signature.getInstance(algorithm.code());
    } catch (final NoSuchAlgorithmException e) {
      throw new VerifySignatureException(e);
    }
  }

  /**
   * 获取此验证器使用的签名算法。
   *
   * @return 签名算法。
   */
  public SignatureAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * 验证消息的签名。
   *
   * @param publicKey
   *     用于验证签名的公钥。
   * @param input
   *     要验证签名的消息的输入流。该函数<b>不会</b>关闭此输入流。
   * @param signature
   *     要验证的签名。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   */
  public boolean verify(final PublicKey publicKey, final InputStream input,
      final byte[] signature) throws VerifySignatureException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("input", input);
    requireNonNull("signature", signature);
    try {
      engine.initVerify(publicKey);
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (true) {
        final int n = input.read(buffer);
        if (n < 0) {
          break;
        } else if (n > 0) {
          engine.update(buffer, 0, n);
        }
      }
      return engine.verify(signature);
    } catch (final GeneralSecurityException | IOException e) {
      throw new VerifySignatureException(e);
    }
  }

  /**
   * 验证消息的签名。
   *
   * @param publicKey
   *     用于验证签名的公钥。
   * @param message
   *     要验证签名的消息的字节数组。
   * @param signature
   *     要验证的签名。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   */
  public boolean verify(final PublicKey publicKey, final byte[] message,
      final byte[] signature) throws VerifySignatureException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("message", message);
    requireNonNull("signature", signature);
    try {
      engine.initVerify(publicKey);
      engine.update(message);
      return engine.verify(signature);
    } catch (final GeneralSecurityException e) {
      throw new VerifySignatureException(e);
    }
  }

  /**
   * 验证消息的签名。
   *
   * @param publicKey
   *     用于验证签名的公钥。
   * @param message
   *     要验证签名的消息字符串，使用指定字符集编码。
   * @param charset
   *     用于编码消息的字符集。
   * @param signature
   *     要验证的签名。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   */
  public boolean verify(final PublicKey publicKey, final String message,
      final Charset charset, final byte[] signature) throws VerifySignatureException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    requireNonNull("signature", signature);
    return verify(publicKey, message.getBytes(charset), signature);
  }

  /**
   * 验证消息的签名。
   *
   * @param publicKey
   *     用于验证签名的公钥。
   * @param message
   *     要验证签名的消息字符串，使用UTF-8字符集编码。
   * @param signature
   *     要验证的签名。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   */
  public boolean verify(final PublicKey publicKey, final String message,
      final byte[] signature) throws VerifySignatureException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("message", message);
    requireNonNull("signature", signature);
    return verify(publicKey, message.getBytes(UTF_8), signature);
  }

  /**
   * 验证消息的签名。
   *
   * @param publicKey
   *     用于验证签名的公钥。
   * @param data
   *     要签名的数据，将被编码为规范化JSON字符串并验证。
   * @param mapper
   *     用于将数据编码为规范化JSON字符串的JSON映射器。
   * @param signature
   *     要验证的签名。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   */
  public <T> boolean verify(final PublicKey publicKey, final T data,
      final JsonMapper mapper, final byte[] signature)
      throws VerifySignatureException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("data", data);
    requireNonNull("mapper", mapper);
    requireNonNull("signature", signature);
    return verifyDataImpl(publicKey, data, mapper, signature);
  }

  /**
   * 验证已签名消息的签名。
   *
   * @param <T>
   *     消息中数据的类型。
   * @param publicKey
   *     用于验证签名的公钥。
   * @param message
   *     要验证的已签名消息。签名应以BASE-64格式编码，
   *     并存储在此对象的{@code signature}字段中。
   * @param mapper
   *     用于将数据编码为规范化JSON字符串的JSON映射器。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   */
  public <T> boolean verify(final PublicKey publicKey,
      final SignedMessage<T> message, final JsonMapper mapper)
      throws VerifySignatureException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("message", message);
    requireNonNull("mapper", mapper);
    final String base64Signature = message.getSignature();
    if (base64Signature == null) {
      return false;
    }
    final Base64Codec codec = new Base64Codec();
    final byte[] signature;
    try {
      signature = codec.decode(base64Signature);
    } catch (final DecodingException e) {
      logger.error("Invalid format of BASE-64 encoded signature: {}",
          base64Signature, e);
      return false;
    }
    message.setSignature(null);
    final boolean result = verifyDataImpl(publicKey, message, mapper, signature);
    message.setSignature(base64Signature);
    return result;
  }

  /**
   * 验证数据。
   *
   * @param <T>
   *     要验证的数据类型。
   * @param publicKey
   *     用于验证签名的公钥。
   * @param data
   *     要验证的数据，将被编码为规范化JSON字符串并验证。
   * @param mapper
   *     用于将数据编码为规范化JSON字符串的JSON映射器。
   * @param signature
   *     要验证的签名。
   * @return
   *     如果签名匹配则返回{@code true}；否则返回{@code false}。
   * @throws VerifySignatureException
   *     如果发生任何错误。
   * @see JsonMapperUtils#formatNormalized(Object, JsonMapper)
   */
  private <T> boolean verifyDataImpl(final PublicKey publicKey, final T data,
      final JsonMapper mapper, final byte[] signature)
      throws VerifySignatureException {
    final String json;
    try {
      json = JsonMapperUtils.formatNormalized(data, mapper);
    } catch (final JsonProcessingException e) {
      throw new VerifySignatureException(e);
    }
    return verify(publicKey, json, signature);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SignatureVerifier other = (SignatureVerifier) o;
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