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

  private final Class<? extends Enum> enumClass;

  private final boolean supportJsonValue;

  @Nullable
  private final Method valueGetter;

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
    if (supportJsonValue) {
      this.valueGetter = getValueGetter(enumClass);
    } else {
      this.valueGetter = null;
    }
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
    final Enum result = (valueGetter == null ? decodeWithName(str) : decodeWithValue(str));
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

  private Enum decodeWithValue(final String value) throws DecodingException {
    assert valueGetter != null;
    final Enum[] values = enumClass.getEnumConstants();
    for (final Enum e : values) {
      final Object v;
      try {
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
    } else if (valueGetter != null) {
      try {
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
