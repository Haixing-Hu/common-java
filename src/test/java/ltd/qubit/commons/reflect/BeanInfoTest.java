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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.model.Foo;
import ltd.qubit.commons.reflect.testbed.ChildBean;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.reflect.testbed.ParentBean;
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
    assertFalse(p1.isReadonly());
    assertTrue(p1.isIdentifier());
    assertFalse(p1.isNullable());
    assertTrue(p1.isUnique());
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
    assertTrue(p2.isReadonly());
    assertFalse(p2.isIdentifier());
    assertFalse(p2.isNullable());
    assertFalse(p2.isUnique());
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
    assertFalse(p3.isReadonly());
    assertFalse(p3.isIdentifier());
    assertFalse(p3.isNullable());
    assertFalse(p3.isUnique());
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
    final Property p2 = info.getProperty("code");
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
    final Property p3 = info.getProperty("name");
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
    assertFalse(p4.isReadonly());
    assertFalse(p4.isIdentifier());
    assertTrue(p4.isNullable());
    assertFalse(p4.isUnique());
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
    final Property p6 = info.getProperty("deleted");
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
    final Property p7 = info.getProperty("info");
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
    assertTrue(p8.isReadonly());
    assertFalse(p8.isIdentifier());
    assertFalse(p8.isNullable());
    assertFalse(p8.isUnique());
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
    assertFalse(p9.isReadonly());
    assertFalse(p9.isIdentifier());
    assertFalse(p9.isNullable());
    assertFalse(p9.isUnique());
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
        Property.of(Info.class, "toParams"),
        Property.of(Info.class, "hashCode"),
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

  @Test
  public void testBeanInfoOfBeanWithPublicFields() {
    final BeanInfo info = BeanInfo.of(Foo.class);
    assertSame(Foo.class, info.getType());
    final List<Property> expectedProperties = Arrays.asList(
        Property.of(Foo.class, "class"),
        Property.of(Foo.class, "m_boolean"),
        Property.of(Foo.class, "m_char"),
        Property.of(Foo.class, "m_byte"),
        Property.of(Foo.class, "m_short"),
        Property.of(Foo.class, "m_int"),
        Property.of(Foo.class, "m_long"),
        Property.of(Foo.class, "m_float"),
        Property.of(Foo.class, "m_double"),
        Property.of(Foo.class, "m_Boolean"),
        Property.of(Foo.class, "m_Character"),
        Property.of(Foo.class, "m_Byte"),
        Property.of(Foo.class, "m_Short"),
        Property.of(Foo.class, "m_Integer"),
        Property.of(Foo.class, "m_Long"),
        Property.of(Foo.class, "m_Float"),
        Property.of(Foo.class, "m_Double"),
        Property.of(Foo.class, "m_BigInteger"),
        Property.of(Foo.class, "m_BigDecimal"),
        Property.of(Foo.class, "m_String"),
        Property.of(Foo.class, "m_Instant"),
        Property.of(Foo.class, "m_LocalDate"),
        Property.of(Foo.class, "m_LocalTime"),
        Property.of(Foo.class, "m_LocalDateTime"),
        Property.of(Foo.class, "m_ZonedDateTime"),
        Property.of(Foo.class, "m_java_sql_Date"),
        Property.of(Foo.class, "m_java_sql_Time"),
        Property.of(Foo.class, "m_java_sql_Timestamp"),
        Property.of(Foo.class, "m_java_util_Date"),
        Property.of(Foo.class, "m_Gender"),
        Property.of(Foo.class, "m_State"),
        Property.of(Foo.class, "m_LongArray"),
        Property.of(Foo.class, "m_StringArray"),
        Property.of(Foo.class, "m_InstantArray"),
        Property.of(Foo.class, "m_StateArray"),
        Property.of(Foo.class, "m_child")
    );
    final List<String> expectedPropertyNames = expectedProperties
        .stream().map(Property::getName).sorted().toList();
    final Collection<Property> properties = info.getProperties();
    assertNotNull(properties);
    final List<String> propertyNames = properties
        .stream().map(Property::getName).sorted().toList();
    assertEquals(expectedPropertyNames, propertyNames);
    assertEquals(new HashSet<>(expectedProperties), new HashSet<>(properties));
  }
}
