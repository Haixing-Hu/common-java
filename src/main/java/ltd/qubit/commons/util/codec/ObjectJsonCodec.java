/// /////////////////////////////////////////////////////////////////////////////
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

/**
 * The class of codecs which convert objects to strings and vice versa using JSON
 * format.
 *
 * @param <T>
 *     the type of the objects to be converted.
 */
public class ObjectJsonCodec<T> implements ObjectCodec<T> {

  private final JsonMapper mapper;
  private final Class<T> type;

  public ObjectJsonCodec(final Class<T> type) {
    this(type, new CustomizedJsonMapper());
  }

  public ObjectJsonCodec(final Class<T> type, final JsonMapper mapper) {
    this.mapper = mapper;
    this.type = type;
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
  public String encode(final T source) throws EncodingException {
    try {
      return mapper.writeValueAsString(source);
    } catch (final JsonProcessingException e) {
      throw new EncodingException(e);
    }
  }
}