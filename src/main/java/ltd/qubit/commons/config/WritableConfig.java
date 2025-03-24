////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Nullable;

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
 * The {@link WritableConfig} interface extends the {@link Config} interface to provide
 * methods for modifying configuration values.
 *
 * @author Haixing Hu
 */
public interface WritableConfig extends Config {

  /**
   * Sets the description of this configuration.
   *
   * @param description
   *     the new description to set, or {@code null} if none.
   */
  void setDescription(@Nullable String description);

  /**
   * Sets the description of a property.
   *
   * @param name
   *     the name of the property.
   * @param description
   *     the new description to set.
   */
  void setDescription(String name, String description);

  /**
   * Removes a property.
   *
   * @param name
   *     the name of the property to remove.
   * @return
   *     the removed property, or null if no such property.
   */
  Property remove(String name);

  /**
   * Sets whether a property is final.
   *
   * @param name
   *     the name of the property.
   * @param isFinal
   *     whether the property should be final.
   */
  void setFinal(String name, boolean isFinal);

  /**
   * Sets the type of a property.
   *
   * @param name
   *     the name of the property.
   * @param type
   *     the new type to set.
   */
  void setType(String name, Type type);

  /**
   * Sets a boolean value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setBoolean(String name, boolean value);

  /**
   * Sets a boolean value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBoolean(String name, boolean value, boolean isFinal);

  /**
   * Sets multiple boolean values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setBooleans(String name, boolean... values);

  /**
   * Sets multiple boolean values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBooleans(String name, boolean[] values, boolean isFinal);

  /**
   * Sets multiple boolean values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setBooleans(String name, BooleanCollection values);

  /**
   * Adds a boolean value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addBoolean(String name, boolean value);

  /**
   * Adds multiple boolean values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBooleans(String name, boolean... values);

  /**
   * Adds multiple boolean values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBooleans(String name, BooleanCollection values);

  // Char operations
  /**
   * Sets a char value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setChar(String name, char value);

  /**
   * Sets a char value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setChar(String name, char value, boolean isFinal);

  /**
   * Sets multiple char values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setChars(String name, char... values);

  /**
   * Sets multiple char values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setChars(String name, char[] values, boolean isFinal);

  /**
   * Sets multiple char values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setChars(String name, CharCollection values);

  /**
   * Adds a char value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addChar(String name, char value);

  /**
   * Adds multiple char values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addChars(String name, char... values);

  /**
   * Adds multiple char values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addChars(String name, CharCollection values);

  // Byte operations
  /**
   * Sets a byte value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setByte(String name, byte value);

  /**
   * Sets a byte value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setByte(String name, byte value, boolean isFinal);

  /**
   * Sets multiple byte values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setBytes(String name, byte... values);

  /**
   * Sets multiple byte values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBytes(String name, byte[] values, boolean isFinal);

  /**
   * Sets multiple byte values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setBytes(String name, ByteCollection values);

  /**
   * Adds a byte value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addByte(String name, byte value);

  /**
   * Adds multiple byte values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBytes(String name, byte... values);

  /**
   * Adds multiple byte values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBytes(String name, ByteCollection values);

  // Short operations
  /**
   * Sets a short value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setShort(String name, short value);

  /**
   * Sets a short value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setShort(String name, short value, boolean isFinal);

  /**
   * Sets multiple short values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setShorts(String name, short... values);

  /**
   * Sets multiple short values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setShorts(String name, short[] values, boolean isFinal);

  /**
   * Sets multiple short values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setShorts(String name, ShortCollection values);

  /**
   * Adds a short value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addShort(String name, short value);

  /**
   * Adds multiple short values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addShorts(String name, short... values);

  /**
   * Adds multiple short values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addShorts(String name, ShortCollection values);

  // Int operations
  /**
   * Sets an int value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setInt(String name, int value);

  /**
   * Sets an int value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setInt(String name, int value, boolean isFinal);

  /**
   * Sets multiple int values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setInts(String name, int... values);

  /**
   * Sets multiple int values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setInts(String name, int[] values, boolean isFinal);

  /**
   * Sets multiple int values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setInts(String name, IntCollection values);

  /**
   * Adds an int value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addInt(String name, int value);

  /**
   * Adds multiple int values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addInts(String name, int... values);

  /**
   * Adds multiple int values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addInts(String name, IntCollection values);

  // Long operations
  /**
   * Sets a long value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setLong(String name, long value);

  /**
   * Sets a long value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setLong(String name, long value, boolean isFinal);

  /**
   * Sets multiple long values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setLongs(String name, long... values);

  /**
   * Sets multiple long values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setLongs(String name, long[] values, boolean isFinal);

  /**
   * Sets multiple long values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setLongs(String name, LongCollection values);

  /**
   * Adds a long value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addLong(String name, long value);

  /**
   * Adds multiple long values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addLongs(String name, long... values);

  /**
   * Adds multiple long values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addLongs(String name, LongCollection values);

  // Float operations
  /**
   * Sets a float value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setFloat(String name, float value);

  /**
   * Sets a float value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setFloat(String name, float value, boolean isFinal);

  /**
   * Sets multiple float values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setFloats(String name, float... values);

  /**
   * Sets multiple float values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setFloats(String name, float[] values, boolean isFinal);

  /**
   * Sets multiple float values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setFloats(String name, FloatCollection values);

  /**
   * Adds a float value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addFloat(String name, float value);

  /**
   * Adds multiple float values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addFloats(String name, float... values);

  /**
   * Adds multiple float values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addFloats(String name, FloatCollection values);

  // Double operations
  /**
   * Sets a double value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   */
  void setDouble(String name, double value);

  /**
   * Sets a double value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setDouble(String name, double value, boolean isFinal);

  /**
   * Sets multiple double values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setDoubles(String name, double... values);

  /**
   * Sets multiple double values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   * @param isFinal
   *     whether the property should be final.
   */
  void setDoubles(String name, double[] values, boolean isFinal);

  /**
   * Sets multiple double values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setDoubles(String name, DoubleCollection values);

  /**
   * Adds a double value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addDouble(String name, double value);

  /**
   * Adds multiple double values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addDoubles(String name, double... values);

  /**
   * Adds multiple double values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addDoubles(String name, DoubleCollection values);

  // String operations
  /**
   * Sets a string value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setString(String name, @Nullable String value);

  /**
   * Sets a string value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setString(String name, @Nullable String value, boolean isFinal);

  /**
   * Sets multiple string values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setStrings(String name, @Nullable String... values);

  /**
   * Sets multiple string values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setStrings(String name, @Nullable String[] values, boolean isFinal);

  /**
   * Sets multiple string values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setStrings(String name, Collection<String> values);

  /**
   * Adds a string value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addString(String name, String value);

  /**
   * Adds multiple string values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addStrings(String name, String... values);

  /**
   * Adds multiple string values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addStrings(String name, Collection<String> values);

  // Date operations
  /**
   * Sets a date value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setDate(String name, @Nullable Date value);

  /**
   * Sets a date value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setDate(String name, @Nullable Date value, boolean isFinal);

  /**
   * Sets multiple date values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setDates(String name, @Nullable Date... values);

  /**
   * Sets multiple date values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setDates(String name, @Nullable Date[] values, boolean isFinal);

  /**
   * Sets multiple date values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setDates(String name, Collection<Date> values);

  /**
   * Adds a date value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addDate(String name, Date value);

  /**
   * Adds multiple date values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addDates(String name, Date... values);

  /**
   * Adds multiple date values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addDates(String name, Collection<Date> values);

  // BigInteger operations
  /**
   * Sets a BigInteger value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setBigInteger(String name, @Nullable BigInteger value);

  /**
   * Sets a BigInteger value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBigInteger(String name, @Nullable BigInteger value, boolean isFinal);

  /**
   * Sets multiple BigInteger values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setBigIntegers(String name, @Nullable BigInteger... values);

  /**
   * Sets multiple BigInteger values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBigIntegers(String name, @Nullable BigInteger[] values, boolean isFinal);

  /**
   * Sets multiple BigInteger values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setBigIntegers(String name, Collection<BigInteger> values);

  /**
   * Adds a BigInteger value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addBigInteger(String name, BigInteger value);

  /**
   * Adds multiple BigInteger values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBigIntegers(String name, BigInteger... values);

  /**
   * Adds multiple BigInteger values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBigIntegers(String name, Collection<BigInteger> values);

  // BigDecimal operations
  /**
   * Sets a BigDecimal value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setBigDecimal(String name, @Nullable BigDecimal value);

  /**
   * Sets a BigDecimal value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBigDecimal(String name, @Nullable BigDecimal value, boolean isFinal);

  /**
   * Sets multiple BigDecimal values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setBigDecimals(String name, @Nullable BigDecimal... values);

  /**
   * Sets multiple BigDecimal values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setBigDecimals(String name, @Nullable BigDecimal[] values, boolean isFinal);

  /**
   * Sets multiple BigDecimal values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setBigDecimals(String name, Collection<BigDecimal> values);

  /**
   * Adds a BigDecimal value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addBigDecimal(String name, BigDecimal value);

  /**
   * Adds multiple BigDecimal values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBigDecimals(String name, BigDecimal... values);

  /**
   * Adds multiple BigDecimal values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addBigDecimals(String name, Collection<BigDecimal> values);

  // ByteArray operations
  /**
   * Sets a byte array value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setByteArray(String name, @Nullable byte[] value);

  /**
   * Sets a byte array value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setByteArray(String name, @Nullable byte[] value, boolean isFinal);

  /**
   * Sets multiple byte array values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setByteArrays(String name, @Nullable byte[]... values);

  /**
   * Sets multiple byte array values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setByteArrays(String name, @Nullable byte[][] values, boolean isFinal);

  /**
   * Sets multiple byte array values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setByteArrays(String name, Collection<byte[]> values);

  /**
   * Adds a byte array value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addByteArray(String name, byte[] value);

  /**
   * Adds multiple byte array values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addByteArrays(String name, byte[]... values);

  /**
   * Adds multiple byte array values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addByteArrays(String name, Collection<byte[]> values);

  // Enum operations
  /**
   * Sets an enum value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setEnum(String name, @Nullable Enum<?> value);

  /**
   * Sets an enum value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setEnum(String name, @Nullable Enum<?> value, boolean isFinal);

  /**
   * Sets multiple enum values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setEnums(String name, @Nullable Enum<?>... values);

  /**
   * Sets multiple enum values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setEnums(String name, @Nullable Enum<?>[] values, boolean isFinal);

  /**
   * Sets multiple enum values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, can be {@code null}.
   */
  void setEnums(String name, @Nullable Collection<? extends Enum<?>> values);

  /**
   * Sets multiple enum values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setEnums(String name, @Nullable Collection<? extends Enum<?>> values, boolean isFinal);

  /**
   * Adds an enum value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addEnum(String name, Enum<?> value);

  /**
   * Adds multiple enum values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addEnums(String name, Enum<?>... values);

  /**
   * Adds multiple enum values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addEnums(String name, Collection<? extends Enum<?>> values);

  // Class operations
  /**
   * Sets a class value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   */
  void setClass(String name, @Nullable Class<?> value);

  /**
   * Sets a class value and its final status.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to set, or {@code null} if none.
   * @param isFinal
   *     whether the property should be final.
   */
  void setClass(String name, @Nullable Class<?> value, boolean isFinal);

  /**
   * Sets multiple class values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   */
  void setClasses(String name, @Nullable Class<?>... values);

  /**
   * Sets multiple class values and their final status.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set, elements can be {@code null}.
   * @param isFinal
   *     whether the property should be final.
   */
  void setClasses(String name, @Nullable Class<?>[] values, boolean isFinal);

  /**
   * Sets multiple class values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to set.
   */
  void setClasses(String name, Collection<Class<?>> values);

  /**
   * Adds a class value.
   *
   * @param name
   *     the name of the property.
   * @param value
   *     the value to add.
   */
  void addClass(String name, Class<?> value);

  /**
   * Adds multiple class values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addClasses(String name, Class<?>... values);

  /**
   * Adds multiple class values.
   *
   * @param name
   *     the name of the property.
   * @param values
   *     the values to add.
   */
  void addClasses(String name, Collection<Class<?>> values);

  /**
   * Merges another configuration into this one.
   *
   * @param config
   *     the configuration to merge from.
   * @param policy
   *     the merging policy to use.
   */
  void merge(Config config, MergingPolicy policy);

  /**
   * Merges another configuration into this one with a prefix.
   *
   * @param config
   *     the configuration to merge from.
   * @param prefix
   *     the prefix to add to property names.
   * @param policy
   *     the merging policy to use.
   */
  void merge(Config config, String prefix, MergingPolicy policy);

  /**
   * Assigns values from another configuration.
   *
   * @param config
   *     the configuration to assign from.
   */
  void assign(Config config);

  /**
   * Assigns values from another configuration with a prefix.
   *
   * @param config
   *     the configuration to assign from.
   * @param prefix
   *     the prefix to add to property names.
   */
  void assign(Config config, String prefix);

  /**
   * Clears a property's values.
   *
   * @param name
   *     the name of the property to clear.
   * @return the cleared property.
   */
  Property clear(String name);

  /**
   * Removes all properties.
   */
  void clear();
}