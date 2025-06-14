////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.lang.ClassUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the ToStringBuilder and ShortPrefixToStringStyle class.
 *
 * @author Haixing Hu
 */
public class ShortPrefixToStringStyleTest {

  private final Integer base = 5;
  private final String baseStr = "Integer";

  private static final ToStringStyle STYLE = ShortPrefixToStringStyle.INSTANCE;

  @Test
  public void testBlank() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr + "[]", builder.reset(base).toString());
  }

  @Test
  public void testAppendSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    assertEquals(baseStr + "[]",
        builder.reset(base).appendSuper("Integer@8888[]").toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).appendSuper("Integer@8888[<null>]").toString());

    assertEquals(
        baseStr + "[a=\"hello\"]",
        builder.reset(base).appendSuper("Integer@8888[]").append("a", "hello").toString());

    assertEquals(
        baseStr + "[<null>,a=\"hello\"]",
        builder.reset(base).appendSuper("Integer@8888[<null>]").append("a",
            "hello").toString());

    assertEquals(baseStr + "[a=\"hello\"]",
        builder.reset(base).appendSuper(null).append("a", "hello").toString());
  }

  @Test
  public void testObject() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Integer i3 = 3;
    final Integer i4 = 4;

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) null).toString());

    assertEquals(baseStr + "[3]", builder.reset(base).append(i3).toString());

    assertEquals(baseStr + "[a=<null>]",
        builder.reset(base).append("a", (Object) null).toString());

    assertEquals(baseStr + "[a=3]",
        builder.reset(base).append("a", i3).toString());

    assertEquals(baseStr + "[a=3,b=4]",
        builder.reset(base).append("a", i3).append("b", i4).toString());

    assertEquals(baseStr + "[a=<Integer>]",
        builder.reset(base).append("a", i3, false).toString());

    assertEquals(
        baseStr + "[a=<size=0>]",
        builder.reset(base).append("a", new ArrayList<Byte>(), false).toString());

    assertEquals(baseStr + "[a=[]]",
        builder.reset(base).append("a", new ArrayList<Byte>(), true).toString());

    assertEquals(
        baseStr + "[a=<size=0>]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), false).toString());

    assertEquals(
        baseStr + "[a={}]",
        builder.reset(base).append("a", new HashMap<Byte, Byte>(), true).toString());

    assertEquals(
        baseStr + "[a=<size=0>]",
        builder.reset(base).append("a", (Object) new String[0], false).toString());

    assertEquals(
        baseStr + "[a={}]",
        builder.reset(base).append("a", (Object) new String[0], true).toString());
  }

  @Test
  public void testPerson() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final ToStringStyleTest.Person p = new ToStringStyleTest.Person();
    p.name = "John Q. Public";
    p.age = 45;
    p.smoker = true;
    final String pBaseStr = ClassUtils.getShortClassName(p.getClass());

    assertEquals(
        pBaseStr + "[name=\"John Q. Public\",age=45,smoker=true]",
        builder.reset(p).append("name", p.name).append("age", p.age).append(
            "smoker", p.smoker).toString());
  }

  @Test
  public void testLong() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    assertEquals(baseStr + "[3]", builder.reset(base).append(3L).toString());

    assertEquals(baseStr + "[a=3]",
        builder.reset(base).append("a", 3L).toString());

    assertEquals(baseStr + "[a=3,b=4]",
        builder.reset(base).append("a", 3L).append("b", 4L).toString());
  }

  @Test
  public void testObjectArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    Object[] array = new Object[] { null, base, new int[] { 3, 6 } };

    assertEquals(baseStr + "[{<null>,5,{3,6}}]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[{<null>,5,{3,6}}]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[] array = new long[] { 1, 2, - 3, 4 };
    assertEquals(baseStr + "[{1,2,-3,4}]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[{1,2,-3,4}]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) array).toString());
  }

  @Test
  public void testLongArrayArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[][] array = new long[][] { { 1, 2 }, null, { 5 } };

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        builder.reset(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        builder.reset(base).append((Object) array).toString());
  }

}