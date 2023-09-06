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

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.CaseFormat;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.text.CaseFormat.UPPER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_UNDERSCORE;

/**
 * This model represents basic information about a deletable object belonging to
 * an entity.
 *
 * @author Haixing Hu
 */
public class InfoWithEntity extends Info implements WithEntity {

  @Serial
  private static final long serialVersionUID = 7281371900014761423L;

  /**
   * The name of the entity the object belongs to.
   */
  @Nullable
  private String entity;

  /**
   * Create a {@link InfoWithEntity} object.
   *
   * @param id
   *     The unique identifier of the object, which can be {@code null}.
   * @return If {@code id} is not {@code null}, return a {@link InfoWithEntity}
   *     object for the object with the specified ID; otherwise return
   *     {@code null}.
   */
  public static InfoWithEntity create(@Nullable final Long id) {
    if (id == null) {
      return null;
    } else {
      return new InfoWithEntity(id);
    }
  }

  /**
   * Create a {@link InfoWithEntity} object.
   *
   * @param id
   *     The unique identifier of the object, which can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @return If {@code id} and {@code code} are not both {@code null}, return a
   *     {@link InfoWithEntity} object with specified ID and code; otherwise
   *     return {@code null}.
   */
  public static InfoWithEntity create(@Nullable final Long id,
      @Nullable final String code) {
    if (id == null && code == null) {
      return null;
    } else {
      return new InfoWithEntity(id, code);
    }
  }

  /**
   * Create a {@link InfoWithEntity} object.
   *
   * @param id
   *     The unique identifier of the object, which can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   * @return If {@code id}, {@code code} and {@code name} are not all
   *     {@code null}, return a {@link InfoWithEntity} object for the object
   *     with the specified ID, code and name; otherwise return {@code null }.
   */
  public static InfoWithEntity create(@Nullable final Long id,
      @Nullable final String code, @Nullable final String name) {
    if (id == null && code == null && name == null) {
      return null;
    } else {
      return new InfoWithEntity(id, code, name);
    }
  }

  /**
   * Create a {@link InfoWithEntity} object.
   *
   * @param id
   *     The unique identifier of the object, which can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   * @param entityClass
   *     The class of the entity the object belongs to, can be {@code null}.
   * @return If {@code id}, {@code code}, {@code name} and {@code entityClass}
   *     are not all {@code null}, return a {@link InfoWithEntity} object for
   *     the object with the specified ID, code, name and entity; otherwise
   *     returns {@code null}.
   */
  public static InfoWithEntity create(@Nullable final Long id,
      @Nullable final String code, @Nullable final String name,
      @Nullable final Class<?> entityClass) {
    if (id == null && code == null && name == null && entityClass == null) {
      return null;
    } else {
      return new InfoWithEntity(id, code, name, entityClass);
    }
  }

  /**
   * Constructs an empty {@link InfoWithEntity}.
   */
  public InfoWithEntity() {}

  /**
   * Constructs a {@link InfoWithEntity} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   */
  public InfoWithEntity(@Nullable final Long id) {
    this.id = id;
  }

  /**
   * Constructs a {@link InfoWithEntity} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   */
  public InfoWithEntity(@Nullable final Long id, @Nullable final String code) {
    this.id = id;
    this.code = code;
  }

  /**
   * Constructs a {@link InfoWithEntity} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   */
  public InfoWithEntity(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  /**
   * Constructs a {@link InfoWithEntity} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   * @param entityClass
   *     The class of the entity the object belongs to, can be {@code null}.
   *     Note that the constructed {@link InfoWithEntity} will use the
   *     {@link CaseFormat#UPPER_UNDERSCORE} form of the simple name of the
   *     entity class as the entity name.
   */
  public InfoWithEntity(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name, @Nullable final Class<?> entityClass) {
    this.id = id;
    this.code = code;
    this.name = name;
    if (entityClass != null) {
      final String entityClassName = entityClass.getSimpleName();
      this.entity = UPPER_CAMEL.to(UPPER_UNDERSCORE, entityClassName);
    }
  }

  /**
   * Constructs a {@link InfoWithEntity} for an object.
   *
   * @param id
   *     The unique identifier of the object, can be {@code null}.
   * @param code
   *     The code of the object, which can be {@code null}.
   * @param name
   *     The name of the object, which can be {@code null}.
   * @param entity
   *     The name of the entity the object belongs to, can be {@code null}.
   */
  public InfoWithEntity(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name, @Nullable final String entity) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.entity = entity;
  }

  /**
   * Copy constructor.
   *
   * @param other
   *     The other {@link InfoWithEntity} object to be copied.
   */
  public InfoWithEntity(final InfoWithEntity other) {
    assign(other);
  }

  @Override
  @Nullable
  public String getEntity() {
    return entity;
  }

  public void setEntity(@Nullable final String entity) {
    this.entity = entity;
  }

  public void assign(final Info other) {
    super.assign(other);
    if (other instanceof InfoWithEntity) {
      entity = ((InfoWithEntity) other).entity;
    }
  }

  public void assign(final InfoWithEntity other) {
    super.assign(other);
    entity = other.entity;
  }

  @Override
  public InfoWithEntity clone() {
    return new InfoWithEntity(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InfoWithEntity other = (InfoWithEntity) o;
    return super.equals(other)
        && Equality.equals(entity, other.entity);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, super.hashCode());
    result = Hash.combine(result, multiplier, entity);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("entity", entity)
            .toString();
  }
}
