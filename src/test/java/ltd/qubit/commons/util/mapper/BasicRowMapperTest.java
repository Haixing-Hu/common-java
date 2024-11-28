////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethodWithType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link BasicRowMapper}.
 *
 * @author Haixing Hu
 */
public class BasicRowMapperTest {

  private BasicRowMapper<TestEntity> mapper;
  private RowMapperBuilder<TestEntity> builder;
  private Class<TestEntity> cls;
  private List<String> headers;
  private Map<String, GetterMethod<TestEntity, ?>> getterMap;
  private Map<String, SetterMethodWithType<TestEntity, ?>> setterMap;
  private Map<String, String> propertyMap;
  private Set<String> continueLastRowHeaders;

  @BeforeEach
  public void setUp() throws NoSuchFieldException {
    builder = new RowMapperBuilder<>(TestEntity.class);
    builder.headers = new ArrayList<>(Arrays.asList("column1", "column2", "column3",
        "column4", "column5", "column6"));
    builder.getterMap = new HashMap<>();
    builder.setterMap = new HashMap<>();
    builder.propertyMap = new HashMap<>();
    builder.propertyMap.put("column1", "field1");
    builder.propertyMap.put("column2", "field2");
    builder.propertyMap.put("column3", "child.field1");
    builder.propertyMap.put("column4", "child.field2");
    builder.propertyMap.put("column5", "child.child.field1");
    builder.propertyMap.put("column6", "child.child.field2");
    builder.continueLastRowHeaders = Set.of("column1", "column2");
    builder.firstRowAsHeaders = true;
    mapper = new BasicRowMapper<>(builder);
  }

  @Test
  public void testToRow() {
    final TestEntity entity = new TestEntity();
    entity.setField1("value1");
    entity.setField2("value2");
    final TestEntity child = new TestEntity();
    child.setField1("childValue1");
    child.setField2("childValue2");
    entity.setChild(child);
    final Map<String, String> row = mapper.toRow(entity);
    assertEquals("value1", row.get("column1"));
    assertEquals("value2", row.get("column2"));
    assertEquals("childValue1", row.get("column3"));
    assertEquals("childValue2", row.get("column4"));
    assertEquals("", row.get("column5"));
    assertEquals("", row.get("column6"));
  }

  @Test
  public void testFromRow() {
    final Map<String, String> lastRow = new HashMap<>();
    lastRow.put("column1", "lastValue1");
    lastRow.put("column2", "lastValue2");

    final Map<String, String> currentRow = new HashMap<>();
    currentRow.put("column1", "");
    currentRow.put("column2", "currentValue2");

    final TestEntity obj1 = mapper.fromRow(lastRow, currentRow);
    assertEquals("lastValue1", obj1.getField1());
    assertEquals("currentValue2", obj1.getField2());
    assertNull(obj1.getChild());

    currentRow.put("column3", "childValue3");
    currentRow.put("column5", "childValue5");
    final TestEntity obj2 = mapper.fromRow(lastRow, currentRow);
    assertEquals("lastValue1", obj2.getField1());
    assertEquals("currentValue2", obj2.getField2());
    assertNotNull(obj2.getChild());
    assertEquals("childValue3", obj2.getChild().getField1());
    assertNull(obj2.getChild().getField2());
    assertNotNull(obj2.getChild().getChild());
    assertEquals("childValue5", obj2.getChild().getChild().getField1());
    assertNull(obj2.getChild().getChild().getField2());
  }

  @Test
  public void testFromRowWhenContinueLastRowIsFalse() {
    mapper.setContinueLastRow("column1", false);
    final Map<String, String> lastRow = new HashMap<>();
    lastRow.put("column1", "lastValue1");
    lastRow.put("column2", "lastValue2");
    final Map<String, String> currentRow = new HashMap<>();
    currentRow.put("column1", "");
    currentRow.put("column2", "currentValue2");
    final TestEntity entity = mapper.fromRow(lastRow, currentRow);
    assertEquals("", entity.getField1());
    assertEquals("currentValue2", entity.getField2());
    assertNull(entity.getChild());
  }

  @Test
  public void testToRowWhenHeadersIsEmpty() {
    builder.headers = new ArrayList<>();
    mapper = new BasicRowMapper<>(builder);
    final TestEntity entity = new TestEntity();
    entity.setField1("value1");
    entity.setField2("value2");
    final Map<String, String> row = mapper.toRow(entity);
    assertTrue(row.isEmpty());
  }

  @Test
  public void testToRowWhenPropertyMapIsEmpty() {
    builder.propertyMap = new HashMap<>();
    mapper = new BasicRowMapper<>(builder);
    final TestEntity entity = new TestEntity();
    entity.setField1("value1");
    entity.setField2("value2");
    final Map<String, String> row = mapper.toRow(entity);
    assertTrue(row.isEmpty());
  }

  @Test
  public void testFromRowWhenHeadersIsEmpty() {
    builder.headers = new ArrayList<>();
    mapper = new BasicRowMapper<>(builder);
    final Map<String, String> lastRow = new HashMap<>();
    lastRow.put("column1", "lastValue1");
    lastRow.put("column2", "lastValue2");

    final Map<String, String> currentRow = new HashMap<>();
    currentRow.put("column1", "");
    currentRow.put("column2", "currentValue2");

    final TestEntity entity = mapper.fromRow(lastRow, currentRow);

    assertNull(entity.getField1());
    assertNull(entity.getField2());
  }

  @Test
  public void testFromRowWhenFieldMapIsEmpty() {
    builder.propertyMap = new HashMap<>();
    mapper = new BasicRowMapper<>(builder);

    final Map<String, String> lastRow = new HashMap<>();
    lastRow.put("column1", "lastValue1");
    lastRow.put("column2", "lastValue2");

    final Map<String, String> currentRow = new HashMap<>();
    currentRow.put("column1", "");
    currentRow.put("column2", "currentValue2");

    final TestEntity entity = mapper.fromRow(lastRow, currentRow);

    assertNull(entity.getField1());
    assertNull(entity.getField2());
  }
}
