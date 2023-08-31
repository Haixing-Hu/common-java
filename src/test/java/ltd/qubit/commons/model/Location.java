////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;

import static ltd.qubit.commons.lang.BigDecimalUtils.limitPrecision;

/**
 * 此模型表示地理位置坐标。
 *
 * <p>经纬度坐标的表示方式通常有三种：
 * <ol>
 * <li>ddd.ddddd° 用小数表示度，保留小数点后面5位</li>
 * <li>ddd°mm.mmm' 用整数表示度，小数表示分，保留小数点后面3位</li>
 * <li>ddd°mm'ss" 用整数表示度、分、秒</li>
 * </ol>
 *
 * <p>其中
 * <ul>
 * <li>1分 = 60秒</li>
 * <li>1度 = 60分</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public class Location implements Serializable, Emptyful, Normalizable,
    Assignable<Location> {

  private static final long serialVersionUID = -4224079873422617869L;

  /**
   * 经纬度坐标用小数形式表示时保留的小数点后位数。
   *
   * <p>默认保留小数点后{@value}位。</p>
   */
  public static final int PRECISION = LocationCoordinateCodec.SCALE;

  /**
   * 纬度，采用小数形式表示，保留小数点后面5位。
   */

  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(LocationCoordinateCodec.SCALE)
  private BigDecimal latitude;

  /**
   * 经度，采用小数形式表示，保留小数点后面5位。
   */

  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(LocationCoordinateCodec.SCALE)
  private BigDecimal longitude;

  public static Location create(@Nullable final BigDecimal latitude,
          @Nullable final BigDecimal longitude) {
    if (latitude == null && longitude == null) {
      return null;
    } else {
      return new Location(latitude, longitude);
    }
  }

  public Location() {
    // empty
  }

  public Location(final Location other) {
    assign(other);
  }

  public Location(final BigDecimal latitude, final BigDecimal longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public void assign(final Location other) {
    Argument.requireNonNull("other", other);
    latitude = other.latitude;
    longitude = other.longitude;
  }

  @Override
  public Location clone() {
    return new Location(this);
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(final BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(final BigDecimal longitude) {
    this.longitude = longitude;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Location other = (Location) o;
    return Equality.equals(latitude, other.latitude)
        && Equality.equals(longitude, other.longitude);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, latitude);
    result = Hash.combine(result, multiplier, longitude);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("latitude", latitude)
        .append("longitude", longitude)
        .toString();
  }

  @Override
  public final void normalize() {
    latitude = limitPrecision(latitude, PRECISION);
    longitude = limitPrecision(longitude, PRECISION);
  }
}
