////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.datastructure.list.primitive.IntList;
import ltd.qubit.commons.datastructure.list.primitive.impl.IntArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for the comparison functions of the Strings class.
 *
 * @author Haixing Hu
 */
public class StringUtilsComparisonTest {

  @Test
  public void testEquals() {
    assertTrue(StringUtils.equals(null, null));
    assertFalse(StringUtils.equals(null, ""));
    assertFalse(StringUtils.equals("", null));
    assertTrue(StringUtils.equals("", ""));
    assertTrue(StringUtils.equals("foo", "foo"));
    assertFalse(StringUtils.equals("foo", "Foo"));
    assertTrue(
        StringUtils.equals("foo", new String(new char[]{'f', 'o', 'o'})));
    assertFalse(
        StringUtils.equals("foo", new String(new char[]{'f', 'O', 'O'})));
    assertFalse(StringUtils.equals("foo", "bar"));
    assertFalse(StringUtils.equals("foo", null));
    assertFalse(StringUtils.equals(null, "foo"));
  }

  @Test
  public void testEqualsIgnoreCase() {
    assertTrue(StringUtils.equalsIgnoreCase(null, null));
    assertFalse(StringUtils.equalsIgnoreCase(null, ""));
    assertFalse(StringUtils.equalsIgnoreCase("", null));
    assertTrue(StringUtils.equalsIgnoreCase("", ""));
    assertTrue(StringUtils.equalsIgnoreCase("foo", "foo"));
    assertTrue(StringUtils.equalsIgnoreCase("foo", "Foo"));
    assertTrue(StringUtils.equalsIgnoreCase("foo",
        new String(new char[]{'f', 'o', 'o'})));
    assertTrue(StringUtils.equalsIgnoreCase("foo",
        new String(new char[]{'f', 'O', 'O'})));
    assertFalse(StringUtils.equalsIgnoreCase("foo", "bar"));
    assertFalse(StringUtils.equalsIgnoreCase("foo", null));
    assertFalse(StringUtils.equalsIgnoreCase(null, "foo"));
  }

  @Test
  public void testStartsWith() {
    assertTrue(StringUtils.startsWith(null, null));
    assertFalse(StringUtils.startsWith("foobar", null));
    assertFalse(StringUtils.startsWith(null, "foo"));
    assertTrue(StringUtils.startsWith("", ""));
    assertFalse(StringUtils.startsWith("", "abc"));
    assertTrue(StringUtils.startsWith("foobar", ""));

    assertTrue(StringUtils.startsWith("foobar", "foo"));
    assertTrue(StringUtils.startsWith("FOOBAR", "FOO"));
    assertFalse(StringUtils.startsWith("foobar", "FOO"));
    assertFalse(StringUtils.startsWith("FOOBAR", "foo"));
    assertFalse(StringUtils.startsWith("foo", "foobar"));
    assertFalse(StringUtils.startsWith("bar", "foobar"));
    assertFalse(StringUtils.startsWith("foobar", "bar"));
    assertFalse(StringUtils.startsWith("FOOBAR", "BAR"));
    assertFalse(StringUtils.startsWith("foobar", "BAR"));
    assertFalse(StringUtils.startsWith("FOOBAR", "bar"));
  }

  @Test
  public void testStartsWithIgnoreCase() {
    assertTrue(StringUtils.startsWithIgnoreCase(null, null));
    assertFalse(StringUtils.startsWithIgnoreCase("foobar", null));
    assertFalse(StringUtils.startsWithIgnoreCase(null, "foo"));
    assertTrue(StringUtils.startsWithIgnoreCase("", ""));
    assertFalse(StringUtils.startsWithIgnoreCase("", "abc"));
    assertTrue(StringUtils.startsWithIgnoreCase("foobar", ""));
    assertTrue(StringUtils.startsWithIgnoreCase("foobar", "foo"));
    assertTrue(StringUtils.startsWithIgnoreCase("FOOBAR", "FOO"));
    assertTrue(StringUtils.startsWithIgnoreCase("foobar", "FOO"));
    assertTrue(StringUtils.startsWithIgnoreCase("FOOBAR", "foo"));
    assertFalse(StringUtils.startsWithIgnoreCase("foo", "foobar"));
    assertFalse(StringUtils.startsWithIgnoreCase("bar", "foobar"));
    assertFalse(StringUtils.startsWithIgnoreCase("foobar", "bar"));
    assertFalse(StringUtils.startsWithIgnoreCase("FOOBAR", "BAR"));
    assertFalse(StringUtils.startsWithIgnoreCase("foobar", "BAR"));
    assertFalse(StringUtils.startsWithIgnoreCase("FOOBAR", "bar"));
  }

  @Test
  public void testEndsWith() {
    assertTrue(StringUtils.endsWith(null, null));
    assertFalse(StringUtils.endsWith("foobar", null));
    assertFalse(StringUtils.endsWith(null, "foo"));
    assertTrue(StringUtils.endsWith("", ""));
    assertTrue(StringUtils.endsWith("foobar", ""));
    assertFalse(StringUtils.endsWith("", "abc"));

    assertFalse(StringUtils.endsWith("foobar", "foo"));
    assertFalse(StringUtils.endsWith("FOOBAR", "FOO"));
    assertFalse(StringUtils.endsWith("foobar", "FOO"));
    assertFalse(StringUtils.endsWith("FOOBAR", "foo"));
    assertFalse(StringUtils.endsWith("foo", "foobar"));
    assertFalse(StringUtils.endsWith("bar", "foobar"));
    assertTrue(StringUtils.endsWith("foobar", "bar"));
    assertTrue(StringUtils.endsWith("FOOBAR", "BAR"));
    assertFalse(StringUtils.endsWith("foobar", "BAR"));
    assertFalse(StringUtils.endsWith("FOOBAR", "bar"));
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
    assertEquals(-1, StringUtils.indexOfDifference(null, null));
    assertEquals(0, StringUtils.indexOfDifference(null, "i am a robot"));
    assertEquals(-1, StringUtils.indexOfDifference("", ""));
    assertEquals(0, StringUtils.indexOfDifference("", "abc"));
    assertEquals(0, StringUtils.indexOfDifference("abc", ""));
    assertEquals(0, StringUtils.indexOfDifference("i am a machine", null));
    assertEquals(7,
        StringUtils.indexOfDifference("i am a machine", "i am a robot"));
    assertEquals(-1, StringUtils.indexOfDifference("foo", "foo"));
    assertEquals(0,
        StringUtils.indexOfDifference("i am a robot", "you are a robot"));
    //System.out.println("indexOfDiff: "
    //    + Strings.indexOfDifference("i am a robot", "not machine"));
  }

  @Test
  public void testIndexOfDifference_StringArray() {
    assertEquals(-1, StringUtils.indexOfDifference((String[]) null));
    assertEquals(-1, StringUtils.indexOfDifference());
    assertEquals(-1, StringUtils.indexOfDifference("abc"));
    assertEquals(-1, StringUtils.indexOfDifference(new String[]{null, null}));
    assertEquals(-1, StringUtils.indexOfDifference(new String[]{"", ""}));
    assertEquals(0, StringUtils.indexOfDifference(new String[]{"", null}));
    assertEquals(0, StringUtils.indexOfDifference("abc", null, null));
    assertEquals(0, StringUtils.indexOfDifference(null, null, "abc"));
    assertEquals(0, StringUtils.indexOfDifference(new String[]{"", "abc"}));
    assertEquals(0, StringUtils.indexOfDifference(new String[]{"abc", ""}));
    assertEquals(-1, StringUtils.indexOfDifference(new String[]{"abc", "abc"}));
    assertEquals(1, StringUtils.indexOfDifference(new String[]{"abc", "a"}));
    assertEquals(2, StringUtils.indexOfDifference(new String[]{"ab", "abxyz"}));
    assertEquals(2,
        StringUtils.indexOfDifference(new String[]{"abcde", "abxyz"}));
    assertEquals(0,
        StringUtils.indexOfDifference(new String[]{"abcde", "xyz"}));
    assertEquals(0,
        StringUtils.indexOfDifference(new String[]{"xyz", "abcde"}));
    assertEquals(7, StringUtils
        .indexOfDifference(new String[]{"i am a machine", "i am a robot"}));
  }

  @Test
  public void testDifference_StringString() {
    assertNull(StringUtils.getDifference(null, null));
    assertEquals("", StringUtils.getDifference("", ""));
    assertEquals("abc", StringUtils.getDifference("", "abc"));
    assertEquals("", StringUtils.getDifference("abc", ""));
    assertEquals("i am a robot",
        StringUtils.getDifference(null, "i am a robot"));
    assertEquals("i am a machine",
        StringUtils.getDifference("i am a machine", null));
    assertEquals("robot",
        StringUtils.getDifference("i am a machine", "i am a robot"));
    assertEquals("", StringUtils.getDifference("abc", "abc"));
    assertEquals("you are a robot",
        StringUtils.getDifference("i am a robot", "you are a robot"));
  }

  @Test
  public void testGetCommonPrefix() {
    assertEquals("", StringUtils.getCommonPrefix((String[]) null));
    assertEquals("", StringUtils.getCommonPrefix());
    assertEquals("abc", StringUtils.getCommonPrefix("abc"));
    assertEquals("", StringUtils.getCommonPrefix(null, null));
    assertEquals("", StringUtils.getCommonPrefix("", ""));
    assertEquals("", StringUtils.getCommonPrefix("", null));
    assertEquals("", StringUtils.getCommonPrefix("abc", null, null));
    assertEquals("", StringUtils.getCommonPrefix(null, null, "abc"));
    assertEquals("", StringUtils.getCommonPrefix("", "abc"));
    assertEquals("", StringUtils.getCommonPrefix("abc", ""));
    assertEquals("abc", StringUtils.getCommonPrefix("abc", "abc"));
    assertEquals("a", StringUtils.getCommonPrefix("abc", "a"));
    assertEquals("ab", StringUtils.getCommonPrefix("ab", "abxyz"));
    assertEquals("ab", StringUtils.getCommonPrefix("abcde", "abxyz"));
    assertEquals("", StringUtils.getCommonPrefix("abcde", "xyz"));
    assertEquals("", StringUtils.getCommonPrefix("xyz", "abcde"));
    assertEquals("i am a ",
        StringUtils.getCommonPrefix("i am a machine", "i am a robot"));
  }

  @Test
  public void testGetLevenshteinDistance_StringString() {
    assertEquals(0, StringUtils.getLevenshteinDistance("", ""));
    assertEquals(1, StringUtils.getLevenshteinDistance("", "a"));
    assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", ""));
    assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog"));
    assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant"));
    assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo"));
    assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant"));
    assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz"));
    assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo"));
    assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo"));
    try {
      @SuppressWarnings("unused") final int d = StringUtils
          .getLevenshteinDistance("a", null);
      fail("expecting IllegalArgumentException");
    } catch (final IllegalArgumentException ex) {
      // empty
    }
    try {
      @SuppressWarnings("unused") final int d = StringUtils
          .getLevenshteinDistance(null, "a");
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

    // assertEquals(null, StringUtils.getOccurrences(null, null, true, null));
    // assertEquals(null, StringUtils.getOccurrences(null, null, false, null));
    // assertEquals(null, StringUtils.getOccurrences(null, "hello", true, null));
    // assertEquals(null, StringUtils.getOccurrences(null, "hello", false, null));
    // assertEquals(null, StringUtils.getOccurrences(null, "hello", true, result));
    // assertEquals(null,
    //     StringUtils.getOccurrences(null, "hello", false, result));
    // assertEquals(null, StringUtils.getOccurrences("hello", null, true, result));
    // assertEquals(null,
    //     StringUtils.getOccurrences("hello", null, false, result));

    for (final Object[] ele : Cases) {
      final String name = (String) ele[0];
      final String subname = (String) ele[1];
      final boolean x = (Boolean) ele[2];
      final int[] data = (int[]) ele[3];
      result.clear();
      list = StringUtils.getOccurrences(name, subname, x, result);
      expect = new IntArrayList(data);
      assertEquals(expect, list);
    }
  }

}
