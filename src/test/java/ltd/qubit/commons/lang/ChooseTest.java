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
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link Choose} class.
 *
 * @author 胡海星
 */
public class ChooseTest {

  @Test
  public void testChooseTwoArguments() {
    // Test with first argument non-null
    assertEquals("first", Choose.choose("first", "second"));
    assertEquals(Integer.valueOf(1), Choose.choose(1, 2));
    assertEquals(Boolean.TRUE, Choose.choose(true, false));

    // Test with first argument null
    assertEquals("second", Choose.choose(null, "second"));
    assertEquals(Integer.valueOf(2), Choose.choose(null, 2));
    assertEquals(Boolean.FALSE, Choose.choose(null, false));

    // Test with both arguments null
    assertNull(Choose.choose(null, null));

    // Test with different types
    assertEquals("hello", Choose.choose("hello", null));
    assertEquals(Long.valueOf(42L), Choose.choose(42L, null));
    assertEquals(Double.valueOf(3.14), Choose.choose(3.14, null));
  }

  @Test
  public void testChooseThreeArguments() {
    // Test with first argument non-null
    assertEquals("first", Choose.choose("first", "second", "third"));
    assertEquals(Integer.valueOf(1), Choose.choose(1, 2, 3));

    // Test with first argument null, second non-null
    assertEquals("second", Choose.choose(null, "second", "third"));
    assertEquals(Integer.valueOf(2), Choose.choose(null, 2, 3));

    // Test with first two arguments null, third non-null
    assertEquals("third", Choose.choose(null, null, "third"));
    assertEquals(Integer.valueOf(3), Choose.choose(null, null, 3));

    // Test with all arguments null
    assertNull(Choose.choose(null, null, null));

    // Test with mixed null and non-null
    assertEquals("middle", Choose.choose(null, "middle", null));
    assertEquals("last", Choose.choose(null, null, "last"));
  }

  @Test
  public void testChooseVarargs() {
    // Test with empty array
    assertNull(Choose.choose());

    // Test with null array
    String[] nullArray = null;
    assertNull(Choose.choose(nullArray));

    // Test with single argument
    assertEquals("single", Choose.choose("single"));
    assertEquals(Integer.valueOf(42), Choose.choose(42));

    // Test with multiple arguments
    assertEquals("first", Choose.choose("first", "second", "third", "fourth"));
    assertEquals(Integer.valueOf(1), Choose.choose(1, 2, 3, 4, 5));

    // Test with all null arguments
    assertNull(Choose.choose(null, null, null, null));

    // Test with first argument null
    assertEquals("second", Choose.choose(null, "second", "third"));

    // Test with middle argument non-null
    assertEquals("middle", Choose.choose(null, null, "middle", null, null));

    // Test with last argument non-null
    assertEquals("last", Choose.choose(null, null, null, "last"));

    // Test with many arguments
    assertEquals("found", Choose.choose(null, null, null, null, null, "found",
        null, null));

    // Test with mixed types
    assertEquals("string", Choose.choose(null, null, "string", 42, true));
  }

  @Test
  public void testChooseWithDifferentTypes() {
    // Test with String
    assertEquals("hello", Choose.choose(null, "hello"));
    assertEquals("world", Choose.choose("world", null));

    // Test with Integer
    assertEquals(Integer.valueOf(10), Choose.choose(null, 10));
    assertEquals(Integer.valueOf(20), Choose.choose(20, null));

    // Test with Boolean
    assertEquals(Boolean.TRUE, Choose.choose(null, true));
    assertEquals(Boolean.FALSE, Choose.choose(false, null));

    // Test with Long
    assertEquals(Long.valueOf(100L), Choose.choose(null, 100L));
    assertEquals(Long.valueOf(200L), Choose.choose(200L, null));

    // Test with Double
    assertEquals(Double.valueOf(3.14), Choose.choose(null, 3.14));
    assertEquals(Double.valueOf(2.71), Choose.choose(2.71, null));

    // Test with Float
    assertEquals(Float.valueOf(1.5f), Choose.choose(null, 1.5f));
    assertEquals(Float.valueOf(2.5f), Choose.choose(2.5f, null));

    // Test with Byte
    assertEquals(Byte.valueOf((byte) 1), Choose.choose(null, (byte) 1));
    assertEquals(Byte.valueOf((byte) 2), Choose.choose((byte) 2, null));

    // Test with Short
    assertEquals(Short.valueOf((short) 10), Choose.choose(null, (short) 10));
    assertEquals(Short.valueOf((short) 20), Choose.choose((short) 20, null));

    // Test with Character
    assertEquals(Character.valueOf('a'), Choose.choose(null, 'a'));
    assertEquals(Character.valueOf('b'), Choose.choose('b', null));
  }

  @Test
  public void testChooseWithCustomObjects() {
    // Test with custom objects
    TestObject obj1 = new TestObject("first");
    TestObject obj2 = new TestObject("second");
    TestObject obj3 = new TestObject("third");

    assertEquals(obj1, Choose.choose(obj1, obj2));
    assertEquals(obj2, Choose.choose(null, obj2));
    assertEquals(obj3, Choose.choose(null, null, obj3));
    assertNull(Choose.choose(null, null, null));

    // Test with varargs
    assertEquals(obj1, Choose.choose(obj1, obj2, obj3));
    assertEquals(obj2, Choose.choose(null, obj2, obj3));
    assertEquals(obj3, Choose.choose(null, null, obj3));
  }

  @Test
  public void testChooseEdgeCases() {
    // Test with empty string (not null)
    assertEquals("", Choose.choose("", "fallback"));
    assertEquals("", Choose.choose(null, ""));

    // Test with zero values (not null)
    assertEquals(Integer.valueOf(0), Choose.choose(0, 1));
    assertEquals(Integer.valueOf(0), Choose.choose(null, 0));

    // Test with false (not null)
    assertEquals(Boolean.FALSE, Choose.choose(false, true));
    assertEquals(Boolean.FALSE, Choose.choose(null, false));

    // Test method chaining
    String result = Choose.choose(
        Choose.choose(null, null),
        Choose.choose(null, "default")
    );
    assertEquals("default", result);
  }

  // Helper class for testing custom objects
  private static class TestObject {
    private final String value;

    public TestObject(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      TestObject that = (TestObject) obj;
      return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
      return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
      return "TestObject{value='" + value + "'}";
    }
  }
}