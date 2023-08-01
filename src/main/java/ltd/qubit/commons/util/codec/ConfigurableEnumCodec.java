////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.EnumUtils;
import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.text.jackson.XmlMapperUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurableEnumCodec<T extends Enum<T>> implements
        Encoder<T, String>, Decoder<String, T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurableEnumCodec.class);

  private EnumMapper mapper;
  private final Map<String, T> nameToValue = new HashMap<>();
  private final Map<T, String> valueToName = new HashMap<>();

  public ConfigurableEnumCodec() {}

  public ConfigurableEnumCodec(@Nonnull final EnumMapper mapper) {
    this();
    setMapper(mapper);
  }

  public final EnumMapper getMapper() {
    return mapper;
  }

  @SuppressWarnings("unchecked")
  public final ConfigurableEnumCodec<T> setMapper(@Nonnull final EnumMapper mapper) {
    this.mapper = mapper;
    final Class<T> cls;
    try {
      cls = (Class<T>) ClassUtils.getClass(mapper.getClassName());
    } catch (final ClassNotFoundException e) {
      LOGGER.error("Cannot find the class: " + mapper.getClassName());
      throw new RuntimeException(e);
    }
    nameToValue.clear();
    valueToName.clear();
    for (final EnumMap map : mapper.getMaps()) {
      final T value = EnumUtils.forName(map.getValue(), false, true, cls);
      if (value == null) {
        final String message = "No such enumeration: " + map.getValue();
        LOGGER.error(message);
        throw new RuntimeException(message);
      }
      nameToValue.put(map.getName(), value);
      valueToName.put(value, map.getName());
    }
    return this;
  }

  public final ConfigurableEnumCodec<T> setMapperXml(final String resource) {
    final EnumMapper result;
    try {
      result = XmlMapperUtils.parse(resource, EnumMapper.class);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    setMapper(result);
    return this;
  }

  @Override
  public String encode(final T value) {
    return valueToName.get(value);
  }

  @Override
  public T decode(final String str) {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    return nameToValue.get(text);
  }
}
