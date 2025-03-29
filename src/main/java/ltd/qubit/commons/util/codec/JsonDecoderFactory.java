/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

/**
 * The class of factories which create decoders for JSON objects.
 *
 * @author Haixing Hu
 */
public class JsonDecoderFactory implements DecoderFactory<String> {

  private final JsonMapper mapper;

  /**
   * Creates a new instance of {@link JsonDecoderFactory} with a default
   * {@link CustomizedJsonMapper}.
   */
  public JsonDecoderFactory() {
    this(new CustomizedJsonMapper());
  }

  /**
   * Creates a new instance of {@link JsonDecoderFactory} with the specified
   * {@link JsonMapper}.
   *
   * @param mapper
   *     the {@link JsonMapper} to be used for JSON processing.
   */
  public JsonDecoderFactory(final JsonMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public <T> Decoder<String, T> getDecoder(final Class<T> targetClass) {
    return new JsonObjectDecoder<>(targetClass, mapper);
  }

  @Override
  public <T> ListDecoder<String, T> getListDecoder(final Class<T> targetClass) {
    return new JsonObjectListDecoder<>(targetClass, mapper);
  }
}