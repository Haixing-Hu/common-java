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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the CharSequenceUtils class.
 *
 * @author Haixing Hu
 */
public class CharSequenceUtilsTest {

  @Test
  public void testStartsWith() {
    assertTrue(CharSequenceUtils.startsWith("Hello, world", "Hello"));
    assertTrue(CharSequenceUtils.startsWith("Hello, world", ""));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", "hello"));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", "world"));
    assertFalse(CharSequenceUtils.startsWith("Hello", "Hello, world"));

    // 测试带起始索引的方法
    assertTrue(CharSequenceUtils.startsWith("Hello, world", 7, "world"));
    assertTrue(CharSequenceUtils.startsWith("Hello, world", 7, ""));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", 7, "World"));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", 7, "Hello"));

    // 测试带起始索引和结束索引的方法
    assertTrue(CharSequenceUtils.startsWith("Hello, world", 0, 5, "Hello"));
    assertTrue(CharSequenceUtils.startsWith("Hello, world", 7, 12, "world"));
    assertTrue(CharSequenceUtils.startsWith("Hello, world", 0, 5, ""));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", 0, 5, "hello"));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", 0, 5, "Hello, world"));
    assertFalse(CharSequenceUtils.startsWith("Hello, world", 0, 4, "Hello"));
  }

  @Test
  public void testStartsWithIgnoreCase() {
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", "Hello"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", "hello"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", "HELLO"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", ""));
    assertFalse(CharSequenceUtils.startsWithIgnoreCase("Hello, world", "world"));
    assertFalse(CharSequenceUtils.startsWithIgnoreCase("Hello", "Hello, world"));

    // 测试带起始索引的方法
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 7, "world"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 7, "WORLD"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 7, ""));
    assertFalse(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 7, "Hello"));

    // 测试带起始索引和结束索引的方法
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 0, 5, "Hello"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 0, 5, "hello"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 0, 5, "HELLO"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 7, 12, "world"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 7, 12, "WORLD"));
    assertTrue(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 0, 5, ""));
    assertFalse(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 0, 5, "Hello, world"));
    assertFalse(CharSequenceUtils.startsWithIgnoreCase("Hello, world", 0, 4, "Hello"));
  }

  @Test
  public void testEndsWith() {
    assertTrue(CharSequenceUtils.endsWith("Hello, world", "world"));
    assertTrue(CharSequenceUtils.endsWith("Hello, world", ""));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", "World"));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", "Hello"));
    assertFalse(CharSequenceUtils.endsWith("world", "Hello, world"));

    // 测试带结束索引的方法
    assertTrue(CharSequenceUtils.endsWith("Hello, world", 5, "Hello"));
    assertTrue(CharSequenceUtils.endsWith("Hello, world", 5, ""));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", 5, "hello"));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", 5, "world"));

    // 测试带起始索引和结束索引的方法
    assertTrue(CharSequenceUtils.endsWith("Hello, world", 0, 5, "Hello"));
    assertTrue(CharSequenceUtils.endsWith("Hello, world", 7, 12, "world"));
    assertTrue(CharSequenceUtils.endsWith("Hello, world", 0, 5, ""));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", 0, 5, "hello"));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", 0, 5, "Hello, world"));
    assertFalse(CharSequenceUtils.endsWith("Hello, world", 0, 4, "Hello"));
  }

  @Test
  public void testEndsWithIgnoreCase() {
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", "world"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", "WORLD"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", "World"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", ""));
    assertFalse(CharSequenceUtils.endsWithIgnoreCase("Hello, world", "Hello"));
    assertFalse(CharSequenceUtils.endsWithIgnoreCase("world", "Hello, world"));

    // 测试带结束索引的方法
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 5, "Hello"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 5, "HELLO"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 5, "hello"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 5, ""));
    assertFalse(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 5, "world"));

    // 测试带起始索引和结束索引的方法
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 0, 5, "Hello"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 0, 5, "HELLO"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 0, 5, "hello"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 7, 12, "world"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 7, 12, "WORLD"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 7, 12, "World"));
    assertTrue(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 0, 5, ""));
    assertFalse(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 0, 5, "Hello, world"));
    assertFalse(CharSequenceUtils.endsWithIgnoreCase("Hello, world", 0, 4, "Hello"));
  }
}