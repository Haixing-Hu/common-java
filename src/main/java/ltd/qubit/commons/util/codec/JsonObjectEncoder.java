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
 * The class of encoder which convert objects to strings using JSON format.
 *
 * @param <T>
 *     the type of the objects to be converted.
 */
public class JsonObjectEncoder<T> implements ObjectEncoder<T> {

  private final JsonMapper mapper;

  public JsonObjectEncoder() {
    this(new CustomizedJsonMapper());
  }

  public JsonObjectEncoder(final JsonMapper mapper) {
    this.mapper = mapper;
  }

  public JsonMapper getMapper() {
    return mapper;
  }

  @Override
  public String encode(final T source) throws EncodingException {
    try {
      return mapper.writeValueAsString(source);
    } catch (final JsonProcessingException e) {
      throw new EncodingException(e);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("mapper", mapper)
        .toString();
  }
}