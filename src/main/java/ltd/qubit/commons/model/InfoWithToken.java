////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serial;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class InfoWithToken extends Info implements WithToken {

  @Serial
  private static final long serialVersionUID = -634198626515479152L;

  /**
   * 访问令牌。
   */
  @Nullable
  private Token token;

  public InfoWithToken() {
    // empty
  }

  public InfoWithToken(final InfoWithToken other) {
    assign(other);
  }

  @Nullable
  public Token getToken() {
    return token;
  }

  public void setToken(@Nullable final Token token) {
    this.token = token;
  }

  public void assign(final Info other) {
    super.assign(other);
    if (other instanceof InfoWithToken) {
      token = Assignment.clone(((InfoWithToken) other).token);
    }
  }

  public void assign(final InfoWithToken other) {
    super.assign(other);
    token = Assignment.clone(other.token);
  }

  @Override
  public InfoWithToken clone() {
    return new InfoWithToken(this);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InfoWithToken other = (InfoWithToken) o;
    return super.equals(other)
        && Equality.equals(token, other.token);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, super.hashCode());
    result = Hash.combine(result, multiplier, token);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("token", token)
        .toString();
  }
}
