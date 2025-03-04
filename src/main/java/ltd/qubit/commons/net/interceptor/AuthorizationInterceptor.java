////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

/**
 * A interceptor which adds an authorization header to the HTTP request.
 *
 * @author Haixing Hu
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class AuthorizationInterceptor extends AddHeaderInterceptor {

  public static final String DEFAULT_HEADER_NAME = "Authorization";

  public static final String DEFAULT_KEY_PREFIX = "Bearer ";

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
    super(headerName, keyPrefix + key);
  }
}
