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
import java.io.Writer;
import java.util.List;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.jackson.JsonMapperUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A serializer which uses Jackson to serialize objects to JSON format.
 *
 * @param <E>
 *     the type of the objects to be serialized.
 * @author Haixing Hu
 */
public class JacksonJsonSerializer<E> implements TextSerializer<E> {

  private final Class<E> type;
  private final JsonMapper mapper;

  /**
   * Constructs a new {@link JacksonJsonSerializer} instance.
   *
   * @param type
   *    the class object of the type of the objects to be serialized.
   */
  public JacksonJsonSerializer(final Class<E> type) {
    this(type, new CustomizedJsonMapper());
  }

  /**
   * Constructs a new {@link JacksonJsonSerializer} instance.
   *
   * @param type
   *    the class object of the type of the objects to be serialized.
   *    It must not be {@code null}.
   * @param mapper
   *    the Jackson XML mapper to be used for serialization.
   *    It must not be {@code null}.
   */
  public JacksonJsonSerializer(final Class<E> type, final JsonMapper mapper) {
    this.type = requireNonNull("type", type);
    this.mapper = requireNonNull("mapper", mapper);
  }

  @Override
  public void serialize(final Writer writer, final E obj) throws SerializationException {
    try {
      mapper.writeValue(writer, obj);
    } catch (final IOException e) {
      throw new SerializationException(e);
    }
  }

  @Override
  public void serializeList(final Writer writer, final List<E> list) throws SerializationException {
    try {
      final String xml = JsonMapperUtils.formatList(list, mapper);
      writer.write(xml);
    } catch (final IOException e) {
      throw new SerializationException(e);
    }
  }
}
