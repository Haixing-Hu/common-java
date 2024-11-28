////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此模型表示各种证件、执照的基本信息。
 *
 * @author 胡海星
 */
public class CredentialInfo implements Identifiable, Emptyful, Normalizable,
    Assignable<CredentialInfo> {

  private static final long serialVersionUID = -1465636291913090852L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 证件类型。
   *
   * <p>对于个人，证件类型通常是身份证，社保卡等；
   * 对于机构，证件类型通常是营业执照、组织机构代码证等。
   */
  private CredentialType type;

  /**
   * 证件号码。
   *
   * <p>对于个人，证件号码通常是身份证号码，社保卡号码等；
   * 对于机构，证件号码通常是营业执照编号、组织机构代码等。
   */
  @Size(min = 1, max = 128)
  private String number;

  /**
   * 证件的验证状态。
   */
  @Nullable
  private VerifyState verified;

  /**
   * 创建一个证件。
   *
   * @param type
   *     证件类型，可以为{@code null}.
   * @param number
   *     证件号码，可以为{@code null}.
   * @return 若{@code type}和{@code number}不全为{@code null}，则返回一个指定类型和号
   *     码的证件对象，否则返回{@code null}.
   */
  public static CredentialInfo create(@Nullable final CredentialType type,
      @Nullable final String number) {
    if (type == null && number == null) {
      return null;
    } else {
      return new CredentialInfo(type, number);
    }
  }

  public CredentialInfo() {
    // empty
  }

  public CredentialInfo(final CredentialType type, final String number) {
    this.type = requireNonNull("type", type);
    this.number = requireNonNull("number", number);
  }

  public CredentialInfo(final CredentialInfo other) {
    assign(other);
  }

  public CredentialInfo(final Credential credential) {
    assign(credential);
  }

  @Override
  public void assign(final CredentialInfo other) {
    requireNonNull("other", other);
    id = other.id;
    type = other.type;
    number = other.number;
    verified = other.verified;
  }

  public void assign(final Credential credential) {
    this.id = credential.getId();
    this.type = credential.getType();
    this.number = credential.getNumber();
    this.verified = credential.getVerified();
  }

  @Override
  public CredentialInfo cloneEx() {
    return new CredentialInfo(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public CredentialType getType() {
    return type;
  }

  public void setType(final CredentialType type) {
    this.type = type;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(final String number) {
    this.number = number;
  }

  @Nullable
  public VerifyState getVerified() {
    return verified;
  }

  public void setVerified(@Nullable final VerifyState verified) {
    this.verified = verified;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final CredentialInfo other = (CredentialInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(type, other.type)
        && Equality.equals(number, other.number)
        && Equality.equals(verified, other.verified);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, number);
    result = Hash.combine(result, multiplier, verified);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("type", type)
        .append("number", number)
        .append("verified", verified)
        .toString();
  }

  public boolean isSame(@Nullable final CredentialType type,
      @Nullable final String number) {
    return Equality.equals(this.type, type)
        && Equality.equals(this.number, number);
  }

  public boolean isSame(@Nullable final CredentialInfo info) {
    return (info != null)
      && Equality.equals(this.type, info.type)
      && Equality.equals(this.number, info.number);
  }

  public boolean isSame(@Nullable final Credential info) {
    return (info != null)
        && Equality.equals(this.type, info.getType())
        && Equality.equals(this.number, info.getNumber());
  }
}
