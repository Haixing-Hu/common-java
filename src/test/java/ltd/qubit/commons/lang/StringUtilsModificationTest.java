////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static ltd.qubit.commons.lang.StringUtils.chomp;
import static ltd.qubit.commons.lang.StringUtils.chop;
import static ltd.qubit.commons.lang.StringUtils.normalizeSpace;
import static ltd.qubit.commons.lang.StringUtils.removeChar;
import static ltd.qubit.commons.lang.StringUtils.removePrefix;
import static ltd.qubit.commons.lang.StringUtils.removePrefixAndSuffix;
import static ltd.qubit.commons.lang.StringUtils.removeSubstring;
import static ltd.qubit.commons.lang.StringUtils.removeSuffix;
import static ltd.qubit.commons.lang.StringUtils.strip;
import static ltd.qubit.commons.lang.StringUtils.stripEnd;
import static ltd.qubit.commons.lang.StringUtils.stripStart;

/**
 * Unit test for the modification functions in the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsModificationTest extends StringUtilsTestBase {

  @Test
  public void testStrip_String() {
    // null strip
    assertNull(strip(null));
    assertEquals("", strip(""));
    assertEquals("", strip("        "));
    assertEquals("abc", strip("  abc  "));
    assertEquals(NON_BLANK, strip(BLANK + NON_BLANK + BLANK));

    // "" strip
    assertNull(strip(null, " "));
    assertEquals("", strip("", ""));
    assertEquals("        ", strip("        ", ""));
    assertEquals("  abc  ", strip("  abc  ", ""));
    assertEquals(BLANK, strip(BLANK, ""));

    // " " strip
    assertNull(strip(null, " "));
    assertEquals("", strip("", " "));
    assertEquals("", strip("        ", " "));
    assertEquals("abc", strip("  abc  ", " "));

    // "ab" strip
    assertNull(strip(null, "ab"));
    assertEquals("", strip("", "ab"));
    assertEquals("        ", strip("        ", "ab"));
    assertEquals("  abc  ", strip("  abc  ", "ab"));
    assertEquals("c", strip("abcabab", "ab"));
    assertEquals(BLANK, strip(BLANK, ""));
  }

  @Test
  public void testStripStart_String_String() {
    // null stripStart
    assertNull(stripStart(null));
    assertEquals("", stripStart(""));
    assertEquals("", stripStart("        "));
    assertEquals("abc  ", stripStart("  abc  "));
    assertEquals(NON_BLANK + BLANK,
        stripStart(BLANK + NON_BLANK + BLANK));

    // "" stripStart
    assertNull(stripStart(null, ""));
    assertEquals("", stripStart("", ""));
    assertEquals("        ", stripStart("        ", ""));
    assertEquals("  abc  ", stripStart("  abc  ", ""));
    assertEquals(BLANK, stripStart(BLANK, ""));

    // " " stripStart
    assertNull(stripStart(null, " "));
    assertEquals("", stripStart("", " "));
    assertEquals("", stripStart("        ", " "));
    assertEquals("abc  ", stripStart("  abc  ", " "));

    // "ab" stripStart
    assertNull(stripStart(null, "ab"));
    assertEquals("", stripStart("", "ab"));
    assertEquals("        ", stripStart("        ", "ab"));
    assertEquals("  abc  ", stripStart("  abc  ", "ab"));
    assertEquals("cabab", stripStart("abcabab", "ab"));
    assertEquals(BLANK, stripStart(BLANK, ""));
  }

  @Test
  public void testStripEnd_String_String() {
    // null stripEnd
    assertNull(stripEnd(null));
    assertEquals("", stripEnd(""));
    assertEquals("", stripEnd("        "));
    assertEquals("  abc", stripEnd("  abc  "));
    assertEquals(BLANK + NON_BLANK, stripEnd(BLANK + NON_BLANK + BLANK));

    // "" stripEnd
    assertNull(stripEnd(null, ""));
    assertEquals("", stripEnd("", ""));
    assertEquals("        ", stripEnd("        ", ""));
    assertEquals("  abc  ", stripEnd("  abc  ", ""));
    assertEquals(BLANK, stripEnd(BLANK, ""));

    // " " stripEnd
    assertNull(stripEnd(null, " "));
    assertEquals("", stripEnd("", " "));
    assertEquals("", stripEnd("        ", " "));
    assertEquals("  abc", stripEnd("  abc  ", " "));

    // "ab" stripEnd
    assertNull(stripEnd(null, "ab"));
    assertEquals("", stripEnd("", "ab"));
    assertEquals("        ", stripEnd("        ", "ab"));
    assertEquals("  abc  ", stripEnd("  abc  ", "ab"));
    assertEquals("abc", stripEnd("abcabab", "ab"));
    assertEquals(BLANK, stripEnd(BLANK, ""));
  }

  @Test
  public void testChop() {
    final String[][] chopCases = {
        {"foobar" + "\r\n", "foobar"},
        {"foobar" + "\n", "foobar"},
        {"foobar" + "\r", "foobar"},
        {"foobar" + " \r", "foobar" + " "},
        {"foo", "fo"},
        {"foo\nfoo", "foo\nfo"},
        {"\n", ""},
        {"\r", ""},
        {"\r\n", ""},
        {null, null},
        {"", ""},
        {"a", ""},
    };
    for (final String[] chopCase : chopCases) {
      final String original = chopCase[0];
      final String expectedResult = chopCase[1];
      assertEquals(expectedResult, chop(original), "chop(String) failed");
    }
  }

  @Test
  public void testChomp() {

    final String[][] chompCases = {
        {"foobar" + "\r\n", "foobar"},
        {"foobar" + "\n", "foobar"},
        {"foobar" + "\r", "foobar"},
        {"foobar" + " \r", "foobar" + " "},
        {"foobar", "foobar"},
        {"foobar" + "\n\n", "foobar" + "\n"},
        {"foobar" + "\r\n\r\n", "foobar" + "\r\n"},
        {"foo\nfoo", "foo\nfoo"},
        {"foo\n\rfoo", "foo\n\rfoo"},
        {"\n", ""},
        {"\r", ""},
        {"a", "a"},
        {"\r\n", ""},
        {"", ""},
        {null, null},
        {"foobar" + "\n\r", "foobar" + "\n"}
    };
    for (final String[] chompCase : chompCases) {
      final String original = chompCase[0];
      final String expectedResult = chompCase[1];
      assertEquals(expectedResult, chomp(original), "chomp(String) failed");
    }
    assertEquals("foo", chomp("foobar", "bar"), "chomp(String, String) failed");
    assertEquals("foobar", chomp("foobar", "baz"), "chomp(String, String) failed");
    assertEquals("foo", chomp("foo", "foooo"), "chomp(String, String) failed");
    assertEquals("foobar", chomp("foobar", ""), "chomp(String, String) failed");
    assertEquals("foobar", chomp("foobar", (String) null), "chomp(String, String) failed");
    assertEquals("", chomp("", "foo"), "chomp(String, String) failed");
    assertEquals("", chomp("", (String) null), "chomp(String, String) failed");
    assertEquals("", chomp("", ""), "chomp(String, String) failed");
    assertEquals(null, chomp(null, "foo"), "chomp(String, String) failed");
    assertEquals(null, chomp(null, (String) null), "chomp(String, String) failed");
    assertEquals(null, chomp(null, ""), "chomp(String, String) failed");
    assertEquals("", chomp("foo", "foo"), "chomp(String, String) failed");
    assertEquals(" ", chomp(" foo", "foo"), "chomp(String, String) failed");
    assertEquals("foo ", chomp("foo ", "foo"), "chomp(String, String) failed");
  }

  @Test
  public void testChopNewLine() {

    final String[][] newLineCases = {
        {"foobar" + "\r\n", "foobar"},
        {"foobar" + "\n", "foobar"},
        {"foobar" + "\r", "foobar"},
        {"foobar", "foobar"},
        {"foobar" + "\n" + "foobar", "foobar" + "\n" + "foobar"},
        {"foobar" + "\n\n", "foobar" + "\n"},
        {"\n", ""},
        {"", ""},
        {"\r\n", ""}
    };

    for (final String[] newLineCase : newLineCases) {
      final String original = newLineCase[0];
      final String expectedResult = newLineCase[1];
      assertEquals(
          expectedResult, chomp(original), "chomp(String) failed");
    }
  }

  @Test
  public void testNormalizeSpace() {
    String str = " hello \n\r\n\r\r     world  \u000B  !!   ";
    String expected = "hello\nworld !!";
    assertEquals(expected, normalizeSpace(str, false));

    expected = "hello world !!";
    assertEquals(expected, normalizeSpace(str, true));

    str = "\n\n\n\n\r\r\r\r\r\r  abcd\u00A0e\r\u00A0\u00A0\u00A0\u00A0 \nfgh\n  ";
    expected = "abcd e\nfgh";
    assertEquals(expected, normalizeSpace(str, false));

    expected = "abcd e fgh";
    assertEquals(expected, normalizeSpace(str, true));

    str = "2011年02月07日\u00A009:26";
    expected = "2011年02月07日 09:26";
    assertEquals(expected, normalizeSpace(str, true));
  }

  @Test
  public void testRemovePrefix() {
    // Strings.removeStart("", *)        = ""
    assertNull(removePrefix(null, null, false));
    assertNull(removePrefix(null, "", false));
    assertNull(removePrefix(null, "a", false));

    // Strings.removeStart(*, null)      = *
    assertEquals("", removePrefix("", null, false));
    assertEquals("", removePrefix("", "", false));
    assertEquals("", removePrefix("", "a", false));

    // All others:
    assertEquals("domain.com", removePrefix("www.domain.com", "www.", false));
    assertEquals("wwW.domain.com", removePrefix("wwW.domain.com", "www.", false));
    assertEquals("domain.com", removePrefix("domain.com", "www.", false));
    assertEquals("domain.com", removePrefix("domain.com", "", false));
    assertEquals("domain.com", removePrefix("domain.com", null, false));
    assertEquals("", removePrefix("domain.com", "domain.com", false));
    assertEquals("domain.com", removePrefix("domain.com", "DOMAIN.COM", false));

    // Strings.removeStart("", *)        = ""
    assertNull(removePrefix(null, null, true));
    assertNull(removePrefix(null, "", true));
    assertNull(removePrefix(null, "a", true));

    // Strings.removeStart(*, null)      = *
    assertEquals("", removePrefix("", null, true));
    assertEquals("", removePrefix("", "", true));
    assertEquals("", removePrefix("", "a", true));

    // All others:
    assertEquals("domain.com", removePrefix("www.domain.com", "www.", true));
    assertEquals("domain.com", removePrefix("wwW.domain.com", "www.", true));
    assertEquals("domain.com", removePrefix("domain.com", "", true));
    assertEquals("", removePrefix("domain.com", "domain.com", true));
    assertEquals("", removePrefix("domain.com", "DOMAIN.COM", true));
  }

  @Test
  public void testRemoveSuffix() {
    // Strings.removeEnd("", *)        = ""
    assertNull(removeSuffix(null, null, false));
    assertNull(removeSuffix(null, "", false));
    assertNull(removeSuffix(null, "a", false));

    // Strings.removeEnd(*, null)      = *
    assertEquals("", removeSuffix("", null, false));
    assertEquals("", removeSuffix("", "", false));
    assertEquals("", removeSuffix("", "a", false));

    // All others:
    assertEquals("www.domain.com.", removeSuffix("www.domain.com.", ".com", false));
    assertEquals("www.domain", removeSuffix("www.domain.com", ".com", false));
    assertEquals("www.domain.Com", removeSuffix("www.domain.Com", ".com", false));
    assertEquals("www.domain", removeSuffix("www.domain", ".com", false));
    assertEquals("domain.com", removeSuffix("domain.com", "", false));
    assertEquals("domain.com", removeSuffix("domain.com", null, false));
    assertEquals("", removeSuffix("domain.com", "domain.com", false));
    assertEquals("domain.com", removeSuffix("domain.com", "domain.Com", false));

    // Strings.removeEndIgnoreCase("", *)        = ""
    assertNull(removeSuffix(null, null, true));
    assertNull(removeSuffix(null, "", true));
    assertNull(removeSuffix(null, "a", true));

    // Strings.removeEnd(*, null)      = *
    assertEquals("", removeSuffix("", null, true));
    assertEquals("", removeSuffix("", "", true));
    assertEquals("", removeSuffix("", "a", true));

    // All others:
    assertEquals("www.domain.com.", removeSuffix("www.domain.com.", ".com", true));
    assertEquals("www.domain", removeSuffix("www.domain.com", ".com", true));
    assertEquals("www.domain", removeSuffix("www.domain.Com", ".com", true));
    assertEquals("www.domain", removeSuffix("www.domain", ".com", true));
    assertEquals("domain.com", removeSuffix("domain.com", "", true));
    assertEquals("domain.com", removeSuffix("domain.com", null, true));
    assertEquals("", removeSuffix("domain.com", "domain.com", true));
    assertEquals("", removeSuffix("domain.com", "domain.Com", true));
  }

  @Test
  public void testRemove_String() {
    // Strings.removeSubstring(null, *, *, *)        = null
    assertNull(removeSubstring(null, null, -1, false));
    assertNull(removeSubstring(null, "", -1, false));
    assertNull(removeSubstring(null, "a", -1, false));

    // Strings.removeSubstring("", *, *, *)          = ""
    assertEquals("", removeSubstring("", null, -1, false));
    assertEquals("", removeSubstring("", "", -1, false));
    assertEquals("", removeSubstring("", "a", -1, false));

    // Strings.remove(str, null, *, *)        = str
    assertNull(removeSubstring(null, null, -1, false));
    assertEquals("", removeSubstring("", null, -1, false));
    assertEquals("a", removeSubstring("a", null, -1, false));

    // Strings.remove(str, "", *, *)          = str
    assertNull(removeSubstring(null, "", -1, false));
    assertEquals("", removeSubstring("", "", -1, false));
    assertEquals("a", removeSubstring("a", "", -1, false));

    assertEquals("queued", removeSubstring("queued", "Ue", -1, false));
    assertEquals("qd", removeSubstring("queued", "Ue", -1, true));
    assertEquals("queued", removeSubstring("queued", "Ue", 1, false));
    assertEquals("qued", removeSubstring("queued", "Ue", 1, true));
    assertEquals("queued", removeSubstring("queued", "Ue", 0, false));
    assertEquals("queued", removeSubstring("queued", "Ue", 0, true));

    // Strings.remove("queued", "zz") = "queued"
    assertEquals("queued", removeSubstring("queued", "zz", -1, false));
    assertEquals("queued", removeSubstring("queued", "zz", -1, true));
  }

  @Test
  public void testRemoveChar() {
    assertNull(removeChar(null, 'a', -1));
    assertEquals("", removeChar("", 'a', -1));
    assertEquals("qeed", removeChar("queued", 'u', -1));
    assertEquals("qeued", removeChar("queued", 'u', 1));
    assertEquals("queued", removeChar("queued", 'u', 0));
    assertEquals("queued", removeChar("queued", 'z', -1));
  }

  @Test
  public void testRemovePrefixAndSuffix() {
    // Strings.removeStart("", *)        = ""
    assertNull(removePrefixAndSuffix(null, null, null, false));
    assertNull(removePrefixAndSuffix(null, "", "", false));
    assertNull(removePrefixAndSuffix(null, "a", "b", false));

    // Strings.removeStart(*, null)      = *
    assertEquals("", removePrefixAndSuffix("", null, null, false));
    assertEquals("", removePrefixAndSuffix("", "", "", false));
    assertEquals("", removePrefixAndSuffix("", "a", "b", false));

    // All others:
    assertEquals("domain", removePrefixAndSuffix("www.domain.com", "www.", ".com", false));
    assertEquals("wwW.domain", removePrefixAndSuffix("wwW.domain.com", "www.", ".com", false));
    assertEquals("domainxcom", removePrefixAndSuffix("domainxcom", "www.", ".com", false));
    assertEquals("domain", removePrefixAndSuffix("domain.com", "", ".com", false));
    assertEquals("domain", removePrefixAndSuffix("domain.com", null, ".com", false));
    assertEquals("", removePrefixAndSuffix("domain.com", "domain", ".com", false));
    assertEquals("domain.com", removePrefixAndSuffix("domain.com", "DOMAIN", ".COM", false));

    // Strings.removeStart("", *)        = ""
    assertNull(removePrefixAndSuffix(null, null, null, true));
    assertNull(removePrefixAndSuffix(null, "", "", true));
    assertNull(removePrefixAndSuffix(null, "a", "b", true));

    // Strings.removeStart(*, null)      = *
    assertEquals("", removePrefixAndSuffix("", null, null, true));
    assertEquals("", removePrefixAndSuffix("", "", "", true));
    assertEquals("", removePrefixAndSuffix("", "a", "b", true));

    // All others:
    assertEquals("domain", removePrefixAndSuffix("www.domain.com", "www.", ".com", true));
    assertEquals("domain.", removePrefixAndSuffix("wwW.domain.com", "www.", "Com", true));
    assertEquals("domain", removePrefixAndSuffix("domain.com", "", ".coM", true));
    assertEquals("", removePrefixAndSuffix("domain.com", "domain.com", "com", true));
    assertEquals("m", removePrefixAndSuffix("domain.com", "DOMAIN.CO", "COM", true));
    assertEquals("", removePrefixAndSuffix("domain.com", "DX", "domain.COM", true));
    assertEquals("domain", removePrefixAndSuffix("domain.com", "DX", ".COM", true));
    assertEquals("domain.com", removePrefixAndSuffix("domain.com", "DX", "COMX", true));

    assertEquals("hello", removePrefixAndSuffix("<tag>hello</tag>", "<tag>", "</tag>", true));
  }
}