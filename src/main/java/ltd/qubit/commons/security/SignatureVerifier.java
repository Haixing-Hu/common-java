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
import java.security.PublicKey;
import java.security.Signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

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
 * The class of objects used to verify digital signature of messages.
 *
 * @author Haixing Hu
 * @see Signature
 * @see SignatureAlgorithm
 */
public class SignatureVerifier {

  private final transient Logger logger = LoggerFactory.getLogger(this.getClass());
  private final SignatureAlgorithm algorithm;
  private final transient Signature engine;

  public SignatureVerifier(final SignatureAlgorithm algorithm)
      throws VerifySignatureException {
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
   * Verify the signature of a message.
   *
   * @param publicKey
   *     the public key used to verify signature.
   * @param input
   *     the input stream of the message whose signature is to be verified. The
   *     function will <b>NOT</b> close this input stream.
   * @param signature
   *     the signature to be verified.
   * @return
   *     {@code true} if the signature matches; {@code false} otherwise.
   * @throws VerifySignatureException
   *     if any error occurs.
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
   * Verify the signature of a message.
   *
   * @param publicKey
   *     the public key used to verify signature.
   * @param message
   *     the byte array of the message whose signature is to be verified.
   * @param signature
   *     the signature to be verified.
   * @return
   *     {@code true} if the signature matches; {@code false} otherwise.
   * @throws VerifySignatureException
   *     if any error occurs.
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
   * Verify the signature of a message.
   *
   * @param publicKey
   *     the public key used to verify signature.
   * @param message
   *     the string of the message whose signature is to be verified，encoded
   *     in the specified charset.
   * @param charset
   *     the charset used to encoding the message.
   * @param signature
   *     the signature to be verified.
   * @return
   *     {@code true} if the signature matches; {@code false} otherwise.
   * @throws VerifySignatureException
   *     if any error occurs.
   */
  public boolean verify(final PublicKey publicKey, final String message,
      final Charset charset, final byte[] signature) throws VerifyMessageException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("message", message);
    requireNonNull("charset", charset);
    requireNonNull("signature", signature);
    return verify(publicKey, message.getBytes(charset), signature);
  }

  /**
   * Verify the signature of a message.
   *
   * @param publicKey
   *     the public key used to verify signature.
   * @param message
   *     the string of the message whose signature is to be verified，encoded
   *     in the UTF-8 charset.
   * @param signature
   *     the signature to be verified.
   * @return
   *     {@code true} if the signature matches; {@code false} otherwise.
   * @throws VerifySignatureException
   *     if any error occurs.
   */
  public boolean verify(final PublicKey publicKey, final String message,
      final byte[] signature) throws VerifyMessageException {
    requireNonNull("publicKey", publicKey);
    requireNonNull("message", message);
    requireNonNull("signature", signature);
    return verify(publicKey, message.getBytes(UTF_8), signature);
  }

  /**
   * Verify the signature of a message.
   *
   * @param publicKey
   *     the public key used to verify signature.
   * @param data
   *     the data to be signed, which will be encoded as a normalized JSON string
   *     and verified.
   * @param mapper
   *     the JSON mapper used to encode the data to a normalized JSON string.
   * @param signature
   *     the signature to be verified.
   * @return
   *     {@code true} if the signature matches; {@code false} otherwise.
   * @throws VerifySignatureException
   *     if any error occurs.
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
   * Verify the signature of a message.
   *
   * @param publicKey
   *     the public key used to verify signature.
   * @param message
   *     the signed message to be signed. The signature is encoded in the BASE-64
   *     format and stored in the {@code signature} field of this object.
   * @param mapper
   *     the JSON mapper used to encode the data to a normalized JSON string.
   * @return
   *     {@code true} if the signature matches; {@code false} otherwise.
   * @throws VerifySignatureException
   *     if any error occurs.
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

  private <T> boolean verifyDataImpl(final PublicKey publicKey, final T data,
      final JsonMapper mapper, final byte[] signature)
      throws VerifyMessageException {
    final String json;
    try {
      json = JsonMapperUtils.formatNormalized(data, mapper);
    } catch (final JsonProcessingException e) {
      throw new VerifySignatureException(e);
    }
    return verify(publicKey, json, signature);
  }

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
