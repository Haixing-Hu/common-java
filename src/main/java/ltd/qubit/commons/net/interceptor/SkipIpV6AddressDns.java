// ////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
// ////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import okhttp3.Dns;

/**
 * 这个类用于在 DNS 解析时跳过 IPv6 地址，只返回 IPv4 地址。
 *
 * @author 胡海星
 */
public class SkipIpV6AddressDns implements Dns {

  /**
   * 单例实例。
   */
  public static final SkipIpV6AddressDns INSTANCE = new SkipIpV6AddressDns();

  @Nonnull
  @Override
  public List<InetAddress> lookup(@NotNull final String hostname) throws UnknownHostException {
    final InetAddress[] addresses = InetAddress.getAllByName(hostname);
    return Arrays.stream(addresses)
                 .filter((r) -> r instanceof Inet4Address)
                 .toList();
  }
}