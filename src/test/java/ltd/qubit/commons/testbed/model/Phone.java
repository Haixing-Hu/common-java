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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.annotation.TypeCodec;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.codec.DecodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此模型表示电话号码。
 *
 * @author 胡海星
 */
@TypeCodec(PhoneCodec.class)
public class Phone implements Serializable, Emptyful, Normalizable, Assignable<Phone> {

  private static final long serialVersionUID = -1953942405568151170L;

  /**
   * 中国的电话号码区号：{@value}。
   */
  public static final String CHINA_AREA = "86";

  /**
   * 国家区号前缀。
   */
  public static final String COUNTRY_CODE_PREFIX = "+";

  /**
   * 分割电话号码各部分的字符串。
   */
  public static final String PART_SEPARATOR = "-";

  /**
   * 默认电话号码区号 {@value}。
   */
  public static final String DEFAULT_COUNTRY_AREA = CHINA_AREA;

  /**
   * 国家区号。
   */
  @Size(min = 1, max = 16)
  @Nullable
  private String countryArea;

  /**
   * 城市区号。
   */
  @Size(min = 1, max = 16)
  @Nullable
  private String cityArea;

  /**
   * 电话号码。
   */
  @Size(min = 1, max = 64)
  private String number;

  public Phone() { }

  public Phone(final String number) {
    this.number = requireNonNull("number", number);
  }

  public Phone(@Nullable final String countryArea, final String number) {
    this.countryArea = countryArea;
    this.number = requireNonNull("number", number);
  }

  public Phone(@Nullable final String countryArea,
      @Nullable final String cityArea, final String number) {
    this.countryArea = countryArea;
    this.cityArea = cityArea;
    this.number = requireNonNull("number", number);
  }

  public Phone(final Phone other) {
    assign(other);
  }

  @Override
  public void assign(final Phone other) {
    requireNonNull("other", other);
    countryArea = other.countryArea;
    cityArea = other.cityArea;
    number = other.number;
  }

  @Override
  public Phone cloneEx() {
    return new Phone(this);
  }

  @Nullable
  public String getCountryArea() {
    return countryArea;
  }

  public void setCountryArea(@Nullable final String countryArea) {
    this.countryArea = countryArea;
  }

  @Nullable
  public String getCityArea() {
    return cityArea;
  }

  public void setCityArea(@Nullable final String cityArea) {
    this.cityArea = cityArea;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(final String number) {
    this.number = number;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Phone other = (Phone) o;
    return Equality.equals(countryArea, other.countryArea) && Equality.equals(
        cityArea, other.cityArea) && Equality.equals(number, other.number);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, countryArea);
    result = Hash.combine(result, multiplier, cityArea);
    result = Hash.combine(result, multiplier, number);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("countryArea", countryArea)
        .append("cityArea", cityArea)
        .append("number", number)
        .toString();
  }

  public static Phone fromString(final String phone) {
    try {
      return new PhoneCodec().decode(phone);
    } catch (final DecodingException e) {
      final Logger logger = LoggerFactory.getLogger(Phone.class);
      logger.error("Invalid phone number format: {}", phone, e);
      return null;
    }
  }
}