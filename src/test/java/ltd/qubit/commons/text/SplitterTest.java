////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.util.filter.codepoint.UpperCaseCodePointFilter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplitterTest {
  protected static final String WHITESPACE;
  protected static final String NON_WHITESPACE;
  protected static final String BLANK;
  protected static final String NON_BLANK;

  static {
    final StringBuilder ws = new StringBuilder();
    final StringBuilder nws = new StringBuilder();
    final StringBuilder bk = new StringBuilder();
    final StringBuilder nbk = new StringBuilder();
    for (int i = 0; i < Character.MAX_VALUE; i++) {
      final char ch = (char) i;
      if (Character.isWhitespace(ch)) {
        ws.append(ch);
      } else if (i <= Ascii.MAX) {
        nws.append(ch);
      }
      if (CharUtils.isBlank(ch)) {
        bk.append(ch);
        // if the last 2 chars compose a non-blank surrogate pair character,
        // remove the last 2 chars from the builder
        final int n = bk.length();
        if (n >= 2) {
          final char high = bk.charAt(n - 2);
          if (Utf16.isSurrogatePair(high, ch)) {
            final int cp = Utf16.compose(high, ch);
            if (! CharUtils.isBlank(cp)) {
              bk.setLength(n - 2);
            }
          }
        }
      } else if (i <= Ascii.MAX) {
        nbk.append(ch);
      }
    }
    WHITESPACE = ws.toString();
    NON_WHITESPACE = nws.toString();
    BLANK = bk.toString();
    NON_BLANK = nbk.toString();
  }

  @Test
  public void testSplitByChar() {
    assertEquals(Collections.emptyList(),
        new Splitter().byChar(',').split(null));
    assertEquals(List.of(""),
        new Splitter().byChar(',').split(""));
    assertEquals(Collections.emptyList(),
        new Splitter().byChar(',').ignoreEmpty(true).split(""));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byChar('.').split("a.b.c"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byChar('.').split("a..b.c"));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byChar('.').ignoreEmpty(true).split("a..b.c"));
    assertEquals(List.of("a", " ", "b", "c"),
        new Splitter().byChar('.').split("a. .b.c"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byChar('.').strip(true).split("a. .b.c"));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byChar('.').strip(true).ignoreEmpty(true).split("a. .b.c"));
    assertEquals(List.of("", "a", " ", "b", "c", ""),
        new Splitter().byChar('.').split(".a. .b.c."));
    assertEquals(List.of("", "a", " ", "b", "c", " "),
        new Splitter().byChar('.').split(".a. .b.c. "));
    assertEquals(List.of("", "a", "", "b", "c", ""),
        new Splitter().byChar('.').strip(true).split(".a. .b.c. "));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byChar('.').strip(true).ignoreEmpty(true).split(".a. .b.c. "));
    assertEquals(List.of("", "a", "", "", "b", "c", ""),
        new Splitter().byChar(' ').strip(true).split(" a   b c "));
  }

  @Test
  public void testSplitByCharsInArray() {
    final char[] separators = new char[] {',', '.', ':'};

    assertEquals(Collections.emptyList(),
        new Splitter().byCharsIn(separators).split(null));
    assertEquals(List.of(""),
        new Splitter().byCharsIn(separators).split(""));
    assertEquals(Collections.emptyList(),
        new Splitter().byCharsIn(separators).ignoreEmpty(true).split(""));
    assertEquals(List.of("a", "b", "c", "d"),
        new Splitter().byCharsIn(separators).split("a,b.c:d"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byCharsIn(separators).split("a.,b:c"));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byCharsIn(separators).ignoreEmpty(true).split("a,.b:c"));
    assertEquals(List.of("a", " ", "b", "c"),
        new Splitter().byCharsIn(separators).split("a. :b,c"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byCharsIn(separators).strip(true).split("a: .b,c"));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byCharsIn(separators).strip(true).ignoreEmpty(true).split("a. :b,c"));
    assertEquals(List.of("", "a", " ", "b", "c", ""),
        new Splitter().byCharsIn(separators).split(",a. .b:c:"));
    assertEquals(List.of("", "a", " ", "b", "c", " "),
        new Splitter().byCharsIn(separators).split(".a. ,b:c. "));
    assertEquals(List.of("", "a", "", "b", "c", ""),
        new Splitter().byCharsIn(separators).strip(true).split(":a: ,b.c, "));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byCharsIn(separators).strip(true).ignoreEmpty(true).split(":a: ,b.c, "));
    assertEquals(List.of(":a: ,b.c,"),
        new Splitter().byCharsIn(new char[0]).strip(true).ignoreEmpty(true).split(":a: ,b.c, "));

    assertEquals(List.of(":a: ,b.c,"),
       new Splitter().byCharsIn((char[]) null).split(":a: ,b.c,"));
  }

  @Test
  public void testSplitByCharsInString() {
    final String separators = ",.:";

    assertEquals(Collections.emptyList(),
        new Splitter().byCharsIn(separators).split(null));
    assertEquals(List.of(""),
        new Splitter().byCharsIn(separators).split(""));
    assertEquals(Collections.emptyList(),
        new Splitter().byCharsIn(separators).ignoreEmpty(true).split(""));
    assertEquals(List.of("a", "b", "c", "d"),
        new Splitter().byCharsIn(separators).split("a,b.c:d"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byCharsIn(separators).split("a.,b:c"));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byCharsIn(separators).ignoreEmpty(true).split("a,.b:c"));
    assertEquals(List.of("a", " ", "b", "c"),
        new Splitter().byCharsIn(separators).split("a. :b,c"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byCharsIn(separators).strip(true).split("a: .b,c"));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byCharsIn(separators).strip(true).ignoreEmpty(true).split("a. :b,c"));
    assertEquals(List.of("", "a", " ", "b", "c", ""),
        new Splitter().byCharsIn(separators).split(",a. .b:c:"));
    assertEquals(List.of("", "a", " ", "b", "c", " "),
        new Splitter().byCharsIn(separators).split(".a. ,b:c. "));
    assertEquals(List.of("", "a", "", "b", "c", ""),
        new Splitter().byCharsIn(separators).strip(true).split(":a: ,b.c, "));
    assertEquals(List.of("a", "b", "c"),
        new Splitter().byCharsIn(separators).strip(true).ignoreEmpty(true).split(":a: ,b.c, "));
    assertEquals(List.of(":a: ,b.c,"),
        new Splitter().byCharsIn(new char[0]).strip(true).ignoreEmpty(true).split(":a: ,b.c, "));

    assertEquals(List.of(":a: ,b.c,"),
        new Splitter().byCharsIn((String) null).split(":a: ,b.c,"));
  }

  @Test
  public void testSplitByCodePointInStringSimpleCase() {
    final String separators = ".,:";
    assertEquals(Collections.emptyList(),
        new Splitter().byCodePointsIn(separators).split(null));
    assertEquals(List.of(""),
        new Splitter().byCodePointsIn(separators).split(""));
    assertEquals(Collections.emptyList(),
        new Splitter().byCodePointsIn(separators).ignoreEmpty(true).split(""));
    assertEquals(List.of("a", "b", "c", "d"),
        new Splitter().byCodePointsIn(separators).split("a,b.c:d"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byCodePointsIn(separators).split("a.,b:c"));
    assertEquals(List.of("a", "b", "c"), new Splitter()
        .byCodePointsIn(separators)
        .ignoreEmpty(true)
        .split("a,.b:c"));
    assertEquals(List.of("a", " ", "b", "c"),
        new Splitter().byCodePointsIn(separators).split("a. :b,c"));
    assertEquals(List.of("a", "", "b", "c"),
        new Splitter().byCodePointsIn(separators).strip(true).split("a: .b,c"));
    assertEquals(List.of("a", "b", "c"), new Splitter()
        .byCodePointsIn(separators)
        .strip(true)
        .ignoreEmpty(true)
        .split("a. :b,c"));
    assertEquals(List.of("", "a", " ", "b", "c", ""),
        new Splitter().byCodePointsIn(separators).split(",a. .b:c:"));
    assertEquals(List.of("", "a", " ", "b", "c", " "),
        new Splitter().byCodePointsIn(separators).split(".a. ,b:c. "));
    assertEquals(List.of("", "a", "", "b", "c", ""), new Splitter()
        .byCodePointsIn(separators)
        .strip(true)
        .split(":a: ,b.c, "));
    assertEquals(List.of("a", "b", "c"), new Splitter()
        .byCodePointsIn(separators)
        .strip(true)
        .ignoreEmpty(true)
        .split(":a: ,b.c, "));
    assertEquals(List.of(":a: ,b.c,"), new Splitter()
        .byCodePointsIn("")
        .strip(true)
        .ignoreEmpty(true)
        .split(":a: ,b.c, "));
  }

  @Test
  public void testSplitByCodePointInStringUnicodeCase() {
    final String separators = ",:\uD83D\uDD6E.";
    assertEquals(List.of("", "a", " ", "b", "c", ""), new Splitter()
        .byCodePointsIn(separators)
        .split(",a. \uD83D\uDD6Eb\uD83D\uDD6Ec\uD83D\uDD6E"));
    assertEquals(List.of("", "a", " ", "b", "c", " "), new Splitter()
        .byCodePointsIn(separators)
        .split(".a\uD83D\uDD6E ,b\uD83D\uDD6Ec. "));
    assertEquals(List.of("", "a", "", "b", "c", ""), new Splitter()
        .byCodePointsIn(separators)
        .strip(true)
        .split(":a\uD83D\uDD6E ,b.c, "));
    assertEquals(List.of("a", "b", "c"), new Splitter()
        .byCodePointsIn(separators)
        .strip(true)
        .ignoreEmpty(true)
        .split("\uD83D\uDD6Ea: \uD83D\uDD6Eb.c, "));
    assertEquals(List.of(":a: ,b.c\uD83D\uDD6E"), new Splitter()
        .byCodePointsIn("")
        .strip(true)
        .ignoreEmpty(true)
        .split(":a: ,b.c\uD83D\uDD6E "));
    assertEquals(List.of("a\uD83D:", "b.c\uDD6E, "), new Splitter()
        .byCodePointsIn("\uD83D\uDD6E")
        .split("a\uD83D:\uD83D\uDD6Eb.c\uDD6E, "));
    assertEquals(List.of("", "a\uD83D", " ", "b", "c\uDD6E", " "),
        new Splitter()
            .byCodePointsIn(separators)
            .split("\uD83D\uDD6Ea\uD83D: \uD83D\uDD6Eb.c\uDD6E, "));
  }

  @Test
  public void testSplitByCodePointSatisfy() {
    assertEquals(Arrays.asList("aBcDeFGhIJKlMn"),
        new Splitter().byCodePointsSatisfy(null).split("aBcDeFGhIJKlMn"));

    assertEquals(Arrays.asList("a", "c", "e", "", "h", "", " ", "l", "n"),
        new Splitter()
            .byCodePointsSatisfy(UpperCaseCodePointFilter.INSTANCE)
            .split("aBcDeFGhIJ KlMn"));

    assertEquals(Arrays.asList("a", "c", "e", "h", " ", "l", "n"),
        new Splitter()
            .ignoreEmpty(true)
            .byCodePointsSatisfy(UpperCaseCodePointFilter.INSTANCE)
            .split("aBcDeFGhIJ KlMn"));

    assertEquals(Arrays.asList("a", "c", "e", "h", "l", "n"),
        new Splitter()
            .strip(true)
            .ignoreEmpty(true)
            .byCodePointsSatisfy(UpperCaseCodePointFilter.INSTANCE)
            .split("aBcDeFGhIJ KlMn"));
  }

  @Test
  public void testSplitBySubstring() {
    List<String> result = null;

    result = new Splitter()
        .bySubstring(".")
        .ignoreEmpty(true)
        .split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring("").split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring(null).split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring(".").split("", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .strip(true)
        .ignoreEmpty(true)
        .split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring(".").strip(true).split("", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .ignoreEmpty(true)
        .split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring(null).split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "),
        result);

    result.clear();
    result = new Splitter()
        .bySubstring(null)
        .ignoreEmpty(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "),
        result);

    result.clear();
    result = new Splitter()
        .bySubstring(null)
        .strip(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "", "c", "", ""),
        result);

    result.clear();
    result = new Splitter()
        .bySubstring(null)
        .strip(true)
        .ignoreEmpty(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "c"), result);

    result.clear();
    result = new Splitter().bySubstring("").split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "),
        result);

    result.clear();
    result = new Splitter()
        .bySubstring("")
        .ignoreEmpty(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "),
        result);

    result.clear();
    result = new Splitter()
        .bySubstring("")
        .strip(true)
        .split("a.b.. c  ", result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "", "c", "", ""),
        result);

    result.clear();
    result = new Splitter()
        .bySubstring("")
        .strip(true)
        .ignoreEmpty(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "c"), result);

    result.clear();
    result = new Splitter().bySubstring(null).split("a", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a"), result);

    result.clear();
    result = new Splitter().bySubstring("").split("a", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a"), result);

    result.clear();
    result = new Splitter().bySubstring(null).split("", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(""), result);

    result.clear();
    result = new Splitter().bySubstring("").split("", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(null)
        .ignoreEmpty(true)
        .split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring("").ignoreEmpty(true).split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().bySubstring(".").split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "", " c  "), result);

    result.clear();
    result = new Splitter().bySubstring(".").strip(true).split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .ignoreEmpty(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", " c  "), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .strip(true)
        .ignoreEmpty(true)
        .split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .strip(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "", "c"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .strip(true)
        .ignoreEmpty(true)
        .split("a.b.. c  ", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = new Splitter().bySubstring(".").split(". a .", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", " a ", ""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .ignoreEmpty(true)
        .split(". a .", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(" a "), result);

    result.clear();
    result = new Splitter().bySubstring(".").strip(true).split(". a .", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a", ""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(".")
        .strip(true)
        .ignoreEmpty(true)
        .split(". a .", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a"), result);

    result.clear();
    result = new Splitter().bySubstring(" ").split(" a  b c", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a", "", "b", "c"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(" ")
        .ignoreEmpty(true)
        .split(" a  b c", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(" ")
        .strip(true)
        .split(" a  b c", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a", "", "b", "c"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(" ")
        .strip(true)
        .ignoreEmpty(true)
        .split(" a  b c", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = new Splitter().bySubstring(";").split(" ;;a;  b; ;c;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(" ", "", "a", "  b", " ", "c", ""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";")
        .ignoreEmpty(true)
        .split(" ;;a;  b; ;c;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(" ", "a", "  b", " ", "c"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";")
        .strip(true)
        .split(" ;;a;  b; ;c;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "", "a", "b", "", "c", ""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";")
        .strip(true)
        .ignoreEmpty(true)
        .split(" ;;a;  b; ;c;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";;")
        .strip(true)
        .split(" ;;a;  b; ;c;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a;  b; ;c;"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";;")
        .strip(true)
        .split(";;;;;;;;;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "", "", "", ";"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";;")
        .strip(true)
        .ignoreEmpty(true)
        .split(";;;;;;;;;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList(";"), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";;;")
        .strip(true)
        .split(";;;;;;;;;", result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "", "", ""), result);

    result.clear();
    result = new Splitter()
        .bySubstring(";;;")
        .strip(true)
        .ignoreEmpty(true)
        .split(";;;;;;;;;", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  public void testSplitByCharTypes() {
    assertTrue(new Splitter().byCharTypes().split(null).isEmpty());
    assertTrue(new Splitter().byCharTypes().ignoreEmpty(true).split("").isEmpty());
    assertEquals(Arrays.asList(""),
        new Splitter().byCharTypes().split(""));
    assertEquals(Arrays.asList("ab", "   ", "de", " ", "fg"),
        new Splitter().byCharTypes().split("ab   de fg"));
    assertEquals(Arrays.asList("ab", "", "de", "", "fg"),
        new Splitter().byCharTypes().strip(true).split("ab   de fg"));
    assertEquals(Arrays.asList("ab", "de", "fg"),
        new Splitter().byCharTypes().strip(true).ignoreEmpty(true).split("ab   de fg"));
    assertEquals(Arrays.asList("number", "5"),
        new Splitter().byCharTypes().split("number5"));
    assertEquals(Arrays.asList("foo", "B", "ar"),
        new Splitter().byCharTypes().split("fooBar"));
    assertEquals(Arrays.asList("foo", "Bar"),
        new Splitter().byCharTypes().camelCase(true).split("fooBar"));
    assertEquals(Arrays.asList("foo", "200", "B", "ar"),
        new Splitter().byCharTypes().split("foo200Bar"));
    assertEquals(Arrays.asList("foo", "200", "Bar"),
        new Splitter().byCharTypes().camelCase(true).split("foo200Bar"));
    assertEquals(Arrays.asList("ASFR", "ules"),
        new Splitter().byCharTypes().split("ASFRules"));
    assertEquals(Arrays.asList("ASF", "Rules"),
        new Splitter().byCharTypes().camelCase(true).split("ASFRules"));
  }

  @Test
  public void testSplitToLines() {
    assertTrue(new Splitter()
        .toLines()
        .strip(false)
        .ignoreEmpty(false)
        .split(null)
        .isEmpty());
    assertTrue(new Splitter()
        .toLines()
        .strip(false)
        .ignoreEmpty(true)
        .split("")
        .isEmpty());
    assertEquals(Arrays.asList(""),
        new Splitter()
            .toLines()
            .strip(false)
            .ignoreEmpty(false)
            .split(""));
    assertEquals(Arrays.asList("  a", "b", "    ", "d", "", "e"),
        new Splitter()
        .toLines()
        .strip(false)
        .ignoreEmpty(false)
        .split("  a\nb\n    \rd\r\n\ne"));
    assertEquals(Arrays.asList("  a", "b", "    ", "d", "e"),
        new Splitter()
        .toLines()
        .strip(false)
        .ignoreEmpty(true)
        .split("  a\nb\n    \rd\r\n\ne"));
    assertEquals(Arrays.asList("a", "b", "d", "e"),
        new Splitter()
        .toLines()
        .strip(true)
        .ignoreEmpty(true)
        .split("  a\nb\n    \rd\r\n\ne"));
  }

  @Test
  public void testSplitByWhitespace() {
    List<String> result = null;

    result = new Splitter().byWhitespaces().split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().byWhitespaces().split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    String str = "a b  .c";
    result.clear();
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);

    str = " a ";
    result.clear();
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a"), result);

    str = "a" + WHITESPACE + "b" + NON_WHITESPACE + "c";
    result.clear();
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a", "b" + NON_WHITESPACE + "c"), result);

    str = "a\fb\t.c";
    result.clear();
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);

    str = "a\u007Fb\u007F\t.c";
    result.clear();
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a\u007Fb\u007F", ".c"), result);


    str = "a\u007Fb\u007F\t.c";
    result.clear();
    result = new Splitter().byWhitespaces().strip(true).split(str, result);
    assertEquals(Arrays.asList("a\u007Fb", ".c"), result);

    // check that do not clear the passed list
    str = "a b  .c";
    result.clear();
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);
    str = " a ";
    // do not clear the result
    result = new Splitter().byWhitespaces().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c", "a"), result);
  }

  @Test
  public void testSplitByBlanks() {
    List<String> result = null;

    result = new Splitter().byBlanks().split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = new Splitter().byBlanks().split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    String str = "a b  .c";
    result.clear();
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);

    str = " a ";
    result.clear();
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a"), result);

    str = "a" + BLANK + "b" + NON_BLANK + "c";
    result.clear();
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a", "b" + NON_BLANK + "c"), result);

    str = "a\fb\t.c";
    result.clear();
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);

    str = "a\u007Fb\u007F\t.c";
    result.clear();
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);


    str = "a\u007Fb\u007F\t.c";
    result.clear();
    result = new Splitter().byBlanks().strip(true).split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);

    // check that do not clear the passed list
    str = "a b  .c";
    result.clear();
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);
    str = " a ";
    // do not clear the result
    result = new Splitter().byBlanks().split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c", "a"), result);
  }

  @Test
  public void testSplitToChars() {
    assertTrue(new Splitter().toChars().split(null).isEmpty());
    assertTrue(new Splitter().toChars().ignoreEmpty(true).split("").isEmpty());
    assertEquals(Arrays.asList(""), new Splitter().toChars().split(""));
    assertEquals(Arrays.asList("a", "b", " ", " ", "c", " ", "d"),
        new Splitter().toChars().split("ab  c d"));
    assertEquals(Arrays.asList("a", "b", "c", "d"),
        new Splitter().toChars().strip(true).ignoreEmpty(true).split("ab  c d"));
    // for unicode
    assertEquals(Arrays.asList("a", "⚽", "\uD83C", "\uDFC6"),
        new Splitter().toChars().split("a⚽\uD83C\uDFC6"));
  }

  @Test
  public void testSplitToCodePoints() {
    assertTrue(new Splitter().toCodePoints().split(null).isEmpty());
    assertTrue(new Splitter().toCodePoints().ignoreEmpty(true).split("").isEmpty());
    assertEquals(Arrays.asList(""), new Splitter().toCodePoints().split(""));
    assertEquals(Arrays.asList("a", "b", " ", " ", "c", " ", "d"),
        new Splitter().toCodePoints().split("ab  c d"));
    assertEquals(Arrays.asList("a", "b", "c", "d"),
        new Splitter().toCodePoints().strip(true).ignoreEmpty(true).split("ab  c d"));
    // for unicode
    assertEquals(Arrays.asList("a", "⚽", "\uD83C\uDFC6"),
        new Splitter().toCodePoints().split("a⚽\uD83C\uDFC6"));
  }

  @Test
  public void testNotSpecifyStrategy() {
    assertThrows(IllegalStateException.class,
        () -> new Splitter().split("abc"));
  }
}
