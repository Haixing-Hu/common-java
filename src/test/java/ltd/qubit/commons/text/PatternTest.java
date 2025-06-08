////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlDeserializeEquals;
import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlSerializeEquals;
import static ltd.qubit.commons.test.XmlUnitUtils.assertXmlEqual;

/**
 * Unit test of the {@link Pattern} class.
 *
 * @author Haixing Hu
 */
public class PatternTest {

  /**
   * Test method for {@link Pattern#Pattern()}.
   */
  @Test
  public void testPattern() {
    final Pattern pattern = new Pattern();
    assertNull(pattern.getType());
    assertNull(pattern.getIgnoreCase());
    assertEquals(StringUtils.EMPTY, pattern.getExpression());
  }

  /**
   * Test method for {@link Pattern#Pattern(PatternType, String)}.
   */
  @Test
  public void testPatternPatternTypeString() {
    Pattern pattern = new Pattern(PatternType.SUFFIX, "suffix");
    assertEquals(PatternType.SUFFIX, pattern.getType());
    assertNull(pattern.getIgnoreCase());
    assertEquals("suffix", pattern.getExpression());

    pattern = new Pattern(PatternType.PREFIX, "prefix");
    assertEquals(PatternType.PREFIX, pattern.getType());
    assertNull(pattern.getIgnoreCase());
    assertEquals("prefix", pattern.getExpression());
  }

  /**
   * Test method for {@link Pattern#Pattern(PatternType, Boolean, String)}.
   */
  @Test
  public void testPatternPatternTypeBooleanString() {
    Pattern pattern = new Pattern(PatternType.SUFFIX, true, "suffix");
    assertEquals(PatternType.SUFFIX, pattern.getType());
    assertTrue(pattern.getIgnoreCase());
    assertEquals("suffix", pattern.getExpression());

    pattern = new Pattern(PatternType.PREFIX, false, "prefix");
    assertEquals(PatternType.PREFIX, pattern.getType());
    assertFalse(pattern.getIgnoreCase());
    assertEquals("prefix", pattern.getExpression());
  }

  /**
   * Test method for {@link Pattern#getType()} and {@link Pattern#setType(PatternType)}.
   */
  @Test
  public void testGetSetType() {
    final Pattern pattern = new Pattern();

    assertNull(pattern.getType());

    pattern.setType(PatternType.GLOB);
    assertEquals(PatternType.GLOB, pattern.getType());

    pattern.setType(PatternType.PREFIX);
    assertEquals(PatternType.PREFIX, pattern.getType());

    pattern.setType(PatternType.REGEX);
    assertEquals(PatternType.REGEX, pattern.getType());

    pattern.setType(PatternType.LITERAL);
    assertEquals(PatternType.LITERAL, pattern.getType());

    pattern.setType(PatternType.SUFFIX);
    assertEquals(PatternType.SUFFIX, pattern.getType());

    pattern.setType(null);
    assertNull(pattern.getType());
  }

  /**
   * Test method for {@link Pattern#getIgnoreCase()} and
   * {@link Pattern#setIgnoreCase(Boolean)}.
   */
  @Test
  public void testGetSetCaseInsensitive() {
    final Pattern pattern = new Pattern();

    assertNull(pattern.getIgnoreCase());

    pattern.setIgnoreCase(true);
    assertTrue(pattern.getIgnoreCase());

    pattern.setIgnoreCase(false);
    assertFalse(pattern.getIgnoreCase());
  }

  /**
   * Test method for {@link Pattern#getExpression()} and
   * {@link Pattern#setExpression(String)}.
   */
  @Test
  public void testGetSetExpression() {
    final Pattern pattern = new Pattern();

    assertEquals(StringUtils.EMPTY, pattern.getExpression());

    pattern.setExpression("test");
    assertEquals("test", pattern.getExpression());

    try {
      pattern.setExpression(null);
      fail("Must throw NullPointerException.");
    } catch (final NullPointerException e) {
      // pass
    }
  }


  /**
   * Test method for {@link Pattern#matches(String)}.
   */
  @Test
  public void testMatches() {
    testMatchesForStringPattern();
    testMatchesForPrefixPattern();
    testMatchesForSuffixPattern();
    testMatchesForRegexPattern();
    testMatchesForGlobPattern();
  }

  private void testMatchesForStringPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.LITERAL);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertTrue(p.matches(""));
    assertFalse(p.matches("str"));

    p.setExpression("hello");
    assertTrue(p.matches("hello"));
    assertFalse(p.matches("str"));
    assertFalse(p.matches(""));
    assertFalse(p.matches("Hello"));

    p.setIgnoreCase(true);
    assertTrue(p.matches("hello"));
    assertFalse(p.matches("str"));
    assertFalse(p.matches(""));
    assertTrue(p.matches("Hello"));
  }

  private void testMatchesForPrefixPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.PREFIX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertTrue(p.matches(""));
    assertTrue(p.matches("str"));

    p.setExpression("hello");
    assertTrue(p.matches("hello"));
    assertTrue(p.matches("hello123"));
    assertFalse(p.matches("str"));
    assertFalse(p.matches(""));
    assertFalse(p.matches("Hello"));
    assertFalse(p.matches("Hello123"));

    p.setIgnoreCase(true);
    assertTrue(p.matches("hello"));
    assertTrue(p.matches("hello123"));
    assertFalse(p.matches("str"));
    assertFalse(p.matches(""));
    assertTrue(p.matches("Hello"));
    assertTrue(p.matches("Hello123"));
  }

  private void testMatchesForSuffixPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.SUFFIX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertTrue(p.matches(""));
    assertTrue(p.matches("str"));

    p.setExpression("hello");
    assertTrue(p.matches("hello"));
    assertTrue(p.matches("123hello"));
    assertFalse(p.matches("hello123"));
    assertFalse(p.matches("str"));
    assertFalse(p.matches(""));
    assertFalse(p.matches("Hello"));
    assertFalse(p.matches("123Hello"));
    assertFalse(p.matches("Hello123"));

    p.setIgnoreCase(true);
    assertTrue(p.matches("hello"));
    assertTrue(p.matches("123hello"));
    assertFalse(p.matches("hello123"));
    assertFalse(p.matches("str"));
    assertFalse(p.matches(""));
    assertTrue(p.matches("Hello"));
    assertTrue(p.matches("123Hello"));
    assertFalse(p.matches("Hello123"));
  }

  private void testMatchesForRegexPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.REGEX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertTrue(p.matches(""));
    assertFalse(p.matches("str"));

    p.setExpression(".");
    assertFalse(p.matches(""));
    assertTrue(p.matches("s"));
    assertTrue(p.matches("t"));
    assertFalse(p.matches("st"));

    p.setExpression(".*");
    assertTrue(p.matches(""));
    assertTrue(p.matches("s"));
    assertTrue(p.matches("s"));
    assertTrue(p.matches("st"));

    p.setExpression("ab.c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertFalse(p.matches("abc"));
    assertFalse(p.matches("abxyc"));
    assertFalse(p.matches("abx"));

    p.setExpression("ab.*c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertTrue(p.matches("abc"));
    assertTrue(p.matches("abxyc"));
    assertFalse(p.matches("aBxc"));
    assertFalse(p.matches("Abyc"));
    assertFalse(p.matches("abC"));
    assertFalse(p.matches("AbxYC"));
    assertFalse(p.matches("abx"));

    p.setExpression("ab.+c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertFalse(p.matches("abc"));
    assertTrue(p.matches("abxyc"));
    assertFalse(p.matches("aBxc"));
    assertFalse(p.matches("Abyc"));
    assertFalse(p.matches("abC"));
    assertFalse(p.matches("AbxYC"));
    assertFalse(p.matches("abx"));

    p.setIgnoreCase(true);
    p.setExpression("ab.*c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertTrue(p.matches("abc"));
    assertTrue(p.matches("abxyc"));
    assertTrue(p.matches("aBxc"));
    assertTrue(p.matches("Abyc"));
    assertTrue(p.matches("abC"));
    assertTrue(p.matches("AbxYC"));
    assertFalse(p.matches("abx"));

    p.setExpression("ab.+c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertFalse(p.matches("abc"));
    assertTrue(p.matches("abxyc"));
    assertTrue(p.matches("aBxc"));
    assertTrue(p.matches("Abyc"));
    assertFalse(p.matches("abC"));
    assertTrue(p.matches("AbxYC"));
    assertFalse(p.matches("abx"));
  }

  private void testMatchesForGlobPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.GLOB);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertTrue(p.matches(""));
    assertFalse(p.matches("str"));

    p.setExpression("?");
    assertFalse(p.matches(""));
    assertTrue(p.matches("s"));
    assertTrue(p.matches("t"));
    assertFalse(p.matches("st"));

    p.setExpression("*");
    assertTrue(p.matches(""));
    assertTrue(p.matches("s"));
    assertTrue(p.matches("s"));
    assertTrue(p.matches("st"));

    p.setExpression("ab?c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertFalse(p.matches("abc"));
    assertFalse(p.matches("abxyc"));
    assertFalse(p.matches("abx"));

    p.setExpression("ab*c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertTrue(p.matches("abc"));
    assertTrue(p.matches("abxyc"));
    assertFalse(p.matches("aBxc"));
    assertFalse(p.matches("Abyc"));
    assertFalse(p.matches("abC"));
    assertFalse(p.matches("AbxYC"));
    assertFalse(p.matches("abx"));

    p.setIgnoreCase(true);
    p.setExpression("ab*c");
    assertFalse(p.matches(""));
    assertTrue(p.matches("abxc"));
    assertTrue(p.matches("abyc"));
    assertTrue(p.matches("abc"));
    assertTrue(p.matches("abxyc"));
    assertTrue(p.matches("aBxc"));
    assertTrue(p.matches("Abyc"));
    assertTrue(p.matches("abC"));
    assertTrue(p.matches("AbxYC"));
    assertFalse(p.matches("abx"));

  }

  @Test
  public void testXmlSerialization2() throws Exception {
    String xml = "<pattern type='PREFIX' ignore-case='true'>http://  </pattern>";
    Pattern pattern = new Pattern(PatternType.PREFIX, true, "http://");
    assertXmlSerializeEquals(Pattern.class, pattern, xml);
    assertXmlDeserializeEquals(Pattern.class, xml, pattern);

    xml = "<pattern type='PREFIX'>xxx</pattern>";
    pattern = new Pattern(PatternType.PREFIX, "xxx");
    assertXmlSerializeEquals(Pattern.class, pattern, xml);
    assertXmlDeserializeEquals(Pattern.class, xml, pattern);
  }

  @Test
  public void testBinarySerilization() throws IOException {
    Pattern p1 = null;
    Pattern p2 = null;
    byte[] data = null;

    //  test for null
    p1 = null;
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);
    try {
      p2 = BinarySerialization.deserialize(Pattern.class, data, false);
      fail("shoudl throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    //  test for empty
    p1 = new Pattern();
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    //  test for normal object
    p1.setIgnoreCase(true);
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    p1.setType(PatternType.REGEX);
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    p1.setExpression("expression");
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);

    p1.setType(Pattern.DEFAULT_TYPE);
    data = BinarySerialization.serialize(Pattern.class, p1);
    p2 = BinarySerialization.deserialize(Pattern.class, data, true);
    assertEquals(p1, p2);
  }

  /**
   * Test method for {@link Pattern#equals(Object)} and
   * {@link Pattern#hashCode()}.
   */
  @Test
  public void testEqualsHashCode() {
    final Pattern p1 = new Pattern();
    final Pattern p2 = new Pattern();

    assertEquals(p1, p2);
    assertNotEquals(null, p1);
    assertEquals(p1.hashCode(), p2.hashCode());

    p1.setIgnoreCase(false);
    p2.setIgnoreCase(true);
    assertNotEquals(p1, p2);
    assertNotEquals(p1.hashCode(), p2.hashCode());

    p1.setIgnoreCase(false);
    p2.setIgnoreCase(false);
    p1.setExpression("expression");
    p2.setExpression("expression");
    assertEquals(p1, p2);
    assertEquals(p1.hashCode(), p2.hashCode());

    p2.setExpression("expression2");
    assertNotEquals(p1, p2);
    assertNotEquals(p1.hashCode(), p2.hashCode());

    p2.setExpression("expression");
    p1.setType(PatternType.LITERAL);
    p2.setType(PatternType.LITERAL);
    assertEquals(p1, p2);
    assertEquals(p1.hashCode(), p2.hashCode());

    p2.setType(PatternType.REGEX);
    assertNotEquals(p1, p2);
    assertNotEquals(p1.hashCode(), p2.hashCode());
  }

  /**
   * Test method for {@link Pattern#toString()}.
   */
  @Test
  public void testToString() {
    final Pattern p = new Pattern();

    System.out.println(p);

    p.setIgnoreCase(true);
    System.out.println(p);

    p.setType(PatternType.REGEX);
    System.out.println(p);

    p.setExpression("expression");
    System.out.println(p);

    p.setType(Pattern.DEFAULT_TYPE);
    p.setIgnoreCase(Pattern.DEFAULT_IGNORE_CASE);
    System.out.println(p);
  }

  @Test
  public void testXmlMapperBug_9432() throws JsonProcessingException {
   final String xml = "<pattern type='PREFIX' ignore-case='true'>http://</pattern>";
   final Pattern pattern = new Pattern(PatternType.PREFIX, true, "http://");
   final XmlMapper mapper = new CustomizedXmlMapper();
   final String actual = mapper.writeValueAsString(pattern);
   System.out.println("Actual XML: " + actual);
   assertXmlEqual(xml, actual);
  }
}