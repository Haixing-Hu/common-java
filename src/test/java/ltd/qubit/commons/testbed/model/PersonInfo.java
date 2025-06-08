////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.time.LocalDate;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示一个人的基本信息。
 *
 * @author 胡海星
 */
public class PersonInfo implements Identifiable, WithName, WithBirthday,
    Emptyful, Normalizable, Assignable<PersonInfo> {

  private static final long serialVersionUID = -1051312542127306317L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 姓名。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 性别。
   */
  @Nullable
  private Gender gender;

  /**
   * 出生日期。
   */
  @Nullable
  private LocalDate birthday;

  /**
   * 身份证件。
   */
  @Nullable
  private CredentialInfo credential;

  /**
   * 手机号码。
   */
  @Nullable
  private Phone mobile;

  /**
   * 电子邮件地址。
   */
  @Nullable
  private String email;

  public PersonInfo() {
    // empty
  }

  public PersonInfo(final Person person) {
    Argument.requireNonNull("person", person);
    id = person.getId();
    name = person.getName();
    gender = person.getGender();
    birthday = person.getBirthday();
    credential = person.getCredential();
    final Contact contact = person.getContact();
    if (contact != null) {
      mobile = contact.getMobile();
      email = contact.getEmail();
    }
  }

  public PersonInfo(final PersonInfo other) {
    assign(other);
  }

  @Override
  public void assign(final PersonInfo other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    name = other.name;
    gender = other.gender;
    birthday = other.birthday;
    credential = Assignment.clone(other.credential);
    mobile = Assignment.clone(other.mobile);
    email = other.email;
  }

  @Override
  public PersonInfo cloneEx() {
    return new PersonInfo(this);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public Gender getGender() {
    return gender;
  }

  public void setGender(@Nullable final Gender gender) {
    this.gender = gender;
  }

  @Override
  @Nullable
  public LocalDate getBirthday() {
    return birthday;
  }

  @Override
  public void setBirthday(@Nullable final LocalDate birthday) {
    this.birthday = birthday;
  }

  @Nullable
  public CredentialInfo getCredential() {
    return credential;
  }

  public void setCredential(@Nullable final CredentialInfo credential) {
    this.credential = credential;
  }

  @Nullable
  public Phone getMobile() {
    return mobile;
  }

  public void setMobile(@Nullable final Phone mobile) {
    this.mobile = mobile;
  }

  @Nullable
  public String getEmail() {
    return email;
  }

  public void setEmail(@Nullable final String email) {
    this.email = email;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final PersonInfo other = (PersonInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(name, other.name)
        && Equality.equals(gender, other.gender)
        && Equality.equals(birthday, other.birthday)
        && Equality.equals(credential, other.credential)
        && Equality.equals(mobile, other.mobile)
        && Equality.equals(email, other.email);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, gender);
    result = Hash.combine(result, multiplier, birthday);
    result = Hash.combine(result, multiplier, credential);
    result = Hash.combine(result, multiplier, mobile);
    result = Hash.combine(result, multiplier, email);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("gender", gender)
        .append("birthday", birthday)
        .append("credential", credential)
        .append("mobile", mobile)
        .append("email", email)
        .toString();
  }

  @Override
  @Computed({"id", "name", "gender", "birthday", "credential", "mobile", "email"})
  public final boolean isEmpty() {
    return (id == null)
        && (name == null || name.isEmpty())
        && (gender == null)
        && (birthday == null)
        && (credential == null || credential.isEmpty())
        && (mobile == null || mobile.isEmpty())
        && (email == null || email.isEmpty());
  }
}