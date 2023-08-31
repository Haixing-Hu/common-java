////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import jakarta.validation.constraints.Size;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.pair.KeyValuePairList;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * 此模型表示错误信息。
 *
 * @author Haixing Hu
 */
public class ErrorInfo implements Serializable, Assignable<ErrorInfo> {

  private static final long serialVersionUID = 4005921165449943047L;

  /**
   * 错误类型。
   */
  private ErrorType type;

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

  public ErrorInfo(final ServerSideException e) {
    type = e.getType();
    code = e.getCode().name();
    params = new KeyValuePairList(e.getParams());
  }

  public ErrorInfo(final DataUpdateFailException e) {
    type = ErrorType.DATABASE_ERROR;
    code = ErrorCode.DATABASE_ERROR.name();
    params = KeyValuePairList.of("table", e.getTable(), "message", e.getMessage());
  }

  public ErrorInfo(final ErrorType type, final ErrorCode code, final Exception e) {
    this.type = type;
    this.code = code.name();
    this.params = KeyValuePairList.of("message", e.getMessage());
  }

  public ErrorInfo(final ErrorType type, final ErrorCode code,
      final KeyValuePairList params) {
    this.type = type;
    this.code = code.name();
    this.params = params;
  }

  public ErrorType getType() {
    return type;
  }

  public void setType(final ErrorType type) {
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
