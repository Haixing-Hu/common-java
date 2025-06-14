////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

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

  @Serial
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

  /**
   * 构造一个新的错误信息。
   */
  public ErrorInfo() {
  }

  /**
   * 构造一个新的错误信息，复制另一个错误信息的内容。
   *
   * @param other
   *     要复制的错误信息。
   */
  public ErrorInfo(final ErrorInfo other) {
    assign(other);
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param type
   *     错误类型。
   * @param code
   *     错误代码。
   * @param message
   *     错误消息。
   */
  public ErrorInfo(final String type, final String code, final String message) {
    this.type = type;
    this.code = code;
    this.params = KeyValuePairList.of("message", message);
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param type
   *     错误类型。
   * @param code
   *     错误代码。
   * @param e
   *     异常对象。
   */
  public ErrorInfo(final String type, final String code, final Throwable e) {
    this.type = type;
    this.code = code;
    this.params = KeyValuePairList.of("message", e.getMessage());
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param type
   *     错误类型。
   * @param code
   *     错误代码。
   * @param params
   *     错误参数。
   */
  public ErrorInfo(final String type, final String code, final KeyValuePair ... params) {
    this.type = type;
    this.code = code;
    this.params = KeyValuePairList.ofArray(params);
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param type
   *     错误类型。
   * @param code
   *     错误代码。
   * @param params
   *     错误参数列表。
   */
  public ErrorInfo(final String type, final String code,
      @Nullable final KeyValuePairList params) {
    this.type = type;
    this.code = code;
    this.params = params;
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param <E1>
   *     错误类型枚举的类型。
   * @param <E2>
   *     错误代码枚举的类型。
   * @param type
   *     错误类型枚举。
   * @param code
   *     错误代码枚举。
   * @param message
   *     错误消息。
   */
  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, final String message) {
    this.type = type.name();
    this.code = code.name();
    this.params = KeyValuePairList.of("message", message);
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param <E1>
   *     错误类型枚举的类型。
   * @param <E2>
   *     错误代码枚举的类型。
   * @param type
   *     错误类型枚举。
   * @param code
   *     错误代码枚举。
   * @param e
   *     异常对象。
   */
  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, final Throwable e) {
    this.type = type.name();
    this.code = code.name();
    if (e instanceof SQLException) {
      // 不要把 SQLSyntaxErrorException.message() 作为错误消息，因为它包含了 SQL 语句，可能会泄露敏感信息。
      this.params = KeyValuePairList.builder()
          .add("message", ((SQLSyntaxErrorException) e).getSQLState())
          .add("reason", e.getMessage())
          .build();
    } else {
      this.params = KeyValuePairList.of("message", e.getMessage());
    }
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param <E1>
   *     错误类型枚举的类型。
   * @param <E2>
   *     错误代码枚举的类型。
   * @param type
   *     错误类型枚举。
   * @param code
   *     错误代码枚举。
   * @param params
   *     错误参数。
   */
  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, final KeyValuePair ... params) {
    this.type = type.name();
    this.code = code.name();
    this.params = KeyValuePairList.ofArray(params);
  }

  /**
   * 构造一个新的错误信息。
   *
   * @param <E1>
   *     错误类型枚举的类型。
   * @param <E2>
   *     错误代码枚举的类型。
   * @param type
   *     错误类型枚举。
   * @param code
   *     错误代码枚举。
   * @param params
   *     错误参数列表。
   */
  public <E1 extends Enum<E1>, E2 extends Enum<E2>>
  ErrorInfo(final E1 type, final E2 code, @Nullable final KeyValuePairList params) {
    this.type = type.name();
    this.code = code.name();
    this.params = params;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void assign(final ErrorInfo other) {
    type = other.type;
    code = other.code;
    message = other.message;
    params = Assignment.clone(other.params);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo cloneEx() {
    return new ErrorInfo(this);
  }

  /**
   * 获取错误类型。
   *
   * @return
   *     错误类型。
   */
  public String getType() {
    return type;
  }

  /**
   * 设置错误类型。
   *
   * @param type
   *     错误类型。
   */
  public void setType(final String type) {
    this.type = type;
  }

  /**
   * 获取错误代码。
   *
   * @return
   *     错误代码。
   */
  public String getCode() {
    return code;
  }

  /**
   * 设置错误代码。
   *
   * @param code
   *     错误代码。
   */
  public void setCode(final String code) {
    this.code = code;
  }

  /**
   * 获取错误消息。
   *
   * @return
   *     错误消息。
   */
  @Nullable
  public String getMessage() {
    return message;
  }

  /**
   * 设置错误消息。
   *
   * @param message
   *     错误消息。
   */
  public void setMessage(@Nullable final String message) {
    this.message = message;
  }

  /**
   * 获取错误参数。
   *
   * @return
   *     错误参数。
   */
  @Nullable
  public KeyValuePairList getParams() {
    return params;
  }

  /**
   * 设置错误参数。
   *
   * @param params
   *     错误参数。
   */
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