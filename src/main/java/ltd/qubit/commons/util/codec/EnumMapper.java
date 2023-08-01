////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.util.List;

public class EnumMapper {

  private String className;

  private List<EnumMap> maps;

  public final String getClassName() {
    return className;
  }

  public final EnumMapper setClassName(final String className) {
    this.className = className;
    return this;
  }

  public final List<EnumMap> getMaps() {
    return maps;
  }

  public final EnumMapper setMaps(final List<EnumMap> maps) {
    this.maps = maps;
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
    final EnumMapper other = (EnumMapper) o;
    return Equality.equals(className, other.className)
            && Equality.equals(maps, other.maps);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, className);
    result = Hash.combine(result, multiplier, maps);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("className", className)
            .append("enumMaps", maps)
            .toString();
  }
}
