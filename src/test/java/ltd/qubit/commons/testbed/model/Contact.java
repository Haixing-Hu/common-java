////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.io.Serializable;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示联系方式。
 *
 * @author 胡海星
 */
public class Contact implements Serializable, WithAddress, Emptyful,
    Normalizable, Assignable<Contact> {

  private static final long serialVersionUID = -3017966202832988873L;

  /**
   * 固定电话号码。
   */
  @Nullable
  private Phone phone;

  /**
   * 固定电话号码验证状态。
   */
  private VerifyState phoneVerified;

  /**
   * 手机号码。
   */
  @Nullable
  private Phone mobile;

  /**
   * 手机号码验证状态。
   */
  private VerifyState mobileVerified;

  /**
   * 电子邮件地址。
   */
  @Size(max = 512)
  @Nullable
  private String email;

  /**
   * 电子邮件地址验证状态。
   */
  private VerifyState emailVerified;

  /**
   * 网址 URL。
   */
  @Size(max = 512)
  @Nullable
  private String url;

  /**
   * 联系地址。
   */
  @Reference
  @Nullable
  private Address address;

  /**
   * 联系地址验证状态。
   */
  private VerifyState addressVerified;

  public static Contact create(@Nullable final Phone phone,
          @Nullable final Phone mobile,
          @Nullable final String email,
          @Nullable final String url,
          @Nullable final Address address) {
    if (phone == null
            && mobile == null
            && email == null
            && url == null
            && address == null) {
      return null;
    } else {
      final Contact result = new Contact();
      result.setPhone(phone);
      result.setMobile(mobile);
      result.setEmail(email);
      result.setUrl(url);
      result.setAddress(address);
      return result;
    }
  }

  public Contact() {
    // empty
  }

  public Contact(final Contact other) {
    assign(other);
  }

  @Override
  public void assign(final Contact other) {
    Argument.requireNonNull("other", other);
    phone = Assignment.clone(other.phone);
    phoneVerified = other.phoneVerified;
    mobile = Assignment.clone(other.mobile);
    mobileVerified = other.mobileVerified;
    email = other.email;
    emailVerified = other.emailVerified;
    url = other.url;
    address = Assignment.clone(other.address);
    addressVerified = other.addressVerified;
  }

  @Override
  public Contact cloneEx() {
    return new Contact(this);
  }

  @Nullable
  public Phone getPhone() {
    return phone;
  }

  public void setPhone(@Nullable final Phone phone) {
    this.phone = phone;
  }

  public VerifyState getPhoneVerified() {
    return phoneVerified;
  }

  public void setPhoneVerified(final VerifyState phoneVerified) {
    this.phoneVerified = phoneVerified;
  }

  @Nullable
  public Phone getMobile() {
    return mobile;
  }

  public void setMobile(@Nullable final Phone mobile) {
    this.mobile = mobile;
  }

  public VerifyState getMobileVerified() {
    return mobileVerified;
  }

  public void setMobileVerified(final VerifyState mobileVerified) {
    this.mobileVerified = mobileVerified;
  }

  @Nullable
  public String getEmail() {
    return email;
  }

  public void setEmail(@Nullable final String email) {
    this.email = email;
  }

  public VerifyState getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(final VerifyState emailVerified) {
    this.emailVerified = emailVerified;
  }

  @Nullable
  public String getUrl() {
    return url;
  }

  public void setUrl(@Nullable final String url) {
    this.url = url;
  }

  @Override
  @Nullable
  public Address getAddress() {
    return address;
  }

  @Override
  public void setAddress(@Nullable final Address address) {
    this.address = address;
  }

  public VerifyState getAddressVerified() {
    return addressVerified;
  }

  public void setAddressVerified(final VerifyState addressVerified) {
    this.addressVerified = addressVerified;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Contact other = (Contact) o;
    return Equality.equals(phone, other.phone)
        && Equality.equals(phoneVerified, other.phoneVerified)
        && Equality.equals(mobile, other.mobile)
        && Equality.equals(mobileVerified, other.mobileVerified)
        && Equality.equals(email, other.email)
        && Equality.equals(emailVerified, other.emailVerified)
        && Equality.equals(url, other.url)
        && Equality.equals(address, other.address)
        && Equality.equals(addressVerified, other.addressVerified);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, phone);
    result = Hash.combine(result, multiplier, phoneVerified);
    result = Hash.combine(result, multiplier, mobile);
    result = Hash.combine(result, multiplier, mobileVerified);
    result = Hash.combine(result, multiplier, email);
    result = Hash.combine(result, multiplier, emailVerified);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, address);
    result = Hash.combine(result, multiplier, addressVerified);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("phone", phone)
        .append("phoneVerified", phoneVerified)
        .append("mobile", mobile)
        .append("mobileVerified", mobileVerified)
        .append("email", email)
        .append("emailVerified", emailVerified)
        .append("url", url)
        .append("address", address)
        .append("addressVerified", addressVerified)
        .toString();
  }

  public void setVerifyState() {
    if (phone == null) {
      phoneVerified = null;
    } else {
      phoneVerified = VerifyState.NONE;
    }
    if (mobile == null) {
      mobileVerified = null;
    } else {
      mobileVerified = VerifyState.NONE;
    }
    if (email == null) {
      emailVerified = null;
    } else {
      emailVerified = VerifyState.NONE;
    }
    if (address == null) {
      addressVerified = null;
    } else {
      addressVerified = VerifyState.NONE;
    }
  }

  public final void copyVerifyState(final Contact other) {
    if (phone == null) {
      phoneVerified = null;
    } else if (phone.equals(other.phone)) {
      phoneVerified = other.phoneVerified;
    } else {
      phoneVerified = VerifyState.NONE;
    }
    if (mobile == null) {
      mobileVerified = null;
    } else if (mobile.equals(other.mobile)) {
      mobileVerified = other.mobileVerified;
    } else {
      mobileVerified = VerifyState.NONE;
    }
    if (email == null) {
      emailVerified = null;
    } else if (email.equals(other.email)) {
      emailVerified = other.emailVerified;
    } else {
      emailVerified = VerifyState.NONE;
    }
    if (address == null) {
      addressVerified = null;
    } else if (other.address == null) {
      addressVerified = VerifyState.NONE;
    } else if (address.isSame(other.address)) {
      addressVerified = other.addressVerified;
    } else {
      addressVerified = VerifyState.NONE;
    }
  }

  @Override
  public void normalize() {
    phone = Normalizable.normalize(phone);
    mobile = Normalizable.normalize(mobile);
    email = Normalizable.normalize(email);
    url = Normalizable.normalize(url);
    address = Normalizable.normalize(address);
    if (phone == null) {
      phoneVerified = null;
    }
    if (mobile == null) {
      mobileVerified = null;
    }
    if (email == null) {
      emailVerified = null;
    }
    if (address == null) {
      addressVerified = null;
    }
  }
}