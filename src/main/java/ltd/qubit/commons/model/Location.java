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
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ltd.qubit.commons.annotation.Indexed;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.annotation.TypeCodec;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.deserializer.LocationCoordinateDeserializer;
import ltd.qubit.commons.text.jackson.serializer.LocationCoordinateSerializer;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.LocationCodec;
import ltd.qubit.commons.util.codec.LocationCoordinateCodec;

/**
 * This model represents geographical locations.
 * <p>
 * There are three ways to represent latitude and longitude coordinates:
 * <ol>
 * <li>{@code ddd.ddddd°}: represents the degree as a decimal, retaining 5
 * digits after the decimal point.</li>
 * <li>{@code ddd°mm.mmm'}: Uses integers to represent degrees and decimals
 * to represent minutes, retaining 3 digits after the decimal point.</li>
 * <li>{@code ddd°mm'ss"}: represents degrees, minutes and seconds as
 * integers.</li>
 * </ol>
 * <p>
 * Where
 * <ul>
 * <li>1 minute = 60 seconds</li>
 * <li>1 degree = 60 minutes</li>
 * </ul>
 *
 * @author Haixing Hu
 */
@TypeCodec(LocationCodec.class)
public class Location implements Serializable, Emptyful, Normalizable,
    Assignable<Location> {

  @Serial
  private static final long serialVersionUID = -4224079873422617869L;

  /**
   * The number of digits after the decimal point retained when latitude and
   * longitude coordinates are expressed in decimal form.
   * <p>
   * By default, {@value} digits are retained after the decimal point.
   */
  public static final int PRECISION = LocationCoordinateCodec.SCALE;

  /**
   * Longitude, expressed in decimal form, retaining {@value PRECISION} digits
   * after the decimal point.
   */
  @JsonSerialize(using = LocationCoordinateSerializer.class)
  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(LocationCoordinateCodec.SCALE)
  @Indexed
  private BigDecimal longitude;

  /**
   * Latitude, expressed in decimal form, retaining {@value PRECISION} digits
   * after the decimal point.
   */
  @JsonSerialize(using = LocationCoordinateSerializer.class)
  @JsonDeserialize(using = LocationCoordinateDeserializer.class)
  @Scale(LocationCoordinateCodec.SCALE)
  @Indexed
  private BigDecimal latitude;

  /**
   * Create a new {@link Location} object.
   *
   * @param longitude
   *     The specified longitude.
   * @param latitude
   *     specified latitude.
   * @return
   *     A newly created {@link Location} object based on the specified latitude
   *     and longitude, or {@code null} if one of the arguments is {@code null}.
   */
  public static Location create(@Nullable final BigDecimal longitude,
      @Nullable final BigDecimal latitude) {
    if (longitude == null || latitude == null) {
      return null;
    } else {
      return new Location(longitude, latitude);
    }
  }

  /**
   * Create a new {@link Location} object.
   *
   * @param longitude
   *     String encoding of the specified longitude.
   * @param latitude
   *     String encoding of the specified latitude.
   * @return
   *     A newly created {@link Location} object based on the specified latitude
   *     and longitude, or {@code null} if one of the parameters is {@code null}.
   * @throws DecodingException
   *     If the parameter is not the correct latitude and longitude encoding.
   */
  public static Location create(@Nullable final String longitude,
      @Nullable final String latitude) throws DecodingException {
    if (longitude == null || latitude == null) {
      return null;
    } else {
      return new Location(longitude, latitude);
    }
  }

  /**
   * Constructs a new {@link Location} object.
   *
   * @param location
   *     The string encoding of the specified longitude and latitude. Note that
   *     the encoding is in the form of "&lt;longitude&gt;,&lt;latitude&gt;",
   *     such as "118.905641,32.078". If the argument is {@code null}, return
   *     a {@code null}.
   * @return
   *     A newly created {@link Location} object based on the specified latitude
   *     and longitude, or {@code null} if the argument is {@code null}.
   * @throws DecodingException
   *     If the argument is not the correct latitude and longitude encoding.
   */
  public static Location create(@Nullable final String location)
      throws DecodingException {
    if (location == null) {
      return null;
    } else {
      return new Location(location);
    }
  }

  /**
   * Constructs an empty {@link Location} object.
   */
  public Location() {
    // empty
  }

  /**
   * Copy constructs a new {@link Location} object.
   *
   * @param other
   *     被复制的{@link Location}对象。
   */
  public Location(final Location other) {
    assign(other);
  }

  /**
   * Constructs a new {@link Location} object.
   *
   * @param longitude
   *     The specified longitude.
   * @param latitude
   *     specified latitude.
   */
  public Location(final BigDecimal longitude, final BigDecimal latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  /**
   * Constructs a new {@link Location} object.
   *
   * @param longitude
   *     String encoding of the specified longitude.
   * @param latitude
   *     String encoding of the specified latitude.
   * @throws DecodingException
   *     If the parameter is not the correct latitude and longitude encoding.
   */
  public Location(final String longitude, final String latitude)
      throws DecodingException {
    final LocationCoordinateCodec codec = LocationCoordinateCodec.INSTANCE;
    this.longitude = codec.decode(longitude);
    this.latitude = codec.decode(latitude);
  }

  /**
   * Constructs a new {@link Location} object.
   *
   * @param location
   *     The string encoding of the specified longitude and latitude. Note that
   *     the encoding is in the form of "&lt;longitude&gt;,&lt;latitude&gt;",
   *     for example "118.905641,32.078".
   * @throws DecodingException
   *     If the argument is not the correct latitude and longitude encoding.
   */
  public Location(final String location) throws DecodingException {
    final LocationCodec codec = new LocationCodec();
    final Location loc = codec.decode(location);
    this.longitude = loc.longitude;
    this.latitude = loc.latitude;
  }

  @Override
  public void assign(final Location other) {
    Argument.requireNonNull("other", other);
    longitude = other.longitude;
    latitude = other.latitude;
  }

  @Override
  public Location clone() {
    return new Location(this);
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(final BigDecimal longitude) {
    this.longitude = longitude;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(final BigDecimal latitude) {
    this.latitude = latitude;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Location other = (Location) o;
    return Equality.equals(longitude, other.longitude)
        && Equality.equals(latitude, other.latitude);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, longitude);
    result = Hash.combine(result, multiplier, latitude);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("longitude", longitude)
        .append("latitude", latitude)
        .toString();
  }

  /**
   * Normalizes the longitude and latitude coordinates of this location with
   * the default precision.
   */
  @Override
  public final void normalize() {
    normalize(PRECISION);
  }

  /**
   * Normalizes the longitude and latitude coordinates of this location with
   * the specified precision.
   *
   * @param precision
   *     The specified precision, that is, the number of digits after the
   *     decimal point retained when longitude and latitude coordinates are
   *     expressed in decimal form.
   */
  public final void normalize(final int precision) {
    longitude = LocationCoordinateCodec.normalize(longitude, precision);
    latitude = LocationCoordinateCodec.normalize(latitude, precision);
  }
}
