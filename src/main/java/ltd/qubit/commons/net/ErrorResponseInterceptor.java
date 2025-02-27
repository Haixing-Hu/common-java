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
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An interceptor that intercepts the error response of a HTTP request and
 * processes the error response.
 *
 * @param <E>
 *      the type of the error response.
 */
public class ErrorResponseInterceptor<E> implements Interceptor {

  private final Converter<ResponseBody, E> responseBodyConverter;

  private final Consumer<E> errorConsumer;

  /**
   * Constructs a new {@link ErrorResponseInterceptor} with the specified error
   * response class and error consumer.
   *
   * @param errorClass
   *     the class of the error response.
   * @param errorConsumer
   *     the consumer to process the error response.
   */
  public ErrorResponseInterceptor(final Class<E> errorClass, final Consumer<E> errorConsumer) {
    this(new ResponseBodyJsonConverter<>(errorClass), errorConsumer);
  }

  /**
   * Constructs a new {@link ErrorResponseInterceptor} with the specified error
   * response converter and error consumer.
   *
   * @param responseBodyConverter
   *     the converter to convert the error response body to the error response
   *     object.
   * @param errorConsumer
   *     the consumer to process the error response.
   */
  public ErrorResponseInterceptor(final Converter<ResponseBody, E> responseBodyConverter,
      final Consumer<E> errorConsumer) {
    this.responseBodyConverter = requireNonNull("responseBodyConverter", responseBodyConverter);
    this.errorConsumer = requireNonNull("errorConsumer", errorConsumer);
  }

  @Nonnull
  @Override
  public Response intercept(@Nonnull final Chain chain) throws IOException {
    final Request request = chain.request();
    final Response response = chain.proceed(request);
    final ResponseBody responseBody = response.body();
    if (responseBody != null) {
      try {
        // try to convert the response body to an error response object
        final E error = responseBodyConverter.convert(responseBody);
        if (error != null) {
          // if the error response object is not null, process it
          errorConsumer.accept(error);
        }
      } catch (final IOException e) {
        // ignore the conversion error
      }
    }
    return response;
  }
}
