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
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

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
 * A wrapper of {@link okhttp3.logging.HttpLoggingInterceptor} which logs the
 * HTTP request and response messages using a SLF4J logger.
 *
 * @author Haixing Hu
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class HttpLoggingInterceptor implements Interceptor {

  private final Logger logger;
  private final HttpClientOptions options;

  /**
   * Creates a new instance of HttpLoggingInterceptor.
   *
   * @param logger
   *     the SLF4J logger to use for logging.
   * @param options
   *     the HTTP client options.
   */
  public HttpLoggingInterceptor(final Logger logger, final HttpClientOptions options) {
    this.logger = requireNonNull("logger", logger);
    this.options = requireNonNull("options", options);
  }

  @Override
  @Nonnull
  public Response intercept(final Interceptor.Chain chain) throws IOException {
    final Request request = chain.request();
    final boolean useHttpLogging = options.isUseHttpLogging();
    if (!useHttpLogging) {
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
    final Level loggingLevel = Level.DEBUG;
    final LoggingEventBuilder theLogger = logger.atLevel(loggingLevel);
    theLogger.log("--> {} {}", request.method(), request.url());
    // 记录请求头
    if (logRequestHeaders) {
      logRequestHeaders(theLogger, request);
    }
    // 记录请求体
    if (logRequestBody) {
      logRequestBody(theLogger, request);
    } else {
      final RequestBody requestBody = request.body();
      if (requestBody != null) {
        theLogger.log("--> END {} ({}-bytes body)", request.method(), requestBody.contentLength());
      } else {
        theLogger.log("--> END {} (no request body)", request.method());
      }
    }
    // 执行请求
    final long startNs = System.nanoTime();
    final Response response;
    try {
      response = chain.proceed(request);
    } catch (final Exception e) {
      theLogger.log("<-- HTTP FAILED: {}", e.getMessage());
      throw e;
    }
    final long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
    // 记录响应
    final ResponseBody responseBody = response.body();
    if (responseBody == null) {
      theLogger.log("<-- HTTP FAILED: response body is null");
      return response;
    }
    final long contentLength = responseBody.contentLength();
    final String bodySize = (contentLength != -1 ? contentLength + "-bytes" : "unknown-length");
    theLogger.log("<-- {} {} {} ({}ms, {}-bytes body)",
        response.code(), response.message(), request.url(), tookMs, bodySize);
    // 记录响应头
    if (logResponseHeaders) {
      logResponseHeaders(theLogger, response);
    }
    // 记录响应体
    if (logResponseBody && hasBody(response)) {
      logResponseBody(theLogger, response);
    } else {
      theLogger.log("<-- END HTTP");
    }
    return response;
  }

  private void logRequestHeaders(final LoggingEventBuilder logger, final Request request) throws IOException {
    final Headers headers = request.headers();
    final RequestBody requestBody = request.body();
    if (requestBody != null) {
      final MediaType contentType = requestBody.contentType();
      if (contentType != null && headers.get("Content-Type") == null) {
        logger.log("Content-Type: {}", contentType);
      }
      final long contentLength = requestBody.contentLength();
      if (contentLength != -1 && headers.get("Content-Length") == null) {
        logger.log("Content-Length: {}", contentLength);
      }
    }
    for (int i = 0, count = headers.size(); i < count; i++) {
      logger.log("{}: {}", headers.name(i), headers.value(i));
    }
  }

  private void logRequestBody(final LoggingEventBuilder logger, final Request request) throws IOException {
    if (bodyHasUnknownEncoding(request.headers())) {
      logger.log("--> END {} (encoded body omitted)", request.method());
    } else {
      final RequestBody requestBody = request.body();
      if (requestBody == null) {
        logger.log("--> END {} (no request body)", request.method());
      } else if (requestBody.isDuplex()) {
        logger.log("--> END {} (duplex request body omitted)", request.method());
      } else if (requestBody.isOneShot()) {
        logger.log("--> END {} (one-shot body omitted)", request.method());
      } else {
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        final Charset charset = getCharsetFromMediaType(requestBody.contentType());
        logger.log("");
        if (isProbablyUtf8(buffer)) {
          logger.log(buffer.readString(charset));
          logger.log("--> END {} ({}-bytes body)", request.method(), requestBody.contentLength());
        } else {
          logger.log("--> END {} (binary {}-bytes body omitted)", request.method(), requestBody.contentLength());
        }
      }
    }
  }

  private void logResponseHeaders(final LoggingEventBuilder logger, final Response response)
      throws IOException {
    final Headers headers = response.headers();
    for (int i = 0, count = headers.size(); i < count; i++) {
      logger.log("{}: {}", headers.name(i), headers.value(i));
    }
  }

  private void logResponseBody(final LoggingEventBuilder logger, final Response response)
      throws IOException {
    if (bodyHasUnknownEncoding(response.headers())) {
      logger.log("<-- END HTTP (encoded body omitted)");
    } else {
      final ResponseBody responseBody = response.body();
      if (responseBody == null) {
        logger.log("<-- END HTTP (no response body)");
        return;
      }
      final BufferedSource source = responseBody.source();
      source.request(Long.MAX_VALUE); // 将整个响应体缓存起来
      // 为避免消耗源中的数据，这里使用 clone() 复制一份 Buffer 用于日志输出
      Buffer buffer = source.buffer().clone();
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
          logger.log("<-- END HTTP (binary {}-bytes body omitted)", buffer.size());
        } else {
          logger.log("");
          logger.log(buffer.readString(charset));
          logger.log("<-- END HTTP ({}-bytes, {}-gzipped-bytes body)", buffer.size(), gzippedLength);
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
          logger.log("<-- END HTTP (binary {}-bytes body omitted)", buffer.size());
        } else {
          final long contentLength = responseBody.contentLength();
          if (contentLength != 0) {
            logger.log("");
            logger.log(buffer.readString(charset));
          }
          logger.log("<-- END HTTP ({}-bytes body)", buffer.size());
        }
      }
    }
  }

  /**
   * 判断请求或响应的头部是否使用了未知的编码方式（非 identity 和 gzip）。
   */
  private boolean bodyHasUnknownEncoding(final Headers headers) {
    final String contentEncoding = headers.get("Content-Encoding");
    if (contentEncoding == null) return false;
    return !contentEncoding.equalsIgnoreCase("identity")
        && !contentEncoding.equalsIgnoreCase("gzip");
  }

  /**
   * 简单判断响应是否有响应体。对于 HEAD 请求以及某些响应码通常没有响应体。
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
}