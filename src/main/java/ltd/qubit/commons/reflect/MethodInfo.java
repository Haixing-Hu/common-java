////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Method;

import ltd.qubit.commons.lang.CompareToBuilder;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Stores the information about a method.
 *
 * @author Haixing Hu
 */
public final class MethodInfo implements Comparable<MethodInfo> {

  private final TypeInfo ownerInfo;
  private final Method method;
  private final int depth;
  private final String name;
  private final Class<?>[] actualParameterType;
  private final Class<?> actualReturnType;
  private final MethodSignature signature;

  public MethodInfo(final TypeInfo ownerInfo, final Method method, final int depth) {
    this.ownerInfo = requireNonNull("ownerInfo", ownerInfo);
    this.method = requireNonNull("method", method);
    this.depth = requireNonNegative("depth", depth);
    this.name = method.getName();
    this.actualParameterType = ownerInfo.resolveActualTypes(method.getParameterTypes(),
        method.getGenericParameterTypes());
    this.actualReturnType = ownerInfo.resolveActualType(method.getReturnType(),
        method.getGenericReturnType());
    this.signature = new MethodSignature(this.name, this.actualParameterType);
  }

  public MethodInfo(final MethodInfo methodInfo, final int depth) {
    requireNonNull("methodInfo", methodInfo);
    this.ownerInfo = methodInfo.ownerInfo;
    this.method = methodInfo.method;
    this.depth = requireNonNegative("depth", depth);
    this.name = methodInfo.name;
    this.actualParameterType = methodInfo.actualParameterType;
    this.actualReturnType = methodInfo.actualReturnType;
    this.signature = methodInfo.signature;
  }

  public final TypeInfo getOwnerInfo() {
    return ownerInfo;
  }

  public final Method getMethod() {
    return method;
  }

  public final int getDepth() {
    return depth;
  }

  public final String getName() {
    return name;
  }

  public final Class<?>[] getActualParameterType() {
    return actualParameterType;
  }

  public final Class<?> getActualReturnType() {
    return actualReturnType;
  }

  public final MethodSignature getSignature() {
    return signature;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final MethodInfo other = (MethodInfo) o;
    return Equality.equals(depth, other.depth)
        && Equality.equals(ownerInfo, other.ownerInfo)
        && Equality.equals(method, other.method)
        && Equality.equals(name, other.name)
        && Equality.equals(actualParameterType, other.actualParameterType)
        && Equality.equals(actualReturnType, other.actualReturnType)
        && Equality.equals(signature, other.signature);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, ownerInfo);
    result = Hash.combine(result, multiplier, method);
    result = Hash.combine(result, multiplier, depth);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, actualParameterType);
    result = Hash.combine(result, multiplier, actualReturnType);
    result = Hash.combine(result, multiplier, signature);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("depth", depth)
        .append("ownerInfo", ownerInfo)
        .append("method", method)
        .append("actualParameterType", actualParameterType)
        .append("actualReturnType", actualReturnType)
        .append("signature", signature)
        .toString();
  }

  @Override
  public int compareTo(final MethodInfo other) {
    if (other == null) {
      return +1;
    }
    return new CompareToBuilder()
        .append(name, other.name)
        .append(depth, other.depth)
        .append(actualParameterType, other.actualParameterType)
        .append(actualReturnType, other.actualReturnType)
        .compare();
  }
}
