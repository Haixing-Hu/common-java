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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.json.JsonMapper;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.net.HttpClientBuilder.DEFAULT_CONNECTION_TIMEOUT;
import static ltd.qubit.commons.net.HttpClientBuilder.DEFAULT_PROXY_TYPE;
import static ltd.qubit.commons.net.HttpClientBuilder.DEFAULT_READ_TIMEOUT;
import static ltd.qubit.commons.net.HttpClientBuilder.DEFAULT_USE_LOGGING;
import static ltd.qubit.commons.net.HttpClientBuilder.DEFAULT_USE_PROXY;
import static ltd.qubit.commons.net.HttpClientBuilder.DEFAULT_WRITE_TIMEOUT;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_CONNECTION_TIMEOUT;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_PROXY_HOST;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_PROXY_PASSWORD;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_PROXY_PORT;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_PROXY_TYPE;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_PROXY_USERNAME;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_READ_TIMEOUT;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_USE_LOGGING;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_USE_PROXY;
import static ltd.qubit.commons.net.HttpClientBuilder.KEY_WRITE_TIMEOUT;

/**
 * A builder for building a API client.
 * <p>
 * <b>NOTE:</b> This class is <b>NOT</b> thread-safe. But the built API client
 * is thread-safe.
 *
 * @author Haixing Hu
 */
public class ApiBuilder {

  private Logger logger;

  private DefaultConfig config;

  private JsonMapper jsonMapper;

  private String baseUrl;

  private OkHttpClient httpClient;

  @Nullable
  private Executor callbackExecutor;

  @Nullable
  private Boolean validateEagerly;

  private final List<Interceptor> interceptors = new ArrayList<>();

  private final List<Converter.Factory> converterFactories = new ArrayList<>();

  private final List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>();

  public ApiBuilder() {
    this(LoggerFactory.getLogger(ApiBuilder.class), new DefaultConfig());
  }

  public ApiBuilder(final Logger logger) {
    this(logger, new DefaultConfig());
  }

  public ApiBuilder(final Logger logger, final DefaultConfig config) {
    this(logger, config, new CustomizedJsonMapper());
  }

  public ApiBuilder(final Logger logger, final DefaultConfig config, final JsonMapper jsonMapper) {
    this.logger = requireNonNull("logger", logger);
    this.config = requireNonNull("config", config);
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
  }

  public Logger getLogger() {
    return logger;
  }

  public ApiBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  public Config getConfig() {
    return config;
  }

  public ApiBuilder setConfig(final DefaultConfig config) {
    this.config = requireNonNull("config", config);
    return this;
  }

  public JsonMapper getJsonMapper() {
    return jsonMapper;
  }

  public ApiBuilder setJsonMapper(final JsonMapper jsonMapper) {
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
    return this;
  }

  public boolean isUseProxy() {
    return config.getBoolean(KEY_USE_PROXY, DEFAULT_USE_PROXY);
  }

  public ApiBuilder setUseProxy(final boolean useProxy) {
    config.setBoolean(KEY_USE_PROXY, useProxy);
    return this;
  }

  public String getProxyType() {
    return config.getString(KEY_PROXY_TYPE, DEFAULT_PROXY_TYPE);
  }

  public ApiBuilder setProxyType(final String proxyType) {
    config.setString(KEY_PROXY_TYPE, proxyType);
    return this;
  }

  public ApiBuilder setProxyType(final Proxy.Type proxyType) {
    config.setString(KEY_PROXY_TYPE, proxyType.name());
    return this;
  }

  @Nullable
  public String getProxyHost() {
    return config.getString(KEY_PROXY_HOST, null);
  }

  public ApiBuilder setProxyHost(@Nullable final String proxyHost) {
    config.setString(KEY_PROXY_HOST, proxyHost);
    return this;
  }

  public int getProxyPort() {
    return config.getInt(KEY_PROXY_PORT, 0);
  }

  public ApiBuilder setProxyPort(final int proxyPort) {
    config.setInt(KEY_PROXY_PORT, proxyPort);
    return this;
  }

  @Nullable
  public String getProxyUsername() {
    return config.getString(KEY_PROXY_USERNAME, null);
  }

  public ApiBuilder setProxyUsername(@Nullable final String proxyUsername) {
    config.setString(KEY_PROXY_USERNAME, proxyUsername);
    return this;
  }

  @Nullable
  public String getProxyPassword() {
    return config.getString(KEY_PROXY_PASSWORD, null);
  }

  public ApiBuilder setProxyPassword(@Nullable final String proxyPassword) {
    config.setString(KEY_PROXY_PASSWORD, proxyPassword);
    return this;
  }

  public int getConnectionTimeout() {
    return config.getInt(KEY_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
  }

  public ApiBuilder setConnectionTimeout(final int connectionTimeout) {
    config.setInt(KEY_CONNECTION_TIMEOUT, connectionTimeout);
    return this;
  }

  public int getReadTimeout() {
    return config.getInt(KEY_READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
  }

  public ApiBuilder setReadTimeout(final int readTimeout) {
    config.setInt(KEY_READ_TIMEOUT, readTimeout);
    return this;
  }

  public int getWriteTimeout() {
    return config.getInt(KEY_WRITE_TIMEOUT, DEFAULT_WRITE_TIMEOUT);
  }

  public ApiBuilder setWriteTimeout(final int writeTimeout) {
    config.setInt(KEY_WRITE_TIMEOUT, writeTimeout);
    return this;
  }

  public boolean isUseLogging() {
    return config.getBoolean(KEY_USE_LOGGING, DEFAULT_USE_LOGGING);
  }

  public ApiBuilder setUseLogging(final boolean useLogging) {
    config.setBoolean(KEY_USE_LOGGING, useLogging);
    return this;
  }

  public ApiBuilder addInterceptor(final Interceptor interceptor) {
    interceptors.add(requireNonNull("interceptor", interceptor));
    return this;
  }

  public ApiBuilder addInterceptors(final Interceptor ... interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  public ApiBuilder addInterceptors(final Collection<Interceptor> interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  public ApiBuilder clearInterceptors() {
    interceptors.clear();
    return this;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public ApiBuilder setBaseUrl(final String baseUrl) {
    this.baseUrl = requireNonNull("baseUrl", baseUrl);
    return this;
  }

  public OkHttpClient getHttpClient() {
    return httpClient;
  }

  public ApiBuilder setHttpClient(final OkHttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  @Nullable
  public Executor getCallbackExecutor() {
    return callbackExecutor;
  }

  public ApiBuilder setCallbackExecutor(@Nullable final Executor callbackExecutor) {
    this.callbackExecutor = callbackExecutor;
    return this;
  }

  @Nullable
  public Boolean getValidateEagerly() {
    return validateEagerly;
  }

  public ApiBuilder setValidateEagerly(@Nullable final Boolean validateEagerly) {
    this.validateEagerly = validateEagerly;
    return this;
  }

  public ApiBuilder addConverterFactory(final Converter.Factory converterFactory) {
    converterFactories.add(requireNonNull("converterFactory", converterFactory));
    return this;
  }

  public ApiBuilder addConverterFactories(final Converter.Factory ... converterFactories) {
    for (final Converter.Factory converterFactory : converterFactories) {
      this.converterFactories.add(requireNonNull("converterFactory", converterFactory));
    }
    return this;
  }

  public ApiBuilder addConverterFactories(final Collection<Converter.Factory> converterFactories) {
    for (final Converter.Factory converterFactory : converterFactories) {
      this.converterFactories.add(requireNonNull("converterFactory", converterFactory));
    }
    return this;
  }

  public ApiBuilder clearConverterFactories() {
    converterFactories.clear();
    return this;
  }

  public ApiBuilder addCallAdapterFactory(final CallAdapter.Factory callAdapterFactory) {
    callAdapterFactories.add(requireNonNull("callAdapterFactory", callAdapterFactory));
    return this;
  }

  public ApiBuilder addCallAdapterFactories(final CallAdapter.Factory ... callAdapterFactories) {
    for (final CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
      this.callAdapterFactories.add(requireNonNull("callAdapterFactory", callAdapterFactory));
    }
    return this;
  }

  public ApiBuilder addCallAdapterFactories(final Collection<CallAdapter.Factory> callAdapterFactories) {
    for (final CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
      this.callAdapterFactories.add(requireNonNull("callAdapterFactory", callAdapterFactory));
    }
    return this;
  }

  public ApiBuilder clearCallAdapterFactories() {
    callAdapterFactories.clear();
    return this;
  }

  /**
   * Builds a RESTful API client.
   *
   * @param <API>
   *     the interface type of the API.
   * @param apiClass
   *     the interface class of the API.
   * @return
   *     the API client.
   */
  public <API> API build(final Class<API> apiClass) {
    final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
        .baseUrl(baseUrl);
    if (httpClient != null) {
      retrofitBuilder.client(httpClient);
    } else {
      final HttpClientBuilder builder = new HttpClientBuilder(logger, config);
      builder.addInterceptors(interceptors);
      retrofitBuilder.client(builder.build());
    }
    if (callbackExecutor != null) {
      retrofitBuilder.callbackExecutor(callbackExecutor);
    }
    if (validateEagerly != null) {
      retrofitBuilder.validateEagerly(validateEagerly);
    }
    if (converterFactories.isEmpty()) {
      retrofitBuilder.addConverterFactory(JacksonConverterFactory.create(jsonMapper));
    } else {
      for (final Converter.Factory converterFactory : converterFactories) {
        retrofitBuilder.addConverterFactory(converterFactory);
      }
    }
    if (!callAdapterFactories.isEmpty()) {
      for (final CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
        retrofitBuilder.addCallAdapterFactory(callAdapterFactory);
      }
    }
    return retrofitBuilder.build().create(apiClass);
  }
}
