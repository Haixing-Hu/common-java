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
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.annotation.RegEx;

import jakarta.xml.bind.annotation.XmlAnyElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

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

  @Serial
  private static final long serialVersionUID = 4750094224811838182L;

  /**
   * 日志记录器。
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SignedMessage.class);

  /**
   * 签名正则表达式。
   */
  @RegEx
  private static final String SIGNATURE_REGEXP = ",\"signature\":\"([^\"]+)\"}$";

  /**
   * 签名模式。
   */
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

  /**
   * 构造一个新的已签名消息对象。
   */
  public SignedMessage() {
  }

  /**
   * 构造一个带有指定数据的已签名消息对象。
   *
   * @param data
   *     消息数据。
   */
  public SignedMessage(final T data) {
    this.data = data;
  }

  /**
   * 获取消息数据。
   *
   * @return 消息数据。
   */
  public T getData() {
    return data;
  }

  /**
   * 设置消息数据。
   *
   * @param data
   *     新的消息数据。
   */
  public void setData(final T data) {
    this.data = data;
  }

  /**
   * 获取回调URL地址。
   *
   * @return 回调URL地址，可能为{@code null}。
   */
  @Nullable
  public String getReturnUrl() {
    return returnUrl;
  }

  /**
   * 设置回调URL地址。
   *
   * @param returnUrl
   *     新的回调URL地址，可以为{@code null}。
   */
  public void setReturnUrl(@Nullable final String returnUrl) {
    this.returnUrl = returnUrl;
  }

  /**
   * 获取通知URL地址。
   *
   * @return 通知URL地址，可能为{@code null}。
   */
  @Nullable
  public String getNotifyUrl() {
    return notifyUrl;
  }

  /**
   * 设置通知URL地址。
   *
   * @param notifyUrl
   *     新的通知URL地址，可以为{@code null}。
   */
  public void setNotifyUrl(@Nullable final String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  /**
   * 获取数字签名。
   *
   * @return 数字签名。
   */
  public String getSignature() {
    return signature;
  }

  /**
   * 设置数字签名。
   *
   * @param signature
   *     新的数字签名。
   */
  public void setSignature(final String signature) {
    this.signature = signature;
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