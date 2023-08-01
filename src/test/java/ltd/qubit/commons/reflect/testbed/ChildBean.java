////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class ChildBean extends ParentBean {

  private static final long serialVersionUID = -5744358754720585150L;
  private Long number;

  @Unique
  @Size(min = 1, max = 64)
  private String code;

  @Unique(respectTo = "parentId")
  @Size(min = 1, max = 128)
  private String name;

  @Nullable
  @Size(max = 1024)
  private String description;

  @Nullable
  private Long parentId;

  private boolean deleted;

  @Override
  public final Long getNumber() {
    return number;
  }

  public final ChildBean setNumber(final Long number) {
    this.number = number;
    return this;
  }

  public final String getCode() {
    return code;
  }

  public final ChildBean setCode(final String code) {
    this.code = code;
    return this;
  }

  public final String getName() {
    return name;
  }

  public final ChildBean setName(final String name) {
    this.name = name;
    return this;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final ChildBean setDescription(@Nullable final String description) {
    this.description = description;
    return this;
  }

  @Nullable
  public final Long getParentId() {
    return parentId;
  }

  public final ChildBean setParentId(@Nullable final Long parentId) {
    this.parentId = parentId;
    return this;
  }

  public final boolean isDeleted() {
    return deleted;
  }

  public final ChildBean setDeleted(final boolean deleted) {
    this.deleted = deleted;
    return this;
  }

  public final Info getInfo() {
    return new Info(getId(), code, name);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ChildBean other = (ChildBean) o;
    return super.equals(other)
        && Equality.equals(deleted, other.deleted)
        && Equality.equals(number, other.number)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(description, other.description)
        && Equality.equals(parentId, other.parentId);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, super.hashCode());
    result = Hash.combine(result, multiplier, number);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, parentId);
    result = Hash.combine(result, multiplier, deleted);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("number", number)
        .append("code", code)
        .append("name", name)
        .append("description", description)
        .append("parentId", parentId)
        .append("deleted", deleted)
        .toString();
  }
}
