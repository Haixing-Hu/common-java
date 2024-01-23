////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static ltd.qubit.commons.lang.StringUtils.endsWith;
import static ltd.qubit.commons.lang.StringUtils.equalsIgnoreCase;
import static ltd.qubit.commons.lang.StringUtils.getCommonPrefix;
import static ltd.qubit.commons.lang.StringUtils.getDifference;
import static ltd.qubit.commons.lang.StringUtils.getLevenshteinDistance;
import static ltd.qubit.commons.lang.StringUtils.getOccurrences;
import static ltd.qubit.commons.lang.StringUtils.indexOfDifference;
import static ltd.qubit.commons.lang.StringUtils.startsWith;
import static ltd.qubit.commons.lang.StringUtils.startsWithIgnoreCase;

/**
 * Unit test for the comparison functions of the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsComparisonTest {

  @Test
  public void testEquals() {
    assertEquals(true, StringUtils.equals(null, null));
    assertEquals(false, StringUtils.equals(null, ""));
    assertEquals(false, StringUtils.equals("", null));
    assertEquals(true, StringUtils.equals("", ""));
    assertEquals(true, StringUtils.equals("foo", "foo"));
    assertEquals(false, StringUtils.equals("foo", "Foo"));
    assertEquals(true, StringUtils.equals("foo",
        new String(new char[]{'f', 'o', 'o'})));
    assertEquals(false, StringUtils.equals("foo",
        new String(new char[]{'f', 'O', 'O'})));
    assertEquals(false, StringUtils.equals("foo", "bar"));
    assertEquals(false, StringUtils.equals("foo", null));
    assertEquals(false, StringUtils.equals(null, "foo"));
  }

  @Test
  public void testEqualsIgnoreCase() {
    assertEquals(true, equalsIgnoreCase(null, null));
    assertEquals(false, equalsIgnoreCase(null, ""));
    assertEquals(false, equalsIgnoreCase("", null));
    assertEquals(true, equalsIgnoreCase("", ""));
    assertEquals(true, equalsIgnoreCase("foo", "foo"));
    assertEquals(true, equalsIgnoreCase("foo", "Foo"));
    assertEquals(true, equalsIgnoreCase("foo", new String(new char[]{'f', 'o', 'o'})));
    assertEquals(true, equalsIgnoreCase("foo", new String(new char[]{'f', 'O', 'O'})));
    assertEquals(false, equalsIgnoreCase("foo", "bar"));
    assertEquals(false, equalsIgnoreCase("foo", null));
    assertEquals(false, equalsIgnoreCase(null, "foo"));
  }

  @Test
  public void testStartsWith() {
    assertTrue(startsWith(null, null));
    assertFalse(startsWith("foobar", null));
    assertFalse(startsWith(null, "foo"));
    assertTrue(startsWith("", ""));
    assertFalse(startsWith("", "abc"));
    assertTrue(startsWith("foobar", ""));

    assertTrue(startsWith("foobar", "foo"));
    assertTrue(startsWith("FOOBAR", "FOO"));
    assertFalse(startsWith("foobar", "FOO"));
    assertFalse(startsWith("FOOBAR", "foo"));
    assertFalse(startsWith("foo", "foobar"));
    assertFalse(startsWith("bar", "foobar"));
    assertFalse(startsWith("foobar", "bar"));
    assertFalse(startsWith("FOOBAR", "BAR"));
    assertFalse(startsWith("foobar", "BAR"));
    assertFalse(startsWith("FOOBAR", "bar"));
  }

  @Test
  public void testStartsWithIgnoreCase() {
    assertTrue(startsWithIgnoreCase(null, null));
    assertFalse(startsWithIgnoreCase("foobar", null));
    assertFalse(startsWithIgnoreCase(null, "foo"));
    assertTrue(startsWithIgnoreCase("", ""));
    assertFalse(startsWithIgnoreCase("", "abc"));
    assertTrue(startsWithIgnoreCase("foobar", ""));
    assertTrue(startsWithIgnoreCase("foobar", "foo"));
    assertTrue(startsWithIgnoreCase("FOOBAR", "FOO"));
    assertTrue(startsWithIgnoreCase("foobar", "FOO"));
    assertTrue(startsWithIgnoreCase("FOOBAR", "foo"));
    assertFalse(startsWithIgnoreCase("foo", "foobar"));
    assertFalse(startsWithIgnoreCase("bar", "foobar"));
    assertFalse(startsWithIgnoreCase("foobar", "bar"));
    assertFalse(startsWithIgnoreCase("FOOBAR", "BAR"));
    assertFalse(startsWithIgnoreCase("foobar", "BAR"));
    assertFalse(startsWithIgnoreCase("FOOBAR", "bar"));
  }

  @Test
  public void testEndsWith() {
    assertTrue(endsWith(null, null));
    assertFalse(endsWith("foobar", null));
    assertFalse(endsWith(null, "foo"));
    assertTrue(endsWith("", ""));
    assertTrue(endsWith("foobar", ""));
    assertFalse(endsWith("", "abc"));

    assertFalse(endsWith("foobar", "foo"));
    assertFalse(endsWith("FOOBAR", "FOO"));
    assertFalse(endsWith("foobar", "FOO"));
    assertFalse(endsWith("FOOBAR", "foo"));
    assertFalse(endsWith("foo", "foobar"));
    assertFalse(endsWith("bar", "foobar"));
    assertTrue(endsWith("foobar", "bar"));
    assertTrue(endsWith("FOOBAR", "BAR"));
    assertFalse(endsWith("foobar", "BAR"));
    assertFalse(endsWith("FOOBAR", "bar"));
  }

  @Test
  public void testEndsWithIgnoreCase() {
    assertTrue(StringUtils.endsWithIgnoreCase(null, null));
    assertFalse(StringUtils.endsWithIgnoreCase("foobar", null));
    assertFalse(StringUtils.endsWithIgnoreCase(null, "foo"));
    assertTrue(StringUtils.endsWithIgnoreCase("", ""));
    assertTrue(StringUtils.endsWithIgnoreCase("foobar", ""));
    assertFalse(StringUtils.endsWithIgnoreCase("", "abc"));

    assertFalse(StringUtils.endsWithIgnoreCase("foobar", "foo"));
    assertFalse(StringUtils.endsWithIgnoreCase("FOOBAR", "FOO"));
    assertFalse(StringUtils.endsWithIgnoreCase("foobar", "FOO"));
    assertFalse(StringUtils.endsWithIgnoreCase("FOOBAR", "foo"));
    assertFalse(StringUtils.endsWithIgnoreCase("foo", "foobar"));
    assertFalse(StringUtils.endsWithIgnoreCase("bar", "foobar"));
    assertTrue(StringUtils.endsWithIgnoreCase("foobar", "bar"));
    assertTrue(StringUtils.endsWithIgnoreCase("FOOBAR", "BAR"));
    assertTrue(StringUtils.endsWithIgnoreCase("foobar", "BAR"));
    assertTrue(StringUtils.endsWithIgnoreCase("FOOBAR", "bar"));
  }

  @Test
  public void testIndexOfDifference_StringString() {
    assertEquals(-1, indexOfDifference(null, null));
    assertEquals(0, indexOfDifference(null, "i am a robot"));
    assertEquals(-1, indexOfDifference("", ""));
    assertEquals(0, indexOfDifference("", "abc"));
    assertEquals(0, indexOfDifference("abc", ""));
    assertEquals(0, indexOfDifference("i am a machine", null));
    assertEquals(7, indexOfDifference("i am a machine", "i am a robot"));
    assertEquals(-1, indexOfDifference("foo", "foo"));
    assertEquals(0, indexOfDifference("i am a robot", "you are a robot"));
    //System.out.println("indexOfDiff: "
    //    + Strings.indexOfDifference("i am a robot", "not machine"));
  }

  @Test
  public void testIndexOfDifference_StringArray() {
    assertEquals(-1, indexOfDifference((String[]) null));
    assertEquals(-1, indexOfDifference());
    assertEquals(-1, indexOfDifference("abc"));
    assertEquals(-1, indexOfDifference(new String[]{null, null}));
    assertEquals(-1, indexOfDifference(new String[]{"", ""}));
    assertEquals(0, indexOfDifference(new String[]{"", null}));
    assertEquals(0, indexOfDifference("abc", null, null));
    assertEquals(0, indexOfDifference(null, null, "abc"));
    assertEquals(0, indexOfDifference(new String[]{"", "abc"}));
    assertEquals(0, indexOfDifference(new String[]{"abc", ""}));
    assertEquals(-1, indexOfDifference(new String[]{"abc", "abc"}));
    assertEquals(1, indexOfDifference(new String[]{"abc", "a"}));
    assertEquals(2, indexOfDifference(new String[]{"ab", "abxyz"}));
    assertEquals(2, indexOfDifference(new String[]{"abcde", "abxyz"}));
    assertEquals(0, indexOfDifference(new String[]{"abcde", "xyz"}));
    assertEquals(0, indexOfDifference(new String[]{"xyz", "abcde"}));
    assertEquals(7, indexOfDifference(new String[]{"i am a machine", "i am a robot"}));
  }

  @Test
  public void testDifference_StringString() {
    assertNull(getDifference(null, null));
    assertEquals("", getDifference("", ""));
    assertEquals("abc", getDifference("", "abc"));
    assertEquals("", getDifference("abc", ""));
    assertEquals("i am a robot", getDifference(null, "i am a robot"));
    assertEquals("i am a machine", getDifference("i am a machine", null));
    assertEquals("robot", getDifference("i am a machine", "i am a robot"));
    assertEquals("", getDifference("abc", "abc"));
    assertEquals("you are a robot", getDifference("i am a robot", "you are a robot"));
  }

  @Test
  public void testGetCommonPrefix() {
    assertEquals("", getCommonPrefix((String[]) null));
    assertEquals("", getCommonPrefix());
    assertEquals("abc", getCommonPrefix("abc"));
    assertEquals("", getCommonPrefix(null, null));
    assertEquals("", getCommonPrefix("", ""));
    assertEquals("", getCommonPrefix("", null));
    assertEquals("", getCommonPrefix("abc", null, null));
    assertEquals("", getCommonPrefix(null, null, "abc"));
    assertEquals("", getCommonPrefix("", "abc"));
    assertEquals("", getCommonPrefix("abc", ""));
    assertEquals("abc", getCommonPrefix("abc", "abc"));
    assertEquals("a", getCommonPrefix("abc", "a"));
    assertEquals("ab", getCommonPrefix("ab", "abxyz"));
    assertEquals("ab", getCommonPrefix("abcde", "abxyz"));
    assertEquals("", getCommonPrefix("abcde", "xyz"));
    assertEquals("", getCommonPrefix("xyz", "abcde"));
    assertEquals("i am a ",
        getCommonPrefix("i am a machine", "i am a robot"));
  }

  @Test
  public void testGetLevenshteinDistance_StringString() {
    assertEquals(0, getLevenshteinDistance("", ""));
    assertEquals(1, getLevenshteinDistance("", "a"));
    assertEquals(7, getLevenshteinDistance("aaapppp", ""));
    assertEquals(1, getLevenshteinDistance("frog", "fog"));
    assertEquals(3, getLevenshteinDistance("fly", "ant"));
    assertEquals(7, getLevenshteinDistance("elephant", "hippo"));
    assertEquals(7, getLevenshteinDistance("hippo", "elephant"));
    assertEquals(8, getLevenshteinDistance("hippo", "zzzzzzzz"));
    assertEquals(8, getLevenshteinDistance("zzzzzzzz", "hippo"));
    assertEquals(1, getLevenshteinDistance("hello", "hallo"));
    try {
      @SuppressWarnings("unused") final int d = getLevenshteinDistance("a", null);
      fail("expecting IllegalArgumentException");
    } catch (final IllegalArgumentException ex) {
      // empty
    }
    try {
      @SuppressWarnings("unused") final int d = getLevenshteinDistance(null, "a");
      fail("expecting IllegalArgumentException");
    } catch (final IllegalArgumentException ex) {
      // empty
    }
  }

  @Test
  public void testGetOccurrences() {
    final IntList result = new IntArrayList();
    IntList expect = new IntArrayList();
    IntList list = new IntArrayList();
    final Object[][] Cases = {
        {"hello", "", true, new int[]{0, 1, 2, 3, 4}},
        {"hello", "", false, new int[]{0, 1, 2, 3, 4}},

        {"hello", "h", true, new int[]{0}},
        {"hello", "e", true, new int[]{1}},
        {"hello", "d", true, new int[]{}},

        {"hello", "h", false, new int[]{0}},
        {"hello", "e", false, new int[]{1}},
        {"hello", "d", false, new int[]{}},

        {"hello", "H", true, new int[]{0}},
        {"hello", "E", true, new int[]{1}},
        {"hello", "D", true, new int[]{}},

        {"hello", "H", false, new int[]{}},
        {"hello", "E", false, new int[]{}},
        {"hello", "D", false, new int[]{}},
    };

    assertEquals(expect, getOccurrences(null, null, true, null));
    assertEquals(expect, getOccurrences(null, null, false, null));
    assertEquals(expect, getOccurrences(null, "hello", true, null));
    assertEquals(expect, getOccurrences(null, "hello", false, null));
    assertEquals(expect, getOccurrences(null, "hello", true, result));
    assertEquals(expect, getOccurrences(null, "hello", false, result));
    assertEquals(expect, getOccurrences("hello", null, true, result));
    assertEquals(expect, getOccurrences("hello", null, false, result));

    for (final Object[] test : Cases) {
      final String name = (String) test[0];
      final String subname = (String) test[1];
      final boolean x = (Boolean) test[2];
      final int[] data = (int[]) test[3];
      result.clear();
      list = getOccurrences(name, subname, x, result);
      expect = new IntArrayList(data);
      assertEquals(expect, list, Arrays.toString(test));
    }
  }

}
