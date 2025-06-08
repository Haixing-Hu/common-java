////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class SetterMethodWithType<T, R> {

  private final Class<R> argumentClass;

  private final SetterMethod<T, R> setter;

  public SetterMethodWithType(final Class<R> argumentClass, final SetterMethod<T, R> setter) {
    this.argumentClass = argumentClass;
    this.setter = setter;
  }

  public Class<R> getArgumentClass() {
    return argumentClass;
  }

  public SetterMethod<T, R> getSetter() {
    return setter;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SetterMethodWithType<?, ?> other = (SetterMethodWithType<?, ?>) o;
    return Equality.equals(argumentClass, other.argumentClass)
        && Equality.equals(setter, other.setter);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, argumentClass);
    result = Hash.combine(result, multiplier, setter);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("argumentClass", argumentClass)
        .append("setter", setter)
        .toString();
  }
}