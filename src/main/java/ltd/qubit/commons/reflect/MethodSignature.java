////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Method;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Represents the signature of a method.
 *
 * @author Haixing Hu
 */
final class MethodSignature {
  public final String name;
  public final Class<?>[] parameterTypes;

  public MethodSignature(final Method method) {
    this.name = method.getName();
    this.parameterTypes = method.getParameterTypes();
  }

  public MethodSignature(final String name, final Class<?>[] parameterTypes) {
    this.name = name;
    this.parameterTypes = parameterTypes;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final MethodSignature other = (MethodSignature) o;
    return Equality.equals(name, other.name)
        && Equality.equals(parameterTypes, other.parameterTypes);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, parameterTypes);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("parameters", parameterTypes)
        .toString();
  }
}
