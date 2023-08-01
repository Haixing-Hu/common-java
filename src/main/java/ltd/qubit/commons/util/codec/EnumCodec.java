////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.Stripper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumCodec<T extends Enum<T>> implements Codec<T, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(EnumCodec.class);

  private final Map<T, String> valueToName = new HashMap<>();
  private final Map<String, T> nameToValue = new HashMap<>();
  private boolean emptyForNull = false;

  public EnumCodec(final Class<T> cls) {
    this(cls, false);
  }

  public EnumCodec(final Class<T> cls, final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    for (final T e : cls.getEnumConstants()) {
      valueToName.put(e, e.name());
      nameToValue.put(e.name(), e);
    }
  }

  public EnumCodec(final Map<T, String> map) {
    this(map, false);
  }

  public EnumCodec(final Map<T, String> map, final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    for (final Map.Entry<T, String> entry : map.entrySet()) {
      valueToName.put(entry.getKey(), entry.getValue());
      nameToValue.put(entry.getValue(), entry.getKey());
    }
  }

  public final EnumCodec<T> setMap(final Map<T, String> map) {
    nameToValue.clear();
    valueToName.clear();
    for (final Map.Entry<T, String> entry : map.entrySet()) {
      valueToName.put(entry.getKey(), entry.getValue());
      nameToValue.put(entry.getValue(), entry.getKey());
    }
    return this;
  }

  public final boolean isEmptyForNull() {
    return emptyForNull;
  }

  public final EnumCodec<T> setEmptyForNull(final boolean emptyForNull) {
    this.emptyForNull = emptyForNull;
    return this;
  }

  @Override
  public String encode(@Nullable final T value) {
    if (value == null) {
      return (emptyForNull ? StringUtils.EMPTY : null);
    } else {
      return valueToName.get(value);
    }
  }

  @Override
  public T decode(@Nullable final String str) {
    final String text = new Stripper().strip(str);
    if (text == null) {
      return null;
    } else if (text.length() == 0) {
      if (emptyForNull) {
        return null;
      }
    }
    if (nameToValue.containsKey(text)) {
      return nameToValue.get(text);
    } else {
      LOGGER.error("{} cannot deserialize enumeration value: {}",
              ClassUtils.getShortClassName(this.getClass()), text);
      return null;
    }
  }
}
