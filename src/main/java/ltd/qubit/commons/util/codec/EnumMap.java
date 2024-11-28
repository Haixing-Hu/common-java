////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The map between a enumerator and its name.
 *
 * @author Haixing Hu
 */
public class EnumMap {

  private String value;

  private String name;

  public final String getValue() {
    return value;
  }

  public final EnumMap setValue(final String value) {
    this.value = value;
    return this;
  }

  public final String getName() {
    return name;
  }

  public final EnumMap setName(final String name) {
    this.name = name;
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
    final EnumMap other = (EnumMap) o;
    return Equality.equals(value, other.value)
            && Equality.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, name);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("value", value)
            .append("name", name)
            .toString();
  }
}
