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

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.testbed.model.Foo;
import ltd.qubit.commons.reflect.testbed.ChildBean;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.util.range.CloseRange;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.reflect.FieldUtils.getField;
import static ltd.qubit.commons.reflect.FieldUtils.getReadMethod;
import static ltd.qubit.commons.reflect.FieldUtils.getWriteMethod;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;

/**
 * Unit test of the {@link Property} class.
 *
 * @author Haixing Hu
 */
public class PropertyTest {

  @Test
  public void testConstructor() {
    final Field idField = getField(ChildBean.class, BEAN_FIELD, "id");
    assertNotNull(idField);
    final Method getIdMethod = getReadMethod(idField);
    assertNotNull(getIdMethod);
    final Method setIdMethod = getWriteMethod(idField);
    assertNotNull(setIdMethod);
    final Property p1 = Property.of(ChildBean.class, "id");
    assertSame(ChildBean.class, p1.getOwnerClass());
    assertEquals("id", p1.getName());
    assertEquals(idField, p1.getField());
    assertEquals(getIdMethod, p1.getReadMethod());
    assertEquals(setIdMethod, p1.getWriteMethod());
    assertSame(Long.class, p1.getType());
    assertFalse(p1.isReadonly());
    assertTrue(p1.isIdentifier());
    assertFalse(p1.isNullable());
    assertTrue(p1.isUnique());
    assertNull(p1.getUniqueRespectTo());
    assertNull(p1.getSizeRange());

    final Field codeField = getField(ChildBean.class, BEAN_FIELD, "code");
    assertNotNull(codeField);
    final Method getCodeMethod = getReadMethod(codeField);
    assertNotNull(getCodeMethod);
    final Method setCodeMethod = getWriteMethod(codeField);
    assertNotNull(setCodeMethod);
    final Property p2 = Property.of(ChildBean.class, "code");
    assertSame(ChildBean.class, p2.getOwnerClass());
    assertEquals("code", p2.getName());
    assertEquals(codeField, p2.getField());
    assertEquals(getCodeMethod, p2.getReadMethod());
    assertEquals(setCodeMethod, p2.getWriteMethod());
    assertSame(String.class, p2.getType());
    assertFalse(p2.isReadonly());
    assertFalse(p2.isIdentifier());
    assertFalse(p2.isNullable());
    assertTrue(p2.isUnique());
    assertNull(p2.getUniqueRespectTo());
    assertEquals(new CloseRange<>(1, 64), p2.getSizeRange());

    final Field nameField = getField(ChildBean.class, BEAN_FIELD, "name");
    assertNotNull(nameField);
    final Method getNameMethod = getReadMethod(nameField);
    assertNotNull(getNameMethod);
    final Method setNameMethod = getWriteMethod(nameField);
    assertNotNull(setNameMethod);
    final Property p3 = Property.of(ChildBean.class, "name");
    assertSame(ChildBean.class, p3.getOwnerClass());
    assertEquals("name", p3.getName());
    assertEquals(nameField, p3.getField());
    assertEquals(getNameMethod, p3.getReadMethod());
    assertEquals(setNameMethod, p3.getWriteMethod());
    assertSame(String.class, p3.getType());
    assertFalse(p3.isReadonly());
    assertFalse(p3.isIdentifier());
    assertFalse(p3.isNullable());
    assertTrue(p3.isUnique());
    assertArrayEquals(new String[]{"parentId"}, p3.getUniqueRespectTo());
    assertEquals(new CloseRange<>(1, 128), p3.getSizeRange());

    final Field descriptionField = getField(ChildBean.class, BEAN_FIELD,
        "description");
    assertNotNull(descriptionField);
    final Method getDescriptionMethod = getReadMethod(descriptionField);
    assertNotNull(getDescriptionMethod);
    final Method setDescriptionMethod = getWriteMethod(descriptionField);
    assertNotNull(setDescriptionMethod);
    final Property p4 = Property.of(ChildBean.class, "description");
    assertSame(ChildBean.class, p4.getOwnerClass());
    assertEquals("description", p4.getName());
    assertEquals(descriptionField, p4.getField());
    assertEquals(getDescriptionMethod, p4.getReadMethod());
    assertEquals(setDescriptionMethod, p4.getWriteMethod());
    assertSame(String.class, p4.getType());
    assertFalse(p4.isReadonly());
    assertFalse(p4.isIdentifier());
    assertTrue(p4.isNullable());
    assertFalse(p4.isUnique());
    assertNull(p4.getUniqueRespectTo());
    assertEquals(new CloseRange<>(0, 1024), p4.getSizeRange());

    final Field parentIdField = getField(ChildBean.class, BEAN_FIELD,
        "parentId");
    assertNotNull(parentIdField);
    final Method getParentIdMethod = getReadMethod(parentIdField);
    assertNotNull(getParentIdMethod);
    final Method setParentIdMethod = getWriteMethod(parentIdField);
    assertNotNull(setParentIdMethod);
    final Property p5 = Property.of(ChildBean.class, "parentId");
    assertSame(ChildBean.class, p5.getOwnerClass());
    assertEquals("parentId", p5.getName());
    assertEquals(parentIdField, p5.getField());
    assertEquals(getParentIdMethod, p5.getReadMethod());
    assertEquals(setParentIdMethod, p5.getWriteMethod());
    assertSame(Long.class, p5.getType());
    assertFalse(p5.isReadonly());
    assertFalse(p5.isIdentifier());
    assertTrue(p5.isNullable());
    assertFalse(p5.isUnique());
    assertNull(p5.getUniqueRespectTo());
    assertNull(p5.getSizeRange());

    final Field deletedField = getField(ChildBean.class, BEAN_FIELD, "deleted");
    assertNotNull(deletedField);
    final Method isDeletedMethod = getReadMethod(deletedField);
    assertNotNull(isDeletedMethod);
    final Method setDeletedMethod = getWriteMethod(deletedField);
    assertNotNull(setDeletedMethod);
    final Property p6 = Property.of(ChildBean.class, "deleted");
    assertSame(ChildBean.class, p6.getOwnerClass());
    assertEquals("deleted", p6.getName());
    assertEquals(deletedField, p6.getField());
    assertEquals(isDeletedMethod, p6.getReadMethod());
    assertEquals(setDeletedMethod, p6.getWriteMethod());
    assertSame(boolean.class, p6.getType());
    assertFalse(p6.isReadonly());
    assertFalse(p6.isIdentifier());
    assertFalse(p6.isNullable());
    assertFalse(p6.isUnique());
    assertNull(p6.getUniqueRespectTo());
    assertNull(p6.getSizeRange());

    final Method getInfoMethod = getReadMethod(ChildBean.class, "info");
    assertNotNull(getInfoMethod);
    final Property p7 = Property.of(ChildBean.class, "info");
    assertSame(ChildBean.class, p7.getOwnerClass());
    assertEquals("info", p7.getName());
    assertNull(p7.getField());
    assertEquals(getInfoMethod, p7.getReadMethod());
    assertNull(p7.getWriteMethod());
    assertSame(Info.class, p7.getType());
    assertTrue(p7.isReadonly());
    assertFalse(p7.isIdentifier());
    assertFalse(p7.isNullable());
    assertFalse(p7.isUnique());
    assertNull(p6.getUniqueRespectTo());
    assertNull(p6.getSizeRange());
  }

  @Test
  public void testPropertyOfPublicField() throws NoSuchFieldException {
    final Foo foo = new Foo();
    final Property p1 = Property.of(Foo.class, "m_boolean");
    assertSame(Foo.class, p1.getOwnerClass());
    assertEquals("m_boolean", p1.getName());
    assertSame(boolean.class, p1.getType());
    assertTrue(p1.isPublicField());
    assertEquals(Foo.class.getField("m_boolean"), p1.getField());
    assertFalse(p1.isReadonly());
    assertFalse(p1.isIdentifier());
    assertFalse(p1.isNullable());
    assertFalse(p1.isUnique());
    assertNull(p1.getUniqueRespectTo());
    assertNull(p1.getSizeRange());
    p1.setValue(foo, true);
    assertEquals(Boolean.TRUE, p1.getValue(foo));

    final Property p2 = Property.of(Foo.class, "m_Integer");
    assertSame(Foo.class, p2.getOwnerClass());
    assertEquals("m_Integer", p2.getName());
    assertSame(Integer.class, p2.getType());
    assertTrue(p2.isPublicField());
    assertEquals(Foo.class.getField("m_Integer"), p2.getField());
    assertFalse(p2.isReadonly());
    assertFalse(p2.isIdentifier());
    assertFalse(p2.isNullable());
    assertFalse(p2.isUnique());
    assertNull(p2.getUniqueRespectTo());
    assertNull(p2.getSizeRange());
    p2.setValue(foo, 200);
    assertEquals(200, p2.getValue(foo));
  }

  @Test
  public void testPropertyOf() throws NoSuchMethodException {
    final Property prop = Property.of(String.class, "length");
    assertNotNull(prop);
    assertEquals(String.class.getDeclaredMethod("length"), prop.getReadMethod());
  }
}
