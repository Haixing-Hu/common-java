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
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

/**
 * HTTP客户端配置的接口。
 *
 * @author 胡海星
 */
public interface HttpClientOptions {

  /**
   * 连接超时的配置键，单位为秒。
   */
  String KEY_CONNECTION_TIMEOUT = "http.timeout.connection";

  /**
   * 读取超时的配置键，单位为秒。
   */
  String KEY_READ_TIMEOUT = "http.timeout.read";

  /**
   * 写入超时的配置键，单位为秒。
   */
  String KEY_WRITE_TIMEOUT = "http.timeout.write";

  /**
   * 连接到模型提供者时是否使用代理服务器的配置键。
   */
  String KEY_USE_PROXY = "http.proxy.use";

  /**
   * 代理服务器类型的配置键。
   */
  String KEY_PROXY_TYPE = "http.proxy.type";

  /**
   * 代理服务器主机的配置键。
   */
  String KEY_PROXY_HOST = "http.proxy.host";

  /**
   * 代理服务器端口的配置键。
   */
  String KEY_PROXY_PORT = "http.proxy.port";

  /**
   * 代理服务器用户名的配置键。
   */
  String KEY_PROXY_USERNAME = "http.proxy.username";

  /**
   * 代理服务器密码的配置键。
   */
  String KEY_PROXY_PASSWORD = "http.proxy.password";

  /**
   * 是否使用HTTP日志拦截器的配置键。
   */
  String KEY_USE_HTTP_LOGGING = "http.logging.use";

  /**
   * 是否记录HTTP请求头的配置键。
   */
  String KEY_LOG_HTTP_REQUEST_HEADER = "http.logging.request.header";

  /**
   * 是否记录HTTP请求体的配置键。
   */
  String KEY_LOG_HTTP_REQUEST_BODY = "http.logging.request.body";

  /**
   * 是否记录HTTP响应头的配置键。
   */
  String KEY_LOG_HTTP_RESPONSE_HEADER = "http.logging.response.header";

  /**
   * 是否记录HTTP响应体的配置键。
   */
  String KEY_LOG_HTTP_RESPONSE_BODY = "http.logging.response.body";

  /**
   * 敏感HTTP头的配置键。
   */
  String KEY_SENSITIVE_HTTP_HEADERS = "http.logging.sensitive-headers";

  /**
   * DNS解析中是否仅使用IPv4地址的配置键。
   */
  String KEY_USE_ONLY_IPV4_ADDRESS = "http.dns.use-only-ipv4";

  /**
   * 连接超时的默认值，单位为秒。
   */
  int DEFAULT_CONNECTION_TIMEOUT = 10;

  /**
   * 读取超时的默认值，单位为秒。
   */
  int DEFAULT_READ_TIMEOUT = 120;

  /**
   * 写入超时的默认值，单位为秒。
   */
  int DEFAULT_WRITE_TIMEOUT = 120;

  /**
   * 是否使用代理服务器的默认值。
   */
  boolean DEFAULT_USE_PROXY = false;

  /**
   * 代理服务器类型的默认值。
   */
  String DEFAULT_PROXY_TYPE = "http";

  /**
   * 是否使用HTTP日志拦截器的默认值。
   */
  boolean DEFAULT_USE_HTTP_LOGGING = true;

  /**
   * 是否记录HTTP请求头的默认值。
   */
  boolean DEFAULT_LOG_HTTP_REQUEST_HEADER = true;

  /**
   * 是否记录HTTP请求体的默认值。
   */
  boolean DEFAULT_LOG_HTTP_REQUEST_BODY = true;

  /**
   * 是否记录HTTP响应头的默认值。
   */
  boolean DEFAULT_LOG_HTTP_RESPONSE_HEADER = true;

  /**
   * 是否记录HTTP响应体的默认值。
   */
  boolean DEFAULT_LOG_HTTP_RESPONSE_BODY = true;

  /**
   * DNS解析中是否仅使用IPv4地址的默认值。
   */
  boolean DEFAULT_USE_ONLY_IPV4_ADDRESS = false;

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

  /**
   * Gets whether to log the HTTP request headers.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @return
   *     {@code true} if the HTTP request headers should be logged;
   *     {@code false} otherwise.
   */
  boolean isLogHttpRequestHeader();

  /**
   * Sets whether to log the HTTP request headers.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @param logHttpRequestHeader
   *     {@code true} if the HTTP request headers should be logged;
   *     {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setLogHttpRequestHeader(boolean logHttpRequestHeader);

  /**
   * Gets whether to log the HTTP request body.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @return
   *     {@code true} if the HTTP request body should be logged;
   *     {@code false} otherwise.
   */
  boolean isLogHttpRequestBody();

  /**
   * Sets whether to log the HTTP request body.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @param logHttpRequestBody
   *     {@code true} if the HTTP response body should be logged;
   *     {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setLogHttpRequestBody(boolean logHttpRequestBody);

  /**
   * Gets whether to log the HTTP response headers.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @return
   *     {@code true} if the HTTP response headers should be logged;
   *     {@code false} otherwise.
   */
  boolean isLogHttpResponseHeader();

  /**
   * Sets whether to log the HTTP response headers.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @param logHttpResponseHeader
   *     {@code true} if the HTTP response headers should be logged;
   *     {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setLogHttpResponseHeader(boolean logHttpResponseHeader);

  /**
   * Gets whether to log the HTTP response body.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @return
   *     {@code true} if the HTTP response body should be logged;
   *     {@code false} otherwise.
   */
  boolean isLogHttpResponseBody();

  /**
   * Sets whether to log the HTTP response body.
   * <p>
   * This option is only effective when {@link #isUseHttpLogging()} is {@code true}.
   *
   * @param logHttpResponseBody
   *     {@code true} if the HTTP response body should be logged;
   *     {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setLogHttpResponseBody(boolean logHttpResponseBody);

  /**
   * Gets the list of sensitive HTTP headers whose values should be masked in logs.
   *
   * @return
   *     a list of sensitive HTTP header names.
   */
  List<String> getSensitiveHttpHeaders();

  /**
   * Sets the list of sensitive HTTP headers whose values should be masked in logs.
   *
   * @param headers
   *     the list of sensitive HTTP header names.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setSensitiveHttpHeaders(List<String> headers);

  /**
   * Adds a sensitive HTTP header whose value should be masked in logs.
   *
   * @param headerName
   *     the name of the sensitive HTTP header.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions addSensitiveHttpHeader(String headerName);

  /**
   * Adds multiple sensitive HTTP headers whose values should be masked in logs.
   *
   * @param headerNames
   *     the names of the sensitive HTTP headers.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions addSensitiveHttpHeaders(String... headerNames);

  /**
   * Adds multiple sensitive HTTP headers whose values should be masked in logs.
   *
   * @param headerNames
   *     a collection of names of the sensitive HTTP headers.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions addSensitiveHttpHeaders(Collection<String> headerNames);

  /**
   * Clears the list of sensitive HTTP headers.
   *
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions clearSensitiveHttpHeaders();

  /**
   * Gets whether to use only IPv4 addresses in DNS resolution.
   *
   * @return
   *     {@code true} if only IPv4 addresses should be used; {@code false} otherwise.
   */
  boolean isUseOnlyIpV4Address();

  /**
   * Sets whether to use only IPv4 addresses in DNS resolution.
   *
   * @param useOnlyIpV4Address
   *     {@code true} if only IPv4 addresses should be used; {@code false} otherwise.
   * @return
   *     this object, to support method chaining.
   */
  HttpClientOptions setUseOnlyIpV4Address(boolean useOnlyIpV4Address);

}