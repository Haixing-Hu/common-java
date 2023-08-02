////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.tostring;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for the ToStringBuilder class.
 *
 * @author Haixing Hu
 */
public class ToStringBuilderTest {

  private final Integer base = 5;
  private final String baseStr = base.getClass().getName() + "@"
      + Integer.toHexString(System.identityHashCode(base));

  @BeforeEach
  public void setUp() {
    ToStringStyle.setDefault(DefaultToStringStyle.INSTANCE);
  }

  @Test
  public void testConstructorEx1() {
    assertEquals("<null>", new ToStringBuilder().toString());
  }

  @Test
  public void testGetSetDefault() {
    final ToStringStyle defaultStyle = ToStringStyle.getDefault();
    assertNotNull(defaultStyle);
  }

  @Test
  public void testBlank() {
    assertEquals(baseStr + "[]", new ToStringBuilder(base).toString());
  }

  /**
   * Create the same toString() as Object.toString().
   *
   * @param o
   *          the object to create the string for.
   * @return a String in the Object.toString format.
   */
  private String toBaseString(final Object o) {
    return o.getClass().getName() + "@"
        + Integer.toHexString(System.identityHashCode(o));
  }

  @Test
  public void testAppendSuper() {
    assertEquals(baseStr + "[]",
        new ToStringBuilder(base).appendSuper("Integer@8888[]").toString());

    assertEquals(
        baseStr + "[<null>]",
        new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").toString());

    assertEquals(
        baseStr + "[a=\"hello\"]",
        new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a",
            "hello").toString());

    assertEquals(
        baseStr + "[<null>,a=\"hello\"]",
        new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").append(
            "a", "hello").toString());

    assertEquals(
        baseStr + "[a=\"hello\"]",
        new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
  }

  @Test
  public void testAppendToString() {
    assertEquals(baseStr + "[]",
        new ToStringBuilder(base).appendToString("Integer@8888[]").toString());

    assertEquals(
        baseStr + "[<null>]",
        new ToStringBuilder(base).appendToString("Integer@8888[<null>]").toString());

    assertEquals(
        baseStr + "[a=\"hello\"]",
        new ToStringBuilder(base).appendToString("Integer@8888[]").append("a",
            "hello").toString());

    assertEquals(
        baseStr + "[<null>,a=\"hello\"]",
        new ToStringBuilder(base).appendToString("Integer@8888[<null>]").append(
            "a", "hello").toString());

    assertEquals(
        baseStr + "[a=\"hello\"]",
        new ToStringBuilder(base).appendToString(null).append("a", "hello").toString());
  }

  @Test
  public void testObject() {
    final Integer i3 = 3;
    final Integer i4 = 4;

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) null).toString());

    assertEquals(baseStr + "[3]",
        new ToStringBuilder(base).append(i3).toString());

    assertEquals(baseStr + "[a=<null>]",
        new ToStringBuilder(base).append("a", (Object) null).toString());

    assertEquals(baseStr + "[a=3]",
        new ToStringBuilder(base).append("a", i3).toString());

    assertEquals(baseStr + "[a=3,b=4]",
        new ToStringBuilder(base).append("a", i3).append("b", i4).toString());

    assertEquals(baseStr + "[a=<Integer>]",
        new ToStringBuilder(base).append("a", i3, false).toString());

    assertEquals(
        baseStr + "[a=<size=0>]",
        new ToStringBuilder(base).append("a", new ArrayList<Byte>(), false).toString());

    assertEquals(
        baseStr + "[a=[]]",
        new ToStringBuilder(base).append("a", new ArrayList<Byte>(), true).toString());

    assertEquals(
        baseStr + "[a=<size=0>]",
        new ToStringBuilder(base).append("a", new HashMap<Byte, Byte>(), false).toString());

    assertEquals(
        baseStr + "[a={}]",
        new ToStringBuilder(base).append("a", new HashMap<Byte, Byte>(), true).toString());

    assertEquals(
        baseStr + "[a=<size=0>]",
        new ToStringBuilder(base).append("a", (Object) new String[0], false).toString());

    assertEquals(
        baseStr + "[a={}]",
        new ToStringBuilder(base).append("a", (Object) new String[0], true).toString());
  }

  @Test
  public void testLong() {
    assertEquals(baseStr + "[3]",
        new ToStringBuilder(base).append(3L).toString());

    assertEquals(baseStr + "[a=3]",
        new ToStringBuilder(base).append("a", 3L).toString());

    assertEquals(baseStr + "[a=3,b=4]",
        new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
  }

  @Test
  public void testInt() {
    assertEquals(baseStr + "[3]",
        new ToStringBuilder(base).append(3).toString());

    assertEquals(baseStr + "[a=3]",
        new ToStringBuilder(base).append("a", 3).toString());

    assertEquals(
        baseStr + "[a=3,b=4]",
        new ToStringBuilder(base).append("a", 3).append("b", 4).toString());
  }

  @Test
  public void testShort() {
    assertEquals(baseStr + "[3]",
        new ToStringBuilder(base).append((short) 3).toString());

    assertEquals(baseStr + "[a=3]",
        new ToStringBuilder(base).append("a", (short) 3).toString());

    assertEquals(
        baseStr + "[a=3,b=4]",
        new ToStringBuilder(base).append("a", (short) 3).append("b", (short) 4).toString());
  }

  @Test
  public void testChar() {
    assertEquals(baseStr + "['A'\\u0041]",
        new ToStringBuilder(base).append((char) 65).toString());

    assertEquals(baseStr + "[a='A'\\u0041]",
        new ToStringBuilder(base).append("a", (char) 65).toString());

    assertEquals(
        baseStr + "[a='A'\\u0041,b='B'\\u0042]",
        new ToStringBuilder(base).append("a", (char) 65).append("b", (char) 66).toString());
  }

  @Test
  public void testByte() {
    assertEquals(baseStr + "[3]",
        new ToStringBuilder(base).append((byte) 3).toString());

    assertEquals(baseStr + "[a=3]",
        new ToStringBuilder(base).append("a", (byte) 3).toString());

    assertEquals(
        baseStr + "[a=3,b=4]",
        new ToStringBuilder(base).append("a", (byte) 3).append("b", (byte) 4).toString());
  }

  @Test
  public void testDouble() {
    assertEquals(baseStr + "[3.2]",
        new ToStringBuilder(base).append(3.2).toString());

    assertEquals(baseStr + "[a=3.2]",
        new ToStringBuilder(base).append("a", 3.2).toString());

    assertEquals(
        baseStr + "[a=3.2,b=4.3]",
        new ToStringBuilder(base).append("a", 3.2).append("b",
            4.3).toString());
  }

  @Test
  public void testFloat() {
    assertEquals(baseStr + "[3.2]",
        new ToStringBuilder(base).append(3.2f).toString());

    assertEquals(baseStr + "[a=3.2]",
        new ToStringBuilder(base).append("a", 3.2f).toString());

    assertEquals(
        baseStr + "[a=3.2,b=4.3]",
        new ToStringBuilder(base).append("a", 3.2f)
                                 .append("b", 4.3f).toString());
  }

  @Test
  public void testBoolean() {
    assertEquals(baseStr + "[true]",
        new ToStringBuilder(base).append(true).toString());

    assertEquals(baseStr + "[a=true]",
        new ToStringBuilder(base).append("a", true).toString());

    assertEquals(
        baseStr + "[a=true,b=false]",
        new ToStringBuilder(base).append("a", true).append("b", false).toString());
  }

  @Test
  public void testString() {
    assertEquals(baseStr + "[\"\"]",
        new ToStringBuilder(base).append("").toString());

    assertEquals(baseStr + "[a=\"abc\"]",
        new ToStringBuilder(base).append("a", "abc").toString());

    assertEquals(
        baseStr + "[a=\"a\",b=\"\"]",
        new ToStringBuilder(base)
            .append("a", "a")
            .append("b", "")
            .toString());
  }

  @Test
  public void testObjectArray() {
    Object[] array = new Object[] { null, base, new int[] { 3, 6 } };

    assertEquals(baseStr + "[{<null>,5,{3,6}}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{<null>,5,{3,6}}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testLongArray() {
    long[] array = new long[] { 1, 2, - 3, 4 };
    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testIntArray() {
    int[] array = new int[] { 1, 2, - 3, 4 };
    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testShortArray() {
    short[] array = new short[] { 1, 2, - 3, 4 };
    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testByteArray() {
    byte[] array = new byte[] { 1, 2, - 3, 4 };
    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{1,2,-3,4}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testCharArray() {
    char[] array = new char[] { 'A', '2', '_', 'D' };
    assertEquals(baseStr + "[{'A'\\u0041,'2'\\u0032,'_'\\u005F,'D'\\u0044}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{'A'\\u0041,'2'\\u0032,'_'\\u005F,'D'\\u0044}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testDoubleArray() {
    double[] array = new double[] { 1.0, 2.9876, - 3.00001, 4.3 };
    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(
        base).append(array).toString());

    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(
        base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testFloatArray() {
    float[] array = new float[] { 1.0f, 2.9876f, - 3.00001f, 4.3f };
    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(
        base).append(array).toString());

    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(
        base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testBooleanArray() {
    boolean[] array = new boolean[] { true, false, false };
    assertEquals(baseStr + "[{true,false,false}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{true,false,false}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testLongArrayArray() {
    long[][] array = new long[][] { { 1, 2 }, null, { 5 } };
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testIntArrayArray() {
    int[][] array = new int[][] { { 1, 2 }, null, { 5 } };
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testShortArrayArray() {
    short[][] array = new short[][] { { 1, 2 }, null, { 5 } };
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testByteArrayArray() {
    byte[][] array = new byte[][] { { 1, 2 }, null, { 5 } };
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{{1,2},<null>,{5}}]",
        new ToStringBuilder(base).append((Object) array).toString());

    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testCharArrayArray() {
    char[][] array = new char[][] { { 'A', 'B' }, null, { 'p' } };
    assertEquals(baseStr + "[{{'A'\\u0041,'B'\\u0042},<null>,{'p'\\u0070}}]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[{{'A'\\u0041,'B'\\u0042},<null>,{'p'\\u0070}}]",
        new ToStringBuilder(base).append((Object) array).toString());
    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());

    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testDoubleArrayArray() {
    double[][] array = new double[][] { { 1.0, 2.29686 }, null, { Double.NaN } };
    assertEquals(baseStr + "[{{1.0,2.29686},<null>,{NaN}}]",
        new ToStringBuilder(base).append(array).toString());
    assertEquals(baseStr + "[{{1.0,2.29686},<null>,{NaN}}]",
        new ToStringBuilder(base).append((Object) array).toString());
    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testFloatArrayArray() {
    float[][] array = new float[][] { { 1.0f, 2.29686f }, null, { Float.NaN } };
    assertEquals(baseStr + "[{{1.0,2.29686},<null>,{NaN}}]",
        new ToStringBuilder(base).append(array).toString());
    assertEquals(baseStr + "[{{1.0,2.29686},<null>,{NaN}}]",
        new ToStringBuilder(base).append((Object) array).toString());
    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testBooleanArrayArray() {
    boolean[][] array = new boolean[][] { { true, false }, null, { false } };
    assertEquals(baseStr + "[{{true,false},<null>,{false}}]",
        new ToStringBuilder(base).append(array).toString());
    assertEquals(baseStr + "[{{true,false},<null>,{false}}]",
        new ToStringBuilder(base).append((Object) array).toString());
    array = null;
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append(array).toString());
    assertEquals(baseStr + "[<null>]",
        new ToStringBuilder(base).append((Object) array).toString());
  }

  @Test
  public void testObjectCycle() {
    final ObjectCycle a = new ObjectCycle();
    final ObjectCycle b = new ObjectCycle();
    a.obj = b;
    b.obj = a;

    final String expected = toBaseString(a) + "[" + toBaseString(b) + "["
        + toBaseString(a) + "]]";
    assertEquals(expected, a.toString());
  }

  static class ObjectCycle {
    Object obj;

    @Override
    public String toString() {
      return new ToStringBuilder(this).append(obj).toString();
    }
  }

  /**
   * Points out failure to print anything from appendToString methods using
   * MULTI_LINE.
   */
  class MultiLineTestObject {
    Integer i = 31337;

    @Override
    public String toString() {
      return new ToStringBuilder(this).append("testInt", i).toString();
    }
  }

  @Test
  public void testAppendToStringUsingMultiLineStyle() {
    final MultiLineTestObject obj = new MultiLineTestObject();
    final ToStringBuilder builder = new ToStringBuilder(MultiLineToStringStyle.INSTANCE);
    builder.reset(this).appendToString(obj.toString());
    assertEquals(builder.toString().indexOf("testInt=31337"), - 1);
  }

}
