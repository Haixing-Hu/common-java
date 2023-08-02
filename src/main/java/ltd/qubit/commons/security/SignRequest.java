////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

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

  public SignRequest() {
    // empty
  }

  public String getSignerType() {
    return signerType;
  }

  public void setSignerType(final String signerType) {
    this.signerType = signerType;
  }

  public Long getSignerId() {
    return signerId;
  }

  public void setSignerId(final Long signerId) {
    this.signerId = signerId;
  }

  public String getSignerCode() {
    return signerCode;
  }

  public void setSignerCode(final String signerCode) {
    this.signerCode = signerCode;
  }

  public String getKeyVersion() {
    return keyVersion;
  }

  public void setKeyVersion(final String keyVersion) {
    this.keyVersion = keyVersion;
  }

  @Nullable
  public KeyValuePairList getPayload() {
    return payload;
  }

  public void setPayload(@Nullable final KeyValuePairList payload) {
    this.payload = payload;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

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
