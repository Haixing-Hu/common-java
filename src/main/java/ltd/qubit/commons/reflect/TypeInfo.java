////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 存储类型的元信息。
 *
 * @author 胡海星
 */
@Immutable
public final class TypeInfo {

  private final Class<?> type;
  private final Type[] parameters;
  private final Type[] actualArguments;
  private final Map<Type, Class<?>> genericTypeMap = new HashMap<>();

  /**
   * 创建一个 {@link TypeInfo} 对象，用于收集类型的元信息。
   *
   * @param type
   *     一个 {@link Type} 对象，应该是 {@link Class} 对象或 {@link ParameterizedType} 对象。
   */
  public TypeInfo(final Type type) {
    this(type, null);
  }

  /**
   * 创建一个 {@link TypeInfo} 对象，用于收集类型的元信息。
   *
   * @param type
   *     一个 {@link Type} 对象，应该是 {@link Class} 对象或 {@link ParameterizedType} 对象。
   * @param subTypeInfo
   *     指定类型的子类型的元信息。
   */
  public TypeInfo(final Type type, @Nullable final TypeInfo subTypeInfo) {
    requireNonNull("type", type);
    if (type instanceof Class) {
      final Class<?> theType = (Class<?>) type;
      this.type = theType;
      this.parameters = theType.getTypeParameters();
      this.actualArguments = ArrayUtils.EMPTY_TYPE_ARRAY;
    } else if (type instanceof ParameterizedType) {
      final ParameterizedType theType = (ParameterizedType) type;
      this.type = (Class<?>) theType.getRawType();
      this.parameters = this.type.getTypeParameters();
      this.actualArguments = theType.getActualTypeArguments();
      buildGenericTypeMap(subTypeInfo);
    } else {
      throw new IllegalArgumentException("Unsupported type object: " + type.getClass());
    }
  }

  private void buildGenericTypeMap(@Nullable final TypeInfo subTypeInfo) {
    assert (parameters.length == actualArguments.length);
    for (int i = 0; i < parameters.length; ++i) {
      final Type genericType = parameters[i];
      final Type actualType = actualArguments[i];
      if (actualType instanceof Class) {
        genericTypeMap.put(genericType, (Class<?>) actualType);
      } else {
        if (subTypeInfo == null) {
          throw new IllegalArgumentException("Cannot resolve the generic type "
              + "parameter " + actualType.getTypeName() + " of " + type.toGenericString());
        }
        // FIXME: how can we get the erasure parameter type of a generic class?
        // FIXME: can we simply use the Object.class?
        final Class<?> resolvedType = subTypeInfo.resolveActualType(Object.class, actualType);
        genericTypeMap.put(genericType, resolvedType);
        actualArguments[i] = resolvedType;
      }
    }
  }

  /**
   * 获取类型。
   *
   * @return 类型。
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * 获取类型参数。
   *
   * @return 类型参数数组。
   */
  public Type[] getParameters() {
    return parameters;
  }

  /**
   * 获取实际类型参数。
   *
   * @return 实际类型参数数组。
   */
  public Type[] getActualArguments() {
    return actualArguments;
  }

  /**
   * 获取泛型类型映射。
   *
   * @return 从泛型类型到实际类型的映射。
   */
  public Map<Type, Class<?>> getGenericTypeMap() {
    return genericTypeMap;
  }

  /**
   * 解析泛型类型的实际类型。
   *
   * @param genericType
   *     泛型类型，可能是类、类型变量、参数化类型或泛型数组类型。
   * @return
   *     泛型类型的解析后的实际类型，如果泛型类型无法解析则返回 {@code Object.class}。
   * @throws IllegalArgumentException
   *     如果不支持该泛型类型的实现。
   */
  public Class<?> resolveActualType(final Type genericType) {
    return resolveActualType(Object.class, genericType);
  }

  /**
   * 解析泛型类型的实际类型。
   *
   * @param erasureType
   *     泛型类型的擦除类型，即如果泛型类型是无法解析的类型变量，则将其解析为此擦除类型。
   * @param genericType
   *     泛型类型，可能是类、类型变量、参数化类型或泛型数组类型。
   * @return
   *     泛型类型的解析后的实际类型。
   * @throws IllegalArgumentException
   *     如果不支持该泛型类型的实现。
   */
  public Class<?> resolveActualType(final Class<?> erasureType,
      final Type genericType) {
    if (genericType instanceof Class) {
      return (Class<?>) genericType;
    } else if (genericType instanceof TypeVariable) {
      final Class<?> actualType = genericTypeMap.get(genericType);
      return (actualType == null ? erasureType : actualType);
    } else if (genericType instanceof ParameterizedType) {
      final ParameterizedType theType = (ParameterizedType) genericType;
      return (Class<?>) theType.getRawType();
    } else if (genericType instanceof GenericArrayType) {
      final GenericArrayType theType = (GenericArrayType) genericType;
      final Type genericComponentType = theType.getGenericComponentType();
      final Class<?> erasureComponentType = erasureType.getComponentType();
      final Class<?> actualComponentType = resolveActualType(
          erasureComponentType, genericComponentType);
      return Array.newInstance(actualComponentType, 0).getClass();
    } else if (genericType instanceof WildcardType) {
      return erasureType;
    } else {
      throw new IllegalArgumentException("Unsupported type object: "
          + genericType.getClass());
    }
  }

  /**
   * 解析泛型类型数组的实际类型。
   *
   * @param genericTypes
   *     泛型类型数组，其元素可能是类、类型变量、参数化类型或泛型数组类型。
   * @return
   *     泛型类型数组的解析后的实际类型数组。
   * @throws IllegalArgumentException
   *     如果不支持某个泛型类型的实现。
   */
  @NotNull
  public Class<?>[] resolveActualTypes(final Type[] genericTypes) {
    if (genericTypes == null || genericTypes.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Class<?>[] result = new Class<?>[genericTypes.length];
    for (int i = 0; i < genericTypes.length; ++i) {
      result[i] = resolveActualType(genericTypes[i]);
    }
    return result;
  }

  /**
   * 解析泛型类型数组的实际类型。
   *
   * @param erasureTypes
   *     泛型类型的擦除类型数组，即如果泛型类型是无法解析的类型变量，
   *     则将其解析为此数组中对应的擦除类型。
   * @param genericTypes
   *     泛型类型数组，其元素可能是类、类型变量、参数化类型或泛型数组类型。
   * @return
   *     泛型类型数组的解析后的实际类型数组。
   * @throws IllegalArgumentException
   *     如果不支持某个泛型类型的实现。
   */
  @NotNull
  public Class<?>[] resolveActualTypes(final Class<?>[] erasureTypes,
      final Type[] genericTypes) {
    if (genericTypes == null || genericTypes.length == 0) {
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Class<?>[] result = new Class<?>[genericTypes.length];
    for (int i = 0; i < genericTypes.length; ++i) {
      result[i] = resolveActualType(erasureTypes[i], genericTypes[i]);
    }
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final TypeInfo other = (TypeInfo) o;
    return Equality.equals(type, other.type)
        && Equality.equals(parameters, other.parameters)
        && Equality.equals(actualArguments, other.actualArguments)
        && Equality.equals(genericTypeMap, other.genericTypeMap);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, parameters);
    result = Hash.combine(result, multiplier, actualArguments);
    result = Hash.combine(result, multiplier, genericTypeMap);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("parameters", parameters)
        .append("actualArguments", actualArguments)
        .append("genericTypeMap", genericTypeMap)
        .toString();
  }
}