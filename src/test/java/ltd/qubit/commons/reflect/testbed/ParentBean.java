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
import ltd.qubit.commons.annotation.Identifier;

public class ParentBean extends AbstractIdentifiable<Long, ParentBean> {

  private static final long serialVersionUID = 4489874225419221524L;
  @Identifier
  private Long id;

  private Number number;

  public final Long getId() {
    return id;
  }

  public final ParentBean setId(final Long id) {
    this.id = id;
    return this;
  }

  public Number getNumber() {
    return number;
  }

  public ParentBean setNumber(final Number number) {
    this.number = number;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ParentBean other = (ParentBean) o;
    return Equality.equals(id, other.id)
        && Equality.equals(number, other.number);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, number);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("number", number)
        .toString();
  }
}
