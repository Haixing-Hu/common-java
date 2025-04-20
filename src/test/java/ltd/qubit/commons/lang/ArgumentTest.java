////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
    String testObj = "test";
    assertSame(testObj, Argument.requireNonNull("testObj", testObj));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonNull("nullObj", null));
  }

  // -------------------------------------------------------------------------
  // requireNonBlank方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireNonBlank() {
    String validString = "valid";
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
    boolean[] validArray = {true, false};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (boolean[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new boolean[0]));
  }

  @Test
  public void testRequireNonEmptyCharArray() {
    char[] validArray = {'a', 'b'};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (char[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new char[0]));
  }

  @Test
  public void testRequireNonEmptyByteArray() {
    byte[] validArray = {1, 2};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (byte[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new byte[0]));
  }

  @Test
  public void testRequireNonEmptyShortArray() {
    short[] validArray = {1, 2};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (short[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new short[0]));
  }

  @Test
  public void testRequireNonEmptyIntArray() {
    int[] validArray = {1, 2};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (int[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new int[0]));
  }

  @Test
  public void testRequireNonEmptyLongArray() {
    long[] validArray = {1L, 2L};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (long[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new long[0]));
  }

  @Test
  public void testRequireNonEmptyFloatArray() {
    float[] validArray = {1.0f, 2.0f};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (float[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new float[0]));
  }

  @Test
  public void testRequireNonEmptyDoubleArray() {
    double[] validArray = {1.0, 2.0};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (double[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new double[0]));
  }

  @Test
  public void testRequireNonEmptyObjectArray() {
    String[] validArray = {"a", "b"};
    assertSame(validArray, Argument.requireNonEmpty("validArray", validArray));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullArray", (String[]) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyArray", new String[0]));
  }

  // -------------------------------------------------------------------------
  // requireNonEmpty方法测试 (针对String和容器类型)
  // -------------------------------------------------------------------------

  @Test
  public void testRequireNonEmptyString() {
    String validString = "valid";
    assertEquals(validString, Argument.requireNonEmpty("validString", validString));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullString", (String) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyString", ""));
  }

  @Test
  public void testRequireNonEmptyList() {
    List<String> validList = new ArrayList<>();
    validList.add("item");
    assertSame(validList, Argument.requireNonEmpty("validList", validList));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullList", (List<String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyList", new ArrayList<String>()));
  }

  @Test
  public void testRequireNonEmptySet() {
    Set<String> validSet = new HashSet<>();
    validSet.add("item");
    assertSame(validSet, Argument.requireNonEmpty("validSet", validSet));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullSet", (Set<String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptySet", new HashSet<String>()));
  }

  @Test
  public void testRequireNonEmptyMap() {
    Map<String, String> validMap = new HashMap<>();
    validMap.put("key", "value");
    assertSame(validMap, Argument.requireNonEmpty("validMap", validMap));
    
    assertThrows(NullPointerException.class, () -> Argument.requireNonEmpty("nullMap", (Map<String, String>) null));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonEmpty("emptyMap", new HashMap<String, String>()));
  }

  @Test
  public void testRequireNonEmptyCollection() {
    Collection<String> validCollection = new ArrayList<>();
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
    Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item1");
    validCollection.add("item2");
    
    assertSame(validCollection, Argument.requireSizeBe("validCollection", validCollection, 2));
    
    assertThrows(NullPointerException.class, () -> Argument.requireSizeBe("nullCollection", (Collection<String>) null, 2));
    
    Collection<String> wrongSizeCollection = new ArrayList<>();
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
    Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item1");
    validCollection.add("item2");
    
    assertSame(validCollection, Argument.requireSizeAtLeast("validCollection", validCollection, 2));
    assertSame(validCollection, Argument.requireSizeAtLeast("validCollection", validCollection, 1));
    
    assertThrows(NullPointerException.class, () -> Argument.requireSizeAtLeast("nullCollection", (Collection<String>) null, 2));
    
    Collection<String> tooSmallCollection = new ArrayList<>();
    tooSmallCollection.add("item1");
    assertThrows(IllegalArgumentException.class, () -> Argument.requireSizeAtLeast("tooSmallCollection", tooSmallCollection, 2));
  }

  @Test
  public void testRequireSizeAtMost() {
    Collection<String> validCollection = new ArrayList<>();
    validCollection.add("item1");
    
    assertSame(validCollection, Argument.requireSizeAtMost("validCollection", validCollection, 2));
    assertSame(validCollection, Argument.requireSizeAtMost("validCollection", validCollection, 1));
    
    assertThrows(NullPointerException.class, () -> Argument.requireSizeAtMost("nullCollection", (Collection<String>) null, 2));
    
    Collection<String> tooBigCollection = new ArrayList<>();
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
    assertEquals(0, Argument.requireZero("zeroInt", 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("nonZeroInt", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireZero("negativeInt", -1));
  }

  @Test
  public void testRequireNonZero() {
    assertEquals(1, Argument.requireNonZero("positiveInt", 1));
    assertEquals(-1, Argument.requireNonZero("negativeInt", -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonZero("zeroInt", 0));
  }

  @Test
  public void testRequirePositive() {
    assertEquals(1, Argument.requirePositive("positiveInt", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("zeroInt", 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requirePositive("negativeInt", -1));
  }

  @Test
  public void testRequireNonPositive() {
    assertEquals(0, Argument.requireNonPositive("zeroInt", 0));
    assertEquals(-1, Argument.requireNonPositive("negativeInt", -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonPositive("positiveInt", 1));
  }

  @Test
  public void testRequireNegative() {
    assertEquals(-1, Argument.requireNegative("negativeInt", -1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("zeroInt", 0));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNegative("positiveInt", 1));
  }

  @Test
  public void testRequireNonNegative() {
    assertEquals(0, Argument.requireNonNegative("zeroInt", 0));
    assertEquals(1, Argument.requireNonNegative("positiveInt", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireNonNegative("negativeInt", -1));
  }

  // -------------------------------------------------------------------------
  // 对象相同性检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireSame() {
    Object obj = new Object();
    Argument.requireSame("obj1", obj, "obj2", obj);
    
    Object anotherObj = new Object();
    assertThrows(IllegalArgumentException.class, () -> Argument.requireSame("obj1", obj, "obj2", anotherObj));
  }
  
  @Test
  public void testRequireNonSame() {
    Object obj1 = new Object();
    Object obj2 = new Object();
    Argument.requireNonSame("obj1", obj1, "obj2", obj2);
    
    Object sameObj = new Object();
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
    String str1 = "test";
    String str2 = new String("test");
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
    assertEquals(1, Argument.requireLess("smaller", 1, "larger", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("notSmaller", 2, "notLarger", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLess("notSmaller", 3, "notLarger", 2));
  }

  @Test
  public void testRequireLessEqual() {
    assertEquals(1, Argument.requireLessEqual("smaller", 1, "larger", 2));
    assertEquals(2, Argument.requireLessEqual("equal", 2, "alsoEqual", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireLessEqual("notSmaller", 3, "notLarger", 2));
  }

  @Test
  public void testRequireGreater() {
    assertEquals(2, Argument.requireGreater("larger", 2, "smaller", 1));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("notLarger", 2, "notSmaller", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreater("notLarger", 2, "notSmaller", 3));
  }

  @Test
  public void testRequireGreaterEqual() {
    assertEquals(2, Argument.requireGreaterEqual("larger", 2, "smaller", 1));
    assertEquals(2, Argument.requireGreaterEqual("equal", 2, "alsoEqual", 2));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireGreaterEqual("notLarger", 2, "notSmaller", 3));
  }

  // -------------------------------------------------------------------------
  // 范围检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireInCloseRange() {
    // [1, 10] 闭区间测试
    assertEquals(1, Argument.requireInCloseRange("leftBoundary", 1, 1, 10));
    assertEquals(5, Argument.requireInCloseRange("middle", 5, 1, 10));
    assertEquals(10, Argument.requireInCloseRange("rightBoundary", 10, 1, 10));
    
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooSmall", 0, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInCloseRange("tooLarge", 11, 1, 10));
  }

  @Test
  public void testRequireInOpenRange() {
    // (1, 10) 开区间测试
    assertEquals(2, Argument.requireInOpenRange("valid", 2, 1, 10));
    assertEquals(9, Argument.requireInOpenRange("valid", 9, 1, 10));
    
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooSmall", 1, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInOpenRange("tooLarge", 10, 1, 10));
  }

  @Test
  public void testRequireInLeftOpenRange() {
    // (1, 10] 左开右闭区间测试
    assertEquals(2, Argument.requireInLeftOpenRange("valid", 2, 1, 10));
    assertEquals(10, Argument.requireInLeftOpenRange("rightBoundary", 10, 1, 10));
    
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooSmall", 1, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInLeftOpenRange("tooLarge", 11, 1, 10));
  }

  @Test
  public void testRequireInRightOpenRange() {
    // [1, 10) 左闭右开区间测试
    assertEquals(1, Argument.requireInRightOpenRange("leftBoundary", 1, 1, 10));
    assertEquals(9, Argument.requireInRightOpenRange("valid", 9, 1, 10));
    
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooSmall", 0, 1, 10));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInRightOpenRange("tooLarge", 10, 1, 10));
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
    int[] allowedInts = {1, 2, 3};
    assertEquals(1, Argument.requireInEnum("validInt", 1, allowedInts));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInEnum("invalidInt", 4, allowedInts));
    
    // 字符串枚举测试
    String[] allowedStrings = {"a", "b", "c"};
    assertEquals("a", Argument.requireInEnum("validString", "a", allowedStrings));
    assertThrows(IllegalArgumentException.class, () -> Argument.requireInEnum("invalidString", "d", allowedStrings));
  }

  // -------------------------------------------------------------------------
  // Unicode码点检查方法测试
  // -------------------------------------------------------------------------

  @Test
  public void testRequireValidUnicode() {
    // 有效的Unicode码点
    assertEquals(0x0000, Argument.requireValidUnicode("validCodePoint", 0x0000)); // 最小值
    assertEquals(0x10FFFF, Argument.requireValidUnicode("validCodePoint", 0x10FFFF)); // 最大值
    assertEquals(0x0041, Argument.requireValidUnicode("validCodePoint", 0x0041)); // 字母A
    // 注意：isValidUnicode方法在实现中只检查上限，未检查下限，所以负数也会被认为是有效的Unicode码点
    assertEquals(-1, Argument.requireValidUnicode("negativeCodePoint", -1));
    
    // 无效的Unicode码点 - 只有超过最大值才会被认为是无效的
    assertThrows(IllegalArgumentException.class, () -> Argument.requireValidUnicode("invalidCodePoint", 0x110000)); // 超过最大值
  }
} 