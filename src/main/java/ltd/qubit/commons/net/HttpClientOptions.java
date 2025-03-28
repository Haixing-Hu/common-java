/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.Proxy;

import javax.annotation.Nullable;

/**
 * The interface of configurations for HTTP clients.
 *
 * @author Haixing Hu
 */
public interface HttpClientOptions {

  /**
   * The configuration key of the connection timeout, in seconds.
   */
  String KEY_CONNECTION_TIMEOUT = "http.timeout.connection";

  /**
   * The configuration key of the read timeout, in seconds.
   */
  String KEY_READ_TIMEOUT = "http.timeout.read";

  /**
   * The configuration key of the write timeout, in seconds.
   */
  String KEY_WRITE_TIMEOUT = "http.timeout.write";

  /**
   * The configuration key of whether to use the proxy server while connecting
   * to the model provider.
   */
  String KEY_USE_PROXY = "http.proxy.use";

  /**
   * The configuration key of the proxy server type.
   */
  String KEY_PROXY_TYPE = "http.proxy.type";

  /**
   * The configuration key of the proxy server host.
   */
  String KEY_PROXY_HOST = "http.proxy.host";

  /**
   * The configuration key of the proxy server port.
   */
  String KEY_PROXY_PORT = "http.proxy.port";

  /**
   * The configuration key of the proxy server username.
   */
  String KEY_PROXY_USERNAME = "http.proxy.username";

  /**
   * The configuration key of the proxy server password.
   */
  String KEY_PROXY_PASSWORD = "http.proxy.password";

  /**
   * The configuration key of whether to use the HTTP logging interceptor.
   */
  String KEY_USE_HTTP_LOGGING = "http.logging.use";

  /**
   * The default timeout for the connection in seconds.
   */
  int DEFAULT_CONNECTION_TIMEOUT = 10;

  /**
   * The default timeout for the read in seconds.
   */
  int DEFAULT_READ_TIMEOUT = 120;

  /**
   * The default timeout for the write out seconds.
   */
  int DEFAULT_WRITE_TIMEOUT = 120;

  /**
   * The default value of whether to use the proxy server.
   */
  boolean DEFAULT_USE_PROXY = false;

  /**
   * The default value of the proxy server type.
   */
  String DEFAULT_PROXY_TYPE = "http";

  /**
   * The default value of whether to use the HTTP logging interceptor.
   */
  boolean DEFAULT_USE_HTTP_LOGGING = true;

  /**
   * Gets whether to use the proxy server.
   *
   * @return
   *     {@code true} if the proxy server should be used; {@code false} otherwise.
   */
  boolean isUseProxy();

  /**
   * Sets whether to use the proxy server.
   *
   * @param useProxy
   *     {@code true} if the proxy server should be used; {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setUseProxy(boolean useProxy);

  /**
   * Gets the proxy server type.
   *
   * @return
   *     the proxy server type.
   */
  String getProxyType();

  /**
   * Sets the proxy server type.
   *
   * @param proxyType
   *     the proxy server type.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setProxyType(String proxyType);

  /**
   * Sets the proxy server type.
   *
   * @param proxyType
   *     the proxy server type.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setProxyType(Proxy.Type proxyType);

  /**
   * Gets the proxy server host.
   *
   * @return
   *     the proxy server host, or {@code null} if not set.
   */
  @Nullable
  String getProxyHost();

  /**
   * Sets the proxy server host.
   *
   * @param proxyHost
   *     the proxy server host, or {@code null} to clear the setting.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setProxyHost(@Nullable String proxyHost);

  /**
   * Gets the proxy server port.
   *
   * @return
   *     the proxy server port.
   */
  int getProxyPort();

  /**
   * Sets the proxy server port.
   *
   * @param proxyPort
   *     the proxy server port.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setProxyPort(int proxyPort);

  /**
   * Gets the proxy server username.
   *
   * @return
   *     the proxy server username, or {@code null} if not set.
   */
  @Nullable
  String getProxyUsername();

  /**
   * Sets the proxy server username.
   *
   * @param proxyUsername
   *     the proxy server username, or {@code null} to clear the setting.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setProxyUsername(@Nullable String proxyUsername);

  /**
   * Gets the proxy server password.
   *
   * @return
   *     the proxy server password, or {@code null} if not set.
   */
  @Nullable
  String getProxyPassword();

  /**
   * Sets the proxy server password.
   *
   * @param proxyPassword
   *     the proxy server password, or {@code null} to clear the setting.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setProxyPassword(@Nullable String proxyPassword);

  /**
   * Gets the connection timeout in seconds.
   *
   * @return
   *     the connection timeout in seconds.
   */
  int getConnectionTimeout();

  /**
   * Sets the connection timeout in seconds.
   *
   * @param connectionTimeout
   *     the connection timeout in seconds.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setConnectionTimeout(int connectionTimeout);

  /**
   * Gets the read timeout in seconds.
   *
   * @return
   *     the read timeout in seconds.
   */
  int getReadTimeout();

  /**
   * Sets the read timeout in seconds.
   *
   * @param readTimeout
   *     the read timeout in seconds.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setReadTimeout(int readTimeout);

  /**
   * Gets the write timeout in seconds.
   *
   * @return
   *     the write timeout in seconds.
   */
  int getWriteTimeout();

  /**
   * Sets the write timeout in seconds.
   *
   * @param writeTimeout
   *     the write timeout in seconds.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setWriteTimeout(int writeTimeout);

  /**
   * Gets whether to use the HTTP logging interceptor.
   *
   * @return
   *     {@code true} if the HTTP logging interceptor should be used; {@code false} otherwise.
   */
  boolean isUseHttpLogging();

  /**
   * Sets whether to use the HTTP logging interceptor.
   *
   * @param useHttpLogging
   *     {@code true} if the HTTP logging interceptor should be used; {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setUseHttpLogging(boolean useHttpLogging);

}