////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serializable;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.KeyIndex;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.text.CaseFormat.LOWER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_UNDERSCORE;

/**
 * 此模型表示对象所关联的所有者的信息。
 *
 * @author 胡海星
 */
public class Owner implements Serializable, Emptyful, Normalizable, Assignable<Owner> {

  private static final long serialVersionUID = 5222045589841495742L;

  /**
   * 实体对象的类型的名字。
   */
  @Size(min = 1, max = 64)
  @KeyIndex(0)
  private String type;

  /**
   * 实体对象的唯一标识。
   */
  @KeyIndex(1)
  private Long id;

  /**
   * 实体对象的属性的名称。
   */
  @Size(min = 1, max = 64)
  @KeyIndex(2)
  private String property;

  public Owner(final Class<?> cls, final Long id) {
    this(UPPER_CAMEL.to(UPPER_UNDERSCORE, cls.getSimpleName()), id, null);
  }

  public Owner(final Class<?> cls, final Long id, @Nullable final String property) {
    this(UPPER_CAMEL.to(UPPER_UNDERSCORE, cls.getSimpleName()), id,
        LOWER_CAMEL.to(UPPER_UNDERSCORE, property));
  }

  public <T extends Identifiable> Owner(final T obj, final String property) {
    this(obj.getClass(), obj.getId(), property);
  }

  private Owner() {}

  private Owner(final String type, final Long id, @Nullable final String property) {
    this.type = type;
    this.id = id;
    this.property = property;
  }

  public Owner(final Owner other) {
    assign(other);
  }

  @Override
  public void assign(final Owner other) {
    Argument.requireNonNull("other", other);
    type = other.type;
    id = other.id;
    property = other.property;
  }

  @Override
  public Owner clone() {
    return new Owner(this);
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(final String property) {
    this.property = property;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Owner other = (Owner) o;
    return Equality.equals(type, other.type)
        && Equality.equals(id, other.id)
        && Equality.equals(property, other.property);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, property);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("id", id)
        .append("property", property)
        .toString();
  }
}
