/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import ltd.qubit.commons.net.HttpClientOptions;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link okhttp3.logging.HttpLoggingInterceptor} 的包装器，使用SLF4J日志记录器
 * 记录HTTP请求和响应消息。
 *
 * @author 胡海星
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class HttpLoggingInterceptor implements Interceptor {

  /**
   * 默认的敏感头部列表。
   */
  public static final String[] DEFAULT_SENSITIVE_HEADERS = {
      "Authorization",
      "Api-Key",
      "X-Api-Key",
      "Bearer",
      "Cookie",
      "Set-Cookie",
      "Secret-Key",
      "Client-Secret",
      "Access-Token",
      "Refresh-Token",
      "Private-Token",
      "Session-Token",
      "JWT-Token",
      "Password",
      "X-Auth-Password",
      "X-Client-ID",
      "X-Client-Secret",
      "X-Auth-Token",
      "X-Auth-App-Token",
      "X-Auth-User-Token",
  };

  private final Logger logger;
  private final HttpClientOptions options;
  private final Set<String> sensitiveHeaders;

  /**
   * 创建使用默认敏感头部的HttpLoggingInterceptor新实例。
   *
   * @param logger
   *     用于记录日志的SLF4J记录器。
   * @param options
   *     HTTP客户端选项。
   */
  public HttpLoggingInterceptor(final Logger logger, final HttpClientOptions options) {
    this(logger, options, DEFAULT_SENSITIVE_HEADERS);
  }

  /**
   * 创建使用自定义敏感头部的HttpLoggingInterceptor新实例。
   *
   * @param logger
   *     用于记录日志的SLF4J记录器。
   * @param options
   *     HTTP客户端选项。
   * @param sensitiveHeaders
   *     需要在日志中屏蔽的敏感头部名称列表。
   */
  public HttpLoggingInterceptor(final Logger logger, final HttpClientOptions options,
      final String[] sensitiveHeaders) {
    this.logger = requireNonNull("logger", logger);
    this.options = requireNonNull("options", options);
    this.sensitiveHeaders = new HashSet<>();
    if (sensitiveHeaders != null) {
      for (final String header : sensitiveHeaders) {
        if (header != null && !header.isEmpty()) {
          this.sensitiveHeaders.add(header.toLowerCase());
        }
      }
    }
  }

  /**
   * 向列表中添加新的敏感HTTP头部名称。
   *
   * @param headerName
   *     要视为敏感的头部名称。
   * @return
   *     此实例，用于方法链式调用。
   */
  public HttpLoggingInterceptor addSensitiveHttpHeader(final String headerName) {
    if (headerName != null && !headerName.isEmpty()) {
      this.sensitiveHeaders.add(headerName.toLowerCase());
    }
    return this;
  }

  /**
   * 添加多个在日志中应被屏蔽的敏感HTTP头部。
   *
   * @param headerNames
   *     要视为敏感的头部名称。
   * @return
   *     此实例，用于方法链式调用。
   */
  public HttpLoggingInterceptor addSensitiveHttpHeaders(final String... headerNames) {
    if (headerNames != null) {
      for (final String header : headerNames) {
        addSensitiveHttpHeader(header);
      }
    }
    return this;
  }

  @Override
  @Nonnull
  public Response intercept(final Interceptor.Chain chain) throws IOException {
    final Request request = chain.request();
    final boolean useHttpLogging = options.isUseHttpLogging();
    if (!useHttpLogging) {
      return chain.proceed(request);
    }
    if (!logger.isTraceEnabled()) {
      return chain.proceed(request);
    }
    final boolean logRequestHeaders = options.isLogHttpRequestHeader();
    final boolean logRequestBody = options.isLogHttpRequestBody();
    final boolean logResponseHeaders = options.isLogHttpResponseHeader();
    final boolean logResponseBody = options.isLogHttpResponseBody();
    // 如果全部日志记录开关都未开启，则直接执行请求
    if (!logRequestHeaders
        && !logRequestBody
        && !logResponseHeaders
        && !logResponseBody) {
      return chain.proceed(request);
    }
    logger.trace("--> {} {}", request.method(), request.url());
    // 记录请求头
    if (logRequestHeaders) {
      logRequestHeaders(request);
    }
    // 记录请求体
    if (logRequestBody) {
      logRequestBody(request);
    } else {
      final RequestBody requestBody = request.body();
      if (requestBody != null) {
        logger.trace("--> END {} ({}-bytes body)", request.method(), requestBody.contentLength());
      } else {
        logger.trace("--> END {} (no request body)", request.method());
      }
    }
    // 执行请求
    final long startNs = System.nanoTime();
    final Response response;
    try {
      response = chain.proceed(request);
    } catch (final Exception e) {
      logger.trace("<-- HTTP FAILED: {}", e.getMessage());
      throw e;
    }
    final long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
    // 记录响应
    final ResponseBody responseBody = response.body();
    if (responseBody == null) {
      logger.trace("<-- HTTP FAILED: response body is null");
      return response;
    }
    final long contentLength = responseBody.contentLength();
    final String bodySize = (contentLength != -1 ? contentLength + "-bytes" : "unknown-length");
    logger.trace("<-- {} {} {} ({}ms, {}-bytes body)",
        response.code(), response.message(), request.url(), tookMs, bodySize);
    // 记录响应头
    if (logResponseHeaders) {
      logResponseHeaders(response);
    }
    // 记录响应体
    if (logResponseBody && hasBody(response)) {
      logResponseBody(response);
    } else {
      logger.trace("<-- END HTTP");
    }
    return response;
  }

  private void logRequestHeaders(final Request request) throws IOException {
    final RequestBody requestBody = request.body();
    final Headers headers = request.headers();
    if (requestBody != null) {
      final MediaType contentType = requestBody.contentType();
      logger.trace("Content-Type: {}", contentType);
      final long contentLength = requestBody.contentLength();
      if (contentLength != -1) {
        logger.trace("Content-Length: {}", contentLength);
      }
    }
    for (int i = 0, count = headers.size(); i < count; i++) {
      final String name = headers.name(i);
      final String value = maskSensitiveHeaderValue(name, headers.value(i));
      logger.trace("{}: {}", name, value);
    }
  }

  private void logRequestBody(final Request request) throws IOException {
    if (bodyHasUnknownEncoding(request.headers())) {
      logger.trace("--> END {} (encoded body omitted)", request.method());
    } else {
      final RequestBody requestBody = request.body();
      if (requestBody == null) {
        logger.trace("--> END {} (no request body)", request.method());
      } else if (requestBody.isDuplex()) {
        logger.trace("--> END {} (duplex request body omitted)", request.method());
      } else if (requestBody.isOneShot()) {
        logger.trace("--> END {} (one-shot body omitted)", request.method());
      } else {
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        final Charset charset = getCharsetFromMediaType(requestBody.contentType());
        logger.trace("");
        final long contentLength = requestBody.contentLength();
        if (isProbablyUtf8(buffer)) {
          logger.trace(buffer.readString(charset));
          logger.trace("--> END {} ({}-bytes body)", request.method(), contentLength);
        } else {
          logger.trace("--> END {} (binary {}-bytes body omitted)", request.method(), contentLength);
        }
      }
    }
  }

  private void logResponseHeaders(final Response response)
      throws IOException {
    final Headers headers = response.headers();
    for (int i = 0, count = headers.size(); i < count; i++) {
      final String name = headers.name(i);
      final String value = maskSensitiveHeaderValue(name, headers.value(i));
      logger.trace("{}: {}", name, value);
    }
  }

  private void logResponseBody(final Response response)
      throws IOException {
    if (bodyHasUnknownEncoding(response.headers())) {
      logger.trace("<-- END HTTP (encoded body omitted)");
    } else {
      final ResponseBody responseBody = response.body();
      if (responseBody == null) {
        logger.trace("<-- END HTTP (no response body)");
        return;
      }
      final BufferedSource source = responseBody.source();
      source.request(Long.MAX_VALUE); // 将整个响应体缓存起来
      // 为避免消耗源中的数据，这里使用 clone() 复制一份 Buffer 用于日志输出
      Buffer buffer = source.getBuffer().clone();
      final String contentEncoding = response.headers().get("Content-Encoding");
      if ("gzip".equalsIgnoreCase(contentEncoding)) {
        final long gzippedLength = buffer.size();
        try (final GzipSource gzipSource = new GzipSource(buffer.clone())) {
          final Buffer decompressed = new Buffer();
          decompressed.writeAll(gzipSource);
          buffer = decompressed;
        }
        final Charset charset = getCharsetFromMediaType(responseBody.contentType());
        if (!isProbablyUtf8(buffer)) {
          logger.trace("<-- END HTTP (binary {}-bytes body omitted)", buffer.size());
        } else {
          logger.trace("");
          logger.trace(buffer.readString(charset));
          logger.trace("<-- END HTTP ({}-bytes, {}-gzipped-bytes body)", buffer.size(), gzippedLength);
        }
      } else {
        Charset charset = UTF_8;
        final MediaType mediaType = responseBody.contentType();
        if (mediaType != null) {
          final Charset ct = mediaType.charset(UTF_8);
          if (ct != null) {
            charset = ct;
          }
        }
        if (!isProbablyUtf8(buffer)) {
          logger.trace("<-- END HTTP (binary {}-bytes body omitted)", buffer.size());
        } else {
          final long contentLength = responseBody.contentLength();
          if (contentLength != 0) {
            logger.trace("");
            logger.trace(buffer.readString(charset));
          }
          logger.trace("<-- END HTTP ({}-bytes body)", buffer.size());
        }
      }
    }
  }

  /**
   * 判断请求或响应的头部是否使用了未知的编码方式（非identity和gzip）。
   */
  private boolean bodyHasUnknownEncoding(final Headers headers) {
    final String contentEncoding = headers.get("Content-Encoding");
    if (contentEncoding == null) return false;
    return !contentEncoding.equalsIgnoreCase("identity")
        && !contentEncoding.equalsIgnoreCase("gzip");
  }

  /**
   * 简单判断响应是否有响应体。对于HEAD请求以及某些响应码通常没有响应体。
   */
  private boolean hasBody(final Response response) {
    // HEAD 请求没有响应体
    if (response.request().method().equalsIgnoreCase("HEAD")) {
      return false;
    }
    final int code = response.code();
    if ((code >= 100 && code < 200) || code == 204 || code == 304) {
      return response.body() != null && response.body().contentLength() != 0;
    }
    return true;
  }

  private Charset getCharsetFromMediaType(@Nullable final MediaType mediaType) {
    Charset charset = UTF_8;
    if (mediaType != null) {
      final Charset ct = mediaType.charset(UTF_8);
      if (ct != null) {
        charset = ct;
      }
    }
    return charset;
  }

  private boolean isProbablyUtf8(final Buffer buffer) {
    try {
      final Buffer prefix = new Buffer();
      final long byteCount = Math.min(buffer.size(), 64);
      buffer.copyTo(prefix, 0, byteCount);
      for (int i = 0; i < 16; ++i) {
        if (prefix.exhausted()) {
          break;
        }
        final int codePoint = prefix.readUtf8CodePoint();
        if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
          return false;
        }
      }
      return true;
    } catch (final EOFException e) {
      return false; // Truncated UTF-8 sequence.
    }
  }

  /**
   * 对敏感的HTTP头部值进行打码处理。
   *
   * @param name
   *     HTTP头部名称
   * @param value
   *     HTTP头部值
   * @return
   *     如果头部是敏感的，返回打码后的值；否则返回原值
   */
  private String maskSensitiveHeaderValue(final String name, final String value) {
    if (value == null || value.isEmpty()) {
      return value;
    }
    if (sensitiveHeaders.contains(name.toLowerCase())) {
      if (value.length() <= 4) {
        return "****";
      } else {
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
      }
    }
    return value;
  }
}