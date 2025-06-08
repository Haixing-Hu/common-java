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
 * 向HTTP请求添加授权头部的拦截器。
 *
 * @author 胡海星
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class AuthorizationInterceptor extends AddHeaderInterceptor {

  /**
   * 默认的授权头部名称。
   */
  public static final String DEFAULT_HEADER_NAME = "Authorization";

  /**
   * 默认的密钥前缀。
   */
  public static final String DEFAULT_KEY_PREFIX = "Bearer ";

  /**
   * 创建新的 {@link AuthorizationInterceptor} 实例。
   *
   * @param key
   *     授权密钥。
   */
  public AuthorizationInterceptor(final String key) {
    this(key, DEFAULT_HEADER_NAME, DEFAULT_KEY_PREFIX);
  }

  /**
   * 创建新的 {@link AuthorizationInterceptor} 实例。
   *
   * @param key
   *     授权密钥。
   * @param headerName
   *     授权头部的名称。
   */
  public AuthorizationInterceptor(final String key, final String headerName) {
    this(key, headerName, DEFAULT_KEY_PREFIX);
  }

  /**
   * 创建新的 {@link AuthorizationInterceptor} 实例。
   *
   * @param key
   *     授权密钥。
   * @param headerName
   *     授权头部的名称。
   * @param keyPrefix
   *     授权密钥的前缀。
   */
  public AuthorizationInterceptor(final String key, final String headerName,
      final String keyPrefix) {
    super(headerName, keyPrefix + key);
  }
}