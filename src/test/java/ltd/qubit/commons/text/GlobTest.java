////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.io.io.error.InvalidFormatException;
import ltd.qubit.commons.io.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.io.serialize.XmlSerialization;
import ltd.qubit.commons.text.xml.XmlException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for the {@link Glob} class.
 *
 * @author Haixing Hu
 */
public class GlobTest {

  /**
   * Test method for
   * {@link Glob#toRegex(String)}.
   */
  @Test
  public void testToRegex() {
    String glob = null;
    String regex = null;

    glob = "";
    regex = Glob.toRegex(glob);
    assertEquals("", regex);

    glob = "*";
    regex = Glob.toRegex(glob);
    assertEquals(".*", regex);

    glob = "*.java";
    regex = Glob.toRegex(glob);
    assertEquals(".*\\.java", regex);

    glob = "*.[ch]";
    regex = Glob.toRegex(glob);
    assertEquals(".*\\.[ch]", regex);

    glob = "*.{c,cpp,h,hpp,cxx,hxx}";
    regex = Glob.toRegex(glob);
    assertEquals(".*\\.(c|cpp|h|hpp|cxx|hxx)", regex);

    glob = "[^#]*";
    regex = Glob.toRegex(glob);
    assertEquals("[^#].*", regex);

    glob = "h?ello*.{c,cpp,h,hpp,cxx,hxx}";
    regex = Glob.toRegex(glob);
    assertEquals("h.ello.*\\.(c|cpp|h|hpp|cxx|hxx)", regex);
  }

  @Test
  public void testXmlSerilization() throws XmlException {
    Glob g1 = null;
    Glob g2 = null;
    String xml = null;

    g1 = new Glob();
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*", Pattern.CASE_INSENSITIVE);
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*.java", Pattern.CANON_EQ);
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*.[ch]", Pattern.CANON_EQ | Pattern.UNICODE_CASE);
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("*.{c,cpp,h,hpp,cxx,hxx}");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("[^#]*");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("h?ello*.{c,cpp,h,hpp,cxx,hxx}");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);

    g1 = new Glob("h?e>l<l&o*.{c,cpp,h,hpp,cxx,hxx}");
    xml = XmlSerialization.serialize(Glob.class, g1);
    System.out.println(xml);
    g2 = XmlSerialization.deserialize(Glob.class, xml);
    assertEquals(g1, g2);
  }

  @Test
  public void testBinarySerialization() throws IOException {
    Glob g1 = null;
    Glob g2 = null;
    byte[] data = null;

    // test for null
    g1 = null;
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);
    try {
      g2 = BinarySerialization.deserialize(Glob.class, data, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    // test for empty
    g1 = new Glob();
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*", Pattern.CASE_INSENSITIVE);
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*.java", Pattern.CANON_EQ);
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*.[ch]", Pattern.CANON_EQ | Pattern.UNICODE_CASE);
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("*.{c,cpp,h,hpp,cxx,hxx}");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("[^#]*");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("h?ello*.{c,cpp,h,hpp,cxx,hxx}");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);

    g1 = new Glob("h?e>l<l&o*.{c,cpp,h,hpp,cxx,hxx}");
    data = BinarySerialization.serialize(Glob.class, g1);
    g2 = BinarySerialization.deserialize(Glob.class, data, true);
    assertEquals(g1, g2);
  }

}
