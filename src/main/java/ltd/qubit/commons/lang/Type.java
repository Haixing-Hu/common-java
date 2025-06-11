////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Array;
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
import ltd.qubit.commons.util.codec.EnumCodec;
import ltd.qubit.commons.util.codec.IsoInstantCodec;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;
import ltd.qubit.commons.util.codec.TimestampCodec;

/**
 * 常见数据类型的枚举。
 *
 * <p>目前此枚举支持以下类型：
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
 * @author 胡海星
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
  STRING_ARRAY,
  ENUM,
  ENUM_ARRAY;

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
    TYPE_CLASS_MAP.put(ENUM, new ClassKey(Enum.class));
    TYPE_CLASS_MAP.put(ENUM_ARRAY, new ClassKey(Enum[].class));
  }

  /**
   * 根据类获取对应的类型。
   *
   * @param clazz
   *     要获取类型的类。
   * @return 对应的类型，如果不支持该类则返回{@code null}。
   */
  @Nullable
  public static Type forClass(final Class<?> clazz) {
    if (clazz.isEnum()) {
      return ENUM;
    }
    if (clazz.isArray() && clazz.getComponentType().isEnum()) {
      return ENUM_ARRAY;
    }
    return CLASS_TYPE_MAP.get(new ClassKey(clazz));
  }

  /**
   * 将类型转换为对应的类。
   *
   * @param type
   *     要转换的类型。
   * @return 对应的类，如果不支持该类型则返回{@code null}。
   */
  @Nullable
  public static Class<?> toClass(final Type type) {
    final ClassKey result = TYPE_CLASS_MAP.get(type);
    return (result == null ? null : result.getActualClass());
  }

  /**
   * 将文本解析为此类型的对象。
   *
   * @param text
   *     要解析的文本，必须已经过必要的修剪。
   * @return
   *     解析后的对象。
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
        return LocalDateTimeCodec.INSTANCE.decodeNoThrow(text);
      case TIME:
        return LocalDateTimeCodec.INSTANCE.decodeNoThrow(text);
      case DATETIME:
        return LocalDateTimeCodec.INSTANCE.decodeNoThrow(text);
      case TIMESTAMP:
        return TimestampCodec.INSTANCE.decodeNoThrow(text);
      case INSTANT:
        return IsoInstantCodec.INSTANCE.decodeNoThrow(text);
      case BYTE_ARRAY:
        return Base64Codec.INSTANCE.decodeNoThrow(text);
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


  /**
   * 将文本解析为指定类型的对象。
   *
   * @param cls
   *     要解析对象的类。
   * @param text
   *     要解析的文本，必须已经过必要的修剪。
   * @return
   *     解析后的对象。
   */
  public static Object parse(final Class<?> cls, final String text) {
    final Type type = forClass(cls);
    if (type == null) {
      throw new IllegalArgumentException("Unsupported class: " + cls.getName());
    }
    switch (type) {
      case ENUM:
        return parseEnum(cls, text);
      case ENUM_ARRAY:
        return parseEnumArray(cls, text);
      default:
        return type.parse(text);
    }
  }

  private static Object parseEnum(final Class<?> enumClass, final String text) {
    @SuppressWarnings({"unchecked", "rawtypes"})
    final EnumCodec<?> codec = new EnumCodec(enumClass);
    return codec.decodeThrowRuntime(text);
  }

  private static Object parseEnumArray(final Class<?> enumClass, final String text) {
    final String[] values = text.split(",");
    final EnumCodec<?> codec = new EnumCodec(enumClass);
    final Enum<?>[] result = (Enum<?>[]) Array.newInstance(enumClass, values.length);
    for (int i = 0; i < values.length; ++i) {
      result[i] = codec.decodeThrowRuntime(values[i]);
    }
    return result;
  }

  /**
   * 将值格式化为字符串。
   *
   * @param value
   *     要格式化的值。
   * @return
   *     格式化后的字符串。
   */
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