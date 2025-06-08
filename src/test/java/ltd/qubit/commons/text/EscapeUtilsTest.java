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
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static ltd.qubit.commons.text.EscapeUtils.ESCAPE_CSV;
import static ltd.qubit.commons.text.EscapeUtils.ESCAPE_ECMASCRIPT;
import static ltd.qubit.commons.text.EscapeUtils.ESCAPE_JAVA;
import static ltd.qubit.commons.text.EscapeUtils.ESCAPE_JSON;
import static ltd.qubit.commons.text.EscapeUtils.ESCAPE_XML10;
import static ltd.qubit.commons.text.EscapeUtils.UNESCAPE_CSV;
import static ltd.qubit.commons.text.EscapeUtils.UNESCAPE_JAVA;
import static ltd.qubit.commons.text.EscapeUtils.escapeCsv;
import static ltd.qubit.commons.text.EscapeUtils.escapeEcmaScript;
import static ltd.qubit.commons.text.EscapeUtils.escapeHtml3;
import static ltd.qubit.commons.text.EscapeUtils.escapeHtml4;
import static ltd.qubit.commons.text.EscapeUtils.escapeJava;
import static ltd.qubit.commons.text.EscapeUtils.escapeJson;
import static ltd.qubit.commons.text.EscapeUtils.escapeXSI;
import static ltd.qubit.commons.text.EscapeUtils.escapeXml10;
import static ltd.qubit.commons.text.EscapeUtils.escapeXml11;
import static ltd.qubit.commons.text.EscapeUtils.unescapeCsv;
import static ltd.qubit.commons.text.EscapeUtils.unescapeEcmaScript;
import static ltd.qubit.commons.text.EscapeUtils.unescapeHtml3;
import static ltd.qubit.commons.text.EscapeUtils.unescapeHtml4;
import static ltd.qubit.commons.text.EscapeUtils.unescapeJava;
import static ltd.qubit.commons.text.EscapeUtils.unescapeXSI;
import static ltd.qubit.commons.text.EscapeUtils.unescapeXml;

public class EscapeUtilsTest {
  private static final String FOO = "foo";

  private static final String[][] HTML_ESCAPES = {
      {"no escaping", "plain text", "plain text"},
      {"no escaping", "plain text", "plain text"},
      {"empty string", "", ""},
      {"null", null, null},
      {"ampersand", "bread &amp; butter", "bread & butter"},
      {"quotes", "&quot;bread&quot; &amp; butter", "\"bread\" & butter"},
      {"final character only", "greater than &gt;", "greater than >"},
      {"first character only", "&lt; less than", "< less than"},
      {"apostrophe", "Huntington's chorea", "Huntington's chorea"},
      {"languages", "English,Fran&ccedil;ais,\u65E5\u672C\u8A9E (nihongo)",
          "English,Fran\u00E7ais,\u65E5\u672C\u8A9E (nihongo)"},
      {"8-bit ascii shouldn't number-escape", "\u0080\u009F", "\u0080\u009F"},
  };

  private void assertEscapeJava(final String escaped, final String original)
      throws IOException {
    assertEscapeJava(escaped, original, null);
  }

  private void assertEscapeJava(final String expected, final String original,
      String message) throws IOException {
    final String converted = escapeJava(original);
    message = "escapeJava(String) failed" + (message == null ? "" : ": " + message);
    assertEquals(expected, converted, message);
    final StringWriter writer = new StringWriter();
    ESCAPE_JAVA.transform(original, writer);
    assertEquals(expected, writer.toString());
  }

  private void assertUnescapeJava(final String unescaped, final String original)
      throws IOException {
    assertUnescapeJava(unescaped, original, null);
  }

  private void assertUnescapeJava(final String unescaped, final String original,
      final String message) throws IOException {
    final String actual = unescapeJava(original);
    assertEquals(unescaped, actual, "unescape(String) failed"
        + (message == null ? "" : ": " + message)
        + ": expected '" + escapeJava(unescaped)
        // we escape this so we can see it in the error message
        + "' actual '" + escapeJava(actual) + "'");
    final StringWriter writer = new StringWriter();
    UNESCAPE_JAVA.transform(original, writer);
    assertEquals(unescaped, writer.toString());
  }

  private void checkCsvEscapeWriter(final String expected, final String value) {
    try {
      final StringWriter writer = new StringWriter();
      ESCAPE_CSV.transform(value, writer);
      assertEquals(expected, writer.toString());
    } catch (final IOException e) {
      fail("Threw: " + e);
    }
  }

  private void checkCsvUnescapeWriter(final String expected, final String value) {
    try {
      final StringWriter writer = new StringWriter();
      UNESCAPE_CSV.transform(value, writer);
      assertEquals(expected, writer.toString());
    } catch (final IOException e) {
      fail("Threw: " + e);
    }
  }

  @Test
  public void testBuilder() {
    final String result = EscapeUtils
        .builder(ESCAPE_XML10)
        .escape("<")
        .append(">")
        .toString();
    assertEquals("&lt;>", result);
  }

  // HTML and XML
  @Test
  public void testDeleteCharacter() {
    final String deleteString = "Delete: \u007F";
    assertEquals("Delete: \\u007F", escapeJson(deleteString));
  }

  @Test
  public void testEscapeCsvString() {
    assertEquals("foo.bar", escapeCsv("foo.bar"));
    assertEquals("\"foo,bar\"", escapeCsv("foo,bar"));
    assertEquals("\"foo\nbar\"", escapeCsv("foo\nbar"));
    assertEquals("\"foo\rbar\"", escapeCsv("foo\rbar"));
    assertEquals("\"foo\"\"bar\"", escapeCsv("foo\"bar"));
    assertEquals("foo\uD84C\uDFB4bar", escapeCsv("foo\uD84C\uDFB4bar"));
    assertEquals("", escapeCsv(""));
    assertNull(escapeCsv(null));
  }

  @Test
  public void testEscapeCsvWriter() {
    checkCsvEscapeWriter("foo.bar", "foo.bar");
    checkCsvEscapeWriter("\"foo,bar\"", "foo,bar");
    checkCsvEscapeWriter("\"foo\nbar\"", "foo\nbar");
    checkCsvEscapeWriter("\"foo\rbar\"", "foo\rbar");
    checkCsvEscapeWriter("\"foo\"\"bar\"", "foo\"bar");
    checkCsvEscapeWriter("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar");
    checkCsvEscapeWriter("", null);
    checkCsvEscapeWriter("", "");
  }

  @Test
  public void testEscapeEcmaScript() {
    assertNull(escapeEcmaScript(null));
    try {
      ESCAPE_ECMASCRIPT.transform(null, (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    try {
      ESCAPE_ECMASCRIPT.transform("", (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    assertEquals("He didn\\'t say, \\\"stop!\\\"",
        escapeEcmaScript("He didn't say, \"stop!\""));
    assertEquals("document.getElementById(\\\"test\\\").value = \\'<script>alert(\\'aaa\\');<\\/script>\\';",
        escapeEcmaScript(
            "document.getElementById(\"test\").value = '<script>alert('aaa');</script>';"));
  }

  @Test
  public void testEscapeHiragana() {
    // Some random Japanese Unicode characters
    final String original = "\u304B\u304C\u3068";
    final String escaped = escapeHtml4(original);
    assertEquals(original, escaped,
        "Hiragana character Unicode behavior should not be being escaped by escapeHtml4");

    final String unescaped = unescapeHtml4(escaped);

    assertEquals(escaped, unescaped,
        "Hiragana character Unicode behavior has changed - expected no unescaping");
  }

  @Test
  public void testEscapeHtml3() {
    for (final String[] element : HTML_ESCAPES) {
      final String message = element[0];
      final String expected = element[1];
      final String original = element[2];
      assertEquals(expected, escapeHtml4(original), message);
      final StringWriter sw = new StringWriter();
      try {
        EscapeUtils.ESCAPE_HTML3.transform(original, sw);
      } catch (final IOException e) {
        // expected
      }
      final String actual = original == null ? null : sw.toString();
      assertEquals(expected, actual, message);
    }
  }

  @Test
  public void testEscapeHtml4() {
    for (final String[] element : HTML_ESCAPES) {
      final String message = element[0];
      final String expected = element[1];
      final String original = element[2];
      assertEquals(expected, escapeHtml4(original), message);
      final StringWriter sw = new StringWriter();
      try {
        EscapeUtils.ESCAPE_HTML4.transform(original, sw);
      } catch (final IOException e) {
        // expected
      }
      final String actual = original == null ? null : sw.toString();
      assertEquals(expected, actual, message);
    }
  }

  /**
   * Tests // https://issues.apache.org/jira/browse/LANG-480
   */
  @Test
  public void testEscapeHtmlHighUnicode() {
    // this is the utf8 representation of the character:
    // COUNTING ROD UNIT DIGIT THREE
    // in Unicode
    // code point: U+1D362
    final byte[] data = {(byte) 0xF0, (byte) 0x9D, (byte) 0x8D, (byte) 0xA2};

    final String original = new String(data, StandardCharsets.UTF_8);

    final String escaped = escapeHtml4(original);
    assertEquals(original, escaped, "High Unicode should not have been escaped");

    final String unescaped = unescapeHtml4(escaped);
    assertEquals(original, unescaped, "High Unicode should have been unchanged");

    // TODO: I think this should hold, needs further investigation
    //  String unescapedFromEntity = StringEscapeUtils.unescapeHtml4("&#119650;");
    //  assertEquals("High Unicode should have been unescaped", original, unescapedFromEntity);
  }



  @Test
  public void testEscapeHtmlThree() {
    assertNull(escapeHtml3(null));
    assertEquals("a", escapeHtml3("a"));
    assertEquals("&lt;b&gt;a", escapeHtml3("<b>a"));
  }

  @Test
  public void testEscapeHtmlVersions() {
    assertEquals("&Beta;", escapeHtml4("\u0392"));
    assertEquals("\u0392", unescapeHtml4("&Beta;"));
    // TODO: refine API for escaping/unescaping specific HTML versions
  }

  @Test
  public void testEscapeJava() throws IOException {
    assertNull(escapeJava(null));
    try {
      ESCAPE_JAVA.transform(null, (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    try {
      ESCAPE_JAVA.transform("", (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    assertEscapeJava("", "", "empty string");
    assertEscapeJava(FOO, FOO);
    assertEscapeJava("\\t", "\t", "tab");
    assertEscapeJava("\\\\", "\\", "backslash");
    assertEscapeJava("'", "'", "single quote should not be escaped");
    assertEscapeJava("\\\\\\b\\t\\r", "\\\b\t\r");
    assertEscapeJava("\\u1234", "\u1234");
    assertEscapeJava("\\u0234", "\u0234");
    assertEscapeJava("\\u00EF", "\u00ef");
    assertEscapeJava("\\u0001", "\u0001");
    assertEscapeJava("\\uABCD", "\uabcd", "Should use capitalized Unicode hex");
    assertEscapeJava("He didn't say, \\\"stop!\\\"",
        "He didn't say, \"stop!\"");
    assertEscapeJava("This space is non-breaking:" + "\\u00A0", "This space is non-breaking:\u00a0",
        "non-breaking space");
    assertEscapeJava("\\uABCD\\u1234\\u012C",
        "\uABCD\u1234\u012C");
  }

  @Test
  public void testEscapeJavaWithSlash() {
    final String input = "String with a slash (/) in it";
    final String actual = escapeJava(input);
    /*
     * In 2.4 StringEscapeUtils.escapeJava(String) escapes '/' characters, which
     * are not a valid character to escape in a Java string.
     */
    assertEquals(input, actual);
  }

  @Test
  public void testEscapeJson() {
    assertNull(escapeJson(null));
    try {
      ESCAPE_JSON.transform(null, (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    try {
      ESCAPE_JSON.transform("", (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }

    assertEquals("He didn't say, \\\"stop!\\\"", escapeJson("He didn't say, \"stop!\""));

    final String expected = "\\\"foo\\\" isn't \\\"bar\\\". specials: \\b\\r\\n\\f\\t\\\\\\/";
    final String input = "\"foo\" isn't \"bar\". specials: \b\r\n\f\t\\/";

    assertEquals(expected, escapeJson(input));
  }

  @Test
  public void testEscapeXml10() {
    assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", escapeXml10("a<b>c\"d'e&f"));
    assertEquals("a\tb\rc\nd", escapeXml10("a\tb\rc\nd"),
        "XML 1.0 should not escape \t \n \r");
    assertEquals("ab", escapeXml10("a\u0000\u0001\u0008\u000b\u000c\u000e\u001fb"),
        "XML 1.0 should omit most #x0-x8 | #xb | #xc | #xe-#x19");
    assertEquals("a\ud7ff  \ue000b", escapeXml10("a\ud7ff\ud800 \udfff \ue000b"),
        "XML 1.0 should omit #xd800-#xdfff");
    assertEquals("a\ufffdb", escapeXml10("a\ufffd\ufffe\uffffb"),
        "XML 1.0 should omit #xfffe | #xffff");
    assertEquals("a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b",
        escapeXml10("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"),
        "XML 1.0 should escape #x7f-#x84 | #x86 - #x9f, for XML 1.1 compatibility");
  }

  @Test
  public void testEscapeXml11() {
    assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", escapeXml11("a<b>c\"d'e&f"));
    assertEquals("a\tb\rc\nd", escapeXml11("a\tb\rc\nd"),
        "XML 1.1 should not escape \t \n \r");
    assertEquals("ab", escapeXml11("a\u0000b"),
        "XML 1.1 should omit #x0");
    assertEquals("a&#1;&#8;&#11;&#12;&#14;&#31;b",
        escapeXml11("a\u0001\u0008\u000b\u000c\u000e\u001fb"),
        "XML 1.1 should escape #x1-x8 | #xb | #xc | #xe-#x19");
    assertEquals("a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b",
        escapeXml11("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"),
        "XML 1.1 should escape #x7F-#x84 | #x86-#x9F");
    assertEquals("a\ud7ff  \ue000b", escapeXml11("a\ud7ff\ud800 \udfff \ue000b"),
        "XML 1.1 should omit #xd800-#xdfff");
    assertEquals("a\ufffdb", escapeXml11("a\ufffd\ufffe\uffffb"),
        "XML 1.1 should omit #xfffe | #xffff");
  }

  @Test
  public void testEscapeXSI() {
    assertNull(null, escapeXSI(null));
    assertEquals("He\\ didn\\'t\\ say,\\ \\\"Stop!\\\"",
        escapeXSI("He didn't say, \"Stop!\""));
    assertEquals("\\\\", escapeXSI("\\"));
    assertEquals("", escapeXSI("\n"));
  }

  @Test
  public void testLang313() {
    assertEquals("& &", unescapeHtml4("& &amp;"));
  }

  /**
   * Tests https://issues.apache.org/jira/browse/LANG-708
   *
   * @throws IOException
   *             if an I/O error occurs
   */
  @Test
  public void testLang708() throws IOException {
    final String filename = "src/test/resources/ltd/qubit/commons/text/stringEscapeUtilsTestData.txt";
    final byte[] inputBytes = Files.readAllBytes(Paths.get(filename));
    final String input = new String(inputBytes, StandardCharsets.UTF_8);
    final String escaped = escapeEcmaScript(new Stripper().strip(input));
    // just the end:
    assertTrue(escaped.endsWith("}]"), escaped);
    // a little more:
    assertTrue(escaped.endsWith("\"valueCode\\\":\\\"\\\"}]"), escaped);
  }

  /**
   * Tests https://issues.apache.org/jira/browse/LANG-911
   */
  @Test
  public void testLang911() {
    final String bellsTest = "\ud83d\udc80\ud83d\udd14";
    final String escapedText = escapeJava(bellsTest);
    assertEquals("\\uD83D\\uDC80\\uD83D\\uDD14", escapedText);
    final String unescapedText = unescapeJava(escapedText);
    assertEquals(bellsTest, unescapedText);
  }

  // Tests issue #38569
  // https://issues.apache.org/bugzilla/show_bug.cgi?id=38569
  @Test
  public void testStandaloneAmphersand() {
    assertEquals("<P&O>", unescapeHtml4("&lt;P&O&gt;"));
    assertEquals("test & <", unescapeHtml4("test & &lt;"));
    assertEquals("<P&O>", unescapeXml("&lt;P&O&gt;"));
    assertEquals("test & <", unescapeXml("test & &lt;"));
  }

  @Test
  public void testUnescapeCsvString() {
    assertEquals("foo.bar", unescapeCsv("foo.bar"));
    assertEquals("foo,bar", unescapeCsv("\"foo,bar\""));
    assertEquals("foo\nbar", unescapeCsv("\"foo\nbar\""));
    assertEquals("foo\rbar", unescapeCsv("\"foo\rbar\""));
    assertEquals("foo\"bar", unescapeCsv("\"foo\"\"bar\""));
    assertEquals("foo\uD84C\uDFB4bar", unescapeCsv("foo\uD84C\uDFB4bar"));
    assertEquals("", unescapeCsv(""));
    assertNull(unescapeCsv(null));

    assertEquals("foo.bar", unescapeCsv("\"foo.bar\""));
  }

  @Test
  public void testUnescapeCsvWriter() {
    checkCsvUnescapeWriter("foo.bar", "foo.bar");
    checkCsvUnescapeWriter("foo,bar", "\"foo,bar\"");
    checkCsvUnescapeWriter("foo\nbar", "\"foo\nbar\"");
    checkCsvUnescapeWriter("foo\rbar", "\"foo\rbar\"");
    checkCsvUnescapeWriter("foo\"bar", "\"foo\"\"bar\"");
    checkCsvUnescapeWriter("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar");
    checkCsvUnescapeWriter("", null);
    checkCsvUnescapeWriter("", "");

    checkCsvUnescapeWriter("foo.bar", "\"foo.bar\"");
  }

  @Test
  public void testUnescapeEcmaScript() {
    assertNull(unescapeEcmaScript(null));
    assertEquals("8lvc1u+6B#-I", unescapeEcmaScript("8lvc1u+6B#-I"));
    assertEquals("<script src=\"build/main.bundle.js\"></script>",
        unescapeEcmaScript("<script src=\"build/main.bundle.js\"></script>"));
    assertEquals("<script src=\"build/main.bundle.js\"></script>>",
        unescapeEcmaScript("<script src=\"build/main.bundle.js\"></script>>"));
  }

  @Test
  public void testUnescapeHexCharsHtml() {
    // Simple easy to grok test
    assertEquals("\u0080\u009F", unescapeHtml4("&#x80;&#x9F;"), "hex number unescape");
    assertEquals("\u0080\u009F", unescapeHtml4("&#X80;&#X9F;"), "hex number unescape");
    // Test all Character values:
    for (char i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
      final char c2 = (char) (i + 1);
      final String expected = Character.toString(i) + c2;
      final String escapedC1 = "&#x" + Integer.toHexString(i) + ";";
      final String escapedC2 = "&#x" + Integer.toHexString(c2) + ";";
      assertEquals(expected, unescapeHtml4(escapedC1 + escapedC2),
          "hex number unescape index " + i);
    }
  }

  @Test
  public void testUnescapeHtml3() {
    for (final String[] element : HTML_ESCAPES) {
      final String message = element[0];
      final String expected = element[2];
      final String original = element[1];
      assertEquals(expected, unescapeHtml3(original), message);

      final StringWriter sw = new StringWriter();
      try {
        EscapeUtils.UNESCAPE_HTML3.transform(original, sw);
      } catch (final IOException e) {
        // expected
      }
      final String actual = original == null ? null : sw.toString();
      assertEquals(expected, actual, message);
    }
    // \u00E7 is a cedilla (c with wiggle under)
    // note that the test string must be 7-bit-clean (Unicode escaped) or else it will compile incorrectly
    // on some locales
    assertEquals("Fran\u00E7ais", unescapeHtml3("Fran\u00E7ais"),
        "funny chars pass through OK");

    assertEquals("Hello&;World", unescapeHtml3("Hello&;World"));
    assertEquals("Hello&#;World", unescapeHtml3("Hello&#;World"));
    assertEquals("Hello&# ;World", unescapeHtml3("Hello&# ;World"));
    assertEquals("Hello&##;World", unescapeHtml3("Hello&##;World"));
  }

  @Test
  public void testUnescapeHtml4() {
    for (final String[] element : HTML_ESCAPES) {
      final String message = element[0];
      final String expected = element[2];
      final String original = element[1];
      assertEquals(expected, unescapeHtml4(original), message);

      final StringWriter sw = new StringWriter();
      try {
        EscapeUtils.UNESCAPE_HTML4.transform(original, sw);
      } catch (final IOException e) {
        // expected
      }
      final String actual = original == null ? null : sw.toString();
      assertEquals(expected, actual, message);
    }
    // \u00E7 is a cedilla (c with wiggle under)
    // note that the test string must be 7-bit-clean (Unicode escaped) or else it will compile incorrectly
    // on some locales
    assertEquals("Fran\u00E7ais", unescapeHtml4("Fran\u00E7ais"),
        "funny chars pass through OK");

    assertEquals("Hello&;World", unescapeHtml4("Hello&;World"));
    assertEquals("Hello&#;World", unescapeHtml4("Hello&#;World"));
    assertEquals("Hello&# ;World", unescapeHtml4("Hello&# ;World"));
    assertEquals("Hello&##;World", unescapeHtml4("Hello&##;World"));
  }

  @Test
  public void testUnescapeJava() throws IOException {
    assertNull(unescapeJava(null));
    try {
      UNESCAPE_JAVA.transform(null, (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    try {
      UNESCAPE_JAVA.transform("", (Appendable) null);
      fail("Exception expected!");
    } catch (final IOException ex) {
      fail("Exception expected!");
    } catch (final NullPointerException ex) {
      // expected
    }
    assertThrows(RuntimeException.class, () -> unescapeJava("\\u02-3"));

    assertUnescapeJava("", "");
    assertUnescapeJava("test", "test");
    assertUnescapeJava("\ntest\b", "\\ntest\\b");
    assertUnescapeJava("\u123425foo\ntest\b", "\\u123425foo\\ntest\\b");
    assertUnescapeJava("'\foo\teste\r", "\\'\\foo\\teste\\r");
    assertUnescapeJava("", "\\");
    //foo
    assertUnescapeJava("\uABCDx", "\\uabcdx", "lowercase Unicode");
    assertUnescapeJava("\uABCDx", "\\uABCDx", "uppercase Unicode");
    assertUnescapeJava("\uABCD", "\\uabcd", "Unicode as final character");
  }

  @Test
  public void testUnescapeJson() {
    final String jsonString =
        "{\"age\":100,\"name\":\"kyong.com\n\",\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"]}";

    assertEquals("", EscapeUtils.unescapeJson(""));
    assertEquals(" ", EscapeUtils.unescapeJson(" "));
    assertEquals("a:b", EscapeUtils.unescapeJson("a:b"));
    assertEquals(jsonString, EscapeUtils.unescapeJson(jsonString));
  }

  @Test // TEXT-120
  public void testUnescapeJsonDoubleQuoteAndForwardSlash() {
    final String escapedJsonString = "double quote: \\\" and a forward slash: \\/";
    final String jsonString = "double quote: \" and a forward slash: /";

    assertEquals(jsonString, EscapeUtils.unescapeJson(escapedJsonString));
  }

  @Test
  public void testUnescapeUnknownEntity() {
    assertEquals("&zzzz;", unescapeHtml4("&zzzz;"));
  }

  /**
   * Reverse of the above.
   *
   * @see <a href="https://issues.apache.org/jira/browse/LANG-729">LANG-729</a>
   */
  @Test
  public void testUnescapeXmlSupplementaryCharacters() {
    assertEquals("\uD84C\uDFB4", unescapeXml("&#144308;"),
        "Supplementary character must be represented using a single escape");
    assertEquals("a b c \uD84C\uDFB4", unescapeXml("a b c &#144308;"),
        "Supplementary characters mixed with basic characters should be decoded correctly");
  }

  @Test
  public void testUnscapeXSI() {
    assertNull(null, unescapeXSI(null));
    assertEquals("\"", unescapeXSI("\\\""));
    assertEquals("He didn't say, \"Stop!\"",
        unescapeXSI("He\\ didn\\'t\\ say,\\ \\\"Stop!\\\""));
    assertEquals("\\", unescapeXSI("\\\\"));
    assertEquals("", unescapeXSI("\\"));
  }
}