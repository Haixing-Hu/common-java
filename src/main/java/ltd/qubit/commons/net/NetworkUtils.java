////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utility functions for network.
 *
 * @author Haixing Hu
 */
public class NetworkUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(NetworkUtils.class);

  /**
   * Tests whether the device can access the specified host at the specified port.
   *
   * @param host
   *     the host to be tested.
   * @param port
   *     the port to be tested.
   * @param timeout
   *     the timeout in milliseconds.
   * @return
   *     {@code true} if the device can access the specified host at the specified port,
   *     {@code false} otherwise.
   * @author Haixing Hu
   */
  public static boolean ping(final String host, final int port, final int timeout) {
    LOGGER.info("Connecting to {}:{}...", host, port);
    try (final Socket socket = new Socket()) {
      final InetSocketAddress address = new InetSocketAddress(host, port);
      socket.connect(address, timeout);
      LOGGER.info("Connect to {}:{} success.", host, port);
      return true;
    } catch (final IOException e) {
      LOGGER.error("Connect to {}:{} failed.", host, port, e);
      return false;
    }
  }
}
