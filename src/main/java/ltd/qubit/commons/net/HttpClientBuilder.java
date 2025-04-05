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

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A builder of {@link OkHttpClient} clients which is much easier to use
 * than the {@link OkHttpClient.Builder} class.
 * <p>
 * <b>NOTE:</b> This class is <b>NOT</b> thread-safe. But the built client is
 * thread-safe.
 *
 * @author Haixing Hu
 */
public class HttpClientBuilder implements HttpClientOptions {

  private final List<Interceptor> interceptors = new ArrayList<>();

  private Logger logger;

  private HttpClientOptions options;

  /**
   * Construct a provider of the HTTP client.
   */
  public HttpClientBuilder() {
    this(LoggerFactory.getLogger(HttpClientBuilder.class), new DefaultHttpClientOptions());
  }

  /**
   * Construct a provider of the HTTP client.
   *
   * @param logger
   *     the logger used by the provided http client.
   */
  public HttpClientBuilder(final Logger logger) {
    this(logger, new DefaultHttpClientOptions());
  }

  /**
   * Construct a provider of the HTTP client.
   *
   * @param logger
   *     the logger used by the provided http client.
   * @param config
   *     the configuration used by the provided http client.
   */
  public HttpClientBuilder(final Logger logger, final WritableConfig config) {
    this(logger, new DefaultHttpClientOptions(config));
  }

  /**
   * Construct a provider of the HTTP client.
   *
   * @param logger
   *     the logger used by the provided http client.
   * @param options
   *     the HTTP client options used by the provided http client.
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
   * Gets the HTTP client options used by this builder.
   *
   * @return
   *     the HTTP client options used by this builder.
   */
  public HttpClientOptions getOptions() {
    return options;
  }

  /**
   * Sets the HTTP client options used by this builder.
   *
   * @param options
   *     the HTTP client options to be used, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the options is {@code null}.
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

  /**
   * Adds an interceptor to the HTTP client.
   * <p>
   * Interceptors can be used to monitor, modify, or retry HTTP requests and responses.
   *
   * @param interceptor
   *     the interceptor to add, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the interceptor is {@code null}.
   */
  public HttpClientBuilder addInterceptor(final Interceptor interceptor) {
    interceptors.add(requireNonNull("interceptor", interceptor));
    return this;
  }

  /**
   * Adds multiple interceptors to the HTTP client.
   * <p>
   * Interceptors can be used to monitor, modify, or retry HTTP requests and responses.
   *
   * @param interceptors
   *     the array of interceptors to add, elements must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if any of the interceptors is {@code null}.
   */
  public HttpClientBuilder addInterceptors(final Interceptor ... interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  /**
   * Adds a collection of interceptors to the HTTP client.
   * <p>
   * Interceptors can be used to monitor, modify, or retry HTTP requests and responses.
   *
   * @param interceptors
   *     the collection of interceptors to add, elements must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if any of the interceptors is {@code null}.
   */
  public HttpClientBuilder addInterceptors(final Collection<Interceptor> interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  /**
   * Clears all interceptors previously added to this builder.
   *
   * @return
   *     this builder, to support method chaining.
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
   * Build a pre-configured HTTP client.
   *
   * @return
   *    A pre-configured HTTP client.
   */
  public OkHttpClient build() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(getConnectionTimeout(), TimeUnit.SECONDS)
           .readTimeout(getReadTimeout(), TimeUnit.SECONDS)
           .writeTimeout(getWriteTimeout(), TimeUnit.SECONDS);

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
      builder.addInterceptor(loggingInterceptor);
      builder.eventListener(new ConnectionLoggingEventListener(logger));
    }

    return builder.build();
  }
}