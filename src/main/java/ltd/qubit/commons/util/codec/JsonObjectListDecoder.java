////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The class of codecs which convert strings to objects using JSON format.
 *
 * @param <T>
 *     the type of the objects to be converted.
 */
public class JsonObjectListDecoder<T> implements ObjectListDecoder<T> {

  private final JsonMapper mapper;
  private final Class<T> type;
  private final CollectionType collectionType;

  public JsonObjectListDecoder(final Class<T> type) {
    this(type, new CustomizedJsonMapper());
  }

  public JsonObjectListDecoder(final Class<T> type, final JsonMapper mapper) {
    this.mapper = mapper;
    this.type = type;
    this.collectionType = mapper.getTypeFactory().constructCollectionType(List.class, type);
  }

  public JsonMapper getMapper() {
    return mapper;
  }

  public Class<T> getType() {
    return type;
  }

  public CollectionType getCollectionType() {
    return collectionType;
  }

  @Override
  public List<T> decode(final String source) throws DecodingException {
    try {
      return mapper.readValue(source, collectionType);
    } catch (final JsonProcessingException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("mapper", mapper)
        .append("type", type)
        .append("collectionType", collectionType)
        .toString();
  }
}