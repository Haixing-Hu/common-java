////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class PersonNoAnnotation {

  private Long id;

  private String name;

  private String code;

  private Gender gender;

  private OrganizationNoAnnotation company;

  private Map<String, String> payload;

  private List<String> jobTags;

  private Instant createTime;

  private Instant modifyTime;

  private Instant deleteTime;

  public PersonNoAnnotation() {
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

  public Gender getGender() {
    return gender;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  public OrganizationNoAnnotation getCompany() {
    return company;
  }

  public void setCompany(final OrganizationNoAnnotation company) {
    this.company = company;
  }

  public Map<String, String> getPayload() {
    return payload;
  }

  public void setPayload(final Map<String, String> payload) {
    this.payload = payload;
  }

  public List<String> getJobTags() {
    return jobTags;
  }

  public void setJobTags(final List<String> jobTags) {
    this.jobTags = jobTags;
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
    final PersonNoAnnotation other = (PersonNoAnnotation) o;
    return Equality.equals(id, other.id)
        && Equality.equals(name, other.name)
        && Equality.equals(code, other.code)
        && Equality.equals(gender, other.gender)
        && Equality.equals(company, other.company)
        && Equality.equals(payload, other.payload)
        && Equality.equals(jobTags, other.jobTags)
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
    result = Hash.combine(result, multiplier, gender);
    result = Hash.combine(result, multiplier, company);
    result = Hash.combine(result, multiplier, payload);
    result = Hash.combine(result, multiplier, jobTags);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this).append("id", id)
        .append("name", name)
        .append("code", code)
        .append("gender", gender)
        .append("company", company)
        .append("payload", payload)
        .append("tags", jobTags)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
