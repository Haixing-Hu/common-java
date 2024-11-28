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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;
import ltd.qubit.commons.text.jackson.XmlMapperUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A serializer which uses Jackson to serialize objects to XML format.
 *
 * @param <E>
 *     the type of the objects to be serialized.
 * @author Haixing Hu
 */
public class JacksonXmlSerializer<E> implements TextSerializer<E> {

  private final Class<E> type;
  private final XmlMapper mapper;

  /**
   * Constructs a new {@link JacksonXmlSerializer} instance.
   *
   * @param type
   *    the class object of the type of the objects to be serialized.
   */
  public JacksonXmlSerializer(final Class<E> type) {
    this(type, new CustomizedXmlMapper());
  }

  /**
   * Constructs a new {@link JacksonXmlSerializer} instance.
   *
   * @param type
   *    the class object of the type of the objects to be serialized.
   *    It must not be {@code null}.
   * @param mapper
   *    the Jackson XML mapper to be used for serialization.
   *    It must not be {@code null}.
   */
  public JacksonXmlSerializer(final Class<E> type, final XmlMapper mapper) {
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
      final String xml = XmlMapperUtils.formatList(list, type, mapper);
      writer.write(xml);
    } catch (final IOException e) {
      throw new SerializationException(e);
    }
  }
}
