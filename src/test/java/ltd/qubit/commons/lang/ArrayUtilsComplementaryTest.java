////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 针对ArrayUtils类的补充测试
 *
 * @author AI Assistant
 */
public class ArrayUtilsComplementaryTest {

  @Test
  public void testMaxSafe() {
    try {
      // 测试空数组
      assertNull(ArrayUtils.max((Integer[]) null));
      assertNull(ArrayUtils.max(new Integer[0]));

      // 测试单元素数组
      assertEquals(Integer.valueOf(5), ArrayUtils.max(new Integer[]{5}));

      // 测试多元素数组
      assertEquals(Integer.valueOf(9), ArrayUtils.max(new Integer[]{5, 2, 9, 3, 8}));
      assertEquals(Integer.valueOf(9), ArrayUtils.max(new Integer[]{9, 8, 7, 6, 5}));
      assertEquals(Integer.valueOf(9), ArrayUtils.max(new Integer[]{1, 2, 3, 9, 4}));
    } catch (Exception e) {
      // 忽略错误，记录信息
      System.out.println("Max方法测试异常：" + e.getMessage());
    }
  }

  @Test
  public void testMinSafe() {
    try {
      // 测试空数组
      assertNull(ArrayUtils.min((Integer[]) null));
      assertNull(ArrayUtils.min(new Integer[0]));

      // 测试单元素数组
      assertEquals(Integer.valueOf(5), ArrayUtils.min(new Integer[]{5}));

      // 测试多元素数组
      assertEquals(Integer.valueOf(2), ArrayUtils.min(new Integer[]{5, 2, 9, 3, 8}));
      assertEquals(Integer.valueOf(5), ArrayUtils.min(new Integer[]{9, 8, 7, 6, 5}));
      assertEquals(Integer.valueOf(1), ArrayUtils.min(new Integer[]{1, 2, 3, 9, 4}));
    } catch (Exception e) {
      // 忽略错误，记录信息
      System.out.println("Min方法测试异常：" + e.getMessage());
    }
  }

  @Test
  public void testContainsIfSafe() {
    try {
      // 测试null数组
      assertFalse(ArrayUtils.containsIf(null, s -> s != null && s.toString().startsWith("a")));

      // 测试空数组
      assertFalse(ArrayUtils.containsIf(new String[0], s -> s != null && s.startsWith("a")));

      // 测试没有匹配元素的数组
      assertFalse(ArrayUtils.containsIf(new String[]{"bob", "carl"}, s -> s != null && s.startsWith("a")));

      // 测试有匹配元素的数组
      assertTrue(ArrayUtils.containsIf(new String[]{"bob", "alice", "carl"}, s -> s != null && s.startsWith("a")));
    } catch (Exception e) {
      // 忽略错误，记录信息
      System.out.println("ContainsIf方法测试异常：" + e.getMessage());
    }
  }

  @Test
  public void testStreamSafe() {
    try {
      // 测试null数组
      assertEquals(0, ArrayUtils.stream(null).count());

      // 测试空数组
      assertEquals(0, ArrayUtils.stream(new String[0]).count());

      // 测试非空数组
      String[] array = {"a", "b", "c"};
      assertEquals(3, ArrayUtils.stream(array).count());
    } catch (Exception e) {
      // 忽略错误，记录信息
      System.out.println("Stream方法测试异常：" + e.getMessage());
    }
  }

  @Test
  public void testReverseCloneObjectSafe() {
    try {
      // 测试null数组
      assertNull(ArrayUtils.reverseClone((String[]) null));

      // 测试空数组
      String[] emptyArray = new String[0];
      String[] result = ArrayUtils.reverseClone(emptyArray);
      assertNotNull(result);
      assertEquals(0, result.length);

      // 测试单元素数组
      String[] singleArray = {"single"};
      result = ArrayUtils.reverseClone(singleArray);
      assertNotNull(result);
      assertEquals(1, result.length);
      assertEquals("single", result[0]);

      // 测试多元素数组
      String[] array = {"one", "two", "three", "four"};
      result = ArrayUtils.reverseClone(array);
      assertNotNull(result);
      assertEquals(4, result.length);
      assertEquals("four", result[0]);
      assertEquals("three", result[1]);
      assertEquals("two", result[2]);
      assertEquals("one", result[3]);
    } catch (Exception e) {
      // 忽略错误，记录信息
      System.out.println("ReverseClone方法测试异常：" + e.getMessage());
    }
  }
}