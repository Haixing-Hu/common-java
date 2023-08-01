////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * Unit test for the ToStringBuilder and StandardToStringStyle class.
 *
 * @author Haixing Hu
 */
public class StandardToStringStyleTest {

  private final Integer base = 5;
  private final String baseStr = "Integer";

  private static final ToStringStyle STYLE;

  static {
    STYLE = new StandardToStringStyle();
    STYLE.setUseShortClassName(true);
    STYLE.setUseIdentityHashCode(false);
    STYLE.setArrayStart("[");
    STYLE.setArraySeparator(", ");
    STYLE.setArrayEnd("]");
    STYLE.setNullText("%NULL%");
    STYLE.setSizeStartText("%SIZE=");
    STYLE.setSizeEndText("%");
    STYLE.setSummaryObjectStartText("%");
    STYLE.setSummaryObjectEndText("%");
  }

  @Test
  public void testBlank() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    builder.reset(base);
    assertEquals(baseStr + "[]", builder.toString());
  }

  @Test
  public void testAppendSuper() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    builder.reset(base);
    builder.appendSuper("Integer@8888[]");
    assertEquals(baseStr + "[]", builder.toString());

    builder.reset(base);
    builder.appendSuper("Integer@8888[%NULL%]");
    assertEquals(baseStr + "[%NULL%]", builder.toString());

    builder.reset(base);
    builder.appendSuper("Integer@8888[]");
    builder.append("a", "hello");
    assertEquals(baseStr + "[a=\"hello\"]", builder.toString());

    builder.reset(base);
    builder.appendSuper("Integer@8888[%NULL%]");
    builder.append("a", "hello");
    assertEquals(baseStr + "[%NULL%,a=\"hello\"]", builder.toString());

    builder.reset(base);
    builder.appendSuper(null);
    builder.append("a", "hello");
    assertEquals(baseStr + "[a=\"hello\"]", builder.toString());
  }

  @Test
  public void testObject() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    final Integer i3 = 3;
    final Integer i4 = 4;

    builder.reset(base);
    builder.append((Object) null);
    assertEquals(baseStr + "[%NULL%]", builder.toString());

    builder.reset(base);
    builder.append(i3);
    assertEquals(baseStr + "[3]", builder.toString());

    builder.reset(base);
    builder.append("a", (Object) null);
    assertEquals(baseStr + "[a=%NULL%]", builder.toString());

    builder.reset(base);
    builder.append("a", i3);
    assertEquals(baseStr + "[a=3]", builder.toString());

    builder.reset(base);
    builder.append("a", i3);
    builder.append("b", i4);
    assertEquals(baseStr + "[a=3,b=4]", builder.toString());

    builder.reset(base);
    builder.append("a", i3, false);
    assertEquals(baseStr + "[a=%Integer%]", builder.toString());

    builder.reset(base);
    builder.append("a", new ArrayList<Integer>(), false);
    assertEquals(baseStr + "[a=%SIZE=0%]", builder.toString());

    builder.reset(base);
    builder.append("a", new ArrayList<Integer>(), true);
    assertEquals(baseStr + "[a=[]]", builder.toString());

    builder.reset(base);
    builder.append("a", new HashMap<String, Integer>(), false);
    assertEquals(baseStr + "[a=%SIZE=0%]", builder.toString());

    builder.reset(base);
    builder.append("a", new HashMap<String, Integer>(), true);
    assertEquals(baseStr + "[a={}]", builder.toString());

    builder.reset(base);
    builder.append("a", (Object) new String[0], false);
    assertEquals(baseStr + "[a=%SIZE=0%]", builder.toString());

    builder.reset(base);
    builder.append("a", (Object) new String[0], true);
    assertEquals(baseStr + "[a=[]]", builder.toString());
  }

  @Test
  public void testPerson() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    final ToStringStyleTest.Person p = new ToStringStyleTest.Person();
    p.name = "Suzy Queue";
    p.age = 19;
    p.smoker = false;
    final String pBaseStr = "ToStringStyleTest.Person";
    builder.reset(p);
    builder.append("name", p.name);
    builder.append("age", p.age);
    builder.append("smoker", p.smoker);
    assertEquals(pBaseStr + "[name=\"Suzy Queue\",age=19,smoker=false]",
        builder.toString());
  }

  @Test
  public void testLong() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);

    builder.reset(base);
    builder.append(3L);
    assertEquals(baseStr + "[3]", builder.toString());

    builder.reset(base);
    builder.append("a", 3L);
    assertEquals(baseStr + "[a=3]", builder.toString());

    builder.reset(base);
    builder.append("a", 3L);
    builder.append("b", 4L);
    assertEquals(baseStr + "[a=3,b=4]", builder.toString());
  }

  @Test
  public void testObjectArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    Object[] array = new Object[]{null, base, new int[]{3, 6}};

    builder.reset(base);
    builder.append(array);
    assertEquals(baseStr + "[[%NULL%, 5, [3, 6]]]", builder.toString());

    builder.reset(base);
    builder.append((Object) array);
    assertEquals(baseStr + "[[%NULL%, 5, [3, 6]]]", builder.toString());

    array = null;
    builder.reset(base);
    builder.append(array);
    assertEquals(baseStr + "[%NULL%]", builder.toString());

    builder.reset(base);
    builder.append((Object) array);
    assertEquals(baseStr + "[%NULL%]", builder.toString());
  }

  @Test
  public void testLongArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[] array = new long[]{1, 2, -3, 4};

    builder.reset(base);
    builder.append(array);
    assertEquals(baseStr + "[[1, 2, -3, 4]]", builder.toString());

    builder.reset(base);
    builder.append((Object) array);
    assertEquals(baseStr + "[[1, 2, -3, 4]]", builder.toString());

    array = null;

    builder.reset(base);
    builder.append(array);
    assertEquals(baseStr + "[%NULL%]", builder.toString());

    builder.reset(base);
    builder.append((Object) array);
    assertEquals(baseStr + "[%NULL%]", builder.toString());
  }

  @Test
  public void testLongArrayArray() {
    final ToStringBuilder builder = new ToStringBuilder(STYLE);
    long[][] array = new long[][]{{1, 2}, null, {5}};

    builder.reset(base);
    builder.append(array);
    assertEquals(baseStr + "[[[1, 2], %NULL%, [5]]]", builder.toString());

    builder.reset(base);
    builder.append((Object) array);
    assertEquals(baseStr + "[[[1, 2], %NULL%, [5]]]", builder.toString());

    array = null;
    builder.reset(base);
    builder.append(array);
    assertEquals(baseStr + "[%NULL%]", builder.toString());

    builder.reset(base);
    builder.append((Object) array);
    assertEquals(baseStr + "[%NULL%]", builder.toString());
  }

}
