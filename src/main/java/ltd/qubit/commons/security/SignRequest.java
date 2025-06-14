////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.io.Serial;
import java.io.Serializable;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 此模型表示对指定消息进行签名的请求。
 *
 * @author 胡海星
 */
public class SignRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 5826613907667012382L;

  /**
   * 该签名签发者对象的类型。
   */
  @Size(min = 1, max = 64)
  private String signerType;

  /**
   * 该签名签发者对象的ID。
   */
  private Long signerId;

  /**
   * 该签名签发者对象的编码。
   */
  private String signerCode;

  /**
   * 密钥对的版本，通常是其public key的MD5哈希值；用于在密钥分发系统中区分不同
   * 版本的密钥。
   */
  @Size(min = 1, max = 64)
  private String keyVersion;

  /**
   * 其他需要编码进被签名消息的额外数据。
   */
  @Nullable
  private KeyValuePairList payload;

  /**
   * 被签名的消息的BASE64编码。
   */
  private String message;

  /**
   * 构造一个新的签名请求对象。
   */
  public SignRequest() {
    // empty
  }

  /**
   * 获取签名签发者对象的类型。
   *
   * @return 签名签发者对象的类型。
   */
  public String getSignerType() {
    return signerType;
  }

  /**
   * 设置签名签发者对象的类型。
   *
   * @param signerType
   *     新的签名签发者对象的类型。
   */
  public void setSignerType(final String signerType) {
    this.signerType = signerType;
  }

  /**
   * 获取签名签发者对象的ID。
   *
   * @return 签名签发者对象的ID。
   */
  public Long getSignerId() {
    return signerId;
  }

  /**
   * 设置签名签发者对象的ID。
   *
   * @param signerId
   *     新的签名签发者对象的ID。
   */
  public void setSignerId(final Long signerId) {
    this.signerId = signerId;
  }

  /**
   * 获取签名签发者对象的编码。
   *
   * @return 签名签发者对象的编码。
   */
  public String getSignerCode() {
    return signerCode;
  }

  /**
   * 设置签名签发者对象的编码。
   *
   * @param signerCode
   *     新的签名签发者对象的编码。
   */
  public void setSignerCode(final String signerCode) {
    this.signerCode = signerCode;
  }

  /**
   * 获取密钥对的版本。
   *
   * @return 密钥对的版本。
   */
  public String getKeyVersion() {
    return keyVersion;
  }

  /**
   * 设置密钥对的版本。
   *
   * @param keyVersion
   *     新的密钥对的版本。
   */
  public void setKeyVersion(final String keyVersion) {
    this.keyVersion = keyVersion;
  }

  /**
   * 获取其他需要编码进被签名消息的额外数据。
   *
   * @return 额外数据，可能为{@code null}。
   */
  @Nullable
  public KeyValuePairList getPayload() {
    return payload;
  }

  /**
   * 设置其他需要编码进被签名消息的额外数据。
   *
   * @param payload
   *     新的额外数据，可以为{@code null}。
   */
  public void setPayload(@Nullable final KeyValuePairList payload) {
    this.payload = payload;
  }

  /**
   * 获取被签名的消息的BASE64编码。
   *
   * @return 被签名的消息的BASE64编码。
   */
  public String getMessage() {
    return message;
  }

  /**
   * 设置被签名的消息的BASE64编码。
   *
   * @param message
   *     新的被签名的消息的BASE64编码。
   */
  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SignRequest other = (SignRequest) o;
    return Equality.equals(signerType, other.signerType)
        && Equality.equals(signerId, other.signerId)
        && Equality.equals(signerCode, other.signerCode)
        && Equality.equals(keyVersion, other.keyVersion)
        && Equality.equals(payload, other.payload)
        && Equality.equals(message, other.message);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, signerType);
    result = Hash.combine(result, multiplier, signerId);
    result = Hash.combine(result, multiplier, signerCode);
    result = Hash.combine(result, multiplier, keyVersion);
    result = Hash.combine(result, multiplier, payload);
    result = Hash.combine(result, multiplier, message);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("signerType", signerType)
        .append("signerId", signerId)
        .append("signerCode", signerCode)
        .append("keyVersion", keyVersion)
        .append("payload", payload)
        .append("message", message)
        .toString();
  }
}