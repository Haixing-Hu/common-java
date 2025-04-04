/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor.Level;

import ltd.qubit.commons.net.HttpClientOptions;

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

  private final okhttp3.logging.HttpLoggingInterceptor.Logger delegateLogger;
  private final okhttp3.logging.HttpLoggingInterceptor delegate;
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
    delegateLogger = new okhttp3.logging.HttpLoggingInterceptor.Logger() {
      @Override
      public void log(@Nonnull final String message) {
        logger.debug(message);
      }
    };
    this.delegate = new okhttp3.logging.HttpLoggingInterceptor(this.delegateLogger);
    this.logger = requireNonNull("logger", logger);
    this.options = requireNonNull("options", options);
    updateLogLevel();
  }

  private void updateLogLevel() {
    if (logger.isTraceEnabled() && options.isLogHttpResponseBody()) {
      delegate.setLevel(Level.BODY);
    } else {
      delegate.setLevel(Level.HEADERS);
    }
  }

  @Nonnull
  @Override
  public Response intercept(@Nonnull final Chain chain) throws IOException {
    updateLogLevel();
    return delegate.intercept(chain);
  }
}