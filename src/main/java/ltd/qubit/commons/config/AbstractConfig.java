////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.config.error.PropertyHasNoValueError;
import ltd.qubit.commons.config.error.PropertyNotExistError;
import ltd.qubit.commons.error.TypeConvertException;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.text.Replacer;

/**
 * An {@link AbstractConfig} is an abstract base class for classes implementing
 * the {@link Config} interface.
 *
 * @author Haixing Hu
 */
public abstract class AbstractConfig implements Config, Serializable {

  @Serial
  private static final long serialVersionUID = -7405936020731523154L;

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public String getDescription(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getDescription();
  }

  @Override
  public Type getType(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getType();
  }

  @Override
  public boolean isFinal(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.isFinal();
  }

  @Override
  public int getCount(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      return 0;
    } else {
      return prop.getCount();
    }
  }

  @Override
  public boolean getBoolean(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsBoolean();
  }

  @Override
  public boolean getBoolean(final String name, final boolean defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsBoolean();
  }

  @Override
  public boolean[] getBooleans(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsBoolean();
  }

  @Override
  public boolean[] getBooleans(final String name,
      @Nullable final boolean[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsBoolean();
  }

  @Override
  public char getChar(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsChar();
  }

  @Override
  public char getChar(final String name, final char defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsChar();
  }

  @Override
  public char[] getChars(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsChar();
  }

  @Override
  public char[] getChars(final String name,
      @Nullable final char[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsChar();
  }

  @Override
  public byte getByte(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsByte();
  }

  @Override
  public byte getByte(final String name, final byte defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsByte();
  }

  @Override
  public byte[] getBytes(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsByte();
  }

  @Override
  public byte[] getBytes(final String name,
      @Nullable final byte[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsByte();
  }

  @Override
  public short getShort(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsShort();
  }

  @Override
  public short getShort(final String name, final short defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsShort();
  }

  @Override
  public short[] getShorts(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsShort();
  }

  @Override
  public short[] getShorts(final String name,
      @Nullable final short[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsShort();
  }

  @Override
  public int getInt(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsInt();
  }

  @Override
  public int getInt(final String name, final int defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsInt();
  }

  @Override
  public int[] getInts(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsInt();
  }

  @Override
  public int[] getInts(final String name, @Nullable final int[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsInt();
  }

  @Override
  public long getLong(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsLong();
  }

  @Override
  public long getLong(final String name, final long defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsLong();
  }

  @Override
  public long[] getLongs(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsLong();
  }

  @Override
  public long[] getLongs(final String name,
      @Nullable final long[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsLong();
  }

  @Override
  public float getFloat(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsFloat();
  }

  @Override
  public float getFloat(final String name, final float defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsFloat();
  }

  @Override
  public float[] getFloats(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsFloat();
  }

  @Override
  public float[] getFloats(final String name,
      @Nullable final float[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsFloat();
  }

  @Override
  public double getDouble(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsDouble();
  }

  @Override
  public double getDouble(final String name, final double defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsDouble();
  }

  @Override
  public double[] getDoubles(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsDouble();
  }

  @Override
  public double[] getDoubles(final String name,
      @Nullable final double[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsDouble();
  }

  @Override
  public String getRawString(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsString();
  }

  @Override
  public String getRawString(final String name,
      @Nullable final String defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsString();
  }

  @Override
  public String[] getRawStrings(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsString();
  }

  @Override
  public String[] getRawStrings(final String name,
      @Nullable final String[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsString();
  }

  @Override
  public String getString(final String name) {
    final String result = getRawString(name);
    return substitute(result);
  }

  @Override
  public String getString(final String name,
      @Nullable final String defaultValue) {
    String result = getRawString(name, defaultValue);
    if (result != null) {
      result = substitute(result);
    }
    return result;
  }

  @Override
  public String[] getStrings(final String name) {
    final String[] result = getRawStrings(name);
    for (int i = 0; i < result.length; ++i) {
      if (result[i] != null) {
        result[i] = substitute(result[i]);
      }
    }
    return result;
  }

  @Override
  public String[] getStrings(final String name,
      @Nullable final String[] defaultValues) {
    final String[] result = getRawStrings(name, defaultValues);
    if (result != null) {
      for (int i = 0; i < result.length; ++i) {
        if (result[i] != null) {
          result[i] = substitute(result[i]);
        }
      }
    }
    return result;
  }

  @Override
  public String substitute(@Nullable final String value) {
    logger.trace("Getting the substituted string value: {}", value);
    if ((value == null) || (value.length() == 0)) {
      return value;
    }
    final Matcher match = VARIABLE_PATTERN.matcher(StringUtils.EMPTY);
    String result = value;
    for (int depth = 0; depth < MAX_SUBSTITUTION_DEPTH; ++depth) {
      match.reset(result);
      if (!match.find()) {
        return result;
      }
      // get the variable name to be substituted
      String varName = match.group();
      // remove ${var_name} around the variable var_name
      varName = varName.substring(2, varName.length() - 1);
      // now find the substituted value
      String varValue = null;
      // try to find the name in this DefaultConfiguration object
      final Property varItem = get(varName);
      if (varItem != null) {
        try {
          varValue = varItem.getValueAsString();
        } catch (final TypeConvertException e) {
          logger.warn("Failed to convert the property value of '{}' into "
                  + "a string.", varName);
          varValue = null;
        } catch (final NoSuchElementException e) {
          logger.warn("Failed to get the property value of '{}'.", varName);
          varValue = null;
        }
      }
      if (varValue == null) {
        // try to find the name in the system properties registry.
        try {
          varValue = System.getProperty(varName);
        } catch (final Exception se) {
          logger.warn("Failed to get the system property '{}'. ", varName, se);
        }
      }
      // return the literal contains ${name} if no such name is found
      if (varValue == null) {
        logger.warn("The string contains an unsubstitutable variable '{}': {}",
            varName, result);
        return result;
      }
      // substitute
      logger.trace("Before substitution, the string is: {}", result);
      logger.trace("Substituting the variable '{}' with value '{}'", varName,
          varValue);
      result = new Replacer()
          .searchForSubstring(match.group())
          .replaceWithString(varValue)
          .ignoreCase(false)
          .applyTo(result);
      logger.trace("After substitution, the string is: {}", result);
    }
    // check whether there still has any substitution
    match.reset(result);
    if (match.find()) {
      logger.warn("The string contains unsubstituted variable '{}', "
              + "but maximum depth of substitutions has reached: {}",
          match.group(), result);
    }
    return result;
  }

  @Override
  public BigDecimal getBigDecimal(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsBigDecimal();
  }

  @Override
  public BigDecimal getBigDecimal(final String name,
      @Nullable final BigDecimal defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsBigDecimal();
  }

  @Override
  public BigDecimal[] getBigDecimals(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsBigDecimal();
  }

  @Override
  public BigDecimal[] getBigDecimals(final String name,
      @Nullable final BigDecimal[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsBigDecimal();
  }

  @Override
  public BigInteger getBigInteger(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsBigInteger();
  }

  @Override
  public BigInteger getBigInteger(final String name,
      @Nullable final BigInteger defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsBigInteger();
  }

  @Override
  public BigInteger[] getBigIntegers(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsBigInteger();
  }

  @Override
  public BigInteger[] getBigIntegers(final String name,
      @Nullable final BigInteger[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsBigInteger();
  }

  @Override
  public LocalDate getDate(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsDate();
  }

  @Override
  public LocalDate getDate(final String name, @Nullable final LocalDate defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsDate();
  }

  @Override
  public LocalDate[] getDates(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsDate();
  }

  @Override
  public LocalDate[] getDates(final String name, @Nullable final LocalDate[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsDate();
  }

  @Override
  public LocalTime getTime(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsTime();
  }

  @Override
  public LocalTime getTime(final String name, @Nullable final LocalTime defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsTime();
  }

  @Override
  public LocalTime[] getTimes(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsTime();
  }

  @Override
  public LocalTime[] getTimes(final String name, @Nullable final LocalTime[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsTime();
  }

  @Override
  public LocalDateTime getDateTime(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsDateTime();
  }

  @Override
  public LocalDateTime getDateTime(final String name, @Nullable final LocalDateTime defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsDateTime();
  }

  @Override
  public LocalDateTime[] getDateTimes(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsDateTime();
  }

  @Override
  public LocalDateTime[] getDateTimes(final String name, @Nullable final LocalDateTime[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsDateTime();
  }

  @Override
  public byte[] getByteArray(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsByteArray();
  }

  @Override
  public byte[] getByteArray(final String name,
      @Nullable final byte[] defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsByteArray();
  }

  @Override
  public byte[][] getByteArrays(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsByteArray();
  }

  @Override
  public byte[][] getByteArrays(final String name,
      @Nullable final byte[][] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsByteArray();
  }

  @Override
  public <E extends Enum<E>>
  E getEnum(final String name, final Class<E> enumType) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    final String value = prop.getValueAsString();
    try {
      return Enum.valueOf(enumType, value);
    } catch (final NullPointerException | IllegalArgumentException e) {
      throw new ConfigurationError(e);
    }
  }

  @Override
  public <E extends Enum<E>>
  E getEnum(final String name, @Nullable final E defaultValue, final Class<E> enumType) {
    final Property prop = get(name);
    if (prop == null || prop.isEmpty()) {
      return defaultValue;
    }
    final String value = prop.getValueAsString();
    try {
      return Enum.valueOf(enumType, value);
    } catch (final NullPointerException | IllegalArgumentException e) {
      throw new ConfigurationError(e);
    }
  }

  // @SuppressWarnings("unchecked")
  // @Override
  // public <E extends Enum<E>>
  // E[] getEnums(final String name, final Class<E> enumType) {
  //   final Property prop = get(name);
  //   if (prop == null) {
  //     throw new PropertyNotExistError(name);
  //   }
  //   if (prop.isEmpty()) {
  //     return (E[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
  //   }
  //   final String[] values = prop.getValuesAsString();
  //   try {
  //     return Enum.valueOf(enumType, value);
  //   } catch (final NullPointerException | IllegalArgumentException e) {
  //     throw new ConfigurationError(e);
  //   }
  // }

  @Override
  public Class<?> getClass(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    if (prop.isEmpty()) {
      throw new PropertyHasNoValueError(name);
    }
    return prop.getValueAsClass();
  }

  @Override
  public Class<?> getClass(final String name,
      @Nullable final Class<?> defaultValue) {
    final Property prop = get(name);
    if ((prop == null) || prop.isEmpty()) {
      return defaultValue;
    }
    return prop.getValueAsClass();
  }

  @Override
  public Class<?> getClass(final String name,
      @Nullable final String defaultClassName) {
    final Property prop = get(name);
    if ((prop == null) || (prop.getCount() == 0)) {
      if (defaultClassName == null) {
        return null;
      } else {
        try {
          return ClassUtils.getClass(defaultClassName);
        } catch (final ClassNotFoundException e) {
          throw new ConfigurationError(e);
        }
      }
    } else {
      return prop.getValueAsClass();
    }
  }

  @Override
  public Class<?>[] getClasses(final String name) {
    final Property prop = get(name);
    if (prop == null) {
      throw new PropertyNotExistError(name);
    }
    return prop.getValuesAsClass();
  }

  @Override
  public Class<?>[] getClasses(final String name,
      @Nullable final Class<?>[] defaultValues) {
    final Property prop = get(name);
    if (prop == null) {
      return defaultValues;
    }
    return prop.getValuesAsClass();
  }

  @Override
  public Class<?>[] getClasses(final String name,
      @Nullable final String[] defaultClassNames) {
    final Property prop = get(name);
    if (prop == null) {
      if (defaultClassNames == null) {
        return null;
      } else {
        final Class<?>[] result = (Class<?>[]) Array.newInstance(Class.class,
            defaultClassNames.length);
        for (int i = 0; i < defaultClassNames.length; ++i) {
          try {
            result[i] = ClassUtils.getClass(defaultClassNames[i]);
          } catch (final ClassNotFoundException e) {
            throw new ConfigurationError(e);
          }
        }
        return result;
      }
    } else {
      return prop.getValuesAsClass();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getInstance(final String name) {
    final Class<?> clazz = getClass(name);
    try {
      final T result = (T) ConstructorUtils.newInstance(clazz);
      // configure the configurable object with this configuration
      if (result instanceof Configurable) {
        ((Configurable) result).setConfig(this);
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getInstance(final String name,
      @Nullable final Class<?> defaultClass) {
    final Class<?> clazz = getClass(name, defaultClass);
    if (clazz == null) {
      assert (defaultClass == null);
      return null;
    }
    try {
      final T result = (T) ConstructorUtils.newInstance(clazz);
      // configure the configurable object with this configuration
      if (result instanceof Configurable) {
        ((Configurable) result).setConfig(this);
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getInstance(final String name,
      @Nullable final String defaultClassName) {
    final Class<?> clazz = getClass(name, defaultClassName);
    if (clazz == null) {
      assert (defaultClassName == null);
      return null;
    }
    try {

      final T result = (T) ConstructorUtils.newInstance(clazz);
      // configure the configurable object with this configuration
      if (result instanceof Configurable) {
        ((Configurable) result).setConfig(this);
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getInstance(final String name,
      @Nullable final T defaultValue) {
    final Class<?> clazz = getClass(name, (Class<?>) null);
    if (clazz == null) {
      return defaultValue;
    }
    try {
      final T result = (T) ConstructorUtils.newInstance(clazz);
      // configure the configurable object with this configuration
      if (result instanceof Configurable) {
        ((Configurable) result).setConfig(this);
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] getInstances(final String name, final Class<?> clazz) {
    final Class<?>[] classes = getClasses(name);
    if (classes.length == 0) {
      return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    try {
      final T[] result = (T[]) Array.newInstance(clazz, classes.length);
      for (int i = 0; i < classes.length; ++i) {
        final T value = (T) ConstructorUtils.newInstance(classes[i]);
        // configure the configurable object with this configuration
        if (value instanceof Configurable) {
          ((Configurable) value).setConfig(this);
        }
        result[i] = value;
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] getInstances(final String name, final Class<?> clazz,
      @Nullable final Class<?>[] defaultClasses) {
    final Class<?>[] classes = getClasses(name, defaultClasses);
    if (classes == null) {
      assert (defaultClasses == null);
      return null;
    } else if (classes.length == 0) {
      return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    try {
      final T[] result = (T[]) Array.newInstance(clazz, classes.length);
      for (int i = 0; i < classes.length; ++i) {
        final T value = (T) ConstructorUtils.newInstance(classes[i]);
        // configure the configurable object with this configuration
        if (value instanceof Configurable) {
          ((Configurable) value).setConfig(this);
        }
        result[i] = value;
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] getInstances(final String name, final Class<?> clazz,
      @Nullable final String[] defaultClassNames) {
    final Class<?>[] classes = getClasses(name, defaultClassNames);
    if (classes == null) {
      assert (defaultClassNames == null);
      return null;
    } else if (classes.length == 0) {
      return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    try {
      final T[] result = (T[]) Array.newInstance(clazz, classes.length);
      for (int i = 0; i < classes.length; ++i) {
        final T value = (T) ConstructorUtils.newInstance(classes[i]);
        // configure the configurable object with this configuration
        if (value instanceof Configurable) {
          ((Configurable) value).setConfig(this);
        }
        result[i] = value;
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] getInstances(final String name, final Class<?> clazz,
      @Nullable final T[] defaultValues) {
    final Class<?>[] classes = getClasses(name, (Class<?>[]) null);
    if (classes == null) {
      return defaultValues;
    } else if (classes.length == 0) {
      return (T[]) ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    try {
      final T[] result = (T[]) Array.newInstance(clazz, classes.length);
      for (int i = 0; i < classes.length; ++i) {
        final T value = (T) ConstructorUtils.newInstance(classes[i]);
        // configure the configurable object with this configuration
        if (value instanceof Configurable) {
          ((Configurable) value).setConfig(this);
        }
        result[i] = value;
      }
      return result;
    } catch (final Exception e) {
      throw new ConfigurationError(e);
    }
  }

  @Override
  public Config cloneEx() {
    try {
      return (Config) clone();
    } catch (final CloneNotSupportedException e) {
      throw new UnsupportedOperationException(e);
    }
  }

}