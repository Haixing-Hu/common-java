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
 * 将响应体转换为JSON对象的转换器。
 *
 * @param <T>
 *     JSON对象的类型。
 */
public class ResponseBodyJsonConverter<T> implements Converter<ResponseBody, T> {

  private final Class<T> clazz;

  private final JsonMapper jsonMapper;

  /**
   * 使用指定的类和默认的 {@link CustomizedJsonMapper} 构造一个
   * {@link ResponseBodyJsonConverter}。
   *
   * @param clazz
   *     目标对象的类。
   */
  public ResponseBodyJsonConverter(final Class<T> clazz) {
    this(clazz, new CustomizedJsonMapper());
  }

  /**
   * 使用指定的类和 {@link JsonMapper} 构造一个
   * {@link ResponseBodyJsonConverter}。
   *
   * @param clazz
   *     目标对象的类。
   * @param jsonMapper
   *     用于转换的 {@link JsonMapper}。
   */
  public ResponseBodyJsonConverter(final Class<T> clazz, final JsonMapper jsonMapper) {
    this.clazz = requireNonNull("clazz", clazz);
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
  }

  /**
   * {@inheritDoc}
   */
  @Nullable
  @Override
  public T convert(final ResponseBody responseBody) throws IOException {
    final String json = responseBody.string();
    return jsonMapper.readValue(json, clazz);
  }
}