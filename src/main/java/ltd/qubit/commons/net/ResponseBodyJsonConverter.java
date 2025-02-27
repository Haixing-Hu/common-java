////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.IOException;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.json.JsonMapper;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A converter that converts a response body to a JSON object.
 *
 * @param <T>
 *     the type of the JSON object.
 */
public class ResponseBodyJsonConverter<T> implements Converter<ResponseBody, T> {

  private final Class<T> clazz;

  private final JsonMapper jsonMapper;

  public ResponseBodyJsonConverter(final Class<T> clazz) {
    this(clazz, new CustomizedJsonMapper());
  }

  public ResponseBodyJsonConverter(final Class<T> clazz, final JsonMapper jsonMapper) {
    this.clazz = requireNonNull("clazz", clazz);
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
  }

  @Nullable
  @Override
  public T convert(final ResponseBody responseBody) throws IOException {
    final String json = responseBody.string();
    return jsonMapper.readValue(json, clazz);
  }
}
