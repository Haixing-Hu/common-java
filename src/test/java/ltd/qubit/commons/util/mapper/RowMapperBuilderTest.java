////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethodWithType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link RowMapperBuilder}.
 *
 * @author Haixing Hu
 */
public class RowMapperBuilderTest {

  private RowMapperBuilder<TestEntity> builder;

  @BeforeEach
  public void setUp() {
    builder = new RowMapperBuilder<>(TestEntity.class);
  }

  @Test
  public void testAdd_getter() throws NoSuchFieldException {
    final GetterMethod<TestEntity, ?> getter1 = TestEntity::getField1;
    final GetterMethod<TestEntity, ?> getter2 = TestEntity::getField2;
    builder.add("column1", getter1);
    builder.add("column2", getter2);
    builder.add("column3", TestEntity::getChild, TestEntity::getField1, true);
    builder.add("column4", TestEntity::getChild, TestEntity::getField2, false);
    builder.add("column5", TestEntity::getChild, TestEntity::getChild, TestEntity::getField1, false);
    builder.add("column6", TestEntity::getChild, TestEntity::getChild, TestEntity::getField2, true);

    final List<String> headers = builder.getHeaders();
    assertEquals(6, headers.size());
    assertEquals(List.of("column1", "column2", "column3", "column4", "column5", "column6"), headers);

    final Map<String, String> propertyMap = builder.getPropertyMap();
    assertEquals(6, propertyMap.size());
    assertEquals("field1", propertyMap.get("column1"));
    assertEquals("field2", propertyMap.get("column2"));
    assertEquals("child.field1", propertyMap.get("column3"));
    assertEquals("child.field2", propertyMap.get("column4"));
    assertEquals("child.child.field1", propertyMap.get("column5"));
    assertEquals("child.child.field2", propertyMap.get("column6"));

    final Map<String, GetterMethod<TestEntity, ?>> getterMap = builder.getGetterMap();
    assertEquals(2, getterMap.size());
    assertSame(getter1, getterMap.get("column1"));
    assertSame(getter2, getterMap.get("column2"));

    assertFalse(builder.isContinueLastRow("column1"));
    assertFalse(builder.isContinueLastRow("column2"));
    assertTrue(builder.isContinueLastRow("column3"));
    assertFalse(builder.isContinueLastRow("column4"));
    assertFalse(builder.isContinueLastRow("column5"));
    assertTrue(builder.isContinueLastRow("column6"));
  }

  @Test
  public void testAdd_non_property_getter() throws NoSuchFieldException {
    final GetterMethod<TestEntity, ?> getter1 = (e) -> e.getField(1);
    final GetterMethod<TestEntity, ?> getter2 = (e) -> e.getField(2);
    builder.add("column1", getter1);
    builder.add("column2", getter2, true);

    final List<String> headers = builder.getHeaders();
    assertEquals(2, headers.size());
    assertEquals(List.of("column1", "column2"), headers);

    final Map<String, String> propertyMap = builder.getPropertyMap();
    assertEquals(0, propertyMap.size());
    final Map<String, GetterMethod<TestEntity, ?>> getterMap = builder.getGetterMap();
    assertEquals(2, getterMap.size());
    assertSame(getter1, getterMap.get("column1"));
    assertSame(getter2, getterMap.get("column2"));

    assertFalse(builder.isContinueLastRow("column1"));
    assertTrue(builder.isContinueLastRow("column2"));
  }

  @Test
  public void testAdd_setter() throws NoSuchFieldException {
    final SetterMethodWithType<TestEntity, String> setterWithType1 =
        new SetterMethodWithType<>(String.class, TestEntity::setField1);
    final SetterMethodWithType<TestEntity, String> setterWithType2 =
        new SetterMethodWithType<>(String.class, TestEntity::setField2);
    builder.add("column1", String.class, setterWithType1.getSetter());
    builder.add("column2", String.class, setterWithType2.getSetter(), true);

    final List<String> headers = builder.getHeaders();
    assertEquals(2, headers.size());
    assertEquals(List.of("column1", "column2"), headers);

    final Map<String, String> propertyMap = builder.getPropertyMap();
    assertEquals(2, propertyMap.size());
    assertEquals("field1", propertyMap.get("column1"));
    assertEquals("field2", propertyMap.get("column2"));

    final Map<String, SetterMethodWithType<TestEntity, ?>> setterMap = builder.getSetterMap();
    assertEquals(2, setterMap.size());
    assertEquals(setterWithType1, setterMap.get("column1"));
    assertEquals(setterWithType2, setterMap.get("column2"));

    assertFalse(builder.isContinueLastRow("column1"));
    assertTrue(builder.isContinueLastRow("column2"));
  }

  @Test
  public void testAdd_non_property_setter() throws NoSuchFieldException {
    final SetterMethodWithType<TestEntity, String> setterWithType1 =
        new SetterMethodWithType<>(String.class, (e, v) -> e.setField(1, v));
    final SetterMethodWithType<TestEntity, String> setterWithType2 =
        new SetterMethodWithType<>(String.class, (e, v) -> e.setField(2, v));
    builder.add("column1", String.class, setterWithType1.getSetter());
    builder.add("column2", String.class, setterWithType2.getSetter(), true);

    final List<String> headers = builder.getHeaders();
    assertEquals(2, headers.size());
    assertEquals(List.of("column1", "column2"), headers);

    final Map<String, String> propertyMap = builder.getPropertyMap();
    assertEquals(0, propertyMap.size());

    final Map<String, SetterMethodWithType<TestEntity, ?>> setterMap = builder.getSetterMap();
    assertEquals(2, setterMap.size());
    assertEquals(setterWithType1, setterMap.get("column1"));
    assertEquals(setterWithType2, setterMap.get("column2"));

    assertFalse(builder.isContinueLastRow("column1"));
    assertTrue(builder.isContinueLastRow("column2"));
  }

  @Test
  public void testContinueLastRow() {
    builder.setContinueLastRow("field1", true);
    assertTrue(builder.isContinueLastRow("field1"));

    builder.setContinueLastRow("field1", false);
    assertFalse(builder.isContinueLastRow("field1"));
  }

  @Test
  public void testBuild() {
    final GetterMethod<TestEntity, ?> getter1 = TestEntity::getField1;
    final GetterMethod<TestEntity, ?> getter2 = TestEntity::getField2;
    final GetterMethod<TestEntity, ?> getter7 = (e) -> e.getField(1);
    final GetterMethod<TestEntity, ?> getter8 = (e) -> e.getField(2);
    final SetterMethodWithType<TestEntity, String> setterWithType9 =
        new SetterMethodWithType<>(String.class, (e, v) -> e.setField(1, v));
    final SetterMethodWithType<TestEntity, String> setterWithType10 =
        new SetterMethodWithType<>(String.class, (e, v) -> e.setField(2, v));

    builder.add("column1", getter1)
      .add("column2", getter2)
      .add("column3", TestEntity::getChild, TestEntity::getField1, true)
      .add("column4", TestEntity::getChild, TestEntity::getField2, false)
      .add("column5", TestEntity::getChild, TestEntity::getChild, TestEntity::getField1, false)
      .add("column6", TestEntity::getChild, TestEntity::getChild, TestEntity::getField2, true)
      .add("column7", getter7)
      .add("column8", getter8)
      .add("column9", String.class, setterWithType9.getSetter())
      .add("column10", String.class, setterWithType10.getSetter());
    final BasicRowMapper<TestEntity> mapper = builder.build();
    assertNotNull(mapper);

    final List<String> headers = mapper.getHeaders();
    assertEquals(10, headers.size());
    assertEquals(List.of("column1", "column2", "column3",
        "column4", "column5", "column6", "column7", "column8",
        "column9", "column10"), headers);

    final Map<String, String> propertyMap = mapper.getPropertyMap();
    assertEquals(6, propertyMap.size());
    assertEquals("field1", propertyMap.get("column1"));
    assertEquals("field2", propertyMap.get("column2"));
    assertEquals("child.field1", propertyMap.get("column3"));
    assertEquals("child.field2", propertyMap.get("column4"));
    assertEquals("child.child.field1", propertyMap.get("column5"));
    assertEquals("child.child.field2", propertyMap.get("column6"));

    final Map<String, GetterMethod<TestEntity, ?>> getterMap = builder.getGetterMap();
    assertEquals(4, getterMap.size());
    assertSame(getter1, getterMap.get("column1"));
    assertSame(getter2, getterMap.get("column2"));
    assertSame(getter7, getterMap.get("column7"));
    assertSame(getter8, getterMap.get("column8"));

    final Map<String, SetterMethodWithType<TestEntity, ?>> setterMap = builder.getSetterMap();
    assertEquals(2, setterMap.size());
    assertEquals(setterWithType9, setterMap.get("column9"));
    assertEquals(setterWithType10, setterMap.get("column10"));

    assertFalse(mapper.isContinueLastRow("column1"));
    assertFalse(mapper.isContinueLastRow("column2"));
    assertTrue(mapper.isContinueLastRow("column3"));
    assertFalse(mapper.isContinueLastRow("column4"));
    assertFalse(mapper.isContinueLastRow("column5"));
    assertTrue(mapper.isContinueLastRow("column6"));
  }
}
