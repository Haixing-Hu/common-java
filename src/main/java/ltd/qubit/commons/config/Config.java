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
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Type;

/**
 * {@link Config} 是一个 {@link Property} 的容器。
 *
 * <p>此 {@link Config} 接口不提供修改属性值的功能，因为 {@link Config} 对象旨在一次创建和写入，
 * 然后多次读取。
 *
 * <p>{@link Config} 接口的实现可以从 XML 文件、数据库或其他来源读取配置。
 *
 * @author 胡海星
 * @see DefaultConfig
 */
public interface Config extends CloneableEx<Config> {

  /**
   * 变量替换的正则表达式模式。
   */
  Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{[^\\}\\$\u0020]+\\}");

  /**
   * 变量替换的最大深度。
   */
  int MAX_SUBSTITUTION_DEPTH = 64;

  /**
   * 获取此配置的描述。
   *
   * @return
   *     此配置的描述，如果没有则返回 {@code null}。
   */
  String getDescription();

  /**
   * 获取具有指定名称的属性的描述。
   *
   * @param name
   *     属性的名称。
   * @return
   *     具有指定名称的属性的描述。请注意，返回 null 并不表示没有具有指定名称的属性；
   *     而是表示存在具有指定名称的属性，但其描述为 null。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性。
   */
  String getDescription(String name);

  /**
   * 测试此配置对象是否为空。
   *
   * @return 如果此配置对象为空，则返回 true；否则返回 false。
   */
  boolean isEmpty();

  /**
   * 获取此配置对象中的属性数。
   *
   * @return 此配置对象中的属性数。
   */
  int size();

  /**
   * 获取此配置中的属性集合。
   *
   * @return 此配置中的属性集合。
   */
  Collection<? extends Property> getProperties();

  /**
   * 获取此配置中属性的名称集。
   *
   * @return 此配置中属性的名称集。
   */
  Set<String> getNames();

  /**
   * 测试此配置中是否包含指定的属性名称。
   *
   * @param name
   *     属性名称。
   * @return 如果此配置中包含指定的属性名称，则返回 true；否则返回 false。
   */
  boolean contains(String name);

  /**
   * 获取具有指定名称的 {@link Property}。
   *
   * @param name
   *     要获取的属性的名称。
   * @return
   *     具有指定名称的 {@link Property}，如果不存在具有指定名称的属性，则返回 null。
   */
  Property get(String name);

  /**
   * 获取具有指定名称的属性的类型。
   *
   * @param name
   *     属性的名称。
   * @return 具有指定名称的属性的类型。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性。
   */
  Type getType(String name);

  /**
   * 测试具有指定名称的属性是否是 final 的。
   *
   * @param name
   *     属性的名称。
   * @return
   *     如果具有指定名称的属性是 final 的，则返回 true；否则返回 false。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性。
   */
  boolean isFinal(String name);

  /**
   * 获取具有指定名称的属性中的值的数量。
   *
   * @param name
   *     属性的名称。
   * @return
   *     具有指定名称的属性中的值的数量。请注意，如果此配置没有具有指定名称的属性，
   *     或者此配置具有具有指定名称的属性但该属性没有值，则该函数返回 0。
   */
  int getCount(String name);

  /**
   * 获取指定属性的值作为 {@code boolean} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code boolean}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code boolean}。
   */
  boolean getBoolean(String name);

  /**
   * 获取指定属性的值作为 {@code boolean} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code boolean}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code boolean}。
   */
  boolean getBoolean(String name, boolean defaultValue);

  /**
   * 获取指定属性的值作为 {@code boolean} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code boolean} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code boolean}。
   */
  boolean[] getBooleans(String name);

  /**
   * 获取指定属性的值作为 {@code boolean} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code boolean} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code boolean}。
   */
  boolean[] getBooleans(String name,
      @Nullable boolean[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code char} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code char}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code char}。
   */
  char getChar(String name);

  /**
   * 获取指定属性的值作为 {@code char} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code char}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code char}。
   */
  char getChar(String name, char defaultValue);

  /**
   * 获取指定属性的值作为 {@code char} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code char} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code char}。
   */
  char[] getChars(String name);

  /**
   * 获取指定属性的值作为 {@code char} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code char} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code char}。
   */
  char[] getChars(String name,
      @Nullable char[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code byte} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code byte}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code byte}。
   */
  byte getByte(String name);

  /**
   * 获取指定属性的值作为 {@code byte} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code byte}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code byte}。
   */
  byte getByte(String name, byte defaultValue);

  /**
   * 获取指定属性的值作为 {@code byte} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code byte} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code byte}。
   */
  byte[] getBytes(String name);

  /**
   * 获取指定属性的值作为 {@code byte} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code byte} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code byte}。
   */
  byte[] getBytes(String name, @Nullable byte[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code short} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code short}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code short}。
   */
  short getShort(String name);

  /**
   * 获取指定属性的值作为 {@code short} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code short}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code short}。
   */
  short getShort(String name, short defaultValue);

  /**
   * 获取指定属性的值作为 {@code short} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code short} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code short}。
   */
  short[] getShorts(String name);

  /**
   * 获取指定属性的值作为 {@code short} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code short} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code short}。
   */
  short[] getShorts(String name,
      @Nullable short[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code int} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code int}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code int}。
   */
  int getInt(String name);

  /**
   * 获取指定属性的值作为 {@code int} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code int}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code int}。
   */
  int getInt(String name, int defaultValue);

  /**
   * 获取指定属性的值作为 {@code int} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code int} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code int}。
   */
  int[] getInts(String name);

  /**
   * 获取指定属性的值作为 {@code int} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code int} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code int}。
   */
  int[] getInts(String name, @Nullable int[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code long} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code long}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code long}。
   */
  long getLong(String name);

  /**
   * 获取指定属性的值作为 {@code long} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code long}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code long}。
   */
  long getLong(String name, long defaultValue);

  /**
   * 获取指定属性的值作为 {@code long} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code long} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code long}。
   */
  long[] getLongs(String name);

  /**
   * 获取指定属性的值作为 {@code long} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code long} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code long}。
   */
  long[] getLongs(String name,
      @Nullable long[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code float} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code float}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code float}。
   */
  float getFloat(String name);

  /**
   * 获取指定属性的值作为 {@code float} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code float}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code float}。
   */
  float getFloat(String name, float defaultValue);

  /**
   * 获取指定属性的值作为 {@code float} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code float} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code float}。
   */
  float[] getFloats(String name);

  /**
   * 获取指定属性的值作为 {@code float} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code float} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code float}。
   */
  float[] getFloats(String name, @Nullable float[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code double} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code double}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code double}。
   */
  double getDouble(String name);

  /**
   * 获取指定属性的值作为 {@code double} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。
   * @return
   *     具有指定名称的属性的值，类型为 {@code double}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code double}。
   */
  double getDouble(String name, double defaultValue);

  /**
   * 获取指定属性的值作为 {@code double} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code double} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code double}。
   */
  double[] getDoubles(String name);

  /**
   * 获取指定属性的值作为 {@code double} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code double} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code double}。
   */
  double[] getDoubles(String name, @Nullable double[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code String} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code String}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@code String}。
   * @see #getString(String)
   */
  String getRawString(String name);

  /**
   * 获取指定属性的值作为 {@code String} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@code String}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code String}。
   * @see #getString(String, String)
   */
  String getRawString(String name, @Nullable String defaultValue);

  /**
   * 获取指定属性的值作为 {@code String} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code String} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@code String}。
   * @see #getStrings(String)
   */
  String[] getRawStrings(String name);

  /**
   * 获取指定属性的值作为 {@code String} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code String} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code String}。
   * @see #getStrings(String, String[])
   */
  String[] getRawStrings(String name, @Nullable String[] defaultValues);

  /**
   * 获取指定属性的值作为替换后的 {@code String} 值。
   *
   * <p>也就是说，如果指定名称的属性值包含可替换变量，形式为 {@code ${var_name}}，
   * 则该变量将被该名称的属性值或该名称的系统属性值替换。
   *
   * <p>替换可能是递归的，但替换的最大深度为 {@link #MAX_SUBSTITUTION_DEPTH}。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，作为替换后的 {@code String}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code String}。
   * @see #getRawString(String)
   */
  String getString(String name);

  /**
   * 获取指定属性的值作为替换后的 {@code String} 值。
   *
   * <p>也就是说，如果指定名称的属性值包含可替换变量，形式为 {@code ${var_name}}，
   * 则该变量将被该名称的属性值或该名称的系统属性值替换。
   *
   * <p>替换可能是递归的，但替换的最大深度为 {@link #MAX_SUBSTITUTION_DEPTH}。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，作为替换后的 {@code String}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。请注意，如果返回了 {@code defaultValue}，它也会被替换。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code String}。
   * @see #getRawString(String, String)
   */
  String getString(String name, @Nullable String defaultValue);

  /**
   * 获取指定属性的值作为替换后的 {@code String} 值数组。
   *
   * <p>也就是说，如果指定名称的属性值包含可替换变量，形式为 {@code ${var_name}}，
   * 则该变量将被该名称的属性值或该名称的系统属性值替换。
   *
   * <p>替换可能是递归的，但替换的最大深度为 {@link #MAX_SUBSTITUTION_DEPTH}。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，作为替换后的 {@code String} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code String}。
   * @see #getRawStrings(String)
   */
  String[] getStrings(String name);

  /**
   * 获取指定属性的值作为替换后的 {@code String} 值数组。
   *
   * <p>也就是说，如果指定名称的属性值包含可替换变量，形式为 {@code ${var_name}}，
   * 则该变量将被该名称的属性值或该名称的系统属性值替换。
   *
   * <p>替换可能是递归的，但替换的最大深度为 {@link #MAX_SUBSTITUTION_DEPTH}。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，作为替换后的 {@code String} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。请注意，如果返回了 {@code defaultValues}，它也会被替换。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code String}。
   * @see #getRawStrings(String, String[])
   */
  String[] getStrings(String name, @Nullable String[] defaultValues);

  /**
   * 在 {@code String} 中替换所有可替换变量，使用此配置中的属性值和系统属性值。
   *
   * <p>替换可能是递归的，但替换的最大深度为 {@link #MAX_SUBSTITUTION_DEPTH}。
   *
   * @param value
   *     要替换的 {@code String} 值。它可以为 null。
   * @return
   *     指定字符串的替换结果；如果指定的字符串为 null，则返回 null。
   */
  String substitute(@Nullable String value);

  /**
   * 获取指定属性的值作为 {@link BigDecimal} 对象。
   *
   * <p>如果指定的属性不存在或没有值，则抛出 {@link ConfigurationError}。
   * 如果指定的属性存在且有多个值，则返回第一个值（最早添加到此配置中的值）。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@link BigDecimal} 对象。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link BigDecimal}。
   */
  BigDecimal getBigDecimal(String name);

  /**
   * 获取指定属性的值作为 {@link BigDecimal} 对象。
   *
   * <p>如果指定的属性不存在或没有值，则返回指定的默认值。如果指定的属性存在且有多个值，
   * 则返回第一个值（最早添加到此配置中的值）。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@link BigDecimal}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link BigDecimal}。
   */
  BigDecimal getBigDecimal(String name, @Nullable BigDecimal defaultValue);

  /**
   * 获取指定属性的值作为 {@link BigDecimal} 值数组。如果指定的属性不存在或没有值，
   * 则抛出 {@link ConfigurationError}。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link BigDecimal} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link BigDecimal}。
   */
  BigDecimal[] getBigDecimals(String name);

  /**
   * 获取指定属性的值作为 {@link BigDecimal} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link BigDecimal} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link BigDecimal}。
   */
  BigDecimal[] getBigDecimals(String name,
      @Nullable BigDecimal[] defaultValues);

  /**
   * 获取指定属性的值作为 {@link BigInteger} 对象。
   *
   * <p>如果指定的属性不存在或没有值，则抛出 {@link ConfigurationError}。
   * 如果指定的属性存在且有多个值，则返回第一个值（最早添加到此配置中的值）。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@link BigInteger} 对象。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link BigInteger}。
   */
  BigInteger getBigInteger(String name);

  /**
   * 获取指定属性的值作为 {@link BigInteger} 对象。
   *
   * <p>如果指定的属性不存在或没有值，则返回指定的默认值。如果指定的属性存在且有多个值，
   * 则返回第一个值（最早添加到此配置中的值）。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@link BigInteger}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link BigInteger}。
   */
  BigInteger getBigInteger(String name, @Nullable BigInteger defaultValue);

  /**
   * 获取指定属性的值作为 {@link BigInteger} 值数组。如果指定的属性不存在或没有值，
   * 则抛出 {@link ConfigurationError}。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link BigInteger} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link BigInteger}。
   */
  BigInteger[] getBigIntegers(String name);

  /**
   * 获取指定属性的值作为 {@link BigInteger} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link BigInteger} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link BigInteger}。
   */
  BigInteger[] getBigIntegers(String name,
      @Nullable BigInteger[] defaultValues);

  /**
   * 获取指定属性的值作为 {@link LocalDate} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@link LocalDate}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@link LocalDate}。
   */
  LocalDate getDate(String name);

  /**
   * 获取指定属性的值作为 {@link LocalDate} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@link LocalDate}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link LocalDate}。
   */
  LocalDate getDate(String name, @Nullable LocalDate defaultValue);

  /**
   * 获取指定属性的值作为 {@link LocalDate} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link LocalDate} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@link LocalDate}。
   */
  LocalDate[] getDates(String name);

  /**
   * 获取指定属性的值作为 {@link LocalDate} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link LocalDate} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link LocalDate}。
   */
  LocalDate[] getDates(String name, @Nullable LocalDate[] defaultValues);

  /**
   * 获取指定属性的值作为 {@link LocalTime} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@link LocalTime}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@link LocalTime}。
   */
  LocalTime getTime(String name);

  /**
   * 获取指定属性的值作为 {@link LocalTime} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@link LocalTime}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link LocalTime}。
   */
  LocalTime getTime(String name, @Nullable LocalTime defaultValue);

  /**
   * 获取指定属性的值作为 {@link LocalTime} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link LocalTime} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@link LocalTime}。
   */
  LocalTime[] getTimes(String name);

  /**
   * 获取指定属性的值作为 {@link LocalTime} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link LocalTime} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link LocalTime}。
   */
  LocalTime[] getTimes(String name, @Nullable LocalTime[] defaultValues);

  /**
   * 获取指定属性的值作为 {@link LocalDateTime} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@link LocalDateTime}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@link LocalDateTime}。
   */
  LocalDateTime getDateTime(String name);

  /**
   * 获取指定属性的值作为 {@link LocalDateTime} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@link LocalDateTime}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link LocalDateTime}。
   */
  LocalDateTime getDateTime(String name, @Nullable LocalDateTime defaultValue);

  /**
   * 获取指定属性的值作为 {@link LocalDateTime} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link LocalDateTime} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@link LocalDateTime}。
   */
  LocalDateTime[] getDateTimes(String name);

  /**
   * 获取指定属性的值作为 {@link LocalDateTime} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@link LocalDateTime} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@link LocalDateTime}。
   */
  LocalDateTime[] getDateTimes(String name, @Nullable LocalDateTime[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code byte[]} 值。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code byte[]}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@code byte[]}。
   */
  byte[] getByteArray(String name);

  /**
   * 获取指定属性的值作为 {@code byte[]} 值。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@code byte[]}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code byte[]}。
   */
  byte[] getByteArray(String name, @Nullable byte[] defaultValue);

  /**
   * 获取指定属性的值作为 {@code byte[]} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code byte[]} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@code byte[]}。
   */
  byte[][] getByteArrays(String name);

  /**
   * 获取指定属性的值作为 {@code byte[]} 值数组。
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code byte[]} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code byte[]}。
   */
  byte[][] getByteArrays(String name, @Nullable byte[][] defaultValues);

  /**
   * 获取指定属性的值作为枚举对象。
   * <p>
   * 指定属性的值必须存储为字符串，并且必须是枚举对象的名称。
   *
   * @param <E>
   *     枚举类型。
   * @param name
   *     指定属性的名称。
   * @param enumType
   *     枚举类的类对象。
   * @return
   *     指定属性的值，作为枚举对象。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code String}；或者指定属性的 {@code String} 值无法转换为指定的枚举对象。
   */
  <E extends Enum<E>> E getEnum(String name, Class<E> enumType);

  /**
   * 获取指定属性的值作为枚举对象。
   * <p>
   * 指定属性的值必须存储为字符串，并且必须是枚举对象的名称。
   *
   * @param <E>
   *     枚举类型。
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认枚举数。它可以为 null。
   * @param enumType
   *     枚举类的类对象。
   * @return
   *     指定属性的值，作为枚举对象，可能为 {@code null}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code String}；或者指定属性的 {@code String} 值无法转换为指定的枚举对象。
   */
  @Nullable
  <E extends Enum<E>> E getEnum(String name, @Nullable E defaultValue, Class<E> enumType);

//   /**
//    * 获取指定属性的值作为枚举对象数组。
//    * <p>
//    * 指定属性的值必须存储为字符串，并且必须是枚举对象的名称数组。
//    *
//    * @param <E>
//    *     枚举类型。
//    * @param name
//    *     指定属性的名称。
//    * @param enumType
//    *     枚举类的类对象。
//    * @return
//    *     指定属性的值，作为枚举对象数组，可能为 {@code null}。
//    * @throws ConfigurationError
//    *     如果指定属性的类型不是 {@code String}；或者指定属性的 {@code String} 值无法转换为指定的枚举对象。
//    */
//   <E extends Enum<E>> E[] getEnums(String name, Class<E> enumType);

//   /**
//    * 获取指定属性的值作为枚举对象数组。
//    * <p>
//    * 指定属性的值必须存储为字符串，并且必须是枚举对象的名称数组。
//    *
//    * @param <E>
//    *     枚举类型。
//    * @param name
//    *     指定属性的名称。
//    * @param defaultValues
//    *     在没有此类属性或属性没有值的情况下返回的默认枚举数。它可以为 null。
//    * @param enumType
//    *     枚举类的类对象。
//    * @return
//    *     指定属性的值，作为枚举对象数组，或者在没有此类属性或属性没有值的情况下返回默认值，可能为 {@code null}。
//    * @throws ConfigurationError
//    *     如果指定属性的类型不是 {@code String}；或者指定属性的 {@code String} 值无法转换为指定的枚举对象。
//    */
//   @Nullable
//   <E extends Enum<E>> E[] getEnums(String name, @Nullable E[] defaultValues, Class<E> enumType);

  /**
   * 获取指定属性的值作为 {@code Class} 值。
   *
   * <p>注意：指定属性的类型必须是 {@link Type#CLASS} 或 {@link Type#STRING}。</p>
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值，类型为 {@code Class}。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@code Class}。
   */
  Class<?> getClass(String name);

  /**
   * 获取指定属性的值作为 {@code Class} 值。
   *
   * <p>注意：指定属性的类型必须是 {@link Type#CLASS} 或 {@link Type#STRING}。</p>
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@code Class}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class} 也不是 {@code String}。
   */
  Class<?> getClass(String name, @Nullable Class<?> defaultValue);

  /**
   * 获取指定属性的值作为 {@code Class} 值。
   *
   * <p>注意：指定属性的类型必须是 {@link Type#CLASS} 或 {@link Type#STRING}。</p>
   *
   * @param name
   *     指定属性的名称。
   * @param defaultClassName
   *     在没有此类属性或属性没有值的情况下返回的默认类的名称。它可以为 null。
   * @return
   *     具有指定名称的属性的值，类型为 {@code Class}；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class} 也不是 {@code String}，或者不存在此类属性或属性没有值，
   *     但无法创建名称为 {@code defaultClass} 的类。
   */
  Class<?> getClass(String name, @Nullable String defaultClassName);

  /**
   * 获取指定属性的值作为 {@code Class} 值数组。
   *
   * <p>注意：指定属性的类型必须是 {@link Type#CLASS} 或 {@link Type#STRING}。</p>
   *
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code Class} 数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是
   *     {@code Class}。
   */
  Class<?>[] getClasses(String name);

  /**
   * 获取指定属性的值作为 {@code Class} 值数组。
   *
   * <p>注意：指定属性的类型必须是 {@link Type#CLASS} 或 {@link Type#STRING}。</p>
   *
   * @param name
   *     指定属性的名称。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code Class} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}。
   */
  Class<?>[] getClasses(String name, @Nullable Class<?>[] defaultValues);

  /**
   * 获取指定属性的值作为 {@code Class} 值数组。
   *
   * <p>注意：指定属性的类型必须是 {@link Type#CLASS} 或 {@link Type#STRING}。</p>
   *
   * @param name
   *     指定属性的名称。
   * @param defaultClassNames
   *     在没有此类属性或属性没有值的情况下返回的默认类的名称。它可以为 null。
   * @return
   *     具有指定名称的属性的值数组，类型为 {@code Class} 数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}，或者不存在此类属性或属性没有值，
   *     但无法创建名称为 {@code defaultClass} 的类。
   */
  Class<?>[] getClasses(String name, @Nullable String[] defaultClassNames);

  /**
   * 获取指定属性的值作为 {@code Class} 值，然后创建该类的实例并返回它。
   *
   * @param <T>
   *     要创建和返回的对象的类型。
   * @param name
   *     指定属性的名称。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性；或者指定的属性具有值列表；或者指定属性的类型不是 {@code Class}；
   *     或者无法创建 {@code Class} 的实例。
   */
  <T> T getInstance(String name);

  /**
   * 获取指定属性的值作为 {@code Class} 值，然后创建该类的实例并返回它。
   *
   * @param <T>
   *     要创建和返回的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param defaultClass
   *     在没有此类属性或属性没有值的情况下返回的默认值的类。它可以为 null。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultClass} 的实例；如果不存在此类属性或属性没有值且默认类为 null，则返回 null。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}；或者无法创建指定属性的 {@code Class} 值的实例。
   */
  <T> T getInstance(String name, @Nullable Class<?> defaultClass);

  /**
   * 获取指定属性的值作为 {@code Class} 值，然后创建该类的实例并返回它。
   *
   * @param <T>
   *     要创建和返回的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param defaultClassName
   *     在没有此类属性或属性没有值的情况下返回的默认值的类名。它可以为 null。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例；如果不存在此类属性或属性没有值，
   *     则返回具有 {@code defaultClassName} 的类的实例；如果不存在此类属性或属性没有值且默认类名为 null，
   *     则返回 null。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}；或者无法创建指定属性的 {@code Class} 值的实例。
   */
  <T> T getInstance(String name, @Nullable String defaultClassName);

  /**
   * 获取指定属性的值作为 {@code Class} 值，然后创建该类的实例并返回它。
   *
   * @param <T>
   *     要创建和返回的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param defaultValue
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValue}；如果不存在此类属性或属性没有值且默认类名为 null，
   *     则返回 null。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}；或者无法创建指定属性的 {@code Class} 值的实例。
   */
  <T> T getInstance(String name, @Nullable T defaultValue);

  /**
   * 获取指定属性的值作为 {@code Class} 数组，然后创建数组中每个类的实例并返回对象数组。
   *
   * @param <T>
   *     要创建的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param clazz
   *     类型 T 的类对象。创建类型 T 的数组需要它。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例数组。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性；或者指定的属性具有值列表；或者指定属性的类型不是 {@code Class}；
   *     或者无法创建 {@code Class} 的实例。
   */
  <T> T[] getInstances(String name, Class<?> clazz);

  /**
   * 获取指定属性的值作为 {@code Class} 数组，然后创建数组中每个类的实例并返回对象数组。
   *
   * @param <T>
   *     要创建的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param clazz
   *     类型 T 的类对象。创建类型 T 的数组需要它。
   * @param defaultClasses
   *     在没有此类属性或属性没有值的情况下返回的默认值的类。它可以为 null。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultClass} 的实例。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}；或者无法创建指定属性的 {@code Class} 值的实例。
   */
  <T> T[] getInstances(String name, Class<?> clazz, @Nullable Class<?>[] defaultClasses);

  /**
   * 获取指定属性的值作为 {@code Class} 数组，然后创建数组中每个类的实例并返回对象数组。
   *
   * @param <T>
   *     要创建的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param clazz
   *     类型 T 的类对象。创建类型 T 的数组需要它。
   * @param defaultClassNames
   *     在没有此类属性或属性没有值的情况下返回的默认值的类名。它可以为 null。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例数组；如果不存在此类属性或属性没有值，
   *     则返回具有 {@code defaultClassName} 的类的实例。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}；或者无法创建指定属性的 {@code Class} 值的实例。
   */
  <T> T[] getInstances(String name, Class<?> clazz, @Nullable String[] defaultClassNames);

  /**
   * 获取指定属性的值作为 {@code Class} 数组，然后创建数组中每个类的实例并返回对象数组。
   *
   * @param <T>
   *     要创建的对象的类型。
   * @param name
   *     指定属性的名称。
   * @param clazz
   *     类型 T 的类对象。创建类型 T 的数组需要它。
   * @param defaultValues
   *     在没有此类属性或属性没有值的情况下返回的默认值。它可以为 null。
   * @return
   *     具有指定名称的属性的 {@code Class} 值的实例数组；如果不存在此类属性或属性没有值，
   *     则返回 {@code defaultValues}。
   * @throws ConfigurationError
   *     如果指定属性的类型不是 {@code Class}；或者无法创建指定属性的 {@code Class} 值的实例。
   */
  <T> T[] getInstances(String name, Class<?> clazz, @Nullable T[] defaultValues);
}