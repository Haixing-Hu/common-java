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

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Indexed;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The runtime environment of a client.
 *
 * @author Haixing Hu
 */
public class Environment implements Serializable, Assignable<Environment> {

  @Serial
  private static final long serialVersionUID = 1187646689252728533L;

  /**
   * The IP address of the client.
   */
  @Size(min = 1, max = 128)
  @Indexed
  @Nullable
  private String ip;

  /**
   * The geographical location of the client.
   */
  @Indexed
  @Nullable
  private Location location;

  /**
   * The operating system of the client.
   */
  @Indexed
  @Nullable
  private Platform platform;

  /**
   * The unique device ID of the client.
   */
  @Size(min = 1, max = 64)
  @Indexed
  @Nullable
  private String udid;

  /**
   * The push notification token of the client.
   */
  @Size(min = 1, max = 64)
  @Indexed
  @Nullable
  private String pushToken;

  public Environment() {
    // empty
  }

  public Environment(final Environment other) {
    assign(other);
  }

  @Override
  public void assign(final Environment other) {
    Argument.requireNonNull("other", other);
    ip = other.ip;
    location = Assignment.clone(other.location);
    platform = other.platform;
    udid = other.udid;
    pushToken = other.pushToken;
  }

  @Override
  public Environment clone() {
    return new Environment(this);
  }

  @Nullable
  public String getIp() {
    return ip;
  }

  public void setIp(@Nullable final String ip) {
    this.ip = ip;
  }

  @Nullable
  public Location getLocation() {
    return location;
  }

  public void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Nullable
  public Platform getPlatform() {
    return platform;
  }

  public void setPlatform(@Nullable final Platform platform) {
    this.platform = platform;
  }

  @Nullable
  public String getUdid() {
    return udid;
  }

  public void setUdid(@Nullable final String udid) {
    this.udid = udid;
  }

  @Nullable
  public String getPushToken() {
    return pushToken;
  }

  public void setPushToken(@Nullable final String pushToken) {
    this.pushToken = pushToken;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Environment other = (Environment) o;
    return Equality.equals(ip, other.ip)
        && Equality.equals(location, other.location)
        && Equality.equals(platform, other.platform)
        && Equality.equals(udid, other.udid)
        && Equality.equals(pushToken, other.pushToken);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, ip);
    result = Hash.combine(result, multiplier, location);
    result = Hash.combine(result, multiplier, platform);
    result = Hash.combine(result, multiplier, udid);
    result = Hash.combine(result, multiplier, pushToken);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("ip", ip)
        .append("location", location)
        .append("platform", platform)
        .append("udid", udid)
        .append("pushToken", pushToken)
        .toString();
  }
}
