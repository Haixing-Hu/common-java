////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link MapUtils}类的功能。
 *
 * @author Claude
 */
public class MapUtilsTest {

  @Test
  public void testToArrayWithEmptyMap() {
    Map<String, Integer> emptyMap = new HashMap<>();
    Object[][] result = MapUtils.toArray(emptyMap);
    
    // 验证空Map转换为空二维数组
    assertEquals(0, result.length);
  }
  
  @Test
  public void testToArrayWithNonEmptyMap() {
    Map<String, Integer> map = new HashMap<>();
    map.put("one", 1);
    map.put("two", 2);
    map.put("three", 3);
    
    Object[][] result = MapUtils.toArray(map);
    
    // 验证转换后的数组长度等于Map的大小
    assertEquals(3, result.length);
    
    // 创建一个临时Map用于验证结果
    Map<String, Integer> resultMap = new HashMap<>();
    for (Object[] entry : result) {
      resultMap.put((String) entry[0], (Integer) entry[1]);
    }
    
    // 验证转换后的数组包含原Map的所有键值对
    assertEquals(3, resultMap.size());
    assertEquals(1, resultMap.get("one"));
    assertEquals(2, resultMap.get("two"));
    assertEquals(3, resultMap.get("three"));
  }
  
  @Test
  public void testFromArrayWithEmptyArray() {
    Object[][] emptyArray = new Object[0][0];
    
    Map<String, Integer> result = MapUtils.fromArray(emptyArray);
    
    // 验证空数组转换为空Map
    assertTrue(result.isEmpty());
  }
  
  @Test
  public void testFromArrayWithNonEmptyArray() {
    Object[][] array = new Object[3][2];
    
    array[0][0] = "one";
    array[0][1] = 1;
    
    array[1][0] = "two";
    array[1][1] = 2;
    
    array[2][0] = "three";
    array[2][1] = 3;
    
    Map<String, Integer> result = MapUtils.fromArray(array);
    
    // 验证转换后的Map大小等于数组长度
    assertEquals(3, result.size());
    
    // 验证转换后的Map包含数组中的所有键值对
    assertEquals(1, result.get("one"));
    assertEquals(2, result.get("two"));
    assertEquals(3, result.get("three"));
  }
  
  @Test
  public void testRoundTrip() {
    // 创建原始Map
    Map<String, Integer> originalMap = new HashMap<>();
    originalMap.put("one", 1);
    originalMap.put("two", 2);
    originalMap.put("three", 3);
    
    // Map转换为数组
    Object[][] array = MapUtils.toArray(originalMap);
    
    // 数组转换回Map
    Map<String, Integer> resultMap = MapUtils.fromArray(array);
    
    // 验证最终Map与原始Map相等
    assertEquals(originalMap.size(), resultMap.size());
    assertEquals(originalMap.get("one"), resultMap.get("one"));
    assertEquals(originalMap.get("two"), resultMap.get("two"));
    assertEquals(originalMap.get("three"), resultMap.get("three"));
  }
  
  @Test
  public void testFromArrayWithNullValues() {
    Object[][] array = new Object[2][2];
    
    array[0][0] = "hasValue";
    array[0][1] = 100;
    
    array[1][0] = "nullValue";
    array[1][1] = null;
    
    Map<String, Object> result = MapUtils.fromArray(array);
    
    // 验证转换后的Map大小等于数组长度
    assertEquals(2, result.size());
    
    // 验证转换后的Map包含数组中的所有键值对，包括null值
    assertEquals(100, result.get("hasValue"));
    assertNull(result.get("nullValue"));
    assertTrue(result.containsKey("nullValue"));
  }
} 