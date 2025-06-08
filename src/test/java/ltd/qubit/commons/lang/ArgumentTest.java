////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 对 {@link Argument} 类的单元测试。
 *
 * @author AI Assistant
 */
public class ArgumentTest {

  // -------------------------------------------------------------------------
  // checkBounds方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testCheckBounds() {
    Argument.checkBounds(0, 5, 10); // 在边界内，不应抛出异常
    Argument.checkBounds(5, 5, 10); // 在边界处，不应抛出异常

    // 测试参数越界的情况
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.checkBounds(-1, 5, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.checkBounds(0, -1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.checkBounds(6, 5, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.checkBounds(10, 1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.checkBounds(Integer.MAX_VALUE, 1, 10));
  }

  // -------------------------------------------------------------------------
  // requireNonNull方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireNonNull() {
    final String testObj = "test";
    assertSame(testObj, Argument.requireNonNull("testObj", testObj));

    assertThrows(NullPointerException.class, () -> Argument.requireNonNull("nullObj", null));
  }

  // -------------------------------------------------------------------------
  // requireNonBlank方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireNonBlank() {
    final String validString = "valid";
    assertEquals(validString, Argument.requireNonBlank("validString", validString));

    assertThrows(NullPointerException.class, () -> Argument.requireNonBlank("nullString", null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonBlank("emptyString", ""));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonBlank("blankString", "   "));
  }

  // -------------------------------------------------------------------------
  // requireNonEmpty方法测试 (针对各种数组类型)
  // -------------------------------------------------------------------------

  @Test
  public void testRequireNonEmptyBooleanArray() {
    final boolean[] validArray = {true, false};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (boolean[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new boolean[0]));
  }

  @Test
  public void testRequireNonEmptyCharArray() {
    final char[] validArray = {'a', 'b'};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (char[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new char[0]));
  }

  @Test
  public void testRequireNonEmptyByteArray() {
    final byte[] validArray = {1, 2};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (byte[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new byte[0]));
  }

  @Test
  public void testRequireNonEmptyShortArray() {
    final short[] validArray = {1, 2};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (short[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new short[0]));
  }

  @Test
  public void testRequireNonEmptyIntArray() {
    final int[] validArray = {1, 2};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (int[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new int[0]));
  }

  @Test
  public void testRequireNonEmptyLongArray() {
    final long[] validArray = {1L, 2L};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (long[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new long[0]));
  }

  @Test
  public void testRequireNonEmptyFloatArray() {
    final float[] validArray = {1.0f, 2.0f};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (float[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new float[0]));
  }

  @Test
  public void testRequireNonEmptyDoubleArray() {
    final double[] validArray = {1.0, 2.0};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (double[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new double[0]));
  }

  @Test
  public void testRequireNonEmptyObjectArray() {
    final String[] validArray = {"a", "b"};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (String[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new String[0]));
  }

  // -------------------------------------------------------------------------
  // requireNonEmpty方法测试 (针对String和容器类型)
  // -------------------------------------------------------------------------

  @Test
  public void testRequireNonEmptyString() {
    final String validString = "valid";
    assertEquals(validString, Argument.requireNonEmpty("validString", validString));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullString", (String) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyString", ""));
  }

  @Test
  public void testRequireNonEmptyList() {
    final List<String> validList = new ArrayList<>();
    validList.add("item");
    assertSame(validList, Argument.requireNonEmpty("validList", validList));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullList", (List<String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyList", new ArrayList<String>()));
  }

  @Test
  public void testRequireNonEmptySet() {
    final Set<String> validSet = new HashSet<>();
    validSet.add("item");
    assertSame(validSet, Argument.requireNonEmpty("validSet", validSet));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullSet", (Set<String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptySet", new HashSet<String>()));
  }

  @Test
  public void testRequireNonEmptyMap() {
    final Map<String, String> validMap = new HashMap<>();
    validMap.put("key", "value");
    assertSame(validMap, Argument.requireNonEmpty("validMap", validMap));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullMap", (Map<String, String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyMap", new HashMap<String, String>()));
  }

  @Test
  public void testRequireNonEmptyCollection() {
    final Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item");
    assertSame(validCollection, Argument.requireNonEmpty("validCollection", validCollection));

    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullCollection", (Collection<String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyCollection", new ArrayList<String>()));
  }

  // -------------------------------------------------------------------------
  // requireLengthBe方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireLengthBe() {
    // 测试数组长度校验
    assertArrayEquals(new int[]{1, 2}, Argument.requireLengthBe("intArray", new int[]{1, 2}, 2));
    assertThrows(NullPointerException.class, () -> Argument.requireLengthBe("nullArray", (int[]) null, 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLengthBe("wrongLength", new int[]{1, 2, 3}, 2));

    // 测试字符串长度校验
    assertEquals("ab", Argument.requireLengthBe("string", "ab", 2));
    assertThrows(NullPointerException.class, () -> Argument.requireLengthBe("nullString", (String) null, 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLengthBe("wrongLength", "abc", 2));
  }

  // -------------------------------------------------------------------------
  // requireSizeBe方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireSizeBe() {
    final Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item1");
    validCollection.add("item2");

    assertSame(validCollection, Argument.requireSizeBe("validCollection", validCollection, 2));

    assertThrows(NullPointerException.class, () -> Argument.requireSizeBe("nullCollection", (Collection<String>) null, 2));

    final Collection<String> wrongSizeCollection = new ArrayList<>();
    wrongSizeCollection.add("item1");
    assertThrows(IllegalArgumentException.class, () -> Argument.requireSizeBe("wrongSizeCollection", wrongSizeCollection, 2));
  }

  // -------------------------------------------------------------------------
  // requireLengthAtLeast和requireLengthAtMost方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireLengthAtLeast() {
    assertEquals("abc", Argument.requireLengthAtLeast("string", "abc", 2));
    assertThrows(NullPointerException.class, () -> Argument.requireLengthAtLeast("nullString", (String) null, 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLengthAtLeast("tooShort", "a", 2));
  }

  @Test
  public void testRequireLengthAtMost() {
    assertEquals("a", Argument.requireLengthAtMost("string", "a", 2));
    assertThrows(NullPointerException.class, () -> Argument.requireLengthAtMost("nullString", (String) null, 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLengthAtMost("tooLong", "abc", 2));
  }

  // -------------------------------------------------------------------------
  // requireSizeAtLeast和requireSizeAtMost方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireSizeAtLeast() {
    final Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item1");
    validCollection.add("item2");

    assertSame(validCollection, Argument.requireSizeAtLeast("validCollection", validCollection, 2));
    assertSame(validCollection, Argument.requireSizeAtLeast("validCollection", validCollection, 1));

    assertThrows(NullPointerException.class, () -> Argument.requireSizeAtLeast("nullCollection", (Collection<String>) null, 2));

    final Collection<String> tooSmallCollection = new ArrayList<>();
    tooSmallCollection.add("item1");
    assertThrows(IllegalArgumentException.class, () -> Argument.requireSizeAtLeast("tooSmallCollection", tooSmallCollection, 2));
  }

  @Test
  public void testRequireSizeAtMost() {
    final Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item1");

    assertSame(validCollection, Argument.requireSizeAtMost("validCollection", validCollection, 2));
    assertSame(validCollection, Argument.requireSizeAtMost("validCollection", validCollection, 1));

    assertThrows(NullPointerException.class, () -> Argument.requireSizeAtMost("nullCollection", (Collection<String>) null, 2));

    final Collection<String> tooBigCollection = new ArrayList<>();
    tooBigCollection.add("item1");
    tooBigCollection.add("item2");
    tooBigCollection.add("item3");
    assertThrows(IllegalArgumentException.class, () -> Argument.requireSizeAtMost("tooBigCollection", tooBigCollection, 2));
  }

  // -------------------------------------------------------------------------
  // 数值相关检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireZero() {
    // 测试各个数值类型的零值
    assertEquals((byte) 0, Argument.requireZero("zeroByte", (byte) 0));
    assertEquals((short) 0, Argument.requireZero("zeroShort", (short) 0));
    assertEquals(0, Argument.requireZero("zeroInt", 0));
    assertEquals(0L, Argument.requireZero("zeroLong", 0L));
    assertEquals(0.0f, Argument.requireZero("zeroFloat", 0.0f));
    assertEquals(0.0, Argument.requireZero("zeroDouble", 0.0));

    // 测试非零值（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroByte", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroShort", (short) -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroInt", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroLong", -1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroFloat", 0.1f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroDouble", -0.1));

    // 测试接近零但不是零的值（针对浮点数）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("almostZeroFloat", 0.0015f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("almostZeroDouble", 0.0000015));
  }

  @Test
  public void testRequireNonZero() {
    // 测试各个数值类型的非零值
    assertEquals((byte) 1, Argument.requireNonZero("nonZeroByte", (byte) 1));
    assertEquals((byte) -1, Argument.requireNonZero("nonZeroByte", (byte) -1));
    assertEquals((short) 1, Argument.requireNonZero("nonZeroShort", (short) 1));
    assertEquals((short) -1, Argument.requireNonZero("nonZeroShort", (short) -1));
    assertEquals(1, Argument.requireNonZero("nonZeroInt", 1));
    assertEquals(-1, Argument.requireNonZero("nonZeroInt", -1));
    assertEquals(1L, Argument.requireNonZero("nonZeroLong", 1L));
    assertEquals(-1L, Argument.requireNonZero("nonZeroLong", -1L));
    assertEquals(0.1f, Argument.requireNonZero("nonZeroFloat", 0.1f));
    assertEquals(-0.1f, Argument.requireNonZero("nonZeroFloat", -0.1f));
    assertEquals(0.1, Argument.requireNonZero("nonZeroDouble", 0.1));
    assertEquals(-0.1, Argument.requireNonZero("nonZeroDouble", -0.1));

    // 测试最小/最大值（这些肯定是非零的）
    assertEquals(Byte.MIN_VALUE, Argument.requireNonZero("minByte", Byte.MIN_VALUE));
    assertEquals(Byte.MAX_VALUE, Argument.requireNonZero("maxByte", Byte.MAX_VALUE));
    assertEquals(Short.MIN_VALUE, Argument.requireNonZero("minShort", Short.MIN_VALUE));
    assertEquals(Short.MAX_VALUE, Argument.requireNonZero("maxShort", Short.MAX_VALUE));
    assertEquals(Integer.MIN_VALUE, Argument.requireNonZero("minInt", Integer.MIN_VALUE));
    assertEquals(Integer.MAX_VALUE, Argument.requireNonZero("maxInt", Integer.MAX_VALUE));
    assertEquals(Long.MIN_VALUE, Argument.requireNonZero("minLong", Long.MIN_VALUE));
    assertEquals(Long.MAX_VALUE, Argument.requireNonZero("maxLong", Long.MAX_VALUE));
    assertEquals(Float.MIN_VALUE, Argument.requireNonZero("minFloat", Float.MIN_VALUE));
    assertEquals(Float.MAX_VALUE, Argument.requireNonZero("maxFloat", Float.MAX_VALUE));
    assertEquals(Double.MIN_VALUE, Argument.requireNonZero("minDouble", Double.MIN_VALUE));
    assertEquals(Double.MAX_VALUE, Argument.requireNonZero("maxDouble", Double.MAX_VALUE));

    // 测试零值（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroByte", (byte) 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroShort", (short) 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroInt", 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroLong", 0L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroFloat", 0.0f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroDouble", 0.0));

    // 测试接近零但不是零的值（针对浮点数）
    assertEquals(0.000001f, Argument.requireNonZero("almostZeroFloat", 0.000001f));
    assertEquals(0.0000000001, Argument.requireNonZero("almostZeroDouble", 0.0000000001));
  }

  @Test
  public void testRequirePositive() {
    // 测试各个数值类型的正值
    assertEquals((byte) 1, Argument.requirePositive("positiveByte", (byte) 1));
    assertEquals((short) 1, Argument.requirePositive("positiveShort", (short) 1));
    assertEquals(1, Argument.requirePositive("positiveInt", 1));
    assertEquals(1L, Argument.requirePositive("positiveLong", 1L));
    assertEquals(0.1f, Argument.requirePositive("positiveFloat", 0.1f));
    assertEquals(0.1, Argument.requirePositive("positiveDouble", 0.1));

    // 测试最大值（这些肯定是正的）
    assertEquals(Byte.MAX_VALUE, Argument.requirePositive("maxByte", Byte.MAX_VALUE));
    assertEquals(Short.MAX_VALUE, Argument.requirePositive("maxShort", Short.MAX_VALUE));
    assertEquals(Integer.MAX_VALUE, Argument.requirePositive("maxInt", Integer.MAX_VALUE));
    assertEquals(Long.MAX_VALUE, Argument.requirePositive("maxLong", Long.MAX_VALUE));
    assertEquals(Float.MAX_VALUE, Argument.requirePositive("maxFloat", Float.MAX_VALUE));
    assertEquals(Double.MAX_VALUE, Argument.requirePositive("maxDouble", Double.MAX_VALUE));

    // 测试零和负值（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroByte", (byte) 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroShort", (short) 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroInt", 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroLong", 0L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroFloat", 0.0f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroDouble", 0.0));

    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeByte", (byte) -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeShort", (short) -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeInt", -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeLong", -1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeFloat", -0.1f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeDouble", -0.1));

    // 测试极小正值（针对浮点数）
    assertEquals(Float.MIN_VALUE, Argument.requirePositive("minPositiveFloat", Float.MIN_VALUE));
    assertEquals(Double.MIN_VALUE, Argument.requirePositive("minPositiveDouble", Double.MIN_VALUE));
  }

  @Test
  public void testRequireNonPositive() {
    // 测试各个数值类型的零值和负值
    assertEquals((byte) 0, Argument.requireNonPositive("zeroByte", (byte) 0));
    assertEquals((byte) -1, Argument.requireNonPositive("negativeByte", (byte) -1));
    assertEquals((short) 0, Argument.requireNonPositive("zeroShort", (short) 0));
    assertEquals((short) -1, Argument.requireNonPositive("negativeShort", (short) -1));
    assertEquals(0, Argument.requireNonPositive("zeroInt", 0));
    assertEquals(-1, Argument.requireNonPositive("negativeInt", -1));
    assertEquals(0L, Argument.requireNonPositive("zeroLong", 0L));
    assertEquals(-1L, Argument.requireNonPositive("negativeLong", -1L));
    assertEquals(0.0f, Argument.requireNonPositive("zeroFloat", 0.0f));
    assertEquals(-0.1f, Argument.requireNonPositive("negativeFloat", -0.1f));
    assertEquals(0.0, Argument.requireNonPositive("zeroDouble", 0.0));
    assertEquals(-0.1, Argument.requireNonPositive("negativeDouble", -0.1));

    // 测试最小值（这些肯定是负的）
    assertEquals(Byte.MIN_VALUE, Argument.requireNonPositive("minByte", Byte.MIN_VALUE));
    assertEquals(Short.MIN_VALUE, Argument.requireNonPositive("minShort", Short.MIN_VALUE));
    assertEquals(Integer.MIN_VALUE, Argument.requireNonPositive("minInt", Integer.MIN_VALUE));
    assertEquals(Long.MIN_VALUE, Argument.requireNonPositive("minLong", Long.MIN_VALUE));
    assertEquals(Float.NEGATIVE_INFINITY, Argument.requireNonPositive("negInfFloat", Float.NEGATIVE_INFINITY));
    assertEquals(Double.NEGATIVE_INFINITY, Argument.requireNonPositive("negInfDouble", Double.NEGATIVE_INFINITY));

    // 测试正值（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveByte", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveShort", (short) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveInt", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveLong", 1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveFloat", 0.1f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveDouble", 0.1));

    // 测试极小正值（针对浮点数）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("minPositiveFloat", Float.MIN_VALUE));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("minPositiveDouble", Double.MIN_VALUE));
  }

  @Test
  public void testRequireNegative() {
    // 测试各个数值类型的负值
    assertEquals((byte) -1, Argument.requireNegative("negativeByte", (byte) -1));
    assertEquals((short) -1, Argument.requireNegative("negativeShort", (short) -1));
    assertEquals(-1, Argument.requireNegative("negativeInt", -1));
    assertEquals(-1L, Argument.requireNegative("negativeLong", -1L));
    assertEquals(-0.1f, Argument.requireNegative("negativeFloat", -0.1f));
    assertEquals(-0.1, Argument.requireNegative("negativeDouble", -0.1));

    // 测试最小值（这些肯定是负的）
    assertEquals(Byte.MIN_VALUE, Argument.requireNegative("minByte", Byte.MIN_VALUE));
    assertEquals(Short.MIN_VALUE, Argument.requireNegative("minShort", Short.MIN_VALUE));
    assertEquals(Integer.MIN_VALUE, Argument.requireNegative("minInt", Integer.MIN_VALUE));
    assertEquals(Long.MIN_VALUE, Argument.requireNegative("minLong", Long.MIN_VALUE));
    assertEquals(Float.NEGATIVE_INFINITY, Argument.requireNegative("negInfFloat", Float.NEGATIVE_INFINITY));
    assertEquals(Double.NEGATIVE_INFINITY, Argument.requireNegative("negInfDouble", Double.NEGATIVE_INFINITY));

    // 测试零和正值（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroByte", (byte) 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroShort", (short) 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroInt", 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroLong", 0L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroFloat", 0.0f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroDouble", 0.0));

    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveByte", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveShort", (short) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveInt", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveLong", 1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveFloat", 0.1f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveDouble", 0.1));

    // 测试极小负值（针对浮点数）
    assertEquals(-Float.MIN_NORMAL, Argument.requireNegative("minNegativeFloat", -Float.MIN_NORMAL));
    assertEquals(-Double.MIN_NORMAL, Argument.requireNegative("minNegativeDouble", -Double.MIN_NORMAL));
  }

  @Test
  public void testRequireNonNegative() {
    // 测试各个数值类型的零值和正值
    assertEquals((byte) 0, Argument.requireNonNegative("zeroByte", (byte) 0));
    assertEquals((byte) 1, Argument.requireNonNegative("positiveByte", (byte) 1));
    assertEquals((short) 0, Argument.requireNonNegative("zeroShort", (short) 0));
    assertEquals((short) 1, Argument.requireNonNegative("positiveShort", (short) 1));
    assertEquals(0, Argument.requireNonNegative("zeroInt", 0));
    assertEquals(1, Argument.requireNonNegative("positiveInt", 1));
    assertEquals(0L, Argument.requireNonNegative("zeroLong", 0L));
    assertEquals(1L, Argument.requireNonNegative("positiveLong", 1L));
    assertEquals(0.0f, Argument.requireNonNegative("zeroFloat", 0.0f));
    assertEquals(0.1f, Argument.requireNonNegative("positiveFloat", 0.1f));
    assertEquals(0.0, Argument.requireNonNegative("zeroDouble", 0.0));
    assertEquals(0.1, Argument.requireNonNegative("positiveDouble", 0.1));

    // 测试最大值（这些肯定是正的）
    assertEquals(Byte.MAX_VALUE, Argument.requireNonNegative("maxByte", Byte.MAX_VALUE));
    assertEquals(Short.MAX_VALUE, Argument.requireNonNegative("maxShort", Short.MAX_VALUE));
    assertEquals(Integer.MAX_VALUE, Argument.requireNonNegative("maxInt", Integer.MAX_VALUE));
    assertEquals(Long.MAX_VALUE, Argument.requireNonNegative("maxLong", Long.MAX_VALUE));
    assertEquals(Float.MAX_VALUE, Argument.requireNonNegative("maxFloat", Float.MAX_VALUE));
    assertEquals(Double.MAX_VALUE, Argument.requireNonNegative("maxDouble", Double.MAX_VALUE));
    assertEquals(Float.POSITIVE_INFINITY, Argument.requireNonNegative("posInfFloat", Float.POSITIVE_INFINITY));
    assertEquals(Double.POSITIVE_INFINITY, Argument.requireNonNegative("posInfDouble", Double.POSITIVE_INFINITY));

    // 测试负值（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeByte", (byte) -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeShort", (short) -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeInt", -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeLong", -1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeFloat", -0.1f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeDouble", -0.1));

    // 测试极小负值（针对浮点数）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("minNegativeFloat", -Float.MIN_NORMAL));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("minNegativeDouble", -Double.MIN_NORMAL));
  }

  // -------------------------------------------------------------------------
  // 对象相同性检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireSame() {
    final Object obj = new Object();
    Argument.requireSame("obj1", obj, "obj2", obj);

    final Object anotherObj = new Object();
    assertThrows(IllegalArgumentException.class, () -> Argument.requireSame("obj1", obj, "obj2", anotherObj));
  }

  @Test
  public void testRequireNonSame() {
    final Object obj1 = new Object();
    final Object obj2 = new Object();
    Argument.requireNonSame("obj1", obj1, "obj2", obj2);

    final Object sameObj = new Object();
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonSame("obj1", sameObj, "obj2", sameObj));
  }

  // -------------------------------------------------------------------------
  // 值相等性检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireEqual() {
    // 原始类型的相等性测试
    assertEquals(true, Argument.requireEqual("bool1", true, "bool2", true));
    assertEquals(1, Argument.requireEqual("int1", 1, "int2", 1));
    assertEquals(1.0, Argument.requireEqual("double1", 1.0, "double2", 1.0, 0.0001));

    // 对象的相等性测试
    final String str1 = "test";
    final String str2 = new String("test");
    assertEquals(str1, Argument.requireEqual("str1", str1, "str2", str2));

    // 不相等的情况
    assertThrows(IllegalArgumentException.class, () -> Argument.requireEqual("bool1", true, "bool2", false));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireEqual("int1", 1, "int2", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireEqual("double1", 1.0, "double2", 1.1, 0.0001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireEqual("str1", "a", "str2", "b"));
  }

  @Test
  public void testRequireNotEqual() {
    // 原始类型的不相等测试
    assertEquals(true, Argument.requireNotEqual("bool1", true, "bool2", false));
    assertEquals(1, Argument.requireNotEqual("int1", 1, "int2", 2));
    assertEquals(1.0, Argument.requireNotEqual("double1", 1.0, "double2", 1.1, 0.0001));

    // 对象的不相等测试
    assertEquals("a", Argument.requireNotEqual("str1", "a", "str2", "b"));

    // 相等的情况
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotEqual("bool1", true, "bool2", true));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotEqual("int1", 1, "int2", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotEqual("double1", 1.0, "double2", 1.0, 0.0001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotEqual("str1", "a", "str2", "a"));
  }

  // -------------------------------------------------------------------------
  // 比较大小方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireLess() {
    // 基本数值类型测试
    assertEquals((char) 'a', Argument.requireLess("smaller", (char) 'a', "larger", (char) 'b'));
    assertEquals((byte) 1, Argument.requireLess("smaller", (byte) 1, "larger", (byte) 2));
    assertEquals((short) 1, Argument.requireLess("smaller", (short) 1, "larger", (short) 2));
    assertEquals(1, Argument.requireLess("smaller", 1, "larger", 2));
    assertEquals(1L, Argument.requireLess("smaller", 1L, "larger", 2L));

    // 浮点类型测试（包含epsilon参数）
    assertEquals(1.0f, Argument.requireLess("smaller", 1.0f, "larger", 2.0f, 0.001f));
    assertEquals(1.0, Argument.requireLess("smaller", 1.0, "larger", 2.0, 0.001));

    // 可比较对象测试
    assertEquals("a", Argument.requireLess("smaller", "a", "larger", "b"));

    // 相等的情况（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", (char) 'a', "alsoEqual", (char) 'a'));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", (byte) 1, "alsoEqual", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", (short) 1, "alsoEqual", (short) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", 1, "alsoEqual", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", 1L, "alsoEqual", 1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", 1.0f, "alsoEqual", 1.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", 1.0, "alsoEqual", 1.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("equal", "a", "alsoEqual", "a"));

    // 大于的情况（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", (char) 'b', "smaller", (char) 'a'));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", (byte) 2, "smaller", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", (short) 2, "smaller", (short) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", 2, "smaller", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", 2L, "smaller", 1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", 2.0f, "smaller", 1.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", 2.0, "smaller", 1.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("larger", "b", "smaller", "a"));

    // 接近相等但视为相等的浮点数（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("tooClose1", 1.0005f, "tooClose2", 1.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("tooClose1", 1.0, "tooClose2", 1.0, 0.001));
  }

  @Disabled
  @Test
  public void testRequireLess_edgeCase() {
    // 接近相等但仍然小于的浮点数测试
    assertEquals(1.003f, Argument.requireLess("slightlyLarger", 1.003f, "slightlySmaller", 1.0f, 0.001f));
    assertEquals(1.003, Argument.requireLess("slightlyLarger", 1.003, "slightlySmaller", 1.0, 0.001));
  }

  @Test
  public void testRequireLessEqual() {
    // 基本数值类型测试 - 小于情况
    assertEquals((char) 'a', Argument.requireLessEqual("smaller", (char) 'a', "larger", (char) 'b'));
    assertEquals((byte) 1, Argument.requireLessEqual("smaller", (byte) 1, "larger", (byte) 2));
    assertEquals((short) 1, Argument.requireLessEqual("smaller", (short) 1, "larger", (short) 2));
    assertEquals(1, Argument.requireLessEqual("smaller", 1, "larger", 2));
    assertEquals(1L, Argument.requireLessEqual("smaller", 1L, "larger", 2L));

    // 基本数值类型测试 - 等于情况
    assertEquals((char) 'a', Argument.requireLessEqual("equal", (char) 'a', "alsoEqual", (char) 'a'));
    assertEquals((byte) 1, Argument.requireLessEqual("equal", (byte) 1, "alsoEqual", (byte) 1));
    assertEquals((short) 1, Argument.requireLessEqual("equal", (short) 1, "alsoEqual", (short) 1));
    assertEquals(2, Argument.requireLessEqual("equal", 2, "alsoEqual", 2));
    assertEquals(1L, Argument.requireLessEqual("equal", 1L, "alsoEqual", 1L));

    // 浮点类型测试（包含epsilon参数）- 小于情况
    assertEquals(1.0f, Argument.requireLessEqual("smaller", 1.0f, "larger", 2.0f, 0.001f));
    assertEquals(1.0, Argument.requireLessEqual("smaller", 1.0, "larger", 2.0, 0.001));

    // 浮点类型测试（包含epsilon参数）- 等于情况
    assertEquals(1.0f, Argument.requireLessEqual("equal", 1.0f, "alsoEqual", 1.0f, 0.001f));
    assertEquals(1.0, Argument.requireLessEqual("equal", 1.0, "alsoEqual", 1.0, 0.001));

    // 可比较对象测试
    assertEquals("a", Argument.requireLessEqual("smaller", "a", "larger", "b"));
    assertEquals("a", Argument.requireLessEqual("equal", "a", "alsoEqual", "a"));

    // 大于的情况（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", (char) 'b', "smaller", (char) 'a'));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", (byte) 2, "smaller", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", (short) 2, "smaller", (short) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", 3, "smaller", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", 2L, "smaller", 1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", 2.0f, "smaller", 1.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", 2.0, "smaller", 1.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("larger", "b", "smaller", "a"));

    // 近似相等的浮点数
    assertEquals(1.0005f, Argument.requireLessEqual("almostEqual1", 1.0005f, "almostEqual2", 1.0f, 0.001f));
    assertEquals(1.0005, Argument.requireLessEqual("almostEqual1", 1.0005, "almostEqual2", 1.0, 0.001));
  }

  @Disabled
  @Test
  public void testRequireLessEqual_edgeCase() {
    // 差值恰好等于epsilon的情况
    // 注意：因为浮点数的表示，1.001f - 1.0f = 0.0010000467f > 0.001f
    assertEquals(1.001f, Argument.requireLessEqual("borderCase1", 1.001f, "borderCase2", 1.0f, 0.001f));
    assertEquals(1.001, Argument.requireLessEqual("borderCase1", 1.001, "borderCase2", 1.0, 0.001));
  }

  @Test
  public void testRequireGreater() {
    // 基本数值类型测试
    assertEquals((char) 'b', Argument.requireGreater("larger", (char) 'b', "smaller", (char) 'a'));
    assertEquals((byte) 2, Argument.requireGreater("larger", (byte) 2, "smaller", (byte) 1));
    assertEquals((short) 2, Argument.requireGreater("larger", (short) 2, "smaller", (short) 1));
    assertEquals(2, Argument.requireGreater("larger", 2, "smaller", 1));
    assertEquals(2L, Argument.requireGreater("larger", 2L, "smaller", 1L));

    // 浮点类型测试（包含epsilon参数）
    assertEquals(2.0f, Argument.requireGreater("larger", 2.0f, "smaller", 1.0f, 0.001f));
    assertEquals(2.0, Argument.requireGreater("larger", 2.0, "smaller", 1.0, 0.001));

    // 可比较对象测试
    assertEquals("b", Argument.requireGreater("larger", "b", "smaller", "a"));

    // 相等的情况（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", (char) 'a', "alsoEqual", (char) 'a'));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", (byte) 1, "alsoEqual", (byte) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", (short) 1, "alsoEqual", (short) 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", 2, "alsoEqual", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", 1L, "alsoEqual", 1L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", 1.0f, "alsoEqual", 1.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", 1.0, "alsoEqual", 1.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("equal", "a", "alsoEqual", "a"));

    // 小于的情况（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", (char) 'a', "larger", (char) 'b'));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", (byte) 1, "larger", (byte) 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", (short) 1, "larger", (short) 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", 1, "larger", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", 1L, "larger", 2L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", 1.0f, "larger", 2.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", 1.0, "larger", 2.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("smaller", "a", "larger", "b"));

    // 接近相等但仍然大于的浮点数测试
    assertEquals(1.003f, Argument.requireGreater("slightlyLarger", 1.003f, "slightlySmaller", 1.0f, 0.001f));
    assertEquals(1.003, Argument.requireGreater("slightlyLarger", 1.003, "slightlySmaller", 1.0, 0.001));

    // 接近相等但视为相等的浮点数（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("tooClose1", 1.0005f, "tooClose2", 1.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("tooClose1", 1.0005, "tooClose2", 1.0, 0.001));
  }

  @Test
  public void testRequireGreaterEqual() {
    // 基本数值类型测试 - 大于情况
    assertEquals((char) 'b', Argument.requireGreaterEqual("larger", (char) 'b', "smaller", (char) 'a'));
    assertEquals((byte) 2, Argument.requireGreaterEqual("larger", (byte) 2, "smaller", (byte) 1));
    assertEquals((short) 2, Argument.requireGreaterEqual("larger", (short) 2, "smaller", (short) 1));
    assertEquals(2, Argument.requireGreaterEqual("larger", 2, "smaller", 1));
    assertEquals(2L, Argument.requireGreaterEqual("larger", 2L, "smaller", 1L));

    // 基本数值类型测试 - 等于情况
    assertEquals((char) 'a', Argument.requireGreaterEqual("equal", (char) 'a', "alsoEqual", (char) 'a'));
    assertEquals((byte) 1, Argument.requireGreaterEqual("equal", (byte) 1, "alsoEqual", (byte) 1));
    assertEquals((short) 1, Argument.requireGreaterEqual("equal", (short) 1, "alsoEqual", (short) 1));
    assertEquals(2, Argument.requireGreaterEqual("equal", 2, "alsoEqual", 2));
    assertEquals(1L, Argument.requireGreaterEqual("equal", 1L, "alsoEqual", 1L));

    // 浮点类型测试（包含epsilon参数）- 大于情况
    assertEquals(2.0f, Argument.requireGreaterEqual("larger", 2.0f, "smaller", 1.0f, 0.001f));
    assertEquals(2.0, Argument.requireGreaterEqual("larger", 2.0, "smaller", 1.0, 0.001));

    // 浮点类型测试（包含epsilon参数）- 等于情况
    assertEquals(1.0f, Argument.requireGreaterEqual("equal", 1.0f, "alsoEqual", 1.0f, 0.001f));
    assertEquals(1.0, Argument.requireGreaterEqual("equal", 1.0, "alsoEqual", 1.0, 0.001));

    // 可比较对象测试
    assertEquals("b", Argument.requireGreaterEqual("larger", "b", "smaller", "a"));
    assertEquals("a", Argument.requireGreaterEqual("equal", "a", "alsoEqual", "a"));

    // 小于的情况（应抛出异常）
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", (char) 'a', "larger", (char) 'b'));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", (byte) 1, "larger", (byte) 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", (short) 1, "larger", (short) 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", 1, "larger", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", 1L, "larger", 2L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", 1.0f, "larger", 2.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", 1.0, "larger", 2.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("smaller", "a", "larger", "b"));

    // 近似相等的浮点数
    assertEquals(1.0005f, Argument.requireGreaterEqual("almostEqual1", 1.0005f, "almostEqual2", 1.0f, 0.001f));
    assertEquals(1.0005, Argument.requireGreaterEqual("almostEqual1", 1.0005, "almostEqual2", 1.0, 0.001));
  }

  @Test
  public void testRequireGreaterEqual_edgeCase() {
    // 差值恰好等于epsilon的情况
    // 注意：因为浮点数的表示，1.001f - 1.0f = 0.0010000467f > 0.001f
    assertEquals(1.001f, Argument.requireGreaterEqual("borderCase1", 1.001f, "borderCase2", 1.0f, 0.001f));
    assertEquals(1.001, Argument.requireGreaterEqual("borderCase1", 1.001, "borderCase2", 1.0, 0.001));
  }

  // -------------------------------------------------------------------------
  // 范围检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireInCloseRange() {
    // 基本数值类型测试 - byte
    assertEquals((byte) 1, Argument.requireInCloseRange("leftBoundary", (byte) 1, (byte) 1, (byte) 10));
    assertEquals((byte) 5, Argument.requireInCloseRange("middle", (byte) 5, (byte) 1, (byte) 10));
    assertEquals((byte) 10, Argument.requireInCloseRange("rightBoundary", (byte) 10, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", (byte) 0, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", (byte) 11, (byte) 1, (byte) 10));

    // 可空类型 - Byte
    final Byte nullByte = null;
    final Byte validByte = (byte) 5;
    assertEquals((byte) 5, Argument.requireInCloseRange("validByte", validByte, (byte) 1, (byte) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInCloseRange("nullByte", nullByte, (byte) 1, (byte) 10));

    // 基本数值类型测试 - short
    assertEquals((short) 1, Argument.requireInCloseRange("leftBoundary", (short) 1, (short) 1, (short) 10));
    assertEquals((short) 5, Argument.requireInCloseRange("middle", (short) 5, (short) 1, (short) 10));
    assertEquals((short) 10, Argument.requireInCloseRange("rightBoundary", (short) 10, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", (short) 0, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", (short) 11, (short) 1, (short) 10));

    // 可空类型 - Short
    final Short nullShort = null;
    final Short validShort = (short) 5;
    assertEquals((short) 5, Argument.requireInCloseRange("validShort", validShort, (short) 1, (short) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInCloseRange("nullShort", nullShort, (short) 1, (short) 10));

    // 基本数值类型测试 - int
    assertEquals(1, Argument.requireInCloseRange("leftBoundary", 1, 1, 10));
    assertEquals(5, Argument.requireInCloseRange("middle", 5, 1, 10));
    assertEquals(10, Argument.requireInCloseRange("rightBoundary", 10, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", 0, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", 11, 1, 10));

    // 可空类型 - Integer
    final Integer nullInteger = null;
    final Integer validInteger = 5;
    assertEquals(5, Argument.requireInCloseRange("validInteger", validInteger, 1, 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInCloseRange("nullInteger", nullInteger, 1, 10));

    // 基本数值类型测试 - long
    assertEquals(1L, Argument.requireInCloseRange("leftBoundary", 1L, 1L, 10L));
    assertEquals(5L, Argument.requireInCloseRange("middle", 5L, 1L, 10L));
    assertEquals(10L, Argument.requireInCloseRange("rightBoundary", 10L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", 0L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", 11L, 1L, 10L));

    // 可空类型 - Long
    final Long nullLong = null;
    final Long validLong = 5L;
    assertEquals(5L, Argument.requireInCloseRange("validLong", validLong, 1L, 10L));
    assertThrows(NullPointerException.class, () -> Argument.requireInCloseRange("nullLong", nullLong, 1L, 10L));

    // 基本数值类型测试 - float (含epsilon)
    assertEquals(1.0f, Argument.requireInCloseRange("leftBoundary", 1.0f, 1.0f, 10.0f, 0.001f));
    assertEquals(5.0f, Argument.requireInCloseRange("middle", 5.0f, 1.0f, 10.0f, 0.001f));
    assertEquals(10.0f, Argument.requireInCloseRange("rightBoundary", 10.0f, 1.0f, 10.0f, 0.001f));
    // 边界附近的测试
    assertEquals(0.999f, Argument.requireInCloseRange("nearLeftBoundary", 0.999f, 1.0f, 10.0f, 0.002f)); // 在epsilon范围内
    assertEquals(10.001f, Argument.requireInCloseRange("nearRightBoundary", 10.001f, 1.0f, 10.0f, 0.002f)); // 在epsilon范围内

    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", 0.998f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", 10.002f, 1.0f, 10.0f, 0.001f));

    // 可空类型 - Float
    final Float nullFloat = null;
    final Float validFloat = 5.0f;
    assertEquals(5.0f, Argument.requireInCloseRange("validFloat", validFloat, 1.0f, 10.0f, 0.001f));
    assertThrows(NullPointerException.class, () -> Argument.requireInCloseRange("nullFloat", nullFloat, 1.0f, 10.0f, 0.001f));

    // 基本数值类型测试 - double (含epsilon)
    assertEquals(1.0, Argument.requireInCloseRange("leftBoundary", 1.0, 1.0, 10.0, 0.001));
    assertEquals(5.0, Argument.requireInCloseRange("middle", 5.0, 1.0, 10.0, 0.001));
    assertEquals(10.0, Argument.requireInCloseRange("rightBoundary", 10.0, 1.0, 10.0, 0.001));
    // 边界附近的测试
    assertEquals(0.999, Argument.requireInCloseRange("nearLeftBoundary", 0.999, 1.0, 10.0, 0.002)); // 在epsilon范围内
    assertEquals(10.001, Argument.requireInCloseRange("nearRightBoundary", 10.001, 1.0, 10.0, 0.002)); // 在epsilon范围内

    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", 0.998, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", 10.002, 1.0, 10.0, 0.001));

    // 可空类型 - Double
    final Double nullDouble = null;
    final Double validDouble = 5.0;
    assertEquals(5.0, Argument.requireInCloseRange("validDouble", validDouble, 1.0, 10.0, 0.001));
    assertThrows(NullPointerException.class, () -> Argument.requireInCloseRange("nullDouble", nullDouble, 1.0, 10.0, 0.001));
  }

  @Test
  public void testRequireInOpenRange() {
    // 基本数值类型测试 - byte
    assertEquals((byte) 2, Argument.requireInOpenRange("valid", (byte) 2, (byte) 1, (byte) 10));
    assertEquals((byte) 9, Argument.requireInOpenRange("valid", (byte) 9, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", (byte) 1, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", (byte) 10, (byte) 1, (byte) 10));

    // 可空类型 - Byte
    final Byte nullByte = null;
    final Byte validByte = (byte) 5;
    assertEquals((byte) 5, Argument.requireInOpenRange("validByte", validByte, (byte) 1, (byte) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInOpenRange("nullByte", nullByte, (byte) 1, (byte) 10));

    // 基本数值类型测试 - short
    assertEquals((short) 2, Argument.requireInOpenRange("valid", (short) 2, (short) 1, (short) 10));
    assertEquals((short) 9, Argument.requireInOpenRange("valid", (short) 9, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", (short) 1, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", (short) 10, (short) 1, (short) 10));

    // 可空类型 - Short
    final Short nullShort = null;
    final Short validShort = (short) 5;
    assertEquals((short) 5, Argument.requireInOpenRange("validShort", validShort, (short) 1, (short) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInOpenRange("nullShort", nullShort, (short) 1, (short) 10));

    // 基本数值类型测试 - int
    assertEquals(2, Argument.requireInOpenRange("valid", 2, 1, 10));
    assertEquals(9, Argument.requireInOpenRange("valid", 9, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", 1, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", 10, 1, 10));

    // 可空类型 - Integer
    final Integer nullInteger = null;
    final Integer validInteger = 5;
    assertEquals(5, Argument.requireInOpenRange("validInteger", validInteger, 1, 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInOpenRange("nullInteger", nullInteger, 1, 10));

    // 基本数值类型测试 - long
    assertEquals(2L, Argument.requireInOpenRange("valid", 2L, 1L, 10L));
    assertEquals(9L, Argument.requireInOpenRange("valid", 9L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", 1L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", 10L, 1L, 10L));

    // 可空类型 - Long
    final Long nullLong = null;
    final Long validLong = 5L;
    assertEquals(5L, Argument.requireInOpenRange("validLong", validLong, 1L, 10L));
    assertThrows(NullPointerException.class, () -> Argument.requireInOpenRange("nullLong", nullLong, 1L, 10L));

    // 基本数值类型测试 - float (含epsilon)
    assertEquals(1.002f, Argument.requireInOpenRange("nearLeftBoundary", 1.002f, 1.0f, 10.0f, 0.001f));
    assertEquals(9.998f, Argument.requireInOpenRange("nearRightBoundary", 9.998f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", 1.0005f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", 9.9995f, 1.0f, 10.0f, 0.001f));

    // 可空类型 - Float
    final Float nullFloat = null;
    final Float validFloat = 5.0f;
    assertEquals(5.0f, Argument.requireInOpenRange("validFloat", validFloat, 1.0f, 10.0f, 0.001f));
    assertThrows(NullPointerException.class, () -> Argument.requireInOpenRange("nullFloat", nullFloat, 1.0f, 10.0f, 0.001f));

    // 基本数值类型测试 - double (含epsilon)
    assertEquals(1.002, Argument.requireInOpenRange("nearLeftBoundary", 1.002, 1.0, 10.0, 0.001));
    assertEquals(9.998, Argument.requireInOpenRange("nearRightBoundary", 9.998, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", 1.0005, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", 9.9995, 1.0, 10.0, 0.001));

    // 可空类型 - Double
    final Double nullDouble = null;
    final Double validDouble = 5.0;
    assertEquals(5.0, Argument.requireInOpenRange("validDouble", validDouble, 1.0, 10.0, 0.001));
    assertThrows(NullPointerException.class, () -> Argument.requireInOpenRange("nullDouble", nullDouble, 1.0, 10.0, 0.001));
  }

  @Test
  public void testRequireInLeftOpenRange() {
    // 基本数值类型测试 - byte
    assertEquals((byte) 2, Argument.requireInLeftOpenRange("valid", (byte) 2, (byte) 1, (byte) 10));
    assertEquals((byte) 10, Argument.requireInLeftOpenRange("rightBoundary", (byte) 10, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", (byte) 1, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", (byte) 11, (byte) 1, (byte) 10));

    // 可空类型 - Byte
    final Byte nullByte = null;
    final Byte validByte = (byte) 5;
    assertEquals((byte) 5, Argument.requireInLeftOpenRange("validByte", validByte, (byte) 1, (byte) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInLeftOpenRange("nullByte", nullByte, (byte) 1, (byte) 10));

    // 基本数值类型测试 - short
    assertEquals((short) 2, Argument.requireInLeftOpenRange("valid", (short) 2, (short) 1, (short) 10));
    assertEquals((short) 10, Argument.requireInLeftOpenRange("rightBoundary", (short) 10, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", (short) 1, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", (short) 11, (short) 1, (short) 10));

    // 可空类型 - Short
    final Short nullShort = null;
    final Short validShort = (short) 5;
    assertEquals((short) 5, Argument.requireInLeftOpenRange("validShort", validShort, (short) 1, (short) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInLeftOpenRange("nullShort", nullShort, (short) 1, (short) 10));

    // 基本数值类型测试 - int
    assertEquals(2, Argument.requireInLeftOpenRange("valid", 2, 1, 10));
    assertEquals(10, Argument.requireInLeftOpenRange("rightBoundary", 10, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", 1, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", 11, 1, 10));

    // 可空类型 - Integer
    final Integer nullInteger = null;
    final Integer validInteger = 5;
    assertEquals(5, Argument.requireInLeftOpenRange("validInteger", validInteger, 1, 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInLeftOpenRange("nullInteger", nullInteger, 1, 10));

    // 基本数值类型测试 - long
    assertEquals(2L, Argument.requireInLeftOpenRange("valid", 2L, 1L, 10L));
    assertEquals(10L, Argument.requireInLeftOpenRange("rightBoundary", 10L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", 1L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", 11L, 1L, 10L));

    // 可空类型 - Long
    final Long nullLong = null;
    final Long validLong = 5L;
    assertEquals(5L, Argument.requireInLeftOpenRange("validLong", validLong, 1L, 10L));
    assertThrows(NullPointerException.class, () -> Argument.requireInLeftOpenRange("nullLong", nullLong, 1L, 10L));

    // 基本数值类型测试 - float (含epsilon)
    assertEquals(1.002f, Argument.requireInLeftOpenRange("nearLeftBoundary", 1.002f, 1.0f, 10.0f, 0.001f));
    assertEquals(10.0f, Argument.requireInLeftOpenRange("rightBoundary", 10.0f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", 1.0005f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", 10.002f, 1.0f, 10.0f, 0.001f));

    // 可空类型 - Float
    final Float nullFloat = null;
    final Float validFloat = 5.0f;
    assertEquals(5.0f, Argument.requireInLeftOpenRange("validFloat", validFloat, 1.0f, 10.0f, 0.001f));
    assertThrows(NullPointerException.class, () -> Argument.requireInLeftOpenRange("nullFloat", nullFloat, 1.0f, 10.0f, 0.001f));

    // 基本数值类型测试 - double (含epsilon)
    assertEquals(1.002, Argument.requireInLeftOpenRange("nearLeftBoundary", 1.002, 1.0, 10.0, 0.001));
    assertEquals(10.0, Argument.requireInLeftOpenRange("rightBoundary", 10.0, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", 1.0005, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", 10.002, 1.0, 10.0, 0.001));

    // 可空类型 - Double
    final Double nullDouble = null;
    final Double validDouble = 5.0;
    assertEquals(5.0, Argument.requireInLeftOpenRange("validDouble", validDouble, 1.0, 10.0, 0.001));
    assertThrows(NullPointerException.class, () -> Argument.requireInLeftOpenRange("nullDouble", nullDouble, 1.0, 10.0, 0.001));
  }

  @Test
  public void testRequireInRightOpenRange() {
    // 基本数值类型测试 - byte
    assertEquals((byte) 1, Argument.requireInRightOpenRange("leftBoundary", (byte) 1, (byte) 1, (byte) 10));
    assertEquals((byte) 9, Argument.requireInRightOpenRange("valid", (byte) 9, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", (byte) 0, (byte) 1, (byte) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", (byte) 10, (byte) 1, (byte) 10));

    // 可空类型 - Byte
    final Byte nullByte = null;
    final Byte validByte = (byte) 5;
    assertEquals((byte) 5, Argument.requireInRightOpenRange("validByte", validByte, (byte) 1, (byte) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInRightOpenRange("nullByte", nullByte, (byte) 1, (byte) 10));

    // 基本数值类型测试 - short
    assertEquals((short) 1, Argument.requireInRightOpenRange("leftBoundary", (short) 1, (short) 1, (short) 10));
    assertEquals((short) 9, Argument.requireInRightOpenRange("valid", (short) 9, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", (short) 0, (short) 1, (short) 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", (short) 10, (short) 1, (short) 10));

    // 可空类型 - Short
    final Short nullShort = null;
    final Short validShort = (short) 5;
    assertEquals((short) 5, Argument.requireInRightOpenRange("validShort", validShort, (short) 1, (short) 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInRightOpenRange("nullShort", nullShort, (short) 1, (short) 10));

    // 基本数值类型测试 - int
    assertEquals(1, Argument.requireInRightOpenRange("leftBoundary", 1, 1, 10));
    assertEquals(9, Argument.requireInRightOpenRange("valid", 9, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", 0, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", 10, 1, 10));

    // 可空类型 - Integer
    final Integer nullInteger = null;
    final Integer validInteger = 5;
    assertEquals(5, Argument.requireInRightOpenRange("validInteger", validInteger, 1, 10));
    assertThrows(NullPointerException.class, () -> Argument.requireInRightOpenRange("nullInteger", nullInteger, 1, 10));

    // 基本数值类型测试 - long
    assertEquals(1L, Argument.requireInRightOpenRange("leftBoundary", 1L, 1L, 10L));
    assertEquals(9L, Argument.requireInRightOpenRange("valid", 9L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", 0L, 1L, 10L));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", 10L, 1L, 10L));

    // 可空类型 - Long
    final Long nullLong = null;
    final Long validLong = 5L;
    assertEquals(5L, Argument.requireInRightOpenRange("validLong", validLong, 1L, 10L));
    assertThrows(NullPointerException.class, () -> Argument.requireInRightOpenRange("nullLong", nullLong, 1L, 10L));

    // 基本数值类型测试 - float (含epsilon)
    assertEquals(1.0f, Argument.requireInRightOpenRange("leftBoundary", 1.0f, 1.0f, 10.0f, 0.001f));
    assertEquals(9.998f, Argument.requireInRightOpenRange("nearRightBoundary", 9.998f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", 0.998f, 1.0f, 10.0f, 0.001f));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", 9.9995f, 1.0f, 10.0f, 0.001f));

    // 可空类型 - Float
    final Float nullFloat = null;
    final Float validFloat = 5.0f;
    assertEquals(5.0f, Argument.requireInRightOpenRange("validFloat", validFloat, 1.0f, 10.0f, 0.001f));
    assertThrows(NullPointerException.class, () -> Argument.requireInRightOpenRange("nullFloat", nullFloat, 1.0f, 10.0f, 0.001f));

    // 基本数值类型测试 - double (含epsilon)
    assertEquals(1.0, Argument.requireInRightOpenRange("leftBoundary", 1.0, 1.0, 10.0, 0.001));
    assertEquals(9.998, Argument.requireInRightOpenRange("nearRightBoundary", 9.998, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", 0.998, 1.0, 10.0, 0.001));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", 9.9995, 1.0, 10.0, 0.001));

    // 可空类型 - Double
    final Double nullDouble = null;
    final Double validDouble = 5.0;
    assertEquals(5.0, Argument.requireInRightOpenRange("validDouble", validDouble, 1.0, 10.0, 0.001));
    assertThrows(NullPointerException.class, () -> Argument.requireInRightOpenRange("nullDouble", nullDouble, 1.0, 10.0, 0.001));
  }

  // -------------------------------------------------------------------------
  // 索引范围检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireIndexInCloseRange() {
    assertEquals(1, Argument.requireIndexInCloseRange(1, 1, 10));
    assertEquals(5, Argument.requireIndexInCloseRange(5, 1, 10));
    assertEquals(10, Argument.requireIndexInCloseRange(10, 1, 10));

    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInCloseRange(0, 1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInCloseRange(11, 1, 10));
  }

  @Test
  public void testRequireIndexInOpenRange() {
    assertEquals(2, Argument.requireIndexInOpenRange(2, 1, 10));
    assertEquals(9, Argument.requireIndexInOpenRange(9, 1, 10));

    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInOpenRange(1, 1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInOpenRange(10, 1, 10));
  }

  @Test
  public void testRequireIndexInLeftOpenRange() {
    assertEquals(2, Argument.requireIndexInLeftOpenRange(2, 1, 10));
    assertEquals(10, Argument.requireIndexInLeftOpenRange(10, 1, 10));

    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInLeftOpenRange(1, 1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInLeftOpenRange(11, 1, 10));
  }

  @Test
  public void testRequireIndexInRightOpenRange() {
    assertEquals(1, Argument.requireIndexInRightOpenRange(1, 1, 10));
    assertEquals(9, Argument.requireIndexInRightOpenRange(9, 1, 10));

    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInRightOpenRange(0, 1, 10));
    assertThrows(IndexOutOfBoundsException.class, () -> Argument.requireIndexInRightOpenRange(10, 1, 10));
  }

  // -------------------------------------------------------------------------
  // 枚举值检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireInEnum() {
    // 整数枚举测试
    final int[] allowedInts = {1, 2, 3};
    assertEquals(1, Argument.requireInEnum("validInt", 1, allowedInts));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInEnum("invalidInt", 4, allowedInts));

    // 字符串枚举测试
    final String[] allowedStrings = {"a", "b", "c"};
    assertEquals("a", Argument.requireInEnum("validString", "a", allowedStrings));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInEnum("invalidString", "d", allowedStrings));
  }

  // -------------------------------------------------------------------------
  // Unicode码点检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireValidUnicode() {
    assertEquals(0x0041, Argument.requireValidUnicode("validCodePoint", 0x0041)); // 字符 'A'
    assertEquals(0x4E2D, Argument.requireValidUnicode("validCodePoint", 0x4E2D)); // 字符 '中'

    assertThrows(IllegalArgumentException.class, () -> Argument.requireValidUnicode("invalidCodePoint", -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireValidUnicode("invalidCodePoint", 0x110000)); // 超出Unicode范围
  }

  // -------------------------------------------------------------------------
  // requireInCollection/requireNotInCollection方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireInCollection() {
    final Collection<String> collection = new ArrayList<>();
    collection.add("item1");
    collection.add("item2");

    assertEquals("item1", Argument.requireInCollection("validItem", "item1", collection));

    assertThrows(NullPointerException.class, () -> Argument.requireInCollection("nullItem", null, collection));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCollection("invalidItem", "item3", collection));
  }

  @Test
  public void testRequireNotInCollection() {
    final Collection<String> collection = new ArrayList<>();
    collection.add("item1");
    collection.add("item2");

    assertEquals("item3", Argument.requireNotInCollection("validItem", "item3", collection));

    assertThrows(NullPointerException.class, () -> Argument.requireNotInCollection("nullItem", null, collection));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotInCollection("invalidItem", "item1", collection));
  }

  // -------------------------------------------------------------------------
  // requireInArray/requireNotInArray方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireInArray() {
    final String[] array = {"item1", "item2"};

    assertEquals("item1", Argument.requireInArray("validItem", "item1", array));

    assertThrows(NullPointerException.class, () -> Argument.requireInArray("nullItem", null, array));
    assertThrows(NullPointerException.class, () -> Argument.requireInArray("validItem", "item1", null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInArray("invalidItem", "item3", array));
  }

  @Test
  public void testRequireNotInArray() {
    final String[] array = {"item1", "item2"};

    assertEquals("item3", Argument.requireNotInArray("validItem", "item3", array));

    assertThrows(NullPointerException.class, () -> Argument.requireNotInArray("nullItem", null, array));
    assertThrows(NullPointerException.class, () -> Argument.requireNotInArray("validItem", "item3", null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotInArray("invalidItem", "item1", array));
  }

  // -------------------------------------------------------------------------
  // requireMatch/requireNotMatch方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireMatch() {
    // 使用字符串形式的正则表达式测试
    assertEquals("abc", Argument.requireMatch("validString", "abc", "^[a-z]{1,3}$"));
    assertEquals("12345", Argument.requireMatch("validString", "12345", "^\\d+$"));
    assertEquals("abc_def", Argument.requireMatch("validString", "abc_def", "^[a-z]+_[a-z]+$"));
    assertEquals("2023-12-31", Argument.requireMatch("validString", "2023-12-31", "^\\d{4}-\\d{2}-\\d{2}$"));

    assertThrows(NullPointerException.class, () -> Argument.requireMatch("nullString", null, "^[a-z]+$"));
    assertThrows(NullPointerException.class, () -> Argument.requireMatch("validString", "abc", (String) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireMatch("invalidString", "123abc", "^[a-z]+[0-9]+$"));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireMatch("invalidString", "", "^[a-z]+[0-9]+$"));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireMatch("invalidString", "ABC123", "^[a-z]+[0-9]+$"));

    // 使用Pattern对象形式的正则表达式测试
    final java.util.regex.Pattern patternObj = java.util.regex.Pattern.compile("^[a-z]+[0-9]+$");
    assertEquals("abc123", Argument.requireMatch("validString", "abc123", patternObj));
    assertEquals("xyz789", Argument.requireMatch("validString", "xyz789", patternObj));

    // 多模式匹配
    final java.util.regex.Pattern multiPatternObj = java.util.regex.Pattern.compile("^(\\d{3}-\\d{2}-\\d{4}|\\d{9})$");
    assertEquals("123-45-6789", Argument.requireMatch("validSSN", "123-45-6789", multiPatternObj));
    assertEquals("123456789", Argument.requireMatch("validSSN", "123456789", multiPatternObj));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireMatch("invalidSSN", "12-345-6789", multiPatternObj));

    // 不同模式标志测试
    final java.util.regex.Pattern caseInsensitivePatternObj = java.util.regex.Pattern.compile("^[a-z]+$", java.util.regex.Pattern.CASE_INSENSITIVE);
    assertEquals("ABC", Argument.requireMatch("validString", "ABC", caseInsensitivePatternObj));
    assertEquals("abc", Argument.requireMatch("validString", "abc", caseInsensitivePatternObj));
    assertEquals("AbC", Argument.requireMatch("validString", "AbC", caseInsensitivePatternObj));

    assertThrows(NullPointerException.class, () -> Argument.requireMatch("nullString", null, patternObj));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireMatch("invalidString", "123abc", patternObj));
  }

  @Test
  public void testRequireNotMatch() {
    // 使用字符串形式的正则表达式测试
    assertEquals("123abc", Argument.requireNotMatch("validString", "123abc", "^[a-z]+[0-9]+$"));
    assertEquals("ABC123", Argument.requireNotMatch("validString", "ABC123", "^[a-z]+[0-9]+$"));
    assertEquals("", Argument.requireNotMatch("validString", "", "^[a-z]+[0-9]+$"));
    assertEquals("abc 123", Argument.requireNotMatch("validString", "abc 123", "^[a-z]+[0-9]+$"));
    assertEquals("123", Argument.requireNotMatch("validString", "123", "^[a-z]+[0-9]+$"));
    assertEquals("abc", Argument.requireNotMatch("validString", "abc", "^[a-z]+[0-9]+$"));

    // 特殊情况测试
    assertEquals("12345", Argument.requireNotMatch("validString", "12345", "^[a-z]+$"));
    assertEquals("abc12345", Argument.requireNotMatch("validString", "abc12345", "^\\d+$"));
    assertEquals("abc-def", Argument.requireNotMatch("validString", "abc-def", "^[a-z]+_[a-z]+$"));
    assertEquals("20231231", Argument.requireNotMatch("validString", "20231231", "^\\d{4}-\\d{2}-\\d{2}$"));

    assertThrows(NullPointerException.class, () -> Argument.requireNotMatch("nullString", null, "^[a-z]+$"));
    assertThrows(NullPointerException.class, () -> Argument.requireNotMatch("validString", "123abc", (String) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotMatch("invalidString", "abc123", "^[a-z]+[0-9]+$"));

    // 使用Pattern对象形式的正则表达式测试
    final java.util.regex.Pattern patternObj = java.util.regex.Pattern.compile("^[a-z]+[0-9]+$");
    assertEquals("123abc", Argument.requireNotMatch("validString", "123abc", patternObj));
    assertEquals("ABC123", Argument.requireNotMatch("validString", "ABC123", patternObj));

    // 多模式不匹配
    final java.util.regex.Pattern multiPatternObj = java.util.regex.Pattern.compile("^(\\d{3}-\\d{2}-\\d{4}|\\d{9})$");
    assertEquals("12-345-6789", Argument.requireNotMatch("invalidSSN", "12-345-6789", multiPatternObj));
    assertEquals("1234567890", Argument.requireNotMatch("invalidSSN", "1234567890", multiPatternObj));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotMatch("validSSN", "123-45-6789", multiPatternObj));

    // 不同模式标志测试
    final java.util.regex.Pattern caseInsensitivePatternObj = java.util.regex.Pattern.compile("^[a-z]+$", java.util.regex.Pattern.CASE_INSENSITIVE);
    assertEquals("123", Argument.requireNotMatch("validString", "123", caseInsensitivePatternObj));
    assertEquals("abc123", Argument.requireNotMatch("validString", "abc123", caseInsensitivePatternObj));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotMatch("invalidString", "ABC", caseInsensitivePatternObj));

    assertThrows(NullPointerException.class, () -> Argument.requireNotMatch("nullString", null, patternObj));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNotMatch("invalidString", "abc123", patternObj));
  }
}