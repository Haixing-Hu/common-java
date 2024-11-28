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
 * Stores the meta-information about a field.
 *
 * @author Haixing Hu
 */
@Immutable
public final class FieldInfo implements Comparable<FieldInfo> {

  private final TypeInfo ownerInfo;
  private final Field field;
  private final int depth;
  private final String name;
  private final Class<?> actualType;

  @Nullable
  private final Class<?> actualComponentType;

  @Nullable
  private final Class<?>[] actualTypeArguments;
  private final FieldSignature signature;

  /**
   * Constructs a {@link FieldInfo}.
   *
   * @param ownerInfo
   *          the information about the owner class.
   * @param field
   *          the field.
   * @param depth
   *          the depth of the field in the owner class.
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

  public final TypeInfo getOwnerInfo() {
    return ownerInfo;
  }

  public final Field getField() {
    return field;
  }

  public final int getDepth() {
    return depth;
  }

  public final String getName() {
    return name;
  }

  public final Class<?> getActualType() {
    return actualType;
  }

  @Nullable
  public Class<?> getActualComponentType() {
    return actualComponentType;
  }

  @Nullable
  public Class<?>[] getActualTypeArguments() {
    return actualTypeArguments;
  }

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
