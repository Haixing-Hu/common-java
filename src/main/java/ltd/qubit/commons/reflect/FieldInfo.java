////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNegative;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 存储关于字段的元信息。
 *
 * @author 胡海星
 */
@Immutable
public final class FieldInfo implements Comparable<FieldInfo> {

  /**
   * 拥有者类型的信息。
   */
  private final TypeInfo ownerInfo;

  /**
   * 字段。
   */
  private final Field field;

  /**
   * 字段在拥有者类中的深度。
   */
  private final int depth;

  /**
   * 字段的名称。
   */
  private final String name;

  /**
   * 字段的实际类型。
   */
  private final Class<?> actualType;

  /**
   * 字段的实际组件类型。
   */
  @Nullable
  private final Class<?> actualComponentType;

  /**
   * 字段的实际类型参数。
   */
  @Nullable
  private final Class<?>[] actualTypeArguments;

  /**
   * 字段的签名。
   */
  private final FieldSignature signature;

  /**
   * 构造一个 {@link FieldInfo} 实例。
   *
   * @param ownerInfo
   *     拥有者类的信息。
   * @param field
   *     字段。
   * @param depth
   *     字段在拥有者类中的深度。
   */
  public FieldInfo(final TypeInfo ownerInfo, final Field field, final int depth) {
    this.ownerInfo = requireNonNull("ownerInfo", ownerInfo);
    this.field = requireNonNull("field", field);
    this.depth = requireNonNegative("depth", depth);
    this.name = field.getName();
    final Type gt = field.getGenericType();
    this.actualType = ownerInfo.resolveActualType(field.getType(), gt);
    if (gt instanceof ParameterizedType) {
      final ParameterizedType pt = (ParameterizedType) gt;
      final Type[] args = pt.getActualTypeArguments();
      this.actualComponentType = null;
      this.actualTypeArguments = ownerInfo.resolveActualTypes(args);
    } else if (gt instanceof GenericArrayType) {
      final GenericArrayType at = (GenericArrayType) gt;
      final Type ct = at.getGenericComponentType();
      this.actualComponentType = ownerInfo.resolveActualType(ct);
      this.actualTypeArguments = null;
    } else {
      this.actualComponentType = null;
      this.actualTypeArguments = null;
    }
    this.signature = new FieldSignature(this.name, this.actualType);
  }

  /**
   * 构造一个 {@link FieldInfo} 实例。
   *
   * @param info
   *     已存在的 {@link FieldInfo} 实例。
   * @param depth
   *     字段在拥有者类中的深度。
   */
  public FieldInfo(final FieldInfo info, final int depth) {
    this.ownerInfo = info.ownerInfo;
    this.field = info.field;
    this.depth = requireNonNegative("depth", depth);
    this.name = info.name;
    this.actualType = info.actualType;
    this.actualComponentType = info.actualComponentType;
    this.actualTypeArguments = Assignment.clone(info.actualTypeArguments);
    this.signature = info.signature;
  }

  /**
   * 获取拥有者类型的信息。
   *
   * @return 拥有者类型的信息。
   */
  public final TypeInfo getOwnerInfo() {
    return ownerInfo;
  }

  /**
   * 获取字段。
   *
   * @return 字段。
   */
  public final Field getField() {
    return field;
  }

  /**
   * 获取字段在拥有者类中的深度。
   *
   * @return 字段在拥有者类中的深度。
   */
  public final int getDepth() {
    return depth;
  }

  /**
   * 获取字段的名称。
   *
   * @return 字段的名称。
   */
  public final String getName() {
    return name;
  }

  /**
   * 获取字段的实际类型。
   *
   * @return 字段的实际类型。
   */
  public final Class<?> getActualType() {
    return actualType;
  }

  /**
   * 获取字段的实际组件类型。
   *
   * @return 字段的实际组件类型，如果字段不是数组类型则返回 {@code null}。
   */
  @Nullable
  public Class<?> getActualComponentType() {
    return actualComponentType;
  }

  /**
   * 获取字段的实际类型参数。
   *
   * @return 字段的实际类型参数，如果字段不是泛型类型则返回 {@code null}。
   */
  @Nullable
  public Class<?>[] getActualTypeArguments() {
    return actualTypeArguments;
  }

  /**
   * 获取字段的签名。
   *
   * @return 字段的签名。
   */
  public final FieldSignature getSignature() {
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
    final FieldInfo other = (FieldInfo) o;
    return Equality.equals(depth, other.depth)
        && Equality.equals(ownerInfo, other.ownerInfo)
        && Equality.equals(field, other.field)
        && Equality.equals(name, other.name)
        && Equality.equals(actualType, other.actualType)
        && Equality.equals(actualComponentType, other.actualComponentType)
        && Equality.equals(actualTypeArguments, other.actualTypeArguments)
        && Equality.equals(signature, other.signature);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, field);
    result = Hash.combine(result, multiplier, depth);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, ownerInfo);
    result = Hash.combine(result, multiplier, actualType);
    result = Hash.combine(result, multiplier, actualComponentType);
    result = Hash.combine(result, multiplier, actualTypeArguments);
    result = Hash.combine(result, multiplier, signature);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("depth", depth)
        .append("ownerInfo", ownerInfo)
        .append("actualType", actualType)
        .append("actualComponentType", actualComponentType)
        .append("actualTypeArguments", actualTypeArguments)
        .append("signature", signature)
        .toString();
  }

  @Override
  public int compareTo(final FieldInfo other) {
    if (other == null) {
      return +1;
    }
    // sort by depth, keep the declaring order
    return depth - other.depth;
  }
}