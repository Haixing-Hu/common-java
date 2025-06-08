////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.lang.SplitOption.CAMEL_CASE;
import static ltd.qubit.commons.lang.SplitOption.IGNORE_EMPTY;
import static ltd.qubit.commons.lang.SplitOption.NONE;
import static ltd.qubit.commons.lang.SplitOption.TRIM;
import static ltd.qubit.commons.lang.StringUtils.splitLines;

/**
 * Unit test for the split() function of Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsSplitTest {

  protected static final String WHITESPACE;
  protected static final String NON_WHITESPACE;

  static {
    String ws = "";
    String nws = "";
    for (int i = 0; i < Character.MAX_VALUE; i++) {
      if (Character.isWhitespace((char) i)) {
        ws += String.valueOf((char) i);
      } else if (i < 40) {
        nws += String.valueOf((char) i);
      }
    }
    WHITESPACE = ws;
    NON_WHITESPACE = nws;
  }

  @Test
  public void testSplitByWhitespace() {
    List<String> result = null;

    result = StringUtils.split(null, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    String str = "a b  .c";
    result.clear();
    result = StringUtils.split(str, result);
    assertEquals(Arrays.asList("a", "b", ".c"), result);

    str = " a ";
    result.clear();
    result = StringUtils.split(str, result);
    assertEquals(List.of("a"), result);

    str = "a" + WHITESPACE + "b" + NON_WHITESPACE + "c";
    result.clear();
    result = StringUtils.split(str, result);
    assertEquals(Arrays.asList("a", "b" + NON_WHITESPACE + "c"), result);
  }

  @Test
  public void testSplitByChar() {
    List<String> result = null;

    result = StringUtils.split(null, '.', IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split(null, '.', 0, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", '.', 0, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.split(null, '.', TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result.clear();
    result = StringUtils.split("", '.', TRIM, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.split("", '.', IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result.clear();
    result = StringUtils.split("a.b.. c  ", '.', 0, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("", result.get(2));
    assertEquals(" c  ", result.get(3));

    result.clear();
    result = StringUtils.split(null, '.', TRIM, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result.clear();
    result = StringUtils.split("a.b.. c  ", '.', IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals(" c  ", result.get(2));

    result.clear();
    result = StringUtils.split("", '.', TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result.clear();
    result = StringUtils.split("a.b.. c  ", '.', TRIM, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("c", result.get(3));

    result.clear();
    result = StringUtils.split("a.b.. c  ", '.', TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result.clear();
    result = StringUtils.split(". a .", '.', NONE, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("", result.get(0));
    assertEquals(" a ", result.get(1));
    assertEquals("", result.get(2));

    result.clear();
    result = StringUtils.split(". a .", '.', IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(" a ", result.get(0));

    result.clear();
    result = StringUtils.split(". a .", '.', TRIM, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));

    result.clear();
    result = StringUtils.split(". a .", '.', TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("a", result.get(0));

    result.clear();
    result = StringUtils.split(" a  b c", ' ', NONE, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("c", result.get(4));

    result.clear();
    result = StringUtils.split(" a  b c", ' ', IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result.clear();
    result = StringUtils.split(" a  b c", ' ', TRIM, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("c", result.get(4));

    result.clear();
    result = StringUtils.split(" a  b c", ' ', TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result.clear();
    result = StringUtils.split(" ;;a;  b; ;c;", ';', NONE, result);
    assertNotNull(result);
    assertEquals(7, result.size());
    assertEquals(" ", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("a", result.get(2));
    assertEquals("  b", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("c", result.get(5));
    assertEquals("", result.get(6));

    result.clear();
    result = StringUtils.split(" ;;a;  b; ;c;", ';', IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals(" ", result.get(0));
    assertEquals("a", result.get(1));
    assertEquals("  b", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("c", result.get(4));

    result.clear();
    result = StringUtils.split(" ;;a;  b; ;c;", ';', TRIM, result);
    assertNotNull(result);
    assertEquals(7, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("a", result.get(2));
    assertEquals("b", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("c", result.get(5));
    assertEquals("", result.get(6));

    result.clear();
    result = StringUtils.split(" ;;a;  b; ;c;", ';', TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
  }

  @Test
  public void testSplitByCharsInString() {
    List<String> result = null;

    result = StringUtils.split(null, ".", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", ".", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", "", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", (String) null, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", ".", TRIM, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.split("", (String) null, NONE, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.split(null, (String) null, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result.clear();
    result = StringUtils.split("abc", "", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;", ":.,;", NONE, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("", result.get(5));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;", ":.,;", TRIM, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("", result.get(5));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;", ":.,;", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals(" ", result.get(3));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;", ":.,;", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result.clear();
    result = StringUtils.split(";", ":.,;", TRIM, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));

    result.clear();
    result = StringUtils.split(";", ":.,;", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  public void testSplitByCharsInArray() {
    List<String> result = null;

    result = StringUtils.split(null, new char[]{','}, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", new char[]{','}, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", new char[]{}, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", (char[]) null, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.split("", new char[]{','}, TRIM, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.split("", (char[]) null, NONE, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.split(null, (char[]) null, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());

    result.clear();
    result = StringUtils.split("abc", new char[]{}, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("abc", result.get(0));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;",
        new char[]{':', '.', ',', ';'}, NONE, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals(" ", result.get(4));
    assertEquals("", result.get(5));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;",
        new char[]{':', '.', ',', ';'}, TRIM, result);
    assertNotNull(result);
    assertEquals(6, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals("", result.get(3));
    assertEquals("", result.get(4));
    assertEquals("", result.get(5));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;", new char[]{':', '.', ',', ';'},
        IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("a ", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));
    assertEquals(" ", result.get(3));

    result.clear();
    result = StringUtils.split("a ;b.c:: ;",
        new char[]{':', '.', ',', ';'}, TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("a", result.get(0));
    assertEquals("b", result.get(1));
    assertEquals("c", result.get(2));

    result.clear();
    result = StringUtils.split(";", new char[]{':', '.', ',', ';'},
        TRIM, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("", result.get(0));
    assertEquals("", result.get(1));

    result.clear();
    result = StringUtils.split(";", new char[]{':', '.', ',', ';'},
        TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(0, result.size());

  }

  @Test
  public void testSplitBySubstring() {
    List<String> result = null;

    result = StringUtils.splitByString(null, ".", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString(null, "", NONE, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString(null, null, NONE, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("", ".", NONE, result);
    assertNotNull(result);
    assertEquals(List.of(""), result);

    result.clear();
    result = StringUtils.splitByString(null, ".", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("", ".", TRIM, result);
    assertNotNull(result);
    assertEquals(List.of(""), result);

    result.clear();
    result = StringUtils.splitByString("", ".", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", null, NONE, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", null, IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", null, TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "", "c", "", ""), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", null,
        TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "c"), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", "", NONE, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", "", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", " ", "c", " ", " "), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", "", TRIM, result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "", "c", "", ""), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", "", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", ".", "b", ".", ".", "c"), result);

    result.clear();
    result = StringUtils.splitByString("a", null, NONE, result);
    assertNotNull(result);
    assertEquals(List.of("a"), result);

    result.clear();
    result = StringUtils.splitByString("a", "", NONE, result);
    assertNotNull(result);
    assertEquals(List.of("a"), result);

    result.clear();
    result = StringUtils.splitByString("", null, NONE, result);
    assertNotNull(result);
    assertEquals(List.of(""), result);

    result.clear();
    result = StringUtils.splitByString("", "", NONE, result);
    assertNotNull(result);
    assertEquals(List.of(""), result);

    result.clear();
    result = StringUtils.splitByString("", null, IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("", "", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", ".", NONE, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "", " c  "), result);

    result.clear();
    result = StringUtils.splitByString(null, ".", TRIM, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", ".", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", " c  "), result);

    result.clear();
    result = StringUtils.splitByString("", ".", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", ".", TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "", "c"), result);

    result.clear();
    result = StringUtils.splitByString("a.b.. c  ", ".", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = StringUtils.splitByString(". a .", ".", NONE, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", " a ", ""), result);

    result.clear();
    result = StringUtils.splitByString(". a .", ".", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(List.of(" a "), result);

    result.clear();
    result = StringUtils.splitByString(". a .", ".", TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a", ""), result);

    result.clear();
    result = StringUtils.splitByString(". a .", ".", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(List.of("a"), result);

    result.clear();
    result = StringUtils.splitByString(" a  b c", " ", NONE, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a", "", "b", "c"), result);

    result.clear();
    result = StringUtils.splitByString(" a  b c", " ", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = StringUtils.splitByString(" a  b c", " ", TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a", "", "b", "c"), result);

    result.clear();
    result = StringUtils.splitByString(" a  b c", " ", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = StringUtils
        .splitByString(" ;;a;  b; ;c;", ";", NONE, result);
    assertNotNull(result);
    assertEquals(Arrays.asList(" ", "", "a", "  b", " ", "c", ""), result);

    result.clear();
    result = StringUtils
        .splitByString(" ;;a;  b; ;c;", ";", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList(" ", "a", "  b", " ", "c"), result);

    result.clear();
    result = StringUtils
        .splitByString(" ;;a;  b; ;c;", ";", TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "", "a", "b", "", "c", ""), result);

    result.clear();
    result = StringUtils
        .splitByString(" ;;a;  b; ;c;", ";", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("a", "b", "c"), result);

    result.clear();
    result = StringUtils
        .splitByString(" ;;a;  b; ;c;", ";;", TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "a;  b; ;c;"), result);

    result.clear();
    result = StringUtils.splitByString(";;;;;;;;;", ";;", TRIM, result);
    assertNotNull(result);

    assertEquals(Arrays.asList("", "", "", "", ";"), result);

    result.clear();
    result = StringUtils.splitByString(";;;;;;;;;", ";;", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(List.of(";"), result);

    result.clear();
    result = StringUtils.splitByString(";;;;;;;;;", ";;;", TRIM, result);
    assertNotNull(result);
    assertEquals(Arrays.asList("", "", "", ""), result);

    result.clear();
    result = StringUtils.splitByString(";;;;;;;;;", ";;;", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  public void testSplitByCharType() {
    List<String> result = null;

    result = StringUtils.splitByCharType(null, NONE, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByCharType("", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(Collections.emptyList(), result);

    result.clear();
    result = StringUtils.splitByCharType("", NONE, result);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("", result.get(0));

    result.clear();
    result = StringUtils.splitByCharType("ab de fg", NONE, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals(" ", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("fg", result.get(4));

    result.clear();
    result = StringUtils.splitByCharType("ab de fg", IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals(" ", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("fg", result.get(4));

    result.clear();
    result = StringUtils.splitByCharType("ab de fg", TRIM, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals("", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals("", result.get(3));
    assertEquals("fg", result.get(4));

    result.clear();
    result = StringUtils.splitByCharType("ab de fg", TRIM | IGNORE_EMPTY, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("ab", result.get(0));
    assertEquals("de", result.get(1));
    assertEquals("fg", result.get(2));

    result.clear();
    result = StringUtils.splitByCharType("ab   de fg", NONE, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals("   ", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(" ", result.get(3));
    assertEquals("fg", result.get(4));

    result.clear();
    result = StringUtils.splitByCharType("ab:de:fg", NONE, result);
    assertNotNull(result);
    assertEquals(5, result.size());
    assertEquals("ab", result.get(0));
    assertEquals(":", result.get(1));
    assertEquals("de", result.get(2));
    assertEquals(":", result.get(3));
    assertEquals("fg", result.get(4));

    result.clear();
    result = StringUtils.splitByCharType("number5", NONE, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("number", result.get(0));
    assertEquals("5", result.get(1));

    result.clear();
    result = StringUtils.splitByCharType("fooBar", NONE, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("B", result.get(1));
    assertEquals("ar", result.get(2));

    result.clear();
    result = StringUtils.splitByCharType("foo2000Bar", NONE, result);
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("2000", result.get(1));
    assertEquals("B", result.get(2));
    assertEquals("ar", result.get(3));

    result.clear();
    result = StringUtils.splitByCharType("ASFRules", NONE, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("ASFR", result.get(0));
    assertEquals("ules", result.get(1));

    result.clear();
    result = StringUtils.splitByCharType("fooBar", CAMEL_CASE, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("Bar", result.get(1));

    result.clear();
    result = StringUtils.splitByCharType("foo2000Bar", CAMEL_CASE, result);
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("foo", result.get(0));
    assertEquals("2000", result.get(1));
    assertEquals("Bar", result.get(2));

    result.clear();
    result = StringUtils.splitByCharType("ASFRules", CAMEL_CASE, result);
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("ASF", result.get(0));
    assertEquals("Rules", result.get(1));

  }

  @Test
  public void testSplitLines() {
    assertTrue(splitLines(null, false, false).isEmpty());
    assertTrue(splitLines("", false, true).isEmpty());
    assertEquals(List.of(""), splitLines("", false, false));
    assertEquals(Arrays.asList("  a", "b", "    ", "d", "", "e"),
        splitLines("  a\nb\n    \rd\r\n\ne", false, false));
    assertEquals(Arrays.asList("  a", "b", "    ", "d", "e"),
        splitLines("  a\nb\n    \rd\r\n\ne", false, true));
    assertEquals(Arrays.asList("a", "b", "d", "e"),
        splitLines("  a\nb\n    \rd\r\n\ne", true, true));
  }
}