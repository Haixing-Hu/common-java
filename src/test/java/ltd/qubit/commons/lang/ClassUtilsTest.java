////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    final List<Class<?>> list = ClassUtils.getAllSuperclasses(CY.class);
    assertEquals(2, list.size());
    assertEquals(CX.class, list.get(0));
    assertEquals(Object.class, list.get(1));
    assertEquals(null, ClassUtils.getAllSuperclasses(null));
  }

  @Test
  public void test_getAllInterfaces_Class() {
    final List<Class<?>> list = ClassUtils.getAllInterfaces(CY.class);
    assertEquals(6, list.size());
    assertEquals(IB.class, list.get(0));
    assertEquals(IC.class, list.get(1));
    assertEquals(ID.class, list.get(2));
    assertEquals(IE.class, list.get(3));
    assertEquals(IF.class, list.get(4));
    assertEquals(IA.class, list.get(5));

    assertEquals(null, ClassUtils.getAllInterfaces(null));
  }

  private interface IA {

  }

  private interface IB {

  }

  private interface IC extends ID, IE {

  }

  private interface ID {

  }

  private interface IE extends IF {

  }

  private interface IF {

  }

  private static class CX implements IB, IA, IE {

  }

  private static class CY extends CX implements IB, IC {

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
    assertEquals(null, result.get(1));
    assertEquals(Object.class, result.get(2));

    assertEquals(null, ClassUtils.namesToClasses(null));
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
    assertEquals(null, result.get(1));
    assertEquals("java.lang.Object", result.get(2));

    assertEquals(null, ClassUtils.classesToNames(null));
  }

  @Test
  public void test_isInnerClass_Class() {
    assertEquals(true, ClassUtils.isInnerClass(Inner.class));
    assertEquals(true, ClassUtils.isInnerClass(Map.Entry.class));
    assertEquals(false, ClassUtils.isInnerClass(getClass()));
    assertEquals(false, ClassUtils.isInnerClass(String.class));
    assertEquals(false, ClassUtils.isInnerClass(null));
  }

  @Test
  public void test_isAssignable_ClassArray_ClassArray() {
    final Class<?>[] array2 = new Class<?>[]{Object.class, Object.class};
    final Class<?>[] array1 = new Class<?>[]{Object.class};
    final Class<?>[] array1s = new Class<?>[]{String.class};
    final Class<?>[] array0 = new Class<?>[]{};

    assertEquals(false, ClassUtils.isAssignable(array1, array2));
    assertEquals(false, ClassUtils.isAssignable(null, array2));
    assertEquals(true, ClassUtils.isAssignable(null, array0));
    assertEquals(true, ClassUtils.isAssignable(array0, array0));
    assertEquals(true, ClassUtils.isAssignable(array0, null));
    assertEquals(true, ClassUtils.isAssignable((Class<?>[]) null, null));

    assertEquals(false, ClassUtils.isAssignable(array1, array1s));
    assertEquals(true, ClassUtils.isAssignable(array1s, array1s));
    assertEquals(true, ClassUtils.isAssignable(array1s, array1));
  }

  @Test
  public void test_isAssignable() {
    assertEquals(false, ClassUtils.isAssignable((Class<?>) null, null));
    assertEquals(false, ClassUtils.isAssignable(String.class, null));

    assertEquals(true, ClassUtils.isAssignable(null, Object.class));
    assertEquals(true, ClassUtils.isAssignable(null, Integer.class));
    assertEquals(false, ClassUtils.isAssignable(null, Integer.TYPE));
    assertEquals(true, ClassUtils.isAssignable(String.class, Object.class));
    assertEquals(true, ClassUtils.isAssignable(String.class, String.class));
    assertEquals(false, ClassUtils.isAssignable(Object.class, String.class));
    assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Integer.class));
    assertEquals(true, ClassUtils.isAssignable(Integer.class, Integer.TYPE));
    assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE));
    assertEquals(true, ClassUtils.isAssignable(Integer.class, Integer.class));
  }

  @Test
  public void test_isAssignable_Widening() {
    // test byte conversions
    assertEquals(false, ClassUtils.isAssignable(Byte.TYPE, Character.TYPE),
        "byte -> char");
    assertEquals(true, ClassUtils.isAssignable(Byte.TYPE, Byte.TYPE),
        "byte -> byte");
    assertEquals(true, ClassUtils.isAssignable(Byte.TYPE, Short.TYPE),
        "byte -> short");
    assertEquals(true, ClassUtils.isAssignable(Byte.TYPE, Integer.TYPE),
        "byte -> int");
    assertEquals(true, ClassUtils.isAssignable(Byte.TYPE, Long.TYPE),
        "byte -> long");
    assertEquals(true, ClassUtils.isAssignable(Byte.TYPE, Float.TYPE),
        "byte -> float");
    assertEquals(true, ClassUtils.isAssignable(Byte.TYPE, Double.TYPE),
        "byte -> double");
    assertEquals(false, ClassUtils.isAssignable(Byte.TYPE, Boolean.TYPE),
        "byte -> boolean");

    // test short conversions
    assertEquals(false, ClassUtils.isAssignable(Short.TYPE, Character.TYPE),
        "short -> char");
    assertEquals(false, ClassUtils.isAssignable(Short.TYPE, Byte.TYPE),
        "short -> byte");
    assertEquals(true, ClassUtils.isAssignable(Short.TYPE, Short.TYPE),
        "short -> short");
    assertEquals(true, ClassUtils.isAssignable(Short.TYPE, Integer.TYPE),
        "short -> int");
    assertEquals(true, ClassUtils.isAssignable(Short.TYPE, Long.TYPE),
        "short -> long");
    assertEquals(true, ClassUtils.isAssignable(Short.TYPE, Float.TYPE),
        "short -> float");
    assertEquals(true, ClassUtils.isAssignable(Short.TYPE, Double.TYPE),
        "short -> double");
    assertEquals(false, ClassUtils.isAssignable(Short.TYPE, Boolean.TYPE),
        "short -> boolean");

    // test char conversions
    assertEquals(true, ClassUtils.isAssignable(Character.TYPE, Character.TYPE),
        "char -> char");
    assertEquals(false, ClassUtils.isAssignable(Character.TYPE, Byte.TYPE),
        "char -> byte");
    assertEquals(false, ClassUtils.isAssignable(Character.TYPE, Short.TYPE),
        "char -> short");
    assertEquals(true, ClassUtils.isAssignable(Character.TYPE, Integer.TYPE),
        "char -> int");
    assertEquals(true, ClassUtils.isAssignable(Character.TYPE, Long.TYPE),
        "char -> long");
    assertEquals(true, ClassUtils.isAssignable(Character.TYPE, Float.TYPE),
        "char -> float");
    assertEquals(true, ClassUtils.isAssignable(Character.TYPE, Double.TYPE),
        "char -> double");
    assertEquals(false, ClassUtils.isAssignable(Character.TYPE, Boolean.TYPE),
        "char -> boolean");

    // test int conversions
    assertEquals(false, ClassUtils.isAssignable(Integer.TYPE, Character.TYPE),
        "int -> char");
    assertEquals(false, ClassUtils.isAssignable(Integer.TYPE, Byte.TYPE),
        "int -> byte");
    assertEquals(false, ClassUtils.isAssignable(Integer.TYPE, Short.TYPE),
        "int -> short");
    assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE),
        "int -> int");
    assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Long.TYPE),
        "int -> long");
    assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Float.TYPE),
        "int -> float");
    assertEquals(true, ClassUtils.isAssignable(Integer.TYPE, Double.TYPE),
        "int -> double");
    assertEquals(false, ClassUtils.isAssignable(Integer.TYPE, Boolean.TYPE),
        "int -> boolean");

    // test long conversions
    assertEquals(false, ClassUtils.isAssignable(Long.TYPE, Character.TYPE),
        "long -> char");
    assertEquals(false, ClassUtils.isAssignable(Long.TYPE, Byte.TYPE),
        "long -> byte");
    assertEquals(false, ClassUtils.isAssignable(Long.TYPE, Short.TYPE),
        "long -> short");
    assertEquals(false, ClassUtils.isAssignable(Long.TYPE, Integer.TYPE),
        "long -> int");
    assertEquals(true, ClassUtils.isAssignable(Long.TYPE, Long.TYPE),
        "long -> long");
    assertEquals(true, ClassUtils.isAssignable(Long.TYPE, Float.TYPE),
        "long -> float");
    assertEquals(true, ClassUtils.isAssignable(Long.TYPE, Double.TYPE),
        "long -> double");
    assertEquals(false, ClassUtils.isAssignable(Long.TYPE, Boolean.TYPE),
        "long -> boolean");

    // test float conversions
    assertEquals(false, ClassUtils.isAssignable(Float.TYPE, Character.TYPE),
        "float -> char");
    assertEquals(false, ClassUtils.isAssignable(Float.TYPE, Byte.TYPE),
        "float -> byte");
    assertEquals(false, ClassUtils.isAssignable(Float.TYPE, Short.TYPE),
        "float -> short");
    assertEquals(false, ClassUtils.isAssignable(Float.TYPE, Integer.TYPE),
        "float -> int");
    assertEquals(false, ClassUtils.isAssignable(Float.TYPE, Long.TYPE),
        "float -> long");
    assertEquals(true, ClassUtils.isAssignable(Float.TYPE, Float.TYPE),
        "float -> float");
    assertEquals(true, ClassUtils.isAssignable(Float.TYPE, Double.TYPE),
        "float -> double");
    assertEquals(false, ClassUtils.isAssignable(Float.TYPE, Boolean.TYPE),
        "float -> boolean");

    // test float conversions
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Character.TYPE),
        "double -> char");
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Byte.TYPE),
        "double -> byte");
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Short.TYPE),
        "double -> short");
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Integer.TYPE),
        "double -> int");
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Long.TYPE),
        "double -> long");
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Float.TYPE),
        "double -> float");
    assertEquals(true, ClassUtils.isAssignable(Double.TYPE, Double.TYPE),
        "double -> double");
    assertEquals(false, ClassUtils.isAssignable(Double.TYPE, Boolean.TYPE),
        "double -> boolean");

    // test float conversions
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Character.TYPE),
        "boolean -> char");
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Byte.TYPE),
        "boolean -> byte");
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Short.TYPE),
        "boolean -> short");
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Integer.TYPE),
        "boolean -> int");
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Long.TYPE),
        "boolean -> long");
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Float.TYPE),
        "boolean -> float");
    assertEquals(false, ClassUtils.isAssignable(Boolean.TYPE, Double.TYPE),
        "boolean -> double");
    assertEquals(true, ClassUtils.isAssignable(Boolean.TYPE, Boolean.TYPE),
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

  @SuppressWarnings("unchecked")
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

  @SuppressWarnings("unchecked")
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

  private void assertGetClassThrowsNullPointerException(final String className)
      throws Exception {
    assertGetClassThrowsException(className, NullPointerException.class);
  }

  private void assertGetClassThrowsClassNotFound(final String className)
      throws Exception {
    assertGetClassThrowsException(className, ClassNotFoundException.class);
  }

  // Show the Java bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
  // We may have to delete this if a JDK fixes the bug.
  @SuppressWarnings("unchecked")
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

  @SuppressWarnings("unchecked")
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

  @SuppressWarnings("unchecked")
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
}
