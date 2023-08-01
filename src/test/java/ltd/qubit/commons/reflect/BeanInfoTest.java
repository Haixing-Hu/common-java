////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import ltd.qubit.commons.reflect.testbed.ChildBean;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.reflect.testbed.ParentBean;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static ltd.qubit.commons.reflect.FieldUtils.getField;
import static ltd.qubit.commons.reflect.FieldUtils.getReadMethod;
import static ltd.qubit.commons.reflect.FieldUtils.getWriteMethod;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Unit test of the {@link BeanInfo}.
 */
public class BeanInfoTest {

  @Test
  public void testConstructor_ParentBean() {
    final BeanInfo info = BeanInfo.of(ParentBean.class);
    assertSame(ParentBean.class, info.getType());
    assertNotNull(info.getProperties());
    assertEquals(3, info.getProperties().size());

    final Field idField = getField(ParentBean.class, BEAN_FIELD, "id");
    assertNotNull(idField);
    final Method getIdMethod = getReadMethod(idField);
    assertNotNull(getIdMethod);
    final Method setIdMethod = getWriteMethod(idField);
    assertNotNull(setIdMethod);
    final Property p1 = info.getProperty("id");
    assertSame(ParentBean.class, p1.getOwnerClass());
    assertEquals("id", p1.getName());
    assertEquals(idField, p1.getField());
    assertEquals(getIdMethod, p1.getReadMethod());
    assertEquals(setIdMethod, p1.getWriteMethod());
    assertSame(Long.class, p1.getType());
    assertEquals(false, p1.isReadonly());
    assertEquals(true, p1.isIdentifier());
    assertEquals(false, p1.isNullable());
    assertEquals(true, p1.isUnique());
    assertNull(p1.getUniqueRespectTo());
    assertNull(p1.getSizeRange());

    final Method getClassMethod = getReadMethod(ParentBean.class, "class");
    assertNotNull(getClassMethod);
    final Property p2 = info.getProperty("class");
    assertSame(ParentBean.class, p2.getOwnerClass());
    assertEquals("class", p2.getName());
    assertNull(p2.getField());
    assertEquals(getClassMethod, p2.getReadMethod());
    assertNull(p2.getWriteMethod());
    assertSame(Class.class, p2.getType());
    assertEquals(true, p2.isReadonly());
    assertEquals(false, p2.isIdentifier());
    assertEquals(false, p2.isNullable());
    assertEquals(false, p2.isUnique());
    assertNull(p2.getUniqueRespectTo());
    assertNull(p2.getSizeRange());

    final Field numberField = getField(ParentBean.class, BEAN_FIELD, "number");
    assertNotNull(numberField);
    final Method getNumberMethod = getReadMethod(numberField);
    assertNotNull(getNumberMethod);
    final Method setNumberMethod = getWriteMethod(numberField);
    assertNotNull(setNumberMethod);
    final Property p3 = info.getProperty("number");
    assertSame(ParentBean.class, p3.getOwnerClass());
    assertEquals("number", p3.getName());
    assertEquals(numberField, p3.getField());
    assertEquals(getNumberMethod, p3.getReadMethod());
    assertEquals(setNumberMethod, p3.getWriteMethod());
    assertSame(Number.class, p3.getType());
    assertEquals(false, p3.isReadonly());
    assertEquals(false, p3.isIdentifier());
    assertEquals(false, p3.isNullable());
    assertEquals(false, p3.isUnique());
    assertNull(p3.getUniqueRespectTo());
    assertNull(p3.getSizeRange());
  }

  @Test
  public void testConstructor_ChildBean() {
    final BeanInfo info = BeanInfo.of(ChildBean.class);
    assertSame(ChildBean.class, info.getType());
    assertNotNull(info.getProperties());
    assertEquals(9, info.getProperties().size());

    final Field idField = getField(ChildBean.class, BEAN_FIELD, "id");
    assertNotNull(idField);
    final Method getIdMethod = getReadMethod(idField);
    assertNotNull(getIdMethod);
    final Method setIdMethod = getWriteMethod(idField);
    assertNotNull(setIdMethod);
    final Property p1 = info.getProperty("id");
    assertSame(ChildBean.class, p1.getOwnerClass());
    assertEquals("id", p1.getName());
    assertEquals(idField, p1.getField());
    assertEquals(getIdMethod, p1.getReadMethod());
    assertEquals(setIdMethod, p1.getWriteMethod());
    assertSame(Long.class, p1.getType());
    assertEquals(false, p1.isReadonly());
    assertEquals(true, p1.isIdentifier());
    assertEquals(false, p1.isNullable());
    assertEquals(true, p1.isUnique());
    assertNull(p1.getUniqueRespectTo());
    assertNull(p1.getSizeRange());

    final Field codeField = getField(ChildBean.class, BEAN_FIELD, "code");
    assertNotNull(codeField);
    final Method getCodeMethod = getReadMethod(codeField);
    assertNotNull(getCodeMethod);
    final Method setCodeMethod = getWriteMethod(codeField);
    assertNotNull(setCodeMethod);
    final Property p2 = info.getProperty("code");
    assertSame(ChildBean.class, p2.getOwnerClass());
    assertEquals("code", p2.getName());
    assertEquals(codeField, p2.getField());
    assertEquals(getCodeMethod, p2.getReadMethod());
    assertEquals(setCodeMethod, p2.getWriteMethod());
    assertSame(String.class, p2.getType());
    assertEquals(false, p2.isReadonly());
    assertEquals(false, p2.isIdentifier());
    assertEquals(false, p2.isNullable());
    assertEquals(true, p2.isUnique());
    assertNull(p2.getUniqueRespectTo());
    assertEquals(new CloseRange<>(1, 64), p2.getSizeRange());

    final Field nameField = getField(ChildBean.class, BEAN_FIELD, "name");
    assertNotNull(nameField);
    final Method getNameMethod = getReadMethod(nameField);
    assertNotNull(getNameMethod);
    final Method setNameMethod = getWriteMethod(nameField);
    assertNotNull(setNameMethod);
    final Property p3 = info.getProperty("name");
    assertSame(ChildBean.class, p3.getOwnerClass());
    assertEquals("name", p3.getName());
    assertEquals(nameField, p3.getField());
    assertEquals(getNameMethod, p3.getReadMethod());
    assertEquals(setNameMethod, p3.getWriteMethod());
    assertSame(String.class, p3.getType());
    assertEquals(false, p3.isReadonly());
    assertEquals(false, p3.isIdentifier());
    assertEquals(false, p3.isNullable());
    assertEquals(true, p3.isUnique());
    assertArrayEquals(new String[]{ "parentId" }, p3.getUniqueRespectTo());
    assertEquals(new CloseRange<>(1, 128), p3.getSizeRange());

    final Field descriptionField = getField(ChildBean.class, BEAN_FIELD, "description");
    assertNotNull(descriptionField);
    final Method getDescriptionMethod = getReadMethod(descriptionField);
    assertNotNull(getDescriptionMethod);
    final Method setDescriptionMethod = getWriteMethod(descriptionField);
    assertNotNull(setDescriptionMethod);
    final Property p4 = info.getProperty("description");
    assertSame(ChildBean.class, p4.getOwnerClass());
    assertEquals("description", p4.getName());
    assertEquals(descriptionField, p4.getField());
    assertEquals(getDescriptionMethod, p4.getReadMethod());
    assertEquals(setDescriptionMethod, p4.getWriteMethod());
    assertSame(String.class, p4.getType());
    assertEquals(false, p4.isReadonly());
    assertEquals(false, p4.isIdentifier());
    assertEquals(true, p4.isNullable());
    assertEquals(false, p4.isUnique());
    assertNull(p4.getUniqueRespectTo());
    assertEquals(new CloseRange<>(0, 1024), p4.getSizeRange());

    final Field parentIdField = getField(ChildBean.class, BEAN_FIELD, "parentId");
    assertNotNull(parentIdField);
    final Method getParentIdMethod = getReadMethod(parentIdField);
    assertNotNull(getParentIdMethod);
    final Method setParentIdMethod = getWriteMethod(parentIdField);
    assertNotNull(setParentIdMethod);
    final Property p5 = info.getProperty("parentId");
    assertSame(ChildBean.class, p5.getOwnerClass());
    assertEquals("parentId", p5.getName());
    assertEquals(parentIdField, p5.getField());
    assertEquals(getParentIdMethod, p5.getReadMethod());
    assertEquals(setParentIdMethod, p5.getWriteMethod());
    assertSame(Long.class, p5.getType());
    assertEquals(false, p5.isReadonly());
    assertEquals(false, p5.isIdentifier());
    assertEquals(true, p5.isNullable());
    assertEquals(false, p5.isUnique());
    assertNull(p5.getUniqueRespectTo());
    assertNull(p5.getSizeRange());

    final Field deletedField = getField(ChildBean.class, BEAN_FIELD, "deleted");
    assertNotNull(deletedField);
    final Method isDeletedMethod = getReadMethod(deletedField);
    assertNotNull(isDeletedMethod);
    final Method setDeletedMethod = getWriteMethod(deletedField);
    assertNotNull(setDeletedMethod);
    final Property p6 = info.getProperty("deleted");
    assertSame(ChildBean.class, p6.getOwnerClass());
    assertEquals("deleted", p6.getName());
    assertEquals(deletedField, p6.getField());
    assertEquals(isDeletedMethod, p6.getReadMethod());
    assertEquals(setDeletedMethod, p6.getWriteMethod());
    assertSame(boolean.class, p6.getType());
    assertEquals(false, p6.isReadonly());
    assertEquals(false, p6.isIdentifier());
    assertEquals(false, p6.isNullable());
    assertEquals(false, p6.isUnique());
    assertNull(p6.getUniqueRespectTo());
    assertNull(p6.getSizeRange());

    final Method getInfoMethod = getReadMethod(ChildBean.class, "info");
    assertNotNull(getInfoMethod);
    final Property p7 = info.getProperty("info");
    assertSame(ChildBean.class, p7.getOwnerClass());
    assertEquals("info", p7.getName());
    assertNull(p7.getField());
    assertEquals(getInfoMethod, p7.getReadMethod());
    assertNull(p7.getWriteMethod());
    assertSame(Info.class, p7.getType());
    assertEquals(true, p7.isReadonly());
    assertEquals(false, p7.isIdentifier());
    assertEquals(false, p7.isNullable());
    assertEquals(false, p7.isUnique());
    assertNull(p7.getUniqueRespectTo());
    assertNull(p7.getSizeRange());

    final Method getClassMethod = getReadMethod(ChildBean.class, "class");
    assertNotNull(getClassMethod);
    final Property p8 = info.getProperty("class");
    assertSame(ChildBean.class, p8.getOwnerClass());
    assertEquals("class", p8.getName());
    assertNull(p8.getField());
    assertEquals(getClassMethod, p8.getReadMethod());
    assertNull(p8.getWriteMethod());
    assertSame(Class.class, p8.getType());
    assertEquals(true, p8.isReadonly());
    assertEquals(false, p8.isIdentifier());
    assertEquals(false, p8.isNullable());
    assertEquals(false, p8.isUnique());
    assertNull(p8.getUniqueRespectTo());
    assertNull(p8.getSizeRange());

    final Field numberField = getField(ChildBean.class, BEAN_FIELD, "number");
    assertNotNull(numberField);
    final Method getNumberMethod = getReadMethod(numberField);
    assertNotNull(getNumberMethod);
    final Method setNumberMethod = getWriteMethod(numberField);
    assertNotNull(setNumberMethod);
    final Property p9 = info.getProperty("number");
    assertSame(ChildBean.class, p9.getOwnerClass());
    assertEquals("number", p9.getName());
    assertEquals(numberField, p9.getField());
    assertEquals(getNumberMethod, p9.getReadMethod());
    assertEquals(setNumberMethod, p9.getWriteMethod());
    assertSame(Long.class, p9.getType());
    assertEquals(false, p9.isReadonly());
    assertEquals(false, p9.isIdentifier());
    assertEquals(false, p9.isNullable());
    assertEquals(false, p9.isUnique());
    assertNull(p9.getUniqueRespectTo());
    assertNull(p9.getSizeRange());
  }

  @Test
  public void testConstructor_Info() {
    final BeanInfo info = BeanInfo.of(Info.class);
    assertSame(Info.class, info.getType());
    final Collection<Property> properties = info.getProperties();
    assertNotNull(properties);
    assertEquals(new HashSet<>(Arrays.asList(
        Property.of(Info.class, "class"),
        Property.of(Info.class, "code"),
        Property.of(Info.class, "deleteTime"),
        Property.of(Info.class, "deleted"),
        Property.of(Info.class, "id"),
        Property.of(Info.class, "name"),
        Property.of(Info.class, "empty")
        )), new HashSet<>(properties));
  }

  @Test
  public void testCACHE() {
    final BeanInfo info1 = BeanInfo.of(Info.class);
    final BeanInfo info2 = BeanInfo.of(Info.class);
    assertSame(info1, info2);
  }
}
