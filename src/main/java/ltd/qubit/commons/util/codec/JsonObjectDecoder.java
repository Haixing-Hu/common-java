////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The class of codecs which convert strings to objects using JSON format.
 *
 * @param <T>
 *     the type of the objects to be converted.
 */
public class JsonObjectDecoder<T> implements ObjectDecoder<T> {

  private final JsonMapper mapper;
  private final Class<T> type;

  public JsonObjectDecoder(final Class<T> type) {
    this(type, new CustomizedJsonMapper());
  }

  public JsonObjectDecoder(final Class<T> type, final JsonMapper mapper) {
    this.mapper = mapper;
    this.type = type;
  }

  public JsonMapper getMapper() {
    return mapper;
  }

  public Class<T> getType() {
    return type;
  }

  @Override
  public T decode(final String source) throws DecodingException {
    try {
      return mapper.readValue(source, type);
    } catch (final JsonProcessingException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("mapper", mapper)
        .append("type", type)
        .toString();
  }
}