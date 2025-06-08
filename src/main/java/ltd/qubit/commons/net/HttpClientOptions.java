////////////////////////////////////////////////////////////////////////////////
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
  String KEY_IPV4_ONLY = "http.dns.ipv4-only";

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
  boolean DEFAULT_IPV4_ONLY = false;

  /**
   * 获取是否使用代理服务器。
   *
   * @return
   *     如果应使用代理服务器返回{@code true}；否则返回{@code false}。
   */
  boolean isUseProxy();

  /**
   * 设置是否使用代理服务器。
   *
   * @param useProxy
   *     如果应使用代理服务器设置为{@code true}；否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setUseProxy(boolean useProxy);

  /**
   * 获取代理服务器类型。
   *
   * @return
   *     代理服务器类型。
   */
  String getProxyType();

  /**
   * 设置代理服务器类型。
   *
   * @param proxyType
   *     代理服务器类型。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setProxyType(String proxyType);

  /**
   * 设置代理服务器类型。
   *
   * @param proxyType
   *     代理服务器类型。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setProxyType(Proxy.Type proxyType);

  /**
   * 获取代理服务器主机。
   *
   * @return
   *     代理服务器主机，如果未设置则返回{@code null}。
   */
  @Nullable
  String getProxyHost();

  /**
   * 设置代理服务器主机。
   *
   * @param proxyHost
   *     代理服务器主机，设置为{@code null}以清除设置。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setProxyHost(@Nullable String proxyHost);

  /**
   * 获取代理服务器端口。
   *
   * @return
   *     代理服务器端口。
   */
  int getProxyPort();

  /**
   * 设置代理服务器端口。
   *
   * @param proxyPort
   *     代理服务器端口。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setProxyPort(int proxyPort);

  /**
   * 获取代理服务器用户名。
   *
   * @return
   *     代理服务器用户名，如果未设置则返回{@code null}。
   */
  @Nullable
  String getProxyUsername();

  /**
   * 设置代理服务器用户名。
   *
   * @param proxyUsername
   *     代理服务器用户名，设置为{@code null}以清除设置。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setProxyUsername(@Nullable String proxyUsername);

  /**
   * 获取代理服务器密码。
   *
   * @return
   *     代理服务器密码，如果未设置则返回{@code null}。
   */
  @Nullable
  String getProxyPassword();

  /**
   * 设置代理服务器密码。
   *
   * @param proxyPassword
   *     代理服务器密码，设置为{@code null}以清除设置。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setProxyPassword(@Nullable String proxyPassword);

  /**
   * 获取连接超时时间（以秒为单位）。
   *
   * @return
   *     连接超时时间（以秒为单位）。
   */
  int getConnectionTimeout();

  /**
   * 设置连接超时时间（以秒为单位）。
   *
   * @param connectionTimeout
   *     连接超时时间（以秒为单位）。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setConnectionTimeout(int connectionTimeout);

  /**
   * 获取读取超时时间（以秒为单位）。
   *
   * @return
   *     读取超时时间（以秒为单位）。
   */
  int getReadTimeout();

  /**
   * 设置读取超时时间（以秒为单位）。
   *
   * @param readTimeout
   *     读取超时时间（以秒为单位）。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setReadTimeout(int readTimeout);

  /**
   * 获取写入超时时间（以秒为单位）。
   *
   * @return
   *     写入超时时间（以秒为单位）。
   */
  int getWriteTimeout();

  /**
   * 设置写入超时时间（以秒为单位）。
   *
   * @param writeTimeout
   *     写入超时时间（以秒为单位）。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setWriteTimeout(int writeTimeout);

  /**
   * 获取是否使用HTTP日志拦截器。
   *
   * @return
   *     如果应使用HTTP日志拦截器返回{@code true}；否则返回{@code false}。
   */
  boolean isUseHttpLogging();

  /**
   * 设置是否使用HTTP日志拦截器。
   *
   * @param useHttpLogging
   *     如果应使用HTTP日志拦截器设置为{@code true}；否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setUseHttpLogging(boolean useHttpLogging);

  /**
   * 获取是否记录HTTP请求头。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @return
   *     如果应记录HTTP请求头返回{@code true}；
   *     否则返回{@code false}。
   */
  boolean isLogHttpRequestHeader();

  /**
   * 设置是否记录HTTP请求头。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @param logHttpRequestHeader
   *     如果应记录HTTP请求头设置为{@code true}；
   *     否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setLogHttpRequestHeader(boolean logHttpRequestHeader);

  /**
   * 获取是否记录HTTP请求体。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @return
   *     如果应记录HTTP请求体返回{@code true}；
   *     否则返回{@code false}。
   */
  boolean isLogHttpRequestBody();

  /**
   * 设置是否记录HTTP请求体。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @param logHttpRequestBody
   *     如果应记录HTTP请求体设置为{@code true}；
   *     否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setLogHttpRequestBody(boolean logHttpRequestBody);

  /**
   * 获取是否记录HTTP响应头。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @return
   *     如果应记录HTTP响应头返回{@code true}；
   *     否则返回{@code false}。
   */
  boolean isLogHttpResponseHeader();

  /**
   * 设置是否记录HTTP响应头。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @param logHttpResponseHeader
   *     如果应记录HTTP响应头设置为{@code true}；
   *     否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setLogHttpResponseHeader(boolean logHttpResponseHeader);

  /**
   * 获取是否记录HTTP响应体。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @return
   *     如果应记录HTTP响应体返回{@code true}；
   *     否则返回{@code false}。
   */
  boolean isLogHttpResponseBody();

  /**
   * 设置是否记录HTTP响应体。
   * <p>
   * 此选项仅在{@link #isUseHttpLogging()}为{@code true}时有效。
   *
   * @param logHttpResponseBody
   *     如果应记录HTTP响应体设置为{@code true}；
   *     否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setLogHttpResponseBody(boolean logHttpResponseBody);

  /**
   * 获取在日志中应屏蔽值的敏感HTTP头列表。
   *
   * @return
   *     敏感HTTP头名称列表。
   */
  List<String> getSensitiveHttpHeaders();

  /**
   * 设置在日志中应屏蔽值的敏感HTTP头列表。
   *
   * @param headers
   *     敏感HTTP头名称列表。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setSensitiveHttpHeaders(List<String> headers);

  /**
   * 添加一个在日志中应屏蔽值的敏感HTTP头。
   *
   * @param headerName
   *     敏感HTTP头的名称。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions addSensitiveHttpHeader(String headerName);

  /**
   * 添加多个在日志中应屏蔽值的敏感HTTP头。
   *
   * @param headerNames
   *     敏感HTTP头的名称。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions addSensitiveHttpHeaders(String... headerNames);

  /**
   * 添加多个在日志中应屏蔽值的敏感HTTP头。
   *
   * @param headerNames
   *     敏感HTTP头名称的集合。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions addSensitiveHttpHeaders(Collection<String> headerNames);

  /**
   * 清除敏感HTTP头列表。
   *
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions clearSensitiveHttpHeaders();

  /**
   * 获取在DNS解析中是否仅使用IPv4地址。
   *
   * @return
   *     如果应仅使用IPv4地址返回{@code true}；否则返回{@code false}。
   */
  boolean isIpV4Only();

  /**
   * 设置在DNS解析中是否仅使用IPv4地址。
   *
   * @param ipV4Only
   *     如果应仅使用IPv4地址设置为{@code true}；否则设置为{@code false}。
   * @return
   *     此对象，用于支持方法链式调用。
   */
  HttpClientOptions setIpV4Only(boolean ipV4Only);

}