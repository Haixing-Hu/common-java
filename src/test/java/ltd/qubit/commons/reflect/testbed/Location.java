////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Nullable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

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
 * @author 胡海星
 */
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY,
                getterVisibility = NONE,
                isGetterVisibility = NONE,
                setterVisibility = NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location implements Serializable, Assignable<Location>,
    Normalizable<Location> {

  private static final long serialVersionUID = -4224079873422617869L;

  /**
   * 经纬度坐标用小数形式表示时保留的小数点后位数。
   */
  public static final int PRECISION = LocationCoordinateCodec.SCALE;

  /**
   * 纬度，采用小数形式表示，保留小数点后面5位。
   */
  @JsonSerialize(using = LocationCoordinateSerializer.class)
  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(value = LocationCoordinateCodec.SCALE)
  private BigDecimal latitude;

  /**
   * 经度，采用小数形式表示，保留小数点后面5位。
   */
  @JsonSerialize(using = LocationCoordinateSerializer.class)
  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(value = LocationCoordinateCodec.SCALE)
  private BigDecimal longitude;

  /**
   * 高度，采用小数形式表示，保留小数点后面5位。
   */
  @JsonSerialize(using = LocationCoordinateSerializer.class)
  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(value = LocationCoordinateCodec.SCALE)
  @Nullable
  private BigDecimal altitude;

  public static Location create(@Nullable final BigDecimal latitude,
          @Nullable final BigDecimal longitude,
          @Nullable final BigDecimal altitude) {
    if (latitude == null && longitude == null && altitude == null) {
      return null;
    } else {
      return new Location(latitude, longitude, altitude);
    }
  }

  public Location() {}

  public Location(final Location other) {
    assign(other);
  }

  public Location(final BigDecimal latitude, final BigDecimal longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Location(final BigDecimal latitude, final BigDecimal longitude,
      @Nullable final BigDecimal altitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
  }

  public void assign(final Location other) {
    latitude = other.latitude;
    longitude = other.longitude;
    altitude = other.altitude;
  }

  public final BigDecimal getLatitude() {
    return latitude;
  }

  public final Location setLatitude(final BigDecimal latitude) {
    this.latitude = latitude;
    return this;
  }

  public final BigDecimal getLongitude() {
    return longitude;
  }

  public final Location setLongitude(final BigDecimal longitude) {
    this.longitude = longitude;
    return this;
  }

  @Nullable
  public final BigDecimal getAltitude() {
    return altitude;
  }

  public final Location setAltitude(final BigDecimal altitude) {
    this.altitude = altitude;
    return this;
  }

  @Override
  public final Location normalize() {
    latitude = limitPrecision(latitude, PRECISION);
    longitude = limitPrecision(longitude, PRECISION);
    altitude = limitPrecision(altitude, PRECISION);
    return this;
  }

  public final boolean isEmpty() {
    return (latitude == null)
            && (longitude == null)
            && (altitude == null);
  }

  @Override
  public Location cloneEx() {
    return new Location(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Location other = (Location) o;
    return Equality.equals(latitude, other.latitude)
            && Equality.equals(longitude, other.longitude)
            && Equality.equals(altitude, other.altitude);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, latitude);
    result = Hash.combine(result, multiplier, longitude);
    result = Hash.combine(result, multiplier, altitude);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("latitude", latitude)
            .append("longitude", longitude)
            .append("altitude", altitude)
            .toString();
  }
}