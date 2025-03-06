////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Protocol;

/**
 * A listener for logging OkHttp connection events.
 *
 * @author Haixing Hu
 */
public class ConnectionLoggingEventListener extends EventListener {

  private final Logger logger;

  /**
   * Creates a new connection event listener.
   *
   * @param logger
   *     The logger used to log connection events.
   */
  public ConnectionLoggingEventListener(@Nonnull final Logger logger) {
    this.logger = logger;
  }

  @Override
  public void connectStart(@Nonnull final Call call,
      @Nonnull final InetSocketAddress inetSocketAddress,
      @Nonnull final Proxy proxy) {
    logger.debug("HTTP connection start: URL={}, address={}, proxy={}",
        call.request().url(), inetSocketAddress, proxy);
    super.connectStart(call, inetSocketAddress, proxy);
  }

  @Override
  public void connectEnd(@Nonnull final Call call,
      @Nonnull final InetSocketAddress inetSocketAddress,
      @Nonnull final Proxy proxy, @Nullable final Protocol protocol) {
    logger.debug("HTTP connection successful: URL={}, address={}, proxy={}, protocol={}",
        call.request().url(), inetSocketAddress, proxy, protocol);
    super.connectEnd(call, inetSocketAddress, proxy, protocol);
  }

  @Override
  public void connectFailed(@Nonnull final Call call,
      @Nonnull final InetSocketAddress inetSocketAddress,
      @Nonnull final Proxy proxy, @Nullable final Protocol protocol,
      @Nonnull final IOException ioe) {
    logger.error("HTTP connection failed: URL={}, address={}, proxy={}, protocol={}, error={}",
        call.request().url(), inetSocketAddress, proxy, protocol, ioe.getMessage());
    if (logger.isDebugEnabled()) {
      logger.debug("Connection failure details:", ioe);
    }
    super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
  }
}