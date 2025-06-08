////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 提供网络相关的实用函数。
 *
 * @author 胡海星
 */
public class NetworkUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(NetworkUtils.class);

  /**
   * 测试设备是否可以访问指定主机的指定端口。
   *
   * @param host
   *     要测试的主机。
   * @param port
   *     要测试的端口。
   * @param timeout
   *     超时时间（毫秒）。
   * @return
   *     如果设备可以访问指定主机的指定端口，则返回 {@code true}，否则返回 {@code false}。
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