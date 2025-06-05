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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.config.WritableConfigurable;
import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.net.interceptor.HttpLoggingInterceptor;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;

/**
 * {@link HttpClientOptions} 接口的默认实现，使用 {@link WritableConfig} 存储所有配置值。
 *
 * @author 胡海星
 */
@ThreadSafe
public class DefaultHttpClientOptions implements HttpClientOptions,
    WritableConfigurable, Serializable {

  @Serial
  private static final long serialVersionUID = 5972214359275621714L;


  private WritableConfig config;

  /**
   * 使用默认配置创建 {@link DefaultHttpClientOptions} 的新实例。
   */
  public DefaultHttpClientOptions() {
    this(new DefaultConfig());
  }

  /**
   * 使用指定配置创建 {@link DefaultHttpClientOptions} 的新实例。
   *
   * @param config
   *     要使用的配置。
   */
  public DefaultHttpClientOptions(final WritableConfig config) {
    this.config = requireNonNull("config", config);
    this.config.addStrings(KEY_SENSITIVE_HTTP_HEADERS, HttpLoggingInterceptor.DEFAULT_SENSITIVE_HEADERS);
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
  public boolean isLogHttpRequestHeader() {
    return config.getBoolean(KEY_LOG_HTTP_REQUEST_HEADER, DEFAULT_LOG_HTTP_REQUEST_HEADER);
  }

  @Override
  public HttpClientOptions setLogHttpRequestHeader(final boolean logHttpRequestHeader) {
    config.setBoolean(KEY_LOG_HTTP_REQUEST_HEADER, logHttpRequestHeader);
    return this;
  }

  @Override
  public boolean isLogHttpRequestBody() {
    return config.getBoolean(KEY_LOG_HTTP_REQUEST_BODY, DEFAULT_LOG_HTTP_REQUEST_BODY);
  }

  @Override
  public HttpClientOptions setLogHttpRequestBody(final boolean logHttpRequestBody) {
    config.setBoolean(KEY_LOG_HTTP_REQUEST_BODY, logHttpRequestBody);
    return this;
  }

  @Override
  public boolean isLogHttpResponseHeader() {
    return config.getBoolean(KEY_LOG_HTTP_RESPONSE_HEADER, DEFAULT_LOG_HTTP_RESPONSE_HEADER);
  }

  @Override
  public HttpClientOptions setLogHttpResponseHeader(final boolean logHttpResponseHeader) {
    config.setBoolean(KEY_LOG_HTTP_RESPONSE_HEADER, logHttpResponseHeader);
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

  @Override
  public List<String> getSensitiveHttpHeaders() {
    final String[] headers = config.getStrings(KEY_SENSITIVE_HTTP_HEADERS, EMPTY_STRING_ARRAY);
    if (headers == null || headers.length == 0) {
      return new ArrayList<>();
    } else {
      return new ArrayList<>(Arrays.asList(headers));
    }
  }

  @Override
  public HttpClientOptions setSensitiveHttpHeaders(final List<String> headers) {
    if (headers == null || headers.isEmpty()) {
      config.remove(KEY_SENSITIVE_HTTP_HEADERS);
    } else {
      config.setStrings(KEY_SENSITIVE_HTTP_HEADERS, headers);
    }
    return this;
  }

  @Override
  public HttpClientOptions addSensitiveHttpHeader(final String headerName) {
    if (headerName != null && !headerName.isEmpty()) {
      config.addString(KEY_SENSITIVE_HTTP_HEADERS, headerName);
    }
    return this;
  }

  @Override
  public HttpClientOptions addSensitiveHttpHeaders(final String... headerNames) {
    if (headerNames != null && headerNames.length > 0) {
      config.addStrings(KEY_SENSITIVE_HTTP_HEADERS, headerNames);
    }
    return this;
  }

  @Override
  public HttpClientOptions addSensitiveHttpHeaders(final Collection<String> headerNames) {
    if (headerNames != null && !headerNames.isEmpty()) {
      config.addStrings(KEY_SENSITIVE_HTTP_HEADERS, headerNames);
    }
    return this;
  }

  @Override
  public HttpClientOptions clearSensitiveHttpHeaders() {
    config.remove(KEY_SENSITIVE_HTTP_HEADERS);
    return this;
  }

  @Override
  public boolean isUseOnlyIpV4Address() {
    return config.getBoolean(KEY_USE_ONLY_IPV4_ADDRESS, DEFAULT_USE_ONLY_IPV4_ADDRESS);
  }

  @Override
  public HttpClientOptions setUseOnlyIpV4Address(final boolean useOnlyIpV4Address) {
    config.setBoolean(KEY_USE_ONLY_IPV4_ADDRESS, useOnlyIpV4Address);
    return this;
  }
}