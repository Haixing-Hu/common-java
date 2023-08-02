////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.IOException;

import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.lang.StringUtils;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlDeserializeEquals;
import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlSerializeEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

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
    assertEquals(true, pattern.getIgnoreCase());
    assertEquals("suffix", pattern.getExpression());

    pattern = new Pattern(PatternType.PREFIX, false, "prefix");
    assertEquals(PatternType.PREFIX, pattern.getType());
    assertEquals(false, pattern.getIgnoreCase());
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
    assertEquals(true, pattern.getIgnoreCase());

    pattern.setIgnoreCase(false);
    assertEquals(false, pattern.getIgnoreCase());
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
    assertEquals(true, p.matches(""));
    assertEquals(false, p.matches("str"));

    p.setExpression("hello");
    assertEquals(true, p.matches("hello"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(false, p.matches("Hello"));

    p.setIgnoreCase(true);
    assertEquals(true, p.matches("hello"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("Hello"));
  }

  private void testMatchesForPrefixPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.PREFIX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("str"));

    p.setExpression("hello");
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(false, p.matches("Hello"));
    assertEquals(false, p.matches("Hello123"));

    p.setIgnoreCase(true);
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("Hello"));
    assertEquals(true, p.matches("Hello123"));
  }

  private void testMatchesForSuffixPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.SUFFIX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("str"));

    p.setExpression("hello");
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("123hello"));
    assertEquals(false, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(false, p.matches("Hello"));
    assertEquals(false, p.matches("123Hello"));
    assertEquals(false, p.matches("Hello123"));

    p.setIgnoreCase(true);
    assertEquals(true, p.matches("hello"));
    assertEquals(true, p.matches("123hello"));
    assertEquals(false, p.matches("hello123"));
    assertEquals(false, p.matches("str"));
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("Hello"));
    assertEquals(true, p.matches("123Hello"));
    assertEquals(false, p.matches("Hello123"));
  }

  private void testMatchesForRegexPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.REGEX);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(false, p.matches("str"));

    p.setExpression(".");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("t"));
    assertEquals(false, p.matches("st"));

    p.setExpression(".*");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("st"));

    p.setExpression("ab.c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(false, p.matches("abxyc"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab.*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(false, p.matches("aBxc"));
    assertEquals(false, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(false, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab.+c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(false, p.matches("aBxc"));
    assertEquals(false, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(false, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setIgnoreCase(true);
    p.setExpression("ab.*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(true, p.matches("aBxc"));
    assertEquals(true, p.matches("Abyc"));
    assertEquals(true, p.matches("abC"));
    assertEquals(true, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab.+c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(true, p.matches("aBxc"));
    assertEquals(true, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(true, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));
  }

  private void testMatchesForGlobPattern() {
    final Pattern p = new Pattern();
    p.setType(PatternType.GLOB);

    p.setIgnoreCase(false);

    p.setExpression("");
    assertEquals(true, p.matches(""));
    assertEquals(false, p.matches("str"));

    p.setExpression("?");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("t"));
    assertEquals(false, p.matches("st"));

    p.setExpression("*");
    assertEquals(true, p.matches(""));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("s"));
    assertEquals(true, p.matches("st"));

    p.setExpression("ab?c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(false, p.matches("abc"));
    assertEquals(false, p.matches("abxyc"));
    assertEquals(false, p.matches("abx"));

    p.setExpression("ab*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(false, p.matches("aBxc"));
    assertEquals(false, p.matches("Abyc"));
    assertEquals(false, p.matches("abC"));
    assertEquals(false, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

    p.setIgnoreCase(true);
    p.setExpression("ab*c");
    assertEquals(false, p.matches(""));
    assertEquals(true, p.matches("abxc"));
    assertEquals(true, p.matches("abyc"));
    assertEquals(true, p.matches("abc"));
    assertEquals(true, p.matches("abxyc"));
    assertEquals(true, p.matches("aBxc"));
    assertEquals(true, p.matches("Abyc"));
    assertEquals(true, p.matches("abC"));
    assertEquals(true, p.matches("AbxYC"));
    assertEquals(false, p.matches("abx"));

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

    assertEquals(true, p1.equals(p1));
    assertEquals(true, p1.equals(p2));
    assertEquals(false, p1.equals(null));
    assertEquals(p1.hashCode(), p2.hashCode());

    p1.setIgnoreCase(false);
    p2.setIgnoreCase(true);
    assertEquals(false, p1.equals(p2));
    assertFalse(p1.hashCode() == p2.hashCode());

    p1.setIgnoreCase(false);
    p2.setIgnoreCase(false);
    p1.setExpression("expression");
    p2.setExpression("expression");
    assertEquals(true, p1.equals(p2));
    assertEquals(p1.hashCode(), p2.hashCode());

    p2.setExpression("expression2");
    assertEquals(false, p1.equals(p2));
    assertFalse(p1.hashCode() == p2.hashCode());

    p2.setExpression("expression");
    p1.setType(PatternType.LITERAL);
    p2.setType(PatternType.LITERAL);
    assertEquals(true, p1.equals(p2));
    assertEquals(p1.hashCode(), p2.hashCode());

    p2.setType(PatternType.REGEX);
    assertEquals(false, p1.equals(p2));
    assertFalse(p1.hashCode() == p2.hashCode());
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

}
