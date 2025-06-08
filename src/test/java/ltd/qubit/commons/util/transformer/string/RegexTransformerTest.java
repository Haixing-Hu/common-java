////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlDeserializeEquals;
import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlSerializeEquals;

/**
 * Unit test of the {@link RegexTransformer} class.
 *
 * @author Haixing Hu
 */
public class RegexTransformerTest {

  @Test
  public void testConstructor() {
    final RegexTransformer transformer1 = new RegexTransformer();
    assertEquals("", transformer1.getInputPattern());
    assertEquals("", transformer1.getOutputPattern());
    final RegexTransformer transformer2 = new RegexTransformer("([0-9]+)",
        "abc${1}cdf");
    assertEquals("([0-9]+)", transformer2.getInputPattern());
    assertEquals("abc${1}cdf", transformer2.getOutputPattern());
  }

  @Test
  public void testSetGetInputPattern() {
    final RegexTransformer transformer = new RegexTransformer();
    transformer.setInputPattern("([0-9]+)");
    assertEquals("([0-9]+)", transformer.getInputPattern());
  }

  @Test
  public void testSetGetOutputPattern() {
    final RegexTransformer transformer = new RegexTransformer();
    transformer.setOutputPattern("abc${1}cdf");
    assertEquals("abc${1}cdf", transformer.getOutputPattern());
  }

  @Test
  public void testTransform() {
    final RegexTransformer tr = new RegexTransformer();

    tr.setInputPattern("");
    tr.setOutputPattern("");
    assertEquals("hello 123 world 456", tr.transform("hello 123 world 456"));

    tr.setInputPattern("");
    tr.setOutputPattern("abc${1}cdf");
    assertEquals("hello 123 world 456", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("");
    assertEquals("hello  world ", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("");
    assertEquals("hello  world ", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("abc");
    assertEquals("hello abc world abc", tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("a${1}bc");
    assertEquals("hello a123bc world a456bc",
        tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)");
    tr.setOutputPattern("a${1}bc${1}");
    assertEquals("hello a123bc123 world a456bc456",
        tr.transform("hello 123 world 456"));

    tr.setInputPattern("([0-9]+)-([0-9]+)-([0-9]+)");
    tr.setOutputPattern(
        "[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]");
    assertEquals("date1: [year: 2011, month: 01, day: 02, day again: 02], "
        + "date2: [year: 2012, month: 12, day: 1, day again: 1]",
        tr.transform("date1: 2011-01-02, date2: 2012-12-1"));

    tr.setInputPattern("([0-9]+)-([0-9]+)-([0-9]+)");
    tr.setOutputPattern("${1}${2}${3}${3}");
    assertEquals("date1: 2011010202, date2: 20121211",
        tr.transform("date1: 2011-01-02, date2: 2012-12-1"));

  }

  @Test
  public void testXmlSerialization() throws Exception {

    String xml = "<regex-transformer>"
        + "  <input-pattern></input-pattern>"
        + "  <output-pattern></output-pattern>"
        + "</regex-transformer>";
    RegexTransformer transformer = new RegexTransformer();
    assertXmlSerializeEquals(RegexTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(RegexTransformer.class, xml, transformer);

    xml = "<regex-transformer>"
        + "  <input-pattern> ([0-9]+) </input-pattern>"
        + "  <output-pattern></output-pattern>"
        + "</regex-transformer>";
    transformer = new RegexTransformer("([0-9]+)", "");
    assertXmlSerializeEquals(RegexTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(RegexTransformer.class, xml, transformer);

    xml = "<regex-transformer>"
        + "  <input-pattern>  </input-pattern>"
        + "  <output-pattern>a${1}bc  </output-pattern>"
        + "</regex-transformer>";
    transformer = new RegexTransformer("", "a${1}bc");
    assertXmlSerializeEquals(RegexTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(RegexTransformer.class, xml, transformer);

    xml = "<regex-transformer>"
        + "  <input-pattern> ([0-9]+)-([0-9]+)-([0-9]+) </input-pattern>"
        + "  <output-pattern> [year: ${1}, month: ${2}, day: ${3}, "
        + "day again: ${3}]  </output-pattern>"
        + "</regex-transformer>";
    transformer = new RegexTransformer("([0-9]+)-([0-9]+)-([0-9]+)",
        "[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]");
    assertXmlSerializeEquals(RegexTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(RegexTransformer.class, xml, transformer);
  }
}