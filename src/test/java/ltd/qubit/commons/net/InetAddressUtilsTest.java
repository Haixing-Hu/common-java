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

import ltd.qubit.commons.io.Endian;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.net.InetAddressUtils.formatIPv4Address;
import static ltd.qubit.commons.net.InetAddressUtils.formatMacAddress;
import static ltd.qubit.commons.net.InetAddressUtils.isIPv4Address;

public class InetAddressUtilsTest {

  @Test
  public void testIsIPv4Address() {
    assertFalse(isIPv4Address(""));
    assertFalse(isIPv4Address("www.google.com"));

    assertFalse(isIPv4Address("192"));
    assertFalse(isIPv4Address("192.168"));
    assertFalse(isIPv4Address("192.168.0"));
    assertFalse(isIPv4Address("192.168.0.1."));
    assertFalse(isIPv4Address("0..0.0"));
    assertFalse(isIPv4Address("0.1121.0.0"));
    assertFalse(isIPv4Address("255.256.0.0"));

    assertTrue(isIPv4Address("0.0.0.0"));
    assertTrue(isIPv4Address("192.0.0.0"));
    assertTrue(isIPv4Address("192.168.0.1"));
    assertTrue(isIPv4Address("192.168.0.111"));
    assertFalse(isIPv4Address("192.168.0.000111"));
    assertFalse(isIPv4Address("0.0000000.0.000000"));
    assertFalse(isIPv4Address("255.0000255.0101.0000"));
  }


  @Test
  public void testFormatIPv4Address_BigEndian() {
    // 192.168.1.2 in Big Endian is 0xC0A80102
    assertEquals("192.168.1.2", formatIPv4Address(0xC0A80102, Endian.BIG_ENDIAN));
  }

  @Test
  public void testFormatIPv4Address_LittleEndian() {
    // 192.168.1.2 in Little Endian is 0x0201A8C0
    assertEquals("192.168.1.2", formatIPv4Address(0x0201A8C0, Endian.LITTLE_ENDIAN));
  }

  @Test
  public void testFormatIPv4Address_ZeroAddress() {
    // 0.0.0.0
    assertEquals("0.0.0.0", formatIPv4Address(0x00000000, Endian.BIG_ENDIAN));
    assertEquals("0.0.0.0", formatIPv4Address(0x00000000, Endian.LITTLE_ENDIAN));
  }

  @Test
  public void testFormatIPv4Address_FullAddress() {
    // 255.255.255.255
    assertEquals("255.255.255.255", formatIPv4Address(0xFFFFFFFF, Endian.BIG_ENDIAN));
    assertEquals("255.255.255.255", formatIPv4Address(0xFFFFFFFF, Endian.LITTLE_ENDIAN));
  }

  @Test
  public void testFormatIPv4Address_InvalidEndian() {
    // Invalid Endian
    assertThrows(IllegalArgumentException.class, () -> {
      formatIPv4Address(0x00000000, null);
    });
  }

  @Test
  void testFormatMacAddress_Normal() {
    final byte[] macBytes = new byte[] {
        (byte)0x00, (byte)0x1A, (byte)0x2B, (byte)0x3C, (byte)0x4D, (byte)0x5E,
    };
    final String expected = "00:1A:2B:3C:4D:5E";
    assertEquals(expected, formatMacAddress(macBytes));
  }

  @Test
  void testFormatMacAddress_EmptyArray() {
    final byte[] macBytes = new byte[0];
    final String expected = "";
    assertEquals(expected, formatMacAddress(macBytes));
  }

  @Test
  void testFormatMacAddress_Null() {
    assertThrows(NullPointerException.class, () -> formatMacAddress(null));
  }
}
