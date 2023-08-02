////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import static java.util.Map.entry;

import static ltd.qubit.commons.datastructure.map.MapUtils.invertAsUnmodifiable;

/**
 * The enumeration of common data types.
 *
 * <p>Currently this enumeration supports the following types:
 *
 * <ul>
 * <li>{@code bool}</li>
 * <li>{@code char}</li>
 * <li>{@code byte}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@link String}</li>
 * <li>{@link LocalDate}</li>
 * <li>{@link LocalTime}</li>
 * <li>{@link LocalDateTime}</li>
 * <li>{@link Timestamp}</li>
 * <li>{@code byte[]}</li>
 * <li>{@link Class}</li>
 * <li>{@link BigInteger}</li>
 * <li>{@link BigDecimal}</li>
 * </ul>
 *
 * @author Haixing Hu
 */
public enum Type {
  BOOL,
  CHAR,
  BYTE,
  SHORT,
  INT,
  LONG,
  FLOAT,
  DOUBLE,
  STRING,
  DATE,
  TIME,
  DATETIME,
  TIMESTAMP,
  BYTE_ARRAY,
  CLASS,
  BIG_INTEGER,
  BIG_DECIMAL;

  private static final Map<ClassKey, Type>  CLASS_TYPE_MAP =
      Map.ofEntries(
          entry(new ClassKey(Boolean.class), BOOL),
          entry(new ClassKey(Character.class), CHAR),
          entry(new ClassKey(Byte.class), BYTE),
          entry(new ClassKey(Short.class), SHORT),
          entry(new ClassKey(Integer.class), INT),
          entry(new ClassKey(Long.class), LONG),
          entry(new ClassKey(Float.class), FLOAT),
          entry(new ClassKey(Double.class), DOUBLE),
          entry(new ClassKey(String.class), STRING),
          entry(new ClassKey(LocalDate.class), DATE),
          entry(new ClassKey(LocalTime.class), TIME),
          entry(new ClassKey(LocalDateTime.class), DATETIME),
          entry(new ClassKey(Timestamp.class), TIMESTAMP),
          entry(new ClassKey(byte[].class), BYTE_ARRAY),
          entry(new ClassKey(Class.class), CLASS),
          entry(new ClassKey(BigInteger.class), BIG_INTEGER),
          entry(new ClassKey(BigDecimal.class), BIG_DECIMAL)
      );

  private static final Map<Type, ClassKey>  TYPE_CLASS_MAP =
      invertAsUnmodifiable(CLASS_TYPE_MAP);

  public static Type forClass(final Class<?> clazz) {
    return CLASS_TYPE_MAP.get(new ClassKey(clazz));
  }

  public static Class<?> toClass(final Type type) {
    final ClassKey result = TYPE_CLASS_MAP.get(type);
    return (result == null ? null : result.getActualClass());
  }
}
