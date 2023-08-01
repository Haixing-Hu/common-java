////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/**
 * A {@link Config} is a container of {@link Property}s.
 *
 * <p>The {@link Config} interface does not provides functions to modify the
 * property values, since a {@link Config} object is intended to be create and
 * write once, then read multiple times.
 *
 * <p>The implementation of {@link Config} interface may read configurations
 * from an XML file, a database, or other sources.
 *
 * @author Haixing Hu
 * @see DefaultConfig
 */
public interface Config extends CloneableEx<Config> {

  /**
   * The regular expression pattern of variables substitution.
   */
  Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{[^\\}\\$\u0020]+\\}");

  /**
   * The maximum depth of variables substitutions.
   */
  int MAX_SUBSTITUTION_DEPTH = 64;

  /**
   * Gets the description of this configuration.
   *
   * @return the description of this configuration, or {@code null} if none.
   */
  String getDescription();

  /**
   * Gets the description of the property with the specified name.
   *
   * @param name
   *     the name of a property.
   * @return the description of the property with the specified name. Note that
   *     returning a null does not indicates there is no property with the
   *     specified name; instead, it indicates that there is a property with the
   *     specified name but has a null description.
   * @throws ConfigurationError
   *     if no property with the specified name.
   */
  String getDescription(String name);

  /**
   * Tests whether this Config object is empty.
   *
   * @return true if this Config object is empty; false otherwise.
   */
  boolean isEmpty();

  /**
   * Gets the number of properties in this Config object.
   *
   * @return the number of properties in this Config object.
   */
  int size();

  /**
   * Gets the collection of properties in this configuration.
   *
   * @return the collection of properties in this configuration.
   */
  Collection<? extends Property> getProperties();

  /**
   * Gets the set of names of properties in this configuration.
   *
   * @return the set of names of properties in this configuration.
   */
  Set<String> getNames();

  /**
   * Tests whether a specified property name is contained in this
   * configuration.
   *
   * @param name
   *     a property name.
   * @return true if the specified property name is contained in this
   *     configuration; false otherwise.
   */
  boolean contains(String name);

  /**
   * Gets the {@link Property} with the specified name.
   *
   * @param name
   *     the name of the property to be get.
   * @return the {@link Property} with the specified name, or null if no
   *     property with the specified name.
   */
  Property get(String name);

  /**
   * Gets the type of the property with the specified name.
   *
   * @param name
   *     the name of a property.
   * @return the type of the property with the specified name.
   * @throws ConfigurationError
   *     if no property with the specified name.
   */
  Type getType(String name);

  /**
   * Tests whether the property with the specified name is final.
   *
   * @param name
   *     the name of a property.
   * @return true if the property with the specified name is final; false
   *     otherwise.
   * @throws ConfigurationError
   *     if no property with the specified name.
   */
  boolean isFinal(String name);

  /**
   * Gets the number of values in the property with the specified name.
   *
   * @param name
   *     the name of a property.
   * @return the number of values in the property with the specified name. Note
   *     that if this configuration does not have the property with the
   *     specified name, or this configuration has a property with the specified
   *     name but the property has no value, the function returns 0.
   */
  int getCount(String name);

  /**
   * Gets the value of the specified property as a {@code boolean} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     boolean}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code boolean}.
   */
  boolean getBoolean(String name);

  /**
   * Gets the value of the specified property as a {@code boolean} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default values returned in case of there is no such property or the
   *     property has no value.
   * @return the value of the property with the specified name as a {@code
   *     boolean}; or the {@code defaultValue} if there is no such property or
   *     the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code boolean} .
   */
  boolean getBoolean(String name, boolean defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code boolean}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code boolean} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code boolean}.
   */
  boolean[] getBooleans(String name);

  /**
   * Gets the values of the specified property as a array of {@code boolean}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code boolean} array; or the {@code defaultValues} if there is no
   *     such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code boolean} .
   */
  boolean[] getBooleans(String name,
      @Nullable boolean[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code char} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     char}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code char}.
   */
  char getChar(String name);

  /**
   * Gets the value of the specified property as a {@code char} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     char}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code char}.
   */
  char getChar(String name, char defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code char}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code char} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code char}.
   */
  char[] getChars(String name);

  /**
   * Gets the values of the specified property as a array of {@code char}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code char} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code char}.
   */
  char[] getChars(String name,
      @Nullable char[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code byte} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     byte}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code byte}.
   */
  byte getByte(String name);

  /**
   * Gets the value of the specified property as a {@code byte} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     byte}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code byte}.
   */
  byte getByte(String name, byte defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code byte}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code byte} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code byte}.
   */
  byte[] getBytes(String name);

  /**
   * Gets the values of the specified property as a array of {@code byte}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code byte} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code byte}.
   */
  byte[] getBytes(String name, @Nullable byte[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code short} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     short}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code short}.
   */
  short getShort(String name);

  /**
   * Gets the value of the specified property as a {@code short} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     short}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code short}.
   */
  short getShort(String name, short defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code short}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code short} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code short}.
   */
  short[] getShorts(String name);

  /**
   * Gets the values of the specified property as a array of {@code short}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code short} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code short}.
   */
  short[] getShorts(String name,
      @Nullable short[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code int} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code int}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code int}.
   */
  int getInt(String name);

  /**
   * Gets the value of the specified property as a {@code int} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code int};
   *     or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code int}.
   */
  int getInt(String name, int defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code int}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code int} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code int}.
   */
  int[] getInts(String name);

  /**
   * Gets the values of the specified property as a array of {@code int}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code int} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code int}.
   */
  int[] getInts(String name, @Nullable int[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code long} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     long}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code long}.
   */
  long getLong(String name);

  /**
   * Gets the value of the specified property as a {@code long} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     long}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code long}.
   */
  long getLong(String name, long defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code long}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code long} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code long}.
   */
  long[] getLongs(String name);

  /**
   * Gets the values of the specified property as a array of {@code long}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code long} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code long}.
   */
  long[] getLongs(String name,
      @Nullable long[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code float} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     float}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code float}.
   */
  float getFloat(String name);

  /**
   * Gets the value of the specified property as a {@code float} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     float}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code float}.
   */
  float getFloat(String name, float defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code float}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code float} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code float}.
   */
  float[] getFloats(String name);

  /**
   * Gets the values of the specified property as a array of {@code float}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code float} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code float}.
   */
  float[] getFloats(String name, @Nullable float[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code double} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     double}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code double}.
   */
  double getDouble(String name);

  /**
   * Gets the value of the specified property as a {@code double} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     double}; or the {@code defaultValue} if there is no such property or
   *     the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code double}.
   */
  double getDouble(String name, double defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code double}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code double} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code double}.
   */
  double[] getDoubles(String name);

  /**
   * Gets the values of the specified property as a array of {@code double}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code double} array; or the {@code defaultValues} if there is no
   *     such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code double}.
   */
  double[] getDoubles(String name, @Nullable double[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code String} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     String}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code String}.
   * @see #getString(String)
   */
  String getRawString(String name);

  /**
   * Gets the value of the specified property as a {@code String} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     String}; or the {@code defaultValue} if there is no such property or
   *     the property has no value. Note that if the {@code defaultValue} is
   *     returned, it is also substituted.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code String}.
   * @see #getString(String, String)
   */
  String getRawString(String name, @Nullable String defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code String}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code String} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code String}.
   * @see #getStrings(String)
   */
  String[] getRawStrings(String name);

  /**
   * Gets the values of the specified property as a array of {@code String}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code String} array; or the {@code defaultValues} if there is no
   *     such property or the property has no value. Note that if the {@code
   *     defaultValues} is returned, it is also substituted.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code String}.
   * @see #getStrings(String, String[])
   */
  String[] getRawStrings(String name, @Nullable String[] defaultValues);

  /**
   * Gets the value of the specified property as a substituted {@code String}
   * value.
   *
   * <p>That is, if the property value of the specified name contains
   * substitutable variables, in the form of {@code ${var_name}}, the variable
   * will be substituted by the property value of that name or the system
   * property value of that name.
   *
   * <p>The substitution may be recursive, but the maximum depth of the
   * substitution is {@link #MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a substituted
   *     {@code String}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code String}.
   * @see #getRawString(String)
   */
  String getString(String name);

  /**
   * Gets the value of the specified property as a substituted {@code String}
   * value.
   *
   * <p>That is, if the property value of the specified name contains
   * substitutable variables, in the form of {@code ${var_name}}, the variable
   * will be substituted by the property value of that name or the system
   * property value of that name.
   *
   * <p>The substitution may be recursive, but the maximum depth of the
   * substitution is {@link #MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a substituted
   *     {@code String}; or the {@code defaultValue} if there is no such
   *     property or the property has no value. Note that if the {@code
   *     defaultValue} is returned, it is also substituted.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code String}.
   * @see #getRawString(String, String)
   */
  String getString(String name,
      @Nullable String defaultValue);

  /**
   * Gets the values of the specified property as a array of substituted {@code
   * String} values.
   *
   * <p>That is, if the property value of the specified name contains
   * substitutable variables, in the form of {@code ${var_name}}, the variable
   * will be substituted by the property value of that name or the system
   * property value of that name.
   *
   * <p>The substitution may be recursive, but the maximum depth of the
   * substitution is {@link #MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a substituted {@code String} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code String}.
   * @see #getRawStrings(String)
   */
  String[] getStrings(String name);

  /**
   * Gets the values of the specified property as a array of substituted {@code
   * String} values.
   *
   * <p>That is, if the property value of the specified name contains
   * substitutable variables, in the form of {@code ${var_name}}, the variable
   * will be substituted by the property value of that name or the system
   * property value of that name.
   *
   * <p>The substitution may be recursive, but the maximum depth of the
   * substitution is {@link #MAX_SUBSTITUTION_DEPTH}.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a substituted {@code String} array; or the {@code defaultValues} if
   *     there is no such property or the property has no value. Note that if
   *     the {@code defaultValues} is returned, it is also substituted.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code String}.
   * @see #getRawStrings(String, String[])
   */
  String[] getStrings(String name,
      @Nullable String[] defaultValues);

  /**
   * Substitutes all substitutable variables in a {@code String}, using the
   * property values in this configuration and the system property values.
   *
   * <p>The substitution may be recursive, but the maximum depth of the
   * substitution is {@link #MAX_SUBSTITUTION_DEPTH}.
   *
   * @param value
   *     the {@code String} value to be substituted. It could be null.
   * @return the substituted result of the specified string; or null if the
   *     specified string is null.
   */
  String substitute(@Nullable String value);

  /**
   * Gets the value of the specified property as a {@code BigDecimal} object.
   *
   * <p>If the specified property does not exists or has no value, a {@code
   * ConfigurationError} is thrown. If the specified property exists and has
   * more than one value, the first value (earliest added into this
   * configuration) is returned.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     BigDecimal} object.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code BigDecimal}.
   */
  BigDecimal getBigDecimal(String name);

  /**
   * Gets the value of the specified property as a {@code BigDecimal} object.
   *
   * <p>If the specified property does not exists or has no value, the specified
   * default value is returned. If the specified property exists and has more
   * than one value, the first value (earliest added into this configuration) is
   * returned.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     BigDecimal}; or the {@code defaultValue} if there is no such property
   *     or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code BigDecimal}.
   */
  BigDecimal getBigDecimal(String name,
      @Nullable BigDecimal defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code BigDecimal}
   * values. If the specified property does not exists or has no value, a {@code
   * ConfigurationError} is thrown.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code BigDecimal} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the property has no
   *     value, or the type of the property is not {@code BigDecimal}.
   */
  BigDecimal[] getBigDecimals(String name);

  /**
   * Gets the values of the specified property as a array of {@code BigDecimal}
   * values. If the specified property does not exists or has no value, a {@code
   * ConfigurationError} is thrown.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code BigDecimal} array; or the {@code defaultValues} if there is no
   *     such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code BigDecimal}.
   */
  BigDecimal[] getBigDecimals(String name,
      @Nullable BigDecimal[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code BigInteger} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     BigInteger}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code BigInteger}.
   */
  BigInteger getBigInteger(String name);

  /**
   * Gets the value of the specified property as a {@code BigInteger} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     BigInteger}; or the {@code defaultValue} if there is no such property
   *     or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code BigInteger}.
   */
  BigInteger getBigInteger(String name,
      @Nullable BigInteger defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code BigInteger}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code BigInteger} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code BigInteger}.
   */
  BigInteger[] getBigIntegers(String name);

  /**
   * Gets the values of the specified property as a array of {@code BigInteger}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code BigInteger} array; or the {@code defaultValues} if there is no
   *     such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code BigInteger}.
   */
  BigInteger[] getBigIntegers(String name,
      @Nullable BigInteger[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code Date} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     Date}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Date}.
   */
  Date getDate(String name);

  /**
   * Gets the value of the specified property as a {@code Date} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     Date}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Date}.
   */
  Date getDate(String name, @Nullable Date defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code Date}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code Date} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Date}.
   */
  Date[] getDates(String name);

  /**
   * Gets the values of the specified property as a array of {@code Date}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code Date} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Date}.
   */
  Date[] getDates(String name,
      @Nullable Date[] defaultValues);

  /**
   * Gets the value of the specified property as a {@code byte[]} value.
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     byte[]}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code byte[]}.
   */
  byte[] getByteArray(String name);

  /**
   * Gets the value of the specified property as a {@code byte[]} value.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     byte[]}; or the {@code defaultValue} if there is no such property or
   *     the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code byte[]}.
   */
  byte[] getByteArray(String name,
      @Nullable byte[] defaultValue);

  /**
   * Gets the values of the specified property as a array of {@code byte[]}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code byte[]} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code byte[]}.
   */
  byte[][] getByteArrays(String name);

  /**
   * Gets the values of the specified property as a array of {@code byte[]}
   * values.
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code byte[]} array; or the {@code defaultValues} if there is no
   *     such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code byte[]}.
   */
  byte[][] getByteArrays(String name,
      @Nullable byte[][] defaultValues);

  /**
   * Gets the value of the specified property as a {@code Class} value.
   *
   * <p>Note: the type of the specified property must be either {@link
   * Type#CLASS} or {@link Type#STRING}.</p>
   *
   * @param name
   *     the name of the specified property.
   * @return the value of the property with the specified name as a {@code
   *     Class}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Class}.
   */
  Class<?> getClass(String name);

  /**
   * Gets the value of the specified property as a {@code Class} value.
   *
   * <p>Note: the type of the specified property must be either {@link
   * Type#CLASS} or {@link Type#STRING}.</p>
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     Class}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class} nor {@code
   *     String}.
   */
  Class<?> getClass(String name, @Nullable Class<?> defaultValue);

  /**
   * Gets the value of the specified property as a {@code Class} value.
   *
   * <p>Note: the type of the specified property must be either {@link
   * Type#CLASS} or {@link Type#STRING}.</p>
   *
   * @param name
   *     the name of the specified property.
   * @param defaultClassName
   *     the name of default class returned in case of there is no such property
   *     or the property has no value. It could be null.
   * @return the value of the property with the specified name as a {@code
   *     Class}; or the {@code defaultValue} if there is no such property or the
   *     property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class} nor {@code
   *     String}, or there is no such property or the property has no value, but
   *     the class with the name of {@code defaultClass} can not be created.
   */
  Class<?> getClass(String name, @Nullable String defaultClassName);

  /**
   * Gets the values of the specified property as a array of {@code Class}
   * values.
   *
   * <p>Note: the type of the specified property must be either {@link
   * Type#CLASS} or {@link Type#STRING}.</p>
   *
   * @param name
   *     the name of the specified property.
   * @return the array of the values of the property with the specified name as
   *     a {@code Class} array.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Class}.
   */
  Class<?>[] getClasses(String name);

  /**
   * Gets the values of the specified property as a array of {@code Class}
   * values.
   *
   * <p>Note: the type of the specified property must be either {@link
   * Type#CLASS} or {@link Type#STRING}.</p>
   *
   * @param name
   *     the name of the specified property.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code Class} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}.
   */
  Class<?>[] getClasses(String name, @Nullable Class<?>[] defaultValues);

  /**
   * Gets the values of the specified property as a array of {@code Class}
   * values.
   *
   * <p>Note: the type of the specified property must be either {@link
   * Type#CLASS} or {@link Type#STRING}.</p>
   *
   * @param name
   *     the name of the specified property.
   * @param defaultClassNames
   *     the names of default classes returned in case of there is no such
   *     property or the property has no value. It could be null.
   * @return the array of the values of the property with the specified name as
   *     a {@code Class} array; or the {@code defaultValues} if there is no such
   *     property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}, or there is
   *     no such property or the property has no value, but the class with the
   *     name of {@code defaultClass} can not be created.
   */
  Class<?>[] getClasses(String name, @Nullable String[] defaultClassNames);

  /**
   * Gets the value of the specified property as a {@code Class} value, then
   * creates a instance of that class and returns it.
   *
   * @param <T>
   *     the type of the objects to be created and returned.
   * @param name
   *     the name of the specified property.
   * @return an instance of the {@code Class} value of the property with the
   *     specified name.
   * @throws ConfigurationError
   *     if there is no property with the specified name; or the specified
   *     property has a list of values; or the type of the specified property is
   *     not {@code Class}; or the instance of the {@code Class} can not be
   *     created.
   */
  <T> T getInstance(String name);

  /**
   * Gets the value of the specified property as a {@code Class} value, then
   * creates a instance of that class and returns it.
   *
   * @param <T>
   *     the type of the objects to be created and returned.
   * @param name
   *     the name of the specified property.
   * @param defaultClass
   *     the class of the default value returned in case of there is no such
   *     property or the property has no value. It could be null.
   * @return an instance of the {@code Class} value of the property with the
   *     specified name; or a instance of the {@code defaultClass} if there is
   *     no such property or the property has no value; or null if there is no
   *     such property or the property has no value and the default class is
   *     null.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}; or the
   *     instance of the {@code Class} value of the specified property can not
   *     be created.
   */
  <T> T getInstance(String name, @Nullable Class<?> defaultClass);

  /**
   * Gets the value of the specified property as a {@code Class} value, then
   * creates a instance of that class and returns it.
   *
   * @param <T>
   *     the type of the objects to be created and returned.
   * @param name
   *     the name of the specified property.
   * @param defaultClassName
   *     the class name of the default value returned in case of there is no
   *     such property or the property has no value. It could be null.
   * @return an instance of the {@code Class} value of the property with the
   *     specified name; or a instance of the class with the {@code
   *     defaultClassName} if there is no such property or the property has no
   *     value; or null if there is no such property or the property has no
   *     value and the default class name is null.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}; or the
   *     instance of the {@code Class} value of the specified property can not
   *     be created.
   */
  <T> T getInstance(String name, @Nullable String defaultClassName);

  /**
   * Gets the value of the specified property as a {@code Class} value, then
   * creates a instance of that class and returns it.
   *
   * @param <T>
   *     the type of the objects to be created and returned.
   * @param name
   *     the name of the specified property.
   * @param defaultValue
   *     the default value returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return an instance of the {@code Class} value of the property with the
   *     specified name; or the {@code defaultValue} if there is no such
   *     property or the property has no value; or null if there is no such
   *     property or the property has no value and the default class name is
   *     null.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}; or the
   *     instance of the {@code Class} value of the specified property can not
   *     be created.
   */
  <T> T getInstance(String name, @Nullable T defaultValue);

  /**
   * Gets the values of the specified property as a {@code Class} array, then
   * creates a instance of each class in the array and returns the object
   * array.
   *
   * @param <T>
   *     the type of the objects to be created.
   * @param name
   *     the name of the specified property.
   * @param clazz
   *     the class object of the type T. It is required to create the array of
   *     type T.
   * @return an array of instances of the {@code Class} values of the property
   *     with the specified name.
   * @throws ConfigurationError
   *     if there is no property with the specified name; or the specified
   *     property has a list of values; or the type of the specified property is
   *     not {@code Class}; or the instance of the {@code Class} can not be
   *     created.
   */
  <T> T[] getInstances(String name, Class<?> clazz);

  /**
   * Gets the values of the specified property as a {@code Class} array, then
   * creates a instance of each class in the array and returns the object
   * array.
   *
   * @param <T>
   *     the type of the objects to be created.
   * @param name
   *     the name of the specified property.
   * @param clazz
   *     the class object of the type T. It is required to create the array of
   *     type T.
   * @param defaultClasses
   *     the classes of the default values returned in case of there is no such
   *     property or the property has no value. It could be null.
   * @return an array of instances of the {@code Class} values of the property
   *     with the specified name; or a instance of the {@code defaultClass} if
   *     there is no such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}; or the
   *     instance of the {@code Class} value of the specified property can not
   *     be created.
   */
  <T> T[] getInstances(String name, Class<?> clazz, @Nullable Class<?>[] defaultClasses);

  /**
   * Gets the values of the specified property as a {@code Class} array, then
   * creates a instance of each class in the array and returns the object
   * array.
   *
   * @param <T>
   *     the type of the objects to be created.
   * @param name
   *     the name of the specified property.
   * @param clazz
   *     the class object of the type T. It is required to create the array of
   *     type T.
   * @param defaultClassNames
   *     the class names of the default values returned in case of there is no
   *     such property or the property has no value. It could be null.
   * @return an array of instances of the {@code Class} values of the property
   *     with the specified name; or a instance of the class with the {@code
   *     defaultClassName} if there is no such property or the property has no
   *     value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}; or the
   *     instance of the {@code Class} value of the specified property can not
   *     be created.
   */
  <T> T[] getInstances(String name, Class<?> clazz, @Nullable String[] defaultClassNames);

  /**
   * Gets the values of the specified property as a {@code Class} array, then
   * creates a instance of each class in the array and returns the object
   * array.
   *
   * @param <T>
   *     the type of the objects to be created.
   * @param name
   *     the name of the specified property.
   * @param clazz
   *     the class object of the type T. It is required to create the array of
   *     type T.
   * @param defaultValues
   *     the default values returned in case of there is no such property or the
   *     property has no value. It could be null.
   * @return an array of instances of the {@code Class} values of the property
   *     with the specified name; or the {@code defaultValues} if there is no
   *     such property or the property has no value.
   * @throws ConfigurationError
   *     if the type of the specified property is not {@code Class}; or the
   *     instance of the {@code Class} value of the specified property can not
   *     be created.
   */
  <T> T[] getInstances(String name, Class<?> clazz, @Nullable T[] defaultValues);
}
