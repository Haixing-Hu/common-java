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
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.codec.Base64Codec;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.io.IoUtils.BUFFER_SIZE;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * The class of objects used to sign digital signature of messages.
 *
 * @author Haixing Hu
 * @see Signature
 * @see SignatureAlgorithm
 */
public class SignatureSigner {

  private final SignatureAlgorithm algorithm;
  private final transient Signature engine;

  public SignatureSigner(final SignatureAlgorithm algorithm)
      throws SignMessageException {
    this.algorithm = requireNonNull("algorithm", algorithm);
    try {
      this.engine = Signature.getInstance(algorithm.code());
    } catch (final NoSuchAlgorithmException e) {
      throw new SignMessageException(e);
    }
  }

  public SignatureAlgorithm getAlgorithm() {
    return algorithm;
  }

  /**
   * Sign the message.
   *
   * @param privateKey
   *     the private key used to sign.
   * @param input
   *     the input stream of the message to be signed. Note that this function
   *     will <b>NOT</b> close this input stream.
   * @return
   *     the signature of the message in the input stream.
   * @throws SignMessageException
   *     if any error occurs.
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
   * Sign the message.
   *
   * @param privateKey
   *     the private key used to sign.
   * @param message
   *     the byte array of the message to be signed.
   * @return
   *     the signature of the message.
   * @throws SignMessageException
   *     if any error occurs.
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
   * Sign the message.
   *
   * @param privateKey
   *     the private key used to sign.
   * @param message
   *     the string of the message to be signed, encoded in the specified charset.
   * @param charset
   *     the charset used to encoding the message.
   * @return
   *     the signature of the message.
   * @throws SignMessageException
   *     if any error occurs.
   */
  public byte[] sign(final PrivateKey privateKey, final String message,
      final Charset charset) throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    return sign(privateKey, message.getBytes(charset));
  }

  /**
   * Sign the message.
   *
   * @param privateKey
   *     the private key used to sign.
   * @param message
   *     the string of the message to be signed, encoded in the UTF-8 charset.
   * @return
   *     the signature of the message.
   * @throws SignMessageException
   *     if any error occurs.
   */
  public byte[] sign(final PrivateKey privateKey, final String message)
      throws SignMessageException {
    requireNonNull("privateKey", privateKey);
    requireNonNull("message", message);
    return sign(privateKey, message.getBytes(UTF_8));
  }

  /**
   * Sign the data.
   *
   * @param <T>
   *     the type of the data to be signed.
   * @param privateKey
   *     the private key used to sign.
   * @param data
   *     the data to be signed, which will be encoded as a normalized JSON string
   *     and signed.
   * @param mapper
   *     the JSON mapper used to encode the data to a normalized JSON string.
   * @return
   *     the signature of the data.
   * @throws SignMessageException
   *     if any error occurs.
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
   * Sign a signed message.
   *
   * @param <T>
   *     the type of the data in the message to be signed.
   * @param privateKey
   *     the private key used to sign.
   * @param message
   *     the signed message to be signed. The signature will be encoded in BASE-64
   *     format and will be set to the {@code signature} field of this object.
   * @param mapper
   *     the JSON mapper used to encode the data to a normalized JSON string.
   * @return
   *     the BASE-64 encoded signature of the data.
   * @throws SignMessageException
   *     if any error occurs.
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

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, algorithm);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("algorithm", algorithm)
        .toString();
  }
}
