////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.datastructure.list.primitive.BooleanCollection;
import ltd.qubit.commons.datastructure.list.primitive.ByteCollection;
import ltd.qubit.commons.datastructure.list.primitive.CharCollection;
import ltd.qubit.commons.datastructure.list.primitive.DoubleCollection;
import ltd.qubit.commons.datastructure.list.primitive.FloatCollection;
import ltd.qubit.commons.datastructure.list.primitive.IntCollection;
import ltd.qubit.commons.datastructure.list.primitive.LongCollection;
import ltd.qubit.commons.datastructure.list.primitive.ShortCollection;
import ltd.qubit.commons.lang.Type;

/**
 * {@link WritableConfig} 接口继承了 {@link Config} 接口，提供了修改配置值的方法。
 * <p>
 * 此接口的实现应该是线程安全的，这意味着多个线程可以同时安全地访问和修改配置，而不会导致任何数据不一致或损坏。
 *
 * @author 胡海星
 */
@ThreadSafe
public interface WritableConfig extends Config {

  /**
   * 设置此配置的描述。
   *
   * @param description
   *     要设置的新描述，如果为 {@code null} 则表示没有。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDescription(@Nullable String description);

  /**
   * 设置属性的描述。
   *
   * @param name
   *     属性的名称。
   * @param description
   *     要设置的新描述。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDescription(String name, String description);

  /**
   * 删除一个属性。
   *
   * @param name
   *     要删除的属性的名称。
   * @return
   *     被删除的属性，如果没有此属性则返回 null。
   */
  Property remove(String name);

  /**
   * 设置属性是否是 final 的。
   *
   * @param name
   *     属性的名称。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setFinal(String name, boolean isFinal);

  /**
   * 设置属性的类型。
   *
   * @param name
   *     属性的名称。
   * @param type
   *     要设置的新类型。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setType(String name, Type type);

  /**
   * 设置一个布尔值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBoolean(String name, boolean value);

  /**
   * 设置一个布尔值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBoolean(String name, boolean value, boolean isFinal);

  /**
   * 设置多个布尔值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBooleans(String name, boolean... values);

  /**
   * 设置多个布尔值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBooleans(String name, boolean[] values, boolean isFinal);

  /**
   * 设置多个布尔值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBooleans(String name, BooleanCollection values);

  /**
   * 添加一个布尔值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBoolean(String name, boolean value);

  /**
   * 添加多个布尔值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBooleans(String name, boolean... values);

  /**
   * 添加多个布尔值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBooleans(String name, BooleanCollection values);

  // Char operations
  /**
   * 设置一个字符值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setChar(String name, char value);

  /**
   * 设置一个字符值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setChar(String name, char value, boolean isFinal);

  /**
   * 设置多个字符值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setChars(String name, char... values);

  /**
   * 设置多个字符值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setChars(String name, char[] values, boolean isFinal);

  /**
   * 设置多个字符值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setChars(String name, CharCollection values);

  /**
   * 添加一个字符值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addChar(String name, char value);

  /**
   * 添加多个字符值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addChars(String name, char... values);

  /**
   * 添加多个字符值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addChars(String name, CharCollection values);

  // Byte operations
  /**
   * 设置一个字节值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByte(String name, byte value);

  /**
   * 设置一个字节值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByte(String name, byte value, boolean isFinal);

  /**
   * 设置多个字节值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBytes(String name, byte... values);

  /**
   * 设置多个字节值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBytes(String name, byte[] values, boolean isFinal);

  /**
   * 设置多个字节值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBytes(String name, ByteCollection values);

  /**
   * 添加一个字节值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addByte(String name, byte value);

  /**
   * 添加多个字节值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBytes(String name, byte... values);

  /**
   * 添加多个字节值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBytes(String name, ByteCollection values);

  // Short operations
  /**
   * 设置一个短整数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setShort(String name, short value);

  /**
   * 设置一个短整数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setShort(String name, short value, boolean isFinal);

  /**
   * 设置多个短整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setShorts(String name, short... values);

  /**
   * 设置多个短整数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setShorts(String name, short[] values, boolean isFinal);

  /**
   * 设置多个短整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setShorts(String name, ShortCollection values);

  /**
   * 添加一个短整数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addShort(String name, short value);

  /**
   * 添加多个短整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addShorts(String name, short... values);

  /**
   * 添加多个短整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addShorts(String name, ShortCollection values);

  // Int operations
  /**
   * 设置一个整数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setInt(String name, int value);

  /**
   * 设置一个整数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setInt(String name, int value, boolean isFinal);

  /**
   * 设置多个整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setInts(String name, int... values);

  /**
   * 设置多个整数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setInts(String name, int[] values, boolean isFinal);

  /**
   * 设置多个整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setInts(String name, IntCollection values);

  /**
   * 添加一个整数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addInt(String name, int value);

  /**
   * 添加多个整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addInts(String name, int... values);

  /**
   * 添加多个整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addInts(String name, IntCollection values);

  // Long operations
  /**
   * 设置一个长整数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setLong(String name, long value);

  /**
   * 设置一个长整数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setLong(String name, long value, boolean isFinal);

  /**
   * 设置多个长整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setLongs(String name, long... values);

  /**
   * 设置多个长整数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setLongs(String name, long[] values, boolean isFinal);

  /**
   * 设置多个长整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setLongs(String name, LongCollection values);

  /**
   * 添加一个长整数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addLong(String name, long value);

  /**
   * 添加多个长整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addLongs(String name, long... values);

  /**
   * 添加多个长整数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addLongs(String name, LongCollection values);

  // Float operations
  /**
   * 设置一个浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setFloat(String name, float value);

  /**
   * 设置一个浮点数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setFloat(String name, float value, boolean isFinal);

  /**
   * 设置多个浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setFloats(String name, float... values);

  /**
   * 设置多个浮点数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setFloats(String name, float[] values, boolean isFinal);

  /**
   * 设置多个浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setFloats(String name, FloatCollection values);

  /**
   * 添加一个浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addFloat(String name, float value);

  /**
   * 添加多个浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addFloats(String name, float... values);

  /**
   * 添加多个浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addFloats(String name, FloatCollection values);

  // Double operations
  /**
   * 设置一个双精度浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDouble(String name, double value);

  /**
   * 设置一个双精度浮点数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDouble(String name, double value, boolean isFinal);

  /**
   * 设置多个双精度浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDoubles(String name, double... values);

  /**
   * 设置多个双精度浮点数值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDoubles(String name, double[] values, boolean isFinal);

  /**
   * 设置多个双精度浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDoubles(String name, DoubleCollection values);

  /**
   * 添加一个双精度浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDouble(String name, double value);

  /**
   * 添加多个双精度浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDoubles(String name, double... values);

  /**
   * 添加多个双精度浮点数值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDoubles(String name, DoubleCollection values);

  // String operations
  /**
   * 设置一个字符串值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setString(String name, @Nullable String value);

  /**
   * 设置一个字符串值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setString(String name, @Nullable String value, boolean isFinal);

  /**
   * 设置多个字符串值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setStrings(String name, @Nullable String... values);

  /**
   * 设置多个字符串值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setStrings(String name, @Nullable String[] values, boolean isFinal);

  /**
   * 设置多个字符串值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setStrings(String name, Collection<String> values);

  /**
   * 添加一个字符串值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addString(String name, String value);

  /**
   * 添加多个字符串值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addStrings(String name, String... values);

  /**
   * 添加多个字符串值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addStrings(String name, Collection<String> values);

  // Date operations
  /**
   * 设置一个日期值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDate(String name, @Nullable LocalDate value);

  /**
   * 设置一个日期值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDate(String name, @Nullable LocalDate value, boolean isFinal);

  /**
   * 设置多个日期值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDates(String name, @Nullable LocalDate... values);

  /**
   * 设置多个日期值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDates(String name, @Nullable LocalDate[] values, boolean isFinal);

  /**
   * 设置多个日期值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDates(String name, Collection<LocalDate> values);

  /**
   * 添加一个日期值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDate(String name, LocalDate value);

  /**
   * 添加多个日期值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDates(String name, LocalDate... values);

  /**
   * 添加多个日期值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDates(String name, Collection<LocalDate> values);

  // Time operations
  /**
   * 设置一个时间值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setTime(String name, @Nullable LocalTime value);

  /**
   * 设置一个时间值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setTime(String name, @Nullable LocalTime value, boolean isFinal);

  /**
   * 设置多个时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setTimes(String name, @Nullable LocalTime... values);

  /**
   * 设置多个时间值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setTimes(String name, @Nullable LocalTime[] values, boolean isFinal);

  /**
   * 设置多个时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setTimes(String name, Collection<LocalTime> values);

  /**
   * 添加一个时间值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addTime(String name, LocalTime value);

  /**
   * 添加多个时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addTimes(String name, LocalTime... values);

  /**
   * 添加多个时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addTimes(String name, Collection<LocalTime> values);

  // DateTime operations
  /**
   * 设置一个日期时间值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDateTime(String name, @Nullable LocalDateTime value);

  /**
   * 设置一个日期时间值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDateTime(String name, @Nullable LocalDateTime value, boolean isFinal);

  /**
   * 设置多个日期时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDateTimes(String name, @Nullable LocalDateTime... values);

  /**
   * 设置多个日期时间值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDateTimes(String name, @Nullable LocalDateTime[] values, boolean isFinal);

  /**
   * 设置多个日期时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setDateTimes(String name, Collection<LocalDateTime> values);

  /**
   * 添加一个日期时间值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDateTime(String name, LocalDateTime value);

  /**
   * 添加多个日期时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDateTimes(String name, LocalDateTime... values);

  /**
   * 添加多个日期时间值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addDateTimes(String name, Collection<LocalDateTime> values);

  // BigInteger operations
  /**
   * 设置一个 BigInteger 值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigInteger(String name, @Nullable BigInteger value);

  /**
   * 设置一个 BigInteger 值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigInteger(String name, @Nullable BigInteger value, boolean isFinal);

  /**
   * 设置多个 BigInteger 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigIntegers(String name, @Nullable BigInteger... values);

  /**
   * 设置多个 BigInteger 值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigIntegers(String name, @Nullable BigInteger[] values, boolean isFinal);

  /**
   * 设置多个 BigInteger 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigIntegers(String name, Collection<BigInteger> values);

  /**
   * 添加一个 BigInteger 值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBigInteger(String name, BigInteger value);

  /**
   * 添加多个 BigInteger 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBigIntegers(String name, BigInteger... values);

  /**
   * 添加多个 BigInteger 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBigIntegers(String name, Collection<BigInteger> values);

  // BigDecimal operations
  /**
   * 设置一个 BigDecimal 值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigDecimal(String name, @Nullable BigDecimal value);

  /**
   * 设置一个 BigDecimal 值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigDecimal(String name, @Nullable BigDecimal value, boolean isFinal);

  /**
   * 设置多个 BigDecimal 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigDecimals(String name, @Nullable BigDecimal... values);

  /**
   * 设置多个 BigDecimal 值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigDecimals(String name, @Nullable BigDecimal[] values, boolean isFinal);

  /**
   * 设置多个 BigDecimal 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setBigDecimals(String name, Collection<BigDecimal> values);

  /**
   * 添加一个 BigDecimal 值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBigDecimal(String name, BigDecimal value);

  /**
   * 添加多个 BigDecimal 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBigDecimals(String name, BigDecimal... values);

  /**
   * 添加多个 BigDecimal 值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addBigDecimals(String name, Collection<BigDecimal> values);

  // ByteArray operations
  /**
   * 设置一个字节数组值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByteArray(String name, @Nullable byte[] value);

  /**
   * 设置一个字节数组值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByteArray(String name, @Nullable byte[] value, boolean isFinal);

  /**
   * 设置多个字节数组值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByteArrays(String name, @Nullable byte[]... values);

  /**
   * 设置多个字节数组值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByteArrays(String name, @Nullable byte[][] values, boolean isFinal);

  /**
   * 设置多个字节数组值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setByteArrays(String name, Collection<byte[]> values);

  /**
   * 添加一个字节数组值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addByteArray(String name, byte[] value);

  /**
   * 添加多个字节数组值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addByteArrays(String name, byte[]... values);

  /**
   * 添加多个字节数组值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addByteArrays(String name, Collection<byte[]> values);

  // Enum operations
  /**
   * 设置一个枚举值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setEnum(String name, @Nullable Enum<?> value);

  /**
   * 设置一个枚举值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setEnum(String name, @Nullable Enum<?> value, boolean isFinal);

  /**
   * 设置多个枚举值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setEnums(String name, @Nullable Enum<?>... values);

  /**
   * 设置多个枚举值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setEnums(String name, @Nullable Enum<?>[] values, boolean isFinal);

  /**
   * 设置多个枚举值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setEnums(String name, @Nullable Collection<? extends Enum<?>> values);

  /**
   * 设置多个枚举值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setEnums(String name, @Nullable Collection<? extends Enum<?>> values, boolean isFinal);

  /**
   * 添加一个枚举值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addEnum(String name, Enum<?> value);

  /**
   * 添加多个枚举值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addEnums(String name, Enum<?>... values);

  /**
   * 添加多个枚举值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addEnums(String name, Collection<? extends Enum<?>> values);

  // Class operations
  /**
   * 设置一个类对象值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setClass(String name, @Nullable Class<?> value);

  /**
   * 设置一个类对象值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要设置的值，可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setClass(String name, @Nullable Class<?> value, boolean isFinal);

  /**
   * 设置多个类对象值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setClasses(String name, @Nullable Class<?>... values);

  /**
   * 设置多个类对象值及其 final 状态。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值，其元素可以为 {@code null}。
   * @param isFinal
   *     属性是否应该是 final 的。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setClasses(String name, @Nullable Class<?>[] values, boolean isFinal);

  /**
   * 设置多个类对象值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要设置的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig setClasses(String name, Collection<Class<?>> values);

  /**
   * 添加一个类对象值。
   *
   * @param name
   *     属性的名称。
   * @param value
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addClass(String name, Class<?> value);

  /**
   * 添加多个类对象值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addClasses(String name, Class<?>... values);

  /**
   * 添加多个类对象值。
   *
   * @param name
   *     属性的名称。
   * @param values
   *     要添加的值。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig addClasses(String name, Collection<Class<?>> values);

  /**
   * 将另一个配置合并到此配置中。
   *
   * @param config
   *     要从中合并的配置。
   * @param policy
   *     要使用的合并策略。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig merge(Config config, MergingPolicy policy);

  /**
   * 将另一个带前缀的配置合并到此配置中。
   *
   * @param config
   *     要从中合并的配置。
   * @param prefix
   *     要添加到属性名称的前缀。
   * @param policy
   *     要使用的合并策略。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig merge(Config config, String prefix, MergingPolicy policy);

  /**
   * 从另一个配置中分配值。
   *
   * @param config
   *     要从中分配的配置。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig assign(Config config);

  /**
   * 从另一个带前缀的配置中分配值。
   *
   * @param config
   *     要从中分配的配置。
   * @param prefix
   *     要添加到属性名称的前缀。
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig assign(Config config, String prefix);

  /**
   * 清除属性的值。
   *
   * @param name
   *     要清除的属性的名称。
   * @return 清除后的属性。
   */
  Property clear(String name);

  /**
   * 删除所有属性。
   *
   * @return 此 {@link WritableConfig} 对象，用于方法链式调用。
   */
  WritableConfig clear();
}