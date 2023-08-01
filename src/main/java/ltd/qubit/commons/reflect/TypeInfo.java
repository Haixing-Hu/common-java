////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * Stores the meta information about a type.
 *
 * @author Haixing Hu
 */
@Immutable
public final class TypeInfo {

  private final Class<?> type;
  private final Type[] parameters;
  private final Type[] actualArguments;
  private final Map<Type, Class<?>> genericTypeMap = new HashMap<>();

  /**
   * Create a {@link TypeInfo} gathering the meta-information about a type.
   *
   * @param type
   *     a {@link Type} object, which should be either a {@link Class} object or
   *     a {@link ParameterizedType} object.
   */
  public TypeInfo(final Type type) {
    this(type, null);
  }

  /**
   * Create a {@link TypeInfo} gathering the meta-information about a type.
   *
   * @param type
   *     a {@link Type} object, which should be either a {@link Class} object or
   *     a {@link ParameterizedType} object.
   * @param subTypeInfo
   *     the meta-information about the sub-type of the specified type.
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

  public Class<?> getType() {
    return type;
  }

  public Type[] getParameters() {
    return parameters;
  }

  public Type[] getActualArguments() {
    return actualArguments;
  }

  public Map<Type, Class<?>> getGenericTypeMap() {
    return genericTypeMap;
  }

  /**
   * Resolve the actual type of a generic type.
   *
   * @param genericType
   *     the generic type, which may be a class, a type variable, a parameterized
   *     type, or a generic array type.
   * @return
   *     the resolved actual type of the generic type, or {@code Object.class}
   *     if the generic type cannot be resolved.
   * @throws IllegalArgumentException
   *     if the implementation of the generic type is not supported.
   */
  public Class<?> resolveActualType(final Type genericType) {
    return resolveActualType(Object.class, genericType);
  }

  /**
   * Resolve the actual type of a generic type.
   *
   * @param erasureType
   *     the erasure type of the generic type, i.e., if the generic type is an
   *     unsolvable type variable, it will be resolved to this erasure type.
   * @param genericType
   *     the generic type, which may be a class, a type variable, a parameterized
   *     type, or a generic array type.
   * @return
   *     the resolved actual type of the generic type.
   * @throws IllegalArgumentException
   *     if the implementation of the generic type is not supported.
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
   * Resolve the actual types of an array of generic types.
   *
   * @param genericTypes
   *     the array of generic type, whose element may be a class, a type
   *     variable, a parameterized type, or a generic array type.
   * @return
   *     the array of resolved actual types of the array of generic types.
   * @throws IllegalArgumentException
   *     if the implementation of the generic type is not supported.
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
   * Resolve the actual types of an array of generic types.
   *
   * @param erasureTypes
   *     the array of erasure types of the generic types, i.e., if a generic
   *     type is an unsolvable type variable, it will be resolved to the
   *     corresponding erasure type in this array.
   * @param genericTypes
   *     the array of generic type, whose element may be a class, a type
   *     variable, a parameterized type, or a generic array type.
   * @return
   *     the array of resolved actual types of the array of generic types.
   * @throws IllegalArgumentException
   *     if the implementation of the generic type is not supported.
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
