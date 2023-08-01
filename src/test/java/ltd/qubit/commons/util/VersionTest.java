////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.IOException;
import java.text.ParseException;

import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.text.xml.XmlException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test of the Version class.
 *
 * @author Haixing Hu
 */
public class VersionTest {

  /**
   * Test method for {@link Version#hashCode()}.
   */
  @Test
  public void testHashCode() {
    // It's trivial, so don't need to test.
  }

  /**
   * Test method for {@link Version#EMPTY}.
   */
  @Test
  public void testEmptyVersion() {
    final Version ver = Version.EMPTY;
    assertEquals(0, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());
  }

  /**
   * Test method for {@link Version#Version(int)}.
   */
  @Test
  public void testVersionInt() {
    Version ver = null;

    ver = new Version(0);
    assertEquals(0, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(1);
    assertEquals(1, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(100);
    assertEquals(100, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(255);
    assertEquals(255, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    try {
      ver = new Version(- 1);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(256);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }
  }

  /**
   * Test method for
   * {@link Version#Version(int, int)}.
   */
  @Test
  public void testVersionIntInt() {
    Version ver = null;

    ver = new Version(0, 0);
    assertEquals(0, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(1, 0);
    assertEquals(1, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(0, 1);
    assertEquals(0, ver.major());
    assertEquals(1, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(100, 99);
    assertEquals(100, ver.major());
    assertEquals(99, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(255, 255);
    assertEquals(255, ver.major());
    assertEquals(255, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    try {
      ver = new Version(0, - 1);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(255, 256);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }
  }

  /**
   * Test method for
   * {@link Version#Version(int, int, int)}.
   */
  @Test
  public void testVersionIntIntInt() {
    Version ver = null;

    ver = new Version(0, 0, 0);
    assertEquals(0, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(1, 0, 1);
    assertEquals(1, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(1, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(0, 0, 1);
    assertEquals(0, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(1, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(100, 99, 255);
    assertEquals(100, ver.major());
    assertEquals(99, ver.minor());
    assertEquals(255, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(255, 255, 255);
    assertEquals(255, ver.major());
    assertEquals(255, ver.minor());
    assertEquals(255, ver.milli());
    assertEquals(0, ver.micro());

    try {
      ver = new Version(0, 0, - 1);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(255, 0, 256);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }
  }

  /**
   * Test method for
   * {@link Version#Version(int, int, int, int)}.
   */
  @Test
  public void testVersionIntIntIntInt() {
    Version ver = null;

    ver = new Version(0, 0, 0, 0);
    assertEquals(0, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(0, ver.micro());

    ver = new Version(1, 0, 0, 1);
    assertEquals(1, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(1, ver.micro());

    ver = new Version(255, 0, 0, 1);
    assertEquals(255, ver.major());
    assertEquals(0, ver.minor());
    assertEquals(0, ver.milli());
    assertEquals(1, ver.micro());

    ver = new Version(100, 99, 255, 255);
    assertEquals(100, ver.major());
    assertEquals(99, ver.minor());
    assertEquals(255, ver.milli());
    assertEquals(255, ver.micro());

    ver = new Version(255, 255, 255, 255);
    assertEquals(255, ver.major());
    assertEquals(255, ver.minor());
    assertEquals(255, ver.milli());
    assertEquals(255, ver.micro());

    try {
      ver = new Version(- 1, 0, 0, 0);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(0, - 1, 0, 0);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(0, 0, - 1, 0);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(0, 0, 0, - 1);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(256, 0, 0, 0);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(0, 256, 0, 0);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(0, 0, 256, 0);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version(0, 0, 0, 256);
      fail("An IllegalArgumentException should be thrown.");
    } catch (final IllegalArgumentException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }
  }

  /**
   * Test method for
   * {@link Version#Version(String)}.
   */
  @Test
  public void testVersionString() {
    Version ver = null;

    try {
      ver = new Version("0");
      assertEquals(0, ver.major());
      assertEquals(0, ver.minor());
      assertEquals(0, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("0.0");
      assertEquals(0, ver.major());
      assertEquals(0, ver.minor());
      assertEquals(0, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("0.0.0");
      assertEquals(0, ver.major());
      assertEquals(0, ver.minor());
      assertEquals(0, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("0.0.0.0");
      assertEquals(0, ver.major());
      assertEquals(0, ver.minor());
      assertEquals(0, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("255");
      assertEquals(255, ver.major());
      assertEquals(0, ver.minor());
      assertEquals(0, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("1.2");
      assertEquals(1, ver.major());
      assertEquals(2, ver.minor());
      assertEquals(0, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("1.2.03");
      assertEquals(1, ver.major());
      assertEquals(2, ver.minor());
      assertEquals(3, ver.milli());
      assertEquals(0, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("0001.02.03.04");
      assertEquals(1, ver.major());
      assertEquals(2, ver.minor());
      assertEquals(3, ver.milli());
      assertEquals(4, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("255.255.255.255");
      assertEquals(255, ver.major());
      assertEquals(255, ver.minor());
      assertEquals(255, ver.milli());
      assertEquals(255, ver.micro());
    } catch (final ParseException e) {
      fail("Should not throw exception.");
    }

    try {
      ver = new Version("");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version("-1");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version("1.");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version("1.256");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version("1.1111");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version("1.255.255.255.2");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }

    try {
      ver = new Version("1..25.6");
      fail("An IllegalArgumentException should be thrown.");
    } catch (final ParseException e) {
      // pass
    } catch (final Exception e) {
      fail("No other Exception should be thrown.");
    }
  }

  /**
   * Test method for {@link Version#major()}.
   */
  @Test
  public void testGetMajor() {
    // It was tested in the other test methods
  }

  /**
   * Test method for {@link Version#minor()}.
   */
  @Test
  public void testGetMinor() {
    // It was tested in the other test methods
  }

  /**
   * Test method for {@link Version#milli()}.
   */
  @Test
  public void testGetMilli() {
    // It was tested in the other test methods
  }

  /**
   * Test method for {@link Version#micro()}.
   */
  @Test
  public void testGetMicro() {
    // It was tested in the other test methods
  }

  /**
   * Test method for {@link Version#clone()}.
   */
  @Test
  public void testClone() {
    Version v1 = new Version(255, 255, 255, 255);
    Version v2 = null;

    v2 = v1.clone();
    assertTrue(v1 != v2);
    assertEquals(255, v2.major());
    assertEquals(255, v2.minor());
    assertEquals(255, v2.milli());
    assertEquals(255, v2.micro());

    v1 = new Version(0, 1, 2, 3);
    v2 = v1.clone();
    assertTrue(v1 != v2);
    assertEquals(0, v2.major());
    assertEquals(1, v2.minor());
    assertEquals(2, v2.milli());
    assertEquals(3, v2.micro());

    v1 = new Version(0, 0, 0, 0);
    v2 = v1.clone();
    assertTrue(v1 != v2);
    assertEquals(0, v2.major());
    assertEquals(0, v2.minor());
    assertEquals(0, v2.milli());
    assertEquals(0, v2.micro());
  }

  /**
   * Test method for
   * {@link Version#equals(Object)}.
   */
  @Test
  public void testEqualsObject() {
    Version v1 = null;
    Version v2 = null;

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 0, 0, 0);
    assertEquals(v1, v2);

    v1 = new Version(1, 2, 3, 4);
    v2 = new Version(1, 2, 3, 4);
    assertEquals(v1, v2);

    v1 = new Version(127, 2, 3, 4);
    v2 = new Version(127, 2, 3, 4);
    assertEquals(v1, v2);

    v1 = new Version(128, 2, 3, 4);
    v2 = new Version(128, 2, 3, 4);
    assertEquals(v1, v2);

    v1 = new Version(255, 255, 255, 255);
    v2 = new Version(255, 255, 255, 255);
    assertEquals(v1, v2);

    v1 = new Version(0, 0, 0, 0);
    assertFalse(v1.equals("hello"));

    v1 = new Version(0, 0, 0, 0);
    assertFalse(v1.equals(null));

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(1, 0, 0, 0);
    assertFalse(v1.equals(v2));

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 1, 0, 0);
    assertFalse(v1.equals(v2));

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 0, 1, 0);
    assertFalse(v1.equals(v2));

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 0, 0, 1);
    assertFalse(v1.equals(v2));

  }

  /**
   * Test method for
   * {@link Version#compareTo(ltd.qubit.commons.util.Version)}
   * .
   */
  @Test
  public void testCompareTo() {
    Version v1 = null;
    Version v2 = null;

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 0, 0, 0);
    assertEquals(0, v1.compareTo(v2));

    v1 = new Version(1, 2, 3, 4);
    v2 = new Version(1, 2, 3, 4);
    assertEquals(0, v1.compareTo(v2));

    v1 = new Version(127, 2, 3, 4);
    v2 = new Version(127, 2, 3, 4);
    assertEquals(0, v1.compareTo(v2));

    v1 = new Version(255, 255, 255, 255);
    v2 = new Version(255, 255, 255, 255);
    assertEquals(0, v1.compareTo(v2));

    v1 = new Version(255, 0, 0, 0);
    v2 = new Version(255, 0, 0, 0);
    assertEquals(0, v1.compareTo(v2));

    v1 = new Version(255, 1, 2, 3);
    v2 = new Version(255, 1, 2, 3);
    assertEquals(0, v1.compareTo(v2));

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 0, 0, 1);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(0, 0, 0, 1);
    v2 = new Version(0, 0, 0, 0);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 0, 1, 0);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(0, 0, 1, 0);
    v2 = new Version(0, 0, 0, 0);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(0, 1, 0, 0);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(0, 1, 0, 0);
    v2 = new Version(0, 0, 0, 0);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(0, 0, 0, 0);
    v2 = new Version(1, 0, 0, 0);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(1, 0, 0, 0);
    v2 = new Version(0, 0, 0, 0);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(0, 0, 1, 0);
    v2 = new Version(0, 0, 0, 255);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(0, 0, 255, 0);
    v2 = new Version(0, 1, 0, 1);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(1, 0, 1, 0);
    v2 = new Version(0, 255, 0, 1);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(127, 255, 255, 255);
    v2 = new Version(128, 0, 0, 0);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(128, 0, 0, 0);
    v2 = new Version(127, 255, 255, 255);
    assertTrue(v1.compareTo(v2) > 0);

    v1 = new Version(128, 0, 0, 0);
    v2 = new Version(128, 255, 255, 255);
    assertTrue(v1.compareTo(v2) < 0);

    v1 = new Version(128, 255, 255, 255);
    v2 = new Version(128, 255, 255, 254);
    assertTrue(v1.compareTo(v2) > 0);

  }

  /**
   * Test method for {@link Version#toString()}.
   */
  @Test
  public void testToString() {
    Version ver = null;

    try {
      ver = new Version("0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }

    assertEquals("0.0", ver.toString());

    try {
      ver = new Version("0.0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("0.0", ver.toString());

    try {
      ver = new Version("0.0.0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("0.0", ver.toString());

    try {
      ver = new Version("0.0.0.0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("0.0", ver.toString());

    try {
      ver = new Version("255");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("255.0", ver.toString());

    try {
      ver = new Version("1.2");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("1.2", ver.toString());

    try {
      ver = new Version("1.2.03");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("1.2.3", ver.toString());

    try {
      ver = new Version("0001.02.03.04");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("1.2.3.4", ver.toString());

    try {
      ver = new Version("255.255.255.255");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    assertEquals("255.255.255.255", ver.toString());
  }

  @Test
  public void testXmlSerialization() throws XmlException {
    Version ver = null;
    Version v2 = null;
    String xml = null;

    try {
      ver = new Version("0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("0.0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("0.0.0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("0.0.0.0");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("255");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("1.2");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("1.2.03");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("0001.02.03.04");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);

    try {
      ver = new Version("255.255.255.255");
    } catch (final ParseException e) {
      fail("Should not throw any exception.");
    }
    xml = XmlSerialization.serialize(Version.class, ver);
    System.out.println(xml);
    v2 = XmlSerialization.deserialize(Version.class, xml);
    assertEquals(ver, v2);
  }

  @Test
  public void testBinarySerialization() throws IOException {
    Version v1 = null;
    Version v2 = null;
    byte[] data = null;

    v1 = null;
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);
    try {
      v2 = BinarySerialization.deserialize(Version.class, data, false);
      fail("shoudl throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    v1 = Version.EMPTY;
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(0, 0);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(1, 0);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(0, 1);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(255);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(1, 255);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(1, 0, 255);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(2, 0, 0, 1);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

    v1 = new Version(255, 255, 255, 255);
    data = BinarySerialization.serialize(Version.class, v1);
    v2 = BinarySerialization.deserialize(Version.class, data, true);
    assertEquals(v1, v2);

  }
}
