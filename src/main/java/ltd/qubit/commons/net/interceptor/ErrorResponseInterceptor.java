////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

import java.io.IOException;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import ltd.qubit.commons.net.ResponseBodyJsonConverter;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 拦截HTTP请求错误响应并处理错误响应的拦截器。
 *
 * @param <E>
 *      错误响应的类型。
 * @author 胡海星
 */
public class ErrorResponseInterceptor<E> implements Interceptor {

  private final Converter<ResponseBody, E> responseBodyConverter;

  private final Consumer<E> errorConsumer;

  /**
   * 使用指定的错误响应类和错误消费者构造新的 {@link ErrorResponseInterceptor}。
   *
   * @param errorClass
   *     错误响应的类。
   * @param errorConsumer
   *     处理错误响应的消费者。
   */
  public ErrorResponseInterceptor(final Class<E> errorClass, final Consumer<E> errorConsumer) {
    this(new ResponseBodyJsonConverter<>(errorClass), errorConsumer);
  }

  /**
   * 使用指定的错误响应转换器和错误消费者构造新的 {@link ErrorResponseInterceptor}。
   *
   * @param responseBodyConverter
   *     将错误响应体转换为错误响应对象的转换器。
   * @param errorConsumer
   *     处理错误响应的消费者。
   */
  public ErrorResponseInterceptor(final Converter<ResponseBody, E> responseBodyConverter,
      final Consumer<E> errorConsumer) {
    this.responseBodyConverter = requireNonNull("responseBodyConverter", responseBodyConverter);
    this.errorConsumer = requireNonNull("errorConsumer", errorConsumer);
  }

  /**
   * 拦截HTTP请求并处理错误响应。
   *
   * @param chain
   *     拦截器链。
   * @return
   *     拦截后的响应。
   * @throws IOException
   *     如果发生IO错误。
   */
  @Nonnull
  @Override
  public Response intercept(@Nonnull final Chain chain) throws IOException {
    final Request request = chain.request();
    final Response response = chain.proceed(request);
    final ResponseBody responseBody = response.body();
    if (responseBody == null) {
      return response;
    }
    // 复制响应体，以便可以多次读取
    final byte[] bytes = responseBody.source().readByteArray();
    // 使用复制的 Buffer 来获取内容
    final ResponseBody bufferedBody = ResponseBody.create(responseBody.contentType(), bytes);
    try {
      // try to convert the response body to an error response object
      final E error = responseBodyConverter.convert(bufferedBody);
      if (error != null) {
        // if the error response object is not null, process it
        errorConsumer.accept(error);
      }
    } catch (final IOException e) {
      // ignore the conversion error
    }
    final ResponseBody newBody = ResponseBody.create(responseBody.contentType(), bytes);
    return response.newBuilder().body(newBody).build();
  }
}