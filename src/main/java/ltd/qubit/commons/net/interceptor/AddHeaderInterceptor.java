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

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A interceptor which adds a header to the HTTP request.
 *
 * @author Haixing Hu
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class AddHeaderInterceptor implements Interceptor {

  private final String name;
  private final String value;

  /**
   * Creates a new {@link AddHeaderInterceptor} instance.
   *
   * @param name
   *     the name of the header to be added.
   * @param value
   *     the value of the header to be added.
   */
  public AddHeaderInterceptor(final String name, final String value) {
    this.name = requireNonNull("name", name);
    this.value = requireNonNull("value", value);
  }

  @Nonnull
  @Override
  public Response intercept(final Chain chain) throws IOException {
    final Request request = chain
        .request()
        .newBuilder()
        .addHeader(name, value)
        .build();
    return chain.proceed(request);
  }
}
