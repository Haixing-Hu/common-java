////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serializable;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.pair.KeyValuePair;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 此模型表示错误信息。
 *
 * @author 胡海星
 */
public class ErrorInfo implements Serializable, Assignable<ErrorInfo> {

  private static final long serialVersionUID = 4005921165449943047L;

  /**
   * 错误类型。
   */
  private String type;

  /**
   * 错误代码。
   */
  @Size(min = 1, max = 64)
  private String code;

  /**
   * 错误消息。
   */
  @Size(min = 1, max = 1024)
  @Nullable
  private String message;

  /**
   * 错误参数。
   */
  @Size(min = 1, max = 12)
  @Nullable
  private KeyValuePairList params;

  public ErrorInfo() {
  }

  public ErrorInfo(final ErrorInfo other) {
    assign(other);
  }

  public ErrorInfo(final String type, final String code, final String message) {
    this.type = type;
    this.code = code;
    this.params = KeyValuePairList.of("message", message);
  }

  public ErrorInfo(final String type, final String code, final Throwable e) {
    this.type = type;
    this.code = code;
    this.params = KeyValuePairList.of("message", e.getMessage());
  }

  public ErrorInfo(final String type, final String code, final KeyValuePair ... params) {
    this.type = type;
    this.code = code;
    this.params = KeyValuePairList.ofArray(params);
  }

  public ErrorInfo(final String type, final String code,
      @Nullable final KeyValuePairList params) {
    this.type = type;
    this.code = code;
    this.params = params;
  }

  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, final String message) {
    this.type = type.name();
    this.code = code.name();
    this.params = KeyValuePairList.of("message", message);
  }

  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, final Throwable e) {
    this.type = type.name();
    this.code = code.name();
    this.params = KeyValuePairList.of("message", e.getMessage());
  }

  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, final KeyValuePair ... params) {
    this.type = type.name();
    this.code = code.name();
    this.params = KeyValuePairList.ofArray(params);
  }

  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code,
      @Nullable final KeyValuePairList params) {
    this.type = type.name();
    this.code = code.name();
    this.params = params;
  }

  @Override
  public void assign(final ErrorInfo other) {
    type = other.type;
    code = other.code;
    message = other.message;
    params = Assignment.clone(other.params);
  }

  @Override
  public ErrorInfo clone() {
    return new ErrorInfo(this);
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Nullable
  public String getMessage() {
    return message;
  }

  public void setMessage(@Nullable final String message) {
    this.message = message;
  }

  @Nullable
  public KeyValuePairList getParams() {
    return params;
  }

  public void setParams(@Nullable final KeyValuePairList params) {
    this.params = params;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ErrorInfo other = (ErrorInfo) o;
    return Equality.equals(type, other.type)
        && Equality.equals(code, other.code)
        && Equality.equals(message, other.message)
        && Equality.equals(params, other.params);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, message);
    result = Hash.combine(result, multiplier, params);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("code", code)
        .append("message", message)
        .append("params", params)
        .toString();
  }
}
