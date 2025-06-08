////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TimeZone;
import java.util.Vector;

import org.junit.jupiter.api.Test;

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
import ltd.qubit.commons.testbed.model.Foo;
import ltd.qubit.commons.testbed.model.Gender;
import ltd.qubit.commons.util.ComparisonOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.lang.ArrayUtils.excludeAll;
import static ltd.qubit.commons.lang.DateUtils.BEIJING;
import static ltd.qubit.commons.lang.DateUtils.getDateTime;
import static ltd.qubit.commons.lang.DateUtils.getTime;
import static ltd.qubit.commons.lang.DateUtils.getTimestamp;
import static ltd.qubit.commons.reflect.FieldUtils.getAllFields;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.boolToSql;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isComparable;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isSupportedDataType;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.stringToSql;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.valueToSql;
import static ltd.qubit.commons.util.ComparisonOperator.EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.GREATER;
import static ltd.qubit.commons.util.ComparisonOperator.GREATER_EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.IN;
import static ltd.qubit.commons.util.ComparisonOperator.LESS;
import static ltd.qubit.commons.util.ComparisonOperator.LESS_EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.LIKE;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_IN;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_LIKE;

public class CriterionImplUtilsTest {

  static final ComparisonOperator[] EQUALITY_OPS = {EQUAL, NOT_EQUAL};
  static final ComparisonOperator[] NON_EQUALITY_OPS =
      excludeAll(ComparisonOperator.values(), EQUALITY_OPS);

  static final ComparisonOperator[] PARTIAL_ORDER_OPS = {EQUAL, NOT_EQUAL,
      LESS, LESS_EQUAL, GREATER, GREATER_EQUAL};

  static final ComparisonOperator[] NON_PARTIAL_ORDER_OPS =
      excludeAll(ComparisonOperator.values(), PARTIAL_ORDER_OPS);

  static final ComparisonOperator[] COLLECTION_OPS = {IN, NOT_IN};

  static final ComparisonOperator[] NON_COLLECTION_OPS =
      excludeAll(ComparisonOperator.values(), COLLECTION_OPS);

  static final ComparisonOperator[] LIKE_OPS = {LIKE, NOT_LIKE};

  static final List<Field> FIELDS = getAllFields(Foo.class, BEAN_FIELD);

  @Test
  public void testStringToSql() {
    assertNull(stringToSql(null));
    assertEquals("''", stringToSql(""));
    assertEquals("'abc'", stringToSql("abc"));
    assertEquals("'a\\'bc'", stringToSql("a'bc"));
  }

  @Test
  public void testBooleanToSql() {
    assertNull(boolToSql(null));
    assertEquals("1", boolToSql(Boolean.TRUE));
    assertEquals("0", boolToSql(Boolean.FALSE));
  }

  @Test
  public void testValueToSql() {
    final TimeZone originalDefaultZone = TimeZone.getDefault();
    TimeZone.setDefault(BEIJING);

    assertEquals("'a\\'b\"c'", valueToSql("a'b\"c"));
    assertEquals("'a'", valueToSql('a'));
    assertEquals("-123", valueToSql((byte) -123));
    assertEquals("123", valueToSql((short) +123));
    assertEquals("12345", valueToSql(12345));
    assertEquals("1234567890", valueToSql(1234567890L));
    assertEquals("123.45", valueToSql(123.45f));
    assertEquals("123.45", valueToSql(123.45));
    assertEquals("12345678901234567890",
        valueToSql(new BigInteger("12345678901234567890")));
    assertEquals("12345678901234567890.0123456789",
        valueToSql(new BigDecimal("12345678901234567890.0123456789")));
    assertEquals("'2022-01-23T14:31:23Z'",
        valueToSql(Instant.parse("2022-01-23T14:31:23Z")));
    assertEquals("'2022-01-23'", valueToSql(LocalDate.parse("2022-01-23")));
    assertEquals("'14:23:34'", valueToSql(LocalTime.parse("14:23:34")));
    assertEquals("'2022-01-23 14:31:23'",
        valueToSql(LocalDateTime.parse("2022-01-23T14:31:23")));
    assertEquals("'2022-01-23T04:31:23Z'",
        valueToSql(ZonedDateTime.parse("2022-01-23T14:31:23+10:00")));
    assertEquals("'2022-01-23T06:31:23Z'",
        valueToSql(getDateTime(2022, 1, 23, 14, 31, 23, BEIJING)));
    assertEquals("'21:32:11'", valueToSql(getTime(21, 32, 11)));
    assertEquals("'2022-01-23T06:31:23Z'",
        valueToSql(getTimestamp(2022, 1, 23, 14, 31, 23, BEIJING)));
    assertEquals("(1, 2, 3, 4)", valueToSql(new Integer[]{1, 2, 3, 4}));
    assertEquals("(1, 2, 3, 4)", valueToSql(new int[]{1, 2, 3, 4}));
    assertEquals("('a', 'aa', 'aaa', 'aaaa')",
        valueToSql(new String[]{"a", "aa", "aaa", "aaaa"}));
    assertEquals("('2022-01-23', '2022-01-24', '2022-01-25')",
        valueToSql(Arrays.asList(LocalDate.parse("2022-01-23"),
            LocalDate.parse("2022-01-24"),
            LocalDate.parse("2022-01-25"))));

    TimeZone.setDefault(originalDefaultZone);
  }

  @Test
  public void testIsSupportedDataType() {
    assertTrue(isSupportedDataType(boolean.class));
    assertTrue(isSupportedDataType(char.class));
    assertTrue(isSupportedDataType(byte.class));
    assertTrue(isSupportedDataType(short.class));
    assertTrue(isSupportedDataType(int.class));
    assertTrue(isSupportedDataType(long.class));
    assertTrue(isSupportedDataType(float.class));
    assertTrue(isSupportedDataType(double.class));
    assertTrue(isSupportedDataType(Boolean.class));
    assertTrue(isSupportedDataType(Character.class));
    assertTrue(isSupportedDataType(Byte.class));
    assertTrue(isSupportedDataType(Short.class));
    assertTrue(isSupportedDataType(Integer.class));
    assertTrue(isSupportedDataType(Long.class));
    assertTrue(isSupportedDataType(Float.class));
    assertTrue(isSupportedDataType(Double.class));
    assertTrue(isSupportedDataType(BigInteger.class));
    assertTrue(isSupportedDataType(BigDecimal.class));
    assertTrue(isSupportedDataType(String.class));
    assertTrue(isSupportedDataType(Instant.class));
    assertTrue(isSupportedDataType(LocalDate.class));
    assertTrue(isSupportedDataType(LocalTime.class));
    assertTrue(isSupportedDataType(LocalDateTime.class));
    assertTrue(isSupportedDataType(ZonedDateTime.class));
    assertTrue(isSupportedDataType(java.sql.Date.class));
    assertTrue(isSupportedDataType(java.sql.Time.class));
    assertTrue(isSupportedDataType(java.sql.Timestamp.class));
    assertTrue(isSupportedDataType(java.util.Date.class));
    assertTrue(isSupportedDataType(Enum.class));
    assertTrue(isSupportedDataType(Gender.class));

    assertFalse(isSupportedDataType(Number.class));
    assertFalse(isSupportedDataType(Collection.class));
    assertFalse(isSupportedDataType(List.class));
    assertFalse(isSupportedDataType(Deque.class));
    assertFalse(isSupportedDataType(Queue.class));
    assertFalse(isSupportedDataType(Set.class));
    assertFalse(isSupportedDataType(SortedSet.class));
    assertFalse(isSupportedDataType(Vector.class));
    assertFalse(isSupportedDataType(Stack.class));
    assertFalse(isSupportedDataType(ArrayList.class));
    assertFalse(isSupportedDataType(LinkedList.class));

    assertTrue(isSupportedDataType(boolean[].class));
    assertTrue(isSupportedDataType(char[].class));
    assertTrue(isSupportedDataType(byte[].class));
    assertTrue(isSupportedDataType(short[].class));
    assertTrue(isSupportedDataType(int[].class));
    assertTrue(isSupportedDataType(long[].class));
    assertTrue(isSupportedDataType(float[].class));
    assertTrue(isSupportedDataType(double[].class));
    assertTrue(isSupportedDataType(Boolean[].class));
    assertTrue(isSupportedDataType(Character[].class));
    assertTrue(isSupportedDataType(Byte[].class));
    assertTrue(isSupportedDataType(Short[].class));
    assertTrue(isSupportedDataType(Integer[].class));
    assertTrue(isSupportedDataType(Long[].class));
    assertTrue(isSupportedDataType(Float[].class));
    assertTrue(isSupportedDataType(Double[].class));
    assertTrue(isSupportedDataType(BigInteger[].class));
    assertTrue(isSupportedDataType(BigDecimal[].class));
    assertTrue(isSupportedDataType(String[].class));
    assertTrue(isSupportedDataType(Instant[].class));
    assertTrue(isSupportedDataType(LocalDate[].class));
    assertTrue(isSupportedDataType(LocalTime[].class));
    assertTrue(isSupportedDataType(LocalDateTime[].class));
    assertTrue(isSupportedDataType(ZonedDateTime[].class));
    assertTrue(isSupportedDataType(java.sql.Date[].class));
    assertTrue(isSupportedDataType(java.sql.Time[].class));
    assertTrue(isSupportedDataType(java.sql.Timestamp[].class));
    assertTrue(isSupportedDataType(java.util.Date[].class));
    assertTrue(isSupportedDataType(Enum[].class));
    assertTrue(isSupportedDataType(Gender[].class));

    assertFalse(isSupportedDataType(Foo.class));
    assertFalse(isSupportedDataType(Foo[].class));
    assertFalse(isSupportedDataType(Collection[].class));
    assertFalse(isSupportedDataType(List[].class));
    assertFalse(isSupportedDataType(Deque[].class));
    assertFalse(isSupportedDataType(Queue[].class));
    assertFalse(isSupportedDataType(Set[].class));
    assertFalse(isSupportedDataType(SortedSet[].class));
    assertFalse(isSupportedDataType(Vector[].class));
    assertFalse(isSupportedDataType(Stack[].class));
    assertFalse(isSupportedDataType(ArrayList[].class));
    assertFalse(isSupportedDataType(LinkedList[].class));
  }

  @Test
  public void testIsComparable_OP_null() {
    for (final ComparisonOperator op : EQUALITY_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (type.isPrimitive()) {
          assertFalse(isComparable(type, op, null),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else if (isSupportedDataType(type)) {
          assertTrue(isComparable(type, op, null),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, null),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_EQUALITY_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, null),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_boolean() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (BooleanUtils.isComparable(type)) {
           assertTrue(isComparable(type, op, boolean.class),
               "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
           assertFalse(isComparable(type, op, boolean.class),
               "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, boolean.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_char() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (CharUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, char.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, char.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, char.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (CharUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, char.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, char.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
  }

  @Test
  public void testIsComparable_OP_byte() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (ByteUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, byte.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, byte.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, byte.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_short() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (ShortUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, short.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, short.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, short.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_int() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (IntUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, int.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, int.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, int.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_long() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (LongUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, long.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, long.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, long.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_float() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (FloatUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, float.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, float.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, float.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_double() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (DoubleUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, double.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, double.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, byte.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Boolean() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (BooleanUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Boolean.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Boolean.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Boolean.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Character() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (CharUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Character.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Character.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Character.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (CharUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Character.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Character.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
  }

  @Test
  public void testIsComparable_OP_Byte() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (ByteUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Byte.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Byte.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Byte.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Short() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (ShortUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Short.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Short.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Short.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Integer() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (IntUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Integer.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Integer.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Integer.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Long() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (LongUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Long.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Long.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Long.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Float() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (FloatUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Float.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Float.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Float.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Double() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (DoubleUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Double.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Double.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Double.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_BigInteger() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (BigIntegerUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, BigInteger.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, BigInteger.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, BigInteger.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_BigDecimal() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (BigDecimalUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, BigDecimal.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, BigDecimal.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, BigDecimal.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_String() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (StringUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, String.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, String.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, String.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (StringUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, String.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, String.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
  }

  @Test
  public void testIsComparable_OP_Instant() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (InstantUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Instant.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Instant.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Instant.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_LocalDate() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (LocalDateUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, LocalDate.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, LocalDate.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, LocalDate.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_LocalTime() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (LocalTimeUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, LocalTime.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, LocalTime.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, LocalTime.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_LocalDateTime() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (type == LocalDateTime.class) {
          assertTrue(isComparable(type, op, LocalDateTime.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, LocalDateTime.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, LocalDateTime.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_ZonedDateTime() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (InstantUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, ZonedDateTime.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, ZonedDateTime.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, ZonedDateTime.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_java_sql_Date() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (LocalDateUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, java.sql.Date.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, java.sql.Date.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, java.sql.Date.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_java_sql_Time() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (LocalTimeUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, java.sql.Time.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, java.sql.Time.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, java.sql.Time.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_java_sql_Timestamp() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (InstantUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, java.sql.Timestamp.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, java.sql.Timestamp.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, java.sql.Timestamp.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_java_util_Date() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (InstantUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, java.util.Date.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, java.util.Date.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : NON_PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, java.util.Date.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
  }

  @Test
  public void testIsComparable_OP_Gender() {
    for (final ComparisonOperator op : PARTIAL_ORDER_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (EnumUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Gender.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Gender.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
    for (final ComparisonOperator op : COLLECTION_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        assertFalse(isComparable(type, op, Gender.class),
            "field = " + field.getName() + " type = " + type + " op = " + op);
      }
    }
    for (final ComparisonOperator op : LIKE_OPS) {
      for (final Field field : FIELDS) {
        final Class<?> type = field.getType();
        if (EnumUtils.isComparable(type)) {
          assertTrue(isComparable(type, op, Gender.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        } else {
          assertFalse(isComparable(type, op, Gender.class),
              "field = " + field.getName() + " type = " + type + " op = " + op);
        }
      }
    }
  }
}