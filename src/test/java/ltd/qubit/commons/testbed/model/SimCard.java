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
 * 此模型表示SIM卡信息。
 *
 * @author 胡海星
 */
public class SimCard implements Serializable, Emptyful, Normalizable,
    Assignable<SimCard> {

  private static final long serialVersionUID = -6838740246290718252L;

  /**
   * SIM卡的ICCID。
   *
   * <p>ICCID: Integrate circuit card identity, 集成电路卡识别码，即SIM卡卡号，相当于
   * 手机号码的身份证。 ICCID为IC卡的识别号码，共有20位字符组成，其编码格式为：</p>
   *
   * <pre><code>XXXXXX 0MFSS YYGXX XXXX</code></pre>
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String iccid;

  /**
   * SIM卡的IMEI。
   *
   * <p>IMEI: International Mobile Equipment Identity，国际移动设备识别码，是用于标识
   * GSM，WCDMA和iDEN移动电话以及某些卫星电话的唯一编号。通常，手机有一个IMEI号码，但在
   * 双SIM卡手机中有两个。</p>
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String imei;

  /**
   * SIM卡的手机号码。
   */
  @Nullable
  private Phone phone;

  /**
   * 运营商基本信息。
   */
  @Reference(entity = Organization.class, property = "info")
  @Nullable
  private StatefulInfo operator;

  /**
   * 运营商所属国家的基本信息。
   */
  @Reference(entity = Country.class, property = "info")
  @Nullable
  private Info country;

  /**
   * SIM卡当前所在地理位置。
   */
  @Nullable
  private Location location;

  /**
   * 移动数据网络类型。
   */
  @Nullable
  private DataNetworkType networkType;

  /**
   * SIM卡当前状态。
   */
  @Nullable
  private SimCardStatus status;

  public SimCard() {
    // empty
  }

  public SimCard(final SimCard other) {
    assign(other);
  }

  @Override
  public void assign(final SimCard other) {
    Argument.requireNonNull("other", other);
    iccid = other.iccid;
    imei = other.imei;
    phone = Assignment.clone(other.phone);
    operator = Assignment.clone(other.operator);
    country = Assignment.clone(other.country);
    location = Assignment.clone(other.location);
    networkType = other.networkType;
    status = other.status;
  }

  @Override
  public SimCard cloneEx() {
    return new SimCard(this);
  }

  @Nullable
  public String getIccid() {
    return iccid;
  }

  public void setIccid(@Nullable final String iccid) {
    this.iccid = iccid;
  }

  @Nullable
  public String getImei() {
    return imei;
  }

  public void setImei(@Nullable final String imei) {
    this.imei = imei;
  }

  @Nullable
  public Phone getPhone() {
    return phone;
  }

  public void setPhone(@Nullable final Phone phone) {
    this.phone = phone;
  }

  @Nullable
  public StatefulInfo getOperator() {
    return operator;
  }

  public void setOperator(@Nullable final StatefulInfo operator) {
    this.operator = operator;
  }

  @Nullable
  public Info getCountry() {
    return country;
  }

  public void setCountry(@Nullable final Info country) {
    this.country = country;
  }

  @Nullable
  public Location getLocation() {
    return location;
  }

  public void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Nullable
  public DataNetworkType getNetworkType() {
    return networkType;
  }

  public void setNetworkType(@Nullable final DataNetworkType networkType) {
    this.networkType = networkType;
  }

  @Nullable
  public SimCardStatus getStatus() {
    return status;
  }

  public void setStatus(@Nullable final SimCardStatus status) {
    this.status = status;
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SimCard other = (SimCard) o;
    return Equality.equals(iccid, other.iccid)
        && Equality.equals(imei, other.imei)
        && Equality.equals(phone, other.phone)
        && Equality.equals(operator, other.operator)
        && Equality.equals(country, other.country)
        && Equality.equals(location, other.location)
        && Equality.equals(networkType, other.networkType)
        && Equality.equals(status, other.status);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, iccid);
    result = Hash.combine(result, multiplier, imei);
    result = Hash.combine(result, multiplier, phone);
    result = Hash.combine(result, multiplier, operator);
    result = Hash.combine(result, multiplier, country);
    result = Hash.combine(result, multiplier, location);
    result = Hash.combine(result, multiplier, networkType);
    result = Hash.combine(result, multiplier, status);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("iccid", iccid)
        .append("imei", imei)
        .append("phone", phone)
        .append("operator", operator)
        .append("country", country)
        .append("location", location)
        .append("networkType", networkType)
        .append("status", status)
        .toString();
  }
}