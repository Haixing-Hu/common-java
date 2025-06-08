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
 * 此模型表示地址。
 *
 * @author 胡海星
 */
public class Address implements Serializable, WithLocation, Emptyful,
    Normalizable, Assignable<Address> {

  private static final long serialVersionUID = 4884933318589579610L;

  /**
   * 所属国家的基本信息。
   */
  @Reference(entity = Country.class, property = "info", path = "province.country")
  private Info country;

  /**
   * 所属省份的基本信息。
   */
  @Reference(entity = Province.class, property = "info", path = "city.province")
  private Info province;

  /**
   * 所属城市的基本信息。
   */
  @Reference(entity = City.class, property = "info", path = "district.city")
  private Info city;

  /**
   * 所属区县的基本信息。
   */
  @Reference(entity = District.class, property = "info", path = "street.district")
  private Info district;

  /**
   * 所属街道的基本信息。
   */
  @Reference(entity = Street.class, property = "info")
  private Info street;

  /**
   * 详细地址，门牌号码等。
   */
  @Size(min = 1, max = 4096)
  private String detail;

  /**
   * 邮政编码。
   */
  @Size(max = 64)
  @Nullable
  private String postalcode;

  /**
   * 定位。
   */
  @Nullable
  private Location location;

  public Address() {
    // empty
  }

  public Address(final Address other) {
    assign(other);
  }

  @Override
  public void assign(final Address other) {
    Argument.requireNonNull("other", other);
    country = Assignment.clone(other.country);
    province = Assignment.clone(other.province);
    city = Assignment.clone(other.city);
    district = Assignment.clone(other.district);
    street = Assignment.clone(other.street);
    detail = other.detail;
    postalcode = other.postalcode;
    location = Assignment.clone(other.location);
  }

  @Override
  public Address cloneEx() {
    return new Address(this);
  }

  public Info getCountry() {
    return country;
  }

  public void setCountry(final Info country) {
    this.country = country;
  }

  public Info getProvince() {
    return province;
  }

  public void setProvince(final Info province) {
    this.province = province;
  }

  public Info getCity() {
    return city;
  }

  public void setCity(final Info city) {
    this.city = city;
  }

  public Info getDistrict() {
    return district;
  }

  public void setDistrict(final Info district) {
    this.district = district;
  }

  public Info getStreet() {
    return street;
  }

  public void setStreet(final Info street) {
    this.street = street;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(final String detail) {
    this.detail = detail;
  }

  @Nullable
  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(@Nullable final String postalcode) {
    this.postalcode = postalcode;
  }

  @Override
  @Nullable
  public Location getLocation() {
    return location;
  }

  @Override
  public void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Address other = (Address) o;
    return Equality.equals(country, other.country)
        && Equality.equals(province, other.province)
        && Equality.equals(city, other.city)
        && Equality.equals(district, other.district)
        && Equality.equals(street, other.street)
        && Equality.equals(detail, other.detail)
        && Equality.equals(postalcode, other.postalcode)
        && Equality.equals(location, other.location);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, country);
    result = Hash.combine(result, multiplier, province);
    result = Hash.combine(result, multiplier, city);
    result = Hash.combine(result, multiplier, district);
    result = Hash.combine(result, multiplier, street);
    result = Hash.combine(result, multiplier, detail);
    result = Hash.combine(result, multiplier, postalcode);
    result = Hash.combine(result, multiplier, location);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("country", country)
        .append("province", province)
        .append("city", city)
        .append("district", district)
        .append("street", street)
        .append("detail", detail)
        .append("postalcode", postalcode)
        .append("location", location)
        .toString();
  }

  public boolean isSame(final Address other) {
    if (other == null) {
      return false;
    } else {
      return Equality.equals(detail, other.detail)
          && Equality.equals(postalcode, other.postalcode)
          && Equality.equals(location, other.location)
          && ((street == null && other.street == null)
          || (street != null
          && other.street != null
          && Equality.equals(street.getId(), other.street.getId())));
    }
  }
}