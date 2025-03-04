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
 * A interceptor which adds an authorization header to the HTTP request.
 *
 * @author Haixing Hu
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class AuthorizationInterceptor implements Interceptor {

  public static final String DEFAULT_HEADER_NAME = "Authorization";

  public static final String DEFAULT_KEY_PREFIX = "Bearer ";

  private final String key;
  private final String headerName;
  private final String keyPrefix;

  /**
   * Creates a new {@link AuthorizationInterceptor} instance.
   *
   * @param key
   *     the authorization key.
   */
  public AuthorizationInterceptor(final String key) {
    this(key, DEFAULT_HEADER_NAME, DEFAULT_KEY_PREFIX);
  }

  /**
   * Creates a new {@link AuthorizationInterceptor} instance.
   *
   * @param key
   *     the authorization key.
   * @param headerName
   *     the name of the authorization header.
   */
  public AuthorizationInterceptor(final String key, final String headerName) {
    this(key, headerName, DEFAULT_KEY_PREFIX);
  }

  /**
   * Creates a new {@link AuthorizationInterceptor} instance.
   *
   * @param key
   *     the authorization key.
   * @param headerName
   *     the name of the authorization header.
   * @param keyPrefix
   *     the prefix of the authorization key.
   */
  public AuthorizationInterceptor(final String key, final String headerName,
      final String keyPrefix) {
    this.key = requireNonNull("key", key);
    this.headerName = requireNonNull("headerName", headerName);
    this.keyPrefix = requireNonNull("keyPrefix", keyPrefix);
  }

  public String getKey() {
    return key;
  }

  public String getHeaderName() {
    return headerName;
  }

  public String getKeyPrefix() {
    return keyPrefix;
  }

  @Nonnull
  @Override
  public Response intercept(final Chain chain) throws IOException {
    final String authorizationHeader = "Bearer " + key;
    final Request request = chain
        .request()
        .newBuilder()
        .addHeader("Authorization", authorizationHeader)
        .build();
    return chain.proceed(request);
  }
}
