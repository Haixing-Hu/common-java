////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Represents the signature of a field.
 *
 * @author Haixing Hu
 */
final class FieldSignature {
  public final String name;
  public final Class<?> type;

  public FieldSignature(final Field field) {
    this.name = field.getName();
    this.type = field.getType();
  }

  public FieldSignature(final String name, final Class<?> type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final FieldSignature other = (FieldSignature) o;
    return Equality.equals(name, other.name)
        && Equality.equals(type, other.type);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, type);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("type", type)
        .toString();
  }
}
