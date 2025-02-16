////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonValue;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * The codec of the enumeration class.
 *
 * @author Haixing Hu
 */
@SuppressWarnings("rawtypes")
@Immutable
public class RawEnumCodec implements Codec<Enum, String> {

  /**
   * A cache used to store the value getter method of each enumeration class.
   */
  private static final ClassValue<Method> VALUE_GETTER_CACHE = new ClassValue<>() {
    @Override
    protected Method computeValue(@NotNull final Class<?> type) {
      for (final Method method : type.getDeclaredMethods()) {
        if ((method.getParameterCount() == 0) && method.isAnnotationPresent(JsonValue.class)) {
          return method;
        }
      }
      return null;
    }
  };

  private final Class<? extends Enum> enumClass;

  private final boolean supportJsonValue;

  /**
   * Constructs a {@link RawEnumCodec} object.
   */
  public RawEnumCodec() {
    this(Enum.class, false);
  }

  /**
   * Constructs a {@link RawEnumCodec} object.
   *
   * @param supportJsonValue
   *     whether to support the {@link JsonValue} annotation.
   */
  public RawEnumCodec(final boolean supportJsonValue) {
    this(Enum.class, supportJsonValue);
  }

  /**
   * Constructs a {@link RawEnumCodec} object.
   *
   * @param enumClass
   *     the class object of the enumeration class.
   */
  public RawEnumCodec(final Class<? extends Enum> enumClass) {
    this(enumClass, false);
  }

  /**
   * Constructs a {@link RawEnumCodec} object.
   *
   * @param enumClass
   *     the class object of the enumeration class.
   * @param supportJsonValue
   *     whether to support the {@link JsonValue} annotation.
   */
  public RawEnumCodec(final Class<? extends Enum> enumClass, final boolean supportJsonValue) {
    this.enumClass = requireNonNull("enumClass", enumClass);
    this.supportJsonValue = supportJsonValue;
  }

  private static Method getValueGetter(final Class<? extends Enum> enumClass) {
    for (final Method method : enumClass.getDeclaredMethods()) {
      if ((method.getParameterCount() == 0) && method.isAnnotationPresent(JsonValue.class)) {
        return method;
      }
    }
    return null;
  }

  /**
   * Gets the class object of the enumeration class.
   *
   * @return
   *     the class object of the enumeration class.
   */
  public final Class<? extends Enum> getEnumClass() {
    return enumClass;
  }

  /**
   * Gets whether to support the {@link JsonValue} annotation.
   *
   * @return
   *     whether to support the {@link JsonValue} annotation.
   */
  public boolean isSupportJsonValue() {
    return supportJsonValue;
  }

  @Override
  public Enum decode(final String source) throws DecodingException {
    final String str = new Stripper().strip(source);
    if (isEmpty(str)) {
      return null;
    }
    final Enum result;
    if (supportJsonValue) {
      final Method valueGetter = VALUE_GETTER_CACHE.get(enumClass);
      if (valueGetter != null) {
        result = decodeWithValue(str, valueGetter);
      } else {
        result = decodeWithName(str);
      }
    } else {
      result = decodeWithName(str);
    }
    if (result != null) {
      return result;
    }
    final String message = String.format("Cannot deserialize value of type `%s`"
      + " from string \"%s\": not one of the values accepted for Enum class: %s",
        enumClass.getName(), source, ArrayUtils.toString(enumClass.getEnumConstants()));
    throw new DecodingException(message);
  }

  @Nullable
  private Enum decodeWithName(final String name) {
    // normalize the enum names
    final String normalizedName = name.toUpperCase().replace('-', '_');
    final Enum[] values = enumClass.getEnumConstants();
    for (final Enum e : values) {
      if (e.name().toUpperCase().equals(normalizedName)) {
        return e;
      }
    }
    return null;
  }

  private Enum decodeWithValue(final String value, final Method valueGetter)
      throws DecodingException {
    final Enum[] values = enumClass.getEnumConstants();
    for (final Enum e : values) {
      final Object v;
      try {
        valueGetter.setAccessible(true);
        v = valueGetter.invoke(e);
      } catch (final IllegalAccessException | InvocationTargetException ex) {
        throw new DecodingException(ex);
      }
      if (v.equals(value)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public String encode(final Enum source) throws EncodingException {
    if (source == null) {
      return null;
    }
    // Note that we must use the actual class of the enumerator to get the value getter method.
    final Method valueGetter = VALUE_GETTER_CACHE.get(source.getClass());
    if (supportJsonValue && (valueGetter != null)) {
      try {
        valueGetter.setAccessible(true);
        final Object value = valueGetter.invoke(source);
        return (value == null ? null : value.toString());
      } catch (final IllegalAccessException | InvocationTargetException e) {
        throw new EncodingException(e);
      }
    } else {
      return source.name();
    }
  }
}
