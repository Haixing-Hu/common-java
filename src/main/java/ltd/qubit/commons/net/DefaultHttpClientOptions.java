////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.Serial;
import java.io.Serializable;
import java.net.Proxy;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.config.WritableConfigurable;
import ltd.qubit.commons.config.impl.DefaultConfig;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A default implementation of {@link HttpClientOptions} interface that uses a
 * {@link WritableConfig} to store all configuration values.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class DefaultHttpClientOptions implements HttpClientOptions,
    WritableConfigurable, Serializable {

  @Serial
  private static final long serialVersionUID = 5972214359275621714L;

  /**
   * The configuration key of whether to log the HTTP response body.
   */
  public static final String KEY_LOG_HTTP_RESPONSE_BODY = "http.logging.response.body";

  /**
   * The default value of whether to log the HTTP response body.
   */
  public static final boolean DEFAULT_LOG_HTTP_RESPONSE_BODY = false;

  private WritableConfig config;

  /**
   * Creates a new instance of {@link DefaultHttpClientOptions} with a default
   * configuration.
   */
  public DefaultHttpClientOptions() {
    this(new DefaultConfig());
  }

  /**
   * Creates a new instance of {@link DefaultHttpClientOptions} with the specified
   * configuration.
   *
   * @param config
   *     the configuration to use.
   */
  public DefaultHttpClientOptions(final WritableConfig config) {
    this.config = requireNonNull("config", config);
  }

  @Override
  public WritableConfig getConfig() {
    return config;
  }

  @Override
  public DefaultHttpClientOptions setConfig(final WritableConfig config) {
    this.config = requireNonNull("config", config);
    return this;
  }

  @Override
  public boolean isUseProxy() {
    return config.getBoolean(KEY_USE_PROXY, DEFAULT_USE_PROXY);
  }

  @Override
  public HttpClientOptions setUseProxy(final boolean useProxy) {
    config.setBoolean(KEY_USE_PROXY, useProxy);
    return this;
  }

  @Override
  public String getProxyType() {
    return config.getString(KEY_PROXY_TYPE, DEFAULT_PROXY_TYPE);
  }

  @Override
  public HttpClientOptions setProxyType(final String proxyType) {
    config.setString(KEY_PROXY_TYPE, proxyType);
    return this;
  }

  @Override
  public HttpClientOptions setProxyType(final Proxy.Type proxyType) {
    config.setString(KEY_PROXY_TYPE, proxyType.name().toLowerCase());
    return this;
  }

  @Override
  @Nullable
  public String getProxyHost() {
    return config.getString(KEY_PROXY_HOST, null);
  }

  @Override
  public HttpClientOptions setProxyHost(@Nullable final String proxyHost) {
    if (proxyHost == null) {
      config.remove(KEY_PROXY_HOST);
    } else {
      config.setString(KEY_PROXY_HOST, proxyHost);
    }
    return this;
  }

  @Override
  public int getProxyPort() {
    return config.getInt(KEY_PROXY_PORT, 0);
  }

  @Override
  public HttpClientOptions setProxyPort(final int proxyPort) {
    config.setInt(KEY_PROXY_PORT, proxyPort);
    return this;
  }

  @Override
  @Nullable
  public String getProxyUsername() {
    return config.getString(KEY_PROXY_USERNAME, null);
  }

  @Override
  public HttpClientOptions setProxyUsername(@Nullable final String proxyUsername) {
    if (proxyUsername == null) {
      config.remove(KEY_PROXY_USERNAME);
    } else {
      config.setString(KEY_PROXY_USERNAME, proxyUsername);
    }
    return this;
  }

  @Override
  @Nullable
  public String getProxyPassword() {
    return config.getString(KEY_PROXY_PASSWORD, null);
  }

  @Override
  public HttpClientOptions setProxyPassword(@Nullable final String proxyPassword) {
    if (proxyPassword == null) {
      config.remove(KEY_PROXY_PASSWORD);
    } else {
      config.setString(KEY_PROXY_PASSWORD, proxyPassword);
    }
    return this;
  }

  @Override
  public int getConnectionTimeout() {
    return config.getInt(KEY_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
  }

  @Override
  public HttpClientOptions setConnectionTimeout(final int connectionTimeout) {
    config.setInt(KEY_CONNECTION_TIMEOUT, connectionTimeout);
    return this;
  }

  @Override
  public int getReadTimeout() {
    return config.getInt(KEY_READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
  }

  @Override
  public HttpClientOptions setReadTimeout(final int readTimeout) {
    config.setInt(KEY_READ_TIMEOUT, readTimeout);
    return this;
  }

  @Override
  public int getWriteTimeout() {
    return config.getInt(KEY_WRITE_TIMEOUT, DEFAULT_WRITE_TIMEOUT);
  }

  @Override
  public HttpClientOptions setWriteTimeout(final int writeTimeout) {
    config.setInt(KEY_WRITE_TIMEOUT, writeTimeout);
    return this;
  }

  @Override
  public boolean isUseHttpLogging() {
    return config.getBoolean(KEY_USE_HTTP_LOGGING, DEFAULT_USE_HTTP_LOGGING);
  }

  @Override
  public HttpClientOptions setUseHttpLogging(final boolean useHttpLogging) {
    config.setBoolean(KEY_USE_HTTP_LOGGING, useHttpLogging);
    return this;
  }

  @Override
  public boolean isLogHttpResponseBody() {
    return config.getBoolean(KEY_LOG_HTTP_RESPONSE_BODY, DEFAULT_LOG_HTTP_RESPONSE_BODY);
  }

  @Override
  public HttpClientOptions setLogHttpResponseBody(final boolean logHttpResponseBody) {
    config.setBoolean(KEY_LOG_HTTP_RESPONSE_BODY, logHttpResponseBody);
    return this;
  }
}