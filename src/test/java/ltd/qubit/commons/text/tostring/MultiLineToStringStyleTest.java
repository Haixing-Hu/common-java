////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.lang.SystemUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the ToStringBuilder and MultiLineToStringStyle class.
 *
 * @author Haixing Hu
 */
public class MultiLineToStringStyleTest {

  private final Integer base = 5;
  private final String baseStr = base.getClass().getName() + "@"
      + Integer.toHexString(System.identityHashCode(base));

  private static final ToStringStyle STYLE = MultiLineToStringStyle.INSTANCE;

  @Test
  public void testBlank() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).toString());
  }

  @Test
  public void testAppendSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).appendSuper(
            "Integer@8888[" + SystemUtils.LINE_SEPARATOR + "]").toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).appendSuper(
            "Integer@8888[" + SystemUtils.LINE_SEPARATOR + "  <null>"
                + SystemUtils.LINE_SEPARATOR + "]").toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=\"hello\""
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).appendSuper(
            "Integer@8888[" + SystemUtils.LINE_SEPARATOR + "]").append("a",
            "hello").toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "  a=\"hello\""
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).appendSuper(
            "Integer@8888[" + SystemUtils.LINE_SEPARATOR + "  <null>"
                + SystemUtils.LINE_SEPARATOR + "]").append("a", "hello")
               .toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=\"hello\""
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).appendSuper(null).append("a", "hello").toString());
  }

  @Test
  public void testObject() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Integer i3 = 3;
    final Integer i4 = 4;

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) null).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  3"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(i3).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", (Object) null).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", i3).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3"
        + SystemUtils.LINE_SEPARATOR + "  b=4" + SystemUtils.LINE_SEPARATOR
        + "]", builder.reset(base).append("a", i3).append("b", i4).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<Integer>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", i3, false).toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<size=0>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new ArrayList<Byte>(), false)
               .toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=[]"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new ArrayList<Byte>(), true)
               .toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<size=0>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), false)
               .toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a={}"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), true)
               .toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<size=0>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", (Object) new String[0], false)
               .toString());

    assertEquals(
        baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a={}"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", (Object) new String[0], true)
               .toString());
  }

  @Test
  public void testPerson() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final ToStringStyleTest.Person p = new ToStringStyleTest.Person();
    p.name = "Jane Doe";
    p.age = 25;
    p.smoker = true;
    final String str = p.getClass().getName() + "@"
        + Integer.toHexString(System.identityHashCode(p));

    assertEquals(
        str + "[" + SystemUtils.LINE_SEPARATOR + "  name=\"Jane Doe\""
            + SystemUtils.LINE_SEPARATOR + "  age=25"
            + SystemUtils.LINE_SEPARATOR + "  smoker=true"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(p).append("name", p.name).append("age", p.age).append(
            "smoker", p.smoker).toString());
  }

  @Test
  public void testLong() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  3"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(3L).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append("a", 3L).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3"
        + SystemUtils.LINE_SEPARATOR + "  b=4" + SystemUtils.LINE_SEPARATOR
        + "]", builder.reset(base).append("a", 3L).append("b", 4L).toString());
  }

  @Test
  public void testObjectArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    Object[] array = {null, base, new int[]{3, 6}};

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR
            + "  {<null>,5,{3,6}}" + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR
            + "  {<null>,5,{3,6}}" + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[] array = {1, 2, -3, 4};
    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {1,2,-3,4}"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {1,2,-3,4}"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArrayArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[][] array = {{1, 2}, null, {5}};
    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR
            + "  {{1,2},<null>,{5}}" + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR
            + "  {{1,2},<null>,{5}}" + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>"
            + SystemUtils.LINE_SEPARATOR + "]",
        builder.reset(base).append((Object) array).toString());
  }
}
