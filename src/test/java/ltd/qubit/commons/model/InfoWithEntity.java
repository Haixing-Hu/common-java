////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import javax.annotation.Nullable;

import static ltd.qubit.commons.text.CaseFormat.UPPER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_UNDERSCORE;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * 此模型表示属于某个实体的可删除对象的基本信息。
 *
 * @author 胡海星
 */
@XmlRootElement(name = "info-with-entity")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY,
                getterVisibility = NONE,
                isGetterVisibility = NONE,
                setterVisibility = NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfoWithEntity extends Info implements WithEntity {

  private static final long serialVersionUID = 7281371900014761423L;

  /**
   * 所属实体。
   */
  @Nullable
  private String entity;

  /**
   * 创建一个{@link Info}对象。
   *
   * @param id
   *     对象ID，可以为{@code null}.
   * @return 若{@code id}不为{@code null}，则返回一个指定ID的{@link InfoWithEntity}对
   *     象，否则 返回{@code null}.
   */
  public static InfoWithEntity create(@Nullable final Long id) {
    if (id == null) {
      return null;
    } else {
      return new InfoWithEntity(id);
    }
  }

  /**
   * 创建一个{@link Info}对象。
   *
   * @param id
   *     对象ID，可以为{@code null}.
   * @param code
   *     对象编码，可以为{@code null}.
   * @return 若{@code id}和{@code code}不全为{@code null}，则返回一个指定ID和编码的
   *     {@link InfoWithEntity}对象，否则返回{@code null}.
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
   * 创建一个{@link Info}对象。
   *
   * @param id
   *     对象ID，可以为{@code null}.
   * @param code
   *     对象编码，可以为{@code null}.
   * @param name
   *     对象名称，可以为{@code null}.
   * @return 若{@code id}，{@code code}和{@code name}不全为{@code null}，则返回一个
   *     指定ID、编码和名称的{@link InfoWithEntity}对象，否则返回{@code null}.
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
   * 创建一个{@link Info}对象。
   *
   * @param id
   *     对象ID，可以为{@code null}.
   * @param code
   *     对象编码，可以为{@code null}.
   * @param name
   *     对象名称，可以为{@code null}.
   * @param entityClass
   *     对象所属实体的类对象，可以为{@code null}.
   * @return 若{@code id}，{@code code}，{@code name}和{@code entity}不全为
   *     {@code null}，则返回一个指定ID、编码、名称和实体的{@link InfoWithEntity}对象，
   *     否则返回{@code null}.
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

  public InfoWithEntity() {}

  public InfoWithEntity(@Nullable final Long id) {
    this.id = id;
  }

  public InfoWithEntity(@Nullable final Long id, @Nullable final String code) {
    this.id = id;
    this.code = code;
  }

  public InfoWithEntity(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public InfoWithEntity(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name, @Nullable final Class<?> entityClass) {
    this.id = id;
    this.code = code;
    this.name = name;
    if (entityClass != null) {
      this.entity = UPPER_CAMEL.to(UPPER_UNDERSCORE, entityClass.getSimpleName());
    }
  }

  public InfoWithEntity(@Nullable final Long id, @Nullable final String code,
      @Nullable final String name, @Nullable final String entity) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.entity = entity;
  }

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
