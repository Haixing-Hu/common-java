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

public class StatefulInfoWithToken extends StatefulInfo implements WithToken {

  @Serial
  private static final long serialVersionUID = -634198626515479152L;

  /**
   * 访问令牌。
   */
  @Nullable
  private Token token;

  public StatefulInfoWithToken() {
    // empty
  }

  public StatefulInfoWithToken(final Long id, final String code, final String name,
      final State state, final Token token) {
    super(id, code, name, state);
    this.token = Assignment.clone(token);
  }

  public StatefulInfoWithToken(final StatefulInfoWithToken other) {
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
    if (other instanceof StatefulInfoWithToken) {
      token = Assignment.clone(((StatefulInfoWithToken) other).token);
    }
  }

  public void assign(final StatefulInfoWithToken other) {
    super.assign(other);
    token = Assignment.clone(other.token);
  }

  @Override
  public StatefulInfoWithToken clone() {
    return new StatefulInfoWithToken(this);
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final StatefulInfoWithToken other = (StatefulInfoWithToken) o;
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
