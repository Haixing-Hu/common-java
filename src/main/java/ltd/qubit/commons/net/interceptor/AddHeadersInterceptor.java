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
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A interceptor which adds a headers to the HTTP request.
 *
 * @author Haixing Hu
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class AddHeadersInterceptor implements Interceptor {

  private final Map<String, String> headers;

  /**
   * Creates a new {@link AddHeadersInterceptor} instance.
   *
   * @param headers
   *     the map of the headers to be added.
   */
  public AddHeadersInterceptor(final Map<String, String> headers) {
    this.headers = requireNonNull("headers", headers);
  }

  @Nonnull
  @Override
  public Response intercept(final Chain chain) throws IOException {
    final Request.Builder builder = chain.request().newBuilder();
    for (final Map.Entry<String, String> entry : headers.entrySet()) {
      builder.addHeader(entry.getKey(), entry.getValue());
    }
    final Request request = builder.build();
    return chain.proceed(request);
  }
}
