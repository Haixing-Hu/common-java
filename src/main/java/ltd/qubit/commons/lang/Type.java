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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.codec.Base64Codec;
import ltd.qubit.commons.util.codec.DecodingException;

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
  INSTANT,
  BYTE_ARRAY,
  CLASS,
  BIG_INTEGER,
  BIG_DECIMAL,
  STRING_ARRAY;

  private static final Map<ClassKey, Type>  CLASS_TYPE_MAP = new HashMap<>();

  static {
    CLASS_TYPE_MAP.put(new ClassKey(Boolean.class), BOOL);
    CLASS_TYPE_MAP.put(new ClassKey(Character.class), CHAR);
    CLASS_TYPE_MAP.put(new ClassKey(Byte.class), BYTE);
    CLASS_TYPE_MAP.put(new ClassKey(Short.class), SHORT);
    CLASS_TYPE_MAP.put(new ClassKey(Integer.class), INT);
    CLASS_TYPE_MAP.put(new ClassKey(Long.class), LONG);
    CLASS_TYPE_MAP.put(new ClassKey(Float.class), FLOAT);
    CLASS_TYPE_MAP.put(new ClassKey(Double.class), DOUBLE);
    CLASS_TYPE_MAP.put(new ClassKey(String.class), STRING);
    CLASS_TYPE_MAP.put(new ClassKey(LocalDate.class), DATE);
    CLASS_TYPE_MAP.put(new ClassKey(LocalTime.class), TIME);
    CLASS_TYPE_MAP.put(new ClassKey(LocalDateTime.class), DATETIME);
    CLASS_TYPE_MAP.put(new ClassKey(Timestamp.class), TIMESTAMP);
    CLASS_TYPE_MAP.put(new ClassKey(Instant.class), INSTANT);
    CLASS_TYPE_MAP.put(new ClassKey(byte[].class), BYTE_ARRAY);
    CLASS_TYPE_MAP.put(new ClassKey(Class.class), CLASS);
    CLASS_TYPE_MAP.put(new ClassKey(BigInteger.class), BIG_INTEGER);
    CLASS_TYPE_MAP.put(new ClassKey(BigDecimal.class), BIG_DECIMAL);
    CLASS_TYPE_MAP.put(new ClassKey(String[].class), STRING_ARRAY);
    // add support of primitive types
    CLASS_TYPE_MAP.put(new ClassKey(boolean.class), BOOL);
    CLASS_TYPE_MAP.put(new ClassKey(char.class), CHAR);
    CLASS_TYPE_MAP.put(new ClassKey(byte.class), BYTE);
    CLASS_TYPE_MAP.put(new ClassKey(short.class), SHORT);
    CLASS_TYPE_MAP.put(new ClassKey(int.class), INT);
    CLASS_TYPE_MAP.put(new ClassKey(long.class), LONG);
    CLASS_TYPE_MAP.put(new ClassKey(float.class), FLOAT);
    CLASS_TYPE_MAP.put(new ClassKey(double.class), DOUBLE);
  }

  private static final Map<Type, ClassKey>  TYPE_CLASS_MAP = new HashMap<>();
  static {
    TYPE_CLASS_MAP.put(BOOL, new ClassKey(Boolean.class));
    TYPE_CLASS_MAP.put(CHAR, new ClassKey(Character.class));
    TYPE_CLASS_MAP.put(BYTE, new ClassKey(Byte.class));
    TYPE_CLASS_MAP.put(SHORT, new ClassKey(Short.class));
    TYPE_CLASS_MAP.put(INT, new ClassKey(Integer.class));
    TYPE_CLASS_MAP.put(LONG, new ClassKey(Long.class));
    TYPE_CLASS_MAP.put(FLOAT, new ClassKey(Float.class));
    TYPE_CLASS_MAP.put(DOUBLE, new ClassKey(Double.class));
    TYPE_CLASS_MAP.put(STRING, new ClassKey(String.class));
    TYPE_CLASS_MAP.put(DATE, new ClassKey(LocalDate.class));
    TYPE_CLASS_MAP.put(TIME, new ClassKey(LocalTime.class));
    TYPE_CLASS_MAP.put(DATETIME, new ClassKey(LocalDateTime.class));
    TYPE_CLASS_MAP.put(TIMESTAMP, new ClassKey(Timestamp.class));
    TYPE_CLASS_MAP.put(INSTANT, new ClassKey(Instant.class));
    TYPE_CLASS_MAP.put(BYTE_ARRAY, new ClassKey(byte[].class));
    TYPE_CLASS_MAP.put(CLASS, new ClassKey(Class.class));
    TYPE_CLASS_MAP.put(BIG_INTEGER, new ClassKey(BigInteger.class));
    TYPE_CLASS_MAP.put(BIG_DECIMAL, new ClassKey(BigDecimal.class));
    TYPE_CLASS_MAP.put(STRING_ARRAY, new ClassKey(String[].class));
  }

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
      case INSTANT:
        return Instant.parse(text);
      case BYTE_ARRAY:
        try {
          return Base64Codec.INSTANCE.decode(text);
        } catch (final DecodingException e) {
          throw new RuntimeException(e);
        }
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
      case STRING_ARRAY:
        return text.split(",");
      default:
        throw new UnsupportedOperationException("Unsupported type: " + this);
    }
  }

  public String format(final Object value) {
    switch (this) {
      case BOOL:
        return Boolean.toString((Boolean) value);
      case CHAR:
        return Character.toString((Character) value);
      case BYTE:
        return Byte.toString((Byte) value);
      case SHORT:
        return Short.toString((Short) value);
      case INT:
        return Integer.toString((Integer) value);
      case LONG:
        return Long.toString((Long) value);
      case FLOAT:
        return Float.toString((Float) value);
      case DOUBLE:
        return Double.toString((Double) value);
      case STRING:
        return (String) value;
      case DATE:
        return ((LocalDate) value).toString();
      case TIME:
        return ((LocalTime) value).toString();
      case DATETIME:
        return ((LocalDateTime) value).toString();
      case TIMESTAMP:
        return value.toString();
      case INSTANT:
        return ((Instant) value).toString();
      case BYTE_ARRAY:
        return Base64Codec.INSTANCE.encode((byte[]) value);
      case CLASS:
        return ((Class<?>) value).getName();
      case BIG_INTEGER:
        return value.toString();
      case BIG_DECIMAL:
        return value.toString();
      case STRING_ARRAY:
        return String.join(",", (String[]) value);
      default:
        throw new UnsupportedOperationException("Unsupported type: " + this);
    }
  }
}
