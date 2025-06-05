////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.net.interceptor.ConnectionLoggingEventListener;
import ltd.qubit.commons.net.interceptor.HttpLoggingInterceptor;
import ltd.qubit.commons.net.interceptor.SkipIpV6AddressDns;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link OkHttpClient} 客户端的构建器，使用起来比 {@link OkHttpClient.Builder} 类更简单。
 * <p>
 * <b>注意：</b> 此类 <b>不是</b> 线程安全的。但构建出的客户端是线程安全的。
 *
 * @author 胡海星
 */
public class HttpClientBuilder implements HttpClientOptions {

  private final List<Interceptor> interceptors = new ArrayList<>();

  private Logger logger;

  private HttpClientOptions options;

  /**
   * 构造HTTP客户端提供器。
   */
  public HttpClientBuilder() {
    this(LoggerFactory.getLogger(HttpClientBuilder.class), new DefaultHttpClientOptions());
  }

  /**
   * 构造HTTP客户端提供器。
   *
   * @param logger
   *     提供的HTTP客户端使用的日志记录器。
   */
  public HttpClientBuilder(final Logger logger) {
    this(logger, new DefaultHttpClientOptions());
  }

  /**
   * 构造HTTP客户端提供器。
   *
   * @param logger
   *     提供的HTTP客户端使用的日志记录器。
   * @param config
   *     提供的HTTP客户端使用的配置。
   */
  public HttpClientBuilder(final Logger logger, final WritableConfig config) {
    this(logger, new DefaultHttpClientOptions(config));
  }

  /**
   * 构造HTTP客户端提供器。
   *
   * @param logger
   *     提供的HTTP客户端使用的日志记录器。
   * @param options
   *     提供的HTTP客户端使用的HTTP客户端选项。
   */
  public HttpClientBuilder(final Logger logger, final HttpClientOptions options) {
    this.logger = requireNonNull("logger", logger);
    this.options = requireNonNull("options", options);
  }

  public Logger getLogger() {
    return logger;
  }

  public HttpClientBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  /**
   * 获取此构建器使用的HTTP客户端选项。
   *
   * @return
   *     此构建器使用的HTTP客户端选项。
   */
  public HttpClientOptions getOptions() {
    return options;
  }

  /**
   * 设置此构建器使用的HTTP客户端选项。
   *
   * @param options
   *     要使用的HTTP客户端选项，不能为 {@code null}。
   * @return
   *     此构建器，以支持方法链式调用。
   * @throws NullPointerException
   *     如果选项为 {@code null}。
   */
  public HttpClientBuilder setOptions(final HttpClientOptions options) {
    this.options = requireNonNull("options", options);
    return this;
  }

  @Override
  public boolean isUseProxy() {
    return options.isUseProxy();
  }

  @Override
  public HttpClientBuilder setUseProxy(final boolean useProxy) {
    options.setUseProxy(useProxy);
    return this;
  }

  @Override
  public String getProxyType() {
    return options.getProxyType();
  }

  @Override
  public HttpClientBuilder setProxyType(final String proxyType) {
    options.setProxyType(proxyType);
    return this;
  }

  @Override
  public HttpClientBuilder setProxyType(final Proxy.Type proxyType) {
    options.setProxyType(proxyType);
    return this;
  }

  @Override
  @Nullable
  public String getProxyHost() {
    return options.getProxyHost();
  }

  @Override
  public HttpClientBuilder setProxyHost(@Nullable final String proxyHost) {
    options.setProxyHost(proxyHost);
    return this;
  }

  @Override
  public int getProxyPort() {
    return options.getProxyPort();
  }

  @Override
  public HttpClientBuilder setProxyPort(final int proxyPort) {
    options.setProxyPort(proxyPort);
    return this;
  }

  @Override
  @Nullable
  public String getProxyUsername() {
    return options.getProxyUsername();
  }

  @Override
  public HttpClientBuilder setProxyUsername(@Nullable final String proxyUsername) {
    options.setProxyUsername(proxyUsername);
    return this;
  }

  @Override
  @Nullable
  public String getProxyPassword() {
    return options.getProxyPassword();
  }

  @Override
  public HttpClientBuilder setProxyPassword(@Nullable final String proxyPassword) {
    options.setProxyPassword(proxyPassword);
    return this;
  }

  @Override
  public int getConnectionTimeout() {
    return options.getConnectionTimeout();
  }

  @Override
  public HttpClientBuilder setConnectionTimeout(final int connectionTimeout) {
    options.setConnectionTimeout(connectionTimeout);
    return this;
  }

  @Override
  public int getReadTimeout() {
    return options.getReadTimeout();
  }

  @Override
  public HttpClientBuilder setReadTimeout(final int readTimeout) {
    options.setReadTimeout(readTimeout);
    return this;
  }

  @Override
  public int getWriteTimeout() {
    return options.getWriteTimeout();
  }

  @Override
  public HttpClientBuilder setWriteTimeout(final int writeTimeout) {
    options.setWriteTimeout(writeTimeout);
    return this;
  }

  @Override
  public boolean isUseHttpLogging() {
    return options.isUseHttpLogging();
  }

  @Override
  public HttpClientBuilder setUseHttpLogging(final boolean useHttpLogging) {
    options.setUseHttpLogging(useHttpLogging);
    return this;
  }

  @Override
  public boolean isLogHttpRequestHeader() {
    return options.isLogHttpRequestHeader();
  }

  @Override
  public HttpClientBuilder setLogHttpRequestHeader(final boolean logHttpRequestHeader) {
    options.setLogHttpRequestHeader(logHttpRequestHeader);
    return this;
  }

  @Override
  public boolean isLogHttpRequestBody() {
    return options.isLogHttpRequestBody();
  }

  @Override
  public HttpClientBuilder setLogHttpRequestBody(final boolean logHttpRequestBody) {
    options.setLogHttpRequestBody(logHttpRequestBody);
    return this;
  }

  @Override
  public boolean isLogHttpResponseHeader() {
    return options.isLogHttpResponseHeader();
  }

  @Override
  public HttpClientBuilder setLogHttpResponseHeader(final boolean logHttpResponseHeader) {
    options.setLogHttpResponseHeader(logHttpResponseHeader);
    return this;
  }

  @Override
  public boolean isLogHttpResponseBody() {
    return options.isLogHttpResponseBody();
  }

  @Override
  public HttpClientBuilder setLogHttpResponseBody(final boolean logHttpResponseBody) {
    options.setLogHttpResponseBody(logHttpResponseBody);
    return this;
  }

  @Override
  public List<String> getSensitiveHttpHeaders() {
    return options.getSensitiveHttpHeaders();
  }

  @Override
  public HttpClientBuilder setSensitiveHttpHeaders(final List<String> headers) {
    options.setSensitiveHttpHeaders(headers);
    return this;
  }

  @Override
  public HttpClientBuilder addSensitiveHttpHeader(final String headerName) {
    options.addSensitiveHttpHeader(headerName);
    return this;
  }

  @Override
  public HttpClientBuilder addSensitiveHttpHeaders(final String... headerNames) {
    options.addSensitiveHttpHeaders(headerNames);
    return this;
  }

  @Override
  public HttpClientBuilder addSensitiveHttpHeaders(final Collection<String> headerNames) {
    options.addSensitiveHttpHeaders(headerNames);
    return this;
  }

  @Override
  public HttpClientBuilder clearSensitiveHttpHeaders() {
    options.clearSensitiveHttpHeaders();
    return this;
  }

  @Override
  public boolean isUseOnlyIpV4Address() {
    return options.isUseOnlyIpV4Address();
  }

  @Override
  public HttpClientBuilder setUseOnlyIpV4Address(final boolean useOnlyIpV4Address) {
    options.setUseOnlyIpV4Address(useOnlyIpV4Address);
    return this;
  }

  /**
   * 向HTTP客户端添加拦截器。
   * <p>
   * 拦截器可用于监控、修改或重试HTTP请求和响应。
   *
   * @param interceptor
   *     要添加的拦截器，不能为 {@code null}。
   * @return
   *     此构建器，以支持方法链式调用。
   * @throws NullPointerException
   *     如果拦截器为 {@code null}。
   */
  public HttpClientBuilder addInterceptor(final Interceptor interceptor) {
    interceptors.add(requireNonNull("interceptor", interceptor));
    return this;
  }

  /**
   * 向HTTP客户端添加多个拦截器。
   * <p>
   * 拦截器可用于监控、修改或重试HTTP请求和响应。
   *
   * @param interceptors
   *     要添加的拦截器数组，元素不能为 {@code null}。
   * @return
   *     此构建器，以支持方法链式调用。
   * @throws NullPointerException
   *     如果任何拦截器为 {@code null}。
   */
  public HttpClientBuilder addInterceptors(final Interceptor ... interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  /**
   * 向HTTP客户端添加拦截器集合。
   * <p>
   * 拦截器可用于监控、修改或重试HTTP请求和响应。
   *
   * @param interceptors
   *     要添加的拦截器集合，元素不能为 {@code null}。
   * @return
   *     此构建器，以支持方法链式调用。
   * @throws NullPointerException
   *     如果任何拦截器为 {@code null}。
   */
  public HttpClientBuilder addInterceptors(final Collection<Interceptor> interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  /**
   * 清除之前添加到此构建器的所有拦截器。
   *
   * @return
   *     此构建器，以支持方法链式调用。
   */
  public HttpClientBuilder clearInterceptors() {
    interceptors.clear();
    return this;
  }

  protected Proxy getProxy() {
    final String typeName = getProxyType();
    final Proxy.Type proxyType = Proxy.Type.valueOf(typeName.toUpperCase());
    final String proxyHost = getProxyHost();
    if (proxyHost == null) {
      throw new IllegalArgumentException("The proxy host is not specified.");
    }
    final int proxyPort = getProxyPort();
    if (proxyPort <= 0) {
      throw new IllegalArgumentException("The proxy port is not specified.");
    }
    return new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));
  }

  /**
   * 构建预配置的HTTP客户端。
   *
   * @return
   *    预配置的HTTP客户端。
   */
  public OkHttpClient build() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(getConnectionTimeout(), TimeUnit.SECONDS)
           .readTimeout(getReadTimeout(), TimeUnit.SECONDS)
           .writeTimeout(getWriteTimeout(), TimeUnit.SECONDS);

    // Configure DNS to use only IPv4 addresses if needed
    if (isUseOnlyIpV4Address()) {
      logger.info("Configuring HTTP client to use only IPv4 addresses for DNS resolution.");
      builder.dns(SkipIpV6AddressDns.INSTANCE);
    }

    // Configure proxy if needed
    if (isUseProxy()) {
      logger.info("Configuring a proxy for the HTTP client.");
      final Proxy proxy = getProxy();
      builder.proxy(proxy);

      // Add proxy authentication if needed
      final String username = getProxyUsername();
      if (username != null) {
        final String password = getProxyPassword();
        final String passwordToUse = (password != null) ? password : "";
        logger.info("Using proxy authentication with username: {}", username);
        builder.proxyAuthenticator((route, response) -> {
          final String credential = okhttp3.Credentials.basic(username, passwordToUse);
          return response.request().newBuilder()
                         .header("Proxy-Authorization", credential)
                         .build();
        });
      }
    }

    // Add interceptors
    for (final Interceptor interceptor : interceptors) {
      builder.addInterceptor(interceptor);
    }

    // Add logging if enabled
    if (isUseHttpLogging()) {
      logger.info("Using logging for the HTTP client.");
      final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger, options);

      // Add sensitive HTTP headers to the logging interceptor
      final List<String> headers = getSensitiveHttpHeaders();
      if (headers != null && !headers.isEmpty()) {
        for (final String header : headers) {
          loggingInterceptor.addSensitiveHttpHeader(header);
        }
      }

      builder.addInterceptor(loggingInterceptor);
      builder.eventListener(new ConnectionLoggingEventListener(logger));
    }

    return builder.build();
  }
}