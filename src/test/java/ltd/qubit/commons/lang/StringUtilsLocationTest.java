////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.util.filter.character.CharFilter;
import ltd.qubit.commons.util.filter.character.InStringCharFilter;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.lang.StringUtils.indexOfChar;
import static ltd.qubit.commons.lang.StringUtils.lastIndexOfChar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the indexOfXXXX() functions of the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsLocationTest {

  @Test
  public void testIndexOfChar_String_int_int() {
    assertEquals(-1, indexOfChar(null, ' ', 0));
    assertEquals(-1, indexOfChar(null, ' ', -1));
    assertEquals(-1, indexOfChar("", ' ', 0));
    assertEquals(-1, indexOfChar("", ' ', -1));
    assertEquals(0, indexOfChar("aabaabaa", 'a', 0));
    assertEquals(2, indexOfChar("aabaabaa", 'b', 0));
    assertEquals(5, indexOfChar("aabaabaa", 'b', 3));
    assertEquals(-1, indexOfChar("aabaabaa", 'b', 9));
    assertEquals(2, indexOfChar("aabaabaa", 'b', -1));
  }

  @Test
  public void testIndexOfChar_String_CharFilter_int() {
    assertEquals(-1, indexOfChar(null, null, 0));
    assertEquals(-1, indexOfChar(null, null, -1));
    assertEquals(-1, indexOfChar(null, null, 100));

    assertEquals(-1, indexOfChar(null, new InStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter(""), 100));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter("ab"), 0));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter("ab"), -1));
    assertEquals(-1, indexOfChar(null, new InStringCharFilter("ab"), 100));

    assertEquals(-1, indexOfChar(null, CharFilter.not(new InStringCharFilter("")), 0));
    assertEquals(-1, indexOfChar(null, CharFilter.not(new InStringCharFilter("")), -1));
    assertEquals(-1, indexOfChar(null, CharFilter.not(new InStringCharFilter("")), 100));
    assertEquals(-1, indexOfChar(null, CharFilter.not(new InStringCharFilter("ab")), 0));
    assertEquals(-1, indexOfChar(null, CharFilter.not(new InStringCharFilter("ab")), -1));
    assertEquals(-1, indexOfChar(null, CharFilter.not(new InStringCharFilter("ab")), 100));

    assertEquals(-1, indexOfChar("", null, 0));
    assertEquals(-1, indexOfChar("", null, -1));
    assertEquals(-1, indexOfChar("", null, 100));

    assertEquals(-1, indexOfChar("", new InStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar("", new InStringCharFilter(""), -1));
    assertEquals(-1, indexOfChar("", new InStringCharFilter(""), 100));
    assertEquals(-1, indexOfChar("", new InStringCharFilter("ab"), 0));
    assertEquals(-1, indexOfChar("", new InStringCharFilter("ab"), -1));
    assertEquals(-1, indexOfChar("", new InStringCharFilter("ab"), 100));

    assertEquals(-1, indexOfChar("", CharFilter.not(new InStringCharFilter("")), 0));
    assertEquals(-1, indexOfChar("", CharFilter.not(new InStringCharFilter("")), -1));
    assertEquals(-1, indexOfChar("", CharFilter.not(new InStringCharFilter("")), 100));
    assertEquals(-1, indexOfChar("", CharFilter.not(new InStringCharFilter("ab")), 0));
    assertEquals(-1, indexOfChar("", CharFilter.not(new InStringCharFilter("ab")), -1));
    assertEquals(-1, indexOfChar("", CharFilter.not(new InStringCharFilter("ab")), 100));

    assertEquals(-1, indexOfChar("zzabyycdxx", null, 0));
    assertEquals(-1, indexOfChar("zzabyycdxx", null, -1));
    assertEquals(-1, indexOfChar("zzabyycdxx", null, 100));

    assertEquals(-1, indexOfChar("zzabyycdxx", new InStringCharFilter(""), 0));
    assertEquals(-1, indexOfChar("zzabyycdxx", new InStringCharFilter(""), -1));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", new InStringCharFilter(""), 100));

    assertEquals(0,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("")), 0));
    assertEquals(0,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("")), -1));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("")), 100));

    assertEquals(0,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 0));
    assertEquals(0,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), -1));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 100));
    assertEquals(1,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 1));
    assertEquals(2,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 2));
    assertEquals(8,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 3));
    assertEquals(9,
        indexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 9));

    assertEquals(3,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 0));
    assertEquals(3,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), -1));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 100));
    assertEquals(3,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 1));
    assertEquals(3,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 2));
    assertEquals(3,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 3));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 9));

    assertEquals(3,
        indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 0));
    assertEquals(3,
        indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), -1));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 100));
    assertEquals(3,
        indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 1));
    assertEquals(4,
        indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 4));
    assertEquals(8,
        indexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 6));

    assertEquals(0,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 0));
    assertEquals(0,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), -1));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 100));
    assertEquals(1,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 1));
    assertEquals(6,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 4));
    assertEquals(7,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 7));
    assertEquals(-1,
        indexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 8));

    assertEquals(-1, indexOfChar("ab", new InStringCharFilter("zx"), 0));
    assertEquals(-1, indexOfChar("ab", new InStringCharFilter("zx"), -1));
    assertEquals(-1, indexOfChar("ab", new InStringCharFilter("zx"), 100));

    assertEquals(0, indexOfChar("ab", CharFilter.not(new InStringCharFilter("zx")), 0));
    assertEquals(0, indexOfChar("ab", CharFilter.not(new InStringCharFilter("zx")), -1));
    assertEquals(-1, indexOfChar("ab", CharFilter.not(new InStringCharFilter("zx")), 100));

  }

  @Test
  public void testIndexOfAnyChar_String_CharArray_int() {
    assertEquals(-1, StringUtils.indexOfAnyChar(null, (char[]) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, (char[]) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, (char[]) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, new char[0], 0));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, new char[0], -1));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, new char[0], 100));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, new char[]{'a', 'b'}, 0));
    assertEquals(-1,
        StringUtils.indexOfAnyChar(null, new char[]{'a', 'b'}, -1));
    assertEquals(-1,
        StringUtils.indexOfAnyChar(null, new char[]{'a', 'b'}, 100));

    assertEquals(-1, StringUtils.indexOfAnyChar("", (char[]) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("", (char[]) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("", (char[]) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyChar("", new char[0], 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("", new char[0], -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("", new char[0], 100));
    assertEquals(-1, StringUtils.indexOfAnyChar("", new char[]{'a', 'b'}, 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("", new char[]{'a', 'b'}, -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("", new char[]{'a', 'b'}, 100));

    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", (char[]) null, 0));
    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", (char[]) null, -1));
    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", (char[]) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", new char[0], 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", new char[0], -1));
    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", new char[0], 100));

    assertEquals(0, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 0));
    assertEquals(0, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 100));
    assertEquals(1, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 1));
    assertEquals(2, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 2));
    assertEquals(8, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 3));
    assertEquals(9, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 9));

    assertEquals(3, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 0));
    assertEquals(3, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 100));
    assertEquals(3, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 1));
    assertEquals(4, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 4));
    assertEquals(8, StringUtils
        .indexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 6));

    assertEquals(-1, StringUtils.indexOfAnyChar("ab", new char[]{'z'}, 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("ab", new char[]{'z'}, -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("ab", new char[]{'z'}, 100));

  }

  @Test
  public void testIndexOfAnyChar_String_String_int() {
    assertEquals(-1, StringUtils.indexOfAnyChar(null, (String) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, (String) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, (String) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, "", 0));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, "", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, "", 100));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, "ab", 0));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, "ab", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar(null, "ab", 100));

    assertEquals(-1, StringUtils.indexOfAnyChar("", (String) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("", (String) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("", (String) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyChar("", "", 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("", "", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("", "", 100));
    assertEquals(-1, StringUtils.indexOfAnyChar("", "ab", 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("", "ab", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("", "ab", 100));

    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", (String) null, 0));
    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", (String) null, -1));
    assertEquals(-1,
        StringUtils.indexOfAnyChar("zzabyycdxx", (String) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", "", 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", "", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", "", 100));

    assertEquals(0, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", 0));
    assertEquals(0, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", 100));
    assertEquals(1, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", 1));
    assertEquals(2, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", 2));
    assertEquals(8, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", 3));
    assertEquals(9, StringUtils.indexOfAnyChar("zzabyycdxx", "zax", 9));

    assertEquals(3, StringUtils.indexOfAnyChar("zzabyycdxx", "byx", 0));
    assertEquals(3, StringUtils.indexOfAnyChar("zzabyycdxx", "byx", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("zzabyycdxx", "byx", 100));
    assertEquals(3, StringUtils.indexOfAnyChar("zzabyycdxx", "byx", 1));
    assertEquals(4, StringUtils.indexOfAnyChar("zzabyycdxx", "byx", 4));
    assertEquals(8, StringUtils.indexOfAnyChar("zzabyycdxx", "byx", 6));

    assertEquals(-1, StringUtils.indexOfAnyChar("ab", "zx", 0));
    assertEquals(-1, StringUtils.indexOfAnyChar("ab", "zx", -1));
    assertEquals(-1, StringUtils.indexOfAnyChar("ab", "zx", 100));
  }

  @Test
  public void testIndexOfAnyCharBut_String_CharArray_int() {
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, (char[]) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, (char[]) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, (char[]) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, new char[0], 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, new char[0], -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, new char[0], 100));
    assertEquals(-1,
        StringUtils.indexOfAnyCharBut(null, new char[]{'a', 'b'}, 0));
    assertEquals(-1,
        StringUtils.indexOfAnyCharBut(null, new char[]{'a', 'b'}, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut(null, new char[]{'a', 'b'}, 100));

    assertEquals(-1, StringUtils.indexOfAnyCharBut("", (char[]) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", (char[]) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", (char[]) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", new char[0], 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", new char[0], -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", new char[0], 100));
    assertEquals(-1,
        StringUtils.indexOfAnyCharBut("", new char[]{'a', 'b'}, 0));
    assertEquals(-1,
        StringUtils.indexOfAnyCharBut("", new char[]{'a', 'b'}, -1));
    assertEquals(-1,
        StringUtils.indexOfAnyCharBut("", new char[]{'a', 'b'}, 100));

    assertEquals(0,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", (char[]) null, 0));
    assertEquals(0,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", (char[]) null, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", (char[]) null, 100));
    assertEquals(0,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", new char[0], 0));
    assertEquals(1,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", new char[0], 1));
    assertEquals(0,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", new char[0], -1));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[0], 100));

    assertEquals(3, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 0));
    assertEquals(3, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 100));
    assertEquals(3, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 1));
    assertEquals(3, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 2));
    assertEquals(3, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 3));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 9));

    assertEquals(0, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 0));
    assertEquals(0, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 100));
    assertEquals(1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 1));
    assertEquals(6, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 4));
    assertEquals(6, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 6));

    assertEquals(0, StringUtils.indexOfAnyCharBut("ab", new char[]{'z'}, 0));
    assertEquals(0, StringUtils.indexOfAnyCharBut("ab", new char[]{'z'}, -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("ab", new char[]{'z'}, 100));

  }

  @Test
  public void testIndexOfAnyCharBut_String_String_int() {
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, (String) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, (String) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, (String) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, "", 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, "", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, "", 100));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, "ab", 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, "ab", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut(null, "ab", 100));

    assertEquals(-1, StringUtils.indexOfAnyCharBut("", (String) null, 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", (String) null, -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", (String) null, 100));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", "", 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", "", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", "", 100));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", "ab", 0));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", "ab", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("", "ab", 100));

    assertEquals(0,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", (String) null, 0));
    assertEquals(0,
        StringUtils.indexOfAnyCharBut("zzabyycdxx", (String) null, -1));
    assertEquals(-1, StringUtils
        .indexOfAnyCharBut("zzabyycdxx", (String) null, 100));
    assertEquals(0, StringUtils.indexOfAnyCharBut("zzabyycdxx", "", 0));
    assertEquals(0, StringUtils.indexOfAnyCharBut("zzabyycdxx", "", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("zzabyycdxx", "", 100));

    assertEquals(3, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", 0));
    assertEquals(3, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", 100));
    assertEquals(3, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", 1));
    assertEquals(3, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", 2));
    assertEquals(3, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", 3));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("zzabyycdxx", "zax", 9));

    assertEquals(0, StringUtils.indexOfAnyCharBut("zzabyycdxx", "byx", 0));
    assertEquals(0, StringUtils.indexOfAnyCharBut("zzabyycdxx", "byx", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("zzabyycdxx", "byx", 100));
    assertEquals(1, StringUtils.indexOfAnyCharBut("zzabyycdxx", "byx", 1));
    assertEquals(6, StringUtils.indexOfAnyCharBut("zzabyycdxx", "byx", 4));
    assertEquals(6, StringUtils.indexOfAnyCharBut("zzabyycdxx", "byx", 6));

    assertEquals(0, StringUtils.indexOfAnyCharBut("ab", "zx", 0));
    assertEquals(0, StringUtils.indexOfAnyCharBut("ab", "zx", -1));
    assertEquals(-1, StringUtils.indexOfAnyCharBut("ab", "zx", 100));
  }

  @Test
  public void testIndexOf() {
    assertEquals(-1, StringUtils.indexOf(null, null, 0, false));
    assertEquals(-1, StringUtils.indexOf(null, null, -1, false));
    assertEquals(-1, StringUtils.indexOf(null, null, 100, false));

    assertEquals(-1, StringUtils.indexOf("", null, 0, false));
    assertEquals(-1, StringUtils.indexOf("", null, -1, false));
    assertEquals(-1, StringUtils.indexOf("", null, 100, false));

    assertEquals(-1, StringUtils.indexOf("", "", 0, false));
    assertEquals(-1, StringUtils.indexOf("", "", -1, false));
    assertEquals(-1, StringUtils.indexOf("", "", 100, false));

    assertEquals(0, StringUtils.indexOf("aabaabaa", "a", 0, false));
    assertEquals(0, StringUtils.indexOf("aabaabaa", "a", -1, false));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "a", 100, false));
    assertEquals(1, StringUtils.indexOf("aabaabaa", "a", 1, false));
    assertEquals(3, StringUtils.indexOf("aabaabaa", "a", 2, false));

    assertEquals(2, StringUtils.indexOf("aabaabaa", "b", 0, false));
    assertEquals(2, StringUtils.indexOf("aabaabaa", "b", -1, false));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "b", 100, false));
    assertEquals(5, StringUtils.indexOf("aabaabaa", "b", 3, false));

    assertEquals(-1, StringUtils.indexOf("aabaabaa", "Ab", 0, false));
    assertEquals(1, StringUtils.indexOf("aabaabaa", "Ab", 0, true));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "aB", -1, false));
    assertEquals(1, StringUtils.indexOf("aabaabaa", "aB", -1, true));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "ab", 100, false));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "ab", 100, true));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "AB", 2, false));
    assertEquals(4, StringUtils.indexOf("aabaabaa", "AB", 2, true));

    assertEquals(0, StringUtils.indexOf("aabaabaa", "", 0, false));
    assertEquals(0, StringUtils.indexOf("aabaabaa", "", -1, false));
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "", 100, false));
    assertEquals(4, StringUtils.indexOf("aabaabaa", "", 4, false));
  }

  @Test
  public void testIndexOfAny_String() {
    assertEquals(-1, StringUtils.indexOfAny(null, null, 0, false));
    assertEquals(-1, StringUtils
        .indexOfAny(null, new String[]{"ob", "ba"}, 0, false));
    assertEquals(-1, StringUtils.indexOfAny("foobar", null, 0, false));

    assertEquals(2, StringUtils
        .indexOfAny("foobar", new String[]{"ob", "ba"}, 0, false));
    assertEquals(2, StringUtils
        .indexOfAny("foobar", new String[]{"ob", "ba"}, -1, false));
    assertEquals(-1, StringUtils
        .indexOfAny("foobar", new String[]{"ob", "ba"}, 100, false));
    assertEquals(3, StringUtils
        .indexOfAny("foobar", new String[]{"ob", "ba"}, 3, false));

    assertEquals(-1, StringUtils.indexOfAny("foobar", new String[0], 0, false));
    assertEquals(-1, StringUtils.indexOfAny(null, new String[0], 0, false));
    assertEquals(-1, StringUtils.indexOfAny("", new String[0], 0, false));
    assertEquals(-1, StringUtils
        .indexOfAny("foobar", new String[]{"llll"}, 0, false));

    assertEquals(0,
        StringUtils.indexOfAny("foobar", new String[]{""}, 0, false));
    assertEquals(0, StringUtils
        .indexOfAny("foobar", new String[]{""}, -1, false));
    assertEquals(-1, StringUtils
        .indexOfAny("foobar", new String[]{""}, 100, false));
    assertEquals(4,
        StringUtils.indexOfAny("foobar", new String[]{""}, 4, false));

    assertEquals(-1, StringUtils.indexOfAny("", new String[]{""}, 0, false));
    assertEquals(-1, StringUtils.indexOfAny("", new String[]{"a"}, 0, false));
    assertEquals(-1, StringUtils.indexOfAny("", new String[]{null}, 0, false));
    assertEquals(-1, StringUtils
        .indexOfAny("foobar", new String[]{null}, 0, false));
    assertEquals(0, StringUtils
        .indexOfAny("foobar", new String[]{null, ""}, 0, false));
    assertEquals(-1,
        StringUtils.indexOfAny(null, new String[]{null}, 0, false));

    assertEquals(2, StringUtils
        .indexOfAny("foobar", new String[]{"ob", null}, -1, false));
  }

  @Test
  public void testLastIndexOfChar() {
    assertEquals(-1, lastIndexOfChar(null, ' ', 0));
    assertEquals(-1, lastIndexOfChar(null, ' ', -1));
    assertEquals(-1, lastIndexOfChar("", ' ', 0));
    assertEquals(-1, lastIndexOfChar("", ' ', -1));

    assertEquals(7, lastIndexOfChar("aabaabaa", 'a', 8));
    assertEquals(7,
        lastIndexOfChar("aabaabaa", 'a', Integer.MAX_VALUE));
    assertEquals(5, lastIndexOfChar("aabaabaa", 'b', 8));
    assertEquals(2, lastIndexOfChar("aabaabaa", 'b', 3));
    assertEquals(5, lastIndexOfChar("aabaabaa", 'b', 9));
    assertEquals(5, lastIndexOfChar("aabaabaa", 'b', 100));
    assertEquals(-1, lastIndexOfChar("aabaabaa", 'b', -1));
    assertEquals(0, lastIndexOfChar("aabaabaa", 'a', 0));
  }

  @Test
  public void testLastIndexOfChar_String_CharFilter_int() {
    assertEquals(-1, lastIndexOfChar(null, null, 0));
    assertEquals(-1, lastIndexOfChar(null, null, -1));
    assertEquals(-1, lastIndexOfChar(null, null, 100));

    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter(""), 100));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter("ab"), 0));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter("ab"), -1));
    assertEquals(-1, lastIndexOfChar(null, new InStringCharFilter("ab"), 100));

    assertEquals(-1, lastIndexOfChar(null, CharFilter.not(new InStringCharFilter("")), 0));
    assertEquals(-1, lastIndexOfChar(null, CharFilter.not(new InStringCharFilter("")), -1));
    assertEquals(-1, lastIndexOfChar(null, CharFilter.not(new InStringCharFilter("")), 100));
    assertEquals(-1, lastIndexOfChar(null, CharFilter.not(new InStringCharFilter("ab")), 0));
    assertEquals(-1,
        lastIndexOfChar(null, CharFilter.not(new InStringCharFilter("ab")), -1));
    assertEquals(-1,
        lastIndexOfChar(null, CharFilter.not(new InStringCharFilter("ab")), 100));

    assertEquals(-1, lastIndexOfChar("", null, 0));
    assertEquals(-1, lastIndexOfChar("", null, -1));
    assertEquals(-1, lastIndexOfChar("", null, 100));

    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter(""), 0));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter(""), -1));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter(""), 100));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter("ab"), 0));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter("ab"), -1));
    assertEquals(-1, lastIndexOfChar("", new InStringCharFilter("ab"), 100));

    assertEquals(-1, lastIndexOfChar("", CharFilter.not(new InStringCharFilter("")), 0));
    assertEquals(-1, lastIndexOfChar("", CharFilter.not(new InStringCharFilter("")), -1));
    assertEquals(-1, lastIndexOfChar("", CharFilter.not(new InStringCharFilter("")), 100));
    assertEquals(-1, lastIndexOfChar("", CharFilter.not(new InStringCharFilter("ab")), 0));
    assertEquals(-1, lastIndexOfChar("", CharFilter.not(new InStringCharFilter("ab")), -1));
    assertEquals(-1, lastIndexOfChar("", CharFilter.not(new InStringCharFilter("ab")), 100));

    assertEquals(-1, lastIndexOfChar("zzabyycdxx", null, 0));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", null, -1));
    assertEquals(-1, lastIndexOfChar("zzabyycdxx", null, 100));

    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter(""), 0));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter(""), -1));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter(""), 100));

    assertEquals(0,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("")), 0));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("")), -1));
    assertEquals(9,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("")), 100));

    assertEquals(0,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 0));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), -1));
    assertEquals(9,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 100));
    assertEquals(1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 1));
    assertEquals(2,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 2));
    assertEquals(2,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 3));
    assertEquals(2,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("zax"), 7));

    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 0));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), -1));
    assertEquals(7,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 100));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 1));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 2));
    assertEquals(3,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 3));
    assertEquals(7,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("zax")), 9));

    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 0));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), -1));
    assertEquals(9,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 100));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 1));
    assertEquals(4,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 4));
    assertEquals(5,
        lastIndexOfChar("zzabyycdxx", new InStringCharFilter("byx"), 6));

    assertEquals(0,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 0));
    assertEquals(-1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), -1));
    assertEquals(7,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 100));
    assertEquals(1,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 1));
    assertEquals(2,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 4));
    assertEquals(7,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 7));
    assertEquals(7,
        lastIndexOfChar("zzabyycdxx", CharFilter.not(new InStringCharFilter("byx")), 8));

    assertEquals(-1, lastIndexOfChar("ab", new InStringCharFilter("zx"), 0));
    assertEquals(-1, lastIndexOfChar("ab", new InStringCharFilter("zx"), -1));
    assertEquals(-1, lastIndexOfChar("ab", new InStringCharFilter("zx"), 100));

    assertEquals(0, lastIndexOfChar("ab", CharFilter.not(new InStringCharFilter("zx")), 0));
    assertEquals(-1,
        lastIndexOfChar("ab", CharFilter.not(new InStringCharFilter("zx")), -1));
    assertEquals(1,
        lastIndexOfChar("ab", CharFilter.not(new InStringCharFilter("zx")), 100));

  }

  @Test
  public void testLastIndexOfAnyChar_String_CharArray_int() {
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, (char[]) null, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, (char[]) null, -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, (char[]) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, new char[0], 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, new char[0], -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, new char[0], 100));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar(null, new char[]{'a', 'b'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar(null, new char[]{'a', 'b'}, -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar(null, new char[]{'a', 'b'}, 100));

    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", (char[]) null, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", (char[]) null, -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", (char[]) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", new char[0], 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", new char[0], -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", new char[0], 100));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyChar("", new char[]{'a', 'b'}, 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyChar("", new char[]{'a', 'b'}, -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("", new char[]{'a', 'b'}, 100));

    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", (char[]) null, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", (char[]) null, -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", (char[]) null, 100));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyChar("zzabyycdxx", new char[0], 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyChar("zzabyycdxx", new char[0], -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[0], 100));

    assertEquals(0, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, -1));
    assertEquals(9, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 100));
    assertEquals(1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 1));
    assertEquals(2, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 2));
    assertEquals(2, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 3));
    assertEquals(9, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'z', 'a', 'x'}, 9));

    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, -1));
    assertEquals(9, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 100));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 1));
    assertEquals(4, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 4));
    assertEquals(5, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", new char[]{'b', 'y', 'x'}, 6));

    assertEquals(-1, StringUtils.lastIndexOfAnyChar("ab", new char[]{'z'}, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("ab", new char[]{'z'}, -1));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyChar("ab", new char[]{'z'}, 100));
  }

  @Test
  public void testLastIndexOfAnyChar_String_String_int() {
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, (String) null, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, (String) null, -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, (String) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, "", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, "", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, "", 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, "ab", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, "ab", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar(null, "ab", 100));

    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", (String) null, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", (String) null, -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", (String) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", "", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", "", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", "", 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", "ab", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", "ab", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("", "ab", 100));

    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", (String) null, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", (String) null, -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyChar("zzabyycdxx", (String) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "", 100));

    assertEquals(9, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", -1));
    assertEquals(9, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", 100));
    assertEquals(1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", 1));
    assertEquals(2, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", 2));
    assertEquals(2, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", 3));
    assertEquals(2, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "zax", 7));

    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "byx", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "byx", -1));
    assertEquals(9, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "byx", 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "byx", 1));
    assertEquals(4, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "byx", 4));
    assertEquals(5, StringUtils.lastIndexOfAnyChar("zzabyycdxx", "byx", 6));

    assertEquals(-1, StringUtils.lastIndexOfAnyChar("ab", "zx", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("ab", "zx", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyChar("ab", "zx", 100));
  }

  @Test
  public void testLastIndexOfAnyCharBut_String_CharArray_int() {
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, (char[]) null, 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut(null, (char[]) null, -1));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut(null, (char[]) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, new char[0], 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, new char[0], -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, new char[0], 100));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut(null, new char[]{'a', 'b'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut(null, new char[]{'a', 'b'}, -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut(null, new char[]{'a', 'b'}, 100));

    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", (char[]) null, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", (char[]) null, -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", (char[]) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", new char[0], 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", new char[0], -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", new char[0], 100));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("", new char[]{'a', 'b'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("", new char[]{'a', 'b'}, -1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("", new char[]{'a', 'b'}, 100));

    assertEquals(0, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", (char[]) null, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", (char[]) null, -1));
    assertEquals(9, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", (char[]) null, 100));
    assertEquals(0, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[0], 0));
    assertEquals(1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[0], 1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[0], -1));
    assertEquals(9, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[0], 100));

    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, -1));
    assertEquals(7, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 100));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 1));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 2));
    assertEquals(3, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 3));
    assertEquals(7, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'z', 'a', 'x'}, 9));

    assertEquals(0, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, -1));
    assertEquals(7, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 100));
    assertEquals(1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 1));
    assertEquals(2, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 4));
    assertEquals(6, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", new char[]{'b', 'y', 'x'}, 6));

    assertEquals(0,
        StringUtils.lastIndexOfAnyCharBut("ab", new char[]{'z'}, 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut("ab", new char[]{'z'}, -1));
    assertEquals(1, StringUtils
        .lastIndexOfAnyCharBut("ab", new char[]{'z'}, 100));

  }

  @Test
  public void testLastIndexOfAnyCharBut_String_String_int() {
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, (String) null, 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut(null, (String) null, -1));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut(null, (String) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, "", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, "", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, "", 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, "ab", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, "ab", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut(null, "ab", 100));

    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", (String) null, 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", (String) null, -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", (String) null, 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", "", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", "", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", "", 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", "ab", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", "ab", -1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("", "ab", 100));

    assertEquals(0, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", (String) null, 0));
    assertEquals(-1, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", (String) null, -1));
    assertEquals(9, StringUtils
        .lastIndexOfAnyCharBut("zzabyycdxx", (String) null, 100));
    assertEquals(0, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "", 0));
    assertEquals(1, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "", 1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "", -1));
    assertEquals(9, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "", 100));

    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", -1));
    assertEquals(7,
        StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", 100));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", 1));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", 2));
    assertEquals(3, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", 3));
    assertEquals(7, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "zax", 9));

    assertEquals(0, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "byx", 0));
    assertEquals(-1,
        StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "byx", -1));
    assertEquals(7,
        StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "byx", 100));
    assertEquals(1, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "byx", 1));
    assertEquals(2, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "byx", 4));
    assertEquals(6, StringUtils.lastIndexOfAnyCharBut("zzabyycdxx", "byx", 6));

    assertEquals(0, StringUtils.lastIndexOfAnyCharBut("ab", "zx", 0));
    assertEquals(-1, StringUtils.lastIndexOfAnyCharBut("ab", "zx", -1));
    assertEquals(1, StringUtils.lastIndexOfAnyCharBut("ab", "zx", 100));
  }

  @Test
  public void testLastIndexOf() {
    assertEquals(-1, StringUtils.lastIndexOf(null, null, 0, false));
    assertEquals(-1, StringUtils.lastIndexOf(null, null, -1, false));
    assertEquals(-1, StringUtils.lastIndexOf(null, null, 100, false));

    assertEquals(-1, StringUtils.lastIndexOf("", null, 0, false));
    assertEquals(-1, StringUtils.lastIndexOf("", null, -1, false));
    assertEquals(-1, StringUtils.lastIndexOf("", null, 100, false));

    assertEquals(-1, StringUtils.lastIndexOf("", "", 0, false));
    assertEquals(-1, StringUtils.lastIndexOf("", "", -1, false));
    assertEquals(-1, StringUtils.lastIndexOf("", "", 100, false));

    assertEquals(0, StringUtils.lastIndexOf("aabaabaa", "a", 0, false));
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "a", -1, false));
    assertEquals(7, StringUtils.lastIndexOf("aabaabaa", "a", 100, false));
    assertEquals(1, StringUtils.lastIndexOf("aabaabaa", "a", 1, false));
    assertEquals(1, StringUtils.lastIndexOf("aabaabaa", "a", 2, false));

    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", 0, false));
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", -1, false));
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b", 100, false));
    assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "b", 3, false));

    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "ab", 0, false));
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "ab", -1, false));
    assertEquals(4, StringUtils.lastIndexOf("aabaabaa", "ab", 100, false));
    assertEquals(1, StringUtils.lastIndexOf("aabaabaa", "ab", 3, false));

    assertEquals(0, StringUtils.lastIndexOf("aabaabaa", "", 0, false));
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "", -1, false));
    assertEquals(7, StringUtils.lastIndexOf("aabaabaa", "", 100, false));
    assertEquals(4, StringUtils.lastIndexOf("aabaabaa", "", 4, false));
  }

  @Test
  public void testLastIndexOfAny() {
    assertEquals(-1, StringUtils.lastIndexOfAny(null, null, 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny(null, new String[]{"ob", "ba"}, 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", null, 0, false));

    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", new String[]{"ob", "ba"}, 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", new String[]{"ob", "ba"}, -1, false));
    assertEquals(3, StringUtils
        .lastIndexOfAny("foobar", new String[]{"ob", "ba"}, 100, false));
    assertEquals(3, StringUtils
        .lastIndexOfAny("foobar", new String[]{"ob", "ba"}, 3, false));

    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", new String[0], 0, false));
    assertEquals(-1, StringUtils.lastIndexOfAny(null, new String[0], 0, false));
    assertEquals(-1, StringUtils.lastIndexOfAny("", new String[0], 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", new String[]{"llll"}, 0, false));

    assertEquals(0, StringUtils
        .lastIndexOfAny("foobar", new String[]{""}, 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", new String[]{""}, -1, false));
    assertEquals(5, StringUtils
        .lastIndexOfAny("foobar", new String[]{""}, 100, false));
    assertEquals(4, StringUtils
        .lastIndexOfAny("foobar", new String[]{""}, 4, false));

    assertEquals(-1,
        StringUtils.lastIndexOfAny("", new String[]{""}, 0, false));
    assertEquals(-1,
        StringUtils.lastIndexOfAny("", new String[]{"a"}, 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny("", new String[]{null}, 0, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny("foobar", new String[]{null}, 0, false));
    assertEquals(5, StringUtils
        .lastIndexOfAny("foobar", new String[]{null, ""}, 100, false));
    assertEquals(-1, StringUtils
        .lastIndexOfAny(null, new String[]{null}, 0, false));

    assertEquals(2, StringUtils
        .lastIndexOfAny("foobar", new String[]{"ob", null}, 100, false));
  }

  @Test
  public void testContainsOnly_String() {
    final String str1 = "a";
    final String str2 = "b";
    final String str3 = "ab";
    final String chars1 = "b";
    final String chars2 = "a";
    final String chars3 = "ab";
    assertEquals(false, StringUtils.containsOnly(null, (String) null));
    assertEquals(false, StringUtils.containsOnly("", (String) null));
    assertEquals(false, StringUtils.containsOnly(null, ""));
    assertEquals(false, StringUtils.containsOnly(str1, ""));
    assertEquals(true, StringUtils.containsOnly("", ""));
    assertEquals(true, StringUtils.containsOnly("", chars1));
    assertEquals(false, StringUtils.containsOnly(str1, chars1));
    assertEquals(true, StringUtils.containsOnly(str1, chars2));
    assertEquals(true, StringUtils.containsOnly(str1, chars3));
    assertEquals(true, StringUtils.containsOnly(str2, chars1));
    assertEquals(false, StringUtils.containsOnly(str2, chars2));
    assertEquals(true, StringUtils.containsOnly(str2, chars3));
    assertEquals(false, StringUtils.containsOnly(str3, chars1));
    assertEquals(false, StringUtils.containsOnly(str3, chars2));
    assertEquals(true, StringUtils.containsOnly(str3, chars3));
  }

  @Test
  public void testContainsOnly_Chararray() {
    final String str1 = "a";
    final String str2 = "b";
    final String str3 = "ab";
    final char[] chars1 = {'b'};
    final char[] chars2 = {'a'};
    final char[] chars3 = {'a', 'b'};
    final char[] emptyChars = new char[0];
    assertEquals(false, StringUtils.containsOnly(null, (char[]) null));
    assertEquals(false, StringUtils.containsOnly("", (char[]) null));
    assertEquals(false, StringUtils.containsOnly(null, emptyChars));
    assertEquals(false, StringUtils.containsOnly(str1, emptyChars));
    assertEquals(true, StringUtils.containsOnly("", emptyChars));
    assertEquals(true, StringUtils.containsOnly("", chars1));
    assertEquals(false, StringUtils.containsOnly(str1, chars1));
    assertEquals(true, StringUtils.containsOnly(str1, chars2));
    assertEquals(true, StringUtils.containsOnly(str1, chars3));
    assertEquals(true, StringUtils.containsOnly(str2, chars1));
    assertEquals(false, StringUtils.containsOnly(str2, chars2));
    assertEquals(true, StringUtils.containsOnly(str2, chars3));
    assertEquals(false, StringUtils.containsOnly(str3, chars1));
    assertEquals(false, StringUtils.containsOnly(str3, chars2));
    assertEquals(true, StringUtils.containsOnly(str3, chars3));
  }

  //  TODO
  //  @Test
  //  public void testContainsNone_String() {
  //    final String str1 = "a";
  //    final String str2 = "b";
  //    final String str3 = "ab.";
  //    final String chars1= "b";
  //    final String chars2= ".";
  //    final String chars3= "cd";
  //    assertEquals(true, containsNone(null, (String) null));
  //    assertEquals(true, containsNone("", (String) null));
  //    assertEquals(true, containsNone(null, ""));
  //    assertEquals(true, containsNone(str1, ""));
  //    assertEquals(true, containsNone("", ""));
  //    assertEquals(true, containsNone("", chars1));
  //    assertEquals(true, containsNone(str1, chars1));
  //    assertEquals(true, containsNone(str1, chars2));
  //    assertEquals(true, containsNone(str1, chars3));
  //    assertEquals(false, containsNone(str2, chars1));
  //    assertEquals(true, containsNone(str2, chars2));
  //    assertEquals(true, containsNone(str2, chars3));
  //    assertEquals(false, containsNone(str3, chars1));
  //    assertEquals(false, containsNone(str3, chars2));
  //    assertEquals(true, containsNone(str3, chars3));
  //  }

  //  @Test
  //  public void testContainsNone_Chararray() {
  //    final String str1 = "a";
  //    final String str2 = "b";
  //    final String str3 = "ab.";
  //    final char[] chars1= {'b'};
  //    final char[] chars2= {'.'};
  //    final char[] chars3= {'c', 'd'};
  //    final char[] emptyChars = new char[0];
  //    assertEquals(true, containsNone(null, (char[]) null));
  //    assertEquals(true, containsNone("", (char[]) null));
  //    assertEquals(true, containsNone(null, emptyChars));
  //    assertEquals(true, containsNone(str1, emptyChars));
  //    assertEquals(true, containsNone("", emptyChars));
  //    assertEquals(true, containsNone("", chars1));
  //    assertEquals(true, containsNone(str1, chars1));
  //    assertEquals(true, containsNone(str1, chars2));
  //    assertEquals(true, containsNone(str1, chars3));
  //    assertEquals(false, containsNone(str2, chars1));
  //    assertEquals(true, containsNone(str2, chars2));
  //    assertEquals(true, containsNone(str2, chars3));
  //    assertEquals(false, containsNone(str3, chars1));
  //    assertEquals(false, containsNone(str3, chars2));
  //    assertEquals(true, containsNone(str3, chars3));
  //  }

  @Test
  public void testCountMatches_Int() {
    assertEquals(0, StringUtils.countMatches(null, 0));
    assertEquals(0, StringUtils.countMatches("", 0));
    assertEquals(0, StringUtils.countMatches("hello world", 0));
    assertEquals(1, StringUtils.countMatches("hello world", 'h'));
    assertEquals(2, StringUtils.countMatches("hello world", 'o'));
    assertEquals(3, StringUtils.countMatches("hello world", 'l'));
  }

  @Test
  public void testCountMatches_String() {
    assertEquals(0, StringUtils.countMatches(null, null));
    assertEquals(0, StringUtils.countMatches("blah", null));
    assertEquals(0, StringUtils.countMatches(null, "DD"));
    assertEquals(0, StringUtils.countMatches("x", ""));
    assertEquals(0, StringUtils.countMatches("", ""));
    assertEquals(3, StringUtils.countMatches("one long someone sentence of one", "one"));
    assertEquals(0, StringUtils.countMatches("one long someone sentence of one", "two"));
    assertEquals(4, StringUtils.countMatches("oooooooooooo", "ooo"));
  }
}
