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
  private final okhttp3.logging.HttpLoggingInterceptor.Logger delegateLogger;
  private final okhttp3.logging.HttpLoggingInterceptor delegate;

  public HttpLoggingInterceptor(final Logger logger) {
    this.logger = logger;
    delegateLogger = new okhttp3.logging.HttpLoggingInterceptor.Logger() {
      @Override
      public void log(@Nonnull final String message) {
        logger.debug(message);
      }
    };
    this.delegate = new okhttp3.logging.HttpLoggingInterceptor(this.delegateLogger);
  }

  @Nonnull
  @Override
  public Response intercept(@Nonnull final Chain chain) throws IOException {
    if (logger.isTraceEnabled()) {
      delegate.setLevel(Level.BODY);
    }
    return delegate.intercept(chain);
  }
}
