////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InetAddressUtilsTest {

  @Test
  public void testIsIPv4Address() {
    assertFalse(InetAddressUtils.isIPv4Address(""));
    assertFalse(InetAddressUtils.isIPv4Address("www.google.com"));

    assertFalse(InetAddressUtils.isIPv4Address("192"));
    assertFalse(InetAddressUtils.isIPv4Address("192.168"));
    assertFalse(InetAddressUtils.isIPv4Address("192.168.0"));
    assertFalse(InetAddressUtils.isIPv4Address("192.168.0.1."));
    assertFalse(InetAddressUtils.isIPv4Address("0..0.0"));
    assertFalse(InetAddressUtils.isIPv4Address("0.1121.0.0"));
    assertFalse(InetAddressUtils.isIPv4Address("255.256.0.0"));

    assertTrue(InetAddressUtils.isIPv4Address("0.0.0.0"));
    assertTrue(InetAddressUtils.isIPv4Address("192.0.0.0"));
    assertTrue(InetAddressUtils.isIPv4Address("192.168.0.1"));
    assertTrue(InetAddressUtils.isIPv4Address("192.168.0.111"));
    assertFalse(InetAddressUtils.isIPv4Address("192.168.0.000111"));
    assertFalse(InetAddressUtils.isIPv4Address("0.0000000.0.000000"));
    assertFalse(InetAddressUtils.isIPv4Address("255.0000255.0101.0000"));
  }
}
