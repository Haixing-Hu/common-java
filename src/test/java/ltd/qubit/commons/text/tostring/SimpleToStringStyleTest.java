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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the ToStringBuilder and SimpleToStringStyle class.
 *
 * @author Haixing Hu
 */
public class SimpleToStringStyleTest {

  private final Integer base = 5;

  private static final ToStringStyle STYLE = SimpleToStringStyle.INSTANCE;

  @Test
  public void testBlank() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals("", builder.reset(base).toString());
  }

  @Test
  public void testAppendSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    assertEquals("", builder.reset(base).appendSuper("").toString());

    assertEquals("<null>",
        builder.reset(base).appendSuper("<null>").toString());

    assertEquals("\"hello\"",
        builder.reset(base).appendSuper("").append("a", "hello").toString());

    assertEquals(
        "<null>,\"hello\"",
        builder.reset(base).appendSuper("<null>").append("a", "hello")
               .toString());

    assertEquals("\"hello\"",
        builder.reset(base).appendSuper(null).append("a", "hello").toString());
  }

  @Test
  public void testObject() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Integer i3 = 3;
    final Integer i4 = 4;

    assertEquals("<null>",
        builder.reset(base).append((Object) null).toString());

    assertEquals("3", builder.reset(base).append(i3).toString());

    assertEquals("<null>",
        builder.reset(base).append("a", (Object) null).toString());

    assertEquals("3", builder.reset(base).append("a", i3).toString());

    assertEquals("3,4",
        builder.reset(base).append("a", i3).append("b", i4).toString());

    assertEquals("<Integer>",
        builder.reset(base).append("a", i3, false).toString());

    assertEquals(
        "<size=0>",
        builder.reset(base).append("a", new ArrayList<String>(), false)
               .toString());

    assertEquals(
        "[]",
        builder.reset(base).append("a", new ArrayList<String>(), true)
               .toString());

    assertEquals(
        "<size=0>",
        builder.reset(base).append("a", new HashMap<String, Integer>(), false)
               .toString());

    assertEquals(
        "{}",
        builder.reset(base).append("a", new HashMap<String, Integer>(), true)
               .toString());

    assertEquals(
        "<size=0>",
        builder.reset(base).append("a", (Object) new String[0], false)
               .toString());

    assertEquals(
        "{}",
        builder.reset(base).append("a", (Object) new String[0], true)
               .toString());
  }

  @Test
  public void testPerson() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final ToStringStyleTest.Person p = new ToStringStyleTest.Person();
    p.name = "Jane Q. Public";
    p.age = 47;
    p.smoker = false;

    assertEquals(
        "\"Jane Q. Public\",47,false",
        builder.reset(p).append("name", p.name).append("age", p.age).append(
            "smoker", p.smoker).toString());
  }

  @Test
  public void testLong() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals("3", builder.reset(base).append(3L).toString());

    assertEquals("3", builder.reset(base).append("a", 3L).toString());

    assertEquals("3,4",
        builder.reset(base).append("a", 3L).append("b", 4L).toString());
  }

  @Test
  public void testObjectArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    Object[] array = new Object[]{null, base, new int[]{3, 6}};

    assertEquals("{<null>,5,{3,6}}",
        builder.reset(base).append(array).toString());

    assertEquals("{<null>,5,{3,6}}",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals("<null>", builder.reset(base).append(array).toString());

    assertEquals("<null>",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[] array = new long[]{1, 2, -3, 4};
    assertEquals("{1,2,-3,4}", builder.reset(base).append(array).toString());

    assertEquals("{1,2,-3,4}",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals("<null>", builder.reset(base).append(array).toString());

    assertEquals("<null>",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArrayArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[][] array = new long[][]{{1, 2}, null, {5}};
    assertEquals("{{1,2},<null>,{5}}",
        builder.reset(base).append(array).toString());

    assertEquals("{{1,2},<null>,{5}}",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals("<null>", builder.reset(base).append(array).toString());

    assertEquals("<null>",
        builder.reset(base).append((Object) array).toString());
  }

}
