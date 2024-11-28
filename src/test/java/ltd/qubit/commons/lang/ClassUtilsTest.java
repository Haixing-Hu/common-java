////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.testbed.Foo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for the ClassUtils class.
 *
 * @author Haixing Hu
 */
public class ClassUtilsTest {

  private static class Inner {}

  @Test
  public void test_getShortClassName_Object() {
    assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class));
    assertEquals("ClassUtilsTest.Inner",
        ClassUtils.getShortClassName(new Inner(), "<null>"));
    assertEquals("String", ClassUtils.getShortClassName("hello", "<null>"));
    assertEquals("<null>", ClassUtils.getShortClassName(null, "<null>"));
  }

  @Test
  public void test_getShortClassName_Class() {
    assertEquals("ClassUtils", ClassUtils.getShortClassName(ClassUtils.class));
    assertEquals("Map.Entry", ClassUtils.getShortClassName(Map.Entry.class));
    assertEquals("", ClassUtils.getShortClassName((Class<?>) null));
  }

  @Test
  public void test_getShortClassName_String() {
    assertEquals("ClassUtils",
        ClassUtils.getShortClassName(ClassUtils.class.getName()));
    assertEquals("Map.Entry",
        ClassUtils.getShortClassName(Map.Entry.class.getName()));
    assertEquals("", ClassUtils.getShortClassName((String) null));
    assertEquals("", ClassUtils.getShortClassName(""));
  }

  @Test
  public void test_getPackageName_Object() {
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageName(ClassUtils.class));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageName(new Inner(), "<null>"));
    assertEquals("<null>", ClassUtils.getPackageName(null, "<null>"));
  }

  @Test
  public void test_getPackageName_Class() {
    assertEquals("java.lang", ClassUtils.getPackageName(String.class));
    assertEquals("java.util", ClassUtils.getPackageName(Map.Entry.class));
    assertEquals("", ClassUtils.getPackageName((Class<?>) null));
  }

  @Test
  public void test_getPackageName_String() {
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageName(ClassUtils.class.getName()));
    assertEquals("java.util",
        ClassUtils.getPackageName(Map.Entry.class.getName()));
    assertEquals("", ClassUtils.getPackageName((String) null));
    assertEquals("", ClassUtils.getPackageName(""));
  }

  @Test
  public void test_getAllSuperclasses_Class() {
    final List<Class<?>> list = ClassUtils.getAllSuperclasses(ClassY.class);
    assertEquals(2, list.size());
    assertEquals(ClassX.class, list.get(0));
    assertEquals(Object.class, list.get(1));
    assertNull(ClassUtils.getAllSuperclasses(null));
  }

  @Test
  public void test_getAllInterfaces_Class() {
    final List<Class<?>> list = ClassUtils.getAllInterfaces(ClassY.class);
    assertEquals(6, list.size());
    assertEquals(InterfaceB.class, list.get(0));
    assertEquals(InterfaceC.class, list.get(1));
    assertEquals(InterfaceD.class, list.get(2));
    assertEquals(InterfaceE.class, list.get(3));
    assertEquals(InterfaceF.class, list.get(4));
    assertEquals(InterfaceA.class, list.get(5));

    assertNull(ClassUtils.getAllInterfaces(null));
  }

  private interface InterfaceA {
    //  empty
  }

  private interface InterfaceB {
    //  empty
  }

  private interface InterfaceC extends InterfaceD, InterfaceE {
    //  empty
  }

  private interface InterfaceD {
    //  empty
  }

  private interface InterfaceE extends InterfaceF {
    //  empty
  }

  private interface InterfaceF {
    //  empty
  }

  private static class ClassX implements InterfaceB, InterfaceA, InterfaceE {
    //  empty
  }

  private static class ClassY extends ClassX implements InterfaceB, InterfaceC {
    //  empty
  }

  @Test
  public void test_convertClassNamesToClassUtils_List() {
    final List<String> list = new ArrayList<>();
    List<Class<?>> result = ClassUtils.namesToClasses(list);
    assertEquals(0, result.size());

    list.add("java.lang.String");
    list.add("java.lang.xxx");
    list.add("java.lang.Object");
    result = ClassUtils.namesToClasses(list);
    assertEquals(3, result.size());
    assertEquals(String.class, result.get(0));
    assertNull(result.get(1));
    assertEquals(Object.class, result.get(2));

    assertNull(ClassUtils.namesToClasses(null));
  }

  @Test
  public void test_convertClassUtilsToClassNames_List() {
    final List<Class<?>> list = new ArrayList<>();
    List<String> result = ClassUtils.classesToNames(list);
    assertEquals(0, result.size());

    list.add(String.class);
    list.add(null);
    list.add(Object.class);
    result = ClassUtils.classesToNames(list);
    assertEquals(3, result.size());
    assertEquals("java.lang.String", result.get(0));
    assertNull(result.get(1));
    assertEquals("java.lang.Object", result.get(2));

    assertNull(ClassUtils.classesToNames(null));
  }

  @Test
  public void test_isInnerClass_Class() {
    assertTrue(ClassUtils.isInnerClass(Inner.class));
    assertTrue(ClassUtils.isInnerClass(Entry.class));
    assertFalse(ClassUtils.isInnerClass(getClass()));
    assertFalse(ClassUtils.isInnerClass(String.class));
    assertFalse(ClassUtils.isInnerClass(null));
  }

  @Test
  public void test_isAssignable_ClassArray_ClassArray() {
    final Class<?>[] array2 = new Class<?>[]{Object.class, Object.class};
    final Class<?>[] array1 = new Class<?>[]{Object.class};
    final Class<?>[] array1s = new Class<?>[]{String.class};
    final Class<?>[] array0 = new Class<?>[]{};

    assertFalse(ClassUtils.isAssignable(array1, array2));
    assertFalse(ClassUtils.isAssignable(null, array2));
    assertTrue(ClassUtils.isAssignable(null, array0));
    assertTrue(ClassUtils.isAssignable(array0, array0));
    assertTrue(ClassUtils.isAssignable(array0, null));
    assertTrue(ClassUtils.isAssignable((Class<?>[]) null, null));

    assertFalse(ClassUtils.isAssignable(array1, array1s));
    assertTrue(ClassUtils.isAssignable(array1s, array1s));
    assertTrue(ClassUtils.isAssignable(array1s, array1));
  }

  @Test
  public void test_isAssignable() {
    assertFalse(ClassUtils.isAssignable((Class<?>) null, null));
    assertFalse(ClassUtils.isAssignable(String.class, null));

    assertTrue(ClassUtils.isAssignable(null, Object.class));
    assertTrue(ClassUtils.isAssignable(null, Integer.class));
    assertFalse(ClassUtils.isAssignable(null, Integer.TYPE));
    assertTrue(ClassUtils.isAssignable(String.class, Object.class));
    assertTrue(ClassUtils.isAssignable(String.class, String.class));
    assertFalse(ClassUtils.isAssignable(Object.class, String.class));
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.class));
    assertTrue(ClassUtils.isAssignable(Integer.class, Integer.TYPE));
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE));
    assertTrue(ClassUtils.isAssignable(Integer.class, Integer.class));
  }

  @Test
  public void test_isAssignable_Widening() {
    // test byte conversions
    assertFalse(ClassUtils.isAssignable(Byte.TYPE, Character.TYPE),
        "byte -> char");
    assertTrue(ClassUtils.isAssignable(Byte.TYPE, Byte.TYPE),
        "byte -> byte");
    assertTrue(ClassUtils.isAssignable(Byte.TYPE, Short.TYPE),
        "byte -> short");
    assertTrue(ClassUtils.isAssignable(Byte.TYPE, Integer.TYPE),
        "byte -> int");
    assertTrue(ClassUtils.isAssignable(Byte.TYPE, Long.TYPE),
        "byte -> long");
    assertTrue(ClassUtils.isAssignable(Byte.TYPE, Float.TYPE),
        "byte -> float");
    assertTrue(ClassUtils.isAssignable(Byte.TYPE, Double.TYPE),
        "byte -> double");
    assertFalse(ClassUtils.isAssignable(Byte.TYPE, Boolean.TYPE),
        "byte -> boolean");

    // test short conversions
    assertFalse(ClassUtils.isAssignable(Short.TYPE, Character.TYPE),
        "short -> char");
    assertFalse(ClassUtils.isAssignable(Short.TYPE, Byte.TYPE),
        "short -> byte");
    assertTrue(ClassUtils.isAssignable(Short.TYPE, Short.TYPE),
        "short -> short");
    assertTrue(ClassUtils.isAssignable(Short.TYPE, Integer.TYPE),
        "short -> int");
    assertTrue(ClassUtils.isAssignable(Short.TYPE, Long.TYPE),
        "short -> long");
    assertTrue(ClassUtils.isAssignable(Short.TYPE, Float.TYPE),
        "short -> float");
    assertTrue(ClassUtils.isAssignable(Short.TYPE, Double.TYPE),
        "short -> double");
    assertFalse(ClassUtils.isAssignable(Short.TYPE, Boolean.TYPE),
        "short -> boolean");

    // test char conversions
    assertTrue(ClassUtils.isAssignable(Character.TYPE, Character.TYPE),
        "char -> char");
    assertFalse(ClassUtils.isAssignable(Character.TYPE, Byte.TYPE),
        "char -> byte");
    assertFalse(ClassUtils.isAssignable(Character.TYPE, Short.TYPE),
        "char -> short");
    assertTrue(ClassUtils.isAssignable(Character.TYPE, Integer.TYPE),
        "char -> int");
    assertTrue(ClassUtils.isAssignable(Character.TYPE, Long.TYPE),
        "char -> long");
    assertTrue(ClassUtils.isAssignable(Character.TYPE, Float.TYPE),
        "char -> float");
    assertTrue(ClassUtils.isAssignable(Character.TYPE, Double.TYPE),
        "char -> double");
    assertFalse(ClassUtils.isAssignable(Character.TYPE, Boolean.TYPE),
        "char -> boolean");

    // test int conversions
    assertFalse(ClassUtils.isAssignable(Integer.TYPE, Character.TYPE),
        "int -> char");
    assertFalse(ClassUtils.isAssignable(Integer.TYPE, Byte.TYPE),
        "int -> byte");
    assertFalse(ClassUtils.isAssignable(Integer.TYPE, Short.TYPE),
        "int -> short");
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE),
        "int -> int");
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Long.TYPE),
        "int -> long");
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Float.TYPE),
        "int -> float");
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Double.TYPE),
        "int -> double");
    assertFalse(ClassUtils.isAssignable(Integer.TYPE, Boolean.TYPE),
        "int -> boolean");

    // test long conversions
    assertFalse(ClassUtils.isAssignable(Long.TYPE, Character.TYPE),
        "long -> char");
    assertFalse(ClassUtils.isAssignable(Long.TYPE, Byte.TYPE),
        "long -> byte");
    assertFalse(ClassUtils.isAssignable(Long.TYPE, Short.TYPE),
        "long -> short");
    assertFalse(ClassUtils.isAssignable(Long.TYPE, Integer.TYPE),
        "long -> int");
    assertTrue(ClassUtils.isAssignable(Long.TYPE, Long.TYPE),
        "long -> long");
    assertTrue(ClassUtils.isAssignable(Long.TYPE, Float.TYPE),
        "long -> float");
    assertTrue(ClassUtils.isAssignable(Long.TYPE, Double.TYPE),
        "long -> double");
    assertFalse(ClassUtils.isAssignable(Long.TYPE, Boolean.TYPE),
        "long -> boolean");

    // test float conversions
    assertFalse(ClassUtils.isAssignable(Float.TYPE, Character.TYPE),
        "float -> char");
    assertFalse(ClassUtils.isAssignable(Float.TYPE, Byte.TYPE),
        "float -> byte");
    assertFalse(ClassUtils.isAssignable(Float.TYPE, Short.TYPE),
        "float -> short");
    assertFalse(ClassUtils.isAssignable(Float.TYPE, Integer.TYPE),
        "float -> int");
    assertFalse(ClassUtils.isAssignable(Float.TYPE, Long.TYPE),
        "float -> long");
    assertTrue(ClassUtils.isAssignable(Float.TYPE, Float.TYPE),
        "float -> float");
    assertTrue(ClassUtils.isAssignable(Float.TYPE, Double.TYPE),
        "float -> double");
    assertFalse(ClassUtils.isAssignable(Float.TYPE, Boolean.TYPE),
        "float -> boolean");

    // test float conversions
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Character.TYPE),
        "double -> char");
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Byte.TYPE),
        "double -> byte");
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Short.TYPE),
        "double -> short");
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Integer.TYPE),
        "double -> int");
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Long.TYPE),
        "double -> long");
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Float.TYPE),
        "double -> float");
    assertTrue(ClassUtils.isAssignable(Double.TYPE, Double.TYPE),
        "double -> double");
    assertFalse(ClassUtils.isAssignable(Double.TYPE, Boolean.TYPE),
        "double -> boolean");

    // test float conversions
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Character.TYPE),
        "boolean -> char");
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Byte.TYPE),
        "boolean -> byte");
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Short.TYPE),
        "boolean -> short");
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Integer.TYPE),
        "boolean -> int");
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Long.TYPE),
        "boolean -> long");
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Float.TYPE),
        "boolean -> float");
    assertFalse(ClassUtils.isAssignable(Boolean.TYPE, Double.TYPE),
        "boolean -> double");
    assertTrue(ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE),
        "boolean -> boolean");
  }

  @Test
  public void testPrimitiveToWrapper() {

    // test primitive classes
    assertEquals(
        Boolean.class, ClassUtils.primitiveToWrapper(Boolean.TYPE),
        "boolean -> Boolean.class");
    assertEquals(
        Byte.class, ClassUtils.primitiveToWrapper(Byte.TYPE),
        "byte -> Byte.class");
    assertEquals(
        Character.class, ClassUtils.primitiveToWrapper(Character.TYPE),
        "char -> Character.class");
    assertEquals(
        Short.class, ClassUtils.primitiveToWrapper(Short.TYPE),
        "short -> Short.class");
    assertEquals(
        Integer.class, ClassUtils.primitiveToWrapper(Integer.TYPE),
        "int -> Integer.class");
    assertEquals(
        Long.class, ClassUtils.primitiveToWrapper(Long.TYPE),
        "long -> Long.class");
    assertEquals(
        Double.class, ClassUtils.primitiveToWrapper(Double.TYPE),
        "double -> Double.class");
    assertEquals(
        Float.class, ClassUtils.primitiveToWrapper(Float.TYPE),
        "float -> Float.class");

    // test a few other classes
    assertEquals(
        String.class, ClassUtils.primitiveToWrapper(String.class),
        "String.class -> String.class");
    assertEquals(
        ClassUtils.class,
        ClassUtils.primitiveToWrapper(ClassUtils.class),
        "ClassUtils.class -> ClassUtils.class");
    assertEquals(
        Void.class, ClassUtils.primitiveToWrapper(Void.TYPE),
        "Void.TYPE -> Void.class");

    // test null
    assertNull(
        ClassUtils.primitiveToWrapper(null), "null -> null");
  }

  @Test
  public void testPrimitivesToWrappers() {
    // test null
    assertNull(ClassUtils.primitivesToWrappers(null), "null -> null");
    // test empty array
    assertArrayEquals(ArrayUtils.EMPTY_CLASS_ARRAY,
        ClassUtils.primitivesToWrappers(ArrayUtils.EMPTY_CLASS_ARRAY),
        "empty -> empty");

    // test an array of various classes
    final Class<?>[] primitives = new Class<?>[]{
        Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE,
        Integer.TYPE, Long.TYPE, Double.TYPE, Float.TYPE,
        String.class, ClassUtils.class
    };
    final Class<?>[] wrappers = ClassUtils.primitivesToWrappers(primitives);

    for (int i = 0; i < primitives.length; i++) {
      // test each returned wrapper
      final Class<?> primitive = primitives[i];
      final Class<?> expectedWrapper = ClassUtils.primitiveToWrapper(primitive);

      assertEquals(expectedWrapper, wrappers[i],
          primitive + " -> " + expectedWrapper);
    }

    // test an array of no primitive classes
    final Class<?>[] noPrimitives = new Class<?>[]{
        String.class, ClassUtils.class, Void.TYPE
    };
    // This used to return the exact same array, but no longer does.
    assertNotSame(noPrimitives, ClassUtils.primitivesToWrappers(noPrimitives),
        "unmodified");
  }

  @Test
  public void testWrapperToPrimitive() {
    // an array with classes to convert
    final Class<?>[] primitives = {
        Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE,
        Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
    };
    for (final Class<?> primitive : primitives) {
      final Class<?> wrapperCls = ClassUtils.primitiveToWrapper(primitive);
      assertFalse(wrapperCls.isPrimitive(), "Still primitive");
      assertEquals(primitive,
          ClassUtils.wrapperToPrimitive(wrapperCls),
          wrapperCls + " -> " + primitive);
    }
  }

  @Test
  public void testWrapperToPrimitiveNoWrapper() {
    assertNull(ClassUtils.wrapperToPrimitive(String.class),
        "Wrong result for non wrapper class");
  }

  @Test
  public void testWrapperToPrimitiveNull() {
    assertNull(ClassUtils.wrapperToPrimitive(null), "Wrong result for null class");
  }

  @Test
  public void testWrappersToPrimitives() {
    // an array with classes to test
    final Class<?>[] classes = {
        Boolean.class, Byte.class, Character.class, Short.class,
        Integer.class, Long.class, Float.class, Double.class,
        String.class, ClassUtils.class, null
    };

    final Class<?>[] primitives = ClassUtils.wrappersToPrimitives(classes);
    // now test the result
    assertEquals(classes.length, primitives.length,
        "Wrong length of result array");
    for (int i = 0; i < classes.length; i++) {
      final Class<?> expectedPrimitive = ClassUtils.wrapperToPrimitive(classes[i]);
      assertEquals(expectedPrimitive, primitives[i],
          classes[i] + " -> " + expectedPrimitive);
    }
  }

  @Test
  public void testWrappersToPrimitivesNull() {
    assertNull(ClassUtils.wrappersToPrimitives(null),
        "Wrong result for null input");
  }

  @Test
  public void testWrappersToPrimitivesEmpty() {
    final Class<?>[] empty = new Class<?>[0];
    assertArrayEquals(empty, ClassUtils.wrappersToPrimitives(empty),
        "Wrong result for empty input");
  }

  @Test
  public void testGetClassClassNotFound() throws Exception {
    assertGetClassThrowsClassNotFound("bool");
    assertGetClassThrowsClassNotFound("bool[]");
    assertGetClassThrowsClassNotFound("integer[]");
  }

  @Test
  public void testGetClassInvalidArguments() throws Exception {
    assertGetClassThrowsNullPointerException(null);
    assertGetClassThrowsClassNotFound("[][][]");
    assertGetClassThrowsClassNotFound("[[]");
    assertGetClassThrowsClassNotFound("[");
    assertGetClassThrowsClassNotFound("java.lang.String][");
    assertGetClassThrowsClassNotFound(".hello.world");
    assertGetClassThrowsClassNotFound("hello..world");
  }

  @Test
  public void testWithInterleavingWhitespace() throws ClassNotFoundException {
    assertEquals(int[].class, ClassUtils.getClass(" int [ ] "));
    assertEquals(long[].class, ClassUtils.getClass("\rlong\t[\n]\r"));
    assertEquals(short[].class,
        ClassUtils.getClass("\tshort                \t\t[]"));
    assertEquals(byte[].class, ClassUtils.getClass("byte[\t\t\n\r]   "));
  }

  @Test
  public void testGetClassByNormalNameArrays() throws ClassNotFoundException {
    assertEquals(int[].class, ClassUtils.getClass("int[]"));
    assertEquals(long[].class, ClassUtils.getClass("long[]"));
    assertEquals(short[].class, ClassUtils.getClass("short[]"));
    assertEquals(byte[].class, ClassUtils.getClass("byte[]"));
    assertEquals(char[].class, ClassUtils.getClass("char[]"));
    assertEquals(float[].class, ClassUtils.getClass("float[]"));
    assertEquals(double[].class, ClassUtils.getClass("double[]"));
    assertEquals(boolean[].class, ClassUtils.getClass("boolean[]"));
    assertEquals(String[].class, ClassUtils.getClass("java.lang.String[]"));
  }

  @Test
  public void testGetClassByNormalNameArrays2D() throws ClassNotFoundException {
    assertEquals(int[][].class, ClassUtils.getClass("int[][]"));
    assertEquals(long[][].class, ClassUtils.getClass("long[][]"));
    assertEquals(short[][].class, ClassUtils.getClass("short[][]"));
    assertEquals(byte[][].class, ClassUtils.getClass("byte[][]"));
    assertEquals(char[][].class, ClassUtils.getClass("char[][]"));
    assertEquals(float[][].class, ClassUtils.getClass("float[][]"));
    assertEquals(double[][].class, ClassUtils.getClass("double[][]"));
    assertEquals(boolean[][].class, ClassUtils.getClass("boolean[][]"));
    assertEquals(String[][].class, ClassUtils.getClass("java.lang.String[][]"));
  }

  @Test
  public void testGetClassWithArrayClassUtils2D() throws Exception {
    assertGetClassReturnsClass(String[][].class);
    assertGetClassReturnsClass(int[][].class);
    assertGetClassReturnsClass(long[][].class);
    assertGetClassReturnsClass(short[][].class);
    assertGetClassReturnsClass(byte[][].class);
    assertGetClassReturnsClass(char[][].class);
    assertGetClassReturnsClass(float[][].class);
    assertGetClassReturnsClass(double[][].class);
    assertGetClassReturnsClass(boolean[][].class);
  }

  @Test
  public void testGetClassWithArrayClassUtils() throws Exception {
    assertGetClassReturnsClass(String[].class);
    assertGetClassReturnsClass(int[].class);
    assertGetClassReturnsClass(long[].class);
    assertGetClassReturnsClass(short[].class);
    assertGetClassReturnsClass(byte[].class);
    assertGetClassReturnsClass(char[].class);
    assertGetClassReturnsClass(float[].class);
    assertGetClassReturnsClass(double[].class);
    assertGetClassReturnsClass(boolean[].class);
  }

  @Test
  public void testGetClassRawPrimitives() throws ClassNotFoundException {
    assertEquals(int.class, ClassUtils.getClass("int"));
    assertEquals(long.class, ClassUtils.getClass("long"));
    assertEquals(short.class, ClassUtils.getClass("short"));
    assertEquals(byte.class, ClassUtils.getClass("byte"));
    assertEquals(char.class, ClassUtils.getClass("char"));
    assertEquals(float.class, ClassUtils.getClass("float"));
    assertEquals(double.class, ClassUtils.getClass("double"));
    assertEquals(boolean.class, ClassUtils.getClass("boolean"));
  }

  private void assertGetClassReturnsClass(final Class<?> c) throws Exception {
    assertEquals(c, ClassUtils.getClass(c.getName()));
  }

  private void assertGetClassThrowsException(final String className,
      final Class<?> exceptionType) {
    try {
      ClassUtils.getClass(className);
      fail("ClassUtils.getClass() should fail with an exception of type "
          + exceptionType.getName() + " when given class name \"" + className
          + "\".");
    } catch (final Exception e) {
      assertTrue(exceptionType.isAssignableFrom(e.getClass()));
    }
  }

  private void assertGetClassThrowsNullPointerException(final String className) {
    assertGetClassThrowsException(className, NullPointerException.class);
  }

  private void assertGetClassThrowsClassNotFound(final String className)
      throws Exception {
    assertGetClassThrowsException(className, ClassNotFoundException.class);
  }

  // Show the Java bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
  // We may have to delete this if a JDK fixes the bug.
  @Test
  public void testShowJavaBug() throws Exception {
    // Tests with Collections$UnmodifiableSet
    final Set<String> set = Collections.unmodifiableSet(new HashSet<>());
    final Method isEmptyMethod = set.getClass().getMethod("isEmpty");
    try {
      isEmptyMethod.invoke(set);
      fail("Failed to throw IllegalAccessException as expected");
    } catch (final IllegalAccessException iae) {
      // expected
    }
  }

  @Test
  public void testGetPublicMethod() throws Exception {
    // Tests with Collections$UnmodifiableSet
    final Set<String> set = Collections.unmodifiableSet(new HashSet<>());
    final Method isEmptyMethod = ClassUtils
        .getPublicMethod(set.getClass(), "isEmpty", new Class<?>[0]);
    assertTrue(
        Modifier.isPublic(isEmptyMethod.getDeclaringClass().getModifiers()));

    try {
      isEmptyMethod.invoke(set);
    } catch (final IllegalAccessException iae) {
      fail("Should not have thrown IllegalAccessException");
    }

    // Tests with a public Class
    final Method toStringMethod = ClassUtils
        .getPublicMethod(Object.class, "toString", new Class<?>[0]);
    assertEquals(Object.class.getMethod("toString"), toStringMethod);
  }

  @Test
  public void testToClass_object() {
    assertArrayEquals(null, ClassUtils.toClass(null));

    assertSame(ArrayUtils.EMPTY_CLASS_ARRAY,
        ClassUtils.toClass(new Class<?>[0]));

    final Object[] array = new Object[3];
    array[0] = "Test";
    array[1] = 1;
    array[2] = 99D;

    final Class<?>[] results = ClassUtils.toClass(array);
    assertEquals("String", ClassUtils.getShortClassName(results[0]));
    assertEquals("Integer", ClassUtils.getShortClassName(results[1]));
    assertEquals("Double", ClassUtils.getShortClassName(results[2]));
  }

  @Test
  public void test_getShortCanonicalName_Object() {
    assertEquals("<null>", ClassUtils.getShortCanonicalName(null, "<null>"));
    assertEquals("ClassUtils",
        ClassUtils.getShortCanonicalName(ClassUtils.class));
    assertEquals("ClassUtils[]",
        ClassUtils.getShortCanonicalName(new ClassUtils[0], "<null>"));
    assertEquals("ClassUtils[][]",
        ClassUtils.getShortCanonicalName(new ClassUtils[0][0], "<null>"));
    assertEquals("int[]",
        ClassUtils.getShortCanonicalName(new int[0], "<null>"));
    assertEquals("int[][]",
        ClassUtils.getShortCanonicalName(new int[0][0], "<null>"));
  }

  @Test
  public void test_getShortCanonicalName_Class() {
    assertEquals("ClassUtils",
        ClassUtils.getShortCanonicalName(ClassUtils.class));
    assertEquals("ClassUtils[]",
        ClassUtils.getShortCanonicalName(ClassUtils[].class));
    assertEquals("ClassUtils[][]",
        ClassUtils.getShortCanonicalName(ClassUtils[][].class));
    assertEquals("int[]", ClassUtils.getShortCanonicalName(int[].class));
    assertEquals("int[][]", ClassUtils.getShortCanonicalName(int[][].class));
  }

  @Test
  public void test_getShortCanonicalName_String() {
    assertEquals("ClassUtils", ClassUtils.getShortCanonicalName("ClassUtils"));
    assertEquals("ClassUtils[]", ClassUtils
        .getShortCanonicalName("[Lltd.qubit.commons.lang.ClassUtils;"));
    assertEquals("ClassUtils[][]", ClassUtils
        .getShortCanonicalName("[[Lltd.qubit.commons.lang.ClassUtils;"));
    assertEquals("ClassUtils[]",
        ClassUtils.getShortCanonicalName("ClassUtils[]"));
    assertEquals("ClassUtils[][]",
        ClassUtils.getShortCanonicalName("ClassUtils[][]"));
    assertEquals("int[]", ClassUtils.getShortCanonicalName("[I"));
    assertEquals("int[][]", ClassUtils.getShortCanonicalName("[[I"));
    assertEquals("int[]", ClassUtils.getShortCanonicalName("int[]"));
    assertEquals("int[][]", ClassUtils.getShortCanonicalName("int[][]"));
  }

  @Test
  public void test_getPackageCanonicalName_Object() {
    assertEquals("<null>", ClassUtils.getPackageCanonicalName(null, "<null>"));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageCanonicalName(ClassUtils.class));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageCanonicalName(new ClassUtils[0], "<null>"));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageCanonicalName(new ClassUtils[0][0], "<null>"));
    assertEquals("", ClassUtils.getPackageCanonicalName(new int[0], "<null>"));
    assertEquals("",
        ClassUtils.getPackageCanonicalName(new int[0][0], "<null>"));
  }

  @Test
  public void test_getPackageCanonicalName_Class() {
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageCanonicalName(ClassUtils.class));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageCanonicalName(ClassUtils[].class));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils.getPackageCanonicalName(ClassUtils[][].class));
    assertEquals("", ClassUtils.getPackageCanonicalName(int[].class));
    assertEquals("", ClassUtils.getPackageCanonicalName(int[][].class));
  }

  @Test
  public void test_getPackageCanonicalName_String() {
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils
            .getPackageCanonicalName("ltd.qubit.commons.lang.ClassUtils"));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils
            .getPackageCanonicalName("[Lltd.qubit.commons.lang.ClassUtils;"));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils
            .getPackageCanonicalName("[[Lltd.qubit.commons.lang.ClassUtils;"));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils
            .getPackageCanonicalName("ltd.qubit.commons.lang.ClassUtils[]"));
    assertEquals("ltd.qubit.commons.lang",
        ClassUtils
            .getPackageCanonicalName("ltd.qubit.commons.lang.ClassUtils[][]"));
    assertEquals("", ClassUtils.getPackageCanonicalName("[I"));
    assertEquals("", ClassUtils.getPackageCanonicalName("[[I"));
    assertEquals("", ClassUtils.getPackageCanonicalName("int[]"));
    assertEquals("", ClassUtils.getPackageCanonicalName("int[][]"));
  }


  @Test
  public void testIsJdkBuiltin() {
    assertTrue(ClassUtils.isJdkBuiltIn(String.class));
    assertTrue(ClassUtils.isJdkBuiltIn(Nullable.class));
    assertFalse(ClassUtils.isJdkBuiltIn(Foo.class));
  }

  @Test
  public void testGetDefaultValueObject() {
    Object v = ClassUtils.getDefaultValueObject(boolean.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Boolean.class);
    assertEquals(v, Boolean.FALSE);

    v = ClassUtils.getDefaultValueObject(char.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Character.class);
    assertEquals(v, '\0');

    v = ClassUtils.getDefaultValueObject(byte.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Byte.class);
    assertEquals(v, (byte) 0);

    v = ClassUtils.getDefaultValueObject(short.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Short.class);
    assertEquals(v, (short) 0);

    v = ClassUtils.getDefaultValueObject(int.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Integer.class);
    assertEquals(v, 0);

    v = ClassUtils.getDefaultValueObject(long.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Long.class);
    assertEquals(v, 0L);

    v = ClassUtils.getDefaultValueObject(float.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Float.class);
    assertEquals(v, 0.0f);

    v = ClassUtils.getDefaultValueObject(double.class);
    assertNotNull(v);
    assertEquals(v.getClass(), Double.class);
    assertEquals(v, 0.0);

    v = ClassUtils.getDefaultValueObject(Boolean.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Character.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Byte.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Short.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Integer.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Long.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Float.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Double.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(String.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(Date.class);
    assertNull(v);

    v = ClassUtils.getDefaultValueObject(MyClass.class);
    assertNull(v);
  }

  private static class MyClass {}
}
