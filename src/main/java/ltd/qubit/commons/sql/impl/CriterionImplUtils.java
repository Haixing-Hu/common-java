////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;

import javax.annotation.Nullable;

import ltd.qubit.commons.error.UnsupportedDataTypeException;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.InstantUtils;
import ltd.qubit.commons.lang.LocalDateTimeUtils;
import ltd.qubit.commons.lang.LocalDateUtils;
import ltd.qubit.commons.lang.LocalTimeUtils;
import ltd.qubit.commons.lang.NumericUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.sql.Criterion;
import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.util.ComparisonOperator;
import ltd.qubit.commons.util.codec.InstantCodec;
import ltd.qubit.commons.util.codec.IsoInstantCodec;
import ltd.qubit.commons.util.codec.IsoLocalDateCodec;
import ltd.qubit.commons.util.codec.IsoLocalDateTimeCodec;
import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;
import ltd.qubit.commons.util.codec.LocalDateCodec;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;
import ltd.qubit.commons.util.codec.LocalTimeCodec;

/**
 * 提供实现{@link Criterion}相关类的工具函数。
 *
 * @author 胡海星
 */
public class CriterionImplUtils {

  private static final String FIELD_QUOTE = "`";

  private static final String STRING_QUOTE = "'";

  private static final String LEFT_PARENTHESIS = "(";

  private static final String RIGHT_PARENTHESIS = ")";

  private static final String LIST_SEPARATOR = ", ";

  private static final InstantCodec INSTANT_CODEC = new IsoInstantCodec();

  private static final LocalDateTimeCodec LOCAL_DATETIME_CODEC = new IsoLocalDateTimeCodec();

  private static final LocalDateCodec LOCAL_DATE_CODEC = new IsoLocalDateCodec();

  private static final LocalTimeCodec LOCAL_TIME_CODEC = new IsoLocalTimeCodec();
  private static final Class<?>[] SUPPORTED_NON_ARRAY_VALUE_TYPES = {
      boolean.class,
      byte.class,
      short.class,
      int.class,
      long.class,
      float.class,
      double.class,
      Boolean.class,
      Byte.class,
      Short.class,
      Integer.class,
      Long.class,
      Float.class,
      Double.class,
      BigInteger.class,
      BigDecimal.class,
      char.class,
      Character.class,
      String.class,
      Enum.class,
      LocalDate.class,
      LocalTime.class,
      java.sql.Time.class,
      LocalDateTime.class,
      Instant.class,
      ZonedDateTime.class,
      java.sql.Date.class,
      java.sql.Timestamp.class,
      java.util.Date.class,
  };

  public static String fieldToSql(final String field) {
    return FIELD_QUOTE + field + FIELD_QUOTE;
  }

  @Nullable
  public static String stringToSql(@Nullable final String str) {
    if (str == null) {
      return null;
    }
    final String escaped = new Replacer()
        .searchForChar('\'')
        .replaceWithString("\\'")
        .applyTo(str);
    return STRING_QUOTE + escaped + STRING_QUOTE;
  }

  @Nullable
  public static String charToSql(@Nullable final Character value) {
    return (value == null ? null : STRING_QUOTE + value + STRING_QUOTE);
  }

  public static String charToSql(final char value) {
    return STRING_QUOTE + value + STRING_QUOTE;
  }

  @Nullable
  public static String boolToSql(@Nullable final Boolean value) {
    return (value == null ? null : (value ? "1" : "0"));
  }

  @Nullable
  public static String boolToSql(final boolean value) {
    return (value ? "1" : "0");
  }

  @Nullable
  public static String numberToSql(@Nullable final Number value) {
    return (value == null ? null : value.toString());
  }

  public static String numberToSql(final byte value) {
    return String.valueOf(value);
  }

  public static String numberToSql(final short value) {
    return String.valueOf(value);
  }

  public static String numberToSql(final int value) {
    return String.valueOf(value);
  }

  public static String numberToSql(final long value) {
    return String.valueOf(value);
  }

  public static String numberToSql(final float value) {
    return String.valueOf(value);
  }

  public static String numberToSql(final double value) {
    return String.valueOf(value);
  }

  @Nullable
  public static String arrayToSql(@Nullable final Object obj) {
    if (obj == null) {
      return null;
    }
    final Class<?> cls = obj.getClass();
    if (! cls.isArray()) {
      throw new IllegalArgumentException("The argument must be an array");
    }
    if (cls == boolean[].class) {
      return arrayToSql((boolean[]) obj);
    } else if (cls == char[].class) {
      return arrayToSql((char[]) obj);
    } else if (cls == byte[].class) {
      return arrayToSql((byte[]) obj);
    } else if (cls == short[].class) {
      return arrayToSql((short[]) obj);
    } else if (cls == int[].class) {
      return arrayToSql((int[]) obj);
    } else if (cls == long[].class) {
      return arrayToSql((long[]) obj);
    } else if (cls == float[].class) {
      return arrayToSql((float[]) obj);
    } else if (cls == double[].class) {
      return arrayToSql((double[]) obj);
    }
    final Object[] array = (Object[]) obj;
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final Object val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(valueToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final boolean[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final boolean val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(boolToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  public static String arrayToSql(final char[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final char val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(charToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final byte[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final byte val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(numberToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final short[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final short val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(numberToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final int[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final int val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(numberToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final long[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final long val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(numberToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final float[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final float val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(numberToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String arrayToSql(@Nullable final double[] array) {
    if (array == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final double val : array) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(numberToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  @Nullable
  public static String collectionToSql(@Nullable final Collection<?> col) {
    if (col == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    builder.append(LEFT_PARENTHESIS);
    boolean first = true;
    for (final Object val : col) {
      if (first) {
        first = false;
      } else {
        builder.append(LIST_SEPARATOR);
      }
      builder.append(valueToSql(val));
    }
    builder.append(RIGHT_PARENTHESIS);
    return builder.toString();
  }

  public static String localDateToSql(@Nullable final LocalDate value) {
    return (value == null ? null : stringToSql(LOCAL_DATE_CODEC.encode(value)));
  }

  public static String localTimeToSql(@Nullable final LocalTime value) {
    return (value == null ? null : stringToSql(LOCAL_TIME_CODEC.encode(value)));
  }

  public static String localDateTimeToSql(@Nullable final LocalDateTime value) {
    return (value == null ? null : stringToSql(LOCAL_DATETIME_CODEC.encode(value)));
  }

  public static String zonedDateTimeToSql(@Nullable final ZonedDateTime value) {
    return (value == null ? null : stringToSql(INSTANT_CODEC.encode(value.toInstant())));
  }

  public static String instantToSql(@Nullable final Instant value) {
    return (value == null ? null : stringToSql(INSTANT_CODEC.encode(value)));
  }

  public static String utilDateToSql(@Nullable final java.util.Date value) {
    return (value == null ? null : stringToSql(INSTANT_CODEC.encode(value.toInstant())));
  }

  public static String sqlDateToSql(@Nullable final java.sql.Date value) {
    return (value == null ? null : stringToSql(LOCAL_DATE_CODEC.encode(value.toLocalDate())));
  }

  public static String sqlTimeToSql(@Nullable final java.sql.Time value) {
    return (value == null ? null : stringToSql(LOCAL_TIME_CODEC.encode(value.toLocalTime())));
  }

  public static String sqlTimestampToSql(@Nullable final java.sql.Timestamp value) {
    return (value == null ? null : stringToSql(INSTANT_CODEC.encode(value.toInstant())));
  }

  public static String enumToSql(@Nullable final Enum<?> value) {
    return (value == null ? null : stringToSql(value.name()));
  }

  /**
   * 将给定的数值转换为对应的SQL语句的 Literal 表达式。
   *
   * @param value
   *     给定的数值。
   * @return
   *     给定的数值对应的SQL语句的 Literal 表达式。
   * @throws UnsupportedDataTypeException
   *     如果给定的数值类型不是支持的SQL数据类型。
   */
  public static String valueToSql(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof String) {
      return stringToSql((String) value);
    } else if (value instanceof Character) {
      return charToSql((Character) value);
    } else if (value instanceof Boolean) {
      return boolToSql((Boolean) value);
    } else if (value instanceof Number) {
      return numberToSql((Number) value);
    } else if (value instanceof Instant) {
      return instantToSql((Instant) value);
    } else if (value instanceof LocalDate) {
      return localDateToSql((LocalDate) value);
    } else if (value instanceof LocalTime) {
      return localTimeToSql((LocalTime) value);
    } else if (value instanceof LocalDateTime) {
      return localDateTimeToSql((LocalDateTime) value);
    } else if (value instanceof ZonedDateTime) {
      return zonedDateTimeToSql((ZonedDateTime) value);
    } else if (value instanceof java.sql.Date) {
      return sqlDateToSql((java.sql.Date) value);
    } else if (value instanceof java.sql.Time) {
      return sqlTimeToSql((java.sql.Time) value);
    } else if (value instanceof java.sql.Timestamp) {
      return sqlTimestampToSql((java.sql.Timestamp) value);
    } else if (value instanceof java.util.Date) {
      return utilDateToSql((java.util.Date) value);
    } else if (value instanceof Collection) {
      return collectionToSql((Collection<?>) value);
    } else if (value instanceof Enum) {
      return enumToSql((Enum<?>) value);
    } else if (value.getClass().isArray()) {
      return arrayToSql(value);
    } else {
      throw new UnsupportedDataTypeException(value.getClass().getName());
    }
  }

  /**
   * 测试给定的数值类型是否是支持的SQL数据类型。
   *
   * @param type
   *     给定的数值类型。
   * @return
   *     如果给定的数值类型是支持的SQL数据类型，返回{@code true}；否则返回{@code false}。
   */
  public static boolean isSupportedDataType(final Class<?> type) {
    if (isSupportedNonArrayDataType(type)) {
      return true;
    }
    if (type.isArray()) {
      final Class<?> componentType = type.getComponentType();
      return isSupportedDataType(componentType);
    }
    return false;
  }

  /**
   * 测试给定的数值类型是否是支持的非数组SQL数据类型。
   *
   * @param type
   *     给定的数值类型。
   * @return
   *     如果给定的数值类型是支持的非数组SQL数据类型，返回{@code true}；否则返回
   *     {@code false}。
   */
  public static boolean isSupportedNonArrayDataType(final Class<?> type) {
    for (final Class<?> cls : SUPPORTED_NON_ARRAY_VALUE_TYPES) {
      if (cls.isAssignableFrom(type)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判定表达式左右两边类型是否可比较。
   *
   * @param lhsType
   *     左边的类型。
   * @param operator
   *     比较操作符。
   * @param rhsType
   *     右边的类型，若为{@code null}表示右边的值为{@code null}。
   * @return
   *     如果左右两边类型可比较，则返回{@code true}；否则返回{@code false}。
   */
  public static boolean isComparable(final Class<?> lhsType,
      final ComparisonOperator operator, @Nullable final Class<?> rhsType) {
    if (rhsType == null) {
      switch (operator) {
        case EQUAL:
        case NOT_EQUAL:
          return isSupportedDataType(lhsType) && (!lhsType.isPrimitive());
        default:
          return false;
      }
    } else {
      // enum is treated as string
      Class<?> lhs = lhsType;
      Class<?> rhs = rhsType;
      if (Enum.class.isAssignableFrom(lhsType)) {
        lhs = String.class;
      }
      if (Enum.class.isAssignableFrom(rhsType)) {
        rhs = String.class;
      }
      switch (operator) {
        case EQUAL:
        case NOT_EQUAL:
        case LESS:
        case LESS_EQUAL:
        case GREATER:
        case GREATER_EQUAL:
          return isPartialOrderComparable(lhs, rhs);
        case IN:
        case NOT_IN:
          return rhs.isArray() && isPartialOrderComparable(lhs, rhs.getComponentType());
        case LIKE:
        case NOT_LIKE:
          return StringUtils.isComparable(lhs) && StringUtils.isComparable(rhs);
        default:
          return false;
      }
    }
  }

  public static boolean isPartialOrderComparable(final Class<?> lhsType,
      final Class<?> rhsType) {
    return (NumericUtils.isComparable(lhsType) && NumericUtils.isComparable(rhsType))
        || (StringUtils.isComparable(lhsType) && StringUtils.isComparable(rhsType))
        || (InstantUtils.isComparable(lhsType) && InstantUtils.isComparable(rhsType))
        || (LocalDateUtils.isComparable(lhsType) && LocalDateUtils.isComparable(rhsType))
        || (LocalTimeUtils.isComparable(lhsType) && LocalTimeUtils.isComparable(rhsType))
        || (LocalDateTimeUtils.isComparable(lhsType) && LocalDateTimeUtils.isComparable(rhsType));
  }

  public static boolean isPartialOrderComparable(final Class<?> type) {
    return NumericUtils.isComparable(type)
        || StringUtils.isComparable(type)
        || InstantUtils.isComparable(type)
        || LocalDateUtils.isComparable(type)
        || LocalTimeUtils.isComparable(type)
        || LocalDateTimeUtils.isComparable(type);
  }

  public static Object toComparableValue(final Object value) {
    if (value == null) {
      throw new NullPointerException("value");
    }
    final Class<?> type = value.getClass();
    if (type.isArray()) {
      return toComparableValueArray(value);
    } else {
      return toComparableNonArrayValue(value);
    }
  }

  private static Object toComparableValueArray(final Object value) {
    final Class<?> type = value.getClass();
    if (!type.isArray()) {
      throw new IllegalArgumentException("The value is not an array.");
    }
    final Object[] array;
    if (type.getComponentType().isPrimitive()) {
      array = ArrayUtils.toObject(value);
    } else {
      array = (Object[]) value;
    }
    final Object[] result = new Object[array.length];
    for (int i = 0; i < array.length; ++i) {
      result[i] = toComparableNonArrayValue(array[i]);
    }
    return result;
  }

  private static Object toComparableNonArrayValue(@Nullable final Object value) {
    if (value == null) {
      return null;
    }
    final Class<?> cls = value.getClass();
    if (NumericUtils.isComparable(cls)) {
      return NumericUtils.toNumeric(value);
    } else if (StringUtils.isComparable(cls)) {
      return StringUtils.toString(value);
    } else if (InstantUtils.isComparable(cls)) {
      return InstantUtils.toInstant(value);
    } else if (LocalDateUtils.isComparable(cls)) {
      return LocalDateUtils.toLocalDate(value);
    } else if (LocalTimeUtils.isComparable(cls)) {
      return LocalTimeUtils.toLocalTime(value);
    } else if (LocalDateTimeUtils.isComparable(cls)) {
      return LocalDateTimeUtils.toLocalDateTime(value);
    } else {
      throw new IllegalArgumentException("Unsupported comparable value type: "
          + value.getClass().getName());
    }
  }
}
