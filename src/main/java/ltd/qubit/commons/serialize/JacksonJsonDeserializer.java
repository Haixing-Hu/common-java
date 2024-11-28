////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A deserializer which uses Jackson to deserialize objects from JSON format.
 *
 * @param <E>
 *     the type of the objects to be deserialized.
 * @author Haixing Hu
 */
public class JacksonJsonDeserializer<E> implements TextDeserializer<E> {

  private final Class<E> type;
  private final JsonMapper mapper;

  /**
   * Constructs a new {@link JacksonJsonDeserializer} instance.
   *
   * @param type
   *    the class object of the type of the objects to be serialized.
   */
  public JacksonJsonDeserializer(final Class<E> type) {
    this(type, new CustomizedJsonMapper());
  }

  /**
   * Constructs a new {@link JacksonJsonDeserializer} instance.
   *
   * @param type
   *    the class object of the type of the objects to be serialized.
   *    It must not be {@code null}.
   * @param mapper
   *    the Jackson XML mapper to be used for serialization.
   *    It must not be {@code null}.
   */
  public JacksonJsonDeserializer(final Class<E> type, final JsonMapper mapper) {
    this.type = requireNonNull("type", type);
    this.mapper = requireNonNull("mapper", mapper);
  }

  @Override
  public E deserialize(final Reader reader) throws SerializationException {
    try {
      return mapper.readValue(reader, type);
    } catch (final IOException e) {
      throw new SerializationException(e);
    }
  }

  @Override
  public List<E> deserializeList(final Reader reader) throws SerializationException {
    try {
      return JsonMapperUtils.parseList(reader, type, mapper);
    } catch (final IOException e) {
      throw new SerializationException(e);
    }
  }
}
