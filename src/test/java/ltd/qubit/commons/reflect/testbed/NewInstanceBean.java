////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class NewInstanceBean {

  private String name = "name";
  private String value;

  public NewInstanceBean() {
    value = "value";
    System.out.println("NewInstanceBean.constructor is called.");
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NewInstanceBean other = (NewInstanceBean) o;
    return Equality.equals(name, other.name)
        && Equality.equals(value, other.value);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, value);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("value", value)
        .toString();
  }
}
