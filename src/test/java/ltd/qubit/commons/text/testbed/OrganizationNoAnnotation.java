////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.time.Instant;

public class OrganizationNoAnnotation {

  private Long id;

  private String name;

  private String code;

  private String address;

  private Instant createTime;

  private Instant modifyTime;

  private Instant deleteTime;

  public OrganizationNoAnnotation() {
    // empty
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  public Instant getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Instant getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final OrganizationNoAnnotation other = (OrganizationNoAnnotation) o;
    return Equality.equals(id, other.id)
        && Equality.equals(name, other.name)
        && Equality.equals(code, other.code)
        && Equality.equals(address, other.address)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, address);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("code", code)
        .append("address", address)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
