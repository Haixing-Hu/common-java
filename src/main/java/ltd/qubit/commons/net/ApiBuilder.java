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

import ltd.qubit.commons.config.WritableConfig;
import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A builder for building a API client.
 * <p>
 * <b>NOTE:</b> This class is <b>NOT</b> thread-safe. But the built API client
 * is thread-safe.
 *
 * @author Haixing Hu
 */
public class ApiBuilder implements HttpClientOptions {

  private Logger logger;

  private HttpClientOptions httpOptions;

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

  /**
   * Constructs a new API builder with default settings.
   */
  public ApiBuilder() {
    this(LoggerFactory.getLogger(ApiBuilder.class),
      new DefaultHttpClientOptions(),
      new CustomizedJsonMapper());
  }

  /**
   * Constructs a new API builder with the specified logger and default settings.
   *
   * @param logger
   *     the logger used by this builder, must not be {@code null}.
   */
  public ApiBuilder(final Logger logger) {
    this(logger, new DefaultHttpClientOptions(), new CustomizedJsonMapper());
  }

  /**
   * Constructs a new API builder with the specified logger and HTTP client options.
   *
   * @param logger
   *     the logger used by this builder, must not be {@code null}.
   * @param httpOptions
   *     the HTTP client options used by this builder, must not be {@code null}.
   */
  public ApiBuilder(final Logger logger, final HttpClientOptions httpOptions) {
    this(logger, httpOptions, new CustomizedJsonMapper());
  }

  /**
   * Constructs a new API builder with the specified logger and configuration.
   *
   * @param logger
   *     the logger used by this builder, must not be {@code null}.
   * @param config
   *     the configuration used by this builder, must not be {@code null}.
   */
  public ApiBuilder(final Logger logger, final WritableConfig config) {
    this(logger, new DefaultHttpClientOptions(config), new CustomizedJsonMapper());
  }

  /**
   * Constructs a new API builder with the specified logger, configuration, and JSON mapper.
   *
   * @param logger
   *     the logger used by this builder, must not be {@code null}.
   * @param config
   *     the configuration used by this builder, must not be {@code null}.
   * @param jsonMapper
   *     the JSON mapper used by this builder, must not be {@code null}.
   */
  public ApiBuilder(final Logger logger, final WritableConfig config, final JsonMapper jsonMapper) {
    this(logger, new DefaultHttpClientOptions(config), jsonMapper);
  }

  /**
   * Constructs a new API builder with the specified logger, HTTP client options, and JSON mapper.
   * <p>
   * This is the main constructor that all other constructors delegate to.
   *
   * @param logger
   *     the logger used by this builder, must not be {@code null}.
   * @param httpOptions
   *     the HTTP client options used by this builder, must not be {@code null}.
   * @param jsonMapper
   *     the JSON mapper used by this builder, must not be {@code null}.
   */
  public ApiBuilder(final Logger logger, final HttpClientOptions httpOptions, final JsonMapper jsonMapper) {
    this.logger = requireNonNull("logger", logger);
    this.httpOptions = requireNonNull("options", httpOptions);
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
  }

  /**
   * Gets the logger used by this builder.
   *
   * @return
   *     the logger used by this builder.
   */
  public Logger getLogger() {
    return logger;
  }

  /**
   * Sets the logger used by this builder.
   *
   * @param logger
   *     the logger to be used, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the logger is {@code null}.
   */
  public ApiBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  /**
   * Gets the HTTP client options used by this builder.
   *
   * @return
   *     the HTTP client options used by this builder.
   */
  public HttpClientOptions getHttpOptions() {
    return httpOptions;
  }

  /**
   * Sets the HTTP client options used by this builder.
   *
   * @param httpOptions
   *     the HTTP client options to be used, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the options is {@code null}.
   */
  public ApiBuilder setHttpOptions(final HttpClientOptions httpOptions) {
    this.httpOptions = requireNonNull("options", httpOptions);
    return this;
  }

  /**
   * Gets the JSON mapper used by this builder.
   *
   * @return
   *     the JSON mapper used by this builder.
   */
  public JsonMapper getJsonMapper() {
    return jsonMapper;
  }

  /**
   * Sets the JSON mapper used by this builder.
   *
   * @param jsonMapper
   *     the JSON mapper to be used, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the JSON mapper is {@code null}.
   */
  public ApiBuilder setJsonMapper(final JsonMapper jsonMapper) {
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
    return this;
  }

  @Override
  public boolean isUseProxy() {
    return httpOptions.isUseProxy();
  }

  @Override
  public ApiBuilder setUseProxy(final boolean useProxy) {
    httpOptions.setUseProxy(useProxy);
    return this;
  }

  @Override
  public String getProxyType() {
    return httpOptions.getProxyType();
  }

  @Override
  public ApiBuilder setProxyType(final String proxyType) {
    httpOptions.setProxyType(proxyType);
    return this;
  }

  @Override
  public ApiBuilder setProxyType(final Proxy.Type proxyType) {
    httpOptions.setProxyType(proxyType);
    return this;
  }

  @Override
  @Nullable
  public String getProxyHost() {
    return httpOptions.getProxyHost();
  }

  @Override
  public ApiBuilder setProxyHost(@Nullable final String proxyHost) {
    httpOptions.setProxyHost(proxyHost);
    return this;
  }

  @Override
  public int getProxyPort() {
    return httpOptions.getProxyPort();
  }

  @Override
  public ApiBuilder setProxyPort(final int proxyPort) {
    httpOptions.setProxyPort(proxyPort);
    return this;
  }

  @Override
  @Nullable
  public String getProxyUsername() {
    return httpOptions.getProxyUsername();
  }

  @Override
  public ApiBuilder setProxyUsername(@Nullable final String proxyUsername) {
    httpOptions.setProxyUsername(proxyUsername);
    return this;
  }

  @Override
  @Nullable
  public String getProxyPassword() {
    return httpOptions.getProxyPassword();
  }

  @Override
  public ApiBuilder setProxyPassword(@Nullable final String proxyPassword) {
    httpOptions.setProxyPassword(proxyPassword);
    return this;
  }

  @Override
  public int getConnectionTimeout() {
    return httpOptions.getConnectionTimeout();
  }

  @Override
  public ApiBuilder setConnectionTimeout(final int connectionTimeout) {
    httpOptions.setConnectionTimeout(connectionTimeout);
    return this;
  }

  @Override
  public int getReadTimeout() {
    return httpOptions.getReadTimeout();
  }

  @Override
  public ApiBuilder setReadTimeout(final int readTimeout) {
    httpOptions.setReadTimeout(readTimeout);
    return this;
  }

  @Override
  public int getWriteTimeout() {
    return httpOptions.getWriteTimeout();
  }

  @Override
  public ApiBuilder setWriteTimeout(final int writeTimeout) {
    httpOptions.setWriteTimeout(writeTimeout);
    return this;
  }

  @Override
  public boolean isUseHttpLogging() {
    return httpOptions.isUseHttpLogging();
  }

  @Override
  public ApiBuilder setUseHttpLogging(final boolean useHttpLogging) {
    httpOptions.setUseHttpLogging(useHttpLogging);
    return this;
  }

  @Override
  public boolean isLogHttpRequestHeader() {
    return httpOptions.isLogHttpRequestHeader();
  }

  @Override
  public HttpClientOptions setLogHttpRequestHeader(final boolean logHttpRequestHeader) {
    httpOptions.setLogHttpRequestHeader(logHttpRequestHeader);
    return this;
  }

  @Override
  public boolean isLogHttpRequestBody() {
    return httpOptions.isLogHttpRequestBody();
  }

  @Override
  public HttpClientOptions setLogHttpRequestBody(final boolean logHttpRequestBody) {
    httpOptions.setLogHttpRequestBody(logHttpRequestBody);
    return this;
  }

  @Override
  public boolean isLogHttpResponseHeader() {
    return httpOptions.isLogHttpResponseHeader();
  }

  @Override
  public HttpClientOptions setLogHttpResponseHeader(final boolean logHttpResponseHeader) {
    httpOptions.setLogHttpResponseHeader(logHttpResponseHeader);
    return this;
  }

  @Override
  public boolean isLogHttpResponseBody() {
    return httpOptions.isLogHttpResponseBody();
  }

  @Override
  public ApiBuilder setLogHttpResponseBody(final boolean useHttpLogging) {
    httpOptions.setLogHttpResponseBody(useHttpLogging);
    return this;
  }

  @Override
  public List<String> getSensitiveHttpHeaders() {
    return httpOptions.getSensitiveHttpHeaders();
  }

  @Override
  public ApiBuilder setSensitiveHttpHeaders(final List<String> headers) {
    httpOptions.setSensitiveHttpHeaders(headers);
    return this;
  }

  @Override
  public ApiBuilder addSensitiveHttpHeader(final String headerName) {
    httpOptions.addSensitiveHttpHeader(headerName);
    return this;
  }

  @Override
  public ApiBuilder addSensitiveHttpHeaders(final String... headerNames) {
    httpOptions.addSensitiveHttpHeaders(headerNames);
    return this;
  }

  @Override
  public ApiBuilder addSensitiveHttpHeaders(final Collection<String> headerNames) {
    httpOptions.addSensitiveHttpHeaders(headerNames);
    return this;
  }

  @Override
  public ApiBuilder clearSensitiveHttpHeaders() {
    httpOptions.clearSensitiveHttpHeaders();
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
  public ApiBuilder addInterceptor(final Interceptor interceptor) {
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
  public ApiBuilder addInterceptors(final Interceptor ... interceptors) {
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
  public ApiBuilder addInterceptors(final Collection<Interceptor> interceptors) {
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
  public ApiBuilder clearInterceptors() {
    interceptors.clear();
    return this;
  }

  /**
   * Gets the base URL of the API server.
   *
   * @return
   *     the base URL of the API server.
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Sets the base URL of the API server.
   *
   * @param baseUrl
   *     the base URL of the API server, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the base URL is {@code null}.
   */
  public ApiBuilder setBaseUrl(final String baseUrl) {
    this.baseUrl = requireNonNull("baseUrl", baseUrl);
    return this;
  }

  /**
   * Gets the HTTP client used by this builder.
   * <p>
   * If not explicitly set, a new HTTP client will be created by this builder
   * when building the API client.
   *
   * @return
   *     the HTTP client used by this builder, or {@code null} if not set.
   */
  public OkHttpClient getHttpClient() {
    return httpClient;
  }

  /**
   * Sets the HTTP client to be used by this builder.
   * <p>
   * If set, this client will be used directly when building the API client.
   * If not set, a new HTTP client will be created based on the other
   * configuration options.
   *
   * @param httpClient
   *     the HTTP client to be used, or {@code null} to create a new one.
   * @return
   *     this builder, to support method chaining.
   */
  public ApiBuilder setHttpClient(final OkHttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  /**
   * Gets the callback executor for the API client.
   *
   * @return
   *     the callback executor, or {@code null} if not set.
   */
  @Nullable
  public Executor getCallbackExecutor() {
    return callbackExecutor;
  }

  /**
   * Sets the callback executor for the API client.
   * <p>
   * The executor is used to deliver callbacks on the specified thread.
   *
   * @param callbackExecutor
   *     the callback executor, or {@code null} to use the default one.
   * @return
   *     this builder, to support method chaining.
   */
  public ApiBuilder setCallbackExecutor(@Nullable final Executor callbackExecutor) {
    this.callbackExecutor = callbackExecutor;
    return this;
  }

  /**
   * Gets whether to validate the API interface eagerly.
   *
   * @return
   *     whether to validate the API interface eagerly, or {@code null} if not set.
   */
  @Nullable
  public Boolean getValidateEagerly() {
    return validateEagerly;
  }

  /**
   * Sets whether to validate the API interface eagerly.
   * <p>
   * When enabled, Retrofit will perform runtime checks to verify that each method
   * in the API interface is properly configured. This is useful during development
   * to catch configuration errors early.
   *
   * @param validateEagerly
   *     {@code true} to validate the API interface eagerly, {@code false} to
   *     validate lazily, or {@code null} to use the default setting.
   * @return
   *     this builder, to support method chaining.
   */
  public ApiBuilder setValidateEagerly(@Nullable final Boolean validateEagerly) {
    this.validateEagerly = validateEagerly;
    return this;
  }

  /**
   * Adds a converter factory to the API client.
   * <p>
   * Converter factories are used to convert between HTTP bodies and Java objects.
   *
   * @param converterFactory
   *     the converter factory to add, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the converter factory is {@code null}.
   */
  public ApiBuilder addConverterFactory(final Converter.Factory converterFactory) {
    converterFactories.add(requireNonNull("converterFactory", converterFactory));
    return this;
  }

  /**
   * Adds multiple converter factories to the API client.
   * <p>
   * Converter factories are used to convert between HTTP bodies and Java objects.
   *
   * @param converterFactories
   *     the array of converter factories to add, elements must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if any of the converter factories is {@code null}.
   */
  public ApiBuilder addConverterFactories(final Converter.Factory ... converterFactories) {
    for (final Converter.Factory converterFactory : converterFactories) {
      this.converterFactories.add(requireNonNull("converterFactory", converterFactory));
    }
    return this;
  }

  /**
   * Adds a collection of converter factories to the API client.
   * <p>
   * Converter factories are used to convert between HTTP bodies and Java objects.
   *
   * @param converterFactories
   *     the collection of converter factories to add, elements must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if any of the converter factories is {@code null}.
   */
  public ApiBuilder addConverterFactories(final Collection<Converter.Factory> converterFactories) {
    for (final Converter.Factory converterFactory : converterFactories) {
      this.converterFactories.add(requireNonNull("converterFactory", converterFactory));
    }
    return this;
  }

  /**
   * Clears all converter factories previously added to this builder.
   *
   * @return
   *     this builder, to support method chaining.
   */
  public ApiBuilder clearConverterFactories() {
    converterFactories.clear();
    return this;
  }

  /**
   * Adds a call adapter factory to the API client.
   * <p>
   * Call adapter factories are used to adapt the response types of methods
   * in the API interface.
   *
   * @param callAdapterFactory
   *     the call adapter factory to add, must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if the call adapter factory is {@code null}.
   */
  public ApiBuilder addCallAdapterFactory(final CallAdapter.Factory callAdapterFactory) {
    callAdapterFactories.add(requireNonNull("callAdapterFactory", callAdapterFactory));
    return this;
  }

  /**
   * Adds multiple call adapter factories to the API client.
   * <p>
   * Call adapter factories are used to adapt the response types of methods
   * in the API interface.
   *
   * @param callAdapterFactories
   *     the array of call adapter factories to add, elements must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if any of the call adapter factories is {@code null}.
   */
  public ApiBuilder addCallAdapterFactories(final CallAdapter.Factory ... callAdapterFactories) {
    for (final CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
      this.callAdapterFactories.add(requireNonNull("callAdapterFactory", callAdapterFactory));
    }
    return this;
  }

  /**
   * Adds a collection of call adapter factories to the API client.
   * <p>
   * Call adapter factories are used to adapt the response types of methods
   * in the API interface.
   *
   * @param callAdapterFactories
   *     the collection of call adapter factories to add, elements must not be {@code null}.
   * @return
   *     this builder, to support method chaining.
   * @throws NullPointerException
   *     if any of the call adapter factories is {@code null}.
   */
  public ApiBuilder addCallAdapterFactories(final Collection<CallAdapter.Factory> callAdapterFactories) {
    for (final CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
      this.callAdapterFactories.add(requireNonNull("callAdapterFactory", callAdapterFactory));
    }
    return this;
  }

  /**
   * Clears all call adapter factories previously added to this builder.
   *
   * @return
   *     this builder, to support method chaining.
   */
  public ApiBuilder clearCallAdapterFactories() {
    callAdapterFactories.clear();
    return this;
  }

  /**
   * Builds a RESTful API client.
   * <p>
   * This method creates a fully configured Retrofit client based on the settings
   * provided to this builder. The configuration process follows these steps:
   * <ol>
   *   <li>Creates a Retrofit builder with the configured base URL</li>
   *   <li>Configures the HTTP client, either using the one provided via
   *       {@link #setHttpClient(OkHttpClient)} or creating a new one based on
   *       the HTTP client options</li>
   *   <li>Sets the callback executor if specified</li>
   *   <li>Sets the eager validation setting if specified</li>
   *   <li>Adds converter factories, defaulting to a Jackson converter using the
   *       configured JSON mapper if none are explicitly added</li>
   *   <li>Adds call adapter factories if any are specified</li>
   *   <li>Builds the Retrofit client and creates the API implementation</li>
   * </ol>
   *
   * @param <API>
   *     the interface type of the API.
   * @param apiClass
   *     the interface class of the API, must not be {@code null}.
   * @return
   *     the API client implementation.
   * @throws IllegalArgumentException
   *     if the base URL is not set or is invalid, or if the API interface
   *     is not properly defined.
   * @throws NullPointerException
   *     if the API class is {@code null}.
   */
  public <API> API build(final Class<API> apiClass) {
    final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
        .baseUrl(baseUrl);
    if (httpClient != null) {
      retrofitBuilder.client(httpClient);
    } else {
      final HttpClientBuilder clientBuilder = new HttpClientBuilder(logger, httpOptions)
          .addInterceptors(interceptors);

      // Sensitive HTTP headers were already passed to HttpClientBuilder via httpOptions

      final OkHttpClient client = clientBuilder.build();
      retrofitBuilder.client(client);
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