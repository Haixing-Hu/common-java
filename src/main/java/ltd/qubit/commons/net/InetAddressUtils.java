////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.util.regex.Pattern;

import ltd.qubit.commons.io.Endian;

/**
 * 提供处理网络地址的实用函数。
 *
 * @author 胡海星
 */
public class InetAddressUtils {

  private InetAddressUtils() {
  }

  private static final String IPV4_BASIC_PATTERN_STRING =
      // initial first field, 0-255
      "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}"
      // following 2 fields, 0-255 followed by .
      + "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}"
      // final field, 0-255
      + "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])";

  private static final Pattern IPV4_PATTERN =
      Pattern.compile("^" + IPV4_BASIC_PATTERN_STRING + "$");

  // TODO does not allow for redundant leading zeros
  private static final Pattern IPV4_MAPPED_IPV6_PATTERN =
      Pattern.compile("^::[fF]{4}:" + IPV4_BASIC_PATTERN_STRING + "$");

  private static final Pattern IPV6_STD_PATTERN =
      Pattern.compile("^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");

  private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile(
      "^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)"  // 0-6 hex fields
      + "::"
      + "(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$"); // 0-6 hex fields

  /*
   *  The above pattern is not totally rigorous as it allows for more than 7
   * hex fields in total
   */
  private static final char COLON_CHAR = ':';

  // Must not have more than 7 colons (i.e. 8 fields)
  private static final int MAX_COLON_COUNT = 7;

  /**
   * 检查参数是否为有效的IPv4地址。
   *
   * @param input
   *     要检查有效性的地址字符串
   * @return 如果输入参数是有效的IPv4地址，则返回true
   */
  public static boolean isIPv4Address(final String input) {
    return IPV4_PATTERN.matcher(input).matches();
  }

  /**
   * 检查参数是否为有效的IPv4映射的IPv6地址。
   *
   * @param input
   *     要检查有效性的地址字符串
   * @return 如果输入参数是有效的IPv4映射的IPv6地址，则返回true
   */
  public static boolean isIPv4MappedIPv64Address(final String input) {
    return IPV4_MAPPED_IPV6_PATTERN.matcher(input).matches();
  }

  /**
   * 检查参数是否为有效的标准（非压缩）IPv6地址。
   *
   * @param input
   *     要检查有效性的地址字符串
   * @return 如果输入参数是有效的标准（非压缩）IPv6地址，则返回true
   */
  public static boolean isIPv6StdAddress(final String input) {
    return IPV6_STD_PATTERN.matcher(input).matches();
  }

  /**
   * 检查参数是否为有效的压缩IPv6地址。
   *
   * @param input
   *     要检查有效性的地址字符串
   * @return 如果输入参数是有效的压缩IPv6地址，则返回true
   */
  public static boolean isIPv6HexCompressedAddress(final String input) {
    int colonCount = 0;
    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) == COLON_CHAR) {
        colonCount++;
      }
    }
    return (colonCount <= MAX_COLON_COUNT)
        && IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
  }

  /**
   * 检查参数是否为有效的IPv6地址（包括压缩地址）。
   *
   * @param input
   *     要检查有效性的地址字符串
   * @return 如果输入参数是有效的标准或压缩IPv6地址，则返回true
   */
  public static boolean isIPv6Address(final String input) {
    return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
  }

  /**
   * 格式化IPv4地址。
   *
   * @param ip
   *     要格式化的IPv4地址，表示为指定字节序的整数
   * @param endian
   *     整数的字节序
   * @return
   *     格式化后的IPv4地址
   */
  public static String formatIPv4Address(final int ip, final Endian endian) {
    final int x1 = (ip & 0xFF);         // lowest bits
    final int x2 = ((ip >> 8) & 0xFF);
    final int x3 = ((ip >> 16) & 0xFF);
    final int x4 = ((ip >> 24) & 0xFF); // highest bits
    if (endian == null) {
      throw new IllegalArgumentException("Null endianess.");
    }
    switch (endian) {
      case LITTLE_ENDIAN:
        return String.valueOf(x1) + '.' + x2 + '.' + x3 + '.' + x4;
      case BIG_ENDIAN:
        return String.valueOf(x4) + '.' + x3 + '.' + x2 + '.' + x1;
      default:
        throw new IllegalArgumentException("Invalid endianess: " + endian);
    }
  }

  /**
   * 格式化硬件MAC地址。
   *
   * @param macAddressBytes
   *     要格式化的硬件MAC地址，表示为字节数组
   * @return
   *     格式化后的硬件MAC地址
   */
  public static String formatMacAddress(final byte[] macAddressBytes) {
    if (macAddressBytes == null) {
      throw new NullPointerException("macAddressBytes");
    }
    final StringBuilder builder = new StringBuilder();
    for (final byte b : macAddressBytes) {
      builder.append(String.format("%02X:", b));
    }
    if (builder.length() > 0) {
      builder.deleteCharAt(builder.length() - 1);
    }
    return builder.toString();
  }
}