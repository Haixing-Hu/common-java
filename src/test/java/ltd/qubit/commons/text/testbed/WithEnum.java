////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class WithEnum {

  private ValueEnum value;

  public ValueEnum getValue() {
    return value;
  }

  public void setValue(final ValueEnum value) {
    this.value = value;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final WithEnum other = (WithEnum) o;
    return Equality.equals(value, other.value);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, value);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("value", value)
        .toString();
  }
}
