////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.annotation.RegEx;

import jakarta.xml.bind.annotation.XmlAnyElement;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此模型表示带数字签名的消息。
 *
 * @param <T>
 *     消息体的数据
 * @author 胡海星
 * @see SignatureAlgorithm
 * @see SignatureSigner
 * @see SignatureVerifier
 * @see SignatureKeyPairGenerator
 */
public class SignedMessage<T> implements Serializable {

  private static final long serialVersionUID = 4750094224811838182L;

  private static final Logger LOGGER = LoggerFactory.getLogger(SignedMessage.class);

  @RegEx
  private static final String SIGNATURE_REGEXP = ",\"signature\":\"([^\"]+)\"}$";

  private static final Pattern SIGNATURE_PATTERN = Pattern.compile(SIGNATURE_REGEXP);

  /**
   * 消息数据。
   *
   * <p>FIXME: Json/XML serialization of this field not work!
   */
  @XmlAnyElement(lax = true)
  protected T data;

  /**
   * 回调 URL 地址。
   */
  @Nullable
  protected String returnUrl;

  /**
   * 通过 HTTP POST 回写数据的 RESTful 接口 URL。
   */
  @Nullable
  protected String notifyUrl;

  /**
   * 对本消息除 signature 字段外进行JSON编码的RSA数字签名。
   */
  protected String signature;

  public SignedMessage() {
  }

  public SignedMessage(final T data) {
    this.data = data;
  }

  public final T getData() {
    return data;
  }

  public final SignedMessage<T> setData(final T data) {
    this.data = data;
    return this;
  }

  @Nullable
  public final String getReturnUrl() {
    return returnUrl;
  }

  public final SignedMessage<T> setReturnUrl(@Nullable final String returnUrl) {
    this.returnUrl = returnUrl;
    return this;
  }

  public final String getNotifyUrl() {
    return notifyUrl;
  }

  public final SignedMessage<T> setNotifyUrl(final String notifyUrl) {
    this.notifyUrl = notifyUrl;
    return this;
  }

  public final String getSignature() {
    return signature;
  }

  public final SignedMessage<T> setSignature(final String signature) {
    this.signature = signature;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    @SuppressWarnings("unchecked") final SignedMessage<T> other = (SignedMessage<T>) o;
    return Equality.equals(data, other.data)
        && Equality.equals(returnUrl, other.returnUrl)
        && Equality.equals(notifyUrl, other.notifyUrl)
        && Equality.equals(signature, other.signature);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, data);
    result = Hash.combine(result, multiplier, returnUrl);
    result = Hash.combine(result, multiplier, notifyUrl);
    result = Hash.combine(result, multiplier, signature);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("data", data)
        .append("returnUrl", returnUrl)
        .append("notifyUrl", notifyUrl)
        .append("signature", signature)
        .toString();
  }
}
