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

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.addPrefixToEachLine;
import static ltd.qubit.commons.text.jackson.JsonMapperUtils.outputJsonArrayCloseTag;
import static ltd.qubit.commons.text.jackson.JsonMapperUtils.outputJsonArrayOpenTag;

/**
 * A serializer which uses Jackson to serialize objects to JSON format.
 *
 * @param <E>
 *     the type of the objects to be serialized.
 * @author Haixing Hu
 */
public class JacksonJsonSerializer<E> implements TextSerializer<E> {
  /**
   * 用于缩进 JSON 的字符串。
   */
  public static final String INDENT = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.getIndent();

  /**
   * 用于换行的字符串。
   */
  public static final String EOL = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.getEol();

  private final JsonMapper mapper;
  private int count;

  /**
   * Constructs a new {@link JacksonJsonSerializer} instance.
   */
  public JacksonJsonSerializer() {
    this(new CustomizedJsonMapper());
  }

  /**
   * Constructs a new {@link JacksonJsonSerializer} instance.
   *
   * @param mapper
   *    the Jackson XML mapper to be used for serialization.
   *    It must not be {@code null}.
   */
  public JacksonJsonSerializer(final JsonMapper mapper) {
    this.mapper = requireNonNull("mapper", mapper);
    if (mapper instanceof CustomizedJsonMapper) {
      ((CustomizedJsonMapper) mapper).setPrettyPrint(true);
    } else {
      mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }
    this.count = 0;
  }

  @Override
  public void init(final Writer writer) throws IOException {
    outputJsonArrayOpenTag(writer);
    writer.write(EOL);
    count = 0;
  }

  @Override
  public void close(final Writer writer) throws IOException {
    if (count > 0) {
      writer.write(EOL);
    }
    outputJsonArrayCloseTag(writer);
    writer.write(EOL);
    writer.flush();
  }

  @Override
  public void serialize(final Writer writer, final E obj) throws IOException {
    serializeList(writer, List.of(obj));
  }

  @Override
  public void serializeList(final Writer writer, final List<E> list) throws IOException {
    for (final E obj : list) {
      if (count > 0) {
        writer.write(',');
        writer.write(EOL);
      }
      final String json = mapper.writeValueAsString(obj);
      final String indentedJson = addPrefixToEachLine(json, INDENT);
      writer.write(indentedJson);
      ++count;
    }
  }
}
