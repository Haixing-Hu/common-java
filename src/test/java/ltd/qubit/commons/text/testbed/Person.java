////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility = ANY,
    getterVisibility = NONE,
    isGetterVisibility = NONE,
    setterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
public class Person {

  private Long id;

  private String name;

  private String code;

  private Organization company;

  private Map<String, String> payload;

  private Instant createTime;

  public Person() {
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

  public Organization getCompany() {
    return company;
  }

  public void setCompany(final Organization company) {
    this.company = company;
  }

  public Map<String, String> getPayload() {
    return payload;
  }

  public void setPayload(final Map<String, String> payload) {
    this.payload = payload;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Person other = (Person) o;
    return Equality.equals(id, other.id)
        && Equality.equals(name, other.name)
        && Equality.equals(code, other.code)
        && Equality.equals(company, other.company)
        && Equality.equals(payload, other.payload)
        && Equality.equals(createTime, other.createTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, company);
    result = Hash.combine(result, multiplier, payload);
    result = Hash.combine(result, multiplier, createTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("code", code)
        .append("company", company)
        .append("payload", payload)
        .append("createTime", createTime)
        .toString();
  }
}
