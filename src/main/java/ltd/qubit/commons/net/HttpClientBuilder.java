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

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.impl.DefaultConfig;
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
public class HttpClientBuilder {
  /**
   * The configuration key of the connection timeout, in seconds.
   */
  public static final String KEY_CONNECTION_TIMEOUT = "http.timeout.connection";

  /**
   * The configuration key of the read timeout, in seconds.
   */
  public static final String KEY_READ_TIMEOUT = "http.timeout.read";

  /**
   * The configuration key of the write timeout, in seconds.
   */
  public static final String KEY_WRITE_TIMEOUT = "http.timeout.write";

  /**
   * The configuration key of whether to use the proxy server while connecting
   * to the model provider.
   */
  public static final String KEY_USE_PROXY = "http.proxy.use";

  /**
   * The configuration key of the proxy server type.
   */
  public static final String KEY_PROXY_TYPE = "http.proxy.type";

  /**
   * The configuration key of the proxy server host.
   */
  public static final String KEY_PROXY_HOST = "http.proxy.host";

  /**
   * The configuration key of the proxy server port.
   */
  public static final String KEY_PROXY_PORT = "http.proxy.port";

  /**
   * The configuration key of the proxy server username.
   */
  public static final String KEY_PROXY_USERNAME = "http.proxy.username";

  /**
   * The configuration key of the proxy server password.
   */
  public static final String KEY_PROXY_PASSWORD = "http.proxy.password";

  /**
   * The configuration key of whether to use the logging interceptor.
   */
  public static final String KEY_USE_LOGGING = "http.logging.use";

  /**
   * The default timeout for the connection in seconds.
   */
  public static final int DEFAULT_CONNECTION_TIMEOUT = 10;

  /**
   * The default timeout for the read in seconds.
   */
  public static final int DEFAULT_READ_TIMEOUT = 120;

  /**
   * The default timeout for the write out seconds.
   */
  public static final int DEFAULT_WRITE_TIMEOUT = 120;

  /**
   * The default value of whether to use the proxy server.
   */
  public static final boolean DEFAULT_USE_PROXY = false;

  /**
   * The default value of the proxy server type.
   */
  public static final String DEFAULT_PROXY_TYPE = "http";

  /**
   * The default value of whether to use the logging interceptor.
   */
  public static final boolean DEFAULT_USE_LOGGING = true;

  private final List<Interceptor> interceptors = new ArrayList<>();

  private Logger logger;

  private DefaultConfig config;

  /**
   * Construct a provider of the HTTP client.
   */
  public HttpClientBuilder() {
    this(LoggerFactory.getLogger(HttpClientBuilder.class), new DefaultConfig());
  }

  /**
   * Construct a provider of the HTTP client.
   *
   * @param logger
   *     the logger used by the provided http client.
   */
  public HttpClientBuilder(final Logger logger) {
    this(logger, new DefaultConfig());
  }

  /**
   * Construct a provider of the HTTP client.
   *
   * @param logger
   *     the logger used by the provided http client.
   * @param config
   *     the configuration used by the provided http client.
   */
  public HttpClientBuilder(final Logger logger, final DefaultConfig config) {
    this.logger = requireNonNull("logger", logger);
    this.config = requireNonNull("config", config);
  }

  public Logger getLogger() {
    return logger;
  }

  public HttpClientBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  public Config getConfig() {
    return config;
  }

  public HttpClientBuilder setConfig(final DefaultConfig config) {
    this.config = requireNonNull("config", config);
    return this;
  }

  public boolean isUseProxy() {
    return config.getBoolean(KEY_USE_PROXY, DEFAULT_USE_PROXY);
  }

  public HttpClientBuilder setUseProxy(final boolean useProxy) {
    config.setBoolean(KEY_USE_PROXY, useProxy);
    return this;
  }

  public String getProxyType() {
    return config.getString(KEY_PROXY_TYPE, DEFAULT_PROXY_TYPE);
  }

  public HttpClientBuilder setProxyType(final String proxyType) {
    config.setString(KEY_PROXY_TYPE, proxyType);
    return this;
  }

  public HttpClientBuilder setProxyType(final Proxy.Type proxyType) {
    config.setString(KEY_PROXY_TYPE, proxyType.name());
    return this;
  }

  @Nullable
  public String getProxyHost() {
    return config.getString(KEY_PROXY_HOST, null);
  }

  public HttpClientBuilder setProxyHost(@Nullable final String proxyHost) {
    config.setString(KEY_PROXY_HOST, proxyHost);
    return this;
  }

  public int getProxyPort() {
    return config.getInt(KEY_PROXY_PORT, 0);
  }

  public HttpClientBuilder setProxyPort(final int proxyPort) {
    config.setInt(KEY_PROXY_PORT, proxyPort);
    return this;
  }

  @Nullable
  public String getProxyUsername() {
    return config.getString(KEY_PROXY_USERNAME, null);
  }

  public HttpClientBuilder setProxyUsername(@Nullable final String proxyUsername) {
    config.setString(KEY_PROXY_USERNAME, proxyUsername);
    return this;
  }

  @Nullable
  public String getProxyPassword() {
    return config.getString(KEY_PROXY_PASSWORD, null);
  }

  public HttpClientBuilder setProxyPassword(@Nullable final String proxyPassword) {
    config.setString(KEY_PROXY_PASSWORD, proxyPassword);
    return this;
  }

  public int getConnectionTimeout() {
    return config.getInt(KEY_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
  }

  public HttpClientBuilder setConnectionTimeout(final int connectionTimeout) {
    config.setInt(KEY_CONNECTION_TIMEOUT, connectionTimeout);
    return this;
  }

  public int getReadTimeout() {
    return config.getInt(KEY_READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
  }

  public HttpClientBuilder setReadTimeout(final int readTimeout) {
    config.setInt(KEY_READ_TIMEOUT, readTimeout);
    return this;
  }

  public int getWriteTimeout() {
    return config.getInt(KEY_WRITE_TIMEOUT, DEFAULT_WRITE_TIMEOUT);
  }

  public HttpClientBuilder setWriteTimeout(final int writeTimeout) {
    config.setInt(KEY_WRITE_TIMEOUT, writeTimeout);
    return this;
  }

  public boolean isUseLogging() {
    return config.getBoolean(KEY_USE_LOGGING, DEFAULT_USE_LOGGING);
  }

  public HttpClientBuilder setUseLogging(final boolean useLogging) {
    config.setBoolean(KEY_USE_LOGGING, useLogging);
    return this;
  }

  public HttpClientBuilder addInterceptor(final Interceptor interceptor) {
    interceptors.add(requireNonNull("interceptor", interceptor));
    return this;
  }

  public HttpClientBuilder addInterceptors(final Interceptor ... interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  public HttpClientBuilder addInterceptors(final Collection<Interceptor> interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  public HttpClientBuilder clearInterceptors() {
    interceptors.clear();
    return this;
  }

  protected Proxy getProxy() {
    if (isUseProxy()) {
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
    } else {
      return null;
    }
  }

  private OkHttpClient.Builder getHttpClientBuilder() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(getConnectionTimeout(), TimeUnit.SECONDS) //自定义超时时间
           .readTimeout(getReadTimeout(), TimeUnit.SECONDS)    //自定义超时时间
           .writeTimeout(getWriteTimeout(), TimeUnit.SECONDS);   //自定义超时时间
    final Proxy proxy = getProxy();
    if (proxy != null) {
      addProxy(builder, proxy);
    }
    for (final Interceptor interceptor : interceptors) {
      builder.addInterceptor(interceptor);
    }
    if (isUseLogging()) {
      addLoggingInterceptor(builder);
    }
    return builder;
  }

  private void addProxy(final OkHttpClient.Builder builder, final Proxy proxy) {
    logger.info("Using proxy: {}", proxy);
    builder.proxy(proxy);
    // add proxy authentication
    final String username = getProxyUsername();
    if (username != null) {
      final String password = getProxyPassword();
      // password may be null, in which case it will be treated as an empty string
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

  private void addLoggingInterceptor(final OkHttpClient.Builder builder) {
    // check whether there is a HttpLoggingInterceptor exists
    for (final Interceptor interceptor : builder.interceptors()) {
      if (interceptor instanceof HttpLoggingInterceptor) {
        return;
      }
    }
    // add a HttpLoggingInterceptor
    builder.addInterceptor(new HttpLoggingInterceptor(logger));
  }

  /**
   * Build a pre-configured HTTP client.
   *
   * @return
   *    A pre-configured HTTP client.
   */
  public OkHttpClient build() {
    final OkHttpClient.Builder builder = getHttpClientBuilder();
    return builder.build();
  }
}
