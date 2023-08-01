////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.Pattern;
import ltd.qubit.commons.text.PatternType;
import ltd.qubit.commons.text.jackson.CustomizedXmlMapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlDeserializeEquals;
import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlSerializeEquals;
import static ltd.qubit.commons.text.jackson.XmlMapperUtils.parseListNoThrow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test of the {@link UrlPattern} class.
 *
 * @author Haixing Hu
 */
public class UrlPatternTest {

  /**
   * Test method for {@link UrlPattern#UrlPattern()}.
   */
  @Test
  public void testUrlPattern() {
    final UrlPattern urlPattern = new UrlPattern();
    assertNull(urlPattern.getPart());
    assertEquals(new Pattern(), urlPattern.getPattern());
  }

  /**
   * Test method for {@link UrlPattern#UrlPattern(UrlPart, Pattern)}.
   */
  @Test
  public void testUrlPatternUrlPartPattern() {

    final Pattern pattern = new Pattern(PatternType.GLOB, "www.*");
    UrlPattern urlPattern = new UrlPattern(UrlPart.HOSTNAME, pattern.clone());
    assertEquals(UrlPart.HOSTNAME, urlPattern.getPart());
    assertEquals(pattern, urlPattern.getPattern());

    urlPattern = new UrlPattern(null, pattern);
    assertNull(urlPattern.getPart());

    try {
      new UrlPattern(UrlPart.HOSTNAME, null);
      fail("Must throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }
  }

  /**
   * Test method for {@link UrlPattern#setPart(UrlPart)}
   * and {@link UrlPattern#getPart()}.
   */
  @Test
  public void testGetSetUrlPart() {
    final Pattern pattern = new Pattern(PatternType.GLOB, "www.*");
    final UrlPattern urlPattern = new UrlPattern(UrlPart.HOSTNAME, pattern.clone());

    assertEquals(UrlPart.HOSTNAME, urlPattern.getPart());

    urlPattern.setPart(UrlPart.URL);
    assertEquals(UrlPart.URL, urlPattern.getPart());

    urlPattern.setPart(UrlPart.DOMAIN);
    assertEquals(UrlPart.DOMAIN, urlPattern.getPart());

    urlPattern.setPart(null);
    assertNull(urlPattern.getPart());

  }

  /**
   * Test method for {@link UrlPattern#getPattern()} and
   * {@link UrlPattern#setPattern(Pattern)}.
   */
  @Test
  public void testGetSetPattern() {
    Pattern pattern = new Pattern(PatternType.GLOB, "www.*");
    final UrlPattern urlPattern = new UrlPattern(UrlPart.HOSTNAME, pattern.clone());

    assertEquals(pattern, urlPattern.getPattern());

    pattern = new Pattern(PatternType.SUFFIX, "com.cn");
    urlPattern.setPattern(pattern.clone());
    assertEquals(pattern, urlPattern.getPattern());

    try {
      urlPattern.setPattern(null);
      fail("Must throw NullPointerException");
    } catch (final NullPointerException e) {
      // pass
    }

  }

  /**
   * Test method for {@link UrlPattern#matches(Url)}.
   */
  @Test
  public void testMatches() throws MalformedURLException {
    final UrlPattern p = new UrlPattern();
    final Url sina = new Url("http://www.sina.com.cn/news/12345.html#top");
    final Url google = new Url("http://www.gooGLe.com/");
    final Url gmail = new Url("https://username:password@www.Gmail.com/read?id=1234");
    final Url yahooNews = new Url("http://NEWS.YAHOO.COM.cn/789012345.html#bottom");
    final Url yahooHome = new Url("http://www.YAHOO.COM.cn/");

    p.setPattern(new Pattern(sina.toString()));
    assertEquals(true, p.matches(sina));

    p.setPattern(new Pattern("http://"));
    assertEquals(false, p.matches(sina));

    p.setPart(UrlPart.HOSTNAME);
    p.getPattern().setType(PatternType.PREFIX);
    p.getPattern().setExpression("www");
    assertEquals(true, p.matches(sina));
    assertEquals(true, p.matches(google));
    assertEquals(true, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(true, p.matches(yahooHome));

    p.setPart(UrlPart.HOSTNAME);
    p.getPattern().setType(PatternType.SUFFIX);
    p.getPattern().setExpression("com.cn");
    assertEquals(true, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(true, p.matches(yahooNews));
    assertEquals(true, p.matches(yahooHome));

    p.setPart(UrlPart.URL);
    p.getPattern().setType(PatternType.LITERAL);
    p.getPattern().setExpression(sina.toString());
    assertEquals(true, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));

    p.setPart(UrlPart.DOMAIN);
    p.getPattern().setType(PatternType.LITERAL);
    p.getPattern().setExpression("yahoo.com.cn");
    assertEquals(false, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(true, p.matches(yahooNews));
    assertEquals(true, p.matches(yahooHome));

    p.setPart(UrlPart.USER_INFO);
    p.getPattern().setType(PatternType.GLOB);
    p.getPattern().setExpression("user*");
    assertEquals(false, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(true, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));

    p.setPart(UrlPart.QUERY);
    p.getPattern().setType(PatternType.REGEX);
    p.getPattern().setExpression("^[a-z]+=[0-9]+$");
    assertEquals(false, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(true, p.matches(gmail));
    assertEquals(false, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));

    p.setPart(UrlPart.FRAGMENT);
    p.getPattern().setType(PatternType.REGEX);
    p.getPattern().setExpression("[a-z]+");
    assertEquals(true, p.matches(sina));
    assertEquals(false, p.matches(google));
    assertEquals(false, p.matches(gmail));
    assertEquals(true, p.matches(yahooNews));
    assertEquals(false, p.matches(yahooHome));
  }

  @Test
  public void testXmlSerialization() throws Exception {
    final String xml = "<url-pattern>\n"
            + "        <part>HOSTNAME</part>\n"
            + "        <pattern type='GLOB'>s*.sina.com.cn</pattern>\n"
            + "    </url-pattern>";
    final UrlPattern pattern = new UrlPattern(UrlPart.HOSTNAME,
            new Pattern(PatternType.GLOB, "s*.sina.com.cn"));
    assertXmlSerializeEquals(UrlPattern.class, pattern, xml);
    assertXmlDeserializeEquals(UrlPattern.class, xml, pattern);
  }

  @Test
  public void testBinarySerilization() throws Exception {
    UrlPattern p1 = null;
    UrlPattern p2 = null;
    byte[] data = null;

    // test for null
    p1 = null;
    data = BinarySerialization.serialize(UrlPattern.class, p1);
    p2 = BinarySerialization.deserialize(UrlPattern.class, data, true);
    assertEquals(p1, p2);
    try {
      p2 = BinarySerialization.deserialize(UrlPattern.class, data, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    // test for empty object
    p1 = new UrlPattern();
    data = BinarySerialization.serialize(UrlPattern.class, p1);
    p2 = BinarySerialization.deserialize(UrlPattern.class, data, true);
    assertEquals(p1, p2);

    // test for normal objects
    final String XML_FILE = "url-patterns.xml";
    final URL url = SystemUtils.getResource(XML_FILE, getClass());
    final String xml = IoUtils.toString(url, StandardCharsets.UTF_8);
    final XmlMapper xmlMapper = new CustomizedXmlMapper();
    final List<UrlPattern> patterns = parseListNoThrow(xml, UrlPattern.class, xmlMapper);
    for (final UrlPattern pattern : patterns) {
      data = BinarySerialization.serialize(UrlPattern.class, pattern);
      p2 = BinarySerialization.deserialize(UrlPattern.class, data, true);
      assertEquals(pattern, p2);
    }
  }

  /**
   * Test method for {@link UrlPattern#clone()}.
   */
  @Test
  public void testClone() {
   // fail("Not yet implemented");
  }

  /**
   * Test method for {@link UrlPattern#equals(Object)}.
   */
  @Test
  public void testEqualsObject() {
    // fail("Not yet implemented");
  }

  /**
   * Test method for {@link UrlPattern#hashCode()}.
   */
  @Test
  public void testHashCode() {
   // fail("Not yet implemented");
  }

  /**
   * Test method for {@link UrlPattern#toString()}.
   */
  @Test
  public void testToString() {
   // fail("Not yet implemented");
  }

}
