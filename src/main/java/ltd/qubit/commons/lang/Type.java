////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import javax.annotation.Nullable;

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

  @Nullable
  public static Type forClass(final Class<?> clazz) {
    return CLASS_TYPE_MAP.get(new ClassKey(clazz));
  }

  @Nullable
  public static Class<?> toClass(final Type type) {
    final ClassKey result = TYPE_CLASS_MAP.get(type);
    return (result == null ? null : result.getActualClass());
  }

  /**
   * Parses a text to an object of this type.
   *
   * @param text
   *     the text to be parsed, which must be trimmed if necessary.
   * @return
   *     the parsed object.
   */
  public Object parse(final String text) {
    switch (this) {
      case BOOL:
        return Boolean.parseBoolean(text);
      case CHAR:
        return text.charAt(0);
      case BYTE:
        return Byte.parseByte(text);
      case SHORT:
        return Short.parseShort(text);
      case INT:
        return Integer.parseInt(text);
      case LONG:
        return Long.parseLong(text);
      case FLOAT:
        return Float.parseFloat(text);
      case DOUBLE:
        return Double.parseDouble(text);
      case STRING:
        return text;
      case DATE:
        return LocalDate.parse(text);
      case TIME:
        return LocalTime.parse(text);
      case DATETIME:
        return LocalDateTime.parse(text);
      case TIMESTAMP:
        return Timestamp.valueOf(text);
      case BYTE_ARRAY:
        return text.getBytes();
      case CLASS:
        try {
          return Class.forName(text);
        } catch (final ClassNotFoundException e) {
          throw new IllegalArgumentException("Invalid class name: " + text, e);
        }
      case BIG_INTEGER:
        return new BigInteger(text);
      case BIG_DECIMAL:
        return new BigDecimal(text);
      default:
        throw new UnsupportedOperationException("Unsupported type: " + this);
    }
  }
}
