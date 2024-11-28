////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JoinerTest {

  @Test
  public void testJoin_CharObjectArray() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(new Joiner(',').addAll(objArray).toString());

    strArray = new String[]{};
    assertEquals("", new Joiner(';').addAll(strArray).toString());

    strArray = new String[]{"foo", "bar", "baz"};
    assertEquals("foo;bar;baz", new Joiner(';').addAll(strArray).toString());
    assertEquals("foo;bar", new Joiner(';').addAll(strArray, 0, 2).toString());
    assertEquals("baz", new Joiner(';').addAll(strArray, 2, 3).toString());
    assertEquals("foo;bar;baz", new Joiner(';').addAll(strArray, -1, 1000).toString());
    assertEquals("", new Joiner(';').addAll(strArray, 4, 1000).toString());

    strArray = new String[]{null, "", "foo"};
    assertEquals(";;foo", new Joiner(';').addAll(strArray).toString());
    assertEquals("", new Joiner(';').addAll(strArray, 0, 0).toString());
    assertEquals("", new Joiner(';').addAll(strArray, 0, 1).toString());
    assertEquals(";", new Joiner(';').addAll(strArray, 0, 2).toString());

    objArray = new Object[]{"foo", null, 2L};
    assertEquals("foo;;2", new Joiner(';').addAll(objArray).toString());
    assertEquals("foo", new Joiner(';').addAll(objArray, 0, 1).toString());
    assertEquals("", new Joiner(';').addAll(objArray, 1, 2).toString());
    assertEquals(";2", new Joiner(';').addAll(objArray, 1, 3).toString());
    assertEquals("", new Joiner(';').addAll(objArray, 3, 2).toString());
  }

  @Test
  public void testJoin_StringObjectArray() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(new Joiner(",").addAll(objArray).toString());

    strArray = new String[]{};
    assertEquals("", new Joiner(";").addAll(strArray).toString());

    strArray = new String[]{"foo", "bar", "baz"};
    assertEquals("foo--bar--baz", new Joiner("--").addAll(strArray).toString());
    assertEquals("foo--bar", new Joiner("--").addAll(strArray, 0, 2).toString());
    assertEquals("baz", new Joiner("--").addAll(strArray, 2, 3).toString());
    assertEquals("foo--bar--baz", new Joiner("--").addAll(strArray, -1, 1000).toString());
    assertEquals("", new Joiner("--").addAll(strArray, 4, 1000).toString());

    assertEquals("foobarbaz", new Joiner("").addAll(strArray).toString());
    assertEquals("foobar", new Joiner("").addAll(strArray, 0, 2).toString());
    assertEquals("baz", new Joiner("").addAll(strArray, 2, 3).toString());
    assertEquals("foobarbaz", new Joiner("").addAll(strArray, -1, 1000).toString());
    assertEquals("", new Joiner("").addAll(strArray, 4, 1000).toString());

    assertEquals("foobarbaz", new Joiner(null).addAll(strArray).toString());
    assertEquals("foobar", new Joiner(null).addAll(strArray, 0, 2).toString());
    assertEquals("baz", new Joiner(null).addAll(strArray, 2, 3).toString());
    assertEquals("foobarbaz", new Joiner(null).addAll(strArray, -1, 1000).toString());
    assertEquals("", new Joiner(null).addAll(strArray, 4, 1000).toString());

    strArray = new String[]{null, "", "foo"};
    assertEquals("----foo", new Joiner("--").addAll(strArray).toString());
    assertEquals("", new Joiner("--").addAll(strArray, 0, 0).toString());
    assertEquals("", new Joiner("--").addAll(strArray, 0, 1).toString());
    assertEquals("--", new Joiner("--").addAll(strArray, 0, 2).toString());

    assertEquals("foo", new Joiner("").addAll(strArray).toString());
    assertEquals("", new Joiner("").addAll(strArray, 0, 0).toString());
    assertEquals("", new Joiner("").addAll(strArray, 0, 1).toString());
    assertEquals("", new Joiner("").addAll(strArray, 0, 2).toString());

    assertEquals("foo", new Joiner(null).addAll(strArray).toString());
    assertEquals("", new Joiner(null).addAll(strArray, 0, 0).toString());
    assertEquals("", new Joiner(null).addAll(strArray, 0, 1).toString());
    assertEquals("", new Joiner(null).addAll(strArray, 0, 2).toString());

    objArray = new Object[]{"foo", null, 2L};
    assertEquals("foo;;2", new Joiner(';').addAll(objArray).toString());
    assertEquals("foo", new Joiner(';').addAll(objArray, 0, 1).toString());
    assertEquals("", new Joiner(';').addAll(objArray, 1, 2).toString());
    assertEquals(";2", new Joiner(';').addAll(objArray, 1, 3).toString());
    assertEquals("", new Joiner(';').addAll(objArray, 3, 2).toString());

    assertEquals("foo----2", new Joiner("--").addAll(objArray).toString());
    assertEquals("foo", new Joiner("--").addAll(objArray, 0, 1).toString());
    assertEquals("", new Joiner("--").addAll(objArray, 1, 2).toString());
    assertEquals("--2", new Joiner("--").addAll(objArray, 1, 3).toString());
    assertEquals("", new Joiner("--").addAll(objArray, 3, 2).toString());

    assertEquals("foo2", new Joiner("").addAll(objArray).toString());
    assertEquals("foo", new Joiner("").addAll(objArray, 0, 1).toString());
    assertEquals("", new Joiner("").addAll(objArray, 1, 2).toString());
    assertEquals("2", new Joiner("").addAll(objArray, 1, 3).toString());
    assertEquals("", new Joiner("").addAll(objArray, 3, 2).toString());

  }

  @Test
  public void testJoin_CharIterator() {

    Object[] objArray = null;
    String[] strArray = null;

    assertNull(new Joiner(',').addAll((Iterator<String>) null).toString());

    strArray = new String[]{};
    assertEquals("",
        new Joiner(';').addAll(Arrays.asList(strArray).iterator()).toString());

    strArray = new String[]{"foo", "bar", "baz"};
    assertEquals("foo;bar;baz",
        new Joiner(';').addAll(Arrays.asList(strArray).iterator()).toString());

    strArray = new String[]{null, "", "foo"};
    assertEquals(";;foo",
        new Joiner(';').addAll(Arrays.asList(strArray).iterator()).toString());

    objArray = new Object[]{"foo", null, 2L};
    assertEquals("foo;;2",
        new Joiner(';').addAll(Arrays.asList(objArray).iterator()).toString());
  }

  @Test
  public void testJoin_StringIterator() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(new Joiner(",").addAll((Iterator<String>) null).toString());

    strArray = new String[]{};
    assertEquals("",
        new Joiner(";").addAll(Arrays.asList(strArray).iterator()).toString());

    strArray = new String[]{"foo", "bar", "baz"};
    assertEquals("foo--bar--baz",
        new Joiner("--").addAll(Arrays.asList(strArray).iterator()).toString());

    assertEquals("foobarbaz",
        new Joiner("").addAll(Arrays.asList(strArray).iterator()).toString());

    assertEquals("foobarbaz",
        new Joiner(null).addAll(Arrays.asList(strArray).iterator()).toString());

    strArray = new String[]{null, "", "foo"};
    assertEquals("----foo",
        new Joiner("--").addAll(Arrays.asList(strArray).iterator()).toString());
    assertEquals("foo",
        new Joiner("").addAll(Arrays.asList(strArray).iterator()).toString());
    assertEquals("foo",
        new Joiner(null).addAll(Arrays.asList(strArray).iterator()).toString());

    objArray = new Object[]{"foo", null, 2L};
    assertEquals("foo;;2",
        new Joiner(';').addAll(Arrays.asList(objArray).iterator()).toString());
    assertEquals("foo----2",
        new Joiner("--").addAll(Arrays.asList(objArray).iterator()).toString());
    assertEquals("foo2",
        new Joiner("").addAll(Arrays.asList(objArray).iterator()).toString());
  }

  @Test
  public void testJoin_CharIterable() {

    Object[] objArray = null;
    String[] strArray = null;

    strArray = new String[]{};
    assertEquals("", new Joiner(';').addAll(Arrays.asList(strArray)).toString());

    strArray = new String[]{"foo", "bar", "baz"};
    assertEquals("foo;bar;baz",
        new Joiner(';').addAll(Arrays.asList(strArray)).toString());

    strArray = new String[]{null, "", "foo"};
    assertEquals(";;foo", new Joiner(';').addAll(Arrays.asList(strArray)).toString());

    objArray = new Object[]{"foo", null, 2L};
    assertEquals("foo;;2", new Joiner(';').addAll(Arrays.asList(objArray)).toString());

    assertNull(new Joiner(',').addAll((Iterable<String>) null).toString());
  }

  @Test
  public void testJoin_StringIterable() {
    Object[] objArray = null;
    String[] strArray = null;

    assertNull(new Joiner(",").addAll((Iterable<String>) null).toString());

    strArray = new String[]{};
    assertEquals("", new Joiner(";").addAll(Arrays.asList(strArray)).toString());

    strArray = new String[]{"foo", "bar", "baz"};
    assertEquals("foo--bar--baz", new Joiner("--").addAll(Arrays.asList(strArray)).toString());

    assertEquals("foobarbaz", new Joiner("").addAll(Arrays.asList(strArray)).toString());

    assertEquals("foobarbaz", new Joiner(null).addAll(Arrays.asList(strArray)).toString());

    strArray = new String[]{null, "", "foo"};
    assertEquals("----foo", new Joiner("--").addAll(Arrays.asList(strArray)).toString());
    assertEquals("foo", new Joiner("").addAll(Arrays.asList(strArray)).toString());
    assertEquals("foo", new Joiner(null).addAll(Arrays.asList(strArray)).toString());

    objArray = new Object[]{"foo", null, 2L};
    assertEquals("foo;;2", new Joiner(';').addAll(Arrays.asList(objArray)).toString());
    assertEquals("foo----2",
        new Joiner("--").addAll(Arrays.asList(objArray)).toString());
    assertEquals("foo2", new Joiner("").addAll(Arrays.asList(objArray)).toString());
  }

  @Test
  public void testJoin_char_boolean() {
    boolean[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new boolean[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new boolean[]{true};
    assertEquals("true", new Joiner(';').addAll(values).toString());
    assertEquals("true", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("true", new Joiner(';').addAll(values, -1, 20).toString());

    values = new boolean[]{true, false, true};
    assertEquals("true;false;true", new Joiner(';').addAll(values).toString());
    assertEquals("true;false;true",
        new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("true;false;true", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_char() {
    char[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new char[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new char[]{'a'};
    assertEquals("a", new Joiner(';').addAll(values).toString());
    assertEquals("a", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("a", new Joiner(';').addAll(values, -1, 20).toString());

    values = new char[]{'a', 'b', 'c'};
    assertEquals("a;b;c", new Joiner(';').addAll(values).toString());
    assertEquals("a;b;c", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("a;b;c", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_byte() {
    byte[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new byte[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new byte[]{(byte) 2};
    assertEquals("2", new Joiner(';').addAll(values).toString());
    assertEquals("2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(';').addAll(values, -1, 20).toString());

    values = new byte[]{(byte) 20, (byte) 10, (byte) 2};
    assertEquals("20;10;2", new Joiner(';').addAll(values).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_double() {
    double[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new double[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new double[]{(double) 2};
    assertEquals("2.0", new Joiner(';').addAll(values).toString());
    assertEquals("2.0", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("2.0", new Joiner(';').addAll(values, -1, 20).toString());

    values = new double[]{(double) 20, (double) 10, (double) 2};
    assertEquals("20.0;10.0;2.0", new Joiner(';').addAll(values).toString());
    assertEquals("20.0;10.0;2.0",
        new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("20.0;10.0;2.0", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_float() {
    float[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new float[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new float[]{(float) 2};
    assertEquals("2.0", new Joiner(';').addAll(values).toString());
    assertEquals("2.0", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("2.0", new Joiner(';').addAll(values, -1, 20).toString());

    values = new float[]{(float) 20, (float) 10, (float) 2};
    assertEquals("20.0;10.0;2.0", new Joiner(';').addAll(values).toString());
    assertEquals("20.0;10.0;2.0",
        new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("20.0;10.0;2.0", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_int() {
    int[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new int[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new int[]{2};
    assertEquals("2", new Joiner(';').addAll(values).toString());
    assertEquals("2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(';').addAll(values, -1, 20).toString());

    values = new int[]{20, 10, 2};
    assertEquals("20;10;2", new Joiner(';').addAll(values).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_long() {
    long[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new long[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new long[]{(long) 2};
    assertEquals("2", new Joiner(';').addAll(values).toString());
    assertEquals("2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(';').addAll(values, -1, 20).toString());

    values = new long[]{(long) 20, (long) 10, (long) 2};
    assertEquals("20;10;2", new Joiner(';').addAll(values).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_char_short() {
    short[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new short[]{};
    assertEquals("", new Joiner(';').addAll(values).toString());
    assertEquals("", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, 20).toString());

    values = new short[]{(short) 2};
    assertEquals("2", new Joiner(';').addAll(values).toString());
    assertEquals("2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(';').addAll(values, -1, 20).toString());

    values = new short[]{(short) 20, (short) 10, (short) 2};
    assertEquals("20;10;2", new Joiner(';').addAll(values).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(';').addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(';').addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_String_boolean() {
    boolean[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new boolean[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());

    values = new boolean[]{true};
    assertEquals("true", new Joiner(";").addAll(values).toString());
    assertEquals("true", new Joiner(";").addAll(values, 0, values.length).toString());

    values = new boolean[]{true, false, true};
    assertEquals("true;false;true", new Joiner(";").addAll(values).toString());
    assertEquals("true;false;true",
        new Joiner(";").addAll(values, 0, values.length).toString());
  }

  @Test
  public void testJoin_string_char() {
    char[] values = null;
    assertEquals(null, new Joiner(';').addAll(values).toString());

    values = new char[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new char[]{'a'};
    assertEquals("a", new Joiner(";").addAll(values).toString());
    assertEquals("a", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("a", new Joiner(";").addAll(values, -1, 20).toString());

    values = new char[]{'a', 'b', 'c'};
    assertEquals("a;b;c", new Joiner(";").addAll(values).toString());
    assertEquals("a;b;c", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("a;b;c", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_string_byte() {
    byte[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new byte[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new byte[]{(byte) 2};
    assertEquals("2", new Joiner(";").addAll(values).toString());
    assertEquals("2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(";").addAll(values, -1, 20).toString());

    values = new byte[]{(byte) 20, (byte) 10, (byte) 2};
    assertEquals("20;10;2", new Joiner(";").addAll(values).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_string_double() {
    double[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new double[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new double[]{(double) 2};
    assertEquals("2.0", new Joiner(";").addAll(values).toString());
    assertEquals("2.0", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("2.0", new Joiner(";").addAll(values, -1, 20).toString());

    values = new double[]{(double) 20, (double) 10, (double) 2};
    assertEquals("20.0;10.0;2.0", new Joiner(";").addAll(values).toString());
    assertEquals("20.0;10.0;2.0",
        new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("20.0;10.0;2.0", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_string_float() {
    float[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new float[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new float[]{(float) 2};
    assertEquals("2.0", new Joiner(";").addAll(values).toString());
    assertEquals("2.0", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("2.0", new Joiner(";").addAll(values, -1, 20).toString());

    values = new float[]{(float) 20, (float) 10, (float) 2};
    assertEquals("20.0;10.0;2.0", new Joiner(";").addAll(values).toString());
    assertEquals("20.0;10.0;2.0",
        new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("20.0;10.0;2.0", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_string_int() {
    int[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new int[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new int[]{2};
    assertEquals("2", new Joiner(";").addAll(values).toString());
    assertEquals("2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(";").addAll(values, -1, 20).toString());

    values = new int[]{20, 10, 2};
    assertEquals("20;10;2", new Joiner(";").addAll(values).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_string_long() {
    long[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new long[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new long[]{(long) 2};
    assertEquals("2", new Joiner(";").addAll(values).toString());
    assertEquals("2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(";").addAll(values, -1, 20).toString());

    values = new long[]{(long) 20, (long) 10, (long) 2};
    assertEquals("20;10;2", new Joiner(";").addAll(values).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testJoin_string_short() {
    short[] values = null;
    assertEquals(null, new Joiner(";").addAll(values).toString());

    values = new short[]{};
    assertEquals("", new Joiner(";").addAll(values).toString());
    assertEquals("", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, 20).toString());

    values = new short[]{(short) 2};
    assertEquals("2", new Joiner(";").addAll(values).toString());
    assertEquals("2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("2", new Joiner(";").addAll(values, -1, 20).toString());

    values = new short[]{(short) 20, (short) 10, (short) 2};
    assertEquals("20;10;2", new Joiner(";").addAll(values).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, 0, values.length).toString());
    assertEquals("", new Joiner(";").addAll(values, -1, -1).toString());
    assertEquals("20;10;2", new Joiner(";").addAll(values, -1, 20).toString());
  }

  @Test
  public void testAdd() {
    assertNull(new Joiner("--").toString());
    assertEquals("abc",
        new Joiner("--").add("abc").toString());
    assertEquals("abc--def",
        new Joiner("--").add("abc").add("def").toString());
    assertEquals("abc--def--",
        new Joiner("--").add("abc").add("def").add("").toString());
    assertEquals("abc--def----",
        new Joiner("--").add("abc").add("def").add("").add((String)null).toString());
    assertEquals("abc--def--",
        new Joiner("--").add("abc").add("def").add("")
            .ignoreNull(true)
            .add((String)null).toString());
  }
}
