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
 * 用于构建API客户端的构建器。
 * <p>
 * <b>注意：</b> 此类 <b>不是</b> 线程安全的。但构建出的API客户端是线程安全的。
 *
 * @author 胡海星
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
   * 使用默认设置构造新的API构建器。
   */
  public ApiBuilder() {
    this(LoggerFactory.getLogger(ApiBuilder.class),
      new DefaultHttpClientOptions(),
      new CustomizedJsonMapper());
  }

  /**
   * 使用指定的日志记录器和默认设置构造新的API构建器。
   *
   * @param logger
   *     此构建器使用的日志记录器，不能为 {@code null}。
   */
  public ApiBuilder(final Logger logger) {
    this(logger, new DefaultHttpClientOptions(), new CustomizedJsonMapper());
  }

  /**
   * 使用指定的日志记录器和HTTP客户端选项构造新的API构建器。
   *
   * @param logger
   *     此构建器使用的日志记录器，不能为 {@code null}。
   * @param httpOptions
   *     此构建器使用的HTTP客户端选项，不能为 {@code null}。
   */
  public ApiBuilder(final Logger logger, final HttpClientOptions httpOptions) {
    this(logger, httpOptions, new CustomizedJsonMapper());
  }

  /**
   * 使用指定的日志记录器和配置构造新的API构建器。
   *
   * @param logger
   *     此构建器使用的日志记录器，不能为 {@code null}。
   * @param config
   *     此构建器使用的配置，不能为 {@code null}。
   */
  public ApiBuilder(final Logger logger, final WritableConfig config) {
    this(logger, new DefaultHttpClientOptions(config), new CustomizedJsonMapper());
  }

  /**
   * 使用指定的日志记录器、配置和JSON映射器构造新的API构建器。
   *
   * @param logger
   *     此构建器使用的日志记录器，不能为 {@code null}。
   * @param config
   *     此构建器使用的配置，不能为 {@code null}。
   * @param jsonMapper
   *     此构建器使用的JSON映射器，不能为 {@code null}。
   */
  public ApiBuilder(final Logger logger, final WritableConfig config, final JsonMapper jsonMapper) {
    this(logger, new DefaultHttpClientOptions(config), jsonMapper);
  }

  /**
   * 使用指定的日志记录器、HTTP客户端选项和JSON映射器构造新的API构建器。
   * <p>
   * 这是所有其他构造函数委托的主构造函数。
   *
   * @param logger
   *     此构建器使用的日志记录器，不能为 {@code null}。
   * @param httpOptions
   *     此构建器使用的HTTP客户端选项，不能为 {@code null}。
   * @param jsonMapper
   *     此构建器使用的JSON映射器，不能为 {@code null}。
   */
  public ApiBuilder(final Logger logger, final HttpClientOptions httpOptions, final JsonMapper jsonMapper) {
    this.logger = requireNonNull("logger", logger);
    this.httpOptions = requireNonNull("options", httpOptions);
    this.jsonMapper = requireNonNull("jsonMapper", jsonMapper);
  }

  /**
   * 获取此构建器使用的日志记录器。
   *
   * @return
   *     此构建器使用的日志记录器。
   */
  public Logger getLogger() {
    return logger;
  }

  /**
   * 设置此构建器使用的日志记录器。
   *
   * @param logger
   *     要使用的日志记录器，不能为 {@code null}。
   * @return
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果日志记录器为 {@code null}。
   */
  public ApiBuilder setLogger(final Logger logger) {
    this.logger = requireNonNull("logger", logger);
    return this;
  }

  /**
   * 获取此构建器使用的HTTP客户端选项。
   *
   * @return
   *     此构建器使用的HTTP客户端选项。
   */
  public HttpClientOptions getHttpOptions() {
    return httpOptions;
  }

  /**
   * 设置此构建器使用的HTTP客户端选项。
   *
   * @param httpOptions
   *     要使用的HTTP客户端选项，不能为 {@code null}。
   * @return
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果选项为 {@code null}。
   */
  public ApiBuilder setHttpOptions(final HttpClientOptions httpOptions) {
    this.httpOptions = requireNonNull("options", httpOptions);
    return this;
  }

  /**
   * 获取此构建器使用的JSON映射器。
   *
   * @return
   *     此构建器使用的JSON映射器。
   */
  public JsonMapper getJsonMapper() {
    return jsonMapper;
  }

  /**
   * 设置此构建器使用的JSON映射器。
   *
   * @param jsonMapper
   *     要使用的JSON映射器，不能为 {@code null}。
   * @return
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果JSON映射器为 {@code null}。
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
   * 向HTTP客户端添加拦截器。
   * <p>
   * 拦截器可用于监控、修改或重试HTTP请求和响应。
   *
   * @param interceptor
   *     要添加的拦截器，不能为 {@code null}。
   * @return
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果拦截器为 {@code null}。
   */
  public ApiBuilder addInterceptor(final Interceptor interceptor) {
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
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果任何拦截器为 {@code null}。
   */
  public ApiBuilder addInterceptors(final Interceptor ... interceptors) {
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
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果任何拦截器为 {@code null}。
   */
  public ApiBuilder addInterceptors(final Collection<Interceptor> interceptors) {
    for (final Interceptor interceptor : interceptors) {
      this.interceptors.add(requireNonNull("interceptor", interceptor));
    }
    return this;
  }

  /**
   * 清除之前添加到此构建器的所有拦截器。
   *
   * @return
   *     此构建器，用于支持方法链式调用。
   */
  public ApiBuilder clearInterceptors() {
    interceptors.clear();
    return this;
  }

  /**
   * 获取API服务器的基础URL。
   *
   * @return
   *     API服务器的基础URL。
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * 设置API服务器的基础URL。
   *
   * @param baseUrl
   *     API服务器的基础URL，不能为 {@code null}。
   * @return
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果基础URL为 {@code null}。
   */
  public ApiBuilder setBaseUrl(final String baseUrl) {
    this.baseUrl = requireNonNull("baseUrl", baseUrl);
    return this;
  }

  /**
   * 获取此构建器使用的HTTP客户端。
   * <p>
   * 如果未明确设置，此构建器在构建API客户端时将创建新的HTTP客户端。
   *
   * @return
   *     此构建器使用的HTTP客户端，如果未设置则为 {@code null}。
   */
  public OkHttpClient getHttpClient() {
    return httpClient;
  }

  /**
   * 设置此构建器要使用的HTTP客户端。
   * <p>
   * 如果设置，此客户端将在构建API客户端时直接使用。
   * 如果未设置，将根据其他配置选项创建新的HTTP客户端。
   *
   * @param httpClient
   *     要使用的HTTP客户端，或 {@code null} 以创建新客户端。
   * @return
   *     此构建器，用于支持方法链式调用。
   */
  public ApiBuilder setHttpClient(final OkHttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  /**
   * 获取API客户端的回调执行器。
   *
   * @return
   *     回调执行器，如果未设置则为 {@code null}。
   */
  @Nullable
  public Executor getCallbackExecutor() {
    return callbackExecutor;
  }

  /**
   * 设置API客户端的回调执行器。
   * <p>
   * 执行器用于在指定线程上传递回调。
   *
   * @param callbackExecutor
   *     回调执行器，或 {@code null} 以使用默认执行器。
   * @return
   *     此构建器，用于支持方法链式调用。
   */
  public ApiBuilder setCallbackExecutor(@Nullable final Executor callbackExecutor) {
    this.callbackExecutor = callbackExecutor;
    return this;
  }

  /**
   * 获取是否急切验证API接口。
   * <p>
   * 启用后，Retrofit将执行运行时检查以验证API接口中的每个方法是否正确配置。
   * 这在开发期间很有用，可以提早发现配置错误。
   *
   * @return
   *     是否急切验证API接口，如果未设置则为 {@code null}。
   */
  @Nullable
  public Boolean getValidateEagerly() {
    return validateEagerly;
  }

  /**
   * 设置是否急切验证API接口。
   * <p>
   * 启用后，Retrofit将执行运行时检查以验证API接口中的每个方法是否正确配置。
   * 这在开发期间很有用，可以提早发现配置错误。
   *
   * @param validateEagerly
   *     {@code true} 表示急切验证API接口，{@code false} 表示懒惰验证，
   *     或 {@code null} 以使用默认设置。
   * @return
   *     此构建器，用于支持方法链式调用。
   */
  public ApiBuilder setValidateEagerly(@Nullable final Boolean validateEagerly) {
    this.validateEagerly = validateEagerly;
    return this;
  }

  /**
   * 向API客户端添加转换器工厂。
   * <p>
   * 转换器工厂用于在HTTP主体和Java对象之间进行转换。
   *
   * @param converterFactory
   *     要添加的转换器工厂，不能为 {@code null}。
   * @return
   *     此构建器，用于支持方法链式调用。
   * @throws NullPointerException
   *     如果转换器工厂为 {@code null}。
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
   * 构建RESTful API客户端。
   * <p>
   * 此方法根据提供给此构建器的设置创建完全配置的Retrofit客户端。配置过程遵循以下步骤：
   * <ol>
   *   <li>使用配置的基础URL创建Retrofit构建器</li>
   *   <li>配置HTTP客户端，使用通过 {@link #setHttpClient(OkHttpClient)} 提供的客户端
   *       或根据HTTP客户端选项创建新客户端</li>
   *   <li>如果指定，设置回调执行器</li>
   *   <li>如果指定，设置急切验证设置</li>
   *   <li>添加转换器工厂，如果没有明确添加，则默认使用配置的JSON映射器的Jackson转换器</li>
   *   <li>如果指定，添加调用适配器工厂</li>
   *   <li>构建Retrofit客户端并创建API实现</li>
   * </ol>
   *
   * @param <API>
   *     API的接口类型。
   * @param apiClass
   *     API的接口类，不能为 {@code null}。
   * @return
   *     API客户端实现。
   * @throws IllegalArgumentException
   *     如果基础URL未设置或无效，或API接口定义不正确。
   * @throws NullPointerException
   *     如果API类为 {@code null}。
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