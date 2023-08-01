////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.lang.reflect.Field;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import ltd.qubit.commons.lang.BigDecimalUtils;
import ltd.qubit.commons.lang.BigIntegerUtils;
import ltd.qubit.commons.lang.BooleanUtils;
import ltd.qubit.commons.lang.ByteUtils;
import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.lang.DoubleUtils;
import ltd.qubit.commons.lang.EnumUtils;
import ltd.qubit.commons.lang.FloatUtils;
import ltd.qubit.commons.lang.InstantUtils;
import ltd.qubit.commons.lang.IntUtils;
import ltd.qubit.commons.lang.LocalDateUtils;
import ltd.qubit.commons.lang.LocalTimeUtils;
import ltd.qubit.commons.lang.LongUtils;
import ltd.qubit.commons.lang.ShortUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.model.Foo;
import ltd.qubit.commons.reflect.testbed.State;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.lang.ArrayUtils.excludeAll;
import static ltd.qubit.commons.lang.DateUtils.UTC_ZONE_ID;
import static ltd.qubit.commons.reflect.FieldUtils.getAllFields;
import static ltd.qubit.commons.reflect.FieldUtils.writeField;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyValue;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.setPropertyValue;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;
import static ltd.qubit.commons.sql.ComparisonOperator.EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.GREATER;
import static ltd.qubit.commons.sql.ComparisonOperator.GREATER_EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.IN;
import static ltd.qubit.commons.sql.ComparisonOperator.LESS;
import static ltd.qubit.commons.sql.ComparisonOperator.LESS_EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.LIKE;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_IN;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_LIKE;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isSupportedDataType;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleCriterionTest {

  static final ComparisonOperator[] EQUALITY_OPS = { EQUAL, NOT_EQUAL};
  static final ComparisonOperator[] NON_EQUALITY_OPS =
      excludeAll(ComparisonOperator.values(), EQUALITY_OPS);

  static final ComparisonOperator[] PARTIAL_ORDER_OPS = { EQUAL, NOT_EQUAL,
      LESS, LESS_EQUAL, GREATER, GREATER_EQUAL};

  static final ComparisonOperator[] NON_PARTIAL_ORDER_OPS =
      excludeAll(ComparisonOperator.values(), PARTIAL_ORDER_OPS);

  static final ComparisonOperator[] COLLECTION_OPS = { IN, NOT_IN };

  static final ComparisonOperator[] NON_COLLECTION_OPS =
      excludeAll(ComparisonOperator.values(), COLLECTION_OPS);

  static final ComparisonOperator[] LIKE_OPS = { LIKE, NOT_LIKE };

  static final List<Field> FIELDS = getAllFields(Foo.class, BEAN_FIELD);

  @Test
  public void testConstructor() {
    final SimpleCriterion<Foo> c1 = new SimpleCriterion<>(Foo.class,
        "m_String", LESS, "value");
    assertEquals("m_String", c1.getProperty());
    assertEquals(LESS, c1.getOperator());
    assertEquals("value", c1.getValue());
    assertFalse(c1.isCompareProperties());

    final SimpleCriterion<Foo> c2 = new SimpleCriterion<>(Foo.class,
        "m_String", EQUAL, null);
    assertEquals("m_String", c2.getProperty());
    assertEquals(EQUAL, c2.getOperator());
    assertNull(c2.getValue());
    assertFalse(c2.isCompareProperties());

    final SimpleCriterion<Foo> c3 = new SimpleCriterion<>(Foo.class,
        "m_String", NOT_EQUAL, "m_child.m_String", true);
    assertEquals("m_String", c3.getProperty());
    assertEquals(NOT_EQUAL, c3.getOperator());
    assertEquals("m_child.m_String", c3.getValue());
    assertTrue(c3.isCompareProperties());

    final SimpleCriterion<Foo> c4 = new SimpleCriterion<>(Foo.class,
        "m_Integer", IN, new int[]{1, 2, 3});
    assertEquals("m_Integer", c4.getProperty());
    assertEquals(IN, c4.getOperator());
    assertNotNull(c4.getValue());
    assertTrue(c4.getValue().getClass().isArray());
    assertArrayEquals(new int[]{1, 2, 3}, (int[]) c4.getValue());
    assertFalse(c4.isCompareProperties());
  }

  @Test
  public void testToSql() throws SQLSyntaxErrorException {
    final SimpleCriterion<Foo> c1 = new SimpleCriterion<>(Foo.class,
        "m_String", EQUAL, null);
    assertEquals("`m_string` IS NULL", c1.toSql());

    final SimpleCriterion<Foo> c2 = new SimpleCriterion<>(Foo.class,
        "m_child.m_Integer", NOT_EQUAL, null);
    assertEquals("`m_child_m_integer` IS NOT NULL", c2.toSql());

    final SimpleCriterion<Foo> c3 = new SimpleCriterion<>(Foo.class,
        "m_child.m_int", LESS, null);
    assertThrows(SQLSyntaxErrorException.class, c3::toSql,
        "Cannot convert to SQL: ");

    final SimpleCriterion<Foo> c4 = new SimpleCriterion<>(Foo.class,
        "m_child.m_int", EQUAL, null, true);
    assertThrows(SQLSyntaxErrorException.class, c4::toSql,
        "Cannot convert to SQL: ");

    final SimpleCriterion<Foo> c5 = new SimpleCriterion<>(Foo.class,
        "m_child.m_int", EQUAL, 1);
    assertEquals("`m_child_m_int` = 1", c5.toSql());

    final SimpleCriterion<Foo> c6 = new SimpleCriterion<>(Foo.class,
        "m_child.m_child.m_int", LESS_EQUAL, 1);
    assertEquals("`m_child_m_child_m_int` <= 1", c6.toSql());

    final SimpleCriterion<Foo> c7 = new SimpleCriterion<>(Foo.class,
        "m_String", NOT_EQUAL, "a'bc");
    assertEquals("`m_string` != 'a\\'bc'", c7.toSql());

    final SimpleCriterion<Foo> c8 = new SimpleCriterion<>(Foo.class,
        "m_Long", NOT_EQUAL, "m_child.m_Integer", true);
    assertEquals("`m_long` != `m_child_m_integer`", c8.toSql());

    final SimpleCriterion<Foo> c9 = new SimpleCriterion<>(Foo.class,
        "m_LocalDate", EQUAL, LocalDate.of(2020, 3, 1));
    assertEquals("`m_local_date` = '2020-03-01'", c9.toSql());

    final SimpleCriterion<Foo> c10 = new SimpleCriterion<>(Foo.class,
        "m_Instant", GREATER,
        ZonedDateTime.of(2020, 3, 1, 11, 31, 23, 123000000, UTC_ZONE_ID));
    assertEquals("`m_instant` > '2020-03-01T11:31:23.123Z'", c10.toSql());

    final SimpleCriterion<Foo> c11 = new SimpleCriterion<>(Foo.class,
        "m_int", IN, new int[]{1, 2, 3});
    assertEquals("`m_int` IN (1, 2, 3)", c11.toSql());

    final SimpleCriterion<Foo> c12 = new SimpleCriterion<>(Foo.class,
        "m_String", NOT_IN, new String[]{"a", "b", "c"});
    assertEquals("`m_string` NOT IN ('a', 'b', 'c')", c12.toSql());

    final SimpleCriterion<Foo> c13 = new SimpleCriterion<>(Foo.class,
        "m_int", IN, new Integer[]{1, 2, 3});
    assertEquals("`m_int` IN (1, 2, 3)", c13.toSql());

    final SimpleCriterion<Foo> c14 = new SimpleCriterion<>(Foo.class,
        "m_int", IN, new int[]{1, 2, 3}, true);
    assertThrows(SQLSyntaxErrorException.class, c14::toSql,
        "Cannot convert to SQL: ");

    final SimpleCriterion<Foo> c15 = new SimpleCriterion<>(Foo.class,
        "m_String", IN, "abc");
    assertThrows(SQLSyntaxErrorException.class, c15::toSql,
        "Cannot convert to SQL: ");

    final SimpleCriterion<Foo> c16 = new SimpleCriterion<>(Foo.class,
        "m_child.m_String", LIKE, "%abc");
    assertEquals("`m_child_m_string` LIKE '%abc'", c16.toSql());

    final SimpleCriterion<Foo> c17 = new SimpleCriterion<>(Foo.class,
        "m_child.m_String", NOT_LIKE, 123);
    assertThrows(SQLSyntaxErrorException.class, c17::toSql,
        "Cannot convert to SQL: ");
  }

  private boolean isValid(final String property, final ComparisonOperator op,
      final Object value) {
    return new SimpleCriterion<>(Foo.class, property, op, value).isValid();
  }

  @Test
  public void testIsValid_OP_null() {
    for (final ComparisonOperator op : EQUALITY_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, null);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, null);
        if (type.isPrimitive()) {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
        } else if (isSupportedDataType(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_EQUALITY_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, null);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, null);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_boolean() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_boolean);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_boolean);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_boolean", true);
        if (BooleanUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_boolean);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_boolean);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_boolean", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_char() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_char);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_char);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_char", true);
        if (CharUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_char);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_char);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_char", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_char);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_char);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_char", true);
        if (CharUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
  }

  @Test
  public void testIsValid_OP_byte() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_byte);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_byte);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_byte", true);
        if (ByteUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_byte);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_byte);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_byte", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_short() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_short);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_short);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_short", true);
        if (ShortUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_short);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_short);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_short", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_int() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_int);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_int);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_int", true);
        if (IntUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_int);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_int);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_int", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_long() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_long);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_long);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_long", true);
        if (LongUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_long);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_long);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_long", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_float() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_float);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_float);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_float", true);
        if (FloatUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_float);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_float);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_float", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_double() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_double);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_double);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_double", true);
        if (DoubleUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_double);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_double);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_double", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Boolean() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Boolean);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Boolean);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Boolean", true);
        if (BooleanUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Boolean);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Boolean);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Boolean", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Character() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Character);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Character);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Character", true);
        if (CharUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Character);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Character);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Character", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Character);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Character);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Character", true);
        if (CharUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
  }

  @Test
  public void testIsValid_OP_Byte() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Byte);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Byte);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Byte", true);
        if (ByteUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Byte);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Byte);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Byte", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Short() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Short);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Short);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Short", true);
        if (ShortUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Short);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Short);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Short", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Integer() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Integer);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Integer);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Integer", true);
        if (IntUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Integer);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Integer);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Integer", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Long() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Long);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Long);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Long", true);
        if (LongUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Long);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Long);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Long", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Float() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Float);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Float);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Float", true);
        if (FloatUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Float);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Float);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Float", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Double() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Double);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Double);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Double", true);
        if (DoubleUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Double);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Double);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Double", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_BigInteger() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_BigInteger);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_BigInteger);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_BigInteger", true);
        if (BigIntegerUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_BigInteger);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_BigInteger);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_BigInteger", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_BigDecimal() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_BigDecimal);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_BigDecimal);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_BigDecimal", true);
        if (BigDecimalUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_BigDecimal);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_BigDecimal);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_BigDecimal", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_String() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_String);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_String);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_String", true);
        if (StringUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_String);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_String);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_String", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_String);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_String);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_String", true);
        if (StringUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
  }

  @Test
  public void testIsValid_OP_Instant() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Instant);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Instant);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Instant", true);
        if (InstantUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Instant);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Instant);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Instant", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_LocalDate() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LocalDate);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LocalDate);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LocalDate", true);
        if (LocalDateUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LocalDate);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LocalDate);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LocalDate", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_LocalTime() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LocalTime);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LocalTime);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LocalTime", true);
        if (LocalTimeUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LocalTime);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LocalTime);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LocalTime", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_LocalDateTime() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LocalDateTime);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LocalDateTime);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LocalDateTime", true);
        if (type == LocalDateTime.class) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LocalDateTime);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LocalDateTime);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LocalDateTime", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_ZonedDateTime() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_ZonedDateTime);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_ZonedDateTime);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_ZonedDateTime", true);
        if (InstantUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_ZonedDateTime);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_ZonedDateTime);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_ZonedDateTime", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_java_sql_Date() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_sql_Date);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_sql_Date);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_sql_Date", true);
        if (LocalDateUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_sql_Date);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_sql_Date);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_sql_Date", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_java_sql_Time() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_sql_Time);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_sql_Time);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_sql_Time", true);
        if (LocalTimeUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_sql_Time);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_sql_Time);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_sql_Time", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_java_sql_Timestamp() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_sql_Timestamp);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_sql_Timestamp);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_sql_Timestamp", true);
        if (InstantUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_sql_Timestamp);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_sql_Timestamp);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_sql_Timestamp", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_java_util_Date() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_util_Date);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_util_Date);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_util_Date", true);
        if (InstantUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_java_util_Date);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_java_util_Date);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_java_util_Date", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_Gender() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Gender);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Gender);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Gender", true);
        if (EnumUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Gender);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Gender);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Gender", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_Gender);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_Gender);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_Gender", true);
        if (EnumUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
  }

  @Test
  public void testIsValid_OP_LongArray() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LongArray);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LongArray);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LongArray", true);
        if (LongUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_LongArray);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_LongArray);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_LongArray", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_StringArray() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_StringArray);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_StringArray);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_StringArray", true);
        if (StringUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_StringArray);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_StringArray);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_StringArray", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testIsValid_OP_StateArray() {
    final Foo foo = new Foo();
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Class<?> type = field.getType();
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_StateArray);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_StateArray);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_StateArray", true);
        if (EnumUtils.isComparable(type)) {
          assertTrue(c1.isValid(), c1.toString());
          assertTrue(c2.isValid(), c2.toString());
          assertTrue(c3.isValid(), c3.toString());
        } else {
          assertFalse(c1.isValid(), c1.toString());
          assertFalse(c2.isValid(), c2.toString());
          assertFalse(c3.isValid(), c3.toString());
        }
      }
    }
    for (final ComparisonOperator op : NON_COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final String path = "m_child." + prop;
        final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, prop, op, foo.m_StateArray);
        final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, path, op, foo.m_StateArray);
        final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, prop, op, "m_StateArray", true);
        assertFalse(c1.isValid(), c1.toString());
        assertFalse(c2.isValid(), c2.toString());
        assertFalse(c3.isValid(), c3.toString());
      }
    }
  }

  @Test
  public void testAccept_OP_null() {
    final Foo foo = new Foo();
    foo.m_child = new Foo();
    for (final ComparisonOperator op : EQUALITY_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final Class<?> type = field.getType();
        final Criterion<Foo> c = new SimpleCriterion<>(Foo.class, prop, op, null);
        if (type.isPrimitive() || (! isSupportedDataType(type))) {
          assertFalse(c.accept(foo), c.toString());
        } else {
          final Object oldValue = getPropertyValue(foo, prop);
          if (op == EQUAL) {
            assertFalse(c.accept(foo), "criterion = " + c + ", obj = " + foo);
            writeField(Foo.class, BEAN_FIELD, prop, foo, null);
            assertTrue(c.accept(foo), "criterion = " + c + ", obj = " + foo);
          } else if (op == NOT_EQUAL){
            assertTrue(c.accept(foo), "criterion = " + c + ", obj = " + foo);
            writeField(Foo.class, BEAN_FIELD, prop, foo, null);
            assertFalse(c.accept(foo), "criterion = " + c + ", obj = " + foo);
          }
          setPropertyValue(foo, prop, oldValue);
        }
      }
    }
    for (final ComparisonOperator op : NON_EQUALITY_OPS) {
      for (final Field field : FIELDS) {
        final String prop = field.getName();
        final Criterion<Foo> c = new SimpleCriterion<>(Foo.class, prop, op, null);
        assertFalse(c.accept(foo), "criterion = " + c + ", obj = " + foo);
      }
    }
  }


  @Test
  public void testAccept() {
    final Foo foo = new Foo();
    foo.m_child = new Foo();

    foo.m_Byte = 12;
    final Criterion<Foo> c1 = new SimpleCriterion<>(Foo.class, "m_Byte", EQUAL, null);
    assertFalse(c1.accept(foo));
    foo.m_Byte = null;
    assertTrue(c1.accept(foo));

    foo.m_boolean = false;
    final Criterion<Foo> c2 = new SimpleCriterion<>(Foo.class, "m_boolean", EQUAL, false);
    assertTrue(c2.accept(foo));

    foo.m_Boolean = Boolean.TRUE;
    foo.m_int = 1;
    final Criterion<Foo> c3 = new SimpleCriterion<>(Foo.class, "m_Boolean", EQUAL, "m_int", true);
    assertTrue(c3.accept(foo));

    foo.m_char = 'x';
    foo.m_String = "x";
    final Criterion<Foo> c4 = new SimpleCriterion<>(Foo.class, "m_String", EQUAL, "m_char", true);
    assertTrue(c4.accept(foo));

    foo.m_StateArray = new State[]{State.NORMAL, State.INACTIVE, State.BLOCKED, State.DISABLED};
    foo.m_String = "BLOCKED";
    final Criterion<Foo> c5 = new SimpleCriterion<>(Foo.class, "m_String", IN, "m_StateArray", true);
    assertTrue(c5.accept(foo));

    foo.m_LongArray = new Long[]{10L, 100L, 200L, 300L};
    foo.m_int = 200;
    final Criterion<Foo> c6 = new SimpleCriterion<>(Foo.class, "m_int", IN, "m_LongArray", true);
    assertTrue(c6.accept(foo));
    final Criterion<Foo> c7 = new SimpleCriterion<>(Foo.class, "m_int", NOT_IN, "m_LongArray", true);
    assertFalse(c7.accept(foo));

    foo.m_child.m_int = 300;
    final Criterion<Foo> c8 = new SimpleCriterion<>(Foo.class, "m_child.m_int", IN, foo.m_LongArray);
    assertTrue(c8.accept(foo));

    foo.m_child.m_float = 300.00f;
    final Criterion<Foo> c9 = new SimpleCriterion<>(Foo.class, "m_child.m_float", IN, foo.m_LongArray);
    assertTrue(c9.accept(foo));

    foo.m_child.m_double = 300.00001;
    final Criterion<Foo> c10 = new SimpleCriterion<>(Foo.class, "m_child.m_double", IN, foo.m_LongArray);
    assertFalse(c10.accept(foo));

    final Criterion<Foo> c11 = new SimpleCriterion<>(Foo.class, "m_child.m_double", GREATER, 300L);
    assertTrue(c11.accept(foo));
  }
}
