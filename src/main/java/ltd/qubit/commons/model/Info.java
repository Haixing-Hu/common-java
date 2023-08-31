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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.pair.KeyValuePair;

/**
 * This model represents the basic information of a deletable object.
 *
 * @author Haixing Hu
 */
public class Info implements HasInfo, Deletable, Emptyful, Normalizable,
    Assignable<Info> {

  @Serial
  private static final long serialVersionUID = 7281371900014761423L;

  /**
   * The unique identifier of the object.
   */
  @Identifier
  protected Long id;

  /**
   * The code of the object, usually globally unique.
   */
  @Size(min = 1, max = 64)
  @Unique
  @NotBlank
  protected String code;

  /**
   * The name of the object.
   */
  @Size(min = 1, max = 128)
  @NotBlank
  protected String name;

  /**
   * The mark deletion time of the object, or {@code null} if the object has not
   * been deleted.
   */
  @Nullable
  protected Instant deleteTime;

  /**
   * Create a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object.
   * @return
   *     a {@link Info} object for the object with the specified ID.
   */
  public static Info ofId(final Long id) {
    return new Info(id);
  }

  /**
   * Create a {@link Info} for an object.
   *
   * @param code
   *     The code of the object.
   * @return
   *     a {@link Info} object for the object with the specified code.
   */
  public static Info ofCode(final String code) {
    return new Info(null, code);
  }

  /**
   * Create a {@link Info} for an object.
   *
   * @param name
   *     The name of the object.
   * @return
   *     a {@link Info} object for the object with the specified name.
   */
  public static Info ofName(final String name) {
    return new Info(null, null, name);
  }

  /**
   * Create a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @return
   *     If {@code id} is not {@code null}, return a {@link Info} object for the
   *     object with the specified ID, otherwise return {@code null}.
   */
  public static Info create(@Nullable final Long id) {
    if (id == null) {
      return null;
    } else {
      return new Info(id);
    }
  }

  /**
   * Create a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, which can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @return
   *     If {@code id} and {@code code} are not both {@code null}, return a
   *     {@link Info} object for the object with the specified ID and code;
   *     otherwise return {@code null}.
   */
  public static Info create(@Nullable final Long id, @Nullable final String code) {
    if (id == null && code == null) {
      return null;
    } else {
      return new Info(id, code);
    }
  }

  /**
   * Create a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, which can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   * @return
   *     If {@code id}, {@code code} and {@code name} are not all {@code null},
   *     return a {@link Info} object with the specified ID, code and name;
   *     otherwise return {@code null}.
   */
  public static Info create(@Nullable final Long id,
      @Nullable final String code, @Nullable final String name) {
    if (id == null && code == null && name == null) {
      return null;
    } else {
      return new Info(id, code, name);
    }
  }

  /**
   * Constructs an empty {@link Info}.
   */
  public Info() {
    // empty
  }

  /**
   * Constructs a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   */
  public Info(@Nullable final Long id) {
    this.id = id;
  }

  /**
   * Constructs a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   */
  public Info(@Nullable final Long id, @Nullable final String code) {
    this.id = id;
    this.code = code;
  }

  /**
   * Constructs a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   */
  public Info(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  /**
   * Constructs a {@link Info} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   * @param deleteTime
   *     The mark deletion time of the object, which can be {@code null}.
   */
  public Info(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name, @Nullable final Instant deleteTime) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.deleteTime = deleteTime;
  }

  /**
   * Copy constructor.
   *
   * @param other
   *     The other {@link Info} object to be copied.
   */
  public Info(final Info other) {
    assign(other);
  }

  /**
   * Copy constructor.
   *
   * @param other
   *     The other {@link StatefulInfo} object to be copied.
   */
  public Info(final StatefulInfo other) {
    assign(other);
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
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  @Override
  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  @Override
  public void assign(final Info other) {
    id = other.id;
    code = other.code;
    name = other.name;
    deleteTime = other.deleteTime;
  }

  public void assign(final StatefulInfo other) {
    id = other.getId();
    code = other.getCode();
    name = other.getName();
    deleteTime = other.getDeleteTime();
  }

  @Override
  public Info clone() {
    return new Info(this);
  }

  public KeyValuePair[] toParams() {
    final List<KeyValuePair> params = new ArrayList<>();
    params.add(new KeyValuePair("id", id));
    params.add(new KeyValuePair("name", name));
    params.add(new KeyValuePair("code", code));
    return params.toArray(new KeyValuePair[0]);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    // NOTE: Allow trivial subclasses of Info to compare with Info
    if (!(o instanceof final Info other)) {
      return false;
    }
    return Equality.equals(id, other.id)
            && Equality.equals(code, other.code)
            && Equality.equals(name, other.name)
            && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("id", id)
            .append("code", code)
            .append("name", name)
            .append("deleteTime", deleteTime)
            .toString();
  }
}
