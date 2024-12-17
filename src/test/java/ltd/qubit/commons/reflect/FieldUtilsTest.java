////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.reflect.testbed.AmbiguousMember;
import ltd.qubit.commons.reflect.testbed.App;
import ltd.qubit.commons.reflect.testbed.AppInfo;
import ltd.qubit.commons.reflect.testbed.ChildBean;
import ltd.qubit.commons.reflect.testbed.Country;
import ltd.qubit.commons.reflect.testbed.CustomList;
import ltd.qubit.commons.reflect.testbed.Foo;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.reflect.testbed.Interface;
import ltd.qubit.commons.reflect.testbed.MethodRefBean;
import ltd.qubit.commons.reflect.testbed.MyRecord;
import ltd.qubit.commons.reflect.testbed.PrivatelyShadowedChild;
import ltd.qubit.commons.reflect.testbed.PublicBase;
import ltd.qubit.commons.reflect.testbed.PublicChild;
import ltd.qubit.commons.reflect.testbed.PubliclyShadowedChild;
import ltd.qubit.commons.reflect.testbed.StaticContainer;
import ltd.qubit.commons.reflect.testbed.StringMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.reflect.FieldUtils.IGNORED_FIELD_PREFIXES;
import static ltd.qubit.commons.reflect.FieldUtils.getAllFields;
import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.getField;
import static ltd.qubit.commons.reflect.FieldUtils.getFieldName;
import static ltd.qubit.commons.reflect.FieldUtils.getReadMethod;
import static ltd.qubit.commons.reflect.FieldUtils.getWriteMethod;
import static ltd.qubit.commons.reflect.FieldUtils.hasField;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;
import static ltd.qubit.commons.reflect.FieldUtils.readField;
import static ltd.qubit.commons.reflect.MethodUtils.getMethodByName;
import static ltd.qubit.commons.reflect.Option.ALL;
import static ltd.qubit.commons.reflect.Option.ALL_ACCESS;
import static ltd.qubit.commons.reflect.Option.ALL_EXCLUDE_BRIDGE;
import static ltd.qubit.commons.reflect.Option.ANCESTOR;
import static ltd.qubit.commons.reflect.Option.ANCESTOR_PUBLIC;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;
import static ltd.qubit.commons.reflect.Option.BEAN_METHOD;
import static ltd.qubit.commons.reflect.Option.DEFAULT;
import static ltd.qubit.commons.reflect.Option.NON_STATIC;
import static ltd.qubit.commons.reflect.Option.PACKAGE;
import static ltd.qubit.commons.reflect.Option.PRIVATE;
import static ltd.qubit.commons.reflect.Option.PROTECTED;
import static ltd.qubit.commons.reflect.Option.PUBLIC;
import static ltd.qubit.commons.reflect.Option.STATIC;

/**
 * Unit tests FieldUtils.
 */
public class FieldUtilsTest {

  static final String S = "s";
  static final String SS = "ss";
  static final Integer I0 = 0;
  static final Integer I1 = 1;
  static final Double D0 = 0.0;
  static final Double D1 = 1.0;

  private PublicChild publicChild;
  private PubliclyShadowedChild publiclyShadowedChild;
  private PrivatelyShadowedChild privatelyShadowedChild;
  private final Class<?> parentClass = PublicChild.class.getSuperclass();

  @BeforeEach
  public void setUp() {
    StaticContainer.reset();
    publicChild = new PublicChild();
    publiclyShadowedChild = new PubliclyShadowedChild();
    privatelyShadowedChild = new PrivatelyShadowedChild();
    publicChild.foo();
    publiclyShadowedChild.foo();
    privatelyShadowedChild.foo();
    System.out.println("parentClass = " + parentClass);
  }

  private <T> void fillSet(final Set<T> set, final List<T> list) {
    set.clear();
    set.addAll(list);
  }

  @Test
  public void testGetAllFields() throws Exception {
    final HashSet<Field> expected = new HashSet<>();
    final HashSet<Field> actual = new HashSet<>();

    fillSet(expected,
        Arrays.asList(Interface.class.getDeclaredField("VALUE1")));
    fillSet(actual, getAllFields(Interface.class, ALL));
    assertEquals(expected, actual);

    fillSet(expected, Collections.emptyList());
    fillSet(actual, getAllFields(Interface.class, STATIC | PRIVATE));
    assertEquals(expected, actual);

    fillSet(expected, Collections.emptyList());
    fillSet(actual, getAllFields(Interface.class, STATIC | PACKAGE));
    assertEquals(expected, actual);

    fillSet(expected, Collections.emptyList());
    fillSet(actual, getAllFields(Interface.class, STATIC | PROTECTED));
    assertEquals(expected, actual);

    fillSet(expected,
        Arrays.asList(Interface.class.getDeclaredField("VALUE1")));
    fillSet(actual, getAllFields(Interface.class, STATIC | PUBLIC));
    assertEquals(expected, actual);

    fillSet(expected, Collections.emptyList());
    fillSet(actual, getAllFields(Interface.class, ALL_ACCESS));
    assertEquals(expected, actual);

    fillSet(expected, Arrays.asList(
        Interface.class.getDeclaredField("VALUE1"),
        PublicBase.class.getDeclaredField("VALUE2"),
        PublicBase.class.getDeclaredField("value3"),
        PublicBase.class.getDeclaredField("VALUE4"),
        PublicBase.class.getDeclaredField("field1"),
        PublicBase.class.getDeclaredField("field2"),
        PublicBase.class.getDeclaredField("field3"),
        PublicBase.class.getDeclaredField("field4"),
        PublicBase.class.getDeclaredField("field5")
    ));
    fillSet(actual, getAllFields(PublicBase.class, ALL));

    // remove the ignored field
    final Set<Field> toRemoved = new HashSet<>();
    for (final Field field : actual) {
      final String name = field.getName();
      for (final String prefix : IGNORED_FIELD_PREFIXES) {
        if (name.startsWith(prefix)) {
          toRemoved.add(field);
        }
      }
    }
    actual.removeAll(toRemoved);

    assertEquals(expected, actual);
  }

  @Test
  public void testGetField() {
    Field field = getField(PublicChild.class, ANCESTOR_PUBLIC, "VALUE");
    assertNotNull(field);
    assertEquals(Foo.class, field.getDeclaringClass());

    field = getField(PublicChild.class, ALL, "ss");
    assertNotNull(field);
    assertEquals(parentClass, field.getDeclaringClass());

    assertNull(getField(PublicChild.class, PUBLIC, "ss"));
    assertNull(getField(PublicChild.class, ANCESTOR | PRIVATE, "ss"));
    assertNull(getField(PublicChild.class, ANCESTOR_PUBLIC, "bb"));
    assertNull(getField(PublicChild.class, ANCESTOR_PUBLIC, "ii"));
    assertNull(getField(PublicChild.class, ANCESTOR_PUBLIC, "dd"));

    field = getField(PublicChild.class, ANCESTOR_PUBLIC, "VALUE");
    assertNotNull(field);
    assertEquals(Foo.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "ss");
    assertNotNull(field);
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "bb");
    assertNotNull(field);
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "ii");
    assertNotNull(field);
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "dd");
    assertNotNull(field);
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "VALUE");
    assertNotNull(field);
    assertEquals(Foo.class, field.getDeclaringClass());

    field = getField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "ss");
    assertNotNull(field);
    assertEquals(parentClass, field.getDeclaringClass());

    assertNull(getField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "bb"));
    assertNull(getField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "ii"));
    assertNull(getField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "dd"));

    assertNull(getField(PublicChild.class, STATIC | PUBLIC, "VALUE"));
    assertNull(getField(PublicChild.class, NON_STATIC | PUBLIC, "ss"));
    assertNull(getField(PublicChild.class, NON_STATIC | PUBLIC, "bb"));
    assertNull(getField(PublicChild.class, NON_STATIC | PUBLIC, "ii"));
    assertNull(getField(PublicChild.class, NON_STATIC | PUBLIC, "dd"));

    assertNull(getField(PubliclyShadowedChild.class, STATIC | PUBLIC, "VALUE"));

    field = getField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "ss");
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "bb");
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "ii");
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    field = getField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "dd");
    assertEquals(PubliclyShadowedChild.class, field.getDeclaringClass());

    assertNull(
        getField(PrivatelyShadowedChild.class, STATIC | ALL_ACCESS, "VALUE"));
    assertNull(
        getField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "ss"));
    assertNull(
        getField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "bb"));
    assertNull(
        getField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "ii"));
    assertNull(
        getField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "dd"));
  }

  @Test
  public void testGetFieldNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> getField(null, DEFAULT, "none"));
  }

  @Test
  public void testGetFieldNullPointerException_2() {
    assertThrows(NullPointerException.class,
        () -> getField(PublicChild.class, DEFAULT, null));
  }

  @Test
  public void testReadStaticField() {
    final int options = ANCESTOR | STATIC | PUBLIC;
    final Object value = readField(Foo.class, options, "VALUE", null);
    assertEquals(Foo.VALUE, value);
  }

  @Test
  public void testReadStaticFieldNullPointerException() {
    final int options = ANCESTOR | STATIC | PUBLIC;
    assertThrows(NullPointerException.class,
        () -> readField(null, options, "none", null));
  }

  @Test
  public void testReadStaticFieldNotExistException() {
    final int options = ANCESTOR | STATIC | PUBLIC;
    assertThrows(FieldNotExistException.class,
        () -> readField(PublicChild.class, options, "s", null));
  }

  @Test
  public void testReadNamedStaticField() {
    final int options = ANCESTOR | STATIC | PUBLIC;

    assertEquals(Foo.VALUE, readField(Foo.class, options, "VALUE", null));
    assertEquals(Foo.VALUE,
        readField(PubliclyShadowedChild.class, options, "VALUE", null));
    assertEquals(Foo.VALUE,
        readField(PrivatelyShadowedChild.class, options, "VALUE", null));
    assertEquals(Foo.VALUE,
        readField(PublicChild.class, options, "VALUE", null));
    assertThrows(NullPointerException.class,
        () -> readField(null, options, "none", null));
    assertThrows(NullPointerException.class,
        () -> readField(Foo.class, options, null, null));
    assertThrows(FieldNotExistException.class,
        () -> readField(Foo.class, options, "does_not_exist", null));
    assertThrows(FieldNotExistException.class,
        () -> readField(PublicChild.class, options, "s", null));
  }

  @Test
  public void testReadDeclaredNamedStaticField() {
    final int options = ANCESTOR | STATIC | PUBLIC;
    assertEquals(Foo.VALUE, readField(Foo.class, options, "VALUE", null));
    assertEquals("foo", readField(PublicChild.class, options, "VALUE", null));
    assertEquals("foo",
        readField(PubliclyShadowedChild.class, options, "VALUE", null));
    assertEquals("foo",
        readField(PrivatelyShadowedChild.class, options, "VALUE", null));
  }

  @Test
  public void testReadField() {
    assertEquals("ss", readField(parentClass, DEFAULT, "ss", publicChild));
    assertEquals("ss", readField(parentClass, DEFAULT, "ss", publiclyShadowedChild));
    assertEquals("ss", readField(parentClass, DEFAULT, "ss", privatelyShadowedChild));
    assertEquals(Boolean.FALSE, readField(parentClass, DEFAULT, "bb", publicChild));
    assertEquals(Boolean.FALSE, readField(parentClass, DEFAULT, "bb", publiclyShadowedChild));
    assertEquals(Boolean.FALSE, readField(parentClass, DEFAULT, "bb", privatelyShadowedChild));
    assertEquals(I0, readField(parentClass, DEFAULT, "ii", publicChild));
    assertEquals(I0, readField(parentClass, DEFAULT, "ii", publiclyShadowedChild));
    assertEquals(I0, readField(parentClass, DEFAULT, "ii", privatelyShadowedChild));
    assertEquals(D0, readField(parentClass, DEFAULT, "dd", publicChild));
    assertEquals(D0, readField(parentClass, DEFAULT, "dd", publiclyShadowedChild));
    assertEquals(D0, readField(parentClass, DEFAULT, "dd", privatelyShadowedChild));
    assertThrows(NullPointerException.class,
        () -> readField(null, DEFAULT, "ss", publicChild));
  }

  @Test
  public void testReadNamedField() {
    assertEquals("ss", readField(publicChild.getClass(), ALL, "ss", publicChild));
    assertEquals("ss", readField(publiclyShadowedChild.getClass(), ALL, "ss",
        publiclyShadowedChild));
    assertEquals("ss", readField(privatelyShadowedChild.getClass(), ALL, "ss",
        privatelyShadowedChild));
    //
    //    try {
    //      assertEquals(Boolean.FALSE, readField(publicChild, "b"));
    //      fail("expected IllegalArgumentException");
    //    } catch (final IllegalArgumentException e) {
    //      // pass
    //    }
    //    assertEquals(Boolean.TRUE, readField(publiclyShadowedChild, "b"));
    //    try {
    //      assertEquals(Boolean.FALSE, readField(privatelyShadowedChild, "b"));
    //      fail("expected IllegalArgumentException");
    //    } catch (final IllegalArgumentException e) {
    //      // pass
    //    }
    //    try {
    //      assertEquals(I0, readField(publicChild, "i"));
    //      fail("expected IllegalArgumentException");
    //    } catch (final IllegalArgumentException e) {
    //      // pass
    //    }
    //    assertEquals(I1, readField(publiclyShadowedChild, "i"));
    //    try {
    //      assertEquals(I0, readField(privatelyShadowedChild, "i"));
    //      fail("expected IllegalArgumentException");
    //    } catch (final IllegalArgumentException e) {
    //      // pass
    //    }
    //    try {
    //      assertEquals(D0, readField(publicChild, "d"));
    //      fail("expected IllegalArgumentException");
    //    } catch (final IllegalArgumentException e) {
    //      // pass
    //    }
    //    assertEquals(D1, readField(publiclyShadowedChild, "d"));
    //    try {
    //      assertEquals(D0, readField(privatelyShadowedChild, "d"));
    //      fail("expected IllegalArgumentException");
    //    } catch (final IllegalArgumentException e) {
    //      // pass
    //    }
  }

  //  @Test
  //  public void testReadDeclaredNamedField() {
  //    try {
  //      FieldUtils.readDeclaredField(publicChild, null);
  //      fail("a null field name should cause an IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // expected
  //    }
  //
  //    try {
  //      FieldUtils.readDeclaredField((Object) null, "none");
  //      fail("a null target should cause an IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // expected
  //    }
  //
  //    try {
  //      assertEquals("s", FieldUtils.readDeclaredField(publicChild, "s"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    assertEquals("ss", FieldUtils.readDeclaredField(publiclyShadowedChild, "s"));
  //    try {
  //      assertEquals("s", FieldUtils.readDeclaredField(privatelyShadowedChild, "s"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(publicChild, "b"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    assertEquals(Boolean.TRUE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b"));
  //    try {
  //      assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(privatelyShadowedChild, "b"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      assertEquals(I0, FieldUtils.readDeclaredField(publicChild, "i"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    assertEquals(I1, FieldUtils.readDeclaredField(publiclyShadowedChild, "i"));
  //    try {
  //      assertEquals(I0, FieldUtils.readDeclaredField(privatelyShadowedChild, "i"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      assertEquals(D0, FieldUtils.readDeclaredField(publicChild, "d"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    assertEquals(D1, FieldUtils.readDeclaredField(publiclyShadowedChild, "d"));
  //    try {
  //      assertEquals(D0, FieldUtils.readDeclaredField(privatelyShadowedChild, "d"));
  //      fail("expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //  }

  //  @Test
  //  public void testWriteStaticField() {
  //    Field field = StaticContainer.class.getDeclaredField("mutablePublic");
  //    FieldUtils.writeStaticField(field, "new");
  //    assertEquals("new", StaticContainer.mutablePublic);
  //    field = StaticContainer.class.getDeclaredField("mutableProtected");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = StaticContainer.class.getDeclaredField("mutablePackage");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = StaticContainer.class.getDeclaredField("mutablePrivate");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = StaticContainer.class.getDeclaredField("IMMUTABLE_PUBLIC");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = StaticContainer.class.getDeclaredField("IMMUTABLE_PROTECTED");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = StaticContainer.class.getDeclaredField("IMMUTABLE_PACKAGE");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = StaticContainer.class.getDeclaredField("IMMUTABLE_PRIVATE");
  //    try {
  //      FieldUtils.writeStaticField(field, "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //  }
  //
  //  @Test
  //  public void testWriteNamedStaticField() {
  //    FieldUtils.writeStaticField(StaticContainerChild.class, "mutablePublic", "new");
  //    assertEquals("new", StaticContainer.mutablePublic);
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "mutableProtected", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "mutablePackage", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "mutablePrivate", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PUBLIC", "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PROTECTED", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PACKAGE", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeStaticField(StaticContainerChild.class, "IMMUTABLE_PRIVATE", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //  }
  //
  //  @Test
  //  public void testWriteDeclaredNamedStaticField() {
  //    FieldUtils.writeStaticField(StaticContainer.class, "mutablePublic", "new");
  //    assertEquals("new", StaticContainer.mutablePublic);
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "mutableProtected", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "mutablePackage", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "mutablePrivate", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PUBLIC", "new");
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PROTECTED", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PACKAGE", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredStaticField(StaticContainer.class, "IMMUTABLE_PRIVATE", "new");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //  }

  //  @Test
  //  public void testWriteField() {
  //    Field field = parentClass.getDeclaredField("s");
  //    FieldUtils.writeField(field, publicChild, "S");
  //    assertEquals("S", field.get(publicChild));
  //    field = parentClass.getDeclaredField("b");
  //    try {
  //      FieldUtils.writeField(field, publicChild, Boolean.TRUE);
  //      fail("Expected IllegalAccessException");
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = parentClass.getDeclaredField("i");
  //    try {
  //      FieldUtils.writeField(field, publicChild, Integer.valueOf(Integer.MAX_VALUE));
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //    field = parentClass.getDeclaredField("d");
  //    try {
  //      FieldUtils.writeField(field, publicChild, Double.valueOf(Double.MAX_VALUE));
  //    } catch (final IllegalAccessException e) {
  //      // pass
  //    }
  //  }
  //
  //  @Test
  //  public void testWriteNamedField() {
  //    FieldUtils.writeField(publicChild, "s", "S");
  //    assertEquals("S", readField(publicChild, "s"));
  //    try {
  //      FieldUtils.writeField(publicChild, "b", Boolean.TRUE);
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeField(publicChild, "i", Integer.valueOf(1));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeField(publicChild, "d", Double.valueOf(1.0));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //
  //    FieldUtils.writeField(publiclyShadowedChild, "s", "S");
  //    assertEquals("S", readField(publiclyShadowedChild, "s"));
  //    FieldUtils.writeField(publiclyShadowedChild, "b", Boolean.FALSE);
  //    assertEquals(Boolean.FALSE, readField(publiclyShadowedChild, "b"));
  //    FieldUtils.writeField(publiclyShadowedChild, "i", Integer.valueOf(0));
  //    assertEquals(Integer.valueOf(0), readField(publiclyShadowedChild, "i"));
  //    FieldUtils.writeField(publiclyShadowedChild, "d", Double.valueOf(0.0));
  //    assertEquals(Double.valueOf(0.0), readField(publiclyShadowedChild, "d"));
  //
  //    FieldUtils.writeField(privatelyShadowedChild, "s", "S");
  //    assertEquals("S", readField(privatelyShadowedChild, "s"));
  //    try {
  //      FieldUtils.writeField(privatelyShadowedChild, "b", Boolean.TRUE);
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeField(privatelyShadowedChild, "i", Integer.valueOf(1));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeField(privatelyShadowedChild, "d", Double.valueOf(1.0));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //  }
  //
  //  @Test
  //  public void testWriteDeclaredNamedField() {
  //    try {
  //      FieldUtils.writeDeclaredField(publicChild, "s", "S");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredField(publicChild, "b", Boolean.TRUE);
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredField(publicChild, "i", Integer.valueOf(1));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredField(publicChild, "d", Double.valueOf(1.0));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //
  //    FieldUtils.writeDeclaredField(publiclyShadowedChild, "s", "S");
  //    assertEquals("S", FieldUtils.readDeclaredField(publiclyShadowedChild, "s"));
  //    FieldUtils.writeDeclaredField(publiclyShadowedChild, "b", Boolean.FALSE);
  //    assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b"));
  //    FieldUtils.writeDeclaredField(publiclyShadowedChild, "i", Integer.valueOf(0));
  //    assertEquals(Integer.valueOf(0), FieldUtils.readDeclaredField(publiclyShadowedChild, "i"));
  //    FieldUtils.writeDeclaredField(publiclyShadowedChild, "d", Double.valueOf(0.0));
  //    assertEquals(Double.valueOf(0.0), FieldUtils.readDeclaredField(publiclyShadowedChild, "d"));
  //
  //    try {
  //      FieldUtils.writeDeclaredField(privatelyShadowedChild, "s", "S");
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredField(privatelyShadowedChild, "b", Boolean.TRUE);
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredField(privatelyShadowedChild, "i", Integer.valueOf(1));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //    try {
  //      FieldUtils.writeDeclaredField(privatelyShadowedChild, "d", Double.valueOf(1.0));
  //      fail("Expected IllegalArgumentException");
  //    } catch (final IllegalArgumentException e) {
  //      // pass
  //    }
  //  }

  @Test
  public void testAmbiguousMember() {
    assertThrows(AmbiguousMemberException.class,
        () -> getField(AmbiguousMember.class, ALL, "VALUE"));
  }

  public static class AnnotatedBean {

    @NotNull
    private String fieldAnnotation;
    private String methodAnnotation;
    private String noAnnotation;

    public String getFieldAnnotation() {
      return fieldAnnotation;
    }

    public void setFieldAnnotation(final String fieldAnnotation) {
      this.fieldAnnotation = fieldAnnotation;
    }

    @NotNull
    public String getMethodAnnotation() {
      return methodAnnotation;
    }

    public void setMethodAnnotation(final String methodAnnotation) {
      this.methodAnnotation = methodAnnotation;
    }

    public String getNoAnnotation() {
      return noAnnotation;
    }

    public void setNoAnnotation(final String noAnnotation) {
      this.noAnnotation = noAnnotation;
    }
  }

  @Test
  void testGetAnnotation() throws NoSuchFieldException {
    Field field = AnnotatedBean.class.getDeclaredField("fieldAnnotation");
    assertThat(getAnnotation(field, NotNull.class)).isInstanceOf(NotNull.class);

    field = AnnotatedBean.class.getDeclaredField("methodAnnotation");
    assertThat(getAnnotation(field, NotNull.class)).isInstanceOf(NotNull.class);

    field = AnnotatedBean.class.getDeclaredField("noAnnotation");
    assertThat(getAnnotation(field, NotNull.class)).isNull();
  }

  @Test
  void testIsAnnotationPresent() throws NoSuchFieldException {
    Field field = AnnotatedBean.class.getDeclaredField("fieldAnnotation");
    assertThat(isAnnotationPresent(field, NotNull.class)).isTrue();

    field = AnnotatedBean.class.getDeclaredField("methodAnnotation");
    assertThat(isAnnotationPresent(field, NotNull.class)).isTrue();

    field = AnnotatedBean.class.getDeclaredField("noAnnotation");
    assertThat(isAnnotationPresent(field, NotNull.class)).isFalse();
  }

  @Test
  public void testGetReadMethod_Field() {
    final Field codeField = getField(ChildBean.class, BEAN_FIELD, "code");
    assertNotNull(codeField);
    final Field nameField = getField(ChildBean.class, BEAN_FIELD, "name");
    assertNotNull(nameField);
    final Field descriptionField = getField(ChildBean.class, BEAN_FIELD,
        "description");
    assertNotNull(descriptionField);
    final Field parentIdField = getField(ChildBean.class, BEAN_FIELD,
        "parentId");
    assertNotNull(parentIdField);
    final Field deletedField = getField(ChildBean.class, BEAN_FIELD, "deleted");
    assertNotNull(deletedField);
    final Field idField = getField(ChildBean.class, BEAN_FIELD, "id");
    assertNotNull(idField);

    final Method getCodeMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getCode");
    assertNotNull(getCodeMethod);
    final Method getNameMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getName");
    assertNotNull(getNameMethod);
    final Method getDescriptionMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "getDescription");
    assertNotNull(getDescriptionMethod);
    final Method getParentIdMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "getParentId");
    assertNotNull(getParentIdMethod);
    final Method isDeletedMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "isDeleted");
    assertNotNull(isDeletedMethod);
    final Method getIdMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getId");
    assertNotNull(getIdMethod);

    assertEquals(getCodeMethod, getReadMethod(codeField));
    assertEquals(getNameMethod, getReadMethod(nameField));
    assertEquals(getDescriptionMethod, getReadMethod(descriptionField));
    assertEquals(getParentIdMethod, getReadMethod(parentIdField));
    assertEquals(isDeletedMethod, getReadMethod(deletedField));
    assertEquals(getIdMethod, getReadMethod(idField));
  }

  @Test
  public void testGetReadMethod_String() {
    final Method getCodeMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getCode");
    assertNotNull(getCodeMethod);
    final Method getNameMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getName");
    assertNotNull(getNameMethod);
    final Method getDescriptionMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "getDescription");
    assertNotNull(getDescriptionMethod);
    final Method getParentIdMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "getParentId");
    assertNotNull(getParentIdMethod);
    final Method isDeletedMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "isDeleted");
    assertNotNull(isDeletedMethod);
    final Method getIdMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getId");
    assertNotNull(getIdMethod);
    final Method getInfoMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "getInfo");
    assertNotNull(getInfoMethod);

    assertEquals(getCodeMethod, getReadMethod(ChildBean.class, "code"));
    assertEquals(getNameMethod, getReadMethod(ChildBean.class, "name"));
    assertEquals(getDescriptionMethod,
        getReadMethod(ChildBean.class, "description"));
    assertEquals(getParentIdMethod, getReadMethod(ChildBean.class, "parentId"));
    assertEquals(isDeletedMethod, getReadMethod(ChildBean.class, "deleted"));
    assertEquals(getIdMethod, getReadMethod(ChildBean.class, "id"));
    assertEquals(getInfoMethod, getReadMethod(ChildBean.class, "info"));
  }

  @Test
  public void testGetMethodByName() {
    final Method setIdMethod = getMethodByName(ChildBean.class,
        ALL_EXCLUDE_BRIDGE, "setId");
    assertNotNull(setIdMethod);
    assertThrows(AmbiguousMemberException.class,
        () -> getMethodByName(ChildBean.class, ALL, "setId"),
        "should get ambiguous getId() methods due to the bridge method");
  }

  @Test
  public void testGetWriteMethod_Field() {
    final Method setCodeMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "setCode");
    assertNotNull(setCodeMethod);
    final Method setNameMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "setName");
    assertNotNull(setNameMethod);
    final Method setDescriptionMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "setDescription");
    assertNotNull(setDescriptionMethod);
    final Method setParentIdMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "setParentId");
    assertNotNull(setParentIdMethod);
    final Method setDeletedMethod = getMethodByName(ChildBean.class,
        BEAN_METHOD, "setDeleted");
    assertNotNull(setDeletedMethod);
    final Method setIdMethod = getMethodByName(ChildBean.class, BEAN_METHOD,
        "setId");
    assertNotNull(setIdMethod);

    assertEquals(setCodeMethod,
        getWriteMethod(ChildBean.class, "code", String.class));
    assertEquals(setNameMethod,
        getWriteMethod(ChildBean.class, "name", String.class));
    assertEquals(setDescriptionMethod,
        getWriteMethod(ChildBean.class, "description", String.class));
    assertEquals(setParentIdMethod,
        getWriteMethod(ChildBean.class, "parentId", Long.class));
    assertEquals(setDeletedMethod,
        getWriteMethod(ChildBean.class, "deleted", boolean.class));
    assertEquals(setIdMethod,
        getWriteMethod(ChildBean.class, "id", Long.class));
  }

  @Test
  public void testGetAllFields_Country() throws Exception {
    final List<Field> fields = getAllFields(Country.class, BEAN_FIELD);
    assertArrayEquals(new Field[]{
        Country.class.getDeclaredField("id"),
        Country.class.getDeclaredField("code"),
        Country.class.getDeclaredField("name"),
        Country.class.getDeclaredField("phoneArea"),
        Country.class.getDeclaredField("postalcode"),
        Country.class.getDeclaredField("icon"),
        Country.class.getDeclaredField("url"),
        Country.class.getDeclaredField("description"),
        Country.class.getDeclaredField("createTime"),
        Country.class.getDeclaredField("modifyTime"),
        Country.class.getDeclaredField("deleteTime"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testGetAllFields_App_Bean_Field() throws Exception {
    final List<Field> fields = getAllFields(App.class, BEAN_FIELD);
    assertArrayEquals(new Field[]{
        App.class.getDeclaredField("id"),
        App.class.getDeclaredField("code"),
        App.class.getDeclaredField("name"),
        App.class.getDeclaredField("category"),
        App.class.getDeclaredField("state"),
        App.class.getDeclaredField("icon"),
        App.class.getDeclaredField("url"),
        App.class.getDeclaredField("description"),
        App.class.getDeclaredField("comment"),
        App.class.getDeclaredField("securityKey"),
        App.class.getDeclaredField("token"),
        App.class.getDeclaredField("tokenCreateTime"),
        App.class.getDeclaredField("tokenExpiredTime"),
        App.class.getDeclaredField("predefined"),
        App.class.getDeclaredField("createTime"),
        App.class.getDeclaredField("modifyTime"),
        App.class.getDeclaredField("deleteTime"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testGetAllFields_App_All() throws Exception {
    final List<Field> fields = getAllFields(App.class, ALL);
    assertArrayEquals(new Field[]{
        App.class.getDeclaredField("serialVersionUID"),
        App.class.getDeclaredField("SYSTEM_APP_CODE"),
        App.class.getDeclaredField("id"),
        App.class.getDeclaredField("code"),
        App.class.getDeclaredField("name"),
        App.class.getDeclaredField("category"),
        App.class.getDeclaredField("state"),
        App.class.getDeclaredField("icon"),
        App.class.getDeclaredField("url"),
        App.class.getDeclaredField("description"),
        App.class.getDeclaredField("comment"),
        App.class.getDeclaredField("securityKey"),
        App.class.getDeclaredField("token"),
        App.class.getDeclaredField("tokenCreateTime"),
        App.class.getDeclaredField("tokenExpiredTime"),
        App.class.getDeclaredField("predefined"),
        App.class.getDeclaredField("createTime"),
        App.class.getDeclaredField("modifyTime"),
        App.class.getDeclaredField("deleteTime"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testGetAllFields_Info_ALL() throws Exception {
    final List<Field> fields = getAllFields(Info.class, ALL);
    assertArrayEquals(new Field[]{
        Info.class.getDeclaredField("serialVersionUID"),
        Info.class.getDeclaredField("id"),
        Info.class.getDeclaredField("code"),
        Info.class.getDeclaredField("name"),
        Info.class.getDeclaredField("deleteTime"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testGetAllFields_AppInfo_ALL() throws Exception {
    final List<Field> fields = getAllFields(AppInfo.class, ALL);
    System.out.println("Fields are: " + ArrayUtils.toString(fields));
    assertArrayEquals(new Field[]{
        AppInfo.class.getDeclaredField("serialVersionUID"),
        AppInfo.class.getDeclaredField("description"),
        Info.class.getDeclaredField("serialVersionUID"),
        Info.class.getDeclaredField("id"),
        Info.class.getDeclaredField("code"),
        Info.class.getDeclaredField("name"),
        Info.class.getDeclaredField("deleteTime"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testGetAllFields_StringMap_ALL() throws Exception {
    final List<Field> fields = getAllFields(StringMap.class, ALL);
    assertArrayEquals(new Field[]{
        StringMap.class.getDeclaredField("serialVersionUID"),
        HashMap.class.getDeclaredField("serialVersionUID"),
        HashMap.class.getDeclaredField("DEFAULT_INITIAL_CAPACITY"),
        HashMap.class.getDeclaredField("MAXIMUM_CAPACITY"),
        HashMap.class.getDeclaredField("DEFAULT_LOAD_FACTOR"),
        HashMap.class.getDeclaredField("TREEIFY_THRESHOLD"),
        HashMap.class.getDeclaredField("UNTREEIFY_THRESHOLD"),
        HashMap.class.getDeclaredField("MIN_TREEIFY_CAPACITY"),
        HashMap.class.getDeclaredField("table"),
        HashMap.class.getDeclaredField("entrySet"),
        HashMap.class.getDeclaredField("size"),
        HashMap.class.getDeclaredField("modCount"),
        HashMap.class.getDeclaredField("threshold"),
        HashMap.class.getDeclaredField("loadFactor"),
        AbstractMap.class.getDeclaredField("keySet"),
        AbstractMap.class.getDeclaredField("values"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testGetAllFields_CustomList_ALL() throws Exception {
    final List<Field> fields = getAllFields(CustomList.class, ALL);
    assertArrayEquals(new Field[]{
        CustomList.class.getDeclaredField("name"),
    }, fields.toArray(new Field[0]));
  }

  @Test
  public void testHasField_withoutOptions() throws Exception {
    assertTrue(hasField(PublicChild.class, "VALUE"));
    assertTrue(hasField(PublicChild.class, "ss"));
    assertTrue(hasField(PublicChild.class, "bb"));
    assertTrue(hasField(PublicChild.class, "ii"));
    assertTrue(hasField(PublicChild.class, "dd"));
    assertFalse(hasField(PublicChild.class, "xx"));
  }

  @Test
  public void testHasField_withOptions() throws Exception {
    assertTrue(hasField(PublicChild.class, ANCESTOR_PUBLIC, "VALUE"));

    assertFalse(hasField(PublicChild.class, PUBLIC, "ss"));
    assertTrue(hasField(PublicChild.class, ANCESTOR_PUBLIC, "ss"));
    assertFalse(hasField(PublicChild.class, ANCESTOR | PRIVATE, "ss"));

    assertFalse(hasField(PublicChild.class, ANCESTOR_PUBLIC, "bb"));
    assertFalse(hasField(PublicChild.class, ANCESTOR_PUBLIC, "ii"));
    assertFalse(hasField(PublicChild.class, ANCESTOR_PUBLIC, "dd"));
    assertFalse(hasField(PublicChild.class, ANCESTOR_PUBLIC, "xx"));

    assertTrue(hasField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "ss"));
    assertTrue(hasField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "bb"));
    assertTrue(hasField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "ii"));
    assertTrue(hasField(PubliclyShadowedChild.class, ANCESTOR_PUBLIC, "dd"));

    assertTrue(hasField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "VALUE"));
    assertTrue(hasField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "ss"));
    assertFalse(hasField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "bb"));
    assertFalse(hasField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "ii"));
    assertFalse(hasField(PrivatelyShadowedChild.class, ANCESTOR_PUBLIC, "dd"));

    assertFalse(hasField(PublicChild.class, STATIC | PUBLIC, "VALUE"));
    assertFalse(hasField(PublicChild.class, NON_STATIC, "VALUE"));
    assertFalse(hasField(PublicChild.class, NON_STATIC | PUBLIC, "ss"));
    assertFalse(hasField(PublicChild.class, NON_STATIC | PUBLIC, "bb"));
    assertFalse(hasField(PublicChild.class, NON_STATIC | PUBLIC, "ii"));
    assertFalse(hasField(PublicChild.class, NON_STATIC | PUBLIC, "dd"));

    assertFalse(hasField(PubliclyShadowedChild.class, STATIC | PUBLIC, "VALUE"));
    assertTrue(hasField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "ss"));
    assertTrue(hasField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "bb"));
    assertTrue(hasField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "ii"));
    assertTrue(hasField(PubliclyShadowedChild.class, NON_STATIC | PUBLIC, "dd"));

    assertFalse(hasField(PrivatelyShadowedChild.class, STATIC | ALL_ACCESS, "VALUE"));
    assertFalse(hasField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "ss"));
    assertFalse(hasField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "bb"));
    assertFalse(hasField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "ii"));
    assertFalse(hasField(PrivatelyShadowedChild.class, NON_STATIC | PUBLIC, "dd"));
  }

  @Test
  public void testGetFieldNameByGetter() {
    assertThat(getFieldName(App.class, App::getId))
        .isEqualTo("id");
    assertThat(getFieldName(App.class, App::getCode))
        .isEqualTo("code");
    assertThat(getFieldName(App.class, App::getCreateTime))
        .isEqualTo("createTime");
    assertThat(getFieldName(App.class, App::isPredefined))
        .isEqualTo("predefined");
    assertThat(getFieldName(App.class, App::toString))
        .isNull();
  }

  @Test
  public void testGetFieldByGetter() {
    assertThat(getField(App.class, App::getId))
        .isEqualTo(getField(App.class, "id"));
    assertThat(getField(App.class, App::getCode))
        .isEqualTo(getField(App.class, "code"));
    assertThat(getField(App.class, App::getCreateTime))
        .isEqualTo(getField(App.class, "createTime"));
    assertThat(getField(App.class, App::isPredefined))
        .isEqualTo(getField(App.class, "predefined"));
    assertThat(getField(App.class, App::toString))
        .isNull();
  }

  @Test
  public void testGetFieldNameBySetter() {
    assertThat(getFieldName(App.class, App::setId))
        .isEqualTo("id");
    assertThat(getFieldName(App.class, App::setCode))
        .isEqualTo("code");
    assertThat(getFieldName(App.class, App::setCreateTime))
        .isEqualTo("createTime");
   assertThat(getFieldName(App.class, App::setPredefined))
       .isEqualTo("predefined");
    assertThat(getFieldName(App.class, App::toString))
        .isNull();
  }

  @Test
  public void testGetFieldBySetter() {
    assertThat(getField(App.class, App::setId))
        .isEqualTo(getField(App.class, "id"));
    assertThat(getField(App.class, App::setCode))
        .isEqualTo(getField(App.class, "code"));
    assertThat(getField(App.class, App::setCreateTime))
        .isEqualTo(getField(App.class, "createTime"));
   assertThat(getField(App.class, App::setPredefined))
       .isEqualTo(getField(App.class, "predefined"));
    assertThat(getField(App.class, App::toString))
        .isNull();
  }

  @Test
  public void testGetFieldNameBySetter_2() {
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveBoolean))
        .isEqualTo("primitiveBoolean");

    // FIXME: Current primitive char is not supported
    // assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveChar))
    //     .isEqualTo("primitiveChar");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveByte))
        .isEqualTo("primitiveByte");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveShort))
        .isEqualTo("primitiveShort");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveInt))
        .isEqualTo("primitiveInt");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveLong))
        .isEqualTo("primitiveLong");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveFloat))
        .isEqualTo("primitiveFloat");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setPrimitiveDouble))
        .isEqualTo("primitiveDouble");


    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setBoolean))
        .isEqualTo("boolean");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setByte))
        .isEqualTo("byte");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setCharacter))
        .isEqualTo("character");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setShort))
        .isEqualTo("short");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setInteger))
        .isEqualTo("integer");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setLong))
        .isEqualTo("long");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setFloat))
        .isEqualTo("float");
    assertThat(getFieldName(MethodRefBean.class, MethodRefBean::setDouble))
        .isEqualTo("double");
  }

  @Test
  public void testGetFieldBySetter_2() {
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveBoolean))
        .isEqualTo(getField(MethodRefBean.class, "primitiveBoolean"));

    // FIXME: Current primitive char is not supported
    // assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveChar))
    //     .isEqualTo("primitiveChar");
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveByte))
        .isEqualTo(getField(MethodRefBean.class, "primitiveByte"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveShort))
        .isEqualTo(getField(MethodRefBean.class, "primitiveShort"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveInt))
        .isEqualTo(getField(MethodRefBean.class, "primitiveInt"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveLong))
        .isEqualTo(getField(MethodRefBean.class, "primitiveLong"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveFloat))
        .isEqualTo(getField(MethodRefBean.class, "primitiveFloat"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setPrimitiveDouble))
        .isEqualTo(getField(MethodRefBean.class, "primitiveDouble"));


    assertThat(getField(MethodRefBean.class, MethodRefBean::setBoolean))
        .isEqualTo(getField(MethodRefBean.class, "boolean"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setByte))
        .isEqualTo(getField(MethodRefBean.class, "byte"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setCharacter))
        .isEqualTo(getField(MethodRefBean.class, "character"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setShort))
        .isEqualTo(getField(MethodRefBean.class, "short"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setInteger))
        .isEqualTo(getField(MethodRefBean.class, "integer"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setLong))
        .isEqualTo(getField(MethodRefBean.class, "long"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setFloat))
        .isEqualTo(getField(MethodRefBean.class, "float"));
    assertThat(getField(MethodRefBean.class, MethodRefBean::setDouble))
        .isEqualTo(getField(MethodRefBean.class, "double"));
  }

  @Test
  public void testGetFieldByGetterForRecord() {
    final Field f = getField(MyRecord.class, MyRecord::name);
    assertEquals("name", f.getName());
  }
}
