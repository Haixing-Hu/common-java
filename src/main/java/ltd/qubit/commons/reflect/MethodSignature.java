////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 表示方法的签名。
 *
 * @author 胡海星
 */
final class MethodSignature {

  /**
   * 方法名。
   */
  public final String name;

  /**
   * 参数类型数组。
   */
  public final Class<?>[] parameterTypes;

  /**
   * 根据方法对象构造方法签名。
   *
   * @param method
   *     方法对象。
   */
  public MethodSignature(final Method method) {
    this.name = method.getName();
    this.parameterTypes = method.getParameterTypes();
  }

  /**
   * 根据方法名和参数类型构造方法签名。
   *
   * @param name
   *     方法名。
   * @param parameterTypes
   *     参数类型数组。
   */
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