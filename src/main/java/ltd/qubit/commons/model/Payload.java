////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serializable;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The model of payload entries.
 *
 * @author Haixing Hu
 */
public class Payload implements Serializable, Assignable<Payload> {

  /**
   * The unique ID of this object.
   */
  @Identifier
  private Long id;

  /**
   * The owner of this payload entry.
   */
  private Owner owner;

  /**
   * The key of this payload entry.
   */
  @Size(min = 1, max = 512)
  private String key;

  /**
   * The value of this payload entry.
   */
  @Size(min = 1, max = 512)
  private String value;

  /**
   * The description of this payload entry.
   */
  @Nullable
  private String description;

  public Payload() {
    // empty
  }

  public Payload(final Payload other) {
    assign(other);
  }

  @Override
  public void assign(final Payload other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    owner = Assignment.clone(other.owner);
    key = other.key;
    value = other.value;
    description = other.description;
  }

  @Override
  public Payload clone() {
    return new Payload(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(final Owner owner) {
    this.owner = owner;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Payload other = (Payload) o;
    return Equality.equals(id, other.id)
        && Equality.equals(owner, other.owner)
        && Equality.equals(key, other.key)
        && Equality.equals(value, other.value)
        && Equality.equals(description, other.description);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, owner);
    result = Hash.combine(result, multiplier, key);
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, description);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("owner", owner)
        .append("key", key)
        .append("value", value)
        .append("description", description)
        .toString();
  }
}
