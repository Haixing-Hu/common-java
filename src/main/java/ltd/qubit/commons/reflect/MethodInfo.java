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

import ltd.qubit.commons.lang.CompareToBuilder;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 存储有关方法的信息。
 *
 * @author 胡海星
 */
public final class MethodInfo implements Comparable<MethodInfo> {

  /**
   * 该方法所属的类型信息。
   */
  private final TypeInfo ownerInfo;

  /**
   * 该方法对象。
   */
  private final Method method;

  /**
   * 该方法在类层次结构中的深度。
   */
  private final int depth;

  /**
   * 该方法的名称。
   */
  private final String name;

  /**
   * 该方法的实际参数类型数组。
   */
  private final Class<?>[] actualParameterType;

  /**
   * 该方法的实际返回类型。
   */
  private final Class<?> actualReturnType;

  /**
   * 该方法的签名。
   */
  private final MethodSignature signature;

  /**
   * 构造一个新的方法信息对象。
   *
   * @param ownerInfo
   *     拥有者类型信息
   * @param method
   *     方法对象
   * @param depth
   *     方法在类层次结构中的深度
   */
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

  /**
   * 从另一个方法信息对象构造一个新的方法信息对象，但使用不同的深度。
   *
   * @param methodInfo
   *     另一个方法信息对象
   * @param depth
   *     新的深度
   */
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

  /**
   * 获取拥有者类型信息。
   *
   * @return 拥有者类型信息
   */
  public final TypeInfo getOwnerInfo() {
    return ownerInfo;
  }

  /**
   * 获取方法对象。
   *
   * @return 方法对象
   */
  public final Method getMethod() {
    return method;
  }

  /**
   * 获取方法在类层次结构中的深度。
   *
   * @return 深度
   */
  public final int getDepth() {
    return depth;
  }

  /**
   * 获取方法名称。
   *
   * @return 方法名称
   */
  public final String getName() {
    return name;
  }

  /**
   * 获取实际参数类型数组。
   *
   * @return 实际参数类型数组
   */
  public final Class<?>[] getActualParameterType() {
    return actualParameterType;
  }

  /**
   * 获取实际返回类型。
   *
   * @return 实际返回类型
   */
  public final Class<?> getActualReturnType() {
    return actualReturnType;
  }

  /**
   * 获取方法签名。
   *
   * @return 方法签名
   */
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